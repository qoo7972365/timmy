/*     */ package sun.swing;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.MouseInfo;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.awt.dnd.DragGestureEvent;
/*     */ import java.awt.dnd.DragGestureListener;
/*     */ import java.awt.dnd.DragSource;
/*     */ import java.awt.dnd.DropTarget;
/*     */ import java.awt.dnd.InvalidDnDOperationException;
/*     */ import java.awt.dnd.peer.DragSourceContextPeer;
/*     */ import java.awt.event.ContainerEvent;
/*     */ import java.awt.event.ContainerListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.security.AccessController;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JLayeredPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.LayoutFocusTraversalPolicy;
/*     */ import javax.swing.RepaintManager;
/*     */ import javax.swing.RootPaneContainer;
/*     */ import javax.swing.SwingUtilities;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.DisplayChangedListener;
/*     */ import sun.awt.LightweightFrame;
/*     */ import sun.awt.OverrideNativeWindowHandle;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JLightweightFrame
/*     */   extends LightweightFrame
/*     */   implements RootPaneContainer
/*     */ {
/*  83 */   private final JRootPane rootPane = new JRootPane();
/*     */   
/*     */   private LightweightContent content;
/*     */   
/*     */   private Component component;
/*     */   
/*     */   private JPanel contentPane;
/*     */   
/*     */   private BufferedImage bbImage;
/*  92 */   private volatile int scaleFactor = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 111 */     SwingAccessor.setJLightweightFrameAccessor(new SwingAccessor.JLightweightFrameAccessor()
/*     */         {
/*     */           public void updateCursor(JLightweightFrame param1JLightweightFrame) {
/* 114 */             param1JLightweightFrame.updateClientCursor();
/*     */           }
/*     */         });
/* 117 */   } private static boolean copyBufferEnabled = "true".equals(
/* 118 */       AccessController.doPrivileged(new GetPropertyAction("swing.jlf.copyBufferEnabled", "true")));
/*     */   
/*     */   private int[] copyBuffer;
/*     */   
/*     */   private PropertyChangeListener layoutSizeListener;
/*     */   
/*     */   private SwingUtilities2.RepaintListener repaintListener;
/*     */   
/*     */   public JLightweightFrame() {
/* 127 */     copyBufferEnabled = "true".equals(
/* 128 */         AccessController.doPrivileged(new GetPropertyAction("swing.jlf.copyBufferEnabled", "true")));
/*     */     
/* 130 */     add(this.rootPane, "Center");
/* 131 */     setFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
/* 132 */     if (getGraphicsConfiguration().isTranslucencyCapable()) {
/* 133 */       setBackground(new Color(0, 0, 0, 0));
/*     */     }
/*     */     
/* 136 */     this.layoutSizeListener = new PropertyChangeListener()
/*     */       {
/*     */         public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 139 */           Dimension dimension = (Dimension)param1PropertyChangeEvent.getNewValue();
/*     */           
/* 141 */           if ("preferredSize".equals(param1PropertyChangeEvent.getPropertyName())) {
/* 142 */             JLightweightFrame.this.content.preferredSizeChanged(dimension.width, dimension.height);
/*     */           }
/* 144 */           else if ("maximumSize".equals(param1PropertyChangeEvent.getPropertyName())) {
/* 145 */             JLightweightFrame.this.content.maximumSizeChanged(dimension.width, dimension.height);
/*     */           }
/* 147 */           else if ("minimumSize".equals(param1PropertyChangeEvent.getPropertyName())) {
/* 148 */             JLightweightFrame.this.content.minimumSizeChanged(dimension.width, dimension.height);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 153 */     this.repaintListener = ((paramJComponent, paramInt1, paramInt2, paramInt3, paramInt4) -> {
/*     */         Window window = SwingUtilities.getWindowAncestor(paramJComponent);
/*     */         
/*     */         if (window != this) {
/*     */           return;
/*     */         }
/*     */         
/*     */         Point point = SwingUtilities.convertPoint(paramJComponent, paramInt1, paramInt2, window);
/*     */         
/*     */         Rectangle rectangle = (new Rectangle(point.x, point.y, paramInt3, paramInt4)).intersection(new Rectangle(0, 0, this.bbImage.getWidth() / this.scaleFactor, this.bbImage.getHeight() / this.scaleFactor));
/*     */         
/*     */         if (!rectangle.isEmpty()) {
/*     */           notifyImageUpdated(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */         }
/*     */       });
/* 168 */     SwingAccessor.getRepaintManagerAccessor().addRepaintListener(
/* 169 */         RepaintManager.currentManager(this), this.repaintListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 174 */     SwingAccessor.getRepaintManagerAccessor().removeRepaintListener(
/* 175 */         RepaintManager.currentManager(this), this.repaintListener);
/* 176 */     super.dispose();
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
/*     */   public void setContent(LightweightContent paramLightweightContent) {
/* 188 */     if (paramLightweightContent == null) {
/* 189 */       System.err.println("JLightweightFrame.setContent: content may not be null!");
/*     */       return;
/*     */     } 
/* 192 */     this.content = paramLightweightContent;
/* 193 */     this.component = paramLightweightContent.getComponent();
/*     */     
/* 195 */     Dimension dimension = this.component.getPreferredSize();
/* 196 */     paramLightweightContent.preferredSizeChanged(dimension.width, dimension.height);
/*     */     
/* 198 */     dimension = this.component.getMaximumSize();
/* 199 */     paramLightweightContent.maximumSizeChanged(dimension.width, dimension.height);
/*     */     
/* 201 */     dimension = this.component.getMinimumSize();
/* 202 */     paramLightweightContent.minimumSizeChanged(dimension.width, dimension.height);
/*     */     
/* 204 */     initInterior();
/*     */   }
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() {
/* 209 */     if (this.bbImage == null) return null;
/*     */     
/* 211 */     Graphics2D graphics2D = this.bbImage.createGraphics();
/* 212 */     graphics2D.setBackground(getBackground());
/* 213 */     graphics2D.setColor(getForeground());
/* 214 */     graphics2D.setFont(getFont());
/* 215 */     graphics2D.scale(this.scaleFactor, this.scaleFactor);
/* 216 */     return graphics2D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void grabFocus() {
/* 226 */     if (this.content != null) this.content.focusGrabbed();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ungrabFocus() {
/* 236 */     if (this.content != null) this.content.focusUngrabbed();
/*     */   
/*     */   }
/*     */   
/*     */   public int getScaleFactor() {
/* 241 */     return this.scaleFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDisplayChanged(int paramInt) {
/* 246 */     if (paramInt != this.scaleFactor) {
/* 247 */       if (!copyBufferEnabled) this.content.paintLock(); 
/*     */       try {
/* 249 */         if (this.bbImage != null) {
/* 250 */           resizeBuffer(getWidth(), getHeight(), paramInt);
/*     */         }
/*     */       } finally {
/* 253 */         if (!copyBufferEnabled) this.content.paintUnlock(); 
/*     */       } 
/* 255 */       this.scaleFactor = paramInt;
/*     */     } 
/* 257 */     if (getPeer() instanceof DisplayChangedListener) {
/* 258 */       ((DisplayChangedListener)getPeer()).displayChanged();
/*     */     }
/* 260 */     repaint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 265 */     super.addNotify();
/* 266 */     if (getPeer() instanceof DisplayChangedListener) {
/* 267 */       ((DisplayChangedListener)getPeer()).displayChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   private void syncCopyBuffer(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 272 */     this.content.paintLock();
/*     */     try {
/* 274 */       int[] arrayOfInt = ((DataBufferInt)this.bbImage.getRaster().getDataBuffer()).getData();
/* 275 */       if (paramBoolean) {
/* 276 */         this.copyBuffer = new int[arrayOfInt.length];
/*     */       }
/* 278 */       int i = this.bbImage.getWidth();
/*     */       
/* 280 */       paramInt1 *= paramInt5;
/* 281 */       paramInt2 *= paramInt5;
/* 282 */       paramInt3 *= paramInt5;
/* 283 */       paramInt4 *= paramInt5;
/*     */       
/* 285 */       for (byte b = 0; b < paramInt4; b++) {
/* 286 */         int j = (paramInt2 + b) * i + paramInt1;
/* 287 */         System.arraycopy(arrayOfInt, j, this.copyBuffer, j, paramInt3);
/*     */       } 
/*     */     } finally {
/* 290 */       this.content.paintUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notifyImageUpdated(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 295 */     if (copyBufferEnabled) {
/* 296 */       syncCopyBuffer(false, paramInt1, paramInt2, paramInt3, paramInt4, this.scaleFactor);
/*     */     }
/* 298 */     this.content.imageUpdated(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */   
/*     */   private void initInterior() {
/* 302 */     this.contentPane = new JPanel()
/*     */       {
/*     */         public void paint(Graphics param1Graphics) {
/* 305 */           if (!JLightweightFrame.copyBufferEnabled) {
/* 306 */             JLightweightFrame.this.content.paintLock();
/*     */           }
/*     */           try {
/* 309 */             super.paint(param1Graphics);
/*     */ 
/*     */ 
/*     */             
/* 313 */             final Rectangle clip = (param1Graphics.getClipBounds() != null) ? param1Graphics.getClipBounds() : new Rectangle(0, 0, JLightweightFrame.this.contentPane.getWidth(), JLightweightFrame.this.contentPane.getHeight());
/*     */             
/* 315 */             rectangle.x = Math.max(0, rectangle.x);
/* 316 */             rectangle.y = Math.max(0, rectangle.y);
/* 317 */             rectangle.width = Math.min(JLightweightFrame.this.contentPane.getWidth(), rectangle.width);
/* 318 */             rectangle.height = Math.min(JLightweightFrame.this.contentPane.getHeight(), rectangle.height);
/*     */             
/* 320 */             EventQueue.invokeLater(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 323 */                     Rectangle rectangle = JLightweightFrame.this.contentPane.getBounds().intersection(clip);
/* 324 */                     JLightweightFrame.this.notifyImageUpdated(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */                   }
/*     */                 });
/*     */           } finally {
/* 328 */             if (!JLightweightFrame.copyBufferEnabled) {
/* 329 */               JLightweightFrame.this.content.paintUnlock();
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/*     */         protected boolean isPaintingOrigin() {
/* 335 */           return true;
/*     */         }
/*     */       };
/* 338 */     this.contentPane.setLayout(new BorderLayout());
/* 339 */     this.contentPane.add(this.component);
/* 340 */     if ("true".equals(
/* 341 */         AccessController.doPrivileged(new GetPropertyAction("swing.jlf.contentPaneTransparent", "false"))))
/*     */     {
/* 343 */       this.contentPane.setOpaque(false);
/*     */     }
/* 345 */     setContentPane(this.contentPane);
/*     */     
/* 347 */     this.contentPane.addContainerListener(new ContainerListener()
/*     */         {
/*     */           public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 350 */             Component component = JLightweightFrame.this.component;
/* 351 */             if (param1ContainerEvent.getChild() == component) {
/* 352 */               component.addPropertyChangeListener("preferredSize", JLightweightFrame.this.layoutSizeListener);
/* 353 */               component.addPropertyChangeListener("maximumSize", JLightweightFrame.this.layoutSizeListener);
/* 354 */               component.addPropertyChangeListener("minimumSize", JLightweightFrame.this.layoutSizeListener);
/*     */             } 
/*     */           }
/*     */           
/*     */           public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 359 */             Component component = JLightweightFrame.this.component;
/* 360 */             if (param1ContainerEvent.getChild() == component) {
/* 361 */               component.removePropertyChangeListener(JLightweightFrame.this.layoutSizeListener);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 369 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     
/* 371 */     if (paramInt3 == 0 || paramInt4 == 0) {
/*     */       return;
/*     */     }
/* 374 */     if (!copyBufferEnabled) {
/* 375 */       this.content.paintLock();
/*     */     }
/*     */     try {
/* 378 */       boolean bool = (this.bbImage == null) ? true : false;
/* 379 */       int i = paramInt3;
/* 380 */       int j = paramInt4;
/* 381 */       if (this.bbImage != null) {
/* 382 */         int k = this.bbImage.getWidth() / this.scaleFactor;
/* 383 */         int m = this.bbImage.getHeight() / this.scaleFactor;
/* 384 */         if (paramInt3 != k || paramInt4 != m) {
/* 385 */           bool = true;
/* 386 */           if (this.bbImage != null) {
/* 387 */             int n = k;
/* 388 */             int i1 = m;
/* 389 */             if (n >= i && i1 >= j) {
/* 390 */               bool = false;
/*     */             } else {
/* 392 */               if (n >= i) {
/* 393 */                 i = n;
/*     */               } else {
/* 395 */                 i = Math.max((int)(n * 1.2D), paramInt3);
/*     */               } 
/* 397 */               if (i1 >= j) {
/* 398 */                 j = i1;
/*     */               } else {
/* 400 */                 j = Math.max((int)(i1 * 1.2D), paramInt4);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 406 */       if (bool) {
/* 407 */         resizeBuffer(i, j, this.scaleFactor);
/*     */         return;
/*     */       } 
/* 410 */       this.content.imageReshaped(0, 0, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 413 */       if (!copyBufferEnabled) {
/* 414 */         this.content.paintUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resizeBuffer(int paramInt1, int paramInt2, int paramInt3) {
/* 420 */     this.bbImage = new BufferedImage(paramInt1 * paramInt3, paramInt2 * paramInt3, 3);
/*     */     
/* 422 */     int[] arrayOfInt = ((DataBufferInt)this.bbImage.getRaster().getDataBuffer()).getData();
/* 423 */     if (copyBufferEnabled) {
/* 424 */       syncCopyBuffer(true, 0, 0, paramInt1, paramInt2, paramInt3);
/* 425 */       arrayOfInt = this.copyBuffer;
/*     */     } 
/* 427 */     this.content.imageBufferReset(arrayOfInt, 0, 0, paramInt1, paramInt2, paramInt1 * paramInt3, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JRootPane getRootPane() {
/* 433 */     return this.rootPane;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentPane(Container paramContainer) {
/* 438 */     getRootPane().setContentPane(paramContainer);
/*     */   }
/*     */ 
/*     */   
/*     */   public Container getContentPane() {
/* 443 */     return getRootPane().getContentPane();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayeredPane(JLayeredPane paramJLayeredPane) {
/* 448 */     getRootPane().setLayeredPane(paramJLayeredPane);
/*     */   }
/*     */ 
/*     */   
/*     */   public JLayeredPane getLayeredPane() {
/* 453 */     return getRootPane().getLayeredPane();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGlassPane(Component paramComponent) {
/* 458 */     getRootPane().setGlassPane(paramComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getGlassPane() {
/* 463 */     return getRootPane().getGlassPane();
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
/*     */   private void updateClientCursor() {
/* 475 */     Point point = MouseInfo.getPointerInfo().getLocation();
/* 476 */     SwingUtilities.convertPointFromScreen(point, this);
/* 477 */     Component component = SwingUtilities.getDeepestComponentAt(this, point.x, point.y);
/* 478 */     if (component != null) {
/* 479 */       this.content.setCursor(component.getCursor());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void overrideNativeWindowHandle(long paramLong, Runnable paramRunnable) {
/* 485 */     OverrideNativeWindowHandle overrideNativeWindowHandle = (OverrideNativeWindowHandle)AWTAccessor.getComponentAccessor().getPeer(this);
/* 486 */     if (overrideNativeWindowHandle instanceof OverrideNativeWindowHandle) {
/* 487 */       ((OverrideNativeWindowHandle)overrideNativeWindowHandle).overrideWindowHandle(paramLong);
/*     */     }
/* 489 */     if (paramRunnable != null) {
/* 490 */       paramRunnable.run();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends java.awt.dnd.DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener) {
/* 499 */     return (this.content == null) ? null : this.content.<T>createDragGestureRecognizer(paramClass, paramDragSource, paramComponent, paramInt, paramDragGestureListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent) throws InvalidDnDOperationException {
/* 504 */     return (this.content == null) ? null : this.content.createDragSourceContextPeer(paramDragGestureEvent);
/*     */   }
/*     */   
/*     */   public void addDropTarget(DropTarget paramDropTarget) {
/* 508 */     if (this.content == null)
/* 509 */       return;  this.content.addDropTarget(paramDropTarget);
/*     */   }
/*     */   
/*     */   public void removeDropTarget(DropTarget paramDropTarget) {
/* 513 */     if (this.content == null)
/* 514 */       return;  this.content.removeDropTarget(paramDropTarget);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/swing/JLightweightFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */