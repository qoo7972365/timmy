/*     */ package java.time;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.Temporal;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalAdjuster;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.time.zone.ZoneRules;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ZoneOffset
/*     */   extends ZoneId
/*     */   implements TemporalAccessor, TemporalAdjuster, Comparable<ZoneOffset>, Serializable
/*     */ {
/* 135 */   private static final ConcurrentMap<Integer, ZoneOffset> SECONDS_CACHE = new ConcurrentHashMap<>(16, 0.75F, 4);
/*     */   
/* 137 */   private static final ConcurrentMap<String, ZoneOffset> ID_CACHE = new ConcurrentHashMap<>(16, 0.75F, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_SECONDS = 64800;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2357656521762053153L;
/*     */ 
/*     */ 
/*     */   
/* 151 */   public static final ZoneOffset UTC = ofTotalSeconds(0);
/*     */ 
/*     */ 
/*     */   
/* 155 */   public static final ZoneOffset MIN = ofTotalSeconds(-64800);
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final ZoneOffset MAX = ofTotalSeconds(64800);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int totalSeconds;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZoneOffset of(String paramString) {
/*     */     int i, j, k;
/* 203 */     Objects.requireNonNull(paramString, "offsetId");
/*     */     
/* 205 */     ZoneOffset zoneOffset = ID_CACHE.get(paramString);
/* 206 */     if (zoneOffset != null) {
/* 207 */       return zoneOffset;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 212 */     switch (paramString.length()) {
/*     */       case 2:
/* 214 */         paramString = paramString.charAt(0) + "0" + paramString.charAt(1);
/*     */       case 3:
/* 216 */         i = parseNumber(paramString, 1, false);
/* 217 */         j = 0;
/* 218 */         k = 0;
/*     */         break;
/*     */       case 5:
/* 221 */         i = parseNumber(paramString, 1, false);
/* 222 */         j = parseNumber(paramString, 3, false);
/* 223 */         k = 0;
/*     */         break;
/*     */       case 6:
/* 226 */         i = parseNumber(paramString, 1, false);
/* 227 */         j = parseNumber(paramString, 4, true);
/* 228 */         k = 0;
/*     */         break;
/*     */       case 7:
/* 231 */         i = parseNumber(paramString, 1, false);
/* 232 */         j = parseNumber(paramString, 3, false);
/* 233 */         k = parseNumber(paramString, 5, false);
/*     */         break;
/*     */       case 9:
/* 236 */         i = parseNumber(paramString, 1, false);
/* 237 */         j = parseNumber(paramString, 4, true);
/* 238 */         k = parseNumber(paramString, 7, true);
/*     */         break;
/*     */       default:
/* 241 */         throw new DateTimeException("Invalid ID for ZoneOffset, invalid format: " + paramString);
/*     */     } 
/* 243 */     char c = paramString.charAt(0);
/* 244 */     if (c != '+' && c != '-') {
/* 245 */       throw new DateTimeException("Invalid ID for ZoneOffset, plus/minus not found when expected: " + paramString);
/*     */     }
/* 247 */     if (c == '-') {
/* 248 */       return ofHoursMinutesSeconds(-i, -j, -k);
/*     */     }
/* 250 */     return ofHoursMinutesSeconds(i, j, k);
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
/*     */   private static int parseNumber(CharSequence paramCharSequence, int paramInt, boolean paramBoolean) {
/* 263 */     if (paramBoolean && paramCharSequence.charAt(paramInt - 1) != ':') {
/* 264 */       throw new DateTimeException("Invalid ID for ZoneOffset, colon not found when expected: " + paramCharSequence);
/*     */     }
/* 266 */     char c1 = paramCharSequence.charAt(paramInt);
/* 267 */     char c2 = paramCharSequence.charAt(paramInt + 1);
/* 268 */     if (c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9') {
/* 269 */       throw new DateTimeException("Invalid ID for ZoneOffset, non numeric characters found: " + paramCharSequence);
/*     */     }
/* 271 */     return (c1 - 48) * 10 + c2 - 48;
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
/*     */   public static ZoneOffset ofHours(int paramInt) {
/* 283 */     return ofHoursMinutesSeconds(paramInt, 0, 0);
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
/*     */   public static ZoneOffset ofHoursMinutes(int paramInt1, int paramInt2) {
/* 300 */     return ofHoursMinutesSeconds(paramInt1, paramInt2, 0);
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
/*     */   public static ZoneOffset ofHoursMinutesSeconds(int paramInt1, int paramInt2, int paramInt3) {
/* 317 */     validate(paramInt1, paramInt2, paramInt3);
/* 318 */     int i = totalSeconds(paramInt1, paramInt2, paramInt3);
/* 319 */     return ofTotalSeconds(i);
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
/*     */   public static ZoneOffset from(TemporalAccessor paramTemporalAccessor) {
/* 344 */     Objects.requireNonNull(paramTemporalAccessor, "temporal");
/* 345 */     ZoneOffset zoneOffset = paramTemporalAccessor.<ZoneOffset>query(TemporalQueries.offset());
/* 346 */     if (zoneOffset == null) {
/* 347 */       throw new DateTimeException("Unable to obtain ZoneOffset from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/* 348 */           .getClass().getName());
/*     */     }
/* 350 */     return zoneOffset;
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
/*     */   private static void validate(int paramInt1, int paramInt2, int paramInt3) {
/* 363 */     if (paramInt1 < -18 || paramInt1 > 18) {
/* 364 */       throw new DateTimeException("Zone offset hours not in valid range: value " + paramInt1 + " is not in the range -18 to 18");
/*     */     }
/*     */     
/* 367 */     if (paramInt1 > 0) {
/* 368 */       if (paramInt2 < 0 || paramInt3 < 0) {
/* 369 */         throw new DateTimeException("Zone offset minutes and seconds must be positive because hours is positive");
/*     */       }
/* 371 */     } else if (paramInt1 < 0) {
/* 372 */       if (paramInt2 > 0 || paramInt3 > 0) {
/* 373 */         throw new DateTimeException("Zone offset minutes and seconds must be negative because hours is negative");
/*     */       }
/* 375 */     } else if ((paramInt2 > 0 && paramInt3 < 0) || (paramInt2 < 0 && paramInt3 > 0)) {
/* 376 */       throw new DateTimeException("Zone offset minutes and seconds must have the same sign");
/*     */     } 
/* 378 */     if (paramInt2 < -59 || paramInt2 > 59) {
/* 379 */       throw new DateTimeException("Zone offset minutes not in valid range: value " + paramInt2 + " is not in the range -59 to 59");
/*     */     }
/*     */     
/* 382 */     if (paramInt3 < -59 || paramInt3 > 59) {
/* 383 */       throw new DateTimeException("Zone offset seconds not in valid range: value " + paramInt3 + " is not in the range -59 to 59");
/*     */     }
/*     */     
/* 386 */     if (Math.abs(paramInt1) == 18 && (paramInt2 | paramInt3) != 0) {
/* 387 */       throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
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
/*     */   private static int totalSeconds(int paramInt1, int paramInt2, int paramInt3) {
/* 400 */     return paramInt1 * 3600 + paramInt2 * 60 + paramInt3;
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
/*     */   public static ZoneOffset ofTotalSeconds(int paramInt) {
/* 414 */     if (paramInt < -64800 || paramInt > 64800) {
/* 415 */       throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
/*     */     }
/* 417 */     if (paramInt % 900 == 0) {
/* 418 */       Integer integer = Integer.valueOf(paramInt);
/* 419 */       ZoneOffset zoneOffset = SECONDS_CACHE.get(integer);
/* 420 */       if (zoneOffset == null) {
/* 421 */         zoneOffset = new ZoneOffset(paramInt);
/* 422 */         SECONDS_CACHE.putIfAbsent(integer, zoneOffset);
/* 423 */         zoneOffset = SECONDS_CACHE.get(integer);
/* 424 */         ID_CACHE.putIfAbsent(zoneOffset.getId(), zoneOffset);
/*     */       } 
/* 426 */       return zoneOffset;
/*     */     } 
/* 428 */     return new ZoneOffset(paramInt);
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
/*     */   private ZoneOffset(int paramInt) {
/* 440 */     this.totalSeconds = paramInt;
/* 441 */     this.id = buildId(paramInt);
/*     */   }
/*     */   
/*     */   private static String buildId(int paramInt) {
/* 445 */     if (paramInt == 0) {
/* 446 */       return "Z";
/*     */     }
/* 448 */     int i = Math.abs(paramInt);
/* 449 */     StringBuilder stringBuilder = new StringBuilder();
/* 450 */     int j = i / 3600;
/* 451 */     int k = i / 60 % 60;
/* 452 */     stringBuilder.append((paramInt < 0) ? "-" : "+")
/* 453 */       .append((j < 10) ? "0" : "").append(j)
/* 454 */       .append((k < 10) ? ":0" : ":").append(k);
/* 455 */     int m = i % 60;
/* 456 */     if (m != 0) {
/* 457 */       stringBuilder.append((m < 10) ? ":0" : ":").append(m);
/*     */     }
/* 459 */     return stringBuilder.toString();
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
/*     */   public int getTotalSeconds() {
/* 474 */     return this.totalSeconds;
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
/*     */   public String getId() {
/* 492 */     return this.id;
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
/*     */   public ZoneRules getRules() {
/* 505 */     return ZoneRules.of(this);
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
/*     */   public boolean isSupported(TemporalField paramTemporalField) {
/* 530 */     if (paramTemporalField instanceof ChronoField) {
/* 531 */       return (paramTemporalField == ChronoField.OFFSET_SECONDS);
/*     */     }
/* 533 */     return (paramTemporalField != null && paramTemporalField.isSupportedBy(this));
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
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 561 */     return super.range(paramTemporalField);
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
/*     */   public int get(TemporalField paramTemporalField) {
/* 591 */     if (paramTemporalField == ChronoField.OFFSET_SECONDS)
/* 592 */       return this.totalSeconds; 
/* 593 */     if (paramTemporalField instanceof ChronoField) {
/* 594 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 596 */     return range(paramTemporalField).checkValidIntValue(getLong(paramTemporalField), paramTemporalField);
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
/*     */   public long getLong(TemporalField paramTemporalField) {
/* 623 */     if (paramTemporalField == ChronoField.OFFSET_SECONDS)
/* 624 */       return this.totalSeconds; 
/* 625 */     if (paramTemporalField instanceof ChronoField) {
/* 626 */       throw new UnsupportedTemporalTypeException("Unsupported field: " + paramTemporalField);
/*     */     }
/* 628 */     return paramTemporalField.getFrom(this);
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
/*     */   public <R> R query(TemporalQuery<R> paramTemporalQuery) {
/* 653 */     if (paramTemporalQuery == TemporalQueries.offset() || paramTemporalQuery == TemporalQueries.zone()) {
/* 654 */       return (R)this;
/*     */     }
/* 656 */     return super.query(paramTemporalQuery);
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
/*     */   public Temporal adjustInto(Temporal paramTemporal) {
/* 685 */     return paramTemporal.with(ChronoField.OFFSET_SECONDS, this.totalSeconds);
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
/*     */   public int compareTo(ZoneOffset paramZoneOffset) {
/* 705 */     return paramZoneOffset.totalSeconds - this.totalSeconds;
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
/*     */   public boolean equals(Object paramObject) {
/* 720 */     if (this == paramObject) {
/* 721 */       return true;
/*     */     }
/* 723 */     if (paramObject instanceof ZoneOffset) {
/* 724 */       return (this.totalSeconds == ((ZoneOffset)paramObject).totalSeconds);
/*     */     }
/* 726 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 736 */     return this.totalSeconds;
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
/* 747 */     return this.id;
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
/*     */   private Object writeReplace() {
/* 767 */     return new Ser((byte)8, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws InvalidObjectException {
/* 777 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */ 
/*     */   
/*     */   void write(DataOutput paramDataOutput) throws IOException {
/* 782 */     paramDataOutput.writeByte(8);
/* 783 */     writeExternal(paramDataOutput);
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 787 */     int i = this.totalSeconds;
/* 788 */     boolean bool = (i % 900 == 0) ? (i / 900) : true;
/* 789 */     paramDataOutput.writeByte(bool);
/* 790 */     if (bool == 127) {
/* 791 */       paramDataOutput.writeInt(i);
/*     */     }
/*     */   }
/*     */   
/*     */   static ZoneOffset readExternal(DataInput paramDataInput) throws IOException {
/* 796 */     byte b = paramDataInput.readByte();
/* 797 */     return (b == Byte.MAX_VALUE) ? ofTotalSeconds(paramDataInput.readInt()) : ofTotalSeconds(b * 900);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/ZoneOffset.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */