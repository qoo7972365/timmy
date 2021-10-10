/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import sun.awt.EmbeddedFrame;
/*     */ import sun.awt.OSInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PopupFactory
/*     */ {
/*  68 */   private static final Object SharedInstanceKey = new StringBuffer("PopupFactory.SharedInstanceKey");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_CACHE_SIZE = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int LIGHT_WEIGHT_POPUP = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int MEDIUM_WEIGHT_POPUP = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final int HEAVY_WEIGHT_POPUP = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   private int popupType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setSharedInstance(PopupFactory paramPopupFactory) {
/* 108 */     if (paramPopupFactory == null) {
/* 109 */       throw new IllegalArgumentException("PopupFactory can not be null");
/*     */     }
/* 111 */     SwingUtilities.appContextPut(SharedInstanceKey, paramPopupFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PopupFactory getSharedInstance() {
/* 121 */     PopupFactory popupFactory = (PopupFactory)SwingUtilities.appContextGet(SharedInstanceKey);
/*     */ 
/*     */     
/* 124 */     if (popupFactory == null) {
/* 125 */       popupFactory = new PopupFactory();
/* 126 */       setSharedInstance(popupFactory);
/*     */     } 
/* 128 */     return popupFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setPopupType(int paramInt) {
/* 137 */     this.popupType = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getPopupType() {
/* 144 */     return this.popupType;
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
/*     */   public Popup getPopup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) throws IllegalArgumentException {
/* 168 */     if (paramComponent2 == null) {
/* 169 */       throw new IllegalArgumentException("Popup.getPopup must be passed non-null contents");
/*     */     }
/*     */ 
/*     */     
/* 173 */     int i = getPopupType(paramComponent1, paramComponent2, paramInt1, paramInt2);
/* 174 */     Popup popup = getPopup(paramComponent1, paramComponent2, paramInt1, paramInt2, i);
/*     */     
/* 176 */     if (popup == null)
/*     */     {
/* 178 */       popup = getPopup(paramComponent1, paramComponent2, paramInt1, paramInt2, 2);
/*     */     }
/* 180 */     return popup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPopupType(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/* 188 */     int i = getPopupType();
/*     */     
/* 190 */     if (paramComponent1 == null || invokerInHeavyWeightPopup(paramComponent1)) {
/* 191 */       i = 2;
/*     */     }
/* 193 */     else if (i == 0 && !(paramComponent2 instanceof JToolTip) && !(paramComponent2 instanceof JPopupMenu)) {
/*     */ 
/*     */       
/* 196 */       i = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     Component component = paramComponent1;
/* 203 */     while (component != null) {
/* 204 */       if (component instanceof JComponent && (
/* 205 */         (JComponent)component).getClientProperty(ClientPropertyKey.PopupFactory_FORCE_HEAVYWEIGHT_POPUP) == Boolean.TRUE) {
/*     */         
/* 207 */         i = 2;
/*     */         
/*     */         break;
/*     */       } 
/* 211 */       component = component.getParent();
/*     */     } 
/*     */     
/* 214 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Popup getPopup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2, int paramInt3) {
/*     */     Popup popup;
/* 223 */     if (GraphicsEnvironment.isHeadless()) {
/* 224 */       return getHeadlessPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 227 */     switch (paramInt3) {
/*     */       case 0:
/* 229 */         return getLightWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */       case 1:
/* 231 */         return getMediumWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */       case 2:
/* 233 */         popup = getHeavyWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/* 234 */         if (AccessController.doPrivileged(OSInfo.getOSTypeAction()) == OSInfo.OSType.MACOSX && paramComponent1 != null && 
/*     */           
/* 236 */           EmbeddedFrame.getAppletIfAncestorOf(paramComponent1) != null) {
/* 237 */           ((HeavyWeightPopup)popup).setCacheEnabled(false);
/*     */         }
/* 239 */         return popup;
/*     */     } 
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Popup getHeadlessPopup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/* 249 */     return HeadlessPopup.getHeadlessPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Popup getLightWeightPopup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/* 257 */     return LightWeightPopup.getLightWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Popup getMediumWeightPopup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/* 266 */     return MediumWeightPopup.getMediumWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Popup getHeavyWeightPopup(Component paramComponent1, Component paramComponent2, int paramInt1, int paramInt2) {
/* 275 */     if (GraphicsEnvironment.isHeadless()) {
/* 276 */       return getMediumWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */     }
/* 278 */     return HeavyWeightPopup.getHeavyWeightPopup(paramComponent1, paramComponent2, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean invokerInHeavyWeightPopup(Component paramComponent) {
/* 287 */     if (paramComponent != null)
/*     */     {
/* 289 */       for (Container container = paramComponent.getParent(); container != null; 
/* 290 */         container = container.getParent()) {
/* 291 */         if (container instanceof Popup.HeavyWeightWindow) {
/* 292 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/* 296 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class HeavyWeightPopup
/*     */     extends Popup
/*     */   {
/* 304 */     private static final Object heavyWeightPopupCacheKey = new StringBuffer("PopupFactory.heavyWeightPopupCache");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private volatile boolean isCacheEnabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Popup getHeavyWeightPopup(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 316 */       Window window = (param1Component1 != null) ? SwingUtilities.getWindowAncestor(param1Component1) : null;
/* 317 */       HeavyWeightPopup heavyWeightPopup = null;
/*     */       
/* 319 */       if (window != null) {
/* 320 */         heavyWeightPopup = getRecycledHeavyWeightPopup(window);
/*     */       }
/*     */       
/* 323 */       boolean bool = false;
/* 324 */       if (param1Component2 != null && param1Component2.isFocusable() && 
/* 325 */         param1Component2 instanceof JPopupMenu) {
/* 326 */         JPopupMenu jPopupMenu = (JPopupMenu)param1Component2;
/* 327 */         Component[] arrayOfComponent = jPopupMenu.getComponents();
/* 328 */         for (Component component : arrayOfComponent) {
/* 329 */           if (!(component instanceof MenuElement) && !(component instanceof JSeparator)) {
/*     */             
/* 331 */             bool = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 338 */       if (heavyWeightPopup == null || ((JWindow)heavyWeightPopup
/* 339 */         .getComponent())
/* 340 */         .getFocusableWindowState() != bool) {
/*     */         
/* 342 */         if (heavyWeightPopup != null)
/*     */         {
/*     */           
/* 345 */           heavyWeightPopup._dispose();
/*     */         }
/*     */         
/* 348 */         heavyWeightPopup = new HeavyWeightPopup();
/*     */       } 
/*     */       
/* 351 */       heavyWeightPopup.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/*     */       
/* 353 */       if (bool) {
/* 354 */         JWindow jWindow = (JWindow)heavyWeightPopup.getComponent();
/* 355 */         jWindow.setFocusableWindowState(true);
/*     */ 
/*     */         
/* 358 */         jWindow.setName("###focusableSwingPopup###");
/*     */       } 
/*     */       
/* 361 */       return heavyWeightPopup;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static HeavyWeightPopup getRecycledHeavyWeightPopup(Window param1Window) {
/* 371 */       synchronized (HeavyWeightPopup.class) {
/*     */         List<HeavyWeightPopup> list;
/* 373 */         Map<Window, List<HeavyWeightPopup>> map = getHeavyWeightPopupCache();
/*     */         
/* 375 */         if (map.containsKey(param1Window)) {
/* 376 */           list = map.get(param1Window);
/*     */         } else {
/* 378 */           return null;
/*     */         } 
/* 380 */         if (list.size() > 0) {
/* 381 */           HeavyWeightPopup heavyWeightPopup = list.get(0);
/* 382 */           list.remove(0);
/* 383 */           return heavyWeightPopup;
/*     */         } 
/* 385 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static Map<Window, List<HeavyWeightPopup>> getHeavyWeightPopupCache() {
/* 395 */       synchronized (HeavyWeightPopup.class) {
/* 396 */         Map<Object, Object> map = (Map)SwingUtilities.appContextGet(heavyWeightPopupCacheKey);
/*     */ 
/*     */         
/* 399 */         if (map == null) {
/* 400 */           map = new HashMap<>(2);
/* 401 */           SwingUtilities.appContextPut(heavyWeightPopupCacheKey, map);
/*     */         } 
/*     */         
/* 404 */         return (Map)map;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void recycleHeavyWeightPopup(HeavyWeightPopup param1HeavyWeightPopup) {
/* 412 */       synchronized (HeavyWeightPopup.class) {
/*     */         List<HeavyWeightPopup> list;
/* 414 */         Window window = SwingUtilities.getWindowAncestor(param1HeavyWeightPopup
/* 415 */             .getComponent());
/* 416 */         Map<Window, List<HeavyWeightPopup>> map = getHeavyWeightPopupCache();
/*     */         
/* 418 */         if (window instanceof Popup.DefaultFrame || 
/* 419 */           !window.isVisible()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 425 */           param1HeavyWeightPopup._dispose(); return;
/*     */         } 
/* 427 */         if (map.containsKey(window)) {
/* 428 */           list = map.get(window);
/*     */         } else {
/* 430 */           list = new ArrayList();
/* 431 */           map.put(window, list);
/*     */           
/* 433 */           final Window w = window;
/*     */           
/* 435 */           window1.addWindowListener(new WindowAdapter()
/*     */               {
/*     */                 public void windowClosed(WindowEvent param2WindowEvent) {
/*     */                   List<PopupFactory.HeavyWeightPopup> list;
/* 439 */                   synchronized (PopupFactory.HeavyWeightPopup.class) {
/*     */                     
/* 441 */                     Map map = PopupFactory.HeavyWeightPopup.getHeavyWeightPopupCache();
/*     */                     
/* 443 */                     list = (List)map.remove(w);
/*     */                   } 
/* 445 */                   if (list != null) {
/* 446 */                     int i = list.size() - 1;
/* 447 */                     for (; i >= 0; i--) {
/* 448 */                       ((PopupFactory.HeavyWeightPopup)list.get(i))._dispose();
/*     */                     }
/*     */                   } 
/*     */                 }
/*     */               });
/*     */         } 
/*     */         
/* 455 */         if (list.size() < 5) {
/* 456 */           list.add(param1HeavyWeightPopup);
/*     */         } else {
/* 458 */           param1HeavyWeightPopup._dispose();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCacheEnabled(boolean param1Boolean) {
/* 467 */       this.isCacheEnabled = param1Boolean;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void hide() {
/* 474 */       super.hide();
/* 475 */       if (this.isCacheEnabled) {
/* 476 */         recycleHeavyWeightPopup(this);
/*     */       } else {
/* 478 */         _dispose();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void _dispose() {
/* 491 */       super.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ContainerPopup
/*     */     extends Popup
/*     */   {
/*     */     Component owner;
/*     */     
/*     */     int x;
/*     */     
/*     */     int y;
/*     */ 
/*     */     
/*     */     private ContainerPopup() {}
/*     */ 
/*     */     
/*     */     public void hide() {
/* 510 */       Component component = getComponent();
/*     */       
/* 512 */       if (component != null) {
/* 513 */         Container container = component.getParent();
/*     */         
/* 515 */         if (container != null) {
/* 516 */           Rectangle rectangle = component.getBounds();
/*     */           
/* 518 */           container.remove(component);
/* 519 */           container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*     */         } 
/*     */       } 
/*     */       
/* 523 */       this.owner = null;
/*     */     }
/*     */     public void pack() {
/* 526 */       Component component = getComponent();
/*     */       
/* 528 */       if (component != null) {
/* 529 */         component.setSize(component.getPreferredSize());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void reset(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 535 */       if (param1Component1 instanceof JFrame || param1Component1 instanceof JDialog || param1Component1 instanceof JWindow)
/*     */       {
/*     */ 
/*     */         
/* 539 */         param1Component1 = ((RootPaneContainer)param1Component1).getLayeredPane();
/*     */       }
/* 541 */       super.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/*     */       
/* 543 */       this.x = param1Int1;
/* 544 */       this.y = param1Int2;
/* 545 */       this.owner = param1Component1;
/*     */     }
/*     */     
/*     */     boolean overlappedByOwnedWindow() {
/* 549 */       Component component = getComponent();
/* 550 */       if (this.owner != null && component != null) {
/* 551 */         Window window = SwingUtilities.getWindowAncestor(this.owner);
/* 552 */         if (window == null) {
/* 553 */           return false;
/*     */         }
/* 555 */         Window[] arrayOfWindow = window.getOwnedWindows();
/* 556 */         if (arrayOfWindow != null) {
/* 557 */           Rectangle rectangle = component.getBounds();
/* 558 */           for (Window window1 : arrayOfWindow) {
/* 559 */             if (window1.isVisible() && rectangle
/* 560 */               .intersects(window1.getBounds()))
/*     */             {
/* 562 */               return true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 567 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean fitsOnScreen() {
/* 575 */       boolean bool = false;
/* 576 */       Component component = getComponent();
/* 577 */       if (this.owner != null && component != null) {
/* 578 */         int i = component.getWidth();
/* 579 */         int j = component.getHeight();
/*     */         
/* 581 */         Container container = (Container)SwingUtilities.getRoot(this.owner);
/* 582 */         if (container instanceof JFrame || container instanceof JDialog || container instanceof JWindow) {
/*     */ 
/*     */ 
/*     */           
/* 586 */           Rectangle rectangle = container.getBounds();
/* 587 */           Insets insets = container.getInsets();
/* 588 */           rectangle.x += insets.left;
/* 589 */           rectangle.y += insets.top;
/* 590 */           rectangle.width -= insets.left + insets.right;
/* 591 */           rectangle.height -= insets.top + insets.bottom;
/*     */           
/* 593 */           if (JPopupMenu.canPopupOverlapTaskBar()) {
/*     */             
/* 595 */             GraphicsConfiguration graphicsConfiguration = container.getGraphicsConfiguration();
/* 596 */             Rectangle rectangle1 = getContainerPopupArea(graphicsConfiguration);
/*     */             
/* 598 */             bool = rectangle.intersection(rectangle1).contains(this.x, this.y, i, j);
/*     */           } else {
/*     */             
/* 601 */             bool = rectangle.contains(this.x, this.y, i, j);
/*     */           } 
/* 603 */         } else if (container instanceof JApplet) {
/* 604 */           Rectangle rectangle = container.getBounds();
/* 605 */           Point point = container.getLocationOnScreen();
/* 606 */           rectangle.x = point.x;
/* 607 */           rectangle.y = point.y;
/* 608 */           bool = rectangle.contains(this.x, this.y, i, j);
/*     */         } 
/*     */       } 
/* 611 */       return bool;
/*     */     }
/*     */     Rectangle getContainerPopupArea(GraphicsConfiguration param1GraphicsConfiguration) {
/*     */       Rectangle rectangle;
/*     */       Insets insets;
/* 616 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*     */       
/* 618 */       if (param1GraphicsConfiguration != null) {
/*     */ 
/*     */         
/* 621 */         rectangle = param1GraphicsConfiguration.getBounds();
/* 622 */         insets = toolkit.getScreenInsets(param1GraphicsConfiguration);
/*     */       } else {
/*     */         
/* 625 */         rectangle = new Rectangle(toolkit.getScreenSize());
/* 626 */         insets = new Insets(0, 0, 0, 0);
/*     */       } 
/*     */       
/* 629 */       rectangle.x += insets.left;
/* 630 */       rectangle.y += insets.top;
/* 631 */       rectangle.width -= insets.left + insets.right;
/* 632 */       rectangle.height -= insets.top + insets.bottom;
/* 633 */       return rectangle;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class HeadlessPopup
/*     */     extends ContainerPopup
/*     */   {
/*     */     static Popup getHeadlessPopup(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 644 */       HeadlessPopup headlessPopup = new HeadlessPopup();
/* 645 */       headlessPopup.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/* 646 */       return headlessPopup;
/*     */     }
/*     */     
/*     */     Component createComponent(Component param1Component) {
/* 650 */       return new Panel(new BorderLayout());
/*     */     }
/*     */ 
/*     */     
/*     */     public void show() {}
/*     */ 
/*     */     
/*     */     public void hide() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LightWeightPopup
/*     */     extends ContainerPopup
/*     */   {
/* 664 */     private static final Object lightWeightPopupCacheKey = new StringBuffer("PopupFactory.lightPopupCache");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Popup getLightWeightPopup(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 674 */       LightWeightPopup lightWeightPopup = getRecycledLightWeightPopup();
/*     */       
/* 676 */       if (lightWeightPopup == null) {
/* 677 */         lightWeightPopup = new LightWeightPopup();
/*     */       }
/* 679 */       lightWeightPopup.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/* 680 */       if (!lightWeightPopup.fitsOnScreen() || lightWeightPopup
/* 681 */         .overlappedByOwnedWindow()) {
/* 682 */         lightWeightPopup.hide();
/* 683 */         return null;
/*     */       } 
/* 685 */       return lightWeightPopup;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static List<LightWeightPopup> getLightWeightPopupCache() {
/* 692 */       List<LightWeightPopup> list = (List)SwingUtilities.appContextGet(lightWeightPopupCacheKey);
/*     */       
/* 694 */       if (list == null) {
/* 695 */         list = new ArrayList();
/* 696 */         SwingUtilities.appContextPut(lightWeightPopupCacheKey, list);
/*     */       } 
/* 698 */       return list;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void recycleLightWeightPopup(LightWeightPopup param1LightWeightPopup) {
/* 705 */       synchronized (LightWeightPopup.class) {
/* 706 */         List<LightWeightPopup> list = getLightWeightPopupCache();
/* 707 */         if (list.size() < 5) {
/* 708 */           list.add(param1LightWeightPopup);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static LightWeightPopup getRecycledLightWeightPopup() {
/* 718 */       synchronized (LightWeightPopup.class) {
/* 719 */         List<LightWeightPopup> list = getLightWeightPopupCache();
/* 720 */         if (list.size() > 0) {
/* 721 */           LightWeightPopup lightWeightPopup = list.get(0);
/* 722 */           list.remove(0);
/* 723 */           return lightWeightPopup;
/*     */         } 
/* 725 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void hide() {
/* 735 */       super.hide();
/*     */       
/* 737 */       Container container = (Container)getComponent();
/*     */       
/* 739 */       container.removeAll();
/* 740 */       recycleLightWeightPopup(this);
/*     */     }
/*     */     public void show() {
/* 743 */       Container container1 = null;
/*     */       
/* 745 */       if (this.owner != null) {
/* 746 */         container1 = (this.owner instanceof Container) ? (Container)this.owner : this.owner.getParent();
/*     */       }
/*     */ 
/*     */       
/* 750 */       for (Container container2 = container1; container2 != null; container2 = container2.getParent()) {
/* 751 */         if (container2 instanceof JRootPane) {
/* 752 */           if (!(container2.getParent() instanceof JInternalFrame))
/*     */           {
/*     */             
/* 755 */             container1 = ((JRootPane)container2).getLayeredPane();
/*     */           }
/*     */         } else {
/* 758 */           if (container2 instanceof Window) {
/* 759 */             if (container1 == null)
/* 760 */               container1 = container2; 
/*     */             break;
/*     */           } 
/* 763 */           if (container2 instanceof JApplet) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 771 */       Point point = SwingUtilities.convertScreenLocationToParent(container1, this.x, this.y);
/*     */       
/* 773 */       Component component = getComponent();
/*     */       
/* 775 */       component.setLocation(point.x, point.y);
/* 776 */       if (container1 instanceof JLayeredPane) {
/* 777 */         container1.add(component, JLayeredPane.POPUP_LAYER, 0);
/*     */       } else {
/* 779 */         container1.add(component);
/*     */       } 
/*     */     }
/*     */     
/*     */     Component createComponent(Component param1Component) {
/* 784 */       JPanel jPanel = new JPanel(new BorderLayout(), true);
/*     */       
/* 786 */       jPanel.setOpaque(true);
/* 787 */       return jPanel;
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
/*     */     void reset(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 799 */       super.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/*     */       
/* 801 */       JComponent jComponent = (JComponent)getComponent();
/*     */       
/* 803 */       jComponent.setOpaque(param1Component2.isOpaque());
/* 804 */       jComponent.setLocation(param1Int1, param1Int2);
/* 805 */       jComponent.add(param1Component2, "Center");
/* 806 */       param1Component2.invalidate();
/* 807 */       pack();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class MediumWeightPopup
/*     */     extends ContainerPopup
/*     */   {
/* 816 */     private static final Object mediumWeightPopupCacheKey = new StringBuffer("PopupFactory.mediumPopupCache");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private JRootPane rootPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Popup getMediumWeightPopup(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 830 */       MediumWeightPopup mediumWeightPopup = getRecycledMediumWeightPopup();
/*     */       
/* 832 */       if (mediumWeightPopup == null) {
/* 833 */         mediumWeightPopup = new MediumWeightPopup();
/*     */       }
/* 835 */       mediumWeightPopup.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/* 836 */       if (!mediumWeightPopup.fitsOnScreen() || mediumWeightPopup
/* 837 */         .overlappedByOwnedWindow()) {
/* 838 */         mediumWeightPopup.hide();
/* 839 */         return null;
/*     */       } 
/* 841 */       return mediumWeightPopup;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static List<MediumWeightPopup> getMediumWeightPopupCache() {
/* 848 */       List<MediumWeightPopup> list = (List)SwingUtilities.appContextGet(mediumWeightPopupCacheKey);
/*     */ 
/*     */       
/* 851 */       if (list == null) {
/* 852 */         list = new ArrayList();
/* 853 */         SwingUtilities.appContextPut(mediumWeightPopupCacheKey, list);
/*     */       } 
/* 855 */       return list;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void recycleMediumWeightPopup(MediumWeightPopup param1MediumWeightPopup) {
/* 862 */       synchronized (MediumWeightPopup.class) {
/* 863 */         List<MediumWeightPopup> list = getMediumWeightPopupCache();
/* 864 */         if (list.size() < 5) {
/* 865 */           list.add(param1MediumWeightPopup);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static MediumWeightPopup getRecycledMediumWeightPopup() {
/* 875 */       synchronized (MediumWeightPopup.class) {
/* 876 */         List<MediumWeightPopup> list = getMediumWeightPopupCache();
/* 877 */         if (list.size() > 0) {
/* 878 */           MediumWeightPopup mediumWeightPopup = list.get(0);
/* 879 */           list.remove(0);
/* 880 */           return mediumWeightPopup;
/*     */         } 
/* 882 */         return null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void hide() {
/* 892 */       super.hide();
/* 893 */       this.rootPane.getContentPane().removeAll();
/* 894 */       recycleMediumWeightPopup(this);
/*     */     }
/*     */     public void show() {
/* 897 */       Component component = getComponent();
/* 898 */       Container container = null;
/*     */       
/* 900 */       if (this.owner != null) {
/* 901 */         container = this.owner.getParent();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 908 */       while (!(container instanceof Window) && !(container instanceof java.applet.Applet) && container != null)
/*     */       {
/* 910 */         container = container.getParent();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 916 */       if (container instanceof RootPaneContainer) {
/* 917 */         container = ((RootPaneContainer)container).getLayeredPane();
/* 918 */         Point point = SwingUtilities.convertScreenLocationToParent(container, this.x, this.y);
/*     */         
/* 920 */         component.setVisible(false);
/* 921 */         component.setLocation(point.x, point.y);
/* 922 */         container.add(component, JLayeredPane.POPUP_LAYER, 0);
/*     */       } else {
/*     */         
/* 925 */         Point point = SwingUtilities.convertScreenLocationToParent(container, this.x, this.y);
/*     */ 
/*     */         
/* 928 */         component.setLocation(point.x, point.y);
/* 929 */         component.setVisible(false);
/* 930 */         container.add(component);
/*     */       } 
/* 932 */       component.setVisible(true);
/*     */     }
/*     */     
/*     */     Component createComponent(Component param1Component) {
/* 936 */       MediumWeightComponent mediumWeightComponent = new MediumWeightComponent();
/*     */       
/* 938 */       this.rootPane = new JRootPane();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 943 */       this.rootPane.setOpaque(true);
/* 944 */       mediumWeightComponent.add(this.rootPane, "Center");
/* 945 */       return mediumWeightComponent;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void reset(Component param1Component1, Component param1Component2, int param1Int1, int param1Int2) {
/* 953 */       super.reset(param1Component1, param1Component2, param1Int1, param1Int2);
/*     */       
/* 955 */       Component component = getComponent();
/*     */       
/* 957 */       component.setLocation(param1Int1, param1Int2);
/* 958 */       this.rootPane.getContentPane().add(param1Component2, "Center");
/* 959 */       param1Component2.invalidate();
/* 960 */       component.validate();
/* 961 */       pack();
/*     */     }
/*     */ 
/*     */     
/*     */     private static class MediumWeightComponent
/*     */       extends Panel
/*     */       implements SwingHeavyWeight
/*     */     {
/*     */       MediumWeightComponent() {
/* 970 */         super(new BorderLayout());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/PopupFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */