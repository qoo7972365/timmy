/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import sun.nio.fs.BasicFileAttributesHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FileTreeWalker
/*     */   implements Closeable
/*     */ {
/*     */   private final boolean followLinks;
/*     */   private final LinkOption[] linkOptions;
/*     */   private final int maxDepth;
/*  61 */   private final ArrayDeque<DirectoryNode> stack = new ArrayDeque<>();
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */   
/*     */   private static class DirectoryNode
/*     */   {
/*     */     private final Path dir;
/*     */     private final Object key;
/*     */     private final DirectoryStream<Path> stream;
/*     */     private final Iterator<Path> iterator;
/*     */     private boolean skipped;
/*     */     
/*     */     DirectoryNode(Path param1Path, Object param1Object, DirectoryStream<Path> param1DirectoryStream) {
/*  75 */       this.dir = param1Path;
/*  76 */       this.key = param1Object;
/*  77 */       this.stream = param1DirectoryStream;
/*  78 */       this.iterator = param1DirectoryStream.iterator();
/*     */     }
/*     */     
/*     */     Path directory() {
/*  82 */       return this.dir;
/*     */     }
/*     */     
/*     */     Object key() {
/*  86 */       return this.key;
/*     */     }
/*     */     
/*     */     DirectoryStream<Path> stream() {
/*  90 */       return this.stream;
/*     */     }
/*     */     
/*     */     Iterator<Path> iterator() {
/*  94 */       return this.iterator;
/*     */     }
/*     */     
/*     */     void skip() {
/*  98 */       this.skipped = true;
/*     */     }
/*     */     
/*     */     boolean skipped() {
/* 102 */       return this.skipped;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum EventType
/*     */   {
/* 113 */     START_DIRECTORY,
/*     */ 
/*     */ 
/*     */     
/* 117 */     END_DIRECTORY,
/*     */ 
/*     */ 
/*     */     
/* 121 */     ENTRY;
/*     */   }
/*     */ 
/*     */   
/*     */   static class Event
/*     */   {
/*     */     private final FileTreeWalker.EventType type;
/*     */     
/*     */     private final Path file;
/*     */     private final BasicFileAttributes attrs;
/*     */     private final IOException ioe;
/*     */     
/*     */     private Event(FileTreeWalker.EventType param1EventType, Path param1Path, BasicFileAttributes param1BasicFileAttributes, IOException param1IOException) {
/* 134 */       this.type = param1EventType;
/* 135 */       this.file = param1Path;
/* 136 */       this.attrs = param1BasicFileAttributes;
/* 137 */       this.ioe = param1IOException;
/*     */     }
/*     */     
/*     */     Event(FileTreeWalker.EventType param1EventType, Path param1Path, BasicFileAttributes param1BasicFileAttributes) {
/* 141 */       this(param1EventType, param1Path, param1BasicFileAttributes, null);
/*     */     }
/*     */     
/*     */     Event(FileTreeWalker.EventType param1EventType, Path param1Path, IOException param1IOException) {
/* 145 */       this(param1EventType, param1Path, null, param1IOException);
/*     */     }
/*     */     
/*     */     FileTreeWalker.EventType type() {
/* 149 */       return this.type;
/*     */     }
/*     */     
/*     */     Path file() {
/* 153 */       return this.file;
/*     */     }
/*     */     
/*     */     BasicFileAttributes attributes() {
/* 157 */       return this.attrs;
/*     */     }
/*     */     
/*     */     IOException ioeException() {
/* 161 */       return this.ioe;
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
/*     */   FileTreeWalker(Collection<FileVisitOption> paramCollection, int paramInt) {
/* 178 */     boolean bool = false;
/* 179 */     for (FileVisitOption fileVisitOption : paramCollection) {
/*     */       
/* 181 */       switch (fileVisitOption) { case FOLLOW_LINKS:
/* 182 */           bool = true; continue; }
/*     */       
/* 184 */       throw new AssertionError("Should not get here");
/*     */     } 
/*     */     
/* 187 */     if (paramInt < 0) {
/* 188 */       throw new IllegalArgumentException("'maxDepth' is negative");
/*     */     }
/* 190 */     this.followLinks = bool;
/* 191 */     (new LinkOption[1])[0] = LinkOption.NOFOLLOW_LINKS; this.linkOptions = bool ? new LinkOption[0] : new LinkOption[1];
/*     */     
/* 193 */     this.maxDepth = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BasicFileAttributes getAttributes(Path paramPath, boolean paramBoolean) throws IOException {
/*     */     BasicFileAttributes basicFileAttributes;
/* 205 */     if (paramBoolean && paramPath instanceof BasicFileAttributesHolder)
/*     */     {
/* 207 */       if (System.getSecurityManager() == null) {
/*     */         
/* 209 */         basicFileAttributes = ((BasicFileAttributesHolder)paramPath).get();
/* 210 */         if (basicFileAttributes != null && (!this.followLinks || !basicFileAttributes.isSymbolicLink())) {
/* 211 */           return basicFileAttributes;
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 219 */       basicFileAttributes = Files.readAttributes(paramPath, (Class)BasicFileAttributes.class, this.linkOptions);
/* 220 */     } catch (IOException iOException) {
/* 221 */       if (!this.followLinks) {
/* 222 */         throw iOException;
/*     */       }
/*     */       
/* 225 */       basicFileAttributes = Files.readAttributes(paramPath, (Class)BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/*     */     } 
/*     */ 
/*     */     
/* 229 */     return basicFileAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wouldLoop(Path paramPath, Object paramObject) {
/* 239 */     for (DirectoryNode directoryNode : this.stack) {
/* 240 */       Object object = directoryNode.key();
/* 241 */       if (paramObject != null && object != null) {
/* 242 */         if (paramObject.equals(object))
/*     */         {
/* 244 */           return true; } 
/*     */         continue;
/*     */       } 
/*     */       try {
/* 248 */         if (Files.isSameFile(paramPath, directoryNode.directory()))
/*     */         {
/* 250 */           return true;
/*     */         }
/* 252 */       } catch (IOException|SecurityException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 257 */     return false;
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
/*     */   private Event visit(Path paramPath, boolean paramBoolean1, boolean paramBoolean2) {
/*     */     BasicFileAttributes basicFileAttributes;
/*     */     try {
/* 276 */       basicFileAttributes = getAttributes(paramPath, paramBoolean2);
/* 277 */     } catch (IOException iOException) {
/* 278 */       return new Event(EventType.ENTRY, paramPath, iOException);
/* 279 */     } catch (SecurityException securityException) {
/* 280 */       if (paramBoolean1)
/* 281 */         return null; 
/* 282 */       throw securityException;
/*     */     } 
/*     */ 
/*     */     
/* 286 */     int i = this.stack.size();
/* 287 */     if (i >= this.maxDepth || !basicFileAttributes.isDirectory()) {
/* 288 */       return new Event(EventType.ENTRY, paramPath, basicFileAttributes);
/*     */     }
/*     */ 
/*     */     
/* 292 */     if (this.followLinks && wouldLoop(paramPath, basicFileAttributes.fileKey())) {
/* 293 */       return new Event(EventType.ENTRY, paramPath, new FileSystemLoopException(paramPath
/* 294 */             .toString()));
/*     */     }
/*     */ 
/*     */     
/* 298 */     DirectoryStream<Path> directoryStream = null;
/*     */     try {
/* 300 */       directoryStream = Files.newDirectoryStream(paramPath);
/* 301 */     } catch (IOException iOException) {
/* 302 */       return new Event(EventType.ENTRY, paramPath, iOException);
/* 303 */     } catch (SecurityException securityException) {
/* 304 */       if (paramBoolean1)
/* 305 */         return null; 
/* 306 */       throw securityException;
/*     */     } 
/*     */ 
/*     */     
/* 310 */     this.stack.push(new DirectoryNode(paramPath, basicFileAttributes.fileKey(), directoryStream));
/* 311 */     return new Event(EventType.START_DIRECTORY, paramPath, basicFileAttributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Event walk(Path paramPath) {
/* 319 */     if (this.closed) {
/* 320 */       throw new IllegalStateException("Closed");
/*     */     }
/* 322 */     Event event = visit(paramPath, false, false);
/*     */ 
/*     */     
/* 325 */     assert event != null;
/* 326 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Event next() {
/*     */     Event event;
/* 334 */     DirectoryNode directoryNode = this.stack.peek();
/* 335 */     if (directoryNode == null) {
/* 336 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/* 341 */       Path path = null;
/* 342 */       IOException iOException = null;
/*     */ 
/*     */       
/* 345 */       if (!directoryNode.skipped()) {
/* 346 */         Iterator<Path> iterator = directoryNode.iterator();
/*     */         try {
/* 348 */           if (iterator.hasNext()) {
/* 349 */             path = iterator.next();
/*     */           }
/* 351 */         } catch (DirectoryIteratorException directoryIteratorException) {
/* 352 */           iOException = directoryIteratorException.getCause();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 357 */       if (path == null) {
/*     */         try {
/* 359 */           directoryNode.stream().close();
/* 360 */         } catch (IOException iOException1) {
/* 361 */           if (iOException != null) {
/* 362 */             iOException = iOException1;
/*     */           } else {
/* 364 */             iOException.addSuppressed(iOException1);
/*     */           } 
/*     */         } 
/* 367 */         this.stack.pop();
/* 368 */         return new Event(EventType.END_DIRECTORY, directoryNode.directory(), iOException);
/*     */       } 
/*     */ 
/*     */       
/* 372 */       event = visit(path, true, true);
/*     */ 
/*     */     
/*     */     }
/* 376 */     while (event == null);
/*     */     
/* 378 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void pop() {
/* 388 */     if (!this.stack.isEmpty()) {
/* 389 */       DirectoryNode directoryNode = this.stack.pop();
/*     */       try {
/* 391 */         directoryNode.stream().close();
/* 392 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void skipRemainingSiblings() {
/* 401 */     if (!this.stack.isEmpty()) {
/* 402 */       ((DirectoryNode)this.stack.peek()).skip();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isOpen() {
/* 410 */     return !this.closed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 418 */     if (!this.closed) {
/* 419 */       while (!this.stack.isEmpty()) {
/* 420 */         pop();
/*     */       }
/* 422 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/FileTreeWalker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */