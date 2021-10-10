/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.DirectoryIteratorException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixDirectoryStream
/*     */   implements DirectoryStream<Path>
/*     */ {
/*     */   private final UnixPath dir;
/*     */   private final long dp;
/*     */   private final DirectoryStream.Filter<? super Path> filter;
/*  52 */   private final ReentrantReadWriteLock streamLock = new ReentrantReadWriteLock(true);
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean isClosed;
/*     */ 
/*     */ 
/*     */   
/*     */   private Iterator<Path> iterator;
/*     */ 
/*     */ 
/*     */   
/*     */   UnixDirectoryStream(UnixPath paramUnixPath, long paramLong, DirectoryStream.Filter<? super Path> paramFilter) {
/*  65 */     this.dir = paramUnixPath;
/*  66 */     this.dp = paramLong;
/*  67 */     this.filter = paramFilter;
/*     */   }
/*     */   
/*     */   protected final UnixPath directory() {
/*  71 */     return this.dir;
/*     */   }
/*     */   
/*     */   protected final Lock readLock() {
/*  75 */     return this.streamLock.readLock();
/*     */   }
/*     */   
/*     */   protected final Lock writeLock() {
/*  79 */     return this.streamLock.writeLock();
/*     */   }
/*     */   
/*     */   protected final boolean isOpen() {
/*  83 */     return !this.isClosed;
/*     */   }
/*     */   
/*     */   protected final boolean closeImpl() throws IOException {
/*  87 */     if (!this.isClosed) {
/*  88 */       this.isClosed = true;
/*     */       try {
/*  90 */         UnixNativeDispatcher.closedir(this.dp);
/*  91 */       } catch (UnixException unixException) {
/*  92 */         throw new IOException(unixException.errorString());
/*     */       } 
/*  94 */       return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 104 */     writeLock().lock();
/*     */     try {
/* 106 */       closeImpl();
/*     */     } finally {
/* 108 */       writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final Iterator<Path> iterator(DirectoryStream<Path> paramDirectoryStream) {
/* 113 */     if (this.isClosed) {
/* 114 */       throw new IllegalStateException("Directory stream is closed");
/*     */     }
/* 116 */     synchronized (this) {
/* 117 */       if (this.iterator != null)
/* 118 */         throw new IllegalStateException("Iterator already obtained"); 
/* 119 */       this.iterator = new UnixDirectoryIterator(paramDirectoryStream);
/* 120 */       return this.iterator;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Path> iterator() {
/* 126 */     return iterator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class UnixDirectoryIterator
/*     */     implements Iterator<Path>
/*     */   {
/*     */     private final DirectoryStream<Path> stream;
/*     */     
/*     */     private boolean atEof;
/*     */     
/*     */     private Path nextEntry;
/*     */ 
/*     */     
/*     */     UnixDirectoryIterator(DirectoryStream<Path> param1DirectoryStream) {
/* 142 */       this.atEof = false;
/* 143 */       this.stream = param1DirectoryStream;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isSelfOrParent(byte[] param1ArrayOfbyte) {
/* 148 */       if (param1ArrayOfbyte[0] == 46 && (
/* 149 */         param1ArrayOfbyte.length == 1 || (param1ArrayOfbyte.length == 2 && param1ArrayOfbyte[1] == 46)))
/*     */       {
/* 151 */         return true;
/*     */       }
/*     */       
/* 154 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     private Path readNextEntry() {
/* 159 */       assert Thread.holdsLock(this);
/*     */       
/*     */       while (true) {
/* 162 */         byte[] arrayOfByte = null;
/*     */ 
/*     */         
/* 165 */         UnixDirectoryStream.this.readLock().lock();
/*     */         try {
/* 167 */           if (UnixDirectoryStream.this.isOpen()) {
/* 168 */             arrayOfByte = UnixNativeDispatcher.readdir(UnixDirectoryStream.this.dp);
/*     */           }
/* 170 */         } catch (UnixException unixException) {
/* 171 */           IOException iOException = unixException.asIOException(UnixDirectoryStream.this.dir);
/* 172 */           throw new DirectoryIteratorException(iOException);
/*     */         } finally {
/* 174 */           UnixDirectoryStream.this.readLock().unlock();
/*     */         } 
/*     */ 
/*     */         
/* 178 */         if (arrayOfByte == null) {
/* 179 */           this.atEof = true;
/* 180 */           return null;
/*     */         } 
/*     */ 
/*     */         
/* 184 */         if (!isSelfOrParent(arrayOfByte)) {
/* 185 */           UnixPath unixPath = UnixDirectoryStream.this.dir.resolve(arrayOfByte);
/*     */ 
/*     */           
/*     */           try {
/* 189 */             if (UnixDirectoryStream.this.filter == null || UnixDirectoryStream.this.filter.accept(unixPath))
/* 190 */               return unixPath; 
/* 191 */           } catch (IOException iOException) {
/* 192 */             throw new DirectoryIteratorException(iOException);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized boolean hasNext() {
/* 200 */       if (this.nextEntry == null && !this.atEof)
/* 201 */         this.nextEntry = readNextEntry(); 
/* 202 */       return (this.nextEntry != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized Path next() {
/*     */       Path path;
/* 208 */       if (this.nextEntry == null && !this.atEof) {
/* 209 */         path = readNextEntry();
/*     */       } else {
/* 211 */         path = this.nextEntry;
/* 212 */         this.nextEntry = null;
/*     */       } 
/* 214 */       if (path == null)
/* 215 */         throw new NoSuchElementException(); 
/* 216 */       return path;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 221 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixDirectoryStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */