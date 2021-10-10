/*     */ package javax.swing.plaf.basic;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import javax.swing.InputMap;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.MenuElement;
/*     */ import javax.swing.MenuSelectionManager;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.Timer;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MenuDragMouseEvent;
/*     */ import javax.swing.event.MenuDragMouseListener;
/*     */ import javax.swing.event.MenuKeyEvent;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ import javax.swing.event.MenuListener;
/*     */ import javax.swing.event.MouseInputListener;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import sun.swing.DefaultLookup;
/*     */ import sun.swing.UIAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicMenuUI
/*     */   extends BasicMenuItemUI
/*     */ {
/*     */   protected ChangeListener changeListener;
/*     */   protected MenuListener menuListener;
/*  54 */   private int lastMnemonic = 0;
/*     */   
/*     */   private InputMap selectedWindowInputMap;
/*     */   
/*     */   private static final boolean TRACE = false;
/*     */   
/*     */   private static final boolean VERBOSE = false;
/*     */   
/*     */   private static final boolean DEBUG = false;
/*     */   
/*     */   private static boolean crossMenuMnemonic = true;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  67 */     return new BasicMenuUI();
/*     */   }
/*     */   
/*     */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  71 */     BasicMenuItemUI.loadActionMap(paramLazyActionMap);
/*  72 */     paramLazyActionMap.put(new Actions("selectMenu", null, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  77 */     super.installDefaults();
/*  78 */     updateDefaultBackgroundColor();
/*  79 */     ((JMenu)this.menuItem).setDelay(200);
/*  80 */     crossMenuMnemonic = UIManager.getBoolean("Menu.crossMenuMnemonic");
/*     */   }
/*     */   
/*     */   protected String getPropertyPrefix() {
/*  84 */     return "Menu";
/*     */   }
/*     */   
/*     */   protected void installListeners() {
/*  88 */     super.installListeners();
/*     */     
/*  90 */     if (this.changeListener == null) {
/*  91 */       this.changeListener = createChangeListener(this.menuItem);
/*     */     }
/*  93 */     if (this.changeListener != null) {
/*  94 */       this.menuItem.addChangeListener(this.changeListener);
/*     */     }
/*  96 */     if (this.menuListener == null) {
/*  97 */       this.menuListener = createMenuListener(this.menuItem);
/*     */     }
/*  99 */     if (this.menuListener != null)
/* 100 */       ((JMenu)this.menuItem).addMenuListener(this.menuListener); 
/*     */   }
/*     */   
/*     */   protected void installKeyboardActions() {
/* 104 */     super.installKeyboardActions();
/* 105 */     updateMnemonicBinding();
/*     */   }
/*     */   
/*     */   void installLazyActionMap() {
/* 109 */     LazyActionMap.installLazyActionMap(this.menuItem, BasicMenuUI.class, 
/* 110 */         getPropertyPrefix() + ".actionMap");
/*     */   }
/*     */   
/*     */   void updateMnemonicBinding() {
/* 114 */     int i = this.menuItem.getModel().getMnemonic();
/* 115 */     int[] arrayOfInt = (int[])DefaultLookup.get(this.menuItem, this, "Menu.shortcutKeys");
/*     */     
/* 117 */     if (arrayOfInt == null) {
/* 118 */       arrayOfInt = new int[] { 8 };
/*     */     }
/* 120 */     if (i == this.lastMnemonic) {
/*     */       return;
/*     */     }
/* 123 */     InputMap inputMap = SwingUtilities.getUIInputMap(this.menuItem, 2);
/*     */     
/* 125 */     if (this.lastMnemonic != 0 && inputMap != null) {
/* 126 */       for (int j : arrayOfInt) {
/* 127 */         inputMap.remove(
/* 128 */             KeyStroke.getKeyStroke(this.lastMnemonic, j, false));
/*     */       }
/*     */     }
/* 131 */     if (i != 0) {
/* 132 */       if (inputMap == null) {
/* 133 */         inputMap = createInputMap(2);
/*     */         
/* 135 */         SwingUtilities.replaceUIInputMap(this.menuItem, 2, inputMap);
/*     */       } 
/*     */       
/* 138 */       for (int j : arrayOfInt) {
/* 139 */         inputMap.put(KeyStroke.getKeyStroke(i, j, false), "selectMenu");
/*     */       }
/*     */     } 
/*     */     
/* 143 */     this.lastMnemonic = i;
/*     */   }
/*     */   
/*     */   protected void uninstallKeyboardActions() {
/* 147 */     super.uninstallKeyboardActions();
/* 148 */     this.lastMnemonic = 0;
/*     */   }
/*     */   
/*     */   protected MouseInputListener createMouseInputListener(JComponent paramJComponent) {
/* 152 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected MenuListener createMenuListener(JComponent paramJComponent) {
/* 156 */     return null;
/*     */   }
/*     */   
/*     */   protected ChangeListener createChangeListener(JComponent paramJComponent) {
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   protected PropertyChangeListener createPropertyChangeListener(JComponent paramJComponent) {
/* 164 */     return getHandler();
/*     */   }
/*     */   
/*     */   BasicMenuItemUI.Handler getHandler() {
/* 168 */     if (this.handler == null) {
/* 169 */       this.handler = new Handler();
/*     */     }
/* 171 */     return this.handler;
/*     */   }
/*     */   
/*     */   protected void uninstallDefaults() {
/* 175 */     this.menuItem.setArmed(false);
/* 176 */     this.menuItem.setSelected(false);
/* 177 */     this.menuItem.resetKeyboardActions();
/* 178 */     super.uninstallDefaults();
/*     */   }
/*     */   
/*     */   protected void uninstallListeners() {
/* 182 */     super.uninstallListeners();
/*     */     
/* 184 */     if (this.changeListener != null) {
/* 185 */       this.menuItem.removeChangeListener(this.changeListener);
/*     */     }
/* 187 */     if (this.menuListener != null) {
/* 188 */       ((JMenu)this.menuItem).removeMenuListener(this.menuListener);
/*     */     }
/* 190 */     this.changeListener = null;
/* 191 */     this.menuListener = null;
/* 192 */     this.handler = null;
/*     */   }
/*     */   
/*     */   protected MenuDragMouseListener createMenuDragMouseListener(JComponent paramJComponent) {
/* 196 */     return getHandler();
/*     */   }
/*     */   
/*     */   protected MenuKeyListener createMenuKeyListener(JComponent paramJComponent) {
/* 200 */     return (MenuKeyListener)getHandler();
/*     */   }
/*     */   
/*     */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 204 */     if (((JMenu)this.menuItem).isTopLevelMenu() == true) {
/* 205 */       Dimension dimension = paramJComponent.getPreferredSize();
/* 206 */       return new Dimension(dimension.width, 32767);
/*     */     } 
/* 208 */     return null;
/*     */   }
/*     */   
/*     */   protected void setupPostTimer(JMenu paramJMenu) {
/* 212 */     Timer timer = new Timer(paramJMenu.getDelay(), new Actions("selectMenu", paramJMenu, false));
/*     */     
/* 214 */     timer.setRepeats(false);
/* 215 */     timer.start();
/*     */   }
/*     */   
/*     */   private static void appendPath(MenuElement[] paramArrayOfMenuElement, MenuElement paramMenuElement) {
/* 219 */     MenuElement[] arrayOfMenuElement = new MenuElement[paramArrayOfMenuElement.length + 1];
/* 220 */     System.arraycopy(paramArrayOfMenuElement, 0, arrayOfMenuElement, 0, paramArrayOfMenuElement.length);
/* 221 */     arrayOfMenuElement[paramArrayOfMenuElement.length] = paramMenuElement;
/* 222 */     MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Actions
/*     */     extends UIAction
/*     */   {
/*     */     private static final String SELECT = "selectMenu";
/*     */     private JMenu menu;
/*     */     private boolean force = false;
/*     */     
/*     */     Actions(String param1String, JMenu param1JMenu, boolean param1Boolean) {
/* 234 */       super(param1String);
/* 235 */       this.menu = param1JMenu;
/* 236 */       this.force = param1Boolean;
/*     */     }
/*     */     
/*     */     private JMenu getMenu(ActionEvent param1ActionEvent) {
/* 240 */       if (param1ActionEvent.getSource() instanceof JMenu) {
/* 241 */         return (JMenu)param1ActionEvent.getSource();
/*     */       }
/* 243 */       return this.menu;
/*     */     }
/*     */     
/*     */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 247 */       JMenu jMenu = getMenu(param1ActionEvent);
/* 248 */       if (!BasicMenuUI.crossMenuMnemonic) {
/* 249 */         JPopupMenu jPopupMenu = BasicPopupMenuUI.getLastPopup();
/* 250 */         if (jPopupMenu != null && jPopupMenu != jMenu.getParent()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 255 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 256 */       if (this.force) {
/* 257 */         Container container = jMenu.getParent();
/* 258 */         if (container != null && container instanceof javax.swing.JMenuBar) {
/*     */ 
/*     */ 
/*     */           
/* 262 */           MenuElement[] arrayOfMenuElement1, arrayOfMenuElement2 = jMenu.getPopupMenu().getSubElements();
/* 263 */           if (arrayOfMenuElement2.length > 0) {
/* 264 */             arrayOfMenuElement1 = new MenuElement[4];
/* 265 */             arrayOfMenuElement1[0] = (MenuElement)container;
/* 266 */             arrayOfMenuElement1[1] = jMenu;
/* 267 */             arrayOfMenuElement1[2] = jMenu.getPopupMenu();
/* 268 */             arrayOfMenuElement1[3] = arrayOfMenuElement2[0];
/*     */           } else {
/* 270 */             arrayOfMenuElement1 = new MenuElement[3];
/* 271 */             arrayOfMenuElement1[0] = (MenuElement)container;
/* 272 */             arrayOfMenuElement1[1] = jMenu;
/* 273 */             arrayOfMenuElement1[2] = jMenu.getPopupMenu();
/*     */           } 
/* 275 */           menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*     */         } 
/*     */       } else {
/* 278 */         MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 279 */         if (arrayOfMenuElement.length > 0 && arrayOfMenuElement[arrayOfMenuElement.length - 1] == jMenu) {
/* 280 */           BasicMenuUI.appendPath(arrayOfMenuElement, jMenu.getPopupMenu());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEnabled(Object param1Object) {
/* 286 */       if (param1Object instanceof JMenu) {
/* 287 */         return ((JMenu)param1Object).isEnabled();
/*     */       }
/* 289 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDefaultBackgroundColor() {
/* 298 */     if (!UIManager.getBoolean("Menu.useMenuBarBackgroundForTopLevel")) {
/*     */       return;
/*     */     }
/* 301 */     JMenu jMenu = (JMenu)this.menuItem;
/* 302 */     if (jMenu.getBackground() instanceof javax.swing.plaf.UIResource) {
/* 303 */       if (jMenu.isTopLevelMenu()) {
/* 304 */         jMenu.setBackground(UIManager.getColor("MenuBar.background"));
/*     */       } else {
/* 306 */         jMenu.setBackground(UIManager.getColor(getPropertyPrefix() + ".background"));
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
/*     */   protected class MouseInputHandler
/*     */     implements MouseInputListener
/*     */   {
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 331 */       BasicMenuUI.this.getHandler().mouseClicked(param1MouseEvent);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 342 */       BasicMenuUI.this.getHandler().mousePressed(param1MouseEvent);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 352 */       BasicMenuUI.this.getHandler().mouseReleased(param1MouseEvent);
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
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 364 */       BasicMenuUI.this.getHandler().mouseEntered(param1MouseEvent);
/*     */     }
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 367 */       BasicMenuUI.this.getHandler().mouseExited(param1MouseEvent);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 378 */       BasicMenuUI.this.getHandler().mouseDragged(param1MouseEvent);
/*     */     }
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 382 */       BasicMenuUI.this.getHandler().mouseMoved(param1MouseEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class ChangeHandler
/*     */     implements ChangeListener
/*     */   {
/*     */     public JMenu menu;
/*     */     
/*     */     public BasicMenuUI ui;
/*     */     public boolean isSelected = false;
/*     */     public Component wasFocused;
/*     */     
/*     */     public ChangeHandler(JMenu param1JMenu, BasicMenuUI param1BasicMenuUI1) {
/* 397 */       this.menu = param1JMenu;
/* 398 */       this.ui = param1BasicMenuUI1;
/*     */     }
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {}
/*     */   }
/*     */   
/*     */   private class Handler
/*     */     extends BasicMenuItemUI.Handler implements MenuKeyListener {
/*     */     private Handler() {}
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 409 */       if (param1PropertyChangeEvent.getPropertyName() == "mnemonic") {
/*     */         
/* 411 */         BasicMenuUI.this.updateMnemonicBinding();
/*     */       } else {
/*     */         
/* 414 */         if (param1PropertyChangeEvent.getPropertyName().equals("ancestor")) {
/* 415 */           BasicMenuUI.this.updateDefaultBackgroundColor();
/*     */         }
/* 417 */         super.propertyChange(param1PropertyChangeEvent);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 435 */       JMenu jMenu = (JMenu)BasicMenuUI.this.menuItem;
/* 436 */       if (!jMenu.isEnabled()) {
/*     */         return;
/*     */       }
/*     */       
/* 440 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 441 */       if (jMenu.isTopLevelMenu()) {
/* 442 */         if (jMenu.isSelected() && jMenu.getPopupMenu().isShowing()) {
/* 443 */           menuSelectionManager.clearSelectedPath();
/*     */         } else {
/* 445 */           Container container = jMenu.getParent();
/* 446 */           if (container != null && container instanceof javax.swing.JMenuBar) {
/* 447 */             MenuElement[] arrayOfMenuElement1 = new MenuElement[2];
/* 448 */             arrayOfMenuElement1[0] = (MenuElement)container;
/* 449 */             arrayOfMenuElement1[1] = jMenu;
/* 450 */             menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 455 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 456 */       if (arrayOfMenuElement.length > 0 && arrayOfMenuElement[arrayOfMenuElement.length - 1] != jMenu
/* 457 */         .getPopupMenu())
/*     */       {
/* 459 */         if (jMenu.isTopLevelMenu() || jMenu
/* 460 */           .getDelay() == 0) {
/* 461 */           BasicMenuUI.appendPath(arrayOfMenuElement, jMenu.getPopupMenu());
/*     */         } else {
/* 463 */           BasicMenuUI.this.setupPostTimer(jMenu);
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 475 */       JMenu jMenu = (JMenu)BasicMenuUI.this.menuItem;
/* 476 */       if (!jMenu.isEnabled()) {
/*     */         return;
/*     */       }
/* 479 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 480 */       menuSelectionManager.processMouseEvent(param1MouseEvent);
/* 481 */       if (!param1MouseEvent.isConsumed()) {
/* 482 */         menuSelectionManager.clearSelectedPath();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 494 */       JMenu jMenu = (JMenu)BasicMenuUI.this.menuItem;
/*     */ 
/*     */       
/* 497 */       if (!jMenu.isEnabled() && !UIManager.getBoolean("MenuItem.disabledAreNavigable")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 502 */       MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/* 503 */       MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/* 504 */       if (!jMenu.isTopLevelMenu()) {
/* 505 */         if (arrayOfMenuElement.length <= 0 || arrayOfMenuElement[arrayOfMenuElement.length - 1] != jMenu
/*     */           
/* 507 */           .getPopupMenu()) {
/* 508 */           if (jMenu.getDelay() == 0) {
/* 509 */             BasicMenuUI.appendPath(BasicMenuUI.this.getPath(), jMenu.getPopupMenu());
/*     */           } else {
/* 511 */             menuSelectionManager.setSelectedPath(BasicMenuUI.this.getPath());
/* 512 */             BasicMenuUI.this.setupPostTimer(jMenu);
/*     */           }
/*     */         
/*     */         }
/* 516 */       } else if (arrayOfMenuElement.length > 0 && arrayOfMenuElement[0] == jMenu
/* 517 */         .getParent()) {
/* 518 */         MenuElement[] arrayOfMenuElement1 = new MenuElement[3];
/*     */ 
/*     */         
/* 521 */         arrayOfMenuElement1[0] = (MenuElement)jMenu.getParent();
/* 522 */         arrayOfMenuElement1[1] = jMenu;
/* 523 */         if (BasicPopupMenuUI.getLastPopup() != null) {
/* 524 */           arrayOfMenuElement1[2] = jMenu.getPopupMenu();
/*     */         }
/* 526 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement1);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 541 */       JMenu jMenu = (JMenu)BasicMenuUI.this.menuItem;
/* 542 */       if (!jMenu.isEnabled())
/*     */         return; 
/* 544 */       MenuSelectionManager.defaultManager().processMouseEvent(param1MouseEvent);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*     */ 
/*     */     
/*     */     public void menuDragMouseEntered(MenuDragMouseEvent param1MenuDragMouseEvent) {}
/*     */ 
/*     */     
/*     */     public void menuDragMouseDragged(MenuDragMouseEvent param1MenuDragMouseEvent) {
/* 555 */       if (!BasicMenuUI.this.menuItem.isEnabled()) {
/*     */         return;
/*     */       }
/* 558 */       MenuSelectionManager menuSelectionManager = param1MenuDragMouseEvent.getMenuSelectionManager();
/* 559 */       MenuElement[] arrayOfMenuElement = param1MenuDragMouseEvent.getPath();
/*     */       
/* 561 */       Point point = param1MenuDragMouseEvent.getPoint();
/* 562 */       if (point.x >= 0 && point.x < BasicMenuUI.this.menuItem.getWidth() && point.y >= 0 && point.y < BasicMenuUI.this.menuItem
/* 563 */         .getHeight()) {
/* 564 */         JMenu jMenu = (JMenu)BasicMenuUI.this.menuItem;
/* 565 */         MenuElement[] arrayOfMenuElement1 = menuSelectionManager.getSelectedPath();
/* 566 */         if (arrayOfMenuElement1.length <= 0 || arrayOfMenuElement1[arrayOfMenuElement1.length - 1] != jMenu
/*     */           
/* 568 */           .getPopupMenu()) {
/* 569 */           if (jMenu.isTopLevelMenu() || jMenu
/* 570 */             .getDelay() == 0 || param1MenuDragMouseEvent
/* 571 */             .getID() == 506) {
/* 572 */             BasicMenuUI.appendPath(arrayOfMenuElement, jMenu.getPopupMenu());
/*     */           } else {
/* 574 */             menuSelectionManager.setSelectedPath(arrayOfMenuElement);
/* 575 */             BasicMenuUI.this.setupPostTimer(jMenu);
/*     */           } 
/*     */         }
/* 578 */       } else if (param1MenuDragMouseEvent.getID() == 502) {
/* 579 */         Component component = menuSelectionManager.componentForPoint(param1MenuDragMouseEvent.getComponent(), param1MenuDragMouseEvent.getPoint());
/* 580 */         if (component == null) {
/* 581 */           menuSelectionManager.clearSelectedPath();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void menuDragMouseExited(MenuDragMouseEvent param1MenuDragMouseEvent) {}
/*     */ 
/*     */     
/*     */     public void menuDragMouseReleased(MenuDragMouseEvent param1MenuDragMouseEvent) {}
/*     */ 
/*     */     
/*     */     public void menuKeyTyped(MenuKeyEvent param1MenuKeyEvent) {
/* 595 */       if (!BasicMenuUI.crossMenuMnemonic && BasicPopupMenuUI.getLastPopup() != null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 601 */       if (BasicPopupMenuUI.getPopups().size() != 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 608 */       char c = Character.toLowerCase((char)BasicMenuUI.this.menuItem.getMnemonic());
/* 609 */       MenuElement[] arrayOfMenuElement = param1MenuKeyEvent.getPath();
/* 610 */       if (c == Character.toLowerCase(param1MenuKeyEvent.getKeyChar())) {
/* 611 */         JPopupMenu jPopupMenu = ((JMenu)BasicMenuUI.this.menuItem).getPopupMenu();
/* 612 */         ArrayList<JPopupMenu> arrayList = new ArrayList(Arrays.asList((Object[])arrayOfMenuElement));
/* 613 */         arrayList.add(jPopupMenu);
/* 614 */         MenuElement[] arrayOfMenuElement1 = jPopupMenu.getSubElements();
/*     */         
/* 616 */         MenuElement menuElement = BasicPopupMenuUI.findEnabledChild(arrayOfMenuElement1, -1, true);
/* 617 */         if (menuElement != null) {
/* 618 */           arrayList.add(menuElement);
/*     */         }
/* 620 */         MenuSelectionManager menuSelectionManager = param1MenuKeyEvent.getMenuSelectionManager();
/* 621 */         MenuElement[] arrayOfMenuElement2 = new MenuElement[0];
/* 622 */         arrayOfMenuElement2 = arrayList.<MenuElement>toArray(arrayOfMenuElement2);
/* 623 */         menuSelectionManager.setSelectedPath(arrayOfMenuElement2);
/* 624 */         param1MenuKeyEvent.consume();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void menuKeyPressed(MenuKeyEvent param1MenuKeyEvent) {}
/*     */     
/*     */     public void menuKeyReleased(MenuKeyEvent param1MenuKeyEvent) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicMenuUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */