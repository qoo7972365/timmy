/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JAXWSUtils
/*     */ {
/*     */   public static String getUUID() {
/*  46 */     return UUID.randomUUID().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFileOrURLName(String fileOrURL) {
/*     */     try {
/*  54 */       return escapeSpace((new URL(fileOrURL)).toExternalForm());
/*  55 */     } catch (MalformedURLException e) {
/*  56 */       return (new File(fileOrURL)).getCanonicalFile().toURL().toExternalForm();
/*     */     }
/*  58 */     catch (Exception e) {
/*     */       
/*  60 */       return fileOrURL;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL getFileOrURL(String fileOrURL) throws IOException {
/*     */     try {
/*  66 */       URL url = new URL(fileOrURL);
/*  67 */       String scheme = String.valueOf(url.getProtocol()).toLowerCase();
/*  68 */       if (scheme.equals("http") || scheme.equals("https"))
/*  69 */         return new URL(url.toURI().toASCIIString()); 
/*  70 */       return url;
/*  71 */     } catch (URISyntaxException e) {
/*  72 */       return (new File(fileOrURL)).toURL();
/*  73 */     } catch (MalformedURLException e) {
/*  74 */       return (new File(fileOrURL)).toURL();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL getEncodedURL(String urlStr) throws MalformedURLException {
/*  79 */     URL url = new URL(urlStr);
/*  80 */     String scheme = String.valueOf(url.getProtocol()).toLowerCase();
/*  81 */     if (scheme.equals("http") || scheme.equals("https")) {
/*     */       try {
/*  83 */         return new URL(url.toURI().toASCIIString());
/*  84 */       } catch (URISyntaxException e) {
/*  85 */         MalformedURLException malformedURLException = new MalformedURLException(e.getMessage());
/*  86 */         malformedURLException.initCause(e);
/*  87 */         throw malformedURLException;
/*     */       } 
/*     */     }
/*  90 */     return url;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String escapeSpace(String url) {
/*  95 */     StringBuilder buf = new StringBuilder();
/*  96 */     for (int i = 0; i < url.length(); i++) {
/*     */       
/*  98 */       if (url.charAt(i) == ' ') {
/*  99 */         buf.append("%20");
/*     */       } else {
/* 101 */         buf.append(url.charAt(i));
/*     */       } 
/* 103 */     }  return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String absolutize(String name) {
/*     */     try {
/* 110 */       URL baseURL = (new File(".")).getCanonicalFile().toURL();
/* 111 */       return (new URL(baseURL, name)).toExternalForm();
/* 112 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 115 */       return name;
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
/*     */   public static void checkAbsoluteness(String systemId) {
/*     */     try {
/* 128 */       new URL(systemId);
/* 129 */     } catch (MalformedURLException mue) {
/*     */       try {
/* 131 */         new URI(systemId);
/* 132 */       } catch (URISyntaxException e) {
/* 133 */         throw new IllegalArgumentException("system ID '" + systemId + "' isn't absolute", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchQNames(QName target, QName pattern) {
/* 144 */     if (target == null || pattern == null)
/*     */     {
/* 146 */       return false;
/*     */     }
/* 148 */     if (pattern.getNamespaceURI().equals(target.getNamespaceURI())) {
/* 149 */       String regex = pattern.getLocalPart().replaceAll("\\*", ".*");
/* 150 */       return Pattern.matches(regex, target.getLocalPart());
/*     */     } 
/* 152 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/JAXWSUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */