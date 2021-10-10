/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.io.ASCIIReader;
/*      */ import com.sun.org.apache.xerces.internal.impl.io.UCSReader;
/*      */ import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
/*      */ import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
/*      */ import com.sun.org.apache.xerces.internal.util.AugmentationsImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.EncodingMap;
/*      */ import com.sun.org.apache.xerces.internal.util.HTTPInputSource;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.URI;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLEntityDescriptionImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLResourceIdentifierImpl;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLLimitAnalyzer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.xml.internal.stream.Entity;
/*      */ import com.sun.xml.internal.stream.StaxEntityResolverWrapper;
/*      */ import com.sun.xml.internal.stream.StaxXMLInputSource;
/*      */ import com.sun.xml.internal.stream.XMLEntityStorage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XMLEntityManager
/*      */   implements XMLComponent, XMLEntityResolver
/*      */ {
/*      */   public static final int DEFAULT_BUFFER_SIZE = 8192;
/*      */   public static final int DEFAULT_XMLDECL_BUFFER_SIZE = 64;
/*      */   public static final int DEFAULT_INTERNAL_BUFFER_SIZE = 1024;
/*      */   protected static final String VALIDATION = "http://xml.org/sax/features/validation";
/*      */   protected boolean fStrictURI;
/*      */   protected static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
/*      */   protected static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
/*      */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*      */   protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
/*      */   protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
/*      */   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   protected static final String STANDARD_URI_CONFORMANT = "http://apache.org/xml/features/standard-uri-conformant";
/*      */   protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*      */   protected static final String STAX_ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/stax-entity-resolver";
/*      */   protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
/*      */   protected static final String BUFFER_SIZE = "http://apache.org/xml/properties/input-buffer-size";
/*      */   protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */   protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */   static final String EXTERNAL_ACCESS_DEFAULT = "all";
/*  179 */   private static final String[] RECOGNIZED_FEATURES = new String[] { "http://xml.org/sax/features/validation", "http://xml.org/sax/features/external-general-entities", "http://xml.org/sax/features/external-parameter-entities", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/warn-on-duplicate-entitydef", "http://apache.org/xml/features/standard-uri-conformant" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  189 */   private static final Boolean[] FEATURE_DEFAULTS = new Boolean[] { null, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  199 */   private static final String[] RECOGNIZED_PROPERTIES = new String[] { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/properties/input-buffer-size", "http://apache.org/xml/properties/security-manager", "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  210 */   private static final Object[] PROPERTY_DEFAULTS = new Object[] { null, null, null, null, new Integer(8192), null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  220 */   private static final String XMLEntity = "[xml]".intern();
/*  221 */   private static final String DTDEntity = "[dtd]".intern();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_BUFFER = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fWarnDuplicateEntityDef;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_ENTITIES = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_ENCODINGS = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean DEBUG_RESOLVER = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fValidation;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fExternalGeneralEntities;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fExternalParameterEntities;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fAllowJavaEncodings = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fLoadExternalDTD = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SymbolTable fSymbolTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLErrorReporter fErrorReporter;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLEntityResolver fEntityResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StaxEntityResolverWrapper fStaxEntityResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyManager fPropertyManager;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean fSupportDTD = true;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean fReplaceEntityReferences = true;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean fSupportExternalEntities = true;
/*      */ 
/*      */ 
/*      */   
/*  312 */   protected String fAccessExternalDTD = "all";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ValidationManager fValidationManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  329 */   protected int fBufferSize = 8192;
/*      */ 
/*      */   
/*  332 */   protected XMLSecurityManager fSecurityManager = null;
/*      */   
/*  334 */   protected XMLLimitAnalyzer fLimitAnalyzer = null;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int entityExpansionIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fStandalone;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fInExternalSubset = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLEntityHandler fEntityHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLEntityScanner fEntityScanner;
/*      */ 
/*      */   
/*      */   protected XMLEntityScanner fXML10EntityScanner;
/*      */ 
/*      */   
/*      */   protected XMLEntityScanner fXML11EntityScanner;
/*      */ 
/*      */   
/*  363 */   protected int fEntityExpansionCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  368 */   protected Map<String, Entity> fEntities = new HashMap<>();
/*      */ 
/*      */   
/*  371 */   protected Stack<Entity> fEntityStack = new Stack<>();
/*      */ 
/*      */   
/*  374 */   protected Entity.ScannedEntity fCurrentEntity = null;
/*      */ 
/*      */   
/*      */   boolean fISCreatedByResolver = false;
/*      */ 
/*      */   
/*      */   protected XMLEntityStorage fEntityStorage;
/*      */ 
/*      */   
/*  383 */   protected final Object[] defaultEncoding = new Object[] { "UTF-8", null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  389 */   private final XMLResourceIdentifierImpl fResourceIdentifier = new XMLResourceIdentifierImpl();
/*      */ 
/*      */   
/*  392 */   private final Augmentations fEntityAugs = new AugmentationsImpl();
/*      */ 
/*      */   
/*  395 */   private CharacterBufferPool fBufferPool = new CharacterBufferPool(this.fBufferSize, 1024);
/*      */ 
/*      */   
/*      */   private static String gUserDir;
/*      */ 
/*      */   
/*      */   private static URI gUserDirURI;
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLEntityManager() {
/*  406 */     this.fSecurityManager = new XMLSecurityManager(true);
/*  407 */     this.fEntityStorage = new XMLEntityStorage(this);
/*  408 */     setScannerVersion((short)1);
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLEntityManager(PropertyManager propertyManager) {
/*  413 */     this.fPropertyManager = propertyManager;
/*      */ 
/*      */     
/*  416 */     this.fEntityStorage = new XMLEntityStorage(this);
/*  417 */     this.fEntityScanner = new XMLEntityScanner(propertyManager, this);
/*  418 */     reset(propertyManager);
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
/*      */   public void addInternalEntity(String name, String text) {
/*  436 */     if (!this.fEntities.containsKey(name)) {
/*  437 */       Entity entity = new Entity.InternalEntity(name, text, this.fInExternalSubset);
/*  438 */       this.fEntities.put(name, entity);
/*      */     }
/*  440 */     else if (this.fWarnDuplicateEntityDef) {
/*  441 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { name }, (short)0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExternalEntity(String name, String publicId, String literalSystemId, String baseSystemId) throws IOException {
/*  475 */     if (!this.fEntities.containsKey(name)) {
/*  476 */       if (baseSystemId == null) {
/*      */         
/*  478 */         int size = this.fEntityStack.size();
/*  479 */         if (size == 0 && this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) {
/*  480 */           baseSystemId = this.fCurrentEntity.entityLocation.getExpandedSystemId();
/*      */         }
/*  482 */         for (int i = size - 1; i >= 0; i--) {
/*      */           
/*  484 */           Entity.ScannedEntity externalEntity = (Entity.ScannedEntity)this.fEntityStack.elementAt(i);
/*  485 */           if (externalEntity.entityLocation != null && externalEntity.entityLocation.getExpandedSystemId() != null) {
/*  486 */             baseSystemId = externalEntity.entityLocation.getExpandedSystemId();
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  493 */       Entity entity = new Entity.ExternalEntity(name, new XMLEntityDescriptionImpl(name, publicId, literalSystemId, baseSystemId, expandSystemId(literalSystemId, baseSystemId, false)), null, this.fInExternalSubset);
/*  494 */       this.fEntities.put(name, entity);
/*      */     }
/*  496 */     else if (this.fWarnDuplicateEntityDef) {
/*  497 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { name }, (short)0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addUnparsedEntity(String name, String publicId, String systemId, String baseSystemId, String notation) {
/*  526 */     if (!this.fEntities.containsKey(name)) {
/*  527 */       Entity.ExternalEntity entity = new Entity.ExternalEntity(name, new XMLEntityDescriptionImpl(name, publicId, systemId, baseSystemId, null), notation, this.fInExternalSubset);
/*      */ 
/*      */       
/*  530 */       this.fEntities.put(name, entity);
/*      */     }
/*  532 */     else if (this.fWarnDuplicateEntityDef) {
/*  533 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ENTITY_DEFINITION", new Object[] { name }, (short)0);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLEntityStorage getEntityStore() {
/*  544 */     return this.fEntityStorage;
/*      */   }
/*      */ 
/*      */   
/*      */   public XMLEntityScanner getEntityScanner() {
/*  549 */     if (this.fEntityScanner == null) {
/*      */       
/*  551 */       if (this.fXML10EntityScanner == null) {
/*  552 */         this.fXML10EntityScanner = new XMLEntityScanner();
/*      */       }
/*  554 */       this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
/*  555 */       this.fEntityScanner = this.fXML10EntityScanner;
/*      */     } 
/*  557 */     return this.fEntityScanner;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScannerVersion(short version) {
/*  563 */     if (version == 1) {
/*  564 */       if (this.fXML10EntityScanner == null) {
/*  565 */         this.fXML10EntityScanner = new XMLEntityScanner();
/*      */       }
/*  567 */       this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
/*  568 */       this.fEntityScanner = this.fXML10EntityScanner;
/*  569 */       this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
/*      */     } else {
/*  571 */       if (this.fXML11EntityScanner == null) {
/*  572 */         this.fXML11EntityScanner = new XML11EntityScanner();
/*      */       }
/*  574 */       this.fXML11EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
/*  575 */       this.fEntityScanner = this.fXML11EntityScanner;
/*  576 */       this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String setupCurrentEntity(boolean reference, String name, XMLInputSource xmlInputSource, boolean literal, boolean isExternal) throws IOException, XNIException {
/*  601 */     String publicId = xmlInputSource.getPublicId();
/*  602 */     String literalSystemId = xmlInputSource.getSystemId();
/*  603 */     String baseSystemId = xmlInputSource.getBaseSystemId();
/*  604 */     String encoding = xmlInputSource.getEncoding();
/*  605 */     boolean encodingExternallySpecified = (encoding != null);
/*  606 */     Boolean isBigEndian = null;
/*      */ 
/*      */     
/*  609 */     InputStream stream = null;
/*  610 */     Reader reader = xmlInputSource.getCharacterStream();
/*      */ 
/*      */     
/*  613 */     String expandedSystemId = expandSystemId(literalSystemId, baseSystemId, this.fStrictURI);
/*  614 */     if (baseSystemId == null) {
/*  615 */       baseSystemId = expandedSystemId;
/*      */     }
/*  617 */     if (reader == null) {
/*  618 */       stream = xmlInputSource.getByteStream();
/*  619 */       if (stream == null) {
/*  620 */         URL location = new URL(expandedSystemId);
/*  621 */         URLConnection connect = location.openConnection();
/*  622 */         if (!(connect instanceof HttpURLConnection)) {
/*  623 */           stream = connect.getInputStream();
/*      */         } else {
/*      */           
/*  626 */           boolean followRedirects = true;
/*      */ 
/*      */           
/*  629 */           if (xmlInputSource instanceof HTTPInputSource) {
/*  630 */             HttpURLConnection urlConnection = (HttpURLConnection)connect;
/*  631 */             HTTPInputSource httpInputSource = (HTTPInputSource)xmlInputSource;
/*      */ 
/*      */             
/*  634 */             Iterator<Map.Entry<String, String>> propIter = httpInputSource.getHTTPRequestProperties();
/*  635 */             while (propIter.hasNext()) {
/*  636 */               Map.Entry<String, String> entry = propIter.next();
/*  637 */               urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
/*      */             } 
/*      */ 
/*      */             
/*  641 */             followRedirects = httpInputSource.getFollowHTTPRedirects();
/*  642 */             if (!followRedirects) {
/*  643 */               setInstanceFollowRedirects(urlConnection, followRedirects);
/*      */             }
/*      */           } 
/*      */           
/*  647 */           stream = connect.getInputStream();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  653 */           if (followRedirects) {
/*  654 */             String redirect = connect.getURL().toString();
/*      */ 
/*      */             
/*  657 */             if (!redirect.equals(expandedSystemId)) {
/*  658 */               literalSystemId = redirect;
/*  659 */               expandedSystemId = redirect;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  666 */       stream = new RewindableInputStream(stream);
/*      */ 
/*      */       
/*  669 */       if (encoding == null) {
/*      */         
/*  671 */         byte[] b4 = new byte[4];
/*  672 */         int count = 0;
/*  673 */         for (; count < 4; count++) {
/*  674 */           b4[count] = (byte)stream.read();
/*      */         }
/*  676 */         if (count == 4) {
/*  677 */           Object[] encodingDesc = getEncodingName(b4, count);
/*  678 */           encoding = (String)encodingDesc[0];
/*  679 */           isBigEndian = (Boolean)encodingDesc[1];
/*      */           
/*  681 */           stream.reset();
/*      */ 
/*      */ 
/*      */           
/*  685 */           if (count > 2 && encoding.equals("UTF-8")) {
/*  686 */             int b0 = b4[0] & 0xFF;
/*  687 */             int b1 = b4[1] & 0xFF;
/*  688 */             int b2 = b4[2] & 0xFF;
/*  689 */             if (b0 == 239 && b1 == 187 && b2 == 191)
/*      */             {
/*  691 */               stream.skip(3L);
/*      */             }
/*      */           } 
/*  694 */           reader = createReader(stream, encoding, isBigEndian);
/*      */         } else {
/*  696 */           reader = createReader(stream, encoding, isBigEndian);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  702 */         encoding = encoding.toUpperCase(Locale.ENGLISH);
/*      */ 
/*      */         
/*  705 */         if (encoding.equals("UTF-8")) {
/*  706 */           int[] b3 = new int[3];
/*  707 */           int count = 0;
/*  708 */           for (; count < 3; count++) {
/*  709 */             b3[count] = stream.read();
/*  710 */             if (b3[count] == -1)
/*      */               break; 
/*      */           } 
/*  713 */           if (count == 3) {
/*  714 */             if (b3[0] != 239 || b3[1] != 187 || b3[2] != 191)
/*      */             {
/*  716 */               stream.reset();
/*      */             }
/*      */           } else {
/*  719 */             stream.reset();
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  724 */         else if (encoding.equals("UTF-16")) {
/*  725 */           int[] b4 = new int[4];
/*  726 */           int count = 0;
/*  727 */           for (; count < 4; count++) {
/*  728 */             b4[count] = stream.read();
/*  729 */             if (b4[count] == -1)
/*      */               break; 
/*      */           } 
/*  732 */           stream.reset();
/*      */           
/*  734 */           String utf16Encoding = "UTF-16";
/*  735 */           if (count >= 2) {
/*  736 */             int b0 = b4[0];
/*  737 */             int b1 = b4[1];
/*  738 */             if (b0 == 254 && b1 == 255) {
/*      */               
/*  740 */               utf16Encoding = "UTF-16BE";
/*  741 */               isBigEndian = Boolean.TRUE;
/*      */             }
/*  743 */             else if (b0 == 255 && b1 == 254) {
/*      */               
/*  745 */               utf16Encoding = "UTF-16LE";
/*  746 */               isBigEndian = Boolean.FALSE;
/*      */             }
/*  748 */             else if (count == 4) {
/*  749 */               int b2 = b4[2];
/*  750 */               int b3 = b4[3];
/*  751 */               if (b0 == 0 && b1 == 60 && b2 == 0 && b3 == 63) {
/*      */                 
/*  753 */                 utf16Encoding = "UTF-16BE";
/*  754 */                 isBigEndian = Boolean.TRUE;
/*      */               } 
/*  756 */               if (b0 == 60 && b1 == 0 && b2 == 63 && b3 == 0) {
/*      */                 
/*  758 */                 utf16Encoding = "UTF-16LE";
/*  759 */                 isBigEndian = Boolean.FALSE;
/*      */               } 
/*      */             } 
/*      */           } 
/*  763 */           reader = createReader(stream, utf16Encoding, isBigEndian);
/*      */ 
/*      */         
/*      */         }
/*  767 */         else if (encoding.equals("ISO-10646-UCS-4")) {
/*  768 */           int[] b4 = new int[4];
/*  769 */           int count = 0;
/*  770 */           for (; count < 4; count++) {
/*  771 */             b4[count] = stream.read();
/*  772 */             if (b4[count] == -1)
/*      */               break; 
/*      */           } 
/*  775 */           stream.reset();
/*      */ 
/*      */           
/*  778 */           if (count == 4)
/*      */           {
/*  780 */             if (b4[0] == 0 && b4[1] == 0 && b4[2] == 0 && b4[3] == 60) {
/*  781 */               isBigEndian = Boolean.TRUE;
/*      */             
/*      */             }
/*  784 */             else if (b4[0] == 60 && b4[1] == 0 && b4[2] == 0 && b4[3] == 0) {
/*  785 */               isBigEndian = Boolean.FALSE;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         }
/*  791 */         else if (encoding.equals("ISO-10646-UCS-2")) {
/*  792 */           int[] b4 = new int[4];
/*  793 */           int count = 0;
/*  794 */           for (; count < 4; count++) {
/*  795 */             b4[count] = stream.read();
/*  796 */             if (b4[count] == -1)
/*      */               break; 
/*      */           } 
/*  799 */           stream.reset();
/*      */           
/*  801 */           if (count == 4)
/*      */           {
/*  803 */             if (b4[0] == 0 && b4[1] == 60 && b4[2] == 0 && b4[3] == 63) {
/*  804 */               isBigEndian = Boolean.TRUE;
/*      */             
/*      */             }
/*  807 */             else if (b4[0] == 60 && b4[1] == 0 && b4[2] == 63 && b4[3] == 0) {
/*  808 */               isBigEndian = Boolean.FALSE;
/*      */             } 
/*      */           }
/*      */         } 
/*      */         
/*  813 */         reader = createReader(stream, encoding, isBigEndian);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  830 */     if (this.fCurrentEntity != null) {
/*  831 */       this.fEntityStack.push(this.fCurrentEntity);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  839 */     this.fCurrentEntity = new Entity.ScannedEntity(reference, name, new XMLResourceIdentifierImpl(publicId, literalSystemId, baseSystemId, expandedSystemId), stream, reader, encoding, literal, encodingExternallySpecified, isExternal);
/*      */ 
/*      */     
/*  842 */     this.fCurrentEntity.setEncodingExternallySpecified(encodingExternallySpecified);
/*  843 */     this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
/*  844 */     this.fResourceIdentifier.setValues(publicId, literalSystemId, baseSystemId, expandedSystemId);
/*  845 */     if (this.fLimitAnalyzer != null) {
/*  846 */       this.fLimitAnalyzer.startEntity(name);
/*      */     }
/*  848 */     return encoding;
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
/*      */   public boolean isExternalEntity(String entityName) {
/*  861 */     Entity entity = this.fEntities.get(entityName);
/*  862 */     if (entity == null) {
/*  863 */       return false;
/*      */     }
/*  865 */     return entity.isExternal();
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
/*      */   public boolean isEntityDeclInExternalSubset(String entityName) {
/*  878 */     Entity entity = this.fEntities.get(entityName);
/*  879 */     if (entity == null) {
/*  880 */       return false;
/*      */     }
/*  882 */     return entity.isEntityDeclInExternalSubset();
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
/*      */   public void setStandalone(boolean standalone) {
/*  897 */     this.fStandalone = standalone;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStandalone() {
/*  903 */     return this.fStandalone;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDeclaredEntity(String entityName) {
/*  908 */     Entity entity = this.fEntities.get(entityName);
/*  909 */     return (entity != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUnparsedEntity(String entityName) {
/*  914 */     Entity entity = this.fEntities.get(entityName);
/*  915 */     if (entity == null) {
/*  916 */       return false;
/*      */     }
/*  918 */     return entity.isUnparsed();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLResourceIdentifier getCurrentResourceIdentifier() {
/*  929 */     return this.fResourceIdentifier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityHandler(XMLEntityHandler entityHandler) {
/*  940 */     this.fEntityHandler = entityHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public StaxXMLInputSource resolveEntityAsPerStax(XMLResourceIdentifier resourceIdentifier) throws IOException {
/*  946 */     if (resourceIdentifier == null) return null;
/*      */     
/*  948 */     String publicId = resourceIdentifier.getPublicId();
/*  949 */     String literalSystemId = resourceIdentifier.getLiteralSystemId();
/*  950 */     String baseSystemId = resourceIdentifier.getBaseSystemId();
/*  951 */     String expandedSystemId = resourceIdentifier.getExpandedSystemId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  958 */     boolean needExpand = (expandedSystemId == null);
/*      */ 
/*      */ 
/*      */     
/*  962 */     if (baseSystemId == null && this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) {
/*  963 */       baseSystemId = this.fCurrentEntity.entityLocation.getExpandedSystemId();
/*  964 */       if (baseSystemId != null)
/*  965 */         needExpand = true; 
/*      */     } 
/*  967 */     if (needExpand) {
/*  968 */       expandedSystemId = expandSystemId(literalSystemId, baseSystemId, false);
/*      */     }
/*      */     
/*  971 */     StaxXMLInputSource staxInputSource = null;
/*  972 */     XMLInputSource xmlInputSource = null;
/*      */     
/*  974 */     XMLResourceIdentifierImpl ri = null;
/*      */     
/*  976 */     if (resourceIdentifier instanceof XMLResourceIdentifierImpl) {
/*  977 */       ri = (XMLResourceIdentifierImpl)resourceIdentifier;
/*      */     } else {
/*  979 */       this.fResourceIdentifier.clear();
/*  980 */       ri = this.fResourceIdentifier;
/*      */     } 
/*  982 */     ri.setValues(publicId, literalSystemId, baseSystemId, expandedSystemId);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  987 */     this.fISCreatedByResolver = false;
/*      */     
/*  989 */     if (this.fStaxEntityResolver != null) {
/*  990 */       staxInputSource = this.fStaxEntityResolver.resolveEntity(ri);
/*  991 */       if (staxInputSource != null) {
/*  992 */         this.fISCreatedByResolver = true;
/*      */       }
/*      */     } 
/*      */     
/*  996 */     if (this.fEntityResolver != null) {
/*  997 */       xmlInputSource = this.fEntityResolver.resolveEntity(ri);
/*  998 */       if (xmlInputSource != null) {
/*  999 */         this.fISCreatedByResolver = true;
/*      */       }
/*      */     } 
/*      */     
/* 1003 */     if (xmlInputSource != null)
/*      */     {
/* 1005 */       staxInputSource = new StaxXMLInputSource(xmlInputSource, this.fISCreatedByResolver);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1010 */     if (staxInputSource == null) {
/*      */ 
/*      */ 
/*      */       
/* 1014 */       staxInputSource = new StaxXMLInputSource(new XMLInputSource(publicId, literalSystemId, baseSystemId));
/* 1015 */     } else if (staxInputSource.hasXMLStreamOrXMLEventReader()) {
/*      */     
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1024 */     return staxInputSource;
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
/*      */   public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier) throws IOException, XNIException {
/* 1051 */     if (resourceIdentifier == null) return null; 
/* 1052 */     String publicId = resourceIdentifier.getPublicId();
/* 1053 */     String literalSystemId = resourceIdentifier.getLiteralSystemId();
/* 1054 */     String baseSystemId = resourceIdentifier.getBaseSystemId();
/* 1055 */     String expandedSystemId = resourceIdentifier.getExpandedSystemId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1063 */     boolean needExpand = (expandedSystemId == null);
/*      */ 
/*      */ 
/*      */     
/* 1067 */     if (baseSystemId == null && this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) {
/* 1068 */       baseSystemId = this.fCurrentEntity.entityLocation.getExpandedSystemId();
/* 1069 */       if (baseSystemId != null)
/* 1070 */         needExpand = true; 
/*      */     } 
/* 1072 */     if (needExpand) {
/* 1073 */       expandedSystemId = expandSystemId(literalSystemId, baseSystemId, false);
/*      */     }
/*      */     
/* 1076 */     XMLInputSource xmlInputSource = null;
/*      */     
/* 1078 */     if (this.fEntityResolver != null) {
/* 1079 */       resourceIdentifier.setBaseSystemId(baseSystemId);
/* 1080 */       resourceIdentifier.setExpandedSystemId(expandedSystemId);
/* 1081 */       xmlInputSource = this.fEntityResolver.resolveEntity(resourceIdentifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1089 */     if (xmlInputSource == null)
/*      */     {
/*      */ 
/*      */       
/* 1093 */       xmlInputSource = new XMLInputSource(publicId, literalSystemId, baseSystemId);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1101 */     return xmlInputSource;
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
/*      */   public void startEntity(boolean isGE, String entityName, boolean literal) throws IOException, XNIException {
/* 1120 */     Entity entity = this.fEntityStorage.getEntity(entityName);
/* 1121 */     if (entity == null) {
/* 1122 */       if (this.fEntityHandler != null) {
/* 1123 */         String encoding = null;
/* 1124 */         this.fResourceIdentifier.clear();
/* 1125 */         this.fEntityAugs.removeAllItems();
/* 1126 */         this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
/* 1127 */         this.fEntityHandler.startEntity(entityName, this.fResourceIdentifier, encoding, this.fEntityAugs);
/* 1128 */         this.fEntityAugs.removeAllItems();
/* 1129 */         this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
/* 1130 */         this.fEntityHandler.endEntity(entityName, this.fEntityAugs);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1136 */     boolean external = entity.isExternal();
/* 1137 */     Entity.ExternalEntity externalEntity = null;
/* 1138 */     String extLitSysId = null, extBaseSysId = null, expandedSystemId = null;
/* 1139 */     if (external) {
/* 1140 */       externalEntity = (Entity.ExternalEntity)entity;
/* 1141 */       extLitSysId = (externalEntity.entityLocation != null) ? externalEntity.entityLocation.getLiteralSystemId() : null;
/* 1142 */       extBaseSysId = (externalEntity.entityLocation != null) ? externalEntity.entityLocation.getBaseSystemId() : null;
/* 1143 */       expandedSystemId = expandSystemId(extLitSysId, extBaseSysId);
/* 1144 */       boolean unparsed = entity.isUnparsed();
/* 1145 */       boolean parameter = entityName.startsWith("%");
/* 1146 */       boolean general = !parameter;
/* 1147 */       if (unparsed || (general && !this.fExternalGeneralEntities) || (parameter && !this.fExternalParameterEntities) || !this.fSupportDTD || !this.fSupportExternalEntities) {
/*      */ 
/*      */ 
/*      */         
/* 1151 */         if (this.fEntityHandler != null) {
/* 1152 */           this.fResourceIdentifier.clear();
/* 1153 */           String encoding = null;
/* 1154 */           this.fResourceIdentifier.setValues((externalEntity.entityLocation != null) ? externalEntity.entityLocation
/* 1155 */               .getPublicId() : null, extLitSysId, extBaseSysId, expandedSystemId);
/*      */           
/* 1157 */           this.fEntityAugs.removeAllItems();
/* 1158 */           this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
/* 1159 */           this.fEntityHandler.startEntity(entityName, this.fResourceIdentifier, encoding, this.fEntityAugs);
/* 1160 */           this.fEntityAugs.removeAllItems();
/* 1161 */           this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
/* 1162 */           this.fEntityHandler.endEntity(entityName, this.fEntityAugs);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1169 */     int size = this.fEntityStack.size();
/* 1170 */     for (int i = size; i >= 0; i--) {
/*      */ 
/*      */       
/* 1173 */       Entity activeEntity = (i == size) ? this.fCurrentEntity : this.fEntityStack.elementAt(i);
/* 1174 */       if (activeEntity.name == entityName) {
/* 1175 */         String path = entityName;
/* 1176 */         for (int j = i + 1; j < size; j++) {
/* 1177 */           activeEntity = this.fEntityStack.elementAt(j);
/* 1178 */           path = path + " -> " + activeEntity.name;
/*      */         } 
/* 1180 */         path = path + " -> " + this.fCurrentEntity.name;
/* 1181 */         path = path + " -> " + entityName;
/* 1182 */         this.fErrorReporter.reportError(getEntityScanner(), "http://www.w3.org/TR/1998/REC-xml-19980210", "RecursiveReference", new Object[] { entityName, path }, (short)2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1187 */         if (this.fEntityHandler != null) {
/* 1188 */           this.fResourceIdentifier.clear();
/* 1189 */           String encoding = null;
/* 1190 */           if (external) {
/* 1191 */             this.fResourceIdentifier.setValues((externalEntity.entityLocation != null) ? externalEntity.entityLocation
/* 1192 */                 .getPublicId() : null, extLitSysId, extBaseSysId, expandedSystemId);
/*      */           }
/*      */           
/* 1195 */           this.fEntityAugs.removeAllItems();
/* 1196 */           this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
/* 1197 */           this.fEntityHandler.startEntity(entityName, this.fResourceIdentifier, encoding, this.fEntityAugs);
/* 1198 */           this.fEntityAugs.removeAllItems();
/* 1199 */           this.fEntityAugs.putItem("ENTITY_SKIPPED", Boolean.TRUE);
/* 1200 */           this.fEntityHandler.endEntity(entityName, this.fEntityAugs);
/*      */         } 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 1208 */     StaxXMLInputSource staxInputSource = null;
/* 1209 */     XMLInputSource xmlInputSource = null;
/*      */     
/* 1211 */     if (external) {
/* 1212 */       staxInputSource = resolveEntityAsPerStax(externalEntity.entityLocation);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1218 */       xmlInputSource = staxInputSource.getXMLInputSource();
/* 1219 */       if (!this.fISCreatedByResolver)
/*      */       {
/* 1221 */         if (this.fLoadExternalDTD) {
/* 1222 */           String accessError = SecuritySupport.checkAccess(expandedSystemId, this.fAccessExternalDTD, "all");
/* 1223 */           if (accessError != null) {
/* 1224 */             this.fErrorReporter.reportError(getEntityScanner(), "http://www.w3.org/TR/1998/REC-xml-19980210", "AccessExternalEntity", new Object[] {
/*      */                   
/* 1226 */                   SecuritySupport.sanitizePath(expandedSystemId), accessError }, (short)2);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1234 */       Entity.InternalEntity internalEntity = (Entity.InternalEntity)entity;
/* 1235 */       Reader reader = new StringReader(internalEntity.text);
/* 1236 */       xmlInputSource = new XMLInputSource(null, null, null, reader, null);
/*      */     } 
/*      */ 
/*      */     
/* 1240 */     startEntity(isGE, entityName, xmlInputSource, literal, external);
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
/*      */   public void startDocumentEntity(XMLInputSource xmlInputSource) throws IOException, XNIException {
/* 1255 */     startEntity(false, XMLEntity, xmlInputSource, false, true);
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
/*      */   public void startDTDEntity(XMLInputSource xmlInputSource) throws IOException, XNIException {
/* 1270 */     startEntity(false, DTDEntity, xmlInputSource, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void startExternalSubset() {
/* 1276 */     this.fInExternalSubset = true;
/*      */   }
/*      */   
/*      */   public void endExternalSubset() {
/* 1280 */     this.fInExternalSubset = false;
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
/*      */   public void startEntity(boolean isGE, String name, XMLInputSource xmlInputSource, boolean literal, boolean isExternal) throws IOException, XNIException {
/* 1304 */     String encoding = setupCurrentEntity(isGE, name, xmlInputSource, literal, isExternal);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1310 */     this.fEntityExpansionCount++;
/* 1311 */     if (this.fLimitAnalyzer != null) {
/* 1312 */       this.fLimitAnalyzer.addValue(this.entityExpansionIndex, name, 1);
/*      */     }
/* 1314 */     if (this.fSecurityManager != null && this.fSecurityManager.isOverLimit(this.entityExpansionIndex, this.fLimitAnalyzer)) {
/* 1315 */       this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/* 1316 */       this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "EntityExpansionLimit", new Object[] { this.fSecurityManager
/* 1317 */             .getLimitValueByIndex(this.entityExpansionIndex) }, (short)2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1322 */       this.fEntityExpansionCount = 0;
/*      */     } 
/*      */ 
/*      */     
/* 1326 */     if (this.fEntityHandler != null) {
/* 1327 */       this.fEntityHandler.startEntity(name, this.fResourceIdentifier, encoding, null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity.ScannedEntity getCurrentEntity() {
/* 1338 */     return this.fCurrentEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity.ScannedEntity getTopLevelEntity() {
/* 1346 */     return 
/* 1347 */       this.fEntityStack.empty() ? null : (Entity.ScannedEntity)this.fEntityStack.elementAt(0);
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
/*      */   public void closeReaders() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endEntity() throws IOException, XNIException {
/* 1371 */     Entity.ScannedEntity entity = (this.fEntityStack.size() > 0) ? (Entity.ScannedEntity)this.fEntityStack.pop() : null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1378 */     if (this.fCurrentEntity != null) {
/*      */       
/*      */       try {
/* 1381 */         if (this.fLimitAnalyzer != null) {
/* 1382 */           this.fLimitAnalyzer.endEntity(XMLSecurityManager.Limit.GENERAL_ENTITY_SIZE_LIMIT, this.fCurrentEntity.name);
/* 1383 */           if (this.fCurrentEntity.name.equals("[xml]")) {
/* 1384 */             this.fSecurityManager.debugPrint(this.fLimitAnalyzer);
/*      */           }
/*      */         } 
/* 1387 */         this.fCurrentEntity.close();
/* 1388 */       } catch (IOException ex) {
/* 1389 */         throw new XNIException(ex);
/*      */       } 
/*      */     }
/*      */     
/* 1393 */     if (this.fEntityHandler != null)
/*      */     {
/* 1395 */       if (entity == null) {
/* 1396 */         this.fEntityAugs.removeAllItems();
/* 1397 */         this.fEntityAugs.putItem("LAST_ENTITY", Boolean.TRUE);
/* 1398 */         this.fEntityHandler.endEntity(this.fCurrentEntity.name, this.fEntityAugs);
/* 1399 */         this.fEntityAugs.removeAllItems();
/*      */       } else {
/* 1401 */         this.fEntityHandler.endEntity(this.fCurrentEntity.name, null);
/*      */       } 
/*      */     }
/*      */     
/* 1405 */     boolean documentEntity = (this.fCurrentEntity.name == XMLEntity);
/*      */ 
/*      */     
/* 1408 */     this.fCurrentEntity = entity;
/* 1409 */     this.fEntityScanner.setCurrentEntity(this.fCurrentEntity);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1415 */     if ((((this.fCurrentEntity == null) ? 1 : 0) & (!documentEntity ? 1 : 0)) != 0) {
/* 1416 */       throw new EOFException();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset(PropertyManager propertyManager) {
/* 1433 */     this.fSymbolTable = (SymbolTable)propertyManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/* 1434 */     this.fErrorReporter = (XMLErrorReporter)propertyManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/*      */     try {
/* 1436 */       this.fStaxEntityResolver = (StaxEntityResolverWrapper)propertyManager.getProperty("http://apache.org/xml/properties/internal/stax-entity-resolver");
/* 1437 */     } catch (XMLConfigurationException e) {
/* 1438 */       this.fStaxEntityResolver = null;
/*      */     } 
/*      */     
/* 1441 */     this.fSupportDTD = ((Boolean)propertyManager.getProperty("javax.xml.stream.supportDTD")).booleanValue();
/* 1442 */     this.fReplaceEntityReferences = ((Boolean)propertyManager.getProperty("javax.xml.stream.isReplacingEntityReferences")).booleanValue();
/* 1443 */     this.fSupportExternalEntities = ((Boolean)propertyManager.getProperty("javax.xml.stream.isSupportingExternalEntities")).booleanValue();
/*      */ 
/*      */     
/* 1446 */     this.fLoadExternalDTD = !((Boolean)propertyManager.getProperty("http://java.sun.com/xml/stream/properties/ignore-external-dtd")).booleanValue();
/*      */ 
/*      */     
/* 1449 */     XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)propertyManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager");
/* 1450 */     this.fAccessExternalDTD = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */     
/* 1452 */     this.fSecurityManager = (XMLSecurityManager)propertyManager.getProperty("http://apache.org/xml/properties/security-manager");
/*      */     
/* 1454 */     this.fLimitAnalyzer = new XMLLimitAnalyzer();
/*      */     
/* 1456 */     this.fEntityStorage.reset(propertyManager);
/*      */     
/* 1458 */     this.fEntityScanner.reset(propertyManager);
/*      */ 
/*      */ 
/*      */     
/* 1462 */     this.fEntities.clear();
/* 1463 */     this.fEntityStack.removeAllElements();
/* 1464 */     this.fCurrentEntity = null;
/* 1465 */     this.fValidation = false;
/* 1466 */     this.fExternalGeneralEntities = true;
/* 1467 */     this.fExternalParameterEntities = true;
/* 1468 */     this.fAllowJavaEncodings = true;
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
/*      */   public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
/* 1488 */     boolean parser_settings = componentManager.getFeature("http://apache.org/xml/features/internal/parser-settings", true);
/*      */     
/* 1490 */     if (!parser_settings) {
/*      */       
/* 1492 */       reset();
/* 1493 */       if (this.fEntityScanner != null) {
/* 1494 */         this.fEntityScanner.reset(componentManager);
/*      */       }
/* 1496 */       if (this.fEntityStorage != null) {
/* 1497 */         this.fEntityStorage.reset(componentManager);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1503 */     this.fValidation = componentManager.getFeature("http://xml.org/sax/features/validation", false);
/* 1504 */     this.fExternalGeneralEntities = componentManager.getFeature("http://xml.org/sax/features/external-general-entities", true);
/* 1505 */     this.fExternalParameterEntities = componentManager.getFeature("http://xml.org/sax/features/external-parameter-entities", true);
/*      */ 
/*      */     
/* 1508 */     this.fAllowJavaEncodings = componentManager.getFeature("http://apache.org/xml/features/allow-java-encodings", false);
/* 1509 */     this.fWarnDuplicateEntityDef = componentManager.getFeature("http://apache.org/xml/features/warn-on-duplicate-entitydef", false);
/* 1510 */     this.fStrictURI = componentManager.getFeature("http://apache.org/xml/features/standard-uri-conformant", false);
/* 1511 */     this.fLoadExternalDTD = componentManager.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", true);
/*      */ 
/*      */     
/* 1514 */     this.fSymbolTable = (SymbolTable)componentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/* 1515 */     this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
/* 1516 */     this.fEntityResolver = (XMLEntityResolver)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver", null);
/* 1517 */     this.fStaxEntityResolver = (StaxEntityResolverWrapper)componentManager.getProperty("http://apache.org/xml/properties/internal/stax-entity-resolver", null);
/* 1518 */     this.fValidationManager = (ValidationManager)componentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager", null);
/* 1519 */     this.fSecurityManager = (XMLSecurityManager)componentManager.getProperty("http://apache.org/xml/properties/security-manager", null);
/* 1520 */     this.entityExpansionIndex = this.fSecurityManager.getIndex("http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit");
/*      */ 
/*      */     
/* 1523 */     this.fSupportDTD = true;
/* 1524 */     this.fReplaceEntityReferences = true;
/* 1525 */     this.fSupportExternalEntities = true;
/*      */ 
/*      */     
/* 1528 */     XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)componentManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", null);
/* 1529 */     if (spm == null) {
/* 1530 */       spm = new XMLSecurityPropertyManager();
/*      */     }
/* 1532 */     this.fAccessExternalDTD = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */ 
/*      */     
/* 1535 */     reset();
/*      */     
/* 1537 */     this.fEntityScanner.reset(componentManager);
/* 1538 */     this.fEntityStorage.reset(componentManager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/* 1546 */     this.fLimitAnalyzer = new XMLLimitAnalyzer();
/*      */     
/* 1548 */     this.fStandalone = false;
/* 1549 */     this.fEntities.clear();
/* 1550 */     this.fEntityStack.removeAllElements();
/* 1551 */     this.fEntityExpansionCount = 0;
/*      */     
/* 1553 */     this.fCurrentEntity = null;
/*      */     
/* 1555 */     if (this.fXML10EntityScanner != null) {
/* 1556 */       this.fXML10EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
/*      */     }
/* 1558 */     if (this.fXML11EntityScanner != null) {
/* 1559 */       this.fXML11EntityScanner.reset(this.fSymbolTable, this, this.fErrorReporter);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1584 */     this.fEntityHandler = null;
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
/*      */   public String[] getRecognizedFeatures() {
/* 1597 */     return (String[])RECOGNIZED_FEATURES.clone();
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
/*      */   public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
/* 1619 */     if (featureId.startsWith("http://apache.org/xml/features/")) {
/* 1620 */       int suffixLength = featureId.length() - "http://apache.org/xml/features/".length();
/* 1621 */       if (suffixLength == "allow-java-encodings".length() && featureId
/* 1622 */         .endsWith("allow-java-encodings")) {
/* 1623 */         this.fAllowJavaEncodings = state;
/*      */       }
/* 1625 */       if (suffixLength == "nonvalidating/load-external-dtd".length() && featureId
/* 1626 */         .endsWith("nonvalidating/load-external-dtd")) {
/* 1627 */         this.fLoadExternalDTD = state;
/*      */         return;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String propertyId, Object value) {
/* 1651 */     if (propertyId.startsWith("http://apache.org/xml/properties/")) {
/* 1652 */       int suffixLength = propertyId.length() - "http://apache.org/xml/properties/".length();
/*      */       
/* 1654 */       if (suffixLength == "internal/symbol-table".length() && propertyId
/* 1655 */         .endsWith("internal/symbol-table")) {
/* 1656 */         this.fSymbolTable = (SymbolTable)value;
/*      */         return;
/*      */       } 
/* 1659 */       if (suffixLength == "internal/error-reporter".length() && propertyId
/* 1660 */         .endsWith("internal/error-reporter")) {
/* 1661 */         this.fErrorReporter = (XMLErrorReporter)value;
/*      */         return;
/*      */       } 
/* 1664 */       if (suffixLength == "internal/entity-resolver".length() && propertyId
/* 1665 */         .endsWith("internal/entity-resolver")) {
/* 1666 */         this.fEntityResolver = (XMLEntityResolver)value;
/*      */         return;
/*      */       } 
/* 1669 */       if (suffixLength == "input-buffer-size".length() && propertyId
/* 1670 */         .endsWith("input-buffer-size")) {
/* 1671 */         Integer bufferSize = (Integer)value;
/* 1672 */         if (bufferSize != null && bufferSize
/* 1673 */           .intValue() > 64) {
/* 1674 */           this.fBufferSize = bufferSize.intValue();
/* 1675 */           this.fEntityScanner.setBufferSize(this.fBufferSize);
/* 1676 */           this.fBufferPool.setExternalBufferSize(this.fBufferSize);
/*      */         } 
/*      */       } 
/* 1679 */       if (suffixLength == "security-manager".length() && propertyId
/* 1680 */         .endsWith("security-manager")) {
/* 1681 */         this.fSecurityManager = (XMLSecurityManager)value;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1686 */     if (propertyId.equals("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager")) {
/*      */       
/* 1688 */       XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)value;
/* 1689 */       this.fAccessExternalDTD = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setLimitAnalyzer(XMLLimitAnalyzer fLimitAnalyzer) {
/* 1694 */     this.fLimitAnalyzer = fLimitAnalyzer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getRecognizedProperties() {
/* 1703 */     return (String[])RECOGNIZED_PROPERTIES.clone();
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
/*      */   public Boolean getFeatureDefault(String featureId) {
/* 1715 */     for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
/* 1716 */       if (RECOGNIZED_FEATURES[i].equals(featureId)) {
/* 1717 */         return FEATURE_DEFAULTS[i];
/*      */       }
/*      */     } 
/* 1720 */     return null;
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
/*      */   public Object getPropertyDefault(String propertyId) {
/* 1733 */     for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
/* 1734 */       if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
/* 1735 */         return PROPERTY_DEFAULTS[i];
/*      */       }
/*      */     } 
/* 1738 */     return null;
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
/*      */   public static String expandSystemId(String systemId) {
/* 1759 */     return expandSystemId(systemId, null);
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
/* 1771 */   private static boolean[] gNeedEscaping = new boolean[128];
/*      */   
/* 1773 */   private static char[] gAfterEscaping1 = new char[128];
/*      */   
/* 1775 */   private static char[] gAfterEscaping2 = new char[128];
/* 1776 */   private static char[] gHexChs = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*      */ 
/*      */   
/*      */   static {
/* 1780 */     for (int i = 0; i <= 31; i++) {
/* 1781 */       gNeedEscaping[i] = true;
/* 1782 */       gAfterEscaping1[i] = gHexChs[i >> 4];
/* 1783 */       gAfterEscaping2[i] = gHexChs[i & 0xF];
/*      */     } 
/* 1785 */     gNeedEscaping[127] = true;
/* 1786 */     gAfterEscaping1[127] = '7';
/* 1787 */     gAfterEscaping2[127] = 'F';
/* 1788 */     char[] escChs = { ' ', '<', '>', '#', '%', '"', '{', '}', '|', '\\', '^', '~', '[', ']', '`' };
/*      */     
/* 1790 */     int len = escChs.length;
/*      */     
/* 1792 */     for (int j = 0; j < len; j++) {
/* 1793 */       char ch = escChs[j];
/* 1794 */       gNeedEscaping[ch] = true;
/* 1795 */       gAfterEscaping1[ch] = gHexChs[ch >> 4];
/* 1796 */       gAfterEscaping2[ch] = gHexChs[ch & 0xF];
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
/*      */ 
/*      */ 
/*      */   
/*      */   private static synchronized URI getUserDir() throws URI.MalformedURIException {
/* 1812 */     String userDir = "";
/*      */     try {
/* 1814 */       userDir = SecuritySupport.getSystemProperty("user.dir");
/*      */     }
/* 1816 */     catch (SecurityException securityException) {}
/*      */ 
/*      */ 
/*      */     
/* 1820 */     if (userDir.length() == 0) {
/* 1821 */       return new URI("file", "", "", null, null);
/*      */     }
/*      */     
/* 1824 */     if (gUserDirURI != null && userDir.equals(gUserDir)) {
/* 1825 */       return gUserDirURI;
/*      */     }
/*      */ 
/*      */     
/* 1829 */     gUserDir = userDir;
/*      */     
/* 1831 */     char separator = File.separatorChar;
/* 1832 */     userDir = userDir.replace(separator, '/');
/*      */     
/* 1834 */     int len = userDir.length();
/* 1835 */     StringBuffer buffer = new StringBuffer(len * 3);
/*      */     
/* 1837 */     if (len >= 2 && userDir.charAt(1) == ':') {
/* 1838 */       int ch = Character.toUpperCase(userDir.charAt(0));
/* 1839 */       if (ch >= 65 && ch <= 90) {
/* 1840 */         buffer.append('/');
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1845 */     int i = 0;
/* 1846 */     for (; i < len; i++) {
/* 1847 */       int ch = userDir.charAt(i);
/*      */       
/* 1849 */       if (ch >= 128)
/*      */         break; 
/* 1851 */       if (gNeedEscaping[ch]) {
/* 1852 */         buffer.append('%');
/* 1853 */         buffer.append(gAfterEscaping1[ch]);
/* 1854 */         buffer.append(gAfterEscaping2[ch]);
/*      */       }
/*      */       else {
/*      */         
/* 1858 */         buffer.append((char)ch);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1863 */     if (i < len) {
/*      */       
/* 1865 */       byte[] bytes = null;
/*      */       
/*      */       try {
/* 1868 */         bytes = userDir.substring(i).getBytes("UTF-8");
/* 1869 */       } catch (UnsupportedEncodingException e) {
/*      */         
/* 1871 */         return new URI("file", "", userDir, null, null);
/*      */       } 
/* 1873 */       len = bytes.length;
/*      */ 
/*      */       
/* 1876 */       for (i = 0; i < len; i++) {
/* 1877 */         byte b = bytes[i];
/*      */         
/* 1879 */         if (b < 0) {
/* 1880 */           int ch = b + 256;
/* 1881 */           buffer.append('%');
/* 1882 */           buffer.append(gHexChs[ch >> 4]);
/* 1883 */           buffer.append(gHexChs[ch & 0xF]);
/*      */         }
/* 1885 */         else if (gNeedEscaping[b]) {
/* 1886 */           buffer.append('%');
/* 1887 */           buffer.append(gAfterEscaping1[b]);
/* 1888 */           buffer.append(gAfterEscaping2[b]);
/*      */         } else {
/*      */           
/* 1891 */           buffer.append((char)b);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1897 */     if (!userDir.endsWith("/")) {
/* 1898 */       buffer.append('/');
/*      */     }
/* 1900 */     gUserDirURI = new URI("file", "", buffer.toString(), null, null);
/*      */     
/* 1902 */     return gUserDirURI;
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
/*      */   public static void absolutizeAgainstUserDir(URI uri) throws URI.MalformedURIException {
/* 1914 */     uri.absolutize(getUserDir());
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
/*      */   public static String expandSystemId(String systemId, String baseSystemId) {
/* 1933 */     if (systemId == null || systemId.length() == 0) {
/* 1934 */       return systemId;
/*      */     }
/*      */     
/*      */     try {
/* 1938 */       URI uRI = new URI(systemId);
/* 1939 */       if (uRI != null) {
/* 1940 */         return systemId;
/*      */       }
/* 1942 */     } catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {}
/*      */ 
/*      */ 
/*      */     
/* 1946 */     String id = fixURI(systemId);
/*      */ 
/*      */     
/* 1949 */     URI base = null;
/* 1950 */     URI uri = null;
/*      */     try {
/* 1952 */       if (baseSystemId == null || baseSystemId.length() == 0 || baseSystemId
/* 1953 */         .equals(systemId)) {
/* 1954 */         String dir = getUserDir().toString();
/* 1955 */         base = new URI("file", "", dir, null, null);
/*      */       } else {
/*      */         try {
/* 1958 */           base = new URI(fixURI(baseSystemId));
/* 1959 */         } catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
/* 1960 */           if (baseSystemId.indexOf(':') != -1) {
/*      */ 
/*      */             
/* 1963 */             base = new URI("file", "", fixURI(baseSystemId), null, null);
/*      */           } else {
/* 1965 */             String dir = getUserDir().toString();
/* 1966 */             dir = dir + fixURI(baseSystemId);
/* 1967 */             base = new URI("file", "", dir, null, null);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1972 */       uri = new URI(base, id);
/* 1973 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1978 */     if (uri == null) {
/* 1979 */       return systemId;
/*      */     }
/* 1981 */     return uri.toString();
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
/*      */   public static String expandSystemId(String systemId, String baseSystemId, boolean strict) throws URI.MalformedURIException {
/* 2004 */     if (systemId == null) {
/* 2005 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2009 */     if (strict) {
/*      */       
/*      */       try {
/* 2012 */         new URI(systemId);
/* 2013 */         return systemId;
/*      */       }
/* 2015 */       catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {
/*      */         
/* 2017 */         URI base = null;
/*      */         
/* 2019 */         if (baseSystemId == null || baseSystemId.length() == 0) {
/* 2020 */           base = new URI("file", "", getUserDir().toString(), null, null);
/*      */         } else {
/*      */ 
/*      */           
/*      */           try {
/* 2025 */             base = new URI(baseSystemId);
/*      */           }
/* 2027 */           catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
/*      */             
/* 2029 */             String dir = getUserDir().toString();
/* 2030 */             dir = dir + baseSystemId;
/* 2031 */             base = new URI("file", "", dir, null, null);
/*      */           } 
/*      */         } 
/*      */         
/* 2035 */         URI uri = new URI(base, systemId);
/*      */         
/* 2037 */         return uri.toString();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2044 */       return expandSystemIdStrictOff(systemId, baseSystemId);
/*      */     }
/* 2046 */     catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
/*      */ 
/*      */       
/*      */       try {
/*      */ 
/*      */ 
/*      */         
/* 2053 */         return expandSystemIdStrictOff1(systemId, baseSystemId);
/* 2054 */       } catch (URISyntaxException uRISyntaxException) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2059 */         if (systemId.length() == 0) {
/* 2060 */           return systemId;
/*      */         }
/*      */ 
/*      */         
/* 2064 */         String id = fixURI(systemId);
/*      */ 
/*      */         
/* 2067 */         URI base = null;
/* 2068 */         URI uri = null;
/*      */         try {
/* 2070 */           if (baseSystemId == null || baseSystemId.length() == 0 || baseSystemId
/* 2071 */             .equals(systemId)) {
/* 2072 */             base = getUserDir();
/*      */           } else {
/*      */             
/*      */             try {
/* 2076 */               base = new URI(fixURI(baseSystemId).trim());
/*      */             }
/* 2078 */             catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {
/* 2079 */               if (baseSystemId.indexOf(':') != -1) {
/*      */ 
/*      */                 
/* 2082 */                 base = new URI("file", "", fixURI(baseSystemId).trim(), null, null);
/*      */               } else {
/*      */                 
/* 2085 */                 base = new URI(getUserDir(), fixURI(baseSystemId));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 2090 */           uri = new URI(base, id.trim());
/*      */         }
/* 2092 */         catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2097 */         if (uri == null) {
/* 2098 */           return systemId;
/*      */         }
/* 2100 */         return uri.toString();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String expandSystemIdStrictOn(String systemId, String baseSystemId) throws URI.MalformedURIException {
/* 2110 */     URI systemURI = new URI(systemId, true);
/*      */     
/* 2112 */     if (systemURI.isAbsoluteURI()) {
/* 2113 */       return systemId;
/*      */     }
/*      */ 
/*      */     
/* 2117 */     URI baseURI = null;
/* 2118 */     if (baseSystemId == null || baseSystemId.length() == 0) {
/* 2119 */       baseURI = getUserDir();
/*      */     } else {
/*      */       
/* 2122 */       baseURI = new URI(baseSystemId, true);
/* 2123 */       if (!baseURI.isAbsoluteURI())
/*      */       {
/* 2125 */         baseURI.absolutize(getUserDir());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2130 */     systemURI.absolutize(baseURI);
/*      */ 
/*      */     
/* 2133 */     return systemURI.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setInstanceFollowRedirects(HttpURLConnection urlCon, boolean followRedirects) {
/*      */     try {
/* 2145 */       Method method = HttpURLConnection.class.getMethod("setInstanceFollowRedirects", new Class[] { boolean.class });
/* 2146 */       method.invoke(urlCon, new Object[] { followRedirects ? Boolean.TRUE : Boolean.FALSE });
/*      */     
/*      */     }
/* 2149 */     catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String expandSystemIdStrictOff(String systemId, String baseSystemId) throws URI.MalformedURIException {
/* 2159 */     URI systemURI = new URI(systemId, true);
/*      */     
/* 2161 */     if (systemURI.isAbsoluteURI()) {
/* 2162 */       if (systemURI.getScheme().length() > 1) {
/* 2163 */         return systemId;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2171 */       throw new URI.MalformedURIException();
/*      */     } 
/*      */ 
/*      */     
/* 2175 */     URI baseURI = null;
/* 2176 */     if (baseSystemId == null || baseSystemId.length() == 0) {
/* 2177 */       baseURI = getUserDir();
/*      */     } else {
/*      */       
/* 2180 */       baseURI = new URI(baseSystemId, true);
/* 2181 */       if (!baseURI.isAbsoluteURI())
/*      */       {
/* 2183 */         baseURI.absolutize(getUserDir());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2188 */     systemURI.absolutize(baseURI);
/*      */ 
/*      */     
/* 2191 */     return systemURI.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String expandSystemIdStrictOff1(String systemId, String baseSystemId) throws URISyntaxException, URI.MalformedURIException {
/* 2200 */     URI systemURI = new URI(systemId);
/*      */     
/* 2202 */     if (systemURI.isAbsolute()) {
/* 2203 */       if (systemURI.getScheme().length() > 1) {
/* 2204 */         return systemId;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2212 */       throw new URISyntaxException(systemId, "the scheme's length is only one character");
/*      */     } 
/*      */ 
/*      */     
/* 2216 */     URI baseURI = null;
/* 2217 */     if (baseSystemId == null || baseSystemId.length() == 0) {
/* 2218 */       baseURI = getUserDir();
/*      */     } else {
/*      */       
/* 2221 */       baseURI = new URI(baseSystemId, true);
/* 2222 */       if (!baseURI.isAbsoluteURI())
/*      */       {
/* 2224 */         baseURI.absolutize(getUserDir());
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2230 */     systemURI = (new URI(baseURI.toString())).resolve(systemURI);
/*      */ 
/*      */     
/* 2233 */     return systemURI.toString();
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
/*      */   protected Object[] getEncodingName(byte[] b4, int count) {
/* 2256 */     if (count < 2) {
/* 2257 */       return this.defaultEncoding;
/*      */     }
/*      */ 
/*      */     
/* 2261 */     int b0 = b4[0] & 0xFF;
/* 2262 */     int b1 = b4[1] & 0xFF;
/* 2263 */     if (b0 == 254 && b1 == 255)
/*      */     {
/* 2265 */       return new Object[] { "UTF-16BE", new Boolean(true) };
/*      */     }
/* 2267 */     if (b0 == 255 && b1 == 254)
/*      */     {
/* 2269 */       return new Object[] { "UTF-16LE", new Boolean(false) };
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2274 */     if (count < 3) {
/* 2275 */       return this.defaultEncoding;
/*      */     }
/*      */ 
/*      */     
/* 2279 */     int b2 = b4[2] & 0xFF;
/* 2280 */     if (b0 == 239 && b1 == 187 && b2 == 191) {
/* 2281 */       return this.defaultEncoding;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2286 */     if (count < 4) {
/* 2287 */       return this.defaultEncoding;
/*      */     }
/*      */ 
/*      */     
/* 2291 */     int b3 = b4[3] & 0xFF;
/* 2292 */     if (b0 == 0 && b1 == 0 && b2 == 0 && b3 == 60)
/*      */     {
/* 2294 */       return new Object[] { "ISO-10646-UCS-4", new Boolean(true) };
/*      */     }
/* 2296 */     if (b0 == 60 && b1 == 0 && b2 == 0 && b3 == 0)
/*      */     {
/* 2298 */       return new Object[] { "ISO-10646-UCS-4", new Boolean(false) };
/*      */     }
/* 2300 */     if (b0 == 0 && b1 == 0 && b2 == 60 && b3 == 0)
/*      */     {
/*      */       
/* 2303 */       return new Object[] { "ISO-10646-UCS-4", null };
/*      */     }
/* 2305 */     if (b0 == 0 && b1 == 60 && b2 == 0 && b3 == 0)
/*      */     {
/*      */       
/* 2308 */       return new Object[] { "ISO-10646-UCS-4", null };
/*      */     }
/* 2310 */     if (b0 == 0 && b1 == 60 && b2 == 0 && b3 == 63)
/*      */     {
/*      */ 
/*      */       
/* 2314 */       return new Object[] { "UTF-16BE", new Boolean(true) };
/*      */     }
/* 2316 */     if (b0 == 60 && b1 == 0 && b2 == 63 && b3 == 0)
/*      */     {
/*      */       
/* 2319 */       return new Object[] { "UTF-16LE", new Boolean(false) };
/*      */     }
/* 2321 */     if (b0 == 76 && b1 == 111 && b2 == 167 && b3 == 148)
/*      */     {
/*      */       
/* 2324 */       return new Object[] { "CP037", null };
/*      */     }
/*      */     
/* 2327 */     return this.defaultEncoding;
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
/*      */   protected Reader createReader(InputStream inputStream, String encoding, Boolean isBigEndian) throws IOException {
/* 2351 */     if (encoding == null) {
/* 2352 */       encoding = "UTF-8";
/*      */     }
/*      */ 
/*      */     
/* 2356 */     String ENCODING = encoding.toUpperCase(Locale.ENGLISH);
/* 2357 */     if (ENCODING.equals("UTF-8"))
/*      */     {
/*      */ 
/*      */       
/* 2361 */       return new UTF8Reader(inputStream, this.fBufferSize, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
/*      */     }
/* 2363 */     if (ENCODING.equals("US-ASCII"))
/*      */     {
/*      */ 
/*      */       
/* 2367 */       return new ASCIIReader(inputStream, this.fBufferSize, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
/*      */     }
/* 2369 */     if (ENCODING.equals("ISO-10646-UCS-4")) {
/* 2370 */       if (isBigEndian != null) {
/* 2371 */         boolean isBE = isBigEndian.booleanValue();
/* 2372 */         if (isBE) {
/* 2373 */           return new UCSReader(inputStream, (short)8);
/*      */         }
/* 2375 */         return new UCSReader(inputStream, (short)4);
/*      */       } 
/*      */       
/* 2378 */       this.fErrorReporter.reportError(getEntityScanner(), "http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[] { encoding }, (short)2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2384 */     if (ENCODING.equals("ISO-10646-UCS-2")) {
/* 2385 */       if (isBigEndian != null) {
/* 2386 */         boolean isBE = isBigEndian.booleanValue();
/* 2387 */         if (isBE) {
/* 2388 */           return new UCSReader(inputStream, (short)2);
/*      */         }
/* 2390 */         return new UCSReader(inputStream, (short)1);
/*      */       } 
/*      */       
/* 2393 */       this.fErrorReporter.reportError(getEntityScanner(), "http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingByteOrderUnsupported", new Object[] { encoding }, (short)2);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2401 */     boolean validIANA = XMLChar.isValidIANAEncoding(encoding);
/* 2402 */     boolean validJava = XMLChar.isValidJavaEncoding(encoding);
/* 2403 */     if (!validIANA || (this.fAllowJavaEncodings && !validJava)) {
/* 2404 */       this.fErrorReporter.reportError(getEntityScanner(), "http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[] { encoding }, (short)2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2416 */       encoding = "ISO-8859-1";
/*      */     } 
/*      */ 
/*      */     
/* 2420 */     String javaEncoding = EncodingMap.getIANA2JavaMapping(ENCODING);
/* 2421 */     if (javaEncoding == null) {
/* 2422 */       if (this.fAllowJavaEncodings) {
/* 2423 */         javaEncoding = encoding;
/*      */       } else {
/* 2425 */         this.fErrorReporter.reportError(getEntityScanner(), "http://www.w3.org/TR/1998/REC-xml-19980210", "EncodingDeclInvalid", new Object[] { encoding }, (short)2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2430 */         javaEncoding = "ISO8859_1";
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2440 */     return new BufferedReader(new InputStreamReader(inputStream, javaEncoding));
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
/*      */   public String getPublicId() {
/* 2456 */     return (this.fCurrentEntity != null && this.fCurrentEntity.entityLocation != null) ? this.fCurrentEntity.entityLocation.getPublicId() : null;
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
/*      */   public String getExpandedSystemId() {
/* 2473 */     if (this.fCurrentEntity != null) {
/* 2474 */       if (this.fCurrentEntity.entityLocation != null && this.fCurrentEntity.entityLocation
/* 2475 */         .getExpandedSystemId() != null) {
/* 2476 */         return this.fCurrentEntity.entityLocation.getExpandedSystemId();
/*      */       }
/*      */       
/* 2479 */       int size = this.fEntityStack.size();
/* 2480 */       for (int i = size - 1; i >= 0; i--) {
/*      */         
/* 2482 */         Entity.ScannedEntity externalEntity = (Entity.ScannedEntity)this.fEntityStack.elementAt(i);
/*      */         
/* 2484 */         if (externalEntity.entityLocation != null && externalEntity.entityLocation
/* 2485 */           .getExpandedSystemId() != null) {
/* 2486 */           return externalEntity.entityLocation.getExpandedSystemId();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2491 */     return null;
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
/*      */   public String getLiteralSystemId() {
/* 2505 */     if (this.fCurrentEntity != null) {
/* 2506 */       if (this.fCurrentEntity.entityLocation != null && this.fCurrentEntity.entityLocation
/* 2507 */         .getLiteralSystemId() != null) {
/* 2508 */         return this.fCurrentEntity.entityLocation.getLiteralSystemId();
/*      */       }
/*      */       
/* 2511 */       int size = this.fEntityStack.size();
/* 2512 */       for (int i = size - 1; i >= 0; i--) {
/*      */         
/* 2514 */         Entity.ScannedEntity externalEntity = (Entity.ScannedEntity)this.fEntityStack.elementAt(i);
/*      */         
/* 2516 */         if (externalEntity.entityLocation != null && externalEntity.entityLocation
/* 2517 */           .getLiteralSystemId() != null) {
/* 2518 */           return externalEntity.entityLocation.getLiteralSystemId();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2523 */     return null;
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
/*      */   public int getLineNumber() {
/* 2545 */     if (this.fCurrentEntity != null) {
/* 2546 */       if (this.fCurrentEntity.isExternal()) {
/* 2547 */         return this.fCurrentEntity.lineNumber;
/*      */       }
/*      */       
/* 2550 */       int size = this.fEntityStack.size();
/* 2551 */       for (int i = size - 1; i > 0; i--) {
/* 2552 */         Entity.ScannedEntity firstExternalEntity = (Entity.ScannedEntity)this.fEntityStack.elementAt(i);
/* 2553 */         if (firstExternalEntity.isExternal()) {
/* 2554 */           return firstExternalEntity.lineNumber;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2560 */     return -1;
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
/*      */   public int getColumnNumber() {
/* 2587 */     if (this.fCurrentEntity != null) {
/* 2588 */       if (this.fCurrentEntity.isExternal()) {
/* 2589 */         return this.fCurrentEntity.columnNumber;
/*      */       }
/*      */       
/* 2592 */       int size = this.fEntityStack.size();
/* 2593 */       for (int i = size - 1; i > 0; i--) {
/* 2594 */         Entity.ScannedEntity firstExternalEntity = (Entity.ScannedEntity)this.fEntityStack.elementAt(i);
/* 2595 */         if (firstExternalEntity.isExternal()) {
/* 2596 */           return firstExternalEntity.columnNumber;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2602 */     return -1;
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
/*      */   protected static String fixURI(String str) {
/* 2620 */     str = str.replace(File.separatorChar, '/');
/*      */ 
/*      */     
/* 2623 */     if (str.length() >= 2) {
/* 2624 */       char ch1 = str.charAt(1);
/*      */       
/* 2626 */       if (ch1 == ':') {
/* 2627 */         char ch0 = Character.toUpperCase(str.charAt(0));
/* 2628 */         if (ch0 >= 'A' && ch0 <= 'Z') {
/* 2629 */           str = "/" + str;
/*      */         
/*      */         }
/*      */       }
/* 2633 */       else if (ch1 == '/' && str.charAt(0) == '/') {
/* 2634 */         str = "file:" + str;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2642 */     int pos = str.indexOf(' ');
/* 2643 */     if (pos >= 0) {
/* 2644 */       StringBuilder sb = new StringBuilder(str.length());
/*      */       int i;
/* 2646 */       for (i = 0; i < pos; i++) {
/* 2647 */         sb.append(str.charAt(i));
/*      */       }
/* 2649 */       sb.append("%20");
/*      */       
/* 2651 */       for (i = pos + 1; i < str.length(); i++) {
/* 2652 */         if (str.charAt(i) == ' ') {
/* 2653 */           sb.append("%20");
/*      */         } else {
/* 2655 */           sb.append(str.charAt(i));
/*      */         } 
/* 2657 */       }  str = sb.toString();
/*      */     } 
/*      */ 
/*      */     
/* 2661 */     return str;
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
/*      */   final void print() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CharacterBuffer
/*      */   {
/*      */     private char[] ch;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isExternal;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CharacterBuffer(boolean isExternal, int size) {
/* 2739 */       this.isExternal = isExternal;
/* 2740 */       this.ch = new char[size];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CharacterBufferPool
/*      */   {
/*      */     private static final int DEFAULT_POOL_SIZE = 3;
/*      */ 
/*      */     
/*      */     private XMLEntityManager.CharacterBuffer[] fInternalBufferPool;
/*      */ 
/*      */     
/*      */     private XMLEntityManager.CharacterBuffer[] fExternalBufferPool;
/*      */     
/*      */     private int fExternalBufferSize;
/*      */     
/*      */     private int fInternalBufferSize;
/*      */     
/*      */     private int poolSize;
/*      */     
/*      */     private int fInternalTop;
/*      */     
/*      */     private int fExternalTop;
/*      */ 
/*      */     
/*      */     public CharacterBufferPool(int externalBufferSize, int internalBufferSize) {
/* 2768 */       this(3, externalBufferSize, internalBufferSize);
/*      */     }
/*      */     
/*      */     public CharacterBufferPool(int poolSize, int externalBufferSize, int internalBufferSize) {
/* 2772 */       this.fExternalBufferSize = externalBufferSize;
/* 2773 */       this.fInternalBufferSize = internalBufferSize;
/* 2774 */       this.poolSize = poolSize;
/* 2775 */       init();
/*      */     }
/*      */ 
/*      */     
/*      */     private void init() {
/* 2780 */       this.fInternalBufferPool = new XMLEntityManager.CharacterBuffer[this.poolSize];
/* 2781 */       this.fExternalBufferPool = new XMLEntityManager.CharacterBuffer[this.poolSize];
/* 2782 */       this.fInternalTop = -1;
/* 2783 */       this.fExternalTop = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public XMLEntityManager.CharacterBuffer getBuffer(boolean external) {
/* 2788 */       if (external) {
/* 2789 */         if (this.fExternalTop > -1) {
/* 2790 */           return this.fExternalBufferPool[this.fExternalTop--];
/*      */         }
/*      */         
/* 2793 */         return new XMLEntityManager.CharacterBuffer(true, this.fExternalBufferSize);
/*      */       } 
/*      */ 
/*      */       
/* 2797 */       if (this.fInternalTop > -1) {
/* 2798 */         return this.fInternalBufferPool[this.fInternalTop--];
/*      */       }
/*      */       
/* 2801 */       return new XMLEntityManager.CharacterBuffer(false, this.fInternalBufferSize);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void returnToPool(XMLEntityManager.CharacterBuffer buffer) {
/* 2808 */       if (buffer.isExternal) {
/* 2809 */         if (this.fExternalTop < this.fExternalBufferPool.length - 1) {
/* 2810 */           this.fExternalBufferPool[++this.fExternalTop] = buffer;
/*      */         }
/*      */       }
/* 2813 */       else if (this.fInternalTop < this.fInternalBufferPool.length - 1) {
/* 2814 */         this.fInternalBufferPool[++this.fInternalTop] = buffer;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setExternalBufferSize(int bufferSize) {
/* 2820 */       this.fExternalBufferSize = bufferSize;
/* 2821 */       this.fExternalBufferPool = new XMLEntityManager.CharacterBuffer[this.poolSize];
/* 2822 */       this.fExternalTop = -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final class RewindableInputStream
/*      */     extends InputStream
/*      */   {
/*      */     private InputStream fInputStream;
/*      */ 
/*      */ 
/*      */     
/*      */     private byte[] fData;
/*      */ 
/*      */ 
/*      */     
/*      */     private int fStartOffset;
/*      */ 
/*      */ 
/*      */     
/*      */     private int fEndOffset;
/*      */ 
/*      */ 
/*      */     
/*      */     private int fOffset;
/*      */ 
/*      */ 
/*      */     
/*      */     private int fLength;
/*      */ 
/*      */     
/*      */     private int fMark;
/*      */ 
/*      */ 
/*      */     
/*      */     public RewindableInputStream(InputStream is) {
/* 2860 */       this.fData = new byte[64];
/* 2861 */       this.fInputStream = is;
/* 2862 */       this.fStartOffset = 0;
/* 2863 */       this.fEndOffset = -1;
/* 2864 */       this.fOffset = 0;
/* 2865 */       this.fLength = 0;
/* 2866 */       this.fMark = 0;
/*      */     }
/*      */     
/*      */     public void setStartOffset(int offset) {
/* 2870 */       this.fStartOffset = offset;
/*      */     }
/*      */     
/*      */     public void rewind() {
/* 2874 */       this.fOffset = this.fStartOffset;
/*      */     }
/*      */     
/*      */     public int read() throws IOException {
/* 2878 */       int b = 0;
/* 2879 */       if (this.fOffset < this.fLength) {
/* 2880 */         return this.fData[this.fOffset++] & 0xFF;
/*      */       }
/* 2882 */       if (this.fOffset == this.fEndOffset) {
/* 2883 */         return -1;
/*      */       }
/* 2885 */       if (this.fOffset == this.fData.length) {
/* 2886 */         byte[] newData = new byte[this.fOffset << 1];
/* 2887 */         System.arraycopy(this.fData, 0, newData, 0, this.fOffset);
/* 2888 */         this.fData = newData;
/*      */       } 
/* 2890 */       b = this.fInputStream.read();
/* 2891 */       if (b == -1) {
/* 2892 */         this.fEndOffset = this.fOffset;
/* 2893 */         return -1;
/*      */       } 
/* 2895 */       this.fData[this.fLength++] = (byte)b;
/* 2896 */       this.fOffset++;
/* 2897 */       return b & 0xFF;
/*      */     }
/*      */     
/*      */     public int read(byte[] b, int off, int len) throws IOException {
/* 2901 */       int bytesLeft = this.fLength - this.fOffset;
/* 2902 */       if (bytesLeft == 0) {
/* 2903 */         if (this.fOffset == this.fEndOffset) {
/* 2904 */           return -1;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2912 */         if (XMLEntityManager.this.fCurrentEntity.mayReadChunks || !XMLEntityManager.this.fCurrentEntity.xmlDeclChunkRead) {
/*      */           
/* 2914 */           if (!XMLEntityManager.this.fCurrentEntity.xmlDeclChunkRead) {
/*      */             
/* 2916 */             XMLEntityManager.this.fCurrentEntity.xmlDeclChunkRead = true;
/* 2917 */             len = 28;
/*      */           } 
/* 2919 */           return this.fInputStream.read(b, off, len);
/*      */         } 
/*      */         
/* 2922 */         int returnedVal = read();
/* 2923 */         if (returnedVal == -1) {
/* 2924 */           this.fEndOffset = this.fOffset;
/* 2925 */           return -1;
/*      */         } 
/* 2927 */         b[off] = (byte)returnedVal;
/* 2928 */         return 1;
/*      */       } 
/*      */       
/* 2931 */       if (len < bytesLeft) {
/* 2932 */         if (len <= 0) {
/* 2933 */           return 0;
/*      */         }
/*      */       } else {
/* 2936 */         len = bytesLeft;
/*      */       } 
/* 2938 */       if (b != null) {
/* 2939 */         System.arraycopy(this.fData, this.fOffset, b, off, len);
/*      */       }
/* 2941 */       this.fOffset += len;
/* 2942 */       return len;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) throws IOException {
/* 2948 */       if (n <= 0L) {
/* 2949 */         return 0L;
/*      */       }
/* 2951 */       int bytesLeft = this.fLength - this.fOffset;
/* 2952 */       if (bytesLeft == 0) {
/* 2953 */         if (this.fOffset == this.fEndOffset) {
/* 2954 */           return 0L;
/*      */         }
/* 2956 */         return this.fInputStream.skip(n);
/*      */       } 
/* 2958 */       if (n <= bytesLeft) {
/* 2959 */         this.fOffset = (int)(this.fOffset + n);
/* 2960 */         return n;
/*      */       } 
/* 2962 */       this.fOffset += bytesLeft;
/* 2963 */       if (this.fOffset == this.fEndOffset) {
/* 2964 */         return bytesLeft;
/*      */       }
/* 2966 */       n -= bytesLeft;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2975 */       return this.fInputStream.skip(n) + bytesLeft;
/*      */     }
/*      */     
/*      */     public int available() throws IOException {
/* 2979 */       int bytesLeft = this.fLength - this.fOffset;
/* 2980 */       if (bytesLeft == 0) {
/* 2981 */         if (this.fOffset == this.fEndOffset) {
/* 2982 */           return -1;
/*      */         }
/* 2984 */         return XMLEntityManager.this.fCurrentEntity.mayReadChunks ? this.fInputStream.available() : 0;
/*      */       } 
/*      */       
/* 2987 */       return bytesLeft;
/*      */     }
/*      */     
/*      */     public void mark(int howMuch) {
/* 2991 */       this.fMark = this.fOffset;
/*      */     }
/*      */     
/*      */     public void reset() {
/* 2995 */       this.fOffset = this.fMark;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean markSupported() {
/* 3000 */       return true;
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 3004 */       if (this.fInputStream != null) {
/* 3005 */         this.fInputStream.close();
/* 3006 */         this.fInputStream = null;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void test() {
/* 3014 */     this.fEntityStorage.addExternalEntity("entityUsecase1", null, "/space/home/stax/sun/6thJan2004/zephyr/data/test.txt", "/space/home/stax/sun/6thJan2004/zephyr/data/entity.xml");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3019 */     this.fEntityStorage.addInternalEntity("entityUsecase2", "<Test>value</Test>");
/* 3020 */     this.fEntityStorage.addInternalEntity("entityUsecase3", "value3");
/* 3021 */     this.fEntityStorage.addInternalEntity("text", "Hello World.");
/* 3022 */     this.fEntityStorage.addInternalEntity("empty-element", "<foo/>");
/* 3023 */     this.fEntityStorage.addInternalEntity("balanced-element", "<foo></foo>");
/* 3024 */     this.fEntityStorage.addInternalEntity("balanced-element-with-text", "<foo>Hello, World</foo>");
/* 3025 */     this.fEntityStorage.addInternalEntity("balanced-element-with-entity", "<foo>&text;</foo>");
/* 3026 */     this.fEntityStorage.addInternalEntity("unbalanced-entity", "<foo>");
/* 3027 */     this.fEntityStorage.addInternalEntity("recursive-entity", "<foo>&recursive-entity2;</foo>");
/* 3028 */     this.fEntityStorage.addInternalEntity("recursive-entity2", "<bar>&recursive-entity3;</bar>");
/* 3029 */     this.fEntityStorage.addInternalEntity("recursive-entity3", "<baz>&recursive-entity;</baz>");
/* 3030 */     this.fEntityStorage.addInternalEntity("ch", "&#x00A9;");
/* 3031 */     this.fEntityStorage.addInternalEntity("ch1", "&#84;");
/* 3032 */     this.fEntityStorage.addInternalEntity("% ch2", "param");
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XMLEntityManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */