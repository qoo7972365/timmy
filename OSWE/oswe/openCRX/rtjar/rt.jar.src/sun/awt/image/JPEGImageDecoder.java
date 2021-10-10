/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.IndexColorModel;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Hashtable;
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
/*     */ public class JPEGImageDecoder
/*     */   extends ImageDecoder
/*     */ {
/*     */   private static ColorModel RGBcolormodel;
/*     */   private static ColorModel ARGBcolormodel;
/*     */   private static ColorModel Graycolormodel;
/*  50 */   private static final Class InputStreamClass = InputStream.class;
/*     */   
/*     */   private ColorModel colormodel;
/*     */ 
/*     */   
/*     */   static {
/*  56 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  59 */             System.loadLibrary("javajpeg");
/*  60 */             return null;
/*     */           }
/*     */         });
/*  63 */     initIDs(InputStreamClass);
/*  64 */     RGBcolormodel = new DirectColorModel(24, 16711680, 65280, 255);
/*  65 */     ARGBcolormodel = ColorModel.getRGBdefault();
/*  66 */     byte[] arrayOfByte = new byte[256];
/*  67 */     for (byte b = 0; b < 'Ā'; b++) {
/*  68 */       arrayOfByte[b] = (byte)b;
/*     */     }
/*  70 */     Graycolormodel = new IndexColorModel(8, 256, arrayOfByte, arrayOfByte, arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   Hashtable props = new Hashtable<>();
/*     */   
/*     */   public JPEGImageDecoder(InputStreamImageSource paramInputStreamImageSource, InputStream paramInputStream) {
/*  79 */     super(paramInputStreamImageSource, paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int hintflags = 22;
/*     */   
/*     */   private static void error(String paramString) throws ImageFormatException {
/*  86 */     throw new ImageFormatException(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sendHeaderInfo(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/*  93 */     setDimensions(paramInt1, paramInt2);
/*     */     
/*  95 */     setProperties(this.props);
/*  96 */     if (paramBoolean1) {
/*  97 */       this.colormodel = Graycolormodel;
/*     */     }
/*  99 */     else if (paramBoolean2) {
/* 100 */       this.colormodel = ARGBcolormodel;
/*     */     } else {
/* 102 */       this.colormodel = RGBcolormodel;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     setColorModel(this.colormodel);
/*     */     
/* 108 */     int i = 22;
/* 109 */     if (!paramBoolean3) {
/* 110 */       i |= 0x8;
/*     */     }
/* 112 */     setHints(22);
/* 113 */     headerComplete();
/*     */     
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public boolean sendPixels(int[] paramArrayOfint, int paramInt) {
/* 119 */     int i = setPixels(0, paramInt, paramArrayOfint.length, 1, this.colormodel, paramArrayOfint, 0, paramArrayOfint.length);
/*     */     
/* 121 */     if (i <= 0) {
/* 122 */       this.aborted = true;
/*     */     }
/* 124 */     return !this.aborted;
/*     */   }
/*     */   
/*     */   public boolean sendPixels(byte[] paramArrayOfbyte, int paramInt) {
/* 128 */     int i = setPixels(0, paramInt, paramArrayOfbyte.length, 1, this.colormodel, paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */     
/* 130 */     if (i <= 0) {
/* 131 */       this.aborted = true;
/*     */     }
/* 133 */     return !this.aborted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void produceImage() throws IOException, ImageFormatException {
/*     */     try {
/* 141 */       readImage(this.input, new byte[1024]);
/* 142 */       if (!this.aborted) {
/* 143 */         imageComplete(3, true);
/*     */       }
/* 145 */     } catch (IOException iOException) {
/* 146 */       if (!this.aborted) {
/* 147 */         throw iOException;
/*     */       }
/*     */     } finally {
/* 150 */       close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static native void initIDs(Class paramClass);
/*     */   
/*     */   private native void readImage(InputStream paramInputStream, byte[] paramArrayOfbyte) throws ImageFormatException, IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/JPEGImageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */