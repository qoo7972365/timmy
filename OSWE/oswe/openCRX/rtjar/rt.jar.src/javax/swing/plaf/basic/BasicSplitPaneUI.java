/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.Canvas;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.KeyboardFocusManager;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.LayoutManager2;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.FocusAdapter;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JSplitPane;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.SplitPaneUI;
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
/*      */ public class BasicSplitPaneUI
/*      */   extends SplitPaneUI
/*      */ {
/*      */   protected static final String NON_CONTINUOUS_DIVIDER = "nonContinuousDivider";
/*   67 */   protected static int KEYBOARD_DIVIDER_MOVE_OFFSET = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JSplitPane splitPane;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasicHorizontalLayoutManager layoutManager;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BasicSplitPaneDivider divider;
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
/*      */   protected FocusListener focusListener;
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
/*      */   private Set<KeyStroke> managingFocusForwardTraversalKeys;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<KeyStroke> managingFocusBackwardTraversalKeys;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int dividerSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component nonContinuousLayoutDivider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean draggingHW;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int beginDragDividerLocation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke upKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke downKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke leftKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke rightKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke homeKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke endKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke dividerResizeToggleKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected ActionListener keyboardUpLeftListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected ActionListener keyboardDownRightListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected ActionListener keyboardHomeListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected ActionListener keyboardEndListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected ActionListener keyboardResizeToggleListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int orientation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lastDragLocation;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean continuousLayout;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean dividerKeyboardResize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean dividerLocationIsSet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Color dividerDraggingColor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean rememberPaneSizes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean keepHidden = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean painted;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean ignoreDividerLocationChange;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  292 */     return new BasicSplitPaneUI();
/*      */   }
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  296 */     paramLazyActionMap.put(new Actions("negativeIncrement"));
/*  297 */     paramLazyActionMap.put(new Actions("positiveIncrement"));
/*  298 */     paramLazyActionMap.put(new Actions("selectMin"));
/*  299 */     paramLazyActionMap.put(new Actions("selectMax"));
/*  300 */     paramLazyActionMap.put(new Actions("startResize"));
/*  301 */     paramLazyActionMap.put(new Actions("toggleFocus"));
/*  302 */     paramLazyActionMap.put(new Actions("focusOutForward"));
/*  303 */     paramLazyActionMap.put(new Actions("focusOutBackward"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  312 */     this.splitPane = (JSplitPane)paramJComponent;
/*  313 */     this.dividerLocationIsSet = false;
/*  314 */     this.dividerKeyboardResize = false;
/*  315 */     this.keepHidden = false;
/*  316 */     installDefaults();
/*  317 */     installListeners();
/*  318 */     installKeyboardActions();
/*  319 */     setLastDragLocation(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installDefaults() {
/*  327 */     LookAndFeel.installBorder(this.splitPane, "SplitPane.border");
/*  328 */     LookAndFeel.installColors(this.splitPane, "SplitPane.background", "SplitPane.foreground");
/*      */     
/*  330 */     LookAndFeel.installProperty(this.splitPane, "opaque", Boolean.TRUE);
/*      */     
/*  332 */     if (this.divider == null) this.divider = createDefaultDivider(); 
/*  333 */     this.divider.setBasicSplitPaneUI(this);
/*      */     
/*  335 */     Border border = this.divider.getBorder();
/*      */     
/*  337 */     if (border == null || !(border instanceof javax.swing.plaf.UIResource)) {
/*  338 */       this.divider.setBorder(UIManager.getBorder("SplitPaneDivider.border"));
/*      */     }
/*      */     
/*  341 */     this.dividerDraggingColor = UIManager.getColor("SplitPaneDivider.draggingColor");
/*      */     
/*  343 */     setOrientation(this.splitPane.getOrientation());
/*      */ 
/*      */ 
/*      */     
/*  347 */     Integer integer = (Integer)UIManager.get("SplitPane.dividerSize");
/*  348 */     LookAndFeel.installProperty(this.splitPane, "dividerSize", Integer.valueOf((integer == null) ? 10 : integer.intValue()));
/*      */     
/*  350 */     this.divider.setDividerSize(this.splitPane.getDividerSize());
/*  351 */     this.dividerSize = this.divider.getDividerSize();
/*  352 */     this.splitPane.add(this.divider, "divider");
/*      */     
/*  354 */     setContinuousLayout(this.splitPane.isContinuousLayout());
/*      */     
/*  356 */     resetLayoutManager();
/*      */ 
/*      */ 
/*      */     
/*  360 */     if (this.nonContinuousLayoutDivider == null) {
/*  361 */       setNonContinuousLayoutDivider(
/*  362 */           createDefaultNonContinuousLayoutDivider(), true);
/*      */     } else {
/*      */       
/*  365 */       setNonContinuousLayoutDivider(this.nonContinuousLayoutDivider, true);
/*      */     } 
/*      */ 
/*      */     
/*  369 */     if (this.managingFocusForwardTraversalKeys == null) {
/*  370 */       this.managingFocusForwardTraversalKeys = new HashSet<>();
/*  371 */       this.managingFocusForwardTraversalKeys.add(
/*  372 */           KeyStroke.getKeyStroke(9, 0));
/*      */     } 
/*  374 */     this.splitPane.setFocusTraversalKeys(0, (Set)this.managingFocusForwardTraversalKeys);
/*      */ 
/*      */     
/*  377 */     if (this.managingFocusBackwardTraversalKeys == null) {
/*  378 */       this.managingFocusBackwardTraversalKeys = new HashSet<>();
/*  379 */       this.managingFocusBackwardTraversalKeys.add(
/*  380 */           KeyStroke.getKeyStroke(9, 1));
/*      */     } 
/*  382 */     this.splitPane.setFocusTraversalKeys(1, (Set)this.managingFocusBackwardTraversalKeys);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  391 */     if ((this.propertyChangeListener = createPropertyChangeListener()) != null)
/*      */     {
/*  393 */       this.splitPane.addPropertyChangeListener(this.propertyChangeListener);
/*      */     }
/*      */     
/*  396 */     if ((this.focusListener = createFocusListener()) != null) {
/*  397 */       this.splitPane.addFocusListener(this.focusListener);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {
/*  406 */     InputMap inputMap = getInputMap(1);
/*      */ 
/*      */     
/*  409 */     SwingUtilities.replaceUIInputMap(this.splitPane, 1, inputMap);
/*      */ 
/*      */     
/*  412 */     LazyActionMap.installLazyActionMap(this.splitPane, BasicSplitPaneUI.class, "SplitPane.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  417 */     if (paramInt == 1) {
/*  418 */       return (InputMap)DefaultLookup.get(this.splitPane, this, "SplitPane.ancestorInputMap");
/*      */     }
/*      */     
/*  421 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  428 */     uninstallKeyboardActions();
/*  429 */     uninstallListeners();
/*  430 */     uninstallDefaults();
/*  431 */     this.dividerLocationIsSet = false;
/*  432 */     this.dividerKeyboardResize = false;
/*  433 */     this.splitPane = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults() {
/*  441 */     if (this.splitPane.getLayout() == this.layoutManager) {
/*  442 */       this.splitPane.setLayout((LayoutManager)null);
/*      */     }
/*      */     
/*  445 */     if (this.nonContinuousLayoutDivider != null) {
/*  446 */       this.splitPane.remove(this.nonContinuousLayoutDivider);
/*      */     }
/*      */     
/*  449 */     LookAndFeel.uninstallBorder(this.splitPane);
/*      */     
/*  451 */     Border border = this.divider.getBorder();
/*      */     
/*  453 */     if (border instanceof javax.swing.plaf.UIResource) {
/*  454 */       this.divider.setBorder((Border)null);
/*      */     }
/*      */     
/*  457 */     this.splitPane.remove(this.divider);
/*  458 */     this.divider.setBasicSplitPaneUI((BasicSplitPaneUI)null);
/*  459 */     this.layoutManager = null;
/*  460 */     this.divider = null;
/*  461 */     this.nonContinuousLayoutDivider = null;
/*      */     
/*  463 */     setNonContinuousLayoutDivider(null);
/*      */ 
/*      */ 
/*      */     
/*  467 */     this.splitPane.setFocusTraversalKeys(0, (Set<? extends AWTKeyStroke>)null);
/*  468 */     this.splitPane.setFocusTraversalKeys(1, (Set<? extends AWTKeyStroke>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {
/*  476 */     if (this.propertyChangeListener != null) {
/*  477 */       this.splitPane.removePropertyChangeListener(this.propertyChangeListener);
/*  478 */       this.propertyChangeListener = null;
/*      */     } 
/*  480 */     if (this.focusListener != null) {
/*  481 */       this.splitPane.removeFocusListener(this.focusListener);
/*  482 */       this.focusListener = null;
/*      */     } 
/*      */     
/*  485 */     this.keyboardUpLeftListener = null;
/*  486 */     this.keyboardDownRightListener = null;
/*  487 */     this.keyboardHomeListener = null;
/*  488 */     this.keyboardEndListener = null;
/*  489 */     this.keyboardResizeToggleListener = null;
/*  490 */     this.handler = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  498 */     SwingUtilities.replaceUIActionMap(this.splitPane, null);
/*  499 */     SwingUtilities.replaceUIInputMap(this.splitPane, 1, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  509 */     return getHandler();
/*      */   }
/*      */   
/*      */   private Handler getHandler() {
/*  513 */     if (this.handler == null) {
/*  514 */       this.handler = new Handler();
/*      */     }
/*  516 */     return this.handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected FocusListener createFocusListener() {
/*  524 */     return getHandler();
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
/*      */   @Deprecated
/*      */   protected ActionListener createKeyboardUpLeftListener() {
/*  543 */     return new KeyboardUpLeftHandler();
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
/*      */   @Deprecated
/*      */   protected ActionListener createKeyboardDownRightListener() {
/*  562 */     return new KeyboardDownRightHandler();
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
/*      */   @Deprecated
/*      */   protected ActionListener createKeyboardHomeListener() {
/*  581 */     return new KeyboardHomeHandler();
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
/*      */   @Deprecated
/*      */   protected ActionListener createKeyboardEndListener() {
/*  600 */     return new KeyboardEndHandler();
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
/*      */   @Deprecated
/*      */   protected ActionListener createKeyboardResizeToggleListener() {
/*  619 */     return new KeyboardResizeToggleHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrientation() {
/*  627 */     return this.orientation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOrientation(int paramInt) {
/*  635 */     this.orientation = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isContinuousLayout() {
/*  643 */     return this.continuousLayout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContinuousLayout(boolean paramBoolean) {
/*  651 */     this.continuousLayout = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLastDragLocation() {
/*  659 */     return this.lastDragLocation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLastDragLocation(int paramInt) {
/*  667 */     this.lastDragLocation = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int getKeyboardMoveIncrement() {
/*  674 */     return 3;
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
/*      */   public class PropertyHandler
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  697 */       BasicSplitPaneUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
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
/*      */   public class FocusHandler
/*      */     extends FocusAdapter
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/*  715 */       BasicSplitPaneUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/*  719 */       BasicSplitPaneUI.this.getHandler().focusLost(param1FocusEvent);
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
/*      */   public class KeyboardUpLeftHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  734 */       if (BasicSplitPaneUI.this.dividerKeyboardResize) {
/*  735 */         BasicSplitPaneUI.this.splitPane.setDividerLocation(Math.max(0, BasicSplitPaneUI.this
/*  736 */               .getDividerLocation(BasicSplitPaneUI.this.splitPane) - BasicSplitPaneUI.this.getKeyboardMoveIncrement()));
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
/*      */   public class KeyboardDownRightHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  751 */       if (BasicSplitPaneUI.this.dividerKeyboardResize) {
/*  752 */         BasicSplitPaneUI.this.splitPane.setDividerLocation(BasicSplitPaneUI.this.getDividerLocation(BasicSplitPaneUI.this.splitPane) + BasicSplitPaneUI.this
/*  753 */             .getKeyboardMoveIncrement());
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
/*      */   public class KeyboardHomeHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  769 */       if (BasicSplitPaneUI.this.dividerKeyboardResize) {
/*  770 */         BasicSplitPaneUI.this.splitPane.setDividerLocation(0);
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
/*      */   public class KeyboardEndHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  786 */       if (BasicSplitPaneUI.this.dividerKeyboardResize) {
/*  787 */         Insets insets = BasicSplitPaneUI.this.splitPane.getInsets();
/*  788 */         byte b1 = (insets != null) ? insets.bottom : 0;
/*  789 */         byte b2 = (insets != null) ? insets.right : 0;
/*      */         
/*  791 */         if (BasicSplitPaneUI.this.orientation == 0) {
/*  792 */           BasicSplitPaneUI.this.splitPane.setDividerLocation(BasicSplitPaneUI.this.splitPane.getHeight() - b1);
/*      */         }
/*      */         else {
/*      */           
/*  796 */           BasicSplitPaneUI.this.splitPane.setDividerLocation(BasicSplitPaneUI.this.splitPane.getWidth() - b2);
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
/*      */ 
/*      */   
/*      */   public class KeyboardResizeToggleHandler
/*      */     implements ActionListener
/*      */   {
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  814 */       if (!BasicSplitPaneUI.this.dividerKeyboardResize) {
/*  815 */         BasicSplitPaneUI.this.splitPane.requestFocus();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicSplitPaneDivider getDivider() {
/*  824 */     return this.divider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component createDefaultNonContinuousLayoutDivider() {
/*  833 */     return new Canvas() {
/*      */         public void paint(Graphics param1Graphics) {
/*  835 */           if (!BasicSplitPaneUI.this.isContinuousLayout() && BasicSplitPaneUI.this.getLastDragLocation() != -1) {
/*  836 */             Dimension dimension = BasicSplitPaneUI.this.splitPane.getSize();
/*      */             
/*  838 */             param1Graphics.setColor(BasicSplitPaneUI.this.dividerDraggingColor);
/*  839 */             if (BasicSplitPaneUI.this.orientation == 1) {
/*  840 */               param1Graphics.fillRect(0, 0, BasicSplitPaneUI.this.dividerSize - 1, dimension.height - 1);
/*      */             } else {
/*  842 */               param1Graphics.fillRect(0, 0, dimension.width - 1, BasicSplitPaneUI.this.dividerSize - 1);
/*      */             } 
/*      */           } 
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
/*      */   protected void setNonContinuousLayoutDivider(Component paramComponent) {
/*  857 */     setNonContinuousLayoutDivider(paramComponent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setNonContinuousLayoutDivider(Component paramComponent, boolean paramBoolean) {
/*  866 */     this.rememberPaneSizes = paramBoolean;
/*  867 */     if (this.nonContinuousLayoutDivider != null && this.splitPane != null) {
/*  868 */       this.splitPane.remove(this.nonContinuousLayoutDivider);
/*      */     }
/*  870 */     this.nonContinuousLayoutDivider = paramComponent;
/*      */   }
/*      */   
/*      */   private void addHeavyweightDivider() {
/*  874 */     if (this.nonContinuousLayoutDivider != null && this.splitPane != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  880 */       Component component1 = this.splitPane.getLeftComponent();
/*  881 */       Component component2 = this.splitPane.getRightComponent();
/*      */       
/*  883 */       int i = this.splitPane.getDividerLocation();
/*      */       
/*  885 */       if (component1 != null)
/*  886 */         this.splitPane.setLeftComponent((Component)null); 
/*  887 */       if (component2 != null)
/*  888 */         this.splitPane.setRightComponent((Component)null); 
/*  889 */       this.splitPane.remove(this.divider);
/*  890 */       this.splitPane.add(this.nonContinuousLayoutDivider, "nonContinuousDivider", this.splitPane
/*      */           
/*  892 */           .getComponentCount());
/*  893 */       this.splitPane.setLeftComponent(component1);
/*  894 */       this.splitPane.setRightComponent(component2);
/*  895 */       this.splitPane.add(this.divider, "divider");
/*  896 */       if (this.rememberPaneSizes) {
/*  897 */         this.splitPane.setDividerLocation(i);
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
/*      */   public Component getNonContinuousLayoutDivider() {
/*  910 */     return this.nonContinuousLayoutDivider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JSplitPane getSplitPane() {
/*  919 */     return this.splitPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BasicSplitPaneDivider createDefaultDivider() {
/*  927 */     return new BasicSplitPaneDivider(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetToPreferredSizes(JSplitPane paramJSplitPane) {
/*  935 */     if (this.splitPane != null) {
/*  936 */       this.layoutManager.resetToPreferredSizes();
/*  937 */       this.splitPane.revalidate();
/*  938 */       this.splitPane.repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDividerLocation(JSplitPane paramJSplitPane, int paramInt) {
/*  947 */     if (!this.ignoreDividerLocationChange) {
/*  948 */       this.dividerLocationIsSet = true;
/*  949 */       this.splitPane.revalidate();
/*  950 */       this.splitPane.repaint();
/*      */       
/*  952 */       if (this.keepHidden) {
/*  953 */         Insets insets = this.splitPane.getInsets();
/*  954 */         int i = this.splitPane.getOrientation();
/*  955 */         if ((i == 0 && paramInt != insets.top && paramInt != this.splitPane
/*      */           
/*  957 */           .getHeight() - this.divider.getHeight() - insets.top) || (i == 1 && paramInt != insets.left && paramInt != this.splitPane
/*      */ 
/*      */           
/*  960 */           .getWidth() - this.divider.getWidth() - insets.left)) {
/*  961 */           setKeepHidden(false);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  966 */       this.ignoreDividerLocationChange = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDividerLocation(JSplitPane paramJSplitPane) {
/*  976 */     if (this.orientation == 1)
/*  977 */       return (this.divider.getLocation()).x; 
/*  978 */     return (this.divider.getLocation()).y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumDividerLocation(JSplitPane paramJSplitPane) {
/*  986 */     int i = 0;
/*  987 */     Component component = this.splitPane.getLeftComponent();
/*      */     
/*  989 */     if (component != null && component.isVisible()) {
/*  990 */       Insets insets = this.splitPane.getInsets();
/*  991 */       Dimension dimension = component.getMinimumSize();
/*  992 */       if (this.orientation == 1) {
/*  993 */         i = dimension.width;
/*      */       } else {
/*  995 */         i = dimension.height;
/*      */       } 
/*  997 */       if (insets != null) {
/*  998 */         if (this.orientation == 1) {
/*  999 */           i += insets.left;
/*      */         } else {
/* 1001 */           i += insets.top;
/*      */         } 
/*      */       }
/*      */     } 
/* 1005 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumDividerLocation(JSplitPane paramJSplitPane) {
/* 1013 */     Dimension dimension = this.splitPane.getSize();
/* 1014 */     int i = 0;
/* 1015 */     Component component = this.splitPane.getRightComponent();
/*      */     
/* 1017 */     if (component != null) {
/* 1018 */       Insets insets = this.splitPane.getInsets();
/* 1019 */       Dimension dimension1 = new Dimension(0, 0);
/* 1020 */       if (component.isVisible()) {
/* 1021 */         dimension1 = component.getMinimumSize();
/*      */       }
/* 1023 */       if (this.orientation == 1) {
/* 1024 */         i = dimension.width - dimension1.width;
/*      */       } else {
/* 1026 */         i = dimension.height - dimension1.height;
/*      */       } 
/* 1028 */       i -= this.dividerSize;
/* 1029 */       if (insets != null) {
/* 1030 */         if (this.orientation == 1) {
/* 1031 */           i -= insets.right;
/*      */         } else {
/* 1033 */           i -= insets.top;
/*      */         } 
/*      */       }
/*      */     } 
/* 1037 */     return Math.max(getMinimumDividerLocation(this.splitPane), i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finishedPaintingChildren(JSplitPane paramJSplitPane, Graphics paramGraphics) {
/* 1046 */     if (paramJSplitPane == this.splitPane && getLastDragLocation() != -1 && 
/* 1047 */       !isContinuousLayout() && !this.draggingHW) {
/* 1048 */       Dimension dimension = this.splitPane.getSize();
/*      */       
/* 1050 */       paramGraphics.setColor(this.dividerDraggingColor);
/* 1051 */       if (this.orientation == 1) {
/* 1052 */         paramGraphics.fillRect(getLastDragLocation(), 0, this.dividerSize - 1, dimension.height - 1);
/*      */       } else {
/*      */         
/* 1055 */         paramGraphics.fillRect(0, this.lastDragLocation, dimension.width - 1, this.dividerSize - 1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 1066 */     if (!this.painted && this.splitPane.getDividerLocation() < 0) {
/* 1067 */       this.ignoreDividerLocationChange = true;
/* 1068 */       this.splitPane.setDividerLocation(getDividerLocation(this.splitPane));
/*      */     } 
/* 1070 */     this.painted = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/* 1079 */     if (this.splitPane != null)
/* 1080 */       return this.layoutManager.preferredLayoutSize(this.splitPane); 
/* 1081 */     return new Dimension(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/* 1090 */     if (this.splitPane != null)
/* 1091 */       return this.layoutManager.minimumLayoutSize(this.splitPane); 
/* 1092 */     return new Dimension(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/* 1101 */     if (this.splitPane != null)
/* 1102 */       return this.layoutManager.maximumLayoutSize(this.splitPane); 
/* 1103 */     return new Dimension(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getInsets(JComponent paramJComponent) {
/* 1112 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetLayoutManager() {
/* 1121 */     if (this.orientation == 1) {
/* 1122 */       this.layoutManager = new BasicHorizontalLayoutManager(0);
/*      */     } else {
/* 1124 */       this.layoutManager = new BasicHorizontalLayoutManager(1);
/*      */     } 
/* 1126 */     this.splitPane.setLayout(this.layoutManager);
/* 1127 */     this.layoutManager.updateComponents();
/* 1128 */     this.splitPane.revalidate();
/* 1129 */     this.splitPane.repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setKeepHidden(boolean paramBoolean) {
/* 1136 */     this.keepHidden = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getKeepHidden() {
/* 1144 */     return this.keepHidden;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void startDragging() {
/* 1152 */     Component component1 = this.splitPane.getLeftComponent();
/* 1153 */     Component component2 = this.splitPane.getRightComponent();
/*      */ 
/*      */     
/* 1156 */     this.beginDragDividerLocation = getDividerLocation(this.splitPane);
/* 1157 */     this.draggingHW = false; ComponentPeer componentPeer;
/* 1158 */     if (component1 != null && (componentPeer = component1.getPeer()) != null && !(componentPeer instanceof java.awt.peer.LightweightPeer)) {
/*      */       
/* 1160 */       this.draggingHW = true;
/* 1161 */     } else if (component2 != null && (componentPeer = component2.getPeer()) != null && !(componentPeer instanceof java.awt.peer.LightweightPeer)) {
/*      */       
/* 1163 */       this.draggingHW = true;
/*      */     } 
/* 1165 */     if (this.orientation == 1) {
/* 1166 */       setLastDragLocation((this.divider.getBounds()).x);
/* 1167 */       this.dividerSize = (this.divider.getSize()).width;
/* 1168 */       if (!isContinuousLayout() && this.draggingHW) {
/* 1169 */         this.nonContinuousLayoutDivider
/* 1170 */           .setBounds(getLastDragLocation(), 0, this.dividerSize, this.splitPane
/* 1171 */             .getHeight());
/* 1172 */         addHeavyweightDivider();
/*      */       } 
/*      */     } else {
/* 1175 */       setLastDragLocation((this.divider.getBounds()).y);
/* 1176 */       this.dividerSize = (this.divider.getSize()).height;
/* 1177 */       if (!isContinuousLayout() && this.draggingHW) {
/* 1178 */         this.nonContinuousLayoutDivider
/* 1179 */           .setBounds(0, getLastDragLocation(), this.splitPane.getWidth(), this.dividerSize);
/*      */         
/* 1181 */         addHeavyweightDivider();
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
/*      */   protected void dragDividerTo(int paramInt) {
/* 1193 */     if (getLastDragLocation() != paramInt) {
/* 1194 */       if (isContinuousLayout()) {
/* 1195 */         this.splitPane.setDividerLocation(paramInt);
/* 1196 */         setLastDragLocation(paramInt);
/*      */       } else {
/* 1198 */         int i = getLastDragLocation();
/*      */         
/* 1200 */         setLastDragLocation(paramInt);
/* 1201 */         if (this.orientation == 1) {
/* 1202 */           if (this.draggingHW) {
/* 1203 */             this.nonContinuousLayoutDivider.setLocation(
/* 1204 */                 getLastDragLocation(), 0);
/*      */           } else {
/* 1206 */             int j = this.splitPane.getHeight();
/* 1207 */             this.splitPane.repaint(i, 0, this.dividerSize, j);
/*      */             
/* 1209 */             this.splitPane.repaint(paramInt, 0, this.dividerSize, j);
/*      */           }
/*      */         
/*      */         }
/* 1213 */         else if (this.draggingHW) {
/* 1214 */           this.nonContinuousLayoutDivider.setLocation(0, 
/* 1215 */               getLastDragLocation());
/*      */         } else {
/* 1217 */           int j = this.splitPane.getWidth();
/*      */           
/* 1219 */           this.splitPane.repaint(0, i, j, this.dividerSize);
/*      */           
/* 1221 */           this.splitPane.repaint(0, paramInt, j, this.dividerSize);
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
/*      */   
/*      */   protected void finishDraggingTo(int paramInt) {
/* 1235 */     dragDividerTo(paramInt);
/* 1236 */     setLastDragLocation(-1);
/* 1237 */     if (!isContinuousLayout()) {
/* 1238 */       Component component = this.splitPane.getLeftComponent();
/* 1239 */       Rectangle rectangle = component.getBounds();
/*      */       
/* 1241 */       if (this.draggingHW) {
/* 1242 */         if (this.orientation == 1) {
/* 1243 */           this.nonContinuousLayoutDivider.setLocation(-this.dividerSize, 0);
/*      */         } else {
/*      */           
/* 1246 */           this.nonContinuousLayoutDivider.setLocation(0, -this.dividerSize);
/*      */         } 
/* 1248 */         this.splitPane.remove(this.nonContinuousLayoutDivider);
/*      */       } 
/* 1250 */       this.splitPane.setDividerLocation(paramInt);
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
/*      */   @Deprecated
/*      */   protected int getDividerBorderSize() {
/* 1266 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class BasicHorizontalLayoutManager
/*      */     implements LayoutManager2
/*      */   {
/*      */     protected int[] sizes;
/*      */ 
/*      */     
/*      */     protected Component[] components;
/*      */ 
/*      */     
/*      */     private int lastSplitPaneSize;
/*      */     
/*      */     private boolean doReset;
/*      */     
/*      */     private int axis;
/*      */ 
/*      */     
/*      */     BasicHorizontalLayoutManager() {
/* 1288 */       this(0);
/*      */     }
/*      */     
/*      */     BasicHorizontalLayoutManager(int param1Int) {
/* 1292 */       this.axis = param1Int;
/* 1293 */       this.components = new Component[3];
/* 1294 */       this.components[2] = null; this.components[1] = null; this.components[0] = null;
/* 1295 */       this.sizes = new int[3];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/* 1306 */       Dimension dimension1 = param1Container.getSize();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1311 */       if (dimension1.height <= 0 || dimension1.width <= 0) {
/* 1312 */         this.lastSplitPaneSize = 0;
/*      */         
/*      */         return;
/*      */       } 
/* 1316 */       int i = BasicSplitPaneUI.this.splitPane.getDividerLocation();
/* 1317 */       Insets insets = BasicSplitPaneUI.this.splitPane.getInsets();
/* 1318 */       int j = getAvailableSize(dimension1, insets);
/*      */       
/* 1320 */       int k = getSizeForPrimaryAxis(dimension1);
/* 1321 */       int m = BasicSplitPaneUI.this.getDividerLocation(BasicSplitPaneUI.this.splitPane);
/* 1322 */       int n = getSizeForPrimaryAxis(insets, true);
/*      */       
/* 1324 */       Dimension dimension2 = (this.components[2] == null) ? null : this.components[2].getPreferredSize();
/*      */       
/* 1326 */       if ((this.doReset && !BasicSplitPaneUI.this.dividerLocationIsSet) || i < 0) {
/* 1327 */         resetToPreferredSizes(j);
/*      */       }
/* 1329 */       else if (this.lastSplitPaneSize <= 0 || j == this.lastSplitPaneSize || !BasicSplitPaneUI.this.painted || (dimension2 != null && 
/*      */ 
/*      */         
/* 1332 */         getSizeForPrimaryAxis(dimension2) != this.sizes[2])) {
/* 1333 */         if (dimension2 != null) {
/* 1334 */           this.sizes[2] = getSizeForPrimaryAxis(dimension2);
/*      */         } else {
/*      */           
/* 1337 */           this.sizes[2] = 0;
/*      */         } 
/* 1339 */         setDividerLocation(i - n, j);
/* 1340 */         BasicSplitPaneUI.this.dividerLocationIsSet = false;
/*      */       }
/* 1342 */       else if (j != this.lastSplitPaneSize) {
/* 1343 */         distributeSpace(j - this.lastSplitPaneSize, BasicSplitPaneUI.this
/* 1344 */             .getKeepHidden());
/*      */       } 
/* 1346 */       this.doReset = false;
/* 1347 */       BasicSplitPaneUI.this.dividerLocationIsSet = false;
/* 1348 */       this.lastSplitPaneSize = j;
/*      */ 
/*      */       
/* 1351 */       int i1 = getInitialLocation(insets);
/* 1352 */       byte b = 0;
/*      */       
/* 1354 */       while (b < 3) {
/* 1355 */         if (this.components[b] != null && this.components[b]
/* 1356 */           .isVisible()) {
/* 1357 */           setComponentToSize(this.components[b], this.sizes[b], i1, insets, dimension1);
/*      */           
/* 1359 */           i1 += this.sizes[b];
/*      */         } 
/* 1361 */         switch (b) {
/*      */           case 0:
/* 1363 */             b = 2;
/*      */           
/*      */           case 2:
/* 1366 */             b = 1;
/*      */           
/*      */           case 1:
/* 1369 */             b = 3;
/*      */         } 
/*      */       
/*      */       } 
/* 1373 */       if (BasicSplitPaneUI.this.painted) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1378 */         int i2 = BasicSplitPaneUI.this.getDividerLocation(BasicSplitPaneUI.this.splitPane);
/*      */         
/* 1380 */         if (i2 != i - n) {
/* 1381 */           int i3 = BasicSplitPaneUI.this.splitPane.getLastDividerLocation();
/*      */           
/* 1383 */           BasicSplitPaneUI.this.ignoreDividerLocationChange = true;
/*      */           try {
/* 1385 */             BasicSplitPaneUI.this.splitPane.setDividerLocation(i2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1394 */             BasicSplitPaneUI.this.splitPane.setLastDividerLocation(i3);
/*      */           } finally {
/* 1396 */             BasicSplitPaneUI.this.ignoreDividerLocationChange = false;
/*      */           } 
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
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {
/* 1409 */       boolean bool = true;
/*      */       
/* 1411 */       if (param1String != null)
/* 1412 */       { if (param1String.equals("divider")) {
/*      */           
/* 1414 */           this.components[2] = param1Component;
/* 1415 */           this.sizes[2] = getSizeForPrimaryAxis(param1Component
/* 1416 */               .getPreferredSize());
/* 1417 */         } else if (param1String.equals("left") || param1String
/* 1418 */           .equals("top")) {
/* 1419 */           this.components[0] = param1Component;
/* 1420 */           this.sizes[0] = 0;
/* 1421 */         } else if (param1String.equals("right") || param1String
/* 1422 */           .equals("bottom")) {
/* 1423 */           this.components[1] = param1Component;
/* 1424 */           this.sizes[1] = 0;
/* 1425 */         } else if (!param1String.equals("nonContinuousDivider")) {
/*      */           
/* 1427 */           bool = false;
/*      */         }  }
/* 1429 */       else { bool = false; }
/*      */       
/* 1431 */       if (!bool) {
/* 1432 */         throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + param1String);
/*      */       }
/*      */       
/* 1435 */       this.doReset = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1445 */       int i = 0;
/* 1446 */       int j = 0;
/* 1447 */       Insets insets = BasicSplitPaneUI.this.splitPane.getInsets();
/*      */       
/* 1449 */       for (byte b = 0; b < 3; b++) {
/* 1450 */         if (this.components[b] != null) {
/* 1451 */           Dimension dimension = this.components[b].getMinimumSize();
/* 1452 */           int k = getSizeForSecondaryAxis(dimension);
/*      */           
/* 1454 */           i += getSizeForPrimaryAxis(dimension);
/* 1455 */           if (k > j)
/* 1456 */             j = k; 
/*      */         } 
/*      */       } 
/* 1459 */       if (insets != null) {
/* 1460 */         i += getSizeForPrimaryAxis(insets, true) + 
/* 1461 */           getSizeForPrimaryAxis(insets, false);
/* 1462 */         j += getSizeForSecondaryAxis(insets, true) + 
/* 1463 */           getSizeForSecondaryAxis(insets, false);
/*      */       } 
/* 1465 */       if (this.axis == 0) {
/* 1466 */         return new Dimension(i, j);
/*      */       }
/* 1468 */       return new Dimension(j, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1478 */       int i = 0;
/* 1479 */       int j = 0;
/* 1480 */       Insets insets = BasicSplitPaneUI.this.splitPane.getInsets();
/*      */       
/* 1482 */       for (byte b = 0; b < 3; b++) {
/* 1483 */         if (this.components[b] != null) {
/*      */           
/* 1485 */           Dimension dimension = this.components[b].getPreferredSize();
/* 1486 */           int k = getSizeForSecondaryAxis(dimension);
/*      */           
/* 1488 */           i += getSizeForPrimaryAxis(dimension);
/* 1489 */           if (k > j)
/* 1490 */             j = k; 
/*      */         } 
/*      */       } 
/* 1493 */       if (insets != null) {
/* 1494 */         i += getSizeForPrimaryAxis(insets, true) + 
/* 1495 */           getSizeForPrimaryAxis(insets, false);
/* 1496 */         j += getSizeForSecondaryAxis(insets, true) + 
/* 1497 */           getSizeForSecondaryAxis(insets, false);
/*      */       } 
/* 1499 */       if (this.axis == 0) {
/* 1500 */         return new Dimension(i, j);
/*      */       }
/* 1502 */       return new Dimension(j, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {
/* 1510 */       for (byte b = 0; b < 3; b++) {
/* 1511 */         if (this.components[b] == param1Component) {
/* 1512 */           this.components[b] = null;
/* 1513 */           this.sizes[b] = 0;
/* 1514 */           this.doReset = true;
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
/*      */ 
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(Component param1Component, Object param1Object) {
/* 1532 */       if (param1Object == null || param1Object instanceof String) {
/* 1533 */         addLayoutComponent((String)param1Object, param1Component);
/*      */       } else {
/* 1535 */         throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
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
/*      */     
/*      */     public float getLayoutAlignmentX(Container param1Container) {
/* 1550 */       return 0.0F;
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
/*      */     public float getLayoutAlignmentY(Container param1Container) {
/* 1562 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invalidateLayout(Container param1Container) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Dimension maximumLayoutSize(Container param1Container) {
/* 1580 */       return new Dimension(2147483647, 2147483647);
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
/*      */     public void resetToPreferredSizes() {
/* 1593 */       this.doReset = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void resetSizeAt(int param1Int) {
/* 1600 */       this.sizes[param1Int] = 0;
/* 1601 */       this.doReset = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void setSizes(int[] param1ArrayOfint) {
/* 1609 */       System.arraycopy(param1ArrayOfint, 0, this.sizes, 0, 3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int[] getSizes() {
/* 1617 */       int[] arrayOfInt = new int[3];
/*      */       
/* 1619 */       System.arraycopy(this.sizes, 0, arrayOfInt, 0, 3);
/* 1620 */       return arrayOfInt;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getPreferredSizeOfComponent(Component param1Component) {
/* 1628 */       return getSizeForPrimaryAxis(param1Component.getPreferredSize());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getMinimumSizeOfComponent(Component param1Component) {
/* 1636 */       return getSizeForPrimaryAxis(param1Component.getMinimumSize());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getSizeOfComponent(Component param1Component) {
/* 1644 */       return getSizeForPrimaryAxis(param1Component.getSize());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getAvailableSize(Dimension param1Dimension, Insets param1Insets) {
/* 1654 */       if (param1Insets == null)
/* 1655 */         return getSizeForPrimaryAxis(param1Dimension); 
/* 1656 */       return getSizeForPrimaryAxis(param1Dimension) - 
/* 1657 */         getSizeForPrimaryAxis(param1Insets, true) + 
/* 1658 */         getSizeForPrimaryAxis(param1Insets, false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int getInitialLocation(Insets param1Insets) {
/* 1667 */       if (param1Insets != null)
/* 1668 */         return getSizeForPrimaryAxis(param1Insets, true); 
/* 1669 */       return 0;
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
/*      */     protected void setComponentToSize(Component param1Component, int param1Int1, int param1Int2, Insets param1Insets, Dimension param1Dimension) {
/* 1681 */       if (param1Insets != null) {
/* 1682 */         if (this.axis == 0) {
/* 1683 */           param1Component.setBounds(param1Int2, param1Insets.top, param1Int1, param1Dimension.height - param1Insets.top + param1Insets.bottom);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1688 */           param1Component.setBounds(param1Insets.left, param1Int2, param1Dimension.width - param1Insets.left + param1Insets.right, param1Int1);
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1693 */       else if (this.axis == 0) {
/* 1694 */         param1Component.setBounds(param1Int2, 0, param1Int1, param1Dimension.height);
/*      */       } else {
/*      */         
/* 1697 */         param1Component.setBounds(0, param1Int2, param1Dimension.width, param1Int1);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getSizeForPrimaryAxis(Dimension param1Dimension) {
/* 1706 */       if (this.axis == 0) {
/* 1707 */         return param1Dimension.width;
/*      */       }
/* 1709 */       return param1Dimension.height;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getSizeForSecondaryAxis(Dimension param1Dimension) {
/* 1716 */       if (this.axis == 0) {
/* 1717 */         return param1Dimension.height;
/*      */       }
/* 1719 */       return param1Dimension.width;
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
/*      */     int getSizeForPrimaryAxis(Insets param1Insets, boolean param1Boolean) {
/* 1732 */       if (this.axis == 0) {
/* 1733 */         if (param1Boolean) {
/* 1734 */           return param1Insets.left;
/*      */         }
/* 1736 */         return param1Insets.right;
/*      */       } 
/* 1738 */       if (param1Boolean) {
/* 1739 */         return param1Insets.top;
/*      */       }
/* 1741 */       return param1Insets.bottom;
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
/*      */     int getSizeForSecondaryAxis(Insets param1Insets, boolean param1Boolean) {
/* 1754 */       if (this.axis == 0) {
/* 1755 */         if (param1Boolean) {
/* 1756 */           return param1Insets.top;
/*      */         }
/* 1758 */         return param1Insets.bottom;
/*      */       } 
/* 1760 */       if (param1Boolean) {
/* 1761 */         return param1Insets.left;
/*      */       }
/* 1763 */       return param1Insets.right;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void updateComponents() {
/* 1774 */       Component component1 = BasicSplitPaneUI.this.splitPane.getLeftComponent();
/* 1775 */       if (this.components[0] != component1) {
/* 1776 */         this.components[0] = component1;
/* 1777 */         if (component1 == null) {
/* 1778 */           this.sizes[0] = 0;
/*      */         } else {
/* 1780 */           this.sizes[0] = -1;
/*      */         } 
/*      */       } 
/*      */       
/* 1784 */       component1 = BasicSplitPaneUI.this.splitPane.getRightComponent();
/* 1785 */       if (this.components[1] != component1) {
/* 1786 */         this.components[1] = component1;
/* 1787 */         if (component1 == null) {
/* 1788 */           this.sizes[1] = 0;
/*      */         } else {
/* 1790 */           this.sizes[1] = -1;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1795 */       Component[] arrayOfComponent = BasicSplitPaneUI.this.splitPane.getComponents();
/* 1796 */       Component component2 = this.components[2];
/*      */       
/* 1798 */       this.components[2] = null;
/* 1799 */       for (int i = arrayOfComponent.length - 1; i >= 0; i--) {
/* 1800 */         if (arrayOfComponent[i] != this.components[0] && arrayOfComponent[i] != this.components[1] && arrayOfComponent[i] != BasicSplitPaneUI.this.nonContinuousLayoutDivider) {
/*      */ 
/*      */           
/* 1803 */           if (component2 != arrayOfComponent[i]) {
/* 1804 */             this.components[2] = arrayOfComponent[i]; break;
/*      */           } 
/* 1806 */           this.components[2] = component2;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1811 */       if (this.components[2] == null) {
/* 1812 */         this.sizes[2] = 0;
/*      */       } else {
/*      */         
/* 1815 */         this.sizes[2] = getSizeForPrimaryAxis(this.components[2].getPreferredSize());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setDividerLocation(int param1Int1, int param1Int2) {
/* 1825 */       boolean bool1 = (this.components[0] != null && this.components[0].isVisible()) ? true : false;
/*      */       
/* 1827 */       boolean bool2 = (this.components[1] != null && this.components[1].isVisible()) ? true : false;
/*      */       
/* 1829 */       boolean bool3 = (this.components[2] != null && this.components[2].isVisible()) ? true : false;
/* 1830 */       int i = param1Int2;
/*      */       
/* 1832 */       if (bool3) {
/* 1833 */         i -= this.sizes[2];
/*      */       }
/* 1835 */       param1Int1 = Math.max(0, Math.min(param1Int1, i));
/* 1836 */       if (bool1) {
/* 1837 */         if (bool2) {
/* 1838 */           this.sizes[0] = param1Int1;
/* 1839 */           this.sizes[1] = i - param1Int1;
/*      */         } else {
/*      */           
/* 1842 */           this.sizes[0] = i;
/* 1843 */           this.sizes[1] = 0;
/*      */         }
/*      */       
/* 1846 */       } else if (bool2) {
/* 1847 */         this.sizes[1] = i;
/* 1848 */         this.sizes[0] = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int[] getPreferredSizes() {
/* 1856 */       int[] arrayOfInt = new int[3];
/*      */       
/* 1858 */       for (byte b = 0; b < 3; b++) {
/* 1859 */         if (this.components[b] != null && this.components[b]
/* 1860 */           .isVisible()) {
/* 1861 */           arrayOfInt[b] = 
/* 1862 */             getPreferredSizeOfComponent(this.components[b]);
/*      */         } else {
/*      */           
/* 1865 */           arrayOfInt[b] = -1;
/*      */         } 
/*      */       } 
/* 1868 */       return arrayOfInt;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int[] getMinimumSizes() {
/* 1875 */       int[] arrayOfInt = new int[3];
/*      */       
/* 1877 */       for (byte b = 0; b < 2; b++) {
/* 1878 */         if (this.components[b] != null && this.components[b]
/* 1879 */           .isVisible()) {
/* 1880 */           arrayOfInt[b] = 
/* 1881 */             getMinimumSizeOfComponent(this.components[b]);
/*      */         } else {
/*      */           
/* 1884 */           arrayOfInt[b] = -1;
/*      */         } 
/*      */       } 
/* 1887 */       arrayOfInt[2] = (this.components[2] != null) ? 
/* 1888 */         getMinimumSizeOfComponent(this.components[2]) : -1;
/* 1889 */       return arrayOfInt;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void resetToPreferredSizes(int param1Int) {
/* 1898 */       int[] arrayOfInt = getPreferredSizes();
/* 1899 */       int i = 0;
/*      */       byte b;
/* 1901 */       for (b = 0; b < 3; b++) {
/* 1902 */         if (arrayOfInt[b] != -1) {
/* 1903 */           i += arrayOfInt[b];
/*      */         }
/*      */       } 
/* 1906 */       if (i > param1Int) {
/* 1907 */         arrayOfInt = getMinimumSizes();
/*      */         
/* 1909 */         i = 0;
/* 1910 */         for (b = 0; b < 3; b++) {
/* 1911 */           if (arrayOfInt[b] != -1) {
/* 1912 */             i += arrayOfInt[b];
/*      */           }
/*      */         } 
/*      */       } 
/* 1916 */       setSizes(arrayOfInt);
/* 1917 */       distributeSpace(param1Int - i, false);
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
/*      */     void distributeSpace(int param1Int, boolean param1Boolean) {
/* 1930 */       boolean bool1 = (this.components[0] != null && this.components[0].isVisible()) ? true : false;
/*      */       
/* 1932 */       boolean bool2 = (this.components[1] != null && this.components[1].isVisible()) ? true : false;
/*      */       
/* 1934 */       if (param1Boolean) {
/* 1935 */         if (bool1 && getSizeForPrimaryAxis(this.components[0]
/* 1936 */             .getSize()) == 0) {
/* 1937 */           bool1 = false;
/* 1938 */           if (bool2 && getSizeForPrimaryAxis(this.components[1]
/* 1939 */               .getSize()) == 0)
/*      */           {
/* 1941 */             bool1 = true;
/*      */           }
/*      */         }
/* 1944 */         else if (bool2 && getSizeForPrimaryAxis(this.components[1]
/* 1945 */             .getSize()) == 0) {
/* 1946 */           bool2 = false;
/*      */         } 
/*      */       }
/* 1949 */       if (bool1 && bool2) {
/* 1950 */         double d = BasicSplitPaneUI.this.splitPane.getResizeWeight();
/* 1951 */         int i = (int)(d * param1Int);
/* 1952 */         int j = param1Int - i;
/*      */         
/* 1954 */         this.sizes[0] = this.sizes[0] + i;
/* 1955 */         this.sizes[1] = this.sizes[1] + j;
/*      */         
/* 1957 */         int k = getMinimumSizeOfComponent(this.components[0]);
/* 1958 */         int m = getMinimumSizeOfComponent(this.components[1]);
/* 1959 */         boolean bool3 = (this.sizes[0] >= k) ? true : false;
/* 1960 */         boolean bool4 = (this.sizes[1] >= m) ? true : false;
/*      */         
/* 1962 */         if (!bool3 && !bool4) {
/* 1963 */           if (this.sizes[0] < 0) {
/* 1964 */             this.sizes[1] = this.sizes[1] + this.sizes[0];
/* 1965 */             this.sizes[0] = 0;
/*      */           }
/* 1967 */           else if (this.sizes[1] < 0) {
/* 1968 */             this.sizes[0] = this.sizes[0] + this.sizes[1];
/* 1969 */             this.sizes[1] = 0;
/*      */           }
/*      */         
/* 1972 */         } else if (!bool3) {
/* 1973 */           if (this.sizes[1] - k - this.sizes[0] < m) {
/*      */             
/* 1975 */             if (this.sizes[0] < 0) {
/* 1976 */               this.sizes[1] = this.sizes[1] + this.sizes[0];
/* 1977 */               this.sizes[0] = 0;
/*      */             } 
/*      */           } else {
/*      */             
/* 1981 */             this.sizes[1] = this.sizes[1] - k - this.sizes[0];
/* 1982 */             this.sizes[0] = k;
/*      */           }
/*      */         
/* 1985 */         } else if (!bool4) {
/* 1986 */           if (this.sizes[0] - m - this.sizes[1] < k) {
/*      */             
/* 1988 */             if (this.sizes[1] < 0) {
/* 1989 */               this.sizes[0] = this.sizes[0] + this.sizes[1];
/* 1990 */               this.sizes[1] = 0;
/*      */             } 
/*      */           } else {
/*      */             
/* 1994 */             this.sizes[0] = this.sizes[0] - m - this.sizes[1];
/* 1995 */             this.sizes[1] = m;
/*      */           } 
/*      */         } 
/* 1998 */         if (this.sizes[0] < 0) {
/* 1999 */           this.sizes[0] = 0;
/*      */         }
/* 2001 */         if (this.sizes[1] < 0) {
/* 2002 */           this.sizes[1] = 0;
/*      */         }
/*      */       }
/* 2005 */       else if (bool1) {
/* 2006 */         this.sizes[0] = Math.max(0, this.sizes[0] + param1Int);
/*      */       }
/* 2008 */       else if (bool2) {
/* 2009 */         this.sizes[1] = Math.max(0, this.sizes[1] + param1Int);
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
/*      */   public class BasicVerticalLayoutManager
/*      */     extends BasicHorizontalLayoutManager
/*      */   {
/*      */     public BasicVerticalLayoutManager() {
/* 2024 */       super(1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements FocusListener, PropertyChangeListener
/*      */   {
/*      */     private Handler() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 2039 */       if (param1PropertyChangeEvent.getSource() == BasicSplitPaneUI.this.splitPane) {
/* 2040 */         String str = param1PropertyChangeEvent.getPropertyName();
/*      */         
/* 2042 */         if (str == "orientation") {
/* 2043 */           BasicSplitPaneUI.this.orientation = BasicSplitPaneUI.this.splitPane.getOrientation();
/* 2044 */           BasicSplitPaneUI.this.resetLayoutManager();
/* 2045 */         } else if (str == "continuousLayout") {
/* 2046 */           BasicSplitPaneUI.this.setContinuousLayout(BasicSplitPaneUI.this.splitPane.isContinuousLayout());
/* 2047 */           if (!BasicSplitPaneUI.this.isContinuousLayout()) {
/* 2048 */             if (BasicSplitPaneUI.this.nonContinuousLayoutDivider == null) {
/* 2049 */               BasicSplitPaneUI.this.setNonContinuousLayoutDivider(BasicSplitPaneUI.this
/* 2050 */                   .createDefaultNonContinuousLayoutDivider(), true);
/*      */             }
/* 2052 */             else if (BasicSplitPaneUI.this.nonContinuousLayoutDivider.getParent() == null) {
/*      */               
/* 2054 */               BasicSplitPaneUI.this.setNonContinuousLayoutDivider(BasicSplitPaneUI.this.nonContinuousLayoutDivider, true);
/*      */             }
/*      */           
/*      */           }
/*      */         }
/* 2059 */         else if (str == "dividerSize") {
/* 2060 */           BasicSplitPaneUI.this.divider.setDividerSize(BasicSplitPaneUI.this.splitPane.getDividerSize());
/* 2061 */           BasicSplitPaneUI.this.dividerSize = BasicSplitPaneUI.this.divider.getDividerSize();
/* 2062 */           BasicSplitPaneUI.this.splitPane.revalidate();
/* 2063 */           BasicSplitPaneUI.this.splitPane.repaint();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 2072 */       BasicSplitPaneUI.this.dividerKeyboardResize = true;
/* 2073 */       BasicSplitPaneUI.this.splitPane.repaint();
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 2077 */       BasicSplitPaneUI.this.dividerKeyboardResize = false;
/* 2078 */       BasicSplitPaneUI.this.splitPane.repaint();
/*      */     }
/*      */   }
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction {
/*      */     private static final String NEGATIVE_INCREMENT = "negativeIncrement";
/*      */     private static final String POSITIVE_INCREMENT = "positiveIncrement";
/*      */     private static final String SELECT_MIN = "selectMin";
/*      */     private static final String SELECT_MAX = "selectMax";
/*      */     private static final String START_RESIZE = "startResize";
/*      */     private static final String TOGGLE_FOCUS = "toggleFocus";
/*      */     private static final String FOCUS_OUT_FORWARD = "focusOutForward";
/*      */     private static final String FOCUS_OUT_BACKWARD = "focusOutBackward";
/*      */     
/*      */     Actions(String param1String) {
/* 2094 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 2098 */       JSplitPane jSplitPane = (JSplitPane)param1ActionEvent.getSource();
/*      */       
/* 2100 */       BasicSplitPaneUI basicSplitPaneUI = (BasicSplitPaneUI)BasicLookAndFeel.getUIOfType(jSplitPane.getUI(), BasicSplitPaneUI.class);
/*      */       
/* 2102 */       if (basicSplitPaneUI == null) {
/*      */         return;
/*      */       }
/* 2105 */       String str = getName();
/* 2106 */       if (str == "negativeIncrement") {
/* 2107 */         if (basicSplitPaneUI.dividerKeyboardResize) {
/* 2108 */           jSplitPane.setDividerLocation(Math.max(0, basicSplitPaneUI
/*      */                 
/* 2110 */                 .getDividerLocation(jSplitPane) - basicSplitPaneUI.getKeyboardMoveIncrement()));
/*      */         }
/*      */       }
/* 2113 */       else if (str == "positiveIncrement") {
/* 2114 */         if (basicSplitPaneUI.dividerKeyboardResize) {
/* 2115 */           jSplitPane.setDividerLocation(basicSplitPaneUI
/* 2116 */               .getDividerLocation(jSplitPane) + basicSplitPaneUI
/* 2117 */               .getKeyboardMoveIncrement());
/*      */         }
/*      */       }
/* 2120 */       else if (str == "selectMin") {
/* 2121 */         if (basicSplitPaneUI.dividerKeyboardResize) {
/* 2122 */           jSplitPane.setDividerLocation(0);
/*      */         }
/*      */       }
/* 2125 */       else if (str == "selectMax") {
/* 2126 */         if (basicSplitPaneUI.dividerKeyboardResize) {
/* 2127 */           Insets insets = jSplitPane.getInsets();
/* 2128 */           byte b1 = (insets != null) ? insets.bottom : 0;
/* 2129 */           byte b2 = (insets != null) ? insets.right : 0;
/*      */           
/* 2131 */           if (basicSplitPaneUI.orientation == 0) {
/* 2132 */             jSplitPane.setDividerLocation(jSplitPane.getHeight() - b1);
/*      */           }
/*      */           else {
/*      */             
/* 2136 */             jSplitPane.setDividerLocation(jSplitPane.getWidth() - b2);
/*      */           }
/*      */         
/*      */         }
/*      */       
/* 2141 */       } else if (str == "startResize") {
/* 2142 */         if (!basicSplitPaneUI.dividerKeyboardResize) {
/* 2143 */           jSplitPane.requestFocus();
/*      */         } else {
/*      */           
/* 2146 */           JSplitPane jSplitPane1 = (JSplitPane)SwingUtilities.getAncestorOfClass(JSplitPane.class, jSplitPane);
/*      */           
/* 2148 */           if (jSplitPane1 != null) {
/* 2149 */             jSplitPane1.requestFocus();
/*      */           }
/*      */         }
/*      */       
/* 2153 */       } else if (str == "toggleFocus") {
/* 2154 */         toggleFocus(jSplitPane);
/*      */       }
/* 2156 */       else if (str == "focusOutForward") {
/* 2157 */         moveFocus(jSplitPane, 1);
/*      */       }
/* 2159 */       else if (str == "focusOutBackward") {
/* 2160 */         moveFocus(jSplitPane, -1);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void moveFocus(JSplitPane param1JSplitPane, int param1Int) {
/* 2165 */       Container container = param1JSplitPane.getFocusCycleRootAncestor();
/* 2166 */       FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/*      */ 
/*      */       
/* 2169 */       Component component = (param1Int > 0) ? focusTraversalPolicy.getComponentAfter(container, param1JSplitPane) : focusTraversalPolicy.getComponentBefore(container, param1JSplitPane);
/* 2170 */       HashSet<Component> hashSet = new HashSet();
/* 2171 */       if (param1JSplitPane.isAncestorOf(component)) {
/*      */         do {
/* 2173 */           hashSet.add(component);
/* 2174 */           container = component.getFocusCycleRootAncestor();
/* 2175 */           focusTraversalPolicy = container.getFocusTraversalPolicy();
/*      */ 
/*      */           
/* 2178 */           component = (param1Int > 0) ? focusTraversalPolicy.getComponentAfter(container, component) : focusTraversalPolicy.getComponentBefore(container, component);
/* 2179 */         } while (param1JSplitPane.isAncestorOf(component) && 
/* 2180 */           !hashSet.contains(component));
/*      */       }
/* 2182 */       if (component != null && !param1JSplitPane.isAncestorOf(component)) {
/* 2183 */         component.requestFocus();
/*      */       }
/*      */     }
/*      */     
/*      */     private void toggleFocus(JSplitPane param1JSplitPane) {
/* 2188 */       Component component1 = param1JSplitPane.getLeftComponent();
/* 2189 */       Component component2 = param1JSplitPane.getRightComponent();
/*      */ 
/*      */       
/* 2192 */       KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 2193 */       Component component3 = keyboardFocusManager.getFocusOwner();
/* 2194 */       Component component4 = getNextSide(param1JSplitPane, component3);
/* 2195 */       if (component4 != null) {
/*      */ 
/*      */         
/* 2198 */         if (component3 != null && ((
/* 2199 */           SwingUtilities.isDescendingFrom(component3, component1) && 
/* 2200 */           SwingUtilities.isDescendingFrom(component4, component1)) || (
/* 2201 */           SwingUtilities.isDescendingFrom(component3, component2) && 
/* 2202 */           SwingUtilities.isDescendingFrom(component4, component2)))) {
/*      */           return;
/*      */         }
/* 2205 */         SwingUtilities2.compositeRequestFocus(component4);
/*      */       } 
/*      */     }
/*      */     
/*      */     private Component getNextSide(JSplitPane param1JSplitPane, Component param1Component) {
/* 2210 */       Component component3, component1 = param1JSplitPane.getLeftComponent();
/* 2211 */       Component component2 = param1JSplitPane.getRightComponent();
/*      */       
/* 2213 */       if (param1Component != null && SwingUtilities.isDescendingFrom(param1Component, component1) && component2 != null) {
/*      */         
/* 2215 */         component3 = getFirstAvailableComponent(component2);
/* 2216 */         if (component3 != null) {
/* 2217 */           return component3;
/*      */         }
/*      */       } 
/* 2220 */       JSplitPane jSplitPane = (JSplitPane)SwingUtilities.getAncestorOfClass(JSplitPane.class, param1JSplitPane);
/* 2221 */       if (jSplitPane != null) {
/*      */         
/* 2223 */         component3 = getNextSide(jSplitPane, param1Component);
/*      */       } else {
/* 2225 */         component3 = getFirstAvailableComponent(component1);
/* 2226 */         if (component3 == null) {
/* 2227 */           component3 = getFirstAvailableComponent(component2);
/*      */         }
/*      */       } 
/* 2230 */       return component3;
/*      */     }
/*      */     
/*      */     private Component getFirstAvailableComponent(Component param1Component) {
/* 2234 */       if (param1Component != null && param1Component instanceof JSplitPane) {
/* 2235 */         JSplitPane jSplitPane = (JSplitPane)param1Component;
/* 2236 */         Component component = getFirstAvailableComponent(jSplitPane.getLeftComponent());
/* 2237 */         if (component != null) {
/* 2238 */           param1Component = component;
/*      */         } else {
/* 2240 */           param1Component = getFirstAvailableComponent(jSplitPane.getRightComponent());
/*      */         } 
/*      */       } 
/* 2243 */       return param1Component;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicSplitPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */