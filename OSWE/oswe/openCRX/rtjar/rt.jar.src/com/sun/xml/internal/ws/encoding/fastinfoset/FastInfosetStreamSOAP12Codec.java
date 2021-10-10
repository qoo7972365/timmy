/*    */ package com.sun.xml.internal.ws.encoding.fastinfoset;
/*    */ 
/*    */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*    */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*    */ import com.sun.xml.internal.ws.api.pipe.StreamSOAPCodec;
/*    */ import com.sun.xml.internal.ws.encoding.ContentTypeImpl;
/*    */ import com.sun.xml.internal.ws.message.stream.StreamHeader;
/*    */ import com.sun.xml.internal.ws.message.stream.StreamHeader12;
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
/*    */ final class FastInfosetStreamSOAP12Codec
/*    */   extends FastInfosetStreamSOAPCodec
/*    */ {
/*    */   FastInfosetStreamSOAP12Codec(StreamSOAPCodec soapCodec, boolean retainState) {
/* 47 */     super(soapCodec, SOAPVersion.SOAP_12, retainState, retainState ? "application/vnd.sun.stateful.soap+fastinfoset" : "application/soap+fastinfoset");
/*    */   }
/*    */ 
/*    */   
/*    */   private FastInfosetStreamSOAP12Codec(FastInfosetStreamSOAPCodec that) {
/* 52 */     super(that);
/*    */   }
/*    */   
/*    */   public Codec copy() {
/* 56 */     return new FastInfosetStreamSOAP12Codec(this);
/*    */   }
/*    */   
/*    */   protected final StreamHeader createHeader(XMLStreamReader reader, XMLStreamBuffer mark) {
/* 60 */     return (StreamHeader)new StreamHeader12(reader, mark);
/*    */   }
/*    */   
/*    */   protected ContentType getContentType(String soapAction) {
/* 64 */     if (soapAction == null) {
/* 65 */       return this._defaultContentType;
/*    */     }
/* 67 */     return (ContentType)new ContentTypeImpl(this._defaultContentType
/* 68 */         .getContentType() + ";action=\"" + soapAction + "\"");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/fastinfoset/FastInfosetStreamSOAP12Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */