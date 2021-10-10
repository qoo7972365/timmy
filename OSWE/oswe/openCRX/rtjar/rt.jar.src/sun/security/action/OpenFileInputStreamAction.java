/*    */ package sun.security.action;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.security.PrivilegedExceptionAction;
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
/*    */ public class OpenFileInputStreamAction
/*    */   implements PrivilegedExceptionAction<FileInputStream>
/*    */ {
/*    */   private final File file;
/*    */   
/*    */   public OpenFileInputStreamAction(File paramFile) {
/* 43 */     this.file = paramFile;
/*    */   }
/*    */   
/*    */   public OpenFileInputStreamAction(String paramString) {
/* 47 */     this.file = new File(paramString);
/*    */   }
/*    */   
/*    */   public FileInputStream run() throws Exception {
/* 51 */     return new FileInputStream(this.file);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/action/OpenFileInputStreamAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */