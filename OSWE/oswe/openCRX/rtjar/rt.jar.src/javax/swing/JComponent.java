/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.AWTKeyStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FocusTraversalPolicy;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.FocusEvent;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyVetoException;
/*      */ import java.beans.Transient;
/*      */ import java.beans.VetoableChangeListener;
/*      */ import java.beans.VetoableChangeSupport;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputValidation;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.EventListener;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleExtendedComponent;
/*      */ import javax.accessibility.AccessibleKeyBinding;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.swing.border.AbstractBorder;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.CompoundBorder;
/*      */ import javax.swing.border.TitledBorder;
/*      */ import javax.swing.event.AncestorListener;
/*      */ import javax.swing.event.EventListenerList;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.RequestFocusController;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.swing.SwingUtilities2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class JComponent
/*      */   extends Container
/*      */   implements Serializable, TransferHandler.HasGetTransferHandler
/*      */ {
/*      */   private static final String uiClassID = "ComponentUI";
/*  196 */   private static final Hashtable<ObjectInputStream, ReadObjectCallback> readObjectCallbacks = new Hashtable<>(1);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<KeyStroke> managingFocusForwardTraversalKeys;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<KeyStroke> managingFocusBackwardTraversalKeys;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int NOT_OBSCURED = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int PARTIALLY_OBSCURED = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int COMPLETELY_OBSCURED = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean DEBUG_GRAPHICS_LOADED;
/*      */ 
/*      */ 
/*      */   
/*  228 */   private static final Object INPUT_VERIFIER_SOURCE_KEY = new StringBuilder("InputVerifierSourceKey");
/*      */ 
/*      */   
/*      */   private boolean isAlignmentXSet;
/*      */ 
/*      */   
/*      */   private float alignmentX;
/*      */ 
/*      */   
/*      */   private boolean isAlignmentYSet;
/*      */ 
/*      */   
/*      */   private float alignmentY;
/*      */ 
/*      */   
/*      */   protected transient ComponentUI ui;
/*      */ 
/*      */   
/*  246 */   protected EventListenerList listenerList = new EventListenerList();
/*      */   
/*      */   private transient ArrayTable clientProperties;
/*      */   
/*      */   private VetoableChangeSupport vetoableChangeSupport;
/*      */   
/*      */   private boolean autoscrolls;
/*      */   
/*      */   private Border border;
/*      */   
/*      */   private int flags;
/*      */   
/*  258 */   private InputVerifier inputVerifier = null;
/*      */ 
/*      */   
/*      */   private boolean verifyInputWhenFocusTarget = true;
/*      */ 
/*      */   
/*      */   transient Component paintingChild;
/*      */ 
/*      */   
/*      */   public static final int WHEN_FOCUSED = 0;
/*      */ 
/*      */   
/*      */   public static final int WHEN_ANCESTOR_OF_FOCUSED_COMPONENT = 1;
/*      */ 
/*      */   
/*      */   public static final int WHEN_IN_FOCUSED_WINDOW = 2;
/*      */ 
/*      */   
/*      */   public static final int UNDEFINED_CONDITION = -1;
/*      */ 
/*      */   
/*      */   private static final String KEYBOARD_BINDINGS_KEY = "_KeyboardBindings";
/*      */ 
/*      */   
/*      */   private static final String WHEN_IN_FOCUSED_WINDOW_BINDINGS = "_WhenInFocusedWindow";
/*      */ 
/*      */   
/*      */   public static final String TOOL_TIP_TEXT_KEY = "ToolTipText";
/*      */ 
/*      */   
/*      */   private static final String NEXT_FOCUS = "nextFocus";
/*      */ 
/*      */   
/*      */   private JPopupMenu popupMenu;
/*      */ 
/*      */   
/*      */   private static final int IS_DOUBLE_BUFFERED = 0;
/*      */ 
/*      */   
/*      */   private static final int ANCESTOR_USING_BUFFER = 1;
/*      */ 
/*      */   
/*      */   private static final int IS_PAINTING_TILE = 2;
/*      */ 
/*      */   
/*      */   private static final int IS_OPAQUE = 3;
/*      */ 
/*      */   
/*      */   private static final int KEY_EVENTS_ENABLED = 4;
/*      */ 
/*      */   
/*      */   private static final int FOCUS_INPUTMAP_CREATED = 5;
/*      */ 
/*      */   
/*      */   private static final int ANCESTOR_INPUTMAP_CREATED = 6;
/*      */ 
/*      */   
/*      */   private static final int WIF_INPUTMAP_CREATED = 7;
/*      */ 
/*      */   
/*      */   private static final int ACTIONMAP_CREATED = 8;
/*      */ 
/*      */   
/*      */   private static final int CREATED_DOUBLE_BUFFER = 9;
/*      */   
/*      */   private static final int IS_PRINTING = 11;
/*      */   
/*      */   private static final int IS_PRINTING_ALL = 12;
/*      */   
/*      */   private static final int IS_REPAINTING = 13;
/*      */   
/*      */   private static final int WRITE_OBJ_COUNTER_FIRST = 14;
/*      */   
/*      */   private static final int RESERVED_1 = 15;
/*      */   
/*      */   private static final int RESERVED_2 = 16;
/*      */   
/*      */   private static final int RESERVED_3 = 17;
/*      */   
/*      */   private static final int RESERVED_4 = 18;
/*      */   
/*      */   private static final int RESERVED_5 = 19;
/*      */   
/*      */   private static final int RESERVED_6 = 20;
/*      */   
/*      */   private static final int WRITE_OBJ_COUNTER_LAST = 21;
/*      */   
/*      */   private static final int REQUEST_FOCUS_DISABLED = 22;
/*      */   
/*      */   private static final int INHERITS_POPUP_MENU = 23;
/*      */   
/*      */   private static final int OPAQUE_SET = 24;
/*      */   
/*      */   private static final int AUTOSCROLLS_SET = 25;
/*      */   
/*      */   private static final int FOCUS_TRAVERSAL_KEYS_FORWARD_SET = 26;
/*      */   
/*      */   private static final int FOCUS_TRAVERSAL_KEYS_BACKWARD_SET = 27;
/*      */   
/*  357 */   private transient AtomicBoolean revalidateRunnableScheduled = new AtomicBoolean(false);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  362 */   private static List<Rectangle> tempRectangles = new ArrayList<>(11);
/*      */ 
/*      */   
/*      */   private InputMap focusInputMap;
/*      */ 
/*      */   
/*      */   private InputMap ancestorInputMap;
/*      */   
/*      */   private ComponentInputMap windowInputMap;
/*      */   
/*      */   private ActionMap actionMap;
/*      */   
/*      */   private static final String defaultLocale = "JComponent.defaultLocale";
/*      */   
/*      */   private static Component componentObtainingGraphicsFrom;
/*      */   
/*  378 */   private static Object componentObtainingGraphicsFromLock = new StringBuilder("componentObtainingGraphicsFrom");
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Object aaTextInfo;
/*      */ 
/*      */ 
/*      */   
/*      */   static Graphics safelyGetGraphics(Component paramComponent) {
/*  387 */     return safelyGetGraphics(paramComponent, SwingUtilities.getRoot(paramComponent));
/*      */   }
/*      */   
/*      */   static Graphics safelyGetGraphics(Component paramComponent1, Component paramComponent2) {
/*  391 */     synchronized (componentObtainingGraphicsFromLock) {
/*  392 */       componentObtainingGraphicsFrom = paramComponent2;
/*  393 */       Graphics graphics = paramComponent1.getGraphics();
/*  394 */       componentObtainingGraphicsFrom = null;
/*  395 */       return graphics;
/*      */     } 
/*      */   }
/*      */   
/*      */   static void getGraphicsInvoked(Component paramComponent) {
/*  400 */     if (!isComponentObtainingGraphicsFrom(paramComponent)) {
/*  401 */       JRootPane jRootPane = ((RootPaneContainer)paramComponent).getRootPane();
/*  402 */       if (jRootPane != null) {
/*  403 */         jRootPane.disableTrueDoubleBuffering();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isComponentObtainingGraphicsFrom(Component paramComponent) {
/*  414 */     synchronized (componentObtainingGraphicsFromLock) {
/*  415 */       return (componentObtainingGraphicsFrom == paramComponent);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Set<KeyStroke> getManagingFocusForwardTraversalKeys() {
/*  424 */     synchronized (JComponent.class) {
/*  425 */       if (managingFocusForwardTraversalKeys == null) {
/*  426 */         managingFocusForwardTraversalKeys = new HashSet<>(1);
/*  427 */         managingFocusForwardTraversalKeys.add(
/*  428 */             KeyStroke.getKeyStroke(9, 2));
/*      */       } 
/*      */     } 
/*      */     
/*  432 */     return managingFocusForwardTraversalKeys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Set<KeyStroke> getManagingFocusBackwardTraversalKeys() {
/*  440 */     synchronized (JComponent.class) {
/*  441 */       if (managingFocusBackwardTraversalKeys == null) {
/*  442 */         managingFocusBackwardTraversalKeys = new HashSet<>(1);
/*  443 */         managingFocusBackwardTraversalKeys.add(
/*  444 */             KeyStroke.getKeyStroke(9, 3));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  449 */     return managingFocusBackwardTraversalKeys;
/*      */   }
/*      */   
/*      */   private static Rectangle fetchRectangle() {
/*  453 */     synchronized (tempRectangles) {
/*      */       Rectangle rectangle;
/*  455 */       int i = tempRectangles.size();
/*  456 */       if (i > 0) {
/*  457 */         rectangle = tempRectangles.remove(i - 1);
/*      */       } else {
/*      */         
/*  460 */         rectangle = new Rectangle(0, 0, 0, 0);
/*      */       } 
/*  462 */       return rectangle;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void recycleRectangle(Rectangle paramRectangle) {
/*  467 */     synchronized (tempRectangles) {
/*  468 */       tempRectangles.add(paramRectangle);
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
/*      */   public void setInheritsPopupMenu(boolean paramBoolean) {
/*  491 */     boolean bool = getFlag(23);
/*  492 */     setFlag(23, paramBoolean);
/*  493 */     firePropertyChange("inheritsPopupMenu", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getInheritsPopupMenu() {
/*  503 */     return getFlag(23);
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
/*      */   
/*      */   public void setComponentPopupMenu(JPopupMenu paramJPopupMenu) {
/*  531 */     if (paramJPopupMenu != null) {
/*  532 */       enableEvents(16L);
/*      */     }
/*  534 */     JPopupMenu jPopupMenu = this.popupMenu;
/*  535 */     this.popupMenu = paramJPopupMenu;
/*  536 */     firePropertyChange("componentPopupMenu", jPopupMenu, paramJPopupMenu);
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
/*      */   public JPopupMenu getComponentPopupMenu() {
/*  553 */     if (!getInheritsPopupMenu()) {
/*  554 */       return this.popupMenu;
/*      */     }
/*      */     
/*  557 */     if (this.popupMenu == null) {
/*      */       
/*  559 */       Container container = getParent();
/*  560 */       while (container != null) {
/*  561 */         if (container instanceof JComponent) {
/*  562 */           return ((JComponent)container).getComponentPopupMenu();
/*      */         }
/*  564 */         if (container instanceof Window || container instanceof java.applet.Applet) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  569 */         container = container.getParent();
/*      */       } 
/*  571 */       return null;
/*      */     } 
/*      */     
/*  574 */     return this.popupMenu;
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
/*      */   public JComponent() {
/*  592 */     enableEvents(8L);
/*  593 */     if (isManagingFocus()) {
/*  594 */       LookAndFeel.installProperty(this, "focusTraversalKeysForward", 
/*      */           
/*  596 */           getManagingFocusForwardTraversalKeys());
/*  597 */       LookAndFeel.installProperty(this, "focusTraversalKeysBackward", 
/*      */           
/*  599 */           getManagingFocusBackwardTraversalKeys());
/*      */     } 
/*      */     
/*  602 */     setLocale(getDefaultLocale());
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
/*      */ 
/*      */   
/*      */   public void updateUI() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUI(ComponentUI paramComponentUI) {
/*  658 */     uninstallUIAndProperties();
/*      */ 
/*      */     
/*  661 */     this
/*  662 */       .aaTextInfo = UIManager.getDefaults().get(SwingUtilities2.AA_TEXT_PROPERTY_KEY);
/*  663 */     ComponentUI componentUI = this.ui;
/*  664 */     this.ui = paramComponentUI;
/*  665 */     if (this.ui != null) {
/*  666 */       this.ui.installUI(this);
/*      */     }
/*      */     
/*  669 */     firePropertyChange("UI", componentUI, paramComponentUI);
/*  670 */     revalidate();
/*  671 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void uninstallUIAndProperties() {
/*  680 */     if (this.ui != null) {
/*  681 */       this.ui.uninstallUI(this);
/*      */       
/*  683 */       if (this.clientProperties != null) {
/*  684 */         synchronized (this.clientProperties) {
/*      */           
/*  686 */           Object[] arrayOfObject = this.clientProperties.getKeys(null);
/*  687 */           if (arrayOfObject != null) {
/*  688 */             for (Object object : arrayOfObject) {
/*  689 */               if (object instanceof sun.swing.UIClientPropertyKey) {
/*  690 */                 putClientProperty(object, (Object)null);
/*      */               }
/*      */             } 
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
/*      */ 
/*      */ 
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
/*  717 */     return "ComponentUI";
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
/*      */   protected Graphics getComponentGraphics(Graphics paramGraphics) {
/*  732 */     Graphics graphics = paramGraphics;
/*  733 */     if (this.ui != null && DEBUG_GRAPHICS_LOADED && 
/*  734 */       DebugGraphics.debugComponentCount() != 0 && 
/*  735 */       shouldDebugGraphics() != 0 && !(paramGraphics instanceof DebugGraphics))
/*      */     {
/*  737 */       graphics = new DebugGraphics(paramGraphics, this);
/*      */     }
/*      */     
/*  740 */     graphics.setColor(getForeground());
/*  741 */     graphics.setFont(getFont());
/*      */     
/*  743 */     return graphics;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void paintComponent(Graphics paramGraphics) {
/*  777 */     if (this.ui != null) {
/*  778 */       Graphics graphics = (paramGraphics == null) ? null : paramGraphics.create();
/*      */       try {
/*  780 */         this.ui.update(graphics, this);
/*      */       } finally {
/*      */         
/*  783 */         graphics.dispose();
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
/*      */   protected void paintChildren(Graphics paramGraphics) {
/*  800 */     Graphics graphics = paramGraphics;
/*      */     
/*  802 */     synchronized (getTreeLock()) {
/*  803 */       int i = getComponentCount() - 1;
/*  804 */       if (i < 0) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  809 */       if (this.paintingChild != null && this.paintingChild instanceof JComponent && this.paintingChild
/*      */         
/*  811 */         .isOpaque()) {
/*  812 */         for (; i >= 0 && 
/*  813 */           getComponent(i) != this.paintingChild; i--);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  818 */       Rectangle rectangle1 = fetchRectangle();
/*      */       
/*  820 */       boolean bool1 = (!isOptimizedDrawingEnabled() && checkIfChildObscuredBySibling()) ? true : false;
/*  821 */       Rectangle rectangle2 = null;
/*  822 */       if (bool1) {
/*  823 */         rectangle2 = graphics.getClipBounds();
/*  824 */         if (rectangle2 == null)
/*      */         {
/*  826 */           rectangle2 = new Rectangle(0, 0, getWidth(), getHeight());
/*      */         }
/*      */       } 
/*  829 */       boolean bool = getFlag(11);
/*  830 */       Window window = SwingUtilities.getWindowAncestor(this);
/*  831 */       boolean bool2 = (window == null || window.isOpaque()) ? true : false;
/*  832 */       for (; i >= 0; i--) {
/*  833 */         Component component = getComponent(i);
/*  834 */         if (component == null) {
/*      */           continue;
/*      */         }
/*      */         
/*  838 */         boolean bool3 = component instanceof JComponent;
/*      */ 
/*      */ 
/*      */         
/*  842 */         if ((!bool2 || bool3 || 
/*  843 */           isLightweightComponent(component)) && component.isVisible()) {
/*      */ 
/*      */ 
/*      */           
/*  847 */           Rectangle rectangle = component.getBounds(rectangle1);
/*      */           
/*  849 */           boolean bool4 = paramGraphics.hitClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */ 
/*      */           
/*  852 */           if (bool4) {
/*  853 */             boolean bool5; if (bool1 && i > 0) {
/*  854 */               int j = rectangle.x;
/*  855 */               int k = rectangle.y;
/*  856 */               int m = rectangle.width;
/*  857 */               int n = rectangle.height;
/*      */               
/*  859 */               SwingUtilities.computeIntersection(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height, rectangle);
/*      */ 
/*      */               
/*  862 */               if (getObscuredState(i, rectangle.x, rectangle.y, rectangle.width, rectangle.height) == 2) {
/*      */                 continue;
/*      */               }
/*      */               
/*  866 */               rectangle.x = j;
/*  867 */               rectangle.y = k;
/*  868 */               rectangle.width = m;
/*  869 */               rectangle.height = n;
/*      */             } 
/*  871 */             Graphics graphics1 = graphics.create(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */             
/*  873 */             graphics1.setColor(component.getForeground());
/*  874 */             graphics1.setFont(component.getFont());
/*      */           } 
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
/*      */         continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  927 */       recycleRectangle(rectangle1);
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
/*      */   protected void paintBorder(Graphics paramGraphics) {
/*  947 */     Border border = getBorder();
/*  948 */     if (border != null) {
/*  949 */       border.paintBorder(this, paramGraphics, 0, 0, getWidth(), getHeight());
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
/*      */   public void update(Graphics paramGraphics) {
/*  965 */     paint(paramGraphics);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void paint(Graphics paramGraphics) {
/*  995 */     boolean bool = false;
/*      */     
/*  997 */     if (getWidth() <= 0 || getHeight() <= 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1001 */     Graphics graphics1 = getComponentGraphics(paramGraphics);
/* 1002 */     Graphics graphics2 = graphics1.create(); try {
/*      */       int i, j, k, m;
/* 1004 */       RepaintManager repaintManager = RepaintManager.currentManager(this);
/* 1005 */       Rectangle rectangle = graphics2.getClipBounds();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1010 */       if (rectangle == null) {
/* 1011 */         i = j = 0;
/* 1012 */         k = getWidth();
/* 1013 */         m = getHeight();
/*      */       } else {
/*      */         
/* 1016 */         i = rectangle.x;
/* 1017 */         j = rectangle.y;
/* 1018 */         k = rectangle.width;
/* 1019 */         m = rectangle.height;
/*      */       } 
/*      */       
/* 1022 */       if (k > getWidth()) {
/* 1023 */         k = getWidth();
/*      */       }
/* 1025 */       if (m > getHeight()) {
/* 1026 */         m = getHeight();
/*      */       }
/*      */       
/* 1029 */       if (getParent() != null && !(getParent() instanceof JComponent)) {
/* 1030 */         adjustPaintFlags();
/* 1031 */         bool = true;
/*      */       } 
/*      */ 
/*      */       
/* 1035 */       boolean bool1 = getFlag(11);
/* 1036 */       if (!bool1 && repaintManager.isDoubleBufferingEnabled() && 
/* 1037 */         !getFlag(1) && isDoubleBuffered() && (
/* 1038 */         getFlag(13) || repaintManager.isPainting())) {
/*      */         
/* 1040 */         repaintManager.beginPaint();
/*      */         try {
/* 1042 */           repaintManager.paint(this, this, graphics2, i, j, k, m);
/*      */         } finally {
/*      */           
/* 1045 */           repaintManager.endPaint();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1050 */         if (rectangle == null) {
/* 1051 */           graphics2.setClip(i, j, k, m);
/*      */         }
/*      */         
/* 1054 */         if (!rectangleIsObscured(i, j, k, m)) {
/* 1055 */           if (!bool1) {
/* 1056 */             paintComponent(graphics2);
/* 1057 */             paintBorder(graphics2);
/*      */           } else {
/*      */             
/* 1060 */             printComponent(graphics2);
/* 1061 */             printBorder(graphics2);
/*      */           } 
/*      */         }
/* 1064 */         if (!bool1) {
/* 1065 */           paintChildren(graphics2);
/*      */         } else {
/*      */           
/* 1068 */           printChildren(graphics2);
/*      */         } 
/*      */       } 
/*      */     } finally {
/* 1072 */       graphics2.dispose();
/* 1073 */       if (bool) {
/* 1074 */         setFlag(1, false);
/* 1075 */         setFlag(2, false);
/* 1076 */         setFlag(11, false);
/* 1077 */         setFlag(12, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void paintForceDoubleBuffered(Graphics paramGraphics) {
/* 1087 */     RepaintManager repaintManager = RepaintManager.currentManager(this);
/* 1088 */     Rectangle rectangle = paramGraphics.getClipBounds();
/* 1089 */     repaintManager.beginPaint();
/* 1090 */     setFlag(13, true);
/*      */     try {
/* 1092 */       repaintManager.paint(this, this, paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */     } finally {
/* 1094 */       repaintManager.endPaint();
/* 1095 */       setFlag(13, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isPainting() {
/* 1104 */     JComponent jComponent = this;
/* 1105 */     while (jComponent != null) {
/* 1106 */       if (jComponent instanceof JComponent && jComponent
/* 1107 */         .getFlag(1)) {
/* 1108 */         return true;
/*      */       }
/* 1110 */       Container container = jComponent.getParent();
/*      */     } 
/* 1112 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustPaintFlags() {
/* 1118 */     for (Container container = getParent(); container != null; 
/* 1119 */       container = container.getParent()) {
/* 1120 */       if (container instanceof JComponent) {
/* 1121 */         JComponent jComponent = (JComponent)container;
/* 1122 */         if (jComponent.getFlag(1))
/* 1123 */           setFlag(1, true); 
/* 1124 */         if (jComponent.getFlag(2))
/* 1125 */           setFlag(2, true); 
/* 1126 */         if (jComponent.getFlag(11))
/* 1127 */           setFlag(11, true); 
/* 1128 */         if (jComponent.getFlag(12)) {
/* 1129 */           setFlag(12, true);
/*      */         }
/*      */         break;
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
/*      */   public void printAll(Graphics paramGraphics) {
/* 1146 */     setFlag(12, true);
/*      */     try {
/* 1148 */       print(paramGraphics);
/*      */     } finally {
/*      */       
/* 1151 */       setFlag(12, false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void print(Graphics paramGraphics) {
/* 1199 */     setFlag(11, true);
/* 1200 */     firePropertyChange("paintingForPrint", false, true);
/*      */     try {
/* 1202 */       paint(paramGraphics);
/*      */     } finally {
/*      */       
/* 1205 */       setFlag(11, false);
/* 1206 */       firePropertyChange("paintingForPrint", true, false);
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
/*      */   protected void printComponent(Graphics paramGraphics) {
/* 1220 */     paintComponent(paramGraphics);
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
/*      */   protected void printChildren(Graphics paramGraphics) {
/* 1233 */     paintChildren(paramGraphics);
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
/*      */   protected void printBorder(Graphics paramGraphics) {
/* 1246 */     paintBorder(paramGraphics);
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
/*      */   public boolean isPaintingTile() {
/* 1260 */     return getFlag(2);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isPaintingForPrint() {
/* 1290 */     return getFlag(11);
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
/*      */   @Deprecated
/*      */   public boolean isManagingFocus() {
/* 1313 */     return false;
/*      */   }
/*      */   
/*      */   private void registerNextFocusableComponent() {
/* 1317 */     registerNextFocusableComponent(getNextFocusableComponent());
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerNextFocusableComponent(Component paramComponent) {
/* 1322 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1327 */     Container container = isFocusCycleRoot() ? this : getFocusCycleRootAncestor();
/* 1328 */     FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 1329 */     if (!(focusTraversalPolicy instanceof LegacyGlueFocusTraversalPolicy)) {
/* 1330 */       focusTraversalPolicy = new LegacyGlueFocusTraversalPolicy(focusTraversalPolicy);
/* 1331 */       container.setFocusTraversalPolicy(focusTraversalPolicy);
/*      */     } 
/* 1333 */     ((LegacyGlueFocusTraversalPolicy)focusTraversalPolicy)
/* 1334 */       .setNextFocusableComponent(this, paramComponent);
/*      */   }
/*      */   
/*      */   private void deregisterNextFocusableComponent() {
/* 1338 */     Component component = getNextFocusableComponent();
/* 1339 */     if (component == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1344 */     Container container = isFocusCycleRoot() ? this : getFocusCycleRootAncestor();
/* 1345 */     if (container == null) {
/*      */       return;
/*      */     }
/* 1348 */     FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy();
/* 1349 */     if (focusTraversalPolicy instanceof LegacyGlueFocusTraversalPolicy) {
/* 1350 */       ((LegacyGlueFocusTraversalPolicy)focusTraversalPolicy)
/* 1351 */         .unsetNextFocusableComponent(this, component);
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
/*      */   @Deprecated
/*      */   public void setNextFocusableComponent(Component paramComponent) {
/* 1378 */     boolean bool = isDisplayable();
/* 1379 */     if (bool) {
/* 1380 */       deregisterNextFocusableComponent();
/*      */     }
/* 1382 */     putClientProperty("nextFocus", paramComponent);
/* 1383 */     if (bool) {
/* 1384 */       registerNextFocusableComponent(paramComponent);
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
/*      */   @Deprecated
/*      */   public Component getNextFocusableComponent() {
/* 1408 */     return (Component)getClientProperty("nextFocus");
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
/*      */   public void setRequestFocusEnabled(boolean paramBoolean) {
/* 1435 */     setFlag(22, !paramBoolean);
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
/*      */   public boolean isRequestFocusEnabled() {
/* 1456 */     return !getFlag(22);
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
/*      */   public void requestFocus() {
/* 1478 */     super.requestFocus();
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
/*      */   public boolean requestFocus(boolean paramBoolean) {
/* 1504 */     return super.requestFocus(paramBoolean);
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
/*      */   public boolean requestFocusInWindow() {
/* 1525 */     return super.requestFocusInWindow();
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
/*      */   protected boolean requestFocusInWindow(boolean paramBoolean) {
/* 1547 */     return super.requestFocusInWindow(paramBoolean);
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
/*      */   public void grabFocus() {
/* 1563 */     requestFocus();
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
/*      */   public void setVerifyInputWhenFocusTarget(boolean paramBoolean) {
/* 1589 */     boolean bool = this.verifyInputWhenFocusTarget;
/*      */     
/* 1591 */     this.verifyInputWhenFocusTarget = paramBoolean;
/* 1592 */     firePropertyChange("verifyInputWhenFocusTarget", bool, paramBoolean);
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
/*      */   public boolean getVerifyInputWhenFocusTarget() {
/* 1612 */     return this.verifyInputWhenFocusTarget;
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
/*      */   public FontMetrics getFontMetrics(Font paramFont) {
/* 1626 */     return SwingUtilities2.getFontMetrics(this, paramFont);
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
/*      */   public void setPreferredSize(Dimension paramDimension) {
/* 1640 */     super.setPreferredSize(paramDimension);
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
/*      */   @Transient
/*      */   public Dimension getPreferredSize() {
/* 1657 */     if (isPreferredSizeSet()) {
/* 1658 */       return super.getPreferredSize();
/*      */     }
/* 1660 */     Dimension dimension = null;
/* 1661 */     if (this.ui != null) {
/* 1662 */       dimension = this.ui.getPreferredSize(this);
/*      */     }
/* 1664 */     return (dimension != null) ? dimension : super.getPreferredSize();
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
/*      */   public void setMaximumSize(Dimension paramDimension) {
/* 1683 */     super.setMaximumSize(paramDimension);
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
/*      */   @Transient
/*      */   public Dimension getMaximumSize() {
/* 1699 */     if (isMaximumSizeSet()) {
/* 1700 */       return super.getMaximumSize();
/*      */     }
/* 1702 */     Dimension dimension = null;
/* 1703 */     if (this.ui != null) {
/* 1704 */       dimension = this.ui.getMaximumSize(this);
/*      */     }
/* 1706 */     return (dimension != null) ? dimension : super.getMaximumSize();
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
/*      */   public void setMinimumSize(Dimension paramDimension) {
/* 1724 */     super.setMinimumSize(paramDimension);
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
/*      */   @Transient
/*      */   public Dimension getMinimumSize() {
/* 1739 */     if (isMinimumSizeSet()) {
/* 1740 */       return super.getMinimumSize();
/*      */     }
/* 1742 */     Dimension dimension = null;
/* 1743 */     if (this.ui != null) {
/* 1744 */       dimension = this.ui.getMinimumSize(this);
/*      */     }
/* 1746 */     return (dimension != null) ? dimension : super.getMinimumSize();
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
/*      */   public boolean contains(int paramInt1, int paramInt2) {
/* 1758 */     return (this.ui != null) ? this.ui.contains(this, paramInt1, paramInt2) : super.contains(paramInt1, paramInt2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorder(Border paramBorder) {
/* 1793 */     Border border = this.border;
/*      */     
/* 1795 */     this.border = paramBorder;
/* 1796 */     firePropertyChange("border", border, paramBorder);
/* 1797 */     if (paramBorder != border) {
/* 1798 */       if (paramBorder == null || border == null || 
/* 1799 */         !paramBorder.getBorderInsets(this).equals(border.getBorderInsets(this))) {
/* 1800 */         revalidate();
/*      */       }
/* 1802 */       repaint();
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
/*      */   public Border getBorder() {
/* 1814 */     return this.border;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Insets getInsets() {
/* 1825 */     if (this.border != null) {
/* 1826 */       return this.border.getBorderInsets(this);
/*      */     }
/* 1828 */     return super.getInsets();
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
/*      */   public Insets getInsets(Insets paramInsets) {
/* 1846 */     if (paramInsets == null) {
/* 1847 */       paramInsets = new Insets(0, 0, 0, 0);
/*      */     }
/* 1849 */     if (this.border != null) {
/* 1850 */       if (this.border instanceof AbstractBorder) {
/* 1851 */         return ((AbstractBorder)this.border).getBorderInsets(this, paramInsets);
/*      */       }
/*      */ 
/*      */       
/* 1855 */       return this.border.getBorderInsets(this);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1860 */     paramInsets.left = paramInsets.top = paramInsets.right = paramInsets.bottom = 0;
/* 1861 */     return paramInsets;
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
/*      */   public float getAlignmentY() {
/* 1874 */     if (this.isAlignmentYSet) {
/* 1875 */       return this.alignmentY;
/*      */     }
/* 1877 */     return super.getAlignmentY();
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
/*      */   public void setAlignmentY(float paramFloat) {
/* 1889 */     this.alignmentY = (paramFloat > 1.0F) ? 1.0F : ((paramFloat < 0.0F) ? 0.0F : paramFloat);
/* 1890 */     this.isAlignmentYSet = true;
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
/*      */   public float getAlignmentX() {
/* 1903 */     if (this.isAlignmentXSet) {
/* 1904 */       return this.alignmentX;
/*      */     }
/* 1906 */     return super.getAlignmentX();
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
/*      */   public void setAlignmentX(float paramFloat) {
/* 1918 */     this.alignmentX = (paramFloat > 1.0F) ? 1.0F : ((paramFloat < 0.0F) ? 0.0F : paramFloat);
/* 1919 */     this.isAlignmentXSet = true;
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
/*      */   public void setInputVerifier(InputVerifier paramInputVerifier) {
/* 1933 */     InputVerifier inputVerifier = (InputVerifier)getClientProperty(ClientPropertyKey.JComponent_INPUT_VERIFIER);
/*      */     
/* 1935 */     putClientProperty(ClientPropertyKey.JComponent_INPUT_VERIFIER, paramInputVerifier);
/* 1936 */     firePropertyChange("inputVerifier", inputVerifier, paramInputVerifier);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InputVerifier getInputVerifier() {
/* 1947 */     return (InputVerifier)getClientProperty(ClientPropertyKey.JComponent_INPUT_VERIFIER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics getGraphics() {
/* 1957 */     if (DEBUG_GRAPHICS_LOADED && shouldDebugGraphics() != 0) {
/* 1958 */       return new DebugGraphics(super.getGraphics(), this);
/*      */     }
/*      */ 
/*      */     
/* 1962 */     return super.getGraphics();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDebugGraphicsOptions(int paramInt) {
/* 1992 */     DebugGraphics.setDebugOptions(this, paramInt);
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
/*      */   public int getDebugGraphicsOptions() {
/* 2011 */     return DebugGraphics.getDebugOptions(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int shouldDebugGraphics() {
/* 2020 */     return DebugGraphics.shouldComponentDebug(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerKeyboardAction(ActionListener paramActionListener, String paramString, KeyStroke paramKeyStroke, int paramInt) {
/* 2089 */     InputMap inputMap = getInputMap(paramInt, true);
/*      */     
/* 2091 */     if (inputMap != null) {
/* 2092 */       ActionMap actionMap = getActionMap(true);
/* 2093 */       ActionStandin actionStandin = new ActionStandin(paramActionListener, paramString);
/* 2094 */       inputMap.put(paramKeyStroke, actionStandin);
/* 2095 */       if (actionMap != null) {
/* 2096 */         actionMap.put(actionStandin, actionStandin);
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
/*      */   private void registerWithKeyboardManager(boolean paramBoolean) {
/*      */     Object object;
/* 2112 */     InputMap inputMap = getInputMap(2, false);
/*      */ 
/*      */ 
/*      */     
/* 2116 */     Hashtable<Object, Object> hashtable = (Hashtable)getClientProperty("_WhenInFocusedWindow");
/*      */     
/* 2118 */     if (inputMap != null) {
/*      */       
/* 2120 */       object = inputMap.allKeys();
/* 2121 */       if (object != null) {
/* 2122 */         for (int i = object.length - 1; i >= 0; 
/* 2123 */           i--) {
/* 2124 */           if (!paramBoolean || hashtable == null || hashtable
/* 2125 */             .get(object[i]) == null) {
/* 2126 */             registerWithKeyboardManager(object[i]);
/*      */           }
/* 2128 */           if (hashtable != null) {
/* 2129 */             hashtable.remove(object[i]);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } else {
/*      */       
/* 2135 */       object = null;
/*      */     } 
/*      */     
/* 2138 */     if (hashtable != null && hashtable.size() > 0) {
/* 2139 */       Enumeration<KeyStroke> enumeration = hashtable.keys();
/*      */       
/* 2141 */       while (enumeration.hasMoreElements()) {
/* 2142 */         KeyStroke keyStroke = enumeration.nextElement();
/* 2143 */         unregisterWithKeyboardManager(keyStroke);
/*      */       } 
/* 2145 */       hashtable.clear();
/*      */     } 
/*      */     
/* 2148 */     if (object != null && object.length > 0) {
/* 2149 */       if (hashtable == null) {
/* 2150 */         hashtable = new Hashtable<>(object.length);
/* 2151 */         putClientProperty("_WhenInFocusedWindow", hashtable);
/*      */       } 
/* 2153 */       for (int i = object.length - 1; i >= 0; i--) {
/* 2154 */         hashtable.put(object[i], object[i]);
/*      */       }
/*      */     } else {
/*      */       
/* 2158 */       putClientProperty("_WhenInFocusedWindow", (Object)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unregisterWithKeyboardManager() {
/* 2169 */     Hashtable hashtable = (Hashtable)getClientProperty("_WhenInFocusedWindow");
/*      */     
/* 2171 */     if (hashtable != null && hashtable.size() > 0) {
/* 2172 */       Enumeration<KeyStroke> enumeration = hashtable.keys();
/*      */       
/* 2174 */       while (enumeration.hasMoreElements()) {
/* 2175 */         KeyStroke keyStroke = enumeration.nextElement();
/* 2176 */         unregisterWithKeyboardManager(keyStroke);
/*      */       } 
/*      */     } 
/* 2179 */     putClientProperty("_WhenInFocusedWindow", (Object)null);
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
/*      */   void componentInputMapChanged(ComponentInputMap paramComponentInputMap) {
/* 2191 */     InputMap inputMap = getInputMap(2, false);
/*      */     
/* 2193 */     while (inputMap != paramComponentInputMap && inputMap != null) {
/* 2194 */       inputMap = inputMap.getParent();
/*      */     }
/* 2196 */     if (inputMap != null) {
/* 2197 */       registerWithKeyboardManager(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void registerWithKeyboardManager(KeyStroke paramKeyStroke) {
/* 2202 */     KeyboardManager.getCurrentManager().registerKeyStroke(paramKeyStroke, this);
/*      */   }
/*      */   
/*      */   private void unregisterWithKeyboardManager(KeyStroke paramKeyStroke) {
/* 2206 */     KeyboardManager.getCurrentManager().unregisterKeyStroke(paramKeyStroke, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerKeyboardAction(ActionListener paramActionListener, KeyStroke paramKeyStroke, int paramInt) {
/* 2216 */     registerKeyboardAction(paramActionListener, (String)null, paramKeyStroke, paramInt);
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
/*      */   public void unregisterKeyboardAction(KeyStroke paramKeyStroke) {
/* 2234 */     ActionMap actionMap = getActionMap(false);
/* 2235 */     for (byte b = 0; b < 3; b++) {
/* 2236 */       InputMap inputMap = getInputMap(b, false);
/* 2237 */       if (inputMap != null) {
/* 2238 */         Object object = inputMap.get(paramKeyStroke);
/*      */         
/* 2240 */         if (actionMap != null && object != null) {
/* 2241 */           actionMap.remove(object);
/*      */         }
/* 2243 */         inputMap.remove(paramKeyStroke);
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
/*      */   public KeyStroke[] getRegisteredKeyStrokes() {
/* 2256 */     int[] arrayOfInt = new int[3];
/* 2257 */     KeyStroke[][] arrayOfKeyStroke = new KeyStroke[3][];
/*      */     
/* 2259 */     for (byte b1 = 0; b1 < 3; b1++) {
/* 2260 */       InputMap inputMap = getInputMap(b1, false);
/* 2261 */       arrayOfKeyStroke[b1] = (inputMap != null) ? inputMap.allKeys() : null;
/* 2262 */       arrayOfInt[b1] = (arrayOfKeyStroke[b1] != null) ? (arrayOfKeyStroke[b1]).length : 0;
/*      */     } 
/*      */     
/* 2265 */     KeyStroke[] arrayOfKeyStroke1 = new KeyStroke[arrayOfInt[0] + arrayOfInt[1] + arrayOfInt[2]]; byte b2;
/*      */     int i;
/* 2267 */     for (b2 = 0, i = 0; b2 < 3; b2++) {
/* 2268 */       if (arrayOfInt[b2] > 0) {
/* 2269 */         System.arraycopy(arrayOfKeyStroke[b2], 0, arrayOfKeyStroke1, i, arrayOfInt[b2]);
/*      */         
/* 2271 */         i += arrayOfInt[b2];
/*      */       } 
/*      */     } 
/* 2274 */     return arrayOfKeyStroke1;
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
/*      */   public int getConditionForKeyStroke(KeyStroke paramKeyStroke) {
/* 2290 */     for (byte b = 0; b < 3; b++) {
/* 2291 */       InputMap inputMap = getInputMap(b, false);
/* 2292 */       if (inputMap != null && inputMap.get(paramKeyStroke) != null) {
/* 2293 */         return b;
/*      */       }
/*      */     } 
/* 2296 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ActionListener getActionForKeyStroke(KeyStroke paramKeyStroke) {
/* 2307 */     ActionMap actionMap = getActionMap(false);
/*      */     
/* 2309 */     if (actionMap == null) {
/* 2310 */       return null;
/*      */     }
/* 2312 */     for (byte b = 0; b < 3; b++) {
/* 2313 */       InputMap inputMap = getInputMap(b, false);
/* 2314 */       if (inputMap != null) {
/* 2315 */         Object object = inputMap.get(paramKeyStroke);
/*      */         
/* 2317 */         if (object != null) {
/* 2318 */           Action action = actionMap.get(object);
/* 2319 */           if (action instanceof ActionStandin) {
/* 2320 */             return ((ActionStandin)action).actionListener;
/*      */           }
/* 2322 */           return action;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2326 */     return null;
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
/*      */   public void resetKeyboardActions() {
/* 2338 */     for (byte b = 0; b < 3; b++) {
/* 2339 */       InputMap inputMap = getInputMap(b, false);
/*      */       
/* 2341 */       if (inputMap != null) {
/* 2342 */         inputMap.clear();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 2347 */     ActionMap actionMap = getActionMap(false);
/*      */     
/* 2349 */     if (actionMap != null) {
/* 2350 */       actionMap.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setInputMap(int paramInt, InputMap paramInputMap) {
/* 2382 */     switch (paramInt) {
/*      */       case 2:
/* 2384 */         if (paramInputMap != null && !(paramInputMap instanceof ComponentInputMap)) {
/* 2385 */           throw new IllegalArgumentException("WHEN_IN_FOCUSED_WINDOW InputMaps must be of type ComponentInputMap");
/*      */         }
/* 2387 */         this.windowInputMap = (ComponentInputMap)paramInputMap;
/* 2388 */         setFlag(7, true);
/* 2389 */         registerWithKeyboardManager(false);
/*      */         return;
/*      */       case 1:
/* 2392 */         this.ancestorInputMap = paramInputMap;
/* 2393 */         setFlag(6, true);
/*      */         return;
/*      */       case 0:
/* 2396 */         this.focusInputMap = paramInputMap;
/* 2397 */         setFlag(5, true);
/*      */         return;
/*      */     } 
/* 2400 */     throw new IllegalArgumentException("condition must be one of JComponent.WHEN_IN_FOCUSED_WINDOW, JComponent.WHEN_FOCUSED or JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT");
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
/*      */   public final InputMap getInputMap(int paramInt) {
/* 2415 */     return getInputMap(paramInt, true);
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
/*      */   public final InputMap getInputMap() {
/* 2427 */     return getInputMap(0, true);
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
/*      */   public final void setActionMap(ActionMap paramActionMap) {
/* 2439 */     this.actionMap = paramActionMap;
/* 2440 */     setFlag(8, true);
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
/*      */   public final ActionMap getActionMap() {
/* 2453 */     return getActionMap(true);
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
/*      */   final InputMap getInputMap(int paramInt, boolean paramBoolean) {
/* 2477 */     switch (paramInt) {
/*      */       case 0:
/* 2479 */         if (getFlag(5)) {
/* 2480 */           return this.focusInputMap;
/*      */         }
/*      */         
/* 2483 */         if (paramBoolean) {
/* 2484 */           InputMap inputMap = new InputMap();
/* 2485 */           setInputMap(paramInt, inputMap);
/* 2486 */           return inputMap;
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
/* 2514 */         return null;case 1: if (getFlag(6)) return this.ancestorInputMap;  if (paramBoolean) { InputMap inputMap = new InputMap(); setInputMap(paramInt, inputMap); return inputMap; }  return null;case 2: if (getFlag(7)) return this.windowInputMap;  if (paramBoolean) { ComponentInputMap componentInputMap = new ComponentInputMap(this); setInputMap(paramInt, componentInputMap); return componentInputMap; }  return null;
/*      */     } 
/*      */     throw new IllegalArgumentException("condition must be one of JComponent.WHEN_IN_FOCUSED_WINDOW, JComponent.WHEN_FOCUSED or JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final ActionMap getActionMap(boolean paramBoolean) {
/* 2527 */     if (getFlag(8)) {
/* 2528 */       return this.actionMap;
/*      */     }
/*      */     
/* 2531 */     if (paramBoolean) {
/* 2532 */       ActionMap actionMap = new ActionMap();
/* 2533 */       setActionMap(actionMap);
/* 2534 */       return actionMap;
/*      */     } 
/* 2536 */     return null;
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
/*      */   public int getBaseline(int paramInt1, int paramInt2) {
/* 2562 */     super.getBaseline(paramInt1, paramInt2);
/* 2563 */     if (this.ui != null) {
/* 2564 */       return this.ui.getBaseline(this, paramInt1, paramInt2);
/*      */     }
/* 2566 */     return -1;
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
/*      */   public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
/* 2591 */     if (this.ui != null) {
/* 2592 */       return this.ui.getBaselineResizeBehavior(this);
/*      */     }
/* 2594 */     return Component.BaselineResizeBehavior.OTHER;
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
/*      */   @Deprecated
/*      */   public boolean requestDefaultFocus() {
/* 2618 */     Container container = isFocusCycleRoot() ? this : getFocusCycleRootAncestor();
/* 2619 */     if (container == null) {
/* 2620 */       return false;
/*      */     }
/*      */     
/* 2623 */     Component component = container.getFocusTraversalPolicy().getDefaultComponent(container);
/* 2624 */     if (component != null) {
/* 2625 */       component.requestFocus();
/* 2626 */       return true;
/*      */     } 
/* 2628 */     return false;
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
/*      */   public void setVisible(boolean paramBoolean) {
/* 2643 */     if (paramBoolean != isVisible()) {
/* 2644 */       super.setVisible(paramBoolean);
/* 2645 */       if (paramBoolean) {
/* 2646 */         Container container = getParent();
/* 2647 */         if (container != null) {
/* 2648 */           Rectangle rectangle = getBounds();
/* 2649 */           container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */         } 
/* 2651 */         revalidate();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnabled(boolean paramBoolean) {
/* 2679 */     boolean bool = isEnabled();
/* 2680 */     super.setEnabled(paramBoolean);
/* 2681 */     firePropertyChange("enabled", bool, paramBoolean);
/* 2682 */     if (paramBoolean != bool) {
/* 2683 */       repaint();
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
/*      */   public void setForeground(Color paramColor) {
/* 2702 */     Color color = getForeground();
/* 2703 */     super.setForeground(paramColor);
/* 2704 */     if ((color != null) ? !color.equals(paramColor) : (paramColor != null && !paramColor.equals(color)))
/*      */     {
/* 2706 */       repaint();
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
/*      */   public void setBackground(Color paramColor) {
/* 2732 */     Color color = getBackground();
/* 2733 */     super.setBackground(paramColor);
/* 2734 */     if ((color != null) ? !color.equals(paramColor) : (paramColor != null && !paramColor.equals(color)))
/*      */     {
/* 2736 */       repaint();
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
/*      */   public void setFont(Font paramFont) {
/* 2753 */     Font font = getFont();
/* 2754 */     super.setFont(paramFont);
/*      */     
/* 2756 */     if (paramFont != font) {
/* 2757 */       revalidate();
/* 2758 */       repaint();
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
/*      */   public static Locale getDefaultLocale() {
/* 2778 */     Locale locale = (Locale)SwingUtilities.appContextGet("JComponent.defaultLocale");
/* 2779 */     if (locale == null) {
/*      */ 
/*      */       
/* 2782 */       locale = Locale.getDefault();
/* 2783 */       setDefaultLocale(locale);
/*      */     } 
/* 2785 */     return locale;
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
/*      */   public static void setDefaultLocale(Locale paramLocale) {
/* 2805 */     SwingUtilities.appContextPut("JComponent.defaultLocale", paramLocale);
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
/*      */   protected void processComponentKeyEvent(KeyEvent paramKeyEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processKeyEvent(KeyEvent paramKeyEvent) {
/* 2832 */     super.processKeyEvent(paramKeyEvent);
/*      */ 
/*      */     
/* 2835 */     if (!paramKeyEvent.isConsumed()) {
/* 2836 */       processComponentKeyEvent(paramKeyEvent);
/*      */     }
/*      */     
/* 2839 */     boolean bool = KeyboardState.shouldProcess(paramKeyEvent);
/*      */     
/* 2841 */     if (paramKeyEvent.isConsumed()) {
/*      */       return;
/*      */     }
/*      */     
/* 2845 */     if (bool && processKeyBindings(paramKeyEvent, (paramKeyEvent.getID() == 401)))
/*      */     {
/* 2847 */       paramKeyEvent.consume();
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
/*      */   protected boolean processKeyBinding(KeyStroke paramKeyStroke, KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean) {
/* 2875 */     InputMap inputMap = getInputMap(paramInt, false);
/* 2876 */     ActionMap actionMap = getActionMap(false);
/*      */     
/* 2878 */     if (inputMap != null && actionMap != null && isEnabled()) {
/* 2879 */       Object object = inputMap.get(paramKeyStroke);
/* 2880 */       Action action = (object == null) ? null : actionMap.get(object);
/* 2881 */       if (action != null) {
/* 2882 */         return SwingUtilities.notifyAction(action, paramKeyStroke, paramKeyEvent, this, paramKeyEvent
/* 2883 */             .getModifiers());
/*      */       }
/*      */     } 
/* 2886 */     return false;
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
/*      */   boolean processKeyBindings(KeyEvent paramKeyEvent, boolean paramBoolean) {
/*      */     KeyStroke keyStroke1;
/* 2902 */     if (!SwingUtilities.isValidKeyEventForKeyBindings(paramKeyEvent)) {
/* 2903 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2909 */     KeyStroke keyStroke2 = null;
/*      */     
/* 2911 */     if (paramKeyEvent.getID() == 400) {
/* 2912 */       keyStroke1 = KeyStroke.getKeyStroke(paramKeyEvent.getKeyChar());
/*      */     } else {
/*      */       
/* 2915 */       keyStroke1 = KeyStroke.getKeyStroke(paramKeyEvent.getKeyCode(), paramKeyEvent.getModifiers(), !paramBoolean);
/*      */       
/* 2917 */       if (paramKeyEvent.getKeyCode() != paramKeyEvent.getExtendedKeyCode()) {
/* 2918 */         keyStroke2 = KeyStroke.getKeyStroke(paramKeyEvent.getExtendedKeyCode(), paramKeyEvent.getModifiers(), !paramBoolean);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2926 */     if (keyStroke2 != null && processKeyBinding(keyStroke2, paramKeyEvent, 0, paramBoolean)) {
/* 2927 */       return true;
/*      */     }
/* 2929 */     if (processKeyBinding(keyStroke1, paramKeyEvent, 0, paramBoolean)) {
/* 2930 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2936 */     Container container = this;
/* 2937 */     while (container != null && !(container instanceof Window) && !(container instanceof java.applet.Applet)) {
/*      */       
/* 2939 */       if (container instanceof JComponent) {
/* 2940 */         if (keyStroke2 != null && container.processKeyBinding(keyStroke2, paramKeyEvent, 1, paramBoolean))
/*      */         {
/* 2942 */           return true; } 
/* 2943 */         if (container.processKeyBinding(keyStroke1, paramKeyEvent, 1, paramBoolean))
/*      */         {
/* 2945 */           return true;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2954 */       if (container instanceof JInternalFrame && 
/* 2955 */         processKeyBindingsForAllComponents(paramKeyEvent, container, paramBoolean)) {
/* 2956 */         return true;
/*      */       }
/* 2958 */       container = container.getParent();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2965 */     if (container != null) {
/* 2966 */       return processKeyBindingsForAllComponents(paramKeyEvent, container, paramBoolean);
/*      */     }
/* 2968 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean processKeyBindingsForAllComponents(KeyEvent paramKeyEvent, Container paramContainer, boolean paramBoolean) {
/*      */     while (true) {
/* 2974 */       if (KeyboardManager.getCurrentManager().fireKeyboardAction(paramKeyEvent, paramBoolean, paramContainer))
/*      */       {
/* 2976 */         return true;
/*      */       }
/* 2978 */       if (paramContainer instanceof Popup.HeavyWeightWindow) {
/* 2979 */         paramContainer = ((Window)paramContainer).getOwner(); continue;
/*      */       }  break;
/*      */     } 
/* 2982 */     return false;
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
/*      */   public void setToolTipText(String paramString) {
/* 3003 */     String str = getToolTipText();
/* 3004 */     putClientProperty("ToolTipText", paramString);
/* 3005 */     ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
/* 3006 */     if (paramString != null) {
/* 3007 */       if (str == null) {
/* 3008 */         toolTipManager.registerComponent(this);
/*      */       }
/*      */     } else {
/* 3011 */       toolTipManager.unregisterComponent(this);
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
/*      */   public String getToolTipText() {
/* 3023 */     return (String)getClientProperty("ToolTipText");
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
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/* 3035 */     return getToolTipText();
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
/*      */   public Point getToolTipLocation(MouseEvent paramMouseEvent) {
/* 3048 */     return null;
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
/*      */   public Point getPopupLocation(MouseEvent paramMouseEvent) {
/* 3064 */     return null;
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
/*      */   public JToolTip createToolTip() {
/* 3078 */     JToolTip jToolTip = new JToolTip();
/* 3079 */     jToolTip.setComponent(this);
/* 3080 */     return jToolTip;
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
/*      */   public void scrollRectToVisible(Rectangle paramRectangle) {
/* 3094 */     int i = getX(), j = getY();
/*      */     
/* 3096 */     Container container = getParent();
/*      */     
/* 3098 */     for (; container != null && !(container instanceof JComponent) && !(container instanceof CellRendererPane); 
/*      */       
/* 3100 */       container = container.getParent()) {
/* 3101 */       Rectangle rectangle = container.getBounds();
/*      */       
/* 3103 */       i += rectangle.x;
/* 3104 */       j += rectangle.y;
/*      */     } 
/*      */     
/* 3107 */     if (container != null && !(container instanceof CellRendererPane)) {
/* 3108 */       paramRectangle.x += i;
/* 3109 */       paramRectangle.y += j;
/*      */       
/* 3111 */       ((JComponent)container).scrollRectToVisible(paramRectangle);
/* 3112 */       paramRectangle.x -= i;
/* 3113 */       paramRectangle.y -= j;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoscrolls(boolean paramBoolean) {
/* 3162 */     setFlag(25, true);
/* 3163 */     if (this.autoscrolls != paramBoolean) {
/* 3164 */       this.autoscrolls = paramBoolean;
/* 3165 */       if (paramBoolean) {
/* 3166 */         enableEvents(16L);
/* 3167 */         enableEvents(32L);
/*      */       } else {
/*      */         
/* 3170 */         Autoscroller.stop(this);
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
/*      */   public boolean getAutoscrolls() {
/* 3183 */     return this.autoscrolls;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransferHandler(TransferHandler paramTransferHandler) {
/* 3226 */     TransferHandler transferHandler = (TransferHandler)getClientProperty(ClientPropertyKey.JComponent_TRANSFER_HANDLER);
/*      */     
/* 3228 */     putClientProperty(ClientPropertyKey.JComponent_TRANSFER_HANDLER, paramTransferHandler);
/*      */     
/* 3230 */     SwingUtilities.installSwingDropTargetAsNecessary(this, paramTransferHandler);
/* 3231 */     firePropertyChange("transferHandler", transferHandler, paramTransferHandler);
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
/*      */   public TransferHandler getTransferHandler() {
/* 3244 */     return (TransferHandler)getClientProperty(ClientPropertyKey.JComponent_TRANSFER_HANDLER);
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
/*      */   TransferHandler.DropLocation dropLocationForPoint(Point paramPoint) {
/* 3259 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object setDropLocation(TransferHandler.DropLocation paramDropLocation, Object paramObject, boolean paramBoolean) {
/* 3299 */     return null;
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
/*      */   void dndDone() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processMouseEvent(MouseEvent paramMouseEvent) {
/* 3321 */     if (this.autoscrolls && paramMouseEvent.getID() == 502) {
/* 3322 */       Autoscroller.stop(this);
/*      */     }
/* 3324 */     super.processMouseEvent(paramMouseEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processMouseMotionEvent(MouseEvent paramMouseEvent) {
/* 3334 */     boolean bool = true;
/* 3335 */     if (this.autoscrolls && paramMouseEvent.getID() == 506) {
/*      */ 
/*      */       
/* 3338 */       bool = !Autoscroller.isRunning(this) ? true : false;
/* 3339 */       Autoscroller.processMouseDragged(paramMouseEvent);
/*      */     } 
/* 3341 */     if (bool) {
/* 3342 */       super.processMouseMotionEvent(paramMouseEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void superProcessMouseMotionEvent(MouseEvent paramMouseEvent) {
/* 3348 */     super.processMouseMotionEvent(paramMouseEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setCreatedDoubleBuffer(boolean paramBoolean) {
/* 3358 */     setFlag(9, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean getCreatedDoubleBuffer() {
/* 3368 */     return getFlag(9);
/*      */   }
/*      */ 
/*      */   
/*      */   final class ActionStandin
/*      */     implements Action
/*      */   {
/*      */     private final ActionListener actionListener;
/*      */     
/*      */     private final String command;
/*      */     
/*      */     private final Action action;
/*      */ 
/*      */     
/*      */     ActionStandin(ActionListener param1ActionListener, String param1String) {
/* 3383 */       this.actionListener = param1ActionListener;
/* 3384 */       if (param1ActionListener instanceof Action) {
/* 3385 */         this.action = (Action)param1ActionListener;
/*      */       } else {
/*      */         
/* 3388 */         this.action = null;
/*      */       } 
/* 3390 */       this.command = param1String;
/*      */     }
/*      */     
/*      */     public Object getValue(String param1String) {
/* 3394 */       if (param1String != null) {
/* 3395 */         if (param1String.equals("ActionCommandKey")) {
/* 3396 */           return this.command;
/*      */         }
/* 3398 */         if (this.action != null) {
/* 3399 */           return this.action.getValue(param1String);
/*      */         }
/* 3401 */         if (param1String.equals("Name")) {
/* 3402 */           return "ActionStandin";
/*      */         }
/*      */       } 
/* 3405 */       return null;
/*      */     }
/*      */     
/*      */     public boolean isEnabled() {
/* 3409 */       if (this.actionListener == null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3415 */         return false;
/*      */       }
/* 3417 */       if (this.action == null) {
/* 3418 */         return true;
/*      */       }
/* 3420 */       return this.action.isEnabled();
/*      */     }
/*      */     
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/* 3424 */       if (this.actionListener != null) {
/* 3425 */         this.actionListener.actionPerformed(param1ActionEvent);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void putValue(String param1String, Object param1Object) {}
/*      */ 
/*      */     
/*      */     public void setEnabled(boolean param1Boolean) {}
/*      */ 
/*      */     
/*      */     public void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {}
/*      */ 
/*      */     
/*      */     public void removePropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {}
/*      */   }
/*      */ 
/*      */   
/*      */   static final class IntVector
/*      */   {
/* 3446 */     int[] array = null;
/* 3447 */     int count = 0;
/* 3448 */     int capacity = 0;
/*      */     
/*      */     int size() {
/* 3451 */       return this.count;
/*      */     }
/*      */     
/*      */     int elementAt(int param1Int) {
/* 3455 */       return this.array[param1Int];
/*      */     }
/*      */     
/*      */     void addElement(int param1Int) {
/* 3459 */       if (this.count == this.capacity) {
/* 3460 */         this.capacity = (this.capacity + 2) * 2;
/* 3461 */         int[] arrayOfInt = new int[this.capacity];
/* 3462 */         if (this.count > 0) {
/* 3463 */           System.arraycopy(this.array, 0, arrayOfInt, 0, this.count);
/*      */         }
/* 3465 */         this.array = arrayOfInt;
/*      */       } 
/* 3467 */       this.array[this.count++] = param1Int;
/*      */     }
/*      */     
/*      */     void setElementAt(int param1Int1, int param1Int2) {
/* 3471 */       this.array[param1Int2] = param1Int1;
/*      */     }
/*      */   }
/*      */   
/*      */   static class KeyboardState
/*      */     implements Serializable {
/* 3477 */     private static final Object keyCodesKey = KeyboardState.class;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static JComponent.IntVector getKeyCodeArray() {
/* 3483 */       JComponent.IntVector intVector = (JComponent.IntVector)SwingUtilities.appContextGet(keyCodesKey);
/* 3484 */       if (intVector == null) {
/* 3485 */         intVector = new JComponent.IntVector();
/* 3486 */         SwingUtilities.appContextPut(keyCodesKey, intVector);
/*      */       } 
/* 3488 */       return intVector;
/*      */     }
/*      */     
/*      */     static void registerKeyPressed(int param1Int) {
/* 3492 */       JComponent.IntVector intVector = getKeyCodeArray();
/* 3493 */       int i = intVector.size();
/*      */       
/* 3495 */       for (byte b = 0; b < i; b++) {
/* 3496 */         if (intVector.elementAt(b) == -1) {
/* 3497 */           intVector.setElementAt(param1Int, b);
/*      */           return;
/*      */         } 
/*      */       } 
/* 3501 */       intVector.addElement(param1Int);
/*      */     }
/*      */     
/*      */     static void registerKeyReleased(int param1Int) {
/* 3505 */       JComponent.IntVector intVector = getKeyCodeArray();
/* 3506 */       int i = intVector.size();
/*      */       
/* 3508 */       for (byte b = 0; b < i; b++) {
/* 3509 */         if (intVector.elementAt(b) == param1Int) {
/* 3510 */           intVector.setElementAt(-1, b);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     static boolean keyIsPressed(int param1Int) {
/* 3517 */       JComponent.IntVector intVector = getKeyCodeArray();
/* 3518 */       int i = intVector.size();
/*      */       
/* 3520 */       for (byte b = 0; b < i; b++) {
/* 3521 */         if (intVector.elementAt(b) == param1Int) {
/* 3522 */           return true;
/*      */         }
/*      */       } 
/* 3525 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean shouldProcess(KeyEvent param1KeyEvent) {
/* 3533 */       switch (param1KeyEvent.getID()) {
/*      */         case 401:
/* 3535 */           if (!keyIsPressed(param1KeyEvent.getKeyCode())) {
/* 3536 */             registerKeyPressed(param1KeyEvent.getKeyCode());
/*      */           }
/* 3538 */           return true;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 402:
/* 3544 */           if (keyIsPressed(param1KeyEvent.getKeyCode()) || param1KeyEvent.getKeyCode() == 154) {
/* 3545 */             registerKeyReleased(param1KeyEvent.getKeyCode());
/* 3546 */             return true;
/*      */           } 
/* 3548 */           return false;
/*      */         case 400:
/* 3550 */           return true;
/*      */       } 
/*      */       
/* 3553 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/* 3558 */   static final RequestFocusController focusController = new RequestFocusController()
/*      */     {
/*      */ 
/*      */       
/*      */       public boolean acceptRequestFocus(Component param1Component1, Component param1Component2, boolean param1Boolean1, boolean param1Boolean2, CausedFocusEvent.Cause param1Cause)
/*      */       {
/* 3564 */         if (param1Component2 == null || !(param1Component2 instanceof JComponent)) {
/* 3565 */           return true;
/*      */         }
/*      */         
/* 3568 */         if (param1Component1 == null || !(param1Component1 instanceof JComponent)) {
/* 3569 */           return true;
/*      */         }
/*      */         
/* 3572 */         JComponent jComponent1 = (JComponent)param1Component2;
/* 3573 */         if (!jComponent1.getVerifyInputWhenFocusTarget()) {
/* 3574 */           return true;
/*      */         }
/*      */         
/* 3577 */         JComponent jComponent2 = (JComponent)param1Component1;
/* 3578 */         InputVerifier inputVerifier = jComponent2.getInputVerifier();
/*      */         
/* 3580 */         if (inputVerifier == null) {
/* 3581 */           return true;
/*      */         }
/* 3583 */         Object object = SwingUtilities.appContextGet(JComponent
/* 3584 */             .INPUT_VERIFIER_SOURCE_KEY);
/* 3585 */         if (object == jComponent2)
/*      */         {
/*      */           
/* 3588 */           return true;
/*      */         }
/* 3590 */         SwingUtilities.appContextPut(JComponent.INPUT_VERIFIER_SOURCE_KEY, jComponent2);
/*      */         
/*      */         try {
/* 3593 */           return inputVerifier.shouldYieldFocus(jComponent2);
/*      */         } finally {
/* 3595 */           if (object != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3601 */             SwingUtilities.appContextPut(JComponent
/* 3602 */                 .INPUT_VERIFIER_SOURCE_KEY, object);
/*      */           } else {
/* 3604 */             SwingUtilities.appContextRemove(JComponent
/* 3605 */                 .INPUT_VERIFIER_SOURCE_KEY);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
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
/*      */   public void enable() {
/* 3622 */     if (isEnabled() != true) {
/* 3623 */       super.enable();
/* 3624 */       if (this.accessibleContext != null) {
/* 3625 */         this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
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
/*      */   @Deprecated
/*      */   public void disable() {
/* 3638 */     if (isEnabled()) {
/* 3639 */       super.disable();
/* 3640 */       if (this.accessibleContext != null) {
/* 3641 */         this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.ENABLED, null);
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
/*      */   public abstract class AccessibleJComponent
/*      */     extends Container.AccessibleAWTContainer
/*      */     implements AccessibleExtendedComponent
/*      */   {
/*      */     private volatile transient int propertyListenersCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     protected FocusListener accessibleFocusHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AccessibleJComponent() {
/* 3679 */       this.propertyListenersCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3685 */       this.accessibleFocusHandler = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleContainerHandler
/*      */       implements ContainerListener
/*      */     {
/*      */       public void componentAdded(ContainerEvent param2ContainerEvent) {
/* 3695 */         Component component = param2ContainerEvent.getChild();
/* 3696 */         if (component != null && component instanceof Accessible)
/* 3697 */           JComponent.AccessibleJComponent.this.firePropertyChange("AccessibleChild", null, component
/*      */               
/* 3699 */               .getAccessibleContext()); 
/*      */       }
/*      */       
/*      */       public void componentRemoved(ContainerEvent param2ContainerEvent) {
/* 3703 */         Component component = param2ContainerEvent.getChild();
/* 3704 */         if (component != null && component instanceof Accessible) {
/* 3705 */           JComponent.AccessibleJComponent.this.firePropertyChange("AccessibleChild", component
/*      */               
/* 3707 */               .getAccessibleContext(), null);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleFocusHandler
/*      */       implements FocusListener
/*      */     {
/*      */       public void focusGained(FocusEvent param2FocusEvent) {
/* 3719 */         if (JComponent.this.accessibleContext != null) {
/* 3720 */           JComponent.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.FOCUSED);
/*      */         }
/*      */       }
/*      */ 
/*      */       
/*      */       public void focusLost(FocusEvent param2FocusEvent) {
/* 3726 */         if (JComponent.this.accessibleContext != null) {
/* 3727 */           JComponent.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.FOCUSED, null);
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
/*      */     public void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/* 3741 */       super.addPropertyChangeListener(param1PropertyChangeListener);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removePropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/* 3752 */       super.removePropertyChangeListener(param1PropertyChangeListener);
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
/*      */     protected String getBorderTitle(Border param1Border) {
/* 3768 */       if (param1Border instanceof TitledBorder)
/* 3769 */         return ((TitledBorder)param1Border).getTitle(); 
/* 3770 */       if (param1Border instanceof CompoundBorder) {
/* 3771 */         String str = getBorderTitle(((CompoundBorder)param1Border).getInsideBorder());
/* 3772 */         if (str == null) {
/* 3773 */           str = getBorderTitle(((CompoundBorder)param1Border).getOutsideBorder());
/*      */         }
/* 3775 */         return str;
/*      */       } 
/* 3777 */       return null;
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
/*      */     public String getAccessibleName() {
/* 3798 */       String str = this.accessibleName;
/*      */ 
/*      */ 
/*      */       
/* 3802 */       if (str == null) {
/* 3803 */         str = (String)JComponent.this.getClientProperty("AccessibleName");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3808 */       if (str == null) {
/* 3809 */         str = getBorderTitle(JComponent.this.getBorder());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3814 */       if (str == null) {
/* 3815 */         Object object = JComponent.this.getClientProperty("labeledBy");
/* 3816 */         if (object instanceof Accessible) {
/* 3817 */           AccessibleContext accessibleContext = ((Accessible)object).getAccessibleContext();
/* 3818 */           if (accessibleContext != null) {
/* 3819 */             str = accessibleContext.getAccessibleName();
/*      */           }
/*      */         } 
/*      */       } 
/* 3823 */       return str;
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
/*      */     public String getAccessibleDescription() {
/* 3842 */       String str = this.accessibleDescription;
/*      */ 
/*      */ 
/*      */       
/* 3846 */       if (str == null) {
/* 3847 */         str = (String)JComponent.this.getClientProperty("AccessibleDescription");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 3852 */       if (str == null) {
/*      */         try {
/* 3854 */           str = getToolTipText();
/* 3855 */         } catch (Exception exception) {}
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3870 */       if (str == null) {
/* 3871 */         Object object = JComponent.this.getClientProperty("labeledBy");
/* 3872 */         if (object instanceof Accessible) {
/* 3873 */           AccessibleContext accessibleContext = ((Accessible)object).getAccessibleContext();
/* 3874 */           if (accessibleContext != null) {
/* 3875 */             str = accessibleContext.getAccessibleDescription();
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 3880 */       return str;
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
/* 3891 */       return AccessibleRole.SWING_COMPONENT;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AccessibleStateSet getAccessibleStateSet() {
/* 3902 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 3903 */       if (JComponent.this.isOpaque()) {
/* 3904 */         accessibleStateSet.add(AccessibleState.OPAQUE);
/*      */       }
/* 3906 */       return accessibleStateSet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/* 3917 */       return super.getAccessibleChildrenCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 3927 */       return super.getAccessibleChild(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     AccessibleExtendedComponent getAccessibleExtendedComponent() {
/* 3938 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getToolTipText() {
/* 3949 */       return JComponent.this.getToolTipText();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getTitledBorderText() {
/* 3960 */       Border border = JComponent.this.getBorder();
/* 3961 */       if (border instanceof TitledBorder) {
/* 3962 */         return ((TitledBorder)border).getTitle();
/*      */       }
/* 3964 */       return null;
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
/*      */     public AccessibleKeyBinding getAccessibleKeyBinding() {
/* 3978 */       Object object = JComponent.this.getClientProperty("labeledBy");
/* 3979 */       if (object instanceof Accessible) {
/* 3980 */         AccessibleContext accessibleContext = ((Accessible)object).getAccessibleContext();
/* 3981 */         if (accessibleContext != null) {
/* 3982 */           AccessibleComponent accessibleComponent = accessibleContext.getAccessibleComponent();
/* 3983 */           if (!(accessibleComponent instanceof AccessibleExtendedComponent))
/* 3984 */             return null; 
/* 3985 */           return ((AccessibleExtendedComponent)accessibleComponent).getAccessibleKeyBinding();
/*      */         } 
/*      */       } 
/* 3988 */       return null;
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
/*      */   private ArrayTable getClientProperties() {
/* 4004 */     if (this.clientProperties == null) {
/* 4005 */       this.clientProperties = new ArrayTable();
/*      */     }
/* 4007 */     return this.clientProperties;
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
/*      */   public final Object getClientProperty(Object paramObject) {
/* 4021 */     if (paramObject == SwingUtilities2.AA_TEXT_PROPERTY_KEY)
/* 4022 */       return this.aaTextInfo; 
/* 4023 */     if (paramObject == SwingUtilities2.COMPONENT_UI_PROPERTY_KEY) {
/* 4024 */       return this.ui;
/*      */     }
/* 4026 */     if (this.clientProperties == null) {
/* 4027 */       return null;
/*      */     }
/* 4029 */     synchronized (this.clientProperties) {
/* 4030 */       return this.clientProperties.get(paramObject);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void putClientProperty(Object paramObject1, Object paramObject2) {
/*      */     Object object;
/* 4064 */     if (paramObject1 == SwingUtilities2.AA_TEXT_PROPERTY_KEY) {
/* 4065 */       this.aaTextInfo = paramObject2;
/*      */       return;
/*      */     } 
/* 4068 */     if (paramObject2 == null && this.clientProperties == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 4073 */     ArrayTable arrayTable = getClientProperties();
/*      */     
/* 4075 */     synchronized (arrayTable) {
/* 4076 */       object = arrayTable.get(paramObject1);
/* 4077 */       if (paramObject2 != null) {
/* 4078 */         arrayTable.put(paramObject1, paramObject2);
/* 4079 */       } else if (object != null) {
/* 4080 */         arrayTable.remove(paramObject1);
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 4086 */     clientPropertyChanged(paramObject1, object, paramObject2);
/* 4087 */     firePropertyChange(paramObject1.toString(), object, paramObject2);
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
/*      */   void clientPropertyChanged(Object paramObject1, Object paramObject2, Object paramObject3) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setUIProperty(String paramString, Object paramObject) {
/* 4108 */     if (paramString == "opaque") {
/* 4109 */       if (!getFlag(24)) {
/* 4110 */         setOpaque(((Boolean)paramObject).booleanValue());
/* 4111 */         setFlag(24, false);
/*      */       } 
/* 4113 */     } else if (paramString == "autoscrolls") {
/* 4114 */       if (!getFlag(25)) {
/* 4115 */         setAutoscrolls(((Boolean)paramObject).booleanValue());
/* 4116 */         setFlag(25, false);
/*      */       } 
/* 4118 */     } else if (paramString == "focusTraversalKeysForward") {
/* 4119 */       if (!getFlag(26)) {
/* 4120 */         super.setFocusTraversalKeys(0, (Set<? extends AWTKeyStroke>)paramObject);
/*      */       
/*      */       }
/*      */     }
/* 4124 */     else if (paramString == "focusTraversalKeysBackward") {
/* 4125 */       if (!getFlag(27)) {
/* 4126 */         super.setFocusTraversalKeys(1, (Set<? extends AWTKeyStroke>)paramObject);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 4131 */       throw new IllegalArgumentException("property \"" + paramString + "\" cannot be set using this method");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFocusTraversalKeys(int paramInt, Set<? extends AWTKeyStroke> paramSet) {
/* 4168 */     if (paramInt == 0) {
/* 4169 */       setFlag(26, true);
/* 4170 */     } else if (paramInt == 1) {
/* 4171 */       setFlag(27, true);
/*      */     } 
/* 4173 */     super.setFocusTraversalKeys(paramInt, paramSet);
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
/*      */   public static boolean isLightweightComponent(Component paramComponent) {
/* 4189 */     return paramComponent.getPeer() instanceof java.awt.peer.LightweightPeer;
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
/*      */   @Deprecated
/*      */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 4207 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public Rectangle getBounds(Rectangle paramRectangle) {
/* 4225 */     if (paramRectangle == null) {
/* 4226 */       return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*      */     }
/*      */     
/* 4229 */     paramRectangle.setBounds(getX(), getY(), getWidth(), getHeight());
/* 4230 */     return paramRectangle;
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
/*      */   public Dimension getSize(Dimension paramDimension) {
/* 4247 */     if (paramDimension == null) {
/* 4248 */       return new Dimension(getWidth(), getHeight());
/*      */     }
/*      */     
/* 4251 */     paramDimension.setSize(getWidth(), getHeight());
/* 4252 */     return paramDimension;
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
/*      */   public Point getLocation(Point paramPoint) {
/* 4269 */     if (paramPoint == null) {
/* 4270 */       return new Point(getX(), getY());
/*      */     }
/*      */     
/* 4273 */     paramPoint.setLocation(getX(), getY());
/* 4274 */     return paramPoint;
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
/*      */   public int getX() {
/* 4288 */     return super.getX();
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
/*      */   public int getY() {
/* 4300 */     return super.getY();
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
/*      */   public int getWidth() {
/* 4312 */     return super.getWidth();
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
/*      */   public int getHeight() {
/* 4324 */     return super.getHeight();
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
/*      */   public boolean isOpaque() {
/* 4342 */     return getFlag(3);
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
/*      */   public void setOpaque(boolean paramBoolean) {
/* 4363 */     boolean bool = getFlag(3);
/* 4364 */     setFlag(3, paramBoolean);
/* 4365 */     setFlag(24, true);
/* 4366 */     firePropertyChange("opaque", bool, paramBoolean);
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
/*      */   boolean rectangleIsObscured(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 4387 */     int i = getComponentCount();
/*      */     
/* 4389 */     for (byte b = 0; b < i; b++) {
/* 4390 */       Component component = getComponent(b);
/*      */ 
/*      */       
/* 4393 */       int j = component.getX();
/* 4394 */       int k = component.getY();
/* 4395 */       int m = component.getWidth();
/* 4396 */       int n = component.getHeight();
/*      */       
/* 4398 */       if (paramInt1 >= j && paramInt1 + paramInt3 <= j + m && paramInt2 >= k && paramInt2 + paramInt4 <= k + n && component
/* 4399 */         .isVisible()) {
/*      */         
/* 4401 */         if (component instanceof JComponent)
/*      */         {
/*      */ 
/*      */           
/* 4405 */           return component.isOpaque();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 4410 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4415 */     return false;
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
/*      */   static final void computeVisibleRect(Component paramComponent, Rectangle paramRectangle) {
/* 4433 */     Container container = paramComponent.getParent();
/* 4434 */     Rectangle rectangle = paramComponent.getBounds();
/*      */     
/* 4436 */     if (container == null || container instanceof Window || container instanceof java.applet.Applet) {
/* 4437 */       paramRectangle.setBounds(0, 0, rectangle.width, rectangle.height);
/*      */     } else {
/* 4439 */       computeVisibleRect(container, paramRectangle);
/* 4440 */       paramRectangle.x -= rectangle.x;
/* 4441 */       paramRectangle.y -= rectangle.y;
/* 4442 */       SwingUtilities.computeIntersection(0, 0, rectangle.width, rectangle.height, paramRectangle);
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
/*      */   public void computeVisibleRect(Rectangle paramRectangle) {
/* 4460 */     computeVisibleRect(this, paramRectangle);
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
/*      */   public Rectangle getVisibleRect() {
/* 4473 */     Rectangle rectangle = new Rectangle();
/*      */     
/* 4475 */     computeVisibleRect(rectangle);
/* 4476 */     return rectangle;
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
/*      */   public void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 4491 */     super.firePropertyChange(paramString, paramBoolean1, paramBoolean2);
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
/*      */   public void firePropertyChange(String paramString, int paramInt1, int paramInt2) {
/* 4507 */     super.firePropertyChange(paramString, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void firePropertyChange(String paramString, char paramChar1, char paramChar2) {
/* 4513 */     super.firePropertyChange(paramString, paramChar1, paramChar2);
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
/*      */   protected void fireVetoableChange(String paramString, Object paramObject1, Object paramObject2) throws PropertyVetoException {
/* 4531 */     if (this.vetoableChangeSupport == null) {
/*      */       return;
/*      */     }
/* 4534 */     this.vetoableChangeSupport.fireVetoableChange(paramString, paramObject1, paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener) {
/* 4545 */     if (this.vetoableChangeSupport == null) {
/* 4546 */       this.vetoableChangeSupport = new VetoableChangeSupport(this);
/*      */     }
/* 4548 */     this.vetoableChangeSupport.addVetoableChangeListener(paramVetoableChangeListener);
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
/*      */   public synchronized void removeVetoableChangeListener(VetoableChangeListener paramVetoableChangeListener) {
/* 4560 */     if (this.vetoableChangeSupport == null) {
/*      */       return;
/*      */     }
/* 4563 */     this.vetoableChangeSupport.removeVetoableChangeListener(paramVetoableChangeListener);
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
/*      */   public synchronized VetoableChangeListener[] getVetoableChangeListeners() {
/* 4581 */     if (this.vetoableChangeSupport == null) {
/* 4582 */       return new VetoableChangeListener[0];
/*      */     }
/* 4584 */     return this.vetoableChangeSupport.getVetoableChangeListeners();
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
/*      */   public Container getTopLevelAncestor() {
/* 4598 */     for (JComponent jComponent = this; jComponent != null; container = jComponent.getParent()) {
/* 4599 */       Container container; if (jComponent instanceof Window || jComponent instanceof java.applet.Applet) {
/* 4600 */         return jComponent;
/*      */       }
/*      */     } 
/* 4603 */     return null;
/*      */   }
/*      */   
/*      */   private AncestorNotifier getAncestorNotifier() {
/* 4607 */     return (AncestorNotifier)
/* 4608 */       getClientProperty(ClientPropertyKey.JComponent_ANCESTOR_NOTIFIER);
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
/*      */   public void addAncestorListener(AncestorListener paramAncestorListener) {
/* 4622 */     AncestorNotifier ancestorNotifier = getAncestorNotifier();
/* 4623 */     if (ancestorNotifier == null) {
/* 4624 */       ancestorNotifier = new AncestorNotifier(this);
/* 4625 */       putClientProperty(ClientPropertyKey.JComponent_ANCESTOR_NOTIFIER, ancestorNotifier);
/*      */     } 
/*      */     
/* 4628 */     ancestorNotifier.addAncestorListener(paramAncestorListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAncestorListener(AncestorListener paramAncestorListener) {
/* 4639 */     AncestorNotifier ancestorNotifier = getAncestorNotifier();
/* 4640 */     if (ancestorNotifier == null) {
/*      */       return;
/*      */     }
/* 4643 */     ancestorNotifier.removeAncestorListener(paramAncestorListener);
/* 4644 */     if ((ancestorNotifier.listenerList.getListenerList()).length == 0) {
/* 4645 */       ancestorNotifier.removeAllListeners();
/* 4646 */       putClientProperty(ClientPropertyKey.JComponent_ANCESTOR_NOTIFIER, (Object)null);
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
/*      */   public AncestorListener[] getAncestorListeners() {
/* 4664 */     AncestorNotifier ancestorNotifier = getAncestorNotifier();
/* 4665 */     if (ancestorNotifier == null) {
/* 4666 */       return new AncestorListener[0];
/*      */     }
/* 4668 */     return ancestorNotifier.getAncestorListeners();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends EventListener> T[] getListeners(Class<T> paramClass) {
/*      */     Object[] arrayOfObject;
/* 4708 */     if (paramClass == AncestorListener.class) {
/*      */       
/* 4710 */       EventListener[] arrayOfEventListener = (EventListener[])getAncestorListeners();
/*      */     }
/* 4712 */     else if (paramClass == VetoableChangeListener.class) {
/*      */       
/* 4714 */       EventListener[] arrayOfEventListener = (EventListener[])getVetoableChangeListeners();
/*      */     }
/* 4716 */     else if (paramClass == PropertyChangeListener.class) {
/*      */       
/* 4718 */       EventListener[] arrayOfEventListener = (EventListener[])getPropertyChangeListeners();
/*      */     } else {
/*      */       
/* 4721 */       arrayOfObject = this.listenerList.getListeners((Class)paramClass);
/*      */     } 
/*      */     
/* 4724 */     if (arrayOfObject.length == 0) {
/* 4725 */       return super.getListeners(paramClass);
/*      */     }
/* 4727 */     return (T[])arrayOfObject;
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
/*      */   public void addNotify() {
/* 4740 */     super.addNotify();
/* 4741 */     firePropertyChange("ancestor", (Object)null, getParent());
/*      */     
/* 4743 */     registerWithKeyboardManager(false);
/* 4744 */     registerNextFocusableComponent();
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
/*      */   public void removeNotify() {
/* 4758 */     super.removeNotify();
/*      */ 
/*      */ 
/*      */     
/* 4762 */     firePropertyChange("ancestor", getParent(), (Object)null);
/*      */     
/* 4764 */     unregisterWithKeyboardManager();
/* 4765 */     deregisterNextFocusableComponent();
/*      */     
/* 4767 */     if (getCreatedDoubleBuffer()) {
/* 4768 */       RepaintManager.currentManager(this).resetDoubleBuffer();
/* 4769 */       setCreatedDoubleBuffer(false);
/*      */     } 
/* 4771 */     if (this.autoscrolls) {
/* 4772 */       Autoscroller.stop(this);
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
/*      */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 4792 */     RepaintManager.currentManager(SunToolkit.targetToAppContext(this))
/* 4793 */       .addDirtyRegion(this, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public void repaint(Rectangle paramRectangle) {
/* 4808 */     repaint(0L, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void revalidate() {
/* 4838 */     if (getParent() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4847 */     if (SunToolkit.isDispatchThreadForAppContext(this)) {
/* 4848 */       invalidate();
/* 4849 */       RepaintManager.currentManager(this).addInvalidComponent(this);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 4855 */       if (this.revalidateRunnableScheduled.getAndSet(true)) {
/*      */         return;
/*      */       }
/* 4858 */       SunToolkit.executeOnEventHandlerThread(this, () -> {
/*      */             this.revalidateRunnableScheduled.set(false);
/*      */             revalidate();
/*      */           });
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
/*      */   public boolean isValidateRoot() {
/* 4880 */     return false;
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
/*      */   public boolean isOptimizedDrawingEnabled() {
/* 4895 */     return true;
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
/*      */   protected boolean isPaintingOrigin() {
/* 4915 */     return false;
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
/*      */   public void paintImmediately(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 4939 */     Container container = this;
/*      */ 
/*      */     
/* 4942 */     if (!isShowing()) {
/*      */       return;
/*      */     }
/*      */     
/* 4946 */     JComponent jComponent = SwingUtilities.getPaintingOrigin(this);
/* 4947 */     if (jComponent != null) {
/* 4948 */       Rectangle rectangle = SwingUtilities.convertRectangle(container, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4), jComponent);
/*      */       
/* 4950 */       jComponent.paintImmediately(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       
/*      */       return;
/*      */     } 
/* 4954 */     while (!container.isOpaque()) {
/* 4955 */       Container container1 = container.getParent();
/* 4956 */       if (container1 != null) {
/* 4957 */         paramInt1 += container.getX();
/* 4958 */         paramInt2 += container.getY();
/* 4959 */         container = container1;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4964 */         if (!(container instanceof JComponent))
/*      */           break; 
/*      */       } 
/*      */     } 
/* 4968 */     if (container instanceof JComponent) {
/* 4969 */       ((JComponent)container)._paintImmediately(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } else {
/* 4971 */       container.repaint(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintImmediately(Rectangle paramRectangle) {
/* 4981 */     paintImmediately(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
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
/*      */   boolean alwaysOnTop() {
/* 4994 */     return false;
/*      */   }
/*      */   
/*      */   void setPaintingChild(Component paramComponent) {
/* 4998 */     this.paintingChild = paramComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void _paintImmediately(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 5007 */     int k = 0, m = 0;
/*      */     
/* 5009 */     boolean bool3 = false;
/*      */     
/* 5011 */     JComponent jComponent1 = null;
/* 5012 */     JComponent jComponent2 = this;
/*      */     
/* 5014 */     RepaintManager repaintManager = RepaintManager.currentManager(this);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5019 */     ArrayList<JComponent> arrayList = new ArrayList(7);
/* 5020 */     byte b = -1;
/* 5021 */     byte b1 = 0;
/*      */     
/* 5023 */     int j = 0, i = j; boolean bool2 = i, bool1 = bool2;
/*      */     
/* 5025 */     Rectangle rectangle = fetchRectangle();
/* 5026 */     rectangle.x = paramInt1;
/* 5027 */     rectangle.y = paramInt2;
/* 5028 */     rectangle.width = paramInt3;
/* 5029 */     rectangle.height = paramInt4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5034 */     boolean bool4 = (alwaysOnTop() && isOpaque()) ? true : false;
/* 5035 */     if (bool4) {
/* 5036 */       SwingUtilities.computeIntersection(0, 0, getWidth(), getHeight(), rectangle);
/*      */       
/* 5038 */       if (rectangle.width == 0) {
/* 5039 */         recycleRectangle(rectangle);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 5044 */     Container container = this; Component component = null;
/* 5045 */     for (; container != null && !(container instanceof Window) && !(container instanceof java.applet.Applet); 
/* 5046 */       component = container, container = container.getParent()) {
/* 5047 */       JComponent jComponent = (container instanceof JComponent) ? (JComponent)container : null;
/*      */       
/* 5049 */       arrayList.add((JComponent)container);
/* 5050 */       if (!bool4 && jComponent != null && !jComponent.isOptimizedDrawingEnabled()) {
/*      */         boolean bool;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5061 */         if (container != this) {
/* 5062 */           if (jComponent.isPaintingOrigin()) {
/* 5063 */             bool = true;
/*      */           } else {
/*      */             
/* 5066 */             Component[] arrayOfComponent = container.getComponents();
/* 5067 */             byte b2 = 0;
/* 5068 */             for (; b2 < arrayOfComponent.length && 
/* 5069 */               arrayOfComponent[b2] != component; b2++);
/*      */             
/* 5071 */             switch (jComponent.getObscuredState(b2, rectangle.x, rectangle.y, rectangle.width, rectangle.height)) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               case 0:
/* 5077 */                 bool = false;
/*      */                 break;
/*      */               case 2:
/* 5080 */                 recycleRectangle(rectangle);
/*      */                 return;
/*      */               default:
/* 5083 */                 bool = true;
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } 
/*      */         } else {
/* 5089 */           bool = false;
/*      */         } 
/*      */         
/* 5092 */         if (bool) {
/*      */ 
/*      */           
/* 5095 */           jComponent2 = jComponent;
/* 5096 */           b = b1;
/* 5097 */           k = m = 0;
/* 5098 */           bool3 = false;
/*      */         } 
/*      */       } 
/* 5101 */       b1++;
/*      */ 
/*      */ 
/*      */       
/* 5105 */       if (repaintManager.isDoubleBufferingEnabled() && jComponent != null && jComponent
/* 5106 */         .isDoubleBuffered()) {
/* 5107 */         bool3 = true;
/* 5108 */         jComponent1 = jComponent;
/*      */       } 
/*      */ 
/*      */       
/* 5112 */       if (!bool4) {
/* 5113 */         int n = container.getX();
/* 5114 */         int i1 = container.getY();
/* 5115 */         i = container.getWidth();
/* 5116 */         j = container.getHeight();
/* 5117 */         SwingUtilities.computeIntersection(bool1, bool2, i, j, rectangle);
/* 5118 */         rectangle.x += n;
/* 5119 */         rectangle.y += i1;
/* 5120 */         k += n;
/* 5121 */         m += i1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5126 */     if (container == null || container.getPeer() == null || rectangle.width <= 0 || rectangle.height <= 0) {
/*      */ 
/*      */       
/* 5129 */       recycleRectangle(rectangle);
/*      */       
/*      */       return;
/*      */     } 
/* 5133 */     jComponent2.setFlag(13, true);
/*      */     
/* 5135 */     rectangle.x -= k;
/* 5136 */     rectangle.y -= m;
/*      */ 
/*      */ 
/*      */     
/* 5140 */     if (jComponent2 != this) {
/*      */       
/* 5142 */       byte b2 = b;
/* 5143 */       for (; b2 > 0; b2--) {
/* 5144 */         Component component1 = arrayList.get(b2);
/* 5145 */         if (component1 instanceof JComponent)
/* 5146 */           ((JComponent)component1).setPaintingChild(arrayList.get(b2 - 1)); 
/*      */       } 
/*      */     } 
/*      */     try {
/*      */       Graphics graphics;
/* 5151 */       if ((graphics = safelyGetGraphics(jComponent2, container)) != null) {
/*      */         try {
/* 5153 */           if (bool3) {
/* 5154 */             RepaintManager repaintManager1 = RepaintManager.currentManager(jComponent1);
/*      */             
/* 5156 */             repaintManager1.beginPaint();
/*      */             try {
/* 5158 */               repaintManager1.paint(jComponent2, jComponent1, graphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */             
/*      */             }
/*      */             finally {
/*      */ 
/*      */               
/* 5164 */               repaintManager1.endPaint();
/*      */             } 
/*      */           } else {
/* 5167 */             graphics.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */             
/* 5169 */             jComponent2.paint(graphics);
/*      */           } 
/*      */         } finally {
/* 5172 */           graphics.dispose();
/*      */         }
/*      */       
/*      */       }
/*      */     } finally {
/*      */       
/* 5178 */       if (jComponent2 != this) {
/*      */         
/* 5180 */         byte b2 = b;
/* 5181 */         for (; b2 > 0; b2--) {
/* 5182 */           Component component1 = arrayList.get(b2);
/* 5183 */           if (component1 instanceof JComponent) {
/* 5184 */             ((JComponent)component1).setPaintingChild((Component)null);
/*      */           }
/*      */         } 
/*      */       } 
/* 5188 */       jComponent2.setFlag(13, false);
/*      */     } 
/* 5190 */     recycleRectangle(rectangle);
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
/*      */   void paintToOffscreen(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*      */     try {
/* 5203 */       setFlag(1, true);
/* 5204 */       if (paramInt2 + paramInt4 < paramInt6 || paramInt1 + paramInt3 < paramInt5) {
/* 5205 */         setFlag(2, true);
/*      */       }
/* 5207 */       if (getFlag(13)) {
/*      */ 
/*      */         
/* 5210 */         paint(paramGraphics);
/*      */       } else {
/*      */         
/* 5213 */         if (!rectangleIsObscured(paramInt1, paramInt2, paramInt3, paramInt4)) {
/* 5214 */           paintComponent(paramGraphics);
/* 5215 */           paintBorder(paramGraphics);
/*      */         } 
/* 5217 */         paintChildren(paramGraphics);
/*      */       } 
/*      */     } finally {
/* 5220 */       setFlag(1, false);
/* 5221 */       setFlag(2, false);
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
/*      */   private int getObscuredState(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 5236 */     boolean bool = false;
/* 5237 */     Rectangle rectangle = fetchRectangle();
/*      */     
/* 5239 */     for (int i = paramInt1 - 1; i >= 0; i--) {
/* 5240 */       boolean bool1; Component component = getComponent(i);
/* 5241 */       if (!component.isVisible()) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 5246 */       if (component instanceof JComponent) {
/* 5247 */         bool1 = component.isOpaque();
/* 5248 */         if (!bool1 && 
/* 5249 */           bool == true) {
/*      */           continue;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 5255 */         bool1 = true;
/*      */       } 
/* 5257 */       Rectangle rectangle1 = component.getBounds(rectangle);
/* 5258 */       if (bool1 && paramInt2 >= rectangle1.x && paramInt2 + paramInt4 <= rectangle1.x + rectangle1.width && paramInt3 >= rectangle1.y && paramInt3 + paramInt5 <= rectangle1.y + rectangle1.height) {
/*      */ 
/*      */ 
/*      */         
/* 5262 */         recycleRectangle(rectangle);
/* 5263 */         return 2;
/*      */       } 
/* 5265 */       if (!bool && paramInt2 + paramInt4 > rectangle1.x && paramInt3 + paramInt5 > rectangle1.y && paramInt2 < rectangle1.x + rectangle1.width && paramInt3 < rectangle1.y + rectangle1.height)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 5270 */         bool = true; } 
/*      */       continue;
/*      */     } 
/* 5273 */     recycleRectangle(rectangle);
/* 5274 */     return bool;
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
/*      */   boolean checkIfChildObscuredBySibling() {
/* 5286 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setFlag(int paramInt, boolean paramBoolean) {
/* 5291 */     if (paramBoolean) {
/* 5292 */       this.flags |= 1 << paramInt;
/*      */     } else {
/* 5294 */       this.flags &= 1 << paramInt ^ 0xFFFFFFFF;
/*      */     } 
/*      */   }
/*      */   private boolean getFlag(int paramInt) {
/* 5298 */     int i = 1 << paramInt;
/* 5299 */     return ((this.flags & i) == i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void setWriteObjCounter(JComponent paramJComponent, byte paramByte) {
/* 5305 */     paramJComponent.flags = paramJComponent.flags & 0xFFC03FFF | paramByte << 14;
/*      */   }
/*      */   
/*      */   static byte getWriteObjCounter(JComponent paramJComponent) {
/* 5309 */     return (byte)(paramJComponent.flags >> 14 & 0xFF);
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
/*      */   public void setDoubleBuffered(boolean paramBoolean) {
/* 5325 */     setFlag(0, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDoubleBuffered() {
/* 5334 */     return getFlag(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JRootPane getRootPane() {
/* 5344 */     return SwingUtilities.getRootPane(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void compWriteObjectNotify() {
/* 5355 */     byte b = getWriteObjCounter(this);
/* 5356 */     setWriteObjCounter(this, (byte)(b + 1));
/* 5357 */     if (b != 0) {
/*      */       return;
/*      */     }
/*      */     
/* 5361 */     uninstallUIAndProperties();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5370 */     if (getToolTipText() != null || this instanceof javax.swing.table.JTableHeader)
/*      */     {
/* 5372 */       ToolTipManager.sharedInstance().unregisterComponent(this);
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
/*      */   private class ReadObjectCallback
/*      */     implements ObjectInputValidation
/*      */   {
/* 5394 */     private final Vector<JComponent> roots = new Vector<>(1);
/*      */     private final ObjectInputStream inputStream;
/*      */     
/*      */     ReadObjectCallback(ObjectInputStream param1ObjectInputStream) throws Exception {
/* 5398 */       this.inputStream = param1ObjectInputStream;
/* 5399 */       param1ObjectInputStream.registerValidation(this, 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void validateObject() throws InvalidObjectException {
/*      */       try {
/* 5410 */         for (JComponent jComponent : this.roots) {
/* 5411 */           SwingUtilities.updateComponentTreeUI(jComponent);
/*      */         }
/*      */       } finally {
/*      */         
/* 5415 */         JComponent.readObjectCallbacks.remove(this.inputStream);
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
/*      */     private void registerComponent(JComponent param1JComponent) {
/* 5430 */       for (JComponent jComponent1 : this.roots) {
/* 5431 */         for (JComponent jComponent2 = param1JComponent; jComponent2 != null; container = jComponent2.getParent()) {
/* 5432 */           Container container; if (jComponent2 == jComponent1) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5442 */       for (byte b = 0; b < this.roots.size(); b++) {
/* 5443 */         JComponent jComponent = this.roots.elementAt(b);
/* 5444 */         for (Container container = jComponent.getParent(); container != null; container = container.getParent()) {
/* 5445 */           if (container == param1JComponent) {
/* 5446 */             this.roots.removeElementAt(b--);
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 5452 */       this.roots.addElement(param1JComponent);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 5467 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5475 */     ReadObjectCallback readObjectCallback = readObjectCallbacks.get(paramObjectInputStream);
/* 5476 */     if (readObjectCallback == null) {
/*      */       try {
/* 5478 */         readObjectCallbacks.put(paramObjectInputStream, readObjectCallback = new ReadObjectCallback(paramObjectInputStream));
/*      */       }
/* 5480 */       catch (Exception exception) {
/* 5481 */         throw new IOException(exception.toString());
/*      */       } 
/*      */     }
/* 5484 */     readObjectCallback.registerComponent(this);
/*      */ 
/*      */     
/* 5487 */     int i = paramObjectInputStream.readInt();
/* 5488 */     if (i > 0) {
/* 5489 */       this.clientProperties = new ArrayTable();
/* 5490 */       for (byte b = 0; b < i; b++) {
/* 5491 */         this.clientProperties.put(paramObjectInputStream.readObject(), paramObjectInputStream
/* 5492 */             .readObject());
/*      */       }
/*      */     } 
/* 5495 */     if (getToolTipText() != null) {
/* 5496 */       ToolTipManager.sharedInstance().registerComponent(this);
/*      */     }
/* 5498 */     setWriteObjCounter(this, (byte)0);
/* 5499 */     this.revalidateRunnableScheduled = new AtomicBoolean(false);
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 5515 */     paramObjectOutputStream.defaultWriteObject();
/* 5516 */     if (getUIClassID().equals("ComponentUI")) {
/* 5517 */       byte b = getWriteObjCounter(this);
/* 5518 */       b = (byte)(b - 1); setWriteObjCounter(this, b);
/* 5519 */       if (b == 0 && this.ui != null) {
/* 5520 */         this.ui.installUI(this);
/*      */       }
/*      */     } 
/* 5523 */     ArrayTable.writeArrayTable(paramObjectOutputStream, this.clientProperties);
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
/*      */   protected String paramString() {
/* 5539 */     String str1 = isPreferredSizeSet() ? getPreferredSize().toString() : "";
/*      */     
/* 5541 */     String str2 = isMinimumSizeSet() ? getMinimumSize().toString() : "";
/*      */     
/* 5543 */     String str3 = isMaximumSizeSet() ? getMaximumSize().toString() : "";
/*      */     
/* 5545 */     String str4 = (this.border == null) ? "" : ((this.border == this) ? "this" : this.border.toString());
/*      */     
/* 5547 */     return super.paramString() + ",alignmentX=" + this.alignmentX + ",alignmentY=" + this.alignmentY + ",border=" + str4 + ",flags=" + this.flags + ",maximumSize=" + str3 + ",minimumSize=" + str2 + ",preferredSize=" + str1;
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
/*      */   @Deprecated
/*      */   public void hide() {
/* 5563 */     boolean bool = isShowing();
/* 5564 */     super.hide();
/* 5565 */     if (bool) {
/* 5566 */       Container container = getParent();
/* 5567 */       if (container != null) {
/* 5568 */         Rectangle rectangle = getBounds();
/* 5569 */         container.repaint(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */       } 
/* 5571 */       revalidate();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */