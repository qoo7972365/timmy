/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.spi.CalendarDataProvider;
/*     */ import java.util.spi.CalendarNameProvider;
/*     */ import java.util.spi.LocaleServiceProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CalendarDataUtility
/*     */ {
/*     */   public static final String FIRST_DAY_OF_WEEK = "firstDayOfWeek";
/*     */   public static final String MINIMAL_DAYS_IN_FIRST_WEEK = "minimalDaysInFirstWeek";
/*     */   
/*     */   public static int retrieveFirstDayOfWeek(Locale paramLocale) {
/*  51 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CalendarDataProvider.class);
/*  52 */     Integer integer = localeServiceProviderPool.<LocaleServiceProvider, Integer>getLocalizedObject(CalendarWeekParameterGetter.INSTANCE, paramLocale, "firstDayOfWeek", new Object[0]);
/*     */     
/*  54 */     return (integer != null && integer.intValue() >= 1 && integer.intValue() <= 7) ? integer.intValue() : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int retrieveMinimalDaysInFirstWeek(Locale paramLocale) {
/*  59 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CalendarDataProvider.class);
/*  60 */     Integer integer = localeServiceProviderPool.<LocaleServiceProvider, Integer>getLocalizedObject(CalendarWeekParameterGetter.INSTANCE, paramLocale, "minimalDaysInFirstWeek", new Object[0]);
/*     */     
/*  62 */     return (integer != null && integer.intValue() >= 1 && integer.intValue() <= 7) ? integer.intValue() : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String retrieveFieldValueName(String paramString, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale) {
/*  67 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CalendarNameProvider.class);
/*  68 */     return localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(CalendarFieldValueNameGetter.INSTANCE, paramLocale, normalizeCalendarType(paramString), new Object[] {
/*  69 */           Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Boolean.valueOf(false)
/*     */         });
/*     */   }
/*     */   
/*     */   public static String retrieveJavaTimeFieldValueName(String paramString, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale) {
/*  74 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CalendarNameProvider.class);
/*     */     
/*  76 */     String str = localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(CalendarFieldValueNameGetter.INSTANCE, paramLocale, normalizeCalendarType(paramString), new Object[] {
/*  77 */           Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Boolean.valueOf(true) });
/*  78 */     if (str == null)
/*  79 */       str = localeServiceProviderPool.<LocaleServiceProvider, String>getLocalizedObject(CalendarFieldValueNameGetter.INSTANCE, paramLocale, normalizeCalendarType(paramString), new Object[] {
/*  80 */             Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Boolean.valueOf(false)
/*     */           }); 
/*  82 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, Integer> retrieveFieldValueNames(String paramString, int paramInt1, int paramInt2, Locale paramLocale) {
/*  87 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CalendarNameProvider.class);
/*  88 */     return localeServiceProviderPool.<LocaleServiceProvider, Map<String, Integer>>getLocalizedObject(CalendarFieldValueNamesMapGetter.INSTANCE, paramLocale, 
/*  89 */         normalizeCalendarType(paramString), new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Boolean.valueOf(false) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<String, Integer> retrieveJavaTimeFieldValueNames(String paramString, int paramInt1, int paramInt2, Locale paramLocale) {
/*  94 */     LocaleServiceProviderPool localeServiceProviderPool = LocaleServiceProviderPool.getPool((Class)CalendarNameProvider.class);
/*     */     
/*  96 */     Map<String, Integer> map = localeServiceProviderPool.<LocaleServiceProvider, Map>getLocalizedObject(CalendarFieldValueNamesMapGetter.INSTANCE, paramLocale, 
/*  97 */         normalizeCalendarType(paramString), new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Boolean.valueOf(true) });
/*  98 */     if (map == null) {
/*  99 */       map = localeServiceProviderPool.<LocaleServiceProvider, Map>getLocalizedObject(CalendarFieldValueNamesMapGetter.INSTANCE, paramLocale, 
/* 100 */           normalizeCalendarType(paramString), new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Boolean.valueOf(false) });
/*     */     }
/* 102 */     return map;
/*     */   }
/*     */   
/*     */   static String normalizeCalendarType(String paramString) {
/*     */     String str;
/* 107 */     if (paramString.equals("gregorian") || paramString.equals("iso8601")) {
/* 108 */       str = "gregory";
/* 109 */     } else if (paramString.startsWith("islamic")) {
/* 110 */       str = "islamic";
/*     */     } else {
/* 112 */       str = paramString;
/*     */     } 
/* 114 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CalendarFieldValueNameGetter
/*     */     implements LocaleServiceProviderPool.LocalizedObjectGetter<CalendarNameProvider, String>
/*     */   {
/* 124 */     private static final CalendarFieldValueNameGetter INSTANCE = new CalendarFieldValueNameGetter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getObject(CalendarNameProvider param1CalendarNameProvider, Locale param1Locale, String param1String, Object... param1VarArgs) {
/* 132 */       assert param1VarArgs.length == 4;
/* 133 */       int i = ((Integer)param1VarArgs[0]).intValue();
/* 134 */       int j = ((Integer)param1VarArgs[1]).intValue();
/* 135 */       int k = ((Integer)param1VarArgs[2]).intValue();
/* 136 */       boolean bool = ((Boolean)param1VarArgs[3]).booleanValue();
/*     */ 
/*     */ 
/*     */       
/* 140 */       if (bool && param1CalendarNameProvider instanceof CalendarNameProviderImpl)
/*     */       {
/*     */         
/* 143 */         return ((CalendarNameProviderImpl)param1CalendarNameProvider).getJavaTimeDisplayName(param1String, i, j, k, param1Locale);
/*     */       }
/*     */       
/* 146 */       return param1CalendarNameProvider.getDisplayName(param1String, i, j, k, param1Locale);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CalendarFieldValueNamesMapGetter
/*     */     implements LocaleServiceProviderPool.LocalizedObjectGetter<CalendarNameProvider, Map<String, Integer>>
/*     */   {
/* 157 */     private static final CalendarFieldValueNamesMapGetter INSTANCE = new CalendarFieldValueNamesMapGetter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<String, Integer> getObject(CalendarNameProvider param1CalendarNameProvider, Locale param1Locale, String param1String, Object... param1VarArgs) {
/* 165 */       assert param1VarArgs.length == 3;
/* 166 */       int i = ((Integer)param1VarArgs[0]).intValue();
/* 167 */       int j = ((Integer)param1VarArgs[1]).intValue();
/* 168 */       boolean bool = ((Boolean)param1VarArgs[2]).booleanValue();
/*     */ 
/*     */ 
/*     */       
/* 172 */       if (bool && param1CalendarNameProvider instanceof CalendarNameProviderImpl)
/*     */       {
/*     */         
/* 175 */         return ((CalendarNameProviderImpl)param1CalendarNameProvider).getJavaTimeDisplayNames(param1String, i, j, param1Locale);
/*     */       }
/*     */       
/* 178 */       return param1CalendarNameProvider.getDisplayNames(param1String, i, j, param1Locale);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CalendarWeekParameterGetter
/*     */     implements LocaleServiceProviderPool.LocalizedObjectGetter<CalendarDataProvider, Integer>
/*     */   {
/* 185 */     private static final CalendarWeekParameterGetter INSTANCE = new CalendarWeekParameterGetter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Integer getObject(CalendarDataProvider param1CalendarDataProvider, Locale param1Locale, String param1String, Object... param1VarArgs) {
/*     */       int i;
/* 193 */       assert param1VarArgs.length == 0;
/*     */       
/* 195 */       switch (param1String) {
/*     */         case "firstDayOfWeek":
/* 197 */           i = param1CalendarDataProvider.getFirstDayOfWeek(param1Locale);
/*     */           break;
/*     */         case "minimalDaysInFirstWeek":
/* 200 */           i = param1CalendarDataProvider.getMinimalDaysInFirstWeek(param1Locale);
/*     */           break;
/*     */         default:
/* 203 */           throw new InternalError("invalid requestID: " + param1String);
/*     */       } 
/* 205 */       return (i != 0) ? Integer.valueOf(i) : null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/CalendarDataUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */