/*     */ package java.util.prefs;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Base64
/*     */ {
/*     */   static String byteArrayToBase64(byte[] paramArrayOfbyte) {
/*  42 */     return byteArrayToBase64(paramArrayOfbyte, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String byteArrayToAltBase64(byte[] paramArrayOfbyte) {
/*  52 */     return byteArrayToBase64(paramArrayOfbyte, true);
/*     */   }
/*     */   
/*     */   private static String byteArrayToBase64(byte[] paramArrayOfbyte, boolean paramBoolean) {
/*  56 */     int i = paramArrayOfbyte.length;
/*  57 */     int j = i / 3;
/*  58 */     int k = i - 3 * j;
/*  59 */     int m = 4 * (i + 2) / 3;
/*  60 */     StringBuffer stringBuffer = new StringBuffer(m);
/*  61 */     char[] arrayOfChar = paramBoolean ? intToAltBase64 : intToBase64;
/*     */ 
/*     */     
/*  64 */     byte b = 0; int n;
/*  65 */     for (n = 0; n < j; n++) {
/*  66 */       int i1 = paramArrayOfbyte[b++] & 0xFF;
/*  67 */       int i2 = paramArrayOfbyte[b++] & 0xFF;
/*  68 */       int i3 = paramArrayOfbyte[b++] & 0xFF;
/*  69 */       stringBuffer.append(arrayOfChar[i1 >> 2]);
/*  70 */       stringBuffer.append(arrayOfChar[i1 << 4 & 0x3F | i2 >> 4]);
/*  71 */       stringBuffer.append(arrayOfChar[i2 << 2 & 0x3F | i3 >> 6]);
/*  72 */       stringBuffer.append(arrayOfChar[i3 & 0x3F]);
/*     */     } 
/*     */ 
/*     */     
/*  76 */     if (k != 0) {
/*  77 */       n = paramArrayOfbyte[b++] & 0xFF;
/*  78 */       stringBuffer.append(arrayOfChar[n >> 2]);
/*  79 */       if (k == 1) {
/*  80 */         stringBuffer.append(arrayOfChar[n << 4 & 0x3F]);
/*  81 */         stringBuffer.append("==");
/*     */       } else {
/*     */         
/*  84 */         int i1 = paramArrayOfbyte[b++] & 0xFF;
/*  85 */         stringBuffer.append(arrayOfChar[n << 4 & 0x3F | i1 >> 4]);
/*  86 */         stringBuffer.append(arrayOfChar[i1 << 2 & 0x3F]);
/*  87 */         stringBuffer.append('=');
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static final char[] intToBase64 = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private static final char[] intToAltBase64 = new char[] { '!', '"', '#', '$', '%', '&', '\'', '(', ')', ',', '-', '.', ':', ';', '<', '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '?' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] base64ToByteArray(String paramString) {
/* 131 */     return base64ToByteArray(paramString, false);
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
/*     */   static byte[] altBase64ToByteArray(String paramString) {
/* 143 */     return base64ToByteArray(paramString, true);
/*     */   }
/*     */   
/*     */   private static byte[] base64ToByteArray(String paramString, boolean paramBoolean) {
/* 147 */     byte[] arrayOfByte1 = paramBoolean ? altBase64ToInt : base64ToInt;
/* 148 */     int i = paramString.length();
/* 149 */     int j = i / 4;
/* 150 */     if (4 * j != i) {
/* 151 */       throw new IllegalArgumentException("String length must be a multiple of four.");
/*     */     }
/* 153 */     byte b1 = 0;
/* 154 */     int k = j;
/* 155 */     if (i != 0) {
/* 156 */       if (paramString.charAt(i - 1) == '=') {
/* 157 */         b1++;
/* 158 */         k--;
/*     */       } 
/* 160 */       if (paramString.charAt(i - 2) == '=')
/* 161 */         b1++; 
/*     */     } 
/* 163 */     byte[] arrayOfByte2 = new byte[3 * j - b1];
/*     */ 
/*     */     
/* 166 */     byte b2 = 0, b3 = 0; int m;
/* 167 */     for (m = 0; m < k; m++) {
/* 168 */       int n = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 169 */       int i1 = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 170 */       int i2 = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 171 */       int i3 = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 172 */       arrayOfByte2[b3++] = (byte)(n << 2 | i1 >> 4);
/* 173 */       arrayOfByte2[b3++] = (byte)(i1 << 4 | i2 >> 2);
/* 174 */       arrayOfByte2[b3++] = (byte)(i2 << 6 | i3);
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (b1 != 0) {
/* 179 */       m = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 180 */       int n = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 181 */       arrayOfByte2[b3++] = (byte)(m << 2 | n >> 4);
/*     */       
/* 183 */       if (b1 == 1) {
/* 184 */         int i1 = base64toInt(paramString.charAt(b2++), arrayOfByte1);
/* 185 */         arrayOfByte2[b3++] = (byte)(n << 4 | i1 >> 2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 190 */     return arrayOfByte2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int base64toInt(char paramChar, byte[] paramArrayOfbyte) {
/* 201 */     byte b = paramArrayOfbyte[paramChar];
/* 202 */     if (b < 0)
/* 203 */       throw new IllegalArgumentException("Illegal character " + paramChar); 
/* 204 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 214 */   private static final byte[] base64ToInt = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   private static final byte[] altBase64ToInt = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, 62, 9, 10, 11, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 22, 23, 24, 25 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 240 */     int i = Integer.parseInt(paramArrayOfString[0]);
/* 241 */     int j = Integer.parseInt(paramArrayOfString[1]);
/* 242 */     Random random = new Random();
/* 243 */     for (byte b = 0; b < i; b++) {
/* 244 */       for (byte b1 = 0; b1 < j; b1++) {
/* 245 */         byte[] arrayOfByte1 = new byte[b1];
/* 246 */         for (byte b2 = 0; b2 < b1; b2++) {
/* 247 */           arrayOfByte1[b2] = (byte)random.nextInt();
/*     */         }
/* 249 */         String str = byteArrayToBase64(arrayOfByte1);
/* 250 */         byte[] arrayOfByte2 = base64ToByteArray(str);
/* 251 */         if (!Arrays.equals(arrayOfByte1, arrayOfByte2)) {
/* 252 */           System.out.println("Dismal failure!");
/*     */         }
/* 254 */         str = byteArrayToAltBase64(arrayOfByte1);
/* 255 */         arrayOfByte2 = altBase64ToByteArray(str);
/* 256 */         if (!Arrays.equals(arrayOfByte1, arrayOfByte2))
/* 257 */           System.out.println("Alternate dismal failure!"); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/prefs/Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */