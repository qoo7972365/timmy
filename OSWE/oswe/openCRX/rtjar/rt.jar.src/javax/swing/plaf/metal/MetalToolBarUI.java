/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ContainerListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicToolBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetalToolBarUI
/*     */   extends BasicToolBarUI
/*     */ {
/*  64 */   private static List<WeakReference<JComponent>> components = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ContainerListener contListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PropertyChangeListener rolloverListener;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Border nonRolloverBorder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JMenuBar lastMenuBar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void register(JComponent paramJComponent) {
/*  93 */     if (paramJComponent == null)
/*     */     {
/*     */       
/*  96 */       throw new NullPointerException("JComponent must be non-null");
/*     */     }
/*  98 */     components.add(new WeakReference<>(paramJComponent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized void unregister(JComponent paramJComponent) {
/* 105 */     for (int i = components.size() - 1; i >= 0; i--) {
/*     */ 
/*     */       
/* 108 */       JComponent jComponent = ((WeakReference<JComponent>)components.get(i)).get();
/*     */       
/* 110 */       if (jComponent == paramJComponent || jComponent == null) {
/* 111 */         components.remove(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized Object findRegisteredComponentOfType(JComponent paramJComponent, Class paramClass) {
/* 122 */     JRootPane jRootPane = SwingUtilities.getRootPane(paramJComponent);
/* 123 */     if (jRootPane != null) {
/* 124 */       for (int i = components.size() - 1; i >= 0; i--) {
/*     */         
/* 126 */         Component component = (Component)((WeakReference<Object>)components.get(i)).get();
/*     */         
/* 128 */         if (component == null) {
/*     */           
/* 130 */           components.remove(i);
/*     */         }
/* 132 */         else if (paramClass.isInstance(component) && 
/* 133 */           SwingUtilities.getRootPane(component) == jRootPane) {
/* 134 */           return component;
/*     */         } 
/*     */       } 
/*     */     }
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean doesMenuBarBorderToolBar(JMenuBar paramJMenuBar) {
/* 147 */     JToolBar jToolBar = (JToolBar)findRegisteredComponentOfType(paramJMenuBar, JToolBar.class);
/* 148 */     if (jToolBar != null && jToolBar.getOrientation() == 0) {
/* 149 */       JRootPane jRootPane = SwingUtilities.getRootPane(paramJMenuBar);
/* 150 */       Point point = new Point(0, 0);
/* 151 */       point = SwingUtilities.convertPoint(paramJMenuBar, point, jRootPane);
/* 152 */       int i = point.x;
/* 153 */       int j = point.y;
/* 154 */       point.x = point.y = 0;
/* 155 */       point = SwingUtilities.convertPoint(jToolBar, point, jRootPane);
/* 156 */       return (point.x == i && j + paramJMenuBar.getHeight() == point.y && paramJMenuBar
/* 157 */         .getWidth() == jToolBar.getWidth());
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 164 */     return new MetalToolBarUI();
/*     */   }
/*     */ 
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/* 169 */     super.installUI(paramJComponent);
/* 170 */     register(paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/* 175 */     super.uninstallUI(paramJComponent);
/* 176 */     nonRolloverBorder = null;
/* 177 */     unregister(paramJComponent);
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/* 181 */     super.installListeners();
/*     */     
/* 183 */     this.contListener = createContainerListener();
/* 184 */     if (this.contListener != null) {
/* 185 */       this.toolBar.addContainerListener(this.contListener);
/*     */     }
/* 187 */     this.rolloverListener = createRolloverListener();
/* 188 */     if (this.rolloverListener != null) {
/* 189 */       this.toolBar.addPropertyChangeListener(this.rolloverListener);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 194 */     super.uninstallListeners();
/*     */     
/* 196 */     if (this.contListener != null) {
/* 197 */       this.toolBar.removeContainerListener(this.contListener);
/*     */     }
/* 199 */     this.rolloverListener = createRolloverListener();
/* 200 */     if (this.rolloverListener != null) {
/* 201 */       this.toolBar.removePropertyChangeListener(this.rolloverListener);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Border createRolloverBorder() {
/* 206 */     return super.createRolloverBorder();
/*     */   }
/*     */   
/*     */   protected Border createNonRolloverBorder() {
/* 210 */     return super.createNonRolloverBorder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Border createNonRolloverToggleBorder() {
/* 218 */     return createNonRolloverBorder();
/*     */   }
/*     */   
/*     */   protected void setBorderToNonRollover(Component paramComponent) {
/* 222 */     if (paramComponent instanceof JToggleButton && !(paramComponent instanceof javax.swing.JCheckBox)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       JToggleButton jToggleButton = (JToggleButton)paramComponent;
/* 232 */       Border border = jToggleButton.getBorder();
/* 233 */       super.setBorderToNonRollover(paramComponent);
/* 234 */       if (border instanceof javax.swing.plaf.UIResource) {
/* 235 */         if (nonRolloverBorder == null) {
/* 236 */           nonRolloverBorder = createNonRolloverToggleBorder();
/*     */         }
/* 238 */         jToggleButton.setBorder(nonRolloverBorder);
/*     */       } 
/*     */     } else {
/* 241 */       super.setBorderToNonRollover(paramComponent);
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
/*     */   protected ContainerListener createContainerListener() {
/* 254 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PropertyChangeListener createRolloverListener() {
/* 265 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MouseInputListener createDockingListener() {
/* 270 */     return new MetalDockingListener(this.toolBar);
/*     */   }
/*     */   
/*     */   protected void setDragOffset(Point paramPoint) {
/* 274 */     if (!GraphicsEnvironment.isHeadless()) {
/* 275 */       if (this.dragWindow == null) {
/* 276 */         this.dragWindow = createDragWindow(this.toolBar);
/*     */       }
/* 278 */       this.dragWindow.setOffset(paramPoint);
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 295 */     if (paramGraphics == null) {
/* 296 */       throw new NullPointerException("graphics must be non-null");
/*     */     }
/* 298 */     if (paramJComponent.isOpaque() && paramJComponent.getBackground() instanceof javax.swing.plaf.UIResource && ((JToolBar)paramJComponent)
/* 299 */       .getOrientation() == 0 && 
/* 300 */       UIManager.get("MenuBar.gradient") != null) {
/*     */       
/* 302 */       JRootPane jRootPane = SwingUtilities.getRootPane(paramJComponent);
/* 303 */       JMenuBar jMenuBar = (JMenuBar)findRegisteredComponentOfType(paramJComponent, JMenuBar.class);
/*     */       
/* 305 */       if (jMenuBar != null && jMenuBar.isOpaque() && jMenuBar
/* 306 */         .getBackground() instanceof javax.swing.plaf.UIResource) {
/* 307 */         Point point = new Point(0, 0);
/* 308 */         point = SwingUtilities.convertPoint(paramJComponent, point, jRootPane);
/* 309 */         int i = point.x;
/* 310 */         int j = point.y;
/* 311 */         point.x = point.y = 0;
/* 312 */         point = SwingUtilities.convertPoint(jMenuBar, point, jRootPane);
/* 313 */         if (point.x == i && j == point.y + jMenuBar.getHeight() && jMenuBar
/* 314 */           .getWidth() == paramJComponent.getWidth() && 
/* 315 */           MetalUtils.drawGradient(paramJComponent, paramGraphics, "MenuBar.gradient", 0, 
/* 316 */             -jMenuBar.getHeight(), paramJComponent.getWidth(), paramJComponent.getHeight() + jMenuBar
/* 317 */             .getHeight(), true)) {
/* 318 */           setLastMenuBar(jMenuBar);
/* 319 */           paint(paramGraphics, paramJComponent);
/*     */           return;
/*     */         } 
/*     */       } 
/* 323 */       if (MetalUtils.drawGradient(paramJComponent, paramGraphics, "MenuBar.gradient", 0, 0, paramJComponent
/* 324 */           .getWidth(), paramJComponent.getHeight(), true)) {
/* 325 */         setLastMenuBar((JMenuBar)null);
/* 326 */         paint(paramGraphics, paramJComponent);
/*     */         return;
/*     */       } 
/*     */     } 
/* 330 */     setLastMenuBar((JMenuBar)null);
/* 331 */     super.update(paramGraphics, paramJComponent);
/*     */   }
/*     */   
/*     */   private void setLastMenuBar(JMenuBar paramJMenuBar) {
/* 335 */     if (MetalLookAndFeel.usingOcean() && 
/* 336 */       this.lastMenuBar != paramJMenuBar) {
/*     */ 
/*     */       
/* 339 */       if (this.lastMenuBar != null) {
/* 340 */         this.lastMenuBar.repaint();
/*     */       }
/* 342 */       if (paramJMenuBar != null) {
/* 343 */         paramJMenuBar.repaint();
/*     */       }
/* 345 */       this.lastMenuBar = paramJMenuBar;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected class MetalContainerListener
/*     */     extends BasicToolBarUI.ToolBarContListener {}
/*     */   
/*     */   protected class MetalRolloverListener
/*     */     extends BasicToolBarUI.PropertyListener {}
/*     */   
/*     */   protected class MetalDockingListener
/*     */     extends BasicToolBarUI.DockingListener
/*     */   {
/*     */     private boolean pressedInBumps = false;
/*     */     
/*     */     public MetalDockingListener(JToolBar param1JToolBar) {
/* 362 */       super(param1JToolBar);
/*     */     }
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 366 */       super.mousePressed(param1MouseEvent);
/* 367 */       if (!this.toolBar.isEnabled()) {
/*     */         return;
/*     */       }
/* 370 */       this.pressedInBumps = false;
/* 371 */       Rectangle rectangle = new Rectangle();
/*     */       
/* 373 */       if (this.toolBar.getOrientation() == 0) {
/* 374 */         boolean bool = MetalUtils.isLeftToRight(this.toolBar) ? false : ((this.toolBar.getSize()).width - 14);
/* 375 */         rectangle.setBounds(bool, 0, 14, (this.toolBar.getSize()).height);
/*     */       } else {
/* 377 */         rectangle.setBounds(0, 0, (this.toolBar.getSize()).width, 14);
/*     */       } 
/* 379 */       if (rectangle.contains(param1MouseEvent.getPoint())) {
/* 380 */         this.pressedInBumps = true;
/* 381 */         Point point = param1MouseEvent.getPoint();
/* 382 */         if (!MetalUtils.isLeftToRight(this.toolBar)) {
/* 383 */           point.x -= (this.toolBar.getSize()).width - 
/* 384 */             (this.toolBar.getPreferredSize()).width;
/*     */         }
/* 386 */         MetalToolBarUI.this.setDragOffset(point);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 391 */       if (this.pressedInBumps)
/* 392 */         super.mouseDragged(param1MouseEvent); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/MetalToolBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */