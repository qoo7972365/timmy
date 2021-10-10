/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.beans.Transient;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Vector;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleSelection;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import javax.swing.plaf.MenuBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMenuBar
/*     */   extends JComponent
/*     */   implements Accessible, MenuElement
/*     */ {
/*     */   private static final String uiClassID = "MenuBarUI";
/*     */   private transient SingleSelectionModel selectionModel;
/*     */   private boolean paintBorder = true;
/* 107 */   private Insets margin = null;
/*     */ 
/*     */   
/*     */   private static final boolean TRACE = false;
/*     */ 
/*     */   
/*     */   private static final boolean VERBOSE = false;
/*     */   
/*     */   private static final boolean DEBUG = false;
/*     */ 
/*     */   
/*     */   public JMenuBar() {
/* 119 */     setFocusTraversalKeysEnabled(false);
/* 120 */     setSelectionModel(new DefaultSingleSelectionModel());
/* 121 */     updateUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuBarUI getUI() {
/* 129 */     return (MenuBarUI)this.ui;
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
/*     */   public void setUI(MenuBarUI paramMenuBarUI) {
/* 144 */     setUI(paramMenuBarUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 153 */     setUI((MenuBarUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 165 */     return "MenuBarUI";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SingleSelectionModel getSelectionModel() {
/* 176 */     return this.selectionModel;
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
/*     */   public void setSelectionModel(SingleSelectionModel paramSingleSelectionModel) {
/* 189 */     SingleSelectionModel singleSelectionModel = this.selectionModel;
/* 190 */     this.selectionModel = paramSingleSelectionModel;
/* 191 */     firePropertyChange("selectionModel", singleSelectionModel, this.selectionModel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenu add(JMenu paramJMenu) {
/* 202 */     add(paramJMenu);
/* 203 */     return paramJMenu;
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
/*     */   public JMenu getMenu(int paramInt) {
/* 216 */     Component component = getComponentAtIndex(paramInt);
/* 217 */     if (component instanceof JMenu)
/* 218 */       return (JMenu)component; 
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMenuCount() {
/* 228 */     return getComponentCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHelpMenu(JMenu paramJMenu) {
/* 239 */     throw new Error("setHelpMenu() not yet implemented.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transient
/*     */   public JMenu getHelpMenu() {
/* 250 */     throw new Error("getHelpMenu() not yet implemented.");
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
/*     */   @Deprecated
/*     */   public Component getComponentAtIndex(int paramInt) {
/* 263 */     if (paramInt < 0 || paramInt >= getComponentCount()) {
/* 264 */       return null;
/*     */     }
/* 266 */     return getComponent(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentIndex(Component paramComponent) {
/* 277 */     int i = getComponentCount();
/* 278 */     Component[] arrayOfComponent = getComponents();
/* 279 */     for (byte b = 0; b < i; b++) {
/* 280 */       Component component = arrayOfComponent[b];
/* 281 */       if (component == paramComponent)
/* 282 */         return b; 
/*     */     } 
/* 284 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(Component paramComponent) {
/* 294 */     SingleSelectionModel singleSelectionModel = getSelectionModel();
/* 295 */     int i = getComponentIndex(paramComponent);
/* 296 */     singleSelectionModel.setSelectedIndex(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/* 305 */     return this.selectionModel.isSelected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderPainted() {
/* 314 */     return this.paintBorder;
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
/*     */   public void setBorderPainted(boolean paramBoolean) {
/* 329 */     boolean bool = this.paintBorder;
/* 330 */     this.paintBorder = paramBoolean;
/* 331 */     firePropertyChange("borderPainted", bool, this.paintBorder);
/* 332 */     if (paramBoolean != bool) {
/* 333 */       revalidate();
/* 334 */       repaint();
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
/*     */   protected void paintBorder(Graphics paramGraphics) {
/* 347 */     if (isBorderPainted()) {
/* 348 */       super.paintBorder(paramGraphics);
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
/*     */   public void setMargin(Insets paramInsets) {
/* 365 */     Insets insets = this.margin;
/* 366 */     this.margin = paramInsets;
/* 367 */     firePropertyChange("margin", insets, paramInsets);
/* 368 */     if (insets == null || !insets.equals(paramInsets)) {
/* 369 */       revalidate();
/* 370 */       repaint();
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
/*     */   public Insets getMargin() {
/* 383 */     if (this.margin == null) {
/* 384 */       return new Insets(0, 0, 0, 0);
/*     */     }
/* 386 */     return this.margin;
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
/*     */   public void processMouseEvent(MouseEvent paramMouseEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processKeyEvent(KeyEvent paramKeyEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void menuSelectionChanged(boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuElement[] getSubElements() {
/* 425 */     Vector<MenuElement> vector = new Vector();
/* 426 */     int i = getComponentCount();
/*     */     
/*     */     byte b;
/*     */     
/* 430 */     for (b = 0; b < i; b++) {
/* 431 */       Component component = getComponent(b);
/* 432 */       if (component instanceof MenuElement) {
/* 433 */         vector.addElement((MenuElement)component);
/*     */       }
/*     */     } 
/* 436 */     MenuElement[] arrayOfMenuElement = new MenuElement[vector.size()];
/* 437 */     for (b = 0, i = vector.size(); b < i; b++)
/* 438 */       arrayOfMenuElement[b] = vector.elementAt(b); 
/* 439 */     return arrayOfMenuElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 449 */     return this;
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
/*     */   protected String paramString() {
/* 464 */     String str1 = this.paintBorder ? "true" : "false";
/*     */ 
/*     */     
/* 467 */     String str2 = (this.margin != null) ? this.margin.toString() : "";
/*     */     
/* 469 */     return super.paramString() + ",margin=" + str2 + ",paintBorder=" + str1;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 488 */     if (this.accessibleContext == null) {
/* 489 */       this.accessibleContext = new AccessibleJMenuBar();
/*     */     }
/* 491 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJMenuBar
/*     */     extends JComponent.AccessibleJComponent
/*     */     implements AccessibleSelection
/*     */   {
/*     */     public AccessibleStateSet getAccessibleStateSet() {
/* 520 */       return super.getAccessibleStateSet();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 531 */       return AccessibleRole.MENU_BAR;
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
/*     */     public AccessibleSelection getAccessibleSelection() {
/* 543 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getAccessibleSelectionCount() {
/* 552 */       if (JMenuBar.this.isSelected()) {
/* 553 */         return 1;
/*     */       }
/* 555 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Accessible getAccessibleSelection(int param1Int) {
/* 564 */       if (JMenuBar.this.isSelected()) {
/* 565 */         if (param1Int != 0) {
/* 566 */           return null;
/*     */         }
/* 568 */         int i = JMenuBar.this.getSelectionModel().getSelectedIndex();
/* 569 */         if (JMenuBar.this.getComponentAtIndex(i) instanceof Accessible) {
/* 570 */           return (Accessible)JMenuBar.this.getComponentAtIndex(i);
/*     */         }
/*     */       } 
/* 573 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isAccessibleChildSelected(int param1Int) {
/* 584 */       return (param1Int == JMenuBar.this.getSelectionModel().getSelectedIndex());
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
/*     */ 
/*     */     
/*     */     public void addAccessibleSelection(int param1Int) {
/* 598 */       int i = JMenuBar.this.getSelectionModel().getSelectedIndex();
/* 599 */       if (param1Int == i) {
/*     */         return;
/*     */       }
/* 602 */       if (i >= 0 && i < JMenuBar.this.getMenuCount()) {
/* 603 */         JMenu jMenu1 = JMenuBar.this.getMenu(i);
/* 604 */         if (jMenu1 != null) {
/* 605 */           MenuSelectionManager.defaultManager().setSelectedPath(null);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 610 */       JMenuBar.this.getSelectionModel().setSelectedIndex(param1Int);
/* 611 */       JMenu jMenu = JMenuBar.this.getMenu(param1Int);
/* 612 */       if (jMenu != null) {
/* 613 */         MenuElement[] arrayOfMenuElement = new MenuElement[3];
/* 614 */         arrayOfMenuElement[0] = JMenuBar.this;
/* 615 */         arrayOfMenuElement[1] = jMenu;
/* 616 */         arrayOfMenuElement[2] = jMenu.getPopupMenu();
/* 617 */         MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement);
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
/*     */     
/*     */     public void removeAccessibleSelection(int param1Int) {
/* 630 */       if (param1Int >= 0 && param1Int < JMenuBar.this.getMenuCount()) {
/* 631 */         JMenu jMenu = JMenuBar.this.getMenu(param1Int);
/* 632 */         if (jMenu != null) {
/* 633 */           MenuSelectionManager.defaultManager().setSelectedPath(null);
/*     */         }
/*     */         
/* 636 */         JMenuBar.this.getSelectionModel().setSelectedIndex(-1);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clearAccessibleSelection() {
/* 645 */       int i = JMenuBar.this.getSelectionModel().getSelectedIndex();
/* 646 */       if (i >= 0 && i < JMenuBar.this.getMenuCount()) {
/* 647 */         JMenu jMenu = JMenuBar.this.getMenu(i);
/* 648 */         if (jMenu != null) {
/* 649 */           MenuSelectionManager.defaultManager().setSelectedPath(null);
/*     */         }
/*     */       } 
/*     */       
/* 653 */       JMenuBar.this.getSelectionModel().setSelectedIndex(-1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void selectAllAccessibleSelection() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean processKeyBinding(KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean) {
/* 673 */     boolean bool = super.processKeyBinding(paramKeyStroke, paramKeyEvent, paramInt, paramBoolean);
/* 674 */     if (!bool) {
/* 675 */       MenuElement[] arrayOfMenuElement = getSubElements();
/* 676 */       for (MenuElement menuElement : arrayOfMenuElement) {
/* 677 */         if (processBindingForKeyStrokeRecursive(menuElement, paramKeyStroke, paramKeyEvent, paramInt, paramBoolean))
/*     */         {
/* 679 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 683 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean processBindingForKeyStrokeRecursive(MenuElement paramMenuElement, KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean) {
/* 688 */     if (paramMenuElement == null) {
/* 689 */       return false;
/*     */     }
/*     */     
/* 692 */     Component component = paramMenuElement.getComponent();
/*     */     
/* 694 */     if ((!component.isVisible() && !(component instanceof JPopupMenu)) || !component.isEnabled()) {
/* 695 */       return false;
/*     */     }
/*     */     
/* 698 */     if (component != null && component instanceof JComponent && ((JComponent)component)
/* 699 */       .processKeyBinding(paramKeyStroke, paramKeyEvent, paramInt, paramBoolean))
/*     */     {
/* 701 */       return true;
/*     */     }
/*     */     
/* 704 */     MenuElement[] arrayOfMenuElement = paramMenuElement.getSubElements();
/* 705 */     for (MenuElement menuElement : arrayOfMenuElement) {
/* 706 */       if (processBindingForKeyStrokeRecursive(menuElement, paramKeyStroke, paramKeyEvent, paramInt, paramBoolean)) {
/* 707 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 711 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 719 */     super.addNotify();
/* 720 */     KeyboardManager.getCurrentManager().registerMenuBar(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 728 */     super.removeNotify();
/* 729 */     KeyboardManager.getCurrentManager().unregisterMenuBar(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 734 */     paramObjectOutputStream.defaultWriteObject();
/* 735 */     if (getUIClassID().equals("MenuBarUI")) {
/* 736 */       byte b1 = JComponent.getWriteObjCounter(this);
/* 737 */       b1 = (byte)(b1 - 1); JComponent.setWriteObjCounter(this, b1);
/* 738 */       if (b1 == 0 && this.ui != null) {
/* 739 */         this.ui.installUI(this);
/*     */       }
/*     */     } 
/*     */     
/* 743 */     Object[] arrayOfObject = new Object[4];
/* 744 */     byte b = 0;
/*     */     
/* 746 */     if (this.selectionModel instanceof java.io.Serializable) {
/* 747 */       arrayOfObject[b++] = "selectionModel";
/* 748 */       arrayOfObject[b++] = this.selectionModel;
/*     */     } 
/*     */     
/* 751 */     paramObjectOutputStream.writeObject(arrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 761 */     paramObjectInputStream.defaultReadObject();
/* 762 */     Object[] arrayOfObject = (Object[])paramObjectInputStream.readObject();
/*     */     
/* 764 */     for (byte b = 0; b < arrayOfObject.length && 
/* 765 */       arrayOfObject[b] != null; b += 2) {
/*     */ 
/*     */       
/* 768 */       if (arrayOfObject[b].equals("selectionModel"))
/* 769 */         this.selectionModel = (SingleSelectionModel)arrayOfObject[b + 1]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JMenuBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */