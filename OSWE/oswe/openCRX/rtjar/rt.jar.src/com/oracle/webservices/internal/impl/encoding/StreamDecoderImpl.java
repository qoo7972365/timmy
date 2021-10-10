/*    */ package com.oracle.webservices.internal.impl.encoding;
/*    */ 
/*    */ import com.oracle.webservices.internal.impl.internalspi.encoding.StreamDecoder;
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*    */ import com.sun.xml.internal.ws.api.message.Message;
/*    */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*    */ import com.sun.xml.internal.ws.encoding.StreamSOAPCodec;
/*    */ import com.sun.xml.internal.ws.streaming.TidyXMLStreamReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class StreamDecoderImpl
/*    */   implements StreamDecoder
/*    */ {
/*    */   public Message decode(InputStream in, String charset, AttachmentSet att, SOAPVersion soapVersion) throws IOException {
/* 47 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, in, charset, true);
/* 48 */     TidyXMLStreamReader tidyXMLStreamReader = new TidyXMLStreamReader(reader, in);
/* 49 */     return StreamSOAPCodec.decode(soapVersion, (XMLStreamReader)tidyXMLStreamReader, att);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/oracle/webservices/internal/impl/encoding/StreamDecoderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */