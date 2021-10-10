/*      */ package javax.swing.plaf.basic;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.event.WindowFocusListener;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyVetoException;
/*      */ import javax.swing.DefaultDesktopManager;
/*      */ import javax.swing.DesktopManager;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.InputMap;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JInternalFrame;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.LookAndFeel;
/*      */ import javax.swing.RootPaneContainer;
/*      */ import javax.swing.SwingConstants;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.InternalFrameEvent;
/*      */ import javax.swing.event.InternalFrameListener;
/*      */ import javax.swing.event.MouseInputAdapter;
/*      */ import javax.swing.event.MouseInputListener;
/*      */ import javax.swing.plaf.ComponentUI;
/*      */ import javax.swing.plaf.InternalFrameUI;
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
/*      */ public class BasicInternalFrameUI
/*      */   extends InternalFrameUI
/*      */ {
/*      */   protected JInternalFrame frame;
/*      */   private Handler handler;
/*      */   protected MouseInputAdapter borderListener;
/*      */   protected PropertyChangeListener propertyChangeListener;
/*      */   protected LayoutManager internalFrameLayout;
/*      */   protected ComponentListener componentListener;
/*      */   protected MouseInputListener glassPaneDispatcher;
/*      */   private InternalFrameListener internalFrameListener;
/*      */   protected JComponent northPane;
/*      */   protected JComponent southPane;
/*      */   protected JComponent westPane;
/*      */   protected JComponent eastPane;
/*      */   protected BasicInternalFrameTitlePane titlePane;
/*      */   private static DesktopManager sharedDesktopManager;
/*      */   private boolean componentListenerAdded = false;
/*      */   private Rectangle parentBounds;
/*      */   private boolean dragging = false;
/*      */   private boolean resizing = false;
/*      */   @Deprecated
/*      */   protected KeyStroke openMenuKey;
/*      */   private boolean keyBindingRegistered = false;
/*      */   private boolean keyBindingActive = false;
/*      */   
/*      */   public static ComponentUI createUI(JComponent paramJComponent) {
/*   89 */     return new BasicInternalFrameUI((JInternalFrame)paramJComponent);
/*      */   }
/*      */   
/*      */   public BasicInternalFrameUI(JInternalFrame paramJInternalFrame) {
/*   93 */     LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
/*   94 */     if (lookAndFeel instanceof BasicLookAndFeel) {
/*   95 */       ((BasicLookAndFeel)lookAndFeel).installAWTEventListener();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void installUI(JComponent paramJComponent) {
/*  101 */     this.frame = (JInternalFrame)paramJComponent;
/*      */     
/*  103 */     installDefaults();
/*  104 */     installListeners();
/*  105 */     installComponents();
/*  106 */     installKeyboardActions();
/*      */     
/*  108 */     LookAndFeel.installProperty(this.frame, "opaque", Boolean.TRUE);
/*      */   }
/*      */   
/*      */   public void uninstallUI(JComponent paramJComponent) {
/*  112 */     if (paramJComponent != this.frame) {
/*  113 */       throw new IllegalComponentStateException(this + " was asked to deinstall() " + paramJComponent + " when it only knows about " + this.frame + ".");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  118 */     uninstallKeyboardActions();
/*  119 */     uninstallComponents();
/*  120 */     uninstallListeners();
/*  121 */     uninstallDefaults();
/*  122 */     updateFrameCursor();
/*  123 */     this.handler = null;
/*  124 */     this.frame = null;
/*      */   }
/*      */   
/*      */   protected void installDefaults() {
/*  128 */     Icon icon = this.frame.getFrameIcon();
/*  129 */     if (icon == null || icon instanceof javax.swing.plaf.UIResource) {
/*  130 */       this.frame.setFrameIcon(UIManager.getIcon("InternalFrame.icon"));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  135 */     Container container = this.frame.getContentPane();
/*  136 */     if (container != null) {
/*  137 */       Color color = container.getBackground();
/*  138 */       if (color instanceof javax.swing.plaf.UIResource)
/*  139 */         container.setBackground(null); 
/*      */     } 
/*  141 */     this.frame.setLayout(this.internalFrameLayout = createLayoutManager());
/*  142 */     this.frame.setBackground(UIManager.getLookAndFeelDefaults().getColor("control"));
/*      */     
/*  144 */     LookAndFeel.installBorder(this.frame, "InternalFrame.border");
/*      */   }
/*      */   
/*      */   protected void installKeyboardActions() {
/*  148 */     createInternalFrameListener();
/*  149 */     if (this.internalFrameListener != null) {
/*  150 */       this.frame.addInternalFrameListener(this.internalFrameListener);
/*      */     }
/*      */     
/*  153 */     LazyActionMap.installLazyActionMap(this.frame, BasicInternalFrameUI.class, "InternalFrame.actionMap");
/*      */   }
/*      */ 
/*      */   
/*      */   static void loadActionMap(LazyActionMap paramLazyActionMap) {
/*  158 */     paramLazyActionMap.put(new UIAction("showSystemMenu") {
/*      */           public void actionPerformed(ActionEvent param1ActionEvent) {
/*  160 */             JInternalFrame jInternalFrame = (JInternalFrame)param1ActionEvent.getSource();
/*  161 */             if (jInternalFrame.getUI() instanceof BasicInternalFrameUI) {
/*      */               
/*  163 */               JComponent jComponent = ((BasicInternalFrameUI)jInternalFrame.getUI()).getNorthPane();
/*  164 */               if (jComponent instanceof BasicInternalFrameTitlePane) {
/*  165 */                 ((BasicInternalFrameTitlePane)jComponent)
/*  166 */                   .showSystemMenu();
/*      */               }
/*      */             } 
/*      */           }
/*      */           
/*      */           public boolean isEnabled(Object param1Object) {
/*  172 */             if (param1Object instanceof JInternalFrame) {
/*  173 */               JInternalFrame jInternalFrame = (JInternalFrame)param1Object;
/*  174 */               if (jInternalFrame.getUI() instanceof BasicInternalFrameUI) {
/*  175 */                 return ((BasicInternalFrameUI)jInternalFrame.getUI())
/*  176 */                   .isKeyBindingActive();
/*      */               }
/*      */             } 
/*  179 */             return false;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  184 */     BasicLookAndFeel.installAudioActionMap(paramLazyActionMap);
/*      */   }
/*      */   
/*      */   protected void installComponents() {
/*  188 */     setNorthPane(createNorthPane(this.frame));
/*  189 */     setSouthPane(createSouthPane(this.frame));
/*  190 */     setEastPane(createEastPane(this.frame));
/*  191 */     setWestPane(createWestPane(this.frame));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void installListeners() {
/*  198 */     this.borderListener = createBorderListener(this.frame);
/*  199 */     this.propertyChangeListener = createPropertyChangeListener();
/*  200 */     this.frame.addPropertyChangeListener(this.propertyChangeListener);
/*  201 */     installMouseHandlers(this.frame);
/*  202 */     this.glassPaneDispatcher = createGlassPaneDispatcher();
/*  203 */     if (this.glassPaneDispatcher != null) {
/*  204 */       this.frame.getGlassPane().addMouseListener(this.glassPaneDispatcher);
/*  205 */       this.frame.getGlassPane().addMouseMotionListener(this.glassPaneDispatcher);
/*      */     } 
/*  207 */     this.componentListener = createComponentListener();
/*  208 */     if (this.frame.getParent() != null) {
/*  209 */       this.parentBounds = this.frame.getParent().getBounds();
/*      */     }
/*  211 */     if (this.frame.getParent() != null && !this.componentListenerAdded) {
/*  212 */       this.frame.getParent().addComponentListener(this.componentListener);
/*  213 */       this.componentListenerAdded = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WindowFocusListener getWindowFocusListener() {
/*  222 */     return getHandler();
/*      */   }
/*      */ 
/*      */   
/*      */   private void cancelResize() {
/*  227 */     if (this.resizing && 
/*  228 */       this.borderListener instanceof BorderListener) {
/*  229 */       ((BorderListener)this.borderListener).finishMouseReleased();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private Handler getHandler() {
/*  235 */     if (this.handler == null) {
/*  236 */       this.handler = new Handler();
/*      */     }
/*  238 */     return this.handler;
/*      */   }
/*      */   
/*      */   InputMap getInputMap(int paramInt) {
/*  242 */     if (paramInt == 2) {
/*  243 */       return createInputMap(paramInt);
/*      */     }
/*  245 */     return null;
/*      */   }
/*      */   
/*      */   InputMap createInputMap(int paramInt) {
/*  249 */     if (paramInt == 2) {
/*  250 */       Object[] arrayOfObject = (Object[])DefaultLookup.get(this.frame, this, "InternalFrame.windowBindings");
/*      */ 
/*      */       
/*  253 */       if (arrayOfObject != null) {
/*  254 */         return LookAndFeel.makeComponentInputMap(this.frame, arrayOfObject);
/*      */       }
/*      */     } 
/*  257 */     return null;
/*      */   }
/*      */   
/*      */   protected void uninstallDefaults() {
/*  261 */     Icon icon = this.frame.getFrameIcon();
/*  262 */     if (icon instanceof javax.swing.plaf.UIResource) {
/*  263 */       this.frame.setFrameIcon((Icon)null);
/*      */     }
/*  265 */     this.internalFrameLayout = null;
/*  266 */     this.frame.setLayout((LayoutManager)null);
/*  267 */     LookAndFeel.uninstallBorder(this.frame);
/*      */   }
/*      */   
/*      */   protected void uninstallComponents() {
/*  271 */     setNorthPane(null);
/*  272 */     setSouthPane(null);
/*  273 */     setEastPane(null);
/*  274 */     setWestPane(null);
/*  275 */     if (this.titlePane != null) {
/*  276 */       this.titlePane.uninstallDefaults();
/*      */     }
/*  278 */     this.titlePane = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void uninstallListeners() {
/*  285 */     if (this.frame.getParent() != null && this.componentListenerAdded) {
/*  286 */       this.frame.getParent().removeComponentListener(this.componentListener);
/*  287 */       this.componentListenerAdded = false;
/*      */     } 
/*  289 */     this.componentListener = null;
/*  290 */     if (this.glassPaneDispatcher != null) {
/*  291 */       this.frame.getGlassPane().removeMouseListener(this.glassPaneDispatcher);
/*  292 */       this.frame.getGlassPane().removeMouseMotionListener(this.glassPaneDispatcher);
/*  293 */       this.glassPaneDispatcher = null;
/*      */     } 
/*  295 */     deinstallMouseHandlers(this.frame);
/*  296 */     this.frame.removePropertyChangeListener(this.propertyChangeListener);
/*  297 */     this.propertyChangeListener = null;
/*  298 */     this.borderListener = null;
/*      */   }
/*      */   
/*      */   protected void uninstallKeyboardActions() {
/*  302 */     if (this.internalFrameListener != null) {
/*  303 */       this.frame.removeInternalFrameListener(this.internalFrameListener);
/*      */     }
/*  305 */     this.internalFrameListener = null;
/*      */     
/*  307 */     SwingUtilities.replaceUIInputMap(this.frame, 2, null);
/*      */     
/*  309 */     SwingUtilities.replaceUIActionMap(this.frame, null);
/*      */   }
/*      */ 
/*      */   
/*      */   void updateFrameCursor() {
/*  314 */     if (this.resizing) {
/*      */       return;
/*      */     }
/*  317 */     Cursor cursor = this.frame.getLastCursor();
/*  318 */     if (cursor == null) {
/*  319 */       cursor = Cursor.getPredefinedCursor(0);
/*      */     }
/*  321 */     this.frame.setCursor(cursor);
/*      */   }
/*      */   
/*      */   protected LayoutManager createLayoutManager() {
/*  325 */     return getHandler();
/*      */   }
/*      */   
/*      */   protected PropertyChangeListener createPropertyChangeListener() {
/*  329 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getPreferredSize(JComponent paramJComponent) {
/*  335 */     if (this.frame == paramJComponent)
/*  336 */       return this.frame.getLayout().preferredLayoutSize(paramJComponent); 
/*  337 */     return new Dimension(100, 100);
/*      */   }
/*      */   
/*      */   public Dimension getMinimumSize(JComponent paramJComponent) {
/*  341 */     if (this.frame == paramJComponent) {
/*  342 */       return this.frame.getLayout().minimumLayoutSize(paramJComponent);
/*      */     }
/*  344 */     return new Dimension(0, 0);
/*      */   }
/*      */   
/*      */   public Dimension getMaximumSize(JComponent paramJComponent) {
/*  348 */     return new Dimension(2147483647, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void replacePane(JComponent paramJComponent1, JComponent paramJComponent2) {
/*  359 */     if (paramJComponent1 != null) {
/*  360 */       deinstallMouseHandlers(paramJComponent1);
/*  361 */       this.frame.remove(paramJComponent1);
/*      */     } 
/*  363 */     if (paramJComponent2 != null) {
/*  364 */       this.frame.add(paramJComponent2);
/*  365 */       installMouseHandlers(paramJComponent2);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void deinstallMouseHandlers(JComponent paramJComponent) {
/*  370 */     paramJComponent.removeMouseListener(this.borderListener);
/*  371 */     paramJComponent.removeMouseMotionListener(this.borderListener);
/*      */   }
/*      */   
/*      */   protected void installMouseHandlers(JComponent paramJComponent) {
/*  375 */     paramJComponent.addMouseListener(this.borderListener);
/*  376 */     paramJComponent.addMouseMotionListener(this.borderListener);
/*      */   }
/*      */   
/*      */   protected JComponent createNorthPane(JInternalFrame paramJInternalFrame) {
/*  380 */     this.titlePane = new BasicInternalFrameTitlePane(paramJInternalFrame);
/*  381 */     return this.titlePane;
/*      */   }
/*      */ 
/*      */   
/*      */   protected JComponent createSouthPane(JInternalFrame paramJInternalFrame) {
/*  386 */     return null;
/*      */   }
/*      */   
/*      */   protected JComponent createWestPane(JInternalFrame paramJInternalFrame) {
/*  390 */     return null;
/*      */   }
/*      */   
/*      */   protected JComponent createEastPane(JInternalFrame paramJInternalFrame) {
/*  394 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected MouseInputAdapter createBorderListener(JInternalFrame paramJInternalFrame) {
/*  399 */     return new BorderListener();
/*      */   }
/*      */   
/*      */   protected void createInternalFrameListener() {
/*  403 */     this.internalFrameListener = getHandler();
/*      */   }
/*      */   
/*      */   protected final boolean isKeyBindingRegistered() {
/*  407 */     return this.keyBindingRegistered;
/*      */   }
/*      */   
/*      */   protected final void setKeyBindingRegistered(boolean paramBoolean) {
/*  411 */     this.keyBindingRegistered = paramBoolean;
/*      */   }
/*      */   
/*      */   public final boolean isKeyBindingActive() {
/*  415 */     return this.keyBindingActive;
/*      */   }
/*      */   
/*      */   protected final void setKeyBindingActive(boolean paramBoolean) {
/*  419 */     this.keyBindingActive = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupMenuOpenKey() {
/*  428 */     InputMap inputMap = getInputMap(2);
/*  429 */     SwingUtilities.replaceUIInputMap(this.frame, 2, inputMap);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupMenuCloseKey() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public JComponent getNorthPane() {
/*  439 */     return this.northPane;
/*      */   }
/*      */   
/*      */   public void setNorthPane(JComponent paramJComponent) {
/*  443 */     if (this.northPane != null && this.northPane instanceof BasicInternalFrameTitlePane)
/*      */     {
/*  445 */       ((BasicInternalFrameTitlePane)this.northPane).uninstallListeners();
/*      */     }
/*  447 */     replacePane(this.northPane, paramJComponent);
/*  448 */     this.northPane = paramJComponent;
/*  449 */     if (paramJComponent instanceof BasicInternalFrameTitlePane) {
/*  450 */       this.titlePane = (BasicInternalFrameTitlePane)paramJComponent;
/*      */     }
/*      */   }
/*      */   
/*      */   public JComponent getSouthPane() {
/*  455 */     return this.southPane;
/*      */   }
/*      */   
/*      */   public void setSouthPane(JComponent paramJComponent) {
/*  459 */     this.southPane = paramJComponent;
/*      */   }
/*      */   
/*      */   public JComponent getWestPane() {
/*  463 */     return this.westPane;
/*      */   }
/*      */   
/*      */   public void setWestPane(JComponent paramJComponent) {
/*  467 */     this.westPane = paramJComponent;
/*      */   }
/*      */   
/*      */   public JComponent getEastPane() {
/*  471 */     return this.eastPane;
/*      */   }
/*      */   
/*      */   public void setEastPane(JComponent paramJComponent) {
/*  475 */     this.eastPane = paramJComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public class InternalFramePropertyChangeListener
/*      */     implements PropertyChangeListener
/*      */   {
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/*  489 */       BasicInternalFrameUI.this.getHandler().propertyChange(param1PropertyChangeEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class InternalFrameLayout
/*      */     implements LayoutManager
/*      */   {
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {
/*  499 */       BasicInternalFrameUI.this.getHandler().addLayoutComponent(param1String, param1Component);
/*      */     }
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {
/*  503 */       BasicInternalFrameUI.this.getHandler().removeLayoutComponent(param1Component);
/*      */     }
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*  507 */       return BasicInternalFrameUI.this.getHandler().preferredLayoutSize(param1Container);
/*      */     }
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*  511 */       return BasicInternalFrameUI.this.getHandler().minimumLayoutSize(param1Container);
/*      */     }
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  515 */       BasicInternalFrameUI.this.getHandler().layoutContainer(param1Container);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DesktopManager getDesktopManager() {
/*  526 */     if (this.frame.getDesktopPane() != null && this.frame
/*  527 */       .getDesktopPane().getDesktopManager() != null)
/*  528 */       return this.frame.getDesktopPane().getDesktopManager(); 
/*  529 */     if (sharedDesktopManager == null)
/*  530 */       sharedDesktopManager = createDesktopManager(); 
/*  531 */     return sharedDesktopManager;
/*      */   }
/*      */   
/*      */   protected DesktopManager createDesktopManager() {
/*  535 */     return new DefaultDesktopManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeFrame(JInternalFrame paramJInternalFrame) {
/*  545 */     BasicLookAndFeel.playSound(this.frame, "InternalFrame.closeSound");
/*      */     
/*  547 */     getDesktopManager().closeFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void maximizeFrame(JInternalFrame paramJInternalFrame) {
/*  557 */     BasicLookAndFeel.playSound(this.frame, "InternalFrame.maximizeSound");
/*      */     
/*  559 */     getDesktopManager().maximizeFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void minimizeFrame(JInternalFrame paramJInternalFrame) {
/*  569 */     if (!paramJInternalFrame.isIcon())
/*      */     {
/*      */       
/*  572 */       BasicLookAndFeel.playSound(this.frame, "InternalFrame.restoreDownSound");
/*      */     }
/*      */     
/*  575 */     getDesktopManager().minimizeFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void iconifyFrame(JInternalFrame paramJInternalFrame) {
/*  585 */     BasicLookAndFeel.playSound(this.frame, "InternalFrame.minimizeSound");
/*      */     
/*  587 */     getDesktopManager().iconifyFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deiconifyFrame(JInternalFrame paramJInternalFrame) {
/*  597 */     if (!paramJInternalFrame.isMaximum())
/*      */     {
/*      */       
/*  600 */       BasicLookAndFeel.playSound(this.frame, "InternalFrame.restoreUpSound");
/*      */     }
/*      */     
/*  603 */     getDesktopManager().deiconifyFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void activateFrame(JInternalFrame paramJInternalFrame) {
/*  610 */     getDesktopManager().activateFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void deactivateFrame(JInternalFrame paramJInternalFrame) {
/*  616 */     getDesktopManager().deactivateFrame(paramJInternalFrame);
/*      */   }
/*      */ 
/*      */   
/*      */   protected class BorderListener
/*      */     extends MouseInputAdapter
/*      */     implements SwingConstants
/*      */   {
/*      */     int _x;
/*      */     
/*      */     int _y;
/*      */     
/*      */     int __x;
/*      */     
/*      */     int __y;
/*      */     
/*      */     Rectangle startingBounds;
/*      */     
/*      */     int resizeDir;
/*  635 */     protected final int RESIZE_NONE = 0;
/*      */     
/*      */     private boolean discardRelease = false;
/*  638 */     int resizeCornerSize = 16;
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/*  641 */       if (param1MouseEvent.getClickCount() > 1 && param1MouseEvent.getSource() == BasicInternalFrameUI.this.getNorthPane()) {
/*  642 */         if (BasicInternalFrameUI.this.frame.isIconifiable() && BasicInternalFrameUI.this.frame.isIcon()) { 
/*  643 */           try { BasicInternalFrameUI.this.frame.setIcon(false); } catch (PropertyVetoException propertyVetoException) {} }
/*  644 */         else if (BasicInternalFrameUI.this.frame.isMaximizable())
/*  645 */         { if (!BasicInternalFrameUI.this.frame.isMaximum()) { 
/*  646 */             try { BasicInternalFrameUI.this.frame.setMaximum(true); } catch (PropertyVetoException propertyVetoException) {} }
/*      */           else { 
/*  648 */             try { BasicInternalFrameUI.this.frame.setMaximum(false); } catch (PropertyVetoException propertyVetoException) {} }
/*      */            }
/*      */       
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void finishMouseReleased() {
/*  657 */       if (this.discardRelease) {
/*  658 */         this.discardRelease = false;
/*      */         return;
/*      */       } 
/*  661 */       if (this.resizeDir == 0) {
/*  662 */         BasicInternalFrameUI.this.getDesktopManager().endDraggingFrame(BasicInternalFrameUI.this.frame);
/*  663 */         BasicInternalFrameUI.this.dragging = false;
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  668 */         Window window = SwingUtilities.getWindowAncestor(BasicInternalFrameUI.this.frame);
/*  669 */         if (window != null) {
/*  670 */           window.removeWindowFocusListener(BasicInternalFrameUI.this
/*  671 */               .getWindowFocusListener());
/*      */         }
/*  673 */         Container container = BasicInternalFrameUI.this.frame.getTopLevelAncestor();
/*  674 */         if (container instanceof RootPaneContainer) {
/*  675 */           Component component = ((RootPaneContainer)container).getGlassPane();
/*  676 */           component.setCursor(Cursor.getPredefinedCursor(0));
/*      */           
/*  678 */           component.setVisible(false);
/*      */         } 
/*  680 */         BasicInternalFrameUI.this.getDesktopManager().endResizingFrame(BasicInternalFrameUI.this.frame);
/*  681 */         BasicInternalFrameUI.this.resizing = false;
/*  682 */         BasicInternalFrameUI.this.updateFrameCursor();
/*      */       } 
/*  684 */       this._x = 0;
/*  685 */       this._y = 0;
/*  686 */       this.__x = 0;
/*  687 */       this.__y = 0;
/*  688 */       this.startingBounds = null;
/*  689 */       this.resizeDir = 0;
/*      */ 
/*      */ 
/*      */       
/*  693 */       this.discardRelease = true;
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/*  697 */       finishMouseReleased();
/*      */     }
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/*  701 */       Point point1 = SwingUtilities.convertPoint((Component)param1MouseEvent.getSource(), param1MouseEvent
/*  702 */           .getX(), param1MouseEvent.getY(), null);
/*  703 */       this.__x = param1MouseEvent.getX();
/*  704 */       this.__y = param1MouseEvent.getY();
/*  705 */       this._x = point1.x;
/*  706 */       this._y = point1.y;
/*  707 */       this.startingBounds = BasicInternalFrameUI.this.frame.getBounds();
/*  708 */       this.resizeDir = 0;
/*  709 */       this.discardRelease = false;
/*      */       try {
/*  711 */         BasicInternalFrameUI.this.frame.setSelected(true);
/*  712 */       } catch (PropertyVetoException propertyVetoException) {}
/*      */       
/*  714 */       Insets insets = BasicInternalFrameUI.this.frame.getInsets();
/*      */       
/*  716 */       Point point2 = new Point(this.__x, this.__y);
/*  717 */       if (param1MouseEvent.getSource() == BasicInternalFrameUI.this.getNorthPane()) {
/*  718 */         Point point = BasicInternalFrameUI.this.getNorthPane().getLocation();
/*  719 */         point2.x += point.x;
/*  720 */         point2.y += point.y;
/*      */       } 
/*      */       
/*  723 */       if (param1MouseEvent.getSource() == BasicInternalFrameUI.this.getNorthPane() && 
/*  724 */         point2.x > insets.left && point2.y > insets.top && point2.x < BasicInternalFrameUI.this.frame.getWidth() - insets.right) {
/*  725 */         BasicInternalFrameUI.this.getDesktopManager().beginDraggingFrame(BasicInternalFrameUI.this.frame);
/*  726 */         BasicInternalFrameUI.this.dragging = true;
/*      */         
/*      */         return;
/*      */       } 
/*  730 */       if (!BasicInternalFrameUI.this.frame.isResizable()) {
/*      */         return;
/*      */       }
/*      */       
/*  734 */       if (param1MouseEvent.getSource() == BasicInternalFrameUI.this.frame || param1MouseEvent.getSource() == BasicInternalFrameUI.this.getNorthPane()) {
/*  735 */         if (point2.x <= insets.left) {
/*  736 */           if (point2.y < this.resizeCornerSize + insets.top) {
/*  737 */             this.resizeDir = 8;
/*  738 */           } else if (point2.y > BasicInternalFrameUI.this.frame.getHeight() - this.resizeCornerSize - insets.bottom) {
/*      */             
/*  740 */             this.resizeDir = 6;
/*      */           } else {
/*  742 */             this.resizeDir = 7;
/*      */           } 
/*  744 */         } else if (point2.x >= BasicInternalFrameUI.this.frame.getWidth() - insets.right) {
/*  745 */           if (point2.y < this.resizeCornerSize + insets.top) {
/*  746 */             this.resizeDir = 2;
/*  747 */           } else if (point2.y > BasicInternalFrameUI.this.frame.getHeight() - this.resizeCornerSize - insets.bottom) {
/*      */             
/*  749 */             this.resizeDir = 4;
/*      */           } else {
/*  751 */             this.resizeDir = 3;
/*      */           } 
/*  753 */         } else if (point2.y <= insets.top) {
/*  754 */           if (point2.x < this.resizeCornerSize + insets.left) {
/*  755 */             this.resizeDir = 8;
/*  756 */           } else if (point2.x > BasicInternalFrameUI.this.frame.getWidth() - this.resizeCornerSize - insets.right) {
/*      */             
/*  758 */             this.resizeDir = 2;
/*      */           } else {
/*  760 */             this.resizeDir = 1;
/*      */           } 
/*  762 */         } else if (point2.y >= BasicInternalFrameUI.this.frame.getHeight() - insets.bottom) {
/*  763 */           if (point2.x < this.resizeCornerSize + insets.left) {
/*  764 */             this.resizeDir = 6;
/*  765 */           } else if (point2.x > BasicInternalFrameUI.this.frame.getWidth() - this.resizeCornerSize - insets.right) {
/*      */             
/*  767 */             this.resizeDir = 4;
/*      */           } else {
/*  769 */             this.resizeDir = 5;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  774 */           this.discardRelease = true;
/*      */           return;
/*      */         } 
/*  777 */         Cursor cursor = Cursor.getPredefinedCursor(0);
/*  778 */         switch (this.resizeDir) {
/*      */           case 5:
/*  780 */             cursor = Cursor.getPredefinedCursor(9);
/*      */             break;
/*      */           case 1:
/*  783 */             cursor = Cursor.getPredefinedCursor(8);
/*      */             break;
/*      */           case 7:
/*  786 */             cursor = Cursor.getPredefinedCursor(10);
/*      */             break;
/*      */           case 3:
/*  789 */             cursor = Cursor.getPredefinedCursor(11);
/*      */             break;
/*      */           case 4:
/*  792 */             cursor = Cursor.getPredefinedCursor(5);
/*      */             break;
/*      */           case 6:
/*  795 */             cursor = Cursor.getPredefinedCursor(4);
/*      */             break;
/*      */           case 8:
/*  798 */             cursor = Cursor.getPredefinedCursor(6);
/*      */             break;
/*      */           case 2:
/*  801 */             cursor = Cursor.getPredefinedCursor(7);
/*      */             break;
/*      */         } 
/*  804 */         Container container = BasicInternalFrameUI.this.frame.getTopLevelAncestor();
/*  805 */         if (container instanceof RootPaneContainer) {
/*  806 */           Component component = ((RootPaneContainer)container).getGlassPane();
/*  807 */           component.setVisible(true);
/*  808 */           component.setCursor(cursor);
/*      */         } 
/*  810 */         BasicInternalFrameUI.this.getDesktopManager().beginResizingFrame(BasicInternalFrameUI.this.frame, this.resizeDir);
/*  811 */         BasicInternalFrameUI.this.resizing = true;
/*      */ 
/*      */         
/*  814 */         Window window = SwingUtilities.getWindowAncestor(BasicInternalFrameUI.this.frame);
/*  815 */         if (window != null) {
/*  816 */           window.addWindowFocusListener(BasicInternalFrameUI.this
/*  817 */               .getWindowFocusListener());
/*      */         }
/*      */         return;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/*  825 */       if (this.startingBounds == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  830 */       Point point = SwingUtilities.convertPoint((Component)param1MouseEvent.getSource(), param1MouseEvent
/*  831 */           .getX(), param1MouseEvent.getY(), null);
/*  832 */       int i = this._x - point.x;
/*  833 */       int j = this._y - point.y;
/*  834 */       Dimension dimension1 = BasicInternalFrameUI.this.frame.getMinimumSize();
/*  835 */       Dimension dimension2 = BasicInternalFrameUI.this.frame.getMaximumSize();
/*      */       
/*  837 */       Insets insets = BasicInternalFrameUI.this.frame.getInsets();
/*      */ 
/*      */       
/*  840 */       if (BasicInternalFrameUI.this.dragging) {
/*  841 */         if (BasicInternalFrameUI.this.frame.isMaximum() || (param1MouseEvent.getModifiers() & 0x10) != 16) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  849 */         Dimension dimension = BasicInternalFrameUI.this.frame.getParent().getSize();
/*  850 */         int i4 = dimension.width;
/*  851 */         int i5 = dimension.height;
/*      */ 
/*      */         
/*  854 */         int i2 = this.startingBounds.x - i;
/*  855 */         int i3 = this.startingBounds.y - j;
/*      */ 
/*      */         
/*  858 */         if (i2 + insets.left <= -this.__x)
/*  859 */           i2 = -this.__x - insets.left + 1; 
/*  860 */         if (i3 + insets.top <= -this.__y)
/*  861 */           i3 = -this.__y - insets.top + 1; 
/*  862 */         if (i2 + this.__x + insets.right >= i4)
/*  863 */           i2 = i4 - this.__x - insets.right - 1; 
/*  864 */         if (i3 + this.__y + insets.bottom >= i5) {
/*  865 */           i3 = i5 - this.__y - insets.bottom - 1;
/*      */         }
/*  867 */         BasicInternalFrameUI.this.getDesktopManager().dragFrame(BasicInternalFrameUI.this.frame, i2, i3);
/*      */         
/*      */         return;
/*      */       } 
/*  871 */       if (!BasicInternalFrameUI.this.frame.isResizable()) {
/*      */         return;
/*      */       }
/*      */       
/*  875 */       int k = BasicInternalFrameUI.this.frame.getX();
/*  876 */       int m = BasicInternalFrameUI.this.frame.getY();
/*  877 */       int n = BasicInternalFrameUI.this.frame.getWidth();
/*  878 */       int i1 = BasicInternalFrameUI.this.frame.getHeight();
/*      */       
/*  880 */       BasicInternalFrameUI.this.parentBounds = BasicInternalFrameUI.this.frame.getParent().getBounds();
/*      */       
/*  882 */       switch (this.resizeDir) {
/*      */         case 0:
/*      */           return;
/*      */         case 1:
/*  886 */           if (this.startingBounds.height + j < dimension1.height) {
/*  887 */             j = -(this.startingBounds.height - dimension1.height);
/*  888 */           } else if (this.startingBounds.height + j > dimension2.height) {
/*  889 */             j = dimension2.height - this.startingBounds.height;
/*  890 */           }  if (this.startingBounds.y - j < 0) j = this.startingBounds.y;
/*      */           
/*  892 */           k = this.startingBounds.x;
/*  893 */           m = this.startingBounds.y - j;
/*  894 */           n = this.startingBounds.width;
/*  895 */           i1 = this.startingBounds.height + j;
/*      */           break;
/*      */         case 2:
/*  898 */           if (this.startingBounds.height + j < dimension1.height) {
/*  899 */             j = -(this.startingBounds.height - dimension1.height);
/*  900 */           } else if (this.startingBounds.height + j > dimension2.height) {
/*  901 */             j = dimension2.height - this.startingBounds.height;
/*  902 */           }  if (this.startingBounds.y - j < 0) j = this.startingBounds.y;
/*      */           
/*  904 */           if (this.startingBounds.width - i < dimension1.width) {
/*  905 */             i = this.startingBounds.width - dimension1.width;
/*  906 */           } else if (this.startingBounds.width - i > dimension2.width) {
/*  907 */             i = -(dimension2.width - this.startingBounds.width);
/*  908 */           }  if (this.startingBounds.x + this.startingBounds.width - i > 
/*  909 */             BasicInternalFrameUI.this.parentBounds.width)
/*      */           {
/*  911 */             i = this.startingBounds.x + this.startingBounds.width - BasicInternalFrameUI.this.parentBounds.width;
/*      */           }
/*      */           
/*  914 */           k = this.startingBounds.x;
/*  915 */           m = this.startingBounds.y - j;
/*  916 */           n = this.startingBounds.width - i;
/*  917 */           i1 = this.startingBounds.height + j;
/*      */           break;
/*      */         case 3:
/*  920 */           if (this.startingBounds.width - i < dimension1.width) {
/*  921 */             i = this.startingBounds.width - dimension1.width;
/*  922 */           } else if (this.startingBounds.width - i > dimension2.width) {
/*  923 */             i = -(dimension2.width - this.startingBounds.width);
/*  924 */           }  if (this.startingBounds.x + this.startingBounds.width - i > 
/*  925 */             BasicInternalFrameUI.this.parentBounds.width)
/*      */           {
/*  927 */             i = this.startingBounds.x + this.startingBounds.width - BasicInternalFrameUI.this.parentBounds.width;
/*      */           }
/*      */           
/*  930 */           n = this.startingBounds.width - i;
/*  931 */           i1 = this.startingBounds.height;
/*      */           break;
/*      */         case 4:
/*  934 */           if (this.startingBounds.width - i < dimension1.width) {
/*  935 */             i = this.startingBounds.width - dimension1.width;
/*  936 */           } else if (this.startingBounds.width - i > dimension2.width) {
/*  937 */             i = -(dimension2.width - this.startingBounds.width);
/*  938 */           }  if (this.startingBounds.x + this.startingBounds.width - i > 
/*  939 */             BasicInternalFrameUI.this.parentBounds.width)
/*      */           {
/*  941 */             i = this.startingBounds.x + this.startingBounds.width - BasicInternalFrameUI.this.parentBounds.width;
/*      */           }
/*      */           
/*  944 */           if (this.startingBounds.height - j < dimension1.height) {
/*  945 */             j = this.startingBounds.height - dimension1.height;
/*  946 */           } else if (this.startingBounds.height - j > dimension2.height) {
/*  947 */             j = -(dimension2.height - this.startingBounds.height);
/*  948 */           }  if (this.startingBounds.y + this.startingBounds.height - j > 
/*  949 */             BasicInternalFrameUI.this.parentBounds.height)
/*      */           {
/*  951 */             j = this.startingBounds.y + this.startingBounds.height - BasicInternalFrameUI.this.parentBounds.height;
/*      */           }
/*      */           
/*  954 */           n = this.startingBounds.width - i;
/*  955 */           i1 = this.startingBounds.height - j;
/*      */           break;
/*      */         case 5:
/*  958 */           if (this.startingBounds.height - j < dimension1.height) {
/*  959 */             j = this.startingBounds.height - dimension1.height;
/*  960 */           } else if (this.startingBounds.height - j > dimension2.height) {
/*  961 */             j = -(dimension2.height - this.startingBounds.height);
/*  962 */           }  if (this.startingBounds.y + this.startingBounds.height - j > 
/*  963 */             BasicInternalFrameUI.this.parentBounds.height)
/*      */           {
/*  965 */             j = this.startingBounds.y + this.startingBounds.height - BasicInternalFrameUI.this.parentBounds.height;
/*      */           }
/*      */           
/*  968 */           n = this.startingBounds.width;
/*  969 */           i1 = this.startingBounds.height - j;
/*      */           break;
/*      */         case 6:
/*  972 */           if (this.startingBounds.height - j < dimension1.height) {
/*  973 */             j = this.startingBounds.height - dimension1.height;
/*  974 */           } else if (this.startingBounds.height - j > dimension2.height) {
/*  975 */             j = -(dimension2.height - this.startingBounds.height);
/*  976 */           }  if (this.startingBounds.y + this.startingBounds.height - j > 
/*  977 */             BasicInternalFrameUI.this.parentBounds.height)
/*      */           {
/*  979 */             j = this.startingBounds.y + this.startingBounds.height - BasicInternalFrameUI.this.parentBounds.height;
/*      */           }
/*      */           
/*  982 */           if (this.startingBounds.width + i < dimension1.width) {
/*  983 */             i = -(this.startingBounds.width - dimension1.width);
/*  984 */           } else if (this.startingBounds.width + i > dimension2.width) {
/*  985 */             i = dimension2.width - this.startingBounds.width;
/*  986 */           }  if (this.startingBounds.x - i < 0) {
/*  987 */             i = this.startingBounds.x;
/*      */           }
/*      */           
/*  990 */           k = this.startingBounds.x - i;
/*  991 */           m = this.startingBounds.y;
/*  992 */           n = this.startingBounds.width + i;
/*  993 */           i1 = this.startingBounds.height - j;
/*      */           break;
/*      */         case 7:
/*  996 */           if (this.startingBounds.width + i < dimension1.width) {
/*  997 */             i = -(this.startingBounds.width - dimension1.width);
/*  998 */           } else if (this.startingBounds.width + i > dimension2.width) {
/*  999 */             i = dimension2.width - this.startingBounds.width;
/* 1000 */           }  if (this.startingBounds.x - i < 0) {
/* 1001 */             i = this.startingBounds.x;
/*      */           }
/*      */           
/* 1004 */           k = this.startingBounds.x - i;
/* 1005 */           m = this.startingBounds.y;
/* 1006 */           n = this.startingBounds.width + i;
/* 1007 */           i1 = this.startingBounds.height;
/*      */           break;
/*      */         case 8:
/* 1010 */           if (this.startingBounds.width + i < dimension1.width) {
/* 1011 */             i = -(this.startingBounds.width - dimension1.width);
/* 1012 */           } else if (this.startingBounds.width + i > dimension2.width) {
/* 1013 */             i = dimension2.width - this.startingBounds.width;
/* 1014 */           }  if (this.startingBounds.x - i < 0) {
/* 1015 */             i = this.startingBounds.x;
/*      */           }
/*      */           
/* 1018 */           if (this.startingBounds.height + j < dimension1.height) {
/* 1019 */             j = -(this.startingBounds.height - dimension1.height);
/* 1020 */           } else if (this.startingBounds.height + j > dimension2.height) {
/* 1021 */             j = dimension2.height - this.startingBounds.height;
/* 1022 */           }  if (this.startingBounds.y - j < 0) j = this.startingBounds.y;
/*      */           
/* 1024 */           k = this.startingBounds.x - i;
/* 1025 */           m = this.startingBounds.y - j;
/* 1026 */           n = this.startingBounds.width + i;
/* 1027 */           i1 = this.startingBounds.height + j;
/*      */           break;
/*      */         default:
/*      */           return;
/*      */       } 
/* 1032 */       BasicInternalFrameUI.this.getDesktopManager().resizeFrame(BasicInternalFrameUI.this.frame, k, m, n, i1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 1037 */       if (!BasicInternalFrameUI.this.frame.isResizable()) {
/*      */         return;
/*      */       }
/* 1040 */       if (param1MouseEvent.getSource() == BasicInternalFrameUI.this.frame || param1MouseEvent.getSource() == BasicInternalFrameUI.this.getNorthPane()) {
/* 1041 */         Insets insets = BasicInternalFrameUI.this.frame.getInsets();
/* 1042 */         Point point = new Point(param1MouseEvent.getX(), param1MouseEvent.getY());
/* 1043 */         if (param1MouseEvent.getSource() == BasicInternalFrameUI.this.getNorthPane()) {
/* 1044 */           Point point1 = BasicInternalFrameUI.this.getNorthPane().getLocation();
/* 1045 */           point.x += point1.x;
/* 1046 */           point.y += point1.y;
/*      */         } 
/* 1048 */         if (point.x <= insets.left) {
/* 1049 */           if (point.y < this.resizeCornerSize + insets.top)
/* 1050 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(6)); }
/* 1051 */           else if (point.y > BasicInternalFrameUI.this.frame.getHeight() - this.resizeCornerSize - insets.bottom)
/* 1052 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(4)); }
/*      */           else
/* 1054 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(10)); } 
/* 1055 */         } else if (point.x >= BasicInternalFrameUI.this.frame.getWidth() - insets.right) {
/* 1056 */           if (param1MouseEvent.getY() < this.resizeCornerSize + insets.top)
/* 1057 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(7)); }
/* 1058 */           else if (point.y > BasicInternalFrameUI.this.frame.getHeight() - this.resizeCornerSize - insets.bottom)
/* 1059 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(5)); }
/*      */           else
/* 1061 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(11)); } 
/* 1062 */         } else if (point.y <= insets.top) {
/* 1063 */           if (point.x < this.resizeCornerSize + insets.left)
/* 1064 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(6)); }
/* 1065 */           else if (point.x > BasicInternalFrameUI.this.frame.getWidth() - this.resizeCornerSize - insets.right)
/* 1066 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(7)); }
/*      */           else
/* 1068 */           { BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(8)); } 
/* 1069 */         } else if (point.y >= BasicInternalFrameUI.this.frame.getHeight() - insets.bottom) {
/* 1070 */           if (point.x < this.resizeCornerSize + insets.left) {
/* 1071 */             BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(4));
/* 1072 */           } else if (point.x > BasicInternalFrameUI.this.frame.getWidth() - this.resizeCornerSize - insets.right) {
/* 1073 */             BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(5));
/*      */           } else {
/* 1075 */             BasicInternalFrameUI.this.frame.setCursor(Cursor.getPredefinedCursor(9));
/*      */           } 
/*      */         } else {
/* 1078 */           BasicInternalFrameUI.this.updateFrameCursor();
/*      */         } 
/*      */         return;
/*      */       } 
/* 1082 */       BasicInternalFrameUI.this.updateFrameCursor();
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 1086 */       BasicInternalFrameUI.this.updateFrameCursor();
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 1090 */       BasicInternalFrameUI.this.updateFrameCursor();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ComponentHandler
/*      */     implements ComponentListener
/*      */   {
/*      */     public void componentResized(ComponentEvent param1ComponentEvent) {
/* 1102 */       BasicInternalFrameUI.this.getHandler().componentResized(param1ComponentEvent);
/*      */     }
/*      */     
/*      */     public void componentMoved(ComponentEvent param1ComponentEvent) {
/* 1106 */       BasicInternalFrameUI.this.getHandler().componentMoved(param1ComponentEvent);
/*      */     }
/*      */     public void componentShown(ComponentEvent param1ComponentEvent) {
/* 1109 */       BasicInternalFrameUI.this.getHandler().componentShown(param1ComponentEvent);
/*      */     }
/*      */     public void componentHidden(ComponentEvent param1ComponentEvent) {
/* 1112 */       BasicInternalFrameUI.this.getHandler().componentHidden(param1ComponentEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   protected ComponentListener createComponentListener() {
/* 1117 */     return getHandler();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class GlassPaneDispatcher
/*      */     implements MouseInputListener
/*      */   {
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {
/* 1127 */       BasicInternalFrameUI.this.getHandler().mousePressed(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {
/* 1131 */       BasicInternalFrameUI.this.getHandler().mouseEntered(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {
/* 1135 */       BasicInternalFrameUI.this.getHandler().mouseMoved(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {
/* 1139 */       BasicInternalFrameUI.this.getHandler().mouseExited(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {
/* 1143 */       BasicInternalFrameUI.this.getHandler().mouseClicked(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {
/* 1147 */       BasicInternalFrameUI.this.getHandler().mouseReleased(param1MouseEvent);
/*      */     }
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {
/* 1151 */       BasicInternalFrameUI.this.getHandler().mouseDragged(param1MouseEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   protected MouseInputListener createGlassPaneDispatcher() {
/* 1156 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class BasicInternalFrameListener
/*      */     implements InternalFrameListener
/*      */   {
/*      */     public void internalFrameClosing(InternalFrameEvent param1InternalFrameEvent) {
/* 1167 */       BasicInternalFrameUI.this.getHandler().internalFrameClosing(param1InternalFrameEvent);
/*      */     }
/*      */     
/*      */     public void internalFrameClosed(InternalFrameEvent param1InternalFrameEvent) {
/* 1171 */       BasicInternalFrameUI.this.getHandler().internalFrameClosed(param1InternalFrameEvent);
/*      */     }
/*      */     
/*      */     public void internalFrameOpened(InternalFrameEvent param1InternalFrameEvent) {
/* 1175 */       BasicInternalFrameUI.this.getHandler().internalFrameOpened(param1InternalFrameEvent);
/*      */     }
/*      */     
/*      */     public void internalFrameIconified(InternalFrameEvent param1InternalFrameEvent) {
/* 1179 */       BasicInternalFrameUI.this.getHandler().internalFrameIconified(param1InternalFrameEvent);
/*      */     }
/*      */     
/*      */     public void internalFrameDeiconified(InternalFrameEvent param1InternalFrameEvent) {
/* 1183 */       BasicInternalFrameUI.this.getHandler().internalFrameDeiconified(param1InternalFrameEvent);
/*      */     }
/*      */     
/*      */     public void internalFrameActivated(InternalFrameEvent param1InternalFrameEvent) {
/* 1187 */       BasicInternalFrameUI.this.getHandler().internalFrameActivated(param1InternalFrameEvent);
/*      */     }
/*      */ 
/*      */     
/*      */     public void internalFrameDeactivated(InternalFrameEvent param1InternalFrameEvent) {
/* 1192 */       BasicInternalFrameUI.this.getHandler().internalFrameDeactivated(param1InternalFrameEvent);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class Handler
/*      */     implements ComponentListener, InternalFrameListener, LayoutManager, MouseInputListener, PropertyChangeListener, WindowFocusListener, SwingConstants
/*      */   {
/*      */     private Handler() {}
/*      */ 
/*      */     
/*      */     public void windowGainedFocus(WindowEvent param1WindowEvent) {}
/*      */ 
/*      */     
/*      */     public void windowLostFocus(WindowEvent param1WindowEvent) {
/* 1207 */       BasicInternalFrameUI.this.cancelResize();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void componentResized(ComponentEvent param1ComponentEvent) {
/* 1214 */       Rectangle rectangle = ((Component)param1ComponentEvent.getSource()).getBounds();
/* 1215 */       JInternalFrame.JDesktopIcon jDesktopIcon = null;
/*      */       
/* 1217 */       if (BasicInternalFrameUI.this.frame != null) {
/* 1218 */         jDesktopIcon = BasicInternalFrameUI.this.frame.getDesktopIcon();
/*      */ 
/*      */         
/* 1221 */         if (BasicInternalFrameUI.this.frame.isMaximum()) {
/* 1222 */           BasicInternalFrameUI.this.frame.setBounds(0, 0, rectangle.width, rectangle.height);
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1228 */       if (jDesktopIcon != null) {
/* 1229 */         Rectangle rectangle1 = jDesktopIcon.getBounds();
/*      */         
/* 1231 */         int i = rectangle1.y + rectangle.height - BasicInternalFrameUI.this.parentBounds.height;
/* 1232 */         jDesktopIcon.setBounds(rectangle1.x, i, rectangle1.width, rectangle1.height);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1237 */       if (!BasicInternalFrameUI.this.parentBounds.equals(rectangle)) {
/* 1238 */         BasicInternalFrameUI.this.parentBounds = rectangle;
/*      */       }
/*      */ 
/*      */       
/* 1242 */       if (BasicInternalFrameUI.this.frame != null) BasicInternalFrameUI.this.frame.validate(); 
/*      */     }
/*      */     
/*      */     public void componentMoved(ComponentEvent param1ComponentEvent) {}
/*      */     
/*      */     public void componentShown(ComponentEvent param1ComponentEvent) {}
/*      */     
/*      */     public void componentHidden(ComponentEvent param1ComponentEvent) {}
/*      */     
/*      */     public void internalFrameClosed(InternalFrameEvent param1InternalFrameEvent) {
/* 1252 */       BasicInternalFrameUI.this.frame.removeInternalFrameListener(BasicInternalFrameUI.this.getHandler());
/*      */     }
/*      */     
/*      */     public void internalFrameActivated(InternalFrameEvent param1InternalFrameEvent) {
/* 1256 */       if (!BasicInternalFrameUI.this.isKeyBindingRegistered()) {
/* 1257 */         BasicInternalFrameUI.this.setKeyBindingRegistered(true);
/* 1258 */         BasicInternalFrameUI.this.setupMenuOpenKey();
/* 1259 */         BasicInternalFrameUI.this.setupMenuCloseKey();
/*      */       } 
/* 1261 */       if (BasicInternalFrameUI.this.isKeyBindingRegistered())
/* 1262 */         BasicInternalFrameUI.this.setKeyBindingActive(true); 
/*      */     }
/*      */     
/*      */     public void internalFrameDeactivated(InternalFrameEvent param1InternalFrameEvent) {
/* 1266 */       BasicInternalFrameUI.this.setKeyBindingActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void internalFrameClosing(InternalFrameEvent param1InternalFrameEvent) {}
/*      */ 
/*      */     
/*      */     public void internalFrameOpened(InternalFrameEvent param1InternalFrameEvent) {}
/*      */ 
/*      */     
/*      */     public void internalFrameIconified(InternalFrameEvent param1InternalFrameEvent) {}
/*      */ 
/*      */     
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/* 1280 */       Insets insets = BasicInternalFrameUI.this.frame.getInsets();
/*      */       
/* 1282 */       Dimension dimension = new Dimension(BasicInternalFrameUI.this.frame.getRootPane().getPreferredSize());
/* 1283 */       dimension.width += insets.left + insets.right;
/* 1284 */       dimension.height += insets.top + insets.bottom;
/*      */       
/* 1286 */       if (BasicInternalFrameUI.this.getNorthPane() != null) {
/* 1287 */         Dimension dimension1 = BasicInternalFrameUI.this.getNorthPane().getPreferredSize();
/* 1288 */         dimension.width = Math.max(dimension1.width, dimension.width);
/* 1289 */         dimension.height += dimension1.height;
/*      */       } 
/*      */       
/* 1292 */       if (BasicInternalFrameUI.this.getSouthPane() != null) {
/* 1293 */         Dimension dimension1 = BasicInternalFrameUI.this.getSouthPane().getPreferredSize();
/* 1294 */         dimension.width = Math.max(dimension1.width, dimension.width);
/* 1295 */         dimension.height += dimension1.height;
/*      */       } 
/*      */       
/* 1298 */       if (BasicInternalFrameUI.this.getEastPane() != null) {
/* 1299 */         Dimension dimension1 = BasicInternalFrameUI.this.getEastPane().getPreferredSize();
/* 1300 */         dimension.width += dimension1.width;
/* 1301 */         dimension.height = Math.max(dimension1.height, dimension.height);
/*      */       } 
/*      */       
/* 1304 */       if (BasicInternalFrameUI.this.getWestPane() != null) {
/* 1305 */         Dimension dimension1 = BasicInternalFrameUI.this.getWestPane().getPreferredSize();
/* 1306 */         dimension.width += dimension1.width;
/* 1307 */         dimension.height = Math.max(dimension1.height, dimension.height);
/*      */       } 
/* 1309 */       return dimension;
/*      */     }
/*      */     public void internalFrameDeiconified(InternalFrameEvent param1InternalFrameEvent) {}
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */     
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/* 1316 */       Dimension dimension = new Dimension();
/* 1317 */       if (BasicInternalFrameUI.this.getNorthPane() != null && BasicInternalFrameUI.this
/* 1318 */         .getNorthPane() instanceof BasicInternalFrameTitlePane) {
/* 1319 */         dimension = new Dimension(BasicInternalFrameUI.this.getNorthPane().getMinimumSize());
/*      */       }
/* 1321 */       Insets insets = BasicInternalFrameUI.this.frame.getInsets();
/* 1322 */       dimension.width += insets.left + insets.right;
/* 1323 */       dimension.height += insets.top + insets.bottom;
/*      */       
/* 1325 */       return dimension;
/*      */     }
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/* 1329 */       Insets insets = BasicInternalFrameUI.this.frame.getInsets();
/*      */ 
/*      */       
/* 1332 */       int i = insets.left;
/* 1333 */       int j = insets.top;
/* 1334 */       int k = BasicInternalFrameUI.this.frame.getWidth() - insets.left - insets.right;
/* 1335 */       int m = BasicInternalFrameUI.this.frame.getHeight() - insets.top - insets.bottom;
/*      */       
/* 1337 */       if (BasicInternalFrameUI.this.getNorthPane() != null) {
/* 1338 */         Dimension dimension = BasicInternalFrameUI.this.getNorthPane().getPreferredSize();
/* 1339 */         if (DefaultLookup.getBoolean(BasicInternalFrameUI.this.frame, BasicInternalFrameUI.this, "InternalFrame.layoutTitlePaneAtOrigin", false)) {
/*      */           
/* 1341 */           j = 0;
/* 1342 */           m += insets.top;
/* 1343 */           BasicInternalFrameUI.this.getNorthPane().setBounds(0, 0, BasicInternalFrameUI.this.frame.getWidth(), dimension.height);
/*      */         }
/*      */         else {
/*      */           
/* 1347 */           BasicInternalFrameUI.this.getNorthPane().setBounds(i, j, k, dimension.height);
/*      */         } 
/* 1349 */         j += dimension.height;
/* 1350 */         m -= dimension.height;
/*      */       } 
/*      */       
/* 1353 */       if (BasicInternalFrameUI.this.getSouthPane() != null) {
/* 1354 */         Dimension dimension = BasicInternalFrameUI.this.getSouthPane().getPreferredSize();
/* 1355 */         BasicInternalFrameUI.this.getSouthPane().setBounds(i, BasicInternalFrameUI.this.frame.getHeight() - insets.bottom - dimension.height, k, dimension.height);
/*      */ 
/*      */         
/* 1358 */         m -= dimension.height;
/*      */       } 
/*      */       
/* 1361 */       if (BasicInternalFrameUI.this.getWestPane() != null) {
/* 1362 */         Dimension dimension = BasicInternalFrameUI.this.getWestPane().getPreferredSize();
/* 1363 */         BasicInternalFrameUI.this.getWestPane().setBounds(i, j, dimension.width, m);
/* 1364 */         k -= dimension.width;
/* 1365 */         i += dimension.width;
/*      */       } 
/*      */       
/* 1368 */       if (BasicInternalFrameUI.this.getEastPane() != null) {
/* 1369 */         Dimension dimension = BasicInternalFrameUI.this.getEastPane().getPreferredSize();
/* 1370 */         BasicInternalFrameUI.this.getEastPane().setBounds(k - dimension.width, j, dimension.width, m);
/* 1371 */         k -= dimension.width;
/*      */       } 
/*      */       
/* 1374 */       if (BasicInternalFrameUI.this.frame.getRootPane() != null) {
/* 1375 */         BasicInternalFrameUI.this.frame.getRootPane().setBounds(i, j, k, m);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void mousePressed(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseEntered(MouseEvent param1MouseEvent) {}
/*      */ 
/*      */     
/*      */     public void mouseMoved(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseExited(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseClicked(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseReleased(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void mouseDragged(MouseEvent param1MouseEvent) {}
/*      */     
/*      */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 1397 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 1398 */       JInternalFrame jInternalFrame = (JInternalFrame)param1PropertyChangeEvent.getSource();
/* 1399 */       Object object1 = param1PropertyChangeEvent.getNewValue();
/* 1400 */       Object object2 = param1PropertyChangeEvent.getOldValue();
/*      */       
/* 1402 */       if ("closed" == str) {
/* 1403 */         if (object1 == Boolean.TRUE) {
/*      */ 
/*      */           
/* 1406 */           BasicInternalFrameUI.this.cancelResize();
/* 1407 */           if (BasicInternalFrameUI.this.frame.getParent() != null && BasicInternalFrameUI.this.componentListenerAdded) {
/* 1408 */             BasicInternalFrameUI.this.frame.getParent().removeComponentListener(BasicInternalFrameUI.this.componentListener);
/*      */           }
/* 1410 */           BasicInternalFrameUI.this.closeFrame(jInternalFrame);
/*      */         } 
/* 1412 */       } else if ("maximum" == str) {
/* 1413 */         if (object1 == Boolean.TRUE) {
/* 1414 */           BasicInternalFrameUI.this.maximizeFrame(jInternalFrame);
/*      */         } else {
/* 1416 */           BasicInternalFrameUI.this.minimizeFrame(jInternalFrame);
/*      */         } 
/* 1418 */       } else if ("icon" == str) {
/* 1419 */         if (object1 == Boolean.TRUE) {
/* 1420 */           BasicInternalFrameUI.this.iconifyFrame(jInternalFrame);
/*      */         } else {
/* 1422 */           BasicInternalFrameUI.this.deiconifyFrame(jInternalFrame);
/*      */         } 
/* 1424 */       } else if ("selected" == str) {
/* 1425 */         if (object1 == Boolean.TRUE && object2 == Boolean.FALSE) {
/* 1426 */           BasicInternalFrameUI.this.activateFrame(jInternalFrame);
/* 1427 */         } else if (object1 == Boolean.FALSE && object2 == Boolean.TRUE) {
/*      */           
/* 1429 */           BasicInternalFrameUI.this.deactivateFrame(jInternalFrame);
/*      */         } 
/* 1431 */       } else if (str == "ancestor") {
/* 1432 */         if (object1 == null)
/*      */         {
/*      */           
/* 1435 */           BasicInternalFrameUI.this.cancelResize();
/*      */         }
/* 1437 */         if (BasicInternalFrameUI.this.frame.getParent() != null) {
/* 1438 */           BasicInternalFrameUI.this.parentBounds = jInternalFrame.getParent().getBounds();
/*      */         } else {
/* 1440 */           BasicInternalFrameUI.this.parentBounds = null;
/*      */         } 
/* 1442 */         if (BasicInternalFrameUI.this.frame.getParent() != null && !BasicInternalFrameUI.this.componentListenerAdded) {
/* 1443 */           jInternalFrame.getParent().addComponentListener(BasicInternalFrameUI.this.componentListener);
/* 1444 */           BasicInternalFrameUI.this.componentListenerAdded = true;
/*      */         } 
/* 1446 */       } else if ("title" == str || str == "closable" || str == "iconable" || str == "maximizable") {
/*      */ 
/*      */         
/* 1449 */         Dimension dimension1 = BasicInternalFrameUI.this.frame.getMinimumSize();
/* 1450 */         Dimension dimension2 = BasicInternalFrameUI.this.frame.getSize();
/* 1451 */         if (dimension1.width > dimension2.width)
/* 1452 */           BasicInternalFrameUI.this.frame.setSize(dimension1.width, dimension2.height); 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/basic/BasicInternalFrameUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */