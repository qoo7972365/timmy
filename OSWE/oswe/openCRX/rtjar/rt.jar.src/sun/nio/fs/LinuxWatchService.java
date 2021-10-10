/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.NotDirectoryException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardWatchEventKinds;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LinuxWatchService
/*     */   extends AbstractWatchService
/*     */ {
/*  50 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */   
/*     */   private final Poller poller;
/*     */ 
/*     */   
/*     */   LinuxWatchService(UnixFileSystem paramUnixFileSystem) throws IOException {
/*  57 */     int i = -1;
/*     */     try {
/*  59 */       i = inotifyInit();
/*  60 */     } catch (UnixException unixException) {
/*     */ 
/*     */       
/*  63 */       String str = (unixException.errno() == 24) ? "User limit of inotify instances reached or too many open files" : unixException.errorString();
/*  64 */       throw new IOException(str);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  69 */     int[] arrayOfInt = new int[2];
/*     */     try {
/*  71 */       configureBlocking(i, false);
/*  72 */       socketpair(arrayOfInt);
/*  73 */       configureBlocking(arrayOfInt[0], false);
/*  74 */     } catch (UnixException unixException) {
/*  75 */       UnixNativeDispatcher.close(i);
/*  76 */       throw new IOException(unixException.errorString());
/*     */     } 
/*     */     
/*  79 */     this.poller = new Poller(paramUnixFileSystem, this, i, arrayOfInt);
/*  80 */     this.poller.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WatchKey register(Path paramPath, WatchEvent.Kind<?>[] paramArrayOfKind, WatchEvent.Modifier... paramVarArgs) throws IOException {
/*  90 */     return this.poller.register(paramPath, paramArrayOfKind, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void implClose() throws IOException {
/*  96 */     this.poller.close();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LinuxWatchKey
/*     */     extends AbstractWatchKey
/*     */   {
/*     */     private final int ifd;
/*     */     
/*     */     private volatile int wd;
/*     */ 
/*     */     
/*     */     LinuxWatchKey(UnixPath param1UnixPath, LinuxWatchService param1LinuxWatchService, int param1Int1, int param1Int2) {
/* 109 */       super(param1UnixPath, param1LinuxWatchService);
/* 110 */       this.ifd = param1Int1;
/* 111 */       this.wd = param1Int2;
/*     */     }
/*     */     
/*     */     int descriptor() {
/* 115 */       return this.wd;
/*     */     }
/*     */     
/*     */     void invalidate(boolean param1Boolean) {
/* 119 */       if (param1Boolean) {
/*     */         try {
/* 121 */           LinuxWatchService.inotifyRmWatch(this.ifd, this.wd);
/* 122 */         } catch (UnixException unixException) {}
/*     */       }
/*     */ 
/*     */       
/* 126 */       this.wd = -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 131 */       return (this.wd != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void cancel() {
/* 136 */       if (isValid())
/*     */       {
/* 138 */         ((LinuxWatchService)watcher()).poller.cancel(this);
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
/*     */   private static class Poller
/*     */     extends AbstractPoller
/*     */   {
/* 155 */     private static final int SIZEOF_INOTIFY_EVENT = LinuxWatchService.eventSize();
/* 156 */     private static final int[] offsets = LinuxWatchService.eventOffsets();
/* 157 */     private static final int OFFSETOF_WD = offsets[0];
/* 158 */     private static final int OFFSETOF_MASK = offsets[1];
/* 159 */     private static final int OFFSETOF_LEN = offsets[3];
/* 160 */     private static final int OFFSETOF_NAME = offsets[4];
/*     */     
/*     */     private static final int IN_MODIFY = 2;
/*     */     
/*     */     private static final int IN_ATTRIB = 4;
/*     */     
/*     */     private static final int IN_MOVED_FROM = 64;
/*     */     
/*     */     private static final int IN_MOVED_TO = 128;
/*     */     
/*     */     private static final int IN_CREATE = 256;
/*     */     
/*     */     private static final int IN_DELETE = 512;
/*     */     
/*     */     private static final int IN_UNMOUNT = 8192;
/*     */     
/*     */     private static final int IN_Q_OVERFLOW = 16384;
/*     */     
/*     */     private static final int IN_IGNORED = 32768;
/*     */     
/*     */     private static final int BUFFER_SIZE = 8192;
/*     */     private final UnixFileSystem fs;
/*     */     private final LinuxWatchService watcher;
/*     */     private final int ifd;
/*     */     private final int[] socketpair;
/*     */     private final Map<Integer, LinuxWatchService.LinuxWatchKey> wdToKey;
/*     */     private final long address;
/*     */     
/*     */     Poller(UnixFileSystem param1UnixFileSystem, LinuxWatchService param1LinuxWatchService, int param1Int, int[] param1ArrayOfint) {
/* 189 */       this.fs = param1UnixFileSystem;
/* 190 */       this.watcher = param1LinuxWatchService;
/* 191 */       this.ifd = param1Int;
/* 192 */       this.socketpair = param1ArrayOfint;
/* 193 */       this.wdToKey = new HashMap<>();
/* 194 */       this.address = LinuxWatchService.unsafe.allocateMemory(8192L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void wakeup() throws IOException {
/*     */       try {
/* 201 */         UnixNativeDispatcher.write(this.socketpair[1], this.address, 1);
/* 202 */       } catch (UnixException unixException) {
/* 203 */         throw new IOException(unixException.errorString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object implRegister(Path param1Path, Set<? extends WatchEvent.Kind<?>> param1Set, WatchEvent.Modifier... param1VarArgs) {
/* 212 */       UnixPath unixPath = (UnixPath)param1Path;
/*     */       
/* 214 */       int i = 0;
/* 215 */       for (WatchEvent.Kind<Path> kind : param1Set) {
/* 216 */         if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
/* 217 */           i |= 0x180;
/*     */           continue;
/*     */         } 
/* 220 */         if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
/* 221 */           i |= 0x240;
/*     */           continue;
/*     */         } 
/* 224 */         if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
/* 225 */           i |= 0x6;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 231 */       if (param1VarArgs.length > 0) {
/* 232 */         WatchEvent.Modifier[] arrayOfModifier; int k; byte b; for (arrayOfModifier = param1VarArgs, k = arrayOfModifier.length, b = 0; b < k; ) { WatchEvent.Modifier modifier = arrayOfModifier[b];
/* 233 */           if (modifier == null)
/* 234 */             return new NullPointerException(); 
/* 235 */           if (modifier instanceof com.sun.nio.file.SensitivityWatchEventModifier) {
/*     */             b++; continue;
/* 237 */           }  return new UnsupportedOperationException("Modifier not supported"); }
/*     */       
/*     */       } 
/*     */ 
/*     */       
/* 242 */       UnixFileAttributes unixFileAttributes = null;
/*     */       try {
/* 244 */         unixFileAttributes = UnixFileAttributes.get(unixPath, true);
/* 245 */       } catch (UnixException unixException) {
/* 246 */         return unixException.asIOException(unixPath);
/*     */       } 
/* 248 */       if (!unixFileAttributes.isDirectory()) {
/* 249 */         return new NotDirectoryException(unixPath.getPathForExceptionMessage());
/*     */       }
/*     */ 
/*     */       
/* 253 */       int j = -1;
/*     */       
/*     */       try {
/* 256 */         NativeBuffer nativeBuffer = NativeBuffers.asNativeBuffer(unixPath.getByteArrayForSysCalls());
/*     */         try {
/* 258 */           j = LinuxWatchService.inotifyAddWatch(this.ifd, nativeBuffer.address(), i);
/*     */         } finally {
/* 260 */           nativeBuffer.release();
/*     */         } 
/* 262 */       } catch (UnixException unixException) {
/* 263 */         if (unixException.errno() == 28) {
/* 264 */           return new IOException("User limit of inotify watches reached");
/*     */         }
/* 266 */         return unixException.asIOException(unixPath);
/*     */       } 
/*     */ 
/*     */       
/* 270 */       LinuxWatchService.LinuxWatchKey linuxWatchKey = this.wdToKey.get(Integer.valueOf(j));
/* 271 */       if (linuxWatchKey == null) {
/* 272 */         linuxWatchKey = new LinuxWatchService.LinuxWatchKey(unixPath, this.watcher, this.ifd, j);
/* 273 */         this.wdToKey.put(Integer.valueOf(j), linuxWatchKey);
/*     */       } 
/* 275 */       return linuxWatchKey;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void implCancelKey(WatchKey param1WatchKey) {
/* 281 */       LinuxWatchService.LinuxWatchKey linuxWatchKey = (LinuxWatchService.LinuxWatchKey)param1WatchKey;
/* 282 */       if (linuxWatchKey.isValid()) {
/* 283 */         this.wdToKey.remove(Integer.valueOf(linuxWatchKey.descriptor()));
/* 284 */         linuxWatchKey.invalidate(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void implCloseAll() {
/* 292 */       for (Map.Entry<Integer, LinuxWatchService.LinuxWatchKey> entry : this.wdToKey.entrySet()) {
/* 293 */         ((LinuxWatchService.LinuxWatchKey)entry.getValue()).invalidate(true);
/*     */       }
/* 295 */       this.wdToKey.clear();
/*     */ 
/*     */       
/* 298 */       LinuxWatchService.unsafe.freeMemory(this.address);
/* 299 */       UnixNativeDispatcher.close(this.socketpair[0]);
/* 300 */       UnixNativeDispatcher.close(this.socketpair[1]);
/* 301 */       UnixNativeDispatcher.close(this.ifd);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/*     */         while (true) {
/*     */           byte b;
/* 314 */           int i = LinuxWatchService.poll(this.ifd, this.socketpair[0]);
/*     */ 
/*     */           
/*     */           try {
/* 318 */             b = UnixNativeDispatcher.read(this.ifd, this.address, 8192);
/* 319 */           } catch (UnixException unixException) {
/* 320 */             if (unixException.errno() != 11)
/* 321 */               throw unixException; 
/* 322 */             b = 0;
/*     */           } 
/*     */ 
/*     */           
/* 326 */           int j = 0;
/* 327 */           while (j < b) {
/* 328 */             long l = this.address + j;
/* 329 */             int k = LinuxWatchService.unsafe.getInt(l + OFFSETOF_WD);
/* 330 */             int m = LinuxWatchService.unsafe.getInt(l + OFFSETOF_MASK);
/* 331 */             int n = LinuxWatchService.unsafe.getInt(l + OFFSETOF_LEN);
/*     */ 
/*     */             
/* 334 */             UnixPath unixPath = null;
/* 335 */             if (n > 0) {
/* 336 */               int i1 = n;
/*     */ 
/*     */ 
/*     */               
/* 340 */               while (i1 > 0) {
/* 341 */                 long l1 = l + OFFSETOF_NAME + i1 - 1L;
/* 342 */                 if (LinuxWatchService.unsafe.getByte(l1) != 0)
/*     */                   break; 
/* 344 */                 i1--;
/*     */               } 
/* 346 */               if (i1 > 0) {
/* 347 */                 byte[] arrayOfByte = new byte[i1];
/* 348 */                 LinuxWatchService.unsafe.copyMemory(null, l + OFFSETOF_NAME, arrayOfByte, Unsafe.ARRAY_BYTE_BASE_OFFSET, i1);
/*     */                 
/* 350 */                 unixPath = new UnixPath(this.fs, arrayOfByte);
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 355 */             processEvent(k, m, unixPath);
/*     */             
/* 357 */             j += SIZEOF_INOTIFY_EVENT + n;
/*     */           } 
/*     */ 
/*     */           
/* 361 */           if (i > 1 || (i == 1 && b == 0))
/*     */             try {
/* 363 */               UnixNativeDispatcher.read(this.socketpair[0], this.address, 8192);
/* 364 */               boolean bool = processRequests();
/* 365 */               if (bool)
/*     */                 break; 
/* 367 */             } catch (UnixException unixException) {
/* 368 */               if (unixException.errno() != 11) {
/* 369 */                 throw unixException;
/*     */               }
/*     */             }  
/*     */         } 
/* 373 */       } catch (UnixException unixException) {
/* 374 */         unixException.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private WatchEvent.Kind<?> maskToEventKind(int param1Int) {
/* 383 */       if ((param1Int & 0x2) > 0)
/* 384 */         return StandardWatchEventKinds.ENTRY_MODIFY; 
/* 385 */       if ((param1Int & 0x4) > 0)
/* 386 */         return StandardWatchEventKinds.ENTRY_MODIFY; 
/* 387 */       if ((param1Int & 0x100) > 0)
/* 388 */         return StandardWatchEventKinds.ENTRY_CREATE; 
/* 389 */       if ((param1Int & 0x80) > 0)
/* 390 */         return StandardWatchEventKinds.ENTRY_CREATE; 
/* 391 */       if ((param1Int & 0x200) > 0)
/* 392 */         return StandardWatchEventKinds.ENTRY_DELETE; 
/* 393 */       if ((param1Int & 0x40) > 0)
/* 394 */         return StandardWatchEventKinds.ENTRY_DELETE; 
/* 395 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void processEvent(int param1Int1, int param1Int2, UnixPath param1UnixPath) {
/* 403 */       if ((param1Int2 & 0x4000) > 0) {
/* 404 */         for (Map.Entry<Integer, LinuxWatchService.LinuxWatchKey> entry : this.wdToKey.entrySet()) {
/* 405 */           ((LinuxWatchService.LinuxWatchKey)entry.getValue())
/* 406 */             .signalEvent(StandardWatchEventKinds.OVERFLOW, null);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 412 */       LinuxWatchService.LinuxWatchKey linuxWatchKey = this.wdToKey.get(Integer.valueOf(param1Int1));
/* 413 */       if (linuxWatchKey == null) {
/*     */         return;
/*     */       }
/*     */       
/* 417 */       if ((param1Int2 & 0x8000) > 0) {
/* 418 */         this.wdToKey.remove(Integer.valueOf(param1Int1));
/* 419 */         linuxWatchKey.invalidate(false);
/* 420 */         linuxWatchKey.signal();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 425 */       if (param1UnixPath == null) {
/*     */         return;
/*     */       }
/*     */       
/* 429 */       WatchEvent.Kind<?> kind = maskToEventKind(param1Int2);
/* 430 */       if (kind != null) {
/* 431 */         linuxWatchKey.signalEvent(kind, param1UnixPath);
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
/*     */ 
/*     */   
/*     */   static {
/* 460 */     AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/* 462 */             System.loadLibrary("nio");
/* 463 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static native int eventSize();
/*     */   
/*     */   private static native int[] eventOffsets();
/*     */   
/*     */   private static native int inotifyInit() throws UnixException;
/*     */   
/*     */   private static native int inotifyAddWatch(int paramInt1, long paramLong, int paramInt2) throws UnixException;
/*     */   
/*     */   private static native void inotifyRmWatch(int paramInt1, int paramInt2) throws UnixException;
/*     */   
/*     */   private static native void configureBlocking(int paramInt, boolean paramBoolean) throws UnixException;
/*     */   
/*     */   private static native void socketpair(int[] paramArrayOfint) throws UnixException;
/*     */   
/*     */   private static native int poll(int paramInt1, int paramInt2) throws UnixException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/LinuxWatchService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */