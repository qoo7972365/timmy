/*    */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.internal.org.jvnet.staxex.Base64Data;
/*    */ import com.sun.xml.internal.org.jvnet.staxex.XMLStreamReaderEx;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ 
/*    */ 
/*    */ final class StAXExConnector
/*    */   extends StAXStreamConnector
/*    */ {
/*    */   private final XMLStreamReaderEx in;
/*    */   
/*    */   public StAXExConnector(XMLStreamReaderEx in, XmlVisitor visitor) {
/* 48 */     super((XMLStreamReader)in, visitor);
/* 49 */     this.in = in;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void handleCharacters() throws XMLStreamException, SAXException {
/* 54 */     if (this.predictor.expectText()) {
/* 55 */       CharSequence pcdata = this.in.getPCDATA();
/* 56 */       if (pcdata instanceof Base64Data) {
/* 57 */         Base64Data bd = (Base64Data)pcdata;
/* 58 */         Base64Data binary = new Base64Data();
/* 59 */         if (!bd.hasData()) {
/* 60 */           binary.set(bd.getDataHandler());
/*    */         } else {
/* 62 */           binary.set(bd.get(), bd.getDataLen(), bd.getMimeType());
/*    */         } 
/*    */         
/* 65 */         this.visitor.text((CharSequence)binary);
/* 66 */         this.textReported = true;
/*    */       } else {
/* 68 */         this.buffer.append(pcdata);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/StAXExConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */