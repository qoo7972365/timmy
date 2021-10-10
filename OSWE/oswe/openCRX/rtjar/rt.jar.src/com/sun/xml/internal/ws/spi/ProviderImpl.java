/*     */ package com.sun.xml.internal.ws.spi;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.BindingID;
/*     */ import com.sun.xml.internal.ws.api.WSService;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;
/*     */ import com.sun.xml.internal.ws.resources.ProviderApiMessages;
/*     */ import com.sun.xml.internal.ws.transport.http.server.EndpointImpl;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.spi.Invoker;
/*     */ import javax.xml.ws.spi.Provider;
/*     */ import javax.xml.ws.spi.ServiceDelegate;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProviderImpl
/*     */   extends Provider
/*     */ {
/*  83 */   private static final ContextClassloaderLocal<JAXBContext> eprjc = new ContextClassloaderLocal<JAXBContext>()
/*     */     {
/*     */       protected JAXBContext initialValue() throws Exception {
/*  86 */         return ProviderImpl.getEPRJaxbContext();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final ProviderImpl INSTANCE = new ProviderImpl();
/*     */ 
/*     */   
/*     */   public Endpoint createEndpoint(String bindingId, Object implementor) {
/*  97 */     return (Endpoint)new EndpointImpl((bindingId != null) ? 
/*  98 */         BindingID.parse(bindingId) : BindingID.parse(implementor.getClass()), implementor, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class serviceClass) {
/* 104 */     return (ServiceDelegate)new WSServiceDelegate(wsdlDocumentLocation, serviceName, serviceClass, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class serviceClass, WebServiceFeature... features) {
/* 109 */     for (WebServiceFeature feature : features) {
/* 110 */       if (!(feature instanceof com.sun.xml.internal.ws.api.ServiceSharedFeatureMarker))
/* 111 */         throw new WebServiceException("Doesn't support any Service specific features"); 
/*     */     } 
/* 113 */     return (ServiceDelegate)new WSServiceDelegate(wsdlDocumentLocation, serviceName, serviceClass, features);
/*     */   }
/*     */   
/*     */   public ServiceDelegate createServiceDelegate(Source wsdlSource, QName serviceName, Class serviceClass) {
/* 117 */     return (ServiceDelegate)new WSServiceDelegate(wsdlSource, serviceName, serviceClass, new WebServiceFeature[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Endpoint createAndPublishEndpoint(String address, Object implementor) {
/* 124 */     EndpointImpl endpointImpl = new EndpointImpl(BindingID.parse(implementor.getClass()), implementor, new WebServiceFeature[0]);
/*     */     
/* 126 */     endpointImpl.publish(address);
/* 127 */     return (Endpoint)endpointImpl;
/*     */   }
/*     */   
/*     */   public Endpoint createEndpoint(String bindingId, Object implementor, WebServiceFeature... features) {
/* 131 */     return (Endpoint)new EndpointImpl((bindingId != null) ? 
/* 132 */         BindingID.parse(bindingId) : BindingID.parse(implementor.getClass()), implementor, features);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Endpoint createAndPublishEndpoint(String address, Object implementor, WebServiceFeature... features) {
/* 138 */     EndpointImpl endpointImpl = new EndpointImpl(BindingID.parse(implementor.getClass()), implementor, features);
/* 139 */     endpointImpl.publish(address);
/* 140 */     return (Endpoint)endpointImpl;
/*     */   }
/*     */   
/*     */   public Endpoint createEndpoint(String bindingId, Class implementorClass, Invoker invoker, WebServiceFeature... features) {
/* 144 */     return (Endpoint)new EndpointImpl((bindingId != null) ? 
/* 145 */         BindingID.parse(bindingId) : BindingID.parse(implementorClass), implementorClass, invoker, features);
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference readEndpointReference(Source eprInfoset) {
/*     */     try {
/* 151 */       Unmarshaller unmarshaller = ((JAXBContext)eprjc.get()).createUnmarshaller();
/* 152 */       return (EndpointReference)unmarshaller.unmarshal(eprInfoset);
/* 153 */     } catch (JAXBException e) {
/* 154 */       throw new WebServiceException("Error creating Marshaller or marshalling.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getPort(EndpointReference endpointReference, Class<T> clazz, WebServiceFeature... webServiceFeatures) {
/*     */     WSService service;
/* 164 */     if (endpointReference == null)
/* 165 */       throw new WebServiceException(ProviderApiMessages.NULL_EPR()); 
/* 166 */     WSEndpointReference wsepr = new WSEndpointReference(endpointReference);
/* 167 */     WSEndpointReference.Metadata metadata = wsepr.getMetaData();
/*     */     
/* 169 */     if (metadata.getWsdlSource() != null) {
/* 170 */       service = (WSService)createServiceDelegate(metadata.getWsdlSource(), metadata.getServiceName(), Service.class);
/*     */     } else {
/* 172 */       throw new WebServiceException("WSDL metadata is missing in EPR");
/* 173 */     }  return (T)service.getPort(wsepr, clazz, webServiceFeatures);
/*     */   }
/*     */   
/*     */   public W3CEndpointReference createW3CEndpointReference(String address, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters) {
/* 177 */     return createW3CEndpointReference(address, null, serviceName, portName, metadata, wsdlDocumentLocation, referenceParameters, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public W3CEndpointReference createW3CEndpointReference(String address, QName interfaceName, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters, List<Element> elements, Map<QName, String> attributes) {
/*     */     // Byte code:
/*     */     //   0: invokestatic getInstance : ()Lcom/sun/xml/internal/ws/api/server/ContainerResolver;
/*     */     //   3: invokevirtual getContainer : ()Lcom/sun/xml/internal/ws/api/server/Container;
/*     */     //   6: astore #10
/*     */     //   8: aload_1
/*     */     //   9: ifnonnull -> 158
/*     */     //   12: aload_3
/*     */     //   13: ifnull -> 21
/*     */     //   16: aload #4
/*     */     //   18: ifnonnull -> 32
/*     */     //   21: new java/lang/IllegalStateException
/*     */     //   24: dup
/*     */     //   25: invokestatic NULL_ADDRESS_SERVICE_ENDPOINT : ()Ljava/lang/String;
/*     */     //   28: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   31: athrow
/*     */     //   32: aload #10
/*     */     //   34: ldc com/sun/xml/internal/ws/api/server/Module
/*     */     //   36: invokevirtual getSPI : (Ljava/lang/Class;)Ljava/lang/Object;
/*     */     //   39: checkcast com/sun/xml/internal/ws/api/server/Module
/*     */     //   42: astore #11
/*     */     //   44: aload #11
/*     */     //   46: ifnull -> 143
/*     */     //   49: aload #11
/*     */     //   51: invokevirtual getBoundEndpoints : ()Ljava/util/List;
/*     */     //   54: astore #12
/*     */     //   56: aload #12
/*     */     //   58: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   63: astore #13
/*     */     //   65: aload #13
/*     */     //   67: invokeinterface hasNext : ()Z
/*     */     //   72: ifeq -> 143
/*     */     //   75: aload #13
/*     */     //   77: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   82: checkcast com/sun/xml/internal/ws/api/server/BoundEndpoint
/*     */     //   85: astore #14
/*     */     //   87: aload #14
/*     */     //   89: invokeinterface getEndpoint : ()Lcom/sun/xml/internal/ws/api/server/WSEndpoint;
/*     */     //   94: astore #15
/*     */     //   96: aload #15
/*     */     //   98: invokevirtual getServiceName : ()Ljavax/xml/namespace/QName;
/*     */     //   101: aload_3
/*     */     //   102: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   105: ifeq -> 140
/*     */     //   108: aload #15
/*     */     //   110: invokevirtual getPortName : ()Ljavax/xml/namespace/QName;
/*     */     //   113: aload #4
/*     */     //   115: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   118: ifeq -> 140
/*     */     //   121: aload #14
/*     */     //   123: invokeinterface getAddress : ()Ljava/net/URI;
/*     */     //   128: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   131: astore_1
/*     */     //   132: goto -> 143
/*     */     //   135: astore #16
/*     */     //   137: goto -> 143
/*     */     //   140: goto -> 65
/*     */     //   143: aload_1
/*     */     //   144: ifnonnull -> 158
/*     */     //   147: new java/lang/IllegalStateException
/*     */     //   150: dup
/*     */     //   151: invokestatic NULL_ADDRESS : ()Ljava/lang/String;
/*     */     //   154: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   157: athrow
/*     */     //   158: aload_3
/*     */     //   159: ifnonnull -> 178
/*     */     //   162: aload #4
/*     */     //   164: ifnull -> 178
/*     */     //   167: new java/lang/IllegalStateException
/*     */     //   170: dup
/*     */     //   171: invokestatic NULL_SERVICE : ()Ljava/lang/String;
/*     */     //   174: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   177: athrow
/*     */     //   178: aconst_null
/*     */     //   179: astore #11
/*     */     //   181: aload #6
/*     */     //   183: ifnull -> 352
/*     */     //   186: invokestatic createDefaultCatalogResolver : ()Lorg/xml/sax/EntityResolver;
/*     */     //   189: astore #12
/*     */     //   191: new java/net/URL
/*     */     //   194: dup
/*     */     //   195: aload #6
/*     */     //   197: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   200: astore #13
/*     */     //   202: aload #13
/*     */     //   204: new javax/xml/transform/stream/StreamSource
/*     */     //   207: dup
/*     */     //   208: aload #13
/*     */     //   210: invokevirtual toExternalForm : ()Ljava/lang/String;
/*     */     //   213: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   216: aload #12
/*     */     //   218: iconst_1
/*     */     //   219: aload #10
/*     */     //   221: ldc com/sun/xml/internal/ws/api/wsdl/parser/WSDLParserExtension
/*     */     //   223: invokestatic find : (Ljava/lang/Class;)Lcom/sun/xml/internal/ws/util/ServiceFinder;
/*     */     //   226: invokevirtual toArray : ()[Ljava/lang/Object;
/*     */     //   229: checkcast [Lcom/sun/xml/internal/ws/api/wsdl/parser/WSDLParserExtension;
/*     */     //   232: invokestatic parse : (Ljava/net/URL;Ljavax/xml/transform/Source;Lorg/xml/sax/EntityResolver;ZLcom/sun/xml/internal/ws/api/server/Container;[Lcom/sun/xml/internal/ws/api/wsdl/parser/WSDLParserExtension;)Lcom/sun/xml/internal/ws/api/model/wsdl/WSDLModel;
/*     */     //   235: astore #14
/*     */     //   237: aload_3
/*     */     //   238: ifnull -> 316
/*     */     //   241: aload #14
/*     */     //   243: aload_3
/*     */     //   244: invokeinterface getService : (Ljavax/xml/namespace/QName;)Lcom/sun/xml/internal/ws/api/model/wsdl/WSDLService;
/*     */     //   249: astore #15
/*     */     //   251: aload #15
/*     */     //   253: ifnonnull -> 270
/*     */     //   256: new java/lang/IllegalStateException
/*     */     //   259: dup
/*     */     //   260: aload_3
/*     */     //   261: aload #6
/*     */     //   263: invokestatic NOTFOUND_SERVICE_IN_WSDL : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   266: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   269: athrow
/*     */     //   270: aload #4
/*     */     //   272: ifnull -> 307
/*     */     //   275: aload #15
/*     */     //   277: aload #4
/*     */     //   279: invokeinterface get : (Ljavax/xml/namespace/QName;)Lcom/sun/xml/internal/ws/api/model/wsdl/WSDLPort;
/*     */     //   284: astore #16
/*     */     //   286: aload #16
/*     */     //   288: ifnonnull -> 307
/*     */     //   291: new java/lang/IllegalStateException
/*     */     //   294: dup
/*     */     //   295: aload #4
/*     */     //   297: aload_3
/*     */     //   298: aload #6
/*     */     //   300: invokestatic NOTFOUND_PORT_IN_WSDL : (Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   303: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   306: athrow
/*     */     //   307: aload_3
/*     */     //   308: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   311: astore #11
/*     */     //   313: goto -> 332
/*     */     //   316: aload #14
/*     */     //   318: invokeinterface getFirstServiceName : ()Ljavax/xml/namespace/QName;
/*     */     //   323: astore #15
/*     */     //   325: aload #15
/*     */     //   327: invokevirtual getNamespaceURI : ()Ljava/lang/String;
/*     */     //   330: astore #11
/*     */     //   332: goto -> 352
/*     */     //   335: astore #12
/*     */     //   337: new java/lang/IllegalStateException
/*     */     //   340: dup
/*     */     //   341: aload #6
/*     */     //   343: invokestatic ERROR_WSDL : (Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   346: aload #12
/*     */     //   348: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   351: athrow
/*     */     //   352: aload #5
/*     */     //   354: ifnull -> 370
/*     */     //   357: aload #5
/*     */     //   359: invokeinterface size : ()I
/*     */     //   364: ifne -> 370
/*     */     //   367: aconst_null
/*     */     //   368: astore #5
/*     */     //   370: new com/sun/xml/internal/ws/api/addressing/WSEndpointReference
/*     */     //   373: dup
/*     */     //   374: ldc javax/xml/ws/wsaddressing/W3CEndpointReference
/*     */     //   376: invokestatic fromSpecClass : (Ljava/lang/Class;)Lcom/sun/xml/internal/ws/api/addressing/AddressingVersion;
/*     */     //   379: aload_1
/*     */     //   380: aload_3
/*     */     //   381: aload #4
/*     */     //   383: aload_2
/*     */     //   384: aload #5
/*     */     //   386: aload #6
/*     */     //   388: aload #11
/*     */     //   390: aload #7
/*     */     //   392: aload #8
/*     */     //   394: aload #9
/*     */     //   396: invokespecial <init> : (Lcom/sun/xml/internal/ws/api/addressing/AddressingVersion;Ljava/lang/String;Ljavax/xml/namespace/QName;Ljavax/xml/namespace/QName;Ljavax/xml/namespace/QName;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/Map;)V
/*     */     //   399: ldc javax/xml/ws/wsaddressing/W3CEndpointReference
/*     */     //   401: invokevirtual toSpec : (Ljava/lang/Class;)Ljavax/xml/ws/EndpointReference;
/*     */     //   404: checkcast javax/xml/ws/wsaddressing/W3CEndpointReference
/*     */     //   407: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #183	-> 0
/*     */     //   #184	-> 8
/*     */     //   #185	-> 12
/*     */     //   #186	-> 21
/*     */     //   #189	-> 32
/*     */     //   #190	-> 44
/*     */     //   #191	-> 49
/*     */     //   #192	-> 56
/*     */     //   #193	-> 87
/*     */     //   #194	-> 96
/*     */     //   #196	-> 121
/*     */     //   #200	-> 132
/*     */     //   #197	-> 135
/*     */     //   #201	-> 137
/*     */     //   #203	-> 140
/*     */     //   #206	-> 143
/*     */     //   #207	-> 147
/*     */     //   #210	-> 158
/*     */     //   #211	-> 167
/*     */     //   #214	-> 178
/*     */     //   #215	-> 181
/*     */     //   #217	-> 186
/*     */     //   #219	-> 191
/*     */     //   #220	-> 202
/*     */     //   #221	-> 223
/*     */     //   #220	-> 232
/*     */     //   #222	-> 237
/*     */     //   #223	-> 241
/*     */     //   #224	-> 251
/*     */     //   #225	-> 256
/*     */     //   #227	-> 270
/*     */     //   #228	-> 275
/*     */     //   #229	-> 286
/*     */     //   #230	-> 291
/*     */     //   #233	-> 307
/*     */     //   #234	-> 313
/*     */     //   #235	-> 316
/*     */     //   #236	-> 325
/*     */     //   #240	-> 332
/*     */     //   #238	-> 335
/*     */     //   #239	-> 337
/*     */     //   #243	-> 352
/*     */     //   #244	-> 367
/*     */     //   #246	-> 370
/*     */     //   #247	-> 376
/*     */     //   #248	-> 401
/*     */     //   #246	-> 407
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   96	44	15	wse	Lcom/sun/xml/internal/ws/api/server/WSEndpoint;
/*     */     //   87	53	14	be	Lcom/sun/xml/internal/ws/api/server/BoundEndpoint;
/*     */     //   56	87	12	beList	Ljava/util/List;
/*     */     //   44	114	11	module	Lcom/sun/xml/internal/ws/api/server/Module;
/*     */     //   286	21	16	wsdlPort	Lcom/sun/xml/internal/ws/api/model/wsdl/WSDLPort;
/*     */     //   251	62	15	wsdlService	Lcom/sun/xml/internal/ws/api/model/wsdl/WSDLService;
/*     */     //   325	7	15	firstService	Ljavax/xml/namespace/QName;
/*     */     //   191	141	12	er	Lorg/xml/sax/EntityResolver;
/*     */     //   202	130	13	wsdlLoc	Ljava/net/URL;
/*     */     //   237	95	14	wsdlDoc	Lcom/sun/xml/internal/ws/api/model/wsdl/WSDLModel;
/*     */     //   337	15	12	e	Ljava/lang/Exception;
/*     */     //   0	408	0	this	Lcom/sun/xml/internal/ws/spi/ProviderImpl;
/*     */     //   0	408	1	address	Ljava/lang/String;
/*     */     //   0	408	2	interfaceName	Ljavax/xml/namespace/QName;
/*     */     //   0	408	3	serviceName	Ljavax/xml/namespace/QName;
/*     */     //   0	408	4	portName	Ljavax/xml/namespace/QName;
/*     */     //   0	408	5	metadata	Ljava/util/List;
/*     */     //   0	408	6	wsdlDocumentLocation	Ljava/lang/String;
/*     */     //   0	408	7	referenceParameters	Ljava/util/List;
/*     */     //   0	408	8	elements	Ljava/util/List;
/*     */     //   0	408	9	attributes	Ljava/util/Map;
/*     */     //   8	400	10	container	Lcom/sun/xml/internal/ws/api/server/Container;
/*     */     //   181	227	11	wsdlTargetNamespace	Ljava/lang/String;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   56	87	12	beList	Ljava/util/List<Lcom/sun/xml/internal/ws/api/server/BoundEndpoint;>;
/*     */     //   0	408	5	metadata	Ljava/util/List<Lorg/w3c/dom/Element;>;
/*     */     //   0	408	7	referenceParameters	Ljava/util/List<Lorg/w3c/dom/Element;>;
/*     */     //   0	408	8	elements	Ljava/util/List<Lorg/w3c/dom/Element;>;
/*     */     //   0	408	9	attributes	Ljava/util/Map<Ljavax/xml/namespace/QName;Ljava/lang/String;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   121	132	135	javax/xml/ws/WebServiceException
/*     */     //   186	332	335	java/lang/Exception
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static JAXBContext getEPRJaxbContext() {
/* 256 */     return AccessController.<JAXBContext>doPrivileged(new PrivilegedAction<JAXBContext>() {
/*     */           public JAXBContext run() {
/*     */             try {
/* 259 */               return JAXBContext.newInstance(new Class[] { MemberSubmissionEndpointReference.class, W3CEndpointReference.class });
/* 260 */             } catch (JAXBException e) {
/* 261 */               throw new WebServiceException("Error creating JAXBContext for W3CEndpointReference. ", e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/ProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */