/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.util.logging.PlatformLogger;
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
/*     */ public final class SplashScreen
/*     */ {
/*     */   private BufferedImage image;
/*     */   private final long splashPtr;
/*     */   
/*     */   SplashScreen(long paramLong) {
/* 100 */     this.splashPtr = paramLong;
/*     */   }
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
/*     */   public static SplashScreen getSplashScreen() {
/* 115 */     synchronized (SplashScreen.class) {
/* 116 */       if (GraphicsEnvironment.isHeadless()) {
/* 117 */         throw new HeadlessException();
/*     */       }
/*     */       
/* 120 */       if (!wasClosed && theInstance == null) {
/* 121 */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */             {
/*     */               public Void run() {
/* 124 */                 System.loadLibrary("splashscreen");
/* 125 */                 return null;
/*     */               }
/*     */             });
/* 128 */         long l = _getInstance();
/* 129 */         if (l != 0L && _isVisible(l)) {
/* 130 */           theInstance = new SplashScreen(l);
/*     */         }
/*     */       } 
/* 133 */       return theInstance;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImageURL(URL paramURL) throws NullPointerException, IOException, IllegalStateException {
/* 153 */     checkVisible();
/* 154 */     URLConnection uRLConnection = paramURL.openConnection();
/* 155 */     uRLConnection.connect();
/* 156 */     int i = uRLConnection.getContentLength();
/* 157 */     InputStream inputStream = uRLConnection.getInputStream();
/* 158 */     byte[] arrayOfByte = new byte[i];
/* 159 */     int j = 0;
/*     */     
/*     */     while (true) {
/* 162 */       int k = inputStream.available();
/* 163 */       if (k <= 0)
/*     */       {
/*     */         
/* 166 */         k = 1;
/*     */       }
/*     */ 
/*     */       
/* 170 */       if (j + k > i) {
/* 171 */         i = j * 2;
/* 172 */         if (j + k > i) {
/* 173 */           i = k + j;
/*     */         }
/* 175 */         byte[] arrayOfByte1 = arrayOfByte;
/* 176 */         arrayOfByte = new byte[i];
/* 177 */         System.arraycopy(arrayOfByte1, 0, arrayOfByte, 0, j);
/*     */       } 
/*     */       
/* 180 */       int m = inputStream.read(arrayOfByte, j, k);
/* 181 */       if (m < 0) {
/*     */         break;
/*     */       }
/* 184 */       j += m;
/*     */     } 
/* 186 */     synchronized (SplashScreen.class) {
/* 187 */       checkVisible();
/* 188 */       if (!_setImageData(this.splashPtr, arrayOfByte)) {
/* 189 */         throw new IOException("Bad image format or i/o error when loading image");
/*     */       }
/* 191 */       this.imageURL = paramURL;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkVisible() {
/* 196 */     if (!isVisible()) {
/* 197 */       throw new IllegalStateException("no splash screen available");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL getImageURL() throws IllegalStateException {
/* 207 */     synchronized (SplashScreen.class) {
/* 208 */       checkVisible();
/* 209 */       if (this.imageURL == null) {
/*     */         try {
/* 211 */           String str1 = _getImageFileName(this.splashPtr);
/* 212 */           String str2 = _getImageJarName(this.splashPtr);
/* 213 */           if (str1 != null) {
/* 214 */             if (str2 != null) {
/* 215 */               this.imageURL = new URL("jar:" + (new File(str2)).toURL().toString() + "!/" + str1);
/*     */             } else {
/* 217 */               this.imageURL = (new File(str1)).toURL();
/*     */             }
/*     */           
/*     */           }
/* 221 */         } catch (MalformedURLException malformedURLException) {
/* 222 */           if (log.isLoggable(PlatformLogger.Level.FINE)) {
/* 223 */             log.fine("MalformedURLException caught in the getImageURL() method", malformedURLException);
/*     */           }
/*     */         } 
/*     */       }
/* 227 */       return this.imageURL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBounds() throws IllegalStateException {
/* 246 */     synchronized (SplashScreen.class) {
/* 247 */       checkVisible();
/* 248 */       float f = _getScaleFactor(this.splashPtr);
/* 249 */       Rectangle rectangle = _getBounds(this.splashPtr);
/* 250 */       assert f > 0.0F;
/* 251 */       if (f > 0.0F && f != 1.0F) {
/* 252 */         rectangle.setSize((int)(rectangle.getWidth() / f), 
/* 253 */             (int)(rectangle.getHeight() / f));
/*     */       }
/* 255 */       return rectangle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getSize() throws IllegalStateException {
/* 274 */     return getBounds().getSize();
/*     */   }
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
/*     */   public Graphics2D createGraphics() throws IllegalStateException {
/* 295 */     synchronized (SplashScreen.class) {
/* 296 */       checkVisible();
/* 297 */       if (this.image == null) {
/*     */         
/* 299 */         Dimension dimension = _getBounds(this.splashPtr).getSize();
/* 300 */         this.image = new BufferedImage(dimension.width, dimension.height, 2);
/*     */       } 
/*     */       
/* 303 */       float f = _getScaleFactor(this.splashPtr);
/* 304 */       Graphics2D graphics2D = this.image.createGraphics();
/* 305 */       assert f > 0.0F;
/* 306 */       if (f <= 0.0F) {
/* 307 */         f = 1.0F;
/*     */       }
/* 309 */       graphics2D.scale(f, f);
/* 310 */       return graphics2D;
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
/*     */   public void update() throws IllegalStateException {
/*     */     BufferedImage bufferedImage;
/* 323 */     synchronized (SplashScreen.class) {
/* 324 */       checkVisible();
/* 325 */       bufferedImage = this.image;
/*     */     } 
/* 327 */     if (bufferedImage == null) {
/* 328 */       throw new IllegalStateException("no overlay image available");
/*     */     }
/* 330 */     DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
/* 331 */     if (!(dataBuffer instanceof DataBufferInt)) {
/* 332 */       throw new AssertionError("Overlay image DataBuffer is of invalid type == " + dataBuffer.getClass().getName());
/*     */     }
/* 334 */     int i = dataBuffer.getNumBanks();
/* 335 */     if (i != 1) {
/* 336 */       throw new AssertionError("Invalid number of banks ==" + i + " in overlay image DataBuffer");
/*     */     }
/* 338 */     if (!(bufferedImage.getSampleModel() instanceof SinglePixelPackedSampleModel)) {
/* 339 */       throw new AssertionError("Overlay image has invalid sample model == " + bufferedImage.getSampleModel().getClass().getName());
/*     */     }
/* 341 */     SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)bufferedImage.getSampleModel();
/* 342 */     int j = singlePixelPackedSampleModel.getScanlineStride();
/* 343 */     Rectangle rectangle = bufferedImage.getRaster().getBounds();
/*     */ 
/*     */     
/* 346 */     int[] arrayOfInt = SunWritableRaster.stealData((DataBufferInt)dataBuffer, 0);
/* 347 */     synchronized (SplashScreen.class) {
/* 348 */       checkVisible();
/* 349 */       _update(this.splashPtr, arrayOfInt, rectangle.x, rectangle.y, rectangle.width, rectangle.height, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IllegalStateException {
/* 360 */     synchronized (SplashScreen.class) {
/* 361 */       checkVisible();
/* 362 */       _close(this.splashPtr);
/* 363 */       this.image = null;
/* 364 */       markClosed();
/*     */     } 
/*     */   }
/*     */   
/*     */   static void markClosed() {
/* 369 */     synchronized (SplashScreen.class) {
/* 370 */       wasClosed = true;
/* 371 */       theInstance = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 390 */     synchronized (SplashScreen.class) {
/* 391 */       return (!wasClosed && _isVisible(this.splashPtr));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean wasClosed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URL imageURL;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 409 */   private static SplashScreen theInstance = null;
/*     */   
/* 411 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.SplashScreen");
/*     */   
/*     */   private static native void _update(long paramLong, int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */   
/*     */   private static native boolean _isVisible(long paramLong);
/*     */   
/*     */   private static native Rectangle _getBounds(long paramLong);
/*     */   
/*     */   private static native long _getInstance();
/*     */   
/*     */   private static native void _close(long paramLong);
/*     */   
/*     */   private static native String _getImageFileName(long paramLong);
/*     */   
/*     */   private static native String _getImageJarName(long paramLong);
/*     */   
/*     */   private static native boolean _setImageData(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   private static native float _getScaleFactor(long paramLong);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/SplashScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */