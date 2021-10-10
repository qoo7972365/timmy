/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.MouseWheelListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.swing.BoundedRangeModel;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.ScrollPaneConstants;
/*      */ import javax.swing.Scrollable;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.ScrollPaneUI;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicScrollPaneUI
/*      */   extends ScrollPaneUI
/*      */   implements ScrollPaneConstants
/*      */ {
/*      */   protected JScrollPane scrollpane;
/*      */   protected ChangeListener vsbChangeListener;
/*      */   protected ChangeListener hsbChangeListener;
/*      */   protected ChangeListener viewportChangeListener;
/*      */   protected PropertyChangeListener spPropertyChangeListener;
/*      */   private MouseWheelListener mouseScrollListener;
/*   61 */   private int oldExtent = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener vsbPropertyChangeListener;
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener hsbPropertyChangeListener;
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler handler;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean setValueCalled = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*   84 */     return new BasicScrollPaneUI();
/*      */   }
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*   88 */     paramLazyActionMap.put(new Actions("scrollUp"));
/*   89 */     paramLazyActionMap.put(new Actions("scrollDown"));
/*   90 */     paramLazyActionMap.put(new Actions("scrollHome"));
/*   91 */     paramLazyActionMap.put(new Actions("scrollEnd"));
/*   92 */     paramLazyActionMap.put(new Actions("unitScrollUp"));
/*   93 */     paramLazyActionMap.put(new Actions("unitScrollDown"));
/*   94 */     paramLazyActionMap.put(new Actions("scrollLeft"));
/*   95 */     paramLazyActionMap.put(new Actions("scrollRight"));
/*   96 */     paramLazyActionMap.put(new Actions("unitScrollRight"));
/*   97 */     paramLazyActionMap.put(new Actions("unitScrollLeft"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  103 */     Border border = this.scrollpane.getViewportBorder();
/*  104 */     if (border != null) {
/*  105 */       Rectangle rectangle = this.scrollpane.getViewportBorderBounds();
/*  106 */       border.paintBorder(this.scrollpane, paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  115 */     return new Dimension(32767, 32767);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installDefaults(JScrollPane paramJScrollPane) {
/*  121 */     LookAndFeel.installBorder(paramJScrollPane, "ScrollPane.border");
/*  122 */     LookAndFeel.installColorsAndFont(paramJScrollPane, "ScrollPane.background", "ScrollPane.foreground", "ScrollPane.font");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  127 */     Border border = paramJScrollPane.getViewportBorder();
/*  128 */     if (border == null || border instanceof javax.swing.plaf.UIResource) {
/*  129 */       border = UIManager.getBorder("ScrollPane.viewportBorder");
/*  130 */       paramJScrollPane.setViewportBorder(border);
/*      */     } 
/*  132 */     LookAndFeel.installProperty(paramJScrollPane, "opaque", Boolean.TRUE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners(JScrollPane paramJScrollPane) {
/*  138 */     this.vsbChangeListener = createVSBChangeListener();
/*  139 */     this.vsbPropertyChangeListener = createVSBPropertyChangeListener();
/*  140 */     this.hsbChangeListener = createHSBChangeListener();
/*  141 */     this.hsbPropertyChangeListener = createHSBPropertyChangeListener();
/*  142 */     this.viewportChangeListener = createViewportChangeListener();
/*  143 */     this.spPropertyChangeListener = createPropertyChangeListener();
/*      */     
/*  145 */     JViewport jViewport = this.scrollpane.getViewport();
/*  146 */     JScrollBar jScrollBar1 = this.scrollpane.getVerticalScrollBar();
/*  147 */     JScrollBar jScrollBar2 = this.scrollpane.getHorizontalScrollBar();
/*      */     
/*  149 */     if (jViewport != null) {
/*  150 */       jViewport.addChangeListener(this.viewportChangeListener);
/*      */     }
/*  152 */     if (jScrollBar1 != null) {
/*  153 */       jScrollBar1.getModel().addChangeListener(this.vsbChangeListener);
/*  154 */       jScrollBar1.addPropertyChangeListener(this.vsbPropertyChangeListener);
/*      */     } 
/*  156 */     if (jScrollBar2 != null) {
/*  157 */       jScrollBar2.getModel().addChangeListener(this.hsbChangeListener);
/*  158 */       jScrollBar2.addPropertyChangeListener(this.hsbPropertyChangeListener);
/*      */     } 
/*      */     
/*  161 */     this.scrollpane.addPropertyChangeListener(this.spPropertyChangeListener);
/*      */     
/*  163 */     this.mouseScrollListener = createMouseWheelListener();
/*  164 */     this.scrollpane.addMouseWheelListener(this.mouseScrollListener);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions(JScrollPane paramJScrollPane) {
/*  169 */     InputMap inputMap = getInputMap(1);
/*      */ 
/*      */     
/*  172 */     SwingUtilities.replaceUIInputMap(paramJScrollPane, 1, inputMap);
/*      */ 
/*      */     
/*  175 */     LazyActionMap.installLazyActionMap(paramJScrollPane, BasicScrollPaneUI.class, "ScrollPane.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  180 */     if (paramInt == 1) {
/*  181 */       InputMap inputMap1 = (InputMap)DefaultLookup.get(this.scrollpane, this, "ScrollPane.ancestorInputMap");
/*      */       
/*      */       InputMap inputMap2;
/*      */       
/*  185 */       if (this.scrollpane.getComponentOrientation().isLeftToRight() || (
/*  186 */         inputMap2 = (InputMap)DefaultLookup.get(this.scrollpane, this, "ScrollPane.ancestorInputMap.RightToLeft")) == null)
/*      */       {
/*  188 */         return inputMap1;
/*      */       }
/*  190 */       inputMap2.setParent(inputMap1);
/*  191 */       return inputMap2;
/*      */     } 
/*      */     
/*  194 */     return null;
/*      */   }
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  198 */     this.scrollpane = (JScrollPane)paramJComponent;
/*  199 */     installDefaults(this.scrollpane);
/*  200 */     installListeners(this.scrollpane);
/*  201 */     installKeyboardActions(this.scrollpane);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults(JScrollPane paramJScrollPane) {
/*  206 */     LookAndFeel.uninstallBorder(this.scrollpane);
/*      */     
/*  208 */     if (this.scrollpane.getViewportBorder() instanceof javax.swing.plaf.UIResource) {
/*  209 */       this.scrollpane.setViewportBorder((Border)null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallListeners(JComponent paramJComponent) {
/*  215 */     JViewport jViewport = this.scrollpane.getViewport();
/*  216 */     JScrollBar jScrollBar1 = this.scrollpane.getVerticalScrollBar();
/*  217 */     JScrollBar jScrollBar2 = this.scrollpane.getHorizontalScrollBar();
/*      */     
/*  219 */     if (jViewport != null) {
/*  220 */       jViewport.removeChangeListener(this.viewportChangeListener);
/*      */     }
/*  222 */     if (jScrollBar1 != null) {
/*  223 */       jScrollBar1.getModel().removeChangeListener(this.vsbChangeListener);
/*  224 */       jScrollBar1.removePropertyChangeListener(this.vsbPropertyChangeListener);
/*      */     } 
/*  226 */     if (jScrollBar2 != null) {
/*  227 */       jScrollBar2.getModel().removeChangeListener(this.hsbChangeListener);
/*  228 */       jScrollBar2.removePropertyChangeListener(this.hsbPropertyChangeListener);
/*      */     } 
/*      */     
/*  231 */     this.scrollpane.removePropertyChangeListener(this.spPropertyChangeListener);
/*      */     
/*  233 */     if (this.mouseScrollListener != null) {
/*  234 */       this.scrollpane.removeMouseWheelListener(this.mouseScrollListener);
/*      */     }
/*      */     
/*  237 */     this.vsbChangeListener = null;
/*  238 */     this.hsbChangeListener = null;
/*  239 */     this.viewportChangeListener = null;
/*  240 */     this.spPropertyChangeListener = null;
/*  241 */     this.mouseScrollListener = null;
/*  242 */     this.handler = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions(JScrollPane paramJScrollPane) {
/*  247 */     SwingUtilities.replaceUIActionMap(paramJScrollPane, null);
/*  248 */     SwingUtilities.replaceUIInputMap(paramJScrollPane, 1, null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  254 */     uninstallDefaults(this.scrollpane);
/*  255 */     uninstallListeners(this.scrollpane);
/*  256 */     uninstallKeyboardActions(this.scrollpane);
/*  257 */     this.scrollpane = null;
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  261 */     if (this.handler == null) {
/*  262 */       this.handler = new Handler();
/*      */     }
/*  264 */     return this.handler;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void syncScrollPaneWithViewport() {
/*  269 */     JViewport jViewport1 = this.scrollpane.getViewport();
/*  270 */     JScrollBar jScrollBar1 = this.scrollpane.getVerticalScrollBar();
/*  271 */     JScrollBar jScrollBar2 = this.scrollpane.getHorizontalScrollBar();
/*  272 */     JViewport jViewport2 = this.scrollpane.getRowHeader();
/*  273 */     JViewport jViewport3 = this.scrollpane.getColumnHeader();
/*  274 */     boolean bool = this.scrollpane.getComponentOrientation().isLeftToRight();
/*      */     
/*  276 */     if (jViewport1 != null) {
/*  277 */       Dimension dimension1 = jViewport1.getExtentSize();
/*  278 */       Dimension dimension2 = jViewport1.getViewSize();
/*  279 */       Point point = jViewport1.getViewPosition();
/*      */       
/*  281 */       if (jScrollBar1 != null) {
/*  282 */         int i = dimension1.height;
/*  283 */         int j = dimension2.height;
/*  284 */         int k = Math.max(0, Math.min(point.y, j - i));
/*  285 */         jScrollBar1.setValues(k, i, 0, j);
/*      */       } 
/*      */       
/*  288 */       if (jScrollBar2 != null) {
/*  289 */         int k, i = dimension1.width;
/*  290 */         int j = dimension2.width;
/*      */ 
/*      */         
/*  293 */         if (bool) {
/*  294 */           k = Math.max(0, Math.min(point.x, j - i));
/*      */         } else {
/*  296 */           int m = jScrollBar2.getValue();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  301 */           if (this.setValueCalled && j - m == point.x) {
/*  302 */             k = Math.max(0, Math.min(j - i, m));
/*      */ 
/*      */             
/*  305 */             if (i != 0) {
/*  306 */               this.setValueCalled = false;
/*      */             }
/*      */           }
/*  309 */           else if (i > j) {
/*  310 */             point.x = j - i;
/*  311 */             jViewport1.setViewPosition(point);
/*  312 */             k = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  325 */             k = Math.max(0, Math.min(j - i, j - i - point.x));
/*  326 */             if (this.oldExtent > i) {
/*  327 */               k -= this.oldExtent - i;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  332 */         this.oldExtent = i;
/*  333 */         jScrollBar2.setValues(k, i, 0, j);
/*      */       } 
/*      */       
/*  336 */       if (jViewport2 != null) {
/*  337 */         Point point1 = jViewport2.getViewPosition();
/*  338 */         point1.y = (jViewport1.getViewPosition()).y;
/*  339 */         point1.x = 0;
/*  340 */         jViewport2.setViewPosition(point1);
/*      */       } 
/*      */       
/*  343 */       if (jViewport3 != null) {
/*  344 */         Point point1 = jViewport3.getViewPosition();
/*  345 */         if (bool) {
/*  346 */           point1.x = (jViewport1.getViewPosition()).x;
/*      */         } else {
/*  348 */           point1.x = Math.max(0, (jViewport1.getViewPosition()).x);
/*      */         } 
/*  350 */         point1.y = 0;
/*  351 */         jViewport3.setViewPosition(point1);
/*      */       } 
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
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  365 */     if (paramJComponent == null) {
/*  366 */       throw new NullPointerException("Component must be non-null");
/*      */     }
/*      */     
/*  369 */     if (paramInt1 < 0 || paramInt2 < 0) {
/*  370 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*      */     }
/*      */     
/*  373 */     JViewport jViewport1 = this.scrollpane.getViewport();
/*  374 */     Insets insets = this.scrollpane.getInsets();
/*  375 */     int i = insets.top;
/*  376 */     paramInt2 = paramInt2 - insets.top - insets.bottom;
/*  377 */     paramInt1 = paramInt1 - insets.left - insets.right;
/*  378 */     JViewport jViewport2 = this.scrollpane.getColumnHeader();
/*  379 */     if (jViewport2 != null && jViewport2.isVisible()) {
/*  380 */       Component component1 = jViewport2.getView();
/*  381 */       if (component1 != null && component1.isVisible()) {
/*      */         
/*  383 */         Dimension dimension1 = component1.getPreferredSize();
/*  384 */         int j = component1.getBaseline(dimension1.width, dimension1.height);
/*      */         
/*  386 */         if (j >= 0) {
/*  387 */           return i + j;
/*      */         }
/*      */       } 
/*  390 */       Dimension dimension = jViewport2.getPreferredSize();
/*  391 */       paramInt2 -= dimension.height;
/*  392 */       i += dimension.height;
/*      */     } 
/*  394 */     Component component = (jViewport1 == null) ? null : jViewport1.getView();
/*  395 */     if (component != null && component.isVisible() && component
/*  396 */       .getBaselineResizeBehavior() == Component.BaselineResizeBehavior.CONSTANT_ASCENT) {
/*      */       
/*  398 */       Border border = this.scrollpane.getViewportBorder();
/*  399 */       if (border != null) {
/*  400 */         Insets insets1 = border.getBorderInsets(this.scrollpane);
/*  401 */         i += insets1.top;
/*  402 */         paramInt2 = paramInt2 - insets1.top - insets1.bottom;
/*  403 */         paramInt1 = paramInt1 - insets1.left - insets1.right;
/*      */       } 
/*  405 */       if (component.getWidth() > 0 && component.getHeight() > 0) {
/*  406 */         Dimension dimension = component.getMinimumSize();
/*  407 */         paramInt1 = Math.max(dimension.width, component.getWidth());
/*  408 */         paramInt2 = Math.max(dimension.height, component.getHeight());
/*      */       } 
/*  410 */       if (paramInt1 > 0 && paramInt2 > 0) {
/*  411 */         int j = component.getBaseline(paramInt1, paramInt2);
/*  412 */         if (j > 0) {
/*  413 */           return i + j;
/*      */         }
/*      */       } 
/*      */     } 
/*  417 */     return -1;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/*  430 */     super.getBaselineResizeBehavior(paramJComponent);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  436 */     return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
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
/*      */   public class ViewportChangeHandler
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  452 */       BasicScrollPaneUI.this.getHandler().stateChanged(param1ChangeEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   protected ChangeListener createViewportChangeListener() {
/*  457 */     return getHandler();
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
/*      */   public class HSBChangeListener
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  474 */       BasicScrollPaneUI.this.getHandler().stateChanged(param1ChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener createHSBPropertyChangeListener() {
/*  483 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected ChangeListener createHSBChangeListener() {
/*  487 */     return getHandler();
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
/*      */   public class VSBChangeListener
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  504 */       BasicScrollPaneUI.this.getHandler().stateChanged(param1ChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PropertyChangeListener createVSBPropertyChangeListener() {
/*  514 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected ChangeListener createVSBChangeListener() {
/*  518 */     return getHandler();
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
/*      */   protected class MouseWheelHandler
/*      */     implements MouseWheelListener
/*      */   {
/*      */     public void mouseWheelMoved(MouseWheelEvent param1MouseWheelEvent) {
/*  550 */       BasicScrollPaneUI.this.getHandler().mouseWheelMoved(param1MouseWheelEvent);
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
/*      */   protected MouseWheelListener createMouseWheelListener() {
/*  565 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected void updateScrollBarDisplayPolicy(PropertyChangeEvent paramPropertyChangeEvent) {
/*  569 */     this.scrollpane.revalidate();
/*  570 */     this.scrollpane.repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateViewport(PropertyChangeEvent paramPropertyChangeEvent) {
/*  576 */     JViewport jViewport1 = (JViewport)paramPropertyChangeEvent.getOldValue();
/*  577 */     JViewport jViewport2 = (JViewport)paramPropertyChangeEvent.getNewValue();
/*      */     
/*  579 */     if (jViewport1 != null) {
/*  580 */       jViewport1.removeChangeListener(this.viewportChangeListener);
/*      */     }
/*      */     
/*  583 */     if (jViewport2 != null) {
/*  584 */       Point point = jViewport2.getViewPosition();
/*  585 */       if (this.scrollpane.getComponentOrientation().isLeftToRight()) {
/*  586 */         point.x = Math.max(point.x, 0);
/*      */       } else {
/*  588 */         int i = (jViewport2.getViewSize()).width;
/*  589 */         int j = (jViewport2.getExtentSize()).width;
/*  590 */         if (j > i) {
/*  591 */           point.x = i - j;
/*      */         } else {
/*  593 */           point.x = Math.max(0, Math.min(i - j, point.x));
/*      */         } 
/*      */       } 
/*  596 */       point.y = Math.max(point.y, 0);
/*  597 */       jViewport2.setViewPosition(point);
/*  598 */       jViewport2.addChangeListener(this.viewportChangeListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateRowHeader(PropertyChangeEvent paramPropertyChangeEvent) {
/*  605 */     JViewport jViewport = (JViewport)paramPropertyChangeEvent.getNewValue();
/*  606 */     if (jViewport != null) {
/*  607 */       JViewport jViewport1 = this.scrollpane.getViewport();
/*  608 */       Point point = jViewport.getViewPosition();
/*  609 */       point.y = (jViewport1 != null) ? (jViewport1.getViewPosition()).y : 0;
/*  610 */       jViewport.setViewPosition(point);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateColumnHeader(PropertyChangeEvent paramPropertyChangeEvent) {
/*  617 */     JViewport jViewport = (JViewport)paramPropertyChangeEvent.getNewValue();
/*  618 */     if (jViewport != null) {
/*  619 */       JViewport jViewport1 = this.scrollpane.getViewport();
/*  620 */       Point point = jViewport.getViewPosition();
/*  621 */       if (jViewport1 == null) {
/*  622 */         point.x = 0;
/*      */       }
/*  624 */       else if (this.scrollpane.getComponentOrientation().isLeftToRight()) {
/*  625 */         point.x = (jViewport1.getViewPosition()).x;
/*      */       } else {
/*  627 */         point.x = Math.max(0, (jViewport1.getViewPosition()).x);
/*      */       } 
/*      */       
/*  630 */       jViewport.setViewPosition(point);
/*  631 */       this.scrollpane.add(jViewport, "COLUMN_HEADER");
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateHorizontalScrollBar(PropertyChangeEvent paramPropertyChangeEvent) {
/*  636 */     updateScrollBar(paramPropertyChangeEvent, this.hsbChangeListener, this.hsbPropertyChangeListener);
/*      */   }
/*      */   
/*      */   private void updateVerticalScrollBar(PropertyChangeEvent paramPropertyChangeEvent) {
/*  640 */     updateScrollBar(paramPropertyChangeEvent, this.vsbChangeListener, this.vsbPropertyChangeListener);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateScrollBar(PropertyChangeEvent paramPropertyChangeEvent, ChangeListener paramChangeListener, PropertyChangeListener paramPropertyChangeListener) {
/*  645 */     JScrollBar jScrollBar = (JScrollBar)paramPropertyChangeEvent.getOldValue();
/*  646 */     if (jScrollBar != null) {
/*  647 */       if (paramChangeListener != null) {
/*  648 */         jScrollBar.getModel().removeChangeListener(paramChangeListener);
/*      */       }
/*  650 */       if (paramPropertyChangeListener != null) {
/*  651 */         jScrollBar.removePropertyChangeListener(paramPropertyChangeListener);
/*      */       }
/*      */     } 
/*  654 */     jScrollBar = (JScrollBar)paramPropertyChangeEvent.getNewValue();
/*  655 */     if (jScrollBar != null) {
/*  656 */       if (paramChangeListener != null) {
/*  657 */         jScrollBar.getModel().addChangeListener(paramChangeListener);
/*      */       }
/*  659 */       if (paramPropertyChangeListener != null) {
/*  660 */         jScrollBar.addPropertyChangeListener(paramPropertyChangeListener);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  675 */       BasicScrollPaneUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
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
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  705 */     return getHandler();
/*      */   }
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String SCROLL_UP = "scrollUp";
/*      */     private static final String SCROLL_DOWN = "scrollDown";
/*      */     private static final String SCROLL_HOME = "scrollHome";
/*      */     private static final String SCROLL_END = "scrollEnd";
/*      */     private static final String UNIT_SCROLL_UP = "unitScrollUp";
/*      */     private static final String UNIT_SCROLL_DOWN = "unitScrollDown";
/*      */     private static final String SCROLL_LEFT = "scrollLeft";
/*      */     private static final String SCROLL_RIGHT = "scrollRight";
/*      */     private static final String UNIT_SCROLL_LEFT = "unitScrollLeft";
/*      */     private static final String UNIT_SCROLL_RIGHT = "unitScrollRight";
/*      */     
/*      */     Actions(String param1String) {
/*  723 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  727 */       JScrollPane jScrollPane = (JScrollPane)param1ActionEvent.getSource();
/*  728 */       boolean bool = jScrollPane.getComponentOrientation().isLeftToRight();
/*  729 */       String str = getName();
/*      */       
/*  731 */       if (str == "scrollUp") {
/*  732 */         scroll(jScrollPane, 1, -1, true);
/*      */       }
/*  734 */       else if (str == "scrollDown") {
/*  735 */         scroll(jScrollPane, 1, 1, true);
/*      */       }
/*  737 */       else if (str == "scrollHome") {
/*  738 */         scrollHome(jScrollPane);
/*      */       }
/*  740 */       else if (str == "scrollEnd") {
/*  741 */         scrollEnd(jScrollPane);
/*      */       }
/*  743 */       else if (str == "unitScrollUp") {
/*  744 */         scroll(jScrollPane, 1, -1, false);
/*      */       }
/*  746 */       else if (str == "unitScrollDown") {
/*  747 */         scroll(jScrollPane, 1, 1, false);
/*      */       }
/*  749 */       else if (str == "scrollLeft") {
/*  750 */         scroll(jScrollPane, 0, bool ? -1 : 1, true);
/*      */       
/*      */       }
/*  753 */       else if (str == "scrollRight") {
/*  754 */         scroll(jScrollPane, 0, bool ? 1 : -1, true);
/*      */       
/*      */       }
/*  757 */       else if (str == "unitScrollLeft") {
/*  758 */         scroll(jScrollPane, 0, bool ? -1 : 1, false);
/*      */       
/*      */       }
/*  761 */       else if (str == "unitScrollRight") {
/*  762 */         scroll(jScrollPane, 0, bool ? 1 : -1, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void scrollEnd(JScrollPane param1JScrollPane) {
/*  768 */       JViewport jViewport = param1JScrollPane.getViewport();
/*      */       Component component;
/*  770 */       if (jViewport != null && (component = jViewport.getView()) != null) {
/*  771 */         Rectangle rectangle1 = jViewport.getViewRect();
/*  772 */         Rectangle rectangle2 = component.getBounds();
/*  773 */         if (param1JScrollPane.getComponentOrientation().isLeftToRight()) {
/*  774 */           jViewport.setViewPosition(new Point(rectangle2.width - rectangle1.width, rectangle2.height - rectangle1.height));
/*      */         } else {
/*      */           
/*  777 */           jViewport.setViewPosition(new Point(0, rectangle2.height - rectangle1.height));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void scrollHome(JScrollPane param1JScrollPane) {
/*  784 */       JViewport jViewport = param1JScrollPane.getViewport();
/*      */       Component component;
/*  786 */       if (jViewport != null && (component = jViewport.getView()) != null) {
/*  787 */         if (param1JScrollPane.getComponentOrientation().isLeftToRight()) {
/*  788 */           jViewport.setViewPosition(new Point(0, 0));
/*      */         } else {
/*  790 */           Rectangle rectangle1 = jViewport.getViewRect();
/*  791 */           Rectangle rectangle2 = component.getBounds();
/*  792 */           jViewport.setViewPosition(new Point(rectangle2.width - rectangle1.width, 0));
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private void scroll(JScrollPane param1JScrollPane, int param1Int1, int param1Int2, boolean param1Boolean) {
/*  799 */       JViewport jViewport = param1JScrollPane.getViewport();
/*      */       Component component;
/*  801 */       if (jViewport != null && (component = jViewport.getView()) != null) {
/*  802 */         byte b; Rectangle rectangle = jViewport.getViewRect();
/*  803 */         Dimension dimension = component.getSize();
/*      */ 
/*      */         
/*  806 */         if (component instanceof Scrollable) {
/*  807 */           if (param1Boolean)
/*      */           {
/*  809 */             b = ((Scrollable)component).getScrollableBlockIncrement(rectangle, param1Int1, param1Int2);
/*      */           }
/*      */           else
/*      */           {
/*  813 */             b = ((Scrollable)component).getScrollableUnitIncrement(rectangle, param1Int1, param1Int2);
/*      */           }
/*      */         
/*      */         }
/*  817 */         else if (param1Boolean) {
/*  818 */           if (param1Int1 == 1) {
/*  819 */             b = rectangle.height;
/*      */           } else {
/*      */             
/*  822 */             b = rectangle.width;
/*      */           } 
/*      */         } else {
/*      */           
/*  826 */           b = 10;
/*      */         } 
/*      */         
/*  829 */         if (param1Int1 == 1) {
/*  830 */           rectangle.y += b * param1Int2;
/*  831 */           if (rectangle.y + rectangle.height > dimension.height) {
/*  832 */             rectangle.y = Math.max(0, dimension.height - rectangle.height);
/*      */           }
/*  834 */           else if (rectangle.y < 0) {
/*  835 */             rectangle.y = 0;
/*      */           }
/*      */         
/*      */         }
/*  839 */         else if (param1JScrollPane.getComponentOrientation().isLeftToRight()) {
/*  840 */           rectangle.x += b * param1Int2;
/*  841 */           if (rectangle.x + rectangle.width > dimension.width) {
/*  842 */             rectangle.x = Math.max(0, dimension.width - rectangle.width);
/*  843 */           } else if (rectangle.x < 0) {
/*  844 */             rectangle.x = 0;
/*      */           } 
/*      */         } else {
/*  847 */           rectangle.x -= b * param1Int2;
/*  848 */           if (rectangle.width > dimension.width) {
/*  849 */             rectangle.x = dimension.width - rectangle.width;
/*      */           } else {
/*  851 */             rectangle.x = Math.max(0, Math.min(dimension.width - rectangle.width, rectangle.x));
/*      */           } 
/*      */         } 
/*      */         
/*  855 */         jViewport.setViewPosition(rectangle.getLocation());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class Handler
/*      */     implements ChangeListener, PropertyChangeListener, MouseWheelListener
/*      */   {
/*      */     public void mouseWheelMoved(MouseWheelEvent param1MouseWheelEvent) {
/*  866 */       if (BasicScrollPaneUI.this.scrollpane.isWheelScrollingEnabled() && param1MouseWheelEvent
/*  867 */         .getWheelRotation() != 0) {
/*  868 */         JScrollBar jScrollBar = BasicScrollPaneUI.this.scrollpane.getVerticalScrollBar();
/*  869 */         boolean bool1 = (param1MouseWheelEvent.getWheelRotation() < 0) ? true : true;
/*  870 */         boolean bool2 = true;
/*      */ 
/*      */         
/*  873 */         if (jScrollBar == null || !jScrollBar.isVisible() || param1MouseWheelEvent
/*  874 */           .isShiftDown()) {
/*  875 */           jScrollBar = BasicScrollPaneUI.this.scrollpane.getHorizontalScrollBar();
/*  876 */           if (jScrollBar == null || !jScrollBar.isVisible()) {
/*      */             return;
/*      */           }
/*  879 */           bool2 = false;
/*      */         } 
/*      */         
/*  882 */         param1MouseWheelEvent.consume();
/*      */         
/*  884 */         if (param1MouseWheelEvent.getScrollType() == 0) {
/*  885 */           JViewport jViewport = BasicScrollPaneUI.this.scrollpane.getViewport();
/*  886 */           if (jViewport == null)
/*  887 */             return;  Component component = jViewport.getView();
/*  888 */           int i = Math.abs(param1MouseWheelEvent.getUnitsToScroll());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  897 */           boolean bool = (Math.abs(param1MouseWheelEvent.getWheelRotation()) == 1) ? true : false;
/*      */ 
/*      */           
/*  900 */           Object object = jScrollBar.getClientProperty("JScrollBar.fastWheelScrolling");
/*      */           
/*  902 */           if (Boolean.TRUE == object && component instanceof Scrollable)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  914 */             Scrollable scrollable = (Scrollable)component;
/*  915 */             Rectangle rectangle = jViewport.getViewRect();
/*  916 */             int j = rectangle.x;
/*      */             
/*  918 */             boolean bool3 = component.getComponentOrientation().isLeftToRight();
/*  919 */             int k = jScrollBar.getMinimum();
/*      */             
/*  921 */             int m = jScrollBar.getMaximum() - jScrollBar.getModel().getExtent();
/*      */             
/*  923 */             if (bool) {
/*      */               
/*  925 */               int i1 = scrollable.getScrollableBlockIncrement(rectangle, bool2, bool1);
/*      */ 
/*      */               
/*  928 */               if (bool1) {
/*  929 */                 k = Math.max(k, jScrollBar
/*  930 */                     .getValue() - i1);
/*      */               } else {
/*      */                 
/*  933 */                 m = Math.min(m, jScrollBar
/*  934 */                     .getValue() + i1);
/*      */               } 
/*      */             } 
/*      */             int n;
/*  938 */             for (n = 0; n < i; n++) {
/*      */               
/*  940 */               int i1 = scrollable.getScrollableUnitIncrement(rectangle, bool2, bool1);
/*      */ 
/*      */ 
/*      */               
/*  944 */               if (bool2 == true) {
/*  945 */                 if (bool1) {
/*  946 */                   rectangle.y -= i1;
/*  947 */                   if (rectangle.y <= k) {
/*  948 */                     rectangle.y = k;
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } else {
/*  953 */                   rectangle.y += i1;
/*  954 */                   if (rectangle.y >= m) {
/*  955 */                     rectangle.y = m;
/*      */ 
/*      */ 
/*      */                     
/*      */                     break;
/*      */                   } 
/*      */                 } 
/*  962 */               } else if ((bool3 && !bool1) || (!bool3 && bool1)) {
/*      */                 
/*  964 */                 rectangle.x -= i1;
/*  965 */                 if (bool3 && 
/*  966 */                   rectangle.x < k) {
/*  967 */                   rectangle.x = k;
/*      */ 
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/*  973 */               } else if ((bool3 && !bool1) || (!bool3 && bool1)) {
/*      */                 
/*  975 */                 rectangle.x += i1;
/*  976 */                 if (bool3 && 
/*  977 */                   rectangle.x > m) {
/*  978 */                   rectangle.x = m;
/*      */ 
/*      */                   
/*      */                   break;
/*      */                 } 
/*      */               } else {
/*  984 */                 assert false : "Non-sensical ComponentOrientation / scroll direction";
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/*  989 */             if (bool2 == true) {
/*  990 */               jScrollBar.setValue(rectangle.y);
/*      */             
/*      */             }
/*  993 */             else if (bool3) {
/*  994 */               jScrollBar.setValue(rectangle.x);
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/* 1000 */               n = jScrollBar.getValue() - rectangle.x - j;
/*      */               
/* 1002 */               if (n < k) {
/* 1003 */                 n = k;
/*      */               }
/* 1005 */               else if (n > m) {
/* 1006 */                 n = m;
/*      */               } 
/* 1008 */               jScrollBar.setValue(n);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else
/*      */           {
/* 1015 */             BasicScrollBarUI.scrollByUnits(jScrollBar, bool1, i, bool);
/*      */           }
/*      */         
/*      */         }
/* 1019 */         else if (param1MouseWheelEvent.getScrollType() == 1) {
/*      */           
/* 1021 */           BasicScrollBarUI.scrollByBlock(jScrollBar, bool1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1030 */       JViewport jViewport = BasicScrollPaneUI.this.scrollpane.getViewport();
/*      */       
/* 1032 */       if (jViewport != null) {
/* 1033 */         if (param1ChangeEvent.getSource() == jViewport) {
/* 1034 */           BasicScrollPaneUI.this.syncScrollPaneWithViewport();
/*      */         } else {
/*      */           
/* 1037 */           JScrollBar jScrollBar = BasicScrollPaneUI.this.scrollpane.getHorizontalScrollBar();
/* 1038 */           if (jScrollBar != null && param1ChangeEvent.getSource() == jScrollBar.getModel()) {
/* 1039 */             hsbStateChanged(jViewport, param1ChangeEvent);
/*      */           } else {
/*      */             
/* 1042 */             JScrollBar jScrollBar1 = BasicScrollPaneUI.this.scrollpane.getVerticalScrollBar();
/* 1043 */             if (jScrollBar1 != null && param1ChangeEvent.getSource() == jScrollBar1.getModel()) {
/* 1044 */               vsbStateChanged(jViewport, param1ChangeEvent);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     private void vsbStateChanged(JViewport param1JViewport, ChangeEvent param1ChangeEvent) {
/* 1052 */       BoundedRangeModel boundedRangeModel = (BoundedRangeModel)param1ChangeEvent.getSource();
/* 1053 */       Point point = param1JViewport.getViewPosition();
/* 1054 */       point.y = boundedRangeModel.getValue();
/* 1055 */       param1JViewport.setViewPosition(point);
/*      */     }
/*      */     
/*      */     private void hsbStateChanged(JViewport param1JViewport, ChangeEvent param1ChangeEvent) {
/* 1059 */       BoundedRangeModel boundedRangeModel = (BoundedRangeModel)param1ChangeEvent.getSource();
/* 1060 */       Point point = param1JViewport.getViewPosition();
/* 1061 */       int i = boundedRangeModel.getValue();
/* 1062 */       if (BasicScrollPaneUI.this.scrollpane.getComponentOrientation().isLeftToRight()) {
/* 1063 */         point.x = i;
/*      */       } else {
/* 1065 */         int j = (param1JViewport.getViewSize()).width;
/* 1066 */         int k = (param1JViewport.getExtentSize()).width;
/* 1067 */         int m = point.x;
/*      */ 
/*      */ 
/*      */         
/* 1071 */         point.x = j - k - i;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1076 */         if (k == 0 && i != 0 && m == j) {
/* 1077 */           BasicScrollPaneUI.this.setValueCalled = true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1083 */         else if (k != 0 && m < 0 && point.x == 0) {
/* 1084 */           point.x += i;
/*      */         } 
/*      */       } 
/*      */       
/* 1088 */       param1JViewport.setViewPosition(point);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1099 */       if (param1PropertyChangeEvent.getSource() == BasicScrollPaneUI.this.scrollpane) {
/* 1100 */         scrollPanePropertyChange(param1PropertyChangeEvent);
/*      */       } else {
/*      */         
/* 1103 */         sbPropertyChange(param1PropertyChangeEvent);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void scrollPanePropertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1108 */       String str = param1PropertyChangeEvent.getPropertyName();
/*      */       
/* 1110 */       if (str == "verticalScrollBarDisplayPolicy") {
/* 1111 */         BasicScrollPaneUI.this.updateScrollBarDisplayPolicy(param1PropertyChangeEvent);
/*      */       }
/* 1113 */       else if (str == "horizontalScrollBarDisplayPolicy") {
/* 1114 */         BasicScrollPaneUI.this.updateScrollBarDisplayPolicy(param1PropertyChangeEvent);
/*      */       }
/* 1116 */       else if (str == "viewport") {
/* 1117 */         BasicScrollPaneUI.this.updateViewport(param1PropertyChangeEvent);
/*      */       }
/* 1119 */       else if (str == "rowHeader") {
/* 1120 */         BasicScrollPaneUI.this.updateRowHeader(param1PropertyChangeEvent);
/*      */       }
/* 1122 */       else if (str == "columnHeader") {
/* 1123 */         BasicScrollPaneUI.this.updateColumnHeader(param1PropertyChangeEvent);
/*      */       }
/* 1125 */       else if (str == "verticalScrollBar") {
/* 1126 */         BasicScrollPaneUI.this.updateVerticalScrollBar(param1PropertyChangeEvent);
/*      */       }
/* 1128 */       else if (str == "horizontalScrollBar") {
/* 1129 */         BasicScrollPaneUI.this.updateHorizontalScrollBar(param1PropertyChangeEvent);
/*      */       }
/* 1131 */       else if (str == "componentOrientation") {
/* 1132 */         BasicScrollPaneUI.this.scrollpane.revalidate();
/* 1133 */         BasicScrollPaneUI.this.scrollpane.repaint();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void sbPropertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1139 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1140 */       Object object = param1PropertyChangeEvent.getSource();
/*      */       
/* 1142 */       if ("model" == str) {
/* 1143 */         JScrollBar jScrollBar = BasicScrollPaneUI.this.scrollpane.getVerticalScrollBar();
/*      */         
/* 1145 */         BoundedRangeModel boundedRangeModel = (BoundedRangeModel)param1PropertyChangeEvent.getOldValue();
/* 1146 */         ChangeListener changeListener = null;
/*      */         
/* 1148 */         if (object == jScrollBar) {
/* 1149 */           changeListener = BasicScrollPaneUI.this.vsbChangeListener;
/*      */         }
/* 1151 */         else if (object == BasicScrollPaneUI.this.scrollpane.getHorizontalScrollBar()) {
/* 1152 */           jScrollBar = BasicScrollPaneUI.this.scrollpane.getHorizontalScrollBar();
/* 1153 */           changeListener = BasicScrollPaneUI.this.hsbChangeListener;
/*      */         } 
/* 1155 */         if (changeListener != null) {
/* 1156 */           if (boundedRangeModel != null) {
/* 1157 */             boundedRangeModel.removeChangeListener(changeListener);
/*      */           }
/* 1159 */           if (jScrollBar.getModel() != null) {
/* 1160 */             jScrollBar.getModel().addChangeListener(changeListener);
/*      */           }
/*      */         }
/*      */       
/* 1164 */       } else if ("componentOrientation" == str && 
/* 1165 */         object == BasicScrollPaneUI.this.scrollpane.getHorizontalScrollBar()) {
/* 1166 */         JScrollBar jScrollBar = BasicScrollPaneUI.this.scrollpane.getHorizontalScrollBar();
/* 1167 */         JViewport jViewport = BasicScrollPaneUI.this.scrollpane.getViewport();
/* 1168 */         Point point = jViewport.getViewPosition();
/* 1169 */         if (BasicScrollPaneUI.this.scrollpane.getComponentOrientation().isLeftToRight()) {
/* 1170 */           point.x = jScrollBar.getValue();
/*      */         } else {
/* 1172 */           point.x = (jViewport.getViewSize()).width - (jViewport.getExtentSize()).width - jScrollBar.getValue();
/*      */         } 
/* 1174 */         jViewport.setViewPosition(point);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicScrollPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */