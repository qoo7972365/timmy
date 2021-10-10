/*     */ package java.time.chrono;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.LocalDate;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.time.format.TextStyle;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.time.temporal.ValueRange;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import sun.util.calendar.CalendarDate;
/*     */ import sun.util.calendar.Era;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JapaneseEra
/*     */   implements Era, Serializable
/*     */ {
/*     */   static final int ERA_OFFSET = 2;
/*     */   static final Era[] ERA_CONFIG;
/* 137 */   public static final JapaneseEra MEIJI = new JapaneseEra(-1, LocalDate.of(1868, 1, 1));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   public static final JapaneseEra TAISHO = new JapaneseEra(0, LocalDate.of(1912, 7, 30));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   public static final JapaneseEra SHOWA = new JapaneseEra(1, LocalDate.of(1926, 12, 25));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static final JapaneseEra HEISEI = new JapaneseEra(2, LocalDate.of(1989, 1, 8));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   private static final JapaneseEra REIWA = new JapaneseEra(3, LocalDate.of(2019, 5, 1));
/*     */ 
/*     */ 
/*     */   
/* 161 */   private static final int N_ERA_CONSTANTS = REIWA.getValue() + 2;
/*     */   
/*     */   private static final long serialVersionUID = 1466499369062886794L;
/*     */   
/*     */   private static final JapaneseEra[] KNOWN_ERAS;
/*     */   
/*     */   private final transient int eraValue;
/*     */   
/*     */   private final transient LocalDate since;
/*     */   
/*     */   static {
/* 172 */     ERA_CONFIG = JapaneseChronology.JCAL.getEras();
/*     */     
/* 174 */     KNOWN_ERAS = new JapaneseEra[ERA_CONFIG.length];
/* 175 */     KNOWN_ERAS[0] = MEIJI;
/* 176 */     KNOWN_ERAS[1] = TAISHO;
/* 177 */     KNOWN_ERAS[2] = SHOWA;
/* 178 */     KNOWN_ERAS[3] = HEISEI;
/* 179 */     KNOWN_ERAS[4] = REIWA;
/* 180 */     for (int i = N_ERA_CONSTANTS; i < ERA_CONFIG.length; i++) {
/* 181 */       CalendarDate calendarDate = ERA_CONFIG[i].getSinceDate();
/* 182 */       LocalDate localDate = LocalDate.of(calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDayOfMonth());
/* 183 */       KNOWN_ERAS[i] = new JapaneseEra(i - 2 + 1, localDate);
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
/*     */   private JapaneseEra(int paramInt, LocalDate paramLocalDate) {
/* 203 */     this.eraValue = paramInt;
/* 204 */     this.since = paramLocalDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Era getPrivateEra() {
/* 214 */     return ERA_CONFIG[ordinal(this.eraValue)];
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
/*     */   public static JapaneseEra of(int paramInt) {
/* 238 */     if (paramInt < MEIJI.eraValue || paramInt + 2 > KNOWN_ERAS.length) {
/* 239 */       throw new DateTimeException("Invalid era: " + paramInt);
/*     */     }
/* 241 */     return KNOWN_ERAS[ordinal(paramInt)];
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
/*     */   public static JapaneseEra valueOf(String paramString) {
/* 255 */     Objects.requireNonNull(paramString, "japaneseEra");
/* 256 */     for (JapaneseEra japaneseEra : KNOWN_ERAS) {
/* 257 */       if (japaneseEra.getName().equals(paramString)) {
/* 258 */         return japaneseEra;
/*     */       }
/*     */     } 
/* 261 */     throw new IllegalArgumentException("japaneseEra is invalid");
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
/*     */   public static JapaneseEra[] values() {
/* 276 */     return Arrays.<JapaneseEra>copyOf(KNOWN_ERAS, KNOWN_ERAS.length);
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
/*     */   public String getDisplayName(TextStyle paramTextStyle, Locale paramLocale) {
/* 289 */     if (getValue() > N_ERA_CONSTANTS - 2) {
/* 290 */       Objects.requireNonNull(paramLocale, "locale");
/* 291 */       return (paramTextStyle.asNormal() == TextStyle.NARROW) ? getAbbreviation() : getName();
/*     */     } 
/*     */     
/* 294 */     return (new DateTimeFormatterBuilder())
/* 295 */       .appendText(ChronoField.ERA, paramTextStyle)
/* 296 */       .toFormatter(paramLocale)
/* 297 */       .withChronology(JapaneseChronology.INSTANCE)
/* 298 */       .format((this == MEIJI) ? JapaneseDate.MEIJI_6_ISODATE : this.since);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static JapaneseEra from(LocalDate paramLocalDate) {
/* 309 */     if (paramLocalDate.isBefore(JapaneseDate.MEIJI_6_ISODATE)) {
/* 310 */       throw new DateTimeException("JapaneseDate before Meiji 6 are not supported");
/*     */     }
/* 312 */     for (int i = KNOWN_ERAS.length - 1; i > 0; i--) {
/* 313 */       JapaneseEra japaneseEra = KNOWN_ERAS[i];
/* 314 */       if (paramLocalDate.compareTo(japaneseEra.since) >= 0) {
/* 315 */         return japaneseEra;
/*     */       }
/*     */     } 
/* 318 */     return null;
/*     */   }
/*     */   
/*     */   static JapaneseEra toJapaneseEra(Era paramEra) {
/* 322 */     for (int i = ERA_CONFIG.length - 1; i >= 0; i--) {
/* 323 */       if (ERA_CONFIG[i].equals(paramEra)) {
/* 324 */         return KNOWN_ERAS[i];
/*     */       }
/*     */     } 
/* 327 */     return null;
/*     */   }
/*     */   
/*     */   static Era privateEraFrom(LocalDate paramLocalDate) {
/* 331 */     for (int i = KNOWN_ERAS.length - 1; i > 0; i--) {
/* 332 */       JapaneseEra japaneseEra = KNOWN_ERAS[i];
/* 333 */       if (paramLocalDate.compareTo(japaneseEra.since) >= 0) {
/* 334 */         return ERA_CONFIG[i];
/*     */       }
/*     */     } 
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int ordinal(int paramInt) {
/* 348 */     return paramInt + 2 - 1;
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
/*     */   public int getValue() {
/* 363 */     return this.eraValue;
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
/*     */   public ValueRange range(TemporalField paramTemporalField) {
/* 394 */     if (paramTemporalField == ChronoField.ERA) {
/* 395 */       return JapaneseChronology.INSTANCE.range(ChronoField.ERA);
/*     */     }
/* 397 */     return super.range(paramTemporalField);
/*     */   }
/*     */ 
/*     */   
/*     */   String getAbbreviation() {
/* 402 */     return ERA_CONFIG[ordinal(getValue())].getAbbreviation();
/*     */   }
/*     */   
/*     */   String getName() {
/* 406 */     return ERA_CONFIG[ordinal(getValue())].getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 411 */     return getName();
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
/* 422 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*     */   private Object writeReplace() {
/* 438 */     return new Ser((byte)5, this);
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 442 */     paramDataOutput.writeByte(getValue());
/*     */   }
/*     */   
/*     */   static JapaneseEra readExternal(DataInput paramDataInput) throws IOException {
/* 446 */     byte b = paramDataInput.readByte();
/* 447 */     return of(b);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/chrono/JapaneseEra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */