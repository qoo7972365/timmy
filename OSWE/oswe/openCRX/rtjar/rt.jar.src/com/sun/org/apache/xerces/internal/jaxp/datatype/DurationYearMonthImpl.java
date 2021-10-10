/*     */ package com.sun.org.apache.xerces.internal.jaxp.datatype;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DurationYearMonthImpl
/*     */   extends DurationImpl
/*     */ {
/*     */   public DurationYearMonthImpl(boolean isPositive, BigInteger years, BigInteger months) {
/*  74 */     super(isPositive, years, months, (BigInteger)null, (BigInteger)null, (BigInteger)null, (BigDecimal)null);
/*  75 */     convertToCanonicalYearMonth();
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
/*     */   protected DurationYearMonthImpl(boolean isPositive, int years, int months) {
/*  98 */     this(isPositive, 
/*  99 */         wrap(years), 
/* 100 */         wrap(months));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DurationYearMonthImpl(long durationInMilliseconds) {
/* 140 */     super(durationInMilliseconds);
/* 141 */     convertToCanonicalYearMonth();
/*     */     
/* 143 */     this.days = null;
/* 144 */     this.hours = null;
/* 145 */     this.minutes = null;
/* 146 */     this.seconds = null;
/* 147 */     this.signum = calcSignum(!(this.signum < 0));
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
/*     */   protected DurationYearMonthImpl(String lexicalRepresentation) {
/* 174 */     super(lexicalRepresentation);
/* 175 */     if (getDays() > 0 || getHours() > 0 || 
/* 176 */       getMinutes() > 0 || getSeconds() > 0) {
/* 177 */       throw new IllegalArgumentException("Trying to create an xdt:yearMonthDuration with an invalid lexical representation of \"" + lexicalRepresentation + "\", data model requires PnYnM.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     convertToCanonicalYearMonth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 190 */     return getYears() * 12 + getMonths();
/*     */   }
/*     */   
/*     */   private void convertToCanonicalYearMonth() {
/* 194 */     while (getMonths() >= 12) {
/*     */       
/* 196 */       this.months = this.months.subtract(BigInteger.valueOf(12L));
/* 197 */       this.years = BigInteger.valueOf(getYears()).add(BigInteger.ONE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/datatype/DurationYearMonthImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */