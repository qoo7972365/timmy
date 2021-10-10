/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.spi.CalendarNameProvider;
/*     */ import sun.util.calendar.CalendarSystem;
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
/*     */ public class CalendarNameProviderImpl
/*     */   extends CalendarNameProvider
/*     */   implements AvailableLanguageTags
/*     */ {
/*     */   private final LocaleProviderAdapter.Type type;
/*     */   private final Set<String> langtags;
/*     */   
/*     */   public CalendarNameProviderImpl(LocaleProviderAdapter.Type paramType, Set<String> paramSet) {
/*  49 */     this.type = paramType;
/*  50 */     this.langtags = paramSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayName(String paramString, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale) {
/*  55 */     return getDisplayNameImpl(paramString, paramInt1, paramInt2, paramInt3, paramLocale, false);
/*     */   }
/*     */   
/*     */   public String getJavaTimeDisplayName(String paramString, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale) {
/*  59 */     return getDisplayNameImpl(paramString, paramInt1, paramInt2, paramInt3, paramLocale, true);
/*     */   }
/*     */   
/*     */   public String getDisplayNameImpl(String paramString, int paramInt1, int paramInt2, int paramInt3, Locale paramLocale, boolean paramBoolean) {
/*  63 */     String str1 = null;
/*  64 */     String str2 = getResourceKey(paramString, paramInt1, paramInt3, paramBoolean);
/*  65 */     if (str2 != null) {
/*  66 */       LocaleResources localeResources = LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale);
/*  67 */       String[] arrayOfString = paramBoolean ? localeResources.getJavaTimeNames(str2) : localeResources.getCalendarNames(str2);
/*  68 */       if (arrayOfString != null && arrayOfString.length > 0) {
/*  69 */         if (paramInt1 == 7 || paramInt1 == 1) {
/*  70 */           paramInt2--;
/*     */         }
/*  72 */         if (paramInt2 < 0)
/*  73 */           return null; 
/*  74 */         if (paramInt2 >= arrayOfString.length) {
/*  75 */           if (paramInt1 == 0 && "japanese".equals(paramString)) {
/*  76 */             Era[] arrayOfEra = CalendarSystem.forName("japanese").getEras();
/*  77 */             if (paramInt2 <= arrayOfEra.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  83 */               if (this.type == LocaleProviderAdapter.Type.CLDR) {
/*  84 */                 localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(paramLocale);
/*  85 */                 str2 = getResourceKeyFor(LocaleProviderAdapter.Type.JRE, paramString, paramInt1, paramInt3, paramBoolean);
/*     */ 
/*     */                 
/*  88 */                 arrayOfString = paramBoolean ? localeResources.getJavaTimeNames(str2) : localeResources.getCalendarNames(str2);
/*     */               } 
/*  90 */               if (arrayOfString == null || paramInt2 >= arrayOfString.length) {
/*     */                 
/*  92 */                 Era era = arrayOfEra[paramInt2 - 1];
/*  93 */                 if (paramBoolean) {
/*  94 */                   return (getBaseStyle(paramInt3) == 4) ? era
/*  95 */                     .getAbbreviation() : era
/*  96 */                     .getName();
/*     */                 }
/*  98 */                 return ((paramInt3 & 0x2) != 0) ? era
/*  99 */                   .getName() : era
/* 100 */                   .getAbbreviation();
/*     */               } 
/*     */             } else {
/*     */               
/* 104 */               return null;
/*     */             } 
/*     */           } else {
/* 107 */             return null;
/*     */           } 
/*     */         }
/* 110 */         str1 = arrayOfString[paramInt2];
/*     */         
/* 112 */         if (str1.length() == 0 && (paramInt3 == 32769 || paramInt3 == 32770 || paramInt3 == 32772))
/*     */         {
/*     */           
/* 115 */           str1 = getDisplayName(paramString, paramInt1, paramInt2, 
/* 116 */               getBaseStyle(paramInt3), paramLocale);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return str1;
/*     */   }
/*     */   
/* 124 */   private static int[] REST_OF_STYLES = new int[] { 32769, 2, 32770, 4, 32772 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Integer> getDisplayNames(String paramString, int paramInt1, int paramInt2, Locale paramLocale) {
/*     */     Map<String, Integer> map;
/* 132 */     if (paramInt2 == 0) {
/* 133 */       map = getDisplayNamesImpl(paramString, paramInt1, 1, paramLocale, false);
/* 134 */       for (int i : REST_OF_STYLES) {
/* 135 */         map.putAll(getDisplayNamesImpl(paramString, paramInt1, i, paramLocale, false));
/*     */       }
/*     */     } else {
/*     */       
/* 139 */       map = getDisplayNamesImpl(paramString, paramInt1, paramInt2, paramLocale, false);
/*     */     } 
/* 141 */     return map.isEmpty() ? null : map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Integer> getJavaTimeDisplayNames(String paramString, int paramInt1, int paramInt2, Locale paramLocale) {
/* 147 */     Map<String, Integer> map = getDisplayNamesImpl(paramString, paramInt1, paramInt2, paramLocale, true);
/* 148 */     return map.isEmpty() ? null : map;
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, Integer> getDisplayNamesImpl(String paramString, int paramInt1, int paramInt2, Locale paramLocale, boolean paramBoolean) {
/* 153 */     String str = getResourceKey(paramString, paramInt1, paramInt2, paramBoolean);
/* 154 */     TreeMap<Object, Object> treeMap = new TreeMap<>(LengthBasedComparator.INSTANCE);
/* 155 */     if (str != null) {
/* 156 */       LocaleResources localeResources = LocaleProviderAdapter.forType(this.type).getLocaleResources(paramLocale);
/* 157 */       String[] arrayOfString = paramBoolean ? localeResources.getJavaTimeNames(str) : localeResources.getCalendarNames(str);
/* 158 */       if (arrayOfString != null && 
/* 159 */         !hasDuplicates(arrayOfString)) {
/* 160 */         if (paramInt1 == 1) {
/* 161 */           if (arrayOfString.length > 0) {
/* 162 */             treeMap.put(arrayOfString[0], Integer.valueOf(1));
/*     */           }
/*     */         } else {
/* 165 */           byte b1 = (paramInt1 == 7) ? 1 : 0;
/* 166 */           for (byte b2 = 0; b2 < arrayOfString.length; b2++) {
/* 167 */             String str1 = arrayOfString[b2];
/*     */ 
/*     */             
/* 170 */             if (str1.length() != 0)
/*     */             {
/*     */               
/* 173 */               treeMap.put(str1, Integer.valueOf(b1 + b2));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 179 */     return (Map)treeMap;
/*     */   }
/*     */   
/*     */   private static int getBaseStyle(int paramInt) {
/* 183 */     return paramInt & 0xFFFF7FFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LengthBasedComparator
/*     */     implements Comparator<String>
/*     */   {
/* 191 */     private static final LengthBasedComparator INSTANCE = new LengthBasedComparator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(String param1String1, String param1String2) {
/* 198 */       int i = param1String2.length() - param1String1.length();
/* 199 */       return (i == 0) ? param1String1.compareTo(param1String2) : i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Locale[] getAvailableLocales() {
/* 205 */     return LocaleProviderAdapter.toLocaleArray(this.langtags);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupportedLocale(Locale paramLocale) {
/* 210 */     if (Locale.ROOT.equals(paramLocale)) {
/* 211 */       return true;
/*     */     }
/* 213 */     String str = null;
/* 214 */     if (paramLocale.hasExtensions()) {
/* 215 */       str = paramLocale.getUnicodeLocaleType("ca");
/* 216 */       paramLocale = paramLocale.stripExtensions();
/*     */     } 
/*     */     
/* 219 */     if (str != null) {
/* 220 */       switch (str) {
/*     */         case "buddhist":
/*     */         case "japanese":
/*     */         case "gregory":
/*     */         case "islamic":
/*     */         case "roc":
/*     */           break;
/*     */         
/*     */         default:
/* 229 */           return false;
/*     */       } 
/*     */     }
/* 232 */     if (this.langtags.contains(paramLocale.toLanguageTag())) {
/* 233 */       return true;
/*     */     }
/* 235 */     if (this.type == LocaleProviderAdapter.Type.JRE) {
/* 236 */       String str1 = paramLocale.toString().replace('_', '-');
/* 237 */       return this.langtags.contains(str1);
/*     */     } 
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAvailableLanguageTags() {
/* 244 */     return this.langtags;
/*     */   }
/*     */   
/*     */   private boolean hasDuplicates(String[] paramArrayOfString) {
/* 248 */     int i = paramArrayOfString.length;
/* 249 */     for (byte b = 0; b < i - 1; b++) {
/* 250 */       String str = paramArrayOfString[b];
/* 251 */       if (str != null) {
/* 252 */         for (int j = b + 1; j < i; j++) {
/* 253 */           if (str.equals(paramArrayOfString[j])) {
/* 254 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 259 */     return false;
/*     */   }
/*     */   
/*     */   private String getResourceKey(String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 263 */     return getResourceKeyFor(this.type, paramString, paramInt1, paramInt2, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getResourceKeyFor(LocaleProviderAdapter.Type paramType, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 268 */     int i = getBaseStyle(paramInt2);
/* 269 */     boolean bool1 = (paramInt2 != i) ? true : false;
/*     */     
/* 271 */     if ("gregory".equals(paramString)) {
/* 272 */       paramString = null;
/*     */     }
/* 274 */     boolean bool2 = (i == 4) ? true : false;
/* 275 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 277 */     if (paramBoolean) {
/* 278 */       stringBuilder.append("java.time.");
/*     */     }
/* 280 */     switch (paramInt1) {
/*     */       case 0:
/* 282 */         if (paramString != null) {
/* 283 */           stringBuilder.append(paramString).append('.');
/*     */         }
/* 285 */         if (bool2) {
/* 286 */           stringBuilder.append("narrow.");
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 291 */         else if (paramType == LocaleProviderAdapter.Type.JRE) {
/* 292 */           if (paramBoolean && 
/* 293 */             i == 2) {
/* 294 */             stringBuilder.append("long.");
/*     */           }
/*     */           
/* 297 */           if (i == 1) {
/* 298 */             stringBuilder.append("short.");
/*     */           }
/*     */         }
/* 301 */         else if (i == 2) {
/* 302 */           stringBuilder.append("long.");
/*     */         } 
/*     */ 
/*     */         
/* 306 */         stringBuilder.append("Eras");
/*     */         break;
/*     */       
/*     */       case 1:
/* 310 */         if (!bool2) {
/* 311 */           stringBuilder.append(paramString).append(".FirstYear");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2:
/* 316 */         if ("islamic".equals(paramString)) {
/* 317 */           stringBuilder.append(paramString).append('.');
/*     */         }
/* 319 */         if (bool1) {
/* 320 */           stringBuilder.append("standalone.");
/*     */         }
/* 322 */         stringBuilder.append("Month").append(toStyleName(i));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 7:
/* 327 */         if (bool1 && bool2) {
/* 328 */           stringBuilder.append("standalone.");
/*     */         }
/* 330 */         stringBuilder.append("Day").append(toStyleName(i));
/*     */         break;
/*     */       
/*     */       case 9:
/* 334 */         if (bool2) {
/* 335 */           stringBuilder.append("narrow.");
/*     */         }
/* 337 */         stringBuilder.append("AmPmMarkers");
/*     */         break;
/*     */     } 
/* 340 */     return (stringBuilder.length() > 0) ? stringBuilder.toString() : null;
/*     */   }
/*     */   
/*     */   private static String toStyleName(int paramInt) {
/* 344 */     switch (paramInt) {
/*     */       case 1:
/* 346 */         return "Abbreviations";
/*     */       case 4:
/* 348 */         return "Narrows";
/*     */     } 
/* 350 */     return "Names";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/CalendarNameProviderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */