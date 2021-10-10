/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.encoding.SOAPBindingCodec;
/*     */ import com.sun.xml.internal.ws.encoding.StreamSOAPCodec;
/*     */ import com.sun.xml.internal.ws.encoding.XMLHTTPBindingCodec;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Codecs
/*     */ {
/*     */   @NotNull
/*     */   public static SOAPBindingCodec createSOAPBindingCodec(WSFeatureList feature) {
/*  58 */     return (SOAPBindingCodec)new SOAPBindingCodec(feature);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Codec createXMLCodec(WSFeatureList feature) {
/*  68 */     return (Codec)new XMLHTTPBindingCodec(feature);
/*     */   }
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
/*     */   @NotNull
/*     */   public static SOAPBindingCodec createSOAPBindingCodec(WSBinding binding, StreamSOAPCodec xmlEnvelopeCodec) {
/*  89 */     return (SOAPBindingCodec)new SOAPBindingCodec(binding.getFeatures(), xmlEnvelopeCodec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static StreamSOAPCodec createSOAPEnvelopeXmlCodec(@NotNull SOAPVersion version) {
/* 102 */     return (StreamSOAPCodec)StreamSOAPCodec.create(version);
/*     */   }
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
/*     */   @NotNull
/*     */   public static StreamSOAPCodec createSOAPEnvelopeXmlCodec(@NotNull WSBinding binding) {
/* 117 */     return (StreamSOAPCodec)StreamSOAPCodec.create(binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static StreamSOAPCodec createSOAPEnvelopeXmlCodec(@NotNull WSFeatureList features) {
/* 130 */     return (StreamSOAPCodec)StreamSOAPCodec.create(features);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/Codecs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */