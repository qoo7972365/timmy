/*     */ package javax.naming;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NameImpl
/*     */ {
/*     */   private static final byte LEFT_TO_RIGHT = 1;
/*     */   private static final byte RIGHT_TO_LEFT = 2;
/*     */   private static final byte FLAT = 0;
/*     */   private Vector<String> components;
/*  51 */   private byte syntaxDirection = 1;
/*  52 */   private String syntaxSeparator = "/";
/*  53 */   private String syntaxSeparator2 = null;
/*     */   private boolean syntaxCaseInsensitive = false;
/*     */   private boolean syntaxTrimBlanks = false;
/*  56 */   private String syntaxEscape = "\\";
/*  57 */   private String syntaxBeginQuote1 = "\"";
/*  58 */   private String syntaxEndQuote1 = "\"";
/*  59 */   private String syntaxBeginQuote2 = "'";
/*  60 */   private String syntaxEndQuote2 = "'";
/*  61 */   private String syntaxAvaSeparator = null;
/*  62 */   private String syntaxTypevalSeparator = null;
/*     */   
/*     */   private static final int STYLE_NONE = 0;
/*     */   
/*     */   private static final int STYLE_QUOTE1 = 1;
/*     */   
/*     */   private static final int STYLE_QUOTE2 = 2;
/*     */   
/*     */   private static final int STYLE_ESCAPE = 3;
/*     */   
/*  72 */   private int escapingStyle = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isA(String paramString1, int paramInt, String paramString2) {
/*  77 */     return (paramString2 != null && paramString1.startsWith(paramString2, paramInt));
/*     */   }
/*     */   
/*     */   private final boolean isMeta(String paramString, int paramInt) {
/*  81 */     return (isA(paramString, paramInt, this.syntaxEscape) || 
/*  82 */       isA(paramString, paramInt, this.syntaxBeginQuote1) || 
/*  83 */       isA(paramString, paramInt, this.syntaxBeginQuote2) || 
/*  84 */       isSeparator(paramString, paramInt));
/*     */   }
/*     */   
/*     */   private final boolean isSeparator(String paramString, int paramInt) {
/*  88 */     return (isA(paramString, paramInt, this.syntaxSeparator) || 
/*  89 */       isA(paramString, paramInt, this.syntaxSeparator2));
/*     */   }
/*     */   
/*     */   private final int skipSeparator(String paramString, int paramInt) {
/*  93 */     if (isA(paramString, paramInt, this.syntaxSeparator)) {
/*  94 */       paramInt += this.syntaxSeparator.length();
/*  95 */     } else if (isA(paramString, paramInt, this.syntaxSeparator2)) {
/*  96 */       paramInt += this.syntaxSeparator2.length();
/*     */     } 
/*  98 */     return paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int extractComp(String paramString, int paramInt1, int paramInt2, Vector<String> paramVector) throws InvalidNameException {
/* 105 */     boolean bool = true;
/* 106 */     boolean bool1 = false;
/* 107 */     StringBuffer stringBuffer = new StringBuffer(paramInt2);
/*     */     
/* 109 */     while (paramInt1 < paramInt2) {
/*     */       
/* 111 */       if (bool && ((bool1 = isA(paramString, paramInt1, this.syntaxBeginQuote1)) || 
/* 112 */         isA(paramString, paramInt1, this.syntaxBeginQuote2))) {
/*     */ 
/*     */         
/* 115 */         String str1 = bool1 ? this.syntaxBeginQuote1 : this.syntaxBeginQuote2;
/* 116 */         String str2 = bool1 ? this.syntaxEndQuote1 : this.syntaxEndQuote2;
/* 117 */         if (this.escapingStyle == 0) {
/* 118 */           this.escapingStyle = bool1 ? 1 : 2;
/*     */         }
/*     */ 
/*     */         
/* 122 */         paramInt1 += str1.length();
/* 123 */         for (; paramInt1 < paramInt2 && !paramString.startsWith(str2, paramInt1); 
/* 124 */           paramInt1++) {
/*     */ 
/*     */           
/* 127 */           if (isA(paramString, paramInt1, this.syntaxEscape) && 
/* 128 */             isA(paramString, paramInt1 + this.syntaxEscape.length(), str2)) {
/* 129 */             paramInt1 += this.syntaxEscape.length();
/*     */           }
/* 131 */           stringBuffer.append(paramString.charAt(paramInt1));
/*     */         } 
/*     */ 
/*     */         
/* 135 */         if (paramInt1 >= paramInt2) {
/* 136 */           throw new InvalidNameException(paramString + ": no close quote");
/*     */         }
/*     */ 
/*     */         
/* 140 */         paramInt1 += str2.length();
/*     */ 
/*     */         
/* 143 */         if (paramInt1 == paramInt2 || isSeparator(paramString, paramInt1)) {
/*     */           break;
/*     */         }
/*     */         
/* 147 */         throw new InvalidNameException(paramString + ": close quote appears before end of component");
/*     */       } 
/*     */       
/* 150 */       if (isSeparator(paramString, paramInt1)) {
/*     */         break;
/*     */       }
/* 153 */       if (isA(paramString, paramInt1, this.syntaxEscape)) {
/* 154 */         if (isMeta(paramString, paramInt1 + this.syntaxEscape.length())) {
/*     */ 
/*     */           
/* 157 */           paramInt1 += this.syntaxEscape.length();
/* 158 */           if (this.escapingStyle == 0) {
/* 159 */             this.escapingStyle = 3;
/*     */           }
/* 161 */         } else if (paramInt1 + this.syntaxEscape.length() >= paramInt2) {
/* 162 */           throw new InvalidNameException(paramString + ": unescaped " + this.syntaxEscape + " at end of component");
/*     */         }
/*     */       
/* 165 */       } else if (isA(paramString, paramInt1, this.syntaxTypevalSeparator) && ((
/* 166 */         bool1 = isA(paramString, paramInt1 + this.syntaxTypevalSeparator.length(), this.syntaxBeginQuote1)) || 
/* 167 */         isA(paramString, paramInt1 + this.syntaxTypevalSeparator.length(), this.syntaxBeginQuote2))) {
/*     */         
/* 169 */         String str1 = bool1 ? this.syntaxBeginQuote1 : this.syntaxBeginQuote2;
/* 170 */         String str2 = bool1 ? this.syntaxEndQuote1 : this.syntaxEndQuote2;
/*     */         
/* 172 */         paramInt1 += this.syntaxTypevalSeparator.length();
/* 173 */         stringBuffer.append(this.syntaxTypevalSeparator + str1);
/*     */ 
/*     */         
/* 176 */         paramInt1 += str1.length();
/* 177 */         for (; paramInt1 < paramInt2 && !paramString.startsWith(str2, paramInt1); 
/* 178 */           paramInt1++) {
/*     */ 
/*     */           
/* 181 */           if (isA(paramString, paramInt1, this.syntaxEscape) && 
/* 182 */             isA(paramString, paramInt1 + this.syntaxEscape.length(), str2)) {
/* 183 */             paramInt1 += this.syntaxEscape.length();
/*     */           }
/* 185 */           stringBuffer.append(paramString.charAt(paramInt1));
/*     */         } 
/*     */ 
/*     */         
/* 189 */         if (paramInt1 >= paramInt2) {
/* 190 */           throw new InvalidNameException(paramString + ": typeval no close quote");
/*     */         }
/*     */         
/* 193 */         paramInt1 += str2.length();
/* 194 */         stringBuffer.append(str2);
/*     */ 
/*     */         
/* 197 */         if (paramInt1 == paramInt2 || isSeparator(paramString, paramInt1)) {
/*     */           break;
/*     */         }
/* 200 */         throw new InvalidNameException(paramString.substring(paramInt1) + ": typeval close quote appears before end of component");
/*     */       } 
/*     */ 
/*     */       
/* 204 */       stringBuffer.append(paramString.charAt(paramInt1++));
/* 205 */       bool = false;
/*     */     } 
/*     */     
/* 208 */     if (this.syntaxDirection == 2) {
/* 209 */       paramVector.insertElementAt(stringBuffer.toString(), 0);
/*     */     } else {
/* 211 */       paramVector.addElement(stringBuffer.toString());
/* 212 */     }  return paramInt1;
/*     */   }
/*     */   
/*     */   private static boolean getBoolean(Properties paramProperties, String paramString) {
/* 216 */     return toBoolean(paramProperties.getProperty(paramString));
/*     */   }
/*     */   
/*     */   private static boolean toBoolean(String paramString) {
/* 220 */     return (paramString != null && paramString
/* 221 */       .toLowerCase(Locale.ENGLISH).equals("true"));
/*     */   }
/*     */ 
/*     */   
/*     */   private final void recordNamingConvention(Properties paramProperties) {
/* 226 */     String str = paramProperties.getProperty("jndi.syntax.direction", "flat");
/* 227 */     if (str.equals("left_to_right")) {
/* 228 */       this.syntaxDirection = 1;
/* 229 */     } else if (str.equals("right_to_left")) {
/* 230 */       this.syntaxDirection = 2;
/* 231 */     } else if (str.equals("flat")) {
/* 232 */       this.syntaxDirection = 0;
/*     */     } else {
/* 234 */       throw new IllegalArgumentException(str + "is not a valid value for the jndi.syntax.direction property");
/*     */     } 
/*     */ 
/*     */     
/* 238 */     if (this.syntaxDirection != 0) {
/* 239 */       this.syntaxSeparator = paramProperties.getProperty("jndi.syntax.separator");
/* 240 */       this.syntaxSeparator2 = paramProperties.getProperty("jndi.syntax.separator2");
/* 241 */       if (this.syntaxSeparator == null) {
/* 242 */         throw new IllegalArgumentException("jndi.syntax.separator property required for non-flat syntax");
/*     */       }
/*     */     } else {
/*     */       
/* 246 */       this.syntaxSeparator = null;
/*     */     } 
/* 248 */     this.syntaxEscape = paramProperties.getProperty("jndi.syntax.escape");
/*     */     
/* 250 */     this.syntaxCaseInsensitive = getBoolean(paramProperties, "jndi.syntax.ignorecase");
/* 251 */     this.syntaxTrimBlanks = getBoolean(paramProperties, "jndi.syntax.trimblanks");
/*     */     
/* 253 */     this.syntaxBeginQuote1 = paramProperties.getProperty("jndi.syntax.beginquote");
/* 254 */     this.syntaxEndQuote1 = paramProperties.getProperty("jndi.syntax.endquote");
/* 255 */     if (this.syntaxEndQuote1 == null && this.syntaxBeginQuote1 != null) {
/* 256 */       this.syntaxEndQuote1 = this.syntaxBeginQuote1;
/* 257 */     } else if (this.syntaxBeginQuote1 == null && this.syntaxEndQuote1 != null) {
/* 258 */       this.syntaxBeginQuote1 = this.syntaxEndQuote1;
/* 259 */     }  this.syntaxBeginQuote2 = paramProperties.getProperty("jndi.syntax.beginquote2");
/* 260 */     this.syntaxEndQuote2 = paramProperties.getProperty("jndi.syntax.endquote2");
/* 261 */     if (this.syntaxEndQuote2 == null && this.syntaxBeginQuote2 != null) {
/* 262 */       this.syntaxEndQuote2 = this.syntaxBeginQuote2;
/* 263 */     } else if (this.syntaxBeginQuote2 == null && this.syntaxEndQuote2 != null) {
/* 264 */       this.syntaxBeginQuote2 = this.syntaxEndQuote2;
/*     */     } 
/* 266 */     this.syntaxAvaSeparator = paramProperties.getProperty("jndi.syntax.separator.ava");
/* 267 */     this
/* 268 */       .syntaxTypevalSeparator = paramProperties.getProperty("jndi.syntax.separator.typeval");
/*     */   }
/*     */   
/*     */   NameImpl(Properties paramProperties) {
/* 272 */     if (paramProperties != null) {
/* 273 */       recordNamingConvention(paramProperties);
/*     */     }
/* 275 */     this.components = new Vector<>();
/*     */   }
/*     */   
/*     */   NameImpl(Properties paramProperties, String paramString) throws InvalidNameException {
/* 279 */     this(paramProperties);
/*     */     
/* 281 */     boolean bool1 = (this.syntaxDirection == 2) ? true : false;
/* 282 */     boolean bool2 = true;
/* 283 */     int i = paramString.length();
/*     */     
/* 285 */     for (int j = 0; j < i; ) {
/* 286 */       j = extractComp(paramString, j, i, this.components);
/*     */ 
/*     */ 
/*     */       
/* 290 */       String str = bool1 ? this.components.firstElement() : this.components.lastElement();
/* 291 */       if (str.length() >= 1) {
/* 292 */         bool2 = false;
/*     */       }
/*     */       
/* 295 */       if (j < i) {
/* 296 */         j = skipSeparator(paramString, j);
/* 297 */         if (j == i && !bool2) {
/*     */           
/* 299 */           if (bool1) {
/* 300 */             this.components.insertElementAt("", 0); continue;
/*     */           } 
/* 302 */           this.components.addElement("");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   NameImpl(Properties paramProperties, Enumeration<String> paramEnumeration) {
/* 310 */     this(paramProperties);
/*     */ 
/*     */     
/* 313 */     while (paramEnumeration.hasMoreElements()) {
/* 314 */       this.components.addElement(paramEnumeration.nextElement());
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
/*     */   private final String stringifyComp(String paramString) {
/* 341 */     int i = paramString.length();
/* 342 */     boolean bool1 = false, bool2 = false;
/* 343 */     String str1 = null, str2 = null;
/* 344 */     StringBuffer stringBuffer = new StringBuffer(i);
/*     */ 
/*     */ 
/*     */     
/* 348 */     if (this.syntaxSeparator != null && paramString
/* 349 */       .indexOf(this.syntaxSeparator) >= 0)
/* 350 */       if (this.syntaxBeginQuote1 != null) {
/* 351 */         str1 = this.syntaxBeginQuote1;
/* 352 */         str2 = this.syntaxEndQuote1;
/* 353 */       } else if (this.syntaxBeginQuote2 != null) {
/* 354 */         str1 = this.syntaxBeginQuote2;
/* 355 */         str2 = this.syntaxEndQuote2;
/* 356 */       } else if (this.syntaxEscape != null) {
/* 357 */         bool1 = true;
/*     */       }  
/* 359 */     if (this.syntaxSeparator2 != null && paramString
/* 360 */       .indexOf(this.syntaxSeparator2) >= 0) {
/* 361 */       if (this.syntaxBeginQuote1 != null) {
/* 362 */         if (str1 == null) {
/* 363 */           str1 = this.syntaxBeginQuote1;
/* 364 */           str2 = this.syntaxEndQuote1;
/*     */         } 
/* 366 */       } else if (this.syntaxBeginQuote2 != null) {
/* 367 */         if (str1 == null) {
/* 368 */           str1 = this.syntaxBeginQuote2;
/* 369 */           str2 = this.syntaxEndQuote2;
/*     */         } 
/* 371 */       } else if (this.syntaxEscape != null) {
/* 372 */         bool2 = true;
/*     */       } 
/*     */     }
/*     */     
/* 376 */     if (str1 != null) {
/*     */ 
/*     */       
/* 379 */       stringBuffer = stringBuffer.append(str1);
/*     */ 
/*     */ 
/*     */       
/* 383 */       for (int j = 0; j < i; ) {
/* 384 */         if (paramString.startsWith(str2, j)) {
/*     */           
/* 386 */           stringBuffer.append(this.syntaxEscape).append(str2);
/* 387 */           j += str2.length();
/*     */           continue;
/*     */         } 
/* 390 */         stringBuffer.append(paramString.charAt(j++));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 395 */       stringBuffer.append(str2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 406 */       boolean bool = true;
/* 407 */       for (int j = 0; j < i; ) {
/*     */         
/* 409 */         if (bool && isA(paramString, j, this.syntaxBeginQuote1)) {
/* 410 */           stringBuffer.append(this.syntaxEscape).append(this.syntaxBeginQuote1);
/* 411 */           j += this.syntaxBeginQuote1.length();
/* 412 */         } else if (bool && isA(paramString, j, this.syntaxBeginQuote2)) {
/* 413 */           stringBuffer.append(this.syntaxEscape).append(this.syntaxBeginQuote2);
/* 414 */           j += this.syntaxBeginQuote2.length();
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 419 */         else if (isA(paramString, j, this.syntaxEscape)) {
/* 420 */           if (j + this.syntaxEscape.length() >= i) {
/*     */             
/* 422 */             stringBuffer.append(this.syntaxEscape);
/* 423 */           } else if (isMeta(paramString, j + this.syntaxEscape.length())) {
/*     */             
/* 425 */             stringBuffer.append(this.syntaxEscape);
/*     */           } 
/* 427 */           stringBuffer.append(this.syntaxEscape);
/* 428 */           j += this.syntaxEscape.length();
/*     */ 
/*     */         
/*     */         }
/* 432 */         else if (bool1 && paramString.startsWith(this.syntaxSeparator, j)) {
/*     */           
/* 434 */           stringBuffer.append(this.syntaxEscape).append(this.syntaxSeparator);
/* 435 */           j += this.syntaxSeparator.length();
/* 436 */         } else if (bool2 && paramString
/* 437 */           .startsWith(this.syntaxSeparator2, j)) {
/*     */           
/* 439 */           stringBuffer.append(this.syntaxEscape).append(this.syntaxSeparator2);
/* 440 */           j += this.syntaxSeparator2.length();
/*     */         } else {
/*     */           
/* 443 */           stringBuffer.append(paramString.charAt(j++));
/*     */         } 
/* 445 */         bool = false;
/*     */       } 
/*     */     } 
/* 448 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 452 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/* 454 */     boolean bool = true;
/* 455 */     int i = this.components.size();
/*     */     
/* 457 */     for (byte b = 0; b < i; b++) {
/* 458 */       String str; if (this.syntaxDirection == 2) {
/*     */         
/* 460 */         str = stringifyComp(this.components.elementAt(i - 1 - b));
/*     */       } else {
/* 462 */         str = stringifyComp(this.components.elementAt(b));
/*     */       } 
/* 464 */       if (b != 0 && this.syntaxSeparator != null)
/* 465 */         stringBuffer.append(this.syntaxSeparator); 
/* 466 */       if (str.length() >= 1)
/* 467 */         bool = false; 
/* 468 */       stringBuffer = stringBuffer.append(str);
/*     */     } 
/* 470 */     if (bool && i >= 1 && this.syntaxSeparator != null)
/* 471 */       stringBuffer = stringBuffer.append(this.syntaxSeparator); 
/* 472 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 476 */     if (paramObject != null && paramObject instanceof NameImpl) {
/* 477 */       NameImpl nameImpl = (NameImpl)paramObject;
/* 478 */       if (nameImpl.size() == size()) {
/* 479 */         Enumeration<String> enumeration1 = getAll();
/* 480 */         Enumeration<String> enumeration2 = nameImpl.getAll();
/* 481 */         while (enumeration1.hasMoreElements()) {
/*     */           
/* 483 */           String str1 = enumeration1.nextElement();
/* 484 */           String str2 = enumeration2.nextElement();
/* 485 */           if (this.syntaxTrimBlanks) {
/* 486 */             str1 = str1.trim();
/* 487 */             str2 = str2.trim();
/*     */           } 
/* 489 */           if (this.syntaxCaseInsensitive) {
/* 490 */             if (!str1.equalsIgnoreCase(str2))
/* 491 */               return false;  continue;
/*     */           } 
/* 493 */           if (!str1.equals(str2)) {
/* 494 */             return false;
/*     */           }
/*     */         } 
/* 497 */         return true;
/*     */       } 
/*     */     } 
/* 500 */     return false;
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
/*     */   public int compareTo(NameImpl paramNameImpl) {
/* 512 */     if (this == paramNameImpl) {
/* 513 */       return 0;
/*     */     }
/*     */     
/* 516 */     int i = size();
/* 517 */     int j = paramNameImpl.size();
/* 518 */     int k = Math.min(i, j);
/*     */     
/* 520 */     byte b1 = 0, b2 = 0;
/*     */     
/* 522 */     while (k-- != 0) {
/* 523 */       int m; String str1 = get(b1++);
/* 524 */       String str2 = paramNameImpl.get(b2++);
/*     */ 
/*     */       
/* 527 */       if (this.syntaxTrimBlanks) {
/* 528 */         str1 = str1.trim();
/* 529 */         str2 = str2.trim();
/*     */       } 
/*     */ 
/*     */       
/* 533 */       if (this.syntaxCaseInsensitive) {
/* 534 */         m = str1.compareToIgnoreCase(str2);
/*     */       } else {
/* 536 */         m = str1.compareTo(str2);
/*     */       } 
/*     */       
/* 539 */       if (m != 0) {
/* 540 */         return m;
/*     */       }
/*     */     } 
/*     */     
/* 544 */     return i - j;
/*     */   }
/*     */   
/*     */   public int size() {
/* 548 */     return this.components.size();
/*     */   }
/*     */   
/*     */   public Enumeration<String> getAll() {
/* 552 */     return this.components.elements();
/*     */   }
/*     */   
/*     */   public String get(int paramInt) {
/* 556 */     return this.components.elementAt(paramInt);
/*     */   }
/*     */   
/*     */   public Enumeration<String> getPrefix(int paramInt) {
/* 560 */     if (paramInt < 0 || paramInt > size()) {
/* 561 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*     */     }
/* 563 */     return new NameImplEnumerator(this.components, 0, paramInt);
/*     */   }
/*     */   
/*     */   public Enumeration<String> getSuffix(int paramInt) {
/* 567 */     int i = size();
/* 568 */     if (paramInt < 0 || paramInt > i) {
/* 569 */       throw new ArrayIndexOutOfBoundsException(paramInt);
/*     */     }
/* 571 */     return new NameImplEnumerator(this.components, paramInt, i);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 575 */     return this.components.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean startsWith(int paramInt, Enumeration<String> paramEnumeration) {
/* 579 */     if (paramInt < 0 || paramInt > size()) {
/* 580 */       return false;
/*     */     }
/*     */     try {
/* 583 */       Enumeration<String> enumeration = getPrefix(paramInt);
/* 584 */       while (enumeration.hasMoreElements()) {
/* 585 */         String str1 = enumeration.nextElement();
/* 586 */         String str2 = paramEnumeration.nextElement();
/* 587 */         if (this.syntaxTrimBlanks) {
/* 588 */           str1 = str1.trim();
/* 589 */           str2 = str2.trim();
/*     */         } 
/* 591 */         if (this.syntaxCaseInsensitive) {
/* 592 */           if (!str1.equalsIgnoreCase(str2))
/* 593 */             return false;  continue;
/*     */         } 
/* 595 */         if (!str1.equals(str2)) {
/* 596 */           return false;
/*     */         }
/*     */       } 
/* 599 */     } catch (NoSuchElementException noSuchElementException) {
/* 600 */       return false;
/*     */     } 
/* 602 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean endsWith(int paramInt, Enumeration<String> paramEnumeration) {
/* 610 */     int i = size() - paramInt;
/* 611 */     if (i < 0 || i > size()) {
/* 612 */       return false;
/*     */     }
/*     */     try {
/* 615 */       Enumeration<String> enumeration = getSuffix(i);
/* 616 */       while (enumeration.hasMoreElements()) {
/* 617 */         String str1 = enumeration.nextElement();
/* 618 */         String str2 = paramEnumeration.nextElement();
/* 619 */         if (this.syntaxTrimBlanks) {
/* 620 */           str1 = str1.trim();
/* 621 */           str2 = str2.trim();
/*     */         } 
/* 623 */         if (this.syntaxCaseInsensitive) {
/* 624 */           if (!str1.equalsIgnoreCase(str2))
/* 625 */             return false;  continue;
/*     */         } 
/* 627 */         if (!str1.equals(str2)) {
/* 628 */           return false;
/*     */         }
/*     */       } 
/* 631 */     } catch (NoSuchElementException noSuchElementException) {
/* 632 */       return false;
/*     */     } 
/* 634 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addAll(Enumeration<String> paramEnumeration) throws InvalidNameException {
/* 638 */     boolean bool = false;
/* 639 */     while (paramEnumeration.hasMoreElements()) {
/*     */       try {
/* 641 */         String str = paramEnumeration.nextElement();
/* 642 */         if (size() > 0 && this.syntaxDirection == 0) {
/* 643 */           throw new InvalidNameException("A flat name can only have a single component");
/*     */         }
/*     */         
/* 646 */         this.components.addElement(str);
/* 647 */         bool = true;
/* 648 */       } catch (NoSuchElementException noSuchElementException) {
/*     */         break;
/*     */       } 
/*     */     } 
/* 652 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int paramInt, Enumeration<String> paramEnumeration) throws InvalidNameException {
/* 657 */     boolean bool = false;
/* 658 */     for (int i = paramInt; paramEnumeration.hasMoreElements(); i++) {
/*     */       try {
/* 660 */         String str = paramEnumeration.nextElement();
/* 661 */         if (size() > 0 && this.syntaxDirection == 0) {
/* 662 */           throw new InvalidNameException("A flat name can only have a single component");
/*     */         }
/*     */         
/* 665 */         this.components.insertElementAt(str, i);
/* 666 */         bool = true;
/* 667 */       } catch (NoSuchElementException noSuchElementException) {
/*     */         break;
/*     */       } 
/*     */     } 
/* 671 */     return bool;
/*     */   }
/*     */   
/*     */   public void add(String paramString) throws InvalidNameException {
/* 675 */     if (size() > 0 && this.syntaxDirection == 0) {
/* 676 */       throw new InvalidNameException("A flat name can only have a single component");
/*     */     }
/*     */     
/* 679 */     this.components.addElement(paramString);
/*     */   }
/*     */   
/*     */   public void add(int paramInt, String paramString) throws InvalidNameException {
/* 683 */     if (size() > 0 && this.syntaxDirection == 0) {
/* 684 */       throw new InvalidNameException("A flat name can only zero or one component");
/*     */     }
/*     */     
/* 687 */     this.components.insertElementAt(paramString, paramInt);
/*     */   }
/*     */   
/*     */   public Object remove(int paramInt) {
/* 691 */     Object object = this.components.elementAt(paramInt);
/* 692 */     this.components.removeElementAt(paramInt);
/* 693 */     return object;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 697 */     int i = 0;
/* 698 */     for (Enumeration<String> enumeration = getAll(); enumeration.hasMoreElements(); ) {
/* 699 */       String str = enumeration.nextElement();
/* 700 */       if (this.syntaxTrimBlanks) {
/* 701 */         str = str.trim();
/*     */       }
/* 703 */       if (this.syntaxCaseInsensitive) {
/* 704 */         str = str.toLowerCase(Locale.ENGLISH);
/*     */       }
/*     */       
/* 707 */       i += str.hashCode();
/*     */     } 
/* 709 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/naming/NameImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */