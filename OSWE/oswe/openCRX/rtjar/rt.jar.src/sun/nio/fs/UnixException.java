/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.AccessDeniedException;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.FileSystemException;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixException
/*     */   extends Exception
/*     */ {
/*     */   static final long serialVersionUID = 7227016794320723218L;
/*     */   private int errno;
/*     */   private String msg;
/*     */   
/*     */   UnixException(int paramInt) {
/*  42 */     this.errno = paramInt;
/*  43 */     this.msg = null;
/*     */   }
/*     */   
/*     */   UnixException(String paramString) {
/*  47 */     this.errno = 0;
/*  48 */     this.msg = paramString;
/*     */   }
/*     */   
/*     */   int errno() {
/*  52 */     return this.errno;
/*     */   }
/*     */   
/*     */   void setError(int paramInt) {
/*  56 */     this.errno = paramInt;
/*  57 */     this.msg = null;
/*     */   }
/*     */   
/*     */   String errorString() {
/*  61 */     if (this.msg != null) {
/*  62 */       return this.msg;
/*     */     }
/*  64 */     return Util.toString(UnixNativeDispatcher.strerror(errno()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  70 */     return errorString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IOException translateToIOException(String paramString1, String paramString2) {
/*  79 */     if (this.msg != null) {
/*  80 */       return new IOException(this.msg);
/*     */     }
/*     */     
/*  83 */     if (errno() == 13)
/*  84 */       return new AccessDeniedException(paramString1, paramString2, null); 
/*  85 */     if (errno() == 2)
/*  86 */       return new NoSuchFileException(paramString1, paramString2, null); 
/*  87 */     if (errno() == 17) {
/*  88 */       return new FileAlreadyExistsException(paramString1, paramString2, null);
/*     */     }
/*     */     
/*  91 */     return new FileSystemException(paramString1, paramString2, errorString());
/*     */   }
/*     */   
/*     */   void rethrowAsIOException(String paramString) throws IOException {
/*  95 */     IOException iOException = translateToIOException(paramString, null);
/*  96 */     throw iOException;
/*     */   }
/*     */   
/*     */   void rethrowAsIOException(UnixPath paramUnixPath1, UnixPath paramUnixPath2) throws IOException {
/* 100 */     String str1 = (paramUnixPath1 == null) ? null : paramUnixPath1.getPathForExceptionMessage();
/* 101 */     String str2 = (paramUnixPath2 == null) ? null : paramUnixPath2.getPathForExceptionMessage();
/* 102 */     IOException iOException = translateToIOException(str1, str2);
/* 103 */     throw iOException;
/*     */   }
/*     */   
/*     */   void rethrowAsIOException(UnixPath paramUnixPath) throws IOException {
/* 107 */     rethrowAsIOException(paramUnixPath, null);
/*     */   }
/*     */   
/*     */   IOException asIOException(UnixPath paramUnixPath) {
/* 111 */     return translateToIOException(paramUnixPath.getPathForExceptionMessage(), null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */