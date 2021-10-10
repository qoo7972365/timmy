/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.MenuEvent;
/*      */ import javax.swing.event.MenuListener;
/*      */ import javax.swing.plaf.MenuItemUI;
/*      */ import javax.swing.plaf.PopupMenuUI;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JMenu
/*      */   extends JMenuItem
/*      */   implements Accessible, MenuElement
/*      */ {
/*      */   private static final String uiClassID = "MenuUI";
/*      */   private JPopupMenu popupMenu;
/*  129 */   private ChangeListener menuChangeListener = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   private MenuEvent menuEvent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int delay;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   private Point customMenuLocation = null;
/*      */   
/*      */   private static final boolean TRACE = false;
/*      */   
/*      */   private static final boolean VERBOSE = false;
/*      */   
/*      */   private static final boolean DEBUG = false;
/*      */   
/*      */   protected WinListener popupListener;
/*      */   
/*      */   public JMenu() {
/*  159 */     this("");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenu(String paramString) {
/*  169 */     super(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenu(Action paramAction) {
/*  180 */     this();
/*  181 */     setAction(paramAction);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenu(String paramString, boolean paramBoolean) {
/*  192 */     this(paramString);
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
/*      */   void initFocusability() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  217 */     setUI((MenuItemUI)UIManager.getUI(this));
/*      */     
/*  219 */     if (this.popupMenu != null)
/*      */     {
/*  221 */       this.popupMenu.setUI((PopupMenuUI)UIManager.getUI(this.popupMenu));
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
/*      */   public String getUIClassID() {
/*  235 */     return "MenuUI";
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
/*      */   public void setModel(ButtonModel paramButtonModel) {
/*  256 */     ButtonModel buttonModel = getModel();
/*      */     
/*  258 */     super.setModel(paramButtonModel);
/*      */     
/*  260 */     if (buttonModel != null && this.menuChangeListener != null) {
/*  261 */       buttonModel.removeChangeListener(this.menuChangeListener);
/*  262 */       this.menuChangeListener = null;
/*      */     } 
/*      */     
/*  265 */     this.model = paramButtonModel;
/*      */     
/*  267 */     if (paramButtonModel != null) {
/*  268 */       this.menuChangeListener = createMenuChangeListener();
/*  269 */       paramButtonModel.addChangeListener(this.menuChangeListener);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSelected() {
/*  279 */     return getModel().isSelected();
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
/*      */   public void setSelected(boolean paramBoolean) {
/*  293 */     ButtonModel buttonModel = getModel();
/*  294 */     boolean bool = buttonModel.isSelected();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  304 */     if (paramBoolean != buttonModel.isSelected()) {
/*  305 */       getModel().setSelected(paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPopupMenuVisible() {
/*  315 */     ensurePopupMenuCreated();
/*  316 */     return this.popupMenu.isVisible();
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
/*      */   public void setPopupMenuVisible(boolean paramBoolean) {
/*  336 */     boolean bool = isPopupMenuVisible();
/*  337 */     if (paramBoolean != bool && (isEnabled() || !paramBoolean)) {
/*  338 */       ensurePopupMenuCreated();
/*  339 */       if (paramBoolean == true && isShowing()) {
/*      */         
/*  341 */         Point point = getCustomMenuLocation();
/*  342 */         if (point == null) {
/*  343 */           point = getPopupMenuOrigin();
/*      */         }
/*  345 */         getPopupMenu().show(this, point.x, point.y);
/*      */       } else {
/*  347 */         getPopupMenu().setVisible(false);
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
/*      */   protected Point getPopupMenuOrigin() {
/*      */     int i, j;
/*  370 */     JPopupMenu jPopupMenu = getPopupMenu();
/*      */     
/*  372 */     Dimension dimension1 = getSize();
/*  373 */     Dimension dimension2 = jPopupMenu.getSize();
/*      */ 
/*      */     
/*  376 */     if (dimension2.width == 0) {
/*  377 */       dimension2 = jPopupMenu.getPreferredSize();
/*      */     }
/*  379 */     Point point = getLocationOnScreen();
/*  380 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  381 */     GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
/*  382 */     Rectangle rectangle = new Rectangle(toolkit.getScreenSize());
/*      */     
/*  384 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  385 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*  386 */     for (byte b = 0; b < arrayOfGraphicsDevice.length; b++) {
/*  387 */       if (arrayOfGraphicsDevice[b].getType() == 0) {
/*      */         
/*  389 */         GraphicsConfiguration graphicsConfiguration1 = arrayOfGraphicsDevice[b].getDefaultConfiguration();
/*  390 */         if (graphicsConfiguration1.getBounds().contains(point)) {
/*  391 */           graphicsConfiguration = graphicsConfiguration1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  398 */     if (graphicsConfiguration != null) {
/*  399 */       rectangle = graphicsConfiguration.getBounds();
/*      */       
/*  401 */       Insets insets = toolkit.getScreenInsets(graphicsConfiguration);
/*      */       
/*  403 */       rectangle.width -= 
/*  404 */         Math.abs(insets.left + insets.right);
/*  405 */       rectangle.height -= 
/*  406 */         Math.abs(insets.top + insets.bottom);
/*  407 */       point.x -= Math.abs(insets.left);
/*  408 */       point.y -= Math.abs(insets.top);
/*      */     } 
/*      */     
/*  411 */     Container container = getParent();
/*  412 */     if (container instanceof JPopupMenu) {
/*      */       
/*  414 */       int k = UIManager.getInt("Menu.submenuPopupOffsetX");
/*  415 */       int m = UIManager.getInt("Menu.submenuPopupOffsetY");
/*      */       
/*  417 */       if (SwingUtilities.isLeftToRight(this)) {
/*      */         
/*  419 */         i = dimension1.width + k;
/*  420 */         if (point.x + i + dimension2.width >= rectangle.width + rectangle.x && rectangle.width - dimension1.width < 2 * (point.x - rectangle.x))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  426 */           i = 0 - k - dimension2.width;
/*      */         }
/*      */       } else {
/*      */         
/*  430 */         i = 0 - k - dimension2.width;
/*  431 */         if (point.x + i < rectangle.x && rectangle.width - dimension1.width > 2 * (point.x - rectangle.x))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  436 */           i = dimension1.width + k;
/*      */         }
/*      */       } 
/*      */       
/*  440 */       j = m;
/*  441 */       if (point.y + j + dimension2.height >= rectangle.height + rectangle.y && rectangle.height - dimension1.height < 2 * (point.y - rectangle.y))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  447 */         j = dimension1.height - m - dimension2.height;
/*      */       }
/*      */     } else {
/*      */       
/*  451 */       int k = UIManager.getInt("Menu.menuPopupOffsetX");
/*  452 */       int m = UIManager.getInt("Menu.menuPopupOffsetY");
/*      */       
/*  454 */       if (SwingUtilities.isLeftToRight(this)) {
/*      */         
/*  456 */         i = k;
/*  457 */         if (point.x + i + dimension2.width >= rectangle.width + rectangle.x && rectangle.width - dimension1.width < 2 * (point.x - rectangle.x))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  463 */           i = dimension1.width - k - dimension2.width;
/*      */         }
/*      */       } else {
/*      */         
/*  467 */         i = dimension1.width - k - dimension2.width;
/*  468 */         if (point.x + i < rectangle.x && rectangle.width - dimension1.width > 2 * (point.x - rectangle.x))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  473 */           i = k;
/*      */         }
/*      */       } 
/*      */       
/*  477 */       j = dimension1.height + m;
/*  478 */       if (point.y + j + dimension2.height >= rectangle.height + rectangle.y && rectangle.height - dimension1.height < 2 * (point.y - rectangle.y))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  484 */         j = 0 - m - dimension2.height;
/*      */       }
/*      */     } 
/*  487 */     return new Point(i, j);
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
/*      */   public int getDelay() {
/*  505 */     return this.delay;
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
/*      */   public void setDelay(int paramInt) {
/*  524 */     if (paramInt < 0) {
/*  525 */       throw new IllegalArgumentException("Delay must be a positive integer");
/*      */     }
/*  527 */     this.delay = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensurePopupMenuCreated() {
/*  538 */     if (this.popupMenu == null) {
/*  539 */       JMenu jMenu = this;
/*  540 */       this.popupMenu = new JPopupMenu();
/*  541 */       this.popupMenu.setInvoker(this);
/*  542 */       this.popupListener = createWinListener(this.popupMenu);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Point getCustomMenuLocation() {
/*  550 */     return this.customMenuLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMenuLocation(int paramInt1, int paramInt2) {
/*  560 */     this.customMenuLocation = new Point(paramInt1, paramInt2);
/*  561 */     if (this.popupMenu != null) {
/*  562 */       this.popupMenu.setLocation(paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenuItem add(JMenuItem paramJMenuItem) {
/*  573 */     ensurePopupMenuCreated();
/*  574 */     return this.popupMenu.add(paramJMenuItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component add(Component paramComponent) {
/*  585 */     ensurePopupMenuCreated();
/*  586 */     this.popupMenu.add(paramComponent);
/*  587 */     return paramComponent;
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
/*      */   public Component add(Component paramComponent, int paramInt) {
/*  601 */     ensurePopupMenuCreated();
/*  602 */     this.popupMenu.add(paramComponent, paramInt);
/*  603 */     return paramComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenuItem add(String paramString) {
/*  613 */     return add(new JMenuItem(paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenuItem add(Action paramAction) {
/*  624 */     JMenuItem jMenuItem = createActionComponent(paramAction);
/*  625 */     jMenuItem.setAction(paramAction);
/*  626 */     add(jMenuItem);
/*  627 */     return jMenuItem;
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
/*      */   protected JMenuItem createActionComponent(Action paramAction) {
/*  641 */     JMenuItem jMenuItem = new JMenuItem() {
/*      */         protected PropertyChangeListener createActionPropertyChangeListener(Action param1Action) {
/*  643 */           PropertyChangeListener propertyChangeListener = JMenu.this.createActionChangeListener(this);
/*  644 */           if (propertyChangeListener == null) {
/*  645 */             propertyChangeListener = super.createActionPropertyChangeListener(param1Action);
/*      */           }
/*  647 */           return propertyChangeListener;
/*      */         }
/*      */       };
/*  650 */     jMenuItem.setHorizontalTextPosition(11);
/*  651 */     jMenuItem.setVerticalTextPosition(0);
/*  652 */     return jMenuItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createActionChangeListener(JMenuItem paramJMenuItem) {
/*  660 */     return paramJMenuItem.createActionPropertyChangeListener0(paramJMenuItem.getAction());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSeparator() {
/*  668 */     ensurePopupMenuCreated();
/*  669 */     this.popupMenu.addSeparator();
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
/*      */   public void insert(String paramString, int paramInt) {
/*  683 */     if (paramInt < 0) {
/*  684 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*      */     
/*  687 */     ensurePopupMenuCreated();
/*  688 */     this.popupMenu.insert(new JMenuItem(paramString), paramInt);
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
/*      */   public JMenuItem insert(JMenuItem paramJMenuItem, int paramInt) {
/*  702 */     if (paramInt < 0) {
/*  703 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*  705 */     ensurePopupMenuCreated();
/*  706 */     this.popupMenu.insert(paramJMenuItem, paramInt);
/*  707 */     return paramJMenuItem;
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
/*      */   public JMenuItem insert(Action paramAction, int paramInt) {
/*  721 */     if (paramInt < 0) {
/*  722 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*      */     
/*  725 */     ensurePopupMenuCreated();
/*  726 */     JMenuItem jMenuItem = new JMenuItem(paramAction);
/*  727 */     jMenuItem.setHorizontalTextPosition(11);
/*  728 */     jMenuItem.setVerticalTextPosition(0);
/*  729 */     this.popupMenu.insert(jMenuItem, paramInt);
/*  730 */     return jMenuItem;
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
/*      */   public void insertSeparator(int paramInt) {
/*  742 */     if (paramInt < 0) {
/*  743 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*      */     
/*  746 */     ensurePopupMenuCreated();
/*  747 */     this.popupMenu.insert(new JPopupMenu.Separator(), paramInt);
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
/*      */   public JMenuItem getItem(int paramInt) {
/*  763 */     if (paramInt < 0) {
/*  764 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*      */     
/*  767 */     Component component = getMenuComponent(paramInt);
/*  768 */     if (component instanceof JMenuItem) {
/*  769 */       return (JMenuItem)component;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  774 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/*  785 */     return getMenuComponentCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTearOff() {
/*  796 */     throw new Error("boolean isTearOff() {} not yet implemented");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove(JMenuItem paramJMenuItem) {
/*  806 */     if (this.popupMenu != null) {
/*  807 */       this.popupMenu.remove(paramJMenuItem);
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
/*      */   public void remove(int paramInt) {
/*  819 */     if (paramInt < 0) {
/*  820 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*  822 */     if (paramInt > getItemCount()) {
/*  823 */       throw new IllegalArgumentException("index greater than the number of items.");
/*      */     }
/*  825 */     if (this.popupMenu != null) {
/*  826 */       this.popupMenu.remove(paramInt);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove(Component paramComponent) {
/*  835 */     if (this.popupMenu != null) {
/*  836 */       this.popupMenu.remove(paramComponent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAll() {
/*  843 */     if (this.popupMenu != null) {
/*  844 */       this.popupMenu.removeAll();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMenuComponentCount() {
/*  853 */     int i = 0;
/*  854 */     if (this.popupMenu != null)
/*  855 */       i = this.popupMenu.getComponentCount(); 
/*  856 */     return i;
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
/*      */   public Component getMenuComponent(int paramInt) {
/*  868 */     if (this.popupMenu != null) {
/*  869 */       return this.popupMenu.getComponent(paramInt);
/*      */     }
/*  871 */     return null;
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
/*      */   public Component[] getMenuComponents() {
/*  883 */     if (this.popupMenu != null) {
/*  884 */       return this.popupMenu.getComponents();
/*      */     }
/*  886 */     return new Component[0];
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
/*      */   public boolean isTopLevelMenu() {
/*  898 */     return getParent() instanceof JMenuBar;
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
/*      */   public boolean isMenuComponent(Component paramComponent) {
/*  911 */     if (paramComponent == this) {
/*  912 */       return true;
/*      */     }
/*  914 */     if (paramComponent instanceof JPopupMenu) {
/*  915 */       JPopupMenu jPopupMenu = (JPopupMenu)paramComponent;
/*  916 */       if (jPopupMenu == getPopupMenu()) {
/*  917 */         return true;
/*      */       }
/*      */     } 
/*  920 */     int i = getMenuComponentCount();
/*  921 */     Component[] arrayOfComponent = getMenuComponents();
/*  922 */     for (byte b = 0; b < i; b++) {
/*  923 */       Component component = arrayOfComponent[b];
/*      */       
/*  925 */       if (component == paramComponent) {
/*  926 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  930 */       if (component instanceof JMenu) {
/*  931 */         JMenu jMenu = (JMenu)component;
/*  932 */         if (jMenu.isMenuComponent(paramComponent))
/*  933 */           return true; 
/*      */       } 
/*      */     } 
/*  936 */     return false;
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
/*      */   private Point translateToPopupMenu(Point paramPoint) {
/*  949 */     return translateToPopupMenu(paramPoint.x, paramPoint.y);
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
/*      */   private Point translateToPopupMenu(int paramInt1, int paramInt2) {
/*      */     int i;
/*      */     int j;
/*  964 */     if (getParent() instanceof JPopupMenu) {
/*  965 */       i = paramInt1 - (getSize()).width;
/*  966 */       j = paramInt2;
/*      */     } else {
/*  968 */       i = paramInt1;
/*  969 */       j = paramInt2 - (getSize()).height;
/*      */     } 
/*      */     
/*  972 */     return new Point(i, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JPopupMenu getPopupMenu() {
/*  980 */     ensurePopupMenuCreated();
/*  981 */     return this.popupMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMenuListener(MenuListener paramMenuListener) {
/*  990 */     this.listenerList.add(MenuListener.class, paramMenuListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeMenuListener(MenuListener paramMenuListener) {
/*  999 */     this.listenerList.remove(MenuListener.class, paramMenuListener);
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
/*      */   public MenuListener[] getMenuListeners() {
/* 1011 */     return this.listenerList.<MenuListener>getListeners(MenuListener.class);
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
/*      */   protected void fireMenuSelected() {
/* 1027 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/* 1030 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1031 */       if (arrayOfObject[i] == MenuListener.class) {
/* 1032 */         if (arrayOfObject[i + 1] == null) {
/* 1033 */           throw new Error(getText() + " has a NULL Listener!! " + i);
/*      */         }
/*      */         
/* 1036 */         if (this.menuEvent == null)
/* 1037 */           this.menuEvent = new MenuEvent(this); 
/* 1038 */         ((MenuListener)arrayOfObject[i + 1]).menuSelected(this.menuEvent);
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
/*      */   protected void fireMenuDeselected() {
/* 1057 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/* 1060 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1061 */       if (arrayOfObject[i] == MenuListener.class) {
/* 1062 */         if (arrayOfObject[i + 1] == null) {
/* 1063 */           throw new Error(getText() + " has a NULL Listener!! " + i);
/*      */         }
/*      */         
/* 1066 */         if (this.menuEvent == null)
/* 1067 */           this.menuEvent = new MenuEvent(this); 
/* 1068 */         ((MenuListener)arrayOfObject[i + 1]).menuDeselected(this.menuEvent);
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
/*      */   protected void fireMenuCanceled() {
/* 1087 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*      */ 
/*      */     
/* 1090 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1091 */       if (arrayOfObject[i] == MenuListener.class) {
/* 1092 */         if (arrayOfObject[i + 1] == null) {
/* 1093 */           throw new Error(getText() + " has a NULL Listener!! " + i);
/*      */         }
/*      */ 
/*      */         
/* 1097 */         if (this.menuEvent == null)
/* 1098 */           this.menuEvent = new MenuEvent(this); 
/* 1099 */         ((MenuListener)arrayOfObject[i + 1]).menuCanceled(this.menuEvent);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void configureAcceleratorFromAction(Action paramAction) {}
/*      */   
/*      */   class MenuChangeListener
/*      */     implements ChangeListener, Serializable
/*      */   {
/*      */     boolean isSelected = false;
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 1113 */       ButtonModel buttonModel = (ButtonModel)param1ChangeEvent.getSource();
/* 1114 */       boolean bool = buttonModel.isSelected();
/*      */       
/* 1116 */       if (bool != this.isSelected) {
/* 1117 */         if (bool == true) {
/* 1118 */           JMenu.this.fireMenuSelected();
/*      */         } else {
/* 1120 */           JMenu.this.fireMenuDeselected();
/*      */         } 
/* 1122 */         this.isSelected = bool;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private ChangeListener createMenuChangeListener() {
/* 1128 */     return new MenuChangeListener();
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
/*      */   protected WinListener createWinListener(JPopupMenu paramJPopupMenu) {
/* 1141 */     return new WinListener(paramJPopupMenu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class WinListener
/*      */     extends WindowAdapter
/*      */     implements Serializable
/*      */   {
/*      */     JPopupMenu popupMenu;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WinListener(JPopupMenu param1JPopupMenu) {
/* 1165 */       this.popupMenu = param1JPopupMenu;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void windowClosing(WindowEvent param1WindowEvent) {
/* 1171 */       JMenu.this.setSelected(false);
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
/*      */   public void menuSelectionChanged(boolean paramBoolean) {
/* 1187 */     setSelected(paramBoolean);
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
/*      */   public MenuElement[] getSubElements() {
/* 1201 */     if (this.popupMenu == null) {
/* 1202 */       return new MenuElement[0];
/*      */     }
/* 1204 */     MenuElement[] arrayOfMenuElement = new MenuElement[1];
/* 1205 */     arrayOfMenuElement[0] = this.popupMenu;
/* 1206 */     return arrayOfMenuElement;
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
/*      */   public Component getComponent() {
/* 1219 */     return this;
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
/*      */   public void applyComponentOrientation(ComponentOrientation paramComponentOrientation) {
/* 1236 */     super.applyComponentOrientation(paramComponentOrientation);
/*      */     
/* 1238 */     if (this.popupMenu != null) {
/* 1239 */       int i = getMenuComponentCount();
/* 1240 */       for (byte b = 0; b < i; b++) {
/* 1241 */         getMenuComponent(b).applyComponentOrientation(paramComponentOrientation);
/*      */       }
/* 1243 */       this.popupMenu.setComponentOrientation(paramComponentOrientation);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setComponentOrientation(ComponentOrientation paramComponentOrientation) {
/* 1248 */     super.setComponentOrientation(paramComponentOrientation);
/* 1249 */     if (this.popupMenu != null) {
/* 1250 */       this.popupMenu.setComponentOrientation(paramComponentOrientation);
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
/*      */   public void setAccelerator(KeyStroke paramKeyStroke) {
/* 1269 */     throw new Error("setAccelerator() is not defined for JMenu.  Use setMnemonic() instead.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processKeyEvent(KeyEvent paramKeyEvent) {
/* 1278 */     MenuSelectionManager.defaultManager().processKeyEvent(paramKeyEvent);
/* 1279 */     if (paramKeyEvent.isConsumed()) {
/*      */       return;
/*      */     }
/* 1282 */     super.processKeyEvent(paramKeyEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doClick(int paramInt) {
/* 1292 */     MenuElement[] arrayOfMenuElement = buildMenuElementArray(this);
/* 1293 */     MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MenuElement[] buildMenuElementArray(JMenu paramJMenu) {
/* 1303 */     Vector<JPopupMenu> vector = new Vector();
/* 1304 */     JPopupMenu jPopupMenu = paramJMenu.getPopupMenu();
/*      */ 
/*      */     
/*      */     while (true) {
/*      */       Component component;
/*      */       
/* 1310 */       while (jPopupMenu instanceof JPopupMenu) {
/* 1311 */         JPopupMenu jPopupMenu1 = jPopupMenu;
/* 1312 */         vector.insertElementAt(jPopupMenu1, 0);
/* 1313 */         component = jPopupMenu1.getInvoker();
/* 1314 */       }  if (component instanceof JMenu) {
/* 1315 */         JMenu jMenu = (JMenu)component;
/* 1316 */         vector.insertElementAt(jMenu, 0);
/* 1317 */         component = jMenu.getParent(); continue;
/* 1318 */       }  if (component instanceof JMenuBar) {
/* 1319 */         JMenuBar jMenuBar = (JMenuBar)component;
/* 1320 */         vector.insertElementAt(jMenuBar, 0);
/* 1321 */         MenuElement[] arrayOfMenuElement = new MenuElement[vector.size()];
/* 1322 */         vector.copyInto((Object[])arrayOfMenuElement);
/* 1323 */         return arrayOfMenuElement;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1335 */     paramObjectOutputStream.defaultWriteObject();
/* 1336 */     if (getUIClassID().equals("MenuUI")) {
/* 1337 */       byte b = JComponent.getWriteObjCounter(this);
/* 1338 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 1339 */       if (b == 0 && this.ui != null) {
/* 1340 */         this.ui.installUI(this);
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
/*      */   protected String paramString() {
/* 1356 */     return super.paramString();
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1374 */     if (this.accessibleContext == null) {
/* 1375 */       this.accessibleContext = new AccessibleJMenu();
/*      */     }
/* 1377 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJMenu
/*      */     extends JMenuItem.AccessibleJMenuItem
/*      */     implements AccessibleSelection
/*      */   {
/*      */     public int getAccessibleChildrenCount() {
/* 1406 */       Component[] arrayOfComponent = JMenu.this.getMenuComponents();
/* 1407 */       byte b = 0;
/* 1408 */       for (Component component : arrayOfComponent) {
/* 1409 */         if (component instanceof Accessible) {
/* 1410 */           b++;
/*      */         }
/*      */       } 
/* 1413 */       return b;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 1423 */       Component[] arrayOfComponent = JMenu.this.getMenuComponents();
/* 1424 */       int i = 0;
/* 1425 */       for (Component component : arrayOfComponent) {
/* 1426 */         if (component instanceof Accessible) {
/* 1427 */           if (i == param1Int) {
/* 1428 */             if (component instanceof JComponent) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1433 */               AccessibleContext accessibleContext = component.getAccessibleContext();
/* 1434 */               accessibleContext.setAccessibleParent(JMenu.this);
/*      */             } 
/* 1436 */             return (Accessible)component;
/*      */           } 
/* 1438 */           i++;
/*      */         } 
/*      */       } 
/*      */       
/* 1442 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/* 1453 */       return AccessibleRole.MENU;
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
/*      */     public AccessibleSelection getAccessibleSelection() {
/* 1465 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleSelectionCount() {
/* 1475 */       MenuElement[] arrayOfMenuElement = MenuSelectionManager.defaultManager().getSelectedPath();
/* 1476 */       if (arrayOfMenuElement != null) {
/* 1477 */         for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/* 1478 */           if (arrayOfMenuElement[b] == JMenu.this && 
/* 1479 */             b + 1 < arrayOfMenuElement.length) {
/* 1480 */             return 1;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1485 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleSelection(int param1Int) {
/* 1496 */       if (param1Int < 0 || param1Int >= JMenu.this.getItemCount()) {
/* 1497 */         return null;
/*      */       }
/*      */       
/* 1500 */       MenuElement[] arrayOfMenuElement = MenuSelectionManager.defaultManager().getSelectedPath();
/* 1501 */       if (arrayOfMenuElement != null) {
/* 1502 */         for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/* 1503 */           if (arrayOfMenuElement[b] == JMenu.this)
/*      */           {
/*      */             
/* 1506 */             while (++b < arrayOfMenuElement.length) {
/* 1507 */               if (arrayOfMenuElement[b] instanceof JMenuItem) {
/* 1508 */                 return (Accessible)arrayOfMenuElement[b];
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       }
/* 1514 */       return null;
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
/*      */     public boolean isAccessibleChildSelected(int param1Int) {
/* 1528 */       MenuElement[] arrayOfMenuElement = MenuSelectionManager.defaultManager().getSelectedPath();
/* 1529 */       if (arrayOfMenuElement != null) {
/* 1530 */         JMenuItem jMenuItem = JMenu.this.getItem(param1Int);
/* 1531 */         for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/* 1532 */           if (arrayOfMenuElement[b] == jMenuItem) {
/* 1533 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/* 1537 */       return false;
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
/*      */     
/*      */     public void addAccessibleSelection(int param1Int) {
/* 1553 */       if (param1Int < 0 || param1Int >= JMenu.this.getItemCount()) {
/*      */         return;
/*      */       }
/* 1556 */       JMenuItem jMenuItem = JMenu.this.getItem(param1Int);
/* 1557 */       if (jMenuItem != null) {
/* 1558 */         if (jMenuItem instanceof JMenu) {
/* 1559 */           MenuElement[] arrayOfMenuElement = JMenu.this.buildMenuElementArray((JMenu)jMenuItem);
/* 1560 */           MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement);
/*      */         } else {
/* 1562 */           MenuSelectionManager.defaultManager().setSelectedPath(null);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeAccessibleSelection(int param1Int) {
/* 1575 */       if (param1Int < 0 || param1Int >= JMenu.this.getItemCount()) {
/*      */         return;
/*      */       }
/* 1578 */       JMenuItem jMenuItem = JMenu.this.getItem(param1Int);
/* 1579 */       if (jMenuItem != null && jMenuItem instanceof JMenu && 
/* 1580 */         jMenuItem.isSelected()) {
/*      */         
/* 1582 */         MenuElement[] arrayOfMenuElement1 = MenuSelectionManager.defaultManager().getSelectedPath();
/* 1583 */         MenuElement[] arrayOfMenuElement2 = new MenuElement[arrayOfMenuElement1.length - 2];
/* 1584 */         for (byte b = 0; b < arrayOfMenuElement1.length - 2; b++) {
/* 1585 */           arrayOfMenuElement2[b] = arrayOfMenuElement1[b];
/*      */         }
/* 1587 */         MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement2);
/*      */       } 
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
/*      */     public void clearAccessibleSelection() {
/* 1600 */       MenuElement[] arrayOfMenuElement = MenuSelectionManager.defaultManager().getSelectedPath();
/* 1601 */       if (arrayOfMenuElement != null)
/* 1602 */         for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/* 1603 */           if (arrayOfMenuElement[b] == JMenu.this) {
/* 1604 */             MenuElement[] arrayOfMenuElement1 = new MenuElement[b + 1];
/* 1605 */             System.arraycopy(arrayOfMenuElement, 0, arrayOfMenuElement1, 0, b);
/* 1606 */             arrayOfMenuElement1[b] = JMenu.this.getPopupMenu();
/* 1607 */             MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement1);
/*      */           } 
/*      */         }  
/*      */     }
/*      */     
/*      */     public void selectAllAccessibleSelection() {}
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */