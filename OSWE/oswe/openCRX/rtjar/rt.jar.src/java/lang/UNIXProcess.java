/*     */ package java.lang;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UNIXProcess
/*     */   extends Process
/*     */ {
/*  61 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */   
/*     */   private final int pid;
/*     */   
/*     */   private int exitcode;
/*     */   
/*     */   private boolean hasExited;
/*     */   
/*     */   private OutputStream stdin;
/*     */   private InputStream stdout;
/*     */   private InputStream stderr;
/*     */   private DeferredCloseInputStream stdout_inner_stream;
/*     */   
/*     */   private enum LaunchMechanism
/*     */   {
/*  76 */     FORK,
/*  77 */     POSIX_SPAWN,
/*  78 */     VFORK;
/*     */   }
/*     */   
/*     */   private enum Platform
/*     */   {
/*  83 */     LINUX((String)new UNIXProcess.LaunchMechanism[] { UNIXProcess.LaunchMechanism.VFORK, UNIXProcess.LaunchMechanism.FORK
/*     */       }),
/*  85 */     BSD((String)new UNIXProcess.LaunchMechanism[] { UNIXProcess.LaunchMechanism.POSIX_SPAWN, UNIXProcess.LaunchMechanism.FORK
/*     */       }),
/*  87 */     SOLARIS((String)new UNIXProcess.LaunchMechanism[] { UNIXProcess.LaunchMechanism.POSIX_SPAWN, UNIXProcess.LaunchMechanism.FORK
/*     */       }),
/*  89 */     AIX((String)new UNIXProcess.LaunchMechanism[] { UNIXProcess.LaunchMechanism.POSIX_SPAWN, UNIXProcess.LaunchMechanism.FORK });
/*     */     
/*     */     final UNIXProcess.LaunchMechanism defaultLaunchMechanism;
/*     */     final Set<UNIXProcess.LaunchMechanism> validLaunchMechanisms;
/*     */     
/*     */     Platform(UNIXProcess.LaunchMechanism... param1VarArgs) {
/*  95 */       this.defaultLaunchMechanism = param1VarArgs[0];
/*  96 */       this
/*  97 */         .validLaunchMechanisms = EnumSet.copyOf(Arrays.asList(param1VarArgs));
/*     */     }
/*     */     
/*     */     private String helperPath(String param1String1, String param1String2) {
/* 101 */       switch (this) {
/*     */         case SOLARIS:
/* 103 */           if (param1String2.equals("x86")) { param1String2 = "i386"; }
/* 104 */           else if (param1String2.equals("x86_64")) { param1String2 = "amd64"; }
/*     */         
/*     */         case LINUX:
/*     */         case AIX:
/* 108 */           return param1String1 + "/lib/" + param1String2 + "/jspawnhelper";
/*     */         
/*     */         case BSD:
/* 111 */           return param1String1 + "/lib/jspawnhelper";
/*     */       } 
/*     */       
/* 114 */       throw new AssertionError("Unsupported platform: " + this);
/*     */     }
/*     */ 
/*     */     
/*     */     String helperPath() {
/* 119 */       return AccessController.<String>doPrivileged(() -> helperPath(System.getProperty("java.home"), System.getProperty("os.arch")));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     UNIXProcess.LaunchMechanism launchMechanism() {
/* 127 */       return AccessController.<UNIXProcess.LaunchMechanism>doPrivileged(() -> {
/*     */             UNIXProcess.LaunchMechanism launchMechanism;
/*     */             
/*     */             String str = System.getProperty("jdk.lang.Process.launchMechanism");
/*     */             
/*     */             if (str == null) {
/*     */               launchMechanism = this.defaultLaunchMechanism;
/*     */               
/*     */               str = launchMechanism.name().toLowerCase(Locale.ENGLISH);
/*     */             } else {
/*     */               try {
/*     */                 launchMechanism = UNIXProcess.LaunchMechanism.valueOf(str.toUpperCase(Locale.ENGLISH));
/* 139 */               } catch (IllegalArgumentException illegalArgumentException) {
/*     */                 launchMechanism = null;
/*     */               } 
/*     */             } 
/*     */             if (launchMechanism == null || !this.validLaunchMechanisms.contains(launchMechanism)) {
/*     */               throw new Error(str + " is not a supported process launch mechanism on this platform.");
/*     */             }
/*     */             return launchMechanism;
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Platform get() {
/* 155 */       String str = AccessController.<String>doPrivileged(() -> System.getProperty("os.name"));
/*     */ 
/*     */ 
/*     */       
/* 159 */       if (str.equals("Linux")) return LINUX; 
/* 160 */       if (str.contains("OS X")) return BSD; 
/* 161 */       if (str.equals("SunOS")) return SOLARIS; 
/* 162 */       if (str.equals("AIX")) return AIX;
/*     */       
/* 164 */       throw new Error(str + " is not a supported OS platform.");
/*     */     }
/*     */   }
/*     */   
/* 168 */   private static final Platform platform = Platform.get();
/* 169 */   private static final LaunchMechanism launchMechanism = platform.launchMechanism();
/* 170 */   private static final byte[] helperpath = toCString(platform.helperPath());
/*     */   private static final Executor processReaperExecutor;
/*     */   UNIXProcess(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt1, byte[] paramArrayOfbyte3, int paramInt2, byte[] paramArrayOfbyte4, int[] paramArrayOfint, boolean paramBoolean) throws IOException { this.pid = forkAndExec(launchMechanism.ordinal() + 1, helperpath, paramArrayOfbyte1, paramArrayOfbyte2, paramInt1, paramArrayOfbyte3, paramInt2, paramArrayOfbyte4, paramArrayOfint, paramBoolean); try { AccessController.doPrivileged(() -> { initStreams(paramArrayOfint); return null; }); } catch (PrivilegedActionException privilegedActionException) { throw (IOException)privilegedActionException.getException(); }  }
/* 173 */   static FileDescriptor newFileDescriptor(int paramInt) { FileDescriptor fileDescriptor = new FileDescriptor(); fdAccess.set(fileDescriptor, paramInt); return fileDescriptor; } void initStreams(int[] paramArrayOfint) throws IOException { switch (platform) { case LINUX: case BSD: this.stdin = (paramArrayOfint[0] == -1) ? ProcessBuilder.NullOutputStream.INSTANCE : new ProcessPipeOutputStream(paramArrayOfint[0]); this.stdout = (paramArrayOfint[1] == -1) ? ProcessBuilder.NullInputStream.INSTANCE : new ProcessPipeInputStream(paramArrayOfint[1]); this.stderr = (paramArrayOfint[2] == -1) ? ProcessBuilder.NullInputStream.INSTANCE : new ProcessPipeInputStream(paramArrayOfint[2]); processReaperExecutor.execute(() -> { int i = waitForProcessExit(this.pid); synchronized (this) { this.exitcode = i; this.hasExited = true; notifyAll(); }  if (this.stdout instanceof ProcessPipeInputStream) ((ProcessPipeInputStream)this.stdout).processExited();  if (this.stderr instanceof ProcessPipeInputStream) ((ProcessPipeInputStream)this.stderr).processExited();  if (this.stdin instanceof ProcessPipeOutputStream) ((ProcessPipeOutputStream)this.stdin).processExited();  }); return;case SOLARIS: this.stdin = (paramArrayOfint[0] == -1) ? ProcessBuilder.NullOutputStream.INSTANCE : new BufferedOutputStream(new FileOutputStream(newFileDescriptor(paramArrayOfint[0]))); this.stdout = (paramArrayOfint[1] == -1) ? ProcessBuilder.NullInputStream.INSTANCE : new BufferedInputStream(this.stdout_inner_stream = new DeferredCloseInputStream(newFileDescriptor(paramArrayOfint[1]))); this.stderr = (paramArrayOfint[2] == -1) ? ProcessBuilder.NullInputStream.INSTANCE : new DeferredCloseInputStream(newFileDescriptor(paramArrayOfint[2])); processReaperExecutor.execute(() -> { int i = waitForProcessExit(this.pid); synchronized (this) { this.exitcode = i; this.hasExited = true; notifyAll(); }  }); return;case AIX: this.stdin = (paramArrayOfint[0] == -1) ? ProcessBuilder.NullOutputStream.INSTANCE : new ProcessPipeOutputStream(paramArrayOfint[0]); this.stdout = (paramArrayOfint[1] == -1) ? ProcessBuilder.NullInputStream.INSTANCE : new DeferredCloseProcessPipeInputStream(paramArrayOfint[1]); this.stderr = (paramArrayOfint[2] == -1) ? ProcessBuilder.NullInputStream.INSTANCE : new DeferredCloseProcessPipeInputStream(paramArrayOfint[2]); processReaperExecutor.execute(() -> { int i = waitForProcessExit(this.pid); synchronized (this) { this.exitcode = i; this.hasExited = true; notifyAll(); }  if (this.stdout instanceof DeferredCloseProcessPipeInputStream) ((DeferredCloseProcessPipeInputStream)this.stdout).processExited();  if (this.stderr instanceof DeferredCloseProcessPipeInputStream) ((DeferredCloseProcessPipeInputStream)this.stderr).processExited();  if (this.stdin instanceof ProcessPipeOutputStream) ((ProcessPipeOutputStream)this.stdin).processExited();  }); return; }  throw new AssertionError("Unsupported platform: " + platform); } public OutputStream getOutputStream() { return this.stdin; } public InputStream getInputStream() { return this.stdout; } private static byte[] toCString(String paramString) { if (paramString == null)
/* 174 */       return null; 
/* 175 */     byte[] arrayOfByte1 = paramString.getBytes();
/* 176 */     byte[] arrayOfByte2 = new byte[arrayOfByte1.length + 1];
/* 177 */     System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
/*     */ 
/*     */     
/* 180 */     arrayOfByte2[arrayOfByte2.length - 1] = 0;
/* 181 */     return arrayOfByte2; }
/*     */ 
/*     */   
/*     */   public InputStream getErrorStream() {
/*     */     return this.stderr;
/*     */   }
/*     */   
/*     */   public synchronized int waitFor() throws InterruptedException {
/*     */     while (!this.hasExited) {
/*     */       wait();
/*     */     }
/*     */     return this.exitcode;
/*     */   }
/*     */   
/*     */   public synchronized boolean waitFor(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
/*     */     if (this.hasExited) {
/*     */       return true;
/*     */     }
/*     */     if (paramLong <= 0L) {
/*     */       return false;
/*     */     }
/*     */     long l1 = paramTimeUnit.toNanos(paramLong);
/*     */     long l2 = System.nanoTime() + l1;
/*     */     while (true) {
/*     */       TimeUnit.NANOSECONDS.timedWait(this, l1);
/*     */       if (this.hasExited)
/*     */         return true; 
/*     */       l1 = l2 - System.nanoTime();
/*     */       if (l1 <= 0L)
/*     */         return this.hasExited; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized int exitValue() {
/*     */     if (!this.hasExited)
/*     */       throw new IllegalThreadStateException("process hasn't exited"); 
/*     */     return this.exitcode;
/*     */   }
/*     */   
/*     */   static {
/* 221 */     processReaperExecutor = AccessController.<Executor>doPrivileged(() -> {
/*     */           ThreadGroup threadGroup1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           for (threadGroup1 = Thread.currentThread().getThreadGroup(); threadGroup1.getParent() != null; threadGroup1 = threadGroup1.getParent());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           ThreadGroup threadGroup2 = threadGroup1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           ThreadFactory threadFactory = ();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return Executors.newCachedThreadPool(threadFactory);
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     init(); } private void destroy(boolean paramBoolean) { switch (platform) { case LINUX:
/*     */       case AIX:
/*     */       case BSD:
/*     */         synchronized (this) { if (!this.hasExited)
/*     */             destroyProcess(this.pid, paramBoolean);  }  try { this.stdin.close(); } catch (IOException iOException) {} try { this.stdout.close(); } catch (IOException iOException) {} try { this.stderr.close(); } catch (IOException iOException) {} return;
/*     */       case SOLARIS:
/*     */         synchronized (this) { if (!this.hasExited)
/*     */             destroyProcess(this.pid, paramBoolean);  try { this.stdin.close(); if (this.stdout_inner_stream != null)
/*     */               this.stdout_inner_stream.closeDeferred(this.stdout);  if (this.stderr instanceof DeferredCloseInputStream)
/*     */               ((DeferredCloseInputStream)this.stderr).closeDeferred(this.stderr);  } catch (IOException iOException) {} }  return; }  throw new AssertionError("Unsupported platform: " + platform); } public void destroy() { destroy(false); } public Process destroyForcibly() { destroy(true); return this; } public synchronized boolean isAlive() { return !this.hasExited; } private native int waitForProcessExit(int paramInt);
/*     */   private native int forkAndExec(int paramInt1, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt2, byte[] paramArrayOfbyte4, int paramInt3, byte[] paramArrayOfbyte5, int[] paramArrayOfint, boolean paramBoolean) throws IOException;
/*     */   private static native void destroyProcess(int paramInt, boolean paramBoolean);
/*     */   private static native void init();
/* 507 */   private static class ProcessPipeInputStream extends BufferedInputStream { private final Object closeLock = new Object();
/*     */     
/*     */     ProcessPipeInputStream(int param1Int) {
/* 510 */       super(new FileInputStream(UNIXProcess.newFileDescriptor(param1Int)));
/*     */     }
/*     */     
/*     */     private static byte[] drainInputStream(InputStream param1InputStream) throws IOException {
/* 514 */       int i = 0;
/*     */       
/* 516 */       byte[] arrayOfByte = null; int j;
/* 517 */       while ((j = param1InputStream.available()) > 0) {
/* 518 */         arrayOfByte = (arrayOfByte == null) ? new byte[j] : Arrays.copyOf(arrayOfByte, i + j);
/* 519 */         i += param1InputStream.read(arrayOfByte, i, j);
/*     */       } 
/* 521 */       return (arrayOfByte == null || i == arrayOfByte.length) ? arrayOfByte : Arrays.copyOf(arrayOfByte, i);
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized void processExited() {
/* 526 */       synchronized (this.closeLock) {
/*     */         try {
/* 528 */           InputStream inputStream = this.in;
/*     */           
/* 530 */           if (inputStream != null) {
/* 531 */             byte[] arrayOfByte = drainInputStream(inputStream);
/* 532 */             inputStream.close();
/* 533 */             this.in = (arrayOfByte == null) ? ProcessBuilder.NullInputStream.INSTANCE : new ByteArrayInputStream(arrayOfByte);
/*     */           }
/*     */         
/*     */         }
/* 537 */         catch (IOException iOException) {}
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 545 */       synchronized (this.closeLock) {
/* 546 */         super.close();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ProcessPipeOutputStream
/*     */     extends BufferedOutputStream
/*     */   {
/*     */     ProcessPipeOutputStream(int param1Int) {
/* 558 */       super(new FileOutputStream(UNIXProcess.newFileDescriptor(param1Int)));
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized void processExited() {
/* 563 */       OutputStream outputStream = this.out;
/* 564 */       if (outputStream != null) {
/*     */         try {
/* 566 */           outputStream.close();
/* 567 */         } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */         
/* 571 */         this.out = ProcessBuilder.NullOutputStream.INSTANCE;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DeferredCloseInputStream
/*     */     extends FileInputStream
/*     */   {
/*     */     private Object lock;
/*     */     
/*     */     private boolean closePending;
/*     */     
/*     */     private int useCount;
/*     */     
/*     */     private InputStream streamToClose;
/*     */     
/*     */     DeferredCloseInputStream(FileDescriptor param1FileDescriptor) {
/* 589 */       super(param1FileDescriptor);
/*     */ 
/*     */       
/* 592 */       this.lock = new Object();
/* 593 */       this.closePending = false;
/* 594 */       this.useCount = 0;
/*     */     }
/*     */     
/*     */     private void raise() {
/* 598 */       synchronized (this.lock) {
/* 599 */         this.useCount++;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void lower() throws IOException {
/* 604 */       synchronized (this.lock) {
/* 605 */         this.useCount--;
/* 606 */         if (this.useCount == 0 && this.closePending) {
/* 607 */           this.streamToClose.close();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void closeDeferred(InputStream param1InputStream) throws IOException {
/* 616 */       synchronized (this.lock) {
/* 617 */         if (this.useCount == 0) {
/* 618 */           param1InputStream.close();
/*     */         } else {
/* 620 */           this.closePending = true;
/* 621 */           this.streamToClose = param1InputStream;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 627 */       synchronized (this.lock) {
/* 628 */         this.useCount = 0;
/* 629 */         this.closePending = false;
/*     */       } 
/* 631 */       super.close();
/*     */     }
/*     */     
/*     */     public int read() throws IOException {
/* 635 */       raise();
/*     */       try {
/* 637 */         return super.read();
/*     */       } finally {
/* 639 */         lower();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 644 */       raise();
/*     */       try {
/* 646 */         return super.read(param1ArrayOfbyte);
/*     */       } finally {
/* 648 */         lower();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 653 */       raise();
/*     */       try {
/* 655 */         return super.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */       } finally {
/* 657 */         lower();
/*     */       } 
/*     */     }
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 662 */       raise();
/*     */       try {
/* 664 */         return super.skip(param1Long);
/*     */       } finally {
/* 666 */         lower();
/*     */       } 
/*     */     }
/*     */     
/*     */     public int available() throws IOException {
/* 671 */       raise();
/*     */       try {
/* 673 */         return super.available();
/*     */       } finally {
/* 675 */         lower();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DeferredCloseProcessPipeInputStream
/*     */     extends BufferedInputStream
/*     */   {
/* 704 */     private final Object closeLock = new Object();
/* 705 */     private int useCount = 0;
/*     */     private boolean closePending = false;
/*     */     
/*     */     DeferredCloseProcessPipeInputStream(int param1Int) {
/* 709 */       super(new FileInputStream(UNIXProcess.newFileDescriptor(param1Int)));
/*     */     }
/*     */ 
/*     */     
/*     */     private InputStream drainInputStream(InputStream param1InputStream) throws IOException {
/* 714 */       int j, i = 0;
/*     */       
/* 716 */       byte[] arrayOfByte = null;
/* 717 */       synchronized (this.closeLock) {
/* 718 */         if (this.buf == null)
/* 719 */           return null; 
/* 720 */         j = param1InputStream.available();
/*     */       } 
/* 722 */       while (j > 0) {
/* 723 */         arrayOfByte = (arrayOfByte == null) ? new byte[j] : Arrays.copyOf(arrayOfByte, i + j);
/* 724 */         synchronized (this.closeLock) {
/* 725 */           if (this.buf == null)
/* 726 */             return null; 
/* 727 */           i += param1InputStream.read(arrayOfByte, i, j);
/* 728 */           j = param1InputStream.available();
/*     */         } 
/*     */       } 
/* 731 */       return (arrayOfByte == null) ? ProcessBuilder.NullInputStream.INSTANCE : new ByteArrayInputStream((i == arrayOfByte.length) ? arrayOfByte : 
/*     */           
/* 733 */           Arrays.copyOf(arrayOfByte, i));
/*     */     }
/*     */ 
/*     */     
/*     */     synchronized void processExited() {
/*     */       try {
/* 739 */         InputStream inputStream = this.in;
/* 740 */         if (inputStream != null) {
/* 741 */           InputStream inputStream1 = drainInputStream(inputStream);
/* 742 */           inputStream.close();
/* 743 */           this.in = inputStream1;
/*     */         } 
/* 745 */       } catch (IOException iOException) {}
/*     */     }
/*     */     
/*     */     private void raise() {
/* 749 */       synchronized (this.closeLock) {
/* 750 */         this.useCount++;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void lower() throws IOException {
/* 755 */       synchronized (this.closeLock) {
/* 756 */         this.useCount--;
/* 757 */         if (this.useCount == 0 && this.closePending) {
/* 758 */           this.closePending = false;
/* 759 */           super.close();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 766 */       raise();
/*     */       try {
/* 768 */         return super.read();
/*     */       } finally {
/* 770 */         lower();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte) throws IOException {
/* 776 */       raise();
/*     */       try {
/* 778 */         return super.read(param1ArrayOfbyte);
/*     */       } finally {
/* 780 */         lower();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
/* 786 */       raise();
/*     */       try {
/* 788 */         return super.read(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */       } finally {
/* 790 */         lower();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long param1Long) throws IOException {
/* 796 */       raise();
/*     */       try {
/* 798 */         return super.skip(param1Long);
/*     */       } finally {
/* 800 */         lower();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int available() throws IOException {
/* 806 */       raise();
/*     */       try {
/* 808 */         return super.available();
/*     */       } finally {
/* 810 */         lower();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 818 */       synchronized (this.closeLock) {
/* 819 */         if (this.useCount == 0) {
/* 820 */           super.close();
/*     */         } else {
/*     */           
/* 823 */           this.closePending = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/UNIXProcess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */