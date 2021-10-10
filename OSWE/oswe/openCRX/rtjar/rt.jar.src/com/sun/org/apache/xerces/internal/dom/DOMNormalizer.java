/*      */ package com.sun.org.apache.xerces.internal.dom;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.impl.Constants;
/*      */ import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.DTDGrammar;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDDescription;
/*      */ import com.sun.org.apache.xerces.internal.impl.dtd.XMLDTDValidator;
/*      */ import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
/*      */ import com.sun.org.apache.xerces.internal.impl.xs.util.SimpleLocator;
/*      */ import com.sun.org.apache.xerces.internal.parsers.XMLGrammarPreparser;
/*      */ import com.sun.org.apache.xerces.internal.util.AugmentationsImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
/*      */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*      */ import com.sun.org.apache.xerces.internal.util.XML11Char;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLGrammarPoolImpl;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLSymbols;
/*      */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*      */ import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLLocator;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*      */ import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
/*      */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*      */ import com.sun.org.apache.xerces.internal.xs.AttributePSVI;
/*      */ import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
/*      */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Vector;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.Comment;
/*      */ import org.w3c.dom.DOMErrorHandler;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.DocumentType;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Entity;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.w3c.dom.ProcessingInstruction;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DOMNormalizer
/*      */   implements XMLDocumentHandler
/*      */ {
/*      */   protected static final boolean DEBUG_ND = false;
/*      */   protected static final boolean DEBUG = false;
/*      */   protected static final boolean DEBUG_EVENTS = false;
/*      */   protected static final String PREFIX = "NS";
/*  118 */   protected DOMConfigurationImpl fConfiguration = null;
/*  119 */   protected CoreDocumentImpl fDocument = null;
/*  120 */   protected final XMLAttributesProxy fAttrProxy = new XMLAttributesProxy();
/*  121 */   protected final QName fQName = new QName();
/*      */ 
/*      */ 
/*      */   
/*      */   protected RevalidationHandler fValidationHandler;
/*      */ 
/*      */ 
/*      */   
/*      */   protected SymbolTable fSymbolTable;
/*      */ 
/*      */   
/*      */   protected DOMErrorHandler fErrorHandler;
/*      */ 
/*      */   
/*  135 */   private final DOMErrorImpl fError = new DOMErrorImpl();
/*      */ 
/*      */   
/*      */   protected boolean fNamespaceValidation = false;
/*      */ 
/*      */   
/*      */   protected boolean fPSVI = false;
/*      */ 
/*      */   
/*  144 */   protected final NamespaceContext fNamespaceContext = new NamespaceSupport();
/*      */ 
/*      */   
/*  147 */   protected final NamespaceContext fLocalNSBinder = new NamespaceSupport();
/*      */ 
/*      */   
/*  150 */   protected final ArrayList fAttributeList = new ArrayList(5);
/*      */ 
/*      */   
/*  153 */   protected final DOMLocatorImpl fLocator = new DOMLocatorImpl();
/*      */ 
/*      */   
/*  156 */   protected Node fCurrentNode = null;
/*  157 */   private QName fAttrQName = new QName();
/*      */ 
/*      */   
/*  160 */   final XMLString fNormalizedValue = new XMLString(new char[16], 0, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private XMLDTDValidator fDTDValidator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean allWhitespace = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void normalizeDocument(CoreDocumentImpl document, DOMConfigurationImpl config) {
/*  181 */     this.fDocument = document;
/*  182 */     this.fConfiguration = config;
/*      */ 
/*      */ 
/*      */     
/*  186 */     this.fSymbolTable = (SymbolTable)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/symbol-table");
/*      */     
/*  188 */     this.fNamespaceContext.reset();
/*  189 */     this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*      */     
/*  191 */     if ((this.fConfiguration.features & 0x40) != 0) {
/*  192 */       String schemaLang = (String)this.fConfiguration.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
/*      */       
/*  194 */       if (schemaLang != null && schemaLang.equals(Constants.NS_XMLSCHEMA)) {
/*  195 */         this
/*  196 */           .fValidationHandler = CoreDOMImplementationImpl.singleton.getValidator("http://www.w3.org/2001/XMLSchema");
/*  197 */         this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", true);
/*  198 */         this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
/*      */         
/*  200 */         this.fNamespaceValidation = true;
/*      */ 
/*      */         
/*  203 */         this.fPSVI = ((this.fConfiguration.features & 0x80) != 0);
/*      */       } 
/*      */       
/*  206 */       this.fConfiguration.setFeature("http://xml.org/sax/features/validation", true);
/*      */ 
/*      */       
/*  209 */       this.fDocument.clearIdentifiers();
/*      */       
/*  211 */       if (this.fValidationHandler != null)
/*      */       {
/*  213 */         ((XMLComponent)this.fValidationHandler).reset(this.fConfiguration);
/*      */       }
/*      */     } 
/*      */     
/*  217 */     this.fErrorHandler = (DOMErrorHandler)this.fConfiguration.getParameter("error-handler");
/*  218 */     if (this.fValidationHandler != null) {
/*  219 */       this.fValidationHandler.setDocumentHandler(this);
/*  220 */       this.fValidationHandler.startDocument(new SimpleLocator(this.fDocument.fDocumentURI, this.fDocument.fDocumentURI, -1, -1), this.fDocument.encoding, this.fNamespaceContext, (Augmentations)null);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  227 */       for (Node kid = this.fDocument.getFirstChild(); kid != null; kid = next) {
/*  228 */         Node next = kid.getNextSibling();
/*  229 */         kid = normalizeNode(kid);
/*  230 */         if (kid != null) {
/*  231 */           next = kid;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  236 */       if (this.fValidationHandler != null) {
/*  237 */         this.fValidationHandler.endDocument((Augmentations)null);
/*  238 */         CoreDOMImplementationImpl.singleton.releaseValidator("http://www.w3.org/2001/XMLSchema", this.fValidationHandler);
/*      */         
/*  240 */         this.fValidationHandler = null;
/*      */       } 
/*  242 */     } catch (AbortException e) {
/*      */       return;
/*  244 */     } catch (RuntimeException e) {
/*  245 */       throw e;
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
/*      */   protected Node normalizeNode(Node node) {
/*      */     DocumentTypeImpl docType;
/*      */     ElementImpl elem;
/*      */     String value;
/*      */     Node next;
/*      */     AttributeMap attributes;
/*      */     short nextType;
/*      */     Node kid;
/*  268 */     int type = node.getNodeType();
/*      */     
/*  270 */     this.fLocator.fRelatedNode = node;
/*      */     
/*  272 */     switch (type) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 10:
/*  277 */         docType = (DocumentTypeImpl)node;
/*  278 */         this.fDTDValidator = (XMLDTDValidator)CoreDOMImplementationImpl.singleton.getValidator("http://www.w3.org/TR/REC-xml");
/*  279 */         this.fDTDValidator.setDocumentHandler(this);
/*  280 */         this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", createGrammarPool(docType));
/*  281 */         this.fDTDValidator.reset(this.fConfiguration);
/*  282 */         this.fDTDValidator.startDocument(new SimpleLocator(this.fDocument.fDocumentURI, this.fDocument.fDocumentURI, -1, -1), this.fDocument.encoding, this.fNamespaceContext, null);
/*      */ 
/*      */         
/*  285 */         this.fDTDValidator.doctypeDecl(docType.getName(), docType.getPublicId(), docType.getSystemId(), null);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  297 */         if (this.fDocument.errorChecking && (
/*  298 */           this.fConfiguration.features & 0x100) != 0 && this.fDocument
/*  299 */           .isXMLVersionChanged()) {
/*  300 */           boolean wellformed; if (this.fNamespaceValidation) {
/*  301 */             wellformed = CoreDocumentImpl.isValidQName(node.getPrefix(), node.getLocalName(), this.fDocument.isXML11Version());
/*      */           } else {
/*      */             
/*  304 */             wellformed = CoreDocumentImpl.isXMLName(node.getNodeName(), this.fDocument.isXML11Version());
/*      */           } 
/*  306 */           if (!wellformed) {
/*  307 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Element", node
/*      */ 
/*      */                   
/*  310 */                   .getNodeName() });
/*  311 */             reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "wf-invalid-character-in-node-name");
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  317 */         this.fNamespaceContext.pushContext();
/*  318 */         this.fLocalNSBinder.reset();
/*      */         
/*  320 */         elem = (ElementImpl)node;
/*  321 */         if (elem.needsSyncChildren()) {
/*  322 */           elem.synchronizeChildren();
/*      */         }
/*  324 */         attributes = elem.hasAttributes() ? (AttributeMap)elem.getAttributes() : null;
/*      */ 
/*      */         
/*  327 */         if ((this.fConfiguration.features & 0x1) != 0) {
/*      */ 
/*      */ 
/*      */           
/*  331 */           namespaceFixUp(elem, attributes);
/*      */           
/*  333 */           if ((this.fConfiguration.features & 0x200) == 0 && attributes != null) {
/*  334 */             for (int i = 0; i < attributes.getLength(); i++) {
/*  335 */               Attr att = (Attr)attributes.getItem(i);
/*  336 */               if (XMLSymbols.PREFIX_XMLNS.equals(att.getPrefix()) || XMLSymbols.PREFIX_XMLNS
/*  337 */                 .equals(att.getName())) {
/*  338 */                 elem.removeAttributeNode(att);
/*  339 */                 i--;
/*      */               }
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  345 */         } else if (attributes != null) {
/*  346 */           for (int i = 0; i < attributes.getLength(); i++) {
/*  347 */             Attr attr = (Attr)attributes.item(i);
/*      */             
/*  349 */             attr.normalize();
/*  350 */             if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0) {
/*  351 */               isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, attributes, attr, attr.getValue(), this.fDocument.isXML11Version());
/*  352 */               if (this.fDocument.isXMLVersionChanged()) {
/*  353 */                 boolean wellformed = CoreDocumentImpl.isXMLName(node.getNodeName(), this.fDocument.isXML11Version());
/*  354 */                 if (!wellformed) {
/*  355 */                   String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Attr", node
/*      */ 
/*      */                         
/*  358 */                         .getNodeName() });
/*  359 */                   reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "wf-invalid-character-in-node-name");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  369 */         if (this.fValidationHandler != null) {
/*      */ 
/*      */ 
/*      */           
/*  373 */           this.fAttrProxy.setAttributes(attributes, this.fDocument, elem);
/*  374 */           updateQName(elem, this.fQName);
/*      */ 
/*      */           
/*  377 */           this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  378 */           this.fCurrentNode = node;
/*      */           
/*  380 */           this.fValidationHandler.startElement(this.fQName, this.fAttrProxy, (Augmentations)null);
/*      */         } 
/*      */         
/*  383 */         if (this.fDTDValidator != null) {
/*      */ 
/*      */ 
/*      */           
/*  387 */           this.fAttrProxy.setAttributes(attributes, this.fDocument, elem);
/*  388 */           updateQName(elem, this.fQName);
/*      */ 
/*      */           
/*  391 */           this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  392 */           this.fCurrentNode = node;
/*      */           
/*  394 */           this.fDTDValidator.startElement(this.fQName, this.fAttrProxy, null);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  399 */         for (kid = elem.getFirstChild(); kid != null; kid = node1) {
/*  400 */           Node node1 = kid.getNextSibling();
/*  401 */           kid = normalizeNode(kid);
/*  402 */           if (kid != null) {
/*  403 */             node1 = kid;
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
/*      */ 
/*      */ 
/*      */         
/*  417 */         if (this.fValidationHandler != null) {
/*  418 */           updateQName(elem, this.fQName);
/*      */ 
/*      */ 
/*      */           
/*  422 */           this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  423 */           this.fCurrentNode = node;
/*  424 */           this.fValidationHandler.endElement(this.fQName, (Augmentations)null);
/*      */         } 
/*      */         
/*  427 */         if (this.fDTDValidator != null) {
/*  428 */           updateQName(elem, this.fQName);
/*      */ 
/*      */ 
/*      */           
/*  432 */           this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  433 */           this.fCurrentNode = node;
/*  434 */           this.fDTDValidator.endElement(this.fQName, null);
/*      */         } 
/*      */ 
/*      */         
/*  438 */         this.fNamespaceContext.popContext();
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 8:
/*  448 */         if ((this.fConfiguration.features & 0x20) == 0) {
/*  449 */           Node prevSibling = node.getPreviousSibling();
/*  450 */           Node parent = node.getParentNode();
/*      */           
/*  452 */           parent.removeChild(node);
/*  453 */           if (prevSibling != null && prevSibling.getNodeType() == 3) {
/*  454 */             Node nextSibling = prevSibling.getNextSibling();
/*  455 */             if (nextSibling != null && nextSibling.getNodeType() == 3) {
/*  456 */               ((TextImpl)nextSibling).insertData(0, prevSibling.getNodeValue());
/*  457 */               parent.removeChild(prevSibling);
/*  458 */               return nextSibling;
/*      */             } 
/*      */           } 
/*      */           break;
/*      */         } 
/*  463 */         if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0) {
/*  464 */           String commentdata = ((Comment)node).getData();
/*      */ 
/*      */           
/*  467 */           isCommentWF(this.fErrorHandler, this.fError, this.fLocator, commentdata, this.fDocument.isXML11Version());
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 5:
/*  477 */         if ((this.fConfiguration.features & 0x4) == 0) {
/*  478 */           Node prevSibling = node.getPreviousSibling();
/*  479 */           Node parent = node.getParentNode();
/*  480 */           ((EntityReferenceImpl)node).setReadOnly(false, true);
/*  481 */           expandEntityRef(parent, node);
/*  482 */           parent.removeChild(node);
/*  483 */           Node node1 = (prevSibling != null) ? prevSibling.getNextSibling() : parent.getFirstChild();
/*      */ 
/*      */ 
/*      */           
/*  487 */           if (prevSibling != null && node1 != null && prevSibling.getNodeType() == 3 && node1
/*  488 */             .getNodeType() == 3) {
/*  489 */             return prevSibling;
/*      */           }
/*  491 */           return node1;
/*      */         } 
/*  493 */         if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0 && this.fDocument
/*  494 */           .isXMLVersionChanged()) {
/*  495 */           CoreDocumentImpl.isXMLName(node.getNodeName(), this.fDocument.isXML11Version());
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/*  508 */         if ((this.fConfiguration.features & 0x8) == 0) {
/*      */           
/*  510 */           Node prevSibling = node.getPreviousSibling();
/*  511 */           if (prevSibling != null && prevSibling.getNodeType() == 3) {
/*  512 */             ((Text)prevSibling).appendData(node.getNodeValue());
/*  513 */             node.getParentNode().removeChild(node);
/*  514 */             return prevSibling;
/*      */           } 
/*      */           
/*  517 */           Text text = this.fDocument.createTextNode(node.getNodeValue());
/*  518 */           Node parent = node.getParentNode();
/*  519 */           node = parent.replaceChild(text, node);
/*  520 */           return text;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  526 */         if (this.fValidationHandler != null) {
/*      */ 
/*      */           
/*  529 */           this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  530 */           this.fCurrentNode = node;
/*  531 */           this.fValidationHandler.startCDATA((Augmentations)null);
/*  532 */           this.fValidationHandler.characterData(node.getNodeValue(), (Augmentations)null);
/*  533 */           this.fValidationHandler.endCDATA((Augmentations)null);
/*      */         } 
/*      */         
/*  536 */         if (this.fDTDValidator != null) {
/*      */ 
/*      */           
/*  539 */           this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  540 */           this.fCurrentNode = node;
/*  541 */           this.fDTDValidator.startCDATA(null);
/*  542 */           this.fDTDValidator.characterData(node.getNodeValue(), null);
/*  543 */           this.fDTDValidator.endCDATA(null);
/*      */         } 
/*  545 */         value = node.getNodeValue();
/*      */         
/*  547 */         if ((this.fConfiguration.features & 0x10) != 0) {
/*      */           
/*  549 */           Node parent = node.getParentNode();
/*  550 */           if (this.fDocument.errorChecking)
/*  551 */             isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), this.fDocument.isXML11Version()); 
/*      */           int index;
/*  553 */           while ((index = value.indexOf("]]>")) >= 0) {
/*  554 */             node.setNodeValue(value.substring(0, index + 2));
/*  555 */             value = value.substring(index + 2);
/*      */             
/*  557 */             Node firstSplitNode = node;
/*  558 */             Node newChild = this.fDocument.createCDATASection(value);
/*  559 */             parent.insertBefore(newChild, node.getNextSibling());
/*  560 */             node = newChild;
/*      */             
/*  562 */             this.fLocator.fRelatedNode = firstSplitNode;
/*  563 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "cdata-sections-splitted", null);
/*      */ 
/*      */ 
/*      */             
/*  567 */             reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)1, "cdata-sections-splitted");
/*      */           } 
/*      */           
/*      */           break;
/*      */         } 
/*  572 */         if (this.fDocument.errorChecking)
/*      */         {
/*  574 */           isCDataWF(this.fErrorHandler, this.fError, this.fLocator, value, this.fDocument.isXML11Version());
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  588 */         next = node.getNextSibling();
/*      */         
/*  590 */         if (next != null && next.getNodeType() == 3) {
/*  591 */           ((Text)node).appendData(next.getNodeValue());
/*  592 */           node.getParentNode().removeChild(next);
/*      */ 
/*      */ 
/*      */           
/*  596 */           return node;
/*  597 */         }  if (node.getNodeValue().length() == 0) {
/*      */           
/*  599 */           node.getParentNode().removeChild(node);
/*      */ 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/*  607 */         nextType = (next != null) ? next.getNodeType() : -1;
/*  608 */         if (nextType == -1 || (((this.fConfiguration.features & 0x4) != 0 || nextType != 6) && ((this.fConfiguration.features & 0x20) != 0 || nextType != 8) && ((this.fConfiguration.features & 0x8) != 0 || nextType != 4))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  614 */           if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0) {
/*  615 */             isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), this.fDocument.isXML11Version());
/*      */           }
/*  617 */           if (this.fValidationHandler != null) {
/*  618 */             this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  619 */             this.fCurrentNode = node;
/*  620 */             this.fValidationHandler.characterData(node.getNodeValue(), (Augmentations)null);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  626 */           if (this.fDTDValidator != null) {
/*  627 */             this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
/*  628 */             this.fCurrentNode = node;
/*  629 */             this.fDTDValidator.characterData(node.getNodeValue(), null);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  634 */             if (this.allWhitespace) {
/*  635 */               this.allWhitespace = false;
/*  636 */               ((TextImpl)node).setIgnorableWhitespace(true);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/*  652 */         if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0) {
/*  653 */           boolean wellformed; ProcessingInstruction pinode = (ProcessingInstruction)node;
/*      */           
/*  655 */           String target = pinode.getTarget();
/*      */           
/*  657 */           if (this.fDocument.isXML11Version()) {
/*  658 */             wellformed = XML11Char.isXML11ValidName(target);
/*      */           } else {
/*      */             
/*  661 */             wellformed = XMLChar.isValidName(target);
/*      */           } 
/*      */           
/*  664 */           if (!wellformed) {
/*  665 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Element", node
/*      */ 
/*      */                   
/*  668 */                   .getNodeName() });
/*  669 */             reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "wf-invalid-character-in-node-name");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  676 */           isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, pinode.getData(), this.fDocument.isXML11Version());
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/*  681 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private XMLGrammarPool createGrammarPool(DocumentTypeImpl docType) {
/*  686 */     XMLGrammarPoolImpl pool = new XMLGrammarPoolImpl();
/*      */     
/*  688 */     XMLGrammarPreparser preParser = new XMLGrammarPreparser(this.fSymbolTable);
/*  689 */     preParser.registerPreparser("http://www.w3.org/TR/REC-xml", null);
/*  690 */     preParser.setFeature("http://apache.org/xml/features/namespaces", true);
/*  691 */     preParser.setFeature("http://apache.org/xml/features/validation", true);
/*  692 */     preParser.setProperty("http://apache.org/xml/properties/internal/grammar-pool", pool);
/*      */     
/*  694 */     String internalSubset = docType.getInternalSubset();
/*  695 */     XMLInputSource is = new XMLInputSource(docType.getPublicId(), docType.getSystemId(), null);
/*      */     
/*  697 */     if (internalSubset != null)
/*  698 */       is.setCharacterStream(new StringReader(internalSubset)); 
/*      */     
/*  700 */     try { DTDGrammar g = (DTDGrammar)preParser.preparseGrammar("http://www.w3.org/TR/REC-xml", is);
/*  701 */       ((XMLDTDDescription)g.getGrammarDescription()).setRootName(docType.getName());
/*  702 */       is.setCharacterStream(null);
/*  703 */       g = (DTDGrammar)preParser.preparseGrammar("http://www.w3.org/TR/REC-xml", is);
/*  704 */       ((XMLDTDDescription)g.getGrammarDescription()).setRootName(docType.getName()); }
/*      */     
/*  706 */     catch (XNIException xNIException) {  }
/*  707 */     catch (IOException iOException) {}
/*      */ 
/*      */     
/*  710 */     return pool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void expandEntityRef(Node parent, Node reference) {
/*  717 */     for (Node kid = reference.getFirstChild(); kid != null; kid = next) {
/*  718 */       Node next = kid.getNextSibling();
/*  719 */       parent.insertBefore(kid, reference);
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
/*      */   protected final void namespaceFixUp(ElementImpl element, AttributeMap attributes) {
/*  745 */     if (attributes != null)
/*      */     {
/*      */       
/*  748 */       for (int k = 0; k < attributes.getLength(); k++) {
/*  749 */         Attr attr = (Attr)attributes.getItem(k);
/*      */ 
/*      */ 
/*      */         
/*  753 */         if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0 && this.fDocument
/*  754 */           .isXMLVersionChanged())
/*      */         {
/*  756 */           this.fDocument.checkQName(attr.getPrefix(), attr.getLocalName());
/*      */         }
/*      */         
/*  759 */         String str = attr.getNamespaceURI();
/*  760 */         if (str != null && str.equals(NamespaceContext.XMLNS_URI))
/*      */         {
/*      */ 
/*      */           
/*  764 */           if ((this.fConfiguration.features & 0x200) != 0) {
/*      */ 
/*      */ 
/*      */             
/*  768 */             String value = attr.getNodeValue();
/*  769 */             if (value == null) {
/*  770 */               value = XMLSymbols.EMPTY_STRING;
/*      */             }
/*      */ 
/*      */             
/*  774 */             if (this.fDocument.errorChecking && value.equals(NamespaceContext.XMLNS_URI)) {
/*      */ 
/*      */               
/*  777 */               this.fLocator.fRelatedNode = attr;
/*  778 */               String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CantBindXMLNS", null);
/*  779 */               reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "CantBindXMLNS");
/*      */             }
/*      */             else {
/*      */               
/*  783 */               String str1 = attr.getPrefix();
/*      */               
/*  785 */               str1 = (str1 == null || str1.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(str1);
/*  786 */               String localpart = this.fSymbolTable.addSymbol(attr.getLocalName());
/*  787 */               if (str1 == XMLSymbols.PREFIX_XMLNS) {
/*      */                 
/*  789 */                 value = this.fSymbolTable.addSymbol(value);
/*  790 */                 if (value.length() != 0) {
/*  791 */                   this.fNamespaceContext.declarePrefix(localpart, value);
/*      */ 
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */               else {
/*      */ 
/*      */                 
/*  801 */                 value = this.fSymbolTable.addSymbol(value);
/*  802 */                 this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, value);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  828 */     String uri = element.getNamespaceURI();
/*  829 */     String prefix = element.getPrefix();
/*      */ 
/*      */     
/*  832 */     if ((this.fConfiguration.features & 0x200) == 0) {
/*      */       
/*  834 */       uri = null;
/*  835 */     } else if (uri != null) {
/*  836 */       uri = this.fSymbolTable.addSymbol(uri);
/*      */       
/*  838 */       prefix = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
/*  839 */       if (this.fNamespaceContext.getURI(prefix) != uri)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  846 */         addNamespaceDecl(prefix, uri, element);
/*  847 */         this.fLocalNSBinder.declarePrefix(prefix, uri);
/*  848 */         this.fNamespaceContext.declarePrefix(prefix, uri);
/*      */       }
/*      */     
/*  851 */     } else if (element.getLocalName() == null) {
/*      */ 
/*      */       
/*  854 */       if (this.fNamespaceValidation) {
/*  855 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalElementName", new Object[] { element
/*      */               
/*  857 */               .getNodeName() });
/*  858 */         reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)3, "NullLocalElementName");
/*      */       } else {
/*      */         
/*  861 */         String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalElementName", new Object[] { element
/*      */               
/*  863 */               .getNodeName() });
/*  864 */         reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "NullLocalElementName");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  869 */       uri = this.fNamespaceContext.getURI(XMLSymbols.EMPTY_STRING);
/*  870 */       if (uri != null && uri.length() > 0) {
/*      */ 
/*      */         
/*  873 */         addNamespaceDecl(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING, element);
/*  874 */         this.fLocalNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*  875 */         this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  884 */     if (attributes != null) {
/*      */ 
/*      */       
/*  887 */       attributes.cloneMap(this.fAttributeList);
/*  888 */       for (int i = 0; i < this.fAttributeList.size(); i++) {
/*  889 */         Attr attr = this.fAttributeList.get(i);
/*  890 */         this.fLocator.fRelatedNode = attr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  896 */         attr.normalize();
/*  897 */         String value = attr.getValue();
/*  898 */         String name = attr.getNodeName();
/*  899 */         uri = attr.getNamespaceURI();
/*      */ 
/*      */         
/*  902 */         if (value == null) {
/*  903 */           value = XMLSymbols.EMPTY_STRING;
/*      */         }
/*      */         
/*  906 */         if (uri != null) {
/*  907 */           prefix = attr.getPrefix();
/*      */           
/*  909 */           prefix = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
/*  910 */           this.fSymbolTable.addSymbol(attr.getLocalName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  917 */           if (uri == null || !uri.equals(NamespaceContext.XMLNS_URI))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  923 */             if (this.fDocument.errorChecking && (this.fConfiguration.features & 0x100) != 0) {
/*  924 */               isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, attributes, attr, attr.getValue(), this.fDocument.isXML11Version());
/*  925 */               if (this.fDocument.isXMLVersionChanged()) {
/*  926 */                 boolean wellformed = CoreDocumentImpl.isXMLName(attr.getNodeName(), this.fDocument.isXML11Version());
/*  927 */                 if (!wellformed) {
/*  928 */                   String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Attribute", attr
/*      */ 
/*      */                         
/*  931 */                         .getNodeName() });
/*  932 */                   reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "wf-invalid-character-in-node-name");
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  950 */             ((AttrImpl)attr).setIdAttribute(false);
/*      */ 
/*      */             
/*  953 */             uri = this.fSymbolTable.addSymbol(uri);
/*      */ 
/*      */             
/*  956 */             String declaredURI = this.fNamespaceContext.getURI(prefix);
/*      */             
/*  958 */             if (prefix == XMLSymbols.EMPTY_STRING || declaredURI != uri)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  966 */               name = attr.getNodeName();
/*      */ 
/*      */               
/*  969 */               String declaredPrefix = this.fNamespaceContext.getPrefix(uri);
/*  970 */               if (declaredPrefix != null && declaredPrefix != XMLSymbols.EMPTY_STRING) {
/*      */ 
/*      */                 
/*  973 */                 prefix = declaredPrefix;
/*      */               } else {
/*  975 */                 if (prefix == XMLSymbols.EMPTY_STRING || this.fLocalNSBinder.getURI(prefix) != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*  983 */                   int counter = 1;
/*  984 */                   prefix = this.fSymbolTable.addSymbol("NS" + counter++);
/*  985 */                   while (this.fLocalNSBinder.getURI(prefix) != null) {
/*  986 */                     prefix = this.fSymbolTable.addSymbol("NS" + counter++);
/*      */                   }
/*      */                 } 
/*      */ 
/*      */                 
/*  991 */                 addNamespaceDecl(prefix, uri, element);
/*  992 */                 value = this.fSymbolTable.addSymbol(value);
/*  993 */                 this.fLocalNSBinder.declarePrefix(prefix, value);
/*  994 */                 this.fNamespaceContext.declarePrefix(prefix, uri);
/*      */               } 
/*      */ 
/*      */               
/*  998 */               attr.setPrefix(prefix);
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1006 */           ((AttrImpl)attr).setIdAttribute(false);
/*      */           
/* 1008 */           if (attr.getLocalName() == null)
/*      */           {
/* 1010 */             if (this.fNamespaceValidation) {
/* 1011 */               String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalAttrName", new Object[] { attr
/*      */                     
/* 1013 */                     .getNodeName() });
/* 1014 */               reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)3, "NullLocalAttrName");
/*      */             } else {
/*      */               
/* 1017 */               String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalAttrName", new Object[] { attr
/*      */                     
/* 1019 */                     .getNodeName() });
/* 1020 */               reportDOMError(this.fErrorHandler, this.fError, this.fLocator, msg, (short)2, "NullLocalAttrName");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void addNamespaceDecl(String prefix, String uri, ElementImpl element) {
/* 1051 */     if (prefix == XMLSymbols.EMPTY_STRING) {
/*      */ 
/*      */ 
/*      */       
/* 1055 */       element.setAttributeNS(NamespaceContext.XMLNS_URI, XMLSymbols.PREFIX_XMLNS, uri);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1060 */       element.setAttributeNS(NamespaceContext.XMLNS_URI, "xmlns:" + prefix, uri);
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
/*      */   public static final void isCDataWF(DOMErrorHandler errorHandler, DOMErrorImpl error, DOMLocatorImpl locator, String datavalue, boolean isXML11Version) {
/* 1078 */     if (datavalue == null || datavalue.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1082 */     char[] dataarray = datavalue.toCharArray();
/* 1083 */     int datalength = dataarray.length;
/*      */ 
/*      */     
/* 1086 */     if (isXML11Version) {
/*      */       
/* 1088 */       int i = 0;
/* 1089 */       while (i < datalength) {
/* 1090 */         char c = dataarray[i++];
/* 1091 */         if (XML11Char.isXML11Invalid(c)) {
/*      */           
/* 1093 */           if (XMLChar.isHighSurrogate(c) && i < datalength) {
/* 1094 */             char c2 = dataarray[i++];
/* 1095 */             if (XMLChar.isLowSurrogate(c2) && 
/* 1096 */               XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 1100 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect", new Object[] {
/*      */ 
/*      */                 
/* 1103 */                 Integer.toString(c, 16) });
/* 1104 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/* 1112 */         if (c == ']') {
/* 1113 */           int count = i;
/* 1114 */           if (count < datalength && dataarray[count] == ']') {
/* 1115 */             while (++count < datalength && dataarray[count] == ']');
/*      */ 
/*      */             
/* 1118 */             if (count < datalength && dataarray[count] == '>')
/*      */             {
/* 1120 */               String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent", null);
/* 1121 */               reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1130 */       int i = 0;
/* 1131 */       while (i < datalength) {
/* 1132 */         char c = dataarray[i++];
/* 1133 */         if (XMLChar.isInvalid(c)) {
/*      */           
/* 1135 */           if (XMLChar.isHighSurrogate(c) && i < datalength) {
/* 1136 */             char c2 = dataarray[i++];
/* 1137 */             if (XMLChar.isLowSurrogate(c2) && 
/* 1138 */               XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1146 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect", new Object[] {
/*      */ 
/*      */                 
/* 1149 */                 Integer.toString(c, 16) });
/* 1150 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character"); continue;
/*      */         } 
/* 1152 */         if (c == ']') {
/* 1153 */           int count = i;
/* 1154 */           if (count < datalength && dataarray[count] == ']') {
/* 1155 */             while (++count < datalength && dataarray[count] == ']');
/*      */ 
/*      */             
/* 1158 */             if (count < datalength && dataarray[count] == '>') {
/* 1159 */               String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent", null);
/* 1160 */               reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void isXMLCharWF(DOMErrorHandler errorHandler, DOMErrorImpl error, DOMLocatorImpl locator, String datavalue, boolean isXML11Version) {
/* 1178 */     if (datavalue == null || datavalue.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1182 */     char[] dataarray = datavalue.toCharArray();
/* 1183 */     int datalength = dataarray.length;
/*      */ 
/*      */     
/* 1186 */     if (isXML11Version) {
/*      */       
/* 1188 */       int i = 0;
/* 1189 */       while (i < datalength) {
/* 1190 */         if (XML11Char.isXML11Invalid(dataarray[i++]))
/*      */         {
/* 1192 */           char ch = dataarray[i - 1];
/* 1193 */           if (XMLChar.isHighSurrogate(ch) && i < datalength) {
/* 1194 */             char ch2 = dataarray[i++];
/* 1195 */             if (XMLChar.isLowSurrogate(ch2) && 
/* 1196 */               XMLChar.isSupplemental(XMLChar.supplemental(ch, ch2))) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 1200 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "InvalidXMLCharInDOM", new Object[] {
/*      */                 
/* 1202 */                 Integer.toString(dataarray[i - 1], 16) });
/* 1203 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1210 */       int i = 0;
/* 1211 */       while (i < datalength) {
/* 1212 */         if (XMLChar.isInvalid(dataarray[i++])) {
/*      */           
/* 1214 */           char ch = dataarray[i - 1];
/* 1215 */           if (XMLChar.isHighSurrogate(ch) && i < datalength) {
/* 1216 */             char ch2 = dataarray[i++];
/* 1217 */             if (XMLChar.isLowSurrogate(ch2) && 
/* 1218 */               XMLChar.isSupplemental(XMLChar.supplemental(ch, ch2))) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 1222 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "InvalidXMLCharInDOM", new Object[] {
/*      */                 
/* 1224 */                 Integer.toString(dataarray[i - 1], 16) });
/* 1225 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
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
/*      */   public static final void isCommentWF(DOMErrorHandler errorHandler, DOMErrorImpl error, DOMLocatorImpl locator, String datavalue, boolean isXML11Version) {
/* 1241 */     if (datavalue == null || datavalue.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1245 */     char[] dataarray = datavalue.toCharArray();
/* 1246 */     int datalength = dataarray.length;
/*      */ 
/*      */     
/* 1249 */     if (isXML11Version) {
/*      */       
/* 1251 */       int i = 0;
/* 1252 */       while (i < datalength) {
/* 1253 */         char c = dataarray[i++];
/* 1254 */         if (XML11Char.isXML11Invalid(c)) {
/*      */           
/* 1256 */           if (XMLChar.isHighSurrogate(c) && i < datalength) {
/* 1257 */             char c2 = dataarray[i++];
/* 1258 */             if (XMLChar.isLowSurrogate(c2) && 
/* 1259 */               XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 1263 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInComment", new Object[] {
/*      */                 
/* 1265 */                 Integer.toString(dataarray[i - 1], 16) });
/* 1266 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character"); continue;
/*      */         } 
/* 1268 */         if (c == '-' && i < datalength && dataarray[i] == '-') {
/* 1269 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "DashDashInComment", null);
/*      */ 
/*      */           
/* 1272 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1278 */       int i = 0;
/* 1279 */       while (i < datalength) {
/* 1280 */         char c = dataarray[i++];
/* 1281 */         if (XMLChar.isInvalid(c)) {
/*      */           
/* 1283 */           if (XMLChar.isHighSurrogate(c) && i < datalength) {
/* 1284 */             char c2 = dataarray[i++];
/* 1285 */             if (XMLChar.isLowSurrogate(c2) && 
/* 1286 */               XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
/*      */               continue;
/*      */             }
/*      */           } 
/* 1290 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInComment", new Object[] {
/* 1291 */                 Integer.toString(dataarray[i - 1], 16) });
/* 1292 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character"); continue;
/*      */         } 
/* 1294 */         if (c == '-' && i < datalength && dataarray[i] == '-') {
/* 1295 */           String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "DashDashInComment", null);
/*      */ 
/*      */           
/* 1298 */           reportDOMError(errorHandler, error, locator, msg, (short)2, "wf-invalid-character");
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
/*      */   public static final void isAttrValueWF(DOMErrorHandler errorHandler, DOMErrorImpl error, DOMLocatorImpl locator, NamedNodeMap attributes, Attr a, String value, boolean xml11Version) {
/* 1313 */     if (a instanceof AttrImpl && ((AttrImpl)a).hasStringValue()) {
/* 1314 */       isXMLCharWF(errorHandler, error, locator, value, xml11Version);
/*      */     } else {
/* 1316 */       NodeList children = a.getChildNodes();
/*      */       
/* 1318 */       for (int j = 0; j < children.getLength(); j++) {
/* 1319 */         Node child = children.item(j);
/*      */         
/* 1321 */         if (child.getNodeType() == 5) {
/* 1322 */           Document owner = a.getOwnerDocument();
/* 1323 */           Entity ent = null;
/*      */ 
/*      */           
/* 1326 */           if (owner != null) {
/* 1327 */             DocumentType docType = owner.getDoctype();
/* 1328 */             if (docType != null) {
/* 1329 */               NamedNodeMap entities = docType.getEntities();
/* 1330 */               ent = (Entity)entities.getNamedItemNS("*", child
/*      */                   
/* 1332 */                   .getNodeName());
/*      */             } 
/*      */           } 
/*      */           
/* 1336 */           if (ent == null) {
/* 1337 */             String msg = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "UndeclaredEntRefInAttrValue", new Object[] { a
/*      */                   
/* 1339 */                   .getNodeName() });
/* 1340 */             reportDOMError(errorHandler, error, locator, msg, (short)2, "UndeclaredEntRefInAttrValue");
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1346 */           isXMLCharWF(errorHandler, error, locator, child.getNodeValue(), xml11Version);
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
/*      */   public static final void reportDOMError(DOMErrorHandler errorHandler, DOMErrorImpl error, DOMLocatorImpl locator, String message, short severity, String type) {
/* 1361 */     if (errorHandler != null) {
/* 1362 */       error.reset();
/* 1363 */       error.fMessage = message;
/* 1364 */       error.fSeverity = severity;
/* 1365 */       error.fLocator = locator;
/* 1366 */       error.fType = type;
/* 1367 */       error.fRelatedData = locator.fRelatedNode;
/*      */       
/* 1369 */       if (!errorHandler.handleError(error))
/* 1370 */         throw new AbortException(); 
/*      */     } 
/* 1372 */     if (severity == 3) {
/* 1373 */       throw new AbortException();
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void updateQName(Node node, QName qname) {
/* 1378 */     String prefix = node.getPrefix();
/* 1379 */     String namespace = node.getNamespaceURI();
/* 1380 */     String localName = node.getLocalName();
/*      */ 
/*      */     
/* 1383 */     qname.prefix = (prefix != null && prefix.length() != 0) ? this.fSymbolTable.addSymbol(prefix) : null;
/* 1384 */     qname.localpart = (localName != null) ? this.fSymbolTable.addSymbol(localName) : null;
/* 1385 */     qname.rawname = this.fSymbolTable.addSymbol(node.getNodeName());
/* 1386 */     qname.uri = (namespace != null) ? this.fSymbolTable.addSymbol(namespace) : null;
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
/*      */   final String normalizeAttributeValue(String value, Attr attr) {
/* 1410 */     if (!attr.getSpecified())
/*      */     {
/*      */       
/* 1413 */       return value;
/*      */     }
/* 1415 */     int end = value.length();
/*      */     
/* 1417 */     if (this.fNormalizedValue.ch.length < end) {
/* 1418 */       this.fNormalizedValue.ch = new char[end];
/*      */     }
/* 1420 */     this.fNormalizedValue.length = 0;
/* 1421 */     boolean normalized = false;
/* 1422 */     for (int i = 0; i < end; i++) {
/* 1423 */       char c = value.charAt(i);
/* 1424 */       if (c == '\t' || c == '\n') {
/* 1425 */         this.fNormalizedValue.ch[this.fNormalizedValue.length++] = ' ';
/* 1426 */         normalized = true;
/*      */       }
/* 1428 */       else if (c == '\r') {
/* 1429 */         normalized = true;
/* 1430 */         this.fNormalizedValue.ch[this.fNormalizedValue.length++] = ' ';
/* 1431 */         int next = i + 1;
/* 1432 */         if (next < end && value.charAt(next) == '\n') i = next;
/*      */       
/*      */       } else {
/* 1435 */         this.fNormalizedValue.ch[this.fNormalizedValue.length++] = c;
/*      */       } 
/*      */     } 
/* 1438 */     if (normalized) {
/* 1439 */       value = this.fNormalizedValue.toString();
/* 1440 */       attr.setValue(value);
/*      */     } 
/* 1442 */     return value;
/*      */   }
/*      */   
/*      */   protected final class XMLAttributesProxy
/*      */     implements XMLAttributes
/*      */   {
/*      */     protected AttributeMap fAttributes;
/*      */     protected CoreDocumentImpl fDocument;
/*      */     protected ElementImpl fElement;
/* 1451 */     protected final Vector fAugmentations = new Vector(5);
/*      */ 
/*      */     
/*      */     public void setAttributes(AttributeMap attributes, CoreDocumentImpl doc, ElementImpl elem) {
/* 1455 */       this.fDocument = doc;
/* 1456 */       this.fAttributes = attributes;
/* 1457 */       this.fElement = elem;
/* 1458 */       if (attributes != null) {
/* 1459 */         int length = attributes.getLength();
/*      */         
/* 1461 */         this.fAugmentations.setSize(length);
/*      */ 
/*      */ 
/*      */         
/* 1465 */         for (int i = 0; i < length; i++) {
/* 1466 */           this.fAugmentations.setElementAt(new AugmentationsImpl(), i);
/*      */         }
/*      */       } else {
/* 1469 */         this.fAugmentations.setSize(0);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int addAttribute(QName qname, String attrType, String attrValue) {
/* 1479 */       int index = this.fElement.getXercesAttribute(qname.uri, qname.localpart);
/*      */       
/* 1481 */       if (index < 0) {
/*      */ 
/*      */ 
/*      */         
/* 1485 */         AttrImpl attr = (AttrImpl)((CoreDocumentImpl)this.fElement.getOwnerDocument()).createAttributeNS(qname.uri, qname.rawname, qname.localpart);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1490 */         attr.setNodeValue(attrValue);
/* 1491 */         index = this.fElement.setXercesAttributeNode(attr);
/* 1492 */         this.fAugmentations.insertElementAt(new AugmentationsImpl(), index);
/* 1493 */         attr.setSpecified(false);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1503 */       return index;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAllAttributes() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAttributeAt(int attrIndex) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLength() {
/* 1518 */       return (this.fAttributes != null) ? this.fAttributes.getLength() : 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getIndex(String qName) {
/* 1524 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getIndex(String uri, String localPart) {
/* 1529 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setName(int attrIndex, QName attrName) {}
/*      */ 
/*      */     
/*      */     public void getName(int attrIndex, QName attrName) {
/* 1537 */       if (this.fAttributes != null) {
/* 1538 */         DOMNormalizer.this.updateQName((Node)this.fAttributes.getItem(attrIndex), attrName);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public String getPrefix(int index) {
/* 1544 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getURI(int index) {
/* 1550 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getLocalName(int index) {
/* 1556 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getQName(int index) {
/* 1562 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public QName getQualifiedName(int index) {
/* 1567 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setType(int attrIndex, String attrType) {}
/*      */ 
/*      */     
/*      */     public String getType(int index) {
/* 1576 */       return "CDATA";
/*      */     }
/*      */ 
/*      */     
/*      */     public String getType(String qName) {
/* 1581 */       return "CDATA";
/*      */     }
/*      */ 
/*      */     
/*      */     public String getType(String uri, String localName) {
/* 1586 */       return "CDATA";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValue(int attrIndex, String attrValue) {
/* 1595 */       if (this.fAttributes != null) {
/* 1596 */         AttrImpl attr = (AttrImpl)this.fAttributes.getItem(attrIndex);
/* 1597 */         boolean specified = attr.getSpecified();
/* 1598 */         attr.setValue(attrValue);
/* 1599 */         attr.setSpecified(specified);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValue(int attrIndex, String attrValue, XMLString value) {
/* 1605 */       setValue(attrIndex, value.toString());
/*      */     }
/*      */     
/*      */     public String getValue(int index) {
/* 1609 */       return (this.fAttributes != null) ? this.fAttributes.item(index).getNodeValue() : "";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getValue(String qName) {
/* 1616 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getValue(String uri, String localName) {
/* 1621 */       if (this.fAttributes != null) {
/* 1622 */         Node node = this.fAttributes.getNamedItemNS(uri, localName);
/* 1623 */         return (node != null) ? node.getNodeValue() : null;
/*      */       } 
/* 1625 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setNonNormalizedValue(int attrIndex, String attrValue) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getNonNormalizedValue(int attrIndex) {
/* 1637 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setSpecified(int attrIndex, boolean specified) {
/* 1642 */       AttrImpl attr = (AttrImpl)this.fAttributes.getItem(attrIndex);
/* 1643 */       attr.setSpecified(specified);
/*      */     }
/*      */     
/*      */     public boolean isSpecified(int attrIndex) {
/* 1647 */       return ((Attr)this.fAttributes.getItem(attrIndex)).getSpecified();
/*      */     }
/*      */     
/*      */     public Augmentations getAugmentations(int attributeIndex) {
/* 1651 */       return this.fAugmentations.elementAt(attributeIndex);
/*      */     }
/*      */ 
/*      */     
/*      */     public Augmentations getAugmentations(String uri, String localPart) {
/* 1656 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public Augmentations getAugmentations(String qName) {
/* 1661 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAugmentations(int attrIndex, Augmentations augs) {
/* 1671 */       this.fAugmentations.setElementAt(augs, attrIndex);
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
/*      */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doctypeDecl(String rootElement, String publicId, String systemId, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void comment(XMLString text, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 1794 */     Element currentElement = (Element)this.fCurrentNode;
/* 1795 */     int attrCount = attributes.getLength();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1801 */     for (int i = 0; i < attrCount; i++) {
/* 1802 */       attributes.getName(i, this.fAttrQName);
/* 1803 */       Attr attr = null;
/*      */       
/* 1805 */       attr = currentElement.getAttributeNodeNS(this.fAttrQName.uri, this.fAttrQName.localpart);
/*      */       
/* 1807 */       AttributePSVI attrPSVI = (AttributePSVI)attributes.getAugmentations(i).getItem("ATTRIBUTE_PSVI");
/*      */       
/* 1809 */       if (attrPSVI != null) {
/*      */ 
/*      */         
/* 1812 */         XSTypeDefinition decl = attrPSVI.getMemberTypeDefinition();
/* 1813 */         boolean id = false;
/* 1814 */         if (decl != null) {
/* 1815 */           id = ((XSSimpleType)decl).isIDType();
/*      */         } else {
/* 1817 */           decl = attrPSVI.getTypeDefinition();
/* 1818 */           if (decl != null) {
/* 1819 */             id = ((XSSimpleType)decl).isIDType();
/*      */           }
/*      */         } 
/* 1822 */         if (id) {
/* 1823 */           ((ElementImpl)currentElement).setIdAttributeNode(attr, true);
/*      */         }
/*      */         
/* 1826 */         if (this.fPSVI) {
/* 1827 */           ((PSVIAttrNSImpl)attr).setPSVI(attrPSVI);
/*      */         }
/* 1829 */         if ((this.fConfiguration.features & 0x2) != 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1835 */           boolean specified = attr.getSpecified();
/* 1836 */           attr.setValue(attrPSVI.getSchemaNormalizedValue());
/* 1837 */           if (!specified) {
/* 1838 */             ((AttrImpl)attr).setSpecified(specified);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
/* 1862 */     startElement(element, attributes, augs);
/* 1863 */     endElement(element, augs);
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
/*      */   public void startGeneralEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endGeneralEntity(String name, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void characters(XMLString text, Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
/* 1952 */     this.allWhitespace = true;
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
/*      */   public void endElement(QName element, Augmentations augs) throws XNIException {
/* 1969 */     if (augs != null) {
/* 1970 */       ElementPSVI elementPSVI = (ElementPSVI)augs.getItem("ELEMENT_PSVI");
/* 1971 */       if (elementPSVI != null) {
/* 1972 */         ElementImpl elementNode = (ElementImpl)this.fCurrentNode;
/* 1973 */         if (this.fPSVI) {
/* 1974 */           ((PSVIElementNSImpl)this.fCurrentNode).setPSVI(elementPSVI);
/*      */         }
/*      */         
/* 1977 */         String normalizedValue = elementPSVI.getSchemaNormalizedValue();
/* 1978 */         if ((this.fConfiguration.features & 0x2) != 0) {
/* 1979 */           if (normalizedValue != null) {
/* 1980 */             elementNode.setTextContent(normalizedValue);
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1986 */           String text = elementNode.getTextContent();
/* 1987 */           if (text.length() == 0)
/*      */           {
/* 1989 */             if (normalizedValue != null) {
/* 1990 */               elementNode.setTextContent(normalizedValue);
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
/*      */   
/*      */   public void startCDATA(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endCDATA(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endDocument(Augmentations augs) throws XNIException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDocumentSource(XMLDocumentSource source) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLDocumentSource getDocumentSource() {
/* 2039 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/dom/DOMNormalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */