/*     */ package java.net;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatagramSocketImpl
/*     */   implements SocketOptions
/*     */ {
/*     */   protected int localPort;
/*     */   protected FileDescriptor fd;
/*     */   DatagramSocket socket;
/*     */   
/*     */   int dataAvailable() {
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setDatagramSocket(DatagramSocket paramDatagramSocket) {
/*  63 */     this.socket = paramDatagramSocket;
/*     */   }
/*     */   
/*     */   DatagramSocket getDatagramSocket() {
/*  67 */     return this.socket;
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
/*     */   protected abstract void create() throws SocketException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void bind(int paramInt, InetAddress paramInetAddress) throws SocketException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void send(DatagramPacket paramDatagramPacket) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void connect(InetAddress paramInetAddress, int paramInt) throws SocketException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void disconnect() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int peek(InetAddress paramInetAddress) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int peekData(DatagramPacket paramDatagramPacket) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void receive(DatagramPacket paramDatagramPacket) throws IOException;
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
/*     */   protected abstract void setTTL(byte paramByte) throws IOException;
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
/*     */   protected abstract byte getTTL() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void setTimeToLive(int paramInt) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getTimeToLive() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void join(InetAddress paramInetAddress) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void leave(InetAddress paramInetAddress) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void joinGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void leaveGroup(SocketAddress paramSocketAddress, NetworkInterface paramNetworkInterface) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void close();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getLocalPort() {
/* 253 */     return this.localPort;
/*     */   }
/*     */   
/*     */   <T> void setOption(SocketOption<T> paramSocketOption, T paramT) throws IOException {
/* 257 */     if (paramSocketOption == StandardSocketOptions.SO_SNDBUF) {
/* 258 */       setOption(4097, paramT);
/* 259 */     } else if (paramSocketOption == StandardSocketOptions.SO_RCVBUF) {
/* 260 */       setOption(4098, paramT);
/* 261 */     } else if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR) {
/* 262 */       setOption(4, paramT);
/* 263 */     } else if (paramSocketOption == StandardSocketOptions.IP_TOS) {
/* 264 */       setOption(3, paramT);
/* 265 */     } else if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_IF && 
/* 266 */       getDatagramSocket() instanceof MulticastSocket) {
/* 267 */       setOption(31, paramT);
/* 268 */     } else if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_TTL && 
/* 269 */       getDatagramSocket() instanceof MulticastSocket) {
/* 270 */       if (!(paramT instanceof Integer)) {
/* 271 */         throw new IllegalArgumentException("not an integer");
/*     */       }
/* 273 */       setTimeToLive(((Integer)paramT).intValue());
/* 274 */     } else if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_LOOP && 
/* 275 */       getDatagramSocket() instanceof MulticastSocket) {
/* 276 */       setOption(18, paramT);
/*     */     } else {
/* 278 */       throw new UnsupportedOperationException("unsupported option");
/*     */     } 
/*     */   }
/*     */   
/*     */   <T> T getOption(SocketOption<T> paramSocketOption) throws IOException {
/* 283 */     if (paramSocketOption == StandardSocketOptions.SO_SNDBUF)
/* 284 */       return (T)getOption(4097); 
/* 285 */     if (paramSocketOption == StandardSocketOptions.SO_RCVBUF)
/* 286 */       return (T)getOption(4098); 
/* 287 */     if (paramSocketOption == StandardSocketOptions.SO_REUSEADDR)
/* 288 */       return (T)getOption(4); 
/* 289 */     if (paramSocketOption == StandardSocketOptions.IP_TOS)
/* 290 */       return (T)getOption(3); 
/* 291 */     if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_IF && 
/* 292 */       getDatagramSocket() instanceof MulticastSocket)
/* 293 */       return (T)getOption(31); 
/* 294 */     if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_TTL && 
/* 295 */       getDatagramSocket() instanceof MulticastSocket) {
/* 296 */       return (T)Integer.valueOf(getTimeToLive());
/*     */     }
/* 298 */     if (paramSocketOption == StandardSocketOptions.IP_MULTICAST_LOOP && 
/* 299 */       getDatagramSocket() instanceof MulticastSocket) {
/* 300 */       return (T)getOption(18);
/*     */     }
/* 302 */     throw new UnsupportedOperationException("unsupported option");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FileDescriptor getFileDescriptor() {
/* 312 */     return this.fd;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/DatagramSocketImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */