/*     */ package com.sun.org.apache.xml.internal.utils;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.dtm.ref.dom2dtm.DOM2DTM;
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
/*     */ public class TreeWalker
/*     */ {
/*  48 */   private ContentHandler m_contentHandler = null;
/*     */ 
/*     */   
/*  51 */   private LocatorImpl m_locator = new LocatorImpl();
/*     */ 
/*     */ 
/*     */   
/*     */   boolean nextIsRaw;
/*     */ 
/*     */ 
/*     */   
/*     */   public ContentHandler getContentHandler() {
/*  60 */     return this.m_contentHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentHandler(ContentHandler ch) {
/*  70 */     this.m_contentHandler = ch;
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
/*     */   public TreeWalker(ContentHandler contentHandler) {
/*  97 */     this(contentHandler, null);
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
/*     */   public void traverse(Node pos) throws SAXException {
/* 114 */     this.m_contentHandler.startDocument();
/*     */     
/* 116 */     traverseFragment(pos);
/*     */     
/* 118 */     this.m_contentHandler.endDocument();
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
/*     */   public void traverseFragment(Node pos) throws SAXException {
/* 133 */     Node top = pos;
/*     */     
/* 135 */     while (null != pos) {
/*     */       
/* 137 */       startNode(pos);
/*     */       
/* 139 */       Node nextNode = pos.getFirstChild();
/*     */       
/* 141 */       while (null == nextNode) {
/*     */         
/* 143 */         endNode(pos);
/*     */         
/* 145 */         if (top.equals(pos)) {
/*     */           break;
/*     */         }
/* 148 */         nextNode = pos.getNextSibling();
/*     */         
/* 150 */         if (null == nextNode) {
/*     */           
/* 152 */           pos = pos.getParentNode();
/*     */           
/* 154 */           if (null == pos || top.equals(pos)) {
/*     */             
/* 156 */             if (null != pos) {
/* 157 */               endNode(pos);
/*     */             }
/* 159 */             nextNode = null;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 166 */       pos = nextNode;
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
/*     */   public void traverse(Node pos, Node top) throws SAXException {
/* 186 */     this.m_contentHandler.startDocument();
/*     */     
/* 188 */     while (null != pos) {
/*     */       
/* 190 */       startNode(pos);
/*     */       
/* 192 */       Node nextNode = pos.getFirstChild();
/*     */       
/* 194 */       while (null == nextNode) {
/*     */         
/* 196 */         endNode(pos);
/*     */         
/* 198 */         if (null != top && top.equals(pos)) {
/*     */           break;
/*     */         }
/* 201 */         nextNode = pos.getNextSibling();
/*     */         
/* 203 */         if (null == nextNode) {
/*     */           
/* 205 */           pos = pos.getParentNode();
/*     */           
/* 207 */           if (null == pos || (null != top && top.equals(pos))) {
/*     */             
/* 209 */             nextNode = null;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 216 */       pos = nextNode;
/*     */     } 
/* 218 */     this.m_contentHandler.endDocument();
/*     */   }
/*     */   
/*     */   public TreeWalker(ContentHandler contentHandler, String systemId) {
/* 222 */     this.nextIsRaw = false;
/*     */     this.m_contentHandler = contentHandler;
/*     */     if (this.m_contentHandler != null)
/*     */       this.m_contentHandler.setDocumentLocator(this.m_locator); 
/*     */     if (systemId != null)
/*     */       this.m_locator.setSystemId(systemId); 
/*     */   }
/*     */   private final void dispatachChars(Node node) throws SAXException {
/* 230 */     if (this.m_contentHandler instanceof DOM2DTM.CharacterNodeHandler) {
/*     */       
/* 232 */       ((DOM2DTM.CharacterNodeHandler)this.m_contentHandler).characters(node);
/*     */     }
/*     */     else {
/*     */       
/* 236 */       String data = ((Text)node).getData();
/* 237 */       this.m_contentHandler.characters(data.toCharArray(), 0, data.length());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void startNode(Node node) throws SAXException {
/*     */     String data;
/*     */     NamedNodeMap atts;
/*     */     int nAttrs;
/*     */     int i;
/*     */     String ns;
/*     */     ProcessingInstruction pi;
/*     */     boolean isLexH;
/*     */     EntityReference eref;
/*     */     String name;
/*     */     LexicalHandler lh;
/* 252 */     if (this.m_contentHandler instanceof NodeConsumer)
/*     */     {
/* 254 */       ((NodeConsumer)this.m_contentHandler).setOriginatingNode(node);
/*     */     }
/*     */     
/* 257 */     if (node instanceof Locator) {
/*     */       
/* 259 */       Locator loc = (Locator)node;
/* 260 */       this.m_locator.setColumnNumber(loc.getColumnNumber());
/* 261 */       this.m_locator.setLineNumber(loc.getLineNumber());
/* 262 */       this.m_locator.setPublicId(loc.getPublicId());
/* 263 */       this.m_locator.setSystemId(loc.getSystemId());
/*     */     }
/*     */     else {
/*     */       
/* 267 */       this.m_locator.setColumnNumber(0);
/* 268 */       this.m_locator.setLineNumber(0);
/*     */     } 
/*     */     
/* 271 */     switch (node.getNodeType()) {
/*     */ 
/*     */       
/*     */       case 8:
/* 275 */         data = ((Comment)node).getData();
/*     */         
/* 277 */         if (this.m_contentHandler instanceof LexicalHandler) {
/*     */           
/* 279 */           LexicalHandler lexicalHandler = (LexicalHandler)this.m_contentHandler;
/*     */           
/* 281 */           lexicalHandler.comment(data.toCharArray(), 0, data.length());
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
/* 293 */         atts = ((Element)node).getAttributes();
/* 294 */         nAttrs = atts.getLength();
/*     */         
/* 296 */         for (i = 0; i < nAttrs; i++) {
/*     */           
/* 298 */           Node attr = atts.item(i);
/* 299 */           String attrName = attr.getNodeName();
/*     */           
/* 301 */           if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) {
/*     */             int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 308 */             String prefix = ((index = attrName.indexOf(":")) < 0) ? "" : attrName.substring(index + 1);
/*     */             
/* 310 */             this.m_contentHandler.startPrefixMapping(prefix, attr
/* 311 */                 .getNodeValue());
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 316 */         ns = DOM2Helper.getNamespaceOfNode(node);
/* 317 */         if (null == ns)
/* 318 */           ns = ""; 
/* 319 */         this.m_contentHandler.startElement(ns, 
/* 320 */             DOM2Helper.getLocalNameOfNode(node), node
/* 321 */             .getNodeName(), new AttList(atts));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 326 */         pi = (ProcessingInstruction)node;
/* 327 */         name = pi.getNodeName();
/*     */ 
/*     */         
/* 330 */         if (name.equals("xslt-next-is-raw")) {
/*     */           
/* 332 */           this.nextIsRaw = true;
/*     */           
/*     */           break;
/*     */         } 
/* 336 */         this.m_contentHandler.processingInstruction(pi.getNodeName(), pi
/* 337 */             .getData());
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 4:
/* 343 */         isLexH = this.m_contentHandler instanceof LexicalHandler;
/* 344 */         lh = isLexH ? (LexicalHandler)this.m_contentHandler : null;
/*     */ 
/*     */         
/* 347 */         if (isLexH)
/*     */         {
/* 349 */           lh.startCDATA();
/*     */         }
/*     */         
/* 352 */         dispatachChars(node);
/*     */ 
/*     */         
/* 355 */         if (isLexH)
/*     */         {
/* 357 */           lh.endCDATA();
/*     */         }
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 366 */         if (this.nextIsRaw) {
/*     */           
/* 368 */           this.nextIsRaw = false;
/*     */           
/* 370 */           this.m_contentHandler.processingInstruction("javax.xml.transform.disable-output-escaping", "");
/* 371 */           dispatachChars(node);
/* 372 */           this.m_contentHandler.processingInstruction("javax.xml.transform.enable-output-escaping", "");
/*     */           
/*     */           break;
/*     */         } 
/* 376 */         dispatachChars(node);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 382 */         eref = (EntityReference)node;
/*     */         
/* 384 */         if (this.m_contentHandler instanceof LexicalHandler)
/*     */         {
/* 386 */           ((LexicalHandler)this.m_contentHandler).startEntity(eref
/* 387 */               .getNodeName());
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
/*     */   protected void endNode(Node node) throws SAXException {
/*     */     String ns;
/*     */     NamedNodeMap atts;
/*     */     int nAttrs;
/*     */     int i;
/*     */     EntityReference eref;
/* 411 */     switch (node.getNodeType()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 417 */         ns = DOM2Helper.getNamespaceOfNode(node);
/* 418 */         if (null == ns)
/* 419 */           ns = ""; 
/* 420 */         this.m_contentHandler.endElement(ns, 
/* 421 */             DOM2Helper.getLocalNameOfNode(node), node
/* 422 */             .getNodeName());
/*     */         
/* 424 */         atts = ((Element)node).getAttributes();
/* 425 */         nAttrs = atts.getLength();
/*     */         
/* 427 */         for (i = 0; i < nAttrs; i++) {
/*     */           
/* 429 */           Node attr = atts.item(i);
/* 430 */           String attrName = attr.getNodeName();
/*     */           
/* 432 */           if (attrName.equals("xmlns") || attrName.startsWith("xmlns:")) {
/*     */             int index;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 439 */             String prefix = ((index = attrName.indexOf(":")) < 0) ? "" : attrName.substring(index + 1);
/*     */             
/* 441 */             this.m_contentHandler.endPrefixMapping(prefix);
/*     */           } 
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 5:
/* 449 */         eref = (EntityReference)node;
/*     */         
/* 451 */         if (this.m_contentHandler instanceof LexicalHandler) {
/*     */           
/* 453 */           LexicalHandler lh = (LexicalHandler)this.m_contentHandler;
/*     */           
/* 455 */           lh.endEntity(eref.getNodeName());
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/utils/TreeWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */