/*     */ package sun.nio.cs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharsetMapping
/*     */ {
/*     */   public static final char UNMAPPABLE_DECODING = '�';
/*     */   public static final int UNMAPPABLE_ENCODING = 65533;
/*     */   char[] b2cSB;
/*     */   char[] b2cDB1;
/*     */   char[] b2cDB2;
/*     */   int b2Min;
/*     */   int b2Max;
/*     */   int b1MinDB1;
/*     */   int b1MaxDB1;
/*     */   int b1MinDB2;
/*     */   int b1MaxDB2;
/*     */   int dbSegSize;
/*     */   char[] c2b;
/*     */   char[] c2bIndex;
/*     */   char[] b2cSupp;
/*     */   char[] c2bSupp;
/*     */   Entry[] b2cComp;
/*     */   Entry[] c2bComp;
/*     */   
/*     */   public char decodeSingle(int paramInt) {
/*  63 */     return this.b2cSB[paramInt];
/*     */   }
/*     */   
/*     */   public char decodeDouble(int paramInt1, int paramInt2) {
/*  67 */     if (paramInt2 >= this.b2Min && paramInt2 < this.b2Max) {
/*  68 */       paramInt2 -= this.b2Min;
/*  69 */       if (paramInt1 >= this.b1MinDB1 && paramInt1 <= this.b1MaxDB1) {
/*  70 */         paramInt1 -= this.b1MinDB1;
/*  71 */         return this.b2cDB1[paramInt1 * this.dbSegSize + paramInt2];
/*     */       } 
/*  73 */       if (paramInt1 >= this.b1MinDB2 && paramInt1 <= this.b1MaxDB2) {
/*  74 */         paramInt1 -= this.b1MinDB2;
/*  75 */         return this.b2cDB2[paramInt1 * this.dbSegSize + paramInt2];
/*     */       } 
/*     */     } 
/*  78 */     return '�';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char[] decodeSurrogate(int paramInt, char[] paramArrayOfchar) {
/*  85 */     int i = this.b2cSupp.length / 2;
/*  86 */     int j = Arrays.binarySearch(this.b2cSupp, 0, i, (char)paramInt);
/*  87 */     if (j >= 0) {
/*  88 */       Character.toChars(this.b2cSupp[i + j] + 131072, paramArrayOfchar, 0);
/*  89 */       return paramArrayOfchar;
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public char[] decodeComposite(Entry paramEntry, char[] paramArrayOfchar) {
/*  95 */     int i = findBytes(this.b2cComp, paramEntry);
/*  96 */     if (i >= 0) {
/*  97 */       paramArrayOfchar[0] = (char)(this.b2cComp[i]).cp;
/*  98 */       paramArrayOfchar[1] = (char)(this.b2cComp[i]).cp2;
/*  99 */       return paramArrayOfchar;
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public int encodeChar(char paramChar) {
/* 105 */     char c = this.c2bIndex[paramChar >> 8];
/* 106 */     if (c == Character.MAX_VALUE)
/* 107 */       return 65533; 
/* 108 */     return this.c2b[c + (paramChar & 0xFF)];
/*     */   }
/*     */   
/*     */   public int encodeSurrogate(char paramChar1, char paramChar2) {
/* 112 */     int i = Character.toCodePoint(paramChar1, paramChar2);
/* 113 */     if (i < 131072 || i >= 196608)
/* 114 */       return 65533; 
/* 115 */     int j = this.c2bSupp.length / 2;
/* 116 */     int k = Arrays.binarySearch(this.c2bSupp, 0, j, (char)i);
/* 117 */     if (k >= 0)
/* 118 */       return this.c2bSupp[j + k]; 
/* 119 */     return 65533;
/*     */   }
/*     */   
/*     */   public boolean isCompositeBase(Entry paramEntry) {
/* 123 */     if (paramEntry.cp <= 12791 && paramEntry.cp >= 230) {
/* 124 */       return (findCP(this.c2bComp, paramEntry) >= 0);
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public int encodeComposite(Entry paramEntry) {
/* 130 */     int i = findComp(this.c2bComp, paramEntry);
/* 131 */     if (i >= 0)
/* 132 */       return (this.c2bComp[i]).bs; 
/* 133 */     return 65533;
/*     */   }
/*     */ 
/*     */   
/*     */   public static CharsetMapping get(final InputStream is) {
/* 138 */     return AccessController.<CharsetMapping>doPrivileged(new PrivilegedAction<CharsetMapping>() {
/*     */           public CharsetMapping run() {
/* 140 */             return (new CharsetMapping()).load(is);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static class Entry {
/*     */     public int bs;
/*     */     public int cp;
/*     */     public int cp2;
/*     */   }
/*     */   
/* 151 */   static Comparator<Entry> comparatorBytes = new Comparator<Entry>()
/*     */     {
/*     */       public int compare(CharsetMapping.Entry param1Entry1, CharsetMapping.Entry param1Entry2) {
/* 154 */         return param1Entry1.bs - param1Entry2.bs;
/*     */       }
/*     */       public boolean equals(Object param1Object) {
/* 157 */         return (this == param1Object);
/*     */       }
/*     */     };
/*     */   
/* 161 */   static Comparator<Entry> comparatorCP = new Comparator<Entry>()
/*     */     {
/*     */       public int compare(CharsetMapping.Entry param1Entry1, CharsetMapping.Entry param1Entry2) {
/* 164 */         return param1Entry1.cp - param1Entry2.cp;
/*     */       }
/*     */       public boolean equals(Object param1Object) {
/* 167 */         return (this == param1Object);
/*     */       }
/*     */     };
/*     */   
/* 171 */   static Comparator<Entry> comparatorComp = new Comparator<Entry>()
/*     */     {
/*     */       public int compare(CharsetMapping.Entry param1Entry1, CharsetMapping.Entry param1Entry2) {
/* 174 */         int i = param1Entry1.cp - param1Entry2.cp;
/* 175 */         if (i == 0)
/* 176 */           i = param1Entry1.cp2 - param1Entry2.cp2; 
/* 177 */         return i;
/*     */       }
/*     */       public boolean equals(Object param1Object) {
/* 180 */         return (this == param1Object);
/*     */       }
/*     */     };
/*     */   private static final int MAP_SINGLEBYTE = 1; private static final int MAP_DOUBLEBYTE1 = 2; private static final int MAP_DOUBLEBYTE2 = 3;
/*     */   static int findBytes(Entry[] paramArrayOfEntry, Entry paramEntry) {
/* 185 */     return Arrays.binarySearch(paramArrayOfEntry, 0, paramArrayOfEntry.length, paramEntry, comparatorBytes);
/*     */   }
/*     */   private static final int MAP_SUPPLEMENT = 5; private static final int MAP_SUPPLEMENT_C2B = 6; private static final int MAP_COMPOSITE = 7; private static final int MAP_INDEXC2B = 8;
/*     */   static int findCP(Entry[] paramArrayOfEntry, Entry paramEntry) {
/* 189 */     return Arrays.binarySearch(paramArrayOfEntry, 0, paramArrayOfEntry.length, paramEntry, comparatorCP);
/*     */   }
/*     */   
/*     */   static int findComp(Entry[] paramArrayOfEntry, Entry paramEntry) {
/* 193 */     return Arrays.binarySearch(paramArrayOfEntry, 0, paramArrayOfEntry.length, paramEntry, comparatorComp);
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
/*     */   private static final boolean readNBytes(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 209 */     int i = 0;
/* 210 */     while (paramInt > 0) {
/* 211 */       int j = paramInputStream.read(paramArrayOfbyte, i, paramInt);
/* 212 */       if (j == -1)
/* 213 */         return false; 
/* 214 */       paramInt -= j;
/* 215 */       i += j;
/*     */     } 
/* 217 */     return true;
/*     */   }
/*     */   
/* 220 */   int off = 0;
/*     */   byte[] bb;
/*     */   
/*     */   private char[] readCharArray() {
/* 224 */     int i = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 225 */     char[] arrayOfChar = new char[i];
/* 226 */     for (byte b = 0; b < i; b++) {
/* 227 */       arrayOfChar[b] = (char)((this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF);
/*     */     }
/* 229 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   void readSINGLEBYTE() {
/* 233 */     char[] arrayOfChar = readCharArray();
/* 234 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 235 */       char c = arrayOfChar[b];
/* 236 */       if (c != '�') {
/* 237 */         this.c2b[this.c2bIndex[c >> 8] + (c & 0xFF)] = (char)b;
/*     */       }
/*     */     } 
/* 240 */     this.b2cSB = arrayOfChar;
/*     */   }
/*     */   
/*     */   void readINDEXC2B() {
/* 244 */     char[] arrayOfChar = readCharArray();
/* 245 */     for (int i = arrayOfChar.length - 1; i >= 0; i--) {
/* 246 */       if (this.c2b == null && arrayOfChar[i] != -1) {
/* 247 */         this.c2b = new char[arrayOfChar[i] + 256];
/* 248 */         Arrays.fill(this.c2b, '�');
/*     */         break;
/*     */       } 
/*     */     } 
/* 252 */     this.c2bIndex = arrayOfChar;
/*     */   }
/*     */   
/*     */   char[] readDB(int paramInt1, int paramInt2, int paramInt3) {
/* 256 */     char[] arrayOfChar = readCharArray();
/* 257 */     for (byte b = 0; b < arrayOfChar.length; b++) {
/* 258 */       char c = arrayOfChar[b];
/* 259 */       if (c != '�') {
/* 260 */         int i = b / paramInt3;
/* 261 */         int j = b % paramInt3;
/* 262 */         int k = (i + paramInt1) * 256 + j + paramInt2;
/*     */         
/* 264 */         this.c2b[this.c2bIndex[c >> 8] + (c & 0xFF)] = (char)k;
/*     */       } 
/*     */     } 
/* 267 */     return arrayOfChar;
/*     */   }
/*     */   
/*     */   void readDOUBLEBYTE1() {
/* 271 */     this.b1MinDB1 = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 272 */     this.b1MaxDB1 = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 273 */     this.b2Min = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 274 */     this.b2Max = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 275 */     this.dbSegSize = this.b2Max - this.b2Min + 1;
/* 276 */     this.b2cDB1 = readDB(this.b1MinDB1, this.b2Min, this.dbSegSize);
/*     */   }
/*     */   
/*     */   void readDOUBLEBYTE2() {
/* 280 */     this.b1MinDB2 = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 281 */     this.b1MaxDB2 = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 282 */     this.b2Min = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 283 */     this.b2Max = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 284 */     this.dbSegSize = this.b2Max - this.b2Min + 1;
/* 285 */     this.b2cDB2 = readDB(this.b1MinDB2, this.b2Min, this.dbSegSize);
/*     */   }
/*     */   
/*     */   void readCOMPOSITE() {
/* 289 */     char[] arrayOfChar = readCharArray();
/* 290 */     int i = arrayOfChar.length / 3;
/* 291 */     this.b2cComp = new Entry[i];
/* 292 */     this.c2bComp = new Entry[i];
/* 293 */     for (byte b1 = 0, b2 = 0; b1 < i; b1++) {
/* 294 */       Entry entry = new Entry();
/* 295 */       entry.bs = arrayOfChar[b2++];
/* 296 */       entry.cp = arrayOfChar[b2++];
/* 297 */       entry.cp2 = arrayOfChar[b2++];
/* 298 */       this.b2cComp[b1] = entry;
/* 299 */       this.c2bComp[b1] = entry;
/*     */     } 
/* 301 */     Arrays.sort(this.c2bComp, 0, this.c2bComp.length, comparatorComp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CharsetMapping load(InputStream paramInputStream) {
/*     */     try {
/* 309 */       int i = (paramInputStream.read() & 0xFF) << 24 | (paramInputStream.read() & 0xFF) << 16 | (paramInputStream.read() & 0xFF) << 8 | paramInputStream.read() & 0xFF;
/* 310 */       this.bb = new byte[i];
/* 311 */       this.off = 0;
/*     */ 
/*     */       
/* 314 */       if (!readNBytes(paramInputStream, this.bb, i))
/* 315 */         throw new RuntimeException("Corrupted data file"); 
/* 316 */       paramInputStream.close();
/*     */       
/* 318 */       while (this.off < i) {
/* 319 */         int j = (this.bb[this.off++] & 0xFF) << 8 | this.bb[this.off++] & 0xFF;
/* 320 */         switch (j) {
/*     */           case 8:
/* 322 */             readINDEXC2B();
/*     */             continue;
/*     */           case 1:
/* 325 */             readSINGLEBYTE();
/*     */             continue;
/*     */           case 2:
/* 328 */             readDOUBLEBYTE1();
/*     */             continue;
/*     */           case 3:
/* 331 */             readDOUBLEBYTE2();
/*     */             continue;
/*     */           case 5:
/* 334 */             this.b2cSupp = readCharArray();
/*     */             continue;
/*     */           case 6:
/* 337 */             this.c2bSupp = readCharArray();
/*     */             continue;
/*     */           case 7:
/* 340 */             readCOMPOSITE();
/*     */             continue;
/*     */         } 
/* 343 */         throw new RuntimeException("Corrupted data file");
/*     */       } 
/*     */       
/* 346 */       this.bb = null;
/* 347 */       return this;
/* 348 */     } catch (IOException iOException) {
/* 349 */       iOException.printStackTrace();
/* 350 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/CharsetMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */