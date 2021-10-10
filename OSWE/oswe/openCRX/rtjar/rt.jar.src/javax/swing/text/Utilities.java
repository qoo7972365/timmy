/*      */ package javax.swing.text;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedString;
/*      */ import java.text.BreakIterator;
/*      */ import javax.swing.JComponent;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Utilities
/*      */ {
/*      */   static JComponent getJComponent(View paramView) {
/*   58 */     if (paramView != null) {
/*   59 */       Container container = paramView.getContainer();
/*   60 */       if (container instanceof JComponent) {
/*   61 */         return (JComponent)container;
/*      */       }
/*      */     } 
/*   64 */     return null;
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
/*      */   public static final int drawTabbedText(Segment paramSegment, int paramInt1, int paramInt2, Graphics paramGraphics, TabExpander paramTabExpander, int paramInt3) {
/*   84 */     return drawTabbedText(null, paramSegment, paramInt1, paramInt2, paramGraphics, paramTabExpander, paramInt3);
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
/*      */   static final int drawTabbedText(View paramView, Segment paramSegment, int paramInt1, int paramInt2, Graphics paramGraphics, TabExpander paramTabExpander, int paramInt3) {
/*  106 */     return drawTabbedText(paramView, paramSegment, paramInt1, paramInt2, paramGraphics, paramTabExpander, paramInt3, null);
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
/*      */   static final int drawTabbedText(View paramView, Segment paramSegment, int paramInt1, int paramInt2, Graphics paramGraphics, TabExpander paramTabExpander, int paramInt3, int[] paramArrayOfint) {
/*  120 */     JComponent jComponent = getJComponent(paramView);
/*  121 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(jComponent, paramGraphics);
/*  122 */     int i = paramInt1;
/*  123 */     char[] arrayOfChar = paramSegment.array;
/*  124 */     int j = paramSegment.offset;
/*  125 */     byte b = 0;
/*  126 */     int k = paramSegment.offset;
/*  127 */     int m = 0;
/*  128 */     int n = -1;
/*  129 */     int i1 = 0;
/*  130 */     int i2 = 0;
/*  131 */     if (paramArrayOfint != null) {
/*  132 */       int i5 = -paramInt3 + j;
/*  133 */       View view = null;
/*  134 */       if (paramView != null && (
/*  135 */         view = paramView.getParent()) != null) {
/*  136 */         i5 += view.getStartOffset();
/*      */       }
/*  138 */       m = paramArrayOfint[0];
/*      */       
/*  140 */       n = paramArrayOfint[1] + i5;
/*      */       
/*  142 */       i1 = paramArrayOfint[2] + i5;
/*      */       
/*  144 */       i2 = paramArrayOfint[3] + i5;
/*      */     } 
/*      */     
/*  147 */     int i3 = paramSegment.offset + paramSegment.count;
/*  148 */     for (int i4 = j; i4 < i3; i4++) {
/*  149 */       if (arrayOfChar[i4] == '\t' || ((m != 0 || i4 <= n) && arrayOfChar[i4] == ' ' && i1 <= i4 && i4 <= i2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  155 */         if (b) {
/*  156 */           i = SwingUtilities2.drawChars(jComponent, paramGraphics, arrayOfChar, k, b, paramInt1, paramInt2);
/*      */           
/*  158 */           b = 0;
/*      */         } 
/*  160 */         k = i4 + 1;
/*  161 */         if (arrayOfChar[i4] == '\t') {
/*  162 */           if (paramTabExpander != null) {
/*  163 */             i = (int)paramTabExpander.nextTabStop(i, paramInt3 + i4 - j);
/*      */           } else {
/*  165 */             i += fontMetrics.charWidth(' ');
/*      */           } 
/*  167 */         } else if (arrayOfChar[i4] == ' ') {
/*  168 */           i += fontMetrics.charWidth(' ') + m;
/*  169 */           if (i4 <= n) {
/*  170 */             i++;
/*      */           }
/*      */         } 
/*  173 */         paramInt1 = i;
/*  174 */       } else if (arrayOfChar[i4] == '\n' || arrayOfChar[i4] == '\r') {
/*  175 */         if (b) {
/*  176 */           i = SwingUtilities2.drawChars(jComponent, paramGraphics, arrayOfChar, k, b, paramInt1, paramInt2);
/*      */           
/*  178 */           b = 0;
/*      */         } 
/*  180 */         k = i4 + 1;
/*  181 */         paramInt1 = i;
/*      */       } else {
/*  183 */         b++;
/*      */       } 
/*      */     } 
/*  186 */     if (b > 0) {
/*  187 */       i = SwingUtilities2.drawChars(jComponent, paramGraphics, arrayOfChar, k, b, paramInt1, paramInt2);
/*      */     }
/*      */     
/*  190 */     return i;
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
/*      */   public static final int getTabbedTextWidth(Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, TabExpander paramTabExpander, int paramInt2) {
/*  208 */     return getTabbedTextWidth(null, paramSegment, paramFontMetrics, paramInt1, paramTabExpander, paramInt2, null);
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
/*      */   static final int getTabbedTextWidth(View paramView, Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, TabExpander paramTabExpander, int paramInt2, int[] paramArrayOfint) {
/*  222 */     int i = paramInt1;
/*  223 */     char[] arrayOfChar = paramSegment.array;
/*  224 */     int j = paramSegment.offset;
/*  225 */     int k = paramSegment.offset + paramSegment.count;
/*  226 */     byte b = 0;
/*  227 */     int m = 0;
/*  228 */     int n = -1;
/*  229 */     int i1 = 0;
/*  230 */     int i2 = 0;
/*  231 */     if (paramArrayOfint != null) {
/*  232 */       int i4 = -paramInt2 + j;
/*  233 */       View view = null;
/*  234 */       if (paramView != null && (
/*  235 */         view = paramView.getParent()) != null) {
/*  236 */         i4 += view.getStartOffset();
/*      */       }
/*  238 */       m = paramArrayOfint[0];
/*      */       
/*  240 */       n = paramArrayOfint[1] + i4;
/*      */       
/*  242 */       i1 = paramArrayOfint[2] + i4;
/*      */       
/*  244 */       i2 = paramArrayOfint[3] + i4;
/*      */     } 
/*      */ 
/*      */     
/*  248 */     for (int i3 = j; i3 < k; i3++) {
/*  249 */       if (arrayOfChar[i3] == '\t' || ((m != 0 || i3 <= n) && arrayOfChar[i3] == ' ' && i1 <= i3 && i3 <= i2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  255 */         i += paramFontMetrics.charsWidth(arrayOfChar, i3 - b, b);
/*  256 */         b = 0;
/*  257 */         if (arrayOfChar[i3] == '\t') {
/*  258 */           if (paramTabExpander != null) {
/*  259 */             i = (int)paramTabExpander.nextTabStop(i, paramInt2 + i3 - j);
/*      */           } else {
/*      */             
/*  262 */             i += paramFontMetrics.charWidth(' ');
/*      */           } 
/*  264 */         } else if (arrayOfChar[i3] == ' ') {
/*  265 */           i += paramFontMetrics.charWidth(' ') + m;
/*  266 */           if (i3 <= n) {
/*  267 */             i++;
/*      */           }
/*      */         } 
/*  270 */       } else if (arrayOfChar[i3] == '\n') {
/*      */ 
/*      */         
/*  273 */         i += paramFontMetrics.charsWidth(arrayOfChar, i3 - b, b);
/*  274 */         b = 0;
/*      */       } else {
/*  276 */         b++;
/*      */       } 
/*      */     } 
/*  279 */     i += paramFontMetrics.charsWidth(arrayOfChar, k - b, b);
/*  280 */     return i - paramInt1;
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
/*      */   public static final int getTabbedTextOffset(Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, TabExpander paramTabExpander, int paramInt3) {
/*  303 */     return getTabbedTextOffset(paramSegment, paramFontMetrics, paramInt1, paramInt2, paramTabExpander, paramInt3, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int getTabbedTextOffset(View paramView, Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, TabExpander paramTabExpander, int paramInt3, int[] paramArrayOfint) {
/*  310 */     return getTabbedTextOffset(paramView, paramSegment, paramFontMetrics, paramInt1, paramInt2, paramTabExpander, paramInt3, true, paramArrayOfint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int getTabbedTextOffset(Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, TabExpander paramTabExpander, int paramInt3, boolean paramBoolean) {
/*  319 */     return getTabbedTextOffset(null, paramSegment, paramFontMetrics, paramInt1, paramInt2, paramTabExpander, paramInt3, paramBoolean, null);
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
/*      */   static final int getTabbedTextOffset(View paramView, Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, TabExpander paramTabExpander, int paramInt3, boolean paramBoolean, int[] paramArrayOfint) {
/*  336 */     if (paramInt1 >= paramInt2)
/*      */     {
/*  338 */       return 0;
/*      */     }
/*  340 */     int i = paramInt1;
/*      */ 
/*      */     
/*  343 */     char[] arrayOfChar = paramSegment.array;
/*  344 */     int j = paramSegment.offset;
/*  345 */     int k = paramSegment.count;
/*  346 */     int m = 0;
/*  347 */     int n = -1;
/*  348 */     int i1 = 0;
/*  349 */     int i2 = 0;
/*  350 */     if (paramArrayOfint != null) {
/*  351 */       int i5 = -paramInt3 + j;
/*  352 */       View view = null;
/*  353 */       if (paramView != null && (
/*  354 */         view = paramView.getParent()) != null) {
/*  355 */         i5 += view.getStartOffset();
/*      */       }
/*  357 */       m = paramArrayOfint[0];
/*      */       
/*  359 */       n = paramArrayOfint[1] + i5;
/*      */       
/*  361 */       i1 = paramArrayOfint[2] + i5;
/*      */       
/*  363 */       i2 = paramArrayOfint[3] + i5;
/*      */     } 
/*      */     
/*  366 */     int i3 = paramSegment.offset + paramSegment.count;
/*  367 */     for (int i4 = paramSegment.offset; i4 < i3; i4++) {
/*  368 */       if (arrayOfChar[i4] == '\t' || ((m != 0 || i4 <= n) && arrayOfChar[i4] == ' ' && i1 <= i4 && i4 <= i2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  374 */         if (arrayOfChar[i4] == '\t') {
/*  375 */           if (paramTabExpander != null) {
/*  376 */             i = (int)paramTabExpander.nextTabStop(i, paramInt3 + i4 - j);
/*      */           } else {
/*      */             
/*  379 */             i += paramFontMetrics.charWidth(' ');
/*      */           } 
/*  381 */         } else if (arrayOfChar[i4] == ' ') {
/*  382 */           i += paramFontMetrics.charWidth(' ') + m;
/*  383 */           if (i4 <= n) {
/*  384 */             i++;
/*      */           }
/*      */         } 
/*      */       } else {
/*  388 */         i += paramFontMetrics.charWidth(arrayOfChar[i4]);
/*      */       } 
/*  390 */       if (paramInt2 < i) {
/*      */         int i5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  397 */         if (paramBoolean) {
/*  398 */           i5 = i4 + 1 - j;
/*      */           
/*  400 */           int i6 = paramFontMetrics.charsWidth(arrayOfChar, j, i5);
/*  401 */           int i7 = paramInt2 - paramInt1;
/*      */           
/*  403 */           if (i7 < i6) {
/*  404 */             while (i5 > 0) {
/*  405 */               byte b = (i5 > 1) ? paramFontMetrics.charsWidth(arrayOfChar, j, i5 - 1) : 0;
/*      */               
/*  407 */               if (i7 >= b) {
/*  408 */                 if (i7 - b < i6 - i7) {
/*  409 */                   i5--;
/*      */                 }
/*      */                 
/*      */                 break;
/*      */               } 
/*      */               
/*  415 */               i6 = b;
/*  416 */               i5--;
/*      */             } 
/*      */           }
/*      */         } else {
/*  420 */           i5 = i4 - j;
/*      */           
/*  422 */           while (i5 > 0 && paramFontMetrics.charsWidth(arrayOfChar, j, i5) > paramInt2 - paramInt1) {
/*  423 */             i5--;
/*      */           }
/*      */         } 
/*      */         
/*  427 */         return i5;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  432 */     return k;
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
/*      */   public static final int getBreakLocation(Segment paramSegment, FontMetrics paramFontMetrics, int paramInt1, int paramInt2, TabExpander paramTabExpander, int paramInt3) {
/*  452 */     char[] arrayOfChar = paramSegment.array;
/*  453 */     int i = paramSegment.offset;
/*  454 */     int j = paramSegment.count;
/*  455 */     int k = getTabbedTextOffset(paramSegment, paramFontMetrics, paramInt1, paramInt2, paramTabExpander, paramInt3, false);
/*      */ 
/*      */     
/*  458 */     if (k >= j - 1) {
/*  459 */       return j;
/*      */     }
/*      */     
/*  462 */     for (int m = i + k; m >= i; m--) {
/*  463 */       char c = arrayOfChar[m];
/*  464 */       if (c < 'Ā') {
/*      */         
/*  466 */         if (Character.isWhitespace(c)) {
/*  467 */           k = m - i + 1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } else {
/*  472 */         BreakIterator breakIterator = BreakIterator.getLineInstance();
/*  473 */         breakIterator.setText(paramSegment);
/*  474 */         int n = breakIterator.preceding(m + 1);
/*  475 */         if (n > i) {
/*  476 */           k = n - i;
/*      */         }
/*      */         break;
/*      */       } 
/*      */     } 
/*  481 */     return k;
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
/*      */   public static final int getRowStart(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/*  497 */     Rectangle rectangle = paramJTextComponent.modelToView(paramInt);
/*  498 */     if (rectangle == null) {
/*  499 */       return -1;
/*      */     }
/*  501 */     int i = paramInt;
/*  502 */     int j = rectangle.y;
/*  503 */     while (rectangle != null && j == rectangle.y) {
/*      */       
/*  505 */       if (rectangle.height != 0) {
/*  506 */         paramInt = i;
/*      */       }
/*  508 */       i--;
/*  509 */       rectangle = (i >= 0) ? paramJTextComponent.modelToView(i) : null;
/*      */     } 
/*  511 */     return paramInt;
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
/*      */   public static final int getRowEnd(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/*  527 */     Rectangle rectangle = paramJTextComponent.modelToView(paramInt);
/*  528 */     if (rectangle == null) {
/*  529 */       return -1;
/*      */     }
/*  531 */     int i = paramJTextComponent.getDocument().getLength();
/*  532 */     int j = paramInt;
/*  533 */     int k = rectangle.y;
/*  534 */     while (rectangle != null && k == rectangle.y) {
/*      */       
/*  536 */       if (rectangle.height != 0) {
/*  537 */         paramInt = j;
/*      */       }
/*  539 */       j++;
/*  540 */       rectangle = (j <= i) ? paramJTextComponent.modelToView(j) : null;
/*      */     } 
/*  542 */     return paramInt;
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
/*      */   public static final int getPositionAbove(JTextComponent paramJTextComponent, int paramInt1, int paramInt2) throws BadLocationException {
/*  559 */     int i = getRowStart(paramJTextComponent, paramInt1) - 1;
/*  560 */     if (i < 0) {
/*  561 */       return -1;
/*      */     }
/*  563 */     int j = Integer.MAX_VALUE;
/*  564 */     int k = 0;
/*  565 */     Rectangle rectangle = null;
/*  566 */     if (i >= 0) {
/*  567 */       rectangle = paramJTextComponent.modelToView(i);
/*  568 */       k = rectangle.y;
/*      */     } 
/*  570 */     while (rectangle != null && k == rectangle.y) {
/*  571 */       int m = Math.abs(rectangle.x - paramInt2);
/*  572 */       if (m < j) {
/*  573 */         paramInt1 = i;
/*  574 */         j = m;
/*      */       } 
/*  576 */       i--;
/*  577 */       rectangle = (i >= 0) ? paramJTextComponent.modelToView(i) : null;
/*      */     } 
/*  579 */     return paramInt1;
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
/*      */   public static final int getPositionBelow(JTextComponent paramJTextComponent, int paramInt1, int paramInt2) throws BadLocationException {
/*  596 */     int i = getRowEnd(paramJTextComponent, paramInt1) + 1;
/*  597 */     if (i <= 0) {
/*  598 */       return -1;
/*      */     }
/*  600 */     int j = Integer.MAX_VALUE;
/*  601 */     int k = paramJTextComponent.getDocument().getLength();
/*  602 */     int m = 0;
/*  603 */     Rectangle rectangle = null;
/*  604 */     if (i <= k) {
/*  605 */       rectangle = paramJTextComponent.modelToView(i);
/*  606 */       m = rectangle.y;
/*      */     } 
/*  608 */     while (rectangle != null && m == rectangle.y) {
/*  609 */       int n = Math.abs(paramInt2 - rectangle.x);
/*  610 */       if (n < j) {
/*  611 */         paramInt1 = i;
/*  612 */         j = n;
/*      */       } 
/*  614 */       i++;
/*  615 */       rectangle = (i <= k) ? paramJTextComponent.modelToView(i) : null;
/*      */     } 
/*  617 */     return paramInt1;
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
/*      */   public static final int getWordStart(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/*  630 */     Document document = paramJTextComponent.getDocument();
/*  631 */     Element element = getParagraphElement(paramJTextComponent, paramInt);
/*  632 */     if (element == null) {
/*  633 */       throw new BadLocationException("No word at " + paramInt, paramInt);
/*      */     }
/*  635 */     int i = element.getStartOffset();
/*  636 */     int j = Math.min(element.getEndOffset(), document.getLength());
/*      */     
/*  638 */     Segment segment = SegmentCache.getSharedSegment();
/*  639 */     document.getText(i, j - i, segment);
/*  640 */     if (segment.count > 0) {
/*  641 */       BreakIterator breakIterator = BreakIterator.getWordInstance(paramJTextComponent.getLocale());
/*  642 */       breakIterator.setText(segment);
/*  643 */       int k = segment.offset + paramInt - i;
/*  644 */       if (k >= breakIterator.last()) {
/*  645 */         k = breakIterator.last() - 1;
/*      */       }
/*  647 */       breakIterator.following(k);
/*  648 */       paramInt = i + breakIterator.previous() - segment.offset;
/*      */     } 
/*  650 */     SegmentCache.releaseSharedSegment(segment);
/*  651 */     return paramInt;
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
/*      */   public static final int getWordEnd(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/*  664 */     Document document = paramJTextComponent.getDocument();
/*  665 */     Element element = getParagraphElement(paramJTextComponent, paramInt);
/*  666 */     if (element == null) {
/*  667 */       throw new BadLocationException("No word at " + paramInt, paramInt);
/*      */     }
/*  669 */     int i = element.getStartOffset();
/*  670 */     int j = Math.min(element.getEndOffset(), document.getLength());
/*      */     
/*  672 */     Segment segment = SegmentCache.getSharedSegment();
/*  673 */     document.getText(i, j - i, segment);
/*  674 */     if (segment.count > 0) {
/*  675 */       BreakIterator breakIterator = BreakIterator.getWordInstance(paramJTextComponent.getLocale());
/*  676 */       breakIterator.setText(segment);
/*  677 */       int k = paramInt - i + segment.offset;
/*  678 */       if (k >= breakIterator.last()) {
/*  679 */         k = breakIterator.last() - 1;
/*      */       }
/*  681 */       paramInt = i + breakIterator.following(k) - segment.offset;
/*      */     } 
/*  683 */     SegmentCache.releaseSharedSegment(segment);
/*  684 */     return paramInt;
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
/*      */   public static final int getNextWord(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/*  698 */     Element element = getParagraphElement(paramJTextComponent, paramInt);
/*  699 */     int i = getNextWordInParagraph(paramJTextComponent, element, paramInt, false);
/*  700 */     for (; i == -1; 
/*  701 */       i = getNextWordInParagraph(paramJTextComponent, element, paramInt, true)) {
/*      */ 
/*      */       
/*  704 */       paramInt = element.getEndOffset();
/*  705 */       element = getParagraphElement(paramJTextComponent, paramInt);
/*      */     } 
/*  707 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getNextWordInParagraph(JTextComponent paramJTextComponent, Element paramElement, int paramInt, boolean paramBoolean) throws BadLocationException {
/*  718 */     if (paramElement == null) {
/*  719 */       throw new BadLocationException("No more words", paramInt);
/*      */     }
/*  721 */     Document document = paramElement.getDocument();
/*  722 */     int i = paramElement.getStartOffset();
/*  723 */     int j = Math.min(paramElement.getEndOffset(), document.getLength());
/*  724 */     if (paramInt >= j || paramInt < i) {
/*  725 */       throw new BadLocationException("No more words", paramInt);
/*      */     }
/*  727 */     Segment segment = SegmentCache.getSharedSegment();
/*  728 */     document.getText(i, j - i, segment);
/*  729 */     BreakIterator breakIterator = BreakIterator.getWordInstance(paramJTextComponent.getLocale());
/*  730 */     breakIterator.setText(segment);
/*  731 */     if (paramBoolean && breakIterator.first() == segment.offset + paramInt - i && 
/*  732 */       !Character.isWhitespace(segment.array[breakIterator.first()]))
/*      */     {
/*  734 */       return paramInt;
/*      */     }
/*  736 */     int k = breakIterator.following(segment.offset + paramInt - i);
/*  737 */     if (k == -1 || k >= segment.offset + segment.count)
/*      */     {
/*      */       
/*  740 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  745 */     char c = segment.array[k];
/*  746 */     if (!Character.isWhitespace(c)) {
/*  747 */       return i + k - segment.offset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  753 */     k = breakIterator.next();
/*  754 */     if (k != -1) {
/*  755 */       paramInt = i + k - segment.offset;
/*  756 */       if (paramInt != j) {
/*  757 */         return paramInt;
/*      */       }
/*      */     } 
/*  760 */     SegmentCache.releaseSharedSegment(segment);
/*  761 */     return -1;
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
/*      */   public static final int getPreviousWord(JTextComponent paramJTextComponent, int paramInt) throws BadLocationException {
/*  776 */     Element element = getParagraphElement(paramJTextComponent, paramInt);
/*  777 */     int i = getPrevWordInParagraph(paramJTextComponent, element, paramInt);
/*  778 */     for (; i == -1; 
/*  779 */       i = getPrevWordInParagraph(paramJTextComponent, element, paramInt)) {
/*      */ 
/*      */       
/*  782 */       paramInt = element.getStartOffset() - 1;
/*  783 */       element = getParagraphElement(paramJTextComponent, paramInt);
/*      */     } 
/*  785 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getPrevWordInParagraph(JTextComponent paramJTextComponent, Element paramElement, int paramInt) throws BadLocationException {
/*  796 */     if (paramElement == null) {
/*  797 */       throw new BadLocationException("No more words", paramInt);
/*      */     }
/*  799 */     Document document = paramElement.getDocument();
/*  800 */     int i = paramElement.getStartOffset();
/*  801 */     int j = paramElement.getEndOffset();
/*  802 */     if (paramInt > j || paramInt < i) {
/*  803 */       throw new BadLocationException("No more words", paramInt);
/*      */     }
/*  805 */     Segment segment = SegmentCache.getSharedSegment();
/*  806 */     document.getText(i, j - i, segment);
/*  807 */     BreakIterator breakIterator = BreakIterator.getWordInstance(paramJTextComponent.getLocale());
/*  808 */     breakIterator.setText(segment);
/*  809 */     if (breakIterator.following(segment.offset + paramInt - i) == -1) {
/*  810 */       breakIterator.last();
/*      */     }
/*  812 */     int k = breakIterator.previous();
/*  813 */     if (k == segment.offset + paramInt - i) {
/*  814 */       k = breakIterator.previous();
/*      */     }
/*      */     
/*  817 */     if (k == -1)
/*      */     {
/*  819 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  824 */     char c = segment.array[k];
/*  825 */     if (!Character.isWhitespace(c)) {
/*  826 */       return i + k - segment.offset;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  832 */     k = breakIterator.previous();
/*  833 */     if (k != -1) {
/*  834 */       return i + k - segment.offset;
/*      */     }
/*  836 */     SegmentCache.releaseSharedSegment(segment);
/*  837 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Element getParagraphElement(JTextComponent paramJTextComponent, int paramInt) {
/*  848 */     Document document = paramJTextComponent.getDocument();
/*  849 */     if (document instanceof StyledDocument) {
/*  850 */       return ((StyledDocument)document).getParagraphElement(paramInt);
/*      */     }
/*  852 */     Element element1 = document.getDefaultRootElement();
/*  853 */     int i = element1.getElementIndex(paramInt);
/*  854 */     Element element2 = element1.getElement(i);
/*  855 */     if (paramInt >= element2.getStartOffset() && paramInt < element2.getEndOffset()) {
/*  856 */       return element2;
/*      */     }
/*  858 */     return null;
/*      */   }
/*      */   
/*      */   static boolean isComposedTextElement(Document paramDocument, int paramInt) {
/*  862 */     Element element = paramDocument.getDefaultRootElement();
/*  863 */     while (!element.isLeaf()) {
/*  864 */       element = element.getElement(element.getElementIndex(paramInt));
/*      */     }
/*  866 */     return isComposedTextElement(element);
/*      */   }
/*      */   
/*      */   static boolean isComposedTextElement(Element paramElement) {
/*  870 */     AttributeSet attributeSet = paramElement.getAttributes();
/*  871 */     return isComposedTextAttributeDefined(attributeSet);
/*      */   }
/*      */   
/*      */   static boolean isComposedTextAttributeDefined(AttributeSet paramAttributeSet) {
/*  875 */     return (paramAttributeSet != null && paramAttributeSet
/*  876 */       .isDefined(StyleConstants.ComposedTextAttribute));
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
/*      */   static int drawComposedText(View paramView, AttributeSet paramAttributeSet, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws BadLocationException {
/*  894 */     Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  895 */     AttributedString attributedString = (AttributedString)paramAttributeSet.getAttribute(StyleConstants.ComposedTextAttribute);
/*      */     
/*  897 */     attributedString.addAttribute(TextAttribute.FONT, paramGraphics.getFont());
/*      */     
/*  899 */     if (paramInt3 >= paramInt4) {
/*  900 */       return paramInt1;
/*      */     }
/*  902 */     AttributedCharacterIterator attributedCharacterIterator = attributedString.getIterator(null, paramInt3, paramInt4);
/*  903 */     return paramInt1 + (int)SwingUtilities2.drawString(
/*  904 */         getJComponent(paramView), graphics2D, attributedCharacterIterator, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void paintComposedText(Graphics paramGraphics, Rectangle paramRectangle, GlyphView paramGlyphView) {
/*  911 */     if (paramGraphics instanceof Graphics2D) {
/*  912 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/*  913 */       int i = paramGlyphView.getStartOffset();
/*  914 */       int j = paramGlyphView.getEndOffset();
/*  915 */       AttributeSet attributeSet = paramGlyphView.getElement().getAttributes();
/*      */       
/*  917 */       AttributedString attributedString = (AttributedString)attributeSet.getAttribute(StyleConstants.ComposedTextAttribute);
/*  918 */       int k = paramGlyphView.getElement().getStartOffset();
/*  919 */       int m = paramRectangle.y + paramRectangle.height - (int)paramGlyphView.getGlyphPainter().getDescent(paramGlyphView);
/*  920 */       int n = paramRectangle.x;
/*      */ 
/*      */       
/*  923 */       attributedString.addAttribute(TextAttribute.FONT, paramGlyphView.getFont());
/*  924 */       attributedString.addAttribute(TextAttribute.FOREGROUND, paramGlyphView.getForeground());
/*  925 */       if (StyleConstants.isBold(paramGlyphView.getAttributes())) {
/*  926 */         attributedString.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
/*      */       }
/*  928 */       if (StyleConstants.isItalic(paramGlyphView.getAttributes())) {
/*  929 */         attributedString.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
/*      */       }
/*  931 */       if (paramGlyphView.isUnderline()) {
/*  932 */         attributedString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
/*      */       }
/*  934 */       if (paramGlyphView.isStrikeThrough()) {
/*  935 */         attributedString.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
/*      */       }
/*  937 */       if (paramGlyphView.isSuperscript()) {
/*  938 */         attributedString.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
/*      */       }
/*  940 */       if (paramGlyphView.isSubscript()) {
/*  941 */         attributedString.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
/*      */       }
/*      */ 
/*      */       
/*  945 */       AttributedCharacterIterator attributedCharacterIterator = attributedString.getIterator(null, i - k, j - k);
/*  946 */       SwingUtilities2.drawString(getJComponent(paramGlyphView), graphics2D, attributedCharacterIterator, n, m);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isLeftToRight(Component paramComponent) {
/*  956 */     return paramComponent.getComponentOrientation().isLeftToRight();
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
/*      */ 
/*      */   
/*      */   static int getNextVisualPositionFrom(View paramView, int paramInt1, Position.Bias paramBias, Shape paramShape, int paramInt2, Position.Bias[] paramArrayOfBias) throws BadLocationException {
/*      */     int i;
/*  996 */     if (paramView.getViewCount() == 0)
/*      */     {
/*  998 */       return paramInt1;
/*      */     }
/* 1000 */     boolean bool = (paramInt2 == 1 || paramInt2 == 7) ? true : false;
/*      */ 
/*      */     
/* 1003 */     if (paramInt1 == -1) {
/*      */       
/* 1005 */       boolean bool1 = bool ? (paramView.getViewCount() - 1) : false;
/* 1006 */       View view = paramView.getView(bool1);
/* 1007 */       Shape shape = paramView.getChildAllocation(bool1, paramShape);
/* 1008 */       i = view.getNextVisualPositionFrom(paramInt1, paramBias, shape, paramInt2, paramArrayOfBias);
/*      */       
/* 1010 */       if (i == -1 && !bool && paramView.getViewCount() > 1)
/*      */       {
/*      */ 
/*      */         
/* 1014 */         view = paramView.getView(1);
/* 1015 */         shape = paramView.getChildAllocation(1, paramShape);
/* 1016 */         i = view.getNextVisualPositionFrom(-1, paramArrayOfBias[0], shape, paramInt2, paramArrayOfBias);
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1022 */       int k, j = bool ? -1 : 1;
/*      */       
/* 1024 */       if (paramBias == Position.Bias.Backward && paramInt1 > 0) {
/* 1025 */         k = paramView.getViewIndex(paramInt1 - 1, Position.Bias.Forward);
/*      */       } else {
/*      */         
/* 1028 */         k = paramView.getViewIndex(paramInt1, Position.Bias.Forward);
/*      */       } 
/* 1030 */       View view = paramView.getView(k);
/* 1031 */       Shape shape = paramView.getChildAllocation(k, paramShape);
/* 1032 */       i = view.getNextVisualPositionFrom(paramInt1, paramBias, shape, paramInt2, paramArrayOfBias);
/*      */       
/* 1034 */       if ((paramInt2 == 3 || paramInt2 == 7) && paramView instanceof CompositeView && ((CompositeView)paramView)
/*      */ 
/*      */         
/* 1037 */         .flipEastAndWestAtEnds(paramInt1, paramBias)) {
/* 1038 */         j *= -1;
/*      */       }
/* 1040 */       k += j;
/* 1041 */       if (i == -1 && k >= 0 && k < paramView
/* 1042 */         .getViewCount()) {
/* 1043 */         view = paramView.getView(k);
/* 1044 */         shape = paramView.getChildAllocation(k, paramShape);
/* 1045 */         i = view.getNextVisualPositionFrom(-1, paramBias, shape, paramInt2, paramArrayOfBias);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1050 */         if (i == paramInt1 && paramArrayOfBias[0] != paramBias) {
/* 1051 */           return getNextVisualPositionFrom(paramView, paramInt1, paramArrayOfBias[0], paramShape, paramInt2, paramArrayOfBias);
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1056 */       else if (i != -1 && paramArrayOfBias[0] != paramBias && ((j == 1 && view
/* 1057 */         .getEndOffset() == i) || (j == -1 && view
/*      */         
/* 1059 */         .getStartOffset() == i)) && k >= 0 && k < paramView
/* 1060 */         .getViewCount()) {
/*      */ 
/*      */         
/* 1063 */         view = paramView.getView(k);
/* 1064 */         shape = paramView.getChildAllocation(k, paramShape);
/* 1065 */         Position.Bias bias = paramArrayOfBias[0];
/* 1066 */         int m = view.getNextVisualPositionFrom(-1, paramBias, shape, paramInt2, paramArrayOfBias);
/*      */         
/* 1068 */         if (paramArrayOfBias[0] == paramBias) {
/* 1069 */           i = m;
/*      */         } else {
/*      */           
/* 1072 */           paramArrayOfBias[0] = bias;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1076 */     return i;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/Utilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */