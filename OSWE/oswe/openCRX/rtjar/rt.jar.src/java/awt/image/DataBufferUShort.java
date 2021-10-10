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
/*     */ 
/*     */ public final class DataBufferUShort
/*     */   extends DataBuffer
/*     */ {
/*     */   short[] data;
/*     */   short[][] bankdata;
/*     */   
/*     */   public DataBufferUShort(int paramInt) {
/*  75 */     super(StateTrackable.State.STABLE, 1, paramInt);
/*  76 */     this.data = new short[paramInt];
/*  77 */     this.bankdata = new short[1][];
/*  78 */     this.bankdata[0] = this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataBufferUShort(int paramInt1, int paramInt2) {
/*  89 */     super(StateTrackable.State.STABLE, 1, paramInt1, paramInt2);
/*  90 */     this.bankdata = new short[paramInt2][];
/*  91 */     for (byte b = 0; b < paramInt2; b++) {
/*  92 */       this.bankdata[b] = new short[paramInt1];
/*     */     }
/*  94 */     this.data = this.bankdata[0];
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
/*     */   public DataBufferUShort(short[] paramArrayOfshort, int paramInt) {
/* 113 */     super(StateTrackable.State.UNTRACKABLE, 1, paramInt);
/* 114 */     if (paramArrayOfshort == null) {
/* 115 */       throw new NullPointerException("dataArray is null");
/*     */     }
/* 117 */     this.data = paramArrayOfshort;
/* 118 */     this.bankdata = new short[1][];
/* 119 */     this.bankdata[0] = this.data;
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
/*     */   public DataBufferUShort(short[] paramArrayOfshort, int paramInt1, int paramInt2) {
/* 139 */     super(StateTrackable.State.UNTRACKABLE, 1, paramInt1, 1, paramInt2);
/* 140 */     if (paramArrayOfshort == null) {
/* 141 */       throw new NullPointerException("dataArray is null");
/*     */     }
/* 143 */     if (paramInt1 + paramInt2 > paramArrayOfshort.length) {
/* 144 */       throw new IllegalArgumentException("Length of dataArray is less  than size+offset.");
/*     */     }
/*     */     
/* 147 */     this.data = paramArrayOfshort;
/* 148 */     this.bankdata = new short[1][];
/* 149 */     this.bankdata[0] = this.data;
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
/*     */   public DataBufferUShort(short[][] paramArrayOfshort, int paramInt) {
/* 167 */     super(StateTrackable.State.UNTRACKABLE, 1, paramInt, paramArrayOfshort.length);
/* 168 */     if (paramArrayOfshort == null) {
/* 169 */       throw new NullPointerException("dataArray is null");
/*     */     }
/* 171 */     for (byte b = 0; b < paramArrayOfshort.length; b++) {
/* 172 */       if (paramArrayOfshort[b] == null) {
/* 173 */         throw new NullPointerException("dataArray[" + b + "] is null");
/*     */       }
/*     */     } 
/*     */     
/* 177 */     this.bankdata = (short[][])paramArrayOfshort.clone();
/* 178 */     this.data = this.bankdata[0];
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
/*     */   public DataBufferUShort(short[][] paramArrayOfshort, int paramInt, int[] paramArrayOfint) {
/* 201 */     super(StateTrackable.State.UNTRACKABLE, 1, paramInt, paramArrayOfshort.length, paramArrayOfint);
/* 202 */     if (paramArrayOfshort == null) {
/* 203 */       throw new NullPointerException("dataArray is null");
/*     */     }
/* 205 */     for (byte b = 0; b < paramArrayOfshort.length; b++) {
/* 206 */       if (paramArrayOfshort[b] == null) {
/* 207 */         throw new NullPointerException("dataArray[" + b + "] is null");
/*     */       }
/* 209 */       if (paramInt + paramArrayOfint[b] > (paramArrayOfshort[b]).length) {
/* 210 */         throw new IllegalArgumentException("Length of dataArray[" + b + "] is less than size+offsets[" + b + "].");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 216 */     this.bankdata = (short[][])paramArrayOfshort.clone();
/* 217 */     this.data = this.bankdata[0];
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
/*     */   public short[] getData() {
/* 231 */     this.theTrackable.setUntrackable();
/* 232 */     return this.data;
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
/*     */   public short[] getData(int paramInt) {
/* 247 */     this.theTrackable.setUntrackable();
/* 248 */     return this.bankdata[paramInt];
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
/*     */   public short[][] getBankData() {
/* 262 */     this.theTrackable.setUntrackable();
/* 263 */     return (short[][])this.bankdata.clone();
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
/* 275 */     return this.data[paramInt + this.offset] & 0xFFFF;
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
/* 288 */     return this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]] & 0xFFFF;
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
/* 301 */     this.data[paramInt1 + this.offset] = (short)(paramInt2 & 0xFFFF);
/* 302 */     this.theTrackable.markDirty();
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
/* 315 */     this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]] = (short)(paramInt3 & 0xFFFF);
/* 316 */     this.theTrackable.markDirty();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/DataBufferUShort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */