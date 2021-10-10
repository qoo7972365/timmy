/*     */ package javax.print;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MimeType
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -2785720609362367683L;
/*     */   private String[] myPieces;
/* 105 */   private transient String myStringValue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   private transient ParameterMapEntrySet myEntrySet = null;
/*     */   
/*     */   private static final int TOKEN_LEXEME = 0;
/*     */   private static final int QUOTED_STRING_LEXEME = 1;
/*     */   private static final int TSPECIAL_LEXEME = 2;
/* 115 */   private transient ParameterMap myParameterMap = null;
/*     */   private static final int EOF_LEXEME = 3;
/*     */   private static final int ILLEGAL_LEXEME = 4;
/*     */   
/*     */   private class ParameterMapEntry implements Map.Entry {
/*     */     private int myIndex;
/*     */     
/*     */     public ParameterMapEntry(int param1Int) {
/* 123 */       this.myIndex = param1Int;
/*     */     }
/*     */     public Object getKey() {
/* 126 */       return MimeType.this.myPieces[this.myIndex];
/*     */     }
/*     */     public Object getValue() {
/* 129 */       return MimeType.this.myPieces[this.myIndex + 1];
/*     */     }
/*     */     public Object setValue(Object param1Object) {
/* 132 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public boolean equals(Object param1Object) {
/* 135 */       return (param1Object != null && param1Object instanceof Map.Entry && 
/*     */         
/* 137 */         getKey().equals(((Map.Entry)param1Object).getKey()) && 
/* 138 */         getValue().equals(((Map.Entry)param1Object).getValue()));
/*     */     }
/*     */     public int hashCode() {
/* 141 */       return getKey().hashCode() ^ getValue().hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class ParameterMapEntrySetIterator
/*     */     implements Iterator
/*     */   {
/* 149 */     private int myIndex = 2;
/*     */     public boolean hasNext() {
/* 151 */       return (this.myIndex < MimeType.this.myPieces.length);
/*     */     }
/*     */     public Object next() {
/* 154 */       if (hasNext()) {
/* 155 */         MimeType.ParameterMapEntry parameterMapEntry = new MimeType.ParameterMapEntry(this.myIndex);
/* 156 */         this.myIndex += 2;
/* 157 */         return parameterMapEntry;
/*     */       } 
/* 159 */       throw new NoSuchElementException();
/*     */     }
/*     */     
/*     */     public void remove() {
/* 163 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     private ParameterMapEntrySetIterator() {} }
/*     */   
/*     */   private class ParameterMapEntrySet extends AbstractSet {
/*     */     private ParameterMapEntrySet() {}
/*     */     
/*     */     public Iterator iterator() {
/* 172 */       return new MimeType.ParameterMapEntrySetIterator();
/*     */     }
/*     */     public int size() {
/* 175 */       return (MimeType.this.myPieces.length - 2) / 2;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParameterMap
/*     */     extends AbstractMap {
/*     */     private ParameterMap() {}
/*     */     
/*     */     public Set entrySet() {
/* 184 */       if (MimeType.this.myEntrySet == null) {
/* 185 */         MimeType.this.myEntrySet = new MimeType.ParameterMapEntrySet();
/*     */       }
/* 187 */       return MimeType.this.myEntrySet;
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
/*     */   public MimeType(String paramString) {
/* 204 */     parse(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 212 */     return getStringValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMediaType() {
/* 219 */     return this.myPieces[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMediaSubtype() {
/* 226 */     return this.myPieces[1];
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
/*     */   public Map getParameterMap() {
/* 238 */     if (this.myParameterMap == null) {
/* 239 */       this.myParameterMap = new ParameterMap();
/*     */     }
/* 241 */     return this.myParameterMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 251 */     return getStringValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 258 */     return getStringValue().hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 278 */     return (paramObject != null && paramObject instanceof MimeType && 
/*     */       
/* 280 */       getStringValue().equals(((MimeType)paramObject).getStringValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getStringValue() {
/* 287 */     if (this.myStringValue == null) {
/* 288 */       StringBuffer stringBuffer = new StringBuffer();
/* 289 */       stringBuffer.append(this.myPieces[0]);
/* 290 */       stringBuffer.append('/');
/* 291 */       stringBuffer.append(this.myPieces[1]);
/* 292 */       int i = this.myPieces.length;
/* 293 */       for (byte b = 2; b < i; b += 2) {
/* 294 */         stringBuffer.append(';');
/* 295 */         stringBuffer.append(' ');
/* 296 */         stringBuffer.append(this.myPieces[b]);
/* 297 */         stringBuffer.append('=');
/* 298 */         stringBuffer.append(addQuotes(this.myPieces[b + 1]));
/*     */       } 
/* 300 */       this.myStringValue = stringBuffer.toString();
/*     */     } 
/* 302 */     return this.myStringValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LexicalAnalyzer
/*     */   {
/*     */     protected String mySource;
/*     */ 
/*     */     
/*     */     protected int mySourceLength;
/*     */ 
/*     */     
/*     */     protected int myCurrentIndex;
/*     */     
/*     */     protected int myLexemeType;
/*     */     
/*     */     protected int myLexemeBeginIndex;
/*     */     
/*     */     protected int myLexemeEndIndex;
/*     */ 
/*     */     
/*     */     public LexicalAnalyzer(String param1String) {
/* 325 */       this.mySource = param1String;
/* 326 */       this.mySourceLength = param1String.length();
/* 327 */       this.myCurrentIndex = 0;
/* 328 */       nextLexeme();
/*     */     }
/*     */     
/*     */     public int getLexemeType() {
/* 332 */       return this.myLexemeType;
/*     */     }
/*     */     
/*     */     public String getLexeme() {
/* 336 */       return (this.myLexemeBeginIndex >= this.mySourceLength) ? null : this.mySource
/*     */         
/* 338 */         .substring(this.myLexemeBeginIndex, this.myLexemeEndIndex);
/*     */     }
/*     */     
/*     */     public char getLexemeFirstCharacter() {
/* 342 */       return (this.myLexemeBeginIndex >= this.mySourceLength) ? Character.MIN_VALUE : this.mySource
/*     */         
/* 344 */         .charAt(this.myLexemeBeginIndex);
/*     */     }
/*     */     
/*     */     public void nextLexeme() {
/* 348 */       byte b = 0;
/* 349 */       byte b1 = 0;
/*     */       
/* 351 */       while (b) {
/* 352 */         char c; switch (b) {
/*     */           
/*     */           case false:
/* 355 */             if (this.myCurrentIndex >= this.mySourceLength) {
/* 356 */               this.myLexemeType = 3;
/* 357 */               this.myLexemeBeginIndex = this.mySourceLength;
/* 358 */               this.myLexemeEndIndex = this.mySourceLength;
/* 359 */               b = -1; continue;
/*     */             } 
/* 361 */             if (Character.isWhitespace(c = this.mySource.charAt(this.myCurrentIndex++))) {
/* 362 */               b = 0; continue;
/* 363 */             }  if (c == '"') {
/* 364 */               this.myLexemeType = 1;
/* 365 */               this.myLexemeBeginIndex = this.myCurrentIndex;
/* 366 */               b = 1; continue;
/* 367 */             }  if (c == '(') {
/* 368 */               b1++;
/* 369 */               b = 3; continue;
/* 370 */             }  if (c == '/' || c == ';' || c == '=' || c == ')' || c == '<' || c == '>' || c == '@' || c == ',' || c == ':' || c == '\\' || c == '[' || c == ']' || c == '?') {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 375 */               this.myLexemeType = 2;
/* 376 */               this.myLexemeBeginIndex = this.myCurrentIndex - 1;
/* 377 */               this.myLexemeEndIndex = this.myCurrentIndex;
/* 378 */               b = -1; continue;
/*     */             } 
/* 380 */             this.myLexemeType = 0;
/* 381 */             this.myLexemeBeginIndex = this.myCurrentIndex - 1;
/* 382 */             b = 5;
/*     */ 
/*     */ 
/*     */           
/*     */           case true:
/* 387 */             if (this.myCurrentIndex >= this.mySourceLength) {
/* 388 */               this.myLexemeType = 4;
/* 389 */               this.myLexemeBeginIndex = this.mySourceLength;
/* 390 */               this.myLexemeEndIndex = this.mySourceLength;
/* 391 */               b = -1; continue;
/* 392 */             }  if ((c = this.mySource.charAt(this.myCurrentIndex++)) == '"') {
/* 393 */               this.myLexemeEndIndex = this.myCurrentIndex - 1;
/* 394 */               b = -1; continue;
/* 395 */             }  if (c == '\\') {
/* 396 */               b = 2; continue;
/*     */             } 
/* 398 */             b = 1;
/*     */ 
/*     */ 
/*     */           
/*     */           case true:
/* 403 */             if (this.myCurrentIndex >= this.mySourceLength) {
/* 404 */               this.myLexemeType = 4;
/* 405 */               this.myLexemeBeginIndex = this.mySourceLength;
/* 406 */               this.myLexemeEndIndex = this.mySourceLength;
/* 407 */               b = -1; continue;
/*     */             } 
/* 409 */             this.myCurrentIndex++;
/* 410 */             b = 1;
/*     */           
/*     */           case true:
/* 413 */             if (this.myCurrentIndex >= this.mySourceLength) {
/* 414 */               this.myLexemeType = 4;
/* 415 */               this.myLexemeBeginIndex = this.mySourceLength;
/* 416 */               this.myLexemeEndIndex = this.mySourceLength;
/* 417 */               b = -1; continue;
/* 418 */             }  if ((c = this.mySource.charAt(this.myCurrentIndex++)) == '(') {
/* 419 */               b1++;
/* 420 */               b = 3; continue;
/* 421 */             }  if (c == ')') {
/* 422 */               b1--;
/* 423 */               b = (b1 == 0) ? 0 : 3; continue;
/* 424 */             }  if (c == '\\') {
/* 425 */               b = 4; continue;
/* 426 */             }  b = 3;
/*     */ 
/*     */ 
/*     */           
/*     */           case true:
/* 431 */             if (this.myCurrentIndex >= this.mySourceLength) {
/* 432 */               this.myLexemeType = 4;
/* 433 */               this.myLexemeBeginIndex = this.mySourceLength;
/* 434 */               this.myLexemeEndIndex = this.mySourceLength;
/* 435 */               b = -1; continue;
/*     */             } 
/* 437 */             this.myCurrentIndex++;
/* 438 */             b = 3;
/*     */ 
/*     */ 
/*     */           
/*     */           case true:
/* 443 */             if (this.myCurrentIndex >= this.mySourceLength) {
/* 444 */               this.myLexemeEndIndex = this.myCurrentIndex;
/* 445 */               b = -1; continue;
/*     */             } 
/* 447 */             if (Character.isWhitespace(c = this.mySource.charAt(this.myCurrentIndex++))) {
/* 448 */               this.myLexemeEndIndex = this.myCurrentIndex - 1;
/* 449 */               b = -1; continue;
/* 450 */             }  if (c == '"' || c == '(' || c == '/' || c == ';' || c == '=' || c == ')' || c == '<' || c == '>' || c == '@' || c == ',' || c == ':' || c == '\\' || c == '[' || c == ']' || c == '?') {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 456 */               this.myLexemeEndIndex = --this.myCurrentIndex;
/* 457 */               b = -1; continue;
/*     */             } 
/* 459 */             b = 5;
/*     */         } 
/*     */       } 
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
/*     */   private static String toUnicodeLowerCase(String paramString) {
/* 478 */     int i = paramString.length();
/* 479 */     char[] arrayOfChar = new char[i];
/* 480 */     for (byte b = 0; b < i; b++) {
/* 481 */       arrayOfChar[b] = Character.toLowerCase(paramString.charAt(b));
/*     */     }
/* 483 */     return new String(arrayOfChar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String removeBackslashes(String paramString) {
/* 490 */     int i = paramString.length();
/* 491 */     char[] arrayOfChar = new char[i];
/*     */     
/* 493 */     byte b2 = 0;
/*     */     
/* 495 */     for (byte b1 = 0; b1 < i; b1++) {
/* 496 */       char c = paramString.charAt(b1);
/* 497 */       if (c == '\\') {
/* 498 */         c = paramString.charAt(++b1);
/*     */       }
/* 500 */       arrayOfChar[b2++] = c;
/*     */     } 
/* 502 */     return new String(arrayOfChar, 0, b2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String addQuotes(String paramString) {
/* 510 */     int i = paramString.length();
/*     */ 
/*     */     
/* 513 */     StringBuffer stringBuffer = new StringBuffer(i + 2);
/* 514 */     stringBuffer.append('"');
/* 515 */     for (byte b = 0; b < i; b++) {
/* 516 */       char c = paramString.charAt(b);
/* 517 */       if (c == '"') {
/* 518 */         stringBuffer.append('\\');
/*     */       }
/* 520 */       stringBuffer.append(c);
/*     */     } 
/* 522 */     stringBuffer.append('"');
/* 523 */     return stringBuffer.toString();
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
/*     */   private void parse(String paramString) {
/* 546 */     if (paramString == null) {
/* 547 */       throw new NullPointerException();
/*     */     }
/* 549 */     LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(paramString);
/*     */     
/* 551 */     Vector<String> vector = new Vector();
/* 552 */     boolean bool1 = false;
/* 553 */     boolean bool2 = false;
/*     */ 
/*     */     
/* 556 */     if (lexicalAnalyzer.getLexemeType() == 0) {
/* 557 */       String str = toUnicodeLowerCase(lexicalAnalyzer.getLexeme());
/* 558 */       vector.add(str);
/* 559 */       lexicalAnalyzer.nextLexeme();
/* 560 */       bool1 = str.equals("text");
/*     */     } else {
/* 562 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 565 */     if (lexicalAnalyzer.getLexemeType() == 2 && lexicalAnalyzer
/* 566 */       .getLexemeFirstCharacter() == '/') {
/* 567 */       lexicalAnalyzer.nextLexeme();
/*     */     } else {
/* 569 */       throw new IllegalArgumentException();
/*     */     } 
/* 571 */     if (lexicalAnalyzer.getLexemeType() == 0) {
/* 572 */       vector.add(toUnicodeLowerCase(lexicalAnalyzer.getLexeme()));
/* 573 */       lexicalAnalyzer.nextLexeme();
/*     */     } else {
/* 575 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 578 */     while (lexicalAnalyzer.getLexemeType() == 2 && lexicalAnalyzer
/* 579 */       .getLexemeFirstCharacter() == ';') {
/*     */       
/* 581 */       lexicalAnalyzer.nextLexeme();
/*     */ 
/*     */       
/* 584 */       if (lexicalAnalyzer.getLexemeType() == 0) {
/* 585 */         String str = toUnicodeLowerCase(lexicalAnalyzer.getLexeme());
/* 586 */         vector.add(str);
/* 587 */         lexicalAnalyzer.nextLexeme();
/* 588 */         bool2 = str.equals("charset");
/*     */       } else {
/* 590 */         throw new IllegalArgumentException();
/*     */       } 
/*     */ 
/*     */       
/* 594 */       if (lexicalAnalyzer.getLexemeType() == 2 && lexicalAnalyzer
/* 595 */         .getLexemeFirstCharacter() == '=') {
/* 596 */         lexicalAnalyzer.nextLexeme();
/*     */       } else {
/* 598 */         throw new IllegalArgumentException();
/*     */       } 
/*     */ 
/*     */       
/* 602 */       if (lexicalAnalyzer.getLexemeType() == 0) {
/* 603 */         String str = lexicalAnalyzer.getLexeme();
/* 604 */         vector.add((bool1 && bool2) ? 
/* 605 */             toUnicodeLowerCase(str) : str);
/*     */         
/* 607 */         lexicalAnalyzer.nextLexeme(); continue;
/* 608 */       }  if (lexicalAnalyzer.getLexemeType() == 1) {
/* 609 */         String str = removeBackslashes(lexicalAnalyzer.getLexeme());
/* 610 */         vector.add((bool1 && bool2) ? 
/* 611 */             toUnicodeLowerCase(str) : str);
/*     */         
/* 613 */         lexicalAnalyzer.nextLexeme(); continue;
/*     */       } 
/* 615 */       throw new IllegalArgumentException();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 620 */     if (lexicalAnalyzer.getLexemeType() != 3) {
/* 621 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 625 */     int i = vector.size();
/* 626 */     this.myPieces = vector.<String>toArray(new String[i]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 631 */     for (byte b = 4; b < i; b += 2) {
/* 632 */       byte b1 = 2;
/* 633 */       while (b1 < b && this.myPieces[b1].compareTo(this.myPieces[b]) <= 0) {
/* 634 */         b1 += 2;
/*     */       }
/* 636 */       while (b1 < b) {
/* 637 */         String str = this.myPieces[b1];
/* 638 */         this.myPieces[b1] = this.myPieces[b];
/* 639 */         this.myPieces[b] = str;
/* 640 */         str = this.myPieces[b1 + 1];
/* 641 */         this.myPieces[b1 + 1] = this.myPieces[b + 1];
/* 642 */         this.myPieces[b + 1] = str;
/* 643 */         b1 += 2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/MimeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */