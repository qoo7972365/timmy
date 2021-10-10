/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.OverlappingFileLockException;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileHandler
/*     */   extends StreamHandler
/*     */ {
/*     */   private MeteredStream meter;
/*     */   private boolean append;
/*     */   private int limit;
/*     */   private int count;
/*     */   private String pattern;
/*     */   private String lockFileName;
/*     */   private FileChannel lockFileChannel;
/*     */   private File[] files;
/*     */   private static final int DEFAULT_MAX_LOCKS = 100;
/*     */   private static int maxLocks;
/* 161 */   private static final Set<String> locks = new HashSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 168 */     maxLocks = ((Integer)AccessController.<Integer>doPrivileged(() -> Integer.getInteger("jdk.internal.FileHandlerLogging.maxLocks", 100))).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     if (maxLocks <= 0) {
/* 176 */       maxLocks = 100;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class MeteredStream
/*     */     extends OutputStream
/*     */   {
/*     */     final OutputStream out;
/*     */     
/*     */     int written;
/*     */ 
/*     */     
/*     */     MeteredStream(OutputStream param1OutputStream, int param1Int) {
/* 190 */       this.out = param1OutputStream;
/* 191 */       this.written = param1Int;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(int param1Int) throws IOException {
/* 196 */       this.out.write(param1Int);
/* 197 */       this.written++;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte) throws IOException {
/* 202 */       this.out.write(param1ArrayOfbyte);
/* 203 */       this.written += param1ArrayOfbyte.length;
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 208 */       this.out.write(param1ArrayOfbyte, param1Int1, param1Int2);
/* 209 */       this.written += param1Int2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void flush() throws IOException {
/* 214 */       this.out.flush();
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 219 */       this.out.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private void open(File paramFile, boolean paramBoolean) throws IOException {
/* 224 */     int i = 0;
/* 225 */     if (paramBoolean) {
/* 226 */       i = (int)paramFile.length();
/*     */     }
/* 228 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile.toString(), paramBoolean);
/* 229 */     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
/* 230 */     this.meter = new MeteredStream(bufferedOutputStream, i);
/* 231 */     setOutputStream(this.meter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void configure() {
/* 239 */     LogManager logManager = LogManager.getLogManager();
/*     */     
/* 241 */     String str = getClass().getName();
/*     */     
/* 243 */     this.pattern = logManager.getStringProperty(str + ".pattern", "%h/java%u.log");
/* 244 */     this.limit = logManager.getIntProperty(str + ".limit", 0);
/* 245 */     if (this.limit < 0) {
/* 246 */       this.limit = 0;
/*     */     }
/* 248 */     this.count = logManager.getIntProperty(str + ".count", 1);
/* 249 */     if (this.count <= 0) {
/* 250 */       this.count = 1;
/*     */     }
/* 252 */     this.append = logManager.getBooleanProperty(str + ".append", false);
/* 253 */     setLevel(logManager.getLevelProperty(str + ".level", Level.ALL));
/* 254 */     setFilter(logManager.getFilterProperty(str + ".filter", null));
/* 255 */     setFormatter(logManager.getFormatterProperty(str + ".formatter", new XMLFormatter()));
/*     */     try {
/* 257 */       setEncoding(logManager.getStringProperty(str + ".encoding", null));
/* 258 */     } catch (Exception exception) {
/*     */       try {
/* 260 */         setEncoding((String)null);
/* 261 */       } catch (Exception exception1) {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileHandler() throws IOException, SecurityException {
/* 279 */     checkPermission();
/* 280 */     configure();
/* 281 */     openFiles();
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
/*     */   public FileHandler(String paramString) throws IOException, SecurityException {
/* 302 */     if (paramString.length() < 1) {
/* 303 */       throw new IllegalArgumentException();
/*     */     }
/* 305 */     checkPermission();
/* 306 */     configure();
/* 307 */     this.pattern = paramString;
/* 308 */     this.limit = 0;
/* 309 */     this.count = 1;
/* 310 */     openFiles();
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
/*     */   public FileHandler(String paramString, boolean paramBoolean) throws IOException, SecurityException {
/* 335 */     if (paramString.length() < 1) {
/* 336 */       throw new IllegalArgumentException();
/*     */     }
/* 338 */     checkPermission();
/* 339 */     configure();
/* 340 */     this.pattern = paramString;
/* 341 */     this.limit = 0;
/* 342 */     this.count = 1;
/* 343 */     this.append = paramBoolean;
/* 344 */     openFiles();
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
/*     */   public FileHandler(String paramString, int paramInt1, int paramInt2) throws IOException, SecurityException {
/* 372 */     if (paramInt1 < 0 || paramInt2 < 1 || paramString.length() < 1) {
/* 373 */       throw new IllegalArgumentException();
/*     */     }
/* 375 */     checkPermission();
/* 376 */     configure();
/* 377 */     this.pattern = paramString;
/* 378 */     this.limit = paramInt1;
/* 379 */     this.count = paramInt2;
/* 380 */     openFiles();
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
/*     */   public FileHandler(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) throws IOException, SecurityException {
/* 411 */     if (paramInt1 < 0 || paramInt2 < 1 || paramString.length() < 1) {
/* 412 */       throw new IllegalArgumentException();
/*     */     }
/* 414 */     checkPermission();
/* 415 */     configure();
/* 416 */     this.pattern = paramString;
/* 417 */     this.limit = paramInt1;
/* 418 */     this.count = paramInt2;
/* 419 */     this.append = paramBoolean;
/* 420 */     openFiles();
/*     */   }
/*     */   
/*     */   private boolean isParentWritable(Path paramPath) {
/* 424 */     Path path = paramPath.getParent();
/* 425 */     if (path == null) {
/* 426 */       path = paramPath.toAbsolutePath().getParent();
/*     */     }
/* 428 */     return (path != null && Files.isWritable(path));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void openFiles() throws IOException {
/* 436 */     LogManager logManager = LogManager.getLogManager();
/* 437 */     logManager.checkPermission();
/* 438 */     if (this.count < 1) {
/* 439 */       throw new IllegalArgumentException("file count = " + this.count);
/*     */     }
/* 441 */     if (this.limit < 0) {
/* 442 */       this.limit = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 447 */     InitializationErrorManager initializationErrorManager = new InitializationErrorManager();
/* 448 */     setErrorManager(initializationErrorManager);
/*     */ 
/*     */ 
/*     */     
/* 452 */     byte b = -1;
/*     */     while (true) {
/* 454 */       b++;
/* 455 */       if (b > maxLocks) {
/* 456 */         throw new IOException("Couldn't get lock for " + this.pattern + ", maxLocks: " + maxLocks);
/*     */       }
/*     */ 
/*     */       
/* 460 */       this.lockFileName = generate(this.pattern, 0, b).toString() + ".lck";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 465 */       synchronized (locks) {
/* 466 */         boolean bool2; if (locks.contains(this.lockFileName)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 472 */         Path path = Paths.get(this.lockFileName, new String[0]);
/* 473 */         FileChannel fileChannel = null;
/* 474 */         byte b2 = -1;
/* 475 */         boolean bool1 = false;
/* 476 */         while (fileChannel == null && b2++ < 1) {
/*     */           try {
/* 478 */             fileChannel = FileChannel.open(path, new OpenOption[] { StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE });
/*     */             
/* 480 */             bool1 = true;
/*     */ 
/*     */           
/*     */           }
/*     */           catch (FileAlreadyExistsException fileAlreadyExistsException) {
/*     */ 
/*     */             
/* 487 */             if (Files.isRegularFile(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS
/* 488 */                 }) && isParentWritable(path)) {
/*     */               try {
/* 490 */                 fileChannel = FileChannel.open(path, new OpenOption[] { StandardOpenOption.WRITE, StandardOpenOption.APPEND });
/*     */                 continue;
/* 492 */               } catch (NoSuchFileException noSuchFileException) {
/*     */ 
/*     */                 
/*     */                 continue;
/*     */               }
/* 497 */               catch (IOException iOException) {
/*     */                 break;
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 510 */         if (fileChannel == null)
/* 511 */           continue;  this.lockFileChannel = fileChannel;
/*     */ 
/*     */         
/*     */         try {
/* 515 */           bool2 = (this.lockFileChannel.tryLock() != null) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 523 */         catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 529 */           bool2 = bool1;
/* 530 */         } catch (OverlappingFileLockException overlappingFileLockException) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 535 */           bool2 = false;
/*     */         } 
/* 537 */         if (bool2) {
/*     */           
/* 539 */           locks.add(this.lockFileName);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 544 */         this.lockFileChannel.close();
/*     */       } 
/*     */     } 
/*     */     
/* 548 */     this.files = new File[this.count];
/* 549 */     for (byte b1 = 0; b1 < this.count; b1++) {
/* 550 */       this.files[b1] = generate(this.pattern, b1, b);
/*     */     }
/*     */ 
/*     */     
/* 554 */     if (this.append) {
/* 555 */       open(this.files[0], true);
/*     */     } else {
/* 557 */       rotate();
/*     */     } 
/*     */ 
/*     */     
/* 561 */     Exception exception = initializationErrorManager.lastException;
/* 562 */     if (exception != null) {
/* 563 */       if (exception instanceof IOException)
/* 564 */         throw (IOException)exception; 
/* 565 */       if (exception instanceof SecurityException) {
/* 566 */         throw (SecurityException)exception;
/*     */       }
/* 568 */       throw new IOException("Exception: " + exception);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 573 */     setErrorManager(new ErrorManager());
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
/*     */   private File generate(String paramString, int paramInt1, int paramInt2) throws IOException {
/* 587 */     File file = null;
/* 588 */     String str = "";
/* 589 */     byte b = 0;
/* 590 */     boolean bool1 = false;
/* 591 */     boolean bool2 = false;
/* 592 */     while (b < paramString.length()) {
/* 593 */       char c = paramString.charAt(b);
/* 594 */       b++;
/* 595 */       char c1 = Character.MIN_VALUE;
/* 596 */       if (b < paramString.length()) {
/* 597 */         c1 = Character.toLowerCase(paramString.charAt(b));
/*     */       }
/* 599 */       if (c == '/') {
/* 600 */         if (file == null) {
/* 601 */           file = new File(str);
/*     */         } else {
/* 603 */           file = new File(file, str);
/*     */         } 
/* 605 */         str = ""; continue;
/*     */       } 
/* 607 */       if (c == '%') {
/* 608 */         if (c1 == 't') {
/* 609 */           String str1 = System.getProperty("java.io.tmpdir");
/* 610 */           if (str1 == null) {
/* 611 */             str1 = System.getProperty("user.home");
/*     */           }
/* 613 */           file = new File(str1);
/* 614 */           b++;
/* 615 */           str = ""; continue;
/*     */         } 
/* 617 */         if (c1 == 'h') {
/* 618 */           file = new File(System.getProperty("user.home"));
/* 619 */           if (isSetUID())
/*     */           {
/*     */             
/* 622 */             throw new IOException("can't use %h in set UID program");
/*     */           }
/* 624 */           b++;
/* 625 */           str = ""; continue;
/*     */         } 
/* 627 */         if (c1 == 'g') {
/* 628 */           str = str + paramInt1;
/* 629 */           bool1 = true;
/* 630 */           b++; continue;
/*     */         } 
/* 632 */         if (c1 == 'u') {
/* 633 */           str = str + paramInt2;
/* 634 */           bool2 = true;
/* 635 */           b++; continue;
/*     */         } 
/* 637 */         if (c1 == '%') {
/* 638 */           str = str + "%";
/* 639 */           b++;
/*     */           continue;
/*     */         } 
/*     */       } 
/* 643 */       str = str + c;
/*     */     } 
/* 645 */     if (this.count > 1 && !bool1) {
/* 646 */       str = str + "." + paramInt1;
/*     */     }
/* 648 */     if (paramInt2 > 0 && !bool2) {
/* 649 */       str = str + "." + paramInt2;
/*     */     }
/* 651 */     if (str.length() > 0) {
/* 652 */       if (file == null) {
/* 653 */         file = new File(str);
/*     */       } else {
/* 655 */         file = new File(file, str);
/*     */       } 
/*     */     }
/* 658 */     return file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void rotate() {
/* 665 */     Level level = getLevel();
/* 666 */     setLevel(Level.OFF);
/*     */     
/* 668 */     super.close();
/* 669 */     for (int i = this.count - 2; i >= 0; i--) {
/* 670 */       File file1 = this.files[i];
/* 671 */       File file2 = this.files[i + 1];
/* 672 */       if (file1.exists()) {
/* 673 */         if (file2.exists()) {
/* 674 */           file2.delete();
/*     */         }
/* 676 */         file1.renameTo(file2);
/*     */       } 
/*     */     } 
/*     */     try {
/* 680 */       open(this.files[0], false);
/* 681 */     } catch (IOException iOException) {
/*     */ 
/*     */       
/* 684 */       reportError(null, iOException, 4);
/*     */     } 
/*     */     
/* 687 */     setLevel(level);
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
/* 698 */     if (!isLoggable(paramLogRecord)) {
/*     */       return;
/*     */     }
/* 701 */     super.publish(paramLogRecord);
/* 702 */     flush();
/* 703 */     if (this.limit > 0 && this.meter.written >= this.limit)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 709 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run() {
/* 712 */               FileHandler.this.rotate();
/* 713 */               return null;
/*     */             }
/*     */           });
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
/*     */   public synchronized void close() throws SecurityException {
/* 727 */     super.close();
/*     */     
/* 729 */     if (this.lockFileName == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 734 */       this.lockFileChannel.close();
/* 735 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 738 */     synchronized (locks) {
/* 739 */       locks.remove(this.lockFileName);
/*     */     } 
/* 741 */     (new File(this.lockFileName)).delete();
/* 742 */     this.lockFileName = null;
/* 743 */     this.lockFileChannel = null;
/*     */   }
/*     */   
/*     */   private static native boolean isSetUID();
/*     */   
/*     */   private static class InitializationErrorManager extends ErrorManager {
/*     */     public void error(String param1String, Exception param1Exception, int param1Int) {
/* 750 */       this.lastException = param1Exception;
/*     */     }
/*     */     
/*     */     Exception lastException;
/*     */     
/*     */     private InitializationErrorManager() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/FileHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */