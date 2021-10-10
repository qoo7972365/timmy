/*      */ package com.sun.org.apache.xerces.internal.impl.xs.traversers;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*      */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.SchemaDVFactory;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaGrammar;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaException;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaLoader;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSAttributeDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSAttributeGroupDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSComplexTypeDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSDDescription;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSDeclarationPool;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSElementDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSGrammarBucket;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSGroupDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSModelGroupImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSNotationDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.XSParticleDecl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.identity.IdentityConstraint;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.opti.ElementImpl;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOM;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaDOMParser;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.opti.SchemaParsingConfig;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.SimpleLocator;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.XSInputSource;
/*      */ import com.sun.org.apache.xerces.internal.parsers.XML11Configuration;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMInputSource;
/*      */ import com.sun.org.apache.xerces.internal.util.DOMUtil;
/*      */ import com.sun.org.apache.xerces.internal.util.DefaultErrorHandler;
/*      */ import com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.SAXInputSource;
/*      */ import com.sun.org.apache.xerces.internal.util.StAXInputSource;
/*      */ import com.sun.org.apache.xerces.internal.util.StAXLocationWrapper;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolHash;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.URI;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLSchemaDescription;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
/*      */ import com.sun.org.apache.xerces.internal.xs.StringList;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSAttributeDeclaration;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSAttributeGroupDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSAttributeUse;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSModelGroup;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSModelGroupDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSNamedMap;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSObject;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSObjectList;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSParticle;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSTerm;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*      */ import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import java.util.Vector;
/*      */ import javax.xml.stream.XMLEventReader;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import jdk.xml.internal.JdkXmlUtils;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.xml.sax.InputSource;
/*      */ import org.xml.sax.SAXException;
/*      */ import org.xml.sax.SAXNotRecognizedException;
/*      */ import org.xml.sax.SAXParseException;
/*      */ import org.xml.sax.XMLReader;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XSDHandler
/*      */ {
/*      */   protected static final String VALIDATION = "http://xml.org/sax/features/validation";
/*      */   protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
/*      */   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
/*      */   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
/*      */   protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
/*      */   protected static final String DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
/*      */   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
/*      */   protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
/*      */   protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
/*      */   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
/*      */   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
/*      */   private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
/*      */   protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
/*      */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*      */   protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
/*      */   public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
/*      */   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
/*      */   public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
/*      */   public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
/*      */   public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
/*      */   protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
/*      */   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
/*      */   private static final String XML_SECURITY_PROPERTY_MANAGER = "http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager";
/*      */   protected static final boolean DEBUG_NODE_POOL = false;
/*      */   static final int ATTRIBUTE_TYPE = 1;
/*      */   static final int ATTRIBUTEGROUP_TYPE = 2;
/*      */   static final int ELEMENT_TYPE = 3;
/*      */   static final int GROUP_TYPE = 4;
/*      */   static final int IDENTITYCONSTRAINT_TYPE = 5;
/*      */   static final int NOTATION_TYPE = 6;
/*      */   static final int TYPEDECL_TYPE = 7;
/*      */   public static final String REDEF_IDENTIFIER = "_fn3dktizrknc9pi";
/*  247 */   protected XSDeclarationPool fDeclPool = null;
/*      */ 
/*      */   
/*  250 */   protected XMLSecurityManager fSecurityManager = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private String fAccessExternalSchema;
/*      */ 
/*      */   
/*      */   private String fAccessExternalDTD;
/*      */ 
/*      */   
/*      */   private boolean registryEmpty = true;
/*      */ 
/*      */   
/*  263 */   private Map<String, Element> fUnparsedAttributeRegistry = new HashMap<>();
/*  264 */   private Map<String, Element> fUnparsedAttributeGroupRegistry = new HashMap<>();
/*  265 */   private Map<String, Element> fUnparsedElementRegistry = new HashMap<>();
/*  266 */   private Map<String, Element> fUnparsedGroupRegistry = new HashMap<>();
/*  267 */   private Map<String, Element> fUnparsedIdentityConstraintRegistry = new HashMap<>();
/*  268 */   private Map<String, Element> fUnparsedNotationRegistry = new HashMap<>();
/*  269 */   private Map<String, Element> fUnparsedTypeRegistry = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*  273 */   private Map<String, XSDocumentInfo> fUnparsedAttributeRegistrySub = new HashMap<>();
/*  274 */   private Map<String, XSDocumentInfo> fUnparsedAttributeGroupRegistrySub = new HashMap<>();
/*  275 */   private Map<String, XSDocumentInfo> fUnparsedElementRegistrySub = new HashMap<>();
/*  276 */   private Map<String, XSDocumentInfo> fUnparsedGroupRegistrySub = new HashMap<>();
/*  277 */   private Map<String, XSDocumentInfo> fUnparsedIdentityConstraintRegistrySub = new HashMap<>();
/*  278 */   private Map<String, XSDocumentInfo> fUnparsedNotationRegistrySub = new HashMap<>();
/*  279 */   private Map<String, XSDocumentInfo> fUnparsedTypeRegistrySub = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*  283 */   private Map<String, XSDocumentInfo>[] fUnparsedRegistriesExt = new HashMap[] { null, null, null, null, null, null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  298 */   private Map<XSDocumentInfo, Vector<XSDocumentInfo>> fDependencyMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  304 */   private Map<String, Vector> fImportMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  309 */   private Vector<String> fAllTNSs = new Vector<>();
/*      */ 
/*      */   
/*  312 */   private Map<String, XMLSchemaLoader.LocationArray> fLocationPairs = null;
/*      */ 
/*      */   
/*  315 */   Map<Node, String> fHiddenNodes = null;
/*      */ 
/*      */   
/*      */   private String null2EmptyString(String ns) {
/*  319 */     return (ns == null) ? XMLSymbols.EMPTY_STRING : ns;
/*      */   }
/*      */   private String emptyString2Null(String ns) {
/*  322 */     return (ns == XMLSymbols.EMPTY_STRING) ? null : ns;
/*      */   }
/*      */   
/*      */   private String doc2SystemId(Element ele) {
/*  326 */     String documentURI = null;
/*      */ 
/*      */ 
/*      */     
/*  330 */     if (ele.getOwnerDocument() instanceof SchemaDOM) {
/*  331 */       documentURI = ((SchemaDOM)ele.getOwnerDocument()).getDocumentURI();
/*      */     }
/*  333 */     return (documentURI != null) ? documentURI : this.fDoc2SystemId.get(ele);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  341 */   private Map<XSDKey, Element> fTraversed = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*  345 */   private Map<Element, String> fDoc2SystemId = new HashMap<>();
/*      */ 
/*      */   
/*  348 */   private XSDocumentInfo fRoot = null;
/*      */ 
/*      */ 
/*      */   
/*  352 */   private Map fDoc2XSDocumentMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*  356 */   private Map fRedefine2XSDMap = null;
/*      */ 
/*      */   
/*  359 */   private Map fRedefine2NSSupport = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  366 */   private Map fRedefinedRestrictedAttributeGroupRegistry = new HashMap<>();
/*  367 */   private Map fRedefinedRestrictedGroupRegistry = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fLastSchemaWasDuplicate;
/*      */ 
/*      */   
/*      */   private boolean fValidateAnnotations = false;
/*      */ 
/*      */   
/*      */   private boolean fHonourAllSchemaLocations = false;
/*      */ 
/*      */   
/*      */   boolean fNamespaceGrowth = false;
/*      */ 
/*      */   
/*      */   boolean fTolerateDuplicates = false;
/*      */ 
/*      */   
/*      */   private XMLErrorReporter fErrorReporter;
/*      */ 
/*      */   
/*      */   private XMLErrorHandler fErrorHandler;
/*      */ 
/*      */   
/*      */   private Locale fLocale;
/*      */ 
/*      */   
/*      */   private XMLEntityResolver fEntityManager;
/*      */ 
/*      */   
/*      */   private XSAttributeChecker fAttributeChecker;
/*      */ 
/*      */   
/*      */   private SymbolTable fSymbolTable;
/*      */ 
/*      */   
/*      */   private XSGrammarBucket fGrammarBucket;
/*      */ 
/*      */   
/*      */   private XSDDescription fSchemaGrammarDescription;
/*      */ 
/*      */   
/*      */   private XMLGrammarPool fGrammarPool;
/*      */ 
/*      */   
/*  413 */   private XMLSecurityPropertyManager fSecurityPropertyMgr = null;
/*      */   
/*      */   private boolean fOverrideDefaultParser;
/*      */   
/*      */   XSDAttributeGroupTraverser fAttributeGroupTraverser;
/*      */   
/*      */   XSDAttributeTraverser fAttributeTraverser;
/*      */   
/*      */   XSDComplexTypeTraverser fComplexTypeTraverser;
/*      */   
/*      */   XSDElementTraverser fElementTraverser;
/*      */   
/*      */   XSDGroupTraverser fGroupTraverser;
/*      */   
/*      */   XSDKeyrefTraverser fKeyrefTraverser;
/*      */   
/*      */   XSDNotationTraverser fNotationTraverser;
/*      */   
/*      */   XSDSimpleTypeTraverser fSimpleTypeTraverser;
/*      */   
/*      */   XSDUniqueOrKeyTraverser fUniqueOrKeyTraverser;
/*      */   
/*      */   XSDWildcardTraverser fWildCardTraverser;
/*      */   SchemaDVFactory fDVFactory;
/*      */   SchemaDOMParser fSchemaParser;
/*      */   SchemaContentHandler fXSContentHandler;
/*      */   StAXSchemaParser fStAXSchemaParser;
/*      */   XML11Configuration fAnnotationValidator;
/*      */   XSAnnotationGrammarPool fGrammarBucketAdapter;
/*      */   private static final int INIT_STACK_SIZE = 30;
/*      */   private static final int INC_STACK_SIZE = 10;
/*  444 */   private int fLocalElemStackPos = 0;
/*      */   
/*  446 */   private XSParticleDecl[] fParticle = new XSParticleDecl[30];
/*  447 */   private Element[] fLocalElementDecl = new Element[30];
/*  448 */   private XSDocumentInfo[] fLocalElementDecl_schema = new XSDocumentInfo[30];
/*  449 */   private int[] fAllContext = new int[30];
/*  450 */   private XSObject[] fParent = new XSObject[30];
/*  451 */   private String[][] fLocalElemNamespaceContext = new String[30][1];
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int INIT_KEYREF_STACK = 2;
/*      */ 
/*      */   
/*      */   private static final int INC_KEYREF_STACK_AMOUNT = 2;
/*      */ 
/*      */   
/*  461 */   private int fKeyrefStackPos = 0;
/*      */   
/*  463 */   private Element[] fKeyrefs = new Element[2];
/*  464 */   private XSDocumentInfo[] fKeyrefsMapXSDocumentInfo = new XSDocumentInfo[2];
/*  465 */   private XSElementDecl[] fKeyrefElems = new XSElementDecl[2];
/*  466 */   private String[][] fKeyrefNamespaceContext = new String[2][1];
/*      */ 
/*      */   
/*  469 */   SymbolHash fGlobalAttrDecls = new SymbolHash(12);
/*  470 */   SymbolHash fGlobalAttrGrpDecls = new SymbolHash(5);
/*  471 */   SymbolHash fGlobalElemDecls = new SymbolHash(25);
/*  472 */   SymbolHash fGlobalGroupDecls = new SymbolHash(5);
/*  473 */   SymbolHash fGlobalNotationDecls = new SymbolHash(1);
/*  474 */   SymbolHash fGlobalIDConstraintDecls = new SymbolHash(3);
/*  475 */   SymbolHash fGlobalTypeDecls = new SymbolHash(25);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XSDHandler(XSGrammarBucket gBucket) {
/*  487 */     this();
/*  488 */     this.fGrammarBucket = gBucket;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  493 */     this.fSchemaGrammarDescription = new XSDDescription();
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
/*      */   public SchemaGrammar parseSchema(XMLInputSource is, XSDDescription desc, Map<String, XMLSchemaLoader.LocationArray> locationPairs) throws IOException {
/*  510 */     this.fLocationPairs = locationPairs;
/*  511 */     this.fSchemaParser.resetNodePool();
/*  512 */     SchemaGrammar grammar = null;
/*  513 */     String schemaNamespace = null;
/*  514 */     short referType = desc.getContextType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  522 */     if (referType != 3) {
/*      */       
/*  524 */       if (this.fHonourAllSchemaLocations && referType == 2 && isExistingGrammar(desc, this.fNamespaceGrowth)) {
/*  525 */         grammar = this.fGrammarBucket.getGrammar(desc.getTargetNamespace());
/*      */       } else {
/*      */         
/*  528 */         grammar = findGrammar(desc, this.fNamespaceGrowth);
/*      */       } 
/*  530 */       if (grammar != null) {
/*  531 */         if (!this.fNamespaceGrowth) {
/*  532 */           return grammar;
/*      */         }
/*      */         
/*      */         try {
/*  536 */           if (grammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(is.getSystemId(), is.getBaseSystemId(), false))) {
/*  537 */             return grammar;
/*      */           }
/*      */         }
/*  540 */         catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  546 */       schemaNamespace = desc.getTargetNamespace();
/*      */       
/*  548 */       if (schemaNamespace != null) {
/*  549 */         schemaNamespace = this.fSymbolTable.addSymbol(schemaNamespace);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  555 */     prepareForParse();
/*      */     
/*  557 */     Element schemaRoot = null;
/*      */     
/*  559 */     if (is instanceof DOMInputSource) {
/*  560 */       schemaRoot = getSchemaDocument(schemaNamespace, (DOMInputSource)is, (referType == 3), referType, (Element)null);
/*      */ 
/*      */     
/*      */     }
/*  564 */     else if (is instanceof SAXInputSource) {
/*  565 */       schemaRoot = getSchemaDocument(schemaNamespace, (SAXInputSource)is, (referType == 3), referType, (Element)null);
/*      */ 
/*      */     
/*      */     }
/*  569 */     else if (is instanceof StAXInputSource) {
/*  570 */       schemaRoot = getSchemaDocument(schemaNamespace, (StAXInputSource)is, (referType == 3), referType, (Element)null);
/*      */ 
/*      */     
/*      */     }
/*  574 */     else if (is instanceof XSInputSource) {
/*  575 */       schemaRoot = getSchemaDocument((XSInputSource)is, desc);
/*      */     } else {
/*      */       
/*  578 */       schemaRoot = getSchemaDocument(schemaNamespace, is, (referType == 3), referType, (Element)null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  584 */     if (schemaRoot == null) {
/*      */       
/*  586 */       if (is instanceof XSInputSource) {
/*  587 */         return this.fGrammarBucket.getGrammar(desc.getTargetNamespace());
/*      */       }
/*  589 */       return grammar;
/*      */     } 
/*      */     
/*  592 */     if (referType == 3) {
/*  593 */       Element schemaElem = schemaRoot;
/*  594 */       schemaNamespace = DOMUtil.getAttrValue(schemaElem, SchemaSymbols.ATT_TARGETNAMESPACE);
/*  595 */       if (schemaNamespace != null && schemaNamespace.length() > 0) {
/*      */ 
/*      */         
/*  598 */         schemaNamespace = this.fSymbolTable.addSymbol(schemaNamespace);
/*  599 */         desc.setTargetNamespace(schemaNamespace);
/*      */       } else {
/*      */         
/*  602 */         schemaNamespace = null;
/*      */       } 
/*  604 */       grammar = findGrammar(desc, this.fNamespaceGrowth);
/*  605 */       String schemaId = XMLEntityManager.expandSystemId(is.getSystemId(), is.getBaseSystemId(), false);
/*  606 */       if (grammar != null)
/*      */       {
/*      */         
/*  609 */         if (!this.fNamespaceGrowth || (schemaId != null && grammar.getDocumentLocations().contains(schemaId))) {
/*  610 */           return grammar;
/*      */         }
/*      */       }
/*      */       
/*  614 */       XSDKey key = new XSDKey(schemaId, referType, schemaNamespace);
/*  615 */       this.fTraversed.put(key, schemaRoot);
/*  616 */       if (schemaId != null) {
/*  617 */         this.fDoc2SystemId.put(schemaRoot, schemaId);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  623 */     prepareForTraverse();
/*      */     
/*  625 */     this.fRoot = constructTrees(schemaRoot, is.getSystemId(), desc, (grammar != null));
/*  626 */     if (this.fRoot == null) {
/*  627 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  631 */     buildGlobalNameRegistries();
/*      */ 
/*      */     
/*  634 */     ArrayList annotationInfo = this.fValidateAnnotations ? new ArrayList() : null;
/*  635 */     traverseSchemas(annotationInfo);
/*      */ 
/*      */     
/*  638 */     traverseLocalElements();
/*      */ 
/*      */     
/*  641 */     resolveKeyRefs();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  649 */     for (int i = this.fAllTNSs.size() - 1; i >= 0; i--) {
/*      */       
/*  651 */       String tns = this.fAllTNSs.elementAt(i);
/*      */       
/*  653 */       Vector<String> ins = this.fImportMap.get(tns);
/*      */       
/*  655 */       SchemaGrammar sg = this.fGrammarBucket.getGrammar(emptyString2Null(tns));
/*  656 */       if (sg != null) {
/*      */ 
/*      */ 
/*      */         
/*  660 */         int count = 0;
/*  661 */         for (int j = 0; j < ins.size(); j++) {
/*      */           
/*  663 */           SchemaGrammar isg = this.fGrammarBucket.getGrammar(ins.elementAt(j));
/*      */           
/*  665 */           if (isg != null)
/*  666 */             ins.setElementAt(isg, count++); 
/*      */         } 
/*  668 */         ins.setSize(count);
/*      */         
/*  670 */         sg.setImportedGrammars(ins);
/*      */       } 
/*      */     } 
/*      */     
/*  674 */     if (this.fValidateAnnotations && annotationInfo.size() > 0) {
/*  675 */       validateAnnotations(annotationInfo);
/*      */     }
/*      */ 
/*      */     
/*  679 */     return this.fGrammarBucket.getGrammar(this.fRoot.fTargetNamespace);
/*      */   }
/*      */   
/*      */   private void validateAnnotations(ArrayList<String> annotationInfo) {
/*  683 */     if (this.fAnnotationValidator == null) {
/*  684 */       createAnnotationValidator();
/*      */     }
/*  686 */     int size = annotationInfo.size();
/*  687 */     XMLInputSource src = new XMLInputSource(null, null, null);
/*  688 */     this.fGrammarBucketAdapter.refreshGrammars(this.fGrammarBucket);
/*  689 */     for (int i = 0; i < size; i += 2) {
/*  690 */       src.setSystemId(annotationInfo.get(i));
/*  691 */       XSAnnotationInfo annotation = (XSAnnotationInfo)annotationInfo.get(i + 1);
/*  692 */       while (annotation != null) {
/*  693 */         src.setCharacterStream(new StringReader(annotation.fAnnotation));
/*      */         try {
/*  695 */           this.fAnnotationValidator.parse(src);
/*      */         }
/*  697 */         catch (IOException iOException) {}
/*  698 */         annotation = annotation.next;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createAnnotationValidator() {
/*  704 */     this.fAnnotationValidator = new XML11Configuration();
/*  705 */     this.fGrammarBucketAdapter = new XSAnnotationGrammarPool();
/*  706 */     this.fAnnotationValidator.setFeature("http://xml.org/sax/features/validation", true);
/*  707 */     this.fAnnotationValidator.setFeature("http://apache.org/xml/features/validation/schema", true);
/*  708 */     this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarBucketAdapter);
/*      */     
/*  710 */     this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/security-manager", (this.fSecurityManager != null) ? this.fSecurityManager : new XMLSecurityManager(true));
/*  711 */     this.fAnnotationValidator.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr);
/*      */     
/*  713 */     this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/error-handler", (this.fErrorHandler != null) ? this.fErrorHandler : new DefaultErrorHandler());
/*      */     
/*  715 */     this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/locale", this.fLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SchemaGrammar getGrammar(String tns) {
/*  723 */     return this.fGrammarBucket.getGrammar(tns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SchemaGrammar findGrammar(XSDDescription desc, boolean ignoreConflict) {
/*  732 */     SchemaGrammar sg = this.fGrammarBucket.getGrammar(desc.getTargetNamespace());
/*  733 */     if (sg == null && 
/*  734 */       this.fGrammarPool != null) {
/*  735 */       sg = (SchemaGrammar)this.fGrammarPool.retrieveGrammar(desc);
/*  736 */       if (sg != null)
/*      */       {
/*      */         
/*  739 */         if (!this.fGrammarBucket.putGrammar(sg, true, ignoreConflict)) {
/*      */ 
/*      */           
/*  742 */           reportSchemaWarning("GrammarConflict", null, null);
/*  743 */           sg = null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  748 */     return sg;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  754 */   private static final String[][] NS_ERROR_CODES = new String[][] { { "src-include.2.1", "src-include.2.1" }, { "src-redefine.3.1", "src-redefine.3.1" }, { "src-import.3.1", "src-import.3.2" }, null, { "TargetNamespace.1", "TargetNamespace.2" }, { "TargetNamespace.1", "TargetNamespace.2" }, { "TargetNamespace.1", "TargetNamespace.2" }, { "TargetNamespace.1", "TargetNamespace.2" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  765 */   private static final String[] ELE_ERROR_CODES = new String[] { "src-include.1", "src-redefine.2", "src-import.2", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector fReportedTNS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XSDocumentInfo constructTrees(Element schemaRoot, String locationHint, XSDDescription desc, boolean nsCollision) {
/*  781 */     if (schemaRoot == null) return null; 
/*  782 */     String callerTNS = desc.getTargetNamespace();
/*  783 */     short referType = desc.getContextType();
/*      */     
/*  785 */     XSDocumentInfo currSchemaInfo = null;
/*      */     
/*      */     try {
/*  788 */       currSchemaInfo = new XSDocumentInfo(schemaRoot, this.fAttributeChecker, this.fSymbolTable);
/*  789 */     } catch (XMLSchemaException se) {
/*  790 */       reportSchemaError(ELE_ERROR_CODES[referType], new Object[] { locationHint }, schemaRoot);
/*      */ 
/*      */       
/*  793 */       return null;
/*      */     } 
/*      */     
/*  796 */     if (currSchemaInfo.fTargetNamespace != null && currSchemaInfo.fTargetNamespace
/*  797 */       .length() == 0) {
/*  798 */       reportSchemaWarning("EmptyTargetNamespace", new Object[] { locationHint }, schemaRoot);
/*      */ 
/*      */       
/*  801 */       currSchemaInfo.fTargetNamespace = null;
/*      */     } 
/*      */     
/*  804 */     if (callerTNS != null) {
/*      */ 
/*      */       
/*  807 */       int secondIdx = 0;
/*      */       
/*  809 */       if (referType == 0 || referType == 1) {
/*      */ 
/*      */ 
/*      */         
/*  813 */         if (currSchemaInfo.fTargetNamespace == null) {
/*  814 */           currSchemaInfo.fTargetNamespace = callerTNS;
/*  815 */           currSchemaInfo.fIsChameleonSchema = true;
/*      */ 
/*      */         
/*      */         }
/*  819 */         else if (callerTNS != currSchemaInfo.fTargetNamespace) {
/*  820 */           reportSchemaError(NS_ERROR_CODES[referType][secondIdx], new Object[] { callerTNS, currSchemaInfo.fTargetNamespace }, schemaRoot);
/*      */ 
/*      */           
/*  823 */           return null;
/*      */         }
/*      */       
/*      */       }
/*  827 */       else if (referType != 3 && callerTNS != currSchemaInfo.fTargetNamespace) {
/*  828 */         reportSchemaError(NS_ERROR_CODES[referType][secondIdx], new Object[] { callerTNS, currSchemaInfo.fTargetNamespace }, schemaRoot);
/*      */ 
/*      */         
/*  831 */         return null;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  836 */     else if (currSchemaInfo.fTargetNamespace != null) {
/*      */       
/*  838 */       if (referType == 3) {
/*  839 */         desc.setTargetNamespace(currSchemaInfo.fTargetNamespace);
/*  840 */         callerTNS = currSchemaInfo.fTargetNamespace;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  845 */         int secondIdx = 1;
/*  846 */         reportSchemaError(NS_ERROR_CODES[referType][secondIdx], new Object[] { callerTNS, currSchemaInfo.fTargetNamespace }, schemaRoot);
/*      */ 
/*      */         
/*  849 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  856 */     currSchemaInfo.addAllowedNS(currSchemaInfo.fTargetNamespace);
/*      */     
/*  858 */     SchemaGrammar sg = null;
/*      */ 
/*      */     
/*  861 */     if (nsCollision) {
/*  862 */       SchemaGrammar sg2 = this.fGrammarBucket.getGrammar(currSchemaInfo.fTargetNamespace);
/*  863 */       if (sg2.isImmutable()) {
/*  864 */         sg = new SchemaGrammar(sg2);
/*  865 */         this.fGrammarBucket.putGrammar(sg);
/*      */         
/*  867 */         updateImportListWith(sg);
/*      */       } else {
/*      */         
/*  870 */         sg = sg2;
/*      */       } 
/*      */ 
/*      */       
/*  874 */       updateImportListFor(sg);
/*      */     }
/*  876 */     else if (referType == 0 || referType == 1) {
/*      */       
/*  878 */       sg = this.fGrammarBucket.getGrammar(currSchemaInfo.fTargetNamespace);
/*      */     }
/*  880 */     else if (this.fHonourAllSchemaLocations && referType == 2) {
/*  881 */       sg = findGrammar(desc, false);
/*  882 */       if (sg == null) {
/*  883 */         sg = new SchemaGrammar(currSchemaInfo.fTargetNamespace, desc.makeClone(), this.fSymbolTable);
/*  884 */         this.fGrammarBucket.putGrammar(sg);
/*      */       } 
/*      */     } else {
/*      */       
/*  888 */       sg = new SchemaGrammar(currSchemaInfo.fTargetNamespace, desc.makeClone(), this.fSymbolTable);
/*  889 */       this.fGrammarBucket.putGrammar(sg);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  894 */     sg.addDocument((Object)null, this.fDoc2SystemId.get(currSchemaInfo.fSchemaElement));
/*      */     
/*  896 */     this.fDoc2XSDocumentMap.put(schemaRoot, currSchemaInfo);
/*  897 */     Vector<XSDocumentInfo> dependencies = new Vector<>();
/*  898 */     Element rootNode = schemaRoot;
/*      */     
/*  900 */     Element newSchemaRoot = null;
/*  901 */     Element child = DOMUtil.getFirstChildElement(rootNode);
/*  902 */     for (; child != null; 
/*  903 */       child = DOMUtil.getNextSiblingElement(child)) {
/*  904 */       String schemaNamespace = null;
/*  905 */       String schemaHint = null;
/*  906 */       String localName = DOMUtil.getLocalName(child);
/*      */       
/*  908 */       short refType = -1;
/*  909 */       boolean importCollision = false;
/*      */       
/*  911 */       if (localName.equals(SchemaSymbols.ELT_ANNOTATION))
/*      */         continue; 
/*  913 */       if (localName.equals(SchemaSymbols.ELT_IMPORT)) {
/*  914 */         refType = 2;
/*      */ 
/*      */         
/*  917 */         Object[] importAttrs = this.fAttributeChecker.checkAttributes(child, true, currSchemaInfo);
/*  918 */         schemaHint = (String)importAttrs[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
/*  919 */         schemaNamespace = (String)importAttrs[XSAttributeChecker.ATTIDX_NAMESPACE];
/*  920 */         if (schemaNamespace != null) {
/*  921 */           schemaNamespace = this.fSymbolTable.addSymbol(schemaNamespace);
/*      */         }
/*      */         
/*  924 */         Element importChild = DOMUtil.getFirstChildElement(child);
/*  925 */         if (importChild != null) {
/*  926 */           String importComponentType = DOMUtil.getLocalName(importChild);
/*  927 */           if (importComponentType.equals(SchemaSymbols.ELT_ANNOTATION)) {
/*      */             
/*  929 */             sg.addAnnotation(this.fElementTraverser
/*  930 */                 .traverseAnnotationDecl(importChild, importAttrs, true, currSchemaInfo));
/*      */           } else {
/*  932 */             reportSchemaError("s4s-elt-must-match.1", new Object[] { localName, "annotation?", importComponentType }, child);
/*      */           } 
/*  934 */           if (DOMUtil.getNextSiblingElement(importChild) != null) {
/*  935 */             reportSchemaError("s4s-elt-must-match.1", new Object[] { localName, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(importChild)) }, child);
/*      */           }
/*      */         } else {
/*      */           
/*  939 */           String text = DOMUtil.getSyntheticAnnotation(child);
/*  940 */           if (text != null) {
/*  941 */             sg.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(child, text, importAttrs, true, currSchemaInfo));
/*      */           }
/*      */         } 
/*  944 */         this.fAttributeChecker.returnAttrArray(importAttrs, currSchemaInfo);
/*      */ 
/*      */         
/*  947 */         if (schemaNamespace == currSchemaInfo.fTargetNamespace) {
/*  948 */           reportSchemaError((schemaNamespace != null) ? "src-import.1.1" : "src-import.1.2", new Object[] { schemaNamespace }, child);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*  955 */         if (currSchemaInfo.isAllowedNS(schemaNamespace)) {
/*  956 */           if (!this.fHonourAllSchemaLocations && !this.fNamespaceGrowth) {
/*      */             continue;
/*      */           }
/*      */         } else {
/*  960 */           currSchemaInfo.addAllowedNS(schemaNamespace);
/*      */         } 
/*      */ 
/*      */         
/*  964 */         String tns = null2EmptyString(currSchemaInfo.fTargetNamespace);
/*      */         
/*  966 */         Vector<String> ins = this.fImportMap.get(tns);
/*      */         
/*  968 */         if (ins == null) {
/*      */           
/*  970 */           this.fAllTNSs.addElement(tns);
/*  971 */           ins = new Vector();
/*  972 */           this.fImportMap.put(tns, ins);
/*  973 */           ins.addElement(schemaNamespace);
/*      */         }
/*  975 */         else if (!ins.contains(schemaNamespace)) {
/*  976 */           ins.addElement(schemaNamespace);
/*      */         } 
/*      */         
/*  979 */         this.fSchemaGrammarDescription.reset();
/*  980 */         this.fSchemaGrammarDescription.setContextType((short)2);
/*  981 */         this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(schemaRoot));
/*  982 */         this.fSchemaGrammarDescription.setLiteralSystemId(schemaHint);
/*  983 */         this.fSchemaGrammarDescription.setLocationHints(new String[] { schemaHint });
/*  984 */         this.fSchemaGrammarDescription.setTargetNamespace(schemaNamespace);
/*      */ 
/*      */ 
/*      */         
/*  988 */         SchemaGrammar isg = findGrammar(this.fSchemaGrammarDescription, this.fNamespaceGrowth);
/*  989 */         if (isg != null) {
/*  990 */           if (this.fNamespaceGrowth) {
/*      */             try {
/*  992 */               if (isg.getDocumentLocations().contains(XMLEntityManager.expandSystemId(schemaHint, this.fSchemaGrammarDescription.getBaseSystemId(), false))) {
/*      */                 continue;
/*      */               }
/*      */               
/*  996 */               importCollision = true;
/*      */             
/*      */             }
/*  999 */             catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {}
/*      */           
/*      */           }
/* 1002 */           else if (!this.fHonourAllSchemaLocations || isExistingGrammar(this.fSchemaGrammarDescription, false)) {
/*      */             continue;
/*      */           } 
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1014 */         newSchemaRoot = resolveSchema(this.fSchemaGrammarDescription, false, child, (isg == null));
/*      */       }
/* 1016 */       else if (localName.equals(SchemaSymbols.ELT_INCLUDE) || localName
/* 1017 */         .equals(SchemaSymbols.ELT_REDEFINE)) {
/*      */ 
/*      */ 
/*      */         
/* 1021 */         Object[] includeAttrs = this.fAttributeChecker.checkAttributes(child, true, currSchemaInfo);
/* 1022 */         schemaHint = (String)includeAttrs[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
/*      */         
/* 1024 */         if (localName.equals(SchemaSymbols.ELT_REDEFINE)) {
/* 1025 */           if (this.fRedefine2NSSupport == null) this.fRedefine2NSSupport = new HashMap<>(); 
/* 1026 */           this.fRedefine2NSSupport.put(child, new SchemaNamespaceSupport(currSchemaInfo.fNamespaceSupport));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1031 */         if (localName.equals(SchemaSymbols.ELT_INCLUDE)) {
/* 1032 */           Element includeChild = DOMUtil.getFirstChildElement(child);
/* 1033 */           if (includeChild != null) {
/* 1034 */             String includeComponentType = DOMUtil.getLocalName(includeChild);
/* 1035 */             if (includeComponentType.equals(SchemaSymbols.ELT_ANNOTATION)) {
/*      */               
/* 1037 */               sg.addAnnotation(this.fElementTraverser
/* 1038 */                   .traverseAnnotationDecl(includeChild, includeAttrs, true, currSchemaInfo));
/*      */             } else {
/* 1040 */               reportSchemaError("s4s-elt-must-match.1", new Object[] { localName, "annotation?", includeComponentType }, child);
/*      */             } 
/* 1042 */             if (DOMUtil.getNextSiblingElement(includeChild) != null) {
/* 1043 */               reportSchemaError("s4s-elt-must-match.1", new Object[] { localName, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(includeChild)) }, child);
/*      */             }
/*      */           } else {
/*      */             
/* 1047 */             String text = DOMUtil.getSyntheticAnnotation(child);
/* 1048 */             if (text != null) {
/* 1049 */               sg.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(child, text, includeAttrs, true, currSchemaInfo));
/*      */             }
/*      */           } 
/*      */         } else {
/*      */           
/* 1054 */           Element redefinedChild = DOMUtil.getFirstChildElement(child);
/* 1055 */           for (; redefinedChild != null; 
/* 1056 */             redefinedChild = DOMUtil.getNextSiblingElement(redefinedChild)) {
/* 1057 */             String redefinedComponentType = DOMUtil.getLocalName(redefinedChild);
/* 1058 */             if (redefinedComponentType.equals(SchemaSymbols.ELT_ANNOTATION)) {
/*      */               
/* 1060 */               sg.addAnnotation(this.fElementTraverser
/* 1061 */                   .traverseAnnotationDecl(redefinedChild, includeAttrs, true, currSchemaInfo));
/* 1062 */               DOMUtil.setHidden(redefinedChild, this.fHiddenNodes);
/*      */             } else {
/*      */               
/* 1065 */               String text = DOMUtil.getSyntheticAnnotation(child);
/* 1066 */               if (text != null) {
/* 1067 */                 sg.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(child, text, includeAttrs, true, currSchemaInfo));
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1073 */         this.fAttributeChecker.returnAttrArray(includeAttrs, currSchemaInfo);
/*      */         
/* 1075 */         if (schemaHint == null) {
/* 1076 */           reportSchemaError("s4s-att-must-appear", new Object[] { "<include> or <redefine>", "schemaLocation" }, child);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1081 */         boolean mustResolve = false;
/* 1082 */         refType = 0;
/* 1083 */         if (localName.equals(SchemaSymbols.ELT_REDEFINE)) {
/* 1084 */           mustResolve = nonAnnotationContent(child);
/* 1085 */           refType = 1;
/*      */         } 
/* 1087 */         this.fSchemaGrammarDescription.reset();
/* 1088 */         this.fSchemaGrammarDescription.setContextType(refType);
/* 1089 */         this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(schemaRoot));
/* 1090 */         this.fSchemaGrammarDescription.setLocationHints(new String[] { schemaHint });
/* 1091 */         this.fSchemaGrammarDescription.setTargetNamespace(callerTNS);
/*      */         
/* 1093 */         boolean alreadyTraversed = false;
/* 1094 */         XMLInputSource schemaSource = resolveSchemaSource(this.fSchemaGrammarDescription, mustResolve, child, true);
/* 1095 */         if (this.fNamespaceGrowth && refType == 0) {
/*      */           try {
/* 1097 */             String schemaId = XMLEntityManager.expandSystemId(schemaSource.getSystemId(), schemaSource.getBaseSystemId(), false);
/* 1098 */             alreadyTraversed = sg.getDocumentLocations().contains(schemaId);
/*      */           }
/* 1100 */           catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException malformedURIException) {}
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1105 */         if (!alreadyTraversed) {
/* 1106 */           newSchemaRoot = resolveSchema(schemaSource, this.fSchemaGrammarDescription, mustResolve, child);
/* 1107 */           schemaNamespace = currSchemaInfo.fTargetNamespace;
/*      */         } else {
/*      */           
/* 1110 */           this.fLastSchemaWasDuplicate = true;
/*      */         } 
/*      */       } else {
/*      */         break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1121 */       XSDocumentInfo newSchemaInfo = null;
/* 1122 */       if (this.fLastSchemaWasDuplicate) {
/* 1123 */         newSchemaInfo = (newSchemaRoot == null) ? null : (XSDocumentInfo)this.fDoc2XSDocumentMap.get(newSchemaRoot);
/*      */       } else {
/*      */         
/* 1126 */         newSchemaInfo = constructTrees(newSchemaRoot, schemaHint, this.fSchemaGrammarDescription, importCollision);
/*      */       } 
/*      */       
/* 1129 */       if (localName.equals(SchemaSymbols.ELT_REDEFINE) && newSchemaInfo != null) {
/*      */ 
/*      */ 
/*      */         
/* 1133 */         if (this.fRedefine2XSDMap == null) this.fRedefine2XSDMap = new HashMap<>(); 
/* 1134 */         this.fRedefine2XSDMap.put(child, newSchemaInfo);
/*      */       } 
/* 1136 */       if (newSchemaRoot != null) {
/* 1137 */         if (newSchemaInfo != null)
/* 1138 */           dependencies.addElement(newSchemaInfo); 
/* 1139 */         newSchemaRoot = null;
/*      */       } 
/*      */       continue;
/*      */     } 
/* 1143 */     this.fDependencyMap.put(currSchemaInfo, dependencies);
/* 1144 */     return currSchemaInfo;
/*      */   }
/*      */   
/*      */   private boolean isExistingGrammar(XSDDescription desc, boolean ignoreConflict) {
/* 1148 */     SchemaGrammar sg = this.fGrammarBucket.getGrammar(desc.getTargetNamespace());
/* 1149 */     if (sg == null) {
/* 1150 */       return (findGrammar(desc, ignoreConflict) != null);
/*      */     }
/* 1152 */     if (sg.isImmutable()) {
/* 1153 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 1157 */       return sg.getDocumentLocations().contains(XMLEntityManager.expandSystemId(desc.getLiteralSystemId(), desc.getBaseSystemId(), false));
/*      */     }
/* 1159 */     catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
/* 1160 */       return false;
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
/*      */   private void updateImportListFor(SchemaGrammar grammar) {
/* 1174 */     Vector<SchemaGrammar> importedGrammars = grammar.getImportedGrammars();
/* 1175 */     if (importedGrammars != null) {
/* 1176 */       for (int i = 0; i < importedGrammars.size(); i++) {
/* 1177 */         SchemaGrammar isg1 = importedGrammars.elementAt(i);
/* 1178 */         SchemaGrammar isg2 = this.fGrammarBucket.getGrammar(isg1.getTargetNamespace());
/* 1179 */         if (isg2 != null && isg1 != isg2) {
/* 1180 */           importedGrammars.set(i, isg2);
/*      */         }
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
/*      */   private void updateImportListWith(SchemaGrammar newGrammar) {
/* 1196 */     SchemaGrammar[] schemaGrammars = this.fGrammarBucket.getGrammars();
/* 1197 */     for (int i = 0; i < schemaGrammars.length; i++) {
/* 1198 */       SchemaGrammar sg = schemaGrammars[i];
/* 1199 */       if (sg != newGrammar) {
/* 1200 */         Vector<SchemaGrammar> importedGrammars = sg.getImportedGrammars();
/* 1201 */         if (importedGrammars != null) {
/* 1202 */           for (int j = 0; j < importedGrammars.size(); j++) {
/* 1203 */             SchemaGrammar isg = importedGrammars.elementAt(j);
/* 1204 */             if (null2EmptyString(isg.getTargetNamespace()).equals(null2EmptyString(newGrammar.getTargetNamespace()))) {
/* 1205 */               if (isg != newGrammar) {
/* 1206 */                 importedGrammars.set(j, newGrammar);
/*      */               }
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
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
/*      */   protected void buildGlobalNameRegistries() {
/* 1223 */     this.registryEmpty = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1234 */     Stack<XSDocumentInfo> schemasToProcess = new Stack();
/* 1235 */     schemasToProcess.push(this.fRoot);
/*      */     
/* 1237 */     while (!schemasToProcess.empty()) {
/*      */       
/* 1239 */       XSDocumentInfo currSchemaDoc = schemasToProcess.pop();
/* 1240 */       Element currDoc = currSchemaDoc.fSchemaElement;
/* 1241 */       if (DOMUtil.isHidden(currDoc, this.fHiddenNodes)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1246 */       Element currRoot = currDoc;
/*      */       
/* 1248 */       boolean dependenciesCanOccur = true;
/*      */       
/* 1250 */       Element globalComp = DOMUtil.getFirstChildElement(currRoot);
/* 1251 */       for (; globalComp != null; 
/* 1252 */         globalComp = DOMUtil.getNextSiblingElement(globalComp)) {
/*      */ 
/*      */         
/* 1255 */         if (!DOMUtil.getLocalName(globalComp).equals(SchemaSymbols.ELT_ANNOTATION))
/*      */         {
/*      */ 
/*      */           
/* 1259 */           if (DOMUtil.getLocalName(globalComp).equals(SchemaSymbols.ELT_INCLUDE) || 
/* 1260 */             DOMUtil.getLocalName(globalComp).equals(SchemaSymbols.ELT_IMPORT)) {
/* 1261 */             if (!dependenciesCanOccur) {
/* 1262 */               reportSchemaError("s4s-elt-invalid-content.3", new Object[] { DOMUtil.getLocalName(globalComp) }, globalComp);
/*      */             }
/* 1264 */             DOMUtil.setHidden(globalComp, this.fHiddenNodes);
/*      */           }
/* 1266 */           else if (DOMUtil.getLocalName(globalComp).equals(SchemaSymbols.ELT_REDEFINE)) {
/* 1267 */             if (!dependenciesCanOccur) {
/* 1268 */               reportSchemaError("s4s-elt-invalid-content.3", new Object[] { DOMUtil.getLocalName(globalComp) }, globalComp);
/*      */             }
/* 1270 */             Element redefineComp = DOMUtil.getFirstChildElement(globalComp);
/* 1271 */             for (; redefineComp != null; 
/* 1272 */               redefineComp = DOMUtil.getNextSiblingElement(redefineComp)) {
/* 1273 */               String lName = DOMUtil.getAttrValue(redefineComp, SchemaSymbols.ATT_NAME);
/* 1274 */               if (lName.length() != 0)
/*      */               {
/* 1276 */                 String qName = (currSchemaDoc.fTargetNamespace == null) ? ("," + lName) : (currSchemaDoc.fTargetNamespace + "," + lName);
/*      */ 
/*      */                 
/* 1279 */                 String componentType = DOMUtil.getLocalName(redefineComp);
/* 1280 */                 if (componentType.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
/* 1281 */                   checkForDuplicateNames(qName, 2, this.fUnparsedAttributeGroupRegistry, this.fUnparsedAttributeGroupRegistrySub, redefineComp, currSchemaDoc);
/*      */                   
/* 1283 */                   String targetLName = DOMUtil.getAttrValue(redefineComp, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
/*      */                   
/* 1285 */                   renameRedefiningComponents(currSchemaDoc, redefineComp, SchemaSymbols.ELT_ATTRIBUTEGROUP, lName, targetLName);
/*      */                 
/*      */                 }
/* 1288 */                 else if (componentType.equals(SchemaSymbols.ELT_COMPLEXTYPE) || componentType
/* 1289 */                   .equals(SchemaSymbols.ELT_SIMPLETYPE)) {
/* 1290 */                   checkForDuplicateNames(qName, 7, this.fUnparsedTypeRegistry, this.fUnparsedTypeRegistrySub, redefineComp, currSchemaDoc);
/*      */                   
/* 1292 */                   String targetLName = DOMUtil.getAttrValue(redefineComp, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
/*      */                   
/* 1294 */                   if (componentType.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
/* 1295 */                     renameRedefiningComponents(currSchemaDoc, redefineComp, SchemaSymbols.ELT_COMPLEXTYPE, lName, targetLName);
/*      */                   }
/*      */                   else {
/*      */                     
/* 1299 */                     renameRedefiningComponents(currSchemaDoc, redefineComp, SchemaSymbols.ELT_SIMPLETYPE, lName, targetLName);
/*      */                   }
/*      */                 
/*      */                 }
/* 1303 */                 else if (componentType.equals(SchemaSymbols.ELT_GROUP)) {
/* 1304 */                   checkForDuplicateNames(qName, 4, this.fUnparsedGroupRegistry, this.fUnparsedGroupRegistrySub, redefineComp, currSchemaDoc);
/*      */                   
/* 1306 */                   String targetLName = DOMUtil.getAttrValue(redefineComp, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
/*      */                   
/* 1308 */                   renameRedefiningComponents(currSchemaDoc, redefineComp, SchemaSymbols.ELT_GROUP, lName, targetLName);
/*      */                 }
/*      */               
/*      */               }
/*      */             
/*      */             } 
/*      */           } else {
/*      */             
/* 1316 */             dependenciesCanOccur = false;
/* 1317 */             String lName = DOMUtil.getAttrValue(globalComp, SchemaSymbols.ATT_NAME);
/* 1318 */             if (lName.length() != 0) {
/*      */               
/* 1320 */               String qName = (currSchemaDoc.fTargetNamespace == null) ? ("," + lName) : (currSchemaDoc.fTargetNamespace + "," + lName);
/*      */ 
/*      */               
/* 1323 */               String componentType = DOMUtil.getLocalName(globalComp);
/*      */               
/* 1325 */               if (componentType.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
/* 1326 */                 checkForDuplicateNames(qName, 1, this.fUnparsedAttributeRegistry, this.fUnparsedAttributeRegistrySub, globalComp, currSchemaDoc);
/*      */               }
/* 1328 */               else if (componentType.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
/* 1329 */                 checkForDuplicateNames(qName, 2, this.fUnparsedAttributeGroupRegistry, this.fUnparsedAttributeGroupRegistrySub, globalComp, currSchemaDoc);
/*      */               }
/* 1331 */               else if (componentType.equals(SchemaSymbols.ELT_COMPLEXTYPE) || componentType
/* 1332 */                 .equals(SchemaSymbols.ELT_SIMPLETYPE)) {
/* 1333 */                 checkForDuplicateNames(qName, 7, this.fUnparsedTypeRegistry, this.fUnparsedTypeRegistrySub, globalComp, currSchemaDoc);
/*      */               }
/* 1335 */               else if (componentType.equals(SchemaSymbols.ELT_ELEMENT)) {
/* 1336 */                 checkForDuplicateNames(qName, 3, this.fUnparsedElementRegistry, this.fUnparsedElementRegistrySub, globalComp, currSchemaDoc);
/*      */               }
/* 1338 */               else if (componentType.equals(SchemaSymbols.ELT_GROUP)) {
/* 1339 */                 checkForDuplicateNames(qName, 4, this.fUnparsedGroupRegistry, this.fUnparsedGroupRegistrySub, globalComp, currSchemaDoc);
/*      */               }
/* 1341 */               else if (componentType.equals(SchemaSymbols.ELT_NOTATION)) {
/* 1342 */                 checkForDuplicateNames(qName, 6, this.fUnparsedNotationRegistry, this.fUnparsedNotationRegistrySub, globalComp, currSchemaDoc);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/* 1348 */       DOMUtil.setHidden(currDoc, this.fHiddenNodes);
/*      */       
/* 1350 */       Vector<XSDocumentInfo> currSchemaDepends = this.fDependencyMap.get(currSchemaDoc);
/* 1351 */       for (int i = 0; i < currSchemaDepends.size(); i++) {
/* 1352 */         schemasToProcess.push(currSchemaDepends.elementAt(i));
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
/*      */   protected void traverseSchemas(ArrayList<String> annotationInfo) {
/* 1374 */     setSchemasVisible(this.fRoot);
/* 1375 */     Stack<XSDocumentInfo> schemasToProcess = new Stack();
/* 1376 */     schemasToProcess.push(this.fRoot);
/* 1377 */     while (!schemasToProcess.empty())
/*      */     
/* 1379 */     { XSDocumentInfo currSchemaDoc = schemasToProcess.pop();
/* 1380 */       Element currDoc = currSchemaDoc.fSchemaElement;
/*      */       
/* 1382 */       SchemaGrammar currSG = this.fGrammarBucket.getGrammar(currSchemaDoc.fTargetNamespace);
/*      */       
/* 1384 */       if (DOMUtil.isHidden(currDoc, this.fHiddenNodes)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1388 */       Element currRoot = currDoc;
/* 1389 */       boolean sawAnnotation = false;
/*      */ 
/*      */       
/* 1392 */       Element globalComp = DOMUtil.getFirstVisibleChildElement(currRoot, this.fHiddenNodes);
/* 1393 */       for (; globalComp != null; 
/* 1394 */         globalComp = DOMUtil.getNextVisibleSiblingElement(globalComp, this.fHiddenNodes)) {
/* 1395 */         DOMUtil.setHidden(globalComp, this.fHiddenNodes);
/* 1396 */         String componentType = DOMUtil.getLocalName(globalComp);
/*      */         
/* 1398 */         if (DOMUtil.getLocalName(globalComp).equals(SchemaSymbols.ELT_REDEFINE)) {
/*      */           
/* 1400 */           currSchemaDoc.backupNSSupport((this.fRedefine2NSSupport != null) ? (SchemaNamespaceSupport)this.fRedefine2NSSupport.get(globalComp) : null);
/* 1401 */           Element redefinedComp = DOMUtil.getFirstVisibleChildElement(globalComp, this.fHiddenNodes);
/* 1402 */           for (; redefinedComp != null; 
/* 1403 */             redefinedComp = DOMUtil.getNextVisibleSiblingElement(redefinedComp, this.fHiddenNodes)) {
/* 1404 */             String redefinedComponentType = DOMUtil.getLocalName(redefinedComp);
/* 1405 */             DOMUtil.setHidden(redefinedComp, this.fHiddenNodes);
/* 1406 */             if (redefinedComponentType.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
/* 1407 */               this.fAttributeGroupTraverser.traverseGlobal(redefinedComp, currSchemaDoc, currSG);
/*      */             }
/* 1409 */             else if (redefinedComponentType.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
/* 1410 */               this.fComplexTypeTraverser.traverseGlobal(redefinedComp, currSchemaDoc, currSG);
/*      */             }
/* 1412 */             else if (redefinedComponentType.equals(SchemaSymbols.ELT_GROUP)) {
/* 1413 */               this.fGroupTraverser.traverseGlobal(redefinedComp, currSchemaDoc, currSG);
/*      */             }
/* 1415 */             else if (redefinedComponentType.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
/* 1416 */               this.fSimpleTypeTraverser.traverseGlobal(redefinedComp, currSchemaDoc, currSG);
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */               
/* 1424 */               reportSchemaError("s4s-elt-must-match.1", new Object[] { DOMUtil.getLocalName(globalComp), "(annotation | (simpleType | complexType | group | attributeGroup))*", redefinedComponentType }, redefinedComp);
/*      */             } 
/*      */           } 
/* 1427 */           currSchemaDoc.restoreNSSupport();
/*      */         }
/* 1429 */         else if (componentType.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
/* 1430 */           this.fAttributeTraverser.traverseGlobal(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1432 */         else if (componentType.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
/* 1433 */           this.fAttributeGroupTraverser.traverseGlobal(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1435 */         else if (componentType.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
/* 1436 */           this.fComplexTypeTraverser.traverseGlobal(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1438 */         else if (componentType.equals(SchemaSymbols.ELT_ELEMENT)) {
/* 1439 */           this.fElementTraverser.traverseGlobal(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1441 */         else if (componentType.equals(SchemaSymbols.ELT_GROUP)) {
/* 1442 */           this.fGroupTraverser.traverseGlobal(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1444 */         else if (componentType.equals(SchemaSymbols.ELT_NOTATION)) {
/* 1445 */           this.fNotationTraverser.traverse(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1447 */         else if (componentType.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
/* 1448 */           this.fSimpleTypeTraverser.traverseGlobal(globalComp, currSchemaDoc, currSG);
/*      */         }
/* 1450 */         else if (componentType.equals(SchemaSymbols.ELT_ANNOTATION)) {
/* 1451 */           currSG.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(globalComp, currSchemaDoc.getSchemaAttrs(), true, currSchemaDoc));
/* 1452 */           sawAnnotation = true;
/*      */         } else {
/*      */           
/* 1455 */           reportSchemaError("s4s-elt-invalid-content.1", new Object[] { SchemaSymbols.ELT_SCHEMA, DOMUtil.getLocalName(globalComp) }, globalComp);
/*      */         } 
/*      */       } 
/*      */       
/* 1459 */       if (!sawAnnotation) {
/* 1460 */         String text = DOMUtil.getSyntheticAnnotation(currRoot);
/* 1461 */         if (text != null) {
/* 1462 */           currSG.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(currRoot, text, currSchemaDoc.getSchemaAttrs(), true, currSchemaDoc));
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1467 */       if (annotationInfo != null) {
/* 1468 */         XSAnnotationInfo info = currSchemaDoc.getAnnotations();
/*      */         
/* 1470 */         if (info != null) {
/* 1471 */           annotationInfo.add(doc2SystemId(currDoc));
/* 1472 */           annotationInfo.add(info);
/*      */         } 
/*      */       } 
/*      */       
/* 1476 */       currSchemaDoc.returnSchemaAttrs();
/* 1477 */       DOMUtil.setHidden(currDoc, this.fHiddenNodes);
/*      */ 
/*      */       
/* 1480 */       Vector<XSDocumentInfo> currSchemaDepends = this.fDependencyMap.get(currSchemaDoc);
/* 1481 */       for (int i = 0; i < currSchemaDepends.size(); i++)
/* 1482 */         schemasToProcess.push(currSchemaDepends.elementAt(i));  } 
/*      */   } private final boolean needReportTNSError(String uri) { if (this.fReportedTNS == null) { this.fReportedTNS = new Vector(); } else if (this.fReportedTNS.contains(uri)) { return false; }  this.fReportedTNS.addElement(uri); return true; } private static final String[] COMP_TYPE = new String[] { null, "attribute declaration", "attribute group", "element declaration", "group", "identity constraint", "notation", "type definition" }; private static final String[] CIRCULAR_CODES = new String[] { "Internal-Error", "Internal-Error", "src-attribute_group.3", "e-props-correct.6", "mg-props-correct.2", "Internal-Error", "Internal-Error", "st-props-correct.2" }; private SimpleLocator xl; void addGlobalAttributeDecl(XSAttributeDecl decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getName()) : (namespace + "," + decl.getName()); if (this.fGlobalAttrDecls.get(declKey) == null) this.fGlobalAttrDecls.put(declKey, decl);  } void addGlobalAttributeGroupDecl(XSAttributeGroupDecl decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getName()) : (namespace + "," + decl.getName()); if (this.fGlobalAttrGrpDecls.get(declKey) == null) this.fGlobalAttrGrpDecls.put(declKey, decl);  } void addGlobalElementDecl(XSElementDecl decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getName()) : (namespace + "," + decl.getName()); if (this.fGlobalElemDecls.get(declKey) == null) this.fGlobalElemDecls.put(declKey, decl);  } void addGlobalGroupDecl(XSGroupDecl decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getName()) : (namespace + "," + decl.getName()); if (this.fGlobalGroupDecls.get(declKey) == null) this.fGlobalGroupDecls.put(declKey, decl);  } void addGlobalNotationDecl(XSNotationDecl decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getName()) : (namespace + "," + decl.getName()); if (this.fGlobalNotationDecls.get(declKey) == null) this.fGlobalNotationDecls.put(declKey, decl);  } void addGlobalTypeDecl(XSTypeDefinition decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getName()) : (namespace + "," + decl.getName()); if (this.fGlobalTypeDecls.get(declKey) == null) this.fGlobalTypeDecls.put(declKey, decl);  } void addIDConstraintDecl(IdentityConstraint decl) { String namespace = decl.getNamespace(); String declKey = (namespace == null || namespace.length() == 0) ? ("," + decl.getIdentityConstraintName()) : (namespace + "," + decl.getIdentityConstraintName()); if (this.fGlobalIDConstraintDecls.get(declKey) == null) this.fGlobalIDConstraintDecls.put(declKey, decl);  } private XSAttributeDecl getGlobalAttributeDecl(String declKey) { return (XSAttributeDecl)this.fGlobalAttrDecls.get(declKey); } private XSAttributeGroupDecl getGlobalAttributeGroupDecl(String declKey) { return (XSAttributeGroupDecl)this.fGlobalAttrGrpDecls.get(declKey); } private XSElementDecl getGlobalElementDecl(String declKey) { return (XSElementDecl)this.fGlobalElemDecls.get(declKey); } private XSGroupDecl getGlobalGroupDecl(String declKey) { return (XSGroupDecl)this.fGlobalGroupDecls.get(declKey); } private XSNotationDecl getGlobalNotationDecl(String declKey) { return (XSNotationDecl)this.fGlobalNotationDecls.get(declKey); } private XSTypeDefinition getGlobalTypeDecl(String declKey) { return (XSTypeDefinition)this.fGlobalTypeDecls.get(declKey); } private IdentityConstraint getIDConstraintDecl(String declKey) { return (IdentityConstraint)this.fGlobalIDConstraintDecls.get(declKey); } protected Object getGlobalDecl(XSDocumentInfo currSchema, int declType, QName declToTraverse, Element elmNode) { if (declToTraverse.uri != null && declToTraverse.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA) if (declType == 7) { Object object = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(declToTraverse.localpart); if (object != null) return object;  }   if (!currSchema.isAllowedNS(declToTraverse.uri)) if (currSchema.needReportTNSError(declToTraverse.uri)) { String code = (declToTraverse.uri == null) ? "src-resolve.4.1" : "src-resolve.4.2"; reportSchemaError(code, new Object[] { this.fDoc2SystemId.get(currSchema.fSchemaElement), declToTraverse.uri, declToTraverse.rawname }, elmNode); }   SchemaGrammar sGrammar = this.fGrammarBucket.getGrammar(declToTraverse.uri); if (sGrammar == null) { if (needReportTNSError(declToTraverse.uri)) reportSchemaError("src-resolve", new Object[] { declToTraverse.rawname, COMP_TYPE[declType] }, elmNode);  return null; }  Object retObj = getGlobalDeclFromGrammar(sGrammar, declType, declToTraverse.localpart); String declKey = (declToTraverse.uri == null) ? ("," + declToTraverse.localpart) : (declToTraverse.uri + "," + declToTraverse.localpart); if (!this.fTolerateDuplicates) { if (retObj != null) return retObj;  } else { Object retObj2 = getGlobalDecl(declKey, declType); if (retObj2 != null) return retObj2;  }  XSDocumentInfo schemaWithDecl = null; Element decl = null; XSDocumentInfo declDoc = null; switch (declType) { case 1: decl = getElementFromMap(this.fUnparsedAttributeRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedAttributeRegistrySub, declKey); break;case 2: decl = getElementFromMap(this.fUnparsedAttributeGroupRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedAttributeGroupRegistrySub, declKey); break;case 3: decl = getElementFromMap(this.fUnparsedElementRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedElementRegistrySub, declKey); break;case 4: decl = getElementFromMap(this.fUnparsedGroupRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedGroupRegistrySub, declKey); break;case 5: decl = getElementFromMap(this.fUnparsedIdentityConstraintRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedIdentityConstraintRegistrySub, declKey); break;case 6: decl = getElementFromMap(this.fUnparsedNotationRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedNotationRegistrySub, declKey); break;case 7: decl = getElementFromMap(this.fUnparsedTypeRegistry, declKey); declDoc = getDocInfoFromMap(this.fUnparsedTypeRegistrySub, declKey); break;default: reportSchemaError("Internal-Error", new Object[] { "XSDHandler asked to locate component of type " + declType + "; it does not recognize this type!" }, elmNode); break; }  if (decl == null) { if (retObj == null) reportSchemaError("src-resolve", new Object[] { declToTraverse.rawname, COMP_TYPE[declType] }, elmNode);  return retObj; }  schemaWithDecl = findXSDocumentForDecl(currSchema, decl, declDoc); if (schemaWithDecl == null) { if (retObj == null) { String code = (declToTraverse.uri == null) ? "src-resolve.4.1" : "src-resolve.4.2"; reportSchemaError(code, new Object[] { this.fDoc2SystemId.get(currSchema.fSchemaElement), declToTraverse.uri, declToTraverse.rawname }, elmNode); }  return retObj; }  if (DOMUtil.isHidden(decl, this.fHiddenNodes)) { if (retObj == null) { String code = CIRCULAR_CODES[declType]; if (declType == 7 && SchemaSymbols.ELT_COMPLEXTYPE.equals(DOMUtil.getLocalName(decl))) code = "ct-props-correct.3";  reportSchemaError(code, new Object[] { declToTraverse.prefix + ":" + declToTraverse.localpart }, elmNode); }  return retObj; }  return traverseGlobalDecl(declType, decl, schemaWithDecl, sGrammar); } protected Object getGlobalDecl(String declKey, int declType) { Object retObj = null; switch (declType) { case 1: retObj = getGlobalAttributeDecl(declKey); break;case 2: retObj = getGlobalAttributeGroupDecl(declKey); break;case 3: retObj = getGlobalElementDecl(declKey); break;case 4: retObj = getGlobalGroupDecl(declKey); break;case 5: retObj = getIDConstraintDecl(declKey); break;case 6: retObj = getGlobalNotationDecl(declKey); break;case 7: retObj = getGlobalTypeDecl(declKey); break; }  return retObj; }
/*      */   protected Object getGlobalDeclFromGrammar(SchemaGrammar sGrammar, int declType, String localpart) { Object retObj = null; switch (declType) { case 1: retObj = sGrammar.getGlobalAttributeDecl(localpart); break;case 2: retObj = sGrammar.getGlobalAttributeGroupDecl(localpart); break;case 3: retObj = sGrammar.getGlobalElementDecl(localpart); break;case 4: retObj = sGrammar.getGlobalGroupDecl(localpart); break;case 5: retObj = sGrammar.getIDConstraintDecl(localpart); break;case 6: retObj = sGrammar.getGlobalNotationDecl(localpart); break;case 7: retObj = sGrammar.getGlobalTypeDecl(localpart); break; }  return retObj; }
/*      */   protected Object getGlobalDeclFromGrammar(SchemaGrammar sGrammar, int declType, String localpart, String schemaLoc) { Object retObj = null; switch (declType) { case 1: retObj = sGrammar.getGlobalAttributeDecl(localpart, schemaLoc); break;case 2: retObj = sGrammar.getGlobalAttributeGroupDecl(localpart, schemaLoc); break;case 3: retObj = sGrammar.getGlobalElementDecl(localpart, schemaLoc); break;case 4: retObj = sGrammar.getGlobalGroupDecl(localpart, schemaLoc); break;case 5: retObj = sGrammar.getIDConstraintDecl(localpart, schemaLoc); break;case 6: retObj = sGrammar.getGlobalNotationDecl(localpart, schemaLoc); break;case 7: retObj = sGrammar.getGlobalTypeDecl(localpart, schemaLoc); break; }  return retObj; }
/*      */   protected Object traverseGlobalDecl(int declType, Element decl, XSDocumentInfo schemaDoc, SchemaGrammar grammar) { Object retObj = null; DOMUtil.setHidden(decl, this.fHiddenNodes); SchemaNamespaceSupport nsSupport = null; Element parent = DOMUtil.getParent(decl); if (DOMUtil.getLocalName(parent).equals(SchemaSymbols.ELT_REDEFINE)) nsSupport = (this.fRedefine2NSSupport != null) ? (SchemaNamespaceSupport)this.fRedefine2NSSupport.get(parent) : null;  schemaDoc.backupNSSupport(nsSupport); switch (declType) { case 7: if (DOMUtil.getLocalName(decl).equals(SchemaSymbols.ELT_COMPLEXTYPE)) { retObj = this.fComplexTypeTraverser.traverseGlobal(decl, schemaDoc, grammar); break; }  retObj = this.fSimpleTypeTraverser.traverseGlobal(decl, schemaDoc, grammar); break;case 1: retObj = this.fAttributeTraverser.traverseGlobal(decl, schemaDoc, grammar); break;case 3: retObj = this.fElementTraverser.traverseGlobal(decl, schemaDoc, grammar); break;case 2: retObj = this.fAttributeGroupTraverser.traverseGlobal(decl, schemaDoc, grammar); break;case 4: retObj = this.fGroupTraverser.traverseGlobal(decl, schemaDoc, grammar); break;case 6: retObj = this.fNotationTraverser.traverse(decl, schemaDoc, grammar); break; }  schemaDoc.restoreNSSupport(); return retObj; }
/*      */   public String schemaDocument2SystemId(XSDocumentInfo schemaDoc) { return this.fDoc2SystemId.get(schemaDoc.fSchemaElement); }
/*      */   Object getGrpOrAttrGrpRedefinedByRestriction(int type, QName name, XSDocumentInfo currSchema, Element elmNode) { String realName = (name.uri != null) ? (name.uri + "," + name.localpart) : ("," + name.localpart); String nameToFind = null; switch (type) { case 2: nameToFind = (String)this.fRedefinedRestrictedAttributeGroupRegistry.get(realName); break;case 4: nameToFind = (String)this.fRedefinedRestrictedGroupRegistry.get(realName); break;default: return null; }  if (nameToFind == null) return null;  int commaPos = nameToFind.indexOf(","); QName qNameToFind = new QName(XMLSymbols.EMPTY_STRING, nameToFind.substring(commaPos + 1), nameToFind.substring(commaPos), (commaPos == 0) ? null : nameToFind.substring(0, commaPos)); Object retObj = getGlobalDecl(currSchema, type, qNameToFind, elmNode); if (retObj == null) { switch (type) { case 2: reportSchemaError("src-redefine.7.2.1", new Object[] { name.localpart }, elmNode); break;case 4: reportSchemaError("src-redefine.6.2.1", new Object[] { name.localpart }, elmNode); break; }  return null; }  return retObj; }
/* 1489 */   public XSDHandler() { this.fReportedTNS = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4080 */     this.xl = new SimpleLocator(); this.fHiddenNodes = new HashMap<>(); this.fSchemaParser = new SchemaDOMParser(new SchemaParsingConfig()); } protected void resolveKeyRefs() { for (int i = 0; i < this.fKeyrefStackPos; i++) { XSDocumentInfo keyrefSchemaDoc = this.fKeyrefsMapXSDocumentInfo[i]; keyrefSchemaDoc.fNamespaceSupport.makeGlobal(); keyrefSchemaDoc.fNamespaceSupport.setEffectiveContext(this.fKeyrefNamespaceContext[i]); SchemaGrammar keyrefGrammar = this.fGrammarBucket.getGrammar(keyrefSchemaDoc.fTargetNamespace); DOMUtil.setHidden(this.fKeyrefs[i], this.fHiddenNodes); this.fKeyrefTraverser.traverse(this.fKeyrefs[i], this.fKeyrefElems[i], keyrefSchemaDoc, keyrefGrammar); }  } protected Map getIDRegistry() { return this.fUnparsedIdentityConstraintRegistry; } protected Map getIDRegistry_sub() { return this.fUnparsedIdentityConstraintRegistrySub; } protected void storeKeyRef(Element keyrefToStore, XSDocumentInfo schemaDoc, XSElementDecl currElemDecl) { String keyrefName = DOMUtil.getAttrValue(keyrefToStore, SchemaSymbols.ATT_NAME); if (keyrefName.length() != 0) { String keyrefQName = (schemaDoc.fTargetNamespace == null) ? ("," + keyrefName) : (schemaDoc.fTargetNamespace + "," + keyrefName); checkForDuplicateNames(keyrefQName, 5, this.fUnparsedIdentityConstraintRegistry, this.fUnparsedIdentityConstraintRegistrySub, keyrefToStore, schemaDoc); }  if (this.fKeyrefStackPos == this.fKeyrefs.length) { Element[] elemArray = new Element[this.fKeyrefStackPos + 2]; System.arraycopy(this.fKeyrefs, 0, elemArray, 0, this.fKeyrefStackPos); this.fKeyrefs = elemArray; XSElementDecl[] declArray = new XSElementDecl[this.fKeyrefStackPos + 2]; System.arraycopy(this.fKeyrefElems, 0, declArray, 0, this.fKeyrefStackPos); this.fKeyrefElems = declArray; String[][] stringArray = new String[this.fKeyrefStackPos + 2][]; System.arraycopy(this.fKeyrefNamespaceContext, 0, stringArray, 0, this.fKeyrefStackPos); this.fKeyrefNamespaceContext = stringArray; XSDocumentInfo[] xsDocumentInfo = new XSDocumentInfo[this.fKeyrefStackPos + 2]; System.arraycopy(this.fKeyrefsMapXSDocumentInfo, 0, xsDocumentInfo, 0, this.fKeyrefStackPos); this.fKeyrefsMapXSDocumentInfo = xsDocumentInfo; }  this.fKeyrefs[this.fKeyrefStackPos] = keyrefToStore; this.fKeyrefElems[this.fKeyrefStackPos] = currElemDecl; this.fKeyrefNamespaceContext[this.fKeyrefStackPos] = schemaDoc.fNamespaceSupport.getEffectiveLocalContext(); this.fKeyrefsMapXSDocumentInfo[this.fKeyrefStackPos++] = schemaDoc; } private Element resolveSchema(XSDDescription desc, boolean mustResolve, Element referElement, boolean usePairs) { XMLInputSource schemaSource = null; try { Map<String, XMLSchemaLoader.LocationArray> pairs = usePairs ? this.fLocationPairs : Collections.<String, XMLSchemaLoader.LocationArray>emptyMap(); schemaSource = XMLSchemaLoader.resolveDocument(desc, pairs, this.fEntityManager); } catch (IOException ex) { if (mustResolve) { reportSchemaError("schema_reference.4", new Object[] { desc.getLocationHints()[0] }, referElement); } else { reportSchemaWarning("schema_reference.4", new Object[] { desc.getLocationHints()[0] }, referElement); }  }  if (schemaSource instanceof DOMInputSource) return getSchemaDocument(desc.getTargetNamespace(), (DOMInputSource)schemaSource, mustResolve, desc.getContextType(), referElement);  if (schemaSource instanceof SAXInputSource) return getSchemaDocument(desc.getTargetNamespace(), (SAXInputSource)schemaSource, mustResolve, desc.getContextType(), referElement);  if (schemaSource instanceof StAXInputSource) return getSchemaDocument(desc.getTargetNamespace(), (StAXInputSource)schemaSource, mustResolve, desc.getContextType(), referElement);  if (schemaSource instanceof XSInputSource) return getSchemaDocument((XSInputSource)schemaSource, desc);  return getSchemaDocument(desc.getTargetNamespace(), schemaSource, mustResolve, desc.getContextType(), referElement); } private Element resolveSchema(XMLInputSource schemaSource, XSDDescription desc, boolean mustResolve, Element referElement) { if (schemaSource instanceof DOMInputSource) return getSchemaDocument(desc.getTargetNamespace(), (DOMInputSource)schemaSource, mustResolve, desc.getContextType(), referElement);  if (schemaSource instanceof SAXInputSource) return getSchemaDocument(desc.getTargetNamespace(), (SAXInputSource)schemaSource, mustResolve, desc.getContextType(), referElement);  if (schemaSource instanceof StAXInputSource) return getSchemaDocument(desc.getTargetNamespace(), (StAXInputSource)schemaSource, mustResolve, desc.getContextType(), referElement);  if (schemaSource instanceof XSInputSource) return getSchemaDocument((XSInputSource)schemaSource, desc);  return getSchemaDocument(desc.getTargetNamespace(), schemaSource, mustResolve, desc.getContextType(), referElement); } private XMLInputSource resolveSchemaSource(XSDDescription desc, boolean mustResolve, Element referElement, boolean usePairs) { XMLInputSource schemaSource = null; try { Map<String, XMLSchemaLoader.LocationArray> pairs = usePairs ? this.fLocationPairs : Collections.<String, XMLSchemaLoader.LocationArray>emptyMap(); schemaSource = XMLSchemaLoader.resolveDocument(desc, pairs, this.fEntityManager); } catch (IOException ex) { if (mustResolve) { reportSchemaError("schema_reference.4", new Object[] { desc.getLocationHints()[0] }, referElement); } else { reportSchemaWarning("schema_reference.4", new Object[] { desc.getLocationHints()[0] }, referElement); }  }  return schemaSource; } private Element getSchemaDocument(String schemaNamespace, XMLInputSource schemaSource, boolean mustResolve, short referType, Element referElement) { boolean hasInput = true; IOException exception = null; Element schemaElement = null; try { if (schemaSource != null && (schemaSource.getSystemId() != null || schemaSource.getByteStream() != null || schemaSource.getCharacterStream() != null)) { XSDKey key = null; String schemaId = null; if (referType != 3) { schemaId = XMLEntityManager.expandSystemId(schemaSource.getSystemId(), schemaSource.getBaseSystemId(), false); key = new XSDKey(schemaId, referType, schemaNamespace); if ((schemaElement = this.fTraversed.get(key)) != null) { this.fLastSchemaWasDuplicate = true; return schemaElement; }  if (referType == 2 || referType == 0 || referType == 1) { String accessError = SecuritySupport.checkAccess(schemaId, this.fAccessExternalSchema, "all"); if (accessError != null) reportSchemaFatalError("schema_reference.access", new Object[] { SecuritySupport.sanitizePath(schemaId), accessError }, referElement);  }  }  this.fSchemaParser.parse(schemaSource); Document schemaDocument = this.fSchemaParser.getDocument(); schemaElement = (schemaDocument != null) ? DOMUtil.getRoot(schemaDocument) : null; return getSchemaDocument0(key, schemaId, schemaElement); }  hasInput = false; } catch (IOException ex) { exception = ex; }  return getSchemaDocument1(mustResolve, hasInput, schemaSource, referElement, exception); } private Element getSchemaDocument(String schemaNamespace, SAXInputSource schemaSource, boolean mustResolve, short referType, Element referElement) { XMLReader parser = schemaSource.getXMLReader(); InputSource inputSource = schemaSource.getInputSource(); boolean hasInput = true; IOException exception = null; Element schemaElement = null; try { if (inputSource != null && (inputSource.getSystemId() != null || inputSource.getByteStream() != null || inputSource.getCharacterStream() != null)) { XSDKey key = null; String schemaId = null; if (referType != 3) { schemaId = XMLEntityManager.expandSystemId(inputSource.getSystemId(), schemaSource.getBaseSystemId(), false); key = new XSDKey(schemaId, referType, schemaNamespace); if ((schemaElement = this.fTraversed.get(key)) != null) { this.fLastSchemaWasDuplicate = true; return schemaElement; }  }  boolean namespacePrefixes = false; if (parser != null) { try { namespacePrefixes = parser.getFeature("http://xml.org/sax/features/namespace-prefixes"); } catch (SAXException sAXException) {} } else { parser = JdkXmlUtils.getXMLReader(this.fOverrideDefaultParser, this.fSecurityManager.isSecureProcessing()); try { parser.setFeature("http://xml.org/sax/features/namespace-prefixes", true); namespacePrefixes = true; if (parser instanceof com.sun.org.apache.xerces.internal.parsers.SAXParser && this.fSecurityManager != null) parser.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);  } catch (SAXException sAXException) {} try { parser.setProperty("http://javax.xml.XMLConstants/property/accessExternalDTD", this.fAccessExternalDTD); } catch (SAXNotRecognizedException exc) { XMLSecurityManager.printWarning(parser.getClass().getName(), "http://javax.xml.XMLConstants/property/accessExternalDTD", exc); }  }  boolean stringsInternalized = false; try { stringsInternalized = parser.getFeature("http://xml.org/sax/features/string-interning"); } catch (SAXException sAXException) {} if (this.fXSContentHandler == null) this.fXSContentHandler = new SchemaContentHandler();  this.fXSContentHandler.reset(this.fSchemaParser, this.fSymbolTable, namespacePrefixes, stringsInternalized); parser.setContentHandler(this.fXSContentHandler); parser.setErrorHandler(this.fErrorReporter.getSAXErrorHandler()); parser.parse(inputSource); try { parser.setContentHandler(null); parser.setErrorHandler(null); } catch (Exception exception1) {} Document schemaDocument = this.fXSContentHandler.getDocument(); schemaElement = (schemaDocument != null) ? DOMUtil.getRoot(schemaDocument) : null; return getSchemaDocument0(key, schemaId, schemaElement); }  hasInput = false; } catch (SAXParseException spe) { throw SAX2XNIUtil.createXMLParseException0(spe); } catch (SAXException se) { throw SAX2XNIUtil.createXNIException0(se); } catch (IOException ioe) { exception = ioe; }  return getSchemaDocument1(mustResolve, hasInput, schemaSource, referElement, exception); } private Element getSchemaDocument(String schemaNamespace, DOMInputSource schemaSource, boolean mustResolve, short referType, Element referElement) { boolean hasInput = true; IOException exception = null; Element schemaElement = null; Element schemaRootElement = null; Node node = schemaSource.getNode(); short nodeType = -1; if (node != null) { nodeType = node.getNodeType(); if (nodeType == 9) { schemaRootElement = DOMUtil.getRoot((Document)node); } else if (nodeType == 1) { schemaRootElement = (Element)node; }  }  try { if (schemaRootElement != null) { XSDKey key = null; String schemaId = null; if (referType != 3) { schemaId = XMLEntityManager.expandSystemId(schemaSource.getSystemId(), schemaSource.getBaseSystemId(), false); boolean isDocument = (nodeType == 9); if (!isDocument) { Node parent = schemaRootElement.getParentNode(); if (parent != null) isDocument = (parent.getNodeType() == 9);  }  key = new XSDKey(schemaId, referType, schemaNamespace); if (isDocument && (schemaElement = this.fTraversed.get(key)) != null) { this.fLastSchemaWasDuplicate = true; return schemaElement; }  }  schemaElement = schemaRootElement; return getSchemaDocument0(key, schemaId, schemaElement); }  hasInput = false; } catch (IOException ioe) { exception = ioe; }  return getSchemaDocument1(mustResolve, hasInput, schemaSource, referElement, exception); } private Element getSchemaDocument(String schemaNamespace, StAXInputSource schemaSource, boolean mustResolve, short referType, Element referElement) { IOException exception = null; Element schemaElement = null; try { boolean consumeRemainingContent = schemaSource.shouldConsumeRemainingContent(); XMLStreamReader streamReader = schemaSource.getXMLStreamReader(); XMLEventReader eventReader = schemaSource.getXMLEventReader(); XSDKey key = null; String schemaId = null; if (referType != 3) { schemaId = XMLEntityManager.expandSystemId(schemaSource.getSystemId(), schemaSource.getBaseSystemId(), false); boolean isDocument = consumeRemainingContent; if (!isDocument) if (streamReader != null) { isDocument = (streamReader.getEventType() == 7); } else { isDocument = eventReader.peek().isStartDocument(); }   key = new XSDKey(schemaId, referType, schemaNamespace); if (isDocument && (schemaElement = this.fTraversed.get(key)) != null) { this.fLastSchemaWasDuplicate = true; return schemaElement; }  }  if (this.fStAXSchemaParser == null) this.fStAXSchemaParser = new StAXSchemaParser();  this.fStAXSchemaParser.reset(this.fSchemaParser, this.fSymbolTable); if (streamReader != null) { this.fStAXSchemaParser.parse(streamReader); if (consumeRemainingContent) while (streamReader.hasNext()) streamReader.next();   } else { this.fStAXSchemaParser.parse(eventReader); if (consumeRemainingContent) while (eventReader.hasNext()) eventReader.nextEvent();   }  Document schemaDocument = this.fStAXSchemaParser.getDocument(); schemaElement = (schemaDocument != null) ? DOMUtil.getRoot(schemaDocument) : null; return getSchemaDocument0(key, schemaId, schemaElement); } catch (XMLStreamException e) { StAXLocationWrapper slw = new StAXLocationWrapper(); slw.setLocation(e.getLocation()); throw new XMLParseException(slw, e.getMessage(), e); } catch (IOException e) { exception = e; return getSchemaDocument1(mustResolve, true, schemaSource, referElement, exception); }  } private Element getSchemaDocument0(XSDKey key, String schemaId, Element schemaElement) { if (key != null) this.fTraversed.put(key, schemaElement);  if (schemaId != null) this.fDoc2SystemId.put(schemaElement, schemaId);  this.fLastSchemaWasDuplicate = false; return schemaElement; } private Element getSchemaDocument1(boolean mustResolve, boolean hasInput, XMLInputSource schemaSource, Element referElement, IOException ioe) { if (mustResolve) { if (hasInput) { reportSchemaError("schema_reference.4", new Object[] { schemaSource.getSystemId() }, referElement, ioe); } else { reportSchemaError("schema_reference.4", new Object[] { (schemaSource == null) ? "" : schemaSource.getSystemId() }, referElement, ioe); }  } else if (hasInput) { reportSchemaWarning("schema_reference.4", new Object[] { schemaSource.getSystemId() }, referElement, ioe); }  this.fLastSchemaWasDuplicate = false; return null; } private Element getSchemaDocument(XSInputSource schemaSource, XSDDescription desc) { SchemaGrammar[] grammars = schemaSource.getGrammars(); short referType = desc.getContextType(); if (grammars != null && grammars.length > 0) { Vector expandedGrammars = expandGrammars(grammars); if (this.fNamespaceGrowth || !existingGrammars(expandedGrammars)) { addGrammars(expandedGrammars); if (referType == 3) desc.setTargetNamespace(grammars[0].getTargetNamespace());  }  } else { XSObject[] components = schemaSource.getComponents(); if (components != null && components.length > 0) { Map<String, Vector> importDependencies = new HashMap<>(); Vector expandedComponents = expandComponents(components, importDependencies); if (this.fNamespaceGrowth || canAddComponents(expandedComponents)) { addGlobalComponents(expandedComponents, importDependencies); if (referType == 3) desc.setTargetNamespace(components[0].getNamespace());  }  }  }  return null; } private Vector expandGrammars(SchemaGrammar[] grammars) { Vector<SchemaGrammar> currGrammars = new Vector(); for (int i = 0; i < grammars.length; i++) { if (!currGrammars.contains(grammars[i])) currGrammars.add(grammars[i]);  }  for (int j = 0; j < currGrammars.size(); j++) { SchemaGrammar sg1 = currGrammars.elementAt(j); Vector<SchemaGrammar> gs = sg1.getImportedGrammars(); if (gs != null) for (int k = gs.size() - 1; k >= 0; k--) { SchemaGrammar sg2 = gs.elementAt(k); if (!currGrammars.contains(sg2)) currGrammars.addElement(sg2);  }   }  return currGrammars; } private boolean existingGrammars(Vector<SchemaGrammar> grammars) { int length = grammars.size(); XSDDescription desc = new XSDDescription(); for (int i = 0; i < length; i++) { SchemaGrammar sg1 = grammars.elementAt(i); desc.setNamespace(sg1.getTargetNamespace()); SchemaGrammar sg2 = findGrammar(desc, false); if (sg2 != null) return true;  }  return false; } private boolean canAddComponents(Vector<XSObject> components) { int size = components.size(); XSDDescription desc = new XSDDescription(); for (int i = 0; i < size; i++) { XSObject component = components.elementAt(i); if (!canAddComponent(component, desc)) return false;  }  return true; } private boolean canAddComponent(XSObject component, XSDDescription desc) { desc.setNamespace(component.getNamespace()); SchemaGrammar sg = findGrammar(desc, false); if (sg == null) return true;  if (sg.isImmutable()) return false;  short componentType = component.getType(); String name = component.getName(); switch (componentType) { case 3: if (sg.getGlobalTypeDecl(name) == component) return true;  return false;case 1: if (sg.getGlobalAttributeDecl(name) == component) return true;  return false;case 5: if (sg.getGlobalAttributeDecl(name) == component) return true;  return false;case 2: if (sg.getGlobalElementDecl(name) == component) return true;  return false;case 6: if (sg.getGlobalGroupDecl(name) == component) return true;  return false;case 11: if (sg.getGlobalNotationDecl(name) == component) return true;  return false; }  return true; }
/*      */   private void addGrammars(Vector<SchemaGrammar> grammars) { int length = grammars.size(); XSDDescription desc = new XSDDescription(); for (int i = 0; i < length; i++) { SchemaGrammar sg1 = grammars.elementAt(i); desc.setNamespace(sg1.getTargetNamespace()); SchemaGrammar sg2 = findGrammar(desc, this.fNamespaceGrowth); if (sg1 != sg2) addGrammarComponents(sg1, sg2);  }  }
/*      */   private void addGrammarComponents(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { if (dstGrammar == null) { createGrammarFrom(srcGrammar); return; }  SchemaGrammar tmpGrammar = dstGrammar; if (tmpGrammar.isImmutable()) tmpGrammar = createGrammarFrom(dstGrammar);  addNewGrammarLocations(srcGrammar, tmpGrammar); addNewImportedGrammars(srcGrammar, tmpGrammar); addNewGrammarComponents(srcGrammar, tmpGrammar); }
/*      */   private SchemaGrammar createGrammarFrom(SchemaGrammar grammar) { SchemaGrammar newGrammar = new SchemaGrammar(grammar); this.fGrammarBucket.putGrammar(newGrammar); updateImportListWith(newGrammar); updateImportListFor(newGrammar); return newGrammar; }
/*      */   private void addNewGrammarLocations(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { StringList locations = srcGrammar.getDocumentLocations(); int locSize = locations.size(); StringList locations2 = dstGrammar.getDocumentLocations(); for (int i = 0; i < locSize; i++) { String loc = locations.item(i); if (!locations2.contains(loc)) dstGrammar.addDocument((Object)null, loc);  }  }
/*      */   private void addNewImportedGrammars(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { Vector igs1 = srcGrammar.getImportedGrammars(); if (igs1 != null) { Vector igs2 = dstGrammar.getImportedGrammars(); if (igs2 == null) { igs2 = (Vector)igs1.clone(); dstGrammar.setImportedGrammars(igs2); } else { updateImportList(igs1, igs2); }  }  }
/*      */   private void updateImportList(Vector<SchemaGrammar> importedSrc, Vector<SchemaGrammar> importedDst) { int size = importedSrc.size(); for (int i = 0; i < size; i++) { SchemaGrammar sg = importedSrc.elementAt(i); if (!containedImportedGrammar(importedDst, sg)) importedDst.add(sg);  }  }
/*      */   private void addNewGrammarComponents(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { dstGrammar.resetComponents(); addGlobalElementDecls(srcGrammar, dstGrammar); addGlobalAttributeDecls(srcGrammar, dstGrammar); addGlobalAttributeGroupDecls(srcGrammar, dstGrammar); addGlobalGroupDecls(srcGrammar, dstGrammar); addGlobalTypeDecls(srcGrammar, dstGrammar); addGlobalNotationDecls(srcGrammar, dstGrammar); }
/* 4088 */   public SimpleLocator element2Locator(Element e) { if (!(e instanceof ElementImpl)) {
/* 4089 */       return null;
/*      */     }
/* 4091 */     SimpleLocator l = new SimpleLocator();
/* 4092 */     return element2Locator(e, l) ? l : null; } private void addGlobalElementDecls(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { XSNamedMap components = srcGrammar.getComponents((short)2); int len = components.getLength(); for (int i = 0; i < len; i++) { XSElementDecl srcDecl = (XSElementDecl)components.item(i); XSElementDecl dstDecl = dstGrammar.getGlobalElementDecl(srcDecl.getName()); if (dstDecl == null) { dstGrammar.addGlobalElementDecl(srcDecl); } else if (dstDecl != srcDecl) {  }  }  ObjectList componentsExt = srcGrammar.getComponentsExt((short)2); len = componentsExt.getLength(); for (int j = 0; j < len; j += 2) { String key = (String)componentsExt.item(j); int index = key.indexOf(','); String location = key.substring(0, index); String name = key.substring(index + 1, key.length()); XSElementDecl srcDecl = (XSElementDecl)componentsExt.item(j + 1); XSElementDecl dstDecl = dstGrammar.getGlobalElementDecl(name, location); if (dstDecl == null) { dstGrammar.addGlobalElementDecl(srcDecl, location); } else if (dstDecl != srcDecl) {  }  }  } private void addGlobalAttributeDecls(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { XSNamedMap components = srcGrammar.getComponents((short)1); int len = components.getLength(); for (int i = 0; i < len; i++) { XSAttributeDecl srcDecl = (XSAttributeDecl)components.item(i); XSAttributeDecl dstDecl = dstGrammar.getGlobalAttributeDecl(srcDecl.getName()); if (dstDecl == null) { dstGrammar.addGlobalAttributeDecl(srcDecl); } else if (dstDecl != srcDecl && !this.fTolerateDuplicates) { reportSharingError(srcDecl.getNamespace(), srcDecl.getName()); }  }  ObjectList componentsExt = srcGrammar.getComponentsExt((short)1); len = componentsExt.getLength(); for (int j = 0; j < len; j += 2) { String key = (String)componentsExt.item(j); int index = key.indexOf(','); String location = key.substring(0, index); String name = key.substring(index + 1, key.length()); XSAttributeDecl srcDecl = (XSAttributeDecl)componentsExt.item(j + 1); XSAttributeDecl dstDecl = dstGrammar.getGlobalAttributeDecl(name, location); if (dstDecl == null) { dstGrammar.addGlobalAttributeDecl(srcDecl, location); } else if (dstDecl != srcDecl) {  }  }  } private void addGlobalAttributeGroupDecls(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { XSNamedMap components = srcGrammar.getComponents((short)5); int len = components.getLength(); for (int i = 0; i < len; i++) { XSAttributeGroupDecl srcDecl = (XSAttributeGroupDecl)components.item(i); XSAttributeGroupDecl dstDecl = dstGrammar.getGlobalAttributeGroupDecl(srcDecl.getName()); if (dstDecl == null) { dstGrammar.addGlobalAttributeGroupDecl(srcDecl); } else if (dstDecl != srcDecl && !this.fTolerateDuplicates) { reportSharingError(srcDecl.getNamespace(), srcDecl.getName()); }  }  ObjectList componentsExt = srcGrammar.getComponentsExt((short)5); len = componentsExt.getLength(); for (int j = 0; j < len; j += 2) { String key = (String)componentsExt.item(j); int index = key.indexOf(','); String location = key.substring(0, index); String name = key.substring(index + 1, key.length()); XSAttributeGroupDecl srcDecl = (XSAttributeGroupDecl)componentsExt.item(j + 1); XSAttributeGroupDecl dstDecl = dstGrammar.getGlobalAttributeGroupDecl(name, location); if (dstDecl == null) { dstGrammar.addGlobalAttributeGroupDecl(srcDecl, location); } else if (dstDecl != srcDecl) {  }  }  } private void addGlobalNotationDecls(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { XSNamedMap components = srcGrammar.getComponents((short)11); int len = components.getLength(); for (int i = 0; i < len; i++) { XSNotationDecl srcDecl = (XSNotationDecl)components.item(i); XSNotationDecl dstDecl = dstGrammar.getGlobalNotationDecl(srcDecl.getName()); if (dstDecl == null) { dstGrammar.addGlobalNotationDecl(srcDecl); } else if (dstDecl != srcDecl && !this.fTolerateDuplicates) { reportSharingError(srcDecl.getNamespace(), srcDecl.getName()); }  }  ObjectList componentsExt = srcGrammar.getComponentsExt((short)11); len = componentsExt.getLength(); for (int j = 0; j < len; j += 2) { String key = (String)componentsExt.item(j); int index = key.indexOf(','); String location = key.substring(0, index); String name = key.substring(index + 1, key.length()); XSNotationDecl srcDecl = (XSNotationDecl)componentsExt.item(j + 1); XSNotationDecl dstDecl = dstGrammar.getGlobalNotationDecl(name, location); if (dstDecl == null) { dstGrammar.addGlobalNotationDecl(srcDecl, location); } else if (dstDecl != srcDecl) {  }  }  } private void addGlobalGroupDecls(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { XSNamedMap components = srcGrammar.getComponents((short)6); int len = components.getLength(); for (int i = 0; i < len; i++) { XSGroupDecl srcDecl = (XSGroupDecl)components.item(i); XSGroupDecl dstDecl = dstGrammar.getGlobalGroupDecl(srcDecl.getName()); if (dstDecl == null) { dstGrammar.addGlobalGroupDecl(srcDecl); } else if (srcDecl != dstDecl && !this.fTolerateDuplicates) { reportSharingError(srcDecl.getNamespace(), srcDecl.getName()); }  }  ObjectList componentsExt = srcGrammar.getComponentsExt((short)6); len = componentsExt.getLength(); for (int j = 0; j < len; j += 2) { String key = (String)componentsExt.item(j); int index = key.indexOf(','); String location = key.substring(0, index); String name = key.substring(index + 1, key.length()); XSGroupDecl srcDecl = (XSGroupDecl)componentsExt.item(j + 1); XSGroupDecl dstDecl = dstGrammar.getGlobalGroupDecl(name, location); if (dstDecl == null) { dstGrammar.addGlobalGroupDecl(srcDecl, location); } else if (dstDecl != srcDecl) {  }  }  } private void addGlobalTypeDecls(SchemaGrammar srcGrammar, SchemaGrammar dstGrammar) { XSNamedMap components = srcGrammar.getComponents((short)3); int len = components.getLength(); for (int i = 0; i < len; i++) { XSTypeDefinition srcDecl = (XSTypeDefinition)components.item(i); XSTypeDefinition dstDecl = dstGrammar.getGlobalTypeDecl(srcDecl.getName()); if (dstDecl == null) { dstGrammar.addGlobalTypeDecl(srcDecl); } else if (dstDecl != srcDecl && !this.fTolerateDuplicates) { reportSharingError(srcDecl.getNamespace(), srcDecl.getName()); }  }  ObjectList componentsExt = srcGrammar.getComponentsExt((short)3); len = componentsExt.getLength(); for (int j = 0; j < len; j += 2) { String key = (String)componentsExt.item(j); int index = key.indexOf(','); String location = key.substring(0, index); String name = key.substring(index + 1, key.length()); XSTypeDefinition srcDecl = (XSTypeDefinition)componentsExt.item(j + 1); XSTypeDefinition dstDecl = dstGrammar.getGlobalTypeDecl(name, location); if (dstDecl == null) { dstGrammar.addGlobalTypeDecl(srcDecl, location); } else if (dstDecl != srcDecl) {  }  }  } private Vector expandComponents(XSObject[] components, Map<String, Vector> dependencies) { Vector<XSObject> newComponents = new Vector(); int i; for (i = 0; i < components.length; i++) { if (!newComponents.contains(components[i])) newComponents.add(components[i]);  }  for (i = 0; i < newComponents.size(); i++) { XSObject component = newComponents.elementAt(i); expandRelatedComponents(component, newComponents, dependencies); }  return newComponents; } private void expandRelatedComponents(XSObject component, Vector componentList, Map<String, Vector> dependencies) { short componentType = component.getType(); switch (componentType) { case 3: expandRelatedTypeComponents((XSTypeDefinition)component, componentList, component.getNamespace(), dependencies); break;case 1: expandRelatedAttributeComponents((XSAttributeDeclaration)component, componentList, component.getNamespace(), dependencies); break;case 5: expandRelatedAttributeGroupComponents((XSAttributeGroupDefinition)component, componentList, component.getNamespace(), dependencies);case 2: expandRelatedElementComponents((XSElementDeclaration)component, componentList, component.getNamespace(), dependencies); break;case 6: expandRelatedModelGroupDefinitionComponents((XSModelGroupDefinition)component, componentList, component.getNamespace(), dependencies); break; }  } private void expandRelatedAttributeComponents(XSAttributeDeclaration decl, Vector componentList, String namespace, Map<String, Vector> dependencies) { addRelatedType(decl.getTypeDefinition(), componentList, namespace, dependencies); } private void expandRelatedElementComponents(XSElementDeclaration decl, Vector componentList, String namespace, Map<String, Vector> dependencies) { addRelatedType(decl.getTypeDefinition(), componentList, namespace, dependencies); XSElementDeclaration subElemDecl = decl.getSubstitutionGroupAffiliation(); if (subElemDecl != null) addRelatedElement(subElemDecl, componentList, namespace, dependencies);  } private void expandRelatedTypeComponents(XSTypeDefinition type, Vector componentList, String namespace, Map<String, Vector> dependencies) { if (type instanceof XSComplexTypeDecl) { expandRelatedComplexTypeComponents((XSComplexTypeDecl)type, componentList, namespace, dependencies); } else if (type instanceof com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl) { expandRelatedSimpleTypeComponents((XSSimpleTypeDefinition)type, componentList, namespace, dependencies); }  } private void expandRelatedModelGroupDefinitionComponents(XSModelGroupDefinition modelGroupDef, Vector componentList, String namespace, Map<String, Vector> dependencies) { expandRelatedModelGroupComponents(modelGroupDef.getModelGroup(), componentList, namespace, dependencies); } private void expandRelatedAttributeGroupComponents(XSAttributeGroupDefinition attrGroup, Vector componentList, String namespace, Map<String, Vector> dependencies) { expandRelatedAttributeUsesComponents(attrGroup.getAttributeUses(), componentList, namespace, dependencies); } private void expandRelatedComplexTypeComponents(XSComplexTypeDecl type, Vector componentList, String namespace, Map<String, Vector> dependencies) { addRelatedType(type.getBaseType(), componentList, namespace, dependencies); expandRelatedAttributeUsesComponents(type.getAttributeUses(), componentList, namespace, dependencies); XSParticle particle = type.getParticle(); if (particle != null) expandRelatedParticleComponents(particle, componentList, namespace, dependencies);  } private void expandRelatedSimpleTypeComponents(XSSimpleTypeDefinition type, Vector componentList, String namespace, Map<String, Vector> dependencies) { XSTypeDefinition baseType = type.getBaseType(); if (baseType != null) addRelatedType(baseType, componentList, namespace, dependencies);  XSTypeDefinition itemType = type.getItemType(); if (itemType != null) addRelatedType(itemType, componentList, namespace, dependencies);  XSTypeDefinition primitiveType = type.getPrimitiveType(); if (primitiveType != null) addRelatedType(primitiveType, componentList, namespace, dependencies);  XSObjectList memberTypes = type.getMemberTypes(); if (memberTypes.size() > 0) for (int i = 0; i < memberTypes.size(); i++) addRelatedType((XSTypeDefinition)memberTypes.item(i), componentList, namespace, dependencies);   } private void expandRelatedAttributeUsesComponents(XSObjectList attrUses, Vector componentList, String namespace, Map<String, Vector> dependencies) { int attrUseSize = (attrUses == null) ? 0 : attrUses.size(); for (int i = 0; i < attrUseSize; i++) expandRelatedAttributeUseComponents((XSAttributeUse)attrUses.item(i), componentList, namespace, dependencies);  } private void expandRelatedAttributeUseComponents(XSAttributeUse component, Vector componentList, String namespace, Map<String, Vector> dependencies) { addRelatedAttribute(component.getAttrDeclaration(), componentList, namespace, dependencies); }
/*      */   private void expandRelatedParticleComponents(XSParticle component, Vector componentList, String namespace, Map<String, Vector> dependencies) { XSTerm term = component.getTerm(); switch (term.getType()) { case 2: addRelatedElement((XSElementDeclaration)term, componentList, namespace, dependencies); break;case 7: expandRelatedModelGroupComponents((XSModelGroup)term, componentList, namespace, dependencies); break; }  }
/*      */   private void expandRelatedModelGroupComponents(XSModelGroup modelGroup, Vector componentList, String namespace, Map<String, Vector> dependencies) { XSObjectList particles = modelGroup.getParticles(); int length = (particles == null) ? 0 : particles.getLength(); for (int i = 0; i < length; i++) expandRelatedParticleComponents((XSParticle)particles.item(i), componentList, namespace, dependencies);  }
/*      */   private void addRelatedType(XSTypeDefinition type, Vector<XSTypeDefinition> componentList, String namespace, Map<String, Vector> dependencies) { if (!type.getAnonymous()) { if (!type.getNamespace().equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && !componentList.contains(type)) { Vector importedNamespaces = findDependentNamespaces(namespace, dependencies); addNamespaceDependency(namespace, type.getNamespace(), importedNamespaces); componentList.add(type); }  } else { expandRelatedTypeComponents(type, componentList, namespace, dependencies); }  }
/*      */   private void addRelatedElement(XSElementDeclaration decl, Vector<XSElementDeclaration> componentList, String namespace, Map<String, Vector> dependencies) { if (decl.getScope() == 1) { if (!componentList.contains(decl)) { Vector importedNamespaces = findDependentNamespaces(namespace, dependencies); addNamespaceDependency(namespace, decl.getNamespace(), importedNamespaces); componentList.add(decl); }  } else { expandRelatedElementComponents(decl, componentList, namespace, dependencies); }  }
/*      */   private void addRelatedAttribute(XSAttributeDeclaration decl, Vector<XSAttributeDeclaration> componentList, String namespace, Map<String, Vector> dependencies) { if (decl.getScope() == 1) { if (!componentList.contains(decl)) { Vector importedNamespaces = findDependentNamespaces(namespace, dependencies); addNamespaceDependency(namespace, decl.getNamespace(), importedNamespaces); componentList.add(decl); }  } else { expandRelatedAttributeComponents(decl, componentList, namespace, dependencies); }  }
/*      */   private void addGlobalComponents(Vector<XSObject> components, Map<String, Vector> importDependencies) { XSDDescription desc = new XSDDescription(); int size = components.size(); for (int i = 0; i < size; i++) addGlobalComponent(components.elementAt(i), desc);  updateImportDependencies(importDependencies); }
/*      */   private void addGlobalComponent(XSObject component, XSDDescription desc) { String namespace = component.getNamespace(); desc.setNamespace(namespace); SchemaGrammar sg = getSchemaGrammar(desc); short componentType = component.getType(); String name = component.getName(); switch (componentType) { case 3: if (!((XSTypeDefinition)component).getAnonymous()) { if (sg.getGlobalTypeDecl(name) == null) sg.addGlobalTypeDecl((XSTypeDefinition)component);  if (sg.getGlobalTypeDecl(name, "") == null) sg.addGlobalTypeDecl((XSTypeDefinition)component, "");  }  break;case 1: if (((XSAttributeDecl)component).getScope() == 1) { if (sg.getGlobalAttributeDecl(name) == null) sg.addGlobalAttributeDecl((XSAttributeDecl)component);  if (sg.getGlobalAttributeDecl(name, "") == null) sg.addGlobalAttributeDecl((XSAttributeDecl)component, "");  }  break;case 5: if (sg.getGlobalAttributeDecl(name) == null) sg.addGlobalAttributeGroupDecl((XSAttributeGroupDecl)component);  if (sg.getGlobalAttributeDecl(name, "") == null) sg.addGlobalAttributeGroupDecl((XSAttributeGroupDecl)component, "");  break;case 2: if (((XSElementDecl)component).getScope() == 1) { sg.addGlobalElementDeclAll((XSElementDecl)component); if (sg.getGlobalElementDecl(name) == null) sg.addGlobalElementDecl((XSElementDecl)component);  if (sg.getGlobalElementDecl(name, "") == null) sg.addGlobalElementDecl((XSElementDecl)component, "");  }  break;case 6: if (sg.getGlobalGroupDecl(name) == null) sg.addGlobalGroupDecl((XSGroupDecl)component);  if (sg.getGlobalGroupDecl(name, "") == null) sg.addGlobalGroupDecl((XSGroupDecl)component, "");  break;case 11: if (sg.getGlobalNotationDecl(name) == null) sg.addGlobalNotationDecl((XSNotationDecl)component);  if (sg.getGlobalNotationDecl(name, "") == null) sg.addGlobalNotationDecl((XSNotationDecl)component, "");  break; }  }
/*      */   private void updateImportDependencies(Map<String, Vector> table) { if (table == null) return;  for (Map.Entry<String, Vector> entry : table.entrySet()) { String namespace = entry.getKey(); Vector importList = entry.getValue(); if (importList.size() > 0) expandImportList(namespace, importList);  }  }
/* 4101 */   public boolean element2Locator(Element e, SimpleLocator l) { if (l == null)
/* 4102 */       return false; 
/* 4103 */     if (e instanceof ElementImpl) {
/* 4104 */       ElementImpl ele = (ElementImpl)e;
/*      */       
/* 4106 */       Document doc = ele.getOwnerDocument();
/* 4107 */       String sid = this.fDoc2SystemId.get(DOMUtil.getRoot(doc));
/*      */       
/* 4109 */       int line = ele.getLineNumber();
/* 4110 */       int column = ele.getColumnNumber();
/* 4111 */       l.setValues(sid, sid, line, column, ele.getCharacterOffset());
/* 4112 */       return true;
/*      */     } 
/* 4114 */     return false; }
/*      */   private void expandImportList(String namespace, Vector namespaceList) { SchemaGrammar sg = this.fGrammarBucket.getGrammar(namespace); if (sg != null) { Vector isgs = sg.getImportedGrammars(); if (isgs == null) { isgs = new Vector(); addImportList(sg, isgs, namespaceList); sg.setImportedGrammars(isgs); } else { updateImportList(sg, isgs, namespaceList); }  }  }
/*      */   private void addImportList(SchemaGrammar sg, Vector<SchemaGrammar> importedGrammars, Vector<String> namespaceList) { int size = namespaceList.size(); for (int i = 0; i < size; i++) { SchemaGrammar isg = this.fGrammarBucket.getGrammar(namespaceList.elementAt(i)); if (isg != null) importedGrammars.add(isg);  }  }
/*      */   private void updateImportList(SchemaGrammar sg, Vector<SchemaGrammar> importedGrammars, Vector<String> namespaceList) { int size = namespaceList.size(); for (int i = 0; i < size; i++) { SchemaGrammar isg = this.fGrammarBucket.getGrammar(namespaceList.elementAt(i)); if (isg != null && !containedImportedGrammar(importedGrammars, isg)) importedGrammars.add(isg);  }  }
/* 4118 */   private boolean containedImportedGrammar(Vector<SchemaGrammar> importedGrammar, SchemaGrammar grammar) { int size = importedGrammar.size(); for (int i = 0; i < size; i++) { SchemaGrammar sg = importedGrammar.elementAt(i); if (null2EmptyString(sg.getTargetNamespace()).equals(null2EmptyString(grammar.getTargetNamespace()))) return true;  }  return false; } private SchemaGrammar getSchemaGrammar(XSDDescription desc) { SchemaGrammar sg = findGrammar(desc, this.fNamespaceGrowth); if (sg == null) { sg = new SchemaGrammar(desc.getNamespace(), desc.makeClone(), this.fSymbolTable); this.fGrammarBucket.putGrammar(sg); } else if (sg.isImmutable()) { sg = createGrammarFrom(sg); }  return sg; } private Vector findDependentNamespaces(String namespace, Map<String, Vector> table) { String ns = null2EmptyString(namespace); Vector namespaceList = (Vector)getFromMap(table, ns); if (namespaceList == null) { namespaceList = new Vector(); table.put(ns, namespaceList); }  return namespaceList; } private void addNamespaceDependency(String namespace1, String namespace2, Vector<String> list) { String ns1 = null2EmptyString(namespace1); String ns2 = null2EmptyString(namespace2); if (!ns1.equals(ns2) && !list.contains(ns2)) list.add(ns2);  } private void reportSharingError(String namespace, String name) { String qName = (namespace == null) ? ("," + name) : (namespace + "," + name); reportSchemaError("sch-props-correct.2", new Object[] { qName }, null); } private void createTraversers() { this.fAttributeChecker = new XSAttributeChecker(this); this.fAttributeGroupTraverser = new XSDAttributeGroupTraverser(this, this.fAttributeChecker); this.fAttributeTraverser = new XSDAttributeTraverser(this, this.fAttributeChecker); this.fComplexTypeTraverser = new XSDComplexTypeTraverser(this, this.fAttributeChecker); this.fElementTraverser = new XSDElementTraverser(this, this.fAttributeChecker); this.fGroupTraverser = new XSDGroupTraverser(this, this.fAttributeChecker); this.fKeyrefTraverser = new XSDKeyrefTraverser(this, this.fAttributeChecker); this.fNotationTraverser = new XSDNotationTraverser(this, this.fAttributeChecker); this.fSimpleTypeTraverser = new XSDSimpleTypeTraverser(this, this.fAttributeChecker); this.fUniqueOrKeyTraverser = new XSDUniqueOrKeyTraverser(this, this.fAttributeChecker); this.fWildCardTraverser = new XSDWildcardTraverser(this, this.fAttributeChecker); } void prepareForParse() { this.fTraversed.clear(); this.fDoc2SystemId.clear(); this.fHiddenNodes.clear(); this.fLastSchemaWasDuplicate = false; } void prepareForTraverse() { if (!this.registryEmpty) { this.fUnparsedAttributeRegistry.clear(); this.fUnparsedAttributeGroupRegistry.clear(); this.fUnparsedElementRegistry.clear(); this.fUnparsedGroupRegistry.clear(); this.fUnparsedIdentityConstraintRegistry.clear(); this.fUnparsedNotationRegistry.clear(); this.fUnparsedTypeRegistry.clear(); this.fUnparsedAttributeRegistrySub.clear(); this.fUnparsedAttributeGroupRegistrySub.clear(); this.fUnparsedElementRegistrySub.clear(); this.fUnparsedGroupRegistrySub.clear(); this.fUnparsedIdentityConstraintRegistrySub.clear(); this.fUnparsedNotationRegistrySub.clear(); this.fUnparsedTypeRegistrySub.clear(); }  int i; for (i = 1; i <= 7; i++) { if (this.fUnparsedRegistriesExt[i] != null) this.fUnparsedRegistriesExt[i].clear();  }  this.fDependencyMap.clear(); this.fDoc2XSDocumentMap.clear(); if (this.fRedefine2XSDMap != null) this.fRedefine2XSDMap.clear();  if (this.fRedefine2NSSupport != null) this.fRedefine2NSSupport.clear();  this.fAllTNSs.removeAllElements(); this.fImportMap.clear(); this.fRoot = null; for (i = 0; i < this.fLocalElemStackPos; i++) { this.fParticle[i] = null; this.fLocalElementDecl[i] = null; this.fLocalElementDecl_schema[i] = null; this.fLocalElemNamespaceContext[i] = null; }  this.fLocalElemStackPos = 0; for (i = 0; i < this.fKeyrefStackPos; i++) { this.fKeyrefs[i] = null; this.fKeyrefElems[i] = null; this.fKeyrefNamespaceContext[i] = null; this.fKeyrefsMapXSDocumentInfo[i] = null; }  this.fKeyrefStackPos = 0; if (this.fAttributeChecker == null) createTraversers();  Locale locale = this.fErrorReporter.getLocale(); this.fAttributeChecker.reset(this.fSymbolTable); this.fAttributeGroupTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fAttributeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fComplexTypeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fElementTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fGroupTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fKeyrefTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fNotationTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fSimpleTypeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fUniqueOrKeyTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fWildCardTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale); this.fRedefinedRestrictedAttributeGroupRegistry.clear(); this.fRedefinedRestrictedGroupRegistry.clear(); this.fGlobalAttrDecls.clear(); this.fGlobalAttrGrpDecls.clear(); this.fGlobalElemDecls.clear(); this.fGlobalGroupDecls.clear(); this.fGlobalNotationDecls.clear(); this.fGlobalIDConstraintDecls.clear(); this.fGlobalTypeDecls.clear(); } public void setDeclPool(XSDeclarationPool declPool) { this.fDeclPool = declPool; } public void setDVFactory(SchemaDVFactory dvFactory) { this.fDVFactory = dvFactory; } private Element getElementFromMap(Map<String, Element> registry, String declKey) { if (registry == null) return null; 
/* 4119 */     return registry.get(declKey); }
/*      */   public SchemaDVFactory getDVFactory() { return this.fDVFactory; }
/*      */   public void reset(XMLComponentManager componentManager) { this.fSymbolTable = (SymbolTable)componentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"); this.fSecurityManager = (XMLSecurityManager)componentManager.getProperty("http://apache.org/xml/properties/security-manager", null); this.fEntityManager = (XMLEntityResolver)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-manager"); XMLEntityResolver er = (XMLEntityResolver)componentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"); if (er != null) this.fSchemaParser.setEntityResolver(er);  this.fErrorReporter = (XMLErrorReporter)componentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"); this.fErrorHandler = this.fErrorReporter.getErrorHandler(); this.fLocale = this.fErrorReporter.getLocale(); this.fValidateAnnotations = componentManager.getFeature("http://apache.org/xml/features/validate-annotations", false); this.fHonourAllSchemaLocations = componentManager.getFeature("http://apache.org/xml/features/honour-all-schemaLocations", false); this.fNamespaceGrowth = componentManager.getFeature("http://apache.org/xml/features/namespace-growth", false); this.fTolerateDuplicates = componentManager.getFeature("http://apache.org/xml/features/internal/tolerate-duplicates", false); try { if (this.fErrorHandler != this.fSchemaParser.getProperty("http://apache.org/xml/properties/internal/error-handler")) { this.fSchemaParser.setProperty("http://apache.org/xml/properties/internal/error-handler", (this.fErrorHandler != null) ? this.fErrorHandler : new DefaultErrorHandler()); if (this.fAnnotationValidator != null) this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/error-handler", (this.fErrorHandler != null) ? this.fErrorHandler : new DefaultErrorHandler());  }  if (this.fLocale != this.fSchemaParser.getProperty("http://apache.org/xml/properties/locale")) { this.fSchemaParser.setProperty("http://apache.org/xml/properties/locale", this.fLocale); if (this.fAnnotationValidator != null) this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/locale", this.fLocale);  }  } catch (XMLConfigurationException xMLConfigurationException) {} try { this.fSchemaParser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", this.fErrorReporter.getFeature("http://apache.org/xml/features/continue-after-fatal-error")); } catch (XMLConfigurationException xMLConfigurationException) {} try { if (componentManager.getFeature("http://apache.org/xml/features/allow-java-encodings", false)) this.fSchemaParser.setFeature("http://apache.org/xml/features/allow-java-encodings", true);  } catch (XMLConfigurationException xMLConfigurationException) {} try { if (componentManager.getFeature("http://apache.org/xml/features/standard-uri-conformant", false)) this.fSchemaParser.setFeature("http://apache.org/xml/features/standard-uri-conformant", true);  } catch (XMLConfigurationException xMLConfigurationException) {} try { this.fGrammarPool = (XMLGrammarPool)componentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool"); } catch (XMLConfigurationException e) { this.fGrammarPool = null; }  try { if (componentManager.getFeature("http://apache.org/xml/features/disallow-doctype-decl", false)) this.fSchemaParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);  } catch (XMLConfigurationException xMLConfigurationException) {} try { if (this.fSecurityManager != null) this.fSchemaParser.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);  } catch (XMLConfigurationException xMLConfigurationException) {} this.fSecurityPropertyMgr = (XMLSecurityPropertyManager)componentManager.getProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager"); this.fSchemaParser.setProperty("http://www.oracle.com/xml/jaxp/properties/xmlSecurityPropertyManager", this.fSecurityPropertyMgr); this.fAccessExternalDTD = this.fSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD); this.fAccessExternalSchema = this.fSecurityPropertyMgr.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA); this.fOverrideDefaultParser = componentManager.getFeature("jdk.xml.overrideDefaultParser"); this.fSchemaParser.setFeature("jdk.xml.overrideDefaultParser", this.fOverrideDefaultParser); }
/*      */   void traverseLocalElements() { this.fElementTraverser.fDeferTraversingLocalElements = false; for (int i = 0; i < this.fLocalElemStackPos; i++) { Element currElem = this.fLocalElementDecl[i]; XSDocumentInfo currSchema = this.fLocalElementDecl_schema[i]; SchemaGrammar currGrammar = this.fGrammarBucket.getGrammar(currSchema.fTargetNamespace); this.fElementTraverser.traverseLocal(this.fParticle[i], currElem, currSchema, currGrammar, this.fAllContext[i], this.fParent[i], this.fLocalElemNamespaceContext[i]); if ((this.fParticle[i]).fType == 0) { XSModelGroupImpl group = null; if (this.fParent[i] instanceof XSComplexTypeDecl) { XSParticle p = ((XSComplexTypeDecl)this.fParent[i]).getParticle(); if (p != null) group = (XSModelGroupImpl)p.getTerm();  } else { group = ((XSGroupDecl)this.fParent[i]).fModelGroup; }  if (group != null) removeParticle(group, this.fParticle[i]);  }  }  }
/* 4123 */   private boolean removeParticle(XSModelGroupImpl group, XSParticleDecl particle) { for (int i = 0; i < group.fParticleCount; i++) { XSParticleDecl member = group.fParticles[i]; if (member == particle) { for (int j = i; j < group.fParticleCount - 1; j++) group.fParticles[j] = group.fParticles[j + 1];  group.fParticleCount--; return true; }  if (member.fType == 3 && removeParticle((XSModelGroupImpl)member.fValue, particle)) return true;  }  return false; } void fillInLocalElemInfo(Element elmDecl, XSDocumentInfo schemaDoc, int allContextFlags, XSObject parent, XSParticleDecl particle) { if (this.fParticle.length == this.fLocalElemStackPos) { XSParticleDecl[] newStackP = new XSParticleDecl[this.fLocalElemStackPos + 10]; System.arraycopy(this.fParticle, 0, newStackP, 0, this.fLocalElemStackPos); this.fParticle = newStackP; Element[] newStackE = new Element[this.fLocalElemStackPos + 10]; System.arraycopy(this.fLocalElementDecl, 0, newStackE, 0, this.fLocalElemStackPos); this.fLocalElementDecl = newStackE; XSDocumentInfo[] newStackE_schema = new XSDocumentInfo[this.fLocalElemStackPos + 10]; System.arraycopy(this.fLocalElementDecl_schema, 0, newStackE_schema, 0, this.fLocalElemStackPos); this.fLocalElementDecl_schema = newStackE_schema; int[] newStackI = new int[this.fLocalElemStackPos + 10]; System.arraycopy(this.fAllContext, 0, newStackI, 0, this.fLocalElemStackPos); this.fAllContext = newStackI; XSObject[] newStackC = new XSObject[this.fLocalElemStackPos + 10]; System.arraycopy(this.fParent, 0, newStackC, 0, this.fLocalElemStackPos); this.fParent = newStackC; String[][] newStackN = new String[this.fLocalElemStackPos + 10][]; System.arraycopy(this.fLocalElemNamespaceContext, 0, newStackN, 0, this.fLocalElemStackPos); this.fLocalElemNamespaceContext = newStackN; }  this.fParticle[this.fLocalElemStackPos] = particle; this.fLocalElementDecl[this.fLocalElemStackPos] = elmDecl; this.fLocalElementDecl_schema[this.fLocalElemStackPos] = schemaDoc; this.fAllContext[this.fLocalElemStackPos] = allContextFlags; this.fParent[this.fLocalElemStackPos] = parent; this.fLocalElemNamespaceContext[this.fLocalElemStackPos++] = schemaDoc.fNamespaceSupport.getEffectiveLocalContext(); } void checkForDuplicateNames(String qName, int declType, Map<String, Element> registry, Map<String, XSDocumentInfo> registry_sub, Element currComp, XSDocumentInfo currSchema) { Object objElem = null; if ((objElem = registry.get(qName)) == null) { if (this.fNamespaceGrowth && !this.fTolerateDuplicates) checkForDuplicateNames(qName, declType, currComp);  registry.put(qName, currComp); registry_sub.put(qName, currSchema); } else { Element collidingElem = (Element)objElem; XSDocumentInfo collidingElemSchema = registry_sub.get(qName); if (collidingElem == currComp) return;  Element elemParent = null; XSDocumentInfo redefinedSchema = null; boolean collidedWithRedefine = true; if (DOMUtil.getLocalName(elemParent = DOMUtil.getParent(collidingElem)).equals(SchemaSymbols.ELT_REDEFINE)) { redefinedSchema = (this.fRedefine2XSDMap != null) ? (XSDocumentInfo)this.fRedefine2XSDMap.get(elemParent) : null; } else if (DOMUtil.getLocalName(DOMUtil.getParent(currComp)).equals(SchemaSymbols.ELT_REDEFINE)) { redefinedSchema = collidingElemSchema; collidedWithRedefine = false; }  if (redefinedSchema != null) { if (collidingElemSchema == currSchema) { reportSchemaError("sch-props-correct.2", new Object[] { qName }, currComp); return; }  String newName = qName.substring(qName.lastIndexOf(',') + 1) + "_fn3dktizrknc9pi"; if (redefinedSchema == currSchema) { currComp.setAttribute(SchemaSymbols.ATT_NAME, newName); if (currSchema.fTargetNamespace == null) { registry.put("," + newName, currComp); registry_sub.put("," + newName, currSchema); } else { registry.put(currSchema.fTargetNamespace + "," + newName, currComp); registry_sub.put(currSchema.fTargetNamespace + "," + newName, currSchema); }  if (currSchema.fTargetNamespace == null) { checkForDuplicateNames("," + newName, declType, registry, registry_sub, currComp, currSchema); } else { checkForDuplicateNames(currSchema.fTargetNamespace + "," + newName, declType, registry, registry_sub, currComp, currSchema); }  } else if (collidedWithRedefine) { if (currSchema.fTargetNamespace == null) { checkForDuplicateNames("," + newName, declType, registry, registry_sub, currComp, currSchema); } else { checkForDuplicateNames(currSchema.fTargetNamespace + "," + newName, declType, registry, registry_sub, currComp, currSchema); }  } else { reportSchemaError("sch-props-correct.2", new Object[] { qName }, currComp); }  } else if (!this.fTolerateDuplicates) { reportSchemaError("sch-props-correct.2", new Object[] { qName }, currComp); } else if (this.fUnparsedRegistriesExt[declType] != null && this.fUnparsedRegistriesExt[declType].get(qName) == currSchema) { reportSchemaError("sch-props-correct.2", new Object[] { qName }, currComp); }  }  if (this.fTolerateDuplicates) { if (this.fUnparsedRegistriesExt[declType] == null) this.fUnparsedRegistriesExt[declType] = new HashMap<>();  this.fUnparsedRegistriesExt[declType].put(qName, currSchema); }  } void checkForDuplicateNames(String qName, int declType, Element currComp) { int namespaceEnd = qName.indexOf(','); String namespace = qName.substring(0, namespaceEnd); SchemaGrammar grammar = this.fGrammarBucket.getGrammar(emptyString2Null(namespace)); if (grammar != null) { Object obj = getGlobalDeclFromGrammar(grammar, declType, qName.substring(namespaceEnd + 1)); if (obj != null) reportSchemaError("sch-props-correct.2", new Object[] { qName }, currComp);  }  } private void renameRedefiningComponents(XSDocumentInfo currSchema, Element child, String componentType, String oldName, String newName) { if (componentType.equals(SchemaSymbols.ELT_SIMPLETYPE)) { Element grandKid = DOMUtil.getFirstChildElement(child); if (grandKid == null) { reportSchemaError("src-redefine.5.a.a", null, child); } else { String grandKidName = DOMUtil.getLocalName(grandKid); if (grandKidName.equals(SchemaSymbols.ELT_ANNOTATION)) grandKid = DOMUtil.getNextSiblingElement(grandKid);  if (grandKid == null) { reportSchemaError("src-redefine.5.a.a", null, child); } else { grandKidName = DOMUtil.getLocalName(grandKid); if (!grandKidName.equals(SchemaSymbols.ELT_RESTRICTION)) { reportSchemaError("src-redefine.5.a.b", new Object[] { grandKidName }, child); } else { Object[] attrs = this.fAttributeChecker.checkAttributes(grandKid, false, currSchema); QName derivedBase = (QName)attrs[XSAttributeChecker.ATTIDX_BASE]; if (derivedBase == null || derivedBase.uri != currSchema.fTargetNamespace || !derivedBase.localpart.equals(oldName)) { reportSchemaError("src-redefine.5.a.c", new Object[] { grandKidName, ((currSchema.fTargetNamespace == null) ? "" : currSchema.fTargetNamespace) + "," + oldName }, child); } else if (derivedBase.prefix != null && derivedBase.prefix.length() > 0) { grandKid.setAttribute(SchemaSymbols.ATT_BASE, derivedBase.prefix + ":" + newName); } else { grandKid.setAttribute(SchemaSymbols.ATT_BASE, newName); }  this.fAttributeChecker.returnAttrArray(attrs, currSchema); }  }  }  } else if (componentType.equals(SchemaSymbols.ELT_COMPLEXTYPE)) { Element grandKid = DOMUtil.getFirstChildElement(child); if (grandKid == null) { reportSchemaError("src-redefine.5.b.a", null, child); } else { if (DOMUtil.getLocalName(grandKid).equals(SchemaSymbols.ELT_ANNOTATION)) grandKid = DOMUtil.getNextSiblingElement(grandKid);  if (grandKid == null) { reportSchemaError("src-redefine.5.b.a", null, child); } else { Element greatGrandKid = DOMUtil.getFirstChildElement(grandKid); if (greatGrandKid == null) { reportSchemaError("src-redefine.5.b.b", null, grandKid); } else { String greatGrandKidName = DOMUtil.getLocalName(greatGrandKid); if (greatGrandKidName.equals(SchemaSymbols.ELT_ANNOTATION)) greatGrandKid = DOMUtil.getNextSiblingElement(greatGrandKid);  if (greatGrandKid == null) { reportSchemaError("src-redefine.5.b.b", null, grandKid); } else { greatGrandKidName = DOMUtil.getLocalName(greatGrandKid); if (!greatGrandKidName.equals(SchemaSymbols.ELT_RESTRICTION) && !greatGrandKidName.equals(SchemaSymbols.ELT_EXTENSION)) { reportSchemaError("src-redefine.5.b.c", new Object[] { greatGrandKidName }, greatGrandKid); } else { Object[] attrs = this.fAttributeChecker.checkAttributes(greatGrandKid, false, currSchema); QName derivedBase = (QName)attrs[XSAttributeChecker.ATTIDX_BASE]; if (derivedBase == null || derivedBase.uri != currSchema.fTargetNamespace || !derivedBase.localpart.equals(oldName)) { reportSchemaError("src-redefine.5.b.d", new Object[] { greatGrandKidName, ((currSchema.fTargetNamespace == null) ? "" : currSchema.fTargetNamespace) + "," + oldName }, greatGrandKid); } else if (derivedBase.prefix != null && derivedBase.prefix.length() > 0) { greatGrandKid.setAttribute(SchemaSymbols.ATT_BASE, derivedBase.prefix + ":" + newName); } else { greatGrandKid.setAttribute(SchemaSymbols.ATT_BASE, newName); }  }  }  }  }  }  } else if (componentType.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) { String processedBaseName = (currSchema.fTargetNamespace == null) ? ("," + oldName) : (currSchema.fTargetNamespace + "," + oldName); int attGroupRefsCount = changeRedefineGroup(processedBaseName, componentType, newName, child, currSchema); if (attGroupRefsCount > 1) { reportSchemaError("src-redefine.7.1", new Object[] { new Integer(attGroupRefsCount) }, child); } else if (attGroupRefsCount != 1) { if (currSchema.fTargetNamespace == null) { this.fRedefinedRestrictedAttributeGroupRegistry.put(processedBaseName, "," + newName); } else { this.fRedefinedRestrictedAttributeGroupRegistry.put(processedBaseName, currSchema.fTargetNamespace + "," + newName); }  }  } else if (componentType.equals(SchemaSymbols.ELT_GROUP)) { String processedBaseName = (currSchema.fTargetNamespace == null) ? ("," + oldName) : (currSchema.fTargetNamespace + "," + oldName); int groupRefsCount = changeRedefineGroup(processedBaseName, componentType, newName, child, currSchema); if (groupRefsCount > 1) { reportSchemaError("src-redefine.6.1.1", new Object[] { new Integer(groupRefsCount) }, child); } else if (groupRefsCount != 1) { if (currSchema.fTargetNamespace == null) { this.fRedefinedRestrictedGroupRegistry.put(processedBaseName, "," + newName); } else { this.fRedefinedRestrictedGroupRegistry.put(processedBaseName, currSchema.fTargetNamespace + "," + newName); }  }  } else { reportSchemaError("Internal-Error", new Object[] { "could not handle this particular <redefine>; please submit your schemas and instance document in a bug report!" }, child); }  } private String findQName(String name, XSDocumentInfo schemaDoc) { SchemaNamespaceSupport currNSMap = schemaDoc.fNamespaceSupport; int colonPtr = name.indexOf(':'); String prefix = XMLSymbols.EMPTY_STRING; if (colonPtr > 0) prefix = name.substring(0, colonPtr);  String uri = currNSMap.getURI(this.fSymbolTable.addSymbol(prefix)); String localpart = (colonPtr == 0) ? name : name.substring(colonPtr + 1); if (prefix == XMLSymbols.EMPTY_STRING && uri == null && schemaDoc.fIsChameleonSchema) uri = schemaDoc.fTargetNamespace;  if (uri == null) return "," + localpart;  return uri + "," + localpart; } private int changeRedefineGroup(String originalQName, String elementSought, String newName, Element curr, XSDocumentInfo schemaDoc) { int result = 0; Element child = DOMUtil.getFirstChildElement(curr); for (; child != null; child = DOMUtil.getNextSiblingElement(child)) { String name = DOMUtil.getLocalName(child); if (!name.equals(elementSought)) { result += changeRedefineGroup(originalQName, elementSought, newName, child, schemaDoc); } else { String ref = child.getAttribute(SchemaSymbols.ATT_REF); if (ref.length() != 0) { String processedRef = findQName(ref, schemaDoc); if (originalQName.equals(processedRef)) { String prefix = XMLSymbols.EMPTY_STRING; int colonptr = ref.indexOf(":"); if (colonptr > 0) { prefix = ref.substring(0, colonptr); child.setAttribute(SchemaSymbols.ATT_REF, prefix + ":" + newName); } else { child.setAttribute(SchemaSymbols.ATT_REF, newName); }  result++; if (elementSought.equals(SchemaSymbols.ELT_GROUP)) { String minOccurs = child.getAttribute(SchemaSymbols.ATT_MINOCCURS); String maxOccurs = child.getAttribute(SchemaSymbols.ATT_MAXOCCURS); if ((maxOccurs.length() != 0 && !maxOccurs.equals("1")) || (minOccurs.length() != 0 && !minOccurs.equals("1"))) reportSchemaError("src-redefine.6.1.2", new Object[] { ref }, child);  }  }  }  }  }  return result; } private XSDocumentInfo findXSDocumentForDecl(XSDocumentInfo currSchema, Element decl, XSDocumentInfo decl_Doc) { Object temp = decl_Doc; if (temp == null) return null;  XSDocumentInfo declDocInfo = (XSDocumentInfo)temp; return declDocInfo; } private boolean nonAnnotationContent(Element elem) { for (Element child = DOMUtil.getFirstChildElement(elem); child != null; child = DOMUtil.getNextSiblingElement(child)) { if (!DOMUtil.getLocalName(child).equals(SchemaSymbols.ELT_ANNOTATION)) return true;  }  return false; } private void setSchemasVisible(XSDocumentInfo startSchema) { if (DOMUtil.isHidden(startSchema.fSchemaElement, this.fHiddenNodes)) { DOMUtil.setVisible(startSchema.fSchemaElement, this.fHiddenNodes); Vector<XSDocumentInfo> dependingSchemas = this.fDependencyMap.get(startSchema); for (int i = 0; i < dependingSchemas.size(); i++) setSchemasVisible(dependingSchemas.elementAt(i));  }  } private XSDocumentInfo getDocInfoFromMap(Map<String, XSDocumentInfo> registry, String declKey) { if (registry == null) return null; 
/* 4124 */     return registry.get(declKey); }
/*      */ 
/*      */   
/*      */   private Object getFromMap(Map registry, String key) {
/* 4128 */     if (registry == null) return null; 
/* 4129 */     return registry.get(key);
/*      */   }
/*      */   
/*      */   void reportSchemaFatalError(String key, Object[] args, Element ele) {
/* 4133 */     reportSchemaErr(key, args, ele, (short)2, null);
/*      */   }
/*      */   
/*      */   void reportSchemaError(String key, Object[] args, Element ele) {
/* 4137 */     reportSchemaErr(key, args, ele, (short)1, null);
/*      */   }
/*      */   
/*      */   void reportSchemaError(String key, Object[] args, Element ele, Exception exception) {
/* 4141 */     reportSchemaErr(key, args, ele, (short)1, exception);
/*      */   }
/*      */   
/*      */   void reportSchemaWarning(String key, Object[] args, Element ele) {
/* 4145 */     reportSchemaErr(key, args, ele, (short)0, null);
/*      */   }
/*      */   
/*      */   void reportSchemaWarning(String key, Object[] args, Element ele, Exception exception) {
/* 4149 */     reportSchemaErr(key, args, ele, (short)0, exception);
/*      */   }
/*      */   
/*      */   void reportSchemaErr(String key, Object[] args, Element ele, short type, Exception exception) {
/* 4153 */     if (element2Locator(ele, this.xl)) {
/* 4154 */       this.fErrorReporter.reportError(this.xl, "http://www.w3.org/TR/xml-schema-1", key, args, type, exception);
/*      */     }
/*      */     else {
/*      */       
/* 4158 */       this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", key, args, type, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static class XSAnnotationGrammarPool
/*      */     implements XMLGrammarPool
/*      */   {
/*      */     private XSGrammarBucket fGrammarBucket;
/*      */     
/*      */     private Grammar[] fInitialGrammarSet;
/*      */ 
/*      */     
/*      */     private XSAnnotationGrammarPool() {}
/*      */ 
/*      */     
/*      */     public Grammar[] retrieveInitialGrammarSet(String grammarType) {
/* 4175 */       if (grammarType == "http://www.w3.org/2001/XMLSchema") {
/* 4176 */         if (this.fInitialGrammarSet == null) {
/* 4177 */           if (this.fGrammarBucket == null) {
/* 4178 */             this.fInitialGrammarSet = new Grammar[] { SchemaGrammar.Schema4Annotations.INSTANCE };
/*      */           } else {
/*      */             
/* 4181 */             SchemaGrammar[] schemaGrammars = this.fGrammarBucket.getGrammars();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4187 */             for (int i = 0; i < schemaGrammars.length; i++) {
/* 4188 */               if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(schemaGrammars[i].getTargetNamespace())) {
/* 4189 */                 this.fInitialGrammarSet = (Grammar[])schemaGrammars;
/* 4190 */                 return this.fInitialGrammarSet;
/*      */               } 
/*      */             } 
/* 4193 */             Grammar[] grammars = new Grammar[schemaGrammars.length + 1];
/* 4194 */             System.arraycopy(schemaGrammars, 0, grammars, 0, schemaGrammars.length);
/* 4195 */             grammars[grammars.length - 1] = SchemaGrammar.Schema4Annotations.INSTANCE;
/* 4196 */             this.fInitialGrammarSet = grammars;
/*      */           } 
/*      */         }
/* 4199 */         return this.fInitialGrammarSet;
/*      */       } 
/* 4201 */       return new Grammar[0];
/*      */     }
/*      */ 
/*      */     
/*      */     public void cacheGrammars(String grammarType, Grammar[] grammars) {}
/*      */ 
/*      */     
/*      */     public Grammar retrieveGrammar(XMLGrammarDescription desc) {
/* 4209 */       if (desc.getGrammarType() == "http://www.w3.org/2001/XMLSchema") {
/* 4210 */         String tns = ((XMLSchemaDescription)desc).getTargetNamespace();
/* 4211 */         if (this.fGrammarBucket != null) {
/* 4212 */           Grammar grammar = this.fGrammarBucket.getGrammar(tns);
/* 4213 */           if (grammar != null) {
/* 4214 */             return grammar;
/*      */           }
/*      */         } 
/* 4217 */         if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(tns)) {
/* 4218 */           return SchemaGrammar.Schema4Annotations.INSTANCE;
/*      */         }
/*      */       } 
/* 4221 */       return null;
/*      */     }
/*      */     
/*      */     public void refreshGrammars(XSGrammarBucket gBucket) {
/* 4225 */       this.fGrammarBucket = gBucket;
/* 4226 */       this.fInitialGrammarSet = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void lockPool() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unlockPool() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void clear() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class XSDKey
/*      */   {
/*      */     String systemId;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     short referType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String referNS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     XSDKey(String systemId, short referType, String referNS) {
/* 4281 */       this.systemId = systemId;
/* 4282 */       this.referType = referType;
/* 4283 */       this.referNS = referNS;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 4289 */       return (this.referNS == null) ? 0 : this.referNS.hashCode();
/*      */     }
/*      */     
/*      */     public boolean equals(Object obj) {
/* 4293 */       if (!(obj instanceof XSDKey)) {
/* 4294 */         return false;
/*      */       }
/* 4296 */       XSDKey key = (XSDKey)obj;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4306 */       if (this.referNS != key.referNS) {
/* 4307 */         return false;
/*      */       }
/*      */       
/* 4310 */       if (this.systemId == null || !this.systemId.equals(key.systemId)) {
/* 4311 */         return false;
/*      */       }
/*      */       
/* 4314 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class SAX2XNIUtil extends ErrorHandlerWrapper {
/*      */     public static XMLParseException createXMLParseException0(SAXParseException exception) {
/* 4320 */       return createXMLParseException(exception);
/*      */     }
/*      */     public static XNIException createXNIException0(SAXException exception) {
/* 4323 */       return createXNIException(exception);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGenerateSyntheticAnnotations(boolean state) {
/* 4331 */     this.fSchemaParser.setFeature("http://apache.org/xml/features/generate-synthetic-annotations", state);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/traversers/XSDHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */