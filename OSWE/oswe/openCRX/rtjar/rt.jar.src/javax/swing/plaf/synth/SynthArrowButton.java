/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.SwingConstants;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SynthArrowButton
/*     */   extends JButton
/*     */   implements SwingConstants, UIResource
/*     */ {
/*     */   private int direction;
/*     */   
/*     */   public SynthArrowButton(int paramInt) {
/*  41 */     super.setFocusable(false);
/*  42 */     setDirection(paramInt);
/*  43 */     setDefaultCapable(false);
/*     */   }
/*     */   
/*     */   public String getUIClassID() {
/*  47 */     return "ArrowButtonUI";
/*     */   }
/*     */   
/*     */   public void updateUI() {
/*  51 */     setUI(new SynthArrowButtonUI());
/*     */   }
/*     */   
/*     */   public void setDirection(int paramInt) {
/*  55 */     this.direction = paramInt;
/*  56 */     putClientProperty("__arrow_direction__", Integer.valueOf(paramInt));
/*  57 */     repaint();
/*     */   }
/*     */   
/*     */   public int getDirection() {
/*  61 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setFocusable(boolean paramBoolean) {}
/*     */   
/*     */   private static class SynthArrowButtonUI extends SynthButtonUI {
/*     */     protected void installDefaults(AbstractButton param1AbstractButton) {
/*  68 */       super.installDefaults(param1AbstractButton);
/*  69 */       updateStyle(param1AbstractButton);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void paint(SynthContext param1SynthContext, Graphics param1Graphics) {
/*  74 */       SynthArrowButton synthArrowButton = (SynthArrowButton)param1SynthContext.getComponent();
/*  75 */       param1SynthContext.getPainter().paintArrowButtonForeground(param1SynthContext, param1Graphics, 0, 0, synthArrowButton
/*  76 */           .getWidth(), synthArrowButton.getHeight(), synthArrowButton
/*  77 */           .getDirection());
/*     */     }
/*     */     private SynthArrowButtonUI() {}
/*     */     void paintBackground(SynthContext param1SynthContext, Graphics param1Graphics, JComponent param1JComponent) {
/*  81 */       param1SynthContext.getPainter().paintArrowButtonBackground(param1SynthContext, param1Graphics, 0, 0, param1JComponent
/*  82 */           .getWidth(), param1JComponent.getHeight());
/*     */     }
/*     */ 
/*     */     
/*     */     public void paintBorder(SynthContext param1SynthContext, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  87 */       param1SynthContext.getPainter().paintArrowButtonBorder(param1SynthContext, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */     
/*     */     public Dimension getMinimumSize() {
/*  91 */       return new Dimension(5, 5);
/*     */     }
/*     */     
/*     */     public Dimension getMaximumSize() {
/*  95 */       return new Dimension(2147483647, 2147483647);
/*     */     }
/*     */     
/*     */     public Dimension getPreferredSize(JComponent param1JComponent) {
/*  99 */       SynthContext synthContext = getContext(param1JComponent);
/* 100 */       Dimension dimension = null;
/* 101 */       if (synthContext.getComponent().getName() == "ScrollBar.button")
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 106 */         dimension = (Dimension)synthContext.getStyle().get(synthContext, "ScrollBar.buttonSize");
/*     */       }
/* 108 */       if (dimension == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 113 */         int i = synthContext.getStyle().getInt(synthContext, "ArrowButton.size", 16);
/* 114 */         dimension = new Dimension(i, i);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       Container container = synthContext.getComponent().getParent();
/* 121 */       if (container instanceof JComponent && !(container instanceof javax.swing.JComboBox)) {
/*     */         
/* 123 */         Object object = ((JComponent)container).getClientProperty("JComponent.sizeVariant");
/* 124 */         if (object != null) {
/* 125 */           if ("large".equals(object)) {
/* 126 */             dimension = new Dimension((int)(dimension.width * 1.15D), (int)(dimension.height * 1.15D));
/*     */           
/*     */           }
/* 129 */           else if ("small".equals(object)) {
/* 130 */             dimension = new Dimension((int)(dimension.width * 0.857D), (int)(dimension.height * 0.857D));
/*     */           
/*     */           }
/* 133 */           else if ("mini".equals(object)) {
/* 134 */             dimension = new Dimension((int)(dimension.width * 0.714D), (int)(dimension.height * 0.714D));
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 141 */       synthContext.dispose();
/* 142 */       return dimension;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthArrowButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */