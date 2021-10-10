/*     */ package sun.rmi.transport.proxy;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import sun.rmi.runtime.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpAwareServerSocket
/*     */   extends ServerSocket
/*     */ {
/*     */   public HttpAwareServerSocket(int paramInt) throws IOException {
/*  50 */     super(paramInt);
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
/*     */   public HttpAwareServerSocket(int paramInt1, int paramInt2) throws IOException {
/*  64 */     super(paramInt1, paramInt2);
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
/*     */   public Socket accept() throws IOException {
/*  80 */     Socket socket = super.accept();
/*     */     
/*  82 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
/*     */     
/*  84 */     RMIMasterSocketFactory.proxyLog.log(Log.BRIEF, "socket accepted (checking for POST)");
/*     */ 
/*     */     
/*  87 */     bufferedInputStream.mark(4);
/*     */ 
/*     */ 
/*     */     
/*  91 */     boolean bool = (bufferedInputStream.read() == 80 && bufferedInputStream.read() == 79 && bufferedInputStream.read() == 83 && bufferedInputStream.read() == 84) ? true : false;
/*  92 */     bufferedInputStream.reset();
/*     */     
/*  94 */     if (RMIMasterSocketFactory.proxyLog.isLoggable(Log.BRIEF)) {
/*  95 */       RMIMasterSocketFactory.proxyLog.log(Log.BRIEF, bool ? "POST found, HTTP socket returned" : "POST not found, direct socket returned");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (bool) {
/* 101 */       return new HttpReceiveSocket(socket, bufferedInputStream, null);
/*     */     }
/* 103 */     return new WrappedSocket(socket, bufferedInputStream, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 112 */     return "HttpAware" + super.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/proxy/HttpAwareServerSocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */