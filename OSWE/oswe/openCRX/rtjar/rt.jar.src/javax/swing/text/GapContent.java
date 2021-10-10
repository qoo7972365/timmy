/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Vector;
/*     */ import javax.swing.undo.AbstractUndoableEdit;
/*     */ import javax.swing.undo.CannotRedoException;
/*     */ import javax.swing.undo.CannotUndoException;
/*     */ import javax.swing.undo.UndoableEdit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GapContent
/*     */   extends GapVector
/*     */   implements AbstractDocument.Content, Serializable
/*     */ {
/*     */   public GapContent() {
/*  67 */     this(10);
/*     */   }
/*     */   protected Object allocateArray(int paramInt) { return new char[paramInt]; }
/*     */   protected int getArrayLength() { char[] arrayOfChar = (char[])getArray(); return arrayOfChar.length; }
/*     */   public int length() { return getArrayLength() - getGapEnd() - getGapStart(); }
/*     */   public UndoableEdit insertString(int paramInt, String paramString) throws BadLocationException { if (paramInt > length() || paramInt < 0)
/*     */       throw new BadLocationException("Invalid insert", length());  char[] arrayOfChar = paramString.toCharArray(); replace(paramInt, 0, arrayOfChar, arrayOfChar.length); return new InsertUndo(paramInt, paramString.length()); }
/*     */   public UndoableEdit remove(int paramInt1, int paramInt2) throws BadLocationException { if (paramInt1 + paramInt2 >= length())
/*     */       throw new BadLocationException("Invalid remove", length() + 1);  String str = getString(paramInt1, paramInt2); RemoveUndo removeUndo = new RemoveUndo(paramInt1, str);
/*     */     replace(paramInt1, paramInt2, empty, 0);
/*     */     return removeUndo; } public String getString(int paramInt1, int paramInt2) throws BadLocationException { Segment segment = new Segment();
/*     */     getChars(paramInt1, paramInt2, segment);
/*  79 */     return new String(segment.array, segment.offset, segment.count); } public GapContent(int paramInt) { super(Math.max(paramInt, 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     this.unusedMarks = 0; char[] arrayOfChar = new char[1]; arrayOfChar[0] = '\n'; replace(0, 0, arrayOfChar, arrayOfChar.length); this.marks = new MarkVector(); this.search = new MarkData(0); this.queue = new ReferenceQueue<>(); }
/*     */   public void getChars(int paramInt1, int paramInt2, Segment paramSegment) throws BadLocationException { int i = paramInt1 + paramInt2; if (paramInt1 < 0 || i < 0)
/*     */       throw new BadLocationException("Invalid location", -1);  if (i > length() || paramInt1 > length())
/*     */       throw new BadLocationException("Invalid location", length() + 1);  int j = getGapStart(); int k = getGapEnd(); char[] arrayOfChar = (char[])getArray(); if (paramInt1 + paramInt2 <= j) { paramSegment.array = arrayOfChar; paramSegment.offset = paramInt1; }
/*     */     else if (paramInt1 >= j) { paramSegment.array = arrayOfChar; paramSegment.offset = k + paramInt1 - j; }
/*     */     else { int m = j - paramInt1; if (paramSegment.isPartialReturn()) {
/*     */         paramSegment.array = arrayOfChar; paramSegment.offset = paramInt1; paramSegment.count = m; return;
/*     */       }  paramSegment.array = new char[paramInt2]; paramSegment.offset = 0; System.arraycopy(arrayOfChar, paramInt1, paramSegment.array, 0, m); System.arraycopy(arrayOfChar, k, paramSegment.array, m, paramInt2 - m); }
/*     */      paramSegment.count = paramInt2; }
/*     */   public Position createPosition(int paramInt) throws BadLocationException { while (this.queue.poll() != null)
/*     */       this.unusedMarks++;  if (this.unusedMarks > Math.max(5, this.marks.size() / 10))
/*     */       removeUnusedMarks();  int i = getGapStart(); int j = getGapEnd(); int k = (paramInt < i) ? paramInt : (paramInt + j - i); this.search.index = k; int m = findSortIndex(this.search); MarkData markData; StickyPosition stickyPosition; if (m >= this.marks.size() || (markData = this.marks.elementAt(m)).index != k || (stickyPosition = markData.getPosition()) == null) {
/*     */       stickyPosition = new StickyPosition(); markData = new MarkData(k, stickyPosition, this.queue); stickyPosition.setMark(markData); this.marks.insertElementAt(markData, m);
/* 343 */     }  return stickyPosition; } protected void shiftEnd(int paramInt) { int i = getGapEnd();
/*     */     
/* 345 */     super.shiftEnd(paramInt);
/*     */ 
/*     */     
/* 348 */     int j = getGapEnd() - i;
/* 349 */     int k = findMarkAdjustIndex(i);
/* 350 */     int m = this.marks.size();
/* 351 */     for (int n = k; n < m; n++)
/* 352 */     { MarkData markData = this.marks.elementAt(n);
/* 353 */       markData.index += j; }  } final class MarkData extends WeakReference<StickyPosition> {
/*     */     int index; MarkData(int param1Int) { super((GapContent.StickyPosition)null); this.index = param1Int; } MarkData(int param1Int, GapContent.StickyPosition param1StickyPosition, ReferenceQueue<? super GapContent.StickyPosition> param1ReferenceQueue) { super(param1StickyPosition, param1ReferenceQueue); this.index = param1Int; }
/*     */     public final int getOffset() { int i = GapContent.this.getGapStart(); int j = GapContent.this.getGapEnd(); int k = (this.index < i) ? this.index : (this.index - j - i); return Math.max(k, 0); }
/*     */     GapContent.StickyPosition getPosition() { return get(); } }
/*     */   final class StickyPosition implements Position {
/*     */     GapContent.MarkData mark;
/*     */     void setMark(GapContent.MarkData param1MarkData) { this.mark = param1MarkData; }
/*     */     public final int getOffset() { return this.mark.getOffset(); }
/*     */     public String toString() { return Integer.toString(getOffset()); } } private static final char[] empty = new char[0]; private transient MarkVector marks; private transient MarkData search; private transient int unusedMarks; private transient ReferenceQueue<StickyPosition> queue; static final int GROWTH_SIZE = 524288;
/* 362 */   int getNewArraySize(int paramInt) { if (paramInt < 524288) {
/* 363 */       return super.getNewArraySize(paramInt);
/*     */     }
/* 365 */     return paramInt + 524288; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shiftGap(int paramInt) {
/* 376 */     int i = getGapStart();
/* 377 */     int j = paramInt - i;
/* 378 */     int k = getGapEnd();
/* 379 */     int m = k + j;
/* 380 */     int n = k - i;
/*     */ 
/*     */     
/* 383 */     super.shiftGap(paramInt);
/*     */ 
/*     */     
/* 386 */     if (j > 0) {
/*     */       
/* 388 */       int i1 = findMarkAdjustIndex(i);
/* 389 */       int i2 = this.marks.size();
/* 390 */       for (int i3 = i1; i3 < i2; i3++) {
/* 391 */         MarkData markData = this.marks.elementAt(i3);
/* 392 */         if (markData.index >= m) {
/*     */           break;
/*     */         }
/* 395 */         markData.index -= n;
/*     */       } 
/* 397 */     } else if (j < 0) {
/*     */       
/* 399 */       int i1 = findMarkAdjustIndex(paramInt);
/* 400 */       int i2 = this.marks.size();
/* 401 */       for (int i3 = i1; i3 < i2; i3++) {
/* 402 */         MarkData markData = this.marks.elementAt(i3);
/* 403 */         if (markData.index >= k) {
/*     */           break;
/*     */         }
/* 406 */         markData.index += n;
/*     */       } 
/*     */     } 
/* 409 */     resetMarksAtZero();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetMarksAtZero() {
/* 417 */     if (this.marks != null && getGapStart() == 0) {
/* 418 */       int i = getGapEnd();
/* 419 */       byte b = 0; int j = this.marks.size();
/* 420 */       while (b < j) {
/* 421 */         MarkData markData = this.marks.elementAt(b);
/* 422 */         if (markData.index <= i) {
/* 423 */           markData.index = 0;
/*     */           b++;
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
/*     */   protected void shiftGapStartDown(int paramInt) {
/* 442 */     int i = findMarkAdjustIndex(paramInt);
/* 443 */     int j = this.marks.size();
/* 444 */     int k = getGapStart();
/* 445 */     int m = getGapEnd();
/* 446 */     for (int n = i; n < j; n++) {
/* 447 */       MarkData markData = this.marks.elementAt(n);
/* 448 */       if (markData.index > k) {
/*     */         break;
/*     */       }
/*     */       
/* 452 */       markData.index = m;
/*     */     } 
/*     */ 
/*     */     
/* 456 */     super.shiftGapStartDown(paramInt);
/*     */     
/* 458 */     resetMarksAtZero();
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
/*     */   protected void shiftGapEndUp(int paramInt) {
/* 470 */     int i = findMarkAdjustIndex(getGapEnd());
/* 471 */     int j = this.marks.size();
/* 472 */     for (int k = i; k < j; k++) {
/* 473 */       MarkData markData = this.marks.elementAt(k);
/* 474 */       if (markData.index >= paramInt) {
/*     */         break;
/*     */       }
/* 477 */       markData.index = paramInt;
/*     */     } 
/*     */ 
/*     */     
/* 481 */     super.shiftGapEndUp(paramInt);
/*     */     
/* 483 */     resetMarksAtZero();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int compare(MarkData paramMarkData1, MarkData paramMarkData2) {
/* 494 */     if (paramMarkData1.index < paramMarkData2.index)
/* 495 */       return -1; 
/* 496 */     if (paramMarkData1.index > paramMarkData2.index) {
/* 497 */       return 1;
/*     */     }
/* 499 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int findMarkAdjustIndex(int paramInt) {
/* 508 */     this.search.index = Math.max(paramInt, 1);
/* 509 */     int i = findSortIndex(this.search);
/*     */ 
/*     */ 
/*     */     
/* 513 */     for (int j = i - 1; j >= 0; j--) {
/* 514 */       MarkData markData = this.marks.elementAt(j);
/* 515 */       if (markData.index != this.search.index) {
/*     */         break;
/*     */       }
/* 518 */       i--;
/*     */     } 
/* 520 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int findSortIndex(MarkData paramMarkData) {
/* 530 */     int i = 0;
/* 531 */     int j = this.marks.size() - 1;
/* 532 */     int k = 0;
/*     */     
/* 534 */     if (j == -1) {
/* 535 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 539 */     MarkData markData = this.marks.elementAt(j);
/* 540 */     int m = compare(paramMarkData, markData);
/* 541 */     if (m > 0) {
/* 542 */       return j + 1;
/*     */     }
/* 544 */     while (i <= j) {
/* 545 */       k = i + (j - i) / 2;
/* 546 */       MarkData markData1 = this.marks.elementAt(k);
/* 547 */       m = compare(paramMarkData, markData1);
/*     */       
/* 549 */       if (m == 0)
/*     */       {
/* 551 */         return k; } 
/* 552 */       if (m < 0) {
/* 553 */         j = k - 1; continue;
/*     */       } 
/* 555 */       i = k + 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 560 */     return (m < 0) ? k : (k + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void removeUnusedMarks() {
/* 568 */     int i = this.marks.size();
/* 569 */     MarkVector markVector = new MarkVector(i);
/* 570 */     for (byte b = 0; b < i; b++) {
/* 571 */       MarkData markData = this.marks.elementAt(b);
/* 572 */       if (markData.get() != null) {
/* 573 */         markVector.addElement(markData);
/*     */       }
/*     */     } 
/* 576 */     this.marks = markVector;
/* 577 */     this.unusedMarks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   static class MarkVector
/*     */     extends GapVector
/*     */   {
/*     */     MarkVector() {}
/*     */ 
/*     */     
/*     */     MarkVector(int param1Int) {
/* 588 */       super(param1Int);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Object allocateArray(int param1Int) {
/* 596 */       return new GapContent.MarkData[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int getArrayLength() {
/* 603 */       GapContent.MarkData[] arrayOfMarkData = (GapContent.MarkData[])getArray();
/* 604 */       return arrayOfMarkData.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 611 */       return getArrayLength() - getGapEnd() - getGapStart();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insertElementAt(GapContent.MarkData param1MarkData, int param1Int) {
/* 619 */       this.oneMark[0] = param1MarkData;
/* 620 */       replace(param1Int, 0, this.oneMark, 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addElement(GapContent.MarkData param1MarkData) {
/* 627 */       insertElementAt(param1MarkData, size());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public GapContent.MarkData elementAt(int param1Int) {
/* 634 */       int i = getGapStart();
/* 635 */       int j = getGapEnd();
/* 636 */       GapContent.MarkData[] arrayOfMarkData = (GapContent.MarkData[])getArray();
/* 637 */       if (param1Int < i)
/*     */       {
/* 639 */         return arrayOfMarkData[param1Int];
/*     */       }
/*     */       
/* 642 */       param1Int += j - i;
/* 643 */       return arrayOfMarkData[param1Int];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void replaceRange(int param1Int1, int param1Int2, Object[] param1ArrayOfObject) {
/* 654 */       int i = getGapStart();
/* 655 */       int j = getGapEnd();
/* 656 */       int k = param1Int1;
/* 657 */       byte b = 0;
/* 658 */       Object[] arrayOfObject = (Object[])getArray();
/* 659 */       if (param1Int1 >= i) {
/*     */         
/* 661 */         k += j - i;
/* 662 */         param1Int2 += j - i;
/*     */       }
/* 664 */       else if (param1Int2 >= i) {
/*     */         
/* 666 */         param1Int2 += j - i;
/* 667 */         while (k < i) {
/* 668 */           arrayOfObject[k++] = param1ArrayOfObject[b++];
/*     */         }
/* 670 */         k = j;
/*     */       }
/*     */       else {
/*     */         
/* 674 */         while (k < param1Int2) {
/* 675 */           arrayOfObject[k++] = param1ArrayOfObject[b++];
/*     */         }
/*     */       } 
/* 678 */       while (k < param1Int2) {
/* 679 */         arrayOfObject[k++] = param1ArrayOfObject[b++];
/*     */       }
/*     */     }
/*     */     
/* 683 */     GapContent.MarkData[] oneMark = new GapContent.MarkData[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 691 */     paramObjectInputStream.defaultReadObject();
/* 692 */     this.marks = new MarkVector();
/* 693 */     this.search = new MarkData(0);
/* 694 */     this.queue = new ReferenceQueue<>();
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
/*     */   protected Vector getPositionsInRange(Vector paramVector, int paramInt1, int paramInt2) {
/* 713 */     int j, k, i = paramInt1 + paramInt2;
/*     */ 
/*     */     
/* 716 */     int m = getGapStart();
/* 717 */     int n = getGapEnd();
/*     */ 
/*     */     
/* 720 */     if (paramInt1 < m) {
/* 721 */       if (paramInt1 == 0) {
/*     */         
/* 723 */         j = 0;
/*     */       } else {
/*     */         
/* 726 */         j = findMarkAdjustIndex(paramInt1);
/*     */       } 
/* 728 */       if (i >= m) {
/* 729 */         k = findMarkAdjustIndex(i + n - m + 1);
/*     */       } else {
/*     */         
/* 732 */         k = findMarkAdjustIndex(i + 1);
/*     */       } 
/*     */     } else {
/*     */       
/* 736 */       j = findMarkAdjustIndex(paramInt1 + n - m);
/* 737 */       k = findMarkAdjustIndex(i + n - m + 1);
/*     */     } 
/*     */     
/* 740 */     Vector<UndoPosRef> vector = (paramVector == null) ? new Vector(Math.max(1, k - j)) : paramVector;
/*     */ 
/*     */     
/* 743 */     for (int i1 = j; i1 < k; i1++) {
/* 744 */       vector.addElement(new UndoPosRef(this.marks.elementAt(i1)));
/*     */     }
/* 746 */     return vector;
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
/*     */   protected void updateUndoPositions(Vector<UndoPosRef> paramVector, int paramInt1, int paramInt2) {
/*     */     byte b;
/* 761 */     int i = paramInt1 + paramInt2;
/* 762 */     int j = getGapEnd();
/*     */     
/* 764 */     int k = findMarkAdjustIndex(j + 1);
/*     */     
/* 766 */     if (paramInt1 != 0) {
/* 767 */       b = findMarkAdjustIndex(j);
/*     */     } else {
/*     */       
/* 770 */       b = 0;
/*     */     } 
/*     */ 
/*     */     
/* 774 */     for (int m = paramVector.size() - 1; m >= 0; m--) {
/* 775 */       UndoPosRef undoPosRef = paramVector.elementAt(m);
/* 776 */       undoPosRef.resetLocation(i, j);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 782 */     if (b < k) {
/* 783 */       Object[] arrayOfObject = new Object[k - b];
/* 784 */       byte b1 = 0;
/*     */       
/* 786 */       if (paramInt1 == 0) {
/*     */         byte b2;
/*     */ 
/*     */         
/* 790 */         for (b2 = b; b2 < k; b2++) {
/* 791 */           MarkData markData = this.marks.elementAt(b2);
/* 792 */           if (markData.index == 0) {
/* 793 */             arrayOfObject[b1++] = markData;
/*     */           }
/*     */         } 
/* 796 */         for (b2 = b; b2 < k; b2++) {
/* 797 */           MarkData markData = this.marks.elementAt(b2);
/* 798 */           if (markData.index != 0) {
/* 799 */             arrayOfObject[b1++] = markData;
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         byte b2;
/* 804 */         for (b2 = b; b2 < k; b2++) {
/* 805 */           MarkData markData = this.marks.elementAt(b2);
/* 806 */           if (markData.index != j) {
/* 807 */             arrayOfObject[b1++] = markData;
/*     */           }
/*     */         } 
/* 810 */         for (b2 = b; b2 < k; b2++) {
/* 811 */           MarkData markData = this.marks.elementAt(b2);
/* 812 */           if (markData.index == j) {
/* 813 */             arrayOfObject[b1++] = markData;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 818 */       this.marks.replaceRange(b, k, arrayOfObject);
/*     */     } 
/*     */   }
/*     */   
/*     */   final class UndoPosRef
/*     */   {
/*     */     protected int undoLocation;
/*     */     protected GapContent.MarkData rec;
/*     */     
/*     */     UndoPosRef(GapContent.MarkData param1MarkData) {
/* 828 */       this.rec = param1MarkData;
/* 829 */       this.undoLocation = param1MarkData.getOffset();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void resetLocation(int param1Int1, int param1Int2) {
/* 840 */       if (this.undoLocation != param1Int1) {
/* 841 */         this.rec.index = this.undoLocation;
/*     */       } else {
/*     */         
/* 844 */         this.rec.index = param1Int2;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class InsertUndo
/*     */     extends AbstractUndoableEdit
/*     */   {
/*     */     protected int offset;
/*     */     
/*     */     protected int length;
/*     */     
/*     */     protected String string;
/*     */     protected Vector posRefs;
/*     */     
/*     */     protected InsertUndo(int param1Int1, int param1Int2) {
/* 861 */       this.offset = param1Int1;
/* 862 */       this.length = param1Int2;
/*     */     }
/*     */     
/*     */     public void undo() throws CannotUndoException {
/* 866 */       super.undo();
/*     */       
/*     */       try {
/* 869 */         this.posRefs = GapContent.this.getPositionsInRange((Vector)null, this.offset, this.length);
/* 870 */         this.string = GapContent.this.getString(this.offset, this.length);
/* 871 */         GapContent.this.remove(this.offset, this.length);
/* 872 */       } catch (BadLocationException badLocationException) {
/* 873 */         throw new CannotUndoException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void redo() throws CannotRedoException {
/* 878 */       super.redo();
/*     */       try {
/* 880 */         GapContent.this.insertString(this.offset, this.string);
/* 881 */         this.string = null;
/*     */         
/* 883 */         if (this.posRefs != null) {
/* 884 */           GapContent.this.updateUndoPositions(this.posRefs, this.offset, this.length);
/* 885 */           this.posRefs = null;
/*     */         } 
/* 887 */       } catch (BadLocationException badLocationException) {
/* 888 */         throw new CannotRedoException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class RemoveUndo
/*     */     extends AbstractUndoableEdit
/*     */   {
/*     */     protected int offset;
/*     */ 
/*     */     
/*     */     protected int length;
/*     */ 
/*     */     
/*     */     protected String string;
/*     */ 
/*     */     
/*     */     protected Vector posRefs;
/*     */ 
/*     */     
/*     */     protected RemoveUndo(int param1Int, String param1String) {
/* 911 */       this.offset = param1Int;
/* 912 */       this.string = param1String;
/* 913 */       this.length = param1String.length();
/* 914 */       this.posRefs = GapContent.this.getPositionsInRange((Vector)null, param1Int, this.length);
/*     */     }
/*     */     
/*     */     public void undo() throws CannotUndoException {
/* 918 */       super.undo();
/*     */       try {
/* 920 */         GapContent.this.insertString(this.offset, this.string);
/*     */         
/* 922 */         if (this.posRefs != null) {
/* 923 */           GapContent.this.updateUndoPositions(this.posRefs, this.offset, this.length);
/* 924 */           this.posRefs = null;
/*     */         } 
/* 926 */         this.string = null;
/* 927 */       } catch (BadLocationException badLocationException) {
/* 928 */         throw new CannotUndoException();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void redo() throws CannotRedoException {
/* 933 */       super.redo();
/*     */       try {
/* 935 */         this.string = GapContent.this.getString(this.offset, this.length);
/*     */         
/* 937 */         this.posRefs = GapContent.this.getPositionsInRange((Vector)null, this.offset, this.length);
/* 938 */         GapContent.this.remove(this.offset, this.length);
/* 939 */       } catch (BadLocationException badLocationException) {
/* 940 */         throw new CannotRedoException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/GapContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */