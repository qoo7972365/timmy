/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpReceiveSocket
/*     */   extends WrappedSocket
/*     */   implements RMISocketInfo
/*     */ {
/*     */   private boolean headerSent = false;
/*     */   
/*     */   public HttpReceiveSocket(Socket paramSocket, InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
/*  67 */     super(paramSocket, paramInputStream, paramOutputStream);
/*     */     
/*  69 */     this
/*  70 */       .in = new HttpInputStream((paramInputStream != null) ? paramInputStream : paramSocket.getInputStream());
/*  71 */     this
/*  72 */       .out = (paramOutputStream != null) ? paramOutputStream : paramSocket.getOutputStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReusable() {
/*  80 */     return false;
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
/*     */   public InetAddress getInetAddress() {
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/* 102 */     if (!this.headerSent) {
/* 103 */       DataOutputStream dataOutputStream = new DataOutputStream(this.out);
/* 104 */       dataOutputStream.writeBytes("HTTP/1.0 200 OK\r\n");
/* 105 */       dataOutputStream.flush();
/* 106 */       this.headerSent = true;
/* 107 */       this.out = new HttpOutputStream(this.out);
/*     */     } 
/* 109 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 117 */     getOutputStream().close();
/* 118 */     this.socket.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return "HttpReceive" + this.socket.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/HttpReceiveSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */