/*     */ package java.io;
/*     */ 
/*     */ import java.nio.channels.FileChannel;
/*     */ import sun.nio.ch.FileChannelImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private final FileDescriptor fd;
/*     */   private final String path;
/*  60 */   private FileChannel channel = null;
/*     */   
/*  62 */   private final Object closeLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean closed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInputStream(String paramString) throws FileNotFoundException {
/*  93 */     this((paramString != null) ? new File(paramString) : null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInputStream(File paramFile) throws FileNotFoundException {
/* 124 */     String str = (paramFile != null) ? paramFile.getPath() : null;
/* 125 */     SecurityManager securityManager = System.getSecurityManager();
/* 126 */     if (securityManager != null) {
/* 127 */       securityManager.checkRead(str);
/*     */     }
/* 129 */     if (str == null) {
/* 130 */       throw new NullPointerException();
/*     */     }
/* 132 */     if (paramFile.isInvalid()) {
/* 133 */       throw new FileNotFoundException("Invalid file path");
/*     */     }
/* 135 */     this.fd = new FileDescriptor();
/* 136 */     this.fd.attach(this);
/* 137 */     this.path = str;
/* 138 */     open(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInputStream(FileDescriptor paramFileDescriptor) {
/* 166 */     SecurityManager securityManager = System.getSecurityManager();
/* 167 */     if (paramFileDescriptor == null) {
/* 168 */       throw new NullPointerException();
/*     */     }
/* 170 */     if (securityManager != null) {
/* 171 */       securityManager.checkRead(paramFileDescriptor);
/*     */     }
/* 173 */     this.fd = paramFileDescriptor;
/* 174 */     this.path = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     this.fd.attach(this);
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
/*     */   private void open(String paramString) throws FileNotFoundException {
/* 195 */     open0(paramString);
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
/*     */   public int read() throws IOException {
/* 207 */     return read0();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte) throws IOException {
/* 233 */     return readBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 255 */     return readBytes(paramArrayOfbyte, paramInt1, paramInt2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long paramLong) throws IOException {
/* 283 */     return skip0(paramLong);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() throws IOException {
/* 306 */     return available0();
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
/*     */   public void close() throws IOException {
/* 324 */     synchronized (this.closeLock) {
/* 325 */       if (this.closed) {
/*     */         return;
/*     */       }
/* 328 */       this.closed = true;
/*     */     } 
/* 330 */     if (this.channel != null) {
/* 331 */       this.channel.close();
/*     */     }
/*     */     
/* 334 */     this.fd.closeAll(new Closeable() {
/*     */           public void close() throws IOException {
/* 336 */             FileInputStream.this.close0();
/*     */           }
/*     */         });
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
/*     */   public final FileDescriptor getFD() throws IOException {
/* 352 */     if (this.fd != null) {
/* 353 */       return this.fd;
/*     */     }
/* 355 */     throw new IOException();
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
/*     */   
/*     */   public FileChannel getChannel() {
/* 375 */     synchronized (this) {
/* 376 */       if (this.channel == null) {
/* 377 */         this.channel = FileChannelImpl.open(this.fd, this.path, true, false, this);
/*     */       }
/* 379 */       return this.channel;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 388 */     initIDs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws IOException {
/* 399 */     if (this.fd != null && this.fd != FileDescriptor.in)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 405 */       close();
/*     */     }
/*     */   }
/*     */   
/*     */   private native void open0(String paramString) throws FileNotFoundException;
/*     */   
/*     */   private native int read0() throws IOException;
/*     */   
/*     */   private native int readBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   private native long skip0(long paramLong) throws IOException;
/*     */   
/*     */   private native int available0() throws IOException;
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   private native void close0() throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/FileInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */