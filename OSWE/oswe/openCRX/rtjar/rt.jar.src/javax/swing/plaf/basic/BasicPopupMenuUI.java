/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.AWTEvent;
/*      */ import java.awt.Component;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.AWTEventListener;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.ComponentInputMap;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JApplet;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.MenuElement;
/*      */ import javax.swing.MenuSelectionManager;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.MenuKeyEvent;
/*      */ import javax.swing.event.MenuKeyListener;
/*      */ import javax.swing.event.PopupMenuEvent;
/*      */ import javax.swing.event.PopupMenuListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.PopupMenuUI;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicPopupMenuUI
/*      */   extends PopupMenuUI
/*      */ {
/*   64 */   static final StringBuilder MOUSE_GRABBER_KEY = new StringBuilder("javax.swing.plaf.basic.BasicPopupMenuUI.MouseGrabber");
/*      */   
/*   66 */   static final StringBuilder MENU_KEYBOARD_HELPER_KEY = new StringBuilder("javax.swing.plaf.basic.BasicPopupMenuUI.MenuKeyboardHelper");
/*      */ 
/*      */   
/*   69 */   protected JPopupMenu popupMenu = null;
/*   70 */   private transient PopupMenuListener popupMenuListener = null;
/*   71 */   private MenuKeyListener menuKeyListener = null;
/*      */   
/*      */   private static boolean checkedUnpostPopup;
/*      */   private static boolean unpostPopup;
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*   77 */     return new BasicPopupMenuUI();
/*      */   }
/*      */   
/*      */   public BasicPopupMenuUI() {
/*   81 */     BasicLookAndFeel.needsEventHelper = true;
/*   82 */     LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
/*   83 */     if (lookAndFeel instanceof BasicLookAndFeel) {
/*   84 */       ((BasicLookAndFeel)lookAndFeel).installAWTEventListener();
/*      */     }
/*      */   }
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*   89 */     this.popupMenu = (JPopupMenu)paramJComponent;
/*      */     
/*   91 */     installDefaults();
/*   92 */     installListeners();
/*   93 */     installKeyboardActions();
/*      */   }
/*      */   
/*      */   public void installDefaults() {
/*   97 */     if (this.popupMenu.getLayout() == null || this.popupMenu
/*   98 */       .getLayout() instanceof javax.swing.plaf.UIResource) {
/*   99 */       this.popupMenu.setLayout(new DefaultMenuLayout(this.popupMenu, 1));
/*      */     }
/*  101 */     LookAndFeel.installProperty(this.popupMenu, "opaque", Boolean.TRUE);
/*  102 */     LookAndFeel.installBorder(this.popupMenu, "PopupMenu.border");
/*  103 */     LookAndFeel.installColorsAndFont(this.popupMenu, "PopupMenu.background", "PopupMenu.foreground", "PopupMenu.font");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  110 */     if (this.popupMenuListener == null) {
/*  111 */       this.popupMenuListener = new BasicPopupMenuListener();
/*      */     }
/*  113 */     this.popupMenu.addPopupMenuListener(this.popupMenuListener);
/*      */     
/*  115 */     if (this.menuKeyListener == null) {
/*  116 */       this.menuKeyListener = new BasicMenuKeyListener();
/*      */     }
/*  118 */     this.popupMenu.addMenuKeyListener(this.menuKeyListener);
/*      */     
/*  120 */     AppContext appContext = AppContext.getAppContext();
/*  121 */     synchronized (MOUSE_GRABBER_KEY) {
/*  122 */       MouseGrabber mouseGrabber = (MouseGrabber)appContext.get(MOUSE_GRABBER_KEY);
/*      */       
/*  124 */       if (mouseGrabber == null) {
/*  125 */         mouseGrabber = new MouseGrabber();
/*  126 */         appContext.put(MOUSE_GRABBER_KEY, mouseGrabber);
/*      */       } 
/*      */     } 
/*  129 */     synchronized (MENU_KEYBOARD_HELPER_KEY) {
/*      */       
/*  131 */       MenuKeyboardHelper menuKeyboardHelper = (MenuKeyboardHelper)appContext.get(MENU_KEYBOARD_HELPER_KEY);
/*  132 */       if (menuKeyboardHelper == null) {
/*  133 */         menuKeyboardHelper = new MenuKeyboardHelper();
/*  134 */         appContext.put(MENU_KEYBOARD_HELPER_KEY, menuKeyboardHelper);
/*  135 */         MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  136 */         menuSelectionManager.addChangeListener(menuKeyboardHelper);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {}
/*      */   
/*      */   static InputMap getInputMap(JPopupMenu paramJPopupMenu, JComponent paramJComponent) {
/*  145 */     ComponentInputMap componentInputMap = null;
/*  146 */     Object[] arrayOfObject = (Object[])UIManager.get("PopupMenu.selectedWindowInputMapBindings");
/*  147 */     if (arrayOfObject != null) {
/*  148 */       componentInputMap = LookAndFeel.makeComponentInputMap(paramJComponent, arrayOfObject);
/*  149 */       if (!paramJPopupMenu.getComponentOrientation().isLeftToRight()) {
/*  150 */         Object[] arrayOfObject1 = (Object[])UIManager.get("PopupMenu.selectedWindowInputMapBindings.RightToLeft");
/*  151 */         if (arrayOfObject1 != null) {
/*  152 */           ComponentInputMap componentInputMap1 = LookAndFeel.makeComponentInputMap(paramJComponent, arrayOfObject1);
/*  153 */           componentInputMap1.setParent(componentInputMap);
/*  154 */           componentInputMap = componentInputMap1;
/*      */         } 
/*      */       } 
/*      */     } 
/*  158 */     return componentInputMap;
/*      */   }
/*      */   
/*      */   static ActionMap getActionMap() {
/*  162 */     return LazyActionMap.getActionMap(BasicPopupMenuUI.class, "PopupMenu.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  167 */     paramLazyActionMap.put(new Actions("cancel"));
/*  168 */     paramLazyActionMap.put(new Actions("selectNext"));
/*  169 */     paramLazyActionMap.put(new Actions("selectPrevious"));
/*  170 */     paramLazyActionMap.put(new Actions("selectParent"));
/*  171 */     paramLazyActionMap.put(new Actions("selectChild"));
/*  172 */     paramLazyActionMap.put(new Actions("return"));
/*  173 */     BasicLookAndFeel.installAudioActionMap(paramLazyActionMap);
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  177 */     uninstallDefaults();
/*  178 */     uninstallListeners();
/*  179 */     uninstallKeyboardActions();
/*      */     
/*  181 */     this.popupMenu = null;
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/*  185 */     LookAndFeel.uninstallBorder(this.popupMenu);
/*      */   }
/*      */   
/*      */   protected void uninstallListeners() {
/*  189 */     if (this.popupMenuListener != null) {
/*  190 */       this.popupMenu.removePopupMenuListener(this.popupMenuListener);
/*      */     }
/*  192 */     if (this.menuKeyListener != null) {
/*  193 */       this.popupMenu.removeMenuKeyListener(this.menuKeyListener);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  198 */     SwingUtilities.replaceUIActionMap(this.popupMenu, null);
/*  199 */     SwingUtilities.replaceUIInputMap(this.popupMenu, 2, null);
/*      */   }
/*      */ 
/*      */   
/*      */   static MenuElement getFirstPopup() {
/*  204 */     MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  205 */     MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*  206 */     MenuElement menuElement = null;
/*      */     
/*  208 */     for (byte b = 0; menuElement == null && b < arrayOfMenuElement.length; b++) {
/*  209 */       if (arrayOfMenuElement[b] instanceof JPopupMenu) {
/*  210 */         menuElement = arrayOfMenuElement[b];
/*      */       }
/*      */     } 
/*  213 */     return menuElement;
/*      */   }
/*      */   
/*      */   static JPopupMenu getLastPopup() {
/*  217 */     MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  218 */     MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*  219 */     JPopupMenu jPopupMenu = null;
/*      */     
/*  221 */     for (int i = arrayOfMenuElement.length - 1; jPopupMenu == null && i >= 0; i--) {
/*  222 */       if (arrayOfMenuElement[i] instanceof JPopupMenu)
/*  223 */         jPopupMenu = (JPopupMenu)arrayOfMenuElement[i]; 
/*      */     } 
/*  225 */     return jPopupMenu;
/*      */   }
/*      */   
/*      */   static List<JPopupMenu> getPopups() {
/*  229 */     MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  230 */     MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*      */     
/*  232 */     ArrayList<JPopupMenu> arrayList = new ArrayList(arrayOfMenuElement.length);
/*  233 */     for (MenuElement menuElement : arrayOfMenuElement) {
/*  234 */       if (menuElement instanceof JPopupMenu) {
/*  235 */         arrayList.add((JPopupMenu)menuElement);
/*      */       }
/*      */     } 
/*  238 */     return arrayList;
/*      */   }
/*      */   
/*      */   public boolean isPopupTrigger(MouseEvent paramMouseEvent) {
/*  242 */     return (paramMouseEvent.getID() == 502 && (paramMouseEvent
/*  243 */       .getModifiers() & 0x4) != 0);
/*      */   }
/*      */   
/*      */   private static boolean checkInvokerEqual(MenuElement paramMenuElement1, MenuElement paramMenuElement2) {
/*  247 */     Component component1 = paramMenuElement1.getComponent();
/*  248 */     Component component2 = paramMenuElement2.getComponent();
/*      */     
/*  250 */     if (component1 instanceof JPopupMenu) {
/*  251 */       component1 = ((JPopupMenu)component1).getInvoker();
/*      */     }
/*  253 */     if (component2 instanceof JPopupMenu) {
/*  254 */       component2 = ((JPopupMenu)component2).getInvoker();
/*      */     }
/*  256 */     return (component1 == component2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class BasicPopupMenuListener
/*      */     implements PopupMenuListener
/*      */   {
/*      */     private BasicPopupMenuListener() {}
/*      */ 
/*      */     
/*      */     public void popupMenuCanceled(PopupMenuEvent param1PopupMenuEvent) {}
/*      */ 
/*      */     
/*      */     public void popupMenuWillBecomeInvisible(PopupMenuEvent param1PopupMenuEvent) {}
/*      */ 
/*      */     
/*      */     public void popupMenuWillBecomeVisible(PopupMenuEvent param1PopupMenuEvent) {
/*  274 */       BasicLookAndFeel.playSound((JPopupMenu)param1PopupMenuEvent.getSource(), "PopupMenu.popupSound");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class BasicMenuKeyListener
/*      */     implements MenuKeyListener
/*      */   {
/*  284 */     MenuElement menuToOpen = null;
/*      */     
/*      */     public void menuKeyTyped(MenuKeyEvent param1MenuKeyEvent) {
/*  287 */       if (this.menuToOpen != null) {
/*      */         
/*  289 */         JPopupMenu jPopupMenu = ((JMenu)this.menuToOpen).getPopupMenu();
/*  290 */         MenuElement menuElement = BasicPopupMenuUI.findEnabledChild(jPopupMenu
/*  291 */             .getSubElements(), -1, true);
/*      */         
/*  293 */         ArrayList<MenuElement> arrayList = new ArrayList(Arrays.asList((Object[])param1MenuKeyEvent.getPath()));
/*  294 */         arrayList.add(this.menuToOpen);
/*  295 */         arrayList.add(jPopupMenu);
/*  296 */         if (menuElement != null) {
/*  297 */           arrayList.add(menuElement);
/*      */         }
/*  299 */         MenuElement[] arrayOfMenuElement = new MenuElement[0];
/*  300 */         arrayOfMenuElement = arrayList.<MenuElement>toArray(arrayOfMenuElement);
/*  301 */         MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement);
/*  302 */         param1MenuKeyEvent.consume();
/*      */       } 
/*  304 */       this.menuToOpen = null;
/*      */     }
/*      */     
/*      */     public void menuKeyPressed(MenuKeyEvent param1MenuKeyEvent) {
/*  308 */       char c = param1MenuKeyEvent.getKeyChar();
/*      */ 
/*      */       
/*  311 */       if (!Character.isLetterOrDigit(c)) {
/*      */         return;
/*      */       }
/*      */       
/*  315 */       MenuSelectionManager menuSelectionManager = param1MenuKeyEvent.getMenuSelectionManager();
/*  316 */       MenuElement[] arrayOfMenuElement1 = param1MenuKeyEvent.getPath();
/*  317 */       MenuElement[] arrayOfMenuElement2 = BasicPopupMenuUI.this.popupMenu.getSubElements();
/*  318 */       int i = -1;
/*  319 */       byte b1 = 0;
/*  320 */       byte b = -1;
/*  321 */       int[] arrayOfInt = null;
/*      */       
/*  323 */       for (byte b2 = 0; b2 < arrayOfMenuElement2.length; b2++) {
/*  324 */         if (arrayOfMenuElement2[b2] instanceof JMenuItem) {
/*      */ 
/*      */           
/*  327 */           JMenuItem jMenuItem = (JMenuItem)arrayOfMenuElement2[b2];
/*  328 */           int j = jMenuItem.getMnemonic();
/*  329 */           if (jMenuItem.isEnabled() && jMenuItem
/*  330 */             .isVisible() && lower(c) == lower(j)) {
/*  331 */             if (!b1) {
/*  332 */               b = b2;
/*  333 */               b1++;
/*      */             } else {
/*  335 */               if (arrayOfInt == null) {
/*  336 */                 arrayOfInt = new int[arrayOfMenuElement2.length];
/*  337 */                 arrayOfInt[0] = b;
/*      */               } 
/*  339 */               arrayOfInt[b1++] = b2;
/*      */             } 
/*      */           }
/*  342 */           if (jMenuItem.isArmed() || jMenuItem.isSelected()) {
/*  343 */             i = b1 - 1;
/*      */           }
/*      */         } 
/*      */       } 
/*  347 */       if (b1 != 0)
/*      */       {
/*  349 */         if (b1 == 1) {
/*      */           
/*  351 */           JMenuItem jMenuItem = (JMenuItem)arrayOfMenuElement2[b];
/*  352 */           if (jMenuItem instanceof JMenu) {
/*      */             
/*  354 */             this.menuToOpen = jMenuItem;
/*  355 */           } else if (jMenuItem.isEnabled()) {
/*      */             
/*  357 */             menuSelectionManager.clearSelectedPath();
/*  358 */             jMenuItem.doClick();
/*      */           } 
/*  360 */           param1MenuKeyEvent.consume();
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  367 */           MenuElement menuElement = arrayOfMenuElement2[arrayOfInt[(i + 1) % b1]];
/*      */           
/*  369 */           MenuElement[] arrayOfMenuElement = new MenuElement[arrayOfMenuElement1.length + 1];
/*  370 */           System.arraycopy(arrayOfMenuElement1, 0, arrayOfMenuElement, 0, arrayOfMenuElement1.length);
/*  371 */           arrayOfMenuElement[arrayOfMenuElement1.length] = menuElement;
/*  372 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*  373 */           param1MenuKeyEvent.consume();
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     public void menuKeyReleased(MenuKeyEvent param1MenuKeyEvent) {}
/*      */     
/*      */     private char lower(char param1Char) {
/*  381 */       return Character.toLowerCase(param1Char);
/*      */     }
/*      */     
/*      */     private char lower(int param1Int) {
/*  385 */       return Character.toLowerCase((char)param1Int);
/*      */     }
/*      */ 
/*      */     
/*      */     private BasicMenuKeyListener() {}
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String CANCEL = "cancel";
/*      */     private static final String SELECT_NEXT = "selectNext";
/*      */     private static final String SELECT_PREVIOUS = "selectPrevious";
/*      */     private static final String SELECT_PARENT = "selectParent";
/*      */     private static final String SELECT_CHILD = "selectChild";
/*      */     private static final String RETURN = "return";
/*      */     private static final boolean FORWARD = true;
/*      */     private static final boolean BACKWARD = false;
/*      */     private static final boolean PARENT = false;
/*      */     private static final boolean CHILD = true;
/*      */     
/*      */     Actions(String param1String) {
/*  408 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  412 */       String str = getName();
/*  413 */       if (str == "cancel") {
/*  414 */         cancel();
/*      */       }
/*  416 */       else if (str == "selectNext") {
/*  417 */         selectItem(true);
/*      */       }
/*  419 */       else if (str == "selectPrevious") {
/*  420 */         selectItem(false);
/*      */       }
/*  422 */       else if (str == "selectParent") {
/*  423 */         selectParentChild(false);
/*      */       }
/*  425 */       else if (str == "selectChild") {
/*  426 */         selectParentChild(true);
/*      */       }
/*  428 */       else if (str == "return") {
/*  429 */         doReturn();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void doReturn() {
/*  435 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/*  436 */       Component component = keyboardFocusManager.getFocusOwner();
/*  437 */       if (component != null && !(component instanceof JRootPane)) {
/*      */         return;
/*      */       }
/*      */       
/*  441 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  442 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*      */       
/*  444 */       if (arrayOfMenuElement.length > 0) {
/*  445 */         MenuElement menuElement = arrayOfMenuElement[arrayOfMenuElement.length - 1];
/*  446 */         if (menuElement instanceof JMenu) {
/*  447 */           MenuElement[] arrayOfMenuElement1 = new MenuElement[arrayOfMenuElement.length + 1];
/*  448 */           System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, arrayOfMenuElement.length);
/*  449 */           arrayOfMenuElement1[arrayOfMenuElement.length] = ((JMenu)menuElement).getPopupMenu();
/*  450 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*  451 */         } else if (menuElement instanceof JMenuItem) {
/*  452 */           JMenuItem jMenuItem = (JMenuItem)menuElement;
/*      */           
/*  454 */           if (jMenuItem.getUI() instanceof BasicMenuItemUI) {
/*  455 */             ((BasicMenuItemUI)jMenuItem.getUI()).doClick(menuSelectionManager);
/*      */           } else {
/*      */             
/*  458 */             menuSelectionManager.clearSelectedPath();
/*  459 */             jMenuItem.doClick(0);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     private void selectParentChild(boolean param1Boolean) {
/*  465 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  466 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*  467 */       int i = arrayOfMenuElement.length;
/*      */       
/*  469 */       if (!param1Boolean) {
/*      */         
/*  471 */         int j = i - 1;
/*      */         
/*  473 */         if (i > 2 && (arrayOfMenuElement[j] instanceof JPopupMenu || arrayOfMenuElement[--j] instanceof JPopupMenu) && 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  479 */           !((JMenu)arrayOfMenuElement[j - 1]).isTopLevelMenu()) {
/*      */ 
/*      */           
/*  482 */           MenuElement[] arrayOfMenuElement1 = new MenuElement[j];
/*  483 */           System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, j);
/*  484 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*  489 */       } else if (i > 0 && arrayOfMenuElement[i - 1] instanceof JMenu && 
/*  490 */         !((JMenu)arrayOfMenuElement[i - 1]).isTopLevelMenu()) {
/*      */         MenuElement[] arrayOfMenuElement2;
/*      */         
/*  493 */         JMenu jMenu = (JMenu)arrayOfMenuElement[i - 1];
/*  494 */         JPopupMenu jPopupMenu = jMenu.getPopupMenu();
/*  495 */         MenuElement[] arrayOfMenuElement1 = jPopupMenu.getSubElements();
/*  496 */         MenuElement menuElement = BasicPopupMenuUI.findEnabledChild(arrayOfMenuElement1, -1, true);
/*      */ 
/*      */         
/*  499 */         if (menuElement == null) {
/*  500 */           arrayOfMenuElement2 = new MenuElement[i + 1];
/*      */         } else {
/*  502 */           arrayOfMenuElement2 = new MenuElement[i + 2];
/*  503 */           arrayOfMenuElement2[i + 1] = menuElement;
/*      */         } 
/*  505 */         System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement2, 0, i);
/*  506 */         arrayOfMenuElement2[i] = jPopupMenu;
/*  507 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement2);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  514 */       if (i > 1 && arrayOfMenuElement[0] instanceof javax.swing.JMenuBar) {
/*  515 */         MenuElement menuElement1 = arrayOfMenuElement[1];
/*  516 */         MenuElement menuElement2 = BasicPopupMenuUI.findEnabledChild(arrayOfMenuElement[0]
/*  517 */             .getSubElements(), menuElement1, param1Boolean);
/*      */         
/*  519 */         if (menuElement2 != null && menuElement2 != menuElement1) {
/*      */           MenuElement[] arrayOfMenuElement1;
/*  521 */           if (i == 2) {
/*      */             
/*  523 */             arrayOfMenuElement1 = new MenuElement[2];
/*  524 */             arrayOfMenuElement1[0] = arrayOfMenuElement[0];
/*  525 */             arrayOfMenuElement1[1] = menuElement2;
/*      */           } else {
/*      */             
/*  528 */             arrayOfMenuElement1 = new MenuElement[3];
/*  529 */             arrayOfMenuElement1[0] = arrayOfMenuElement[0];
/*  530 */             arrayOfMenuElement1[1] = menuElement2;
/*  531 */             arrayOfMenuElement1[2] = ((JMenu)menuElement2).getPopupMenu();
/*      */           } 
/*  533 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void selectItem(boolean param1Boolean) {
/*  539 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  540 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*  541 */       if (arrayOfMenuElement.length == 0) {
/*      */         return;
/*      */       }
/*  544 */       int i = arrayOfMenuElement.length;
/*  545 */       if (i == 1 && arrayOfMenuElement[0] instanceof JPopupMenu) {
/*      */         
/*  547 */         JPopupMenu jPopupMenu = (JPopupMenu)arrayOfMenuElement[0];
/*  548 */         MenuElement[] arrayOfMenuElement1 = new MenuElement[2];
/*  549 */         arrayOfMenuElement1[0] = jPopupMenu;
/*  550 */         arrayOfMenuElement1[1] = BasicPopupMenuUI.findEnabledChild(jPopupMenu.getSubElements(), -1, param1Boolean);
/*  551 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*  552 */       } else if (i == 2 && arrayOfMenuElement[0] instanceof javax.swing.JMenuBar && arrayOfMenuElement[1] instanceof JMenu) {
/*      */         MenuElement[] arrayOfMenuElement1;
/*      */ 
/*      */ 
/*      */         
/*  557 */         JPopupMenu jPopupMenu = ((JMenu)arrayOfMenuElement[1]).getPopupMenu();
/*      */         
/*  559 */         MenuElement menuElement = BasicPopupMenuUI.findEnabledChild(jPopupMenu.getSubElements(), -1, true);
/*      */ 
/*      */         
/*  562 */         if (menuElement != null) {
/*      */           
/*  564 */           arrayOfMenuElement1 = new MenuElement[4];
/*  565 */           arrayOfMenuElement1[3] = menuElement;
/*      */         } else {
/*      */           
/*  568 */           arrayOfMenuElement1 = new MenuElement[3];
/*      */         } 
/*  570 */         System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, 2);
/*  571 */         arrayOfMenuElement1[2] = jPopupMenu;
/*  572 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*      */       }
/*  574 */       else if (arrayOfMenuElement[i - 1] instanceof JPopupMenu && arrayOfMenuElement[i - 2] instanceof JMenu) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  579 */         JMenu jMenu = (JMenu)arrayOfMenuElement[i - 2];
/*  580 */         JPopupMenu jPopupMenu = jMenu.getPopupMenu();
/*      */         
/*  582 */         MenuElement menuElement = BasicPopupMenuUI.findEnabledChild(jPopupMenu.getSubElements(), -1, param1Boolean);
/*      */         
/*  584 */         if (menuElement != null) {
/*  585 */           MenuElement[] arrayOfMenuElement1 = new MenuElement[i + 1];
/*  586 */           System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, i);
/*  587 */           arrayOfMenuElement1[i] = menuElement;
/*  588 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  594 */         else if (i > 2 && arrayOfMenuElement[i - 3] instanceof JPopupMenu) {
/*  595 */           jPopupMenu = (JPopupMenu)arrayOfMenuElement[i - 3];
/*  596 */           menuElement = BasicPopupMenuUI.findEnabledChild(jPopupMenu.getSubElements(), jMenu, param1Boolean);
/*      */ 
/*      */           
/*  599 */           if (menuElement != null && menuElement != jMenu) {
/*  600 */             MenuElement[] arrayOfMenuElement1 = new MenuElement[i - 1];
/*  601 */             System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, i - 2);
/*  602 */             arrayOfMenuElement1[i - 2] = menuElement;
/*  603 */             menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  610 */         MenuElement[] arrayOfMenuElement1 = arrayOfMenuElement[i - 2].getSubElements();
/*      */         
/*  612 */         MenuElement menuElement = BasicPopupMenuUI.findEnabledChild(arrayOfMenuElement1, arrayOfMenuElement[i - 1], param1Boolean);
/*  613 */         if (menuElement == null) {
/*  614 */           menuElement = BasicPopupMenuUI.findEnabledChild(arrayOfMenuElement1, -1, param1Boolean);
/*      */         }
/*  616 */         if (menuElement != null) {
/*  617 */           arrayOfMenuElement[i - 1] = menuElement;
/*  618 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void cancel() {
/*  627 */       JPopupMenu jPopupMenu = BasicPopupMenuUI.getLastPopup();
/*  628 */       if (jPopupMenu != null) {
/*  629 */         jPopupMenu.putClientProperty("JPopupMenu.firePopupMenuCanceled", Boolean.TRUE);
/*      */       }
/*  631 */       String str = UIManager.getString("Menu.cancelMode");
/*  632 */       if ("hideMenuTree".equals(str)) {
/*  633 */         MenuSelectionManager.defaultManager().clearSelectedPath();
/*      */       } else {
/*  635 */         shortenSelectedPath();
/*      */       } 
/*      */     }
/*      */     
/*      */     private void shortenSelectedPath() {
/*  640 */       MenuElement[] arrayOfMenuElement1 = MenuSelectionManager.defaultManager().getSelectedPath();
/*  641 */       if (arrayOfMenuElement1.length <= 2) {
/*  642 */         MenuSelectionManager.defaultManager().clearSelectedPath();
/*      */         
/*      */         return;
/*      */       } 
/*  646 */       int i = 2;
/*  647 */       MenuElement menuElement = arrayOfMenuElement1[arrayOfMenuElement1.length - 1];
/*  648 */       JPopupMenu jPopupMenu = BasicPopupMenuUI.getLastPopup();
/*  649 */       if (menuElement == jPopupMenu) {
/*  650 */         MenuElement menuElement1 = arrayOfMenuElement1[arrayOfMenuElement1.length - 2];
/*  651 */         if (menuElement1 instanceof JMenu) {
/*  652 */           JMenu jMenu = (JMenu)menuElement1;
/*  653 */           if (jMenu.isEnabled() && jPopupMenu.getComponentCount() > 0) {
/*      */             
/*  655 */             i = 1;
/*      */           } else {
/*      */             
/*  658 */             i = 3;
/*      */           } 
/*      */         } 
/*      */       } 
/*  662 */       if (arrayOfMenuElement1.length - i <= 2 && 
/*  663 */         !UIManager.getBoolean("Menu.preserveTopLevelSelection"))
/*      */       {
/*  665 */         i = arrayOfMenuElement1.length;
/*      */       }
/*  667 */       MenuElement[] arrayOfMenuElement2 = new MenuElement[arrayOfMenuElement1.length - i];
/*  668 */       System.arraycopy(arrayOfMenuElement1, 0, arrayOfMenuElement2, 0, arrayOfMenuElement1.length - i);
/*  669 */       MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static MenuElement nextEnabledChild(MenuElement[] paramArrayOfMenuElement, int paramInt1, int paramInt2) {
/*  675 */     for (int i = paramInt1; i <= paramInt2; i++) {
/*  676 */       if (paramArrayOfMenuElement[i] != null) {
/*  677 */         Component component = paramArrayOfMenuElement[i].getComponent();
/*  678 */         if (component != null && (component
/*  679 */           .isEnabled() || UIManager.getBoolean("MenuItem.disabledAreNavigable")) && component
/*  680 */           .isVisible()) {
/*  681 */           return paramArrayOfMenuElement[i];
/*      */         }
/*      */       } 
/*      */     } 
/*  685 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static MenuElement previousEnabledChild(MenuElement[] paramArrayOfMenuElement, int paramInt1, int paramInt2) {
/*  690 */     for (int i = paramInt1; i >= paramInt2; i--) {
/*  691 */       if (paramArrayOfMenuElement[i] != null) {
/*  692 */         Component component = paramArrayOfMenuElement[i].getComponent();
/*  693 */         if (component != null && (component
/*  694 */           .isEnabled() || UIManager.getBoolean("MenuItem.disabledAreNavigable")) && component
/*  695 */           .isVisible()) {
/*  696 */           return paramArrayOfMenuElement[i];
/*      */         }
/*      */       } 
/*      */     } 
/*  700 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   static MenuElement findEnabledChild(MenuElement[] paramArrayOfMenuElement, int paramInt, boolean paramBoolean) {
/*      */     MenuElement menuElement;
/*  706 */     if (paramBoolean) {
/*  707 */       menuElement = nextEnabledChild(paramArrayOfMenuElement, paramInt + 1, paramArrayOfMenuElement.length - 1);
/*  708 */       if (menuElement == null) menuElement = nextEnabledChild(paramArrayOfMenuElement, 0, paramInt - 1); 
/*      */     } else {
/*  710 */       menuElement = previousEnabledChild(paramArrayOfMenuElement, paramInt - 1, 0);
/*  711 */       if (menuElement == null) menuElement = previousEnabledChild(paramArrayOfMenuElement, paramArrayOfMenuElement.length - 1, paramInt + 1);
/*      */     
/*      */     } 
/*  714 */     return menuElement;
/*      */   }
/*      */ 
/*      */   
/*      */   static MenuElement findEnabledChild(MenuElement[] paramArrayOfMenuElement, MenuElement paramMenuElement, boolean paramBoolean) {
/*  719 */     for (byte b = 0; b < paramArrayOfMenuElement.length; b++) {
/*  720 */       if (paramArrayOfMenuElement[b] == paramMenuElement) {
/*  721 */         return findEnabledChild(paramArrayOfMenuElement, b, paramBoolean);
/*      */       }
/*      */     } 
/*  724 */     return null;
/*      */   }
/*      */   
/*      */   static class MouseGrabber
/*      */     implements ChangeListener, AWTEventListener, ComponentListener, WindowListener
/*      */   {
/*      */     Window grabbedWindow;
/*      */     MenuElement[] lastPathSelected;
/*      */     
/*      */     public MouseGrabber() {
/*  734 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  735 */       menuSelectionManager.addChangeListener(this);
/*  736 */       this.lastPathSelected = menuSelectionManager.getSelectedPath();
/*  737 */       if (this.lastPathSelected.length != 0) {
/*  738 */         grabWindow(this.lastPathSelected);
/*      */       }
/*      */     }
/*      */     
/*      */     void uninstall() {
/*  743 */       synchronized (BasicPopupMenuUI.MOUSE_GRABBER_KEY) {
/*  744 */         MenuSelectionManager.defaultManager().removeChangeListener(this);
/*  745 */         ungrabWindow();
/*  746 */         AppContext.getAppContext().remove(BasicPopupMenuUI.MOUSE_GRABBER_KEY);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void grabWindow(MenuElement[] param1ArrayOfMenuElement) {
/*  752 */       final Toolkit tk = Toolkit.getDefaultToolkit();
/*  753 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*  756 */               tk.addAWTEventListener(BasicPopupMenuUI.MouseGrabber.this, -2147352464L);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  761 */               return null;
/*      */             }
/*      */           });
/*      */ 
/*      */       
/*  766 */       Component component = param1ArrayOfMenuElement[0].getComponent();
/*  767 */       if (component instanceof JPopupMenu) {
/*  768 */         component = ((JPopupMenu)component).getInvoker();
/*      */       }
/*  770 */       this
/*      */         
/*  772 */         .grabbedWindow = (component instanceof Window) ? (Window)component : SwingUtilities.getWindowAncestor(component);
/*  773 */       if (this.grabbedWindow != null) {
/*  774 */         if (toolkit instanceof SunToolkit) {
/*  775 */           ((SunToolkit)toolkit).grab(this.grabbedWindow);
/*      */         } else {
/*  777 */           this.grabbedWindow.addComponentListener(this);
/*  778 */           this.grabbedWindow.addWindowListener(this);
/*      */         } 
/*      */       }
/*      */     }
/*      */     
/*      */     void ungrabWindow() {
/*  784 */       final Toolkit tk = Toolkit.getDefaultToolkit();
/*      */       
/*  786 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */           {
/*      */             public Object run() {
/*  789 */               tk.removeAWTEventListener(BasicPopupMenuUI.MouseGrabber.this);
/*  790 */               return null;
/*      */             }
/*      */           });
/*      */       
/*  794 */       realUngrabWindow();
/*      */     }
/*      */     
/*      */     void realUngrabWindow() {
/*  798 */       Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  799 */       if (this.grabbedWindow != null) {
/*  800 */         if (toolkit instanceof SunToolkit) {
/*  801 */           ((SunToolkit)toolkit).ungrab(this.grabbedWindow);
/*      */         } else {
/*  803 */           this.grabbedWindow.removeComponentListener(this);
/*  804 */           this.grabbedWindow.removeWindowListener(this);
/*      */         } 
/*  806 */         this.grabbedWindow = null;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/*  811 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  812 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*      */       
/*  814 */       if (this.lastPathSelected.length == 0 && arrayOfMenuElement.length != 0) {
/*  815 */         grabWindow(arrayOfMenuElement);
/*      */       }
/*      */       
/*  818 */       if (this.lastPathSelected.length != 0 && arrayOfMenuElement.length == 0) {
/*  819 */         ungrabWindow();
/*      */       }
/*      */       
/*  822 */       this.lastPathSelected = arrayOfMenuElement;
/*      */     }
/*      */     
/*      */     public void eventDispatched(AWTEvent param1AWTEvent) {
/*  826 */       if (param1AWTEvent instanceof sun.awt.UngrabEvent) {
/*      */         
/*  828 */         cancelPopupMenu();
/*      */         return;
/*      */       } 
/*  831 */       if (!(param1AWTEvent instanceof MouseEvent)) {
/*      */         return;
/*      */       }
/*      */       
/*  835 */       MouseEvent mouseEvent = (MouseEvent)param1AWTEvent;
/*  836 */       Component component = mouseEvent.getComponent();
/*  837 */       switch (mouseEvent.getID()) {
/*      */         case 501:
/*  839 */           if (isInPopup(component) || (component instanceof JMenu && ((JMenu)component)
/*  840 */             .isSelected())) {
/*      */             return;
/*      */           }
/*  843 */           if (!(component instanceof JComponent) || ((JComponent)component)
/*  844 */             .getClientProperty("doNotCancelPopup") != BasicComboBoxUI.HIDE_POPUP_KEY) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  849 */             cancelPopupMenu();
/*      */ 
/*      */ 
/*      */             
/*  853 */             boolean bool = UIManager.getBoolean("PopupMenu.consumeEventOnClose");
/*      */             
/*  855 */             if (bool && !(component instanceof MenuElement)) {
/*  856 */               mouseEvent.consume();
/*      */             }
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 502:
/*  862 */           if (!(component instanceof MenuElement))
/*      */           {
/*  864 */             if (isInPopup(component)) {
/*      */               break;
/*      */             }
/*      */           }
/*  868 */           if (component instanceof JMenu || !(component instanceof JMenuItem)) {
/*  869 */             MenuSelectionManager.defaultManager()
/*  870 */               .processMouseEvent(mouseEvent);
/*      */           }
/*      */           break;
/*      */         case 506:
/*  874 */           if (!(component instanceof MenuElement))
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  879 */             if (isInPopup(component)) {
/*      */               break;
/*      */             }
/*      */           }
/*  883 */           MenuSelectionManager.defaultManager()
/*  884 */             .processMouseEvent(mouseEvent);
/*      */           break;
/*      */         case 507:
/*  887 */           if (isInPopup(component) || (component instanceof JComboBox && ((JComboBox)component)
/*  888 */             .isPopupVisible())) {
/*      */             return;
/*      */           }
/*      */           
/*  892 */           cancelPopupMenu();
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     boolean isInPopup(Component param1Component) {
/*  898 */       for (Component component = param1Component; component != null && 
/*  899 */         !(component instanceof java.applet.Applet) && !(component instanceof Window); component = component.getParent()) {
/*      */         
/*  901 */         if (component instanceof JPopupMenu) {
/*  902 */           return true;
/*      */         }
/*      */       } 
/*  905 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void cancelPopupMenu() {
/*      */       try {
/*  915 */         List<JPopupMenu> list = BasicPopupMenuUI.getPopups();
/*  916 */         for (JPopupMenu jPopupMenu : list) {
/*  917 */           jPopupMenu.putClientProperty("JPopupMenu.firePopupMenuCanceled", Boolean.TRUE);
/*      */         }
/*  919 */         MenuSelectionManager.defaultManager().clearSelectedPath();
/*  920 */       } catch (RuntimeException runtimeException) {
/*  921 */         realUngrabWindow();
/*  922 */         throw runtimeException;
/*  923 */       } catch (Error error) {
/*  924 */         realUngrabWindow();
/*  925 */         throw error;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void componentResized(ComponentEvent param1ComponentEvent) {
/*  930 */       cancelPopupMenu();
/*      */     }
/*      */     public void componentMoved(ComponentEvent param1ComponentEvent) {
/*  933 */       cancelPopupMenu();
/*      */     }
/*      */     public void componentShown(ComponentEvent param1ComponentEvent) {
/*  936 */       cancelPopupMenu();
/*      */     }
/*      */     public void componentHidden(ComponentEvent param1ComponentEvent) {
/*  939 */       cancelPopupMenu();
/*      */     }
/*      */     public void windowClosing(WindowEvent param1WindowEvent) {
/*  942 */       cancelPopupMenu();
/*      */     }
/*      */     public void windowClosed(WindowEvent param1WindowEvent) {
/*  945 */       cancelPopupMenu();
/*      */     }
/*      */     public void windowIconified(WindowEvent param1WindowEvent) {
/*  948 */       cancelPopupMenu();
/*      */     }
/*      */     public void windowDeactivated(WindowEvent param1WindowEvent) {
/*  951 */       cancelPopupMenu();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void windowOpened(WindowEvent param1WindowEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void windowDeiconified(WindowEvent param1WindowEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void windowActivated(WindowEvent param1WindowEvent) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class MenuKeyboardHelper
/*      */     implements ChangeListener, KeyListener
/*      */   {
/*  973 */     private Component lastFocused = null;
/*  974 */     private MenuElement[] lastPathSelected = new MenuElement[0];
/*      */     
/*      */     private JPopupMenu lastPopup;
/*      */     private JRootPane invokerRootPane;
/*  978 */     private ActionMap menuActionMap = BasicPopupMenuUI.getActionMap();
/*      */ 
/*      */     
/*      */     private InputMap menuInputMap;
/*      */ 
/*      */     
/*      */     private boolean focusTraversalKeysEnabled;
/*      */ 
/*      */     
/*      */     private boolean receivedKeyPressed = false;
/*      */ 
/*      */     
/*      */     void removeItems() {
/*  991 */       if (this.lastFocused != null) {
/*  992 */         if (!this.lastFocused.requestFocusInWindow()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1000 */           Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
/* 1001 */           if (window != null && "###focusableSwingPopup###"
/* 1002 */             .equals(window.getName())) {
/* 1003 */             this.lastFocused.requestFocus();
/*      */           }
/*      */         } 
/*      */         
/* 1007 */         this.lastFocused = null;
/*      */       } 
/* 1009 */       if (this.invokerRootPane != null) {
/* 1010 */         this.invokerRootPane.removeKeyListener(this);
/* 1011 */         this.invokerRootPane.setFocusTraversalKeysEnabled(this.focusTraversalKeysEnabled);
/* 1012 */         removeUIInputMap(this.invokerRootPane, this.menuInputMap);
/* 1013 */         removeUIActionMap(this.invokerRootPane, this.menuActionMap);
/* 1014 */         this.invokerRootPane = null;
/*      */       } 
/* 1016 */       this.receivedKeyPressed = false;
/*      */     }
/*      */     
/* 1019 */     private FocusListener rootPaneFocusListener = new FocusAdapter() {
/*      */         public void focusGained(FocusEvent param2FocusEvent) {
/* 1021 */           Component component = param2FocusEvent.getOppositeComponent();
/* 1022 */           if (component != null) {
/* 1023 */             BasicPopupMenuUI.MenuKeyboardHelper.this.lastFocused = component;
/*      */           }
/* 1025 */           param2FocusEvent.getComponent().removeFocusListener(this);
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     JPopupMenu getActivePopup(MenuElement[] param1ArrayOfMenuElement) {
/* 1034 */       for (int i = param1ArrayOfMenuElement.length - 1; i >= 0; i--) {
/* 1035 */         MenuElement menuElement = param1ArrayOfMenuElement[i];
/* 1036 */         if (menuElement instanceof JPopupMenu) {
/* 1037 */           return (JPopupMenu)menuElement;
/*      */         }
/*      */       } 
/* 1040 */       return null;
/*      */     }
/*      */     
/*      */     void addUIInputMap(JComponent param1JComponent, InputMap param1InputMap) {
/* 1044 */       InputMap inputMap1 = null;
/* 1045 */       InputMap inputMap2 = param1JComponent.getInputMap(2);
/*      */       
/* 1047 */       while (inputMap2 != null && !(inputMap2 instanceof javax.swing.plaf.UIResource)) {
/* 1048 */         inputMap1 = inputMap2;
/* 1049 */         inputMap2 = inputMap2.getParent();
/*      */       } 
/*      */       
/* 1052 */       if (inputMap1 == null) {
/* 1053 */         param1JComponent.setInputMap(2, param1InputMap);
/*      */       } else {
/* 1055 */         inputMap1.setParent(param1InputMap);
/*      */       } 
/* 1057 */       param1InputMap.setParent(inputMap2);
/*      */     }
/*      */     
/*      */     void addUIActionMap(JComponent param1JComponent, ActionMap param1ActionMap) {
/* 1061 */       ActionMap actionMap1 = null;
/* 1062 */       ActionMap actionMap2 = param1JComponent.getActionMap();
/*      */       
/* 1064 */       while (actionMap2 != null && !(actionMap2 instanceof javax.swing.plaf.UIResource)) {
/* 1065 */         actionMap1 = actionMap2;
/* 1066 */         actionMap2 = actionMap2.getParent();
/*      */       } 
/*      */       
/* 1069 */       if (actionMap1 == null) {
/* 1070 */         param1JComponent.setActionMap(param1ActionMap);
/*      */       } else {
/* 1072 */         actionMap1.setParent(param1ActionMap);
/*      */       } 
/* 1074 */       param1ActionMap.setParent(actionMap2);
/*      */     }
/*      */     
/*      */     void removeUIInputMap(JComponent param1JComponent, InputMap param1InputMap) {
/* 1078 */       InputMap inputMap1 = null;
/* 1079 */       InputMap inputMap2 = param1JComponent.getInputMap(2);
/*      */       
/* 1081 */       while (inputMap2 != null) {
/* 1082 */         if (inputMap2 == param1InputMap) {
/* 1083 */           if (inputMap1 == null) {
/* 1084 */             param1JComponent.setInputMap(2, param1InputMap
/* 1085 */                 .getParent()); break;
/*      */           } 
/* 1087 */           inputMap1.setParent(param1InputMap.getParent());
/*      */           
/*      */           break;
/*      */         } 
/* 1091 */         inputMap1 = inputMap2;
/* 1092 */         inputMap2 = inputMap2.getParent();
/*      */       } 
/*      */     }
/*      */     
/*      */     void removeUIActionMap(JComponent param1JComponent, ActionMap param1ActionMap) {
/* 1097 */       ActionMap actionMap1 = null;
/* 1098 */       ActionMap actionMap2 = param1JComponent.getActionMap();
/*      */       
/* 1100 */       while (actionMap2 != null) {
/* 1101 */         if (actionMap2 == param1ActionMap) {
/* 1102 */           if (actionMap1 == null) {
/* 1103 */             param1JComponent.setActionMap(param1ActionMap.getParent()); break;
/*      */           } 
/* 1105 */           actionMap1.setParent(param1ActionMap.getParent());
/*      */           
/*      */           break;
/*      */         } 
/* 1109 */         actionMap1 = actionMap2;
/* 1110 */         actionMap2 = actionMap2.getParent();
/*      */       } 
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1115 */       if (!(UIManager.getLookAndFeel() instanceof BasicLookAndFeel)) {
/* 1116 */         uninstall();
/*      */         return;
/*      */       } 
/* 1119 */       MenuSelectionManager menuSelectionManager = (MenuSelectionManager)param1ChangeEvent.getSource();
/* 1120 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 1121 */       JPopupMenu jPopupMenu = getActivePopup(arrayOfMenuElement);
/* 1122 */       if (jPopupMenu != null && !jPopupMenu.isFocusable()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1127 */       if (this.lastPathSelected.length != 0 && arrayOfMenuElement.length != 0 && 
/* 1128 */         !BasicPopupMenuUI.checkInvokerEqual(arrayOfMenuElement[0], this.lastPathSelected[0])) {
/* 1129 */         removeItems();
/* 1130 */         this.lastPathSelected = new MenuElement[0];
/*      */       } 
/*      */ 
/*      */       
/* 1134 */       if (this.lastPathSelected.length == 0 && arrayOfMenuElement.length > 0) {
/*      */         JComponent jComponent;
/*      */ 
/*      */         
/* 1138 */         if (jPopupMenu == null) {
/* 1139 */           if (arrayOfMenuElement.length == 2 && arrayOfMenuElement[0] instanceof javax.swing.JMenuBar && arrayOfMenuElement[1] instanceof JMenu) {
/*      */ 
/*      */             
/* 1142 */             jComponent = (JComponent)arrayOfMenuElement[1];
/* 1143 */             jPopupMenu = ((JMenu)jComponent).getPopupMenu();
/*      */           } else {
/*      */             return;
/*      */           } 
/*      */         } else {
/* 1148 */           Component component = jPopupMenu.getInvoker();
/* 1149 */           if (component instanceof JFrame) {
/* 1150 */             jComponent = ((JFrame)component).getRootPane();
/* 1151 */           } else if (component instanceof JDialog) {
/* 1152 */             jComponent = ((JDialog)component).getRootPane();
/* 1153 */           } else if (component instanceof JApplet) {
/* 1154 */             jComponent = ((JApplet)component).getRootPane();
/*      */           } else {
/* 1156 */             while (!(component instanceof JComponent)) {
/* 1157 */               if (component == null) {
/*      */                 return;
/*      */               }
/* 1160 */               component = component.getParent();
/*      */             } 
/* 1162 */             jComponent = (JComponent)component;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1167 */         this
/* 1168 */           .lastFocused = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*      */ 
/*      */ 
/*      */         
/* 1172 */         this.invokerRootPane = SwingUtilities.getRootPane(jComponent);
/* 1173 */         if (this.invokerRootPane != null) {
/* 1174 */           this.invokerRootPane.addFocusListener(this.rootPaneFocusListener);
/* 1175 */           this.invokerRootPane.requestFocus(true);
/* 1176 */           this.invokerRootPane.addKeyListener(this);
/* 1177 */           this
/* 1178 */             .focusTraversalKeysEnabled = this.invokerRootPane.getFocusTraversalKeysEnabled();
/* 1179 */           this.invokerRootPane.setFocusTraversalKeysEnabled(false);
/*      */           
/* 1181 */           this.menuInputMap = BasicPopupMenuUI.getInputMap(jPopupMenu, this.invokerRootPane);
/* 1182 */           addUIInputMap(this.invokerRootPane, this.menuInputMap);
/* 1183 */           addUIActionMap(this.invokerRootPane, this.menuActionMap);
/*      */         } 
/* 1185 */       } else if (this.lastPathSelected.length != 0 && arrayOfMenuElement.length == 0) {
/*      */ 
/*      */         
/* 1188 */         removeItems();
/*      */       }
/* 1190 */       else if (jPopupMenu != this.lastPopup) {
/* 1191 */         this.receivedKeyPressed = false;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1196 */       this.lastPathSelected = arrayOfMenuElement;
/* 1197 */       this.lastPopup = jPopupMenu;
/*      */     }
/*      */     
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 1201 */       this.receivedKeyPressed = true;
/* 1202 */       MenuSelectionManager.defaultManager().processKeyEvent(param1KeyEvent);
/*      */     }
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {
/* 1206 */       if (this.receivedKeyPressed) {
/* 1207 */         this.receivedKeyPressed = false;
/* 1208 */         MenuSelectionManager.defaultManager().processKeyEvent(param1KeyEvent);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {
/* 1213 */       if (this.receivedKeyPressed) {
/* 1214 */         MenuSelectionManager.defaultManager().processKeyEvent(param1KeyEvent);
/*      */       }
/*      */     }
/*      */     
/*      */     void uninstall() {
/* 1219 */       synchronized (BasicPopupMenuUI.MENU_KEYBOARD_HELPER_KEY) {
/* 1220 */         MenuSelectionManager.defaultManager().removeChangeListener(this);
/* 1221 */         AppContext.getAppContext().remove(BasicPopupMenuUI.MENU_KEYBOARD_HELPER_KEY);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicPopupMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */