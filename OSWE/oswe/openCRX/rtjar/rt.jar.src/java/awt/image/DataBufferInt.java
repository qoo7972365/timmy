/*     */ package java.awt.image;
/*     */ 
/*     */ import sun.java2d.StateTrackable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataBufferInt
/*     */   extends DataBuffer
/*     */ {
/*     */   int[] data;
/*     */   int[][] bankdata;
/*     */   
/*     */   public DataBufferInt(int paramInt) {
/*  74 */     super(StateTrackable.State.STABLE, 3, paramInt);
/*  75 */     this.data = new int[paramInt];
/*  76 */     this.bankdata = new int[1][];
/*  77 */     this.bankdata[0] = this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataBufferInt(int paramInt1, int paramInt2) {
/*  88 */     super(StateTrackable.State.STABLE, 3, paramInt1, paramInt2);
/*  89 */     this.bankdata = new int[paramInt2][];
/*  90 */     for (byte b = 0; b < paramInt2; b++) {
/*  91 */       this.bankdata[b] = new int[paramInt1];
/*     */     }
/*  93 */     this.data = this.bankdata[0];
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
/*     */   public DataBufferInt(int[] paramArrayOfint, int paramInt) {
/* 112 */     super(StateTrackable.State.UNTRACKABLE, 3, paramInt);
/* 113 */     this.data = paramArrayOfint;
/* 114 */     this.bankdata = new int[1][];
/* 115 */     this.bankdata[0] = this.data;
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
/*     */   public DataBufferInt(int[] paramArrayOfint, int paramInt1, int paramInt2) {
/* 135 */     super(StateTrackable.State.UNTRACKABLE, 3, paramInt1, 1, paramInt2);
/* 136 */     this.data = paramArrayOfint;
/* 137 */     this.bankdata = new int[1][];
/* 138 */     this.bankdata[0] = this.data;
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
/*     */   public DataBufferInt(int[][] paramArrayOfint, int paramInt) {
/* 156 */     super(StateTrackable.State.UNTRACKABLE, 3, paramInt, paramArrayOfint.length);
/* 157 */     this.bankdata = (int[][])paramArrayOfint.clone();
/* 158 */     this.data = this.bankdata[0];
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
/*     */   public DataBufferInt(int[][] paramArrayOfint, int paramInt, int[] paramArrayOfint1) {
/* 181 */     super(StateTrackable.State.UNTRACKABLE, 3, paramInt, paramArrayOfint.length, paramArrayOfint1);
/* 182 */     this.bankdata = (int[][])paramArrayOfint.clone();
/* 183 */     this.data = this.bankdata[0];
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
/*     */   public int[] getData() {
/* 197 */     this.theTrackable.setUntrackable();
/* 198 */     return this.data;
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
/*     */   public int[] getData(int paramInt) {
/* 213 */     this.theTrackable.setUntrackable();
/* 214 */     return this.bankdata[paramInt];
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
/*     */   public int[][] getBankData() {
/* 228 */     this.theTrackable.setUntrackable();
/* 229 */     return (int[][])this.bankdata.clone();
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
/*     */   public int getElem(int paramInt) {
/* 241 */     return this.data[paramInt + this.offset];
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
/*     */   public int getElem(int paramInt1, int paramInt2) {
/* 254 */     return this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]];
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
/*     */   public void setElem(int paramInt1, int paramInt2) {
/* 267 */     this.data[paramInt1 + this.offset] = paramInt2;
/* 268 */     this.theTrackable.markDirty();
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
/*     */   public void setElem(int paramInt1, int paramInt2, int paramInt3) {
/* 281 */     this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]] = paramInt3;
/* 282 */     this.theTrackable.markDirty();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/DataBufferInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */