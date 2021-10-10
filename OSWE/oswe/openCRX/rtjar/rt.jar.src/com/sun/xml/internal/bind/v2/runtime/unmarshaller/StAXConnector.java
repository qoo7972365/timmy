/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import javax.xml.bind.helpers.ValidationEventLocatorImpl;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ abstract class StAXConnector
/*     */ {
/*     */   protected final XmlVisitor visitor;
/*     */   protected final UnmarshallingContext context;
/*     */   protected final XmlVisitor.TextPredictor predictor;
/*     */   
/*     */   private final class TagNameImpl
/*     */     extends TagName
/*     */   {
/*     */     private TagNameImpl() {}
/*     */     
/*     */     public String getQname() {
/*  51 */       return StAXConnector.this.getCurrentQName();
/*     */     }
/*     */   }
/*     */   
/*  55 */   protected final TagName tagName = new TagNameImpl();
/*     */   
/*     */   protected StAXConnector(XmlVisitor visitor) {
/*  58 */     this.visitor = visitor;
/*  59 */     this.context = visitor.getContext();
/*  60 */     this.predictor = visitor.getPredictor();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void bridge() throws XMLStreamException;
/*     */ 
/*     */   
/*     */   protected abstract Location getCurrentLocation();
/*     */ 
/*     */   
/*     */   protected abstract String getCurrentQName();
/*     */ 
/*     */   
/*     */   protected final void handleStartDocument(NamespaceContext nsc) throws SAXException {
/*  75 */     this.visitor.startDocument(new LocatorEx() {
/*     */           public ValidationEventLocator getLocation() {
/*  77 */             return (ValidationEventLocator)new ValidationEventLocatorImpl(this);
/*     */           }
/*     */           public int getColumnNumber() {
/*  80 */             return StAXConnector.this.getCurrentLocation().getColumnNumber();
/*     */           }
/*     */           public int getLineNumber() {
/*  83 */             return StAXConnector.this.getCurrentLocation().getLineNumber();
/*     */           }
/*     */           public String getPublicId() {
/*  86 */             return StAXConnector.this.getCurrentLocation().getPublicId();
/*     */           }
/*     */           public String getSystemId() {
/*  89 */             return StAXConnector.this.getCurrentLocation().getSystemId();
/*     */           }
/*     */         },  nsc);
/*     */   }
/*     */   
/*     */   protected final void handleEndDocument() throws SAXException {
/*  95 */     this.visitor.endDocument();
/*     */   }
/*     */   
/*     */   protected static String fixNull(String s) {
/*  99 */     if (s == null) return ""; 
/* 100 */     return s;
/*     */   }
/*     */   
/*     */   protected final String getQName(String prefix, String localName) {
/* 104 */     if (prefix == null || prefix.length() == 0) {
/* 105 */       return localName;
/*     */     }
/* 107 */     return prefix + ':' + localName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/StAXConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */