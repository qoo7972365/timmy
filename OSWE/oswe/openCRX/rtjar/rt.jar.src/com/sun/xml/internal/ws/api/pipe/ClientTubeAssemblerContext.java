/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.ws.addressing.W3CWsaClientTube;
/*     */ import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionWsaClientTube;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.client.ClientPipelineHook;
/*     */ import com.sun.xml.internal.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.internal.ws.api.model.SEIModel;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.internal.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.client.ClientSchemaValidationTube;
/*     */ import com.sun.xml.internal.ws.developer.SchemaValidationFeature;
/*     */ import com.sun.xml.internal.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.internal.ws.handler.ClientLogicalHandlerTube;
/*     */ import com.sun.xml.internal.ws.handler.ClientMessageHandlerTube;
/*     */ import com.sun.xml.internal.ws.handler.ClientSOAPHandlerTube;
/*     */ import com.sun.xml.internal.ws.handler.HandlerTube;
/*     */ import com.sun.xml.internal.ws.protocol.soap.ClientMUTube;
/*     */ import com.sun.xml.internal.ws.transport.DeferredTransportPipe;
/*     */ import com.sun.xml.internal.ws.util.pipe.DumpTube;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientTubeAssemblerContext
/*     */ {
/*     */   @NotNull
/*     */   private final EndpointAddress address;
/*     */   @Nullable
/*     */   private final WSDLPort wsdlModel;
/*     */   @Nullable
/*     */   private final SEIModel seiModel;
/*     */   @Nullable
/*     */   private final Class sei;
/*     */   @NotNull
/*     */   private final WSService rootOwner;
/*     */   @NotNull
/*     */   private final WSBinding binding;
/*     */   @NotNull
/*     */   private final Container container;
/*     */   @NotNull
/*     */   private Codec codec;
/*     */   @Nullable
/*     */   private final WSBindingProvider bindingProvider;
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding) {
/*  84 */     this(address, wsdlModel, rootOwner, binding, Container.NONE);
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
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container) {
/*  96 */     this(address, wsdlModel, rootOwner, binding, container, ((BindingImpl)binding).createCodec());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container, Codec codec) {
/* 107 */     this(address, wsdlModel, rootOwner, binding, container, codec, (SEIModel)null, (Class)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
/* 118 */     this(address, wsdlModel, rootOwner, null, binding, container, codec, seiModel, sei);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSBindingProvider bindingProvider, @NotNull WSBinding binding, @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
/* 129 */     this(address, wsdlModel, (bindingProvider == null) ? null : bindingProvider.getPortInfo().getOwner(), bindingProvider, binding, container, codec, seiModel, sei);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @Nullable WSService rootOwner, @Nullable WSBindingProvider bindingProvider, @NotNull WSBinding binding, @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
/* 138 */     this.address = address;
/* 139 */     this.wsdlModel = wsdlModel;
/* 140 */     this.rootOwner = rootOwner;
/* 141 */     this.bindingProvider = bindingProvider;
/* 142 */     this.binding = binding;
/* 143 */     this.container = container;
/* 144 */     this.codec = codec;
/* 145 */     this.seiModel = seiModel;
/* 146 */     this.sei = sei;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public EndpointAddress getAddress() {
/* 155 */     return this.address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getWsdlModel() {
/* 164 */     return this.wsdlModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSService getService() {
/* 173 */     return this.rootOwner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSPortInfo getPortInfo() {
/* 181 */     return (this.bindingProvider == null) ? null : this.bindingProvider.getPortInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSBindingProvider getBindingProvider() {
/* 190 */     return this.bindingProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSBinding getBinding() {
/* 197 */     return this.binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 207 */     return this.seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Class getSEI() {
/* 217 */     return this.sei;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 226 */     return this.container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createDumpTube(String name, PrintStream out, Tube next) {
/* 233 */     return (Tube)new DumpTube(name, out, next);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createSecurityTube(@NotNull Tube next) {
/* 240 */     ClientPipelineHook hook = (ClientPipelineHook)this.container.getSPI(ClientPipelineHook.class);
/* 241 */     if (hook != null) {
/* 242 */       ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(this.address, this.wsdlModel, this.rootOwner, this.binding, this.container);
/*     */       
/* 244 */       return PipeAdapter.adapt(hook.createSecurityPipe(ctxt, PipeAdapter.adapt(next)));
/*     */     } 
/* 246 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createWsaTube(Tube next) {
/* 253 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding && AddressingVersion.isEnabled(this.binding) && this.wsdlModel != null) {
/* 254 */       if (AddressingVersion.fromBinding(this.binding) == AddressingVersion.MEMBER) {
/* 255 */         return (Tube)new MemberSubmissionWsaClientTube(this.wsdlModel, this.binding, next);
/*     */       }
/* 257 */       return (Tube)new W3CWsaClientTube(this.wsdlModel, this.binding, next);
/*     */     } 
/*     */     
/* 260 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createHandlerTube(Tube next) {
/*     */     ClientSOAPHandlerTube clientSOAPHandlerTube1, clientSOAPHandlerTube2;
/* 267 */     HandlerTube cousinHandlerTube = null;
/*     */     
/* 269 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/*     */       
/* 271 */       ClientMessageHandlerTube clientMessageHandlerTube3 = new ClientMessageHandlerTube(this.seiModel, this.binding, this.wsdlModel, next);
/* 272 */       ClientMessageHandlerTube clientMessageHandlerTube2 = clientMessageHandlerTube3, clientMessageHandlerTube1 = clientMessageHandlerTube2;
/*     */ 
/*     */       
/* 275 */       ClientSOAPHandlerTube clientSOAPHandlerTube = new ClientSOAPHandlerTube(this.binding, (Tube)clientMessageHandlerTube1, (HandlerTube)clientMessageHandlerTube2);
/* 276 */       clientSOAPHandlerTube1 = clientSOAPHandlerTube2 = clientSOAPHandlerTube;
/*     */     } 
/* 278 */     return (Tube)new ClientLogicalHandlerTube(this.binding, this.seiModel, (Tube)clientSOAPHandlerTube1, (HandlerTube)clientSOAPHandlerTube2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createClientMUTube(Tube next) {
/* 286 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 287 */       return (Tube)new ClientMUTube(this.binding, next);
/*     */     }
/* 289 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createValidationTube(Tube next) {
/* 296 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding && this.binding.isFeatureEnabled(SchemaValidationFeature.class) && this.wsdlModel != null) {
/* 297 */       return (Tube)new ClientSchemaValidationTube(this.binding, this.wsdlModel, next);
/*     */     }
/* 299 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createTransportTube() {
/* 306 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     return (Tube)new DeferredTransportPipe(cl, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Codec getCodec() {
/* 322 */     return this.codec;
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
/*     */   public void setCodec(@NotNull Codec codec) {
/* 337 */     this.codec = codec;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/ClientTubeAssemblerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */