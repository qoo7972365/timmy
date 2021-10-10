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
/*     */ public final class DataBufferFloat
/*     */   extends DataBuffer
/*     */ {
/*     */   float[][] bankdata;
/*     */   float[] data;
/*     */   
/*     */   public DataBufferFloat(int paramInt) {
/*  67 */     super(StateTrackable.State.STABLE, 4, paramInt);
/*  68 */     this.data = new float[paramInt];
/*  69 */     this.bankdata = new float[1][];
/*  70 */     this.bankdata[0] = this.data;
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
/*     */   public DataBufferFloat(int paramInt1, int paramInt2) {
/*  84 */     super(StateTrackable.State.STABLE, 4, paramInt1, paramInt2);
/*  85 */     this.bankdata = new float[paramInt2][];
/*  86 */     for (byte b = 0; b < paramInt2; b++) {
/*  87 */       this.bankdata[b] = new float[paramInt1];
/*     */     }
/*  89 */     this.data = this.bankdata[0];
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
/*     */   public DataBufferFloat(float[] paramArrayOffloat, int paramInt) {
/* 109 */     super(StateTrackable.State.UNTRACKABLE, 4, paramInt);
/* 110 */     this.data = paramArrayOffloat;
/* 111 */     this.bankdata = new float[1][];
/* 112 */     this.bankdata[0] = this.data;
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
/*     */   public DataBufferFloat(float[] paramArrayOffloat, int paramInt1, int paramInt2) {
/* 135 */     super(StateTrackable.State.UNTRACKABLE, 4, paramInt1, 1, paramInt2);
/* 136 */     this.data = paramArrayOffloat;
/* 137 */     this.bankdata = new float[1][];
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
/*     */ 
/*     */   
/*     */   public DataBufferFloat(float[][] paramArrayOffloat, int paramInt) {
/* 158 */     super(StateTrackable.State.UNTRACKABLE, 4, paramInt, paramArrayOffloat.length);
/* 159 */     this.bankdata = (float[][])paramArrayOffloat.clone();
/* 160 */     this.data = this.bankdata[0];
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
/*     */   public DataBufferFloat(float[][] paramArrayOffloat, int paramInt, int[] paramArrayOfint) {
/* 182 */     super(StateTrackable.State.UNTRACKABLE, 4, paramInt, paramArrayOffloat.length, paramArrayOfint);
/* 183 */     this.bankdata = (float[][])paramArrayOffloat.clone();
/* 184 */     this.data = this.bankdata[0];
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
/*     */   public float[] getData() {
/* 198 */     this.theTrackable.setUntrackable();
/* 199 */     return this.data;
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
/*     */   public float[] getData(int paramInt) {
/* 214 */     this.theTrackable.setUntrackable();
/* 215 */     return this.bankdata[paramInt];
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
/*     */   public float[][] getBankData() {
/* 229 */     this.theTrackable.setUntrackable();
/* 230 */     return (float[][])this.bankdata.clone();
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
/*     */   public int getElem(int paramInt) {
/* 244 */     return (int)this.data[paramInt + this.offset];
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
/*     */   public int getElem(int paramInt1, int paramInt2) {
/* 259 */     return (int)this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]];
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
/* 272 */     this.data[paramInt1 + this.offset] = paramInt2;
/* 273 */     this.theTrackable.markDirty();
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
/*     */   public void setElem(int paramInt1, int paramInt2, int paramInt3) {
/* 287 */     this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]] = paramInt3;
/* 288 */     this.theTrackable.markDirty();
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
/*     */   public float getElemFloat(int paramInt) {
/* 302 */     return this.data[paramInt + this.offset];
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
/*     */   public float getElemFloat(int paramInt1, int paramInt2) {
/* 317 */     return this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]];
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
/*     */   public void setElemFloat(int paramInt, float paramFloat) {
/* 330 */     this.data[paramInt + this.offset] = paramFloat;
/* 331 */     this.theTrackable.markDirty();
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
/*     */   public void setElemFloat(int paramInt1, int paramInt2, float paramFloat) {
/* 345 */     this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]] = paramFloat;
/* 346 */     this.theTrackable.markDirty();
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
/*     */   public double getElemDouble(int paramInt) {
/* 360 */     return this.data[paramInt + this.offset];
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
/*     */   public double getElemDouble(int paramInt1, int paramInt2) {
/* 375 */     return this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]];
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
/*     */   public void setElemDouble(int paramInt, double paramDouble) {
/* 388 */     this.data[paramInt + this.offset] = (float)paramDouble;
/* 389 */     this.theTrackable.markDirty();
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
/*     */   public void setElemDouble(int paramInt1, int paramInt2, double paramDouble) {
/* 403 */     this.bankdata[paramInt1][paramInt2 + this.offsets[paramInt1]] = (float)paramDouble;
/* 404 */     this.theTrackable.markDirty();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/DataBufferFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */