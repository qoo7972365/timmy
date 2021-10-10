/*     */ package com.sun.corba.se.impl.legacy.connection;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPAddress;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*     */ import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
/*     */ import com.sun.corba.se.spi.legacy.connection.ORBSocketFactory;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.SocketInfo;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSocketFactory
/*     */   implements ORBSocketFactory
/*     */ {
/*     */   private ORB orb;
/*  56 */   private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.transport");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setORB(ORB paramORB) {
/*  65 */     this.orb = paramORB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSocket createServerSocket(String paramString, int paramInt) throws IOException {
/*     */     ServerSocket serverSocket;
/*  72 */     if (!paramString.equals("IIOP_CLEAR_TEXT")) {
/*  73 */       throw wrapper.defaultCreateServerSocketGivenNonIiopClearText(paramString);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (this.orb.getORBData().acceptorSocketType().equals("SocketChannel")) {
/*     */       
/*  80 */       ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
/*  81 */       serverSocket = serverSocketChannel.socket();
/*     */     } else {
/*  83 */       serverSocket = new ServerSocket();
/*     */     } 
/*  85 */     serverSocket.bind(new InetSocketAddress(paramInt));
/*  86 */     return serverSocket;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketInfo getEndPointInfo(ORB paramORB, IOR paramIOR, SocketInfo paramSocketInfo) {
/*  94 */     IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)paramIOR.getProfile().getTaggedProfileTemplate();
/*  95 */     IIOPAddress iIOPAddress = iIOPProfileTemplate.getPrimaryAddress();
/*     */     
/*  97 */     return new EndPointInfoImpl("IIOP_CLEAR_TEXT", iIOPAddress
/*  98 */         .getPort(), iIOPAddress
/*  99 */         .getHost().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Socket createSocket(SocketInfo paramSocketInfo) throws IOException, GetEndPointInfoAgainException {
/*     */     Socket socket;
/* 109 */     if (this.orb.getORBData().acceptorSocketType().equals("SocketChannel")) {
/*     */ 
/*     */       
/* 112 */       InetSocketAddress inetSocketAddress = new InetSocketAddress(paramSocketInfo.getHost(), paramSocketInfo.getPort());
/* 113 */       SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
/* 114 */       socket = socketChannel.socket();
/*     */     } else {
/*     */       
/* 117 */       socket = new Socket(paramSocketInfo.getHost(), paramSocketInfo.getPort());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 122 */       socket.setTcpNoDelay(true);
/* 123 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 126 */     return socket;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/legacy/connection/DefaultSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */