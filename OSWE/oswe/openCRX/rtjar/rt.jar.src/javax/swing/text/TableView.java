/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.util.BitSet;
/*     */ import java.util.Vector;
/*     */ import javax.swing.SizeRequirements;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.text.html.HTML;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TableView
/*     */   extends BoxView
/*     */ {
/*     */   int[] columnSpans;
/*     */   int[] columnOffsets;
/*     */   SizeRequirements[] columnRequirements;
/*     */   Vector<TableRow> rows;
/*     */   boolean gridValid;
/*     */   
/*     */   public TableView(Element paramElement) {
/*  78 */     super(paramElement, 1);
/*  79 */     this.rows = new Vector<>();
/*  80 */     this.gridValid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TableRow createTableRow(Element paramElement) {
/*  90 */     return new TableRow(paramElement);
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
/*     */   @Deprecated
/*     */   protected TableCell createTableCell(Element paramElement) {
/* 103 */     return new TableCell(paramElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getColumnCount() {
/* 110 */     return this.columnSpans.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getColumnSpan(int paramInt) {
/* 119 */     return this.columnSpans[paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getRowCount() {
/* 126 */     return this.rows.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getRowSpan(int paramInt) {
/* 133 */     TableRow tableRow = getRow(paramInt);
/* 134 */     if (tableRow != null) {
/* 135 */       return (int)tableRow.getPreferredSpan(1);
/*     */     }
/* 137 */     return 0;
/*     */   }
/*     */   
/*     */   TableRow getRow(int paramInt) {
/* 141 */     if (paramInt < this.rows.size()) {
/* 142 */       return this.rows.elementAt(paramInt);
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getColumnsOccupied(View paramView) {
/* 154 */     AttributeSet attributeSet = paramView.getElement().getAttributes();
/* 155 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.COLSPAN);
/* 156 */     if (str != null) {
/*     */       try {
/* 158 */         return Integer.parseInt(str);
/* 159 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 164 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getRowsOccupied(View paramView) {
/* 174 */     AttributeSet attributeSet = paramView.getElement().getAttributes();
/* 175 */     String str = (String)attributeSet.getAttribute(HTML.Attribute.ROWSPAN);
/* 176 */     if (str != null) {
/*     */       try {
/* 178 */         return Integer.parseInt(str);
/* 179 */       } catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 184 */     return 1;
/*     */   }
/*     */   
/*     */   void invalidateGrid() {
/* 188 */     this.gridValid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void forwardUpdate(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) {
/* 193 */     super.forwardUpdate(paramElementChange, paramDocumentEvent, paramShape, paramViewFactory);
/*     */ 
/*     */     
/* 196 */     if (paramShape != null) {
/* 197 */       Container container = getContainer();
/* 198 */       if (container != null) {
/*     */         
/* 200 */         Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/* 201 */         container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replace(int paramInt1, int paramInt2, View[] paramArrayOfView) {
/* 212 */     super.replace(paramInt1, paramInt2, paramArrayOfView);
/* 213 */     invalidateGrid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void updateGrid() {
/* 222 */     if (!this.gridValid) {
/*     */ 
/*     */       
/* 225 */       this.rows.removeAllElements();
/* 226 */       int i = getViewCount(); int j;
/* 227 */       for (j = 0; j < i; j++) {
/* 228 */         View view = getView(j);
/* 229 */         if (view instanceof TableRow) {
/* 230 */           this.rows.addElement((TableRow)view);
/* 231 */           TableRow tableRow = (TableRow)view;
/* 232 */           tableRow.clearFilledColumns();
/* 233 */           tableRow.setRow(j);
/*     */         } 
/*     */       } 
/*     */       
/* 237 */       j = 0;
/* 238 */       int k = this.rows.size(); byte b;
/* 239 */       for (b = 0; b < k; b++) {
/* 240 */         TableRow tableRow = getRow(b);
/* 241 */         int m = 0;
/* 242 */         for (byte b1 = 0; b1 < tableRow.getViewCount(); b1++, m++) {
/* 243 */           View view = tableRow.getView(b1);
/*     */           
/* 245 */           for (; tableRow.isFilled(m); m++);
/* 246 */           int n = getRowsOccupied(view);
/* 247 */           int i1 = getColumnsOccupied(view);
/* 248 */           if (i1 > 1 || n > 1) {
/*     */             
/* 250 */             int i2 = b + n;
/* 251 */             int i3 = m + i1;
/* 252 */             for (byte b2 = b; b2 < i2; b2++) {
/* 253 */               for (int i4 = m; i4 < i3; i4++) {
/* 254 */                 if (b2 != b || i4 != m) {
/* 255 */                   addFill(b2, i4);
/*     */                 }
/*     */               } 
/*     */             } 
/* 259 */             if (i1 > 1) {
/* 260 */               m += i1 - 1;
/*     */             }
/*     */           } 
/*     */         } 
/* 264 */         j = Math.max(j, m);
/*     */       } 
/*     */ 
/*     */       
/* 268 */       this.columnSpans = new int[j];
/* 269 */       this.columnOffsets = new int[j];
/* 270 */       this.columnRequirements = new SizeRequirements[j];
/* 271 */       for (b = 0; b < j; b++) {
/* 272 */         this.columnRequirements[b] = new SizeRequirements();
/*     */       }
/* 274 */       this.gridValid = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addFill(int paramInt1, int paramInt2) {
/* 282 */     TableRow tableRow = getRow(paramInt1);
/* 283 */     if (tableRow != null) {
/* 284 */       tableRow.fillColumn(paramInt2);
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
/*     */   protected void layoutColumns(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2, SizeRequirements[] paramArrayOfSizeRequirements) {
/* 305 */     SizeRequirements.calculateTiledPositions(paramInt, null, paramArrayOfSizeRequirements, paramArrayOfint1, paramArrayOfint2);
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
/*     */   protected void layoutMinorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/* 333 */     updateGrid();
/*     */ 
/*     */     
/* 336 */     int i = getRowCount();
/* 337 */     for (byte b = 0; b < i; b++) {
/* 338 */       TableRow tableRow = getRow(b);
/* 339 */       tableRow.layoutChanged(paramInt2);
/*     */     } 
/*     */ 
/*     */     
/* 343 */     layoutColumns(paramInt1, this.columnOffsets, this.columnSpans, this.columnRequirements);
/*     */ 
/*     */     
/* 346 */     super.layoutMinorAxis(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2);
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
/*     */   protected SizeRequirements calculateMinorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) {
/* 358 */     updateGrid();
/*     */ 
/*     */     
/* 361 */     calculateColumnRequirements(paramInt);
/*     */ 
/*     */ 
/*     */     
/* 365 */     if (paramSizeRequirements == null) {
/* 366 */       paramSizeRequirements = new SizeRequirements();
/*     */     }
/* 368 */     long l1 = 0L;
/* 369 */     long l2 = 0L;
/* 370 */     long l3 = 0L;
/* 371 */     for (SizeRequirements sizeRequirements : this.columnRequirements) {
/* 372 */       l1 += sizeRequirements.minimum;
/* 373 */       l2 += sizeRequirements.preferred;
/* 374 */       l3 += sizeRequirements.maximum;
/*     */     } 
/* 376 */     paramSizeRequirements.minimum = (int)l1;
/* 377 */     paramSizeRequirements.preferred = (int)l2;
/* 378 */     paramSizeRequirements.maximum = (int)l3;
/* 379 */     paramSizeRequirements.alignment = 0.0F;
/* 380 */     return paramSizeRequirements;
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
/*     */   void calculateColumnRequirements(int paramInt) {
/* 410 */     boolean bool = false;
/* 411 */     int i = getRowCount(); byte b;
/* 412 */     for (b = 0; b < i; b++) {
/* 413 */       TableRow tableRow = getRow(b);
/* 414 */       int j = 0;
/* 415 */       int k = tableRow.getViewCount();
/* 416 */       for (byte b1 = 0; b1 < k; b1++, j++) {
/* 417 */         View view = tableRow.getView(b1);
/* 418 */         for (; tableRow.isFilled(j); j++);
/* 419 */         int m = getRowsOccupied(view);
/* 420 */         int n = getColumnsOccupied(view);
/* 421 */         if (n == 1) {
/* 422 */           checkSingleColumnCell(paramInt, j, view);
/*     */         } else {
/* 424 */           bool = true;
/* 425 */           j += n - 1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 431 */     if (bool) {
/* 432 */       for (b = 0; b < i; b++) {
/* 433 */         TableRow tableRow = getRow(b);
/* 434 */         int j = 0;
/* 435 */         int k = tableRow.getViewCount();
/* 436 */         for (byte b1 = 0; b1 < k; b1++, j++) {
/* 437 */           View view = tableRow.getView(b1);
/* 438 */           for (; tableRow.isFilled(j); j++);
/* 439 */           int m = getColumnsOccupied(view);
/* 440 */           if (m > 1) {
/* 441 */             checkMultiColumnCell(paramInt, j, m, view);
/* 442 */             j += m - 1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkSingleColumnCell(int paramInt1, int paramInt2, View paramView) {
/* 462 */     SizeRequirements sizeRequirements = this.columnRequirements[paramInt2];
/* 463 */     sizeRequirements.minimum = Math.max((int)paramView.getMinimumSpan(paramInt1), sizeRequirements.minimum);
/* 464 */     sizeRequirements.preferred = Math.max((int)paramView.getPreferredSpan(paramInt1), sizeRequirements.preferred);
/* 465 */     sizeRequirements.maximum = Math.max((int)paramView.getMaximumSpan(paramInt1), sizeRequirements.maximum);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkMultiColumnCell(int paramInt1, int paramInt2, int paramInt3, View paramView) {
/* 474 */     long l1 = 0L;
/* 475 */     long l2 = 0L;
/* 476 */     long l3 = 0L; int i;
/* 477 */     for (i = 0; i < paramInt3; i++) {
/* 478 */       SizeRequirements sizeRequirements = this.columnRequirements[paramInt2 + i];
/* 479 */       l1 += sizeRequirements.minimum;
/* 480 */       l2 += sizeRequirements.preferred;
/* 481 */       l3 += sizeRequirements.maximum;
/*     */     } 
/*     */ 
/*     */     
/* 485 */     i = (int)paramView.getMinimumSpan(paramInt1);
/* 486 */     if (i > l1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 493 */       SizeRequirements[] arrayOfSizeRequirements = new SizeRequirements[paramInt3];
/* 494 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 495 */         SizeRequirements sizeRequirements = arrayOfSizeRequirements[b1] = this.columnRequirements[paramInt2 + b1];
/* 496 */         sizeRequirements.maximum = Math.max(sizeRequirements.maximum, (int)paramView.getMaximumSpan(paramInt1));
/*     */       } 
/* 498 */       int[] arrayOfInt1 = new int[paramInt3];
/* 499 */       int[] arrayOfInt2 = new int[paramInt3];
/* 500 */       SizeRequirements.calculateTiledPositions(i, null, arrayOfSizeRequirements, arrayOfInt2, arrayOfInt1);
/*     */ 
/*     */       
/* 503 */       for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 504 */         SizeRequirements sizeRequirements = arrayOfSizeRequirements[b2];
/* 505 */         sizeRequirements.minimum = Math.max(arrayOfInt1[b2], sizeRequirements.minimum);
/* 506 */         sizeRequirements.preferred = Math.max(sizeRequirements.minimum, sizeRequirements.preferred);
/* 507 */         sizeRequirements.maximum = Math.max(sizeRequirements.preferred, sizeRequirements.maximum);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 512 */     int j = (int)paramView.getPreferredSpan(paramInt1);
/* 513 */     if (j > l2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 520 */       SizeRequirements[] arrayOfSizeRequirements = new SizeRequirements[paramInt3];
/* 521 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 522 */         SizeRequirements sizeRequirements = arrayOfSizeRequirements[b1] = this.columnRequirements[paramInt2 + b1];
/*     */       }
/* 524 */       int[] arrayOfInt1 = new int[paramInt3];
/* 525 */       int[] arrayOfInt2 = new int[paramInt3];
/* 526 */       SizeRequirements.calculateTiledPositions(j, null, arrayOfSizeRequirements, arrayOfInt2, arrayOfInt1);
/*     */ 
/*     */       
/* 529 */       for (byte b2 = 0; b2 < paramInt3; b2++) {
/* 530 */         SizeRequirements sizeRequirements = arrayOfSizeRequirements[b2];
/* 531 */         sizeRequirements.preferred = Math.max(arrayOfInt1[b2], sizeRequirements.preferred);
/* 532 */         sizeRequirements.maximum = Math.max(sizeRequirements.preferred, sizeRequirements.maximum);
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
/*     */ 
/*     */   
/*     */   protected View getViewAtPosition(int paramInt, Rectangle paramRectangle) {
/* 552 */     int i = getViewCount();
/* 553 */     for (byte b = 0; b < i; b++) {
/* 554 */       View view = getView(b);
/* 555 */       int j = view.getStartOffset();
/* 556 */       int k = view.getEndOffset();
/* 557 */       if (paramInt >= j && paramInt < k) {
/*     */         
/* 559 */         if (paramRectangle != null) {
/* 560 */           childAllocation(b, paramRectangle);
/*     */         }
/* 562 */         return view;
/*     */       } 
/*     */     } 
/* 565 */     if (paramInt == getEndOffset()) {
/* 566 */       View view = getView(i - 1);
/* 567 */       if (paramRectangle != null) {
/* 568 */         childAllocation(i - 1, paramRectangle);
/*     */       }
/* 570 */       return view;
/*     */     } 
/* 572 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 582 */   private static final BitSet EMPTY = new BitSet();
/*     */ 
/*     */ 
/*     */   
/*     */   public class TableRow
/*     */     extends BoxView
/*     */   {
/*     */     BitSet fillColumns;
/*     */ 
/*     */     
/*     */     int row;
/*     */ 
/*     */     
/*     */     public TableRow(Element param1Element) {
/* 596 */       super(param1Element, 0);
/* 597 */       this.fillColumns = new BitSet();
/*     */     }
/*     */     
/*     */     void clearFilledColumns() {
/* 601 */       this.fillColumns.and(TableView.EMPTY);
/*     */     }
/*     */     
/*     */     void fillColumn(int param1Int) {
/* 605 */       this.fillColumns.set(param1Int);
/*     */     }
/*     */     
/*     */     boolean isFilled(int param1Int) {
/* 609 */       return this.fillColumns.get(param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     int getRow() {
/* 614 */       return this.row;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setRow(int param1Int) {
/* 622 */       this.row = param1Int;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getColumnCount() {
/* 629 */       byte b1 = 0;
/* 630 */       int i = this.fillColumns.size();
/* 631 */       for (byte b2 = 0; b2 < i; b2++) {
/* 632 */         if (this.fillColumns.get(b2)) {
/* 633 */           b1++;
/*     */         }
/*     */       } 
/* 636 */       return getViewCount() + b1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void replace(int param1Int1, int param1Int2, View[] param1ArrayOfView) {
/* 645 */       super.replace(param1Int1, param1Int2, param1ArrayOfView);
/* 646 */       TableView.this.invalidateGrid();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void layoutMajorAxis(int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
/* 669 */       int i = 0;
/* 670 */       int j = getViewCount();
/* 671 */       for (byte b = 0; b < j; b++, i++) {
/* 672 */         View view = getView(b);
/* 673 */         for (; isFilled(i); i++);
/* 674 */         int k = TableView.this.getColumnsOccupied(view);
/* 675 */         param1ArrayOfint2[b] = TableView.this.columnSpans[i];
/* 676 */         param1ArrayOfint1[b] = TableView.this.columnOffsets[i];
/* 677 */         if (k > 1) {
/* 678 */           int m = TableView.this.columnSpans.length;
/* 679 */           for (byte b1 = 1; b1 < k; b1++) {
/*     */ 
/*     */ 
/*     */             
/* 683 */             if (i + b1 < m) {
/* 684 */               param1ArrayOfint2[b] = param1ArrayOfint2[b] + TableView.this.columnSpans[i + b1];
/*     */             }
/*     */           } 
/* 687 */           i += k - 1;
/*     */         } 
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void layoutMinorAxis(int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
/* 713 */       super.layoutMinorAxis(param1Int1, param1Int2, param1ArrayOfint1, param1ArrayOfint2);
/* 714 */       int i = 0;
/* 715 */       int j = getViewCount();
/* 716 */       for (byte b = 0; b < j; b++, i++) {
/* 717 */         View view = getView(b);
/* 718 */         for (; isFilled(i); i++);
/* 719 */         int k = TableView.this.getColumnsOccupied(view);
/* 720 */         int m = TableView.this.getRowsOccupied(view);
/* 721 */         if (m > 1) {
/* 722 */           for (byte b1 = 1; b1 < m; b1++) {
/*     */ 
/*     */ 
/*     */             
/* 726 */             int n = getRow() + b1;
/* 727 */             if (n < TableView.this.getViewCount()) {
/* 728 */               int i1 = TableView.this.getSpan(1, getRow() + b1);
/* 729 */               param1ArrayOfint2[b] = param1ArrayOfint2[b] + i1;
/*     */             } 
/*     */           } 
/*     */         }
/* 733 */         if (k > 1) {
/* 734 */           i += k - 1;
/*     */         }
/*     */       } 
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
/*     */     public int getResizeWeight(int param1Int) {
/* 748 */       return 1;
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
/*     */     
/*     */     protected View getViewAtPosition(int param1Int, Rectangle param1Rectangle) {
/* 765 */       int i = getViewCount();
/* 766 */       for (byte b = 0; b < i; b++) {
/* 767 */         View view = getView(b);
/* 768 */         int j = view.getStartOffset();
/* 769 */         int k = view.getEndOffset();
/* 770 */         if (param1Int >= j && param1Int < k) {
/*     */           
/* 772 */           if (param1Rectangle != null) {
/* 773 */             childAllocation(b, param1Rectangle);
/*     */           }
/* 775 */           return view;
/*     */         } 
/*     */       } 
/* 778 */       if (param1Int == getEndOffset()) {
/* 779 */         View view = getView(i - 1);
/* 780 */         if (param1Rectangle != null) {
/* 781 */           childAllocation(i - 1, param1Rectangle);
/*     */         }
/* 783 */         return view;
/*     */       } 
/* 785 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public class TableCell
/*     */     extends BoxView
/*     */     implements GridCell
/*     */   {
/*     */     int row;
/*     */ 
/*     */ 
/*     */     
/*     */     int col;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TableCell(Element param1Element) {
/* 807 */       super(param1Element, 1);
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
/*     */     public int getColumnCount() {
/* 819 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRowCount() {
/* 829 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setGridLocation(int param1Int1, int param1Int2) {
/* 840 */       this.row = param1Int1;
/* 841 */       this.col = param1Int2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getGridRow() {
/* 848 */       return this.row;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getGridColumn() {
/* 855 */       return this.col;
/*     */     }
/*     */   }
/*     */   
/*     */   static interface GridCell {
/*     */     void setGridLocation(int param1Int1, int param1Int2);
/*     */     
/*     */     int getGridRow();
/*     */     
/*     */     int getGridColumn();
/*     */     
/*     */     int getColumnCount();
/*     */     
/*     */     int getRowCount();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/TableView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */