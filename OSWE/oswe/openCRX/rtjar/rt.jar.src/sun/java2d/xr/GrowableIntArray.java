/*     */ package sun.java2d.xr;
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
/*     */ public class GrowableIntArray
/*     */ {
/*     */   int[] array;
/*     */   int size;
/*     */   int cellSize;
/*     */   
/*     */   public GrowableIntArray(int paramInt1, int paramInt2) {
/*  44 */     this.array = new int[paramInt2];
/*  45 */     this.size = 0;
/*  46 */     this.cellSize = paramInt1;
/*     */   }
/*     */   
/*     */   private int getNextCellIndex() {
/*  50 */     int i = this.size;
/*  51 */     this.size += this.cellSize;
/*     */     
/*  53 */     if (this.size >= this.array.length) {
/*  54 */       growArray();
/*     */     }
/*     */     
/*  57 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getArray() {
/*  64 */     return this.array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSizedArray() {
/*  71 */     return Arrays.copyOf(this.array, getSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNextIndex() {
/*  79 */     return getNextCellIndex() / this.cellSize;
/*     */   }
/*     */   
/*     */   protected final int getCellIndex(int paramInt) {
/*  83 */     return this.cellSize * paramInt;
/*     */   }
/*     */   
/*     */   public final int getInt(int paramInt) {
/*  87 */     return this.array[paramInt];
/*     */   }
/*     */   
/*     */   public final void addInt(int paramInt) {
/*  91 */     int i = getNextIndex();
/*  92 */     this.array[i] = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/*  99 */     return this.size / this.cellSize;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 103 */     this.size = 0;
/*     */   }
/*     */   
/*     */   protected void growArray() {
/* 107 */     int i = Math.max(this.array.length * 2, 10);
/* 108 */     int[] arrayOfInt = this.array;
/* 109 */     this.array = new int[i];
/*     */     
/* 111 */     System.arraycopy(arrayOfInt, 0, this.array, 0, arrayOfInt.length);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/GrowableIntArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */