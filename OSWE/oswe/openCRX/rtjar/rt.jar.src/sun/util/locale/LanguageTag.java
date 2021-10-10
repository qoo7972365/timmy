/*     */ package sun.util.locale;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LanguageTag
/*     */ {
/*     */   public static final String SEP = "-";
/*     */   public static final String PRIVATEUSE = "x";
/*     */   public static final String UNDETERMINED = "und";
/*     */   public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
/*  53 */   private String language = "";
/*  54 */   private String script = "";
/*  55 */   private String region = "";
/*  56 */   private String privateuse = "";
/*     */   
/*  58 */   private List<String> extlangs = Collections.emptyList();
/*  59 */   private List<String> variants = Collections.emptyList();
/*  60 */   private List<String> extensions = Collections.emptyList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final Map<String, String[]> GRANDFATHERED = (Map)new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  99 */     String[][] arrayOfString = { { "art-lojban", "jbo" }, { "cel-gaulish", "xtg-x-cel-gaulish" }, { "en-GB-oed", "en-GB-x-oed" }, { "i-ami", "ami" }, { "i-bnn", "bnn" }, { "i-default", "en-x-i-default" }, { "i-enochian", "und-x-i-enochian" }, { "i-hak", "hak" }, { "i-klingon", "tlh" }, { "i-lux", "lb" }, { "i-mingo", "see-x-i-mingo" }, { "i-navajo", "nv" }, { "i-pwn", "pwn" }, { "i-tao", "tao" }, { "i-tay", "tay" }, { "i-tsu", "tsu" }, { "no-bok", "nb" }, { "no-nyn", "nn" }, { "sgn-BE-FR", "sfb" }, { "sgn-BE-NL", "vgt" }, { "sgn-CH-DE", "sgg" }, { "zh-guoyu", "cmn" }, { "zh-hakka", "hak" }, { "zh-min", "nan-x-zh-min" }, { "zh-min-nan", "nan" }, { "zh-xiang", "hsn" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     for (String[] arrayOfString1 : arrayOfString) {
/* 129 */       GRANDFATHERED.put(LocaleUtils.toLowerString(arrayOfString1[0]), arrayOfString1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LanguageTag parse(String paramString, ParseStatus paramParseStatus) {
/*     */     StringTokenIterator stringTokenIterator;
/* 182 */     if (paramParseStatus == null) {
/* 183 */       paramParseStatus = new ParseStatus();
/*     */     } else {
/* 185 */       paramParseStatus.reset();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     String[] arrayOfString = GRANDFATHERED.get(LocaleUtils.toLowerString(paramString));
/* 192 */     if (arrayOfString != null) {
/*     */       
/* 194 */       stringTokenIterator = new StringTokenIterator(arrayOfString[1], "-");
/*     */     } else {
/* 196 */       stringTokenIterator = new StringTokenIterator(paramString, "-");
/*     */     } 
/*     */     
/* 199 */     LanguageTag languageTag = new LanguageTag();
/*     */ 
/*     */     
/* 202 */     if (languageTag.parseLanguage(stringTokenIterator, paramParseStatus)) {
/* 203 */       languageTag.parseExtlangs(stringTokenIterator, paramParseStatus);
/* 204 */       languageTag.parseScript(stringTokenIterator, paramParseStatus);
/* 205 */       languageTag.parseRegion(stringTokenIterator, paramParseStatus);
/* 206 */       languageTag.parseVariants(stringTokenIterator, paramParseStatus);
/* 207 */       languageTag.parseExtensions(stringTokenIterator, paramParseStatus);
/*     */     } 
/* 209 */     languageTag.parsePrivateuse(stringTokenIterator, paramParseStatus);
/*     */     
/* 211 */     if (!stringTokenIterator.isDone() && !paramParseStatus.isError()) {
/* 212 */       String str = stringTokenIterator.current();
/* 213 */       paramParseStatus.errorIndex = stringTokenIterator.currentStart();
/* 214 */       if (str.length() == 0) {
/* 215 */         paramParseStatus.errorMsg = "Empty subtag";
/*     */       } else {
/* 217 */         paramParseStatus.errorMsg = "Invalid subtag: " + str;
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     return languageTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parseLanguage(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 229 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 230 */       return false;
/*     */     }
/*     */     
/* 233 */     boolean bool = false;
/*     */     
/* 235 */     String str = paramStringTokenIterator.current();
/* 236 */     if (isLanguage(str)) {
/* 237 */       bool = true;
/* 238 */       this.language = str;
/* 239 */       paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/* 240 */       paramStringTokenIterator.next();
/*     */     } 
/*     */     
/* 243 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean parseExtlangs(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 247 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 248 */       return false;
/*     */     }
/*     */     
/* 251 */     boolean bool = false;
/*     */     
/* 253 */     while (!paramStringTokenIterator.isDone()) {
/* 254 */       String str = paramStringTokenIterator.current();
/* 255 */       if (!isExtlang(str)) {
/*     */         break;
/*     */       }
/* 258 */       bool = true;
/* 259 */       if (this.extlangs.isEmpty()) {
/* 260 */         this.extlangs = new ArrayList<>(3);
/*     */       }
/* 262 */       this.extlangs.add(str);
/* 263 */       paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/* 264 */       paramStringTokenIterator.next();
/*     */       
/* 266 */       if (this.extlangs.size() == 3) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 272 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean parseScript(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 276 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 277 */       return false;
/*     */     }
/*     */     
/* 280 */     boolean bool = false;
/*     */     
/* 282 */     String str = paramStringTokenIterator.current();
/* 283 */     if (isScript(str)) {
/* 284 */       bool = true;
/* 285 */       this.script = str;
/* 286 */       paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/* 287 */       paramStringTokenIterator.next();
/*     */     } 
/*     */     
/* 290 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean parseRegion(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 294 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 295 */       return false;
/*     */     }
/*     */     
/* 298 */     boolean bool = false;
/*     */     
/* 300 */     String str = paramStringTokenIterator.current();
/* 301 */     if (isRegion(str)) {
/* 302 */       bool = true;
/* 303 */       this.region = str;
/* 304 */       paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/* 305 */       paramStringTokenIterator.next();
/*     */     } 
/*     */     
/* 308 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean parseVariants(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 312 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 313 */       return false;
/*     */     }
/*     */     
/* 316 */     boolean bool = false;
/*     */     
/* 318 */     while (!paramStringTokenIterator.isDone()) {
/* 319 */       String str = paramStringTokenIterator.current();
/* 320 */       if (!isVariant(str)) {
/*     */         break;
/*     */       }
/* 323 */       bool = true;
/* 324 */       if (this.variants.isEmpty()) {
/* 325 */         this.variants = new ArrayList<>(3);
/*     */       }
/* 327 */       this.variants.add(str);
/* 328 */       paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/* 329 */       paramStringTokenIterator.next();
/*     */     } 
/*     */     
/* 332 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean parseExtensions(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 336 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 337 */       return false;
/*     */     }
/*     */     
/* 340 */     boolean bool = false;
/*     */     
/* 342 */     while (!paramStringTokenIterator.isDone()) {
/* 343 */       String str = paramStringTokenIterator.current();
/* 344 */       if (isExtensionSingleton(str)) {
/* 345 */         int i = paramStringTokenIterator.currentStart();
/* 346 */         String str1 = str;
/* 347 */         StringBuilder stringBuilder = new StringBuilder(str1);
/*     */         
/* 349 */         paramStringTokenIterator.next();
/* 350 */         while (!paramStringTokenIterator.isDone()) {
/* 351 */           str = paramStringTokenIterator.current();
/* 352 */           if (isExtensionSubtag(str)) {
/* 353 */             stringBuilder.append("-").append(str);
/* 354 */             paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/*     */ 
/*     */ 
/*     */             
/* 358 */             paramStringTokenIterator.next();
/*     */           } 
/*     */         } 
/* 361 */         if (paramParseStatus.parseLength <= i) {
/* 362 */           paramParseStatus.errorIndex = i;
/* 363 */           paramParseStatus.errorMsg = "Incomplete extension '" + str1 + "'";
/*     */           
/*     */           break;
/*     */         } 
/* 367 */         if (this.extensions.isEmpty()) {
/* 368 */           this.extensions = new ArrayList<>(4);
/*     */         }
/* 370 */         this.extensions.add(stringBuilder.toString());
/* 371 */         bool = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 376 */     return bool;
/*     */   }
/*     */   
/*     */   private boolean parsePrivateuse(StringTokenIterator paramStringTokenIterator, ParseStatus paramParseStatus) {
/* 380 */     if (paramStringTokenIterator.isDone() || paramParseStatus.isError()) {
/* 381 */       return false;
/*     */     }
/*     */     
/* 384 */     boolean bool = false;
/*     */     
/* 386 */     String str = paramStringTokenIterator.current();
/* 387 */     if (isPrivateusePrefix(str)) {
/* 388 */       int i = paramStringTokenIterator.currentStart();
/* 389 */       StringBuilder stringBuilder = new StringBuilder(str);
/*     */       
/* 391 */       paramStringTokenIterator.next();
/* 392 */       while (!paramStringTokenIterator.isDone()) {
/* 393 */         str = paramStringTokenIterator.current();
/* 394 */         if (!isPrivateuseSubtag(str)) {
/*     */           break;
/*     */         }
/* 397 */         stringBuilder.append("-").append(str);
/* 398 */         paramParseStatus.parseLength = paramStringTokenIterator.currentEnd();
/*     */         
/* 400 */         paramStringTokenIterator.next();
/*     */       } 
/*     */       
/* 403 */       if (paramParseStatus.parseLength <= i) {
/*     */         
/* 405 */         paramParseStatus.errorIndex = i;
/* 406 */         paramParseStatus.errorMsg = "Incomplete privateuse";
/*     */       } else {
/* 408 */         this.privateuse = stringBuilder.toString();
/* 409 */         bool = true;
/*     */       } 
/*     */     } 
/*     */     
/* 413 */     return bool;
/*     */   }
/*     */   
/*     */   public static LanguageTag parseLocale(BaseLocale paramBaseLocale, LocaleExtensions paramLocaleExtensions) {
/* 417 */     LanguageTag languageTag = new LanguageTag();
/*     */     
/* 419 */     String str1 = paramBaseLocale.getLanguage();
/* 420 */     String str2 = paramBaseLocale.getScript();
/* 421 */     String str3 = paramBaseLocale.getRegion();
/* 422 */     String str4 = paramBaseLocale.getVariant();
/*     */     
/* 424 */     boolean bool = false;
/*     */     
/* 426 */     String str5 = null;
/*     */     
/* 428 */     if (isLanguage(str1)) {
/*     */       
/* 430 */       if (str1.equals("iw")) {
/* 431 */         str1 = "he";
/* 432 */       } else if (str1.equals("ji")) {
/* 433 */         str1 = "yi";
/* 434 */       } else if (str1.equals("in")) {
/* 435 */         str1 = "id";
/*     */       } 
/* 437 */       languageTag.language = str1;
/*     */     } 
/*     */     
/* 440 */     if (isScript(str2)) {
/* 441 */       languageTag.script = canonicalizeScript(str2);
/* 442 */       bool = true;
/*     */     } 
/*     */     
/* 445 */     if (isRegion(str3)) {
/* 446 */       languageTag.region = canonicalizeRegion(str3);
/* 447 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 451 */     if (languageTag.language.equals("no") && languageTag.region.equals("NO") && str4.equals("NY")) {
/* 452 */       languageTag.language = "nn";
/* 453 */       str4 = "";
/*     */     } 
/*     */     
/* 456 */     if (str4.length() > 0) {
/* 457 */       ArrayList<String> arrayList1 = null;
/* 458 */       StringTokenIterator stringTokenIterator = new StringTokenIterator(str4, "_");
/* 459 */       while (!stringTokenIterator.isDone()) {
/* 460 */         String str = stringTokenIterator.current();
/* 461 */         if (!isVariant(str)) {
/*     */           break;
/*     */         }
/* 464 */         if (arrayList1 == null) {
/* 465 */           arrayList1 = new ArrayList();
/*     */         }
/* 467 */         arrayList1.add(str);
/* 468 */         stringTokenIterator.next();
/*     */       } 
/* 470 */       if (arrayList1 != null) {
/* 471 */         languageTag.variants = arrayList1;
/* 472 */         bool = true;
/*     */       } 
/* 474 */       if (!stringTokenIterator.isDone()) {
/*     */         
/* 476 */         StringBuilder stringBuilder = new StringBuilder();
/* 477 */         while (!stringTokenIterator.isDone()) {
/* 478 */           String str = stringTokenIterator.current();
/* 479 */           if (!isPrivateuseSubtag(str)) {
/*     */             break;
/*     */           }
/*     */           
/* 483 */           if (stringBuilder.length() > 0) {
/* 484 */             stringBuilder.append("-");
/*     */           }
/* 486 */           stringBuilder.append(str);
/* 487 */           stringTokenIterator.next();
/*     */         } 
/* 489 */         if (stringBuilder.length() > 0) {
/* 490 */           str5 = stringBuilder.toString();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 495 */     ArrayList<String> arrayList = null;
/* 496 */     String str6 = null;
/*     */     
/* 498 */     if (paramLocaleExtensions != null) {
/* 499 */       Set<Character> set = paramLocaleExtensions.getKeys();
/* 500 */       for (Character character : set) {
/* 501 */         Extension extension = paramLocaleExtensions.getExtension(character);
/* 502 */         if (isPrivateusePrefixChar(character.charValue())) {
/* 503 */           str6 = extension.getValue(); continue;
/*     */         } 
/* 505 */         if (arrayList == null) {
/* 506 */           arrayList = new ArrayList();
/*     */         }
/* 508 */         arrayList.add(character.toString() + "-" + extension.getValue());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 513 */     if (arrayList != null) {
/* 514 */       languageTag.extensions = arrayList;
/* 515 */       bool = true;
/*     */     } 
/*     */ 
/*     */     
/* 519 */     if (str5 != null) {
/* 520 */       if (str6 == null) {
/* 521 */         str6 = "lvariant-" + str5;
/*     */       } else {
/*     */         
/* 524 */         str6 = str6 + "-" + "lvariant" + "-" + str5.replace("_", "-");
/*     */       } 
/*     */     }
/*     */     
/* 528 */     if (str6 != null) {
/* 529 */       languageTag.privateuse = str6;
/*     */     }
/*     */     
/* 532 */     if (languageTag.language.length() == 0 && (bool || str6 == null))
/*     */     {
/*     */ 
/*     */       
/* 536 */       languageTag.language = "und";
/*     */     }
/*     */     
/* 539 */     return languageTag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLanguage() {
/* 547 */     return this.language;
/*     */   }
/*     */   
/*     */   public List<String> getExtlangs() {
/* 551 */     if (this.extlangs.isEmpty()) {
/* 552 */       return Collections.emptyList();
/*     */     }
/* 554 */     return Collections.unmodifiableList(this.extlangs);
/*     */   }
/*     */   
/*     */   public String getScript() {
/* 558 */     return this.script;
/*     */   }
/*     */   
/*     */   public String getRegion() {
/* 562 */     return this.region;
/*     */   }
/*     */   
/*     */   public List<String> getVariants() {
/* 566 */     if (this.variants.isEmpty()) {
/* 567 */       return Collections.emptyList();
/*     */     }
/* 569 */     return Collections.unmodifiableList(this.variants);
/*     */   }
/*     */   
/*     */   public List<String> getExtensions() {
/* 573 */     if (this.extensions.isEmpty()) {
/* 574 */       return Collections.emptyList();
/*     */     }
/* 576 */     return Collections.unmodifiableList(this.extensions);
/*     */   }
/*     */   
/*     */   public String getPrivateuse() {
/* 580 */     return this.privateuse;
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
/*     */   public static boolean isLanguage(String paramString) {
/* 593 */     int i = paramString.length();
/* 594 */     return (i >= 2 && i <= 8 && LocaleUtils.isAlphaString(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isExtlang(String paramString) {
/* 600 */     return (paramString.length() == 3 && LocaleUtils.isAlphaString(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isScript(String paramString) {
/* 605 */     return (paramString.length() == 4 && LocaleUtils.isAlphaString(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRegion(String paramString) {
/* 611 */     return ((paramString.length() == 2 && LocaleUtils.isAlphaString(paramString)) || (paramString
/* 612 */       .length() == 3 && LocaleUtils.isNumericString(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVariant(String paramString) {
/* 618 */     int i = paramString.length();
/* 619 */     if (i >= 5 && i <= 8) {
/* 620 */       return LocaleUtils.isAlphaNumericString(paramString);
/*     */     }
/* 622 */     if (i == 4) {
/* 623 */       return (LocaleUtils.isNumeric(paramString.charAt(0)) && 
/* 624 */         LocaleUtils.isAlphaNumeric(paramString.charAt(1)) && 
/* 625 */         LocaleUtils.isAlphaNumeric(paramString.charAt(2)) && 
/* 626 */         LocaleUtils.isAlphaNumeric(paramString.charAt(3)));
/*     */     }
/* 628 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isExtensionSingleton(String paramString) {
/* 638 */     return (paramString.length() == 1 && 
/* 639 */       LocaleUtils.isAlphaString(paramString) && 
/* 640 */       !LocaleUtils.caseIgnoreMatch("x", paramString));
/*     */   }
/*     */   
/*     */   public static boolean isExtensionSingletonChar(char paramChar) {
/* 644 */     return isExtensionSingleton(String.valueOf(paramChar));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isExtensionSubtag(String paramString) {
/* 649 */     int i = paramString.length();
/* 650 */     return (i >= 2 && i <= 8 && LocaleUtils.isAlphaNumericString(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPrivateusePrefix(String paramString) {
/* 655 */     return (paramString.length() == 1 && 
/* 656 */       LocaleUtils.caseIgnoreMatch("x", paramString));
/*     */   }
/*     */   
/*     */   public static boolean isPrivateusePrefixChar(char paramChar) {
/* 660 */     return LocaleUtils.caseIgnoreMatch("x", String.valueOf(paramChar));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPrivateuseSubtag(String paramString) {
/* 665 */     int i = paramString.length();
/* 666 */     return (i >= 1 && i <= 8 && LocaleUtils.isAlphaNumericString(paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String canonicalizeLanguage(String paramString) {
/* 674 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeExtlang(String paramString) {
/* 678 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeScript(String paramString) {
/* 682 */     return LocaleUtils.toTitleString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeRegion(String paramString) {
/* 686 */     return LocaleUtils.toUpperString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeVariant(String paramString) {
/* 690 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeExtension(String paramString) {
/* 694 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeExtensionSingleton(String paramString) {
/* 698 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizeExtensionSubtag(String paramString) {
/* 702 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizePrivateuse(String paramString) {
/* 706 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */   
/*     */   public static String canonicalizePrivateuseSubtag(String paramString) {
/* 710 */     return LocaleUtils.toLowerString(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 715 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 717 */     if (this.language.length() > 0) {
/* 718 */       stringBuilder.append(this.language);
/*     */       
/* 720 */       for (String str : this.extlangs) {
/* 721 */         stringBuilder.append("-").append(str);
/*     */       }
/*     */       
/* 724 */       if (this.script.length() > 0) {
/* 725 */         stringBuilder.append("-").append(this.script);
/*     */       }
/*     */       
/* 728 */       if (this.region.length() > 0) {
/* 729 */         stringBuilder.append("-").append(this.region);
/*     */       }
/*     */       
/* 732 */       for (String str : this.variants) {
/* 733 */         stringBuilder.append("-").append(str);
/*     */       }
/*     */       
/* 736 */       for (String str : this.extensions) {
/* 737 */         stringBuilder.append("-").append(str);
/*     */       }
/*     */     } 
/* 740 */     if (this.privateuse.length() > 0) {
/* 741 */       if (stringBuilder.length() > 0) {
/* 742 */         stringBuilder.append("-");
/*     */       }
/* 744 */       stringBuilder.append(this.privateuse);
/*     */     } 
/*     */     
/* 747 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/LanguageTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */