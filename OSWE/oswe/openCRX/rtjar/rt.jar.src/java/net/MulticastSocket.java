/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MulticastSocket
/*     */   extends DatagramSocket
/*     */ {
/*     */   private boolean interfaceSet;
/*     */   private Object ttlLock;
/*     */   private Object infLock;
/*     */   private InetAddress infAddress;
/*     */   
/*     */   public MulticastSocket() throws IOException {
/* 111 */     this(new InetSocketAddress(0));
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
/*     */   public MulticastSocket(int paramInt) throws IOException {
/* 136 */     this(new InetSocketAddress(paramInt));
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
/*     */   public MulticastSocket(SocketAddress paramSocketAddress) throws IOException {
/* 165 */     super((SocketAddress)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 184 */     this.ttlLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     this.infLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     this.infAddress = null;
/*     */     setReuseAddress(true);
/*     */     if (paramSocketAddress != null) {
/*     */       try {
/*     */         bind(paramSocketAddress);
/*     */       } finally {
/*     */         if (!isBound()) {
/*     */           close();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setTTL(byte paramByte) throws IOException {
/* 215 */     if (isClosed())
/* 216 */       throw new SocketException("Socket is closed"); 
/* 217 */     getImpl().setTTL(paramByte);
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
/*     */   public void setTimeToLive(int paramInt) throws IOException {
/* 240 */     if (paramInt < 0 || paramInt > 255) {
/* 241 */       throw new IllegalArgumentException("ttl out of range");
/*     */     }
/* 243 */     if (isClosed())
/* 244 */       throw new SocketException("Socket is closed"); 
/* 245 */     getImpl().setTimeToLive(paramInt);
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
/*     */   public byte getTTL() throws IOException {
/* 261 */     if (isClosed())
/* 262 */       throw new SocketException("Socket is closed"); 
/* 263 */     return getImpl().getTTL();
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
/*     */   public int getTimeToLive() throws IOException {
/* 275 */     if (isClosed())
/* 276 */       throw new SocketException("Socket is closed"); 
/* 277 */     return getImpl().getTimeToLive();
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
/*     */   public void joinGroup(InetAddress paramInetAddress) throws IOException {
/* 299 */     if (isClosed()) {
/* 300 */       throw new SocketException("Socket is closed");
/*     */     }
/*     */     
/* 303 */     checkAddress(paramInetAddress, "joinGroup");
/* 304 */     SecurityManager securityManager = System.getSecurityManager();
/* 305 */     if (securityManager != null) {
/* 306 */       securityManager.checkMulticast(paramInetAddress);
/*     */     }
/*     */     
/* 309 */     if (!paramInetAddress.isMulticastAddress()) {
/* 310 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 317 */     NetworkInterface networkInterface = NetworkInterface.getDefault();
/*     */     
/* 319 */     if (!this.interfaceSet && networkInterface != null) {
/* 320 */       setNetworkInterface(networkInterface);
/*     */     }
/*     */     
/* 323 */     getImpl().join(paramInetAddress);
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
/*     */   public void leaveGroup(InetAddress paramInetAddress) throws IOException {
/* 344 */     if (isClosed()) {
/* 345 */       throw new SocketException("Socket is closed");
/*     */     }
/*     */     
/* 348 */     checkAddress(paramInetAddress, "leaveGroup");
/* 349 */     SecurityManager securityManager = System.getSecurityManager();
/* 350 */     if (securityManager != null) {
/* 351 */       securityManager.checkMulticast(paramInetAddress);
/*     */     }
/*     */     
/* 354 */     if (!paramInetAddress.isMulticastAddress()) {
/* 355 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/* 358 */     getImpl().leave(paramInetAddress);
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
/*     */   public void joinGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface) throws IOException {
/* 387 */     if (isClosed()) {
/* 388 */       throw new SocketException("Socket is closed");
/*     */     }
/* 390 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress)) {
/* 391 */       throw new IllegalArgumentException("Unsupported address type");
/*     */     }
/* 393 */     if (this.oldImpl) {
/* 394 */       throw new UnsupportedOperationException();
/*     */     }
/* 396 */     checkAddress(((InetSocketAddress)paramSocketAddress).getAddress(), "joinGroup");
/* 397 */     SecurityManager securityManager = System.getSecurityManager();
/* 398 */     if (securityManager != null) {
/* 399 */       securityManager.checkMulticast(((InetSocketAddress)paramSocketAddress).getAddress());
/*     */     }
/*     */     
/* 402 */     if (!((InetSocketAddress)paramSocketAddress).getAddress().isMulticastAddress()) {
/* 403 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/* 406 */     getImpl().joinGroup(paramSocketAddress, paramNetworkInterface);
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
/*     */   public void leaveGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface) throws IOException {
/* 434 */     if (isClosed()) {
/* 435 */       throw new SocketException("Socket is closed");
/*     */     }
/* 437 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress)) {
/* 438 */       throw new IllegalArgumentException("Unsupported address type");
/*     */     }
/* 440 */     if (this.oldImpl) {
/* 441 */       throw new UnsupportedOperationException();
/*     */     }
/* 443 */     checkAddress(((InetSocketAddress)paramSocketAddress).getAddress(), "leaveGroup");
/* 444 */     SecurityManager securityManager = System.getSecurityManager();
/* 445 */     if (securityManager != null) {
/* 446 */       securityManager.checkMulticast(((InetSocketAddress)paramSocketAddress).getAddress());
/*     */     }
/*     */     
/* 449 */     if (!((InetSocketAddress)paramSocketAddress).getAddress().isMulticastAddress()) {
/* 450 */       throw new SocketException("Not a multicast address");
/*     */     }
/*     */     
/* 453 */     getImpl().leaveGroup(paramSocketAddress, paramNetworkInterface);
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
/*     */   public void setInterface(InetAddress paramInetAddress) throws SocketException {
/* 466 */     if (isClosed()) {
/* 467 */       throw new SocketException("Socket is closed");
/*     */     }
/* 469 */     checkAddress(paramInetAddress, "setInterface");
/* 470 */     synchronized (this.infLock) {
/* 471 */       getImpl().setOption(16, paramInetAddress);
/* 472 */       this.infAddress = paramInetAddress;
/* 473 */       this.interfaceSet = true;
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
/*     */   public InetAddress getInterface() throws SocketException {
/* 491 */     if (isClosed()) {
/* 492 */       throw new SocketException("Socket is closed");
/*     */     }
/* 494 */     synchronized (this.infLock) {
/*     */       
/* 496 */       InetAddress inetAddress = (InetAddress)getImpl().getOption(16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 502 */       if (this.infAddress == null) {
/* 503 */         return inetAddress;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 509 */       if (inetAddress.equals(this.infAddress)) {
/* 510 */         return inetAddress;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 519 */         NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
/* 520 */         Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
/* 521 */         while (enumeration.hasMoreElements()) {
/* 522 */           InetAddress inetAddress1 = enumeration.nextElement();
/* 523 */           if (inetAddress1.equals(this.infAddress)) {
/* 524 */             return this.infAddress;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 532 */         this.infAddress = null;
/* 533 */         return inetAddress;
/* 534 */       } catch (Exception exception) {
/* 535 */         return inetAddress;
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
/*     */   public void setNetworkInterface(NetworkInterface paramNetworkInterface) throws SocketException {
/* 553 */     synchronized (this.infLock) {
/* 554 */       getImpl().setOption(31, paramNetworkInterface);
/* 555 */       this.infAddress = null;
/* 556 */       this.interfaceSet = true;
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
/*     */   public NetworkInterface getNetworkInterface() throws SocketException {
/* 571 */     NetworkInterface networkInterface = (NetworkInterface)getImpl().getOption(31);
/* 572 */     if (networkInterface.getIndex() == 0 || networkInterface.getIndex() == -1) {
/* 573 */       InetAddress[] arrayOfInetAddress = new InetAddress[1];
/* 574 */       arrayOfInetAddress[0] = InetAddress.anyLocalAddress();
/* 575 */       return new NetworkInterface(arrayOfInetAddress[0].getHostName(), 0, arrayOfInetAddress);
/*     */     } 
/* 577 */     return networkInterface;
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
/*     */   public void setLoopbackMode(boolean paramBoolean) throws SocketException {
/* 596 */     getImpl().setOption(18, Boolean.valueOf(paramBoolean));
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
/*     */   public boolean getLoopbackMode() throws SocketException {
/* 608 */     return ((Boolean)getImpl().getOption(18)).booleanValue();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public void send(DatagramPacket paramDatagramPacket, byte paramByte) throws IOException {
/* 661 */     if (isClosed())
/* 662 */       throw new SocketException("Socket is closed"); 
/* 663 */     checkAddress(paramDatagramPacket.getAddress(), "send");
/* 664 */     synchronized (this.ttlLock) {
/* 665 */       synchronized (paramDatagramPacket) {
/* 666 */         if (this.connectState == 0) {
/*     */ 
/*     */ 
/*     */           
/* 670 */           SecurityManager securityManager = System.getSecurityManager();
/* 671 */           if (securityManager != null) {
/* 672 */             if (paramDatagramPacket.getAddress().isMulticastAddress()) {
/* 673 */               securityManager.checkMulticast(paramDatagramPacket.getAddress(), paramByte);
/*     */             } else {
/* 675 */               securityManager.checkConnect(paramDatagramPacket.getAddress().getHostAddress(), paramDatagramPacket
/* 676 */                   .getPort());
/*     */             } 
/*     */           }
/*     */         } else {
/*     */           
/* 681 */           InetAddress inetAddress = null;
/* 682 */           inetAddress = paramDatagramPacket.getAddress();
/* 683 */           if (inetAddress == null) {
/* 684 */             paramDatagramPacket.setAddress(this.connectedAddress);
/* 685 */             paramDatagramPacket.setPort(this.connectedPort);
/* 686 */           } else if (!inetAddress.equals(this.connectedAddress) || paramDatagramPacket
/* 687 */             .getPort() != this.connectedPort) {
/* 688 */             throw new SecurityException("connected address and packet address differ");
/*     */           } 
/*     */         } 
/*     */         
/* 692 */         byte b = getTTL();
/*     */         try {
/* 694 */           if (paramByte != b)
/*     */           {
/* 696 */             getImpl().setTTL(paramByte);
/*     */           }
/*     */           
/* 699 */           getImpl().send(paramDatagramPacket);
/*     */         } finally {
/*     */           
/* 702 */           if (paramByte != b)
/* 703 */             getImpl().setTTL(b); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/MulticastSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */