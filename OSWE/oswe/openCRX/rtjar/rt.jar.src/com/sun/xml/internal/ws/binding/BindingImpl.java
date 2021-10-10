/*     */ package com.sun.xml.internal.ws.binding;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.EnvelopeStyleFeature;
/*     */ import com.oracle.webservices.internal.api.message.MessageContextFactory;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.internal.ws.developer.BindingTypeFeature;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.CommandInfo;
/*     */ import javax.activation.CommandMap;
/*     */ import javax.activation.MailcapCommandMap;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.soap.AddressingFeature;
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
/*     */ public abstract class BindingImpl
/*     */   implements WSBinding
/*     */ {
/*  73 */   protected static final WebServiceFeature[] EMPTY_FEATURES = new WebServiceFeature[0];
/*     */   
/*     */   private HandlerConfiguration handlerConfig;
/*     */   
/*  77 */   private final Set<QName> addedHeaders = new HashSet<>();
/*  78 */   private final Set<QName> knownHeaders = new HashSet<>();
/*  79 */   private final Set<QName> unmodKnownHeaders = Collections.unmodifiableSet(this.knownHeaders);
/*     */   
/*     */   private final BindingID bindingId;
/*     */   
/*     */   protected final WebServiceFeatureList features;
/*  84 */   protected final Map<QName, WebServiceFeatureList> operationFeatures = new HashMap<>();
/*     */   
/*  86 */   protected final Map<QName, WebServiceFeatureList> inputMessageFeatures = new HashMap<>();
/*     */   
/*  88 */   protected final Map<QName, WebServiceFeatureList> outputMessageFeatures = new HashMap<>();
/*     */   
/*  90 */   protected final Map<MessageKey, WebServiceFeatureList> faultMessageFeatures = new HashMap<>();
/*     */   
/*  92 */   protected Service.Mode serviceMode = Service.Mode.PAYLOAD;
/*     */   
/*     */   protected MessageContextFactory messageContextFactory;
/*     */   
/*     */   protected BindingImpl(BindingID bindingId, WebServiceFeature... features) {
/*  97 */     this.bindingId = bindingId;
/*  98 */     this.handlerConfig = new HandlerConfiguration(Collections.emptySet(), Collections.emptyList());
/*  99 */     if (this.handlerConfig.getHandlerKnownHeaders() != null)
/* 100 */       this.knownHeaders.addAll(this.handlerConfig.getHandlerKnownHeaders()); 
/* 101 */     this.features = new WebServiceFeatureList(features);
/* 102 */     this.features.validate();
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<Handler> getHandlerChain() {
/* 108 */     return this.handlerConfig.getHandlerChain();
/*     */   }
/*     */   
/*     */   public HandlerConfiguration getHandlerConfig() {
/* 112 */     return this.handlerConfig;
/*     */   }
/*     */   
/*     */   protected void setHandlerConfig(HandlerConfiguration handlerConfig) {
/* 116 */     this.handlerConfig = handlerConfig;
/* 117 */     this.knownHeaders.clear();
/* 118 */     this.knownHeaders.addAll(this.addedHeaders);
/* 119 */     if (handlerConfig != null && handlerConfig.getHandlerKnownHeaders() != null)
/* 120 */       this.knownHeaders.addAll(handlerConfig.getHandlerKnownHeaders()); 
/*     */   }
/*     */   
/*     */   public void setMode(@NotNull Service.Mode mode) {
/* 124 */     this.serviceMode = mode;
/*     */   }
/*     */   
/*     */   public Set<QName> getKnownHeaders() {
/* 128 */     return this.unmodKnownHeaders;
/*     */   }
/*     */   
/*     */   public boolean addKnownHeader(QName headerQName) {
/* 132 */     this.addedHeaders.add(headerQName);
/* 133 */     return this.knownHeaders.add(headerQName);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public BindingID getBindingId() {
/* 139 */     return this.bindingId;
/*     */   }
/*     */   
/*     */   public final SOAPVersion getSOAPVersion() {
/* 143 */     return this.bindingId.getSOAPVersion();
/*     */   }
/*     */   
/*     */   public AddressingVersion getAddressingVersion() {
/*     */     AddressingVersion addressingVersion;
/* 148 */     if (this.features.isEnabled((Class)AddressingFeature.class)) {
/* 149 */       addressingVersion = AddressingVersion.W3C;
/* 150 */     } else if (this.features.isEnabled((Class)MemberSubmissionAddressingFeature.class)) {
/* 151 */       addressingVersion = AddressingVersion.MEMBER;
/*     */     } else {
/* 153 */       addressingVersion = null;
/* 154 */     }  return addressingVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final Codec createCodec() {
/* 164 */     initializeJavaActivationHandlers();
/*     */     
/* 166 */     return this.bindingId.createEncoder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initializeJavaActivationHandlers() {
/*     */     try {
/* 172 */       CommandMap map = CommandMap.getDefaultCommandMap();
/* 173 */       if (map instanceof MailcapCommandMap) {
/* 174 */         MailcapCommandMap mailMap = (MailcapCommandMap)map;
/*     */ 
/*     */         
/* 177 */         if (!cmdMapInitialized(mailMap)) {
/* 178 */           mailMap.addMailcap("text/xml;;x-java-content-handler=com.sun.xml.internal.ws.encoding.XmlDataContentHandler");
/* 179 */           mailMap.addMailcap("application/xml;;x-java-content-handler=com.sun.xml.internal.ws.encoding.XmlDataContentHandler");
/* 180 */           mailMap.addMailcap("image/*;;x-java-content-handler=com.sun.xml.internal.ws.encoding.ImageDataContentHandler");
/* 181 */           mailMap.addMailcap("text/plain;;x-java-content-handler=com.sun.xml.internal.ws.encoding.StringDataContentHandler");
/*     */         } 
/*     */       } 
/* 184 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean cmdMapInitialized(MailcapCommandMap mailMap) {
/* 190 */     CommandInfo[] commands = mailMap.getAllCommands("text/xml");
/* 191 */     if (commands == null || commands.length == 0) {
/* 192 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     String saajClassName = "com.sun.xml.internal.messaging.saaj.soap.XmlDataContentHandler";
/* 201 */     String jaxwsClassName = "com.sun.xml.internal.ws.encoding.XmlDataContentHandler";
/* 202 */     for (CommandInfo command : commands) {
/* 203 */       String commandClass = command.getCommandClass();
/* 204 */       if (saajClassName.equals(commandClass) || jaxwsClassName
/* 205 */         .equals(commandClass)) {
/* 206 */         return true;
/*     */       }
/*     */     } 
/* 209 */     return false;
/*     */   }
/*     */   
/*     */   public static BindingImpl create(@NotNull BindingID bindingId) {
/* 213 */     if (bindingId.equals(BindingID.XML_HTTP)) {
/* 214 */       return new HTTPBindingImpl();
/*     */     }
/* 216 */     return new SOAPBindingImpl(bindingId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BindingImpl create(@NotNull BindingID bindingId, WebServiceFeature[] features) {
/* 221 */     for (WebServiceFeature feature : features) {
/* 222 */       if (feature instanceof BindingTypeFeature) {
/* 223 */         BindingTypeFeature f = (BindingTypeFeature)feature;
/* 224 */         bindingId = BindingID.parse(f.getBindingId());
/*     */       } 
/*     */     } 
/* 227 */     if (bindingId.equals(BindingID.XML_HTTP)) {
/* 228 */       return new HTTPBindingImpl(features);
/*     */     }
/* 230 */     return new SOAPBindingImpl(bindingId, features);
/*     */   }
/*     */   
/*     */   public static WSBinding getDefaultBinding() {
/* 234 */     return new SOAPBindingImpl((BindingID)BindingID.SOAP11_HTTP);
/*     */   }
/*     */   
/*     */   public String getBindingID() {
/* 238 */     return this.bindingId.toString();
/*     */   }
/*     */   @Nullable
/*     */   public <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType) {
/* 242 */     return this.features.get(featureType);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <F extends WebServiceFeature> F getOperationFeature(@NotNull Class<F> featureType, @NotNull QName operationName) {
/* 247 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 248 */     return FeatureListUtil.mergeFeature(featureType, operationFeatureList, this.features);
/*     */   }
/*     */   
/*     */   public boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> feature) {
/* 252 */     return this.features.isEnabled(feature);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOperationFeatureEnabled(@NotNull Class<? extends WebServiceFeature> featureType, @NotNull QName operationName) {
/* 257 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 258 */     return FeatureListUtil.isFeatureEnabled(featureType, operationFeatureList, this.features);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeatureList getFeatures() {
/* 264 */     if (!isFeatureEnabled((Class)EnvelopeStyleFeature.class)) {
/* 265 */       WebServiceFeature[] f = { (WebServiceFeature)getSOAPVersion().toFeature() };
/* 266 */       this.features.mergeFeatures(f, false);
/*     */     } 
/* 268 */     return this.features;
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceFeatureList getOperationFeatures(@NotNull QName operationName) {
/* 272 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 273 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, this.features });
/*     */   }
/*     */   @NotNull
/*     */   public WebServiceFeatureList getInputMessageFeatures(@NotNull QName operationName) {
/* 277 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 278 */     WebServiceFeatureList messageFeatureList = this.inputMessageFeatures.get(operationName);
/* 279 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, messageFeatureList, this.features });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeatureList getOutputMessageFeatures(@NotNull QName operationName) {
/* 284 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 285 */     WebServiceFeatureList messageFeatureList = this.outputMessageFeatures.get(operationName);
/* 286 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, messageFeatureList, this.features });
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WebServiceFeatureList getFaultMessageFeatures(@NotNull QName operationName, @NotNull QName messageName) {
/* 291 */     WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
/* 292 */     WebServiceFeatureList messageFeatureList = this.faultMessageFeatures.get(new MessageKey(operationName, messageName));
/*     */     
/* 294 */     return FeatureListUtil.mergeList(new WebServiceFeatureList[] { operationFeatureList, messageFeatureList, this.features });
/*     */   }
/*     */   
/*     */   public void setOperationFeatures(@NotNull QName operationName, WebServiceFeature... newFeatures) {
/* 298 */     if (newFeatures != null) {
/* 299 */       WebServiceFeatureList featureList = this.operationFeatures.get(operationName);
/* 300 */       if (featureList == null) {
/* 301 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 303 */       for (WebServiceFeature f : newFeatures) {
/* 304 */         featureList.add(f);
/*     */       }
/* 306 */       this.operationFeatures.put(operationName, featureList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setInputMessageFeatures(@NotNull QName operationName, WebServiceFeature... newFeatures) {
/* 311 */     if (newFeatures != null) {
/* 312 */       WebServiceFeatureList featureList = this.inputMessageFeatures.get(operationName);
/* 313 */       if (featureList == null) {
/* 314 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 316 */       for (WebServiceFeature f : newFeatures) {
/* 317 */         featureList.add(f);
/*     */       }
/* 319 */       this.inputMessageFeatures.put(operationName, featureList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOutputMessageFeatures(@NotNull QName operationName, WebServiceFeature... newFeatures) {
/* 324 */     if (newFeatures != null) {
/* 325 */       WebServiceFeatureList featureList = this.outputMessageFeatures.get(operationName);
/* 326 */       if (featureList == null) {
/* 327 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 329 */       for (WebServiceFeature f : newFeatures) {
/* 330 */         featureList.add(f);
/*     */       }
/* 332 */       this.outputMessageFeatures.put(operationName, featureList);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFaultMessageFeatures(@NotNull QName operationName, @NotNull QName messageName, WebServiceFeature... newFeatures) {
/* 337 */     if (newFeatures != null) {
/* 338 */       MessageKey key = new MessageKey(operationName, messageName);
/* 339 */       WebServiceFeatureList featureList = this.faultMessageFeatures.get(key);
/* 340 */       if (featureList == null) {
/* 341 */         featureList = new WebServiceFeatureList();
/*     */       }
/* 343 */       for (WebServiceFeature f : newFeatures) {
/* 344 */         featureList.add(f);
/*     */       }
/* 346 */       this.faultMessageFeatures.put(key, featureList);
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public synchronized MessageContextFactory getMessageContextFactory() {
/* 351 */     if (this.messageContextFactory == null) {
/* 352 */       this.messageContextFactory = MessageContextFactory.createFactory(getFeatures().toArray());
/*     */     }
/* 354 */     return this.messageContextFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class MessageKey
/*     */   {
/*     */     private final QName operationName;
/*     */     
/*     */     private final QName messageName;
/*     */ 
/*     */     
/*     */     public MessageKey(QName operationName, QName messageName) {
/* 367 */       this.operationName = operationName;
/* 368 */       this.messageName = messageName;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 373 */       int hashFirst = (this.operationName != null) ? this.operationName.hashCode() : 0;
/* 374 */       int hashSecond = (this.messageName != null) ? this.messageName.hashCode() : 0;
/*     */       
/* 376 */       return (hashFirst + hashSecond) * hashSecond + hashFirst;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 381 */       if (obj == null) {
/* 382 */         return false;
/*     */       }
/* 384 */       if (getClass() != obj.getClass()) {
/* 385 */         return false;
/*     */       }
/* 387 */       MessageKey other = (MessageKey)obj;
/* 388 */       if (this.operationName != other.operationName && (this.operationName == null || !this.operationName.equals(other.operationName))) {
/* 389 */         return false;
/*     */       }
/* 391 */       if (this.messageName != other.messageName && (this.messageName == null || !this.messageName.equals(other.messageName))) {
/* 392 */         return false;
/*     */       }
/* 394 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 399 */       return "(" + this.operationName + ", " + this.messageName + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/binding/BindingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */