/*     */ package sun.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import sun.util.locale.provider.CalendarDataUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuddhistCalendar
/*     */   extends GregorianCalendar
/*     */ {
/*     */   private static final long serialVersionUID = -8527488697350388578L;
/*     */   private static final int BUDDHIST_YEAR_OFFSET = 543;
/*     */   
/*     */   public BuddhistCalendar() {}
/*     */   
/*     */   public BuddhistCalendar(TimeZone paramTimeZone) {
/*  64 */     super(paramTimeZone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuddhistCalendar(Locale paramLocale) {
/*  73 */     super(paramLocale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuddhistCalendar(TimeZone paramTimeZone, Locale paramLocale) {
/*  83 */     super(paramTimeZone, paramLocale);
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
/*     */   public String getCalendarType() {
/*  95 */     return "buddhist";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 105 */     return (paramObject instanceof BuddhistCalendar && super
/* 106 */       .equals(paramObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 115 */     return super.hashCode() ^ 0x21F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(int paramInt) {
/* 126 */     if (paramInt == 1) {
/* 127 */       return super.get(paramInt) + this.yearOffset;
/*     */     }
/* 129 */     return super.get(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int paramInt1, int paramInt2) {
/* 140 */     if (paramInt1 == 1) {
/* 141 */       super.set(paramInt1, paramInt2 - this.yearOffset);
/*     */     } else {
/* 143 */       super.set(paramInt1, paramInt2);
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
/*     */   public void add(int paramInt1, int paramInt2) {
/* 155 */     int i = this.yearOffset;
/*     */ 
/*     */     
/* 158 */     this.yearOffset = 0;
/*     */     try {
/* 160 */       super.add(paramInt1, paramInt2);
/*     */     } finally {
/* 162 */       this.yearOffset = i;
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
/*     */   public void roll(int paramInt1, int paramInt2) {
/* 176 */     int i = this.yearOffset;
/*     */ 
/*     */     
/* 179 */     this.yearOffset = 0;
/*     */     try {
/* 181 */       super.roll(paramInt1, paramInt2);
/*     */     } finally {
/* 183 */       this.yearOffset = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayName(int paramInt1, int paramInt2, Locale paramLocale) {
/* 189 */     if (paramInt1 != 0) {
/* 190 */       return super.getDisplayName(paramInt1, paramInt2, paramLocale);
/*     */     }
/*     */     
/* 193 */     return CalendarDataUtility.retrieveFieldValueName("buddhist", paramInt1, get(paramInt1), paramInt2, paramLocale);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Integer> getDisplayNames(int paramInt1, int paramInt2, Locale paramLocale) {
/* 198 */     if (paramInt1 != 0) {
/* 199 */       return super.getDisplayNames(paramInt1, paramInt2, paramLocale);
/*     */     }
/* 201 */     return CalendarDataUtility.retrieveFieldValueNames("buddhist", paramInt1, paramInt2, paramLocale);
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
/*     */   public int getActualMaximum(int paramInt) {
/* 215 */     int i = this.yearOffset;
/*     */ 
/*     */     
/* 218 */     this.yearOffset = 0;
/*     */     try {
/* 220 */       return super.getActualMaximum(paramInt);
/*     */     } finally {
/* 222 */       this.yearOffset = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 231 */     String str = super.toString();
/*     */     
/* 233 */     if (!isSet(1)) {
/* 234 */       return str;
/*     */     }
/*     */ 
/*     */     
/* 238 */     int i = str.indexOf("YEAR=");
/*     */ 
/*     */     
/* 241 */     if (i == -1) {
/* 242 */       return str;
/*     */     }
/* 244 */     i += "YEAR=".length();
/* 245 */     StringBuilder stringBuilder = new StringBuilder(str.substring(0, i));
/*     */     
/* 247 */     while (Character.isDigit(str.charAt(i++)));
/*     */     
/* 249 */     int j = internalGet(1) + 543;
/* 250 */     stringBuilder.append(j).append(str.substring(i - 1));
/* 251 */     return stringBuilder.toString();
/*     */   }
/*     */   
/* 254 */   private transient int yearOffset = 543;
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 258 */     paramObjectInputStream.defaultReadObject();
/* 259 */     this.yearOffset = 543;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/BuddhistCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */