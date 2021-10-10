/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.Color;
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
/*     */ public class XRColor
/*     */ {
/*  37 */   public static final XRColor FULL_ALPHA = new XRColor(65535, 0, 0, 0);
/*  38 */   public static final XRColor NO_ALPHA = new XRColor(0, 0, 0, 0);
/*     */   int red;
/*     */   int green;
/*     */   
/*     */   public XRColor() {
/*  43 */     this.red = 0;
/*  44 */     this.green = 0;
/*  45 */     this.blue = 0;
/*  46 */     this.alpha = 0;
/*     */   }
/*     */   int blue; int alpha;
/*     */   public XRColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  50 */     this.alpha = paramInt1;
/*  51 */     this.red = paramInt2;
/*  52 */     this.green = paramInt3;
/*  53 */     this.blue = paramInt4;
/*     */   }
/*     */   
/*     */   public XRColor(Color paramColor) {
/*  57 */     setColorValues(paramColor);
/*     */   }
/*     */   
/*     */   public void setColorValues(Color paramColor) {
/*  61 */     this.alpha = byteToXRColorValue(paramColor.getAlpha());
/*     */     
/*  63 */     this.red = byteToXRColorValue(
/*  64 */         (int)((paramColor.getRed() * paramColor.getAlpha()) / 255.0D));
/*  65 */     this.green = byteToXRColorValue(
/*  66 */         (int)((paramColor.getGreen() * paramColor.getAlpha()) / 255.0D));
/*  67 */     this.blue = byteToXRColorValue(
/*  68 */         (int)((paramColor.getBlue() * paramColor.getAlpha()) / 255.0D));
/*     */   }
/*     */   
/*     */   public static int[] ARGBPrePixelToXRColors(int[] paramArrayOfint) {
/*  72 */     int[] arrayOfInt = new int[paramArrayOfint.length * 4];
/*  73 */     XRColor xRColor = new XRColor();
/*     */     
/*  75 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/*  76 */       xRColor.setColorValues(paramArrayOfint[b], true);
/*  77 */       arrayOfInt[b * 4 + 0] = xRColor.alpha;
/*  78 */       arrayOfInt[b * 4 + 1] = xRColor.red;
/*  79 */       arrayOfInt[b * 4 + 2] = xRColor.green;
/*  80 */       arrayOfInt[b * 4 + 3] = xRColor.blue;
/*     */     } 
/*     */     
/*  83 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */   public void setColorValues(int paramInt, boolean paramBoolean) {
/*  87 */     long l = XRUtils.intToULong(paramInt);
/*  88 */     this.alpha = (int)(((l & 0xFFFFFFFFFF000000L) >> 16L) + 255L);
/*  89 */     this.red = (int)(((l & 0xFF0000L) >> 8L) + 255L);
/*  90 */     this.green = (int)(((l & 0xFF00L) >> 0L) + 255L);
/*  91 */     this.blue = (int)(((l & 0xFFL) << 8L) + 255L);
/*     */     
/*  93 */     if (this.alpha == 255) {
/*  94 */       this.alpha = 0;
/*     */     }
/*     */     
/*  97 */     if (!paramBoolean) {
/*  98 */       double d = XRUtils.XFixedToDouble(this.alpha);
/*  99 */       this.red = (int)(this.red * d);
/* 100 */       this.green = (int)(this.green * d);
/* 101 */       this.blue = (int)(this.blue * d);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int byteToXRColorValue(int paramInt) {
/* 106 */     int i = 0;
/*     */     
/* 108 */     if (paramInt != 0) {
/* 109 */       if (paramInt == 255) {
/* 110 */         i = 65535;
/*     */       } else {
/* 112 */         i = (paramInt << 8) + 255;
/*     */       } 
/*     */     }
/*     */     
/* 116 */     return i;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 120 */     return "A:" + this.alpha + "  R:" + this.red + "  G:" + this.green + " B:" + this.blue;
/*     */   }
/*     */   
/*     */   public void setAlpha(int paramInt) {
/* 124 */     this.alpha = paramInt;
/*     */   }
/*     */   
/*     */   public int getAlpha() {
/* 128 */     return this.alpha;
/*     */   }
/*     */   
/*     */   public int getRed() {
/* 132 */     return this.red;
/*     */   }
/*     */   
/*     */   public int getGreen() {
/* 136 */     return this.green;
/*     */   }
/*     */   
/*     */   public int getBlue() {
/* 140 */     return this.blue;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */