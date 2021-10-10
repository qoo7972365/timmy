/*     */ package java.time.zone;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.ZoneOffset;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public final class ZoneOffsetTransition
/*     */   implements Comparable<ZoneOffsetTransition>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6946044323557704546L;
/*     */   private final LocalDateTime transition;
/*     */   private final ZoneOffset offsetBefore;
/*     */   private final ZoneOffset offsetAfter;
/*     */   
/*     */   public static ZoneOffsetTransition of(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2) {
/* 135 */     Objects.requireNonNull(paramLocalDateTime, "transition");
/* 136 */     Objects.requireNonNull(paramZoneOffset1, "offsetBefore");
/* 137 */     Objects.requireNonNull(paramZoneOffset2, "offsetAfter");
/* 138 */     if (paramZoneOffset1.equals(paramZoneOffset2)) {
/* 139 */       throw new IllegalArgumentException("Offsets must not be equal");
/*     */     }
/* 141 */     if (paramLocalDateTime.getNano() != 0) {
/* 142 */       throw new IllegalArgumentException("Nano-of-second must be zero");
/*     */     }
/* 144 */     return new ZoneOffsetTransition(paramLocalDateTime, paramZoneOffset1, paramZoneOffset2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ZoneOffsetTransition(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2) {
/* 155 */     this.transition = paramLocalDateTime;
/* 156 */     this.offsetBefore = paramZoneOffset1;
/* 157 */     this.offsetAfter = paramZoneOffset2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ZoneOffsetTransition(long paramLong, ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2) {
/* 168 */     this.transition = LocalDateTime.ofEpochSecond(paramLong, 0, paramZoneOffset1);
/* 169 */     this.offsetBefore = paramZoneOffset1;
/* 170 */     this.offsetAfter = paramZoneOffset2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 181 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */   private Object writeReplace() {
/* 202 */     return new Ser((byte)2, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 212 */     Ser.writeEpochSec(toEpochSecond(), paramDataOutput);
/* 213 */     Ser.writeOffset(this.offsetBefore, paramDataOutput);
/* 214 */     Ser.writeOffset(this.offsetAfter, paramDataOutput);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ZoneOffsetTransition readExternal(DataInput paramDataInput) throws IOException {
/* 225 */     long l = Ser.readEpochSec(paramDataInput);
/* 226 */     ZoneOffset zoneOffset1 = Ser.readOffset(paramDataInput);
/* 227 */     ZoneOffset zoneOffset2 = Ser.readOffset(paramDataInput);
/* 228 */     if (zoneOffset1.equals(zoneOffset2)) {
/* 229 */       throw new IllegalArgumentException("Offsets must not be equal");
/*     */     }
/* 231 */     return new ZoneOffsetTransition(l, zoneOffset1, zoneOffset2);
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
/*     */   public Instant getInstant() {
/* 247 */     return this.transition.toInstant(this.offsetBefore);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long toEpochSecond() {
/* 256 */     return this.transition.toEpochSecond(this.offsetBefore);
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
/*     */   public LocalDateTime getDateTimeBefore() {
/* 273 */     return this.transition;
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
/*     */   public LocalDateTime getDateTimeAfter() {
/* 287 */     return this.transition.plusSeconds(getDurationSeconds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneOffset getOffsetBefore() {
/* 298 */     return this.offsetBefore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZoneOffset getOffsetAfter() {
/* 309 */     return this.offsetAfter;
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
/*     */   public Duration getDuration() {
/* 322 */     return Duration.ofSeconds(getDurationSeconds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getDurationSeconds() {
/* 331 */     return getOffsetAfter().getTotalSeconds() - getOffsetBefore().getTotalSeconds();
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
/*     */   public boolean isGap() {
/* 344 */     return (getOffsetAfter().getTotalSeconds() > getOffsetBefore().getTotalSeconds());
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
/*     */   public boolean isOverlap() {
/* 357 */     return (getOffsetAfter().getTotalSeconds() < getOffsetBefore().getTotalSeconds());
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
/*     */   public boolean isValidOffset(ZoneOffset paramZoneOffset) {
/* 371 */     return isGap() ? false : ((getOffsetBefore().equals(paramZoneOffset) || getOffsetAfter().equals(paramZoneOffset)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   List<ZoneOffset> getValidOffsets() {
/* 382 */     if (isGap()) {
/* 383 */       return Collections.emptyList();
/*     */     }
/* 385 */     return Arrays.asList(new ZoneOffset[] { getOffsetBefore(), getOffsetAfter() });
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
/*     */   public int compareTo(ZoneOffsetTransition paramZoneOffsetTransition) {
/* 400 */     return getInstant().compareTo(paramZoneOffsetTransition.getInstant());
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
/*     */   public boolean equals(Object paramObject) {
/* 414 */     if (paramObject == this) {
/* 415 */       return true;
/*     */     }
/* 417 */     if (paramObject instanceof ZoneOffsetTransition) {
/* 418 */       ZoneOffsetTransition zoneOffsetTransition = (ZoneOffsetTransition)paramObject;
/* 419 */       return (this.transition.equals(zoneOffsetTransition.transition) && this.offsetBefore
/* 420 */         .equals(zoneOffsetTransition.offsetBefore) && this.offsetAfter.equals(zoneOffsetTransition.offsetAfter));
/*     */     } 
/* 422 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 432 */     return this.transition.hashCode() ^ this.offsetBefore.hashCode() ^ Integer.rotateLeft(this.offsetAfter.hashCode(), 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 443 */     StringBuilder stringBuilder = new StringBuilder();
/* 444 */     stringBuilder.append("Transition[")
/* 445 */       .append(isGap() ? "Gap" : "Overlap")
/* 446 */       .append(" at ")
/* 447 */       .append(this.transition)
/* 448 */       .append(this.offsetBefore)
/* 449 */       .append(" to ")
/* 450 */       .append(this.offsetAfter)
/* 451 */       .append(']');
/* 452 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/zone/ZoneOffsetTransition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */