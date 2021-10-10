/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InternalLocaleBuilder
/*     */ {
/*  43 */   private static final CaseInsensitiveChar PRIVATEUSE_KEY = new CaseInsensitiveChar("x");
/*     */ 
/*     */   
/*  46 */   private String language = "";
/*  47 */   private String script = "";
/*  48 */   private String region = "";
/*  49 */   private String variant = "";
/*     */ 
/*     */   
/*     */   private Map<CaseInsensitiveChar, String> extensions;
/*     */   
/*     */   private Set<CaseInsensitiveString> uattributes;
/*     */   
/*     */   private Map<CaseInsensitiveString, String> ukeywords;
/*     */ 
/*     */   
/*     */   public InternalLocaleBuilder setLanguage(String paramString) throws LocaleSyntaxException {
/*  60 */     if (LocaleUtils.isEmpty(paramString)) {
/*  61 */       this.language = "";
/*     */     } else {
/*  63 */       if (!LanguageTag.isLanguage(paramString)) {
/*  64 */         throw new LocaleSyntaxException("Ill-formed language: " + paramString, 0);
/*     */       }
/*  66 */       this.language = paramString;
/*     */     } 
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder setScript(String paramString) throws LocaleSyntaxException {
/*  72 */     if (LocaleUtils.isEmpty(paramString)) {
/*  73 */       this.script = "";
/*     */     } else {
/*  75 */       if (!LanguageTag.isScript(paramString)) {
/*  76 */         throw new LocaleSyntaxException("Ill-formed script: " + paramString, 0);
/*     */       }
/*  78 */       this.script = paramString;
/*     */     } 
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder setRegion(String paramString) throws LocaleSyntaxException {
/*  84 */     if (LocaleUtils.isEmpty(paramString)) {
/*  85 */       this.region = "";
/*     */     } else {
/*  87 */       if (!LanguageTag.isRegion(paramString)) {
/*  88 */         throw new LocaleSyntaxException("Ill-formed region: " + paramString, 0);
/*     */       }
/*  90 */       this.region = paramString;
/*     */     } 
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder setVariant(String paramString) throws LocaleSyntaxException {
/*  96 */     if (LocaleUtils.isEmpty(paramString)) {
/*  97 */       this.variant = "";
/*     */     } else {
/*     */       
/* 100 */       String str = paramString.replaceAll("-", "_");
/* 101 */       int i = checkVariants(str, "_");
/* 102 */       if (i != -1) {
/* 103 */         throw new LocaleSyntaxException("Ill-formed variant: " + paramString, i);
/*     */       }
/* 105 */       this.variant = str;
/*     */     } 
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder addUnicodeLocaleAttribute(String paramString) throws LocaleSyntaxException {
/* 111 */     if (!UnicodeLocaleExtension.isAttribute(paramString)) {
/* 112 */       throw new LocaleSyntaxException("Ill-formed Unicode locale attribute: " + paramString);
/*     */     }
/*     */     
/* 115 */     if (this.uattributes == null) {
/* 116 */       this.uattributes = new HashSet<>(4);
/*     */     }
/* 118 */     this.uattributes.add(new CaseInsensitiveString(paramString));
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder removeUnicodeLocaleAttribute(String paramString) throws LocaleSyntaxException {
/* 123 */     if (paramString == null || !UnicodeLocaleExtension.isAttribute(paramString)) {
/* 124 */       throw new LocaleSyntaxException("Ill-formed Unicode locale attribute: " + paramString);
/*     */     }
/* 126 */     if (this.uattributes != null) {
/* 127 */       this.uattributes.remove(new CaseInsensitiveString(paramString));
/*     */     }
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder setUnicodeLocaleKeyword(String paramString1, String paramString2) throws LocaleSyntaxException {
/* 133 */     if (!UnicodeLocaleExtension.isKey(paramString1)) {
/* 134 */       throw new LocaleSyntaxException("Ill-formed Unicode locale keyword key: " + paramString1);
/*     */     }
/*     */     
/* 137 */     CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(paramString1);
/* 138 */     if (paramString2 == null) {
/* 139 */       if (this.ukeywords != null)
/*     */       {
/* 141 */         this.ukeywords.remove(caseInsensitiveString);
/*     */       }
/*     */     } else {
/* 144 */       if (paramString2.length() != 0) {
/*     */         
/* 146 */         String str = paramString2.replaceAll("_", "-");
/*     */         
/* 148 */         StringTokenIterator stringTokenIterator = new StringTokenIterator(str, "-");
/* 149 */         while (!stringTokenIterator.isDone()) {
/* 150 */           String str1 = stringTokenIterator.current();
/* 151 */           if (!UnicodeLocaleExtension.isTypeSubtag(str1)) {
/* 152 */             throw new LocaleSyntaxException("Ill-formed Unicode locale keyword type: " + paramString2, stringTokenIterator
/*     */                 
/* 154 */                 .currentStart());
/*     */           }
/* 156 */           stringTokenIterator.next();
/*     */         } 
/*     */       } 
/* 159 */       if (this.ukeywords == null) {
/* 160 */         this.ukeywords = new HashMap<>(4);
/*     */       }
/* 162 */       this.ukeywords.put(caseInsensitiveString, paramString2);
/*     */     } 
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public InternalLocaleBuilder setExtension(char paramChar, String paramString) throws LocaleSyntaxException {
/* 169 */     boolean bool1 = LanguageTag.isPrivateusePrefixChar(paramChar);
/* 170 */     if (!bool1 && !LanguageTag.isExtensionSingletonChar(paramChar)) {
/* 171 */       throw new LocaleSyntaxException("Ill-formed extension key: " + paramChar);
/*     */     }
/*     */     
/* 174 */     boolean bool2 = LocaleUtils.isEmpty(paramString);
/* 175 */     CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(paramChar);
/*     */     
/* 177 */     if (bool2) {
/* 178 */       if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
/*     */         
/* 180 */         if (this.uattributes != null) {
/* 181 */           this.uattributes.clear();
/*     */         }
/* 183 */         if (this.ukeywords != null) {
/* 184 */           this.ukeywords.clear();
/*     */         }
/*     */       }
/* 187 */       else if (this.extensions != null && this.extensions.containsKey(caseInsensitiveChar)) {
/* 188 */         this.extensions.remove(caseInsensitiveChar);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 193 */       String str = paramString.replaceAll("_", "-");
/* 194 */       StringTokenIterator stringTokenIterator = new StringTokenIterator(str, "-");
/* 195 */       while (!stringTokenIterator.isDone()) {
/* 196 */         boolean bool; String str1 = stringTokenIterator.current();
/*     */         
/* 198 */         if (bool1) {
/* 199 */           bool = LanguageTag.isPrivateuseSubtag(str1);
/*     */         } else {
/* 201 */           bool = LanguageTag.isExtensionSubtag(str1);
/*     */         } 
/* 203 */         if (!bool) {
/* 204 */           throw new LocaleSyntaxException("Ill-formed extension value: " + str1, stringTokenIterator
/* 205 */               .currentStart());
/*     */         }
/* 207 */         stringTokenIterator.next();
/*     */       } 
/*     */       
/* 210 */       if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
/* 211 */         setUnicodeLocaleExtension(str);
/*     */       } else {
/* 213 */         if (this.extensions == null) {
/* 214 */           this.extensions = new HashMap<>(4);
/*     */         }
/* 216 */         this.extensions.put(caseInsensitiveChar, str);
/*     */       } 
/*     */     } 
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InternalLocaleBuilder setExtensions(String paramString) throws LocaleSyntaxException {
/* 226 */     if (LocaleUtils.isEmpty(paramString)) {
/* 227 */       clearExtensions();
/* 228 */       return this;
/*     */     } 
/* 230 */     paramString = paramString.replaceAll("_", "-");
/* 231 */     StringTokenIterator stringTokenIterator = new StringTokenIterator(paramString, "-");
/*     */     
/* 233 */     ArrayList<String> arrayList = null;
/* 234 */     String str = null;
/*     */     
/* 236 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/* 240 */     while (!stringTokenIterator.isDone()) {
/* 241 */       String str1 = stringTokenIterator.current();
/* 242 */       if (LanguageTag.isExtensionSingleton(str1)) {
/* 243 */         int j = stringTokenIterator.currentStart();
/* 244 */         String str2 = str1;
/* 245 */         StringBuilder stringBuilder = new StringBuilder(str2);
/*     */         
/* 247 */         stringTokenIterator.next();
/* 248 */         while (!stringTokenIterator.isDone()) {
/* 249 */           str1 = stringTokenIterator.current();
/* 250 */           if (LanguageTag.isExtensionSubtag(str1)) {
/* 251 */             stringBuilder.append("-").append(str1);
/* 252 */             i = stringTokenIterator.currentEnd();
/*     */ 
/*     */ 
/*     */             
/* 256 */             stringTokenIterator.next();
/*     */           } 
/*     */         } 
/* 259 */         if (i < j) {
/* 260 */           throw new LocaleSyntaxException("Incomplete extension '" + str2 + "'", j);
/*     */         }
/*     */ 
/*     */         
/* 264 */         if (arrayList == null) {
/* 265 */           arrayList = new ArrayList(4);
/*     */         }
/* 267 */         arrayList.add(stringBuilder.toString());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 272 */     if (!stringTokenIterator.isDone()) {
/* 273 */       String str1 = stringTokenIterator.current();
/* 274 */       if (LanguageTag.isPrivateusePrefix(str1)) {
/* 275 */         int j = stringTokenIterator.currentStart();
/* 276 */         StringBuilder stringBuilder = new StringBuilder(str1);
/*     */         
/* 278 */         stringTokenIterator.next();
/* 279 */         while (!stringTokenIterator.isDone()) {
/* 280 */           str1 = stringTokenIterator.current();
/* 281 */           if (!LanguageTag.isPrivateuseSubtag(str1)) {
/*     */             break;
/*     */           }
/* 284 */           stringBuilder.append("-").append(str1);
/* 285 */           i = stringTokenIterator.currentEnd();
/*     */           
/* 287 */           stringTokenIterator.next();
/*     */         } 
/* 289 */         if (i <= j) {
/* 290 */           throw new LocaleSyntaxException("Incomplete privateuse:" + paramString
/* 291 */               .substring(j), j);
/*     */         }
/*     */         
/* 294 */         str = stringBuilder.toString();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 299 */     if (!stringTokenIterator.isDone()) {
/* 300 */       throw new LocaleSyntaxException("Ill-formed extension subtags:" + paramString
/* 301 */           .substring(stringTokenIterator.currentStart()), stringTokenIterator
/* 302 */           .currentStart());
/*     */     }
/*     */     
/* 305 */     return setExtensions(arrayList, str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InternalLocaleBuilder setExtensions(List<String> paramList, String paramString) {
/* 313 */     clearExtensions();
/*     */     
/* 315 */     if (!LocaleUtils.isEmpty(paramList)) {
/* 316 */       HashSet<CaseInsensitiveChar> hashSet = new HashSet(paramList.size());
/* 317 */       for (String str : paramList) {
/* 318 */         CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(str);
/*     */         
/* 320 */         if (!hashSet.contains(caseInsensitiveChar))
/*     */         {
/* 322 */           if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
/* 323 */             setUnicodeLocaleExtension(str.substring(2));
/*     */           } else {
/* 325 */             if (this.extensions == null) {
/* 326 */               this.extensions = new HashMap<>(4);
/*     */             }
/* 328 */             this.extensions.put(caseInsensitiveChar, str.substring(2));
/*     */           } 
/*     */         }
/* 331 */         hashSet.add(caseInsensitiveChar);
/*     */       } 
/*     */     } 
/* 334 */     if (paramString != null && paramString.length() > 0) {
/*     */       
/* 336 */       if (this.extensions == null) {
/* 337 */         this.extensions = new HashMap<>(1);
/*     */       }
/* 339 */       this.extensions.put(new CaseInsensitiveChar(paramString), paramString.substring(2));
/*     */     } 
/*     */     
/* 342 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InternalLocaleBuilder setLanguageTag(LanguageTag paramLanguageTag) {
/* 349 */     clear();
/* 350 */     if (!paramLanguageTag.getExtlangs().isEmpty()) {
/* 351 */       this.language = paramLanguageTag.getExtlangs().get(0);
/*     */     } else {
/* 353 */       String str = paramLanguageTag.getLanguage();
/* 354 */       if (!str.equals("und")) {
/* 355 */         this.language = str;
/*     */       }
/*     */     } 
/* 358 */     this.script = paramLanguageTag.getScript();
/* 359 */     this.region = paramLanguageTag.getRegion();
/*     */     
/* 361 */     List<String> list = paramLanguageTag.getVariants();
/* 362 */     if (!list.isEmpty()) {
/* 363 */       StringBuilder stringBuilder = new StringBuilder(list.get(0));
/* 364 */       int i = list.size();
/* 365 */       for (byte b = 1; b < i; b++) {
/* 366 */         stringBuilder.append("_").append(list.get(b));
/*     */       }
/* 368 */       this.variant = stringBuilder.toString();
/*     */     } 
/*     */     
/* 371 */     setExtensions(paramLanguageTag.getExtensions(), paramLanguageTag.getPrivateuse());
/*     */     
/* 373 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder setLocale(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions) throws LocaleSyntaxException {
/* 377 */     String str1 = paramBaseLocale.getLanguage();
/* 378 */     String str2 = paramBaseLocale.getScript();
/* 379 */     String str3 = paramBaseLocale.getRegion();
/* 380 */     String str4 = paramBaseLocale.getVariant();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     if (str1.equals("ja") && str3.equals("JP") && str4.equals("JP")) {
/*     */ 
/*     */       
/* 388 */       assert "japanese".equals(paramLocaleExtensions.getUnicodeLocaleType("ca"));
/* 389 */       str4 = "";
/*     */     
/*     */     }
/* 392 */     else if (str1.equals("th") && str3.equals("TH") && str4.equals("TH")) {
/*     */ 
/*     */       
/* 395 */       assert "thai".equals(paramLocaleExtensions.getUnicodeLocaleType("nu"));
/* 396 */       str4 = "";
/*     */     
/*     */     }
/* 399 */     else if (str1.equals("no") && str3.equals("NO") && str4.equals("NY")) {
/*     */ 
/*     */       
/* 402 */       str1 = "nn";
/* 403 */       str4 = "";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 409 */     if (str1.length() > 0 && !LanguageTag.isLanguage(str1)) {
/* 410 */       throw new LocaleSyntaxException("Ill-formed language: " + str1);
/*     */     }
/*     */     
/* 413 */     if (str2.length() > 0 && !LanguageTag.isScript(str2)) {
/* 414 */       throw new LocaleSyntaxException("Ill-formed script: " + str2);
/*     */     }
/*     */     
/* 417 */     if (str3.length() > 0 && !LanguageTag.isRegion(str3)) {
/* 418 */       throw new LocaleSyntaxException("Ill-formed region: " + str3);
/*     */     }
/*     */     
/* 421 */     if (str4.length() > 0) {
/* 422 */       int i = checkVariants(str4, "_");
/* 423 */       if (i != -1) {
/* 424 */         throw new LocaleSyntaxException("Ill-formed variant: " + str4, i);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 430 */     this.language = str1;
/* 431 */     this.script = str2;
/* 432 */     this.region = str3;
/* 433 */     this.variant = str4;
/* 434 */     clearExtensions();
/*     */     
/* 436 */     Set<Character> set = (paramLocaleExtensions == null) ? null : paramLocaleExtensions.getKeys();
/* 437 */     if (set != null)
/*     */     {
/* 439 */       for (Character character : set) {
/* 440 */         Extension extension = paramLocaleExtensions.getExtension(character);
/* 441 */         if (extension instanceof UnicodeLocaleExtension) {
/* 442 */           UnicodeLocaleExtension unicodeLocaleExtension = (UnicodeLocaleExtension)extension;
/* 443 */           for (String str : unicodeLocaleExtension.getUnicodeLocaleAttributes()) {
/* 444 */             if (this.uattributes == null) {
/* 445 */               this.uattributes = new HashSet<>(4);
/*     */             }
/* 447 */             this.uattributes.add(new CaseInsensitiveString(str));
/*     */           } 
/* 449 */           for (String str : unicodeLocaleExtension.getUnicodeLocaleKeys()) {
/* 450 */             if (this.ukeywords == null) {
/* 451 */               this.ukeywords = new HashMap<>(4);
/*     */             }
/* 453 */             this.ukeywords.put(new CaseInsensitiveString(str), unicodeLocaleExtension.getUnicodeLocaleType(str));
/*     */           }  continue;
/*     */         } 
/* 456 */         if (this.extensions == null) {
/* 457 */           this.extensions = new HashMap<>(4);
/*     */         }
/* 459 */         this.extensions.put(new CaseInsensitiveChar(character.charValue()), extension.getValue());
/*     */       } 
/*     */     }
/*     */     
/* 463 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder clear() {
/* 467 */     this.language = "";
/* 468 */     this.script = "";
/* 469 */     this.region = "";
/* 470 */     this.variant = "";
/* 471 */     clearExtensions();
/* 472 */     return this;
/*     */   }
/*     */   
/*     */   public InternalLocaleBuilder clearExtensions() {
/* 476 */     if (this.extensions != null) {
/* 477 */       this.extensions.clear();
/*     */     }
/* 479 */     if (this.uattributes != null) {
/* 480 */       this.uattributes.clear();
/*     */     }
/* 482 */     if (this.ukeywords != null) {
/* 483 */       this.ukeywords.clear();
/*     */     }
/* 485 */     return this;
/*     */   }
/*     */   
/*     */   public BaseLocale getBaseLocale() {
/* 489 */     String str1 = this.language;
/* 490 */     String str2 = this.script;
/* 491 */     String str3 = this.region;
/* 492 */     String str4 = this.variant;
/*     */ 
/*     */ 
/*     */     
/* 496 */     if (this.extensions != null) {
/* 497 */       String str = this.extensions.get(PRIVATEUSE_KEY);
/* 498 */       if (str != null) {
/* 499 */         StringTokenIterator stringTokenIterator = new StringTokenIterator(str, "-");
/* 500 */         boolean bool = false;
/* 501 */         int i = -1;
/* 502 */         while (!stringTokenIterator.isDone()) {
/* 503 */           if (bool) {
/* 504 */             i = stringTokenIterator.currentStart();
/*     */             break;
/*     */           } 
/* 507 */           if (LocaleUtils.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
/* 508 */             bool = true;
/*     */           }
/* 510 */           stringTokenIterator.next();
/*     */         } 
/* 512 */         if (i != -1) {
/* 513 */           StringBuilder stringBuilder = new StringBuilder(str4);
/* 514 */           if (stringBuilder.length() != 0) {
/* 515 */             stringBuilder.append("_");
/*     */           }
/* 517 */           stringBuilder.append(str.substring(i).replaceAll("-", "_"));
/*     */           
/* 519 */           str4 = stringBuilder.toString();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 524 */     return BaseLocale.getInstance(str1, str2, str3, str4);
/*     */   }
/*     */   
/*     */   public LocaleExtensions getLocaleExtensions() {
/* 528 */     if (LocaleUtils.isEmpty(this.extensions) && LocaleUtils.isEmpty(this.uattributes) && 
/* 529 */       LocaleUtils.isEmpty(this.ukeywords)) {
/* 530 */       return null;
/*     */     }
/*     */     
/* 533 */     LocaleExtensions localeExtensions = new LocaleExtensions(this.extensions, this.uattributes, this.ukeywords);
/* 534 */     return localeExtensions.isEmpty() ? null : localeExtensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String removePrivateuseVariant(String paramString) {
/* 542 */     StringTokenIterator stringTokenIterator = new StringTokenIterator(paramString, "-");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 547 */     int i = -1;
/* 548 */     boolean bool = false;
/* 549 */     while (!stringTokenIterator.isDone()) {
/* 550 */       if (i != -1) {
/*     */ 
/*     */         
/* 553 */         bool = true;
/*     */         break;
/*     */       } 
/* 556 */       if (LocaleUtils.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
/* 557 */         i = stringTokenIterator.currentStart();
/*     */       }
/* 559 */       stringTokenIterator.next();
/*     */     } 
/* 561 */     if (!bool) {
/* 562 */       return paramString;
/*     */     }
/*     */     
/* 565 */     assert i == 0 || i > 1;
/* 566 */     return (i == 0) ? null : paramString.substring(0, i - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int checkVariants(String paramString1, String paramString2) {
/* 574 */     StringTokenIterator stringTokenIterator = new StringTokenIterator(paramString1, paramString2);
/* 575 */     while (!stringTokenIterator.isDone()) {
/* 576 */       String str = stringTokenIterator.current();
/* 577 */       if (!LanguageTag.isVariant(str)) {
/* 578 */         return stringTokenIterator.currentStart();
/*     */       }
/* 580 */       stringTokenIterator.next();
/*     */     } 
/* 582 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setUnicodeLocaleExtension(String paramString) {
/* 592 */     if (this.uattributes != null) {
/* 593 */       this.uattributes.clear();
/*     */     }
/* 595 */     if (this.ukeywords != null) {
/* 596 */       this.ukeywords.clear();
/*     */     }
/*     */     
/* 599 */     StringTokenIterator stringTokenIterator = new StringTokenIterator(paramString, "-");
/*     */ 
/*     */     
/* 602 */     while (!stringTokenIterator.isDone() && 
/* 603 */       UnicodeLocaleExtension.isAttribute(stringTokenIterator.current())) {
/*     */ 
/*     */       
/* 606 */       if (this.uattributes == null) {
/* 607 */         this.uattributes = new HashSet<>(4);
/*     */       }
/* 609 */       this.uattributes.add(new CaseInsensitiveString(stringTokenIterator.current()));
/* 610 */       stringTokenIterator.next();
/*     */     } 
/*     */ 
/*     */     
/* 614 */     CaseInsensitiveString caseInsensitiveString = null;
/*     */     
/* 616 */     int i = -1;
/* 617 */     int j = -1;
/* 618 */     while (!stringTokenIterator.isDone()) {
/* 619 */       if (caseInsensitiveString != null) {
/* 620 */         if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
/*     */           
/* 622 */           assert i == -1 || j != -1;
/* 623 */           String str = (i == -1) ? "" : paramString.substring(i, j);
/* 624 */           if (this.ukeywords == null) {
/* 625 */             this.ukeywords = new HashMap<>(4);
/*     */           }
/* 627 */           this.ukeywords.put(caseInsensitiveString, str);
/*     */ 
/*     */           
/* 630 */           CaseInsensitiveString caseInsensitiveString1 = new CaseInsensitiveString(stringTokenIterator.current());
/* 631 */           caseInsensitiveString = this.ukeywords.containsKey(caseInsensitiveString1) ? null : caseInsensitiveString1;
/* 632 */           i = j = -1;
/*     */         } else {
/* 634 */           if (i == -1) {
/* 635 */             i = stringTokenIterator.currentStart();
/*     */           }
/* 637 */           j = stringTokenIterator.currentEnd();
/*     */         } 
/* 639 */       } else if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
/*     */ 
/*     */         
/* 642 */         caseInsensitiveString = new CaseInsensitiveString(stringTokenIterator.current());
/* 643 */         if (this.ukeywords != null && this.ukeywords.containsKey(caseInsensitiveString))
/*     */         {
/* 645 */           caseInsensitiveString = null;
/*     */         }
/*     */       } 
/*     */       
/* 649 */       if (!stringTokenIterator.hasNext()) {
/* 650 */         if (caseInsensitiveString != null) {
/*     */           
/* 652 */           assert i == -1 || j != -1;
/* 653 */           String str = (i == -1) ? "" : paramString.substring(i, j);
/* 654 */           if (this.ukeywords == null) {
/* 655 */             this.ukeywords = new HashMap<>(4);
/*     */           }
/* 657 */           this.ukeywords.put(caseInsensitiveString, str);
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/* 662 */       stringTokenIterator.next();
/*     */     } 
/*     */   }
/*     */   
/*     */   static final class CaseInsensitiveString { private final String str;
/*     */     private final String lowerStr;
/*     */     
/*     */     CaseInsensitiveString(String param1String) {
/* 670 */       this.str = param1String;
/* 671 */       this.lowerStr = LocaleUtils.toLowerString(param1String);
/*     */     }
/*     */     
/*     */     public String value() {
/* 675 */       return this.str;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 680 */       return this.lowerStr.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 685 */       if (this == param1Object) {
/* 686 */         return true;
/*     */       }
/* 688 */       if (!(param1Object instanceof CaseInsensitiveString)) {
/* 689 */         return false;
/*     */       }
/* 691 */       return this.lowerStr.equals(((CaseInsensitiveString)param1Object).lowerStr);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class CaseInsensitiveChar
/*     */   {
/*     */     private final char ch;
/*     */     
/*     */     private final char lowerCh;
/*     */     
/*     */     private CaseInsensitiveChar(String param1String) {
/* 703 */       this(param1String.charAt(0));
/*     */     }
/*     */     
/*     */     CaseInsensitiveChar(char param1Char) {
/* 707 */       this.ch = param1Char;
/* 708 */       this.lowerCh = LocaleUtils.toLower(this.ch);
/*     */     }
/*     */     
/*     */     public char value() {
/* 712 */       return this.ch;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 717 */       return this.lowerCh;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 722 */       if (this == param1Object) {
/* 723 */         return true;
/*     */       }
/* 725 */       if (!(param1Object instanceof CaseInsensitiveChar)) {
/* 726 */         return false;
/*     */       }
/* 728 */       return (this.lowerCh == ((CaseInsensitiveChar)param1Object).lowerCh);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/InternalLocaleBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */