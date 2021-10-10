/*     */ package java.sql;
/*     */ 
/*     */ import java.time.Instant;
/*     */ import java.time.LocalTime;
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
/*     */ public class Time
/*     */   extends Date
/*     */ {
/*     */   static final long serialVersionUID = 8397324403548013681L;
/*     */   
/*     */   @Deprecated
/*     */   public Time(int paramInt1, int paramInt2, int paramInt3) {
/*  61 */     super(70, 0, 1, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Time(long paramLong) {
/*  72 */     super(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTime(long paramLong) {
/*  83 */     super.setTime(paramLong);
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
/*     */   public static Time valueOf(String paramString) {
/*     */     int i, j, k;
/*  99 */     if (paramString == null) throw new IllegalArgumentException();
/*     */     
/* 101 */     int m = paramString.indexOf(':');
/* 102 */     int n = paramString.indexOf(':', m + 1);
/* 103 */     if ((((m > 0) ? 1 : 0) & ((n > 0) ? 1 : 0) & (
/* 104 */       (n < paramString.length() - 1) ? 1 : 0)) != 0) {
/* 105 */       i = Integer.parseInt(paramString.substring(0, m));
/*     */       
/* 107 */       j = Integer.parseInt(paramString.substring(m + 1, n));
/* 108 */       k = Integer.parseInt(paramString.substring(n + 1));
/*     */     } else {
/* 110 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 113 */     return new Time(i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String str1, str2, str3;
/* 123 */     int i = getHours();
/* 124 */     int j = getMinutes();
/* 125 */     int k = getSeconds();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     if (i < 10) {
/* 131 */       str1 = "0" + i;
/*     */     } else {
/* 133 */       str1 = Integer.toString(i);
/*     */     } 
/* 135 */     if (j < 10) {
/* 136 */       str2 = "0" + j;
/*     */     } else {
/* 138 */       str2 = Integer.toString(j);
/*     */     } 
/* 140 */     if (k < 10) {
/* 141 */       str3 = "0" + k;
/*     */     } else {
/* 143 */       str3 = Integer.toString(k);
/*     */     } 
/* 145 */     return str1 + ":" + str2 + ":" + str3;
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
/*     */   @Deprecated
/*     */   public int getYear() {
/* 161 */     throw new IllegalArgumentException();
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
/*     */   @Deprecated
/*     */   public int getMonth() {
/* 175 */     throw new IllegalArgumentException();
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
/*     */   @Deprecated
/*     */   public int getDay() {
/* 188 */     throw new IllegalArgumentException();
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
/*     */   @Deprecated
/*     */   public int getDate() {
/* 202 */     throw new IllegalArgumentException();
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
/*     */   @Deprecated
/*     */   public void setYear(int paramInt) {
/* 216 */     throw new IllegalArgumentException();
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
/*     */   @Deprecated
/*     */   public void setMonth(int paramInt) {
/* 230 */     throw new IllegalArgumentException();
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
/*     */   @Deprecated
/*     */   public void setDate(int paramInt) {
/* 244 */     throw new IllegalArgumentException();
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
/*     */   public static Time valueOf(LocalTime paramLocalTime) {
/* 265 */     return new Time(paramLocalTime.getHour(), paramLocalTime.getMinute(), paramLocalTime.getSecond());
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
/*     */   public LocalTime toLocalTime() {
/* 279 */     return LocalTime.of(getHours(), getMinutes(), getSeconds());
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
/*     */   public Instant toInstant() {
/* 291 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/Time.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */