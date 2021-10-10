/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LocaleMatcher
/*     */ {
/*     */   public static List<Locale> filter(List<Locale.LanguageRange> paramList, Collection<Locale> paramCollection, Locale.FilteringMode paramFilteringMode) {
/*  51 */     if (paramList.isEmpty() || paramCollection.isEmpty()) {
/*  52 */       return new ArrayList<>();
/*     */     }
/*     */ 
/*     */     
/*  56 */     ArrayList<String> arrayList = new ArrayList();
/*  57 */     for (Locale locale : paramCollection) {
/*  58 */       arrayList.add(locale.toLanguageTag());
/*     */     }
/*     */ 
/*     */     
/*  62 */     List<String> list = filterTags(paramList, arrayList, paramFilteringMode);
/*     */ 
/*     */     
/*  65 */     ArrayList<Locale> arrayList1 = new ArrayList(list.size());
/*  66 */     for (String str : list) {
/*  67 */       arrayList1.add(Locale.forLanguageTag(str));
/*     */     }
/*     */     
/*  70 */     return arrayList1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<String> filterTags(List<Locale.LanguageRange> paramList, Collection<String> paramCollection, Locale.FilteringMode paramFilteringMode) {
/*  76 */     if (paramList.isEmpty() || paramCollection.isEmpty()) {
/*  77 */       return new ArrayList<>();
/*     */     }
/*     */ 
/*     */     
/*  81 */     if (paramFilteringMode == Locale.FilteringMode.EXTENDED_FILTERING) {
/*  82 */       return filterExtended(paramList, paramCollection);
/*     */     }
/*  84 */     ArrayList<Locale.LanguageRange> arrayList = new ArrayList();
/*  85 */     for (Locale.LanguageRange languageRange : paramList) {
/*  86 */       String str = languageRange.getRange();
/*  87 */       if (str.startsWith("*-") || str
/*  88 */         .indexOf("-*") != -1) {
/*  89 */         if (paramFilteringMode == Locale.FilteringMode.AUTOSELECT_FILTERING)
/*  90 */           return filterExtended(paramList, paramCollection); 
/*  91 */         if (paramFilteringMode == Locale.FilteringMode.MAP_EXTENDED_RANGES) {
/*  92 */           if (str.charAt(0) == '*') {
/*  93 */             str = "*";
/*     */           } else {
/*  95 */             str = str.replaceAll("-[*]", "");
/*     */           } 
/*  97 */           arrayList.add(new Locale.LanguageRange(str, languageRange.getWeight())); continue;
/*  98 */         }  if (paramFilteringMode == Locale.FilteringMode.REJECT_EXTENDED_RANGES) {
/*  99 */           throw new IllegalArgumentException("An extended range \"" + str + "\" found in REJECT_EXTENDED_RANGES mode.");
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/* 104 */       arrayList.add(languageRange);
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return filterBasic(arrayList, paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> filterBasic(List<Locale.LanguageRange> paramList, Collection<String> paramCollection) {
/* 114 */     ArrayList<String> arrayList = new ArrayList();
/* 115 */     for (Locale.LanguageRange languageRange : paramList) {
/* 116 */       String str = languageRange.getRange();
/* 117 */       if (str.equals("*")) {
/* 118 */         return new ArrayList<>(paramCollection);
/*     */       }
/* 120 */       for (String str1 : paramCollection) {
/* 121 */         str1 = str1.toLowerCase();
/* 122 */         if (str1.startsWith(str)) {
/* 123 */           int i = str.length();
/* 124 */           if ((str1.length() == i || str1.charAt(i) == '-') && 
/* 125 */             !arrayList.contains(str1)) {
/* 126 */             arrayList.add(str1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 133 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<String> filterExtended(List<Locale.LanguageRange> paramList, Collection<String> paramCollection) {
/* 138 */     ArrayList<String> arrayList = new ArrayList();
/* 139 */     for (Locale.LanguageRange languageRange : paramList) {
/* 140 */       String str = languageRange.getRange();
/* 141 */       if (str.equals("*")) {
/* 142 */         return new ArrayList<>(paramCollection);
/*     */       }
/* 144 */       String[] arrayOfString = str.split("-");
/* 145 */       for (String str1 : paramCollection) {
/* 146 */         str1 = str1.toLowerCase();
/* 147 */         String[] arrayOfString1 = str1.split("-");
/* 148 */         if (!arrayOfString[0].equals(arrayOfString1[0]) && 
/* 149 */           !arrayOfString[0].equals("*")) {
/*     */           continue;
/*     */         }
/*     */         
/* 153 */         byte b1 = 1;
/* 154 */         byte b2 = 1;
/*     */         
/* 156 */         while (b1 < arrayOfString.length && b2 < arrayOfString1.length) {
/*     */           
/* 158 */           if (arrayOfString[b1].equals("*")) {
/* 159 */             b1++; continue;
/* 160 */           }  if (arrayOfString[b1].equals(arrayOfString1[b2])) {
/* 161 */             b1++;
/* 162 */             b2++; continue;
/* 163 */           }  if (arrayOfString1[b2].length() == 1 && 
/* 164 */             !arrayOfString1[b2].equals("*")) {
/*     */             break;
/*     */           }
/* 167 */           b2++;
/*     */         } 
/*     */ 
/*     */         
/* 171 */         if (arrayOfString.length == b1 && !arrayList.contains(str1)) {
/* 172 */           arrayList.add(str1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Locale lookup(List<Locale.LanguageRange> paramList, Collection<Locale> paramCollection) {
/* 182 */     if (paramList.isEmpty() || paramCollection.isEmpty()) {
/* 183 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 187 */     ArrayList<String> arrayList = new ArrayList();
/* 188 */     for (Locale locale : paramCollection) {
/* 189 */       arrayList.add(locale.toLanguageTag());
/*     */     }
/*     */ 
/*     */     
/* 193 */     String str = lookupTag(paramList, arrayList);
/*     */     
/* 195 */     if (str == null) {
/* 196 */       return null;
/*     */     }
/* 198 */     return Locale.forLanguageTag(str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String lookupTag(List<Locale.LanguageRange> paramList, Collection<String> paramCollection) {
/* 204 */     if (paramList.isEmpty() || paramCollection.isEmpty()) {
/* 205 */       return null;
/*     */     }
/*     */     
/* 208 */     for (Locale.LanguageRange languageRange : paramList) {
/* 209 */       String str1 = languageRange.getRange();
/*     */ 
/*     */       
/* 212 */       if (str1.equals("*")) {
/*     */         continue;
/*     */       }
/*     */       
/* 216 */       String str2 = str1.replaceAll("\\x2A", "\\\\p{Alnum}*");
/* 217 */       while (str2.length() > 0) {
/* 218 */         for (String str : paramCollection) {
/* 219 */           str = str.toLowerCase();
/* 220 */           if (str.matches(str2)) {
/* 221 */             return str;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 226 */         int i = str2.lastIndexOf('-');
/* 227 */         if (i >= 0) {
/* 228 */           str2 = str2.substring(0, i);
/*     */ 
/*     */           
/* 231 */           if (str2.lastIndexOf('-') == str2.length() - 2)
/*     */           {
/* 233 */             str2 = str2.substring(0, str2.length() - 2); } 
/*     */           continue;
/*     */         } 
/* 236 */         str2 = "";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 241 */     return null;
/*     */   }
/*     */   
/*     */   public static List<Locale.LanguageRange> parse(String paramString) {
/* 245 */     paramString = paramString.replaceAll(" ", "").toLowerCase();
/* 246 */     if (paramString.startsWith("accept-language:")) {
/* 247 */       paramString = paramString.substring(16);
/*     */     }
/*     */     
/* 250 */     String[] arrayOfString = paramString.split(",");
/* 251 */     ArrayList<Locale.LanguageRange> arrayList = new ArrayList(arrayOfString.length);
/* 252 */     ArrayList<String> arrayList1 = new ArrayList();
/* 253 */     byte b = 0;
/*     */     
/* 255 */     for (String str1 : arrayOfString) {
/*     */       String str2;
/*     */       
/*     */       double d;
/*     */       int i;
/* 260 */       if ((i = str1.indexOf(";q=")) == -1) {
/* 261 */         str2 = str1;
/* 262 */         d = 1.0D;
/*     */       } else {
/* 264 */         str2 = str1.substring(0, i);
/* 265 */         i += 3;
/*     */         try {
/* 267 */           d = Double.parseDouble(str1.substring(i));
/*     */         }
/* 269 */         catch (Exception exception) {
/* 270 */           throw new IllegalArgumentException("weight=\"" + str1
/* 271 */               .substring(i) + "\" for language range \"" + str2 + "\"");
/*     */         } 
/*     */ 
/*     */         
/* 275 */         if (d < 0.0D || d > 1.0D) {
/* 276 */           throw new IllegalArgumentException("weight=" + d + " for language range \"" + str2 + "\". It must be between " + 0.0D + " and " + 1.0D + ".");
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       if (!arrayList1.contains(str2)) {
/* 284 */         Locale.LanguageRange languageRange = new Locale.LanguageRange(str2, d);
/* 285 */         i = b;
/* 286 */         for (byte b1 = 0; b1 < b; b1++) {
/* 287 */           if (((Locale.LanguageRange)arrayList.get(b1)).getWeight() < d) {
/* 288 */             i = b1;
/*     */             break;
/*     */           } 
/*     */         } 
/* 292 */         arrayList.add(i, languageRange);
/* 293 */         b++;
/* 294 */         arrayList1.add(str2);
/*     */ 
/*     */ 
/*     */         
/*     */         String str;
/*     */ 
/*     */         
/* 301 */         if ((str = getEquivalentForRegionAndVariant(str2)) != null && 
/* 302 */           !arrayList1.contains(str)) {
/* 303 */           arrayList.add(i + 1, new Locale.LanguageRange(str, d));
/* 304 */           b++;
/* 305 */           arrayList1.add(str);
/*     */         } 
/*     */         
/*     */         String[] arrayOfString1;
/* 309 */         if ((arrayOfString1 = getEquivalentsForLanguage(str2)) != null) {
/* 310 */           for (String str3 : arrayOfString1) {
/*     */             
/* 312 */             if (!arrayList1.contains(str3)) {
/* 313 */               arrayList.add(i + 1, new Locale.LanguageRange(str3, d));
/* 314 */               b++;
/* 315 */               arrayList1.add(str3);
/*     */             } 
/*     */ 
/*     */             
/* 319 */             str = getEquivalentForRegionAndVariant(str3);
/* 320 */             if (str != null && 
/* 321 */               !arrayList1.contains(str)) {
/* 322 */               arrayList.add(i + 1, new Locale.LanguageRange(str, d));
/* 323 */               b++;
/* 324 */               arrayList1.add(str);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return arrayList;
/*     */   }
/*     */   
/*     */   private static String[] getEquivalentsForLanguage(String paramString) {
/* 335 */     String str = paramString;
/*     */     
/* 337 */     while (str.length() > 0) {
/* 338 */       if (LocaleEquivalentMaps.singleEquivMap.containsKey(str)) {
/* 339 */         String str1 = LocaleEquivalentMaps.singleEquivMap.get(str);
/*     */ 
/*     */         
/* 342 */         return new String[] { paramString.replaceFirst(str, str1) };
/* 343 */       }  if (LocaleEquivalentMaps.multiEquivsMap.containsKey(str)) {
/* 344 */         String[] arrayOfString = LocaleEquivalentMaps.multiEquivsMap.get(str);
/* 345 */         for (byte b = 0; b < arrayOfString.length; b++) {
/* 346 */           arrayOfString[b] = paramString.replaceFirst(str, arrayOfString[b]);
/*     */         }
/* 348 */         return arrayOfString;
/*     */       } 
/*     */ 
/*     */       
/* 352 */       int i = str.lastIndexOf('-');
/* 353 */       if (i == -1) {
/*     */         break;
/*     */       }
/* 356 */       str = str.substring(0, i);
/*     */     } 
/*     */     
/* 359 */     return null;
/*     */   }
/*     */   
/*     */   private static String getEquivalentForRegionAndVariant(String paramString) {
/* 363 */     int i = getExtentionKeyIndex(paramString);
/*     */     
/* 365 */     for (String str : LocaleEquivalentMaps.regionVariantEquivMap.keySet()) {
/*     */       int j;
/* 367 */       if ((j = paramString.indexOf(str)) != -1) {
/*     */         
/* 369 */         if (i != Integer.MIN_VALUE && j > i) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 374 */         int k = j + str.length();
/* 375 */         if (paramString.length() == k || paramString.charAt(k) == '-') {
/* 376 */           return paramString.replaceFirst(str, LocaleEquivalentMaps.regionVariantEquivMap.get(str));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 381 */     return null;
/*     */   }
/*     */   
/*     */   private static int getExtentionKeyIndex(String paramString) {
/* 385 */     char[] arrayOfChar = paramString.toCharArray();
/* 386 */     int i = Integer.MIN_VALUE;
/* 387 */     for (byte b = 1; b < arrayOfChar.length; b++) {
/* 388 */       if (arrayOfChar[b] == '-') {
/* 389 */         if (b - i == 2) {
/* 390 */           return i;
/*     */         }
/* 392 */         i = b;
/*     */       } 
/*     */     } 
/*     */     
/* 396 */     return Integer.MIN_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Locale.LanguageRange> mapEquivalents(List<Locale.LanguageRange> paramList, Map<String, List<String>> paramMap) {
/* 402 */     if (paramList.isEmpty()) {
/* 403 */       return new ArrayList<>();
/*     */     }
/* 405 */     if (paramMap == null || paramMap.isEmpty()) {
/* 406 */       return new ArrayList<>(paramList);
/*     */     }
/*     */ 
/*     */     
/* 410 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 411 */     for (String str : paramMap.keySet()) {
/* 412 */       hashMap.put(str.toLowerCase(), str);
/*     */     }
/*     */     
/* 415 */     ArrayList<Locale.LanguageRange> arrayList = new ArrayList();
/* 416 */     for (Locale.LanguageRange languageRange : paramList) {
/* 417 */       String str1 = languageRange.getRange();
/* 418 */       String str2 = str1;
/* 419 */       boolean bool = false;
/*     */       
/* 421 */       while (str2.length() > 0) {
/* 422 */         if (hashMap.containsKey(str2)) {
/* 423 */           bool = true;
/* 424 */           List list = paramMap.get(hashMap.get(str2));
/* 425 */           if (list != null) {
/* 426 */             int j = str2.length();
/* 427 */             for (String str : list) {
/* 428 */               arrayList.add(new Locale.LanguageRange(str.toLowerCase() + str1
/* 429 */                     .substring(j), languageRange
/* 430 */                     .getWeight()));
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 438 */         int i = str2.lastIndexOf('-');
/* 439 */         if (i == -1) {
/*     */           break;
/*     */         }
/* 442 */         str2 = str2.substring(0, i);
/*     */       } 
/*     */       
/* 445 */       if (!bool) {
/* 446 */         arrayList.add(languageRange);
/*     */       }
/*     */     } 
/*     */     
/* 450 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/LocaleMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */