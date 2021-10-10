/*     */ package java.time.format;
/*     */ 
/*     */ import java.time.chrono.Chronology;
/*     */ import java.time.chrono.IsoChronology;
/*     */ import java.time.chrono.JapaneseChronology;
/*     */ import java.time.temporal.ChronoField;
/*     */ import java.time.temporal.IsoFields;
/*     */ import java.time.temporal.TemporalField;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.util.locale.provider.CalendarDataUtility;
/*     */ import sun.util.locale.provider.LocaleProviderAdapter;
/*     */ import sun.util.locale.provider.LocaleResources;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DateTimeTextProvider
/*     */ {
/* 106 */   private static final ConcurrentMap<Map.Entry<TemporalField, Locale>, Object> CACHE = new ConcurrentHashMap<>(16, 0.75F, 2);
/*     */   
/* 108 */   private static final Comparator<Map.Entry<String, Long>> COMPARATOR = new Comparator<Map.Entry<String, Long>>()
/*     */     {
/*     */       public int compare(Map.Entry<String, Long> param1Entry1, Map.Entry<String, Long> param1Entry2) {
/* 111 */         return ((String)param1Entry2.getKey()).length() - ((String)param1Entry1.getKey()).length();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DateTimeTextProvider getInstance() {
/* 123 */     return new DateTimeTextProvider();
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
/*     */   public String getText(TemporalField paramTemporalField, long paramLong, TextStyle paramTextStyle, Locale paramLocale) {
/* 141 */     Object object = findStore(paramTemporalField, paramLocale);
/* 142 */     if (object instanceof LocaleStore) {
/* 143 */       return ((LocaleStore)object).getText(paramLong, paramTextStyle);
/*     */     }
/* 145 */     return null;
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
/*     */   public String getText(Chronology paramChronology, TemporalField paramTemporalField, long paramLong, TextStyle paramTextStyle, Locale paramLocale) {
/*     */     byte b;
/*     */     int i;
/* 165 */     if (paramChronology == IsoChronology.INSTANCE || !(paramTemporalField instanceof ChronoField))
/*     */     {
/* 167 */       return getText(paramTemporalField, paramLong, paramTextStyle, paramLocale);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 172 */     if (paramTemporalField == ChronoField.ERA) {
/* 173 */       b = 0;
/* 174 */       if (paramChronology == JapaneseChronology.INSTANCE) {
/* 175 */         if (paramLong == -999L) {
/* 176 */           i = 0;
/*     */         } else {
/* 178 */           i = (int)paramLong + 2;
/*     */         } 
/*     */       } else {
/* 181 */         i = (int)paramLong;
/*     */       } 
/* 183 */     } else if (paramTemporalField == ChronoField.MONTH_OF_YEAR) {
/* 184 */       b = 2;
/* 185 */       i = (int)paramLong - 1;
/* 186 */     } else if (paramTemporalField == ChronoField.DAY_OF_WEEK) {
/* 187 */       b = 7;
/* 188 */       i = (int)paramLong + 1;
/* 189 */       if (i > 7) {
/* 190 */         i = 1;
/*     */       }
/* 192 */     } else if (paramTemporalField == ChronoField.AMPM_OF_DAY) {
/* 193 */       b = 9;
/* 194 */       i = (int)paramLong;
/*     */     } else {
/* 196 */       return null;
/*     */     } 
/* 198 */     return CalendarDataUtility.retrieveJavaTimeFieldValueName(paramChronology
/* 199 */         .getCalendarType(), b, i, paramTextStyle.toCalendarStyle(), paramLocale);
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
/*     */   public Iterator<Map.Entry<String, Long>> getTextIterator(TemporalField paramTemporalField, TextStyle paramTextStyle, Locale paramLocale) {
/* 219 */     Object object = findStore(paramTemporalField, paramLocale);
/* 220 */     if (object instanceof LocaleStore) {
/* 221 */       return ((LocaleStore)object).getTextIterator(paramTextStyle);
/*     */     }
/* 223 */     return null;
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
/*     */   public Iterator<Map.Entry<String, Long>> getTextIterator(Chronology paramChronology, TemporalField paramTemporalField, TextStyle paramTextStyle, Locale paramLocale) {
/*     */     byte b;
/* 245 */     if (paramChronology == IsoChronology.INSTANCE || !(paramTemporalField instanceof ChronoField))
/*     */     {
/* 247 */       return getTextIterator(paramTemporalField, paramTextStyle, paramLocale);
/*     */     }
/*     */ 
/*     */     
/* 251 */     switch ((ChronoField)paramTemporalField) {
/*     */       case ERA:
/* 253 */         b = 0;
/*     */         break;
/*     */       case MONTH_OF_YEAR:
/* 256 */         b = 2;
/*     */         break;
/*     */       case DAY_OF_WEEK:
/* 259 */         b = 7;
/*     */         break;
/*     */       case AMPM_OF_DAY:
/* 262 */         b = 9;
/*     */         break;
/*     */       default:
/* 265 */         return null;
/*     */     } 
/*     */     
/* 268 */     boolean bool = (paramTextStyle == null) ? false : paramTextStyle.toCalendarStyle();
/* 269 */     Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames(paramChronology
/* 270 */         .getCalendarType(), b, bool, paramLocale);
/* 271 */     if (map == null) {
/* 272 */       return null;
/*     */     }
/* 274 */     ArrayList<Map.Entry<String, Long>> arrayList = new ArrayList(map.size());
/* 275 */     switch (b)
/*     */     { case 0:
/* 277 */         for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 278 */           int i = ((Integer)entry.getValue()).intValue();
/* 279 */           if (paramChronology == JapaneseChronology.INSTANCE) {
/* 280 */             if (i == 0) {
/* 281 */               i = -999;
/*     */             } else {
/* 283 */               i -= 2;
/*     */             } 
/*     */           }
/* 286 */           arrayList.add(createEntry(entry.getKey(), Long.valueOf(i)));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 305 */         return arrayList.iterator();case 2: for (Map.Entry<String, Integer> entry : map.entrySet()) arrayList.add(createEntry((String)entry.getKey(), Long.valueOf((((Integer)entry.getValue()).intValue() + 1))));  return arrayList.iterator();case 7: for (Map.Entry<String, Integer> entry : map.entrySet()) arrayList.add(createEntry((String)entry.getKey(), Long.valueOf(toWeekDay(((Integer)entry.getValue()).intValue()))));  return arrayList.iterator(); }  for (Map.Entry<String, Integer> entry : map.entrySet()) arrayList.add(createEntry((String)entry.getKey(), Long.valueOf(((Integer)entry.getValue()).intValue())));  return arrayList.iterator();
/*     */   }
/*     */   
/*     */   private Object findStore(TemporalField paramTemporalField, Locale paramLocale) {
/* 309 */     Map.Entry<TemporalField, Locale> entry = createEntry(paramTemporalField, paramLocale);
/* 310 */     Object object = CACHE.get(entry);
/* 311 */     if (object == null) {
/* 312 */       object = createStore(paramTemporalField, paramLocale);
/* 313 */       CACHE.putIfAbsent(entry, object);
/* 314 */       object = CACHE.get(entry);
/*     */     } 
/* 316 */     return object;
/*     */   }
/*     */   
/*     */   private static int toWeekDay(int paramInt) {
/* 320 */     if (paramInt == 1) {
/* 321 */       return 7;
/*     */     }
/* 323 */     return paramInt - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object createStore(TemporalField paramTemporalField, Locale paramLocale) {
/* 328 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 329 */     if (paramTemporalField == ChronoField.ERA) {
/* 330 */       for (TextStyle textStyle : TextStyle.values()) {
/* 331 */         if (!textStyle.isStandalone()) {
/*     */ 
/*     */ 
/*     */           
/* 335 */           Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 0, textStyle
/* 336 */               .toCalendarStyle(), paramLocale);
/* 337 */           if (map != null) {
/* 338 */             HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 339 */             for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 340 */               hashMap1.put(Long.valueOf(((Integer)entry.getValue()).intValue()), entry.getKey());
/*     */             }
/* 342 */             if (!hashMap1.isEmpty())
/* 343 */               hashMap.put(textStyle, hashMap1); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 347 */       return new LocaleStore((Map)hashMap);
/*     */     } 
/*     */     
/* 350 */     if (paramTemporalField == ChronoField.MONTH_OF_YEAR) {
/* 351 */       for (TextStyle textStyle : TextStyle.values()) {
/* 352 */         Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 2, textStyle
/* 353 */             .toCalendarStyle(), paramLocale);
/* 354 */         HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 355 */         if (map != null) {
/* 356 */           for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 357 */             hashMap1.put(Long.valueOf((((Integer)entry.getValue()).intValue() + 1)), entry.getKey());
/*     */           
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 363 */           for (byte b = 0; b <= 11; b++) {
/*     */             
/* 365 */             String str = CalendarDataUtility.retrieveJavaTimeFieldValueName("gregory", 2, b, textStyle
/* 366 */                 .toCalendarStyle(), paramLocale);
/* 367 */             if (str == null) {
/*     */               break;
/*     */             }
/* 370 */             hashMap1.put(Long.valueOf((b + 1)), str);
/*     */           } 
/*     */         } 
/* 373 */         if (!hashMap1.isEmpty()) {
/* 374 */           hashMap.put(textStyle, hashMap1);
/*     */         }
/*     */       } 
/* 377 */       return new LocaleStore((Map)hashMap);
/*     */     } 
/*     */     
/* 380 */     if (paramTemporalField == ChronoField.DAY_OF_WEEK) {
/* 381 */       for (TextStyle textStyle : TextStyle.values()) {
/* 382 */         Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 7, textStyle
/* 383 */             .toCalendarStyle(), paramLocale);
/* 384 */         HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 385 */         if (map != null) {
/* 386 */           for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 387 */             hashMap1.put(Long.valueOf(toWeekDay(((Integer)entry.getValue()).intValue())), entry.getKey());
/*     */           
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 393 */           for (byte b = 1; b <= 7; b++) {
/*     */             
/* 395 */             String str = CalendarDataUtility.retrieveJavaTimeFieldValueName("gregory", 7, b, textStyle
/* 396 */                 .toCalendarStyle(), paramLocale);
/* 397 */             if (str == null) {
/*     */               break;
/*     */             }
/* 400 */             hashMap1.put(Long.valueOf(toWeekDay(b)), str);
/*     */           } 
/*     */         } 
/* 403 */         if (!hashMap1.isEmpty()) {
/* 404 */           hashMap.put(textStyle, hashMap1);
/*     */         }
/*     */       } 
/* 407 */       return new LocaleStore((Map)hashMap);
/*     */     } 
/*     */     
/* 410 */     if (paramTemporalField == ChronoField.AMPM_OF_DAY) {
/* 411 */       for (TextStyle textStyle : TextStyle.values()) {
/* 412 */         if (!textStyle.isStandalone()) {
/*     */ 
/*     */ 
/*     */           
/* 416 */           Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames("gregory", 9, textStyle
/* 417 */               .toCalendarStyle(), paramLocale);
/* 418 */           if (map != null) {
/* 419 */             HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 420 */             for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 421 */               hashMap1.put(Long.valueOf(((Integer)entry.getValue()).intValue()), entry.getKey());
/*     */             }
/* 423 */             if (!hashMap1.isEmpty())
/* 424 */               hashMap.put(textStyle, hashMap1); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 428 */       return new LocaleStore((Map)hashMap);
/*     */     } 
/*     */     
/* 431 */     if (paramTemporalField == IsoFields.QUARTER_OF_YEAR) {
/*     */       
/* 433 */       String[] arrayOfString = { "QuarterNames", "standalone.QuarterNames", "QuarterAbbreviations", "standalone.QuarterAbbreviations", "QuarterNarrows", "standalone.QuarterNarrows" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 441 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 442 */         String[] arrayOfString1 = getLocalizedResource(arrayOfString[b], paramLocale);
/* 443 */         if (arrayOfString1 != null) {
/* 444 */           HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 445 */           for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
/* 446 */             hashMap1.put(Long.valueOf((b1 + 1)), arrayOfString1[b1]);
/*     */           }
/* 448 */           hashMap.put(TextStyle.values()[b], hashMap1);
/*     */         } 
/*     */       } 
/* 451 */       return new LocaleStore((Map)hashMap);
/*     */     } 
/*     */     
/* 454 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <A, B> Map.Entry<A, B> createEntry(A paramA, B paramB) {
/* 465 */     return new AbstractMap.SimpleImmutableEntry<>(paramA, paramB);
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
/*     */   static <T> T getLocalizedResource(String paramString, Locale paramLocale) {
/* 480 */     LocaleResources localeResources = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(paramLocale);
/* 481 */     ResourceBundle resourceBundle = localeResources.getJavaTimeFormatData();
/* 482 */     return resourceBundle.containsKey(paramString) ? (T)resourceBundle.getObject(paramString) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class LocaleStore
/*     */   {
/*     */     private final Map<TextStyle, Map<Long, String>> valueTextMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Map<TextStyle, List<Map.Entry<String, Long>>> parsable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LocaleStore(Map<TextStyle, Map<Long, String>> param1Map) {
/* 510 */       this.valueTextMap = param1Map;
/* 511 */       HashMap<Object, Object> hashMap = new HashMap<>();
/* 512 */       ArrayList<?> arrayList = new ArrayList();
/* 513 */       for (Map.Entry<TextStyle, Map<Long, String>> entry : param1Map.entrySet()) {
/* 514 */         HashMap<Object, Object> hashMap1 = new HashMap<>();
/* 515 */         for (Map.Entry entry1 : ((Map)entry.getValue()).entrySet()) {
/* 516 */           if (hashMap1.put(entry1.getValue(), DateTimeTextProvider.createEntry((A)entry1.getValue(), (B)entry1.getKey())) != null);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 521 */         ArrayList<?> arrayList1 = new ArrayList(hashMap1.values());
/* 522 */         Collections.sort(arrayList1, DateTimeTextProvider.COMPARATOR);
/* 523 */         hashMap.put(entry.getKey(), arrayList1);
/* 524 */         arrayList.addAll(arrayList1);
/* 525 */         hashMap.put(null, arrayList);
/*     */       } 
/* 527 */       Collections.sort(arrayList, DateTimeTextProvider.COMPARATOR);
/* 528 */       this.parsable = (Map)hashMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getText(long param1Long, TextStyle param1TextStyle) {
/* 540 */       Map map = this.valueTextMap.get(param1TextStyle);
/* 541 */       return (map != null) ? (String)map.get(Long.valueOf(param1Long)) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Iterator<Map.Entry<String, Long>> getTextIterator(TextStyle param1TextStyle) {
/* 554 */       List<Map.Entry<String, Long>> list = this.parsable.get(param1TextStyle);
/* 555 */       return (list != null) ? list.iterator() : null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/time/format/DateTimeTextProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */