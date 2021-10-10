/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import sun.util.calendar.ZoneInfo;
/*     */ import sun.util.resources.LocaleData;
/*     */ import sun.util.resources.OpenListResourceBundle;
/*     */ import sun.util.resources.ParallelListResourceBundle;
/*     */ import sun.util.resources.TimeZoneNamesBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleResources
/*     */ {
/*     */   private final Locale locale;
/*     */   private final LocaleData localeData;
/*     */   private final LocaleProviderAdapter.Type type;
/*  74 */   private ConcurrentMap<String, ResourceReference> cache = new ConcurrentHashMap<>();
/*  75 */   private ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
/*     */   
/*     */   private static final String BREAK_ITERATOR_INFO = "BII.";
/*     */   
/*     */   private static final String CALENDAR_DATA = "CALD.";
/*     */   
/*     */   private static final String COLLATION_DATA_CACHEKEY = "COLD";
/*     */   
/*     */   private static final String DECIMAL_FORMAT_SYMBOLS_DATA_CACHEKEY = "DFSD";
/*     */   private static final String CURRENCY_NAMES = "CN.";
/*     */   private static final String LOCALE_NAMES = "LN.";
/*     */   private static final String TIME_ZONE_NAMES = "TZN.";
/*     */   private static final String ZONE_IDS_CACHEKEY = "ZID";
/*     */   private static final String CALENDAR_NAMES = "CALN.";
/*     */   private static final String NUMBER_PATTERNS_CACHEKEY = "NP";
/*     */   private static final String DATE_TIME_PATTERN = "DTP.";
/*  91 */   private static final Object NULLOBJECT = new Object();
/*     */   
/*     */   LocaleResources(ResourceBundleBasedAdapter paramResourceBundleBasedAdapter, Locale paramLocale) {
/*  94 */     this.locale = paramLocale;
/*  95 */     this.localeData = paramResourceBundleBasedAdapter.getLocaleData();
/*  96 */     this.type = ((LocaleProviderAdapter)paramResourceBundleBasedAdapter).getAdapterType();
/*     */   }
/*     */   
/*     */   private void removeEmptyReferences() {
/*     */     Reference<?> reference;
/* 101 */     while ((reference = this.referenceQueue.poll()) != null) {
/* 102 */       this.cache.remove(((ResourceReference)reference).getCacheKey());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Object getBreakIteratorInfo(String paramString) {
/* 108 */     String str = "BII." + paramString;
/*     */     
/* 110 */     removeEmptyReferences();
/* 111 */     ResourceReference resourceReference = this.cache.get(str); Object object;
/* 112 */     if (resourceReference == null || (object = resourceReference.get()) == null) {
/* 113 */       object = this.localeData.getBreakIteratorInfo(this.locale).getObject(paramString);
/* 114 */       this.cache.put(str, new ResourceReference(str, object, this.referenceQueue));
/*     */     } 
/*     */     
/* 117 */     return object;
/*     */   }
/*     */ 
/*     */   
/*     */   int getCalendarData(String paramString) {
/* 122 */     String str = "CALD." + paramString;
/*     */     
/* 124 */     removeEmptyReferences();
/*     */     
/* 126 */     ResourceReference resourceReference = this.cache.get(str); Integer integer;
/* 127 */     if (resourceReference == null || (integer = (Integer)resourceReference.get()) == null) {
/* 128 */       ResourceBundle resourceBundle = this.localeData.getCalendarData(this.locale);
/* 129 */       if (resourceBundle.containsKey(paramString)) {
/* 130 */         integer = Integer.valueOf(Integer.parseInt(resourceBundle.getString(paramString)));
/*     */       } else {
/* 132 */         integer = Integer.valueOf(0);
/*     */       } 
/*     */       
/* 135 */       this.cache.put(str, new ResourceReference(str, integer, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return integer.intValue();
/*     */   }
/*     */   
/*     */   public String getCollationData() {
/* 143 */     String str1 = "Rule";
/* 144 */     String str2 = "";
/*     */     
/* 146 */     removeEmptyReferences();
/* 147 */     ResourceReference resourceReference = this.cache.get("COLD");
/* 148 */     if (resourceReference == null || (str2 = (String)resourceReference.get()) == null) {
/* 149 */       ResourceBundle resourceBundle = this.localeData.getCollationData(this.locale);
/* 150 */       if (resourceBundle.containsKey(str1)) {
/* 151 */         str2 = resourceBundle.getString(str1);
/*     */       }
/* 153 */       this.cache.put("COLD", new ResourceReference("COLD", str2, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getDecimalFormatSymbolsData() {
/* 163 */     removeEmptyReferences();
/* 164 */     ResourceReference resourceReference = this.cache.get("DFSD"); Object[] arrayOfObject;
/* 165 */     if (resourceReference == null || (arrayOfObject = (Object[])resourceReference.get()) == null) {
/*     */ 
/*     */       
/* 168 */       ResourceBundle resourceBundle = this.localeData.getNumberFormatData(this.locale);
/* 169 */       arrayOfObject = new Object[3];
/*     */ 
/*     */ 
/*     */       
/* 173 */       String str = this.locale.getUnicodeLocaleType("nu");
/* 174 */       if (str != null) {
/* 175 */         String str1 = str + ".NumberElements";
/* 176 */         if (resourceBundle.containsKey(str1)) {
/* 177 */           arrayOfObject[0] = resourceBundle.getStringArray(str1);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 182 */       if (arrayOfObject[0] == null && resourceBundle.containsKey("DefaultNumberingSystem")) {
/* 183 */         String str1 = resourceBundle.getString("DefaultNumberingSystem") + ".NumberElements";
/* 184 */         if (resourceBundle.containsKey(str1)) {
/* 185 */           arrayOfObject[0] = resourceBundle.getStringArray(str1);
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 191 */       if (arrayOfObject[0] == null) {
/* 192 */         arrayOfObject[0] = resourceBundle.getStringArray("NumberElements");
/*     */       }
/*     */       
/* 195 */       this.cache.put("DFSD", new ResourceReference("DFSD", arrayOfObject, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 199 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */   public String getCurrencyName(String paramString) {
/* 203 */     Object object = null;
/* 204 */     String str = "CN." + paramString;
/*     */     
/* 206 */     removeEmptyReferences();
/* 207 */     ResourceReference resourceReference = this.cache.get(str);
/*     */     
/* 209 */     if (resourceReference != null && (object = resourceReference.get()) != null) {
/* 210 */       if (object.equals(NULLOBJECT)) {
/* 211 */         object = null;
/*     */       }
/*     */       
/* 214 */       return (String)object;
/*     */     } 
/*     */     
/* 217 */     OpenListResourceBundle openListResourceBundle = this.localeData.getCurrencyNames(this.locale);
/*     */     
/* 219 */     if (openListResourceBundle.containsKey(paramString)) {
/* 220 */       object = openListResourceBundle.getObject(paramString);
/* 221 */       this.cache.put(str, new ResourceReference(str, object, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 225 */     return (String)object;
/*     */   }
/*     */   
/*     */   public String getLocaleName(String paramString) {
/* 229 */     Object object = null;
/* 230 */     String str = "LN." + paramString;
/*     */     
/* 232 */     removeEmptyReferences();
/* 233 */     ResourceReference resourceReference = this.cache.get(str);
/*     */     
/* 235 */     if (resourceReference != null && (object = resourceReference.get()) != null) {
/* 236 */       if (object.equals(NULLOBJECT)) {
/* 237 */         object = null;
/*     */       }
/*     */       
/* 240 */       return (String)object;
/*     */     } 
/*     */     
/* 243 */     OpenListResourceBundle openListResourceBundle = this.localeData.getLocaleNames(this.locale);
/*     */     
/* 245 */     if (openListResourceBundle.containsKey(paramString)) {
/* 246 */       object = openListResourceBundle.getObject(paramString);
/* 247 */       this.cache.put(str, new ResourceReference(str, object, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 251 */     return (String)object;
/*     */   }
/*     */   
/*     */   String[] getTimeZoneNames(String paramString) {
/* 255 */     String[] arrayOfString = null;
/* 256 */     String str = "TZN.." + paramString;
/*     */     
/* 258 */     removeEmptyReferences();
/* 259 */     ResourceReference resourceReference = this.cache.get(str);
/*     */     
/* 261 */     if (Objects.isNull(resourceReference) || Objects.isNull(arrayOfString = (String[])resourceReference.get())) {
/* 262 */       TimeZoneNamesBundle timeZoneNamesBundle = this.localeData.getTimeZoneNames(this.locale);
/* 263 */       if (timeZoneNamesBundle.containsKey(paramString)) {
/* 264 */         arrayOfString = timeZoneNamesBundle.getStringArray(paramString);
/* 265 */         this.cache.put(str, new ResourceReference(str, arrayOfString, this.referenceQueue));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   Set<String> getZoneIDs() {
/* 275 */     Set<String> set = null;
/*     */     
/* 277 */     removeEmptyReferences();
/* 278 */     ResourceReference resourceReference = this.cache.get("ZID");
/* 279 */     if (resourceReference == null || (set = (Set)resourceReference.get()) == null) {
/* 280 */       TimeZoneNamesBundle timeZoneNamesBundle = this.localeData.getTimeZoneNames(this.locale);
/* 281 */       set = timeZoneNamesBundle.keySet();
/* 282 */       this.cache.put("ZID", new ResourceReference("ZID", set, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 286 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   String[][] getZoneStrings() {
/* 291 */     TimeZoneNamesBundle timeZoneNamesBundle = this.localeData.getTimeZoneNames(this.locale);
/* 292 */     Set<String> set = getZoneIDs();
/*     */     
/* 294 */     LinkedHashSet<String[]> linkedHashSet = new LinkedHashSet();
/* 295 */     for (String str : set) {
/* 296 */       linkedHashSet.add(timeZoneNamesBundle.getStringArray(str));
/*     */     }
/*     */ 
/*     */     
/* 300 */     if (this.type == LocaleProviderAdapter.Type.CLDR) {
/*     */       
/* 302 */       Map<String, String> map = ZoneInfo.getAliasTable();
/* 303 */       for (String str : map.keySet()) {
/* 304 */         if (!set.contains(str)) {
/* 305 */           String str1 = map.get(str);
/* 306 */           if (set.contains(str1)) {
/* 307 */             String[] arrayOfString = timeZoneNamesBundle.getStringArray(str1);
/* 308 */             arrayOfString[0] = str;
/* 309 */             linkedHashSet.add(arrayOfString);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 314 */     return linkedHashSet.<String[]>toArray(new String[0][]);
/*     */   }
/*     */   
/*     */   String[] getCalendarNames(String paramString) {
/* 318 */     String[] arrayOfString = null;
/* 319 */     String str = "CALN." + paramString;
/*     */     
/* 321 */     removeEmptyReferences();
/* 322 */     ResourceReference resourceReference = this.cache.get(str);
/*     */     
/* 324 */     if (resourceReference == null || (arrayOfString = (String[])resourceReference.get()) == null) {
/* 325 */       ResourceBundle resourceBundle = this.localeData.getDateFormatData(this.locale);
/* 326 */       if (resourceBundle.containsKey(paramString)) {
/* 327 */         arrayOfString = resourceBundle.getStringArray(paramString);
/* 328 */         this.cache.put(str, new ResourceReference(str, arrayOfString, this.referenceQueue));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 333 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   String[] getJavaTimeNames(String paramString) {
/* 337 */     String[] arrayOfString = null;
/* 338 */     String str = "CALN." + paramString;
/*     */     
/* 340 */     removeEmptyReferences();
/* 341 */     ResourceReference resourceReference = this.cache.get(str);
/*     */     
/* 343 */     if (resourceReference == null || (arrayOfString = (String[])resourceReference.get()) == null) {
/* 344 */       ResourceBundle resourceBundle = getJavaTimeFormatData();
/* 345 */       if (resourceBundle.containsKey(paramString)) {
/* 346 */         arrayOfString = resourceBundle.getStringArray(paramString);
/* 347 */         this.cache.put(str, new ResourceReference(str, arrayOfString, this.referenceQueue));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 352 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   public String getDateTimePattern(int paramInt1, int paramInt2, Calendar paramCalendar) {
/* 356 */     if (paramCalendar == null) {
/* 357 */       paramCalendar = Calendar.getInstance(this.locale);
/*     */     }
/* 359 */     return getDateTimePattern((String)null, paramInt1, paramInt2, paramCalendar.getCalendarType());
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
/*     */   public String getJavaTimeDateTimePattern(int paramInt1, int paramInt2, String paramString) {
/* 372 */     paramString = CalendarDataUtility.normalizeCalendarType(paramString);
/*     */     
/* 374 */     String str = getDateTimePattern("java.time.", paramInt1, paramInt2, paramString);
/* 375 */     if (str == null) {
/* 376 */       str = getDateTimePattern((String)null, paramInt1, paramInt2, paramString);
/*     */     }
/* 378 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDateTimePattern(String paramString1, int paramInt1, int paramInt2, String paramString2) {
/* 383 */     String str1, str2 = null;
/* 384 */     String str3 = null;
/*     */     
/* 386 */     if (paramInt1 >= 0) {
/* 387 */       if (paramString1 != null) {
/* 388 */         str2 = getDateTimePattern(paramString1, "TimePatterns", paramInt1, paramString2);
/*     */       }
/* 390 */       if (str2 == null) {
/* 391 */         str2 = getDateTimePattern((String)null, "TimePatterns", paramInt1, paramString2);
/*     */       }
/*     */     } 
/* 394 */     if (paramInt2 >= 0) {
/* 395 */       if (paramString1 != null) {
/* 396 */         str3 = getDateTimePattern(paramString1, "DatePatterns", paramInt2, paramString2);
/*     */       }
/* 398 */       if (str3 == null) {
/* 399 */         str3 = getDateTimePattern((String)null, "DatePatterns", paramInt2, paramString2);
/*     */       }
/*     */     } 
/* 402 */     if (paramInt1 >= 0)
/* 403 */     { if (paramInt2 >= 0)
/* 404 */       { String str = null;
/* 405 */         if (paramString1 != null) {
/* 406 */           str = getDateTimePattern(paramString1, "DateTimePatterns", 0, paramString2);
/*     */         }
/* 408 */         if (str == null) {
/* 409 */           str = getDateTimePattern((String)null, "DateTimePatterns", 0, paramString2);
/*     */         }
/* 411 */         switch (str)
/*     */         { case "{1} {0}":
/* 413 */             str1 = str3 + " " + str2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 430 */             return str1;case "{0} {1}": str1 = str2 + " " + str3; return str1; }  str1 = MessageFormat.format(str, new Object[] { str2, str3 }); } else { str1 = str2; }  } else if (paramInt2 >= 0) { str1 = str3; } else { throw new IllegalArgumentException("No date or time style specified"); }  return str1;
/*     */   }
/*     */   
/*     */   public String[] getNumberPatterns() {
/* 434 */     String[] arrayOfString = null;
/*     */     
/* 436 */     removeEmptyReferences();
/* 437 */     ResourceReference resourceReference = this.cache.get("NP");
/*     */     
/* 439 */     if (resourceReference == null || (arrayOfString = (String[])resourceReference.get()) == null) {
/* 440 */       ResourceBundle resourceBundle = this.localeData.getNumberFormatData(this.locale);
/* 441 */       arrayOfString = resourceBundle.getStringArray("NumberPatterns");
/* 442 */       this.cache.put("NP", new ResourceReference("NP", arrayOfString, this.referenceQueue));
/*     */     } 
/*     */ 
/*     */     
/* 446 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceBundle getJavaTimeFormatData() {
/* 455 */     ResourceBundle resourceBundle = this.localeData.getDateFormatData(this.locale);
/* 456 */     if (resourceBundle instanceof ParallelListResourceBundle) {
/* 457 */       this.localeData.setSupplementary((ParallelListResourceBundle)resourceBundle);
/*     */     }
/* 459 */     return resourceBundle;
/*     */   }
/*     */   
/*     */   private String getDateTimePattern(String paramString1, String paramString2, int paramInt, String paramString3) {
/* 463 */     StringBuilder stringBuilder = new StringBuilder();
/* 464 */     if (paramString1 != null) {
/* 465 */       stringBuilder.append(paramString1);
/*     */     }
/* 467 */     if (!"gregory".equals(paramString3)) {
/* 468 */       stringBuilder.append(paramString3).append('.');
/*     */     }
/* 470 */     stringBuilder.append(paramString2);
/* 471 */     String str1 = stringBuilder.toString();
/* 472 */     String str2 = stringBuilder.insert(0, "DTP.").toString();
/*     */     
/* 474 */     removeEmptyReferences();
/* 475 */     ResourceReference resourceReference = this.cache.get(str2);
/* 476 */     Object object = NULLOBJECT;
/*     */     
/* 478 */     if (resourceReference == null || (object = resourceReference.get()) == null) {
/* 479 */       ResourceBundle resourceBundle = (paramString1 != null) ? getJavaTimeFormatData() : this.localeData.getDateFormatData(this.locale);
/* 480 */       if (resourceBundle.containsKey(str1)) {
/* 481 */         object = resourceBundle.getStringArray(str1);
/*     */       } else {
/* 483 */         assert !str1.equals(paramString2);
/* 484 */         if (resourceBundle.containsKey(paramString2)) {
/* 485 */           object = resourceBundle.getStringArray(paramString2);
/*     */         }
/*     */       } 
/* 488 */       this.cache.put(str2, new ResourceReference(str2, object, this.referenceQueue));
/*     */     } 
/*     */     
/* 491 */     if (object == NULLOBJECT) {
/* 492 */       assert paramString1 != null;
/* 493 */       return null;
/*     */     } 
/* 495 */     return ((String[])object)[paramInt];
/*     */   }
/*     */   
/*     */   private static class ResourceReference extends SoftReference<Object> {
/*     */     private final String cacheKey;
/*     */     
/*     */     ResourceReference(String param1String, Object param1Object, ReferenceQueue<Object> param1ReferenceQueue) {
/* 502 */       super(param1Object, param1ReferenceQueue);
/* 503 */       this.cacheKey = param1String;
/*     */     }
/*     */     
/*     */     String getCacheKey() {
/* 507 */       return this.cacheKey;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/LocaleResources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */