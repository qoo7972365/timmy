/*     */ package sun.nio.fs;
/*     */ 
/*     */ import com.sun.nio.file.ExtendedCopyOption;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.AtomicMoveNotSupportedException;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.DirectoryNotEmptyException;
/*     */ import java.nio.file.FileAlreadyExistsException;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.LinkPermission;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.ExecutionException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnixCopyFile
/*     */ {
/*     */   private static class Flags
/*     */   {
/*     */     boolean replaceExisting;
/*     */     boolean atomicMove;
/*     */     boolean followLinks;
/*     */     boolean interruptible;
/*     */     boolean copyBasicAttributes;
/*     */     boolean copyPosixAttributes;
/*     */     boolean copyNonPosixAttributes;
/*     */     boolean failIfUnableToCopyBasic;
/*     */     boolean failIfUnableToCopyPosix;
/*     */     boolean failIfUnableToCopyNonPosix;
/*     */     
/*     */     static Flags fromCopyOptions(CopyOption... param1VarArgs) {
/*  65 */       Flags flags = new Flags();
/*  66 */       flags.followLinks = true;
/*  67 */       for (CopyOption copyOption : param1VarArgs) {
/*  68 */         if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
/*  69 */           flags.replaceExisting = true;
/*     */         
/*     */         }
/*  72 */         else if (copyOption == LinkOption.NOFOLLOW_LINKS) {
/*  73 */           flags.followLinks = false;
/*     */         
/*     */         }
/*  76 */         else if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
/*     */ 
/*     */           
/*  79 */           flags.copyBasicAttributes = true;
/*  80 */           flags.copyPosixAttributes = true;
/*  81 */           flags.copyNonPosixAttributes = true;
/*  82 */           flags.failIfUnableToCopyBasic = true;
/*     */         
/*     */         }
/*  85 */         else if (copyOption == ExtendedCopyOption.INTERRUPTIBLE) {
/*  86 */           flags.interruptible = true;
/*     */         } else {
/*     */           
/*  89 */           if (copyOption == null)
/*  90 */             throw new NullPointerException(); 
/*  91 */           throw new UnsupportedOperationException("Unsupported copy option");
/*     */         } 
/*  93 */       }  return flags;
/*     */     }
/*     */     
/*     */     static Flags fromMoveOptions(CopyOption... param1VarArgs) {
/*  97 */       Flags flags = new Flags();
/*  98 */       for (CopyOption copyOption : param1VarArgs) {
/*  99 */         if (copyOption == StandardCopyOption.ATOMIC_MOVE) {
/* 100 */           flags.atomicMove = true;
/*     */         
/*     */         }
/* 103 */         else if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
/* 104 */           flags.replaceExisting = true;
/*     */         
/*     */         }
/* 107 */         else if (copyOption != LinkOption.NOFOLLOW_LINKS) {
/*     */ 
/*     */ 
/*     */           
/* 111 */           if (copyOption == null)
/* 112 */             throw new NullPointerException(); 
/* 113 */           throw new UnsupportedOperationException("Unsupported copy option");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 118 */       flags.copyBasicAttributes = true;
/* 119 */       flags.copyPosixAttributes = true;
/* 120 */       flags.copyNonPosixAttributes = true;
/* 121 */       flags.failIfUnableToCopyBasic = true;
/* 122 */       return flags;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void copyDirectory(UnixPath paramUnixPath1, UnixFileAttributes paramUnixFileAttributes, UnixPath paramUnixPath2, Flags paramFlags) throws IOException {
/*     */     try {
/* 134 */       UnixNativeDispatcher.mkdir(paramUnixPath2, paramUnixFileAttributes.mode());
/* 135 */     } catch (UnixException unixException) {
/* 136 */       unixException.rethrowAsIOException(paramUnixPath2);
/*     */     } 
/*     */ 
/*     */     
/* 140 */     if (!paramFlags.copyBasicAttributes && !paramFlags.copyPosixAttributes && !paramFlags.copyNonPosixAttributes) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     int i = -1;
/*     */     try {
/* 148 */       i = UnixNativeDispatcher.open(paramUnixPath2, 0, 0);
/* 149 */     } catch (UnixException unixException) {
/*     */       
/* 151 */       if (paramFlags.copyNonPosixAttributes && paramFlags.failIfUnableToCopyNonPosix) { 
/* 152 */         try { UnixNativeDispatcher.rmdir(paramUnixPath2); } catch (UnixException unixException1) {}
/* 153 */         unixException.rethrowAsIOException(paramUnixPath2); }
/*     */     
/*     */     } 
/*     */     
/* 157 */     boolean bool = false;
/*     */     
/*     */     try {
/* 160 */       if (paramFlags.copyPosixAttributes) {
/*     */         try {
/* 162 */           if (i >= 0) {
/* 163 */             UnixNativeDispatcher.fchown(i, paramUnixFileAttributes.uid(), paramUnixFileAttributes.gid());
/* 164 */             UnixNativeDispatcher.fchmod(i, paramUnixFileAttributes.mode());
/*     */           } else {
/* 166 */             UnixNativeDispatcher.chown(paramUnixPath2, paramUnixFileAttributes.uid(), paramUnixFileAttributes.gid());
/* 167 */             UnixNativeDispatcher.chmod(paramUnixPath2, paramUnixFileAttributes.mode());
/*     */           } 
/* 169 */         } catch (UnixException unixException) {
/*     */           
/* 171 */           if (paramFlags.failIfUnableToCopyPosix) {
/* 172 */             unixException.rethrowAsIOException(paramUnixPath2);
/*     */           }
/*     */         } 
/*     */       }
/* 176 */       if (paramFlags.copyNonPosixAttributes && i >= 0) {
/* 177 */         int j = -1;
/*     */         try {
/* 179 */           j = UnixNativeDispatcher.open(paramUnixPath1, 0, 0);
/* 180 */         } catch (UnixException unixException) {
/* 181 */           if (paramFlags.failIfUnableToCopyNonPosix)
/* 182 */             unixException.rethrowAsIOException(paramUnixPath1); 
/*     */         } 
/* 184 */         if (j >= 0) {
/* 185 */           paramUnixPath1.getFileSystem().copyNonPosixAttributes(j, i);
/* 186 */           UnixNativeDispatcher.close(j);
/*     */         } 
/*     */       } 
/*     */       
/* 190 */       if (paramFlags.copyBasicAttributes)
/*     */         try {
/* 192 */           if (i >= 0 && UnixNativeDispatcher.futimesSupported()) {
/* 193 */             UnixNativeDispatcher.futimes(i, paramUnixFileAttributes
/* 194 */                 .lastAccessTime().to(TimeUnit.MICROSECONDS), paramUnixFileAttributes
/* 195 */                 .lastModifiedTime().to(TimeUnit.MICROSECONDS));
/*     */           } else {
/* 197 */             UnixNativeDispatcher.utimes(paramUnixPath2, paramUnixFileAttributes
/* 198 */                 .lastAccessTime().to(TimeUnit.MICROSECONDS), paramUnixFileAttributes
/* 199 */                 .lastModifiedTime().to(TimeUnit.MICROSECONDS));
/*     */           } 
/* 201 */         } catch (UnixException unixException) {
/*     */           
/* 203 */           if (paramFlags.failIfUnableToCopyBasic) {
/* 204 */             unixException.rethrowAsIOException(paramUnixPath2);
/*     */           }
/*     */         }  
/* 207 */       bool = true;
/*     */     } finally {
/* 209 */       if (i >= 0)
/* 210 */         UnixNativeDispatcher.close(i); 
/* 211 */       if (!bool) {
/*     */         
/* 213 */         try { UnixNativeDispatcher.rmdir(paramUnixPath2); } catch (UnixException unixException) {}
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
/*     */   private static void copyFile(UnixPath paramUnixPath1, UnixFileAttributes paramUnixFileAttributes, UnixPath paramUnixPath2, Flags paramFlags, long paramLong) throws IOException {
/* 226 */     int i = -1;
/*     */     try {
/* 228 */       i = UnixNativeDispatcher.open(paramUnixPath1, 0, 0);
/* 229 */     } catch (UnixException unixException) {
/* 230 */       unixException.rethrowAsIOException(paramUnixPath1);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 235 */       int j = -1;
/*     */       try {
/* 237 */         j = UnixNativeDispatcher.open(paramUnixPath2, 193, paramUnixFileAttributes
/*     */ 
/*     */ 
/*     */             
/* 241 */             .mode());
/* 242 */       } catch (UnixException unixException) {
/* 243 */         unixException.rethrowAsIOException(paramUnixPath2);
/*     */       } 
/*     */ 
/*     */       
/* 247 */       boolean bool = false;
/*     */       
/*     */       try {
/*     */         try {
/* 251 */           transfer(j, i, paramLong);
/* 252 */         } catch (UnixException unixException) {
/* 253 */           unixException.rethrowAsIOException(paramUnixPath1, paramUnixPath2);
/*     */         } 
/*     */         
/* 256 */         if (paramFlags.copyPosixAttributes) {
/*     */           try {
/* 258 */             UnixNativeDispatcher.fchown(j, paramUnixFileAttributes.uid(), paramUnixFileAttributes.gid());
/* 259 */             UnixNativeDispatcher.fchmod(j, paramUnixFileAttributes.mode());
/* 260 */           } catch (UnixException unixException) {
/* 261 */             if (paramFlags.failIfUnableToCopyPosix) {
/* 262 */               unixException.rethrowAsIOException(paramUnixPath2);
/*     */             }
/*     */           } 
/*     */         }
/* 266 */         if (paramFlags.copyNonPosixAttributes) {
/* 267 */           paramUnixPath1.getFileSystem().copyNonPosixAttributes(i, j);
/*     */         }
/*     */         
/* 270 */         if (paramFlags.copyBasicAttributes)
/*     */           try {
/* 272 */             if (UnixNativeDispatcher.futimesSupported()) {
/* 273 */               UnixNativeDispatcher.futimes(j, paramUnixFileAttributes
/* 274 */                   .lastAccessTime().to(TimeUnit.MICROSECONDS), paramUnixFileAttributes
/* 275 */                   .lastModifiedTime().to(TimeUnit.MICROSECONDS));
/*     */             } else {
/* 277 */               UnixNativeDispatcher.utimes(paramUnixPath2, paramUnixFileAttributes
/* 278 */                   .lastAccessTime().to(TimeUnit.MICROSECONDS), paramUnixFileAttributes
/* 279 */                   .lastModifiedTime().to(TimeUnit.MICROSECONDS));
/*     */             } 
/* 281 */           } catch (UnixException unixException) {
/* 282 */             if (paramFlags.failIfUnableToCopyBasic) {
/* 283 */               unixException.rethrowAsIOException(paramUnixPath2);
/*     */             }
/*     */           }  
/* 286 */         bool = true;
/*     */       } finally {
/* 288 */         UnixNativeDispatcher.close(j);
/*     */ 
/*     */         
/* 291 */         if (!bool) {
/*     */           try {
/* 293 */             UnixNativeDispatcher.unlink(paramUnixPath2);
/* 294 */           } catch (UnixException unixException) {}
/*     */         }
/*     */       } 
/*     */     } finally {
/* 298 */       UnixNativeDispatcher.close(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void copyLink(UnixPath paramUnixPath1, UnixFileAttributes paramUnixFileAttributes, UnixPath paramUnixPath2, Flags paramFlags) throws IOException {
/* 309 */     byte[] arrayOfByte = null;
/*     */     try {
/* 311 */       arrayOfByte = UnixNativeDispatcher.readlink(paramUnixPath1);
/* 312 */     } catch (UnixException unixException) {
/* 313 */       unixException.rethrowAsIOException(paramUnixPath1);
/*     */     } 
/*     */     try {
/* 316 */       UnixNativeDispatcher.symlink(arrayOfByte, paramUnixPath2);
/*     */       
/* 318 */       if (paramFlags.copyPosixAttributes) {
/*     */         try {
/* 320 */           UnixNativeDispatcher.lchown(paramUnixPath2, paramUnixFileAttributes.uid(), paramUnixFileAttributes.gid());
/* 321 */         } catch (UnixException unixException) {}
/*     */       
/*     */       }
/*     */     }
/* 325 */     catch (UnixException unixException) {
/* 326 */       unixException.rethrowAsIOException(paramUnixPath2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void copySpecial(UnixPath paramUnixPath1, UnixFileAttributes paramUnixFileAttributes, UnixPath paramUnixPath2, Flags paramFlags) throws IOException {
/*     */     try {
/* 338 */       UnixNativeDispatcher.mknod(paramUnixPath2, paramUnixFileAttributes.mode(), paramUnixFileAttributes.rdev());
/* 339 */     } catch (UnixException unixException) {
/* 340 */       unixException.rethrowAsIOException(paramUnixPath2);
/*     */     } 
/* 342 */     boolean bool = false;
/*     */     try {
/* 344 */       if (paramFlags.copyPosixAttributes)
/*     */         try {
/* 346 */           UnixNativeDispatcher.chown(paramUnixPath2, paramUnixFileAttributes.uid(), paramUnixFileAttributes.gid());
/* 347 */           UnixNativeDispatcher.chmod(paramUnixPath2, paramUnixFileAttributes.mode());
/* 348 */         } catch (UnixException unixException) {
/* 349 */           if (paramFlags.failIfUnableToCopyPosix) {
/* 350 */             unixException.rethrowAsIOException(paramUnixPath2);
/*     */           }
/*     */         }  
/* 353 */       if (paramFlags.copyBasicAttributes)
/*     */         try {
/* 355 */           UnixNativeDispatcher.utimes(paramUnixPath2, paramUnixFileAttributes
/* 356 */               .lastAccessTime().to(TimeUnit.MICROSECONDS), paramUnixFileAttributes
/* 357 */               .lastModifiedTime().to(TimeUnit.MICROSECONDS));
/* 358 */         } catch (UnixException unixException) {
/* 359 */           if (paramFlags.failIfUnableToCopyBasic) {
/* 360 */             unixException.rethrowAsIOException(paramUnixPath2);
/*     */           }
/*     */         }  
/* 363 */       bool = true;
/*     */     } finally {
/* 365 */       if (!bool) {
/* 366 */         try { UnixNativeDispatcher.unlink(paramUnixPath2); } catch (UnixException unixException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void move(UnixPath paramUnixPath1, UnixPath paramUnixPath2, CopyOption... paramVarArgs) throws IOException {
/* 376 */     SecurityManager securityManager = System.getSecurityManager();
/* 377 */     if (securityManager != null) {
/* 378 */       paramUnixPath1.checkWrite();
/* 379 */       paramUnixPath2.checkWrite();
/*     */     } 
/*     */ 
/*     */     
/* 383 */     Flags flags = Flags.fromMoveOptions(paramVarArgs);
/*     */ 
/*     */     
/* 386 */     if (flags.atomicMove) {
/*     */       try {
/* 388 */         UnixNativeDispatcher.rename(paramUnixPath1, paramUnixPath2);
/* 389 */       } catch (UnixException unixException) {
/* 390 */         if (unixException.errno() == 18) {
/* 391 */           throw new AtomicMoveNotSupportedException(paramUnixPath1
/* 392 */               .getPathForExceptionMessage(), paramUnixPath2
/* 393 */               .getPathForExceptionMessage(), unixException
/* 394 */               .errorString());
/*     */         }
/* 396 */         unixException.rethrowAsIOException(paramUnixPath1, paramUnixPath2);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 402 */     UnixFileAttributes unixFileAttributes1 = null;
/* 403 */     UnixFileAttributes unixFileAttributes2 = null;
/*     */ 
/*     */     
/*     */     try {
/* 407 */       unixFileAttributes1 = UnixFileAttributes.get(paramUnixPath1, false);
/* 408 */     } catch (UnixException unixException) {
/* 409 */       unixException.rethrowAsIOException(paramUnixPath1);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 414 */       unixFileAttributes2 = UnixFileAttributes.get(paramUnixPath2, false);
/* 415 */     } catch (UnixException unixException) {}
/*     */ 
/*     */     
/* 418 */     boolean bool = (unixFileAttributes2 != null) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 424 */     if (bool) {
/* 425 */       if (unixFileAttributes1.isSameFile(unixFileAttributes2))
/*     */         return; 
/* 427 */       if (!flags.replaceExisting) {
/* 428 */         throw new FileAlreadyExistsException(paramUnixPath2
/* 429 */             .getPathForExceptionMessage());
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 434 */         if (unixFileAttributes2.isDirectory()) {
/* 435 */           UnixNativeDispatcher.rmdir(paramUnixPath2);
/*     */         } else {
/* 437 */           UnixNativeDispatcher.unlink(paramUnixPath2);
/*     */         } 
/* 439 */       } catch (UnixException unixException) {
/*     */         
/* 441 */         if (unixFileAttributes2.isDirectory() && (unixException
/* 442 */           .errno() == 17 || unixException.errno() == 39))
/*     */         {
/* 444 */           throw new DirectoryNotEmptyException(paramUnixPath2
/* 445 */               .getPathForExceptionMessage());
/*     */         }
/* 447 */         unixException.rethrowAsIOException(paramUnixPath2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 453 */       UnixNativeDispatcher.rename(paramUnixPath1, paramUnixPath2);
/*     */       return;
/* 455 */     } catch (UnixException unixException) {
/* 456 */       if (unixException.errno() != 18 && unixException.errno() != 21) {
/* 457 */         unixException.rethrowAsIOException(paramUnixPath1, paramUnixPath2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 462 */       if (unixFileAttributes1.isDirectory()) {
/* 463 */         copyDirectory(paramUnixPath1, unixFileAttributes1, paramUnixPath2, flags);
/*     */       }
/* 465 */       else if (unixFileAttributes1.isSymbolicLink()) {
/* 466 */         copyLink(paramUnixPath1, unixFileAttributes1, paramUnixPath2, flags);
/*     */       }
/* 468 */       else if (unixFileAttributes1.isDevice()) {
/* 469 */         copySpecial(paramUnixPath1, unixFileAttributes1, paramUnixPath2, flags);
/*     */       } else {
/* 471 */         copyFile(paramUnixPath1, unixFileAttributes1, paramUnixPath2, flags, 0L);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 478 */         if (unixFileAttributes1.isDirectory()) {
/* 479 */           UnixNativeDispatcher.rmdir(paramUnixPath1);
/*     */         } else {
/* 481 */           UnixNativeDispatcher.unlink(paramUnixPath1);
/*     */         } 
/* 483 */       } catch (UnixException unixException1) {
/*     */ 
/*     */         
/*     */         try {
/* 487 */           if (unixFileAttributes1.isDirectory()) {
/* 488 */             UnixNativeDispatcher.rmdir(paramUnixPath2);
/*     */           } else {
/* 490 */             UnixNativeDispatcher.unlink(paramUnixPath2);
/*     */           } 
/* 492 */         } catch (UnixException unixException2) {}
/*     */         
/* 494 */         if (unixFileAttributes1.isDirectory() && (unixException1
/* 495 */           .errno() == 17 || unixException1.errno() == 39))
/*     */         {
/* 497 */           throw new DirectoryNotEmptyException(paramUnixPath1
/* 498 */               .getPathForExceptionMessage());
/*     */         }
/* 500 */         unixException1.rethrowAsIOException(paramUnixPath1);
/*     */       } 
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void copy(final UnixPath source, final UnixPath target, CopyOption... paramVarArgs) throws IOException {
/* 510 */     SecurityManager securityManager = System.getSecurityManager();
/* 511 */     if (securityManager != null) {
/* 512 */       source.checkRead();
/* 513 */       target.checkWrite();
/*     */     } 
/*     */ 
/*     */     
/* 517 */     final Flags flags = Flags.fromCopyOptions(paramVarArgs);
/*     */     
/* 519 */     UnixFileAttributes unixFileAttributes1 = null;
/* 520 */     UnixFileAttributes unixFileAttributes2 = null;
/*     */ 
/*     */     
/*     */     try {
/* 524 */       unixFileAttributes1 = UnixFileAttributes.get(source, flags.followLinks);
/* 525 */     } catch (UnixException unixException) {
/* 526 */       unixException.rethrowAsIOException(source);
/*     */     } 
/*     */ 
/*     */     
/* 530 */     if (securityManager != null && unixFileAttributes1.isSymbolicLink()) {
/* 531 */       securityManager.checkPermission(new LinkPermission("symbolic"));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 536 */       unixFileAttributes2 = UnixFileAttributes.get(target, false);
/* 537 */     } catch (UnixException unixException) {}
/*     */ 
/*     */     
/* 540 */     boolean bool = (unixFileAttributes2 != null) ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 546 */     if (bool) {
/* 547 */       if (unixFileAttributes1.isSameFile(unixFileAttributes2))
/*     */         return; 
/* 549 */       if (!flags.replaceExisting)
/* 550 */         throw new FileAlreadyExistsException(target
/* 551 */             .getPathForExceptionMessage()); 
/*     */       try {
/* 553 */         if (unixFileAttributes2.isDirectory()) {
/* 554 */           UnixNativeDispatcher.rmdir(target);
/*     */         } else {
/* 556 */           UnixNativeDispatcher.unlink(target);
/*     */         } 
/* 558 */       } catch (UnixException unixException) {
/*     */         
/* 560 */         if (unixFileAttributes2.isDirectory() && (unixException
/* 561 */           .errno() == 17 || unixException.errno() == 39))
/*     */         {
/* 563 */           throw new DirectoryNotEmptyException(target
/* 564 */               .getPathForExceptionMessage());
/*     */         }
/* 566 */         unixException.rethrowAsIOException(target);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 571 */     if (unixFileAttributes1.isDirectory()) {
/* 572 */       copyDirectory(source, unixFileAttributes1, target, flags);
/*     */       return;
/*     */     } 
/* 575 */     if (unixFileAttributes1.isSymbolicLink()) {
/* 576 */       copyLink(source, unixFileAttributes1, target, flags);
/*     */       return;
/*     */     } 
/* 579 */     if (!flags.interruptible) {
/*     */       
/* 581 */       copyFile(source, unixFileAttributes1, target, flags, 0L);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 586 */     final UnixFileAttributes attrsToCopy = unixFileAttributes1;
/* 587 */     Cancellable cancellable = new Cancellable() {
/*     */         public void implRun() throws IOException {
/* 589 */           UnixCopyFile.copyFile(source, attrsToCopy, target, flags, 
/* 590 */               addressToPollForCancel());
/*     */         }
/*     */       };
/*     */     try {
/* 594 */       Cancellable.runInterruptibly(cancellable);
/* 595 */     } catch (ExecutionException executionException) {
/* 596 */       Throwable throwable = executionException.getCause();
/* 597 */       if (throwable instanceof IOException)
/* 598 */         throw (IOException)throwable; 
/* 599 */       throw new IOException(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 609 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 612 */             System.loadLibrary("nio");
/* 613 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static native void transfer(int paramInt1, int paramInt2, long paramLong) throws UnixException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixCopyFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */