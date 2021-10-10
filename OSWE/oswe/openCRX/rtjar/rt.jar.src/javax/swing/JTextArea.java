/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import javax.swing.text.AbstractDocument;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import javax.swing.text.JTextComponent;
/*     */ import javax.swing.text.PlainDocument;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JTextArea
/*     */   extends JTextComponent
/*     */ {
/*     */   private static final String uiClassID = "TextAreaUI";
/*     */   private int rows;
/*     */   private int columns;
/*     */   private int columnWidth;
/*     */   private int rowHeight;
/*     */   private boolean wrap;
/*     */   private boolean word;
/*     */   
/*     */   public JTextArea() {
/* 140 */     this((Document)null, (String)null, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JTextArea(String paramString) {
/* 150 */     this((Document)null, paramString, 0, 0);
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
/*     */   public JTextArea(int paramInt1, int paramInt2) {
/* 164 */     this((Document)null, (String)null, paramInt1, paramInt2);
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
/*     */   public JTextArea(String paramString, int paramInt1, int paramInt2) {
/* 178 */     this((Document)null, paramString, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JTextArea(Document paramDocument) {
/* 188 */     this(paramDocument, (String)null, 0, 0);
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
/*     */   public JTextArea(Document paramDocument, String paramString, int paramInt1, int paramInt2) {
/* 205 */     this.rows = paramInt1;
/* 206 */     this.columns = paramInt2;
/* 207 */     if (paramDocument == null) {
/* 208 */       paramDocument = createDefaultModel();
/*     */     }
/* 210 */     setDocument(paramDocument);
/* 211 */     if (paramString != null) {
/* 212 */       setText(paramString);
/* 213 */       select(0, 0);
/*     */     } 
/* 215 */     if (paramInt1 < 0) {
/* 216 */       throw new IllegalArgumentException("rows: " + paramInt1);
/*     */     }
/* 218 */     if (paramInt2 < 0) {
/* 219 */       throw new IllegalArgumentException("columns: " + paramInt2);
/*     */     }
/* 221 */     LookAndFeel.installProperty(this, "focusTraversalKeysForward", 
/*     */ 
/*     */         
/* 224 */         JComponent.getManagingFocusForwardTraversalKeys());
/* 225 */     LookAndFeel.installProperty(this, "focusTraversalKeysBackward", 
/*     */ 
/*     */         
/* 228 */         JComponent.getManagingFocusBackwardTraversalKeys());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUIClassID() {
/* 239 */     return "TextAreaUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Document createDefaultModel() {
/* 250 */     return new PlainDocument();
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
/*     */   public void setTabSize(int paramInt) {
/* 267 */     Document document = getDocument();
/* 268 */     if (document != null) {
/* 269 */       int i = getTabSize();
/* 270 */       document.putProperty("tabSize", Integer.valueOf(paramInt));
/* 271 */       firePropertyChange("tabSize", i, paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTabSize() {
/* 282 */     int i = 8;
/* 283 */     Document document = getDocument();
/* 284 */     if (document != null) {
/* 285 */       Integer integer = (Integer)document.getProperty("tabSize");
/* 286 */       if (integer != null) {
/* 287 */         i = integer.intValue();
/*     */       }
/*     */     } 
/* 290 */     return i;
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
/*     */   public void setLineWrap(boolean paramBoolean) {
/* 309 */     boolean bool = this.wrap;
/* 310 */     this.wrap = paramBoolean;
/* 311 */     firePropertyChange("lineWrap", bool, paramBoolean);
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
/*     */   public boolean getLineWrap() {
/* 323 */     return this.wrap;
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
/*     */   public void setWrapStyleWord(boolean paramBoolean) {
/* 343 */     boolean bool = this.word;
/* 344 */     this.word = paramBoolean;
/* 345 */     firePropertyChange("wrapStyleWord", bool, paramBoolean);
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
/*     */   public boolean getWrapStyleWord() {
/* 360 */     return this.word;
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
/*     */   public int getLineOfOffset(int paramInt) throws BadLocationException {
/* 373 */     Document document = getDocument();
/* 374 */     if (paramInt < 0)
/* 375 */       throw new BadLocationException("Can't translate offset to line", -1); 
/* 376 */     if (paramInt > document.getLength()) {
/* 377 */       throw new BadLocationException("Can't translate offset to line", document.getLength() + 1);
/*     */     }
/* 379 */     Element element = getDocument().getDefaultRootElement();
/* 380 */     return element.getElementIndex(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineCount() {
/* 390 */     Element element = getDocument().getDefaultRootElement();
/* 391 */     return element.getElementCount();
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
/*     */   public int getLineStartOffset(int paramInt) throws BadLocationException {
/* 405 */     int i = getLineCount();
/* 406 */     if (paramInt < 0)
/* 407 */       throw new BadLocationException("Negative line", -1); 
/* 408 */     if (paramInt >= i) {
/* 409 */       throw new BadLocationException("No such line", getDocument().getLength() + 1);
/*     */     }
/* 411 */     Element element1 = getDocument().getDefaultRootElement();
/* 412 */     Element element2 = element1.getElement(paramInt);
/* 413 */     return element2.getStartOffset();
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
/*     */   public int getLineEndOffset(int paramInt) throws BadLocationException {
/* 428 */     int i = getLineCount();
/* 429 */     if (paramInt < 0)
/* 430 */       throw new BadLocationException("Negative line", -1); 
/* 431 */     if (paramInt >= i) {
/* 432 */       throw new BadLocationException("No such line", getDocument().getLength() + 1);
/*     */     }
/* 434 */     Element element1 = getDocument().getDefaultRootElement();
/* 435 */     Element element2 = element1.getElement(paramInt);
/* 436 */     int j = element2.getEndOffset();
/*     */     
/* 438 */     return (paramInt == i - 1) ? (j - 1) : j;
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
/*     */   public void insert(String paramString, int paramInt) {
/* 456 */     Document document = getDocument();
/* 457 */     if (document != null) {
/*     */       try {
/* 459 */         document.insertString(paramInt, paramString, null);
/* 460 */       } catch (BadLocationException badLocationException) {
/* 461 */         throw new IllegalArgumentException(badLocationException.getMessage());
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
/*     */   public void append(String paramString) {
/* 474 */     Document document = getDocument();
/* 475 */     if (document != null) {
/*     */       try {
/* 477 */         document.insertString(document.getLength(), paramString, null);
/* 478 */       } catch (BadLocationException badLocationException) {}
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
/*     */   public void replaceRange(String paramString, int paramInt1, int paramInt2) {
/* 496 */     if (paramInt2 < paramInt1) {
/* 497 */       throw new IllegalArgumentException("end before start");
/*     */     }
/* 499 */     Document document = getDocument();
/* 500 */     if (document != null) {
/*     */       try {
/* 502 */         if (document instanceof AbstractDocument) {
/* 503 */           ((AbstractDocument)document).replace(paramInt1, paramInt2 - paramInt1, paramString, null);
/*     */         }
/*     */         else {
/*     */           
/* 507 */           document.remove(paramInt1, paramInt2 - paramInt1);
/* 508 */           document.insertString(paramInt1, paramString, null);
/*     */         } 
/* 510 */       } catch (BadLocationException badLocationException) {
/* 511 */         throw new IllegalArgumentException(badLocationException.getMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRows() {
/* 522 */     return this.rows;
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
/*     */   public void setRows(int paramInt) {
/* 536 */     int i = this.rows;
/* 537 */     if (paramInt < 0) {
/* 538 */       throw new IllegalArgumentException("rows less than zero.");
/*     */     }
/* 540 */     if (paramInt != i) {
/* 541 */       this.rows = paramInt;
/* 542 */       invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getRowHeight() {
/* 553 */     if (this.rowHeight == 0) {
/* 554 */       FontMetrics fontMetrics = getFontMetrics(getFont());
/* 555 */       this.rowHeight = fontMetrics.getHeight();
/*     */     } 
/* 557 */     return this.rowHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumns() {
/* 566 */     return this.columns;
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
/*     */   public void setColumns(int paramInt) {
/* 580 */     int i = this.columns;
/* 581 */     if (paramInt < 0) {
/* 582 */       throw new IllegalArgumentException("columns less than zero.");
/*     */     }
/* 584 */     if (paramInt != i) {
/* 585 */       this.columns = paramInt;
/* 586 */       invalidate();
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
/*     */   protected int getColumnWidth() {
/* 601 */     if (this.columnWidth == 0) {
/* 602 */       FontMetrics fontMetrics = getFontMetrics(getFont());
/* 603 */       this.columnWidth = fontMetrics.charWidth('m');
/*     */     } 
/* 605 */     return this.columnWidth;
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
/*     */   public Dimension getPreferredSize() {
/* 618 */     Dimension dimension = super.getPreferredSize();
/* 619 */     dimension = (dimension == null) ? new Dimension(400, 400) : dimension;
/* 620 */     Insets insets = getInsets();
/*     */     
/* 622 */     if (this.columns != 0) {
/* 623 */       dimension.width = Math.max(dimension.width, this.columns * getColumnWidth() + insets.left + insets.right);
/*     */     }
/*     */     
/* 626 */     if (this.rows != 0) {
/* 627 */       dimension.height = Math.max(dimension.height, this.rows * getRowHeight() + insets.top + insets.bottom);
/*     */     }
/*     */     
/* 630 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font paramFont) {
/* 640 */     super.setFont(paramFont);
/* 641 */     this.rowHeight = 0;
/* 642 */     this.columnWidth = 0;
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
/*     */   protected String paramString() {
/* 656 */     String str1 = this.wrap ? "true" : "false";
/*     */     
/* 658 */     String str2 = this.word ? "true" : "false";
/*     */ 
/*     */     
/* 661 */     return super.paramString() + ",colums=" + this.columns + ",columWidth=" + this.columnWidth + ",rows=" + this.rows + ",rowHeight=" + this.rowHeight + ",word=" + str2 + ",wrap=" + str1;
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
/*     */   public boolean getScrollableTracksViewportWidth() {
/* 682 */     return this.wrap ? true : super.getScrollableTracksViewportWidth();
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
/*     */   public Dimension getPreferredScrollableViewportSize() {
/* 695 */     Dimension dimension = super.getPreferredScrollableViewportSize();
/* 696 */     dimension = (dimension == null) ? new Dimension(400, 400) : dimension;
/* 697 */     Insets insets = getInsets();
/*     */     
/* 699 */     dimension
/* 700 */       .width = (this.columns == 0) ? dimension.width : (this.columns * getColumnWidth() + insets.left + insets.right);
/* 701 */     dimension
/* 702 */       .height = (this.rows == 0) ? dimension.height : (this.rows * getRowHeight() + insets.top + insets.bottom);
/* 703 */     return dimension;
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
/*     */   public int getScrollableUnitIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 728 */     switch (paramInt1) {
/*     */       case 1:
/* 730 */         return getRowHeight();
/*     */       case 0:
/* 732 */         return getColumnWidth();
/*     */     } 
/* 734 */     throw new IllegalArgumentException("Invalid orientation: " + paramInt1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 743 */     paramObjectOutputStream.defaultWriteObject();
/* 744 */     if (getUIClassID().equals("TextAreaUI")) {
/* 745 */       byte b = JComponent.getWriteObjCounter(this);
/* 746 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 747 */       if (b == 0 && this.ui != null) {
/* 748 */         this.ui.installUI(this);
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 768 */     if (this.accessibleContext == null) {
/* 769 */       this.accessibleContext = new AccessibleJTextArea();
/*     */     }
/* 771 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJTextArea
/*     */     extends JTextComponent.AccessibleJTextComponent
/*     */   {
/*     */     public AccessibleStateSet getAccessibleStateSet() {
/* 799 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 800 */       accessibleStateSet.add(AccessibleState.MULTI_LINE);
/* 801 */       return accessibleStateSet;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JTextArea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */