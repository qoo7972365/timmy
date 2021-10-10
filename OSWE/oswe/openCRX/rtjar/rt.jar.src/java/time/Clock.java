/*     */ package java.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Clock
/*     */ {
/*     */   public static Clock systemUTC() {
/* 155 */     return new SystemClock(ZoneOffset.UTC);
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
/*     */   public static Clock systemDefaultZone() {
/* 178 */     return new SystemClock(ZoneId.systemDefault());
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
/*     */   public static Clock system(ZoneId paramZoneId) {
/* 197 */     Objects.requireNonNull(paramZoneId, "zone");
/* 198 */     return new SystemClock(paramZoneId);
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
/*     */   public static Clock tickSeconds(ZoneId paramZoneId) {
/* 222 */     return new TickClock(system(paramZoneId), 1000000000L);
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
/*     */   public static Clock tickMinutes(ZoneId paramZoneId) {
/* 245 */     return new TickClock(system(paramZoneId), 60000000000L);
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
/*     */   public static Clock tick(Clock paramClock, Duration paramDuration) {
/* 280 */     Objects.requireNonNull(paramClock, "baseClock");
/* 281 */     Objects.requireNonNull(paramDuration, "tickDuration");
/* 282 */     if (paramDuration.isNegative()) {
/* 283 */       throw new IllegalArgumentException("Tick duration must not be negative");
/*     */     }
/* 285 */     long l = paramDuration.toNanos();
/* 286 */     if (l % 1000000L != 0L)
/*     */     {
/* 288 */       if (1000000000L % l != 0L)
/*     */       {
/*     */         
/* 291 */         throw new IllegalArgumentException("Invalid tick duration"); } 
/*     */     }
/* 293 */     if (l <= 1L) {
/* 294 */       return paramClock;
/*     */     }
/* 296 */     return new TickClock(paramClock, l);
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
/*     */   public static Clock fixed(Instant paramInstant, ZoneId paramZoneId) {
/* 315 */     Objects.requireNonNull(paramInstant, "fixedInstant");
/* 316 */     Objects.requireNonNull(paramZoneId, "zone");
/* 317 */     return new FixedClock(paramInstant, paramZoneId);
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
/*     */   public static Clock offset(Clock paramClock, Duration paramDuration) {
/* 341 */     Objects.requireNonNull(paramClock, "baseClock");
/* 342 */     Objects.requireNonNull(paramDuration, "offsetDuration");
/* 343 */     if (paramDuration.equals(Duration.ZERO)) {
/* 344 */       return paramClock;
/*     */     }
/* 346 */     return new OffsetClock(paramClock, paramDuration);
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
/*     */   public abstract ZoneId getZone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Clock withZone(ZoneId paramZoneId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long millis() {
/* 398 */     return instant().toEpochMilli();
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
/*     */   public abstract Instant instant();
/*     */ 
/*     */ 
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
/* 425 */     return super.equals(paramObject);
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
/* 439 */     return super.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   static final class SystemClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 6740630888130243051L;
/*     */     
/*     */     private final ZoneId zone;
/*     */     
/*     */     SystemClock(ZoneId param1ZoneId) {
/* 452 */       this.zone = param1ZoneId;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 456 */       return this.zone;
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId param1ZoneId) {
/* 460 */       if (param1ZoneId.equals(this.zone)) {
/* 461 */         return this;
/*     */       }
/* 463 */       return new SystemClock(param1ZoneId);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 467 */       return System.currentTimeMillis();
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 471 */       return Instant.ofEpochMilli(millis());
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 475 */       if (param1Object instanceof SystemClock) {
/* 476 */         return this.zone.equals(((SystemClock)param1Object).zone);
/*     */       }
/* 478 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 482 */       return this.zone.hashCode() + 1;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 486 */       return "SystemClock[" + this.zone + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class FixedClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7430389292664866958L;
/*     */     
/*     */     private final Instant instant;
/*     */     private final ZoneId zone;
/*     */     
/*     */     FixedClock(Instant param1Instant, ZoneId param1ZoneId) {
/* 501 */       this.instant = param1Instant;
/* 502 */       this.zone = param1ZoneId;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 506 */       return this.zone;
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId param1ZoneId) {
/* 510 */       if (param1ZoneId.equals(this.zone)) {
/* 511 */         return this;
/*     */       }
/* 513 */       return new FixedClock(this.instant, param1ZoneId);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 517 */       return this.instant.toEpochMilli();
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 521 */       return this.instant;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 525 */       if (param1Object instanceof FixedClock) {
/* 526 */         FixedClock fixedClock = (FixedClock)param1Object;
/* 527 */         return (this.instant.equals(fixedClock.instant) && this.zone.equals(fixedClock.zone));
/*     */       } 
/* 529 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 533 */       return this.instant.hashCode() ^ this.zone.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 537 */       return "FixedClock[" + this.instant + "," + this.zone + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class OffsetClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2007484719125426256L;
/*     */     private final Clock baseClock;
/*     */     private final Duration offset;
/*     */     
/*     */     OffsetClock(Clock param1Clock, Duration param1Duration) {
/* 551 */       this.baseClock = param1Clock;
/* 552 */       this.offset = param1Duration;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 556 */       return this.baseClock.getZone();
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId param1ZoneId) {
/* 560 */       if (param1ZoneId.equals(this.baseClock.getZone())) {
/* 561 */         return this;
/*     */       }
/* 563 */       return new OffsetClock(this.baseClock.withZone(param1ZoneId), this.offset);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 567 */       return Math.addExact(this.baseClock.millis(), this.offset.toMillis());
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 571 */       return this.baseClock.instant().plus(this.offset);
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 575 */       if (param1Object instanceof OffsetClock) {
/* 576 */         OffsetClock offsetClock = (OffsetClock)param1Object;
/* 577 */         return (this.baseClock.equals(offsetClock.baseClock) && this.offset.equals(offsetClock.offset));
/*     */       } 
/* 579 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 583 */       return this.baseClock.hashCode() ^ this.offset.hashCode();
/*     */     }
/*     */     
/*     */     public String toString() {
/* 587 */       return "OffsetClock[" + this.baseClock + "," + this.offset + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class TickClock
/*     */     extends Clock
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 6504659149906368850L;
/*     */     private final Clock baseClock;
/*     */     private final long tickNanos;
/*     */     
/*     */     TickClock(Clock param1Clock, long param1Long) {
/* 601 */       this.baseClock = param1Clock;
/* 602 */       this.tickNanos = param1Long;
/*     */     }
/*     */     
/*     */     public ZoneId getZone() {
/* 606 */       return this.baseClock.getZone();
/*     */     }
/*     */     
/*     */     public Clock withZone(ZoneId param1ZoneId) {
/* 610 */       if (param1ZoneId.equals(this.baseClock.getZone())) {
/* 611 */         return this;
/*     */       }
/* 613 */       return new TickClock(this.baseClock.withZone(param1ZoneId), this.tickNanos);
/*     */     }
/*     */     
/*     */     public long millis() {
/* 617 */       long l = this.baseClock.millis();
/* 618 */       return l - Math.floorMod(l, this.tickNanos / 1000000L);
/*     */     }
/*     */     
/*     */     public Instant instant() {
/* 622 */       if (this.tickNanos % 1000000L == 0L) {
/* 623 */         long l = this.baseClock.millis();
/* 624 */         return Instant.ofEpochMilli(l - Math.floorMod(l, this.tickNanos / 1000000L));
/*     */       } 
/* 626 */       Instant instant = this.baseClock.instant();
/* 627 */       long l1 = instant.getNano();
/* 628 */       long l2 = Math.floorMod(l1, this.tickNanos);
/* 629 */       return instant.minusNanos(l2);
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 633 */       if (param1Object instanceof TickClock) {
/* 634 */         TickClock tickClock = (TickClock)param1Object;
/* 635 */         return (this.baseClock.equals(tickClock.baseClock) && this.tickNanos == tickClock.tickNanos);
/*     */       } 
/* 637 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 641 */       return this.baseClock.hashCode() ^ (int)(this.tickNanos ^ this.tickNanos >>> 32L);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 645 */       return "TickClock[" + this.baseClock + "," + Duration.ofNanos(this.tickNanos) + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/Clock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */