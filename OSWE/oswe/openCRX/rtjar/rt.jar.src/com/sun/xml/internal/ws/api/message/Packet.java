/*      */ package com.sun.xml.internal.ws.api.message;
/*      */ 
/*      */ import com.oracle.webservices.internal.api.message.BaseDistributedPropertySet;
/*      */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*      */ import com.oracle.webservices.internal.api.message.ContentType;
/*      */ import com.oracle.webservices.internal.api.message.DistributedPropertySet;
/*      */ import com.oracle.webservices.internal.api.message.MessageContext;
/*      */ import com.oracle.webservices.internal.api.message.PropertySet;
/*      */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*      */ import com.sun.istack.internal.NotNull;
/*      */ import com.sun.istack.internal.Nullable;
/*      */ import com.sun.xml.internal.bind.marshaller.SAX2DOMEx;
/*      */ import com.sun.xml.internal.ws.addressing.WsaPropertyBag;
/*      */ import com.sun.xml.internal.ws.addressing.WsaTubeHelper;
/*      */ import com.sun.xml.internal.ws.api.Component;
/*      */ import com.sun.xml.internal.ws.api.DistributedPropertySet;
/*      */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*      */ import com.sun.xml.internal.ws.api.PropertySet;
/*      */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*      */ import com.sun.xml.internal.ws.api.WSBinding;
/*      */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*      */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*      */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*      */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*      */ import com.sun.xml.internal.ws.api.server.TransportBackChannel;
/*      */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*      */ import com.sun.xml.internal.ws.api.server.WebServiceContextDelegate;
/*      */ import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
/*      */ import com.sun.xml.internal.ws.client.ContentNegotiation;
/*      */ import com.sun.xml.internal.ws.client.HandlerConfiguration;
/*      */ import com.sun.xml.internal.ws.client.Stub;
/*      */ import com.sun.xml.internal.ws.message.RelatesToHeader;
/*      */ import com.sun.xml.internal.ws.message.StringHeader;
/*      */ import com.sun.xml.internal.ws.resources.AddressingMessages;
/*      */ import com.sun.xml.internal.ws.util.DOMUtil;
/*      */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*      */ import com.sun.xml.internal.ws.wsdl.DispatchException;
/*      */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.channels.WritableByteChannel;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.ws.BindingProvider;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import javax.xml.ws.soap.MTOMFeature;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.ContentHandler;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Packet
/*      */   extends BaseDistributedPropertySet
/*      */   implements MessageContext, MessageMetadata
/*      */ {
/*      */   private Message message;
/*      */   
/*      */   public Packet(Message request) {
/*  175 */     this();
/*  176 */     this.message = request;
/*  177 */     if (this.message != null) this.message.setMessageMedadata(this);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet() {
/*  184 */     this.invocationProperties = new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Packet(Packet that) {
/*  191 */     relatePackets(that, true);
/*  192 */     this.invocationProperties = that.invocationProperties;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet copy(boolean copyMessage) {
/*  206 */     Packet copy = new Packet(this);
/*  207 */     if (copyMessage && this.message != null) {
/*  208 */       copy.message = this.message.copy();
/*      */     }
/*  210 */     if (copy.message != null) copy.message.setMessageMedadata(copy); 
/*  211 */     return copy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Message getMessage() {
/*  222 */     if (this.message != null && !(this.message instanceof MessageWrapper)) {
/*  223 */       this.message = (Message)new MessageWrapper(this, this.message);
/*      */     }
/*  225 */     return this.message;
/*      */   }
/*      */   
/*      */   public Message getInternalMessage() {
/*  229 */     return (this.message instanceof MessageWrapper) ? ((MessageWrapper)this.message).delegate : this.message;
/*      */   }
/*      */   
/*      */   public WSBinding getBinding() {
/*  233 */     if (this.endpoint != null) {
/*  234 */       return this.endpoint.getBinding();
/*      */     }
/*  236 */     if (this.proxy != null) {
/*  237 */       return (WSBinding)this.proxy.getBinding();
/*      */     }
/*  239 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMessage(Message message) {
/*  247 */     this.message = message;
/*  248 */     if (message != null) this.message.setMessageMedadata(this); 
/*      */   }
/*      */   
/*  251 */   private WSDLOperationMapping wsdlOperationMapping = null;
/*      */   
/*      */   private QName wsdlOperation;
/*      */   
/*      */   public boolean wasTransportSecure;
/*      */   public static final String INBOUND_TRANSPORT_HEADERS = "com.sun.xml.internal.ws.api.message.packet.inbound.transport.headers";
/*      */   public static final String OUTBOUND_TRANSPORT_HEADERS = "com.sun.xml.internal.ws.api.message.packet.outbound.transport.headers";
/*      */   public static final String HA_INFO = "com.sun.xml.internal.ws.api.message.packet.hainfo";
/*      */   @Property({"com.sun.xml.internal.ws.handler.config"})
/*      */   public HandlerConfiguration handlerConfig;
/*      */   @Property({"com.sun.xml.internal.ws.client.handle"})
/*      */   public BindingProvider proxy;
/*      */   public boolean isAdapterDeliversNonAnonymousResponse;
/*      */   
/*      */   @Property({"javax.xml.ws.wsdl.operation"})
/*      */   @Nullable
/*      */   public final QName getWSDLOperation() {
/*  268 */     if (this.wsdlOperation != null) return this.wsdlOperation; 
/*  269 */     if (this.wsdlOperationMapping == null) this.wsdlOperationMapping = getWSDLOperationMapping(); 
/*  270 */     if (this.wsdlOperationMapping != null) this.wsdlOperation = this.wsdlOperationMapping.getOperationName(); 
/*  271 */     return this.wsdlOperation;
/*      */   }
/*      */   
/*      */   public WSDLOperationMapping getWSDLOperationMapping() {
/*  275 */     if (this.wsdlOperationMapping != null) return this.wsdlOperationMapping; 
/*  276 */     OperationDispatcher opDispatcher = null;
/*  277 */     if (this.endpoint != null) {
/*  278 */       opDispatcher = this.endpoint.getOperationDispatcher();
/*  279 */     } else if (this.proxy != null) {
/*  280 */       opDispatcher = ((Stub)this.proxy).getOperationDispatcher();
/*      */     } 
/*      */     
/*  283 */     if (opDispatcher != null) {
/*      */       try {
/*  285 */         this.wsdlOperationMapping = opDispatcher.getWSDLOperationMapping(this);
/*  286 */       } catch (DispatchException dispatchException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  291 */     return this.wsdlOperationMapping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWSDLOperation(QName wsdlOp) {
/*  302 */     this.wsdlOperation = wsdlOp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean packetTakesPriorityOverRequestContext = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EndpointAddress endpointAddress;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ContentNegotiation contentNegotiation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String acceptableMimeTypes;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WebServiceContextDelegate webServiceContextDelegate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public TransportBackChannel transportBackChannel;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component component;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.internal.ws.api.server.WSEndpoint"})
/*      */   public WSEndpoint endpoint;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"javax.xml.ws.soap.http.soapaction.uri"})
/*      */   public String soapAction;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.internal.ws.server.OneWayOperation"})
/*      */   public Boolean expectReply;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Boolean isOneWay;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean isSynchronousMEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean nonNullAsyncHandlerGiven;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Boolean isRequestReplyMEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<String> handlerScopePropertyNames;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Map<String, Object> invocationProperties;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"javax.xml.ws.service.endpoint.address"})
/*      */   public String getEndPointAddressString() {
/*  397 */     if (this.endpointAddress == null) {
/*  398 */       return null;
/*      */     }
/*  400 */     return this.endpointAddress.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEndPointAddressString(String s) {
/*  405 */     if (s == null) {
/*  406 */       this.endpointAddress = null;
/*      */     } else {
/*  408 */       this.endpointAddress = EndpointAddress.create(s);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.internal.ws.client.ContentNegotiation"})
/*      */   public String getContentNegotiationString() {
/*  422 */     return (this.contentNegotiation != null) ? this.contentNegotiation.toString() : null;
/*      */   }
/*      */   
/*      */   public void setContentNegotiationString(String s) {
/*  426 */     if (s == null) {
/*  427 */       this.contentNegotiation = null;
/*      */     } else {
/*      */       try {
/*  430 */         this.contentNegotiation = ContentNegotiation.valueOf(s);
/*  431 */       } catch (IllegalArgumentException e) {
/*      */         
/*  433 */         this.contentNegotiation = ContentNegotiation.none;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"javax.xml.ws.reference.parameters"})
/*      */   @NotNull
/*      */   public List<Element> getReferenceParameters() {
/*  449 */     Message msg = getMessage();
/*  450 */     List<Element> refParams = new ArrayList<>();
/*  451 */     if (msg == null) {
/*  452 */       return refParams;
/*      */     }
/*  454 */     MessageHeaders hl = msg.getHeaders();
/*  455 */     for (Header h : hl.asList()) {
/*  456 */       String attr = h.getAttribute(AddressingVersion.W3C.nsUri, "IsReferenceParameter");
/*  457 */       if (attr != null && (attr.equals("true") || attr.equals("1"))) {
/*  458 */         Document d = DOMUtil.createDom();
/*  459 */         SAX2DOMEx s2d = new SAX2DOMEx(d);
/*      */         try {
/*  461 */           h.writeTo((ContentHandler)s2d, XmlUtil.DRACONIAN_ERROR_HANDLER);
/*  462 */           refParams.add((Element)d.getLastChild());
/*  463 */         } catch (SAXException e) {
/*  464 */           throw new WebServiceException(e);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  478 */     return refParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Property({"com.sun.xml.internal.ws.api.message.HeaderList"})
/*      */   MessageHeaders getHeaderList() {
/*  487 */     Message msg = getMessage();
/*  488 */     if (msg == null) {
/*  489 */       return null;
/*      */     }
/*  491 */     return msg.getHeaders();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransportBackChannel keepTransportBackChannelOpen() {
/*  546 */     TransportBackChannel r = this.transportBackChannel;
/*  547 */     this.transportBackChannel = null;
/*  548 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean isRequestReplyMEP()
/*      */   {
/*  695 */     return this.isRequestReplyMEP; } public void setRequestReplyMEP(Boolean x) {
/*  696 */     this.isRequestReplyMEP = x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set<String> getHandlerScopePropertyNames(boolean readOnly) {
/*  742 */     Set<String> o = this.handlerScopePropertyNames;
/*  743 */     if (o == null) {
/*  744 */       if (readOnly) {
/*  745 */         return Collections.emptySet();
/*      */       }
/*  747 */       o = new HashSet<>();
/*  748 */       this.handlerScopePropertyNames = o;
/*      */     } 
/*  750 */     return o;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Set<String> getApplicationScopePropertyNames(boolean readOnly) {
/*      */     assert false;
/*  762 */     return new HashSet<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Packet createResponse(Message msg) {
/*  782 */     Packet response = new Packet(this);
/*  783 */     response.setMessage(msg);
/*  784 */     return response;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet createClientResponse(Message msg) {
/*  798 */     Packet response = new Packet(this);
/*  799 */     response.setMessage(msg);
/*  800 */     finishCreateRelateClientResponse(response);
/*  801 */     return response;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet relateClientResponse(Packet response) {
/*  808 */     response.relatePackets(this, true);
/*  809 */     finishCreateRelateClientResponse(response);
/*  810 */     return response;
/*      */   }
/*      */   
/*      */   private void finishCreateRelateClientResponse(Packet response) {
/*  814 */     response.soapAction = null;
/*  815 */     response.setState(State.ClientResponse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet createServerResponse(@Nullable Message responseMessage, @Nullable WSDLPort wsdlPort, @Nullable SEIModel seiModel, @NotNull WSBinding binding) {
/*  835 */     Packet r = createClientResponse(responseMessage);
/*  836 */     return relateServerResponse(r, wsdlPort, seiModel, binding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyPropertiesTo(@Nullable Packet response) {
/*  844 */     relatePackets(response, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void relatePackets(@Nullable Packet packet, boolean isCopy) {
/*      */     Packet request, response;
/*  860 */     if (!isCopy) {
/*  861 */       request = this;
/*  862 */       response = packet;
/*      */ 
/*      */       
/*  865 */       response.soapAction = null;
/*  866 */       response.invocationProperties.putAll(request.invocationProperties);
/*  867 */       if (getState().equals(State.ServerRequest)) {
/*  868 */         response.setState(State.ServerResponse);
/*      */       }
/*      */     } else {
/*  871 */       request = packet;
/*  872 */       response = this;
/*      */ 
/*      */       
/*  875 */       response.soapAction = request.soapAction;
/*  876 */       response.setState(request.getState());
/*      */     } 
/*      */     
/*  879 */     request.copySatelliteInto(response);
/*  880 */     response.isAdapterDeliversNonAnonymousResponse = request.isAdapterDeliversNonAnonymousResponse;
/*  881 */     response.handlerConfig = request.handlerConfig;
/*  882 */     response.handlerScopePropertyNames = request.handlerScopePropertyNames;
/*  883 */     response.contentNegotiation = request.contentNegotiation;
/*  884 */     response.wasTransportSecure = request.wasTransportSecure;
/*  885 */     response.transportBackChannel = request.transportBackChannel;
/*  886 */     response.endpointAddress = request.endpointAddress;
/*  887 */     response.wsdlOperation = request.wsdlOperation;
/*  888 */     response.wsdlOperationMapping = request.wsdlOperationMapping;
/*  889 */     response.acceptableMimeTypes = request.acceptableMimeTypes;
/*  890 */     response.endpoint = request.endpoint;
/*  891 */     response.proxy = request.proxy;
/*  892 */     response.webServiceContextDelegate = request.webServiceContextDelegate;
/*  893 */     response.expectReply = request.expectReply;
/*  894 */     response.component = request.component;
/*  895 */     response.mtomAcceptable = request.mtomAcceptable;
/*  896 */     response.mtomRequest = request.mtomRequest;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet relateServerResponse(@Nullable Packet r, @Nullable WSDLPort wsdlPort, @Nullable SEIModel seiModel, @NotNull WSBinding binding) {
/*  902 */     relatePackets(r, false);
/*  903 */     r.setState(State.ServerResponse);
/*  904 */     AddressingVersion av = binding.getAddressingVersion();
/*      */     
/*  906 */     if (av == null) {
/*  907 */       return r;
/*      */     }
/*      */     
/*  910 */     if (getMessage() == null) {
/*  911 */       return r;
/*      */     }
/*      */ 
/*      */     
/*  915 */     String inputAction = AddressingUtils.getAction(getMessage().getHeaders(), av, binding.getSOAPVersion());
/*  916 */     if (inputAction == null) {
/*  917 */       return r;
/*      */     }
/*      */     
/*  920 */     if (r.getMessage() == null || (wsdlPort != null && getMessage().isOneWay(wsdlPort))) {
/*  921 */       return r;
/*      */     }
/*      */ 
/*      */     
/*  925 */     populateAddressingHeaders(binding, r, wsdlPort, seiModel);
/*  926 */     return r;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet createServerResponse(@Nullable Message responseMessage, @NotNull AddressingVersion addressingVersion, @NotNull SOAPVersion soapVersion, @NotNull String action) {
/*  946 */     Packet responsePacket = createClientResponse(responseMessage);
/*  947 */     responsePacket.setState(State.ServerResponse);
/*      */     
/*  949 */     if (addressingVersion == null) {
/*  950 */       return responsePacket;
/*      */     }
/*      */     
/*  953 */     String inputAction = AddressingUtils.getAction(getMessage().getHeaders(), addressingVersion, soapVersion);
/*  954 */     if (inputAction == null) {
/*  955 */       return responsePacket;
/*      */     }
/*      */     
/*  958 */     populateAddressingHeaders(responsePacket, addressingVersion, soapVersion, action, false);
/*  959 */     return responsePacket;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResponseMessage(@NotNull Packet request, @Nullable Message responseMessage, @NotNull AddressingVersion addressingVersion, @NotNull SOAPVersion soapVersion, @NotNull String action) {
/*  973 */     Packet temp = request.createServerResponse(responseMessage, addressingVersion, soapVersion, action);
/*  974 */     setMessage(temp.getMessage());
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateAddressingHeaders(Packet responsePacket, AddressingVersion av, SOAPVersion sv, String action, boolean mustUnderstand) {
/*  979 */     if (av == null) {
/*      */       return;
/*      */     }
/*  982 */     if (responsePacket.getMessage() == null) {
/*      */       return;
/*      */     }
/*  985 */     MessageHeaders hl = responsePacket.getMessage().getHeaders();
/*      */     
/*  987 */     WsaPropertyBag wpb = (WsaPropertyBag)getSatellite(WsaPropertyBag.class);
/*  988 */     Message msg = getMessage();
/*      */     
/*  990 */     WSEndpointReference replyTo = null;
/*  991 */     Header replyToFromRequestMsg = AddressingUtils.getFirstHeader(msg.getHeaders(), av.replyToTag, true, sv);
/*  992 */     Header replyToFromResponseMsg = hl.get(av.toTag, false);
/*  993 */     boolean replaceToTag = true;
/*      */     try {
/*  995 */       if (replyToFromRequestMsg != null) {
/*  996 */         replyTo = replyToFromRequestMsg.readAsEPR(av);
/*      */       }
/*  998 */       if (replyToFromResponseMsg != null && replyTo == null) {
/*  999 */         replaceToTag = false;
/*      */       }
/* 1001 */     } catch (XMLStreamException e) {
/* 1002 */       throw new WebServiceException(AddressingMessages.REPLY_TO_CANNOT_PARSE(), e);
/*      */     } 
/* 1004 */     if (replyTo == null) {
/* 1005 */       replyTo = AddressingUtils.getReplyTo(msg.getHeaders(), av, sv);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1011 */     if (AddressingUtils.getAction(responsePacket.getMessage().getHeaders(), av, sv) == null)
/*      */     {
/* 1013 */       hl.add((Header)new StringHeader(av.actionTag, action, sv, mustUnderstand));
/*      */     }
/*      */ 
/*      */     
/* 1017 */     if (responsePacket.getMessage().getHeaders().get(av.messageIDTag, false) == null) {
/*      */       
/* 1019 */       String newID = Message.generateMessageID();
/* 1020 */       hl.add((Header)new StringHeader(av.messageIDTag, newID));
/*      */     } 
/*      */ 
/*      */     
/* 1024 */     String mid = null;
/* 1025 */     if (wpb != null) {
/* 1026 */       mid = wpb.getMessageID();
/*      */     }
/* 1028 */     if (mid == null) {
/* 1029 */       mid = AddressingUtils.getMessageID(msg.getHeaders(), av, sv);
/*      */     }
/* 1031 */     if (mid != null) {
/* 1032 */       hl.addOrReplace((Header)new RelatesToHeader(av.relatesToTag, mid));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1037 */     WSEndpointReference refpEPR = null;
/* 1038 */     if (responsePacket.getMessage().isFault()) {
/*      */       
/* 1040 */       if (wpb != null) {
/* 1041 */         refpEPR = wpb.getFaultToFromRequest();
/*      */       }
/* 1043 */       if (refpEPR == null) {
/* 1044 */         refpEPR = AddressingUtils.getFaultTo(msg.getHeaders(), av, sv);
/*      */       }
/*      */       
/* 1047 */       if (refpEPR == null) {
/* 1048 */         refpEPR = replyTo;
/*      */       }
/*      */     } else {
/*      */       
/* 1052 */       refpEPR = replyTo;
/*      */     } 
/* 1054 */     if (replaceToTag && refpEPR != null) {
/* 1055 */       hl.addOrReplace((Header)new StringHeader(av.toTag, refpEPR.getAddress()));
/* 1056 */       refpEPR.addReferenceParametersToList(hl);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void populateAddressingHeaders(WSBinding binding, Packet responsePacket, WSDLPort wsdlPort, SEIModel seiModel) {
/* 1061 */     AddressingVersion addressingVersion = binding.getAddressingVersion();
/*      */     
/* 1063 */     if (addressingVersion == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1067 */     WsaTubeHelper wsaHelper = addressingVersion.getWsaHelper(wsdlPort, seiModel, binding);
/*      */ 
/*      */     
/* 1070 */     String action = responsePacket.getMessage().isFault() ? wsaHelper.getFaultAction(this, responsePacket) : wsaHelper.getOutputAction(this);
/* 1071 */     if (action == null) {
/* 1072 */       LOGGER.info("WSA headers are not added as value for wsa:Action cannot be resolved for this message");
/*      */       return;
/*      */     } 
/* 1075 */     populateAddressingHeaders(responsePacket, addressingVersion, binding.getSOAPVersion(), action, AddressingVersion.isRequired(binding));
/*      */   }
/*      */   
/*      */   public String toShortString() {
/* 1079 */     return super.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*      */     String content;
/* 1085 */     StringBuilder buf = new StringBuilder();
/* 1086 */     buf.append(super.toString());
/*      */     
/*      */     try {
/* 1089 */       Message msg = getMessage();
/* 1090 */       if (msg != null) {
/* 1091 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1092 */         XMLStreamWriter xmlWriter = XMLStreamWriterFactory.create(baos, "UTF-8");
/* 1093 */         msg.copy().writeTo(xmlWriter);
/* 1094 */         xmlWriter.flush();
/* 1095 */         xmlWriter.close();
/* 1096 */         baos.flush();
/* 1097 */         XMLStreamWriterFactory.recycle(xmlWriter);
/*      */         
/* 1099 */         byte[] bytes = baos.toByteArray();
/*      */         
/* 1101 */         content = new String(bytes, "UTF-8");
/*      */       } else {
/* 1103 */         content = "<none>";
/*      */       } 
/* 1105 */     } catch (Throwable t) {
/* 1106 */       throw new WebServiceException(t);
/*      */     } 
/* 1108 */     buf.append(" Content: ").append(content);
/* 1109 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1116 */   private static final BasePropertySet.PropertyMap model = parse(Packet.class);
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 1121 */     return model;
/*      */   }
/*      */   
/*      */   public Map<String, Object> asMapIncludingInvocationProperties() {
/* 1125 */     final Map<String, Object> asMap = asMap();
/* 1126 */     return new AbstractMap<String, Object>()
/*      */       {
/*      */         public Object get(Object key) {
/* 1129 */           Object o = asMap.get(key);
/* 1130 */           if (o != null) {
/* 1131 */             return o;
/*      */           }
/* 1133 */           return Packet.this.invocationProperties.get(key);
/*      */         }
/*      */ 
/*      */         
/*      */         public int size() {
/* 1138 */           return asMap.size() + Packet.this.invocationProperties.size();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean containsKey(Object key) {
/* 1143 */           if (asMap.containsKey(key))
/* 1144 */             return true; 
/* 1145 */           return Packet.this.invocationProperties.containsKey(key);
/*      */         }
/*      */ 
/*      */         
/*      */         public Set<Map.Entry<String, Object>> entrySet() {
/* 1150 */           final Set<Map.Entry<String, Object>> asMapEntries = asMap.entrySet();
/* 1151 */           final Set<Map.Entry<String, Object>> ipEntries = Packet.this.invocationProperties.entrySet();
/*      */           
/* 1153 */           return new AbstractSet<Map.Entry<String, Object>>()
/*      */             {
/*      */               public Iterator<Map.Entry<String, Object>> iterator() {
/* 1156 */                 final Iterator<Map.Entry<String, Object>> asMapIt = asMapEntries.iterator();
/* 1157 */                 final Iterator<Map.Entry<String, Object>> ipIt = ipEntries.iterator();
/*      */                 
/* 1159 */                 return new Iterator<Map.Entry<String, Object>>()
/*      */                   {
/*      */                     public boolean hasNext() {
/* 1162 */                       return (asMapIt.hasNext() || ipIt.hasNext());
/*      */                     }
/*      */ 
/*      */                     
/*      */                     public Map.Entry<String, Object> next() {
/* 1167 */                       if (asMapIt.hasNext())
/* 1168 */                         return asMapIt.next(); 
/* 1169 */                       return ipIt.next();
/*      */                     }
/*      */ 
/*      */                     
/*      */                     public void remove() {
/* 1174 */                       throw new UnsupportedOperationException();
/*      */                     }
/*      */                   };
/*      */               }
/*      */ 
/*      */               
/*      */               public int size() {
/* 1181 */                 return asMap.size() + Packet.this.invocationProperties.size();
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */         
/*      */         public Object put(String key, Object value) {
/* 1188 */           if (Packet.this.supports(key)) {
/* 1189 */             return asMap.put(key, value);
/*      */           }
/* 1191 */           return Packet.this.invocationProperties.put(key, value);
/*      */         }
/*      */ 
/*      */         
/*      */         public void clear() {
/* 1196 */           asMap.clear();
/* 1197 */           Packet.this.invocationProperties.clear();
/*      */         }
/*      */ 
/*      */         
/*      */         public Object remove(Object key) {
/* 1202 */           if (Packet.this.supports(key)) {
/* 1203 */             return asMap.remove(key);
/*      */           }
/* 1205 */           return Packet.this.invocationProperties.remove(key);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/* 1210 */   private static final Logger LOGGER = Logger.getLogger(Packet.class.getName());
/*      */ 
/*      */   
/*      */   public SOAPMessage getSOAPMessage() throws SOAPException {
/* 1214 */     return getAsSOAPMessage();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPMessage getAsSOAPMessage() throws SOAPException {
/* 1220 */     Message msg = getMessage();
/* 1221 */     if (msg == null)
/* 1222 */       return null; 
/* 1223 */     if (msg instanceof MessageWritable)
/* 1224 */       ((MessageWritable)msg).setMTOMConfiguration(this.mtomFeature); 
/* 1225 */     return msg.readAsSOAPMessage(this, getState().isInbound());
/*      */   }
/*      */   
/* 1228 */   public Codec codec = null; private ContentType contentType; private Boolean mtomRequest; private Boolean mtomAcceptable;
/*      */   
/*      */   public Codec getCodec() {
/* 1231 */     if (this.codec != null) {
/* 1232 */       return this.codec;
/*      */     }
/* 1234 */     if (this.endpoint != null) {
/* 1235 */       this.codec = this.endpoint.createCodec();
/*      */     }
/* 1237 */     WSBinding wsb = getBinding();
/* 1238 */     if (wsb != null) {
/* 1239 */       this.codec = wsb.getBindingId().createEncoder(wsb);
/*      */     }
/* 1241 */     return this.codec;
/*      */   }
/*      */   private MTOMFeature mtomFeature; Boolean checkMtomAcceptable; private Boolean fastInfosetAcceptable;
/*      */   
/*      */   public ContentType writeTo(OutputStream out) throws IOException {
/* 1246 */     Message msg = getInternalMessage();
/* 1247 */     if (msg instanceof MessageWritable) {
/* 1248 */       ((MessageWritable)msg).setMTOMConfiguration(this.mtomFeature);
/* 1249 */       return ((MessageWritable)msg).writeTo(out);
/*      */     } 
/* 1251 */     return (ContentType)getCodec().encode(this, out);
/*      */   }
/*      */   
/*      */   public ContentType writeTo(WritableByteChannel buffer) {
/* 1255 */     return (ContentType)getCodec().encode(this, buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getMtomRequest() {
/* 1278 */     return this.mtomRequest;
/*      */   }
/*      */   
/*      */   public void setMtomRequest(Boolean mtomRequest) {
/* 1282 */     this.mtomRequest = mtomRequest;
/*      */   }
/*      */   
/*      */   public Boolean getMtomAcceptable() {
/* 1286 */     return this.mtomAcceptable;
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkMtomAcceptable() {
/* 1291 */     if (this.checkMtomAcceptable == null) {
/* 1292 */       if (this.acceptableMimeTypes == null || this.isFastInfosetDisabled) {
/* 1293 */         this.checkMtomAcceptable = Boolean.valueOf(false);
/*      */       } else {
/* 1295 */         this.checkMtomAcceptable = Boolean.valueOf((this.acceptableMimeTypes.indexOf("application/xop+xml") != -1));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1306 */     this.mtomAcceptable = this.checkMtomAcceptable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Boolean getFastInfosetAcceptable(String fiMimeType) {
/* 1312 */     if (this.fastInfosetAcceptable == null) {
/* 1313 */       if (this.acceptableMimeTypes == null || this.isFastInfosetDisabled) {
/* 1314 */         this.fastInfosetAcceptable = Boolean.valueOf(false);
/*      */       } else {
/* 1316 */         this.fastInfosetAcceptable = Boolean.valueOf((this.acceptableMimeTypes.indexOf(fiMimeType) != -1));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1329 */     return this.fastInfosetAcceptable;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMtomFeature(MTOMFeature mtomFeature) {
/* 1334 */     this.mtomFeature = mtomFeature;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MTOMFeature getMtomFeature() {
/* 1340 */     WSBinding binding = getBinding();
/* 1341 */     if (binding != null) {
/* 1342 */       return (MTOMFeature)binding.getFeature(MTOMFeature.class);
/*      */     }
/* 1344 */     return this.mtomFeature;
/*      */   }
/*      */ 
/*      */   
/*      */   public ContentType getContentType() {
/* 1349 */     if (this.contentType == null) {
/* 1350 */       this.contentType = getInternalContentType();
/*      */     }
/* 1352 */     if (this.contentType == null) {
/* 1353 */       this.contentType = (ContentType)getCodec().getStaticContentType(this);
/*      */     }
/* 1355 */     if (this.contentType == null);
/*      */ 
/*      */     
/* 1358 */     return this.contentType;
/*      */   }
/*      */   
/*      */   public ContentType getInternalContentType() {
/* 1362 */     Message msg = getInternalMessage();
/* 1363 */     if (msg instanceof MessageWritable) {
/* 1364 */       return ((MessageWritable)msg).getContentType();
/*      */     }
/* 1366 */     return this.contentType;
/*      */   }
/*      */   
/*      */   public void setContentType(ContentType contentType) {
/* 1370 */     this.contentType = contentType;
/*      */   }
/*      */   
/*      */   public enum Status {
/* 1374 */     Request, Response, Unknown;
/* 1375 */     public boolean isRequest() { return Request.equals(this); } public boolean isResponse() {
/* 1376 */       return Response.equals(this);
/*      */     } }
/*      */   
/*      */   public enum State {
/* 1380 */     ServerRequest(true), ClientRequest(false), ServerResponse(false), ClientResponse(true); private boolean inbound;
/*      */     
/*      */     State(boolean inbound) {
/* 1383 */       this.inbound = inbound;
/*      */     }
/*      */     public boolean isInbound() {
/* 1386 */       return this.inbound;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1394 */   private State state = State.ServerRequest;
/*      */   private boolean isFastInfosetDisabled;
/*      */   
/*      */   public State getState() {
/* 1398 */     return this.state; } public void setState(State state) {
/* 1399 */     this.state = state;
/*      */   }
/*      */   public boolean shouldUseMtom() {
/* 1402 */     if (getState().isInbound()) {
/* 1403 */       return isMtomContentType();
/*      */     }
/* 1405 */     return shouldUseMtomOutbound();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldUseMtomOutbound() {
/* 1411 */     MTOMFeature myMtomFeature = getMtomFeature();
/* 1412 */     if (myMtomFeature != null && myMtomFeature.isEnabled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1417 */       if (getMtomAcceptable() == null && getMtomRequest() == null) {
/* 1418 */         return true;
/*      */       }
/* 1420 */       if (getMtomAcceptable() != null && getMtomAcceptable().booleanValue() && getState().equals(State.ServerResponse)) {
/* 1421 */         return true;
/*      */       }
/* 1423 */       if (getMtomRequest() != null && getMtomRequest().booleanValue() && getState().equals(State.ServerResponse)) {
/* 1424 */         return true;
/*      */       }
/* 1426 */       if (getMtomRequest() != null && getMtomRequest().booleanValue() && getState().equals(State.ClientRequest)) {
/* 1427 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1431 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isMtomContentType() {
/* 1435 */     return (getInternalContentType() != null && 
/* 1436 */       getInternalContentType().getContentType().contains("application/xop+xml"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSatellite(@NotNull PropertySet satellite) {
/* 1443 */     addSatellite((PropertySet)satellite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSatellite(@NotNull Class keyClass, @NotNull PropertySet satellite) {
/* 1450 */     addSatellite(keyClass, (PropertySet)satellite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copySatelliteInto(@NotNull DistributedPropertySet r) {
/* 1457 */     copySatelliteInto((DistributedPropertySet)r);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSatellite(PropertySet satellite) {
/* 1464 */     removeSatellite((PropertySet)satellite);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFastInfosetDisabled(boolean b) {
/* 1473 */     this.isFastInfosetDisabled = b;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/message/Packet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */