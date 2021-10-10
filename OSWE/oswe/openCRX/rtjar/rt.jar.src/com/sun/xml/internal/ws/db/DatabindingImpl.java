/*     */ package com.sun.xml.internal.ws.db;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.internal.api.message.MessageContext;
/*     */ import com.sun.xml.internal.ws.api.databinding.ClientCallBridge;
/*     */ import com.sun.xml.internal.ws.api.databinding.Databinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.internal.ws.api.databinding.EndpointCallBridge;
/*     */ import com.sun.xml.internal.ws.api.databinding.JavaCallInfo;
/*     */ import com.sun.xml.internal.ws.api.databinding.WSDLGenInfo;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.MessageContextFactory;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.model.MEP;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.sei.StubAsyncHandler;
/*     */ import com.sun.xml.internal.ws.client.sei.StubHandler;
/*     */ import com.sun.xml.internal.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.internal.ws.model.JavaMethodImpl;
/*     */ import com.sun.xml.internal.ws.model.RuntimeModeler;
/*     */ import com.sun.xml.internal.ws.server.sei.TieHandler;
/*     */ import com.sun.xml.internal.ws.wsdl.ActionBasedOperationSignature;
/*     */ import com.sun.xml.internal.ws.wsdl.DispatchException;
/*     */ import com.sun.xml.internal.ws.wsdl.OperationDispatcher;
/*     */ import com.sun.xml.internal.ws.wsdl.writer.WSDLGenerator;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DatabindingImpl
/*     */   implements Databinding
/*     */ {
/*     */   AbstractSEIModelImpl seiModel;
/*     */   Map<Method, StubHandler> stubHandlers;
/*  74 */   Map<JavaMethodImpl, TieHandler> wsdlOpMap = new HashMap<>();
/*  75 */   Map<Method, TieHandler> tieHandlers = new HashMap<>();
/*     */   OperationDispatcher operationDispatcher;
/*     */   OperationDispatcher operationDispatcherNoWsdl;
/*     */   boolean clientConfig = false;
/*     */   Codec codec;
/*  80 */   MessageContextFactory packetFactory = null;
/*     */   
/*     */   public DatabindingImpl(DatabindingProviderImpl p, DatabindingConfig config) {
/*  83 */     RuntimeModeler modeler = new RuntimeModeler(config);
/*  84 */     modeler.setClassLoader(config.getClassLoader());
/*  85 */     this.seiModel = modeler.buildRuntimeModel();
/*  86 */     WSDLPort wsdlport = config.getWsdlPort();
/*  87 */     this.packetFactory = new MessageContextFactory(this.seiModel.getWSBinding().getFeatures());
/*  88 */     this.clientConfig = isClientConfig(config);
/*  89 */     if (this.clientConfig) {
/*  90 */       initStubHandlers();
/*     */     }
/*  92 */     this.seiModel.setDatabinding(this);
/*  93 */     if (wsdlport != null) {
/*  94 */       freeze(wsdlport);
/*     */     }
/*  96 */     if (this.operationDispatcher == null) {
/*  97 */       this.operationDispatcherNoWsdl = new OperationDispatcher(null, this.seiModel.getWSBinding(), (SEIModel)this.seiModel);
/*     */     }
/*     */     
/* 100 */     for (JavaMethodImpl jm : this.seiModel.getJavaMethods()) {
/* 101 */       if (!jm.isAsync()) {
/* 102 */         TieHandler th = new TieHandler(jm, this.seiModel.getWSBinding(), this.packetFactory);
/* 103 */         this.wsdlOpMap.put(jm, th);
/* 104 */         this.tieHandlers.put(th.getMethod(), th);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isClientConfig(DatabindingConfig config) {
/* 112 */     if (config.getContractClass() == null) {
/* 113 */       return false;
/*     */     }
/* 115 */     if (!config.getContractClass().isInterface()) {
/* 116 */       return false;
/*     */     }
/* 118 */     return (config.getEndpointClass() == null || config.getEndpointClass().isInterface());
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeze(WSDLPort port) {
/* 123 */     if (this.clientConfig) {
/*     */       return;
/*     */     }
/* 126 */     synchronized (this) {
/* 127 */       if (this.operationDispatcher == null) {
/* 128 */         this.operationDispatcher = (port == null) ? null : new OperationDispatcher(port, this.seiModel.getWSBinding(), (SEIModel)this.seiModel);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public SEIModel getModel() {
/* 134 */     return (SEIModel)this.seiModel;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initStubHandlers() {
/* 139 */     this.stubHandlers = new HashMap<>();
/* 140 */     Map<ActionBasedOperationSignature, JavaMethodImpl> syncs = new HashMap<>();
/*     */ 
/*     */     
/* 143 */     for (JavaMethodImpl m : this.seiModel.getJavaMethods()) {
/* 144 */       if (!(m.getMEP()).isAsync) {
/* 145 */         StubHandler handler = new StubHandler(m, this.packetFactory);
/* 146 */         syncs.put(m.getOperationSignature(), m);
/* 147 */         this.stubHandlers.put(m.getMethod(), handler);
/*     */       } 
/*     */     } 
/* 150 */     for (JavaMethodImpl jm : this.seiModel.getJavaMethods()) {
/* 151 */       JavaMethodImpl sync = syncs.get(jm.getOperationSignature());
/* 152 */       if (jm.getMEP() == MEP.ASYNC_CALLBACK || jm.getMEP() == MEP.ASYNC_POLL) {
/* 153 */         Method m = jm.getMethod();
/* 154 */         StubAsyncHandler handler = new StubAsyncHandler(jm, sync, this.packetFactory);
/* 155 */         this.stubHandlers.put(m, handler);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   JavaMethodImpl resolveJavaMethod(Packet req) throws DispatchException {
/* 161 */     WSDLOperationMapping m = req.getWSDLOperationMapping();
/* 162 */     if (m == null) {
/* 163 */       synchronized (this) {
/*     */ 
/*     */         
/* 166 */         m = (this.operationDispatcher != null) ? this.operationDispatcher.getWSDLOperationMapping(req) : this.operationDispatcherNoWsdl.getWSDLOperationMapping(req);
/*     */       } 
/*     */     }
/* 169 */     return (JavaMethodImpl)m.getJavaMethod();
/*     */   }
/*     */   
/*     */   public JavaCallInfo deserializeRequest(Packet req) {
/* 173 */     JavaCallInfo call = new JavaCallInfo();
/*     */     try {
/* 175 */       JavaMethodImpl wsdlOp = resolveJavaMethod(req);
/* 176 */       TieHandler tie = this.wsdlOpMap.get(wsdlOp);
/* 177 */       call.setMethod(tie.getMethod());
/* 178 */       Object[] args = tie.readRequest(req.getMessage());
/* 179 */       call.setParameters(args);
/* 180 */     } catch (DispatchException e) {
/* 181 */       call.setException((Throwable)e);
/*     */     } 
/* 183 */     return (JavaCallInfo)call;
/*     */   }
/*     */   
/*     */   public JavaCallInfo deserializeResponse(Packet res, JavaCallInfo call) {
/* 187 */     StubHandler stubHandler = this.stubHandlers.get(call.getMethod());
/*     */     try {
/* 189 */       return stubHandler.readResponse(res, call);
/* 190 */     } catch (Throwable e) {
/* 191 */       call.setException(e);
/* 192 */       return call;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public WebServiceFeature[] getFeatures() {
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet serializeRequest(JavaCallInfo call) {
/* 203 */     StubHandler stubHandler = this.stubHandlers.get(call.getMethod());
/* 204 */     Packet p = stubHandler.createRequestPacket(call);
/* 205 */     p.setState(Packet.State.ClientRequest);
/* 206 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet serializeResponse(JavaCallInfo call) {
/* 211 */     Method method = call.getMethod();
/* 212 */     Message message = null;
/* 213 */     if (method != null) {
/* 214 */       TieHandler th = this.tieHandlers.get(method);
/* 215 */       if (th != null) {
/* 216 */         return th.serializeResponse(call);
/*     */       }
/*     */     } 
/* 219 */     if (call.getException() instanceof DispatchException) {
/* 220 */       message = ((DispatchException)call.getException()).fault;
/*     */     }
/* 222 */     Packet p = (Packet)this.packetFactory.createContext(message);
/* 223 */     p.setState(Packet.State.ServerResponse);
/* 224 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientCallBridge getClientBridge(Method method) {
/* 229 */     return (ClientCallBridge)this.stubHandlers.get(method);
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
/*     */   public void generateWSDL(WSDLGenInfo info) {
/* 241 */     WSDLGenerator wsdlGen = new WSDLGenerator(this.seiModel, info.getWsdlResolver(), this.seiModel.getWSBinding(), info.getContainer(), this.seiModel.getEndpointClass(), info.isInlineSchemas(), info.isSecureXmlProcessingDisabled(), info.getExtensions());
/* 242 */     wsdlGen.doGeneration();
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointCallBridge getEndpointBridge(Packet req) throws DispatchException {
/* 247 */     JavaMethodImpl wsdlOp = resolveJavaMethod(req);
/* 248 */     return (EndpointCallBridge)this.wsdlOpMap.get(wsdlOp);
/*     */   }
/*     */   
/*     */   Codec getCodec() {
/* 252 */     if (this.codec == null) {
/* 253 */       this.codec = ((BindingImpl)this.seiModel.getWSBinding()).createCodec();
/*     */     }
/* 255 */     return this.codec;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) throws IOException {
/* 260 */     return getCodec().encode(packet, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(InputStream in, String ct, Packet p) throws IOException {
/* 265 */     getCodec().decode(in, ct, p);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaCallInfo createJavaCallInfo(Method method, Object[] args) {
/* 270 */     return (JavaCallInfo)new JavaCallInfo(method, args);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaCallInfo deserializeResponse(MessageContext message, JavaCallInfo call) {
/* 276 */     return deserializeResponse((Packet)message, call);
/*     */   }
/*     */ 
/*     */   
/*     */   public JavaCallInfo deserializeRequest(MessageContext message) {
/* 281 */     return deserializeRequest((Packet)message);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContextFactory getMessageContextFactory() {
/* 286 */     return this.packetFactory;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/DatabindingImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */