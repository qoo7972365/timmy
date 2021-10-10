/*     */ package java.lang;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CharacterDataLatin1
/*     */   extends CharacterData
/*     */ {
/*     */   int getProperties(int paramInt) {
/*  71 */     char c = (char)paramInt;
/*  72 */     return A[c];
/*     */   }
/*     */ 
/*     */   
/*     */   int getPropertiesEx(int paramInt) {
/*  77 */     char c = (char)paramInt;
/*  78 */     return B[c];
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isOtherLowercase(int paramInt) {
/*  83 */     int i = getPropertiesEx(paramInt);
/*  84 */     return ((i & 0x1) != 0);
/*     */   }
/*     */   
/*     */   boolean isOtherUppercase(int paramInt) {
/*  88 */     int i = getPropertiesEx(paramInt);
/*  89 */     return ((i & 0x2) != 0);
/*     */   }
/*     */   
/*     */   boolean isOtherAlphabetic(int paramInt) {
/*  93 */     int i = getPropertiesEx(paramInt);
/*  94 */     return ((i & 0x4) != 0);
/*     */   }
/*     */   
/*     */   boolean isIdeographic(int paramInt) {
/*  98 */     int i = getPropertiesEx(paramInt);
/*  99 */     return ((i & 0x10) != 0);
/*     */   }
/*     */   
/*     */   int getType(int paramInt) {
/* 103 */     int i = getProperties(paramInt);
/* 104 */     return i & 0x1F;
/*     */   }
/*     */   
/*     */   boolean isJavaIdentifierStart(int paramInt) {
/* 108 */     int i = getProperties(paramInt);
/* 109 */     return ((i & 0x7000) >= 20480);
/*     */   }
/*     */   
/*     */   boolean isJavaIdentifierPart(int paramInt) {
/* 113 */     int i = getProperties(paramInt);
/* 114 */     return ((i & 0x3000) != 0);
/*     */   }
/*     */   
/*     */   boolean isUnicodeIdentifierStart(int paramInt) {
/* 118 */     int i = getProperties(paramInt);
/* 119 */     return ((i & 0x7000) == 28672);
/*     */   }
/*     */   
/*     */   boolean isUnicodeIdentifierPart(int paramInt) {
/* 123 */     int i = getProperties(paramInt);
/* 124 */     return ((i & 0x1000) != 0);
/*     */   }
/*     */   
/*     */   boolean isIdentifierIgnorable(int paramInt) {
/* 128 */     int i = getProperties(paramInt);
/* 129 */     return ((i & 0x7000) == 4096);
/*     */   }
/*     */   
/*     */   int toLowerCase(int paramInt) {
/* 133 */     int i = paramInt;
/* 134 */     int j = getProperties(paramInt);
/*     */     
/* 136 */     if ((j & 0x20000) != 0 && (j & 0x7FC0000) != 133955584) {
/*     */       
/* 138 */       int k = j << 5 >> 23;
/* 139 */       i = paramInt + k;
/*     */     } 
/* 141 */     return i;
/*     */   }
/*     */   
/*     */   int toUpperCase(int paramInt) {
/* 145 */     int i = paramInt;
/* 146 */     int j = getProperties(paramInt);
/*     */     
/* 148 */     if ((j & 0x10000) != 0) {
/* 149 */       if ((j & 0x7FC0000) != 133955584) {
/* 150 */         int k = j << 5 >> 23;
/* 151 */         i = paramInt - k;
/* 152 */       } else if (paramInt == 181) {
/* 153 */         i = 924;
/*     */       } 
/*     */     }
/* 156 */     return i;
/*     */   }
/*     */   
/*     */   int toTitleCase(int paramInt) {
/* 160 */     return toUpperCase(paramInt);
/*     */   }
/*     */   
/*     */   int digit(int paramInt1, int paramInt2) {
/* 164 */     int i = -1;
/* 165 */     if (paramInt2 >= 2 && paramInt2 <= 36) {
/* 166 */       int j = getProperties(paramInt1);
/* 167 */       int k = j & 0x1F;
/* 168 */       if (k == 9) {
/* 169 */         i = paramInt1 + ((j & 0x3E0) >> 5) & 0x1F;
/*     */       }
/* 171 */       else if ((j & 0xC00) == 3072) {
/*     */         
/* 173 */         i = (paramInt1 + ((j & 0x3E0) >> 5) & 0x1F) + 10;
/*     */       } 
/*     */     } 
/* 176 */     return (i < paramInt2) ? i : -1;
/*     */   }
/*     */   
/*     */   int getNumericValue(int paramInt) {
/* 180 */     int i = getProperties(paramInt);
/* 181 */     int j = -1;
/*     */     
/* 183 */     switch (i & 0xC00)
/*     */     
/*     */     { default:
/* 186 */         j = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 198 */         return j;case 1024: j = paramInt + ((i & 0x3E0) >> 5) & 0x1F; return j;case 2048: j = -2; return j;case 3072: break; }  j = (paramInt + ((i & 0x3E0) >> 5) & 0x1F) + 10; return j;
/*     */   }
/*     */   
/*     */   boolean isWhitespace(int paramInt) {
/* 202 */     int i = getProperties(paramInt);
/* 203 */     return ((i & 0x7000) == 16384);
/*     */   }
/*     */   
/*     */   byte getDirectionality(int paramInt) {
/* 207 */     int i = getProperties(paramInt);
/* 208 */     byte b = (byte)((i & 0x78000000) >> 27);
/*     */     
/* 210 */     if (b == 15) {
/* 211 */       b = -1;
/*     */     }
/* 213 */     return b;
/*     */   }
/*     */   
/*     */   boolean isMirrored(int paramInt) {
/* 217 */     int i = getProperties(paramInt);
/* 218 */     return ((i & Integer.MIN_VALUE) != 0);
/*     */   }
/*     */   
/*     */   int toUpperCaseEx(int paramInt) {
/* 222 */     int i = paramInt;
/* 223 */     int j = getProperties(paramInt);
/*     */     
/* 225 */     if ((j & 0x10000) != 0)
/* 226 */       if ((j & 0x7FC0000) != 133955584)
/* 227 */       { int k = j << 5 >> 23;
/* 228 */         i = paramInt - k; }
/*     */       else
/*     */       
/* 231 */       { switch (paramInt)
/*     */         { case 181:
/* 233 */             i = 924;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 238 */             return i; }  i = -1; }   return i;
/*     */   }
/*     */   
/* 241 */   static char[] sharpsMap = new char[] { 'S', 'S' };
/*     */   
/*     */   char[] toUpperCaseCharArray(int paramInt) {
/* 244 */     char[] arrayOfChar = { (char)paramInt };
/* 245 */     if (paramInt == 223) {
/* 246 */       arrayOfChar = sharpsMap;
/*     */     }
/* 248 */     return arrayOfChar;
/*     */   }
/*     */   
/* 251 */   static final CharacterDataLatin1 instance = new CharacterDataLatin1();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 258 */   static final int[] A = new int[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String A_DATA = "䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ堀䀏倀䀏堀䀏怀䀏倀䀏䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ倀䀏倀䀏倀䀏堀䀏怀䀌栀\030栀\030⠀\030⠀怚⠀\030栀\030栀\030\025\026栀\030 \031㠀\030 \024㠀\030㠀\030᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉㠀\030栀\030\031栀\031\031栀\030栀\030翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡\025栀\030\026栀\033栀倗栀\033翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢\025栀\031\026栀\031䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ倀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ㠀\f栀\030⠀怚⠀怚⠀怚⠀怚栀\034栀\030栀\033栀\034\000瀅\035栀\031䠀တ栀\034栀\033⠀\034⠀\031᠀؋᠀؋栀\033߽瀂栀\030栀\030栀\033᠀ԋ\000瀅\036栀ࠋ栀ࠋ栀ࠋ栀\030瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁栀\031瀁瀁瀁瀁瀁瀁瀁߽瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂栀\031瀂瀂瀂瀂瀂瀂瀂؝瀂";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 300 */   static final char[] B = "\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\001\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     .toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 320 */     char[] arrayOfChar = "䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ堀䀏倀䀏堀䀏怀䀏倀䀏䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ倀䀏倀䀏倀䀏堀䀏怀䀌栀\030栀\030⠀\030⠀怚⠀\030栀\030栀\030\025\026栀\030 \031㠀\030 \024㠀\030㠀\030᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉᠀㘉㠀\030栀\030\031栀\031\031栀\030栀\030翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡翡\025栀\030\026栀\033栀倗栀\033翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢翢\025栀\031\026栀\031䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ倀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ䠀ဏ㠀\f栀\030⠀怚⠀怚⠀怚⠀怚栀\034栀\030栀\033栀\034\000瀅\035栀\031䠀တ栀\034栀\033⠀\034⠀\031᠀؋᠀؋栀\033߽瀂栀\030栀\030栀\033᠀ԋ\000瀅\036栀ࠋ栀ࠋ栀ࠋ栀\030瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁瀁栀\031瀁瀁瀁瀁瀁瀁瀁߽瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂瀂栀\031瀂瀂瀂瀂瀂瀂瀂؝瀂".toCharArray();
/* 321 */     assert arrayOfChar.length == 512;
/* 322 */     byte b1 = 0, b2 = 0;
/* 323 */     while (b1 < 'Ȁ') {
/* 324 */       int i = arrayOfChar[b1++] << 16;
/* 325 */       A[b2++] = i | arrayOfChar[b1++];
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/CharacterDataLatin1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */