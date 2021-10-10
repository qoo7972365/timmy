/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SocketHandler
/*     */   extends StreamHandler
/*     */ {
/*     */   private Socket sock;
/*     */   private String host;
/*     */   private int port;
/*     */   
/*     */   private void configure() {
/*  90 */     LogManager logManager = LogManager.getLogManager();
/*  91 */     String str = getClass().getName();
/*     */     
/*  93 */     setLevel(logManager.getLevelProperty(str + ".level", Level.ALL));
/*  94 */     setFilter(logManager.getFilterProperty(str + ".filter", null));
/*  95 */     setFormatter(logManager.getFormatterProperty(str + ".formatter", new XMLFormatter()));
/*     */     try {
/*  97 */       setEncoding(logManager.getStringProperty(str + ".encoding", null));
/*  98 */     } catch (Exception exception) {
/*     */       try {
/* 100 */         setEncoding((String)null);
/* 101 */       } catch (Exception exception1) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     this.port = logManager.getIntProperty(str + ".port", 0);
/* 107 */     this.host = logManager.getStringProperty(str + ".host", null);
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
/*     */   public SocketHandler() throws IOException {
/* 121 */     this.sealed = false;
/* 122 */     configure();
/*     */     
/*     */     try {
/* 125 */       connect();
/* 126 */     } catch (IOException iOException) {
/* 127 */       System.err.println("SocketHandler: connect failed to " + this.host + ":" + this.port);
/* 128 */       throw iOException;
/*     */     } 
/* 130 */     this.sealed = true;
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
/*     */   public SocketHandler(String paramString, int paramInt) throws IOException {
/* 149 */     this.sealed = false;
/* 150 */     configure();
/* 151 */     this.sealed = true;
/* 152 */     this.port = paramInt;
/* 153 */     this.host = paramString;
/* 154 */     connect();
/*     */   }
/*     */ 
/*     */   
/*     */   private void connect() throws IOException {
/* 159 */     if (this.port == 0) {
/* 160 */       throw new IllegalArgumentException("Bad port: " + this.port);
/*     */     }
/* 162 */     if (this.host == null) {
/* 163 */       throw new IllegalArgumentException("Null host name: " + this.host);
/*     */     }
/*     */ 
/*     */     
/* 167 */     this.sock = new Socket(this.host, this.port);
/* 168 */     OutputStream outputStream = this.sock.getOutputStream();
/* 169 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
/* 170 */     setOutputStream(bufferedOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws SecurityException {
/* 181 */     super.close();
/* 182 */     if (this.sock != null) {
/*     */       try {
/* 184 */         this.sock.close();
/* 185 */       } catch (IOException iOException) {}
/*     */     }
/*     */ 
/*     */     
/* 189 */     this.sock = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void publish(LogRecord paramLogRecord) {
/* 200 */     if (!isLoggable(paramLogRecord)) {
/*     */       return;
/*     */     }
/* 203 */     super.publish(paramLogRecord);
/* 204 */     flush();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/SocketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */