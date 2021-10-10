/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleFileVisitor<T>
/*     */   implements FileVisitor<T>
/*     */ {
/*     */   public FileVisitResult preVisitDirectory(T paramT, BasicFileAttributes paramBasicFileAttributes) throws IOException {
/*  60 */     Objects.requireNonNull(paramT);
/*  61 */     Objects.requireNonNull(paramBasicFileAttributes);
/*  62 */     return FileVisitResult.CONTINUE;
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
/*     */   public FileVisitResult visitFile(T paramT, BasicFileAttributes paramBasicFileAttributes) throws IOException {
/*  75 */     Objects.requireNonNull(paramT);
/*  76 */     Objects.requireNonNull(paramBasicFileAttributes);
/*  77 */     return FileVisitResult.CONTINUE;
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
/*     */   public FileVisitResult visitFileFailed(T paramT, IOException paramIOException) throws IOException {
/*  90 */     Objects.requireNonNull(paramT);
/*  91 */     throw paramIOException;
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
/*     */   public FileVisitResult postVisitDirectory(T paramT, IOException paramIOException) throws IOException {
/* 107 */     Objects.requireNonNull(paramT);
/* 108 */     if (paramIOException != null)
/* 109 */       throw paramIOException; 
/* 110 */     return FileVisitResult.CONTINUE;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/SimpleFileVisitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */