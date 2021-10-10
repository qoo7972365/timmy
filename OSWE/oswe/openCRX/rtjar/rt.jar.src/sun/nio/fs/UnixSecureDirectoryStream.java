/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.AtomicMoveNotSupportedException;
/*     */ import java.nio.file.ClosedDirectoryStreamException;
/*     */ import java.nio.file.DirectoryNotEmptyException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.NotDirectoryException;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.SecureDirectoryStream;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.FileAttributeView;
/*     */ import java.nio.file.attribute.FileOwnerAttributeView;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.nio.file.attribute.GroupPrincipal;
/*     */ import java.nio.file.attribute.PosixFileAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributes;
/*     */ import java.nio.file.attribute.PosixFilePermission;
/*     */ import java.nio.file.attribute.UserPrincipal;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixSecureDirectoryStream
/*     */   implements SecureDirectoryStream<Path>
/*     */ {
/*     */   private final UnixDirectoryStream ds;
/*     */   private final int dfd;
/*     */   
/*     */   UnixSecureDirectoryStream(UnixPath paramUnixPath, long paramLong, int paramInt, DirectoryStream.Filter<? super Path> paramFilter) {
/*  53 */     this.ds = new UnixDirectoryStream(paramUnixPath, paramLong, paramFilter);
/*  54 */     this.dfd = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*  61 */     this.ds.writeLock().lock();
/*     */     try {
/*  63 */       if (this.ds.closeImpl()) {
/*  64 */         UnixNativeDispatcher.close(this.dfd);
/*     */       }
/*     */     } finally {
/*  67 */       this.ds.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Path> iterator() {
/*  73 */     return this.ds.iterator(this);
/*     */   }
/*     */   
/*     */   private UnixPath getName(Path paramPath) {
/*  77 */     if (paramPath == null)
/*  78 */       throw new NullPointerException(); 
/*  79 */     if (!(paramPath instanceof UnixPath))
/*  80 */       throw new ProviderMismatchException(); 
/*  81 */     return (UnixPath)paramPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecureDirectoryStream<Path> newDirectoryStream(Path paramPath, LinkOption... paramVarArgs) throws IOException {
/*  92 */     UnixPath unixPath1 = getName(paramPath);
/*  93 */     UnixPath unixPath2 = this.ds.directory().resolve(unixPath1);
/*  94 */     boolean bool = Util.followLinks(paramVarArgs);
/*     */ 
/*     */     
/*  97 */     SecurityManager securityManager = System.getSecurityManager();
/*  98 */     if (securityManager != null) {
/*  99 */       unixPath2.checkRead();
/*     */     }
/*     */     
/* 102 */     this.ds.readLock().lock();
/*     */     try {
/* 104 */       if (!this.ds.isOpen()) {
/* 105 */         throw new ClosedDirectoryStreamException();
/*     */       }
/*     */       
/* 108 */       int i = -1;
/* 109 */       int j = -1;
/* 110 */       long l = 0L;
/*     */       try {
/* 112 */         int k = 0;
/* 113 */         if (!bool)
/* 114 */           k |= 0x20000; 
/* 115 */         i = UnixNativeDispatcher.openat(this.dfd, unixPath1.asByteArray(), k, 0);
/* 116 */         j = UnixNativeDispatcher.dup(i);
/* 117 */         l = UnixNativeDispatcher.fdopendir(i);
/* 118 */       } catch (UnixException unixException) {
/* 119 */         if (i != -1)
/* 120 */           UnixNativeDispatcher.close(i); 
/* 121 */         if (j != -1)
/* 122 */           UnixNativeDispatcher.close(j); 
/* 123 */         if (unixException.errno() == 20)
/* 124 */           throw new NotDirectoryException(unixPath1.toString()); 
/* 125 */         unixException.rethrowAsIOException(unixPath1);
/*     */       } 
/* 127 */       return new UnixSecureDirectoryStream(unixPath2, l, j, null);
/*     */     } finally {
/* 129 */       this.ds.readLock().unlock();
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
/*     */   public SeekableByteChannel newByteChannel(Path paramPath, Set<? extends OpenOption> paramSet, FileAttribute<?>... paramVarArgs) throws IOException {
/* 142 */     UnixPath unixPath = getName(paramPath);
/*     */ 
/*     */     
/* 145 */     int i = UnixFileModeAttribute.toUnixMode(438, paramVarArgs);
/*     */ 
/*     */     
/* 148 */     String str = this.ds.directory().resolve(unixPath).getPathForPermissionCheck();
/*     */     
/* 150 */     this.ds.readLock().lock();
/*     */     try {
/* 152 */       if (!this.ds.isOpen()) {
/* 153 */         throw new ClosedDirectoryStreamException();
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 161 */       this.ds.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void implDelete(Path paramPath, boolean paramBoolean, int paramInt) throws IOException {
/* 172 */     UnixPath unixPath = getName(paramPath);
/*     */ 
/*     */     
/* 175 */     SecurityManager securityManager = System.getSecurityManager();
/* 176 */     if (securityManager != null) {
/* 177 */       this.ds.directory().resolve(unixPath).checkDelete();
/*     */     }
/*     */     
/* 180 */     this.ds.readLock().lock();
/*     */     try {
/* 182 */       if (!this.ds.isOpen()) {
/* 183 */         throw new ClosedDirectoryStreamException();
/*     */       }
/* 185 */       if (!paramBoolean) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 190 */         UnixFileAttributes unixFileAttributes = null;
/*     */         try {
/* 192 */           unixFileAttributes = UnixFileAttributes.get(this.dfd, unixPath, false);
/* 193 */         } catch (UnixException unixException) {
/* 194 */           unixException.rethrowAsIOException(unixPath);
/*     */         } 
/* 196 */         paramInt = unixFileAttributes.isDirectory() ? 512 : 0;
/*     */       } 
/*     */       
/*     */       try {
/* 200 */         UnixNativeDispatcher.unlinkat(this.dfd, unixPath.asByteArray(), paramInt);
/* 201 */       } catch (UnixException unixException) {
/* 202 */         if ((paramInt & 0x200) != 0 && (
/* 203 */           unixException.errno() == 17 || unixException.errno() == 39)) {
/* 204 */           throw new DirectoryNotEmptyException(null);
/*     */         }
/*     */         
/* 207 */         unixException.rethrowAsIOException(unixPath);
/*     */       } 
/*     */     } finally {
/* 210 */       this.ds.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteFile(Path paramPath) throws IOException {
/* 216 */     implDelete(paramPath, true, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteDirectory(Path paramPath) throws IOException {
/* 221 */     implDelete(paramPath, true, 512);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(Path paramPath1, SecureDirectoryStream<Path> paramSecureDirectoryStream, Path paramPath2) throws IOException {
/* 231 */     UnixPath unixPath1 = getName(paramPath1);
/* 232 */     UnixPath unixPath2 = getName(paramPath2);
/* 233 */     if (paramSecureDirectoryStream == null)
/* 234 */       throw new NullPointerException(); 
/* 235 */     if (!(paramSecureDirectoryStream instanceof UnixSecureDirectoryStream))
/* 236 */       throw new ProviderMismatchException(); 
/* 237 */     UnixSecureDirectoryStream unixSecureDirectoryStream = (UnixSecureDirectoryStream)paramSecureDirectoryStream;
/*     */ 
/*     */     
/* 240 */     SecurityManager securityManager = System.getSecurityManager();
/* 241 */     if (securityManager != null) {
/* 242 */       this.ds.directory().resolve(unixPath1).checkWrite();
/* 243 */       unixSecureDirectoryStream.ds.directory().resolve(unixPath2).checkWrite();
/*     */     } 
/*     */ 
/*     */     
/* 247 */     this.ds.readLock().lock();
/*     */     try {
/* 249 */       unixSecureDirectoryStream.ds.readLock().lock();
/*     */       try {
/* 251 */         if (!this.ds.isOpen() || !unixSecureDirectoryStream.ds.isOpen())
/* 252 */           throw new ClosedDirectoryStreamException(); 
/*     */         try {
/* 254 */           UnixNativeDispatcher.renameat(this.dfd, unixPath1.asByteArray(), unixSecureDirectoryStream.dfd, unixPath2.asByteArray());
/* 255 */         } catch (UnixException unixException) {
/* 256 */           if (unixException.errno() == 18) {
/* 257 */             throw new AtomicMoveNotSupportedException(unixPath1
/* 258 */                 .toString(), unixPath2.toString(), unixException.errorString());
/*     */           }
/* 260 */           unixException.rethrowAsIOException(unixPath1, unixPath2);
/*     */         } 
/*     */       } finally {
/* 263 */         unixSecureDirectoryStream.ds.readLock().unlock();
/*     */       } 
/*     */     } finally {
/* 266 */       this.ds.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <V extends FileAttributeView> V getFileAttributeViewImpl(UnixPath paramUnixPath, Class<V> paramClass, boolean paramBoolean) {
/* 275 */     if (paramClass == null)
/* 276 */       throw new NullPointerException(); 
/* 277 */     Class<V> clazz = paramClass;
/* 278 */     if (clazz == BasicFileAttributeView.class) {
/* 279 */       return (V)new BasicFileAttributeViewImpl(paramUnixPath, paramBoolean);
/*     */     }
/* 281 */     if (clazz == PosixFileAttributeView.class || clazz == FileOwnerAttributeView.class) {
/* 282 */       return (V)new PosixFileAttributeViewImpl(paramUnixPath, paramBoolean);
/*     */     }
/*     */     
/* 285 */     return (V)null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V extends FileAttributeView> V getFileAttributeView(Class<V> paramClass) {
/* 293 */     return getFileAttributeViewImpl(null, paramClass, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V extends FileAttributeView> V getFileAttributeView(Path paramPath, Class<V> paramClass, LinkOption... paramVarArgs) {
/* 304 */     UnixPath unixPath = getName(paramPath);
/* 305 */     boolean bool = Util.followLinks(paramVarArgs);
/* 306 */     return getFileAttributeViewImpl(unixPath, paramClass, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class BasicFileAttributeViewImpl
/*     */     implements BasicFileAttributeView
/*     */   {
/*     */     final UnixPath file;
/*     */     
/*     */     final boolean followLinks;
/*     */ 
/*     */     
/*     */     BasicFileAttributeViewImpl(UnixPath param1UnixPath, boolean param1Boolean) {
/* 320 */       this.file = param1UnixPath;
/* 321 */       this.followLinks = param1Boolean;
/*     */     }
/*     */     
/*     */     int open() throws IOException {
/* 325 */       int i = 0;
/* 326 */       if (!this.followLinks)
/* 327 */         i |= 0x20000; 
/*     */       try {
/* 329 */         return UnixNativeDispatcher.openat(UnixSecureDirectoryStream.this.dfd, this.file.asByteArray(), i, 0);
/* 330 */       } catch (UnixException unixException) {
/* 331 */         unixException.rethrowAsIOException(this.file);
/* 332 */         return -1;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void checkWriteAccess() {
/* 337 */       SecurityManager securityManager = System.getSecurityManager();
/* 338 */       if (securityManager != null) {
/* 339 */         if (this.file == null) {
/* 340 */           UnixSecureDirectoryStream.this.ds.directory().checkWrite();
/*     */         } else {
/* 342 */           UnixSecureDirectoryStream.this.ds.directory().resolve(this.file).checkWrite();
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String name() {
/* 349 */       return "basic";
/*     */     }
/*     */ 
/*     */     
/*     */     public BasicFileAttributes readAttributes() throws IOException {
/* 354 */       UnixSecureDirectoryStream.this.ds.readLock().lock();
/*     */       try {
/* 356 */         if (!UnixSecureDirectoryStream.this.ds.isOpen()) {
/* 357 */           throw new ClosedDirectoryStreamException();
/*     */         }
/* 359 */         SecurityManager securityManager = System.getSecurityManager();
/* 360 */         if (securityManager != null) {
/* 361 */           if (this.file == null) {
/* 362 */             UnixSecureDirectoryStream.this.ds.directory().checkRead();
/*     */           } else {
/* 364 */             UnixSecureDirectoryStream.this.ds.directory().resolve(this.file).checkRead();
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */ 
/*     */         
/* 379 */         UnixSecureDirectoryStream.this.ds.readLock().unlock();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTimes(FileTime param1FileTime1, FileTime param1FileTime2, FileTime param1FileTime3) throws IOException {
/* 389 */       checkWriteAccess();
/*     */       
/* 391 */       UnixSecureDirectoryStream.this.ds.readLock().lock();
/*     */       try {
/* 393 */         if (!UnixSecureDirectoryStream.this.ds.isOpen()) {
/* 394 */           throw new ClosedDirectoryStreamException();
/*     */         }
/* 396 */         int i = (this.file == null) ? UnixSecureDirectoryStream.this.dfd : open();
/*     */         
/*     */         try {
/* 399 */           if (param1FileTime1 == null || param1FileTime2 == null) {
/*     */             try {
/* 401 */               UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(i);
/* 402 */               if (param1FileTime1 == null)
/* 403 */                 param1FileTime1 = unixFileAttributes.lastModifiedTime(); 
/* 404 */               if (param1FileTime2 == null)
/* 405 */                 param1FileTime2 = unixFileAttributes.lastAccessTime(); 
/* 406 */             } catch (UnixException unixException) {
/* 407 */               unixException.rethrowAsIOException(this.file);
/*     */             } 
/*     */           }
/*     */           
/*     */           try {
/* 412 */             UnixNativeDispatcher.futimes(i, param1FileTime2
/* 413 */                 .to(TimeUnit.MICROSECONDS), param1FileTime1
/* 414 */                 .to(TimeUnit.MICROSECONDS));
/* 415 */           } catch (UnixException unixException) {
/* 416 */             unixException.rethrowAsIOException(this.file);
/*     */           } 
/*     */         } finally {
/* 419 */           if (this.file != null)
/* 420 */             UnixNativeDispatcher.close(i); 
/*     */         } 
/*     */       } finally {
/* 423 */         UnixSecureDirectoryStream.this.ds.readLock().unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class PosixFileAttributeViewImpl
/*     */     extends BasicFileAttributeViewImpl
/*     */     implements PosixFileAttributeView
/*     */   {
/*     */     PosixFileAttributeViewImpl(UnixPath param1UnixPath, boolean param1Boolean) {
/* 435 */       super(param1UnixPath, param1Boolean);
/*     */     }
/*     */     
/*     */     private void checkWriteAndUserAccess() {
/* 439 */       SecurityManager securityManager = System.getSecurityManager();
/* 440 */       if (securityManager != null) {
/* 441 */         checkWriteAccess();
/* 442 */         securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String name() {
/* 448 */       return "posix";
/*     */     }
/*     */ 
/*     */     
/*     */     public PosixFileAttributes readAttributes() throws IOException {
/* 453 */       SecurityManager securityManager = System.getSecurityManager();
/* 454 */       if (securityManager != null) {
/* 455 */         if (this.file == null) {
/* 456 */           UnixSecureDirectoryStream.this.ds.directory().checkRead();
/*     */         } else {
/* 458 */           UnixSecureDirectoryStream.this.ds.directory().resolve(this.file).checkRead();
/* 459 */         }  securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
/*     */       } 
/*     */       
/* 462 */       UnixSecureDirectoryStream.this.ds.readLock().lock();
/*     */       try {
/* 464 */         if (!UnixSecureDirectoryStream.this.ds.isOpen()) {
/* 465 */           throw new ClosedDirectoryStreamException();
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */ 
/*     */         
/* 477 */         UnixSecureDirectoryStream.this.ds.readLock().unlock();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPermissions(Set<PosixFilePermission> param1Set) throws IOException {
/* 486 */       checkWriteAndUserAccess();
/*     */       
/* 488 */       UnixSecureDirectoryStream.this.ds.readLock().lock();
/*     */       try {
/* 490 */         if (!UnixSecureDirectoryStream.this.ds.isOpen()) {
/* 491 */           throw new ClosedDirectoryStreamException();
/*     */         }
/* 493 */         int i = (this.file == null) ? UnixSecureDirectoryStream.this.dfd : open();
/*     */         try {
/* 495 */           UnixNativeDispatcher.fchmod(i, UnixFileModeAttribute.toUnixMode(param1Set));
/* 496 */         } catch (UnixException unixException) {
/* 497 */           unixException.rethrowAsIOException(this.file);
/*     */         } finally {
/* 499 */           if (this.file != null && i >= 0)
/* 500 */             UnixNativeDispatcher.close(i); 
/*     */         } 
/*     */       } finally {
/* 503 */         UnixSecureDirectoryStream.this.ds.readLock().unlock();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void setOwners(int param1Int1, int param1Int2) throws IOException {
/* 509 */       checkWriteAndUserAccess();
/*     */       
/* 511 */       UnixSecureDirectoryStream.this.ds.readLock().lock();
/*     */       try {
/* 513 */         if (!UnixSecureDirectoryStream.this.ds.isOpen()) {
/* 514 */           throw new ClosedDirectoryStreamException();
/*     */         }
/* 516 */         int i = (this.file == null) ? UnixSecureDirectoryStream.this.dfd : open();
/*     */         try {
/* 518 */           UnixNativeDispatcher.fchown(i, param1Int1, param1Int2);
/* 519 */         } catch (UnixException unixException) {
/* 520 */           unixException.rethrowAsIOException(this.file);
/*     */         } finally {
/* 522 */           if (this.file != null && i >= 0)
/* 523 */             UnixNativeDispatcher.close(i); 
/*     */         } 
/*     */       } finally {
/* 526 */         UnixSecureDirectoryStream.this.ds.readLock().unlock();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public UserPrincipal getOwner() throws IOException {
/* 532 */       return readAttributes().owner();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOwner(UserPrincipal param1UserPrincipal) throws IOException {
/* 539 */       if (!(param1UserPrincipal instanceof UnixUserPrincipals.User))
/* 540 */         throw new ProviderMismatchException(); 
/* 541 */       if (param1UserPrincipal instanceof UnixUserPrincipals.Group)
/* 542 */         throw new IOException("'owner' parameter can't be a group"); 
/* 543 */       int i = ((UnixUserPrincipals.User)param1UserPrincipal).uid();
/* 544 */       setOwners(i, -1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setGroup(GroupPrincipal param1GroupPrincipal) throws IOException {
/* 551 */       if (!(param1GroupPrincipal instanceof UnixUserPrincipals.Group))
/* 552 */         throw new ProviderMismatchException(); 
/* 553 */       int i = ((UnixUserPrincipals.Group)param1GroupPrincipal).gid();
/* 554 */       setOwners(-1, i);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixSecureDirectoryStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */