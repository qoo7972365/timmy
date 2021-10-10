/*     */ package sun.util.calendar;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class LocalGregorianCalendar
/*     */   extends BaseCalendar
/*     */ {
/*     */   private String name;
/*     */   private Era[] eras;
/*     */   
/*     */   public static class Date
/*     */     extends BaseCalendar.Date
/*     */   {
/*     */     protected Date(TimeZone param1TimeZone) {
/*  52 */       super(param1TimeZone);
/*     */     }
/*     */     
/*  55 */     private int gregorianYear = Integer.MIN_VALUE;
/*     */ 
/*     */     
/*     */     public Date setEra(Era param1Era) {
/*  59 */       if (getEra() != param1Era) {
/*  60 */         super.setEra(param1Era);
/*  61 */         this.gregorianYear = Integer.MIN_VALUE;
/*     */       } 
/*  63 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date addYear(int param1Int) {
/*  68 */       super.addYear(param1Int);
/*  69 */       this.gregorianYear += param1Int;
/*  70 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Date setYear(int param1Int) {
/*  75 */       if (getYear() != param1Int) {
/*  76 */         super.setYear(param1Int);
/*  77 */         this.gregorianYear = Integer.MIN_VALUE;
/*     */       } 
/*  79 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getNormalizedYear() {
/*  84 */       return this.gregorianYear;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setNormalizedYear(int param1Int) {
/*  89 */       this.gregorianYear = param1Int;
/*     */     }
/*     */     
/*     */     void setLocalEra(Era param1Era) {
/*  93 */       super.setEra(param1Era);
/*     */     }
/*     */     
/*     */     void setLocalYear(int param1Int) {
/*  97 */       super.setYear(param1Int);
/*     */     }
/*     */     protected Date() {}
/*     */     
/*     */     public String toString() {
/* 102 */       String str = super.toString();
/* 103 */       str = str.substring(str.indexOf('T'));
/* 104 */       StringBuffer stringBuffer = new StringBuffer();
/* 105 */       Era era = getEra();
/* 106 */       if (era != null) {
/* 107 */         String str1 = era.getAbbreviation();
/* 108 */         if (str1 != null) {
/* 109 */           stringBuffer.append(str1);
/*     */         }
/*     */       } 
/* 112 */       stringBuffer.append(getYear()).append('.');
/* 113 */       CalendarUtils.sprintf0d(stringBuffer, getMonth(), 2).append('.');
/* 114 */       CalendarUtils.sprintf0d(stringBuffer, getDayOfMonth(), 2);
/* 115 */       stringBuffer.append(str);
/* 116 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   static LocalGregorianCalendar getLocalGregorianCalendar(String paramString) {
/*     */     Properties properties;
/*     */     try {
/* 123 */       properties = CalendarSystem.getCalendarProperties();
/* 124 */     } catch (IOException|IllegalArgumentException iOException) {
/* 125 */       throw new InternalError(iOException);
/*     */     } 
/*     */     
/* 128 */     String str = properties.getProperty("calendar." + paramString + ".eras");
/* 129 */     if (str == null) {
/* 130 */       return null;
/*     */     }
/* 132 */     ArrayList<Era> arrayList = new ArrayList();
/* 133 */     StringTokenizer stringTokenizer = new StringTokenizer(str, ";");
/* 134 */     while (stringTokenizer.hasMoreTokens()) {
/* 135 */       String str1 = stringTokenizer.nextToken().trim();
/* 136 */       StringTokenizer stringTokenizer1 = new StringTokenizer(str1, ",");
/* 137 */       String str2 = null;
/* 138 */       boolean bool = true;
/* 139 */       long l = 0L;
/* 140 */       String str3 = null;
/*     */       
/* 142 */       while (stringTokenizer1.hasMoreTokens()) {
/* 143 */         String str4 = stringTokenizer1.nextToken();
/* 144 */         int i = str4.indexOf('=');
/*     */         
/* 146 */         if (i == -1) {
/* 147 */           return null;
/*     */         }
/* 149 */         String str5 = str4.substring(0, i);
/* 150 */         String str6 = str4.substring(i + 1);
/* 151 */         if ("name".equals(str5)) {
/* 152 */           str2 = str6; continue;
/* 153 */         }  if ("since".equals(str5)) {
/* 154 */           if (str6.endsWith("u")) {
/* 155 */             bool = false;
/* 156 */             l = Long.parseLong(str6.substring(0, str6.length() - 1)); continue;
/*     */           } 
/* 158 */           l = Long.parseLong(str6); continue;
/*     */         } 
/* 160 */         if ("abbr".equals(str5)) {
/* 161 */           str3 = str6; continue;
/*     */         } 
/* 163 */         throw new RuntimeException("Unknown key word: " + str5);
/*     */       } 
/*     */       
/* 166 */       Era era = new Era(str2, str3, l, bool);
/* 167 */       arrayList.add(era);
/*     */     } 
/* 169 */     Era[] arrayOfEra = new Era[arrayList.size()];
/* 170 */     arrayList.toArray(arrayOfEra);
/*     */     
/* 172 */     return new LocalGregorianCalendar(paramString, arrayOfEra);
/*     */   }
/*     */   
/*     */   private LocalGregorianCalendar(String paramString, Era[] paramArrayOfEra) {
/* 176 */     this.name = paramString;
/* 177 */     this.eras = paramArrayOfEra;
/* 178 */     setEras(paramArrayOfEra);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 183 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getCalendarDate() {
/* 188 */     return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getCalendarDate(long paramLong) {
/* 193 */     return getCalendarDate(paramLong, newCalendarDate());
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getCalendarDate(long paramLong, TimeZone paramTimeZone) {
/* 198 */     return getCalendarDate(paramLong, newCalendarDate(paramTimeZone));
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getCalendarDate(long paramLong, CalendarDate paramCalendarDate) {
/* 203 */     Date date = (Date)super.getCalendarDate(paramLong, paramCalendarDate);
/* 204 */     return adjustYear(date, paramLong, date.getZoneOffset());
/*     */   }
/*     */   
/*     */   private Date adjustYear(Date paramDate, long paramLong, int paramInt) {
/*     */     int i;
/* 209 */     for (i = this.eras.length - 1; i >= 0; i--) {
/* 210 */       Era era = this.eras[i];
/* 211 */       long l = era.getSince(null);
/* 212 */       if (era.isLocalTime()) {
/* 213 */         l -= paramInt;
/*     */       }
/* 215 */       if (paramLong >= l) {
/* 216 */         paramDate.setLocalEra(era);
/* 217 */         int j = paramDate.getNormalizedYear() - era.getSinceDate().getYear() + 1;
/* 218 */         paramDate.setLocalYear(j);
/*     */         break;
/*     */       } 
/*     */     } 
/* 222 */     if (i < 0) {
/* 223 */       paramDate.setLocalEra((Era)null);
/* 224 */       paramDate.setLocalYear(paramDate.getNormalizedYear());
/*     */     } 
/* 226 */     paramDate.setNormalized(true);
/* 227 */     return paramDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date newCalendarDate() {
/* 232 */     return new Date();
/*     */   }
/*     */ 
/*     */   
/*     */   public Date newCalendarDate(TimeZone paramTimeZone) {
/* 237 */     return new Date(paramTimeZone);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validate(CalendarDate paramCalendarDate) {
/* 242 */     Date date = (Date)paramCalendarDate;
/* 243 */     Era era = date.getEra();
/* 244 */     if (era != null) {
/* 245 */       if (!validateEra(era)) {
/* 246 */         return false;
/*     */       }
/* 248 */       date.setNormalizedYear(era.getSinceDate().getYear() + date.getYear() - 1);
/* 249 */       Date date1 = newCalendarDate(paramCalendarDate.getZone());
/* 250 */       date1.setEra(era).setDate(paramCalendarDate.getYear(), paramCalendarDate.getMonth(), paramCalendarDate.getDayOfMonth());
/* 251 */       normalize(date1);
/* 252 */       if (date1.getEra() != era) {
/* 253 */         return false;
/*     */       }
/*     */     } else {
/* 256 */       if (paramCalendarDate.getYear() >= this.eras[0].getSinceDate().getYear()) {
/* 257 */         return false;
/*     */       }
/* 259 */       date.setNormalizedYear(date.getYear());
/*     */     } 
/* 261 */     return super.validate(date);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validateEra(Era paramEra) {
/* 266 */     for (byte b = 0; b < this.eras.length; b++) {
/* 267 */       if (paramEra == this.eras[b]) {
/* 268 */         return true;
/*     */       }
/*     */     } 
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean normalize(CalendarDate paramCalendarDate) {
/* 276 */     if (paramCalendarDate.isNormalized()) {
/* 277 */       return true;
/*     */     }
/*     */     
/* 280 */     normalizeYear(paramCalendarDate);
/* 281 */     Date date = (Date)paramCalendarDate;
/*     */ 
/*     */     
/* 284 */     super.normalize(date);
/*     */     
/* 286 */     boolean bool = false;
/* 287 */     long l = 0L;
/* 288 */     int i = date.getNormalizedYear();
/*     */     
/* 290 */     Era era = null; int j;
/* 291 */     for (j = this.eras.length - 1; j >= 0; j--) {
/* 292 */       era = this.eras[j];
/* 293 */       if (era.isLocalTime()) {
/* 294 */         CalendarDate calendarDate = era.getSinceDate();
/* 295 */         int k = calendarDate.getYear();
/* 296 */         if (i > k) {
/*     */           break;
/*     */         }
/* 299 */         if (i == k) {
/* 300 */           int m = date.getMonth();
/* 301 */           int n = calendarDate.getMonth();
/* 302 */           if (m > n) {
/*     */             break;
/*     */           }
/* 305 */           if (m == n) {
/* 306 */             int i1 = date.getDayOfMonth();
/* 307 */             int i2 = calendarDate.getDayOfMonth();
/* 308 */             if (i1 > i2) {
/*     */               break;
/*     */             }
/* 311 */             if (i1 == i2) {
/* 312 */               long l1 = date.getTimeOfDay();
/* 313 */               long l2 = calendarDate.getTimeOfDay();
/* 314 */               if (l1 >= l2) {
/*     */                 break;
/*     */               }
/* 317 */               j--;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 323 */         if (!bool) {
/* 324 */           l = getTime(paramCalendarDate);
/* 325 */           bool = true;
/*     */         } 
/*     */         
/* 328 */         long l1 = era.getSince(paramCalendarDate.getZone());
/* 329 */         if (l >= l1) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 334 */     if (j >= 0) {
/* 335 */       date.setLocalEra(era);
/* 336 */       int k = date.getNormalizedYear() - era.getSinceDate().getYear() + 1;
/* 337 */       date.setLocalYear(k);
/*     */     } else {
/*     */       
/* 340 */       date.setEra((Era)null);
/* 341 */       date.setLocalYear(i);
/* 342 */       date.setNormalizedYear(i);
/*     */     } 
/* 344 */     date.setNormalized(true);
/* 345 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   void normalizeMonth(CalendarDate paramCalendarDate) {
/* 350 */     normalizeYear(paramCalendarDate);
/* 351 */     super.normalizeMonth(paramCalendarDate);
/*     */   }
/*     */   
/*     */   void normalizeYear(CalendarDate paramCalendarDate) {
/* 355 */     Date date = (Date)paramCalendarDate;
/*     */ 
/*     */     
/* 358 */     Era era = date.getEra();
/* 359 */     if (era == null || !validateEra(era)) {
/* 360 */       date.setNormalizedYear(date.getYear());
/*     */     } else {
/* 362 */       date.setNormalizedYear(era.getSinceDate().getYear() + date.getYear() - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeapYear(int paramInt) {
/* 372 */     return CalendarUtils.isGregorianLeapYear(paramInt);
/*     */   }
/*     */   
/*     */   public boolean isLeapYear(Era paramEra, int paramInt) {
/* 376 */     if (paramEra == null) {
/* 377 */       return isLeapYear(paramInt);
/*     */     }
/* 379 */     int i = paramEra.getSinceDate().getYear() + paramInt - 1;
/* 380 */     return isLeapYear(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getCalendarDateFromFixedDate(CalendarDate paramCalendarDate, long paramLong) {
/* 385 */     Date date = (Date)paramCalendarDate;
/* 386 */     super.getCalendarDateFromFixedDate(date, paramLong);
/* 387 */     adjustYear(date, (paramLong - 719163L) * 86400000L, 0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/calendar/LocalGregorianCalendar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */