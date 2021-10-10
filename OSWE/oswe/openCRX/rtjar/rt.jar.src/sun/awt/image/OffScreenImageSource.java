/*     */ package sun.awt.image;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ImageConsumer;
/*     */ import java.awt.image.ImageProducer;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ public class OffScreenImageSource
/*     */   implements ImageProducer
/*     */ {
/*     */   BufferedImage image;
/*     */   int width;
/*     */   int height;
/*     */   Hashtable properties;
/*     */   private ImageConsumer theConsumer;
/*     */   
/*     */   public OffScreenImageSource(BufferedImage paramBufferedImage, Hashtable paramHashtable) {
/*  47 */     this.image = paramBufferedImage;
/*  48 */     if (paramHashtable != null) {
/*  49 */       this.properties = paramHashtable;
/*     */     } else {
/*  51 */       this.properties = new Hashtable<>();
/*     */     } 
/*  53 */     this.width = paramBufferedImage.getWidth();
/*  54 */     this.height = paramBufferedImage.getHeight();
/*     */   }
/*     */   
/*     */   public OffScreenImageSource(BufferedImage paramBufferedImage) {
/*  58 */     this(paramBufferedImage, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addConsumer(ImageConsumer paramImageConsumer) {
/*  65 */     this.theConsumer = paramImageConsumer;
/*  66 */     produce();
/*     */   }
/*     */   
/*     */   public synchronized boolean isConsumer(ImageConsumer paramImageConsumer) {
/*  70 */     return (paramImageConsumer == this.theConsumer);
/*     */   }
/*     */   
/*     */   public synchronized void removeConsumer(ImageConsumer paramImageConsumer) {
/*  74 */     if (this.theConsumer == paramImageConsumer) {
/*  75 */       this.theConsumer = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void startProduction(ImageConsumer paramImageConsumer) {
/*  80 */     addConsumer(paramImageConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestTopDownLeftRightResend(ImageConsumer paramImageConsumer) {}
/*     */   
/*     */   private void sendPixels() {
/*  87 */     ColorModel colorModel = this.image.getColorModel();
/*  88 */     WritableRaster writableRaster = this.image.getRaster();
/*  89 */     int i = writableRaster.getNumDataElements();
/*  90 */     int j = writableRaster.getDataBuffer().getDataType();
/*  91 */     int[] arrayOfInt = new int[this.width * i];
/*  92 */     boolean bool = true;
/*     */     
/*  94 */     if (colorModel instanceof java.awt.image.IndexColorModel) {
/*  95 */       byte[] arrayOfByte = new byte[this.width];
/*  96 */       this.theConsumer.setColorModel(colorModel);
/*     */       
/*  98 */       if (writableRaster instanceof ByteComponentRaster) {
/*  99 */         bool = false;
/* 100 */         for (byte b = 0; b < this.height; b++) {
/* 101 */           writableRaster.getDataElements(0, b, this.width, 1, arrayOfByte);
/* 102 */           this.theConsumer.setPixels(0, b, this.width, 1, colorModel, arrayOfByte, 0, this.width);
/*     */         }
/*     */       
/*     */       }
/* 106 */       else if (writableRaster instanceof BytePackedRaster) {
/* 107 */         bool = false;
/*     */         
/* 109 */         for (byte b = 0; b < this.height; b++) {
/* 110 */           writableRaster.getPixels(0, b, this.width, 1, arrayOfInt);
/* 111 */           for (byte b1 = 0; b1 < this.width; b1++) {
/* 112 */             arrayOfByte[b1] = (byte)arrayOfInt[b1];
/*     */           }
/* 114 */           this.theConsumer.setPixels(0, b, this.width, 1, colorModel, arrayOfByte, 0, this.width);
/*     */         }
/*     */       
/*     */       }
/* 118 */       else if (j == 2 || j == 3) {
/*     */ 
/*     */ 
/*     */         
/* 122 */         bool = false;
/* 123 */         for (byte b = 0; b < this.height; b++) {
/* 124 */           writableRaster.getPixels(0, b, this.width, 1, arrayOfInt);
/* 125 */           this.theConsumer.setPixels(0, b, this.width, 1, colorModel, arrayOfInt, 0, this.width);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 130 */     } else if (colorModel instanceof java.awt.image.DirectColorModel) {
/* 131 */       byte b1; byte[] arrayOfByte; byte b2; short[] arrayOfShort; byte b3; this.theConsumer.setColorModel(colorModel);
/* 132 */       bool = false;
/* 133 */       switch (j) {
/*     */         case 3:
/* 135 */           for (b1 = 0; b1 < this.height; b1++) {
/* 136 */             writableRaster.getDataElements(0, b1, this.width, 1, arrayOfInt);
/* 137 */             this.theConsumer.setPixels(0, b1, this.width, 1, colorModel, arrayOfInt, 0, this.width);
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 0:
/* 142 */           arrayOfByte = new byte[this.width];
/* 143 */           for (b2 = 0; b2 < this.height; b2++) {
/* 144 */             writableRaster.getDataElements(0, b2, this.width, 1, arrayOfByte);
/* 145 */             for (byte b = 0; b < this.width; b++) {
/* 146 */               arrayOfInt[b] = arrayOfByte[b] & 0xFF;
/*     */             }
/* 148 */             this.theConsumer.setPixels(0, b2, this.width, 1, colorModel, arrayOfInt, 0, this.width);
/*     */           } 
/*     */           break;
/*     */         
/*     */         case 1:
/* 153 */           arrayOfShort = new short[this.width];
/* 154 */           for (b3 = 0; b3 < this.height; b3++) {
/* 155 */             writableRaster.getDataElements(0, b3, this.width, 1, arrayOfShort);
/* 156 */             for (byte b = 0; b < this.width; b++) {
/* 157 */               arrayOfInt[b] = arrayOfShort[b] & 0xFFFF;
/*     */             }
/* 159 */             this.theConsumer.setPixels(0, b3, this.width, 1, colorModel, arrayOfInt, 0, this.width);
/*     */           } 
/*     */           break;
/*     */         
/*     */         default:
/* 164 */           bool = true;
/*     */           break;
/*     */       } 
/*     */     } 
/* 168 */     if (bool) {
/*     */       
/* 170 */       ColorModel colorModel1 = ColorModel.getRGBdefault();
/* 171 */       this.theConsumer.setColorModel(colorModel1);
/*     */       
/* 173 */       for (byte b = 0; b < this.height; b++) {
/* 174 */         for (byte b1 = 0; b1 < this.width; b1++) {
/* 175 */           arrayOfInt[b1] = this.image.getRGB(b1, b);
/*     */         }
/* 177 */         this.theConsumer.setPixels(0, b, this.width, 1, colorModel1, arrayOfInt, 0, this.width);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void produce() {
/*     */     try {
/* 185 */       this.theConsumer.setDimensions(this.image.getWidth(), this.image.getHeight());
/* 186 */       this.theConsumer.setProperties(this.properties);
/* 187 */       sendPixels();
/* 188 */       this.theConsumer.imageComplete(2);
/* 189 */       this.theConsumer.imageComplete(3);
/* 190 */     } catch (NullPointerException nullPointerException) {
/* 191 */       if (this.theConsumer != null)
/* 192 */         this.theConsumer.imageComplete(1); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/OffScreenImageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */