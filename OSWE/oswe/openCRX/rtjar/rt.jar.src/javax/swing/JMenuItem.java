/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.event.MenuDragMouseEvent;
/*     */ import javax.swing.event.MenuDragMouseListener;
/*     */ import javax.swing.event.MenuKeyEvent;
/*     */ import javax.swing.event.MenuKeyListener;
/*     */ import javax.swing.plaf.MenuItemUI;
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
/*     */ public class JMenuItem
/*     */   extends AbstractButton
/*     */   implements Accessible, MenuElement
/*     */ {
/*     */   private static final String uiClassID = "MenuItemUI";
/*     */   private static final boolean TRACE = false;
/*     */   private static final boolean VERBOSE = false;
/*     */   private static final boolean DEBUG = false;
/*     */   private boolean isMouseDragged = false;
/*     */   private KeyStroke accelerator;
/*     */   
/*     */   public JMenuItem() {
/* 110 */     this((String)null, (Icon)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuItem(Icon paramIcon) {
/* 119 */     this((String)null, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuItem(String paramString) {
/* 128 */     this(paramString, (Icon)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuItem(Action paramAction) {
/* 139 */     this();
/* 140 */     setAction(paramAction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuItem(String paramString, Icon paramIcon) {
/* 150 */     setModel(new DefaultButtonModel());
/* 151 */     init(paramString, paramIcon);
/* 152 */     initFocusability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuItem(String paramString, int paramInt) {
/* 163 */     setModel(new DefaultButtonModel());
/* 164 */     init(paramString, (Icon)null);
/* 165 */     setMnemonic(paramInt);
/* 166 */     initFocusability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(ButtonModel paramButtonModel) {
/* 173 */     super.setModel(paramButtonModel);
/* 174 */     if (paramButtonModel instanceof DefaultButtonModel) {
/* 175 */       ((DefaultButtonModel)paramButtonModel).setMenuItem(true);
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
/*     */   void initFocusability() {
/* 188 */     setFocusable(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(String paramString, Icon paramIcon) {
/* 198 */     if (paramString != null) {
/* 199 */       setText(paramString);
/*     */     }
/*     */     
/* 202 */     if (paramIcon != null) {
/* 203 */       setIcon(paramIcon);
/*     */     }
/*     */ 
/*     */     
/* 207 */     addFocusListener(new MenuItemFocusListener());
/* 208 */     setUIProperty("borderPainted", Boolean.FALSE);
/* 209 */     setFocusPainted(false);
/* 210 */     setHorizontalTextPosition(11);
/* 211 */     setHorizontalAlignment(10);
/* 212 */     updateUI();
/*     */   }
/*     */   
/*     */   private static class MenuItemFocusListener implements FocusListener, Serializable {
/*     */     private MenuItemFocusListener() {}
/*     */     
/*     */     public void focusGained(FocusEvent param1FocusEvent) {}
/*     */     
/*     */     public void focusLost(FocusEvent param1FocusEvent) {
/* 221 */       JMenuItem jMenuItem = (JMenuItem)param1FocusEvent.getSource();
/* 222 */       if (jMenuItem.isFocusPainted()) {
/* 223 */         jMenuItem.repaint();
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
/*     */   public void setUI(MenuItemUI paramMenuItemUI) {
/* 241 */     setUI(paramMenuItemUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 250 */     setUI((MenuItemUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 263 */     return "MenuItemUI";
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
/*     */   public void setArmed(boolean paramBoolean) {
/* 279 */     ButtonModel buttonModel = getModel();
/*     */     
/* 281 */     boolean bool = buttonModel.isArmed();
/* 282 */     if (buttonModel.isArmed() != paramBoolean) {
/* 283 */       buttonModel.setArmed(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmed() {
/* 294 */     ButtonModel buttonModel = getModel();
/* 295 */     return buttonModel.isArmed();
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
/*     */   public void setEnabled(boolean paramBoolean) {
/* 309 */     if (!paramBoolean && !UIManager.getBoolean("MenuItem.disabledAreNavigable")) {
/* 310 */       setArmed(false);
/*     */     }
/* 312 */     super.setEnabled(paramBoolean);
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
/*     */   boolean alwaysOnTop() {
/* 325 */     if (SwingUtilities.getAncestorOfClass(JInternalFrame.class, this) != null)
/*     */     {
/* 327 */       return false;
/*     */     }
/* 329 */     return true;
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
/*     */   
/*     */   public void setAccelerator(KeyStroke paramKeyStroke) {
/* 354 */     KeyStroke keyStroke = this.accelerator;
/* 355 */     this.accelerator = paramKeyStroke;
/* 356 */     repaint();
/* 357 */     revalidate();
/* 358 */     firePropertyChange("accelerator", keyStroke, this.accelerator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyStroke getAccelerator() {
/* 368 */     return this.accelerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configurePropertiesFromAction(Action paramAction) {
/* 377 */     super.configurePropertiesFromAction(paramAction);
/* 378 */     configureAcceleratorFromAction(paramAction);
/*     */   }
/*     */   
/*     */   void setIconFromAction(Action paramAction) {
/* 382 */     Icon icon = null;
/* 383 */     if (paramAction != null) {
/* 384 */       icon = (Icon)paramAction.getValue("SmallIcon");
/*     */     }
/* 386 */     setIcon(icon);
/*     */   }
/*     */ 
/*     */   
/*     */   void largeIconChanged(Action paramAction) {}
/*     */   
/*     */   void smallIconChanged(Action paramAction) {
/* 393 */     setIconFromAction(paramAction);
/*     */   }
/*     */ 
/*     */   
/*     */   void configureAcceleratorFromAction(Action paramAction) {
/* 398 */     KeyStroke keyStroke = (paramAction == null) ? null : (KeyStroke)paramAction.getValue("AcceleratorKey");
/* 399 */     setAccelerator(keyStroke);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPropertyChanged(Action paramAction, String paramString) {
/* 407 */     if (paramString == "AcceleratorKey") {
/* 408 */       configureAcceleratorFromAction(paramAction);
/*     */     } else {
/*     */       
/* 411 */       super.actionPropertyChanged(paramAction, paramString);
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
/*     */   public void processMouseEvent(MouseEvent paramMouseEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {
/* 429 */     processMenuDragMouseEvent(new MenuDragMouseEvent(paramMouseEvent
/* 430 */           .getComponent(), paramMouseEvent.getID(), paramMouseEvent
/* 431 */           .getWhen(), paramMouseEvent
/* 432 */           .getModifiers(), paramMouseEvent.getX(), paramMouseEvent.getY(), paramMouseEvent
/* 433 */           .getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent
/* 434 */           .getClickCount(), paramMouseEvent.isPopupTrigger(), paramArrayOfMenuElement, paramMenuSelectionManager));
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
/*     */   public void processKeyEvent(KeyEvent paramKeyEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {
/* 458 */     MenuKeyEvent menuKeyEvent = new MenuKeyEvent(paramKeyEvent.getComponent(), paramKeyEvent.getID(), paramKeyEvent.getWhen(), paramKeyEvent.getModifiers(), paramKeyEvent.getKeyCode(), paramKeyEvent.getKeyChar(), paramArrayOfMenuElement, paramMenuSelectionManager);
/*     */     
/* 460 */     processMenuKeyEvent(menuKeyEvent);
/*     */     
/* 462 */     if (menuKeyEvent.isConsumed()) {
/* 463 */       paramKeyEvent.consume();
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
/*     */   public void processMenuDragMouseEvent(MenuDragMouseEvent paramMenuDragMouseEvent) {
/* 475 */     switch (paramMenuDragMouseEvent.getID()) {
/*     */       case 504:
/* 477 */         this.isMouseDragged = false; fireMenuDragMouseEntered(paramMenuDragMouseEvent); break;
/*     */       case 505:
/* 479 */         this.isMouseDragged = false; fireMenuDragMouseExited(paramMenuDragMouseEvent); break;
/*     */       case 506:
/* 481 */         this.isMouseDragged = true; fireMenuDragMouseDragged(paramMenuDragMouseEvent); break;
/*     */       case 502:
/* 483 */         if (this.isMouseDragged) fireMenuDragMouseReleased(paramMenuDragMouseEvent);
/*     */         
/*     */         break;
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
/*     */   public void processMenuKeyEvent(MenuKeyEvent paramMenuKeyEvent) {
/* 499 */     switch (paramMenuKeyEvent.getID()) {
/*     */       case 401:
/* 501 */         fireMenuKeyPressed(paramMenuKeyEvent); break;
/*     */       case 402:
/* 503 */         fireMenuKeyReleased(paramMenuKeyEvent); break;
/*     */       case 400:
/* 505 */         fireMenuKeyTyped(paramMenuKeyEvent);
/*     */         break;
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
/*     */   protected void fireMenuDragMouseEntered(MenuDragMouseEvent paramMenuDragMouseEvent) {
/* 520 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 523 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 524 */       if (arrayOfObject[i] == MenuDragMouseListener.class)
/*     */       {
/* 526 */         ((MenuDragMouseListener)arrayOfObject[i + 1]).menuDragMouseEntered(paramMenuDragMouseEvent);
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
/*     */   protected void fireMenuDragMouseExited(MenuDragMouseEvent paramMenuDragMouseEvent) {
/* 540 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 543 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 544 */       if (arrayOfObject[i] == MenuDragMouseListener.class)
/*     */       {
/* 546 */         ((MenuDragMouseListener)arrayOfObject[i + 1]).menuDragMouseExited(paramMenuDragMouseEvent);
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
/*     */   protected void fireMenuDragMouseDragged(MenuDragMouseEvent paramMenuDragMouseEvent) {
/* 560 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 563 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 564 */       if (arrayOfObject[i] == MenuDragMouseListener.class)
/*     */       {
/* 566 */         ((MenuDragMouseListener)arrayOfObject[i + 1]).menuDragMouseDragged(paramMenuDragMouseEvent);
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
/*     */   protected void fireMenuDragMouseReleased(MenuDragMouseEvent paramMenuDragMouseEvent) {
/* 580 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 583 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 584 */       if (arrayOfObject[i] == MenuDragMouseListener.class)
/*     */       {
/* 586 */         ((MenuDragMouseListener)arrayOfObject[i + 1]).menuDragMouseReleased(paramMenuDragMouseEvent);
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
/*     */   protected void fireMenuKeyPressed(MenuKeyEvent paramMenuKeyEvent) {
/* 604 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 607 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 608 */       if (arrayOfObject[i] == MenuKeyListener.class)
/*     */       {
/* 610 */         ((MenuKeyListener)arrayOfObject[i + 1]).menuKeyPressed(paramMenuKeyEvent);
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
/*     */   protected void fireMenuKeyReleased(MenuKeyEvent paramMenuKeyEvent) {
/* 628 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 631 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 632 */       if (arrayOfObject[i] == MenuKeyListener.class)
/*     */       {
/* 634 */         ((MenuKeyListener)arrayOfObject[i + 1]).menuKeyReleased(paramMenuKeyEvent);
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
/*     */   protected void fireMenuKeyTyped(MenuKeyEvent paramMenuKeyEvent) {
/* 652 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*     */ 
/*     */     
/* 655 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 656 */       if (arrayOfObject[i] == MenuKeyListener.class)
/*     */       {
/* 658 */         ((MenuKeyListener)arrayOfObject[i + 1]).menuKeyTyped(paramMenuKeyEvent);
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
/*     */   public void menuSelectionChanged(boolean paramBoolean) {
/* 674 */     setArmed(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuElement[] getSubElements() {
/* 684 */     return new MenuElement[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 695 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMenuDragMouseListener(MenuDragMouseListener paramMenuDragMouseListener) {
/* 704 */     this.listenerList.add(MenuDragMouseListener.class, paramMenuDragMouseListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMenuDragMouseListener(MenuDragMouseListener paramMenuDragMouseListener) {
/* 713 */     this.listenerList.remove(MenuDragMouseListener.class, paramMenuDragMouseListener);
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
/*     */   public MenuDragMouseListener[] getMenuDragMouseListeners() {
/* 725 */     return this.listenerList.<MenuDragMouseListener>getListeners(MenuDragMouseListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMenuKeyListener(MenuKeyListener paramMenuKeyListener) {
/* 734 */     this.listenerList.add(MenuKeyListener.class, paramMenuKeyListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeMenuKeyListener(MenuKeyListener paramMenuKeyListener) {
/* 743 */     this.listenerList.remove(MenuKeyListener.class, paramMenuKeyListener);
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
/*     */   public MenuKeyListener[] getMenuKeyListeners() {
/* 755 */     return this.listenerList.<MenuKeyListener>getListeners(MenuKeyListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 765 */     paramObjectInputStream.defaultReadObject();
/* 766 */     if (getUIClassID().equals("MenuItemUI")) {
/* 767 */       updateUI();
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 772 */     paramObjectOutputStream.defaultWriteObject();
/* 773 */     if (getUIClassID().equals("MenuItemUI")) {
/* 774 */       byte b = JComponent.getWriteObjCounter(this);
/* 775 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 776 */       if (b == 0 && this.ui != null) {
/* 777 */         this.ui.installUI(this);
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
/*     */   protected String paramString() {
/* 793 */     return super.paramString();
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 811 */     if (this.accessibleContext == null) {
/* 812 */       this.accessibleContext = new AccessibleJMenuItem();
/*     */     }
/* 814 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleJMenuItem
/*     */     extends AbstractButton.AccessibleAbstractButton
/*     */     implements ChangeListener
/*     */   {
/*     */     private boolean isArmed = false;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hasFocus = false;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isPressed = false;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isSelected = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AccessibleJMenuItem() {
/* 843 */       JMenuItem.this.addChangeListener(this);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 853 */       return AccessibleRole.MENU_ITEM;
/*     */     }
/*     */ 
/*     */     
/*     */     private void fireAccessibilityFocusedEvent(JMenuItem param1JMenuItem) {
/* 858 */       MenuElement[] arrayOfMenuElement = MenuSelectionManager.defaultManager().getSelectedPath();
/* 859 */       if (arrayOfMenuElement.length > 0) {
/* 860 */         MenuElement menuElement = arrayOfMenuElement[arrayOfMenuElement.length - 1];
/* 861 */         if (param1JMenuItem == menuElement) {
/* 862 */           firePropertyChange("AccessibleState", null, AccessibleState.FOCUSED);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 873 */       firePropertyChange("AccessibleVisibleData", 
/* 874 */           Boolean.valueOf(false), Boolean.valueOf(true));
/* 875 */       if (JMenuItem.this.getModel().isArmed()) {
/* 876 */         if (!this.isArmed) {
/* 877 */           this.isArmed = true;
/* 878 */           firePropertyChange("AccessibleState", null, AccessibleState.ARMED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 884 */           fireAccessibilityFocusedEvent(JMenuItem.this);
/*     */         }
/*     */       
/* 887 */       } else if (this.isArmed) {
/* 888 */         this.isArmed = false;
/* 889 */         firePropertyChange("AccessibleState", AccessibleState.ARMED, null);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 894 */       if (JMenuItem.this.isFocusOwner()) {
/* 895 */         if (!this.hasFocus) {
/* 896 */           this.hasFocus = true;
/* 897 */           firePropertyChange("AccessibleState", null, AccessibleState.FOCUSED);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 902 */       else if (this.hasFocus) {
/* 903 */         this.hasFocus = false;
/* 904 */         firePropertyChange("AccessibleState", AccessibleState.FOCUSED, null);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 909 */       if (JMenuItem.this.getModel().isPressed()) {
/* 910 */         if (!this.isPressed) {
/* 911 */           this.isPressed = true;
/* 912 */           firePropertyChange("AccessibleState", null, AccessibleState.PRESSED);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 917 */       else if (this.isPressed) {
/* 918 */         this.isPressed = false;
/* 919 */         firePropertyChange("AccessibleState", AccessibleState.PRESSED, null);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 924 */       if (JMenuItem.this.getModel().isSelected()) {
/* 925 */         if (!this.isSelected) {
/* 926 */           this.isSelected = true;
/* 927 */           firePropertyChange("AccessibleState", null, AccessibleState.CHECKED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 934 */           fireAccessibilityFocusedEvent(JMenuItem.this);
/*     */         }
/*     */       
/* 937 */       } else if (this.isSelected) {
/* 938 */         this.isSelected = false;
/* 939 */         firePropertyChange("AccessibleState", AccessibleState.CHECKED, null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JMenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */