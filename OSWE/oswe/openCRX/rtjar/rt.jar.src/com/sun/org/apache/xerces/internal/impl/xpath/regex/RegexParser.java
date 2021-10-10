/*      */ package com.sun.org.apache.xerces.internal.impl.xpath.regex;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class RegexParser
/*      */ {
/*      */   static final int T_CHAR = 0;
/*      */   static final int T_EOF = 1;
/*      */   static final int T_OR = 2;
/*      */   static final int T_STAR = 3;
/*      */   static final int T_PLUS = 4;
/*      */   static final int T_QUESTION = 5;
/*      */   static final int T_LPAREN = 6;
/*      */   static final int T_RPAREN = 7;
/*      */   static final int T_DOT = 8;
/*      */   static final int T_LBRACKET = 9;
/*      */   static final int T_BACKSOLIDUS = 10;
/*      */   static final int T_CARET = 11;
/*      */   static final int T_DOLLAR = 12;
/*      */   static final int T_LPAREN2 = 13;
/*      */   static final int T_LOOKAHEAD = 14;
/*      */   static final int T_NEGATIVELOOKAHEAD = 15;
/*      */   static final int T_LOOKBEHIND = 16;
/*      */   static final int T_NEGATIVELOOKBEHIND = 17;
/*      */   static final int T_INDEPENDENT = 18;
/*      */   static final int T_SET_OPERATIONS = 19;
/*      */   static final int T_POSIX_CHARCLASS_START = 20;
/*      */   static final int T_COMMENT = 21;
/*      */   static final int T_MODIFIERS = 22;
/*      */   static final int T_CONDITION = 23;
/*      */   static final int T_XMLSCHEMA_CC_SUBTRACTION = 24;
/*      */   int offset;
/*      */   String regex;
/*      */   int regexlen;
/*      */   int options;
/*      */   ResourceBundle resources;
/*      */   int chardata;
/*      */   int nexttoken;
/*      */   protected static final int S_NORMAL = 0;
/*      */   protected static final int S_INBRACKETS = 1;
/*      */   protected static final int S_INXBRACKETS = 2;
/*      */   
/*      */   static class ReferencePosition
/*      */   {
/*      */     int refNumber;
/*      */     int position;
/*      */     
/*      */     ReferencePosition(int n, int pos) {
/*   67 */       this.refNumber = n;
/*   68 */       this.position = pos;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   int context = 0;
/*   83 */   int parenOpened = 1;
/*   84 */   int parennumber = 1;
/*      */   boolean hasBackReferences;
/*   86 */   Vector references = null;
/*   87 */   int parenCount = 0;
/*      */   
/*      */   public RegexParser() {
/*   90 */     setLocale(Locale.getDefault());
/*      */   }
/*      */   public RegexParser(Locale locale) {
/*   93 */     setLocale(locale);
/*      */   }
/*      */   
/*      */   public void setLocale(Locale locale) {
/*      */     try {
/*   98 */       if (locale != null) {
/*   99 */         this.resources = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.xpath.regex.message", locale);
/*      */       } else {
/*      */         
/*  102 */         this.resources = SecuritySupport.getResourceBundle("com.sun.org.apache.xerces.internal.impl.xpath.regex.message");
/*      */       }
/*      */     
/*  105 */     } catch (MissingResourceException mre) {
/*  106 */       throw new RuntimeException("Installation Problem???  Couldn't load messages: " + mre
/*  107 */           .getMessage());
/*      */     } 
/*      */   }
/*      */   
/*      */   final ParseException ex(String key, int loc) {
/*  112 */     return new ParseException(this.resources.getString(key), loc);
/*      */   }
/*      */   
/*      */   protected final boolean isSet(int flag) {
/*  116 */     return ((this.options & flag) == flag);
/*      */   }
/*      */   
/*      */   synchronized Token parse(String regex, int options) throws ParseException {
/*  120 */     this.options = options;
/*  121 */     this.offset = 0;
/*  122 */     setContext(0);
/*  123 */     this.parennumber = 1;
/*  124 */     this.parenOpened = 1;
/*  125 */     this.hasBackReferences = false;
/*  126 */     this.regex = regex;
/*  127 */     if (isSet(16))
/*  128 */       this.regex = REUtil.stripExtendedComment(this.regex); 
/*  129 */     this.regexlen = this.regex.length();
/*      */ 
/*      */     
/*  132 */     next();
/*  133 */     Token ret = parseRegex();
/*  134 */     if (this.offset != this.regexlen)
/*  135 */       throw ex("parser.parse.1", this.offset); 
/*  136 */     if (this.parenCount < 0)
/*  137 */       throw ex("parser.factor.0", this.offset); 
/*  138 */     if (this.references != null) {
/*  139 */       for (int i = 0; i < this.references.size(); i++) {
/*  140 */         ReferencePosition position = this.references.elementAt(i);
/*  141 */         if (this.parennumber <= position.refNumber)
/*  142 */           throw ex("parser.parse.2", position.position); 
/*      */       } 
/*  144 */       this.references.removeAllElements();
/*      */     } 
/*  146 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void setContext(int con) {
/*  157 */     this.context = con;
/*      */   }
/*      */   
/*      */   final int read() {
/*  161 */     return this.nexttoken;
/*      */   }
/*      */   final void next() {
/*      */     int ret;
/*  165 */     if (this.offset >= this.regexlen) {
/*  166 */       this.chardata = -1;
/*  167 */       this.nexttoken = 1;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  172 */     int ch = this.regex.charAt(this.offset++);
/*  173 */     this.chardata = ch;
/*      */     
/*  175 */     if (this.context == 1) {
/*      */       int i;
/*      */       
/*  178 */       switch (ch) {
/*      */         case 92:
/*  180 */           i = 10;
/*  181 */           if (this.offset >= this.regexlen)
/*  182 */             throw ex("parser.next.1", this.offset - 1); 
/*  183 */           this.chardata = this.regex.charAt(this.offset++);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 45:
/*  189 */           if (this.offset < this.regexlen && this.regex.charAt(this.offset) == '[') {
/*  190 */             this.offset++;
/*  191 */             i = 24; break;
/*      */           } 
/*  193 */           i = 0;
/*      */           break;
/*      */         
/*      */         case 91:
/*  197 */           if (!isSet(512) && this.offset < this.regexlen && this.regex
/*  198 */             .charAt(this.offset) == ':') {
/*  199 */             this.offset++;
/*  200 */             i = 20;
/*      */             break;
/*      */           } 
/*      */         default:
/*  204 */           if (REUtil.isHighSurrogate(ch) && this.offset < this.regexlen) {
/*  205 */             int low = this.regex.charAt(this.offset);
/*  206 */             if (REUtil.isLowSurrogate(low)) {
/*  207 */               this.chardata = REUtil.composeFromSurrogates(ch, low);
/*  208 */               this.offset++;
/*      */             } 
/*      */           } 
/*  211 */           i = 0; break;
/*      */       } 
/*  213 */       this.nexttoken = i;
/*      */       
/*      */       return;
/*      */     } 
/*  217 */     switch (ch) { case 124:
/*  218 */         ret = 2; break;
/*  219 */       case 42: ret = 3; break;
/*  220 */       case 43: ret = 4; break;
/*  221 */       case 63: ret = 5; break;
/*  222 */       case 41: ret = 7; break;
/*  223 */       case 46: ret = 8; break;
/*  224 */       case 91: ret = 9; break;
/*      */       case 94:
/*  226 */         if (isSet(512)) {
/*  227 */           ret = 0;
/*      */           break;
/*      */         } 
/*  230 */         ret = 11;
/*      */         break;
/*      */       
/*      */       case 36:
/*  234 */         if (isSet(512)) {
/*  235 */           ret = 0;
/*      */           break;
/*      */         } 
/*  238 */         ret = 12;
/*      */         break;
/*      */       
/*      */       case 40:
/*  242 */         ret = 6;
/*  243 */         this.parenCount++;
/*  244 */         if (this.offset >= this.regexlen)
/*      */           break; 
/*  246 */         if (this.regex.charAt(this.offset) != '?')
/*      */           break; 
/*  248 */         if (++this.offset >= this.regexlen)
/*  249 */           throw ex("parser.next.2", this.offset - 1); 
/*  250 */         ch = this.regex.charAt(this.offset++);
/*  251 */         switch (ch) { case 58:
/*  252 */             ret = 13; break;
/*  253 */           case 61: ret = 14; break;
/*  254 */           case 33: ret = 15; break;
/*  255 */           case 91: ret = 19; break;
/*  256 */           case 62: ret = 18; break;
/*      */           case 60:
/*  258 */             if (this.offset >= this.regexlen)
/*  259 */               throw ex("parser.next.2", this.offset - 3); 
/*  260 */             ch = this.regex.charAt(this.offset++);
/*  261 */             if (ch == 61) {
/*  262 */               ret = 16; break;
/*  263 */             }  if (ch == 33) {
/*  264 */               ret = 17; break;
/*      */             } 
/*  266 */             throw ex("parser.next.3", this.offset - 3);
/*      */           
/*      */           case 35:
/*  269 */             while (this.offset < this.regexlen) {
/*  270 */               ch = this.regex.charAt(this.offset++);
/*  271 */               if (ch == 41)
/*      */                 break; 
/*  273 */             }  if (ch != 41)
/*  274 */               throw ex("parser.next.4", this.offset - 1); 
/*  275 */             ret = 21;
/*      */             break; }
/*      */         
/*  278 */         if (ch == 45 || (97 <= ch && ch <= 122) || (65 <= ch && ch <= 90)) {
/*  279 */           this.offset--;
/*  280 */           ret = 22; break;
/*      */         } 
/*  282 */         if (ch == 40) {
/*  283 */           ret = 23;
/*      */           break;
/*      */         } 
/*  286 */         throw ex("parser.next.2", this.offset - 2);
/*      */ 
/*      */ 
/*      */       
/*      */       case 92:
/*  291 */         ret = 10;
/*  292 */         if (this.offset >= this.regexlen)
/*  293 */           throw ex("parser.next.1", this.offset - 1); 
/*  294 */         this.chardata = this.regex.charAt(this.offset++);
/*      */         break;
/*      */       
/*      */       default:
/*  298 */         ret = 0; break; }
/*      */     
/*  300 */     this.nexttoken = ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Token parseRegex() throws ParseException {
/*  313 */     Token tok = parseTerm();
/*  314 */     Token parent = null;
/*  315 */     while (read() == 2) {
/*  316 */       next();
/*  317 */       if (parent == null) {
/*  318 */         parent = Token.createUnion();
/*  319 */         parent.addChild(tok);
/*  320 */         tok = parent;
/*      */       } 
/*  322 */       tok.addChild(parseTerm());
/*      */     } 
/*  324 */     return tok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Token parseTerm() throws ParseException {
/*  331 */     int ch = read();
/*  332 */     Token tok = null;
/*  333 */     if (ch == 2 || ch == 7 || ch == 1) {
/*  334 */       tok = Token.createEmpty();
/*      */     } else {
/*  336 */       tok = parseFactor();
/*  337 */       Token concat = null;
/*  338 */       while ((ch = read()) != 2 && ch != 7 && ch != 1) {
/*  339 */         if (concat == null) {
/*  340 */           concat = Token.createConcat();
/*  341 */           concat.addChild(tok);
/*  342 */           tok = concat;
/*      */         } 
/*  344 */         concat.addChild(parseFactor());
/*      */       } 
/*      */     } 
/*      */     
/*  348 */     if (ch == 7) {
/*  349 */       this.parenCount--;
/*      */     }
/*  351 */     return tok;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Token processCaret() throws ParseException {
/*  357 */     next();
/*  358 */     return Token.token_linebeginning;
/*      */   }
/*      */   Token processDollar() throws ParseException {
/*  361 */     next();
/*  362 */     return Token.token_lineend;
/*      */   }
/*      */   Token processLookahead() throws ParseException {
/*  365 */     next();
/*  366 */     Token tok = Token.createLook(20, parseRegex());
/*  367 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  368 */     next();
/*  369 */     return tok;
/*      */   }
/*      */   Token processNegativelookahead() throws ParseException {
/*  372 */     next();
/*  373 */     Token tok = Token.createLook(21, parseRegex());
/*  374 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  375 */     next();
/*  376 */     return tok;
/*      */   }
/*      */   Token processLookbehind() throws ParseException {
/*  379 */     next();
/*  380 */     Token tok = Token.createLook(22, parseRegex());
/*  381 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  382 */     next();
/*  383 */     return tok;
/*      */   }
/*      */   Token processNegativelookbehind() throws ParseException {
/*  386 */     next();
/*  387 */     Token tok = Token.createLook(23, parseRegex());
/*  388 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  389 */     next();
/*  390 */     return tok;
/*      */   }
/*      */   Token processBacksolidus_A() throws ParseException {
/*  393 */     next();
/*  394 */     return Token.token_stringbeginning;
/*      */   }
/*      */   Token processBacksolidus_Z() throws ParseException {
/*  397 */     next();
/*  398 */     return Token.token_stringend2;
/*      */   }
/*      */   Token processBacksolidus_z() throws ParseException {
/*  401 */     next();
/*  402 */     return Token.token_stringend;
/*      */   }
/*      */   Token processBacksolidus_b() throws ParseException {
/*  405 */     next();
/*  406 */     return Token.token_wordedge;
/*      */   }
/*      */   Token processBacksolidus_B() throws ParseException {
/*  409 */     next();
/*  410 */     return Token.token_not_wordedge;
/*      */   }
/*      */   Token processBacksolidus_lt() throws ParseException {
/*  413 */     next();
/*  414 */     return Token.token_wordbeginning;
/*      */   }
/*      */   Token processBacksolidus_gt() throws ParseException {
/*  417 */     next();
/*  418 */     return Token.token_wordend;
/*      */   }
/*      */   Token processStar(Token tok) throws ParseException {
/*  421 */     next();
/*  422 */     if (read() == 5) {
/*  423 */       next();
/*  424 */       return Token.createNGClosure(tok);
/*      */     } 
/*  426 */     return Token.createClosure(tok);
/*      */   }
/*      */   
/*      */   Token processPlus(Token tok) throws ParseException {
/*  430 */     next();
/*  431 */     if (read() == 5) {
/*  432 */       next();
/*  433 */       return Token.createConcat(tok, Token.createNGClosure(tok));
/*      */     } 
/*  435 */     return Token.createConcat(tok, Token.createClosure(tok));
/*      */   }
/*      */   
/*      */   Token processQuestion(Token tok) throws ParseException {
/*  439 */     next();
/*  440 */     Token par = Token.createUnion();
/*  441 */     if (read() == 5) {
/*  442 */       next();
/*  443 */       par.addChild(Token.createEmpty());
/*  444 */       par.addChild(tok);
/*      */     } else {
/*  446 */       par.addChild(tok);
/*  447 */       par.addChild(Token.createEmpty());
/*      */     } 
/*  449 */     return par;
/*      */   }
/*      */   boolean checkQuestion(int off) {
/*  452 */     return (off < this.regexlen && this.regex.charAt(off) == '?');
/*      */   }
/*      */   Token processParen() throws ParseException {
/*  455 */     next();
/*  456 */     int p = this.parenOpened++;
/*  457 */     Token tok = Token.createParen(parseRegex(), p);
/*  458 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  459 */     this.parennumber++;
/*  460 */     next();
/*  461 */     return tok;
/*      */   }
/*      */   Token processParen2() throws ParseException {
/*  464 */     next();
/*  465 */     Token tok = Token.createParen(parseRegex(), 0);
/*  466 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  467 */     next();
/*  468 */     return tok;
/*      */   }
/*      */   
/*      */   Token processCondition() throws ParseException {
/*  472 */     if (this.offset + 1 >= this.regexlen) throw ex("parser.factor.4", this.offset);
/*      */     
/*  474 */     int refno = -1;
/*  475 */     Token condition = null;
/*  476 */     int ch = this.regex.charAt(this.offset);
/*  477 */     if (49 <= ch && ch <= 57) {
/*  478 */       refno = ch - 48;
/*  479 */       int finalRefno = refno;
/*      */       
/*  481 */       if (this.parennumber <= refno) {
/*  482 */         throw ex("parser.parse.2", this.offset);
/*      */       }
/*  484 */       while (this.offset + 1 < this.regexlen) {
/*  485 */         ch = this.regex.charAt(this.offset + 1);
/*  486 */         if (49 <= ch && ch <= 57) {
/*  487 */           refno = refno * 10 + ch - 48;
/*  488 */           if (refno < this.parennumber) {
/*  489 */             finalRefno = refno;
/*  490 */             this.offset++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  501 */       this.hasBackReferences = true;
/*  502 */       if (this.references == null) this.references = new Vector(); 
/*  503 */       this.references.addElement(new ReferencePosition(finalRefno, this.offset));
/*  504 */       this.offset++;
/*  505 */       if (this.regex.charAt(this.offset) != ')') throw ex("parser.factor.1", this.offset); 
/*  506 */       this.offset++;
/*      */     } else {
/*  508 */       if (ch == 63) this.offset--; 
/*  509 */       next();
/*  510 */       condition = parseFactor();
/*  511 */       switch (condition.type) {
/*      */         case 20:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */           break;
/*      */         case 8:
/*  518 */           if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*      */           break;
/*      */         default:
/*  521 */           throw ex("parser.factor.5", this.offset);
/*      */       } 
/*      */     
/*      */     } 
/*  525 */     next();
/*  526 */     Token yesPattern = parseRegex();
/*  527 */     Token noPattern = null;
/*  528 */     if (yesPattern.type == 2) {
/*  529 */       if (yesPattern.size() != 2) throw ex("parser.factor.6", this.offset); 
/*  530 */       noPattern = yesPattern.getChild(1);
/*  531 */       yesPattern = yesPattern.getChild(0);
/*      */     } 
/*  533 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  534 */     next();
/*  535 */     return Token.createCondition(refno, condition, yesPattern, noPattern);
/*      */   }
/*      */   
/*      */   Token processModifiers() throws ParseException {
/*      */     Token tok;
/*  540 */     int add = 0, mask = 0, ch = -1;
/*  541 */     while (this.offset < this.regexlen) {
/*  542 */       ch = this.regex.charAt(this.offset);
/*  543 */       int v = REUtil.getOptionValue(ch);
/*  544 */       if (v == 0)
/*  545 */         break;  add |= v;
/*  546 */       this.offset++;
/*      */     } 
/*  548 */     if (this.offset >= this.regexlen) throw ex("parser.factor.2", this.offset - 1); 
/*  549 */     if (ch == 45) {
/*  550 */       this.offset++;
/*  551 */       while (this.offset < this.regexlen) {
/*  552 */         ch = this.regex.charAt(this.offset);
/*  553 */         int v = REUtil.getOptionValue(ch);
/*  554 */         if (v == 0)
/*  555 */           break;  mask |= v;
/*  556 */         this.offset++;
/*      */       } 
/*  558 */       if (this.offset >= this.regexlen) throw ex("parser.factor.2", this.offset - 1);
/*      */     
/*      */     } 
/*  561 */     if (ch == 58) {
/*  562 */       this.offset++;
/*  563 */       next();
/*  564 */       tok = Token.createModifierGroup(parseRegex(), add, mask);
/*  565 */       if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  566 */       next();
/*  567 */     } else if (ch == 41) {
/*  568 */       this.offset++;
/*  569 */       next();
/*  570 */       tok = Token.createModifierGroup(parseRegex(), add, mask);
/*      */     } else {
/*  572 */       throw ex("parser.factor.3", this.offset);
/*      */     } 
/*  574 */     return tok;
/*      */   }
/*      */   Token processIndependent() throws ParseException {
/*  577 */     next();
/*  578 */     Token tok = Token.createLook(24, parseRegex());
/*  579 */     if (read() != 7) throw ex("parser.factor.1", this.offset - 1); 
/*  580 */     next();
/*  581 */     return tok;
/*      */   }
/*      */   Token processBacksolidus_c() throws ParseException {
/*      */     int ch2;
/*  585 */     if (this.offset >= this.regexlen || ((
/*  586 */       ch2 = this.regex.charAt(this.offset++)) & 0xFFE0) != 64)
/*  587 */       throw ex("parser.atom.1", this.offset - 1); 
/*  588 */     next();
/*  589 */     return Token.createChar(ch2 - 64);
/*      */   }
/*      */   Token processBacksolidus_C() throws ParseException {
/*  592 */     throw ex("parser.process.1", this.offset);
/*      */   }
/*      */   Token processBacksolidus_i() throws ParseException {
/*  595 */     Token tok = Token.createChar(105);
/*  596 */     next();
/*  597 */     return tok;
/*      */   }
/*      */   Token processBacksolidus_I() throws ParseException {
/*  600 */     throw ex("parser.process.1", this.offset);
/*      */   }
/*      */   Token processBacksolidus_g() throws ParseException {
/*  603 */     next();
/*  604 */     return Token.getGraphemePattern();
/*      */   }
/*      */   Token processBacksolidus_X() throws ParseException {
/*  607 */     next();
/*  608 */     return Token.getCombiningCharacterSequence();
/*      */   }
/*      */   Token processBackreference() throws ParseException {
/*  611 */     int refnum = this.chardata - 48;
/*  612 */     int finalRefnum = refnum;
/*      */     
/*  614 */     if (this.parennumber <= refnum) {
/*  615 */       throw ex("parser.parse.2", this.offset - 2);
/*      */     }
/*  617 */     while (this.offset < this.regexlen) {
/*  618 */       int ch = this.regex.charAt(this.offset);
/*  619 */       if (49 <= ch && ch <= 57) {
/*  620 */         refnum = refnum * 10 + ch - 48;
/*  621 */         if (refnum < this.parennumber) {
/*  622 */           this.offset++;
/*  623 */           finalRefnum = refnum;
/*  624 */           this.chardata = ch;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  635 */     Token tok = Token.createBackReference(finalRefnum);
/*  636 */     this.hasBackReferences = true;
/*  637 */     if (this.references == null) this.references = new Vector(); 
/*  638 */     this.references.addElement(new ReferencePosition(finalRefnum, this.offset - 2));
/*  639 */     next();
/*  640 */     return tok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Token parseFactor() throws ParseException {
/*  655 */     int ch = read();
/*      */     
/*  657 */     switch (ch) { case 11:
/*  658 */         return processCaret();
/*  659 */       case 12: return processDollar();
/*  660 */       case 14: return processLookahead();
/*  661 */       case 15: return processNegativelookahead();
/*  662 */       case 16: return processLookbehind();
/*  663 */       case 17: return processNegativelookbehind();
/*      */       
/*      */       case 21:
/*  666 */         next();
/*  667 */         return Token.createEmpty();
/*      */       
/*      */       case 10:
/*  670 */         switch (this.chardata) { case 65:
/*  671 */             return processBacksolidus_A();
/*  672 */           case 90: return processBacksolidus_Z();
/*  673 */           case 122: return processBacksolidus_z();
/*  674 */           case 98: return processBacksolidus_b();
/*  675 */           case 66: return processBacksolidus_B();
/*  676 */           case 60: return processBacksolidus_lt();
/*  677 */           case 62: return processBacksolidus_gt(); }
/*      */         
/*      */         break; }
/*      */     
/*  681 */     Token tok = parseAtom();
/*  682 */     ch = read();
/*  683 */     switch (ch) { case 3:
/*  684 */         return processStar(tok);
/*  685 */       case 4: return processPlus(tok);
/*  686 */       case 5: return processQuestion(tok);
/*      */       case 0:
/*  688 */         if (this.chardata == 123 && this.offset < this.regexlen) {
/*      */           
/*  690 */           int off = this.offset;
/*  691 */           int min = 0, max = -1;
/*      */           
/*  693 */           if ((ch = this.regex.charAt(off++)) >= 48 && ch <= 57) {
/*      */             
/*  695 */             min = ch - 48;
/*  696 */             while (off < this.regexlen && (
/*  697 */               ch = this.regex.charAt(off++)) >= 48 && ch <= 57) {
/*  698 */               min = min * 10 + ch - 48;
/*  699 */               if (min < 0) {
/*  700 */                 throw ex("parser.quantifier.5", this.offset);
/*      */               }
/*      */             } 
/*      */           } else {
/*  704 */             throw ex("parser.quantifier.1", this.offset);
/*      */           } 
/*      */           
/*  707 */           max = min;
/*  708 */           if (ch == 44) {
/*      */             
/*  710 */             if (off >= this.regexlen) {
/*  711 */               throw ex("parser.quantifier.3", this.offset);
/*      */             }
/*  713 */             if ((ch = this.regex.charAt(off++)) >= 48 && ch <= 57) {
/*      */               
/*  715 */               max = ch - 48;
/*  716 */               while (off < this.regexlen && (
/*  717 */                 ch = this.regex.charAt(off++)) >= 48 && ch <= 57) {
/*      */                 
/*  719 */                 max = max * 10 + ch - 48;
/*  720 */                 if (max < 0) {
/*  721 */                   throw ex("parser.quantifier.5", this.offset);
/*      */                 }
/*      */               } 
/*  724 */               if (min > max) {
/*  725 */                 throw ex("parser.quantifier.4", this.offset);
/*      */               }
/*      */             } else {
/*  728 */               max = -1;
/*      */             } 
/*      */           } 
/*      */           
/*  732 */           if (ch != 125) {
/*  733 */             throw ex("parser.quantifier.2", this.offset);
/*      */           }
/*  735 */           if (checkQuestion(off)) {
/*  736 */             tok = Token.createNGClosure(tok);
/*  737 */             this.offset = off + 1;
/*      */           } else {
/*  739 */             tok = Token.createClosure(tok);
/*  740 */             this.offset = off;
/*      */           } 
/*      */           
/*  743 */           tok.setMin(min);
/*  744 */           tok.setMax(max);
/*      */           
/*  746 */           next();
/*      */         }  break; }
/*      */     
/*  749 */     return tok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Token parseAtom() throws ParseException {
/*  759 */     int ch2, pstart, high, ch = read();
/*  760 */     Token tok = null;
/*  761 */     switch (ch) { case 6:
/*  762 */         return processParen();
/*  763 */       case 13: return processParen2();
/*  764 */       case 23: return processCondition();
/*  765 */       case 22: return processModifiers();
/*  766 */       case 18: return processIndependent();
/*      */       case 8:
/*  768 */         next();
/*  769 */         tok = Token.token_dot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  845 */         return tok;case 9: return parseCharacterClass(true);case 19: return parseSetOperations();case 10: switch (this.chardata) { case 68: case 83: case 87: case 100: case 115: case 119: tok = getTokenForShorthand(this.chardata); next(); return tok;case 101: case 102: case 110: case 114: case 116: case 117: case 118: case 120: ch2 = decodeEscaped(); if (ch2 < 65536) { tok = Token.createChar(ch2); break; }  tok = Token.createString(REUtil.decomposeToSurrogates(ch2)); break;case 99: return processBacksolidus_c();case 67: return processBacksolidus_C();case 105: return processBacksolidus_i();case 73: return processBacksolidus_I();case 103: return processBacksolidus_g();case 88: return processBacksolidus_X();case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57: return processBackreference();case 80: case 112: pstart = this.offset; tok = processBacksolidus_pP(this.chardata); if (tok == null) throw ex("parser.atom.5", pstart);  break;default: tok = Token.createChar(this.chardata); break; }  next(); return tok;case 0: if (this.chardata == 93 || this.chardata == 123 || this.chardata == 125) throw ex("parser.atom.4", this.offset - 1);  tok = Token.createChar(this.chardata); high = this.chardata; next(); if (REUtil.isHighSurrogate(high) && read() == 0 && REUtil.isLowSurrogate(this.chardata)) { char[] sur = new char[2]; sur[0] = (char)high; sur[1] = (char)this.chardata; tok = Token.createParen(Token.createString(new String(sur)), 0); next(); }  return tok; }
/*      */     
/*      */     throw ex("parser.atom.4", this.offset - 1);
/*      */   }
/*      */   protected RangeToken processBacksolidus_pP(int c) throws ParseException {
/*  850 */     next();
/*  851 */     if (read() != 0 || this.chardata != 123) {
/*  852 */       throw ex("parser.atom.2", this.offset - 1);
/*      */     }
/*      */     
/*  855 */     boolean positive = (c == 112);
/*  856 */     int namestart = this.offset;
/*  857 */     int nameend = this.regex.indexOf('}', namestart);
/*      */     
/*  859 */     if (nameend < 0) {
/*  860 */       throw ex("parser.atom.3", this.offset);
/*      */     }
/*  862 */     String pname = this.regex.substring(namestart, nameend);
/*  863 */     this.offset = nameend + 1;
/*      */     
/*  865 */     return Token.getRange(pname, positive, isSet(512));
/*      */   }
/*      */   
/*      */   int processCIinCharacterClass(RangeToken tok, int c) {
/*  869 */     return decodeEscaped();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RangeToken parseCharacterClass(boolean useNrange) throws ParseException {
/*      */     RangeToken tok;
/*  880 */     setContext(1);
/*  881 */     next();
/*  882 */     boolean nrange = false;
/*  883 */     RangeToken base = null;
/*      */     
/*  885 */     if (read() == 0 && this.chardata == 94) {
/*  886 */       nrange = true;
/*  887 */       next();
/*  888 */       if (useNrange) {
/*  889 */         tok = Token.createNRange();
/*      */       } else {
/*  891 */         base = Token.createRange();
/*  892 */         base.addRange(0, 1114111);
/*  893 */         tok = Token.createRange();
/*      */       } 
/*      */     } else {
/*  896 */       tok = Token.createRange();
/*      */     } 
/*      */     
/*  899 */     boolean firstloop = true; int type;
/*  900 */     while ((type = read()) != 1 && (
/*  901 */       type != 0 || this.chardata != 93 || firstloop)) {
/*      */       
/*  903 */       int c = this.chardata;
/*  904 */       boolean end = false;
/*  905 */       if (type == 10) {
/*  906 */         int pstart; RangeToken tok2; switch (c) { case 68: case 83: case 87:
/*      */           case 100:
/*      */           case 115:
/*      */           case 119:
/*  910 */             tok.mergeRanges(getTokenForShorthand(c));
/*  911 */             end = true; break;
/*      */           case 67:
/*      */           case 73:
/*      */           case 99:
/*      */           case 105:
/*  916 */             c = processCIinCharacterClass(tok, c);
/*  917 */             if (c < 0) end = true;
/*      */             
/*      */             break;
/*      */           case 80:
/*      */           case 112:
/*  922 */             pstart = this.offset;
/*  923 */             tok2 = processBacksolidus_pP(c);
/*  924 */             if (tok2 == null) throw ex("parser.atom.5", pstart); 
/*  925 */             tok.mergeRanges(tok2);
/*  926 */             end = true;
/*      */             break;
/*      */           
/*      */           default:
/*  930 */             c = decodeEscaped();
/*      */             break; }
/*      */ 
/*      */       
/*  934 */       } else if (type == 20) {
/*  935 */         int nameend = this.regex.indexOf(':', this.offset);
/*  936 */         if (nameend < 0) throw ex("parser.cc.1", this.offset); 
/*  937 */         boolean positive = true;
/*  938 */         if (this.regex.charAt(this.offset) == '^') {
/*  939 */           this.offset++;
/*  940 */           positive = false;
/*      */         } 
/*  942 */         String name = this.regex.substring(this.offset, nameend);
/*  943 */         RangeToken range = Token.getRange(name, positive, 
/*  944 */             isSet(512));
/*  945 */         if (range == null) throw ex("parser.cc.3", this.offset); 
/*  946 */         tok.mergeRanges(range);
/*  947 */         end = true;
/*  948 */         if (nameend + 1 >= this.regexlen || this.regex.charAt(nameend + 1) != ']')
/*  949 */           throw ex("parser.cc.1", nameend); 
/*  950 */         this.offset = nameend + 2;
/*      */       }
/*  952 */       else if (type == 24 && !firstloop) {
/*  953 */         if (nrange) {
/*  954 */           nrange = false;
/*  955 */           if (useNrange) {
/*  956 */             tok = (RangeToken)Token.complementRanges(tok);
/*      */           } else {
/*      */             
/*  959 */             base.subtractRanges(tok);
/*  960 */             tok = base;
/*      */           } 
/*      */         } 
/*  963 */         RangeToken range2 = parseCharacterClass(false);
/*  964 */         tok.subtractRanges(range2);
/*  965 */         if (read() != 0 || this.chardata != 93) {
/*  966 */           throw ex("parser.cc.5", this.offset);
/*      */         }
/*      */         break;
/*      */       } 
/*  970 */       next();
/*  971 */       if (!end) {
/*  972 */         if (read() != 0 || this.chardata != 45) {
/*  973 */           if (!isSet(2) || c > 65535) {
/*  974 */             tok.addRange(c, c);
/*      */           } else {
/*      */             
/*  977 */             addCaseInsensitiveChar(tok, c);
/*      */           } 
/*      */         } else {
/*  980 */           if (type == 24) {
/*  981 */             throw ex("parser.cc.8", this.offset - 1);
/*      */           }
/*      */           
/*  984 */           next();
/*  985 */           if ((type = read()) == 1) throw ex("parser.cc.2", this.offset); 
/*  986 */           if (type == 0 && this.chardata == 93) {
/*  987 */             if (!isSet(2) || c > 65535) {
/*  988 */               tok.addRange(c, c);
/*      */             } else {
/*      */               
/*  991 */               addCaseInsensitiveChar(tok, c);
/*      */             } 
/*  993 */             tok.addRange(45, 45);
/*      */           } else {
/*  995 */             int rangeend = this.chardata;
/*  996 */             if (type == 10) {
/*  997 */               rangeend = decodeEscaped();
/*      */             }
/*  999 */             next();
/* 1000 */             if (c > rangeend) {
/* 1001 */               throw ex("parser.ope.3", this.offset - 1);
/*      */             }
/* 1003 */             if (!isSet(2) || (c > 65535 && rangeend > 65535)) {
/*      */               
/* 1005 */               tok.addRange(c, rangeend);
/*      */             } else {
/*      */               
/* 1008 */               addCaseInsensitiveCharRange(tok, c, rangeend);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/* 1013 */       if (isSet(1024) && 
/* 1014 */         read() == 0 && this.chardata == 44) {
/* 1015 */         next();
/*      */       }
/* 1017 */       firstloop = false;
/*      */     } 
/* 1019 */     if (read() == 1) {
/* 1020 */       throw ex("parser.cc.2", this.offset);
/*      */     }
/*      */     
/* 1023 */     if (!useNrange && nrange) {
/* 1024 */       base.subtractRanges(tok);
/* 1025 */       tok = base;
/*      */     } 
/* 1027 */     tok.sortRanges();
/* 1028 */     tok.compactRanges();
/* 1029 */     setContext(0);
/* 1030 */     next();
/*      */     
/* 1032 */     return tok;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RangeToken parseSetOperations() throws ParseException {
/* 1039 */     RangeToken tok = parseCharacterClass(false);
/*      */     int type;
/* 1041 */     while ((type = read()) != 7) {
/* 1042 */       int ch = this.chardata;
/* 1043 */       if ((type == 0 && (ch == 45 || ch == 38)) || type == 4) {
/*      */         
/* 1045 */         next();
/* 1046 */         if (read() != 9) throw ex("parser.ope.1", this.offset - 1); 
/* 1047 */         RangeToken t2 = parseCharacterClass(false);
/* 1048 */         if (type == 4) {
/* 1049 */           tok.mergeRanges(t2); continue;
/* 1050 */         }  if (ch == 45) {
/* 1051 */           tok.subtractRanges(t2); continue;
/* 1052 */         }  if (ch == 38) {
/* 1053 */           tok.intersectRanges(t2); continue;
/*      */         } 
/* 1055 */         throw new RuntimeException("ASSERT");
/*      */       } 
/* 1057 */       throw ex("parser.ope.2", this.offset - 1);
/*      */     } 
/*      */     
/* 1060 */     next();
/* 1061 */     return tok;
/*      */   }
/*      */   
/*      */   Token getTokenForShorthand(int ch) {
/*      */     Token tok;
/* 1066 */     switch (ch) {
/*      */       
/*      */       case 100:
/* 1069 */         tok = isSet(32) ? Token.getRange("Nd", true) : Token.token_0to9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1095 */         return tok;case 68: tok = isSet(32) ? Token.getRange("Nd", false) : Token.token_not_0to9; return tok;case 119: tok = isSet(32) ? Token.getRange("IsWord", true) : Token.token_wordchars; return tok;case 87: tok = isSet(32) ? Token.getRange("IsWord", false) : Token.token_not_wordchars; return tok;case 115: tok = isSet(32) ? Token.getRange("IsSpace", true) : Token.token_spaces; return tok;case 83: tok = isSet(32) ? Token.getRange("IsSpace", false) : Token.token_not_spaces; return tok;
/*      */     } 
/*      */     throw new RuntimeException("Internal Error: shorthands: \\u" + Integer.toString(ch, 16));
/*      */   }
/*      */   int decodeEscaped() throws ParseException {
/*      */     int v1, uv;
/* 1101 */     if (read() != 10) throw ex("parser.next.1", this.offset - 1); 
/* 1102 */     int c = this.chardata;
/* 1103 */     switch (c) { case 101:
/* 1104 */         c = 27; break;
/* 1105 */       case 102: c = 12; break;
/* 1106 */       case 110: c = 10; break;
/* 1107 */       case 114: c = 13; break;
/* 1108 */       case 116: c = 9;
/*      */         break;
/*      */       case 120:
/* 1111 */         next();
/* 1112 */         if (read() != 0) throw ex("parser.descape.1", this.offset - 1); 
/* 1113 */         if (this.chardata == 123) {
/* 1114 */           int i = 0;
/* 1115 */           int j = 0;
/*      */           while (true) {
/* 1117 */             next();
/* 1118 */             if (read() != 0) throw ex("parser.descape.1", this.offset - 1); 
/* 1119 */             if ((i = hexChar(this.chardata)) < 0)
/*      */               break; 
/* 1121 */             if (j > j * 16) throw ex("parser.descape.2", this.offset - 1); 
/* 1122 */             j = j * 16 + i;
/*      */           } 
/* 1124 */           if (this.chardata != 125) throw ex("parser.descape.3", this.offset - 1); 
/* 1125 */           if (j > 1114111) throw ex("parser.descape.4", this.offset - 1); 
/* 1126 */           c = j; break;
/*      */         } 
/* 1128 */         v1 = 0;
/* 1129 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1130 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1131 */         uv = v1;
/* 1132 */         next();
/* 1133 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1134 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1135 */         uv = uv * 16 + v1;
/* 1136 */         c = uv;
/*      */         break;
/*      */ 
/*      */       
/*      */       case 117:
/* 1141 */         v1 = 0;
/* 1142 */         next();
/* 1143 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1144 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1145 */         uv = v1;
/* 1146 */         next();
/* 1147 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1148 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1149 */         uv = uv * 16 + v1;
/* 1150 */         next();
/* 1151 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1152 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1153 */         uv = uv * 16 + v1;
/* 1154 */         next();
/* 1155 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1156 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1157 */         uv = uv * 16 + v1;
/* 1158 */         c = uv;
/*      */         break;
/*      */       
/*      */       case 118:
/* 1162 */         next();
/* 1163 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1164 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1165 */         uv = v1;
/* 1166 */         next();
/* 1167 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1168 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1169 */         uv = uv * 16 + v1;
/* 1170 */         next();
/* 1171 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1172 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1173 */         uv = uv * 16 + v1;
/* 1174 */         next();
/* 1175 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1176 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1177 */         uv = uv * 16 + v1;
/* 1178 */         next();
/* 1179 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1180 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1181 */         uv = uv * 16 + v1;
/* 1182 */         next();
/* 1183 */         if (read() != 0 || (v1 = hexChar(this.chardata)) < 0)
/* 1184 */           throw ex("parser.descape.1", this.offset - 1); 
/* 1185 */         uv = uv * 16 + v1;
/* 1186 */         if (uv > 1114111) throw ex("parser.descappe.4", this.offset - 1); 
/* 1187 */         c = uv;
/*      */         break;
/*      */       case 65:
/*      */       case 90:
/*      */       case 122:
/* 1192 */         throw ex("parser.descape.5", this.offset - 2); }
/*      */ 
/*      */     
/* 1195 */     return c;
/*      */   }
/*      */   
/*      */   private static final int hexChar(int ch) {
/* 1199 */     if (ch < 48) return -1; 
/* 1200 */     if (ch > 102) return -1; 
/* 1201 */     if (ch <= 57) return ch - 48; 
/* 1202 */     if (ch < 65) return -1; 
/* 1203 */     if (ch <= 70) return ch - 65 + 10; 
/* 1204 */     if (ch < 97) return -1; 
/* 1205 */     return ch - 97 + 10;
/*      */   }
/*      */   
/*      */   protected static final void addCaseInsensitiveChar(RangeToken tok, int c) {
/* 1209 */     int[] caseMap = CaseInsensitiveMap.get(c);
/* 1210 */     tok.addRange(c, c);
/*      */     
/* 1212 */     if (caseMap != null) {
/* 1213 */       for (int i = 0; i < caseMap.length; i += 2) {
/* 1214 */         tok.addRange(caseMap[i], caseMap[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final void addCaseInsensitiveCharRange(RangeToken tok, int start, int end) {
/*      */     int r1, r2;
/* 1223 */     if (start <= end) {
/* 1224 */       r1 = start;
/* 1225 */       r2 = end;
/*      */     } else {
/* 1227 */       r1 = end;
/* 1228 */       r2 = start;
/*      */     } 
/*      */     
/* 1231 */     tok.addRange(r1, r2);
/* 1232 */     for (int ch = r1; ch <= r2; ch++) {
/* 1233 */       int[] caseMap = CaseInsensitiveMap.get(ch);
/* 1234 */       if (caseMap != null)
/* 1235 */         for (int i = 0; i < caseMap.length; i += 2)
/* 1236 */           tok.addRange(caseMap[i], caseMap[i]);  
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xpath/regex/RegexParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */