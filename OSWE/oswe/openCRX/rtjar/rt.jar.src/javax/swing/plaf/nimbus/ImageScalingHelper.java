/*     */ package javax.swing.plaf.nimbus;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Insets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ImageScalingHelper
/*     */ {
/*     */   enum PaintType
/*     */   {
/*  44 */     CENTER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     TILE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     PAINT9_STRETCH,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     PAINT9_TILE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
/*     */ 
/*     */ 
/*     */   
/*     */   static final int PAINT_TOP_LEFT = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int PAINT_TOP = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   static final int PAINT_TOP_RIGHT = 4;
/*     */ 
/*     */   
/*     */   static final int PAINT_LEFT = 8;
/*     */ 
/*     */   
/*     */   static final int PAINT_CENTER = 16;
/*     */ 
/*     */   
/*     */   static final int PAINT_RIGHT = 32;
/*     */ 
/*     */   
/*     */   static final int PAINT_BOTTOM_RIGHT = 64;
/*     */ 
/*     */   
/*     */   static final int PAINT_BOTTOM = 128;
/*     */ 
/*     */   
/*     */   static final int PAINT_BOTTOM_LEFT = 256;
/*     */ 
/*     */   
/*     */   static final int PAINT_ALL = 512;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void paint(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Image paramImage, Insets paramInsets1, Insets paramInsets2, PaintType paramPaintType, int paramInt5) {
/* 105 */     if (paramImage == null || paramImage.getWidth(null) <= 0 || paramImage.getHeight(null) <= 0) {
/*     */       return;
/*     */     }
/* 108 */     if (paramInsets1 == null) {
/* 109 */       paramInsets1 = EMPTY_INSETS;
/*     */     }
/* 111 */     if (paramInsets2 == null) {
/* 112 */       paramInsets2 = EMPTY_INSETS;
/*     */     }
/* 114 */     int i = paramImage.getWidth(null);
/* 115 */     int j = paramImage.getHeight(null);
/*     */     
/* 117 */     if (paramPaintType == PaintType.CENTER) {
/*     */       
/* 119 */       paramGraphics.drawImage(paramImage, paramInt1 + (paramInt3 - i) / 2, paramInt2 + (paramInt4 - j) / 2, null);
/*     */     }
/* 121 */     else if (paramPaintType == PaintType.TILE) {
/*     */       
/* 123 */       byte b = 0;
/* 124 */       for (int k = paramInt2, m = paramInt2 + paramInt4; k < m; 
/* 125 */         k += j - b, b = 0) {
/* 126 */         byte b1 = 0;
/* 127 */         for (int n = paramInt1, i1 = paramInt1 + paramInt3; n < i1; 
/* 128 */           n += i - b1, b1 = 0) {
/* 129 */           int i2 = Math.min(i1, n + i - b1);
/* 130 */           int i3 = Math.min(m, k + j - b);
/* 131 */           paramGraphics.drawImage(paramImage, n, k, i2, i3, b1, b, b1 + i2 - n, b + i3 - k, null);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 137 */       int k = paramInsets1.top;
/* 138 */       int m = paramInsets1.left;
/* 139 */       int n = paramInsets1.bottom;
/* 140 */       int i1 = paramInsets1.right;
/*     */       
/* 142 */       int i2 = paramInsets2.top;
/* 143 */       int i3 = paramInsets2.left;
/* 144 */       int i4 = paramInsets2.bottom;
/* 145 */       int i5 = paramInsets2.right;
/*     */ 
/*     */       
/* 148 */       if (k + n > j) {
/* 149 */         i4 = i2 = n = k = Math.max(0, j / 2);
/*     */       }
/* 151 */       if (m + i1 > i) {
/* 152 */         i3 = i5 = m = i1 = Math.max(0, i / 2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 157 */       if (i2 + i4 > paramInt4) {
/* 158 */         i2 = i4 = Math.max(0, paramInt4 / 2 - 1);
/*     */       }
/* 160 */       if (i3 + i5 > paramInt3) {
/* 161 */         i3 = i5 = Math.max(0, paramInt3 / 2 - 1);
/*     */       }
/*     */       
/* 164 */       boolean bool = (paramPaintType == PaintType.PAINT9_STRETCH) ? true : false;
/* 165 */       if ((paramInt5 & 0x200) != 0) {
/* 166 */         paramInt5 = 0x1FF & (paramInt5 ^ 0xFFFFFFFF);
/*     */       }
/*     */       
/* 169 */       if ((paramInt5 & 0x8) != 0) {
/* 170 */         drawChunk(paramImage, paramGraphics, bool, paramInt1, paramInt2 + i2, paramInt1 + i3, paramInt2 + paramInt4 - i4, 0, k, m, j - n, false);
/*     */       }
/*     */       
/* 173 */       if ((paramInt5 & 0x1) != 0) {
/* 174 */         drawImage(paramImage, paramGraphics, paramInt1, paramInt2, paramInt1 + i3, paramInt2 + i2, 0, 0, m, k);
/*     */       }
/*     */       
/* 177 */       if ((paramInt5 & 0x2) != 0) {
/* 178 */         drawChunk(paramImage, paramGraphics, bool, paramInt1 + i3, paramInt2, paramInt1 + paramInt3 - i5, paramInt2 + i2, m, 0, i - i1, k, true);
/*     */       }
/*     */       
/* 181 */       if ((paramInt5 & 0x4) != 0) {
/* 182 */         drawImage(paramImage, paramGraphics, paramInt1 + paramInt3 - i5, paramInt2, paramInt1 + paramInt3, paramInt2 + i2, i - i1, 0, i, k);
/*     */       }
/*     */       
/* 185 */       if ((paramInt5 & 0x20) != 0) {
/* 186 */         drawChunk(paramImage, paramGraphics, bool, paramInt1 + paramInt3 - i5, paramInt2 + i2, paramInt1 + paramInt3, paramInt2 + paramInt4 - i4, i - i1, k, i, j - n, false);
/*     */       }
/*     */ 
/*     */       
/* 190 */       if ((paramInt5 & 0x40) != 0) {
/* 191 */         drawImage(paramImage, paramGraphics, paramInt1 + paramInt3 - i5, paramInt2 + paramInt4 - i4, paramInt1 + paramInt3, paramInt2 + paramInt4, i - i1, j - n, i, j);
/*     */       }
/*     */       
/* 194 */       if ((paramInt5 & 0x80) != 0) {
/* 195 */         drawChunk(paramImage, paramGraphics, bool, paramInt1 + i3, paramInt2 + paramInt4 - i4, paramInt1 + paramInt3 - i5, paramInt2 + paramInt4, m, j - n, i - i1, j, true);
/*     */       }
/*     */ 
/*     */       
/* 199 */       if ((paramInt5 & 0x100) != 0) {
/* 200 */         drawImage(paramImage, paramGraphics, paramInt1, paramInt2 + paramInt4 - i4, paramInt1 + i3, paramInt2 + paramInt4, 0, j - n, m, j);
/*     */       }
/*     */       
/* 203 */       if ((paramInt5 & 0x10) != 0) {
/* 204 */         drawImage(paramImage, paramGraphics, paramInt1 + i3, paramInt2 + i2, paramInt1 + paramInt3 - i5, paramInt2 + paramInt4 - i4, m, k, i - i1, j - n);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawChunk(Image paramImage, Graphics paramGraphics, boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean2) {
/* 232 */     if (paramInt3 - paramInt1 <= 0 || paramInt4 - paramInt2 <= 0 || paramInt7 - paramInt5 <= 0 || paramInt8 - paramInt6 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 237 */     if (paramBoolean1) {
/* 238 */       paramGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, null);
/*     */     } else {
/*     */       byte b;
/* 241 */       int k, i = paramInt7 - paramInt5;
/* 242 */       int j = paramInt8 - paramInt6;
/*     */ 
/*     */ 
/*     */       
/* 246 */       if (paramBoolean2) {
/* 247 */         b = i;
/* 248 */         k = 0;
/*     */       } else {
/*     */         
/* 251 */         b = 0;
/* 252 */         k = j;
/*     */       } 
/* 254 */       while (paramInt1 < paramInt3 && paramInt2 < paramInt4) {
/* 255 */         int m = Math.min(paramInt3, paramInt1 + i);
/* 256 */         int n = Math.min(paramInt4, paramInt2 + j);
/*     */         
/* 258 */         paramGraphics.drawImage(paramImage, paramInt1, paramInt2, m, n, paramInt5, paramInt6, paramInt5 + m - paramInt1, paramInt6 + n - paramInt2, null);
/*     */ 
/*     */         
/* 261 */         paramInt1 += b;
/* 262 */         paramInt2 += k;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void drawImage(Image paramImage, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/* 271 */     if (paramInt3 - paramInt1 <= 0 || paramInt4 - paramInt2 <= 0 || paramInt7 - paramInt5 <= 0 || paramInt8 - paramInt6 <= 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 276 */     paramGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/nimbus/ImageScalingHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */