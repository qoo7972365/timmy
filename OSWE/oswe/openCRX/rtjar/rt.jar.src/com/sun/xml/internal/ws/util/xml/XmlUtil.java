/*     */ package com.sun.xml.internal.ws.util.xml;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogManager;
/*     */ import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;
/*     */ import com.sun.xml.internal.ws.server.ServerRtException;
/*     */ import com.sun.xml.internal.ws.util.ByteArrayBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*     */ import javax.xml.transform.sax.TransformerHandler;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import javax.xml.xpath.XPathFactoryConfigurationException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlUtil
/*     */ {
/*     */   private static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";
/*     */   private static final String LEXICAL_HANDLER_PROPERTY = "http://xml.org/sax/properties/lexical-handler";
/*     */   private static final String DISALLOW_DOCTYPE_DECL = "http://apache.org/xml/features/disallow-doctype-decl";
/*     */   private static final String EXTERNAL_GE = "http://xml.org/sax/features/external-general-entities";
/*     */   private static final String EXTERNAL_PE = "http://xml.org/sax/features/external-parameter-entities";
/*     */   private static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/*  95 */   private static final Logger LOGGER = Logger.getLogger(XmlUtil.class.getName());
/*     */   
/*     */   private static final String DISABLE_XML_SECURITY = "com.sun.xml.internal.ws.disableXmlSecurity";
/*     */   
/*  99 */   private static boolean XML_SECURITY_DISABLED = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run()
/*     */         {
/* 103 */           return Boolean.valueOf(Boolean.getBoolean("com.sun.xml.internal.ws.disableXmlSecurity"));
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*     */   
/*     */   public static String getPrefix(String s) {
/* 109 */     int i = s.indexOf(':');
/* 110 */     if (i == -1)
/* 111 */       return null; 
/* 112 */     return s.substring(0, i);
/*     */   }
/*     */   
/*     */   public static String getLocalPart(String s) {
/* 116 */     int i = s.indexOf(':');
/* 117 */     if (i == -1)
/* 118 */       return s; 
/* 119 */     return s.substring(i + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeOrNull(Element e, String name) {
/* 125 */     Attr a = e.getAttributeNode(name);
/* 126 */     if (a == null)
/* 127 */       return null; 
/* 128 */     return a.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeNSOrNull(Element e, String name, String nsURI) {
/* 135 */     Attr a = e.getAttributeNodeNS(nsURI, name);
/* 136 */     if (a == null)
/* 137 */       return null; 
/* 138 */     return a.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAttributeNSOrNull(Element e, QName name) {
/* 144 */     Attr a = e.getAttributeNodeNS(name.getNamespaceURI(), name.getLocalPart());
/* 145 */     if (a == null)
/* 146 */       return null; 
/* 147 */     return a.getValue();
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
/*     */   public static Iterator getAllChildren(Element element) {
/* 179 */     return new NodeListIterator(element.getChildNodes());
/*     */   }
/*     */   
/*     */   public static Iterator getAllAttributes(Element element) {
/* 183 */     return new NamedNodeMapIterator(element.getAttributes());
/*     */   }
/*     */   
/*     */   public static List<String> parseTokenList(String tokenList) {
/* 187 */     List<String> result = new ArrayList<>();
/* 188 */     StringTokenizer tokenizer = new StringTokenizer(tokenList, " ");
/* 189 */     while (tokenizer.hasMoreTokens()) {
/* 190 */       result.add(tokenizer.nextToken());
/*     */     }
/* 192 */     return result;
/*     */   }
/*     */   
/*     */   public static String getTextForNode(Node node) {
/* 196 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 198 */     NodeList children = node.getChildNodes();
/* 199 */     if (children.getLength() == 0) {
/* 200 */       return null;
/*     */     }
/* 202 */     for (int i = 0; i < children.getLength(); i++) {
/* 203 */       Node n = children.item(i);
/*     */       
/* 205 */       if (n instanceof org.w3c.dom.Text) {
/* 206 */         sb.append(n.getNodeValue());
/* 207 */       } else if (n instanceof org.w3c.dom.EntityReference) {
/* 208 */         String s = getTextForNode(n);
/* 209 */         if (s == null) {
/* 210 */           return null;
/*     */         }
/* 212 */         sb.append(s);
/*     */       } else {
/* 214 */         return null;
/*     */       } 
/*     */     } 
/* 217 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static InputStream getUTF8Stream(String s) {
/*     */     try {
/* 222 */       ByteArrayBuffer bab = new ByteArrayBuffer();
/* 223 */       Writer w = new OutputStreamWriter((OutputStream)bab, "utf-8");
/* 224 */       w.write(s);
/* 225 */       w.close();
/* 226 */       return bab.newInputStream();
/* 227 */     } catch (IOException e) {
/* 228 */       throw new RuntimeException("should not happen");
/*     */     } 
/*     */   }
/*     */   
/* 232 */   static final ContextClassloaderLocal<TransformerFactory> transformerFactory = new ContextClassloaderLocal<TransformerFactory>()
/*     */     {
/*     */       protected TransformerFactory initialValue() throws Exception {
/* 235 */         return TransformerFactory.newInstance();
/*     */       }
/*     */     };
/*     */   
/* 239 */   static final ContextClassloaderLocal<SAXParserFactory> saxParserFactory = new ContextClassloaderLocal<SAXParserFactory>()
/*     */     {
/*     */       protected SAXParserFactory initialValue() throws Exception {
/* 242 */         SAXParserFactory factory = SAXParserFactory.newInstance();
/* 243 */         factory.setNamespaceAware(true);
/* 244 */         return factory;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transformer newTransformer() {
/*     */     try {
/* 253 */       return ((TransformerFactory)transformerFactory.get()).newTransformer();
/* 254 */     } catch (TransformerConfigurationException tex) {
/* 255 */       throw new IllegalStateException("Unable to create a JAXP transformer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Result> T identityTransform(Source src, T result) throws TransformerException, SAXException, ParserConfigurationException, IOException {
/* 264 */     if (src instanceof StreamSource) {
/*     */ 
/*     */       
/* 267 */       StreamSource ssrc = (StreamSource)src;
/* 268 */       TransformerHandler th = ((SAXTransformerFactory)transformerFactory.get()).newTransformerHandler();
/* 269 */       th.setResult((Result)result);
/* 270 */       XMLReader reader = ((SAXParserFactory)saxParserFactory.get()).newSAXParser().getXMLReader();
/* 271 */       reader.setContentHandler(th);
/* 272 */       reader.setProperty("http://xml.org/sax/properties/lexical-handler", th);
/* 273 */       reader.parse(toInputSource(ssrc));
/*     */     } else {
/* 275 */       newTransformer().transform(src, (Result)result);
/*     */     } 
/* 277 */     return result;
/*     */   }
/*     */   
/*     */   private static InputSource toInputSource(StreamSource src) {
/* 281 */     InputSource is = new InputSource();
/* 282 */     is.setByteStream(src.getInputStream());
/* 283 */     is.setCharacterStream(src.getReader());
/* 284 */     is.setPublicId(src.getPublicId());
/* 285 */     is.setSystemId(src.getSystemId());
/* 286 */     return is;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityResolver createEntityResolver(@Nullable URL catalogUrl) {
/* 294 */     CatalogManager manager = new CatalogManager();
/* 295 */     manager.setIgnoreMissingProperties(true);
/*     */     
/* 297 */     manager.setUseStaticCatalog(false);
/* 298 */     Catalog catalog = manager.getCatalog();
/*     */     try {
/* 300 */       if (catalogUrl != null) {
/* 301 */         catalog.parseCatalog(catalogUrl);
/*     */       }
/* 303 */     } catch (IOException e) {
/* 304 */       throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */     } 
/* 306 */     return (EntityResolver)workaroundCatalogResolver(catalog);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityResolver createDefaultCatalogResolver() {
/* 315 */     CatalogManager manager = new CatalogManager();
/* 316 */     manager.setIgnoreMissingProperties(true);
/*     */     
/* 318 */     manager.setUseStaticCatalog(false);
/*     */     
/* 320 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*     */     
/* 322 */     Catalog catalog = manager.getCatalog(); try {
/*     */       Enumeration<URL> catalogEnum;
/* 324 */       if (cl == null) {
/* 325 */         catalogEnum = ClassLoader.getSystemResources("META-INF/jax-ws-catalog.xml");
/*     */       } else {
/* 327 */         catalogEnum = cl.getResources("META-INF/jax-ws-catalog.xml");
/*     */       } 
/*     */       
/* 330 */       while (catalogEnum.hasMoreElements()) {
/* 331 */         URL url = catalogEnum.nextElement();
/* 332 */         catalog.parseCatalog(url);
/*     */       } 
/* 334 */     } catch (IOException e) {
/* 335 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 338 */     return (EntityResolver)workaroundCatalogResolver(catalog);
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
/*     */   private static CatalogResolver workaroundCatalogResolver(final Catalog catalog) {
/* 350 */     CatalogManager manager = new CatalogManager()
/*     */       {
/*     */         public Catalog getCatalog() {
/* 353 */           return catalog;
/*     */         }
/*     */       };
/* 356 */     manager.setIgnoreMissingProperties(true);
/*     */     
/* 358 */     manager.setUseStaticCatalog(false);
/*     */     
/* 360 */     return new CatalogResolver(manager);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 366 */   public static final ErrorHandler DRACONIAN_ERROR_HANDLER = new ErrorHandler()
/*     */     {
/*     */       public void warning(SAXParseException exception) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void error(SAXParseException exception) throws SAXException {
/* 373 */         throw exception;
/*     */       }
/*     */ 
/*     */       
/*     */       public void fatalError(SAXParseException exception) throws SAXException {
/* 378 */         throw exception;
/*     */       }
/*     */     };
/*     */   
/*     */   public static DocumentBuilderFactory newDocumentBuilderFactory() {
/* 383 */     return newDocumentBuilderFactory(false);
/*     */   }
/*     */   
/*     */   public static DocumentBuilderFactory newDocumentBuilderFactory(boolean disableSecurity) {
/* 387 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 388 */     String featureToSet = "http://javax.xml.XMLConstants/feature/secure-processing";
/*     */     try {
/* 390 */       boolean securityOn = !isXMLSecurityDisabled(disableSecurity);
/* 391 */       factory.setFeature(featureToSet, securityOn);
/* 392 */       factory.setNamespaceAware(true);
/* 393 */       if (securityOn) {
/* 394 */         factory.setExpandEntityReferences(false);
/* 395 */         featureToSet = "http://apache.org/xml/features/disallow-doctype-decl";
/* 396 */         factory.setFeature(featureToSet, true);
/* 397 */         featureToSet = "http://xml.org/sax/features/external-general-entities";
/* 398 */         factory.setFeature(featureToSet, false);
/* 399 */         featureToSet = "http://xml.org/sax/features/external-parameter-entities";
/* 400 */         factory.setFeature(featureToSet, false);
/* 401 */         featureToSet = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/* 402 */         factory.setFeature(featureToSet, false);
/*     */       } 
/* 404 */     } catch (ParserConfigurationException e) {
/* 405 */       LOGGER.log(Level.WARNING, "Factory [{0}] doesn't support " + featureToSet + " feature!", new Object[] { factory.getClass().getName() });
/*     */     } 
/* 407 */     return factory;
/*     */   }
/*     */   
/*     */   public static TransformerFactory newTransformerFactory(boolean secureXmlProcessingEnabled) {
/* 411 */     TransformerFactory factory = TransformerFactory.newInstance();
/*     */     try {
/* 413 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", isXMLSecurityDisabled(secureXmlProcessingEnabled));
/* 414 */     } catch (TransformerConfigurationException e) {
/* 415 */       LOGGER.log(Level.WARNING, "Factory [{0}] doesn't support secure xml processing!", new Object[] { factory.getClass().getName() });
/*     */     } 
/* 417 */     return factory;
/*     */   }
/*     */   
/*     */   public static TransformerFactory newTransformerFactory() {
/* 421 */     return newTransformerFactory(true);
/*     */   }
/*     */   
/*     */   public static SAXParserFactory newSAXParserFactory(boolean disableSecurity) {
/* 425 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/* 426 */     String featureToSet = "http://javax.xml.XMLConstants/feature/secure-processing";
/*     */     try {
/* 428 */       boolean securityOn = !isXMLSecurityDisabled(disableSecurity);
/* 429 */       factory.setFeature(featureToSet, securityOn);
/* 430 */       factory.setNamespaceAware(true);
/* 431 */       if (securityOn) {
/* 432 */         featureToSet = "http://apache.org/xml/features/disallow-doctype-decl";
/* 433 */         factory.setFeature(featureToSet, true);
/* 434 */         featureToSet = "http://xml.org/sax/features/external-general-entities";
/* 435 */         factory.setFeature(featureToSet, false);
/* 436 */         featureToSet = "http://xml.org/sax/features/external-parameter-entities";
/* 437 */         factory.setFeature(featureToSet, false);
/* 438 */         featureToSet = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/* 439 */         factory.setFeature(featureToSet, false);
/*     */       } 
/* 441 */     } catch (ParserConfigurationException|org.xml.sax.SAXNotRecognizedException|org.xml.sax.SAXNotSupportedException e) {
/* 442 */       LOGGER.log(Level.WARNING, "Factory [{0}] doesn't support " + featureToSet + " feature!", new Object[] { factory.getClass().getName() });
/*     */     } 
/* 444 */     return factory;
/*     */   }
/*     */   
/*     */   public static XPathFactory newXPathFactory(boolean secureXmlProcessingEnabled) {
/* 448 */     XPathFactory factory = XPathFactory.newInstance();
/*     */     try {
/* 450 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", isXMLSecurityDisabled(secureXmlProcessingEnabled));
/* 451 */     } catch (XPathFactoryConfigurationException e) {
/* 452 */       LOGGER.log(Level.WARNING, "Factory [{0}] doesn't support secure xml processing!", new Object[] { factory.getClass().getName() });
/*     */     } 
/* 454 */     return factory;
/*     */   }
/*     */   
/*     */   public static XMLInputFactory newXMLInputFactory(boolean secureXmlProcessingEnabled) {
/* 458 */     XMLInputFactory factory = XMLInputFactory.newInstance();
/* 459 */     if (isXMLSecurityDisabled(secureXmlProcessingEnabled)) {
/*     */       
/* 461 */       factory.setProperty("javax.xml.stream.supportDTD", Boolean.valueOf(false));
/* 462 */       factory.setProperty("javax.xml.stream.isSupportingExternalEntities", Boolean.valueOf(false));
/*     */     } 
/* 464 */     return factory;
/*     */   }
/*     */   
/*     */   private static boolean isXMLSecurityDisabled(boolean runtimeDisabled) {
/* 468 */     return (XML_SECURITY_DISABLED || runtimeDisabled);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SchemaFactory allowExternalAccess(SchemaFactory sf, String value, boolean disableSecureProcessing) {
/* 474 */     if (isXMLSecurityDisabled(disableSecureProcessing)) {
/* 475 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 476 */         LOGGER.log(Level.FINE, "Xml Security disabled, no JAXP xsd external access configuration necessary.");
/*     */       }
/* 478 */       return sf;
/*     */     } 
/*     */     
/* 481 */     if (System.getProperty("javax.xml.accessExternalSchema") != null) {
/* 482 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 483 */         LOGGER.log(Level.FINE, "Detected explicitly JAXP configuration, no JAXP xsd external access configuration necessary.");
/*     */       }
/* 485 */       return sf;
/*     */     } 
/*     */     
/*     */     try {
/* 489 */       sf.setProperty("http://javax.xml.XMLConstants/property/accessExternalSchema", value);
/* 490 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 491 */         LOGGER.log(Level.FINE, "Property \"{0}\" is supported and has been successfully set by used JAXP implementation.", new Object[] { "http://javax.xml.XMLConstants/property/accessExternalSchema" });
/*     */       }
/* 493 */     } catch (SAXException ignored) {
/*     */       
/* 495 */       if (LOGGER.isLoggable(Level.CONFIG)) {
/* 496 */         LOGGER.log(Level.CONFIG, "Property \"{0}\" is not supported by used JAXP implementation.", new Object[] { "http://javax.xml.XMLConstants/property/accessExternalSchema" });
/*     */       }
/*     */     } 
/* 499 */     return sf;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/XmlUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */