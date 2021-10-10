/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.StandardSocketOptions;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ServerSocketAdaptor
/*     */   extends ServerSocket
/*     */ {
/*     */   private final ServerSocketChannelImpl ssc;
/*  59 */   private volatile int timeout = 0;
/*     */   
/*     */   public static ServerSocket create(ServerSocketChannelImpl paramServerSocketChannelImpl) {
/*     */     try {
/*  63 */       return new ServerSocketAdaptor(paramServerSocketChannelImpl);
/*  64 */     } catch (IOException iOException) {
/*  65 */       throw new Error(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerSocketAdaptor(ServerSocketChannelImpl paramServerSocketChannelImpl) throws IOException {
/*  73 */     this.ssc = paramServerSocketChannelImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress) throws IOException {
/*  78 */     bind(paramSocketAddress, 50);
/*     */   }
/*     */   
/*     */   public void bind(SocketAddress paramSocketAddress, int paramInt) throws IOException {
/*  82 */     if (paramSocketAddress == null)
/*  83 */       paramSocketAddress = new InetSocketAddress(0); 
/*     */     try {
/*  85 */       this.ssc.bind(paramSocketAddress, paramInt);
/*  86 */     } catch (Exception exception) {
/*  87 */       Net.translateException(exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public InetAddress getInetAddress() {
/*  92 */     if (!this.ssc.isBound())
/*  93 */       return null; 
/*  94 */     return Net.getRevealedLocalAddress(this.ssc.localAddress()).getAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLocalPort() {
/*  99 */     if (!this.ssc.isBound())
/* 100 */       return -1; 
/* 101 */     return Net.asInetSocketAddress(this.ssc.localAddress()).getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public Socket accept() throws IOException {
/* 106 */     synchronized (this.ssc.blockingLock()) {
/* 107 */       if (!this.ssc.isBound())
/* 108 */         throw new IllegalBlockingModeException(); 
/*     */       try {
/* 110 */         if (this.timeout == 0) {
/*     */ 
/*     */           
/* 113 */           SocketChannel socketChannel = this.ssc.accept();
/* 114 */           if (socketChannel == null && !this.ssc.isBlocking())
/* 115 */             throw new IllegalBlockingModeException(); 
/* 116 */           return socketChannel.socket();
/*     */         } 
/*     */         
/* 119 */         if (!this.ssc.isBlocking())
/* 120 */           throw new IllegalBlockingModeException(); 
/* 121 */         this.ssc.configureBlocking(false);
/*     */         try {
/*     */           SocketChannel socketChannel;
/* 124 */           if ((socketChannel = this.ssc.accept()) != null)
/* 125 */             return socketChannel.socket(); 
/* 126 */           long l = this.timeout;
/*     */           while (true) {
/* 128 */             if (!this.ssc.isOpen())
/* 129 */               throw new ClosedChannelException(); 
/* 130 */             long l1 = System.currentTimeMillis();
/* 131 */             int i = this.ssc.poll(Net.POLLIN, l);
/* 132 */             if (i > 0 && (socketChannel = this.ssc.accept()) != null)
/* 133 */               return socketChannel.socket(); 
/* 134 */             l -= System.currentTimeMillis() - l1;
/* 135 */             if (l <= 0L)
/* 136 */               throw new SocketTimeoutException(); 
/*     */           } 
/*     */         } finally {
/*     */           try {
/* 140 */             this.ssc.configureBlocking(true);
/* 141 */           } catch (ClosedChannelException closedChannelException) {}
/*     */         } 
/* 143 */       } catch (Exception exception) {
/* 144 */         Net.translateException(exception);
/*     */         assert false;
/* 146 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 152 */     this.ssc.close();
/*     */   }
/*     */   
/*     */   public ServerSocketChannel getChannel() {
/* 156 */     return this.ssc;
/*     */   }
/*     */   
/*     */   public boolean isBound() {
/* 160 */     return this.ssc.isBound();
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 164 */     return !this.ssc.isOpen();
/*     */   }
/*     */   
/*     */   public void setSoTimeout(int paramInt) throws SocketException {
/* 168 */     this.timeout = paramInt;
/*     */   }
/*     */   
/*     */   public int getSoTimeout() throws SocketException {
/* 172 */     return this.timeout;
/*     */   }
/*     */   
/*     */   public void setReuseAddress(boolean paramBoolean) throws SocketException {
/*     */     try {
/* 177 */       this.ssc.setOption(StandardSocketOptions.SO_REUSEADDR, Boolean.valueOf(paramBoolean));
/* 178 */     } catch (IOException iOException) {
/* 179 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getReuseAddress() throws SocketException {
/*     */     try {
/* 185 */       return ((Boolean)this.ssc.<Boolean>getOption(StandardSocketOptions.SO_REUSEADDR)).booleanValue();
/* 186 */     } catch (IOException iOException) {
/* 187 */       Net.translateToSocketException(iOException);
/* 188 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 193 */     if (!isBound())
/* 194 */       return "ServerSocket[unbound]"; 
/* 195 */     return "ServerSocket[addr=" + getInetAddress() + ",localport=" + 
/* 196 */       getLocalPort() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReceiveBufferSize(int paramInt) throws SocketException {
/* 201 */     if (paramInt <= 0)
/* 202 */       throw new IllegalArgumentException("size cannot be 0 or negative"); 
/*     */     try {
/* 204 */       this.ssc.setOption(StandardSocketOptions.SO_RCVBUF, Integer.valueOf(paramInt));
/* 205 */     } catch (IOException iOException) {
/* 206 */       Net.translateToSocketException(iOException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getReceiveBufferSize() throws SocketException {
/*     */     try {
/* 212 */       return ((Integer)this.ssc.<Integer>getOption(StandardSocketOptions.SO_RCVBUF)).intValue();
/* 213 */     } catch (IOException iOException) {
/* 214 */       Net.translateToSocketException(iOException);
/* 215 */       return -1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/ServerSocketAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */