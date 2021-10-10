/*     */ package com.sun.xml.internal.ws.handler;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.message.DOMMessage;
/*     */ import com.sun.xml.internal.ws.message.EmptyMessageImpl;
/*     */ import com.sun.xml.internal.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.internal.ws.message.source.PayloadSourceMessage;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBSource;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.LogicalMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ class LogicalMessageImpl
/*     */   implements LogicalMessage
/*     */ {
/*     */   private Packet packet;
/*     */   protected BindingContext defaultJaxbContext;
/*  74 */   private ImmutableLM lm = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogicalMessageImpl(BindingContext defaultJaxbContext, Packet packet) {
/*  80 */     this.packet = packet;
/*  81 */     this.defaultJaxbContext = defaultJaxbContext;
/*     */   }
/*     */   
/*     */   public Source getPayload() {
/*  85 */     if (this.lm == null) {
/*  86 */       Source payload = this.packet.getMessage().copy().readPayloadAsSource();
/*  87 */       if (payload instanceof DOMSource) {
/*  88 */         this.lm = createLogicalMessageImpl(payload);
/*     */       }
/*  90 */       return payload;
/*     */     } 
/*  92 */     return this.lm.getPayload();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPayload(Source payload) {
/*  97 */     this.lm = createLogicalMessageImpl(payload);
/*     */   }
/*     */   
/*     */   private ImmutableLM createLogicalMessageImpl(Source payload) {
/* 101 */     if (payload == null) {
/* 102 */       this.lm = new EmptyLogicalMessageImpl();
/* 103 */     } else if (payload instanceof DOMSource) {
/* 104 */       this.lm = new DOMLogicalMessageImpl((DOMSource)payload);
/*     */     } else {
/* 106 */       this.lm = new SourceLogicalMessageImpl(payload);
/*     */     } 
/* 108 */     return this.lm;
/*     */   }
/*     */   public Object getPayload(BindingContext context) {
/*     */     Object o;
/* 112 */     if (context == null) {
/* 113 */       context = this.defaultJaxbContext;
/*     */     }
/* 115 */     if (context == null) {
/* 116 */       throw new WebServiceException("JAXBContext parameter cannot be null");
/*     */     }
/*     */     
/* 119 */     if (this.lm == null) {
/*     */       try {
/* 121 */         o = this.packet.getMessage().copy().readPayloadAsJAXB(context.createUnmarshaller());
/* 122 */       } catch (JAXBException e) {
/* 123 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } else {
/* 126 */       o = this.lm.getPayload(context);
/* 127 */       this.lm = new JAXBLogicalMessageImpl(context.getJAXBContext(), o);
/*     */     } 
/* 129 */     return o;
/*     */   }
/*     */   public Object getPayload(JAXBContext context) {
/*     */     Object o;
/* 133 */     if (context == null) {
/* 134 */       return getPayload(this.defaultJaxbContext);
/*     */     }
/* 136 */     if (context == null) {
/* 137 */       throw new WebServiceException("JAXBContext parameter cannot be null");
/*     */     }
/*     */     
/* 140 */     if (this.lm == null) {
/*     */       try {
/* 142 */         o = this.packet.getMessage().copy().readPayloadAsJAXB(context.createUnmarshaller());
/* 143 */       } catch (JAXBException e) {
/* 144 */         throw new WebServiceException(e);
/*     */       } 
/*     */     } else {
/* 147 */       o = this.lm.getPayload(context);
/* 148 */       this.lm = new JAXBLogicalMessageImpl(context, o);
/*     */     } 
/* 150 */     return o;
/*     */   }
/*     */   
/*     */   public void setPayload(Object payload, BindingContext context) {
/* 154 */     if (context == null) {
/* 155 */       context = this.defaultJaxbContext;
/*     */     }
/* 157 */     if (payload == null) {
/* 158 */       this.lm = new EmptyLogicalMessageImpl();
/*     */     } else {
/* 160 */       this.lm = new JAXBLogicalMessageImpl(context.getJAXBContext(), payload);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPayload(Object payload, JAXBContext context) {
/* 165 */     if (context == null) {
/* 166 */       setPayload(payload, this.defaultJaxbContext);
/*     */     }
/* 168 */     if (payload == null) {
/* 169 */       this.lm = new EmptyLogicalMessageImpl();
/*     */     } else {
/* 171 */       this.lm = new JAXBLogicalMessageImpl(context, payload);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPayloadModifed() {
/* 176 */     return (this.lm != null);
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
/*     */   public Message getMessage(MessageHeaders headers, AttachmentSet attachments, WSBinding binding) {
/* 188 */     assert isPayloadModifed();
/* 189 */     if (isPayloadModifed()) {
/* 190 */       return this.lm.getMessage(headers, attachments, binding);
/*     */     }
/* 192 */     return this.packet.getMessage();
/*     */   }
/*     */   
/*     */   private abstract class ImmutableLM {
/*     */     private ImmutableLM() {}
/*     */     
/*     */     public abstract Source getPayload();
/*     */     
/*     */     public abstract Object getPayload(BindingContext param1BindingContext);
/*     */     
/*     */     public abstract Object getPayload(JAXBContext param1JAXBContext);
/*     */     
/*     */     public abstract Message getMessage(MessageHeaders param1MessageHeaders, AttachmentSet param1AttachmentSet, WSBinding param1WSBinding); }
/*     */   
/*     */   private class DOMLogicalMessageImpl extends SourceLogicalMessageImpl {
/*     */     private DOMSource dom;
/*     */     
/*     */     public DOMLogicalMessageImpl(DOMSource dom) {
/* 210 */       super(dom);
/* 211 */       this.dom = dom;
/*     */     }
/*     */ 
/*     */     
/*     */     public Source getPayload() {
/* 216 */       return this.dom;
/*     */     }
/*     */     
/*     */     public Message getMessage(MessageHeaders headers, AttachmentSet attachments, WSBinding binding) {
/* 220 */       Node n = this.dom.getNode();
/* 221 */       if (n.getNodeType() == 9) {
/* 222 */         n = ((Document)n).getDocumentElement();
/*     */       }
/* 224 */       return (Message)new DOMMessage(binding.getSOAPVersion(), headers, (Element)n, attachments);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class EmptyLogicalMessageImpl
/*     */     extends ImmutableLM
/*     */   {
/*     */     public Source getPayload() {
/* 235 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getPayload(JAXBContext context) {
/* 240 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getPayload(BindingContext context) {
/* 245 */       return null;
/*     */     }
/*     */     
/*     */     public Message getMessage(MessageHeaders headers, AttachmentSet attachments, WSBinding binding) {
/* 249 */       return (Message)new EmptyMessageImpl(headers, attachments, binding.getSOAPVersion());
/*     */     }
/*     */   }
/*     */   
/*     */   private class JAXBLogicalMessageImpl extends ImmutableLM {
/*     */     private JAXBContext ctxt;
/*     */     private Object o;
/*     */     
/*     */     public JAXBLogicalMessageImpl(JAXBContext ctxt, Object o) {
/* 258 */       this.ctxt = ctxt;
/* 259 */       this.o = o;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Source getPayload() {
/* 265 */       JAXBContext context = this.ctxt;
/* 266 */       if (context == null) {
/* 267 */         context = LogicalMessageImpl.this.defaultJaxbContext.getJAXBContext();
/*     */       }
/*     */       try {
/* 270 */         return (Source)new JAXBSource(context, this.o);
/* 271 */       } catch (JAXBException e) {
/* 272 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getPayload(JAXBContext context) {
/*     */       try {
/* 282 */         Source payloadSrc = getPayload();
/* 283 */         if (payloadSrc == null)
/* 284 */           return null; 
/* 285 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 286 */         return unmarshaller.unmarshal(payloadSrc);
/* 287 */       } catch (JAXBException e) {
/* 288 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getPayload(BindingContext context) {
/*     */       try {
/* 296 */         Source payloadSrc = getPayload();
/* 297 */         if (payloadSrc == null)
/* 298 */           return null; 
/* 299 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 300 */         return unmarshaller.unmarshal(payloadSrc);
/* 301 */       } catch (JAXBException e) {
/* 302 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Message getMessage(MessageHeaders headers, AttachmentSet attachments, WSBinding binding) {
/* 307 */       return JAXBMessage.create(BindingContextFactory.create(this.ctxt), this.o, binding.getSOAPVersion(), headers, attachments);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SourceLogicalMessageImpl extends ImmutableLM {
/*     */     private Source payloadSrc;
/*     */     
/*     */     public SourceLogicalMessageImpl(Source source) {
/* 315 */       this.payloadSrc = source;
/*     */     }
/*     */     
/*     */     public Source getPayload() {
/* 319 */       assert !(this.payloadSrc instanceof DOMSource);
/*     */       try {
/* 321 */         Transformer transformer = XmlUtil.newTransformer();
/* 322 */         DOMResult domResult = new DOMResult();
/* 323 */         transformer.transform(this.payloadSrc, domResult);
/* 324 */         DOMSource dom = new DOMSource(domResult.getNode());
/* 325 */         LogicalMessageImpl.this.lm = new LogicalMessageImpl.DOMLogicalMessageImpl(dom);
/* 326 */         this.payloadSrc = null;
/* 327 */         return dom;
/* 328 */       } catch (TransformerException te) {
/* 329 */         throw new WebServiceException(te);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object getPayload(JAXBContext context) {
/*     */       try {
/* 335 */         Source payloadSrc = getPayload();
/* 336 */         if (payloadSrc == null)
/* 337 */           return null; 
/* 338 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 339 */         return unmarshaller.unmarshal(payloadSrc);
/* 340 */       } catch (JAXBException e) {
/* 341 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getPayload(BindingContext context) {
/*     */       try {
/* 348 */         Source payloadSrc = getPayload();
/* 349 */         if (payloadSrc == null)
/* 350 */           return null; 
/* 351 */         Unmarshaller unmarshaller = context.createUnmarshaller();
/* 352 */         return unmarshaller.unmarshal(payloadSrc);
/* 353 */       } catch (JAXBException e) {
/* 354 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Message getMessage(MessageHeaders headers, AttachmentSet attachments, WSBinding binding) {
/* 360 */       assert this.payloadSrc != null;
/* 361 */       return (Message)new PayloadSourceMessage(headers, this.payloadSrc, attachments, binding.getSOAPVersion());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/handler/LogicalMessageImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */