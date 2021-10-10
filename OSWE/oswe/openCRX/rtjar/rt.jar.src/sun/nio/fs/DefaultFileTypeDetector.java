/*    */ package sun.nio.fs;
/*    */ 
/*    */ import java.nio.file.FileSystems;
/*    */ import java.nio.file.spi.FileSystemProvider;
/*    */ import java.nio.file.spi.FileTypeDetector;
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
/*    */ public class DefaultFileTypeDetector
/*    */ {
/*    */   public static FileTypeDetector create() {
/* 36 */     FileSystemProvider fileSystemProvider = FileSystems.getDefault().provider();
/* 37 */     return ((UnixFileSystemProvider)fileSystemProvider).getFileTypeDetector();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/DefaultFileTypeDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */