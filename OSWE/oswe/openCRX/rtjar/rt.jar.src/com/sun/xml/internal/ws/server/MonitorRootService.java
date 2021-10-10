/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.org.glassfish.gmbal.AMXMetadata;
/*     */ import com.sun.org.glassfish.gmbal.Description;
/*     */ import com.sun.org.glassfish.gmbal.ManagedAttribute;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObject;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.WSFeatureList;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.util.RuntimeVersion;
/*     */ import java.net.URL;
/*     */ import java.util.Set;
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
/*     */ @ManagedObject
/*     */ @Description("Metro Web Service endpoint")
/*     */ @AMXMetadata(type = "WSEndpoint")
/*     */ public final class MonitorRootService
/*     */   extends MonitorBase
/*     */ {
/*     */   private final WSEndpoint endpoint;
/*     */   
/*     */   MonitorRootService(WSEndpoint endpoint) {
/*  55 */     this.endpoint = endpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Policy associated with Endpoint")
/*     */   public String policy() {
/*  65 */     return (this.endpoint.getPolicyMap() != null) ? this.endpoint
/*  66 */       .getPolicyMap().toString() : null;
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Container")
/*     */   @NotNull
/*     */   public Container container() {
/*  72 */     return this.endpoint.getContainer();
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Port name")
/*     */   @NotNull
/*     */   public QName portName() {
/*  79 */     return this.endpoint.getPortName();
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Service name")
/*     */   @NotNull
/*     */   public QName serviceName() {
/*  85 */     return this.endpoint.getServiceName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Binding SOAP Version")
/*     */   public String soapVersionHttpBindingId() {
/*  95 */     return (this.endpoint.getBinding().getSOAPVersion()).httpBindingId;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Binding Addressing Version")
/*     */   public AddressingVersion addressingVersion() {
/* 101 */     return this.endpoint.getBinding().getAddressingVersion();
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Binding Identifier")
/*     */   @NotNull
/*     */   public BindingID bindingID() {
/* 107 */     return this.endpoint.getBinding().getBindingId();
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Binding features")
/*     */   @NotNull
/*     */   public WSFeatureList features() {
/* 113 */     return this.endpoint.getBinding().getFeatures();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("WSDLPort bound port type")
/*     */   public QName wsdlPortTypeName() {
/* 123 */     return (this.endpoint.getPort() != null) ? this.endpoint
/* 124 */       .getPort().getBinding().getPortTypeName() : null;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Endpoint address")
/*     */   public EndpointAddress wsdlEndpointAddress() {
/* 130 */     return (this.endpoint.getPort() != null) ? this.endpoint
/* 131 */       .getPort().getAddress() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("Documents referenced")
/*     */   public Set<String> serviceDefinitionImports() {
/* 141 */     return (this.endpoint.getServiceDefinition() != null) ? this.endpoint
/* 142 */       .getServiceDefinition().getPrimary().getImports() : null;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("System ID where document is taken from")
/*     */   public URL serviceDefinitionURL() {
/* 148 */     return (this.endpoint.getServiceDefinition() != null) ? this.endpoint
/* 149 */       .getServiceDefinition().getPrimary().getURL() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("SEI model WSDL location")
/*     */   public String seiModelWSDLLocation() {
/* 159 */     return (this.endpoint.getSEIModel() != null) ? this.endpoint
/* 160 */       .getSEIModel().getWSDLLocation() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("JAX-WS runtime version")
/*     */   public String jaxwsRuntimeVersion() {
/* 170 */     return RuntimeVersion.VERSION.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   @Description("If true: show what goes across HTTP transport")
/*     */   public boolean dumpHTTPMessages() {
/* 179 */     return HttpAdapter.dump;
/*     */   }
/*     */   @ManagedAttribute
/*     */   @Description("Show what goes across HTTP transport")
/*     */   public void dumpHTTPMessages(boolean x) {
/* 184 */     HttpAdapter.setDump(x);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/MonitorRootService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */