/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.DefaultListCellRenderer;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicListUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthListUI
/*     */   extends BasicListUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private SynthStyle style;
/*     */   private boolean useListColors;
/*     */   private boolean useUIBorder;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  56 */     return new SynthListUI();
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
/*  73 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/*  75 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/*  76 */     synthContext.getPainter().paintListBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/*  77 */         .getWidth(), paramJComponent.getHeight());
/*  78 */     synthContext.dispose();
/*  79 */     paint(paramGraphics, paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  88 */     paramSynthContext.getPainter().paintListBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  96 */     super.installListeners();
/*  97 */     this.list.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 105 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 106 */       updateStyle((JList)paramPropertyChangeEvent.getSource());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 115 */     super.uninstallListeners();
/* 116 */     this.list.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/* 124 */     if (this.list.getCellRenderer() == null || this.list
/* 125 */       .getCellRenderer() instanceof javax.swing.plaf.UIResource) {
/* 126 */       this.list.setCellRenderer(new SynthListCellRenderer());
/*     */     }
/* 128 */     updateStyle(this.list);
/*     */   }
/*     */   
/*     */   private void updateStyle(JComponent paramJComponent) {
/* 132 */     SynthContext synthContext = getContext(this.list, 1);
/* 133 */     SynthStyle synthStyle = this.style;
/*     */     
/* 135 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */     
/* 137 */     if (this.style != synthStyle) {
/* 138 */       synthContext.setComponentState(512);
/* 139 */       Color color1 = this.list.getSelectionBackground();
/* 140 */       if (color1 == null || color1 instanceof javax.swing.plaf.UIResource) {
/* 141 */         this.list.setSelectionBackground(this.style.getColor(synthContext, ColorType.TEXT_BACKGROUND));
/*     */       }
/*     */ 
/*     */       
/* 145 */       Color color2 = this.list.getSelectionForeground();
/* 146 */       if (color2 == null || color2 instanceof javax.swing.plaf.UIResource) {
/* 147 */         this.list.setSelectionForeground(this.style.getColor(synthContext, ColorType.TEXT_FOREGROUND));
/*     */       }
/*     */ 
/*     */       
/* 151 */       this.useListColors = this.style.getBoolean(synthContext, "List.rendererUseListColors", true);
/*     */       
/* 153 */       this.useUIBorder = this.style.getBoolean(synthContext, "List.rendererUseUIBorder", true);
/*     */ 
/*     */       
/* 156 */       int i = this.style.getInt(synthContext, "List.cellHeight", -1);
/* 157 */       if (i != -1) {
/* 158 */         this.list.setFixedCellHeight(i);
/*     */       }
/* 160 */       if (synthStyle != null) {
/* 161 */         uninstallKeyboardActions();
/* 162 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 165 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 173 */     super.uninstallDefaults();
/*     */     
/* 175 */     SynthContext synthContext = getContext(this.list, 1);
/*     */     
/* 177 */     this.style.uninstallDefaults(synthContext);
/* 178 */     synthContext.dispose();
/* 179 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 187 */     return getContext(paramJComponent, getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 191 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent) {
/* 195 */     return SynthLookAndFeel.getComponentState(paramJComponent);
/*     */   }
/*     */   
/*     */   private class SynthListCellRenderer
/*     */     extends DefaultListCellRenderer.UIResource {
/*     */     public String getName() {
/* 201 */       return "List.cellRenderer";
/*     */     }
/*     */     private SynthListCellRenderer() {}
/*     */     public void setBorder(Border param1Border) {
/* 205 */       if (SynthListUI.this.useUIBorder || param1Border instanceof SynthBorder) {
/* 206 */         super.setBorder(param1Border);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getListCellRendererComponent(JList<?> param1JList, Object param1Object, int param1Int, boolean param1Boolean1, boolean param1Boolean2) {
/* 212 */       if (!SynthListUI.this.useListColors && (param1Boolean1 || param1Boolean2)) {
/* 213 */         SynthLookAndFeel.setSelectedUI(
/* 214 */             (SynthLabelUI)SynthLookAndFeel.getUIOfType(getUI(), SynthLabelUI.class), param1Boolean1, param1Boolean2, param1JList
/* 215 */             .isEnabled(), false);
/*     */       } else {
/*     */         
/* 218 */         SynthLookAndFeel.resetSelectedUI();
/*     */       } 
/*     */       
/* 221 */       super.getListCellRendererComponent(param1JList, param1Object, param1Int, param1Boolean1, param1Boolean2);
/*     */       
/* 223 */       return this;
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 227 */       super.paint(param1Graphics);
/* 228 */       SynthLookAndFeel.resetSelectedUI();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthListUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */