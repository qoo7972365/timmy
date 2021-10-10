/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ import java.util.spi.TimeZoneNameProvider;
/*     */ import sun.util.calendar.ZoneInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TimeZoneNameUtility
/*     */ {
/*  50 */   private static ConcurrentHashMap<Locale, SoftReference<String[][]>> cachedZoneData = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static final Map<String, SoftReference<Map<Locale, String[]>>> cachedDisplayNames = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[][] getZoneStrings(Locale paramLocale) {
/*  66 */     SoftReference<String[][]> softReference = cachedZoneData.get(paramLocale);
/*     */     String[][] arrayOfString;
/*  68 */     if (softReference == null || (arrayOfString = softReference.get()) == null) {
/*  69 */       arrayOfString = loadZoneStrings(paramLocale);
/*  70 */       softReference = (SoftReference)new SoftReference<>(arrayOfString);
/*  71 */       cachedZoneData.put(paramLocale, softReference);
/*     */     } 
/*     */     
/*  74 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[][] loadZoneStrings(Locale paramLocale) {
/*  80 */     LocaleProviderAdapter localeProviderAdapter = LocaleProviderAdapter.getAdapter((Class)TimeZoneNameProvider.class, paramLocale);
/*  81 */     TimeZoneNameProvider timeZoneNameProvider = localeProviderAdapter.getTimeZoneNameProvider();
/*  82 */     if (timeZoneNameProvider instanceof TimeZoneNameProviderImpl) {
/*  83 */       return ((TimeZoneNameProviderImpl)timeZoneNameProvider).getZoneStrings(paramLocale);
/*     */     }
/*     */ 
/*     */     
/*  87 */     Set<String> set = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale).getZoneIDs();
/*  88 */     LinkedList<String[]> linkedList = new LinkedList();
/*  89 */     for (String str : set) {
/*  90 */       String[] arrayOfString1 = retrieveDisplayNamesImpl(str, paramLocale);
/*  91 */       if (arrayOfString1 != null) {
/*  92 */         linkedList.add(arrayOfString1);
/*     */       }
/*     */     } 
/*     */     
/*  96 */     String[][] arrayOfString = new String[linkedList.size()][];
/*  97 */     return linkedList.<String[]>toArray(arrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] retrieveDisplayNames(String paramString, Locale paramLocale) {
/* 104 */     Objects.requireNonNull(paramString);
/* 105 */     Objects.requireNonNull(paramLocale);
/*     */     
/* 107 */     return retrieveDisplayNamesImpl(paramString, paramLocale);
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
/*     */   public static String retrieveGenericDisplayName(String paramString, int paramInt, Locale paramLocale) {
/* 119 */     String[] arrayOfString = retrieveDisplayNamesImpl(paramString, paramLocale);
/* 120 */     if (Objects.nonNull(arrayOfString)) {
/* 121 */       return arrayOfString[6 - paramInt];
/*     */     }
/* 123 */     return null;
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
/*     */   public static String retrieveDisplayName(String paramString, boolean paramBoolean, int paramInt, Locale paramLocale) {
/* 137 */     String[] arrayOfString = retrieveDisplayNamesImpl(paramString, paramLocale);
/* 138 */     if (Objects.nonNull(arrayOfString)) {
/* 139 */       return arrayOfString[(paramBoolean ? 4 : 2) - paramInt];
/*     */     }
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] retrieveDisplayNamesImpl(String paramString, Locale paramLocale) {
/* 147 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)TimeZoneNameProvider.class);
/*     */     
/* 149 */     Map<Object, Object> map = null;
/*     */     
/* 151 */     SoftReference<Map> softReference = (SoftReference)cachedDisplayNames.get(paramString);
/* 152 */     if (Objects.nonNull(softReference)) {
/* 153 */       map = softReference.get();
/* 154 */       if (Objects.nonNull(map)) {
/* 155 */         String[] arrayOfString1 = (String[])map.get(paramLocale);
/* 156 */         if (Objects.nonNull(arrayOfString1)) {
/* 157 */           return arrayOfString1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 163 */     String[] arrayOfString = new String[7];
/* 164 */     arrayOfString[0] = paramString;
/* 165 */     for (byte b = 1; b <= 6; b++) {
/* 166 */       arrayOfString[b] = localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(TimeZoneNameGetter.INSTANCE, paramLocale, (b < 5) ? ((b < 3) ? "std" : "dst") : "generic", new Object[] {
/* 167 */             Integer.valueOf(b % 2), paramString
/*     */           });
/*     */     } 
/* 170 */     if (Objects.isNull(map)) {
/* 171 */       map = new ConcurrentHashMap<>();
/*     */     }
/* 173 */     map.put(paramLocale, arrayOfString);
/* 174 */     softReference = new SoftReference<>(map);
/* 175 */     cachedDisplayNames.put(paramString, softReference);
/* 176 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TimeZoneNameGetter
/*     */     implements LocaleServiceProviderPool.LocalizedObjectGetter<TimeZoneNameProvider, String>
/*     */   {
/* 187 */     private static final TimeZoneNameGetter INSTANCE = new TimeZoneNameGetter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getObject(TimeZoneNameProvider param1TimeZoneNameProvider, Locale param1Locale, String param1String, Object... param1VarArgs) {
/* 195 */       assert param1VarArgs.length == 2;
/* 196 */       int i = ((Integer)param1VarArgs[0]).intValue();
/* 197 */       String str1 = (String)param1VarArgs[1];
/* 198 */       String str2 = getName(param1TimeZoneNameProvider, param1Locale, param1String, i, str1);
/* 199 */       if (str2 == null) {
/* 200 */         Map<String, String> map = ZoneInfo.getAliasTable();
/* 201 */         if (map != null) {
/* 202 */           String str = map.get(str1);
/* 203 */           if (str != null) {
/* 204 */             str2 = getName(param1TimeZoneNameProvider, param1Locale, param1String, i, str);
/*     */           }
/* 206 */           if (str2 == null) {
/* 207 */             str2 = examineAliases(param1TimeZoneNameProvider, param1Locale, param1String, (str != null) ? str : str1, i, map);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 213 */       return str2;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static String examineAliases(TimeZoneNameProvider param1TimeZoneNameProvider, Locale param1Locale, String param1String1, String param1String2, int param1Int, Map<String, String> param1Map) {
/* 219 */       for (Map.Entry<String, String> entry : param1Map.entrySet()) {
/* 220 */         if (((String)entry.getValue()).equals(param1String2)) {
/* 221 */           String str1 = (String)entry.getKey();
/* 222 */           String str2 = getName(param1TimeZoneNameProvider, param1Locale, param1String1, param1Int, str1);
/* 223 */           if (str2 != null) {
/* 224 */             return str2;
/*     */           }
/* 226 */           str2 = examineAliases(param1TimeZoneNameProvider, param1Locale, param1String1, str1, param1Int, param1Map);
/* 227 */           if (str2 != null) {
/* 228 */             return str2;
/*     */           }
/*     */         } 
/*     */       } 
/* 232 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     private static String getName(TimeZoneNameProvider param1TimeZoneNameProvider, Locale param1Locale, String param1String1, int param1Int, String param1String2) {
/* 237 */       String str = null;
/* 238 */       switch (param1String1) {
/*     */         case "std":
/* 240 */           str = param1TimeZoneNameProvider.getDisplayName(param1String2, false, param1Int, param1Locale);
/*     */           break;
/*     */         case "dst":
/* 243 */           str = param1TimeZoneNameProvider.getDisplayName(param1String2, true, param1Int, param1Locale);
/*     */           break;
/*     */         case "generic":
/* 246 */           str = param1TimeZoneNameProvider.getGenericDisplayName(param1String2, param1Int, param1Locale);
/*     */           break;
/*     */       } 
/* 249 */       return str;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/TimeZoneNameUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */