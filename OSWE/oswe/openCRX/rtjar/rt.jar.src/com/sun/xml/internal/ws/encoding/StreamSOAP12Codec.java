/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StreamSOAP12Codec
/*     */   extends StreamSOAPCodec
/*     */ {
/*     */   public static final String SOAP12_MIME_TYPE = "application/soap+xml";
/*     */   public static final String DEFAULT_SOAP12_CONTENT_TYPE = "application/soap+xml; charset=utf-8";
/*  54 */   private static final List<String> EXPECTED_CONTENT_TYPES = Collections.singletonList("application/soap+xml");
/*     */   
/*     */   StreamSOAP12Codec() {
/*  57 */     super(SOAPVersion.SOAP_12);
/*     */   }
/*     */   
/*     */   StreamSOAP12Codec(WSBinding binding) {
/*  61 */     super(binding);
/*     */   }
/*     */   
/*     */   StreamSOAP12Codec(WSFeatureList features) {
/*  65 */     super(features);
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  69 */     return "application/soap+xml";
/*     */   }
/*     */ 
/*     */   
/*     */   protected ContentType getContentType(Packet packet) {
/*  74 */     ContentTypeImpl.Builder b = getContenTypeBuilder(packet);
/*     */     
/*  76 */     if (packet.soapAction == null) {
/*  77 */       return b.build();
/*     */     }
/*  79 */     b.contentType += ";action=" + fixQuotesAroundSoapAction(packet.soapAction);
/*  80 */     return b.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet, AttachmentSet att) throws IOException {
/*  86 */     ContentType ct = new ContentType(contentType);
/*  87 */     packet.soapAction = fixQuotesAroundSoapAction(ct.getParameter("action"));
/*  88 */     super.decode(in, contentType, packet, att);
/*     */   }
/*     */   
/*     */   private String fixQuotesAroundSoapAction(String soapAction) {
/*  92 */     if (soapAction != null && (!soapAction.startsWith("\"") || !soapAction.endsWith("\""))) {
/*  93 */       String fixedSoapAction = soapAction;
/*  94 */       if (!soapAction.startsWith("\""))
/*  95 */         fixedSoapAction = "\"" + fixedSoapAction; 
/*  96 */       if (!soapAction.endsWith("\""))
/*  97 */         fixedSoapAction = fixedSoapAction + "\""; 
/*  98 */       return fixedSoapAction;
/*     */     } 
/* 100 */     return soapAction;
/*     */   }
/*     */   
/*     */   protected List<String> getExpectedContentTypes() {
/* 104 */     return EXPECTED_CONTENT_TYPES;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultContentType() {
/* 109 */     return "application/soap+xml; charset=utf-8";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/StreamSOAP12Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */