/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.AWTEventListener;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.LayerUI;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JLayer<V extends Component>
/*     */   extends JComponent
/*     */   implements Scrollable, PropertyChangeListener, Accessible
/*     */ {
/*     */   private V view;
/*     */   private LayerUI<? super V> layerUI;
/*     */   private JPanel glassPane;
/*     */   private long eventMask;
/*     */   private transient boolean isPainting;
/*     */   private transient boolean isPaintingImmediately;
/* 163 */   private static final LayerEventController eventController = new LayerEventController();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JLayer() {
/* 174 */     this((V)null);
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
/*     */   public JLayer(V paramV) {
/* 186 */     this(paramV, new LayerUI<>());
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
/*     */   public JLayer(V paramV, LayerUI<V> paramLayerUI) {
/* 198 */     setGlassPane(createGlassPane());
/* 199 */     setView(paramV);
/* 200 */     setUI(paramLayerUI);
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
/*     */   public V getView() {
/* 213 */     return this.view;
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
/*     */   public void setView(V paramV) {
/* 225 */     V v = getView();
/* 226 */     if (v != null) {
/* 227 */       super.remove((Component)v);
/*     */     }
/* 229 */     if (paramV != null) {
/* 230 */       super.addImpl((Component)paramV, (Object)null, getComponentCount());
/*     */     }
/* 232 */     this.view = paramV;
/* 233 */     firePropertyChange("view", v, paramV);
/* 234 */     revalidate();
/* 235 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUI(LayerUI<? super V> paramLayerUI) {
/* 245 */     this.layerUI = paramLayerUI;
/* 246 */     setUI(paramLayerUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayerUI<? super V> getUI() {
/* 255 */     return this.layerUI;
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
/*     */   public JPanel getGlassPane() {
/* 268 */     return this.glassPane;
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
/*     */   public void setGlassPane(JPanel paramJPanel) {
/* 280 */     JPanel jPanel = getGlassPane();
/* 281 */     boolean bool = false;
/* 282 */     if (jPanel != null) {
/* 283 */       bool = jPanel.isVisible();
/* 284 */       super.remove(jPanel);
/*     */     } 
/* 286 */     if (paramJPanel != null) {
/* 287 */       AWTAccessor.getComponentAccessor().setMixingCutoutShape(paramJPanel, new Rectangle());
/*     */       
/* 289 */       paramJPanel.setVisible(bool);
/* 290 */       super.addImpl(paramJPanel, (Object)null, 0);
/*     */     } 
/* 292 */     this.glassPane = paramJPanel;
/* 293 */     firePropertyChange("glassPane", jPanel, paramJPanel);
/* 294 */     revalidate();
/* 295 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JPanel createGlassPane() {
/* 306 */     return new DefaultLayerGlassPane();
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
/*     */   public void setLayout(LayoutManager paramLayoutManager) {
/* 320 */     if (paramLayoutManager != null) {
/* 321 */       throw new IllegalArgumentException("JLayer.setLayout() not supported");
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
/*     */   public void setBorder(Border paramBorder) {
/* 338 */     if (paramBorder != null) {
/* 339 */       throw new IllegalArgumentException("JLayer.setBorder() not supported");
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
/*     */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 353 */     throw new UnsupportedOperationException("Adding components to JLayer is not supported, use setView() or setGlassPane() instead");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Component paramComponent) {
/* 362 */     if (paramComponent == null) {
/* 363 */       super.remove(paramComponent);
/* 364 */     } else if (paramComponent == getView()) {
/* 365 */       setView((V)null);
/* 366 */     } else if (paramComponent == getGlassPane()) {
/* 367 */       setGlassPane((JPanel)null);
/*     */     } else {
/* 369 */       super.remove(paramComponent);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 377 */     if (this.view != null) {
/* 378 */       setView((V)null);
/*     */     }
/* 380 */     if (this.glassPane != null) {
/* 381 */       setGlassPane((JPanel)null);
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
/*     */   protected boolean isPaintingOrigin() {
/* 393 */     return true;
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
/*     */   public void paintImmediately(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 407 */     if (!this.isPaintingImmediately && getUI() != null) {
/* 408 */       this.isPaintingImmediately = true;
/*     */       try {
/* 410 */         getUI().paintImmediately(paramInt1, paramInt2, paramInt3, paramInt4, this);
/*     */       } finally {
/* 412 */         this.isPaintingImmediately = false;
/*     */       } 
/*     */     } else {
/* 415 */       super.paintImmediately(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics paramGraphics) {
/* 425 */     if (!this.isPainting) {
/* 426 */       this.isPainting = true;
/*     */       try {
/* 428 */         super.paintComponent(paramGraphics);
/*     */       } finally {
/* 430 */         this.isPainting = false;
/*     */       } 
/*     */     } else {
/* 433 */       super.paint(paramGraphics);
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
/*     */   protected void paintComponent(Graphics paramGraphics) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptimizedDrawingEnabled() {
/* 457 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 464 */     if (getUI() != null) {
/* 465 */       getUI().applyPropertyChange(paramPropertyChangeEvent, this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayerEventMask(long paramLong) {
/* 506 */     long l = getLayerEventMask();
/* 507 */     this.eventMask = paramLong;
/* 508 */     firePropertyChange("layerEventMask", l, paramLong);
/* 509 */     if (paramLong != l) {
/* 510 */       disableEvents(l);
/* 511 */       enableEvents(this.eventMask);
/* 512 */       if (isDisplayable()) {
/* 513 */         eventController.updateAWTEventListener(l, paramLong);
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
/*     */   public long getLayerEventMask() {
/* 531 */     return this.eventMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 539 */     if (getUI() != null) {
/* 540 */       getUI().updateUI(this);
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
/*     */   public Dimension getPreferredScrollableViewportSize() {
/* 555 */     if (getView() instanceof Scrollable) {
/* 556 */       return ((Scrollable)getView()).getPreferredScrollableViewportSize();
/*     */     }
/* 558 */     return getPreferredSize();
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
/*     */   public int getScrollableBlockIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 575 */     if (getView() instanceof Scrollable) {
/* 576 */       return ((Scrollable)getView()).getScrollableBlockIncrement(paramRectangle, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 579 */     return (paramInt1 == 1) ? paramRectangle.height : paramRectangle.width;
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
/*     */   public boolean getScrollableTracksViewportHeight() {
/* 596 */     if (getView() instanceof Scrollable) {
/* 597 */       return ((Scrollable)getView()).getScrollableTracksViewportHeight();
/*     */     }
/* 599 */     return false;
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
/*     */   public boolean getScrollableTracksViewportWidth() {
/* 615 */     if (getView() instanceof Scrollable) {
/* 616 */       return ((Scrollable)getView()).getScrollableTracksViewportWidth();
/*     */     }
/* 618 */     return false;
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
/*     */   public int getScrollableUnitIncrement(Rectangle paramRectangle, int paramInt1, int paramInt2) {
/* 641 */     if (getView() instanceof Scrollable) {
/* 642 */       return ((Scrollable)getView()).getScrollableUnitIncrement(paramRectangle, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 645 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 650 */     paramObjectInputStream.defaultReadObject();
/* 651 */     if (this.layerUI != null) {
/* 652 */       setUI(this.layerUI);
/*     */     }
/* 654 */     if (this.eventMask != 0L) {
/* 655 */       eventController.updateAWTEventListener(0L, this.eventMask);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 663 */     super.addNotify();
/* 664 */     eventController.updateAWTEventListener(0L, this.eventMask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 671 */     super.removeNotify();
/* 672 */     eventController.updateAWTEventListener(this.eventMask, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doLayout() {
/* 680 */     if (getUI() != null) {
/* 681 */       getUI().doLayout(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleContext getAccessibleContext() {
/* 691 */     if (this.accessibleContext == null) {
/* 692 */       this.accessibleContext = new JComponent.AccessibleJComponent() {
/*     */           public AccessibleRole getAccessibleRole() {
/* 694 */             return AccessibleRole.PANEL;
/*     */           }
/*     */         };
/*     */     }
/* 698 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LayerEventController
/*     */     implements AWTEventListener
/*     */   {
/* 705 */     private ArrayList<Long> layerMaskList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long currentEventMask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final long ACCEPTED_EVENTS = 231487L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void eventDispatched(AWTEvent param1AWTEvent) {
/* 724 */       Object object = param1AWTEvent.getSource();
/* 725 */       if (object instanceof Component) {
/* 726 */         Component component = (Component)object;
/* 727 */         while (component != null) {
/* 728 */           if (component instanceof JLayer) {
/* 729 */             JLayer jLayer = (JLayer)component;
/* 730 */             LayerUI layerUI = jLayer.getUI();
/* 731 */             if (layerUI != null && 
/* 732 */               isEventEnabled(jLayer.getLayerEventMask(), param1AWTEvent.getID()) && (!(param1AWTEvent instanceof InputEvent) || 
/* 733 */               !((InputEvent)param1AWTEvent).isConsumed())) {
/* 734 */               layerUI.eventDispatched(param1AWTEvent, jLayer);
/*     */             }
/*     */           } 
/* 737 */           component = component.getParent();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void updateAWTEventListener(long param1Long1, long param1Long2) {
/* 743 */       if (param1Long1 != 0L) {
/* 744 */         this.layerMaskList.remove(Long.valueOf(param1Long1));
/*     */       }
/* 746 */       if (param1Long2 != 0L) {
/* 747 */         this.layerMaskList.add(Long.valueOf(param1Long2));
/*     */       }
/* 749 */       long l = 0L;
/* 750 */       for (Long long_ : this.layerMaskList) {
/* 751 */         l |= long_.longValue();
/*     */       }
/*     */       
/* 754 */       l &= 0x3883FL;
/* 755 */       if (l == 0L) {
/* 756 */         removeAWTEventListener();
/* 757 */       } else if (getCurrentEventMask() != l) {
/* 758 */         removeAWTEventListener();
/* 759 */         addAWTEventListener(l);
/*     */       } 
/* 761 */       this.currentEventMask = l;
/*     */     }
/*     */     
/*     */     private long getCurrentEventMask() {
/* 765 */       return this.currentEventMask;
/*     */     }
/*     */     
/*     */     private void addAWTEventListener(final long eventMask) {
/* 769 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 771 */               Toolkit.getDefaultToolkit()
/* 772 */                 .addAWTEventListener(JLayer.LayerEventController.this, eventMask);
/* 773 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     private void removeAWTEventListener() {
/* 780 */       AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */             public Void run() {
/* 782 */               Toolkit.getDefaultToolkit()
/* 783 */                 .removeAWTEventListener(JLayer.LayerEventController.this);
/* 784 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/*     */     private boolean isEventEnabled(long param1Long, int param1Int) {
/* 790 */       return (((param1Long & 0x1L) != 0L && param1Int >= 100 && param1Int <= 103) || ((param1Long & 0x2L) != 0L && param1Int >= 300 && param1Int <= 301) || ((param1Long & 0x4L) != 0L && param1Int >= 1004 && param1Int <= 1005) || ((param1Long & 0x8L) != 0L && param1Int >= 400 && param1Int <= 402) || ((param1Long & 0x20000L) != 0L && param1Int == 507) || ((param1Long & 0x20L) != 0L && (param1Int == 503 || param1Int == 506)) || ((param1Long & 0x10L) != 0L && param1Int != 503 && param1Int != 506 && param1Int != 507 && param1Int >= 500 && param1Int <= 507) || ((param1Long & 0x800L) != 0L && param1Int >= 1100 && param1Int <= 1101) || ((param1Long & 0x8000L) != 0L && param1Int == 1400) || ((param1Long & 0x10000L) != 0L && (param1Int == 1401 || param1Int == 1402)));
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
/*     */     private LayerEventController() {}
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
/*     */   private static class DefaultLayerGlassPane
/*     */     extends JPanel
/*     */   {
/*     */     public DefaultLayerGlassPane() {
/* 833 */       setOpaque(false);
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
/*     */     public boolean contains(int param1Int1, int param1Int2) {
/* 849 */       for (byte b = 0; b < getComponentCount(); b++) {
/* 850 */         Component component = getComponent(b);
/* 851 */         Point point = SwingUtilities.convertPoint(this, new Point(param1Int1, param1Int2), component);
/* 852 */         if (component.isVisible() && component.contains(point)) {
/* 853 */           return true;
/*     */         }
/*     */       } 
/* 856 */       if ((getMouseListeners()).length == 0 && (
/* 857 */         getMouseMotionListeners()).length == 0 && (
/* 858 */         getMouseWheelListeners()).length == 0 && 
/* 859 */         !isCursorSet()) {
/* 860 */         return false;
/*     */       }
/* 862 */       return super.contains(param1Int1, param1Int2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */