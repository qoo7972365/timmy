/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Shape;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.LookAndFeel;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicSplitPaneDivider;
/*     */ import javax.swing.plaf.basic.BasicSplitPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthSplitPaneUI
/*     */   extends BasicSplitPaneUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*     */   private static Set<KeyStroke> managingFocusForwardTraversalKeys;
/*     */   private static Set<KeyStroke> managingFocusBackwardTraversalKeys;
/*     */   private SynthStyle style;
/*     */   private SynthStyle dividerStyle;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  76 */     return new SynthSplitPaneUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  84 */     updateStyle(this.splitPane);
/*     */     
/*  86 */     setOrientation(this.splitPane.getOrientation());
/*  87 */     setContinuousLayout(this.splitPane.isContinuousLayout());
/*     */     
/*  89 */     resetLayoutManager();
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (this.nonContinuousLayoutDivider == null) {
/*  94 */       setNonContinuousLayoutDivider(
/*  95 */           createDefaultNonContinuousLayoutDivider(), true);
/*     */     } else {
/*     */       
/*  98 */       setNonContinuousLayoutDivider(this.nonContinuousLayoutDivider, true);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (managingFocusForwardTraversalKeys == null) {
/* 103 */       managingFocusForwardTraversalKeys = new HashSet<>();
/* 104 */       managingFocusForwardTraversalKeys.add(
/* 105 */           KeyStroke.getKeyStroke(9, 0));
/*     */     } 
/* 107 */     this.splitPane.setFocusTraversalKeys(0, (Set)managingFocusForwardTraversalKeys);
/*     */ 
/*     */     
/* 110 */     if (managingFocusBackwardTraversalKeys == null) {
/* 111 */       managingFocusBackwardTraversalKeys = new HashSet<>();
/* 112 */       managingFocusBackwardTraversalKeys.add(
/* 113 */           KeyStroke.getKeyStroke(9, 1));
/*     */     } 
/* 115 */     this.splitPane.setFocusTraversalKeys(1, (Set)managingFocusBackwardTraversalKeys);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateStyle(JSplitPane paramJSplitPane) {
/* 120 */     SynthContext synthContext = getContext(paramJSplitPane, Region.SPLIT_PANE_DIVIDER, 1);
/*     */     
/* 122 */     SynthStyle synthStyle1 = this.dividerStyle;
/* 123 */     this.dividerStyle = SynthLookAndFeel.updateStyle(synthContext, this);
/* 124 */     synthContext.dispose();
/*     */     
/* 126 */     synthContext = getContext(paramJSplitPane, 1);
/* 127 */     SynthStyle synthStyle2 = this.style;
/*     */     
/* 129 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*     */     
/* 131 */     if (this.style != synthStyle2) {
/* 132 */       Object object = this.style.get(synthContext, "SplitPane.size");
/* 133 */       if (object == null) {
/* 134 */         object = Integer.valueOf(6);
/*     */       }
/* 136 */       LookAndFeel.installProperty(paramJSplitPane, "dividerSize", object);
/*     */       
/* 138 */       object = this.style.get(synthContext, "SplitPane.oneTouchExpandable");
/* 139 */       if (object != null) {
/* 140 */         LookAndFeel.installProperty(paramJSplitPane, "oneTouchExpandable", object);
/*     */       }
/*     */       
/* 143 */       if (this.divider != null) {
/* 144 */         paramJSplitPane.remove(this.divider);
/* 145 */         this.divider.setDividerSize(paramJSplitPane.getDividerSize());
/*     */       } 
/* 147 */       if (synthStyle2 != null) {
/* 148 */         uninstallKeyboardActions();
/* 149 */         installKeyboardActions();
/*     */       } 
/*     */     } 
/* 152 */     if (this.style != synthStyle2 || this.dividerStyle != synthStyle1) {
/*     */ 
/*     */       
/* 155 */       if (this.divider != null) {
/* 156 */         paramJSplitPane.remove(this.divider);
/*     */       }
/* 158 */       this.divider = createDefaultDivider();
/* 159 */       this.divider.setBasicSplitPaneUI(this);
/* 160 */       paramJSplitPane.add(this.divider, "divider");
/*     */     } 
/* 162 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/* 170 */     super.installListeners();
/* 171 */     this.splitPane.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 179 */     SynthContext synthContext = getContext(this.splitPane, 1);
/*     */     
/* 181 */     this.style.uninstallDefaults(synthContext);
/* 182 */     synthContext.dispose();
/* 183 */     this.style = null;
/*     */     
/* 185 */     synthContext = getContext(this.splitPane, Region.SPLIT_PANE_DIVIDER, 1);
/* 186 */     this.dividerStyle.uninstallDefaults(synthContext);
/* 187 */     synthContext.dispose();
/* 188 */     this.dividerStyle = null;
/*     */     
/* 190 */     super.uninstallDefaults();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 199 */     super.uninstallListeners();
/* 200 */     this.splitPane.removePropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 208 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 212 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */   
/*     */   SynthContext getContext(JComponent paramJComponent, Region paramRegion) {
/* 216 */     return getContext(paramJComponent, paramRegion, getComponentState(paramJComponent, paramRegion));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, Region paramRegion, int paramInt) {
/* 220 */     if (paramRegion == Region.SPLIT_PANE_DIVIDER) {
/* 221 */       return SynthContext.getContext(paramJComponent, paramRegion, this.dividerStyle, paramInt);
/*     */     }
/* 223 */     return SynthContext.getContext(paramJComponent, paramRegion, this.style, paramInt);
/*     */   }
/*     */   
/*     */   private int getComponentState(JComponent paramJComponent, Region paramRegion) {
/* 227 */     int i = SynthLookAndFeel.getComponentState(paramJComponent);
/*     */     
/* 229 */     if (this.divider.isMouseOver()) {
/* 230 */       i |= 0x2;
/*     */     }
/* 232 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 240 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent)) {
/* 241 */       updateStyle((JSplitPane)paramPropertyChangeEvent.getSource());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicSplitPaneDivider createDefaultDivider() {
/* 250 */     SynthSplitPaneDivider synthSplitPaneDivider = new SynthSplitPaneDivider(this);
/*     */     
/* 252 */     synthSplitPaneDivider.setDividerSize(this.splitPane.getDividerSize());
/* 253 */     return synthSplitPaneDivider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component createDefaultNonContinuousLayoutDivider() {
/* 261 */     return new Canvas() {
/*     */         public void paint(Graphics param1Graphics) {
/* 263 */           SynthSplitPaneUI.this.paintDragDivider(param1Graphics, 0, 0, getWidth(), getHeight());
/*     */         }
/*     */       };
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
/* 282 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 284 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 285 */     synthContext.getPainter().paintSplitPaneBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 286 */         .getWidth(), paramJComponent.getHeight());
/* 287 */     paint(synthContext, paramGraphics);
/* 288 */     synthContext.dispose();
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
/* 302 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 304 */     paint(synthContext, paramGraphics);
/* 305 */     synthContext.dispose();
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
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 318 */     super.paint(paramGraphics, this.splitPane);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 327 */     paramSynthContext.getPainter().paintSplitPaneBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   private void paintDragDivider(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 331 */     SynthContext synthContext = getContext(this.splitPane, Region.SPLIT_PANE_DIVIDER);
/* 332 */     synthContext.setComponentState((synthContext.getComponentState() | 0x2) ^ 0x2 | 0x4);
/*     */     
/* 334 */     Shape shape = paramGraphics.getClip();
/* 335 */     paramGraphics.clipRect(paramInt1, paramInt2, paramInt3, paramInt4);
/* 336 */     synthContext.getPainter().paintSplitPaneDragDivider(synthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, this.splitPane
/* 337 */         .getOrientation());
/* 338 */     paramGraphics.setClip(shape);
/* 339 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishedPaintingChildren(JSplitPane paramJSplitPane, Graphics paramGraphics) {
/* 347 */     if (paramJSplitPane == this.splitPane && getLastDragLocation() != -1 && 
/* 348 */       !isContinuousLayout() && !this.draggingHW)
/* 349 */       if (paramJSplitPane.getOrientation() == 1) {
/* 350 */         paintDragDivider(paramGraphics, getLastDragLocation(), 0, this.dividerSize - 1, this.splitPane
/* 351 */             .getHeight() - 1);
/*     */       } else {
/* 353 */         paintDragDivider(paramGraphics, 0, getLastDragLocation(), this.splitPane
/* 354 */             .getWidth() - 1, this.dividerSize - 1);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthSplitPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */