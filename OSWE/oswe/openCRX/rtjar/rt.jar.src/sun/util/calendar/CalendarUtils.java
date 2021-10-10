/*     */ package sun.util.calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CalendarUtils
/*     */ {
/*     */   public static final boolean isGregorianLeapYear(int paramInt) {
/*  43 */     return (paramInt % 4 == 0 && (paramInt % 100 != 0 || paramInt % 400 == 0));
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
/*     */   public static final boolean isJulianLeapYear(int paramInt) {
/*  58 */     return (paramInt % 4 == 0);
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
/*     */   public static final long floorDivide(long paramLong1, long paramLong2) {
/*  71 */     return (paramLong1 >= 0L) ? (paramLong1 / paramLong2) : ((paramLong1 + 1L) / paramLong2 - 1L);
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
/*     */   public static final int floorDivide(int paramInt1, int paramInt2) {
/*  85 */     return (paramInt1 >= 0) ? (paramInt1 / paramInt2) : ((paramInt1 + 1) / paramInt2 - 1);
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
/*     */   public static final int floorDivide(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/* 103 */     if (paramInt1 >= 0) {
/* 104 */       paramArrayOfint[0] = paramInt1 % paramInt2;
/* 105 */       return paramInt1 / paramInt2;
/*     */     } 
/* 107 */     int i = (paramInt1 + 1) / paramInt2 - 1;
/* 108 */     paramArrayOfint[0] = paramInt1 - i * paramInt2;
/* 109 */     return i;
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
/*     */   public static final int floorDivide(long paramLong, int paramInt, int[] paramArrayOfint) {
/* 126 */     if (paramLong >= 0L) {
/* 127 */       paramArrayOfint[0] = (int)(paramLong % paramInt);
/* 128 */       return (int)(paramLong / paramInt);
/*     */     } 
/* 130 */     int i = (int)((paramLong + 1L) / paramInt - 1L);
/* 131 */     paramArrayOfint[0] = (int)(paramLong - (i * paramInt));
/* 132 */     return i;
/*     */   }
/*     */   
/*     */   public static final long mod(long paramLong1, long paramLong2) {
/* 136 */     return paramLong1 - paramLong2 * floorDivide(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   public static final int mod(int paramInt1, int paramInt2) {
/* 140 */     return paramInt1 - paramInt2 * floorDivide(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public static final int amod(int paramInt1, int paramInt2) {
/* 144 */     int i = mod(paramInt1, paramInt2);
/* 145 */     return (i == 0) ? paramInt2 : i;
/*     */   }
/*     */   
/*     */   public static final long amod(long paramLong1, long paramLong2) {
/* 149 */     long l = mod(paramLong1, paramLong2);
/* 150 */     return (l == 0L) ? paramLong2 : l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final StringBuilder sprintf0d(StringBuilder paramStringBuilder, int paramInt1, int paramInt2) {
/* 157 */     long l = paramInt1;
/* 158 */     if (l < 0L) {
/* 159 */       paramStringBuilder.append('-');
/* 160 */       l = -l;
/* 161 */       paramInt2--;
/*     */     } 
/* 163 */     int i = 10; byte b;
/* 164 */     for (b = 2; b < paramInt2; b++) {
/* 165 */       i *= 10;
/*     */     }
/* 167 */     for (b = 1; b < paramInt2 && l < i; b++) {
/* 168 */       paramStringBuilder.append('0');
/* 169 */       i /= 10;
/*     */     } 
/* 171 */     paramStringBuilder.append(l);
/* 172 */     return paramStringBuilder;
/*     */   }
/*     */   
/*     */   public static final StringBuffer sprintf0d(StringBuffer paramStringBuffer, int paramInt1, int paramInt2) {
/* 176 */     long l = paramInt1;
/* 177 */     if (l < 0L) {
/* 178 */       paramStringBuffer.append('-');
/* 179 */       l = -l;
/* 180 */       paramInt2--;
/*     */     } 
/* 182 */     int i = 10; byte b;
/* 183 */     for (b = 2; b < paramInt2; b++) {
/* 184 */       i *= 10;
/*     */     }
/* 186 */     for (b = 1; b < paramInt2 && l < i; b++) {
/* 187 */       paramStringBuffer.append('0');
/* 188 */       i /= 10;
/*     */     } 
/* 190 */     paramStringBuffer.append(l);
/* 191 */     return paramStringBuffer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/CalendarUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */