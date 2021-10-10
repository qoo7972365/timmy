/*     */ package sun.rmi.transport.tcp;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.security.AccessController;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ConnectionMultiplexer
/*     */ {
/*  50 */   static int logLevel = LogStream.parseLevel(getLogLevel());
/*     */   
/*     */   private static String getLogLevel() {
/*  53 */     return AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.transport.tcp.multiplex.logLevel"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   static final Log multiplexLog = Log.getLog("sun.rmi.transport.tcp.multiplex", "multiplex", logLevel);
/*     */ 
/*     */   
/*     */   private static final int OPEN = 225;
/*     */ 
/*     */   
/*     */   private static final int CLOSE = 226;
/*     */ 
/*     */   
/*     */   private static final int CLOSEACK = 227;
/*     */ 
/*     */   
/*     */   private static final int REQUEST = 228;
/*     */ 
/*     */   
/*     */   private static final int TRANSMIT = 229;
/*     */ 
/*     */   
/*     */   private TCPChannel channel;
/*     */   
/*     */   private InputStream in;
/*     */   
/*     */   private OutputStream out;
/*     */   
/*     */   private boolean orig;
/*     */   
/*     */   private DataInputStream dataIn;
/*     */   
/*     */   private DataOutputStream dataOut;
/*     */   
/*  89 */   private Hashtable<Integer, MultiplexConnectionInfo> connectionTable = new Hashtable<>(7);
/*     */ 
/*     */   
/*  92 */   private int numConnections = 0;
/*     */ 
/*     */   
/*     */   private static final int maxConnections = 256;
/*     */ 
/*     */   
/*  98 */   private int lastID = 4097;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean alive = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionMultiplexer(TCPChannel paramTCPChannel, InputStream paramInputStream, OutputStream paramOutputStream, boolean paramBoolean) {
/* 119 */     this.channel = paramTCPChannel;
/* 120 */     this.in = paramInputStream;
/* 121 */     this.out = paramOutputStream;
/* 122 */     this.orig = paramBoolean;
/*     */     
/* 124 */     this.dataIn = new DataInputStream(paramInputStream);
/* 125 */     this.dataOut = new DataOutputStream(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() throws IOException {
/*     */     try {
/*     */       int i;
/*     */       while (true) {
/*     */         int j, k;
/*     */         MultiplexConnectionInfo multiplexConnectionInfo;
/*     */         TCPConnection tCPConnection;
/* 140 */         i = this.dataIn.readUnsignedByte();
/* 141 */         switch (i) {
/*     */ 
/*     */           
/*     */           case 225:
/* 145 */             j = this.dataIn.readUnsignedShort();
/*     */             
/* 147 */             if (multiplexLog.isLoggable(Log.VERBOSE)) {
/* 148 */               multiplexLog.log(Log.VERBOSE, "operation  OPEN " + j);
/*     */             }
/*     */             
/* 151 */             multiplexConnectionInfo = this.connectionTable.get(Integer.valueOf(j));
/* 152 */             if (multiplexConnectionInfo != null) {
/* 153 */               throw new IOException("OPEN: Connection ID already exists");
/*     */             }
/* 155 */             multiplexConnectionInfo = new MultiplexConnectionInfo(j);
/* 156 */             multiplexConnectionInfo.in = new MultiplexInputStream(this, multiplexConnectionInfo, 2048);
/* 157 */             multiplexConnectionInfo.out = new MultiplexOutputStream(this, multiplexConnectionInfo, 2048);
/* 158 */             synchronized (this.connectionTable) {
/* 159 */               this.connectionTable.put(Integer.valueOf(j), multiplexConnectionInfo);
/* 160 */               this.numConnections++;
/*     */             } 
/*     */             
/* 163 */             tCPConnection = new TCPConnection(this.channel, multiplexConnectionInfo.in, multiplexConnectionInfo.out);
/* 164 */             this.channel.acceptMultiplexConnection(tCPConnection);
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 226:
/* 169 */             j = this.dataIn.readUnsignedShort();
/*     */             
/* 171 */             if (multiplexLog.isLoggable(Log.VERBOSE)) {
/* 172 */               multiplexLog.log(Log.VERBOSE, "operation  CLOSE " + j);
/*     */             }
/*     */             
/* 175 */             multiplexConnectionInfo = this.connectionTable.get(Integer.valueOf(j));
/* 176 */             if (multiplexConnectionInfo == null) {
/* 177 */               throw new IOException("CLOSE: Invalid connection ID");
/*     */             }
/* 179 */             multiplexConnectionInfo.in.disconnect();
/* 180 */             multiplexConnectionInfo.out.disconnect();
/* 181 */             if (!multiplexConnectionInfo.closed)
/* 182 */               sendCloseAck(multiplexConnectionInfo); 
/* 183 */             synchronized (this.connectionTable) {
/* 184 */               this.connectionTable.remove(Integer.valueOf(j));
/* 185 */               this.numConnections--;
/*     */             } 
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 227:
/* 191 */             j = this.dataIn.readUnsignedShort();
/*     */             
/* 193 */             if (multiplexLog.isLoggable(Log.VERBOSE)) {
/* 194 */               multiplexLog.log(Log.VERBOSE, "operation  CLOSEACK " + j);
/*     */             }
/*     */ 
/*     */             
/* 198 */             multiplexConnectionInfo = this.connectionTable.get(Integer.valueOf(j));
/* 199 */             if (multiplexConnectionInfo == null) {
/* 200 */               throw new IOException("CLOSEACK: Invalid connection ID");
/*     */             }
/* 202 */             if (!multiplexConnectionInfo.closed) {
/* 203 */               throw new IOException("CLOSEACK: Connection not closed");
/*     */             }
/* 205 */             multiplexConnectionInfo.in.disconnect();
/* 206 */             multiplexConnectionInfo.out.disconnect();
/* 207 */             synchronized (this.connectionTable) {
/* 208 */               this.connectionTable.remove(Integer.valueOf(j));
/* 209 */               this.numConnections--;
/*     */             } 
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 228:
/* 215 */             j = this.dataIn.readUnsignedShort();
/* 216 */             multiplexConnectionInfo = this.connectionTable.get(Integer.valueOf(j));
/* 217 */             if (multiplexConnectionInfo == null) {
/* 218 */               throw new IOException("REQUEST: Invalid connection ID");
/*     */             }
/* 220 */             k = this.dataIn.readInt();
/*     */             
/* 222 */             if (multiplexLog.isLoggable(Log.VERBOSE)) {
/* 223 */               multiplexLog.log(Log.VERBOSE, "operation  REQUEST " + j + ": " + k);
/*     */             }
/*     */ 
/*     */             
/* 227 */             multiplexConnectionInfo.out.request(k);
/*     */             continue;
/*     */ 
/*     */           
/*     */           case 229:
/* 232 */             j = this.dataIn.readUnsignedShort();
/* 233 */             multiplexConnectionInfo = this.connectionTable.get(Integer.valueOf(j));
/* 234 */             if (multiplexConnectionInfo == null)
/* 235 */               throw new IOException("SEND: Invalid connection ID"); 
/* 236 */             k = this.dataIn.readInt();
/*     */             
/* 238 */             if (multiplexLog.isLoggable(Log.VERBOSE)) {
/* 239 */               multiplexLog.log(Log.VERBOSE, "operation  TRANSMIT " + j + ": " + k);
/*     */             }
/*     */ 
/*     */             
/* 243 */             multiplexConnectionInfo.in.receive(k, this.dataIn); continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 247 */       throw new IOException("Invalid operation: " + 
/* 248 */           Integer.toHexString(i));
/*     */     }
/*     */     finally {
/*     */       
/* 252 */       shutDown();
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
/*     */   public synchronized TCPConnection openConnection() throws IOException {
/*     */     int i;
/*     */     do {
/* 267 */       this.lastID = ++this.lastID & 0x7FFF;
/* 268 */       i = this.lastID;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       if (!this.orig)
/* 274 */         continue;  i |= 0x8000;
/* 275 */     } while (this.connectionTable.get(Integer.valueOf(i)) != null);
/*     */ 
/*     */     
/* 278 */     MultiplexConnectionInfo multiplexConnectionInfo = new MultiplexConnectionInfo(i);
/* 279 */     multiplexConnectionInfo.in = new MultiplexInputStream(this, multiplexConnectionInfo, 2048);
/* 280 */     multiplexConnectionInfo.out = new MultiplexOutputStream(this, multiplexConnectionInfo, 2048);
/*     */ 
/*     */     
/* 283 */     synchronized (this.connectionTable) {
/* 284 */       if (!this.alive)
/* 285 */         throw new IOException("Multiplexer connection dead"); 
/* 286 */       if (this.numConnections >= 256) {
/* 287 */         throw new IOException("Cannot exceed 256 simultaneous multiplexed connections");
/*     */       }
/* 289 */       this.connectionTable.put(Integer.valueOf(i), multiplexConnectionInfo);
/* 290 */       this.numConnections++;
/*     */     } 
/*     */ 
/*     */     
/* 294 */     synchronized (this.dataOut) {
/*     */       try {
/* 296 */         this.dataOut.writeByte(225);
/* 297 */         this.dataOut.writeShort(i);
/* 298 */         this.dataOut.flush();
/* 299 */       } catch (IOException iOException) {
/* 300 */         multiplexLog.log(Log.BRIEF, "exception: ", iOException);
/*     */         
/* 302 */         shutDown();
/* 303 */         throw iOException;
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     return new TCPConnection(this.channel, multiplexConnectionInfo.in, multiplexConnectionInfo.out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutDown() {
/* 316 */     synchronized (this.connectionTable) {
/*     */       
/* 318 */       if (!this.alive)
/*     */         return; 
/* 320 */       this.alive = false;
/*     */ 
/*     */       
/* 323 */       Enumeration<MultiplexConnectionInfo> enumeration = this.connectionTable.elements();
/* 324 */       while (enumeration.hasMoreElements()) {
/* 325 */         MultiplexConnectionInfo multiplexConnectionInfo = enumeration.nextElement();
/* 326 */         multiplexConnectionInfo.in.disconnect();
/* 327 */         multiplexConnectionInfo.out.disconnect();
/*     */       } 
/* 329 */       this.connectionTable.clear();
/* 330 */       this.numConnections = 0;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 335 */       this.in.close();
/* 336 */     } catch (IOException iOException) {}
/*     */     
/*     */     try {
/* 339 */       this.out.close();
/* 340 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendRequest(MultiplexConnectionInfo paramMultiplexConnectionInfo, int paramInt) throws IOException {
/* 351 */     synchronized (this.dataOut) {
/* 352 */       if (this.alive && !paramMultiplexConnectionInfo.closed) {
/*     */         try {
/* 354 */           this.dataOut.writeByte(228);
/* 355 */           this.dataOut.writeShort(paramMultiplexConnectionInfo.id);
/* 356 */           this.dataOut.writeInt(paramInt);
/* 357 */           this.dataOut.flush();
/* 358 */         } catch (IOException iOException) {
/* 359 */           multiplexLog.log(Log.BRIEF, "exception: ", iOException);
/*     */           
/* 361 */           shutDown();
/* 362 */           throw iOException;
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
/*     */ 
/*     */ 
/*     */   
/*     */   void sendTransmit(MultiplexConnectionInfo paramMultiplexConnectionInfo, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 377 */     synchronized (this.dataOut) {
/* 378 */       if (this.alive && !paramMultiplexConnectionInfo.closed) {
/*     */         try {
/* 380 */           this.dataOut.writeByte(229);
/* 381 */           this.dataOut.writeShort(paramMultiplexConnectionInfo.id);
/* 382 */           this.dataOut.writeInt(paramInt2);
/* 383 */           this.dataOut.write(paramArrayOfbyte, paramInt1, paramInt2);
/* 384 */           this.dataOut.flush();
/* 385 */         } catch (IOException iOException) {
/* 386 */           multiplexLog.log(Log.BRIEF, "exception: ", iOException);
/*     */           
/* 388 */           shutDown();
/* 389 */           throw iOException;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendClose(MultiplexConnectionInfo paramMultiplexConnectionInfo) throws IOException {
/* 400 */     paramMultiplexConnectionInfo.out.disconnect();
/* 401 */     synchronized (this.dataOut) {
/* 402 */       if (this.alive && !paramMultiplexConnectionInfo.closed) {
/*     */         try {
/* 404 */           this.dataOut.writeByte(226);
/* 405 */           this.dataOut.writeShort(paramMultiplexConnectionInfo.id);
/* 406 */           this.dataOut.flush();
/* 407 */           paramMultiplexConnectionInfo.closed = true;
/* 408 */         } catch (IOException iOException) {
/* 409 */           multiplexLog.log(Log.BRIEF, "exception: ", iOException);
/*     */           
/* 411 */           shutDown();
/* 412 */           throw iOException;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void sendCloseAck(MultiplexConnectionInfo paramMultiplexConnectionInfo) throws IOException {
/* 423 */     synchronized (this.dataOut) {
/* 424 */       if (this.alive && !paramMultiplexConnectionInfo.closed) {
/*     */         try {
/* 426 */           this.dataOut.writeByte(227);
/* 427 */           this.dataOut.writeShort(paramMultiplexConnectionInfo.id);
/* 428 */           this.dataOut.flush();
/* 429 */           paramMultiplexConnectionInfo.closed = true;
/* 430 */         } catch (IOException iOException) {
/* 431 */           multiplexLog.log(Log.BRIEF, "exception: ", iOException);
/*     */           
/* 433 */           shutDown();
/* 434 */           throw iOException;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 444 */     super.finalize();
/* 445 */     shutDown();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/tcp/ConnectionMultiplexer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */