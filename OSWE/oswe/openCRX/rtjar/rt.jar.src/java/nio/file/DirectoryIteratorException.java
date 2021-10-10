/*    */ package java.nio.file;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InvalidObjectException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.util.ConcurrentModificationException;
/*    */ import java.util.Objects;
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
/*    */ public final class DirectoryIteratorException
/*    */   extends ConcurrentModificationException
/*    */ {
/*    */   private static final long serialVersionUID = -6012699886086212874L;
/*    */   
/*    */   public DirectoryIteratorException(IOException paramIOException) {
/* 59 */     super(Objects.<Throwable>requireNonNull(paramIOException));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IOException getCause() {
/* 69 */     return (IOException)super.getCause();
/*    */   }
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
/*    */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 82 */     paramObjectInputStream.defaultReadObject();
/* 83 */     Throwable throwable = super.getCause();
/* 84 */     if (!(throwable instanceof IOException))
/* 85 */       throw new InvalidObjectException("Cause must be an IOException"); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/DirectoryIteratorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */