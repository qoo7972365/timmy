/*     */ package java.time;
/*     */ 
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.format.TextStyle;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.TemporalQueries;
/*     */ import java.time.temporal.TemporalQuery;
/*     */ import java.time.temporal.UnsupportedTemporalTypeException;
/*     */ import java.time.zone.ZoneRules;
/*     */ import java.time.zone.ZoneRulesException;
/*     */ import java.time.zone.ZoneRulesProvider;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ZoneId
/*     */   implements Serializable
/*     */ {
/*     */   public static final Map<String, String> SHORT_IDS;
/*     */   private static final long serialVersionUID = 8352817235686L;
/*     */   
/*     */   static {
/* 225 */     HashMap<Object, Object> hashMap = new HashMap<>(64);
/* 226 */     hashMap.put("ACT", "Australia/Darwin");
/* 227 */     hashMap.put("AET", "Australia/Sydney");
/* 228 */     hashMap.put("AGT", "America/Argentina/Buenos_Aires");
/* 229 */     hashMap.put("ART", "Africa/Cairo");
/* 230 */     hashMap.put("AST", "America/Anchorage");
/* 231 */     hashMap.put("BET", "America/Sao_Paulo");
/* 232 */     hashMap.put("BST", "Asia/Dhaka");
/* 233 */     hashMap.put("CAT", "Africa/Harare");
/* 234 */     hashMap.put("CNT", "America/St_Johns");
/* 235 */     hashMap.put("CST", "America/Chicago");
/* 236 */     hashMap.put("CTT", "Asia/Shanghai");
/* 237 */     hashMap.put("EAT", "Africa/Addis_Ababa");
/* 238 */     hashMap.put("ECT", "Europe/Paris");
/* 239 */     hashMap.put("IET", "America/Indiana/Indianapolis");
/* 240 */     hashMap.put("IST", "Asia/Kolkata");
/* 241 */     hashMap.put("JST", "Asia/Tokyo");
/* 242 */     hashMap.put("MIT", "Pacific/Apia");
/* 243 */     hashMap.put("NET", "Asia/Yerevan");
/* 244 */     hashMap.put("NST", "Pacific/Auckland");
/* 245 */     hashMap.put("PLT", "Asia/Karachi");
/* 246 */     hashMap.put("PNT", "America/Phoenix");
/* 247 */     hashMap.put("PRT", "America/Puerto_Rico");
/* 248 */     hashMap.put("PST", "America/Los_Angeles");
/* 249 */     hashMap.put("SST", "Pacific/Guadalcanal");
/* 250 */     hashMap.put("VST", "Asia/Ho_Chi_Minh");
/* 251 */     hashMap.put("EST", "-05:00");
/* 252 */     hashMap.put("MST", "-07:00");
/* 253 */     hashMap.put("HST", "-10:00");
/* 254 */     SHORT_IDS = Collections.unmodifiableMap(hashMap);
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
/*     */   public static ZoneId systemDefault() {
/* 274 */     return TimeZone.getDefault().toZoneId();
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
/*     */   public static Set<String> getAvailableZoneIds() {
/* 290 */     return ZoneRulesProvider.getAvailableZoneIds();
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
/*     */   public static ZoneId of(String paramString, Map<String, String> paramMap) {
/* 311 */     Objects.requireNonNull(paramString, "zoneId");
/* 312 */     Objects.requireNonNull(paramMap, "aliasMap");
/* 313 */     String str = paramMap.get(paramString);
/* 314 */     str = (str != null) ? str : paramString;
/* 315 */     return of(str);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZoneId of(String paramString) {
/* 359 */     return of(paramString, true);
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
/*     */   public static ZoneId ofOffset(String paramString, ZoneOffset paramZoneOffset) {
/* 376 */     Objects.requireNonNull(paramString, "prefix");
/* 377 */     Objects.requireNonNull(paramZoneOffset, "offset");
/* 378 */     if (paramString.length() == 0) {
/* 379 */       return paramZoneOffset;
/*     */     }
/*     */     
/* 382 */     if (!paramString.equals("GMT") && !paramString.equals("UTC") && !paramString.equals("UT")) {
/* 383 */       throw new IllegalArgumentException("prefix should be GMT, UTC or UT, is: " + paramString);
/*     */     }
/*     */     
/* 386 */     if (paramZoneOffset.getTotalSeconds() != 0) {
/* 387 */       paramString = paramString.concat(paramZoneOffset.getId());
/*     */     }
/* 389 */     return new ZoneRegion(paramString, paramZoneOffset.getRules());
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
/*     */   static ZoneId of(String paramString, boolean paramBoolean) {
/* 403 */     Objects.requireNonNull(paramString, "zoneId");
/* 404 */     if (paramString.length() <= 1 || paramString.startsWith("+") || paramString.startsWith("-"))
/* 405 */       return ZoneOffset.of(paramString); 
/* 406 */     if (paramString.startsWith("UTC") || paramString.startsWith("GMT"))
/* 407 */       return ofWithPrefix(paramString, 3, paramBoolean); 
/* 408 */     if (paramString.startsWith("UT")) {
/* 409 */       return ofWithPrefix(paramString, 2, paramBoolean);
/*     */     }
/* 411 */     return ZoneRegion.ofId(paramString, paramBoolean);
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
/*     */   private static ZoneId ofWithPrefix(String paramString, int paramInt, boolean paramBoolean) {
/* 423 */     String str = paramString.substring(0, paramInt);
/* 424 */     if (paramString.length() == paramInt) {
/* 425 */       return ofOffset(str, ZoneOffset.UTC);
/*     */     }
/* 427 */     if (paramString.charAt(paramInt) != '+' && paramString.charAt(paramInt) != '-') {
/* 428 */       return ZoneRegion.ofId(paramString, paramBoolean);
/*     */     }
/*     */     try {
/* 431 */       ZoneOffset zoneOffset = ZoneOffset.of(paramString.substring(paramInt));
/* 432 */       if (zoneOffset == ZoneOffset.UTC) {
/* 433 */         return ofOffset(str, zoneOffset);
/*     */       }
/* 435 */       return ofOffset(str, zoneOffset);
/* 436 */     } catch (DateTimeException dateTimeException) {
/* 437 */       throw new DateTimeException("Invalid ID for offset-based ZoneId: " + paramString, dateTimeException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ZoneId from(TemporalAccessor paramTemporalAccessor) {
/* 463 */     ZoneId zoneId = paramTemporalAccessor.<ZoneId>query(TemporalQueries.zone());
/* 464 */     if (zoneId == null) {
/* 465 */       throw new DateTimeException("Unable to obtain ZoneId from TemporalAccessor: " + paramTemporalAccessor + " of type " + paramTemporalAccessor
/* 466 */           .getClass().getName());
/*     */     }
/* 468 */     return zoneId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ZoneId() {
/* 476 */     if (getClass() != ZoneOffset.class && getClass() != ZoneRegion.class) {
/* 477 */       throw new AssertionError("Invalid subclass");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName(TextStyle paramTextStyle, Locale paramLocale) {
/* 508 */     return (new DateTimeFormatterBuilder()).appendZoneText(paramTextStyle).toFormatter(paramLocale).format(toTemporal());
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
/*     */   private TemporalAccessor toTemporal() {
/* 524 */     return new TemporalAccessor()
/*     */       {
/*     */         public boolean isSupported(TemporalField param1TemporalField) {
/* 527 */           return false;
/*     */         }
/*     */         
/*     */         public long getLong(TemporalField param1TemporalField) {
/* 531 */           throw new UnsupportedTemporalTypeException("Unsupported field: " + param1TemporalField);
/*     */         }
/*     */ 
/*     */         
/*     */         public <R> R query(TemporalQuery<R> param1TemporalQuery) {
/* 536 */           if (param1TemporalQuery == TemporalQueries.zoneId()) {
/* 537 */             return (R)ZoneId.this;
/*     */           }
/* 539 */           return super.query(param1TemporalQuery);
/*     */         }
/*     */       };
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
/*     */   public ZoneId normalized() {
/*     */     try {
/* 582 */       ZoneRules zoneRules = getRules();
/* 583 */       if (zoneRules.isFixedOffset()) {
/* 584 */         return zoneRules.getOffset(Instant.EPOCH);
/*     */       }
/* 586 */     } catch (ZoneRulesException zoneRulesException) {}
/*     */ 
/*     */     
/* 589 */     return this;
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
/* 603 */     if (this == paramObject) {
/* 604 */       return true;
/*     */     }
/* 606 */     if (paramObject instanceof ZoneId) {
/* 607 */       ZoneId zoneId = (ZoneId)paramObject;
/* 608 */       return getId().equals(zoneId.getId());
/*     */     } 
/* 610 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 620 */     return getId().hashCode();
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
/* 631 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 641 */     return getId();
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
/* 662 */     return new Ser((byte)7, this);
/*     */   }
/*     */   
/*     */   public abstract String getId();
/*     */   
/*     */   public abstract ZoneRules getRules();
/*     */   
/*     */   abstract void write(DataOutput paramDataOutput) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/ZoneId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */