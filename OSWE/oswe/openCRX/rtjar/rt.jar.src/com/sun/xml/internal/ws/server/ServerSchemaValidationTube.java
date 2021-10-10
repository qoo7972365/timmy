/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*     */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.internal.ws.util.pipe.AbstractSchemaValidationTube;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.Validator;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.ls.LSResourceResolver;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSchemaValidationTube
/*     */   extends AbstractSchemaValidationTube
/*     */ {
/*  57 */   private static final Logger LOGGER = Logger.getLogger(ServerSchemaValidationTube.class.getName());
/*     */   
/*     */   private final Schema schema;
/*     */   
/*     */   private final Validator validator;
/*     */   
/*     */   private final boolean noValidation;
/*     */   private final SEIModel seiModel;
/*     */   private final WSDLPort wsdlPort;
/*     */   
/*     */   public ServerSchemaValidationTube(WSEndpoint endpoint, WSBinding binding, SEIModel seiModel, WSDLPort wsdlPort, Tube next) {
/*  68 */     super(binding, next);
/*  69 */     this.seiModel = seiModel;
/*  70 */     this.wsdlPort = wsdlPort;
/*     */     
/*  72 */     if (endpoint.getServiceDefinition() != null) {
/*  73 */       AbstractSchemaValidationTube.MetadataResolverImpl mdresolver = new AbstractSchemaValidationTube.MetadataResolverImpl(this, (Iterable)endpoint.getServiceDefinition());
/*  74 */       Source[] sources = getSchemaSources((Iterable)endpoint.getServiceDefinition(), mdresolver);
/*  75 */       for (Source source : sources) {
/*  76 */         LOGGER.fine("Constructing service validation schema from = " + source.getSystemId());
/*     */       }
/*     */       
/*  79 */       if (sources.length != 0) {
/*  80 */         this.noValidation = false;
/*  81 */         this.sf.setResourceResolver((LSResourceResolver)mdresolver);
/*     */         try {
/*  83 */           this.schema = this.sf.newSchema(sources);
/*  84 */         } catch (SAXException e) {
/*  85 */           throw new WebServiceException(e);
/*     */         } 
/*  87 */         this.validator = this.schema.newValidator();
/*     */         return;
/*     */       } 
/*     */     } 
/*  91 */     this.noValidation = true;
/*  92 */     this.schema = null;
/*  93 */     this.validator = null;
/*     */   }
/*     */   
/*     */   protected Validator getValidator() {
/*  97 */     return this.validator;
/*     */   }
/*     */   
/*     */   protected boolean isNoValidation() {
/* 101 */     return this.noValidation;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 106 */     if (isNoValidation() || !this.feature.isInbound() || !request.getMessage().hasPayload() || request.getMessage().isFault()) {
/* 107 */       return super.processRequest(request);
/*     */     }
/*     */     try {
/* 110 */       doProcess(request);
/* 111 */     } catch (SAXException se) {
/* 112 */       LOGGER.log(Level.WARNING, "Client Request doesn't pass Service's Schema Validation", se);
/*     */ 
/*     */ 
/*     */       
/* 116 */       SOAPVersion soapVersion = this.binding.getSOAPVersion();
/* 117 */       Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, se, soapVersion.faultCodeClient);
/*     */       
/* 119 */       return doReturnWith(request.createServerResponse(faultMsg, this.wsdlPort, this.seiModel, this.binding));
/*     */     } 
/*     */     
/* 122 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 127 */     if (isNoValidation() || !this.feature.isOutbound() || response.getMessage() == null || !response.getMessage().hasPayload() || response.getMessage().isFault()) {
/* 128 */       return super.processResponse(response);
/*     */     }
/*     */     try {
/* 131 */       doProcess(response);
/* 132 */     } catch (SAXException se) {
/*     */       
/* 134 */       throw new WebServiceException(se);
/*     */     } 
/* 136 */     return super.processResponse(response);
/*     */   }
/*     */   
/*     */   protected ServerSchemaValidationTube(ServerSchemaValidationTube that, TubeCloner cloner) {
/* 140 */     super(that, cloner);
/*     */     
/* 142 */     this.schema = that.schema;
/* 143 */     this.validator = this.schema.newValidator();
/* 144 */     this.noValidation = that.noValidation;
/* 145 */     this.seiModel = that.seiModel;
/* 146 */     this.wsdlPort = that.wsdlPort;
/*     */   }
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 150 */     return (AbstractTubeImpl)new ServerSchemaValidationTube(this, cloner);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/ServerSchemaValidationTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */