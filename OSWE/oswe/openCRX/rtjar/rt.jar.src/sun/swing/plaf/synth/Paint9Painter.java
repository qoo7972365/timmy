/*     */ package sun.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ import java.awt.image.BufferedImage;
/*     */ import sun.swing.CachedPainter;
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
/*     */ public class Paint9Painter
/*     */   extends CachedPainter
/*     */ {
/*     */   public enum PaintType
/*     */   {
/*  45 */     CENTER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     TILE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     PAINT9_STRETCH,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     PAINT9_TILE;
/*     */   }
/*     */   
/*  67 */   private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/*     */ 
/*     */   
/*     */   public static final int PAINT_TOP_LEFT = 1;
/*     */ 
/*     */   
/*     */   public static final int PAINT_TOP = 2;
/*     */   
/*     */   public static final int PAINT_TOP_RIGHT = 4;
/*     */   
/*     */   public static final int PAINT_LEFT = 8;
/*     */   
/*     */   public static final int PAINT_CENTER = 16;
/*     */   
/*     */   public static final int PAINT_RIGHT = 32;
/*     */   
/*     */   public static final int PAINT_BOTTOM_RIGHT = 64;
/*     */   
/*     */   public static final int PAINT_BOTTOM = 128;
/*     */   
/*     */   public static final int PAINT_BOTTOM_LEFT = 256;
/*     */   
/*     */   public static final int PAINT_ALL = 512;
/*     */ 
/*     */   
/*     */   public static boolean validImage(Image paramImage) {
/*  93 */     return (paramImage != null && paramImage.getWidth(null) > 0 && paramImage
/*  94 */       .getHeight(null) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Paint9Painter(int paramInt) {
/*  99 */     super(paramInt);
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
/*     */   public void paint(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Image paramImage, Insets paramInsets1, Insets paramInsets2, PaintType paramPaintType, int paramInt5) {
/* 131 */     if (paramImage == null) {
/*     */       return;
/*     */     }
/* 134 */     paint(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, new Object[] { paramImage, paramInsets1, paramInsets2, paramPaintType, Integer.valueOf(paramInt5) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void paintToImage(Component paramComponent, Image paramImage, Graphics paramGraphics, int paramInt1, int paramInt2, Object[] paramArrayOfObject) {
/* 139 */     byte b = 0;
/* 140 */     while (b < paramArrayOfObject.length) {
/* 141 */       Image image = (Image)paramArrayOfObject[b++];
/* 142 */       Insets insets1 = (Insets)paramArrayOfObject[b++];
/* 143 */       Insets insets2 = (Insets)paramArrayOfObject[b++];
/* 144 */       PaintType paintType = (PaintType)paramArrayOfObject[b++];
/* 145 */       int i = ((Integer)paramArrayOfObject[b++]).intValue();
/* 146 */       paint9(paramGraphics, 0, 0, paramInt1, paramInt2, image, insets1, insets2, paintType, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint9(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Image paramImage, Insets paramInsets1, Insets paramInsets2, PaintType paramPaintType, int paramInt5) {
/* 153 */     if (!validImage(paramImage)) {
/*     */       return;
/*     */     }
/* 156 */     if (paramInsets1 == null) {
/* 157 */       paramInsets1 = EMPTY_INSETS;
/*     */     }
/* 159 */     if (paramInsets2 == null) {
/* 160 */       paramInsets2 = EMPTY_INSETS;
/*     */     }
/* 162 */     int i = paramImage.getWidth(null);
/* 163 */     int j = paramImage.getHeight(null);
/*     */     
/* 165 */     if (paramPaintType == PaintType.CENTER) {
/*     */       
/* 167 */       paramGraphics.drawImage(paramImage, paramInt1 + (paramInt3 - i) / 2, paramInt2 + (paramInt4 - j) / 2, null);
/*     */     
/*     */     }
/* 170 */     else if (paramPaintType == PaintType.TILE) {
/*     */       
/* 172 */       byte b = 0;
/* 173 */       for (int k = paramInt2, m = paramInt2 + paramInt4; k < m; 
/* 174 */         k += j - b, b = 0) {
/* 175 */         byte b1 = 0;
/* 176 */         for (int n = paramInt1, i1 = paramInt1 + paramInt3; n < i1; 
/* 177 */           n += i - b1, b1 = 0) {
/* 178 */           int i2 = Math.min(i1, n + i - b1);
/* 179 */           int i3 = Math.min(m, k + j - b);
/* 180 */           paramGraphics.drawImage(paramImage, n, k, i2, i3, b1, b, b1 + i2 - n, b + i3 - k, null);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 187 */       int k = paramInsets1.top;
/* 188 */       int m = paramInsets1.left;
/* 189 */       int n = paramInsets1.bottom;
/* 190 */       int i1 = paramInsets1.right;
/*     */       
/* 192 */       int i2 = paramInsets2.top;
/* 193 */       int i3 = paramInsets2.left;
/* 194 */       int i4 = paramInsets2.bottom;
/* 195 */       int i5 = paramInsets2.right;
/*     */ 
/*     */       
/* 198 */       if (k + n > j) {
/* 199 */         i4 = i2 = n = k = Math.max(0, j / 2);
/*     */       }
/* 201 */       if (m + i1 > i) {
/* 202 */         i3 = i5 = m = i1 = Math.max(0, i / 2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 207 */       if (i2 + i4 > paramInt4) {
/* 208 */         i2 = i4 = Math.max(0, paramInt4 / 2 - 1);
/*     */       }
/* 210 */       if (i3 + i5 > paramInt3) {
/* 211 */         i3 = i5 = Math.max(0, paramInt3 / 2 - 1);
/*     */       }
/*     */       
/* 214 */       boolean bool = (paramPaintType == PaintType.PAINT9_STRETCH) ? true : false;
/* 215 */       if ((paramInt5 & 0x200) != 0) {
/* 216 */         paramInt5 = 0x1FF & (paramInt5 ^ 0xFFFFFFFF);
/*     */       }
/*     */       
/* 219 */       if ((paramInt5 & 0x8) != 0) {
/* 220 */         drawChunk(paramImage, paramGraphics, bool, paramInt1, paramInt2 + i2, paramInt1 + i3, paramInt2 + paramInt4 - i4, 0, k, m, j - n, false);
/*     */       }
/*     */       
/* 223 */       if ((paramInt5 & 0x1) != 0) {
/* 224 */         drawImage(paramImage, paramGraphics, paramInt1, paramInt2, paramInt1 + i3, paramInt2 + i2, 0, 0, m, k);
/*     */       }
/*     */       
/* 227 */       if ((paramInt5 & 0x2) != 0) {
/* 228 */         drawChunk(paramImage, paramGraphics, bool, paramInt1 + i3, paramInt2, paramInt1 + paramInt3 - i5, paramInt2 + i2, m, 0, i - i1, k, true);
/*     */       }
/*     */       
/* 231 */       if ((paramInt5 & 0x4) != 0) {
/* 232 */         drawImage(paramImage, paramGraphics, paramInt1 + paramInt3 - i5, paramInt2, paramInt1 + paramInt3, paramInt2 + i2, i - i1, 0, i, k);
/*     */       }
/*     */       
/* 235 */       if ((paramInt5 & 0x20) != 0) {
/* 236 */         drawChunk(paramImage, paramGraphics, bool, paramInt1 + paramInt3 - i5, paramInt2 + i2, paramInt1 + paramInt3, paramInt2 + paramInt4 - i4, i - i1, k, i, j - n, false);
/*     */       }
/*     */ 
/*     */       
/* 240 */       if ((paramInt5 & 0x40) != 0) {
/* 241 */         drawImage(paramImage, paramGraphics, paramInt1 + paramInt3 - i5, paramInt2 + paramInt4 - i4, paramInt1 + paramInt3, paramInt2 + paramInt4, i - i1, j - n, i, j);
/*     */       }
/*     */       
/* 244 */       if ((paramInt5 & 0x80) != 0) {
/* 245 */         drawChunk(paramImage, paramGraphics, bool, paramInt1 + i3, paramInt2 + paramInt4 - i4, paramInt1 + paramInt3 - i5, paramInt2 + paramInt4, m, j - n, i - i1, j, true);
/*     */       }
/*     */ 
/*     */       
/* 249 */       if ((paramInt5 & 0x100) != 0) {
/* 250 */         drawImage(paramImage, paramGraphics, paramInt1, paramInt2 + paramInt4 - i4, paramInt1 + i3, paramInt2 + paramInt4, 0, j - n, m, j);
/*     */       }
/*     */       
/* 253 */       if ((paramInt5 & 0x10) != 0) {
/* 254 */         drawImage(paramImage, paramGraphics, paramInt1 + i3, paramInt2 + i2, paramInt1 + paramInt3 - i5, paramInt2 + paramInt4 - i4, m, k, i - i1, j - n);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawImage(Image paramImage, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 264 */     if (paramInt3 - paramInt1 <= 0 || paramInt4 - paramInt2 <= 0 || paramInt7 - paramInt5 <= 0 || paramInt8 - paramInt6 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 269 */     paramGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawChunk(Image paramImage, Graphics paramGraphics, boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean2) {
/* 294 */     if (paramInt3 - paramInt1 <= 0 || paramInt4 - paramInt2 <= 0 || paramInt7 - paramInt5 <= 0 || paramInt8 - paramInt6 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 299 */     if (paramBoolean1) {
/* 300 */       paramGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, null);
/*     */     } else {
/*     */       byte b;
/* 303 */       int k, i = paramInt7 - paramInt5;
/* 304 */       int j = paramInt8 - paramInt6;
/*     */ 
/*     */ 
/*     */       
/* 308 */       if (paramBoolean2) {
/* 309 */         b = i;
/* 310 */         k = 0;
/*     */       } else {
/*     */         
/* 313 */         b = 0;
/* 314 */         k = j;
/*     */       } 
/* 316 */       while (paramInt1 < paramInt3 && paramInt2 < paramInt4) {
/* 317 */         int m = Math.min(paramInt3, paramInt1 + i);
/* 318 */         int n = Math.min(paramInt4, paramInt2 + j);
/*     */         
/* 320 */         paramGraphics.drawImage(paramImage, paramInt1, paramInt2, m, n, paramInt5, paramInt6, paramInt5 + m - paramInt1, paramInt6 + n - paramInt2, null);
/*     */ 
/*     */         
/* 323 */         paramInt1 += b;
/* 324 */         paramInt2 += k;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Image createImage(Component paramComponent, int paramInt1, int paramInt2, GraphicsConfiguration paramGraphicsConfiguration, Object[] paramArrayOfObject) {
/* 335 */     if (paramGraphicsConfiguration == null) {
/* 336 */       return new BufferedImage(paramInt1, paramInt2, 2);
/*     */     }
/* 338 */     return paramGraphicsConfiguration.createCompatibleImage(paramInt1, paramInt2, 3);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/plaf/synth/Paint9Painter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */