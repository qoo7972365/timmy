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
/*     */ 
/*     */ 
/*     */ public class GrowableByteArray
/*     */ {
/*     */   byte[] array;
/*     */   int size;
/*     */   int cellSize;
/*     */   
/*     */   public GrowableByteArray(int paramInt1, int paramInt2) {
/*  46 */     this.array = new byte[paramInt2];
/*  47 */     this.size = 0;
/*  48 */     this.cellSize = paramInt1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNextCellIndex() {
/*  53 */     int i = this.size;
/*  54 */     this.size += this.cellSize;
/*     */     
/*  56 */     if (this.size >= this.array.length)
/*     */     {
/*  58 */       growArray();
/*     */     }
/*     */     
/*  61 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getArray() {
/*  69 */     return this.array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getSizedArray() {
/*  77 */     return Arrays.copyOf(this.array, getSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getByte(int paramInt) {
/*  82 */     return this.array[getCellIndex(paramInt)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getNextIndex() {
/*  91 */     return getNextCellIndex() / this.cellSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final int getCellIndex(int paramInt) {
/*  96 */     return this.cellSize * paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void addByte(byte paramByte) {
/* 101 */     int i = getNextIndex();
/* 102 */     this.array[i] = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/* 110 */     return this.size / this.cellSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 115 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void growArray() {
/* 120 */     int i = Math.max(this.array.length * 2, 10);
/* 121 */     byte[] arrayOfByte = this.array;
/* 122 */     this.array = new byte[i];
/*     */     
/* 124 */     System.arraycopy(arrayOfByte, 0, this.array, 0, arrayOfByte.length);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/GrowableByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */