/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.nio.channels.AsynchronousFileChannel;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.util.Set;
/*     */ import sun.misc.JavaIOFileDescriptorAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.nio.ch.FileChannelImpl;
/*     */ import sun.nio.ch.SimpleAsynchronousFileChannelImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixChannelFactory
/*     */ {
/*  48 */   private static final JavaIOFileDescriptorAccess fdAccess = SharedSecrets.getJavaIOFileDescriptorAccess();
/*     */ 
/*     */   
/*     */   protected static class Flags
/*     */   {
/*     */     boolean read;
/*     */     
/*     */     boolean write;
/*     */     
/*     */     boolean append;
/*     */     
/*     */     boolean truncateExisting;
/*     */     
/*     */     boolean noFollowLinks;
/*     */     boolean create;
/*     */     boolean createNew;
/*     */     boolean deleteOnClose;
/*     */     boolean sync;
/*     */     boolean dsync;
/*     */     
/*     */     static Flags toFlags(Set<? extends OpenOption> param1Set) {
/*  69 */       Flags flags = new Flags();
/*  70 */       for (OpenOption openOption : param1Set) {
/*  71 */         if (openOption instanceof StandardOpenOption) {
/*  72 */           switch ((StandardOpenOption)openOption) { case READ:
/*  73 */               flags.read = true; continue;
/*  74 */             case WRITE: flags.write = true; continue;
/*  75 */             case APPEND: flags.append = true; continue;
/*  76 */             case TRUNCATE_EXISTING: flags.truncateExisting = true; continue;
/*  77 */             case CREATE: flags.create = true; continue;
/*  78 */             case CREATE_NEW: flags.createNew = true; continue;
/*  79 */             case DELETE_ON_CLOSE: flags.deleteOnClose = true; continue;
/*     */             case SPARSE: continue;
/*  81 */             case SYNC: flags.sync = true; continue;
/*  82 */             case DSYNC: flags.dsync = true; continue; }
/*  83 */            throw new UnsupportedOperationException();
/*     */         } 
/*     */ 
/*     */         
/*  87 */         if (openOption == LinkOption.NOFOLLOW_LINKS) {
/*  88 */           flags.noFollowLinks = true;
/*     */           continue;
/*     */         } 
/*  91 */         if (openOption == null)
/*  92 */           throw new NullPointerException(); 
/*  93 */         throw new UnsupportedOperationException(openOption + " not supported");
/*     */       } 
/*  95 */       return flags;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static FileChannel newFileChannel(int paramInt, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 104 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 105 */     fdAccess.set(fileDescriptor, paramInt);
/* 106 */     return FileChannelImpl.open(fileDescriptor, paramString, paramBoolean1, paramBoolean2, null);
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
/*     */   static FileChannel newFileChannel(int paramInt1, UnixPath paramUnixPath, String paramString, Set<? extends OpenOption> paramSet, int paramInt2) throws UnixException {
/* 119 */     Flags flags = Flags.toFlags(paramSet);
/*     */ 
/*     */     
/* 122 */     if (!flags.read && !flags.write) {
/* 123 */       if (flags.append) {
/* 124 */         flags.write = true;
/*     */       } else {
/* 126 */         flags.read = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 131 */     if (flags.read && flags.append)
/* 132 */       throw new IllegalArgumentException("READ + APPEND not allowed"); 
/* 133 */     if (flags.append && flags.truncateExisting) {
/* 134 */       throw new IllegalArgumentException("APPEND + TRUNCATE_EXISTING not allowed");
/*     */     }
/* 136 */     FileDescriptor fileDescriptor = open(paramInt1, paramUnixPath, paramString, flags, paramInt2);
/* 137 */     return FileChannelImpl.open(fileDescriptor, paramUnixPath.toString(), flags.read, flags.write, flags.append, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static FileChannel newFileChannel(UnixPath paramUnixPath, Set<? extends OpenOption> paramSet, int paramInt) throws UnixException {
/* 148 */     return newFileChannel(-1, paramUnixPath, null, paramSet, paramInt);
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
/*     */   static AsynchronousFileChannel newAsynchronousFileChannel(UnixPath paramUnixPath, Set<? extends OpenOption> paramSet, int paramInt, ThreadPool paramThreadPool) throws UnixException {
/* 160 */     Flags flags = Flags.toFlags(paramSet);
/*     */ 
/*     */     
/* 163 */     if (!flags.read && !flags.write) {
/* 164 */       flags.read = true;
/*     */     }
/*     */ 
/*     */     
/* 168 */     if (flags.append) {
/* 169 */       throw new UnsupportedOperationException("APPEND not allowed");
/*     */     }
/*     */     
/* 172 */     FileDescriptor fileDescriptor = open(-1, paramUnixPath, null, flags, paramInt);
/* 173 */     return SimpleAsynchronousFileChannelImpl.open(fileDescriptor, flags.read, flags.write, paramThreadPool);
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
/*     */   protected static FileDescriptor open(int paramInt1, UnixPath paramUnixPath, String paramString, Flags paramFlags, int paramInt2) throws UnixException {
/*     */     int i, j;
/* 189 */     if (paramFlags.read && paramFlags.write) {
/* 190 */       i = 2;
/*     */     } else {
/* 192 */       i = paramFlags.write ? 1 : 0;
/*     */     } 
/* 194 */     if (paramFlags.write) {
/* 195 */       if (paramFlags.truncateExisting)
/* 196 */         i |= 0x200; 
/* 197 */       if (paramFlags.append) {
/* 198 */         i |= 0x400;
/*     */       }
/*     */       
/* 201 */       if (paramFlags.createNew) {
/* 202 */         byte[] arrayOfByte = paramUnixPath.asByteArray();
/*     */ 
/*     */         
/* 205 */         if (arrayOfByte[arrayOfByte.length - 1] == 46 && (arrayOfByte.length == 1 || arrayOfByte[arrayOfByte.length - 2] == 47))
/*     */         {
/*     */ 
/*     */           
/* 209 */           throw new UnixException(17);
/*     */         }
/* 211 */         i |= 0xC0;
/*     */       }
/* 213 */       else if (paramFlags.create) {
/* 214 */         i |= 0x40;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 219 */     boolean bool = true;
/* 220 */     if (!paramFlags.createNew && (paramFlags.noFollowLinks || paramFlags.deleteOnClose)) {
/* 221 */       if (paramFlags.deleteOnClose);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       bool = false;
/* 231 */       i |= 0x20000;
/*     */     } 
/*     */     
/* 234 */     if (paramFlags.dsync)
/* 235 */       i |= 0x1000; 
/* 236 */     if (paramFlags.sync) {
/* 237 */       i |= 0x101000;
/*     */     }
/*     */     
/* 240 */     SecurityManager securityManager = System.getSecurityManager();
/* 241 */     if (securityManager != null) {
/* 242 */       if (paramString == null)
/* 243 */         paramString = paramUnixPath.getPathForPermissionCheck(); 
/* 244 */       if (paramFlags.read)
/* 245 */         securityManager.checkRead(paramString); 
/* 246 */       if (paramFlags.write)
/* 247 */         securityManager.checkWrite(paramString); 
/* 248 */       if (paramFlags.deleteOnClose) {
/* 249 */         securityManager.checkDelete(paramString);
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 254 */       if (paramInt1 >= 0) {
/* 255 */         j = UnixNativeDispatcher.openat(paramInt1, paramUnixPath.asByteArray(), i, paramInt2);
/*     */       } else {
/* 257 */         j = UnixNativeDispatcher.open(paramUnixPath, i, paramInt2);
/*     */       } 
/* 259 */     } catch (UnixException unixException) {
/*     */       
/* 261 */       if (paramFlags.createNew && unixException.errno() == 21) {
/* 262 */         unixException.setError(17);
/*     */       }
/*     */ 
/*     */       
/* 266 */       if (!bool && unixException.errno() == 40) {
/* 267 */         unixException = new UnixException(unixException.getMessage() + " (NOFOLLOW_LINKS specified)");
/*     */       }
/*     */       
/* 270 */       throw unixException;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     if (paramFlags.deleteOnClose) {
/*     */       try {
/* 278 */         if (paramInt1 >= 0) {
/* 279 */           UnixNativeDispatcher.unlinkat(paramInt1, paramUnixPath.asByteArray(), 0);
/*     */         } else {
/* 281 */           UnixNativeDispatcher.unlink(paramUnixPath);
/*     */         } 
/* 283 */       } catch (UnixException unixException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     FileDescriptor fileDescriptor = new FileDescriptor();
/* 290 */     fdAccess.set(fileDescriptor, j);
/* 291 */     return fileDescriptor;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixChannelFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */