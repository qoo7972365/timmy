/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DerIndefLenConverter
/*     */ {
/*     */   private static final int TAG_MASK = 31;
/*     */   private static final int FORM_MASK = 32;
/*     */   private static final int CLASS_MASK = 192;
/*     */   private static final int LEN_LONG = 128;
/*     */   private static final int LEN_MASK = 127;
/*     */   private static final int SKIP_EOC_BYTES = 2;
/*     */   private byte[] data;
/*     */   private byte[] newData;
/*     */   private int newDataPos;
/*     */   private int dataPos;
/*     */   private int dataSize;
/*     */   private int index;
/*  53 */   private int unresolved = 0;
/*     */   
/*  55 */   private ArrayList<Object> ndefsList = new ArrayList();
/*     */   
/*  57 */   private int numOfTotalLenBytes = 0;
/*     */   
/*     */   private boolean isEOC(int paramInt) {
/*  60 */     return ((paramInt & 0x1F) == 0 && (paramInt & 0x20) == 0 && (paramInt & 0xC0) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLongForm(int paramInt) {
/*  67 */     return ((paramInt & 0x80) == 128);
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
/*     */   static boolean isIndefinite(int paramInt) {
/*  85 */     return (isLongForm(paramInt) && (paramInt & 0x7F) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseTag() throws IOException {
/*  93 */     if (this.dataPos == this.dataSize)
/*     */       return; 
/*     */     try {
/*  96 */       if (isEOC(this.data[this.dataPos]) && this.data[this.dataPos + 1] == 0) {
/*  97 */         int i = 0;
/*  98 */         Object object = null;
/*     */         int j;
/* 100 */         for (j = this.ndefsList.size() - 1; j >= 0; j--) {
/*     */ 
/*     */           
/* 103 */           object = this.ndefsList.get(j);
/* 104 */           if (object instanceof Integer) {
/*     */             break;
/*     */           }
/* 107 */           i += ((byte[])object).length - 3;
/*     */         } 
/*     */         
/* 110 */         if (j < 0) {
/* 111 */           throw new IOException("EOC does not have matching indefinite-length tag");
/*     */         }
/*     */         
/* 114 */         int k = this.dataPos - ((Integer)object).intValue() + i;
/*     */         
/* 116 */         byte[] arrayOfByte = getLengthBytes(k);
/* 117 */         this.ndefsList.set(j, arrayOfByte);
/* 118 */         this.unresolved--;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 124 */         this.numOfTotalLenBytes += arrayOfByte.length - 3;
/*     */       } 
/* 126 */       this.dataPos++;
/* 127 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 128 */       throw new IOException(indexOutOfBoundsException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeTag() {
/* 137 */     if (this.dataPos == this.dataSize)
/*     */       return; 
/* 139 */     byte b = this.data[this.dataPos++];
/* 140 */     if (isEOC(b) && this.data[this.dataPos] == 0) {
/* 141 */       this.dataPos++;
/* 142 */       writeTag();
/*     */     } else {
/* 144 */       this.newData[this.newDataPos++] = (byte)b;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parseLength() throws IOException {
/* 152 */     int i = 0;
/* 153 */     if (this.dataPos == this.dataSize)
/* 154 */       return i; 
/* 155 */     int j = this.data[this.dataPos++] & 0xFF;
/* 156 */     if (isIndefinite(j)) {
/* 157 */       this.ndefsList.add(new Integer(this.dataPos));
/* 158 */       this.unresolved++;
/* 159 */       return i;
/*     */     } 
/* 161 */     if (isLongForm(j)) {
/* 162 */       j &= 0x7F;
/* 163 */       if (j > 4) {
/* 164 */         throw new IOException("Too much data");
/*     */       }
/* 166 */       if (this.dataSize - this.dataPos < j + 1) {
/* 167 */         throw new IOException("Too little data");
/*     */       }
/* 169 */       for (byte b = 0; b < j; b++) {
/* 170 */         i = (i << 8) + (this.data[this.dataPos++] & 0xFF);
/*     */       }
/* 172 */       if (i < 0) {
/* 173 */         throw new IOException("Invalid length bytes");
/*     */       }
/*     */     } else {
/* 176 */       i = j & 0x7F;
/*     */     } 
/* 178 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeLengthAndValue() throws IOException {
/* 188 */     if (this.dataPos == this.dataSize)
/*     */       return; 
/* 190 */     int i = 0;
/* 191 */     int j = this.data[this.dataPos++] & 0xFF;
/* 192 */     if (isIndefinite(j)) {
/* 193 */       byte[] arrayOfByte = (byte[])this.ndefsList.get(this.index++);
/* 194 */       System.arraycopy(arrayOfByte, 0, this.newData, this.newDataPos, arrayOfByte.length);
/*     */       
/* 196 */       this.newDataPos += arrayOfByte.length;
/*     */       return;
/*     */     } 
/* 199 */     if (isLongForm(j)) {
/* 200 */       j &= 0x7F;
/* 201 */       for (byte b = 0; b < j; b++) {
/* 202 */         i = (i << 8) + (this.data[this.dataPos++] & 0xFF);
/*     */       }
/* 204 */       if (i < 0) {
/* 205 */         throw new IOException("Invalid length bytes");
/*     */       }
/*     */     } else {
/* 208 */       i = j & 0x7F;
/*     */     } 
/* 210 */     writeLength(i);
/* 211 */     writeValue(i);
/*     */   }
/*     */   
/*     */   private void writeLength(int paramInt) {
/* 215 */     if (paramInt < 128) {
/* 216 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     }
/* 218 */     else if (paramInt < 256) {
/* 219 */       this.newData[this.newDataPos++] = -127;
/* 220 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     }
/* 222 */     else if (paramInt < 65536) {
/* 223 */       this.newData[this.newDataPos++] = -126;
/* 224 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 8);
/* 225 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     }
/* 227 */     else if (paramInt < 16777216) {
/* 228 */       this.newData[this.newDataPos++] = -125;
/* 229 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 16);
/* 230 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 8);
/* 231 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     } else {
/*     */       
/* 234 */       this.newData[this.newDataPos++] = -124;
/* 235 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 24);
/* 236 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 16);
/* 237 */       this.newData[this.newDataPos++] = (byte)(paramInt >> 8);
/* 238 */       this.newData[this.newDataPos++] = (byte)paramInt;
/*     */     } 
/*     */   }
/*     */   
/*     */   private byte[] getLengthBytes(int paramInt) {
/*     */     byte[] arrayOfByte;
/* 244 */     byte b = 0;
/*     */     
/* 246 */     if (paramInt < 128) {
/* 247 */       arrayOfByte = new byte[1];
/* 248 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     }
/* 250 */     else if (paramInt < 256) {
/* 251 */       arrayOfByte = new byte[2];
/* 252 */       arrayOfByte[b++] = -127;
/* 253 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     }
/* 255 */     else if (paramInt < 65536) {
/* 256 */       arrayOfByte = new byte[3];
/* 257 */       arrayOfByte[b++] = -126;
/* 258 */       arrayOfByte[b++] = (byte)(paramInt >> 8);
/* 259 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     }
/* 261 */     else if (paramInt < 16777216) {
/* 262 */       arrayOfByte = new byte[4];
/* 263 */       arrayOfByte[b++] = -125;
/* 264 */       arrayOfByte[b++] = (byte)(paramInt >> 16);
/* 265 */       arrayOfByte[b++] = (byte)(paramInt >> 8);
/* 266 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     } else {
/*     */       
/* 269 */       arrayOfByte = new byte[5];
/* 270 */       arrayOfByte[b++] = -124;
/* 271 */       arrayOfByte[b++] = (byte)(paramInt >> 24);
/* 272 */       arrayOfByte[b++] = (byte)(paramInt >> 16);
/* 273 */       arrayOfByte[b++] = (byte)(paramInt >> 8);
/* 274 */       arrayOfByte[b++] = (byte)paramInt;
/*     */     } 
/*     */     
/* 277 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getNumOfLenBytes(int paramInt) {
/* 283 */     byte b = 0;
/*     */     
/* 285 */     if (paramInt < 128) {
/* 286 */       b = 1;
/* 287 */     } else if (paramInt < 256) {
/* 288 */       b = 2;
/* 289 */     } else if (paramInt < 65536) {
/* 290 */       b = 3;
/* 291 */     } else if (paramInt < 16777216) {
/* 292 */       b = 4;
/*     */     } else {
/* 294 */       b = 5;
/*     */     } 
/* 296 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseValue(int paramInt) {
/* 303 */     this.dataPos += paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeValue(int paramInt) {
/* 310 */     for (byte b = 0; b < paramInt; b++) {
/* 311 */       this.newData[this.newDataPos++] = this.data[this.dataPos++];
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
/*     */   byte[] convert(byte[] paramArrayOfbyte) throws IOException {
/* 325 */     this.data = paramArrayOfbyte;
/* 326 */     this.dataPos = 0; this.index = 0;
/* 327 */     this.dataSize = this.data.length;
/* 328 */     int i = 0;
/* 329 */     int j = 0;
/*     */ 
/*     */     
/* 332 */     while (this.dataPos < this.dataSize) {
/* 333 */       parseTag();
/* 334 */       i = parseLength();
/* 335 */       parseValue(i);
/* 336 */       if (this.unresolved == 0) {
/* 337 */         j = this.dataSize - this.dataPos;
/* 338 */         this.dataSize = this.dataPos;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 343 */     if (this.unresolved != 0) {
/* 344 */       throw new IOException("not all indef len BER resolved");
/*     */     }
/*     */     
/* 347 */     this.newData = new byte[this.dataSize + this.numOfTotalLenBytes + j];
/* 348 */     this.dataPos = 0; this.newDataPos = 0; this.index = 0;
/*     */ 
/*     */ 
/*     */     
/* 352 */     while (this.dataPos < this.dataSize) {
/* 353 */       writeTag();
/* 354 */       writeLengthAndValue();
/*     */     } 
/* 356 */     System.arraycopy(paramArrayOfbyte, this.dataSize, this.newData, this.dataSize + this.numOfTotalLenBytes, j);
/*     */ 
/*     */     
/* 359 */     return this.newData;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/DerIndefLenConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */