/*     */ package com.sun.xml.internal.ws.api.message;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyle;
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyleFeature;
/*     */ import com.oracle.webservices.internal.api.message.MessageContext;
/*     */ import com.oracle.webservices.internal.api.message.MessageContextFactory;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
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
/*     */ public class MessageContextFactory
/*     */   extends MessageContextFactory
/*     */ {
/*     */   private WSFeatureList features;
/*     */   private Codec soapCodec;
/*     */   private Codec xmlCodec;
/*     */   private EnvelopeStyleFeature envelopeStyle;
/*     */   private EnvelopeStyle.Style singleSoapStyle;
/*     */   
/*     */   public MessageContextFactory(WebServiceFeature[] wsf) {
/*  67 */     this((WSFeatureList)new WebServiceFeatureList(wsf));
/*     */   }
/*     */   
/*     */   public MessageContextFactory(WSFeatureList wsf) {
/*  71 */     this.features = wsf;
/*  72 */     this.envelopeStyle = (EnvelopeStyleFeature)this.features.get(EnvelopeStyleFeature.class);
/*  73 */     if (this.envelopeStyle == null) {
/*  74 */       this.envelopeStyle = new EnvelopeStyleFeature(new EnvelopeStyle.Style[] { EnvelopeStyle.Style.SOAP11 });
/*  75 */       this.features.mergeFeatures(new WebServiceFeature[] { (WebServiceFeature)this.envelopeStyle }, false);
/*     */     } 
/*  77 */     for (EnvelopeStyle.Style s : this.envelopeStyle.getStyles()) {
/*  78 */       if (s.isXML()) {
/*  79 */         if (this.xmlCodec == null) this.xmlCodec = Codecs.createXMLCodec(this.features); 
/*     */       } else {
/*  81 */         if (this.soapCodec == null) this.soapCodec = (Codec)Codecs.createSOAPBindingCodec(this.features); 
/*  82 */         this.singleSoapStyle = s;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected MessageContextFactory newFactory(WebServiceFeature... f) {
/*  88 */     return new MessageContextFactory(f);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContext createContext() {
/*  93 */     return packet(null);
/*     */   }
/*     */   
/*     */   public MessageContext createContext(SOAPMessage soap) {
/*  97 */     throwIfIllegalMessageArgument(soap);
/*  98 */     return packet(Messages.create(soap));
/*     */   }
/*     */   
/*     */   public MessageContext createContext(Source m, EnvelopeStyle.Style envelopeStyle) {
/* 102 */     throwIfIllegalMessageArgument(m);
/* 103 */     return packet(Messages.create(m, SOAPVersion.from(envelopeStyle)));
/*     */   }
/*     */   
/*     */   public MessageContext createContext(Source m) {
/* 107 */     throwIfIllegalMessageArgument(m);
/* 108 */     return packet(Messages.create(m, SOAPVersion.from(this.singleSoapStyle)));
/*     */   }
/*     */   
/*     */   public MessageContext createContext(InputStream in, String contentType) throws IOException {
/* 112 */     throwIfIllegalMessageArgument(in);
/*     */     
/* 114 */     Packet p = packet(null);
/* 115 */     this.soapCodec.decode(in, contentType, p);
/* 116 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public MessageContext createContext(InputStream in, MimeHeaders headers) throws IOException {
/* 124 */     String contentType = getHeader(headers, "Content-Type");
/* 125 */     Packet packet = (Packet)createContext(in, contentType);
/* 126 */     packet.acceptableMimeTypes = getHeader(headers, "Accept");
/* 127 */     packet.soapAction = HttpAdapter.fixQuotesAroundSoapAction(getHeader(headers, "SOAPAction"));
/*     */     
/* 129 */     return packet;
/*     */   }
/*     */   
/*     */   static String getHeader(MimeHeaders headers, String name) {
/* 133 */     String[] values = headers.getHeader(name);
/* 134 */     return (values != null && values.length > 0) ? values[0] : null;
/*     */   }
/*     */   
/*     */   static Map<String, List<String>> toMap(MimeHeaders headers) {
/* 138 */     HashMap<String, List<String>> map = new HashMap<>();
/* 139 */     for (Iterator<MimeHeader> i = headers.getAllHeaders(); i.hasNext(); ) {
/* 140 */       MimeHeader mh = i.next();
/* 141 */       List<String> values = map.get(mh.getName());
/* 142 */       if (values == null) {
/* 143 */         values = new ArrayList<>();
/* 144 */         map.put(mh.getName(), values);
/*     */       } 
/* 146 */       values.add(mh.getValue());
/*     */     } 
/* 148 */     return map;
/*     */   }
/*     */   
/*     */   public MessageContext createContext(Message m) {
/* 152 */     throwIfIllegalMessageArgument(m);
/* 153 */     return packet(m);
/*     */   }
/*     */   
/*     */   private Packet packet(Message m) {
/* 157 */     Packet p = new Packet();
/*     */     
/* 159 */     p.codec = this.soapCodec;
/* 160 */     if (m != null) p.setMessage(m); 
/* 161 */     MTOMFeature mf = (MTOMFeature)this.features.get(MTOMFeature.class);
/* 162 */     if (mf != null) {
/* 163 */       p.setMtomFeature(mf);
/*     */     }
/* 165 */     return p;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwIfIllegalMessageArgument(Object message) throws IllegalArgumentException {
/* 171 */     if (message == null) {
/* 172 */       throw new IllegalArgumentException("null messages are not allowed.  Consider using MessageContextFactory.createContext()");
/*     */     }
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public MessageContext doCreate() {
/* 178 */     return packet(null);
/*     */   }
/*     */   @Deprecated
/*     */   public MessageContext doCreate(SOAPMessage m) {
/* 182 */     return createContext(m);
/*     */   }
/*     */   @Deprecated
/*     */   public MessageContext doCreate(Source x, SOAPVersion soapVersion) {
/* 186 */     return packet(Messages.create(x, soapVersion));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/MessageContextFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */