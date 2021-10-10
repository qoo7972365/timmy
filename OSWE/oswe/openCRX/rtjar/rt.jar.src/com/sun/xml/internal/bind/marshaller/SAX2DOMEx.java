/*     */ package com.sun.xml.internal.bind.marshaller;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.xml.internal.bind.util.Which;
/*     */ import com.sun.xml.internal.bind.v2.util.XmlFactory;
/*     */ import java.util.Stack;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAX2DOMEx
/*     */   implements ContentHandler
/*     */ {
/*  53 */   private Node node = null;
/*     */   private boolean isConsolidate;
/*  55 */   protected final Stack<Node> nodeStack = new Stack<>();
/*  56 */   private final FinalArrayList<String> unprocessedNamespaces = new FinalArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Document document;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx(Node node) {
/*  67 */     this(node, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx(Node node, boolean isConsolidate) {
/*  75 */     this.node = node;
/*  76 */     this.isConsolidate = isConsolidate;
/*  77 */     this.nodeStack.push(this.node);
/*     */     
/*  79 */     if (node instanceof Document) {
/*  80 */       this.document = (Document)node;
/*     */     } else {
/*  82 */       this.document = node.getOwnerDocument();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx(DocumentBuilderFactory f) throws ParserConfigurationException {
/*  90 */     f.setValidating(false);
/*  91 */     this.document = f.newDocumentBuilder().newDocument();
/*  92 */     this.node = this.document;
/*  93 */     this.nodeStack.push(this.document);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAX2DOMEx() throws ParserConfigurationException {
/* 101 */     DocumentBuilderFactory factory = XmlFactory.createDocumentBuilderFactory(false);
/* 102 */     factory.setValidating(false);
/*     */     
/* 104 */     this.document = factory.newDocumentBuilder().newDocument();
/* 105 */     this.node = this.document;
/* 106 */     this.nodeStack.push(this.document);
/*     */   }
/*     */   
/*     */   public final Element getCurrentElement() {
/* 110 */     return (Element)this.nodeStack.peek();
/*     */   }
/*     */   
/*     */   public Node getDOM() {
/* 114 */     return this.node;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() {}
/*     */ 
/*     */   
/*     */   public void endDocument() {}
/*     */   
/*     */   protected void namespace(Element element, String prefix, String uri) {
/*     */     String qname;
/* 125 */     if ("".equals(prefix) || prefix == null) {
/* 126 */       qname = "xmlns";
/*     */     } else {
/* 128 */       qname = "xmlns:" + prefix;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     if (element.hasAttributeNS("http://www.w3.org/2000/xmlns/", qname))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       element.removeAttributeNS("http://www.w3.org/2000/xmlns/", qname);
/*     */     }
/*     */ 
/*     */     
/* 146 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", qname, uri);
/*     */   }
/*     */   
/*     */   public void startElement(String namespace, String localName, String qName, Attributes attrs) {
/* 150 */     Node parent = this.nodeStack.peek();
/*     */ 
/*     */ 
/*     */     
/* 154 */     Element element = this.document.createElementNS(namespace, qName);
/*     */     
/* 156 */     if (element == null)
/*     */     {
/*     */       
/* 159 */       throw new AssertionError(
/* 160 */           Messages.format("SAX2DOMEx.DomImplDoesntSupportCreateElementNs", this.document
/* 161 */             .getClass().getName(), 
/* 162 */             Which.which(this.document.getClass())));
/*     */     }
/*     */ 
/*     */     
/* 166 */     for (int i = 0; i < this.unprocessedNamespaces.size(); i += 2) {
/* 167 */       String prefix = (String)this.unprocessedNamespaces.get(i);
/* 168 */       String uri = (String)this.unprocessedNamespaces.get(i + 1);
/*     */       
/* 170 */       namespace(element, prefix, uri);
/*     */     } 
/* 172 */     this.unprocessedNamespaces.clear();
/*     */ 
/*     */     
/* 175 */     if (attrs != null) {
/* 176 */       int length = attrs.getLength();
/* 177 */       for (int j = 0; j < length; j++) {
/* 178 */         String namespaceuri = attrs.getURI(j);
/* 179 */         String value = attrs.getValue(j);
/* 180 */         String qname = attrs.getQName(j);
/* 181 */         element.setAttributeNS(namespaceuri, qname, value);
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     parent.appendChild(element);
/*     */     
/* 187 */     this.nodeStack.push(element);
/*     */   }
/*     */   
/*     */   public void endElement(String namespace, String localName, String qName) {
/* 191 */     this.nodeStack.pop();
/*     */   }
/*     */   
/*     */   public void characters(char[] ch, int start, int length) {
/* 195 */     characters(new String(ch, start, length));
/*     */   }
/*     */   protected Text characters(String s) {
/*     */     Text text;
/* 199 */     Node parent = this.nodeStack.peek();
/* 200 */     Node lastChild = parent.getLastChild();
/*     */     
/* 202 */     if (this.isConsolidate && lastChild != null && lastChild.getNodeType() == 3) {
/* 203 */       text = (Text)lastChild;
/* 204 */       text.appendData(s);
/*     */     } else {
/* 206 */       text = this.document.createTextNode(s);
/* 207 */       parent.appendChild(text);
/*     */     } 
/* 209 */     return text;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) {}
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 216 */     Node parent = this.nodeStack.peek();
/* 217 */     Node n = this.document.createProcessingInstruction(target, data);
/* 218 */     parent.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) {}
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) {
/* 228 */     this.unprocessedNamespaces.add(prefix);
/* 229 */     this.unprocessedNamespaces.add(uri);
/*     */   }
/*     */   
/*     */   public void endPrefixMapping(String prefix) {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/marshaller/SAX2DOMEx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */