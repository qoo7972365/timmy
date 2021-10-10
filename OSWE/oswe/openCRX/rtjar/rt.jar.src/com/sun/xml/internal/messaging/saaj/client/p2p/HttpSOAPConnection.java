/*     */ package com.sun.xml.internal.messaging.saaj.client.p2p;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.util.Base64;
/*     */ import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.internal.messaging.saaj.util.ParseUtil;
/*     */ import com.sun.xml.internal.messaging.saaj.util.SAAJUtil;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPConnection;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpSOAPConnection
/*     */   extends SOAPConnection
/*     */ {
/*  52 */   public static final String vmVendor = SAAJUtil.getSystemProperty("java.vendor.url");
/*     */   private static final String sunVmVendor = "http://java.sun.com/";
/*     */   private static final String ibmVmVendor = "http://www.ibm.com/";
/*  55 */   private static final boolean isSunVM = "http://java.sun.com/".equals(vmVendor);
/*  56 */   private static final boolean isIBMVM = "http://www.ibm.com/".equals(vmVendor);
/*     */   
/*     */   private static final String JAXM_URLENDPOINT = "javax.xml.messaging.URLEndpoint";
/*     */   
/*  60 */   protected static final Logger log = Logger.getLogger("com.sun.xml.internal.messaging.saaj.client.p2p", "com.sun.xml.internal.messaging.saaj.client.p2p.LocalStrings");
/*     */ 
/*     */ 
/*     */   
/*  64 */   MessageFactory messageFactory = null;
/*     */   
/*     */   boolean closed = false;
/*     */   private static final String SSL_PKG;
/*     */   
/*     */   public HttpSOAPConnection() throws SOAPException {
/*     */     try {
/*  71 */       this.messageFactory = MessageFactory.newInstance("Dynamic Protocol");
/*  72 */     } catch (NoSuchMethodError ex) {
/*     */       
/*  74 */       this.messageFactory = MessageFactory.newInstance();
/*  75 */     } catch (Exception ex) {
/*  76 */       log.log(Level.SEVERE, "SAAJ0001.p2p.cannot.create.msg.factory", ex);
/*  77 */       throw new SOAPExceptionImpl("Unable to create message factory", ex);
/*     */     } 
/*     */   }
/*     */   private static final String SSL_PROVIDER; private static final int dL = 0;
/*     */   public void close() throws SOAPException {
/*  82 */     if (this.closed) {
/*  83 */       log.severe("SAAJ0002.p2p.close.already.closed.conn");
/*  84 */       throw new SOAPExceptionImpl("Connection already closed");
/*     */     } 
/*     */     
/*  87 */     this.messageFactory = null;
/*  88 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage call(SOAPMessage message, Object endPoint) throws SOAPException {
/*  93 */     if (this.closed) {
/*  94 */       log.severe("SAAJ0003.p2p.call.already.closed.conn");
/*  95 */       throw new SOAPExceptionImpl("Connection is closed");
/*     */     } 
/*     */     
/*  98 */     Class<?> urlEndpointClass = null;
/*  99 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */     try {
/* 101 */       if (loader != null) {
/* 102 */         urlEndpointClass = loader.loadClass("javax.xml.messaging.URLEndpoint");
/*     */       } else {
/* 104 */         urlEndpointClass = Class.forName("javax.xml.messaging.URLEndpoint");
/*     */       } 
/* 106 */     } catch (ClassNotFoundException ex) {
/*     */       
/* 108 */       if (log.isLoggable(Level.FINEST)) {
/* 109 */         log.finest("SAAJ0090.p2p.endpoint.available.only.for.JAXM");
/*     */       }
/*     */     } 
/* 112 */     if (urlEndpointClass != null && 
/* 113 */       urlEndpointClass.isInstance(endPoint)) {
/* 114 */       String url = null;
/*     */       
/*     */       try {
/* 117 */         Method m = urlEndpointClass.getMethod("getURL", (Class[])null);
/* 118 */         url = (String)m.invoke(endPoint, (Object[])null);
/* 119 */       } catch (Exception ex) {
/*     */         
/* 121 */         log.log(Level.SEVERE, "SAAJ0004.p2p.internal.err", ex);
/* 122 */         throw new SOAPExceptionImpl("Internal error: " + ex
/* 123 */             .getMessage());
/*     */       } 
/*     */       try {
/* 126 */         endPoint = new URL(url);
/* 127 */       } catch (MalformedURLException mex) {
/* 128 */         log.log(Level.SEVERE, "SAAJ0005.p2p.", mex);
/* 129 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 134 */     if (endPoint instanceof String) {
/*     */       try {
/* 136 */         endPoint = new URL((String)endPoint);
/* 137 */       } catch (MalformedURLException mex) {
/* 138 */         log.log(Level.SEVERE, "SAAJ0006.p2p.bad.URL", mex);
/* 139 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 143 */     if (endPoint instanceof URL)
/*     */       try {
/* 145 */         SOAPMessage response = post(message, (URL)endPoint);
/* 146 */         return response;
/* 147 */       } catch (Exception ex) {
/*     */         
/* 149 */         throw new SOAPExceptionImpl(ex);
/*     */       }  
/* 151 */     log.severe("SAAJ0007.p2p.bad.endPoint.type");
/* 152 */     throw new SOAPExceptionImpl("Bad endPoint type " + endPoint);
/*     */   }
/*     */ 
/*     */   
/*     */   SOAPMessage post(SOAPMessage message, URL endPoint) throws SOAPException, IOException {
/* 157 */     boolean isFailure = false;
/*     */     
/* 159 */     URL url = null;
/* 160 */     HttpURLConnection httpConnection = null;
/*     */     
/* 162 */     int responseCode = 0;
/*     */     try {
/* 164 */       if (endPoint.getProtocol().equals("https"))
/*     */       {
/* 166 */         initHttps();
/*     */       }
/* 168 */       URI uri = new URI(endPoint.toString());
/* 169 */       String userInfo = uri.getRawUserInfo();
/*     */       
/* 171 */       url = endPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 178 */       if (!url.getProtocol().equalsIgnoreCase("http") && 
/* 179 */         !url.getProtocol().equalsIgnoreCase("https")) {
/* 180 */         log.severe("SAAJ0052.p2p.protocol.mustbe.http.or.https");
/* 181 */         throw new IllegalArgumentException("Protocol " + url
/*     */             
/* 183 */             .getProtocol() + " not supported in URL " + url);
/*     */       } 
/*     */ 
/*     */       
/* 187 */       httpConnection = createConnection(url);
/*     */       
/* 189 */       httpConnection.setRequestMethod("POST");
/*     */       
/* 191 */       httpConnection.setDoOutput(true);
/* 192 */       httpConnection.setDoInput(true);
/* 193 */       httpConnection.setUseCaches(false);
/* 194 */       httpConnection.setInstanceFollowRedirects(true);
/*     */       
/* 196 */       if (message.saveRequired()) {
/* 197 */         message.saveChanges();
/*     */       }
/* 199 */       MimeHeaders headers = message.getMimeHeaders();
/*     */       
/* 201 */       Iterator<MimeHeader> it = headers.getAllHeaders();
/* 202 */       boolean hasAuth = false;
/* 203 */       while (it.hasNext()) {
/* 204 */         MimeHeader header = it.next();
/*     */         
/* 206 */         String[] values = headers.getHeader(header.getName());
/* 207 */         if (values.length == 1) {
/* 208 */           httpConnection.setRequestProperty(header
/* 209 */               .getName(), header
/* 210 */               .getValue());
/*     */         } else {
/* 212 */           StringBuffer concat = new StringBuffer();
/* 213 */           int i = 0;
/* 214 */           while (i < values.length) {
/* 215 */             if (i != 0)
/* 216 */               concat.append(','); 
/* 217 */             concat.append(values[i]);
/* 218 */             i++;
/*     */           } 
/*     */           
/* 221 */           httpConnection.setRequestProperty(header
/* 222 */               .getName(), concat
/* 223 */               .toString());
/*     */         } 
/*     */         
/* 226 */         if ("Authorization".equals(header.getName())) {
/* 227 */           hasAuth = true;
/* 228 */           if (log.isLoggable(Level.FINE)) {
/* 229 */             log.fine("SAAJ0091.p2p.https.auth.in.POST.true");
/*     */           }
/*     */         } 
/*     */       } 
/* 233 */       if (!hasAuth && userInfo != null) {
/* 234 */         initAuthUserInfo(httpConnection, userInfo);
/*     */       }
/*     */       
/* 237 */       OutputStream out = httpConnection.getOutputStream();
/*     */       try {
/* 239 */         message.writeTo(out);
/* 240 */         out.flush();
/*     */       } finally {
/* 242 */         out.close();
/*     */       } 
/*     */       
/* 245 */       httpConnection.connect();
/*     */ 
/*     */       
/*     */       try {
/* 249 */         responseCode = httpConnection.getResponseCode();
/*     */ 
/*     */         
/* 252 */         if (responseCode == 500) {
/* 253 */           isFailure = true;
/*     */ 
/*     */         
/*     */         }
/* 257 */         else if (responseCode / 100 != 2) {
/* 258 */           log.log(Level.SEVERE, "SAAJ0008.p2p.bad.response", (Object[])new String[] { httpConnection
/*     */                 
/* 260 */                 .getResponseMessage() });
/* 261 */           throw new SOAPExceptionImpl("Bad response: (" + responseCode + httpConnection
/*     */ 
/*     */               
/* 264 */               .getResponseMessage());
/*     */         }
/*     */       
/* 267 */       } catch (IOException e) {
/*     */         
/* 269 */         responseCode = httpConnection.getResponseCode();
/* 270 */         if (responseCode == 500) {
/* 271 */           isFailure = true;
/*     */         } else {
/* 273 */           throw e;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 278 */     } catch (SOAPException ex) {
/* 279 */       throw ex;
/* 280 */     } catch (Exception ex) {
/* 281 */       log.severe("SAAJ0009.p2p.msg.send.failed");
/* 282 */       throw new SOAPExceptionImpl("Message send failed", ex);
/*     */     } 
/*     */     
/* 285 */     SOAPMessage response = null;
/* 286 */     InputStream httpIn = null;
/* 287 */     if (responseCode == 200 || isFailure) {
/*     */       try {
/* 289 */         MimeHeaders headers = new MimeHeaders();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 295 */         int i = 1;
/*     */         
/*     */         while (true) {
/* 298 */           String key = httpConnection.getHeaderFieldKey(i);
/* 299 */           String value = httpConnection.getHeaderField(i);
/*     */           
/* 301 */           if (key == null && value == null) {
/*     */             break;
/*     */           }
/* 304 */           if (key != null) {
/* 305 */             StringTokenizer values = new StringTokenizer(value, ",");
/*     */             
/* 307 */             while (values.hasMoreTokens())
/* 308 */               headers.addHeader(key, values.nextToken().trim()); 
/*     */           } 
/* 310 */           i++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 316 */         httpIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
/*     */         
/* 318 */         byte[] bytes = readFully(httpIn);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 323 */         int length = (httpConnection.getContentLength() == -1) ? bytes.length : httpConnection.getContentLength();
/*     */ 
/*     */ 
/*     */         
/* 327 */         if (length == 0) {
/* 328 */           response = null;
/* 329 */           log.warning("SAAJ0014.p2p.content.zero");
/*     */         } else {
/* 331 */           ByteInputStream in = new ByteInputStream(bytes, length);
/* 332 */           response = this.messageFactory.createMessage(headers, (InputStream)in);
/*     */         }
/*     */       
/* 335 */       } catch (SOAPException ex) {
/* 336 */         throw ex;
/* 337 */       } catch (Exception ex) {
/* 338 */         log.log(Level.SEVERE, "SAAJ0010.p2p.cannot.read.resp", ex);
/* 339 */         throw new SOAPExceptionImpl("Unable to read response: " + ex
/* 340 */             .getMessage());
/*     */       } finally {
/* 342 */         if (httpIn != null)
/* 343 */           httpIn.close(); 
/* 344 */         httpConnection.disconnect();
/*     */       } 
/*     */     }
/* 347 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage get(Object endPoint) throws SOAPException {
/* 354 */     if (this.closed) {
/* 355 */       log.severe("SAAJ0011.p2p.get.already.closed.conn");
/* 356 */       throw new SOAPExceptionImpl("Connection is closed");
/*     */     } 
/* 358 */     Class<?> urlEndpointClass = null;
/*     */     
/*     */     try {
/* 361 */       urlEndpointClass = Class.forName("javax.xml.messaging.URLEndpoint");
/* 362 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 366 */     if (urlEndpointClass != null && 
/* 367 */       urlEndpointClass.isInstance(endPoint)) {
/* 368 */       String url = null;
/*     */       
/*     */       try {
/* 371 */         Method m = urlEndpointClass.getMethod("getURL", (Class[])null);
/* 372 */         url = (String)m.invoke(endPoint, (Object[])null);
/* 373 */       } catch (Exception ex) {
/* 374 */         log.severe("SAAJ0004.p2p.internal.err");
/* 375 */         throw new SOAPExceptionImpl("Internal error: " + ex
/* 376 */             .getMessage());
/*     */       } 
/*     */       try {
/* 379 */         endPoint = new URL(url);
/* 380 */       } catch (MalformedURLException mex) {
/* 381 */         log.severe("SAAJ0005.p2p.");
/* 382 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 387 */     if (endPoint instanceof String) {
/*     */       try {
/* 389 */         endPoint = new URL((String)endPoint);
/* 390 */       } catch (MalformedURLException mex) {
/* 391 */         log.severe("SAAJ0006.p2p.bad.URL");
/* 392 */         throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 396 */     if (endPoint instanceof URL)
/*     */       try {
/* 398 */         SOAPMessage response = doGet((URL)endPoint);
/* 399 */         return response;
/* 400 */       } catch (Exception ex) {
/* 401 */         throw new SOAPExceptionImpl(ex);
/*     */       }  
/* 403 */     throw new SOAPExceptionImpl("Bad endPoint type " + endPoint);
/*     */   }
/*     */   
/*     */   SOAPMessage doGet(URL endPoint) throws SOAPException, IOException {
/* 407 */     boolean isFailure = false;
/*     */     
/* 409 */     URL url = null;
/* 410 */     HttpURLConnection httpConnection = null;
/*     */     
/* 412 */     int responseCode = 0;
/*     */     
/*     */     try {
/* 415 */       if (endPoint.getProtocol().equals("https")) {
/* 416 */         initHttps();
/*     */       }
/* 418 */       URI uri = new URI(endPoint.toString());
/* 419 */       String userInfo = uri.getRawUserInfo();
/*     */       
/* 421 */       url = endPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 428 */       if (!url.getProtocol().equalsIgnoreCase("http") && 
/* 429 */         !url.getProtocol().equalsIgnoreCase("https")) {
/* 430 */         log.severe("SAAJ0052.p2p.protocol.mustbe.http.or.https");
/* 431 */         throw new IllegalArgumentException("Protocol " + url
/*     */             
/* 433 */             .getProtocol() + " not supported in URL " + url);
/*     */       } 
/*     */ 
/*     */       
/* 437 */       httpConnection = createConnection(url);
/*     */       
/* 439 */       httpConnection.setRequestMethod("GET");
/*     */       
/* 441 */       httpConnection.setDoOutput(true);
/* 442 */       httpConnection.setDoInput(true);
/* 443 */       httpConnection.setUseCaches(false);
/* 444 */       HttpURLConnection.setFollowRedirects(true);
/*     */       
/* 446 */       httpConnection.connect();
/*     */ 
/*     */       
/*     */       try {
/* 450 */         responseCode = httpConnection.getResponseCode();
/*     */ 
/*     */         
/* 453 */         if (responseCode == 500) {
/* 454 */           isFailure = true;
/* 455 */         } else if (responseCode / 100 != 2) {
/* 456 */           log.log(Level.SEVERE, "SAAJ0008.p2p.bad.response", (Object[])new String[] { httpConnection
/*     */                 
/* 458 */                 .getResponseMessage() });
/* 459 */           throw new SOAPExceptionImpl("Bad response: (" + responseCode + httpConnection
/*     */ 
/*     */               
/* 462 */               .getResponseMessage());
/*     */         }
/*     */       
/* 465 */       } catch (IOException e) {
/*     */         
/* 467 */         responseCode = httpConnection.getResponseCode();
/* 468 */         if (responseCode == 500) {
/* 469 */           isFailure = true;
/*     */         } else {
/* 471 */           throw e;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 476 */     } catch (SOAPException ex) {
/* 477 */       throw ex;
/* 478 */     } catch (Exception ex) {
/* 479 */       log.severe("SAAJ0012.p2p.get.failed");
/* 480 */       throw new SOAPExceptionImpl("Get failed", ex);
/*     */     } 
/*     */     
/* 483 */     SOAPMessage response = null;
/* 484 */     InputStream httpIn = null;
/* 485 */     if (responseCode == 200 || isFailure) {
/*     */       try {
/* 487 */         MimeHeaders headers = new MimeHeaders();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 493 */         int i = 1;
/*     */         
/*     */         while (true) {
/* 496 */           String key = httpConnection.getHeaderFieldKey(i);
/* 497 */           String value = httpConnection.getHeaderField(i);
/*     */           
/* 499 */           if (key == null && value == null) {
/*     */             break;
/*     */           }
/* 502 */           if (key != null) {
/* 503 */             StringTokenizer values = new StringTokenizer(value, ",");
/*     */             
/* 505 */             while (values.hasMoreTokens())
/* 506 */               headers.addHeader(key, values.nextToken().trim()); 
/*     */           } 
/* 508 */           i++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 514 */         httpIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 521 */         if (httpIn == null || httpConnection
/* 522 */           .getContentLength() == 0 || httpIn
/* 523 */           .available() == 0) {
/* 524 */           response = null;
/* 525 */           log.warning("SAAJ0014.p2p.content.zero");
/*     */         } else {
/* 527 */           response = this.messageFactory.createMessage(headers, httpIn);
/*     */         }
/*     */       
/* 530 */       } catch (SOAPException ex) {
/* 531 */         throw ex;
/* 532 */       } catch (Exception ex) {
/* 533 */         log.log(Level.SEVERE, "SAAJ0010.p2p.cannot.read.resp", ex);
/*     */ 
/*     */         
/* 536 */         throw new SOAPExceptionImpl("Unable to read response: " + ex
/* 537 */             .getMessage());
/*     */       } finally {
/* 539 */         if (httpIn != null)
/* 540 */           httpIn.close(); 
/* 541 */         httpConnection.disconnect();
/*     */       } 
/*     */     }
/* 544 */     return response;
/*     */   }
/*     */   
/*     */   private byte[] readFully(InputStream istream) throws IOException {
/* 548 */     ByteArrayOutputStream bout = new ByteArrayOutputStream();
/* 549 */     byte[] buf = new byte[1024];
/* 550 */     int num = 0;
/*     */     
/* 552 */     while ((num = istream.read(buf)) != -1) {
/* 553 */       bout.write(buf, 0, num);
/*     */     }
/*     */     
/* 556 */     byte[] ret = bout.toByteArray();
/*     */     
/* 558 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 568 */     if (isIBMVM) {
/* 569 */       SSL_PKG = "com.ibm.net.ssl.internal.www.protocol";
/* 570 */       SSL_PROVIDER = "com.ibm.net.ssl.internal.ssl.Provider";
/*     */     } else {
/*     */       
/* 573 */       SSL_PKG = "com.sun.net.ssl.internal.www.protocol";
/* 574 */       SSL_PROVIDER = "com.sun.net.ssl.internal.ssl.Provider";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initHttps() {
/* 580 */     String pkgs = SAAJUtil.getSystemProperty("java.protocol.handler.pkgs");
/* 581 */     if (log.isLoggable(Level.FINE)) {
/* 582 */       log.log(Level.FINE, "SAAJ0053.p2p.providers", (Object[])new String[] { pkgs });
/*     */     }
/* 584 */     if (pkgs == null || pkgs.indexOf(SSL_PKG) < 0) {
/* 585 */       if (pkgs == null) {
/* 586 */         pkgs = SSL_PKG;
/*     */       } else {
/* 588 */         pkgs = pkgs + "|" + SSL_PKG;
/* 589 */       }  System.setProperty("java.protocol.handler.pkgs", pkgs);
/* 590 */       if (log.isLoggable(Level.FINE)) {
/* 591 */         log.log(Level.FINE, "SAAJ0054.p2p.set.providers", (Object[])new String[] { pkgs });
/*     */       }
/*     */       try {
/* 594 */         Class<?> c = Class.forName(SSL_PROVIDER);
/* 595 */         Provider p = (Provider)c.newInstance();
/* 596 */         Security.addProvider(p);
/* 597 */         if (log.isLoggable(Level.FINE)) {
/* 598 */           log.log(Level.FINE, "SAAJ0055.p2p.added.ssl.provider", (Object[])new String[] { SSL_PROVIDER });
/*     */         
/*     */         }
/*     */       }
/* 602 */       catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initAuthUserInfo(HttpURLConnection conn, String userInfo) {
/* 611 */     if (userInfo != null) {
/*     */       String user, password;
/* 613 */       int delimiter = userInfo.indexOf(':');
/* 614 */       if (delimiter == -1) {
/* 615 */         user = ParseUtil.decode(userInfo);
/* 616 */         password = null;
/*     */       } else {
/* 618 */         user = ParseUtil.decode(userInfo.substring(0, delimiter++));
/* 619 */         password = ParseUtil.decode(userInfo.substring(delimiter));
/*     */       } 
/*     */       
/* 622 */       String plain = user + ":";
/* 623 */       byte[] nameBytes = plain.getBytes();
/* 624 */       byte[] passwdBytes = password.getBytes();
/*     */ 
/*     */       
/* 627 */       byte[] concat = new byte[nameBytes.length + passwdBytes.length];
/*     */       
/* 629 */       System.arraycopy(nameBytes, 0, concat, 0, nameBytes.length);
/* 630 */       System.arraycopy(passwdBytes, 0, concat, nameBytes.length, passwdBytes.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 636 */       String auth = "Basic " + new String(Base64.encode(concat));
/* 637 */       conn.setRequestProperty("Authorization", auth);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void d(String s) {
/* 645 */     log.log(Level.SEVERE, "SAAJ0013.p2p.HttpSOAPConnection", (Object[])new String[] { s });
/*     */ 
/*     */     
/* 648 */     System.err.println("HttpSOAPConnection: " + s);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpURLConnection createConnection(URL endpoint) throws IOException {
/* 653 */     return (HttpURLConnection)endpoint.openConnection();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/client/p2p/HttpSOAPConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */