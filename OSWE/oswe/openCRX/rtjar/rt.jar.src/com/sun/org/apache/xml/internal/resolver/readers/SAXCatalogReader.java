/*     */ package com.sun.org.apache.xml.internal.resolver.readers;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogException;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogManager;
/*     */ import com.sun.org.apache.xml.internal.resolver.helpers.BootstrapResolver;
/*     */ import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import org.xml.sax.AttributeList;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DocumentHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.Parser;
/*     */ import org.xml.sax.SAXException;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXCatalogReader
/*     */   implements CatalogReader, ContentHandler, DocumentHandler
/*     */ {
/*  80 */   protected SAXParserFactory parserFactory = null;
/*     */ 
/*     */   
/*  83 */   protected String parserClass = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected Map<String, String> namespaceMap = new HashMap<>();
/*     */ 
/*     */   
/*  95 */   private SAXCatalogParser saxParser = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean abandonHope = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private Catalog catalog;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParserFactory(SAXParserFactory parserFactory) {
/* 108 */     this.parserFactory = parserFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParserClass(String parserClass) {
/* 114 */     this.parserClass = parserClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public SAXParserFactory getParserFactory() {
/* 119 */     return this.parserFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParserClass() {
/* 124 */     return this.parserClass;
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
/* 136 */   protected Debug debug = (CatalogManager.getStaticManager()).debug;
/*     */ 
/*     */   
/*     */   public SAXCatalogReader() {
/* 140 */     this.parserFactory = null;
/* 141 */     this.parserClass = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SAXCatalogReader(SAXParserFactory parserFactory) {
/* 146 */     this.parserFactory = parserFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public SAXCatalogReader(String parserClass) {
/* 151 */     this.parserClass = parserClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCatalogParser(String namespaceURI, String rootElement, String parserClass) {
/* 160 */     if (namespaceURI == null) {
/* 161 */       this.namespaceMap.put(rootElement, parserClass);
/*     */     } else {
/* 163 */       this.namespaceMap.put("{" + namespaceURI + "}" + rootElement, parserClass);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCatalogParser(String namespaceURI, String rootElement) {
/* 172 */     if (namespaceURI == null) {
/* 173 */       return this.namespaceMap.get(rootElement);
/*     */     }
/* 175 */     return this.namespaceMap.get("{" + namespaceURI + "}" + rootElement);
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
/*     */   public void readCatalog(Catalog catalog, String fileUrl) throws MalformedURLException, IOException, CatalogException {
/* 192 */     URL url = null;
/*     */     
/*     */     try {
/* 195 */       url = new URL(fileUrl);
/* 196 */     } catch (MalformedURLException e) {
/* 197 */       url = new URL("file:///" + fileUrl);
/*     */     } 
/*     */     
/* 200 */     this.debug = (catalog.getCatalogManager()).debug;
/*     */     
/*     */     try {
/* 203 */       URLConnection urlCon = url.openConnection();
/* 204 */       readCatalog(catalog, urlCon.getInputStream());
/* 205 */     } catch (FileNotFoundException e) {
/* 206 */       (catalog.getCatalogManager()).debug.message(1, "Failed to load catalog, file not found", url
/* 207 */           .toString());
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
/*     */   public void readCatalog(Catalog catalog, InputStream is) throws IOException, CatalogException {
/* 225 */     if (this.parserFactory == null && this.parserClass == null) {
/* 226 */       this.debug.message(1, "Cannot read SAX catalog without a parser");
/* 227 */       throw new CatalogException(6);
/*     */     } 
/*     */     
/* 230 */     this.debug = (catalog.getCatalogManager()).debug;
/* 231 */     BootstrapResolver bootstrapResolver = catalog.getCatalogManager().getBootstrapResolver();
/*     */     
/* 233 */     this.catalog = catalog;
/*     */     
/*     */     try {
/* 236 */       if (this.parserFactory != null) {
/* 237 */         SAXParser parser = this.parserFactory.newSAXParser();
/* 238 */         SAXParserHandler spHandler = new SAXParserHandler();
/* 239 */         spHandler.setContentHandler(this);
/* 240 */         if (bootstrapResolver != null) {
/* 241 */           spHandler.setEntityResolver((EntityResolver)bootstrapResolver);
/*     */         }
/* 243 */         parser.parse(new InputSource(is), spHandler);
/*     */       } else {
/* 245 */         Parser parser = (Parser)ReflectUtil.forName(this.parserClass).newInstance();
/* 246 */         parser.setDocumentHandler(this);
/* 247 */         if (bootstrapResolver != null) {
/* 248 */           parser.setEntityResolver((EntityResolver)bootstrapResolver);
/*     */         }
/* 250 */         parser.parse(new InputSource(is));
/*     */       } 
/* 252 */     } catch (ClassNotFoundException cnfe) {
/* 253 */       throw new CatalogException(6);
/* 254 */     } catch (IllegalAccessException iae) {
/* 255 */       throw new CatalogException(6);
/* 256 */     } catch (InstantiationException ie) {
/* 257 */       throw new CatalogException(6);
/* 258 */     } catch (ParserConfigurationException pce) {
/* 259 */       throw new CatalogException(5);
/* 260 */     } catch (SAXException se) {
/* 261 */       Exception e = se.getException();
/*     */       
/* 263 */       UnknownHostException uhe = new UnknownHostException();
/* 264 */       FileNotFoundException fnfe = new FileNotFoundException();
/* 265 */       if (e != null) {
/* 266 */         if (e.getClass() == uhe.getClass())
/* 267 */           throw new CatalogException(7, e
/* 268 */               .toString()); 
/* 269 */         if (e.getClass() == fnfe.getClass()) {
/* 270 */           throw new CatalogException(7, e
/* 271 */               .toString());
/*     */         }
/*     */       } 
/* 274 */       throw new CatalogException(se);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {
/* 283 */     if (this.saxParser != null) {
/* 284 */       this.saxParser.setDocumentLocator(locator);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 290 */     this.saxParser = null;
/* 291 */     this.abandonHope = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 297 */     if (this.saxParser != null) {
/* 298 */       this.saxParser.endDocument();
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
/*     */   public void startElement(String name, AttributeList atts) throws SAXException {
/* 312 */     if (this.abandonHope) {
/*     */       return;
/*     */     }
/*     */     
/* 316 */     if (this.saxParser == null) {
/* 317 */       String prefix = "";
/* 318 */       if (name.indexOf(':') > 0) {
/* 319 */         prefix = name.substring(0, name.indexOf(':'));
/*     */       }
/*     */       
/* 322 */       String localName = name;
/* 323 */       if (localName.indexOf(':') > 0) {
/* 324 */         localName = localName.substring(localName.indexOf(':') + 1);
/*     */       }
/*     */       
/* 327 */       String namespaceURI = null;
/* 328 */       if (prefix.equals("")) {
/* 329 */         namespaceURI = atts.getValue("xmlns");
/*     */       } else {
/* 331 */         namespaceURI = atts.getValue("xmlns:" + prefix);
/*     */       } 
/*     */       
/* 334 */       String saxParserClass = getCatalogParser(namespaceURI, localName);
/*     */ 
/*     */       
/* 337 */       if (saxParserClass == null) {
/* 338 */         this.abandonHope = true;
/* 339 */         if (namespaceURI == null) {
/* 340 */           this.debug.message(2, "No Catalog parser for " + name);
/*     */         } else {
/* 342 */           this.debug.message(2, "No Catalog parser for {" + namespaceURI + "}" + name);
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 350 */         this
/* 351 */           .saxParser = (SAXCatalogParser)ReflectUtil.forName(saxParserClass).newInstance();
/*     */         
/* 353 */         this.saxParser.setCatalog(this.catalog);
/* 354 */         this.saxParser.startDocument();
/* 355 */         this.saxParser.startElement(name, atts);
/* 356 */       } catch (ClassNotFoundException cnfe) {
/* 357 */         this.saxParser = null;
/* 358 */         this.abandonHope = true;
/* 359 */         this.debug.message(2, cnfe.toString());
/* 360 */       } catch (InstantiationException ie) {
/* 361 */         this.saxParser = null;
/* 362 */         this.abandonHope = true;
/* 363 */         this.debug.message(2, ie.toString());
/* 364 */       } catch (IllegalAccessException iae) {
/* 365 */         this.saxParser = null;
/* 366 */         this.abandonHope = true;
/* 367 */         this.debug.message(2, iae.toString());
/* 368 */       } catch (ClassCastException cce) {
/* 369 */         this.saxParser = null;
/* 370 */         this.abandonHope = true;
/* 371 */         this.debug.message(2, cce.toString());
/*     */       } 
/*     */     } else {
/* 374 */       this.saxParser.startElement(name, atts);
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
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/* 390 */     if (this.abandonHope) {
/*     */       return;
/*     */     }
/*     */     
/* 394 */     if (this.saxParser == null) {
/* 395 */       String saxParserClass = getCatalogParser(namespaceURI, localName);
/*     */ 
/*     */       
/* 398 */       if (saxParserClass == null) {
/* 399 */         this.abandonHope = true;
/* 400 */         if (namespaceURI == null) {
/* 401 */           this.debug.message(2, "No Catalog parser for " + localName);
/*     */         } else {
/* 403 */           this.debug.message(2, "No Catalog parser for {" + namespaceURI + "}" + localName);
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 411 */         this
/* 412 */           .saxParser = (SAXCatalogParser)ReflectUtil.forName(saxParserClass).newInstance();
/*     */         
/* 414 */         this.saxParser.setCatalog(this.catalog);
/* 415 */         this.saxParser.startDocument();
/* 416 */         this.saxParser.startElement(namespaceURI, localName, qName, atts);
/* 417 */       } catch (ClassNotFoundException cnfe) {
/* 418 */         this.saxParser = null;
/* 419 */         this.abandonHope = true;
/* 420 */         this.debug.message(2, cnfe.toString());
/* 421 */       } catch (InstantiationException ie) {
/* 422 */         this.saxParser = null;
/* 423 */         this.abandonHope = true;
/* 424 */         this.debug.message(2, ie.toString());
/* 425 */       } catch (IllegalAccessException iae) {
/* 426 */         this.saxParser = null;
/* 427 */         this.abandonHope = true;
/* 428 */         this.debug.message(2, iae.toString());
/* 429 */       } catch (ClassCastException cce) {
/* 430 */         this.saxParser = null;
/* 431 */         this.abandonHope = true;
/* 432 */         this.debug.message(2, cce.toString());
/*     */       } 
/*     */     } else {
/* 435 */       this.saxParser.startElement(namespaceURI, localName, qName, atts);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endElement(String name) throws SAXException {
/* 441 */     if (this.saxParser != null) {
/* 442 */       this.saxParser.endElement(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/* 450 */     if (this.saxParser != null) {
/* 451 */       this.saxParser.endElement(namespaceURI, localName, qName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 458 */     if (this.saxParser != null) {
/* 459 */       this.saxParser.characters(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 466 */     if (this.saxParser != null) {
/* 467 */       this.saxParser.ignorableWhitespace(ch, start, length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 474 */     if (this.saxParser != null) {
/* 475 */       this.saxParser.processingInstruction(target, data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 482 */     if (this.saxParser != null) {
/* 483 */       this.saxParser.startPrefixMapping(prefix, uri);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {
/* 490 */     if (this.saxParser != null) {
/* 491 */       this.saxParser.endPrefixMapping(prefix);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {
/* 498 */     if (this.saxParser != null)
/* 499 */       this.saxParser.skippedEntity(name); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/readers/SAXCatalogReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */