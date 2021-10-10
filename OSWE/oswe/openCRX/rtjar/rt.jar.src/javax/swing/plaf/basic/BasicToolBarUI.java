/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dialog;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Frame;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JRootPane;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIDefaults;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.CompoundBorder;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.ToolBarUI;
/*      */ import sun.swing.DefaultLookup;
/*      */ import sun.swing.UIAction;
/*      */ 
/*      */ public class BasicToolBarUI
/*      */   extends ToolBarUI implements SwingConstants {
/*      */   protected JToolBar toolBar;
/*      */   private boolean floating;
/*      */   private int floatingX;
/*      */   private int floatingY;
/*      */   private JFrame floatingFrame;
/*      */   private RootPaneContainer floatingToolBar;
/*      */   protected DragWindow dragWindow;
/*      */   private Container dockingSource;
/*   62 */   private int dockingSensitivity = 0;
/*   63 */   protected int focusedCompIndex = -1;
/*      */   
/*   65 */   protected Color dockingColor = null;
/*   66 */   protected Color floatingColor = null;
/*   67 */   protected Color dockingBorderColor = null;
/*   68 */   protected Color floatingBorderColor = null;
/*      */   
/*      */   protected MouseInputListener dockingListener;
/*      */   
/*      */   protected PropertyChangeListener propertyListener;
/*      */   
/*      */   protected ContainerListener toolBarContListener;
/*      */   protected FocusListener toolBarFocusListener;
/*      */   private Handler handler;
/*   77 */   protected String constraintBeforeFloating = "North";
/*      */ 
/*      */   
/*   80 */   private static String IS_ROLLOVER = "JToolBar.isRollover";
/*      */   
/*      */   private static Border rolloverBorder;
/*      */   private static Border nonRolloverBorder;
/*      */   private static Border nonRolloverToggleBorder;
/*      */   private boolean rolloverBorders = false;
/*   86 */   private HashMap<AbstractButton, Border> borderTable = new HashMap<>();
/*   87 */   private Hashtable<AbstractButton, Boolean> rolloverTable = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke downKey;
/*      */ 
/*      */ 
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
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected KeyStroke rightKey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  132 */   private static String FOCUSED_COMP_INDEX = "JToolBar.focusedCompIndex";
/*      */ 
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  136 */     return new BasicToolBarUI();
/*      */   }
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  141 */     this.toolBar = (JToolBar)paramJComponent;
/*      */ 
/*      */     
/*  144 */     installDefaults();
/*  145 */     installComponents();
/*  146 */     installListeners();
/*  147 */     installKeyboardActions();
/*      */ 
/*      */     
/*  150 */     this.dockingSensitivity = 0;
/*  151 */     this.floating = false;
/*  152 */     this.floatingX = this.floatingY = 0;
/*  153 */     this.floatingToolBar = null;
/*      */     
/*  155 */     setOrientation(this.toolBar.getOrientation());
/*  156 */     LookAndFeel.installProperty(paramJComponent, "opaque", Boolean.TRUE);
/*      */     
/*  158 */     if (paramJComponent.getClientProperty(FOCUSED_COMP_INDEX) != null)
/*      */     {
/*  160 */       this.focusedCompIndex = ((Integer)paramJComponent.getClientProperty(FOCUSED_COMP_INDEX)).intValue();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  168 */     uninstallDefaults();
/*  169 */     uninstallComponents();
/*  170 */     uninstallListeners();
/*  171 */     uninstallKeyboardActions();
/*      */ 
/*      */     
/*  174 */     if (isFloating()) {
/*  175 */       setFloating(false, null);
/*      */     }
/*  177 */     this.floatingToolBar = null;
/*  178 */     this.dragWindow = null;
/*  179 */     this.dockingSource = null;
/*      */     
/*  181 */     paramJComponent.putClientProperty(FOCUSED_COMP_INDEX, Integer.valueOf(this.focusedCompIndex));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installDefaults() {
/*  186 */     LookAndFeel.installBorder(this.toolBar, "ToolBar.border");
/*  187 */     LookAndFeel.installColorsAndFont(this.toolBar, "ToolBar.background", "ToolBar.foreground", "ToolBar.font");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  192 */     if (this.dockingColor == null || this.dockingColor instanceof javax.swing.plaf.UIResource)
/*  193 */       this.dockingColor = UIManager.getColor("ToolBar.dockingBackground"); 
/*  194 */     if (this.floatingColor == null || this.floatingColor instanceof javax.swing.plaf.UIResource)
/*  195 */       this.floatingColor = UIManager.getColor("ToolBar.floatingBackground"); 
/*  196 */     if (this.dockingBorderColor == null || this.dockingBorderColor instanceof javax.swing.plaf.UIResource)
/*      */     {
/*  198 */       this.dockingBorderColor = UIManager.getColor("ToolBar.dockingForeground"); } 
/*  199 */     if (this.floatingBorderColor == null || this.floatingBorderColor instanceof javax.swing.plaf.UIResource)
/*      */     {
/*  201 */       this.floatingBorderColor = UIManager.getColor("ToolBar.floatingForeground");
/*      */     }
/*      */     
/*  204 */     Object object = this.toolBar.getClientProperty(IS_ROLLOVER);
/*  205 */     if (object == null) {
/*  206 */       object = UIManager.get("ToolBar.isRollover");
/*      */     }
/*  208 */     if (object != null) {
/*  209 */       this.rolloverBorders = ((Boolean)object).booleanValue();
/*      */     }
/*      */     
/*  212 */     if (rolloverBorder == null) {
/*  213 */       rolloverBorder = createRolloverBorder();
/*      */     }
/*  215 */     if (nonRolloverBorder == null) {
/*  216 */       nonRolloverBorder = createNonRolloverBorder();
/*      */     }
/*  218 */     if (nonRolloverToggleBorder == null) {
/*  219 */       nonRolloverToggleBorder = createNonRolloverToggleBorder();
/*      */     }
/*      */ 
/*      */     
/*  223 */     setRolloverBorders(isRolloverBorders());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallDefaults() {
/*  228 */     LookAndFeel.uninstallBorder(this.toolBar);
/*  229 */     this.dockingColor = null;
/*  230 */     this.floatingColor = null;
/*  231 */     this.dockingBorderColor = null;
/*  232 */     this.floatingBorderColor = null;
/*      */     
/*  234 */     installNormalBorders(this.toolBar);
/*      */     
/*  236 */     rolloverBorder = null;
/*  237 */     nonRolloverBorder = null;
/*  238 */     nonRolloverToggleBorder = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installComponents() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallComponents() {}
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  251 */     this.dockingListener = createDockingListener();
/*      */     
/*  253 */     if (this.dockingListener != null) {
/*      */       
/*  255 */       this.toolBar.addMouseMotionListener(this.dockingListener);
/*  256 */       this.toolBar.addMouseListener(this.dockingListener);
/*      */     } 
/*      */     
/*  259 */     this.propertyListener = createPropertyListener();
/*  260 */     if (this.propertyListener != null) {
/*  261 */       this.toolBar.addPropertyChangeListener(this.propertyListener);
/*      */     }
/*      */     
/*  264 */     this.toolBarContListener = createToolBarContListener();
/*  265 */     if (this.toolBarContListener != null) {
/*  266 */       this.toolBar.addContainerListener(this.toolBarContListener);
/*      */     }
/*      */     
/*  269 */     this.toolBarFocusListener = createToolBarFocusListener();
/*      */     
/*  271 */     if (this.toolBarFocusListener != null) {
/*      */ 
/*      */       
/*  274 */       Component[] arrayOfComponent = this.toolBar.getComponents();
/*      */       
/*  276 */       for (Component component : arrayOfComponent) {
/*  277 */         component.addFocusListener(this.toolBarFocusListener);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {
/*  284 */     if (this.dockingListener != null) {
/*      */       
/*  286 */       this.toolBar.removeMouseMotionListener(this.dockingListener);
/*  287 */       this.toolBar.removeMouseListener(this.dockingListener);
/*      */       
/*  289 */       this.dockingListener = null;
/*      */     } 
/*      */     
/*  292 */     if (this.propertyListener != null) {
/*      */       
/*  294 */       this.toolBar.removePropertyChangeListener(this.propertyListener);
/*  295 */       this.propertyListener = null;
/*      */     } 
/*      */     
/*  298 */     if (this.toolBarContListener != null) {
/*      */       
/*  300 */       this.toolBar.removeContainerListener(this.toolBarContListener);
/*  301 */       this.toolBarContListener = null;
/*      */     } 
/*      */     
/*  304 */     if (this.toolBarFocusListener != null) {
/*      */ 
/*      */       
/*  307 */       Component[] arrayOfComponent = this.toolBar.getComponents();
/*      */       
/*  309 */       for (Component component : arrayOfComponent) {
/*  310 */         component.removeFocusListener(this.toolBarFocusListener);
/*      */       }
/*      */       
/*  313 */       this.toolBarFocusListener = null;
/*      */     } 
/*  315 */     this.handler = null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void installKeyboardActions() {
/*  320 */     InputMap inputMap = getInputMap(1);
/*      */ 
/*      */     
/*  323 */     SwingUtilities.replaceUIInputMap(this.toolBar, 1, inputMap);
/*      */ 
/*      */ 
/*      */     
/*  327 */     LazyActionMap.installLazyActionMap(this.toolBar, BasicToolBarUI.class, "ToolBar.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  332 */     if (paramInt == 1) {
/*  333 */       return (InputMap)DefaultLookup.get(this.toolBar, this, "ToolBar.ancestorInputMap");
/*      */     }
/*      */     
/*  336 */     return null;
/*      */   }
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  340 */     paramLazyActionMap.put(new Actions("navigateRight"));
/*  341 */     paramLazyActionMap.put(new Actions("navigateLeft"));
/*  342 */     paramLazyActionMap.put(new Actions("navigateUp"));
/*  343 */     paramLazyActionMap.put(new Actions("navigateDown"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  348 */     SwingUtilities.replaceUIActionMap(this.toolBar, null);
/*  349 */     SwingUtilities.replaceUIInputMap(this.toolBar, 1, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void navigateFocusedComp(int paramInt) {
/*  356 */     int j, i = this.toolBar.getComponentCount();
/*      */ 
/*      */     
/*  359 */     switch (paramInt) {
/*      */ 
/*      */       
/*      */       case 3:
/*      */       case 5:
/*  364 */         if (this.focusedCompIndex < 0 || this.focusedCompIndex >= i)
/*      */           break; 
/*  366 */         j = this.focusedCompIndex + 1;
/*      */         
/*  368 */         while (j != this.focusedCompIndex) {
/*      */           
/*  370 */           if (j >= i) j = 0; 
/*  371 */           Component component = this.toolBar.getComponentAtIndex(j++);
/*      */           
/*  373 */           if (component != null && component.isFocusTraversable() && component.isEnabled()) {
/*      */             
/*  375 */             component.requestFocus();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 7:
/*  385 */         if (this.focusedCompIndex < 0 || this.focusedCompIndex >= i)
/*      */           break; 
/*  387 */         j = this.focusedCompIndex - 1;
/*      */         
/*  389 */         while (j != this.focusedCompIndex) {
/*      */           
/*  391 */           if (j < 0) j = i - 1; 
/*  392 */           Component component = this.toolBar.getComponentAtIndex(j--);
/*      */           
/*  394 */           if (component != null && component.isFocusTraversable() && component.isEnabled()) {
/*      */             
/*  396 */             component.requestFocus();
/*      */             break;
/*      */           } 
/*      */         } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Border createRolloverBorder() {
/*  418 */     Object object = UIManager.get("ToolBar.rolloverBorder");
/*  419 */     if (object != null) {
/*  420 */       return (Border)object;
/*      */     }
/*  422 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  423 */     return new CompoundBorder(new BasicBorders.RolloverButtonBorder(uIDefaults
/*  424 */           .getColor("controlShadow"), uIDefaults
/*  425 */           .getColor("controlDkShadow"), uIDefaults
/*  426 */           .getColor("controlHighlight"), uIDefaults
/*  427 */           .getColor("controlLtHighlight")), new BasicBorders.RolloverMarginBorder());
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
/*      */   protected Border createNonRolloverBorder() {
/*  441 */     Object object = UIManager.get("ToolBar.nonrolloverBorder");
/*  442 */     if (object != null) {
/*  443 */       return (Border)object;
/*      */     }
/*  445 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  446 */     return new CompoundBorder(new BasicBorders.ButtonBorder(uIDefaults
/*  447 */           .getColor("Button.shadow"), uIDefaults
/*  448 */           .getColor("Button.darkShadow"), uIDefaults
/*  449 */           .getColor("Button.light"), uIDefaults
/*  450 */           .getColor("Button.highlight")), new BasicBorders.RolloverMarginBorder());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Border createNonRolloverToggleBorder() {
/*  458 */     UIDefaults uIDefaults = UIManager.getLookAndFeelDefaults();
/*  459 */     return new CompoundBorder(new BasicBorders.RadioButtonBorder(uIDefaults
/*  460 */           .getColor("ToggleButton.shadow"), uIDefaults
/*  461 */           .getColor("ToggleButton.darkShadow"), uIDefaults
/*  462 */           .getColor("ToggleButton.light"), uIDefaults
/*  463 */           .getColor("ToggleButton.highlight")), new BasicBorders.RolloverMarginBorder());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JFrame createFloatingFrame(JToolBar paramJToolBar) {
/*  472 */     Window window = SwingUtilities.getWindowAncestor(paramJToolBar);
/*      */     
/*  474 */     JFrame jFrame = new JFrame(paramJToolBar.getName(), (window != null) ? window.getGraphicsConfiguration() : null)
/*      */       {
/*      */         protected JRootPane createRootPane()
/*      */         {
/*  478 */           JRootPane jRootPane = new JRootPane() {
/*      */               private boolean packing = false;
/*      */               
/*      */               public void validate() {
/*  482 */                 super.validate();
/*  483 */                 if (!this.packing) {
/*  484 */                   this.packing = true;
/*  485 */                   BasicToolBarUI.null.this.pack();
/*  486 */                   this.packing = false;
/*      */                 } 
/*      */               }
/*      */             };
/*  490 */           jRootPane.setOpaque(true);
/*  491 */           return jRootPane;
/*      */         }
/*      */       };
/*  494 */     jFrame.getRootPane().setName("ToolBar.FloatingFrame");
/*  495 */     jFrame.setResizable(false);
/*  496 */     WindowListener windowListener = createFrameListener();
/*  497 */     jFrame.addWindowListener(windowListener);
/*  498 */     return jFrame;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected RootPaneContainer createFloatingWindow(JToolBar paramJToolBar) {
/*      */     ToolBarDialog toolBarDialog;
/*      */     class ToolBarDialog
/*      */       extends JDialog
/*      */     {
/*      */       public ToolBarDialog(Frame param1Frame, String param1String, boolean param1Boolean) {
/*  510 */         super(param1Frame, param1String, param1Boolean);
/*      */       }
/*      */       
/*      */       public ToolBarDialog(Dialog param1Dialog, String param1String, boolean param1Boolean) {
/*  514 */         super(param1Dialog, param1String, param1Boolean);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected JRootPane createRootPane() {
/*  520 */         JRootPane jRootPane = new JRootPane() {
/*      */             private boolean packing = false;
/*      */             
/*      */             public void validate() {
/*  524 */               super.validate();
/*  525 */               if (!this.packing) {
/*  526 */                 this.packing = true;
/*  527 */                 BasicToolBarUI.ToolBarDialog.this.pack();
/*  528 */                 this.packing = false;
/*      */               } 
/*      */             }
/*      */           };
/*  532 */         jRootPane.setOpaque(true);
/*  533 */         return jRootPane;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*  538 */     Window window = SwingUtilities.getWindowAncestor(paramJToolBar);
/*  539 */     if (window instanceof Frame) {
/*  540 */       toolBarDialog = new ToolBarDialog((Frame)window, paramJToolBar.getName(), false);
/*  541 */     } else if (window instanceof Dialog) {
/*  542 */       toolBarDialog = new ToolBarDialog(this, (Dialog)window, paramJToolBar.getName(), false);
/*      */     } else {
/*  544 */       toolBarDialog = new ToolBarDialog(this, (Frame)null, paramJToolBar.getName(), false);
/*      */     } 
/*      */     
/*  547 */     toolBarDialog.getRootPane().setName("ToolBar.FloatingWindow");
/*  548 */     toolBarDialog.setTitle(paramJToolBar.getName());
/*  549 */     toolBarDialog.setResizable(false);
/*  550 */     WindowListener windowListener = createFrameListener();
/*  551 */     toolBarDialog.addWindowListener(windowListener);
/*  552 */     return toolBarDialog;
/*      */   }
/*      */   
/*      */   protected DragWindow createDragWindow(JToolBar paramJToolBar) {
/*  556 */     Window window = null;
/*  557 */     if (this.toolBar != null) {
/*      */       Container container;
/*  559 */       for (container = this.toolBar.getParent(); container != null && !(container instanceof Window);)
/*  560 */         container = container.getParent(); 
/*  561 */       if (container != null && container instanceof Window)
/*  562 */         window = (Window)container; 
/*      */     } 
/*  564 */     if (this.floatingToolBar == null) {
/*  565 */       this.floatingToolBar = createFloatingWindow(this.toolBar);
/*      */     }
/*  567 */     if (this.floatingToolBar instanceof Window) window = (Window)this.floatingToolBar; 
/*  568 */     return new DragWindow(window);
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
/*      */   public boolean isRolloverBorders() {
/*  581 */     return this.rolloverBorders;
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
/*      */   public void setRolloverBorders(boolean paramBoolean) {
/*  594 */     this.rolloverBorders = paramBoolean;
/*      */     
/*  596 */     if (this.rolloverBorders) {
/*  597 */       installRolloverBorders(this.toolBar);
/*      */     } else {
/*  599 */       installNonRolloverBorders(this.toolBar);
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
/*      */   protected void installRolloverBorders(JComponent paramJComponent) {
/*  615 */     Component[] arrayOfComponent = paramJComponent.getComponents();
/*      */     
/*  617 */     for (Component component : arrayOfComponent) {
/*  618 */       if (component instanceof JComponent) {
/*  619 */         ((JComponent)component).updateUI();
/*  620 */         setBorderToRollover(component);
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
/*      */   protected void installNonRolloverBorders(JComponent paramJComponent) {
/*  639 */     Component[] arrayOfComponent = paramJComponent.getComponents();
/*      */     
/*  641 */     for (Component component : arrayOfComponent) {
/*  642 */       if (component instanceof JComponent) {
/*  643 */         ((JComponent)component).updateUI();
/*  644 */         setBorderToNonRollover(component);
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
/*      */   protected void installNormalBorders(JComponent paramJComponent) {
/*  663 */     Component[] arrayOfComponent = paramJComponent.getComponents();
/*      */     
/*  665 */     for (Component component : arrayOfComponent) {
/*  666 */       setBorderToNormal(component);
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
/*      */   protected void setBorderToRollover(Component paramComponent) {
/*  679 */     if (paramComponent instanceof AbstractButton) {
/*  680 */       AbstractButton abstractButton = (AbstractButton)paramComponent;
/*      */       
/*  682 */       Border border = this.borderTable.get(abstractButton);
/*  683 */       if (border == null || border instanceof javax.swing.plaf.UIResource) {
/*  684 */         this.borderTable.put(abstractButton, abstractButton.getBorder());
/*      */       }
/*      */ 
/*      */       
/*  688 */       if (abstractButton.getBorder() instanceof javax.swing.plaf.UIResource) {
/*  689 */         abstractButton.setBorder(getRolloverBorder(abstractButton));
/*      */       }
/*      */       
/*  692 */       this.rolloverTable.put(abstractButton, abstractButton.isRolloverEnabled() ? Boolean.TRUE : Boolean.FALSE);
/*      */       
/*  694 */       abstractButton.setRolloverEnabled(true);
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
/*      */   protected Border getRolloverBorder(AbstractButton paramAbstractButton) {
/*  707 */     return rolloverBorder;
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
/*      */   protected void setBorderToNonRollover(Component paramComponent) {
/*  719 */     if (paramComponent instanceof AbstractButton) {
/*  720 */       AbstractButton abstractButton = (AbstractButton)paramComponent;
/*      */       
/*  722 */       Border border = this.borderTable.get(abstractButton);
/*  723 */       if (border == null || border instanceof javax.swing.plaf.UIResource) {
/*  724 */         this.borderTable.put(abstractButton, abstractButton.getBorder());
/*      */       }
/*      */ 
/*      */       
/*  728 */       if (abstractButton.getBorder() instanceof javax.swing.plaf.UIResource) {
/*  729 */         abstractButton.setBorder(getNonRolloverBorder(abstractButton));
/*      */       }
/*  731 */       this.rolloverTable.put(abstractButton, abstractButton.isRolloverEnabled() ? Boolean.TRUE : Boolean.FALSE);
/*      */       
/*  733 */       abstractButton.setRolloverEnabled(false);
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
/*      */   protected Border getNonRolloverBorder(AbstractButton paramAbstractButton) {
/*  746 */     if (paramAbstractButton instanceof javax.swing.JToggleButton) {
/*  747 */       return nonRolloverToggleBorder;
/*      */     }
/*  749 */     return nonRolloverBorder;
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
/*      */   protected void setBorderToNormal(Component paramComponent) {
/*  763 */     if (paramComponent instanceof AbstractButton) {
/*  764 */       AbstractButton abstractButton = (AbstractButton)paramComponent;
/*      */       
/*  766 */       Border border = this.borderTable.remove(abstractButton);
/*  767 */       abstractButton.setBorder(border);
/*      */       
/*  769 */       Boolean bool = this.rolloverTable.remove(abstractButton);
/*  770 */       if (bool != null) {
/*  771 */         abstractButton.setRolloverEnabled(bool.booleanValue());
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setFloatingLocation(int paramInt1, int paramInt2) {
/*  777 */     this.floatingX = paramInt1;
/*  778 */     this.floatingY = paramInt2;
/*      */   }
/*      */   
/*      */   public boolean isFloating() {
/*  782 */     return this.floating;
/*      */   }
/*      */   
/*      */   public void setFloating(boolean paramBoolean, Point paramPoint) {
/*  786 */     if (this.toolBar.isFloatable()) {
/*  787 */       boolean bool = false;
/*  788 */       Window window = SwingUtilities.getWindowAncestor(this.toolBar);
/*  789 */       if (window != null) {
/*  790 */         bool = window.isVisible();
/*      */       }
/*  792 */       if (this.dragWindow != null)
/*  793 */         this.dragWindow.setVisible(false); 
/*  794 */       this.floating = paramBoolean;
/*  795 */       if (this.floatingToolBar == null) {
/*  796 */         this.floatingToolBar = createFloatingWindow(this.toolBar);
/*      */       }
/*  798 */       if (paramBoolean == true) {
/*      */         
/*  800 */         if (this.dockingSource == null) {
/*      */           
/*  802 */           this.dockingSource = this.toolBar.getParent();
/*  803 */           this.dockingSource.remove(this.toolBar);
/*      */         } 
/*  805 */         this.constraintBeforeFloating = calculateConstraint();
/*  806 */         if (this.propertyListener != null)
/*  807 */           UIManager.addPropertyChangeListener(this.propertyListener); 
/*  808 */         this.floatingToolBar.getContentPane().add(this.toolBar, "Center");
/*  809 */         if (this.floatingToolBar instanceof Window) {
/*  810 */           ((Window)this.floatingToolBar).pack();
/*  811 */           ((Window)this.floatingToolBar).setLocation(this.floatingX, this.floatingY);
/*  812 */           if (bool) {
/*  813 */             ((Window)this.floatingToolBar).show();
/*      */           } else {
/*  815 */             window.addWindowListener(new WindowAdapter() {
/*      */                   public void windowOpened(WindowEvent param1WindowEvent) {
/*  817 */                     ((Window)BasicToolBarUI.this.floatingToolBar).show();
/*      */                   }
/*      */                 });
/*      */           } 
/*      */         } 
/*      */       } else {
/*  823 */         if (this.floatingToolBar == null)
/*  824 */           this.floatingToolBar = createFloatingWindow(this.toolBar); 
/*  825 */         if (this.floatingToolBar instanceof Window) ((Window)this.floatingToolBar).setVisible(false); 
/*  826 */         this.floatingToolBar.getContentPane().remove(this.toolBar);
/*  827 */         String str = getDockingConstraint(this.dockingSource, paramPoint);
/*      */         
/*  829 */         if (str == null) {
/*  830 */           str = "North";
/*      */         }
/*  832 */         int i = mapConstraintToOrientation(str);
/*  833 */         setOrientation(i);
/*  834 */         if (this.dockingSource == null)
/*  835 */           this.dockingSource = this.toolBar.getParent(); 
/*  836 */         if (this.propertyListener != null)
/*  837 */           UIManager.removePropertyChangeListener(this.propertyListener); 
/*  838 */         this.dockingSource.add(str, this.toolBar);
/*      */       } 
/*  840 */       this.dockingSource.invalidate();
/*  841 */       Container container = this.dockingSource.getParent();
/*  842 */       if (container != null)
/*  843 */         container.validate(); 
/*  844 */       this.dockingSource.repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private int mapConstraintToOrientation(String paramString) {
/*  850 */     int i = this.toolBar.getOrientation();
/*      */     
/*  852 */     if (paramString != null)
/*      */     {
/*  854 */       if (paramString.equals("East") || paramString.equals("West")) {
/*  855 */         i = 1;
/*  856 */       } else if (paramString.equals("North") || paramString.equals("South")) {
/*  857 */         i = 0;
/*      */       } 
/*      */     }
/*  860 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOrientation(int paramInt) {
/*  865 */     this.toolBar.setOrientation(paramInt);
/*      */     
/*  867 */     if (this.dragWindow != null) {
/*  868 */       this.dragWindow.setOrientation(paramInt);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getDockingColor() {
/*  875 */     return this.dockingColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDockingColor(Color paramColor) {
/*  882 */     this.dockingColor = paramColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getFloatingColor() {
/*  889 */     return this.floatingColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFloatingColor(Color paramColor) {
/*  896 */     this.floatingColor = paramColor;
/*      */   }
/*      */   
/*      */   private boolean isBlocked(Component paramComponent, Object paramObject) {
/*  900 */     if (paramComponent instanceof Container) {
/*  901 */       Container container = (Container)paramComponent;
/*  902 */       LayoutManager layoutManager = container.getLayout();
/*  903 */       if (layoutManager instanceof BorderLayout) {
/*  904 */         BorderLayout borderLayout = (BorderLayout)layoutManager;
/*  905 */         Component component = borderLayout.getLayoutComponent(container, paramObject);
/*  906 */         return (component != null && component != this.toolBar);
/*      */       } 
/*      */     } 
/*  909 */     return false;
/*      */   }
/*      */   
/*      */   public boolean canDock(Component paramComponent, Point paramPoint) {
/*  913 */     return (paramPoint != null && getDockingConstraint(paramComponent, paramPoint) != null);
/*      */   }
/*      */   
/*      */   private String calculateConstraint() {
/*  917 */     String str = null;
/*  918 */     LayoutManager layoutManager = this.dockingSource.getLayout();
/*  919 */     if (layoutManager instanceof BorderLayout) {
/*  920 */       str = (String)((BorderLayout)layoutManager).getConstraints(this.toolBar);
/*      */     }
/*  922 */     return (str != null) ? str : this.constraintBeforeFloating;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDockingConstraint(Component paramComponent, Point paramPoint) {
/*  928 */     if (paramPoint == null) return this.constraintBeforeFloating; 
/*  929 */     if (paramComponent.contains(paramPoint)) {
/*  930 */       this
/*      */         
/*  932 */         .dockingSensitivity = (this.toolBar.getOrientation() == 0) ? (this.toolBar.getSize()).height : (this.toolBar.getSize()).width;
/*      */       
/*  934 */       if (paramPoint.y < this.dockingSensitivity && !isBlocked(paramComponent, "North")) {
/*  935 */         return "North";
/*      */       }
/*      */       
/*  938 */       if (paramPoint.x >= paramComponent.getWidth() - this.dockingSensitivity && !isBlocked(paramComponent, "East")) {
/*  939 */         return "East";
/*      */       }
/*      */       
/*  942 */       if (paramPoint.x < this.dockingSensitivity && !isBlocked(paramComponent, "West")) {
/*  943 */         return "West";
/*      */       }
/*  945 */       if (paramPoint.y >= paramComponent.getHeight() - this.dockingSensitivity && !isBlocked(paramComponent, "South")) {
/*  946 */         return "South";
/*      */       }
/*      */     } 
/*  949 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dragTo(Point paramPoint1, Point paramPoint2) {
/*  954 */     if (this.toolBar.isFloatable()) {
/*      */       
/*      */       try {
/*      */         
/*  958 */         if (this.dragWindow == null)
/*  959 */           this.dragWindow = createDragWindow(this.toolBar); 
/*  960 */         Point point1 = this.dragWindow.getOffset();
/*  961 */         if (point1 == null) {
/*  962 */           Dimension dimension = this.toolBar.getPreferredSize();
/*  963 */           point1 = new Point(dimension.width / 2, dimension.height / 2);
/*  964 */           this.dragWindow.setOffset(point1);
/*      */         } 
/*  966 */         Point point2 = new Point(paramPoint2.x + paramPoint1.x, paramPoint2.y + paramPoint1.y);
/*      */         
/*  968 */         Point point3 = new Point(point2.x - point1.x, point2.y - point1.y);
/*      */         
/*  970 */         if (this.dockingSource == null)
/*  971 */           this.dockingSource = this.toolBar.getParent(); 
/*  972 */         this.constraintBeforeFloating = calculateConstraint();
/*  973 */         Point point4 = this.dockingSource.getLocationOnScreen();
/*  974 */         Point point5 = new Point(point2.x - point4.x, point2.y - point4.y);
/*      */         
/*  976 */         if (canDock(this.dockingSource, point5)) {
/*  977 */           this.dragWindow.setBackground(getDockingColor());
/*  978 */           String str = getDockingConstraint(this.dockingSource, point5);
/*      */           
/*  980 */           int i = mapConstraintToOrientation(str);
/*  981 */           this.dragWindow.setOrientation(i);
/*  982 */           this.dragWindow.setBorderColor(this.dockingBorderColor);
/*      */         } else {
/*  984 */           this.dragWindow.setBackground(getFloatingColor());
/*  985 */           this.dragWindow.setBorderColor(this.floatingBorderColor);
/*  986 */           this.dragWindow.setOrientation(this.toolBar.getOrientation());
/*      */         } 
/*      */         
/*  989 */         this.dragWindow.setLocation(point3.x, point3.y);
/*  990 */         if (!this.dragWindow.isVisible()) {
/*  991 */           Dimension dimension = this.toolBar.getPreferredSize();
/*  992 */           this.dragWindow.setSize(dimension.width, dimension.height);
/*  993 */           this.dragWindow.show();
/*      */         }
/*      */       
/*  996 */       } catch (IllegalComponentStateException illegalComponentStateException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void floatAt(Point paramPoint1, Point paramPoint2) {
/* 1004 */     if (this.toolBar.isFloatable()) {
/*      */       
/*      */       try {
/*      */         
/* 1008 */         Point point1 = this.dragWindow.getOffset();
/* 1009 */         if (point1 == null) {
/* 1010 */           point1 = paramPoint1;
/* 1011 */           this.dragWindow.setOffset(point1);
/*      */         } 
/* 1013 */         Point point2 = new Point(paramPoint2.x + paramPoint1.x, paramPoint2.y + paramPoint1.y);
/*      */         
/* 1015 */         setFloatingLocation(point2.x - point1.x, point2.y - point1.y);
/*      */         
/* 1017 */         if (this.dockingSource != null) {
/* 1018 */           Point point3 = this.dockingSource.getLocationOnScreen();
/* 1019 */           Point point4 = new Point(point2.x - point3.x, point2.y - point3.y);
/*      */           
/* 1021 */           if (canDock(this.dockingSource, point4)) {
/* 1022 */             setFloating(false, point4);
/*      */           } else {
/* 1024 */             setFloating(true, null);
/*      */           } 
/*      */         } else {
/* 1027 */           setFloating(true, null);
/*      */         } 
/* 1029 */         this.dragWindow.setOffset((Point)null);
/*      */       }
/* 1031 */       catch (IllegalComponentStateException illegalComponentStateException) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/* 1038 */     if (this.handler == null) {
/* 1039 */       this.handler = new Handler();
/*      */     }
/* 1041 */     return this.handler;
/*      */   }
/*      */ 
/*      */   
/*      */   protected ContainerListener createToolBarContListener() {
/* 1046 */     return getHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   protected FocusListener createToolBarFocusListener() {
/* 1051 */     return getHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   protected PropertyChangeListener createPropertyListener() {
/* 1056 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected MouseInputListener createDockingListener() {
/* 1060 */     (getHandler()).tb = this.toolBar;
/* 1061 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected WindowListener createFrameListener() {
/* 1065 */     return new FrameListener();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintDragWindow(Graphics paramGraphics) {
/* 1076 */     paramGraphics.setColor(this.dragWindow.getBackground());
/* 1077 */     int i = this.dragWindow.getWidth();
/* 1078 */     int j = this.dragWindow.getHeight();
/* 1079 */     paramGraphics.fillRect(0, 0, i, j);
/* 1080 */     paramGraphics.setColor(this.dragWindow.getBorderColor());
/* 1081 */     paramGraphics.drawRect(0, 0, i - 1, j - 1);
/*      */   }
/*      */   
/*      */   private static class Actions
/*      */     extends UIAction {
/*      */     private static final String NAVIGATE_RIGHT = "navigateRight";
/*      */     private static final String NAVIGATE_LEFT = "navigateLeft";
/*      */     private static final String NAVIGATE_UP = "navigateUp";
/*      */     private static final String NAVIGATE_DOWN = "navigateDown";
/*      */     
/*      */     public Actions(String param1String) {
/* 1092 */       super(param1String);
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 1096 */       String str = getName();
/* 1097 */       JToolBar jToolBar = (JToolBar)param1ActionEvent.getSource();
/* 1098 */       BasicToolBarUI basicToolBarUI = (BasicToolBarUI)BasicLookAndFeel.getUIOfType(jToolBar
/* 1099 */           .getUI(), BasicToolBarUI.class);
/*      */       
/* 1101 */       if ("navigateRight" == str) {
/* 1102 */         basicToolBarUI.navigateFocusedComp(3);
/* 1103 */       } else if ("navigateLeft" == str) {
/* 1104 */         basicToolBarUI.navigateFocusedComp(7);
/* 1105 */       } else if ("navigateUp" == str) {
/* 1106 */         basicToolBarUI.navigateFocusedComp(1);
/* 1107 */       } else if ("navigateDown" == str) {
/* 1108 */         basicToolBarUI.navigateFocusedComp(5);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements ContainerListener, FocusListener, MouseInputListener, PropertyChangeListener
/*      */   {
/*      */     JToolBar tb;
/*      */ 
/*      */     
/*      */     public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 1121 */       Component component = param1ContainerEvent.getChild();
/*      */       
/* 1123 */       if (BasicToolBarUI.this.toolBarFocusListener != null) {
/* 1124 */         component.addFocusListener(BasicToolBarUI.this.toolBarFocusListener);
/*      */       }
/*      */       
/* 1127 */       if (BasicToolBarUI.this.isRolloverBorders()) {
/* 1128 */         BasicToolBarUI.this.setBorderToRollover(component);
/*      */       } else {
/* 1130 */         BasicToolBarUI.this.setBorderToNonRollover(component);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 1135 */       Component component = param1ContainerEvent.getChild();
/*      */       
/* 1137 */       if (BasicToolBarUI.this.toolBarFocusListener != null) {
/* 1138 */         component.removeFocusListener(BasicToolBarUI.this.toolBarFocusListener);
/*      */       }
/*      */ 
/*      */       
/* 1142 */       BasicToolBarUI.this.setBorderToNormal(component);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 1150 */       Component component = param1FocusEvent.getComponent();
/* 1151 */       BasicToolBarUI.this.focusedCompIndex = BasicToolBarUI.this.toolBar.getComponentIndex(component);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {}
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isDragging = false;
/*      */     
/* 1162 */     Point origin = null;
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1165 */       if (!this.tb.isEnabled()) {
/*      */         return;
/*      */       }
/* 1168 */       this.isDragging = false;
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1172 */       if (!this.tb.isEnabled()) {
/*      */         return;
/*      */       }
/* 1175 */       if (this.isDragging) {
/* 1176 */         Point point = param1MouseEvent.getPoint();
/* 1177 */         if (this.origin == null)
/* 1178 */           this.origin = param1MouseEvent.getComponent().getLocationOnScreen(); 
/* 1179 */         BasicToolBarUI.this.floatAt(point, this.origin);
/*      */       } 
/* 1181 */       this.origin = null;
/* 1182 */       this.isDragging = false;
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 1186 */       if (!this.tb.isEnabled()) {
/*      */         return;
/*      */       }
/* 1189 */       this.isDragging = true;
/* 1190 */       Point point = param1MouseEvent.getPoint();
/* 1191 */       if (this.origin == null) {
/* 1192 */         this.origin = param1MouseEvent.getComponent().getLocationOnScreen();
/*      */       }
/* 1194 */       BasicToolBarUI.this.dragTo(point, this.origin);
/*      */     }
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     private Handler() {}
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1207 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1208 */       if (str == "lookAndFeel") {
/* 1209 */         BasicToolBarUI.this.toolBar.updateUI();
/* 1210 */       } else if (str == "orientation") {
/*      */ 
/*      */         
/* 1213 */         Component[] arrayOfComponent = BasicToolBarUI.this.toolBar.getComponents();
/* 1214 */         int i = ((Integer)param1PropertyChangeEvent.getNewValue()).intValue();
/*      */ 
/*      */         
/* 1217 */         for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 1218 */           if (arrayOfComponent[b] instanceof JToolBar.Separator) {
/* 1219 */             JToolBar.Separator separator = (JToolBar.Separator)arrayOfComponent[b];
/* 1220 */             if (i == 0) {
/* 1221 */               separator.setOrientation(1);
/*      */             } else {
/* 1223 */               separator.setOrientation(0);
/*      */             } 
/* 1225 */             Dimension dimension = separator.getSeparatorSize();
/* 1226 */             if (dimension != null && dimension.width != dimension.height) {
/*      */               
/* 1228 */               Dimension dimension1 = new Dimension(dimension.height, dimension.width);
/*      */               
/* 1230 */               separator.setSeparatorSize(dimension1);
/*      */             } 
/*      */           } 
/*      */         } 
/* 1234 */       } else if (str == BasicToolBarUI.IS_ROLLOVER) {
/* 1235 */         BasicToolBarUI.this.installNormalBorders(BasicToolBarUI.this.toolBar);
/* 1236 */         BasicToolBarUI.this.setRolloverBorders(((Boolean)param1PropertyChangeEvent.getNewValue()).booleanValue());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected class FrameListener extends WindowAdapter {
/*      */     public void windowClosing(WindowEvent param1WindowEvent) {
/* 1243 */       if (BasicToolBarUI.this.toolBar.isFloatable()) {
/* 1244 */         if (BasicToolBarUI.this.dragWindow != null)
/* 1245 */           BasicToolBarUI.this.dragWindow.setVisible(false); 
/* 1246 */         BasicToolBarUI.this.floating = false;
/* 1247 */         if (BasicToolBarUI.this.floatingToolBar == null)
/* 1248 */           BasicToolBarUI.this.floatingToolBar = BasicToolBarUI.this.createFloatingWindow(BasicToolBarUI.this.toolBar); 
/* 1249 */         if (BasicToolBarUI.this.floatingToolBar instanceof Window) ((Window)BasicToolBarUI.this.floatingToolBar).setVisible(false); 
/* 1250 */         BasicToolBarUI.this.floatingToolBar.getContentPane().remove(BasicToolBarUI.this.toolBar);
/* 1251 */         String str = BasicToolBarUI.this.constraintBeforeFloating;
/* 1252 */         if (BasicToolBarUI.this.toolBar.getOrientation() == 0) {
/* 1253 */           if (str == "West" || str == "East") {
/* 1254 */             str = "North";
/*      */           }
/*      */         }
/* 1257 */         else if (str == "North" || str == "South") {
/* 1258 */           str = "West";
/*      */         } 
/*      */         
/* 1261 */         if (BasicToolBarUI.this.dockingSource == null)
/* 1262 */           BasicToolBarUI.this.dockingSource = BasicToolBarUI.this.toolBar.getParent(); 
/* 1263 */         if (BasicToolBarUI.this.propertyListener != null)
/* 1264 */           UIManager.removePropertyChangeListener(BasicToolBarUI.this.propertyListener); 
/* 1265 */         BasicToolBarUI.this.dockingSource.add(BasicToolBarUI.this.toolBar, str);
/* 1266 */         BasicToolBarUI.this.dockingSource.invalidate();
/* 1267 */         Container container = BasicToolBarUI.this.dockingSource.getParent();
/* 1268 */         if (container != null)
/* 1269 */           container.validate(); 
/* 1270 */         BasicToolBarUI.this.dockingSource.repaint();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ToolBarContListener
/*      */     implements ContainerListener
/*      */   {
/*      */     public void componentAdded(ContainerEvent param1ContainerEvent) {
/* 1282 */       BasicToolBarUI.this.getHandler().componentAdded(param1ContainerEvent);
/*      */     }
/*      */     
/*      */     public void componentRemoved(ContainerEvent param1ContainerEvent) {
/* 1286 */       BasicToolBarUI.this.getHandler().componentRemoved(param1ContainerEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ToolBarFocusListener
/*      */     implements FocusListener
/*      */   {
/*      */     public void focusGained(FocusEvent param1FocusEvent) {
/* 1297 */       BasicToolBarUI.this.getHandler().focusGained(param1FocusEvent);
/*      */     }
/*      */     
/*      */     public void focusLost(FocusEvent param1FocusEvent) {
/* 1301 */       BasicToolBarUI.this.getHandler().focusLost(param1FocusEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected class PropertyListener
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1311 */       BasicToolBarUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class DockingListener
/*      */     implements MouseInputListener
/*      */   {
/*      */     protected JToolBar toolBar;
/*      */ 
/*      */     
/*      */     protected boolean isDragging = false;
/*      */ 
/*      */     
/* 1326 */     protected Point origin = null;
/*      */     
/*      */     public DockingListener(JToolBar param1JToolBar) {
/* 1329 */       this.toolBar = param1JToolBar;
/* 1330 */       (BasicToolBarUI.this.getHandler()).tb = param1JToolBar;
/*      */     }
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 1334 */       BasicToolBarUI.this.getHandler().mouseClicked(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1338 */       (BasicToolBarUI.this.getHandler()).tb = this.toolBar;
/* 1339 */       BasicToolBarUI.this.getHandler().mousePressed(param1MouseEvent);
/* 1340 */       this.isDragging = (BasicToolBarUI.this.getHandler()).isDragging;
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1344 */       (BasicToolBarUI.this.getHandler()).tb = this.toolBar;
/* 1345 */       (BasicToolBarUI.this.getHandler()).isDragging = this.isDragging;
/* 1346 */       (BasicToolBarUI.this.getHandler()).origin = this.origin;
/* 1347 */       BasicToolBarUI.this.getHandler().mouseReleased(param1MouseEvent);
/* 1348 */       this.isDragging = (BasicToolBarUI.this.getHandler()).isDragging;
/* 1349 */       this.origin = (BasicToolBarUI.this.getHandler()).origin;
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 1353 */       BasicToolBarUI.this.getHandler().mouseEntered(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 1357 */       BasicToolBarUI.this.getHandler().mouseExited(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 1361 */       (BasicToolBarUI.this.getHandler()).tb = this.toolBar;
/* 1362 */       (BasicToolBarUI.this.getHandler()).origin = this.origin;
/* 1363 */       BasicToolBarUI.this.getHandler().mouseDragged(param1MouseEvent);
/* 1364 */       this.isDragging = (BasicToolBarUI.this.getHandler()).isDragging;
/* 1365 */       this.origin = (BasicToolBarUI.this.getHandler()).origin;
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 1369 */       BasicToolBarUI.this.getHandler().mouseMoved(param1MouseEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   protected class DragWindow
/*      */     extends Window {
/* 1375 */     Color borderColor = Color.gray;
/* 1376 */     int orientation = BasicToolBarUI.this.toolBar.getOrientation();
/*      */     Point offset;
/*      */     
/*      */     DragWindow(Window param1Window) {
/* 1380 */       super(param1Window);
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
/*      */     public int getOrientation() {
/* 1392 */       return this.orientation;
/*      */     }
/*      */     
/*      */     public void setOrientation(int param1Int) {
/* 1396 */       if (isShowing()) {
/* 1397 */         if (param1Int == this.orientation)
/*      */           return; 
/* 1399 */         this.orientation = param1Int;
/* 1400 */         Dimension dimension = getSize();
/* 1401 */         setSize(new Dimension(dimension.height, dimension.width));
/* 1402 */         if (this.offset != null) {
/* 1403 */           if (BasicGraphicsUtils.isLeftToRight(BasicToolBarUI.this.toolBar)) {
/* 1404 */             setOffset(new Point(this.offset.y, this.offset.x));
/* 1405 */           } else if (param1Int == 0) {
/* 1406 */             setOffset(new Point(dimension.height - this.offset.y, this.offset.x));
/*      */           } else {
/* 1408 */             setOffset(new Point(this.offset.y, dimension.width - this.offset.x));
/*      */           } 
/*      */         }
/* 1411 */         repaint();
/*      */       } 
/*      */     }
/*      */     
/*      */     public Point getOffset() {
/* 1416 */       return this.offset;
/*      */     }
/*      */     
/*      */     public void setOffset(Point param1Point) {
/* 1420 */       this.offset = param1Point;
/*      */     }
/*      */     
/*      */     public void setBorderColor(Color param1Color) {
/* 1424 */       if (this.borderColor == param1Color)
/*      */         return; 
/* 1426 */       this.borderColor = param1Color;
/* 1427 */       repaint();
/*      */     }
/*      */     
/*      */     public Color getBorderColor() {
/* 1431 */       return this.borderColor;
/*      */     }
/*      */     
/*      */     public void paint(Graphics param1Graphics) {
/* 1435 */       BasicToolBarUI.this.paintDragWindow(param1Graphics);
/*      */       
/* 1437 */       super.paint(param1Graphics);
/*      */     }
/*      */     public Insets getInsets() {
/* 1440 */       return new Insets(1, 1, 1, 1);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicToolBarUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */