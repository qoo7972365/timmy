/*     */ package sun.java2d.pisces;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PiscesCache
/*     */ {
/*     */   final int bboxX0;
/*     */   final int bboxY0;
/*     */   final int bboxX1;
/*     */   final int bboxY1;
/*     */   final int[][] rowAARLE;
/*  51 */   private int x0 = Integer.MIN_VALUE; private int y0 = Integer.MIN_VALUE;
/*     */   
/*     */   private final int[][] touchedTile;
/*     */   
/*     */   static final int TILE_SIZE_LG = 5;
/*     */   
/*     */   static final int TILE_SIZE = 32;
/*     */   
/*     */   private static final int INIT_ROW_SIZE = 8;
/*     */   
/*     */   PiscesCache(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  62 */     assert paramInt4 >= paramInt2 && paramInt3 >= paramInt1;
/*  63 */     this.bboxX0 = paramInt1;
/*  64 */     this.bboxY0 = paramInt2;
/*  65 */     this.bboxX1 = paramInt3 + 1;
/*  66 */     this.bboxY1 = paramInt4 + 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     this.rowAARLE = new int[this.bboxY1 - this.bboxY0 + 1][8];
/*  72 */     this.x0 = 0;
/*  73 */     this.y0 = -1;
/*     */     
/*  75 */     int i = paramInt4 - paramInt2 + 32 >> 5;
/*  76 */     int j = paramInt3 - paramInt1 + 32 >> 5;
/*     */     
/*  78 */     this.touchedTile = new int[i][j];
/*     */   }
/*     */   
/*     */   void addRLERun(int paramInt1, int paramInt2) {
/*  82 */     if (paramInt2 > 0) {
/*  83 */       addTupleToRow(this.y0, paramInt1, paramInt2);
/*  84 */       if (paramInt1 != 0) {
/*     */         
/*  86 */         int i = this.x0 >> 5;
/*  87 */         int j = this.y0 >> 5;
/*  88 */         int k = this.x0 + paramInt2 - 1 >> 5;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  93 */         if (k >= (this.touchedTile[j]).length) {
/*  94 */           k = (this.touchedTile[j]).length - 1;
/*     */         }
/*  96 */         if (i <= k) {
/*  97 */           int m = i + 1 << 5;
/*  98 */           if (m > this.x0 + paramInt2) {
/*  99 */             this.touchedTile[j][i] = this.touchedTile[j][i] + paramInt1 * paramInt2;
/*     */           } else {
/* 101 */             this.touchedTile[j][i] = this.touchedTile[j][i] + paramInt1 * (m - this.x0);
/*     */           } 
/* 103 */           i++;
/*     */         } 
/*     */ 
/*     */         
/* 107 */         for (; i < k; i++)
/*     */         {
/* 109 */           this.touchedTile[j][i] = this.touchedTile[j][i] + (paramInt1 << 5);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 119 */         if (i == k) {
/* 120 */           int m = Math.min(this.x0 + paramInt2, i + 1 << 5);
/* 121 */           int n = i << 5;
/* 122 */           this.touchedTile[j][i] = this.touchedTile[j][i] + paramInt1 * (m - n);
/*     */         } 
/*     */       } 
/* 125 */       this.x0 += paramInt2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void startRow(int paramInt1, int paramInt2) {
/* 131 */     assert paramInt1 - this.bboxY0 > this.y0;
/* 132 */     assert paramInt1 <= this.bboxY1;
/*     */     
/* 134 */     this.y0 = paramInt1 - this.bboxY0;
/*     */     
/* 136 */     assert this.rowAARLE[this.y0][1] == 0;
/*     */     
/* 138 */     this.x0 = paramInt2 - this.bboxX0;
/* 139 */     assert this.x0 >= 0 : "Input must not be to the left of bbox bounds";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     this.rowAARLE[this.y0][0] = paramInt2;
/* 145 */     this.rowAARLE[this.y0][1] = 2;
/*     */   }
/*     */   
/*     */   int alphaSumInTile(int paramInt1, int paramInt2) {
/* 149 */     paramInt1 -= this.bboxX0;
/* 150 */     paramInt2 -= this.bboxY0;
/* 151 */     return this.touchedTile[paramInt2 >> 5][paramInt1 >> 5];
/*     */   }
/*     */   
/*     */   int minTouched(int paramInt) {
/* 155 */     return this.rowAARLE[paramInt][0];
/*     */   }
/*     */   
/*     */   int rowLength(int paramInt) {
/* 159 */     return this.rowAARLE[paramInt][1];
/*     */   }
/*     */   
/*     */   private void addTupleToRow(int paramInt1, int paramInt2, int paramInt3) {
/* 163 */     int i = this.rowAARLE[paramInt1][1];
/* 164 */     this.rowAARLE[paramInt1] = Helpers.widenArray(this.rowAARLE[paramInt1], i, 2);
/* 165 */     this.rowAARLE[paramInt1][i++] = paramInt2;
/* 166 */     this.rowAARLE[paramInt1][i++] = paramInt3;
/* 167 */     this.rowAARLE[paramInt1][1] = i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 172 */     String str = "bbox = [" + this.bboxX0 + ", " + this.bboxY0 + " => " + this.bboxX1 + ", " + this.bboxY1 + "]\n";
/*     */ 
/*     */     
/* 175 */     for (int[] arrayOfInt : this.rowAARLE) {
/* 176 */       if (arrayOfInt != null) {
/*     */         
/* 178 */         str = str + "minTouchedX=" + arrayOfInt[0] + "\tRLE Entries: " + Arrays.toString(
/* 179 */             Arrays.copyOfRange(arrayOfInt, 2, arrayOfInt[1])) + "\n";
/*     */       } else {
/* 181 */         str = str + "[]\n";
/*     */       } 
/*     */     } 
/* 184 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/PiscesCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */