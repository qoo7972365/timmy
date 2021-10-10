/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.AttList;
/*     */ import com.sun.org.apache.xml.internal.utils.DOM2Helper;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.EntityReference;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.LocatorImpl;
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
/*     */ public final class TreeWalker
/*     */ {
/*     */   private final ContentHandler m_contentHandler;
/*     */   private final SerializationHandler m_Serializer;
/*  60 */   private final LocatorImpl m_locator = new LocatorImpl();
/*     */ 
/*     */ 
/*     */   
/*     */   boolean nextIsRaw;
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentHandler getContentHandler() {
/*  69 */     return this.m_contentHandler;
/*     */   }
/*     */   
/*     */   public TreeWalker(ContentHandler ch) {
/*  73 */     this(ch, null);
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
/*     */   public void traverse(Node pos) throws SAXException {
/* 112 */     this.m_contentHandler.startDocument();
/*     */     
/* 114 */     Node top = pos;
/*     */     
/* 116 */     while (null != pos) {
/*     */       
/* 118 */       startNode(pos);
/*     */       
/* 120 */       Node nextNode = pos.getFirstChild();
/*     */       
/* 122 */       while (null == nextNode) {
/*     */         
/* 124 */         endNode(pos);
/*     */         
/* 126 */         if (top.equals(pos)) {
/*     */           break;
/*     */         }
/* 129 */         nextNode = pos.getNextSibling();
/*     */         
/* 131 */         if (null == nextNode) {
/*     */           
/* 133 */           pos = pos.getParentNode();
/*     */           
/* 135 */           if (null == pos || top.equals(pos)) {
/*     */             
/* 137 */             if (null != pos) {
/* 138 */               endNode(pos);
/*     */             }
/* 140 */             nextNode = null;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       pos = nextNode;
/*     */     } 
/* 149 */     this.m_contentHandler.endDocument();
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
/*     */   public void traverse(Node pos, Node top) throws SAXException {
/* 168 */     this.m_contentHandler.startDocument();
/*     */     
/* 170 */     while (null != pos) {
/*     */       
/* 172 */       startNode(pos);
/*     */       
/* 174 */       Node nextNode = pos.getFirstChild();
/*     */       
/* 176 */       while (null == nextNode) {
/*     */         
/* 178 */         endNode(pos);
/*     */         
/* 180 */         if (null != top && top.equals(pos)) {
/*     */           break;
/*     */         }
/* 183 */         nextNode = pos.getNextSibling();
/*     */         
/* 185 */         if (null == nextNode) {
/*     */           
/* 187 */           pos = pos.getParentNode();
/*     */           
/* 189 */           if (null == pos || (null != top && top.equals(pos))) {
/*     */             
/* 191 */             nextNode = null;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 198 */       pos = nextNode;
/*     */     } 
/* 200 */     this.m_contentHandler.endDocument();
/*     */   }
/*     */   
/*     */   public TreeWalker(ContentHandler contentHandler, String systemId) {
/* 204 */     this.nextIsRaw = false; this.m_contentHandler = contentHandler;
/*     */     if (this.m_contentHandler instanceof SerializationHandler) {
/*     */       this.m_Serializer = (SerializationHandler)this.m_contentHandler;
/*     */     } else {
/*     */       this.m_Serializer = null;
/*     */     } 
/*     */     this.m_contentHandler.setDocumentLocator(this.m_locator);
/*     */     if (systemId != null)
/* 212 */       this.m_locator.setSystemId(systemId);  } private final void dispatachChars(Node node) throws SAXException { if (this.m_Serializer != null) {
/*     */       
/* 214 */       this.m_Serializer.characters(node);
/*     */     }
/*     */     else {
/*     */       
/* 218 */       String data = ((Text)node).getData();
/* 219 */       this.m_contentHandler.characters(data.toCharArray(), 0, data.length());
/*     */     }  }
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
/*     */   protected void startNode(Node node) throws SAXException {
/*     */     String data;
/*     */     Element elem_node;
/*     */     String uri;
/*     */     NamedNodeMap atts;
/*     */     int nAttrs;
/*     */     int i;
/*     */     String ns;
/*     */     ProcessingInstruction pi;
/*     */     boolean isLexH;
/*     */     EntityReference eref;
/*     */     String name;
/*     */     LexicalHandler lh;
/* 244 */     if (node instanceof Locator) {
/*     */       
/* 246 */       Locator loc = (Locator)node;
/* 247 */       this.m_locator.setColumnNumber(loc.getColumnNumber());
/* 248 */       this.m_locator.setLineNumber(loc.getLineNumber());
/* 249 */       this.m_locator.setPublicId(loc.getPublicId());
/* 250 */       this.m_locator.setSystemId(loc.getSystemId());
/*     */     }
/*     */     else {
/*     */       
/* 254 */       this.m_locator.setColumnNumber(0);
/* 255 */       this.m_locator.setLineNumber(0);
/*     */     } 
/*     */     
/* 258 */     switch (node.getNodeType()) {
/*     */ 
/*     */       
/*     */       case 8:
/* 262 */         data = ((Comment)node).getData();
/*     */         
/* 264 */         if (this.m_contentHandler instanceof LexicalHandler) {
/*     */           
/* 266 */           LexicalHandler lexicalHandler = (LexicalHandler)this.m_contentHandler;
/*     */           
/* 268 */           lexicalHandler.comment(data.toCharArray(), 0, data.length());
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 280 */         elem_node = (Element)node;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 285 */         uri = elem_node.getNamespaceURI();
/* 286 */         if (uri != null) {
/* 287 */           String prefix = elem_node.getPrefix();
/* 288 */           if (prefix == null)
/* 289 */             prefix = ""; 
/* 290 */           this.m_contentHandler.startPrefixMapping(prefix, uri);
/*     */         } 
/*     */         
/* 293 */         atts = elem_node.getAttributes();
/* 294 */         nAttrs = atts.getLength();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 300 */         for (i = 0; i < nAttrs; i++) {
/*     */           
/* 302 */           Node attr = atts.item(i);
/* 303 */           String attrName = attr.getNodeName();
/* 304 */           int colon = attrName.indexOf(':');
/*     */ 
/*     */           
/* 307 */           if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) {
/*     */             String prefix;
/*     */ 
/*     */ 
/*     */             
/* 312 */             if (colon < 0) {
/* 313 */               prefix = "";
/*     */             } else {
/* 315 */               prefix = attrName.substring(colon + 1);
/*     */             } 
/* 317 */             this.m_contentHandler.startPrefixMapping(prefix, attr
/* 318 */                 .getNodeValue());
/*     */           }
/* 320 */           else if (colon > 0) {
/* 321 */             String prefix = attrName.substring(0, colon);
/* 322 */             String str1 = attr.getNamespaceURI();
/* 323 */             if (str1 != null) {
/* 324 */               this.m_contentHandler.startPrefixMapping(prefix, str1);
/*     */             }
/*     */           } 
/*     */         } 
/* 328 */         ns = DOM2Helper.getNamespaceOfNode(node);
/* 329 */         if (null == ns)
/* 330 */           ns = ""; 
/* 331 */         this.m_contentHandler.startElement(ns, 
/* 332 */             DOM2Helper.getLocalNameOfNode(node), node
/* 333 */             .getNodeName(), new AttList(atts));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 338 */         pi = (ProcessingInstruction)node;
/* 339 */         name = pi.getNodeName();
/*     */ 
/*     */         
/* 342 */         if (name.equals("xslt-next-is-raw")) {
/*     */           
/* 344 */           this.nextIsRaw = true;
/*     */           
/*     */           break;
/*     */         } 
/* 348 */         this.m_contentHandler.processingInstruction(pi.getNodeName(), pi
/* 349 */             .getData());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 355 */         isLexH = this.m_contentHandler instanceof LexicalHandler;
/* 356 */         lh = isLexH ? (LexicalHandler)this.m_contentHandler : null;
/*     */ 
/*     */         
/* 359 */         if (isLexH)
/*     */         {
/* 361 */           lh.startCDATA();
/*     */         }
/*     */         
/* 364 */         dispatachChars(node);
/*     */ 
/*     */         
/* 367 */         if (isLexH)
/*     */         {
/* 369 */           lh.endCDATA();
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 378 */         if (this.nextIsRaw) {
/*     */           
/* 380 */           this.nextIsRaw = false;
/*     */           
/* 382 */           this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/* 383 */           dispatachChars(node);
/* 384 */           this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*     */           
/*     */           break;
/*     */         } 
/* 388 */         dispatachChars(node);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 394 */         eref = (EntityReference)node;
/*     */         
/* 396 */         if (this.m_contentHandler instanceof LexicalHandler)
/*     */         {
/* 398 */           ((LexicalHandler)this.m_contentHandler).startEntity(eref
/* 399 */               .getNodeName());
/*     */         }
/*     */         break;
/*     */     } 
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
/*     */   protected void endNode(Node node) throws SAXException {
/*     */     String ns;
/*     */     EntityReference eref;
/* 423 */     switch (node.getNodeType()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 429 */         ns = DOM2Helper.getNamespaceOfNode(node);
/* 430 */         if (null == ns)
/* 431 */           ns = ""; 
/* 432 */         this.m_contentHandler.endElement(ns, 
/* 433 */             DOM2Helper.getLocalNameOfNode(node), node
/* 434 */             .getNodeName());
/*     */         
/* 436 */         if (this.m_Serializer == null) {
/*     */ 
/*     */ 
/*     */           
/* 440 */           Element elem_node = (Element)node;
/* 441 */           NamedNodeMap atts = elem_node.getAttributes();
/* 442 */           int nAttrs = atts.getLength();
/*     */ 
/*     */ 
/*     */           
/* 446 */           for (int i = nAttrs - 1; 0 <= i; i--) {
/*     */             
/* 448 */             Node attr = atts.item(i);
/* 449 */             String attrName = attr.getNodeName();
/* 450 */             int colon = attrName.indexOf(':');
/*     */ 
/*     */             
/* 453 */             if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) {
/*     */               String prefix;
/*     */ 
/*     */ 
/*     */               
/* 458 */               if (colon < 0) {
/* 459 */                 prefix = "";
/*     */               } else {
/* 461 */                 prefix = attrName.substring(colon + 1);
/*     */               } 
/* 463 */               this.m_contentHandler.endPrefixMapping(prefix);
/*     */             }
/* 465 */             else if (colon > 0) {
/* 466 */               String prefix = attrName.substring(0, colon);
/* 467 */               this.m_contentHandler.endPrefixMapping(prefix);
/*     */             } 
/*     */           } 
/*     */           
/* 471 */           String uri = elem_node.getNamespaceURI();
/* 472 */           if (uri != null) {
/* 473 */             String prefix = elem_node.getPrefix();
/* 474 */             if (prefix == null)
/* 475 */               prefix = ""; 
/* 476 */             this.m_contentHandler.endPrefixMapping(prefix);
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 485 */         eref = (EntityReference)node;
/*     */         
/* 487 */         if (this.m_contentHandler instanceof LexicalHandler) {
/*     */           
/* 489 */           LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/*     */           
/* 491 */           lh.endEntity(eref.getNodeName());
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/TreeWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */