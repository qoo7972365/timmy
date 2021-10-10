/*      */ package com.sun.org.apache.xerces.internal.dom;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.URI;
/*      */ import com.sun.org.apache.xerces.internal.util.XML11Char;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.CDATASection;
/*      */ import org.w3c.dom.Comment;
/*      */ import org.w3c.dom.DOMConfiguration;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.DOMImplementation;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentFragment;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Entity;
/*      */ import org.w3c.dom.EntityReference;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.Notation;
/*      */ import org.w3c.dom.ProcessingInstruction;
/*      */ import org.w3c.dom.Text;
/*      */ import org.w3c.dom.UserDataHandler;
/*      */ import org.w3c.dom.events.Event;
/*      */ import org.w3c.dom.events.EventListener;
/*      */ import org.w3c.dom.ls.DOMImplementationLS;
/*      */ import org.w3c.dom.ls.LSSerializer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CoreDocumentImpl
/*      */   extends ParentNode
/*      */   implements Document
/*      */ {
/*  142 */   transient DOMNormalizer domNormalizer = null;
/*  143 */   transient DOMConfigurationImpl fConfiguration = null;
/*      */ 
/*      */   
/*  146 */   transient Object fXPathEvaluator = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   protected int changes = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean errorChecking = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean ancestorChecking = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean xmlVersionChanged = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  205 */   private int documentNumber = 0;
/*      */ 
/*      */ 
/*      */   
/*  209 */   private int nodeCounter = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean xml11Version = false;
/*      */ 
/*      */ 
/*      */   
/*  218 */   private static final int[] kidOK = new int[13];
/*      */   static {
/*  220 */     kidOK[9] = 1410;
/*      */ 
/*      */ 
/*      */     
/*  224 */     kidOK[1] = 442; kidOK[5] = 442; kidOK[6] = 442; kidOK[11] = 442;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     kidOK[2] = 40;
/*      */ 
/*      */     
/*  236 */     kidOK[12] = 0; kidOK[4] = 0; kidOK[3] = 0; kidOK[8] = 0; kidOK[7] = 0; kidOK[10] = 0;
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
/*  269 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("docType", DocumentTypeImpl.class), new ObjectStreamField("docElement", ElementImpl.class), new ObjectStreamField("fFreeNLCache", NodeListCache.class), new ObjectStreamField("encoding", String.class), new ObjectStreamField("actualEncoding", String.class), new ObjectStreamField("version", String.class), new ObjectStreamField("standalone", boolean.class), new ObjectStreamField("fDocumentURI", String.class), new ObjectStreamField("userData", Hashtable.class), new ObjectStreamField("identifiers", Hashtable.class), new ObjectStreamField("changes", int.class), new ObjectStreamField("allowGrammarAccess", boolean.class), new ObjectStreamField("errorChecking", boolean.class), new ObjectStreamField("ancestorChecking", boolean.class), new ObjectStreamField("xmlVersionChanged", boolean.class), new ObjectStreamField("documentNumber", int.class), new ObjectStreamField("nodeCounter", int.class), new ObjectStreamField("nodeTable", Hashtable.class), new ObjectStreamField("xml11Version", boolean.class) };
/*      */ 
/*      */   
/*      */   static final long serialVersionUID = 0L;
/*      */ 
/*      */   
/*      */   protected DocumentTypeImpl docType;
/*      */   
/*      */   protected ElementImpl docElement;
/*      */   
/*      */   transient NodeListCache fFreeNLCache;
/*      */   
/*      */   protected String encoding;
/*      */   
/*      */   protected String actualEncoding;
/*      */   
/*      */   protected String version;
/*      */   
/*      */   protected boolean standalone;
/*      */   
/*      */   protected String fDocumentURI;
/*      */   
/*      */   private Map<Node, Map<String, ParentNode.UserDataRecord>> nodeUserData;
/*      */   
/*      */   protected Map<String, Node> identifiers;
/*      */   
/*      */   protected boolean allowGrammarAccess;
/*      */   
/*      */   private Map<Node, Integer> nodeTable;
/*      */ 
/*      */   
/*      */   public CoreDocumentImpl() {
/*  301 */     this(false);
/*      */   }
/*      */ 
/*      */   
/*      */   public CoreDocumentImpl(boolean grammarAccess) {
/*  306 */     super((CoreDocumentImpl)null);
/*  307 */     this.ownerDocument = this;
/*  308 */     this.allowGrammarAccess = grammarAccess;
/*  309 */     String systemProp = SecuritySupport.getSystemProperty("http://java.sun.com/xml/dom/properties/ancestor-check");
/*  310 */     if (systemProp != null && 
/*  311 */       systemProp.equalsIgnoreCase("false")) {
/*  312 */       this.ancestorChecking = false;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CoreDocumentImpl(DocumentType doctype) {
/*  322 */     this(doctype, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public CoreDocumentImpl(DocumentType doctype, boolean grammarAccess) {
/*  327 */     this(grammarAccess);
/*  328 */     if (doctype != null) {
/*      */       DocumentTypeImpl doctypeImpl;
/*      */       try {
/*  331 */         doctypeImpl = (DocumentTypeImpl)doctype;
/*  332 */       } catch (ClassCastException e) {
/*  333 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/*  334 */         throw new DOMException((short)4, msg);
/*      */       } 
/*  336 */       doctypeImpl.ownerDocument = this;
/*  337 */       appendChild(doctype);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Document getOwnerDocument() {
/*  348 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public short getNodeType() {
/*  353 */     return 9;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNodeName() {
/*  358 */     return "#document";
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
/*      */   public Node cloneNode(boolean deep) {
/*  372 */     CoreDocumentImpl newdoc = new CoreDocumentImpl();
/*  373 */     callUserDataHandlers(this, newdoc, (short)1);
/*  374 */     cloneNode(newdoc, deep);
/*      */     
/*  376 */     return newdoc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void cloneNode(CoreDocumentImpl newdoc, boolean deep) {
/*  387 */     if (needsSyncChildren()) {
/*  388 */       synchronizeChildren();
/*      */     }
/*      */     
/*  391 */     if (deep) {
/*  392 */       Map<Node, String> reversedIdentifiers = null;
/*      */       
/*  394 */       if (this.identifiers != null) {
/*      */         
/*  396 */         reversedIdentifiers = new HashMap<>(this.identifiers.size());
/*  397 */         for (String elementId : this.identifiers.keySet()) {
/*  398 */           reversedIdentifiers.put(this.identifiers.get(elementId), elementId);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  403 */       for (ChildNode kid = this.firstChild; kid != null; 
/*  404 */         kid = kid.nextSibling) {
/*  405 */         newdoc.appendChild(newdoc.importNode(kid, true, true, reversedIdentifiers));
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  411 */     newdoc.allowGrammarAccess = this.allowGrammarAccess;
/*  412 */     newdoc.errorChecking = this.errorChecking;
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
/*      */   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
/*  434 */     int type = newChild.getNodeType();
/*  435 */     if (this.errorChecking && ((
/*  436 */       type == 1 && this.docElement != null) || (type == 10 && this.docType != null))) {
/*      */       
/*  438 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null);
/*  439 */       throw new DOMException((short)3, msg);
/*      */     } 
/*      */ 
/*      */     
/*  443 */     if (newChild.getOwnerDocument() == null && newChild instanceof DocumentTypeImpl)
/*      */     {
/*  445 */       ((DocumentTypeImpl)newChild).ownerDocument = this;
/*      */     }
/*  447 */     super.insertBefore(newChild, refChild);
/*      */ 
/*      */     
/*  450 */     if (type == 1) {
/*  451 */       this.docElement = (ElementImpl)newChild;
/*      */     }
/*  453 */     else if (type == 10) {
/*  454 */       this.docType = (DocumentTypeImpl)newChild;
/*      */     } 
/*      */     
/*  457 */     return newChild;
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
/*      */   public Node removeChild(Node oldChild) throws DOMException {
/*  470 */     super.removeChild(oldChild);
/*      */ 
/*      */     
/*  473 */     int type = oldChild.getNodeType();
/*  474 */     if (type == 1) {
/*  475 */       this.docElement = null;
/*      */     }
/*  477 */     else if (type == 10) {
/*  478 */       this.docType = null;
/*      */     } 
/*      */     
/*  481 */     return oldChild;
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
/*      */   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
/*  496 */     if (newChild.getOwnerDocument() == null && newChild instanceof DocumentTypeImpl)
/*      */     {
/*  498 */       ((DocumentTypeImpl)newChild).ownerDocument = this;
/*      */     }
/*      */     
/*  501 */     if (this.errorChecking && ((this.docType != null && oldChild
/*  502 */       .getNodeType() != 10 && newChild
/*  503 */       .getNodeType() == 10) || (this.docElement != null && oldChild
/*      */       
/*  505 */       .getNodeType() != 1 && newChild
/*  506 */       .getNodeType() == 1)))
/*      */     {
/*  508 */       throw new DOMException((short)3, 
/*      */           
/*  510 */           DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "HIERARCHY_REQUEST_ERR", null));
/*      */     }
/*  512 */     super.replaceChild(newChild, oldChild);
/*      */     
/*  514 */     int type = oldChild.getNodeType();
/*  515 */     if (type == 1) {
/*  516 */       this.docElement = (ElementImpl)newChild;
/*      */     }
/*  518 */     else if (type == 10) {
/*  519 */       this.docType = (DocumentTypeImpl)newChild;
/*      */     } 
/*  521 */     return oldChild;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextContent() throws DOMException {
/*  529 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextContent(String textContent) throws DOMException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getFeature(String feature, String version) {
/*  546 */     boolean anyVersion = (version == null || version.length() == 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  553 */     if (feature.equalsIgnoreCase("+XPath") && (anyVersion || version
/*  554 */       .equals("3.0"))) {
/*      */ 
/*      */ 
/*      */       
/*  558 */       if (this.fXPathEvaluator != null) {
/*  559 */         return this.fXPathEvaluator;
/*      */       }
/*      */       
/*      */       try {
/*  563 */         Class<?> xpathClass = ObjectFactory.findProviderClass("com.sun.org.apache.xpath.internal.domapi.XPathEvaluatorImpl", true);
/*      */ 
/*      */         
/*  566 */         Constructor<?> xpathClassConstr = xpathClass.getConstructor(new Class[] { Document.class });
/*      */ 
/*      */ 
/*      */         
/*  570 */         Class[] interfaces = xpathClass.getInterfaces();
/*  571 */         for (int i = 0; i < interfaces.length; i++) {
/*  572 */           if (interfaces[i].getName().equals("org.w3c.dom.xpath.XPathEvaluator")) {
/*      */             
/*  574 */             this.fXPathEvaluator = xpathClassConstr.newInstance(new Object[] { this });
/*  575 */             return this.fXPathEvaluator;
/*      */           } 
/*      */         } 
/*  578 */         return null;
/*  579 */       } catch (Exception e) {
/*  580 */         return null;
/*      */       } 
/*      */     } 
/*  583 */     return super.getFeature(feature, version);
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
/*      */   public Attr createAttribute(String name) throws DOMException {
/*  605 */     if (this.errorChecking && !isXMLName(name, this.xml11Version)) {
/*      */       
/*  607 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*      */ 
/*      */ 
/*      */       
/*  611 */       throw new DOMException((short)5, msg);
/*      */     } 
/*  613 */     return new AttrImpl(this, name);
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
/*      */   public CDATASection createCDATASection(String data) throws DOMException {
/*  628 */     return new CDATASectionImpl(this, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comment createComment(String data) {
/*  637 */     return new CommentImpl(this, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DocumentFragment createDocumentFragment() {
/*  645 */     return new DocumentFragmentImpl(this);
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
/*      */   public Element createElement(String tagName) throws DOMException {
/*  663 */     if (this.errorChecking && !isXMLName(tagName, this.xml11Version)) {
/*  664 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*  665 */       throw new DOMException((short)5, msg);
/*      */     } 
/*  667 */     return new ElementImpl(this, tagName);
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
/*      */   public EntityReference createEntityReference(String name) throws DOMException {
/*  684 */     if (this.errorChecking && !isXMLName(name, this.xml11Version)) {
/*  685 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*  686 */       throw new DOMException((short)5, msg);
/*      */     } 
/*  688 */     return new EntityReferenceImpl(this, name);
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
/*      */   public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
/*  709 */     if (this.errorChecking && !isXMLName(target, this.xml11Version)) {
/*  710 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*  711 */       throw new DOMException((short)5, msg);
/*      */     } 
/*  713 */     return new ProcessingInstructionImpl(this, target, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Text createTextNode(String data) {
/*  724 */     return new TextImpl(this, data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DocumentType getDoctype() {
/*  735 */     if (needsSyncChildren()) {
/*  736 */       synchronizeChildren();
/*      */     }
/*  738 */     return this.docType;
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
/*      */   public Element getDocumentElement() {
/*  751 */     if (needsSyncChildren()) {
/*  752 */       synchronizeChildren();
/*      */     }
/*  754 */     return this.docElement;
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
/*      */   public NodeList getElementsByTagName(String tagname) {
/*  767 */     return new DeepNodeListImpl(this, tagname);
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
/*      */   public DOMImplementation getImplementation() {
/*  779 */     return CoreDOMImplementationImpl.getDOMImplementation();
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
/*      */   public void setErrorChecking(boolean check) {
/*  808 */     this.errorChecking = check;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrictErrorChecking(boolean check) {
/*  815 */     this.errorChecking = check;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getErrorChecking() {
/*  822 */     return this.errorChecking;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStrictErrorChecking() {
/*  829 */     return this.errorChecking;
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
/*      */   public String getInputEncoding() {
/*  842 */     return this.actualEncoding;
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
/*      */   public void setInputEncoding(String value) {
/*  855 */     this.actualEncoding = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXmlEncoding(String value) {
/*  866 */     this.encoding = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String value) {
/*  875 */     setXmlEncoding(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getXmlEncoding() {
/*  883 */     return this.encoding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/*  892 */     return getXmlEncoding();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setXmlVersion(String value) {
/*  901 */     if (value.equals("1.0") || value.equals("1.1")) {
/*      */ 
/*      */       
/*  904 */       if (!getXmlVersion().equals(value)) {
/*  905 */         this.xmlVersionChanged = true;
/*      */         
/*  907 */         isNormalized(false);
/*  908 */         this.version = value;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  915 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/*  916 */       throw new DOMException((short)9, msg);
/*      */     } 
/*      */     
/*  919 */     if (getXmlVersion().equals("1.1")) {
/*  920 */       this.xml11Version = true;
/*      */     } else {
/*      */       
/*  923 */       this.xml11Version = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVersion(String value) {
/*  933 */     setXmlVersion(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getXmlVersion() {
/*  942 */     return (this.version == null) ? "1.0" : this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  951 */     return getXmlVersion();
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
/*      */   public void setXmlStandalone(boolean value) throws DOMException {
/*  966 */     this.standalone = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStandalone(boolean value) {
/*  975 */     setXmlStandalone(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getXmlStandalone() {
/*  984 */     return this.standalone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStandalone() {
/*  993 */     return getXmlStandalone();
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
/*      */   public String getDocumentURI() {
/* 1005 */     return this.fDocumentURI;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Node renameNode(Node n, String namespaceURI, String name) throws DOMException {
/*      */     ElementImpl el;
/*      */     AttrImpl at;
/*      */     Element element;
/* 1016 */     if (this.errorChecking && n.getOwnerDocument() != this && n != this) {
/* 1017 */       String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/*      */       
/* 1019 */       throw new DOMException((short)4, str);
/*      */     } 
/* 1021 */     switch (n.getNodeType()) {
/*      */       case 1:
/* 1023 */         el = (ElementImpl)n;
/* 1024 */         if (el instanceof ElementNSImpl) {
/* 1025 */           ((ElementNSImpl)el).rename(namespaceURI, name);
/*      */ 
/*      */           
/* 1028 */           callUserDataHandlers(el, (Node)null, (short)4);
/*      */         
/*      */         }
/* 1031 */         else if (namespaceURI == null) {
/* 1032 */           if (this.errorChecking) {
/* 1033 */             int colon1 = name.indexOf(':');
/* 1034 */             if (colon1 != -1) {
/*      */               
/* 1036 */               String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
/*      */ 
/*      */ 
/*      */               
/* 1040 */               throw new DOMException((short)14, str);
/*      */             } 
/* 1042 */             if (!isXMLName(name, this.xml11Version)) {
/* 1043 */               String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*      */ 
/*      */               
/* 1046 */               throw new DOMException((short)5, str);
/*      */             } 
/*      */           } 
/*      */           
/* 1050 */           el.rename(name);
/*      */ 
/*      */           
/* 1053 */           callUserDataHandlers(el, (Node)null, (short)4);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1058 */           ElementNSImpl nel = new ElementNSImpl(this, namespaceURI, name);
/*      */ 
/*      */ 
/*      */           
/* 1062 */           copyEventListeners(el, nel);
/*      */ 
/*      */           
/* 1065 */           Map<String, ParentNode.UserDataRecord> data = removeUserDataTable(el);
/*      */ 
/*      */           
/* 1068 */           Node parent = el.getParentNode();
/* 1069 */           Node nextSib = el.getNextSibling();
/* 1070 */           if (parent != null) {
/* 1071 */             parent.removeChild(el);
/*      */           }
/*      */           
/* 1074 */           Node child = el.getFirstChild();
/* 1075 */           while (child != null) {
/* 1076 */             el.removeChild(child);
/* 1077 */             nel.appendChild(child);
/* 1078 */             child = el.getFirstChild();
/*      */           } 
/*      */           
/* 1081 */           nel.moveSpecifiedAttributes(el);
/*      */ 
/*      */           
/* 1084 */           setUserDataTable(nel, data);
/*      */ 
/*      */           
/* 1087 */           callUserDataHandlers(el, nel, (short)4);
/*      */ 
/*      */ 
/*      */           
/* 1091 */           if (parent != null) {
/* 1092 */             parent.insertBefore(nel, nextSib);
/*      */           }
/* 1094 */           el = nel;
/*      */         } 
/*      */ 
/*      */         
/* 1098 */         renamedElement((Element)n, el);
/* 1099 */         return el;
/*      */       
/*      */       case 2:
/* 1102 */         at = (AttrImpl)n;
/*      */ 
/*      */         
/* 1105 */         element = at.getOwnerElement();
/* 1106 */         if (element != null) {
/* 1107 */           element.removeAttributeNode(at);
/*      */         }
/* 1109 */         if (n instanceof AttrNSImpl) {
/* 1110 */           ((AttrNSImpl)at).rename(namespaceURI, name);
/*      */           
/* 1112 */           if (element != null) {
/* 1113 */             element.setAttributeNodeNS(at);
/*      */           }
/*      */ 
/*      */           
/* 1117 */           callUserDataHandlers(at, (Node)null, (short)4);
/*      */         
/*      */         }
/* 1120 */         else if (namespaceURI == null) {
/* 1121 */           at.rename(name);
/*      */           
/* 1123 */           if (element != null) {
/* 1124 */             element.setAttributeNode(at);
/*      */           }
/*      */ 
/*      */           
/* 1128 */           callUserDataHandlers(at, (Node)null, (short)4);
/*      */         }
/*      */         else {
/*      */           
/* 1132 */           AttrNSImpl nat = new AttrNSImpl(this, namespaceURI, name);
/*      */ 
/*      */           
/* 1135 */           copyEventListeners(at, nat);
/*      */ 
/*      */           
/* 1138 */           Map<String, ParentNode.UserDataRecord> data = removeUserDataTable(at);
/*      */ 
/*      */           
/* 1141 */           Node child = at.getFirstChild();
/* 1142 */           while (child != null) {
/* 1143 */             at.removeChild(child);
/* 1144 */             nat.appendChild(child);
/* 1145 */             child = at.getFirstChild();
/*      */           } 
/*      */ 
/*      */           
/* 1149 */           setUserDataTable(nat, data);
/*      */ 
/*      */           
/* 1152 */           callUserDataHandlers(at, nat, (short)4);
/*      */ 
/*      */           
/* 1155 */           if (element != null) {
/* 1156 */             element.setAttributeNode(nat);
/*      */           }
/* 1158 */           at = nat;
/*      */         } 
/*      */ 
/*      */         
/* 1162 */         renamedAttrNode((Attr)n, at);
/*      */         
/* 1164 */         return at;
/*      */     } 
/*      */     
/* 1167 */     String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/* 1168 */     throw new DOMException((short)9, msg);
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
/*      */   public void normalizeDocument() {
/* 1181 */     if (isNormalized() && !isNormalizeDocRequired()) {
/*      */       return;
/*      */     }
/* 1184 */     if (needsSyncChildren()) {
/* 1185 */       synchronizeChildren();
/*      */     }
/*      */     
/* 1188 */     if (this.domNormalizer == null) {
/* 1189 */       this.domNormalizer = new DOMNormalizer();
/*      */     }
/*      */     
/* 1192 */     if (this.fConfiguration == null) {
/* 1193 */       this.fConfiguration = new DOMConfigurationImpl();
/*      */     } else {
/*      */       
/* 1196 */       this.fConfiguration.reset();
/*      */     } 
/*      */     
/* 1199 */     this.domNormalizer.normalizeDocument(this, this.fConfiguration);
/* 1200 */     isNormalized(true);
/*      */ 
/*      */     
/* 1203 */     this.xmlVersionChanged = false;
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
/*      */   public DOMConfiguration getDomConfig() {
/* 1215 */     if (this.fConfiguration == null) {
/* 1216 */       this.fConfiguration = new DOMConfigurationImpl();
/*      */     }
/* 1218 */     return this.fConfiguration;
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
/*      */   public String getBaseURI() {
/* 1231 */     if (this.fDocumentURI != null && this.fDocumentURI.length() != 0) {
/*      */       try {
/* 1233 */         return (new URI(this.fDocumentURI)).toString();
/*      */       }
/* 1235 */       catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
/*      */         
/* 1237 */         return null;
/*      */       } 
/*      */     }
/* 1240 */     return this.fDocumentURI;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentURI(String documentURI) {
/* 1247 */     this.fDocumentURI = documentURI;
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
/*      */   public boolean getAsync() {
/* 1269 */     return false;
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
/*      */   public void setAsync(boolean async) {
/* 1287 */     if (async) {
/* 1288 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/* 1289 */       throw new DOMException((short)9, msg);
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
/*      */   public void abort() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean load(String uri) {
/* 1339 */     return false;
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
/*      */   public boolean loadXML(String source) {
/* 1351 */     return false;
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
/*      */   public String saveXML(Node node) throws DOMException {
/* 1374 */     if (this.errorChecking && node != null && this != node
/* 1375 */       .getOwnerDocument()) {
/* 1376 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
/* 1377 */       throw new DOMException((short)4, msg);
/*      */     } 
/* 1379 */     DOMImplementationLS domImplLS = (DOMImplementationLS)DOMImplementationImpl.getDOMImplementation();
/* 1380 */     LSSerializer xmlWriter = domImplLS.createLSSerializer();
/* 1381 */     if (node == null) {
/* 1382 */       node = this;
/*      */     }
/* 1384 */     return xmlWriter.writeToString(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setMutationEvents(boolean set) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean getMutationEvents() {
/* 1400 */     return false;
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
/*      */   public DocumentType createDocumentType(String qualifiedName, String publicID, String systemID) throws DOMException {
/* 1419 */     return new DocumentTypeImpl(this, qualifiedName, publicID, systemID);
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
/*      */   public Entity createEntity(String name) throws DOMException {
/* 1436 */     if (this.errorChecking && !isXMLName(name, this.xml11Version)) {
/* 1437 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/* 1438 */       throw new DOMException((short)5, msg);
/*      */     } 
/* 1440 */     return new EntityImpl(this, name);
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
/*      */   public Notation createNotation(String name) throws DOMException {
/* 1457 */     if (this.errorChecking && !isXMLName(name, this.xml11Version)) {
/* 1458 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/* 1459 */       throw new DOMException((short)5, msg);
/*      */     } 
/* 1461 */     return new NotationImpl(this, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElementDefinitionImpl createElementDefinition(String name) throws DOMException {
/* 1472 */     if (this.errorChecking && !isXMLName(name, this.xml11Version)) {
/* 1473 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/* 1474 */       throw new DOMException((short)5, msg);
/*      */     } 
/* 1476 */     return new ElementDefinitionImpl(this, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getNodeNumber() {
/* 1486 */     if (this.documentNumber == 0) {
/*      */       
/* 1488 */       CoreDOMImplementationImpl cd = (CoreDOMImplementationImpl)CoreDOMImplementationImpl.getDOMImplementation();
/* 1489 */       this.documentNumber = cd.assignDocumentNumber();
/*      */     } 
/* 1491 */     return this.documentNumber;
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
/*      */   protected int getNodeNumber(Node node) {
/*      */     int num;
/* 1506 */     if (this.nodeTable == null) {
/* 1507 */       this.nodeTable = new HashMap<>();
/* 1508 */       num = --this.nodeCounter;
/* 1509 */       this.nodeTable.put(node, new Integer(num));
/*      */     } else {
/* 1511 */       Integer n = this.nodeTable.get(node);
/* 1512 */       if (n == null) {
/* 1513 */         num = --this.nodeCounter;
/* 1514 */         this.nodeTable.put(node, Integer.valueOf(num));
/*      */       } else {
/* 1516 */         num = n.intValue();
/*      */       } 
/*      */     } 
/* 1519 */     return num;
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
/*      */   public Node importNode(Node source, boolean deep) throws DOMException {
/* 1533 */     return importNode(source, deep, false, (Map<Node, String>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node importNode(Node source, boolean deep, boolean cloningDoc, Map<Node, String> reversedIdentifiers) throws DOMException {
/*      */     Element newElement;
/*      */     Entity srcentity;
/*      */     DocumentType srcdoctype;
/*      */     Notation srcnotation;
/*      */     String msg;
/*      */     boolean domLevel20;
/*      */     EntityImpl newentity;
/*      */     DocumentTypeImpl newdoctype;
/*      */     NotationImpl newnotation;
/*      */     NamedNodeMap sourceAttrs, smap, tmap;
/* 1551 */     Node newnode = null;
/* 1552 */     Map<String, ParentNode.UserDataRecord> userData = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1565 */     if (source instanceof NodeImpl) {
/* 1566 */       userData = ((NodeImpl)source).getUserDataRecord();
/*      */     }
/* 1568 */     int type = source.getNodeType();
/* 1569 */     switch (type) {
/*      */       
/*      */       case 1:
/* 1572 */         domLevel20 = source.getOwnerDocument().getImplementation().hasFeature("XML", "2.0");
/*      */         
/* 1574 */         if (!domLevel20 || source.getLocalName() == null) {
/* 1575 */           newElement = createElement(source.getNodeName());
/*      */         } else {
/* 1577 */           newElement = createElementNS(source.getNamespaceURI(), source
/* 1578 */               .getNodeName());
/*      */         } 
/*      */         
/* 1581 */         sourceAttrs = source.getAttributes();
/* 1582 */         if (sourceAttrs != null) {
/* 1583 */           int length = sourceAttrs.getLength();
/* 1584 */           for (int index = 0; index < length; index++) {
/* 1585 */             Attr attr = (Attr)sourceAttrs.item(index);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1591 */             if (attr.getSpecified() || cloningDoc) {
/* 1592 */               Attr newAttr = (Attr)importNode(attr, true, cloningDoc, reversedIdentifiers);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1597 */               if (!domLevel20 || attr
/* 1598 */                 .getLocalName() == null) {
/* 1599 */                 newElement.setAttributeNode(newAttr);
/*      */               } else {
/* 1601 */                 newElement.setAttributeNodeNS(newAttr);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1607 */         if (reversedIdentifiers != null) {
/*      */           
/* 1609 */           String elementId = reversedIdentifiers.get(source);
/* 1610 */           if (elementId != null) {
/* 1611 */             if (this.identifiers == null) {
/* 1612 */               this.identifiers = new HashMap<>();
/*      */             }
/*      */             
/* 1615 */             this.identifiers.put(elementId, newElement);
/*      */           } 
/*      */         } 
/*      */         
/* 1619 */         newnode = newElement;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1625 */         if (source.getOwnerDocument().getImplementation().hasFeature("XML", "2.0")) {
/* 1626 */           if (source.getLocalName() == null) {
/* 1627 */             newnode = createAttribute(source.getNodeName());
/*      */           } else {
/* 1629 */             newnode = createAttributeNS(source.getNamespaceURI(), source
/* 1630 */                 .getNodeName());
/*      */           } 
/*      */         } else {
/*      */           
/* 1634 */           newnode = createAttribute(source.getNodeName());
/*      */         } 
/*      */ 
/*      */         
/* 1638 */         if (source instanceof AttrImpl) {
/* 1639 */           AttrImpl attr = (AttrImpl)source;
/* 1640 */           if (attr.hasStringValue()) {
/* 1641 */             AttrImpl newattr = (AttrImpl)newnode;
/* 1642 */             newattr.setValue(attr.getValue());
/* 1643 */             deep = false;
/*      */             break;
/*      */           } 
/* 1646 */           deep = true;
/*      */ 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1655 */         if (source.getFirstChild() == null) {
/* 1656 */           newnode.setNodeValue(source.getNodeValue());
/* 1657 */           deep = false; break;
/*      */         } 
/* 1659 */         deep = true;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1666 */         newnode = createTextNode(source.getNodeValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/* 1671 */         newnode = createCDATASection(source.getNodeValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 5:
/* 1676 */         newnode = createEntityReference(source.getNodeName());
/*      */ 
/*      */         
/* 1679 */         deep = false;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 6:
/* 1684 */         srcentity = (Entity)source;
/*      */         
/* 1686 */         newentity = (EntityImpl)createEntity(source.getNodeName());
/* 1687 */         newentity.setPublicId(srcentity.getPublicId());
/* 1688 */         newentity.setSystemId(srcentity.getSystemId());
/* 1689 */         newentity.setNotationName(srcentity.getNotationName());
/*      */ 
/*      */         
/* 1692 */         newentity.isReadOnly(false);
/* 1693 */         newnode = newentity;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 7:
/* 1698 */         newnode = createProcessingInstruction(source.getNodeName(), source
/* 1699 */             .getNodeValue());
/*      */         break;
/*      */ 
/*      */       
/*      */       case 8:
/* 1704 */         newnode = createComment(source.getNodeValue());
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/* 1711 */         if (!cloningDoc) {
/* 1712 */           String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/* 1713 */           throw new DOMException((short)9, str);
/*      */         } 
/* 1715 */         srcdoctype = (DocumentType)source;
/*      */         
/* 1717 */         newdoctype = (DocumentTypeImpl)createDocumentType(srcdoctype.getNodeName(), srcdoctype
/* 1718 */             .getPublicId(), srcdoctype
/* 1719 */             .getSystemId());
/*      */         
/* 1721 */         smap = srcdoctype.getEntities();
/* 1722 */         tmap = newdoctype.getEntities();
/* 1723 */         if (smap != null) {
/* 1724 */           for (int i = 0; i < smap.getLength(); i++) {
/* 1725 */             tmap.setNamedItem(importNode(smap.item(i), true, true, reversedIdentifiers));
/*      */           }
/*      */         }
/*      */         
/* 1729 */         smap = srcdoctype.getNotations();
/* 1730 */         tmap = newdoctype.getNotations();
/* 1731 */         if (smap != null) {
/* 1732 */           for (int i = 0; i < smap.getLength(); i++) {
/* 1733 */             tmap.setNamedItem(importNode(smap.item(i), true, true, reversedIdentifiers));
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1743 */         newnode = newdoctype;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 11:
/* 1748 */         newnode = createDocumentFragment();
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 12:
/* 1754 */         srcnotation = (Notation)source;
/*      */         
/* 1756 */         newnotation = (NotationImpl)createNotation(source.getNodeName());
/* 1757 */         newnotation.setPublicId(srcnotation.getPublicId());
/* 1758 */         newnotation.setSystemId(srcnotation.getSystemId());
/*      */         
/* 1760 */         newnode = newnotation;
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 1766 */         msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/* 1767 */         throw new DOMException((short)9, msg);
/*      */     } 
/*      */ 
/*      */     
/* 1771 */     if (userData != null) {
/* 1772 */       callUserDataHandlers(source, newnode, (short)2, userData);
/*      */     }
/*      */     
/* 1775 */     if (deep) {
/* 1776 */       Node srckid = source.getFirstChild();
/* 1777 */       for (; srckid != null; 
/* 1778 */         srckid = srckid.getNextSibling()) {
/* 1779 */         newnode.appendChild(importNode(srckid, true, cloningDoc, reversedIdentifiers));
/*      */       }
/*      */     } 
/*      */     
/* 1783 */     if (newnode.getNodeType() == 6) {
/* 1784 */       ((NodeImpl)newnode).setReadOnly(true, true);
/*      */     }
/* 1786 */     return newnode;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Node adoptNode(Node source) {
/*      */     NodeImpl node;
/*      */     Map<String, ParentNode.UserDataRecord> userData;
/*      */     AttrImpl attr;
/*      */     String msg;
/*      */     Node parent;
/*      */     Node child;
/*      */     NamedNodeMap entities;
/*      */     Node entityNode;
/*      */     try {
/* 1801 */       node = (NodeImpl)source;
/* 1802 */     } catch (ClassCastException e) {
/*      */       
/* 1804 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1809 */     if (source == null)
/* 1810 */       return null; 
/* 1811 */     if (source.getOwnerDocument() != null) {
/*      */       
/* 1813 */       DOMImplementation thisImpl = getImplementation();
/* 1814 */       DOMImplementation otherImpl = source.getOwnerDocument().getImplementation();
/*      */ 
/*      */       
/* 1817 */       if (thisImpl != otherImpl)
/*      */       {
/*      */         
/* 1820 */         if (thisImpl instanceof DOMImplementationImpl && otherImpl instanceof DeferredDOMImplementationImpl) {
/*      */ 
/*      */           
/* 1823 */           undeferChildren(node);
/* 1824 */         } else if (!(thisImpl instanceof DeferredDOMImplementationImpl) || !(otherImpl instanceof DOMImplementationImpl)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1829 */           return null;
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1834 */     switch (node.getNodeType()) {
/*      */       case 2:
/* 1836 */         attr = (AttrImpl)node;
/*      */         
/* 1838 */         if (attr.getOwnerElement() != null)
/*      */         {
/* 1840 */           attr.getOwnerElement().removeAttributeNode(attr);
/*      */         }
/*      */         
/* 1843 */         attr.isSpecified(true);
/* 1844 */         userData = node.getUserDataRecord();
/*      */ 
/*      */         
/* 1847 */         attr.setOwnerDocument(this);
/* 1848 */         if (userData != null) {
/* 1849 */           setUserDataTable(node, userData);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/*      */       case 12:
/* 1857 */         msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", null);
/* 1858 */         throw new DOMException((short)7, msg);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 9:
/*      */       case 10:
/* 1865 */         msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", null);
/* 1866 */         throw new DOMException((short)9, msg);
/*      */       
/*      */       case 5:
/* 1869 */         userData = node.getUserDataRecord();
/*      */         
/* 1871 */         parent = node.getParentNode();
/* 1872 */         if (parent != null) {
/* 1873 */           parent.removeChild(source);
/*      */         }
/*      */ 
/*      */         
/* 1877 */         while ((child = node.getFirstChild()) != null) {
/* 1878 */           node.removeChild(child);
/*      */         }
/*      */         
/* 1881 */         node.setOwnerDocument(this);
/* 1882 */         if (userData != null) {
/* 1883 */           setUserDataTable(node, userData);
/*      */         }
/*      */         
/* 1886 */         if (this.docType == null) {
/*      */           break;
/*      */         }
/* 1889 */         entities = this.docType.getEntities();
/* 1890 */         entityNode = entities.getNamedItem(node.getNodeName());
/* 1891 */         if (entityNode == null) {
/*      */           break;
/*      */         }
/* 1894 */         child = entityNode.getFirstChild();
/* 1895 */         for (; child != null; child = child.getNextSibling()) {
/* 1896 */           Node childClone = child.cloneNode(true);
/* 1897 */           node.appendChild(childClone);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 1:
/* 1902 */         userData = node.getUserDataRecord();
/*      */         
/* 1904 */         parent = node.getParentNode();
/* 1905 */         if (parent != null) {
/* 1906 */           parent.removeChild(source);
/*      */         }
/*      */         
/* 1909 */         node.setOwnerDocument(this);
/* 1910 */         if (userData != null) {
/* 1911 */           setUserDataTable(node, userData);
/*      */         }
/*      */         
/* 1914 */         ((ElementImpl)node).reconcileDefaultAttributes();
/*      */         break;
/*      */       
/*      */       default:
/* 1918 */         userData = node.getUserDataRecord();
/*      */         
/* 1920 */         parent = node.getParentNode();
/* 1921 */         if (parent != null) {
/* 1922 */           parent.removeChild(source);
/*      */         }
/*      */         
/* 1925 */         node.setOwnerDocument(this);
/* 1926 */         if (userData != null) {
/* 1927 */           setUserDataTable(node, userData);
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1934 */     if (userData != null) {
/* 1935 */       callUserDataHandlers(source, (Node)null, (short)5, userData);
/*      */     }
/*      */     
/* 1938 */     return node;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void undeferChildren(Node node) {
/* 1948 */     Node top = node;
/*      */     
/* 1950 */     while (null != node) {
/*      */       
/* 1952 */       if (((NodeImpl)node).needsSyncData()) {
/* 1953 */         ((NodeImpl)node).synchronizeData();
/*      */       }
/*      */       
/* 1956 */       NamedNodeMap attributes = node.getAttributes();
/* 1957 */       if (attributes != null) {
/* 1958 */         int length = attributes.getLength();
/* 1959 */         for (int i = 0; i < length; i++) {
/* 1960 */           undeferChildren(attributes.item(i));
/*      */         }
/*      */       } 
/*      */       
/* 1964 */       Node nextNode = null;
/* 1965 */       nextNode = node.getFirstChild();
/*      */       
/* 1967 */       while (null == nextNode) {
/*      */         
/* 1969 */         if (top.equals(node)) {
/*      */           break;
/*      */         }
/* 1972 */         nextNode = node.getNextSibling();
/*      */         
/* 1974 */         if (null == nextNode) {
/* 1975 */           node = node.getParentNode();
/*      */           
/* 1977 */           if (null == node || top.equals(node)) {
/* 1978 */             nextNode = null;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1984 */       node = nextNode;
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
/*      */   public Element getElementById(String elementId) {
/* 2002 */     return getIdentifier(elementId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void clearIdentifiers() {
/* 2009 */     if (this.identifiers != null) {
/* 2010 */       this.identifiers.clear();
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
/*      */   public void putIdentifier(String idName, Element element) {
/* 2025 */     if (element == null) {
/* 2026 */       removeIdentifier(idName);
/*      */       
/*      */       return;
/*      */     } 
/* 2030 */     if (needsSyncData()) {
/* 2031 */       synchronizeData();
/*      */     }
/*      */     
/* 2034 */     if (this.identifiers == null) {
/* 2035 */       this.identifiers = new HashMap<>();
/*      */     }
/*      */     
/* 2038 */     this.identifiers.put(idName, element);
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
/*      */   public Element getIdentifier(String idName) {
/* 2051 */     if (needsSyncData()) {
/* 2052 */       synchronizeData();
/*      */     }
/*      */     
/* 2055 */     if (this.identifiers == null) {
/* 2056 */       return null;
/*      */     }
/* 2058 */     Element elem = (Element)this.identifiers.get(idName);
/* 2059 */     if (elem != null) {
/*      */       
/* 2061 */       Node parent = elem.getParentNode();
/* 2062 */       while (parent != null) {
/* 2063 */         if (parent == this) {
/* 2064 */           return elem;
/*      */         }
/* 2066 */         parent = parent.getParentNode();
/*      */       } 
/*      */     } 
/* 2069 */     return null;
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
/*      */   public void removeIdentifier(String idName) {
/* 2081 */     if (needsSyncData()) {
/* 2082 */       synchronizeData();
/*      */     }
/*      */     
/* 2085 */     if (this.identifiers == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2089 */     this.identifiers.remove(idName);
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
/*      */   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
/* 2121 */     return new ElementNSImpl(this, namespaceURI, qualifiedName);
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
/*      */   public Element createElementNS(String namespaceURI, String qualifiedName, String localpart) throws DOMException {
/* 2141 */     return new ElementNSImpl(this, namespaceURI, qualifiedName, localpart);
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
/*      */   public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
/* 2164 */     return new AttrNSImpl(this, namespaceURI, qualifiedName);
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
/*      */   public Attr createAttributeNS(String namespaceURI, String qualifiedName, String localpart) throws DOMException {
/* 2185 */     return new AttrNSImpl(this, namespaceURI, qualifiedName, localpart);
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
/*      */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
/* 2206 */     return new DeepNodeListImpl(this, namespaceURI, localName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/* 2215 */     CoreDocumentImpl newdoc = (CoreDocumentImpl)super.clone();
/* 2216 */     newdoc.docType = null;
/* 2217 */     newdoc.docElement = null;
/* 2218 */     return newdoc;
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
/*      */   public static final boolean isXMLName(String s, boolean xml11Version) {
/* 2233 */     if (s == null) {
/* 2234 */       return false;
/*      */     }
/* 2236 */     if (!xml11Version) {
/* 2237 */       return XMLChar.isValidName(s);
/*      */     }
/* 2239 */     return XML11Char.isXML11ValidName(s);
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
/*      */   public static final boolean isValidQName(String prefix, String local, boolean xml11Version) {
/* 2253 */     if (local == null) return false; 
/* 2254 */     boolean validNCName = false;
/*      */     
/* 2256 */     if (!xml11Version) {
/*      */       
/* 2258 */       validNCName = ((prefix == null || XMLChar.isValidNCName(prefix)) && XMLChar.isValidNCName(local));
/*      */     }
/*      */     else {
/*      */       
/* 2262 */       validNCName = ((prefix == null || XML11Char.isXML11ValidNCName(prefix)) && XML11Char.isXML11ValidNCName(local));
/*      */     } 
/*      */     
/* 2265 */     return validNCName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isKidOK(Node parent, Node child) {
/* 2276 */     if (this.allowGrammarAccess && parent
/* 2277 */       .getNodeType() == 10) {
/* 2278 */       return (child.getNodeType() == 1);
/*      */     }
/* 2280 */     return (0 != (kidOK[parent.getNodeType()] & 1 << child.getNodeType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void changed() {
/* 2287 */     this.changes++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int changes() {
/* 2294 */     return this.changes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   NodeListCache getNodeListCache(ParentNode owner) {
/* 2303 */     if (this.fFreeNLCache == null) {
/* 2304 */       return new NodeListCache(owner);
/*      */     }
/* 2306 */     NodeListCache c = this.fFreeNLCache;
/* 2307 */     this.fFreeNLCache = this.fFreeNLCache.next;
/* 2308 */     c.fChild = null;
/* 2309 */     c.fChildIndex = -1;
/* 2310 */     c.fLength = -1;
/*      */     
/* 2312 */     if (c.fOwner != null) {
/* 2313 */       c.fOwner.fNodeListCache = null;
/*      */     }
/* 2315 */     c.fOwner = owner;
/*      */     
/* 2317 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void freeNodeListCache(NodeListCache c) {
/* 2325 */     c.next = this.fFreeNLCache;
/* 2326 */     this.fFreeNLCache = c;
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
/*      */   public Object setUserData(Node n, String key, Object data, UserDataHandler handler) {
/*      */     Map<String, ParentNode.UserDataRecord> t;
/* 2349 */     if (data == null) {
/* 2350 */       if (this.nodeUserData != null) {
/* 2351 */         t = this.nodeUserData.get(n);
/* 2352 */         if (t != null) {
/* 2353 */           ParentNode.UserDataRecord userDataRecord = t.remove(key);
/* 2354 */           if (userDataRecord != null) {
/* 2355 */             return userDataRecord.fData;
/*      */           }
/*      */         } 
/*      */       } 
/* 2359 */       return null;
/*      */     } 
/*      */     
/* 2362 */     if (this.nodeUserData == null) {
/* 2363 */       this.nodeUserData = new HashMap<>();
/* 2364 */       t = new HashMap<>();
/* 2365 */       this.nodeUserData.put(n, t);
/*      */     } else {
/* 2367 */       t = this.nodeUserData.get(n);
/* 2368 */       if (t == null) {
/* 2369 */         t = new HashMap<>();
/* 2370 */         this.nodeUserData.put(n, t);
/*      */       } 
/*      */     } 
/* 2373 */     ParentNode.UserDataRecord r = t.put(key, new ParentNode.UserDataRecord(this, data, handler));
/* 2374 */     if (r != null) {
/* 2375 */       return r.fData;
/*      */     }
/* 2377 */     return null;
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
/*      */   public Object getUserData(Node n, String key) {
/* 2393 */     if (this.nodeUserData == null) {
/* 2394 */       return null;
/*      */     }
/* 2396 */     Map<String, ParentNode.UserDataRecord> t = this.nodeUserData.get(n);
/* 2397 */     if (t == null) {
/* 2398 */       return null;
/*      */     }
/* 2400 */     ParentNode.UserDataRecord r = t.get(key);
/* 2401 */     if (r != null) {
/* 2402 */       return r.fData;
/*      */     }
/* 2404 */     return null;
/*      */   }
/*      */   
/*      */   protected Map<String, ParentNode.UserDataRecord> getUserDataRecord(Node n) {
/* 2408 */     if (this.nodeUserData == null) {
/* 2409 */       return null;
/*      */     }
/* 2411 */     Map<String, ParentNode.UserDataRecord> t = this.nodeUserData.get(n);
/* 2412 */     if (t == null) {
/* 2413 */       return null;
/*      */     }
/* 2415 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Map<String, ParentNode.UserDataRecord> removeUserDataTable(Node n) {
/* 2424 */     if (this.nodeUserData == null) {
/* 2425 */       return null;
/*      */     }
/* 2427 */     return this.nodeUserData.get(n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setUserDataTable(Node n, Map<String, ParentNode.UserDataRecord> data) {
/* 2436 */     if (this.nodeUserData == null) {
/* 2437 */       this.nodeUserData = new HashMap<>();
/*      */     }
/*      */     
/* 2440 */     if (data != null) {
/* 2441 */       this.nodeUserData.put(n, data);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void callUserDataHandlers(Node n, Node c, short operation) {
/* 2452 */     if (this.nodeUserData == null) {
/*      */       return;
/*      */     }
/*      */     
/* 2456 */     if (n instanceof NodeImpl) {
/* 2457 */       Map<String, ParentNode.UserDataRecord> t = ((NodeImpl)n).getUserDataRecord();
/* 2458 */       if (t == null || t.isEmpty()) {
/*      */         return;
/*      */       }
/* 2461 */       callUserDataHandlers(n, c, operation, t);
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
/*      */   void callUserDataHandlers(Node n, Node c, short operation, Map<String, ParentNode.UserDataRecord> userData) {
/* 2473 */     if (userData == null || userData.isEmpty()) {
/*      */       return;
/*      */     }
/* 2476 */     for (String key : userData.keySet()) {
/* 2477 */       ParentNode.UserDataRecord r = userData.get(key);
/* 2478 */       if (r.fHandler != null) {
/* 2479 */         r.fHandler.handle(operation, key, r.fData, n, c);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void checkNamespaceWF(String qname, int colon1, int colon2) {
/* 2523 */     if (!this.errorChecking) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2529 */     if (colon1 == 0 || colon1 == qname.length() - 1 || colon2 != colon1) {
/*      */       
/* 2531 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
/*      */ 
/*      */ 
/*      */       
/* 2535 */       throw new DOMException((short)14, msg);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void checkDOMNSErr(String prefix, String namespace) {
/* 2540 */     if (this.errorChecking) {
/* 2541 */       if (namespace == null) {
/*      */         
/* 2543 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
/*      */ 
/*      */ 
/*      */         
/* 2547 */         throw new DOMException((short)14, msg);
/*      */       } 
/* 2549 */       if (prefix.equals("xml") && 
/* 2550 */         !namespace.equals(NamespaceContext.XML_URI)) {
/*      */         
/* 2552 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
/*      */ 
/*      */ 
/*      */         
/* 2556 */         throw new DOMException((short)14, msg);
/*      */       } 
/* 2558 */       if ((prefix
/* 2559 */         .equals("xmlns") && 
/* 2560 */         !namespace.equals(NamespaceContext.XMLNS_URI)) || (
/* 2561 */         !prefix.equals("xmlns") && namespace
/* 2562 */         .equals(NamespaceContext.XMLNS_URI))) {
/*      */         
/* 2564 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", null);
/*      */ 
/*      */ 
/*      */         
/* 2568 */         throw new DOMException((short)14, msg);
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
/*      */   protected final void checkQName(String prefix, String local) {
/* 2581 */     if (!this.errorChecking) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2586 */     boolean validNCName = false;
/* 2587 */     if (!this.xml11Version) {
/*      */       
/* 2589 */       validNCName = ((prefix == null || XMLChar.isValidNCName(prefix)) && XMLChar.isValidNCName(local));
/*      */     }
/*      */     else {
/*      */       
/* 2593 */       validNCName = ((prefix == null || XML11Char.isXML11ValidNCName(prefix)) && XML11Char.isXML11ValidNCName(local));
/*      */     } 
/*      */     
/* 2596 */     if (!validNCName) {
/*      */ 
/*      */       
/* 2599 */       String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", null);
/*      */ 
/*      */ 
/*      */       
/* 2603 */       throw new DOMException((short)5, msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isXML11Version() {
/* 2612 */     return this.xml11Version;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isNormalizeDocRequired() {
/* 2618 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isXMLVersionChanged() {
/* 2624 */     return this.xmlVersionChanged;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUserData(NodeImpl n, Object data) {
/* 2634 */     setUserData(n, "XERCES1DOMUSERDATA", data, (UserDataHandler)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object getUserData(NodeImpl n) {
/* 2642 */     return getUserData(n, "XERCES1DOMUSERDATA");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addEventListener(NodeImpl node, String type, EventListener listener, boolean useCapture) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeEventListener(NodeImpl node, String type, EventListener listener, boolean useCapture) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void copyEventListeners(NodeImpl src, NodeImpl tgt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean dispatchEvent(NodeImpl node, Event event) {
/* 2666 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacedText(NodeImpl node) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void deletedText(NodeImpl node, int offset, int count) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void insertedText(NodeImpl node, int offset, int count) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void modifyingCharacterData(NodeImpl node, boolean replace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void modifiedCharacterData(NodeImpl node, String oldvalue, String value, boolean replace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void insertingNode(NodeImpl node, boolean replace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void insertedNode(NodeImpl node, NodeImpl newInternal, boolean replace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removingNode(NodeImpl node, NodeImpl oldChild, boolean replace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removedNode(NodeImpl node, boolean replace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacingNode(NodeImpl node) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacedNode(NodeImpl node) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacingData(NodeImpl node) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void replacedCharacterData(NodeImpl node, String oldvalue, String value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void modifiedAttrValue(AttrImpl attr, String oldvalue) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setAttrNode(AttrImpl attr, AttrImpl previous) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removedAttrNode(AttrImpl attr, NodeImpl oldOwner, String name) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void renamedAttrNode(Attr oldAt, Attr newAt) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void renamedElement(Element oldEl, Element newEl) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 2789 */     Hashtable<Node, Hashtable<String, ParentNode.UserDataRecord>> nud = null;
/* 2790 */     if (this.nodeUserData != null) {
/* 2791 */       nud = new Hashtable<>();
/* 2792 */       for (Map.Entry<Node, Map<String, ParentNode.UserDataRecord>> e : this.nodeUserData.entrySet())
/*      */       {
/* 2794 */         nud.put(e.getKey(), new Hashtable<>(e.getValue()));
/*      */       }
/*      */     } 
/*      */     
/* 2798 */     Hashtable<String, Node> ids = (this.identifiers == null) ? null : new Hashtable<>(this.identifiers);
/* 2799 */     Hashtable<Node, Integer> nt = (this.nodeTable == null) ? null : new Hashtable<>(this.nodeTable);
/*      */ 
/*      */     
/* 2802 */     ObjectOutputStream.PutField pf = out.putFields();
/* 2803 */     pf.put("docType", this.docType);
/* 2804 */     pf.put("docElement", this.docElement);
/* 2805 */     pf.put("fFreeNLCache", this.fFreeNLCache);
/* 2806 */     pf.put("encoding", this.encoding);
/* 2807 */     pf.put("actualEncoding", this.actualEncoding);
/* 2808 */     pf.put("version", this.version);
/* 2809 */     pf.put("standalone", this.standalone);
/* 2810 */     pf.put("fDocumentURI", this.fDocumentURI);
/*      */ 
/*      */     
/* 2813 */     pf.put("userData", nud);
/* 2814 */     pf.put("identifiers", ids);
/* 2815 */     pf.put("changes", this.changes);
/* 2816 */     pf.put("allowGrammarAccess", this.allowGrammarAccess);
/* 2817 */     pf.put("errorChecking", this.errorChecking);
/* 2818 */     pf.put("ancestorChecking", this.ancestorChecking);
/* 2819 */     pf.put("xmlVersionChanged", this.xmlVersionChanged);
/* 2820 */     pf.put("documentNumber", this.documentNumber);
/* 2821 */     pf.put("nodeCounter", this.nodeCounter);
/* 2822 */     pf.put("nodeTable", nt);
/* 2823 */     pf.put("xml11Version", this.xml11Version);
/* 2824 */     out.writeFields();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 2831 */     ObjectInputStream.GetField gf = in.readFields();
/* 2832 */     this.docType = (DocumentTypeImpl)gf.get("docType", (Object)null);
/* 2833 */     this.docElement = (ElementImpl)gf.get("docElement", (Object)null);
/* 2834 */     this.fFreeNLCache = (NodeListCache)gf.get("fFreeNLCache", (Object)null);
/* 2835 */     this.encoding = (String)gf.get("encoding", (Object)null);
/* 2836 */     this.actualEncoding = (String)gf.get("actualEncoding", (Object)null);
/* 2837 */     this.version = (String)gf.get("version", (Object)null);
/* 2838 */     this.standalone = gf.get("standalone", false);
/* 2839 */     this.fDocumentURI = (String)gf.get("fDocumentURI", (Object)null);
/*      */ 
/*      */ 
/*      */     
/* 2843 */     Hashtable<Node, Hashtable<String, ParentNode.UserDataRecord>> nud = (Hashtable<Node, Hashtable<String, ParentNode.UserDataRecord>>)gf.get("userData", (Object)null);
/*      */     
/* 2845 */     Hashtable<String, Node> ids = (Hashtable<String, Node>)gf.get("identifiers", (Object)null);
/*      */     
/* 2847 */     this.changes = gf.get("changes", 0);
/* 2848 */     this.allowGrammarAccess = gf.get("allowGrammarAccess", false);
/* 2849 */     this.errorChecking = gf.get("errorChecking", true);
/* 2850 */     this.ancestorChecking = gf.get("ancestorChecking", true);
/* 2851 */     this.xmlVersionChanged = gf.get("xmlVersionChanged", false);
/* 2852 */     this.documentNumber = gf.get("documentNumber", 0);
/* 2853 */     this.nodeCounter = gf.get("nodeCounter", 0);
/*      */     
/* 2855 */     Hashtable<Node, Integer> nt = (Hashtable<Node, Integer>)gf.get("nodeTable", (Object)null);
/*      */     
/* 2857 */     this.xml11Version = gf.get("xml11Version", false);
/*      */ 
/*      */     
/* 2860 */     if (nud != null) {
/* 2861 */       this.nodeUserData = new HashMap<>();
/* 2862 */       for (Map.Entry<Node, Hashtable<String, ParentNode.UserDataRecord>> e : nud.entrySet()) {
/* 2863 */         this.nodeUserData.put(e.getKey(), new HashMap<>(e.getValue()));
/*      */       }
/*      */     } 
/*      */     
/* 2867 */     if (ids != null) this.identifiers = new HashMap<>(ids); 
/* 2868 */     if (nt != null) this.nodeTable = new HashMap<>(nt); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/CoreDocumentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */