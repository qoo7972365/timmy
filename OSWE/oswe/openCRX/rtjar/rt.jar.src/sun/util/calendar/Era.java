/*     */ package sun.util.calendar;
/*     */ 
/*     */ import java.util.Locale;
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
/*     */ public final class Era
/*     */ {
/*     */   private final String name;
/*     */   private final String abbr;
/*     */   private final long since;
/*     */   private final CalendarDate sinceDate;
/*     */   private final boolean localTime;
/*     */   private int hash;
/*     */   
/*     */   public Era(String paramString1, String paramString2, long paramLong, boolean paramBoolean) {
/* 131 */     this.hash = 0; this.name = paramString1; this.abbr = paramString2; this.since = paramLong; this.localTime = paramBoolean; Gregorian gregorian = CalendarSystem.getGregorianCalendar(); Gregorian.Date date = gregorian.newCalendarDate((TimeZone)null); gregorian.getCalendarDate(paramLong, date); this.sinceDate = new ImmutableGregorianDate(date);
/*     */   }
/*     */   public String getName() { return this.name; }
/* 134 */   public String getDisplayName(Locale paramLocale) { return this.name; } public String getAbbreviation() { return this.abbr; } public String getDiaplayAbbreviation(Locale paramLocale) { return this.abbr; } public int hashCode() { if (this.hash == 0) {
/* 135 */       this.hash = this.name.hashCode() ^ this.abbr.hashCode() ^ (int)this.since ^ (int)(this.since >> 32L) ^ (this.localTime ? 1 : 0);
/*     */     }
/*     */     
/* 138 */     return this.hash; }
/*     */   public long getSince(TimeZone paramTimeZone) { if (paramTimeZone == null || !this.localTime) return this.since;  int i = paramTimeZone.getOffset(this.since); return this.since - i; }
/*     */   public CalendarDate getSinceDate() { return this.sinceDate; }
/*     */   public boolean isLocalTime() { return this.localTime; }
/* 142 */   public boolean equals(Object paramObject) { if (!(paramObject instanceof Era)) return false;  Era era = (Era)paramObject; return (this.name.equals(era.name) && this.abbr.equals(era.abbr) && this.since == era.since && this.localTime == era.localTime); } public String toString() { StringBuilder stringBuilder = new StringBuilder();
/* 143 */     stringBuilder.append('[');
/* 144 */     stringBuilder.append(getName()).append(" (");
/* 145 */     stringBuilder.append(getAbbreviation()).append(')');
/* 146 */     stringBuilder.append(" since ").append(getSinceDate());
/* 147 */     if (this.localTime) {
/* 148 */       stringBuilder.setLength(stringBuilder.length() - 1);
/* 149 */       stringBuilder.append(" local time");
/*     */     } 
/* 151 */     stringBuilder.append(']');
/* 152 */     return stringBuilder.toString(); }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/Era.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */