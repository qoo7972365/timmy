/*     */ package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverDirectHTTP
/*     */   extends ResourceResolverSpi
/*     */ {
/*  66 */   private static Logger log = Logger.getLogger(ResolverDirectHTTP.class.getName());
/*     */ 
/*     */   
/*  69 */   private static final String[] properties = new String[] { "http.proxy.host", "http.proxy.port", "http.proxy.username", "http.proxy.password", "http.basic.username", "http.basic.password" };
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HttpProxyHost = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HttpProxyPort = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int HttpProxyUser = 2;
/*     */ 
/*     */   
/*     */   private static final int HttpProxyPass = 3;
/*     */ 
/*     */   
/*     */   private static final int HttpBasicUser = 4;
/*     */ 
/*     */   
/*     */   private static final int HttpBasicPass = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineIsThreadSafe() {
/*  95 */     return true;
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
/*     */   public XMLSignatureInput engineResolveURI(ResourceResolverContext paramResourceResolverContext) throws ResourceResolverException {
/*     */     try {
/* 114 */       URI uRI = getNewURI(paramResourceResolverContext.uriToResolve, paramResourceResolverContext.baseUri);
/* 115 */       URL uRL = uRI.toURL();
/*     */       
/* 117 */       URLConnection uRLConnection = openConnection(uRL);
/*     */ 
/*     */       
/* 120 */       String str1 = uRLConnection.getHeaderField("WWW-Authenticate");
/*     */       
/* 122 */       if (str1 != null && str1.startsWith("Basic")) {
/*     */ 
/*     */         
/* 125 */         String str3 = engineGetProperty(properties[4]);
/*     */         
/* 127 */         String str4 = engineGetProperty(properties[5]);
/*     */         
/* 129 */         if (str3 != null && str4 != null) {
/* 130 */           uRLConnection = openConnection(uRL);
/*     */           
/* 132 */           String str5 = str3 + ":" + str4;
/* 133 */           String str6 = Base64.encode(str5.getBytes("ISO-8859-1"));
/*     */ 
/*     */           
/* 136 */           uRLConnection.setRequestProperty("Authorization", "Basic " + str6);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 141 */       String str2 = uRLConnection.getHeaderField("Content-Type");
/* 142 */       InputStream inputStream = uRLConnection.getInputStream();
/* 143 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 144 */       byte[] arrayOfByte = new byte[4096];
/* 145 */       int i = 0;
/* 146 */       int j = 0;
/*     */       
/* 148 */       while ((i = inputStream.read(arrayOfByte)) >= 0) {
/* 149 */         byteArrayOutputStream.write(arrayOfByte, 0, i);
/* 150 */         j += i;
/*     */       } 
/*     */       
/* 153 */       if (log.isLoggable(Level.FINE)) {
/* 154 */         log.log(Level.FINE, "Fetched " + j + " bytes from URI " + uRI.toString());
/*     */       }
/*     */       
/* 157 */       XMLSignatureInput xMLSignatureInput = new XMLSignatureInput(byteArrayOutputStream.toByteArray());
/*     */       
/* 159 */       xMLSignatureInput.setSourceURI(uRI.toString());
/* 160 */       xMLSignatureInput.setMIMEType(str2);
/*     */       
/* 162 */       return xMLSignatureInput;
/* 163 */     } catch (URISyntaxException uRISyntaxException) {
/* 164 */       throw new ResourceResolverException("generic.EmptyMessage", uRISyntaxException, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/* 165 */     } catch (MalformedURLException malformedURLException) {
/* 166 */       throw new ResourceResolverException("generic.EmptyMessage", malformedURLException, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/* 167 */     } catch (IOException iOException) {
/* 168 */       throw new ResourceResolverException("generic.EmptyMessage", iOException, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/* 169 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 170 */       throw new ResourceResolverException("generic.EmptyMessage", illegalArgumentException, paramResourceResolverContext.attr, paramResourceResolverContext.baseUri);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private URLConnection openConnection(URL paramURL) throws IOException {
/*     */     URLConnection uRLConnection;
/* 177 */     String str1 = engineGetProperty(properties[0]);
/*     */     
/* 179 */     String str2 = engineGetProperty(properties[1]);
/*     */     
/* 181 */     String str3 = engineGetProperty(properties[2]);
/*     */     
/* 183 */     String str4 = engineGetProperty(properties[3]);
/*     */     
/* 185 */     Proxy proxy = null;
/* 186 */     if (str1 != null && str2 != null) {
/* 187 */       int i = Integer.parseInt(str2);
/* 188 */       proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(str1, i));
/*     */     } 
/*     */ 
/*     */     
/* 192 */     if (proxy != null) {
/* 193 */       uRLConnection = paramURL.openConnection(proxy);
/*     */       
/* 195 */       if (str3 != null && str4 != null) {
/* 196 */         String str5 = str3 + ":" + str4;
/* 197 */         String str6 = "Basic " + Base64.encode(str5.getBytes("ISO-8859-1"));
/*     */         
/* 199 */         uRLConnection.setRequestProperty("Proxy-Authorization", str6);
/*     */       } 
/*     */     } else {
/* 202 */       uRLConnection = paramURL.openConnection();
/*     */     } 
/*     */     
/* 205 */     return uRLConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean engineCanResolveURI(ResourceResolverContext paramResourceResolverContext) {
/* 216 */     if (paramResourceResolverContext.uriToResolve == null) {
/* 217 */       if (log.isLoggable(Level.FINE)) {
/* 218 */         log.log(Level.FINE, "quick fail, uri == null");
/*     */       }
/* 220 */       return false;
/*     */     } 
/*     */     
/* 223 */     if (paramResourceResolverContext.uriToResolve.equals("") || paramResourceResolverContext.uriToResolve.charAt(0) == '#') {
/* 224 */       if (log.isLoggable(Level.FINE)) {
/* 225 */         log.log(Level.FINE, "quick fail for empty URIs and local ones");
/*     */       }
/* 227 */       return false;
/*     */     } 
/*     */     
/* 230 */     if (log.isLoggable(Level.FINE)) {
/* 231 */       log.log(Level.FINE, "I was asked whether I can resolve " + paramResourceResolverContext.uriToResolve);
/*     */     }
/*     */     
/* 234 */     if (paramResourceResolverContext.uriToResolve.startsWith("http:") || (paramResourceResolverContext.baseUri != null && paramResourceResolverContext.baseUri
/* 235 */       .startsWith("http:"))) {
/* 236 */       if (log.isLoggable(Level.FINE)) {
/* 237 */         log.log(Level.FINE, "I state that I can resolve " + paramResourceResolverContext.uriToResolve);
/*     */       }
/* 239 */       return true;
/*     */     } 
/*     */     
/* 242 */     if (log.isLoggable(Level.FINE)) {
/* 243 */       log.log(Level.FINE, "I state that I can't resolve " + paramResourceResolverContext.uriToResolve);
/*     */     }
/*     */     
/* 246 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] engineGetPropertyKeys() {
/* 253 */     return (String[])properties.clone();
/*     */   }
/*     */   
/*     */   private static URI getNewURI(String paramString1, String paramString2) throws URISyntaxException {
/* 257 */     URI uRI = null;
/* 258 */     if (paramString2 == null || "".equals(paramString2)) {
/* 259 */       uRI = new URI(paramString1);
/*     */     } else {
/* 261 */       uRI = (new URI(paramString2)).resolve(paramString1);
/*     */     } 
/*     */ 
/*     */     
/* 265 */     if (uRI.getFragment() != null) {
/* 266 */       return new URI(uRI
/* 267 */           .getScheme(), uRI.getSchemeSpecificPart(), null);
/*     */     }
/*     */     
/* 270 */     return uRI;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/resolver/implementations/ResolverDirectHTTP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */