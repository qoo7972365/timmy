/*    */ package sun.nio.ch;
/*    */ 
/*    */ import java.io.FileDescriptor;
/*    */ import java.io.IOException;
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
/*    */ public class FileKey
/*    */ {
/*    */   private long st_dev;
/*    */   private long st_ino;
/*    */   
/*    */   public static FileKey create(FileDescriptor paramFileDescriptor) {
/* 42 */     FileKey fileKey = new FileKey();
/*    */     try {
/* 44 */       fileKey.init(paramFileDescriptor);
/* 45 */     } catch (IOException iOException) {
/* 46 */       throw new Error(iOException);
/*    */     } 
/* 48 */     return fileKey;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 52 */     return (int)(this.st_dev ^ this.st_dev >>> 32L) + (int)(this.st_ino ^ this.st_ino >>> 32L);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 57 */     if (paramObject == this)
/* 58 */       return true; 
/* 59 */     if (!(paramObject instanceof FileKey))
/* 60 */       return false; 
/* 61 */     FileKey fileKey = (FileKey)paramObject;
/* 62 */     if (this.st_dev != fileKey.st_dev || this.st_ino != fileKey.st_ino)
/*    */     {
/* 64 */       return false;
/*    */     }
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private native void init(FileDescriptor paramFileDescriptor) throws IOException;
/*    */   
/*    */   static {
/* 73 */     initIDs();
/*    */   }
/*    */   
/*    */   private static native void initIDs();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/FileKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */