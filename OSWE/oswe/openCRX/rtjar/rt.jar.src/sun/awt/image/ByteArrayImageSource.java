/*    */ package sun.awt.image;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.ByteArrayInputStream;
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
/*    */ public class ByteArrayImageSource
/*    */   extends InputStreamImageSource
/*    */ {
/*    */   byte[] imagedata;
/*    */   int imageoffset;
/*    */   int imagelength;
/*    */   
/*    */   public ByteArrayImageSource(byte[] paramArrayOfbyte) {
/* 38 */     this(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*    */   }
/*    */   
/*    */   public ByteArrayImageSource(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 42 */     this.imagedata = paramArrayOfbyte;
/* 43 */     this.imageoffset = paramInt1;
/* 44 */     this.imagelength = paramInt2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final boolean checkSecurity(Object paramObject, boolean paramBoolean) {
/* 51 */     return true;
/*    */   }
/*    */   
/*    */   protected ImageDecoder getDecoder() {
/* 55 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(this.imagedata, this.imageoffset, this.imagelength));
/*    */ 
/*    */ 
/*    */     
/* 59 */     return getDecoder(bufferedInputStream);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/ByteArrayImageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */