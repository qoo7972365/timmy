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
/*     */ class DurationDayTimeImpl
/*     */   extends DurationImpl
/*     */ {
/*     */   public DurationDayTimeImpl(boolean isPositive, BigInteger days, BigInteger hours, BigInteger minutes, BigDecimal seconds) {
/*  64 */     super(isPositive, (BigInteger)null, (BigInteger)null, days, hours, minutes, seconds);
/*  65 */     convertToCanonicalDayTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DurationDayTimeImpl(boolean isPositive, int days, int hours, int minutes, int seconds) {
/*  75 */     this(isPositive, 
/*     */         
/*  77 */         wrap(days), 
/*  78 */         wrap(hours), 
/*  79 */         wrap(minutes), (seconds != Integer.MIN_VALUE) ? new BigDecimal(
/*  80 */           String.valueOf(seconds)) : null);
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
/*     */   protected DurationDayTimeImpl(String lexicalRepresentation) {
/* 106 */     super(lexicalRepresentation);
/*     */     
/* 108 */     if (getYears() > 0 || getMonths() > 0) {
/* 109 */       throw new IllegalArgumentException("Trying to create an xdt:dayTimeDuration with an invalid lexical representation of \"" + lexicalRepresentation + "\", data model requires a format PnDTnHnMnS.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     convertToCanonicalDayTime();
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
/*     */   protected DurationDayTimeImpl(long durationInMilliseconds) {
/* 155 */     super(durationInMilliseconds);
/* 156 */     convertToCanonicalDayTime();
/*     */     
/* 158 */     this.years = null;
/* 159 */     this.months = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValue() {
/* 168 */     float sec = (this.seconds == null) ? 0.0F : this.seconds.floatValue();
/* 169 */     return (((getDays() * 24 + 
/* 170 */       getHours()) * 60 + 
/* 171 */       getMinutes()) * 60) + sec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertToCanonicalDayTime() {
/* 177 */     while (getSeconds() >= 60) {
/*     */       
/* 179 */       this.seconds = this.seconds.subtract(BigDecimal.valueOf(60L));
/* 180 */       this.minutes = BigInteger.valueOf(getMinutes()).add(BigInteger.ONE);
/*     */     } 
/*     */     
/* 183 */     while (getMinutes() >= 60) {
/*     */       
/* 185 */       this.minutes = this.minutes.subtract(BigInteger.valueOf(60L));
/* 186 */       this.hours = BigInteger.valueOf(getHours()).add(BigInteger.ONE);
/*     */     } 
/*     */     
/* 189 */     while (getHours() >= 24) {
/*     */       
/* 191 */       this.hours = this.hours.subtract(BigInteger.valueOf(24L));
/* 192 */       this.days = BigInteger.valueOf(getDays()).add(BigInteger.ONE);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/jaxp/datatype/DurationDayTimeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */