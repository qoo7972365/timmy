/*     */ package com.sun.org.apache.xml.internal.serializer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import javax.xml.transform.SourceLocator;
/*     */ import javax.xml.transform.Transformer;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
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
/*     */ public class EmptySerializer
/*     */   implements SerializationHandler
/*     */ {
/*     */   protected static final String ERR = "EmptySerializer method not over-ridden";
/*     */   
/*     */   protected void couldThrowIOException() throws IOException {}
/*     */   
/*     */   protected void couldThrowSAXException() throws SAXException {}
/*     */   
/*     */   protected void couldThrowSAXException(char[] chars, int off, int len) throws SAXException {}
/*     */   
/*     */   protected void couldThrowSAXException(String elemQName) throws SAXException {}
/*     */   
/*     */   void aMethodIsCalled() {}
/*     */   
/*     */   public ContentHandler asContentHandler() throws IOException {
/*  89 */     couldThrowIOException();
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContentHandler(ContentHandler ch) {
/*  97 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 104 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties getOutputFormat() {
/* 111 */     aMethodIsCalled();
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() {
/* 119 */     aMethodIsCalled();
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Writer getWriter() {
/* 127 */     aMethodIsCalled();
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean reset() {
/* 135 */     aMethodIsCalled();
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Node node) throws IOException {
/* 143 */     couldThrowIOException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCdataSectionElements(Vector URI_and_localNames) {
/* 150 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setEscaping(boolean escape) throws SAXException {
/* 157 */     couldThrowSAXException();
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndent(boolean indent) {
/* 165 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndentAmount(int spaces) {
/* 172 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsStandalone(boolean isStandalone) {
/* 179 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputFormat(Properties format) {
/* 186 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputStream(OutputStream output) {
/* 193 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(String version) {
/* 200 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWriter(Writer writer) {
/* 207 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransformer(Transformer transformer) {
/* 214 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transformer getTransformer() {
/* 221 */     aMethodIsCalled();
/* 222 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushPending() throws SAXException {
/* 229 */     couldThrowSAXException();
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
/*     */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean XSLAttribute) throws SAXException {
/* 243 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttributes(Attributes atts) throws SAXException {
/* 250 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String name, String value) {
/* 257 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(String chars) throws SAXException {
/* 265 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String elemName) throws SAXException {
/* 272 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 279 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName) throws SAXException {
/* 287 */     couldThrowSAXException(qName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String qName) throws SAXException {
/* 294 */     couldThrowSAXException(qName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void namespaceAfterStartElement(String uri, String prefix) throws SAXException {
/* 302 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush) throws SAXException {
/* 313 */     couldThrowSAXException();
/* 314 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void entityReference(String entityName) throws SAXException {
/* 321 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceMappings getNamespaceMappings() {
/* 328 */     aMethodIsCalled();
/* 329 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) {
/* 336 */     aMethodIsCalled();
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String name, boolean isElement) {
/* 344 */     aMethodIsCalled();
/* 345 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURIFromPrefix(String prefix) {
/* 352 */     aMethodIsCalled();
/* 353 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator arg0) {
/* 360 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 367 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String arg0, String arg1) throws SAXException {
/* 375 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String arg0) throws SAXException {
/* 382 */     couldThrowSAXException();
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
/*     */   public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
/* 394 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String arg0, String arg1, String arg2) throws SAXException {
/* 402 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
/* 409 */     couldThrowSAXException(arg0, arg1, arg2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
/* 417 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String arg0, String arg1) throws SAXException {
/* 425 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String arg0) throws SAXException {
/* 432 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(String comment) throws SAXException {
/* 439 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String arg0, String arg1, String arg2) throws SAXException {
/* 447 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {
/* 454 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEntity(String arg0) throws SAXException {
/* 461 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String arg0) throws SAXException {
/* 468 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {
/* 475 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {
/* 482 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] arg0, int arg1, int arg2) throws SAXException {
/* 489 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDoctypePublic() {
/* 496 */     aMethodIsCalled();
/* 497 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDoctypeSystem() {
/* 504 */     aMethodIsCalled();
/* 505 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 512 */     aMethodIsCalled();
/* 513 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIndent() {
/* 520 */     aMethodIsCalled();
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndentAmount() {
/* 528 */     aMethodIsCalled();
/* 529 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMediaType() {
/* 536 */     aMethodIsCalled();
/* 537 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getOmitXMLDeclaration() {
/* 544 */     aMethodIsCalled();
/* 545 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStandalone() {
/* 552 */     aMethodIsCalled();
/* 553 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 560 */     aMethodIsCalled();
/* 561 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoctype(String system, String pub) {
/* 568 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoctypePublic(String doctype) {
/* 575 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDoctypeSystem(String doctype) {
/* 582 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 589 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMediaType(String mediatype) {
/* 596 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOmitXMLDeclaration(boolean b) {
/* 603 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStandalone(String standalone) {
/* 610 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void elementDecl(String arg0, String arg1) throws SAXException {
/* 617 */     couldThrowSAXException();
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
/*     */   public void attributeDecl(String arg0, String arg1, String arg2, String arg3, String arg4) throws SAXException {
/* 630 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void internalEntityDecl(String arg0, String arg1) throws SAXException {
/* 638 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void externalEntityDecl(String arg0, String arg1, String arg2) throws SAXException {
/* 646 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warning(SAXParseException arg0) throws SAXException {
/* 653 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(SAXParseException arg0) throws SAXException {
/* 660 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fatalError(SAXParseException arg0) throws SAXException {
/* 667 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMSerializer asDOMSerializer() throws IOException {
/* 674 */     couldThrowIOException();
/* 675 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceMappings(NamespaceMappings mappings) {
/* 682 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceLocator(SourceLocator locator) {
/* 690 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUniqueAttribute(String name, String value, int flags) throws SAXException {
/* 699 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(Node node) throws SAXException {
/* 707 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addXSLAttribute(String qName, String value, String uri) {
/* 715 */     aMethodIsCalled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String uri, String localName, String rawName, String type, String value) throws SAXException {
/* 723 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notationDecl(String arg0, String arg1, String arg2) throws SAXException {
/* 730 */     couldThrowSAXException();
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
/*     */   public void unparsedEntityDecl(String arg0, String arg1, String arg2, String arg3) throws SAXException {
/* 742 */     couldThrowSAXException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDTDEntityExpansion(boolean expand) {
/* 749 */     aMethodIsCalled();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serializer/EmptySerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */