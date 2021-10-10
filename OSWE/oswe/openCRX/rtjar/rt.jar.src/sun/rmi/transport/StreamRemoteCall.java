/*     */ package sun.rmi.transport;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.UnmarshalException;
/*     */ import java.rmi.server.ObjID;
/*     */ import java.rmi.server.RemoteCall;
/*     */ import java.security.AccessController;
/*     */ import sun.misc.ObjectInputFilter;
/*     */ import sun.rmi.runtime.Log;
/*     */ import sun.rmi.server.UnicastRef;
/*     */ import sun.rmi.transport.tcp.TCPEndpoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamRemoteCall
/*     */   implements RemoteCall
/*     */ {
/*  54 */   private ConnectionInputStream in = null;
/*  55 */   private ConnectionOutputStream out = null;
/*     */   private Connection conn;
/*  57 */   private ObjectInputFilter filter = null;
/*     */   private boolean resultStarted = false;
/*  59 */   private Exception serverException = null;
/*     */   
/*     */   public StreamRemoteCall(Connection paramConnection) {
/*  62 */     this.conn = paramConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamRemoteCall(Connection paramConnection, ObjID paramObjID, int paramInt, long paramLong) throws RemoteException {
/*     */     try {
/*  69 */       this.conn = paramConnection;
/*  70 */       Transport.transportLog.log(Log.VERBOSE, "write remote call header...");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       this.conn.getOutputStream().write(80);
/*  76 */       getOutputStream();
/*  77 */       paramObjID.write(this.out);
/*     */       
/*  79 */       this.out.writeInt(paramInt);
/*  80 */       this.out.writeLong(paramLong);
/*  81 */     } catch (IOException iOException) {
/*  82 */       throw new MarshalException("Error marshaling call header", iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  90 */     return this.conn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOutput getOutputStream() throws IOException {
/*  98 */     return getOutputStream(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ObjectOutput getOutputStream(boolean paramBoolean) throws IOException {
/* 104 */     if (this.out == null) {
/* 105 */       Transport.transportLog.log(Log.VERBOSE, "getting output stream");
/*     */       
/* 107 */       this.out = new ConnectionOutputStream(this.conn, paramBoolean);
/*     */     } 
/* 109 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseOutputStream() throws IOException {
/*     */     try {
/* 118 */       if (this.out != null) {
/*     */         try {
/* 120 */           this.out.flush();
/*     */         } finally {
/* 122 */           this.out.done();
/*     */         } 
/*     */       }
/* 125 */       this.conn.releaseOutputStream();
/*     */     } finally {
/* 127 */       this.out = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setObjectInputFilter(ObjectInputFilter paramObjectInputFilter) {
/* 132 */     if (this.in != null) {
/* 133 */       throw new IllegalStateException("set filter must occur before calling getInputStream");
/*     */     }
/* 135 */     this.filter = paramObjectInputFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectInput getInputStream() throws IOException {
/* 143 */     if (this.in == null) {
/* 144 */       Transport.transportLog.log(Log.VERBOSE, "getting input stream");
/*     */       
/* 146 */       this.in = new ConnectionInputStream(this.conn.getInputStream());
/* 147 */       if (this.filter != null) {
/* 148 */         AccessController.doPrivileged(() -> {
/*     */               ObjectInputFilter.Config.setObjectInputFilter(this.in, this.filter);
/*     */               return null;
/*     */             });
/*     */       }
/*     */     } 
/* 154 */     return this.in;
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
/*     */   public void releaseInputStream() throws IOException {
/*     */     try {
/* 167 */       if (this.in != null) {
/*     */         
/*     */         try {
/* 170 */           this.in.done();
/* 171 */         } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */         
/* 175 */         this.in.registerRefs();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 180 */         this.in.done(this.conn);
/*     */       } 
/* 182 */       this.conn.releaseInputStream();
/*     */     } finally {
/* 184 */       this.in = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void discardPendingRefs() {
/* 192 */     this.in.discardRefs();
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
/*     */   public ObjectOutput getResultStream(boolean paramBoolean) throws IOException {
/* 206 */     if (this.resultStarted) {
/* 207 */       throw new StreamCorruptedException("result already in progress");
/*     */     }
/* 209 */     this.resultStarted = true;
/*     */ 
/*     */ 
/*     */     
/* 213 */     DataOutputStream dataOutputStream = new DataOutputStream(this.conn.getOutputStream());
/* 214 */     dataOutputStream.writeByte(81);
/* 215 */     getOutputStream(true);
/*     */     
/* 217 */     if (paramBoolean) {
/* 218 */       this.out.writeByte(1);
/*     */     } else {
/* 220 */       this.out.writeByte(2);
/* 221 */     }  this.out.writeID();
/* 222 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeCall() throws Exception {
/*     */     byte b;
/*     */     Object object;
/* 233 */     DGCAckHandler dGCAckHandler = null;
/*     */     try {
/* 235 */       if (this.out != null) {
/* 236 */         dGCAckHandler = this.out.getDGCAckHandler();
/*     */       }
/* 238 */       releaseOutputStream();
/* 239 */       object = new DataInputStream(this.conn.getInputStream());
/* 240 */       byte b1 = object.readByte();
/* 241 */       if (b1 != 81) {
/* 242 */         if (Transport.transportLog.isLoggable(Log.BRIEF)) {
/* 243 */           Transport.transportLog.log(Log.BRIEF, "transport return code invalid: " + b1);
/*     */         }
/*     */         
/* 246 */         throw new UnmarshalException("Transport return code invalid");
/*     */       } 
/* 248 */       getInputStream();
/* 249 */       b = this.in.readByte();
/* 250 */       this.in.readID();
/* 251 */     } catch (UnmarshalException null) {
/* 252 */       throw object;
/* 253 */     } catch (IOException null) {
/* 254 */       throw new UnmarshalException("Error unmarshaling return header", object);
/*     */     } finally {
/*     */       
/* 257 */       if (dGCAckHandler != null) {
/* 258 */         dGCAckHandler.release();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 263 */     switch (b) {
/*     */       case 1:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 2:
/*     */         try {
/* 270 */           object = this.in.readObject();
/* 271 */         } catch (Exception exception) {
/* 272 */           discardPendingRefs();
/* 273 */           throw new UnmarshalException("Error unmarshaling return", exception);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 278 */         if (object instanceof Exception) {
/* 279 */           exceptionReceivedFromServer((Exception)object); break;
/*     */         } 
/* 281 */         discardPendingRefs();
/* 282 */         throw new UnmarshalException("Return type not Exception");
/*     */     } 
/*     */ 
/*     */     
/* 286 */     if (Transport.transportLog.isLoggable(Log.BRIEF)) {
/* 287 */       Transport.transportLog.log(Log.BRIEF, "return code invalid: " + b);
/*     */     }
/*     */     
/* 290 */     throw new UnmarshalException("Return code invalid");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void exceptionReceivedFromServer(Exception paramException) throws Exception {
/* 300 */     this.serverException = paramException;
/*     */     
/* 302 */     StackTraceElement[] arrayOfStackTraceElement1 = paramException.getStackTrace();
/* 303 */     StackTraceElement[] arrayOfStackTraceElement2 = (new Throwable()).getStackTrace();
/* 304 */     StackTraceElement[] arrayOfStackTraceElement3 = new StackTraceElement[arrayOfStackTraceElement1.length + arrayOfStackTraceElement2.length];
/*     */     
/* 306 */     System.arraycopy(arrayOfStackTraceElement1, 0, arrayOfStackTraceElement3, 0, arrayOfStackTraceElement1.length);
/*     */     
/* 308 */     System.arraycopy(arrayOfStackTraceElement2, 0, arrayOfStackTraceElement3, arrayOfStackTraceElement1.length, arrayOfStackTraceElement2.length);
/*     */     
/* 310 */     paramException.setStackTrace(arrayOfStackTraceElement3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (UnicastRef.clientCallLog.isLoggable(Log.BRIEF)) {
/*     */       
/* 318 */       TCPEndpoint tCPEndpoint = (TCPEndpoint)this.conn.getChannel().getEndpoint();
/* 319 */       UnicastRef.clientCallLog.log(Log.BRIEF, "outbound call received exception: [" + tCPEndpoint
/* 320 */           .getHost() + ":" + tCPEndpoint
/* 321 */           .getPort() + "] exception: ", paramException);
/*     */     } 
/*     */     
/* 324 */     throw paramException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exception getServerException() {
/* 332 */     return this.serverException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void done() throws IOException {
/* 340 */     releaseInputStream();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/transport/StreamRemoteCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */