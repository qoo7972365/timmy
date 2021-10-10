/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.WatchService;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractPath
/*     */   implements Path
/*     */ {
/*     */   public final boolean startsWith(String paramString) {
/*  43 */     return startsWith(getFileSystem().getPath(paramString, new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean endsWith(String paramString) {
/*  48 */     return endsWith(getFileSystem().getPath(paramString, new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Path resolve(String paramString) {
/*  53 */     return resolve(getFileSystem().getPath(paramString, new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Path resolveSibling(Path paramPath) {
/*  58 */     if (paramPath == null)
/*  59 */       throw new NullPointerException(); 
/*  60 */     Path path = getParent();
/*  61 */     return (path == null) ? paramPath : path.resolve(paramPath);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Path resolveSibling(String paramString) {
/*  66 */     return resolveSibling(getFileSystem().getPath(paramString, new String[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Iterator<Path> iterator() {
/*  71 */     return new Iterator<Path>() {
/*  72 */         private int i = 0;
/*     */         
/*     */         public boolean hasNext() {
/*  75 */           return (this.i < AbstractPath.this.getNameCount());
/*     */         }
/*     */         
/*     */         public Path next() {
/*  79 */           if (this.i < AbstractPath.this.getNameCount()) {
/*  80 */             Path path = AbstractPath.this.getName(this.i);
/*  81 */             this.i++;
/*  82 */             return path;
/*     */           } 
/*  84 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/*  89 */           throw new UnsupportedOperationException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final File toFile() {
/*  96 */     return new File(toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WatchKey register(WatchService paramWatchService, WatchEvent.Kind<?>... paramVarArgs) throws IOException {
/* 104 */     return register(paramWatchService, paramVarArgs, new WatchEvent.Modifier[0]);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/AbstractPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */