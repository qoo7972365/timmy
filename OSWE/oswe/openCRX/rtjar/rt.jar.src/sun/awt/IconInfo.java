/*     */ package sun.awt;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.Arrays;
/*     */ import sun.awt.image.ImageRepresentation;
/*     */ import sun.awt.image.ToolkitImage;
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
/*     */ public class IconInfo
/*     */ {
/*     */   private int[] intIconData;
/*     */   private long[] longIconData;
/*     */   private Image image;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private int scaledWidth;
/*     */   private int scaledHeight;
/*     */   private int rawLength;
/*     */   
/*     */   public IconInfo(int[] paramArrayOfint) {
/*  72 */     this
/*  73 */       .intIconData = (null == paramArrayOfint) ? null : Arrays.copyOf(paramArrayOfint, paramArrayOfint.length);
/*  74 */     this.width = paramArrayOfint[0];
/*  75 */     this.height = paramArrayOfint[1];
/*  76 */     this.scaledWidth = this.width;
/*  77 */     this.scaledHeight = this.height;
/*  78 */     this.rawLength = this.width * this.height + 2;
/*     */   }
/*     */   
/*     */   public IconInfo(long[] paramArrayOflong) {
/*  82 */     this
/*  83 */       .longIconData = (null == paramArrayOflong) ? null : Arrays.copyOf(paramArrayOflong, paramArrayOflong.length);
/*  84 */     this.width = (int)paramArrayOflong[0];
/*  85 */     this.height = (int)paramArrayOflong[1];
/*  86 */     this.scaledWidth = this.width;
/*  87 */     this.scaledHeight = this.height;
/*  88 */     this.rawLength = this.width * this.height + 2;
/*     */   }
/*     */   
/*     */   public IconInfo(Image paramImage) {
/*  92 */     this.image = paramImage;
/*  93 */     if (paramImage instanceof ToolkitImage) {
/*  94 */       ImageRepresentation imageRepresentation = ((ToolkitImage)paramImage).getImageRep();
/*  95 */       imageRepresentation.reconstruct(32);
/*  96 */       this.width = imageRepresentation.getWidth();
/*  97 */       this.height = imageRepresentation.getHeight();
/*     */     } else {
/*  99 */       this.width = paramImage.getWidth(null);
/* 100 */       this.height = paramImage.getHeight(null);
/*     */     } 
/* 102 */     this.scaledWidth = this.width;
/* 103 */     this.scaledHeight = this.height;
/* 104 */     this.rawLength = this.width * this.height + 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScaledSize(int paramInt1, int paramInt2) {
/* 111 */     this.scaledWidth = paramInt1;
/* 112 */     this.scaledHeight = paramInt2;
/* 113 */     this.rawLength = paramInt1 * paramInt2 + 2;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 117 */     return (this.width > 0 && this.height > 0);
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 121 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 125 */     return this.height;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 129 */     return "IconInfo[w=" + this.width + ",h=" + this.height + ",sw=" + this.scaledWidth + ",sh=" + this.scaledHeight + "]";
/*     */   }
/*     */   
/*     */   public int getRawLength() {
/* 133 */     return this.rawLength;
/*     */   }
/*     */   
/*     */   public int[] getIntData() {
/* 137 */     if (this.intIconData == null) {
/* 138 */       if (this.longIconData != null) {
/* 139 */         this.intIconData = longArrayToIntArray(this.longIconData);
/* 140 */       } else if (this.image != null) {
/* 141 */         this.intIconData = imageToIntArray(this.image, this.scaledWidth, this.scaledHeight);
/*     */       } 
/*     */     }
/* 144 */     return this.intIconData;
/*     */   }
/*     */   
/*     */   public long[] getLongData() {
/* 148 */     if (this.longIconData == null) {
/* 149 */       if (this.intIconData != null) {
/* 150 */         this.longIconData = intArrayToLongArray(this.intIconData);
/* 151 */       } else if (this.image != null) {
/* 152 */         int[] arrayOfInt = imageToIntArray(this.image, this.scaledWidth, this.scaledHeight);
/* 153 */         this.longIconData = intArrayToLongArray(arrayOfInt);
/*     */       } 
/*     */     }
/* 156 */     return this.longIconData;
/*     */   }
/*     */   
/*     */   public Image getImage() {
/* 160 */     if (this.image == null) {
/* 161 */       if (this.intIconData != null) {
/* 162 */         this.image = intArrayToImage(this.intIconData);
/* 163 */       } else if (this.longIconData != null) {
/* 164 */         int[] arrayOfInt = longArrayToIntArray(this.longIconData);
/* 165 */         this.image = intArrayToImage(arrayOfInt);
/*     */       } 
/*     */     }
/* 168 */     return this.image;
/*     */   }
/*     */   
/*     */   private static int[] longArrayToIntArray(long[] paramArrayOflong) {
/* 172 */     int[] arrayOfInt = new int[paramArrayOflong.length];
/* 173 */     for (byte b = 0; b < paramArrayOflong.length; b++)
/*     */     {
/*     */ 
/*     */       
/* 177 */       arrayOfInt[b] = (int)paramArrayOflong[b];
/*     */     }
/* 179 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   private static long[] intArrayToLongArray(int[] paramArrayOfint) {
/* 183 */     long[] arrayOfLong = new long[paramArrayOfint.length];
/* 184 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 185 */       arrayOfLong[b] = paramArrayOfint[b];
/*     */     }
/* 187 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   
/*     */   static Image intArrayToImage(int[] paramArrayOfint) {
/* 192 */     DirectColorModel directColorModel = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, false, 3);
/*     */ 
/*     */     
/* 195 */     DataBufferInt dataBufferInt = new DataBufferInt(paramArrayOfint, paramArrayOfint.length - 2, 2);
/*     */     
/* 197 */     WritableRaster writableRaster = Raster.createPackedRaster(dataBufferInt, paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[0], new int[] { 16711680, 65280, 255, -16777216 }, (Point)null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     return new BufferedImage(directColorModel, writableRaster, false, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] imageToIntArray(Image paramImage, int paramInt1, int paramInt2) {
/* 211 */     if (paramInt1 <= 0 || paramInt2 <= 0) {
/* 212 */       return null;
/*     */     }
/*     */     
/* 215 */     DirectColorModel directColorModel = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, false, 3);
/*     */ 
/*     */     
/* 218 */     DataBufferInt dataBufferInt = new DataBufferInt(paramInt1 * paramInt2);
/*     */     
/* 220 */     WritableRaster writableRaster = Raster.createPackedRaster(dataBufferInt, paramInt1, paramInt2, paramInt1, new int[] { 16711680, 65280, 255, -16777216 }, (Point)null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     BufferedImage bufferedImage = new BufferedImage(directColorModel, writableRaster, false, null);
/* 226 */     Graphics graphics = bufferedImage.getGraphics();
/* 227 */     graphics.drawImage(paramImage, 0, 0, paramInt1, paramInt2, null);
/* 228 */     graphics.dispose();
/* 229 */     int[] arrayOfInt1 = dataBufferInt.getData();
/* 230 */     int[] arrayOfInt2 = new int[paramInt1 * paramInt2 + 2];
/* 231 */     arrayOfInt2[0] = paramInt1;
/* 232 */     arrayOfInt2[1] = paramInt2;
/* 233 */     System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 2, paramInt1 * paramInt2);
/* 234 */     return arrayOfInt2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/IconInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */