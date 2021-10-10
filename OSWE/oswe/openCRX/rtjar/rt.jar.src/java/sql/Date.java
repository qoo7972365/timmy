/*     */ package java.sql;
/*     */ 
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDate;
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
/*     */ public class Date
/*     */   extends Date
/*     */ {
/*     */   static final long serialVersionUID = 1511598038487230103L;
/*     */   
/*     */   @Deprecated
/*     */   public Date(int paramInt1, int paramInt2, int paramInt3) {
/*  59 */     super(paramInt1, paramInt2, paramInt3);
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
/*     */   public Date(long paramLong) {
/*  76 */     super(paramLong);
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
/*     */   public void setTime(long paramLong) {
/*  95 */     super.setTime(paramLong);
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
/*     */   public static Date valueOf(String paramString) {
/* 118 */     Date date = null;
/* 119 */     if (paramString == null) {
/* 120 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 123 */     int i = paramString.indexOf('-');
/* 124 */     int j = paramString.indexOf('-', i + 1);
/*     */     
/* 126 */     if (i > 0 && j > 0 && j < paramString.length() - 1) {
/* 127 */       String str1 = paramString.substring(0, i);
/* 128 */       String str2 = paramString.substring(i + 1, j);
/* 129 */       String str3 = paramString.substring(j + 1);
/* 130 */       if (str1.length() == 4 && str2
/* 131 */         .length() >= 1 && str2.length() <= 2 && str3
/* 132 */         .length() >= 1 && str3.length() <= 2) {
/* 133 */         int k = Integer.parseInt(str1);
/* 134 */         int m = Integer.parseInt(str2);
/* 135 */         int n = Integer.parseInt(str3);
/*     */         
/* 137 */         if (m >= 1 && m <= 12 && n >= 1 && n <= 31) {
/* 138 */           date = new Date(k - 1900, m - 1, n);
/*     */         }
/*     */       } 
/*     */     } 
/* 142 */     if (date == null) {
/* 143 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 146 */     return date;
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
/*     */   public String toString() {
/* 158 */     int i = getYear() + 1900;
/* 159 */     int j = getMonth() + 1;
/* 160 */     int k = getDate();
/*     */     
/* 162 */     char[] arrayOfChar = "2000-00-00".toCharArray();
/* 163 */     arrayOfChar[0] = Character.forDigit(i / 1000, 10);
/* 164 */     arrayOfChar[1] = Character.forDigit(i / 100 % 10, 10);
/* 165 */     arrayOfChar[2] = Character.forDigit(i / 10 % 10, 10);
/* 166 */     arrayOfChar[3] = Character.forDigit(i % 10, 10);
/* 167 */     arrayOfChar[5] = Character.forDigit(j / 10, 10);
/* 168 */     arrayOfChar[6] = Character.forDigit(j % 10, 10);
/* 169 */     arrayOfChar[8] = Character.forDigit(k / 10, 10);
/* 170 */     arrayOfChar[9] = Character.forDigit(k % 10, 10);
/*     */     
/* 172 */     return new String(arrayOfChar);
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
/*     */   @Deprecated
/*     */   public int getHours() {
/* 187 */     throw new IllegalArgumentException();
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
/*     */   public int getMinutes() {
/* 200 */     throw new IllegalArgumentException();
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
/*     */   public int getSeconds() {
/* 213 */     throw new IllegalArgumentException();
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
/*     */   public void setHours(int paramInt) {
/* 226 */     throw new IllegalArgumentException();
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
/*     */   public void setMinutes(int paramInt) {
/* 239 */     throw new IllegalArgumentException();
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
/*     */   public void setSeconds(int paramInt) {
/* 252 */     throw new IllegalArgumentException();
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
/*     */   public static Date valueOf(LocalDate paramLocalDate) {
/* 276 */     return new Date(paramLocalDate.getYear() - 1900, paramLocalDate.getMonthValue() - 1, paramLocalDate
/* 277 */         .getDayOfMonth());
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
/*     */   public LocalDate toLocalDate() {
/* 292 */     return LocalDate.of(getYear() + 1900, getMonth() + 1, getDate());
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
/* 304 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/Date.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */