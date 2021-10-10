/*     */ package java.nio.file.attribute;
/*     */ 
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneOffset;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FileTime
/*     */   implements Comparable<FileTime>
/*     */ {
/*     */   private final TimeUnit unit;
/*     */   private final long value;
/*     */   private Instant instant;
/*     */   private String valueAsString;
/*     */   private static final long HOURS_PER_DAY = 24L;
/*     */   private static final long MINUTES_PER_HOUR = 60L;
/*     */   private static final long SECONDS_PER_MINUTE = 60L;
/*     */   private static final long SECONDS_PER_HOUR = 3600L;
/*     */   private static final long SECONDS_PER_DAY = 86400L;
/*     */   private static final long MILLIS_PER_SECOND = 1000L;
/*     */   private static final long MICROS_PER_SECOND = 1000000L;
/*     */   private static final long NANOS_PER_SECOND = 1000000000L;
/*     */   private static final int NANOS_PER_MILLI = 1000000;
/*     */   private static final int NANOS_PER_MICRO = 1000;
/*     */   private static final long MIN_SECOND = -31557014167219200L;
/*     */   private static final long MAX_SECOND = 31556889864403199L;
/*     */   private static final long DAYS_PER_10000_YEARS = 3652425L;
/*     */   private static final long SECONDS_PER_10000_YEARS = 315569520000L;
/*     */   private static final long SECONDS_0000_TO_1970 = 62167219200L;
/*     */   
/*     */   private FileTime(long paramLong, TimeUnit paramTimeUnit, Instant paramInstant) {
/*  78 */     this.value = paramLong;
/*  79 */     this.unit = paramTimeUnit;
/*  80 */     this.instant = paramInstant;
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
/*     */   public static FileTime from(long paramLong, TimeUnit paramTimeUnit) {
/*  96 */     Objects.requireNonNull(paramTimeUnit, "unit");
/*  97 */     return new FileTime(paramLong, paramTimeUnit, null);
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
/*     */   public static FileTime fromMillis(long paramLong) {
/* 110 */     return new FileTime(paramLong, TimeUnit.MILLISECONDS, null);
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
/*     */   public static FileTime from(Instant paramInstant) {
/* 124 */     Objects.requireNonNull(paramInstant, "instant");
/* 125 */     return new FileTime(0L, null, paramInstant);
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
/*     */   public long to(TimeUnit paramTimeUnit) {
/* 142 */     Objects.requireNonNull(paramTimeUnit, "unit");
/* 143 */     if (this.unit != null) {
/* 144 */       return paramTimeUnit.convert(this.value, this.unit);
/*     */     }
/* 146 */     long l1 = paramTimeUnit.convert(this.instant.getEpochSecond(), TimeUnit.SECONDS);
/* 147 */     if (l1 == Long.MIN_VALUE || l1 == Long.MAX_VALUE) {
/* 148 */       return l1;
/*     */     }
/* 150 */     long l2 = paramTimeUnit.convert(this.instant.getNano(), TimeUnit.NANOSECONDS);
/* 151 */     long l3 = l1 + l2;
/*     */     
/* 153 */     if (((l1 ^ l3) & (l2 ^ l3)) < 0L) {
/* 154 */       return (l1 < 0L) ? Long.MIN_VALUE : Long.MAX_VALUE;
/*     */     }
/* 156 */     return l3;
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
/*     */   public long toMillis() {
/* 170 */     if (this.unit != null) {
/* 171 */       return this.unit.toMillis(this.value);
/*     */     }
/* 173 */     long l1 = this.instant.getEpochSecond();
/* 174 */     int i = this.instant.getNano();
/*     */     
/* 176 */     long l2 = l1 * 1000L;
/* 177 */     long l3 = Math.abs(l1);
/* 178 */     if ((l3 | 0x3E8L) >>> 31L != 0L && 
/* 179 */       l2 / 1000L != l1) {
/* 180 */       return (l1 < 0L) ? Long.MIN_VALUE : Long.MAX_VALUE;
/*     */     }
/*     */     
/* 183 */     return l2 + (i / 1000000);
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
/*     */   private static long scale(long paramLong1, long paramLong2, long paramLong3) {
/* 209 */     if (paramLong1 > paramLong3) return Long.MAX_VALUE; 
/* 210 */     if (paramLong1 < -paramLong3) return Long.MIN_VALUE; 
/* 211 */     return paramLong1 * paramLong2;
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
/*     */   public Instant toInstant() {
/* 231 */     if (this.instant == null) {
/* 232 */       long l = 0L;
/* 233 */       int i = 0;
/* 234 */       switch (this.unit) {
/*     */         case DAYS:
/* 236 */           l = scale(this.value, 86400L, 106751991167300L);
/*     */           break;
/*     */         
/*     */         case HOURS:
/* 240 */           l = scale(this.value, 3600L, 2562047788015215L);
/*     */           break;
/*     */         
/*     */         case MINUTES:
/* 244 */           l = scale(this.value, 60L, 153722867280912930L);
/*     */           break;
/*     */         
/*     */         case SECONDS:
/* 248 */           l = this.value;
/*     */           break;
/*     */         case MILLISECONDS:
/* 251 */           l = Math.floorDiv(this.value, 1000L);
/* 252 */           i = (int)Math.floorMod(this.value, 1000L) * 1000000;
/*     */           break;
/*     */         
/*     */         case MICROSECONDS:
/* 256 */           l = Math.floorDiv(this.value, 1000000L);
/* 257 */           i = (int)Math.floorMod(this.value, 1000000L) * 1000;
/*     */           break;
/*     */         
/*     */         case NANOSECONDS:
/* 261 */           l = Math.floorDiv(this.value, 1000000000L);
/* 262 */           i = (int)Math.floorMod(this.value, 1000000000L); break;
/*     */         default:
/* 264 */           throw new AssertionError("Unit not handled");
/*     */       } 
/* 266 */       if (l <= -31557014167219200L) {
/* 267 */         this.instant = Instant.MIN;
/* 268 */       } else if (l >= 31556889864403199L) {
/* 269 */         this.instant = Instant.MAX;
/*     */       } else {
/* 271 */         this.instant = Instant.ofEpochSecond(l, i);
/*     */       } 
/* 273 */     }  return this.instant;
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
/*     */   public boolean equals(Object paramObject) {
/* 291 */     return (paramObject instanceof FileTime) ? ((compareTo((FileTime)paramObject) == 0)) : false;
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
/*     */   public int hashCode() {
/* 305 */     return toInstant().hashCode();
/*     */   }
/*     */   
/*     */   private long toDays() {
/* 309 */     if (this.unit != null) {
/* 310 */       return this.unit.toDays(this.value);
/*     */     }
/* 312 */     return TimeUnit.SECONDS.toDays(toInstant().getEpochSecond());
/*     */   }
/*     */ 
/*     */   
/*     */   private long toExcessNanos(long paramLong) {
/* 317 */     if (this.unit != null) {
/* 318 */       return this.unit.toNanos(this.value - this.unit.convert(paramLong, TimeUnit.DAYS));
/*     */     }
/* 320 */     return TimeUnit.SECONDS.toNanos(toInstant().getEpochSecond() - TimeUnit.DAYS
/* 321 */         .toSeconds(paramLong));
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
/*     */   public int compareTo(FileTime paramFileTime) {
/* 339 */     if (this.unit != null && this.unit == paramFileTime.unit) {
/* 340 */       return Long.compare(this.value, paramFileTime.value);
/*     */     }
/*     */     
/* 343 */     long l1 = toInstant().getEpochSecond();
/* 344 */     long l2 = paramFileTime.toInstant().getEpochSecond();
/* 345 */     int i = Long.compare(l1, l2);
/* 346 */     if (i != 0) {
/* 347 */       return i;
/*     */     }
/* 349 */     i = Long.compare(toInstant().getNano(), paramFileTime.toInstant().getNano());
/* 350 */     if (i != 0) {
/* 351 */       return i;
/*     */     }
/* 353 */     if (l1 != 31556889864403199L && l1 != -31557014167219200L) {
/* 354 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 359 */     long l3 = toDays();
/* 360 */     long l4 = paramFileTime.toDays();
/* 361 */     if (l3 == l4) {
/* 362 */       return Long.compare(toExcessNanos(l3), paramFileTime.toExcessNanos(l4));
/*     */     }
/* 364 */     return Long.compare(l3, l4);
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
/*     */   private StringBuilder append(StringBuilder paramStringBuilder, int paramInt1, int paramInt2) {
/* 377 */     while (paramInt1 > 0) {
/* 378 */       paramStringBuilder.append((char)(paramInt2 / paramInt1 + 48));
/* 379 */       paramInt2 %= paramInt1;
/* 380 */       paramInt1 /= 10;
/*     */     } 
/* 382 */     return paramStringBuilder;
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
/*     */   public String toString() {
/* 412 */     if (this.valueAsString == null) {
/* 413 */       LocalDateTime localDateTime; long l = 0L;
/* 414 */       int i = 0;
/* 415 */       if (this.instant == null && this.unit.compareTo(TimeUnit.SECONDS) >= 0) {
/* 416 */         l = this.unit.toSeconds(this.value);
/*     */       } else {
/* 418 */         l = toInstant().getEpochSecond();
/* 419 */         i = toInstant().getNano();
/*     */       } 
/*     */       
/* 422 */       int j = 0;
/* 423 */       if (l >= -62167219200L) {
/*     */         
/* 425 */         long l1 = l - 315569520000L + 62167219200L;
/* 426 */         long l2 = Math.floorDiv(l1, 315569520000L) + 1L;
/* 427 */         long l3 = Math.floorMod(l1, 315569520000L);
/* 428 */         localDateTime = LocalDateTime.ofEpochSecond(l3 - 62167219200L, i, ZoneOffset.UTC);
/* 429 */         j = localDateTime.getYear() + (int)l2 * 10000;
/*     */       } else {
/*     */         
/* 432 */         long l1 = l + 62167219200L;
/* 433 */         long l2 = l1 / 315569520000L;
/* 434 */         long l3 = l1 % 315569520000L;
/* 435 */         localDateTime = LocalDateTime.ofEpochSecond(l3 - 62167219200L, i, ZoneOffset.UTC);
/* 436 */         j = localDateTime.getYear() + (int)l2 * 10000;
/*     */       } 
/* 438 */       if (j <= 0) {
/* 439 */         j--;
/*     */       }
/* 441 */       int k = localDateTime.getNano();
/* 442 */       StringBuilder stringBuilder = new StringBuilder(64);
/* 443 */       stringBuilder.append((j < 0) ? "-" : "");
/* 444 */       j = Math.abs(j);
/* 445 */       if (j < 10000) {
/* 446 */         append(stringBuilder, 1000, Math.abs(j));
/*     */       } else {
/* 448 */         stringBuilder.append(String.valueOf(j));
/*     */       } 
/* 450 */       stringBuilder.append('-');
/* 451 */       append(stringBuilder, 10, localDateTime.getMonthValue());
/* 452 */       stringBuilder.append('-');
/* 453 */       append(stringBuilder, 10, localDateTime.getDayOfMonth());
/* 454 */       stringBuilder.append('T');
/* 455 */       append(stringBuilder, 10, localDateTime.getHour());
/* 456 */       stringBuilder.append(':');
/* 457 */       append(stringBuilder, 10, localDateTime.getMinute());
/* 458 */       stringBuilder.append(':');
/* 459 */       append(stringBuilder, 10, localDateTime.getSecond());
/* 460 */       if (k != 0) {
/* 461 */         stringBuilder.append('.');
/*     */         
/* 463 */         int m = 100000000;
/* 464 */         while (k % 10 == 0) {
/* 465 */           k /= 10;
/* 466 */           m /= 10;
/*     */         } 
/* 468 */         append(stringBuilder, m, k);
/*     */       } 
/* 470 */       stringBuilder.append('Z');
/* 471 */       this.valueAsString = stringBuilder.toString();
/*     */     } 
/* 473 */     return this.valueAsString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/file/attribute/FileTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */