/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractWriter
/*     */ {
/*     */   private ElementIterator it;
/*     */   private Writer out;
/*  46 */   private int indentLevel = 0;
/*  47 */   private int indentSpace = 2;
/*  48 */   private Document doc = null;
/*  49 */   private int maxLineLength = 100;
/*  50 */   private int currLength = 0;
/*  51 */   private int startOffset = 0;
/*  52 */   private int endOffset = 0;
/*     */ 
/*     */ 
/*     */   
/*  56 */   private int offsetIndent = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String lineSeparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canWrapLines;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isLineEmpty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] indentChars;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] tempChars;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] newlineChars;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Segment segment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final char NEWLINE = '\n';
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractWriter(Writer paramWriter, Document paramDocument) {
/* 118 */     this(paramWriter, paramDocument, 0, paramDocument.getLength());
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
/*     */   protected AbstractWriter(Writer paramWriter, Document paramDocument, int paramInt1, int paramInt2) {
/* 133 */     this.doc = paramDocument;
/* 134 */     this.it = new ElementIterator(paramDocument.getDefaultRootElement());
/* 135 */     this.out = paramWriter;
/* 136 */     this.startOffset = paramInt1;
/* 137 */     this.endOffset = paramInt1 + paramInt2;
/* 138 */     Object object = paramDocument.getProperty("__EndOfLine__");
/*     */     
/* 140 */     if (object instanceof String) {
/* 141 */       setLineSeparator((String)object);
/*     */     } else {
/*     */       
/* 144 */       String str = null;
/*     */       try {
/* 146 */         str = System.getProperty("line.separator");
/* 147 */       } catch (SecurityException securityException) {}
/* 148 */       if (str == null)
/*     */       {
/*     */         
/* 151 */         str = "\n";
/*     */       }
/* 153 */       setLineSeparator(str);
/*     */     } 
/* 155 */     this.canWrapLines = true;
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
/*     */   protected AbstractWriter(Writer paramWriter, Element paramElement) {
/* 167 */     this(paramWriter, paramElement, 0, paramElement.getEndOffset());
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
/*     */   protected AbstractWriter(Writer paramWriter, Element paramElement, int paramInt1, int paramInt2) {
/* 182 */     this.doc = paramElement.getDocument();
/* 183 */     this.it = new ElementIterator(paramElement);
/* 184 */     this.out = paramWriter;
/* 185 */     this.startOffset = paramInt1;
/* 186 */     this.endOffset = paramInt1 + paramInt2;
/* 187 */     this.canWrapLines = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStartOffset() {
/* 196 */     return this.startOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndOffset() {
/* 205 */     return this.endOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ElementIterator getElementIterator() {
/* 214 */     return this.it;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Writer getWriter() {
/* 223 */     return this.out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Document getDocument() {
/* 232 */     return this.doc;
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
/*     */   protected boolean inRange(Element paramElement) {
/* 247 */     int i = getStartOffset();
/* 248 */     int j = getEndOffset();
/* 249 */     if ((paramElement.getStartOffset() >= i && paramElement
/* 250 */       .getStartOffset() < j) || (i >= paramElement
/* 251 */       .getStartOffset() && i < paramElement
/* 252 */       .getEndOffset())) {
/* 253 */       return true;
/*     */     }
/* 255 */     return false;
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
/*     */   protected abstract void write() throws IOException, BadLocationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getText(Element paramElement) throws BadLocationException {
/* 278 */     return this.doc.getText(paramElement.getStartOffset(), paramElement
/* 279 */         .getEndOffset() - paramElement.getStartOffset());
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
/*     */   protected void text(Element paramElement) throws BadLocationException, IOException {
/* 295 */     int i = Math.max(getStartOffset(), paramElement.getStartOffset());
/* 296 */     int j = Math.min(getEndOffset(), paramElement.getEndOffset());
/* 297 */     if (i < j) {
/* 298 */       if (this.segment == null) {
/* 299 */         this.segment = new Segment();
/*     */       }
/* 301 */       getDocument().getText(i, j - i, this.segment);
/* 302 */       if (this.segment.count > 0) {
/* 303 */         write(this.segment.array, this.segment.offset, this.segment.count);
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
/*     */   protected void setLineLength(int paramInt) {
/* 315 */     this.maxLineLength = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getLineLength() {
/* 324 */     return this.maxLineLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCurrentLineLength(int paramInt) {
/* 333 */     this.currLength = paramInt;
/* 334 */     this.isLineEmpty = (this.currLength == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getCurrentLineLength() {
/* 343 */     return this.currLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLineEmpty() {
/* 354 */     return this.isLineEmpty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCanWrapLines(boolean paramBoolean) {
/* 365 */     this.canWrapLines = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean getCanWrapLines() {
/* 375 */     return this.canWrapLines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setIndentSpace(int paramInt) {
/* 386 */     this.indentSpace = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIndentSpace() {
/* 395 */     return this.indentSpace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineSeparator(String paramString) {
/* 406 */     this.lineSeparator = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLineSeparator() {
/* 415 */     return this.lineSeparator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void incrIndent() {
/* 425 */     if (this.offsetIndent > 0) {
/* 426 */       this.offsetIndent++;
/*     */     
/*     */     }
/* 429 */     else if (++this.indentLevel * getIndentSpace() >= getLineLength()) {
/* 430 */       this.offsetIndent++;
/* 431 */       this.indentLevel--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decrIndent() {
/* 440 */     if (this.offsetIndent > 0) {
/* 441 */       this.offsetIndent--;
/*     */     } else {
/*     */       
/* 444 */       this.indentLevel--;
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
/*     */   protected int getIndentLevel() {
/* 456 */     return this.indentLevel;
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
/*     */   protected void indent() throws IOException {
/* 468 */     int i = getIndentLevel() * getIndentSpace();
/* 469 */     if (this.indentChars == null || i > this.indentChars.length) {
/* 470 */       this.indentChars = new char[i];
/* 471 */       for (byte b = 0; b < i; b++) {
/* 472 */         this.indentChars[b] = ' ';
/*     */       }
/*     */     } 
/* 475 */     int j = getCurrentLineLength();
/* 476 */     boolean bool = isLineEmpty();
/* 477 */     output(this.indentChars, 0, i);
/* 478 */     if (bool && j == 0) {
/* 479 */       this.isLineEmpty = true;
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
/*     */   protected void write(char paramChar) throws IOException {
/* 491 */     if (this.tempChars == null) {
/* 492 */       this.tempChars = new char[128];
/*     */     }
/* 494 */     this.tempChars[0] = paramChar;
/* 495 */     write(this.tempChars, 0, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void write(String paramString) throws IOException {
/* 506 */     if (paramString == null) {
/*     */       return;
/*     */     }
/* 509 */     int i = paramString.length();
/* 510 */     if (this.tempChars == null || this.tempChars.length < i) {
/* 511 */       this.tempChars = new char[i];
/*     */     }
/* 513 */     paramString.getChars(0, i, this.tempChars, 0);
/* 514 */     write(this.tempChars, 0, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeLineSeparator() throws IOException {
/* 524 */     String str = getLineSeparator();
/* 525 */     int i = str.length();
/* 526 */     if (this.newlineChars == null || this.newlineChars.length < i) {
/* 527 */       this.newlineChars = new char[i];
/*     */     }
/* 529 */     str.getChars(0, i, this.newlineChars, 0);
/* 530 */     output(this.newlineChars, 0, i);
/* 531 */     setCurrentLineLength(0);
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
/*     */   protected void write(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 549 */     if (!getCanWrapLines()) {
/*     */ 
/*     */       
/* 552 */       int i = paramInt1;
/* 553 */       int j = paramInt1 + paramInt2;
/* 554 */       int k = indexOf(paramArrayOfchar, '\n', paramInt1, j);
/* 555 */       while (k != -1) {
/* 556 */         if (k > i) {
/* 557 */           output(paramArrayOfchar, i, k - i);
/*     */         }
/* 559 */         writeLineSeparator();
/* 560 */         i = k + 1;
/* 561 */         k = indexOf(paramArrayOfchar, '\n', i, j);
/*     */       } 
/* 563 */       if (i < j) {
/* 564 */         output(paramArrayOfchar, i, j - i);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 569 */       int i = paramInt1;
/* 570 */       int j = paramInt1 + paramInt2;
/* 571 */       int k = getCurrentLineLength();
/* 572 */       int m = getLineLength();
/*     */       
/* 574 */       while (i < j) {
/* 575 */         int n = indexOf(paramArrayOfchar, '\n', i, j);
/*     */         
/* 577 */         boolean bool1 = false;
/* 578 */         boolean bool2 = false;
/*     */         
/* 580 */         k = getCurrentLineLength();
/* 581 */         if (n != -1 && k + n - i < m) {
/*     */           
/* 583 */           if (n > i) {
/* 584 */             output(paramArrayOfchar, i, n - i);
/*     */           }
/* 586 */           i = n + 1;
/* 587 */           bool2 = true;
/*     */         }
/* 589 */         else if (n == -1 && k + j - i < m) {
/*     */           
/* 591 */           if (j > i) {
/* 592 */             output(paramArrayOfchar, i, j - i);
/*     */           }
/* 594 */           i = j;
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 600 */           int i1 = -1;
/* 601 */           int i2 = Math.min(j - i, m - k - 1);
/*     */           
/* 603 */           int i3 = 0;
/* 604 */           while (i3 < i2) {
/* 605 */             if (Character.isWhitespace(paramArrayOfchar[i3 + i]))
/*     */             {
/* 607 */               i1 = i3;
/*     */             }
/* 609 */             i3++;
/*     */           } 
/* 611 */           if (i1 != -1) {
/*     */             
/* 613 */             i1 += i + 1;
/* 614 */             output(paramArrayOfchar, i, i1 - i);
/* 615 */             i = i1;
/* 616 */             bool1 = true;
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 625 */             i3 = Math.max(0, i2);
/* 626 */             i2 = j - i;
/* 627 */             while (i3 < i2) {
/* 628 */               if (Character.isWhitespace(paramArrayOfchar[i3 + i])) {
/*     */                 
/* 630 */                 i1 = i3;
/*     */                 break;
/*     */               } 
/* 633 */               i3++;
/*     */             } 
/* 635 */             if (i1 == -1) {
/* 636 */               output(paramArrayOfchar, i, j - i);
/* 637 */               i1 = j;
/*     */             } else {
/*     */               
/* 640 */               i1 += i;
/* 641 */               if (paramArrayOfchar[i1] == '\n') {
/* 642 */                 output(paramArrayOfchar, i, i1++ - i);
/*     */                 
/* 644 */                 bool2 = true;
/*     */               } else {
/*     */                 
/* 647 */                 output(paramArrayOfchar, i, ++i1 - i);
/*     */                 
/* 649 */                 bool1 = true;
/*     */               } 
/*     */             } 
/* 652 */             i = i1;
/*     */           } 
/*     */         } 
/* 655 */         if (bool2 || bool1 || i < j) {
/* 656 */           writeLineSeparator();
/* 657 */           if (i < j || !bool2) {
/* 658 */             indent();
/*     */           }
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
/*     */   protected void writeAttributes(AttributeSet paramAttributeSet) throws IOException {
/* 674 */     Enumeration<?> enumeration = paramAttributeSet.getAttributeNames();
/* 675 */     while (enumeration.hasMoreElements()) {
/* 676 */       Object object = enumeration.nextElement();
/* 677 */       write(" " + object + "=" + paramAttributeSet.getAttribute(object));
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
/*     */   protected void output(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws IOException {
/* 696 */     getWriter().write(paramArrayOfchar, paramInt1, paramInt2);
/* 697 */     setCurrentLineLength(getCurrentLineLength() + paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int indexOf(char[] paramArrayOfchar, char paramChar, int paramInt1, int paramInt2) {
/* 705 */     while (paramInt1 < paramInt2) {
/* 706 */       if (paramArrayOfchar[paramInt1] == paramChar) {
/* 707 */         return paramInt1;
/*     */       }
/* 709 */       paramInt1++;
/*     */     } 
/* 711 */     return -1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/AbstractWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */