/*     */ package java.awt.image.renderable;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.ImageConsumer;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.RenderedImage;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
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
/*     */ public class RenderableImageProducer
/*     */   implements ImageProducer, Runnable
/*     */ {
/*     */   RenderableImage rdblImage;
/*     */   RenderContext rc;
/*  71 */   Vector ics = new Vector();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderableImageProducer(RenderableImage paramRenderableImage, RenderContext paramRenderContext) {
/*  82 */     this.rdblImage = paramRenderableImage;
/*  83 */     this.rc = paramRenderContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setRenderContext(RenderContext paramRenderContext) {
/*  92 */     this.rc = paramRenderContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addConsumer(ImageConsumer paramImageConsumer) {
/* 102 */     if (!this.ics.contains(paramImageConsumer)) {
/* 103 */       this.ics.addElement(paramImageConsumer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isConsumer(ImageConsumer paramImageConsumer) {
/* 115 */     return this.ics.contains(paramImageConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeConsumer(ImageConsumer paramImageConsumer) {
/* 125 */     this.ics.removeElement(paramImageConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void startProduction(ImageConsumer paramImageConsumer) {
/* 136 */     addConsumer(paramImageConsumer);
/*     */     
/* 138 */     Thread thread = new Thread(this, "RenderableImageProducer Thread");
/* 139 */     thread.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestTopDownLeftRightResend(ImageConsumer paramImageConsumer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     RenderedImage renderedImage;
/* 160 */     if (this.rc != null) {
/* 161 */       renderedImage = this.rdblImage.createRendering(this.rc);
/*     */     } else {
/* 163 */       renderedImage = this.rdblImage.createDefaultRendering();
/*     */     } 
/*     */ 
/*     */     
/* 167 */     ColorModel colorModel = renderedImage.getColorModel();
/* 168 */     Raster raster = renderedImage.getData();
/* 169 */     SampleModel sampleModel = raster.getSampleModel();
/* 170 */     DataBuffer dataBuffer = raster.getDataBuffer();
/*     */     
/* 172 */     if (colorModel == null) {
/* 173 */       colorModel = ColorModel.getRGBdefault();
/*     */     }
/* 175 */     int i = raster.getMinX();
/* 176 */     int j = raster.getMinY();
/* 177 */     int k = raster.getWidth();
/* 178 */     int m = raster.getHeight();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     Enumeration<ImageConsumer> enumeration = this.ics.elements();
/* 184 */     while (enumeration.hasMoreElements()) {
/* 185 */       ImageConsumer imageConsumer = enumeration.nextElement();
/* 186 */       imageConsumer.setDimensions(k, m);
/* 187 */       imageConsumer.setHints(30);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     int[] arrayOfInt1 = new int[k];
/*     */     
/* 197 */     int n = sampleModel.getNumBands();
/* 198 */     int[] arrayOfInt2 = new int[n];
/* 199 */     for (byte b = 0; b < m; b++) {
/* 200 */       for (byte b1 = 0; b1 < k; b1++) {
/* 201 */         sampleModel.getPixel(b1, b, arrayOfInt2, dataBuffer);
/* 202 */         arrayOfInt1[b1] = colorModel.getDataElement(arrayOfInt2, 0);
/*     */       } 
/*     */       
/* 205 */       enumeration = this.ics.elements();
/* 206 */       while (enumeration.hasMoreElements()) {
/* 207 */         ImageConsumer imageConsumer = enumeration.nextElement();
/* 208 */         imageConsumer.setPixels(0, b, k, 1, colorModel, arrayOfInt1, 0, k);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 213 */     enumeration = this.ics.elements();
/* 214 */     while (enumeration.hasMoreElements()) {
/* 215 */       ImageConsumer imageConsumer = enumeration.nextElement();
/* 216 */       imageConsumer.imageComplete(3);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/renderable/RenderableImageProducer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */