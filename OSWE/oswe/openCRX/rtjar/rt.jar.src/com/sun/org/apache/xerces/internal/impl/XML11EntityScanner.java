/*      */ package com.sun.org.apache.xerces.internal.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.util.XML11Char;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLChar;
/*      */ import com.sun.org.apache.xerces.internal.util.XMLStringBuffer;
/*      */ import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
/*      */ import com.sun.org.apache.xerces.internal.xni.QName;
/*      */ import com.sun.org.apache.xerces.internal.xni.XMLString;
/*      */ import java.io.IOException;
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
/*      */ public class XML11EntityScanner
/*      */   extends XMLEntityScanner
/*      */ {
/*      */   public int peekChar() throws IOException {
/*   72 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*   73 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*   77 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*      */ 
/*      */     
/*   80 */     if (this.fCurrentEntity.isExternal()) {
/*   81 */       return (c != 13 && c != 133 && c != 8232) ? c : 10;
/*      */     }
/*      */     
/*   84 */     return c;
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
/*      */   
/*      */   protected int scanChar(XMLScanner.NameType nt) throws IOException {
/*  100 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  101 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  105 */     int offset = this.fCurrentEntity.position;
/*  106 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  107 */     boolean external = false;
/*  108 */     if (c == 10 || ((c == 13 || c == 133 || c == 8232) && (
/*  109 */       external = this.fCurrentEntity.isExternal()))) {
/*  110 */       this.fCurrentEntity.lineNumber++;
/*  111 */       this.fCurrentEntity.columnNumber = 1;
/*  112 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  113 */         invokeListeners(1);
/*  114 */         this.fCurrentEntity.ch[0] = (char)c;
/*  115 */         load(1, false, false);
/*  116 */         offset = 0;
/*      */       } 
/*  118 */       if (c == 13 && external) {
/*  119 */         int cc = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  120 */         if (cc != 10 && cc != 133) {
/*  121 */           this.fCurrentEntity.position--;
/*      */         }
/*      */       } 
/*  124 */       c = 10;
/*      */     } 
/*      */ 
/*      */     
/*  128 */     this.fCurrentEntity.columnNumber++;
/*  129 */     if (!this.detectingVersion) {
/*  130 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/*      */     }
/*  132 */     return c;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String scanNmtoken() throws IOException {
/*  153 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  154 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  158 */     int offset = this.fCurrentEntity.position;
/*      */     
/*      */     while (true) {
/*  161 */       char ch = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  162 */       if (XML11Char.isXML11Name(ch)) {
/*  163 */         if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  164 */           int i = this.fCurrentEntity.position - offset;
/*  165 */           invokeListeners(i);
/*  166 */           if (i == this.fCurrentEntity.ch.length) {
/*      */             
/*  168 */             char[] tmp = new char[this.fCurrentEntity.ch.length << 1];
/*  169 */             System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */             
/*  171 */             this.fCurrentEntity.ch = tmp;
/*      */           } else {
/*      */             
/*  174 */             System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */           } 
/*      */           
/*  177 */           offset = 0;
/*  178 */           if (load(i, false, false))
/*      */             break; 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  183 */       if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  184 */         if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  185 */           int i = this.fCurrentEntity.position - offset;
/*  186 */           invokeListeners(i);
/*  187 */           if (i == this.fCurrentEntity.ch.length) {
/*      */             
/*  189 */             char[] tmp = new char[this.fCurrentEntity.ch.length << 1];
/*  190 */             System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */             
/*  192 */             this.fCurrentEntity.ch = tmp;
/*      */           } else {
/*      */             
/*  195 */             System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */           } 
/*      */           
/*  198 */           offset = 0;
/*  199 */           if (load(i, false, false)) {
/*  200 */             this.fCurrentEntity.startPosition--;
/*  201 */             this.fCurrentEntity.position--;
/*      */             break;
/*      */           } 
/*      */         } 
/*  205 */         char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  206 */         if (!XMLChar.isLowSurrogate(ch2) || 
/*  207 */           !XML11Char.isXML11Name(XMLChar.supplemental(ch, ch2))) {
/*  208 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*  211 */         if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  212 */           int i = this.fCurrentEntity.position - offset;
/*  213 */           invokeListeners(i);
/*  214 */           if (i == this.fCurrentEntity.ch.length) {
/*      */             
/*  216 */             char[] tmp = new char[this.fCurrentEntity.ch.length << 1];
/*  217 */             System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */             
/*  219 */             this.fCurrentEntity.ch = tmp;
/*      */           } else {
/*      */             
/*  222 */             System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */           } 
/*      */           
/*  225 */           offset = 0;
/*  226 */           if (load(i, false, false)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/*      */     
/*  237 */     int length = this.fCurrentEntity.position - offset;
/*  238 */     this.fCurrentEntity.columnNumber += length;
/*      */ 
/*      */     
/*  241 */     String symbol = null;
/*  242 */     if (length > 0) {
/*  243 */       symbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */     }
/*  245 */     return symbol;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String scanName(XMLScanner.NameType nt) throws IOException {
/*  269 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  270 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  274 */     int offset = this.fCurrentEntity.position;
/*  275 */     char ch = this.fCurrentEntity.ch[offset];
/*      */     
/*  277 */     if (XML11Char.isXML11NameStart(ch)) {
/*  278 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  279 */         invokeListeners(1);
/*  280 */         this.fCurrentEntity.ch[0] = ch;
/*  281 */         offset = 0;
/*  282 */         if (load(1, false, false)) {
/*  283 */           this.fCurrentEntity.columnNumber++;
/*  284 */           String str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
/*  285 */           return str;
/*      */         }
/*      */       
/*      */       } 
/*  289 */     } else if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  290 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  291 */         invokeListeners(1);
/*  292 */         this.fCurrentEntity.ch[0] = ch;
/*  293 */         offset = 0;
/*  294 */         if (load(1, false, false)) {
/*  295 */           this.fCurrentEntity.position--;
/*  296 */           this.fCurrentEntity.startPosition--;
/*  297 */           return null;
/*      */         } 
/*      */       } 
/*  300 */       char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  301 */       if (!XMLChar.isLowSurrogate(ch2) || 
/*  302 */         !XML11Char.isXML11NameStart(XMLChar.supplemental(ch, ch2))) {
/*  303 */         this.fCurrentEntity.position--;
/*  304 */         return null;
/*      */       } 
/*  306 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  307 */         invokeListeners(2);
/*  308 */         this.fCurrentEntity.ch[0] = ch;
/*  309 */         this.fCurrentEntity.ch[1] = ch2;
/*  310 */         offset = 0;
/*  311 */         if (load(2, false, false)) {
/*  312 */           this.fCurrentEntity.columnNumber += 2;
/*  313 */           String str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
/*  314 */           return str;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  319 */       return null;
/*      */     } 
/*      */     
/*  322 */     int length = 0;
/*      */     while (true) {
/*  324 */       ch = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  325 */       if (XML11Char.isXML11Name(ch)) {
/*  326 */         if ((length = checkBeforeLoad(this.fCurrentEntity, offset, offset)) > 0) {
/*  327 */           offset = 0;
/*  328 */           if (load(length, false, false))
/*      */             break; 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  333 */       if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  334 */         if ((length = checkBeforeLoad(this.fCurrentEntity, offset, offset)) > 0) {
/*  335 */           offset = 0;
/*  336 */           if (load(length, false, false)) {
/*  337 */             this.fCurrentEntity.position--;
/*  338 */             this.fCurrentEntity.startPosition--;
/*      */             break;
/*      */           } 
/*      */         } 
/*  342 */         char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  343 */         if (!XMLChar.isLowSurrogate(ch2) || 
/*  344 */           !XML11Char.isXML11Name(XMLChar.supplemental(ch, ch2))) {
/*  345 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*  348 */         if ((length = checkBeforeLoad(this.fCurrentEntity, offset, offset)) > 0) {
/*  349 */           offset = 0;
/*  350 */           if (load(length, false, false)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/*      */     
/*  361 */     length = this.fCurrentEntity.position - offset;
/*  362 */     this.fCurrentEntity.columnNumber += length;
/*      */ 
/*      */     
/*  365 */     String symbol = null;
/*  366 */     if (length > 0) {
/*  367 */       checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, length);
/*  368 */       checkEntityLimit(nt, this.fCurrentEntity, offset, length);
/*  369 */       symbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */     } 
/*  371 */     return symbol;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String scanNCName() throws IOException {
/*  394 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  395 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  399 */     int offset = this.fCurrentEntity.position;
/*  400 */     char ch = this.fCurrentEntity.ch[offset];
/*      */     
/*  402 */     if (XML11Char.isXML11NCNameStart(ch)) {
/*  403 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  404 */         invokeListeners(1);
/*  405 */         this.fCurrentEntity.ch[0] = ch;
/*  406 */         offset = 0;
/*  407 */         if (load(1, false, false)) {
/*  408 */           this.fCurrentEntity.columnNumber++;
/*  409 */           String str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
/*  410 */           return str;
/*      */         }
/*      */       
/*      */       } 
/*  414 */     } else if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  415 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  416 */         invokeListeners(1);
/*  417 */         this.fCurrentEntity.ch[0] = ch;
/*  418 */         offset = 0;
/*  419 */         if (load(1, false, false)) {
/*  420 */           this.fCurrentEntity.position--;
/*  421 */           this.fCurrentEntity.startPosition--;
/*  422 */           return null;
/*      */         } 
/*      */       } 
/*  425 */       char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  426 */       if (!XMLChar.isLowSurrogate(ch2) || 
/*  427 */         !XML11Char.isXML11NCNameStart(XMLChar.supplemental(ch, ch2))) {
/*  428 */         this.fCurrentEntity.position--;
/*  429 */         return null;
/*      */       } 
/*  431 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  432 */         invokeListeners(2);
/*  433 */         this.fCurrentEntity.ch[0] = ch;
/*  434 */         this.fCurrentEntity.ch[1] = ch2;
/*  435 */         offset = 0;
/*  436 */         if (load(2, false, false)) {
/*  437 */           this.fCurrentEntity.columnNumber += 2;
/*  438 */           String str = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
/*  439 */           return str;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  444 */       return null;
/*      */     } 
/*      */     
/*      */     while (true) {
/*  448 */       ch = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  449 */       if (XML11Char.isXML11NCName(ch)) {
/*  450 */         if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  451 */           int i = this.fCurrentEntity.position - offset;
/*  452 */           invokeListeners(i);
/*  453 */           if (i == this.fCurrentEntity.ch.length) {
/*      */             
/*  455 */             char[] tmp = new char[this.fCurrentEntity.ch.length << 1];
/*  456 */             System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */             
/*  458 */             this.fCurrentEntity.ch = tmp;
/*      */           } else {
/*      */             
/*  461 */             System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */           } 
/*      */           
/*  464 */           offset = 0;
/*  465 */           if (load(i, false, false))
/*      */             break; 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  470 */       if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  471 */         if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  472 */           int i = this.fCurrentEntity.position - offset;
/*  473 */           invokeListeners(i);
/*  474 */           if (i == this.fCurrentEntity.ch.length) {
/*      */             
/*  476 */             char[] tmp = new char[this.fCurrentEntity.ch.length << 1];
/*  477 */             System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */             
/*  479 */             this.fCurrentEntity.ch = tmp;
/*      */           } else {
/*      */             
/*  482 */             System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */           } 
/*      */           
/*  485 */           offset = 0;
/*  486 */           if (load(i, false, false)) {
/*  487 */             this.fCurrentEntity.startPosition--;
/*  488 */             this.fCurrentEntity.position--;
/*      */             break;
/*      */           } 
/*      */         } 
/*  492 */         char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  493 */         if (!XMLChar.isLowSurrogate(ch2) || 
/*  494 */           !XML11Char.isXML11NCName(XMLChar.supplemental(ch, ch2))) {
/*  495 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*  498 */         if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  499 */           int i = this.fCurrentEntity.position - offset;
/*  500 */           invokeListeners(i);
/*  501 */           if (i == this.fCurrentEntity.ch.length) {
/*      */             
/*  503 */             char[] tmp = new char[this.fCurrentEntity.ch.length << 1];
/*  504 */             System.arraycopy(this.fCurrentEntity.ch, offset, tmp, 0, i);
/*      */             
/*  506 */             this.fCurrentEntity.ch = tmp;
/*      */           } else {
/*      */             
/*  509 */             System.arraycopy(this.fCurrentEntity.ch, offset, this.fCurrentEntity.ch, 0, i);
/*      */           } 
/*      */           
/*  512 */           offset = 0;
/*  513 */           if (load(i, false, false)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/*      */     
/*  524 */     int length = this.fCurrentEntity.position - offset;
/*  525 */     this.fCurrentEntity.columnNumber += length;
/*      */ 
/*      */     
/*  528 */     String symbol = null;
/*  529 */     if (length > 0) {
/*  530 */       symbol = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */     }
/*  532 */     return symbol;
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
/*      */   protected boolean scanQName(QName qname, XMLScanner.NameType nt) throws IOException {
/*  562 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  563 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/*  567 */     int offset = this.fCurrentEntity.position;
/*  568 */     char ch = this.fCurrentEntity.ch[offset];
/*      */     
/*  570 */     if (XML11Char.isXML11NCNameStart(ch)) {
/*  571 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  572 */         invokeListeners(1);
/*  573 */         this.fCurrentEntity.ch[0] = ch;
/*  574 */         offset = 0;
/*  575 */         if (load(1, false, false)) {
/*  576 */           this.fCurrentEntity.columnNumber++;
/*  577 */           String name = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 1);
/*  578 */           qname.setValues(null, name, name, null);
/*  579 */           checkEntityLimit(nt, this.fCurrentEntity, 0, 1);
/*  580 */           return true;
/*      */         }
/*      */       
/*      */       } 
/*  584 */     } else if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  585 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  586 */         invokeListeners(1);
/*  587 */         this.fCurrentEntity.ch[0] = ch;
/*  588 */         offset = 0;
/*  589 */         if (load(1, false, false)) {
/*  590 */           this.fCurrentEntity.startPosition--;
/*  591 */           this.fCurrentEntity.position--;
/*  592 */           return false;
/*      */         } 
/*      */       } 
/*  595 */       char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  596 */       if (!XMLChar.isLowSurrogate(ch2) || 
/*  597 */         !XML11Char.isXML11NCNameStart(XMLChar.supplemental(ch, ch2))) {
/*  598 */         this.fCurrentEntity.position--;
/*  599 */         return false;
/*      */       } 
/*  601 */       if (++this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  602 */         invokeListeners(2);
/*  603 */         this.fCurrentEntity.ch[0] = ch;
/*  604 */         this.fCurrentEntity.ch[1] = ch2;
/*  605 */         offset = 0;
/*  606 */         if (load(2, false, false)) {
/*  607 */           this.fCurrentEntity.columnNumber += 2;
/*  608 */           String name = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, 0, 2);
/*  609 */           qname.setValues(null, name, name, null);
/*  610 */           checkEntityLimit(nt, this.fCurrentEntity, 0, 2);
/*  611 */           return true;
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  616 */       return false;
/*      */     } 
/*      */     
/*  619 */     int index = -1;
/*  620 */     int length = 0;
/*  621 */     boolean sawIncompleteSurrogatePair = false;
/*      */     while (true) {
/*  623 */       ch = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  624 */       if (XML11Char.isXML11Name(ch)) {
/*  625 */         if (ch == ':') {
/*  626 */           if (index != -1) {
/*      */             break;
/*      */           }
/*  629 */           index = this.fCurrentEntity.position;
/*      */           
/*  631 */           checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, index - offset);
/*      */         } 
/*  633 */         if ((length = checkBeforeLoad(this.fCurrentEntity, offset, index)) > 0) {
/*  634 */           if (index != -1) {
/*  635 */             index -= offset;
/*      */           }
/*  637 */           offset = 0;
/*  638 */           if (load(length, false, false))
/*      */             break; 
/*      */         } 
/*      */         continue;
/*      */       } 
/*  643 */       if (XML11Char.isXML11NameHighSurrogate(ch)) {
/*  644 */         if ((length = checkBeforeLoad(this.fCurrentEntity, offset, index)) > 0) {
/*  645 */           if (index != -1) {
/*  646 */             index -= offset;
/*      */           }
/*  648 */           offset = 0;
/*  649 */           if (load(length, false, false)) {
/*  650 */             sawIncompleteSurrogatePair = true;
/*  651 */             this.fCurrentEntity.startPosition--;
/*  652 */             this.fCurrentEntity.position--;
/*      */             break;
/*      */           } 
/*      */         } 
/*  656 */         char ch2 = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  657 */         if (!XMLChar.isLowSurrogate(ch2) || 
/*  658 */           !XML11Char.isXML11Name(XMLChar.supplemental(ch, ch2))) {
/*  659 */           sawIncompleteSurrogatePair = true;
/*  660 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*  663 */         if ((length = checkBeforeLoad(this.fCurrentEntity, offset, index)) > 0) {
/*  664 */           if (index != -1) {
/*  665 */             index -= offset;
/*      */           }
/*  667 */           offset = 0;
/*  668 */           if (load(length, false, false)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/*      */     
/*  679 */     length = this.fCurrentEntity.position - offset;
/*  680 */     this.fCurrentEntity.columnNumber += length;
/*      */     
/*  682 */     if (length > 0) {
/*  683 */       String prefix = null;
/*  684 */       String localpart = null;
/*  685 */       String rawname = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, length);
/*      */       
/*  687 */       if (index != -1) {
/*  688 */         int prefixLength = index - offset;
/*      */         
/*  690 */         checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, prefixLength);
/*  691 */         prefix = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, offset, prefixLength);
/*      */         
/*  693 */         int len = length - prefixLength - 1;
/*  694 */         int startLocal = index + 1;
/*  695 */         if (!XML11Char.isXML11NCNameStart(this.fCurrentEntity.ch[startLocal]) && (
/*  696 */           !XML11Char.isXML11NameHighSurrogate(this.fCurrentEntity.ch[startLocal]) || sawIncompleteSurrogatePair))
/*      */         {
/*  698 */           this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IllegalQName", null, (short)2);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  704 */         checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, index + 1, len);
/*  705 */         localpart = this.fSymbolTable.addSymbol(this.fCurrentEntity.ch, index + 1, len);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  710 */         localpart = rawname;
/*      */         
/*  712 */         checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, length);
/*      */       } 
/*  714 */       qname.setValues(prefix, localpart, rawname, null);
/*  715 */       checkEntityLimit(nt, this.fCurrentEntity, offset, length);
/*  716 */       return true;
/*      */     } 
/*  718 */     return false;
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
/*      */   protected int scanContent(XMLString content) throws IOException {
/*  751 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  752 */       load(0, true, true);
/*      */     }
/*  754 */     else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/*  755 */       invokeListeners(1);
/*  756 */       this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
/*  757 */       load(1, false, false);
/*  758 */       this.fCurrentEntity.position = 0;
/*  759 */       this.fCurrentEntity.startPosition = 0;
/*      */     } 
/*      */ 
/*      */     
/*  763 */     int offset = this.fCurrentEntity.position;
/*  764 */     int c = this.fCurrentEntity.ch[offset];
/*  765 */     int newlines = 0;
/*  766 */     boolean counted = false;
/*  767 */     boolean external = this.fCurrentEntity.isExternal();
/*  768 */     if (c == 10 || ((c == 13 || c == 133 || c == 8232) && external)) {
/*      */       do {
/*  770 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  771 */         if (c == 13 && external) {
/*  772 */           newlines++;
/*  773 */           this.fCurrentEntity.lineNumber++;
/*  774 */           this.fCurrentEntity.columnNumber = 1;
/*  775 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  776 */             checkEntityLimit(null, this.fCurrentEntity, offset, newlines);
/*  777 */             offset = 0;
/*  778 */             this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/*  779 */             this.fCurrentEntity.position = newlines;
/*  780 */             this.fCurrentEntity.startPosition = newlines;
/*  781 */             if (load(newlines, false, true)) {
/*  782 */               counted = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*  786 */           int cc = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  787 */           if (cc == 10 || cc == 133) {
/*  788 */             this.fCurrentEntity.position++;
/*  789 */             offset++;
/*      */           }
/*      */           else {
/*      */             
/*  793 */             newlines++;
/*      */           }
/*      */         
/*  796 */         } else if (c == 10 || ((c == 133 || c == 8232) && external)) {
/*  797 */           newlines++;
/*  798 */           this.fCurrentEntity.lineNumber++;
/*  799 */           this.fCurrentEntity.columnNumber = 1;
/*  800 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  801 */             checkEntityLimit(null, this.fCurrentEntity, offset, newlines);
/*  802 */             offset = 0;
/*  803 */             this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/*  804 */             this.fCurrentEntity.position = newlines;
/*  805 */             this.fCurrentEntity.startPosition = newlines;
/*  806 */             if (load(newlines, false, true)) {
/*  807 */               counted = true;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  813 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*  816 */       } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
/*  817 */       for (int i = offset; i < this.fCurrentEntity.position; i++) {
/*  818 */         this.fCurrentEntity.ch[i] = '\n';
/*      */       }
/*  820 */       int j = this.fCurrentEntity.position - offset;
/*  821 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/*  822 */         checkEntityLimit(null, this.fCurrentEntity, offset, j);
/*  823 */         content.setValues(this.fCurrentEntity.ch, offset, j);
/*  824 */         return -1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  829 */     if (external) {
/*  830 */       while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/*  831 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  832 */         if (!XML11Char.isXML11Content(c) || c == 133 || c == 8232) {
/*  833 */           this.fCurrentEntity.position--;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  839 */       while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/*  840 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*      */         
/*  842 */         if (!XML11Char.isXML11InternalEntityContent(c)) {
/*  843 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  848 */     int length = this.fCurrentEntity.position - offset;
/*  849 */     this.fCurrentEntity.columnNumber += length - newlines;
/*  850 */     if (!counted) {
/*  851 */       checkEntityLimit(null, this.fCurrentEntity, offset, length);
/*      */     }
/*  853 */     content.setValues(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */     
/*  856 */     if (this.fCurrentEntity.position != this.fCurrentEntity.count) {
/*  857 */       c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*      */ 
/*      */       
/*  860 */       if ((c == 13 || c == 133 || c == 8232) && external) {
/*  861 */         c = 10;
/*      */       }
/*      */     } else {
/*      */       
/*  865 */       c = -1;
/*      */     } 
/*  867 */     return c;
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
/*      */   protected int scanLiteral(int quote, XMLString content, boolean isNSURI) throws IOException {
/*  903 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  904 */       load(0, true, true);
/*      */     }
/*  906 */     else if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/*  907 */       invokeListeners(1);
/*  908 */       this.fCurrentEntity.ch[0] = this.fCurrentEntity.ch[this.fCurrentEntity.count - 1];
/*  909 */       load(1, false, false);
/*  910 */       this.fCurrentEntity.startPosition = 0;
/*  911 */       this.fCurrentEntity.position = 0;
/*      */     } 
/*      */ 
/*      */     
/*  915 */     int offset = this.fCurrentEntity.position;
/*  916 */     int c = this.fCurrentEntity.ch[offset];
/*  917 */     int newlines = 0;
/*  918 */     boolean external = this.fCurrentEntity.isExternal();
/*  919 */     if (c == 10 || ((c == 13 || c == 133 || c == 8232) && external)) {
/*      */       do {
/*  921 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  922 */         if (c == 13 && external) {
/*  923 */           newlines++;
/*  924 */           this.fCurrentEntity.lineNumber++;
/*  925 */           this.fCurrentEntity.columnNumber = 1;
/*  926 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  927 */             offset = 0;
/*  928 */             this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/*  929 */             this.fCurrentEntity.position = newlines;
/*  930 */             this.fCurrentEntity.startPosition = newlines;
/*  931 */             if (load(newlines, false, true)) {
/*      */               break;
/*      */             }
/*      */           } 
/*  935 */           int cc = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*  936 */           if (cc == 10 || cc == 133) {
/*  937 */             this.fCurrentEntity.position++;
/*  938 */             offset++;
/*      */           }
/*      */           else {
/*      */             
/*  942 */             newlines++;
/*      */           }
/*      */         
/*  945 */         } else if (c == 10 || ((c == 133 || c == 8232) && external)) {
/*  946 */           newlines++;
/*  947 */           this.fCurrentEntity.lineNumber++;
/*  948 */           this.fCurrentEntity.columnNumber = 1;
/*  949 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/*  950 */             offset = 0;
/*  951 */             this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/*  952 */             this.fCurrentEntity.position = newlines;
/*  953 */             this.fCurrentEntity.startPosition = newlines;
/*  954 */             if (load(newlines, false, true)) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } else {
/*      */           
/*  960 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*  963 */       } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
/*  964 */       for (int i = offset; i < this.fCurrentEntity.position; i++) {
/*  965 */         this.fCurrentEntity.ch[i] = '\n';
/*      */       }
/*  967 */       int j = this.fCurrentEntity.position - offset;
/*  968 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/*  969 */         content.setValues(this.fCurrentEntity.ch, offset, j);
/*  970 */         return -1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  975 */     if (external) {
/*  976 */       while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/*  977 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*  978 */         if (c == quote || c == 37 || !XML11Char.isXML11Content(c) || c == 133 || c == 8232) {
/*      */           
/*  980 */           this.fCurrentEntity.position--;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  986 */       while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/*  987 */         c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/*      */         
/*  989 */         if ((c == quote && !this.fCurrentEntity.literal) || c == 37 || 
/*  990 */           !XML11Char.isXML11InternalEntityContent(c)) {
/*  991 */           this.fCurrentEntity.position--;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  996 */     int length = this.fCurrentEntity.position - offset;
/*  997 */     this.fCurrentEntity.columnNumber += length - newlines;
/*      */     
/*  999 */     checkEntityLimit(null, this.fCurrentEntity, offset, length);
/* 1000 */     if (isNSURI) {
/* 1001 */       checkLimit(XMLSecurityManager.Limit.MAX_NAME_LIMIT, this.fCurrentEntity, offset, length);
/*      */     }
/* 1003 */     content.setValues(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */     
/* 1006 */     if (this.fCurrentEntity.position != this.fCurrentEntity.count) {
/* 1007 */       c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/*      */ 
/*      */ 
/*      */       
/* 1011 */       if (c == quote && this.fCurrentEntity.literal) {
/* 1012 */         c = -1;
/*      */       }
/*      */     } else {
/*      */       
/* 1016 */       c = -1;
/*      */     } 
/* 1018 */     return c;
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
/*      */   protected boolean scanData(String delimiter, XMLStringBuffer buffer) throws IOException {
/* 1055 */     boolean done = false;
/* 1056 */     int delimLen = delimiter.length();
/* 1057 */     char charAt0 = delimiter.charAt(0);
/* 1058 */     boolean external = this.fCurrentEntity.isExternal();
/*      */     
/*      */     while (true) {
/* 1061 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1062 */         load(0, true, false);
/*      */       }
/*      */       
/* 1065 */       boolean bNextEntity = false;
/*      */       
/* 1067 */       while (this.fCurrentEntity.position >= this.fCurrentEntity.count - delimLen && !bNextEntity) {
/*      */ 
/*      */         
/* 1070 */         System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.position, this.fCurrentEntity.ch, 0, this.fCurrentEntity.count - this.fCurrentEntity.position);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1076 */         bNextEntity = load(this.fCurrentEntity.count - this.fCurrentEntity.position, false, false);
/* 1077 */         this.fCurrentEntity.position = 0;
/* 1078 */         this.fCurrentEntity.startPosition = 0;
/*      */       } 
/*      */       
/* 1081 */       if (this.fCurrentEntity.position >= this.fCurrentEntity.count - delimLen) {
/*      */         
/* 1083 */         int i = this.fCurrentEntity.count - this.fCurrentEntity.position;
/* 1084 */         checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, this.fCurrentEntity.position, i);
/* 1085 */         buffer.append(this.fCurrentEntity.ch, this.fCurrentEntity.position, i);
/* 1086 */         this.fCurrentEntity.columnNumber += this.fCurrentEntity.count;
/* 1087 */         this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/* 1088 */         this.fCurrentEntity.position = this.fCurrentEntity.count;
/* 1089 */         this.fCurrentEntity.startPosition = this.fCurrentEntity.count;
/* 1090 */         load(0, true, false);
/* 1091 */         return false;
/*      */       } 
/*      */ 
/*      */       
/* 1095 */       int offset = this.fCurrentEntity.position;
/* 1096 */       int c = this.fCurrentEntity.ch[offset];
/* 1097 */       int newlines = 0;
/* 1098 */       if (c == 10 || ((c == 13 || c == 133 || c == 8232) && external)) {
/*      */         do {
/* 1100 */           c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1101 */           if (c == 13 && external) {
/* 1102 */             newlines++;
/* 1103 */             this.fCurrentEntity.lineNumber++;
/* 1104 */             this.fCurrentEntity.columnNumber = 1;
/* 1105 */             if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1106 */               offset = 0;
/* 1107 */               this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/* 1108 */               this.fCurrentEntity.position = newlines;
/* 1109 */               this.fCurrentEntity.startPosition = newlines;
/* 1110 */               if (load(newlines, false, true)) {
/*      */                 break;
/*      */               }
/*      */             } 
/* 1114 */             int cc = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 1115 */             if (cc == 10 || cc == 133) {
/* 1116 */               this.fCurrentEntity.position++;
/* 1117 */               offset++;
/*      */             }
/*      */             else {
/*      */               
/* 1121 */               newlines++;
/*      */             }
/*      */           
/* 1124 */           } else if (c == 10 || ((c == 133 || c == 8232) && external)) {
/* 1125 */             newlines++;
/* 1126 */             this.fCurrentEntity.lineNumber++;
/* 1127 */             this.fCurrentEntity.columnNumber = 1;
/* 1128 */             if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1129 */               offset = 0;
/* 1130 */               this.fCurrentEntity.baseCharOffset += this.fCurrentEntity.position - this.fCurrentEntity.startPosition;
/* 1131 */               this.fCurrentEntity.position = newlines;
/* 1132 */               this.fCurrentEntity.startPosition = newlines;
/* 1133 */               this.fCurrentEntity.count = newlines;
/* 1134 */               if (load(newlines, false, true)) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/* 1140 */             this.fCurrentEntity.position--;
/*      */             break;
/*      */           } 
/* 1143 */         } while (this.fCurrentEntity.position < this.fCurrentEntity.count - 1);
/* 1144 */         for (int i = offset; i < this.fCurrentEntity.position; i++) {
/* 1145 */           this.fCurrentEntity.ch[i] = '\n';
/*      */         }
/* 1147 */         int j = this.fCurrentEntity.position - offset;
/* 1148 */         if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1149 */           checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, j);
/* 1150 */           buffer.append(this.fCurrentEntity.ch, offset, j);
/* 1151 */           return true;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1156 */       if (external) {
/* 1157 */         while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/* 1158 */           c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1159 */           if (c == charAt0) {
/*      */             
/* 1161 */             int delimOffset = this.fCurrentEntity.position - 1;
/* 1162 */             for (int i = 1; i < delimLen; i++) {
/* 1163 */               if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1164 */                 this.fCurrentEntity.position -= i;
/*      */                 break label117;
/*      */               } 
/* 1167 */               c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1168 */               if (delimiter.charAt(i) != c) {
/* 1169 */                 this.fCurrentEntity.position--;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1173 */             if (this.fCurrentEntity.position == delimOffset + delimLen) {
/* 1174 */               done = true; break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1178 */           if (c == 10 || c == 13 || c == 133 || c == 8232) {
/* 1179 */             this.fCurrentEntity.position--;
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 1184 */           if (!XML11Char.isXML11ValidLiteral(c)) {
/* 1185 */             this.fCurrentEntity.position--;
/* 1186 */             int i = this.fCurrentEntity.position - offset;
/* 1187 */             this.fCurrentEntity.columnNumber += i - newlines;
/* 1188 */             checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, i);
/* 1189 */             buffer.append(this.fCurrentEntity.ch, offset, i);
/* 1190 */             return true;
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 1195 */         label117: while (this.fCurrentEntity.position < this.fCurrentEntity.count) {
/* 1196 */           c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1197 */           if (c == charAt0) {
/*      */             
/* 1199 */             int delimOffset = this.fCurrentEntity.position - 1;
/* 1200 */             for (int i = 1; i < delimLen; i++) {
/* 1201 */               if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1202 */                 this.fCurrentEntity.position -= i;
/*      */                 break label117;
/*      */               } 
/* 1205 */               c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1206 */               if (delimiter.charAt(i) != c) {
/* 1207 */                 this.fCurrentEntity.position--;
/*      */                 break;
/*      */               } 
/*      */             } 
/* 1211 */             if (this.fCurrentEntity.position == delimOffset + delimLen) {
/* 1212 */               done = true; break;
/*      */             } 
/*      */             continue;
/*      */           } 
/* 1216 */           if (c == 10) {
/* 1217 */             this.fCurrentEntity.position--;
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/* 1222 */           if (!XML11Char.isXML11Valid(c)) {
/* 1223 */             this.fCurrentEntity.position--;
/* 1224 */             int i = this.fCurrentEntity.position - offset;
/* 1225 */             this.fCurrentEntity.columnNumber += i - newlines;
/* 1226 */             checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, i);
/* 1227 */             buffer.append(this.fCurrentEntity.ch, offset, i);
/* 1228 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1232 */       int length = this.fCurrentEntity.position - offset;
/* 1233 */       this.fCurrentEntity.columnNumber += length - newlines;
/* 1234 */       checkEntityLimit(XMLScanner.NameType.COMMENT, this.fCurrentEntity, offset, length);
/* 1235 */       if (done) {
/* 1236 */         length -= delimLen;
/*      */       }
/* 1238 */       buffer.append(this.fCurrentEntity.ch, offset, length);
/*      */ 
/*      */       
/* 1241 */       if (done) {
/* 1242 */         return !done;
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean skipChar(int c, XMLScanner.NameType nt) throws IOException {
/* 1262 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1263 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/* 1267 */     int offset = this.fCurrentEntity.position;
/* 1268 */     int cc = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 1269 */     if (cc == c) {
/* 1270 */       this.fCurrentEntity.position++;
/* 1271 */       if (c == 10) {
/* 1272 */         this.fCurrentEntity.lineNumber++;
/* 1273 */         this.fCurrentEntity.columnNumber = 1;
/*      */       } else {
/*      */         
/* 1276 */         this.fCurrentEntity.columnNumber++;
/*      */       } 
/* 1278 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1279 */       return true;
/*      */     } 
/* 1281 */     if (c == 10 && (cc == 8232 || cc == 133) && this.fCurrentEntity.isExternal()) {
/* 1282 */       this.fCurrentEntity.position++;
/* 1283 */       this.fCurrentEntity.lineNumber++;
/* 1284 */       this.fCurrentEntity.columnNumber = 1;
/* 1285 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1286 */       return true;
/*      */     } 
/* 1288 */     if (c == 10 && cc == 13 && this.fCurrentEntity.isExternal()) {
/*      */       
/* 1290 */       if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1291 */         invokeListeners(1);
/* 1292 */         this.fCurrentEntity.ch[0] = (char)cc;
/* 1293 */         load(1, false, false);
/*      */       } 
/* 1295 */       int ccc = this.fCurrentEntity.ch[++this.fCurrentEntity.position];
/* 1296 */       if (ccc == 10 || ccc == 133) {
/* 1297 */         this.fCurrentEntity.position++;
/*      */       }
/* 1299 */       this.fCurrentEntity.lineNumber++;
/* 1300 */       this.fCurrentEntity.columnNumber = 1;
/* 1301 */       checkEntityLimit(nt, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1302 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1306 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean skipSpaces() throws IOException {
/* 1327 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1328 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1338 */     if (this.fCurrentEntity == null) {
/* 1339 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1343 */     int c = this.fCurrentEntity.ch[this.fCurrentEntity.position];
/* 1344 */     int offset = this.fCurrentEntity.position - 1;
/*      */     
/* 1346 */     if (this.fCurrentEntity.isExternal()) {
/* 1347 */       if (XML11Char.isXML11Space(c)) {
/*      */         while (true) {
/* 1349 */           boolean entityChanged = false;
/*      */           
/* 1351 */           if (c == 10 || c == 13 || c == 133 || c == 8232) {
/* 1352 */             this.fCurrentEntity.lineNumber++;
/* 1353 */             this.fCurrentEntity.columnNumber = 1;
/* 1354 */             if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1355 */               invokeListeners(1);
/* 1356 */               this.fCurrentEntity.ch[0] = (char)c;
/* 1357 */               entityChanged = load(1, true, false);
/* 1358 */               if (!entityChanged) {
/*      */ 
/*      */                 
/* 1361 */                 this.fCurrentEntity.startPosition = 0;
/* 1362 */                 this.fCurrentEntity.position = 0;
/* 1363 */               } else if (this.fCurrentEntity == null) {
/* 1364 */                 return true;
/*      */               } 
/*      */             } 
/*      */             
/* 1368 */             if (c == 13) {
/*      */ 
/*      */               
/* 1371 */               int cc = this.fCurrentEntity.ch[++this.fCurrentEntity.position];
/* 1372 */               if (cc != 10 && cc != 133) {
/* 1373 */                 this.fCurrentEntity.position--;
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/* 1378 */             this.fCurrentEntity.columnNumber++;
/*      */           } 
/*      */ 
/*      */           
/* 1382 */           checkEntityLimit(null, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1383 */           offset = this.fCurrentEntity.position;
/*      */ 
/*      */           
/* 1386 */           if (!entityChanged)
/* 1387 */             this.fCurrentEntity.position++; 
/* 1388 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1389 */             load(0, true, true);
/*      */             
/* 1391 */             if (this.fCurrentEntity == null) {
/* 1392 */               return true;
/*      */             }
/*      */           } 
/*      */           
/* 1396 */           if (!XML11Char.isXML11Space(c = this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
/* 1397 */             return true;
/*      */           }
/*      */         } 
/*      */       }
/* 1401 */     } else if (XMLChar.isSpace(c)) {
/*      */       while (true) {
/* 1403 */         boolean entityChanged = false;
/*      */         
/* 1405 */         if (c == 10) {
/* 1406 */           this.fCurrentEntity.lineNumber++;
/* 1407 */           this.fCurrentEntity.columnNumber = 1;
/* 1408 */           if (this.fCurrentEntity.position == this.fCurrentEntity.count - 1) {
/* 1409 */             invokeListeners(1);
/* 1410 */             this.fCurrentEntity.ch[0] = (char)c;
/* 1411 */             entityChanged = load(1, true, false);
/* 1412 */             if (!entityChanged) {
/*      */ 
/*      */               
/* 1415 */               this.fCurrentEntity.startPosition = 0;
/* 1416 */               this.fCurrentEntity.position = 0;
/* 1417 */             } else if (this.fCurrentEntity == null) {
/* 1418 */               return true;
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 1423 */           this.fCurrentEntity.columnNumber++;
/*      */         } 
/*      */ 
/*      */         
/* 1427 */         checkEntityLimit(null, this.fCurrentEntity, offset, this.fCurrentEntity.position - offset);
/* 1428 */         offset = this.fCurrentEntity.position;
/*      */ 
/*      */         
/* 1431 */         if (!entityChanged)
/* 1432 */           this.fCurrentEntity.position++; 
/* 1433 */         if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1434 */           load(0, true, true);
/*      */           
/* 1436 */           if (this.fCurrentEntity == null) {
/* 1437 */             return true;
/*      */           }
/*      */         } 
/*      */         
/* 1441 */         if (!XMLChar.isSpace(c = this.fCurrentEntity.ch[this.fCurrentEntity.position])) {
/* 1442 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 1446 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean skipString(String s) throws IOException {
/* 1466 */     if (this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1467 */       load(0, true, true);
/*      */     }
/*      */ 
/*      */     
/* 1471 */     int length = s.length();
/* 1472 */     int beforeSkip = this.fCurrentEntity.position;
/* 1473 */     for (int i = 0; i < length; i++) {
/* 1474 */       char c = this.fCurrentEntity.ch[this.fCurrentEntity.position++];
/* 1475 */       if (c != s.charAt(i)) {
/* 1476 */         this.fCurrentEntity.position -= i + 1;
/* 1477 */         return false;
/*      */       } 
/* 1479 */       if (i < length - 1 && this.fCurrentEntity.position == this.fCurrentEntity.count) {
/* 1480 */         invokeListeners(0);
/* 1481 */         System.arraycopy(this.fCurrentEntity.ch, this.fCurrentEntity.count - i - 1, this.fCurrentEntity.ch, 0, i + 1);
/*      */ 
/*      */         
/* 1484 */         if (load(i + 1, false, false)) {
/* 1485 */           this.fCurrentEntity.startPosition -= i + 1;
/* 1486 */           this.fCurrentEntity.position -= i + 1;
/* 1487 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1491 */     this.fCurrentEntity.columnNumber += length;
/* 1492 */     if (!this.detectingVersion) {
/* 1493 */       checkEntityLimit(null, this.fCurrentEntity, beforeSkip, length);
/*      */     }
/* 1495 */     return true;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/XML11EntityScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */