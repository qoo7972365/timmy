/*     */ package sun.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UCompactIntArray
/*     */   implements Cloneable
/*     */ {
/*     */   private static final int PLANEMASK = 196608;
/*     */   private static final int PLANESHIFT = 16;
/*     */   private static final int PLANECOUNT = 16;
/*     */   private static final int CODEPOINTMASK = 65535;
/*     */   private static final int UNICODECOUNT = 65536;
/*     */   private static final int BLOCKSHIFT = 7;
/*     */   private static final int BLOCKCOUNT = 128;
/*     */   private static final int INDEXSHIFT = 9;
/*     */   private static final int INDEXCOUNT = 512;
/*     */   private static final int BLOCKMASK = 127;
/*     */   private int defaultValue;
/*  34 */   private int[][] values = new int[16][];
/*  35 */   private short[][] indices = new short[16][];
/*  36 */   private boolean[][] blockTouched = new boolean[16][]; private boolean isCompact;
/*  37 */   private boolean[] planeTouched = new boolean[16];
/*     */   public UCompactIntArray() {}
/*     */   
/*     */   public UCompactIntArray(int paramInt) {
/*  41 */     this();
/*  42 */     this.defaultValue = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int elementAt(int paramInt) {
/*  51 */     int i = (paramInt & 0x30000) >> 16;
/*  52 */     if (!this.planeTouched[i]) {
/*  53 */       return this.defaultValue;
/*     */     }
/*  55 */     paramInt &= 0xFFFF;
/*  56 */     return this.values[i][(this.indices[i][paramInt >> 7] & 0xFFFF) + (paramInt & 0x7F)];
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
/*     */   public void setElementAt(int paramInt1, int paramInt2) {
/*  68 */     if (this.isCompact) {
/*  69 */       expand();
/*     */     }
/*  71 */     int i = (paramInt1 & 0x30000) >> 16;
/*  72 */     if (!this.planeTouched[i]) {
/*  73 */       initPlane(i);
/*     */     }
/*  75 */     paramInt1 &= 0xFFFF;
/*  76 */     this.values[i][paramInt1] = paramInt2;
/*  77 */     this.blockTouched[i][paramInt1 >> 7] = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compact() {
/*  85 */     if (this.isCompact) {
/*     */       return;
/*     */     }
/*  88 */     for (byte b = 0; b < 16; b++) {
/*  89 */       if (this.planeTouched[b]) {
/*     */ 
/*     */         
/*  92 */         byte b1 = 0;
/*  93 */         boolean bool = false;
/*  94 */         short s = -1;
/*     */         int i;
/*  96 */         for (i = 0; i < (this.indices[b]).length; i++, bool += true) {
/*  97 */           this.indices[b][i] = -1;
/*  98 */           if (!this.blockTouched[b][i] && s != -1) {
/*     */ 
/*     */ 
/*     */             
/* 102 */             this.indices[b][i] = s;
/*     */           } else {
/* 104 */             int j = b1 * 128;
/* 105 */             if (i > b1) {
/* 106 */               System.arraycopy(this.values[b], bool, this.values[b], j, 128);
/*     */             }
/*     */             
/* 109 */             if (!this.blockTouched[b][i])
/*     */             {
/* 111 */               s = (short)j;
/*     */             }
/* 113 */             this.indices[b][i] = (short)j;
/* 114 */             b1++;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 119 */         i = b1 * 128;
/* 120 */         int[] arrayOfInt = new int[i];
/* 121 */         System.arraycopy(this.values[b], 0, arrayOfInt, 0, i);
/* 122 */         this.values[b] = arrayOfInt;
/* 123 */         this.blockTouched[b] = null;
/*     */       } 
/* 125 */     }  this.isCompact = true;
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
/*     */   private void expand() {
/* 137 */     if (this.isCompact) {
/*     */       
/* 139 */       for (byte b = 0; b < 16; b++) {
/* 140 */         if (this.planeTouched[b]) {
/*     */ 
/*     */           
/* 143 */           this.blockTouched[b] = new boolean[512];
/* 144 */           int[] arrayOfInt = new int[65536]; byte b1;
/* 145 */           for (b1 = 0; b1 < 65536; b1++) {
/* 146 */             arrayOfInt[b1] = this.values[b][this.indices[b][b1 >> 7] & 65535 + (b1 & 0x7F)];
/*     */             
/* 148 */             this.blockTouched[b][b1 >> 7] = true;
/*     */           } 
/* 150 */           for (b1 = 0; b1 < 'Ȁ'; b1++) {
/* 151 */             this.indices[b][b1] = (short)(b1 << 7);
/*     */           }
/* 153 */           this.values[b] = arrayOfInt;
/*     */         } 
/* 155 */       }  this.isCompact = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initPlane(int paramInt) {
/* 160 */     this.values[paramInt] = new int[65536];
/* 161 */     this.indices[paramInt] = new short[512];
/* 162 */     this.blockTouched[paramInt] = new boolean[512];
/* 163 */     this.planeTouched[paramInt] = true;
/*     */     
/* 165 */     if (this.planeTouched[0] && paramInt != 0) {
/* 166 */       System.arraycopy(this.indices[0], 0, this.indices[paramInt], 0, 512);
/*     */     } else {
/* 168 */       for (byte b1 = 0; b1 < 'Ȁ'; b1++) {
/* 169 */         this.indices[paramInt][b1] = (short)(b1 << 7);
/*     */       }
/*     */     } 
/* 172 */     for (byte b = 0; b < 65536; b++) {
/* 173 */       this.values[paramInt][b] = this.defaultValue;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getKSize() {
/* 178 */     int i = 0;
/* 179 */     for (byte b = 0; b < 16; b++) {
/* 180 */       if (this.planeTouched[b]) {
/* 181 */         i += (this.values[b]).length * 4 + (this.indices[b]).length * 2;
/*     */       }
/*     */     } 
/* 184 */     return i / 1024;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/UCompactIntArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */