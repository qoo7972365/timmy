/*     */ package java.awt.image;
/*     */ 
/*     */ import sun.awt.image.SunWritableRaster;
/*     */ import sun.java2d.StateTrackable;
/*     */ import sun.java2d.StateTrackableDelegate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DataBuffer
/*     */ {
/*     */   public static final int TYPE_BYTE = 0;
/*     */   public static final int TYPE_USHORT = 1;
/*     */   public static final int TYPE_SHORT = 2;
/*     */   public static final int TYPE_INT = 3;
/*     */   public static final int TYPE_FLOAT = 4;
/*     */   public static final int TYPE_DOUBLE = 5;
/*     */   public static final int TYPE_UNDEFINED = 32;
/*     */   protected int dataType;
/*     */   protected int banks;
/*     */   protected int offset;
/*     */   protected int size;
/*     */   protected int[] offsets;
/*     */   StateTrackableDelegate theTrackable;
/* 112 */   private static final int[] dataTypeSize = new int[] { 8, 16, 16, 32, 32, 64 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDataTypeSize(int paramInt) {
/* 121 */     if (paramInt < 0 || paramInt > 5) {
/* 122 */       throw new IllegalArgumentException("Unknown data type " + paramInt);
/*     */     }
/* 124 */     return dataTypeSize[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DataBuffer(int paramInt1, int paramInt2) {
/* 135 */     this(StateTrackable.State.UNTRACKABLE, paramInt1, paramInt2);
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
/*     */   DataBuffer(StateTrackable.State paramState, int paramInt1, int paramInt2) {
/* 150 */     this.theTrackable = StateTrackableDelegate.createInstance(paramState);
/* 151 */     this.dataType = paramInt1;
/* 152 */     this.banks = 1;
/* 153 */     this.size = paramInt2;
/* 154 */     this.offset = 0;
/* 155 */     this.offsets = new int[1];
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
/*     */   protected DataBuffer(int paramInt1, int paramInt2, int paramInt3) {
/* 168 */     this(StateTrackable.State.UNTRACKABLE, paramInt1, paramInt2, paramInt3);
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
/*     */   DataBuffer(StateTrackable.State paramState, int paramInt1, int paramInt2, int paramInt3) {
/* 186 */     this.theTrackable = StateTrackableDelegate.createInstance(paramState);
/* 187 */     this.dataType = paramInt1;
/* 188 */     this.banks = paramInt3;
/* 189 */     this.size = paramInt2;
/* 190 */     this.offset = 0;
/* 191 */     this.offsets = new int[this.banks];
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
/*     */   protected DataBuffer(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 205 */     this(StateTrackable.State.UNTRACKABLE, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   DataBuffer(StateTrackable.State paramState, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 224 */     this.theTrackable = StateTrackableDelegate.createInstance(paramState);
/* 225 */     this.dataType = paramInt1;
/* 226 */     this.banks = paramInt3;
/* 227 */     this.size = paramInt2;
/* 228 */     this.offset = paramInt4;
/* 229 */     this.offsets = new int[paramInt3];
/* 230 */     for (byte b = 0; b < paramInt3; b++) {
/* 231 */       this.offsets[b] = paramInt4;
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
/*     */   protected DataBuffer(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
/* 250 */     this(StateTrackable.State.UNTRACKABLE, paramInt1, paramInt2, paramInt3, paramArrayOfint);
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
/*     */   DataBuffer(StateTrackable.State paramState, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfint) {
/* 273 */     if (paramInt3 != paramArrayOfint.length) {
/* 274 */       throw new ArrayIndexOutOfBoundsException("Number of banks does not match number of bank offsets");
/*     */     }
/*     */     
/* 277 */     this.theTrackable = StateTrackableDelegate.createInstance(paramState);
/* 278 */     this.dataType = paramInt1;
/* 279 */     this.banks = paramInt3;
/* 280 */     this.size = paramInt2;
/* 281 */     this.offset = paramArrayOfint[0];
/* 282 */     this.offsets = (int[])paramArrayOfint.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataType() {
/* 289 */     return this.dataType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 296 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOffset() {
/* 303 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getOffsets() {
/* 310 */     return (int[])this.offsets.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumBanks() {
/* 317 */     return this.banks;
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
/* 329 */     return getElem(0, paramInt);
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
/*     */   public abstract int getElem(int paramInt1, int paramInt2);
/*     */ 
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
/* 354 */     setElem(0, paramInt1, paramInt2);
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
/*     */   public abstract void setElem(int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
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
/* 381 */     return getElem(paramInt);
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
/*     */   public float getElemFloat(int paramInt1, int paramInt2) {
/* 398 */     return getElem(paramInt1, paramInt2);
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
/*     */   public void setElemFloat(int paramInt, float paramFloat) {
/* 413 */     setElem(paramInt, (int)paramFloat);
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
/*     */   public void setElemFloat(int paramInt1, int paramInt2, float paramFloat) {
/* 429 */     setElem(paramInt1, paramInt2, (int)paramFloat);
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
/*     */   public double getElemDouble(int paramInt) {
/* 445 */     return getElem(paramInt);
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
/*     */   public double getElemDouble(int paramInt1, int paramInt2) {
/* 461 */     return getElem(paramInt1, paramInt2);
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
/*     */   public void setElemDouble(int paramInt, double paramDouble) {
/* 476 */     setElem(paramInt, (int)paramDouble);
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
/*     */   public void setElemDouble(int paramInt1, int paramInt2, double paramDouble) {
/* 492 */     setElem(paramInt1, paramInt2, (int)paramDouble);
/*     */   }
/*     */   
/*     */   static int[] toIntArray(Object paramObject) {
/* 496 */     if (paramObject instanceof int[])
/* 497 */       return (int[])paramObject; 
/* 498 */     if (paramObject == null)
/* 499 */       return null; 
/* 500 */     if (paramObject instanceof short[]) {
/* 501 */       short[] arrayOfShort = (short[])paramObject;
/* 502 */       int[] arrayOfInt = new int[arrayOfShort.length];
/* 503 */       for (byte b = 0; b < arrayOfShort.length; b++) {
/* 504 */         arrayOfInt[b] = arrayOfShort[b] & 0xFFFF;
/*     */       }
/* 506 */       return arrayOfInt;
/* 507 */     }  if (paramObject instanceof byte[]) {
/* 508 */       byte[] arrayOfByte = (byte[])paramObject;
/* 509 */       int[] arrayOfInt = new int[arrayOfByte.length];
/* 510 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 511 */         arrayOfInt[b] = 0xFF & arrayOfByte[b];
/*     */       }
/* 513 */       return arrayOfInt;
/*     */     } 
/* 515 */     return null;
/*     */   }
/*     */   
/*     */   static {
/* 519 */     SunWritableRaster.setDataStealer(new SunWritableRaster.DataStealer() {
/*     */           public byte[] getData(DataBufferByte param1DataBufferByte, int param1Int) {
/* 521 */             return param1DataBufferByte.bankdata[param1Int];
/*     */           }
/*     */           
/*     */           public short[] getData(DataBufferUShort param1DataBufferUShort, int param1Int) {
/* 525 */             return param1DataBufferUShort.bankdata[param1Int];
/*     */           }
/*     */           
/*     */           public int[] getData(DataBufferInt param1DataBufferInt, int param1Int) {
/* 529 */             return param1DataBufferInt.bankdata[param1Int];
/*     */           }
/*     */           
/*     */           public StateTrackableDelegate getTrackable(DataBuffer param1DataBuffer) {
/* 533 */             return param1DataBuffer.theTrackable;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void setTrackable(DataBuffer param1DataBuffer, StateTrackableDelegate param1StateTrackableDelegate) {
/* 539 */             param1DataBuffer.theTrackable = param1StateTrackableDelegate;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/image/DataBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */