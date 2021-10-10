/*     */ package java.text;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CalendarBuilder
/*     */ {
/*     */   private static final int UNSET = 0;
/*     */   private static final int COMPUTED = 1;
/*     */   private static final int MINIMUM_USER_STAMP = 2;
/*     */   private static final int MAX_FIELD = 18;
/*     */   public static final int WEEK_YEAR = 17;
/*     */   public static final int ISO_DAY_OF_WEEK = 1000;
/*  63 */   private final int[] field = new int[36];
/*  64 */   private int nextStamp = 2;
/*  65 */   private int maxFieldIndex = -1;
/*     */ 
/*     */   
/*     */   CalendarBuilder set(int paramInt1, int paramInt2) {
/*  69 */     if (paramInt1 == 1000) {
/*  70 */       paramInt1 = 7;
/*  71 */       paramInt2 = toCalendarDayOfWeek(paramInt2);
/*     */     } 
/*  73 */     this.field[paramInt1] = this.nextStamp++;
/*  74 */     this.field[18 + paramInt1] = paramInt2;
/*  75 */     if (paramInt1 > this.maxFieldIndex && paramInt1 < 17) {
/*  76 */       this.maxFieldIndex = paramInt1;
/*     */     }
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   CalendarBuilder addYear(int paramInt) {
/*  82 */     this.field[19] = this.field[19] + paramInt;
/*  83 */     this.field[35] = this.field[35] + paramInt;
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   boolean isSet(int paramInt) {
/*  88 */     if (paramInt == 1000) {
/*  89 */       paramInt = 7;
/*     */     }
/*  91 */     return (this.field[paramInt] > 0);
/*     */   }
/*     */   
/*     */   CalendarBuilder clear(int paramInt) {
/*  95 */     if (paramInt == 1000) {
/*  96 */       paramInt = 7;
/*     */     }
/*  98 */     this.field[paramInt] = 0;
/*  99 */     this.field[18 + paramInt] = 0;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   Calendar establish(Calendar paramCalendar) {
/* 104 */     boolean bool = (isSet(17) && this.field[17] > this.field[1]) ? true : false;
/*     */     
/* 106 */     if (bool && !paramCalendar.isWeekDateSupported()) {
/*     */       
/* 108 */       if (!isSet(1)) {
/* 109 */         set(1, this.field[35]);
/*     */       }
/* 111 */       bool = false;
/*     */     } 
/*     */     
/* 114 */     paramCalendar.clear();
/*     */     
/*     */     int i;
/* 117 */     for (i = 2; i < this.nextStamp; i++) {
/* 118 */       for (byte b = 0; b <= this.maxFieldIndex; b++) {
/* 119 */         if (this.field[b] == i) {
/* 120 */           paramCalendar.set(b, this.field[18 + b]);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 126 */     if (bool) {
/* 127 */       i = isSet(3) ? this.field[21] : 1;
/*     */       
/* 129 */       int j = isSet(7) ? this.field[25] : paramCalendar.getFirstDayOfWeek();
/* 130 */       if (!isValidDayOfWeek(j) && paramCalendar.isLenient()) {
/* 131 */         if (j >= 8) {
/* 132 */           j--;
/* 133 */           i += j / 7;
/* 134 */           j = j % 7 + 1;
/*     */         } else {
/* 136 */           while (j <= 0) {
/* 137 */             j += 7;
/* 138 */             i--;
/*     */           } 
/*     */         } 
/* 141 */         j = toCalendarDayOfWeek(j);
/*     */       } 
/* 143 */       paramCalendar.setWeekDate(this.field[35], i, j);
/*     */     } 
/* 145 */     return paramCalendar;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 149 */     StringBuilder stringBuilder = new StringBuilder();
/* 150 */     stringBuilder.append("CalendarBuilder:["); int i;
/* 151 */     for (i = 0; i < this.field.length; i++) {
/* 152 */       if (isSet(i)) {
/* 153 */         stringBuilder.append(i).append('=').append(this.field[18 + i]).append(',');
/*     */       }
/*     */     } 
/* 156 */     i = stringBuilder.length() - 1;
/* 157 */     if (stringBuilder.charAt(i) == ',') {
/* 158 */       stringBuilder.setLength(i);
/*     */     }
/* 160 */     stringBuilder.append(']');
/* 161 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   static int toISODayOfWeek(int paramInt) {
/* 165 */     return (paramInt == 1) ? 7 : (paramInt - 1);
/*     */   }
/*     */   
/*     */   static int toCalendarDayOfWeek(int paramInt) {
/* 169 */     if (!isValidDayOfWeek(paramInt))
/*     */     {
/* 171 */       return paramInt;
/*     */     }
/* 173 */     return (paramInt == 7) ? 1 : (paramInt + 1);
/*     */   }
/*     */   
/*     */   static boolean isValidDayOfWeek(int paramInt) {
/* 177 */     return (paramInt > 0 && paramInt <= 7);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/CalendarBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */