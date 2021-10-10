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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   private final FileDescriptor fd;
/*     */   private final boolean append;
/*     */   private FileChannel channel;
/*     */   private final String path;
/*  76 */   private final Object closeLock = new Object();
/*     */ 
/*     */ 
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
/*     */   public FileOutputStream(String paramString) throws FileNotFoundException {
/* 101 */     this((paramString != null) ? new File(paramString) : null, false);
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
/*     */   
/*     */   public FileOutputStream(String paramString, boolean paramBoolean) throws FileNotFoundException {
/* 133 */     this((paramString != null) ? new File(paramString) : null, paramBoolean);
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
/*     */   public FileOutputStream(File paramFile) throws FileNotFoundException {
/* 162 */     this(paramFile, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileOutputStream(File paramFile, boolean paramBoolean) throws FileNotFoundException {
/* 197 */     String str = (paramFile != null) ? paramFile.getPath() : null;
/* 198 */     SecurityManager securityManager = System.getSecurityManager();
/* 199 */     if (securityManager != null) {
/* 200 */       securityManager.checkWrite(str);
/*     */     }
/* 202 */     if (str == null) {
/* 203 */       throw new NullPointerException();
/*     */     }
/* 205 */     if (paramFile.isInvalid()) {
/* 206 */       throw new FileNotFoundException("Invalid file path");
/*     */     }
/* 208 */     this.fd = new FileDescriptor();
/* 209 */     this.fd.attach(this);
/* 210 */     this.append = paramBoolean;
/* 211 */     this.path = str;
/*     */     
/* 213 */     open(str, paramBoolean);
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
/*     */   public FileOutputStream(FileDescriptor paramFileDescriptor) {
/* 240 */     SecurityManager securityManager = System.getSecurityManager();
/* 241 */     if (paramFileDescriptor == null) {
/* 242 */       throw new NullPointerException();
/*     */     }
/* 244 */     if (securityManager != null) {
/* 245 */       securityManager.checkWrite(paramFileDescriptor);
/*     */     }
/* 247 */     this.fd = paramFileDescriptor;
/* 248 */     this.append = false;
/* 249 */     this.path = null;
/*     */     
/* 251 */     this.fd.attach(this);
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
/*     */   private void open(String paramString, boolean paramBoolean) throws FileNotFoundException {
/* 270 */     open0(paramString, paramBoolean);
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
/*     */   public void write(int paramInt) throws IOException {
/* 290 */     write(paramInt, this.append);
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
/*     */   public void write(byte[] paramArrayOfbyte) throws IOException {
/* 313 */     writeBytes(paramArrayOfbyte, 0, paramArrayOfbyte.length, this.append);
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 326 */     writeBytes(paramArrayOfbyte, paramInt1, paramInt2, this.append);
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
/*     */   public void close() throws IOException {
/* 343 */     synchronized (this.closeLock) {
/* 344 */       if (this.closed) {
/*     */         return;
/*     */       }
/* 347 */       this.closed = true;
/*     */     } 
/*     */     
/* 350 */     if (this.channel != null) {
/* 351 */       this.channel.close();
/*     */     }
/*     */     
/* 354 */     this.fd.closeAll(new Closeable() {
/*     */           public void close() throws IOException {
/* 356 */             FileOutputStream.this.close0();
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
/* 372 */     if (this.fd != null) {
/* 373 */       return this.fd;
/*     */     }
/* 375 */     throw new IOException();
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
/*     */   public FileChannel getChannel() {
/* 396 */     synchronized (this) {
/* 397 */       if (this.channel == null) {
/* 398 */         this.channel = FileChannelImpl.open(this.fd, this.path, false, true, this.append, this);
/*     */       }
/* 400 */       return this.channel;
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
/*     */   protected void finalize() throws IOException {
/* 413 */     if (this.fd != null) {
/* 414 */       if (this.fd == FileDescriptor.out || this.fd == FileDescriptor.err) {
/* 415 */         flush();
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 422 */         close();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 432 */     initIDs();
/*     */   }
/*     */   
/*     */   private native void open0(String paramString, boolean paramBoolean) throws FileNotFoundException;
/*     */   
/*     */   private native void write(int paramInt, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private native void writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException;
/*     */   
/*     */   private native void close0() throws IOException;
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/FileOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */