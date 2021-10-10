/*     */ package java.sql;
/*     */ 
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Timestamp
/*     */   extends Date
/*     */ {
/*     */   private int nanos;
/*     */   static final long serialVersionUID = 2745179027874758501L;
/*     */   private static final int MILLIS_PER_SECOND = 1000;
/*     */   
/*     */   @Deprecated
/*     */   public Timestamp(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*  91 */     super(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*  92 */     if (paramInt7 > 999999999 || paramInt7 < 0) {
/*  93 */       throw new IllegalArgumentException("nanos > 999999999 or < 0");
/*     */     }
/*  95 */     this.nanos = paramInt7;
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
/*     */   public Timestamp(long paramLong) {
/* 111 */     super(paramLong / 1000L * 1000L);
/* 112 */     this.nanos = (int)(paramLong % 1000L * 1000000L);
/* 113 */     if (this.nanos < 0) {
/* 114 */       this.nanos = 1000000000 + this.nanos;
/* 115 */       super.setTime((paramLong / 1000L - 1L) * 1000L);
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
/*     */   public void setTime(long paramLong) {
/* 129 */     super.setTime(paramLong / 1000L * 1000L);
/* 130 */     this.nanos = (int)(paramLong % 1000L * 1000000L);
/* 131 */     if (this.nanos < 0) {
/* 132 */       this.nanos = 1000000000 + this.nanos;
/* 133 */       super.setTime((paramLong / 1000L - 1L) * 1000L);
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
/*     */   public long getTime() {
/* 146 */     long l = super.getTime();
/* 147 */     return l + (this.nanos / 1000000);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Timestamp valueOf(String paramString) {
/*     */     String str1, str2;
/* 177 */     int m, n, i1, i = 0;
/* 178 */     int j = 0;
/* 179 */     int k = 0;
/*     */ 
/*     */ 
/*     */     
/* 183 */     int i2 = 0;
/*     */ 
/*     */ 
/*     */     
/* 187 */     int i6 = 0;
/* 188 */     int i7 = 0;
/* 189 */     int i8 = 0;
/* 190 */     String str3 = "Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]";
/* 191 */     String str4 = "000000000";
/* 192 */     String str5 = "-";
/* 193 */     String str6 = ":";
/*     */     
/* 195 */     if (paramString == null) throw new IllegalArgumentException("null string");
/*     */ 
/*     */     
/* 198 */     paramString = paramString.trim();
/* 199 */     int i5 = paramString.indexOf(' ');
/* 200 */     if (i5 > 0) {
/* 201 */       str1 = paramString.substring(0, i5);
/* 202 */       str2 = paramString.substring(i5 + 1);
/*     */     } else {
/* 204 */       throw new IllegalArgumentException(str3);
/*     */     } 
/*     */ 
/*     */     
/* 208 */     int i3 = str1.indexOf('-');
/* 209 */     int i4 = str1.indexOf('-', i3 + 1);
/*     */ 
/*     */     
/* 212 */     if (str2 == null)
/* 213 */       throw new IllegalArgumentException(str3); 
/* 214 */     i6 = str2.indexOf(':');
/* 215 */     i7 = str2.indexOf(':', i6 + 1);
/* 216 */     i8 = str2.indexOf('.', i7 + 1);
/*     */ 
/*     */     
/* 219 */     boolean bool = false;
/* 220 */     if (i3 > 0 && i4 > 0 && i4 < str1.length() - 1) {
/* 221 */       String str7 = str1.substring(0, i3);
/* 222 */       String str8 = str1.substring(i3 + 1, i4);
/* 223 */       String str9 = str1.substring(i4 + 1);
/* 224 */       if (str7.length() == 4 && str8
/* 225 */         .length() >= 1 && str8.length() <= 2 && str9
/* 226 */         .length() >= 1 && str9.length() <= 2) {
/* 227 */         i = Integer.parseInt(str7);
/* 228 */         j = Integer.parseInt(str8);
/* 229 */         k = Integer.parseInt(str9);
/*     */         
/* 231 */         if (j >= 1 && j <= 12 && k >= 1 && k <= 31) {
/* 232 */           bool = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 236 */     if (!bool) {
/* 237 */       throw new IllegalArgumentException(str3);
/*     */     }
/*     */ 
/*     */     
/* 241 */     if ((((i6 > 0) ? 1 : 0) & ((i7 > 0) ? 1 : 0) & (
/* 242 */       (i7 < str2.length() - 1) ? 1 : 0)) != 0) {
/* 243 */       m = Integer.parseInt(str2.substring(0, i6));
/*     */       
/* 245 */       n = Integer.parseInt(str2.substring(i6 + 1, i7));
/* 246 */       if ((((i8 > 0) ? 1 : 0) & ((i8 < str2.length() - 1) ? 1 : 0)) != 0)
/*     */       
/* 248 */       { i1 = Integer.parseInt(str2.substring(i7 + 1, i8));
/* 249 */         String str = str2.substring(i8 + 1);
/* 250 */         if (str.length() > 9)
/* 251 */           throw new IllegalArgumentException(str3); 
/* 252 */         if (!Character.isDigit(str.charAt(0)))
/* 253 */           throw new IllegalArgumentException(str3); 
/* 254 */         str = str + str4.substring(0, 9 - str.length());
/* 255 */         i2 = Integer.parseInt(str); }
/* 256 */       else { if (i8 > 0) {
/* 257 */           throw new IllegalArgumentException(str3);
/*     */         }
/* 259 */         i1 = Integer.parseInt(str2.substring(i7 + 1)); }
/*     */     
/*     */     } else {
/* 262 */       throw new IllegalArgumentException(str3);
/*     */     } 
/*     */     
/* 265 */     return new Timestamp(i - 1900, j - 1, k, m, n, i1, i2);
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
/*     */   public String toString() {
/*     */     String str1, str2, str3, str4, str5, str6, str7;
/* 279 */     int i = getYear() + 1900;
/* 280 */     int j = getMonth() + 1;
/* 281 */     int k = getDate();
/* 282 */     int m = getHours();
/* 283 */     int n = getMinutes();
/* 284 */     int i1 = getSeconds();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     String str8 = "000000000";
/* 293 */     String str9 = "0000";
/*     */ 
/*     */     
/* 296 */     if (i < 1000) {
/*     */       
/* 298 */       str1 = "" + i;
/* 299 */       str1 = str9.substring(0, 4 - str1.length()) + str1;
/*     */     } else {
/*     */       
/* 302 */       str1 = "" + i;
/*     */     } 
/* 304 */     if (j < 10) {
/* 305 */       str2 = "0" + j;
/*     */     } else {
/* 307 */       str2 = Integer.toString(j);
/*     */     } 
/* 309 */     if (k < 10) {
/* 310 */       str3 = "0" + k;
/*     */     } else {
/* 312 */       str3 = Integer.toString(k);
/*     */     } 
/* 314 */     if (m < 10) {
/* 315 */       str4 = "0" + m;
/*     */     } else {
/* 317 */       str4 = Integer.toString(m);
/*     */     } 
/* 319 */     if (n < 10) {
/* 320 */       str5 = "0" + n;
/*     */     } else {
/* 322 */       str5 = Integer.toString(n);
/*     */     } 
/* 324 */     if (i1 < 10) {
/* 325 */       str6 = "0" + i1;
/*     */     } else {
/* 327 */       str6 = Integer.toString(i1);
/*     */     } 
/* 329 */     if (this.nanos == 0) {
/* 330 */       str7 = "0";
/*     */     } else {
/* 332 */       str7 = Integer.toString(this.nanos);
/*     */ 
/*     */       
/* 335 */       str7 = str8.substring(0, 9 - str7.length()) + str7;
/*     */ 
/*     */ 
/*     */       
/* 339 */       char[] arrayOfChar = new char[str7.length()];
/* 340 */       str7.getChars(0, str7.length(), arrayOfChar, 0);
/* 341 */       byte b = 8;
/* 342 */       while (arrayOfChar[b] == '0') {
/* 343 */         b--;
/*     */       }
/*     */       
/* 346 */       str7 = new String(arrayOfChar, 0, b + 1);
/*     */     } 
/*     */ 
/*     */     
/* 350 */     StringBuffer stringBuffer = new StringBuffer(20 + str7.length());
/* 351 */     stringBuffer.append(str1);
/* 352 */     stringBuffer.append("-");
/* 353 */     stringBuffer.append(str2);
/* 354 */     stringBuffer.append("-");
/* 355 */     stringBuffer.append(str3);
/* 356 */     stringBuffer.append(" ");
/* 357 */     stringBuffer.append(str4);
/* 358 */     stringBuffer.append(":");
/* 359 */     stringBuffer.append(str5);
/* 360 */     stringBuffer.append(":");
/* 361 */     stringBuffer.append(str6);
/* 362 */     stringBuffer.append(".");
/* 363 */     stringBuffer.append(str7);
/*     */     
/* 365 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNanos() {
/* 375 */     return this.nanos;
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
/*     */   public void setNanos(int paramInt) {
/* 388 */     if (paramInt > 999999999 || paramInt < 0) {
/* 389 */       throw new IllegalArgumentException("nanos > 999999999 or < 0");
/*     */     }
/* 391 */     this.nanos = paramInt;
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
/*     */   public boolean equals(Timestamp paramTimestamp) {
/* 404 */     if (super.equals(paramTimestamp)) {
/* 405 */       if (this.nanos == paramTimestamp.nanos) {
/* 406 */         return true;
/*     */       }
/* 408 */       return false;
/*     */     } 
/*     */     
/* 411 */     return false;
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
/*     */   public boolean equals(Object paramObject) {
/* 434 */     if (paramObject instanceof Timestamp) {
/* 435 */       return equals((Timestamp)paramObject);
/*     */     }
/* 437 */     return false;
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
/*     */   public boolean before(Timestamp paramTimestamp) {
/* 450 */     return (compareTo(paramTimestamp) < 0);
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
/*     */   public boolean after(Timestamp paramTimestamp) {
/* 462 */     return (compareTo(paramTimestamp) > 0);
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
/*     */   public int compareTo(Timestamp paramTimestamp) {
/* 479 */     long l1 = getTime();
/* 480 */     long l2 = paramTimestamp.getTime();
/* 481 */     boolean bool = (l1 < l2) ? true : ((l1 == l2) ? false : true);
/* 482 */     if (!bool) {
/* 483 */       if (this.nanos > paramTimestamp.nanos)
/* 484 */         return 1; 
/* 485 */       if (this.nanos < paramTimestamp.nanos) {
/* 486 */         return -1;
/*     */       }
/*     */     } 
/* 489 */     return bool;
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
/*     */   public int compareTo(Date paramDate) {
/* 507 */     if (paramDate instanceof Timestamp)
/*     */     {
/*     */ 
/*     */       
/* 511 */       return compareTo((Timestamp)paramDate);
/*     */     }
/*     */ 
/*     */     
/* 515 */     Timestamp timestamp = new Timestamp(paramDate.getTime());
/* 516 */     return compareTo(timestamp);
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
/*     */   public int hashCode() {
/* 529 */     return super.hashCode();
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
/*     */   public static Timestamp valueOf(LocalDateTime paramLocalDateTime) {
/* 551 */     return new Timestamp(paramLocalDateTime.getYear() - 1900, paramLocalDateTime
/* 552 */         .getMonthValue() - 1, paramLocalDateTime
/* 553 */         .getDayOfMonth(), paramLocalDateTime
/* 554 */         .getHour(), paramLocalDateTime
/* 555 */         .getMinute(), paramLocalDateTime
/* 556 */         .getSecond(), paramLocalDateTime
/* 557 */         .getNano());
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
/*     */   public LocalDateTime toLocalDateTime() {
/* 572 */     return LocalDateTime.of(getYear() + 1900, 
/* 573 */         getMonth() + 1, 
/* 574 */         getDate(), 
/* 575 */         getHours(), 
/* 576 */         getMinutes(), 
/* 577 */         getSeconds(), 
/* 578 */         getNanos());
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
/*     */   public static Timestamp from(Instant paramInstant) {
/*     */     try {
/* 598 */       Timestamp timestamp = new Timestamp(paramInstant.getEpochSecond() * 1000L);
/* 599 */       timestamp.nanos = paramInstant.getNano();
/* 600 */       return timestamp;
/* 601 */     } catch (ArithmeticException arithmeticException) {
/* 602 */       throw new IllegalArgumentException(arithmeticException);
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
/*     */   public Instant toInstant() {
/* 617 */     return Instant.ofEpochSecond(super.getTime() / 1000L, this.nanos);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/Timestamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */