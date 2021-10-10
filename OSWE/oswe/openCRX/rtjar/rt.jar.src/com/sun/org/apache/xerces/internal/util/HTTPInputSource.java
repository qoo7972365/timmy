/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HTTPInputSource
/*     */   extends XMLInputSource
/*     */ {
/*     */   protected boolean fFollowRedirects = true;
/*  54 */   protected Map<String, String> fHTTPRequestProperties = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HTTPInputSource(String publicId, String systemId, String baseSystemId) {
/*  76 */     super(publicId, systemId, baseSystemId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HTTPInputSource(XMLResourceIdentifier resourceIdentifier) {
/*  87 */     super(resourceIdentifier);
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
/*     */   public HTTPInputSource(String publicId, String systemId, String baseSystemId, InputStream byteStream, String encoding) {
/* 107 */     super(publicId, systemId, baseSystemId, byteStream, encoding);
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
/*     */   public HTTPInputSource(String publicId, String systemId, String baseSystemId, Reader charStream, String encoding) {
/* 128 */     super(publicId, systemId, baseSystemId, charStream, encoding);
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
/*     */   public boolean getFollowHTTPRedirects() {
/* 140 */     return this.fFollowRedirects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFollowHTTPRedirects(boolean followRedirects) {
/* 149 */     this.fFollowRedirects = followRedirects;
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
/*     */   public String getHTTPRequestProperty(String key) {
/* 162 */     return this.fHTTPRequestProperties.get(key);
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
/*     */   public Iterator<Map.Entry<String, String>> getHTTPRequestProperties() {
/* 176 */     return this.fHTTPRequestProperties.entrySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHTTPRequestProperty(String key, String value) {
/* 187 */     if (value != null) {
/* 188 */       this.fHTTPRequestProperties.put(key, value);
/*     */     } else {
/*     */       
/* 191 */       this.fHTTPRequestProperties.remove(key);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/util/HTTPInputSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */