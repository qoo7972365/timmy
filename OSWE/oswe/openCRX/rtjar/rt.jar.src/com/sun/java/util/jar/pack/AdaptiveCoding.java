/*     */ package com.sun.java.util.jar.pack;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AdaptiveCoding
/*     */   implements CodingMethod
/*     */ {
/*     */   CodingMethod headCoding;
/*     */   int headLength;
/*     */   CodingMethod tailCoding;
/*     */   public static final int KX_MIN = 0;
/*     */   public static final int KX_MAX = 3;
/*     */   public static final int KX_LG2BASE = 4;
/*     */   public static final int KX_BASE = 16;
/*     */   public static final int KB_MIN = 0;
/*     */   public static final int KB_MAX = 255;
/*     */   public static final int KB_OFFSET = 1;
/*     */   public static final int KB_DEFAULT = 3;
/*     */   
/*     */   public AdaptiveCoding(int paramInt, CodingMethod paramCodingMethod1, CodingMethod paramCodingMethod2) {
/*  45 */     assert isCodableLength(paramInt);
/*  46 */     this.headLength = paramInt;
/*  47 */     this.headCoding = paramCodingMethod1;
/*  48 */     this.tailCoding = paramCodingMethod2;
/*     */   }
/*     */   
/*     */   public void setHeadCoding(CodingMethod paramCodingMethod) {
/*  52 */     this.headCoding = paramCodingMethod;
/*     */   }
/*     */   public void setHeadLength(int paramInt) {
/*  55 */     assert isCodableLength(paramInt);
/*  56 */     this.headLength = paramInt;
/*     */   }
/*     */   public void setTailCoding(CodingMethod paramCodingMethod) {
/*  59 */     this.tailCoding = paramCodingMethod;
/*     */   }
/*     */   
/*     */   public boolean isTrivial() {
/*  63 */     return (this.headCoding == this.tailCoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeArrayTo(OutputStream paramOutputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/*  68 */     writeArray(this, paramOutputStream, paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   private static void writeArray(AdaptiveCoding paramAdaptiveCoding, OutputStream paramOutputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/*     */     while (true) {
/*  73 */       int i = paramInt1 + paramAdaptiveCoding.headLength;
/*  74 */       assert i <= paramInt2;
/*  75 */       paramAdaptiveCoding.headCoding.writeArrayTo(paramOutputStream, paramArrayOfint, paramInt1, i);
/*  76 */       paramInt1 = i;
/*  77 */       if (paramAdaptiveCoding.tailCoding instanceof AdaptiveCoding) {
/*  78 */         paramAdaptiveCoding = (AdaptiveCoding)paramAdaptiveCoding.tailCoding;
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*  83 */     paramAdaptiveCoding.tailCoding.writeArrayTo(paramOutputStream, paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void readArrayFrom(InputStream paramInputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/*  87 */     readArray(this, paramInputStream, paramArrayOfint, paramInt1, paramInt2);
/*     */   }
/*     */   private static void readArray(AdaptiveCoding paramAdaptiveCoding, InputStream paramInputStream, int[] paramArrayOfint, int paramInt1, int paramInt2) throws IOException {
/*     */     while (true) {
/*  91 */       int i = paramInt1 + paramAdaptiveCoding.headLength;
/*  92 */       assert i <= paramInt2;
/*  93 */       paramAdaptiveCoding.headCoding.readArrayFrom(paramInputStream, paramArrayOfint, paramInt1, i);
/*  94 */       paramInt1 = i;
/*  95 */       if (paramAdaptiveCoding.tailCoding instanceof AdaptiveCoding) {
/*  96 */         paramAdaptiveCoding = (AdaptiveCoding)paramAdaptiveCoding.tailCoding;
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 101 */     paramAdaptiveCoding.tailCoding.readArrayFrom(paramInputStream, paramArrayOfint, paramInt1, paramInt2);
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
/*     */   static int getKXOf(int paramInt) {
/* 115 */     for (byte b = 0; b <= 3; b++) {
/* 116 */       if ((paramInt - 1 & 0xFFFFFF00) == 0)
/* 117 */         return b; 
/* 118 */       paramInt >>>= 4;
/*     */     } 
/* 120 */     return -1;
/*     */   }
/*     */   
/*     */   static int getKBOf(int paramInt) {
/* 124 */     int i = getKXOf(paramInt);
/* 125 */     if (i < 0) return -1; 
/* 126 */     paramInt >>>= i * 4;
/* 127 */     return paramInt - 1;
/*     */   }
/*     */   
/*     */   static int decodeK(int paramInt1, int paramInt2) {
/* 131 */     assert 0 <= paramInt1 && paramInt1 <= 3;
/* 132 */     assert 0 <= paramInt2 && paramInt2 <= 255;
/* 133 */     return paramInt2 + 1 << paramInt1 * 4;
/*     */   }
/*     */   
/*     */   static int getNextK(int paramInt) {
/* 137 */     if (paramInt <= 0) return 1; 
/* 138 */     int i = getKXOf(paramInt);
/* 139 */     if (i < 0) return Integer.MAX_VALUE;
/*     */     
/* 141 */     int j = 1 << i * 4;
/* 142 */     int k = 255 << i * 4;
/* 143 */     int m = paramInt + j;
/* 144 */     m &= j - 1 ^ 0xFFFFFFFF;
/* 145 */     if ((m - j & (k ^ 0xFFFFFFFF)) == 0) {
/* 146 */       assert getKXOf(m) == i;
/* 147 */       return m;
/*     */     } 
/* 149 */     if (i == 3) return Integer.MAX_VALUE; 
/* 150 */     i++;
/* 151 */     int n = 255 << i * 4;
/* 152 */     m |= k & (n ^ 0xFFFFFFFF);
/* 153 */     m += j;
/* 154 */     assert getKXOf(m) == i;
/* 155 */     return m;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isCodableLength(int paramInt) {
/* 160 */     int i = getKXOf(paramInt);
/* 161 */     if (i < 0) return false; 
/* 162 */     int j = 1 << i * 4;
/* 163 */     int k = 255 << i * 4;
/* 164 */     return ((paramInt - j & (k ^ 0xFFFFFFFF)) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getMetaCoding(Coding paramCoding) {
/* 170 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
/*     */     try {
/* 172 */       makeMetaCoding(this, paramCoding, byteArrayOutputStream);
/* 173 */     } catch (IOException iOException) {
/* 174 */       throw new RuntimeException(iOException);
/*     */     } 
/* 176 */     return byteArrayOutputStream.toByteArray();
/*     */   }
/*     */   private static void makeMetaCoding(AdaptiveCoding paramAdaptiveCoding, Coding paramCoding, ByteArrayOutputStream paramByteArrayOutputStream) throws IOException {
/*     */     CodingMethod codingMethod;
/*     */     byte b;
/*     */     while (true) {
/* 182 */       CodingMethod codingMethod1 = paramAdaptiveCoding.headCoding;
/* 183 */       int i = paramAdaptiveCoding.headLength;
/* 184 */       codingMethod = paramAdaptiveCoding.tailCoding;
/* 185 */       int j = i;
/* 186 */       assert isCodableLength(j);
/* 187 */       byte b1 = (codingMethod1 == paramCoding) ? 1 : 0;
/* 188 */       b = (codingMethod == paramCoding) ? 1 : 0;
/* 189 */       if (b1 + b > 1) b = 0; 
/* 190 */       int k = 1 * b1 + 2 * b;
/* 191 */       assert k < 3;
/* 192 */       int m = getKXOf(j);
/* 193 */       int n = getKBOf(j);
/* 194 */       assert decodeK(m, n) == j;
/* 195 */       byte b2 = (n != 3) ? 1 : 0;
/* 196 */       paramByteArrayOutputStream.write(117 + m + 4 * b2 + 8 * k);
/* 197 */       if (b2 != 0) paramByteArrayOutputStream.write(n); 
/* 198 */       if (b1 == 0) paramByteArrayOutputStream.write(codingMethod1.getMetaCoding(paramCoding)); 
/* 199 */       if (codingMethod instanceof AdaptiveCoding) {
/* 200 */         paramAdaptiveCoding = (AdaptiveCoding)codingMethod; continue;
/*     */       }  break;
/*     */     } 
/* 203 */     if (b == 0) paramByteArrayOutputStream.write(codingMethod.getMetaCoding(paramCoding));
/*     */   
/*     */   }
/*     */   
/*     */   public static int parseMetaCoding(byte[] paramArrayOfbyte, int paramInt, Coding paramCoding, CodingMethod[] paramArrayOfCodingMethod) {
/* 208 */     int i = paramArrayOfbyte[paramInt++] & 0xFF;
/* 209 */     if (i < 117 || i >= 141) return paramInt - 1; 
/* 210 */     AdaptiveCoding adaptiveCoding = null;
/* 211 */     for (boolean bool = true; bool; ) {
/* 212 */       bool = false;
/* 213 */       assert i >= 117;
/* 214 */       i -= 117;
/* 215 */       int j = i % 4;
/* 216 */       int k = i / 4 % 2;
/* 217 */       int m = i / 8;
/* 218 */       assert m < 3;
/* 219 */       int n = m & 0x1;
/* 220 */       int i1 = m & 0x2;
/* 221 */       CodingMethod[] arrayOfCodingMethod1 = { paramCoding }, arrayOfCodingMethod2 = { paramCoding };
/* 222 */       int i2 = 3;
/* 223 */       if (k != 0)
/* 224 */         i2 = paramArrayOfbyte[paramInt++] & 0xFF; 
/* 225 */       if (n == 0) {
/* 226 */         paramInt = BandStructure.parseMetaCoding(paramArrayOfbyte, paramInt, paramCoding, arrayOfCodingMethod1);
/*     */       }
/* 228 */       if (i1 == 0 && (i = paramArrayOfbyte[paramInt] & 0xFF) >= 117 && i < 141) {
/*     */         
/* 230 */         paramInt++;
/* 231 */         bool = true;
/* 232 */       } else if (i1 == 0) {
/* 233 */         paramInt = BandStructure.parseMetaCoding(paramArrayOfbyte, paramInt, paramCoding, arrayOfCodingMethod2);
/*     */       } 
/* 235 */       AdaptiveCoding adaptiveCoding1 = new AdaptiveCoding(decodeK(j, i2), arrayOfCodingMethod1[0], arrayOfCodingMethod2[0]);
/*     */       
/* 237 */       if (adaptiveCoding == null) {
/* 238 */         paramArrayOfCodingMethod[0] = adaptiveCoding1;
/*     */       } else {
/* 240 */         adaptiveCoding.tailCoding = adaptiveCoding1;
/*     */       } 
/* 242 */       adaptiveCoding = adaptiveCoding1;
/*     */     } 
/* 244 */     return paramInt;
/*     */   }
/*     */   
/*     */   private String keyString(CodingMethod paramCodingMethod) {
/* 248 */     if (paramCodingMethod instanceof Coding)
/* 249 */       return ((Coding)paramCodingMethod).keyString(); 
/* 250 */     return paramCodingMethod.toString();
/*     */   }
/*     */   public String toString() {
/* 253 */     StringBuilder stringBuilder = new StringBuilder(20);
/* 254 */     AdaptiveCoding adaptiveCoding = this;
/* 255 */     stringBuilder.append("run(");
/*     */     while (true) {
/* 257 */       stringBuilder.append(adaptiveCoding.headLength).append("*");
/* 258 */       stringBuilder.append(keyString(adaptiveCoding.headCoding));
/* 259 */       if (adaptiveCoding.tailCoding instanceof AdaptiveCoding) {
/* 260 */         adaptiveCoding = (AdaptiveCoding)adaptiveCoding.tailCoding;
/* 261 */         stringBuilder.append(" ");
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 266 */     stringBuilder.append(" **").append(keyString(adaptiveCoding.tailCoding));
/* 267 */     stringBuilder.append(")");
/* 268 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/util/jar/pack/AdaptiveCoding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */