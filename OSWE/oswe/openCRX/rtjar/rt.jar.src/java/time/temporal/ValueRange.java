/*     */ package java.time.temporal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.DateTimeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ValueRange
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7317881728594519368L;
/*     */   private final long minSmallest;
/*     */   private final long minLargest;
/*     */   private final long maxSmallest;
/*     */   private final long maxLargest;
/*     */   
/*     */   public static ValueRange of(long paramLong1, long paramLong2) {
/* 126 */     if (paramLong1 > paramLong2) {
/* 127 */       throw new IllegalArgumentException("Minimum value must be less than maximum value");
/*     */     }
/* 129 */     return new ValueRange(paramLong1, paramLong1, paramLong2, paramLong2);
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
/*     */   public static ValueRange of(long paramLong1, long paramLong2, long paramLong3) {
/* 147 */     return of(paramLong1, paramLong1, paramLong2, paramLong3);
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
/*     */   public static ValueRange of(long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
/* 166 */     if (paramLong1 > paramLong2) {
/* 167 */       throw new IllegalArgumentException("Smallest minimum value must be less than largest minimum value");
/*     */     }
/* 169 */     if (paramLong3 > paramLong4) {
/* 170 */       throw new IllegalArgumentException("Smallest maximum value must be less than largest maximum value");
/*     */     }
/* 172 */     if (paramLong2 > paramLong4) {
/* 173 */       throw new IllegalArgumentException("Minimum value must be less than maximum value");
/*     */     }
/* 175 */     return new ValueRange(paramLong1, paramLong2, paramLong3, paramLong4);
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
/*     */   private ValueRange(long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
/* 187 */     this.minSmallest = paramLong1;
/* 188 */     this.minLargest = paramLong2;
/* 189 */     this.maxSmallest = paramLong3;
/* 190 */     this.maxLargest = paramLong4;
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
/*     */   public boolean isFixed() {
/* 204 */     return (this.minSmallest == this.minLargest && this.maxSmallest == this.maxLargest);
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
/*     */   public long getMinimum() {
/* 217 */     return this.minSmallest;
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
/*     */   public long getLargestMinimum() {
/* 229 */     return this.minLargest;
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
/*     */   public long getSmallestMaximum() {
/* 241 */     return this.maxSmallest;
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
/*     */   public long getMaximum() {
/* 253 */     return this.maxLargest;
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
/*     */   public boolean isIntValue() {
/* 270 */     return (getMinimum() >= -2147483648L && getMaximum() <= 2147483647L);
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
/*     */   public boolean isValidValue(long paramLong) {
/* 282 */     return (paramLong >= getMinimum() && paramLong <= getMaximum());
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
/*     */   public boolean isValidIntValue(long paramLong) {
/* 295 */     return (isIntValue() && isValidValue(paramLong));
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
/*     */   public long checkValidValue(long paramLong, TemporalField paramTemporalField) {
/* 310 */     if (!isValidValue(paramLong)) {
/* 311 */       throw new DateTimeException(genInvalidFieldMessage(paramTemporalField, paramLong));
/*     */     }
/* 313 */     return paramLong;
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
/*     */   public int checkValidIntValue(long paramLong, TemporalField paramTemporalField) {
/* 329 */     if (!isValidIntValue(paramLong)) {
/* 330 */       throw new DateTimeException(genInvalidFieldMessage(paramTemporalField, paramLong));
/*     */     }
/* 332 */     return (int)paramLong;
/*     */   }
/*     */   
/*     */   private String genInvalidFieldMessage(TemporalField paramTemporalField, long paramLong) {
/* 336 */     if (paramTemporalField != null) {
/* 337 */       return "Invalid value for " + paramTemporalField + " (valid values " + this + "): " + paramLong;
/*     */     }
/* 339 */     return "Invalid value (valid values " + this + "): " + paramLong;
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException, InvalidObjectException {
/* 358 */     paramObjectInputStream.defaultReadObject();
/* 359 */     if (this.minSmallest > this.minLargest) {
/* 360 */       throw new InvalidObjectException("Smallest minimum value must be less than largest minimum value");
/*     */     }
/* 362 */     if (this.maxSmallest > this.maxLargest) {
/* 363 */       throw new InvalidObjectException("Smallest maximum value must be less than largest maximum value");
/*     */     }
/* 365 */     if (this.minLargest > this.maxLargest) {
/* 366 */       throw new InvalidObjectException("Minimum value must be less than maximum value");
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
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 383 */     if (paramObject == this) {
/* 384 */       return true;
/*     */     }
/* 386 */     if (paramObject instanceof ValueRange) {
/* 387 */       ValueRange valueRange = (ValueRange)paramObject;
/* 388 */       return (this.minSmallest == valueRange.minSmallest && this.minLargest == valueRange.minLargest && this.maxSmallest == valueRange.maxSmallest && this.maxLargest == valueRange.maxLargest);
/*     */     } 
/*     */     
/* 391 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 401 */     long l = this.minSmallest + (this.minLargest << 16L) + (this.minLargest >> 48L) + (this.maxSmallest << 32L) + (this.maxSmallest >> 32L) + (this.maxLargest << 48L) + (this.maxLargest >> 16L);
/*     */ 
/*     */     
/* 404 */     return (int)(l ^ l >>> 32L);
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
/*     */   public String toString() {
/* 419 */     StringBuilder stringBuilder = new StringBuilder();
/* 420 */     stringBuilder.append(this.minSmallest);
/* 421 */     if (this.minSmallest != this.minLargest) {
/* 422 */       stringBuilder.append('/').append(this.minLargest);
/*     */     }
/* 424 */     stringBuilder.append(" - ").append(this.maxSmallest);
/* 425 */     if (this.maxSmallest != this.maxLargest) {
/* 426 */       stringBuilder.append('/').append(this.maxLargest);
/*     */     }
/* 428 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/temporal/ValueRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */