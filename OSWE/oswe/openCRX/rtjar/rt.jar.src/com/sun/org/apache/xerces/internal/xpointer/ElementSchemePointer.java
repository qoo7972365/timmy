/*     */ package com.sun.org.apache.xerces.internal.xpointer;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolTable;
/*     */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*     */ import com.sun.org.apache.xerces.internal.xni.Augmentations;
/*     */ import com.sun.org.apache.xerces.internal.xni.QName;
/*     */ import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
/*     */ import com.sun.org.apache.xerces.internal.xni.XNIException;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ElementSchemePointer
/*     */   implements XPointerPart
/*     */ {
/*     */   private String fSchemeName;
/*     */   private String fSchemeData;
/*     */   private String fShortHandPointerName;
/*     */   private boolean fIsResolveElement = false;
/*     */   private boolean fIsElementFound = false;
/*     */   private boolean fWasOnlyEmptyElementFound = false;
/*     */   boolean fIsShortHand = false;
/*  71 */   int fFoundDepth = 0;
/*     */ 
/*     */   
/*     */   private int[] fChildSequence;
/*     */ 
/*     */   
/*  77 */   private int fCurrentChildPosition = 1;
/*     */ 
/*     */   
/*  80 */   private int fCurrentChildDepth = 0;
/*     */ 
/*     */   
/*     */   private int[] fCurrentChildSequence;
/*     */ 
/*     */   
/*     */   private boolean fIsFragmentResolved = false;
/*     */ 
/*     */   
/*     */   private ShortHandPointer fShortHandPointer;
/*     */ 
/*     */   
/*     */   protected XMLErrorReporter fErrorReporter;
/*     */ 
/*     */   
/*     */   protected XMLErrorHandler fErrorHandler;
/*     */ 
/*     */   
/*     */   private SymbolTable fSymbolTable;
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementSchemePointer() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public ElementSchemePointer(SymbolTable symbolTable) {
/* 107 */     this.fSymbolTable = symbolTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public ElementSchemePointer(SymbolTable symbolTable, XMLErrorReporter errorReporter) {
/* 112 */     this.fSymbolTable = symbolTable;
/* 113 */     this.fErrorReporter = errorReporter;
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
/*     */   public void parseXPointer(String xpointer) throws XNIException {
/* 129 */     init();
/*     */ 
/*     */     
/* 132 */     Tokens tokens = new Tokens(this.fSymbolTable);
/*     */ 
/*     */     
/* 135 */     Scanner scanner = new Scanner(this.fSymbolTable)
/*     */       {
/*     */         protected void addToken(ElementSchemePointer.Tokens tokens, int token) throws XNIException {
/* 138 */           if (token == 1 || token == 0) {
/*     */             
/* 140 */             super.addToken(tokens, token);
/*     */             return;
/*     */           } 
/* 143 */           ElementSchemePointer.this.reportError("InvalidElementSchemeToken", new Object[] {
/* 144 */                 ElementSchemePointer.Tokens.access$200(tokens, token)
/*     */               });
/*     */         }
/*     */       };
/*     */     
/* 149 */     int length = xpointer.length();
/* 150 */     boolean success = scanner.scanExpr(this.fSymbolTable, tokens, xpointer, 0, length);
/*     */ 
/*     */     
/* 153 */     if (!success) {
/* 154 */       reportError("InvalidElementSchemeXPointer", new Object[] { xpointer });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     int[] tmpChildSequence = new int[tokens.getTokenCount() / 2 + 1];
/*     */ 
/*     */     
/* 163 */     int i = 0;
/*     */ 
/*     */     
/* 166 */     while (tokens.hasMore()) {
/* 167 */       int token = tokens.nextToken();
/*     */       
/* 169 */       switch (token) {
/*     */ 
/*     */ 
/*     */         
/*     */         case 0:
/* 174 */           token = tokens.nextToken();
/* 175 */           this.fShortHandPointerName = tokens.getTokenString(token);
/*     */ 
/*     */           
/* 178 */           this.fShortHandPointer = new ShortHandPointer(this.fSymbolTable);
/* 179 */           this.fShortHandPointer.setSchemeName(this.fShortHandPointerName);
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 1:
/* 184 */           tmpChildSequence[i] = tokens.nextToken();
/* 185 */           i++;
/*     */           continue;
/*     */       } 
/*     */ 
/*     */       
/* 190 */       reportError("InvalidElementSchemeXPointer", new Object[] { xpointer });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     this.fChildSequence = new int[i];
/* 197 */     this.fCurrentChildSequence = new int[i];
/* 198 */     System.arraycopy(tmpChildSequence, 0, this.fChildSequence, 0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchemeName() {
/* 207 */     return this.fSchemeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSchemeData() {
/* 216 */     return this.fSchemeData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchemeName(String schemeName) {
/* 225 */     this.fSchemeName = schemeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchemeData(String schemeData) {
/* 235 */     this.fSchemeData = schemeData;
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
/*     */   public boolean resolveXPointer(QName element, XMLAttributes attributes, Augmentations augs, int event) throws XNIException {
/* 248 */     boolean isShortHandPointerResolved = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     if (this.fShortHandPointerName != null) {
/*     */       
/* 255 */       isShortHandPointerResolved = this.fShortHandPointer.resolveXPointer(element, attributes, augs, event);
/*     */       
/* 257 */       if (isShortHandPointerResolved) {
/* 258 */         this.fIsResolveElement = true;
/* 259 */         this.fIsShortHand = true;
/*     */       } else {
/* 261 */         this.fIsResolveElement = false;
/*     */       } 
/*     */     } else {
/* 264 */       this.fIsResolveElement = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 269 */     if (this.fChildSequence.length > 0) {
/* 270 */       this.fIsFragmentResolved = matchChildSequence(element, event);
/* 271 */     } else if (isShortHandPointerResolved && this.fChildSequence.length <= 0) {
/*     */       
/* 273 */       this.fIsFragmentResolved = isShortHandPointerResolved;
/*     */     } else {
/* 275 */       this.fIsFragmentResolved = false;
/*     */     } 
/*     */     
/* 278 */     return this.fIsFragmentResolved;
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
/*     */   protected boolean matchChildSequence(QName element, int event) throws XNIException {
/* 294 */     if (this.fCurrentChildDepth >= this.fCurrentChildSequence.length) {
/* 295 */       int[] tmpCurrentChildSequence = new int[this.fCurrentChildSequence.length];
/* 296 */       System.arraycopy(this.fCurrentChildSequence, 0, tmpCurrentChildSequence, 0, this.fCurrentChildSequence.length);
/*     */ 
/*     */ 
/*     */       
/* 300 */       this.fCurrentChildSequence = new int[this.fCurrentChildDepth * 2];
/* 301 */       System.arraycopy(tmpCurrentChildSequence, 0, this.fCurrentChildSequence, 0, tmpCurrentChildSequence.length);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 306 */     if (this.fIsResolveElement) {
/*     */       
/* 308 */       this.fWasOnlyEmptyElementFound = false;
/* 309 */       if (event == 0) {
/* 310 */         this.fCurrentChildSequence[this.fCurrentChildDepth] = this.fCurrentChildPosition;
/* 311 */         this.fCurrentChildDepth++;
/*     */ 
/*     */         
/* 314 */         this.fCurrentChildPosition = 1;
/*     */ 
/*     */         
/* 317 */         if (this.fCurrentChildDepth <= this.fFoundDepth || this.fFoundDepth == 0) {
/* 318 */           if (checkMatch()) {
/* 319 */             this.fIsElementFound = true;
/* 320 */             this.fFoundDepth = this.fCurrentChildDepth;
/*     */           } else {
/* 322 */             this.fIsElementFound = false;
/* 323 */             this.fFoundDepth = 0;
/*     */           }
/*     */         
/*     */         }
/* 327 */       } else if (event == 1) {
/* 328 */         if (this.fCurrentChildDepth == this.fFoundDepth) {
/* 329 */           this.fIsElementFound = true;
/* 330 */         } else if ((this.fCurrentChildDepth < this.fFoundDepth && this.fFoundDepth != 0) || (this.fCurrentChildDepth > this.fFoundDepth && this.fFoundDepth == 0)) {
/*     */ 
/*     */           
/* 333 */           this.fIsElementFound = false;
/*     */         } 
/*     */ 
/*     */         
/* 337 */         this.fCurrentChildSequence[this.fCurrentChildDepth] = 0;
/*     */         
/* 339 */         this.fCurrentChildDepth--;
/* 340 */         this.fCurrentChildPosition = this.fCurrentChildSequence[this.fCurrentChildDepth] + 1;
/*     */       }
/* 342 */       else if (event == 2) {
/*     */         
/* 344 */         this.fCurrentChildSequence[this.fCurrentChildDepth] = this.fCurrentChildPosition;
/* 345 */         this.fCurrentChildPosition++;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 350 */         if (checkMatch()) {
/* 351 */           this.fIsElementFound = true;
/* 352 */           this.fWasOnlyEmptyElementFound = true;
/*     */         } else {
/* 354 */           this.fIsElementFound = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 361 */     return this.fIsElementFound;
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
/*     */   protected boolean checkMatch() {
/* 374 */     if (!this.fIsShortHand) {
/*     */ 
/*     */       
/* 377 */       if (this.fChildSequence.length <= this.fCurrentChildDepth + 1) {
/*     */         
/* 379 */         for (int i = 0; i < this.fChildSequence.length; i++) {
/* 380 */           if (this.fChildSequence[i] != this.fCurrentChildSequence[i]) {
/* 381 */             return false;
/*     */           }
/*     */         } 
/*     */       } else {
/* 385 */         return false;
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 391 */     else if (this.fChildSequence.length <= this.fCurrentChildDepth + 1) {
/*     */       
/* 393 */       for (int i = 0; i < this.fChildSequence.length; i++) {
/*     */         
/* 395 */         if (this.fCurrentChildSequence.length < i + 2) {
/* 396 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 400 */         if (this.fChildSequence[i] != this.fCurrentChildSequence[i + 1]) {
/* 401 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 405 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 410 */     return true;
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
/*     */   public boolean isFragmentResolved() throws XNIException {
/* 422 */     return this.fIsFragmentResolved;
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
/*     */   public boolean isChildFragmentResolved() {
/* 434 */     if (this.fIsShortHand && this.fShortHandPointer != null && this.fChildSequence.length <= 0) {
/* 435 */       return this.fShortHandPointer.isChildFragmentResolved();
/*     */     }
/* 437 */     return this.fWasOnlyEmptyElementFound ? (!this.fWasOnlyEmptyElementFound) : ((this.fIsFragmentResolved && this.fCurrentChildDepth >= this.fFoundDepth));
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
/*     */   protected void reportError(String key, Object[] arguments) throws XNIException {
/* 450 */     throw new XNIException(this.fErrorReporter
/* 451 */         .getMessageFormatter("http://www.w3.org/TR/XPTR")
/* 452 */         .formatMessage(this.fErrorReporter.getLocale(), key, arguments));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initErrorReporter() {
/* 459 */     if (this.fErrorReporter == null) {
/* 460 */       this.fErrorReporter = new XMLErrorReporter();
/*     */     }
/* 462 */     if (this.fErrorHandler == null) {
/* 463 */       this.fErrorHandler = new XPointerErrorHandler();
/*     */     }
/* 465 */     this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/XPTR", new XPointerMessageFormatter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 474 */     this.fSchemeName = null;
/* 475 */     this.fSchemeData = null;
/* 476 */     this.fShortHandPointerName = null;
/* 477 */     this.fIsResolveElement = false;
/* 478 */     this.fIsElementFound = false;
/* 479 */     this.fWasOnlyEmptyElementFound = false;
/* 480 */     this.fFoundDepth = 0;
/* 481 */     this.fCurrentChildPosition = 1;
/* 482 */     this.fCurrentChildDepth = 0;
/* 483 */     this.fIsFragmentResolved = false;
/* 484 */     this.fShortHandPointer = null;
/*     */     
/* 486 */     initErrorReporter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class Tokens
/*     */   {
/*     */     private static final int XPTRTOKEN_ELEM_NCNAME = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int XPTRTOKEN_ELEM_CHILD = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 514 */     private final String[] fgTokenNames = new String[] { "XPTRTOKEN_ELEM_NCNAME", "XPTRTOKEN_ELEM_CHILD" };
/*     */ 
/*     */     
/*     */     private static final int INITIAL_TOKEN_COUNT = 256;
/*     */ 
/*     */     
/* 520 */     private int[] fTokens = new int[256];
/*     */     
/* 522 */     private int fTokenCount = 0;
/*     */ 
/*     */     
/*     */     private int fCurrentTokenIndex;
/*     */     
/*     */     private SymbolTable fSymbolTable;
/*     */     
/* 529 */     private Hashtable fTokenNames = new Hashtable<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Tokens(SymbolTable symbolTable) {
/* 537 */       this.fSymbolTable = symbolTable;
/*     */       
/* 539 */       this.fTokenNames.put(new Integer(0), "XPTRTOKEN_ELEM_NCNAME");
/*     */       
/* 541 */       this.fTokenNames.put(new Integer(1), "XPTRTOKEN_ELEM_CHILD");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String getTokenString(int token) {
/* 551 */       return (String)this.fTokenNames.get(new Integer(token));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Integer getToken(int token) {
/* 560 */       return (Integer)this.fTokenNames.get(new Integer(token));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void addToken(String tokenStr) {
/* 569 */       Integer tokenInt = (Integer)this.fTokenNames.get(tokenStr);
/* 570 */       if (tokenInt == null) {
/* 571 */         tokenInt = new Integer(this.fTokenNames.size());
/* 572 */         this.fTokenNames.put(tokenInt, tokenStr);
/*     */       } 
/* 574 */       addToken(tokenInt.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void addToken(int token) {
/*     */       try {
/* 584 */         this.fTokens[this.fTokenCount] = token;
/* 585 */       } catch (ArrayIndexOutOfBoundsException ex) {
/* 586 */         int[] oldList = this.fTokens;
/* 587 */         this.fTokens = new int[this.fTokenCount << 1];
/* 588 */         System.arraycopy(oldList, 0, this.fTokens, 0, this.fTokenCount);
/* 589 */         this.fTokens[this.fTokenCount] = token;
/*     */       } 
/* 591 */       this.fTokenCount++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void rewind() {
/* 598 */       this.fCurrentTokenIndex = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hasMore() {
/* 606 */       return (this.fCurrentTokenIndex < this.fTokenCount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int nextToken() throws XNIException {
/* 617 */       if (this.fCurrentTokenIndex == this.fTokenCount)
/* 618 */         ElementSchemePointer.this.reportError("XPointerElementSchemeProcessingError", null); 
/* 619 */       return this.fTokens[this.fCurrentTokenIndex++];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int peekToken() throws XNIException {
/* 630 */       if (this.fCurrentTokenIndex == this.fTokenCount)
/* 631 */         ElementSchemePointer.this.reportError("XPointerElementSchemeProcessingError", null); 
/* 632 */       return this.fTokens[this.fCurrentTokenIndex];
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
/*     */     private String nextTokenAsString() throws XNIException {
/* 644 */       String s = getTokenString(nextToken());
/* 645 */       if (s == null)
/* 646 */         ElementSchemePointer.this.reportError("XPointerElementSchemeProcessingError", null); 
/* 647 */       return s;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int getTokenCount() {
/* 655 */       return this.fTokenCount;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Scanner
/*     */   {
/*     */     private static final byte CHARTYPE_INVALID = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_OTHER = 1;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_MINUS = 2;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_PERIOD = 3;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_SLASH = 4;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_DIGIT = 5;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_LETTER = 6;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_UNDERSCORE = 7;
/*     */ 
/*     */     
/*     */     private static final byte CHARTYPE_NONASCII = 8;
/*     */ 
/*     */     
/* 692 */     private final byte[] fASCIICharMap = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 7, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 1, 1, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private SymbolTable fSymbolTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Scanner(SymbolTable symbolTable) {
/* 721 */       this.fSymbolTable = symbolTable;
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
/*     */     
/*     */     private boolean scanExpr(SymbolTable symbolTable, ElementSchemePointer.Tokens tokens, String data, int currentOffset, int endOffset) throws XNIException {
/* 735 */       String nameHandle = null;
/*     */ 
/*     */       
/* 738 */       while (currentOffset != endOffset) {
/*     */ 
/*     */ 
/*     */         
/* 742 */         int nameOffset, child, ch = data.charAt(currentOffset);
/* 743 */         byte chartype = (ch >= 128) ? 8 : this.fASCIICharMap[ch];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 751 */         switch (chartype) {
/*     */ 
/*     */           
/*     */           case 4:
/* 755 */             if (++currentOffset == endOffset) {
/* 756 */               return false;
/*     */             }
/*     */             
/* 759 */             addToken(tokens, 1);
/* 760 */             ch = data.charAt(currentOffset);
/*     */ 
/*     */             
/* 763 */             child = 0;
/* 764 */             while (ch >= 48 && ch <= 57) {
/* 765 */               child = child * 10 + ch - 48;
/* 766 */               if (++currentOffset == endOffset) {
/*     */                 break;
/*     */               }
/* 769 */               ch = data.charAt(currentOffset);
/*     */             } 
/*     */ 
/*     */             
/* 773 */             if (child == 0) {
/* 774 */               ElementSchemePointer.this.reportError("InvalidChildSequenceCharacter", new Object[] { new Character((char)ch) });
/*     */               
/* 776 */               return false;
/*     */             } 
/*     */             
/* 779 */             tokens.addToken(child);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/*     */           case 2:
/*     */           case 3:
/*     */           case 5:
/*     */           case 6:
/*     */           case 7:
/*     */           case 8:
/* 791 */             nameOffset = currentOffset;
/* 792 */             currentOffset = scanNCName(data, endOffset, currentOffset);
/*     */             
/* 794 */             if (currentOffset == nameOffset) {
/*     */               
/* 796 */               ElementSchemePointer.this.reportError("InvalidNCNameInElementSchemeData", new Object[] { data });
/*     */               
/* 798 */               return false;
/*     */             } 
/*     */             
/* 801 */             if (currentOffset < endOffset) {
/* 802 */               ch = data.charAt(currentOffset);
/*     */             } else {
/* 804 */               ch = -1;
/*     */             } 
/*     */             
/* 807 */             nameHandle = symbolTable.addSymbol(data.substring(nameOffset, currentOffset));
/*     */             
/* 809 */             addToken(tokens, 0);
/* 810 */             tokens.addToken(nameHandle);
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 815 */       return true;
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
/*     */     
/*     */     private int scanNCName(String data, int endOffset, int currentOffset) {
/* 829 */       int ch = data.charAt(currentOffset);
/* 830 */       if (ch >= 128) {
/* 831 */         if (!XMLChar.isNameStart(ch)) {
/* 832 */           return currentOffset;
/*     */         }
/*     */       } else {
/* 835 */         byte chartype = this.fASCIICharMap[ch];
/* 836 */         if (chartype != 6 && chartype != 7)
/*     */         {
/* 838 */           return currentOffset;
/*     */         }
/*     */       } 
/* 841 */       while (++currentOffset < endOffset) {
/* 842 */         ch = data.charAt(currentOffset);
/* 843 */         if (ch >= 128) {
/* 844 */           if (!XMLChar.isName(ch))
/*     */             break; 
/*     */           continue;
/*     */         } 
/* 848 */         byte chartype = this.fASCIICharMap[ch];
/* 849 */         if (chartype != 6 && chartype != 5 && chartype != 3 && chartype != 2 && chartype != 7) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 858 */       return currentOffset;
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
/*     */ 
/*     */ 
/*     */     
/*     */     protected void addToken(ElementSchemePointer.Tokens tokens, int token) throws XNIException {
/* 874 */       tokens.addToken(token);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/xpointer/ElementSchemePointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */