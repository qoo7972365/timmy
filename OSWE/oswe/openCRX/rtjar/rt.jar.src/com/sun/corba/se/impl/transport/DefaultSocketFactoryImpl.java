/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.pept.transport.Acceptor;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.ORBSocketFactory;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSocketFactoryImpl
/*     */   implements ORBSocketFactory
/*     */ {
/*     */   private ORB orb;
/*     */   
/*  51 */   private static final boolean keepAlive = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         
/*     */         public Boolean run()
/*     */         {
/*  56 */           String str = System.getProperty("com.sun.CORBA.transport.enableTcpKeepAlive");
/*  57 */           if (str != null) {
/*  58 */             return new Boolean(!"false".equalsIgnoreCase(str));
/*     */           }
/*  60 */           return Boolean.FALSE;
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*     */ 
/*     */   
/*     */   public void setORB(ORB paramORB) {
/*  67 */     this.orb = paramORB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket createServerSocket(String paramString, InetSocketAddress paramInetSocketAddress) throws IOException {
/*  74 */     ServerSocketChannel serverSocketChannel = null;
/*  75 */     ServerSocket serverSocket = null;
/*     */     
/*  77 */     if (this.orb.getORBData().acceptorSocketType().equals("SocketChannel")) {
/*  78 */       serverSocketChannel = ServerSocketChannel.open();
/*  79 */       serverSocket = serverSocketChannel.socket();
/*     */     } else {
/*  81 */       serverSocket = new ServerSocket();
/*     */     } 
/*  83 */     serverSocket.bind(paramInetSocketAddress);
/*  84 */     return serverSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket createSocket(String paramString, InetSocketAddress paramInetSocketAddress) throws IOException {
/*  91 */     SocketChannel socketChannel = null;
/*  92 */     Socket socket = null;
/*     */     
/*  94 */     if (this.orb.getORBData().connectionSocketType().equals("SocketChannel")) {
/*  95 */       socketChannel = SocketChannel.open(paramInetSocketAddress);
/*  96 */       socket = socketChannel.socket();
/*     */     } else {
/*     */       
/*  99 */       socket = new Socket(paramInetSocketAddress.getHostName(), paramInetSocketAddress.getPort());
/*     */     } 
/*     */ 
/*     */     
/* 103 */     socket.setTcpNoDelay(true);
/*     */     
/* 105 */     if (keepAlive) {
/* 106 */       socket.setKeepAlive(true);
/*     */     }
/* 108 */     return socket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAcceptedSocketOptions(Acceptor paramAcceptor, ServerSocket paramServerSocket, Socket paramSocket) throws SocketException {
/* 117 */     paramSocket.setTcpNoDelay(true);
/* 118 */     if (keepAlive)
/* 119 */       paramSocket.setKeepAlive(true); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/DefaultSocketFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */