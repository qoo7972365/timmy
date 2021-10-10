/*      */ package javax.swing;
/*      */ 
/*      */ import com.sun.java.swing.SwingUtilities3;
/*      */ import java.applet.Applet;
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.Image;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.InvocationEvent;
/*      */ import java.awt.image.VolatileImage;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.DisplayChangedListener;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.java2d.SunGraphicsEnvironment;
/*      */ import sun.misc.JavaSecurityAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.swing.SwingAccessor;
/*      */ import sun.swing.SwingUtilities2;
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
/*      */ public class RepaintManager
/*      */ {
/*      */   static final boolean HANDLE_TOP_LEVEL_PAINT;
/*      */   private static final short BUFFER_STRATEGY_NOT_SPECIFIED = 0;
/*      */   private static final short BUFFER_STRATEGY_SPECIFIED_ON = 1;
/*      */   private static final short BUFFER_STRATEGY_SPECIFIED_OFF = 2;
/*      */   private static final short BUFFER_STRATEGY_TYPE;
/*   81 */   private Map<GraphicsConfiguration, VolatileImage> volatileMap = new HashMap<>(1);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Container, Rectangle> hwDirtyComponents;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Component, Rectangle> dirtyComponents;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Component, Rectangle> tmpDirtyComponents;
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Component> invalidComponents;
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Runnable> runnableList;
/*      */ 
/*      */ 
/*      */   
/*      */   boolean doubleBufferingEnabled = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private Dimension doubleBufferMaxSize;
/*      */ 
/*      */ 
/*      */   
/*      */   DoubleBufferInfo standardDoubleBuffer;
/*      */ 
/*      */ 
/*      */   
/*      */   private PaintManager paintManager;
/*      */ 
/*      */ 
/*      */   
/*  122 */   private static final Object repaintManagerKey = RepaintManager.class;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean volatileImageBufferEnabled = true;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int volatileBufferType;
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean nativeDoubleBuffering;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int VOLATILE_LOOP_MAX = 2;
/*      */ 
/*      */ 
/*      */   
/*  143 */   private int paintDepth = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private short bufferStrategyType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean painting;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private JComponent repaintRoot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Thread paintThread;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ProcessingRunnable processingRunnable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   private static final JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  192 */   private static final DisplayChangedListener displayChangedHandler = new DisplayChangedHandler(); Rectangle tmp;
/*      */   private List<SwingUtilities2.RepaintListener> repaintListeners;
/*      */   
/*      */   static {
/*  196 */     SwingAccessor.setRepaintManagerAccessor(new SwingAccessor.RepaintManagerAccessor()
/*      */         {
/*      */           public void addRepaintListener(RepaintManager param1RepaintManager, SwingUtilities2.RepaintListener param1RepaintListener) {
/*  199 */             param1RepaintManager.addRepaintListener(param1RepaintListener);
/*      */           }
/*      */           
/*      */           public void removeRepaintListener(RepaintManager param1RepaintManager, SwingUtilities2.RepaintListener param1RepaintListener) {
/*  203 */             param1RepaintManager.removeRepaintListener(param1RepaintListener);
/*      */           }
/*      */         });
/*      */     
/*  207 */     volatileImageBufferEnabled = "true".equals(
/*  208 */         AccessController.doPrivileged(new GetPropertyAction("swing.volatileImageBufferEnabled", "true")));
/*      */     
/*  210 */     boolean bool = GraphicsEnvironment.isHeadless();
/*  211 */     if (volatileImageBufferEnabled && bool) {
/*  212 */       volatileImageBufferEnabled = false;
/*      */     }
/*  214 */     nativeDoubleBuffering = "true".equals(AccessController.doPrivileged(new GetPropertyAction("awt.nativeDoubleBuffering")));
/*      */     
/*  216 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("swing.bufferPerWindow"));
/*      */     
/*  218 */     if (bool) {
/*  219 */       BUFFER_STRATEGY_TYPE = 2;
/*      */     }
/*  221 */     else if (str == null) {
/*  222 */       BUFFER_STRATEGY_TYPE = 0;
/*      */     }
/*  224 */     else if ("true".equals(str)) {
/*  225 */       BUFFER_STRATEGY_TYPE = 1;
/*      */     } else {
/*      */       
/*  228 */       BUFFER_STRATEGY_TYPE = 2;
/*      */     } 
/*  230 */     HANDLE_TOP_LEVEL_PAINT = "true".equals(AccessController.doPrivileged(new GetPropertyAction("swing.handleTopLevelPaint", "true")));
/*      */ 
/*      */     
/*  233 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  234 */     if (graphicsEnvironment instanceof SunGraphicsEnvironment) {
/*  235 */       ((SunGraphicsEnvironment)graphicsEnvironment).addDisplayChangedListener(displayChangedHandler);
/*      */     }
/*      */     
/*  238 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  239 */     if (toolkit instanceof SunToolkit && ((SunToolkit)toolkit)
/*  240 */       .isSwingBackbufferTranslucencySupported()) {
/*  241 */       volatileBufferType = 3;
/*      */     } else {
/*  243 */       volatileBufferType = 1;
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
/*      */   public static RepaintManager currentManager(Component paramComponent) {
/*  260 */     return currentManager(AppContext.getAppContext());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static RepaintManager currentManager(AppContext paramAppContext) {
/*  269 */     RepaintManager repaintManager = (RepaintManager)paramAppContext.get(repaintManagerKey);
/*  270 */     if (repaintManager == null) {
/*  271 */       repaintManager = new RepaintManager(BUFFER_STRATEGY_TYPE);
/*  272 */       paramAppContext.put(repaintManagerKey, repaintManager);
/*      */     } 
/*  274 */     return repaintManager;
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
/*      */   public static RepaintManager currentManager(JComponent paramJComponent) {
/*  288 */     return currentManager(paramJComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setCurrentManager(RepaintManager paramRepaintManager) {
/*  299 */     if (paramRepaintManager != null) {
/*  300 */       SwingUtilities.appContextPut(repaintManagerKey, paramRepaintManager);
/*      */     } else {
/*  302 */       SwingUtilities.appContextRemove(repaintManagerKey);
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
/*      */   public RepaintManager() {
/*  316 */     this((short)2);
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
/*      */   private void displayChanged() {
/*  333 */     clearImages();
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
/*      */   public synchronized void addInvalidComponent(JComponent paramJComponent) {
/*  346 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  347 */     if (repaintManager != null) {
/*  348 */       repaintManager.addInvalidComponent(paramJComponent);
/*      */       
/*      */       return;
/*      */     } 
/*  352 */     Container container = SwingUtilities.getValidateRoot(paramJComponent, true);
/*      */     
/*  354 */     if (container == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  362 */     if (this.invalidComponents == null) {
/*  363 */       this.invalidComponents = new ArrayList<>();
/*      */     } else {
/*      */       
/*  366 */       int i = this.invalidComponents.size();
/*  367 */       for (byte b = 0; b < i; b++) {
/*  368 */         if (container == this.invalidComponents.get(b)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */     } 
/*  373 */     this.invalidComponents.add(container);
/*      */ 
/*      */ 
/*      */     
/*  377 */     scheduleProcessingRunnable(SunToolkit.targetToAppContext(paramJComponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeInvalidComponent(JComponent paramJComponent) {
/*  387 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  388 */     if (repaintManager != null) {
/*  389 */       repaintManager.removeInvalidComponent(paramJComponent);
/*      */       return;
/*      */     } 
/*  392 */     if (this.invalidComponents != null) {
/*  393 */       int i = this.invalidComponents.indexOf(paramJComponent);
/*  394 */       if (i != -1) {
/*  395 */         this.invalidComponents.remove(i);
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
/*      */ 
/*      */   
/*      */   private void addDirtyRegion0(Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  411 */     if (paramInt3 <= 0 || paramInt4 <= 0 || paramContainer == null) {
/*      */       return;
/*      */     }
/*      */     
/*  415 */     if (paramContainer.getWidth() <= 0 || paramContainer.getHeight() <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  419 */     if (extendDirtyRegion(paramContainer, paramInt1, paramInt2, paramInt3, paramInt4)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  431 */     Container container1 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  437 */     for (Container container2 = paramContainer; container2 != null; container2 = container2.getParent()) {
/*  438 */       if (!container2.isVisible() || container2.getPeer() == null) {
/*      */         return;
/*      */       }
/*  441 */       if (container2 instanceof Window || container2 instanceof Applet) {
/*      */         
/*  443 */         if (container2 instanceof Frame && (((Frame)container2)
/*  444 */           .getExtendedState() & 0x1) == 1) {
/*      */           return;
/*      */         }
/*      */         
/*  448 */         container1 = container2;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  453 */     if (container1 == null)
/*      */       return; 
/*  455 */     synchronized (this) {
/*  456 */       if (extendDirtyRegion(paramContainer, paramInt1, paramInt2, paramInt3, paramInt4)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  461 */       this.dirtyComponents.put(paramContainer, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  466 */     scheduleProcessingRunnable(SunToolkit.targetToAppContext(paramContainer));
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
/*      */   public void addDirtyRegion(JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  483 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  484 */     if (repaintManager != null) {
/*  485 */       repaintManager.addDirtyRegion(paramJComponent, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */       return;
/*      */     } 
/*  488 */     addDirtyRegion0(paramJComponent, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public void addDirtyRegion(Window paramWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  506 */     addDirtyRegion0(paramWindow, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public void addDirtyRegion(Applet paramApplet, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  522 */     addDirtyRegion0(paramApplet, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   void scheduleHeavyWeightPaints() {
/*      */     Map<Container, Rectangle> map;
/*  528 */     synchronized (this) {
/*  529 */       if (this.hwDirtyComponents.size() == 0) {
/*      */         return;
/*      */       }
/*  532 */       map = this.hwDirtyComponents;
/*  533 */       this.hwDirtyComponents = new IdentityHashMap<>();
/*      */     } 
/*  535 */     for (Container container : map.keySet()) {
/*  536 */       Rectangle rectangle = map.get(container);
/*  537 */       if (container instanceof Window) {
/*  538 */         addDirtyRegion((Window)container, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         continue;
/*      */       } 
/*  541 */       if (container instanceof Applet) {
/*  542 */         addDirtyRegion((Applet)container, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         
/*      */         continue;
/*      */       } 
/*  546 */       addDirtyRegion0(container, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*      */   void nativeAddDirtyRegion(AppContext paramAppContext, Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  558 */     if (paramInt3 > 0 && paramInt4 > 0) {
/*  559 */       synchronized (this) {
/*  560 */         Rectangle rectangle = this.hwDirtyComponents.get(paramContainer);
/*  561 */         if (rectangle == null) {
/*  562 */           this.hwDirtyComponents.put(paramContainer, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*      */         } else {
/*      */           
/*  565 */           this.hwDirtyComponents.put(paramContainer, SwingUtilities.computeUnion(paramInt1, paramInt2, paramInt3, paramInt4, rectangle));
/*      */         } 
/*      */       } 
/*      */       
/*  569 */       scheduleProcessingRunnable(paramAppContext);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void nativeQueueSurfaceDataRunnable(AppContext paramAppContext, final Component c, final Runnable r) {
/*  580 */     synchronized (this) {
/*  581 */       if (this.runnableList == null) {
/*  582 */         this.runnableList = new LinkedList<>();
/*      */       }
/*  584 */       this.runnableList.add(new Runnable() {
/*      */             public void run() {
/*  586 */               AccessControlContext accessControlContext1 = AccessController.getContext();
/*      */               
/*  588 */               AccessControlContext accessControlContext2 = AWTAccessor.getComponentAccessor().getAccessControlContext(c);
/*  589 */               RepaintManager.javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Void>() {
/*      */                     public Void run() {
/*  591 */                       r.run();
/*  592 */                       return null;
/*      */                     }
/*      */                   },  accessControlContext1, accessControlContext2);
/*      */             }
/*      */           });
/*      */     } 
/*  598 */     scheduleProcessingRunnable(paramAppContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized boolean extendDirtyRegion(Component paramComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  609 */     Rectangle rectangle = this.dirtyComponents.get(paramComponent);
/*  610 */     if (rectangle != null) {
/*      */ 
/*      */ 
/*      */       
/*  614 */       SwingUtilities.computeUnion(paramInt1, paramInt2, paramInt3, paramInt4, rectangle);
/*  615 */       return true;
/*      */     } 
/*  617 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getDirtyRegion(JComponent paramJComponent) {
/*      */     Rectangle rectangle;
/*  625 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  626 */     if (repaintManager != null) {
/*  627 */       return repaintManager.getDirtyRegion(paramJComponent);
/*      */     }
/*      */     
/*  630 */     synchronized (this) {
/*  631 */       rectangle = this.dirtyComponents.get(paramJComponent);
/*      */     } 
/*  633 */     if (rectangle == null) {
/*  634 */       return new Rectangle(0, 0, 0, 0);
/*      */     }
/*  636 */     return new Rectangle(rectangle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markCompletelyDirty(JComponent paramJComponent) {
/*  644 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  645 */     if (repaintManager != null) {
/*  646 */       repaintManager.markCompletelyDirty(paramJComponent);
/*      */       return;
/*      */     } 
/*  649 */     addDirtyRegion(paramJComponent, 0, 0, 2147483647, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markCompletelyClean(JComponent paramJComponent) {
/*  657 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  658 */     if (repaintManager != null) {
/*  659 */       repaintManager.markCompletelyClean(paramJComponent);
/*      */       return;
/*      */     } 
/*  662 */     synchronized (this) {
/*  663 */       this.dirtyComponents.remove(paramJComponent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCompletelyDirty(JComponent paramJComponent) {
/*  674 */     RepaintManager repaintManager = getDelegate(paramJComponent);
/*  675 */     if (repaintManager != null) {
/*  676 */       return repaintManager.isCompletelyDirty(paramJComponent);
/*      */     }
/*      */ 
/*      */     
/*  680 */     Rectangle rectangle = getDirtyRegion(paramJComponent);
/*  681 */     if (rectangle.width == Integer.MAX_VALUE && rectangle.height == Integer.MAX_VALUE)
/*      */     {
/*  683 */       return true;
/*      */     }
/*  685 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validateInvalidComponents() {
/*      */     List<Component> list;
/*  695 */     synchronized (this) {
/*  696 */       if (this.invalidComponents == null) {
/*      */         return;
/*      */       }
/*  699 */       list = this.invalidComponents;
/*  700 */       this.invalidComponents = null;
/*      */     } 
/*  702 */     int i = list.size();
/*  703 */     for (byte b = 0; b < i; b++) {
/*  704 */       final Component c = list.get(b);
/*  705 */       AccessControlContext accessControlContext1 = AccessController.getContext();
/*      */       
/*  707 */       AccessControlContext accessControlContext2 = AWTAccessor.getComponentAccessor().getAccessControlContext(component);
/*  708 */       javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Void>()
/*      */           {
/*      */             public Void run() {
/*  711 */               c.validate();
/*  712 */               return null;
/*      */             }
/*      */           },  accessControlContext1, accessControlContext2);
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
/*      */   private void prePaintDirtyRegions() {
/*      */     Map<Component, Rectangle> map;
/*      */     List<Runnable> list;
/*  728 */     synchronized (this) {
/*  729 */       map = this.dirtyComponents;
/*  730 */       list = this.runnableList;
/*  731 */       this.runnableList = null;
/*      */     } 
/*  733 */     if (list != null) {
/*  734 */       for (Runnable runnable : list) {
/*  735 */         runnable.run();
/*      */       }
/*      */     }
/*  738 */     paintDirtyRegions();
/*  739 */     if (map.size() > 0)
/*      */     {
/*      */       
/*  742 */       paintDirtyRegions(map);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateWindows(Map<Component, Rectangle> paramMap) {
/*  747 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  748 */     if (!(toolkit instanceof SunToolkit) || 
/*  749 */       !((SunToolkit)toolkit).needUpdateWindow()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  754 */     HashSet<Window> hashSet = new HashSet();
/*  755 */     Set<Component> set = paramMap.keySet();
/*  756 */     for (Component component : set) {
/*      */ 
/*      */ 
/*      */       
/*  760 */       Window window = (component instanceof Window) ? (Window)component : SwingUtilities.getWindowAncestor(component);
/*  761 */       if (window != null && 
/*  762 */         !window.isOpaque())
/*      */       {
/*  764 */         hashSet.add(window);
/*      */       }
/*      */     } 
/*      */     
/*  768 */     for (Window window : hashSet) {
/*  769 */       AWTAccessor.getWindowAccessor().updateWindow(window);
/*      */     }
/*      */   }
/*      */   
/*      */   boolean isPainting() {
/*  774 */     return this.painting;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintDirtyRegions() {
/*  783 */     synchronized (this) {
/*  784 */       Map<Component, Rectangle> map = this.tmpDirtyComponents;
/*  785 */       this.tmpDirtyComponents = this.dirtyComponents;
/*  786 */       this.dirtyComponents = map;
/*  787 */       this.dirtyComponents.clear();
/*      */     } 
/*  789 */     paintDirtyRegions(this.tmpDirtyComponents);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintDirtyRegions(final Map<Component, Rectangle> tmpDirtyComponents) {
/*  795 */     if (tmpDirtyComponents.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  800 */     final ArrayList<Component> roots = new ArrayList(tmpDirtyComponents.size());
/*  801 */     for (Component component : tmpDirtyComponents.keySet()) {
/*  802 */       collectDirtyComponents(tmpDirtyComponents, component, arrayList);
/*      */     }
/*      */     
/*  805 */     final AtomicInteger count = new AtomicInteger(arrayList.size());
/*  806 */     this.painting = true;
/*      */     try {
/*  808 */       for (byte b = 0; b < atomicInteger.get(); b++) {
/*  809 */         final byte i = b;
/*  810 */         final Component dirtyComponent = arrayList.get(b);
/*  811 */         AccessControlContext accessControlContext1 = AccessController.getContext();
/*      */         
/*  813 */         AccessControlContext accessControlContext2 = AWTAccessor.getComponentAccessor().getAccessControlContext(component);
/*  814 */         javaSecurityAccess.doIntersectionPrivilege(new PrivilegedAction<Void>() {
/*      */               public Void run() {
/*  816 */                 Rectangle rectangle = (Rectangle)tmpDirtyComponents.get(dirtyComponent);
/*      */ 
/*      */                 
/*  819 */                 if (rectangle == null) {
/*  820 */                   return null;
/*      */                 }
/*      */                 
/*  823 */                 int i = dirtyComponent.getHeight();
/*  824 */                 int j = dirtyComponent.getWidth();
/*  825 */                 SwingUtilities.computeIntersection(0, 0, j, i, rectangle);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  830 */                 if (dirtyComponent instanceof JComponent) {
/*  831 */                   ((JComponent)dirtyComponent).paintImmediately(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */                 
/*      */                 }
/*  834 */                 else if (dirtyComponent.isShowing()) {
/*  835 */                   Graphics graphics = JComponent.safelyGetGraphics(dirtyComponent, dirtyComponent);
/*      */ 
/*      */ 
/*      */                   
/*  839 */                   if (graphics != null) {
/*  840 */                     graphics.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */                     try {
/*  842 */                       dirtyComponent.paint(graphics);
/*      */                     } finally {
/*  844 */                       graphics.dispose();
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/*  850 */                 if (RepaintManager.this.repaintRoot != null) {
/*  851 */                   RepaintManager.this.adjustRoots(RepaintManager.this.repaintRoot, roots, i + 1);
/*  852 */                   count.set(roots.size());
/*  853 */                   RepaintManager.this.paintManager.isRepaintingRoot = true;
/*  854 */                   RepaintManager.this.repaintRoot.paintImmediately(0, 0, RepaintManager.this.repaintRoot.getWidth(), RepaintManager.this
/*  855 */                       .repaintRoot.getHeight());
/*  856 */                   RepaintManager.this.paintManager.isRepaintingRoot = false;
/*      */                   
/*  858 */                   RepaintManager.this.repaintRoot = null;
/*      */                 } 
/*      */                 
/*  861 */                 return null;
/*      */               }
/*      */             }accessControlContext1, accessControlContext2);
/*      */       } 
/*      */     } finally {
/*  866 */       this.painting = false;
/*      */     } 
/*      */     
/*  869 */     updateWindows(tmpDirtyComponents);
/*      */     
/*  871 */     tmpDirtyComponents.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustRoots(JComponent paramJComponent, List<Component> paramList, int paramInt) {
/*  881 */     for (int i = paramList.size() - 1; i >= paramInt; i--) {
/*  882 */       Component component = paramList.get(i);
/*      */       
/*  884 */       while (component != paramJComponent && component != null && component instanceof JComponent)
/*      */       {
/*      */         
/*  887 */         component = component.getParent();
/*      */       }
/*  889 */       if (component == paramJComponent)
/*  890 */         paramList.remove(i); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private RepaintManager(short paramShort) {
/*  895 */     this.tmp = new Rectangle();
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1290 */     this.repaintListeners = new ArrayList<>(1); this.doubleBufferingEnabled = !nativeDoubleBuffering; synchronized (this) { this.dirtyComponents = new IdentityHashMap<>(); this.tmpDirtyComponents = new IdentityHashMap<>(); this.bufferStrategyType = paramShort; this.hwDirtyComponents = new IdentityHashMap<>(); }  this.processingRunnable = new ProcessingRunnable();
/*      */   }
/*      */   void collectDirtyComponents(Map<Component, Rectangle> paramMap, Component paramComponent, List<Component> paramList) { Component component2 = paramComponent, component1 = component2; int n = paramComponent.getX(); int i1 = paramComponent.getY(); int i2 = paramComponent.getWidth(); int i3 = paramComponent.getHeight(); int k = 0, i = k; int m = 0, j = m; this.tmp.setBounds(paramMap.get(paramComponent)); SwingUtilities.computeIntersection(0, 0, i2, i3, this.tmp); if (this.tmp.isEmpty()) return;  while (component1 instanceof JComponent) { Container container = component1.getParent(); if (container == null) break;  component1 = container; i += n; j += i1; this.tmp.setLocation(this.tmp.x + n, this.tmp.y + i1); n = component1.getX(); i1 = component1.getY(); i2 = component1.getWidth(); i3 = component1.getHeight(); this.tmp = SwingUtilities.computeIntersection(0, 0, i2, i3, this.tmp); if (this.tmp.isEmpty()) return;  if (paramMap.get(component1) != null) { component2 = component1; k = i; m = j; }  }  if (paramComponent != component2) { this.tmp.setLocation(this.tmp.x + k - i, this.tmp.y + m - j); Rectangle rectangle = paramMap.get(component2); SwingUtilities.computeUnion(this.tmp.x, this.tmp.y, this.tmp.width, this.tmp.height, rectangle); }  if (!paramList.contains(component2)) paramList.add(component2);  }
/* 1293 */   public synchronized String toString() { StringBuffer stringBuffer = new StringBuffer(); if (this.dirtyComponents != null) stringBuffer.append("" + this.dirtyComponents);  return stringBuffer.toString(); } public Image getOffscreenBuffer(Component paramComponent, int paramInt1, int paramInt2) { RepaintManager repaintManager = getDelegate(paramComponent); if (repaintManager != null) return repaintManager.getOffscreenBuffer(paramComponent, paramInt1, paramInt2);  return _getOffscreenBuffer(paramComponent, paramInt1, paramInt2); } public Image getVolatileOffscreenBuffer(Component paramComponent, int paramInt1, int paramInt2) { RepaintManager repaintManager = getDelegate(paramComponent); if (repaintManager != null) return repaintManager.getVolatileOffscreenBuffer(paramComponent, paramInt1, paramInt2);  Window window = (paramComponent instanceof Window) ? (Window)paramComponent : SwingUtilities.getWindowAncestor(paramComponent); if (!window.isOpaque()) { Toolkit toolkit = Toolkit.getDefaultToolkit(); if (toolkit instanceof SunToolkit && ((SunToolkit)toolkit).needUpdateWindow()) return null;  }  GraphicsConfiguration graphicsConfiguration = paramComponent.getGraphicsConfiguration(); if (graphicsConfiguration == null) graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();  Dimension dimension = getDoubleBufferMaximumSize(); byte b1 = (paramInt1 < 1) ? 1 : ((paramInt1 > dimension.width) ? dimension.width : paramInt1); byte b2 = (paramInt2 < 1) ? 1 : ((paramInt2 > dimension.height) ? dimension.height : paramInt2); VolatileImage volatileImage = this.volatileMap.get(graphicsConfiguration); if (volatileImage == null || volatileImage.getWidth() < b1 || volatileImage.getHeight() < b2) { if (volatileImage != null) volatileImage.flush();  volatileImage = graphicsConfiguration.createCompatibleVolatileImage(b1, b2, volatileBufferType); this.volatileMap.put(graphicsConfiguration, volatileImage); }  return volatileImage; } private Image _getOffscreenBuffer(Component paramComponent, int paramInt1, int paramInt2) { Dimension dimension = getDoubleBufferMaximumSize(); Window window = (paramComponent instanceof Window) ? (Window)paramComponent : SwingUtilities.getWindowAncestor(paramComponent); if (!window.isOpaque()) { Toolkit toolkit = Toolkit.getDefaultToolkit(); if (toolkit instanceof SunToolkit && ((SunToolkit)toolkit).needUpdateWindow()) return null;  }  if (this.standardDoubleBuffer == null) this.standardDoubleBuffer = new DoubleBufferInfo();  DoubleBufferInfo doubleBufferInfo = this.standardDoubleBuffer; int i = (paramInt1 < 1) ? 1 : ((paramInt1 > dimension.width) ? dimension.width : paramInt1); int j = (paramInt2 < 1) ? 1 : ((paramInt2 > dimension.height) ? dimension.height : paramInt2); if (doubleBufferInfo.needsReset || (doubleBufferInfo.image != null && (doubleBufferInfo.size.width < i || doubleBufferInfo.size.height < j))) { doubleBufferInfo.needsReset = false; if (doubleBufferInfo.image != null) { doubleBufferInfo.image.flush(); doubleBufferInfo.image = null; }  i = Math.max(doubleBufferInfo.size.width, i); j = Math.max(doubleBufferInfo.size.height, j); }  Image image = doubleBufferInfo.image; if (doubleBufferInfo.image == null) { image = paramComponent.createImage(i, j); doubleBufferInfo.size = new Dimension(i, j); if (paramComponent instanceof JComponent) { ((JComponent)paramComponent).setCreatedDoubleBuffer(true); doubleBufferInfo.image = image; }  }  return image; } public void setDoubleBufferMaximumSize(Dimension paramDimension) { this.doubleBufferMaxSize = paramDimension; if (this.doubleBufferMaxSize == null) { clearImages(); } else { clearImages(paramDimension.width, paramDimension.height); }  } private void clearImages() { clearImages(0, 0); } private void clearImages(int paramInt1, int paramInt2) { if (this.standardDoubleBuffer != null && this.standardDoubleBuffer.image != null && (this.standardDoubleBuffer.image.getWidth(null) > paramInt1 || this.standardDoubleBuffer.image.getHeight(null) > paramInt2)) { this.standardDoubleBuffer.image.flush(); this.standardDoubleBuffer.image = null; }  Iterator<GraphicsConfiguration> iterator = this.volatileMap.keySet().iterator(); while (iterator.hasNext()) { GraphicsConfiguration graphicsConfiguration = iterator.next(); VolatileImage volatileImage = this.volatileMap.get(graphicsConfiguration); if (volatileImage.getWidth() > paramInt1 || volatileImage.getHeight() > paramInt2) { volatileImage.flush(); iterator.remove(); }  }  } private void addRepaintListener(SwingUtilities2.RepaintListener paramRepaintListener) { this.repaintListeners.add(paramRepaintListener); }
/*      */   public Dimension getDoubleBufferMaximumSize() { if (this.doubleBufferMaxSize == null) try { Rectangle rectangle = new Rectangle(); GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment(); for (GraphicsDevice graphicsDevice : graphicsEnvironment.getScreenDevices()) { GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration(); rectangle = rectangle.union(graphicsConfiguration.getBounds()); }  this.doubleBufferMaxSize = new Dimension(rectangle.width, rectangle.height); } catch (HeadlessException headlessException) { this.doubleBufferMaxSize = new Dimension(2147483647, 2147483647); }   return this.doubleBufferMaxSize; }
/*      */   public void setDoubleBufferingEnabled(boolean paramBoolean) { this.doubleBufferingEnabled = paramBoolean; PaintManager paintManager = getPaintManager(); if (!paramBoolean && paintManager.getClass() != PaintManager.class) setPaintManager(new PaintManager());  }
/*      */   public boolean isDoubleBufferingEnabled() { return this.doubleBufferingEnabled; }
/* 1297 */   void resetDoubleBuffer() { if (this.standardDoubleBuffer != null) this.standardDoubleBuffer.needsReset = true;  } void resetVolatileDoubleBuffer(GraphicsConfiguration paramGraphicsConfiguration) { Image image = this.volatileMap.remove(paramGraphicsConfiguration); if (image != null) image.flush();  } boolean useVolatileDoubleBuffer() { return volatileImageBufferEnabled; } private synchronized boolean isPaintingThread() { return (Thread.currentThread() == this.paintThread); } void paint(JComponent paramJComponent1, JComponent paramJComponent2, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) { PaintManager paintManager = getPaintManager(); if (!isPaintingThread()) if (paintManager.getClass() != PaintManager.class) { paintManager = new PaintManager(); paintManager.repaintManager = this; }   if (!paintManager.paint(paramJComponent1, paramJComponent2, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4)) { paramGraphics.setClip(paramInt1, paramInt2, paramInt3, paramInt4); paramJComponent1.paintToOffscreen(paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1 + paramInt3, paramInt2 + paramInt4); }  } void copyArea(JComponent paramJComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) { getPaintManager().copyArea(paramJComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramBoolean); } private void removeRepaintListener(SwingUtilities2.RepaintListener paramRepaintListener) { this.repaintListeners.remove(paramRepaintListener); }
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
/*      */   void notifyRepaintPerformed(JComponent paramJComponent, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1312 */     for (SwingUtilities2.RepaintListener repaintListener : this.repaintListeners) {
/* 1313 */       repaintListener.repaintPerformed(paramJComponent, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   void beginPaint() {
/*      */     int i;
/* 1334 */     boolean bool = false;
/*      */     
/* 1336 */     Thread thread = Thread.currentThread();
/* 1337 */     synchronized (this) {
/* 1338 */       i = this.paintDepth;
/* 1339 */       if (this.paintThread == null || thread == this.paintThread) {
/* 1340 */         this.paintThread = thread;
/* 1341 */         this.paintDepth++;
/*      */       } else {
/* 1343 */         bool = true;
/*      */       } 
/*      */     } 
/* 1346 */     if (!bool && i == 0) {
/* 1347 */       getPaintManager().beginPaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void endPaint() {
/* 1355 */     if (isPaintingThread()) {
/* 1356 */       PaintManager paintManager = null;
/* 1357 */       synchronized (this) {
/* 1358 */         if (--this.paintDepth == 0) {
/* 1359 */           paintManager = getPaintManager();
/*      */         }
/*      */       } 
/* 1362 */       if (paintManager != null) {
/* 1363 */         paintManager.endPaint();
/* 1364 */         synchronized (this) {
/* 1365 */           this.paintThread = null;
/*      */         } 
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
/*      */   boolean show(Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1379 */     return getPaintManager().show(paramContainer, paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void doubleBufferingChanged(JRootPane paramJRootPane) {
/* 1387 */     getPaintManager().doubleBufferingChanged(paramJRootPane);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setPaintManager(PaintManager paramPaintManager) {
/*      */     PaintManager paintManager;
/* 1398 */     if (paramPaintManager == null) {
/* 1399 */       paramPaintManager = new PaintManager();
/*      */     }
/*      */     
/* 1402 */     synchronized (this) {
/* 1403 */       paintManager = this.paintManager;
/* 1404 */       this.paintManager = paramPaintManager;
/* 1405 */       paramPaintManager.repaintManager = this;
/*      */     } 
/* 1407 */     if (paintManager != null) {
/* 1408 */       paintManager.dispose();
/*      */     }
/*      */   }
/*      */   
/*      */   private synchronized PaintManager getPaintManager() {
/* 1413 */     if (this.paintManager == null) {
/* 1414 */       BufferStrategyPaintManager bufferStrategyPaintManager = null;
/* 1415 */       if (this.doubleBufferingEnabled && !nativeDoubleBuffering) {
/* 1416 */         Toolkit toolkit; switch (this.bufferStrategyType) {
/*      */           case 0:
/* 1418 */             toolkit = Toolkit.getDefaultToolkit();
/* 1419 */             if (toolkit instanceof SunToolkit) {
/* 1420 */               SunToolkit sunToolkit = (SunToolkit)toolkit;
/* 1421 */               if (sunToolkit.useBufferPerWindow()) {
/* 1422 */                 bufferStrategyPaintManager = new BufferStrategyPaintManager();
/*      */               }
/*      */             } 
/*      */             break;
/*      */           case 1:
/* 1427 */             bufferStrategyPaintManager = new BufferStrategyPaintManager();
/*      */             break;
/*      */         } 
/*      */ 
/*      */ 
/*      */       
/*      */       } 
/* 1434 */       setPaintManager(bufferStrategyPaintManager);
/*      */     } 
/* 1436 */     return this.paintManager;
/*      */   }
/*      */   
/*      */   private void scheduleProcessingRunnable(AppContext paramAppContext) {
/* 1440 */     if (this.processingRunnable.markPending()) {
/* 1441 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 1442 */       if (toolkit instanceof SunToolkit) {
/* 1443 */         SunToolkit.getSystemEventQueueImplPP(paramAppContext)
/* 1444 */           .postEvent(new InvocationEvent(Toolkit.getDefaultToolkit(), this.processingRunnable));
/*      */       } else {
/*      */         
/* 1447 */         Toolkit.getDefaultToolkit().getSystemEventQueue()
/* 1448 */           .postEvent(new InvocationEvent(Toolkit.getDefaultToolkit(), this.processingRunnable));
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
/*      */   
/*      */   static class PaintManager
/*      */   {
/*      */     protected RepaintManager repaintManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isRepaintingRoot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean paint(JComponent param1JComponent1, JComponent param1JComponent2, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1485 */       boolean bool = false;
/*      */       
/* 1487 */       if (this.repaintManager.useVolatileDoubleBuffer()) {
/* 1488 */         Image image; if ((image = getValidImage(this.repaintManager
/* 1489 */             .getVolatileOffscreenBuffer(param1JComponent2, param1Int3, param1Int4))) != null) {
/* 1490 */           VolatileImage volatileImage = (VolatileImage)image;
/*      */           
/* 1492 */           GraphicsConfiguration graphicsConfiguration = param1JComponent2.getGraphicsConfiguration();
/* 1493 */           for (byte b = 0; !bool && b < 2; 
/* 1494 */             b++) {
/* 1495 */             if (volatileImage.validate(graphicsConfiguration) == 2) {
/*      */               
/* 1497 */               this.repaintManager.resetVolatileDoubleBuffer(graphicsConfiguration);
/* 1498 */               image = this.repaintManager.getVolatileOffscreenBuffer(param1JComponent2, param1Int3, param1Int4);
/*      */               
/* 1500 */               volatileImage = (VolatileImage)image;
/*      */             } 
/* 1502 */             paintDoubleBuffered(param1JComponent1, volatileImage, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */             
/* 1504 */             bool = !volatileImage.contentsLost() ? true : false;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1509 */       if (!bool) { Image image; if ((image = getValidImage(this.repaintManager
/* 1510 */             .getOffscreenBuffer(param1JComponent2, param1Int3, param1Int4))) != null) {
/*      */           
/* 1512 */           paintDoubleBuffered(param1JComponent1, image, param1Graphics, param1Int1, param1Int2, param1Int3, param1Int4);
/*      */           
/* 1514 */           bool = true;
/*      */         }  }
/* 1516 */        return bool;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void copyArea(JComponent param1JComponent, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, boolean param1Boolean) {
/* 1524 */       param1Graphics.copyArea(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void beginPaint() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void endPaint() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean show(Container param1Container, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1545 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void doubleBufferingChanged(JRootPane param1JRootPane) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void paintDoubleBuffered(JComponent param1JComponent, Image param1Image, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 1561 */       Graphics graphics = param1Image.getGraphics();
/* 1562 */       int i = Math.min(param1Int3, param1Image.getWidth(null));
/* 1563 */       int j = Math.min(param1Int4, param1Image.getHeight(null));
/*      */       try {
/*      */         int k;
/*      */         int m;
/* 1567 */         for (k = param1Int1, m = param1Int1 + param1Int3; k < m; k += i) {
/* 1568 */           int n; int i1; for (n = param1Int2, i1 = param1Int2 + param1Int4; n < i1; n += j) {
/* 1569 */             graphics.translate(-k, -n);
/* 1570 */             graphics.setClip(k, n, i, j);
/* 1571 */             if (RepaintManager.volatileBufferType != 1 && graphics instanceof Graphics2D) {
/*      */               
/* 1573 */               Graphics2D graphics2D = (Graphics2D)graphics;
/* 1574 */               Color color = graphics2D.getBackground();
/* 1575 */               graphics2D.setBackground(param1JComponent.getBackground());
/* 1576 */               graphics2D.clearRect(k, n, i, j);
/* 1577 */               graphics2D.setBackground(color);
/*      */             } 
/* 1579 */             param1JComponent.paintToOffscreen(graphics, k, n, i, j, m, i1);
/* 1580 */             param1Graphics.setClip(k, n, i, j);
/* 1581 */             if (RepaintManager.volatileBufferType != 1 && param1Graphics instanceof Graphics2D) {
/*      */               
/* 1583 */               Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 1584 */               Composite composite = graphics2D.getComposite();
/* 1585 */               graphics2D.setComposite(AlphaComposite.Src);
/* 1586 */               graphics2D.drawImage(param1Image, k, n, param1JComponent);
/* 1587 */               graphics2D.setComposite(composite);
/*      */             } else {
/* 1589 */               param1Graphics.drawImage(param1Image, k, n, param1JComponent);
/*      */             } 
/* 1591 */             graphics.translate(k, n);
/*      */           } 
/*      */         } 
/*      */       } finally {
/* 1595 */         graphics.dispose();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Image getValidImage(Image param1Image) {
/* 1604 */       if (param1Image != null && param1Image.getWidth(null) > 0 && param1Image
/* 1605 */         .getHeight(null) > 0) {
/* 1606 */         return param1Image;
/*      */       }
/* 1608 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void repaintRoot(JComponent param1JComponent) {
/* 1618 */       assert this.repaintManager.repaintRoot == null;
/* 1619 */       if (this.repaintManager.painting) {
/* 1620 */         this.repaintManager.repaintRoot = param1JComponent;
/*      */       } else {
/*      */         
/* 1623 */         param1JComponent.repaint();
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean isRepaintingRoot() {
/* 1632 */       return this.isRepaintingRoot;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void dispose() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class DoubleBufferInfo
/*      */   {
/*      */     public Image image;
/*      */ 
/*      */     
/*      */     public Dimension size;
/*      */ 
/*      */ 
/*      */     
/*      */     private DoubleBufferInfo() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean needsReset = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DisplayChangedHandler
/*      */     implements DisplayChangedListener
/*      */   {
/*      */     public void displayChanged() {
/* 1666 */       scheduleDisplayChanges();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void paletteChanged() {}
/*      */ 
/*      */     
/*      */     private static void scheduleDisplayChanges() {
/* 1675 */       for (AppContext appContext : AppContext.getAppContexts()) {
/* 1676 */         synchronized (appContext) {
/* 1677 */           if (!appContext.isDisposed()) {
/* 1678 */             EventQueue eventQueue = (EventQueue)appContext.get(AppContext.EVENT_QUEUE_KEY);
/*      */             
/* 1680 */             if (eventQueue != null)
/* 1681 */               eventQueue.postEvent(new InvocationEvent(
/* 1682 */                     Toolkit.getDefaultToolkit(), new RepaintManager.DisplayChangedRunnable())); 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static final class DisplayChangedRunnable
/*      */     implements Runnable {
/*      */     private DisplayChangedRunnable() {}
/*      */     
/*      */     public void run() {
/* 1694 */       RepaintManager.currentManager((JComponent)null).displayChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ProcessingRunnable
/*      */     implements Runnable
/*      */   {
/*      */     private boolean pending;
/*      */ 
/*      */ 
/*      */     
/*      */     private ProcessingRunnable() {}
/*      */ 
/*      */     
/*      */     public synchronized boolean markPending() {
/* 1711 */       if (!this.pending) {
/* 1712 */         this.pending = true;
/* 1713 */         return true;
/*      */       } 
/* 1715 */       return false;
/*      */     }
/*      */     
/*      */     public void run() {
/* 1719 */       synchronized (this) {
/* 1720 */         this.pending = false;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1729 */       RepaintManager.this.scheduleHeavyWeightPaints();
/*      */       
/* 1731 */       RepaintManager.this.validateInvalidComponents();
/* 1732 */       RepaintManager.this.prePaintDirtyRegions();
/*      */     } }
/*      */   
/*      */   private RepaintManager getDelegate(Component paramComponent) {
/* 1736 */     RepaintManager repaintManager = SwingUtilities3.getDelegateRepaintManager(paramComponent);
/* 1737 */     if (this == repaintManager) {
/* 1738 */       repaintManager = null;
/*      */     }
/* 1740 */     return repaintManager;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/RepaintManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */