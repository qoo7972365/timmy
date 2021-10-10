/*     */ package com.sun.xml.internal.ws.api;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.internal.ws.encoding.SOAPBindingCodec;
/*     */ import com.sun.xml.internal.ws.encoding.XMLHTTPBindingCodec;
/*     */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.BindingType;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ public abstract class BindingID
/*     */ {
/*     */   @NotNull
/*     */   public final WSBinding createBinding() {
/*  99 */     return (WSBinding)BindingImpl.create(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getTransport() {
/* 111 */     return "http://schemas.xmlsoap.org/soap/http";
/*     */   }
/*     */   @NotNull
/*     */   public final WSBinding createBinding(WebServiceFeature... features) {
/* 115 */     return (WSBinding)BindingImpl.create(this, features);
/*     */   }
/*     */   @NotNull
/*     */   public final WSBinding createBinding(WSFeatureList features) {
/* 119 */     return createBinding(features.toArray());
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
/*     */   public abstract SOAPVersion getSOAPVersion();
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
/*     */   public abstract Codec createEncoder(@NotNull WSBinding paramWSBinding);
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
/*     */   public abstract String toString();
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
/*     */   public WebServiceFeatureList createBuiltinFeatureList() {
/* 171 */     return new WebServiceFeatureList();
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
/*     */   public boolean canGenerateWSDL() {
/* 184 */     return false;
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
/*     */   public String getParameter(String parameterName, String defaultValue) {
/* 214 */     return defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 222 */     if (!(obj instanceof BindingID))
/* 223 */       return false; 
/* 224 */     return toString().equals(obj.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 229 */     return toString().hashCode();
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
/*     */   @NotNull
/*     */   public static BindingID parse(String lexical) {
/* 247 */     if (lexical.equals(XML_HTTP.toString()))
/* 248 */       return XML_HTTP; 
/* 249 */     if (lexical.equals(REST_HTTP.toString()))
/* 250 */       return REST_HTTP; 
/* 251 */     if (belongsTo(lexical, SOAP11_HTTP.toString()))
/* 252 */       return customize(lexical, SOAP11_HTTP); 
/* 253 */     if (belongsTo(lexical, SOAP12_HTTP.toString()))
/* 254 */       return customize(lexical, SOAP12_HTTP); 
/* 255 */     if (belongsTo(lexical, "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")) {
/* 256 */       return customize(lexical, X_SOAP12_HTTP);
/*     */     }
/*     */     
/* 259 */     for (BindingIDFactory f : ServiceFinder.find(BindingIDFactory.class)) {
/* 260 */       BindingID r = f.parse(lexical);
/* 261 */       if (r != null) {
/* 262 */         return r;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     throw new WebServiceException("Wrong binding ID: " + lexical);
/*     */   }
/*     */   
/*     */   private static boolean belongsTo(String lexical, String id) {
/* 270 */     return (lexical.equals(id) || lexical.startsWith(id + '?'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SOAPHTTPImpl customize(String lexical, SOAPHTTPImpl base) {
/* 277 */     if (lexical.equals(base.toString())) {
/* 278 */       return base;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     SOAPHTTPImpl r = new SOAPHTTPImpl(base.getSOAPVersion(), lexical, base.canGenerateWSDL());
/*     */     
/*     */     try {
/* 287 */       if (lexical.indexOf('?') == -1) {
/* 288 */         return r;
/*     */       }
/* 290 */       String query = URLDecoder.decode(lexical.substring(lexical.indexOf('?') + 1), "UTF-8");
/* 291 */       for (String token : query.split("&")) {
/* 292 */         int idx = token.indexOf('=');
/* 293 */         if (idx < 0)
/* 294 */           throw new WebServiceException("Malformed binding ID (no '=' in " + token + ")"); 
/* 295 */         r.parameters.put(token.substring(0, idx), token.substring(idx + 1));
/*     */       } 
/* 297 */     } catch (UnsupportedEncodingException e) {
/* 298 */       throw new AssertionError(e);
/*     */     } 
/*     */     
/* 301 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static BindingID parse(Class<?> implClass) {
/* 313 */     BindingType bindingType = implClass.<BindingType>getAnnotation(BindingType.class);
/* 314 */     if (bindingType != null) {
/* 315 */       String bindingId = bindingType.value();
/* 316 */       if (bindingId.length() > 0) {
/* 317 */         return parse(bindingId);
/*     */       }
/*     */     } 
/* 320 */     return SOAP11_HTTP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 327 */   public static final SOAPHTTPImpl X_SOAP12_HTTP = new SOAPHTTPImpl(SOAPVersion.SOAP_12, "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public static final SOAPHTTPImpl SOAP12_HTTP = new SOAPHTTPImpl(SOAPVersion.SOAP_12, "http://www.w3.org/2003/05/soap/bindings/HTTP/", true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 338 */   public static final SOAPHTTPImpl SOAP11_HTTP = new SOAPHTTPImpl(SOAPVersion.SOAP_11, "http://schemas.xmlsoap.org/wsdl/soap/http", true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 344 */   public static final SOAPHTTPImpl SOAP12_HTTP_MTOM = new SOAPHTTPImpl(SOAPVersion.SOAP_12, "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 349 */   public static final SOAPHTTPImpl SOAP11_HTTP_MTOM = new SOAPHTTPImpl(SOAPVersion.SOAP_11, "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true", true, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 356 */   public static final BindingID XML_HTTP = new Impl(SOAPVersion.SOAP_11, "http://www.w3.org/2004/08/wsdl/http", false)
/*     */     {
/*     */       public Codec createEncoder(WSBinding binding) {
/* 359 */         return (Codec)new XMLHTTPBindingCodec(binding.getFeatures());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 366 */   private static final BindingID REST_HTTP = new Impl(SOAPVersion.SOAP_11, "http://jax-ws.dev.java.net/rest", true)
/*     */     {
/*     */       public Codec createEncoder(WSBinding binding) {
/* 369 */         return (Codec)new XMLHTTPBindingCodec(binding.getFeatures());
/*     */       }
/*     */     };
/*     */   
/*     */   private static abstract class Impl extends BindingID {
/*     */     final SOAPVersion version;
/*     */     private final String lexical;
/*     */     private final boolean canGenerateWSDL;
/*     */     
/*     */     public Impl(SOAPVersion version, String lexical, boolean canGenerateWSDL) {
/* 379 */       this.version = version;
/* 380 */       this.lexical = lexical;
/* 381 */       this.canGenerateWSDL = canGenerateWSDL;
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPVersion getSOAPVersion() {
/* 386 */       return this.version;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 391 */       return this.lexical;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean canGenerateWSDL() {
/* 397 */       return this.canGenerateWSDL;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SOAPHTTPImpl
/*     */     extends Impl
/*     */     implements Cloneable
/*     */   {
/* 405 */     Map<String, String> parameters = new HashMap<>();
/*     */     
/*     */     static final String MTOM_PARAM = "mtom";
/*     */     
/*     */     public SOAPHTTPImpl(SOAPVersion version, String lexical, boolean canGenerateWSDL) {
/* 410 */       super(version, lexical, canGenerateWSDL);
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPHTTPImpl(SOAPVersion version, String lexical, boolean canGenerateWSDL, boolean mtomEnabled) {
/* 415 */       this(version, lexical, canGenerateWSDL);
/* 416 */       String mtomStr = mtomEnabled ? "true" : "false";
/* 417 */       this.parameters.put("mtom", mtomStr);
/*     */     }
/*     */     @NotNull
/*     */     public Codec createEncoder(WSBinding binding) {
/* 421 */       return (Codec)new SOAPBindingCodec(binding.getFeatures());
/*     */     }
/*     */     
/*     */     private Boolean isMTOMEnabled() {
/* 425 */       String mtom = this.parameters.get("mtom");
/* 426 */       return (mtom == null) ? null : Boolean.valueOf(mtom);
/*     */     }
/*     */ 
/*     */     
/*     */     public WebServiceFeatureList createBuiltinFeatureList() {
/* 431 */       WebServiceFeatureList r = super.createBuiltinFeatureList();
/* 432 */       Boolean mtom = isMTOMEnabled();
/* 433 */       if (mtom != null)
/* 434 */         r.add((WebServiceFeature)new MTOMFeature(mtom.booleanValue())); 
/* 435 */       return r;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getParameter(String parameterName, String defaultValue) {
/* 440 */       if (this.parameters.get(parameterName) == null)
/* 441 */         return super.getParameter(parameterName, defaultValue); 
/* 442 */       return this.parameters.get(parameterName);
/*     */     }
/*     */ 
/*     */     
/*     */     public SOAPHTTPImpl clone() throws CloneNotSupportedException {
/* 447 */       return (SOAPHTTPImpl)super.clone();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/BindingID.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */