/*     */ package java.nio.file;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Paths
/*     */ {
/*     */   public static Path get(String paramString, String... paramVarArgs) {
/*  84 */     return FileSystems.getDefault().getPath(paramString, paramVarArgs);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Path get(URI paramURI) {
/* 132 */     String str = paramURI.getScheme();
/* 133 */     if (str == null) {
/* 134 */       throw new IllegalArgumentException("Missing scheme");
/*     */     }
/*     */     
/* 137 */     if (str.equalsIgnoreCase("file")) {
/* 138 */       return FileSystems.getDefault().provider().getPath(paramURI);
/*     */     }
/*     */     
/* 141 */     for (FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
/* 142 */       if (fileSystemProvider.getScheme().equalsIgnoreCase(str)) {
/* 143 */         return fileSystemProvider.getPath(paramURI);
/*     */       }
/*     */     } 
/*     */     
/* 147 */     throw new FileSystemNotFoundException("Provider \"" + str + "\" not installed");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/Paths.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */