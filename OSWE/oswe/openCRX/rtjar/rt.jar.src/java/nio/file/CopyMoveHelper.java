/*     */ package java.nio.file;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CopyMoveHelper
/*     */ {
/*     */   private static class CopyOptions
/*     */   {
/*     */     boolean replaceExisting = false;
/*     */     boolean copyAttributes = false;
/*     */     boolean followLinks = true;
/*     */     
/*     */     static CopyOptions parse(CopyOption... param1VarArgs) {
/*  51 */       CopyOptions copyOptions = new CopyOptions();
/*  52 */       for (CopyOption copyOption : param1VarArgs) {
/*  53 */         if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
/*  54 */           copyOptions.replaceExisting = true;
/*     */         
/*     */         }
/*  57 */         else if (copyOption == LinkOption.NOFOLLOW_LINKS) {
/*  58 */           copyOptions.followLinks = false;
/*     */         
/*     */         }
/*  61 */         else if (copyOption == StandardCopyOption.COPY_ATTRIBUTES) {
/*  62 */           copyOptions.copyAttributes = true;
/*     */         } else {
/*     */           
/*  65 */           if (copyOption == null)
/*  66 */             throw new NullPointerException(); 
/*  67 */           throw new UnsupportedOperationException("'" + copyOption + "' is not a recognized copy option");
/*     */         } 
/*     */       } 
/*  70 */       return copyOptions;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static CopyOption[] convertMoveToCopyOptions(CopyOption... paramVarArgs) throws AtomicMoveNotSupportedException {
/*  81 */     int i = paramVarArgs.length;
/*  82 */     CopyOption[] arrayOfCopyOption = new CopyOption[i + 2];
/*  83 */     for (byte b = 0; b < i; b++) {
/*  84 */       CopyOption copyOption = paramVarArgs[b];
/*  85 */       if (copyOption == StandardCopyOption.ATOMIC_MOVE) {
/*  86 */         throw new AtomicMoveNotSupportedException(null, null, "Atomic move between providers is not supported");
/*     */       }
/*     */       
/*  89 */       arrayOfCopyOption[b] = copyOption;
/*     */     } 
/*  91 */     arrayOfCopyOption[i] = LinkOption.NOFOLLOW_LINKS;
/*  92 */     arrayOfCopyOption[i + 1] = StandardCopyOption.COPY_ATTRIBUTES;
/*  93 */     return arrayOfCopyOption;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void copyToForeignTarget(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 104 */     CopyOptions copyOptions = CopyOptions.parse(paramVarArgs);
/* 105 */     (new LinkOption[1])[0] = LinkOption.NOFOLLOW_LINKS; LinkOption[] arrayOfLinkOption = copyOptions.followLinks ? new LinkOption[0] : new LinkOption[1];
/*     */ 
/*     */ 
/*     */     
/* 109 */     BasicFileAttributes basicFileAttributes = (BasicFileAttributes)Files.readAttributes(paramPath1, (Class)BasicFileAttributes.class, arrayOfLinkOption);
/*     */ 
/*     */     
/* 112 */     if (basicFileAttributes.isSymbolicLink()) {
/* 113 */       throw new IOException("Copying of symbolic links not supported");
/*     */     }
/*     */     
/* 116 */     if (copyOptions.replaceExisting) {
/* 117 */       Files.deleteIfExists(paramPath2);
/* 118 */     } else if (Files.exists(paramPath2, new LinkOption[0])) {
/* 119 */       throw new FileAlreadyExistsException(paramPath2.toString());
/*     */     } 
/*     */     
/* 122 */     if (basicFileAttributes.isDirectory()) {
/* 123 */       Files.createDirectory(paramPath2, (FileAttribute<?>[])new FileAttribute[0]);
/*     */     } else {
/* 125 */       try (InputStream null = Files.newInputStream(paramPath1, new OpenOption[0])) {
/* 126 */         Files.copy(inputStream, paramPath2, new CopyOption[0]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (copyOptions.copyAttributes) {
/*     */       
/* 133 */       BasicFileAttributeView basicFileAttributeView = Files.<BasicFileAttributeView>getFileAttributeView(paramPath2, BasicFileAttributeView.class, new LinkOption[0]);
/*     */       try {
/* 135 */         basicFileAttributeView.setTimes(basicFileAttributes.lastModifiedTime(), basicFileAttributes
/* 136 */             .lastAccessTime(), basicFileAttributes
/* 137 */             .creationTime());
/* 138 */       } catch (Throwable throwable) {
/*     */         
/*     */         try {
/* 141 */           Files.delete(paramPath2);
/* 142 */         } catch (Throwable throwable1) {
/* 143 */           throwable.addSuppressed(throwable1);
/*     */         } 
/* 145 */         throw throwable;
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
/*     */   static void moveToForeignTarget(Path paramPath1, Path paramPath2, CopyOption... paramVarArgs) throws IOException {
/* 157 */     copyToForeignTarget(paramPath1, paramPath2, convertMoveToCopyOptions(paramVarArgs));
/* 158 */     Files.delete(paramPath1);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/CopyMoveHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */