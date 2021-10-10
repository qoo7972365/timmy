/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseListener;
/*      */ import java.awt.event.MouseMotionListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Vector;
/*      */ import javax.swing.Action;
/*      */ import javax.swing.ActionMap;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JViewport;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ComponentInputMapUIResource;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.TabbedPaneUI;
/*      */ import javax.swing.plaf.UIResource;
/*      */ import javax.swing.text.View;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.SwingUtilities2;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BasicTabbedPaneUI
/*      */   extends TabbedPaneUI
/*      */   implements SwingConstants
/*      */ {
/*      */   protected JTabbedPane tabPane;
/*      */   protected Color highlight;
/*      */   protected Color lightHighlight;
/*      */   protected Color shadow;
/*      */   protected Color darkShadow;
/*      */   protected Color focus;
/*      */   private Color selectedColor;
/*      */   protected int textIconGap;
/*      */   protected int tabRunOverlay;
/*      */   protected Insets tabInsets;
/*      */   protected Insets selectedTabPadInsets;
/*      */   protected Insets tabAreaInsets;
/*      */   protected Insets contentBorderInsets;
/*      */   private boolean tabsOverlapBorder;
/*      */   private boolean tabsOpaque = true;
/*      */   private boolean contentOpaque = true;
/*      */   @Deprecated
/*      */   protected KeyStroke upKey;
/*      */   @Deprecated
/*      */   protected KeyStroke downKey;
/*      */   @Deprecated
/*      */   protected KeyStroke leftKey;
/*      */   @Deprecated
/*      */   protected KeyStroke rightKey;
/*  124 */   protected int[] tabRuns = new int[10];
/*  125 */   protected int runCount = 0;
/*  126 */   protected int selectedRun = -1;
/*  127 */   protected Rectangle[] rects = new Rectangle[0];
/*      */   
/*      */   protected int maxTabHeight;
/*      */   
/*      */   protected int maxTabWidth;
/*      */   
/*      */   protected ChangeListener tabChangeListener;
/*      */   
/*      */   protected PropertyChangeListener propertyChangeListener;
/*      */   
/*      */   protected MouseListener mouseListener;
/*      */   
/*      */   protected FocusListener focusListener;
/*  140 */   private Insets currentPadInsets = new Insets(0, 0, 0, 0);
/*  141 */   private Insets currentTabAreaInsets = new Insets(0, 0, 0, 0);
/*      */ 
/*      */ 
/*      */   
/*      */   private Component visibleComponent;
/*      */ 
/*      */ 
/*      */   
/*      */   private Vector<View> htmlViews;
/*      */ 
/*      */   
/*      */   private Hashtable<Integer, Integer> mnemonicToIndexMap;
/*      */ 
/*      */   
/*      */   private InputMap mnemonicInputMap;
/*      */ 
/*      */   
/*      */   private ScrollableTabSupport tabScroller;
/*      */ 
/*      */   
/*      */   private TabContainer tabContainer;
/*      */ 
/*      */   
/*  164 */   protected transient Rectangle calcRect = new Rectangle(0, 0, 0, 0);
/*      */ 
/*      */ 
/*      */   
/*      */   private int focusIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler handler;
/*      */ 
/*      */ 
/*      */   
/*      */   private int rolloverTabIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isRunsDirty;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean calculatedBaseline;
/*      */ 
/*      */ 
/*      */   
/*      */   private int baseline;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  194 */     return new BasicTabbedPaneUI();
/*      */   }
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  198 */     paramLazyActionMap.put(new Actions("navigateNext"));
/*  199 */     paramLazyActionMap.put(new Actions("navigatePrevious"));
/*  200 */     paramLazyActionMap.put(new Actions("navigateRight"));
/*  201 */     paramLazyActionMap.put(new Actions("navigateLeft"));
/*  202 */     paramLazyActionMap.put(new Actions("navigateUp"));
/*  203 */     paramLazyActionMap.put(new Actions("navigateDown"));
/*  204 */     paramLazyActionMap.put(new Actions("navigatePageUp"));
/*  205 */     paramLazyActionMap.put(new Actions("navigatePageDown"));
/*  206 */     paramLazyActionMap.put(new Actions("requestFocus"));
/*  207 */     paramLazyActionMap.put(new Actions("requestFocusForVisibleComponent"));
/*  208 */     paramLazyActionMap.put(new Actions("setSelectedIndex"));
/*  209 */     paramLazyActionMap.put(new Actions("selectTabWithFocus"));
/*  210 */     paramLazyActionMap.put(new Actions("scrollTabsForwardAction"));
/*  211 */     paramLazyActionMap.put(new Actions("scrollTabsBackwardAction"));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  217 */     this.tabPane = (JTabbedPane)paramJComponent;
/*      */     
/*  219 */     this.calculatedBaseline = false;
/*  220 */     this.rolloverTabIndex = -1;
/*  221 */     this.focusIndex = -1;
/*  222 */     paramJComponent.setLayout(createLayoutManager());
/*  223 */     installComponents();
/*  224 */     installDefaults();
/*  225 */     installListeners();
/*  226 */     installKeyboardActions();
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  230 */     uninstallKeyboardActions();
/*  231 */     uninstallListeners();
/*  232 */     uninstallDefaults();
/*  233 */     uninstallComponents();
/*  234 */     paramJComponent.setLayout((LayoutManager)null);
/*      */     
/*  236 */     this.tabPane = null;
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
/*      */   protected LayoutManager createLayoutManager() {
/*  250 */     if (this.tabPane.getTabLayoutPolicy() == 1) {
/*  251 */       return new TabbedPaneScrollLayout();
/*      */     }
/*  253 */     return new TabbedPaneLayout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean scrollableTabLayoutEnabled() {
/*  263 */     return this.tabPane.getLayout() instanceof TabbedPaneScrollLayout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installComponents() {
/*  273 */     if (scrollableTabLayoutEnabled() && 
/*  274 */       this.tabScroller == null) {
/*  275 */       this.tabScroller = new ScrollableTabSupport(this.tabPane.getTabPlacement());
/*  276 */       this.tabPane.add(this.tabScroller.viewport);
/*      */     } 
/*      */     
/*  279 */     installTabContainer();
/*      */   }
/*      */   
/*      */   private void installTabContainer() {
/*  283 */     for (byte b = 0; b < this.tabPane.getTabCount(); b++) {
/*  284 */       Component component = this.tabPane.getTabComponentAt(b);
/*  285 */       if (component != null) {
/*  286 */         if (this.tabContainer == null) {
/*  287 */           this.tabContainer = new TabContainer();
/*      */         }
/*  289 */         this.tabContainer.add(component);
/*      */       } 
/*      */     } 
/*  292 */     if (this.tabContainer == null) {
/*      */       return;
/*      */     }
/*  295 */     if (scrollableTabLayoutEnabled()) {
/*  296 */       this.tabScroller.tabPanel.add(this.tabContainer);
/*      */     } else {
/*  298 */       this.tabPane.add(this.tabContainer);
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
/*      */   protected JButton createScrollButton(int paramInt) {
/*  317 */     if (paramInt != 5 && paramInt != 1 && paramInt != 3 && paramInt != 7)
/*      */     {
/*  319 */       throw new IllegalArgumentException("Direction must be one of: SOUTH, NORTH, EAST or WEST");
/*      */     }
/*      */     
/*  322 */     return new ScrollableTabButton(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallComponents() {
/*  332 */     uninstallTabContainer();
/*  333 */     if (scrollableTabLayoutEnabled()) {
/*  334 */       this.tabPane.remove(this.tabScroller.viewport);
/*  335 */       this.tabPane.remove(this.tabScroller.scrollForwardButton);
/*  336 */       this.tabPane.remove(this.tabScroller.scrollBackwardButton);
/*  337 */       this.tabScroller = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void uninstallTabContainer() {
/*  342 */     if (this.tabContainer == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  347 */     this.tabContainer.notifyTabbedPane = false;
/*  348 */     this.tabContainer.removeAll();
/*  349 */     if (scrollableTabLayoutEnabled()) {
/*  350 */       this.tabContainer.remove(this.tabScroller.croppedEdge);
/*  351 */       this.tabScroller.tabPanel.remove(this.tabContainer);
/*      */     } else {
/*  353 */       this.tabPane.remove(this.tabContainer);
/*      */     } 
/*  355 */     this.tabContainer = null;
/*      */   }
/*      */   
/*      */   protected void installDefaults() {
/*  359 */     LookAndFeel.installColorsAndFont(this.tabPane, "TabbedPane.background", "TabbedPane.foreground", "TabbedPane.font");
/*      */     
/*  361 */     this.highlight = UIManager.getColor("TabbedPane.light");
/*  362 */     this.lightHighlight = UIManager.getColor("TabbedPane.highlight");
/*  363 */     this.shadow = UIManager.getColor("TabbedPane.shadow");
/*  364 */     this.darkShadow = UIManager.getColor("TabbedPane.darkShadow");
/*  365 */     this.focus = UIManager.getColor("TabbedPane.focus");
/*  366 */     this.selectedColor = UIManager.getColor("TabbedPane.selected");
/*      */     
/*  368 */     this.textIconGap = UIManager.getInt("TabbedPane.textIconGap");
/*  369 */     this.tabInsets = UIManager.getInsets("TabbedPane.tabInsets");
/*  370 */     this.selectedTabPadInsets = UIManager.getInsets("TabbedPane.selectedTabPadInsets");
/*  371 */     this.tabAreaInsets = UIManager.getInsets("TabbedPane.tabAreaInsets");
/*  372 */     this.tabsOverlapBorder = UIManager.getBoolean("TabbedPane.tabsOverlapBorder");
/*  373 */     this.contentBorderInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
/*  374 */     this.tabRunOverlay = UIManager.getInt("TabbedPane.tabRunOverlay");
/*  375 */     this.tabsOpaque = UIManager.getBoolean("TabbedPane.tabsOpaque");
/*  376 */     this.contentOpaque = UIManager.getBoolean("TabbedPane.contentOpaque");
/*  377 */     Object object = UIManager.get("TabbedPane.opaque");
/*  378 */     if (object == null) {
/*  379 */       object = Boolean.FALSE;
/*      */     }
/*  381 */     LookAndFeel.installProperty(this.tabPane, "opaque", object);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     if (this.tabInsets == null) this.tabInsets = new Insets(0, 4, 1, 4); 
/*  387 */     if (this.selectedTabPadInsets == null) this.selectedTabPadInsets = new Insets(2, 2, 2, 1); 
/*  388 */     if (this.tabAreaInsets == null) this.tabAreaInsets = new Insets(3, 2, 0, 2); 
/*  389 */     if (this.contentBorderInsets == null) this.contentBorderInsets = new Insets(2, 2, 3, 3); 
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/*  393 */     this.highlight = null;
/*  394 */     this.lightHighlight = null;
/*  395 */     this.shadow = null;
/*  396 */     this.darkShadow = null;
/*  397 */     this.focus = null;
/*  398 */     this.tabInsets = null;
/*  399 */     this.selectedTabPadInsets = null;
/*  400 */     this.tabAreaInsets = null;
/*  401 */     this.contentBorderInsets = null;
/*      */   }
/*      */   
/*      */   protected void installListeners() {
/*  405 */     if ((this.propertyChangeListener = createPropertyChangeListener()) != null) {
/*  406 */       this.tabPane.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*  408 */     if ((this.tabChangeListener = createChangeListener()) != null) {
/*  409 */       this.tabPane.addChangeListener(this.tabChangeListener);
/*      */     }
/*  411 */     if ((this.mouseListener = createMouseListener()) != null) {
/*  412 */       this.tabPane.addMouseListener(this.mouseListener);
/*      */     }
/*  414 */     this.tabPane.addMouseMotionListener(getHandler());
/*  415 */     if ((this.focusListener = createFocusListener()) != null) {
/*  416 */       this.tabPane.addFocusListener(this.focusListener);
/*      */     }
/*  418 */     this.tabPane.addContainerListener(getHandler());
/*  419 */     if (this.tabPane.getTabCount() > 0) {
/*  420 */       this.htmlViews = createHTMLVector();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void uninstallListeners() {
/*  425 */     if (this.mouseListener != null) {
/*  426 */       this.tabPane.removeMouseListener(this.mouseListener);
/*  427 */       this.mouseListener = null;
/*      */     } 
/*  429 */     this.tabPane.removeMouseMotionListener(getHandler());
/*  430 */     if (this.focusListener != null) {
/*  431 */       this.tabPane.removeFocusListener(this.focusListener);
/*  432 */       this.focusListener = null;
/*      */     } 
/*      */     
/*  435 */     this.tabPane.removeContainerListener(getHandler());
/*  436 */     if (this.htmlViews != null) {
/*  437 */       this.htmlViews.removeAllElements();
/*  438 */       this.htmlViews = null;
/*      */     } 
/*  440 */     if (this.tabChangeListener != null) {
/*  441 */       this.tabPane.removeChangeListener(this.tabChangeListener);
/*  442 */       this.tabChangeListener = null;
/*      */     } 
/*  444 */     if (this.propertyChangeListener != null) {
/*  445 */       this.tabPane.removePropertyChangeListener(this.propertyChangeListener);
/*  446 */       this.propertyChangeListener = null;
/*      */     } 
/*  448 */     this.handler = null;
/*      */   }
/*      */   
/*      */   protected MouseListener createMouseListener() {
/*  452 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected FocusListener createFocusListener() {
/*  456 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected ChangeListener createChangeListener() {
/*  460 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  464 */     return getHandler();
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  468 */     if (this.handler == null) {
/*  469 */       this.handler = new Handler();
/*      */     }
/*  471 */     return this.handler;
/*      */   }
/*      */   
/*      */   protected void installKeyboardActions() {
/*  475 */     InputMap inputMap = getInputMap(1);
/*      */ 
/*      */     
/*  478 */     SwingUtilities.replaceUIInputMap(this.tabPane, 1, inputMap);
/*      */ 
/*      */     
/*  481 */     inputMap = getInputMap(0);
/*  482 */     SwingUtilities.replaceUIInputMap(this.tabPane, 0, inputMap);
/*      */     
/*  484 */     LazyActionMap.installLazyActionMap(this.tabPane, BasicTabbedPaneUI.class, "TabbedPane.actionMap");
/*      */     
/*  486 */     updateMnemonics();
/*      */   }
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  490 */     if (paramInt == 1) {
/*  491 */       return (InputMap)DefaultLookup.get(this.tabPane, this, "TabbedPane.ancestorInputMap");
/*      */     }
/*      */     
/*  494 */     if (paramInt == 0) {
/*  495 */       return (InputMap)DefaultLookup.get(this.tabPane, this, "TabbedPane.focusInputMap");
/*      */     }
/*      */     
/*  498 */     return null;
/*      */   }
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  502 */     SwingUtilities.replaceUIActionMap(this.tabPane, null);
/*  503 */     SwingUtilities.replaceUIInputMap(this.tabPane, 1, null);
/*      */ 
/*      */     
/*  506 */     SwingUtilities.replaceUIInputMap(this.tabPane, 0, null);
/*      */     
/*  508 */     SwingUtilities.replaceUIInputMap(this.tabPane, 2, null);
/*      */ 
/*      */     
/*  511 */     this.mnemonicToIndexMap = null;
/*  512 */     this.mnemonicInputMap = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMnemonics() {
/*  520 */     resetMnemonics();
/*  521 */     for (int i = this.tabPane.getTabCount() - 1; i >= 0; 
/*  522 */       i--) {
/*  523 */       int j = this.tabPane.getMnemonicAt(i);
/*      */       
/*  525 */       if (j > 0) {
/*  526 */         addMnemonic(i, j);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetMnemonics() {
/*  535 */     if (this.mnemonicToIndexMap != null) {
/*  536 */       this.mnemonicToIndexMap.clear();
/*  537 */       this.mnemonicInputMap.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMnemonic(int paramInt1, int paramInt2) {
/*  545 */     if (this.mnemonicToIndexMap == null) {
/*  546 */       initMnemonics();
/*      */     }
/*  548 */     this.mnemonicInputMap.put(KeyStroke.getKeyStroke(paramInt2, BasicLookAndFeel.getFocusAcceleratorKeyMask()), "setSelectedIndex");
/*      */     
/*  550 */     this.mnemonicToIndexMap.put(Integer.valueOf(paramInt2), Integer.valueOf(paramInt1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initMnemonics() {
/*  557 */     this.mnemonicToIndexMap = new Hashtable<>();
/*  558 */     this.mnemonicInputMap = new ComponentInputMapUIResource(this.tabPane);
/*  559 */     this.mnemonicInputMap.setParent(SwingUtilities.getUIInputMap(this.tabPane, 2));
/*      */     
/*  561 */     SwingUtilities.replaceUIInputMap(this.tabPane, 2, this.mnemonicInputMap);
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
/*      */   private void setRolloverTab(int paramInt1, int paramInt2) {
/*  575 */     setRolloverTab(tabForCoordinate(this.tabPane, paramInt1, paramInt2, false));
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
/*      */   protected void setRolloverTab(int paramInt) {
/*  588 */     this.rolloverTabIndex = paramInt;
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
/*      */   protected int getRolloverTab() {
/*  600 */     return this.rolloverTabIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  605 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  610 */     return null;
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
/*      */   public int getBaseline(JComponent paramJComponent, int paramInt1, int paramInt2) {
/*  622 */     super.getBaseline(paramJComponent, paramInt1, paramInt2);
/*  623 */     int i = calculateBaselineIfNecessary();
/*  624 */     if (i != -1) {
/*  625 */       int j = this.tabPane.getTabPlacement();
/*  626 */       Insets insets1 = this.tabPane.getInsets();
/*  627 */       Insets insets2 = getTabAreaInsets(j);
/*  628 */       switch (j) {
/*      */         case 1:
/*  630 */           i += insets1.top + insets2.top;
/*  631 */           return i;
/*      */         case 3:
/*  633 */           i = paramInt2 - insets1.bottom - insets2.bottom - this.maxTabHeight + i;
/*      */           
/*  635 */           return i;
/*      */         case 2:
/*      */         case 4:
/*  638 */           i += insets1.top + insets2.top;
/*  639 */           return i;
/*      */       } 
/*      */     } 
/*  642 */     return -1;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior(JComponent paramJComponent) {
/*  655 */     super.getBaselineResizeBehavior(paramJComponent);
/*  656 */     switch (this.tabPane.getTabPlacement()) {
/*      */       case 1:
/*      */       case 2:
/*      */       case 4:
/*  660 */         return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */       case 3:
/*  662 */         return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
/*      */     } 
/*  664 */     return Component.BaselineResizeBehavior.OTHER;
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
/*      */   protected int getBaseline(int paramInt) {
/*  678 */     if (this.tabPane.getTabComponentAt(paramInt) != null) {
/*  679 */       int k = getBaselineOffset();
/*  680 */       if (k != 0)
/*      */       {
/*      */ 
/*      */         
/*  684 */         return -1;
/*      */       }
/*  686 */       Component component = this.tabPane.getTabComponentAt(paramInt);
/*  687 */       Dimension dimension = component.getPreferredSize();
/*  688 */       Insets insets = getTabInsets(this.tabPane.getTabPlacement(), paramInt);
/*  689 */       int m = this.maxTabHeight - insets.top - insets.bottom;
/*  690 */       return component.getBaseline(dimension.width, dimension.height) + (m - dimension.height) / 2 + insets.top;
/*      */     } 
/*      */ 
/*      */     
/*  694 */     View view = getTextViewForTab(paramInt);
/*  695 */     if (view != null) {
/*  696 */       int k = (int)view.getPreferredSpan(1);
/*  697 */       int m = BasicHTML.getHTMLBaseline(view, 
/*  698 */           (int)view.getPreferredSpan(0), k);
/*  699 */       if (m >= 0) {
/*  700 */         return this.maxTabHeight / 2 - k / 2 + m + 
/*  701 */           getBaselineOffset();
/*      */       }
/*  703 */       return -1;
/*      */     } 
/*      */     
/*  706 */     FontMetrics fontMetrics = getFontMetrics();
/*  707 */     int i = fontMetrics.getHeight();
/*  708 */     int j = fontMetrics.getAscent();
/*  709 */     return this.maxTabHeight / 2 - i / 2 + j + 
/*  710 */       getBaselineOffset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getBaselineOffset() {
/*  721 */     switch (this.tabPane.getTabPlacement()) {
/*      */       case 1:
/*  723 */         if (this.tabPane.getTabCount() > 1) {
/*  724 */           return 1;
/*      */         }
/*      */         
/*  727 */         return -1;
/*      */       
/*      */       case 3:
/*  730 */         if (this.tabPane.getTabCount() > 1) {
/*  731 */           return -1;
/*      */         }
/*      */         
/*  734 */         return 1;
/*      */     } 
/*      */     
/*  737 */     return this.maxTabHeight % 2;
/*      */   }
/*      */ 
/*      */   
/*      */   private int calculateBaselineIfNecessary() {
/*  742 */     if (!this.calculatedBaseline) {
/*  743 */       this.calculatedBaseline = true;
/*  744 */       this.baseline = -1;
/*  745 */       if (this.tabPane.getTabCount() > 0) {
/*  746 */         calculateBaseline();
/*      */       }
/*      */     } 
/*  749 */     return this.baseline;
/*      */   }
/*      */   
/*      */   private void calculateBaseline() {
/*  753 */     int i = this.tabPane.getTabCount();
/*  754 */     int j = this.tabPane.getTabPlacement();
/*  755 */     this.maxTabHeight = calculateMaxTabHeight(j);
/*  756 */     this.baseline = getBaseline(0);
/*  757 */     if (isHorizontalTabPlacement()) {
/*  758 */       for (byte b = 1; b < i; b++) {
/*  759 */         if (getBaseline(b) != this.baseline) {
/*  760 */           this.baseline = -1;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  767 */       FontMetrics fontMetrics = getFontMetrics();
/*  768 */       int k = fontMetrics.getHeight();
/*  769 */       int m = calculateTabHeight(j, 0, k);
/*  770 */       for (byte b = 1; b < i; b++) {
/*  771 */         int n = calculateTabHeight(j, b, k);
/*  772 */         if (m != n) {
/*      */           
/*  774 */           this.baseline = -1;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/*  784 */     int i = this.tabPane.getSelectedIndex();
/*  785 */     int j = this.tabPane.getTabPlacement();
/*      */     
/*  787 */     ensureCurrentLayout();
/*      */ 
/*      */     
/*  790 */     if (this.tabsOverlapBorder) {
/*  791 */       paintContentBorder(paramGraphics, j, i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  796 */     if (!scrollableTabLayoutEnabled()) {
/*  797 */       paintTabArea(paramGraphics, j, i);
/*      */     }
/*  799 */     if (!this.tabsOverlapBorder) {
/*  800 */       paintContentBorder(paramGraphics, j, i);
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
/*      */   protected void paintTabArea(Graphics paramGraphics, int paramInt1, int paramInt2) {
/*  822 */     int i = this.tabPane.getTabCount();
/*      */     
/*  824 */     Rectangle rectangle1 = new Rectangle();
/*  825 */     Rectangle rectangle2 = new Rectangle();
/*  826 */     Rectangle rectangle3 = paramGraphics.getClipBounds();
/*      */ 
/*      */     
/*  829 */     for (int j = this.runCount - 1; j >= 0; j--) {
/*  830 */       int k = this.tabRuns[j];
/*  831 */       int m = this.tabRuns[(j == this.runCount - 1) ? 0 : (j + 1)];
/*  832 */       int n = (m != 0) ? (m - 1) : (i - 1);
/*  833 */       for (int i1 = k; i1 <= n; i1++) {
/*  834 */         if (i1 != paramInt2 && this.rects[i1].intersects(rectangle3)) {
/*  835 */           paintTab(paramGraphics, paramInt1, this.rects, i1, rectangle1, rectangle2);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  842 */     if (paramInt2 >= 0 && this.rects[paramInt2].intersects(rectangle3)) {
/*  843 */       paintTab(paramGraphics, paramInt1, this.rects, paramInt2, rectangle1, rectangle2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintTab(Graphics paramGraphics, int paramInt1, Rectangle[] paramArrayOfRectangle, int paramInt2, Rectangle paramRectangle1, Rectangle paramRectangle2) {
/*  850 */     Rectangle rectangle = paramArrayOfRectangle[paramInt2];
/*  851 */     int i = this.tabPane.getSelectedIndex();
/*  852 */     boolean bool = (i == paramInt2) ? true : false;
/*      */     
/*  854 */     if (this.tabsOpaque || this.tabPane.isOpaque()) {
/*  855 */       paintTabBackground(paramGraphics, paramInt1, paramInt2, rectangle.x, rectangle.y, rectangle.width, rectangle.height, bool);
/*      */     }
/*      */ 
/*      */     
/*  859 */     paintTabBorder(paramGraphics, paramInt1, paramInt2, rectangle.x, rectangle.y, rectangle.width, rectangle.height, bool);
/*      */ 
/*      */     
/*  862 */     String str = this.tabPane.getTitleAt(paramInt2);
/*  863 */     Font font = this.tabPane.getFont();
/*  864 */     FontMetrics fontMetrics = SwingUtilities2.getFontMetrics(this.tabPane, paramGraphics, font);
/*  865 */     Icon icon = getIconForTab(paramInt2);
/*      */     
/*  867 */     layoutLabel(paramInt1, fontMetrics, paramInt2, str, icon, rectangle, paramRectangle1, paramRectangle2, bool);
/*      */ 
/*      */     
/*  870 */     if (this.tabPane.getTabComponentAt(paramInt2) == null) {
/*  871 */       String str1 = str;
/*      */       
/*  873 */       if (scrollableTabLayoutEnabled() && this.tabScroller.croppedEdge.isParamsSet() && this.tabScroller.croppedEdge
/*  874 */         .getTabIndex() == paramInt2 && isHorizontalTabPlacement()) {
/*      */         
/*  876 */         int j = this.tabScroller.croppedEdge.getCropline() - paramRectangle2.x - rectangle.x - this.tabScroller.croppedEdge.getCroppedSideWidth();
/*  877 */         str1 = SwingUtilities2.clipStringIfNecessary(null, fontMetrics, str, j);
/*  878 */       } else if (!scrollableTabLayoutEnabled() && isHorizontalTabPlacement()) {
/*  879 */         str1 = SwingUtilities2.clipStringIfNecessary(null, fontMetrics, str, paramRectangle2.width);
/*      */       } 
/*      */       
/*  882 */       paintText(paramGraphics, paramInt1, font, fontMetrics, paramInt2, str1, paramRectangle2, bool);
/*      */ 
/*      */       
/*  885 */       paintIcon(paramGraphics, paramInt1, paramInt2, icon, paramRectangle1, bool);
/*      */     } 
/*  887 */     paintFocusIndicator(paramGraphics, paramInt1, paramArrayOfRectangle, paramInt2, paramRectangle1, paramRectangle2, bool);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isHorizontalTabPlacement() {
/*  892 */     return (this.tabPane.getTabPlacement() == 1 || this.tabPane.getTabPlacement() == 3);
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
/*  917 */   private static int[] xCropLen = new int[] { 1, 1, 0, 0, 1, 1, 2, 2 };
/*  918 */   private static int[] yCropLen = new int[] { 0, 3, 3, 6, 6, 9, 9, 12 };
/*      */ 
/*      */   
/*      */   private static final int CROP_SEGMENT = 12;
/*      */ 
/*      */ 
/*      */   
/*      */   private static Polygon createCroppedTabShape(int paramInt1, Rectangle paramRectangle, int paramInt2) {
/*      */     int i, j, k, m;
/*  927 */     switch (paramInt1) {
/*      */       case 2:
/*      */       case 4:
/*  930 */         i = paramRectangle.width;
/*  931 */         j = paramRectangle.x;
/*  932 */         k = paramRectangle.x + paramRectangle.width;
/*  933 */         m = paramRectangle.y + paramRectangle.height;
/*      */         break;
/*      */ 
/*      */       
/*      */       default:
/*  938 */         i = paramRectangle.height;
/*  939 */         j = paramRectangle.y;
/*  940 */         k = paramRectangle.y + paramRectangle.height;
/*  941 */         m = paramRectangle.x + paramRectangle.width; break;
/*      */     } 
/*  943 */     int n = i / 12;
/*  944 */     if (i % 12 > 0) {
/*  945 */       n++;
/*      */     }
/*  947 */     int i1 = 2 + n * 8;
/*  948 */     int[] arrayOfInt1 = new int[i1];
/*  949 */     int[] arrayOfInt2 = new int[i1];
/*  950 */     byte b1 = 0;
/*      */     
/*  952 */     arrayOfInt1[b1] = m;
/*  953 */     arrayOfInt2[b1++] = k;
/*  954 */     arrayOfInt1[b1] = m;
/*  955 */     arrayOfInt2[b1++] = j;
/*  956 */     for (byte b2 = 0; b2 < n; b2++) {
/*  957 */       for (byte b = 0; b < xCropLen.length; b++) {
/*  958 */         arrayOfInt1[b1] = paramInt2 - xCropLen[b];
/*  959 */         arrayOfInt2[b1] = j + b2 * 12 + yCropLen[b];
/*  960 */         if (arrayOfInt2[b1] >= k) {
/*  961 */           arrayOfInt2[b1] = k;
/*  962 */           b1++;
/*      */           break;
/*      */         } 
/*  965 */         b1++;
/*      */       } 
/*      */     } 
/*  968 */     if (paramInt1 == 1 || paramInt1 == 3) {
/*  969 */       return new Polygon(arrayOfInt1, arrayOfInt2, b1);
/*      */     }
/*      */     
/*  972 */     return new Polygon(arrayOfInt2, arrayOfInt1, b1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintCroppedTabEdge(Graphics paramGraphics) {
/*  980 */     int n, i = this.tabScroller.croppedEdge.getTabIndex();
/*  981 */     int j = this.tabScroller.croppedEdge.getCropline();
/*      */     
/*  983 */     switch (this.tabPane.getTabPlacement()) {
/*      */       case 2:
/*      */       case 4:
/*  986 */         k = (this.rects[i]).x;
/*  987 */         m = j;
/*  988 */         n = k;
/*  989 */         paramGraphics.setColor(this.shadow);
/*  990 */         while (n <= k + (this.rects[i]).width) {
/*  991 */           for (byte b = 0; b < xCropLen.length; b += 2) {
/*  992 */             paramGraphics.drawLine(n + yCropLen[b], m - xCropLen[b], n + yCropLen[b + 1] - 1, m - xCropLen[b + 1]);
/*      */           }
/*      */           
/*  995 */           n += 12;
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1001 */     int k = j;
/* 1002 */     int m = (this.rects[i]).y;
/* 1003 */     int i1 = m;
/* 1004 */     paramGraphics.setColor(this.shadow);
/* 1005 */     while (i1 <= m + (this.rects[i]).height) {
/* 1006 */       for (byte b = 0; b < xCropLen.length; b += 2) {
/* 1007 */         paramGraphics.drawLine(k - xCropLen[b], i1 + yCropLen[b], k - xCropLen[b + 1], i1 + yCropLen[b + 1] - 1);
/*      */       }
/*      */       
/* 1010 */       i1 += 12;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void layoutLabel(int paramInt1, FontMetrics paramFontMetrics, int paramInt2, String paramString, Icon paramIcon, Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3, boolean paramBoolean) {
/* 1020 */     paramRectangle3.x = paramRectangle3.y = paramRectangle2.x = paramRectangle2.y = 0;
/*      */     
/* 1022 */     View view = getTextViewForTab(paramInt2);
/* 1023 */     if (view != null) {
/* 1024 */       this.tabPane.putClientProperty("html", view);
/*      */     }
/*      */     
/* 1027 */     SwingUtilities.layoutCompoundLabel(this.tabPane, paramFontMetrics, paramString, paramIcon, 0, 0, 0, 11, paramRectangle1, paramRectangle2, paramRectangle3, this.textIconGap);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1038 */     this.tabPane.putClientProperty("html", (Object)null);
/*      */     
/* 1040 */     int i = getTabLabelShiftX(paramInt1, paramInt2, paramBoolean);
/* 1041 */     int j = getTabLabelShiftY(paramInt1, paramInt2, paramBoolean);
/* 1042 */     paramRectangle2.x += i;
/* 1043 */     paramRectangle2.y += j;
/* 1044 */     paramRectangle3.x += i;
/* 1045 */     paramRectangle3.y += j;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintIcon(Graphics paramGraphics, int paramInt1, int paramInt2, Icon paramIcon, Rectangle paramRectangle, boolean paramBoolean) {
/* 1051 */     if (paramIcon != null) {
/* 1052 */       paramIcon.paintIcon(this.tabPane, paramGraphics, paramRectangle.x, paramRectangle.y);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintText(Graphics paramGraphics, int paramInt1, Font paramFont, FontMetrics paramFontMetrics, int paramInt2, String paramString, Rectangle paramRectangle, boolean paramBoolean) {
/* 1061 */     paramGraphics.setFont(paramFont);
/*      */     
/* 1063 */     View view = getTextViewForTab(paramInt2);
/* 1064 */     if (view != null) {
/*      */       
/* 1066 */       view.paint(paramGraphics, paramRectangle);
/*      */     } else {
/*      */       
/* 1069 */       int i = this.tabPane.getDisplayedMnemonicIndexAt(paramInt2);
/*      */       
/* 1071 */       if (this.tabPane.isEnabled() && this.tabPane.isEnabledAt(paramInt2)) {
/* 1072 */         Color color = this.tabPane.getForegroundAt(paramInt2);
/* 1073 */         if (paramBoolean && color instanceof UIResource) {
/* 1074 */           Color color1 = UIManager.getColor("TabbedPane.selectedForeground");
/*      */           
/* 1076 */           if (color1 != null) {
/* 1077 */             color = color1;
/*      */           }
/*      */         } 
/* 1080 */         paramGraphics.setColor(color);
/* 1081 */         SwingUtilities2.drawStringUnderlineCharAt(this.tabPane, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + paramFontMetrics
/*      */             
/* 1083 */             .getAscent());
/*      */       } else {
/*      */         
/* 1086 */         paramGraphics.setColor(this.tabPane.getBackgroundAt(paramInt2).brighter());
/* 1087 */         SwingUtilities2.drawStringUnderlineCharAt(this.tabPane, paramGraphics, paramString, i, paramRectangle.x, paramRectangle.y + paramFontMetrics
/*      */             
/* 1089 */             .getAscent());
/* 1090 */         paramGraphics.setColor(this.tabPane.getBackgroundAt(paramInt2).darker());
/* 1091 */         SwingUtilities2.drawStringUnderlineCharAt(this.tabPane, paramGraphics, paramString, i, paramRectangle.x - 1, paramRectangle.y + paramFontMetrics
/*      */             
/* 1093 */             .getAscent() - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getTabLabelShiftX(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1101 */     Rectangle rectangle = this.rects[paramInt2];
/* 1102 */     String str = paramBoolean ? "selectedLabelShift" : "labelShift";
/* 1103 */     int i = DefaultLookup.getInt(this.tabPane, this, "TabbedPane." + str, 1);
/*      */ 
/*      */     
/* 1106 */     switch (paramInt1) {
/*      */       case 2:
/* 1108 */         return i;
/*      */       case 4:
/* 1110 */         return -i;
/*      */     } 
/*      */ 
/*      */     
/* 1114 */     return rectangle.width % 2;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getTabLabelShiftY(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1119 */     Rectangle rectangle = this.rects[paramInt2];
/*      */     
/* 1121 */     int i = paramBoolean ? DefaultLookup.getInt(this.tabPane, this, "TabbedPane.selectedLabelShift", -1) : DefaultLookup.getInt(this.tabPane, this, "TabbedPane.labelShift", 1);
/*      */     
/* 1123 */     switch (paramInt1) {
/*      */       case 3:
/* 1125 */         return -i;
/*      */       case 2:
/*      */       case 4:
/* 1128 */         return rectangle.height % 2;
/*      */     } 
/*      */     
/* 1131 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintFocusIndicator(Graphics paramGraphics, int paramInt1, Rectangle[] paramArrayOfRectangle, int paramInt2, Rectangle paramRectangle1, Rectangle paramRectangle2, boolean paramBoolean) {
/* 1139 */     Rectangle rectangle = paramArrayOfRectangle[paramInt2];
/* 1140 */     if (this.tabPane.hasFocus() && paramBoolean) {
/*      */       int i, j, k, m;
/* 1142 */       paramGraphics.setColor(this.focus);
/* 1143 */       switch (paramInt1) {
/*      */         case 2:
/* 1145 */           i = rectangle.x + 3;
/* 1146 */           j = rectangle.y + 3;
/* 1147 */           k = rectangle.width - 5;
/* 1148 */           m = rectangle.height - 6;
/*      */           break;
/*      */         case 4:
/* 1151 */           i = rectangle.x + 2;
/* 1152 */           j = rectangle.y + 3;
/* 1153 */           k = rectangle.width - 5;
/* 1154 */           m = rectangle.height - 6;
/*      */           break;
/*      */         case 3:
/* 1157 */           i = rectangle.x + 3;
/* 1158 */           j = rectangle.y + 2;
/* 1159 */           k = rectangle.width - 6;
/* 1160 */           m = rectangle.height - 5;
/*      */           break;
/*      */         
/*      */         default:
/* 1164 */           i = rectangle.x + 3;
/* 1165 */           j = rectangle.y + 3;
/* 1166 */           k = rectangle.width - 6;
/* 1167 */           m = rectangle.height - 5; break;
/*      */       } 
/* 1169 */       BasicGraphicsUtils.drawDashedRect(paramGraphics, i, j, k, m);
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
/*      */   protected void paintTabBorder(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
/* 1182 */     paramGraphics.setColor(this.lightHighlight);
/*      */     
/* 1184 */     switch (paramInt1) {
/*      */       case 2:
/* 1186 */         paramGraphics.drawLine(paramInt3 + 1, paramInt4 + paramInt6 - 2, paramInt3 + 1, paramInt4 + paramInt6 - 2);
/* 1187 */         paramGraphics.drawLine(paramInt3, paramInt4 + 2, paramInt3, paramInt4 + paramInt6 - 3);
/* 1188 */         paramGraphics.drawLine(paramInt3 + 1, paramInt4 + 1, paramInt3 + 1, paramInt4 + 1);
/* 1189 */         paramGraphics.drawLine(paramInt3 + 2, paramInt4, paramInt3 + paramInt5 - 1, paramInt4);
/*      */         
/* 1191 */         paramGraphics.setColor(this.shadow);
/* 1192 */         paramGraphics.drawLine(paramInt3 + 2, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 2);
/*      */         
/* 1194 */         paramGraphics.setColor(this.darkShadow);
/* 1195 */         paramGraphics.drawLine(paramInt3 + 2, paramInt4 + paramInt6 - 1, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 1);
/*      */         return;
/*      */       case 4:
/* 1198 */         paramGraphics.drawLine(paramInt3, paramInt4, paramInt3 + paramInt5 - 3, paramInt4);
/*      */         
/* 1200 */         paramGraphics.setColor(this.shadow);
/* 1201 */         paramGraphics.drawLine(paramInt3, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 3, paramInt4 + paramInt6 - 2);
/* 1202 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + 2, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 3);
/*      */         
/* 1204 */         paramGraphics.setColor(this.darkShadow);
/* 1205 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + 1, paramInt3 + paramInt5 - 2, paramInt4 + 1);
/* 1206 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2);
/* 1207 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 1, paramInt4 + 2, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 3);
/* 1208 */         paramGraphics.drawLine(paramInt3, paramInt4 + paramInt6 - 1, paramInt3 + paramInt5 - 3, paramInt4 + paramInt6 - 1);
/*      */         return;
/*      */       case 3:
/* 1211 */         paramGraphics.drawLine(paramInt3, paramInt4, paramInt3, paramInt4 + paramInt6 - 3);
/* 1212 */         paramGraphics.drawLine(paramInt3 + 1, paramInt4 + paramInt6 - 2, paramInt3 + 1, paramInt4 + paramInt6 - 2);
/*      */         
/* 1214 */         paramGraphics.setColor(this.shadow);
/* 1215 */         paramGraphics.drawLine(paramInt3 + 2, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 3, paramInt4 + paramInt6 - 2);
/* 1216 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 3);
/*      */         
/* 1218 */         paramGraphics.setColor(this.darkShadow);
/* 1219 */         paramGraphics.drawLine(paramInt3 + 2, paramInt4 + paramInt6 - 1, paramInt3 + paramInt5 - 3, paramInt4 + paramInt6 - 1);
/* 1220 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2);
/* 1221 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 1, paramInt4, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 3);
/*      */         return;
/*      */     } 
/*      */     
/* 1225 */     paramGraphics.drawLine(paramInt3, paramInt4 + 2, paramInt3, paramInt4 + paramInt6 - 1);
/* 1226 */     paramGraphics.drawLine(paramInt3 + 1, paramInt4 + 1, paramInt3 + 1, paramInt4 + 1);
/* 1227 */     paramGraphics.drawLine(paramInt3 + 2, paramInt4, paramInt3 + paramInt5 - 3, paramInt4);
/*      */     
/* 1229 */     paramGraphics.setColor(this.shadow);
/* 1230 */     paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + 2, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 1);
/*      */     
/* 1232 */     paramGraphics.setColor(this.darkShadow);
/* 1233 */     paramGraphics.drawLine(paramInt3 + paramInt5 - 1, paramInt4 + 2, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 1);
/* 1234 */     paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + 1, paramInt3 + paramInt5 - 2, paramInt4 + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintTabBackground(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
/* 1242 */     paramGraphics.setColor((!paramBoolean || this.selectedColor == null) ? this.tabPane
/* 1243 */         .getBackgroundAt(paramInt2) : this.selectedColor);
/* 1244 */     switch (paramInt1) {
/*      */       case 2:
/* 1246 */         paramGraphics.fillRect(paramInt3 + 1, paramInt4 + 1, paramInt5 - 1, paramInt6 - 3);
/*      */         return;
/*      */       case 4:
/* 1249 */         paramGraphics.fillRect(paramInt3, paramInt4 + 1, paramInt5 - 2, paramInt6 - 3);
/*      */         return;
/*      */       case 3:
/* 1252 */         paramGraphics.fillRect(paramInt3 + 1, paramInt4, paramInt5 - 3, paramInt6 - 1);
/*      */         return;
/*      */     } 
/*      */     
/* 1256 */     paramGraphics.fillRect(paramInt3 + 1, paramInt4 + 1, paramInt5 - 3, paramInt6 - 1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void paintContentBorder(Graphics paramGraphics, int paramInt1, int paramInt2) {
/* 1261 */     int i = this.tabPane.getWidth();
/* 1262 */     int j = this.tabPane.getHeight();
/* 1263 */     Insets insets1 = this.tabPane.getInsets();
/* 1264 */     Insets insets2 = getTabAreaInsets(paramInt1);
/*      */     
/* 1266 */     int k = insets1.left;
/* 1267 */     int m = insets1.top;
/* 1268 */     int n = i - insets1.right - insets1.left;
/* 1269 */     int i1 = j - insets1.top - insets1.bottom;
/*      */     
/* 1271 */     switch (paramInt1) {
/*      */       case 2:
/* 1273 */         k += calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth);
/* 1274 */         if (this.tabsOverlapBorder) {
/* 1275 */           k -= insets2.right;
/*      */         }
/* 1277 */         n -= k - insets1.left;
/*      */         break;
/*      */       case 4:
/* 1280 */         n -= calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth);
/* 1281 */         if (this.tabsOverlapBorder) {
/* 1282 */           n += insets2.left;
/*      */         }
/*      */         break;
/*      */       case 3:
/* 1286 */         i1 -= calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight);
/* 1287 */         if (this.tabsOverlapBorder) {
/* 1288 */           i1 += insets2.top;
/*      */         }
/*      */         break;
/*      */       
/*      */       default:
/* 1293 */         m += calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight);
/* 1294 */         if (this.tabsOverlapBorder) {
/* 1295 */           m -= insets2.bottom;
/*      */         }
/* 1297 */         i1 -= m - insets1.top;
/*      */         break;
/*      */     } 
/* 1300 */     if (this.tabPane.getTabCount() > 0 && (this.contentOpaque || this.tabPane.isOpaque())) {
/*      */       
/* 1302 */       Color color = UIManager.getColor("TabbedPane.contentAreaColor");
/* 1303 */       if (color != null) {
/* 1304 */         paramGraphics.setColor(color);
/*      */       }
/* 1306 */       else if (this.selectedColor == null || paramInt2 == -1) {
/* 1307 */         paramGraphics.setColor(this.tabPane.getBackground());
/*      */       } else {
/*      */         
/* 1310 */         paramGraphics.setColor(this.selectedColor);
/*      */       } 
/* 1312 */       paramGraphics.fillRect(k, m, n, i1);
/*      */     } 
/*      */     
/* 1315 */     paintContentBorderTopEdge(paramGraphics, paramInt1, paramInt2, k, m, n, i1);
/* 1316 */     paintContentBorderLeftEdge(paramGraphics, paramInt1, paramInt2, k, m, n, i1);
/* 1317 */     paintContentBorderBottomEdge(paramGraphics, paramInt1, paramInt2, k, m, n, i1);
/* 1318 */     paintContentBorderRightEdge(paramGraphics, paramInt1, paramInt2, k, m, n, i1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintContentBorderTopEdge(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1326 */     Rectangle rectangle = (paramInt2 < 0) ? null : getTabBounds(paramInt2, this.calcRect);
/*      */     
/* 1328 */     paramGraphics.setColor(this.lightHighlight);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1334 */     if (paramInt1 != 1 || paramInt2 < 0 || rectangle.y + rectangle.height + 1 < paramInt4 || rectangle.x < paramInt3 || rectangle.x > paramInt3 + paramInt5) {
/*      */ 
/*      */       
/* 1337 */       paramGraphics.drawLine(paramInt3, paramInt4, paramInt3 + paramInt5 - 2, paramInt4);
/*      */     } else {
/*      */       
/* 1340 */       paramGraphics.drawLine(paramInt3, paramInt4, rectangle.x - 1, paramInt4);
/* 1341 */       if (rectangle.x + rectangle.width < paramInt3 + paramInt5 - 2) {
/* 1342 */         paramGraphics.drawLine(rectangle.x + rectangle.width, paramInt4, paramInt3 + paramInt5 - 2, paramInt4);
/*      */       } else {
/*      */         
/* 1345 */         paramGraphics.setColor(this.shadow);
/* 1346 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4, paramInt3 + paramInt5 - 2, paramInt4);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintContentBorderLeftEdge(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1355 */     Rectangle rectangle = (paramInt2 < 0) ? null : getTabBounds(paramInt2, this.calcRect);
/*      */     
/* 1357 */     paramGraphics.setColor(this.lightHighlight);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1363 */     if (paramInt1 != 2 || paramInt2 < 0 || rectangle.x + rectangle.width + 1 < paramInt3 || rectangle.y < paramInt4 || rectangle.y > paramInt4 + paramInt6) {
/*      */ 
/*      */       
/* 1366 */       paramGraphics.drawLine(paramInt3, paramInt4, paramInt3, paramInt4 + paramInt6 - 2);
/*      */     } else {
/*      */       
/* 1369 */       paramGraphics.drawLine(paramInt3, paramInt4, paramInt3, rectangle.y - 1);
/* 1370 */       if (rectangle.y + rectangle.height < paramInt4 + paramInt6 - 2) {
/* 1371 */         paramGraphics.drawLine(paramInt3, rectangle.y + rectangle.height, paramInt3, paramInt4 + paramInt6 - 2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintContentBorderBottomEdge(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1381 */     Rectangle rectangle = (paramInt2 < 0) ? null : getTabBounds(paramInt2, this.calcRect);
/*      */     
/* 1383 */     paramGraphics.setColor(this.shadow);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1389 */     if (paramInt1 != 3 || paramInt2 < 0 || rectangle.y - 1 > paramInt6 || rectangle.x < paramInt3 || rectangle.x > paramInt3 + paramInt5) {
/*      */ 
/*      */       
/* 1392 */       paramGraphics.drawLine(paramInt3 + 1, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2);
/* 1393 */       paramGraphics.setColor(this.darkShadow);
/* 1394 */       paramGraphics.drawLine(paramInt3, paramInt4 + paramInt6 - 1, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 1);
/*      */     } else {
/*      */       
/* 1397 */       paramGraphics.drawLine(paramInt3 + 1, paramInt4 + paramInt6 - 2, rectangle.x - 1, paramInt4 + paramInt6 - 2);
/* 1398 */       paramGraphics.setColor(this.darkShadow);
/* 1399 */       paramGraphics.drawLine(paramInt3, paramInt4 + paramInt6 - 1, rectangle.x - 1, paramInt4 + paramInt6 - 1);
/* 1400 */       if (rectangle.x + rectangle.width < paramInt3 + paramInt5 - 2) {
/* 1401 */         paramGraphics.setColor(this.shadow);
/* 1402 */         paramGraphics.drawLine(rectangle.x + rectangle.width, paramInt4 + paramInt6 - 2, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2);
/* 1403 */         paramGraphics.setColor(this.darkShadow);
/* 1404 */         paramGraphics.drawLine(rectangle.x + rectangle.width, paramInt4 + paramInt6 - 1, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintContentBorderRightEdge(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 1414 */     Rectangle rectangle = (paramInt2 < 0) ? null : getTabBounds(paramInt2, this.calcRect);
/*      */     
/* 1416 */     paramGraphics.setColor(this.shadow);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1422 */     if (paramInt1 != 4 || paramInt2 < 0 || rectangle.x - 1 > paramInt5 || rectangle.y < paramInt4 || rectangle.y > paramInt4 + paramInt6) {
/*      */ 
/*      */       
/* 1425 */       paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + 1, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 3);
/* 1426 */       paramGraphics.setColor(this.darkShadow);
/* 1427 */       paramGraphics.drawLine(paramInt3 + paramInt5 - 1, paramInt4, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 1);
/*      */     } else {
/*      */       
/* 1430 */       paramGraphics.drawLine(paramInt3 + paramInt5 - 2, paramInt4 + 1, paramInt3 + paramInt5 - 2, rectangle.y - 1);
/* 1431 */       paramGraphics.setColor(this.darkShadow);
/* 1432 */       paramGraphics.drawLine(paramInt3 + paramInt5 - 1, paramInt4, paramInt3 + paramInt5 - 1, rectangle.y - 1);
/*      */       
/* 1434 */       if (rectangle.y + rectangle.height < paramInt4 + paramInt6 - 2) {
/* 1435 */         paramGraphics.setColor(this.shadow);
/* 1436 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 2, rectangle.y + rectangle.height, paramInt3 + paramInt5 - 2, paramInt4 + paramInt6 - 2);
/*      */         
/* 1438 */         paramGraphics.setColor(this.darkShadow);
/* 1439 */         paramGraphics.drawLine(paramInt3 + paramInt5 - 1, rectangle.y + rectangle.height, paramInt3 + paramInt5 - 1, paramInt4 + paramInt6 - 2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureCurrentLayout() {
/* 1446 */     if (!this.tabPane.isValid()) {
/* 1447 */       this.tabPane.validate();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1453 */     if (!this.tabPane.isValid()) {
/* 1454 */       TabbedPaneLayout tabbedPaneLayout = (TabbedPaneLayout)this.tabPane.getLayout();
/* 1455 */       tabbedPaneLayout.calculateLayoutInfo();
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
/*      */   public Rectangle getTabBounds(JTabbedPane paramJTabbedPane, int paramInt) {
/* 1467 */     ensureCurrentLayout();
/* 1468 */     Rectangle rectangle = new Rectangle();
/* 1469 */     return getTabBounds(paramInt, rectangle);
/*      */   }
/*      */   
/*      */   public int getTabRunCount(JTabbedPane paramJTabbedPane) {
/* 1473 */     ensureCurrentLayout();
/* 1474 */     return this.runCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int tabForCoordinate(JTabbedPane paramJTabbedPane, int paramInt1, int paramInt2) {
/* 1482 */     return tabForCoordinate(paramJTabbedPane, paramInt1, paramInt2, true);
/*      */   }
/*      */ 
/*      */   
/*      */   private int tabForCoordinate(JTabbedPane paramJTabbedPane, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 1487 */     if (paramBoolean) {
/* 1488 */       ensureCurrentLayout();
/*      */     }
/* 1490 */     if (this.isRunsDirty)
/*      */     {
/*      */       
/* 1493 */       return -1;
/*      */     }
/* 1495 */     Point point = new Point(paramInt1, paramInt2);
/*      */     
/* 1497 */     if (scrollableTabLayoutEnabled()) {
/* 1498 */       translatePointToTabPanel(paramInt1, paramInt2, point);
/* 1499 */       Rectangle rectangle = this.tabScroller.viewport.getViewRect();
/* 1500 */       if (!rectangle.contains(point)) {
/* 1501 */         return -1;
/*      */       }
/*      */     } 
/* 1504 */     int i = this.tabPane.getTabCount();
/* 1505 */     for (byte b = 0; b < i; b++) {
/* 1506 */       if (this.rects[b].contains(point.x, point.y)) {
/* 1507 */         return b;
/*      */       }
/*      */     } 
/* 1510 */     return -1;
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
/*      */   protected Rectangle getTabBounds(int paramInt, Rectangle paramRectangle) {
/* 1534 */     paramRectangle.width = (this.rects[paramInt]).width;
/* 1535 */     paramRectangle.height = (this.rects[paramInt]).height;
/*      */     
/* 1537 */     if (scrollableTabLayoutEnabled()) {
/*      */ 
/*      */       
/* 1540 */       Point point1 = this.tabScroller.viewport.getLocation();
/* 1541 */       Point point2 = this.tabScroller.viewport.getViewPosition();
/* 1542 */       paramRectangle.x = (this.rects[paramInt]).x + point1.x - point2.x;
/* 1543 */       paramRectangle.y = (this.rects[paramInt]).y + point1.y - point2.y;
/*      */     } else {
/*      */       
/* 1546 */       paramRectangle.x = (this.rects[paramInt]).x;
/* 1547 */       paramRectangle.y = (this.rects[paramInt]).y;
/*      */     } 
/* 1549 */     return paramRectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getClosestTab(int paramInt1, int paramInt2) {
/* 1557 */     int i = 0;
/* 1558 */     int j = Math.min(this.rects.length, this.tabPane.getTabCount());
/* 1559 */     int k = j;
/* 1560 */     int m = this.tabPane.getTabPlacement();
/* 1561 */     boolean bool = (m == 1 || m == 3) ? true : false;
/* 1562 */     int n = bool ? paramInt1 : paramInt2;
/*      */     
/* 1564 */     while (i != k) {
/* 1565 */       int i2, i3, i1 = (k + i) / 2;
/*      */ 
/*      */ 
/*      */       
/* 1569 */       if (bool) {
/* 1570 */         i2 = (this.rects[i1]).x;
/* 1571 */         i3 = i2 + (this.rects[i1]).width;
/*      */       } else {
/*      */         
/* 1574 */         i2 = (this.rects[i1]).y;
/* 1575 */         i3 = i2 + (this.rects[i1]).height;
/*      */       } 
/* 1577 */       if (n < i2) {
/* 1578 */         k = i1;
/* 1579 */         if (i == k)
/* 1580 */           return Math.max(0, i1 - 1); 
/*      */         continue;
/*      */       } 
/* 1583 */       if (n >= i3) {
/* 1584 */         i = i1;
/* 1585 */         if (k - i <= 1) {
/* 1586 */           return Math.max(i1 + 1, j - 1);
/*      */         }
/*      */         continue;
/*      */       } 
/* 1590 */       return i1;
/*      */     } 
/*      */     
/* 1593 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Point translatePointToTabPanel(int paramInt1, int paramInt2, Point paramPoint) {
/* 1602 */     Point point1 = this.tabScroller.viewport.getLocation();
/* 1603 */     Point point2 = this.tabScroller.viewport.getViewPosition();
/* 1604 */     paramPoint.x = paramInt1 - point1.x + point2.x;
/* 1605 */     paramPoint.y = paramInt2 - point1.y + point2.y;
/* 1606 */     return paramPoint;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component getVisibleComponent() {
/* 1612 */     return this.visibleComponent;
/*      */   }
/*      */   
/*      */   protected void setVisibleComponent(Component paramComponent) {
/* 1616 */     if (this.visibleComponent != null && this.visibleComponent != paramComponent && this.visibleComponent
/*      */       
/* 1618 */       .getParent() == this.tabPane && this.visibleComponent
/* 1619 */       .isVisible())
/*      */     {
/* 1621 */       this.visibleComponent.setVisible(false);
/*      */     }
/* 1623 */     if (paramComponent != null && !paramComponent.isVisible()) {
/* 1624 */       paramComponent.setVisible(true);
/*      */     }
/* 1626 */     this.visibleComponent = paramComponent;
/*      */   }
/*      */   
/*      */   protected void assureRectsCreated(int paramInt) {
/* 1630 */     int i = this.rects.length;
/* 1631 */     if (paramInt != i) {
/* 1632 */       Rectangle[] arrayOfRectangle = new Rectangle[paramInt];
/* 1633 */       System.arraycopy(this.rects, 0, arrayOfRectangle, 0, 
/* 1634 */           Math.min(i, paramInt));
/* 1635 */       this.rects = arrayOfRectangle;
/* 1636 */       for (int j = i; j < paramInt; j++) {
/* 1637 */         this.rects[j] = new Rectangle();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void expandTabRunsArray() {
/* 1644 */     int i = this.tabRuns.length;
/* 1645 */     int[] arrayOfInt = new int[i + 10];
/* 1646 */     System.arraycopy(this.tabRuns, 0, arrayOfInt, 0, this.runCount);
/* 1647 */     this.tabRuns = arrayOfInt;
/*      */   }
/*      */   
/*      */   protected int getRunForTab(int paramInt1, int paramInt2) {
/* 1651 */     for (byte b = 0; b < this.runCount; b++) {
/* 1652 */       int i = this.tabRuns[b];
/* 1653 */       int j = lastTabInRun(paramInt1, b);
/* 1654 */       if (paramInt2 >= i && paramInt2 <= j) {
/* 1655 */         return b;
/*      */       }
/*      */     } 
/* 1658 */     return 0;
/*      */   }
/*      */   
/*      */   protected int lastTabInRun(int paramInt1, int paramInt2) {
/* 1662 */     if (this.runCount == 1) {
/* 1663 */       return paramInt1 - 1;
/*      */     }
/* 1665 */     boolean bool = (paramInt2 == this.runCount - 1) ? false : (paramInt2 + 1);
/* 1666 */     if (this.tabRuns[bool] == 0) {
/* 1667 */       return paramInt1 - 1;
/*      */     }
/* 1669 */     return this.tabRuns[bool] - 1;
/*      */   }
/*      */   
/*      */   protected int getTabRunOverlay(int paramInt) {
/* 1673 */     return this.tabRunOverlay;
/*      */   }
/*      */   
/*      */   protected int getTabRunIndent(int paramInt1, int paramInt2) {
/* 1677 */     return 0;
/*      */   }
/*      */   
/*      */   protected boolean shouldPadTabRun(int paramInt1, int paramInt2) {
/* 1681 */     return (this.runCount > 1);
/*      */   }
/*      */   
/*      */   protected boolean shouldRotateTabRuns(int paramInt) {
/* 1685 */     return true;
/*      */   }
/*      */   
/*      */   protected Icon getIconForTab(int paramInt) {
/* 1689 */     return (!this.tabPane.isEnabled() || !this.tabPane.isEnabledAt(paramInt)) ? this.tabPane
/* 1690 */       .getDisabledIconAt(paramInt) : this.tabPane.getIconAt(paramInt);
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
/*      */   protected View getTextViewForTab(int paramInt) {
/* 1705 */     if (this.htmlViews != null) {
/* 1706 */       return this.htmlViews.elementAt(paramInt);
/*      */     }
/* 1708 */     return null;
/*      */   }
/*      */   
/*      */   protected int calculateTabHeight(int paramInt1, int paramInt2, int paramInt3) {
/* 1712 */     int i = 0;
/* 1713 */     Component component = this.tabPane.getTabComponentAt(paramInt2);
/* 1714 */     if (component != null) {
/* 1715 */       i = (component.getPreferredSize()).height;
/*      */     } else {
/* 1717 */       View view = getTextViewForTab(paramInt2);
/* 1718 */       if (view != null) {
/*      */         
/* 1720 */         i += (int)view.getPreferredSpan(1);
/*      */       } else {
/*      */         
/* 1723 */         i += paramInt3;
/*      */       } 
/* 1725 */       Icon icon = getIconForTab(paramInt2);
/*      */       
/* 1727 */       if (icon != null) {
/* 1728 */         i = Math.max(i, icon.getIconHeight());
/*      */       }
/*      */     } 
/* 1731 */     Insets insets = getTabInsets(paramInt1, paramInt2);
/* 1732 */     i += insets.top + insets.bottom + 2;
/* 1733 */     return i;
/*      */   }
/*      */   
/*      */   protected int calculateMaxTabHeight(int paramInt) {
/* 1737 */     FontMetrics fontMetrics = getFontMetrics();
/* 1738 */     int i = this.tabPane.getTabCount();
/* 1739 */     int j = 0;
/* 1740 */     int k = fontMetrics.getHeight();
/* 1741 */     for (byte b = 0; b < i; b++) {
/* 1742 */       j = Math.max(calculateTabHeight(paramInt, b, k), j);
/*      */     }
/* 1744 */     return j;
/*      */   }
/*      */   
/*      */   protected int calculateTabWidth(int paramInt1, int paramInt2, FontMetrics paramFontMetrics) {
/* 1748 */     Insets insets = getTabInsets(paramInt1, paramInt2);
/* 1749 */     int i = insets.left + insets.right + 3;
/* 1750 */     Component component = this.tabPane.getTabComponentAt(paramInt2);
/* 1751 */     if (component != null) {
/* 1752 */       i += (component.getPreferredSize()).width;
/*      */     } else {
/* 1754 */       Icon icon = getIconForTab(paramInt2);
/* 1755 */       if (icon != null) {
/* 1756 */         i += icon.getIconWidth() + this.textIconGap;
/*      */       }
/* 1758 */       View view = getTextViewForTab(paramInt2);
/* 1759 */       if (view != null) {
/*      */         
/* 1761 */         i += (int)view.getPreferredSpan(0);
/*      */       } else {
/*      */         
/* 1764 */         String str = this.tabPane.getTitleAt(paramInt2);
/* 1765 */         i += SwingUtilities2.stringWidth(this.tabPane, paramFontMetrics, str);
/*      */       } 
/*      */     } 
/* 1768 */     return i;
/*      */   }
/*      */   
/*      */   protected int calculateMaxTabWidth(int paramInt) {
/* 1772 */     FontMetrics fontMetrics = getFontMetrics();
/* 1773 */     int i = this.tabPane.getTabCount();
/* 1774 */     int j = 0;
/* 1775 */     for (byte b = 0; b < i; b++) {
/* 1776 */       j = Math.max(calculateTabWidth(paramInt, b, fontMetrics), j);
/*      */     }
/* 1778 */     return j;
/*      */   }
/*      */   
/*      */   protected int calculateTabAreaHeight(int paramInt1, int paramInt2, int paramInt3) {
/* 1782 */     Insets insets = getTabAreaInsets(paramInt1);
/* 1783 */     int i = getTabRunOverlay(paramInt1);
/* 1784 */     return (paramInt2 > 0) ? (paramInt2 * (paramInt3 - i) + i + insets.top + insets.bottom) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int calculateTabAreaWidth(int paramInt1, int paramInt2, int paramInt3) {
/* 1791 */     Insets insets = getTabAreaInsets(paramInt1);
/* 1792 */     int i = getTabRunOverlay(paramInt1);
/* 1793 */     return (paramInt2 > 0) ? (paramInt2 * (paramInt3 - i) + i + insets.left + insets.right) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Insets getTabInsets(int paramInt1, int paramInt2) {
/* 1800 */     return this.tabInsets;
/*      */   }
/*      */   
/*      */   protected Insets getSelectedTabPadInsets(int paramInt) {
/* 1804 */     rotateInsets(this.selectedTabPadInsets, this.currentPadInsets, paramInt);
/* 1805 */     return this.currentPadInsets;
/*      */   }
/*      */   
/*      */   protected Insets getTabAreaInsets(int paramInt) {
/* 1809 */     rotateInsets(this.tabAreaInsets, this.currentTabAreaInsets, paramInt);
/* 1810 */     return this.currentTabAreaInsets;
/*      */   }
/*      */   
/*      */   protected Insets getContentBorderInsets(int paramInt) {
/* 1814 */     return this.contentBorderInsets;
/*      */   }
/*      */   
/*      */   protected FontMetrics getFontMetrics() {
/* 1818 */     Font font = this.tabPane.getFont();
/* 1819 */     return this.tabPane.getFontMetrics(font);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void navigateSelectedTab(int paramInt) {
/* 1826 */     int m, i = this.tabPane.getTabPlacement();
/*      */ 
/*      */     
/* 1829 */     int j = DefaultLookup.getBoolean(this.tabPane, this, "TabbedPane.selectionFollowsFocus", true) ? this.tabPane.getSelectedIndex() : getFocusIndex();
/* 1830 */     int k = this.tabPane.getTabCount();
/* 1831 */     boolean bool = BasicGraphicsUtils.isLeftToRight(this.tabPane);
/*      */ 
/*      */     
/* 1834 */     if (k <= 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1839 */     switch (i) {
/*      */       case 2:
/*      */       case 4:
/* 1842 */         switch (paramInt) {
/*      */           case 12:
/* 1844 */             selectNextTab(j);
/*      */             break;
/*      */           case 13:
/* 1847 */             selectPreviousTab(j);
/*      */             break;
/*      */           case 1:
/* 1850 */             selectPreviousTabInRun(j);
/*      */             break;
/*      */           case 5:
/* 1853 */             selectNextTabInRun(j);
/*      */             break;
/*      */           case 7:
/* 1856 */             m = getTabRunOffset(i, k, j, false);
/* 1857 */             selectAdjacentRunTab(i, j, m);
/*      */             break;
/*      */           case 3:
/* 1860 */             m = getTabRunOffset(i, k, j, true);
/* 1861 */             selectAdjacentRunTab(i, j, m);
/*      */             break;
/*      */         } 
/*      */ 
/*      */         
/*      */         return;
/*      */     } 
/*      */     
/* 1869 */     switch (paramInt) {
/*      */       case 12:
/* 1871 */         selectNextTab(j);
/*      */         break;
/*      */       case 13:
/* 1874 */         selectPreviousTab(j);
/*      */         break;
/*      */       case 1:
/* 1877 */         m = getTabRunOffset(i, k, j, false);
/* 1878 */         selectAdjacentRunTab(i, j, m);
/*      */         break;
/*      */       case 5:
/* 1881 */         m = getTabRunOffset(i, k, j, true);
/* 1882 */         selectAdjacentRunTab(i, j, m);
/*      */         break;
/*      */       case 3:
/* 1885 */         if (bool) {
/* 1886 */           selectNextTabInRun(j); break;
/*      */         } 
/* 1888 */         selectPreviousTabInRun(j);
/*      */         break;
/*      */       
/*      */       case 7:
/* 1892 */         if (bool) {
/* 1893 */           selectPreviousTabInRun(j); break;
/*      */         } 
/* 1895 */         selectNextTabInRun(j);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void selectNextTabInRun(int paramInt) {
/* 1904 */     int i = this.tabPane.getTabCount();
/* 1905 */     int j = getNextTabIndexInRun(i, paramInt);
/*      */     
/* 1907 */     while (j != paramInt && !this.tabPane.isEnabledAt(j)) {
/* 1908 */       j = getNextTabIndexInRun(i, j);
/*      */     }
/* 1910 */     navigateTo(j);
/*      */   }
/*      */   
/*      */   protected void selectPreviousTabInRun(int paramInt) {
/* 1914 */     int i = this.tabPane.getTabCount();
/* 1915 */     int j = getPreviousTabIndexInRun(i, paramInt);
/*      */     
/* 1917 */     while (j != paramInt && !this.tabPane.isEnabledAt(j)) {
/* 1918 */       j = getPreviousTabIndexInRun(i, j);
/*      */     }
/* 1920 */     navigateTo(j);
/*      */   }
/*      */   
/*      */   protected void selectNextTab(int paramInt) {
/* 1924 */     int i = getNextTabIndex(paramInt);
/*      */     
/* 1926 */     while (i != paramInt && !this.tabPane.isEnabledAt(i)) {
/* 1927 */       i = getNextTabIndex(i);
/*      */     }
/* 1929 */     navigateTo(i);
/*      */   }
/*      */   
/*      */   protected void selectPreviousTab(int paramInt) {
/* 1933 */     int i = getPreviousTabIndex(paramInt);
/*      */     
/* 1935 */     while (i != paramInt && !this.tabPane.isEnabledAt(i)) {
/* 1936 */       i = getPreviousTabIndex(i);
/*      */     }
/* 1938 */     navigateTo(i);
/*      */   }
/*      */   
/*      */   protected void selectAdjacentRunTab(int paramInt1, int paramInt2, int paramInt3) {
/*      */     int i;
/* 1943 */     if (this.runCount < 2) {
/*      */       return;
/*      */     }
/*      */     
/* 1947 */     Rectangle rectangle = this.rects[paramInt2];
/* 1948 */     switch (paramInt1) {
/*      */       case 2:
/*      */       case 4:
/* 1951 */         i = tabForCoordinate(this.tabPane, rectangle.x + rectangle.width / 2 + paramInt3, rectangle.y + rectangle.height / 2);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 1957 */         i = tabForCoordinate(this.tabPane, rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2 + paramInt3);
/*      */         break;
/*      */     } 
/* 1960 */     if (i != -1) {
/* 1961 */       while (!this.tabPane.isEnabledAt(i) && i != paramInt2) {
/* 1962 */         i = getNextTabIndex(i);
/*      */       }
/* 1964 */       navigateTo(i);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void navigateTo(int paramInt) {
/* 1969 */     if (DefaultLookup.getBoolean(this.tabPane, this, "TabbedPane.selectionFollowsFocus", true)) {
/*      */       
/* 1971 */       this.tabPane.setSelectedIndex(paramInt);
/*      */     } else {
/*      */       
/* 1974 */       setFocusIndex(paramInt, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   void setFocusIndex(int paramInt, boolean paramBoolean) {
/* 1979 */     if (paramBoolean && !this.isRunsDirty) {
/* 1980 */       repaintTab(this.focusIndex);
/* 1981 */       this.focusIndex = paramInt;
/* 1982 */       repaintTab(this.focusIndex);
/*      */     } else {
/*      */       
/* 1985 */       this.focusIndex = paramInt;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void repaintTab(int paramInt) {
/* 1995 */     if (!this.isRunsDirty && paramInt >= 0 && paramInt < this.tabPane.getTabCount()) {
/* 1996 */       this.tabPane.repaint(getTabBounds(this.tabPane, paramInt));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateFocusIndex() {
/* 2004 */     if (this.focusIndex >= this.tabPane.getTabCount()) {
/* 2005 */       setFocusIndex(this.tabPane.getSelectedIndex(), false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getFocusIndex() {
/* 2016 */     return this.focusIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getTabRunOffset(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 2021 */     int j, i = getRunForTab(paramInt2, paramInt3);
/*      */     
/* 2023 */     switch (paramInt1)
/*      */     { case 2:
/* 2025 */         if (i == 0) {
/*      */           
/* 2027 */           j = paramBoolean ? -(calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth) - this.maxTabWidth) : -this.maxTabWidth;
/*      */         
/*      */         }
/* 2030 */         else if (i == this.runCount - 1) {
/*      */ 
/*      */           
/* 2033 */           j = paramBoolean ? this.maxTabWidth : (calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth) - this.maxTabWidth);
/*      */         } else {
/* 2035 */           j = paramBoolean ? this.maxTabWidth : -this.maxTabWidth;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2082 */         return j;case 4: if (i == 0) { j = paramBoolean ? this.maxTabWidth : (calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth) - this.maxTabWidth); } else if (i == this.runCount - 1) { j = paramBoolean ? -(calculateTabAreaWidth(paramInt1, this.runCount, this.maxTabWidth) - this.maxTabWidth) : -this.maxTabWidth; } else { j = paramBoolean ? this.maxTabWidth : -this.maxTabWidth; }  return j;case 3: if (i == 0) { j = paramBoolean ? this.maxTabHeight : (calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight) - this.maxTabHeight); } else if (i == this.runCount - 1) { j = paramBoolean ? -(calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight) - this.maxTabHeight) : -this.maxTabHeight; } else { j = paramBoolean ? this.maxTabHeight : -this.maxTabHeight; }  return j; }  if (i == 0) { j = paramBoolean ? -(calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight) - this.maxTabHeight) : -this.maxTabHeight; } else if (i == this.runCount - 1) { j = paramBoolean ? this.maxTabHeight : (calculateTabAreaHeight(paramInt1, this.runCount, this.maxTabHeight) - this.maxTabHeight); } else { j = paramBoolean ? this.maxTabHeight : -this.maxTabHeight; }  return j;
/*      */   }
/*      */   
/*      */   protected int getPreviousTabIndex(int paramInt) {
/* 2086 */     int i = (paramInt - 1 >= 0) ? (paramInt - 1) : (this.tabPane.getTabCount() - 1);
/* 2087 */     return (i >= 0) ? i : 0;
/*      */   }
/*      */   
/*      */   protected int getNextTabIndex(int paramInt) {
/* 2091 */     return (paramInt + 1) % this.tabPane.getTabCount();
/*      */   }
/*      */   
/*      */   protected int getNextTabIndexInRun(int paramInt1, int paramInt2) {
/* 2095 */     if (this.runCount < 2) {
/* 2096 */       return getNextTabIndex(paramInt2);
/*      */     }
/* 2098 */     int i = getRunForTab(paramInt1, paramInt2);
/* 2099 */     int j = getNextTabIndex(paramInt2);
/* 2100 */     if (j == this.tabRuns[getNextTabRun(i)]) {
/* 2101 */       return this.tabRuns[i];
/*      */     }
/* 2103 */     return j;
/*      */   }
/*      */   
/*      */   protected int getPreviousTabIndexInRun(int paramInt1, int paramInt2) {
/* 2107 */     if (this.runCount < 2) {
/* 2108 */       return getPreviousTabIndex(paramInt2);
/*      */     }
/* 2110 */     int i = getRunForTab(paramInt1, paramInt2);
/* 2111 */     if (paramInt2 == this.tabRuns[i]) {
/* 2112 */       int j = this.tabRuns[getNextTabRun(i)] - 1;
/* 2113 */       return (j != -1) ? j : (paramInt1 - 1);
/*      */     } 
/* 2115 */     return getPreviousTabIndex(paramInt2);
/*      */   }
/*      */   
/*      */   protected int getPreviousTabRun(int paramInt) {
/* 2119 */     int i = (paramInt - 1 >= 0) ? (paramInt - 1) : (this.runCount - 1);
/* 2120 */     return (i >= 0) ? i : 0;
/*      */   }
/*      */   
/*      */   protected int getNextTabRun(int paramInt) {
/* 2124 */     return (paramInt + 1) % this.runCount;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static void rotateInsets(Insets paramInsets1, Insets paramInsets2, int paramInt) {
/* 2129 */     switch (paramInt) {
/*      */       case 2:
/* 2131 */         paramInsets2.top = paramInsets1.left;
/* 2132 */         paramInsets2.left = paramInsets1.top;
/* 2133 */         paramInsets2.bottom = paramInsets1.right;
/* 2134 */         paramInsets2.right = paramInsets1.bottom;
/*      */         return;
/*      */       case 3:
/* 2137 */         paramInsets2.top = paramInsets1.bottom;
/* 2138 */         paramInsets2.left = paramInsets1.left;
/* 2139 */         paramInsets2.bottom = paramInsets1.top;
/* 2140 */         paramInsets2.right = paramInsets1.right;
/*      */         return;
/*      */       case 4:
/* 2143 */         paramInsets2.top = paramInsets1.left;
/* 2144 */         paramInsets2.left = paramInsets1.bottom;
/* 2145 */         paramInsets2.bottom = paramInsets1.right;
/* 2146 */         paramInsets2.right = paramInsets1.top;
/*      */         return;
/*      */     } 
/*      */     
/* 2150 */     paramInsets2.top = paramInsets1.top;
/* 2151 */     paramInsets2.left = paramInsets1.left;
/* 2152 */     paramInsets2.bottom = paramInsets1.bottom;
/* 2153 */     paramInsets2.right = paramInsets1.right;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean requestFocusForVisibleComponent() {
/* 2161 */     return SwingUtilities2.tabbedPaneChangeFocusTo(getVisibleComponent());
/*      */   }
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction {
/*      */     static final String NEXT = "navigateNext";
/*      */     static final String PREVIOUS = "navigatePrevious";
/*      */     static final String RIGHT = "navigateRight";
/*      */     static final String LEFT = "navigateLeft";
/*      */     static final String UP = "navigateUp";
/*      */     static final String DOWN = "navigateDown";
/*      */     static final String PAGE_UP = "navigatePageUp";
/*      */     static final String PAGE_DOWN = "navigatePageDown";
/*      */     static final String REQUEST_FOCUS = "requestFocus";
/*      */     static final String REQUEST_FOCUS_FOR_VISIBLE = "requestFocusForVisibleComponent";
/*      */     static final String SET_SELECTED = "setSelectedIndex";
/*      */     static final String SELECT_FOCUSED = "selectTabWithFocus";
/*      */     static final String SCROLL_FORWARD = "scrollTabsForwardAction";
/*      */     static final String SCROLL_BACKWARD = "scrollTabsBackwardAction";
/*      */     
/*      */     Actions(String param1String) {
/* 2182 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2186 */       String str = getName();
/* 2187 */       JTabbedPane jTabbedPane = (JTabbedPane)param1ActionEvent.getSource();
/*      */       
/* 2189 */       BasicTabbedPaneUI basicTabbedPaneUI = (BasicTabbedPaneUI)BasicLookAndFeel.getUIOfType(jTabbedPane.getUI(), BasicTabbedPaneUI.class);
/*      */       
/* 2191 */       if (basicTabbedPaneUI == null) {
/*      */         return;
/*      */       }
/* 2194 */       if (str == "navigateNext") {
/* 2195 */         basicTabbedPaneUI.navigateSelectedTab(12);
/*      */       }
/* 2197 */       else if (str == "navigatePrevious") {
/* 2198 */         basicTabbedPaneUI.navigateSelectedTab(13);
/*      */       }
/* 2200 */       else if (str == "navigateRight") {
/* 2201 */         basicTabbedPaneUI.navigateSelectedTab(3);
/*      */       }
/* 2203 */       else if (str == "navigateLeft") {
/* 2204 */         basicTabbedPaneUI.navigateSelectedTab(7);
/*      */       }
/* 2206 */       else if (str == "navigateUp") {
/* 2207 */         basicTabbedPaneUI.navigateSelectedTab(1);
/*      */       }
/* 2209 */       else if (str == "navigateDown") {
/* 2210 */         basicTabbedPaneUI.navigateSelectedTab(5);
/*      */       }
/* 2212 */       else if (str == "navigatePageUp") {
/* 2213 */         int i = jTabbedPane.getTabPlacement();
/* 2214 */         if (i == 1 || i == 3) {
/* 2215 */           basicTabbedPaneUI.navigateSelectedTab(7);
/*      */         } else {
/* 2217 */           basicTabbedPaneUI.navigateSelectedTab(1);
/*      */         }
/*      */       
/* 2220 */       } else if (str == "navigatePageDown") {
/* 2221 */         int i = jTabbedPane.getTabPlacement();
/* 2222 */         if (i == 1 || i == 3) {
/* 2223 */           basicTabbedPaneUI.navigateSelectedTab(3);
/*      */         } else {
/* 2225 */           basicTabbedPaneUI.navigateSelectedTab(5);
/*      */         }
/*      */       
/* 2228 */       } else if (str == "requestFocus") {
/* 2229 */         jTabbedPane.requestFocus();
/*      */       }
/* 2231 */       else if (str == "requestFocusForVisibleComponent") {
/* 2232 */         basicTabbedPaneUI.requestFocusForVisibleComponent();
/*      */       }
/* 2234 */       else if (str == "setSelectedIndex") {
/* 2235 */         String str1 = param1ActionEvent.getActionCommand();
/*      */         
/* 2237 */         if (str1 != null && str1.length() > 0) {
/* 2238 */           char c = param1ActionEvent.getActionCommand().charAt(0);
/* 2239 */           if (c >= 'a' && c <= 'z') {
/* 2240 */             c -= ' ';
/*      */           }
/* 2242 */           Integer integer = (Integer)basicTabbedPaneUI.mnemonicToIndexMap.get(Integer.valueOf(c));
/* 2243 */           if (integer != null && jTabbedPane.isEnabledAt(integer.intValue())) {
/* 2244 */             jTabbedPane.setSelectedIndex(integer.intValue());
/*      */           }
/*      */         }
/*      */       
/* 2248 */       } else if (str == "selectTabWithFocus") {
/* 2249 */         int i = basicTabbedPaneUI.getFocusIndex();
/* 2250 */         if (i != -1) {
/* 2251 */           jTabbedPane.setSelectedIndex(i);
/*      */         }
/*      */       }
/* 2254 */       else if (str == "scrollTabsForwardAction") {
/* 2255 */         if (basicTabbedPaneUI.scrollableTabLayoutEnabled()) {
/* 2256 */           basicTabbedPaneUI.tabScroller.scrollForward(jTabbedPane.getTabPlacement());
/*      */         }
/*      */       }
/* 2259 */       else if (str == "scrollTabsBackwardAction" && 
/* 2260 */         basicTabbedPaneUI.scrollableTabLayoutEnabled()) {
/* 2261 */         basicTabbedPaneUI.tabScroller.scrollBackward(jTabbedPane.getTabPlacement());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class TabbedPaneLayout
/*      */     implements LayoutManager
/*      */   {
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */ 
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 2278 */       return calculateSize(false);
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 2282 */       return calculateSize(true);
/*      */     }
/*      */     
/*      */     protected Dimension calculateSize(boolean param1Boolean) {
/* 2286 */       int i = BasicTabbedPaneUI.this.tabPane.getTabPlacement();
/* 2287 */       Insets insets1 = BasicTabbedPaneUI.this.tabPane.getInsets();
/* 2288 */       Insets insets2 = BasicTabbedPaneUI.this.getContentBorderInsets(i);
/* 2289 */       Insets insets3 = BasicTabbedPaneUI.this.getTabAreaInsets(i);
/*      */       
/* 2291 */       Dimension dimension = new Dimension(0, 0);
/* 2292 */       int j = 0;
/* 2293 */       int k = 0;
/* 2294 */       int m = 0;
/* 2295 */       int n = 0;
/*      */ 
/*      */       
/*      */       int i1;
/*      */       
/* 2300 */       for (i1 = 0; i1 < BasicTabbedPaneUI.this.tabPane.getTabCount(); i1++) {
/* 2301 */         Component component = BasicTabbedPaneUI.this.tabPane.getComponentAt(i1);
/* 2302 */         if (component != null) {
/*      */           
/* 2304 */           Dimension dimension1 = param1Boolean ? component.getMinimumSize() : component.getPreferredSize();
/*      */           
/* 2306 */           if (dimension1 != null) {
/* 2307 */             n = Math.max(dimension1.height, n);
/* 2308 */             m = Math.max(dimension1.width, m);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2313 */       k += m;
/* 2314 */       j += n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2320 */       switch (i)
/*      */       { case 2:
/*      */         case 4:
/* 2323 */           j = Math.max(j, BasicTabbedPaneUI.this.calculateMaxTabHeight(i));
/* 2324 */           i1 = preferredTabAreaWidth(i, j - insets3.top - insets3.bottom);
/* 2325 */           k += i1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2334 */           return new Dimension(k + insets1.left + insets1.right + insets2.left + insets2.right, j + insets1.bottom + insets1.top + insets2.top + insets2.bottom); }  k = Math.max(k, BasicTabbedPaneUI.this.calculateMaxTabWidth(i)); i1 = preferredTabAreaHeight(i, k - insets3.left - insets3.right); j += i1; return new Dimension(k + insets1.left + insets1.right + insets2.left + insets2.right, j + insets1.bottom + insets1.top + insets2.top + insets2.bottom);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected int preferredTabAreaHeight(int param1Int1, int param1Int2) {
/* 2340 */       FontMetrics fontMetrics = BasicTabbedPaneUI.this.getFontMetrics();
/* 2341 */       int i = BasicTabbedPaneUI.this.tabPane.getTabCount();
/* 2342 */       int j = 0;
/* 2343 */       if (i > 0) {
/* 2344 */         byte b1 = 1;
/* 2345 */         int k = 0;
/*      */         
/* 2347 */         int m = BasicTabbedPaneUI.this.calculateMaxTabHeight(param1Int1);
/*      */         
/* 2349 */         for (byte b2 = 0; b2 < i; b2++) {
/* 2350 */           int n = BasicTabbedPaneUI.this.calculateTabWidth(param1Int1, b2, fontMetrics);
/*      */           
/* 2352 */           if (k && k + n > param1Int2) {
/* 2353 */             b1++;
/* 2354 */             k = 0;
/*      */           } 
/* 2356 */           k += n;
/*      */         } 
/* 2358 */         j = BasicTabbedPaneUI.this.calculateTabAreaHeight(param1Int1, b1, m);
/*      */       } 
/* 2360 */       return j;
/*      */     }
/*      */     
/*      */     protected int preferredTabAreaWidth(int param1Int1, int param1Int2) {
/* 2364 */       FontMetrics fontMetrics = BasicTabbedPaneUI.this.getFontMetrics();
/* 2365 */       int i = BasicTabbedPaneUI.this.tabPane.getTabCount();
/* 2366 */       int j = 0;
/* 2367 */       if (i > 0) {
/* 2368 */         byte b1 = 1;
/* 2369 */         int k = 0;
/* 2370 */         int m = fontMetrics.getHeight();
/*      */         
/* 2372 */         BasicTabbedPaneUI.this.maxTabWidth = BasicTabbedPaneUI.this.calculateMaxTabWidth(param1Int1);
/*      */         
/* 2374 */         for (byte b2 = 0; b2 < i; b2++) {
/* 2375 */           int n = BasicTabbedPaneUI.this.calculateTabHeight(param1Int1, b2, m);
/*      */           
/* 2377 */           if (k && k + n > param1Int2) {
/* 2378 */             b1++;
/* 2379 */             k = 0;
/*      */           } 
/* 2381 */           k += n;
/*      */         } 
/* 2383 */         j = BasicTabbedPaneUI.this.calculateTabAreaWidth(param1Int1, b1, BasicTabbedPaneUI.this.maxTabWidth);
/*      */       } 
/* 2385 */       return j;
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
/*      */     public void layoutContainer(Container param1Container) {
/* 2400 */       BasicTabbedPaneUI.this.setRolloverTab(-1);
/*      */       
/* 2402 */       int i = BasicTabbedPaneUI.this.tabPane.getTabPlacement();
/* 2403 */       Insets insets1 = BasicTabbedPaneUI.this.tabPane.getInsets();
/* 2404 */       int j = BasicTabbedPaneUI.this.tabPane.getSelectedIndex();
/* 2405 */       Component component1 = BasicTabbedPaneUI.this.getVisibleComponent();
/*      */       
/* 2407 */       calculateLayoutInfo();
/*      */       
/* 2409 */       Component component2 = null;
/* 2410 */       if (j < 0) {
/* 2411 */         if (component1 != null)
/*      */         {
/* 2413 */           BasicTabbedPaneUI.this.setVisibleComponent(null);
/*      */         }
/*      */       } else {
/* 2416 */         component2 = BasicTabbedPaneUI.this.tabPane.getComponentAt(j);
/*      */       } 
/*      */       
/* 2419 */       int k = 0;
/* 2420 */       int m = 0;
/* 2421 */       Insets insets2 = BasicTabbedPaneUI.this.getContentBorderInsets(i);
/*      */       
/* 2423 */       boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2432 */       if (component2 != null) {
/* 2433 */         if (component2 != component1 && component1 != null)
/*      */         {
/* 2435 */           if (SwingUtilities.findFocusOwner(component1) != null) {
/* 2436 */             bool = true;
/*      */           }
/*      */         }
/* 2439 */         BasicTabbedPaneUI.this.setVisibleComponent(component2);
/*      */       } 
/*      */       
/* 2442 */       Rectangle rectangle = BasicTabbedPaneUI.this.tabPane.getBounds();
/* 2443 */       int n = BasicTabbedPaneUI.this.tabPane.getComponentCount();
/*      */       
/* 2445 */       if (n > 0) {
/*      */         int i1, i2;
/* 2447 */         switch (i) {
/*      */           case 2:
/* 2449 */             k = BasicTabbedPaneUI.this.calculateTabAreaWidth(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabWidth);
/* 2450 */             i1 = insets1.left + k + insets2.left;
/* 2451 */             i2 = insets1.top + insets2.top;
/*      */             break;
/*      */           case 4:
/* 2454 */             k = BasicTabbedPaneUI.this.calculateTabAreaWidth(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabWidth);
/* 2455 */             i1 = insets1.left + insets2.left;
/* 2456 */             i2 = insets1.top + insets2.top;
/*      */             break;
/*      */           case 3:
/* 2459 */             m = BasicTabbedPaneUI.this.calculateTabAreaHeight(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabHeight);
/* 2460 */             i1 = insets1.left + insets2.left;
/* 2461 */             i2 = insets1.top + insets2.top;
/*      */             break;
/*      */           
/*      */           default:
/* 2465 */             m = BasicTabbedPaneUI.this.calculateTabAreaHeight(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabHeight);
/* 2466 */             i1 = insets1.left + insets2.left;
/* 2467 */             i2 = insets1.top + m + insets2.top;
/*      */             break;
/*      */         } 
/* 2470 */         int i3 = rectangle.width - k - insets1.left - insets1.right - insets2.left - insets2.right;
/*      */ 
/*      */         
/* 2473 */         int i4 = rectangle.height - m - insets1.top - insets1.bottom - insets2.top - insets2.bottom;
/*      */ 
/*      */ 
/*      */         
/* 2477 */         for (byte b = 0; b < n; b++) {
/* 2478 */           Component component = BasicTabbedPaneUI.this.tabPane.getComponent(b);
/* 2479 */           if (component == BasicTabbedPaneUI.this.tabContainer) {
/*      */             
/* 2481 */             int i5 = (k == 0) ? rectangle.width : (k + insets1.left + insets1.right + insets2.left + insets2.right);
/*      */ 
/*      */             
/* 2484 */             int i6 = (m == 0) ? rectangle.height : (m + insets1.top + insets1.bottom + insets2.top + insets2.bottom);
/*      */ 
/*      */ 
/*      */             
/* 2488 */             int i7 = 0;
/* 2489 */             int i8 = 0;
/* 2490 */             if (i == 3) {
/* 2491 */               i8 = rectangle.height - i6;
/* 2492 */             } else if (i == 4) {
/* 2493 */               i7 = rectangle.width - i5;
/*      */             } 
/* 2495 */             component.setBounds(i7, i8, i5, i6);
/*      */           } else {
/* 2497 */             component.setBounds(i1, i2, i3, i4);
/*      */           } 
/*      */         } 
/*      */       } 
/* 2501 */       layoutTabComponents();
/* 2502 */       if (bool && 
/* 2503 */         !BasicTabbedPaneUI.this.requestFocusForVisibleComponent()) {
/* 2504 */         BasicTabbedPaneUI.this.tabPane.requestFocus();
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void calculateLayoutInfo() {
/* 2510 */       int i = BasicTabbedPaneUI.this.tabPane.getTabCount();
/* 2511 */       BasicTabbedPaneUI.this.assureRectsCreated(i);
/* 2512 */       calculateTabRects(BasicTabbedPaneUI.this.tabPane.getTabPlacement(), i);
/* 2513 */       BasicTabbedPaneUI.this.isRunsDirty = false;
/*      */     }
/*      */     
/*      */     private void layoutTabComponents() {
/* 2517 */       if (BasicTabbedPaneUI.this.tabContainer == null) {
/*      */         return;
/*      */       }
/* 2520 */       Rectangle rectangle = new Rectangle();
/* 2521 */       Point point = new Point(-BasicTabbedPaneUI.this.tabContainer.getX(), -BasicTabbedPaneUI.this.tabContainer.getY());
/* 2522 */       if (BasicTabbedPaneUI.this.scrollableTabLayoutEnabled()) {
/* 2523 */         BasicTabbedPaneUI.this.translatePointToTabPanel(0, 0, point);
/*      */       }
/* 2525 */       for (byte b = 0; b < BasicTabbedPaneUI.this.tabPane.getTabCount(); b++) {
/* 2526 */         Component component = BasicTabbedPaneUI.this.tabPane.getTabComponentAt(b);
/* 2527 */         if (component != null) {
/*      */ 
/*      */           
/* 2530 */           BasicTabbedPaneUI.this.getTabBounds(b, rectangle);
/* 2531 */           Dimension dimension = component.getPreferredSize();
/* 2532 */           Insets insets = BasicTabbedPaneUI.this.getTabInsets(BasicTabbedPaneUI.this.tabPane.getTabPlacement(), b);
/* 2533 */           int i = rectangle.x + insets.left + point.x;
/* 2534 */           int j = rectangle.y + insets.top + point.y;
/* 2535 */           int k = rectangle.width - insets.left - insets.right;
/* 2536 */           int m = rectangle.height - insets.top - insets.bottom;
/*      */           
/* 2538 */           int n = i + (k - dimension.width) / 2;
/* 2539 */           int i1 = j + (m - dimension.height) / 2;
/* 2540 */           int i2 = BasicTabbedPaneUI.this.tabPane.getTabPlacement();
/* 2541 */           boolean bool = (b == BasicTabbedPaneUI.this.tabPane.getSelectedIndex()) ? true : false;
/* 2542 */           component.setBounds(n + BasicTabbedPaneUI.this.getTabLabelShiftX(i2, b, bool), i1 + BasicTabbedPaneUI.this
/* 2543 */               .getTabLabelShiftY(i2, b, bool), dimension.width, dimension.height);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     protected void calculateTabRects(int param1Int1, int param1Int2) {
/*      */       int n, i1, i2;
/* 2549 */       FontMetrics fontMetrics = BasicTabbedPaneUI.this.getFontMetrics();
/* 2550 */       Dimension dimension = BasicTabbedPaneUI.this.tabPane.getSize();
/* 2551 */       Insets insets1 = BasicTabbedPaneUI.this.tabPane.getInsets();
/* 2552 */       Insets insets2 = BasicTabbedPaneUI.this.getTabAreaInsets(param1Int1);
/* 2553 */       int i = fontMetrics.getHeight();
/* 2554 */       int j = BasicTabbedPaneUI.this.tabPane.getSelectedIndex();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2559 */       boolean bool = (param1Int1 == 2 || param1Int1 == 4) ? true : false;
/* 2560 */       boolean bool1 = BasicGraphicsUtils.isLeftToRight(BasicTabbedPaneUI.this.tabPane);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2565 */       switch (param1Int1) {
/*      */         case 2:
/* 2567 */           BasicTabbedPaneUI.this.maxTabWidth = BasicTabbedPaneUI.this.calculateMaxTabWidth(param1Int1);
/* 2568 */           n = insets1.left + insets2.left;
/* 2569 */           i1 = insets1.top + insets2.top;
/* 2570 */           i2 = dimension.height - insets1.bottom + insets2.bottom;
/*      */           break;
/*      */         case 4:
/* 2573 */           BasicTabbedPaneUI.this.maxTabWidth = BasicTabbedPaneUI.this.calculateMaxTabWidth(param1Int1);
/* 2574 */           n = dimension.width - insets1.right - insets2.right - BasicTabbedPaneUI.this.maxTabWidth;
/* 2575 */           i1 = insets1.top + insets2.top;
/* 2576 */           i2 = dimension.height - insets1.bottom + insets2.bottom;
/*      */           break;
/*      */         case 3:
/* 2579 */           BasicTabbedPaneUI.this.maxTabHeight = BasicTabbedPaneUI.this.calculateMaxTabHeight(param1Int1);
/* 2580 */           n = insets1.left + insets2.left;
/* 2581 */           i1 = dimension.height - insets1.bottom - insets2.bottom - BasicTabbedPaneUI.this.maxTabHeight;
/* 2582 */           i2 = dimension.width - insets1.right + insets2.right;
/*      */           break;
/*      */         
/*      */         default:
/* 2586 */           BasicTabbedPaneUI.this.maxTabHeight = BasicTabbedPaneUI.this.calculateMaxTabHeight(param1Int1);
/* 2587 */           n = insets1.left + insets2.left;
/* 2588 */           i1 = insets1.top + insets2.top;
/* 2589 */           i2 = dimension.width - insets1.right + insets2.right;
/*      */           break;
/*      */       } 
/*      */       
/* 2593 */       int k = BasicTabbedPaneUI.this.getTabRunOverlay(param1Int1);
/*      */       
/* 2595 */       BasicTabbedPaneUI.this.runCount = 0;
/* 2596 */       BasicTabbedPaneUI.this.selectedRun = -1;
/*      */       
/* 2598 */       if (param1Int2 == 0) {
/*      */         return;
/*      */       }
/*      */       
/*      */       int m;
/*      */       
/* 2604 */       for (m = 0; m < param1Int2; m++) {
/* 2605 */         Rectangle rectangle = BasicTabbedPaneUI.this.rects[m];
/*      */         
/* 2607 */         if (!bool) {
/*      */           
/* 2609 */           if (m > 0) {
/* 2610 */             (BasicTabbedPaneUI.this.rects[m - 1]).x += (BasicTabbedPaneUI.this.rects[m - 1]).width;
/*      */           } else {
/* 2612 */             BasicTabbedPaneUI.this.tabRuns[0] = 0;
/* 2613 */             BasicTabbedPaneUI.this.runCount = 1;
/* 2614 */             BasicTabbedPaneUI.this.maxTabWidth = 0;
/* 2615 */             rectangle.x = n;
/*      */           } 
/* 2617 */           rectangle.width = BasicTabbedPaneUI.this.calculateTabWidth(param1Int1, m, fontMetrics);
/* 2618 */           BasicTabbedPaneUI.this.maxTabWidth = Math.max(BasicTabbedPaneUI.this.maxTabWidth, rectangle.width);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2623 */           if (rectangle.x != n && rectangle.x + rectangle.width > i2) {
/* 2624 */             if (BasicTabbedPaneUI.this.runCount > BasicTabbedPaneUI.this.tabRuns.length - 1) {
/* 2625 */               BasicTabbedPaneUI.this.expandTabRunsArray();
/*      */             }
/* 2627 */             BasicTabbedPaneUI.this.tabRuns[BasicTabbedPaneUI.this.runCount] = m;
/* 2628 */             BasicTabbedPaneUI.this.runCount++;
/* 2629 */             rectangle.x = n;
/*      */           } 
/*      */           
/* 2632 */           rectangle.y = i1;
/* 2633 */           rectangle.height = BasicTabbedPaneUI.this.maxTabHeight;
/*      */         }
/*      */         else {
/*      */           
/* 2637 */           if (m > 0) {
/* 2638 */             (BasicTabbedPaneUI.this.rects[m - 1]).y += (BasicTabbedPaneUI.this.rects[m - 1]).height;
/*      */           } else {
/* 2640 */             BasicTabbedPaneUI.this.tabRuns[0] = 0;
/* 2641 */             BasicTabbedPaneUI.this.runCount = 1;
/* 2642 */             BasicTabbedPaneUI.this.maxTabHeight = 0;
/* 2643 */             rectangle.y = i1;
/*      */           } 
/* 2645 */           rectangle.height = BasicTabbedPaneUI.this.calculateTabHeight(param1Int1, m, i);
/* 2646 */           BasicTabbedPaneUI.this.maxTabHeight = Math.max(BasicTabbedPaneUI.this.maxTabHeight, rectangle.height);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2651 */           if (rectangle.y != i1 && rectangle.y + rectangle.height > i2) {
/* 2652 */             if (BasicTabbedPaneUI.this.runCount > BasicTabbedPaneUI.this.tabRuns.length - 1) {
/* 2653 */               BasicTabbedPaneUI.this.expandTabRunsArray();
/*      */             }
/* 2655 */             BasicTabbedPaneUI.this.tabRuns[BasicTabbedPaneUI.this.runCount] = m;
/* 2656 */             BasicTabbedPaneUI.this.runCount++;
/* 2657 */             rectangle.y = i1;
/*      */           } 
/*      */           
/* 2660 */           rectangle.x = n;
/* 2661 */           rectangle.width = BasicTabbedPaneUI.this.maxTabWidth;
/*      */         } 
/*      */         
/* 2664 */         if (m == j) {
/* 2665 */           BasicTabbedPaneUI.this.selectedRun = BasicTabbedPaneUI.this.runCount - 1;
/*      */         }
/*      */       } 
/*      */       
/* 2669 */       if (BasicTabbedPaneUI.this.runCount > 1) {
/*      */         
/* 2671 */         normalizeTabRuns(param1Int1, param1Int2, bool ? i1 : n, i2);
/*      */         
/* 2673 */         BasicTabbedPaneUI.this.selectedRun = BasicTabbedPaneUI.this.getRunForTab(param1Int2, j);
/*      */ 
/*      */         
/* 2676 */         if (BasicTabbedPaneUI.this.shouldRotateTabRuns(param1Int1)) {
/* 2677 */           rotateTabRuns(param1Int1, BasicTabbedPaneUI.this.selectedRun);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2683 */       for (m = BasicTabbedPaneUI.this.runCount - 1; m >= 0; m--) {
/* 2684 */         int i3 = BasicTabbedPaneUI.this.tabRuns[m];
/* 2685 */         int i4 = BasicTabbedPaneUI.this.tabRuns[(m == BasicTabbedPaneUI.this.runCount - 1) ? 0 : (m + 1)];
/* 2686 */         int i5 = (i4 != 0) ? (i4 - 1) : (param1Int2 - 1);
/* 2687 */         if (!bool) {
/* 2688 */           for (int i6 = i3; i6 <= i5; i6++) {
/* 2689 */             Rectangle rectangle = BasicTabbedPaneUI.this.rects[i6];
/* 2690 */             rectangle.y = i1;
/* 2691 */             rectangle.x += BasicTabbedPaneUI.this.getTabRunIndent(param1Int1, m);
/*      */           } 
/* 2693 */           if (BasicTabbedPaneUI.this.shouldPadTabRun(param1Int1, m)) {
/* 2694 */             padTabRun(param1Int1, i3, i5, i2);
/*      */           }
/* 2696 */           if (param1Int1 == 3) {
/* 2697 */             i1 -= BasicTabbedPaneUI.this.maxTabHeight - k;
/*      */           } else {
/* 2699 */             i1 += BasicTabbedPaneUI.this.maxTabHeight - k;
/*      */           } 
/*      */         } else {
/* 2702 */           for (int i6 = i3; i6 <= i5; i6++) {
/* 2703 */             Rectangle rectangle = BasicTabbedPaneUI.this.rects[i6];
/* 2704 */             rectangle.x = n;
/* 2705 */             rectangle.y += BasicTabbedPaneUI.this.getTabRunIndent(param1Int1, m);
/*      */           } 
/* 2707 */           if (BasicTabbedPaneUI.this.shouldPadTabRun(param1Int1, m)) {
/* 2708 */             padTabRun(param1Int1, i3, i5, i2);
/*      */           }
/* 2710 */           if (param1Int1 == 4) {
/* 2711 */             n -= BasicTabbedPaneUI.this.maxTabWidth - k;
/*      */           } else {
/* 2713 */             n += BasicTabbedPaneUI.this.maxTabWidth - k;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2719 */       padSelectedTab(param1Int1, j);
/*      */ 
/*      */ 
/*      */       
/* 2723 */       if (!bool1 && !bool) {
/* 2724 */         int i3 = dimension.width - insets1.right + insets2.right;
/*      */         
/* 2726 */         for (m = 0; m < param1Int2; m++) {
/* 2727 */           (BasicTabbedPaneUI.this.rects[m]).x = i3 - (BasicTabbedPaneUI.this.rects[m]).x - (BasicTabbedPaneUI.this.rects[m]).width;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void rotateTabRuns(int param1Int1, int param1Int2) {
/* 2737 */       for (byte b = 0; b < param1Int2; b++) {
/* 2738 */         int i = BasicTabbedPaneUI.this.tabRuns[0];
/* 2739 */         for (byte b1 = 1; b1 < BasicTabbedPaneUI.this.runCount; b1++) {
/* 2740 */           BasicTabbedPaneUI.this.tabRuns[b1 - 1] = BasicTabbedPaneUI.this.tabRuns[b1];
/*      */         }
/* 2742 */         BasicTabbedPaneUI.this.tabRuns[BasicTabbedPaneUI.this.runCount - 1] = i;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void normalizeTabRuns(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2748 */       boolean bool1 = (param1Int1 == 2 || param1Int1 == 4) ? true : false;
/* 2749 */       int i = BasicTabbedPaneUI.this.runCount - 1;
/* 2750 */       boolean bool2 = true;
/* 2751 */       double d = 1.25D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2764 */       while (bool2) {
/* 2765 */         int m, n, j = BasicTabbedPaneUI.this.lastTabInRun(param1Int2, i);
/* 2766 */         int k = BasicTabbedPaneUI.this.lastTabInRun(param1Int2, i - 1);
/*      */ 
/*      */ 
/*      */         
/* 2770 */         if (!bool1) {
/* 2771 */           m = (BasicTabbedPaneUI.this.rects[j]).x + (BasicTabbedPaneUI.this.rects[j]).width;
/* 2772 */           n = (int)(BasicTabbedPaneUI.this.maxTabWidth * d);
/*      */         } else {
/* 2774 */           m = (BasicTabbedPaneUI.this.rects[j]).y + (BasicTabbedPaneUI.this.rects[j]).height;
/* 2775 */           n = (int)(BasicTabbedPaneUI.this.maxTabHeight * d * 2.0D);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2780 */         if (param1Int4 - m > n) {
/*      */ 
/*      */           
/* 2783 */           BasicTabbedPaneUI.this.tabRuns[i] = k;
/* 2784 */           if (!bool1) {
/* 2785 */             (BasicTabbedPaneUI.this.rects[k]).x = param1Int3;
/*      */           } else {
/* 2787 */             (BasicTabbedPaneUI.this.rects[k]).y = param1Int3;
/*      */           } 
/* 2789 */           for (int i1 = k + 1; i1 <= j; i1++) {
/* 2790 */             if (!bool1) {
/* 2791 */               (BasicTabbedPaneUI.this.rects[i1 - 1]).x += (BasicTabbedPaneUI.this.rects[i1 - 1]).width;
/*      */             } else {
/* 2793 */               (BasicTabbedPaneUI.this.rects[i1 - 1]).y += (BasicTabbedPaneUI.this.rects[i1 - 1]).height;
/*      */             }
/*      */           
/*      */           } 
/* 2797 */         } else if (i == BasicTabbedPaneUI.this.runCount - 1) {
/*      */           
/* 2799 */           bool2 = false;
/*      */         } 
/* 2801 */         if (i - 1 > 0) {
/*      */           
/* 2803 */           i--;
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 2808 */         i = BasicTabbedPaneUI.this.runCount - 1;
/* 2809 */         d += 0.25D;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void padTabRun(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 2815 */       Rectangle rectangle = BasicTabbedPaneUI.this.rects[param1Int3];
/* 2816 */       if (param1Int1 == 1 || param1Int1 == 3) {
/* 2817 */         int i = rectangle.x + rectangle.width - (BasicTabbedPaneUI.this.rects[param1Int2]).x;
/* 2818 */         int j = param1Int4 - rectangle.x + rectangle.width;
/* 2819 */         float f = j / i;
/*      */         
/* 2821 */         for (int k = param1Int2; k <= param1Int3; k++) {
/* 2822 */           Rectangle rectangle1 = BasicTabbedPaneUI.this.rects[k];
/* 2823 */           if (k > param1Int2) {
/* 2824 */             (BasicTabbedPaneUI.this.rects[k - 1]).x += (BasicTabbedPaneUI.this.rects[k - 1]).width;
/*      */           }
/* 2826 */           rectangle1.width += Math.round(rectangle1.width * f);
/*      */         } 
/* 2828 */         rectangle.width = param1Int4 - rectangle.x;
/*      */       } else {
/* 2830 */         int i = rectangle.y + rectangle.height - (BasicTabbedPaneUI.this.rects[param1Int2]).y;
/* 2831 */         int j = param1Int4 - rectangle.y + rectangle.height;
/* 2832 */         float f = j / i;
/*      */         
/* 2834 */         for (int k = param1Int2; k <= param1Int3; k++) {
/* 2835 */           Rectangle rectangle1 = BasicTabbedPaneUI.this.rects[k];
/* 2836 */           if (k > param1Int2) {
/* 2837 */             (BasicTabbedPaneUI.this.rects[k - 1]).y += (BasicTabbedPaneUI.this.rects[k - 1]).height;
/*      */           }
/* 2839 */           rectangle1.height += Math.round(rectangle1.height * f);
/*      */         } 
/* 2841 */         rectangle.height = param1Int4 - rectangle.y;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void padSelectedTab(int param1Int1, int param1Int2) {
/* 2847 */       if (param1Int2 >= 0) {
/* 2848 */         Rectangle rectangle = BasicTabbedPaneUI.this.rects[param1Int2];
/* 2849 */         Insets insets = BasicTabbedPaneUI.this.getSelectedTabPadInsets(param1Int1);
/* 2850 */         rectangle.x -= insets.left;
/* 2851 */         rectangle.width += insets.left + insets.right;
/* 2852 */         rectangle.y -= insets.top;
/* 2853 */         rectangle.height += insets.top + insets.bottom;
/*      */         
/* 2855 */         if (!BasicTabbedPaneUI.this.scrollableTabLayoutEnabled()) {
/*      */           
/* 2857 */           Dimension dimension = BasicTabbedPaneUI.this.tabPane.getSize();
/* 2858 */           Insets insets1 = BasicTabbedPaneUI.this.tabPane.getInsets();
/*      */           
/* 2860 */           if (param1Int1 == 2 || param1Int1 == 4) {
/* 2861 */             int i = insets1.top - rectangle.y;
/* 2862 */             if (i > 0) {
/* 2863 */               rectangle.y += i;
/* 2864 */               rectangle.height -= i;
/*      */             } 
/* 2866 */             int j = rectangle.y + rectangle.height + insets1.bottom - dimension.height;
/* 2867 */             if (j > 0) {
/* 2868 */               rectangle.height -= j;
/*      */             }
/*      */           } else {
/* 2871 */             int i = insets1.left - rectangle.x;
/* 2872 */             if (i > 0) {
/* 2873 */               rectangle.x += i;
/* 2874 */               rectangle.width -= i;
/*      */             } 
/* 2876 */             int j = rectangle.x + rectangle.width + insets1.right - dimension.width;
/* 2877 */             if (j > 0)
/* 2878 */               rectangle.width -= j; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class TabbedPaneScrollLayout extends TabbedPaneLayout {
/*      */     private TabbedPaneScrollLayout() {}
/*      */     
/*      */     protected int preferredTabAreaHeight(int param1Int1, int param1Int2) {
/* 2889 */       return BasicTabbedPaneUI.this.calculateMaxTabHeight(param1Int1);
/*      */     }
/*      */     
/*      */     protected int preferredTabAreaWidth(int param1Int1, int param1Int2) {
/* 2893 */       return BasicTabbedPaneUI.this.calculateMaxTabWidth(param1Int1);
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
/*      */     public void layoutContainer(Container param1Container) {
/* 2908 */       BasicTabbedPaneUI.this.setRolloverTab(-1);
/*      */       
/* 2910 */       int i = BasicTabbedPaneUI.this.tabPane.getTabPlacement();
/* 2911 */       int j = BasicTabbedPaneUI.this.tabPane.getTabCount();
/* 2912 */       Insets insets1 = BasicTabbedPaneUI.this.tabPane.getInsets();
/* 2913 */       int k = BasicTabbedPaneUI.this.tabPane.getSelectedIndex();
/* 2914 */       Component component1 = BasicTabbedPaneUI.this.getVisibleComponent();
/*      */       
/* 2916 */       calculateLayoutInfo();
/*      */       
/* 2918 */       Component component2 = null;
/* 2919 */       if (k < 0) {
/* 2920 */         if (component1 != null)
/*      */         {
/* 2922 */           BasicTabbedPaneUI.this.setVisibleComponent(null);
/*      */         }
/*      */       } else {
/* 2925 */         component2 = BasicTabbedPaneUI.this.tabPane.getComponentAt(k);
/*      */       } 
/*      */       
/* 2928 */       if (BasicTabbedPaneUI.this.tabPane.getTabCount() == 0) {
/* 2929 */         BasicTabbedPaneUI.this.tabScroller.croppedEdge.resetParams();
/* 2930 */         BasicTabbedPaneUI.this.tabScroller.scrollForwardButton.setVisible(false);
/* 2931 */         BasicTabbedPaneUI.this.tabScroller.scrollBackwardButton.setVisible(false);
/*      */         
/*      */         return;
/*      */       } 
/* 2935 */       boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2944 */       if (component2 != null) {
/* 2945 */         if (component2 != component1 && component1 != null)
/*      */         {
/* 2947 */           if (SwingUtilities.findFocusOwner(component1) != null) {
/* 2948 */             bool = true;
/*      */           }
/*      */         }
/* 2951 */         BasicTabbedPaneUI.this.setVisibleComponent(component2);
/*      */       } 
/*      */ 
/*      */       
/* 2955 */       Insets insets2 = BasicTabbedPaneUI.this.getContentBorderInsets(i);
/* 2956 */       Rectangle rectangle = BasicTabbedPaneUI.this.tabPane.getBounds();
/* 2957 */       int m = BasicTabbedPaneUI.this.tabPane.getComponentCount();
/*      */       
/* 2959 */       if (m > 0) {
/* 2960 */         int n, i1, i2, i3, i4, i5, i6, i7; switch (i) {
/*      */           
/*      */           case 2:
/* 2963 */             i2 = BasicTabbedPaneUI.this.calculateTabAreaWidth(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabWidth);
/* 2964 */             i3 = rectangle.height - insets1.top - insets1.bottom;
/* 2965 */             n = insets1.left;
/* 2966 */             i1 = insets1.top;
/*      */ 
/*      */             
/* 2969 */             i4 = n + i2 + insets2.left;
/* 2970 */             i5 = i1 + insets2.top;
/* 2971 */             i6 = rectangle.width - insets1.left - insets1.right - i2 - insets2.left - insets2.right;
/*      */             
/* 2973 */             i7 = rectangle.height - insets1.top - insets1.bottom - insets2.top - insets2.bottom;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 4:
/* 2978 */             i2 = BasicTabbedPaneUI.this.calculateTabAreaWidth(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabWidth);
/* 2979 */             i3 = rectangle.height - insets1.top - insets1.bottom;
/* 2980 */             n = rectangle.width - insets1.right - i2;
/* 2981 */             i1 = insets1.top;
/*      */ 
/*      */             
/* 2984 */             i4 = insets1.left + insets2.left;
/* 2985 */             i5 = insets1.top + insets2.top;
/* 2986 */             i6 = rectangle.width - insets1.left - insets1.right - i2 - insets2.left - insets2.right;
/*      */             
/* 2988 */             i7 = rectangle.height - insets1.top - insets1.bottom - insets2.top - insets2.bottom;
/*      */             break;
/*      */ 
/*      */           
/*      */           case 3:
/* 2993 */             i2 = rectangle.width - insets1.left - insets1.right;
/* 2994 */             i3 = BasicTabbedPaneUI.this.calculateTabAreaHeight(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabHeight);
/* 2995 */             n = insets1.left;
/* 2996 */             i1 = rectangle.height - insets1.bottom - i3;
/*      */ 
/*      */             
/* 2999 */             i4 = insets1.left + insets2.left;
/* 3000 */             i5 = insets1.top + insets2.top;
/* 3001 */             i6 = rectangle.width - insets1.left - insets1.right - insets2.left - insets2.right;
/*      */             
/* 3003 */             i7 = rectangle.height - insets1.top - insets1.bottom - i3 - insets2.top - insets2.bottom;
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 3009 */             i2 = rectangle.width - insets1.left - insets1.right;
/* 3010 */             i3 = BasicTabbedPaneUI.this.calculateTabAreaHeight(i, BasicTabbedPaneUI.this.runCount, BasicTabbedPaneUI.this.maxTabHeight);
/* 3011 */             n = insets1.left;
/* 3012 */             i1 = insets1.top;
/*      */ 
/*      */             
/* 3015 */             i4 = n + insets2.left;
/* 3016 */             i5 = i1 + i3 + insets2.top;
/* 3017 */             i6 = rectangle.width - insets1.left - insets1.right - insets2.left - insets2.right;
/*      */             
/* 3019 */             i7 = rectangle.height - insets1.top - insets1.bottom - i3 - insets2.top - insets2.bottom;
/*      */             break;
/*      */         } 
/*      */         
/* 3023 */         for (byte b = 0; b < m; b++) {
/* 3024 */           Component component = BasicTabbedPaneUI.this.tabPane.getComponent(b);
/*      */           
/* 3026 */           if (BasicTabbedPaneUI.this.tabScroller != null && component == BasicTabbedPaneUI.this.tabScroller.viewport) {
/* 3027 */             int i10, i11; JViewport jViewport = (JViewport)component;
/* 3028 */             Rectangle rectangle1 = jViewport.getViewRect();
/* 3029 */             int i8 = i2;
/* 3030 */             int i9 = i3;
/* 3031 */             Dimension dimension = BasicTabbedPaneUI.this.tabScroller.scrollForwardButton.getPreferredSize();
/* 3032 */             switch (i) {
/*      */               case 2:
/*      */               case 4:
/* 3035 */                 i10 = (BasicTabbedPaneUI.this.rects[j - 1]).y + (BasicTabbedPaneUI.this.rects[j - 1]).height;
/* 3036 */                 if (i10 > i3) {
/*      */                   
/* 3038 */                   i9 = (i3 > 2 * dimension.height) ? (i3 - 2 * dimension.height) : 0;
/* 3039 */                   if (i10 - rectangle1.y <= i9)
/*      */                   {
/*      */                     
/* 3042 */                     i9 = i10 - rectangle1.y;
/*      */                   }
/*      */                 } 
/*      */                 break;
/*      */ 
/*      */               
/*      */               default:
/* 3049 */                 i11 = (BasicTabbedPaneUI.this.rects[j - 1]).x + (BasicTabbedPaneUI.this.rects[j - 1]).width;
/* 3050 */                 if (i11 > i2) {
/*      */                   
/* 3052 */                   i8 = (i2 > 2 * dimension.width) ? (i2 - 2 * dimension.width) : 0;
/* 3053 */                   if (i11 - rectangle1.x <= i8)
/*      */                   {
/*      */                     
/* 3056 */                     i8 = i11 - rectangle1.x; } 
/*      */                 } 
/*      */                 break;
/*      */             } 
/* 3060 */             component.setBounds(n, i1, i8, i9);
/*      */           }
/* 3062 */           else if (BasicTabbedPaneUI.this.tabScroller != null && (component == 
/* 3063 */             BasicTabbedPaneUI.this.tabScroller.scrollForwardButton || component == 
/* 3064 */             BasicTabbedPaneUI.this.tabScroller.scrollBackwardButton)) {
/* 3065 */             int i12, i13; Component component3 = component;
/* 3066 */             Dimension dimension = component3.getPreferredSize();
/* 3067 */             int i8 = 0;
/* 3068 */             int i9 = 0;
/* 3069 */             int i10 = dimension.width;
/* 3070 */             int i11 = dimension.height;
/* 3071 */             boolean bool1 = false;
/*      */             
/* 3073 */             switch (i) {
/*      */               case 2:
/*      */               case 4:
/* 3076 */                 i12 = (BasicTabbedPaneUI.this.rects[j - 1]).y + (BasicTabbedPaneUI.this.rects[j - 1]).height;
/* 3077 */                 if (i12 > i3) {
/* 3078 */                   bool1 = true;
/* 3079 */                   i8 = (i == 2) ? (n + i2 - dimension.width) : n;
/* 3080 */                   i9 = (component == BasicTabbedPaneUI.this.tabScroller.scrollForwardButton) ? (rectangle.height - insets1.bottom - dimension.height) : (rectangle.height - insets1.bottom - 2 * dimension.height);
/*      */                 } 
/*      */                 break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               default:
/* 3089 */                 i13 = (BasicTabbedPaneUI.this.rects[j - 1]).x + (BasicTabbedPaneUI.this.rects[j - 1]).width;
/*      */                 
/* 3091 */                 if (i13 > i2) {
/* 3092 */                   bool1 = true;
/* 3093 */                   i8 = (component == BasicTabbedPaneUI.this.tabScroller.scrollForwardButton) ? (rectangle.width - insets1.left - dimension.width) : (rectangle.width - insets1.left - 2 * dimension.width);
/*      */ 
/*      */                   
/* 3096 */                   i9 = (i == 1) ? (i1 + i3 - dimension.height) : i1;
/*      */                 }  break;
/*      */             } 
/* 3099 */             component.setVisible(bool1);
/* 3100 */             if (bool1) {
/* 3101 */               component.setBounds(i8, i9, i10, i11);
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 3106 */             component.setBounds(i4, i5, i6, i7);
/*      */           } 
/*      */         } 
/* 3109 */         layoutTabComponents();
/* 3110 */         layoutCroppedEdge();
/* 3111 */         if (bool && 
/* 3112 */           !BasicTabbedPaneUI.this.requestFocusForVisibleComponent()) {
/* 3113 */           BasicTabbedPaneUI.this.tabPane.requestFocus();
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void layoutCroppedEdge() {
/* 3120 */       BasicTabbedPaneUI.this.tabScroller.croppedEdge.resetParams();
/* 3121 */       Rectangle rectangle = BasicTabbedPaneUI.this.tabScroller.viewport.getViewRect();
/*      */       
/* 3123 */       for (byte b = 0; b < BasicTabbedPaneUI.this.rects.length; b++) {
/* 3124 */         int i; Rectangle rectangle1 = BasicTabbedPaneUI.this.rects[b];
/* 3125 */         switch (BasicTabbedPaneUI.this.tabPane.getTabPlacement()) {
/*      */           case 2:
/*      */           case 4:
/* 3128 */             i = rectangle.y + rectangle.height;
/* 3129 */             if (rectangle1.y < i && rectangle1.y + rectangle1.height > i) {
/* 3130 */               BasicTabbedPaneUI.this.tabScroller.croppedEdge.setParams(b, i - rectangle1.y - 1, 
/* 3131 */                   -BasicTabbedPaneUI.this.currentTabAreaInsets.left, 0);
/*      */             }
/*      */             break;
/*      */ 
/*      */           
/*      */           default:
/* 3137 */             i = rectangle.x + rectangle.width;
/* 3138 */             if (rectangle1.x < i - 1 && rectangle1.x + rectangle1.width > i)
/* 3139 */               BasicTabbedPaneUI.this.tabScroller.croppedEdge.setParams(b, i - rectangle1.x - 1, 0, 
/* 3140 */                   -BasicTabbedPaneUI.this.currentTabAreaInsets.top); 
/*      */             break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     protected void calculateTabRects(int param1Int1, int param1Int2) {
/* 3147 */       FontMetrics fontMetrics = BasicTabbedPaneUI.this.getFontMetrics();
/* 3148 */       Dimension dimension = BasicTabbedPaneUI.this.tabPane.getSize();
/* 3149 */       Insets insets1 = BasicTabbedPaneUI.this.tabPane.getInsets();
/* 3150 */       Insets insets2 = BasicTabbedPaneUI.this.getTabAreaInsets(param1Int1);
/* 3151 */       int i = fontMetrics.getHeight();
/* 3152 */       int j = BasicTabbedPaneUI.this.tabPane.getSelectedIndex();
/*      */       
/* 3154 */       boolean bool = (param1Int1 == 2 || param1Int1 == 4) ? true : false;
/* 3155 */       boolean bool1 = BasicGraphicsUtils.isLeftToRight(BasicTabbedPaneUI.this.tabPane);
/* 3156 */       int k = insets2.left;
/* 3157 */       int m = insets2.top;
/* 3158 */       int n = 0;
/* 3159 */       int i1 = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3164 */       switch (param1Int1) {
/*      */         case 2:
/*      */         case 4:
/* 3167 */           BasicTabbedPaneUI.this.maxTabWidth = BasicTabbedPaneUI.this.calculateMaxTabWidth(param1Int1);
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 3172 */           BasicTabbedPaneUI.this.maxTabHeight = BasicTabbedPaneUI.this.calculateMaxTabHeight(param1Int1);
/*      */           break;
/*      */       } 
/* 3175 */       BasicTabbedPaneUI.this.runCount = 0;
/* 3176 */       BasicTabbedPaneUI.this.selectedRun = -1;
/*      */       
/* 3178 */       if (param1Int2 == 0) {
/*      */         return;
/*      */       }
/*      */       
/* 3182 */       BasicTabbedPaneUI.this.selectedRun = 0;
/* 3183 */       BasicTabbedPaneUI.this.runCount = 1;
/*      */       
/*      */       byte b;
/*      */       
/* 3187 */       for (b = 0; b < param1Int2; b++) {
/* 3188 */         Rectangle rectangle = BasicTabbedPaneUI.this.rects[b];
/*      */         
/* 3190 */         if (!bool) {
/*      */           
/* 3192 */           if (b > 0) {
/* 3193 */             (BasicTabbedPaneUI.this.rects[b - 1]).x += (BasicTabbedPaneUI.this.rects[b - 1]).width;
/*      */           } else {
/* 3195 */             BasicTabbedPaneUI.this.tabRuns[0] = 0;
/* 3196 */             BasicTabbedPaneUI.this.maxTabWidth = 0;
/* 3197 */             i1 += BasicTabbedPaneUI.this.maxTabHeight;
/* 3198 */             rectangle.x = k;
/*      */           } 
/* 3200 */           rectangle.width = BasicTabbedPaneUI.this.calculateTabWidth(param1Int1, b, fontMetrics);
/* 3201 */           n = rectangle.x + rectangle.width;
/* 3202 */           BasicTabbedPaneUI.this.maxTabWidth = Math.max(BasicTabbedPaneUI.this.maxTabWidth, rectangle.width);
/*      */           
/* 3204 */           rectangle.y = m;
/* 3205 */           rectangle.height = BasicTabbedPaneUI.this.maxTabHeight;
/*      */         }
/*      */         else {
/*      */           
/* 3209 */           if (b > 0) {
/* 3210 */             (BasicTabbedPaneUI.this.rects[b - 1]).y += (BasicTabbedPaneUI.this.rects[b - 1]).height;
/*      */           } else {
/* 3212 */             BasicTabbedPaneUI.this.tabRuns[0] = 0;
/* 3213 */             BasicTabbedPaneUI.this.maxTabHeight = 0;
/* 3214 */             n = BasicTabbedPaneUI.this.maxTabWidth;
/* 3215 */             rectangle.y = m;
/*      */           } 
/* 3217 */           rectangle.height = BasicTabbedPaneUI.this.calculateTabHeight(param1Int1, b, i);
/* 3218 */           i1 = rectangle.y + rectangle.height;
/* 3219 */           BasicTabbedPaneUI.this.maxTabHeight = Math.max(BasicTabbedPaneUI.this.maxTabHeight, rectangle.height);
/*      */           
/* 3221 */           rectangle.x = k;
/* 3222 */           rectangle.width = BasicTabbedPaneUI.this.maxTabWidth;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3227 */       if (BasicTabbedPaneUI.this.tabsOverlapBorder)
/*      */       {
/* 3229 */         padSelectedTab(param1Int1, j);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3234 */       if (!bool1 && !bool) {
/* 3235 */         int i2 = dimension.width - insets1.right + insets2.right;
/*      */         
/* 3237 */         for (b = 0; b < param1Int2; b++) {
/* 3238 */           (BasicTabbedPaneUI.this.rects[b]).x = i2 - (BasicTabbedPaneUI.this.rects[b]).x - (BasicTabbedPaneUI.this.rects[b]).width;
/*      */         }
/*      */       } 
/* 3241 */       BasicTabbedPaneUI.this.tabScroller.tabPanel.setPreferredSize(new Dimension(n, i1));
/* 3242 */       BasicTabbedPaneUI.this.tabScroller.tabPanel.invalidate();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ScrollableTabSupport
/*      */     implements ActionListener, ChangeListener
/*      */   {
/*      */     public BasicTabbedPaneUI.ScrollableTabViewport viewport;
/*      */     public BasicTabbedPaneUI.ScrollableTabPanel tabPanel;
/*      */     public JButton scrollForwardButton;
/*      */     public JButton scrollBackwardButton;
/*      */     public BasicTabbedPaneUI.CroppedEdge croppedEdge;
/*      */     public int leadingTabIndex;
/* 3255 */     private Point tabViewPosition = new Point(0, 0);
/*      */     
/*      */     ScrollableTabSupport(int param1Int) {
/* 3258 */       this.viewport = new BasicTabbedPaneUI.ScrollableTabViewport();
/* 3259 */       this.tabPanel = new BasicTabbedPaneUI.ScrollableTabPanel();
/* 3260 */       this.viewport.setView(this.tabPanel);
/* 3261 */       this.viewport.addChangeListener(this);
/* 3262 */       this.croppedEdge = new BasicTabbedPaneUI.CroppedEdge();
/* 3263 */       createButtons();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void createButtons() {
/* 3270 */       if (this.scrollForwardButton != null) {
/* 3271 */         BasicTabbedPaneUI.this.tabPane.remove(this.scrollForwardButton);
/* 3272 */         this.scrollForwardButton.removeActionListener(this);
/* 3273 */         BasicTabbedPaneUI.this.tabPane.remove(this.scrollBackwardButton);
/* 3274 */         this.scrollBackwardButton.removeActionListener(this);
/*      */       } 
/* 3276 */       int i = BasicTabbedPaneUI.this.tabPane.getTabPlacement();
/* 3277 */       if (i == 1 || i == 3) {
/* 3278 */         this.scrollForwardButton = BasicTabbedPaneUI.this.createScrollButton(3);
/* 3279 */         this.scrollBackwardButton = BasicTabbedPaneUI.this.createScrollButton(7);
/*      */       } else {
/*      */         
/* 3282 */         this.scrollForwardButton = BasicTabbedPaneUI.this.createScrollButton(5);
/* 3283 */         this.scrollBackwardButton = BasicTabbedPaneUI.this.createScrollButton(1);
/*      */       } 
/* 3285 */       this.scrollForwardButton.addActionListener(this);
/* 3286 */       this.scrollBackwardButton.addActionListener(this);
/* 3287 */       BasicTabbedPaneUI.this.tabPane.add(this.scrollForwardButton);
/* 3288 */       BasicTabbedPaneUI.this.tabPane.add(this.scrollBackwardButton);
/*      */     }
/*      */     
/*      */     public void scrollForward(int param1Int) {
/* 3292 */       Dimension dimension = this.viewport.getViewSize();
/* 3293 */       Rectangle rectangle = this.viewport.getViewRect();
/*      */       
/* 3295 */       if (param1Int == 1 || param1Int == 3) {
/* 3296 */         if (rectangle.width >= dimension.width - rectangle.x) {
/*      */           return;
/*      */         }
/*      */       }
/* 3300 */       else if (rectangle.height >= dimension.height - rectangle.y) {
/*      */         return;
/*      */       } 
/*      */       
/* 3304 */       setLeadingTabIndex(param1Int, this.leadingTabIndex + 1);
/*      */     }
/*      */     
/*      */     public void scrollBackward(int param1Int) {
/* 3308 */       if (this.leadingTabIndex == 0) {
/*      */         return;
/*      */       }
/* 3311 */       setLeadingTabIndex(param1Int, this.leadingTabIndex - 1);
/*      */     }
/*      */     
/*      */     public void setLeadingTabIndex(int param1Int1, int param1Int2) {
/* 3315 */       this.leadingTabIndex = param1Int2;
/* 3316 */       Dimension dimension = this.viewport.getViewSize();
/* 3317 */       Rectangle rectangle = this.viewport.getViewRect();
/*      */       
/* 3319 */       switch (param1Int1) {
/*      */         case 1:
/*      */         case 3:
/* 3322 */           this.tabViewPosition.x = (this.leadingTabIndex == 0) ? 0 : (BasicTabbedPaneUI.this.rects[this.leadingTabIndex]).x;
/*      */           
/* 3324 */           if (dimension.width - this.tabViewPosition.x < rectangle.width) {
/*      */ 
/*      */             
/* 3327 */             Dimension dimension1 = new Dimension(dimension.width - this.tabViewPosition.x, rectangle.height);
/*      */             
/* 3329 */             this.viewport.setExtentSize(dimension1);
/*      */           } 
/*      */           break;
/*      */         case 2:
/*      */         case 4:
/* 3334 */           this.tabViewPosition.y = (this.leadingTabIndex == 0) ? 0 : (BasicTabbedPaneUI.this.rects[this.leadingTabIndex]).y;
/*      */           
/* 3336 */           if (dimension.height - this.tabViewPosition.y < rectangle.height) {
/*      */ 
/*      */             
/* 3339 */             Dimension dimension1 = new Dimension(rectangle.width, dimension.height - this.tabViewPosition.y);
/*      */             
/* 3341 */             this.viewport.setExtentSize(dimension1);
/*      */           }  break;
/*      */       } 
/* 3344 */       this.viewport.setViewPosition(this.tabViewPosition);
/*      */     }
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 3348 */       updateView();
/*      */     }
/*      */     
/*      */     private void updateView() {
/* 3352 */       int i = BasicTabbedPaneUI.this.tabPane.getTabPlacement();
/* 3353 */       int j = BasicTabbedPaneUI.this.tabPane.getTabCount();
/* 3354 */       BasicTabbedPaneUI.this.assureRectsCreated(j);
/* 3355 */       Rectangle rectangle1 = this.viewport.getBounds();
/* 3356 */       Dimension dimension = this.viewport.getViewSize();
/* 3357 */       Rectangle rectangle2 = this.viewport.getViewRect();
/*      */       
/* 3359 */       this.leadingTabIndex = BasicTabbedPaneUI.this.getClosestTab(rectangle2.x, rectangle2.y);
/*      */ 
/*      */       
/* 3362 */       if (this.leadingTabIndex + 1 < j) {
/* 3363 */         switch (i) {
/*      */           case 1:
/*      */           case 3:
/* 3366 */             if ((BasicTabbedPaneUI.this.rects[this.leadingTabIndex]).x < rectangle2.x) {
/* 3367 */               this.leadingTabIndex++;
/*      */             }
/*      */             break;
/*      */           case 2:
/*      */           case 4:
/* 3372 */             if ((BasicTabbedPaneUI.this.rects[this.leadingTabIndex]).y < rectangle2.y) {
/* 3373 */               this.leadingTabIndex++;
/*      */             }
/*      */             break;
/*      */         } 
/*      */       }
/* 3378 */       Insets insets = BasicTabbedPaneUI.this.getContentBorderInsets(i);
/* 3379 */       switch (i) {
/*      */         case 2:
/* 3381 */           BasicTabbedPaneUI.this.tabPane.repaint(rectangle1.x + rectangle1.width, rectangle1.y, insets.left, rectangle1.height);
/*      */           
/* 3383 */           this.scrollBackwardButton.setEnabled((rectangle2.y > 0 && this.leadingTabIndex > 0));
/*      */           
/* 3385 */           this.scrollForwardButton.setEnabled((this.leadingTabIndex < j - 1 && dimension.height - rectangle2.y > rectangle2.height));
/*      */           return;
/*      */ 
/*      */         
/*      */         case 4:
/* 3390 */           BasicTabbedPaneUI.this.tabPane.repaint(rectangle1.x - insets.right, rectangle1.y, insets.right, rectangle1.height);
/*      */           
/* 3392 */           this.scrollBackwardButton.setEnabled((rectangle2.y > 0 && this.leadingTabIndex > 0));
/*      */           
/* 3394 */           this.scrollForwardButton.setEnabled((this.leadingTabIndex < j - 1 && dimension.height - rectangle2.y > rectangle2.height));
/*      */           return;
/*      */ 
/*      */         
/*      */         case 3:
/* 3399 */           BasicTabbedPaneUI.this.tabPane.repaint(rectangle1.x, rectangle1.y - insets.bottom, rectangle1.width, insets.bottom);
/*      */           
/* 3401 */           this.scrollBackwardButton.setEnabled((rectangle2.x > 0 && this.leadingTabIndex > 0));
/*      */           
/* 3403 */           this.scrollForwardButton.setEnabled((this.leadingTabIndex < j - 1 && dimension.width - rectangle2.x > rectangle2.width));
/*      */           return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3409 */       BasicTabbedPaneUI.this.tabPane.repaint(rectangle1.x, rectangle1.y + rectangle1.height, rectangle1.width, insets.top);
/*      */       
/* 3411 */       this.scrollBackwardButton.setEnabled((rectangle2.x > 0 && this.leadingTabIndex > 0));
/*      */       
/* 3413 */       this.scrollForwardButton.setEnabled((this.leadingTabIndex < j - 1 && dimension.width - rectangle2.x > rectangle2.width));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 3423 */       ActionMap actionMap = BasicTabbedPaneUI.this.tabPane.getActionMap();
/*      */       
/* 3425 */       if (actionMap != null) {
/*      */         String str;
/*      */         
/* 3428 */         if (param1ActionEvent.getSource() == this.scrollForwardButton) {
/* 3429 */           str = "scrollTabsForwardAction";
/*      */         } else {
/*      */           
/* 3432 */           str = "scrollTabsBackwardAction";
/*      */         } 
/* 3434 */         Action action = actionMap.get(str);
/*      */         
/* 3436 */         if (action != null && action.isEnabled()) {
/* 3437 */           action.actionPerformed(new ActionEvent(BasicTabbedPaneUI.this.tabPane, 1001, null, param1ActionEvent
/* 3438 */                 .getWhen(), param1ActionEvent
/* 3439 */                 .getModifiers()));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public String toString() {
/* 3445 */       return "viewport.viewSize=" + this.viewport.getViewSize() + "\nviewport.viewRectangle=" + this.viewport
/* 3446 */         .getViewRect() + "\nleadingTabIndex=" + this.leadingTabIndex + "\ntabViewPosition=" + this.tabViewPosition;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class ScrollableTabViewport
/*      */     extends JViewport
/*      */     implements UIResource
/*      */   {
/*      */     public ScrollableTabViewport() {
/* 3456 */       setName("TabbedPane.scrollableViewport");
/* 3457 */       setScrollMode(0);
/* 3458 */       setOpaque(BasicTabbedPaneUI.this.tabPane.isOpaque());
/* 3459 */       Color color = UIManager.getColor("TabbedPane.tabAreaBackground");
/* 3460 */       if (color == null) {
/* 3461 */         color = BasicTabbedPaneUI.this.tabPane.getBackground();
/*      */       }
/* 3463 */       setBackground(color);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ScrollableTabPanel extends JPanel implements UIResource {
/*      */     public ScrollableTabPanel() {
/* 3469 */       super((LayoutManager)null);
/* 3470 */       setOpaque(BasicTabbedPaneUI.this.tabPane.isOpaque());
/* 3471 */       Color color = UIManager.getColor("TabbedPane.tabAreaBackground");
/* 3472 */       if (color == null) {
/* 3473 */         color = BasicTabbedPaneUI.this.tabPane.getBackground();
/*      */       }
/* 3475 */       setBackground(color);
/*      */     }
/*      */     public void paintComponent(Graphics param1Graphics) {
/* 3478 */       super.paintComponent(param1Graphics);
/* 3479 */       BasicTabbedPaneUI.this.paintTabArea(param1Graphics, BasicTabbedPaneUI.this.tabPane.getTabPlacement(), BasicTabbedPaneUI.this.tabPane
/* 3480 */           .getSelectedIndex());
/* 3481 */       if (BasicTabbedPaneUI.this.tabScroller.croppedEdge.isParamsSet() && BasicTabbedPaneUI.this.tabContainer == null) {
/* 3482 */         Rectangle rectangle = BasicTabbedPaneUI.this.rects[BasicTabbedPaneUI.this.tabScroller.croppedEdge.getTabIndex()];
/* 3483 */         param1Graphics.translate(rectangle.x, rectangle.y);
/* 3484 */         BasicTabbedPaneUI.this.tabScroller.croppedEdge.paintComponent(param1Graphics);
/* 3485 */         param1Graphics.translate(-rectangle.x, -rectangle.y);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void doLayout() {
/* 3490 */       if (getComponentCount() > 0) {
/* 3491 */         Component component = getComponent(0);
/* 3492 */         component.setBounds(0, 0, getWidth(), getHeight());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ScrollableTabButton
/*      */     extends BasicArrowButton implements UIResource, SwingConstants {
/*      */     public ScrollableTabButton(int param1Int) {
/* 3500 */       super(param1Int, 
/* 3501 */           UIManager.getColor("TabbedPane.selected"), 
/* 3502 */           UIManager.getColor("TabbedPane.shadow"), 
/* 3503 */           UIManager.getColor("TabbedPane.darkShadow"), 
/* 3504 */           UIManager.getColor("TabbedPane.highlight"));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements ChangeListener, ContainerListener, FocusListener, MouseListener, MouseMotionListener, PropertyChangeListener
/*      */   {
/*      */     private Handler() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 3518 */       JTabbedPane jTabbedPane = (JTabbedPane)param1PropertyChangeEvent.getSource();
/* 3519 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 3520 */       boolean bool = BasicTabbedPaneUI.this.scrollableTabLayoutEnabled();
/* 3521 */       if (str == "mnemonicAt") {
/* 3522 */         BasicTabbedPaneUI.this.updateMnemonics();
/* 3523 */         jTabbedPane.repaint();
/*      */       }
/* 3525 */       else if (str == "displayedMnemonicIndexAt") {
/* 3526 */         jTabbedPane.repaint();
/*      */       }
/* 3528 */       else if (str == "indexForTitle") {
/* 3529 */         BasicTabbedPaneUI.this.calculatedBaseline = false;
/* 3530 */         Integer integer = (Integer)param1PropertyChangeEvent.getNewValue();
/*      */ 
/*      */         
/* 3533 */         if (BasicTabbedPaneUI.this.htmlViews != null) {
/* 3534 */           BasicTabbedPaneUI.this.htmlViews.removeElementAt(integer.intValue());
/*      */         }
/* 3536 */         updateHtmlViews(integer.intValue());
/* 3537 */       } else if (str == "tabLayoutPolicy") {
/* 3538 */         BasicTabbedPaneUI.this.uninstallUI(jTabbedPane);
/* 3539 */         BasicTabbedPaneUI.this.installUI(jTabbedPane);
/* 3540 */         BasicTabbedPaneUI.this.calculatedBaseline = false;
/* 3541 */       } else if (str == "tabPlacement") {
/* 3542 */         if (BasicTabbedPaneUI.this.scrollableTabLayoutEnabled()) {
/* 3543 */           BasicTabbedPaneUI.this.tabScroller.createButtons();
/*      */         }
/* 3545 */         BasicTabbedPaneUI.this.calculatedBaseline = false;
/* 3546 */       } else if (str == "opaque" && bool) {
/* 3547 */         boolean bool1 = ((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue();
/* 3548 */         BasicTabbedPaneUI.this.tabScroller.tabPanel.setOpaque(bool1);
/* 3549 */         BasicTabbedPaneUI.this.tabScroller.viewport.setOpaque(bool1);
/* 3550 */       } else if (str == "background" && bool) {
/* 3551 */         Color color1 = (Color)param1PropertyChangeEvent.getNewValue();
/* 3552 */         BasicTabbedPaneUI.this.tabScroller.tabPanel.setBackground(color1);
/* 3553 */         BasicTabbedPaneUI.this.tabScroller.viewport.setBackground(color1);
/* 3554 */         Color color2 = (BasicTabbedPaneUI.this.selectedColor == null) ? color1 : BasicTabbedPaneUI.this.selectedColor;
/* 3555 */         BasicTabbedPaneUI.this.tabScroller.scrollForwardButton.setBackground(color2);
/* 3556 */         BasicTabbedPaneUI.this.tabScroller.scrollBackwardButton.setBackground(color2);
/* 3557 */       } else if (str == "indexForTabComponent") {
/* 3558 */         if (BasicTabbedPaneUI.this.tabContainer != null) {
/* 3559 */           BasicTabbedPaneUI.this.tabContainer.removeUnusedTabComponents();
/*      */         }
/* 3561 */         Component component = BasicTabbedPaneUI.this.tabPane.getTabComponentAt(((Integer)param1PropertyChangeEvent
/* 3562 */             .getNewValue()).intValue());
/* 3563 */         if (component != null) {
/* 3564 */           if (BasicTabbedPaneUI.this.tabContainer == null) {
/* 3565 */             BasicTabbedPaneUI.this.installTabContainer();
/*      */           } else {
/* 3567 */             BasicTabbedPaneUI.this.tabContainer.add(component);
/*      */           } 
/*      */         }
/* 3570 */         BasicTabbedPaneUI.this.tabPane.revalidate();
/* 3571 */         BasicTabbedPaneUI.this.tabPane.repaint();
/* 3572 */         BasicTabbedPaneUI.this.calculatedBaseline = false;
/* 3573 */       } else if (str == "indexForNullComponent") {
/* 3574 */         BasicTabbedPaneUI.this.isRunsDirty = true;
/* 3575 */         updateHtmlViews(((Integer)param1PropertyChangeEvent.getNewValue()).intValue());
/* 3576 */       } else if (str == "font") {
/* 3577 */         BasicTabbedPaneUI.this.calculatedBaseline = false;
/*      */       } 
/*      */     }
/*      */     
/*      */     private void updateHtmlViews(int param1Int) {
/* 3582 */       String str = BasicTabbedPaneUI.this.tabPane.getTitleAt(param1Int);
/* 3583 */       boolean bool = BasicHTML.isHTMLString(str);
/* 3584 */       if (bool) {
/* 3585 */         if (BasicTabbedPaneUI.this.htmlViews == null) {
/* 3586 */           BasicTabbedPaneUI.this.htmlViews = BasicTabbedPaneUI.this.createHTMLVector();
/*      */         } else {
/* 3588 */           View view = BasicHTML.createHTMLView(BasicTabbedPaneUI.this.tabPane, str);
/* 3589 */           BasicTabbedPaneUI.this.htmlViews.insertElementAt(view, param1Int);
/*      */         }
/*      */       
/* 3592 */       } else if (BasicTabbedPaneUI.this.htmlViews != null) {
/* 3593 */         BasicTabbedPaneUI.this.htmlViews.insertElementAt(null, param1Int);
/*      */       } 
/*      */       
/* 3596 */       BasicTabbedPaneUI.this.updateMnemonics();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 3603 */       JTabbedPane jTabbedPane = (JTabbedPane)param1ChangeEvent.getSource();
/* 3604 */       jTabbedPane.revalidate();
/* 3605 */       jTabbedPane.repaint();
/*      */       
/* 3607 */       BasicTabbedPaneUI.this.setFocusIndex(jTabbedPane.getSelectedIndex(), false);
/*      */       
/* 3609 */       if (BasicTabbedPaneUI.this.scrollableTabLayoutEnabled()) {
/* 3610 */         BasicTabbedPaneUI.this.ensureCurrentLayout();
/* 3611 */         int i = jTabbedPane.getSelectedIndex();
/* 3612 */         if (i < BasicTabbedPaneUI.this.rects.length && i != -1) {
/* 3613 */           BasicTabbedPaneUI.this.tabScroller.tabPanel.scrollRectToVisible((Rectangle)BasicTabbedPaneUI.this.rects[i]
/* 3614 */               .clone());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 3629 */       BasicTabbedPaneUI.this.setRolloverTab(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 3633 */       BasicTabbedPaneUI.this.setRolloverTab(-1);
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 3637 */       if (!BasicTabbedPaneUI.this.tabPane.isEnabled()) {
/*      */         return;
/*      */       }
/* 3640 */       int i = BasicTabbedPaneUI.this.tabForCoordinate(BasicTabbedPaneUI.this.tabPane, param1MouseEvent.getX(), param1MouseEvent.getY());
/* 3641 */       if (i >= 0 && BasicTabbedPaneUI.this.tabPane.isEnabledAt(i)) {
/* 3642 */         if (i != BasicTabbedPaneUI.this.tabPane.getSelectedIndex()) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3647 */           BasicTabbedPaneUI.this.tabPane.setSelectedIndex(i);
/*      */         }
/* 3649 */         else if (BasicTabbedPaneUI.this.tabPane.isRequestFocusEnabled()) {
/*      */ 
/*      */           
/* 3652 */           BasicTabbedPaneUI.this.tabPane.requestFocus();
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 3664 */       BasicTabbedPaneUI.this.setRolloverTab(param1MouseEvent.getX(), param1MouseEvent.getY());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 3671 */       BasicTabbedPaneUI.this.setFocusIndex(BasicTabbedPaneUI.this.tabPane.getSelectedIndex(), true);
/*      */     }
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 3674 */       BasicTabbedPaneUI.this.repaintTab(BasicTabbedPaneUI.this.focusIndex);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 3712 */       JTabbedPane jTabbedPane = (JTabbedPane)param1ContainerEvent.getContainer();
/* 3713 */       Component component = param1ContainerEvent.getChild();
/* 3714 */       if (component instanceof UIResource) {
/*      */         return;
/*      */       }
/* 3717 */       BasicTabbedPaneUI.this.isRunsDirty = true;
/* 3718 */       updateHtmlViews(jTabbedPane.indexOfComponent(component));
/*      */     }
/*      */     public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 3721 */       JTabbedPane jTabbedPane = (JTabbedPane)param1ContainerEvent.getContainer();
/* 3722 */       Component component = param1ContainerEvent.getChild();
/* 3723 */       if (component instanceof UIResource) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3733 */       Integer integer = (Integer)jTabbedPane.getClientProperty("__index_to_remove__");
/* 3734 */       if (integer != null) {
/* 3735 */         int i = integer.intValue();
/* 3736 */         if (BasicTabbedPaneUI.this.htmlViews != null && BasicTabbedPaneUI.this.htmlViews.size() > i) {
/* 3737 */           BasicTabbedPaneUI.this.htmlViews.removeElementAt(i);
/*      */         }
/* 3739 */         jTabbedPane.putClientProperty("__index_to_remove__", (Object)null);
/*      */       } 
/* 3741 */       BasicTabbedPaneUI.this.isRunsDirty = true;
/* 3742 */       BasicTabbedPaneUI.this.updateMnemonics();
/*      */       
/* 3744 */       BasicTabbedPaneUI.this.validateFocusIndex();
/*      */     }
/*      */   }
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
/* 3758 */       BasicTabbedPaneUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class TabSelectionHandler
/*      */     implements ChangeListener
/*      */   {
/*      */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 3772 */       BasicTabbedPaneUI.this.getHandler().stateChanged(param1ChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class MouseHandler
/*      */     extends MouseAdapter
/*      */   {
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 3786 */       BasicTabbedPaneUI.this.getHandler().mousePressed(param1MouseEvent);
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
/*      */     extends FocusAdapter
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 3800 */       BasicTabbedPaneUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 3803 */       BasicTabbedPaneUI.this.getHandler().focusLost(param1FocusEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private Vector<View> createHTMLVector() {
/* 3808 */     Vector<View> vector = new Vector();
/* 3809 */     int i = this.tabPane.getTabCount();
/* 3810 */     if (i > 0) {
/* 3811 */       for (byte b = 0; b < i; b++) {
/* 3812 */         String str = this.tabPane.getTitleAt(b);
/* 3813 */         if (BasicHTML.isHTMLString(str)) {
/* 3814 */           vector.addElement(BasicHTML.createHTMLView(this.tabPane, str));
/*      */         } else {
/* 3816 */           vector.addElement(null);
/*      */         } 
/*      */       } 
/*      */     }
/* 3820 */     return vector;
/*      */   }
/*      */   
/*      */   private class TabContainer extends JPanel implements UIResource {
/*      */     private boolean notifyTabbedPane = true;
/*      */     
/*      */     public TabContainer() {
/* 3827 */       super((LayoutManager)null);
/* 3828 */       setOpaque(false);
/*      */     }
/*      */     
/*      */     public void remove(Component param1Component) {
/* 3832 */       int i = BasicTabbedPaneUI.this.tabPane.indexOfTabComponent(param1Component);
/* 3833 */       super.remove(param1Component);
/* 3834 */       if (this.notifyTabbedPane && i != -1) {
/* 3835 */         BasicTabbedPaneUI.this.tabPane.setTabComponentAt(i, (Component)null);
/*      */       }
/*      */     }
/*      */     
/*      */     private void removeUnusedTabComponents() {
/* 3840 */       for (Component component : getComponents()) {
/* 3841 */         if (!(component instanceof UIResource)) {
/* 3842 */           int i = BasicTabbedPaneUI.this.tabPane.indexOfTabComponent(component);
/* 3843 */           if (i == -1) {
/* 3844 */             super.remove(component);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean isOptimizedDrawingEnabled() {
/* 3851 */       return (BasicTabbedPaneUI.this.tabScroller != null && !BasicTabbedPaneUI.this.tabScroller.croppedEdge.isParamsSet());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void doLayout() {
/* 3858 */       if (BasicTabbedPaneUI.this.scrollableTabLayoutEnabled()) {
/* 3859 */         BasicTabbedPaneUI.this.tabScroller.tabPanel.repaint();
/* 3860 */         BasicTabbedPaneUI.this.tabScroller.updateView();
/*      */       } else {
/* 3862 */         BasicTabbedPaneUI.this.tabPane.repaint(getBounds());
/*      */       } 
/*      */     } }
/*      */   
/*      */   private class CroppedEdge extends JPanel implements UIResource {
/*      */     private Shape shape;
/*      */     private int tabIndex;
/*      */     private int cropline;
/*      */     private int cropx;
/*      */     private int cropy;
/*      */     
/*      */     public CroppedEdge() {
/* 3874 */       setOpaque(false);
/*      */     }
/*      */     
/*      */     public void setParams(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 3878 */       this.tabIndex = param1Int1;
/* 3879 */       this.cropline = param1Int2;
/* 3880 */       this.cropx = param1Int3;
/* 3881 */       this.cropy = param1Int4;
/* 3882 */       Rectangle rectangle = BasicTabbedPaneUI.this.rects[param1Int1];
/* 3883 */       setBounds(rectangle);
/* 3884 */       this.shape = BasicTabbedPaneUI.createCroppedTabShape(BasicTabbedPaneUI.this.tabPane.getTabPlacement(), rectangle, param1Int2);
/* 3885 */       if (getParent() == null && BasicTabbedPaneUI.this.tabContainer != null) {
/* 3886 */         BasicTabbedPaneUI.this.tabContainer.add(this, 0);
/*      */       }
/*      */     }
/*      */     
/*      */     public void resetParams() {
/* 3891 */       this.shape = null;
/* 3892 */       if (getParent() == BasicTabbedPaneUI.this.tabContainer && BasicTabbedPaneUI.this.tabContainer != null) {
/* 3893 */         BasicTabbedPaneUI.this.tabContainer.remove(this);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isParamsSet() {
/* 3898 */       return (this.shape != null);
/*      */     }
/*      */     
/*      */     public int getTabIndex() {
/* 3902 */       return this.tabIndex;
/*      */     }
/*      */     
/*      */     public int getCropline() {
/* 3906 */       return this.cropline;
/*      */     }
/*      */     
/*      */     public int getCroppedSideWidth() {
/* 3910 */       return 3;
/*      */     }
/*      */     
/*      */     private Color getBgColor() {
/* 3914 */       Container container = BasicTabbedPaneUI.this.tabPane.getParent();
/* 3915 */       if (container != null) {
/* 3916 */         Color color = container.getBackground();
/* 3917 */         if (color != null) {
/* 3918 */           return color;
/*      */         }
/*      */       } 
/* 3921 */       return UIManager.getColor("control");
/*      */     }
/*      */     
/*      */     protected void paintComponent(Graphics param1Graphics) {
/* 3925 */       super.paintComponent(param1Graphics);
/* 3926 */       if (isParamsSet() && param1Graphics instanceof Graphics2D) {
/* 3927 */         Graphics2D graphics2D = (Graphics2D)param1Graphics;
/* 3928 */         graphics2D.clipRect(0, 0, getWidth(), getHeight());
/* 3929 */         graphics2D.setColor(getBgColor());
/* 3930 */         graphics2D.translate(this.cropx, this.cropy);
/* 3931 */         graphics2D.fill(this.shape);
/* 3932 */         BasicTabbedPaneUI.this.paintCroppedTabEdge(param1Graphics);
/* 3933 */         graphics2D.translate(-this.cropx, -this.cropy);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicTabbedPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */