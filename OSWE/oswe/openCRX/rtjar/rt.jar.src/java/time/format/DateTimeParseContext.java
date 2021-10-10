/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.ZoneId;
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DateTimeParseContext
/*     */ {
/*     */   private DateTimeFormatter formatter;
/*     */   private boolean caseSensitive = true;
/*     */   private boolean strict = true;
/* 109 */   private final ArrayList<Parsed> parsed = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/* 113 */   private ArrayList<Consumer<Chronology>> chronoListeners = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DateTimeParseContext(DateTimeFormatter paramDateTimeFormatter) {
/* 122 */     this.formatter = paramDateTimeFormatter;
/* 123 */     this.parsed.add(new Parsed());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DateTimeParseContext copy() {
/* 131 */     DateTimeParseContext dateTimeParseContext = new DateTimeParseContext(this.formatter);
/* 132 */     dateTimeParseContext.caseSensitive = this.caseSensitive;
/* 133 */     dateTimeParseContext.strict = this.strict;
/* 134 */     return dateTimeParseContext;
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
/*     */   Locale getLocale() {
/* 147 */     return this.formatter.getLocale();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DecimalStyle getDecimalStyle() {
/* 158 */     return this.formatter.getDecimalStyle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Chronology getEffectiveChronology() {
/* 167 */     Chronology chronology = (currentParsed()).chrono;
/* 168 */     if (chronology == null) {
/* 169 */       chronology = this.formatter.getChronology();
/* 170 */       if (chronology == null) {
/* 171 */         chronology = IsoChronology.INSTANCE;
/*     */       }
/*     */     } 
/* 174 */     return chronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isCaseSensitive() {
/* 184 */     return this.caseSensitive;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setCaseSensitive(boolean paramBoolean) {
/* 193 */     this.caseSensitive = paramBoolean;
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
/*     */   boolean subSequenceEquals(CharSequence paramCharSequence1, int paramInt1, CharSequence paramCharSequence2, int paramInt2, int paramInt3) {
/* 209 */     if (paramInt1 + paramInt3 > paramCharSequence1.length() || paramInt2 + paramInt3 > paramCharSequence2.length()) {
/* 210 */       return false;
/*     */     }
/* 212 */     if (isCaseSensitive()) {
/* 213 */       for (byte b = 0; b < paramInt3; b++) {
/* 214 */         char c1 = paramCharSequence1.charAt(paramInt1 + b);
/* 215 */         char c2 = paramCharSequence2.charAt(paramInt2 + b);
/* 216 */         if (c1 != c2) {
/* 217 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 221 */       for (byte b = 0; b < paramInt3; b++) {
/* 222 */         char c1 = paramCharSequence1.charAt(paramInt1 + b);
/* 223 */         char c2 = paramCharSequence2.charAt(paramInt2 + b);
/* 224 */         if (c1 != c2 && Character.toUpperCase(c1) != Character.toUpperCase(c2) && 
/* 225 */           Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
/* 226 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 230 */     return true;
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
/*     */   boolean charEquals(char paramChar1, char paramChar2) {
/* 242 */     if (isCaseSensitive()) {
/* 243 */       return (paramChar1 == paramChar2);
/*     */     }
/* 245 */     return charEqualsIgnoreCase(paramChar1, paramChar2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean charEqualsIgnoreCase(char paramChar1, char paramChar2) {
/* 256 */     return (paramChar1 == paramChar2 || 
/* 257 */       Character.toUpperCase(paramChar1) == Character.toUpperCase(paramChar2) || 
/* 258 */       Character.toLowerCase(paramChar1) == Character.toLowerCase(paramChar2));
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
/*     */   boolean isStrict() {
/* 270 */     return this.strict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setStrict(boolean paramBoolean) {
/* 279 */     this.strict = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startOptional() {
/* 287 */     this.parsed.add(currentParsed().copy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void endOptional(boolean paramBoolean) {
/* 296 */     if (paramBoolean) {
/* 297 */       this.parsed.remove(this.parsed.size() - 2);
/*     */     } else {
/* 299 */       this.parsed.remove(this.parsed.size() - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Parsed currentParsed() {
/* 310 */     return this.parsed.get(this.parsed.size() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Parsed toUnresolved() {
/* 319 */     return currentParsed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TemporalAccessor toResolved(ResolverStyle paramResolverStyle, Set<TemporalField> paramSet) {
/* 328 */     Parsed parsed = currentParsed();
/* 329 */     parsed.chrono = getEffectiveChronology();
/* 330 */     parsed.zone = (parsed.zone != null) ? parsed.zone : this.formatter.getZone();
/* 331 */     return parsed.resolve(paramResolverStyle, paramSet);
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
/*     */   Long getParsed(TemporalField paramTemporalField) {
/* 348 */     return (currentParsed()).fieldValues.get(paramTemporalField);
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
/*     */   int setParsedField(TemporalField paramTemporalField, long paramLong, int paramInt1, int paramInt2) {
/* 364 */     Objects.requireNonNull(paramTemporalField, "field");
/* 365 */     Long long_ = (currentParsed()).fieldValues.put(paramTemporalField, Long.valueOf(paramLong));
/* 366 */     return (long_ != null && long_.longValue() != paramLong) ? (paramInt1 ^ 0xFFFFFFFF) : paramInt2;
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
/*     */   void setParsed(Chronology paramChronology) {
/* 382 */     Objects.requireNonNull(paramChronology, "chrono");
/* 383 */     (currentParsed()).chrono = paramChronology;
/* 384 */     if (this.chronoListeners != null && !this.chronoListeners.isEmpty()) {
/*     */       
/* 386 */       Consumer[] arrayOfConsumer1 = new Consumer[1];
/* 387 */       Consumer[] arrayOfConsumer2 = this.chronoListeners.<Consumer>toArray(arrayOfConsumer1);
/* 388 */       this.chronoListeners.clear();
/* 389 */       for (Consumer<Chronology> consumer : arrayOfConsumer2) {
/* 390 */         consumer.accept(paramChronology);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addChronoChangedListener(Consumer<Chronology> paramConsumer) {
/* 401 */     if (this.chronoListeners == null) {
/* 402 */       this.chronoListeners = new ArrayList<>();
/*     */     }
/* 404 */     this.chronoListeners.add(paramConsumer);
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
/*     */   void setParsed(ZoneId paramZoneId) {
/* 416 */     Objects.requireNonNull(paramZoneId, "zone");
/* 417 */     (currentParsed()).zone = paramZoneId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setParsedLeapSecond() {
/* 424 */     (currentParsed()).leapSecond = true;
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
/* 435 */     return currentParsed().toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DateTimeParseContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */