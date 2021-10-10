/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.List;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.RowSorter;
/*     */ import javax.swing.SortOrder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTableHeaderUI;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ import sun.swing.table.DefaultTableCellHeaderRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthTableHeaderUI
/*     */   extends BasicTableHeaderUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*  52 */   private TableCellRenderer prevRenderer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle style;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new SynthTableHeaderUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  71 */     this.prevRenderer = this.header.getDefaultRenderer();
/*  72 */     if (this.prevRenderer instanceof javax.swing.plaf.UIResource) {
/*  73 */       this.header.setDefaultRenderer(new HeaderRenderer());
/*     */     }
/*  75 */     updateStyle(this.header);
/*     */   }
/*     */   
/*     */   private void updateStyle(JTableHeader paramJTableHeader) {
/*  79 */     SynthContext synthContext = getContext(paramJTableHeader, 1);
/*  80 */     SynthStyle synthStyle = this.style;
/*  81 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  82 */     if (this.style != synthStyle && 
/*  83 */       synthStyle != null) {
/*  84 */       uninstallKeyboardActions();
/*  85 */       installKeyboardActions();
/*     */     } 
/*     */     
/*  88 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  96 */     super.installListeners();
/*  97 */     this.header.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 105 */     if (this.header.getDefaultRenderer() instanceof HeaderRenderer) {
/* 106 */       this.header.setDefaultRenderer(this.prevRenderer);
/*     */     }
/*     */     
/* 109 */     SynthContext synthContext = getContext(this.header, 1);
/*     */     
/* 111 */     this.style.uninstallDefaults(synthContext);
/* 112 */     synthContext.dispose();
/* 113 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 121 */     this.header.removePropertyChangeListener(this);
/* 122 */     super.uninstallListeners();
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 139 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 141 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 142 */     synthContext.getPainter().paintTableHeaderBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 143 */         .getWidth(), paramJComponent.getHeight());
/* 144 */     paint(synthContext, paramGraphics);
/* 145 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 159 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 161 */     paint(synthContext, paramGraphics);
/* 162 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 173 */     super.paint(paramGraphics, paramSynthContext.getComponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 182 */     paramSynthContext.getPainter().paintTableHeaderBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 192 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 196 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rolloverColumnUpdated(int paramInt1, int paramInt2) {
/* 204 */     this.header.repaint(this.header.getHeaderRect(paramInt1));
/* 205 */     this.header.repaint(this.header.getHeaderRect(paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 213 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 214 */       updateStyle((JTableHeader)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */   
/*     */   private class HeaderRenderer
/*     */     extends DefaultTableCellHeaderRenderer {
/*     */     HeaderRenderer() {
/* 220 */       setHorizontalAlignment(10);
/* 221 */       setName("TableHeader.renderer");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/* 230 */       boolean bool = (param1Int2 == SynthTableHeaderUI.this.getRolloverColumn()) ? true : false;
/* 231 */       if (param1Boolean1 || bool || param1Boolean2) {
/* 232 */         SynthLookAndFeel.setSelectedUI(
/* 233 */             (SynthLabelUI)SynthLookAndFeel.getUIOfType(getUI(), SynthLabelUI.class), param1Boolean1, param1Boolean2, param1JTable
/* 234 */             .isEnabled(), bool);
/*     */       } else {
/*     */         
/* 237 */         SynthLookAndFeel.resetSelectedUI();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 242 */       RowSorter<? extends TableModel> rowSorter = (param1JTable == null) ? null : param1JTable.getRowSorter();
/* 243 */       List<? extends RowSorter.SortKey> list = (rowSorter == null) ? null : rowSorter.getSortKeys();
/* 244 */       if (list != null && list.size() > 0 && ((RowSorter.SortKey)list.get(0)).getColumn() == param1JTable
/* 245 */         .convertColumnIndexToModel(param1Int2))
/* 246 */       { switch (((RowSorter.SortKey)list.get(0)).getSortOrder())
/*     */         { case ASCENDING:
/* 248 */             putClientProperty("Table.sortOrder", "ASCENDING");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 263 */             super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2);
/*     */ 
/*     */             
/* 266 */             return this;case DESCENDING: putClientProperty("Table.sortOrder", "DESCENDING"); super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2); return this;case UNSORTED: putClientProperty("Table.sortOrder", "UNSORTED"); super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2); return this; }  throw new AssertionError("Cannot happen"); }  putClientProperty("Table.sortOrder", "UNSORTED"); super.getTableCellRendererComponent(param1JTable, param1Object, param1Boolean1, param1Boolean2, param1Int1, param1Int2); return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setBorder(Border param1Border) {
/* 271 */       if (param1Border instanceof SynthBorder)
/* 272 */         super.setBorder(param1Border); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthTableHeaderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */