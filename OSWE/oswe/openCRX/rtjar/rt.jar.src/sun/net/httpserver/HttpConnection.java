/*     */ package sun.net.httpserver;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.logging.Logger;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HttpConnection
/*     */ {
/*     */   HttpContextImpl context;
/*     */   SSLEngine engine;
/*     */   SSLContext sslContext;
/*     */   SSLStreams sslStreams;
/*     */   InputStream i;
/*     */   InputStream raw;
/*     */   OutputStream rawout;
/*     */   SocketChannel chan;
/*     */   SelectionKey selectionKey;
/*     */   String protocol;
/*     */   long time;
/*     */   volatile long creationTime;
/*     */   volatile long rspStartedTime;
/*     */   int remaining;
/*     */   boolean closed = false;
/*     */   Logger logger;
/*     */   volatile State state;
/*     */   
/*     */   public enum State
/*     */   {
/*  64 */     IDLE, REQUEST, RESPONSE;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  68 */     String str = null;
/*  69 */     if (this.chan != null) {
/*  70 */       str = this.chan.toString();
/*     */     }
/*  72 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setChannel(SocketChannel paramSocketChannel) {
/*  79 */     this.chan = paramSocketChannel;
/*     */   }
/*     */   
/*     */   void setContext(HttpContextImpl paramHttpContextImpl) {
/*  83 */     this.context = paramHttpContextImpl;
/*     */   }
/*     */   
/*     */   State getState() {
/*  87 */     return this.state;
/*     */   }
/*     */   
/*     */   void setState(State paramState) {
/*  91 */     this.state = paramState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setParameters(InputStream paramInputStream1, OutputStream paramOutputStream, SocketChannel paramSocketChannel, SSLEngine paramSSLEngine, SSLStreams paramSSLStreams, SSLContext paramSSLContext, String paramString, HttpContextImpl paramHttpContextImpl, InputStream paramInputStream2) {
/* 100 */     this.context = paramHttpContextImpl;
/* 101 */     this.i = paramInputStream1;
/* 102 */     this.rawout = paramOutputStream;
/* 103 */     this.raw = paramInputStream2;
/* 104 */     this.protocol = paramString;
/* 105 */     this.engine = paramSSLEngine;
/* 106 */     this.chan = paramSocketChannel;
/* 107 */     this.sslContext = paramSSLContext;
/* 108 */     this.sslStreams = paramSSLStreams;
/* 109 */     this.logger = paramHttpContextImpl.getLogger();
/*     */   }
/*     */   
/*     */   SocketChannel getChannel() {
/* 113 */     return this.chan;
/*     */   }
/*     */   
/*     */   synchronized void close() {
/* 117 */     if (this.closed) {
/*     */       return;
/*     */     }
/* 120 */     this.closed = true;
/* 121 */     if (this.logger != null && this.chan != null) {
/* 122 */       this.logger.finest("Closing connection: " + this.chan.toString());
/*     */     }
/*     */     
/* 125 */     if (!this.chan.isOpen()) {
/* 126 */       ServerImpl.dprint("Channel already closed");
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 131 */       if (this.raw != null) {
/* 132 */         this.raw.close();
/*     */       }
/* 134 */     } catch (IOException iOException) {
/* 135 */       ServerImpl.dprint(iOException);
/*     */     } 
/*     */     try {
/* 138 */       if (this.rawout != null) {
/* 139 */         this.rawout.close();
/*     */       }
/* 141 */     } catch (IOException iOException) {
/* 142 */       ServerImpl.dprint(iOException);
/*     */     } 
/*     */     try {
/* 145 */       if (this.sslStreams != null) {
/* 146 */         this.sslStreams.close();
/*     */       }
/* 148 */     } catch (IOException iOException) {
/* 149 */       ServerImpl.dprint(iOException);
/*     */     } 
/*     */     try {
/* 152 */       this.chan.close();
/* 153 */     } catch (IOException iOException) {
/* 154 */       ServerImpl.dprint(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setRemaining(int paramInt) {
/* 162 */     this.remaining = paramInt;
/*     */   }
/*     */   
/*     */   int getRemaining() {
/* 166 */     return this.remaining;
/*     */   }
/*     */   
/*     */   SelectionKey getSelectionKey() {
/* 170 */     return this.selectionKey;
/*     */   }
/*     */   
/*     */   InputStream getInputStream() {
/* 174 */     return this.i;
/*     */   }
/*     */   
/*     */   OutputStream getRawOutputStream() {
/* 178 */     return this.rawout;
/*     */   }
/*     */   
/*     */   String getProtocol() {
/* 182 */     return this.protocol;
/*     */   }
/*     */   
/*     */   SSLEngine getSSLEngine() {
/* 186 */     return this.engine;
/*     */   }
/*     */   
/*     */   SSLContext getSSLContext() {
/* 190 */     return this.sslContext;
/*     */   }
/*     */   
/*     */   HttpContextImpl getHttpContext() {
/* 194 */     return this.context;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/net/httpserver/HttpConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */