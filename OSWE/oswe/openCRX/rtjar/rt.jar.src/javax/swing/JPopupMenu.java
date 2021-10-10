/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.GraphicsDevice;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.security.AccessController;
/*      */ import java.util.Vector;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.swing.event.MenuKeyEvent;
/*      */ import javax.swing.event.MenuKeyListener;
/*      */ import javax.swing.event.PopupMenuEvent;
/*      */ import javax.swing.event.PopupMenuListener;
/*      */ import javax.swing.plaf.PopupMenuUI;
/*      */ import javax.swing.plaf.basic.BasicComboPopup;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JPopupMenu
/*      */   extends JComponent
/*      */   implements Accessible, MenuElement
/*      */ {
/*      */   private static final String uiClassID = "PopupMenuUI";
/*   98 */   private static final Object defaultLWPopupEnabledKey = new StringBuffer("JPopupMenu.defaultLWPopupEnabledKey");
/*      */   static boolean popupPostionFixDisabled = false;
/*      */   transient Component invoker;
/*      */   transient Popup popup;
/*      */   transient Frame frame;
/*      */   private int desiredLocationX;
/*      */   private int desiredLocationY;
/*      */   
/*      */   static {
/*  107 */     popupPostionFixDisabled = ((String)AccessController.<String>doPrivileged(new GetPropertyAction("javax.swing.adjustPopupLocationToFit", ""))).equals("false");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  133 */     classLock = new Object();
/*      */   }
/*      */ 
/*      */   
/*      */   private String label = null;
/*      */   
/*      */   private boolean paintBorder = true;
/*      */   
/*      */   private Insets margin = null;
/*      */   private boolean lightWeightPopup = true;
/*      */   private SingleSelectionModel selectionModel;
/*      */   private static final Object classLock;
/*      */   private static final boolean TRACE = false;
/*      */   private static final boolean VERBOSE = false;
/*      */   private static final boolean DEBUG = false;
/*      */   
/*      */   public static void setDefaultLightWeightPopupEnabled(boolean paramBoolean) {
/*  150 */     SwingUtilities.appContextPut(defaultLWPopupEnabledKey, 
/*  151 */         Boolean.valueOf(paramBoolean));
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
/*      */   public static boolean getDefaultLightWeightPopupEnabled() {
/*  165 */     Boolean bool = (Boolean)SwingUtilities.appContextGet(defaultLWPopupEnabledKey);
/*  166 */     if (bool == null) {
/*  167 */       SwingUtilities.appContextPut(defaultLWPopupEnabledKey, Boolean.TRUE);
/*      */       
/*  169 */       return true;
/*      */     } 
/*  171 */     return bool.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JPopupMenu() {
/*  178 */     this((String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JPopupMenu(String paramString) {
/*  188 */     this.label = paramString;
/*  189 */     this.lightWeightPopup = getDefaultLightWeightPopupEnabled();
/*  190 */     setSelectionModel(new DefaultSingleSelectionModel());
/*  191 */     enableEvents(16L);
/*  192 */     setFocusTraversalKeysEnabled(false);
/*  193 */     updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PopupMenuUI getUI() {
/*  204 */     return (PopupMenuUI)this.ui;
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
/*      */   public void setUI(PopupMenuUI paramPopupMenuUI) {
/*  219 */     setUI(paramPopupMenuUI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  228 */     setUI((PopupMenuUI)UIManager.getUI(this));
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
/*      */   public String getUIClassID() {
/*  240 */     return "PopupMenuUI";
/*      */   }
/*      */   
/*      */   protected void processFocusEvent(FocusEvent paramFocusEvent) {
/*  244 */     super.processFocusEvent(paramFocusEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processKeyEvent(KeyEvent paramKeyEvent) {
/*  253 */     MenuSelectionManager.defaultManager().processKeyEvent(paramKeyEvent);
/*  254 */     if (paramKeyEvent.isConsumed()) {
/*      */       return;
/*      */     }
/*  257 */     super.processKeyEvent(paramKeyEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SingleSelectionModel getSelectionModel() {
/*  268 */     return this.selectionModel;
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
/*      */   public void setSelectionModel(SingleSelectionModel paramSingleSelectionModel) {
/*  281 */     this.selectionModel = paramSingleSelectionModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenuItem add(JMenuItem paramJMenuItem) {
/*  291 */     add(paramJMenuItem);
/*  292 */     return paramJMenuItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenuItem add(String paramString) {
/*  302 */     return add(new JMenuItem(paramString));
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
/*      */   public JMenuItem add(Action paramAction) {
/*  314 */     JMenuItem jMenuItem = createActionComponent(paramAction);
/*  315 */     jMenuItem.setAction(paramAction);
/*  316 */     add(jMenuItem);
/*  317 */     return jMenuItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Point adjustPopupLocationToFitScreen(int paramInt1, int paramInt2) {
/*      */     Rectangle rectangle;
/*  328 */     Point point = new Point(paramInt1, paramInt2);
/*      */     
/*  330 */     if (popupPostionFixDisabled == true || GraphicsEnvironment.isHeadless()) {
/*  331 */       return point;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  336 */     GraphicsConfiguration graphicsConfiguration = getCurrentGraphicsConfiguration(point);
/*  337 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  338 */     if (graphicsConfiguration != null) {
/*      */       
/*  340 */       rectangle = graphicsConfiguration.getBounds();
/*      */     } else {
/*      */       
/*  343 */       rectangle = new Rectangle(toolkit.getScreenSize());
/*      */     } 
/*      */ 
/*      */     
/*  347 */     Dimension dimension = getPreferredSize();
/*  348 */     long l1 = point.x + dimension.width;
/*  349 */     long l2 = point.y + dimension.height;
/*  350 */     int i = rectangle.width;
/*  351 */     int j = rectangle.height;
/*      */     
/*  353 */     if (!canPopupOverlapTaskBar()) {
/*      */       
/*  355 */       Insets insets = toolkit.getScreenInsets(graphicsConfiguration);
/*  356 */       rectangle.x += insets.left;
/*  357 */       rectangle.y += insets.top;
/*  358 */       i -= insets.left + insets.right;
/*  359 */       j -= insets.top + insets.bottom;
/*      */     } 
/*  361 */     int k = rectangle.x + i;
/*  362 */     int m = rectangle.y + j;
/*      */ 
/*      */     
/*  365 */     if (l1 > k) {
/*  366 */       point.x = k - dimension.width;
/*      */     }
/*      */     
/*  369 */     if (l2 > m) {
/*  370 */       point.y = m - dimension.height;
/*      */     }
/*      */     
/*  373 */     if (point.x < rectangle.x) {
/*  374 */       point.x = rectangle.x;
/*      */     }
/*      */     
/*  377 */     if (point.y < rectangle.y) {
/*  378 */       point.y = rectangle.y;
/*      */     }
/*      */     
/*  381 */     return point;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GraphicsConfiguration getCurrentGraphicsConfiguration(Point paramPoint) {
/*  391 */     GraphicsConfiguration graphicsConfiguration = null;
/*      */     
/*  393 */     GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  394 */     GraphicsDevice[] arrayOfGraphicsDevice = graphicsEnvironment.getScreenDevices();
/*  395 */     for (byte b = 0; b < arrayOfGraphicsDevice.length; b++) {
/*  396 */       if (arrayOfGraphicsDevice[b].getType() == 0) {
/*      */         
/*  398 */         GraphicsConfiguration graphicsConfiguration1 = arrayOfGraphicsDevice[b].getDefaultConfiguration();
/*  399 */         if (graphicsConfiguration1.getBounds().contains(paramPoint)) {
/*  400 */           graphicsConfiguration = graphicsConfiguration1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  406 */     if (graphicsConfiguration == null && getInvoker() != null) {
/*  407 */       graphicsConfiguration = getInvoker().getGraphicsConfiguration();
/*      */     }
/*  409 */     return graphicsConfiguration;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean canPopupOverlapTaskBar() {
/*  416 */     boolean bool = true;
/*      */     
/*  418 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  419 */     if (toolkit instanceof SunToolkit) {
/*  420 */       bool = ((SunToolkit)toolkit).canPopupOverlapTaskBar();
/*      */     }
/*      */     
/*  423 */     return bool;
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
/*  437 */     JMenuItem jMenuItem = new JMenuItem() {
/*      */         protected PropertyChangeListener createActionPropertyChangeListener(Action param1Action) {
/*  439 */           PropertyChangeListener propertyChangeListener = JPopupMenu.this.createActionChangeListener(this);
/*  440 */           if (propertyChangeListener == null) {
/*  441 */             propertyChangeListener = super.createActionPropertyChangeListener(param1Action);
/*      */           }
/*  443 */           return propertyChangeListener;
/*      */         }
/*      */       };
/*  446 */     jMenuItem.setHorizontalTextPosition(11);
/*  447 */     jMenuItem.setVerticalTextPosition(0);
/*  448 */     return jMenuItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createActionChangeListener(JMenuItem paramJMenuItem) {
/*  456 */     return paramJMenuItem.createActionPropertyChangeListener0(paramJMenuItem.getAction());
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
/*      */   public void remove(int paramInt) {
/*  469 */     if (paramInt < 0) {
/*  470 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*  472 */     if (paramInt > getComponentCount() - 1) {
/*  473 */       throw new IllegalArgumentException("index greater than the number of items.");
/*      */     }
/*  475 */     super.remove(paramInt);
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
/*      */   public void setLightWeightPopupEnabled(boolean paramBoolean) {
/*  502 */     this.lightWeightPopup = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLightWeightPopupEnabled() {
/*  512 */     return this.lightWeightPopup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLabel() {
/*  522 */     return this.label;
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
/*      */   public void setLabel(String paramString) {
/*  537 */     String str = this.label;
/*  538 */     this.label = paramString;
/*  539 */     firePropertyChange("label", str, paramString);
/*  540 */     if (this.accessibleContext != null) {
/*  541 */       this.accessibleContext.firePropertyChange("AccessibleVisibleData", str, paramString);
/*      */     }
/*      */ 
/*      */     
/*  545 */     invalidate();
/*  546 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSeparator() {
/*  553 */     add(new Separator());
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
/*      */   public void insert(Action paramAction, int paramInt) {
/*  567 */     JMenuItem jMenuItem = createActionComponent(paramAction);
/*  568 */     jMenuItem.setAction(paramAction);
/*  569 */     insert(jMenuItem, paramInt);
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
/*      */   public void insert(Component paramComponent, int paramInt) {
/*  582 */     if (paramInt < 0) {
/*  583 */       throw new IllegalArgumentException("index less than zero.");
/*      */     }
/*      */     
/*  586 */     int i = getComponentCount();
/*      */     
/*  588 */     Vector<Component> vector = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  594 */     for (int j = paramInt; j < i; j++) {
/*  595 */       vector.addElement(getComponent(paramInt));
/*  596 */       remove(paramInt);
/*      */     } 
/*      */     
/*  599 */     add(paramComponent);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  604 */     for (Component component : vector) {
/*  605 */       add(component);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPopupMenuListener(PopupMenuListener paramPopupMenuListener) {
/*  615 */     this.listenerList.add(PopupMenuListener.class, paramPopupMenuListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePopupMenuListener(PopupMenuListener paramPopupMenuListener) {
/*  624 */     this.listenerList.remove(PopupMenuListener.class, paramPopupMenuListener);
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
/*      */   public PopupMenuListener[] getPopupMenuListeners() {
/*  636 */     return this.listenerList.<PopupMenuListener>getListeners(PopupMenuListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMenuKeyListener(MenuKeyListener paramMenuKeyListener) {
/*  646 */     this.listenerList.add(MenuKeyListener.class, paramMenuKeyListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeMenuKeyListener(MenuKeyListener paramMenuKeyListener) {
/*  656 */     this.listenerList.remove(MenuKeyListener.class, paramMenuKeyListener);
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
/*      */   public MenuKeyListener[] getMenuKeyListeners() {
/*  668 */     return this.listenerList.<MenuKeyListener>getListeners(MenuKeyListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePopupMenuWillBecomeVisible() {
/*  676 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  677 */     PopupMenuEvent popupMenuEvent = null;
/*  678 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  679 */       if (arrayOfObject[i] == PopupMenuListener.class) {
/*  680 */         if (popupMenuEvent == null)
/*  681 */           popupMenuEvent = new PopupMenuEvent(this); 
/*  682 */         ((PopupMenuListener)arrayOfObject[i + 1]).popupMenuWillBecomeVisible(popupMenuEvent);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePopupMenuWillBecomeInvisible() {
/*  692 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  693 */     PopupMenuEvent popupMenuEvent = null;
/*  694 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  695 */       if (arrayOfObject[i] == PopupMenuListener.class) {
/*  696 */         if (popupMenuEvent == null)
/*  697 */           popupMenuEvent = new PopupMenuEvent(this); 
/*  698 */         ((PopupMenuListener)arrayOfObject[i + 1]).popupMenuWillBecomeInvisible(popupMenuEvent);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePopupMenuCanceled() {
/*  708 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/*  709 */     PopupMenuEvent popupMenuEvent = null;
/*  710 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/*  711 */       if (arrayOfObject[i] == PopupMenuListener.class) {
/*  712 */         if (popupMenuEvent == null)
/*  713 */           popupMenuEvent = new PopupMenuEvent(this); 
/*  714 */         ((PopupMenuListener)arrayOfObject[i + 1]).popupMenuCanceled(popupMenuEvent);
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
/*      */   boolean alwaysOnTop() {
/*  726 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pack() {
/*  734 */     if (this.popup != null) {
/*  735 */       Dimension dimension = getPreferredSize();
/*      */       
/*  737 */       if (dimension == null || dimension.width != getWidth() || dimension.height != 
/*  738 */         getHeight()) {
/*  739 */         showPopup();
/*      */       } else {
/*  741 */         validate();
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
/*      */   public void setVisible(boolean paramBoolean) {
/*  761 */     if (paramBoolean == isVisible()) {
/*      */       return;
/*      */     }
/*      */     
/*  765 */     if (!paramBoolean) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  771 */       Boolean bool = (Boolean)getClientProperty("JPopupMenu.firePopupMenuCanceled");
/*  772 */       if (bool != null && bool == Boolean.TRUE) {
/*  773 */         putClientProperty("JPopupMenu.firePopupMenuCanceled", Boolean.FALSE);
/*  774 */         firePopupMenuCanceled();
/*      */       } 
/*  776 */       getSelectionModel().clearSelection();
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  781 */     else if (isPopupMenu()) {
/*  782 */       MenuElement[] arrayOfMenuElement = new MenuElement[1];
/*  783 */       arrayOfMenuElement[0] = this;
/*  784 */       MenuSelectionManager.defaultManager().setSelectedPath(arrayOfMenuElement);
/*      */     } 
/*      */ 
/*      */     
/*  788 */     if (paramBoolean) {
/*  789 */       firePopupMenuWillBecomeVisible();
/*  790 */       showPopup();
/*  791 */       firePropertyChange("visible", Boolean.FALSE, Boolean.TRUE);
/*      */     
/*      */     }
/*  794 */     else if (this.popup != null) {
/*  795 */       firePopupMenuWillBecomeInvisible();
/*  796 */       this.popup.hide();
/*  797 */       this.popup = null;
/*  798 */       firePropertyChange("visible", Boolean.TRUE, Boolean.FALSE);
/*      */ 
/*      */       
/*  801 */       if (isPopupMenu()) {
/*  802 */         MenuSelectionManager.defaultManager().clearSelectedPath();
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
/*      */   private void showPopup() {
/*  818 */     Popup popup1 = this.popup;
/*      */     
/*  820 */     if (popup1 != null) {
/*  821 */       popup1.hide();
/*      */     }
/*  823 */     PopupFactory popupFactory = PopupFactory.getSharedInstance();
/*      */     
/*  825 */     if (isLightWeightPopupEnabled()) {
/*  826 */       popupFactory.setPopupType(0);
/*      */     } else {
/*      */       
/*  829 */       popupFactory.setPopupType(2);
/*      */     } 
/*      */ 
/*      */     
/*  833 */     Point point = adjustPopupLocationToFitScreen(this.desiredLocationX, this.desiredLocationY);
/*  834 */     this.desiredLocationX = point.x;
/*  835 */     this.desiredLocationY = point.y;
/*      */     
/*  837 */     Popup popup2 = getUI().getPopup(this, this.desiredLocationX, this.desiredLocationY);
/*      */ 
/*      */     
/*  840 */     popupFactory.setPopupType(0);
/*  841 */     this.popup = popup2;
/*  842 */     popup2.show();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVisible() {
/*  850 */     return (this.popup != null);
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
/*      */   public void setLocation(int paramInt1, int paramInt2) {
/*  870 */     int i = this.desiredLocationX;
/*  871 */     int j = this.desiredLocationY;
/*      */     
/*  873 */     this.desiredLocationX = paramInt1;
/*  874 */     this.desiredLocationY = paramInt2;
/*  875 */     if (this.popup != null && (paramInt1 != i || paramInt2 != j)) {
/*  876 */       showPopup();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isPopupMenu() {
/*  887 */     return (this.invoker != null && !(this.invoker instanceof JMenu));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getInvoker() {
/*  897 */     return this.invoker;
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
/*      */   public void setInvoker(Component paramComponent) {
/*  911 */     Component component = this.invoker;
/*  912 */     this.invoker = paramComponent;
/*  913 */     if (component != this.invoker && this.ui != null) {
/*  914 */       this.ui.uninstallUI(this);
/*  915 */       this.ui.installUI(this);
/*      */     } 
/*  917 */     invalidate();
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
/*      */   public void show(Component paramComponent, int paramInt1, int paramInt2) {
/*  934 */     setInvoker(paramComponent);
/*  935 */     Frame frame = getFrame(paramComponent);
/*  936 */     if (frame != this.frame)
/*      */     {
/*      */       
/*  939 */       if (frame != null) {
/*  940 */         this.frame = frame;
/*  941 */         if (this.popup != null) {
/*  942 */           setVisible(false);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  947 */     if (paramComponent != null) {
/*  948 */       Point point = paramComponent.getLocationOnScreen();
/*      */ 
/*      */ 
/*      */       
/*  952 */       long l1 = point.x + paramInt1;
/*      */       
/*  954 */       long l2 = point.y + paramInt2;
/*      */       
/*  956 */       if (l1 > 2147483647L) l1 = 2147483647L; 
/*  957 */       if (l1 < -2147483648L) l1 = -2147483648L; 
/*  958 */       if (l2 > 2147483647L) l2 = 2147483647L; 
/*  959 */       if (l2 < -2147483648L) l2 = -2147483648L;
/*      */       
/*  961 */       setLocation((int)l1, (int)l2);
/*      */     } else {
/*  963 */       setLocation(paramInt1, paramInt2);
/*      */     } 
/*  965 */     setVisible(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JPopupMenu getRootPopupMenu() {
/*  975 */     JPopupMenu jPopupMenu = this;
/*  976 */     while (jPopupMenu != null && jPopupMenu.isPopupMenu() != true && jPopupMenu
/*  977 */       .getInvoker() != null && jPopupMenu
/*  978 */       .getInvoker().getParent() != null && jPopupMenu
/*  979 */       .getInvoker().getParent() instanceof JPopupMenu)
/*      */     {
/*  981 */       jPopupMenu = (JPopupMenu)jPopupMenu.getInvoker().getParent();
/*      */     }
/*  983 */     return jPopupMenu;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Component getComponentAtIndex(int paramInt) {
/*  995 */     return getComponent(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getComponentIndex(Component paramComponent) {
/* 1006 */     int i = getComponentCount();
/* 1007 */     Component[] arrayOfComponent = getComponents();
/* 1008 */     for (byte b = 0; b < i; b++) {
/* 1009 */       Component component = arrayOfComponent[b];
/* 1010 */       if (component == paramComponent)
/* 1011 */         return b; 
/*      */     } 
/* 1013 */     return -1;
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
/*      */   public void setPopupSize(Dimension paramDimension) {
/* 1026 */     Dimension dimension = getPreferredSize();
/*      */     
/* 1028 */     setPreferredSize(paramDimension);
/* 1029 */     if (this.popup != null) {
/* 1030 */       Dimension dimension1 = getPreferredSize();
/*      */       
/* 1032 */       if (!dimension.equals(dimension1)) {
/* 1033 */         showPopup();
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
/*      */   public void setPopupSize(int paramInt1, int paramInt2) {
/* 1049 */     setPopupSize(new Dimension(paramInt1, paramInt2));
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
/*      */   public void setSelected(Component paramComponent) {
/* 1063 */     SingleSelectionModel singleSelectionModel = getSelectionModel();
/* 1064 */     int i = getComponentIndex(paramComponent);
/* 1065 */     singleSelectionModel.setSelectedIndex(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBorderPainted() {
/* 1075 */     return this.paintBorder;
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
/*      */   public void setBorderPainted(boolean paramBoolean) {
/* 1087 */     this.paintBorder = paramBoolean;
/* 1088 */     repaint();
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
/*      */   protected void paintBorder(Graphics paramGraphics) {
/* 1100 */     if (isBorderPainted()) {
/* 1101 */       super.paintBorder(paramGraphics);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getMargin() {
/* 1112 */     if (this.margin == null) {
/* 1113 */       return new Insets(0, 0, 0, 0);
/*      */     }
/* 1115 */     return this.margin;
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
/*      */   boolean isSubPopupMenu(JPopupMenu paramJPopupMenu) {
/* 1128 */     int i = getComponentCount();
/* 1129 */     Component[] arrayOfComponent = getComponents();
/* 1130 */     for (byte b = 0; b < i; b++) {
/* 1131 */       Component component = arrayOfComponent[b];
/* 1132 */       if (component instanceof JMenu) {
/* 1133 */         JMenu jMenu = (JMenu)component;
/* 1134 */         JPopupMenu jPopupMenu = jMenu.getPopupMenu();
/* 1135 */         if (jPopupMenu == paramJPopupMenu)
/* 1136 */           return true; 
/* 1137 */         if (jPopupMenu.isSubPopupMenu(paramJPopupMenu))
/* 1138 */           return true; 
/*      */       } 
/*      */     } 
/* 1141 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Frame getFrame(Component paramComponent) {
/* 1146 */     Component component = paramComponent;
/*      */     
/* 1148 */     while (!(component instanceof Frame) && component != null) {
/* 1149 */       component = component.getParent();
/*      */     }
/* 1151 */     return (Frame)component;
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
/*      */   protected String paramString() {
/* 1166 */     String str1 = (this.label != null) ? this.label : "";
/*      */     
/* 1168 */     String str2 = this.paintBorder ? "true" : "false";
/*      */ 
/*      */     
/* 1171 */     String str3 = (this.margin != null) ? this.margin.toString() : "";
/* 1172 */     String str4 = isLightWeightPopupEnabled() ? "true" : "false";
/*      */     
/* 1174 */     return super.paramString() + ",desiredLocationX=" + this.desiredLocationX + ",desiredLocationY=" + this.desiredLocationY + ",label=" + str1 + ",lightWeightPopupEnabled=" + str4 + ",margin=" + str3 + ",paintBorder=" + str2;
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1197 */     if (this.accessibleContext == null) {
/* 1198 */       this.accessibleContext = new AccessibleJPopupMenu();
/*      */     }
/* 1200 */     return this.accessibleContext;
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
/*      */   protected class AccessibleJPopupMenu
/*      */     extends JComponent.AccessibleJComponent
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     protected AccessibleJPopupMenu() {
/* 1219 */       JPopupMenu.this.addPropertyChangeListener(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleRole getAccessibleRole() {
/* 1229 */       return AccessibleRole.POPUP_MENU;
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
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1241 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1242 */       if (str == "visible") {
/* 1243 */         if (param1PropertyChangeEvent.getOldValue() == Boolean.FALSE && param1PropertyChangeEvent
/* 1244 */           .getNewValue() == Boolean.TRUE) {
/* 1245 */           handlePopupIsVisibleEvent(true);
/*      */         }
/* 1247 */         else if (param1PropertyChangeEvent.getOldValue() == Boolean.TRUE && param1PropertyChangeEvent
/* 1248 */           .getNewValue() == Boolean.FALSE) {
/* 1249 */           handlePopupIsVisibleEvent(false);
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void handlePopupIsVisibleEvent(boolean param1Boolean) {
/* 1258 */       if (param1Boolean) {
/*      */         
/* 1260 */         firePropertyChange("AccessibleState", null, AccessibleState.VISIBLE);
/*      */ 
/*      */         
/* 1263 */         fireActiveDescendant();
/*      */       } else {
/*      */         
/* 1266 */         firePropertyChange("AccessibleState", AccessibleState.VISIBLE, null);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void fireActiveDescendant() {
/* 1276 */       if (JPopupMenu.this instanceof BasicComboPopup) {
/*      */         
/* 1278 */         JList<Object> jList = ((BasicComboPopup)JPopupMenu.this).getList();
/* 1279 */         if (jList == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1284 */         AccessibleContext accessibleContext1 = jList.getAccessibleContext();
/* 1285 */         AccessibleSelection accessibleSelection = accessibleContext1.getAccessibleSelection();
/* 1286 */         if (accessibleSelection == null) {
/*      */           return;
/*      */         }
/* 1289 */         Accessible accessible = accessibleSelection.getAccessibleSelection(0);
/* 1290 */         if (accessible == null) {
/*      */           return;
/*      */         }
/* 1293 */         AccessibleContext accessibleContext2 = accessible.getAccessibleContext();
/*      */ 
/*      */         
/* 1296 */         if (accessibleContext2 != null && JPopupMenu.this.invoker != null) {
/* 1297 */           AccessibleContext accessibleContext = JPopupMenu.this.invoker.getAccessibleContext();
/* 1298 */           if (accessibleContext != null)
/*      */           {
/*      */ 
/*      */             
/* 1302 */             accessibleContext.firePropertyChange("AccessibleActiveDescendant", null, accessibleContext2);
/*      */           }
/*      */         } 
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
/* 1316 */     Vector<String> vector = new Vector();
/*      */     
/* 1318 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1320 */     if (this.invoker != null && this.invoker instanceof java.io.Serializable) {
/* 1321 */       vector.addElement("invoker");
/* 1322 */       vector.addElement(this.invoker);
/*      */     } 
/*      */     
/* 1325 */     if (this.popup != null && this.popup instanceof java.io.Serializable) {
/* 1326 */       vector.addElement("popup");
/* 1327 */       vector.addElement(this.popup);
/*      */     } 
/* 1329 */     paramObjectOutputStream.writeObject(vector);
/*      */     
/* 1331 */     if (getUIClassID().equals("PopupMenuUI")) {
/* 1332 */       byte b = JComponent.getWriteObjCounter(this);
/* 1333 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 1334 */       if (b == 0 && this.ui != null) {
/* 1335 */         this.ui.installUI(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1343 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1345 */     Vector<E> vector = (Vector)paramObjectInputStream.readObject();
/* 1346 */     byte b = 0;
/* 1347 */     int i = vector.size();
/*      */     
/* 1349 */     if (b < i && vector.elementAt(b)
/* 1350 */       .equals("invoker")) {
/* 1351 */       this.invoker = (Component)vector.elementAt(++b);
/* 1352 */       b++;
/*      */     } 
/* 1354 */     if (b < i && vector.elementAt(b)
/* 1355 */       .equals("popup")) {
/* 1356 */       this.popup = (Popup)vector.elementAt(++b);
/* 1357 */       b++;
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
/*      */   public void processMouseEvent(MouseEvent paramMouseEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeyEvent(KeyEvent paramKeyEvent, MenuElement[] paramArrayOfMenuElement, MenuSelectionManager paramMenuSelectionManager) {
/* 1385 */     MenuKeyEvent menuKeyEvent = new MenuKeyEvent(paramKeyEvent.getComponent(), paramKeyEvent.getID(), paramKeyEvent.getWhen(), paramKeyEvent.getModifiers(), paramKeyEvent.getKeyCode(), paramKeyEvent.getKeyChar(), paramArrayOfMenuElement, paramMenuSelectionManager);
/*      */     
/* 1387 */     processMenuKeyEvent(menuKeyEvent);
/*      */     
/* 1389 */     if (menuKeyEvent.isConsumed()) {
/* 1390 */       paramKeyEvent.consume();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processMenuKeyEvent(MenuKeyEvent paramMenuKeyEvent) {
/* 1401 */     switch (paramMenuKeyEvent.getID()) {
/*      */       case 401:
/* 1403 */         fireMenuKeyPressed(paramMenuKeyEvent); break;
/*      */       case 402:
/* 1405 */         fireMenuKeyReleased(paramMenuKeyEvent); break;
/*      */       case 400:
/* 1407 */         fireMenuKeyTyped(paramMenuKeyEvent);
/*      */         break;
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
/*      */   private void fireMenuKeyPressed(MenuKeyEvent paramMenuKeyEvent) {
/* 1421 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1422 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1423 */       if (arrayOfObject[i] == MenuKeyListener.class) {
/* 1424 */         ((MenuKeyListener)arrayOfObject[i + 1]).menuKeyPressed(paramMenuKeyEvent);
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
/*      */   private void fireMenuKeyReleased(MenuKeyEvent paramMenuKeyEvent) {
/* 1437 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1438 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1439 */       if (arrayOfObject[i] == MenuKeyListener.class) {
/* 1440 */         ((MenuKeyListener)arrayOfObject[i + 1]).menuKeyReleased(paramMenuKeyEvent);
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
/*      */   private void fireMenuKeyTyped(MenuKeyEvent paramMenuKeyEvent) {
/* 1453 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1454 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1455 */       if (arrayOfObject[i] == MenuKeyListener.class) {
/* 1456 */         ((MenuKeyListener)arrayOfObject[i + 1]).menuKeyTyped(paramMenuKeyEvent);
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
/*      */   public void menuSelectionChanged(boolean paramBoolean) {
/* 1475 */     if (this.invoker instanceof JMenu) {
/* 1476 */       JMenu jMenu = (JMenu)this.invoker;
/* 1477 */       if (paramBoolean) {
/* 1478 */         jMenu.setPopupMenuVisible(true);
/*      */       } else {
/* 1480 */         jMenu.setPopupMenuVisible(false);
/*      */       } 
/* 1482 */     }  if (isPopupMenu() && !paramBoolean) {
/* 1483 */       setVisible(false);
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
/*      */   public MenuElement[] getSubElements() {
/* 1499 */     Vector<MenuElement> vector = new Vector();
/* 1500 */     int i = getComponentCount();
/*      */     
/*      */     byte b;
/*      */     
/* 1504 */     for (b = 0; b < i; b++) {
/* 1505 */       Component component = getComponent(b);
/* 1506 */       if (component instanceof MenuElement) {
/* 1507 */         vector.addElement((MenuElement)component);
/*      */       }
/*      */     } 
/* 1510 */     MenuElement[] arrayOfMenuElement = new MenuElement[vector.size()];
/* 1511 */     for (b = 0, i = vector.size(); b < i; b++)
/* 1512 */       arrayOfMenuElement[b] = vector.elementAt(b); 
/* 1513 */     return arrayOfMenuElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getComponent() {
/* 1522 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Separator
/*      */     extends JSeparator
/*      */   {
/*      */     public Separator() {
/* 1534 */       super(0);
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
/*      */     public String getUIClassID() {
/* 1546 */       return "PopupMenuSeparatorUI";
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
/*      */   public boolean isPopupTrigger(MouseEvent paramMouseEvent) {
/* 1559 */     return getUI().isPopupTrigger(paramMouseEvent);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JPopupMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */