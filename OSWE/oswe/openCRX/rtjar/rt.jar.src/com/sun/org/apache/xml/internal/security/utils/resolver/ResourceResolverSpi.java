/*     */ package com.sun.org.apache.xml.internal.security.utils.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ResourceResolverSpi
/*     */ {
/*  40 */   private static Logger log = Logger.getLogger(ResourceResolverSpi.class.getName());
/*     */ 
/*     */   
/*  43 */   protected Map<String, String> properties = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected final boolean secureValidation = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public XMLSignatureInput engineResolve(Attr paramAttr, String paramString) throws ResourceResolverException {
/*  68 */     throw new UnsupportedOperationException();
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
/*     */   public XMLSignatureInput engineResolveURI(ResourceResolverContext paramResourceResolverContext) throws ResourceResolverException {
/*  83 */     return engineResolve(paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineSetProperty(String paramString1, String paramString2) {
/*  93 */     if (this.properties == null) {
/*  94 */       this.properties = new HashMap<>();
/*     */     }
/*  96 */     this.properties.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String engineGetProperty(String paramString) {
/* 106 */     if (this.properties == null) {
/* 107 */       return null;
/*     */     }
/* 109 */     return this.properties.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void engineAddProperies(Map<String, String> paramMap) {
/* 117 */     if (paramMap != null && !paramMap.isEmpty()) {
/* 118 */       if (this.properties == null) {
/* 119 */         this.properties = new HashMap<>();
/*     */       }
/* 121 */       this.properties.putAll(paramMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineIsThreadSafe() {
/* 132 */     return false;
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
/*     */   @Deprecated
/*     */   public boolean engineCanResolve(Attr paramAttr, String paramString) {
/* 148 */     throw new UnsupportedOperationException();
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
/*     */   public boolean engineCanResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 163 */     return engineCanResolve(paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] engineGetPropertyKeys() {
/* 172 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean understandsProperty(String paramString) {
/* 182 */     String[] arrayOfString = engineGetPropertyKeys();
/*     */     
/* 184 */     if (arrayOfString != null) {
/* 185 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 186 */         if (arrayOfString[b].equals(paramString)) {
/* 187 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 192 */     return false;
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
/*     */   public static String fixURI(String paramString) {
/* 206 */     paramString = paramString.replace(File.separatorChar, '/');
/*     */     
/* 208 */     if (paramString.length() >= 4) {
/*     */ 
/*     */       
/* 211 */       char c1 = Character.toUpperCase(paramString.charAt(0));
/* 212 */       char c2 = paramString.charAt(1);
/* 213 */       char c3 = paramString.charAt(2);
/* 214 */       char c4 = paramString.charAt(3);
/* 215 */       boolean bool = ('A' <= c1 && c1 <= 'Z' && c2 == ':' && c3 == '/' && c4 != '/') ? true : false;
/*     */ 
/*     */ 
/*     */       
/* 219 */       if (bool && log.isLoggable(Level.FINE)) {
/* 220 */         log.log(Level.FINE, "Found DOS filename: " + paramString);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 225 */     if (paramString.length() >= 2) {
/* 226 */       char c = paramString.charAt(1);
/*     */       
/* 228 */       if (c == ':') {
/* 229 */         char c1 = Character.toUpperCase(paramString.charAt(0));
/*     */         
/* 231 */         if ('A' <= c1 && c1 <= 'Z') {
/* 232 */           paramString = "/" + paramString;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 238 */     return paramString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/ResourceResolverSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */