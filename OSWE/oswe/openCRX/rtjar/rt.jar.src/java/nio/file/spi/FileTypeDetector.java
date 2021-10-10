/*    */ package java.nio.file.spi;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class FileTypeDetector
/*    */ {
/*    */   private static Void checkPermission() {
/* 53 */     SecurityManager securityManager = System.getSecurityManager();
/* 54 */     if (securityManager != null)
/* 55 */       securityManager.checkPermission(new RuntimePermission("fileTypeDetector")); 
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private FileTypeDetector(Void paramVoid) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected FileTypeDetector() {
/* 68 */     this(checkPermission());
/*    */   }
/*    */   
/*    */   public abstract String probeContentType(Path paramPath) throws IOException;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/spi/FileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */