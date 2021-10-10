/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.peer.MenuBarPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
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
/*     */ 
/*     */ public class MenuBar
/*     */   extends MenuComponent
/*     */   implements MenuContainer, Accessible
/*     */ {
/*     */   static {
/*  74 */     Toolkit.loadLibraries();
/*  75 */     if (!GraphicsEnvironment.isHeadless()) {
/*  76 */       initIDs();
/*     */     }
/*  78 */     AWTAccessor.setMenuBarAccessor(new AWTAccessor.MenuBarAccessor()
/*     */         {
/*     */           public Menu getHelpMenu(MenuBar param1MenuBar) {
/*  81 */             return param1MenuBar.helpMenu;
/*     */           }
/*     */           
/*     */           public Vector<Menu> getMenus(MenuBar param1MenuBar) {
/*  85 */             return param1MenuBar.menus;
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
/*  97 */   Vector<Menu> menus = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Menu helpMenu;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String base = "menubar";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -4930327919388951260L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int menuBarSerializedDataVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String constructComponentName() {
/* 133 */     synchronized (MenuBar.class) {
/* 134 */       return "menubar" + nameCounter++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 144 */     synchronized (getTreeLock()) {
/* 145 */       if (this.peer == null) {
/* 146 */         this.peer = Toolkit.getDefaultToolkit().createMenuBar(this);
/*     */       }
/* 148 */       int i = getMenuCount();
/* 149 */       for (byte b = 0; b < i; b++) {
/* 150 */         getMenu(b).addNotify();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 161 */     synchronized (getTreeLock()) {
/* 162 */       int i = getMenuCount();
/* 163 */       for (byte b = 0; b < i; b++) {
/* 164 */         getMenu(b).removeNotify();
/*     */       }
/* 166 */       super.removeNotify();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu getHelpMenu() {
/* 175 */     return this.helpMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHelpMenu(Menu paramMenu) {
/* 185 */     synchronized (getTreeLock()) {
/* 186 */       if (this.helpMenu == paramMenu) {
/*     */         return;
/*     */       }
/* 189 */       if (this.helpMenu != null) {
/* 190 */         remove(this.helpMenu);
/*     */       }
/* 192 */       this.helpMenu = paramMenu;
/* 193 */       if (paramMenu != null) {
/* 194 */         if (paramMenu.parent != this) {
/* 195 */           add(paramMenu);
/*     */         }
/* 197 */         paramMenu.isHelpMenu = true;
/* 198 */         paramMenu.parent = this;
/* 199 */         MenuBarPeer menuBarPeer = (MenuBarPeer)this.peer;
/* 200 */         if (menuBarPeer != null) {
/* 201 */           if (paramMenu.peer == null) {
/* 202 */             paramMenu.addNotify();
/*     */           }
/* 204 */           menuBarPeer.addHelpMenu(paramMenu);
/*     */         } 
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
/*     */   public Menu add(Menu paramMenu) {
/* 221 */     synchronized (getTreeLock()) {
/* 222 */       if (paramMenu.parent != null) {
/* 223 */         paramMenu.parent.remove(paramMenu);
/*     */       }
/* 225 */       paramMenu.parent = this;
/*     */       
/* 227 */       MenuBarPeer menuBarPeer = (MenuBarPeer)this.peer;
/* 228 */       if (menuBarPeer != null) {
/* 229 */         if (paramMenu.peer == null) {
/* 230 */           paramMenu.addNotify();
/*     */         }
/* 232 */         this.menus.addElement(paramMenu);
/* 233 */         menuBarPeer.addMenu(paramMenu);
/*     */       } else {
/* 235 */         this.menus.addElement(paramMenu);
/*     */       } 
/* 237 */       return paramMenu;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int paramInt) {
/* 248 */     synchronized (getTreeLock()) {
/* 249 */       Menu menu = getMenu(paramInt);
/* 250 */       this.menus.removeElementAt(paramInt);
/* 251 */       MenuBarPeer menuBarPeer = (MenuBarPeer)this.peer;
/* 252 */       if (menuBarPeer != null) {
/* 253 */         menuBarPeer.delMenu(paramInt);
/* 254 */         menu.removeNotify();
/* 255 */         menu.parent = null;
/*     */       } 
/* 257 */       if (this.helpMenu == menu) {
/* 258 */         this.helpMenu = null;
/* 259 */         menu.isHelpMenu = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(MenuComponent paramMenuComponent) {
/* 270 */     synchronized (getTreeLock()) {
/* 271 */       int i = this.menus.indexOf(paramMenuComponent);
/* 272 */       if (i >= 0) {
/* 273 */         remove(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMenuCount() {
/* 284 */     return countMenus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int countMenus() {
/* 293 */     return getMenuCountImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int getMenuCountImpl() {
/* 301 */     return this.menus.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu getMenu(int paramInt) {
/* 310 */     return getMenuImpl(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Menu getMenuImpl(int paramInt) {
/* 318 */     return this.menus.elementAt(paramInt);
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
/*     */   public synchronized Enumeration<MenuShortcut> shortcuts() {
/* 330 */     Vector<MenuShortcut> vector = new Vector();
/* 331 */     int i = getMenuCount();
/* 332 */     for (byte b = 0; b < i; b++) {
/* 333 */       Enumeration<MenuShortcut> enumeration = getMenu(b).shortcuts();
/* 334 */       while (enumeration.hasMoreElements()) {
/* 335 */         vector.addElement(enumeration.nextElement());
/*     */       }
/*     */     } 
/* 338 */     return vector.elements();
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
/*     */   public MenuItem getShortcutMenuItem(MenuShortcut paramMenuShortcut) {
/* 353 */     int i = getMenuCount();
/* 354 */     for (byte b = 0; b < i; b++) {
/* 355 */       MenuItem menuItem = getMenu(b).getShortcutMenuItem(paramMenuShortcut);
/* 356 */       if (menuItem != null) {
/* 357 */         return menuItem;
/*     */       }
/*     */     } 
/* 360 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean handleShortcut(KeyEvent paramKeyEvent) {
/* 371 */     int i = paramKeyEvent.getID();
/* 372 */     if (i != 401 && i != 402) {
/* 373 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 377 */     int j = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
/* 378 */     if ((paramKeyEvent.getModifiers() & j) == 0) {
/* 379 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 383 */     int k = getMenuCount();
/* 384 */     for (byte b = 0; b < k; b++) {
/* 385 */       Menu menu = getMenu(b);
/* 386 */       if (menu.handleShortcut(paramKeyEvent)) {
/* 387 */         return true;
/*     */       }
/*     */     } 
/* 390 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteShortcut(MenuShortcut paramMenuShortcut) {
/* 399 */     int i = getMenuCount();
/* 400 */     for (byte b = 0; b < i; b++) {
/* 401 */       getMenu(b).deleteShortcut(paramMenuShortcut);
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
/*     */   public MenuBar() throws HeadlessException {
/* 414 */     this.menuBarSerializedDataVersion = 1;
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws ClassNotFoundException, IOException {
/* 427 */     paramObjectOutputStream.defaultWriteObject();
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/* 445 */     paramObjectInputStream.defaultReadObject();
/* 446 */     for (byte b = 0; b < this.menus.size(); b++) {
/* 447 */       Menu menu = this.menus.elementAt(b);
/* 448 */       menu.parent = this;
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
/* 473 */     if (this.accessibleContext == null) {
/* 474 */       this.accessibleContext = new AccessibleAWTMenuBar();
/*     */     }
/* 476 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getAccessibleChildIndex(MenuComponent paramMenuComponent) {
/* 483 */     return this.menus.indexOf(paramMenuComponent);
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
/*     */   protected class AccessibleAWTMenuBar
/*     */     extends MenuComponent.AccessibleAWTMenuComponent
/*     */   {
/*     */     private static final long serialVersionUID = -8577604491830083815L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 512 */       return AccessibleRole.MENU_BAR;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/MenuBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */