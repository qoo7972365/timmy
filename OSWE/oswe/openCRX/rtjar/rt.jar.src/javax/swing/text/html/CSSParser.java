/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CSSParser
/*     */ {
/*  91 */   private static final char[] charMapping = new char[] { Character.MIN_VALUE, Character.MIN_VALUE, '[', ']', '{', '}', '(', ')', Character.MIN_VALUE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   private int[] unitStack = new int[2];
/* 138 */   private char[] tokenBuffer = new char[80];
/* 139 */   private StringBuffer unitBuffer = new StringBuffer(); private static final int IDENTIFIER = 1; private static final int BRACKET_OPEN = 2; private static final int BRACKET_CLOSE = 3; private static final int BRACE_OPEN = 4; private static final int BRACE_CLOSE = 5; private static final int PAREN_OPEN = 6;
/*     */   private static final int PAREN_CLOSE = 7;
/*     */   private static final int END = -1;
/*     */   
/*     */   void parse(Reader paramReader, CSSParserCallback paramCSSParserCallback, boolean paramBoolean) throws IOException {
/* 144 */     this.callback = paramCSSParserCallback;
/* 145 */     this.stackCount = this.tokenBufferLength = 0;
/* 146 */     this.reader = paramReader;
/* 147 */     this.encounteredRuleSet = false;
/*     */     try {
/* 149 */       if (paramBoolean) {
/* 150 */         parseDeclarationBlock();
/*     */       } else {
/*     */         
/* 153 */         while (getNextStatement());
/*     */       } 
/*     */     } finally {
/* 156 */       paramCSSParserCallback = null;
/* 157 */       paramReader = null;
/*     */     } 
/*     */   }
/*     */   private boolean didPushChar; private int pushedChar; private int stackCount; private Reader reader; private boolean encounteredRuleSet;
/*     */   private CSSParserCallback callback;
/*     */   private int tokenBufferLength;
/*     */   private boolean readWS;
/*     */   
/*     */   private boolean getNextStatement() throws IOException {
/* 166 */     this.unitBuffer.setLength(0);
/*     */     
/* 168 */     int i = nextToken(false);
/*     */     
/* 170 */     switch (i) {
/*     */       case 1:
/* 172 */         if (this.tokenBufferLength > 0) {
/* 173 */           if (this.tokenBuffer[0] == '@') {
/* 174 */             parseAtRule();
/*     */           } else {
/*     */             
/* 177 */             this.encounteredRuleSet = true;
/* 178 */             parseRuleSet();
/*     */           } 
/*     */         }
/* 181 */         return true;
/*     */       case 2:
/*     */       case 4:
/*     */       case 6:
/* 185 */         parseTillClosed(i);
/* 186 */         return true;
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 5:
/*     */       case 7:
/* 192 */         throw new RuntimeException("Unexpected top level block close");
/*     */       
/*     */       case -1:
/* 195 */         return false;
/*     */     } 
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseAtRule() throws IOException {
/* 205 */     boolean bool1 = false;
/* 206 */     boolean bool2 = (this.tokenBufferLength == 7 && this.tokenBuffer[0] == '@' && this.tokenBuffer[1] == 'i' && this.tokenBuffer[2] == 'm' && this.tokenBuffer[3] == 'p' && this.tokenBuffer[4] == 'o' && this.tokenBuffer[5] == 'r' && this.tokenBuffer[6] == 't') ? true : false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     this.unitBuffer.setLength(0);
/* 213 */     while (!bool1) {
/* 214 */       int j, i = nextToken(';');
/*     */       
/* 216 */       switch (i) {
/*     */         case 1:
/* 218 */           if (this.tokenBufferLength > 0 && this.tokenBuffer[this.tokenBufferLength - 1] == ';') {
/*     */             
/* 220 */             this.tokenBufferLength--;
/* 221 */             bool1 = true;
/*     */           } 
/* 223 */           if (this.tokenBufferLength > 0) {
/* 224 */             if (this.unitBuffer.length() > 0 && this.readWS) {
/* 225 */               this.unitBuffer.append(' ');
/*     */             }
/* 227 */             this.unitBuffer.append(this.tokenBuffer, 0, this.tokenBufferLength);
/*     */           } 
/*     */ 
/*     */         
/*     */         case 4:
/* 232 */           if (this.unitBuffer.length() > 0 && this.readWS) {
/* 233 */             this.unitBuffer.append(' ');
/*     */           }
/* 235 */           this.unitBuffer.append(charMapping[i]);
/* 236 */           parseTillClosed(i);
/* 237 */           bool1 = true;
/*     */ 
/*     */           
/* 240 */           j = readWS();
/* 241 */           if (j != -1 && j != 59) {
/* 242 */             pushChar(j);
/*     */           }
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 6:
/* 248 */           this.unitBuffer.append(charMapping[i]);
/* 249 */           parseTillClosed(i);
/*     */         case 3:
/*     */         case 5:
/*     */         case 7:
/* 253 */           throw new RuntimeException("Unexpected close in @ rule");
/*     */         
/*     */         case -1:
/* 256 */           bool1 = true;
/*     */       } 
/*     */     
/*     */     } 
/* 260 */     if (bool2 && !this.encounteredRuleSet) {
/* 261 */       this.callback.handleImport(this.unitBuffer.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseRuleSet() throws IOException {
/* 270 */     if (parseSelectors()) {
/* 271 */       this.callback.startRule();
/* 272 */       parseDeclarationBlock();
/* 273 */       this.callback.endRule();
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
/*     */   private boolean parseSelectors() throws IOException {
/* 285 */     if (this.tokenBufferLength > 0) {
/* 286 */       this.callback.handleSelector(new String(this.tokenBuffer, 0, this.tokenBufferLength));
/*     */     }
/*     */ 
/*     */     
/* 290 */     this.unitBuffer.setLength(0); while (true) {
/*     */       int i;
/* 292 */       while ((i = nextToken(false)) == 1) {
/* 293 */         if (this.tokenBufferLength > 0) {
/* 294 */           this.callback.handleSelector(new String(this.tokenBuffer, 0, this.tokenBufferLength));
/*     */         }
/*     */       } 
/*     */       
/* 298 */       switch (i) {
/*     */         case 4:
/* 300 */           return true;
/*     */         case 2:
/*     */         case 6:
/* 303 */           parseTillClosed(i);
/*     */ 
/*     */           
/* 306 */           this.unitBuffer.setLength(0);
/*     */         case 3:
/*     */         case 5:
/*     */         case 7:
/* 310 */           throw new RuntimeException("Unexpected block close in selector");
/*     */         case -1:
/*     */           break;
/*     */       } 
/* 314 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseDeclarationBlock() throws IOException {
/*     */     while (true) {
/* 325 */       int i = parseDeclaration();
/* 326 */       switch (i) { case -1:
/*     */         case 5:
/*     */           return;
/*     */         case 3:
/*     */         case 7:
/*     */           break; } 
/* 332 */     }  throw new RuntimeException("Unexpected close in declaration block");
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
/*     */   private int parseDeclaration() throws IOException {
/*     */     int i;
/* 347 */     if ((i = parseIdentifiers(':', false)) != 1) {
/* 348 */       return i;
/*     */     }
/*     */     
/* 351 */     for (int j = this.unitBuffer.length() - 1; j >= 0; j--) {
/* 352 */       this.unitBuffer.setCharAt(j, 
/* 353 */           Character.toLowerCase(this.unitBuffer.charAt(j)));
/*     */     }
/* 355 */     this.callback.handleProperty(this.unitBuffer.toString());
/*     */     
/* 357 */     i = parseIdentifiers(';', true);
/* 358 */     this.callback.handleValue(this.unitBuffer.toString());
/* 359 */     return i;
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
/*     */   private int parseIdentifiers(char paramChar, boolean paramBoolean) throws IOException {
/*     */     int i;
/* 372 */     this.unitBuffer.setLength(0); while (true) {
/*     */       int j;
/* 374 */       i = nextToken(paramChar);
/*     */       
/* 376 */       switch (i) {
/*     */         case 1:
/* 378 */           if (this.tokenBufferLength > 0) {
/* 379 */             if (this.tokenBuffer[this.tokenBufferLength - 1] == paramChar) {
/* 380 */               if (--this.tokenBufferLength > 0) {
/* 381 */                 if (this.readWS && this.unitBuffer.length() > 0) {
/* 382 */                   this.unitBuffer.append(' ');
/*     */                 }
/* 384 */                 this.unitBuffer.append(this.tokenBuffer, 0, this.tokenBufferLength);
/*     */               } 
/*     */               
/* 387 */               return 1;
/*     */             } 
/* 389 */             if (this.readWS && this.unitBuffer.length() > 0) {
/* 390 */               this.unitBuffer.append(' ');
/*     */             }
/* 392 */             this.unitBuffer.append(this.tokenBuffer, 0, this.tokenBufferLength);
/*     */           } 
/*     */ 
/*     */         
/*     */         case 2:
/*     */         case 4:
/*     */         case 6:
/* 399 */           j = this.unitBuffer.length();
/* 400 */           if (paramBoolean) {
/* 401 */             this.unitBuffer.append(charMapping[i]);
/*     */           }
/* 403 */           parseTillClosed(i);
/* 404 */           if (!paramBoolean) {
/* 405 */             this.unitBuffer.setLength(j);
/*     */           }
/*     */         
/*     */         case -1:
/*     */         case 3:
/*     */         case 5:
/*     */         case 7:
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 416 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseTillClosed(int paramInt) throws IOException {
/* 427 */     boolean bool = false;
/*     */     
/* 429 */     startBlock(paramInt);
/* 430 */     while (!bool) {
/* 431 */       int i = nextToken(false);
/* 432 */       switch (i) {
/*     */         case 1:
/* 434 */           if (this.unitBuffer.length() > 0 && this.readWS) {
/* 435 */             this.unitBuffer.append(' ');
/*     */           }
/* 437 */           if (this.tokenBufferLength > 0) {
/* 438 */             this.unitBuffer.append(this.tokenBuffer, 0, this.tokenBufferLength);
/*     */           }
/*     */         case 2:
/*     */         case 4:
/*     */         case 6:
/* 443 */           if (this.unitBuffer.length() > 0 && this.readWS) {
/* 444 */             this.unitBuffer.append(' ');
/*     */           }
/* 446 */           this.unitBuffer.append(charMapping[i]);
/* 447 */           startBlock(i);
/*     */         case 3:
/*     */         case 5:
/*     */         case 7:
/* 451 */           if (this.unitBuffer.length() > 0 && this.readWS) {
/* 452 */             this.unitBuffer.append(' ');
/*     */           }
/* 454 */           this.unitBuffer.append(charMapping[i]);
/* 455 */           endBlock(i);
/* 456 */           if (!inBlock()) {
/* 457 */             bool = true;
/*     */           }
/*     */ 
/*     */ 
/*     */         
/*     */         case -1:
/* 463 */           throw new RuntimeException("Unclosed block");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextToken(char paramChar) throws IOException {
/* 472 */     this.readWS = false;
/*     */     
/* 474 */     int i = readWS();
/*     */     
/* 476 */     switch (i) {
/*     */       case 39:
/* 478 */         readTill('\'');
/* 479 */         if (this.tokenBufferLength > 0) {
/* 480 */           this.tokenBufferLength--;
/*     */         }
/* 482 */         return 1;
/*     */       case 34:
/* 484 */         readTill('"');
/* 485 */         if (this.tokenBufferLength > 0) {
/* 486 */           this.tokenBufferLength--;
/*     */         }
/* 488 */         return 1;
/*     */       case 91:
/* 490 */         return 2;
/*     */       case 93:
/* 492 */         return 3;
/*     */       case 123:
/* 494 */         return 4;
/*     */       case 125:
/* 496 */         return 5;
/*     */       case 40:
/* 498 */         return 6;
/*     */       case 41:
/* 500 */         return 7;
/*     */       case -1:
/* 502 */         return -1;
/*     */     } 
/* 504 */     pushChar(i);
/* 505 */     getIdentifier(paramChar);
/* 506 */     return 1;
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
/*     */   private boolean getIdentifier(char paramChar) throws IOException {
/* 518 */     boolean bool1 = false;
/* 519 */     boolean bool2 = false;
/* 520 */     byte b = 0;
/* 521 */     int i = 0;
/*     */     
/* 523 */     char c = paramChar;
/*     */ 
/*     */ 
/*     */     
/* 527 */     int j = 0;
/*     */     
/* 529 */     this.tokenBufferLength = 0;
/* 530 */     while (!bool2) {
/* 531 */       byte b1; int k = readChar();
/* 532 */       switch (k) {
/*     */         case 92:
/* 534 */           b1 = 1; break;
/*     */         case 48: case 49: case 50: case 51: case 52: case 53: case 54:
/*     */         case 55:
/*     */         case 56:
/*     */         case 57:
/* 539 */           b1 = 2;
/* 540 */           j = k - 48; break;
/*     */         case 97: case 98: case 99: case 100:
/*     */         case 101:
/*     */         case 102:
/* 544 */           b1 = 2;
/* 545 */           j = k - 97 + 10; break;
/*     */         case 65: case 66: case 67: case 68:
/*     */         case 69:
/*     */         case 70:
/* 549 */           b1 = 2;
/* 550 */           j = k - 65 + 10; break;
/*     */         case 9: case 10: case 13: case 32: case 34: case 39: case 40: case 41:
/*     */         case 91:
/*     */         case 93:
/*     */         case 123:
/*     */         case 125:
/* 556 */           b1 = 3;
/*     */           break;
/*     */         
/*     */         case 47:
/* 560 */           b1 = 4;
/*     */           break;
/*     */ 
/*     */         
/*     */         case -1:
/* 565 */           bool2 = true;
/* 566 */           b1 = 0;
/*     */           break;
/*     */         
/*     */         default:
/* 570 */           b1 = 0;
/*     */           break;
/*     */       } 
/* 573 */       if (bool1) {
/* 574 */         if (b1 == 2) {
/*     */           
/* 576 */           i = i * 16 + j;
/* 577 */           if (++b == 4) {
/* 578 */             bool1 = false;
/* 579 */             append((char)i);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 584 */         bool1 = false;
/* 585 */         if (b > 0) {
/* 586 */           append((char)i);
/*     */           
/* 588 */           pushChar(k); continue;
/*     */         } 
/* 590 */         if (!bool2) {
/* 591 */           append((char)k);
/*     */         }
/*     */         continue;
/*     */       } 
/* 595 */       if (!bool2) {
/* 596 */         if (b1 == 1) {
/* 597 */           bool1 = true;
/* 598 */           i = b = 0; continue;
/*     */         } 
/* 600 */         if (b1 == 3) {
/* 601 */           bool2 = true;
/* 602 */           pushChar(k); continue;
/*     */         } 
/* 604 */         if (b1 == 4) {
/*     */           
/* 606 */           k = readChar();
/* 607 */           if (k == 42) {
/* 608 */             bool2 = true;
/* 609 */             readComment();
/* 610 */             this.readWS = true;
/*     */             continue;
/*     */           } 
/* 613 */           append('/');
/* 614 */           if (k == -1) {
/* 615 */             bool2 = true;
/*     */             continue;
/*     */           } 
/* 618 */           pushChar(k);
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 623 */         append((char)k);
/* 624 */         if (k == c) {
/* 625 */           bool2 = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 630 */     return (this.tokenBufferLength > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readTill(char paramChar) throws IOException {
/* 638 */     boolean bool1 = false;
/* 639 */     byte b = 0;
/* 640 */     int i = 0;
/*     */     
/* 642 */     boolean bool2 = false;
/* 643 */     char c = paramChar;
/*     */ 
/*     */     
/* 646 */     int j = 0;
/*     */     
/* 648 */     this.tokenBufferLength = 0;
/* 649 */     while (!bool2) {
/* 650 */       byte b1; int k = readChar();
/* 651 */       switch (k) {
/*     */         case 92:
/* 653 */           b1 = 1; break;
/*     */         case 48: case 49: case 50: case 51: case 52: case 53: case 54:
/*     */         case 55:
/*     */         case 56:
/*     */         case 57:
/* 658 */           b1 = 2;
/* 659 */           j = k - 48; break;
/*     */         case 97: case 98: case 99: case 100:
/*     */         case 101:
/*     */         case 102:
/* 663 */           b1 = 2;
/* 664 */           j = k - 97 + 10; break;
/*     */         case 65: case 66: case 67: case 68:
/*     */         case 69:
/*     */         case 70:
/* 668 */           b1 = 2;
/* 669 */           j = k - 65 + 10;
/*     */           break;
/*     */ 
/*     */         
/*     */         case -1:
/* 674 */           throw new RuntimeException("Unclosed " + paramChar);
/*     */         
/*     */         default:
/* 677 */           b1 = 0;
/*     */           break;
/*     */       } 
/* 680 */       if (bool1) {
/* 681 */         if (b1 == 2) {
/*     */           
/* 683 */           i = i * 16 + j;
/* 684 */           if (++b == 4) {
/* 685 */             bool1 = false;
/* 686 */             append((char)i);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 691 */         if (b > 0) {
/* 692 */           append((char)i);
/* 693 */           if (b1 == 1) {
/* 694 */             bool1 = true;
/* 695 */             i = b = 0;
/*     */             continue;
/*     */           } 
/* 698 */           if (k == c) {
/* 699 */             bool2 = true;
/*     */           }
/* 701 */           append((char)k);
/* 702 */           bool1 = false;
/*     */           
/*     */           continue;
/*     */         } 
/* 706 */         append((char)k);
/* 707 */         bool1 = false;
/*     */         
/*     */         continue;
/*     */       } 
/* 711 */       if (b1 == 1) {
/* 712 */         bool1 = true;
/* 713 */         i = b = 0;
/*     */         continue;
/*     */       } 
/* 716 */       if (k == c) {
/* 717 */         bool2 = true;
/*     */       }
/* 719 */       append((char)k);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void append(char paramChar) {
/* 725 */     if (this.tokenBufferLength == this.tokenBuffer.length) {
/* 726 */       char[] arrayOfChar = new char[this.tokenBuffer.length * 2];
/* 727 */       System.arraycopy(this.tokenBuffer, 0, arrayOfChar, 0, this.tokenBuffer.length);
/* 728 */       this.tokenBuffer = arrayOfChar;
/*     */     } 
/* 730 */     this.tokenBuffer[this.tokenBufferLength++] = paramChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readComment() throws IOException {
/*     */     while (true) {
/* 740 */       int i = readChar();
/* 741 */       switch (i) {
/*     */         case -1:
/* 743 */           throw new RuntimeException("Unclosed comment");
/*     */         case 42:
/* 745 */           i = readChar();
/* 746 */           if (i == 47) {
/*     */             return;
/*     */           }
/* 749 */           if (i == -1) {
/* 750 */             throw new RuntimeException("Unclosed comment");
/*     */           }
/*     */           
/* 753 */           pushChar(i);
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
/*     */   private void startBlock(int paramInt) {
/* 766 */     if (this.stackCount == this.unitStack.length) {
/* 767 */       int[] arrayOfInt = new int[this.stackCount * 2];
/*     */       
/* 769 */       System.arraycopy(this.unitStack, 0, arrayOfInt, 0, this.stackCount);
/* 770 */       this.unitStack = arrayOfInt;
/*     */     } 
/* 772 */     this.unitStack[this.stackCount++] = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void endBlock(int paramInt) {
/*     */     byte b;
/* 781 */     switch (paramInt) {
/*     */       case 3:
/* 783 */         b = 2;
/*     */         break;
/*     */       case 5:
/* 786 */         b = 4;
/*     */         break;
/*     */       case 7:
/* 789 */         b = 6;
/*     */         break;
/*     */       
/*     */       default:
/* 793 */         b = -1;
/*     */         break;
/*     */     } 
/* 796 */     if (this.stackCount > 0 && this.unitStack[this.stackCount - 1] == b) {
/* 797 */       this.stackCount--;
/*     */     }
/*     */     else {
/*     */       
/* 801 */       throw new RuntimeException("Unmatched block");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inBlock() {
/* 809 */     return (this.stackCount > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readWS() throws IOException {
/*     */     int i;
/* 817 */     while ((i = readChar()) != -1 && 
/* 818 */       Character.isWhitespace((char)i)) {
/* 819 */       this.readWS = true;
/*     */     }
/* 821 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readChar() throws IOException {
/* 828 */     if (this.didPushChar) {
/* 829 */       this.didPushChar = false;
/* 830 */       return this.pushedChar;
/*     */     } 
/* 832 */     return this.reader.read();
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
/*     */   private void pushChar(int paramInt) {
/* 847 */     if (this.didPushChar)
/*     */     {
/* 849 */       throw new RuntimeException("Can not handle look ahead of more than one character");
/*     */     }
/* 851 */     this.didPushChar = true;
/* 852 */     this.pushedChar = paramInt;
/*     */   }
/*     */   
/*     */   static interface CSSParserCallback {
/*     */     void handleImport(String param1String);
/*     */     
/*     */     void handleSelector(String param1String);
/*     */     
/*     */     void startRule();
/*     */     
/*     */     void handleProperty(String param1String);
/*     */     
/*     */     void handleValue(String param1String);
/*     */     
/*     */     void endRule();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/CSSParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */