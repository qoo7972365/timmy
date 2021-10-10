/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.FilePermission;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.AccessMode;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.DirectoryNotEmptyException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystemAlreadyExistsException;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.LinkPermission;
/*     */ import java.nio.file.NotDirectoryException;
/*     */ import java.nio.file.NotLinkException;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.attribute.FileOwnerAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributeView;
/*     */ import java.nio.file.attribute.PosixFileAttributes;
/*     */ import java.nio.file.spi.FileTypeDetector;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import sun.nio.ch.ThreadPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class UnixFileSystemProvider
/*     */   extends AbstractFileSystemProvider
/*     */ {
/*     */   private static final String USER_DIR = "user.dir";
/*     */   private final UnixFileSystem theFileSystem;
/*     */   
/*     */   public UnixFileSystemProvider() {
/*  55 */     String str = System.getProperty("user.dir");
/*  56 */     this.theFileSystem = newFileSystem(str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   abstract UnixFileSystem newFileSystem(String paramString);
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getScheme() {
/*  66 */     return "file";
/*     */   }
/*     */   
/*     */   private void checkUri(URI paramURI) {
/*  70 */     if (!paramURI.getScheme().equalsIgnoreCase(getScheme()))
/*  71 */       throw new IllegalArgumentException("URI does not match this provider"); 
/*  72 */     if (paramURI.getAuthority() != null)
/*  73 */       throw new IllegalArgumentException("Authority component present"); 
/*  74 */     if (paramURI.getPath() == null)
/*  75 */       throw new IllegalArgumentException("Path component is undefined"); 
/*  76 */     if (!paramURI.getPath().equals("/"))
/*  77 */       throw new IllegalArgumentException("Path component should be '/'"); 
/*  78 */     if (paramURI.getQuery() != null)
/*  79 */       throw new IllegalArgumentException("Query component present"); 
/*  80 */     if (paramURI.getFragment() != null) {
/*  81 */       throw new IllegalArgumentException("Fragment component present");
/*     */     }
/*     */   }
/*     */   
/*     */   public final FileSystem newFileSystem(URI paramURI, Map<String, ?> paramMap) {
/*  86 */     checkUri(paramURI);
/*  87 */     throw new FileSystemAlreadyExistsException();
/*     */   }
/*     */ 
/*     */   
/*     */   public final FileSystem getFileSystem(URI paramURI) {
/*  92 */     checkUri(paramURI);
/*  93 */     return this.theFileSystem;
/*     */   }
/*     */ 
/*     */   
/*     */   public Path getPath(URI paramURI) {
/*  98 */     return UnixUriUtils.fromUri(this.theFileSystem, paramURI);
/*     */   }
/*     */   
/*     */   UnixPath checkPath(Path paramPath) {
/* 102 */     if (paramPath == null)
/* 103 */       throw new NullPointerException(); 
/* 104 */     if (!(paramPath instanceof UnixPath))
/* 105 */       throw new ProviderMismatchException(); 
/* 106 */     return (UnixPath)paramPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <V extends java.nio.file.attribute.FileAttributeView> V getFileAttributeView(Path paramPath, Class<V> paramClass, LinkOption... paramVarArgs) {
/* 115 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 116 */     boolean bool = Util.followLinks(paramVarArgs);
/* 117 */     if (paramClass == BasicFileAttributeView.class)
/* 118 */       return (V)UnixFileAttributeViews.createBasicView(unixPath, bool); 
/* 119 */     if (paramClass == PosixFileAttributeView.class)
/* 120 */       return (V)UnixFileAttributeViews.createPosixView(unixPath, bool); 
/* 121 */     if (paramClass == FileOwnerAttributeView.class)
/* 122 */       return (V)UnixFileAttributeViews.createOwnerView(unixPath, bool); 
/* 123 */     if (paramClass == null)
/* 124 */       throw new NullPointerException(); 
/* 125 */     return (V)null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends BasicFileAttributes> A readAttributes(Path paramPath, Class<A> paramClass, LinkOption... paramVarArgs) throws IOException {
/*     */     Class<PosixFileAttributeView> clazz;
/* 136 */     if (paramClass == BasicFileAttributes.class)
/* 137 */     { Class<BasicFileAttributeView> clazz1 = BasicFileAttributeView.class; }
/* 138 */     else if (paramClass == PosixFileAttributes.class)
/* 139 */     { clazz = PosixFileAttributeView.class; }
/* 140 */     else { if (paramClass == null) {
/* 141 */         throw new NullPointerException();
/*     */       }
/* 143 */       throw new UnsupportedOperationException(); }
/* 144 */      return (A)((BasicFileAttributeView)getFileAttributeView(paramPath, (Class)clazz, paramVarArgs)).readAttributes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DynamicFileAttributeView getFileAttributeView(Path paramPath, String paramString, LinkOption... paramVarArgs) {
/* 152 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 153 */     boolean bool = Util.followLinks(paramVarArgs);
/* 154 */     if (paramString.equals("basic"))
/* 155 */       return UnixFileAttributeViews.createBasicView(unixPath, bool); 
/* 156 */     if (paramString.equals("posix"))
/* 157 */       return UnixFileAttributeViews.createPosixView(unixPath, bool); 
/* 158 */     if (paramString.equals("unix"))
/* 159 */       return UnixFileAttributeViews.createUnixView(unixPath, bool); 
/* 160 */     if (paramString.equals("owner"))
/* 161 */       return UnixFileAttributeViews.createOwnerView(unixPath, bool); 
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileChannel newFileChannel(Path paramPath, Set<? extends OpenOption> paramSet, FileAttribute<?>... paramVarArgs) throws IOException {
/* 171 */     UnixPath unixPath = checkPath(paramPath);
/*     */     
/* 173 */     int i = UnixFileModeAttribute.toUnixMode(438, paramVarArgs);
/*     */     try {
/* 175 */       return UnixChannelFactory.newFileChannel(unixPath, paramSet, i);
/* 176 */     } catch (UnixException unixException) {
/* 177 */       unixException.rethrowAsIOException(unixPath);
/* 178 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsynchronousFileChannel newAsynchronousFileChannel(Path paramPath, Set<? extends OpenOption> paramSet, ExecutorService paramExecutorService, FileAttribute<?>... paramVarArgs) throws IOException {
/* 188 */     UnixPath unixPath = checkPath(paramPath);
/*     */     
/* 190 */     int i = UnixFileModeAttribute.toUnixMode(438, paramVarArgs);
/* 191 */     ThreadPool threadPool = (paramExecutorService == null) ? null : ThreadPool.wrap(paramExecutorService, 0);
/*     */     try {
/* 193 */       return 
/* 194 */         UnixChannelFactory.newAsynchronousFileChannel(unixPath, paramSet, i, threadPool);
/* 195 */     } catch (UnixException unixException) {
/* 196 */       unixException.rethrowAsIOException(unixPath);
/* 197 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SeekableByteChannel newByteChannel(Path paramPath, Set<? extends OpenOption> paramSet, FileAttribute<?>... paramVarArgs) throws IOException {
/* 208 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/*     */     
/* 210 */     int i = UnixFileModeAttribute.toUnixMode(438, paramVarArgs);
/*     */     try {
/* 212 */       return UnixChannelFactory.newFileChannel(unixPath, paramSet, i);
/* 213 */     } catch (UnixException unixException) {
/* 214 */       unixException.rethrowAsIOException(unixPath);
/* 215 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean implDelete(Path paramPath, boolean paramBoolean) throws IOException {
/* 221 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 222 */     unixPath.checkDelete();
/*     */ 
/*     */     
/* 225 */     UnixFileAttributes unixFileAttributes = null;
/*     */     try {
/* 227 */       unixFileAttributes = UnixFileAttributes.get(unixPath, false);
/* 228 */       if (unixFileAttributes.isDirectory()) {
/* 229 */         UnixNativeDispatcher.rmdir(unixPath);
/*     */       } else {
/* 231 */         UnixNativeDispatcher.unlink(unixPath);
/*     */       } 
/* 233 */       return true;
/* 234 */     } catch (UnixException unixException) {
/*     */       
/* 236 */       if (!paramBoolean && unixException.errno() == 2) {
/* 237 */         return false;
/*     */       }
/*     */       
/* 240 */       if (unixFileAttributes != null && unixFileAttributes.isDirectory() && (unixException
/* 241 */         .errno() == 17 || unixException.errno() == 39)) {
/* 242 */         throw new DirectoryNotEmptyException(unixPath.getPathForExceptionMessage());
/*     */       }
/* 244 */       unixException.rethrowAsIOException(unixPath);
/* 245 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copy(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 253 */     UnixCopyFile.copy(UnixPath.toUnixPath(paramPath1), 
/* 254 */         UnixPath.toUnixPath(paramPath2), paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 262 */     UnixCopyFile.move(UnixPath.toUnixPath(paramPath1), 
/* 263 */         UnixPath.toUnixPath(paramPath2), paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkAccess(Path paramPath, AccessMode... paramVarArgs) throws IOException {
/* 269 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 270 */     boolean bool1 = false;
/* 271 */     boolean bool2 = false;
/* 272 */     boolean bool3 = false;
/* 273 */     boolean bool4 = false;
/*     */     
/* 275 */     if (paramVarArgs.length == 0) {
/* 276 */       bool1 = true;
/*     */     } else {
/* 278 */       for (AccessMode accessMode : paramVarArgs) {
/* 279 */         switch (accessMode) { case READ:
/* 280 */             bool2 = true; break;
/* 281 */           case WRITE: bool3 = true; break;
/* 282 */           case EXECUTE: bool4 = true; break;
/* 283 */           default: throw new AssertionError("Should not get here"); }
/*     */ 
/*     */       
/*     */       } 
/*     */     } 
/* 288 */     int i = 0;
/* 289 */     if (bool1 || bool2) {
/* 290 */       unixPath.checkRead();
/* 291 */       i |= bool2 ? 4 : 0;
/*     */     } 
/* 293 */     if (bool3) {
/* 294 */       unixPath.checkWrite();
/* 295 */       i |= 0x2;
/*     */     } 
/* 297 */     if (bool4) {
/* 298 */       SecurityManager securityManager = System.getSecurityManager();
/* 299 */       if (securityManager != null)
/*     */       {
/* 301 */         securityManager.checkExec(unixPath.getPathForPermissionCheck());
/*     */       }
/* 303 */       i |= 0x1;
/*     */     } 
/*     */     try {
/* 306 */       UnixNativeDispatcher.access(unixPath, i);
/* 307 */     } catch (UnixException unixException) {
/* 308 */       unixException.rethrowAsIOException(unixPath);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSameFile(Path paramPath1, Path paramPath2) throws IOException {
/*     */     UnixFileAttributes unixFileAttributes1, unixFileAttributes2;
/* 314 */     UnixPath unixPath1 = UnixPath.toUnixPath(paramPath1);
/* 315 */     if (unixPath1.equals(paramPath2))
/* 316 */       return true; 
/* 317 */     if (paramPath2 == null)
/* 318 */       throw new NullPointerException(); 
/* 319 */     if (!(paramPath2 instanceof UnixPath))
/* 320 */       return false; 
/* 321 */     UnixPath unixPath2 = (UnixPath)paramPath2;
/*     */ 
/*     */     
/* 324 */     unixPath1.checkRead();
/* 325 */     unixPath2.checkRead();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 330 */       unixFileAttributes1 = UnixFileAttributes.get(unixPath1, true);
/* 331 */     } catch (UnixException unixException) {
/* 332 */       unixException.rethrowAsIOException(unixPath1);
/* 333 */       return false;
/*     */     } 
/*     */     try {
/* 336 */       unixFileAttributes2 = UnixFileAttributes.get(unixPath2, true);
/* 337 */     } catch (UnixException unixException) {
/* 338 */       unixException.rethrowAsIOException(unixPath2);
/* 339 */       return false;
/*     */     } 
/* 341 */     return unixFileAttributes1.isSameFile(unixFileAttributes2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHidden(Path paramPath) {
/* 346 */     UnixPath unixPath1 = UnixPath.toUnixPath(paramPath);
/* 347 */     unixPath1.checkRead();
/* 348 */     UnixPath unixPath2 = unixPath1.getFileName();
/* 349 */     if (unixPath2 == null)
/* 350 */       return false; 
/* 351 */     return (unixPath2.asByteArray()[0] == 46);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract FileStore getFileStore(UnixPath paramUnixPath) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   public FileStore getFileStore(Path paramPath) throws IOException {
/* 362 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 363 */     SecurityManager securityManager = System.getSecurityManager();
/* 364 */     if (securityManager != null) {
/* 365 */       securityManager.checkPermission(new RuntimePermission("getFileStoreAttributes"));
/* 366 */       unixPath.checkRead();
/*     */     } 
/* 368 */     return getFileStore(unixPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createDirectory(Path paramPath, FileAttribute<?>... paramVarArgs) throws IOException {
/* 375 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 376 */     unixPath.checkWrite();
/*     */     
/* 378 */     int i = UnixFileModeAttribute.toUnixMode(511, paramVarArgs);
/*     */     try {
/* 380 */       UnixNativeDispatcher.mkdir(unixPath, i);
/* 381 */     } catch (UnixException unixException) {
/* 382 */       if (unixException.errno() == 21)
/* 383 */         throw new FileAlreadyExistsException(unixPath.toString()); 
/* 384 */       unixException.rethrowAsIOException(unixPath);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectoryStream<Path> newDirectoryStream(Path paramPath, DirectoryStream.Filter<? super Path> paramFilter) throws IOException {
/* 393 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/* 394 */     unixPath.checkRead();
/* 395 */     if (paramFilter == null) {
/* 396 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */     
/* 400 */     if (!UnixNativeDispatcher.openatSupported()) {
/*     */       try {
/* 402 */         long l1 = UnixNativeDispatcher.opendir(unixPath);
/* 403 */         return new UnixDirectoryStream(unixPath, l1, paramFilter);
/* 404 */       } catch (UnixException unixException) {
/* 405 */         if (unixException.errno() == 20)
/* 406 */           throw new NotDirectoryException(unixPath.getPathForExceptionMessage()); 
/* 407 */         unixException.rethrowAsIOException(unixPath);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 413 */     int i = -1;
/* 414 */     int j = -1;
/* 415 */     long l = 0L;
/*     */     try {
/* 417 */       i = UnixNativeDispatcher.open(unixPath, 0, 0);
/* 418 */       j = UnixNativeDispatcher.dup(i);
/* 419 */       l = UnixNativeDispatcher.fdopendir(i);
/* 420 */     } catch (UnixException unixException) {
/* 421 */       if (i != -1)
/* 422 */         UnixNativeDispatcher.close(i); 
/* 423 */       if (j != -1)
/* 424 */         UnixNativeDispatcher.close(j); 
/* 425 */       if (unixException.errno() == 20)
/* 426 */         throw new NotDirectoryException(unixPath.getPathForExceptionMessage()); 
/* 427 */       unixException.rethrowAsIOException(unixPath);
/*     */     } 
/* 429 */     return new UnixSecureDirectoryStream(unixPath, l, j, paramFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createSymbolicLink(Path paramPath1, Path paramPath2, FileAttribute<?>... paramVarArgs) throws IOException {
/* 436 */     UnixPath unixPath1 = UnixPath.toUnixPath(paramPath1);
/* 437 */     UnixPath unixPath2 = UnixPath.toUnixPath(paramPath2);
/*     */ 
/*     */     
/* 440 */     if (paramVarArgs.length > 0) {
/* 441 */       UnixFileModeAttribute.toUnixMode(0, paramVarArgs);
/* 442 */       throw new UnsupportedOperationException("Initial file attributesnot supported when creating symbolic link");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 447 */     SecurityManager securityManager = System.getSecurityManager();
/* 448 */     if (securityManager != null) {
/* 449 */       securityManager.checkPermission(new LinkPermission("symbolic"));
/* 450 */       unixPath1.checkWrite();
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 455 */       UnixNativeDispatcher.symlink(unixPath2.asByteArray(), unixPath1);
/* 456 */     } catch (UnixException unixException) {
/* 457 */       unixException.rethrowAsIOException(unixPath1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void createLink(Path paramPath1, Path paramPath2) throws IOException {
/* 463 */     UnixPath unixPath1 = UnixPath.toUnixPath(paramPath1);
/* 464 */     UnixPath unixPath2 = UnixPath.toUnixPath(paramPath2);
/*     */ 
/*     */     
/* 467 */     SecurityManager securityManager = System.getSecurityManager();
/* 468 */     if (securityManager != null) {
/* 469 */       securityManager.checkPermission(new LinkPermission("hard"));
/* 470 */       unixPath1.checkWrite();
/* 471 */       unixPath2.checkWrite();
/*     */     } 
/*     */     try {
/* 474 */       UnixNativeDispatcher.link(unixPath2, unixPath1);
/* 475 */     } catch (UnixException unixException) {
/* 476 */       unixException.rethrowAsIOException(unixPath1, unixPath2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Path readSymbolicLink(Path paramPath) throws IOException {
/* 482 */     UnixPath unixPath = UnixPath.toUnixPath(paramPath);
/*     */     
/* 484 */     SecurityManager securityManager = System.getSecurityManager();
/* 485 */     if (securityManager != null) {
/* 486 */       FilePermission filePermission = new FilePermission(unixPath.getPathForPermissionCheck(), "readlink");
/*     */       
/* 488 */       securityManager.checkPermission(filePermission);
/*     */     } 
/*     */     try {
/* 491 */       byte[] arrayOfByte = UnixNativeDispatcher.readlink(unixPath);
/* 492 */       return new UnixPath(unixPath.getFileSystem(), arrayOfByte);
/* 493 */     } catch (UnixException unixException) {
/* 494 */       if (unixException.errno() == 22)
/* 495 */         throw new NotLinkException(unixPath.getPathForExceptionMessage()); 
/* 496 */       unixException.rethrowAsIOException(unixPath);
/* 497 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FileTypeDetector getFileTypeDetector() {
/* 505 */     return new AbstractFileTypeDetector()
/*     */       {
/*     */         public String implProbeContentType(Path param1Path) {
/* 508 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final FileTypeDetector chain(AbstractFileTypeDetector... detectors) {
/* 520 */     return new AbstractFileTypeDetector()
/*     */       {
/*     */         protected String implProbeContentType(Path param1Path) throws IOException {
/* 523 */           for (AbstractFileTypeDetector abstractFileTypeDetector : detectors) {
/* 524 */             String str = abstractFileTypeDetector.implProbeContentType(param1Path);
/* 525 */             if (str != null && !str.isEmpty()) {
/* 526 */               return str;
/*     */             }
/*     */           } 
/* 529 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixFileSystemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */