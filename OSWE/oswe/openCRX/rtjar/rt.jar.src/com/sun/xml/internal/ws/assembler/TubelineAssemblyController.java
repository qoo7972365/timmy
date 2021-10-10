/*     */ package com.sun.xml.internal.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.logging.Logger;
/*     */ import com.sun.xml.internal.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.internal.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.internal.ws.runtime.config.TubeFactoryConfig;
/*     */ import com.sun.xml.internal.ws.runtime.config.TubeFactoryList;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TubelineAssemblyController
/*     */ {
/*     */   private final MetroConfigName metroConfigName;
/*     */   
/*     */   TubelineAssemblyController(MetroConfigName metroConfigName) {
/*  50 */     this.metroConfigName = metroConfigName;
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
/*     */   Collection<TubeCreator> getTubeCreators(ClientTubelineAssemblyContext context) {
/*     */     URI endpointUri;
/*  72 */     if (context.getPortInfo() != null) {
/*  73 */       endpointUri = createEndpointComponentUri(context.getPortInfo().getServiceName(), context.getPortInfo().getPortName());
/*     */     } else {
/*  75 */       endpointUri = null;
/*     */     } 
/*     */     
/*  78 */     MetroConfigLoader configLoader = new MetroConfigLoader(context.getContainer(), this.metroConfigName);
/*  79 */     return initializeTubeCreators(configLoader.getClientSideTubeFactories(endpointUri));
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
/*     */   Collection<TubeCreator> getTubeCreators(DefaultServerTubelineAssemblyContext context) {
/*     */     URI endpointUri;
/* 101 */     if (context.getEndpoint() != null) {
/* 102 */       endpointUri = createEndpointComponentUri(context.getEndpoint().getServiceName(), context.getEndpoint().getPortName());
/*     */     } else {
/* 104 */       endpointUri = null;
/*     */     } 
/*     */     
/* 107 */     MetroConfigLoader configLoader = new MetroConfigLoader(context.getEndpoint().getContainer(), this.metroConfigName);
/* 108 */     return initializeTubeCreators(configLoader.getEndpointSideTubeFactories(endpointUri));
/*     */   }
/*     */   
/*     */   private Collection<TubeCreator> initializeTubeCreators(TubeFactoryList tfl) {
/* 112 */     ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*     */     
/* 114 */     LinkedList<TubeCreator> tubeCreators = new LinkedList<>();
/* 115 */     for (TubeFactoryConfig tubeFactoryConfig : tfl.getTubeFactoryConfigs()) {
/* 116 */       tubeCreators.addFirst(new TubeCreator(tubeFactoryConfig, contextClassLoader));
/*     */     }
/* 118 */     return tubeCreators;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URI createEndpointComponentUri(@NotNull QName serviceName, @NotNull QName portName) {
/* 125 */     StringBuilder sb = (new StringBuilder(serviceName.getNamespaceURI())).append("#wsdl11.port(").append(serviceName.getLocalPart()).append('/').append(portName.getLocalPart()).append(')');
/*     */     try {
/* 127 */       return new URI(sb.toString());
/* 128 */     } catch (URISyntaxException ex) {
/* 129 */       Logger.getLogger(TubelineAssemblyController.class).warning(
/* 130 */           TubelineassemblyMessages.MASM_0020_ERROR_CREATING_URI_FROM_GENERATED_STRING(sb.toString()), ex);
/*     */       
/* 132 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/TubelineAssemblyController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */