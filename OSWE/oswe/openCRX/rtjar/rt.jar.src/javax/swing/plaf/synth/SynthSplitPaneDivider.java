/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*     */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*     */ import sun.swing.DefaultLookup;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SynthSplitPaneDivider
/*     */   extends BasicSplitPaneDivider
/*     */ {
/*     */   public SynthSplitPaneDivider(BasicSplitPaneUI paramBasicSplitPaneUI) {
/*  41 */     super(paramBasicSplitPaneUI);
/*     */   }
/*     */   
/*     */   protected void setMouseOver(boolean paramBoolean) {
/*  45 */     if (isMouseOver() != paramBoolean) {
/*  46 */       repaint();
/*     */     }
/*  48 */     super.setMouseOver(paramBoolean);
/*     */   }
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/*  52 */     super.propertyChange(paramPropertyChangeEvent);
/*  53 */     if (paramPropertyChangeEvent.getSource() == this.splitPane && 
/*  54 */       paramPropertyChangeEvent.getPropertyName() == "orientation") {
/*  55 */       if (this.leftButton instanceof SynthArrowButton) {
/*  56 */         ((SynthArrowButton)this.leftButton).setDirection(
/*  57 */             mapDirection(true));
/*     */       }
/*  59 */       if (this.rightButton instanceof SynthArrowButton) {
/*  60 */         ((SynthArrowButton)this.rightButton).setDirection(
/*  61 */             mapDirection(false));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/*  68 */     Graphics graphics = paramGraphics.create();
/*     */     
/*  70 */     SynthContext synthContext = ((SynthSplitPaneUI)this.splitPaneUI).getContext(this.splitPane, Region.SPLIT_PANE_DIVIDER);
/*     */     
/*  72 */     Rectangle rectangle = getBounds();
/*  73 */     rectangle.x = rectangle.y = 0;
/*  74 */     SynthLookAndFeel.updateSubregion(synthContext, paramGraphics, rectangle);
/*  75 */     synthContext.getPainter().paintSplitPaneDividerBackground(synthContext, paramGraphics, 0, 0, rectangle.width, rectangle.height, this.splitPane
/*     */         
/*  77 */         .getOrientation());
/*     */     
/*  79 */     Object object = null;
/*     */     
/*  81 */     synthContext.getPainter().paintSplitPaneDividerForeground(synthContext, paramGraphics, 0, 0, 
/*  82 */         getWidth(), getHeight(), this.splitPane.getOrientation());
/*  83 */     synthContext.dispose();
/*     */ 
/*     */     
/*  86 */     for (byte b = 0; b < getComponentCount(); b++) {
/*  87 */       Component component = getComponent(b);
/*  88 */       Rectangle rectangle1 = component.getBounds();
/*  89 */       Graphics graphics1 = paramGraphics.create(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
/*     */       
/*  91 */       component.paint(graphics1);
/*  92 */       graphics1.dispose();
/*     */     } 
/*  94 */     graphics.dispose();
/*     */   }
/*     */   
/*     */   private int mapDirection(boolean paramBoolean) {
/*  98 */     if (paramBoolean) {
/*  99 */       if (this.splitPane.getOrientation() == 1) {
/* 100 */         return 7;
/*     */       }
/* 102 */       return 1;
/*     */     } 
/* 104 */     if (this.splitPane.getOrientation() == 1) {
/* 105 */       return 3;
/*     */     }
/* 107 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createLeftOneTouchButton() {
/* 116 */     SynthArrowButton synthArrowButton = new SynthArrowButton(1);
/* 117 */     int i = lookupOneTouchSize();
/*     */     
/* 119 */     synthArrowButton.setName("SplitPaneDivider.leftOneTouchButton");
/* 120 */     synthArrowButton.setMinimumSize(new Dimension(i, i));
/* 121 */     synthArrowButton.setCursor(Cursor.getPredefinedCursor(0));
/* 122 */     synthArrowButton.setFocusPainted(false);
/* 123 */     synthArrowButton.setBorderPainted(false);
/* 124 */     synthArrowButton.setRequestFocusEnabled(false);
/* 125 */     synthArrowButton.setDirection(mapDirection(true));
/* 126 */     return synthArrowButton;
/*     */   }
/*     */   
/*     */   private int lookupOneTouchSize() {
/* 130 */     return DefaultLookup.getInt(this.splitPaneUI.getSplitPane(), this.splitPaneUI, "SplitPaneDivider.oneTouchButtonSize", 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JButton createRightOneTouchButton() {
/* 139 */     SynthArrowButton synthArrowButton = new SynthArrowButton(1);
/* 140 */     int i = lookupOneTouchSize();
/*     */     
/* 142 */     synthArrowButton.setName("SplitPaneDivider.rightOneTouchButton");
/* 143 */     synthArrowButton.setMinimumSize(new Dimension(i, i));
/* 144 */     synthArrowButton.setCursor(Cursor.getPredefinedCursor(0));
/* 145 */     synthArrowButton.setFocusPainted(false);
/* 146 */     synthArrowButton.setBorderPainted(false);
/* 147 */     synthArrowButton.setRequestFocusEnabled(false);
/* 148 */     synthArrowButton.setDirection(mapDirection(false));
/* 149 */     return synthArrowButton;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthSplitPaneDivider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */