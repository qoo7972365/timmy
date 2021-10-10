/*       */ package java.awt;
/*       */ 
/*       */ import java.applet.Applet;
/*       */ import java.awt.dnd.DropTarget;
/*       */ import java.awt.event.ComponentEvent;
/*       */ import java.awt.event.ComponentListener;
/*       */ import java.awt.event.FocusEvent;
/*       */ import java.awt.event.FocusListener;
/*       */ import java.awt.event.HierarchyBoundsListener;
/*       */ import java.awt.event.HierarchyEvent;
/*       */ import java.awt.event.HierarchyListener;
/*       */ import java.awt.event.InputMethodEvent;
/*       */ import java.awt.event.InputMethodListener;
/*       */ import java.awt.event.KeyEvent;
/*       */ import java.awt.event.KeyListener;
/*       */ import java.awt.event.MouseEvent;
/*       */ import java.awt.event.MouseListener;
/*       */ import java.awt.event.MouseMotionListener;
/*       */ import java.awt.event.MouseWheelEvent;
/*       */ import java.awt.event.MouseWheelListener;
/*       */ import java.awt.event.PaintEvent;
/*       */ import java.awt.event.WindowEvent;
/*       */ import java.awt.geom.AffineTransform;
/*       */ import java.awt.im.InputContext;
/*       */ import java.awt.im.InputMethodRequests;
/*       */ import java.awt.image.BufferStrategy;
/*       */ import java.awt.image.ColorModel;
/*       */ import java.awt.image.ImageObserver;
/*       */ import java.awt.image.ImageProducer;
/*       */ import java.awt.image.VolatileImage;
/*       */ import java.awt.peer.ComponentPeer;
/*       */ import java.awt.peer.ContainerPeer;
/*       */ import java.beans.PropertyChangeListener;
/*       */ import java.beans.PropertyChangeSupport;
/*       */ import java.beans.Transient;
/*       */ import java.io.IOException;
/*       */ import java.io.ObjectInputStream;
/*       */ import java.io.ObjectOutputStream;
/*       */ import java.io.OptionalDataException;
/*       */ import java.io.PrintStream;
/*       */ import java.io.PrintWriter;
/*       */ import java.io.Serializable;
/*       */ import java.lang.reflect.InvocationTargetException;
/*       */ import java.lang.reflect.Method;
/*       */ import java.security.AccessControlContext;
/*       */ import java.security.AccessController;
/*       */ import java.security.PrivilegedAction;
/*       */ import java.util.Collections;
/*       */ import java.util.HashSet;
/*       */ import java.util.Locale;
/*       */ import java.util.Map;
/*       */ import java.util.Objects;
/*       */ import java.util.Set;
/*       */ import java.util.Vector;
/*       */ import java.util.WeakHashMap;
/*       */ import javax.accessibility.Accessible;
/*       */ import javax.accessibility.AccessibleComponent;
/*       */ import javax.accessibility.AccessibleContext;
/*       */ import javax.accessibility.AccessibleRole;
/*       */ import javax.accessibility.AccessibleSelection;
/*       */ import javax.accessibility.AccessibleState;
/*       */ import javax.accessibility.AccessibleStateSet;
/*       */ import javax.swing.JComponent;
/*       */ import sun.awt.AWTAccessor;
/*       */ import sun.awt.AppContext;
/*       */ import sun.awt.CausedFocusEvent;
/*       */ import sun.awt.ConstrainableGraphics;
/*       */ import sun.awt.EmbeddedFrame;
/*       */ import sun.awt.EventQueueItem;
/*       */ import sun.awt.RequestFocusController;
/*       */ import sun.awt.SubRegionShowable;
/*       */ import sun.awt.SunToolkit;
/*       */ import sun.awt.WindowClosingListener;
/*       */ import sun.awt.dnd.SunDropTargetEvent;
/*       */ import sun.awt.im.InputContext;
/*       */ import sun.awt.image.VSyncedBSManager;
/*       */ import sun.font.FontDesignMetrics;
/*       */ import sun.font.FontManager;
/*       */ import sun.font.FontManagerFactory;
/*       */ import sun.font.SunFontManager;
/*       */ import sun.java2d.SunGraphics2D;
/*       */ import sun.java2d.SunGraphicsEnvironment;
/*       */ import sun.java2d.pipe.Region;
/*       */ import sun.java2d.pipe.hw.ExtendedBufferCapabilities;
/*       */ import sun.security.action.GetPropertyAction;
/*       */ import sun.util.logging.PlatformLogger;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ public abstract class Component
/*       */   implements ImageObserver, MenuContainer, Serializable
/*       */ {
/*   190 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.Component");
/*   191 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.Component");
/*   192 */   private static final PlatformLogger focusLog = PlatformLogger.getLogger("java.awt.focus.Component");
/*   193 */   private static final PlatformLogger mixingLog = PlatformLogger.getLogger("java.awt.mixing.Component");
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   transient ComponentPeer peer;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   transient Container parent;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   transient AppContext appContext;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   int x;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   int y;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   int width;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   int height;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Color foreground;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Color background;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   volatile Font font;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Font peerFont;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Cursor cursor;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Locale locale;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private volatile transient GraphicsConfiguration graphicsConfig;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   325 */   transient BufferStrategy bufferStrategy = null;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean ignoreRepaint = false;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean visible = true;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean enabled = true;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private volatile boolean valid = false;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   DropTarget dropTarget;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Vector<PopupMenu> popups;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private String name;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean nameExplicitlySet = false;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean focusable = true;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static final int FOCUS_TRAVERSABLE_UNKNOWN = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static final int FOCUS_TRAVERSABLE_DEFAULT = 1;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private static final int FOCUS_TRAVERSABLE_SET = 2;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   427 */   private int isFocusTraversableOverridden = 0;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Set<AWTKeyStroke>[] focusTraversalKeys;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   444 */   private static final String[] focusTraversalKeyPropertyNames = new String[] { "forwardFocusTraversalKeys", "backwardFocusTraversalKeys", "upCycleFocusTraversalKeys", "downCycleFocusTraversalKeys" };
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private boolean focusTraversalKeysEnabled = true;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   470 */   static final Object LOCK = new AWTTreeLock();
/*       */ 
/*       */ 
/*       */   
/*       */   static class AWTTreeLock {}
/*       */ 
/*       */   
/*   477 */   private volatile transient AccessControlContext acc = AccessController.getContext();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Dimension minSize;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean minSizeSet;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Dimension prefSize;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean prefSizeSet;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Dimension maxSize;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean maxSizeSet;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*   522 */   transient ComponentOrientation componentOrientation = ComponentOrientation.UNKNOWN;
/*       */ 
/*       */   
/*       */   boolean newEventsOnly = false;
/*       */ 
/*       */   
/*       */   transient ComponentListener componentListener;
/*       */ 
/*       */   
/*       */   transient FocusListener focusListener;
/*       */   
/*       */   transient HierarchyListener hierarchyListener;
/*       */   
/*       */   transient HierarchyBoundsListener hierarchyBoundsListener;
/*       */   
/*       */   transient KeyListener keyListener;
/*       */   
/*       */   transient MouseListener mouseListener;
/*       */   
/*       */   transient MouseMotionListener mouseMotionListener;
/*       */   
/*       */   transient MouseWheelListener mouseWheelListener;
/*       */   
/*       */   transient InputMethodListener inputMethodListener;
/*       */   
/*   547 */   transient RuntimeException windowClosingException = null;
/*       */   
/*       */   static final String actionListenerK = "actionL";
/*       */   
/*       */   static final String adjustmentListenerK = "adjustmentL";
/*       */   
/*       */   static final String componentListenerK = "componentL";
/*       */   
/*       */   static final String containerListenerK = "containerL";
/*       */   
/*       */   static final String focusListenerK = "focusL";
/*       */   
/*       */   static final String itemListenerK = "itemL";
/*       */   
/*       */   static final String keyListenerK = "keyL";
/*       */   
/*       */   static final String mouseListenerK = "mouseL";
/*       */   
/*       */   static final String mouseMotionListenerK = "mouseMotionL";
/*       */   
/*       */   static final String mouseWheelListenerK = "mouseWheelL";
/*       */   
/*       */   static final String textListenerK = "textL";
/*       */   
/*       */   static final String ownedWindowK = "ownedL";
/*       */   
/*       */   static final String windowListenerK = "windowL";
/*       */   
/*       */   static final String inputMethodListenerK = "inputMethodL";
/*       */   
/*       */   static final String hierarchyListenerK = "hierarchyL";
/*       */   
/*       */   static final String hierarchyBoundsListenerK = "hierarchyBoundsL";
/*       */   
/*       */   static final String windowStateListenerK = "windowStateL";
/*       */   static final String windowFocusListenerK = "windowFocusL";
/*   583 */   long eventMask = 4096L; static boolean isInc;
/*       */   static int incRate;
/*       */   public static final float TOP_ALIGNMENT = 0.0F;
/*       */   public static final float CENTER_ALIGNMENT = 0.5F;
/*       */   public static final float BOTTOM_ALIGNMENT = 1.0F;
/*       */   public static final float LEFT_ALIGNMENT = 0.0F;
/*       */   public static final float RIGHT_ALIGNMENT = 1.0F;
/*       */   private static final long serialVersionUID = -7644114512714619750L;
/*       */   private PropertyChangeSupport changeSupport;
/*       */   
/*   593 */   static { Toolkit.loadLibraries();
/*       */     
/*   595 */     if (!GraphicsEnvironment.isHeadless()) {
/*   596 */       initIDs();
/*       */     }
/*       */     
/*   599 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("awt.image.incrementaldraw"));
/*       */     
/*   601 */     isInc = (str == null || str.equals("true"));
/*       */     
/*   603 */     str = AccessController.<String>doPrivileged(new GetPropertyAction("awt.image.redrawrate"));
/*       */     
/*   605 */     incRate = (str != null) ? Integer.parseInt(str) : 100;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*   839 */     AWTAccessor.setComponentAccessor(new AWTAccessor.ComponentAccessor() {
/*       */           public void setBackgroundEraseDisabled(Component param1Component, boolean param1Boolean) {
/*   841 */             param1Component.backgroundEraseDisabled = param1Boolean;
/*       */           }
/*       */           public boolean getBackgroundEraseDisabled(Component param1Component) {
/*   844 */             return param1Component.backgroundEraseDisabled;
/*       */           }
/*       */           public Rectangle getBounds(Component param1Component) {
/*   847 */             return new Rectangle(param1Component.x, param1Component.y, param1Component.width, param1Component.height);
/*       */           }
/*       */           
/*       */           public void setMixingCutoutShape(Component param1Component, Shape param1Shape) {
/*   851 */             Region region = (param1Shape == null) ? null : Region.getInstance(param1Shape, (AffineTransform)null);
/*       */             
/*   853 */             synchronized (param1Component.getTreeLock()) {
/*   854 */               boolean bool1 = false;
/*   855 */               boolean bool2 = false;
/*       */               
/*   857 */               if (!param1Component.isNonOpaqueForMixing()) {
/*   858 */                 bool2 = true;
/*       */               }
/*       */               
/*   861 */               param1Component.mixingCutoutRegion = region;
/*       */               
/*   863 */               if (!param1Component.isNonOpaqueForMixing()) {
/*   864 */                 bool1 = true;
/*       */               }
/*       */               
/*   867 */               if (param1Component.isMixingNeeded()) {
/*   868 */                 if (bool2) {
/*   869 */                   param1Component.mixOnHiding(param1Component.isLightweight());
/*       */                 }
/*   871 */                 if (bool1) {
/*   872 */                   param1Component.mixOnShowing();
/*       */                 }
/*       */               } 
/*       */             } 
/*       */           }
/*       */ 
/*       */ 
/*       */           
/*       */           public void setGraphicsConfiguration(Component param1Component, GraphicsConfiguration param1GraphicsConfiguration) {
/*   881 */             param1Component.setGraphicsConfiguration(param1GraphicsConfiguration);
/*       */           }
/*       */           public boolean requestFocus(Component param1Component, CausedFocusEvent.Cause param1Cause) {
/*   884 */             return param1Component.requestFocus(param1Cause);
/*       */           }
/*       */           public boolean canBeFocusOwner(Component param1Component) {
/*   887 */             return param1Component.canBeFocusOwner();
/*       */           }
/*       */           
/*       */           public boolean isVisible(Component param1Component) {
/*   891 */             return param1Component.isVisible_NoClientCode();
/*       */           }
/*       */ 
/*       */           
/*       */           public void setRequestFocusController(RequestFocusController param1RequestFocusController) {
/*   896 */             Component.setRequestFocusController(param1RequestFocusController);
/*       */           }
/*       */           public AppContext getAppContext(Component param1Component) {
/*   899 */             return param1Component.appContext;
/*       */           }
/*       */           public void setAppContext(Component param1Component, AppContext param1AppContext) {
/*   902 */             param1Component.appContext = param1AppContext;
/*       */           }
/*       */           public Container getParent(Component param1Component) {
/*   905 */             return param1Component.getParent_NoClientCode();
/*       */           }
/*       */           public void setParent(Component param1Component, Container param1Container) {
/*   908 */             param1Component.parent = param1Container;
/*       */           }
/*       */           public void setSize(Component param1Component, int param1Int1, int param1Int2) {
/*   911 */             param1Component.width = param1Int1;
/*   912 */             param1Component.height = param1Int2;
/*       */           }
/*       */           public Point getLocation(Component param1Component) {
/*   915 */             return param1Component.location_NoClientCode();
/*       */           }
/*       */           public void setLocation(Component param1Component, int param1Int1, int param1Int2) {
/*   918 */             param1Component.x = param1Int1;
/*   919 */             param1Component.y = param1Int2;
/*       */           }
/*       */           public boolean isEnabled(Component param1Component) {
/*   922 */             return param1Component.isEnabledImpl();
/*       */           }
/*       */           public boolean isDisplayable(Component param1Component) {
/*   925 */             return (param1Component.peer != null);
/*       */           }
/*       */           public Cursor getCursor(Component param1Component) {
/*   928 */             return param1Component.getCursor_NoClientCode();
/*       */           }
/*       */           public ComponentPeer getPeer(Component param1Component) {
/*   931 */             return param1Component.peer;
/*       */           }
/*       */           public void setPeer(Component param1Component, ComponentPeer param1ComponentPeer) {
/*   934 */             param1Component.peer = param1ComponentPeer;
/*       */           }
/*       */           public boolean isLightweight(Component param1Component) {
/*   937 */             return param1Component.peer instanceof java.awt.peer.LightweightPeer;
/*       */           }
/*       */           public boolean getIgnoreRepaint(Component param1Component) {
/*   940 */             return param1Component.ignoreRepaint;
/*       */           }
/*       */           public int getWidth(Component param1Component) {
/*   943 */             return param1Component.width;
/*       */           }
/*       */           public int getHeight(Component param1Component) {
/*   946 */             return param1Component.height;
/*       */           }
/*       */           public int getX(Component param1Component) {
/*   949 */             return param1Component.x;
/*       */           }
/*       */           public int getY(Component param1Component) {
/*   952 */             return param1Component.y;
/*       */           }
/*       */           public Color getForeground(Component param1Component) {
/*   955 */             return param1Component.foreground;
/*       */           }
/*       */           public Color getBackground(Component param1Component) {
/*   958 */             return param1Component.background;
/*       */           }
/*       */           public void setBackground(Component param1Component, Color param1Color) {
/*   961 */             param1Component.background = param1Color;
/*       */           }
/*       */           public Font getFont(Component param1Component) {
/*   964 */             return param1Component.getFont_NoClientCode();
/*       */           }
/*       */           public void processEvent(Component param1Component, AWTEvent param1AWTEvent) {
/*   967 */             param1Component.processEvent(param1AWTEvent);
/*       */           }
/*       */           
/*       */           public AccessControlContext getAccessControlContext(Component param1Component) {
/*   971 */             return param1Component.getAccessControlContext();
/*       */           }
/*       */           
/*       */           public void revalidateSynchronously(Component param1Component) {
/*   975 */             param1Component.revalidateSynchronously();
/*       */           }
/*       */         }); } private transient Object objectLock = new Object(); Object getObjectLock() { return this.objectLock; } final AccessControlContext getAccessControlContext() {
/*       */     if (this.acc == null)
/*       */       throw new SecurityException("Component is missing AccessControlContext"); 
/*       */     return this.acc;
/*       */   } boolean isPacked = false; private int boundsOp = 3; public enum BaselineResizeBehavior {
/*       */     CONSTANT_ASCENT, CONSTANT_DESCENT, CENTER_OFFSET, OTHER; } private transient Region compoundShape = null; private transient Region mixingCutoutRegion = null; private transient boolean isAddNotifyComplete = false; transient boolean backgroundEraseDisabled; transient EventQueueItem[] eventCache; private transient boolean coalescingEnabled; int getBoundsOp() {
/*       */     assert Thread.holdsLock(getTreeLock());
/*       */     return this.boundsOp;
/*       */   } void setBoundsOp(int paramInt) {
/*       */     assert Thread.holdsLock(getTreeLock());
/*       */     if (paramInt == 5) {
/*       */       this.boundsOp = 3;
/*       */     } else if (this.boundsOp == 3) {
/*       */       this.boundsOp = paramInt;
/*       */     } 
/*       */   } void initializeFocusTraversalKeys() {
/*   993 */     this.focusTraversalKeys = (Set<AWTKeyStroke>[])new Set[3];
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   String constructComponentName() {
/*  1001 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public String getName() {
/*  1013 */     if (this.name == null && !this.nameExplicitlySet)
/*  1014 */       synchronized (getObjectLock()) {
/*  1015 */         if (this.name == null && !this.nameExplicitlySet) {
/*  1016 */           this.name = constructComponentName();
/*       */         }
/*       */       }  
/*  1019 */     return this.name;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setName(String paramString) {
/*       */     String str;
/*  1031 */     synchronized (getObjectLock()) {
/*  1032 */       str = this.name;
/*  1033 */       this.name = paramString;
/*  1034 */       this.nameExplicitlySet = true;
/*       */     } 
/*  1036 */     firePropertyChange("name", str, paramString);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Container getParent() {
/*  1045 */     return getParent_NoClientCode();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final Container getParent_NoClientCode() {
/*  1053 */     return this.parent;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Container getContainer() {
/*  1060 */     return getParent_NoClientCode();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public ComponentPeer getPeer() {
/*  1070 */     return this.peer;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void setDropTarget(DropTarget paramDropTarget) {
/*  1083 */     if (paramDropTarget == this.dropTarget || (this.dropTarget != null && this.dropTarget.equals(paramDropTarget))) {
/*       */       return;
/*       */     }
/*       */     
/*       */     DropTarget dropTarget;
/*  1088 */     if ((dropTarget = this.dropTarget) != null) {
/*  1089 */       if (this.peer != null) this.dropTarget.removeNotify(this.peer);
/*       */       
/*  1091 */       DropTarget dropTarget1 = this.dropTarget;
/*       */       
/*  1093 */       this.dropTarget = null;
/*       */       
/*       */       try {
/*  1096 */         dropTarget1.setComponent((Component)null);
/*  1097 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  1104 */     if ((this.dropTarget = paramDropTarget) != null) {
/*       */       try {
/*  1106 */         this.dropTarget.setComponent(this);
/*  1107 */         if (this.peer != null) this.dropTarget.addNotify(this.peer); 
/*  1108 */       } catch (IllegalArgumentException illegalArgumentException) {
/*  1109 */         if (dropTarget != null) {
/*       */           try {
/*  1111 */             dropTarget.setComponent(this);
/*  1112 */             if (this.peer != null) this.dropTarget.addNotify(this.peer); 
/*  1113 */           } catch (IllegalArgumentException illegalArgumentException1) {}
/*       */         }
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized DropTarget getDropTarget() {
/*  1126 */     return this.dropTarget;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public GraphicsConfiguration getGraphicsConfiguration() {
/*  1144 */     return getGraphicsConfiguration_NoClientCode();
/*       */   }
/*       */   
/*       */   final GraphicsConfiguration getGraphicsConfiguration_NoClientCode() {
/*  1148 */     return this.graphicsConfig;
/*       */   }
/*       */   
/*       */   void setGraphicsConfiguration(GraphicsConfiguration paramGraphicsConfiguration) {
/*  1152 */     synchronized (getTreeLock()) {
/*  1153 */       if (updateGraphicsData(paramGraphicsConfiguration)) {
/*  1154 */         removeNotify();
/*  1155 */         addNotify();
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/*  1161 */     checkTreeLock();
/*       */     
/*  1163 */     if (this.graphicsConfig == paramGraphicsConfiguration) {
/*  1164 */       return false;
/*       */     }
/*       */     
/*  1167 */     this.graphicsConfig = paramGraphicsConfiguration;
/*       */     
/*  1169 */     ComponentPeer componentPeer = getPeer();
/*  1170 */     if (componentPeer != null) {
/*  1171 */       return componentPeer.updateGraphicsData(paramGraphicsConfiguration);
/*       */     }
/*  1173 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void checkGD(String paramString) {
/*  1181 */     if (this.graphicsConfig != null && 
/*  1182 */       !this.graphicsConfig.getDevice().getIDstring().equals(paramString)) {
/*  1183 */       throw new IllegalArgumentException("adding a container to a container on a different GraphicsDevice");
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public final Object getTreeLock() {
/*  1196 */     return LOCK;
/*       */   }
/*       */   
/*       */   final void checkTreeLock() {
/*  1200 */     if (!Thread.holdsLock(getTreeLock())) {
/*  1201 */       throw new IllegalStateException("This function should be called while holding treeLock");
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Toolkit getToolkit() {
/*  1214 */     return getToolkitImpl();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final Toolkit getToolkitImpl() {
/*  1222 */     Container container = this.parent;
/*  1223 */     if (container != null) {
/*  1224 */       return container.getToolkitImpl();
/*       */     }
/*  1226 */     return Toolkit.getDefaultToolkit();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isValid() {
/*  1243 */     return (this.peer != null && this.valid);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isDisplayable() {
/*  1271 */     return (getPeer() != null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Transient
/*       */   public boolean isVisible() {
/*  1286 */     return isVisible_NoClientCode();
/*       */   }
/*       */   final boolean isVisible_NoClientCode() {
/*  1289 */     return this.visible;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean isRecursivelyVisible() {
/*  1299 */     return (this.visible && (this.parent == null || this.parent.isRecursivelyVisible()));
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Rectangle getRecursivelyVisibleBounds() {
/*  1309 */     Container container = getContainer();
/*  1310 */     Rectangle rectangle1 = getBounds();
/*  1311 */     if (container == null)
/*       */     {
/*  1313 */       return rectangle1;
/*       */     }
/*       */     
/*  1316 */     Rectangle rectangle2 = container.getRecursivelyVisibleBounds();
/*  1317 */     rectangle2.setLocation(0, 0);
/*  1318 */     return rectangle2.intersection(rectangle1);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Point pointRelativeToComponent(Point paramPoint) {
/*  1326 */     Point point = getLocationOnScreen();
/*  1327 */     return new Point(paramPoint.x - point.x, paramPoint.y - point.y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Component findUnderMouseInWindow(PointerInfo paramPointerInfo) {
/*  1341 */     if (!isShowing()) {
/*  1342 */       return null;
/*       */     }
/*  1344 */     Window window = getContainingWindow();
/*  1345 */     if (!Toolkit.getDefaultToolkit().getMouseInfoPeer().isWindowUnderMouse(window)) {
/*  1346 */       return null;
/*       */     }
/*       */     
/*  1349 */     Point point = window.pointRelativeToComponent(paramPointerInfo.getLocation());
/*  1350 */     return window.findComponentAt(point.x, point.y, true);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Point getMousePosition() throws HeadlessException {
/*  1384 */     if (GraphicsEnvironment.isHeadless()) {
/*  1385 */       throw new HeadlessException();
/*       */     }
/*       */     
/*  1388 */     PointerInfo pointerInfo = AccessController.<PointerInfo>doPrivileged(new PrivilegedAction<PointerInfo>()
/*       */         {
/*       */           public PointerInfo run() {
/*  1391 */             return MouseInfo.getPointerInfo();
/*       */           }
/*       */         });
/*       */ 
/*       */     
/*  1396 */     synchronized (getTreeLock()) {
/*  1397 */       Component component = findUnderMouseInWindow(pointerInfo);
/*  1398 */       if (!isSameOrAncestorOf(component, true)) {
/*  1399 */         return null;
/*       */       }
/*  1401 */       return pointRelativeToComponent(pointerInfo.getLocation());
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean isSameOrAncestorOf(Component paramComponent, boolean paramBoolean) {
/*  1409 */     return (paramComponent == this);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isShowing() {
/*  1431 */     if (this.visible && this.peer != null) {
/*  1432 */       Container container = this.parent;
/*  1433 */       return (container == null || container.isShowing());
/*       */     } 
/*  1435 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isEnabled() {
/*  1449 */     return isEnabledImpl();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final boolean isEnabledImpl() {
/*  1457 */     return this.enabled;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setEnabled(boolean paramBoolean) {
/*  1478 */     enable(paramBoolean);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void enable() {
/*  1487 */     if (!this.enabled) {
/*  1488 */       synchronized (getTreeLock()) {
/*  1489 */         this.enabled = true;
/*  1490 */         ComponentPeer componentPeer = this.peer;
/*  1491 */         if (componentPeer != null) {
/*  1492 */           componentPeer.setEnabled(true);
/*  1493 */           if (this.visible && !getRecursivelyVisibleBounds().isEmpty()) {
/*  1494 */             updateCursorImmediately();
/*       */           }
/*       */         } 
/*       */       } 
/*  1498 */       if (this.accessibleContext != null) {
/*  1499 */         this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void enable(boolean paramBoolean) {
/*  1512 */     if (paramBoolean) {
/*  1513 */       enable();
/*       */     } else {
/*  1515 */       disable();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void disable() {
/*  1525 */     if (this.enabled) {
/*  1526 */       KeyboardFocusManager.clearMostRecentFocusOwner(this);
/*  1527 */       synchronized (getTreeLock()) {
/*  1528 */         this.enabled = false;
/*       */         
/*  1530 */         if ((isFocusOwner() || (containsFocus() && !isLightweight())) && 
/*  1531 */           KeyboardFocusManager.isAutoFocusTransferEnabled())
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  1537 */           transferFocus(false);
/*       */         }
/*  1539 */         ComponentPeer componentPeer = this.peer;
/*  1540 */         if (componentPeer != null) {
/*  1541 */           componentPeer.setEnabled(false);
/*  1542 */           if (this.visible && !getRecursivelyVisibleBounds().isEmpty()) {
/*  1543 */             updateCursorImmediately();
/*       */           }
/*       */         } 
/*       */       } 
/*  1547 */       if (this.accessibleContext != null) {
/*  1548 */         this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isDoubleBuffered() {
/*  1564 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void enableInputMethods(boolean paramBoolean) {
/*  1580 */     if (paramBoolean) {
/*  1581 */       if ((this.eventMask & 0x1000L) != 0L) {
/*       */         return;
/*       */       }
/*       */ 
/*       */ 
/*       */       
/*  1587 */       if (isFocusOwner()) {
/*  1588 */         InputContext inputContext = getInputContext();
/*  1589 */         if (inputContext != null) {
/*  1590 */           FocusEvent focusEvent = new FocusEvent(this, 1004);
/*       */           
/*  1592 */           inputContext.dispatchEvent(focusEvent);
/*       */         } 
/*       */       } 
/*       */       
/*  1596 */       this.eventMask |= 0x1000L;
/*       */     } else {
/*  1598 */       if ((this.eventMask & 0x1000L) != 0L) {
/*  1599 */         InputContext inputContext = getInputContext();
/*  1600 */         if (inputContext != null) {
/*  1601 */           inputContext.endComposition();
/*  1602 */           inputContext.removeNotify(this);
/*       */         } 
/*       */       } 
/*  1605 */       this.eventMask &= 0xFFFFFFFFFFFFEFFFL;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setVisible(boolean paramBoolean) {
/*  1623 */     show(paramBoolean);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void show() {
/*  1632 */     if (!this.visible) {
/*  1633 */       synchronized (getTreeLock()) {
/*  1634 */         this.visible = true;
/*  1635 */         mixOnShowing();
/*  1636 */         ComponentPeer componentPeer = this.peer;
/*  1637 */         if (componentPeer != null) {
/*  1638 */           componentPeer.setVisible(true);
/*  1639 */           createHierarchyEvents(1400, this, this.parent, 4L, 
/*       */ 
/*       */               
/*  1642 */               Toolkit.enabledOnToolkit(32768L));
/*  1643 */           if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*  1644 */             repaint();
/*       */           }
/*  1646 */           updateCursorImmediately();
/*       */         } 
/*       */         
/*  1649 */         if (this.componentListener != null || (this.eventMask & 0x1L) != 0L || 
/*       */           
/*  1651 */           Toolkit.enabledOnToolkit(1L)) {
/*  1652 */           ComponentEvent componentEvent = new ComponentEvent(this, 102);
/*       */           
/*  1654 */           Toolkit.getEventQueue().postEvent(componentEvent);
/*       */         } 
/*       */       } 
/*  1657 */       Container container = this.parent;
/*  1658 */       if (container != null) {
/*  1659 */         container.invalidate();
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void show(boolean paramBoolean) {
/*  1670 */     if (paramBoolean) {
/*  1671 */       show();
/*       */     } else {
/*  1673 */       hide();
/*       */     } 
/*       */   }
/*       */   
/*       */   boolean containsFocus() {
/*  1678 */     return isFocusOwner();
/*       */   }
/*       */   
/*       */   void clearMostRecentFocusOwnerOnHide() {
/*  1682 */     KeyboardFocusManager.clearMostRecentFocusOwner(this);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void clearCurrentFocusCycleRootOnHide() {}
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void hide() {
/*  1695 */     this.isPacked = false;
/*       */     
/*  1697 */     if (this.visible) {
/*  1698 */       clearCurrentFocusCycleRootOnHide();
/*  1699 */       clearMostRecentFocusOwnerOnHide();
/*  1700 */       synchronized (getTreeLock()) {
/*  1701 */         this.visible = false;
/*  1702 */         mixOnHiding(isLightweight());
/*  1703 */         if (containsFocus() && KeyboardFocusManager.isAutoFocusTransferEnabled()) {
/*  1704 */           transferFocus(true);
/*       */         }
/*  1706 */         ComponentPeer componentPeer = this.peer;
/*  1707 */         if (componentPeer != null) {
/*  1708 */           componentPeer.setVisible(false);
/*  1709 */           createHierarchyEvents(1400, this, this.parent, 4L, 
/*       */ 
/*       */               
/*  1712 */               Toolkit.enabledOnToolkit(32768L));
/*  1713 */           if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*  1714 */             repaint();
/*       */           }
/*  1716 */           updateCursorImmediately();
/*       */         } 
/*  1718 */         if (this.componentListener != null || (this.eventMask & 0x1L) != 0L || 
/*       */           
/*  1720 */           Toolkit.enabledOnToolkit(1L)) {
/*  1721 */           ComponentEvent componentEvent = new ComponentEvent(this, 103);
/*       */           
/*  1723 */           Toolkit.getEventQueue().postEvent(componentEvent);
/*       */         } 
/*       */       } 
/*  1726 */       Container container = this.parent;
/*  1727 */       if (container != null) {
/*  1728 */         container.invalidate();
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Transient
/*       */   public Color getForeground() {
/*  1745 */     Color color = this.foreground;
/*  1746 */     if (color != null) {
/*  1747 */       return color;
/*       */     }
/*  1749 */     Container container = this.parent;
/*  1750 */     return (container != null) ? container.getForeground() : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setForeground(Color paramColor) {
/*  1763 */     Color color = this.foreground;
/*  1764 */     ComponentPeer componentPeer = this.peer;
/*  1765 */     this.foreground = paramColor;
/*  1766 */     if (componentPeer != null) {
/*  1767 */       paramColor = getForeground();
/*  1768 */       if (paramColor != null) {
/*  1769 */         componentPeer.setForeground(paramColor);
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/*  1774 */     firePropertyChange("foreground", color, paramColor);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isForegroundSet() {
/*  1787 */     return (this.foreground != null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Transient
/*       */   public Color getBackground() {
/*  1800 */     Color color = this.background;
/*  1801 */     if (color != null) {
/*  1802 */       return color;
/*       */     }
/*  1804 */     Container container = this.parent;
/*  1805 */     return (container != null) ? container.getBackground() : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBackground(Color paramColor) {
/*  1824 */     Color color = this.background;
/*  1825 */     ComponentPeer componentPeer = this.peer;
/*  1826 */     this.background = paramColor;
/*  1827 */     if (componentPeer != null) {
/*  1828 */       paramColor = getBackground();
/*  1829 */       if (paramColor != null) {
/*  1830 */         componentPeer.setBackground(paramColor);
/*       */       }
/*       */     } 
/*       */ 
/*       */     
/*  1835 */     firePropertyChange("background", color, paramColor);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isBackgroundSet() {
/*  1848 */     return (this.background != null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Transient
/*       */   public Font getFont() {
/*  1860 */     return getFont_NoClientCode();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final Font getFont_NoClientCode() {
/*  1868 */     Font font = this.font;
/*  1869 */     if (font != null) {
/*  1870 */       return font;
/*       */     }
/*  1872 */     Container container = this.parent;
/*  1873 */     return (container != null) ? container.getFont_NoClientCode() : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setFont(Font paramFont) {
/*       */     Font font1, font2;
/*  1893 */     synchronized (getTreeLock()) {
/*  1894 */       font1 = this.font;
/*  1895 */       font2 = this.font = paramFont;
/*  1896 */       ComponentPeer componentPeer = this.peer;
/*  1897 */       if (componentPeer != null) {
/*  1898 */         paramFont = getFont();
/*  1899 */         if (paramFont != null) {
/*  1900 */           componentPeer.setFont(paramFont);
/*  1901 */           this.peerFont = paramFont;
/*       */         } 
/*       */       } 
/*       */     } 
/*       */ 
/*       */     
/*  1907 */     firePropertyChange("font", font1, font2);
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  1912 */     if (paramFont != font1 && (font1 == null || 
/*  1913 */       !font1.equals(paramFont))) {
/*  1914 */       invalidateIfValid();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isFontSet() {
/*  1928 */     return (this.font != null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Locale getLocale() {
/*  1943 */     Locale locale = this.locale;
/*  1944 */     if (locale != null) {
/*  1945 */       return locale;
/*       */     }
/*  1947 */     Container container = this.parent;
/*       */     
/*  1949 */     if (container == null) {
/*  1950 */       throw new IllegalComponentStateException("This component must have a parent in order to determine its locale");
/*       */     }
/*  1952 */     return container.getLocale();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setLocale(Locale paramLocale) {
/*  1968 */     Locale locale = this.locale;
/*  1969 */     this.locale = paramLocale;
/*       */ 
/*       */ 
/*       */     
/*  1973 */     firePropertyChange("locale", locale, paramLocale);
/*       */ 
/*       */     
/*  1976 */     invalidateIfValid();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public ColorModel getColorModel() {
/*  1989 */     ComponentPeer componentPeer = this.peer;
/*  1990 */     if (componentPeer != null && !(componentPeer instanceof java.awt.peer.LightweightPeer))
/*  1991 */       return componentPeer.getColorModel(); 
/*  1992 */     if (GraphicsEnvironment.isHeadless()) {
/*  1993 */       return ColorModel.getRGBdefault();
/*       */     }
/*  1995 */     return getToolkit().getColorModel();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Point getLocation() {
/*  2019 */     return location();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Point getLocationOnScreen() {
/*  2035 */     synchronized (getTreeLock()) {
/*  2036 */       return getLocationOnScreen_NoTreeLock();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final Point getLocationOnScreen_NoTreeLock() {
/*  2046 */     if (this.peer != null && isShowing()) {
/*  2047 */       if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*       */ 
/*       */         
/*  2050 */         Container container = getNativeContainer();
/*  2051 */         Point point = container.peer.getLocationOnScreen();
/*  2052 */         for (Component component = this; component != container; component = component.getParent()) {
/*  2053 */           point.x += component.x;
/*  2054 */           point.y += component.y;
/*       */         } 
/*  2056 */         return point;
/*       */       } 
/*  2058 */       return this.peer.getLocationOnScreen();
/*       */     } 
/*       */ 
/*       */     
/*  2062 */     throw new IllegalComponentStateException("component must be showing on the screen to determine its location");
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public Point location() {
/*  2073 */     return location_NoClientCode();
/*       */   }
/*       */   
/*       */   private Point location_NoClientCode() {
/*  2077 */     return new Point(this.x, this.y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setLocation(int paramInt1, int paramInt2) {
/*  2098 */     move(paramInt1, paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void move(int paramInt1, int paramInt2) {
/*  2107 */     synchronized (getTreeLock()) {
/*  2108 */       setBoundsOp(1);
/*  2109 */       setBounds(paramInt1, paramInt2, this.width, this.height);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setLocation(Point paramPoint) {
/*  2130 */     setLocation(paramPoint.x, paramPoint.y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Dimension getSize() {
/*  2146 */     return size();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public Dimension size() {
/*  2155 */     return new Dimension(this.width, this.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setSize(int paramInt1, int paramInt2) {
/*  2173 */     resize(paramInt1, paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void resize(int paramInt1, int paramInt2) {
/*  2182 */     synchronized (getTreeLock()) {
/*  2183 */       setBoundsOp(2);
/*  2184 */       setBounds(this.x, this.y, paramInt1, paramInt2);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setSize(Dimension paramDimension) {
/*  2204 */     resize(paramDimension);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void resize(Dimension paramDimension) {
/*  2213 */     setSize(paramDimension.width, paramDimension.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Rectangle getBounds() {
/*  2227 */     return bounds();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public Rectangle bounds() {
/*  2236 */     return new Rectangle(this.x, this.y, this.width, this.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  2261 */     reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  2270 */     synchronized (getTreeLock()) {
/*       */       try {
/*  2272 */         setBoundsOp(3);
/*  2273 */         boolean bool1 = (this.width != paramInt3 || this.height != paramInt4) ? true : false;
/*  2274 */         boolean bool2 = (this.x != paramInt1 || this.y != paramInt2) ? true : false;
/*  2275 */         if (!bool1 && !bool2) {
/*       */           return;
/*       */         }
/*  2278 */         int i = this.x;
/*  2279 */         int j = this.y;
/*  2280 */         int k = this.width;
/*  2281 */         int m = this.height;
/*  2282 */         this.x = paramInt1;
/*  2283 */         this.y = paramInt2;
/*  2284 */         this.width = paramInt3;
/*  2285 */         this.height = paramInt4;
/*       */         
/*  2287 */         if (bool1) {
/*  2288 */           this.isPacked = false;
/*       */         }
/*       */         
/*  2291 */         boolean bool3 = true;
/*  2292 */         mixOnReshaping();
/*  2293 */         if (this.peer != null) {
/*       */           
/*  2295 */           if (!(this.peer instanceof java.awt.peer.LightweightPeer)) {
/*  2296 */             reshapeNativePeer(paramInt1, paramInt2, paramInt3, paramInt4, getBoundsOp());
/*       */             
/*  2298 */             bool1 = (k != this.width || m != this.height) ? true : false;
/*  2299 */             bool2 = (i != this.x || j != this.y) ? true : false;
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*  2304 */             if (this instanceof Window) {
/*  2305 */               bool3 = false;
/*       */             }
/*       */           } 
/*  2308 */           if (bool1) {
/*  2309 */             invalidate();
/*       */           }
/*  2311 */           if (this.parent != null) {
/*  2312 */             this.parent.invalidateIfValid();
/*       */           }
/*       */         } 
/*  2315 */         if (bool3) {
/*  2316 */           notifyNewBounds(bool1, bool2);
/*       */         }
/*  2318 */         repaintParentIfNeeded(i, j, k, m);
/*       */       } finally {
/*  2320 */         setBoundsOp(5);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void repaintParentIfNeeded(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  2328 */     if (this.parent != null && this.peer instanceof java.awt.peer.LightweightPeer && isShowing()) {
/*       */       
/*  2330 */       this.parent.repaint(paramInt1, paramInt2, paramInt3, paramInt4);
/*       */       
/*  2332 */       repaint();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private void reshapeNativePeer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  2339 */     int i = paramInt1;
/*  2340 */     int j = paramInt2;
/*  2341 */     Container container = this.parent;
/*  2342 */     for (; container != null && container.peer instanceof java.awt.peer.LightweightPeer; 
/*  2343 */       container = container.parent) {
/*       */       
/*  2345 */       i += container.x;
/*  2346 */       j += container.y;
/*       */     } 
/*  2348 */     this.peer.setBounds(i, j, paramInt3, paramInt4, paramInt5);
/*       */   }
/*       */ 
/*       */   
/*       */   private void notifyNewBounds(boolean paramBoolean1, boolean paramBoolean2) {
/*  2353 */     if (this.componentListener != null || (this.eventMask & 0x1L) != 0L || 
/*       */       
/*  2355 */       Toolkit.enabledOnToolkit(1L)) {
/*       */       
/*  2357 */       if (paramBoolean1) {
/*  2358 */         ComponentEvent componentEvent = new ComponentEvent(this, 101);
/*       */         
/*  2360 */         Toolkit.getEventQueue().postEvent(componentEvent);
/*       */       } 
/*  2362 */       if (paramBoolean2) {
/*  2363 */         ComponentEvent componentEvent = new ComponentEvent(this, 100);
/*       */         
/*  2365 */         Toolkit.getEventQueue().postEvent(componentEvent);
/*       */       }
/*       */     
/*  2368 */     } else if (this instanceof Container && ((Container)this).countComponents() > 0) {
/*       */       
/*  2370 */       boolean bool = Toolkit.enabledOnToolkit(65536L);
/*  2371 */       if (paramBoolean1)
/*       */       {
/*  2373 */         ((Container)this).createChildHierarchyEvents(1402, 0L, bool);
/*       */       }
/*       */       
/*  2376 */       if (paramBoolean2) {
/*  2377 */         ((Container)this).createChildHierarchyEvents(1401, 0L, bool);
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setBounds(Rectangle paramRectangle) {
/*  2405 */     setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getX() {
/*  2420 */     return this.x;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getY() {
/*  2435 */     return this.y;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getWidth() {
/*  2450 */     return this.width;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getHeight() {
/*  2465 */     return this.height;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Rectangle getBounds(Rectangle paramRectangle) {
/*  2480 */     if (paramRectangle == null) {
/*  2481 */       return new Rectangle(getX(), getY(), getWidth(), getHeight());
/*       */     }
/*       */     
/*  2484 */     paramRectangle.setBounds(getX(), getY(), getWidth(), getHeight());
/*  2485 */     return paramRectangle;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Dimension getSize(Dimension paramDimension) {
/*  2500 */     if (paramDimension == null) {
/*  2501 */       return new Dimension(getWidth(), getHeight());
/*       */     }
/*       */     
/*  2504 */     paramDimension.setSize(getWidth(), getHeight());
/*  2505 */     return paramDimension;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Point getLocation(Point paramPoint) {
/*  2521 */     if (paramPoint == null) {
/*  2522 */       return new Point(getX(), getY());
/*       */     }
/*       */     
/*  2525 */     paramPoint.setLocation(getX(), getY());
/*  2526 */     return paramPoint;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isOpaque() {
/*  2548 */     if (getPeer() == null) {
/*  2549 */       return false;
/*       */     }
/*       */     
/*  2552 */     return !isLightweight();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isLightweight() {
/*  2574 */     return getPeer() instanceof java.awt.peer.LightweightPeer;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setPreferredSize(Dimension paramDimension) {
/*       */     Object object;
/*  2594 */     if (this.prefSizeSet) {
/*  2595 */       object = this.prefSize;
/*       */     } else {
/*       */       
/*  2598 */       object = null;
/*       */     } 
/*  2600 */     this.prefSize = paramDimension;
/*  2601 */     this.prefSizeSet = (paramDimension != null);
/*  2602 */     firePropertyChange("preferredSize", object, paramDimension);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isPreferredSizeSet() {
/*  2615 */     return this.prefSizeSet;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Dimension getPreferredSize() {
/*  2626 */     return preferredSize();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public Dimension preferredSize() {
/*  2639 */     Dimension dimension = this.prefSize;
/*  2640 */     if (dimension == null || (!isPreferredSizeSet() && !isValid())) {
/*  2641 */       synchronized (getTreeLock()) {
/*  2642 */         this
/*       */           
/*  2644 */           .prefSize = (this.peer != null) ? this.peer.getPreferredSize() : getMinimumSize();
/*  2645 */         dimension = this.prefSize;
/*       */       } 
/*       */     }
/*  2648 */     return new Dimension(dimension);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMinimumSize(Dimension paramDimension) {
/*       */     Object object;
/*  2667 */     if (this.minSizeSet) {
/*  2668 */       object = this.minSize;
/*       */     } else {
/*       */       
/*  2671 */       object = null;
/*       */     } 
/*  2673 */     this.minSize = paramDimension;
/*  2674 */     this.minSizeSet = (paramDimension != null);
/*  2675 */     firePropertyChange("minimumSize", object, paramDimension);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isMinimumSizeSet() {
/*  2687 */     return this.minSizeSet;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Dimension getMinimumSize() {
/*  2697 */     return minimumSize();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public Dimension minimumSize() {
/*  2709 */     Dimension dimension = this.minSize;
/*  2710 */     if (dimension == null || (!isMinimumSizeSet() && !isValid())) {
/*  2711 */       synchronized (getTreeLock()) {
/*  2712 */         this
/*       */           
/*  2714 */           .minSize = (this.peer != null) ? this.peer.getMinimumSize() : size();
/*  2715 */         dimension = this.minSize;
/*       */       } 
/*       */     }
/*  2718 */     return new Dimension(dimension);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setMaximumSize(Dimension paramDimension) {
/*       */     Object object;
/*  2738 */     if (this.maxSizeSet) {
/*  2739 */       object = this.maxSize;
/*       */     } else {
/*       */       
/*  2742 */       object = null;
/*       */     } 
/*  2744 */     this.maxSize = paramDimension;
/*  2745 */     this.maxSizeSet = (paramDimension != null);
/*  2746 */     firePropertyChange("maximumSize", object, paramDimension);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isMaximumSizeSet() {
/*  2758 */     return this.maxSizeSet;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Dimension getMaximumSize() {
/*  2769 */     if (isMaximumSizeSet()) {
/*  2770 */       return new Dimension(this.maxSize);
/*       */     }
/*  2772 */     return new Dimension(32767, 32767);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public float getAlignmentX() {
/*  2783 */     return 0.5F;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public float getAlignmentY() {
/*  2794 */     return 0.5F;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int getBaseline(int paramInt1, int paramInt2) {
/*  2822 */     if (paramInt1 < 0 || paramInt2 < 0) {
/*  2823 */       throw new IllegalArgumentException("Width and height must be >= 0");
/*       */     }
/*       */     
/*  2826 */     return -1;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public BaselineResizeBehavior getBaselineResizeBehavior() {
/*  2851 */     return BaselineResizeBehavior.OTHER;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void doLayout() {
/*  2862 */     layout();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void layout() {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void validate() {
/*  2886 */     synchronized (getTreeLock()) {
/*  2887 */       ComponentPeer componentPeer = this.peer;
/*  2888 */       boolean bool = isValid();
/*  2889 */       if (!bool && componentPeer != null) {
/*  2890 */         Font font1 = getFont();
/*  2891 */         Font font2 = this.peerFont;
/*  2892 */         if (font1 != font2 && (font2 == null || 
/*  2893 */           !font2.equals(font1))) {
/*  2894 */           componentPeer.setFont(font1);
/*  2895 */           this.peerFont = font1;
/*       */         } 
/*  2897 */         componentPeer.layout();
/*       */       } 
/*  2899 */       this.valid = true;
/*  2900 */       if (!bool) {
/*  2901 */         mixOnValidating();
/*       */       }
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void invalidate() {
/*  2929 */     synchronized (getTreeLock()) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  2934 */       this.valid = false;
/*  2935 */       if (!isPreferredSizeSet()) {
/*  2936 */         this.prefSize = null;
/*       */       }
/*  2938 */       if (!isMinimumSizeSet()) {
/*  2939 */         this.minSize = null;
/*       */       }
/*  2941 */       if (!isMaximumSizeSet()) {
/*  2942 */         this.maxSize = null;
/*       */       }
/*  2944 */       invalidateParent();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void invalidateParent() {
/*  2954 */     if (this.parent != null) {
/*  2955 */       this.parent.invalidateIfValid();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   final void invalidateIfValid() {
/*  2962 */     if (isValid()) {
/*  2963 */       invalidate();
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void revalidate() {
/*  2984 */     revalidateSynchronously();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final void revalidateSynchronously() {
/*  2991 */     synchronized (getTreeLock()) {
/*  2992 */       invalidate();
/*       */       
/*  2994 */       Container container = getContainer();
/*  2995 */       if (container == null) {
/*       */         
/*  2997 */         validate();
/*       */       } else {
/*  2999 */         while (!container.isValidateRoot() && 
/*  3000 */           container.getContainer() != null)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  3006 */           container = container.getContainer();
/*       */         }
/*       */         
/*  3009 */         container.validate();
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Graphics getGraphics() {
/*  3024 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*       */ 
/*       */ 
/*       */       
/*  3028 */       if (this.parent == null) return null; 
/*  3029 */       Graphics graphics = this.parent.getGraphics();
/*  3030 */       if (graphics == null) return null; 
/*  3031 */       if (graphics instanceof ConstrainableGraphics) {
/*  3032 */         ((ConstrainableGraphics)graphics).constrain(this.x, this.y, this.width, this.height);
/*       */       } else {
/*  3034 */         graphics.translate(this.x, this.y);
/*  3035 */         graphics.setClip(0, 0, this.width, this.height);
/*       */       } 
/*  3037 */       graphics.setFont(getFont());
/*  3038 */       return graphics;
/*       */     } 
/*  3040 */     ComponentPeer componentPeer = this.peer;
/*  3041 */     return (componentPeer != null) ? componentPeer.getGraphics() : null;
/*       */   }
/*       */ 
/*       */   
/*       */   final Graphics getGraphics_NoClientCode() {
/*  3046 */     ComponentPeer componentPeer = this.peer;
/*  3047 */     if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*       */ 
/*       */ 
/*       */       
/*  3051 */       Container container = this.parent;
/*  3052 */       if (container == null) return null; 
/*  3053 */       Graphics graphics = container.getGraphics_NoClientCode();
/*  3054 */       if (graphics == null) return null; 
/*  3055 */       if (graphics instanceof ConstrainableGraphics) {
/*  3056 */         ((ConstrainableGraphics)graphics).constrain(this.x, this.y, this.width, this.height);
/*       */       } else {
/*  3058 */         graphics.translate(this.x, this.y);
/*  3059 */         graphics.setClip(0, 0, this.width, this.height);
/*       */       } 
/*  3061 */       graphics.setFont(getFont_NoClientCode());
/*  3062 */       return graphics;
/*       */     } 
/*  3064 */     return (componentPeer != null) ? componentPeer.getGraphics() : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public FontMetrics getFontMetrics(Font paramFont) {
/*  3090 */     FontManager fontManager = FontManagerFactory.getInstance();
/*  3091 */     if (fontManager instanceof SunFontManager && ((SunFontManager)fontManager)
/*  3092 */       .usePlatformFontMetrics())
/*       */     {
/*  3094 */       if (this.peer != null && !(this.peer instanceof java.awt.peer.LightweightPeer))
/*       */       {
/*  3096 */         return this.peer.getFontMetrics(paramFont);
/*       */       }
/*       */     }
/*  3099 */     return FontDesignMetrics.getMetrics(paramFont);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setCursor(Cursor paramCursor) {
/*  3128 */     this.cursor = paramCursor;
/*  3129 */     updateCursorImmediately();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final void updateCursorImmediately() {
/*  3137 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  3138 */       Container container = getNativeContainer();
/*       */       
/*  3140 */       if (container == null)
/*       */         return; 
/*  3142 */       ComponentPeer componentPeer = container.getPeer();
/*       */       
/*  3144 */       if (componentPeer != null) {
/*  3145 */         componentPeer.updateCursorImmediately();
/*       */       }
/*  3147 */     } else if (this.peer != null) {
/*  3148 */       this.peer.updateCursorImmediately();
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Cursor getCursor() {
/*  3161 */     return getCursor_NoClientCode();
/*       */   }
/*       */   
/*       */   final Cursor getCursor_NoClientCode() {
/*  3165 */     Cursor cursor = this.cursor;
/*  3166 */     if (cursor != null) {
/*  3167 */       return cursor;
/*       */     }
/*  3169 */     Container container = this.parent;
/*  3170 */     if (container != null) {
/*  3171 */       return container.getCursor_NoClientCode();
/*       */     }
/*  3173 */     return Cursor.getPredefinedCursor(0);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean isCursorSet() {
/*  3187 */     return (this.cursor != null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void paint(Graphics paramGraphics) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void update(Graphics paramGraphics) {
/*  3251 */     paint(paramGraphics);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void paintAll(Graphics paramGraphics) {
/*  3267 */     if (isShowing()) {
/*  3268 */       GraphicsCallback.PeerPaintCallback.getInstance()
/*  3269 */         .runOneComponent(this, new Rectangle(0, 0, this.width, this.height), paramGraphics, paramGraphics
/*  3270 */           .getClip(), 3);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void lightweightPaint(Graphics paramGraphics) {
/*  3283 */     paint(paramGraphics);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void paintHeavyweightComponents(Graphics paramGraphics) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void repaint() {
/*  3311 */     repaint(0L, 0, 0, this.width, this.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void repaint(long paramLong) {
/*  3330 */     repaint(paramLong, 0, 0, this.width, this.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void repaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  3354 */     repaint(0L, paramInt1, paramInt2, paramInt3, paramInt4);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  3380 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  3385 */       if (this.parent != null) {
/*  3386 */         if (paramInt1 < 0) {
/*  3387 */           paramInt3 += paramInt1;
/*  3388 */           paramInt1 = 0;
/*       */         } 
/*  3390 */         if (paramInt2 < 0) {
/*  3391 */           paramInt4 += paramInt2;
/*  3392 */           paramInt2 = 0;
/*       */         } 
/*       */         
/*  3395 */         int i = (paramInt3 > this.width) ? this.width : paramInt3;
/*  3396 */         int j = (paramInt4 > this.height) ? this.height : paramInt4;
/*       */         
/*  3398 */         if (i <= 0 || j <= 0) {
/*       */           return;
/*       */         }
/*       */         
/*  3402 */         int k = this.x + paramInt1;
/*  3403 */         int m = this.y + paramInt2;
/*  3404 */         this.parent.repaint(paramLong, k, m, i, j);
/*       */       }
/*       */     
/*  3407 */     } else if (isVisible() && this.peer != null && paramInt3 > 0 && paramInt4 > 0) {
/*       */       
/*  3409 */       PaintEvent paintEvent = new PaintEvent(this, 801, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*       */       
/*  3411 */       SunToolkit.postEvent(SunToolkit.targetToAppContext(this), paintEvent);
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void print(Graphics paramGraphics) {
/*  3433 */     paint(paramGraphics);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void printAll(Graphics paramGraphics) {
/*  3448 */     if (isShowing()) {
/*  3449 */       GraphicsCallback.PeerPrintCallback.getInstance()
/*  3450 */         .runOneComponent(this, new Rectangle(0, 0, this.width, this.height), paramGraphics, paramGraphics
/*  3451 */           .getClip(), 3);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void lightweightPrint(Graphics paramGraphics) {
/*  3464 */     print(paramGraphics);
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   void printHeavyweightComponents(Graphics paramGraphics) {}
/*       */ 
/*       */ 
/*       */   
/*       */   private Insets getInsets_NoClientCode() {
/*  3474 */     ComponentPeer componentPeer = this.peer;
/*  3475 */     if (componentPeer instanceof ContainerPeer) {
/*  3476 */       return (Insets)((ContainerPeer)componentPeer).getInsets().clone();
/*       */     }
/*  3478 */     return new Insets(0, 0, 0, 0);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  3529 */     int i = -1;
/*  3530 */     if ((paramInt1 & 0x30) != 0) {
/*  3531 */       i = 0;
/*  3532 */     } else if ((paramInt1 & 0x8) != 0 && 
/*  3533 */       isInc) {
/*  3534 */       i = incRate;
/*  3535 */       if (i < 0) {
/*  3536 */         i = 0;
/*       */       }
/*       */     } 
/*       */     
/*  3540 */     if (i >= 0) {
/*  3541 */       repaint(i, 0, 0, this.width, this.height);
/*       */     }
/*  3543 */     return ((paramInt1 & 0xA0) == 0);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Image createImage(ImageProducer paramImageProducer) {
/*  3553 */     ComponentPeer componentPeer = this.peer;
/*  3554 */     if (componentPeer != null && !(componentPeer instanceof java.awt.peer.LightweightPeer)) {
/*  3555 */       return componentPeer.createImage(paramImageProducer);
/*       */     }
/*  3557 */     return getToolkit().createImage(paramImageProducer);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Image createImage(int paramInt1, int paramInt2) {
/*  3575 */     ComponentPeer componentPeer = this.peer;
/*  3576 */     if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*  3577 */       if (this.parent != null) return this.parent.createImage(paramInt1, paramInt2); 
/*  3578 */       return null;
/*       */     } 
/*  3580 */     return (componentPeer != null) ? componentPeer.createImage(paramInt1, paramInt2) : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2) {
/*  3600 */     ComponentPeer componentPeer = this.peer;
/*  3601 */     if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*  3602 */       if (this.parent != null) {
/*  3603 */         return this.parent.createVolatileImage(paramInt1, paramInt2);
/*       */       }
/*  3605 */       return null;
/*       */     } 
/*  3607 */     return (componentPeer != null) ? componentPeer
/*  3608 */       .createVolatileImage(paramInt1, paramInt2) : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public VolatileImage createVolatileImage(int paramInt1, int paramInt2, ImageCapabilities paramImageCapabilities) throws AWTException {
/*  3630 */     return createVolatileImage(paramInt1, paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean prepareImage(Image paramImage, ImageObserver paramImageObserver) {
/*  3646 */     return prepareImage(paramImage, -1, -1, paramImageObserver);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  3669 */     ComponentPeer componentPeer = this.peer;
/*  3670 */     if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*  3671 */       return (this.parent != null) ? this.parent
/*  3672 */         .prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver) : 
/*  3673 */         getToolkit().prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */     }
/*  3675 */     return (componentPeer != null) ? componentPeer
/*  3676 */       .prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver) : 
/*  3677 */       getToolkit().prepareImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int checkImage(Image paramImage, ImageObserver paramImageObserver) {
/*  3704 */     return checkImage(paramImage, -1, -1, paramImageObserver);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  3741 */     ComponentPeer componentPeer = this.peer;
/*  3742 */     if (componentPeer instanceof java.awt.peer.LightweightPeer) {
/*  3743 */       return (this.parent != null) ? this.parent
/*  3744 */         .checkImage(paramImage, paramInt1, paramInt2, paramImageObserver) : 
/*  3745 */         getToolkit().checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */     }
/*  3747 */     return (componentPeer != null) ? componentPeer
/*  3748 */       .checkImage(paramImage, paramInt1, paramInt2, paramImageObserver) : 
/*  3749 */       getToolkit().checkImage(paramImage, paramInt1, paramInt2, paramImageObserver);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void createBufferStrategy(int paramInt) {
/*  3775 */     if (paramInt > 1) {
/*       */       
/*  3777 */       BufferCapabilities bufferCapabilities1 = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), BufferCapabilities.FlipContents.UNDEFINED);
/*       */ 
/*       */       
/*       */       try {
/*  3781 */         createBufferStrategy(paramInt, bufferCapabilities1);
/*       */         return;
/*  3783 */       } catch (AWTException aWTException) {}
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  3788 */     BufferCapabilities bufferCapabilities = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), null);
/*       */ 
/*       */     
/*       */     try {
/*  3792 */       createBufferStrategy(paramInt, bufferCapabilities);
/*       */       return;
/*  3794 */     } catch (AWTException aWTException) {
/*       */ 
/*       */ 
/*       */       
/*  3798 */       bufferCapabilities = new BufferCapabilities(new ImageCapabilities(false), new ImageCapabilities(false), null);
/*       */ 
/*       */       
/*       */       try {
/*  3802 */         createBufferStrategy(paramInt, bufferCapabilities);
/*       */         return;
/*  3804 */       } catch (AWTException aWTException1) {
/*       */ 
/*       */         
/*  3807 */         throw new InternalError("Could not create a buffer strategy", aWTException1);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void createBufferStrategy(int paramInt, BufferCapabilities paramBufferCapabilities) throws AWTException {
/*  3836 */     if (paramInt < 1) {
/*  3837 */       throw new IllegalArgumentException("Number of buffers must be at least 1");
/*       */     }
/*       */     
/*  3840 */     if (paramBufferCapabilities == null) {
/*  3841 */       throw new IllegalArgumentException("No capabilities specified");
/*       */     }
/*       */     
/*  3844 */     if (this.bufferStrategy != null) {
/*  3845 */       this.bufferStrategy.dispose();
/*       */     }
/*  3847 */     if (paramInt == 1) {
/*  3848 */       this.bufferStrategy = new SingleBufferStrategy(paramBufferCapabilities);
/*       */     } else {
/*       */       
/*  3851 */       SunGraphicsEnvironment sunGraphicsEnvironment = (SunGraphicsEnvironment)GraphicsEnvironment.getLocalGraphicsEnvironment();
/*  3852 */       if (!paramBufferCapabilities.isPageFlipping() && sunGraphicsEnvironment.isFlipStrategyPreferred(this.peer)) {
/*  3853 */         paramBufferCapabilities = new ProxyCapabilities(paramBufferCapabilities);
/*       */       }
/*       */       
/*  3856 */       if (paramBufferCapabilities.isPageFlipping()) {
/*  3857 */         this.bufferStrategy = new FlipSubRegionBufferStrategy(paramInt, paramBufferCapabilities);
/*       */       } else {
/*  3859 */         this.bufferStrategy = new BltSubRegionBufferStrategy(paramInt, paramBufferCapabilities);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   private class ProxyCapabilities
/*       */     extends ExtendedBufferCapabilities
/*       */   {
/*       */     private BufferCapabilities orig;
/*       */ 
/*       */     
/*       */     private ProxyCapabilities(BufferCapabilities param1BufferCapabilities) {
/*  3873 */       super(param1BufferCapabilities.getFrontBufferCapabilities(), param1BufferCapabilities
/*  3874 */           .getBackBufferCapabilities(), 
/*  3875 */           (param1BufferCapabilities.getFlipContents() == BufferCapabilities.FlipContents.BACKGROUND) ? BufferCapabilities.FlipContents.BACKGROUND : BufferCapabilities.FlipContents.COPIED);
/*       */ 
/*       */ 
/*       */       
/*  3879 */       this.orig = param1BufferCapabilities;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   BufferStrategy getBufferStrategy() {
/*  3890 */     return this.bufferStrategy;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Image getBackBuffer() {
/*  3899 */     if (this.bufferStrategy != null) {
/*  3900 */       if (this.bufferStrategy instanceof BltBufferStrategy) {
/*  3901 */         BltBufferStrategy bltBufferStrategy = (BltBufferStrategy)this.bufferStrategy;
/*  3902 */         return bltBufferStrategy.getBackBuffer();
/*  3903 */       }  if (this.bufferStrategy instanceof FlipBufferStrategy) {
/*  3904 */         FlipBufferStrategy flipBufferStrategy = (FlipBufferStrategy)this.bufferStrategy;
/*  3905 */         return flipBufferStrategy.getBackBuffer();
/*       */       } 
/*       */     } 
/*  3908 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected class FlipBufferStrategy
/*       */     extends BufferStrategy
/*       */   {
/*       */     protected int numBuffers;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected BufferCapabilities caps;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected Image drawBuffer;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected VolatileImage drawVBuffer;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected boolean validatedContents;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     int width;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     int height;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected FlipBufferStrategy(int param1Int, BufferCapabilities param1BufferCapabilities) throws AWTException {
/*  3972 */       if (!(Component.this instanceof Window) && !(Component.this instanceof Canvas))
/*       */       {
/*       */         
/*  3975 */         throw new ClassCastException("Component must be a Canvas or Window");
/*       */       }
/*       */       
/*  3978 */       this.numBuffers = param1Int;
/*  3979 */       this.caps = param1BufferCapabilities;
/*  3980 */       createBuffers(param1Int, param1BufferCapabilities);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected void createBuffers(int param1Int, BufferCapabilities param1BufferCapabilities) throws AWTException {
/*  4002 */       if (param1Int < 2) {
/*  4003 */         throw new IllegalArgumentException("Number of buffers cannot be less than two");
/*       */       }
/*  4005 */       if (Component.this.peer == null) {
/*  4006 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       }
/*  4008 */       if (param1BufferCapabilities == null || !param1BufferCapabilities.isPageFlipping()) {
/*  4009 */         throw new IllegalArgumentException("Page flipping capabilities must be specified");
/*       */       }
/*       */ 
/*       */ 
/*       */       
/*  4014 */       this.width = Component.this.getWidth();
/*  4015 */       this.height = Component.this.getHeight();
/*       */       
/*  4017 */       if (this.drawBuffer != null) {
/*       */         
/*  4019 */         this.drawBuffer = null;
/*  4020 */         this.drawVBuffer = null;
/*  4021 */         destroyBuffers();
/*       */       } 
/*       */ 
/*       */       
/*  4025 */       if (param1BufferCapabilities instanceof ExtendedBufferCapabilities) {
/*  4026 */         ExtendedBufferCapabilities extendedBufferCapabilities = (ExtendedBufferCapabilities)param1BufferCapabilities;
/*       */         
/*  4028 */         if (extendedBufferCapabilities.getVSync() == ExtendedBufferCapabilities.VSyncType.VSYNC_ON)
/*       */         {
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  4034 */           if (!VSyncedBSManager.vsyncAllowed(this)) {
/*  4035 */             param1BufferCapabilities = extendedBufferCapabilities.derive(ExtendedBufferCapabilities.VSyncType.VSYNC_DEFAULT);
/*       */           }
/*       */         }
/*       */       } 
/*       */       
/*  4040 */       Component.this.peer.createBuffers(param1Int, param1BufferCapabilities);
/*  4041 */       updateInternalBuffers();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     private void updateInternalBuffers() {
/*  4050 */       this.drawBuffer = getBackBuffer();
/*  4051 */       if (this.drawBuffer instanceof VolatileImage) {
/*  4052 */         this.drawVBuffer = (VolatileImage)this.drawBuffer;
/*       */       } else {
/*  4054 */         this.drawVBuffer = null;
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected Image getBackBuffer() {
/*  4064 */       if (Component.this.peer != null) {
/*  4065 */         return Component.this.peer.getBackBuffer();
/*       */       }
/*  4067 */       throw new IllegalStateException("Component must have a valid peer");
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected void flip(BufferCapabilities.FlipContents param1FlipContents) {
/*  4084 */       if (Component.this.peer != null) {
/*  4085 */         Image image = getBackBuffer();
/*  4086 */         if (image != null) {
/*  4087 */           Component.this.peer.flip(0, 0, image
/*  4088 */               .getWidth(null), image
/*  4089 */               .getHeight(null), param1FlipContents);
/*       */         }
/*       */       } else {
/*  4092 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     void flipSubRegion(int param1Int1, int param1Int2, int param1Int3, int param1Int4, BufferCapabilities.FlipContents param1FlipContents) {
/*  4100 */       if (Component.this.peer != null) {
/*  4101 */         Component.this.peer.flip(param1Int1, param1Int2, param1Int3, param1Int4, param1FlipContents);
/*       */       } else {
/*  4103 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected void destroyBuffers() {
/*  4112 */       VSyncedBSManager.releaseVsync(this);
/*  4113 */       if (Component.this.peer != null) {
/*  4114 */         Component.this.peer.destroyBuffers();
/*       */       } else {
/*  4116 */         throw new IllegalStateException("Component must have a valid peer");
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public BufferCapabilities getCapabilities() {
/*  4125 */       if (this.caps instanceof Component.ProxyCapabilities) {
/*  4126 */         return ((Component.ProxyCapabilities)this.caps).orig;
/*       */       }
/*  4128 */       return this.caps;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Graphics getDrawGraphics() {
/*  4139 */       revalidate();
/*  4140 */       return this.drawBuffer.getGraphics();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected void revalidate() {
/*  4147 */       revalidate(true);
/*       */     }
/*       */     
/*       */     void revalidate(boolean param1Boolean) {
/*  4151 */       this.validatedContents = false;
/*       */       
/*  4153 */       if (param1Boolean && (Component.this.getWidth() != this.width || Component.this.getHeight() != this.height)) {
/*       */         
/*       */         try {
/*  4156 */           createBuffers(this.numBuffers, this.caps);
/*  4157 */         } catch (AWTException aWTException) {}
/*       */ 
/*       */         
/*  4160 */         this.validatedContents = true;
/*       */       } 
/*       */ 
/*       */ 
/*       */       
/*  4165 */       updateInternalBuffers();
/*       */ 
/*       */       
/*  4168 */       if (this.drawVBuffer != null) {
/*       */         
/*  4170 */         GraphicsConfiguration graphicsConfiguration = Component.this.getGraphicsConfiguration_NoClientCode();
/*  4171 */         int i = this.drawVBuffer.validate(graphicsConfiguration);
/*  4172 */         if (i == 2) {
/*       */           try {
/*  4174 */             createBuffers(this.numBuffers, this.caps);
/*  4175 */           } catch (AWTException aWTException) {}
/*       */ 
/*       */           
/*  4178 */           if (this.drawVBuffer != null)
/*       */           {
/*  4180 */             this.drawVBuffer.validate(graphicsConfiguration);
/*       */           }
/*  4182 */           this.validatedContents = true;
/*  4183 */         } else if (i == 1) {
/*  4184 */           this.validatedContents = true;
/*       */         } 
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean contentsLost() {
/*  4194 */       if (this.drawVBuffer == null) {
/*  4195 */         return false;
/*       */       }
/*  4197 */       return this.drawVBuffer.contentsLost();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean contentsRestored() {
/*  4205 */       return this.validatedContents;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void show() {
/*  4213 */       flip(this.caps.getFlipContents());
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     void showSubRegion(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  4221 */       flipSubRegion(param1Int1, param1Int2, param1Int3, param1Int4, this.caps.getFlipContents());
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void dispose() {
/*  4229 */       if (Component.this.bufferStrategy == this) {
/*  4230 */         Component.this.bufferStrategy = null;
/*  4231 */         if (Component.this.peer != null) {
/*  4232 */           destroyBuffers();
/*       */         }
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected class BltBufferStrategy
/*       */     extends BufferStrategy
/*       */   {
/*       */     protected BufferCapabilities caps;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected VolatileImage[] backBuffers;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected boolean validatedContents;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected int width;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected int height;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     private Insets insets;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected BltBufferStrategy(int param1Int, BufferCapabilities param1BufferCapabilities) {
/*  4279 */       this.caps = param1BufferCapabilities;
/*  4280 */       createBackBuffers(param1Int - 1);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void dispose() {
/*  4288 */       if (this.backBuffers != null) {
/*  4289 */         for (int i = this.backBuffers.length - 1; i >= 0; 
/*  4290 */           i--) {
/*  4291 */           if (this.backBuffers[i] != null) {
/*  4292 */             this.backBuffers[i].flush();
/*  4293 */             this.backBuffers[i] = null;
/*       */           } 
/*       */         } 
/*       */       }
/*  4297 */       if (Component.this.bufferStrategy == this) {
/*  4298 */         Component.this.bufferStrategy = null;
/*       */       }
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected void createBackBuffers(int param1Int) {
/*  4306 */       if (param1Int == 0) {
/*  4307 */         this.backBuffers = null;
/*       */       } else {
/*       */         
/*  4310 */         this.width = Component.this.getWidth();
/*  4311 */         this.height = Component.this.getHeight();
/*  4312 */         this.insets = Component.this.getInsets_NoClientCode();
/*  4313 */         int i = this.width - this.insets.left - this.insets.right;
/*  4314 */         int j = this.height - this.insets.top - this.insets.bottom;
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  4319 */         i = Math.max(1, i);
/*  4320 */         j = Math.max(1, j);
/*  4321 */         if (this.backBuffers == null) {
/*  4322 */           this.backBuffers = new VolatileImage[param1Int];
/*       */         } else {
/*       */           
/*  4325 */           for (byte b1 = 0; b1 < param1Int; b1++) {
/*  4326 */             if (this.backBuffers[b1] != null) {
/*  4327 */               this.backBuffers[b1].flush();
/*  4328 */               this.backBuffers[b1] = null;
/*       */             } 
/*       */           } 
/*       */         } 
/*       */ 
/*       */         
/*  4334 */         for (byte b = 0; b < param1Int; b++) {
/*  4335 */           this.backBuffers[b] = Component.this.createVolatileImage(i, j);
/*       */         }
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public BufferCapabilities getCapabilities() {
/*  4344 */       return this.caps;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Graphics getDrawGraphics() {
/*  4351 */       revalidate();
/*  4352 */       Image image = getBackBuffer();
/*  4353 */       if (image == null) {
/*  4354 */         return Component.this.getGraphics();
/*       */       }
/*  4356 */       SunGraphics2D sunGraphics2D = (SunGraphics2D)image.getGraphics();
/*  4357 */       sunGraphics2D.constrain(-this.insets.left, -this.insets.top, image
/*  4358 */           .getWidth(null) + this.insets.left, image
/*  4359 */           .getHeight(null) + this.insets.top);
/*  4360 */       return sunGraphics2D;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     Image getBackBuffer() {
/*  4368 */       if (this.backBuffers != null) {
/*  4369 */         return this.backBuffers[this.backBuffers.length - 1];
/*       */       }
/*  4371 */       return null;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void show() {
/*  4379 */       showSubRegion(this.insets.left, this.insets.top, this.width - this.insets.right, this.height - this.insets.bottom);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     void showSubRegion(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  4394 */       if (this.backBuffers == null) {
/*       */         return;
/*       */       }
/*       */       
/*  4398 */       param1Int1 -= this.insets.left;
/*  4399 */       param1Int3 -= this.insets.left;
/*  4400 */       param1Int2 -= this.insets.top;
/*  4401 */       param1Int4 -= this.insets.top;
/*  4402 */       Graphics graphics = Component.this.getGraphics_NoClientCode();
/*  4403 */       if (graphics == null) {
/*       */         return;
/*       */       }
/*       */ 
/*       */ 
/*       */       
/*       */       try {
/*  4410 */         graphics.translate(this.insets.left, this.insets.top);
/*  4411 */         for (byte b = 0; b < this.backBuffers.length; b++) {
/*  4412 */           graphics.drawImage(this.backBuffers[b], param1Int1, param1Int2, param1Int3, param1Int4, param1Int1, param1Int2, param1Int3, param1Int4, null);
/*       */ 
/*       */ 
/*       */           
/*  4416 */           graphics.dispose();
/*  4417 */           graphics = null;
/*  4418 */           graphics = this.backBuffers[b].getGraphics();
/*       */         } 
/*       */       } finally {
/*  4421 */         if (graphics != null) {
/*  4422 */           graphics.dispose();
/*       */         }
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected void revalidate() {
/*  4431 */       revalidate(true);
/*       */     }
/*       */     
/*       */     void revalidate(boolean param1Boolean) {
/*  4435 */       this.validatedContents = false;
/*       */       
/*  4437 */       if (this.backBuffers == null) {
/*       */         return;
/*       */       }
/*       */       
/*  4441 */       if (param1Boolean) {
/*  4442 */         Insets insets = Component.this.getInsets_NoClientCode();
/*  4443 */         if (Component.this.getWidth() != this.width || Component.this.getHeight() != this.height || 
/*  4444 */           !insets.equals(this.insets)) {
/*       */           
/*  4446 */           createBackBuffers(this.backBuffers.length);
/*  4447 */           this.validatedContents = true;
/*       */         } 
/*       */       } 
/*       */ 
/*       */       
/*  4452 */       GraphicsConfiguration graphicsConfiguration = Component.this.getGraphicsConfiguration_NoClientCode();
/*       */       
/*  4454 */       int i = this.backBuffers[this.backBuffers.length - 1].validate(graphicsConfiguration);
/*  4455 */       if (i == 2) {
/*  4456 */         if (param1Boolean) {
/*  4457 */           createBackBuffers(this.backBuffers.length);
/*       */           
/*  4459 */           this.backBuffers[this.backBuffers.length - 1].validate(graphicsConfiguration);
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  4465 */         this.validatedContents = true;
/*  4466 */       } else if (i == 1) {
/*  4467 */         this.validatedContents = true;
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean contentsLost() {
/*  4476 */       if (this.backBuffers == null) {
/*  4477 */         return false;
/*       */       }
/*  4479 */       return this.backBuffers[this.backBuffers.length - 1].contentsLost();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean contentsRestored() {
/*  4488 */       return this.validatedContents;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private class FlipSubRegionBufferStrategy
/*       */     extends FlipBufferStrategy
/*       */     implements SubRegionShowable
/*       */   {
/*       */     protected FlipSubRegionBufferStrategy(int param1Int, BufferCapabilities param1BufferCapabilities) throws AWTException {
/*  4503 */       super(param1Int, param1BufferCapabilities);
/*       */     }
/*       */     
/*       */     public void show(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  4507 */       showSubRegion(param1Int1, param1Int2, param1Int3, param1Int4);
/*       */     }
/*       */ 
/*       */     
/*       */     public boolean showIfNotLost(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  4512 */       if (!contentsLost()) {
/*  4513 */         showSubRegion(param1Int1, param1Int2, param1Int3, param1Int4);
/*  4514 */         return !contentsLost();
/*       */       } 
/*  4516 */       return false;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private class BltSubRegionBufferStrategy
/*       */     extends BltBufferStrategy
/*       */     implements SubRegionShowable
/*       */   {
/*       */     protected BltSubRegionBufferStrategy(int param1Int, BufferCapabilities param1BufferCapabilities) {
/*  4533 */       super(param1Int, param1BufferCapabilities);
/*       */     }
/*       */     
/*       */     public void show(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  4537 */       showSubRegion(param1Int1, param1Int2, param1Int3, param1Int4);
/*       */     }
/*       */ 
/*       */     
/*       */     public boolean showIfNotLost(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  4542 */       if (!contentsLost()) {
/*  4543 */         showSubRegion(param1Int1, param1Int2, param1Int3, param1Int4);
/*  4544 */         return !contentsLost();
/*       */       } 
/*  4546 */       return false;
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private class SingleBufferStrategy
/*       */     extends BufferStrategy
/*       */   {
/*       */     private BufferCapabilities caps;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public SingleBufferStrategy(BufferCapabilities param1BufferCapabilities) {
/*  4564 */       this.caps = param1BufferCapabilities;
/*       */     }
/*       */     public BufferCapabilities getCapabilities() {
/*  4567 */       return this.caps;
/*       */     }
/*       */     public Graphics getDrawGraphics() {
/*  4570 */       return Component.this.getGraphics();
/*       */     }
/*       */     public boolean contentsLost() {
/*  4573 */       return false;
/*       */     }
/*       */     public boolean contentsRestored() {
/*  4576 */       return false;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void show() {}
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void setIgnoreRepaint(boolean paramBoolean) {
/*  4601 */     this.ignoreRepaint = paramBoolean;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean getIgnoreRepaint() {
/*  4612 */     return this.ignoreRepaint;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean contains(int paramInt1, int paramInt2) {
/*  4625 */     return inside(paramInt1, paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public boolean inside(int paramInt1, int paramInt2) {
/*  4634 */     return (paramInt1 >= 0 && paramInt1 < this.width && paramInt2 >= 0 && paramInt2 < this.height);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public boolean contains(Point paramPoint) {
/*  4647 */     return contains(paramPoint.x, paramPoint.y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Component getComponentAt(int paramInt1, int paramInt2) {
/*  4672 */     return locate(paramInt1, paramInt2);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public Component locate(int paramInt1, int paramInt2) {
/*  4681 */     return contains(paramInt1, paramInt2) ? this : null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public Component getComponentAt(Point paramPoint) {
/*  4692 */     return getComponentAt(paramPoint.x, paramPoint.y);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public void deliverEvent(Event paramEvent) {
/*  4701 */     postEvent(paramEvent);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public final void dispatchEvent(AWTEvent paramAWTEvent) {
/*  4711 */     dispatchEventImpl(paramAWTEvent);
/*       */   }
/*       */   
/*       */   void dispatchEventImpl(AWTEvent paramAWTEvent) {
/*       */     Container container;
/*  4716 */     int i = paramAWTEvent.getID();
/*       */ 
/*       */     
/*  4719 */     AppContext appContext = this.appContext;
/*  4720 */     if (appContext != null && !appContext.equals(AppContext.getAppContext()) && 
/*  4721 */       eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  4722 */       eventLog.fine("Event " + paramAWTEvent + " is being dispatched on the wrong AppContext");
/*       */     }
/*       */ 
/*       */     
/*  4726 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4727 */       eventLog.finest("{0}", new Object[] { paramAWTEvent });
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4733 */     if (!(paramAWTEvent instanceof KeyEvent))
/*       */     {
/*  4735 */       EventQueue.setCurrentEventAndMostRecentTime(paramAWTEvent);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4743 */     if (paramAWTEvent instanceof SunDropTargetEvent) {
/*  4744 */       ((SunDropTargetEvent)paramAWTEvent).dispatch();
/*       */       
/*       */       return;
/*       */     } 
/*  4748 */     if (!paramAWTEvent.focusManagerIsDispatching) {
/*       */ 
/*       */       
/*  4751 */       if (paramAWTEvent.isPosted) {
/*  4752 */         paramAWTEvent = KeyboardFocusManager.retargetFocusEvent(paramAWTEvent);
/*  4753 */         paramAWTEvent.isPosted = true;
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4759 */       if (KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  4760 */         .dispatchEvent(paramAWTEvent)) {
/*       */         return;
/*       */       }
/*       */     } 
/*       */     
/*  4765 */     if (paramAWTEvent instanceof FocusEvent && focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4766 */       focusLog.finest("" + paramAWTEvent);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4773 */     if (i == 507 && 
/*  4774 */       !eventTypeEnabled(i) && this.peer != null && 
/*  4775 */       !this.peer.handlesWheelScrolling() && 
/*  4776 */       dispatchMouseWheelToAncestor((MouseWheelEvent)paramAWTEvent)) {
/*       */       return;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4784 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/*  4785 */     toolkit.notifyAWTEventListeners(paramAWTEvent);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4792 */     if (!paramAWTEvent.isConsumed() && 
/*  4793 */       paramAWTEvent instanceof KeyEvent) {
/*  4794 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/*  4795 */         .processKeyEvent(this, (KeyEvent)paramAWTEvent);
/*  4796 */       if (paramAWTEvent.isConsumed()) {
/*       */         return;
/*       */       }
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4805 */     if (areInputMethodsEnabled()) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4810 */       if ((paramAWTEvent instanceof InputMethodEvent && !(this instanceof sun.awt.im.CompositionArea)) || paramAWTEvent instanceof java.awt.event.InputEvent || paramAWTEvent instanceof FocusEvent) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  4817 */         InputContext inputContext = getInputContext();
/*       */ 
/*       */         
/*  4820 */         if (inputContext != null) {
/*  4821 */           inputContext.dispatchEvent(paramAWTEvent);
/*  4822 */           if (paramAWTEvent.isConsumed()) {
/*  4823 */             if (paramAWTEvent instanceof FocusEvent && focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4824 */               focusLog.finest("3579: Skipping " + paramAWTEvent);
/*       */             }
/*       */ 
/*       */ 
/*       */ 
/*       */             
/*       */             return;
/*       */           } 
/*       */         } 
/*       */       } 
/*  4834 */     } else if (i == 1004) {
/*  4835 */       InputContext inputContext = getInputContext();
/*  4836 */       if (inputContext != null && inputContext instanceof InputContext) {
/*  4837 */         ((InputContext)inputContext).disableNativeIM();
/*       */       }
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4846 */     switch (i) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       case 401:
/*       */       case 402:
/*  4854 */         container = (this instanceof Container) ? (Container)this : this.parent;
/*  4855 */         if (container != null) {
/*  4856 */           container.preProcessKeyEvent((KeyEvent)paramAWTEvent);
/*  4857 */           if (paramAWTEvent.isConsumed()) {
/*  4858 */             if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4859 */               focusLog.finest("Pre-process consumed event");
/*       */             }
/*       */             return;
/*       */           } 
/*       */         } 
/*       */         break;
/*       */       
/*       */       case 201:
/*  4867 */         if (toolkit instanceof WindowClosingListener) {
/*  4868 */           this
/*  4869 */             .windowClosingException = ((WindowClosingListener)toolkit).windowClosingNotify((WindowEvent)paramAWTEvent);
/*  4870 */           if (checkWindowClosingException()) {
/*       */             return;
/*       */           }
/*       */         } 
/*       */         break;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4883 */     if (this.newEventsOnly) {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  4888 */       if (eventEnabled(paramAWTEvent)) {
/*  4889 */         processEvent(paramAWTEvent);
/*       */       }
/*  4891 */     } else if (i == 507) {
/*       */ 
/*       */ 
/*       */       
/*  4895 */       autoProcessMouseWheel((MouseWheelEvent)paramAWTEvent);
/*  4896 */     } else if (!(paramAWTEvent instanceof MouseEvent) || postsOldMouseEvents()) {
/*       */ 
/*       */ 
/*       */       
/*  4900 */       Event event = paramAWTEvent.convertToOld();
/*  4901 */       if (event != null) {
/*  4902 */         int j = event.key;
/*  4903 */         int k = event.modifiers;
/*       */         
/*  4905 */         postEvent(event);
/*  4906 */         if (event.isConsumed()) {
/*  4907 */           paramAWTEvent.consume();
/*       */         }
/*       */ 
/*       */ 
/*       */         
/*  4912 */         switch (event.id) {
/*       */           case 401:
/*       */           case 402:
/*       */           case 403:
/*       */           case 404:
/*  4917 */             if (event.key != j) {
/*  4918 */               ((KeyEvent)paramAWTEvent).setKeyChar(event.getKeyEventChar());
/*       */             }
/*  4920 */             if (event.modifiers != k) {
/*  4921 */               ((KeyEvent)paramAWTEvent).setModifiers(event.modifiers);
/*       */             }
/*       */             break;
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       } 
/*       */     } 
/*  4934 */     if (i == 201 && !paramAWTEvent.isConsumed() && 
/*  4935 */       toolkit instanceof WindowClosingListener) {
/*  4936 */       this
/*       */         
/*  4938 */         .windowClosingException = ((WindowClosingListener)toolkit).windowClosingDelivered((WindowEvent)paramAWTEvent);
/*  4939 */       if (checkWindowClosingException()) {
/*       */         return;
/*       */       }
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4951 */     if (!(paramAWTEvent instanceof KeyEvent)) {
/*  4952 */       ComponentPeer componentPeer = this.peer;
/*  4953 */       if (paramAWTEvent instanceof FocusEvent && (componentPeer == null || componentPeer instanceof java.awt.peer.LightweightPeer)) {
/*       */ 
/*       */         
/*  4956 */         Component component = (Component)paramAWTEvent.getSource();
/*  4957 */         if (component != null) {
/*  4958 */           Container container1 = component.getNativeContainer();
/*  4959 */           if (container1 != null) {
/*  4960 */             componentPeer = container1.getPeer();
/*       */           }
/*       */         } 
/*       */       } 
/*  4964 */       if (componentPeer != null) {
/*  4965 */         componentPeer.handleEvent(paramAWTEvent);
/*       */       }
/*       */     } 
/*       */     
/*  4969 */     if (SunToolkit.isTouchKeyboardAutoShowEnabled() && toolkit instanceof SunToolkit && (paramAWTEvent instanceof MouseEvent || paramAWTEvent instanceof FocusEvent))
/*       */     {
/*       */       
/*  4972 */       ((SunToolkit)toolkit).showOrHideTouchKeyboard(this, paramAWTEvent);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void autoProcessMouseWheel(MouseWheelEvent paramMouseWheelEvent) {}
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean dispatchMouseWheelToAncestor(MouseWheelEvent paramMouseWheelEvent) {
/*  4991 */     int i = paramMouseWheelEvent.getX() + getX();
/*  4992 */     int j = paramMouseWheelEvent.getY() + getY();
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  4997 */     if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  4998 */       eventLog.finest("dispatchMouseWheelToAncestor");
/*  4999 */       eventLog.finest("orig event src is of " + paramMouseWheelEvent.getSource().getClass());
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5005 */     synchronized (getTreeLock()) {
/*  5006 */       Container container = getParent();
/*  5007 */       while (container != null && !container.eventEnabled(paramMouseWheelEvent)) {
/*       */         
/*  5009 */         i += container.getX();
/*  5010 */         j += container.getY();
/*       */         
/*  5012 */         if (!(container instanceof Window)) {
/*  5013 */           container = container.getParent();
/*       */         }
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5020 */       if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
/*  5021 */         eventLog.finest("new event src is " + container.getClass());
/*       */       }
/*       */       
/*  5024 */       if (container != null && container.eventEnabled(paramMouseWheelEvent)) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5041 */         MouseWheelEvent mouseWheelEvent = new MouseWheelEvent(container, paramMouseWheelEvent.getID(), paramMouseWheelEvent.getWhen(), paramMouseWheelEvent.getModifiers(), i, j, paramMouseWheelEvent.getXOnScreen(), paramMouseWheelEvent.getYOnScreen(), paramMouseWheelEvent.getClickCount(), paramMouseWheelEvent.isPopupTrigger(), paramMouseWheelEvent.getScrollType(), paramMouseWheelEvent.getScrollAmount(), paramMouseWheelEvent.getWheelRotation(), paramMouseWheelEvent.getPreciseWheelRotation());
/*  5042 */         paramMouseWheelEvent.copyPrivateDataInto(mouseWheelEvent);
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5048 */         container.dispatchEventToSelf(mouseWheelEvent);
/*  5049 */         if (mouseWheelEvent.isConsumed()) {
/*  5050 */           paramMouseWheelEvent.consume();
/*       */         }
/*  5052 */         return true;
/*       */       } 
/*       */     } 
/*  5055 */     return false;
/*       */   }
/*       */   
/*       */   boolean checkWindowClosingException() {
/*  5059 */     if (this.windowClosingException != null) {
/*  5060 */       if (this instanceof Dialog) {
/*  5061 */         ((Dialog)this).interruptBlocking();
/*       */       } else {
/*  5063 */         this.windowClosingException.fillInStackTrace();
/*  5064 */         this.windowClosingException.printStackTrace();
/*  5065 */         this.windowClosingException = null;
/*       */       } 
/*  5067 */       return true;
/*       */     } 
/*  5069 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   boolean areInputMethodsEnabled() {
/*  5076 */     return ((this.eventMask & 0x1000L) != 0L && ((this.eventMask & 0x8L) != 0L || this.keyListener != null));
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   boolean eventEnabled(AWTEvent paramAWTEvent) {
/*  5082 */     return eventTypeEnabled(paramAWTEvent.id);
/*       */   }
/*       */   
/*       */   boolean eventTypeEnabled(int paramInt) {
/*  5086 */     switch (paramInt) {
/*       */       case 100:
/*       */       case 101:
/*       */       case 102:
/*       */       case 103:
/*  5091 */         if ((this.eventMask & 0x1L) != 0L || this.componentListener != null)
/*       */         {
/*  5093 */           return true;
/*       */         }
/*       */         break;
/*       */       case 1004:
/*       */       case 1005:
/*  5098 */         if ((this.eventMask & 0x4L) != 0L || this.focusListener != null)
/*       */         {
/*  5100 */           return true;
/*       */         }
/*       */         break;
/*       */       case 400:
/*       */       case 401:
/*       */       case 402:
/*  5106 */         if ((this.eventMask & 0x8L) != 0L || this.keyListener != null)
/*       */         {
/*  5108 */           return true;
/*       */         }
/*       */         break;
/*       */       case 500:
/*       */       case 501:
/*       */       case 502:
/*       */       case 504:
/*       */       case 505:
/*  5116 */         if ((this.eventMask & 0x10L) != 0L || this.mouseListener != null)
/*       */         {
/*  5118 */           return true;
/*       */         }
/*       */         break;
/*       */       case 503:
/*       */       case 506:
/*  5123 */         if ((this.eventMask & 0x20L) != 0L || this.mouseMotionListener != null)
/*       */         {
/*  5125 */           return true;
/*       */         }
/*       */         break;
/*       */       case 507:
/*  5129 */         if ((this.eventMask & 0x20000L) != 0L || this.mouseWheelListener != null)
/*       */         {
/*  5131 */           return true;
/*       */         }
/*       */         break;
/*       */       case 1100:
/*       */       case 1101:
/*  5136 */         if ((this.eventMask & 0x800L) != 0L || this.inputMethodListener != null)
/*       */         {
/*  5138 */           return true;
/*       */         }
/*       */         break;
/*       */       case 1400:
/*  5142 */         if ((this.eventMask & 0x8000L) != 0L || this.hierarchyListener != null)
/*       */         {
/*  5144 */           return true;
/*       */         }
/*       */         break;
/*       */       case 1401:
/*       */       case 1402:
/*  5149 */         if ((this.eventMask & 0x10000L) != 0L || this.hierarchyBoundsListener != null)
/*       */         {
/*  5151 */           return true;
/*       */         }
/*       */         break;
/*       */       case 1001:
/*  5155 */         if ((this.eventMask & 0x80L) != 0L) {
/*  5156 */           return true;
/*       */         }
/*       */         break;
/*       */       case 900:
/*  5160 */         if ((this.eventMask & 0x400L) != 0L) {
/*  5161 */           return true;
/*       */         }
/*       */         break;
/*       */       case 701:
/*  5165 */         if ((this.eventMask & 0x200L) != 0L) {
/*  5166 */           return true;
/*       */         }
/*       */         break;
/*       */       case 601:
/*  5170 */         if ((this.eventMask & 0x100L) != 0L) {
/*  5171 */           return true;
/*       */         }
/*       */         break;
/*       */     } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  5180 */     if (paramInt > 1999) {
/*  5181 */       return true;
/*       */     }
/*  5183 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   @Deprecated
/*       */   public boolean postEvent(Event paramEvent) {
/*  5192 */     ComponentPeer componentPeer = this.peer;
/*       */     
/*  5194 */     if (handleEvent(paramEvent)) {
/*  5195 */       paramEvent.consume();
/*  5196 */       return true;
/*       */     } 
/*       */     
/*  5199 */     Container container = this.parent;
/*  5200 */     int i = paramEvent.x;
/*  5201 */     int j = paramEvent.y;
/*  5202 */     if (container != null) {
/*  5203 */       paramEvent.translate(this.x, this.y);
/*  5204 */       if (container.postEvent(paramEvent)) {
/*  5205 */         paramEvent.consume();
/*  5206 */         return true;
/*       */       } 
/*       */       
/*  5209 */       paramEvent.x = i;
/*  5210 */       paramEvent.y = j;
/*       */     } 
/*  5212 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addComponentListener(ComponentListener paramComponentListener) {
/*  5233 */     if (paramComponentListener == null) {
/*       */       return;
/*       */     }
/*  5236 */     this.componentListener = AWTEventMulticaster.add(this.componentListener, paramComponentListener);
/*  5237 */     this.newEventsOnly = true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeComponentListener(ComponentListener paramComponentListener) {
/*  5257 */     if (paramComponentListener == null) {
/*       */       return;
/*       */     }
/*  5260 */     this.componentListener = AWTEventMulticaster.remove(this.componentListener, paramComponentListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized ComponentListener[] getComponentListeners() {
/*  5276 */     return getListeners(ComponentListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addFocusListener(FocusListener paramFocusListener) {
/*  5295 */     if (paramFocusListener == null) {
/*       */       return;
/*       */     }
/*  5298 */     this.focusListener = AWTEventMulticaster.add(this.focusListener, paramFocusListener);
/*  5299 */     this.newEventsOnly = true;
/*       */ 
/*       */ 
/*       */     
/*  5303 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  5304 */       this.parent.proxyEnableEvents(4L);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeFocusListener(FocusListener paramFocusListener) {
/*  5326 */     if (paramFocusListener == null) {
/*       */       return;
/*       */     }
/*  5329 */     this.focusListener = AWTEventMulticaster.remove(this.focusListener, paramFocusListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized FocusListener[] getFocusListeners() {
/*  5345 */     return getListeners(FocusListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void addHierarchyListener(HierarchyListener paramHierarchyListener) {
/*       */     boolean bool;
/*  5365 */     if (paramHierarchyListener == null) {
/*       */       return;
/*       */     }
/*       */     
/*  5369 */     synchronized (this) {
/*  5370 */       bool = (this.hierarchyListener == null && (this.eventMask & 0x8000L) == 0L) ? true : false;
/*       */ 
/*       */       
/*  5373 */       this.hierarchyListener = AWTEventMulticaster.add(this.hierarchyListener, paramHierarchyListener);
/*  5374 */       bool = (bool && this.hierarchyListener != null) ? true : false;
/*  5375 */       this.newEventsOnly = true;
/*       */     } 
/*  5377 */     if (bool) {
/*  5378 */       synchronized (getTreeLock()) {
/*  5379 */         adjustListeningChildrenOnParent(32768L, 1);
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void removeHierarchyListener(HierarchyListener paramHierarchyListener) {
/*       */     boolean bool;
/*  5403 */     if (paramHierarchyListener == null) {
/*       */       return;
/*       */     }
/*       */     
/*  5407 */     synchronized (this) {
/*  5408 */       bool = (this.hierarchyListener != null && (this.eventMask & 0x8000L) == 0L) ? true : false;
/*       */ 
/*       */       
/*  5411 */       this
/*  5412 */         .hierarchyListener = AWTEventMulticaster.remove(this.hierarchyListener, paramHierarchyListener);
/*  5413 */       bool = (bool && this.hierarchyListener == null) ? true : false;
/*       */     } 
/*  5415 */     if (bool) {
/*  5416 */       synchronized (getTreeLock()) {
/*  5417 */         adjustListeningChildrenOnParent(32768L, -1);
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized HierarchyListener[] getHierarchyListeners() {
/*  5436 */     return getListeners(HierarchyListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void addHierarchyBoundsListener(HierarchyBoundsListener paramHierarchyBoundsListener) {
/*       */     boolean bool;
/*  5456 */     if (paramHierarchyBoundsListener == null) {
/*       */       return;
/*       */     }
/*       */     
/*  5460 */     synchronized (this) {
/*  5461 */       bool = (this.hierarchyBoundsListener == null && (this.eventMask & 0x10000L) == 0L) ? true : false;
/*       */ 
/*       */       
/*  5464 */       this
/*  5465 */         .hierarchyBoundsListener = AWTEventMulticaster.add(this.hierarchyBoundsListener, paramHierarchyBoundsListener);
/*  5466 */       bool = (bool && this.hierarchyBoundsListener != null) ? true : false;
/*       */       
/*  5468 */       this.newEventsOnly = true;
/*       */     } 
/*  5470 */     if (bool) {
/*  5471 */       synchronized (getTreeLock()) {
/*  5472 */         adjustListeningChildrenOnParent(65536L, 1);
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public void removeHierarchyBoundsListener(HierarchyBoundsListener paramHierarchyBoundsListener) {
/*       */     boolean bool;
/*  5496 */     if (paramHierarchyBoundsListener == null) {
/*       */       return;
/*       */     }
/*       */     
/*  5500 */     synchronized (this) {
/*  5501 */       bool = (this.hierarchyBoundsListener != null && (this.eventMask & 0x10000L) == 0L) ? true : false;
/*       */ 
/*       */       
/*  5504 */       this
/*  5505 */         .hierarchyBoundsListener = AWTEventMulticaster.remove(this.hierarchyBoundsListener, paramHierarchyBoundsListener);
/*  5506 */       bool = (bool && this.hierarchyBoundsListener == null) ? true : false;
/*       */     } 
/*       */     
/*  5509 */     if (bool) {
/*  5510 */       synchronized (getTreeLock()) {
/*  5511 */         adjustListeningChildrenOnParent(65536L, -1);
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   int numListening(long paramLong) {
/*  5520 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE) && 
/*  5521 */       paramLong != 32768L && paramLong != 65536L)
/*       */     {
/*       */       
/*  5524 */       eventLog.fine("Assertion failed");
/*       */     }
/*       */     
/*  5527 */     if ((paramLong == 32768L && (this.hierarchyListener != null || (this.eventMask & 0x8000L) != 0L)) || (paramLong == 65536L && (this.hierarchyBoundsListener != null || (this.eventMask & 0x10000L) != 0L)))
/*       */     {
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  5533 */       return 1;
/*       */     }
/*  5535 */     return 0;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   int countHierarchyMembers() {
/*  5541 */     return 1;
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   int createHierarchyEvents(int paramInt, Component paramComponent, Container paramContainer, long paramLong, boolean paramBoolean) {
/*  5547 */     switch (paramInt)
/*       */     { case 1400:
/*  5549 */         if (this.hierarchyListener != null || (this.eventMask & 0x8000L) != 0L || paramBoolean) {
/*       */ 
/*       */           
/*  5552 */           HierarchyEvent hierarchyEvent = new HierarchyEvent(this, paramInt, paramComponent, paramContainer, paramLong);
/*       */ 
/*       */           
/*  5555 */           dispatchEvent(hierarchyEvent);
/*  5556 */           return 1;
/*       */         } 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  5582 */         return 0;case 1401: case 1402: if (eventLog.isLoggable(PlatformLogger.Level.FINE) && paramLong != 0L) eventLog.fine("Assertion (changeFlags == 0) failed");  if (this.hierarchyBoundsListener != null || (this.eventMask & 0x10000L) != 0L || paramBoolean) { HierarchyEvent hierarchyEvent = new HierarchyEvent(this, paramInt, paramComponent, paramContainer); dispatchEvent(hierarchyEvent); return 1; }  return 0; }  if (eventLog.isLoggable(PlatformLogger.Level.FINE)) eventLog.fine("This code must never be reached");  return 0;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized HierarchyBoundsListener[] getHierarchyBoundsListeners() {
/*  5598 */     return getListeners(HierarchyBoundsListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void adjustListeningChildrenOnParent(long paramLong, int paramInt) {
/*  5607 */     if (this.parent != null) {
/*  5608 */       this.parent.adjustListeningChildren(paramLong, paramInt);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addKeyListener(KeyListener paramKeyListener) {
/*  5627 */     if (paramKeyListener == null) {
/*       */       return;
/*       */     }
/*  5630 */     this.keyListener = AWTEventMulticaster.add(this.keyListener, paramKeyListener);
/*  5631 */     this.newEventsOnly = true;
/*       */ 
/*       */ 
/*       */     
/*  5635 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  5636 */       this.parent.proxyEnableEvents(8L);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeKeyListener(KeyListener paramKeyListener) {
/*  5658 */     if (paramKeyListener == null) {
/*       */       return;
/*       */     }
/*  5661 */     this.keyListener = AWTEventMulticaster.remove(this.keyListener, paramKeyListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized KeyListener[] getKeyListeners() {
/*  5677 */     return getListeners(KeyListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addMouseListener(MouseListener paramMouseListener) {
/*  5696 */     if (paramMouseListener == null) {
/*       */       return;
/*       */     }
/*  5699 */     this.mouseListener = AWTEventMulticaster.add(this.mouseListener, paramMouseListener);
/*  5700 */     this.newEventsOnly = true;
/*       */ 
/*       */ 
/*       */     
/*  5704 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  5705 */       this.parent.proxyEnableEvents(16L);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeMouseListener(MouseListener paramMouseListener) {
/*  5727 */     if (paramMouseListener == null) {
/*       */       return;
/*       */     }
/*  5730 */     this.mouseListener = AWTEventMulticaster.remove(this.mouseListener, paramMouseListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized MouseListener[] getMouseListeners() {
/*  5746 */     return getListeners(MouseListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addMouseMotionListener(MouseMotionListener paramMouseMotionListener) {
/*  5765 */     if (paramMouseMotionListener == null) {
/*       */       return;
/*       */     }
/*  5768 */     this.mouseMotionListener = AWTEventMulticaster.add(this.mouseMotionListener, paramMouseMotionListener);
/*  5769 */     this.newEventsOnly = true;
/*       */ 
/*       */ 
/*       */     
/*  5773 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  5774 */       this.parent.proxyEnableEvents(32L);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeMouseMotionListener(MouseMotionListener paramMouseMotionListener) {
/*  5796 */     if (paramMouseMotionListener == null) {
/*       */       return;
/*       */     }
/*  5799 */     this.mouseMotionListener = AWTEventMulticaster.remove(this.mouseMotionListener, paramMouseMotionListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized MouseMotionListener[] getMouseMotionListeners() {
/*  5815 */     return getListeners(MouseMotionListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addMouseWheelListener(MouseWheelListener paramMouseWheelListener) {
/*  5839 */     if (paramMouseWheelListener == null) {
/*       */       return;
/*       */     }
/*  5842 */     this.mouseWheelListener = AWTEventMulticaster.add(this.mouseWheelListener, paramMouseWheelListener);
/*  5843 */     this.newEventsOnly = true;
/*       */ 
/*       */ 
/*       */     
/*  5847 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  5848 */       this.parent.proxyEnableEvents(131072L);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeMouseWheelListener(MouseWheelListener paramMouseWheelListener) {
/*  5869 */     if (paramMouseWheelListener == null) {
/*       */       return;
/*       */     }
/*  5872 */     this.mouseWheelListener = AWTEventMulticaster.remove(this.mouseWheelListener, paramMouseWheelListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized MouseWheelListener[] getMouseWheelListeners() {
/*  5888 */     return getListeners(MouseWheelListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void addInputMethodListener(InputMethodListener paramInputMethodListener) {
/*  5911 */     if (paramInputMethodListener == null) {
/*       */       return;
/*       */     }
/*  5914 */     this.inputMethodListener = AWTEventMulticaster.add(this.inputMethodListener, paramInputMethodListener);
/*  5915 */     this.newEventsOnly = true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized void removeInputMethodListener(InputMethodListener paramInputMethodListener) {
/*  5936 */     if (paramInputMethodListener == null) {
/*       */       return;
/*       */     }
/*  5939 */     this.inputMethodListener = AWTEventMulticaster.remove(this.inputMethodListener, paramInputMethodListener);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public synchronized InputMethodListener[] getInputMethodListeners() {
/*  5955 */     return getListeners(InputMethodListener.class);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/*       */     InputMethodListener inputMethodListener;
/*  6002 */     ComponentListener componentListener = null;
/*  6003 */     if (paramClass == ComponentListener.class) {
/*  6004 */       componentListener = this.componentListener;
/*  6005 */     } else if (paramClass == FocusListener.class) {
/*  6006 */       FocusListener focusListener = this.focusListener;
/*  6007 */     } else if (paramClass == HierarchyListener.class) {
/*  6008 */       HierarchyListener hierarchyListener = this.hierarchyListener;
/*  6009 */     } else if (paramClass == HierarchyBoundsListener.class) {
/*  6010 */       HierarchyBoundsListener hierarchyBoundsListener = this.hierarchyBoundsListener;
/*  6011 */     } else if (paramClass == KeyListener.class) {
/*  6012 */       KeyListener keyListener = this.keyListener;
/*  6013 */     } else if (paramClass == MouseListener.class) {
/*  6014 */       MouseListener mouseListener = this.mouseListener;
/*  6015 */     } else if (paramClass == MouseMotionListener.class) {
/*  6016 */       MouseMotionListener mouseMotionListener = this.mouseMotionListener;
/*  6017 */     } else if (paramClass == MouseWheelListener.class) {
/*  6018 */       MouseWheelListener mouseWheelListener = this.mouseWheelListener;
/*  6019 */     } else if (paramClass == InputMethodListener.class) {
/*  6020 */       inputMethodListener = this.inputMethodListener;
/*  6021 */     } else if (paramClass == PropertyChangeListener.class) {
/*  6022 */       return (T[])getPropertyChangeListeners();
/*       */     } 
/*  6024 */     return AWTEventMulticaster.getListeners(inputMethodListener, paramClass);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public InputMethodRequests getInputMethodRequests() {
/*  6040 */     return null;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   public InputContext getInputContext() {
/*  6055 */     Container container = this.parent;
/*  6056 */     if (container == null) {
/*  6057 */       return null;
/*       */     }
/*  6059 */     return container.getInputContext();
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected final void enableEvents(long paramLong) {
/*  6081 */     long l = 0L;
/*  6082 */     synchronized (this) {
/*  6083 */       if ((paramLong & 0x8000L) != 0L && this.hierarchyListener == null && (this.eventMask & 0x8000L) == 0L)
/*       */       {
/*       */         
/*  6086 */         l |= 0x8000L;
/*       */       }
/*  6088 */       if ((paramLong & 0x10000L) != 0L && this.hierarchyBoundsListener == null && (this.eventMask & 0x10000L) == 0L)
/*       */       {
/*       */         
/*  6091 */         l |= 0x10000L;
/*       */       }
/*  6093 */       this.eventMask |= paramLong;
/*  6094 */       this.newEventsOnly = true;
/*       */     } 
/*       */ 
/*       */ 
/*       */     
/*  6099 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*  6100 */       this.parent.proxyEnableEvents(this.eventMask);
/*       */     }
/*  6102 */     if (l != 0L) {
/*  6103 */       synchronized (getTreeLock()) {
/*  6104 */         adjustListeningChildrenOnParent(l, 1);
/*       */       } 
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   protected final void disableEvents(long paramLong) {
/*  6117 */     long l = 0L;
/*  6118 */     synchronized (this) {
/*  6119 */       if ((paramLong & 0x8000L) != 0L && this.hierarchyListener == null && (this.eventMask & 0x8000L) != 0L)
/*       */       {
/*       */         
/*  6122 */         l |= 0x8000L;
/*       */       }
/*  6124 */       if ((paramLong & 0x10000L) != 0L && this.hierarchyBoundsListener == null && (this.eventMask & 0x10000L) != 0L)
/*       */       {
/*       */         
/*  6127 */         l |= 0x10000L;
/*       */       }
/*  6129 */       this.eventMask &= paramLong ^ 0xFFFFFFFFFFFFFFFFL;
/*       */     } 
/*  6131 */     if (l != 0L)
/*  6132 */       synchronized (getTreeLock())
/*  6133 */       { adjustListeningChildrenOnParent(l, -1); }  
/*       */   } private static final Map<Class<?>, Boolean> coalesceMap = new WeakHashMap<>(); private boolean checkCoalescing() { if (getClass().getClassLoader() == null) return false;  final Class<?> clazz = getClass(); synchronized (coalesceMap) { Boolean bool1 = coalesceMap.get(clazz); if (bool1 != null) return bool1.booleanValue();  Boolean bool2 = AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() { public Boolean run() { return Boolean.valueOf(Component.isCoalesceEventsOverriden(clazz)); } }
/*       */         ); coalesceMap.put(clazz, bool2); return bool2.booleanValue(); }  } private static final Class[] coalesceEventsParams = new Class[] { AWTEvent.class, AWTEvent.class }; private static boolean isCoalesceEventsOverriden(Class<?> paramClass) { assert Thread.holdsLock(coalesceMap); Class<?> clazz = paramClass.getSuperclass(); if (clazz == null) return false;  if (clazz.getClassLoader() != null) { Boolean bool = coalesceMap.get(clazz); if (bool == null) { if (isCoalesceEventsOverriden(clazz)) { coalesceMap.put(clazz, Boolean.valueOf(true)); return true; }  } else if (bool.booleanValue()) { return true; }  }  try { paramClass.getDeclaredMethod("coalesceEvents", coalesceEventsParams); return true; } catch (NoSuchMethodException noSuchMethodException) { return false; }  } final boolean isCoalescingEnabled() { return this.coalescingEnabled; } protected AWTEvent coalesceEvents(AWTEvent paramAWTEvent1, AWTEvent paramAWTEvent2) { return null; } protected void processEvent(AWTEvent paramAWTEvent) { if (paramAWTEvent instanceof FocusEvent) { processFocusEvent((FocusEvent)paramAWTEvent); } else if (paramAWTEvent instanceof MouseEvent) { switch (paramAWTEvent.getID()) { case 500: case 501: case 502: case 504: case 505: processMouseEvent((MouseEvent)paramAWTEvent); break;case 503: case 506: processMouseMotionEvent((MouseEvent)paramAWTEvent); break;case 507: processMouseWheelEvent((MouseWheelEvent)paramAWTEvent); break; }  } else if (paramAWTEvent instanceof KeyEvent) { processKeyEvent((KeyEvent)paramAWTEvent); } else if (paramAWTEvent instanceof ComponentEvent) { processComponentEvent((ComponentEvent)paramAWTEvent); } else if (paramAWTEvent instanceof InputMethodEvent) { processInputMethodEvent((InputMethodEvent)paramAWTEvent); } else if (paramAWTEvent instanceof HierarchyEvent) { switch (paramAWTEvent.getID()) { case 1400: processHierarchyEvent((HierarchyEvent)paramAWTEvent); break;case 1401: case 1402: processHierarchyBoundsEvent((HierarchyEvent)paramAWTEvent); break; }  }  } protected void processComponentEvent(ComponentEvent paramComponentEvent) { ComponentListener componentListener = this.componentListener; if (componentListener != null) { int i = paramComponentEvent.getID(); switch (i) { case 101: componentListener.componentResized(paramComponentEvent); break;case 100: componentListener.componentMoved(paramComponentEvent); break;case 102: componentListener.componentShown(paramComponentEvent); break;case 103: componentListener.componentHidden(paramComponentEvent); break; }  }  } protected void processFocusEvent(FocusEvent paramFocusEvent) { FocusListener focusListener = this.focusListener; if (focusListener != null) { int i = paramFocusEvent.getID(); switch (i) { case 1004: focusListener.focusGained(paramFocusEvent); break;case 1005: focusListener.focusLost(paramFocusEvent); break; }  }  } protected void processKeyEvent(KeyEvent paramKeyEvent) { KeyListener keyListener = this.keyListener; if (keyListener != null) { int i = paramKeyEvent.getID(); switch (i) { case 400: keyListener.keyTyped(paramKeyEvent); break;case 401: keyListener.keyPressed(paramKeyEvent); break;case 402: keyListener.keyReleased(paramKeyEvent); break; }  }  } protected void processMouseEvent(MouseEvent paramMouseEvent) { MouseListener mouseListener = this.mouseListener; if (mouseListener != null) { int i = paramMouseEvent.getID(); switch (i) { case 501: mouseListener.mousePressed(paramMouseEvent); break;case 502: mouseListener.mouseReleased(paramMouseEvent); break;case 500: mouseListener.mouseClicked(paramMouseEvent); break;case 505: mouseListener.mouseExited(paramMouseEvent); break;case 504: mouseListener.mouseEntered(paramMouseEvent); break; }  }  } protected void processMouseMotionEvent(MouseEvent paramMouseEvent) { MouseMotionListener mouseMotionListener = this.mouseMotionListener; if (mouseMotionListener != null) { int i = paramMouseEvent.getID(); switch (i) { case 503: mouseMotionListener.mouseMoved(paramMouseEvent); break;case 506: mouseMotionListener.mouseDragged(paramMouseEvent); break; }  }  } protected void processMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent) { MouseWheelListener mouseWheelListener = this.mouseWheelListener; if (mouseWheelListener != null) { int i = paramMouseWheelEvent.getID(); switch (i) { case 507: mouseWheelListener.mouseWheelMoved(paramMouseWheelEvent); break; }  }  } boolean postsOldMouseEvents() { return false; } protected void processInputMethodEvent(InputMethodEvent paramInputMethodEvent) { InputMethodListener inputMethodListener = this.inputMethodListener; if (inputMethodListener != null) { int i = paramInputMethodEvent.getID(); switch (i) { case 1100: inputMethodListener.inputMethodTextChanged(paramInputMethodEvent); break;case 1101: inputMethodListener.caretPositionChanged(paramInputMethodEvent); break; }  }  } protected void processHierarchyEvent(HierarchyEvent paramHierarchyEvent) { HierarchyListener hierarchyListener = this.hierarchyListener; if (hierarchyListener != null) { int i = paramHierarchyEvent.getID(); switch (i) { case 1400: hierarchyListener.hierarchyChanged(paramHierarchyEvent); break; }  }  } protected void processHierarchyBoundsEvent(HierarchyEvent paramHierarchyEvent) { HierarchyBoundsListener hierarchyBoundsListener = this.hierarchyBoundsListener; if (hierarchyBoundsListener != null) { int i = paramHierarchyEvent.getID(); switch (i) { case 1401: hierarchyBoundsListener.ancestorMoved(paramHierarchyEvent); break;case 1402: hierarchyBoundsListener.ancestorResized(paramHierarchyEvent); break; }  }  } @Deprecated public boolean handleEvent(Event paramEvent) { switch (paramEvent.id) { case 504: return mouseEnter(paramEvent, paramEvent.x, paramEvent.y);case 505: return mouseExit(paramEvent, paramEvent.x, paramEvent.y);case 503: return mouseMove(paramEvent, paramEvent.x, paramEvent.y);case 501: return mouseDown(paramEvent, paramEvent.x, paramEvent.y);case 506: return mouseDrag(paramEvent, paramEvent.x, paramEvent.y);case 502: return mouseUp(paramEvent, paramEvent.x, paramEvent.y);case 401: case 403: return keyDown(paramEvent, paramEvent.key);case 402: case 404: return keyUp(paramEvent, paramEvent.key);case 1001: return action(paramEvent, paramEvent.arg);case 1004: return gotFocus(paramEvent, paramEvent.arg);case 1005: return lostFocus(paramEvent, paramEvent.arg); }  return false; } @Deprecated public boolean mouseDown(Event paramEvent, int paramInt1, int paramInt2) { return false; }
/*       */   @Deprecated public boolean mouseDrag(Event paramEvent, int paramInt1, int paramInt2) { return false; }
/*       */   @Deprecated public boolean mouseUp(Event paramEvent, int paramInt1, int paramInt2) { return false; }
/*       */   @Deprecated public boolean mouseMove(Event paramEvent, int paramInt1, int paramInt2) { return false; }
/*       */   @Deprecated public boolean mouseEnter(Event paramEvent, int paramInt1, int paramInt2) { return false; }
/*       */   @Deprecated public boolean mouseExit(Event paramEvent, int paramInt1, int paramInt2) { return false; }
/*       */   @Deprecated public boolean keyDown(Event paramEvent, int paramInt) { return false; }
/*       */   @Deprecated public boolean keyUp(Event paramEvent, int paramInt) { return false; }
/*       */   @Deprecated public boolean action(Event paramEvent, Object paramObject) { return false; }
/*  6144 */   protected Component() { this.coalescingEnabled = checkCoalescing();
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8059 */     this.autoFocusTransferOnDisposal = true;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  8594 */     this.componentSerializedDataVersion = 4;
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*  9020 */     this.accessibleContext = null; this.appContext = AppContext.getAppContext(); } public void addNotify() { synchronized (getTreeLock()) { ComponentPeer componentPeer = this.peer; if (componentPeer == null || componentPeer instanceof java.awt.peer.LightweightPeer) { if (componentPeer == null) this.peer = componentPeer = getToolkit().createComponent(this);  if (this.parent != null) { long l = 0L; if (this.mouseListener != null || (this.eventMask & 0x10L) != 0L) l |= 0x10L;  if (this.mouseMotionListener != null || (this.eventMask & 0x20L) != 0L) l |= 0x20L;  if (this.mouseWheelListener != null || (this.eventMask & 0x20000L) != 0L) l |= 0x20000L;  if (this.focusListener != null || (this.eventMask & 0x4L) != 0L) l |= 0x4L;  if (this.keyListener != null || (this.eventMask & 0x8L) != 0L) l |= 0x8L;  if (l != 0L) this.parent.proxyEnableEvents(l);  }  } else { Container container = getContainer(); if (container != null && container.isLightweight()) { relocateComponent(); if (!container.isRecursivelyVisibleUpToHeavyweightContainer()) componentPeer.setVisible(false);  }  }  invalidate(); byte b1 = (this.popups != null) ? this.popups.size() : 0; for (byte b2 = 0; b2 < b1; b2++) { PopupMenu popupMenu = this.popups.elementAt(b2); popupMenu.addNotify(); }  if (this.dropTarget != null) this.dropTarget.addNotify(componentPeer);  this.peerFont = getFont(); if (getContainer() != null && !this.isAddNotifyComplete) getContainer().increaseComponentCount(this);  updateZOrder(); if (!this.isAddNotifyComplete) mixOnShowing();  this.isAddNotifyComplete = true; if (this.hierarchyListener != null || (this.eventMask & 0x8000L) != 0L || Toolkit.enabledOnToolkit(32768L)) { HierarchyEvent hierarchyEvent = new HierarchyEvent(this, 1400, this, this.parent, (0x2 | (isRecursivelyVisible() ? 4 : 0))); dispatchEvent(hierarchyEvent); }  }  } public void removeNotify() { KeyboardFocusManager.clearMostRecentFocusOwner(this); if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner() == this) KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalPermanentFocusOwner(null);  synchronized (getTreeLock()) { if (isFocusOwner() && KeyboardFocusManager.isAutoFocusTransferEnabledFor(this)) transferFocus(true);  if (getContainer() != null && this.isAddNotifyComplete) getContainer().decreaseComponentCount(this);  byte b1 = (this.popups != null) ? this.popups.size() : 0; for (byte b2 = 0; b2 < b1; b2++) { PopupMenu popupMenu = this.popups.elementAt(b2); popupMenu.removeNotify(); }  if ((this.eventMask & 0x1000L) != 0L) { InputContext inputContext = getInputContext(); if (inputContext != null) inputContext.removeNotify(this);  }  ComponentPeer componentPeer = this.peer; if (componentPeer != null) { boolean bool = isLightweight(); if (this.bufferStrategy instanceof FlipBufferStrategy) ((FlipBufferStrategy)this.bufferStrategy).destroyBuffers();  if (this.dropTarget != null) this.dropTarget.removeNotify(this.peer);  if (this.visible) componentPeer.setVisible(false);  this.peer = null; this.peerFont = null; Toolkit.getEventQueue().removeSourceEvents(this, false); KeyboardFocusManager.getCurrentKeyboardFocusManager().discardKeyEvents(this); componentPeer.dispose(); mixOnHiding(bool); this.isAddNotifyComplete = false; this.compoundShape = null; }  if (this.hierarchyListener != null || (this.eventMask & 0x8000L) != 0L || Toolkit.enabledOnToolkit(32768L)) { HierarchyEvent hierarchyEvent = new HierarchyEvent(this, 1400, this, this.parent, (0x2 | (isRecursivelyVisible() ? 4 : 0))); dispatchEvent(hierarchyEvent); }  }  } @Deprecated public boolean gotFocus(Event paramEvent, Object paramObject) { return false; } @Deprecated public boolean lostFocus(Event paramEvent, Object paramObject) { return false; } @Deprecated public boolean isFocusTraversable() { if (this.isFocusTraversableOverridden == 0) this.isFocusTraversableOverridden = 1;  return this.focusable; } public boolean isFocusable() { return isFocusTraversable(); } public void setFocusable(boolean paramBoolean) { boolean bool; synchronized (this) { bool = this.focusable; this.focusable = paramBoolean; }  this.isFocusTraversableOverridden = 2; firePropertyChange("focusable", bool, paramBoolean); if (bool && !paramBoolean) { if (isFocusOwner() && KeyboardFocusManager.isAutoFocusTransferEnabled()) transferFocus(true);  KeyboardFocusManager.clearMostRecentFocusOwner(this); }  } final boolean isFocusTraversableOverridden() { return (this.isFocusTraversableOverridden != 1); } public void setFocusTraversalKeys(int paramInt, Set<? extends AWTKeyStroke> paramSet) { if (paramInt < 0 || paramInt >= 3) throw new IllegalArgumentException("invalid focus traversal key identifier");  setFocusTraversalKeys_NoIDCheck(paramInt, paramSet); } public Set<AWTKeyStroke> getFocusTraversalKeys(int paramInt) { if (paramInt < 0 || paramInt >= 3) throw new IllegalArgumentException("invalid focus traversal key identifier");  return getFocusTraversalKeys_NoIDCheck(paramInt); } final void setFocusTraversalKeys_NoIDCheck(int paramInt, Set<? extends AWTKeyStroke> paramSet) { Set<AWTKeyStroke> set; synchronized (this) { if (this.focusTraversalKeys == null) initializeFocusTraversalKeys();  if (paramSet != null) for (AWTKeyStroke aWTKeyStroke : paramSet) { if (aWTKeyStroke == null) throw new IllegalArgumentException("cannot set null focus traversal key");  if (aWTKeyStroke.getKeyChar() != Character.MAX_VALUE) throw new IllegalArgumentException("focus traversal keys cannot map to KEY_TYPED events");  for (byte b = 0; b < this.focusTraversalKeys.length; b++) { if (b != paramInt) if (getFocusTraversalKeys_NoIDCheck(b).contains(aWTKeyStroke)) throw new IllegalArgumentException("focus traversal keys must be unique for a Component");   }  }   set = this.focusTraversalKeys[paramInt]; this.focusTraversalKeys[paramInt] = (paramSet != null) ? Collections.<AWTKeyStroke>unmodifiableSet(new HashSet<>(paramSet)) : null; }  firePropertyChange(focusTraversalKeyPropertyNames[paramInt], set, paramSet); } final Set<AWTKeyStroke> getFocusTraversalKeys_NoIDCheck(int paramInt) { Set<AWTKeyStroke> set = (this.focusTraversalKeys != null) ? this.focusTraversalKeys[paramInt] : null; if (set != null) return set;  Container container = this.parent; if (container != null) return container.getFocusTraversalKeys(paramInt);  return KeyboardFocusManager.getCurrentKeyboardFocusManager().getDefaultFocusTraversalKeys(paramInt); }
/*       */   public boolean areFocusTraversalKeysSet(int paramInt) { if (paramInt < 0 || paramInt >= 3) throw new IllegalArgumentException("invalid focus traversal key identifier");  return (this.focusTraversalKeys != null && this.focusTraversalKeys[paramInt] != null); }
/*       */   public void setFocusTraversalKeysEnabled(boolean paramBoolean) { boolean bool; synchronized (this) { bool = this.focusTraversalKeysEnabled; this.focusTraversalKeysEnabled = paramBoolean; }  firePropertyChange("focusTraversalKeysEnabled", bool, paramBoolean); }
/*       */   public boolean getFocusTraversalKeysEnabled() { return this.focusTraversalKeysEnabled; }
/*       */   public void requestFocus() { requestFocusHelper(false, true); }
/*       */   boolean requestFocus(CausedFocusEvent.Cause paramCause) { return requestFocusHelper(false, true, paramCause); }
/*       */   protected boolean requestFocus(boolean paramBoolean) { return requestFocusHelper(paramBoolean, true); }
/*       */   boolean requestFocus(boolean paramBoolean, CausedFocusEvent.Cause paramCause) { return requestFocusHelper(paramBoolean, true, paramCause); }
/*       */   public boolean requestFocusInWindow() { return requestFocusHelper(false, false); }
/*       */   boolean requestFocusInWindow(CausedFocusEvent.Cause paramCause) { return requestFocusHelper(false, false, paramCause); }
/*       */   protected boolean requestFocusInWindow(boolean paramBoolean) { return requestFocusHelper(paramBoolean, false); }
/*       */   boolean requestFocusInWindow(boolean paramBoolean, CausedFocusEvent.Cause paramCause) { return requestFocusHelper(paramBoolean, false, paramCause); }
/*       */   final boolean requestFocusHelper(boolean paramBoolean1, boolean paramBoolean2) { return requestFocusHelper(paramBoolean1, paramBoolean2, CausedFocusEvent.Cause.UNKNOWN); }
/*       */   final boolean requestFocusHelper(boolean paramBoolean1, boolean paramBoolean2, CausedFocusEvent.Cause paramCause) { AWTEvent aWTEvent = EventQueue.getCurrentEvent(); if (aWTEvent instanceof MouseEvent && SunToolkit.isSystemGenerated(aWTEvent)) { Component component = ((MouseEvent)aWTEvent).getComponent(); if (component == null || component.getContainingWindow() == getContainingWindow()) { focusLog.finest("requesting focus by mouse event \"in window\""); paramBoolean2 = false; }  }  if (!isRequestFocusAccepted(paramBoolean1, paramBoolean2, paramCause)) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("requestFocus is not accepted");  return false; }  KeyboardFocusManager.setMostRecentFocusOwner(this); Component component1 = this; while (component1 != null && !(component1 instanceof Window)) { if (!component1.isVisible()) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("component is recurively invisible");  return false; }  component1 = component1.parent; }  ComponentPeer componentPeer = this.peer; Component component2 = (componentPeer instanceof java.awt.peer.LightweightPeer) ? getNativeContainer() : this; if (component2 == null || !component2.isVisible()) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("Component is not a part of visible hierarchy");  return false; }  componentPeer = component2.peer; if (componentPeer == null) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("Peer is null");  return false; }  long l = 0L; if (EventQueue.isDispatchThread()) { l = Toolkit.getEventQueue().getMostRecentKeyEventTime(); } else { l = System.currentTimeMillis(); }  boolean bool = componentPeer.requestFocus(this, paramBoolean1, paramBoolean2, l, paramCause); if (!bool) { KeyboardFocusManager.getCurrentKeyboardFocusManager(this.appContext).dequeueKeyEvents(l, this); if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("Peer request failed");  } else if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) { focusLog.finest("Pass for " + this); }  return bool; }
/*       */   private boolean isRequestFocusAccepted(boolean paramBoolean1, boolean paramBoolean2, CausedFocusEvent.Cause paramCause) { if (!isFocusable() || !isVisible()) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("Not focusable or not visible");  return false; }  ComponentPeer componentPeer = this.peer; if (componentPeer == null) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("peer is null");  return false; }  Window window = getContainingWindow(); if (window == null || !window.isFocusableWindow()) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("Component doesn't have toplevel");  return false; }  Component component = KeyboardFocusManager.getMostRecentFocusOwner(window); if (component == null) { component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner(); if (component != null && component.getContainingWindow() != window) component = null;  }  if (component == this || component == null) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("focus owner is null or this");  return true; }  if (CausedFocusEvent.Cause.ACTIVATION == paramCause) { if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("cause is activation");  return true; }  boolean bool = requestFocusController.acceptRequestFocus(component, this, paramBoolean1, paramBoolean2, paramCause); if (focusLog.isLoggable(PlatformLogger.Level.FINEST)) focusLog.finest("RequestFocusController returns {0}", new Object[] { Boolean.valueOf(bool) });  return bool; }
/*       */   private static RequestFocusController requestFocusController = new DummyRequestFocusController();
/*  9036 */   public AccessibleContext getAccessibleContext() { return this.accessibleContext; }
/*       */   private boolean autoFocusTransferOnDisposal;
/*       */   private int componentSerializedDataVersion;
/*       */   protected AccessibleContext accessibleContext;
/*       */   private static class DummyRequestFocusController implements RequestFocusController {
/*       */     private DummyRequestFocusController() {}
/*       */     public boolean acceptRequestFocus(Component param1Component1, Component param1Component2, boolean param1Boolean1, boolean param1Boolean2, CausedFocusEvent.Cause param1Cause) { return true; }
/*       */   }
/*       */   static synchronized void setRequestFocusController(RequestFocusController paramRequestFocusController) { if (paramRequestFocusController == null) { requestFocusController = new DummyRequestFocusController(); } else { requestFocusController = paramRequestFocusController; }  }
/*       */   public Container getFocusCycleRootAncestor() { Container container = this.parent; while (container != null && !container.isFocusCycleRoot()) container = container.parent;  return container; }
/*       */   public boolean isFocusCycleRoot(Container paramContainer) { Container container = getFocusCycleRootAncestor(); return (container == paramContainer); }
/*       */   Container getTraversalRoot() { return getFocusCycleRootAncestor(); }
/*       */   public void transferFocus() { nextFocus(); }
/*       */   @Deprecated public void nextFocus() { transferFocus(false); }
/*       */   boolean transferFocus(boolean paramBoolean) { if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("clearOnFailure = " + paramBoolean);  Component component = getNextFocusCandidate(); boolean bool = false; if (component != null && !component.isFocusOwner() && component != this) bool = component.requestFocusInWindow(CausedFocusEvent.Cause.TRAVERSAL_FORWARD);  if (paramBoolean && !bool) { if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("clear global focus owner");  KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwnerPriv(); }  if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("returning result: " + bool);  return bool; }
/*       */   final Component getNextFocusCandidate() { Container container = getTraversalRoot(); Component component1 = this; while (container != null && (!container.isShowing() || !container.canBeFocusOwner())) { component1 = container; container = component1.getFocusCycleRootAncestor(); }  if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("comp = " + component1 + ", root = " + container);  Component component2 = null; if (container != null) { FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy(); Component component = focusTraversalPolicy.getComponentAfter(container, component1); if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("component after is " + component);  if (component == null) { component = focusTraversalPolicy.getDefaultComponent(container); if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("default component is " + component);  }  if (component == null) { Applet applet = EmbeddedFrame.getAppletIfAncestorOf(this); if (applet != null) component = applet;  }  component2 = component; }  if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("Focus transfer candidate: " + component2);  return component2; }
/*       */   public void transferFocusBackward() { transferFocusBackward(false); }
/*       */   boolean transferFocusBackward(boolean paramBoolean) { Container container = getTraversalRoot(); Component component = this; while (container != null && (!container.isShowing() || !container.canBeFocusOwner())) { component = container; container = component.getFocusCycleRootAncestor(); }  boolean bool = false; if (container != null) { FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy(); Component component1 = focusTraversalPolicy.getComponentBefore(container, component); if (component1 == null) component1 = focusTraversalPolicy.getDefaultComponent(container);  if (component1 != null) bool = component1.requestFocusInWindow(CausedFocusEvent.Cause.TRAVERSAL_BACKWARD);  }  if (paramBoolean && !bool) { if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("clear global focus owner");  KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwnerPriv(); }  if (focusLog.isLoggable(PlatformLogger.Level.FINER)) focusLog.finer("returning result: " + bool);  return bool; }
/*       */   public void transferFocusUpCycle() { Container container = getFocusCycleRootAncestor(); while (container != null && (!container.isShowing() || !container.isFocusable() || !container.isEnabled())) container = container.getFocusCycleRootAncestor();  if (container != null) { Container container1 = container.getFocusCycleRootAncestor(); Container container2 = (container1 != null) ? container1 : container; KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalCurrentFocusCycleRootPriv(container2); container.requestFocus(CausedFocusEvent.Cause.TRAVERSAL_UP); } else { Window window = getContainingWindow(); if (window != null) { Component component = window.getFocusTraversalPolicy().getDefaultComponent(window); if (component != null) { KeyboardFocusManager.getCurrentKeyboardFocusManager().setGlobalCurrentFocusCycleRootPriv(window); component.requestFocus(CausedFocusEvent.Cause.TRAVERSAL_UP); }  }  }  }
/*       */   public boolean hasFocus() { return (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this); }
/*       */   public boolean isFocusOwner() { return hasFocus(); }
/*       */   void setAutoFocusTransferOnDisposal(boolean paramBoolean) { this.autoFocusTransferOnDisposal = paramBoolean; }
/*       */   boolean isAutoFocusTransferOnDisposal() { return this.autoFocusTransferOnDisposal; }
/*       */   public void add(PopupMenu paramPopupMenu) { synchronized (getTreeLock()) { if (paramPopupMenu.parent != null) paramPopupMenu.parent.remove(paramPopupMenu);  if (this.popups == null) this.popups = new Vector<>();  this.popups.addElement(paramPopupMenu); paramPopupMenu.parent = this; if (this.peer != null && paramPopupMenu.peer == null) paramPopupMenu.addNotify();  }  }
/*       */   public void remove(MenuComponent paramMenuComponent) { synchronized (getTreeLock()) { if (this.popups == null) return;  int i = this.popups.indexOf(paramMenuComponent); if (i >= 0) { PopupMenu popupMenu = (PopupMenu)paramMenuComponent; if (popupMenu.peer != null) popupMenu.removeNotify();  popupMenu.parent = null; this.popups.removeElementAt(i); if (this.popups.size() == 0) this.popups = null;  }  }  } protected String paramString() { String str1 = Objects.toString(getName(), ""); String str2 = isValid() ? "" : ",invalid"; String str3 = this.visible ? "" : ",hidden"; String str4 = this.enabled ? "" : ",disabled"; return str1 + ',' + this.x + ',' + this.y + ',' + this.width + 'x' + this.height + str2 + str3 + str4; } public String toString() { return getClass().getName() + '[' + paramString() + ']'; } public void list() { list(System.out, 0); } public void list(PrintStream paramPrintStream) { list(paramPrintStream, 0); } public void list(PrintStream paramPrintStream, int paramInt) { for (byte b = 0; b < paramInt; b++) paramPrintStream.print(" ");  paramPrintStream.println(this); } public void list(PrintWriter paramPrintWriter) { list(paramPrintWriter, 0); } public void list(PrintWriter paramPrintWriter, int paramInt) { for (byte b = 0; b < paramInt; b++) paramPrintWriter.print(" ");  paramPrintWriter.println(this); } final Container getNativeContainer() { Container container = getContainer(); while (container != null && container.peer instanceof java.awt.peer.LightweightPeer) container = container.getContainer();  return container; } public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) { synchronized (getObjectLock()) { if (paramPropertyChangeListener == null) return;  if (this.changeSupport == null) this.changeSupport = new PropertyChangeSupport(this);  this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener); }  } public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) { synchronized (getObjectLock()) { if (paramPropertyChangeListener == null || this.changeSupport == null) return;  this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener); }  } public PropertyChangeListener[] getPropertyChangeListeners() { synchronized (getObjectLock()) { if (this.changeSupport == null) return new PropertyChangeListener[0];  return this.changeSupport.getPropertyChangeListeners(); }  } public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) { synchronized (getObjectLock()) { if (paramPropertyChangeListener == null) return;  if (this.changeSupport == null) this.changeSupport = new PropertyChangeSupport(this);  this.changeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener); }  } public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) { synchronized (getObjectLock()) { if (paramPropertyChangeListener == null || this.changeSupport == null) return;  this.changeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener); }  } public PropertyChangeListener[] getPropertyChangeListeners(String paramString) { synchronized (getObjectLock()) { if (this.changeSupport == null) return new PropertyChangeListener[0];  return this.changeSupport.getPropertyChangeListeners(paramString); }  } protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) { PropertyChangeSupport propertyChangeSupport; synchronized (getObjectLock()) { propertyChangeSupport = this.changeSupport; }  if (propertyChangeSupport == null || (paramObject1 != null && paramObject2 != null && paramObject1.equals(paramObject2))) return;  propertyChangeSupport.firePropertyChange(paramString, paramObject1, paramObject2); } protected void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) { PropertyChangeSupport propertyChangeSupport = this.changeSupport; if (propertyChangeSupport == null || paramBoolean1 == paramBoolean2) return;  propertyChangeSupport.firePropertyChange(paramString, paramBoolean1, paramBoolean2); } protected void firePropertyChange(String paramString, int paramInt1, int paramInt2) { PropertyChangeSupport propertyChangeSupport = this.changeSupport; if (propertyChangeSupport == null || paramInt1 == paramInt2) return;  propertyChangeSupport.firePropertyChange(paramString, paramInt1, paramInt2); } public void firePropertyChange(String paramString, byte paramByte1, byte paramByte2) { if (this.changeSupport == null || paramByte1 == paramByte2) return;  firePropertyChange(paramString, Byte.valueOf(paramByte1), Byte.valueOf(paramByte2)); } public void firePropertyChange(String paramString, char paramChar1, char paramChar2) { if (this.changeSupport == null || paramChar1 == paramChar2) return;  firePropertyChange(paramString, new Character(paramChar1), new Character(paramChar2)); } public void firePropertyChange(String paramString, short paramShort1, short paramShort2) { if (this.changeSupport == null || paramShort1 == paramShort2) return;  firePropertyChange(paramString, Short.valueOf(paramShort1), Short.valueOf(paramShort2)); } public void firePropertyChange(String paramString, long paramLong1, long paramLong2) { if (this.changeSupport == null || paramLong1 == paramLong2) return;  firePropertyChange(paramString, Long.valueOf(paramLong1), Long.valueOf(paramLong2)); } public void firePropertyChange(String paramString, float paramFloat1, float paramFloat2) { if (this.changeSupport == null || paramFloat1 == paramFloat2) return;  firePropertyChange(paramString, Float.valueOf(paramFloat1), Float.valueOf(paramFloat2)); } public void firePropertyChange(String paramString, double paramDouble1, double paramDouble2) { if (this.changeSupport == null || paramDouble1 == paramDouble2) return;  firePropertyChange(paramString, Double.valueOf(paramDouble1), Double.valueOf(paramDouble2)); } private void doSwingSerialization() { Package package_ = Package.getPackage("javax.swing"); for (Class<?> clazz = getClass(); clazz != null; clazz = clazz.getSuperclass()) { if (clazz.getPackage() == package_ && clazz.getClassLoader() == null) { final Class<?> swingClass = clazz; Method[] arrayOfMethod = AccessController.<Method[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Method[]>() {
/*       */               public Method[] run() { return swingClass.getDeclaredMethods(); }
/*       */             }); for (int i = arrayOfMethod.length - 1; i >= 0; i--) { final Method method = arrayOfMethod[i]; if (method.getName().equals("compWriteObjectNotify")) { AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*       */                   public Void run() { method.setAccessible(true); return null; }
/*       */                 }); try { method.invoke(this, (Object[])null); } catch (IllegalAccessException illegalAccessException) {  } catch (InvocationTargetException invocationTargetException) {} return; }  }  }  }  } private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException { doSwingSerialization(); paramObjectOutputStream.defaultWriteObject(); AWTEventMulticaster.save(paramObjectOutputStream, "componentL", this.componentListener); AWTEventMulticaster.save(paramObjectOutputStream, "focusL", this.focusListener); AWTEventMulticaster.save(paramObjectOutputStream, "keyL", this.keyListener); AWTEventMulticaster.save(paramObjectOutputStream, "mouseL", this.mouseListener); AWTEventMulticaster.save(paramObjectOutputStream, "mouseMotionL", this.mouseMotionListener); AWTEventMulticaster.save(paramObjectOutputStream, "inputMethodL", this.inputMethodListener); paramObjectOutputStream.writeObject(null); paramObjectOutputStream.writeObject(this.componentOrientation); AWTEventMulticaster.save(paramObjectOutputStream, "hierarchyL", this.hierarchyListener); AWTEventMulticaster.save(paramObjectOutputStream, "hierarchyBoundsL", this.hierarchyBoundsListener); paramObjectOutputStream.writeObject(null); AWTEventMulticaster.save(paramObjectOutputStream, "mouseWheelL", this.mouseWheelListener); paramObjectOutputStream.writeObject(null); } private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException { this.objectLock = new Object(); this.acc = AccessController.getContext(); paramObjectInputStream.defaultReadObject(); this.appContext = AppContext.getAppContext(); this.coalescingEnabled = checkCoalescing(); if (this.componentSerializedDataVersion < 4) { this.focusable = true; this.isFocusTraversableOverridden = 0; initializeFocusTraversalKeys(); this.focusTraversalKeysEnabled = true; }  Object object1; while (null != (object1 = paramObjectInputStream.readObject())) { String str = ((String)object1).intern(); if ("componentL" == str) { addComponentListener((ComponentListener)paramObjectInputStream.readObject()); continue; }  if ("focusL" == str) { addFocusListener((FocusListener)paramObjectInputStream.readObject()); continue; }  if ("keyL" == str) { addKeyListener((KeyListener)paramObjectInputStream.readObject()); continue; }  if ("mouseL" == str) { addMouseListener((MouseListener)paramObjectInputStream.readObject()); continue; }  if ("mouseMotionL" == str) { addMouseMotionListener((MouseMotionListener)paramObjectInputStream.readObject()); continue; }  if ("inputMethodL" == str) { addInputMethodListener((InputMethodListener)paramObjectInputStream.readObject()); continue; }  paramObjectInputStream.readObject(); }  Object object2 = null; try { object2 = paramObjectInputStream.readObject(); } catch (OptionalDataException optionalDataException) { if (!optionalDataException.eof) throw optionalDataException;  }  if (object2 != null) { this.componentOrientation = (ComponentOrientation)object2; } else { this.componentOrientation = ComponentOrientation.UNKNOWN; }  while (null != (object1 = paramObjectInputStream.readObject())) { String str = ((String)object1).intern(); if ("hierarchyL" == str) { addHierarchyListener((HierarchyListener)paramObjectInputStream.readObject()); continue; }  if ("hierarchyBoundsL" == str) { addHierarchyBoundsListener((HierarchyBoundsListener)paramObjectInputStream.readObject()); continue; }  paramObjectInputStream.readObject(); }  while (null != (object1 = paramObjectInputStream.readObject())) { String str = ((String)object1).intern(); if ("mouseWheelL" == str) { addMouseWheelListener((MouseWheelListener)paramObjectInputStream.readObject()); continue; }  paramObjectInputStream.readObject(); }  if (this.popups != null) { int i = this.popups.size(); for (byte b = 0; b < i; b++) { PopupMenu popupMenu = this.popups.elementAt(b); popupMenu.parent = this; }  }  } public void setComponentOrientation(ComponentOrientation paramComponentOrientation) { ComponentOrientation componentOrientation = this.componentOrientation; this.componentOrientation = paramComponentOrientation; firePropertyChange("componentOrientation", componentOrientation, paramComponentOrientation); invalidateIfValid(); } public ComponentOrientation getComponentOrientation() { return this.componentOrientation; } public void applyComponentOrientation(ComponentOrientation paramComponentOrientation) { if (paramComponentOrientation == null) throw new NullPointerException();  setComponentOrientation(paramComponentOrientation); } final boolean canBeFocusOwner() { if (isEnabled() && isDisplayable() && isVisible() && isFocusable()) return true;  return false; } final boolean canBeFocusOwnerRecursively() { if (!canBeFocusOwner()) return false;  synchronized (getTreeLock()) { if (this.parent != null) return this.parent.canContainFocusOwner(this);  }  return true; } final void relocateComponent() { synchronized (getTreeLock()) { if (this.peer == null) return;  int i = this.x; int j = this.y; Container container = getContainer(); for (; container != null && container.isLightweight(); container = container.getContainer()) { i += container.x; j += container.y; }  this.peer.setBounds(i, j, this.width, this.height, 1); }  } Window getContainingWindow() { return SunToolkit.getContainingWindow(this); } protected abstract class AccessibleAWTComponent extends AccessibleContext implements Serializable, AccessibleComponent {
/*  9065 */     private volatile transient int propertyListenersCount = 0;
/*       */     private static final long serialVersionUID = 642321655757800191L;
/*  9067 */     protected ComponentListener accessibleAWTComponentHandler = null;
/*  9068 */     protected FocusListener accessibleAWTFocusHandler = null;
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     protected class AccessibleAWTComponentHandler
/*       */       implements ComponentListener
/*       */     {
/*       */       public void componentHidden(ComponentEvent param2ComponentEvent) {
/*  9077 */         if (Component.this.accessibleContext != null) {
/*  9078 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.VISIBLE, null);
/*       */         }
/*       */       }
/*       */ 
/*       */ 
/*       */       
/*       */       public void componentShown(ComponentEvent param2ComponentEvent) {
/*  9085 */         if (Component.this.accessibleContext != null) {
/*  9086 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.VISIBLE);
/*       */         }
/*       */       }
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       public void componentMoved(ComponentEvent param2ComponentEvent) {}
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*       */       public void componentResized(ComponentEvent param2ComponentEvent) {}
/*       */     }
/*       */ 
/*       */ 
/*       */     
/*       */     protected class AccessibleAWTFocusHandler
/*       */       implements FocusListener
/*       */     {
/*       */       public void focusGained(FocusEvent param2FocusEvent) {
/*  9107 */         if (Component.this.accessibleContext != null) {
/*  9108 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.FOCUSED);
/*       */         }
/*       */       }
/*       */ 
/*       */       
/*       */       public void focusLost(FocusEvent param2FocusEvent) {
/*  9114 */         if (Component.this.accessibleContext != null) {
/*  9115 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.FOCUSED, null);
/*       */         }
/*       */       }
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/*  9129 */       if (this.accessibleAWTComponentHandler == null) {
/*  9130 */         this.accessibleAWTComponentHandler = new AccessibleAWTComponentHandler();
/*       */       }
/*  9132 */       if (this.accessibleAWTFocusHandler == null) {
/*  9133 */         this.accessibleAWTFocusHandler = new AccessibleAWTFocusHandler();
/*       */       }
/*  9135 */       if (this.propertyListenersCount++ == 0) {
/*  9136 */         Component.this.addComponentListener(this.accessibleAWTComponentHandler);
/*  9137 */         Component.this.addFocusListener(this.accessibleAWTFocusHandler);
/*       */       } 
/*  9139 */       super.addPropertyChangeListener(param1PropertyChangeListener);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void removePropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/*  9150 */       if (--this.propertyListenersCount == 0) {
/*  9151 */         Component.this.removeComponentListener(this.accessibleAWTComponentHandler);
/*  9152 */         Component.this.removeFocusListener(this.accessibleAWTFocusHandler);
/*       */       } 
/*  9154 */       super.removePropertyChangeListener(param1PropertyChangeListener);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public String getAccessibleName() {
/*  9175 */       return this.accessibleName;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public String getAccessibleDescription() {
/*  9194 */       return this.accessibleDescription;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public AccessibleRole getAccessibleRole() {
/*  9205 */       return AccessibleRole.AWT_COMPONENT;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public AccessibleStateSet getAccessibleStateSet() {
/*  9216 */       return Component.this.getAccessibleStateSet();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Accessible getAccessibleParent() {
/*  9229 */       if (this.accessibleParent != null) {
/*  9230 */         return this.accessibleParent;
/*       */       }
/*  9232 */       Container container = Component.this.getParent();
/*  9233 */       if (container instanceof Accessible) {
/*  9234 */         return (Accessible)container;
/*       */       }
/*       */       
/*  9237 */       return null;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public int getAccessibleIndexInParent() {
/*  9248 */       return Component.this.getAccessibleIndexInParent();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public int getAccessibleChildrenCount() {
/*  9259 */       return 0;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Accessible getAccessibleChild(int param1Int) {
/*  9269 */       return null;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Locale getLocale() {
/*  9278 */       return Component.this.getLocale();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public AccessibleComponent getAccessibleComponent() {
/*  9289 */       return this;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Color getBackground() {
/*  9302 */       return Component.this.getBackground();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setBackground(Color param1Color) {
/*  9313 */       Component.this.setBackground(param1Color);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Color getForeground() {
/*  9323 */       return Component.this.getForeground();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setForeground(Color param1Color) {
/*  9332 */       Component.this.setForeground(param1Color);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Cursor getCursor() {
/*  9342 */       return Component.this.getCursor();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setCursor(Cursor param1Cursor) {
/*  9354 */       Component.this.setCursor(param1Cursor);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Font getFont() {
/*  9364 */       return Component.this.getFont();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setFont(Font param1Font) {
/*  9373 */       Component.this.setFont(param1Font);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public FontMetrics getFontMetrics(Font param1Font) {
/*  9385 */       if (param1Font == null) {
/*  9386 */         return null;
/*       */       }
/*  9388 */       return Component.this.getFontMetrics(param1Font);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean isEnabled() {
/*  9398 */       return Component.this.isEnabled();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setEnabled(boolean param1Boolean) {
/*  9407 */       boolean bool = Component.this.isEnabled();
/*  9408 */       Component.this.setEnabled(param1Boolean);
/*  9409 */       if (param1Boolean != bool && 
/*  9410 */         Component.this.accessibleContext != null) {
/*  9411 */         if (param1Boolean) {
/*  9412 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.ENABLED);
/*       */         }
/*       */         else {
/*       */           
/*  9416 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.ENABLED, null);
/*       */         } 
/*       */       }
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean isVisible() {
/*  9434 */       return Component.this.isVisible();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setVisible(boolean param1Boolean) {
/*  9443 */       boolean bool = Component.this.isVisible();
/*  9444 */       Component.this.setVisible(param1Boolean);
/*  9445 */       if (param1Boolean != bool && 
/*  9446 */         Component.this.accessibleContext != null) {
/*  9447 */         if (param1Boolean) {
/*  9448 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", null, AccessibleState.VISIBLE);
/*       */         }
/*       */         else {
/*       */           
/*  9452 */           Component.this.accessibleContext.firePropertyChange("AccessibleState", AccessibleState.VISIBLE, null);
/*       */         } 
/*       */       }
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean isShowing() {
/*  9470 */       return Component.this.isShowing();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean contains(Point param1Point) {
/*  9483 */       return Component.this.contains(param1Point);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Point getLocationOnScreen() {
/*  9493 */       synchronized (Component.this.getTreeLock()) {
/*  9494 */         if (Component.this.isShowing()) {
/*  9495 */           return Component.this.getLocationOnScreen();
/*       */         }
/*  9497 */         return null;
/*       */       } 
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Point getLocation() {
/*  9512 */       return Component.this.getLocation();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setLocation(Point param1Point) {
/*  9520 */       Component.this.setLocation(param1Point);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Rectangle getBounds() {
/*  9532 */       return Component.this.getBounds();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setBounds(Rectangle param1Rectangle) {
/*  9544 */       Component.this.setBounds(param1Rectangle);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Dimension getSize() {
/*  9559 */       return Component.this.getSize();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void setSize(Dimension param1Dimension) {
/*  9568 */       Component.this.setSize(param1Dimension);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public Accessible getAccessibleAt(Point param1Point) {
/*  9584 */       return null;
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public boolean isFocusTraversable() {
/*  9593 */       return Component.this.isFocusTraversable();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void requestFocus() {
/*  9600 */       Component.this.requestFocus();
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void addFocusListener(FocusListener param1FocusListener) {
/*  9610 */       Component.this.addFocusListener(param1FocusListener);
/*       */     }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */     
/*       */     public void removeFocusListener(FocusListener param1FocusListener) {
/*  9620 */       Component.this.removeFocusListener(param1FocusListener);
/*       */     }
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   int getAccessibleIndexInParent() {
/*  9634 */     synchronized (getTreeLock()) {
/*  9635 */       byte b = -1;
/*  9636 */       Container container = getParent();
/*  9637 */       if (container != null && container instanceof Accessible) {
/*  9638 */         Component[] arrayOfComponent = container.getComponents();
/*  9639 */         for (byte b1 = 0; b1 < arrayOfComponent.length; b1++) {
/*  9640 */           if (arrayOfComponent[b1] instanceof Accessible) {
/*  9641 */             b++;
/*       */           }
/*  9643 */           if (equals(arrayOfComponent[b1])) {
/*  9644 */             return b;
/*       */           }
/*       */         } 
/*       */       } 
/*  9648 */       return -1;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   AccessibleStateSet getAccessibleStateSet() {
/*  9660 */     synchronized (getTreeLock()) {
/*  9661 */       AccessibleStateSet accessibleStateSet = new AccessibleStateSet();
/*  9662 */       if (isEnabled()) {
/*  9663 */         accessibleStateSet.add(AccessibleState.ENABLED);
/*       */       }
/*  9665 */       if (isFocusTraversable()) {
/*  9666 */         accessibleStateSet.add(AccessibleState.FOCUSABLE);
/*       */       }
/*  9668 */       if (isVisible()) {
/*  9669 */         accessibleStateSet.add(AccessibleState.VISIBLE);
/*       */       }
/*  9671 */       if (isShowing()) {
/*  9672 */         accessibleStateSet.add(AccessibleState.SHOWING);
/*       */       }
/*  9674 */       if (isFocusOwner()) {
/*  9675 */         accessibleStateSet.add(AccessibleState.FOCUSED);
/*       */       }
/*  9677 */       if (this instanceof Accessible) {
/*  9678 */         AccessibleContext accessibleContext = ((Accessible)this).getAccessibleContext();
/*  9679 */         if (accessibleContext != null) {
/*  9680 */           Accessible accessible = accessibleContext.getAccessibleParent();
/*  9681 */           if (accessible != null) {
/*  9682 */             AccessibleContext accessibleContext1 = accessible.getAccessibleContext();
/*  9683 */             if (accessibleContext1 != null) {
/*  9684 */               AccessibleSelection accessibleSelection = accessibleContext1.getAccessibleSelection();
/*  9685 */               if (accessibleSelection != null) {
/*  9686 */                 accessibleStateSet.add(AccessibleState.SELECTABLE);
/*  9687 */                 int i = accessibleContext.getAccessibleIndexInParent();
/*  9688 */                 if (i >= 0 && 
/*  9689 */                   accessibleSelection.isAccessibleChildSelected(i)) {
/*  9690 */                   accessibleStateSet.add(AccessibleState.SELECTED);
/*       */                 }
/*       */               } 
/*       */             } 
/*       */           } 
/*       */         } 
/*       */       } 
/*       */       
/*  9698 */       if (isInstanceOf(this, "javax.swing.JComponent") && (
/*  9699 */         (JComponent)this).isOpaque()) {
/*  9700 */         accessibleStateSet.add(AccessibleState.OPAQUE);
/*       */       }
/*       */       
/*  9703 */       return accessibleStateSet;
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   static boolean isInstanceOf(Object paramObject, String paramString) {
/*  9715 */     if (paramObject == null) return false; 
/*  9716 */     if (paramString == null) return false;
/*       */     
/*  9718 */     Class<?> clazz = paramObject.getClass();
/*  9719 */     while (clazz != null) {
/*  9720 */       if (clazz.getName().equals(paramString)) {
/*  9721 */         return true;
/*       */       }
/*  9723 */       clazz = clazz.getSuperclass();
/*       */     } 
/*  9725 */     return false;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final boolean areBoundsValid() {
/*  9740 */     Container container = getContainer();
/*  9741 */     return (container == null || container.isValid() || container.getLayout() == null);
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void applyCompoundShape(Region paramRegion) {
/*  9749 */     checkTreeLock();
/*       */     
/*  9751 */     if (!areBoundsValid()) {
/*  9752 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9753 */         mixingLog.fine("this = " + this + "; areBoundsValid = " + areBoundsValid());
/*       */       }
/*       */       
/*       */       return;
/*       */     } 
/*  9758 */     if (!isLightweight()) {
/*  9759 */       ComponentPeer componentPeer = getPeer();
/*  9760 */       if (componentPeer != null) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9766 */         if (paramRegion.isEmpty()) {
/*  9767 */           paramRegion = Region.EMPTY_REGION;
/*       */         }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */         
/*  9776 */         if (paramRegion.equals(getNormalShape())) {
/*  9777 */           if (this.compoundShape == null) {
/*       */             return;
/*       */           }
/*  9780 */           this.compoundShape = null;
/*  9781 */           componentPeer.applyShape((Region)null);
/*       */         } else {
/*  9783 */           if (paramRegion.equals(getAppliedShape())) {
/*       */             return;
/*       */           }
/*  9786 */           this.compoundShape = paramRegion;
/*  9787 */           Point point = getLocationOnWindow();
/*  9788 */           if (mixingLog.isLoggable(PlatformLogger.Level.FINER)) {
/*  9789 */             mixingLog.fine("this = " + this + "; compAbsolute=" + point + "; shape=" + paramRegion);
/*       */           }
/*       */           
/*  9792 */           componentPeer.applyShape(paramRegion.getTranslatedRegion(-point.x, -point.y));
/*       */         } 
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   private Region getAppliedShape() {
/*  9804 */     checkTreeLock();
/*       */     
/*  9806 */     return (this.compoundShape == null || isLightweight()) ? getNormalShape() : this.compoundShape;
/*       */   }
/*       */   
/*       */   Point getLocationOnWindow() {
/*  9810 */     checkTreeLock();
/*  9811 */     Point point = getLocation();
/*       */     
/*  9813 */     Container container = getContainer();
/*  9814 */     for (; container != null && !(container instanceof Window); 
/*  9815 */       container = container.getContainer()) {
/*       */       
/*  9817 */       point.x += container.getX();
/*  9818 */       point.y += container.getY();
/*       */     } 
/*       */     
/*  9821 */     return point;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   final Region getNormalShape() {
/*  9828 */     checkTreeLock();
/*       */     
/*  9830 */     Point point = getLocationOnWindow();
/*  9831 */     return 
/*  9832 */       Region.getInstanceXYWH(point.x, point.y, 
/*       */ 
/*       */         
/*  9835 */         getWidth(), 
/*  9836 */         getHeight());
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   Region getOpaqueShape() {
/*  9853 */     checkTreeLock();
/*  9854 */     if (this.mixingCutoutRegion != null) {
/*  9855 */       return this.mixingCutoutRegion;
/*       */     }
/*  9857 */     return getNormalShape();
/*       */   }
/*       */ 
/*       */   
/*       */   final int getSiblingIndexAbove() {
/*  9862 */     checkTreeLock();
/*  9863 */     Container container = getContainer();
/*  9864 */     if (container == null) {
/*  9865 */       return -1;
/*       */     }
/*       */     
/*  9868 */     int i = container.getComponentZOrder(this) - 1;
/*       */     
/*  9870 */     return (i < 0) ? -1 : i;
/*       */   }
/*       */   
/*       */   final ComponentPeer getHWPeerAboveMe() {
/*  9874 */     checkTreeLock();
/*       */     
/*  9876 */     Container container = getContainer();
/*  9877 */     int i = getSiblingIndexAbove();
/*       */     
/*  9879 */     while (container != null) {
/*  9880 */       for (int j = i; j > -1; j--) {
/*  9881 */         Component component = container.getComponent(j);
/*  9882 */         if (component != null && component.isDisplayable() && !component.isLightweight()) {
/*  9883 */           return component.getPeer();
/*       */         }
/*       */       } 
/*       */ 
/*       */ 
/*       */ 
/*       */       
/*  9890 */       if (!container.isLightweight()) {
/*       */         break;
/*       */       }
/*       */       
/*  9894 */       i = container.getSiblingIndexAbove();
/*  9895 */       container = container.getContainer();
/*       */     } 
/*       */     
/*  9898 */     return null;
/*       */   }
/*       */   
/*       */   final int getSiblingIndexBelow() {
/*  9902 */     checkTreeLock();
/*  9903 */     Container container = getContainer();
/*  9904 */     if (container == null) {
/*  9905 */       return -1;
/*       */     }
/*       */     
/*  9908 */     int i = container.getComponentZOrder(this) + 1;
/*       */     
/*  9910 */     return (i >= container.getComponentCount()) ? -1 : i;
/*       */   }
/*       */   
/*       */   final boolean isNonOpaqueForMixing() {
/*  9914 */     return (this.mixingCutoutRegion != null && this.mixingCutoutRegion
/*  9915 */       .isEmpty());
/*       */   }
/*       */   
/*       */   private Region calculateCurrentShape() {
/*  9919 */     checkTreeLock();
/*  9920 */     Region region = getNormalShape();
/*       */     
/*  9922 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9923 */       mixingLog.fine("this = " + this + "; normalShape=" + region);
/*       */     }
/*       */     
/*  9926 */     if (getContainer() != null) {
/*  9927 */       Component component = this;
/*  9928 */       Container container = component.getContainer();
/*       */       
/*  9930 */       while (container != null) {
/*  9931 */         for (int i = component.getSiblingIndexAbove(); i != -1; i--) {
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  9939 */           Component component1 = container.getComponent(i);
/*  9940 */           if (component1.isLightweight() && component1.isShowing()) {
/*  9941 */             region = region.getDifference(component1.getOpaqueShape());
/*       */           }
/*       */         } 
/*       */         
/*  9945 */         if (container.isLightweight()) {
/*  9946 */           region = region.getIntersection(container.getNormalShape());
/*       */ 
/*       */ 
/*       */ 
/*       */           
/*  9951 */           component = container;
/*  9952 */           container = container.getContainer();
/*       */         } 
/*       */       } 
/*       */     } 
/*  9956 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9957 */       mixingLog.fine("currentShape=" + region);
/*       */     }
/*       */     
/*  9960 */     return region;
/*       */   }
/*       */   
/*       */   void applyCurrentShape() {
/*  9964 */     checkTreeLock();
/*  9965 */     if (!areBoundsValid()) {
/*  9966 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9967 */         mixingLog.fine("this = " + this + "; areBoundsValid = " + areBoundsValid());
/*       */       }
/*       */       return;
/*       */     } 
/*  9971 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9972 */       mixingLog.fine("this = " + this);
/*       */     }
/*  9974 */     applyCompoundShape(calculateCurrentShape());
/*       */   }
/*       */   
/*       */   final void subtractAndApplyShape(Region paramRegion) {
/*  9978 */     checkTreeLock();
/*       */     
/*  9980 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/*  9981 */       mixingLog.fine("this = " + this + "; s=" + paramRegion);
/*       */     }
/*       */     
/*  9984 */     applyCompoundShape(getAppliedShape().getDifference(paramRegion));
/*       */   }
/*       */   
/*       */   private final void applyCurrentShapeBelowMe() {
/*  9988 */     checkTreeLock();
/*  9989 */     Container container = getContainer();
/*  9990 */     if (container != null && container.isShowing()) {
/*       */       
/*  9992 */       container.recursiveApplyCurrentShape(getSiblingIndexBelow());
/*       */ 
/*       */       
/*  9995 */       Container container1 = container.getContainer();
/*  9996 */       while (!container.isOpaque() && container1 != null) {
/*  9997 */         container1.recursiveApplyCurrentShape(container.getSiblingIndexBelow());
/*       */         
/*  9999 */         container = container1;
/* 10000 */         container1 = container.getContainer();
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   final void subtractAndApplyShapeBelowMe() {
/* 10006 */     checkTreeLock();
/* 10007 */     Container container = getContainer();
/* 10008 */     if (container != null && isShowing()) {
/* 10009 */       Region region = getOpaqueShape();
/*       */ 
/*       */       
/* 10012 */       container.recursiveSubtractAndApplyShape(region, getSiblingIndexBelow());
/*       */ 
/*       */       
/* 10015 */       Container container1 = container.getContainer();
/* 10016 */       while (!container.isOpaque() && container1 != null) {
/* 10017 */         container1.recursiveSubtractAndApplyShape(region, container.getSiblingIndexBelow());
/*       */         
/* 10019 */         container = container1;
/* 10020 */         container1 = container.getContainer();
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   void mixOnShowing() {
/* 10026 */     synchronized (getTreeLock()) {
/* 10027 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10028 */         mixingLog.fine("this = " + this);
/*       */       }
/* 10030 */       if (!isMixingNeeded()) {
/*       */         return;
/*       */       }
/* 10033 */       if (isLightweight()) {
/* 10034 */         subtractAndApplyShapeBelowMe();
/*       */       } else {
/* 10036 */         applyCurrentShape();
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */   
/*       */   void mixOnHiding(boolean paramBoolean) {
/* 10044 */     synchronized (getTreeLock()) {
/* 10045 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10046 */         mixingLog.fine("this = " + this + "; isLightweight = " + paramBoolean);
/*       */       }
/* 10048 */       if (!isMixingNeeded()) {
/*       */         return;
/*       */       }
/* 10051 */       if (paramBoolean) {
/* 10052 */         applyCurrentShapeBelowMe();
/*       */       }
/*       */     } 
/*       */   }
/*       */   
/*       */   void mixOnReshaping() {
/* 10058 */     synchronized (getTreeLock()) {
/* 10059 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10060 */         mixingLog.fine("this = " + this);
/*       */       }
/* 10062 */       if (!isMixingNeeded()) {
/*       */         return;
/*       */       }
/* 10065 */       if (isLightweight()) {
/* 10066 */         applyCurrentShapeBelowMe();
/*       */       } else {
/* 10068 */         applyCurrentShape();
/*       */       } 
/*       */     } 
/*       */   }
/*       */   
/*       */   void mixOnZOrderChanging(int paramInt1, int paramInt2) {
/* 10074 */     synchronized (getTreeLock()) {
/* 10075 */       boolean bool = (paramInt2 < paramInt1) ? true : false;
/* 10076 */       Container container = getContainer();
/*       */       
/* 10078 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10079 */         mixingLog.fine("this = " + this + "; oldZorder=" + paramInt1 + "; newZorder=" + paramInt2 + "; parent=" + container);
/*       */       }
/*       */       
/* 10082 */       if (!isMixingNeeded()) {
/*       */         return;
/*       */       }
/* 10085 */       if (isLightweight()) {
/* 10086 */         if (bool) {
/* 10087 */           if (container != null && isShowing()) {
/* 10088 */             container.recursiveSubtractAndApplyShape(getOpaqueShape(), getSiblingIndexBelow(), paramInt1);
/*       */           }
/*       */         }
/* 10091 */         else if (container != null) {
/* 10092 */           container.recursiveApplyCurrentShape(paramInt1, paramInt2);
/*       */         }
/*       */       
/*       */       }
/* 10096 */       else if (bool) {
/* 10097 */         applyCurrentShape();
/*       */       }
/* 10099 */       else if (container != null) {
/* 10100 */         Region region = getAppliedShape();
/*       */         
/* 10102 */         for (int i = paramInt1; i < paramInt2; i++) {
/* 10103 */           Component component = container.getComponent(i);
/* 10104 */           if (component.isLightweight() && component.isShowing()) {
/* 10105 */             region = region.getDifference(component.getOpaqueShape());
/*       */           }
/*       */         } 
/* 10108 */         applyCompoundShape(region);
/*       */       } 
/*       */     } 
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void mixOnValidating() {}
/*       */ 
/*       */ 
/*       */   
/*       */   final boolean isMixingNeeded() {
/* 10121 */     if (SunToolkit.getSunAwtDisableMixing()) {
/* 10122 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINEST)) {
/* 10123 */         mixingLog.finest("this = " + this + "; Mixing disabled via sun.awt.disableMixing");
/*       */       }
/* 10125 */       return false;
/*       */     } 
/* 10127 */     if (!areBoundsValid()) {
/* 10128 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10129 */         mixingLog.fine("this = " + this + "; areBoundsValid = " + areBoundsValid());
/*       */       }
/* 10131 */       return false;
/*       */     } 
/* 10133 */     Window window = getContainingWindow();
/* 10134 */     if (window != null) {
/* 10135 */       if (!window.hasHeavyweightDescendants() || !window.hasLightweightDescendants() || window.isDisposing()) {
/* 10136 */         if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10137 */           mixingLog.fine("containing window = " + window + "; has h/w descendants = " + window
/* 10138 */               .hasHeavyweightDescendants() + "; has l/w descendants = " + window
/* 10139 */               .hasLightweightDescendants() + "; disposing = " + window
/* 10140 */               .isDisposing());
/*       */         }
/* 10142 */         return false;
/*       */       } 
/*       */     } else {
/* 10145 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 10146 */         mixingLog.fine("this = " + this + "; containing window is null");
/*       */       }
/* 10148 */       return false;
/*       */     } 
/* 10150 */     return true;
/*       */   }
/*       */ 
/*       */ 
/*       */ 
/*       */ 
/*       */   
/*       */   void updateZOrder() {
/* 10158 */     this.peer.setZOrder(getHWPeerAboveMe());
/*       */   }
/*       */   
/*       */   private static native void initIDs();
/*       */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Component.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */