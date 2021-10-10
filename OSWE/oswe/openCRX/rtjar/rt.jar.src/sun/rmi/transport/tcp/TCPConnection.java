/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.transport.Channel;
/*     */ import sun.rmi.transport.Connection;
/*     */ import sun.rmi.transport.proxy.RMISocketInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCPConnection
/*     */   implements Connection
/*     */ {
/*     */   private Socket socket;
/*     */   private Channel channel;
/*  42 */   private InputStream in = null;
/*  43 */   private OutputStream out = null;
/*  44 */   private long expiration = Long.MAX_VALUE;
/*  45 */   private long lastuse = Long.MIN_VALUE;
/*  46 */   private long roundtrip = 5L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TCPConnection(TCPChannel paramTCPChannel, Socket paramSocket, InputStream paramInputStream, OutputStream paramOutputStream) {
/*  54 */     this.socket = paramSocket;
/*  55 */     this.channel = paramTCPChannel;
/*  56 */     this.in = paramInputStream;
/*  57 */     this.out = paramOutputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TCPConnection(TCPChannel paramTCPChannel, InputStream paramInputStream, OutputStream paramOutputStream) {
/*  66 */     this(paramTCPChannel, null, paramInputStream, paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TCPConnection(TCPChannel paramTCPChannel, Socket paramSocket) {
/*  75 */     this(paramTCPChannel, paramSocket, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/*  83 */     if (this.out == null)
/*  84 */       this.out = new BufferedOutputStream(this.socket.getOutputStream()); 
/*  85 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseOutputStream() throws IOException {
/*  93 */     if (this.out != null) {
/*  94 */       this.out.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 102 */     if (this.in == null)
/* 103 */       this.in = new BufferedInputStream(this.socket.getInputStream()); 
/* 104 */     return this.in;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseInputStream() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusable() {
/* 123 */     if (this.socket != null && this.socket instanceof RMISocketInfo) {
/* 124 */       return ((RMISocketInfo)this.socket).isReusable();
/*     */     }
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setExpiration(long paramLong) {
/* 135 */     this.expiration = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setLastUseTime(long paramLong) {
/* 146 */     this.lastuse = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean expired(long paramLong) {
/* 156 */     return (this.expiration <= paramLong);
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
/*     */   public boolean isDead() {
/*     */     InputStream inputStream;
/*     */     OutputStream outputStream;
/* 175 */     long l = System.currentTimeMillis();
/* 176 */     if (this.roundtrip > 0L && l < this.lastuse + this.roundtrip) {
/* 177 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 181 */       inputStream = getInputStream();
/* 182 */       outputStream = getOutputStream();
/* 183 */     } catch (IOException iOException) {
/* 184 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 188 */     int i = 0;
/*     */     try {
/* 190 */       outputStream.write(82);
/* 191 */       outputStream.flush();
/* 192 */       i = inputStream.read();
/* 193 */     } catch (IOException iOException) {
/* 194 */       TCPTransport.tcpLog.log(Log.VERBOSE, "exception: ", iOException);
/* 195 */       TCPTransport.tcpLog.log(Log.BRIEF, "server ping failed");
/*     */       
/* 197 */       return true;
/*     */     } 
/*     */     
/* 200 */     if (i == 83) {
/*     */       
/* 202 */       this.roundtrip = (System.currentTimeMillis() - l) * 2L;
/*     */       
/* 204 */       return false;
/*     */     } 
/*     */     
/* 207 */     if (TCPTransport.tcpLog.isLoggable(Log.BRIEF)) {
/* 208 */       TCPTransport.tcpLog.log(Log.BRIEF, (i == -1) ? "server has been deactivated" : ("server protocol error: ping response = " + i));
/*     */     }
/*     */ 
/*     */     
/* 212 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 219 */     TCPTransport.tcpLog.log(Log.BRIEF, "close connection");
/*     */     
/* 221 */     if (this.socket != null) {
/* 222 */       this.socket.close();
/*     */     } else {
/* 224 */       this.in.close();
/* 225 */       this.out.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Channel getChannel() {
/* 234 */     return this.channel;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/TCPConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */