/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicScrollPaneUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalScrollPaneUI
/*     */   extends BasicScrollPaneUI
/*     */ {
/*     */   private PropertyChangeListener scrollBarSwapListener;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  59 */     return new MetalScrollPaneUI();
/*     */   }
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  64 */     super.installUI(paramJComponent);
/*     */     
/*  66 */     JScrollPane jScrollPane = (JScrollPane)paramJComponent;
/*  67 */     updateScrollbarsFreeStanding();
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  71 */     super.uninstallUI(paramJComponent);
/*     */     
/*  73 */     JScrollPane jScrollPane = (JScrollPane)paramJComponent;
/*  74 */     JScrollBar jScrollBar1 = jScrollPane.getHorizontalScrollBar();
/*  75 */     JScrollBar jScrollBar2 = jScrollPane.getVerticalScrollBar();
/*  76 */     if (jScrollBar1 != null) {
/*  77 */       jScrollBar1.putClientProperty("JScrollBar.isFreeStanding", (Object)null);
/*     */     }
/*  79 */     if (jScrollBar2 != null) {
/*  80 */       jScrollBar2.putClientProperty("JScrollBar.isFreeStanding", (Object)null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void installListeners(JScrollPane paramJScrollPane) {
/*  85 */     super.installListeners(paramJScrollPane);
/*  86 */     this.scrollBarSwapListener = createScrollBarSwapListener();
/*  87 */     paramJScrollPane.addPropertyChangeListener(this.scrollBarSwapListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners(JComponent paramJComponent) {
/*  94 */     super.uninstallListeners(paramJComponent);
/*  95 */     paramJComponent.removePropertyChangeListener(this.scrollBarSwapListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void uninstallListeners(JScrollPane paramJScrollPane) {
/* 103 */     super.uninstallListeners(paramJScrollPane);
/* 104 */     paramJScrollPane.removePropertyChangeListener(this.scrollBarSwapListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateScrollbarsFreeStanding() {
/*     */     Boolean bool;
/* 114 */     if (this.scrollpane == null) {
/*     */       return;
/*     */     }
/* 117 */     Border border = this.scrollpane.getBorder();
/*     */ 
/*     */     
/* 120 */     if (border instanceof MetalBorders.ScrollPaneBorder) {
/* 121 */       bool = Boolean.FALSE;
/*     */     } else {
/*     */       
/* 124 */       bool = Boolean.TRUE;
/*     */     } 
/* 126 */     JScrollBar jScrollBar = this.scrollpane.getHorizontalScrollBar();
/* 127 */     if (jScrollBar != null) {
/* 128 */       jScrollBar
/* 129 */         .putClientProperty("JScrollBar.isFreeStanding", bool);
/*     */     }
/* 131 */     jScrollBar = this.scrollpane.getVerticalScrollBar();
/* 132 */     if (jScrollBar != null) {
/* 133 */       jScrollBar
/* 134 */         .putClientProperty("JScrollBar.isFreeStanding", bool);
/*     */     }
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createScrollBarSwapListener() {
/* 139 */     return new PropertyChangeListener() {
/*     */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 141 */           String str = param1PropertyChangeEvent.getPropertyName();
/* 142 */           if (str.equals("verticalScrollBar") || str
/* 143 */             .equals("horizontalScrollBar")) {
/* 144 */             JScrollBar jScrollBar1 = (JScrollBar)param1PropertyChangeEvent.getOldValue();
/* 145 */             if (jScrollBar1 != null) {
/* 146 */               jScrollBar1.putClientProperty("JScrollBar.isFreeStanding", (Object)null);
/*     */             }
/*     */             
/* 149 */             JScrollBar jScrollBar2 = (JScrollBar)param1PropertyChangeEvent.getNewValue();
/* 150 */             if (jScrollBar2 != null) {
/* 151 */               jScrollBar2.putClientProperty("JScrollBar.isFreeStanding", Boolean.FALSE);
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 156 */           else if ("border".equals(str)) {
/* 157 */             MetalScrollPaneUI.this.updateScrollbarsFreeStanding();
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalScrollPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */