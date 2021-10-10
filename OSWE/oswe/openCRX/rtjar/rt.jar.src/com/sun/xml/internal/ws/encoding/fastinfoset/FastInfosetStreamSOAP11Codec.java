/*    */ package com.sun.xml.internal.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*    */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*    */ import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
/*    */ import com.sun.xml.internal.ws.message.stream.StreamHeader;
/*    */ import com.sun.xml.internal.ws.message.stream.StreamHeader11;
/*    */ import javax.xml.stream.XMLStreamReader;
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
/*    */ final class FastInfosetStreamSOAP11Codec
/*    */   extends FastInfosetStreamSOAPCodec
/*    */ {
/*    */   FastInfosetStreamSOAP11Codec(StreamSOAPCodec soapCodec, boolean retainState) {
/* 47 */     super(soapCodec, SOAPVersion.SOAP_11, retainState, retainState ? "application/vnd.sun.stateful.fastinfoset" : "application/fastinfoset");
/*    */   }
/*    */ 
/*    */   
/*    */   private FastInfosetStreamSOAP11Codec(FastInfosetStreamSOAP11Codec that) {
/* 52 */     super(that);
/*    */   }
/*    */   
/*    */   public Codec copy() {
/* 56 */     return new FastInfosetStreamSOAP11Codec(this);
/*    */   }
/*    */   
/*    */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 60 */     return (StreamHeader)new StreamHeader11(reader, mark);
/*    */   }
/*    */   
/*    */   protected ContentType getContentType(String soapAction) {
/* 64 */     if (soapAction == null || soapAction.length() == 0) {
/* 65 */       return this._defaultContentType;
/*    */     }
/* 67 */     return (ContentType)new ContentTypeImpl(this._defaultContentType.getContentType(), soapAction);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/fastinfoset/FastInfosetStreamSOAP11Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */