/*    */ package sun.awt.image;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
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
/*    */ public class FileImageSource
/*    */   extends InputStreamImageSource
/*    */ {
/*    */   String imagefile;
/*    */   
/*    */   public FileImageSource(String paramString) {
/* 37 */     SecurityManager securityManager = System.getSecurityManager();
/* 38 */     if (securityManager != null) {
/* 39 */       securityManager.checkRead(paramString);
/*    */     }
/* 41 */     this.imagefile = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   final boolean checkSecurity(Object paramObject, boolean paramBoolean) {
/* 47 */     return true;
/*    */   }
/*    */   protected ImageDecoder getDecoder() {
/*    */     BufferedInputStream bufferedInputStream;
/* 51 */     if (this.imagefile == null) {
/* 52 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 57 */       bufferedInputStream = new BufferedInputStream(new FileInputStream(this.imagefile));
/* 58 */     } catch (FileNotFoundException fileNotFoundException) {
/* 59 */       return null;
/*    */     } 
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
/* 77 */     return getDecoder(bufferedInputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/FileImageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */