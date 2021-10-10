/*    */ package com.sun.xml.internal.ws.encoding;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ final class StreamSOAP11Codec
/*    */   extends StreamSOAPCodec
/*    */ {
/*    */   public static final String SOAP11_MIME_TYPE = "text/xml";
/*    */   public static final String DEFAULT_SOAP11_CONTENT_TYPE = "text/xml; charset=utf-8";
/* 52 */   private static final List<String> EXPECTED_CONTENT_TYPES = Collections.singletonList("text/xml");
/*    */   
/*    */   StreamSOAP11Codec() {
/* 55 */     super(SOAPVersion.SOAP_11);
/*    */   }
/*    */   
/*    */   StreamSOAP11Codec(WSBinding binding) {
/* 59 */     super(binding);
/*    */   }
/*    */   
/*    */   StreamSOAP11Codec(WSFeatureList features) {
/* 63 */     super(features);
/*    */   }
/*    */   
/*    */   public String getMimeType() {
/* 67 */     return "text/xml";
/*    */   }
/*    */ 
/*    */   
/*    */   protected ContentType getContentType(Packet packet) {
/* 72 */     ContentTypeImpl.Builder b = getContenTypeBuilder(packet);
/* 73 */     b.soapAction = packet.soapAction;
/* 74 */     return b.build();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getDefaultContentType() {
/* 79 */     return "text/xml; charset=utf-8";
/*    */   }
/*    */   
/*    */   protected List<String> getExpectedContentTypes() {
/* 83 */     return EXPECTED_CONTENT_TYPES;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/StreamSOAP11Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */