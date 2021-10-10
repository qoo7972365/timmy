/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.Canvas;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Image;
/*    */ import java.awt.MediaTracker;
/*    */ import java.awt.Point;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.image.ImageProducer;
/*    */ import java.awt.image.PixelGrabber;
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
/*    */ public abstract class CustomCursor
/*    */   extends Cursor
/*    */ {
/*    */   protected Image image;
/*    */   
/*    */   public CustomCursor(Image paramImage, Point paramPoint, String paramString) throws IndexOutOfBoundsException {
/* 42 */     super(paramString);
/* 43 */     this.image = paramImage;
/* 44 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*    */ 
/*    */     
/* 47 */     Canvas canvas = new Canvas();
/* 48 */     MediaTracker mediaTracker = new MediaTracker(canvas);
/* 49 */     mediaTracker.addImage(paramImage, 0);
/*    */     try {
/* 51 */       mediaTracker.waitForAll();
/* 52 */     } catch (InterruptedException interruptedException) {}
/*    */     
/* 54 */     int i = paramImage.getWidth(canvas);
/* 55 */     int j = paramImage.getHeight(canvas);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 62 */     if (mediaTracker.isErrorAny() || i < 0 || j < 0) {
/* 63 */       paramPoint.x = paramPoint.y = 0;
/*    */     }
/*    */ 
/*    */     
/* 67 */     Dimension dimension = toolkit.getBestCursorSize(i, j);
/* 68 */     if ((dimension.width != i || dimension.height != j) && dimension.width != 0 && dimension.height != 0) {
/*    */       
/* 70 */       paramImage = paramImage.getScaledInstance(dimension.width, dimension.height, 1);
/*    */ 
/*    */       
/* 73 */       i = dimension.width;
/* 74 */       j = dimension.height;
/*    */     } 
/*    */ 
/*    */     
/* 78 */     if (paramPoint.x >= i || paramPoint.y >= j || paramPoint.x < 0 || paramPoint.y < 0) {
/* 79 */       throw new IndexOutOfBoundsException("invalid hotSpot");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 89 */     int[] arrayOfInt = new int[i * j];
/* 90 */     ImageProducer imageProducer = paramImage.getSource();
/* 91 */     PixelGrabber pixelGrabber = new PixelGrabber(imageProducer, 0, 0, i, j, arrayOfInt, 0, i);
/*    */     
/*    */     try {
/* 94 */       pixelGrabber.grabPixels();
/* 95 */     } catch (InterruptedException interruptedException) {}
/*    */ 
/*    */     
/* 98 */     createNativeCursor(this.image, arrayOfInt, i, j, paramPoint.x, paramPoint.y);
/*    */   }
/*    */   
/*    */   protected abstract void createNativeCursor(Image paramImage, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/CustomCursor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */