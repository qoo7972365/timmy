/*     */ package com.sun.org.apache.xml.internal.resolver.helpers;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.URIResolver;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BootstrapResolver
/*     */   implements EntityResolver, URIResolver
/*     */ {
/*     */   public static final String xmlCatalogXSD = "http://www.oasis-open.org/committees/entity/release/1.0/catalog.xsd";
/*     */   public static final String xmlCatalogRNG = "http://www.oasis-open.org/committees/entity/release/1.0/catalog.rng";
/*     */   public static final String xmlCatalogPubId = "-//OASIS//DTD XML Catalogs V1.0//EN";
/*     */   public static final String xmlCatalogSysId = "http://www.oasis-open.org/committees/entity/release/1.0/catalog.dtd";
/*  66 */   private final Map<String, String> publicMap = new HashMap<>();
/*     */ 
/*     */   
/*  69 */   private final Map<String, String> systemMap = new HashMap<>();
/*     */ 
/*     */   
/*  72 */   private final Map<String, String> uriMap = new HashMap<>();
/*     */ 
/*     */   
/*     */   public BootstrapResolver() {
/*  76 */     URL url = getClass().getResource("/com/sun/org/apache/xml/internal/resolver/etc/catalog.dtd");
/*  77 */     if (url != null) {
/*  78 */       this.publicMap.put("-//OASIS//DTD XML Catalogs V1.0//EN", url.toString());
/*  79 */       this.systemMap.put("http://www.oasis-open.org/committees/entity/release/1.0/catalog.dtd", url.toString());
/*     */     } 
/*     */     
/*  82 */     url = getClass().getResource("/com/sun/org/apache/xml/internal/resolver/etc/catalog.rng");
/*  83 */     if (url != null) {
/*  84 */       this.uriMap.put("http://www.oasis-open.org/committees/entity/release/1.0/catalog.rng", url.toString());
/*     */     }
/*     */     
/*  87 */     url = getClass().getResource("/com/sun/org/apache/xml/internal/resolver/etc/catalog.xsd");
/*  88 */     if (url != null) {
/*  89 */       this.uriMap.put("http://www.oasis-open.org/committees/entity/release/1.0/catalog.xsd", url.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String publicId, String systemId) {
/*  95 */     String resolved = null;
/*     */     
/*  97 */     if (systemId != null && this.systemMap.containsKey(systemId)) {
/*  98 */       resolved = this.systemMap.get(systemId);
/*  99 */     } else if (publicId != null && this.publicMap.containsKey(publicId)) {
/* 100 */       resolved = this.publicMap.get(publicId);
/*     */     } 
/*     */     
/* 103 */     if (resolved != null) {
/*     */       try {
/* 105 */         InputSource iSource = new InputSource(resolved);
/* 106 */         iSource.setPublicId(publicId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 119 */         URL url = new URL(resolved);
/* 120 */         InputStream iStream = url.openStream();
/* 121 */         iSource.setByteStream(iStream);
/*     */         
/* 123 */         return iSource;
/* 124 */       } catch (Exception e) {
/*     */         
/* 126 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source resolve(String href, String base) throws TransformerException {
/* 137 */     String uri = href;
/* 138 */     String fragment = null;
/* 139 */     int hashPos = href.indexOf("#");
/* 140 */     if (hashPos >= 0) {
/* 141 */       uri = href.substring(0, hashPos);
/* 142 */       fragment = href.substring(hashPos + 1);
/*     */     } 
/*     */     
/* 145 */     String result = null;
/* 146 */     if (href != null && this.uriMap.containsKey(href)) {
/* 147 */       result = this.uriMap.get(href);
/*     */     }
/*     */     
/* 150 */     if (result == null) {
/*     */       try {
/* 152 */         URL url = null;
/*     */         
/* 154 */         if (base == null) {
/* 155 */           url = new URL(uri);
/* 156 */           result = url.toString();
/*     */         } else {
/* 158 */           URL baseURL = new URL(base);
/* 159 */           url = (href.length() == 0) ? baseURL : new URL(baseURL, uri);
/* 160 */           result = url.toString();
/*     */         } 
/* 162 */       } catch (MalformedURLException mue) {
/*     */         
/* 164 */         String absBase = makeAbsolute(base);
/* 165 */         if (!absBase.equals(base))
/*     */         {
/* 167 */           return resolve(href, absBase);
/*     */         }
/* 169 */         throw new TransformerException("Malformed URL " + href + "(base " + base + ")", mue);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     SAXSource source = new SAXSource();
/* 177 */     source.setInputSource(new InputSource(result));
/* 178 */     return source;
/*     */   }
/*     */ 
/*     */   
/*     */   private String makeAbsolute(String uri) {
/* 183 */     if (uri == null) {
/* 184 */       uri = "";
/*     */     }
/*     */     
/*     */     try {
/* 188 */       URL url = new URL(uri);
/* 189 */       return url.toString();
/* 190 */     } catch (MalformedURLException mue) {
/*     */       try {
/* 192 */         URL fileURL = FileURL.makeURL(uri);
/* 193 */         return fileURL.toString();
/* 194 */       } catch (MalformedURLException mue2) {
/*     */         
/* 196 */         return uri;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/resolver/helpers/BootstrapResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */