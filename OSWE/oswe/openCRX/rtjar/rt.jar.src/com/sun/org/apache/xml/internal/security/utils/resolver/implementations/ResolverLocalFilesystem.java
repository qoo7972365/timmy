/*     */ package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import java.io.FileInputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverLocalFilesystem
/*     */   extends ResourceResolverSpi
/*     */ {
/*  39 */   private static final int FILE_URI_LENGTH = "file:/".length();
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static Logger log = Logger.getLogger(ResolverLocalFilesystem.class.getName());
/*     */ 
/*     */   
/*     */   public boolean engineIsThreadSafe() {
/*  47 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignatureInput engineResolveURI(ResourceResolverContext paramResourceResolverContext) throws ResourceResolverException {
/*     */     try {
/*  58 */       URI uRI = getNewURI(paramResourceResolverContext.uriToResolve, paramResourceResolverContext.baseUri);
/*     */ 
/*     */       
/*  61 */       String str = translateUriToFilename(uRI.toString());
/*  62 */       FileInputStream fileInputStream = new FileInputStream(str);
/*  63 */       XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(fileInputStream);
/*     */       
/*  65 */       xMLSignatureInput.setSourceURI(uRI.toString());
/*     */       
/*  67 */       return xMLSignatureInput;
/*  68 */     } catch (Exception exception) {
/*  69 */       throw new ResourceResolverException("generic.EmptyMessage", exception, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
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
/*     */   private static String translateUriToFilename(String paramString) {
/*  81 */     String str = paramString.substring(FILE_URI_LENGTH);
/*     */     
/*  83 */     if (str.indexOf("%20") > -1) {
/*  84 */       int i = 0;
/*  85 */       int j = 0;
/*  86 */       StringBuilder stringBuilder = new StringBuilder(str.length());
/*     */       while (true) {
/*  88 */         j = str.indexOf("%20", i);
/*  89 */         if (j == -1) {
/*  90 */           stringBuilder.append(str.substring(i));
/*     */         } else {
/*  92 */           stringBuilder.append(str.substring(i, j));
/*  93 */           stringBuilder.append(' ');
/*  94 */           i = j + 3;
/*     */         } 
/*  96 */         if (j == -1) {
/*  97 */           str = stringBuilder.toString(); break;
/*     */         } 
/*     */       } 
/* 100 */     }  if (str.charAt(1) == ':')
/*     */     {
/* 102 */       return str;
/*     */     }
/*     */     
/* 105 */     return "/" + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineCanResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 112 */     if (paramResourceResolverContext.uriToResolve == null) {
/* 113 */       return false;
/*     */     }
/*     */     
/* 116 */     if (paramResourceResolverContext.uriToResolve.equals("") || paramResourceResolverContext.uriToResolve.charAt(0) == '#' || paramResourceResolverContext.uriToResolve
/* 117 */       .startsWith("http:")) {
/* 118 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 122 */       if (log.isLoggable(Level.FINE)) {
/* 123 */         log.log(Level.FINE, "I was asked whether I can resolve " + paramResourceResolverContext.uriToResolve);
/*     */       }
/*     */       
/* 126 */       if (paramResourceResolverContext.uriToResolve.startsWith("file:") || paramResourceResolverContext.baseUri.startsWith("file:")) {
/* 127 */         if (log.isLoggable(Level.FINE)) {
/* 128 */           log.log(Level.FINE, "I state that I can resolve " + paramResourceResolverContext.uriToResolve);
/*     */         }
/* 130 */         return true;
/*     */       } 
/* 132 */     } catch (Exception exception) {
/* 133 */       if (log.isLoggable(Level.FINE)) {
/* 134 */         log.log(Level.FINE, exception.getMessage(), exception);
/*     */       }
/*     */     } 
/*     */     
/* 138 */     if (log.isLoggable(Level.FINE)) {
/* 139 */       log.log(Level.FINE, "But I can't");
/*     */     }
/*     */     
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   private static URI getNewURI(String paramString1, String paramString2) throws URISyntaxException {
/* 146 */     URI uRI = null;
/* 147 */     if (paramString2 == null || "".equals(paramString2)) {
/* 148 */       uRI = new URI(paramString1);
/*     */     } else {
/* 150 */       uRI = (new URI(paramString2)).resolve(paramString1);
/*     */     } 
/*     */ 
/*     */     
/* 154 */     if (uRI.getFragment() != null) {
/* 155 */       return new URI(uRI
/* 156 */           .getScheme(), uRI.getSchemeSpecificPart(), null);
/*     */     }
/*     */     
/* 159 */     return uRI;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/implementations/ResolverLocalFilesystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */