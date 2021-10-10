/*     */ package com.sun.xml.internal.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.internal.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.WSBinding;
/*     */ import com.sun.xml.internal.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.InstanceResolver;
/*     */ import com.sun.xml.internal.ws.api.server.Invoker;
/*     */ import com.sun.xml.internal.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.api.server.WSWebServiceContext;
/*     */ import com.sun.xml.internal.ws.binding.BindingImpl;
/*     */ import com.sun.xml.internal.ws.server.EndpointFactory;
/*     */ import com.sun.xml.internal.ws.server.ServerRtException;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.internal.ws.transport.http.HttpAdapterList;
/*     */ import com.sun.xml.internal.ws.util.xml.XmlUtil;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.Permission;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.ws.Binding;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.EndpointContext;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.WebServicePermission;
/*     */ import javax.xml.ws.spi.Invoker;
/*     */ import javax.xml.ws.spi.http.HttpContext;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.EntityResolver;
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
/*     */ public class EndpointImpl
/*     */   extends Endpoint
/*     */ {
/*  85 */   private static final WebServicePermission ENDPOINT_PUBLISH_PERMISSION = new WebServicePermission("publishEndpoint");
/*     */   
/*     */   private Object actualEndpoint;
/*     */   
/*     */   private final WSBinding binding;
/*     */   
/*     */   @Nullable
/*     */   private final Object implementor;
/*     */   
/*     */   private List<Source> metadata;
/*     */   
/*     */   private Executor executor;
/*     */   
/*     */   private Map<String, Object> properties;
/*     */   
/*     */   private boolean stopped;
/*     */   
/*     */   @Nullable
/*     */   private EndpointContext endpointContext;
/*     */   
/*     */   @NotNull
/*     */   private final Class<?> implClass;
/*     */   
/*     */   private final Invoker invoker;
/*     */   
/*     */   private Container container;
/*     */   
/*     */   public EndpointImpl(@NotNull BindingID bindingId, @NotNull Object impl, WebServiceFeature... features) {
/* 113 */     this(bindingId, impl, impl.getClass(), 
/* 114 */         InstanceResolver.createSingleton(impl).createInvoker(), features);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointImpl(@NotNull BindingID bindingId, @NotNull Class implClass, Invoker invoker, WebServiceFeature... features) {
/* 120 */     this(bindingId, null, implClass, new InvokerImpl(invoker), features);
/*     */   }
/*     */   
/*     */   private EndpointImpl(@NotNull BindingID bindingId, Object impl, @NotNull Class<?> implClass, Invoker invoker, WebServiceFeature... features) {
/*     */     this.properties = Collections.emptyMap();
/* 125 */     this.binding = (WSBinding)BindingImpl.create(bindingId, features);
/* 126 */     this.implClass = implClass;
/* 127 */     this.invoker = invoker;
/* 128 */     this.implementor = impl;
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
/*     */   public EndpointImpl(WSEndpoint wse, Object serverContext) {
/* 141 */     this(wse, serverContext, (EndpointContext)null);
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
/*     */   public EndpointImpl(WSEndpoint wse, Object serverContext, EndpointContext ctxt) {
/*     */     this.properties = Collections.emptyMap();
/* 154 */     this.endpointContext = ctxt;
/* 155 */     this.actualEndpoint = new HttpEndpoint(null, getAdapter(wse, ""));
/* 156 */     ((HttpEndpoint)this.actualEndpoint).publish(serverContext);
/* 157 */     this.binding = wse.getBinding();
/* 158 */     this.implementor = null;
/* 159 */     this.implClass = null;
/* 160 */     this.invoker = null;
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
/*     */   public EndpointImpl(WSEndpoint wse, String address) {
/* 172 */     this(wse, address, (EndpointContext)null);
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
/*     */   public EndpointImpl(WSEndpoint wse, String address, EndpointContext ctxt) {
/*     */     URL url;
/*     */     this.properties = Collections.emptyMap();
/*     */     try {
/* 188 */       url = new URL(address);
/* 189 */     } catch (MalformedURLException ex) {
/* 190 */       throw new IllegalArgumentException("Cannot create URL for this address " + address);
/*     */     } 
/* 192 */     if (!url.getProtocol().equals("http")) {
/* 193 */       throw new IllegalArgumentException(url.getProtocol() + " protocol based address is not supported");
/*     */     }
/* 195 */     if (!url.getPath().startsWith("/")) {
/* 196 */       throw new IllegalArgumentException("Incorrect WebService address=" + address + ". The address's path should start with /");
/*     */     }
/*     */     
/* 199 */     this.endpointContext = ctxt;
/* 200 */     this.actualEndpoint = new HttpEndpoint(null, getAdapter(wse, url.getPath()));
/* 201 */     ((HttpEndpoint)this.actualEndpoint).publish(address);
/* 202 */     this.binding = wse.getBinding();
/* 203 */     this.implementor = null;
/* 204 */     this.implClass = null;
/* 205 */     this.invoker = null;
/*     */   }
/*     */   
/*     */   public Binding getBinding() {
/* 209 */     return (Binding)this.binding;
/*     */   }
/*     */   
/*     */   public Object getImplementor() {
/* 213 */     return this.implementor;
/*     */   }
/*     */   public void publish(String address) {
/*     */     URL url;
/* 217 */     canPublish();
/*     */     
/*     */     try {
/* 220 */       url = new URL(address);
/* 221 */     } catch (MalformedURLException ex) {
/* 222 */       throw new IllegalArgumentException("Cannot create URL for this address " + address);
/*     */     } 
/* 224 */     if (!url.getProtocol().equals("http")) {
/* 225 */       throw new IllegalArgumentException(url.getProtocol() + " protocol based address is not supported");
/*     */     }
/* 227 */     if (!url.getPath().startsWith("/")) {
/* 228 */       throw new IllegalArgumentException("Incorrect WebService address=" + address + ". The address's path should start with /");
/*     */     }
/*     */     
/* 231 */     createEndpoint(url.getPath());
/* 232 */     ((HttpEndpoint)this.actualEndpoint).publish(address);
/*     */   }
/*     */   
/*     */   public void publish(Object serverContext) {
/* 236 */     canPublish();
/* 237 */     if (!HttpContext.class.isAssignableFrom(serverContext.getClass())) {
/* 238 */       throw new IllegalArgumentException(serverContext.getClass() + " is not a supported context.");
/*     */     }
/* 240 */     createEndpoint(((HttpContext)serverContext).getPath());
/* 241 */     ((HttpEndpoint)this.actualEndpoint).publish(serverContext);
/*     */   }
/*     */   
/*     */   public void publish(HttpContext serverContext) {
/* 245 */     canPublish();
/* 246 */     createEndpoint(serverContext.getPath());
/* 247 */     ((HttpEndpoint)this.actualEndpoint).publish(serverContext);
/*     */   }
/*     */   
/*     */   public void stop() {
/* 251 */     if (isPublished()) {
/* 252 */       ((HttpEndpoint)this.actualEndpoint).stop();
/* 253 */       this.actualEndpoint = null;
/* 254 */       this.stopped = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPublished() {
/* 259 */     return (this.actualEndpoint != null);
/*     */   }
/*     */   
/*     */   public List<Source> getMetadata() {
/* 263 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public void setMetadata(List<Source> metadata) {
/* 267 */     if (isPublished()) {
/* 268 */       throw new IllegalStateException("Cannot set Metadata. Endpoint is already published");
/*     */     }
/* 270 */     this.metadata = metadata;
/*     */   }
/*     */   
/*     */   public Executor getExecutor() {
/* 274 */     return this.executor;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor executor) {
/* 278 */     this.executor = executor;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getProperties() {
/* 282 */     return new HashMap<>(this.properties);
/*     */   }
/*     */   
/*     */   public void setProperties(Map<String, Object> map) {
/* 286 */     this.properties = new HashMap<>(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createEndpoint(String urlPattern) {
/* 295 */     SecurityManager sm = System.getSecurityManager();
/* 296 */     if (sm != null) {
/* 297 */       sm.checkPermission((Permission)ENDPOINT_PUBLISH_PERMISSION);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 302 */       Class.forName("com.sun.net.httpserver.HttpServer");
/* 303 */     } catch (Exception e) {
/* 304 */       throw new UnsupportedOperationException("Couldn't load light weight http server", e);
/*     */     } 
/* 306 */     this.container = getContainer();
/* 307 */     MetadataReader metadataReader = EndpointFactory.getExternalMetadatReader(this.implClass, this.binding);
/* 308 */     WSEndpoint wse = WSEndpoint.create(this.implClass, true, this.invoker, 
/*     */ 
/*     */         
/* 311 */         getProperty(QName.class, "javax.xml.ws.wsdl.service"), 
/* 312 */         getProperty(QName.class, "javax.xml.ws.wsdl.port"), this.container, this.binding, 
/*     */ 
/*     */         
/* 315 */         getPrimaryWsdl(metadataReader), 
/* 316 */         buildDocList(), (EntityResolver)null, false);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 321 */     this.actualEndpoint = new HttpEndpoint(this.executor, getAdapter(wse, urlPattern));
/*     */   }
/*     */   
/*     */   private <T> T getProperty(Class<T> type, String key) {
/* 325 */     Object o = this.properties.get(key);
/* 326 */     if (o == null) return null; 
/* 327 */     if (type.isInstance(o)) {
/* 328 */       return type.cast(o);
/*     */     }
/* 330 */     throw new IllegalArgumentException("Property " + key + " has to be of type " + type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<SDDocumentSource> buildDocList() {
/* 338 */     List<SDDocumentSource> r = new ArrayList<>();
/*     */     
/* 340 */     if (this.metadata != null) {
/* 341 */       for (Source source : this.metadata) {
/*     */         try {
/* 343 */           XMLStreamBufferResult xsbr = (XMLStreamBufferResult)XmlUtil.identityTransform(source, (Result)new XMLStreamBufferResult());
/* 344 */           String systemId = source.getSystemId();
/*     */           
/* 346 */           r.add(SDDocumentSource.create(new URL(systemId), (XMLStreamBuffer)xsbr.getXMLStreamBuffer()));
/* 347 */         } catch (TransformerException te) {
/* 348 */           throw new ServerRtException("server.rt.err", new Object[] { te });
/* 349 */         } catch (IOException te) {
/* 350 */           throw new ServerRtException("server.rt.err", new Object[] { te });
/* 351 */         } catch (SAXException e) {
/* 352 */           throw new ServerRtException("server.rt.err", new Object[] { e });
/* 353 */         } catch (ParserConfigurationException e) {
/* 354 */           throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 359 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SDDocumentSource getPrimaryWsdl(MetadataReader metadataReader) {
/* 367 */     EndpointFactory.verifyImplementorClass(this.implClass, metadataReader);
/* 368 */     String wsdlLocation = EndpointFactory.getWsdlLocation(this.implClass, metadataReader);
/* 369 */     if (wsdlLocation != null) {
/* 370 */       ClassLoader cl = this.implClass.getClassLoader();
/* 371 */       URL url = cl.getResource(wsdlLocation);
/* 372 */       if (url != null) {
/* 373 */         return SDDocumentSource.create(url);
/*     */       }
/* 375 */       throw new ServerRtException("cannot.load.wsdl", new Object[] { wsdlLocation });
/*     */     } 
/* 377 */     return null;
/*     */   }
/*     */   
/*     */   private void canPublish() {
/* 381 */     if (isPublished()) {
/* 382 */       throw new IllegalStateException("Cannot publish this endpoint. Endpoint has been already published.");
/*     */     }
/*     */     
/* 385 */     if (this.stopped) {
/* 386 */       throw new IllegalStateException("Cannot publish this endpoint. Endpoint has been already stopped.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference getEndpointReference(Element... referenceParameters) {
/* 392 */     return (EndpointReference)getEndpointReference(W3CEndpointReference.class, referenceParameters);
/*     */   }
/*     */   
/*     */   public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
/* 396 */     if (!isPublished()) {
/* 397 */       throw new WebServiceException("Endpoint is not published yet");
/*     */     }
/* 399 */     return ((HttpEndpoint)this.actualEndpoint).getEndpointReference(clazz, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndpointContext(EndpointContext ctxt) {
/* 404 */     this.endpointContext = ctxt;
/*     */   }
/*     */   
/*     */   private HttpAdapter getAdapter(WSEndpoint endpoint, String urlPattern) {
/* 408 */     HttpAdapterList adapterList = null;
/* 409 */     if (this.endpointContext != null) {
/* 410 */       if (this.endpointContext instanceof Component) {
/* 411 */         adapterList = (HttpAdapterList)((Component)this.endpointContext).getSPI(HttpAdapterList.class);
/*     */       }
/*     */       
/* 414 */       if (adapterList == null) {
/* 415 */         for (Endpoint e : this.endpointContext.getEndpoints()) {
/* 416 */           if (e.isPublished() && e != this) {
/* 417 */             adapterList = ((HttpEndpoint)((EndpointImpl)e).actualEndpoint).getAdapterOwner();
/* 418 */             assert adapterList != null;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 424 */     if (adapterList == null) {
/* 425 */       adapterList = new ServerAdapterList();
/*     */     }
/* 427 */     return adapterList.createAdapter("", urlPattern, endpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Container getContainer() {
/* 434 */     if (this.endpointContext != null) {
/* 435 */       if (this.endpointContext instanceof Component) {
/* 436 */         Container c = (Container)((Component)this.endpointContext).getSPI(Container.class);
/* 437 */         if (c != null) {
/* 438 */           return c;
/*     */         }
/*     */       } 
/* 441 */       for (Endpoint e : this.endpointContext.getEndpoints()) {
/* 442 */         if (e.isPublished() && e != this) {
/* 443 */           return ((EndpointImpl)e).container;
/*     */         }
/*     */       } 
/*     */     } 
/* 447 */     return new ServerContainer();
/*     */   }
/*     */   
/*     */   private static class InvokerImpl extends Invoker {
/*     */     private Invoker spiInvoker;
/*     */     
/*     */     InvokerImpl(Invoker spiInvoker) {
/* 454 */       this.spiInvoker = spiInvoker;
/*     */     }
/*     */ 
/*     */     
/*     */     public void start(@NotNull WSWebServiceContext wsc, @NotNull WSEndpoint endpoint) {
/*     */       try {
/* 460 */         this.spiInvoker.inject((WebServiceContext)wsc);
/* 461 */       } catch (IllegalAccessException e) {
/* 462 */         throw new WebServiceException(e);
/* 463 */       } catch (InvocationTargetException e) {
/* 464 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object invoke(@NotNull Packet p, @NotNull Method m, @NotNull Object... args) throws InvocationTargetException, IllegalAccessException {
/* 469 */       return this.spiInvoker.invoke(m, args);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/transport/http/server/EndpointImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */