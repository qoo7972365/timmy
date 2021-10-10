/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.peer.MenuPeer;
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
/*     */ public class Menu
/*     */   extends MenuItem
/*     */   implements MenuContainer, Accessible
/*     */ {
/*     */   static {
/*  62 */     Toolkit.loadLibraries();
/*  63 */     if (!GraphicsEnvironment.isHeadless()) {
/*  64 */       initIDs();
/*     */     }
/*     */     
/*  67 */     AWTAccessor.setMenuAccessor(new AWTAccessor.MenuAccessor()
/*     */         {
/*     */           public Vector<MenuComponent> getItems(Menu param1Menu) {
/*  70 */             return param1Menu.items;
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
/*  81 */   Vector<MenuComponent> items = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean tearOff;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isHelpMenu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String base = "menu";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -8809584163345499784L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int menuSerializedDataVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Menu() throws HeadlessException {
/* 124 */     this("", false);
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
/*     */   public Menu(String paramString) throws HeadlessException {
/* 137 */     this(paramString, false);
/*     */   } String constructComponentName() { synchronized (Menu.class) {
/*     */       return "menu" + nameCounter++;
/*     */     }  } public void addNotify() { synchronized (getTreeLock()) {
/*     */       if (this.peer == null)
/*     */         this.peer = Toolkit.getDefaultToolkit().createMenu(this);  int i = getItemCount(); for (byte b = 0; b < i; b++) {
/*     */         MenuItem menuItem = getItem(b);
/*     */         menuItem.parent = this;
/*     */         menuItem.addNotify();
/*     */       } 
/*     */     }  }
/*     */   public void removeNotify() { synchronized (getTreeLock()) {
/*     */       int i = getItemCount();
/*     */       for (byte b = 0; b < i; b++)
/*     */         getItem(b).removeNotify(); 
/*     */       super.removeNotify();
/*     */     }  }
/*     */   public boolean isTearOff() { return this.tearOff; }
/*     */   public int getItemCount() { return countItems(); }
/*     */   @Deprecated public int countItems() { return countItemsImpl(); }
/* 157 */   public Menu(String paramString, boolean paramBoolean) throws HeadlessException { super(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     this.menuSerializedDataVersion = 1; this.tearOff = paramBoolean; } final int countItemsImpl() { return this.items.size(); }
/*     */   public MenuItem getItem(int paramInt) { return getItemImpl(paramInt); }
/*     */   final MenuItem getItemImpl(int paramInt) { return (MenuItem)this.items.elementAt(paramInt); }
/*     */   public MenuItem add(MenuItem paramMenuItem) { synchronized (getTreeLock()) {
/*     */       if (paramMenuItem.parent != null)
/*     */         paramMenuItem.parent.remove(paramMenuItem);  this.items.addElement(paramMenuItem); paramMenuItem.parent = this; MenuPeer menuPeer = (MenuPeer)this.peer; if (menuPeer != null) {
/*     */         paramMenuItem.addNotify();
/*     */         menuPeer.addItem(paramMenuItem);
/*     */       } 
/*     */       return paramMenuItem;
/*     */     }  }
/*     */   public void add(String paramString) { add(new MenuItem(paramString)); }
/* 527 */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException { paramObjectOutputStream.defaultWriteObject(); }
/*     */   public void insert(MenuItem paramMenuItem, int paramInt) {
/*     */     synchronized (getTreeLock()) {
/*     */       if (paramInt < 0)
/*     */         throw new IllegalArgumentException("index less than zero."); 
/*     */       int i = getItemCount();
/*     */       Vector<MenuItem> vector = new Vector();
/*     */       int j;
/*     */       for (j = paramInt; j < i; j++) {
/*     */         vector.addElement(getItem(paramInt));
/*     */         remove(paramInt);
/*     */       } 
/*     */       add(paramMenuItem);
/*     */       for (j = 0; j < vector.size(); j++)
/*     */         add(vector.elementAt(j)); 
/*     */     } 
/*     */   } public void insert(String paramString, int paramInt) {
/*     */     insert(new MenuItem(paramString), paramInt);
/* 545 */   } private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException, HeadlessException { paramObjectInputStream.defaultReadObject();
/* 546 */     for (byte b = 0; b < this.items.size(); b++)
/* 547 */     { MenuItem menuItem = (MenuItem)this.items.elementAt(b);
/* 548 */       menuItem.parent = this; }  }
/*     */   public void addSeparator() { add("-"); }
/*     */   public void insertSeparator(int paramInt) { synchronized (getTreeLock()) { if (paramInt < 0)
/*     */         throw new IllegalArgumentException("index less than zero.");  int i = getItemCount(); Vector<MenuItem> vector = new Vector(); int j; for (j = paramInt; j < i; j++) { vector.addElement(getItem(paramInt)); remove(paramInt); }  addSeparator(); for (j = 0; j < vector.size(); j++)
/*     */         add(vector.elementAt(j));  }  }
/*     */   public void remove(int paramInt) { synchronized (getTreeLock()) { MenuItem menuItem = getItem(paramInt); this.items.removeElementAt(paramInt); MenuPeer menuPeer = (MenuPeer)this.peer; if (menuPeer != null) { menuPeer.delItem(paramInt); menuItem.removeNotify(); menuItem.parent = null; }  }  }
/*     */   public void remove(MenuComponent paramMenuComponent) { synchronized (getTreeLock()) { int i = this.items.indexOf(paramMenuComponent); if (i >= 0)
/*     */         remove(i);  }
/*     */      }
/*     */   public void removeAll() { synchronized (getTreeLock()) { int i = getItemCount(); for (int j = i - 1; j >= 0; j--)
/*     */         remove(j);  }
/*     */      }
/*     */   boolean handleShortcut(KeyEvent paramKeyEvent) { int i = getItemCount(); for (byte b = 0; b < i; b++) { MenuItem menuItem = getItem(b); if (menuItem.handleShortcut(paramKeyEvent))
/*     */         return true;  }
/* 562 */      return false; } public String paramString() { String str = ",tearOff=" + this.tearOff + ",isHelpMenu=" + this.isHelpMenu;
/* 563 */     return super.paramString() + str; } MenuItem getShortcutMenuItem(MenuShortcut paramMenuShortcut) { int i = getItemCount(); for (byte b = 0; b < i; b++) {
/*     */       MenuItem menuItem = getItem(b).getShortcutMenuItem(paramMenuShortcut);
/*     */       if (menuItem != null)
/*     */         return menuItem; 
/*     */     } 
/*     */     return null; }
/*     */   synchronized Enumeration<MenuShortcut> shortcuts() { Vector<MenuShortcut> vector = new Vector();
/*     */     int i = getItemCount();
/*     */     for (byte b = 0; b < i; b++) {
/*     */       MenuItem menuItem = getItem(b);
/*     */       if (menuItem instanceof Menu) {
/*     */         Enumeration<MenuShortcut> enumeration = ((Menu)menuItem).shortcuts();
/*     */         while (enumeration.hasMoreElements())
/*     */           vector.addElement(enumeration.nextElement()); 
/*     */       } else {
/*     */         MenuShortcut menuShortcut = menuItem.getShortcut();
/*     */         if (menuShortcut != null)
/*     */           vector.addElement(menuShortcut); 
/*     */       } 
/*     */     } 
/*     */     return vector.elements(); }
/*     */   void deleteShortcut(MenuShortcut paramMenuShortcut) { int i = getItemCount();
/*     */     for (byte b = 0; b < i; b++)
/*     */       getItem(b).deleteShortcut(paramMenuShortcut);  }
/* 587 */   public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 588 */       this.accessibleContext = new AccessibleAWTMenu();
/*     */     }
/* 590 */     return this.accessibleContext; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getAccessibleChildIndex(MenuComponent paramMenuComponent) {
/* 597 */     return this.items.indexOf(paramMenuComponent);
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
/*     */   protected class AccessibleAWTMenu
/*     */     extends MenuItem.AccessibleAWTMenuItem
/*     */   {
/*     */     private static final long serialVersionUID = 5228160894980069094L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 625 */       return AccessibleRole.MENU;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Menu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */