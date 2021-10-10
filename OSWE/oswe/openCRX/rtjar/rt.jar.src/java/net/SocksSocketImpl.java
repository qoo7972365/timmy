/*      */ package java.net;
/*      */ 
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Iterator;
/*      */ import sun.net.SocksProxy;
/*      */ import sun.net.www.ParseUtil;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class SocksSocketImpl
/*      */   extends PlainSocketImpl
/*      */   implements SocksConsts
/*      */ {
/*   44 */   private String server = null;
/*   45 */   private int serverPort = 1080;
/*      */   private InetSocketAddress external_address;
/*      */   private boolean useV4 = false;
/*   48 */   private Socket cmdsock = null;
/*   49 */   private InputStream cmdIn = null;
/*   50 */   private OutputStream cmdOut = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean applicationSetProxy;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   SocksSocketImpl(String paramString, int paramInt) {
/*   60 */     this.server = paramString;
/*   61 */     this.serverPort = (paramInt == -1) ? 1080 : paramInt;
/*      */   }
/*      */   
/*      */   SocksSocketImpl(Proxy paramProxy) {
/*   65 */     SocketAddress socketAddress = paramProxy.address();
/*   66 */     if (socketAddress instanceof InetSocketAddress) {
/*   67 */       InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*      */       
/*   69 */       this.server = inetSocketAddress.getHostString();
/*   70 */       this.serverPort = inetSocketAddress.getPort();
/*      */     } 
/*      */   }
/*      */   
/*      */   void setV4() {
/*   75 */     this.useV4 = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void privilegedConnect(final String host, final int port, final int timeout) throws IOException {
/*      */     try {
/*   84 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */           {
/*      */             public Void run() throws IOException {
/*   87 */               SocksSocketImpl.this.superConnectServer(host, port, timeout);
/*   88 */               SocksSocketImpl.this.cmdIn = SocksSocketImpl.this.getInputStream();
/*   89 */               SocksSocketImpl.this.cmdOut = SocksSocketImpl.this.getOutputStream();
/*   90 */               return null;
/*      */             }
/*      */           });
/*   93 */     } catch (PrivilegedActionException privilegedActionException) {
/*   94 */       throw (IOException)privilegedActionException.getException();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void superConnectServer(String paramString, int paramInt1, int paramInt2) throws IOException {
/*  100 */     super.connect(new InetSocketAddress(paramString, paramInt1), paramInt2);
/*      */   }
/*      */   
/*      */   private static int remainingMillis(long paramLong) throws IOException {
/*  104 */     if (paramLong == 0L) {
/*  105 */       return 0;
/*      */     }
/*  107 */     long l = paramLong - System.currentTimeMillis();
/*  108 */     if (l > 0L) {
/*  109 */       return (int)l;
/*      */     }
/*  111 */     throw new SocketTimeoutException();
/*      */   }
/*      */   
/*      */   private int readSocksReply(InputStream paramInputStream, byte[] paramArrayOfbyte) throws IOException {
/*  115 */     return readSocksReply(paramInputStream, paramArrayOfbyte, 0L);
/*      */   }
/*      */   
/*      */   private int readSocksReply(InputStream paramInputStream, byte[] paramArrayOfbyte, long paramLong) throws IOException {
/*  119 */     int i = paramArrayOfbyte.length;
/*  120 */     int j = 0;
/*  121 */     while (j < i) {
/*      */       int k;
/*      */       try {
/*  124 */         k = ((SocketInputStream)paramInputStream).read(paramArrayOfbyte, j, i - j, remainingMillis(paramLong));
/*  125 */       } catch (SocketTimeoutException socketTimeoutException) {
/*  126 */         throw new SocketTimeoutException("Connect timed out");
/*      */       } 
/*  128 */       if (k < 0)
/*  129 */         throw new SocketException("Malformed reply from SOCKS server"); 
/*  130 */       j += k;
/*      */     } 
/*  132 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean authenticate(byte paramByte, InputStream paramInputStream, BufferedOutputStream paramBufferedOutputStream) throws IOException {
/*  140 */     return authenticate(paramByte, paramInputStream, paramBufferedOutputStream, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean authenticate(byte paramByte, InputStream paramInputStream, BufferedOutputStream paramBufferedOutputStream, long paramLong) throws IOException {
/*  147 */     if (paramByte == 0) {
/*  148 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     if (paramByte == 2) {
/*      */       
/*  156 */       String str1, str2 = null;
/*  157 */       final InetAddress addr = InetAddress.getByName(this.server);
/*      */       
/*  159 */       PasswordAuthentication passwordAuthentication = AccessController.<PasswordAuthentication>doPrivileged(new PrivilegedAction<PasswordAuthentication>()
/*      */           {
/*      */             public PasswordAuthentication run() {
/*  162 */               return Authenticator.requestPasswordAuthentication(SocksSocketImpl.this
/*  163 */                   .server, addr, SocksSocketImpl.this.serverPort, "SOCKS5", "SOCKS authentication", null);
/*      */             }
/*      */           });
/*  166 */       if (passwordAuthentication != null) {
/*  167 */         str1 = passwordAuthentication.getUserName();
/*  168 */         str2 = new String(passwordAuthentication.getPassword());
/*      */       } else {
/*  170 */         str1 = AccessController.<String>doPrivileged(new GetPropertyAction("user.name"));
/*      */       } 
/*      */       
/*  173 */       if (str1 == null)
/*  174 */         return false; 
/*  175 */       paramBufferedOutputStream.write(1);
/*  176 */       paramBufferedOutputStream.write(str1.length());
/*      */       try {
/*  178 */         paramBufferedOutputStream.write(str1.getBytes("ISO-8859-1"));
/*  179 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */         assert false;
/*      */       } 
/*  182 */       if (str2 != null) {
/*  183 */         paramBufferedOutputStream.write(str2.length());
/*      */         try {
/*  185 */           paramBufferedOutputStream.write(str2.getBytes("ISO-8859-1"));
/*  186 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */           assert false;
/*      */         } 
/*      */       } else {
/*  190 */         paramBufferedOutputStream.write(0);
/*  191 */       }  paramBufferedOutputStream.flush();
/*  192 */       byte[] arrayOfByte = new byte[2];
/*  193 */       int i = readSocksReply(paramInputStream, arrayOfByte, paramLong);
/*  194 */       if (i != 2 || arrayOfByte[1] != 0) {
/*      */ 
/*      */         
/*  197 */         paramBufferedOutputStream.close();
/*  198 */         paramInputStream.close();
/*  199 */         return false;
/*      */       } 
/*      */       
/*  202 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  258 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void connectV4(InputStream paramInputStream, OutputStream paramOutputStream, InetSocketAddress paramInetSocketAddress, long paramLong) throws IOException {
/*  264 */     if (!(paramInetSocketAddress.getAddress() instanceof Inet4Address)) {
/*  265 */       throw new SocketException("SOCKS V4 requires IPv4 only addresses");
/*      */     }
/*  267 */     paramOutputStream.write(4);
/*  268 */     paramOutputStream.write(1);
/*  269 */     paramOutputStream.write(paramInetSocketAddress.getPort() >> 8 & 0xFF);
/*  270 */     paramOutputStream.write(paramInetSocketAddress.getPort() >> 0 & 0xFF);
/*  271 */     paramOutputStream.write(paramInetSocketAddress.getAddress().getAddress());
/*  272 */     String str = getUserName();
/*      */     try {
/*  274 */       paramOutputStream.write(str.getBytes("ISO-8859-1"));
/*  275 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       assert false;
/*      */     } 
/*  278 */     paramOutputStream.write(0);
/*  279 */     paramOutputStream.flush();
/*  280 */     byte[] arrayOfByte = new byte[8];
/*  281 */     int i = readSocksReply(paramInputStream, arrayOfByte, paramLong);
/*  282 */     if (i != 8)
/*  283 */       throw new SocketException("Reply from SOCKS server has bad length: " + i); 
/*  284 */     if (arrayOfByte[0] != 0 && arrayOfByte[0] != 4)
/*  285 */       throw new SocketException("Reply from SOCKS server has bad version"); 
/*  286 */     SocketException socketException = null;
/*  287 */     switch (arrayOfByte[1]) {
/*      */       
/*      */       case 90:
/*  290 */         this.external_address = paramInetSocketAddress;
/*      */         break;
/*      */       case 91:
/*  293 */         socketException = new SocketException("SOCKS request rejected");
/*      */         break;
/*      */       case 92:
/*  296 */         socketException = new SocketException("SOCKS server couldn't reach destination");
/*      */         break;
/*      */       case 93:
/*  299 */         socketException = new SocketException("SOCKS authentication failed");
/*      */         break;
/*      */       default:
/*  302 */         socketException = new SocketException("Reply from SOCKS server contains bad status");
/*      */         break;
/*      */     } 
/*  305 */     if (socketException != null) {
/*  306 */       paramInputStream.close();
/*  307 */       paramOutputStream.close();
/*  308 */       throw socketException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void connect(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*      */     long l;
/*      */     int j;
/*      */     byte[] arrayOfByte2, arrayOfByte3, arrayOfByte4;
/*  330 */     if (paramInt == 0) {
/*  331 */       l = 0L;
/*      */     } else {
/*  333 */       long l1 = System.currentTimeMillis() + paramInt;
/*  334 */       l = (l1 < 0L) ? Long.MAX_VALUE : l1;
/*      */     } 
/*      */     
/*  337 */     SecurityManager securityManager = System.getSecurityManager();
/*  338 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/*  339 */       throw new IllegalArgumentException("Unsupported address type"); 
/*  340 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/*  341 */     if (securityManager != null)
/*  342 */       if (inetSocketAddress.isUnresolved()) {
/*  343 */         securityManager.checkConnect(inetSocketAddress.getHostName(), inetSocketAddress
/*  344 */             .getPort());
/*      */       } else {
/*  346 */         securityManager.checkConnect(inetSocketAddress.getAddress().getHostAddress(), inetSocketAddress
/*  347 */             .getPort());
/*      */       }  
/*  349 */     if (this.server == null) {
/*      */       URI uRI;
/*      */ 
/*      */       
/*  353 */       ProxySelector proxySelector = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*      */           {
/*      */             public ProxySelector run() {
/*  356 */               return ProxySelector.getDefault();
/*      */             }
/*      */           });
/*  359 */       if (proxySelector == null) {
/*      */ 
/*      */ 
/*      */         
/*  363 */         super.connect(inetSocketAddress, remainingMillis(l));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  368 */       String str = inetSocketAddress.getHostString();
/*      */       
/*  370 */       if (inetSocketAddress.getAddress() instanceof Inet6Address && 
/*  371 */         !str.startsWith("[") && str.indexOf(":") >= 0) {
/*  372 */         str = "[" + str + "]";
/*      */       }
/*      */       try {
/*  375 */         uRI = new URI("socket://" + ParseUtil.encodePath(str) + ":" + inetSocketAddress.getPort());
/*  376 */       } catch (URISyntaxException uRISyntaxException) {
/*      */         
/*  378 */         assert false : uRISyntaxException;
/*  379 */         uRI = null;
/*      */       } 
/*  381 */       Proxy proxy = null;
/*  382 */       IOException iOException = null;
/*  383 */       Iterator<Proxy> iterator = null;
/*  384 */       iterator = proxySelector.select(uRI).iterator();
/*  385 */       if (iterator == null || !iterator.hasNext()) {
/*  386 */         super.connect(inetSocketAddress, remainingMillis(l));
/*      */         return;
/*      */       } 
/*  389 */       while (iterator.hasNext()) {
/*  390 */         proxy = iterator.next();
/*  391 */         if (proxy == null || proxy.type() != Proxy.Type.SOCKS) {
/*  392 */           super.connect(inetSocketAddress, remainingMillis(l));
/*      */           
/*      */           return;
/*      */         } 
/*  396 */         if (!(proxy.address() instanceof InetSocketAddress)) {
/*  397 */           throw new SocketException("Unknown address type for proxy: " + proxy);
/*      */         }
/*  399 */         this.server = ((InetSocketAddress)proxy.address()).getHostString();
/*  400 */         this.serverPort = ((InetSocketAddress)proxy.address()).getPort();
/*  401 */         if (proxy instanceof SocksProxy && (
/*  402 */           (SocksProxy)proxy).protocolVersion() == 4) {
/*  403 */           this.useV4 = true;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  409 */           privilegedConnect(this.server, this.serverPort, remainingMillis(l));
/*      */           
/*      */           break;
/*  412 */         } catch (IOException iOException1) {
/*      */           
/*  414 */           proxySelector.connectFailed(uRI, proxy.address(), iOException1);
/*  415 */           this.server = null;
/*  416 */           this.serverPort = -1;
/*  417 */           iOException = iOException1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  426 */       if (this.server == null) {
/*  427 */         throw new SocketException("Can't connect to SOCKS proxy:" + iOException
/*  428 */             .getMessage());
/*      */       }
/*      */     } else {
/*      */       
/*      */       try {
/*  433 */         privilegedConnect(this.server, this.serverPort, remainingMillis(l));
/*  434 */       } catch (IOException iOException) {
/*  435 */         throw new SocketException(iOException.getMessage());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  440 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.cmdOut, 512);
/*  441 */     InputStream inputStream = this.cmdIn;
/*      */     
/*  443 */     if (this.useV4) {
/*      */ 
/*      */       
/*  446 */       if (inetSocketAddress.isUnresolved())
/*  447 */         throw new UnknownHostException(inetSocketAddress.toString()); 
/*  448 */       connectV4(inputStream, bufferedOutputStream, inetSocketAddress, l);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  453 */     bufferedOutputStream.write(5);
/*  454 */     bufferedOutputStream.write(2);
/*  455 */     bufferedOutputStream.write(0);
/*  456 */     bufferedOutputStream.write(2);
/*  457 */     bufferedOutputStream.flush();
/*  458 */     byte[] arrayOfByte1 = new byte[2];
/*  459 */     int i = readSocksReply(inputStream, arrayOfByte1, l);
/*  460 */     if (i != 2 || arrayOfByte1[0] != 5) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  465 */       if (inetSocketAddress.isUnresolved())
/*  466 */         throw new UnknownHostException(inetSocketAddress.toString()); 
/*  467 */       connectV4(inputStream, bufferedOutputStream, inetSocketAddress, l);
/*      */       return;
/*      */     } 
/*  470 */     if (arrayOfByte1[1] == -1)
/*  471 */       throw new SocketException("SOCKS : No acceptable methods"); 
/*  472 */     if (!authenticate(arrayOfByte1[1], inputStream, bufferedOutputStream, l)) {
/*  473 */       throw new SocketException("SOCKS : authentication failed");
/*      */     }
/*  475 */     bufferedOutputStream.write(5);
/*  476 */     bufferedOutputStream.write(1);
/*  477 */     bufferedOutputStream.write(0);
/*      */     
/*  479 */     if (inetSocketAddress.isUnresolved()) {
/*  480 */       bufferedOutputStream.write(3);
/*  481 */       bufferedOutputStream.write(inetSocketAddress.getHostName().length());
/*      */       try {
/*  483 */         bufferedOutputStream.write(inetSocketAddress.getHostName().getBytes("ISO-8859-1"));
/*  484 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */         assert false;
/*      */       } 
/*  487 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 0xFF);
/*  488 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 0xFF);
/*  489 */     } else if (inetSocketAddress.getAddress() instanceof Inet6Address) {
/*  490 */       bufferedOutputStream.write(4);
/*  491 */       bufferedOutputStream.write(inetSocketAddress.getAddress().getAddress());
/*  492 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 0xFF);
/*  493 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 0xFF);
/*      */     } else {
/*  495 */       bufferedOutputStream.write(1);
/*  496 */       bufferedOutputStream.write(inetSocketAddress.getAddress().getAddress());
/*  497 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 8 & 0xFF);
/*  498 */       bufferedOutputStream.write(inetSocketAddress.getPort() >> 0 & 0xFF);
/*      */     } 
/*  500 */     bufferedOutputStream.flush();
/*  501 */     arrayOfByte1 = new byte[4];
/*  502 */     i = readSocksReply(inputStream, arrayOfByte1, l);
/*  503 */     if (i != 4)
/*  504 */       throw new SocketException("Reply from SOCKS server has bad length"); 
/*  505 */     SocketException socketException = null;
/*      */ 
/*      */     
/*  508 */     switch (arrayOfByte1[1]) {
/*      */       
/*      */       case 0:
/*  511 */         switch (arrayOfByte1[3]) {
/*      */           case 1:
/*  513 */             arrayOfByte2 = new byte[4];
/*  514 */             i = readSocksReply(inputStream, arrayOfByte2, l);
/*  515 */             if (i != 4)
/*  516 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  517 */             arrayOfByte1 = new byte[2];
/*  518 */             i = readSocksReply(inputStream, arrayOfByte1, l);
/*  519 */             if (i != 2)
/*  520 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*      */             break;
/*      */           case 3:
/*  523 */             arrayOfByte3 = new byte[1];
/*  524 */             i = readSocksReply(inputStream, arrayOfByte3, l);
/*  525 */             if (i != 1)
/*  526 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  527 */             j = arrayOfByte3[0] & 0xFF;
/*  528 */             arrayOfByte4 = new byte[j];
/*  529 */             i = readSocksReply(inputStream, arrayOfByte4, l);
/*  530 */             if (i != j)
/*  531 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  532 */             arrayOfByte1 = new byte[2];
/*  533 */             i = readSocksReply(inputStream, arrayOfByte1, l);
/*  534 */             if (i != 2)
/*  535 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*      */             break;
/*      */           case 4:
/*  538 */             j = 16;
/*  539 */             arrayOfByte2 = new byte[j];
/*  540 */             i = readSocksReply(inputStream, arrayOfByte2, l);
/*  541 */             if (i != j)
/*  542 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  543 */             arrayOfByte1 = new byte[2];
/*  544 */             i = readSocksReply(inputStream, arrayOfByte1, l);
/*  545 */             if (i != 2)
/*  546 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*      */             break;
/*      */         } 
/*  549 */         socketException = new SocketException("Reply from SOCKS server contains wrong code");
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  554 */         socketException = new SocketException("SOCKS server general failure");
/*      */         break;
/*      */       case 2:
/*  557 */         socketException = new SocketException("SOCKS: Connection not allowed by ruleset");
/*      */         break;
/*      */       case 3:
/*  560 */         socketException = new SocketException("SOCKS: Network unreachable");
/*      */         break;
/*      */       case 4:
/*  563 */         socketException = new SocketException("SOCKS: Host unreachable");
/*      */         break;
/*      */       case 5:
/*  566 */         socketException = new SocketException("SOCKS: Connection refused");
/*      */         break;
/*      */       case 6:
/*  569 */         socketException = new SocketException("SOCKS: TTL expired");
/*      */         break;
/*      */       case 7:
/*  572 */         socketException = new SocketException("SOCKS: Command not supported");
/*      */         break;
/*      */       case 8:
/*  575 */         socketException = new SocketException("SOCKS: address type not supported");
/*      */         break;
/*      */     } 
/*  578 */     if (socketException != null) {
/*  579 */       inputStream.close();
/*  580 */       bufferedOutputStream.close();
/*  581 */       throw socketException;
/*      */     } 
/*  583 */     this.external_address = inetSocketAddress;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void bindV4(InputStream paramInputStream, OutputStream paramOutputStream, InetAddress paramInetAddress, int paramInt) throws IOException {
/*  589 */     if (!(paramInetAddress instanceof Inet4Address)) {
/*  590 */       throw new SocketException("SOCKS V4 requires IPv4 only addresses");
/*      */     }
/*  592 */     bind(paramInetAddress, paramInt);
/*  593 */     byte[] arrayOfByte1 = paramInetAddress.getAddress();
/*      */     
/*  595 */     InetAddress inetAddress = paramInetAddress;
/*  596 */     if (inetAddress.isAnyLocalAddress()) {
/*  597 */       inetAddress = AccessController.<InetAddress>doPrivileged(new PrivilegedAction<InetAddress>()
/*      */           {
/*      */             public InetAddress run() {
/*  600 */               return SocksSocketImpl.this.cmdsock.getLocalAddress();
/*      */             }
/*      */           });
/*      */       
/*  604 */       arrayOfByte1 = inetAddress.getAddress();
/*      */     } 
/*  606 */     paramOutputStream.write(4);
/*  607 */     paramOutputStream.write(2);
/*  608 */     paramOutputStream.write(super.getLocalPort() >> 8 & 0xFF);
/*  609 */     paramOutputStream.write(super.getLocalPort() >> 0 & 0xFF);
/*  610 */     paramOutputStream.write(arrayOfByte1);
/*  611 */     String str = getUserName();
/*      */     try {
/*  613 */       paramOutputStream.write(str.getBytes("ISO-8859-1"));
/*  614 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */       assert false;
/*      */     } 
/*  617 */     paramOutputStream.write(0);
/*  618 */     paramOutputStream.flush();
/*  619 */     byte[] arrayOfByte2 = new byte[8];
/*  620 */     int i = readSocksReply(paramInputStream, arrayOfByte2);
/*  621 */     if (i != 8)
/*  622 */       throw new SocketException("Reply from SOCKS server has bad length: " + i); 
/*  623 */     if (arrayOfByte2[0] != 0 && arrayOfByte2[0] != 4)
/*  624 */       throw new SocketException("Reply from SOCKS server has bad version"); 
/*  625 */     SocketException socketException = null;
/*  626 */     switch (arrayOfByte2[1]) {
/*      */       
/*      */       case 90:
/*  629 */         this.external_address = new InetSocketAddress(paramInetAddress, paramInt);
/*      */         break;
/*      */       case 91:
/*  632 */         socketException = new SocketException("SOCKS request rejected");
/*      */         break;
/*      */       case 92:
/*  635 */         socketException = new SocketException("SOCKS server couldn't reach destination");
/*      */         break;
/*      */       case 93:
/*  638 */         socketException = new SocketException("SOCKS authentication failed");
/*      */         break;
/*      */       default:
/*  641 */         socketException = new SocketException("Reply from SOCKS server contains bad status");
/*      */         break;
/*      */     } 
/*  644 */     if (socketException != null) {
/*  645 */       paramInputStream.close();
/*  646 */       paramOutputStream.close();
/*  647 */       throw socketException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void socksBind(InetSocketAddress paramInetSocketAddress) throws IOException {
/*      */     byte b;
/*      */     int k;
/*      */     byte[] arrayOfByte2, arrayOfByte3;
/*  661 */     if (this.socket != null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  669 */     if (this.server == null) {
/*      */       URI uRI;
/*      */ 
/*      */       
/*  673 */       ProxySelector proxySelector = AccessController.<ProxySelector>doPrivileged(new PrivilegedAction<ProxySelector>()
/*      */           {
/*      */             public ProxySelector run() {
/*  676 */               return ProxySelector.getDefault();
/*      */             }
/*      */           });
/*  679 */       if (proxySelector == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  687 */       String str = paramInetSocketAddress.getHostString();
/*      */       
/*  689 */       if (paramInetSocketAddress.getAddress() instanceof Inet6Address && 
/*  690 */         !str.startsWith("[") && str.indexOf(":") >= 0) {
/*  691 */         str = "[" + str + "]";
/*      */       }
/*      */       try {
/*  694 */         uRI = new URI("serversocket://" + ParseUtil.encodePath(str) + ":" + paramInetSocketAddress.getPort());
/*  695 */       } catch (URISyntaxException uRISyntaxException) {
/*      */         
/*  697 */         assert false : uRISyntaxException;
/*  698 */         uRI = null;
/*      */       } 
/*  700 */       Proxy proxy = null;
/*  701 */       Exception exception = null;
/*  702 */       Iterator<Proxy> iterator = null;
/*  703 */       iterator = proxySelector.select(uRI).iterator();
/*  704 */       if (iterator == null || !iterator.hasNext()) {
/*      */         return;
/*      */       }
/*  707 */       while (iterator.hasNext()) {
/*  708 */         proxy = iterator.next();
/*  709 */         if (proxy == null || proxy.type() != Proxy.Type.SOCKS) {
/*      */           return;
/*      */         }
/*      */         
/*  713 */         if (!(proxy.address() instanceof InetSocketAddress)) {
/*  714 */           throw new SocketException("Unknown address type for proxy: " + proxy);
/*      */         }
/*  716 */         this.server = ((InetSocketAddress)proxy.address()).getHostString();
/*  717 */         this.serverPort = ((InetSocketAddress)proxy.address()).getPort();
/*  718 */         if (proxy instanceof SocksProxy && (
/*  719 */           (SocksProxy)proxy).protocolVersion() == 4) {
/*  720 */           this.useV4 = true;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  726 */           AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */               {
/*      */                 public Void run() throws Exception {
/*  729 */                   SocksSocketImpl.this.cmdsock = new Socket(new PlainSocketImpl());
/*  730 */                   SocksSocketImpl.this.cmdsock.connect(new InetSocketAddress(SocksSocketImpl.this.server, SocksSocketImpl.this.serverPort));
/*  731 */                   SocksSocketImpl.this.cmdIn = SocksSocketImpl.this.cmdsock.getInputStream();
/*  732 */                   SocksSocketImpl.this.cmdOut = SocksSocketImpl.this.cmdsock.getOutputStream();
/*  733 */                   return null;
/*      */                 }
/*      */               });
/*  736 */         } catch (Exception exception1) {
/*      */           
/*  738 */           proxySelector.connectFailed(uRI, proxy.address(), new SocketException(exception1.getMessage()));
/*  739 */           this.server = null;
/*  740 */           this.serverPort = -1;
/*  741 */           this.cmdsock = null;
/*  742 */           exception = exception1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  751 */       if (this.server == null || this.cmdsock == null) {
/*  752 */         throw new SocketException("Can't connect to SOCKS proxy:" + exception
/*  753 */             .getMessage());
/*      */       }
/*      */     } else {
/*      */       try {
/*  757 */         AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*      */             {
/*      */               public Void run() throws Exception {
/*  760 */                 SocksSocketImpl.this.cmdsock = new Socket(new PlainSocketImpl());
/*  761 */                 SocksSocketImpl.this.cmdsock.connect(new InetSocketAddress(SocksSocketImpl.this.server, SocksSocketImpl.this.serverPort));
/*  762 */                 SocksSocketImpl.this.cmdIn = SocksSocketImpl.this.cmdsock.getInputStream();
/*  763 */                 SocksSocketImpl.this.cmdOut = SocksSocketImpl.this.cmdsock.getOutputStream();
/*  764 */                 return null;
/*      */               }
/*      */             });
/*  767 */       } catch (Exception exception) {
/*  768 */         throw new SocketException(exception.getMessage());
/*      */       } 
/*      */     } 
/*  771 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.cmdOut, 512);
/*  772 */     InputStream inputStream = this.cmdIn;
/*  773 */     if (this.useV4) {
/*  774 */       bindV4(inputStream, bufferedOutputStream, paramInetSocketAddress.getAddress(), paramInetSocketAddress.getPort());
/*      */       return;
/*      */     } 
/*  777 */     bufferedOutputStream.write(5);
/*  778 */     bufferedOutputStream.write(2);
/*  779 */     bufferedOutputStream.write(0);
/*  780 */     bufferedOutputStream.write(2);
/*  781 */     bufferedOutputStream.flush();
/*  782 */     byte[] arrayOfByte1 = new byte[2];
/*  783 */     int i = readSocksReply(inputStream, arrayOfByte1);
/*  784 */     if (i != 2 || arrayOfByte1[0] != 5) {
/*      */ 
/*      */       
/*  787 */       bindV4(inputStream, bufferedOutputStream, paramInetSocketAddress.getAddress(), paramInetSocketAddress.getPort());
/*      */       return;
/*      */     } 
/*  790 */     if (arrayOfByte1[1] == -1)
/*  791 */       throw new SocketException("SOCKS : No acceptable methods"); 
/*  792 */     if (!authenticate(arrayOfByte1[1], inputStream, bufferedOutputStream)) {
/*  793 */       throw new SocketException("SOCKS : authentication failed");
/*      */     }
/*      */     
/*  796 */     bufferedOutputStream.write(5);
/*  797 */     bufferedOutputStream.write(2);
/*  798 */     bufferedOutputStream.write(0);
/*  799 */     int j = paramInetSocketAddress.getPort();
/*  800 */     if (paramInetSocketAddress.isUnresolved()) {
/*  801 */       bufferedOutputStream.write(3);
/*  802 */       bufferedOutputStream.write(paramInetSocketAddress.getHostName().length());
/*      */       try {
/*  804 */         bufferedOutputStream.write(paramInetSocketAddress.getHostName().getBytes("ISO-8859-1"));
/*  805 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*      */         assert false;
/*      */       } 
/*  808 */       bufferedOutputStream.write(j >> 8 & 0xFF);
/*  809 */       bufferedOutputStream.write(j >> 0 & 0xFF);
/*  810 */     } else if (paramInetSocketAddress.getAddress() instanceof Inet4Address) {
/*  811 */       byte[] arrayOfByte = paramInetSocketAddress.getAddress().getAddress();
/*  812 */       bufferedOutputStream.write(1);
/*  813 */       bufferedOutputStream.write(arrayOfByte);
/*  814 */       bufferedOutputStream.write(j >> 8 & 0xFF);
/*  815 */       bufferedOutputStream.write(j >> 0 & 0xFF);
/*  816 */       bufferedOutputStream.flush();
/*  817 */     } else if (paramInetSocketAddress.getAddress() instanceof Inet6Address) {
/*  818 */       byte[] arrayOfByte = paramInetSocketAddress.getAddress().getAddress();
/*  819 */       bufferedOutputStream.write(4);
/*  820 */       bufferedOutputStream.write(arrayOfByte);
/*  821 */       bufferedOutputStream.write(j >> 8 & 0xFF);
/*  822 */       bufferedOutputStream.write(j >> 0 & 0xFF);
/*  823 */       bufferedOutputStream.flush();
/*      */     } else {
/*  825 */       this.cmdsock.close();
/*  826 */       throw new SocketException("unsupported address type : " + paramInetSocketAddress);
/*      */     } 
/*  828 */     arrayOfByte1 = new byte[4];
/*  829 */     i = readSocksReply(inputStream, arrayOfByte1);
/*  830 */     SocketException socketException = null;
/*      */ 
/*      */     
/*  833 */     switch (arrayOfByte1[1]) {
/*      */       
/*      */       case 0:
/*  836 */         switch (arrayOfByte1[3]) {
/*      */           case 1:
/*  838 */             arrayOfByte2 = new byte[4];
/*  839 */             i = readSocksReply(inputStream, arrayOfByte2);
/*  840 */             if (i != 4)
/*  841 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  842 */             arrayOfByte1 = new byte[2];
/*  843 */             i = readSocksReply(inputStream, arrayOfByte1);
/*  844 */             if (i != 2)
/*  845 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  846 */             k = (arrayOfByte1[0] & 0xFF) << 8;
/*  847 */             k += arrayOfByte1[1] & 0xFF;
/*  848 */             this.external_address = new InetSocketAddress(new Inet4Address("", arrayOfByte2), k);
/*      */             break;
/*      */           
/*      */           case 3:
/*  852 */             b = arrayOfByte1[1];
/*  853 */             arrayOfByte3 = new byte[b];
/*  854 */             i = readSocksReply(inputStream, arrayOfByte3);
/*  855 */             if (i != b)
/*  856 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  857 */             arrayOfByte1 = new byte[2];
/*  858 */             i = readSocksReply(inputStream, arrayOfByte1);
/*  859 */             if (i != 2)
/*  860 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  861 */             k = (arrayOfByte1[0] & 0xFF) << 8;
/*  862 */             k += arrayOfByte1[1] & 0xFF;
/*  863 */             this.external_address = new InetSocketAddress(new String(arrayOfByte3), k);
/*      */             break;
/*      */           case 4:
/*  866 */             b = arrayOfByte1[1];
/*  867 */             arrayOfByte2 = new byte[b];
/*  868 */             i = readSocksReply(inputStream, arrayOfByte2);
/*  869 */             if (i != b)
/*  870 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  871 */             arrayOfByte1 = new byte[2];
/*  872 */             i = readSocksReply(inputStream, arrayOfByte1);
/*  873 */             if (i != 2)
/*  874 */               throw new SocketException("Reply from SOCKS server badly formatted"); 
/*  875 */             k = (arrayOfByte1[0] & 0xFF) << 8;
/*  876 */             k += arrayOfByte1[1] & 0xFF;
/*  877 */             this.external_address = new InetSocketAddress(new Inet6Address("", arrayOfByte2), k);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       case 1:
/*  883 */         socketException = new SocketException("SOCKS server general failure");
/*      */         break;
/*      */       case 2:
/*  886 */         socketException = new SocketException("SOCKS: Bind not allowed by ruleset");
/*      */         break;
/*      */       case 3:
/*  889 */         socketException = new SocketException("SOCKS: Network unreachable");
/*      */         break;
/*      */       case 4:
/*  892 */         socketException = new SocketException("SOCKS: Host unreachable");
/*      */         break;
/*      */       case 5:
/*  895 */         socketException = new SocketException("SOCKS: Connection refused");
/*      */         break;
/*      */       case 6:
/*  898 */         socketException = new SocketException("SOCKS: TTL expired");
/*      */         break;
/*      */       case 7:
/*  901 */         socketException = new SocketException("SOCKS: Command not supported");
/*      */         break;
/*      */       case 8:
/*  904 */         socketException = new SocketException("SOCKS: address type not supported");
/*      */         break;
/*      */     } 
/*  907 */     if (socketException != null) {
/*  908 */       inputStream.close();
/*  909 */       bufferedOutputStream.close();
/*  910 */       this.cmdsock.close();
/*  911 */       this.cmdsock = null;
/*  912 */       throw socketException;
/*      */     } 
/*  914 */     this.cmdIn = inputStream;
/*  915 */     this.cmdOut = bufferedOutputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void acceptFrom(SocketImpl paramSocketImpl, InetSocketAddress paramInetSocketAddress) throws IOException {
/*      */     int j;
/*      */     byte[] arrayOfByte;
/*      */     int k;
/*  928 */     if (this.cmdsock == null) {
/*      */       return;
/*      */     }
/*      */     
/*  932 */     InputStream inputStream = this.cmdIn;
/*      */     
/*  934 */     socksBind(paramInetSocketAddress);
/*  935 */     inputStream.read();
/*  936 */     int i = inputStream.read();
/*  937 */     inputStream.read();
/*  938 */     SocketException socketException = null;
/*      */ 
/*      */     
/*  941 */     InetSocketAddress inetSocketAddress = null;
/*  942 */     switch (i) {
/*      */       
/*      */       case 0:
/*  945 */         i = inputStream.read();
/*  946 */         switch (i) {
/*      */           case 1:
/*  948 */             arrayOfByte = new byte[4];
/*  949 */             readSocksReply(inputStream, arrayOfByte);
/*  950 */             j = inputStream.read() << 8;
/*  951 */             j += inputStream.read();
/*  952 */             inetSocketAddress = new InetSocketAddress(new Inet4Address("", arrayOfByte), j);
/*      */             break;
/*      */           
/*      */           case 3:
/*  956 */             k = inputStream.read();
/*  957 */             arrayOfByte = new byte[k];
/*  958 */             readSocksReply(inputStream, arrayOfByte);
/*  959 */             j = inputStream.read() << 8;
/*  960 */             j += inputStream.read();
/*  961 */             inetSocketAddress = new InetSocketAddress(new String(arrayOfByte), j);
/*      */             break;
/*      */           case 4:
/*  964 */             arrayOfByte = new byte[16];
/*  965 */             readSocksReply(inputStream, arrayOfByte);
/*  966 */             j = inputStream.read() << 8;
/*  967 */             j += inputStream.read();
/*  968 */             inetSocketAddress = new InetSocketAddress(new Inet6Address("", arrayOfByte), j);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       case 1:
/*  974 */         socketException = new SocketException("SOCKS server general failure");
/*      */         break;
/*      */       case 2:
/*  977 */         socketException = new SocketException("SOCKS: Accept not allowed by ruleset");
/*      */         break;
/*      */       case 3:
/*  980 */         socketException = new SocketException("SOCKS: Network unreachable");
/*      */         break;
/*      */       case 4:
/*  983 */         socketException = new SocketException("SOCKS: Host unreachable");
/*      */         break;
/*      */       case 5:
/*  986 */         socketException = new SocketException("SOCKS: Connection refused");
/*      */         break;
/*      */       case 6:
/*  989 */         socketException = new SocketException("SOCKS: TTL expired");
/*      */         break;
/*      */       case 7:
/*  992 */         socketException = new SocketException("SOCKS: Command not supported");
/*      */         break;
/*      */       case 8:
/*  995 */         socketException = new SocketException("SOCKS: address type not supported");
/*      */         break;
/*      */     } 
/*  998 */     if (socketException != null) {
/*  999 */       this.cmdIn.close();
/* 1000 */       this.cmdOut.close();
/* 1001 */       this.cmdsock.close();
/* 1002 */       this.cmdsock = null;
/* 1003 */       throw socketException;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1011 */     if (paramSocketImpl instanceof SocksSocketImpl) {
/* 1012 */       ((SocksSocketImpl)paramSocketImpl).external_address = inetSocketAddress;
/*      */     }
/* 1014 */     if (paramSocketImpl instanceof PlainSocketImpl) {
/* 1015 */       PlainSocketImpl plainSocketImpl = (PlainSocketImpl)paramSocketImpl;
/* 1016 */       plainSocketImpl.setInputStream((SocketInputStream)inputStream);
/* 1017 */       plainSocketImpl.setFileDescriptor(this.cmdsock.getImpl().getFileDescriptor());
/* 1018 */       plainSocketImpl.setAddress(this.cmdsock.getImpl().getInetAddress());
/* 1019 */       plainSocketImpl.setPort(this.cmdsock.getImpl().getPort());
/* 1020 */       plainSocketImpl.setLocalPort(this.cmdsock.getImpl().getLocalPort());
/*      */     } else {
/* 1022 */       paramSocketImpl.fd = (this.cmdsock.getImpl()).fd;
/* 1023 */       paramSocketImpl.address = (this.cmdsock.getImpl()).address;
/* 1024 */       paramSocketImpl.port = (this.cmdsock.getImpl()).port;
/* 1025 */       paramSocketImpl.localport = (this.cmdsock.getImpl()).localport;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     this.cmdsock = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected InetAddress getInetAddress() {
/* 1044 */     if (this.external_address != null) {
/* 1045 */       return this.external_address.getAddress();
/*      */     }
/* 1047 */     return super.getInetAddress();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getPort() {
/* 1058 */     if (this.external_address != null) {
/* 1059 */       return this.external_address.getPort();
/*      */     }
/* 1061 */     return super.getPort();
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getLocalPort() {
/* 1066 */     if (this.socket != null)
/* 1067 */       return super.getLocalPort(); 
/* 1068 */     if (this.external_address != null) {
/* 1069 */       return this.external_address.getPort();
/*      */     }
/* 1071 */     return super.getLocalPort();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void close() throws IOException {
/* 1076 */     if (this.cmdsock != null)
/* 1077 */       this.cmdsock.close(); 
/* 1078 */     this.cmdsock = null;
/* 1079 */     super.close();
/*      */   }
/*      */   
/*      */   private String getUserName() {
/* 1083 */     String str = "";
/* 1084 */     if (this.applicationSetProxy) {
/*      */       try {
/* 1086 */         str = System.getProperty("user.name");
/* 1087 */       } catch (SecurityException securityException) {}
/*      */     } else {
/* 1089 */       str = AccessController.<String>doPrivileged(new GetPropertyAction("user.name"));
/*      */     } 
/*      */     
/* 1092 */     return str;
/*      */   }
/*      */   
/*      */   SocksSocketImpl() {}
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/SocksSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */