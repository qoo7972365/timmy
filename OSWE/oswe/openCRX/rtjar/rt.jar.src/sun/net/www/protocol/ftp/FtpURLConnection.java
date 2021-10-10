/*     */ package sun.net.www.protocol.ftp;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.ProxySelector;
/*     */ import java.net.SocketPermission;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.net.ProgressMonitor;
/*     */ import sun.net.ProgressSource;
/*     */ import sun.net.ftp.FtpClient;
/*     */ import sun.net.ftp.FtpLoginException;
/*     */ import sun.net.ftp.FtpProtocolException;
/*     */ import sun.net.util.IPAddressUtil;
/*     */ import sun.net.www.MessageHeader;
/*     */ import sun.net.www.MeteredStream;
/*     */ import sun.net.www.ParseUtil;
/*     */ import sun.net.www.URLConnection;
/*     */ import sun.net.www.protocol.http.HttpURLConnection;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FtpURLConnection
/*     */   extends URLConnection
/*     */ {
/*  86 */   HttpURLConnection http = null;
/*     */   
/*     */   private Proxy instProxy;
/*  89 */   InputStream is = null;
/*  90 */   OutputStream os = null;
/*     */   
/*  92 */   FtpClient ftp = null;
/*     */   
/*     */   Permission permission;
/*     */   
/*     */   String password;
/*     */   String user;
/*     */   String host;
/*     */   String pathname;
/*     */   String filename;
/*     */   String fullpath;
/*     */   int port;
/*     */   static final int NONE = 0;
/*     */   static final int ASCII = 1;
/*     */   static final int BIN = 2;
/*     */   static final int DIR = 3;
/* 107 */   int type = 0;
/*     */ 
/*     */ 
/*     */   
/* 111 */   private int connectTimeout = -1;
/* 112 */   private int readTimeout = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected class FtpInputStream
/*     */     extends FilterInputStream
/*     */   {
/*     */     FtpClient ftp;
/*     */ 
/*     */ 
/*     */     
/*     */     FtpInputStream(FtpClient param1FtpClient, InputStream param1InputStream) {
/* 124 */       super(new BufferedInputStream(param1InputStream));
/* 125 */       this.ftp = param1FtpClient;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 130 */       super.close();
/* 131 */       if (this.ftp != null) {
/* 132 */         this.ftp.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected class FtpOutputStream
/*     */     extends FilterOutputStream
/*     */   {
/*     */     FtpClient ftp;
/*     */ 
/*     */ 
/*     */     
/*     */     FtpOutputStream(FtpClient param1FtpClient, OutputStream param1OutputStream) {
/* 147 */       super(param1OutputStream);
/* 148 */       this.ftp = param1FtpClient;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 153 */       super.close();
/* 154 */       if (this.ftp != null) {
/* 155 */         this.ftp.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static URL checkURL(URL paramURL) throws IllegalArgumentException {
/* 161 */     if (paramURL != null && 
/* 162 */       paramURL.toExternalForm().indexOf('\n') > -1) {
/* 163 */       MalformedURLException malformedURLException = new MalformedURLException("Illegal character in URL");
/* 164 */       throw new IllegalArgumentException(malformedURLException.getMessage(), malformedURLException);
/*     */     } 
/*     */     
/* 167 */     String str = IPAddressUtil.checkAuthority(paramURL);
/* 168 */     if (str != null) {
/* 169 */       MalformedURLException malformedURLException = new MalformedURLException(str);
/* 170 */       throw new IllegalArgumentException(malformedURLException.getMessage(), malformedURLException);
/*     */     } 
/* 172 */     return paramURL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FtpURLConnection(URL paramURL) {
/* 181 */     this(paramURL, (Proxy)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FtpURLConnection(URL paramURL, Proxy paramProxy) {
/* 188 */     super(checkURL(paramURL));
/* 189 */     this.instProxy = paramProxy;
/* 190 */     this.host = paramURL.getHost();
/* 191 */     this.port = paramURL.getPort();
/* 192 */     String str = paramURL.getUserInfo();
/*     */     
/* 194 */     if (str != null) {
/* 195 */       int i = str.indexOf(':');
/* 196 */       if (i == -1) {
/* 197 */         this.user = ParseUtil.decode(str);
/* 198 */         this.password = null;
/*     */       } else {
/* 200 */         this.user = ParseUtil.decode(str.substring(0, i++));
/* 201 */         this.password = ParseUtil.decode(str.substring(i));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setTimeouts() {
/* 207 */     if (this.ftp != null) {
/* 208 */       if (this.connectTimeout >= 0) {
/* 209 */         this.ftp.setConnectTimeout(this.connectTimeout);
/*     */       }
/* 211 */       if (this.readTimeout >= 0) {
/* 212 */         this.ftp.setReadTimeout(this.readTimeout);
/*     */       }
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
/*     */   public synchronized void connect() throws IOException {
/* 226 */     if (this.connected) {
/*     */       return;
/*     */     }
/*     */     
/* 230 */     Proxy proxy = null;
/* 231 */     if (this.instProxy == null) {
/*     */ 
/*     */ 
/*     */       
/* 235 */       ProxySelector proxySelector = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*     */           {
/*     */             public ProxySelector run() {
/* 238 */               return ProxySelector.getDefault();
/*     */             }
/*     */           });
/* 241 */       if (proxySelector != null) {
/* 242 */         URI uRI = ParseUtil.toURI(this.url);
/* 243 */         Iterator<Proxy> iterator = proxySelector.select(uRI).iterator();
/* 244 */         while (iterator.hasNext()) {
/* 245 */           proxy = iterator.next();
/* 246 */           if (proxy == null || proxy == Proxy.NO_PROXY || proxy
/* 247 */             .type() == Proxy.Type.SOCKS) {
/*     */             break;
/*     */           }
/* 250 */           if (proxy.type() != Proxy.Type.HTTP || 
/* 251 */             !(proxy.address() instanceof InetSocketAddress)) {
/* 252 */             proxySelector.connectFailed(uRI, proxy.address(), new IOException("Wrong proxy type"));
/*     */             
/*     */             continue;
/*     */           } 
/* 256 */           InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
/*     */           try {
/* 258 */             this.http = new HttpURLConnection(this.url, proxy);
/* 259 */             this.http.setDoInput(getDoInput());
/* 260 */             this.http.setDoOutput(getDoOutput());
/* 261 */             if (this.connectTimeout >= 0) {
/* 262 */               this.http.setConnectTimeout(this.connectTimeout);
/*     */             }
/* 264 */             if (this.readTimeout >= 0) {
/* 265 */               this.http.setReadTimeout(this.readTimeout);
/*     */             }
/* 267 */             this.http.connect();
/* 268 */             this.connected = true;
/*     */             return;
/* 270 */           } catch (IOException iOException) {
/* 271 */             proxySelector.connectFailed(uRI, inetSocketAddress, iOException);
/* 272 */             this.http = null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 277 */       proxy = this.instProxy;
/* 278 */       if (proxy.type() == Proxy.Type.HTTP) {
/* 279 */         this.http = new HttpURLConnection(this.url, this.instProxy);
/* 280 */         this.http.setDoInput(getDoInput());
/* 281 */         this.http.setDoOutput(getDoOutput());
/* 282 */         if (this.connectTimeout >= 0) {
/* 283 */           this.http.setConnectTimeout(this.connectTimeout);
/*     */         }
/* 285 */         if (this.readTimeout >= 0) {
/* 286 */           this.http.setReadTimeout(this.readTimeout);
/*     */         }
/* 288 */         this.http.connect();
/* 289 */         this.connected = true;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 294 */     if (this.user == null) {
/* 295 */       this.user = "anonymous";
/* 296 */       String str = AccessController.<String>doPrivileged(new GetPropertyAction("java.version"));
/*     */       
/* 298 */       this.password = AccessController.<String>doPrivileged(new GetPropertyAction("ftp.protocol.user", "Java" + str + "@"));
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 303 */       this.ftp = FtpClient.create();
/* 304 */       if (proxy != null) {
/* 305 */         this.ftp.setProxy(proxy);
/*     */       }
/* 307 */       setTimeouts();
/* 308 */       if (this.port != -1) {
/* 309 */         this.ftp.connect(new InetSocketAddress(this.host, this.port));
/*     */       } else {
/* 311 */         this.ftp.connect(new InetSocketAddress(this.host, FtpClient.defaultPort()));
/*     */       } 
/* 313 */     } catch (UnknownHostException unknownHostException) {
/*     */ 
/*     */       
/* 316 */       throw unknownHostException;
/* 317 */     } catch (FtpProtocolException ftpProtocolException) {
/* 318 */       if (this.ftp != null) {
/*     */         try {
/* 320 */           this.ftp.close();
/* 321 */         } catch (IOException iOException) {
/* 322 */           ftpProtocolException.addSuppressed(iOException);
/*     */         } 
/*     */       }
/* 325 */       throw new IOException(ftpProtocolException);
/*     */     } 
/*     */     try {
/* 328 */       this.ftp.login(this.user, (this.password == null) ? null : this.password.toCharArray());
/* 329 */     } catch (FtpProtocolException ftpProtocolException) {
/* 330 */       this.ftp.close();
/*     */       
/* 332 */       throw new FtpLoginException("Invalid username/password");
/*     */     } 
/* 334 */     this.connected = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decodePath(String paramString) {
/* 342 */     int i = paramString.indexOf(";type=");
/* 343 */     if (i >= 0) {
/* 344 */       String str = paramString.substring(i + 6, paramString.length());
/* 345 */       if ("i".equalsIgnoreCase(str)) {
/* 346 */         this.type = 2;
/*     */       }
/* 348 */       if ("a".equalsIgnoreCase(str)) {
/* 349 */         this.type = 1;
/*     */       }
/* 351 */       if ("d".equalsIgnoreCase(str)) {
/* 352 */         this.type = 3;
/*     */       }
/* 354 */       paramString = paramString.substring(0, i);
/*     */     } 
/* 356 */     if (paramString != null && paramString.length() > 1 && paramString
/* 357 */       .charAt(0) == '/') {
/* 358 */       paramString = paramString.substring(1);
/*     */     }
/* 360 */     if (paramString == null || paramString.length() == 0) {
/* 361 */       paramString = "./";
/*     */     }
/* 363 */     if (!paramString.endsWith("/")) {
/* 364 */       i = paramString.lastIndexOf('/');
/* 365 */       if (i > 0) {
/* 366 */         this.filename = paramString.substring(i + 1, paramString.length());
/* 367 */         this.filename = ParseUtil.decode(this.filename);
/* 368 */         this.pathname = paramString.substring(0, i);
/*     */       } else {
/* 370 */         this.filename = ParseUtil.decode(paramString);
/* 371 */         this.pathname = null;
/*     */       } 
/*     */     } else {
/* 374 */       this.pathname = paramString.substring(0, paramString.length() - 1);
/* 375 */       this.filename = null;
/*     */     } 
/* 377 */     if (this.pathname != null) {
/* 378 */       this.fullpath = this.pathname + "/" + ((this.filename != null) ? this.filename : "");
/*     */     } else {
/* 380 */       this.fullpath = this.filename;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cd(String paramString) throws FtpProtocolException, IOException {
/* 391 */     if (paramString == null || paramString.isEmpty()) {
/*     */       return;
/*     */     }
/* 394 */     if (paramString.indexOf('/') == -1) {
/* 395 */       this.ftp.changeDirectory(ParseUtil.decode(paramString));
/*     */       
/*     */       return;
/*     */     } 
/* 399 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, "/");
/* 400 */     while (stringTokenizer.hasMoreTokens()) {
/* 401 */       this.ftp.changeDirectory(ParseUtil.decode(stringTokenizer.nextToken()));
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
/*     */   public InputStream getInputStream() throws IOException {
/* 416 */     if (!this.connected) {
/* 417 */       connect();
/*     */     }
/*     */     
/* 420 */     if (this.http != null) {
/* 421 */       return this.http.getInputStream();
/*     */     }
/*     */     
/* 424 */     if (this.os != null) {
/* 425 */       throw new IOException("Already opened for output");
/*     */     }
/*     */     
/* 428 */     if (this.is != null) {
/* 429 */       return this.is;
/*     */     }
/*     */     
/* 432 */     MessageHeader messageHeader = new MessageHeader();
/*     */     
/* 434 */     boolean bool = false;
/*     */     try {
/* 436 */       decodePath(this.url.getPath());
/* 437 */       if (this.filename == null || this.type == 3) {
/* 438 */         this.ftp.setAsciiType();
/* 439 */         cd(this.pathname);
/* 440 */         if (this.filename == null) {
/* 441 */           this.is = new FtpInputStream(this.ftp, this.ftp.list(null));
/*     */         } else {
/* 443 */           this.is = new FtpInputStream(this.ftp, this.ftp.nameList(this.filename));
/*     */         } 
/*     */       } else {
/* 446 */         if (this.type == 1) {
/* 447 */           this.ftp.setAsciiType();
/*     */         } else {
/* 449 */           this.ftp.setBinaryType();
/*     */         } 
/* 451 */         cd(this.pathname);
/* 452 */         this.is = new FtpInputStream(this.ftp, this.ftp.getFileStream(this.filename));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 458 */         long l = this.ftp.getLastTransferSize();
/* 459 */         messageHeader.add("content-length", Long.toString(l));
/* 460 */         if (l > 0L) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 466 */           boolean bool1 = ProgressMonitor.getDefault().shouldMeterInput(this.url, "GET");
/* 467 */           ProgressSource progressSource = null;
/*     */           
/* 469 */           if (bool1) {
/* 470 */             progressSource = new ProgressSource(this.url, "GET", l);
/* 471 */             progressSource.beginTracking();
/*     */           } 
/*     */           
/* 474 */           this.is = new MeteredStream(this.is, progressSource, l);
/*     */         } 
/* 476 */       } catch (Exception exception) {
/* 477 */         exception.printStackTrace();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 482 */       if (bool) {
/* 483 */         messageHeader.add("content-type", "text/plain");
/* 484 */         messageHeader.add("access-type", "directory");
/*     */       } else {
/* 486 */         messageHeader.add("access-type", "file");
/* 487 */         String str = guessContentTypeFromName(this.fullpath);
/* 488 */         if (str == null && this.is.markSupported()) {
/* 489 */           str = guessContentTypeFromStream(this.is);
/*     */         }
/* 491 */         if (str != null) {
/* 492 */           messageHeader.add("content-type", str);
/*     */         }
/*     */       } 
/* 495 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */       try {
/* 497 */         cd(this.fullpath);
/*     */ 
/*     */ 
/*     */         
/* 501 */         this.ftp.setAsciiType();
/*     */         
/* 503 */         this.is = new FtpInputStream(this.ftp, this.ftp.list(null));
/* 504 */         messageHeader.add("content-type", "text/plain");
/* 505 */         messageHeader.add("access-type", "directory");
/* 506 */       } catch (IOException iOException) {
/* 507 */         FileNotFoundException fileNotFoundException1 = new FileNotFoundException(this.fullpath);
/* 508 */         if (this.ftp != null) {
/*     */           try {
/* 510 */             this.ftp.close();
/* 511 */           } catch (IOException iOException1) {
/* 512 */             fileNotFoundException1.addSuppressed(iOException1);
/*     */           } 
/*     */         }
/* 515 */         throw fileNotFoundException1;
/* 516 */       } catch (FtpProtocolException ftpProtocolException) {
/* 517 */         FileNotFoundException fileNotFoundException1 = new FileNotFoundException(this.fullpath);
/* 518 */         if (this.ftp != null) {
/*     */           try {
/* 520 */             this.ftp.close();
/* 521 */           } catch (IOException iOException) {
/* 522 */             fileNotFoundException1.addSuppressed(iOException);
/*     */           } 
/*     */         }
/* 525 */         throw fileNotFoundException1;
/*     */       } 
/* 527 */     } catch (FtpProtocolException ftpProtocolException) {
/* 528 */       if (this.ftp != null) {
/*     */         try {
/* 530 */           this.ftp.close();
/* 531 */         } catch (IOException iOException) {
/* 532 */           ftpProtocolException.addSuppressed(iOException);
/*     */         } 
/*     */       }
/* 535 */       throw new IOException(ftpProtocolException);
/*     */     } 
/* 537 */     setProperties(messageHeader);
/* 538 */     return this.is;
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
/*     */   public OutputStream getOutputStream() throws IOException {
/* 553 */     if (!this.connected) {
/* 554 */       connect();
/*     */     }
/*     */     
/* 557 */     if (this.http != null) {
/* 558 */       OutputStream outputStream = this.http.getOutputStream();
/*     */ 
/*     */       
/* 561 */       this.http.getInputStream();
/* 562 */       return outputStream;
/*     */     } 
/*     */     
/* 565 */     if (this.is != null) {
/* 566 */       throw new IOException("Already opened for input");
/*     */     }
/*     */     
/* 569 */     if (this.os != null) {
/* 570 */       return this.os;
/*     */     }
/*     */     
/* 573 */     decodePath(this.url.getPath());
/* 574 */     if (this.filename == null || this.filename.length() == 0) {
/* 575 */       throw new IOException("illegal filename for a PUT");
/*     */     }
/*     */     try {
/* 578 */       if (this.pathname != null) {
/* 579 */         cd(this.pathname);
/*     */       }
/* 581 */       if (this.type == 1) {
/* 582 */         this.ftp.setAsciiType();
/*     */       } else {
/* 584 */         this.ftp.setBinaryType();
/*     */       } 
/* 586 */       this.os = new FtpOutputStream(this.ftp, this.ftp.putFileStream(this.filename, false));
/* 587 */     } catch (FtpProtocolException ftpProtocolException) {
/* 588 */       throw new IOException(ftpProtocolException);
/*     */     } 
/* 590 */     return this.os;
/*     */   }
/*     */   
/*     */   String guessContentTypeFromFilename(String paramString) {
/* 594 */     return guessContentTypeFromName(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Permission getPermission() {
/* 604 */     if (this.permission == null) {
/* 605 */       int i = this.url.getPort();
/* 606 */       i = (i < 0) ? FtpClient.defaultPort() : i;
/* 607 */       String str = this.host + ":" + i;
/* 608 */       this.permission = new SocketPermission(str, "connect");
/*     */     } 
/* 610 */     return this.permission;
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
/*     */   public void setRequestProperty(String paramString1, String paramString2) {
/* 625 */     super.setRequestProperty(paramString1, paramString2);
/* 626 */     if ("type".equals(paramString1)) {
/* 627 */       if ("i".equalsIgnoreCase(paramString2)) {
/* 628 */         this.type = 2;
/* 629 */       } else if ("a".equalsIgnoreCase(paramString2)) {
/* 630 */         this.type = 1;
/* 631 */       } else if ("d".equalsIgnoreCase(paramString2)) {
/* 632 */         this.type = 3;
/*     */       } else {
/* 634 */         throw new IllegalArgumentException("Value of '" + paramString1 + "' request property was '" + paramString2 + "' when it must be either 'i', 'a' or 'd'");
/*     */       } 
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
/*     */   
/*     */   public String getRequestProperty(String paramString) {
/* 654 */     String str = super.getRequestProperty(paramString);
/*     */     
/* 656 */     if (str == null && 
/* 657 */       "type".equals(paramString)) {
/* 658 */       str = (this.type == 1) ? "a" : ((this.type == 3) ? "d" : "i");
/*     */     }
/*     */ 
/*     */     
/* 662 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setConnectTimeout(int paramInt) {
/* 667 */     if (paramInt < 0) {
/* 668 */       throw new IllegalArgumentException("timeouts can't be negative");
/*     */     }
/* 670 */     this.connectTimeout = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectTimeout() {
/* 675 */     return (this.connectTimeout < 0) ? 0 : this.connectTimeout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReadTimeout(int paramInt) {
/* 680 */     if (paramInt < 0) {
/* 681 */       throw new IllegalArgumentException("timeouts can't be negative");
/*     */     }
/* 683 */     this.readTimeout = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getReadTimeout() {
/* 688 */     return (this.readTimeout < 0) ? 0 : this.readTimeout;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/www/protocol/ftp/FtpURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */