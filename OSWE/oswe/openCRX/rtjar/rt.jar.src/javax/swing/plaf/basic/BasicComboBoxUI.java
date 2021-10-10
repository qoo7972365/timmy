/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.CellRendererPane;
/*      */ import javax.swing.ComboBoxEditor;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.DefaultListCellRenderer;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.ListCellRenderer;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ListDataEvent;
/*      */ import javax.swing.event.ListDataListener;
/*      */ import javax.swing.plaf.ComboBoxUI;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.Position;
/*      */ import sun.awt.AppContext;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicComboBoxUI
/*      */   extends ComboBoxUI
/*      */ {
/*      */   protected JComboBox comboBox;
/*      */   protected boolean hasFocus = false;
/*      */   private boolean isTableCellEditor = false;
/*      */   private static final String IS_TABLE_CELL_EDITOR = "JComboBox.isTableCellEditor";
/*      */   protected JList listBox;
/*   81 */   protected CellRendererPane currentValuePane = new CellRendererPane();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComboPopup popup;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component editor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JButton arrowButton;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener keyListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FocusListener focusListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener propertyChangeListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ItemListener itemListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseListener popupMouseListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected MouseMotionListener popupMouseMotionListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener popupKeyListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ListDataListener listDataListener;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler handler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   private long timeFactor = 1000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   private long lastTime = 0L;
/*  156 */   private long time = 0L;
/*      */ 
/*      */ 
/*      */   
/*      */   JComboBox.KeySelectionManager keySelectionManager;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMinimumSizeDirty = true;
/*      */ 
/*      */   
/*  167 */   protected Dimension cachedMinimumSize = new Dimension(0, 0);
/*      */ 
/*      */   
/*      */   private boolean isDisplaySizeDirty = true;
/*      */ 
/*      */   
/*  173 */   private Dimension cachedDisplaySize = new Dimension(0, 0);
/*      */ 
/*      */   
/*  176 */   private static final Object COMBO_UI_LIST_CELL_RENDERER_KEY = new StringBuffer("DefaultListCellRendererKey");
/*      */ 
/*      */   
/*  179 */   static final StringBuffer HIDE_POPUP_KEY = new StringBuffer("HidePopupKey");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sameBaseline;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean squareButton = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Insets padding;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ListCellRenderer getDefaultListCellRenderer() {
/*  208 */     ListCellRenderer listCellRenderer = (ListCellRenderer)AppContext.getAppContext().get(COMBO_UI_LIST_CELL_RENDERER_KEY);
/*      */     
/*  210 */     if (listCellRenderer == null) {
/*  211 */       listCellRenderer = new DefaultListCellRenderer();
/*  212 */       AppContext.getAppContext().put(COMBO_UI_LIST_CELL_RENDERER_KEY, new DefaultListCellRenderer());
/*      */     } 
/*      */     
/*  215 */     return listCellRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  222 */     paramLazyActionMap.put(new Actions("hidePopup"));
/*  223 */     paramLazyActionMap.put(new Actions("pageDownPassThrough"));
/*  224 */     paramLazyActionMap.put(new Actions("pageUpPassThrough"));
/*  225 */     paramLazyActionMap.put(new Actions("homePassThrough"));
/*  226 */     paramLazyActionMap.put(new Actions("endPassThrough"));
/*  227 */     paramLazyActionMap.put(new Actions("selectNext"));
/*  228 */     paramLazyActionMap.put(new Actions("selectNext2"));
/*  229 */     paramLazyActionMap.put(new Actions("togglePopup"));
/*  230 */     paramLazyActionMap.put(new Actions("spacePopup"));
/*  231 */     paramLazyActionMap.put(new Actions("selectPrevious"));
/*  232 */     paramLazyActionMap.put(new Actions("selectPrevious2"));
/*  233 */     paramLazyActionMap.put(new Actions("enterPressed"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  241 */     return new BasicComboBoxUI();
/*      */   }
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  246 */     this.isMinimumSizeDirty = true;
/*      */     
/*  248 */     this.comboBox = (JComboBox)paramJComponent;
/*  249 */     installDefaults();
/*  250 */     this.popup = createPopup();
/*  251 */     this.listBox = this.popup.getList();
/*      */ 
/*      */     
/*  254 */     Boolean bool = (Boolean)paramJComponent.getClientProperty("JComboBox.isTableCellEditor");
/*  255 */     if (bool != null) {
/*  256 */       this.isTableCellEditor = bool.equals(Boolean.TRUE);
/*      */     }
/*      */     
/*  259 */     if (this.comboBox.getRenderer() == null || this.comboBox.getRenderer() instanceof UIResource) {
/*  260 */       this.comboBox.setRenderer(createRenderer());
/*      */     }
/*      */     
/*  263 */     if (this.comboBox.getEditor() == null || this.comboBox.getEditor() instanceof UIResource) {
/*  264 */       this.comboBox.setEditor(createEditor());
/*      */     }
/*      */     
/*  267 */     installListeners();
/*  268 */     installComponents();
/*      */     
/*  270 */     this.comboBox.setLayout(createLayoutManager());
/*      */     
/*  272 */     this.comboBox.setRequestFocusEnabled(true);
/*      */     
/*  274 */     installKeyboardActions();
/*      */     
/*  276 */     this.comboBox.putClientProperty("doNotCancelPopup", HIDE_POPUP_KEY);
/*      */     
/*  278 */     if (this.keySelectionManager == null || this.keySelectionManager instanceof UIResource) {
/*  279 */       this.keySelectionManager = new DefaultKeySelectionManager();
/*      */     }
/*  281 */     this.comboBox.setKeySelectionManager(this.keySelectionManager);
/*      */   }
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  286 */     setPopupVisible(this.comboBox, false);
/*  287 */     this.popup.uninstallingUI();
/*      */     
/*  289 */     uninstallKeyboardActions();
/*      */     
/*  291 */     this.comboBox.setLayout((LayoutManager)null);
/*      */     
/*  293 */     uninstallComponents();
/*  294 */     uninstallListeners();
/*  295 */     uninstallDefaults();
/*      */     
/*  297 */     if (this.comboBox.getRenderer() == null || this.comboBox.getRenderer() instanceof UIResource) {
/*  298 */       this.comboBox.setRenderer((ListCellRenderer)null);
/*      */     }
/*      */     
/*  301 */     ComboBoxEditor comboBoxEditor = this.comboBox.getEditor();
/*  302 */     if (comboBoxEditor instanceof UIResource) {
/*  303 */       if (comboBoxEditor.getEditorComponent().hasFocus())
/*      */       {
/*  305 */         this.comboBox.requestFocusInWindow();
/*      */       }
/*  307 */       this.comboBox.setEditor((ComboBoxEditor)null);
/*      */     } 
/*      */     
/*  310 */     if (this.keySelectionManager instanceof UIResource) {
/*  311 */       this.comboBox.setKeySelectionManager((JComboBox.KeySelectionManager)null);
/*      */     }
/*      */     
/*  314 */     this.handler = null;
/*  315 */     this.keyListener = null;
/*  316 */     this.focusListener = null;
/*  317 */     this.listDataListener = null;
/*  318 */     this.propertyChangeListener = null;
/*  319 */     this.popup = null;
/*  320 */     this.listBox = null;
/*  321 */     this.comboBox = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installDefaults() {
/*  329 */     LookAndFeel.installColorsAndFont(this.comboBox, "ComboBox.background", "ComboBox.foreground", "ComboBox.font");
/*      */ 
/*      */ 
/*      */     
/*  333 */     LookAndFeel.installBorder(this.comboBox, "ComboBox.border");
/*  334 */     LookAndFeel.installProperty(this.comboBox, "opaque", Boolean.TRUE);
/*      */     
/*  336 */     Long long_ = (Long)UIManager.get("ComboBox.timeFactor");
/*  337 */     this.timeFactor = (long_ == null) ? 1000L : long_.longValue();
/*      */ 
/*      */     
/*  340 */     Boolean bool = (Boolean)UIManager.get("ComboBox.squareButton");
/*  341 */     this.squareButton = (bool == null) ? true : bool.booleanValue();
/*      */     
/*  343 */     this.padding = UIManager.getInsets("ComboBox.padding");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  351 */     if ((this.itemListener = createItemListener()) != null) {
/*  352 */       this.comboBox.addItemListener(this.itemListener);
/*      */     }
/*  354 */     if ((this.propertyChangeListener = createPropertyChangeListener()) != null) {
/*  355 */       this.comboBox.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  357 */     if ((this.keyListener = createKeyListener()) != null) {
/*  358 */       this.comboBox.addKeyListener(this.keyListener);
/*      */     }
/*  360 */     if ((this.focusListener = createFocusListener()) != null) {
/*  361 */       this.comboBox.addFocusListener(this.focusListener);
/*      */     }
/*  363 */     if ((this.popupMouseListener = this.popup.getMouseListener()) != null) {
/*  364 */       this.comboBox.addMouseListener(this.popupMouseListener);
/*      */     }
/*  366 */     if ((this.popupMouseMotionListener = this.popup.getMouseMotionListener()) != null) {
/*  367 */       this.comboBox.addMouseMotionListener(this.popupMouseMotionListener);
/*      */     }
/*  369 */     if ((this.popupKeyListener = this.popup.getKeyListener()) != null) {
/*  370 */       this.comboBox.addKeyListener(this.popupKeyListener);
/*      */     }
/*      */     
/*  373 */     if (this.comboBox.getModel() != null && (
/*  374 */       this.listDataListener = createListDataListener()) != null) {
/*  375 */       this.comboBox.getModel().addListDataListener(this.listDataListener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults() {
/*  385 */     LookAndFeel.installColorsAndFont(this.comboBox, "ComboBox.background", "ComboBox.foreground", "ComboBox.font");
/*      */ 
/*      */ 
/*      */     
/*  389 */     LookAndFeel.uninstallBorder(this.comboBox);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {
/*  398 */     if (this.keyListener != null) {
/*  399 */       this.comboBox.removeKeyListener(this.keyListener);
/*      */     }
/*  401 */     if (this.itemListener != null) {
/*  402 */       this.comboBox.removeItemListener(this.itemListener);
/*      */     }
/*  404 */     if (this.propertyChangeListener != null) {
/*  405 */       this.comboBox.removePropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  407 */     if (this.focusListener != null) {
/*  408 */       this.comboBox.removeFocusListener(this.focusListener);
/*      */     }
/*  410 */     if (this.popupMouseListener != null) {
/*  411 */       this.comboBox.removeMouseListener(this.popupMouseListener);
/*      */     }
/*  413 */     if (this.popupMouseMotionListener != null) {
/*  414 */       this.comboBox.removeMouseMotionListener(this.popupMouseMotionListener);
/*      */     }
/*  416 */     if (this.popupKeyListener != null) {
/*  417 */       this.comboBox.removeKeyListener(this.popupKeyListener);
/*      */     }
/*  419 */     if (this.comboBox.getModel() != null && 
/*  420 */       this.listDataListener != null) {
/*  421 */       this.comboBox.getModel().removeListDataListener(this.listDataListener);
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
/*      */   protected ComboPopup createPopup() {
/*  433 */     return new BasicComboPopup(this.comboBox);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected KeyListener createKeyListener() {
/*  444 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FocusListener createFocusListener() {
/*  454 */     return getHandler();
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
/*  465 */     return getHandler();
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
/*  479 */     return null;
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
/*  490 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LayoutManager createLayoutManager() {
/*  500 */     return getHandler();
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
/*      */   protected ListCellRenderer createRenderer() {
/*  512 */     return new BasicComboBoxRenderer.UIResource();
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
/*      */   protected ComboBoxEditor createEditor() {
/*  524 */     return new BasicComboBoxEditor.UIResource();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/*  531 */     if (this.handler == null) {
/*  532 */       this.handler = new Handler();
/*      */     }
/*  534 */     return this.handler;
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
/*      */   public class KeyHandler
/*      */     extends KeyAdapter
/*      */   {
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/*  558 */       BasicComboBoxUI.this.getHandler().keyPressed(param1KeyEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class FocusHandler
/*      */     implements FocusListener
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/*  572 */       BasicComboBoxUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/*  576 */       BasicComboBoxUI.this.getHandler().focusLost(param1FocusEvent);
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
/*      */   public class ListDataHandler
/*      */     implements ListDataListener
/*      */   {
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/*  592 */       BasicComboBoxUI.this.getHandler().contentsChanged(param1ListDataEvent);
/*      */     }
/*      */     
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/*  596 */       BasicComboBoxUI.this.getHandler().intervalAdded(param1ListDataEvent);
/*      */     }
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/*  600 */       BasicComboBoxUI.this.getHandler().intervalRemoved(param1ListDataEvent);
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
/*      */   public class ItemHandler
/*      */     implements ItemListener
/*      */   {
/*      */     public void itemStateChanged(ItemEvent param1ItemEvent) {}
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
/*      */   public class PropertyChangeHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  635 */       BasicComboBoxUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateToolTipTextForChildren() {
/*  643 */     Component[] arrayOfComponent = this.comboBox.getComponents();
/*  644 */     for (byte b = 0; b < arrayOfComponent.length; b++) {
/*  645 */       if (arrayOfComponent[b] instanceof JComponent) {
/*  646 */         ((JComponent)arrayOfComponent[b]).setToolTipText(this.comboBox.getToolTipText());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class ComboBoxLayoutManager
/*      */     implements LayoutManager
/*      */   {
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  666 */       return BasicComboBoxUI.this.getHandler().preferredLayoutSize(param1Container);
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*  670 */       return BasicComboBoxUI.this.getHandler().minimumLayoutSize(param1Container);
/*      */     }
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  674 */       BasicComboBoxUI.this.getHandler().layoutContainer(param1Container);
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
/*      */   protected void installComponents() {
/*  693 */     this.arrowButton = createArrowButton();
/*      */     
/*  695 */     if (this.arrowButton != null) {
/*  696 */       this.comboBox.add(this.arrowButton);
/*  697 */       configureArrowButton();
/*      */     } 
/*      */     
/*  700 */     if (this.comboBox.isEditable()) {
/*  701 */       addEditor();
/*      */     }
/*      */     
/*  704 */     this.comboBox.add(this.currentValuePane);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallComponents() {
/*  713 */     if (this.arrowButton != null) {
/*  714 */       unconfigureArrowButton();
/*      */     }
/*  716 */     if (this.editor != null) {
/*  717 */       unconfigureEditor();
/*      */     }
/*  719 */     this.comboBox.removeAll();
/*  720 */     this.arrowButton = null;
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
/*      */   public void addEditor() {
/*  733 */     removeEditor();
/*  734 */     this.editor = this.comboBox.getEditor().getEditorComponent();
/*  735 */     if (this.editor != null) {
/*  736 */       configureEditor();
/*  737 */       this.comboBox.add(this.editor);
/*  738 */       if (this.comboBox.isFocusOwner())
/*      */       {
/*  740 */         this.editor.requestFocusInWindow();
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
/*      */   public void removeEditor() {
/*  752 */     if (this.editor != null) {
/*  753 */       unconfigureEditor();
/*  754 */       this.comboBox.remove(this.editor);
/*  755 */       this.editor = null;
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
/*      */   protected void configureEditor() {
/*  767 */     this.editor.setEnabled(this.comboBox.isEnabled());
/*      */     
/*  769 */     this.editor.setFocusable(this.comboBox.isFocusable());
/*      */     
/*  771 */     this.editor.setFont(this.comboBox.getFont());
/*      */     
/*  773 */     if (this.focusListener != null) {
/*  774 */       this.editor.addFocusListener(this.focusListener);
/*      */     }
/*      */     
/*  777 */     this.editor.addFocusListener(getHandler());
/*      */     
/*  779 */     this.comboBox.getEditor().addActionListener(getHandler());
/*      */     
/*  781 */     if (this.editor instanceof JComponent) {
/*  782 */       ((JComponent)this.editor).putClientProperty("doNotCancelPopup", HIDE_POPUP_KEY);
/*      */       
/*  784 */       ((JComponent)this.editor).setInheritsPopupMenu(true);
/*      */     } 
/*      */     
/*  787 */     this.comboBox.configureEditor(this.comboBox.getEditor(), this.comboBox.getSelectedItem());
/*      */     
/*  789 */     this.editor.addPropertyChangeListener(this.propertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void unconfigureEditor() {
/*  799 */     if (this.focusListener != null) {
/*  800 */       this.editor.removeFocusListener(this.focusListener);
/*      */     }
/*      */     
/*  803 */     this.editor.removePropertyChangeListener(this.propertyChangeListener);
/*  804 */     this.editor.removeFocusListener(getHandler());
/*  805 */     this.comboBox.getEditor().removeActionListener(getHandler());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void configureArrowButton() {
/*  815 */     if (this.arrowButton != null) {
/*  816 */       this.arrowButton.setEnabled(this.comboBox.isEnabled());
/*  817 */       this.arrowButton.setFocusable(this.comboBox.isFocusable());
/*  818 */       this.arrowButton.setRequestFocusEnabled(false);
/*  819 */       this.arrowButton.addMouseListener(this.popup.getMouseListener());
/*  820 */       this.arrowButton.addMouseMotionListener(this.popup.getMouseMotionListener());
/*  821 */       this.arrowButton.resetKeyboardActions();
/*  822 */       this.arrowButton.putClientProperty("doNotCancelPopup", HIDE_POPUP_KEY);
/*  823 */       this.arrowButton.setInheritsPopupMenu(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unconfigureArrowButton() {
/*  834 */     if (this.arrowButton != null) {
/*  835 */       this.arrowButton.removeMouseListener(this.popup.getMouseListener());
/*  836 */       this.arrowButton.removeMouseMotionListener(this.popup.getMouseMotionListener());
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
/*      */   protected JButton createArrowButton() {
/*  851 */     BasicArrowButton basicArrowButton = new BasicArrowButton(5, UIManager.getColor("ComboBox.buttonBackground"), UIManager.getColor("ComboBox.buttonShadow"), UIManager.getColor("ComboBox.buttonDarkShadow"), UIManager.getColor("ComboBox.buttonHighlight"));
/*  852 */     basicArrowButton.setName("ComboBox.arrowButton");
/*  853 */     return basicArrowButton;
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
/*      */   public boolean isPopupVisible(JComboBox paramJComboBox) {
/*  869 */     return this.popup.isVisible();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPopupVisible(JComboBox paramJComboBox, boolean paramBoolean) {
/*  876 */     if (paramBoolean) {
/*  877 */       this.popup.show();
/*      */     } else {
/*  879 */       this.popup.hide();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFocusTraversable(JComboBox paramJComboBox) {
/*  888 */     return !this.comboBox.isEditable();
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
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  900 */     this.hasFocus = this.comboBox.hasFocus();
/*  901 */     if (!this.comboBox.isEditable()) {
/*  902 */       Rectangle rectangle = rectangleForCurrentValue();
/*  903 */       paintCurrentValueBackground(paramGraphics, rectangle, this.hasFocus);
/*  904 */       paintCurrentValue(paramGraphics, rectangle, this.hasFocus);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  910 */     return getMinimumSize(paramJComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  918 */     if (!this.isMinimumSizeDirty) {
/*  919 */       return new Dimension(this.cachedMinimumSize);
/*      */     }
/*  921 */     Dimension dimension = getDisplaySize();
/*  922 */     Insets insets = getInsets();
/*      */     
/*  924 */     int i = dimension.height;
/*  925 */     int j = this.squareButton ? i : (this.arrowButton.getPreferredSize()).width;
/*      */     
/*  927 */     dimension.height += insets.top + insets.bottom;
/*  928 */     dimension.width += insets.left + insets.right + j;
/*      */     
/*  930 */     this.cachedMinimumSize.setSize(dimension.width, dimension.height);
/*  931 */     this.isMinimumSizeDirty = false;
/*      */     
/*  933 */     return new Dimension(dimension);
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  938 */     return new Dimension(32767, 32767);
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
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  951 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  952 */     int i = -1;
/*      */     
/*  954 */     getDisplaySize();
/*  955 */     if (this.sameBaseline) {
/*  956 */       Insets insets = paramJComponent.getInsets();
/*  957 */       paramInt2 = paramInt2 - insets.top - insets.bottom;
/*  958 */       if (!this.comboBox.isEditable()) {
/*  959 */         ListCellRenderer listCellRenderer = this.comboBox.getRenderer();
/*  960 */         if (listCellRenderer == null) {
/*  961 */           listCellRenderer = new DefaultListCellRenderer();
/*      */         }
/*  963 */         Object object1 = null;
/*  964 */         Object object2 = this.comboBox.getPrototypeDisplayValue();
/*  965 */         if (object2 != null) {
/*  966 */           object1 = object2;
/*      */         }
/*  968 */         else if (this.comboBox.getModel().getSize() > 0) {
/*      */ 
/*      */           
/*  971 */           object1 = this.comboBox.getModel().getElementAt(0);
/*      */         } 
/*      */         
/*  974 */         Component component = listCellRenderer.getListCellRendererComponent(this.listBox, object1, -1, false, false);
/*      */         
/*  976 */         if (component instanceof JLabel) {
/*  977 */           JLabel jLabel = (JLabel)component;
/*  978 */           String str = jLabel.getText();
/*  979 */           if (str == null || str.isEmpty()) {
/*  980 */             jLabel.setText(" ");
/*      */           }
/*      */         } 
/*  983 */         if (component instanceof JComponent) {
/*  984 */           component.setFont(this.comboBox.getFont());
/*      */         }
/*  986 */         i = component.getBaseline(paramInt1, paramInt2);
/*      */       } else {
/*      */         
/*  989 */         i = this.editor.getBaseline(paramInt1, paramInt2);
/*      */       } 
/*  991 */       if (i > 0) {
/*  992 */         i += insets.top;
/*      */       }
/*      */     } 
/*  995 */     return i;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/* 1009 */     super.getBaselineResizeBehavior(paramJComponent);
/*      */     
/* 1011 */     getDisplaySize();
/* 1012 */     if (this.comboBox.isEditable()) {
/* 1013 */       return this.editor.getBaselineResizeBehavior();
/*      */     }
/* 1015 */     if (this.sameBaseline) {
/* 1016 */       ListCellRenderer listCellRenderer = this.comboBox.getRenderer();
/* 1017 */       if (listCellRenderer == null) {
/* 1018 */         listCellRenderer = new DefaultListCellRenderer();
/*      */       }
/* 1020 */       Object object1 = null;
/* 1021 */       Object object2 = this.comboBox.getPrototypeDisplayValue();
/* 1022 */       if (object2 != null) {
/* 1023 */         object1 = object2;
/*      */       }
/* 1025 */       else if (this.comboBox.getModel().getSize() > 0) {
/*      */ 
/*      */         
/* 1028 */         object1 = this.comboBox.getModel().getElementAt(0);
/*      */       } 
/* 1030 */       if (object1 != null) {
/*      */         
/* 1032 */         Component component = listCellRenderer.getListCellRendererComponent(this.listBox, object1, -1, false, false);
/*      */         
/* 1034 */         return component.getBaselineResizeBehavior();
/*      */       } 
/*      */     } 
/* 1037 */     return Component.BaselineResizeBehavior.OTHER;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAccessibleChildrenCount(JComponent paramJComponent) {
/* 1043 */     if (this.comboBox.isEditable()) {
/* 1044 */       return 2;
/*      */     }
/*      */     
/* 1047 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Accessible getAccessibleChild(JComponent paramJComponent, int paramInt) {
/* 1056 */     switch (paramInt) {
/*      */       case 0:
/* 1058 */         if (this.popup instanceof Accessible) {
/* 1059 */           AccessibleContext accessibleContext = ((Accessible)this.popup).getAccessibleContext();
/* 1060 */           accessibleContext.setAccessibleParent(this.comboBox);
/* 1061 */           return (Accessible)this.popup;
/*      */         } 
/*      */         break;
/*      */       case 1:
/* 1065 */         if (this.comboBox.isEditable() && this.editor instanceof Accessible) {
/*      */           
/* 1067 */           AccessibleContext accessibleContext = ((Accessible)this.editor).getAccessibleContext();
/* 1068 */           accessibleContext.setAccessibleParent(this.comboBox);
/* 1069 */           return (Accessible)this.editor;
/*      */         } 
/*      */         break;
/*      */     } 
/* 1073 */     return null;
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
/*      */   protected boolean isNavigationKey(int paramInt) {
/* 1092 */     return (paramInt == 38 || paramInt == 40 || paramInt == 224 || paramInt == 225);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isNavigationKey(int paramInt1, int paramInt2) {
/* 1097 */     InputMap inputMap = this.comboBox.getInputMap(1);
/* 1098 */     KeyStroke keyStroke = KeyStroke.getKeyStroke(paramInt1, paramInt2);
/*      */     
/* 1100 */     if (inputMap != null && inputMap.get(keyStroke) != null) {
/* 1101 */       return true;
/*      */     }
/* 1103 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectNextPossibleValue() {
/*      */     int i;
/* 1113 */     if (this.comboBox.isPopupVisible()) {
/* 1114 */       i = this.listBox.getSelectedIndex();
/*      */     } else {
/*      */       
/* 1117 */       i = this.comboBox.getSelectedIndex();
/*      */     } 
/*      */     
/* 1120 */     if (i < this.comboBox.getModel().getSize() - 1) {
/* 1121 */       this.listBox.setSelectedIndex(i + 1);
/* 1122 */       this.listBox.ensureIndexIsVisible(i + 1);
/* 1123 */       if (!this.isTableCellEditor && (
/* 1124 */         !UIManager.getBoolean("ComboBox.noActionOnKeyNavigation") || !this.comboBox.isPopupVisible())) {
/* 1125 */         this.comboBox.setSelectedIndex(i + 1);
/*      */       }
/*      */       
/* 1128 */       this.comboBox.repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectPreviousPossibleValue() {
/*      */     int i;
/* 1139 */     if (this.comboBox.isPopupVisible()) {
/* 1140 */       i = this.listBox.getSelectedIndex();
/*      */     } else {
/*      */       
/* 1143 */       i = this.comboBox.getSelectedIndex();
/*      */     } 
/*      */     
/* 1146 */     if (i > 0) {
/* 1147 */       this.listBox.setSelectedIndex(i - 1);
/* 1148 */       this.listBox.ensureIndexIsVisible(i - 1);
/* 1149 */       if (!this.isTableCellEditor && (
/* 1150 */         !UIManager.getBoolean("ComboBox.noActionOnKeyNavigation") || !this.comboBox.isPopupVisible())) {
/* 1151 */         this.comboBox.setSelectedIndex(i - 1);
/*      */       }
/*      */       
/* 1154 */       this.comboBox.repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void toggleOpenClose() {
/* 1162 */     setPopupVisible(this.comboBox, !isPopupVisible(this.comboBox));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Rectangle rectangleForCurrentValue() {
/* 1169 */     int i = this.comboBox.getWidth();
/* 1170 */     int j = this.comboBox.getHeight();
/* 1171 */     Insets insets = getInsets();
/* 1172 */     int k = j - insets.top + insets.bottom;
/* 1173 */     if (this.arrowButton != null) {
/* 1174 */       k = this.arrowButton.getWidth();
/*      */     }
/* 1176 */     if (BasicGraphicsUtils.isLeftToRight(this.comboBox)) {
/* 1177 */       return new Rectangle(insets.left, insets.top, i - insets.left + insets.right + k, j - insets.top + insets.bottom);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1182 */     return new Rectangle(insets.left + k, insets.top, i - insets.left + insets.right + k, j - insets.top + insets.bottom);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Insets getInsets() {
/* 1192 */     return this.comboBox.getInsets();
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
/*      */   public void paintCurrentValue(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/*      */     Component component;
/* 1208 */     ListCellRenderer<Object> listCellRenderer = this.comboBox.getRenderer();
/*      */ 
/*      */     
/* 1211 */     if (paramBoolean && !isPopupVisible(this.comboBox)) {
/* 1212 */       component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox
/* 1213 */           .getSelectedItem(), -1, true, false);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1219 */       component = listCellRenderer.getListCellRendererComponent(this.listBox, this.comboBox
/* 1220 */           .getSelectedItem(), -1, false, false);
/*      */ 
/*      */ 
/*      */       
/* 1224 */       component.setBackground(UIManager.getColor("ComboBox.background"));
/*      */     } 
/* 1226 */     component.setFont(this.comboBox.getFont());
/* 1227 */     if (paramBoolean && !isPopupVisible(this.comboBox)) {
/* 1228 */       component.setForeground(this.listBox.getSelectionForeground());
/* 1229 */       component.setBackground(this.listBox.getSelectionBackground());
/*      */     
/*      */     }
/* 1232 */     else if (this.comboBox.isEnabled()) {
/* 1233 */       component.setForeground(this.comboBox.getForeground());
/* 1234 */       component.setBackground(this.comboBox.getBackground());
/*      */     } else {
/*      */       
/* 1237 */       component.setForeground(DefaultLookup.getColor(this.comboBox, this, "ComboBox.disabledForeground", null));
/*      */       
/* 1239 */       component.setBackground(DefaultLookup.getColor(this.comboBox, this, "ComboBox.disabledBackground", null));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1245 */     boolean bool = false;
/* 1246 */     if (component instanceof javax.swing.JPanel) {
/* 1247 */       bool = true;
/*      */     }
/*      */     
/* 1250 */     int i = paramRectangle.x, j = paramRectangle.y, k = paramRectangle.width, m = paramRectangle.height;
/* 1251 */     if (this.padding != null) {
/* 1252 */       i = paramRectangle.x + this.padding.left;
/* 1253 */       j = paramRectangle.y + this.padding.top;
/* 1254 */       k = paramRectangle.width - this.padding.left + this.padding.right;
/* 1255 */       m = paramRectangle.height - this.padding.top + this.padding.bottom;
/*      */     } 
/*      */     
/* 1258 */     this.currentValuePane.paintComponent(paramGraphics, component, this.comboBox, i, j, k, m, bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintCurrentValueBackground(Graphics paramGraphics, Rectangle paramRectangle, boolean paramBoolean) {
/* 1265 */     Color color = paramGraphics.getColor();
/* 1266 */     if (this.comboBox.isEnabled()) {
/* 1267 */       paramGraphics.setColor(DefaultLookup.getColor(this.comboBox, this, "ComboBox.background", null));
/*      */     } else {
/*      */       
/* 1270 */       paramGraphics.setColor(DefaultLookup.getColor(this.comboBox, this, "ComboBox.disabledBackground", null));
/*      */     } 
/* 1272 */     paramGraphics.fillRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/* 1273 */     paramGraphics.setColor(color);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void repaintCurrentValue() {
/* 1280 */     Rectangle rectangle = rectangleForCurrentValue();
/* 1281 */     this.comboBox.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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
/*      */   protected Dimension getDefaultSize() {
/* 1302 */     Dimension dimension = getSizeForComponent(getDefaultListCellRenderer().getListCellRendererComponent(this.listBox, " ", -1, false, false));
/*      */     
/* 1304 */     return new Dimension(dimension.width, dimension.height);
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
/*      */   protected Dimension getDisplaySize() {
/* 1320 */     if (!this.isDisplaySizeDirty) {
/* 1321 */       return new Dimension(this.cachedDisplaySize);
/*      */     }
/* 1323 */     Dimension dimension = new Dimension();
/*      */     
/* 1325 */     ListCellRenderer listCellRenderer = this.comboBox.getRenderer();
/* 1326 */     if (listCellRenderer == null) {
/* 1327 */       listCellRenderer = new DefaultListCellRenderer();
/*      */     }
/*      */     
/* 1330 */     this.sameBaseline = true;
/*      */     
/* 1332 */     Object object = this.comboBox.getPrototypeDisplayValue();
/* 1333 */     if (object != null) {
/*      */       
/* 1335 */       dimension = getSizeForComponent(listCellRenderer.getListCellRendererComponent(this.listBox, object, -1, false, false));
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1341 */       ComboBoxModel<Object> comboBoxModel = this.comboBox.getModel();
/* 1342 */       int i = comboBoxModel.getSize();
/* 1343 */       int j = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1348 */       if (i > 0) {
/* 1349 */         for (byte b = 0; b < i; b++) {
/*      */ 
/*      */           
/* 1352 */           Object object1 = comboBoxModel.getElementAt(b);
/* 1353 */           Component component = listCellRenderer.getListCellRendererComponent(this.listBox, object1, -1, false, false);
/*      */           
/* 1355 */           Dimension dimension1 = getSizeForComponent(component);
/* 1356 */           if (this.sameBaseline && object1 != null && (!(object1 instanceof String) || 
/* 1357 */             !"".equals(object1))) {
/* 1358 */             int k = component.getBaseline(dimension1.width, dimension1.height);
/* 1359 */             if (k == -1) {
/* 1360 */               this.sameBaseline = false;
/*      */             }
/* 1362 */             else if (j == -1) {
/* 1363 */               j = k;
/*      */             }
/* 1365 */             else if (j != k) {
/* 1366 */               this.sameBaseline = false;
/*      */             } 
/*      */           } 
/* 1369 */           dimension.width = Math.max(dimension.width, dimension1.width);
/* 1370 */           dimension.height = Math.max(dimension.height, dimension1.height);
/*      */         } 
/*      */       } else {
/* 1373 */         dimension = getDefaultSize();
/* 1374 */         if (this.comboBox.isEditable()) {
/* 1375 */           dimension.width = 100;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1380 */     if (this.comboBox.isEditable()) {
/* 1381 */       Dimension dimension1 = this.editor.getPreferredSize();
/* 1382 */       dimension.width = Math.max(dimension.width, dimension1.width);
/* 1383 */       dimension.height = Math.max(dimension.height, dimension1.height);
/*      */     } 
/*      */ 
/*      */     
/* 1387 */     if (this.padding != null) {
/* 1388 */       dimension.width += this.padding.left + this.padding.right;
/* 1389 */       dimension.height += this.padding.top + this.padding.bottom;
/*      */     } 
/*      */ 
/*      */     
/* 1393 */     this.cachedDisplaySize.setSize(dimension.width, dimension.height);
/* 1394 */     this.isDisplaySizeDirty = false;
/*      */     
/* 1396 */     return dimension;
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
/*      */   protected Dimension getSizeForComponent(Component paramComponent) {
/* 1411 */     this.currentValuePane.add(paramComponent);
/* 1412 */     paramComponent.setFont(this.comboBox.getFont());
/* 1413 */     Dimension dimension = paramComponent.getPreferredSize();
/* 1414 */     this.currentValuePane.remove(paramComponent);
/* 1415 */     return dimension;
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
/*      */   protected void installKeyboardActions() {
/* 1433 */     InputMap inputMap = getInputMap(1);
/* 1434 */     SwingUtilities.replaceUIInputMap(this.comboBox, 1, inputMap);
/*      */ 
/*      */ 
/*      */     
/* 1438 */     LazyActionMap.installLazyActionMap(this.comboBox, BasicComboBoxUI.class, "ComboBox.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/* 1443 */     if (paramInt == 1) {
/* 1444 */       return (InputMap)DefaultLookup.get(this.comboBox, this, "ComboBox.ancestorInputMap");
/*      */     }
/*      */     
/* 1447 */     return null;
/*      */   }
/*      */   
/*      */   boolean isTableCellEditor() {
/* 1451 */     return this.isTableCellEditor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/* 1458 */     SwingUtilities.replaceUIInputMap(this.comboBox, 1, null);
/*      */     
/* 1460 */     SwingUtilities.replaceUIActionMap(this.comboBox, null);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction
/*      */   {
/*      */     private static final String HIDE = "hidePopup";
/*      */     
/*      */     private static final String DOWN = "selectNext";
/*      */     private static final String DOWN_2 = "selectNext2";
/*      */     private static final String TOGGLE = "togglePopup";
/*      */     private static final String TOGGLE_2 = "spacePopup";
/*      */     private static final String UP = "selectPrevious";
/*      */     private static final String UP_2 = "selectPrevious2";
/*      */     private static final String ENTER = "enterPressed";
/*      */     private static final String PAGE_DOWN = "pageDownPassThrough";
/*      */     private static final String PAGE_UP = "pageUpPassThrough";
/*      */     private static final String HOME = "homePassThrough";
/*      */     private static final String END = "endPassThrough";
/*      */     
/*      */     Actions(String param1String) {
/* 1482 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1486 */       String str = getName();
/* 1487 */       JComboBox jComboBox = (JComboBox)param1ActionEvent.getSource();
/* 1488 */       BasicComboBoxUI basicComboBoxUI = (BasicComboBoxUI)BasicLookAndFeel.getUIOfType(jComboBox
/* 1489 */           .getUI(), BasicComboBoxUI.class);
/* 1490 */       if (str == "hidePopup") {
/* 1491 */         jComboBox.firePopupMenuCanceled();
/* 1492 */         jComboBox.setPopupVisible(false);
/*      */       }
/* 1494 */       else if (str == "pageDownPassThrough" || str == "pageUpPassThrough" || str == "homePassThrough" || str == "endPassThrough") {
/*      */         
/* 1496 */         int i = getNextIndex(jComboBox, str);
/* 1497 */         if (i >= 0 && i < jComboBox.getItemCount()) {
/* 1498 */           if (UIManager.getBoolean("ComboBox.noActionOnKeyNavigation") && jComboBox.isPopupVisible()) {
/* 1499 */             basicComboBoxUI.listBox.setSelectedIndex(i);
/* 1500 */             basicComboBoxUI.listBox.ensureIndexIsVisible(i);
/* 1501 */             jComboBox.repaint();
/*      */           } else {
/* 1503 */             jComboBox.setSelectedIndex(i);
/*      */           }
/*      */         
/*      */         }
/* 1507 */       } else if (str == "selectNext") {
/* 1508 */         if (jComboBox.isShowing()) {
/* 1509 */           if (jComboBox.isPopupVisible()) {
/* 1510 */             if (basicComboBoxUI != null) {
/* 1511 */               basicComboBoxUI.selectNextPossibleValue();
/*      */             }
/*      */           } else {
/* 1514 */             jComboBox.setPopupVisible(true);
/*      */           }
/*      */         
/*      */         }
/* 1518 */       } else if (str == "selectNext2") {
/*      */ 
/*      */ 
/*      */         
/* 1522 */         if (jComboBox.isShowing()) {
/* 1523 */           if ((jComboBox.isEditable() || (basicComboBoxUI != null && basicComboBoxUI
/* 1524 */             .isTableCellEditor())) && 
/* 1525 */             !jComboBox.isPopupVisible()) {
/* 1526 */             jComboBox.setPopupVisible(true);
/*      */           }
/* 1528 */           else if (basicComboBoxUI != null) {
/* 1529 */             basicComboBoxUI.selectNextPossibleValue();
/*      */           }
/*      */         
/*      */         }
/*      */       }
/* 1534 */       else if (str == "togglePopup" || str == "spacePopup") {
/* 1535 */         if (basicComboBoxUI != null && (str == "togglePopup" || !jComboBox.isEditable())) {
/* 1536 */           if (basicComboBoxUI.isTableCellEditor()) {
/*      */ 
/*      */             
/* 1539 */             jComboBox.setSelectedIndex(basicComboBoxUI.popup.getList()
/* 1540 */                 .getSelectedIndex());
/*      */           } else {
/*      */             
/* 1543 */             jComboBox.setPopupVisible(!jComboBox.isPopupVisible());
/*      */           }
/*      */         
/*      */         }
/* 1547 */       } else if (str == "selectPrevious") {
/* 1548 */         if (basicComboBoxUI != null) {
/* 1549 */           if (basicComboBoxUI.isPopupVisible(jComboBox)) {
/* 1550 */             basicComboBoxUI.selectPreviousPossibleValue();
/*      */           }
/* 1552 */           else if (DefaultLookup.getBoolean(jComboBox, basicComboBoxUI, "ComboBox.showPopupOnNavigation", false)) {
/*      */             
/* 1554 */             basicComboBoxUI.setPopupVisible(jComboBox, true);
/*      */           }
/*      */         
/*      */         }
/* 1558 */       } else if (str == "selectPrevious2") {
/*      */ 
/*      */         
/* 1561 */         if (jComboBox.isShowing() && basicComboBoxUI != null) {
/* 1562 */           if (jComboBox.isEditable() && !jComboBox.isPopupVisible()) {
/* 1563 */             jComboBox.setPopupVisible(true);
/*      */           } else {
/* 1565 */             basicComboBoxUI.selectPreviousPossibleValue();
/*      */           }
/*      */         
/*      */         }
/*      */       }
/* 1570 */       else if (str == "enterPressed") {
/* 1571 */         if (jComboBox.isPopupVisible()) {
/*      */ 
/*      */           
/* 1574 */           if (UIManager.getBoolean("ComboBox.noActionOnKeyNavigation")) {
/* 1575 */             Object object = basicComboBoxUI.popup.getList().getSelectedValue();
/* 1576 */             if (object != null) {
/* 1577 */               jComboBox.getEditor().setItem(object);
/* 1578 */               jComboBox.setSelectedItem(object);
/*      */             } 
/* 1580 */             jComboBox.setPopupVisible(false);
/*      */           }
/*      */           else {
/*      */             
/* 1584 */             boolean bool = UIManager.getBoolean("ComboBox.isEnterSelectablePopup");
/* 1585 */             if (!jComboBox.isEditable() || bool || basicComboBoxUI
/* 1586 */               .isTableCellEditor) {
/* 1587 */               Object object = basicComboBoxUI.popup.getList().getSelectedValue();
/* 1588 */               if (object != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1593 */                 jComboBox.getEditor().setItem(object);
/* 1594 */                 jComboBox.setSelectedItem(object);
/*      */               } 
/*      */             } 
/* 1597 */             jComboBox.setPopupVisible(false);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1602 */           if (basicComboBoxUI.isTableCellEditor && !jComboBox.isEditable()) {
/* 1603 */             jComboBox.setSelectedItem(jComboBox.getSelectedItem());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1608 */           JRootPane jRootPane = SwingUtilities.getRootPane(jComboBox);
/* 1609 */           if (jRootPane != null) {
/* 1610 */             InputMap inputMap = jRootPane.getInputMap(2);
/* 1611 */             ActionMap actionMap = jRootPane.getActionMap();
/* 1612 */             if (inputMap != null && actionMap != null) {
/* 1613 */               Object object = inputMap.get(KeyStroke.getKeyStroke(10, 0));
/* 1614 */               if (object != null) {
/* 1615 */                 Action action = actionMap.get(object);
/* 1616 */                 if (action != null) {
/* 1617 */                   action.actionPerformed(new ActionEvent(jRootPane, param1ActionEvent
/* 1618 */                         .getID(), param1ActionEvent.getActionCommand(), param1ActionEvent
/* 1619 */                         .getWhen(), param1ActionEvent.getModifiers()));
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private int getNextIndex(JComboBox param1JComboBox, String param1String) {
/* 1629 */       int i = param1JComboBox.getMaximumRowCount();
/*      */       
/* 1631 */       int j = param1JComboBox.getSelectedIndex();
/* 1632 */       if (UIManager.getBoolean("ComboBox.noActionOnKeyNavigation") && param1JComboBox
/* 1633 */         .getUI() instanceof BasicComboBoxUI) {
/* 1634 */         j = ((BasicComboBoxUI)param1JComboBox.getUI()).listBox.getSelectedIndex();
/*      */       }
/*      */       
/* 1637 */       if (param1String == "pageUpPassThrough") {
/* 1638 */         int k = j - i;
/* 1639 */         return (k < 0) ? 0 : k;
/*      */       } 
/* 1641 */       if (param1String == "pageDownPassThrough") {
/* 1642 */         int k = j + i;
/* 1643 */         int m = param1JComboBox.getItemCount();
/* 1644 */         return (k < m) ? k : (m - 1);
/*      */       } 
/* 1646 */       if (param1String == "homePassThrough") {
/* 1647 */         return 0;
/*      */       }
/* 1649 */       if (param1String == "endPassThrough") {
/* 1650 */         return param1JComboBox.getItemCount() - 1;
/*      */       }
/* 1652 */       return param1JComboBox.getSelectedIndex();
/*      */     }
/*      */     
/*      */     public boolean isEnabled(Object param1Object) {
/* 1656 */       if (getName() == "hidePopup") {
/* 1657 */         return (param1Object != null && ((JComboBox)param1Object).isPopupVisible());
/*      */       }
/* 1659 */       return true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements ActionListener, FocusListener, KeyListener, LayoutManager, ListDataListener, PropertyChangeListener
/*      */   {
/*      */     private Handler() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1677 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1678 */       if (param1PropertyChangeEvent.getSource() == BasicComboBoxUI.this.editor) {
/*      */ 
/*      */ 
/*      */         
/* 1682 */         if ("border".equals(str)) {
/* 1683 */           BasicComboBoxUI.this.isMinimumSizeDirty = true;
/* 1684 */           BasicComboBoxUI.this.isDisplaySizeDirty = true;
/* 1685 */           BasicComboBoxUI.this.comboBox.revalidate();
/*      */         } 
/*      */       } else {
/* 1688 */         JComboBox jComboBox = (JComboBox)param1PropertyChangeEvent.getSource();
/* 1689 */         if (str == "model") {
/* 1690 */           ComboBoxModel comboBoxModel1 = (ComboBoxModel)param1PropertyChangeEvent.getNewValue();
/* 1691 */           ComboBoxModel comboBoxModel2 = (ComboBoxModel)param1PropertyChangeEvent.getOldValue();
/*      */           
/* 1693 */           if (comboBoxModel2 != null && BasicComboBoxUI.this.listDataListener != null) {
/* 1694 */             comboBoxModel2.removeListDataListener(BasicComboBoxUI.this.listDataListener);
/*      */           }
/*      */           
/* 1697 */           if (comboBoxModel1 != null && BasicComboBoxUI.this.listDataListener != null) {
/* 1698 */             comboBoxModel1.addListDataListener(BasicComboBoxUI.this.listDataListener);
/*      */           }
/*      */           
/* 1701 */           if (BasicComboBoxUI.this.editor != null) {
/* 1702 */             jComboBox.configureEditor(jComboBox.getEditor(), jComboBox.getSelectedItem());
/*      */           }
/* 1704 */           BasicComboBoxUI.this.isMinimumSizeDirty = true;
/* 1705 */           BasicComboBoxUI.this.isDisplaySizeDirty = true;
/* 1706 */           jComboBox.revalidate();
/* 1707 */           jComboBox.repaint();
/*      */         }
/* 1709 */         else if (str == "editor" && jComboBox.isEditable()) {
/* 1710 */           BasicComboBoxUI.this.addEditor();
/* 1711 */           jComboBox.revalidate();
/*      */         }
/* 1713 */         else if (str == "editable") {
/* 1714 */           if (jComboBox.isEditable()) {
/* 1715 */             jComboBox.setRequestFocusEnabled(false);
/* 1716 */             BasicComboBoxUI.this.addEditor();
/*      */           } else {
/* 1718 */             jComboBox.setRequestFocusEnabled(true);
/* 1719 */             BasicComboBoxUI.this.removeEditor();
/*      */           } 
/* 1721 */           BasicComboBoxUI.this.updateToolTipTextForChildren();
/* 1722 */           jComboBox.revalidate();
/*      */         }
/* 1724 */         else if (str == "enabled") {
/* 1725 */           boolean bool = jComboBox.isEnabled();
/* 1726 */           if (BasicComboBoxUI.this.editor != null)
/* 1727 */             BasicComboBoxUI.this.editor.setEnabled(bool); 
/* 1728 */           if (BasicComboBoxUI.this.arrowButton != null)
/* 1729 */             BasicComboBoxUI.this.arrowButton.setEnabled(bool); 
/* 1730 */           jComboBox.repaint();
/*      */         }
/* 1732 */         else if (str == "focusable") {
/* 1733 */           boolean bool = jComboBox.isFocusable();
/* 1734 */           if (BasicComboBoxUI.this.editor != null)
/* 1735 */             BasicComboBoxUI.this.editor.setFocusable(bool); 
/* 1736 */           if (BasicComboBoxUI.this.arrowButton != null)
/* 1737 */             BasicComboBoxUI.this.arrowButton.setFocusable(bool); 
/* 1738 */           jComboBox.repaint();
/*      */         }
/* 1740 */         else if (str == "maximumRowCount") {
/* 1741 */           if (BasicComboBoxUI.this.isPopupVisible(jComboBox)) {
/* 1742 */             BasicComboBoxUI.this.setPopupVisible(jComboBox, false);
/* 1743 */             BasicComboBoxUI.this.setPopupVisible(jComboBox, true);
/*      */           }
/*      */         
/* 1746 */         } else if (str == "font") {
/* 1747 */           BasicComboBoxUI.this.listBox.setFont(jComboBox.getFont());
/* 1748 */           if (BasicComboBoxUI.this.editor != null) {
/* 1749 */             BasicComboBoxUI.this.editor.setFont(jComboBox.getFont());
/*      */           }
/* 1751 */           BasicComboBoxUI.this.isMinimumSizeDirty = true;
/* 1752 */           BasicComboBoxUI.this.isDisplaySizeDirty = true;
/* 1753 */           jComboBox.validate();
/*      */         }
/* 1755 */         else if (str == "ToolTipText") {
/* 1756 */           BasicComboBoxUI.this.updateToolTipTextForChildren();
/*      */         }
/* 1758 */         else if (str == "JComboBox.isTableCellEditor") {
/* 1759 */           Boolean bool = (Boolean)param1PropertyChangeEvent.getNewValue();
/* 1760 */           BasicComboBoxUI.this.isTableCellEditor = bool.equals(Boolean.TRUE);
/*      */         }
/* 1762 */         else if (str == "prototypeDisplayValue") {
/* 1763 */           BasicComboBoxUI.this.isMinimumSizeDirty = true;
/* 1764 */           BasicComboBoxUI.this.isDisplaySizeDirty = true;
/* 1765 */           jComboBox.revalidate();
/*      */         }
/* 1767 */         else if (str == "renderer") {
/* 1768 */           BasicComboBoxUI.this.isMinimumSizeDirty = true;
/* 1769 */           BasicComboBoxUI.this.isDisplaySizeDirty = true;
/* 1770 */           jComboBox.revalidate();
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
/*      */ 
/*      */     
/*      */     public void keyPressed(KeyEvent param1KeyEvent) {
/* 1785 */       if (BasicComboBoxUI.this.isNavigationKey(param1KeyEvent.getKeyCode(), param1KeyEvent.getModifiers())) {
/* 1786 */         BasicComboBoxUI.this.lastTime = 0L;
/* 1787 */       } else if (BasicComboBoxUI.this.comboBox.isEnabled() && BasicComboBoxUI.this.comboBox.getModel().getSize() != 0 && 
/* 1788 */         isTypeAheadKey(param1KeyEvent) && param1KeyEvent.getKeyChar() != Character.MAX_VALUE) {
/* 1789 */         BasicComboBoxUI.this.time = param1KeyEvent.getWhen();
/* 1790 */         if (BasicComboBoxUI.this.comboBox.selectWithKeyChar(param1KeyEvent.getKeyChar())) {
/* 1791 */           param1KeyEvent.consume();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void keyTyped(KeyEvent param1KeyEvent) {}
/*      */ 
/*      */     
/*      */     public void keyReleased(KeyEvent param1KeyEvent) {}
/*      */     
/*      */     private boolean isTypeAheadKey(KeyEvent param1KeyEvent) {
/* 1803 */       return (!param1KeyEvent.isAltDown() && !BasicGraphicsUtils.isMenuShortcutKeyDown(param1KeyEvent));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 1814 */       ComboBoxEditor comboBoxEditor = BasicComboBoxUI.this.comboBox.getEditor();
/*      */       
/* 1816 */       if (comboBoxEditor != null && param1FocusEvent
/* 1817 */         .getSource() == comboBoxEditor.getEditorComponent()) {
/*      */         return;
/*      */       }
/* 1820 */       BasicComboBoxUI.this.hasFocus = true;
/* 1821 */       BasicComboBoxUI.this.comboBox.repaint();
/*      */       
/* 1823 */       if (BasicComboBoxUI.this.comboBox.isEditable() && BasicComboBoxUI.this.editor != null) {
/* 1824 */         BasicComboBoxUI.this.editor.requestFocus();
/*      */       }
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1829 */       ComboBoxEditor comboBoxEditor = BasicComboBoxUI.this.comboBox.getEditor();
/* 1830 */       if (comboBoxEditor != null && param1FocusEvent
/* 1831 */         .getSource() == comboBoxEditor.getEditorComponent()) {
/* 1832 */         Object object1 = comboBoxEditor.getItem();
/*      */         
/* 1834 */         Object object2 = BasicComboBoxUI.this.comboBox.getSelectedItem();
/* 1835 */         if (!param1FocusEvent.isTemporary() && object1 != null && 
/* 1836 */           !object1.equals((object2 == null) ? "" : object2)) {
/* 1837 */           BasicComboBoxUI.this.comboBox
/* 1838 */             .actionPerformed(new ActionEvent(comboBoxEditor, 0, "", 
/* 1839 */                 EventQueue.getMostRecentEventTime(), 0));
/*      */         }
/*      */       } 
/*      */       
/* 1843 */       BasicComboBoxUI.this.hasFocus = false;
/* 1844 */       if (!param1FocusEvent.isTemporary()) {
/* 1845 */         BasicComboBoxUI.this.setPopupVisible(BasicComboBoxUI.this.comboBox, false);
/*      */       }
/* 1847 */       BasicComboBoxUI.this.comboBox.repaint();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void contentsChanged(ListDataEvent param1ListDataEvent) {
/* 1856 */       if (param1ListDataEvent.getIndex0() != -1 || param1ListDataEvent.getIndex1() != -1) {
/* 1857 */         BasicComboBoxUI.this.isMinimumSizeDirty = true;
/* 1858 */         BasicComboBoxUI.this.comboBox.revalidate();
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1863 */       if (BasicComboBoxUI.this.comboBox.isEditable() && BasicComboBoxUI.this.editor != null) {
/* 1864 */         BasicComboBoxUI.this.comboBox.configureEditor(BasicComboBoxUI.this.comboBox.getEditor(), BasicComboBoxUI.this.comboBox
/* 1865 */             .getSelectedItem());
/*      */       }
/*      */       
/* 1868 */       BasicComboBoxUI.this.isDisplaySizeDirty = true;
/* 1869 */       BasicComboBoxUI.this.comboBox.repaint();
/*      */     }
/*      */     
/*      */     public void intervalAdded(ListDataEvent param1ListDataEvent) {
/* 1873 */       contentsChanged(param1ListDataEvent);
/*      */     }
/*      */     
/*      */     public void intervalRemoved(ListDataEvent param1ListDataEvent) {
/* 1877 */       contentsChanged(param1ListDataEvent);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1892 */       return param1Container.getPreferredSize();
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1896 */       return param1Container.getMinimumSize();
/*      */     }
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/* 1900 */       JComboBox jComboBox = (JComboBox)param1Container;
/* 1901 */       int i = jComboBox.getWidth();
/* 1902 */       int j = jComboBox.getHeight();
/*      */       
/* 1904 */       Insets insets = BasicComboBoxUI.this.getInsets();
/* 1905 */       int k = j - insets.top + insets.bottom;
/* 1906 */       int m = k;
/* 1907 */       if (BasicComboBoxUI.this.arrowButton != null) {
/* 1908 */         Insets insets1 = BasicComboBoxUI.this.arrowButton.getInsets();
/*      */ 
/*      */         
/* 1911 */         m = BasicComboBoxUI.this.squareButton ? k : ((BasicComboBoxUI.this.arrowButton.getPreferredSize()).width + insets1.left + insets1.right);
/*      */       } 
/*      */ 
/*      */       
/* 1915 */       if (BasicComboBoxUI.this.arrowButton != null) {
/* 1916 */         if (BasicGraphicsUtils.isLeftToRight(jComboBox)) {
/* 1917 */           BasicComboBoxUI.this.arrowButton.setBounds(i - insets.right + m, insets.top, m, k);
/*      */         } else {
/*      */           
/* 1920 */           BasicComboBoxUI.this.arrowButton.setBounds(insets.left, insets.top, m, k);
/*      */         } 
/*      */       }
/*      */       
/* 1924 */       if (BasicComboBoxUI.this.editor != null) {
/* 1925 */         Rectangle rectangle = BasicComboBoxUI.this.rectangleForCurrentValue();
/* 1926 */         BasicComboBoxUI.this.editor.setBounds(rectangle);
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
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1940 */       Object object = BasicComboBoxUI.this.comboBox.getEditor().getItem();
/* 1941 */       if (object != null) {
/* 1942 */         if (!BasicComboBoxUI.this.comboBox.isPopupVisible() && !object.equals(BasicComboBoxUI.this.comboBox.getSelectedItem())) {
/* 1943 */           BasicComboBoxUI.this.comboBox.setSelectedItem(BasicComboBoxUI.this.comboBox.getEditor().getItem());
/*      */         }
/* 1945 */         ActionMap actionMap = BasicComboBoxUI.this.comboBox.getActionMap();
/* 1946 */         if (actionMap != null) {
/* 1947 */           Action action = actionMap.get("enterPressed");
/* 1948 */           if (action != null)
/* 1949 */             action.actionPerformed(new ActionEvent(BasicComboBoxUI.this.comboBox, param1ActionEvent.getID(), param1ActionEvent
/* 1950 */                   .getActionCommand(), param1ActionEvent
/* 1951 */                   .getModifiers())); 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   class DefaultKeySelectionManager
/*      */     implements JComboBox.KeySelectionManager, UIResource {
/* 1959 */     private String prefix = "";
/* 1960 */     private String typedString = "";
/*      */     
/*      */     public int selectionForKey(char param1Char, ComboBoxModel param1ComboBoxModel) {
/* 1963 */       if (BasicComboBoxUI.this.lastTime == 0L) {
/* 1964 */         this.prefix = "";
/* 1965 */         this.typedString = "";
/*      */       } 
/* 1967 */       boolean bool = true;
/*      */       
/* 1969 */       int i = BasicComboBoxUI.this.comboBox.getSelectedIndex();
/* 1970 */       if (BasicComboBoxUI.this.time - BasicComboBoxUI.this.lastTime < BasicComboBoxUI.this.timeFactor) {
/* 1971 */         this.typedString += param1Char;
/* 1972 */         if (this.prefix.length() == 1 && param1Char == this.prefix.charAt(0)) {
/*      */ 
/*      */           
/* 1975 */           i++;
/*      */         } else {
/* 1977 */           this.prefix = this.typedString;
/*      */         } 
/*      */       } else {
/* 1980 */         i++;
/* 1981 */         this.typedString = "" + param1Char;
/* 1982 */         this.prefix = this.typedString;
/*      */       } 
/* 1984 */       BasicComboBoxUI.this.lastTime = BasicComboBoxUI.this.time;
/*      */       
/* 1986 */       if (i < 0 || i >= param1ComboBoxModel.getSize()) {
/* 1987 */         bool = false;
/* 1988 */         i = 0;
/*      */       } 
/* 1990 */       int j = BasicComboBoxUI.this.listBox.getNextMatch(this.prefix, i, Position.Bias.Forward);
/*      */       
/* 1992 */       if (j < 0 && bool) {
/* 1993 */         j = BasicComboBoxUI.this.listBox.getNextMatch(this.prefix, 0, Position.Bias.Forward);
/*      */       }
/*      */       
/* 1996 */       return j;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicComboBoxUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */