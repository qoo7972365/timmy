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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CompactByteArray
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int UNICODECOUNT = 65536;
/*     */   private static final int BLOCKSHIFT = 7;
/*     */   private static final int BLOCKCOUNT = 128;
/*     */   private static final int INDEXSHIFT = 9;
/*     */   private static final int INDEXCOUNT = 512;
/*     */   private static final int BLOCKMASK = 127;
/*     */   private byte[] values;
/*     */   private short[] indices;
/*     */   private boolean isCompact;
/*     */   private int[] hashes;
/*     */   
/*     */   public CompactByteArray(byte paramByte) {
/*  77 */     this.values = new byte[65536];
/*  78 */     this.indices = new short[512];
/*  79 */     this.hashes = new int[512]; byte b;
/*  80 */     for (b = 0; b < 65536; b++) {
/*  81 */       this.values[b] = paramByte;
/*     */     }
/*  83 */     for (b = 0; b < 'Ȁ'; b++) {
/*  84 */       this.indices[b] = (short)(b << 7);
/*  85 */       this.hashes[b] = 0;
/*     */     } 
/*  87 */     this.isCompact = false;
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
/*     */   public CompactByteArray(short[] paramArrayOfshort, byte[] paramArrayOfbyte) {
/* 100 */     if (paramArrayOfshort.length != 512)
/* 101 */       throw new IllegalArgumentException("Index out of bounds!"); 
/* 102 */     for (byte b = 0; b < 'Ȁ'; b++) {
/* 103 */       short s = paramArrayOfshort[b];
/* 104 */       if (s < 0 || s >= paramArrayOfbyte.length + 128)
/* 105 */         throw new IllegalArgumentException("Index out of bounds!"); 
/*     */     } 
/* 107 */     this.indices = paramArrayOfshort;
/* 108 */     this.values = paramArrayOfbyte;
/* 109 */     this.isCompact = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte elementAt(char paramChar) {
/* 119 */     return this.values[(this.indices[paramChar >> 7] & 0xFFFF) + (paramChar & 0x7F)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setElementAt(char paramChar, byte paramByte) {
/* 130 */     if (this.isCompact)
/* 131 */       expand(); 
/* 132 */     this.values[paramChar] = paramByte;
/* 133 */     touchBlock(paramChar >> 7, paramByte);
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
/*     */   public void setElementAt(char paramChar1, char paramChar2, byte paramByte) {
/* 145 */     if (this.isCompact) {
/* 146 */       expand();
/*     */     }
/* 148 */     for (char c = paramChar1; c <= paramChar2; c++) {
/* 149 */       this.values[c] = paramByte;
/* 150 */       touchBlock(c >> 7, paramByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compact() {
/* 159 */     if (!this.isCompact) {
/* 160 */       byte b = 0;
/* 161 */       boolean bool = false;
/* 162 */       short s = -1;
/*     */       int i;
/* 164 */       for (i = 0; i < this.indices.length; i++, bool += true) {
/* 165 */         this.indices[i] = -1;
/* 166 */         boolean bool1 = blockTouched(i);
/* 167 */         if (!bool1 && s != -1) {
/*     */ 
/*     */ 
/*     */           
/* 171 */           this.indices[i] = s;
/*     */         } else {
/* 173 */           boolean bool2 = false;
/* 174 */           byte b1 = 0;
/* 175 */           for (b1 = 0; b1 < b; 
/* 176 */             b1++, bool2 += true) {
/* 177 */             if (this.hashes[i] == this.hashes[b1] && 
/* 178 */               arrayRegionMatches(this.values, bool, this.values, bool2, 128)) {
/*     */               
/* 180 */               this.indices[i] = (short)bool2;
/*     */               break;
/*     */             } 
/*     */           } 
/* 184 */           if (this.indices[i] == -1) {
/*     */             
/* 186 */             System.arraycopy(this.values, bool, this.values, bool2, 128);
/*     */             
/* 188 */             this.indices[i] = (short)bool2;
/* 189 */             this.hashes[b1] = this.hashes[i];
/* 190 */             b++;
/*     */             
/* 192 */             if (!bool1)
/*     */             {
/*     */               
/* 195 */               s = (short)bool2;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       i = b * 128;
/* 202 */       byte[] arrayOfByte = new byte[i];
/* 203 */       System.arraycopy(this.values, 0, arrayOfByte, 0, i);
/* 204 */       this.values = arrayOfByte;
/* 205 */       this.isCompact = true;
/* 206 */       this.hashes = null;
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
/*     */   static final boolean arrayRegionMatches(byte[] paramArrayOfbyte1, int paramInt1, byte[] paramArrayOfbyte2, int paramInt2, int paramInt3) {
/* 219 */     int i = paramInt1 + paramInt3;
/* 220 */     int j = paramInt2 - paramInt1;
/* 221 */     for (int k = paramInt1; k < i; k++) {
/* 222 */       if (paramArrayOfbyte1[k] != paramArrayOfbyte2[k + j])
/* 223 */         return false; 
/*     */     } 
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void touchBlock(int paramInt1, int paramInt2) {
/* 233 */     this.hashes[paramInt1] = this.hashes[paramInt1] + (paramInt2 << 1) | 0x1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean blockTouched(int paramInt) {
/* 241 */     return (this.hashes[paramInt] != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short[] getIndexArray() {
/* 249 */     return this.indices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getStringArray() {
/* 257 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 266 */       CompactByteArray compactByteArray = (CompactByteArray)super.clone();
/* 267 */       compactByteArray.values = (byte[])this.values.clone();
/* 268 */       compactByteArray.indices = (short[])this.indices.clone();
/* 269 */       if (this.hashes != null) compactByteArray.hashes = (int[])this.hashes.clone(); 
/* 270 */       return compactByteArray;
/* 271 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 272 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 283 */     if (paramObject == null) return false; 
/* 284 */     if (this == paramObject)
/* 285 */       return true; 
/* 286 */     if (getClass() != paramObject.getClass())
/* 287 */       return false; 
/* 288 */     CompactByteArray compactByteArray = (CompactByteArray)paramObject;
/* 289 */     for (byte b = 0; b < 65536; b++) {
/*     */       
/* 291 */       if (elementAt((char)b) != compactByteArray.elementAt((char)b))
/* 292 */         return false; 
/*     */     } 
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 302 */     int i = 0;
/* 303 */     int j = Math.min(3, this.values.length / 16);
/* 304 */     for (int k = 0; k < this.values.length; k += j) {
/* 305 */       i = i * 37 + this.values[k];
/*     */     }
/* 307 */     return i;
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
/* 319 */     if (this.isCompact) {
/*     */       
/* 321 */       this.hashes = new int[512];
/* 322 */       byte[] arrayOfByte = new byte[65536]; byte b;
/* 323 */       for (b = 0; b < 65536; b++) {
/* 324 */         byte b1 = elementAt((char)b);
/* 325 */         arrayOfByte[b] = b1;
/* 326 */         touchBlock(b >> 7, b1);
/*     */       } 
/* 328 */       for (b = 0; b < 'Ȁ'; b++) {
/* 329 */         this.indices[b] = (short)(b << 7);
/*     */       }
/* 331 */       this.values = null;
/* 332 */       this.values = arrayOfByte;
/* 333 */       this.isCompact = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] getArray() {
/* 339 */     return this.values;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/CompactByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */