/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.Vector;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.EventListenerList;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AppContext;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuSelectionManager
/*     */ {
/*  43 */   private Vector<MenuElement> selection = new Vector<>();
/*     */   
/*     */   private static final boolean TRACE = false;
/*     */   
/*     */   private static final boolean VERBOSE = false;
/*     */   
/*     */   private static final boolean DEBUG = false;
/*  50 */   private static final StringBuilder MENU_SELECTION_MANAGER_KEY = new StringBuilder("javax.swing.MenuSelectionManager");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MenuSelectionManager defaultManager() {
/*  59 */     synchronized (MENU_SELECTION_MANAGER_KEY) {
/*  60 */       AppContext appContext = AppContext.getAppContext();
/*  61 */       MenuSelectionManager menuSelectionManager = (MenuSelectionManager)appContext.get(MENU_SELECTION_MANAGER_KEY);
/*     */       
/*  63 */       if (menuSelectionManager == null) {
/*  64 */         menuSelectionManager = new MenuSelectionManager();
/*  65 */         appContext.put(MENU_SELECTION_MANAGER_KEY, menuSelectionManager);
/*     */ 
/*     */         
/*  68 */         Object object = appContext.get(SwingUtilities2.MENU_SELECTION_MANAGER_LISTENER_KEY);
/*  69 */         if (object != null && object instanceof ChangeListener) {
/*  70 */           menuSelectionManager.addChangeListener((ChangeListener)object);
/*     */         }
/*     */       } 
/*     */       
/*  74 */       return menuSelectionManager;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected transient ChangeEvent changeEvent = null;
/*  84 */   protected EventListenerList listenerList = new EventListenerList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedPath(MenuElement[] paramArrayOfMenuElement) {
/*  99 */     int k = this.selection.size();
/* 100 */     byte b = 0;
/*     */     
/* 102 */     if (paramArrayOfMenuElement == null) {
/* 103 */       paramArrayOfMenuElement = new MenuElement[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int i, j;
/*     */ 
/*     */     
/* 111 */     for (i = 0, j = paramArrayOfMenuElement.length; i < j && 
/* 112 */       i < k && this.selection.elementAt(i) == paramArrayOfMenuElement[i]; i++) {
/* 113 */       b++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 118 */     for (i = k - 1; i >= b; i--) {
/* 119 */       MenuElement menuElement = this.selection.elementAt(i);
/* 120 */       this.selection.removeElementAt(i);
/* 121 */       menuElement.menuSelectionChanged(false);
/*     */     } 
/*     */     
/* 124 */     for (i = b, j = paramArrayOfMenuElement.length; i < j; i++) {
/* 125 */       if (paramArrayOfMenuElement[i] != null) {
/* 126 */         this.selection.addElement(paramArrayOfMenuElement[i]);
/* 127 */         paramArrayOfMenuElement[i].menuSelectionChanged(true);
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     fireStateChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuElement[] getSelectedPath() {
/* 140 */     MenuElement[] arrayOfMenuElement = new MenuElement[this.selection.size()]; byte b;
/*     */     int i;
/* 142 */     for (b = 0, i = this.selection.size(); b < i; b++)
/* 143 */       arrayOfMenuElement[b] = this.selection.elementAt(b); 
/* 144 */     return arrayOfMenuElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSelectedPath() {
/* 152 */     if (this.selection.size() > 0) {
/* 153 */       setSelectedPath(null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChangeListener(ChangeListener paramChangeListener) {
/* 163 */     this.listenerList.add(ChangeListener.class, paramChangeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeChangeListener(ChangeListener paramChangeListener) {
/* 172 */     this.listenerList.remove(ChangeListener.class, paramChangeListener);
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
/*     */   public ChangeListener[] getChangeListeners() {
/* 184 */     return this.listenerList.<ChangeListener>getListeners(ChangeListener.class);
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
/*     */   protected void fireStateChanged() {
/* 196 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 199 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 200 */       if (arrayOfObject[i] == ChangeListener.class) {
/*     */         
/* 202 */         if (this.changeEvent == null)
/* 203 */           this.changeEvent = new ChangeEvent(this); 
/* 204 */         ((ChangeListener)arrayOfObject[i + 1]).stateChanged(this.changeEvent);
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
/*     */   public void processMouseEvent(MouseEvent paramMouseEvent) {
/* 227 */     Point point = paramMouseEvent.getPoint();
/*     */     
/* 229 */     Component component = paramMouseEvent.getComponent();
/*     */     
/* 231 */     if (component != null && !component.isShowing()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 237 */     int n = paramMouseEvent.getID();
/* 238 */     int i1 = paramMouseEvent.getModifiers();
/*     */     
/* 240 */     if ((n == 504 || n == 505) && (i1 & 0x1C) != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     if (component != null) {
/* 248 */       SwingUtilities.convertPointToScreen(point, component);
/*     */     }
/*     */     
/* 251 */     int i = point.x;
/* 252 */     int j = point.y;
/*     */     
/* 254 */     Vector<MenuElement> vector = (Vector)this.selection.clone();
/* 255 */     int m = vector.size();
/* 256 */     boolean bool = false;
/* 257 */     for (int k = m - 1; k >= 0 && !bool; k--) {
/* 258 */       MenuElement menuElement = vector.elementAt(k);
/* 259 */       MenuElement[] arrayOfMenuElement1 = menuElement.getSubElements();
/*     */       
/* 261 */       MenuElement[] arrayOfMenuElement2 = null; byte b; int i2;
/* 262 */       for (b = 0, i2 = arrayOfMenuElement1.length; b < i2 && !bool; b++) {
/* 263 */         if (arrayOfMenuElement1[b] != null) {
/*     */           
/* 265 */           Component component1 = arrayOfMenuElement1[b].getComponent();
/* 266 */           if (component1.isShowing()) {
/*     */             int i3, i4;
/* 268 */             if (component1 instanceof JComponent) {
/* 269 */               i3 = component1.getWidth();
/* 270 */               i4 = component1.getHeight();
/*     */             } else {
/* 272 */               Rectangle rectangle = component1.getBounds();
/* 273 */               i3 = rectangle.width;
/* 274 */               i4 = rectangle.height;
/*     */             } 
/* 276 */             point.x = i;
/* 277 */             point.y = j;
/* 278 */             SwingUtilities.convertPointFromScreen(point, component1);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 283 */             if (point.x >= 0 && point.x < i3 && point.y >= 0 && point.y < i4) {
/*     */ 
/*     */               
/* 286 */               if (arrayOfMenuElement2 == null) {
/* 287 */                 arrayOfMenuElement2 = new MenuElement[k + 2];
/* 288 */                 for (byte b1 = 0; b1 <= k; b1++)
/* 289 */                   arrayOfMenuElement2[b1] = vector.elementAt(b1); 
/*     */               } 
/* 291 */               arrayOfMenuElement2[k + 1] = arrayOfMenuElement1[b];
/* 292 */               MenuElement[] arrayOfMenuElement = getSelectedPath();
/*     */ 
/*     */               
/* 295 */               if (arrayOfMenuElement[arrayOfMenuElement.length - 1] != arrayOfMenuElement2[k + 1] && (arrayOfMenuElement.length < 2 || arrayOfMenuElement[arrayOfMenuElement.length - 2] != arrayOfMenuElement2[k + 1])) {
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 300 */                 Component component2 = arrayOfMenuElement[arrayOfMenuElement.length - 1].getComponent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 308 */                 MouseEvent mouseEvent1 = new MouseEvent(component2, 505, paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*     */                 
/* 310 */                 AWTAccessor.MouseEventAccessor mouseEventAccessor1 = AWTAccessor.getMouseEventAccessor();
/* 311 */                 mouseEventAccessor1.setCausedByTouchEvent(mouseEvent1, mouseEventAccessor1
/* 312 */                     .isCausedByTouchEvent(paramMouseEvent));
/* 313 */                 arrayOfMenuElement[arrayOfMenuElement.length - 1]
/* 314 */                   .processMouseEvent(mouseEvent1, arrayOfMenuElement2, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 323 */                 MouseEvent mouseEvent2 = new MouseEvent(component1, 504, paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*     */                 
/* 325 */                 mouseEventAccessor1.setCausedByTouchEvent(mouseEvent2, mouseEventAccessor1
/* 326 */                     .isCausedByTouchEvent(paramMouseEvent));
/* 327 */                 arrayOfMenuElement1[b].processMouseEvent(mouseEvent2, arrayOfMenuElement2, this);
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 334 */               MouseEvent mouseEvent = new MouseEvent(component1, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*     */               
/* 336 */               AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 337 */               mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 338 */                   .isCausedByTouchEvent(paramMouseEvent));
/* 339 */               arrayOfMenuElement1[b].processMouseEvent(mouseEvent, arrayOfMenuElement2, this);
/* 340 */               bool = true;
/* 341 */               paramMouseEvent.consume();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private void printMenuElementArray(MenuElement[] paramArrayOfMenuElement) {
/* 348 */     printMenuElementArray(paramArrayOfMenuElement, false);
/*     */   }
/*     */   
/*     */   private void printMenuElementArray(MenuElement[] paramArrayOfMenuElement, boolean paramBoolean) {
/* 352 */     System.out.println("Path is("); byte b;
/*     */     int i;
/* 354 */     for (b = 0, i = paramArrayOfMenuElement.length; b < i; b++) {
/* 355 */       for (byte b1 = 0; b1 <= b; b1++)
/* 356 */         System.out.print("  "); 
/* 357 */       MenuElement menuElement = paramArrayOfMenuElement[b];
/* 358 */       if (menuElement instanceof JMenuItem) {
/* 359 */         System.out.println(((JMenuItem)menuElement).getText() + ", ");
/* 360 */       } else if (menuElement instanceof JMenuBar) {
/* 361 */         System.out.println("JMenuBar, ");
/* 362 */       } else if (menuElement instanceof JPopupMenu) {
/* 363 */         System.out.println("JPopupMenu, ");
/* 364 */       } else if (menuElement == null) {
/* 365 */         System.out.println("NULL , ");
/*     */       } else {
/* 367 */         System.out.println("" + menuElement + ", ");
/*     */       } 
/*     */     } 
/* 370 */     System.out.println(")");
/*     */     
/* 372 */     if (paramBoolean == true) {
/* 373 */       Thread.dumpStack();
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
/*     */   public Component componentForPoint(Component paramComponent, Point paramPoint) {
/* 390 */     Point point = paramPoint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 400 */     SwingUtilities.convertPointToScreen(point, paramComponent);
/*     */     
/* 402 */     int i = point.x;
/* 403 */     int j = point.y;
/*     */     
/* 405 */     Vector<MenuElement> vector = (Vector)this.selection.clone();
/* 406 */     int m = vector.size();
/* 407 */     for (int k = m - 1; k >= 0; k--) {
/* 408 */       MenuElement menuElement = vector.elementAt(k);
/* 409 */       MenuElement[] arrayOfMenuElement = menuElement.getSubElements(); byte b;
/*     */       int n;
/* 411 */       for (b = 0, n = arrayOfMenuElement.length; b < n; b++) {
/* 412 */         if (arrayOfMenuElement[b] != null) {
/*     */           
/* 414 */           Component component = arrayOfMenuElement[b].getComponent();
/* 415 */           if (component.isShowing()) {
/*     */             int i1, i2;
/* 417 */             if (component instanceof JComponent) {
/* 418 */               i1 = component.getWidth();
/* 419 */               i2 = component.getHeight();
/*     */             } else {
/* 421 */               Rectangle rectangle = component.getBounds();
/* 422 */               i1 = rectangle.width;
/* 423 */               i2 = rectangle.height;
/*     */             } 
/* 425 */             point.x = i;
/* 426 */             point.y = j;
/* 427 */             SwingUtilities.convertPointFromScreen(point, component);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 432 */             if (point.x >= 0 && point.x < i1 && point.y >= 0 && point.y < i2)
/* 433 */               return component; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 437 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processKeyEvent(KeyEvent paramKeyEvent) {
/* 447 */     MenuElement[] arrayOfMenuElement1 = new MenuElement[0];
/* 448 */     arrayOfMenuElement1 = this.selection.<MenuElement>toArray(arrayOfMenuElement1);
/* 449 */     int i = arrayOfMenuElement1.length;
/*     */ 
/*     */     
/* 452 */     if (i < 1) {
/*     */       return;
/*     */     }
/*     */     
/* 456 */     for (int j = i - 1; j >= 0; j--) {
/* 457 */       MenuElement menuElement = arrayOfMenuElement1[j];
/* 458 */       MenuElement[] arrayOfMenuElement4 = menuElement.getSubElements();
/* 459 */       MenuElement[] arrayOfMenuElement3 = null;
/*     */       
/* 461 */       for (byte b = 0; b < arrayOfMenuElement4.length; b++) {
/* 462 */         if (arrayOfMenuElement4[b] != null && arrayOfMenuElement4[b].getComponent().isShowing() && arrayOfMenuElement4[b]
/* 463 */           .getComponent().isEnabled()) {
/*     */ 
/*     */ 
/*     */           
/* 467 */           if (arrayOfMenuElement3 == null) {
/* 468 */             arrayOfMenuElement3 = new MenuElement[j + 2];
/* 469 */             System.arraycopy(arrayOfMenuElement1, 0, arrayOfMenuElement3, 0, j + 1);
/*     */           } 
/* 471 */           arrayOfMenuElement3[j + 1] = arrayOfMenuElement4[b];
/* 472 */           arrayOfMenuElement4[b].processKeyEvent(paramKeyEvent, arrayOfMenuElement3, this);
/* 473 */           if (paramKeyEvent.isConsumed()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 480 */     MenuElement[] arrayOfMenuElement2 = new MenuElement[1];
/* 481 */     arrayOfMenuElement2[0] = arrayOfMenuElement1[0];
/* 482 */     arrayOfMenuElement2[0].processKeyEvent(paramKeyEvent, arrayOfMenuElement2, this);
/* 483 */     if (paramKeyEvent.isConsumed()) {
/*     */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComponentPartOfCurrentMenu(Component paramComponent) {
/* 492 */     if (this.selection.size() > 0) {
/* 493 */       MenuElement menuElement = this.selection.elementAt(0);
/* 494 */       return isComponentPartOfCurrentMenu(menuElement, paramComponent);
/*     */     } 
/* 496 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isComponentPartOfCurrentMenu(MenuElement paramMenuElement, Component paramComponent) {
/* 503 */     if (paramMenuElement == null) {
/* 504 */       return false;
/*     */     }
/* 506 */     if (paramMenuElement.getComponent() == paramComponent) {
/* 507 */       return true;
/*     */     }
/* 509 */     MenuElement[] arrayOfMenuElement = paramMenuElement.getSubElements(); byte b; int i;
/* 510 */     for (b = 0, i = arrayOfMenuElement.length; b < i; b++) {
/* 511 */       if (isComponentPartOfCurrentMenu(arrayOfMenuElement[b], paramComponent)) {
/* 512 */         return true;
/*     */       }
/*     */     } 
/* 515 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/MenuSelectionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */