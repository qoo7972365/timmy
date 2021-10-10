/*      */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
/*      */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*      */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocument;
/*      */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*      */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*      */ import com.sun.xml.internal.messaging.saaj.util.NamespaceContextIterator;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.Name;
/*      */ import javax.xml.soap.Node;
/*      */ import javax.xml.soap.SOAPBodyElement;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.Text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ElementImpl
/*      */   extends ElementNSImpl
/*      */   implements SOAPElement, SOAPBodyElement
/*      */ {
/*   50 */   public static final String DSIG_NS = "http://www.w3.org/2000/09/xmldsig#".intern();
/*   51 */   public static final String XENC_NS = "http://www.w3.org/2001/04/xmlenc#".intern();
/*   52 */   public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".intern();
/*      */   
/*   54 */   private AttributeManager encodingStyleAttribute = new AttributeManager();
/*      */ 
/*      */   
/*      */   protected QName elementQName;
/*      */   
/*   59 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.soap.impl", "com.sun.xml.internal.messaging.saaj.soap.impl.LocalStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   67 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   73 */   public static final String XML_URI = "http://www.w3.org/XML/1998/namespace".intern();
/*      */   
/*      */   public ElementImpl(SOAPDocumentImpl ownerDoc, Name name) {
/*   76 */     super((CoreDocumentImpl)ownerDoc, name
/*      */         
/*   78 */         .getURI(), name
/*   79 */         .getQualifiedName(), name
/*   80 */         .getLocalName());
/*   81 */     this.elementQName = NameImpl.convertToQName(name);
/*      */   }
/*      */   
/*      */   public ElementImpl(SOAPDocumentImpl ownerDoc, QName name) {
/*   85 */     super((CoreDocumentImpl)ownerDoc, name
/*      */         
/*   87 */         .getNamespaceURI(), 
/*   88 */         getQualifiedName(name), name
/*   89 */         .getLocalPart());
/*   90 */     this.elementQName = name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElementImpl(SOAPDocumentImpl ownerDoc, String uri, String qualifiedName) {
/*   98 */     super((CoreDocumentImpl)ownerDoc, uri, qualifiedName);
/*   99 */     this
/*  100 */       .elementQName = new QName(uri, getLocalPart(qualifiedName), getPrefix(qualifiedName));
/*      */   }
/*      */   
/*      */   public void ensureNamespaceIsDeclared(String prefix, String uri) {
/*  104 */     String alreadyDeclaredUri = getNamespaceURI(prefix);
/*  105 */     if (alreadyDeclaredUri == null || !alreadyDeclaredUri.equals(uri)) {
/*      */       try {
/*  107 */         addNamespaceDeclaration(prefix, uri);
/*  108 */       } catch (SOAPException sOAPException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Document getOwnerDocument() {
/*  114 */     Document doc = super.getOwnerDocument();
/*  115 */     if (doc instanceof SOAPDocument) {
/*  116 */       return (Document)((SOAPDocument)doc).getDocument();
/*      */     }
/*  118 */     return doc;
/*      */   }
/*      */   
/*      */   public SOAPElement addChildElement(Name name) throws SOAPException {
/*  122 */     return addElement(name);
/*      */   }
/*      */   
/*      */   public SOAPElement addChildElement(QName qname) throws SOAPException {
/*  126 */     return addElement(qname);
/*      */   }
/*      */   
/*      */   public SOAPElement addChildElement(String localName) throws SOAPException {
/*  130 */     String nsUri = getNamespaceURI("");
/*      */ 
/*      */     
/*  133 */     Name name = (nsUri == null || nsUri.isEmpty()) ? (Name)NameImpl.createFromUnqualifiedName(localName) : NameImpl.createFromQualifiedName(localName, nsUri);
/*  134 */     return addChildElement(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addChildElement(String localName, String prefix) throws SOAPException {
/*  139 */     String uri = getNamespaceURI(prefix);
/*  140 */     if (uri == null) {
/*  141 */       log.log(Level.SEVERE, "SAAJ0101.impl.parent.of.body.elem.mustbe.body", (Object[])new String[] { prefix });
/*      */ 
/*      */ 
/*      */       
/*  145 */       throw new SOAPExceptionImpl("Unable to locate namespace for prefix " + prefix);
/*      */     } 
/*      */     
/*  148 */     return addChildElement(localName, prefix, uri);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceURI(String prefix) {
/*  153 */     if ("xmlns".equals(prefix)) {
/*  154 */       return XMLNS_URI;
/*      */     }
/*      */     
/*  157 */     if ("xml".equals(prefix)) {
/*  158 */       return XML_URI;
/*      */     }
/*      */     
/*  161 */     if ("".equals(prefix)) {
/*      */       
/*  163 */       Node currentAncestor = this;
/*  164 */       while (currentAncestor != null && !(currentAncestor instanceof Document))
/*      */       {
/*      */         
/*  167 */         if (currentAncestor instanceof ElementImpl) {
/*  168 */           QName name = ((ElementImpl)currentAncestor).getElementQName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  179 */           if (((Element)currentAncestor).hasAttributeNS(XMLNS_URI, "xmlns")) {
/*      */ 
/*      */ 
/*      */             
/*  183 */             String uri = ((Element)currentAncestor).getAttributeNS(XMLNS_URI, "xmlns");
/*      */             
/*  185 */             if ("".equals(uri)) {
/*  186 */               return null;
/*      */             }
/*  188 */             return uri;
/*      */           } 
/*      */         } 
/*      */         
/*  192 */         currentAncestor = currentAncestor.getParentNode();
/*      */       }
/*      */     
/*  195 */     } else if (prefix != null) {
/*      */       
/*  197 */       Node currentAncestor = this;
/*      */ 
/*      */ 
/*      */       
/*  201 */       while (currentAncestor != null && !(currentAncestor instanceof Document)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  216 */         if (((Element)currentAncestor).hasAttributeNS(XMLNS_URI, prefix))
/*      */         {
/*  218 */           return ((Element)currentAncestor).getAttributeNS(XMLNS_URI, prefix);
/*      */         }
/*      */ 
/*      */         
/*  222 */         currentAncestor = currentAncestor.getParentNode();
/*      */       } 
/*      */     } 
/*      */     
/*  226 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/*  231 */     ElementImpl copy = new ElementImpl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*  232 */     return replaceElementWithSOAPElement(this, copy);
/*      */   }
/*      */ 
/*      */   
/*      */   public QName createQName(String localName, String prefix) throws SOAPException {
/*  237 */     String uri = getNamespaceURI(prefix);
/*  238 */     if (uri == null) {
/*  239 */       log.log(Level.SEVERE, "SAAJ0102.impl.cannot.locate.ns", new Object[] { prefix });
/*      */       
/*  241 */       throw new SOAPException("Unable to locate namespace for prefix " + prefix);
/*      */     } 
/*      */     
/*  244 */     return new QName(uri, localName, prefix);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespacePrefix(String uri) {
/*  249 */     NamespaceContextIterator eachNamespace = getNamespaceContextNodes();
/*  250 */     while (eachNamespace.hasNext()) {
/*  251 */       Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
/*  252 */       if (namespaceDecl.getNodeValue().equals(uri)) {
/*  253 */         String candidatePrefix = namespaceDecl.getLocalName();
/*  254 */         if ("xmlns".equals(candidatePrefix)) {
/*  255 */           return "";
/*      */         }
/*  257 */         return candidatePrefix;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  262 */     Node currentAncestor = this;
/*  263 */     while (currentAncestor != null && !(currentAncestor instanceof Document)) {
/*      */ 
/*      */       
/*  266 */       if (uri.equals(currentAncestor.getNamespaceURI()))
/*  267 */         return currentAncestor.getPrefix(); 
/*  268 */       currentAncestor = currentAncestor.getParentNode();
/*      */     } 
/*      */     
/*  271 */     return null;
/*      */   }
/*      */   
/*      */   protected Attr getNamespaceAttr(String prefix) {
/*  275 */     NamespaceContextIterator eachNamespace = getNamespaceContextNodes();
/*  276 */     if (!"".equals(prefix))
/*  277 */       prefix = ":" + prefix; 
/*  278 */     while (eachNamespace.hasNext()) {
/*  279 */       Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
/*  280 */       if (!"".equals(prefix)) {
/*  281 */         if (namespaceDecl.getNodeName().endsWith(prefix))
/*  282 */           return namespaceDecl;  continue;
/*      */       } 
/*  284 */       if (namespaceDecl.getNodeName().equals("xmlns")) {
/*  285 */         return namespaceDecl;
/*      */       }
/*      */     } 
/*  288 */     return null;
/*      */   }
/*      */   
/*      */   public NamespaceContextIterator getNamespaceContextNodes() {
/*  292 */     return getNamespaceContextNodes(true);
/*      */   }
/*      */   
/*      */   public NamespaceContextIterator getNamespaceContextNodes(boolean traverseStack) {
/*  296 */     return new NamespaceContextIterator(this, traverseStack);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement addChildElement(String localName, String prefix, String uri) throws SOAPException {
/*  305 */     SOAPElement newElement = createElement((Name)NameImpl.create(localName, prefix, uri));
/*  306 */     addNode((Node)newElement);
/*  307 */     return convertToSoapElement((Element)newElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
/*  314 */     String elementURI = element.getElementName().getURI();
/*  315 */     String localName = element.getLocalName();
/*      */     
/*  317 */     if ("http://schemas.xmlsoap.org/soap/envelope/".equals(elementURI) || "http://www.w3.org/2003/05/soap-envelope"
/*  318 */       .equals(elementURI)) {
/*      */ 
/*      */       
/*  321 */       if ("Envelope".equalsIgnoreCase(localName) || "Header"
/*  322 */         .equalsIgnoreCase(localName) || "Body".equalsIgnoreCase(localName)) {
/*  323 */         log.severe("SAAJ0103.impl.cannot.add.fragements");
/*  324 */         throw new SOAPExceptionImpl("Cannot add fragments which contain elements which are in the SOAP namespace");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  329 */       if ("Fault".equalsIgnoreCase(localName) && !"Body".equalsIgnoreCase(getLocalName())) {
/*  330 */         log.severe("SAAJ0154.impl.adding.fault.to.nonbody");
/*  331 */         throw new SOAPExceptionImpl("Cannot add a SOAPFault as a child of " + getLocalName());
/*      */       } 
/*      */       
/*  334 */       if ("Detail".equalsIgnoreCase(localName) && !"Fault".equalsIgnoreCase(getLocalName())) {
/*  335 */         log.severe("SAAJ0155.impl.adding.detail.nonfault");
/*  336 */         throw new SOAPExceptionImpl("Cannot add a Detail as a child of " + getLocalName());
/*      */       } 
/*      */       
/*  339 */       if ("Fault".equalsIgnoreCase(localName)) {
/*      */         
/*  341 */         if (!elementURI.equals(getElementName().getURI())) {
/*  342 */           log.severe("SAAJ0158.impl.version.mismatch.fault");
/*  343 */           throw new SOAPExceptionImpl("SOAP Version mismatch encountered when trying to add SOAPFault to SOAPBody");
/*      */         } 
/*  345 */         Iterator it = getChildElements();
/*  346 */         if (it.hasNext()) {
/*  347 */           log.severe("SAAJ0156.impl.adding.fault.error");
/*  348 */           throw new SOAPExceptionImpl("Cannot add SOAPFault as a child of a non-Empty SOAPBody");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  354 */     String encodingStyle = element.getEncodingStyle();
/*      */     
/*  356 */     ElementImpl importedElement = (ElementImpl)importElement((Element)element);
/*  357 */     addNode(importedElement);
/*      */     
/*  359 */     if (encodingStyle != null) {
/*  360 */       importedElement.setEncodingStyle(encodingStyle);
/*      */     }
/*  362 */     return convertToSoapElement(importedElement);
/*      */   }
/*      */   
/*      */   protected Element importElement(Element element) {
/*  366 */     Document document = getOwnerDocument();
/*  367 */     Document oldDocument = element.getOwnerDocument();
/*  368 */     if (!oldDocument.equals(document)) {
/*  369 */       return (Element)document.importNode(element, true);
/*      */     }
/*  371 */     return element;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement addElement(Name name) throws SOAPException {
/*  376 */     SOAPElement newElement = createElement(name);
/*  377 */     addNode((Node)newElement);
/*  378 */     return newElement;
/*      */   }
/*      */   
/*      */   protected SOAPElement addElement(QName name) throws SOAPException {
/*  382 */     SOAPElement newElement = createElement(name);
/*  383 */     addNode((Node)newElement);
/*  384 */     return newElement;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement createElement(Name name) {
/*  389 */     if (isNamespaceQualified(name)) {
/*  390 */       return (SOAPElement)
/*  391 */         getOwnerDocument().createElementNS(name
/*  392 */           .getURI(), name
/*  393 */           .getQualifiedName());
/*      */     }
/*  395 */     return (SOAPElement)
/*  396 */       getOwnerDocument().createElement(name.getQualifiedName());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPElement createElement(QName name) {
/*  402 */     if (isNamespaceQualified(name)) {
/*  403 */       return (SOAPElement)
/*  404 */         getOwnerDocument().createElementNS(name
/*  405 */           .getNamespaceURI(), 
/*  406 */           getQualifiedName(name));
/*      */     }
/*  408 */     return (SOAPElement)
/*  409 */       getOwnerDocument().createElement(getQualifiedName(name));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addNode(Node newElement) throws SOAPException {
/*  414 */     insertBefore(newElement, (Node)null);
/*      */     
/*  416 */     if (getOwnerDocument() instanceof org.w3c.dom.DocumentFragment) {
/*      */       return;
/*      */     }
/*  419 */     if (newElement instanceof ElementImpl) {
/*  420 */       ElementImpl element = (ElementImpl)newElement;
/*  421 */       QName elementName = element.getElementQName();
/*  422 */       if (!"".equals(elementName.getNamespaceURI())) {
/*  423 */         element.ensureNamespaceIsDeclared(elementName
/*  424 */             .getPrefix(), elementName.getNamespaceURI());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement findChild(NameImpl name) {
/*  431 */     Iterator<SOAPElement> eachChild = getChildElementNodes();
/*  432 */     while (eachChild.hasNext()) {
/*  433 */       SOAPElement child = eachChild.next();
/*  434 */       if (child.getElementName().equals(name)) {
/*  435 */         return child;
/*      */       }
/*      */     } 
/*      */     
/*  439 */     return null;
/*      */   }
/*      */   
/*      */   public SOAPElement addTextNode(String text) throws SOAPException {
/*  443 */     if (text.startsWith("<![CDATA[") || text
/*  444 */       .startsWith("<![cdata["))
/*  445 */       return addCDATA(text
/*  446 */           .substring("<![CDATA[".length(), text.length() - 3)); 
/*  447 */     return addText(text);
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement addCDATA(String text) throws SOAPException {
/*  452 */     Text cdata = getOwnerDocument().createCDATASection(text);
/*  453 */     addNode(cdata);
/*  454 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement addText(String text) throws SOAPException {
/*  459 */     Text textNode = getOwnerDocument().createTextNode(text);
/*  460 */     addNode(textNode);
/*  461 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/*  466 */     addAttributeBare(name, value);
/*  467 */     if (!"".equals(name.getURI())) {
/*  468 */       ensureNamespaceIsDeclared(name.getPrefix(), name.getURI());
/*      */     }
/*  470 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addAttribute(QName qname, String value) throws SOAPException {
/*  475 */     addAttributeBare(qname, value);
/*  476 */     if (!"".equals(qname.getNamespaceURI())) {
/*  477 */       ensureNamespaceIsDeclared(qname.getPrefix(), qname.getNamespaceURI());
/*      */     }
/*  479 */     return this;
/*      */   }
/*      */   
/*      */   private void addAttributeBare(Name name, String value) {
/*  483 */     addAttributeBare(name
/*  484 */         .getURI(), name
/*  485 */         .getPrefix(), name
/*  486 */         .getQualifiedName(), value);
/*      */   }
/*      */   
/*      */   private void addAttributeBare(QName name, String value) {
/*  490 */     addAttributeBare(name
/*  491 */         .getNamespaceURI(), name
/*  492 */         .getPrefix(), 
/*  493 */         getQualifiedName(name), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAttributeBare(String uri, String prefix, String qualifiedName, String value) {
/*  503 */     uri = (uri.length() == 0) ? null : uri;
/*  504 */     if (qualifiedName.equals("xmlns")) {
/*  505 */       uri = XMLNS_URI;
/*      */     }
/*      */     
/*  508 */     if (uri == null) {
/*  509 */       setAttribute(qualifiedName, value);
/*      */     } else {
/*  511 */       setAttributeNS(uri, qualifiedName, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addNamespaceDeclaration(String prefix, String uri) throws SOAPException {
/*  517 */     if (prefix.length() > 0) {
/*  518 */       setAttributeNS(XMLNS_URI, "xmlns:" + prefix, uri);
/*      */     } else {
/*  520 */       setAttributeNS(XMLNS_URI, "xmlns", uri);
/*      */     } 
/*      */ 
/*      */     
/*  524 */     return this;
/*      */   }
/*      */   
/*      */   public String getAttributeValue(Name name) {
/*  528 */     return getAttributeValueFrom(this, name);
/*      */   }
/*      */   
/*      */   public String getAttributeValue(QName qname) {
/*  532 */     return getAttributeValueFrom(this, qname
/*      */         
/*  534 */         .getNamespaceURI(), qname
/*  535 */         .getLocalPart(), qname
/*  536 */         .getPrefix(), 
/*  537 */         getQualifiedName(qname));
/*      */   }
/*      */   
/*      */   public Iterator getAllAttributes() {
/*  541 */     Iterator<Name> i = getAllAttributesFrom(this);
/*  542 */     ArrayList<Name> list = new ArrayList();
/*  543 */     while (i.hasNext()) {
/*  544 */       Name name = i.next();
/*  545 */       if (!"xmlns".equalsIgnoreCase(name.getPrefix()))
/*  546 */         list.add(name); 
/*      */     } 
/*  548 */     return list.iterator();
/*      */   }
/*      */   
/*      */   public Iterator getAllAttributesAsQNames() {
/*  552 */     Iterator<Name> i = getAllAttributesFrom(this);
/*  553 */     ArrayList<QName> list = new ArrayList();
/*  554 */     while (i.hasNext()) {
/*  555 */       Name name = i.next();
/*  556 */       if (!"xmlns".equalsIgnoreCase(name.getPrefix())) {
/*  557 */         list.add(NameImpl.convertToQName(name));
/*      */       }
/*      */     } 
/*  560 */     return list.iterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator getNamespacePrefixes() {
/*  565 */     return doGetNamespacePrefixes(false);
/*      */   }
/*      */   
/*      */   public Iterator getVisibleNamespacePrefixes() {
/*  569 */     return doGetNamespacePrefixes(true);
/*      */   }
/*      */   
/*      */   protected Iterator doGetNamespacePrefixes(final boolean deep) {
/*  573 */     return new Iterator() {
/*  574 */         String next = null;
/*  575 */         String last = null;
/*  576 */         NamespaceContextIterator eachNamespace = ElementImpl.this
/*  577 */           .getNamespaceContextNodes(deep);
/*      */         
/*      */         void findNext() {
/*  580 */           while (this.next == null && this.eachNamespace.hasNext()) {
/*      */             
/*  582 */             String attributeKey = this.eachNamespace.nextNamespaceAttr().getNodeName();
/*  583 */             if (attributeKey.startsWith("xmlns:")) {
/*  584 */               this.next = attributeKey.substring("xmlns:".length());
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*      */         public boolean hasNext() {
/*  590 */           findNext();
/*  591 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/*  595 */           findNext();
/*  596 */           if (this.next == null) {
/*  597 */             throw new NoSuchElementException();
/*      */           }
/*      */           
/*  600 */           this.last = this.next;
/*  601 */           this.next = null;
/*  602 */           return this.last;
/*      */         }
/*      */         
/*      */         public void remove() {
/*  606 */           if (this.last == null) {
/*  607 */             throw new IllegalStateException();
/*      */           }
/*  609 */           this.eachNamespace.remove();
/*  610 */           this.next = null;
/*  611 */           this.last = null;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public Name getElementName() {
/*  617 */     return NameImpl.convertToName(this.elementQName);
/*      */   }
/*      */   
/*      */   public QName getElementQName() {
/*  621 */     return this.elementQName;
/*      */   }
/*      */   
/*      */   public boolean removeAttribute(Name name) {
/*  625 */     return removeAttribute(name.getURI(), name.getLocalName());
/*      */   }
/*      */   
/*      */   public boolean removeAttribute(QName name) {
/*  629 */     return removeAttribute(name.getNamespaceURI(), name.getLocalPart());
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean removeAttribute(String uri, String localName) {
/*  634 */     String nonzeroLengthUri = (uri == null || uri.length() == 0) ? null : uri;
/*      */     
/*  636 */     Attr attribute = getAttributeNodeNS(nonzeroLengthUri, localName);
/*  637 */     if (attribute == null) {
/*  638 */       return false;
/*      */     }
/*  640 */     removeAttributeNode(attribute);
/*  641 */     return true;
/*      */   }
/*      */   
/*      */   public boolean removeNamespaceDeclaration(String prefix) {
/*  645 */     Attr declaration = getNamespaceAttr(prefix);
/*  646 */     if (declaration == null) {
/*  647 */       return false;
/*      */     }
/*      */     try {
/*  650 */       removeAttributeNode(declaration);
/*  651 */     } catch (DOMException dOMException) {}
/*      */ 
/*      */     
/*  654 */     return true;
/*      */   }
/*      */   
/*      */   public Iterator getChildElements() {
/*  658 */     return getChildElementsFrom(this);
/*      */   }
/*      */   
/*      */   protected SOAPElement convertToSoapElement(Element element) {
/*  662 */     if (element instanceof SOAPElement) {
/*  663 */       return (SOAPElement)element;
/*      */     }
/*  665 */     return replaceElementWithSOAPElement(element, (ElementImpl)
/*      */         
/*  667 */         createElement(NameImpl.copyElementName(element)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static SOAPElement replaceElementWithSOAPElement(Element element, ElementImpl copy) {
/*  675 */     Iterator<Name> eachAttribute = getAllAttributesFrom(element);
/*  676 */     while (eachAttribute.hasNext()) {
/*  677 */       Name name = eachAttribute.next();
/*  678 */       copy.addAttributeBare(name, getAttributeValueFrom(element, name));
/*      */     } 
/*      */     
/*  681 */     Iterator<Node> eachChild = getChildElementsFrom(element);
/*  682 */     while (eachChild.hasNext()) {
/*  683 */       Node nextChild = eachChild.next();
/*  684 */       copy.insertBefore(nextChild, (Node)null);
/*      */     } 
/*      */     
/*  687 */     Node parent = element.getParentNode();
/*  688 */     if (parent != null) {
/*  689 */       parent.replaceChild(copy, element);
/*      */     }
/*      */     
/*  692 */     return copy;
/*      */   }
/*      */   
/*      */   protected Iterator getChildElementNodes() {
/*  696 */     return new Iterator() {
/*  697 */         Iterator eachNode = ElementImpl.this.getChildElements();
/*  698 */         Node next = null;
/*  699 */         Node last = null;
/*      */         
/*      */         public boolean hasNext() {
/*  702 */           if (this.next == null) {
/*  703 */             while (this.eachNode.hasNext()) {
/*  704 */               Node node = this.eachNode.next();
/*  705 */               if (node instanceof SOAPElement) {
/*  706 */                 this.next = node;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*  711 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/*  715 */           if (hasNext()) {
/*  716 */             this.last = this.next;
/*  717 */             this.next = null;
/*  718 */             return this.last;
/*      */           } 
/*  720 */           throw new NoSuchElementException();
/*      */         }
/*      */         
/*      */         public void remove() {
/*  724 */           if (this.last == null) {
/*  725 */             throw new IllegalStateException();
/*      */           }
/*  727 */           Node target = this.last;
/*  728 */           this.last = null;
/*  729 */           ElementImpl.this.removeChild(target);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public Iterator getChildElements(Name name) {
/*  735 */     return getChildElements(name.getURI(), name.getLocalName());
/*      */   }
/*      */   
/*      */   public Iterator getChildElements(QName qname) {
/*  739 */     return getChildElements(qname.getNamespaceURI(), qname.getLocalPart());
/*      */   }
/*      */   
/*      */   private Iterator getChildElements(final String nameUri, final String nameLocal) {
/*  743 */     return new Iterator() {
/*  744 */         Iterator eachElement = ElementImpl.this.getChildElementNodes();
/*  745 */         Node next = null;
/*  746 */         Node last = null;
/*      */         
/*      */         public boolean hasNext() {
/*  749 */           if (this.next == null) {
/*  750 */             while (this.eachElement.hasNext()) {
/*  751 */               Node element = this.eachElement.next();
/*  752 */               String elementUri = element.getNamespaceURI();
/*  753 */               elementUri = (elementUri == null) ? "" : elementUri;
/*  754 */               String elementName = element.getLocalName();
/*  755 */               if (elementUri.equals(nameUri) && elementName
/*  756 */                 .equals(nameLocal)) {
/*  757 */                 this.next = element;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*  762 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/*  766 */           if (!hasNext()) {
/*  767 */             throw new NoSuchElementException();
/*      */           }
/*  769 */           this.last = this.next;
/*  770 */           this.next = null;
/*  771 */           return this.last;
/*      */         }
/*      */         
/*      */         public void remove() {
/*  775 */           if (this.last == null) {
/*  776 */             throw new IllegalStateException();
/*      */           }
/*  778 */           Node target = this.last;
/*  779 */           this.last = null;
/*  780 */           ElementImpl.this.removeChild(target);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public void removeContents() {
/*  786 */     Node currentChild = getFirstChild();
/*      */     
/*  788 */     while (currentChild != null) {
/*  789 */       Node temp = currentChild.getNextSibling();
/*  790 */       if (currentChild instanceof Node) {
/*  791 */         ((Node)currentChild).detachNode();
/*      */       } else {
/*  793 */         Node parent = currentChild.getParentNode();
/*  794 */         if (parent != null) {
/*  795 */           parent.removeChild(currentChild);
/*      */         }
/*      */       } 
/*      */       
/*  799 */       currentChild = temp;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/*  804 */     if (!"".equals(encodingStyle)) {
/*      */       try {
/*  806 */         new URI(encodingStyle);
/*  807 */       } catch (URISyntaxException m) {
/*  808 */         log.log(Level.SEVERE, "SAAJ0105.impl.encoding.style.mustbe.valid.URI", (Object[])new String[] { encodingStyle });
/*      */ 
/*      */ 
/*      */         
/*  812 */         throw new IllegalArgumentException("Encoding style (" + encodingStyle + ") should be a valid URI");
/*      */       } 
/*      */     }
/*      */     
/*  816 */     this.encodingStyleAttribute.setValue(encodingStyle);
/*  817 */     tryToFindEncodingStyleAttributeName();
/*      */   }
/*      */   
/*      */   public String getEncodingStyle() {
/*  821 */     String encodingStyle = this.encodingStyleAttribute.getValue();
/*  822 */     if (encodingStyle != null)
/*  823 */       return encodingStyle; 
/*  824 */     String soapNamespace = getSOAPNamespace();
/*  825 */     if (soapNamespace != null) {
/*  826 */       Attr attr = getAttributeNodeNS(soapNamespace, "encodingStyle");
/*  827 */       if (attr != null) {
/*  828 */         encodingStyle = attr.getValue();
/*      */         try {
/*  830 */           setEncodingStyle(encodingStyle);
/*  831 */         } catch (SOAPException sOAPException) {}
/*      */ 
/*      */         
/*  834 */         return encodingStyle;
/*      */       } 
/*      */     } 
/*  837 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getValue() {
/*  842 */     Node valueNode = getValueNode();
/*  843 */     return (valueNode == null) ? null : valueNode.getValue();
/*      */   }
/*      */   
/*      */   public void setValue(String value) {
/*  847 */     Node valueNode = getValueNodeStrict();
/*  848 */     if (valueNode != null) {
/*  849 */       valueNode.setNodeValue(value);
/*      */     } else {
/*      */       try {
/*  852 */         addTextNode(value);
/*  853 */       } catch (SOAPException e) {
/*  854 */         throw new RuntimeException(e.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Node getValueNodeStrict() {
/*  860 */     Node node = getFirstChild();
/*  861 */     if (node != null) {
/*  862 */       if (node.getNextSibling() == null && node
/*  863 */         .getNodeType() == 3) {
/*  864 */         return node;
/*      */       }
/*  866 */       log.severe("SAAJ0107.impl.elem.child.not.single.text");
/*  867 */       throw new IllegalStateException();
/*      */     } 
/*      */ 
/*      */     
/*  871 */     return null;
/*      */   }
/*      */   
/*      */   protected Node getValueNode() {
/*  875 */     Iterator<Node> i = getChildElements();
/*  876 */     while (i.hasNext()) {
/*  877 */       Node n = i.next();
/*  878 */       if (n.getNodeType() == 3 || n
/*  879 */         .getNodeType() == 4) {
/*      */         
/*  881 */         normalize();
/*      */ 
/*      */         
/*  884 */         return n;
/*      */       } 
/*      */     } 
/*  887 */     return null;
/*      */   }
/*      */   
/*      */   public void setParentElement(SOAPElement element) throws SOAPException {
/*  891 */     if (element == null) {
/*  892 */       log.severe("SAAJ0106.impl.no.null.to.parent.elem");
/*  893 */       throw new SOAPException("Cannot pass NULL to setParentElement");
/*      */     } 
/*  895 */     element.addChildElement(this);
/*  896 */     findEncodingStyleAttributeName();
/*      */   }
/*      */   
/*      */   protected void findEncodingStyleAttributeName() throws SOAPException {
/*  900 */     String soapNamespace = getSOAPNamespace();
/*  901 */     if (soapNamespace != null) {
/*  902 */       String soapNamespacePrefix = getNamespacePrefix(soapNamespace);
/*  903 */       if (soapNamespacePrefix != null) {
/*  904 */         setEncodingStyleNamespace(soapNamespace, soapNamespacePrefix);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEncodingStyleNamespace(String soapNamespace, String soapNamespacePrefix) throws SOAPException {
/*  914 */     NameImpl nameImpl = NameImpl.create("encodingStyle", soapNamespacePrefix, soapNamespace);
/*      */ 
/*      */ 
/*      */     
/*  918 */     this.encodingStyleAttribute.setName((Name)nameImpl);
/*      */   }
/*      */   
/*      */   public SOAPElement getParentElement() {
/*  922 */     Node parentNode = getParentNode();
/*  923 */     if (parentNode instanceof SOAPDocument) {
/*  924 */       return null;
/*      */     }
/*  926 */     return (SOAPElement)parentNode;
/*      */   }
/*      */   
/*      */   protected String getSOAPNamespace() {
/*  930 */     String soapNamespace = null;
/*      */     
/*  932 */     SOAPElement antecedent = this;
/*  933 */     while (antecedent != null) {
/*  934 */       Name antecedentName = antecedent.getElementName();
/*  935 */       String antecedentNamespace = antecedentName.getURI();
/*      */       
/*  937 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(antecedentNamespace) || "http://www.w3.org/2003/05/soap-envelope"
/*  938 */         .equals(antecedentNamespace)) {
/*      */         
/*  940 */         soapNamespace = antecedentNamespace;
/*      */         
/*      */         break;
/*      */       } 
/*  944 */       antecedent = antecedent.getParentElement();
/*      */     } 
/*      */     
/*  947 */     return soapNamespace;
/*      */   }
/*      */   
/*      */   public void detachNode() {
/*  951 */     Node parent = getParentNode();
/*  952 */     if (parent != null) {
/*  953 */       parent.removeChild(this);
/*      */     }
/*  955 */     this.encodingStyleAttribute.clearNameAndValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void tryToFindEncodingStyleAttributeName() {
/*      */     try {
/*  962 */       findEncodingStyleAttributeName();
/*  963 */     } catch (SOAPException sOAPException) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public void recycleNode() {
/*  968 */     detachNode();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class AttributeManager
/*      */   {
/*  975 */     Name attributeName = null;
/*  976 */     String attributeValue = null;
/*      */     
/*      */     public void setName(Name newName) throws SOAPException {
/*  979 */       clearAttribute();
/*  980 */       this.attributeName = newName;
/*  981 */       reconcileAttribute();
/*      */     }
/*      */     public void clearName() {
/*  984 */       clearAttribute();
/*  985 */       this.attributeName = null;
/*      */     }
/*      */     public void setValue(String value) throws SOAPException {
/*  988 */       this.attributeValue = value;
/*  989 */       reconcileAttribute();
/*      */     }
/*      */     public Name getName() {
/*  992 */       return this.attributeName;
/*      */     }
/*      */     public String getValue() {
/*  995 */       return this.attributeValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clearNameAndValue() {
/* 1000 */       this.attributeName = null;
/* 1001 */       this.attributeValue = null;
/*      */     }
/*      */     
/*      */     private void reconcileAttribute() throws SOAPException {
/* 1005 */       if (this.attributeName != null) {
/* 1006 */         ElementImpl.this.removeAttribute(this.attributeName);
/* 1007 */         if (this.attributeValue != null)
/* 1008 */           ElementImpl.this.addAttribute(this.attributeName, this.attributeValue); 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void clearAttribute() {
/* 1013 */       if (this.attributeName != null) {
/* 1014 */         ElementImpl.this.removeAttribute(this.attributeName);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Attr getNamespaceAttrFrom(Element element, String prefix) {
/* 1022 */     NamespaceContextIterator eachNamespace = new NamespaceContextIterator(element);
/*      */     
/* 1024 */     while (eachNamespace.hasNext()) {
/* 1025 */       Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
/*      */       
/* 1027 */       String declaredPrefix = NameImpl.getLocalNameFromTagName(namespaceDecl.getNodeName());
/* 1028 */       if (declaredPrefix.equals(prefix)) {
/* 1029 */         return namespaceDecl;
/*      */       }
/*      */     } 
/* 1032 */     return null;
/*      */   }
/*      */   
/*      */   protected static Iterator getAllAttributesFrom(Element element) {
/* 1036 */     final NamedNodeMap attributes = element.getAttributes();
/*      */     
/* 1038 */     return new Iterator() {
/* 1039 */         int attributesLength = attributes.getLength();
/* 1040 */         int attributeIndex = 0;
/*      */         String currentName;
/*      */         
/*      */         public boolean hasNext() {
/* 1044 */           return (this.attributeIndex < this.attributesLength);
/*      */         }
/*      */         
/*      */         public Object next() {
/* 1048 */           if (!hasNext()) {
/* 1049 */             throw new NoSuchElementException();
/*      */           }
/* 1051 */           Node current = attributes.item(this.attributeIndex++);
/* 1052 */           this.currentName = current.getNodeName();
/*      */           
/* 1054 */           String prefix = NameImpl.getPrefixFromTagName(this.currentName);
/* 1055 */           if (prefix.length() == 0) {
/* 1056 */             return NameImpl.createFromUnqualifiedName(this.currentName);
/*      */           }
/*      */           
/* 1059 */           Name attributeName = NameImpl.createFromQualifiedName(this.currentName, current
/*      */               
/* 1061 */               .getNamespaceURI());
/* 1062 */           return attributeName;
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/* 1067 */           if (this.currentName == null) {
/* 1068 */             throw new IllegalStateException();
/*      */           }
/* 1070 */           attributes.removeNamedItem(this.currentName);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   protected static String getAttributeValueFrom(Element element, Name name) {
/* 1076 */     return getAttributeValueFrom(element, name
/*      */         
/* 1078 */         .getURI(), name
/* 1079 */         .getLocalName(), name
/* 1080 */         .getPrefix(), name
/* 1081 */         .getQualifiedName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getAttributeValueFrom(Element element, String uri, String localName, String prefix, String qualifiedName) {
/* 1092 */     String nonzeroLengthUri = (uri == null || uri.length() == 0) ? null : uri;
/*      */     
/* 1094 */     boolean mustUseGetAttributeNodeNS = (nonzeroLengthUri != null);
/*      */     
/* 1096 */     if (mustUseGetAttributeNodeNS) {
/*      */       
/* 1098 */       if (!element.hasAttributeNS(uri, localName)) {
/* 1099 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1103 */       String attrValue = element.getAttributeNS(nonzeroLengthUri, localName);
/*      */       
/* 1105 */       return attrValue;
/*      */     } 
/*      */     
/* 1108 */     Attr attribute = null;
/* 1109 */     attribute = element.getAttributeNode(qualifiedName);
/*      */     
/* 1111 */     return (attribute == null) ? null : attribute.getValue();
/*      */   }
/*      */   
/*      */   protected static Iterator getChildElementsFrom(final Element element) {
/* 1115 */     return new Iterator() {
/* 1116 */         Node next = element.getFirstChild();
/* 1117 */         Node nextNext = null;
/* 1118 */         Node last = null;
/*      */         
/*      */         public boolean hasNext() {
/* 1121 */           if (this.next != null) {
/* 1122 */             return true;
/*      */           }
/* 1124 */           if (this.next == null && this.nextNext != null) {
/* 1125 */             this.next = this.nextNext;
/*      */           }
/*      */           
/* 1128 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/* 1132 */           if (hasNext()) {
/* 1133 */             this.last = this.next;
/* 1134 */             this.next = null;
/*      */             
/* 1136 */             if (element instanceof ElementImpl && this.last instanceof Element)
/*      */             {
/* 1138 */               this
/* 1139 */                 .last = (Node)((ElementImpl)element).convertToSoapElement((Element)this.last);
/*      */             }
/*      */ 
/*      */             
/* 1143 */             this.nextNext = this.last.getNextSibling();
/* 1144 */             return this.last;
/*      */           } 
/* 1146 */           throw new NoSuchElementException();
/*      */         }
/*      */         
/*      */         public void remove() {
/* 1150 */           if (this.last == null) {
/* 1151 */             throw new IllegalStateException();
/*      */           }
/* 1153 */           Node target = this.last;
/* 1154 */           this.last = null;
/* 1155 */           element.removeChild(target);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static String getQualifiedName(QName name) {
/* 1161 */     String prefix = name.getPrefix();
/* 1162 */     String localName = name.getLocalPart();
/* 1163 */     String qualifiedName = null;
/*      */     
/* 1165 */     if (prefix != null && prefix.length() > 0) {
/* 1166 */       qualifiedName = prefix + ":" + localName;
/*      */     } else {
/* 1168 */       qualifiedName = localName;
/*      */     } 
/* 1170 */     return qualifiedName;
/*      */   }
/*      */   
/*      */   public static String getLocalPart(String qualifiedName) {
/* 1174 */     if (qualifiedName == null)
/*      */     {
/* 1176 */       throw new IllegalArgumentException("Cannot get local name for a \"null\" qualified name");
/*      */     }
/*      */     
/* 1179 */     int index = qualifiedName.indexOf(':');
/* 1180 */     if (index < 0) {
/* 1181 */       return qualifiedName;
/*      */     }
/* 1183 */     return qualifiedName.substring(index + 1);
/*      */   }
/*      */   
/*      */   public static String getPrefix(String qualifiedName) {
/* 1187 */     if (qualifiedName == null)
/*      */     {
/* 1189 */       throw new IllegalArgumentException("Cannot get prefix for a  \"null\" qualified name");
/*      */     }
/*      */     
/* 1192 */     int index = qualifiedName.indexOf(':');
/* 1193 */     if (index < 0) {
/* 1194 */       return "";
/*      */     }
/* 1196 */     return qualifiedName.substring(0, index);
/*      */   }
/*      */   
/*      */   protected boolean isNamespaceQualified(Name name) {
/* 1200 */     return !"".equals(name.getURI());
/*      */   }
/*      */   
/*      */   protected boolean isNamespaceQualified(QName name) {
/* 1204 */     return !"".equals(name.getNamespaceURI());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
/*      */     String localName;
/* 1212 */     int index = qualifiedName.indexOf(':');
/*      */     
/* 1214 */     if (index < 0) {
/* 1215 */       localName = qualifiedName;
/*      */     } else {
/* 1217 */       localName = qualifiedName.substring(index + 1);
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
/* 1229 */     super.setAttributeNS(namespaceURI, qualifiedName, value);
/*      */     
/* 1231 */     String tmpURI = getNamespaceURI();
/* 1232 */     boolean isIDNS = false;
/* 1233 */     if (tmpURI != null && (tmpURI.equals(DSIG_NS) || tmpURI.equals(XENC_NS))) {
/* 1234 */       isIDNS = true;
/*      */     }
/*      */ 
/*      */     
/* 1238 */     if (localName.equals("Id"))
/* 1239 */       if (namespaceURI == null || namespaceURI.equals("")) {
/* 1240 */         setIdAttribute(localName, true);
/* 1241 */       } else if (isIDNS || WSU_NS.equals(namespaceURI)) {
/* 1242 */         setIdAttributeNS(namespaceURI, localName, true);
/*      */       }  
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/ElementImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */