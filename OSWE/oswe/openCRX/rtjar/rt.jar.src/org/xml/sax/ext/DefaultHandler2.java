/*     */ package org.xml.sax.ext;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ public class DefaultHandler2
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler, DeclHandler, EntityResolver2
/*     */ {
/*     */   public void startCDATA() throws SAXException {}
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {}
/*     */   
/*     */   public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException {}
/*     */   
/*     */   public void elementDecl(String name, String model) throws SAXException {}
/*     */   
/*     */   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {}
/*     */   
/*     */   public void internalEntityDecl(String name, String value) throws SAXException {}
/*     */   
/*     */   public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
/* 127 */     return null;
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
/*     */   public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
/* 141 */     return null;
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
/*     */   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 153 */     return resolveEntity(null, publicId, null, systemId);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/ext/DefaultHandler2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */