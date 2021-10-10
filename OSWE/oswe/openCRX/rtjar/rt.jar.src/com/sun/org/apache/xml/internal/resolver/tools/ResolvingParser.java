/*     */ package com.sun.org.apache.xml.internal.resolver.tools;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogManager;
/*     */ import com.sun.org.apache.xml.internal.resolver.helpers.FileURL;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Locale;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.xml.sax.AttributeList;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.DocumentHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.Parser;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolvingParser
/*     */   implements Parser, DTDHandler, DocumentHandler, EntityResolver
/*     */ {
/*     */   public static boolean namespaceAware = true;
/*     */   public static boolean validating = false;
/*     */   public static boolean suppressExplanation = false;
/*  86 */   private SAXParser saxParser = null;
/*     */ 
/*     */   
/*  89 */   private Parser parser = null;
/*     */ 
/*     */   
/*  92 */   private DocumentHandler documentHandler = null;
/*     */ 
/*     */   
/*  95 */   private DTDHandler dtdHandler = null;
/*     */ 
/*     */   
/*  98 */   private CatalogManager catalogManager = CatalogManager.getStaticManager();
/*     */ 
/*     */   
/* 101 */   private CatalogResolver catalogResolver = null;
/*     */ 
/*     */   
/* 104 */   private CatalogResolver piCatalogResolver = null;
/*     */ 
/*     */   
/*     */   private boolean allowXMLCatalogPI = false;
/*     */ 
/*     */   
/*     */   private boolean oasisXMLCatalogPI = false;
/*     */ 
/*     */   
/* 113 */   private URL baseURL = null;
/*     */ 
/*     */   
/*     */   public ResolvingParser() {
/* 117 */     initParser();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResolvingParser(CatalogManager manager) {
/* 122 */     this.catalogManager = manager;
/* 123 */     initParser();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initParser() {
/* 128 */     this.catalogResolver = new CatalogResolver(this.catalogManager);
/* 129 */     SAXParserFactory spf = JdkXmlUtils.getSAXFactory(this.catalogManager.overrideDefaultParser());
/* 130 */     spf.setValidating(validating);
/*     */     
/*     */     try {
/* 133 */       this.saxParser = spf.newSAXParser();
/* 134 */       this.parser = this.saxParser.getParser();
/* 135 */       this.documentHandler = null;
/* 136 */       this.dtdHandler = null;
/* 137 */     } catch (Exception ex) {
/* 138 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Catalog getCatalog() {
/* 144 */     return this.catalogResolver.getCatalog();
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
/*     */   public void parse(InputSource input) throws IOException, SAXException {
/* 171 */     setupParse(input.getSystemId());
/*     */     try {
/* 173 */       this.parser.parse(input);
/* 174 */     } catch (InternalError ie) {
/* 175 */       explain(input.getSystemId());
/* 176 */       throw ie;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse(String systemId) throws IOException, SAXException {
/* 187 */     setupParse(systemId);
/*     */     try {
/* 189 */       this.parser.parse(systemId);
/* 190 */     } catch (InternalError ie) {
/* 191 */       explain(systemId);
/* 192 */       throw ie;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDocumentHandler(DocumentHandler handler) {
/* 198 */     this.documentHandler = handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDTDHandler(DTDHandler handler) {
/* 203 */     this.dtdHandler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityResolver(EntityResolver resolver) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorHandler(ErrorHandler handler) {
/* 218 */     this.parser.setErrorHandler(handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocale(Locale locale) throws SAXException {
/* 223 */     this.parser.setLocale(locale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 229 */     if (this.documentHandler != null) {
/* 230 */       this.documentHandler.characters(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 236 */     if (this.documentHandler != null) {
/* 237 */       this.documentHandler.endDocument();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void endElement(String name) throws SAXException {
/* 243 */     if (this.documentHandler != null) {
/* 244 */       this.documentHandler.endElement(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 251 */     if (this.documentHandler != null) {
/* 252 */       this.documentHandler.ignorableWhitespace(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String pidata) throws SAXException {
/* 260 */     if (target.equals("oasis-xml-catalog")) {
/* 261 */       URL catalog = null;
/* 262 */       String data = pidata;
/*     */       
/* 264 */       int pos = data.indexOf("catalog=");
/* 265 */       if (pos >= 0) {
/* 266 */         data = data.substring(pos + 8);
/* 267 */         if (data.length() > 1) {
/* 268 */           String quote = data.substring(0, 1);
/* 269 */           data = data.substring(1);
/* 270 */           pos = data.indexOf(quote);
/* 271 */           if (pos >= 0) {
/* 272 */             data = data.substring(0, pos);
/*     */             try {
/* 274 */               if (this.baseURL != null) {
/* 275 */                 catalog = new URL(this.baseURL, data);
/*     */               } else {
/* 277 */                 catalog = new URL(data);
/*     */               } 
/* 279 */             } catch (MalformedURLException malformedURLException) {}
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 286 */       if (this.allowXMLCatalogPI) {
/* 287 */         if (this.catalogManager.getAllowOasisXMLCatalogPI()) {
/* 288 */           this.catalogManager.debug.message(4, "oasis-xml-catalog PI", pidata);
/*     */           
/* 290 */           if (catalog != null) {
/*     */             try {
/* 292 */               this.catalogManager.debug.message(4, "oasis-xml-catalog", catalog.toString());
/* 293 */               this.oasisXMLCatalogPI = true;
/*     */               
/* 295 */               if (this.piCatalogResolver == null) {
/* 296 */                 this.piCatalogResolver = new CatalogResolver(true);
/*     */               }
/*     */               
/* 299 */               this.piCatalogResolver.getCatalog().parseCatalog(catalog.toString());
/* 300 */             } catch (Exception e) {
/* 301 */               this.catalogManager.debug.message(3, "Exception parsing oasis-xml-catalog: " + catalog
/* 302 */                   .toString());
/*     */             } 
/*     */           } else {
/* 305 */             this.catalogManager.debug.message(3, "PI oasis-xml-catalog unparseable: " + pidata);
/*     */           } 
/*     */         } else {
/* 308 */           this.catalogManager.debug.message(4, "PI oasis-xml-catalog ignored: " + pidata);
/*     */         } 
/*     */       } else {
/* 311 */         this.catalogManager.debug.message(3, "PI oasis-xml-catalog occurred in an invalid place: " + pidata);
/*     */       }
/*     */     
/*     */     }
/* 315 */     else if (this.documentHandler != null) {
/* 316 */       this.documentHandler.processingInstruction(target, pidata);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 323 */     if (this.documentHandler != null) {
/* 324 */       this.documentHandler.setDocumentLocator(locator);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 330 */     if (this.documentHandler != null) {
/* 331 */       this.documentHandler.startDocument();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String name, AttributeList atts) throws SAXException {
/* 338 */     this.allowXMLCatalogPI = false;
/* 339 */     if (this.documentHandler != null) {
/* 340 */       this.documentHandler.startElement(name, atts);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notationDecl(String name, String publicId, String systemId) throws SAXException {
/* 347 */     this.allowXMLCatalogPI = false;
/* 348 */     if (this.dtdHandler != null) {
/* 349 */       this.dtdHandler.notationDecl(name, publicId, systemId);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
/* 359 */     this.allowXMLCatalogPI = false;
/* 360 */     if (this.dtdHandler != null) {
/* 361 */       this.dtdHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String publicId, String systemId) {
/* 371 */     this.allowXMLCatalogPI = false;
/* 372 */     String resolved = this.catalogResolver.getResolvedEntity(publicId, systemId);
/*     */     
/* 374 */     if (resolved == null && this.piCatalogResolver != null) {
/* 375 */       resolved = this.piCatalogResolver.getResolvedEntity(publicId, systemId);
/*     */     }
/*     */     
/* 378 */     if (resolved != null) {
/*     */       try {
/* 380 */         InputSource iSource = new InputSource(resolved);
/* 381 */         iSource.setPublicId(publicId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 394 */         URL url = new URL(resolved);
/* 395 */         InputStream iStream = url.openStream();
/* 396 */         iSource.setByteStream(iStream);
/*     */         
/* 398 */         return iSource;
/* 399 */       } catch (Exception e) {
/* 400 */         this.catalogManager.debug.message(1, "Failed to create InputSource", resolved);
/* 401 */         return null;
/*     */       } 
/*     */     }
/* 404 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupParse(String systemId) {
/* 410 */     this.allowXMLCatalogPI = true;
/* 411 */     this.parser.setEntityResolver(this);
/* 412 */     this.parser.setDocumentHandler(this);
/* 413 */     this.parser.setDTDHandler(this);
/*     */     
/* 415 */     URL cwd = null;
/*     */     
/*     */     try {
/* 418 */       cwd = FileURL.makeURL("basename");
/* 419 */     } catch (MalformedURLException mue) {
/* 420 */       cwd = null;
/*     */     } 
/*     */     
/*     */     try {
/* 424 */       this.baseURL = new URL(systemId);
/* 425 */     } catch (MalformedURLException mue) {
/* 426 */       if (cwd != null) {
/*     */         try {
/* 428 */           this.baseURL = new URL(cwd, systemId);
/* 429 */         } catch (MalformedURLException mue2) {
/*     */           
/* 431 */           this.baseURL = null;
/*     */         } 
/*     */       } else {
/*     */         
/* 435 */         this.baseURL = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void explain(String systemId) {
/* 442 */     if (!suppressExplanation) {
/* 443 */       System.out.println("Parser probably encountered bad URI in " + systemId);
/* 444 */       System.out.println("For example, replace '/some/uri' with 'file:/some/uri'.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/tools/ResolvingParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */