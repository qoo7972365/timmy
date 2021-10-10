/*    */ package sun.management;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ public class FileSystemImpl
/*    */   extends FileSystem
/*    */ {
/*    */   public boolean supportsFileSecurity(File paramFile) throws IOException {
/* 37 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isAccessUserOnly(File paramFile) throws IOException {
/* 41 */     return isAccessUserOnly0(paramFile.getPath());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static native boolean isAccessUserOnly0(String paramString) throws IOException;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 51 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*    */         {
/*    */           public Void run() {
/* 54 */             System.loadLibrary("management");
/* 55 */             return null;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/FileSystemImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */