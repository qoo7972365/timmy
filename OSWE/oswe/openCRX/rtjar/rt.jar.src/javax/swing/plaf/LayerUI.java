/*     */ package javax.swing.plaf;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.HierarchyEvent;
/*     */ import java.awt.event.InputMethodEvent;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLayer;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayerUI<V extends Component>
/*     */   extends ComponentUI
/*     */   implements Serializable
/*     */ {
/*  64 */   private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  79 */     paramJComponent.paint(paramGraphics);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void eventDispatched(AWTEvent paramAWTEvent, JLayer<? extends V> paramJLayer) {
/* 112 */     if (paramAWTEvent instanceof FocusEvent) {
/* 113 */       processFocusEvent((FocusEvent)paramAWTEvent, paramJLayer);
/*     */     }
/* 115 */     else if (paramAWTEvent instanceof MouseEvent) {
/* 116 */       switch (paramAWTEvent.getID()) {
/*     */         case 500:
/*     */         case 501:
/*     */         case 502:
/*     */         case 504:
/*     */         case 505:
/* 122 */           processMouseEvent((MouseEvent)paramAWTEvent, paramJLayer);
/*     */           break;
/*     */         case 503:
/*     */         case 506:
/* 126 */           processMouseMotionEvent((MouseEvent)paramAWTEvent, paramJLayer);
/*     */           break;
/*     */         case 507:
/* 129 */           processMouseWheelEvent((MouseWheelEvent)paramAWTEvent, paramJLayer);
/*     */           break;
/*     */       } 
/* 132 */     } else if (paramAWTEvent instanceof KeyEvent) {
/* 133 */       processKeyEvent((KeyEvent)paramAWTEvent, paramJLayer);
/* 134 */     } else if (paramAWTEvent instanceof ComponentEvent) {
/* 135 */       processComponentEvent((ComponentEvent)paramAWTEvent, paramJLayer);
/* 136 */     } else if (paramAWTEvent instanceof InputMethodEvent) {
/* 137 */       processInputMethodEvent((InputMethodEvent)paramAWTEvent, paramJLayer);
/* 138 */     } else if (paramAWTEvent instanceof HierarchyEvent) {
/* 139 */       switch (paramAWTEvent.getID()) {
/*     */         case 1400:
/* 141 */           processHierarchyEvent((HierarchyEvent)paramAWTEvent, paramJLayer);
/*     */           break;
/*     */         case 1401:
/*     */         case 1402:
/* 145 */           processHierarchyBoundsEvent((HierarchyEvent)paramAWTEvent, paramJLayer);
/*     */           break;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processComponentEvent(ComponentEvent paramComponentEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processFocusEvent(FocusEvent paramFocusEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processKeyEvent(KeyEvent paramKeyEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processMouseEvent(MouseEvent paramMouseEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processMouseMotionEvent(MouseEvent paramMouseEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processInputMethodEvent(InputMethodEvent paramInputMethodEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processHierarchyEvent(HierarchyEvent paramHierarchyEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processHierarchyBoundsEvent(HierarchyEvent paramHierarchyEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI(JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 456 */     addPropertyChangeListener((JLayer)paramJComponent);
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
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 468 */     removePropertyChangeListener((JLayer)paramJComponent);
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
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 484 */     this.propertyChangeSupport.addPropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 501 */     this.propertyChangeSupport.removePropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public PropertyChangeListener[] getPropertyChangeListeners() {
/* 516 */     return this.propertyChangeSupport.getPropertyChangeListeners();
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
/*     */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 534 */     this.propertyChangeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 554 */     this.propertyChangeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
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
/*     */   public PropertyChangeListener[] getPropertyChangeListeners(String paramString) {
/* 571 */     return this.propertyChangeSupport.getPropertyChangeListeners(paramString);
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
/*     */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 586 */     this.propertyChangeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
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
/*     */   public void applyPropertyChange(PropertyChangeEvent paramPropertyChangeEvent, JLayer<? extends V> paramJLayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/* 612 */     JLayer<V> jLayer = (JLayer)paramJComponent;
/* 613 */     if (jLayer.getView() != null) {
/* 614 */       return jLayer.getView().getBaseline(paramInt1, paramInt2);
/*     */     }
/* 616 */     return super.getBaseline(paramJComponent, paramInt1, paramInt2);
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
/*     */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/* 629 */     JLayer<V> jLayer = (JLayer)paramJComponent;
/* 630 */     if (jLayer.getView() != null) {
/* 631 */       return jLayer.getView().getBaselineResizeBehavior();
/*     */     }
/* 633 */     return super.getBaselineResizeBehavior(paramJComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLayout(JLayer<? extends V> paramJLayer) {
/* 642 */     V v = paramJLayer.getView();
/* 643 */     if (v != null) {
/* 644 */       v.setBounds(0, 0, paramJLayer.getWidth(), paramJLayer.getHeight());
/*     */     }
/* 646 */     JPanel jPanel = paramJLayer.getGlassPane();
/* 647 */     if (jPanel != null) {
/* 648 */       jPanel.setBounds(0, 0, paramJLayer.getWidth(), paramJLayer.getHeight());
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
/*     */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 661 */     JLayer<Object> jLayer = (JLayer)paramJComponent;
/* 662 */     Component component = (Component)jLayer.getView();
/* 663 */     if (component != null) {
/* 664 */       return component.getPreferredSize();
/*     */     }
/* 666 */     return super.getPreferredSize(paramJComponent);
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
/*     */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 678 */     JLayer<Object> jLayer = (JLayer)paramJComponent;
/* 679 */     Component component = (Component)jLayer.getView();
/* 680 */     if (component != null) {
/* 681 */       return component.getMinimumSize();
/*     */     }
/* 683 */     return super.getMinimumSize(paramJComponent);
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
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 695 */     JLayer<Object> jLayer = (JLayer)paramJComponent;
/* 696 */     Component component = (Component)jLayer.getView();
/* 697 */     if (component != null) {
/* 698 */       return component.getMaximumSize();
/*     */     }
/* 700 */     return super.getMaximumSize(paramJComponent);
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
/*     */   public void paintImmediately(int paramInt1, int paramInt2, int paramInt3, int paramInt4, JLayer<? extends V> paramJLayer) {
/* 717 */     paramJLayer.paintImmediately(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/LayerUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */