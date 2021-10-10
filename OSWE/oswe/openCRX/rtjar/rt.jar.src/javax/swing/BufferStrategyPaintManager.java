/*     */ package javax.swing;
/*     */ 
/*     */ import com.sun.java.swing.SwingUtilities3;
/*     */ import java.awt.AWTException;
/*     */ import java.awt.BufferCapabilities;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.ImageCapabilities;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.awt.image.BufferStrategy;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import sun.awt.SubRegionShowable;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.pipe.hw.ExtendedBufferCapabilities;
/*     */ import sun.util.logging.PlatformLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BufferStrategyPaintManager
/*     */   extends RepaintManager.PaintManager
/*     */ {
/*     */   private static Method COMPONENT_CREATE_BUFFER_STRATEGY_METHOD;
/*     */   private static Method COMPONENT_GET_BUFFER_STRATEGY_METHOD;
/*  78 */   private static final PlatformLogger LOGGER = PlatformLogger.getLogger("javax.swing.BufferStrategyPaintManager");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList<BufferInfo> bufferInfos;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean painting;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean showing;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int accumulatedX;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int accumulatedY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int accumulatedMaxX;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int accumulatedMaxY;
/*     */ 
/*     */ 
/*     */   
/*     */   private JComponent rootJ;
/*     */ 
/*     */ 
/*     */   
/*     */   private int xOffset;
/*     */ 
/*     */ 
/*     */   
/*     */   private int yOffset;
/*     */ 
/*     */ 
/*     */   
/*     */   private Graphics bsg;
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferStrategy bufferStrategy;
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferInfo bufferInfo;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean disposeBufferOnEnd;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Method getGetBufferStrategyMethod() {
/* 147 */     if (COMPONENT_GET_BUFFER_STRATEGY_METHOD == null) {
/* 148 */       getMethods();
/*     */     }
/* 150 */     return COMPONENT_GET_BUFFER_STRATEGY_METHOD;
/*     */   }
/*     */   
/*     */   private static Method getCreateBufferStrategyMethod() {
/* 154 */     if (COMPONENT_CREATE_BUFFER_STRATEGY_METHOD == null) {
/* 155 */       getMethods();
/*     */     }
/* 157 */     return COMPONENT_CREATE_BUFFER_STRATEGY_METHOD;
/*     */   }
/*     */   
/*     */   private static void getMethods() {
/* 161 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*     */             try {
/* 165 */               BufferStrategyPaintManager.COMPONENT_CREATE_BUFFER_STRATEGY_METHOD = Component.class
/* 166 */                 .getDeclaredMethod("createBufferStrategy", new Class[] { int.class, BufferCapabilities.class });
/*     */ 
/*     */               
/* 169 */               BufferStrategyPaintManager.COMPONENT_CREATE_BUFFER_STRATEGY_METHOD
/* 170 */                 .setAccessible(true);
/* 171 */               BufferStrategyPaintManager.COMPONENT_GET_BUFFER_STRATEGY_METHOD = Component.class
/* 172 */                 .getDeclaredMethod("getBufferStrategy", new Class[0]);
/* 173 */               BufferStrategyPaintManager.COMPONENT_GET_BUFFER_STRATEGY_METHOD.setAccessible(true);
/* 174 */             } catch (SecurityException securityException) {
/*     */               assert false;
/* 176 */             } catch (NoSuchMethodException noSuchMethodException) {
/*     */               assert false;
/*     */             } 
/* 179 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   BufferStrategyPaintManager() {
/* 185 */     this.bufferInfos = new ArrayList<>(1);
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
/*     */   protected void dispose() {
/* 199 */     SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/*     */             ArrayList arrayList;
/* 202 */             synchronized (BufferStrategyPaintManager.this) {
/* 203 */               while (BufferStrategyPaintManager.this.showing) {
/*     */                 try {
/* 205 */                   BufferStrategyPaintManager.this.wait();
/* 206 */                 } catch (InterruptedException interruptedException) {}
/*     */               } 
/*     */               
/* 209 */               arrayList = BufferStrategyPaintManager.this.bufferInfos;
/* 210 */               BufferStrategyPaintManager.this.bufferInfos = null;
/*     */             } 
/* 212 */             BufferStrategyPaintManager.this.dispose(arrayList);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void dispose(List<BufferInfo> paramList) {
/* 218 */     if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 219 */       LOGGER.finer("BufferStrategyPaintManager disposed", new RuntimeException());
/*     */     }
/*     */     
/* 222 */     if (paramList != null) {
/* 223 */       for (BufferInfo bufferInfo : paramList) {
/* 224 */         bufferInfo.dispose();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean show(Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 235 */     synchronized (this) {
/* 236 */       if (this.painting)
/*     */       {
/*     */         
/* 239 */         return false;
/*     */       }
/* 241 */       this.showing = true;
/*     */     } 
/*     */     try {
/* 244 */       BufferInfo bufferInfo = getBufferInfo(paramContainer);
/*     */       BufferStrategy bufferStrategy;
/* 246 */       if (bufferInfo != null && bufferInfo.isInSync() && (
/* 247 */         bufferStrategy = bufferInfo.getBufferStrategy(false)) != null) {
/* 248 */         SubRegionShowable subRegionShowable = (SubRegionShowable)bufferStrategy;
/*     */         
/* 250 */         boolean bool = bufferInfo.getPaintAllOnExpose();
/* 251 */         bufferInfo.setPaintAllOnExpose(false);
/* 252 */         if (subRegionShowable.showIfNotLost(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4)) {
/* 253 */           return !bool;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 259 */         this.bufferInfo.setContentsLostDuringExpose(true);
/*     */       } 
/*     */     } finally {
/*     */       
/* 263 */       synchronized (this) {
/* 264 */         this.showing = false;
/* 265 */         notifyAll();
/*     */       } 
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean paint(JComponent paramJComponent1, JComponent paramJComponent2, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 274 */     Container container = fetchRoot(paramJComponent1);
/*     */     
/* 276 */     if (prepare(paramJComponent1, container, true, paramInt1, paramInt2, paramInt3, paramInt4)) {
/* 277 */       if (paramGraphics instanceof SunGraphics2D && ((SunGraphics2D)paramGraphics)
/* 278 */         .getDestination() == container) {
/*     */ 
/*     */ 
/*     */         
/* 282 */         int i = ((SunGraphics2D)this.bsg).constrainX;
/* 283 */         int j = ((SunGraphics2D)this.bsg).constrainY;
/* 284 */         if (i != 0 || j != 0) {
/* 285 */           this.bsg.translate(-i, -j);
/*     */         }
/* 287 */         ((SunGraphics2D)this.bsg).constrain(this.xOffset + i, this.yOffset + j, paramInt1 + paramInt3, paramInt2 + paramInt4);
/*     */         
/* 289 */         this.bsg.setClip(paramInt1, paramInt2, paramInt3, paramInt4);
/* 290 */         paramJComponent1.paintToOffscreen(this.bsg, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4);
/*     */         
/* 292 */         accumulate(this.xOffset + paramInt1, this.yOffset + paramInt2, paramInt3, paramInt4);
/* 293 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 298 */       this.bufferInfo.setInSync(false);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 303 */     if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 304 */       LOGGER.finer("prepare failed");
/*     */     }
/* 306 */     return super.paint(paramJComponent1, paramJComponent2, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyArea(JComponent paramJComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
/* 317 */     Container container = fetchRoot(paramJComponent);
/*     */     
/* 319 */     if (prepare(paramJComponent, container, false, 0, 0, 0, 0) && this.bufferInfo.isInSync()) {
/* 320 */       if (paramBoolean) {
/* 321 */         Rectangle rectangle = paramJComponent.getVisibleRect();
/* 322 */         int i = this.xOffset + paramInt1;
/* 323 */         int j = this.yOffset + paramInt2;
/* 324 */         this.bsg.clipRect(this.xOffset + rectangle.x, this.yOffset + rectangle.y, rectangle.width, rectangle.height);
/*     */ 
/*     */         
/* 327 */         this.bsg.copyArea(i, j, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */       } else {
/*     */         
/* 330 */         this.bsg.copyArea(this.xOffset + paramInt1, this.yOffset + paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */       } 
/*     */       
/* 333 */       accumulate(paramInt1 + this.xOffset + paramInt5, paramInt2 + this.yOffset + paramInt6, paramInt3, paramInt4);
/*     */     } else {
/* 335 */       if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 336 */         LOGGER.finer("copyArea: prepare failed or not in sync");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 341 */       if (!flushAccumulatedRegion()) {
/*     */ 
/*     */         
/* 344 */         this.rootJ.repaint();
/*     */       } else {
/* 346 */         super.copyArea(paramJComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramBoolean);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void beginPaint() {
/* 352 */     synchronized (this) {
/* 353 */       this.painting = true;
/*     */ 
/*     */       
/* 356 */       while (this.showing) {
/*     */         try {
/* 358 */           wait();
/* 359 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */     } 
/*     */     
/* 363 */     if (LOGGER.isLoggable(PlatformLogger.Level.FINEST)) {
/* 364 */       LOGGER.finest("beginPaint");
/*     */     }
/*     */     
/* 367 */     resetAccumulated();
/*     */   }
/*     */   
/*     */   public void endPaint() {
/* 371 */     if (LOGGER.isLoggable(PlatformLogger.Level.FINEST)) {
/* 372 */       LOGGER.finest("endPaint: region " + this.accumulatedX + " " + this.accumulatedY + " " + this.accumulatedMaxX + " " + this.accumulatedMaxY);
/*     */     }
/*     */ 
/*     */     
/* 376 */     if (this.painting && 
/* 377 */       !flushAccumulatedRegion()) {
/* 378 */       if (!isRepaintingRoot()) {
/* 379 */         repaintRoot(this.rootJ);
/*     */       }
/*     */       else {
/*     */         
/* 383 */         resetDoubleBufferPerWindow();
/*     */         
/* 385 */         this.rootJ.repaint();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 390 */     BufferInfo bufferInfo = null;
/* 391 */     synchronized (this) {
/* 392 */       this.painting = false;
/* 393 */       if (this.disposeBufferOnEnd) {
/* 394 */         this.disposeBufferOnEnd = false;
/* 395 */         bufferInfo = this.bufferInfo;
/* 396 */         this.bufferInfos.remove(bufferInfo);
/*     */       } 
/*     */     } 
/* 399 */     if (bufferInfo != null) {
/* 400 */       bufferInfo.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean flushAccumulatedRegion() {
/* 410 */     boolean bool = true;
/* 411 */     if (this.accumulatedX != Integer.MAX_VALUE) {
/* 412 */       SubRegionShowable subRegionShowable = (SubRegionShowable)this.bufferStrategy;
/* 413 */       boolean bool1 = this.bufferStrategy.contentsLost();
/* 414 */       if (!bool1) {
/* 415 */         subRegionShowable.show(this.accumulatedX, this.accumulatedY, this.accumulatedMaxX, this.accumulatedMaxY);
/*     */         
/* 417 */         bool1 = this.bufferStrategy.contentsLost();
/*     */       } 
/* 419 */       if (bool1) {
/* 420 */         if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 421 */           LOGGER.finer("endPaint: contents lost");
/*     */         }
/*     */         
/* 424 */         this.bufferInfo.setInSync(false);
/* 425 */         bool = false;
/*     */       } 
/*     */     } 
/* 428 */     resetAccumulated();
/* 429 */     return bool;
/*     */   }
/*     */   
/*     */   private void resetAccumulated() {
/* 433 */     this.accumulatedX = Integer.MAX_VALUE;
/* 434 */     this.accumulatedY = Integer.MAX_VALUE;
/* 435 */     this.accumulatedMaxX = 0;
/* 436 */     this.accumulatedMaxY = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doubleBufferingChanged(final JRootPane rootPane) {
/* 446 */     if ((!rootPane.isDoubleBuffered() || 
/* 447 */       !rootPane.getUseTrueDoubleBuffering()) && rootPane
/* 448 */       .getParent() != null) {
/* 449 */       if (!SwingUtilities.isEventDispatchThread()) {
/* 450 */         Runnable runnable = new Runnable() {
/*     */             public void run() {
/* 452 */               BufferStrategyPaintManager.this.doubleBufferingChanged0(rootPane);
/*     */             }
/*     */           };
/* 455 */         SwingUtilities.invokeLater(runnable);
/*     */       } else {
/*     */         
/* 458 */         doubleBufferingChanged0(rootPane);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doubleBufferingChanged0(JRootPane paramJRootPane) {
/*     */     BufferInfo bufferInfo;
/* 469 */     synchronized (this) {
/*     */ 
/*     */       
/* 472 */       while (this.showing) {
/*     */         try {
/* 474 */           wait();
/* 475 */         } catch (InterruptedException interruptedException) {}
/*     */       } 
/*     */       
/* 478 */       bufferInfo = getBufferInfo(paramJRootPane.getParent());
/* 479 */       if (this.painting && this.bufferInfo == bufferInfo) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 484 */         this.disposeBufferOnEnd = true;
/* 485 */         bufferInfo = null;
/* 486 */       } else if (bufferInfo != null) {
/* 487 */         this.bufferInfos.remove(bufferInfo);
/*     */       } 
/*     */     } 
/* 490 */     if (bufferInfo != null) {
/* 491 */       bufferInfo.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean prepare(JComponent paramJComponent, Container paramContainer, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 502 */     if (this.bsg != null) {
/* 503 */       this.bsg.dispose();
/* 504 */       this.bsg = null;
/*     */     } 
/* 506 */     this.bufferStrategy = null;
/* 507 */     if (paramContainer != null) {
/* 508 */       boolean bool = false;
/* 509 */       BufferInfo bufferInfo = getBufferInfo(paramContainer);
/* 510 */       if (bufferInfo == null) {
/* 511 */         bool = true;
/* 512 */         bufferInfo = new BufferInfo(paramContainer);
/* 513 */         this.bufferInfos.add(bufferInfo);
/* 514 */         if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 515 */           LOGGER.finer("prepare: new BufferInfo: " + paramContainer);
/*     */         }
/*     */       } 
/* 518 */       this.bufferInfo = bufferInfo;
/* 519 */       if (!bufferInfo.hasBufferStrategyChanged()) {
/* 520 */         this.bufferStrategy = bufferInfo.getBufferStrategy(true);
/* 521 */         if (this.bufferStrategy != null) {
/* 522 */           this.bsg = this.bufferStrategy.getDrawGraphics();
/* 523 */           if (this.bufferStrategy.contentsRestored()) {
/* 524 */             bool = true;
/* 525 */             if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 526 */               LOGGER.finer("prepare: contents restored in prepare");
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 533 */           return false;
/*     */         } 
/* 535 */         if (bufferInfo.getContentsLostDuringExpose()) {
/* 536 */           bool = true;
/* 537 */           bufferInfo.setContentsLostDuringExpose(false);
/* 538 */           if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 539 */             LOGGER.finer("prepare: contents lost on expose");
/*     */           }
/*     */         } 
/* 542 */         if (paramBoolean && paramJComponent == this.rootJ && paramInt1 == 0 && paramInt2 == 0 && paramJComponent
/* 543 */           .getWidth() == paramInt3 && paramJComponent.getHeight() == paramInt4) {
/* 544 */           bufferInfo.setInSync(true);
/*     */         }
/* 546 */         else if (bool) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 551 */           bufferInfo.setInSync(false);
/* 552 */           if (!isRepaintingRoot()) {
/* 553 */             repaintRoot(this.rootJ);
/*     */           }
/*     */           else {
/*     */             
/* 557 */             resetDoubleBufferPerWindow();
/*     */           } 
/*     */         } 
/* 560 */         return (this.bufferInfos != null);
/*     */       } 
/*     */     } 
/* 563 */     return false;
/*     */   }
/*     */   
/*     */   private Container fetchRoot(JComponent paramJComponent) {
/* 567 */     boolean bool = false;
/* 568 */     this.rootJ = paramJComponent;
/* 569 */     Container container = paramJComponent;
/* 570 */     this.xOffset = this.yOffset = 0;
/* 571 */     while (container != null && !(container instanceof Window) && 
/*     */       
/* 573 */       !SunToolkit.isInstanceOf(container, "java.applet.Applet")) {
/* 574 */       this.xOffset += container.getX();
/* 575 */       this.yOffset += container.getY();
/* 576 */       container = container.getParent();
/* 577 */       if (container != null) {
/* 578 */         if (container instanceof JComponent) {
/* 579 */           this.rootJ = (JComponent)container; continue;
/*     */         } 
/* 581 */         if (!container.isLightweight()) {
/* 582 */           if (!bool) {
/* 583 */             bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 596 */           return null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 601 */     if (container instanceof RootPaneContainer && this.rootJ instanceof JRootPane)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 606 */       if (this.rootJ.isDoubleBuffered() && ((JRootPane)this.rootJ)
/* 607 */         .getUseTrueDoubleBuffering())
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 612 */         return container;
/*     */       }
/*     */     }
/*     */     
/* 616 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetDoubleBufferPerWindow() {
/* 623 */     if (this.bufferInfos != null) {
/* 624 */       dispose(this.bufferInfos);
/* 625 */       this.bufferInfos = null;
/* 626 */       this.repaintManager.setPaintManager(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferInfo getBufferInfo(Container paramContainer) {
/* 635 */     for (int i = this.bufferInfos.size() - 1; i >= 0; i--) {
/* 636 */       BufferInfo bufferInfo = this.bufferInfos.get(i);
/* 637 */       Container container = bufferInfo.getRoot();
/* 638 */       if (container == null) {
/*     */         
/* 640 */         this.bufferInfos.remove(i);
/* 641 */         if (LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 642 */           LOGGER.finer("BufferInfo pruned, root null");
/*     */         }
/*     */       }
/* 645 */       else if (container == paramContainer) {
/* 646 */         return bufferInfo;
/*     */       } 
/*     */     } 
/* 649 */     return null;
/*     */   }
/*     */   
/*     */   private void accumulate(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 653 */     this.accumulatedX = Math.min(paramInt1, this.accumulatedX);
/* 654 */     this.accumulatedY = Math.min(paramInt2, this.accumulatedY);
/* 655 */     this.accumulatedMaxX = Math.max(this.accumulatedMaxX, paramInt1 + paramInt3);
/* 656 */     this.accumulatedMaxY = Math.max(this.accumulatedMaxY, paramInt2 + paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class BufferInfo
/*     */     extends ComponentAdapter
/*     */     implements WindowListener
/*     */   {
/*     */     private WeakReference<BufferStrategy> weakBS;
/*     */ 
/*     */ 
/*     */     
/*     */     private WeakReference<Container> root;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean inSync;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean contentsLostDuringExpose;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean paintAllOnExpose;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BufferInfo(Container param1Container) {
/* 688 */       this.root = new WeakReference<>(param1Container);
/* 689 */       param1Container.addComponentListener(this);
/* 690 */       if (param1Container instanceof Window) {
/* 691 */         ((Window)param1Container).addWindowListener(this);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setPaintAllOnExpose(boolean param1Boolean) {
/* 696 */       this.paintAllOnExpose = param1Boolean;
/*     */     }
/*     */     
/*     */     public boolean getPaintAllOnExpose() {
/* 700 */       return this.paintAllOnExpose;
/*     */     }
/*     */     
/*     */     public void setContentsLostDuringExpose(boolean param1Boolean) {
/* 704 */       this.contentsLostDuringExpose = param1Boolean;
/*     */     }
/*     */     
/*     */     public boolean getContentsLostDuringExpose() {
/* 708 */       return this.contentsLostDuringExpose;
/*     */     }
/*     */     
/*     */     public void setInSync(boolean param1Boolean) {
/* 712 */       this.inSync = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isInSync() {
/* 721 */       return this.inSync;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Container getRoot() {
/* 728 */       return (this.root == null) ? null : this.root.get();
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
/*     */     public BufferStrategy getBufferStrategy(boolean param1Boolean) {
/* 741 */       BufferStrategy bufferStrategy = (this.weakBS == null) ? null : this.weakBS.get();
/* 742 */       if (bufferStrategy == null && param1Boolean) {
/* 743 */         bufferStrategy = createBufferStrategy();
/* 744 */         if (bufferStrategy != null) {
/* 745 */           this.weakBS = new WeakReference<>(bufferStrategy);
/*     */         }
/* 747 */         if (BufferStrategyPaintManager.LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 748 */           BufferStrategyPaintManager.LOGGER.finer("getBufferStrategy: created bs: " + bufferStrategy);
/*     */         }
/*     */       } 
/* 751 */       return bufferStrategy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasBufferStrategyChanged() {
/* 759 */       Container container = getRoot();
/* 760 */       if (container != null) {
/* 761 */         BufferStrategy bufferStrategy1 = null;
/* 762 */         BufferStrategy bufferStrategy2 = null;
/*     */         
/* 764 */         bufferStrategy1 = getBufferStrategy(false);
/* 765 */         if (container instanceof Window) {
/* 766 */           bufferStrategy2 = ((Window)container).getBufferStrategy();
/*     */         } else {
/*     */ 
/*     */           
/*     */           try {
/* 771 */             bufferStrategy2 = (BufferStrategy)BufferStrategyPaintManager.getGetBufferStrategyMethod().invoke(container, new Object[0]);
/* 772 */           } catch (InvocationTargetException invocationTargetException) {
/*     */             assert false;
/* 774 */           } catch (IllegalArgumentException illegalArgumentException) {
/*     */             assert false;
/* 776 */           } catch (IllegalAccessException illegalAccessException) {
/*     */             assert false;
/*     */           } 
/*     */         } 
/* 780 */         if (bufferStrategy2 != bufferStrategy1) {
/*     */           
/* 782 */           if (bufferStrategy1 != null) {
/* 783 */             bufferStrategy1.dispose();
/*     */           }
/* 785 */           this.weakBS = null;
/* 786 */           return true;
/*     */         } 
/*     */       } 
/* 789 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private BufferStrategy createBufferStrategy() {
/* 798 */       Container container = getRoot();
/* 799 */       if (container == null) {
/* 800 */         return null;
/*     */       }
/* 802 */       BufferStrategy bufferStrategy = null;
/* 803 */       if (SwingUtilities3.isVsyncRequested(container)) {
/* 804 */         bufferStrategy = createBufferStrategy(container, true);
/* 805 */         if (BufferStrategyPaintManager.LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 806 */           BufferStrategyPaintManager.LOGGER.finer("createBufferStrategy: using vsynced strategy");
/*     */         }
/*     */       } 
/* 809 */       if (bufferStrategy == null) {
/* 810 */         bufferStrategy = createBufferStrategy(container, false);
/*     */       }
/* 812 */       if (!(bufferStrategy instanceof SubRegionShowable))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 818 */         bufferStrategy = null;
/*     */       }
/* 820 */       return bufferStrategy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private BufferStrategy createBufferStrategy(Container param1Container, boolean param1Boolean) {
/*     */       BufferCapabilities bufferCapabilities;
/* 829 */       if (param1Boolean) {
/* 830 */         bufferCapabilities = new ExtendedBufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), BufferCapabilities.FlipContents.COPIED, ExtendedBufferCapabilities.VSyncType.VSYNC_ON);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 835 */         bufferCapabilities = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), null);
/*     */       } 
/*     */ 
/*     */       
/* 839 */       BufferStrategy bufferStrategy = null;
/* 840 */       if (SunToolkit.isInstanceOf(param1Container, "java.applet.Applet")) {
/*     */         try {
/* 842 */           BufferStrategyPaintManager.getCreateBufferStrategyMethod().invoke(param1Container, new Object[] { Integer.valueOf(2), bufferCapabilities });
/*     */           
/* 844 */           bufferStrategy = (BufferStrategy)BufferStrategyPaintManager.getGetBufferStrategyMethod().invoke(param1Container, new Object[0]);
/* 845 */         } catch (InvocationTargetException invocationTargetException) {
/*     */           
/* 847 */           if (BufferStrategyPaintManager.LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 848 */             BufferStrategyPaintManager.LOGGER.finer("createBufferStratety failed", invocationTargetException);
/*     */           }
/*     */         }
/* 851 */         catch (IllegalArgumentException illegalArgumentException) {
/*     */           assert false;
/* 853 */         } catch (IllegalAccessException illegalAccessException) {
/*     */           
/*     */           assert false;
/*     */         } 
/*     */       } else {
/*     */         try {
/* 859 */           ((Window)param1Container).createBufferStrategy(2, bufferCapabilities);
/* 860 */           bufferStrategy = ((Window)param1Container).getBufferStrategy();
/* 861 */         } catch (AWTException aWTException) {
/*     */           
/* 863 */           if (BufferStrategyPaintManager.LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 864 */             BufferStrategyPaintManager.LOGGER.finer("createBufferStratety failed", aWTException);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 869 */       return bufferStrategy;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void dispose() {
/* 876 */       Container container = getRoot();
/* 877 */       if (BufferStrategyPaintManager.LOGGER.isLoggable(PlatformLogger.Level.FINER)) {
/* 878 */         BufferStrategyPaintManager.LOGGER.finer("disposed BufferInfo for: " + container);
/*     */       }
/* 880 */       if (container != null) {
/* 881 */         container.removeComponentListener(this);
/* 882 */         if (container instanceof Window) {
/* 883 */           ((Window)container).removeWindowListener(this);
/*     */         }
/* 885 */         BufferStrategy bufferStrategy = getBufferStrategy(false);
/* 886 */         if (bufferStrategy != null) {
/* 887 */           bufferStrategy.dispose();
/*     */         }
/*     */       } 
/* 890 */       this.root = null;
/* 891 */       this.weakBS = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void componentHidden(ComponentEvent param1ComponentEvent) {
/* 901 */       Container container = getRoot();
/* 902 */       if (container != null && container.isVisible()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 911 */         container.repaint();
/*     */       } else {
/*     */         
/* 914 */         setPaintAllOnExpose(true);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void windowIconified(WindowEvent param1WindowEvent) {
/* 919 */       setPaintAllOnExpose(true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void windowClosed(WindowEvent param1WindowEvent) {
/* 925 */       synchronized (BufferStrategyPaintManager.this) {
/* 926 */         while (BufferStrategyPaintManager.this.showing) {
/*     */           try {
/* 928 */             BufferStrategyPaintManager.this.wait();
/* 929 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*     */         
/* 932 */         BufferStrategyPaintManager.this.bufferInfos.remove(this);
/*     */       } 
/* 934 */       dispose();
/*     */     }
/*     */     
/*     */     public void windowOpened(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowClosing(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowDeiconified(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowActivated(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowDeactivated(WindowEvent param1WindowEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/BufferStrategyPaintManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */