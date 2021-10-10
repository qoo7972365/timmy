/*     */ package sun.misc;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormattedFloatingDecimal
/*     */ {
/*     */   private int decExponentRounded;
/*     */   private char[] mantissa;
/*     */   private char[] exponent;
/*     */   
/*     */   public enum Form
/*     */   {
/*  32 */     SCIENTIFIC, COMPATIBLE, DECIMAL_FLOAT, GENERAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FormattedFloatingDecimal valueOf(double paramDouble, int paramInt, Form paramForm) {
/*  37 */     FloatingDecimal.BinaryToASCIIConverter binaryToASCIIConverter = FloatingDecimal.getBinaryToASCIIConverter(paramDouble, (paramForm == Form.COMPATIBLE));
/*  38 */     return new FormattedFloatingDecimal(paramInt, paramForm, binaryToASCIIConverter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final ThreadLocal<Object> threadLocalCharBuffer = new ThreadLocal()
/*     */     {
/*     */       protected Object initialValue()
/*     */       {
/*  49 */         return new char[20];
/*     */       }
/*     */     };
/*     */   
/*     */   private static char[] getBuffer() {
/*  54 */     return (char[])threadLocalCharBuffer.get();
/*     */   }
/*     */   private FormattedFloatingDecimal(int paramInt, Form paramForm, FloatingDecimal.BinaryToASCIIConverter paramBinaryToASCIIConverter) {
/*     */     int k;
/*  58 */     if (paramBinaryToASCIIConverter.isExceptional()) {
/*  59 */       this.mantissa = paramBinaryToASCIIConverter.toJavaFormatString().toCharArray();
/*  60 */       this.exponent = null;
/*     */       return;
/*     */     } 
/*  63 */     char[] arrayOfChar = getBuffer();
/*  64 */     int i = paramBinaryToASCIIConverter.getDigits(arrayOfChar);
/*  65 */     int j = paramBinaryToASCIIConverter.getDecimalExponent();
/*     */     
/*  67 */     boolean bool = paramBinaryToASCIIConverter.isNegative();
/*  68 */     switch (paramForm) {
/*     */       case COMPATIBLE:
/*  70 */         k = j;
/*  71 */         this.decExponentRounded = k;
/*  72 */         fillCompatible(paramInt, arrayOfChar, i, k, bool);
/*     */         return;
/*     */       case DECIMAL_FLOAT:
/*  75 */         k = applyPrecision(j, arrayOfChar, i, j + paramInt);
/*  76 */         fillDecimal(paramInt, arrayOfChar, i, k, bool);
/*  77 */         this.decExponentRounded = k;
/*     */         return;
/*     */       case SCIENTIFIC:
/*  80 */         k = applyPrecision(j, arrayOfChar, i, paramInt + 1);
/*  81 */         fillScientific(paramInt, arrayOfChar, i, k, bool);
/*  82 */         this.decExponentRounded = k;
/*     */         return;
/*     */       case GENERAL:
/*  85 */         k = applyPrecision(j, arrayOfChar, i, paramInt);
/*     */ 
/*     */         
/*  88 */         if (k - 1 < -4 || k - 1 >= paramInt) {
/*     */           
/*  90 */           paramInt--;
/*  91 */           fillScientific(paramInt, arrayOfChar, i, k, bool);
/*     */         } else {
/*     */           
/*  94 */           paramInt -= k;
/*  95 */           fillDecimal(paramInt, arrayOfChar, i, k, bool);
/*     */         } 
/*  97 */         this.decExponentRounded = k;
/*     */         return;
/*     */     } 
/*     */     assert false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExponentRounded() {
/* 106 */     return this.decExponentRounded - 1;
/*     */   }
/*     */   
/*     */   public char[] getMantissa() {
/* 110 */     return this.mantissa;
/*     */   }
/*     */   
/*     */   public char[] getExponent() {
/* 114 */     return this.exponent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int applyPrecision(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3) {
/* 121 */     if (paramInt3 >= paramInt2 || paramInt3 < 0)
/*     */     {
/* 123 */       return paramInt1;
/*     */     }
/* 125 */     if (paramInt3 == 0) {
/*     */ 
/*     */       
/* 128 */       if (paramArrayOfchar[0] >= '5') {
/* 129 */         paramArrayOfchar[0] = '1';
/* 130 */         Arrays.fill(paramArrayOfchar, 1, paramInt2, '0');
/* 131 */         return paramInt1 + 1;
/*     */       } 
/* 133 */       Arrays.fill(paramArrayOfchar, 0, paramInt2, '0');
/* 134 */       return paramInt1;
/*     */     } 
/*     */     
/* 137 */     char c = paramArrayOfchar[paramInt3];
/* 138 */     if (c >= '5') {
/* 139 */       int i = paramInt3;
/* 140 */       c = paramArrayOfchar[--i];
/* 141 */       if (c == '9') {
/* 142 */         while (c == '9' && i > 0) {
/* 143 */           c = paramArrayOfchar[--i];
/*     */         }
/* 145 */         if (c == '9') {
/*     */           
/* 147 */           paramArrayOfchar[0] = '1';
/* 148 */           Arrays.fill(paramArrayOfchar, 1, paramInt2, '0');
/* 149 */           return paramInt1 + 1;
/*     */         } 
/*     */       } 
/* 152 */       paramArrayOfchar[i] = (char)(c + 1);
/* 153 */       Arrays.fill(paramArrayOfchar, i + 1, paramInt2, '0');
/*     */     } else {
/* 155 */       Arrays.fill(paramArrayOfchar, paramInt3, paramInt2, '0');
/*     */     } 
/* 157 */     return paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillCompatible(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 164 */     byte b = paramBoolean ? 1 : 0;
/* 165 */     if (paramInt3 > 0 && paramInt3 < 8) {
/*     */       
/* 167 */       if (paramInt2 < paramInt3) {
/* 168 */         int i = paramInt3 - paramInt2;
/* 169 */         this.mantissa = create(paramBoolean, paramInt2 + i + 2);
/* 170 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b, paramInt2);
/* 171 */         Arrays.fill(this.mantissa, b + paramInt2, b + paramInt2 + i, '0');
/* 172 */         this.mantissa[b + paramInt2 + i] = '.';
/* 173 */         this.mantissa[b + paramInt2 + i + 1] = '0';
/* 174 */       } else if (paramInt3 < paramInt2) {
/* 175 */         int i = Math.min(paramInt2 - paramInt3, paramInt1);
/* 176 */         this.mantissa = create(paramBoolean, paramInt3 + 1 + i);
/* 177 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b, paramInt3);
/* 178 */         this.mantissa[b + paramInt3] = '.';
/* 179 */         System.arraycopy(paramArrayOfchar, paramInt3, this.mantissa, b + paramInt3 + 1, i);
/*     */       } else {
/* 181 */         this.mantissa = create(paramBoolean, paramInt2 + 2);
/* 182 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b, paramInt2);
/* 183 */         this.mantissa[b + paramInt2] = '.';
/* 184 */         this.mantissa[b + paramInt2 + 1] = '0';
/*     */       } 
/* 186 */     } else if (paramInt3 <= 0 && paramInt3 > -3) {
/* 187 */       int i = Math.max(0, Math.min(-paramInt3, paramInt1));
/* 188 */       int j = Math.max(0, Math.min(paramInt2, paramInt1 + paramInt3));
/*     */       
/* 190 */       if (i > 0) {
/* 191 */         this.mantissa = create(paramBoolean, i + 2 + j);
/* 192 */         this.mantissa[b] = '0';
/* 193 */         this.mantissa[b + 1] = '.';
/* 194 */         Arrays.fill(this.mantissa, b + 2, b + 2 + i, '0');
/* 195 */         if (j > 0)
/*     */         {
/* 197 */           System.arraycopy(paramArrayOfchar, 0, this.mantissa, b + 2 + i, j);
/*     */         }
/* 199 */       } else if (j > 0) {
/* 200 */         this.mantissa = create(paramBoolean, i + 2 + j);
/* 201 */         this.mantissa[b] = '0';
/* 202 */         this.mantissa[b + 1] = '.';
/*     */         
/* 204 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b + 2, j);
/*     */       } else {
/* 206 */         this.mantissa = create(paramBoolean, 1);
/* 207 */         this.mantissa[b] = '0';
/*     */       } 
/*     */     } else {
/* 210 */       int i; byte b1; if (paramInt2 > 1) {
/* 211 */         this.mantissa = create(paramBoolean, paramInt2 + 1);
/* 212 */         this.mantissa[b] = paramArrayOfchar[0];
/* 213 */         this.mantissa[b + 1] = '.';
/* 214 */         System.arraycopy(paramArrayOfchar, 1, this.mantissa, b + 2, paramInt2 - 1);
/*     */       } else {
/* 216 */         this.mantissa = create(paramBoolean, 3);
/* 217 */         this.mantissa[b] = paramArrayOfchar[0];
/* 218 */         this.mantissa[b + 1] = '.';
/* 219 */         this.mantissa[b + 2] = '0';
/*     */       } 
/*     */       
/* 222 */       boolean bool = (paramInt3 <= 0) ? true : false;
/* 223 */       if (bool) {
/* 224 */         i = -paramInt3 + 1;
/* 225 */         b1 = 1;
/*     */       } else {
/* 227 */         i = paramInt3 - 1;
/* 228 */         b1 = 0;
/*     */       } 
/*     */       
/* 231 */       if (i <= 9) {
/* 232 */         this.exponent = create(bool, 1);
/* 233 */         this.exponent[b1] = (char)(i + 48);
/* 234 */       } else if (i <= 99) {
/* 235 */         this.exponent = create(bool, 2);
/* 236 */         this.exponent[b1] = (char)(i / 10 + 48);
/* 237 */         this.exponent[b1 + 1] = (char)(i % 10 + 48);
/*     */       } else {
/* 239 */         this.exponent = create(bool, 3);
/* 240 */         this.exponent[b1] = (char)(i / 100 + 48);
/* 241 */         i %= 100;
/* 242 */         this.exponent[b1 + 1] = (char)(i / 10 + 48);
/* 243 */         this.exponent[b1 + 2] = (char)(i % 10 + 48);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static char[] create(boolean paramBoolean, int paramInt) {
/* 249 */     if (paramBoolean) {
/* 250 */       char[] arrayOfChar = new char[paramInt + 1];
/* 251 */       arrayOfChar[0] = '-';
/* 252 */       return arrayOfChar;
/*     */     } 
/* 254 */     return new char[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillDecimal(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 263 */     byte b = paramBoolean ? 1 : 0;
/* 264 */     if (paramInt3 > 0) {
/*     */       
/* 266 */       if (paramInt2 < paramInt3) {
/* 267 */         this.mantissa = create(paramBoolean, paramInt3);
/* 268 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b, paramInt2);
/* 269 */         Arrays.fill(this.mantissa, b + paramInt2, b + paramInt3, '0');
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 274 */         int i = Math.min(paramInt2 - paramInt3, paramInt1);
/* 275 */         this.mantissa = create(paramBoolean, paramInt3 + ((i > 0) ? (i + 1) : 0));
/* 276 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b, paramInt3);
/*     */ 
/*     */ 
/*     */         
/* 280 */         if (i > 0) {
/* 281 */           this.mantissa[b + paramInt3] = '.';
/* 282 */           System.arraycopy(paramArrayOfchar, paramInt3, this.mantissa, b + paramInt3 + 1, i);
/*     */         } 
/*     */       } 
/* 285 */     } else if (paramInt3 <= 0) {
/* 286 */       int i = Math.max(0, Math.min(-paramInt3, paramInt1));
/* 287 */       int j = Math.max(0, Math.min(paramInt2, paramInt1 + paramInt3));
/*     */       
/* 289 */       if (i > 0) {
/* 290 */         this.mantissa = create(paramBoolean, i + 2 + j);
/* 291 */         this.mantissa[b] = '0';
/* 292 */         this.mantissa[b + 1] = '.';
/* 293 */         Arrays.fill(this.mantissa, b + 2, b + 2 + i, '0');
/* 294 */         if (j > 0)
/*     */         {
/* 296 */           System.arraycopy(paramArrayOfchar, 0, this.mantissa, b + 2 + i, j);
/*     */         }
/* 298 */       } else if (j > 0) {
/* 299 */         this.mantissa = create(paramBoolean, i + 2 + j);
/* 300 */         this.mantissa[b] = '0';
/* 301 */         this.mantissa[b + 1] = '.';
/*     */         
/* 303 */         System.arraycopy(paramArrayOfchar, 0, this.mantissa, b + 2, j);
/*     */       } else {
/* 305 */         this.mantissa = create(paramBoolean, 1);
/* 306 */         this.mantissa[b] = '0';
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fillScientific(int paramInt1, char[] paramArrayOfchar, int paramInt2, int paramInt3, boolean paramBoolean) {
/*     */     byte b2;
/*     */     int j;
/* 315 */     byte b1 = paramBoolean ? 1 : 0;
/* 316 */     int i = Math.max(0, Math.min(paramInt2 - 1, paramInt1));
/* 317 */     if (i > 0) {
/* 318 */       this.mantissa = create(paramBoolean, i + 2);
/* 319 */       this.mantissa[b1] = paramArrayOfchar[0];
/* 320 */       this.mantissa[b1 + 1] = '.';
/* 321 */       System.arraycopy(paramArrayOfchar, 1, this.mantissa, b1 + 2, i);
/*     */     } else {
/* 323 */       this.mantissa = create(paramBoolean, 1);
/* 324 */       this.mantissa[b1] = paramArrayOfchar[0];
/*     */     } 
/*     */ 
/*     */     
/* 328 */     if (paramInt3 <= 0) {
/* 329 */       b2 = 45;
/* 330 */       j = -paramInt3 + 1;
/*     */     } else {
/* 332 */       b2 = 43;
/* 333 */       j = paramInt3 - 1;
/*     */     } 
/*     */     
/* 336 */     if (j <= 9) {
/* 337 */       this.exponent = new char[] { b2, '0', (char)(j + 48) };
/*     */     }
/* 339 */     else if (j <= 99) {
/* 340 */       this.exponent = new char[] { b2, (char)(j / 10 + 48), (char)(j % 10 + 48) };
/*     */     } else {
/*     */       
/* 343 */       char c = (char)(j / 100 + 48);
/* 344 */       j %= 100;
/* 345 */       this.exponent = new char[] { b2, c, (char)(j / 10 + 48), (char)(j % 10 + 48) };
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/FormattedFloatingDecimal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */