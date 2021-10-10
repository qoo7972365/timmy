/*      */ package javax.swing.text.html;
/*      */ 
/*      */ import java.awt.Container;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.Vector;
/*      */ import javax.swing.SizeRequirements;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.text.AttributeSet;
/*      */ import javax.swing.text.BoxView;
/*      */ import javax.swing.text.Element;
/*      */ import javax.swing.text.JTextComponent;
/*      */ import javax.swing.text.StyleConstants;
/*      */ import javax.swing.text.View;
/*      */ import javax.swing.text.ViewFactory;
/*      */ 
/*      */ 
/*      */ class TableView
/*      */   extends BoxView
/*      */   implements ViewFactory
/*      */ {
/*      */   private AttributeSet attr;
/*      */   private StyleSheet.BoxPainter painter;
/*      */   private int cellSpacing;
/*      */   private int borderWidth;
/*      */   private int captionIndex;
/*      */   private boolean relativeCells;
/*      */   private boolean multiRowCells;
/*      */   int[] columnSpans;
/*      */   int[] columnOffsets;
/*      */   SizeRequirements totalColumnRequirements;
/*      */   SizeRequirements[] columnRequirements;
/*      */   RowIterator rowIterator;
/*      */   ColumnIterator colIterator;
/*      */   Vector<RowView> rows;
/*      */   boolean skipComments;
/*      */   boolean gridValid;
/*      */   
/*      */   protected RowView createTableRow(Element paramElement) {
/*      */     Object object = paramElement.getAttributes().getAttribute(StyleConstants.NameAttribute);
/*      */     if (object == HTML.Tag.TR)
/*      */       return new RowView(paramElement); 
/*      */     return null;
/*      */   }
/*      */   
/*      */   public TableView(Element paramElement) {
/*   50 */     super(paramElement, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  990 */     this.rowIterator = new RowIterator();
/*  991 */     this.colIterator = new ColumnIterator();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  996 */     this.skipComments = false; this.rows = new Vector<>(); this.gridValid = false; this.captionIndex = -1; this.totalColumnRequirements = new SizeRequirements();
/*      */   }
/*      */   public int getColumnCount() { return this.columnSpans.length; }
/*  999 */   public int getColumnSpan(int paramInt) { if (paramInt < this.columnSpans.length) return this.columnSpans[paramInt];  return 0; } public int getRowCount() { return this.rows.size(); } public int getMultiRowSpan(int paramInt1, int paramInt2) { RowView rowView1 = getRow(paramInt1); RowView rowView2 = getRow(paramInt2); if (rowView1 != null && rowView2 != null) { int i = rowView1.viewIndex; int j = rowView2.viewIndex; return getOffset(1, j) - getOffset(1, i) + getSpan(1, j); }  return 0; } public int getRowSpan(int paramInt) { RowView rowView = getRow(paramInt); if (rowView != null) return getSpan(1, rowView.viewIndex);  return 0; } RowView getRow(int paramInt) { if (paramInt < this.rows.size()) return this.rows.elementAt(paramInt);  return null; } protected View getViewAtPoint(int paramInt1, int paramInt2, Rectangle paramRectangle) { int i = getViewCount(); Rectangle rectangle = new Rectangle(); for (byte b = 0; b < i; b++) { rectangle.setBounds(paramRectangle); childAllocation(b, rectangle); View view = getView(b); if (view instanceof RowView) { view = ((RowView)view).findViewAtPoint(paramInt1, paramInt2, rectangle); if (view != null) { paramRectangle.setBounds(rectangle); return view; }  }  }  return super.getViewAtPoint(paramInt1, paramInt2, paramRectangle); } protected int getColumnsOccupied(View paramView) { AttributeSet attributeSet = paramView.getElement().getAttributes(); if (attributeSet.isDefined(HTML.Attribute.COLSPAN)) { String str = (String)attributeSet.getAttribute(HTML.Attribute.COLSPAN); if (str != null) try { return Integer.parseInt(str); } catch (NumberFormatException numberFormatException) {}  }  return 1; } protected int getRowsOccupied(View paramView) { AttributeSet attributeSet = paramView.getElement().getAttributes(); if (attributeSet.isDefined(HTML.Attribute.ROWSPAN)) { String str = (String)attributeSet.getAttribute(HTML.Attribute.ROWSPAN); if (str != null) try { return Integer.parseInt(str); } catch (NumberFormatException numberFormatException) {}  }  return 1; } protected void invalidateGrid() { this.gridValid = false; } protected StyleSheet getStyleSheet() { HTMLDocument hTMLDocument = (HTMLDocument)getDocument(); return hTMLDocument.getStyleSheet(); } void updateInsets() { short s1 = (short)(int)this.painter.getInset(1, this); short s2 = (short)(int)this.painter.getInset(3, this); if (this.captionIndex != -1) { View view = getView(this.captionIndex); short s = (short)(int)view.getPreferredSpan(1); AttributeSet attributeSet = view.getAttributes(); Object object = attributeSet.getAttribute(CSS.Attribute.CAPTION_SIDE); if (object != null && object.equals("bottom")) { s2 = (short)(s2 + s); } else { s1 = (short)(s1 + s); }  }  setInsets(s1, (short)(int)this.painter.getInset(2, this), s2, (short)(int)this.painter.getInset(4, this)); } protected void setPropertiesFromAttributes() { StyleSheet styleSheet = getStyleSheet(); this.attr = styleSheet.getViewAttributes(this); this.painter = styleSheet.getBoxPainter(this.attr); if (this.attr != null) { setInsets((short)(int)this.painter.getInset(1, this), (short)(int)this.painter.getInset(2, this), (short)(int)this.painter.getInset(3, this), (short)(int)this.painter.getInset(4, this)); CSS.LengthValue lengthValue = (CSS.LengthValue)this.attr.getAttribute(CSS.Attribute.BORDER_SPACING); if (lengthValue != null) { this.cellSpacing = (int)lengthValue.getValue(); } else { this.cellSpacing = 2; }  lengthValue = (CSS.LengthValue)this.attr.getAttribute(CSS.Attribute.BORDER_TOP_WIDTH); if (lengthValue != null) { this.borderWidth = (int)lengthValue.getValue(); } else { this.borderWidth = 0; }  }  } void updateGrid() { if (!this.gridValid) { this.relativeCells = false; this.multiRowCells = false; this.captionIndex = -1; this.rows.removeAllElements(); int i = getViewCount(); int j; for (j = 0; j < i; j++) { View view = getView(j); if (view instanceof RowView) { this.rows.addElement((RowView)view); RowView rowView = (RowView)view; rowView.clearFilledColumns(); rowView.rowIndex = this.rows.size() - 1; rowView.viewIndex = j; } else { Object object = view.getElement().getAttributes().getAttribute(StyleConstants.NameAttribute); if (object instanceof HTML.Tag) { HTML.Tag tag = (HTML.Tag)object; if (tag == HTML.Tag.CAPTION) this.captionIndex = j;  }  }  }  j = 0; int k = this.rows.size(); byte b; for (b = 0; b < k; b++) { RowView rowView = getRow(b); int m = 0; for (byte b1 = 0; b1 < rowView.getViewCount(); b1++, m++) { View view = rowView.getView(b1); if (!this.relativeCells) { AttributeSet attributeSet = view.getAttributes(); CSS.LengthValue lengthValue = (CSS.LengthValue)attributeSet.getAttribute(CSS.Attribute.WIDTH); if (lengthValue != null && lengthValue.isPercentage()) this.relativeCells = true;  }  for (; rowView.isFilled(m); m++); int n = getRowsOccupied(view); if (n > 1) this.multiRowCells = true;  int i1 = getColumnsOccupied(view); if (i1 > 1 || n > 1) { int i2 = b + n; int i3 = m + i1; for (byte b2 = b; b2 < i2; b2++) { for (int i4 = m; i4 < i3; i4++) { if (b2 != b || i4 != m) addFill(b2, i4);  }  }  if (i1 > 1) m += i1 - 1;  }  }  j = Math.max(j, m); }  this.columnSpans = new int[j]; this.columnOffsets = new int[j]; this.columnRequirements = new SizeRequirements[j]; for (b = 0; b < j; b++) { this.columnRequirements[b] = new SizeRequirements(); (this.columnRequirements[b]).maximum = Integer.MAX_VALUE; }  this.gridValid = true; }  } void addFill(int paramInt1, int paramInt2) { RowView rowView = getRow(paramInt1); if (rowView != null) rowView.fillColumn(paramInt2);  } protected void layoutColumns(int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2, SizeRequirements[] paramArrayOfSizeRequirements) { Arrays.fill(paramArrayOfint1, 0); Arrays.fill(paramArrayOfint2, 0); this.colIterator.setLayoutArrays(paramArrayOfint1, paramArrayOfint2, paramInt); CSS.calculateTiledLayout(this.colIterator, paramInt); } void calculateColumnRequirements(int paramInt) { for (SizeRequirements sizeRequirements : this.columnRequirements) { sizeRequirements.minimum = 0; sizeRequirements.preferred = 0; sizeRequirements.maximum = Integer.MAX_VALUE; }  Container container = getContainer(); if (container != null) if (container instanceof JTextComponent) { this.skipComments = !((JTextComponent)container).isEditable(); } else { this.skipComments = true; }   boolean bool = false; int i = getRowCount(); byte b; for (b = 0; b < i; b++) { RowView rowView = getRow(b); int j = 0; int k = rowView.getViewCount(); for (byte b1 = 0; b1 < k; b1++) { View view = rowView.getView(b1); if (!this.skipComments || view instanceof CellView) { for (; rowView.isFilled(j); j++); int m = getRowsOccupied(view); int n = getColumnsOccupied(view); if (n == 1) { checkSingleColumnCell(paramInt, j, view); } else { bool = true; j += n - 1; }  j++; }  }  }  if (bool) for (b = 0; b < i; b++) { RowView rowView = getRow(b); int j = 0; int k = rowView.getViewCount(); for (byte b1 = 0; b1 < k; b1++) { View view = rowView.getView(b1); if (!this.skipComments || view instanceof CellView) { for (; rowView.isFilled(j); j++); int m = getColumnsOccupied(view); if (m > 1) { checkMultiColumnCell(paramInt, j, m, view); j += m - 1; }  j++; }  }  }   } void checkSingleColumnCell(int paramInt1, int paramInt2, View paramView) { SizeRequirements sizeRequirements = this.columnRequirements[paramInt2]; sizeRequirements.minimum = Math.max((int)paramView.getMinimumSpan(paramInt1), sizeRequirements.minimum); sizeRequirements.preferred = Math.max((int)paramView.getPreferredSpan(paramInt1), sizeRequirements.preferred); } void checkMultiColumnCell(int paramInt1, int paramInt2, int paramInt3, View paramView) { long l1 = 0L; long l2 = 0L; long l3 = 0L; int i; for (i = 0; i < paramInt3; i++) { SizeRequirements sizeRequirements = this.columnRequirements[paramInt2 + i]; l1 += sizeRequirements.minimum; l2 += sizeRequirements.preferred; l3 += sizeRequirements.maximum; }  i = (int)paramView.getMinimumSpan(paramInt1); if (i > l1) { SizeRequirements[] arrayOfSizeRequirements = new SizeRequirements[paramInt3]; for (byte b1 = 0; b1 < paramInt3; b1++) arrayOfSizeRequirements[b1] = this.columnRequirements[paramInt2 + b1];  int[] arrayOfInt1 = new int[paramInt3]; int[] arrayOfInt2 = new int[paramInt3]; SizeRequirements.calculateTiledPositions(i, null, arrayOfSizeRequirements, arrayOfInt2, arrayOfInt1); for (byte b2 = 0; b2 < paramInt3; b2++) { SizeRequirements sizeRequirements = arrayOfSizeRequirements[b2]; sizeRequirements.minimum = Math.max(arrayOfInt1[b2], sizeRequirements.minimum); sizeRequirements.preferred = Math.max(sizeRequirements.minimum, sizeRequirements.preferred); sizeRequirements.maximum = Math.max(sizeRequirements.preferred, sizeRequirements.maximum); }  }  int j = (int)paramView.getPreferredSpan(paramInt1); if (j > l2) { SizeRequirements[] arrayOfSizeRequirements = new SizeRequirements[paramInt3]; for (byte b1 = 0; b1 < paramInt3; b1++) arrayOfSizeRequirements[b1] = this.columnRequirements[paramInt2 + b1];  int[] arrayOfInt1 = new int[paramInt3]; int[] arrayOfInt2 = new int[paramInt3]; SizeRequirements.calculateTiledPositions(j, null, arrayOfSizeRequirements, arrayOfInt2, arrayOfInt1); for (byte b2 = 0; b2 < paramInt3; b2++) { SizeRequirements sizeRequirements = arrayOfSizeRequirements[b2]; sizeRequirements.preferred = Math.max(arrayOfInt1[b2], sizeRequirements.preferred); sizeRequirements.maximum = Math.max(sizeRequirements.preferred, sizeRequirements.maximum); }  }  } protected SizeRequirements calculateMinorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) { updateGrid(); calculateColumnRequirements(paramInt); if (paramSizeRequirements == null) paramSizeRequirements = new SizeRequirements();  long l1 = 0L; long l2 = 0L; int i = this.columnRequirements.length; int j; for (j = 0; j < i; j++) { SizeRequirements sizeRequirements = this.columnRequirements[j]; l1 += sizeRequirements.minimum; l2 += sizeRequirements.preferred; }  j = (i + 1) * this.cellSpacing + 2 * this.borderWidth; l1 += j; l2 += j; paramSizeRequirements.minimum = (int)l1; paramSizeRequirements.preferred = (int)l2; paramSizeRequirements.maximum = (int)l2; AttributeSet attributeSet = getAttributes(); CSS.LengthValue lengthValue = (CSS.LengthValue)attributeSet.getAttribute(CSS.Attribute.WIDTH); if (BlockView.spanSetFromAttributes(paramInt, paramSizeRequirements, lengthValue, (CSS.LengthValue)null) && paramSizeRequirements.minimum < (int)l1) paramSizeRequirements.maximum = paramSizeRequirements.minimum = paramSizeRequirements.preferred = (int)l1;  this.totalColumnRequirements.minimum = paramSizeRequirements.minimum; this.totalColumnRequirements.preferred = paramSizeRequirements.preferred; this.totalColumnRequirements.maximum = paramSizeRequirements.maximum; Object object = attributeSet.getAttribute(CSS.Attribute.TEXT_ALIGN); if (object != null) { String str = object.toString(); if (str.equals("left")) { paramSizeRequirements.alignment = 0.0F; } else if (str.equals("center")) { paramSizeRequirements.alignment = 0.5F; } else if (str.equals("right")) { paramSizeRequirements.alignment = 1.0F; } else { paramSizeRequirements.alignment = 0.0F; }  } else { paramSizeRequirements.alignment = 0.0F; }  return paramSizeRequirements; } protected SizeRequirements calculateMajorAxisRequirements(int paramInt, SizeRequirements paramSizeRequirements) { updateInsets(); this.rowIterator.updateAdjustments(); paramSizeRequirements = CSS.calculateTiledRequirements(this.rowIterator, paramSizeRequirements); paramSizeRequirements.maximum = paramSizeRequirements.preferred; return paramSizeRequirements; } protected void layoutMinorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) { updateGrid(); int i = getRowCount(); for (byte b = 0; b < i; b++) { RowView rowView = getRow(b); rowView.layoutChanged(paramInt2); }  layoutColumns(paramInt1, this.columnOffsets, this.columnSpans, this.columnRequirements); super.layoutMinorAxis(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2); } protected void layoutMajorAxis(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) { this.rowIterator.setLayoutArrays(paramArrayOfint1, paramArrayOfint2); CSS.calculateTiledLayout(this.rowIterator, paramInt1); if (this.captionIndex != -1) { View view = getView(this.captionIndex); int i = (int)view.getPreferredSpan(1); paramArrayOfint2[this.captionIndex] = i; short s = (short)(int)this.painter.getInset(3, this); if (s != getBottomInset()) { paramArrayOfint1[this.captionIndex] = paramInt1 + s; } else { paramArrayOfint1[this.captionIndex] = -getTopInset(); }  }  } protected View getViewAtPosition(int paramInt, Rectangle paramRectangle) { int i = getViewCount(); for (byte b = 0; b < i; b++) { View view = getView(b); int j = view.getStartOffset(); int k = view.getEndOffset(); if (paramInt >= j && paramInt < k) { if (paramRectangle != null) childAllocation(b, paramRectangle);  return view; }  }  if (paramInt == getEndOffset()) { View view = getView(i - 1); if (paramRectangle != null) childAllocation(i - 1, paramRectangle);  return view; }  return null; } public AttributeSet getAttributes() { if (this.attr == null) { StyleSheet styleSheet = getStyleSheet(); this.attr = styleSheet.getViewAttributes(this); }  return this.attr; } public void paint(Graphics paramGraphics, Shape paramShape) { Rectangle rectangle = paramShape.getBounds(); setSize(rectangle.width, rectangle.height); if (this.captionIndex != -1) { short s1 = (short)(int)this.painter.getInset(1, this); short s2 = (short)(int)this.painter.getInset(3, this); if (s1 != getTopInset()) { int j = getTopInset() - s1; rectangle.y += j; rectangle.height -= j; } else { rectangle.height -= getBottomInset() - s2; }  }  this.painter.paint(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this); int i = getViewCount(); for (byte b = 0; b < i; b++) { View view = getView(b); view.paint(paramGraphics, getChildAllocation(b, paramShape)); }  } public void setParent(View paramView) { super.setParent(paramView); if (paramView != null) setPropertiesFromAttributes();  } public ViewFactory getViewFactory() { return this; } public void insertUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) { super.insertUpdate(paramDocumentEvent, paramShape, this); } public void removeUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) { super.removeUpdate(paramDocumentEvent, paramShape, this); } public void changedUpdate(DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) { super.changedUpdate(paramDocumentEvent, paramShape, this); } protected void forwardUpdate(DocumentEvent.ElementChange paramElementChange, DocumentEvent paramDocumentEvent, Shape paramShape, ViewFactory paramViewFactory) { super.forwardUpdate(paramElementChange, paramDocumentEvent, paramShape, paramViewFactory); if (paramShape != null) { Container container = getContainer(); if (container != null) { Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds(); container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height); }  }  } public void replace(int paramInt1, int paramInt2, View[] paramArrayOfView) { super.replace(paramInt1, paramInt2, paramArrayOfView); invalidateGrid(); } public View create(Element paramElement) { Object object = paramElement.getAttributes().getAttribute(StyleConstants.NameAttribute); if (object instanceof HTML.Tag) { HTML.Tag tag = (HTML.Tag)object; if (tag == HTML.Tag.TR) return createTableRow(paramElement);  if (tag == HTML.Tag.TD || tag == HTML.Tag.TH) return new CellView(paramElement);  if (tag == HTML.Tag.CAPTION) return new ParagraphView(paramElement);  }  View view = getParent(); if (view != null) { ViewFactory viewFactory = view.getViewFactory(); if (viewFactory != null) return viewFactory.create(paramElement);  }  return null; } private static final BitSet EMPTY = new BitSet();
/*      */   
/*      */   class ColumnIterator implements CSS.LayoutIterator { private int col;
/*      */     private int[] percentages;
/*      */     private int[] adjustmentWeights;
/*      */     private int[] offsets;
/*      */     private int[] spans;
/*      */     
/*      */     void disablePercentages() {
/* 1008 */       this.percentages = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void updatePercentagesAndAdjustmentWeights(int param1Int) {
/* 1015 */       this.adjustmentWeights = new int[TableView.this.columnRequirements.length]; int i;
/* 1016 */       for (i = 0; i < TableView.this.columnRequirements.length; i++) {
/* 1017 */         this.adjustmentWeights[i] = 0;
/*      */       }
/* 1019 */       if (TableView.this.relativeCells) {
/* 1020 */         this.percentages = new int[TableView.this.columnRequirements.length];
/*      */       } else {
/* 1022 */         this.percentages = null;
/*      */       } 
/* 1024 */       i = TableView.this.getRowCount();
/* 1025 */       for (byte b = 0; b < i; b++) {
/* 1026 */         TableView.RowView rowView = TableView.this.getRow(b);
/* 1027 */         int j = 0;
/* 1028 */         int k = rowView.getViewCount();
/* 1029 */         for (byte b1 = 0; b1 < k; b1++, j++) {
/* 1030 */           View view = rowView.getView(b1);
/* 1031 */           for (; rowView.isFilled(j); j++);
/* 1032 */           int m = TableView.this.getRowsOccupied(view);
/* 1033 */           int n = TableView.this.getColumnsOccupied(view);
/* 1034 */           AttributeSet attributeSet = view.getAttributes();
/*      */           
/* 1036 */           CSS.LengthValue lengthValue = (CSS.LengthValue)attributeSet.getAttribute(CSS.Attribute.WIDTH);
/* 1037 */           if (lengthValue != null) {
/* 1038 */             int i1 = (int)(lengthValue.getValue(param1Int) / n + 0.5F);
/* 1039 */             for (byte b2 = 0; b2 < n; b2++) {
/* 1040 */               if (lengthValue.isPercentage()) {
/*      */                 
/* 1042 */                 this.percentages[j + b2] = Math.max(this.percentages[j + b2], i1);
/* 1043 */                 this.adjustmentWeights[j + b2] = Math.max(this.adjustmentWeights[j + b2], 2);
/*      */               } else {
/* 1045 */                 this.adjustmentWeights[j + b2] = Math.max(this.adjustmentWeights[j + b2], 1);
/*      */               } 
/*      */             } 
/*      */           } 
/* 1049 */           j += n - 1;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setLayoutArrays(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int) {
/* 1058 */       this.offsets = param1ArrayOfint1;
/* 1059 */       this.spans = param1ArrayOfint2;
/* 1060 */       updatePercentagesAndAdjustmentWeights(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCount() {
/* 1066 */       return TableView.this.columnRequirements.length;
/*      */     }
/*      */     
/*      */     public void setIndex(int param1Int) {
/* 1070 */       this.col = param1Int;
/*      */     }
/*      */     
/*      */     public void setOffset(int param1Int) {
/* 1074 */       this.offsets[this.col] = param1Int;
/*      */     }
/*      */     
/*      */     public int getOffset() {
/* 1078 */       return this.offsets[this.col];
/*      */     }
/*      */     
/*      */     public void setSpan(int param1Int) {
/* 1082 */       this.spans[this.col] = param1Int;
/*      */     }
/*      */     
/*      */     public int getSpan() {
/* 1086 */       return this.spans[this.col];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getMinimumSpan(float param1Float) {
/* 1093 */       return (TableView.this.columnRequirements[this.col]).minimum;
/*      */     }
/*      */     
/*      */     public float getPreferredSpan(float param1Float) {
/* 1097 */       if (this.percentages != null && this.percentages[this.col] != 0) {
/* 1098 */         return Math.max(this.percentages[this.col], (TableView.this.columnRequirements[this.col]).minimum);
/*      */       }
/* 1100 */       return (TableView.this.columnRequirements[this.col]).preferred;
/*      */     }
/*      */     
/*      */     public float getMaximumSpan(float param1Float) {
/* 1104 */       return (TableView.this.columnRequirements[this.col]).maximum;
/*      */     }
/*      */     
/*      */     public float getBorderWidth() {
/* 1108 */       return TableView.this.borderWidth;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getLeadingCollapseSpan() {
/* 1113 */       return TableView.this.cellSpacing;
/*      */     }
/*      */     
/*      */     public float getTrailingCollapseSpan() {
/* 1117 */       return TableView.this.cellSpacing;
/*      */     }
/*      */     
/*      */     public int getAdjustmentWeight() {
/* 1121 */       return this.adjustmentWeights[this.col];
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class RowIterator
/*      */     implements CSS.LayoutIterator
/*      */   {
/*      */     private int row;
/*      */ 
/*      */ 
/*      */     
/*      */     private int[] adjustments;
/*      */ 
/*      */ 
/*      */     
/*      */     private int[] offsets;
/*      */ 
/*      */     
/*      */     private int[] spans;
/*      */ 
/*      */ 
/*      */     
/*      */     void updateAdjustments() {
/* 1147 */       boolean bool = true;
/* 1148 */       if (TableView.this.multiRowCells) {
/*      */         
/* 1150 */         int i = TableView.this.getRowCount();
/* 1151 */         this.adjustments = new int[i];
/* 1152 */         for (byte b = 0; b < i; b++) {
/* 1153 */           TableView.RowView rowView = TableView.this.getRow(b);
/* 1154 */           if (rowView.multiRowCells == true) {
/* 1155 */             int j = rowView.getViewCount();
/* 1156 */             for (byte b1 = 0; b1 < j; b1++) {
/* 1157 */               View view = rowView.getView(b1);
/* 1158 */               int k = TableView.this.getRowsOccupied(view);
/* 1159 */               if (k > 1) {
/* 1160 */                 int m = (int)view.getPreferredSpan(bool);
/* 1161 */                 adjustMultiRowSpan(m, k, b);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1167 */         this.adjustments = null;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void adjustMultiRowSpan(int param1Int1, int param1Int2, int param1Int3) {
/* 1178 */       if (param1Int3 + param1Int2 > getCount()) {
/*      */ 
/*      */ 
/*      */         
/* 1182 */         param1Int2 = getCount() - param1Int3;
/* 1183 */         if (param1Int2 < 1) {
/*      */           return;
/*      */         }
/*      */       } 
/* 1187 */       int i = 0; int j;
/* 1188 */       for (j = 0; j < param1Int2; j++) {
/* 1189 */         TableView.RowView rowView = TableView.this.getRow(param1Int3 + j);
/* 1190 */         i = (int)(i + rowView.getPreferredSpan(1));
/*      */       } 
/* 1192 */       if (param1Int1 > i) {
/* 1193 */         j = param1Int1 - i;
/* 1194 */         int k = j / param1Int2;
/* 1195 */         int m = k + j - k * param1Int2;
/* 1196 */         TableView.RowView rowView = TableView.this.getRow(param1Int3);
/* 1197 */         this.adjustments[param1Int3] = Math.max(this.adjustments[param1Int3], m);
/*      */         
/* 1199 */         for (byte b = 1; b < param1Int2; b++) {
/* 1200 */           this.adjustments[param1Int3 + b] = Math.max(this.adjustments[param1Int3 + b], k);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void setLayoutArrays(int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
/* 1207 */       this.offsets = param1ArrayOfint1;
/* 1208 */       this.spans = param1ArrayOfint2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setOffset(int param1Int) {
/* 1214 */       TableView.RowView rowView = TableView.this.getRow(this.row);
/* 1215 */       if (rowView != null) {
/* 1216 */         this.offsets[rowView.viewIndex] = param1Int;
/*      */       }
/*      */     }
/*      */     
/*      */     public int getOffset() {
/* 1221 */       TableView.RowView rowView = TableView.this.getRow(this.row);
/* 1222 */       if (rowView != null) {
/* 1223 */         return this.offsets[rowView.viewIndex];
/*      */       }
/* 1225 */       return 0;
/*      */     }
/*      */     
/*      */     public void setSpan(int param1Int) {
/* 1229 */       TableView.RowView rowView = TableView.this.getRow(this.row);
/* 1230 */       if (rowView != null) {
/* 1231 */         this.spans[rowView.viewIndex] = param1Int;
/*      */       }
/*      */     }
/*      */     
/*      */     public int getSpan() {
/* 1236 */       TableView.RowView rowView = TableView.this.getRow(this.row);
/* 1237 */       if (rowView != null) {
/* 1238 */         return this.spans[rowView.viewIndex];
/*      */       }
/* 1240 */       return 0;
/*      */     }
/*      */     
/*      */     public int getCount() {
/* 1244 */       return TableView.this.rows.size();
/*      */     }
/*      */     
/*      */     public void setIndex(int param1Int) {
/* 1248 */       this.row = param1Int;
/*      */     }
/*      */     
/*      */     public float getMinimumSpan(float param1Float) {
/* 1252 */       return getPreferredSpan(param1Float);
/*      */     }
/*      */     
/*      */     public float getPreferredSpan(float param1Float) {
/* 1256 */       TableView.RowView rowView = TableView.this.getRow(this.row);
/* 1257 */       if (rowView != null) {
/* 1258 */         boolean bool = (this.adjustments != null) ? this.adjustments[this.row] : false;
/* 1259 */         return rowView.getPreferredSpan(TableView.this.getAxis()) + bool;
/*      */       } 
/* 1261 */       return 0.0F;
/*      */     }
/*      */     
/*      */     public float getMaximumSpan(float param1Float) {
/* 1265 */       return getPreferredSpan(param1Float);
/*      */     }
/*      */     
/*      */     public float getBorderWidth() {
/* 1269 */       return TableView.this.borderWidth;
/*      */     }
/*      */     
/*      */     public float getLeadingCollapseSpan() {
/* 1273 */       return TableView.this.cellSpacing;
/*      */     }
/*      */     
/*      */     public float getTrailingCollapseSpan() {
/* 1277 */       return TableView.this.cellSpacing;
/*      */     }
/*      */     
/*      */     public int getAdjustmentWeight() {
/* 1281 */       return 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class RowView
/*      */     extends BoxView
/*      */   {
/*      */     private StyleSheet.BoxPainter painter;
/*      */ 
/*      */     
/*      */     private AttributeSet attr;
/*      */ 
/*      */     
/*      */     BitSet fillColumns;
/*      */ 
/*      */     
/*      */     int rowIndex;
/*      */ 
/*      */     
/*      */     int viewIndex;
/*      */ 
/*      */     
/*      */     boolean multiRowCells;
/*      */ 
/*      */ 
/*      */     
/*      */     public RowView(Element param1Element) {
/* 1310 */       super(param1Element, 0);
/* 1311 */       this.fillColumns = new BitSet();
/* 1312 */       setPropertiesFromAttributes();
/*      */     }
/*      */     
/*      */     void clearFilledColumns() {
/* 1316 */       this.fillColumns.and(TableView.EMPTY);
/*      */     }
/*      */     
/*      */     void fillColumn(int param1Int) {
/* 1320 */       this.fillColumns.set(param1Int);
/*      */     }
/*      */     
/*      */     boolean isFilled(int param1Int) {
/* 1324 */       return this.fillColumns.get(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getColumnCount() {
/* 1331 */       byte b1 = 0;
/* 1332 */       int i = this.fillColumns.size();
/* 1333 */       for (byte b2 = 0; b2 < i; b2++) {
/* 1334 */         if (this.fillColumns.get(b2)) {
/* 1335 */           b1++;
/*      */         }
/*      */       } 
/* 1338 */       return getViewCount() + b1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeSet getAttributes() {
/* 1347 */       return this.attr;
/*      */     }
/*      */     
/*      */     View findViewAtPoint(int param1Int1, int param1Int2, Rectangle param1Rectangle) {
/* 1351 */       int i = getViewCount();
/* 1352 */       for (byte b = 0; b < i; b++) {
/* 1353 */         if (getChildAllocation(b, param1Rectangle).contains(param1Int1, param1Int2)) {
/* 1354 */           childAllocation(b, param1Rectangle);
/* 1355 */           return getView(b);
/*      */         } 
/*      */       } 
/* 1358 */       return null;
/*      */     }
/*      */     
/*      */     protected StyleSheet getStyleSheet() {
/* 1362 */       HTMLDocument hTMLDocument = (HTMLDocument)getDocument();
/* 1363 */       return hTMLDocument.getStyleSheet();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void preferenceChanged(View param1View, boolean param1Boolean1, boolean param1Boolean2) {
/* 1380 */       super.preferenceChanged(param1View, param1Boolean1, param1Boolean2);
/* 1381 */       if (TableView.this.multiRowCells && param1Boolean2) {
/* 1382 */         for (int i = this.rowIndex - 1; i >= 0; i--) {
/* 1383 */           RowView rowView = TableView.this.getRow(i);
/* 1384 */           if (rowView.multiRowCells) {
/* 1385 */             rowView.preferenceChanged((View)null, false, true);
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected SizeRequirements calculateMajorAxisRequirements(int param1Int, SizeRequirements param1SizeRequirements) {
/* 1396 */       SizeRequirements sizeRequirements = new SizeRequirements();
/* 1397 */       sizeRequirements.minimum = TableView.this.totalColumnRequirements.minimum;
/* 1398 */       sizeRequirements.maximum = TableView.this.totalColumnRequirements.maximum;
/* 1399 */       sizeRequirements.preferred = TableView.this.totalColumnRequirements.preferred;
/* 1400 */       sizeRequirements.alignment = 0.0F;
/* 1401 */       return sizeRequirements;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getMinimumSpan(int param1Int) {
/*      */       float f;
/* 1407 */       if (param1Int == 0) {
/*      */         
/* 1409 */         f = (TableView.this.totalColumnRequirements.minimum + getLeftInset() + getRightInset());
/*      */       } else {
/*      */         
/* 1412 */         f = super.getMinimumSpan(param1Int);
/*      */       } 
/* 1414 */       return f;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getMaximumSpan(int param1Int) {
/*      */       float f;
/* 1420 */       if (param1Int == 0) {
/*      */         
/* 1422 */         f = 2.14748365E9F;
/*      */       } else {
/*      */         
/* 1425 */         f = super.getMaximumSpan(param1Int);
/*      */       } 
/* 1427 */       return f;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getPreferredSpan(int param1Int) {
/*      */       float f;
/* 1433 */       if (param1Int == 0) {
/*      */         
/* 1435 */         f = (TableView.this.totalColumnRequirements.preferred + getLeftInset() + getRightInset());
/*      */       } else {
/*      */         
/* 1438 */         f = super.getPreferredSpan(param1Int);
/*      */       } 
/* 1440 */       return f;
/*      */     }
/*      */     
/*      */     public void changedUpdate(DocumentEvent param1DocumentEvent, Shape param1Shape, ViewFactory param1ViewFactory) {
/* 1444 */       super.changedUpdate(param1DocumentEvent, param1Shape, param1ViewFactory);
/* 1445 */       int i = param1DocumentEvent.getOffset();
/* 1446 */       if (i <= getStartOffset() && i + param1DocumentEvent.getLength() >= 
/* 1447 */         getEndOffset()) {
/* 1448 */         setPropertiesFromAttributes();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void paint(Graphics param1Graphics, Shape param1Shape) {
/* 1463 */       Rectangle rectangle = (Rectangle)param1Shape;
/* 1464 */       this.painter.paint(param1Graphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this);
/* 1465 */       super.paint(param1Graphics, rectangle);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void replace(int param1Int1, int param1Int2, View[] param1ArrayOfView) {
/* 1474 */       super.replace(param1Int1, param1Int2, param1ArrayOfView);
/* 1475 */       TableView.this.invalidateGrid();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected SizeRequirements calculateMinorAxisRequirements(int param1Int, SizeRequirements param1SizeRequirements) {
/* 1488 */       long l1 = 0L;
/* 1489 */       long l2 = 0L;
/* 1490 */       long l3 = 0L;
/* 1491 */       this.multiRowCells = false;
/* 1492 */       int i = getViewCount();
/* 1493 */       for (byte b = 0; b < i; b++) {
/* 1494 */         View view = getView(b);
/* 1495 */         if (TableView.this.getRowsOccupied(view) > 1) {
/* 1496 */           this.multiRowCells = true;
/* 1497 */           l3 = Math.max((int)view.getMaximumSpan(param1Int), l3);
/*      */         } else {
/* 1499 */           l1 = Math.max((int)view.getMinimumSpan(param1Int), l1);
/* 1500 */           l2 = Math.max((int)view.getPreferredSpan(param1Int), l2);
/* 1501 */           l3 = Math.max((int)view.getMaximumSpan(param1Int), l3);
/*      */         } 
/*      */       } 
/*      */       
/* 1505 */       if (param1SizeRequirements == null) {
/* 1506 */         param1SizeRequirements = new SizeRequirements();
/* 1507 */         param1SizeRequirements.alignment = 0.5F;
/*      */       } 
/* 1509 */       param1SizeRequirements.preferred = (int)l2;
/* 1510 */       param1SizeRequirements.minimum = (int)l1;
/* 1511 */       param1SizeRequirements.maximum = (int)l3;
/* 1512 */       return param1SizeRequirements;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void layoutMajorAxis(int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
/* 1537 */       int i = 0;
/* 1538 */       int j = getViewCount();
/* 1539 */       for (byte b = 0; b < j; b++) {
/* 1540 */         View view = getView(b);
/* 1541 */         if (!TableView.this.skipComments || view instanceof TableView.CellView) {
/*      */ 
/*      */           
/* 1544 */           for (; isFilled(i); i++);
/* 1545 */           int k = TableView.this.getColumnsOccupied(view);
/* 1546 */           param1ArrayOfint2[b] = TableView.this.columnSpans[i];
/* 1547 */           param1ArrayOfint1[b] = TableView.this.columnOffsets[i];
/* 1548 */           if (k > 1) {
/* 1549 */             int m = TableView.this.columnSpans.length;
/* 1550 */             for (byte b1 = 1; b1 < k; b1++) {
/*      */ 
/*      */ 
/*      */               
/* 1554 */               if (i + b1 < m) {
/* 1555 */                 param1ArrayOfint2[b] = param1ArrayOfint2[b] + TableView.this.columnSpans[i + b1];
/* 1556 */                 param1ArrayOfint2[b] = param1ArrayOfint2[b] + TableView.this.cellSpacing;
/*      */               } 
/*      */             } 
/* 1559 */             i += k - 1;
/*      */           } 
/* 1561 */           i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void layoutMinorAxis(int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
/* 1588 */       super.layoutMinorAxis(param1Int1, param1Int2, param1ArrayOfint1, param1ArrayOfint2);
/* 1589 */       int i = 0;
/* 1590 */       int j = getViewCount();
/* 1591 */       for (byte b = 0; b < j; b++, i++) {
/* 1592 */         View view = getView(b);
/* 1593 */         for (; isFilled(i); i++);
/* 1594 */         int k = TableView.this.getColumnsOccupied(view);
/* 1595 */         int m = TableView.this.getRowsOccupied(view);
/* 1596 */         if (m > 1) {
/*      */           
/* 1598 */           int n = this.rowIndex;
/* 1599 */           int i1 = Math.min(this.rowIndex + m - 1, TableView.this.getRowCount() - 1);
/* 1600 */           param1ArrayOfint2[b] = TableView.this.getMultiRowSpan(n, i1);
/*      */         } 
/* 1602 */         if (k > 1) {
/* 1603 */           i += k - 1;
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
/*      */ 
/*      */     
/*      */     public int getResizeWeight(int param1Int) {
/* 1617 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected View getViewAtPosition(int param1Int, Rectangle param1Rectangle) {
/* 1634 */       int i = getViewCount();
/* 1635 */       for (byte b = 0; b < i; b++) {
/* 1636 */         View view = getView(b);
/* 1637 */         int j = view.getStartOffset();
/* 1638 */         int k = view.getEndOffset();
/* 1639 */         if (param1Int >= j && param1Int < k) {
/*      */           
/* 1641 */           if (param1Rectangle != null) {
/* 1642 */             childAllocation(b, param1Rectangle);
/*      */           }
/* 1644 */           return view;
/*      */         } 
/*      */       } 
/* 1647 */       if (param1Int == getEndOffset()) {
/* 1648 */         View view = getView(i - 1);
/* 1649 */         if (param1Rectangle != null) {
/* 1650 */           childAllocation(i - 1, param1Rectangle);
/*      */         }
/* 1652 */         return view;
/*      */       } 
/* 1654 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setPropertiesFromAttributes() {
/* 1661 */       StyleSheet styleSheet = getStyleSheet();
/* 1662 */       this.attr = styleSheet.getViewAttributes(this);
/* 1663 */       this.painter = styleSheet.getBoxPainter(this.attr);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class CellView
/*      */     extends BlockView
/*      */   {
/*      */     public CellView(Element param1Element) {
/* 1702 */       super(param1Element, 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void layoutMajorAxis(int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2) {
/* 1730 */       super.layoutMajorAxis(param1Int1, param1Int2, param1ArrayOfint1, param1ArrayOfint2);
/*      */       
/* 1732 */       int i = 0;
/* 1733 */       int j = param1ArrayOfint2.length; int k;
/* 1734 */       for (k = 0; k < j; k++) {
/* 1735 */         i += param1ArrayOfint2[k];
/*      */       }
/*      */ 
/*      */       
/* 1739 */       k = 0;
/* 1740 */       if (i < param1Int1) {
/*      */         
/* 1742 */         String str = (String)getElement().getAttributes().getAttribute(HTML.Attribute.VALIGN);
/*      */         
/* 1744 */         if (str == null) {
/* 1745 */           AttributeSet attributeSet = getElement().getParentElement().getAttributes();
/* 1746 */           str = (String)attributeSet.getAttribute(HTML.Attribute.VALIGN);
/*      */         } 
/* 1748 */         if (str == null || str.equals("middle")) {
/* 1749 */           k = (param1Int1 - i) / 2;
/* 1750 */         } else if (str.equals("bottom")) {
/* 1751 */           k = param1Int1 - i;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1756 */       if (k != 0) {
/* 1757 */         for (byte b = 0; b < j; b++) {
/* 1758 */           param1ArrayOfint1[b] = param1ArrayOfint1[b] + k;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected SizeRequirements calculateMajorAxisRequirements(int param1Int, SizeRequirements param1SizeRequirements) {
/* 1779 */       SizeRequirements sizeRequirements = super.calculateMajorAxisRequirements(param1Int, param1SizeRequirements);
/* 1780 */       sizeRequirements.maximum = Integer.MAX_VALUE;
/* 1781 */       return sizeRequirements;
/*      */     }
/*      */ 
/*      */     
/*      */     protected SizeRequirements calculateMinorAxisRequirements(int param1Int, SizeRequirements param1SizeRequirements) {
/* 1786 */       SizeRequirements sizeRequirements = super.calculateMinorAxisRequirements(param1Int, param1SizeRequirements);
/*      */ 
/*      */       
/* 1789 */       int i = getViewCount();
/* 1790 */       int j = 0;
/* 1791 */       for (byte b = 0; b < i; b++) {
/* 1792 */         View view = getView(b);
/* 1793 */         j = Math.max((int)view.getMinimumSpan(param1Int), j);
/*      */       } 
/* 1795 */       sizeRequirements.minimum = Math.min(sizeRequirements.minimum, j);
/* 1796 */       return sizeRequirements;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/TableView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */