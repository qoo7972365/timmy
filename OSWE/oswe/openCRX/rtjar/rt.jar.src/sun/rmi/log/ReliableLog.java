/*     */ package sun.rmi.log;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.security.action.GetBooleanAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReliableLog
/*     */ {
/*     */   public static final int PreferredMajorVersion = 0;
/*     */   public static final int PreferredMinorVersion = 2;
/*     */   private boolean Debug = false;
/*  89 */   private static String snapshotPrefix = "Snapshot.";
/*  90 */   private static String logfilePrefix = "Logfile.";
/*  91 */   private static String versionFile = "Version_Number";
/*  92 */   private static String newVersionFile = "New_Version_Number";
/*  93 */   private static int intBytes = 4;
/*  94 */   private static long diskPageSize = 512L;
/*     */   
/*     */   private File dir;
/*  97 */   private int version = 0;
/*  98 */   private String logName = null;
/*  99 */   private LogFile log = null;
/* 100 */   private long snapshotBytes = 0L;
/* 101 */   private long logBytes = 0L;
/* 102 */   private int logEntries = 0;
/* 103 */   private long lastSnapshot = 0L;
/* 104 */   private long lastLog = 0L;
/*     */   
/*     */   private LogHandler handler;
/* 107 */   private final byte[] intBuf = new byte[4];
/*     */ 
/*     */   
/* 110 */   private int majorFormatVersion = 0;
/* 111 */   private int minorFormatVersion = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private static final Constructor<? extends LogFile> logClassConstructor = getLogClassConstructor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableLog(String paramString, LogHandler paramLogHandler, boolean paramBoolean) throws IOException {
/* 143 */     this
/* 144 */       .Debug = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.rmi.log.debug"))).booleanValue();
/* 145 */     this.dir = new File(paramString);
/* 146 */     if (!this.dir.exists() || !this.dir.isDirectory())
/*     */     {
/* 148 */       if (!this.dir.mkdir()) {
/* 149 */         throw new IOException("could not create directory for log: " + paramString);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 154 */     this.handler = paramLogHandler;
/* 155 */     this.lastSnapshot = 0L;
/* 156 */     this.lastLog = 0L;
/* 157 */     getVersion();
/* 158 */     if (this.version == 0) {
/*     */       try {
/* 160 */         snapshot(paramLogHandler.initialSnapshot());
/* 161 */       } catch (IOException iOException) {
/* 162 */         throw iOException;
/* 163 */       } catch (Exception exception) {
/* 164 */         throw new IOException("initial snapshot failed with exception: " + exception);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReliableLog(String paramString, LogHandler paramLogHandler) throws IOException {
/* 184 */     this(paramString, paramLogHandler, false);
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
/*     */   public synchronized Object recover() throws IOException {
/*     */     Object object;
/* 202 */     if (this.Debug) {
/* 203 */       System.err.println("log.debug: recover()");
/*     */     }
/* 205 */     if (this.version == 0) {
/* 206 */       return null;
/*     */     }
/*     */     
/* 209 */     String str = versionName(snapshotPrefix);
/* 210 */     File file = new File(str);
/* 211 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
/*     */ 
/*     */     
/* 214 */     if (this.Debug) {
/* 215 */       System.err.println("log.debug: recovering from " + str);
/*     */     }
/*     */     try {
/*     */       try {
/* 219 */         object = this.handler.recover(bufferedInputStream);
/*     */       }
/* 221 */       catch (IOException iOException) {
/* 222 */         throw iOException;
/* 223 */       } catch (Exception exception) {
/* 224 */         if (this.Debug)
/* 225 */           System.err.println("log.debug: recovery failed: " + exception); 
/* 226 */         throw new IOException("log recover failed with exception: " + exception);
/*     */       } 
/*     */       
/* 229 */       this.snapshotBytes = file.length();
/*     */     } finally {
/* 231 */       bufferedInputStream.close();
/*     */     } 
/*     */     
/* 234 */     return recoverUpdates(object);
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
/*     */   public synchronized void update(Object paramObject) throws IOException {
/* 248 */     update(paramObject, true);
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
/*     */   public synchronized void update(Object paramObject, boolean paramBoolean) throws IOException {
/* 267 */     if (this.log == null) {
/* 268 */       throw new IOException("log is inaccessible, it may have been corrupted or closed");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     long l1 = this.log.getFilePointer();
/* 278 */     boolean bool = this.log.checkSpansBoundary(l1);
/* 279 */     writeInt(this.log, bool ? Integer.MIN_VALUE : 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 285 */       this.handler.writeUpdate(new LogOutputStream(this.log), paramObject);
/* 286 */     } catch (IOException iOException) {
/* 287 */       throw iOException;
/* 288 */     } catch (Exception exception) {
/* 289 */       throw (IOException)(new IOException("write update failed"))
/* 290 */         .initCause(exception);
/*     */     } 
/* 292 */     this.log.sync();
/*     */     
/* 294 */     long l2 = this.log.getFilePointer();
/* 295 */     int i = (int)(l2 - l1 - intBytes);
/* 296 */     this.log.seek(l1);
/*     */     
/* 298 */     if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       writeInt(this.log, i | Integer.MIN_VALUE);
/* 307 */       this.log.sync();
/*     */       
/* 309 */       this.log.seek(l1);
/* 310 */       this.log.writeByte(i >> 24);
/* 311 */       this.log.sync();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 317 */       writeInt(this.log, i);
/* 318 */       this.log.sync();
/*     */     } 
/*     */     
/* 321 */     this.log.seek(l2);
/* 322 */     this.logBytes = l2;
/* 323 */     this.lastLog = System.currentTimeMillis();
/* 324 */     this.logEntries++;
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
/*     */   private static Constructor<? extends LogFile> getLogClassConstructor() {
/* 336 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.log.class"));
/*     */     
/* 338 */     if (str != null) {
/*     */       
/*     */       try {
/* 341 */         ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */             {
/*     */               public ClassLoader run() {
/* 344 */                 return ClassLoader.getSystemClassLoader();
/*     */               }
/*     */             });
/*     */         
/* 348 */         Class<? extends LogFile> clazz = classLoader.loadClass(str).asSubclass(LogFile.class);
/* 349 */         return clazz.getConstructor(new Class[] { String.class, String.class });
/* 350 */       } catch (Exception exception) {
/* 351 */         System.err.println("Exception occurred:");
/* 352 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/* 355 */     return null;
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
/*     */   public synchronized void snapshot(Object paramObject) throws IOException {
/* 370 */     int i = this.version;
/* 371 */     incrVersion();
/*     */     
/* 373 */     String str = versionName(snapshotPrefix);
/* 374 */     File file = new File(str);
/* 375 */     FileOutputStream fileOutputStream = new FileOutputStream(file);
/*     */     try {
/*     */       try {
/* 378 */         this.handler.snapshot(fileOutputStream, paramObject);
/* 379 */       } catch (IOException iOException) {
/* 380 */         throw iOException;
/* 381 */       } catch (Exception exception) {
/* 382 */         throw new IOException("snapshot failed", exception);
/*     */       } 
/* 384 */       this.lastSnapshot = System.currentTimeMillis();
/*     */     } finally {
/* 386 */       fileOutputStream.close();
/* 387 */       this.snapshotBytes = file.length();
/*     */     } 
/*     */     
/* 390 */     openLogFile(true);
/* 391 */     writeVersionFile(true);
/* 392 */     commitToNewVersion();
/* 393 */     deleteSnapshot(i);
/* 394 */     deleteLogFile(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() throws IOException {
/* 404 */     if (this.log == null)
/*     */       return;  try {
/* 406 */       this.log.close();
/*     */     } finally {
/* 408 */       this.log = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long snapshotSize() {
/* 416 */     return this.snapshotBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long logSize() {
/* 423 */     return this.logBytes;
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
/*     */   private void writeInt(DataOutput paramDataOutput, int paramInt) throws IOException {
/* 439 */     this.intBuf[0] = (byte)(paramInt >> 24);
/* 440 */     this.intBuf[1] = (byte)(paramInt >> 16);
/* 441 */     this.intBuf[2] = (byte)(paramInt >> 8);
/* 442 */     this.intBuf[3] = (byte)paramInt;
/* 443 */     paramDataOutput.write(this.intBuf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String fName(String paramString) {
/* 452 */     return this.dir.getPath() + File.separator + paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String versionName(String paramString) {
/* 462 */     return versionName(paramString, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String versionName(String paramString, int paramInt) {
/* 473 */     paramInt = (paramInt == 0) ? this.version : paramInt;
/* 474 */     return fName(paramString) + String.valueOf(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void incrVersion() {
/*     */     
/* 481 */     do { this.version++; } while (this.version == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteFile(String paramString) throws IOException {
/* 492 */     File file = new File(paramString);
/* 493 */     if (!file.delete()) {
/* 494 */       throw new IOException("couldn't remove file: " + paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteNewVersionFile() throws IOException {
/* 503 */     deleteFile(fName(newVersionFile));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteSnapshot(int paramInt) throws IOException {
/* 513 */     if (paramInt == 0)
/* 514 */       return;  deleteFile(versionName(snapshotPrefix, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void deleteLogFile(int paramInt) throws IOException {
/* 524 */     if (paramInt == 0)
/* 525 */       return;  deleteFile(versionName(logfilePrefix, paramInt));
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
/*     */   private void openLogFile(boolean paramBoolean) throws IOException {
/*     */     try {
/* 538 */       close();
/* 539 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 542 */     this.logName = versionName(logfilePrefix);
/*     */     
/*     */     try {
/* 545 */       this
/*     */         
/* 547 */         .log = (logClassConstructor == null) ? new LogFile(this.logName, "rw") : logClassConstructor.newInstance(new Object[] { this.logName, "rw" });
/* 548 */     } catch (Exception exception) {
/* 549 */       throw (IOException)(new IOException("unable to construct LogFile instance"))
/* 550 */         .initCause(exception);
/*     */     } 
/*     */     
/* 553 */     if (paramBoolean) {
/* 554 */       initializeLogFile();
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
/*     */ 
/*     */   
/*     */   private void initializeLogFile() throws IOException {
/* 574 */     this.log.setLength(0L);
/* 575 */     this.majorFormatVersion = 0;
/* 576 */     writeInt(this.log, 0);
/* 577 */     this.minorFormatVersion = 2;
/* 578 */     writeInt(this.log, 2);
/* 579 */     this.logBytes = (intBytes * 2);
/* 580 */     this.logEntries = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeVersionFile(boolean paramBoolean) throws IOException {
/*     */     String str;
/* 592 */     if (paramBoolean) {
/* 593 */       str = newVersionFile;
/*     */     } else {
/* 595 */       str = versionFile;
/*     */     } 
/* 597 */     try(FileOutputStream null = new FileOutputStream(fName(str)); 
/* 598 */         DataOutputStream null = new DataOutputStream(fileOutputStream)) {
/* 599 */       writeInt(dataOutputStream, this.version);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFirstVersion() throws IOException {
/* 609 */     this.version = 0;
/* 610 */     writeVersionFile(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void commitToNewVersion() throws IOException {
/* 619 */     writeVersionFile(false);
/* 620 */     deleteNewVersionFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readVersion(String paramString) throws IOException {
/* 631 */     try (DataInputStream null = new DataInputStream(new FileInputStream(paramString))) {
/*     */       
/* 633 */       return dataInputStream.readInt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void getVersion() throws IOException {
/*     */     try {
/* 645 */       this.version = readVersion(fName(newVersionFile));
/* 646 */       commitToNewVersion();
/* 647 */     } catch (IOException iOException) {
/*     */       try {
/* 649 */         deleteNewVersionFile();
/*     */       }
/* 651 */       catch (IOException iOException1) {}
/*     */ 
/*     */       
/*     */       try {
/* 655 */         this.version = readVersion(fName(versionFile));
/*     */       }
/* 657 */       catch (IOException iOException1) {
/* 658 */         createFirstVersion();
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
/*     */ 
/*     */ 
/*     */   
/*     */   private Object recoverUpdates(Object paramObject) throws IOException {
/* 675 */     this.logBytes = 0L;
/* 676 */     this.logEntries = 0;
/*     */     
/* 678 */     if (this.version == 0) return paramObject;
/*     */     
/* 680 */     String str = versionName(logfilePrefix);
/* 681 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
/*     */     
/* 683 */     DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
/*     */     
/* 685 */     if (this.Debug) {
/* 686 */       System.err.println("log.debug: reading updates from " + str);
/*     */     }
/*     */     try {
/* 689 */       this.majorFormatVersion = dataInputStream.readInt(); this.logBytes += intBytes;
/* 690 */       this.minorFormatVersion = dataInputStream.readInt(); this.logBytes += intBytes;
/* 691 */     } catch (EOFException eOFException) {
/*     */ 
/*     */ 
/*     */       
/* 695 */       openLogFile(true);
/* 696 */       bufferedInputStream = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 704 */     if (this.majorFormatVersion != 0) {
/* 705 */       if (this.Debug) {
/* 706 */         System.err.println("log.debug: major version mismatch: " + this.majorFormatVersion + "." + this.minorFormatVersion);
/*     */       }
/*     */       
/* 709 */       throw new IOException("Log file " + this.logName + " has a version " + this.majorFormatVersion + "." + this.minorFormatVersion + " format, and this implementation  understands only version " + Character.MIN_VALUE + "." + '\002');
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 719 */       while (bufferedInputStream != null) {
/* 720 */         int i = 0;
/*     */         
/*     */         try {
/* 723 */           i = dataInputStream.readInt();
/* 724 */         } catch (EOFException eOFException) {
/* 725 */           if (this.Debug)
/* 726 */             System.err.println("log.debug: log was sync'd cleanly"); 
/*     */           break;
/*     */         } 
/* 729 */         if (i <= 0) {
/* 730 */           if (this.Debug) {
/* 731 */             System.err.println("log.debug: last update incomplete, updateLen = 0x" + 
/*     */ 
/*     */                 
/* 734 */                 Integer.toHexString(i));
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 743 */         if (bufferedInputStream.available() < i) {
/*     */ 
/*     */ 
/*     */           
/* 747 */           if (this.Debug) {
/* 748 */             System.err.println("log.debug: log was truncated");
/*     */           }
/*     */           break;
/*     */         } 
/* 752 */         if (this.Debug)
/* 753 */           System.err.println("log.debug: rdUpdate size " + i); 
/*     */         try {
/* 755 */           paramObject = this.handler.readUpdate(new LogInputStream(bufferedInputStream, i), paramObject);
/*     */         }
/* 757 */         catch (IOException iOException) {
/* 758 */           throw iOException;
/* 759 */         } catch (Exception exception) {
/* 760 */           exception.printStackTrace();
/* 761 */           throw new IOException("read update failed with exception: " + exception);
/*     */         } 
/*     */         
/* 764 */         this.logBytes += (intBytes + i);
/* 765 */         this.logEntries++;
/*     */       } 
/*     */     } finally {
/* 768 */       if (bufferedInputStream != null) {
/* 769 */         bufferedInputStream.close();
/*     */       }
/*     */     } 
/* 772 */     if (this.Debug) {
/* 773 */       System.err.println("log.debug: recovered updates: " + this.logEntries);
/*     */     }
/*     */     
/* 776 */     openLogFile(false);
/*     */ 
/*     */     
/* 779 */     if (this.log == null) {
/* 780 */       throw new IOException("rmid's log is inaccessible, it may have been corrupted or closed");
/*     */     }
/*     */ 
/*     */     
/* 784 */     this.log.seek(this.logBytes);
/* 785 */     this.log.setLength(this.logBytes);
/*     */     
/* 787 */     return paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LogFile
/*     */     extends RandomAccessFile
/*     */   {
/*     */     private final FileDescriptor fd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LogFile(String param1String1, String param1String2) throws FileNotFoundException, IOException {
/* 804 */       super(param1String1, param1String2);
/* 805 */       this.fd = getFD();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void sync() throws IOException {
/* 812 */       this.fd.sync();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean checkSpansBoundary(long param1Long) {
/* 821 */       return (param1Long % 512L > 508L);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/log/ReliableLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */