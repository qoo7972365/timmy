/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.ComponentOrientation;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionAdapter;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.MouseWheelListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.Serializable;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JScrollBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.MenuElement;
/*      */ import javax.swing.MenuSelectionManager;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.LineBorder;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.ListSelectionListener;
/*      */ import sun.awt.AWTAccessor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicComboPopup
/*      */   extends JPopupMenu
/*      */   implements ComboPopup
/*      */ {
/*      */   private static class EmptyListModelClass
/*      */     implements ListModel<Object>, Serializable
/*      */   {
/*      */     private EmptyListModelClass() {}
/*      */     
/*      */     public int getSize() {
/*   69 */       return 0; } public Object getElementAt(int param1Int) {
/*   70 */       return null;
/*      */     }
/*      */     public void addListDataListener(ListDataListener param1ListDataListener) {}
/*      */     public void removeListDataListener(ListDataListener param1ListDataListener) {} }
/*      */   
/*   75 */   static final ListModel EmptyListModel = new EmptyListModelClass();
/*      */   
/*   77 */   private static Border LIST_BORDER = new LineBorder(Color.BLACK, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JComboBox comboBox;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JList list;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JScrollPane scroller;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean valueIsAdjusting = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseMotionListener mouseMotionListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseListener mouseListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener keyListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListSelectionListener listSelectionListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseListener listMouseListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseMotionListener listMouseMotionListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener propertyChangeListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListDataListener listDataListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ItemListener itemListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MouseWheelListener scrollerMouseWheelListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Timer autoscrollTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean hasEntered = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isAutoScrolling = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  194 */   protected int scrollDirection = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int SCROLL_UP = 0;
/*      */ 
/*      */ 
/*      */   
/*      */   protected static final int SCROLL_DOWN = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void show() {
/*  208 */     this.comboBox.firePopupMenuWillBecomeVisible();
/*  209 */     setListSelection(this.comboBox.getSelectedIndex());
/*  210 */     Point point = getPopupLocation();
/*  211 */     show(this.comboBox, point.x, point.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hide() {
/*  219 */     MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
/*  220 */     MenuElement[] arrayOfMenuElement = menuSelectionManager.getSelectedPath();
/*  221 */     for (byte b = 0; b < arrayOfMenuElement.length; b++) {
/*  222 */       if (arrayOfMenuElement[b] == this) {
/*  223 */         menuSelectionManager.clearSelectedPath();
/*      */         break;
/*      */       } 
/*      */     } 
/*  227 */     if (arrayOfMenuElement.length > 0) {
/*  228 */       this.comboBox.repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JList getList() {
/*  236 */     return this.list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MouseListener getMouseListener() {
/*  246 */     if (this.mouseListener == null) {
/*  247 */       this.mouseListener = createMouseListener();
/*      */     }
/*  249 */     return this.mouseListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MouseMotionListener getMouseMotionListener() {
/*  259 */     if (this.mouseMotionListener == null) {
/*  260 */       this.mouseMotionListener = createMouseMotionListener();
/*      */     }
/*  262 */     return this.mouseMotionListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyListener getKeyListener() {
/*  272 */     if (this.keyListener == null) {
/*  273 */       this.keyListener = createKeyListener();
/*      */     }
/*  275 */     return this.keyListener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallingUI() {
/*  284 */     if (this.propertyChangeListener != null) {
/*  285 */       this.comboBox.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  287 */     if (this.itemListener != null) {
/*  288 */       this.comboBox.removeItemListener(this.itemListener);
/*      */     }
/*  290 */     uninstallComboBoxModelListeners(this.comboBox.getModel());
/*  291 */     uninstallKeyboardActions();
/*  292 */     uninstallListListeners();
/*  293 */     uninstallScrollerListeners();
/*      */ 
/*      */ 
/*      */     
/*  297 */     this.list.setModel(EmptyListModel);
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
/*      */   protected void uninstallComboBoxModelListeners(ComboBoxModel paramComboBoxModel) {
/*  311 */     if (paramComboBoxModel != null && this.listDataListener != null) {
/*  312 */       paramComboBoxModel.removeListDataListener(this.listDataListener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicComboPopup(JComboBox paramJComboBox) {
/*  328 */     setName("ComboPopup.popup");
/*  329 */     this.comboBox = paramJComboBox;
/*      */     
/*  331 */     setLightWeightPopupEnabled(this.comboBox.isLightWeightPopupEnabled());
/*      */ 
/*      */     
/*  334 */     this.list = createList();
/*  335 */     this.list.setName("ComboBox.list");
/*  336 */     configureList();
/*  337 */     this.scroller = createScroller();
/*  338 */     this.scroller.setName("ComboBox.scrollPane");
/*  339 */     configureScroller();
/*  340 */     configurePopup();
/*      */     
/*  342 */     installComboBoxListeners();
/*  343 */     installKeyboardActions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePopupMenuWillBecomeVisible() {
/*  350 */     if (this.scrollerMouseWheelListener != null) {
/*  351 */       this.comboBox.addMouseWheelListener(this.scrollerMouseWheelListener);
/*      */     }
/*  353 */     super.firePopupMenuWillBecomeVisible();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePopupMenuWillBecomeInvisible() {
/*  359 */     if (this.scrollerMouseWheelListener != null) {
/*  360 */       this.comboBox.removeMouseWheelListener(this.scrollerMouseWheelListener);
/*      */     }
/*  362 */     super.firePopupMenuWillBecomeInvisible();
/*  363 */     this.comboBox.firePopupMenuWillBecomeInvisible();
/*      */   }
/*      */   
/*      */   protected void firePopupMenuCanceled() {
/*  367 */     if (this.scrollerMouseWheelListener != null) {
/*  368 */       this.comboBox.removeMouseWheelListener(this.scrollerMouseWheelListener);
/*      */     }
/*  370 */     super.firePopupMenuCanceled();
/*  371 */     this.comboBox.firePopupMenuCanceled();
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
/*      */   protected MouseListener createMouseListener() {
/*  386 */     return getHandler();
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
/*      */   protected MouseMotionListener createMouseMotionListener() {
/*  401 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener createKeyListener() {
/*  411 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListSelectionListener createListSelectionListener() {
/*  422 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListDataListener createListDataListener() {
/*  433 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseListener createListMouseListener() {
/*  444 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseMotionListener createListMouseMotionListener() {
/*  455 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  466 */     return getHandler();
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
/*      */   protected ItemListener createItemListener() {
/*  480 */     return getHandler();
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  484 */     if (this.handler == null) {
/*  485 */       this.handler = new Handler();
/*      */     }
/*  487 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JList createList() {
/*  498 */     return new JList(this.comboBox.getModel()) {
/*      */         public void processMouseEvent(MouseEvent param1MouseEvent) {
/*  500 */           if (BasicGraphicsUtils.isMenuShortcutKeyDown(param1MouseEvent)) {
/*      */ 
/*      */             
/*  503 */             Toolkit toolkit = Toolkit.getDefaultToolkit();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  510 */             MouseEvent mouseEvent = new MouseEvent((Component)param1MouseEvent.getSource(), param1MouseEvent.getID(), param1MouseEvent.getWhen(), param1MouseEvent.getModifiers() ^ toolkit.getMenuShortcutKeyMask(), param1MouseEvent.getX(), param1MouseEvent.getY(), param1MouseEvent.getXOnScreen(), param1MouseEvent.getYOnScreen(), param1MouseEvent.getClickCount(), param1MouseEvent.isPopupTrigger(), 0);
/*      */             
/*  512 */             AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/*  513 */             mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/*  514 */                 .isCausedByTouchEvent(param1MouseEvent));
/*  515 */             param1MouseEvent = mouseEvent;
/*      */           } 
/*  517 */           super.processMouseEvent(param1MouseEvent);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureList() {
/*  530 */     this.list.setFont(this.comboBox.getFont());
/*  531 */     this.list.setForeground(this.comboBox.getForeground());
/*  532 */     this.list.setBackground(this.comboBox.getBackground());
/*  533 */     this.list.setSelectionForeground(UIManager.getColor("ComboBox.selectionForeground"));
/*  534 */     this.list.setSelectionBackground(UIManager.getColor("ComboBox.selectionBackground"));
/*  535 */     this.list.setBorder((Border)null);
/*  536 */     this.list.setCellRenderer(this.comboBox.getRenderer());
/*  537 */     this.list.setFocusable(false);
/*  538 */     this.list.setSelectionMode(0);
/*  539 */     setListSelection(this.comboBox.getSelectedIndex());
/*  540 */     installListListeners();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListListeners() {
/*  547 */     if ((this.listMouseListener = createListMouseListener()) != null) {
/*  548 */       this.list.addMouseListener(this.listMouseListener);
/*      */     }
/*  550 */     if ((this.listMouseMotionListener = createListMouseMotionListener()) != null) {
/*  551 */       this.list.addMouseMotionListener(this.listMouseMotionListener);
/*      */     }
/*  553 */     if ((this.listSelectionListener = createListSelectionListener()) != null) {
/*  554 */       this.list.addListSelectionListener(this.listSelectionListener);
/*      */     }
/*      */   }
/*      */   
/*      */   void uninstallListListeners() {
/*  559 */     if (this.listMouseListener != null) {
/*  560 */       this.list.removeMouseListener(this.listMouseListener);
/*  561 */       this.listMouseListener = null;
/*      */     } 
/*  563 */     if (this.listMouseMotionListener != null) {
/*  564 */       this.list.removeMouseMotionListener(this.listMouseMotionListener);
/*  565 */       this.listMouseMotionListener = null;
/*      */     } 
/*  567 */     if (this.listSelectionListener != null) {
/*  568 */       this.list.removeListSelectionListener(this.listSelectionListener);
/*  569 */       this.listSelectionListener = null;
/*      */     } 
/*  571 */     this.handler = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JScrollPane createScroller() {
/*  578 */     JScrollPane jScrollPane = new JScrollPane(this.list, 20, 31);
/*      */ 
/*      */     
/*  581 */     jScrollPane.setHorizontalScrollBar((JScrollBar)null);
/*  582 */     return jScrollPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configureScroller() {
/*  591 */     this.scroller.setFocusable(false);
/*  592 */     this.scroller.getVerticalScrollBar().setFocusable(false);
/*  593 */     this.scroller.setBorder((Border)null);
/*  594 */     installScrollerListeners();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void configurePopup() {
/*  602 */     setLayout(new BoxLayout(this, 1));
/*  603 */     setBorderPainted(true);
/*  604 */     setBorder(LIST_BORDER);
/*  605 */     setOpaque(false);
/*  606 */     add(this.scroller);
/*  607 */     setDoubleBuffered(true);
/*  608 */     setFocusable(false);
/*      */   }
/*      */   
/*      */   private void installScrollerListeners() {
/*  612 */     this.scrollerMouseWheelListener = getHandler();
/*  613 */     if (this.scrollerMouseWheelListener != null) {
/*  614 */       this.scroller.addMouseWheelListener(this.scrollerMouseWheelListener);
/*      */     }
/*      */   }
/*      */   
/*      */   private void uninstallScrollerListeners() {
/*  619 */     if (this.scrollerMouseWheelListener != null) {
/*  620 */       this.scroller.removeMouseWheelListener(this.scrollerMouseWheelListener);
/*  621 */       this.scrollerMouseWheelListener = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installComboBoxListeners() {
/*  629 */     if ((this.propertyChangeListener = createPropertyChangeListener()) != null) {
/*  630 */       this.comboBox.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  632 */     if ((this.itemListener = createItemListener()) != null) {
/*  633 */       this.comboBox.addItemListener(this.itemListener);
/*      */     }
/*  635 */     installComboBoxModelListeners(this.comboBox.getModel());
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
/*      */   protected void installComboBoxModelListeners(ComboBoxModel paramComboBoxModel) {
/*  647 */     if (paramComboBoxModel != null && (this.listDataListener = createListDataListener()) != null) {
/*  648 */       paramComboBoxModel.addListDataListener(this.listDataListener);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class InvocationMouseHandler
/*      */     extends MouseAdapter
/*      */   {
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  703 */       BasicComboPopup.this.getHandler().mousePressed(param1MouseEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  713 */       BasicComboPopup.this.getHandler().mouseReleased(param1MouseEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class InvocationMouseMotionHandler
/*      */     extends MouseMotionAdapter
/*      */   {
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/*  723 */       BasicComboPopup.this.getHandler().mouseDragged(param1MouseEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class InvocationKeyHandler
/*      */     extends KeyAdapter
/*      */   {
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ListSelectionHandler
/*      */     implements ListSelectionListener
/*      */   {
/*      */     public void valueChanged(ListSelectionEvent param1ListSelectionEvent) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class ListDataHandler
/*      */     implements ListDataListener
/*      */   {
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ListMouseHandler
/*      */     extends MouseAdapter
/*      */   {
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  773 */       BasicComboPopup.this.getHandler().mouseReleased(param1MouseEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ListMouseMotionHandler
/*      */     extends MouseMotionAdapter
/*      */   {
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/*  783 */       BasicComboPopup.this.getHandler().mouseMoved(param1MouseEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ItemHandler
/*      */     implements ItemListener
/*      */   {
/*      */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/*  793 */       BasicComboPopup.this.getHandler().itemStateChanged(param1ItemEvent);
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
/*      */   protected class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  809 */       BasicComboPopup.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private class AutoScrollActionHandler
/*      */     implements ActionListener {
/*      */     private int direction;
/*      */     
/*      */     AutoScrollActionHandler(int param1Int) {
/*  818 */       this.direction = param1Int;
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  822 */       if (this.direction == 0) {
/*  823 */         BasicComboPopup.this.autoScrollUp();
/*      */       } else {
/*      */         
/*  826 */         BasicComboPopup.this.autoScrollDown();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements ItemListener, MouseListener, MouseMotionListener, MouseWheelListener, PropertyChangeListener, Serializable
/*      */   {
/*      */     private Handler() {}
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  843 */       if (param1MouseEvent.getSource() == BasicComboPopup.this.list) {
/*      */         return;
/*      */       }
/*  846 */       if (!SwingUtilities.isLeftMouseButton(param1MouseEvent) || !BasicComboPopup.this.comboBox.isEnabled()) {
/*      */         return;
/*      */       }
/*  849 */       if (BasicComboPopup.this.comboBox.isEditable()) {
/*  850 */         Component component = BasicComboPopup.this.comboBox.getEditor().getEditorComponent();
/*  851 */         if (!(component instanceof JComponent) || ((JComponent)component).isRequestFocusEnabled()) {
/*  852 */           component.requestFocus();
/*      */         }
/*      */       }
/*  855 */       else if (BasicComboPopup.this.comboBox.isRequestFocusEnabled()) {
/*  856 */         BasicComboPopup.this.comboBox.requestFocus();
/*      */       } 
/*  858 */       BasicComboPopup.this.togglePopup();
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  862 */       if (param1MouseEvent.getSource() == BasicComboPopup.this.list) {
/*  863 */         if (BasicComboPopup.this.list.getModel().getSize() > 0) {
/*      */           
/*  865 */           if (BasicComboPopup.this.comboBox.getSelectedIndex() == BasicComboPopup.this.list.getSelectedIndex()) {
/*  866 */             BasicComboPopup.this.comboBox.getEditor().setItem(BasicComboPopup.this.list.getSelectedValue());
/*      */           }
/*  868 */           BasicComboPopup.this.comboBox.setSelectedIndex(BasicComboPopup.this.list.getSelectedIndex());
/*      */         } 
/*  870 */         BasicComboPopup.this.comboBox.setPopupVisible(false);
/*      */         
/*  872 */         if (BasicComboPopup.this.comboBox.isEditable() && BasicComboPopup.this.comboBox.getEditor() != null) {
/*  873 */           BasicComboPopup.this.comboBox.configureEditor(BasicComboPopup.this.comboBox.getEditor(), BasicComboPopup.this.comboBox
/*  874 */               .getSelectedItem());
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*  879 */       Component component = (Component)param1MouseEvent.getSource();
/*  880 */       Dimension dimension = component.getSize();
/*  881 */       Rectangle rectangle = new Rectangle(0, 0, dimension.width - 1, dimension.height - 1);
/*  882 */       if (!rectangle.contains(param1MouseEvent.getPoint())) {
/*  883 */         MouseEvent mouseEvent = BasicComboPopup.this.convertMouseEvent(param1MouseEvent);
/*  884 */         Point point = mouseEvent.getPoint();
/*  885 */         Rectangle rectangle1 = new Rectangle();
/*  886 */         BasicComboPopup.this.list.computeVisibleRect(rectangle1);
/*  887 */         if (rectangle1.contains(point)) {
/*  888 */           if (BasicComboPopup.this.comboBox.getSelectedIndex() == BasicComboPopup.this.list.getSelectedIndex()) {
/*  889 */             BasicComboPopup.this.comboBox.getEditor().setItem(BasicComboPopup.this.list.getSelectedValue());
/*      */           }
/*  891 */           BasicComboPopup.this.comboBox.setSelectedIndex(BasicComboPopup.this.list.getSelectedIndex());
/*      */         } 
/*  893 */         BasicComboPopup.this.comboBox.setPopupVisible(false);
/*      */       } 
/*  895 */       BasicComboPopup.this.hasEntered = false;
/*  896 */       BasicComboPopup.this.stopAutoScrolling();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/*  910 */       if (param1MouseEvent.getSource() == BasicComboPopup.this.list) {
/*  911 */         Point point = param1MouseEvent.getPoint();
/*  912 */         Rectangle rectangle = new Rectangle();
/*  913 */         BasicComboPopup.this.list.computeVisibleRect(rectangle);
/*  914 */         if (rectangle.contains(point)) {
/*  915 */           BasicComboPopup.this.updateListBoxSelectionForEvent(param1MouseEvent, false);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/*  921 */       if (param1MouseEvent.getSource() == BasicComboPopup.this.list) {
/*      */         return;
/*      */       }
/*  924 */       if (BasicComboPopup.this.isVisible()) {
/*  925 */         MouseEvent mouseEvent = BasicComboPopup.this.convertMouseEvent(param1MouseEvent);
/*  926 */         Rectangle rectangle = new Rectangle();
/*  927 */         BasicComboPopup.this.list.computeVisibleRect(rectangle);
/*      */         
/*  929 */         if ((mouseEvent.getPoint()).y >= rectangle.y && (mouseEvent.getPoint()).y <= rectangle.y + rectangle.height - 1) {
/*  930 */           BasicComboPopup.this.hasEntered = true;
/*  931 */           if (BasicComboPopup.this.isAutoScrolling) {
/*  932 */             BasicComboPopup.this.stopAutoScrolling();
/*      */           }
/*  934 */           Point point = mouseEvent.getPoint();
/*  935 */           if (rectangle.contains(point)) {
/*  936 */             BasicComboPopup.this.updateListBoxSelectionForEvent(mouseEvent, false);
/*      */           
/*      */           }
/*      */         }
/*  940 */         else if (BasicComboPopup.this.hasEntered) {
/*  941 */           boolean bool = ((mouseEvent.getPoint()).y < rectangle.y) ? false : true;
/*  942 */           if (BasicComboPopup.this.isAutoScrolling && BasicComboPopup.this.scrollDirection != bool) {
/*  943 */             BasicComboPopup.this.stopAutoScrolling();
/*  944 */             BasicComboPopup.this.startAutoScrolling(bool);
/*      */           }
/*  946 */           else if (!BasicComboPopup.this.isAutoScrolling) {
/*  947 */             BasicComboPopup.this.startAutoScrolling(bool);
/*      */           }
/*      */         
/*      */         }
/*  951 */         else if ((param1MouseEvent.getPoint()).y < 0) {
/*  952 */           BasicComboPopup.this.hasEntered = true;
/*  953 */           BasicComboPopup.this.startAutoScrolling(0);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  964 */       JComboBox jComboBox = (JComboBox)param1PropertyChangeEvent.getSource();
/*  965 */       String str = param1PropertyChangeEvent.getPropertyName();
/*      */       
/*  967 */       if (str == "model") {
/*  968 */         ComboBoxModel comboBoxModel1 = (ComboBoxModel)param1PropertyChangeEvent.getOldValue();
/*  969 */         ComboBoxModel comboBoxModel2 = (ComboBoxModel)param1PropertyChangeEvent.getNewValue();
/*  970 */         BasicComboPopup.this.uninstallComboBoxModelListeners(comboBoxModel1);
/*  971 */         BasicComboPopup.this.installComboBoxModelListeners(comboBoxModel2);
/*      */         
/*  973 */         BasicComboPopup.this.list.setModel(comboBoxModel2);
/*      */         
/*  975 */         if (BasicComboPopup.this.isVisible()) {
/*  976 */           BasicComboPopup.this.hide();
/*      */         }
/*      */       }
/*  979 */       else if (str == "renderer") {
/*  980 */         BasicComboPopup.this.list.setCellRenderer(jComboBox.getRenderer());
/*  981 */         if (BasicComboPopup.this.isVisible()) {
/*  982 */           BasicComboPopup.this.hide();
/*      */         }
/*      */       }
/*  985 */       else if (str == "componentOrientation") {
/*      */ 
/*      */ 
/*      */         
/*  989 */         ComponentOrientation componentOrientation = (ComponentOrientation)param1PropertyChangeEvent.getNewValue();
/*      */         
/*  991 */         JList jList = BasicComboPopup.this.getList();
/*  992 */         if (jList != null && jList.getComponentOrientation() != componentOrientation) {
/*  993 */           jList.setComponentOrientation(componentOrientation);
/*      */         }
/*      */         
/*  996 */         if (BasicComboPopup.this.scroller != null && BasicComboPopup.this.scroller.getComponentOrientation() != componentOrientation) {
/*  997 */           BasicComboPopup.this.scroller.setComponentOrientation(componentOrientation);
/*      */         }
/*      */         
/* 1000 */         if (componentOrientation != BasicComboPopup.this.getComponentOrientation()) {
/* 1001 */           BasicComboPopup.this.setComponentOrientation(componentOrientation);
/*      */         }
/*      */       }
/* 1004 */       else if (str == "lightWeightPopupEnabled") {
/* 1005 */         BasicComboPopup.this.setLightWeightPopupEnabled(jComboBox.isLightWeightPopupEnabled());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void itemStateChanged(ItemEvent param1ItemEvent) {
/* 1013 */       if (param1ItemEvent.getStateChange() == 1) {
/* 1014 */         JComboBox jComboBox = (JComboBox)param1ItemEvent.getSource();
/* 1015 */         BasicComboPopup.this.setListSelection(jComboBox.getSelectedIndex());
/*      */       } else {
/* 1017 */         BasicComboPopup.this.setListSelection(-1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseWheelMoved(MouseWheelEvent param1MouseWheelEvent) {
/* 1025 */       param1MouseWheelEvent.consume();
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
/*      */   public boolean isFocusTraversable() {
/* 1038 */     return false;
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
/*      */   protected void startAutoScrolling(int paramInt) {
/* 1052 */     if (this.isAutoScrolling) {
/* 1053 */       this.autoscrollTimer.stop();
/*      */     }
/*      */     
/* 1056 */     this.isAutoScrolling = true;
/*      */     
/* 1058 */     if (paramInt == 0) {
/* 1059 */       this.scrollDirection = 0;
/* 1060 */       Point point = SwingUtilities.convertPoint(this.scroller, new Point(1, 1), this.list);
/* 1061 */       int i = this.list.locationToIndex(point);
/* 1062 */       this.list.setSelectedIndex(i);
/*      */       
/* 1064 */       this.autoscrollTimer = new Timer(100, new AutoScrollActionHandler(0));
/*      */     
/*      */     }
/* 1067 */     else if (paramInt == 1) {
/* 1068 */       this.scrollDirection = 1;
/* 1069 */       Dimension dimension = this.scroller.getSize();
/* 1070 */       Point point = SwingUtilities.convertPoint(this.scroller, new Point(1, dimension.height - 1 - 2), this.list);
/*      */ 
/*      */       
/* 1073 */       int i = this.list.locationToIndex(point);
/* 1074 */       this.list.setSelectedIndex(i);
/*      */       
/* 1076 */       this.autoscrollTimer = new Timer(100, new AutoScrollActionHandler(1));
/*      */     } 
/*      */     
/* 1079 */     this.autoscrollTimer.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void stopAutoScrolling() {
/* 1087 */     this.isAutoScrolling = false;
/*      */     
/* 1089 */     if (this.autoscrollTimer != null) {
/* 1090 */       this.autoscrollTimer.stop();
/* 1091 */       this.autoscrollTimer = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void autoScrollUp() {
/* 1100 */     int i = this.list.getSelectedIndex();
/* 1101 */     if (i > 0) {
/* 1102 */       this.list.setSelectedIndex(i - 1);
/* 1103 */       this.list.ensureIndexIsVisible(i - 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void autoScrollDown() {
/* 1112 */     int i = this.list.getSelectedIndex();
/* 1113 */     int j = this.list.getModel().getSize() - 1;
/* 1114 */     if (i < j) {
/* 1115 */       this.list.setSelectedIndex(i + 1);
/* 1116 */       this.list.ensureIndexIsVisible(i + 1);
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
/*      */   public AccessibleContext getAccessibleContext() {
/* 1137 */     AccessibleContext accessibleContext = super.getAccessibleContext();
/* 1138 */     accessibleContext.setAccessibleParent(this.comboBox);
/* 1139 */     return accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void delegateFocus(MouseEvent paramMouseEvent) {
/* 1150 */     if (this.comboBox.isEditable()) {
/* 1151 */       Component component = this.comboBox.getEditor().getEditorComponent();
/* 1152 */       if (!(component instanceof JComponent) || ((JComponent)component).isRequestFocusEnabled()) {
/* 1153 */         component.requestFocus();
/*      */       }
/*      */     }
/* 1156 */     else if (this.comboBox.isRequestFocusEnabled()) {
/* 1157 */       this.comboBox.requestFocus();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void togglePopup() {
/* 1166 */     if (isVisible()) {
/* 1167 */       hide();
/*      */     } else {
/*      */       
/* 1170 */       show();
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
/*      */   private void setListSelection(int paramInt) {
/* 1182 */     if (paramInt == -1) {
/* 1183 */       this.list.clearSelection();
/*      */     } else {
/*      */       
/* 1186 */       this.list.setSelectedIndex(paramInt);
/* 1187 */       this.list.ensureIndexIsVisible(paramInt);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected MouseEvent convertMouseEvent(MouseEvent paramMouseEvent) {
/* 1192 */     Point point = SwingUtilities.convertPoint((Component)paramMouseEvent.getSource(), paramMouseEvent
/* 1193 */         .getPoint(), this.list);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     MouseEvent mouseEvent = new MouseEvent((Component)paramMouseEvent.getSource(), paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*      */     
/* 1205 */     AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/* 1206 */     mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/* 1207 */         .isCausedByTouchEvent(paramMouseEvent));
/* 1208 */     return mouseEvent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getPopupHeightForRowCount(int paramInt) {
/* 1218 */     int i = Math.min(paramInt, this.comboBox.getItemCount());
/* 1219 */     int j = 0;
/* 1220 */     ListCellRenderer listCellRenderer = this.list.getCellRenderer();
/* 1221 */     Object object = null;
/*      */     
/* 1223 */     for (byte b = 0; b < i; b++) {
/* 1224 */       object = this.list.getModel().getElementAt(b);
/* 1225 */       Component component = listCellRenderer.getListCellRendererComponent(this.list, object, b, false, false);
/* 1226 */       j += (component.getPreferredSize()).height;
/*      */     } 
/*      */     
/* 1229 */     if (j == 0) {
/* 1230 */       j = this.comboBox.getHeight();
/*      */     }
/*      */     
/* 1233 */     Border border = this.scroller.getViewportBorder();
/* 1234 */     if (border != null) {
/* 1235 */       Insets insets = border.getBorderInsets(null);
/* 1236 */       j += insets.top + insets.bottom;
/*      */     } 
/*      */     
/* 1239 */     border = this.scroller.getBorder();
/* 1240 */     if (border != null) {
/* 1241 */       Insets insets = border.getBorderInsets(null);
/* 1242 */       j += insets.top + insets.bottom;
/*      */     } 
/*      */     
/* 1245 */     return j;
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
/*      */   protected Rectangle computePopupBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     Rectangle rectangle1;
/* 1261 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*      */ 
/*      */ 
/*      */     
/* 1265 */     GraphicsConfiguration graphicsConfiguration = this.comboBox.getGraphicsConfiguration();
/* 1266 */     Point point = new Point();
/* 1267 */     SwingUtilities.convertPointFromScreen(point, this.comboBox);
/* 1268 */     if (graphicsConfiguration != null) {
/* 1269 */       Insets insets = toolkit.getScreenInsets(graphicsConfiguration);
/* 1270 */       rectangle1 = graphicsConfiguration.getBounds();
/* 1271 */       rectangle1.width -= insets.left + insets.right;
/* 1272 */       rectangle1.height -= insets.top + insets.bottom;
/* 1273 */       rectangle1.x += point.x + insets.left;
/* 1274 */       rectangle1.y += point.y + insets.top;
/*      */     } else {
/*      */       
/* 1277 */       rectangle1 = new Rectangle(point, toolkit.getScreenSize());
/*      */     } 
/*      */     
/* 1280 */     Rectangle rectangle2 = new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4);
/* 1281 */     if (paramInt2 + paramInt4 > rectangle1.y + rectangle1.height && paramInt4 < rectangle1.height)
/*      */     {
/* 1283 */       rectangle2.y = -rectangle2.height;
/*      */     }
/* 1285 */     return rectangle2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Point getPopupLocation() {
/* 1292 */     Dimension dimension1 = this.comboBox.getSize();
/* 1293 */     Insets insets = getInsets();
/*      */ 
/*      */ 
/*      */     
/* 1297 */     dimension1.setSize(dimension1.width - insets.right + insets.left, 
/* 1298 */         getPopupHeightForRowCount(this.comboBox.getMaximumRowCount()));
/* 1299 */     Rectangle rectangle = computePopupBounds(0, (this.comboBox.getBounds()).height, dimension1.width, dimension1.height);
/*      */     
/* 1301 */     Dimension dimension2 = rectangle.getSize();
/* 1302 */     Point point = rectangle.getLocation();
/*      */     
/* 1304 */     this.scroller.setMaximumSize(dimension2);
/* 1305 */     this.scroller.setPreferredSize(dimension2);
/* 1306 */     this.scroller.setMinimumSize(dimension2);
/*      */     
/* 1308 */     this.list.revalidate();
/*      */     
/* 1310 */     return point;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateListBoxSelectionForEvent(MouseEvent paramMouseEvent, boolean paramBoolean) {
/* 1320 */     Point point = paramMouseEvent.getPoint();
/* 1321 */     if (this.list == null)
/*      */       return; 
/* 1323 */     int i = this.list.locationToIndex(point);
/* 1324 */     if (i == -1)
/* 1325 */       if (point.y < 0) {
/* 1326 */         i = 0;
/*      */       } else {
/* 1328 */         i = this.comboBox.getModel().getSize() - 1;
/*      */       }  
/* 1330 */     if (this.list.getSelectedIndex() != i) {
/* 1331 */       this.list.setSelectedIndex(i);
/* 1332 */       if (paramBoolean)
/* 1333 */         this.list.ensureIndexIsVisible(i); 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicComboPopup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */