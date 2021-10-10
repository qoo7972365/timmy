/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.peer.MenuItemPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleAction;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleValue;
/*     */ import sun.awt.AWTAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuItem
/*     */   extends MenuComponent
/*     */   implements Accessible
/*     */ {
/*     */   static {
/*  75 */     Toolkit.loadLibraries();
/*  76 */     if (!GraphicsEnvironment.isHeadless()) {
/*  77 */       initIDs();
/*     */     }
/*     */     
/*  80 */     AWTAccessor.setMenuItemAccessor(new AWTAccessor.MenuItemAccessor()
/*     */         {
/*     */           public boolean isEnabled(MenuItem param1MenuItem) {
/*  83 */             return param1MenuItem.enabled;
/*     */           }
/*     */           
/*     */           public String getLabel(MenuItem param1MenuItem) {
/*  87 */             return param1MenuItem.label;
/*     */           }
/*     */           
/*     */           public MenuShortcut getShortcut(MenuItem param1MenuItem) {
/*  91 */             return param1MenuItem.shortcut;
/*     */           }
/*     */           
/*     */           public String getActionCommandImpl(MenuItem param1MenuItem) {
/*  95 */             return param1MenuItem.getActionCommandImpl();
/*     */           }
/*     */           
/*     */           public boolean isItemEnabled(MenuItem param1MenuItem) {
/*  99 */             return param1MenuItem.isItemEnabled();
/*     */           }
/*     */         });
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
/*     */   boolean enabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String label;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String actionCommand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long eventMask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient ActionListener actionListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   private MenuShortcut shortcut = null;
/*     */   
/*     */   private static final String base = "menuitem";
/* 166 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -21757335363267194L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int menuItemSerializedDataVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem() throws HeadlessException {
/* 182 */     this("", (MenuShortcut)null);
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
/*     */   public MenuItem(String paramString) throws HeadlessException {
/* 198 */     this(paramString, (MenuShortcut)null);
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
/*     */   
/*     */   String constructComponentName() {
/* 224 */     synchronized (MenuItem.class) {
/* 225 */       return "menuitem" + nameCounter++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 234 */     synchronized (getTreeLock()) {
/* 235 */       if (this.peer == null) {
/* 236 */         this.peer = Toolkit.getDefaultToolkit().createMenuItem(this);
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
/*     */   public String getLabel() {
/* 248 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setLabel(String paramString) {
/* 258 */     this.label = paramString;
/* 259 */     MenuItemPeer menuItemPeer = (MenuItemPeer)this.peer;
/* 260 */     if (menuItemPeer != null) {
/* 261 */       menuItemPeer.setLabel(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 271 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setEnabled(boolean paramBoolean) {
/* 282 */     enable(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public synchronized void enable() {
/* 291 */     this.enabled = true;
/* 292 */     MenuItemPeer menuItemPeer = (MenuItemPeer)this.peer;
/* 293 */     if (menuItemPeer != null) {
/* 294 */       menuItemPeer.setEnabled(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void enable(boolean paramBoolean) {
/* 304 */     if (paramBoolean) {
/* 305 */       enable();
/*     */     } else {
/* 307 */       disable();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public synchronized void disable() {
/* 317 */     this.enabled = false;
/* 318 */     MenuItemPeer menuItemPeer = (MenuItemPeer)this.peer;
/* 319 */     if (menuItemPeer != null) {
/* 320 */       menuItemPeer.setEnabled(false);
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
/*     */   public MenuShortcut getShortcut() {
/* 333 */     return this.shortcut;
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
/*     */   public void setShortcut(MenuShortcut paramMenuShortcut) {
/* 346 */     this.shortcut = paramMenuShortcut;
/* 347 */     MenuItemPeer menuItemPeer = (MenuItemPeer)this.peer;
/* 348 */     if (menuItemPeer != null) {
/* 349 */       menuItemPeer.setLabel(this.label);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteShortcut() {
/* 359 */     this.shortcut = null;
/* 360 */     MenuItemPeer menuItemPeer = (MenuItemPeer)this.peer;
/* 361 */     if (menuItemPeer != null) {
/* 362 */       menuItemPeer.setLabel(this.label);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void deleteShortcut(MenuShortcut paramMenuShortcut) {
/* 371 */     if (paramMenuShortcut.equals(this.shortcut)) {
/* 372 */       this.shortcut = null;
/* 373 */       MenuItemPeer menuItemPeer = (MenuItemPeer)this.peer;
/* 374 */       if (menuItemPeer != null) {
/* 375 */         menuItemPeer.setLabel(this.label);
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
/*     */   void doMenuEvent(long paramLong, int paramInt) {
/* 387 */     Toolkit.getEventQueue().postEvent(new ActionEvent(this, 1001, 
/*     */           
/* 389 */           getActionCommand(), paramLong, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isItemEnabled() {
/* 399 */     if (!isEnabled()) {
/* 400 */       return false;
/*     */     }
/* 402 */     MenuContainer menuContainer = getParent_NoClientCode();
/*     */     while (true) {
/* 404 */       if (!(menuContainer instanceof Menu)) {
/* 405 */         return true;
/*     */       }
/* 407 */       Menu menu = (Menu)menuContainer;
/* 408 */       if (!menu.isEnabled()) {
/* 409 */         return false;
/*     */       }
/* 411 */       menuContainer = menu.getParent_NoClientCode();
/* 412 */       if (menuContainer == null) {
/* 413 */         return true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean handleShortcut(KeyEvent paramKeyEvent) {
/* 423 */     MenuShortcut menuShortcut1 = new MenuShortcut(paramKeyEvent.getKeyCode(), ((paramKeyEvent.getModifiers() & 0x1) > 0));
/*     */     
/* 425 */     MenuShortcut menuShortcut2 = new MenuShortcut(paramKeyEvent.getExtendedKeyCode(), ((paramKeyEvent.getModifiers() & 0x1) > 0));
/*     */ 
/*     */     
/* 428 */     if ((menuShortcut1.equals(this.shortcut) || menuShortcut2.equals(this.shortcut)) && isItemEnabled()) {
/*     */       
/* 430 */       if (paramKeyEvent.getID() == 401) {
/* 431 */         doMenuEvent(paramKeyEvent.getWhen(), paramKeyEvent.getModifiers());
/*     */       }
/*     */ 
/*     */       
/* 435 */       return true;
/*     */     } 
/* 437 */     return false;
/*     */   }
/*     */   
/*     */   MenuItem getShortcutMenuItem(MenuShortcut paramMenuShortcut) {
/* 441 */     return paramMenuShortcut.equals(this.shortcut) ? this : null;
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
/*     */   protected final void enableEvents(long paramLong) {
/* 461 */     this.eventMask |= paramLong;
/* 462 */     this.newEventsOnly = true;
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
/*     */   protected final void disableEvents(long paramLong) {
/* 476 */     this.eventMask &= paramLong ^ 0xFFFFFFFFFFFFFFFFL;
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
/*     */   public void setActionCommand(String paramString) {
/* 491 */     this.actionCommand = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getActionCommand() {
/* 501 */     return getActionCommandImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   final String getActionCommandImpl() {
/* 506 */     return (this.actionCommand == null) ? this.label : this.actionCommand;
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
/*     */   public synchronized void addActionListener(ActionListener paramActionListener) {
/* 524 */     if (paramActionListener == null) {
/*     */       return;
/*     */     }
/* 527 */     this.actionListener = AWTEventMulticaster.add(this.actionListener, paramActionListener);
/* 528 */     this.newEventsOnly = true;
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
/*     */   public synchronized void removeActionListener(ActionListener paramActionListener) {
/* 546 */     if (paramActionListener == null) {
/*     */       return;
/*     */     }
/* 549 */     this.actionListener = AWTEventMulticaster.remove(this.actionListener, paramActionListener);
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
/*     */   public synchronized ActionListener[] getActionListeners() {
/* 567 */     return getListeners(ActionListener.class);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/* 604 */     ActionListener actionListener = null;
/* 605 */     if (paramClass == ActionListener.class) {
/* 606 */       actionListener = this.actionListener;
/*     */     }
/* 608 */     return AWTEventMulticaster.getListeners(actionListener, paramClass);
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
/*     */   protected void processEvent(AWTEvent paramAWTEvent) {
/* 627 */     if (paramAWTEvent instanceof ActionEvent) {
/* 628 */       processActionEvent((ActionEvent)paramAWTEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean eventEnabled(AWTEvent paramAWTEvent) {
/* 634 */     if (paramAWTEvent.id == 1001) {
/* 635 */       if ((this.eventMask & 0x80L) != 0L || this.actionListener != null)
/*     */       {
/* 637 */         return true;
/*     */       }
/* 639 */       return false;
/*     */     } 
/* 641 */     return super.eventEnabled(paramAWTEvent);
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
/*     */   
/*     */   protected void processActionEvent(ActionEvent paramActionEvent) {
/* 667 */     ActionListener actionListener = this.actionListener;
/* 668 */     if (actionListener != null) {
/* 669 */       actionListener.actionPerformed(paramActionEvent);
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
/*     */   public String paramString() {
/* 683 */     String str = ",label=" + this.label;
/* 684 */     if (this.shortcut != null) {
/* 685 */       str = str + ",shortcut=" + this.shortcut;
/*     */     }
/* 687 */     return super.paramString() + str;
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
/*     */   public MenuItem(String paramString, MenuShortcut paramMenuShortcut) throws HeadlessException {
/* 699 */     this.menuItemSerializedDataVersion = 1;
/*     */     this.label = paramString;
/*     */     this.shortcut = paramMenuShortcut;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 721 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 723 */     AWTEventMulticaster.save(paramObjectOutputStream, "actionL", this.actionListener);
/* 724 */     paramObjectOutputStream.writeObject(null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/* 745 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     Object object;
/* 748 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 749 */       String str = ((String)object).intern();
/*     */       
/* 751 */       if ("actionL" == str) {
/* 752 */         addActionListener((ActionListener)paramObjectInputStream.readObject());
/*     */         continue;
/*     */       } 
/* 755 */       paramObjectInputStream.readObject();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public AccessibleContext getAccessibleContext() {
/* 780 */     if (this.accessibleContext == null) {
/* 781 */       this.accessibleContext = new AccessibleAWTMenuItem();
/*     */     }
/* 783 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleAWTMenuItem
/*     */     extends MenuComponent.AccessibleAWTMenuComponent
/*     */     implements AccessibleAction, AccessibleValue
/*     */   {
/*     */     private static final long serialVersionUID = -217847831945965825L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAccessibleName() {
/* 812 */       if (this.accessibleName != null) {
/* 813 */         return this.accessibleName;
/*     */       }
/* 815 */       if (MenuItem.this.getLabel() == null) {
/* 816 */         return super.getAccessibleName();
/*     */       }
/* 818 */       return MenuItem.this.getLabel();
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
/*     */     public AccessibleRole getAccessibleRole() {
/* 830 */       return AccessibleRole.MENU_ITEM;
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
/*     */     public AccessibleAction getAccessibleAction() {
/* 842 */       return this;
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
/*     */     public AccessibleValue getAccessibleValue() {
/* 854 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleActionCount() {
/* 864 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAccessibleActionDescription(int param1Int) {
/* 873 */       if (param1Int == 0)
/*     */       {
/* 875 */         return "click";
/*     */       }
/* 877 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean doAccessibleAction(int param1Int) {
/* 888 */       if (param1Int == 0) {
/*     */         
/* 890 */         Toolkit.getEventQueue().postEvent(new ActionEvent(MenuItem.this, 1001, MenuItem.this
/*     */ 
/*     */               
/* 893 */               .getActionCommand(), 
/* 894 */               EventQueue.getMostRecentEventTime(), 0));
/*     */         
/* 896 */         return true;
/*     */       } 
/* 898 */       return false;
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
/*     */     public Number getCurrentAccessibleValue() {
/* 910 */       return Integer.valueOf(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 919 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMinimumAccessibleValue() {
/* 928 */       return Integer.valueOf(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMaximumAccessibleValue() {
/* 937 */       return Integer.valueOf(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MenuItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */