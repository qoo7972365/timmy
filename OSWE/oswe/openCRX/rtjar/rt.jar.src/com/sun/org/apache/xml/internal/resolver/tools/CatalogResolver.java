/*     */ package com.sun.org.apache.xml.internal.resolver.tools;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.resolver.Catalog;
/*     */ import com.sun.org.apache.xml.internal.resolver.CatalogManager;
/*     */ import com.sun.org.apache.xml.internal.resolver.helpers.FileURL;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.URIResolver;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import jdk.xml.internal.JdkXmlUtils;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CatalogResolver
/*     */   implements EntityResolver, URIResolver
/*     */ {
/*     */   public boolean namespaceAware = true;
/*     */   public boolean validating = false;
/*  79 */   private Catalog catalog = null;
/*     */ 
/*     */   
/*  82 */   private CatalogManager catalogManager = CatalogManager.getStaticManager();
/*     */ 
/*     */   
/*     */   public CatalogResolver() {
/*  86 */     initializeCatalogs(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public CatalogResolver(boolean privateCatalog) {
/*  91 */     initializeCatalogs(privateCatalog);
/*     */   }
/*     */ 
/*     */   
/*     */   public CatalogResolver(CatalogManager manager) {
/*  96 */     this.catalogManager = manager;
/*  97 */     initializeCatalogs(!this.catalogManager.getUseStaticCatalog());
/*     */   }
/*     */ 
/*     */   
/*     */   private void initializeCatalogs(boolean privateCatalog) {
/* 102 */     this.catalog = this.catalogManager.getCatalog();
/*     */   }
/*     */ 
/*     */   
/*     */   public Catalog getCatalog() {
/* 107 */     return this.catalog;
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
/*     */   public String getResolvedEntity(String publicId, String systemId) {
/* 131 */     String resolved = null;
/*     */     
/* 133 */     if (this.catalog == null) {
/* 134 */       this.catalogManager.debug.message(1, "Catalog resolution attempted with null catalog; ignored");
/* 135 */       return null;
/*     */     } 
/*     */     
/* 138 */     if (systemId != null) {
/*     */       try {
/* 140 */         resolved = this.catalog.resolveSystem(systemId);
/* 141 */       } catch (MalformedURLException me) {
/* 142 */         this.catalogManager.debug.message(1, "Malformed URL exception trying to resolve", publicId);
/*     */         
/* 144 */         resolved = null;
/* 145 */       } catch (IOException ie) {
/* 146 */         this.catalogManager.debug.message(1, "I/O exception trying to resolve", publicId);
/* 147 */         resolved = null;
/*     */       } 
/*     */     }
/*     */     
/* 151 */     if (resolved == null) {
/* 152 */       if (publicId != null) {
/*     */         try {
/* 154 */           resolved = this.catalog.resolvePublic(publicId, systemId);
/* 155 */         } catch (MalformedURLException me) {
/* 156 */           this.catalogManager.debug.message(1, "Malformed URL exception trying to resolve", publicId);
/*     */         }
/* 158 */         catch (IOException ie) {
/* 159 */           this.catalogManager.debug.message(1, "I/O exception trying to resolve", publicId);
/*     */         } 
/*     */       }
/*     */       
/* 163 */       if (resolved != null) {
/* 164 */         this.catalogManager.debug.message(2, "Resolved public", publicId, resolved);
/*     */       }
/*     */     } else {
/* 167 */       this.catalogManager.debug.message(2, "Resolved system", systemId, resolved);
/*     */     } 
/*     */     
/* 170 */     return resolved;
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
/*     */   public InputSource resolveEntity(String publicId, String systemId) {
/* 201 */     String resolved = getResolvedEntity(publicId, systemId);
/*     */     
/* 203 */     if (resolved != null) {
/*     */       try {
/* 205 */         InputSource iSource = new InputSource(resolved);
/* 206 */         iSource.setPublicId(publicId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 219 */         URL url = new URL(resolved);
/* 220 */         InputStream iStream = url.openStream();
/* 221 */         iSource.setByteStream(iStream);
/*     */         
/* 223 */         return iSource;
/* 224 */       } catch (Exception e) {
/* 225 */         this.catalogManager.debug.message(1, "Failed to create InputSource", resolved);
/* 226 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source resolve(String href, String base) throws TransformerException {
/* 237 */     String uri = href;
/* 238 */     String fragment = null;
/* 239 */     int hashPos = href.indexOf("#");
/* 240 */     if (hashPos >= 0) {
/* 241 */       uri = href.substring(0, hashPos);
/* 242 */       fragment = href.substring(hashPos + 1);
/*     */     } 
/*     */     
/* 245 */     String result = null;
/*     */     
/*     */     try {
/* 248 */       result = this.catalog.resolveURI(href);
/* 249 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 253 */     if (result == null) {
/*     */       try {
/* 255 */         URL url = null;
/*     */         
/* 257 */         if (base == null) {
/* 258 */           url = new URL(uri);
/* 259 */           result = url.toString();
/*     */         } else {
/* 261 */           URL baseURL = new URL(base);
/* 262 */           url = (href.length() == 0) ? baseURL : new URL(baseURL, uri);
/* 263 */           result = url.toString();
/*     */         } 
/* 265 */       } catch (MalformedURLException mue) {
/*     */         
/* 267 */         String absBase = makeAbsolute(base);
/* 268 */         if (!absBase.equals(base))
/*     */         {
/* 270 */           return resolve(href, absBase);
/*     */         }
/* 272 */         throw new TransformerException("Malformed URL " + href + "(base " + base + ")", mue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     this.catalogManager.debug.message(2, "Resolved URI", href, result);
/*     */     
/* 281 */     SAXSource source = new SAXSource();
/* 282 */     source.setInputSource(new InputSource(result));
/* 283 */     setEntityResolver(source);
/* 284 */     return source;
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
/*     */   private void setEntityResolver(SAXSource source) throws TransformerException {
/* 309 */     XMLReader reader = source.getXMLReader();
/* 310 */     if (reader == null) {
/* 311 */       SAXParserFactory spf = JdkXmlUtils.getSAXFactory(this.catalogManager.overrideDefaultParser());
/*     */       try {
/* 313 */         reader = spf.newSAXParser().getXMLReader();
/*     */       }
/* 315 */       catch (ParserConfigurationException ex) {
/* 316 */         throw new TransformerException(ex);
/*     */       }
/* 318 */       catch (SAXException ex) {
/* 319 */         throw new TransformerException(ex);
/*     */       } 
/*     */     } 
/* 322 */     reader.setEntityResolver(this);
/* 323 */     source.setXMLReader(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   private String makeAbsolute(String uri) {
/* 328 */     if (uri == null) {
/* 329 */       uri = "";
/*     */     }
/*     */     
/*     */     try {
/* 333 */       URL url = new URL(uri);
/* 334 */       return url.toString();
/* 335 */     } catch (MalformedURLException mue) {
/*     */       try {
/* 337 */         URL fileURL = FileURL.makeURL(uri);
/* 338 */         return fileURL.toString();
/* 339 */       } catch (MalformedURLException mue2) {
/*     */         
/* 341 */         return uri;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/tools/CatalogResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */