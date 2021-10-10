/*     */ package sun.java2d.pisces;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import sun.java2d.pipe.AATileGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PiscesTileGenerator
/*     */   implements AATileGenerator
/*     */ {
/*     */   public static final int TILE_SIZE = 32;
/*  40 */   private static final Map<Integer, byte[]> alphaMapsCache = (Map)new ConcurrentHashMap<>();
/*     */   
/*     */   PiscesCache cache;
/*     */   
/*     */   int x;
/*     */   
/*     */   int y;
/*     */   
/*     */   final int maxalpha;
/*     */   
/*     */   private final int maxTileAlphaSum;
/*     */   byte[] alphaMap;
/*     */   
/*     */   public PiscesTileGenerator(Renderer paramRenderer, int paramInt) {
/*  54 */     this.cache = paramRenderer.getCache();
/*  55 */     this.x = this.cache.bboxX0;
/*  56 */     this.y = this.cache.bboxY0;
/*  57 */     this.alphaMap = getAlphaMap(paramInt);
/*  58 */     this.maxalpha = paramInt;
/*  59 */     this.maxTileAlphaSum = 1024 * paramInt;
/*     */   }
/*     */   
/*     */   private static byte[] buildAlphaMap(int paramInt) {
/*  63 */     byte[] arrayOfByte = new byte[paramInt + 1];
/*  64 */     int i = paramInt >> 2;
/*  65 */     for (byte b = 0; b <= paramInt; b++) {
/*  66 */       arrayOfByte[b] = (byte)((b * 255 + i) / paramInt);
/*     */     }
/*  68 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public static byte[] getAlphaMap(int paramInt) {
/*  72 */     if (!alphaMapsCache.containsKey(Integer.valueOf(paramInt))) {
/*  73 */       alphaMapsCache.put(Integer.valueOf(paramInt), buildAlphaMap(paramInt));
/*     */     }
/*  75 */     return alphaMapsCache.get(Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public void getBbox(int[] paramArrayOfint) {
/*  79 */     paramArrayOfint[0] = this.cache.bboxX0;
/*  80 */     paramArrayOfint[1] = this.cache.bboxY0;
/*  81 */     paramArrayOfint[2] = this.cache.bboxX1;
/*  82 */     paramArrayOfint[3] = this.cache.bboxY1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileWidth() {
/*  91 */     return 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTileHeight() {
/*  99 */     return 32;
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
/*     */   public int getTypicalAlpha() {
/* 113 */     int i = this.cache.alphaSumInTile(this.x, this.y);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     return (i == 0) ? 0 : ((i == this.maxTileAlphaSum) ? 255 : 128);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextTile() {
/* 139 */     if ((this.x += 32) >= this.cache.bboxX1) {
/* 140 */       this.x = this.cache.bboxX0;
/* 141 */       this.y += 32;
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
/*     */   public void getAlpha(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 156 */     int i = this.x;
/* 157 */     int j = i + 32;
/* 158 */     int k = this.y;
/* 159 */     int m = k + 32;
/* 160 */     if (j > this.cache.bboxX1) j = this.cache.bboxX1; 
/* 161 */     if (m > this.cache.bboxY1) m = this.cache.bboxY1; 
/* 162 */     k -= this.cache.bboxY0;
/* 163 */     m -= this.cache.bboxY0;
/*     */     
/* 165 */     int n = paramInt1;
/* 166 */     for (int i1 = k; i1 < m; i1++) {
/* 167 */       int[] arrayOfInt = this.cache.rowAARLE[i1];
/* 168 */       assert arrayOfInt != null;
/* 169 */       int i2 = this.cache.minTouched(i1);
/* 170 */       if (i2 > j) i2 = j; 
/*     */       int i3;
/* 172 */       for (i3 = i; i3 < i2; i3++) {
/* 173 */         paramArrayOfbyte[n++] = 0;
/*     */       }
/*     */       
/* 176 */       i3 = 2;
/* 177 */       while (i2 < j && i3 < arrayOfInt[1]) {
/*     */         byte b;
/* 179 */         int i4 = 0;
/* 180 */         assert arrayOfInt[1] > 2;
/*     */         try {
/* 182 */           b = this.alphaMap[arrayOfInt[i3]];
/* 183 */           i4 = arrayOfInt[i3 + 1];
/* 184 */           assert i4 > 0;
/* 185 */         } catch (RuntimeException runtimeException) {
/* 186 */           System.out.println("maxalpha = " + this.maxalpha);
/* 187 */           System.out.println("tile[" + i + ", " + k + " => " + j + ", " + m + "]");
/*     */           
/* 189 */           System.out.println("cx = " + i2 + ", cy = " + i1);
/* 190 */           System.out.println("idx = " + n + ", pos = " + i3);
/* 191 */           System.out.println("len = " + i4);
/* 192 */           System.out.print(this.cache.toString());
/* 193 */           runtimeException.printStackTrace();
/* 194 */           throw runtimeException;
/*     */         } 
/*     */         
/* 197 */         int i5 = i2;
/* 198 */         i2 += i4;
/* 199 */         int i6 = i2;
/* 200 */         if (i5 < i) i5 = i; 
/* 201 */         if (i6 > j) i6 = j; 
/* 202 */         i4 = i6 - i5;
/*     */         
/* 204 */         while (--i4 >= 0) {
/*     */           try {
/* 206 */             paramArrayOfbyte[n++] = b;
/* 207 */           } catch (RuntimeException runtimeException) {
/* 208 */             System.out.println("maxalpha = " + this.maxalpha);
/* 209 */             System.out.println("tile[" + i + ", " + k + " => " + j + ", " + m + "]");
/*     */             
/* 211 */             System.out.println("cx = " + i2 + ", cy = " + i1);
/* 212 */             System.out.println("idx = " + n + ", pos = " + i3);
/* 213 */             System.out.println("rx0 = " + i5 + ", rx1 = " + i6);
/* 214 */             System.out.println("len = " + i4);
/* 215 */             System.out.print(this.cache.toString());
/* 216 */             runtimeException.printStackTrace();
/* 217 */             throw runtimeException;
/*     */           } 
/*     */         } 
/* 220 */         i3 += 2;
/*     */       } 
/* 222 */       if (i2 < i) i2 = i; 
/* 223 */       while (i2 < j) {
/* 224 */         paramArrayOfbyte[n++] = 0;
/* 225 */         i2++;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 233 */       n += paramInt2 - j - i;
/*     */     } 
/* 235 */     nextTile();
/*     */   }
/*     */   
/*     */   static String hex(int paramInt1, int paramInt2) {
/* 239 */     String str = Integer.toHexString(paramInt1);
/* 240 */     while (str.length() < paramInt2) {
/* 241 */       str = "0" + str;
/*     */     }
/* 243 */     return str.substring(0, paramInt2);
/*     */   }
/*     */   
/*     */   public void dispose() {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/PiscesTileGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */