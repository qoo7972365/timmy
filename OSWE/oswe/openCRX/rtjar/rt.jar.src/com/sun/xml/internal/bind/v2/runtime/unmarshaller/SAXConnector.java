/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.UnmarshallerHandler;
/*     */ import org.xml.sax.Attributes;
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
/*     */ 
/*     */ public final class SAXConnector
/*     */   implements UnmarshallerHandler
/*     */ {
/*     */   private LocatorEx loc;
/*  50 */   private static final Logger logger = Util.getClassLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private final StringBuilder buffer = new StringBuilder();
/*     */   private final XmlVisitor next;
/*     */   private final UnmarshallingContext context;
/*     */   private final XmlVisitor.TextPredictor predictor;
/*     */   
/*     */   private static final class TagNameImpl
/*     */     extends TagName {
/*     */     String qname;
/*     */     
/*     */     public String getQname() {
/*  66 */       return this.qname;
/*     */     }
/*     */     
/*     */     private TagNameImpl() {} }
/*  70 */   private final TagNameImpl tagName = new TagNameImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXConnector(XmlVisitor next, LocatorEx externalLocator) {
/*  79 */     this.next = next;
/*  80 */     this.context = next.getContext();
/*  81 */     this.predictor = next.getPredictor();
/*  82 */     this.loc = externalLocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getResult() throws JAXBException, IllegalStateException {
/*  87 */     return this.context.getResult();
/*     */   }
/*     */   
/*     */   public UnmarshallingContext getContext() {
/*  91 */     return this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/*  96 */     if (this.loc != null) {
/*     */       return;
/*     */     }
/*  99 */     this.loc = new LocatorExWrapper(locator);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 104 */     if (logger.isLoggable(Level.FINER)) {
/* 105 */       logger.log(Level.FINER, "SAXConnector.startDocument");
/*     */     }
/* 107 */     this.next.startDocument(this.loc, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 112 */     if (logger.isLoggable(Level.FINER)) {
/* 113 */       logger.log(Level.FINER, "SAXConnector.endDocument");
/*     */     }
/* 115 */     this.next.endDocument();
/*     */   }
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 120 */     if (logger.isLoggable(Level.FINER)) {
/* 121 */       logger.log(Level.FINER, "SAXConnector.startPrefixMapping: {0}:{1}", new Object[] { prefix, uri });
/*     */     }
/* 123 */     this.next.startPrefixMapping(prefix, uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/* 128 */     if (logger.isLoggable(Level.FINER)) {
/* 129 */       logger.log(Level.FINER, "SAXConnector.endPrefixMapping: {0}", new Object[] { prefix });
/*     */     }
/* 131 */     this.next.endPrefixMapping(prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String local, String qname, Attributes atts) throws SAXException {
/* 136 */     if (logger.isLoggable(Level.FINER)) {
/* 137 */       logger.log(Level.FINER, "SAXConnector.startElement: {0}:{1}:{2}, attrs: {3}", new Object[] { uri, local, qname, atts });
/*     */     }
/*     */     
/* 140 */     if (uri == null || uri.length() == 0)
/* 141 */       uri = ""; 
/* 142 */     if (local == null || local.length() == 0)
/* 143 */       local = qname; 
/* 144 */     if (qname == null || qname.length() == 0) {
/* 145 */       qname = local;
/*     */     }
/* 147 */     processText(!this.context.getCurrentState().isMixed());
/*     */     
/* 149 */     this.tagName.uri = uri;
/* 150 */     this.tagName.local = local;
/* 151 */     this.tagName.qname = qname;
/* 152 */     this.tagName.atts = atts;
/* 153 */     this.next.startElement(this.tagName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 158 */     if (logger.isLoggable(Level.FINER)) {
/* 159 */       logger.log(Level.FINER, "SAXConnector.startElement: {0}:{1}:{2}", new Object[] { uri, localName, qName });
/*     */     }
/* 161 */     processText(false);
/* 162 */     this.tagName.uri = uri;
/* 163 */     this.tagName.local = localName;
/* 164 */     this.tagName.qname = qName;
/* 165 */     this.next.endElement(this.tagName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void characters(char[] buf, int start, int len) {
/* 171 */     if (logger.isLoggable(Level.FINEST)) {
/* 172 */       logger.log(Level.FINEST, "SAXConnector.characters: {0}", buf);
/*     */     }
/* 174 */     if (this.predictor.expectText()) {
/* 175 */       this.buffer.append(buf, start, len);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void ignorableWhitespace(char[] buf, int start, int len) {
/* 180 */     if (logger.isLoggable(Level.FINEST)) {
/* 181 */       logger.log(Level.FINEST, "SAXConnector.characters{0}", buf);
/*     */     }
/* 183 */     characters(buf, start, len);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void processText(boolean ignorable) throws SAXException {
/* 197 */     if (this.predictor.expectText() && (!ignorable || !WhiteSpaceProcessor.isWhiteSpace(this.buffer)))
/* 198 */       this.next.text(this.buffer); 
/* 199 */     this.buffer.setLength(0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/SAXConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */