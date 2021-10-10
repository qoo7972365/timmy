/*     */ package java.io;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamTokenizer
/*     */ {
/*  68 */   private Reader reader = null;
/*  69 */   private InputStream input = null;
/*     */   
/*  71 */   private char[] buf = new char[20];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private int peekc = Integer.MAX_VALUE;
/*     */   
/*     */   private static final int NEED_CHAR = 2147483647;
/*     */   
/*     */   private static final int SKIP_LF = 2147483646;
/*     */   
/*     */   private boolean pushedBack;
/*     */   private boolean forceLower;
/*  88 */   private int LINENO = 1;
/*     */   
/*     */   private boolean eolIsSignificantP = false;
/*     */   
/*     */   private boolean slashSlashCommentsP = false;
/*     */   private boolean slashStarCommentsP = false;
/*  94 */   private byte[] ctype = new byte[256];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte CT_WHITESPACE = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte CT_DIGIT = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte CT_ALPHA = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte CT_QUOTE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final byte CT_COMMENT = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public int ttype = -4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TT_EOF = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TT_EOL = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TT_NUMBER = -2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int TT_WORD = -3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TT_NOTHING = -4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String sval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StreamTokenizer() {
/* 189 */     wordChars(97, 122);
/* 190 */     wordChars(65, 90);
/* 191 */     wordChars(160, 255);
/* 192 */     whitespaceChars(0, 32);
/* 193 */     commentChar(47);
/* 194 */     quoteChar(34);
/* 195 */     quoteChar(39);
/* 196 */     parseNumbers();
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
/*     */   @Deprecated
/*     */   public StreamTokenizer(InputStream paramInputStream) {
/* 232 */     this();
/* 233 */     if (paramInputStream == null) {
/* 234 */       throw new NullPointerException();
/*     */     }
/* 236 */     this.input = paramInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamTokenizer(Reader paramReader) {
/* 246 */     this();
/* 247 */     if (paramReader == null) {
/* 248 */       throw new NullPointerException();
/*     */     }
/* 250 */     this.reader = paramReader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetSyntax() {
/* 261 */     for (int i = this.ctype.length; --i >= 0;) {
/* 262 */       this.ctype[i] = 0;
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
/*     */   public void wordChars(int paramInt1, int paramInt2) {
/* 275 */     if (paramInt1 < 0)
/* 276 */       paramInt1 = 0; 
/* 277 */     if (paramInt2 >= this.ctype.length)
/* 278 */       paramInt2 = this.ctype.length - 1; 
/* 279 */     while (paramInt1 <= paramInt2) {
/* 280 */       this.ctype[paramInt1++] = (byte)(this.ctype[paramInt1++] | 0x4);
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
/*     */   public void whitespaceChars(int paramInt1, int paramInt2) {
/* 296 */     if (paramInt1 < 0)
/* 297 */       paramInt1 = 0; 
/* 298 */     if (paramInt2 >= this.ctype.length)
/* 299 */       paramInt2 = this.ctype.length - 1; 
/* 300 */     while (paramInt1 <= paramInt2) {
/* 301 */       this.ctype[paramInt1++] = 1;
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
/*     */   public void ordinaryChars(int paramInt1, int paramInt2) {
/* 316 */     if (paramInt1 < 0)
/* 317 */       paramInt1 = 0; 
/* 318 */     if (paramInt2 >= this.ctype.length)
/* 319 */       paramInt2 = this.ctype.length - 1; 
/* 320 */     while (paramInt1 <= paramInt2) {
/* 321 */       this.ctype[paramInt1++] = 0;
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
/*     */   public void ordinaryChar(int paramInt) {
/* 342 */     if (paramInt >= 0 && paramInt < this.ctype.length) {
/* 343 */       this.ctype[paramInt] = 0;
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
/*     */   public void commentChar(int paramInt) {
/* 356 */     if (paramInt >= 0 && paramInt < this.ctype.length) {
/* 357 */       this.ctype[paramInt] = 16;
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
/*     */   public void quoteChar(int paramInt) {
/* 385 */     if (paramInt >= 0 && paramInt < this.ctype.length) {
/* 386 */       this.ctype[paramInt] = 8;
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
/*     */   public void parseNumbers() {
/* 410 */     for (byte b = 48; b <= 57; b++)
/* 411 */       this.ctype[b] = (byte)(this.ctype[b] | 0x2); 
/* 412 */     this.ctype[46] = (byte)(this.ctype[46] | 0x2);
/* 413 */     this.ctype[45] = (byte)(this.ctype[45] | 0x2);
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
/*     */   public void eolIsSignificant(boolean paramBoolean) {
/* 440 */     this.eolIsSignificantP = paramBoolean;
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
/*     */   public void slashStarComments(boolean paramBoolean) {
/* 456 */     this.slashStarCommentsP = paramBoolean;
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
/*     */   public void slashSlashComments(boolean paramBoolean) {
/* 473 */     this.slashSlashCommentsP = paramBoolean;
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
/*     */   public void lowerCaseMode(boolean paramBoolean) {
/* 494 */     this.forceLower = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   private int read() throws IOException {
/* 499 */     if (this.reader != null)
/* 500 */       return this.reader.read(); 
/* 501 */     if (this.input != null) {
/* 502 */       return this.input.read();
/*     */     }
/* 504 */     throw new IllegalStateException();
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
/*     */   public int nextToken() throws IOException {
/* 526 */     if (this.pushedBack) {
/* 527 */       this.pushedBack = false;
/* 528 */       return this.ttype;
/*     */     } 
/* 530 */     byte[] arrayOfByte = this.ctype;
/* 531 */     this.sval = null;
/*     */     
/* 533 */     int i = this.peekc;
/* 534 */     if (i < 0)
/* 535 */       i = Integer.MAX_VALUE; 
/* 536 */     if (i == 2147483646) {
/* 537 */       i = read();
/* 538 */       if (i < 0)
/* 539 */         return this.ttype = -1; 
/* 540 */       if (i == 10)
/* 541 */         i = Integer.MAX_VALUE; 
/*     */     } 
/* 543 */     if (i == Integer.MAX_VALUE) {
/* 544 */       i = read();
/* 545 */       if (i < 0)
/* 546 */         return this.ttype = -1; 
/*     */     } 
/* 548 */     this.ttype = i;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 553 */     this.peekc = Integer.MAX_VALUE;
/*     */     
/* 555 */     boolean bool = (i < 256) ? arrayOfByte[i] : true;
/* 556 */     while ((bool & true) != 0) {
/* 557 */       if (i == 13) {
/* 558 */         this.LINENO++;
/* 559 */         if (this.eolIsSignificantP) {
/* 560 */           this.peekc = 2147483646;
/* 561 */           return this.ttype = 10;
/*     */         } 
/* 563 */         i = read();
/* 564 */         if (i == 10)
/* 565 */           i = read(); 
/*     */       } else {
/* 567 */         if (i == 10) {
/* 568 */           this.LINENO++;
/* 569 */           if (this.eolIsSignificantP) {
/* 570 */             return this.ttype = 10;
/*     */           }
/*     */         } 
/* 573 */         i = read();
/*     */       } 
/* 575 */       if (i < 0)
/* 576 */         return this.ttype = -1; 
/* 577 */       bool = (i < 256) ? arrayOfByte[i] : true;
/*     */     } 
/*     */     
/* 580 */     if ((bool & 0x2) != 0) {
/* 581 */       boolean bool1 = false;
/* 582 */       if (i == 45) {
/* 583 */         i = read();
/* 584 */         if (i != 46 && (i < 48 || i > 57)) {
/* 585 */           this.peekc = i;
/* 586 */           return this.ttype = 45;
/*     */         } 
/* 588 */         bool1 = true;
/*     */       } 
/* 590 */       double d = 0.0D;
/* 591 */       int j = 0;
/* 592 */       byte b = 0;
/*     */       while (true) {
/* 594 */         if (i == 46 && !b) {
/* 595 */           b = 1;
/* 596 */         } else if (48 <= i && i <= 57) {
/* 597 */           d = d * 10.0D + (i - 48);
/* 598 */           j += b;
/*     */         } else {
/*     */           break;
/* 601 */         }  i = read();
/*     */       } 
/* 603 */       this.peekc = i;
/* 604 */       if (j != 0) {
/* 605 */         double d1 = 10.0D;
/* 606 */         j--;
/* 607 */         while (j > 0) {
/* 608 */           d1 *= 10.0D;
/* 609 */           j--;
/*     */         } 
/*     */         
/* 612 */         d /= d1;
/*     */       } 
/* 614 */       this.nval = bool1 ? -d : d;
/* 615 */       return this.ttype = -2;
/*     */     } 
/*     */     
/* 618 */     if ((bool & 0x4) != 0) {
/* 619 */       byte b = 0;
/*     */       while (true) {
/* 621 */         if (b >= this.buf.length) {
/* 622 */           this.buf = Arrays.copyOf(this.buf, this.buf.length * 2);
/*     */         }
/* 624 */         this.buf[b++] = (char)i;
/* 625 */         i = read();
/* 626 */         bool = (i < 0) ? true : ((i < 256) ? arrayOfByte[i] : true);
/* 627 */         if ((bool & 0x6) == 0) {
/* 628 */           this.peekc = i;
/* 629 */           this.sval = String.copyValueOf(this.buf, 0, b);
/* 630 */           if (this.forceLower)
/* 631 */             this.sval = this.sval.toLowerCase(); 
/* 632 */           return this.ttype = -3;
/*     */         } 
/*     */       } 
/* 635 */     }  if ((bool & 0x8) != 0) {
/* 636 */       this.ttype = i;
/* 637 */       byte b = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 642 */       int j = read();
/* 643 */       while (j >= 0 && j != this.ttype && j != 10 && j != 13) {
/* 644 */         if (j == 92) {
/* 645 */           i = read();
/* 646 */           int k = i;
/* 647 */           if (i >= 48 && i <= 55)
/* 648 */           { i -= 48;
/* 649 */             int m = read();
/* 650 */             if (48 <= m && m <= 55)
/* 651 */             { i = (i << 3) + m - 48;
/* 652 */               m = read();
/* 653 */               if (48 <= m && m <= 55 && k <= 51) {
/* 654 */                 i = (i << 3) + m - 48;
/* 655 */                 j = read();
/*     */               } else {
/* 657 */                 j = m;
/*     */               }  }
/* 659 */             else { j = m; }
/*     */              }
/* 661 */           else { switch (i) {
/*     */               case 97:
/* 663 */                 i = 7;
/*     */                 break;
/*     */               case 98:
/* 666 */                 i = 8;
/*     */                 break;
/*     */               case 102:
/* 669 */                 i = 12;
/*     */                 break;
/*     */               case 110:
/* 672 */                 i = 10;
/*     */                 break;
/*     */               case 114:
/* 675 */                 i = 13;
/*     */                 break;
/*     */               case 116:
/* 678 */                 i = 9;
/*     */                 break;
/*     */               case 118:
/* 681 */                 i = 11;
/*     */                 break;
/*     */             } 
/* 684 */             j = read(); }
/*     */         
/*     */         } else {
/* 687 */           i = j;
/* 688 */           j = read();
/*     */         } 
/* 690 */         if (b >= this.buf.length) {
/* 691 */           this.buf = Arrays.copyOf(this.buf, this.buf.length * 2);
/*     */         }
/* 693 */         this.buf[b++] = (char)i;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 700 */       this.peekc = (j == this.ttype) ? Integer.MAX_VALUE : j;
/*     */       
/* 702 */       this.sval = String.copyValueOf(this.buf, 0, b);
/* 703 */       return this.ttype;
/*     */     } 
/*     */     
/* 706 */     if (i == 47 && (this.slashSlashCommentsP || this.slashStarCommentsP)) {
/* 707 */       i = read();
/* 708 */       if (i == 42 && this.slashStarCommentsP) {
/* 709 */         int j = 0;
/* 710 */         while ((i = read()) != 47 || j != 42) {
/* 711 */           if (i == 13) {
/* 712 */             this.LINENO++;
/* 713 */             i = read();
/* 714 */             if (i == 10) {
/* 715 */               i = read();
/*     */             }
/*     */           }
/* 718 */           else if (i == 10) {
/* 719 */             this.LINENO++;
/* 720 */             i = read();
/*     */           } 
/*     */           
/* 723 */           if (i < 0)
/* 724 */             return this.ttype = -1; 
/* 725 */           j = i;
/*     */         } 
/* 727 */         return nextToken();
/* 728 */       }  if (i == 47 && this.slashSlashCommentsP) {
/* 729 */         while ((i = read()) != 10 && i != 13 && i >= 0);
/* 730 */         this.peekc = i;
/* 731 */         return nextToken();
/*     */       } 
/*     */       
/* 734 */       if ((arrayOfByte[47] & 0x10) != 0) {
/* 735 */         while ((i = read()) != 10 && i != 13 && i >= 0);
/* 736 */         this.peekc = i;
/* 737 */         return nextToken();
/*     */       } 
/* 739 */       this.peekc = i;
/* 740 */       return this.ttype = 47;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 745 */     if ((bool & 0x10) != 0) {
/* 746 */       while ((i = read()) != 10 && i != 13 && i >= 0);
/* 747 */       this.peekc = i;
/* 748 */       return nextToken();
/*     */     } 
/*     */     
/* 751 */     return this.ttype = i;
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
/*     */   public void pushBack() {
/* 766 */     if (this.ttype != -4) {
/* 767 */       this.pushedBack = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lineno() {
/* 776 */     return this.LINENO;
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
/*     */   public String toString() {
/*     */     String str;
/* 795 */     switch (this.ttype)
/*     */     { case -1:
/* 797 */         str = "EOF";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 831 */         return "Token[" + str + "], line " + this.LINENO;case 10: str = "EOL"; return "Token[" + str + "], line " + this.LINENO;case -3: str = this.sval; return "Token[" + str + "], line " + this.LINENO;case -2: str = "n=" + this.nval; return "Token[" + str + "], line " + this.LINENO;case -4: str = "NOTHING"; return "Token[" + str + "], line " + this.LINENO; }  if (this.ttype < 256 && (this.ctype[this.ttype] & 0x8) != 0) { str = this.sval; } else { char[] arrayOfChar = new char[3]; arrayOfChar[2] = '\''; arrayOfChar[0] = '\''; arrayOfChar[1] = (char)this.ttype; str = new String(arrayOfChar); }  return "Token[" + str + "], line " + this.LINENO;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/io/StreamTokenizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */