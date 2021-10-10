/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.HeadlessException;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Window;
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.security.AccessController;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.swing.event.MenuDragMouseEvent;
/*      */ import javax.swing.text.View;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ import sun.swing.SwingUtilities2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SwingUtilities
/*      */   implements SwingConstants
/*      */ {
/*      */   private static boolean canAccessEventQueue = false;
/*      */   private static boolean eventQueueTested = false;
/*      */   private static boolean suppressDropSupport;
/*      */   private static boolean checkedSuppressDropSupport;
/*      */   
/*      */   private static boolean getSuppressDropTarget() {
/*   79 */     if (!checkedSuppressDropSupport) {
/*   80 */       suppressDropSupport = Boolean.valueOf(
/*   81 */           AccessController.<String>doPrivileged(new GetPropertyAction("suppressSwingDropSupport"))).booleanValue();
/*      */       
/*   83 */       checkedSuppressDropSupport = true;
/*      */     } 
/*   85 */     return suppressDropSupport;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void installSwingDropTargetAsNecessary(Component paramComponent, TransferHandler paramTransferHandler) {
/*   95 */     if (!getSuppressDropTarget()) {
/*   96 */       DropTarget dropTarget = paramComponent.getDropTarget();
/*   97 */       if (dropTarget == null || dropTarget instanceof javax.swing.plaf.UIResource) {
/*   98 */         if (paramTransferHandler == null) {
/*   99 */           paramComponent.setDropTarget(null);
/*  100 */         } else if (!GraphicsEnvironment.isHeadless()) {
/*  101 */           paramComponent.setDropTarget(new TransferHandler.SwingDropTarget(paramComponent));
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final boolean isRectangleContainingRectangle(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/*  111 */     return (paramRectangle2.x >= paramRectangle1.x && paramRectangle2.x + paramRectangle2.width <= paramRectangle1.x + paramRectangle1.width && paramRectangle2.y >= paramRectangle1.y && paramRectangle2.y + paramRectangle2.height <= paramRectangle1.y + paramRectangle1.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Rectangle getLocalBounds(Component paramComponent) {
/*  119 */     Rectangle rectangle = new Rectangle(paramComponent.getBounds());
/*  120 */     rectangle.x = rectangle.y = 0;
/*  121 */     return rectangle;
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
/*      */   public static Window getWindowAncestor(Component paramComponent) {
/*  137 */     for (Container container = paramComponent.getParent(); container != null; container = container.getParent()) {
/*  138 */       if (container instanceof Window) {
/*  139 */         return (Window)container;
/*      */       }
/*      */     } 
/*  142 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Point convertScreenLocationToParent(Container paramContainer, int paramInt1, int paramInt2) {
/*  150 */     for (Container container = paramContainer; container != null; container = container.getParent()) {
/*  151 */       if (container instanceof Window) {
/*  152 */         Point point = new Point(paramInt1, paramInt2);
/*      */         
/*  154 */         convertPointFromScreen(point, paramContainer);
/*  155 */         return point;
/*      */       } 
/*      */     } 
/*  158 */     throw new Error("convertScreenLocationToParent: no window ancestor");
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
/*      */   public static Point convertPoint(Component paramComponent1, Point paramPoint, Component paramComponent2) {
/*  174 */     if (paramComponent1 == null && paramComponent2 == null)
/*  175 */       return paramPoint; 
/*  176 */     if (paramComponent1 == null) {
/*  177 */       paramComponent1 = getWindowAncestor(paramComponent2);
/*  178 */       if (paramComponent1 == null)
/*  179 */         throw new Error("Source component not connected to component tree hierarchy"); 
/*      */     } 
/*  181 */     Point point = new Point(paramPoint);
/*  182 */     convertPointToScreen(point, paramComponent1);
/*  183 */     if (paramComponent2 == null) {
/*  184 */       paramComponent2 = getWindowAncestor(paramComponent1);
/*  185 */       if (paramComponent2 == null)
/*  186 */         throw new Error("Destination component not connected to component tree hierarchy"); 
/*      */     } 
/*  188 */     convertPointFromScreen(point, paramComponent2);
/*  189 */     return point;
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
/*      */   public static Point convertPoint(Component paramComponent1, int paramInt1, int paramInt2, Component paramComponent2) {
/*  203 */     Point point = new Point(paramInt1, paramInt2);
/*  204 */     return convertPoint(paramComponent1, point, paramComponent2);
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
/*      */   public static Rectangle convertRectangle(Component paramComponent1, Rectangle paramRectangle, Component paramComponent2) {
/*  218 */     Point point = new Point(paramRectangle.x, paramRectangle.y);
/*  219 */     point = convertPoint(paramComponent1, point, paramComponent2);
/*  220 */     return new Rectangle(point.x, point.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Container getAncestorOfClass(Class<?> paramClass, Component paramComponent) {
/*  230 */     if (paramComponent == null || paramClass == null) {
/*  231 */       return null;
/*      */     }
/*  233 */     Container container = paramComponent.getParent();
/*  234 */     while (container != null && !paramClass.isInstance(container))
/*  235 */       container = container.getParent(); 
/*  236 */     return container;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Container getAncestorNamed(String paramString, Component paramComponent) {
/*  245 */     if (paramComponent == null || paramString == null) {
/*  246 */       return null;
/*      */     }
/*  248 */     Container container = paramComponent.getParent();
/*  249 */     while (container != null && !paramString.equals(container.getName()))
/*  250 */       container = container.getParent(); 
/*  251 */     return container;
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
/*      */   public static Component getDeepestComponentAt(Component paramComponent, int paramInt1, int paramInt2) {
/*  267 */     if (!paramComponent.contains(paramInt1, paramInt2)) {
/*  268 */       return null;
/*      */     }
/*  270 */     if (paramComponent instanceof Container) {
/*  271 */       Component[] arrayOfComponent = ((Container)paramComponent).getComponents();
/*  272 */       for (Component component : arrayOfComponent) {
/*  273 */         if (component != null && component.isVisible()) {
/*  274 */           Point point = component.getLocation();
/*  275 */           if (component instanceof Container) {
/*  276 */             component = getDeepestComponentAt(component, paramInt1 - point.x, paramInt2 - point.y);
/*      */           } else {
/*  278 */             component = component.getComponentAt(paramInt1 - point.x, paramInt2 - point.y);
/*      */           } 
/*  280 */           if (component != null && component.isVisible()) {
/*  281 */             return component;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  286 */     return paramComponent;
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
/*      */   public static MouseEvent convertMouseEvent(Component paramComponent1, MouseEvent paramMouseEvent, Component paramComponent2) {
/*      */     Component component;
/*      */     MouseEvent mouseEvent;
/*  306 */     Point point = convertPoint(paramComponent1, new Point(paramMouseEvent.getX(), paramMouseEvent
/*  307 */           .getY()), paramComponent2);
/*      */ 
/*      */ 
/*      */     
/*  311 */     if (paramComponent2 != null) {
/*  312 */       component = paramComponent2;
/*      */     } else {
/*  314 */       component = paramComponent1;
/*      */     } 
/*      */     
/*  317 */     if (paramMouseEvent instanceof MouseWheelEvent) {
/*  318 */       MouseWheelEvent mouseWheelEvent = (MouseWheelEvent)paramMouseEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  331 */       mouseEvent = new MouseWheelEvent(component, mouseWheelEvent.getID(), mouseWheelEvent.getWhen(), mouseWheelEvent.getModifiers() | mouseWheelEvent.getModifiersEx(), point.x, point.y, mouseWheelEvent.getXOnScreen(), mouseWheelEvent.getYOnScreen(), mouseWheelEvent.getClickCount(), mouseWheelEvent.isPopupTrigger(), mouseWheelEvent.getScrollType(), mouseWheelEvent.getScrollAmount(), mouseWheelEvent.getWheelRotation());
/*      */     }
/*  333 */     else if (paramMouseEvent instanceof MenuDragMouseEvent) {
/*  334 */       MenuDragMouseEvent menuDragMouseEvent = (MenuDragMouseEvent)paramMouseEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  346 */       mouseEvent = new MenuDragMouseEvent(component, menuDragMouseEvent.getID(), menuDragMouseEvent.getWhen(), menuDragMouseEvent.getModifiers() | menuDragMouseEvent.getModifiersEx(), point.x, point.y, menuDragMouseEvent.getXOnScreen(), menuDragMouseEvent.getYOnScreen(), menuDragMouseEvent.getClickCount(), menuDragMouseEvent.isPopupTrigger(), menuDragMouseEvent.getPath(), menuDragMouseEvent.getMenuSelectionManager());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  359 */       mouseEvent = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers() | paramMouseEvent.getModifiersEx(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), paramMouseEvent.getButton());
/*  360 */       AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/*  361 */       mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/*  362 */           .isCausedByTouchEvent(paramMouseEvent));
/*      */     } 
/*  364 */     return mouseEvent;
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
/*      */   public static void convertPointToScreen(Point paramPoint, Component paramComponent) {
/*      */     do {
/*      */       int i, j;
/*  380 */       if (paramComponent instanceof JComponent) {
/*  381 */         i = paramComponent.getX();
/*  382 */         j = paramComponent.getY();
/*  383 */       } else if (paramComponent instanceof java.applet.Applet || paramComponent instanceof Window) {
/*      */         
/*      */         try {
/*  386 */           Point point = paramComponent.getLocationOnScreen();
/*  387 */           i = point.x;
/*  388 */           j = point.y;
/*  389 */         } catch (IllegalComponentStateException illegalComponentStateException) {
/*  390 */           i = paramComponent.getX();
/*  391 */           j = paramComponent.getY();
/*      */         } 
/*      */       } else {
/*  394 */         i = paramComponent.getX();
/*  395 */         j = paramComponent.getY();
/*      */       } 
/*      */       
/*  398 */       paramPoint.x += i;
/*  399 */       paramPoint.y += j;
/*      */       
/*  401 */       if (paramComponent instanceof Window || paramComponent instanceof java.applet.Applet)
/*      */         break; 
/*  403 */       paramComponent = paramComponent.getParent();
/*  404 */     } while (paramComponent != null);
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
/*      */   public static void convertPointFromScreen(Point paramPoint, Component paramComponent) {
/*      */     do {
/*      */       int i, j;
/*  419 */       if (paramComponent instanceof JComponent) {
/*  420 */         i = paramComponent.getX();
/*  421 */         j = paramComponent.getY();
/*  422 */       } else if (paramComponent instanceof java.applet.Applet || paramComponent instanceof Window) {
/*      */         
/*      */         try {
/*  425 */           Point point = paramComponent.getLocationOnScreen();
/*  426 */           i = point.x;
/*  427 */           j = point.y;
/*  428 */         } catch (IllegalComponentStateException illegalComponentStateException) {
/*  429 */           i = paramComponent.getX();
/*  430 */           j = paramComponent.getY();
/*      */         } 
/*      */       } else {
/*  433 */         i = paramComponent.getX();
/*  434 */         j = paramComponent.getY();
/*      */       } 
/*      */       
/*  437 */       paramPoint.x -= i;
/*  438 */       paramPoint.y -= j;
/*      */       
/*  440 */       if (paramComponent instanceof Window || paramComponent instanceof java.applet.Applet)
/*      */         break; 
/*  442 */       paramComponent = paramComponent.getParent();
/*  443 */     } while (paramComponent != null);
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
/*      */   public static Window windowForComponent(Component paramComponent) {
/*  460 */     return getWindowAncestor(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDescendingFrom(Component paramComponent1, Component paramComponent2) {
/*  467 */     if (paramComponent1 == paramComponent2)
/*  468 */       return true; 
/*  469 */     for (Container container = paramComponent1.getParent(); container != null; container = container.getParent()) {
/*  470 */       if (container == paramComponent2)
/*  471 */         return true; 
/*  472 */     }  return false;
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
/*      */   public static Rectangle computeIntersection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle) {
/*  492 */     int i = (paramInt1 > paramRectangle.x) ? paramInt1 : paramRectangle.x;
/*  493 */     int j = (paramInt1 + paramInt3 < paramRectangle.x + paramRectangle.width) ? (paramInt1 + paramInt3) : (paramRectangle.x + paramRectangle.width);
/*  494 */     int k = (paramInt2 > paramRectangle.y) ? paramInt2 : paramRectangle.y;
/*  495 */     int m = (paramInt2 + paramInt4 < paramRectangle.y + paramRectangle.height) ? (paramInt2 + paramInt4) : (paramRectangle.y + paramRectangle.height);
/*      */     
/*  497 */     paramRectangle.x = i;
/*  498 */     paramRectangle.y = k;
/*  499 */     paramRectangle.width = j - i;
/*  500 */     paramRectangle.height = m - k;
/*      */ 
/*      */     
/*  503 */     if (paramRectangle.width < 0 || paramRectangle.height < 0) {
/*  504 */       paramRectangle.x = paramRectangle.y = paramRectangle.width = paramRectangle.height = 0;
/*      */     }
/*      */     
/*  507 */     return paramRectangle;
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
/*      */   public static Rectangle computeUnion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle) {
/*  523 */     int i = (paramInt1 < paramRectangle.x) ? paramInt1 : paramRectangle.x;
/*  524 */     int j = (paramInt1 + paramInt3 > paramRectangle.x + paramRectangle.width) ? (paramInt1 + paramInt3) : (paramRectangle.x + paramRectangle.width);
/*  525 */     int k = (paramInt2 < paramRectangle.y) ? paramInt2 : paramRectangle.y;
/*  526 */     int m = (paramInt2 + paramInt4 > paramRectangle.y + paramRectangle.height) ? (paramInt2 + paramInt4) : (paramRectangle.y + paramRectangle.height);
/*      */     
/*  528 */     paramRectangle.x = i;
/*  529 */     paramRectangle.y = k;
/*  530 */     paramRectangle.width = j - i;
/*  531 */     paramRectangle.height = m - k;
/*  532 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Rectangle[] computeDifference(Rectangle paramRectangle1, Rectangle paramRectangle2) {
/*  541 */     if (paramRectangle2 == null || !paramRectangle1.intersects(paramRectangle2) || isRectangleContainingRectangle(paramRectangle2, paramRectangle1)) {
/*  542 */       return new Rectangle[0];
/*      */     }
/*      */     
/*  545 */     Rectangle rectangle1 = new Rectangle();
/*  546 */     Rectangle rectangle2 = null, rectangle3 = null, rectangle4 = null, rectangle5 = null;
/*      */     
/*  548 */     byte b = 0;
/*      */ 
/*      */     
/*  551 */     if (isRectangleContainingRectangle(paramRectangle1, paramRectangle2)) {
/*  552 */       rectangle1.x = paramRectangle1.x; rectangle1.y = paramRectangle1.y; rectangle1.width = paramRectangle2.x - paramRectangle1.x; rectangle1.height = paramRectangle1.height;
/*  553 */       if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  554 */         rectangle2 = new Rectangle(rectangle1);
/*  555 */         b++;
/*      */       } 
/*      */       
/*  558 */       rectangle1.x = paramRectangle2.x; rectangle1.y = paramRectangle1.y; rectangle1.width = paramRectangle2.width; rectangle1.height = paramRectangle2.y - paramRectangle1.y;
/*  559 */       if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  560 */         rectangle3 = new Rectangle(rectangle1);
/*  561 */         b++;
/*      */       } 
/*      */       
/*  564 */       rectangle1.x = paramRectangle2.x; paramRectangle2.y += paramRectangle2.height; rectangle1.width = paramRectangle2.width;
/*  565 */       rectangle1.height = paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height;
/*  566 */       if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  567 */         rectangle4 = new Rectangle(rectangle1);
/*  568 */         b++;
/*      */       } 
/*      */       
/*  571 */       paramRectangle2.x += paramRectangle2.width; rectangle1.y = paramRectangle1.y; rectangle1.width = paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width;
/*  572 */       rectangle1.height = paramRectangle1.height;
/*  573 */       if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  574 */         rectangle5 = new Rectangle(rectangle1);
/*  575 */         b++;
/*      */       }
/*      */     
/*      */     }
/*  579 */     else if (paramRectangle2.x <= paramRectangle1.x && paramRectangle2.y <= paramRectangle1.y) {
/*  580 */       if (paramRectangle2.x + paramRectangle2.width > paramRectangle1.x + paramRectangle1.width) {
/*      */         
/*  582 */         rectangle1.x = paramRectangle1.x; paramRectangle2.y += paramRectangle2.height;
/*  583 */         rectangle1.width = paramRectangle1.width; rectangle1.height = paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height;
/*  584 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  585 */           rectangle2 = rectangle1;
/*  586 */           b++;
/*      */         } 
/*  588 */       } else if (paramRectangle2.y + paramRectangle2.height > paramRectangle1.y + paramRectangle1.height) {
/*  589 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle1.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle1.height);
/*      */         
/*  591 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  592 */           rectangle2 = rectangle1;
/*  593 */           b++;
/*      */         } 
/*      */       } else {
/*  596 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle1.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle2.y + paramRectangle2.height - paramRectangle1.y);
/*      */ 
/*      */         
/*  599 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  600 */           rectangle2 = new Rectangle(rectangle1);
/*  601 */           b++;
/*      */         } 
/*      */         
/*  604 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y + paramRectangle2.height, paramRectangle1.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height);
/*      */         
/*  606 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  607 */           rectangle3 = new Rectangle(rectangle1);
/*  608 */           b++;
/*      */         } 
/*      */       } 
/*  611 */     } else if (paramRectangle2.x <= paramRectangle1.x && paramRectangle2.y + paramRectangle2.height >= paramRectangle1.y + paramRectangle1.height) {
/*  612 */       if (paramRectangle2.x + paramRectangle2.width > paramRectangle1.x + paramRectangle1.width) {
/*  613 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle2.y - paramRectangle1.y);
/*  614 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  615 */           rectangle2 = rectangle1;
/*  616 */           b++;
/*      */         } 
/*      */       } else {
/*  619 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle2.y - paramRectangle1.y);
/*  620 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  621 */           rectangle2 = new Rectangle(rectangle1);
/*  622 */           b++;
/*      */         } 
/*  624 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle2.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y);
/*      */ 
/*      */         
/*  627 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  628 */           rectangle3 = new Rectangle(rectangle1);
/*  629 */           b++;
/*      */         } 
/*      */       } 
/*  632 */     } else if (paramRectangle2.x <= paramRectangle1.x) {
/*  633 */       if (paramRectangle2.x + paramRectangle2.width >= paramRectangle1.x + paramRectangle1.width) {
/*  634 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle2.y - paramRectangle1.y);
/*  635 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  636 */           rectangle2 = new Rectangle(rectangle1);
/*  637 */           b++;
/*      */         } 
/*      */         
/*  640 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y + paramRectangle2.height, paramRectangle1.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height);
/*      */         
/*  642 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  643 */           rectangle3 = new Rectangle(rectangle1);
/*  644 */           b++;
/*      */         } 
/*      */       } else {
/*  647 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle2.y - paramRectangle1.y);
/*  648 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  649 */           rectangle2 = new Rectangle(rectangle1);
/*  650 */           b++;
/*      */         } 
/*      */         
/*  653 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle2.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle2.height);
/*      */ 
/*      */         
/*  656 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  657 */           rectangle3 = new Rectangle(rectangle1);
/*  658 */           b++;
/*      */         } 
/*      */         
/*  661 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y + paramRectangle2.height, paramRectangle1.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height);
/*      */         
/*  663 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  664 */           rectangle4 = new Rectangle(rectangle1);
/*  665 */           b++;
/*      */         } 
/*      */       } 
/*  668 */     } else if (paramRectangle2.x <= paramRectangle1.x + paramRectangle1.width && paramRectangle2.x + paramRectangle2.width > paramRectangle1.x + paramRectangle1.width) {
/*  669 */       if (paramRectangle2.y <= paramRectangle1.y && paramRectangle2.y + paramRectangle2.height > paramRectangle1.y + paramRectangle1.height) {
/*  670 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle2.x - paramRectangle1.x, paramRectangle1.height);
/*  671 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  672 */           rectangle2 = rectangle1;
/*  673 */           b++;
/*      */         } 
/*  675 */       } else if (paramRectangle2.y <= paramRectangle1.y) {
/*  676 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle2.x - paramRectangle1.x, paramRectangle2.y + paramRectangle2.height - paramRectangle1.y);
/*      */         
/*  678 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  679 */           rectangle2 = new Rectangle(rectangle1);
/*  680 */           b++;
/*      */         } 
/*      */         
/*  683 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y + paramRectangle2.height, paramRectangle1.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height);
/*      */         
/*  685 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  686 */           rectangle3 = new Rectangle(rectangle1);
/*  687 */           b++;
/*      */         } 
/*  689 */       } else if (paramRectangle2.y + paramRectangle2.height > paramRectangle1.y + paramRectangle1.height) {
/*  690 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle2.y - paramRectangle1.y);
/*  691 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  692 */           rectangle2 = new Rectangle(rectangle1);
/*  693 */           b++;
/*      */         } 
/*      */         
/*  696 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y, paramRectangle2.x - paramRectangle1.x, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y);
/*      */         
/*  698 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  699 */           rectangle3 = new Rectangle(rectangle1);
/*  700 */           b++;
/*      */         } 
/*      */       } else {
/*  703 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle1.width, paramRectangle2.y - paramRectangle1.y);
/*  704 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  705 */           rectangle2 = new Rectangle(rectangle1);
/*  706 */           b++;
/*      */         } 
/*      */         
/*  709 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y, paramRectangle2.x - paramRectangle1.x, paramRectangle2.height);
/*      */         
/*  711 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  712 */           rectangle3 = new Rectangle(rectangle1);
/*  713 */           b++;
/*      */         } 
/*      */         
/*  716 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle2.y + paramRectangle2.height, paramRectangle1.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height);
/*      */         
/*  718 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  719 */           rectangle4 = new Rectangle(rectangle1);
/*  720 */           b++;
/*      */         } 
/*      */       } 
/*  723 */     } else if (paramRectangle2.x >= paramRectangle1.x && paramRectangle2.x + paramRectangle2.width <= paramRectangle1.x + paramRectangle1.width) {
/*  724 */       if (paramRectangle2.y <= paramRectangle1.y && paramRectangle2.y + paramRectangle2.height > paramRectangle1.y + paramRectangle1.height) {
/*  725 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle2.x - paramRectangle1.x, paramRectangle1.height);
/*  726 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  727 */           rectangle2 = new Rectangle(rectangle1);
/*  728 */           b++;
/*      */         } 
/*  730 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle1.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle1.height);
/*      */         
/*  732 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  733 */           rectangle3 = new Rectangle(rectangle1);
/*  734 */           b++;
/*      */         } 
/*  736 */       } else if (paramRectangle2.y <= paramRectangle1.y) {
/*  737 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle2.x - paramRectangle1.x, paramRectangle1.height);
/*  738 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  739 */           rectangle2 = new Rectangle(rectangle1);
/*  740 */           b++;
/*      */         } 
/*      */         
/*  743 */         rectangle1.setBounds(paramRectangle2.x, paramRectangle2.y + paramRectangle2.height, paramRectangle2.width, paramRectangle1.y + paramRectangle1.height - paramRectangle2.y + paramRectangle2.height);
/*      */ 
/*      */         
/*  746 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  747 */           rectangle3 = new Rectangle(rectangle1);
/*  748 */           b++;
/*      */         } 
/*      */         
/*  751 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle1.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle1.height);
/*      */         
/*  753 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  754 */           rectangle4 = new Rectangle(rectangle1);
/*  755 */           b++;
/*      */         } 
/*      */       } else {
/*  758 */         rectangle1.setBounds(paramRectangle1.x, paramRectangle1.y, paramRectangle2.x - paramRectangle1.x, paramRectangle1.height);
/*  759 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  760 */           rectangle2 = new Rectangle(rectangle1);
/*  761 */           b++;
/*      */         } 
/*      */         
/*  764 */         rectangle1.setBounds(paramRectangle2.x, paramRectangle1.y, paramRectangle2.width, paramRectangle2.y - paramRectangle1.y);
/*      */         
/*  766 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  767 */           rectangle3 = new Rectangle(rectangle1);
/*  768 */           b++;
/*      */         } 
/*      */         
/*  771 */         rectangle1.setBounds(paramRectangle2.x + paramRectangle2.width, paramRectangle1.y, paramRectangle1.x + paramRectangle1.width - paramRectangle2.x + paramRectangle2.width, paramRectangle1.height);
/*      */         
/*  773 */         if (rectangle1.width > 0 && rectangle1.height > 0) {
/*  774 */           rectangle4 = new Rectangle(rectangle1);
/*  775 */           b++;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  781 */     Rectangle[] arrayOfRectangle = new Rectangle[b];
/*  782 */     b = 0;
/*  783 */     if (rectangle2 != null)
/*  784 */       arrayOfRectangle[b++] = rectangle2; 
/*  785 */     if (rectangle3 != null)
/*  786 */       arrayOfRectangle[b++] = rectangle3; 
/*  787 */     if (rectangle4 != null)
/*  788 */       arrayOfRectangle[b++] = rectangle4; 
/*  789 */     if (rectangle5 != null)
/*  790 */       arrayOfRectangle[b++] = rectangle5; 
/*  791 */     return arrayOfRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLeftMouseButton(MouseEvent paramMouseEvent) {
/*  801 */     return ((paramMouseEvent.getModifiersEx() & 0x400) != 0 || paramMouseEvent
/*  802 */       .getButton() == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMiddleMouseButton(MouseEvent paramMouseEvent) {
/*  812 */     return ((paramMouseEvent.getModifiersEx() & 0x800) != 0 || paramMouseEvent
/*  813 */       .getButton() == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRightMouseButton(MouseEvent paramMouseEvent) {
/*  823 */     return ((paramMouseEvent.getModifiersEx() & 0x1000) != 0 || paramMouseEvent
/*  824 */       .getButton() == 3);
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
/*      */   public static int computeStringWidth(FontMetrics paramFontMetrics, String paramString) {
/*  839 */     return SwingUtilities2.stringWidth(null, paramFontMetrics, paramString);
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
/*      */   public static String layoutCompoundLabel(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, int paramInt5) {
/*  863 */     boolean bool = true;
/*  864 */     int i = paramInt2;
/*  865 */     int j = paramInt4;
/*      */     
/*  867 */     if (paramJComponent != null && 
/*  868 */       !paramJComponent.getComponentOrientation().isLeftToRight()) {
/*  869 */       bool = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     switch (paramInt2) {
/*      */       case 10:
/*  877 */         i = bool ? 2 : 4;
/*      */         break;
/*      */       case 11:
/*  880 */         i = bool ? 4 : 2;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  886 */     switch (paramInt4) {
/*      */       case 10:
/*  888 */         j = bool ? 2 : 4;
/*      */         break;
/*      */       case 11:
/*  891 */         j = bool ? 4 : 2;
/*      */         break;
/*      */     } 
/*      */     
/*  895 */     return layoutCompoundLabelImpl(paramJComponent, paramFontMetrics, paramString, paramIcon, paramInt1, i, paramInt3, j, paramRectangle1, paramRectangle2, paramRectangle3, paramInt5);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String layoutCompoundLabel(FontMetrics paramFontMetrics, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, int paramInt5) {
/*  932 */     return layoutCompoundLabelImpl(null, paramFontMetrics, paramString, paramIcon, paramInt1, paramInt2, paramInt3, paramInt4, paramRectangle1, paramRectangle2, paramRectangle3, paramInt5);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String layoutCompoundLabelImpl(JComponent paramJComponent, FontMetrics paramFontMetrics, String paramString, Icon paramIcon, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, int paramInt5) {
/*      */     byte b2;
/*      */     int i1, i2;
/*  967 */     if (paramIcon != null) {
/*  968 */       paramRectangle2.width = paramIcon.getIconWidth();
/*  969 */       paramRectangle2.height = paramIcon.getIconHeight();
/*      */     } else {
/*      */       
/*  972 */       paramRectangle2.width = paramRectangle2.height = 0;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  980 */     boolean bool = (paramString == null || paramString.equals("")) ? true : false;
/*  981 */     int i = 0;
/*  982 */     byte b1 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  989 */     if (bool) {
/*  990 */       paramRectangle3.width = paramRectangle3.height = 0;
/*  991 */       paramString = "";
/*  992 */       b2 = 0;
/*      */     } else {
/*      */       int i3;
/*      */       
/*  996 */       b2 = (paramIcon == null) ? 0 : paramInt5;
/*      */       
/*  998 */       if (paramInt4 == 0) {
/*  999 */         i3 = paramRectangle1.width;
/*      */       } else {
/*      */         
/* 1002 */         i3 = paramRectangle1.width - paramRectangle2.width + b2;
/*      */       } 
/* 1004 */       View view = (paramJComponent != null) ? (View)paramJComponent.getClientProperty("html") : null;
/* 1005 */       if (view != null) {
/* 1006 */         paramRectangle3.width = Math.min(i3, 
/* 1007 */             (int)view.getPreferredSpan(0));
/* 1008 */         paramRectangle3.height = (int)view.getPreferredSpan(1);
/*      */       } else {
/* 1010 */         paramRectangle3.width = SwingUtilities2.stringWidth(paramJComponent, paramFontMetrics, paramString);
/* 1011 */         i = SwingUtilities2.getLeftSideBearing(paramJComponent, paramFontMetrics, paramString);
/* 1012 */         if (i < 0)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1023 */           paramRectangle3.width -= i;
/*      */         }
/* 1025 */         if (paramRectangle3.width > i3) {
/* 1026 */           paramString = SwingUtilities2.clipString(paramJComponent, paramFontMetrics, paramString, i3);
/*      */           
/* 1028 */           paramRectangle3.width = SwingUtilities2.stringWidth(paramJComponent, paramFontMetrics, paramString);
/*      */         } 
/* 1030 */         paramRectangle3.height = paramFontMetrics.getHeight();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1039 */     if (paramInt3 == 1) {
/* 1040 */       if (paramInt4 != 0) {
/* 1041 */         paramRectangle3.y = 0;
/*      */       } else {
/*      */         
/* 1044 */         paramRectangle3.y = -(paramRectangle3.height + b2);
/*      */       }
/*      */     
/* 1047 */     } else if (paramInt3 == 0) {
/* 1048 */       paramRectangle3.y = paramRectangle2.height / 2 - paramRectangle3.height / 2;
/*      */     
/*      */     }
/* 1051 */     else if (paramInt4 != 0) {
/* 1052 */       paramRectangle3.y = paramRectangle2.height - paramRectangle3.height;
/*      */     } else {
/*      */       
/* 1055 */       paramRectangle3.y = paramRectangle2.height + b2;
/*      */     } 
/*      */ 
/*      */     
/* 1059 */     if (paramInt4 == 2) {
/* 1060 */       paramRectangle3.x = -(paramRectangle3.width + b2);
/*      */     }
/* 1062 */     else if (paramInt4 == 0) {
/* 1063 */       paramRectangle3.x = paramRectangle2.width / 2 - paramRectangle3.width / 2;
/*      */     } else {
/*      */       
/* 1066 */       paramRectangle3.x = paramRectangle2.width + b2;
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
/*      */ 
/*      */ 
/*      */     
/* 1080 */     int j = Math.min(paramRectangle2.x, paramRectangle3.x);
/* 1081 */     int k = Math.max(paramRectangle2.x + paramRectangle2.width, paramRectangle3.x + paramRectangle3.width) - j;
/*      */     
/* 1083 */     int m = Math.min(paramRectangle2.y, paramRectangle3.y);
/* 1084 */     int n = Math.max(paramRectangle2.y + paramRectangle2.height, paramRectangle3.y + paramRectangle3.height) - m;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1089 */     if (paramInt1 == 1) {
/* 1090 */       i2 = paramRectangle1.y - m;
/*      */     }
/* 1092 */     else if (paramInt1 == 0) {
/* 1093 */       i2 = paramRectangle1.y + paramRectangle1.height / 2 - m + n / 2;
/*      */     } else {
/*      */       
/* 1096 */       i2 = paramRectangle1.y + paramRectangle1.height - m + n;
/*      */     } 
/*      */     
/* 1099 */     if (paramInt2 == 2) {
/* 1100 */       i1 = paramRectangle1.x - j;
/*      */     }
/* 1102 */     else if (paramInt2 == 4) {
/* 1103 */       i1 = paramRectangle1.x + paramRectangle1.width - j + k;
/*      */     } else {
/*      */       
/* 1106 */       i1 = paramRectangle1.x + paramRectangle1.width / 2 - j + k / 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1113 */     paramRectangle3.x += i1;
/* 1114 */     paramRectangle3.y += i2;
/*      */     
/* 1116 */     paramRectangle2.x += i1;
/* 1117 */     paramRectangle2.y += i2;
/*      */     
/* 1119 */     if (i < 0) {
/*      */ 
/*      */       
/* 1122 */       paramRectangle3.x -= i;
/*      */       
/* 1124 */       paramRectangle3.width += i;
/*      */     } 
/* 1126 */     if (b1) {
/* 1127 */       paramRectangle3.width -= b1;
/*      */     }
/*      */     
/* 1130 */     return paramString;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void paintComponent(Graphics paramGraphics, Component paramComponent, Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1182 */     getCellRendererPane(paramComponent, paramContainer).paintComponent(paramGraphics, paramComponent, paramContainer, paramInt1, paramInt2, paramInt3, paramInt4, false);
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
/*      */   public static void paintComponent(Graphics paramGraphics, Component paramComponent, Container paramContainer, Rectangle paramRectangle) {
/* 1200 */     paintComponent(paramGraphics, paramComponent, paramContainer, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CellRendererPane getCellRendererPane(Component paramComponent, Container paramContainer) {
/* 1210 */     Container container = paramComponent.getParent();
/* 1211 */     if (container instanceof CellRendererPane) {
/* 1212 */       if (container.getParent() != paramContainer) {
/* 1213 */         paramContainer.add(container);
/*      */       }
/*      */     } else {
/* 1216 */       container = new CellRendererPane();
/* 1217 */       container.add(paramComponent);
/* 1218 */       paramContainer.add(container);
/*      */     } 
/* 1220 */     return (CellRendererPane)container;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateComponentTreeUI(Component paramComponent) {
/* 1229 */     updateComponentTreeUI0(paramComponent);
/* 1230 */     paramComponent.invalidate();
/* 1231 */     paramComponent.validate();
/* 1232 */     paramComponent.repaint();
/*      */   }
/*      */   
/*      */   private static void updateComponentTreeUI0(Component paramComponent) {
/* 1236 */     if (paramComponent instanceof JComponent) {
/* 1237 */       JComponent jComponent = (JComponent)paramComponent;
/* 1238 */       jComponent.updateUI();
/* 1239 */       JPopupMenu jPopupMenu = jComponent.getComponentPopupMenu();
/* 1240 */       if (jPopupMenu != null) {
/* 1241 */         updateComponentTreeUI(jPopupMenu);
/*      */       }
/*      */     } 
/* 1244 */     Component[] arrayOfComponent = null;
/* 1245 */     if (paramComponent instanceof JMenu) {
/* 1246 */       arrayOfComponent = ((JMenu)paramComponent).getMenuComponents();
/*      */     }
/* 1248 */     else if (paramComponent instanceof Container) {
/* 1249 */       arrayOfComponent = ((Container)paramComponent).getComponents();
/*      */     } 
/* 1251 */     if (arrayOfComponent != null) {
/* 1252 */       for (Component component : arrayOfComponent) {
/* 1253 */         updateComponentTreeUI0(component);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void invokeLater(Runnable paramRunnable) {
/* 1295 */     EventQueue.invokeLater(paramRunnable);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void invokeAndWait(Runnable paramRunnable) throws InterruptedException, InvocationTargetException {
/* 1353 */     EventQueue.invokeAndWait(paramRunnable);
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
/*      */   public static boolean isEventDispatchThread() {
/* 1366 */     return EventQueue.isDispatchThread();
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
/*      */   public static int getAccessibleIndexInParent(Component paramComponent) {
/* 1386 */     return paramComponent.getAccessibleContext().getAccessibleIndexInParent();
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
/*      */   public static Accessible getAccessibleAt(Component paramComponent, Point paramPoint) {
/* 1398 */     if (paramComponent instanceof Container)
/* 1399 */       return paramComponent.getAccessibleContext().getAccessibleComponent().getAccessibleAt(paramPoint); 
/* 1400 */     if (paramComponent instanceof Accessible) {
/* 1401 */       Accessible accessible = (Accessible)paramComponent;
/* 1402 */       if (accessible != null) {
/* 1403 */         AccessibleContext accessibleContext = accessible.getAccessibleContext();
/* 1404 */         if (accessibleContext != null) {
/*      */ 
/*      */           
/* 1407 */           int i = accessibleContext.getAccessibleChildrenCount();
/* 1408 */           for (byte b = 0; b < i; b++) {
/* 1409 */             accessible = accessibleContext.getAccessibleChild(b);
/* 1410 */             if (accessible != null) {
/* 1411 */               accessibleContext = accessible.getAccessibleContext();
/* 1412 */               if (accessibleContext != null) {
/* 1413 */                 AccessibleComponent accessibleComponent = accessibleContext.getAccessibleComponent();
/* 1414 */                 if (accessibleComponent != null && accessibleComponent.isShowing()) {
/* 1415 */                   Point point1 = accessibleComponent.getLocation();
/* 1416 */                   Point point2 = new Point(paramPoint.x - point1.x, paramPoint.y - point1.y);
/*      */                   
/* 1418 */                   if (accessibleComponent.contains(point2)) {
/* 1419 */                     return accessible;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1427 */       return (Accessible)paramComponent;
/*      */     } 
/* 1429 */     return null;
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
/*      */   public static AccessibleStateSet getAccessibleStateSet(Component paramComponent) {
/* 1444 */     return paramComponent.getAccessibleContext().getAccessibleStateSet();
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
/*      */   public static int getAccessibleChildrenCount(Component paramComponent) {
/* 1459 */     return paramComponent.getAccessibleContext().getAccessibleChildrenCount();
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
/*      */   public static Accessible getAccessibleChild(Component paramComponent, int paramInt) {
/* 1473 */     return paramComponent.getAccessibleContext().getAccessibleChild(paramInt);
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
/*      */   @Deprecated
/*      */   public static Component findFocusOwner(Component paramComponent) {
/* 1493 */     Component component1 = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*      */ 
/*      */     
/* 1496 */     for (Component component2 = component1; component2 != null; 
/* 1497 */       component2 = (component2 instanceof Window) ? null : component2.getParent()) {
/*      */       
/* 1499 */       if (component2 == paramComponent) {
/* 1500 */         return component1;
/*      */       }
/*      */     } 
/*      */     
/* 1504 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JRootPane getRootPane(Component paramComponent) {
/* 1513 */     if (paramComponent instanceof RootPaneContainer) {
/* 1514 */       return ((RootPaneContainer)paramComponent).getRootPane();
/*      */     }
/* 1516 */     for (; paramComponent != null; paramComponent = paramComponent.getParent()) {
/* 1517 */       if (paramComponent instanceof JRootPane) {
/* 1518 */         return (JRootPane)paramComponent;
/*      */       }
/*      */     } 
/* 1521 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Component getRoot(Component paramComponent) {
/* 1530 */     Component component1 = null;
/* 1531 */     for (Component component2 = paramComponent; component2 != null; component2 = component2.getParent()) {
/* 1532 */       if (component2 instanceof Window) {
/* 1533 */         return component2;
/*      */       }
/* 1535 */       if (component2 instanceof java.applet.Applet) {
/* 1536 */         component1 = component2;
/*      */       }
/*      */     } 
/* 1539 */     return component1;
/*      */   }
/*      */   
/*      */   static JComponent getPaintingOrigin(JComponent paramJComponent) {
/* 1543 */     JComponent jComponent = paramJComponent; Container container;
/* 1544 */     while (container = jComponent.getParent() instanceof JComponent) {
/* 1545 */       JComponent jComponent1 = (JComponent)container;
/* 1546 */       if (jComponent1.isPaintingOrigin()) {
/* 1547 */         return jComponent1;
/*      */       }
/*      */     } 
/* 1550 */     return null;
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
/*      */   public static boolean processKeyBindings(KeyEvent paramKeyEvent) {
/* 1570 */     if (paramKeyEvent != null) {
/* 1571 */       if (paramKeyEvent.isConsumed()) {
/* 1572 */         return false;
/*      */       }
/*      */       
/* 1575 */       Component component = paramKeyEvent.getComponent();
/* 1576 */       boolean bool = (paramKeyEvent.getID() == 401) ? true : false;
/*      */       
/* 1578 */       if (!isValidKeyEventForKeyBindings(paramKeyEvent)) {
/* 1579 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1583 */       while (component != null) {
/* 1584 */         if (component instanceof JComponent) {
/* 1585 */           return ((JComponent)component).processKeyBindings(paramKeyEvent, bool);
/*      */         }
/*      */         
/* 1588 */         if (component instanceof java.applet.Applet || component instanceof Window)
/*      */         {
/*      */ 
/*      */           
/* 1592 */           return JComponent.processKeyBindingsForAllComponents(paramKeyEvent, (Container)component, bool);
/*      */         }
/*      */         
/* 1595 */         component = component.getParent();
/*      */       } 
/*      */     } 
/* 1598 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isValidKeyEventForKeyBindings(KeyEvent paramKeyEvent) {
/* 1606 */     return true;
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
/*      */   public static boolean notifyAction(Action paramAction, KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, Object paramObject, int paramInt) {
/*      */     boolean bool;
/*      */     String str;
/* 1629 */     if (paramAction == null) {
/* 1630 */       return false;
/*      */     }
/* 1632 */     if (paramAction instanceof UIAction) {
/* 1633 */       if (!((UIAction)paramAction).isEnabled(paramObject)) {
/* 1634 */         return false;
/*      */       }
/*      */     }
/* 1637 */     else if (!paramAction.isEnabled()) {
/* 1638 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1644 */     Object object = paramAction.getValue("ActionCommandKey");
/* 1645 */     if (object == null && paramAction instanceof JComponent.ActionStandin) {
/*      */ 
/*      */       
/* 1648 */       bool = true;
/*      */     } else {
/*      */       
/* 1651 */       bool = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1657 */     if (object != null) {
/* 1658 */       str = object.toString();
/*      */     }
/* 1660 */     else if (!bool && paramKeyEvent.getKeyChar() != Character.MAX_VALUE) {
/* 1661 */       str = String.valueOf(paramKeyEvent.getKeyChar());
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1666 */       str = null;
/*      */     } 
/* 1668 */     paramAction.actionPerformed(new ActionEvent(paramObject, 1001, str, paramKeyEvent
/* 1669 */           .getWhen(), paramInt));
/*      */     
/* 1671 */     return true;
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
/*      */   public static void replaceUIInputMap(JComponent paramJComponent, int paramInt, InputMap paramInputMap) {
/* 1684 */     InputMap inputMap = paramJComponent.getInputMap(paramInt, (paramInputMap != null));
/*      */     
/* 1686 */     while (inputMap != null) {
/* 1687 */       InputMap inputMap1 = inputMap.getParent();
/* 1688 */       if (inputMap1 == null || inputMap1 instanceof javax.swing.plaf.UIResource) {
/* 1689 */         inputMap.setParent(paramInputMap);
/*      */         return;
/*      */       } 
/* 1692 */       inputMap = inputMap1;
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
/*      */   public static void replaceUIActionMap(JComponent paramJComponent, ActionMap paramActionMap) {
/* 1706 */     ActionMap actionMap = paramJComponent.getActionMap((paramActionMap != null));
/*      */     
/* 1708 */     while (actionMap != null) {
/* 1709 */       ActionMap actionMap1 = actionMap.getParent();
/* 1710 */       if (actionMap1 == null || actionMap1 instanceof javax.swing.plaf.UIResource) {
/* 1711 */         actionMap.setParent(paramActionMap);
/*      */         return;
/*      */       } 
/* 1714 */       actionMap = actionMap1;
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
/*      */   public static InputMap getUIInputMap(JComponent paramJComponent, int paramInt) {
/* 1728 */     InputMap inputMap = paramJComponent.getInputMap(paramInt, false);
/* 1729 */     while (inputMap != null) {
/* 1730 */       InputMap inputMap1 = inputMap.getParent();
/* 1731 */       if (inputMap1 instanceof javax.swing.plaf.UIResource) {
/* 1732 */         return inputMap1;
/*      */       }
/* 1734 */       inputMap = inputMap1;
/*      */     } 
/* 1736 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ActionMap getUIActionMap(JComponent paramJComponent) {
/* 1747 */     ActionMap actionMap = paramJComponent.getActionMap(false);
/* 1748 */     while (actionMap != null) {
/* 1749 */       ActionMap actionMap1 = actionMap.getParent();
/* 1750 */       if (actionMap1 instanceof javax.swing.plaf.UIResource) {
/* 1751 */         return actionMap1;
/*      */       }
/* 1753 */       actionMap = actionMap1;
/*      */     } 
/* 1755 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1760 */   private static final Object sharedOwnerFrameKey = new StringBuffer("SwingUtilities.sharedOwnerFrame");
/*      */   
/*      */   static class SharedOwnerFrame
/*      */     extends Frame implements WindowListener {
/*      */     public void addNotify() {
/* 1765 */       super.addNotify();
/* 1766 */       installListeners();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void installListeners() {
/* 1773 */       Window[] arrayOfWindow = getOwnedWindows();
/* 1774 */       for (Window window : arrayOfWindow) {
/* 1775 */         if (window != null) {
/* 1776 */           window.removeWindowListener(this);
/* 1777 */           window.addWindowListener(this);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void windowClosed(WindowEvent param1WindowEvent) {
/* 1787 */       synchronized (getTreeLock()) {
/* 1788 */         Window[] arrayOfWindow = getOwnedWindows();
/* 1789 */         for (Window window : arrayOfWindow) {
/* 1790 */           if (window != null) {
/* 1791 */             if (window.isDisplayable()) {
/*      */               return;
/*      */             }
/* 1794 */             window.removeWindowListener(this);
/*      */           } 
/*      */         } 
/* 1797 */         dispose();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void windowOpened(WindowEvent param1WindowEvent) {}
/*      */     
/*      */     public void windowClosing(WindowEvent param1WindowEvent) {}
/*      */     
/*      */     public void windowIconified(WindowEvent param1WindowEvent) {}
/*      */     
/*      */     public void windowDeiconified(WindowEvent param1WindowEvent) {}
/*      */     
/*      */     public void windowActivated(WindowEvent param1WindowEvent) {}
/*      */     
/*      */     public void windowDeactivated(WindowEvent param1WindowEvent) {}
/*      */     
/*      */     public void show() {}
/*      */     
/*      */     public void dispose() {
/*      */       try {
/* 1818 */         getToolkit().getSystemEventQueue();
/* 1819 */         super.dispose();
/* 1820 */       } catch (Exception exception) {}
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
/*      */   static Frame getSharedOwnerFrame() throws HeadlessException {
/* 1836 */     Frame frame = (Frame)appContextGet(sharedOwnerFrameKey);
/* 1837 */     if (frame == null) {
/* 1838 */       frame = new SharedOwnerFrame();
/* 1839 */       appContextPut(sharedOwnerFrameKey, frame);
/*      */     } 
/*      */     
/* 1842 */     return frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static WindowListener getSharedOwnerFrameShutdownListener() throws HeadlessException {
/* 1853 */     Frame frame = getSharedOwnerFrame();
/* 1854 */     return (WindowListener)frame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Object appContextGet(Object paramObject) {
/* 1865 */     return AppContext.getAppContext().get(paramObject);
/*      */   }
/*      */   
/*      */   static void appContextPut(Object paramObject1, Object paramObject2) {
/* 1869 */     AppContext.getAppContext().put(paramObject1, paramObject2);
/*      */   }
/*      */   
/*      */   static void appContextRemove(Object paramObject) {
/* 1873 */     AppContext.getAppContext().remove(paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   static Class<?> loadSystemClass(String paramString) throws ClassNotFoundException {
/* 1878 */     ReflectUtil.checkPackageAccess(paramString);
/* 1879 */     return Class.forName(paramString, true, Thread.currentThread()
/* 1880 */         .getContextClassLoader());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isLeftToRight(Component paramComponent) {
/* 1889 */     return paramComponent.getComponentOrientation().isLeftToRight();
/*      */   }
/*      */   private SwingUtilities() {
/* 1892 */     throw new Error("SwingUtilities is just a container for static methods");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean doesIconReferenceImage(Icon paramIcon, Image paramImage) {
/* 1901 */     Image image = (paramIcon != null && paramIcon instanceof ImageIcon) ? ((ImageIcon)paramIcon).getImage() : null;
/* 1902 */     return (image == paramImage);
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
/*      */   static int findDisplayedMnemonicIndex(String paramString, int paramInt) {
/* 1915 */     if (paramString == null || paramInt == 0) {
/* 1916 */       return -1;
/*      */     }
/*      */     
/* 1919 */     char c1 = Character.toUpperCase((char)paramInt);
/* 1920 */     char c2 = Character.toLowerCase((char)paramInt);
/*      */     
/* 1922 */     int i = paramString.indexOf(c1);
/* 1923 */     int j = paramString.indexOf(c2);
/*      */     
/* 1925 */     if (i == -1)
/* 1926 */       return j; 
/* 1927 */     if (j == -1) {
/* 1928 */       return i;
/*      */     }
/* 1930 */     return (j < i) ? j : i;
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
/*      */   public static Rectangle calculateInnerArea(JComponent paramJComponent, Rectangle paramRectangle) {
/* 1953 */     if (paramJComponent == null) {
/* 1954 */       return null;
/*      */     }
/* 1956 */     Rectangle rectangle = paramRectangle;
/* 1957 */     Insets insets = paramJComponent.getInsets();
/*      */     
/* 1959 */     if (rectangle == null) {
/* 1960 */       rectangle = new Rectangle();
/*      */     }
/*      */     
/* 1963 */     rectangle.x = insets.left;
/* 1964 */     rectangle.y = insets.top;
/* 1965 */     rectangle.width = paramJComponent.getWidth() - insets.left - insets.right;
/* 1966 */     rectangle.height = paramJComponent.getHeight() - insets.top - insets.bottom;
/*      */     
/* 1968 */     return rectangle;
/*      */   }
/*      */   
/*      */   static void updateRendererOrEditorUI(Object paramObject) {
/* 1972 */     if (paramObject == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1976 */     Component component = null;
/*      */     
/* 1978 */     if (paramObject instanceof Component) {
/* 1979 */       component = (Component)paramObject;
/*      */     }
/* 1981 */     if (paramObject instanceof DefaultCellEditor) {
/* 1982 */       component = ((DefaultCellEditor)paramObject).getComponent();
/*      */     }
/*      */     
/* 1985 */     if (component != null) {
/* 1986 */       updateComponentTreeUI(component);
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
/*      */   public static Container getUnwrappedParent(Component paramComponent) {
/* 2007 */     Container container = paramComponent.getParent();
/* 2008 */     while (container instanceof JLayer) {
/* 2009 */       container = container.getParent();
/*      */     }
/* 2011 */     return container;
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
/*      */   public static Component getUnwrappedView(JViewport paramJViewport) {
/* 2038 */     Component component = paramJViewport.getView();
/* 2039 */     while (component instanceof JLayer) {
/* 2040 */       component = ((JLayer<Component>)component).getView();
/*      */     }
/* 2042 */     return component;
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
/*      */   static Container getValidateRoot(Container paramContainer, boolean paramBoolean) {
/* 2066 */     Container container = null;
/*      */     
/* 2068 */     for (; paramContainer != null; paramContainer = paramContainer.getParent()) {
/*      */       
/* 2070 */       if (!paramContainer.isDisplayable() || paramContainer instanceof CellRendererPane) {
/* 2071 */         return null;
/*      */       }
/* 2073 */       if (paramContainer.isValidateRoot()) {
/* 2074 */         container = paramContainer;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2079 */     if (container == null) {
/* 2080 */       return null;
/*      */     }
/*      */     
/* 2083 */     for (; paramContainer != null; paramContainer = paramContainer.getParent()) {
/* 2084 */       if (!paramContainer.isDisplayable() || (paramBoolean && !paramContainer.isVisible())) {
/* 2085 */         return null;
/*      */       }
/* 2087 */       if (paramContainer instanceof Window || paramContainer instanceof java.applet.Applet) {
/* 2088 */         return container;
/*      */       }
/*      */     } 
/*      */     
/* 2092 */     return null;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/SwingUtilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */