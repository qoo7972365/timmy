/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.event.ContainerEvent;
/*      */ import java.awt.event.ContainerListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.awt.peer.ContainerPeer;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.swing.JInternalFrame;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.CausedFocusEvent;
/*      */ import sun.awt.PeerEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.java2d.pipe.Region;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Container
/*      */   extends Component
/*      */ {
/*   96 */   private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.Container");
/*   97 */   private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.Container");
/*      */   
/*   99 */   private static final Component[] EMPTY_ARRAY = new Component[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   private List<Component> component = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   LayoutManager layoutMgr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LightweightDispatcher dispatcher;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient FocusTraversalPolicy focusTraversalPolicy;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean focusCycleRoot = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean focusTraversalPolicyProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Set<Thread> printingThreads;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean printing = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient ContainerListener containerListener;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient int listeningChildren;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient int listeningBoundsChildren;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   transient int descendantsCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  179 */   transient Color preserveBackgroundColor = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 4613797578919906343L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean INCLUDE_SELF = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final boolean SEARCH_HEAVYWEIGHTS = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  209 */   private transient int numOfHWComponents = 0;
/*  210 */   private transient int numOfLWComponents = 0;
/*      */   
/*  212 */   private static final PlatformLogger mixingLog = PlatformLogger.getLogger("java.awt.mixing.Container");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  241 */   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("ncomponents", int.class), new ObjectStreamField("component", Component[].class), new ObjectStreamField("layoutMgr", LayoutManager.class), new ObjectStreamField("dispatcher", LightweightDispatcher.class), new ObjectStreamField("maxSize", Dimension.class), new ObjectStreamField("focusCycleRoot", boolean.class), new ObjectStreamField("containerSerializedDataVersion", int.class), new ObjectStreamField("focusTraversalPolicyProvider", boolean.class) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  254 */     Toolkit.loadLibraries();
/*  255 */     if (!GraphicsEnvironment.isHeadless()) {
/*  256 */       initIDs();
/*      */     }
/*      */     
/*  259 */     AWTAccessor.setContainerAccessor(new AWTAccessor.ContainerAccessor()
/*      */         {
/*      */           public void validateUnconditionally(Container param1Container) {
/*  262 */             param1Container.validateUnconditionally();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public Component findComponentAt(Container param1Container, int param1Int1, int param1Int2, boolean param1Boolean) {
/*  268 */             return param1Container.findComponentAt(param1Int1, param1Int2, param1Boolean);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void initializeFocusTraversalKeys() {
/*  289 */     this.focusTraversalKeys = (Set<AWTKeyStroke>[])new Set[4];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getComponentCount() {
/*  303 */     return countComponents();
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
/*      */   public int countComponents() {
/*  315 */     return this.component.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getComponent(int paramInt) {
/*      */     try {
/*  334 */       return this.component.get(paramInt);
/*  335 */     } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/*  336 */       throw new ArrayIndexOutOfBoundsException("No such child: " + paramInt);
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
/*      */   public Component[] getComponents() {
/*  352 */     return getComponents_NoClientCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Component[] getComponents_NoClientCode() {
/*  360 */     return this.component.<Component>toArray(EMPTY_ARRAY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Component[] getComponentsSync() {
/*  367 */     synchronized (getTreeLock()) {
/*  368 */       return getComponents();
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
/*      */   public Insets getInsets() {
/*  384 */     return insets();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Insets insets() {
/*  393 */     ComponentPeer componentPeer = this.peer;
/*  394 */     if (componentPeer instanceof ContainerPeer) {
/*  395 */       ContainerPeer containerPeer = (ContainerPeer)componentPeer;
/*  396 */       return (Insets)containerPeer.getInsets().clone();
/*      */     } 
/*  398 */     return new Insets(0, 0, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component add(Component paramComponent) {
/*  419 */     addImpl(paramComponent, (Object)null, -1);
/*  420 */     return paramComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component add(String paramString, Component paramComponent) {
/*  440 */     addImpl(paramComponent, paramString, -1);
/*  441 */     return paramComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  469 */     addImpl(paramComponent, (Object)null, paramInt);
/*  470 */     return paramComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkAddToSelf(Component paramComponent) {
/*  478 */     if (paramComponent instanceof Container) {
/*  479 */       for (Container container = this; container != null; container = container.parent) {
/*  480 */         if (container == paramComponent) {
/*  481 */           throw new IllegalArgumentException("adding container's parent to itself");
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkNotAWindow(Component paramComponent) {
/*  491 */     if (paramComponent instanceof Window) {
/*  492 */       throw new IllegalArgumentException("adding a window to a container");
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
/*      */   private void checkAdding(Component paramComponent, int paramInt) {
/*  508 */     checkTreeLock();
/*      */     
/*  510 */     GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
/*      */     
/*  512 */     if (paramInt > this.component.size() || paramInt < 0) {
/*  513 */       throw new IllegalArgumentException("illegal component position");
/*      */     }
/*  515 */     if (paramComponent.parent == this && 
/*  516 */       paramInt == this.component.size()) {
/*  517 */       throw new IllegalArgumentException("illegal component position " + paramInt + " should be less then " + this.component
/*  518 */           .size());
/*      */     }
/*      */     
/*  521 */     checkAddToSelf(paramComponent);
/*  522 */     checkNotAWindow(paramComponent);
/*      */     
/*  524 */     Window window1 = getContainingWindow();
/*  525 */     Window window2 = paramComponent.getContainingWindow();
/*  526 */     if (window1 != window2) {
/*  527 */       throw new IllegalArgumentException("component and container should be in the same top-level window");
/*      */     }
/*  529 */     if (graphicsConfiguration != null) {
/*  530 */       paramComponent.checkGD(graphicsConfiguration.getDevice().getIDstring());
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
/*      */   private boolean removeDelicately(Component paramComponent, Container paramContainer, int paramInt) {
/*  544 */     checkTreeLock();
/*      */     
/*  546 */     int i = getComponentZOrder(paramComponent);
/*  547 */     boolean bool = isRemoveNotifyNeeded(paramComponent, this, paramContainer);
/*  548 */     if (bool) {
/*  549 */       paramComponent.removeNotify();
/*      */     }
/*  551 */     if (paramContainer != this) {
/*  552 */       if (this.layoutMgr != null) {
/*  553 */         this.layoutMgr.removeLayoutComponent(paramComponent);
/*      */       }
/*  555 */       adjustListeningChildren(32768L, 
/*  556 */           -paramComponent.numListening(32768L));
/*  557 */       adjustListeningChildren(65536L, 
/*  558 */           -paramComponent.numListening(65536L));
/*  559 */       adjustDescendants(-paramComponent.countHierarchyMembers());
/*      */       
/*  561 */       paramComponent.parent = null;
/*  562 */       if (bool) {
/*  563 */         paramComponent.setGraphicsConfiguration(null);
/*      */       }
/*  565 */       this.component.remove(i);
/*      */       
/*  567 */       invalidateIfValid();
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  574 */       this.component.remove(i);
/*  575 */       this.component.add(paramInt, paramComponent);
/*      */     } 
/*  577 */     if (paramComponent.parent == null) {
/*  578 */       if (this.containerListener != null || (this.eventMask & 0x2L) != 0L || 
/*      */         
/*  580 */         Toolkit.enabledOnToolkit(2L)) {
/*  581 */         ContainerEvent containerEvent = new ContainerEvent(this, 301, paramComponent);
/*      */ 
/*      */         
/*  584 */         dispatchEvent(containerEvent);
/*      */       } 
/*      */       
/*  587 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1L, 
/*      */           
/*  589 */           Toolkit.enabledOnToolkit(32768L));
/*  590 */       if (this.peer != null && this.layoutMgr == null && isVisible()) {
/*  591 */         updateCursorImmediately();
/*      */       }
/*      */     } 
/*  594 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean canContainFocusOwner(Component paramComponent) {
/*  604 */     if (!isEnabled() || !isDisplayable() || 
/*  605 */       !isVisible() || !isFocusable())
/*      */     {
/*  607 */       return false;
/*      */     }
/*  609 */     if (isFocusCycleRoot()) {
/*  610 */       FocusTraversalPolicy focusTraversalPolicy = getFocusTraversalPolicy();
/*  611 */       if (focusTraversalPolicy instanceof DefaultFocusTraversalPolicy && 
/*  612 */         !((DefaultFocusTraversalPolicy)focusTraversalPolicy).accept(paramComponent)) {
/*  613 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  617 */     synchronized (getTreeLock()) {
/*  618 */       if (this.parent != null) {
/*  619 */         return this.parent.canContainFocusOwner(paramComponent);
/*      */       }
/*      */     } 
/*  622 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean hasHeavyweightDescendants() {
/*  632 */     checkTreeLock();
/*  633 */     return (this.numOfHWComponents > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean hasLightweightDescendants() {
/*  643 */     checkTreeLock();
/*  644 */     return (this.numOfLWComponents > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Container getHeavyweightContainer() {
/*  653 */     checkTreeLock();
/*  654 */     if (this.peer != null && !(this.peer instanceof java.awt.peer.LightweightPeer)) {
/*  655 */       return this;
/*      */     }
/*  657 */     return getNativeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isRemoveNotifyNeeded(Component paramComponent, Container paramContainer1, Container paramContainer2) {
/*  669 */     if (paramContainer1 == null) {
/*  670 */       return false;
/*      */     }
/*  672 */     if (paramComponent.peer == null) {
/*  673 */       return false;
/*      */     }
/*  675 */     if (paramContainer2.peer == null)
/*      */     {
/*  677 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  682 */     if (paramComponent.isLightweight()) {
/*  683 */       boolean bool = paramComponent instanceof Container;
/*      */       
/*  685 */       if (!bool || (bool && !((Container)paramComponent).hasHeavyweightDescendants())) {
/*  686 */         return false;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  693 */     Container container1 = paramContainer1.getHeavyweightContainer();
/*  694 */     Container container2 = paramContainer2.getHeavyweightContainer();
/*  695 */     if (container1 != container2)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  703 */       return !paramComponent.peer.isReparentSupported();
/*      */     }
/*  705 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setComponentZOrder(Component paramComponent, int paramInt) {
/*  760 */     synchronized (getTreeLock()) {
/*      */       
/*  762 */       Container container = paramComponent.parent;
/*  763 */       int i = getComponentZOrder(paramComponent);
/*      */       
/*  765 */       if (container == this && paramInt == i) {
/*      */         return;
/*      */       }
/*  768 */       checkAdding(paramComponent, paramInt);
/*      */ 
/*      */       
/*  771 */       boolean bool = (container != null) ? container.removeDelicately(paramComponent, this, paramInt) : false;
/*      */       
/*  773 */       addDelicately(paramComponent, container, paramInt);
/*      */ 
/*      */ 
/*      */       
/*  777 */       if (!bool && i != -1)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  783 */         paramComponent.mixOnZOrderChanging(i, paramInt);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reparentTraverse(ContainerPeer paramContainerPeer, Container paramContainer) {
/*  794 */     checkTreeLock();
/*      */     
/*  796 */     for (byte b = 0; b < paramContainer.getComponentCount(); b++) {
/*  797 */       Component component = paramContainer.getComponent(b);
/*  798 */       if (component.isLightweight()) {
/*      */ 
/*      */         
/*  801 */         if (component instanceof Container) {
/*  802 */           reparentTraverse(paramContainerPeer, (Container)component);
/*      */         }
/*      */       } else {
/*      */         
/*  806 */         component.getPeer().reparent(paramContainerPeer);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void reparentChild(Component paramComponent) {
/*  817 */     checkTreeLock();
/*  818 */     if (paramComponent == null) {
/*      */       return;
/*      */     }
/*  821 */     if (paramComponent.isLightweight()) {
/*      */       
/*  823 */       if (paramComponent instanceof Container)
/*      */       {
/*  825 */         reparentTraverse((ContainerPeer)getPeer(), (Container)paramComponent);
/*      */       }
/*      */     } else {
/*  828 */       paramComponent.getPeer().reparent((ContainerPeer)getPeer());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDelicately(Component paramComponent, Container paramContainer, int paramInt) {
/*  838 */     checkTreeLock();
/*      */ 
/*      */     
/*  841 */     if (paramContainer != this) {
/*      */       
/*  843 */       if (paramInt == -1) {
/*  844 */         this.component.add(paramComponent);
/*      */       } else {
/*  846 */         this.component.add(paramInt, paramComponent);
/*      */       } 
/*  848 */       paramComponent.parent = this;
/*  849 */       paramComponent.setGraphicsConfiguration(getGraphicsConfiguration());
/*      */       
/*  851 */       adjustListeningChildren(32768L, paramComponent
/*  852 */           .numListening(32768L));
/*  853 */       adjustListeningChildren(65536L, paramComponent
/*  854 */           .numListening(65536L));
/*  855 */       adjustDescendants(paramComponent.countHierarchyMembers());
/*      */     }
/*  857 */     else if (paramInt < this.component.size()) {
/*  858 */       this.component.set(paramInt, paramComponent);
/*      */     } 
/*      */ 
/*      */     
/*  862 */     invalidateIfValid();
/*  863 */     if (this.peer != null) {
/*  864 */       if (paramComponent.peer == null) {
/*  865 */         paramComponent.addNotify();
/*      */       } else {
/*      */         
/*  868 */         Container container1 = getHeavyweightContainer();
/*  869 */         Container container2 = paramContainer.getHeavyweightContainer();
/*  870 */         if (container2 != container1)
/*      */         {
/*  872 */           container1.reparentChild(paramComponent);
/*      */         }
/*  874 */         paramComponent.updateZOrder();
/*      */         
/*  876 */         if (!paramComponent.isLightweight() && isLightweight())
/*      */         {
/*      */           
/*  879 */           paramComponent.relocateComponent();
/*      */         }
/*      */       } 
/*      */     }
/*  883 */     if (paramContainer != this) {
/*      */       
/*  885 */       if (this.layoutMgr != null) {
/*  886 */         if (this.layoutMgr instanceof LayoutManager2) {
/*  887 */           ((LayoutManager2)this.layoutMgr).addLayoutComponent(paramComponent, null);
/*      */         } else {
/*  889 */           this.layoutMgr.addLayoutComponent(null, paramComponent);
/*      */         } 
/*      */       }
/*  892 */       if (this.containerListener != null || (this.eventMask & 0x2L) != 0L || 
/*      */         
/*  894 */         Toolkit.enabledOnToolkit(2L)) {
/*  895 */         ContainerEvent containerEvent = new ContainerEvent(this, 300, paramComponent);
/*      */ 
/*      */         
/*  898 */         dispatchEvent(containerEvent);
/*      */       } 
/*  900 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1L, 
/*      */           
/*  902 */           Toolkit.enabledOnToolkit(32768L));
/*      */ 
/*      */ 
/*      */       
/*  906 */       if (paramComponent.isFocusOwner() && !paramComponent.canBeFocusOwnerRecursively()) {
/*  907 */         paramComponent.transferFocus();
/*  908 */       } else if (paramComponent instanceof Container) {
/*  909 */         Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/*  910 */         if (component != null && isParentOf(component) && !component.canBeFocusOwnerRecursively()) {
/*  911 */           component.transferFocus();
/*      */         }
/*      */       } 
/*      */     } else {
/*  915 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1400L, 
/*      */           
/*  917 */           Toolkit.enabledOnToolkit(32768L));
/*      */     } 
/*      */     
/*  920 */     if (this.peer != null && this.layoutMgr == null && isVisible()) {
/*  921 */       updateCursorImmediately();
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
/*      */   public int getComponentZOrder(Component paramComponent) {
/*  939 */     if (paramComponent == null) {
/*  940 */       return -1;
/*      */     }
/*  942 */     synchronized (getTreeLock()) {
/*      */       
/*  944 */       if (paramComponent.parent != this) {
/*  945 */         return -1;
/*      */       }
/*  947 */       return this.component.indexOf(paramComponent);
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
/*      */   public void add(Component paramComponent, Object paramObject) {
/*  975 */     addImpl(paramComponent, paramObject, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(Component paramComponent, Object paramObject, int paramInt) {
/* 1007 */     addImpl(paramComponent, paramObject, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 1078 */     synchronized (getTreeLock()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1086 */       GraphicsConfiguration graphicsConfiguration = getGraphicsConfiguration();
/*      */       
/* 1088 */       if (paramInt > this.component.size() || (paramInt < 0 && paramInt != -1)) {
/* 1089 */         throw new IllegalArgumentException("illegal component position");
/*      */       }
/*      */       
/* 1092 */       checkAddToSelf(paramComponent);
/* 1093 */       checkNotAWindow(paramComponent);
/*      */       
/* 1095 */       if (paramComponent.parent != null) {
/* 1096 */         paramComponent.parent.remove(paramComponent);
/* 1097 */         if (paramInt > this.component.size()) {
/* 1098 */           throw new IllegalArgumentException("illegal component position");
/*      */         }
/*      */       } 
/* 1101 */       if (graphicsConfiguration != null) {
/* 1102 */         paramComponent.checkGD(graphicsConfiguration.getDevice().getIDstring());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1108 */       if (paramInt == -1) {
/* 1109 */         this.component.add(paramComponent);
/*      */       } else {
/* 1111 */         this.component.add(paramInt, paramComponent);
/*      */       } 
/* 1113 */       paramComponent.parent = this;
/* 1114 */       paramComponent.setGraphicsConfiguration(graphicsConfiguration);
/*      */       
/* 1116 */       adjustListeningChildren(32768L, paramComponent
/* 1117 */           .numListening(32768L));
/* 1118 */       adjustListeningChildren(65536L, paramComponent
/* 1119 */           .numListening(65536L));
/* 1120 */       adjustDescendants(paramComponent.countHierarchyMembers());
/*      */       
/* 1122 */       invalidateIfValid();
/* 1123 */       if (this.peer != null) {
/* 1124 */         paramComponent.addNotify();
/*      */       }
/*      */ 
/*      */       
/* 1128 */       if (this.layoutMgr != null) {
/* 1129 */         if (this.layoutMgr instanceof LayoutManager2) {
/* 1130 */           ((LayoutManager2)this.layoutMgr).addLayoutComponent(paramComponent, paramObject);
/* 1131 */         } else if (paramObject instanceof String) {
/* 1132 */           this.layoutMgr.addLayoutComponent((String)paramObject, paramComponent);
/*      */         } 
/*      */       }
/* 1135 */       if (this.containerListener != null || (this.eventMask & 0x2L) != 0L || 
/*      */         
/* 1137 */         Toolkit.enabledOnToolkit(2L)) {
/* 1138 */         ContainerEvent containerEvent = new ContainerEvent(this, 300, paramComponent);
/*      */ 
/*      */         
/* 1141 */         dispatchEvent(containerEvent);
/*      */       } 
/*      */       
/* 1144 */       paramComponent.createHierarchyEvents(1400, paramComponent, this, 1L, 
/*      */           
/* 1146 */           Toolkit.enabledOnToolkit(32768L));
/* 1147 */       if (this.peer != null && this.layoutMgr == null && isVisible()) {
/* 1148 */         updateCursorImmediately();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean updateGraphicsData(GraphicsConfiguration paramGraphicsConfiguration) {
/* 1155 */     checkTreeLock();
/*      */     
/* 1157 */     boolean bool = super.updateGraphicsData(paramGraphicsConfiguration);
/*      */     
/* 1159 */     for (Component component : this.component) {
/* 1160 */       if (component != null) {
/* 1161 */         bool |= component.updateGraphicsData(paramGraphicsConfiguration);
/*      */       }
/*      */     } 
/* 1164 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkGD(String paramString) {
/* 1173 */     for (Component component : this.component) {
/* 1174 */       if (component != null) {
/* 1175 */         component.checkGD(paramString);
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
/*      */   public void remove(int paramInt) {
/* 1203 */     synchronized (getTreeLock()) {
/* 1204 */       if (paramInt < 0 || paramInt >= this.component.size()) {
/* 1205 */         throw new ArrayIndexOutOfBoundsException(paramInt);
/*      */       }
/* 1207 */       Component component = this.component.get(paramInt);
/* 1208 */       if (this.peer != null) {
/* 1209 */         component.removeNotify();
/*      */       }
/* 1211 */       if (this.layoutMgr != null) {
/* 1212 */         this.layoutMgr.removeLayoutComponent(component);
/*      */       }
/*      */       
/* 1215 */       adjustListeningChildren(32768L, 
/* 1216 */           -component.numListening(32768L));
/* 1217 */       adjustListeningChildren(65536L, 
/* 1218 */           -component.numListening(65536L));
/* 1219 */       adjustDescendants(-component.countHierarchyMembers());
/*      */       
/* 1221 */       component.parent = null;
/* 1222 */       this.component.remove(paramInt);
/* 1223 */       component.setGraphicsConfiguration(null);
/*      */       
/* 1225 */       invalidateIfValid();
/* 1226 */       if (this.containerListener != null || (this.eventMask & 0x2L) != 0L || 
/*      */         
/* 1228 */         Toolkit.enabledOnToolkit(2L)) {
/* 1229 */         ContainerEvent containerEvent = new ContainerEvent(this, 301, component);
/*      */ 
/*      */         
/* 1232 */         dispatchEvent(containerEvent);
/*      */       } 
/*      */       
/* 1235 */       component.createHierarchyEvents(1400, component, this, 1L, 
/*      */           
/* 1237 */           Toolkit.enabledOnToolkit(32768L));
/* 1238 */       if (this.peer != null && this.layoutMgr == null && isVisible()) {
/* 1239 */         updateCursorImmediately();
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
/*      */   public void remove(Component paramComponent) {
/* 1263 */     synchronized (getTreeLock()) {
/* 1264 */       if (paramComponent.parent == this) {
/* 1265 */         int i = this.component.indexOf(paramComponent);
/* 1266 */         if (i >= 0) {
/* 1267 */           remove(i);
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
/*      */   public void removeAll() {
/* 1289 */     synchronized (getTreeLock()) {
/* 1290 */       adjustListeningChildren(32768L, -this.listeningChildren);
/*      */       
/* 1292 */       adjustListeningChildren(65536L, -this.listeningBoundsChildren);
/*      */       
/* 1294 */       adjustDescendants(-this.descendantsCount);
/*      */       
/* 1296 */       while (!this.component.isEmpty()) {
/* 1297 */         Component component = this.component.remove(this.component.size() - 1);
/*      */         
/* 1299 */         if (this.peer != null) {
/* 1300 */           component.removeNotify();
/*      */         }
/* 1302 */         if (this.layoutMgr != null) {
/* 1303 */           this.layoutMgr.removeLayoutComponent(component);
/*      */         }
/* 1305 */         component.parent = null;
/* 1306 */         component.setGraphicsConfiguration(null);
/* 1307 */         if (this.containerListener != null || (this.eventMask & 0x2L) != 0L || 
/*      */           
/* 1309 */           Toolkit.enabledOnToolkit(2L)) {
/* 1310 */           ContainerEvent containerEvent = new ContainerEvent(this, 301, component);
/*      */ 
/*      */           
/* 1313 */           dispatchEvent(containerEvent);
/*      */         } 
/*      */         
/* 1316 */         component.createHierarchyEvents(1400, component, this, 1L, 
/*      */ 
/*      */             
/* 1319 */             Toolkit.enabledOnToolkit(32768L));
/*      */       } 
/* 1321 */       if (this.peer != null && this.layoutMgr == null && isVisible()) {
/* 1322 */         updateCursorImmediately();
/*      */       }
/* 1324 */       invalidateIfValid();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   int numListening(long paramLong) {
/* 1330 */     int i = super.numListening(paramLong);
/*      */     
/* 1332 */     if (paramLong == 32768L) {
/* 1333 */       if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*      */         
/* 1335 */         int j = 0;
/* 1336 */         for (Component component : this.component) {
/* 1337 */           j += component.numListening(paramLong);
/*      */         }
/* 1339 */         if (this.listeningChildren != j) {
/* 1340 */           eventLog.fine("Assertion (listeningChildren == sum) failed");
/*      */         }
/*      */       } 
/* 1343 */       return this.listeningChildren + i;
/* 1344 */     }  if (paramLong == 65536L) {
/* 1345 */       if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/*      */         
/* 1347 */         int j = 0;
/* 1348 */         for (Component component : this.component) {
/* 1349 */           j += component.numListening(paramLong);
/*      */         }
/* 1351 */         if (this.listeningBoundsChildren != j) {
/* 1352 */           eventLog.fine("Assertion (listeningBoundsChildren == sum) failed");
/*      */         }
/*      */       } 
/* 1355 */       return this.listeningBoundsChildren + i;
/*      */     } 
/*      */     
/* 1358 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1359 */       eventLog.fine("This code must never be reached");
/*      */     }
/* 1361 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void adjustListeningChildren(long paramLong, int paramInt) {
/* 1367 */     if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 1368 */       boolean bool = (paramLong == 32768L || paramLong == 65536L || paramLong == 98304L) ? true : false;
/*      */ 
/*      */ 
/*      */       
/* 1372 */       if (!bool) {
/* 1373 */         eventLog.fine("Assertion failed");
/*      */       }
/*      */     } 
/*      */     
/* 1377 */     if (paramInt == 0) {
/*      */       return;
/*      */     }
/* 1380 */     if ((paramLong & 0x8000L) != 0L) {
/* 1381 */       this.listeningChildren += paramInt;
/*      */     }
/* 1383 */     if ((paramLong & 0x10000L) != 0L) {
/* 1384 */       this.listeningBoundsChildren += paramInt;
/*      */     }
/*      */     
/* 1387 */     adjustListeningChildrenOnParent(paramLong, paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   void adjustDescendants(int paramInt) {
/* 1392 */     if (paramInt == 0) {
/*      */       return;
/*      */     }
/* 1395 */     this.descendantsCount += paramInt;
/* 1396 */     adjustDecendantsOnParent(paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   void adjustDecendantsOnParent(int paramInt) {
/* 1401 */     if (this.parent != null) {
/* 1402 */       this.parent.adjustDescendants(paramInt);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   int countHierarchyMembers() {
/* 1408 */     if (log.isLoggable(PlatformLogger.Level.FINE)) {
/*      */       
/* 1410 */       int i = 0;
/* 1411 */       for (Component component : this.component) {
/* 1412 */         i += component.countHierarchyMembers();
/*      */       }
/* 1414 */       if (this.descendantsCount != i) {
/* 1415 */         log.fine("Assertion (descendantsCount == sum) failed");
/*      */       }
/*      */     } 
/* 1418 */     return this.descendantsCount + 1;
/*      */   }
/*      */   
/*      */   private int getListenersCount(int paramInt, boolean paramBoolean) {
/* 1422 */     checkTreeLock();
/* 1423 */     if (paramBoolean) {
/* 1424 */       return this.descendantsCount;
/*      */     }
/* 1426 */     switch (paramInt) {
/*      */       case 1400:
/* 1428 */         return this.listeningChildren;
/*      */       case 1401:
/*      */       case 1402:
/* 1431 */         return this.listeningBoundsChildren;
/*      */     } 
/* 1433 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int createHierarchyEvents(int paramInt, Component paramComponent, Container paramContainer, long paramLong, boolean paramBoolean) {
/* 1440 */     checkTreeLock();
/* 1441 */     int i = getListenersCount(paramInt, paramBoolean); int j;
/*      */     byte b;
/* 1443 */     for (j = i, b = 0; j > 0; b++) {
/* 1444 */       j -= ((Component)this.component.get(b)).createHierarchyEvents(paramInt, paramComponent, paramContainer, paramLong, paramBoolean);
/*      */     }
/*      */     
/* 1447 */     return i + super
/* 1448 */       .createHierarchyEvents(paramInt, paramComponent, paramContainer, paramLong, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void createChildHierarchyEvents(int paramInt, long paramLong, boolean paramBoolean) {
/* 1455 */     checkTreeLock();
/* 1456 */     if (this.component.isEmpty()) {
/*      */       return;
/*      */     }
/* 1459 */     int i = getListenersCount(paramInt, paramBoolean); int j;
/*      */     byte b;
/* 1461 */     for (j = i, b = 0; j > 0; b++) {
/* 1462 */       j -= ((Component)this.component.get(b)).createHierarchyEvents(paramInt, this, this.parent, paramLong, paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutManager getLayout() {
/* 1473 */     return this.layoutMgr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLayout(LayoutManager paramLayoutManager) {
/* 1488 */     this.layoutMgr = paramLayoutManager;
/* 1489 */     invalidateIfValid();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doLayout() {
/* 1502 */     layout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void layout() {
/* 1511 */     LayoutManager layoutManager = this.layoutMgr;
/* 1512 */     if (layoutManager != null) {
/* 1513 */       layoutManager.layoutContainer(this);
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
/*      */   public boolean isValidateRoot() {
/* 1547 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1553 */   private static final boolean isJavaAwtSmartInvalidate = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("java.awt.smartInvalidate"))).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void invalidateParent() {
/* 1563 */     if (!isJavaAwtSmartInvalidate || !isValidateRoot()) {
/* 1564 */       super.invalidateParent();
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
/*      */   public void invalidate() {
/* 1585 */     LayoutManager layoutManager = this.layoutMgr;
/* 1586 */     if (layoutManager instanceof LayoutManager2) {
/* 1587 */       LayoutManager2 layoutManager2 = (LayoutManager2)layoutManager;
/* 1588 */       layoutManager2.invalidateLayout(this);
/*      */     } 
/* 1590 */     super.invalidate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validate() {
/* 1621 */     boolean bool = false;
/* 1622 */     synchronized (getTreeLock()) {
/* 1623 */       if ((!isValid() || descendUnconditionallyWhenValidating) && this.peer != null) {
/*      */ 
/*      */         
/* 1626 */         ContainerPeer containerPeer = null;
/* 1627 */         if (this.peer instanceof ContainerPeer) {
/* 1628 */           containerPeer = (ContainerPeer)this.peer;
/*      */         }
/* 1630 */         if (containerPeer != null) {
/* 1631 */           containerPeer.beginValidate();
/*      */         }
/* 1633 */         validateTree();
/* 1634 */         if (containerPeer != null) {
/* 1635 */           containerPeer.endValidate();
/*      */ 
/*      */           
/* 1638 */           if (!descendUnconditionallyWhenValidating) {
/* 1639 */             bool = isVisible();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1644 */     if (bool) {
/* 1645 */       updateCursorImmediately();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean descendUnconditionallyWhenValidating = false;
/*      */ 
/*      */   
/*      */   transient Component modalComp;
/*      */ 
/*      */   
/*      */   transient AppContext modalAppContext;
/*      */ 
/*      */   
/*      */   private int containerSerializedDataVersion;
/*      */ 
/*      */ 
/*      */   
/*      */   final void validateUnconditionally() {
/* 1666 */     boolean bool = false;
/* 1667 */     synchronized (getTreeLock()) {
/* 1668 */       descendUnconditionallyWhenValidating = true;
/*      */       
/* 1670 */       validate();
/* 1671 */       if (this.peer instanceof ContainerPeer) {
/* 1672 */         bool = isVisible();
/*      */       }
/*      */       
/* 1675 */       descendUnconditionallyWhenValidating = false;
/*      */     } 
/* 1677 */     if (bool) {
/* 1678 */       updateCursorImmediately();
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
/*      */   protected void validateTree() {
/* 1692 */     checkTreeLock();
/* 1693 */     if (!isValid() || descendUnconditionallyWhenValidating) {
/* 1694 */       if (this.peer instanceof ContainerPeer) {
/* 1695 */         ((ContainerPeer)this.peer).beginLayout();
/*      */       }
/* 1697 */       if (!isValid()) {
/* 1698 */         doLayout();
/*      */       }
/* 1700 */       for (byte b = 0; b < this.component.size(); b++) {
/* 1701 */         Component component = this.component.get(b);
/* 1702 */         if (component instanceof Container && !(component instanceof Window) && (
/*      */           
/* 1704 */           !component.isValid() || descendUnconditionallyWhenValidating)) {
/*      */ 
/*      */           
/* 1707 */           ((Container)component).validateTree();
/*      */         } else {
/* 1709 */           component.validate();
/*      */         } 
/*      */       } 
/* 1712 */       if (this.peer instanceof ContainerPeer) {
/* 1713 */         ((ContainerPeer)this.peer).endLayout();
/*      */       }
/*      */     } 
/* 1716 */     super.validate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void invalidateTree() {
/* 1724 */     synchronized (getTreeLock()) {
/* 1725 */       for (byte b = 0; b < this.component.size(); b++) {
/* 1726 */         Component component = this.component.get(b);
/* 1727 */         if (component instanceof Container) {
/* 1728 */           ((Container)component).invalidateTree();
/*      */         } else {
/*      */           
/* 1731 */           component.invalidateIfValid();
/*      */         } 
/*      */       } 
/* 1734 */       invalidateIfValid();
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
/*      */   public void setFont(Font paramFont) {
/* 1750 */     boolean bool = false;
/*      */     
/* 1752 */     Font font1 = getFont();
/* 1753 */     super.setFont(paramFont);
/* 1754 */     Font font2 = getFont();
/* 1755 */     if (font2 != font1 && (font1 == null || 
/* 1756 */       !font1.equals(font2))) {
/* 1757 */       invalidateTree();
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
/*      */   public Dimension getPreferredSize() {
/* 1783 */     return preferredSize();
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
/*      */   public Dimension preferredSize() {
/* 1795 */     Dimension dimension = this.prefSize;
/* 1796 */     if (dimension == null || (!isPreferredSizeSet() && !isValid())) {
/* 1797 */       synchronized (getTreeLock()) {
/* 1798 */         this
/*      */           
/* 1800 */           .prefSize = (this.layoutMgr != null) ? this.layoutMgr.preferredLayoutSize(this) : super.preferredSize();
/* 1801 */         dimension = this.prefSize;
/*      */       } 
/*      */     }
/* 1804 */     if (dimension != null) {
/* 1805 */       return new Dimension(dimension);
/*      */     }
/*      */     
/* 1808 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMinimumSize() {
/* 1835 */     return minimumSize();
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
/*      */   public Dimension minimumSize() {
/* 1847 */     Dimension dimension = this.minSize;
/* 1848 */     if (dimension == null || (!isMinimumSizeSet() && !isValid())) {
/* 1849 */       synchronized (getTreeLock()) {
/* 1850 */         this
/*      */           
/* 1852 */           .minSize = (this.layoutMgr != null) ? this.layoutMgr.minimumLayoutSize(this) : super.minimumSize();
/* 1853 */         dimension = this.minSize;
/*      */       } 
/*      */     }
/* 1856 */     if (dimension != null) {
/* 1857 */       return new Dimension(dimension);
/*      */     }
/*      */     
/* 1860 */     return dimension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getMaximumSize() {
/* 1890 */     Dimension dimension = this.maxSize;
/* 1891 */     if (dimension == null || (!isMaximumSizeSet() && !isValid())) {
/* 1892 */       synchronized (getTreeLock()) {
/* 1893 */         if (this.layoutMgr instanceof LayoutManager2) {
/* 1894 */           LayoutManager2 layoutManager2 = (LayoutManager2)this.layoutMgr;
/* 1895 */           this.maxSize = layoutManager2.maximumLayoutSize(this);
/*      */         } else {
/* 1897 */           this.maxSize = super.getMaximumSize();
/*      */         } 
/* 1899 */         dimension = this.maxSize;
/*      */       } 
/*      */     }
/* 1902 */     if (dimension != null) {
/* 1903 */       return new Dimension(dimension);
/*      */     }
/*      */     
/* 1906 */     return dimension;
/*      */   }
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
/*      */     float f;
/* 1919 */     if (this.layoutMgr instanceof LayoutManager2) {
/* 1920 */       synchronized (getTreeLock()) {
/* 1921 */         LayoutManager2 layoutManager2 = (LayoutManager2)this.layoutMgr;
/* 1922 */         f = layoutManager2.getLayoutAlignmentX(this);
/*      */       } 
/*      */     } else {
/* 1925 */       f = super.getAlignmentX();
/*      */     } 
/* 1927 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAlignmentY() {
/*      */     float f;
/* 1939 */     if (this.layoutMgr instanceof LayoutManager2) {
/* 1940 */       synchronized (getTreeLock()) {
/* 1941 */         LayoutManager2 layoutManager2 = (LayoutManager2)this.layoutMgr;
/* 1942 */         f = layoutManager2.getLayoutAlignmentY(this);
/*      */       } 
/*      */     } else {
/* 1945 */       f = super.getAlignmentY();
/*      */     } 
/* 1947 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/* 1962 */     if (isShowing()) {
/* 1963 */       synchronized (getObjectLock()) {
/* 1964 */         if (this.printing && 
/* 1965 */           this.printingThreads.contains(Thread.currentThread())) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1977 */       GraphicsCallback.PaintCallback.getInstance()
/* 1978 */         .runComponents(getComponentsSync(), paramGraphics, 2);
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
/* 1994 */     if (isShowing()) {
/* 1995 */       if (!(this.peer instanceof java.awt.peer.LightweightPeer)) {
/* 1996 */         paramGraphics.clearRect(0, 0, this.width, this.height);
/*      */       }
/* 1998 */       paint(paramGraphics);
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
/*      */   public void print(Graphics paramGraphics) {
/* 2014 */     if (isShowing()) {
/* 2015 */       Thread thread = Thread.currentThread();
/*      */       try {
/* 2017 */         synchronized (getObjectLock()) {
/* 2018 */           if (this.printingThreads == null) {
/* 2019 */             this.printingThreads = new HashSet<>();
/*      */           }
/* 2021 */           this.printingThreads.add(thread);
/* 2022 */           this.printing = true;
/*      */         } 
/* 2024 */         super.print(paramGraphics);
/*      */       } finally {
/* 2026 */         synchronized (getObjectLock()) {
/* 2027 */           this.printingThreads.remove(thread);
/* 2028 */           this.printing = !this.printingThreads.isEmpty();
/*      */         } 
/*      */       } 
/*      */       
/* 2032 */       GraphicsCallback.PrintCallback.getInstance()
/* 2033 */         .runComponents(getComponentsSync(), paramGraphics, 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintComponents(Graphics paramGraphics) {
/* 2044 */     if (isShowing()) {
/* 2045 */       GraphicsCallback.PaintAllCallback.getInstance()
/* 2046 */         .runComponents(getComponentsSync(), paramGraphics, 4);
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
/*      */   void lightweightPaint(Graphics paramGraphics) {
/* 2058 */     super.lightweightPaint(paramGraphics);
/* 2059 */     paintHeavyweightComponents(paramGraphics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void paintHeavyweightComponents(Graphics paramGraphics) {
/* 2066 */     if (isShowing()) {
/* 2067 */       GraphicsCallback.PaintHeavyweightComponentsCallback.getInstance()
/* 2068 */         .runComponents(getComponentsSync(), paramGraphics, 3);
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
/*      */   public void printComponents(Graphics paramGraphics) {
/* 2080 */     if (isShowing()) {
/* 2081 */       GraphicsCallback.PrintAllCallback.getInstance()
/* 2082 */         .runComponents(getComponentsSync(), paramGraphics, 4);
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
/*      */   void lightweightPrint(Graphics paramGraphics) {
/* 2094 */     super.lightweightPrint(paramGraphics);
/* 2095 */     printHeavyweightComponents(paramGraphics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void printHeavyweightComponents(Graphics paramGraphics) {
/* 2102 */     if (isShowing()) {
/* 2103 */       GraphicsCallback.PrintHeavyweightComponentsCallback.getInstance()
/* 2104 */         .runComponents(getComponentsSync(), paramGraphics, 3);
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
/*      */   public synchronized void addContainerListener(ContainerListener paramContainerListener) {
/* 2122 */     if (paramContainerListener == null) {
/*      */       return;
/*      */     }
/* 2125 */     this.containerListener = AWTEventMulticaster.add(this.containerListener, paramContainerListener);
/* 2126 */     this.newEventsOnly = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeContainerListener(ContainerListener paramContainerListener) {
/* 2142 */     if (paramContainerListener == null) {
/*      */       return;
/*      */     }
/* 2145 */     this.containerListener = AWTEventMulticaster.remove(this.containerListener, paramContainerListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized ContainerListener[] getContainerListeners() {
/* 2161 */     return getListeners(ContainerListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends java.util.EventListener> T[] getListeners(Class<T> paramClass) {
/* 2199 */     ContainerListener containerListener = null;
/* 2200 */     if (paramClass == ContainerListener.class) {
/* 2201 */       containerListener = this.containerListener;
/*      */     } else {
/* 2203 */       return super.getListeners(paramClass);
/*      */     } 
/* 2205 */     return AWTEventMulticaster.getListeners(containerListener, paramClass);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean eventEnabled(AWTEvent paramAWTEvent) {
/* 2210 */     int i = paramAWTEvent.getID();
/*      */     
/* 2212 */     if (i == 300 || i == 301) {
/*      */       
/* 2214 */       if ((this.eventMask & 0x2L) != 0L || this.containerListener != null)
/*      */       {
/* 2216 */         return true;
/*      */       }
/* 2218 */       return false;
/*      */     } 
/* 2220 */     return super.eventEnabled(paramAWTEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processEvent(AWTEvent paramAWTEvent) {
/* 2235 */     if (paramAWTEvent instanceof ContainerEvent) {
/* 2236 */       processContainerEvent((ContainerEvent)paramAWTEvent);
/*      */       return;
/*      */     } 
/* 2239 */     super.processEvent(paramAWTEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processContainerEvent(ContainerEvent paramContainerEvent) {
/* 2261 */     ContainerListener containerListener = this.containerListener;
/* 2262 */     if (containerListener != null) {
/* 2263 */       switch (paramContainerEvent.getID()) {
/*      */         case 300:
/* 2265 */           containerListener.componentAdded(paramContainerEvent);
/*      */           break;
/*      */         case 301:
/* 2268 */           containerListener.componentRemoved(paramContainerEvent);
/*      */           break;
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
/*      */   void dispatchEventImpl(AWTEvent paramAWTEvent) {
/* 2283 */     if (this.dispatcher != null && this.dispatcher.dispatchEvent(paramAWTEvent)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2290 */       paramAWTEvent.consume();
/* 2291 */       if (this.peer != null) {
/* 2292 */         this.peer.handleEvent(paramAWTEvent);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 2297 */     super.dispatchEventImpl(paramAWTEvent);
/*      */     
/* 2299 */     synchronized (getTreeLock()) {
/* 2300 */       switch (paramAWTEvent.getID()) {
/*      */         case 101:
/* 2302 */           createChildHierarchyEvents(1402, 0L, 
/* 2303 */               Toolkit.enabledOnToolkit(65536L));
/*      */           break;
/*      */         case 100:
/* 2306 */           createChildHierarchyEvents(1401, 0L, 
/* 2307 */               Toolkit.enabledOnToolkit(65536L));
/*      */           break;
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
/*      */   void dispatchEventToSelf(AWTEvent paramAWTEvent) {
/* 2321 */     super.dispatchEventImpl(paramAWTEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Component getMouseEventTarget(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2329 */     return getMouseEventTarget(paramInt1, paramInt2, paramBoolean, MouseEventTargetFilter.FILTER, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Component getDropTargetEventTarget(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2338 */     return getMouseEventTarget(paramInt1, paramInt2, paramBoolean, DropTargetEventTargetFilter.FILTER, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Component getMouseEventTarget(int paramInt1, int paramInt2, boolean paramBoolean1, EventTargetFilter paramEventTargetFilter, boolean paramBoolean2) {
/* 2358 */     Component component = null;
/* 2359 */     if (paramBoolean2) {
/* 2360 */       component = getMouseEventTargetImpl(paramInt1, paramInt2, paramBoolean1, paramEventTargetFilter, true, paramBoolean2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2365 */     if (component == null || component == this) {
/* 2366 */       component = getMouseEventTargetImpl(paramInt1, paramInt2, paramBoolean1, paramEventTargetFilter, false, paramBoolean2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2371 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Component getMouseEventTargetImpl(int paramInt1, int paramInt2, boolean paramBoolean1, EventTargetFilter paramEventTargetFilter, boolean paramBoolean2, boolean paramBoolean3) {
/* 2400 */     synchronized (getTreeLock()) {
/*      */       byte b;
/* 2402 */       for (b = 0; b < this.component.size(); b++) {
/* 2403 */         Component component = this.component.get(b);
/* 2404 */         if (component != null && component.visible && ((!paramBoolean2 && component.peer instanceof java.awt.peer.LightweightPeer) || (paramBoolean2 && !(component.peer instanceof java.awt.peer.LightweightPeer))) && component
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2409 */           .contains(paramInt1 - component.x, paramInt2 - component.y))
/*      */         {
/*      */ 
/*      */           
/* 2413 */           if (component instanceof Container) {
/* 2414 */             Container container = (Container)component;
/* 2415 */             Component component1 = container.getMouseEventTarget(paramInt1 - container.x, paramInt2 - container.y, paramBoolean1, paramEventTargetFilter, paramBoolean3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2421 */             if (component1 != null) {
/* 2422 */               return component1;
/*      */             }
/*      */           }
/* 2425 */           else if (paramEventTargetFilter.accept(component)) {
/*      */ 
/*      */             
/* 2428 */             return component;
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2437 */       b = (this.peer instanceof java.awt.peer.LightweightPeer || paramBoolean1) ? 1 : 0;
/* 2438 */       boolean bool = contains(paramInt1, paramInt2);
/*      */ 
/*      */ 
/*      */       
/* 2442 */       if (bool && b != 0 && paramEventTargetFilter.accept(this)) {
/* 2443 */         return this;
/*      */       }
/*      */       
/* 2446 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class MouseEventTargetFilter
/*      */     implements EventTargetFilter
/*      */   {
/* 2455 */     static final Container.EventTargetFilter FILTER = new MouseEventTargetFilter();
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean accept(Component param1Component) {
/* 2460 */       return ((param1Component.eventMask & 0x20L) != 0L || (param1Component.eventMask & 0x10L) != 0L || (param1Component.eventMask & 0x20000L) != 0L || param1Component.mouseListener != null || param1Component.mouseMotionListener != null || param1Component.mouseWheelListener != null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DropTargetEventTargetFilter
/*      */     implements EventTargetFilter
/*      */   {
/* 2470 */     static final Container.EventTargetFilter FILTER = new DropTargetEventTargetFilter();
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean accept(Component param1Component) {
/* 2475 */       DropTarget dropTarget = param1Component.getDropTarget();
/* 2476 */       return (dropTarget != null && dropTarget.isActive());
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
/*      */   void proxyEnableEvents(long paramLong) {
/* 2488 */     if (this.peer instanceof java.awt.peer.LightweightPeer) {
/*      */ 
/*      */       
/* 2491 */       if (this.parent != null) {
/* 2492 */         this.parent.proxyEnableEvents(paramLong);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 2500 */     else if (this.dispatcher != null) {
/* 2501 */       this.dispatcher.enableEvents(paramLong);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void deliverEvent(Event paramEvent) {
/* 2512 */     Component component = getComponentAt(paramEvent.x, paramEvent.y);
/* 2513 */     if (component != null && component != this) {
/* 2514 */       paramEvent.translate(-component.x, -component.y);
/* 2515 */       component.deliverEvent(paramEvent);
/*      */     } else {
/* 2517 */       postEvent(paramEvent);
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
/*      */   public Component getComponentAt(int paramInt1, int paramInt2) {
/* 2540 */     return locate(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Component locate(int paramInt1, int paramInt2) {
/* 2549 */     if (!contains(paramInt1, paramInt2)) {
/* 2550 */       return null;
/*      */     }
/* 2552 */     Component component = null;
/* 2553 */     synchronized (getTreeLock()) {
/*      */ 
/*      */       
/* 2556 */       for (Component component1 : this.component) {
/* 2557 */         if (component1.contains(paramInt1 - component1.x, paramInt2 - component1.y)) {
/* 2558 */           if (!component1.isLightweight())
/*      */           {
/* 2560 */             return component1;
/*      */           }
/* 2562 */           if (component == null)
/*      */           {
/* 2564 */             component = component1;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 2569 */     return (component != null) ? component : this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getComponentAt(Point paramPoint) {
/* 2582 */     return getComponentAt(paramPoint.x, paramPoint.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point getMousePosition(boolean paramBoolean) throws HeadlessException {
/* 2605 */     if (GraphicsEnvironment.isHeadless()) {
/* 2606 */       throw new HeadlessException();
/*      */     }
/* 2608 */     PointerInfo pointerInfo = AccessController.<PointerInfo>doPrivileged(new PrivilegedAction<PointerInfo>()
/*      */         {
/*      */           public PointerInfo run() {
/* 2611 */             return MouseInfo.getPointerInfo();
/*      */           }
/*      */         });
/*      */     
/* 2615 */     synchronized (getTreeLock()) {
/* 2616 */       Component component = findUnderMouseInWindow(pointerInfo);
/* 2617 */       if (isSameOrAncestorOf(component, paramBoolean)) {
/* 2618 */         return pointRelativeToComponent(pointerInfo.getLocation());
/*      */       }
/* 2620 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean isSameOrAncestorOf(Component paramComponent, boolean paramBoolean) {
/* 2625 */     return (this == paramComponent || (paramBoolean && isParentOf(paramComponent)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component findComponentAt(int paramInt1, int paramInt2) {
/* 2652 */     return findComponentAt(paramInt1, paramInt2, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Component findComponentAt(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2665 */     synchronized (getTreeLock()) {
/* 2666 */       if (isRecursivelyVisible()) {
/* 2667 */         return findComponentAtImpl(paramInt1, paramInt2, paramBoolean);
/*      */       }
/*      */     } 
/* 2670 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final Component findComponentAtImpl(int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2676 */     if (!contains(paramInt1, paramInt2) || !this.visible || (!paramBoolean && !this.enabled)) {
/* 2677 */       return null;
/*      */     }
/* 2679 */     Component component = null;
/*      */ 
/*      */     
/* 2682 */     for (Component component1 : this.component) {
/* 2683 */       int i = paramInt1 - component1.x;
/* 2684 */       int j = paramInt2 - component1.y;
/* 2685 */       if (!component1.contains(i, j)) {
/*      */         continue;
/*      */       }
/* 2688 */       if (!component1.isLightweight()) {
/* 2689 */         Component component2 = getChildAt(component1, i, j, paramBoolean);
/* 2690 */         if (component2 != null)
/*      */         {
/* 2692 */           return component2; } 
/*      */         continue;
/*      */       } 
/* 2695 */       if (component == null)
/*      */       {
/* 2697 */         component = getChildAt(component1, i, j, paramBoolean);
/*      */       }
/*      */     } 
/*      */     
/* 2701 */     return (component != null) ? component : this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Component getChildAt(Component paramComponent, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 2710 */     if (paramComponent instanceof Container) {
/* 2711 */       paramComponent = ((Container)paramComponent).findComponentAtImpl(paramInt1, paramInt2, paramBoolean);
/*      */     } else {
/*      */       
/* 2714 */       paramComponent = paramComponent.getComponentAt(paramInt1, paramInt2);
/*      */     } 
/* 2716 */     if (paramComponent != null && paramComponent.visible && (paramBoolean || paramComponent.enabled))
/*      */     {
/* 2718 */       return paramComponent;
/*      */     }
/* 2720 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component findComponentAt(Point paramPoint) {
/* 2747 */     return findComponentAt(paramPoint.x, paramPoint.y);
/*      */   }
/*      */ 
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
/* 2760 */     synchronized (getTreeLock()) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2765 */       super.addNotify();
/* 2766 */       if (!(this.peer instanceof java.awt.peer.LightweightPeer)) {
/* 2767 */         this.dispatcher = new LightweightDispatcher(this);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2775 */       for (byte b = 0; b < this.component.size(); b++) {
/* 2776 */         ((Component)this.component.get(b)).addNotify();
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
/*      */   public void removeNotify() {
/* 2791 */     synchronized (getTreeLock()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2797 */       for (int i = this.component.size() - 1; i >= 0; i--) {
/* 2798 */         Component component = this.component.get(i);
/* 2799 */         if (component != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2806 */           component.setAutoFocusTransferOnDisposal(false);
/* 2807 */           component.removeNotify();
/* 2808 */           component.setAutoFocusTransferOnDisposal(true);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2814 */       if (containsFocus() && KeyboardFocusManager.isAutoFocusTransferEnabledFor(this) && 
/* 2815 */         !transferFocus(false)) {
/* 2816 */         transferFocusBackward(true);
/*      */       }
/*      */       
/* 2819 */       if (this.dispatcher != null) {
/* 2820 */         this.dispatcher.dispose();
/* 2821 */         this.dispatcher = null;
/*      */       } 
/* 2823 */       super.removeNotify();
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
/*      */   public boolean isAncestorOf(Component paramComponent) {
/*      */     Container container;
/* 2837 */     if (paramComponent == null || (container = paramComponent.getParent()) == null) {
/* 2838 */       return false;
/*      */     }
/* 2840 */     while (container != null) {
/* 2841 */       if (container == this) {
/* 2842 */         return true;
/*      */       }
/* 2844 */       container = container.getParent();
/*      */     } 
/* 2846 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void startLWModal() {
/*      */     final Container nativeContainer;
/* 2869 */     this.modalAppContext = AppContext.getAppContext();
/*      */ 
/*      */ 
/*      */     
/* 2873 */     long l = Toolkit.getEventQueue().getMostRecentKeyEventTime();
/* 2874 */     Component component = Component.isInstanceOf(this, "javax.swing.JInternalFrame") ? ((JInternalFrame)this).getMostRecentFocusOwner() : null;
/* 2875 */     if (component != null) {
/* 2876 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 2877 */         .enqueueKeyEvents(l, component);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2883 */     synchronized (getTreeLock()) {
/* 2884 */       container = getHeavyweightContainer();
/* 2885 */       if (container.modalComp != null) {
/* 2886 */         this.modalComp = container.modalComp;
/* 2887 */         container.modalComp = this;
/*      */         
/*      */         return;
/*      */       } 
/* 2891 */       container.modalComp = this;
/*      */     } 
/*      */ 
/*      */     
/* 2895 */     Runnable runnable = new Runnable()
/*      */       {
/*      */         public void run() {
/* 2898 */           EventDispatchThread eventDispatchThread = (EventDispatchThread)Thread.currentThread();
/* 2899 */           eventDispatchThread.pumpEventsForHierarchy(new Conditional()
/*      */               {
/*      */                 public boolean evaluate() {
/* 2902 */                   return (Container.this.windowClosingException == null && nativeContainer.modalComp != null);
/*      */                 }
/*      */               },  Container.this);
/*      */         }
/*      */       };
/*      */     
/* 2908 */     if (EventQueue.isDispatchThread()) {
/*      */ 
/*      */       
/* 2911 */       SequencedEvent sequencedEvent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getCurrentSequencedEvent();
/* 2912 */       if (sequencedEvent != null) {
/* 2913 */         sequencedEvent.dispose();
/*      */       }
/*      */       
/* 2916 */       runnable.run();
/*      */     } else {
/* 2918 */       synchronized (getTreeLock()) {
/* 2919 */         Toolkit.getEventQueue()
/* 2920 */           .postEvent(new PeerEvent(this, runnable, 1L));
/*      */ 
/*      */         
/* 2923 */         while (this.windowClosingException == null && container.modalComp != null) {
/*      */ 
/*      */           
/*      */           try {
/* 2927 */             getTreeLock().wait();
/* 2928 */           } catch (InterruptedException interruptedException) {
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 2934 */     if (this.windowClosingException != null) {
/* 2935 */       this.windowClosingException.fillInStackTrace();
/* 2936 */       throw this.windowClosingException;
/*      */     } 
/* 2938 */     if (component != null) {
/* 2939 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 2940 */         .dequeueKeyEvents(l, component);
/*      */     }
/*      */   }
/*      */   
/*      */   private void stopLWModal() {
/* 2945 */     synchronized (getTreeLock()) {
/* 2946 */       if (this.modalAppContext != null) {
/* 2947 */         Container container = getHeavyweightContainer();
/* 2948 */         if (container != null) {
/* 2949 */           if (this.modalComp != null) {
/* 2950 */             container.modalComp = this.modalComp;
/* 2951 */             this.modalComp = null;
/*      */             
/*      */             return;
/*      */           } 
/* 2955 */           container.modalComp = null;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2960 */         SunToolkit.postEvent(this.modalAppContext, new PeerEvent(this, new WakingRunnable(), 1L));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2965 */       EventQueue.invokeLater(new WakingRunnable());
/* 2966 */       getTreeLock().notifyAll();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final class WakingRunnable
/*      */     implements Runnable
/*      */   {
/*      */     public void run() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String paramString() {
/* 2987 */     String str = super.paramString();
/* 2988 */     LayoutManager layoutManager = this.layoutMgr;
/* 2989 */     if (layoutManager != null) {
/* 2990 */       str = str + ",layout=" + layoutManager.getClass().getName();
/*      */     }
/* 2992 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void list(PrintStream paramPrintStream, int paramInt) {
/* 3011 */     super.list(paramPrintStream, paramInt);
/* 3012 */     synchronized (getTreeLock()) {
/* 3013 */       for (byte b = 0; b < this.component.size(); b++) {
/* 3014 */         Component component = this.component.get(b);
/* 3015 */         if (component != null) {
/* 3016 */           component.list(paramPrintStream, paramInt + 1);
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
/*      */   public void list(PrintWriter paramPrintWriter, int paramInt) {
/* 3038 */     super.list(paramPrintWriter, paramInt);
/* 3039 */     synchronized (getTreeLock()) {
/* 3040 */       for (byte b = 0; b < this.component.size(); b++) {
/* 3041 */         Component component = this.component.get(b);
/* 3042 */         if (component != null) {
/* 3043 */           component.list(paramPrintWriter, paramInt + 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3132 */     if (paramInt < 0 || paramInt >= 4) {
/* 3133 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3138 */     setFocusTraversalKeys_NoIDCheck(paramInt, paramSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<AWTKeyStroke> getFocusTraversalKeys(int paramInt) {
/* 3171 */     if (paramInt < 0 || paramInt >= 4) {
/* 3172 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3177 */     return getFocusTraversalKeys_NoIDCheck(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean areFocusTraversalKeysSet(int paramInt) {
/* 3201 */     if (paramInt < 0 || paramInt >= 4) {
/* 3202 */       throw new IllegalArgumentException("invalid focus traversal key identifier");
/*      */     }
/*      */     
/* 3205 */     return (this.focusTraversalKeys != null && this.focusTraversalKeys[paramInt] != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFocusCycleRoot(Container paramContainer) {
/* 3225 */     if (isFocusCycleRoot() && paramContainer == this) {
/* 3226 */       return true;
/*      */     }
/* 3228 */     return super.isFocusCycleRoot(paramContainer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Container findTraversalRoot() {
/* 3241 */     Container container2, container1 = KeyboardFocusManager.getCurrentKeyboardFocusManager().getCurrentFocusCycleRoot();
/*      */ 
/*      */     
/* 3244 */     if (container1 == this) {
/* 3245 */       container2 = this;
/*      */     } else {
/* 3247 */       container2 = getFocusCycleRootAncestor();
/* 3248 */       if (container2 == null) {
/* 3249 */         container2 = this;
/*      */       }
/*      */     } 
/*      */     
/* 3253 */     if (container2 != container1) {
/* 3254 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 3255 */         .setGlobalCurrentFocusCycleRootPriv(container2);
/*      */     }
/* 3257 */     return container2;
/*      */   }
/*      */ 
/*      */   
/*      */   final boolean containsFocus() {
/* 3262 */     Component component = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
/* 3263 */     return isParentOf(component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isParentOf(Component paramComponent) {
/* 3273 */     synchronized (getTreeLock()) {
/* 3274 */       while (paramComponent != null && paramComponent != this && !(paramComponent instanceof Window)) {
/* 3275 */         paramComponent = paramComponent.getParent();
/*      */       }
/* 3277 */       return (paramComponent == this);
/*      */     } 
/*      */   }
/*      */   
/*      */   void clearMostRecentFocusOwnerOnHide() {
/* 3282 */     boolean bool = false;
/* 3283 */     Window window = null;
/*      */     
/* 3285 */     synchronized (getTreeLock()) {
/* 3286 */       window = getContainingWindow();
/* 3287 */       if (window != null) {
/* 3288 */         Component component = KeyboardFocusManager.getMostRecentFocusOwner(window);
/* 3289 */         bool = (component == this || isParentOf(component)) ? true : false;
/*      */ 
/*      */         
/* 3292 */         synchronized (KeyboardFocusManager.class) {
/* 3293 */           Component component1 = window.getTemporaryLostComponent();
/* 3294 */           if (isParentOf(component1) || component1 == this) {
/* 3295 */             window.setTemporaryLostComponent(null);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3301 */     if (bool) {
/* 3302 */       KeyboardFocusManager.setMostRecentFocusOwner(window, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void clearCurrentFocusCycleRootOnHide() {
/* 3308 */     KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
/* 3309 */     Container container = keyboardFocusManager.getCurrentFocusCycleRoot();
/*      */     
/* 3311 */     if (container == this || isParentOf(container)) {
/* 3312 */       keyboardFocusManager.setGlobalCurrentFocusCycleRootPriv(null);
/*      */     }
/*      */   }
/*      */   
/*      */   final Container getTraversalRoot() {
/* 3317 */     if (isFocusCycleRoot()) {
/* 3318 */       return findTraversalRoot();
/*      */     }
/*      */     
/* 3321 */     return super.getTraversalRoot();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFocusTraversalPolicy(FocusTraversalPolicy paramFocusTraversalPolicy) {
/*      */     FocusTraversalPolicy focusTraversalPolicy;
/* 3347 */     synchronized (this) {
/* 3348 */       focusTraversalPolicy = this.focusTraversalPolicy;
/* 3349 */       this.focusTraversalPolicy = paramFocusTraversalPolicy;
/*      */     } 
/* 3351 */     firePropertyChange("focusTraversalPolicy", focusTraversalPolicy, paramFocusTraversalPolicy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FocusTraversalPolicy getFocusTraversalPolicy() {
/* 3369 */     if (!isFocusTraversalPolicyProvider() && !isFocusCycleRoot()) {
/* 3370 */       return null;
/*      */     }
/*      */     
/* 3373 */     FocusTraversalPolicy focusTraversalPolicy = this.focusTraversalPolicy;
/* 3374 */     if (focusTraversalPolicy != null) {
/* 3375 */       return focusTraversalPolicy;
/*      */     }
/*      */     
/* 3378 */     Container container = getFocusCycleRootAncestor();
/* 3379 */     if (container != null) {
/* 3380 */       return container.getFocusTraversalPolicy();
/*      */     }
/* 3382 */     return KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 3383 */       .getDefaultFocusTraversalPolicy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFocusTraversalPolicySet() {
/* 3397 */     return (this.focusTraversalPolicy != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFocusCycleRoot(boolean paramBoolean) {
/*      */     boolean bool;
/* 3427 */     synchronized (this) {
/* 3428 */       bool = this.focusCycleRoot;
/* 3429 */       this.focusCycleRoot = paramBoolean;
/*      */     } 
/* 3431 */     firePropertyChange("focusCycleRoot", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFocusCycleRoot() {
/* 3453 */     return this.focusCycleRoot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setFocusTraversalPolicyProvider(boolean paramBoolean) {
/*      */     boolean bool;
/* 3472 */     synchronized (this) {
/* 3473 */       bool = this.focusTraversalPolicyProvider;
/* 3474 */       this.focusTraversalPolicyProvider = paramBoolean;
/*      */     } 
/* 3476 */     firePropertyChange("focusTraversalPolicyProvider", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isFocusTraversalPolicyProvider() {
/* 3498 */     return this.focusTraversalPolicyProvider;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferFocusDownCycle() {
/* 3514 */     if (isFocusCycleRoot()) {
/* 3515 */       KeyboardFocusManager.getCurrentKeyboardFocusManager()
/* 3516 */         .setGlobalCurrentFocusCycleRootPriv(this);
/*      */       
/* 3518 */       Component component = getFocusTraversalPolicy().getDefaultComponent(this);
/* 3519 */       if (component != null) {
/* 3520 */         component.requestFocus(CausedFocusEvent.Cause.TRAVERSAL_DOWN);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   void preProcessKeyEvent(KeyEvent paramKeyEvent) {
/* 3526 */     Container container = this.parent;
/* 3527 */     if (container != null) {
/* 3528 */       container.preProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   void postProcessKeyEvent(KeyEvent paramKeyEvent) {
/* 3533 */     Container container = this.parent;
/* 3534 */     if (container != null) {
/* 3535 */       container.postProcessKeyEvent(paramKeyEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   boolean postsOldMouseEvents() {
/* 3540 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3559 */     super.applyComponentOrientation(paramComponentOrientation);
/* 3560 */     synchronized (getTreeLock()) {
/* 3561 */       for (byte b = 0; b < this.component.size(); b++) {
/* 3562 */         Component component = this.component.get(b);
/* 3563 */         component.applyComponentOrientation(paramComponentOrientation);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 3602 */     super.addPropertyChangeListener(paramPropertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener) {
/* 3643 */     super.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Container() {
/* 3652 */     this.containerSerializedDataVersion = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 3681 */     ObjectOutputStream.PutField putField = paramObjectOutputStream.putFields();
/* 3682 */     putField.put("ncomponents", this.component.size());
/* 3683 */     putField.put("component", this.component.toArray(EMPTY_ARRAY));
/* 3684 */     putField.put("layoutMgr", this.layoutMgr);
/* 3685 */     putField.put("dispatcher", this.dispatcher);
/* 3686 */     putField.put("maxSize", this.maxSize);
/* 3687 */     putField.put("focusCycleRoot", this.focusCycleRoot);
/* 3688 */     putField.put("containerSerializedDataVersion", this.containerSerializedDataVersion);
/* 3689 */     putField.put("focusTraversalPolicyProvider", this.focusTraversalPolicyProvider);
/* 3690 */     paramObjectOutputStream.writeFields();
/*      */     
/* 3692 */     AWTEventMulticaster.save(paramObjectOutputStream, "containerL", this.containerListener);
/* 3693 */     paramObjectOutputStream.writeObject(null);
/*      */     
/* 3695 */     if (this.focusTraversalPolicy instanceof java.io.Serializable) {
/* 3696 */       paramObjectOutputStream.writeObject(this.focusTraversalPolicy);
/*      */     } else {
/* 3698 */       paramObjectOutputStream.writeObject(null);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 3721 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*      */     
/* 3723 */     Component[] arrayOfComponent = (Component[])getField.get("component", (Object)null);
/* 3724 */     if (arrayOfComponent == null) {
/* 3725 */       arrayOfComponent = EMPTY_ARRAY;
/*      */     }
/* 3727 */     int i = Integer.valueOf(getField.get("ncomponents", 0)).intValue();
/* 3728 */     if (i < 0 || i > arrayOfComponent.length) {
/* 3729 */       throw new InvalidObjectException("Incorrect number of components");
/*      */     }
/* 3731 */     this.component = new ArrayList<>(i);
/* 3732 */     for (byte b = 0; b < i; b++) {
/* 3733 */       this.component.add(arrayOfComponent[b]);
/*      */     }
/* 3735 */     this.layoutMgr = (LayoutManager)getField.get("layoutMgr", (Object)null);
/* 3736 */     this.dispatcher = (LightweightDispatcher)getField.get("dispatcher", (Object)null);
/*      */     
/* 3738 */     if (this.maxSize == null) {
/* 3739 */       this.maxSize = (Dimension)getField.get("maxSize", (Object)null);
/*      */     }
/* 3741 */     this.focusCycleRoot = getField.get("focusCycleRoot", false);
/* 3742 */     this.containerSerializedDataVersion = getField.get("containerSerializedDataVersion", 1);
/* 3743 */     this.focusTraversalPolicyProvider = getField.get("focusTraversalPolicyProvider", false);
/* 3744 */     List<Component> list = this.component;
/* 3745 */     for (Component component : list) {
/* 3746 */       component.parent = this;
/* 3747 */       adjustListeningChildren(32768L, component
/* 3748 */           .numListening(32768L));
/* 3749 */       adjustListeningChildren(65536L, component
/* 3750 */           .numListening(65536L));
/* 3751 */       adjustDescendants(component.countHierarchyMembers());
/*      */     } 
/*      */     
/*      */     Object object;
/* 3755 */     while (null != (object = paramObjectInputStream.readObject())) {
/* 3756 */       String str = ((String)object).intern();
/*      */       
/* 3758 */       if ("containerL" == str) {
/* 3759 */         addContainerListener((ContainerListener)paramObjectInputStream.readObject());
/*      */         continue;
/*      */       } 
/* 3762 */       paramObjectInputStream.readObject();
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 3767 */       Object object1 = paramObjectInputStream.readObject();
/* 3768 */       if (object1 instanceof FocusTraversalPolicy) {
/* 3769 */         this.focusTraversalPolicy = (FocusTraversalPolicy)object1;
/*      */       }
/* 3771 */     } catch (OptionalDataException optionalDataException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3778 */       if (!optionalDataException.eof) {
/* 3779 */         throw optionalDataException;
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
/*      */   protected class AccessibleAWTContainer
/*      */     extends Component.AccessibleAWTComponent
/*      */   {
/*      */     private static final long serialVersionUID = 5081320404842566097L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private volatile transient int propertyListenersCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected ContainerListener accessibleContainerHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/*      */       return Container.this.getAccessibleChildrenCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/*      */       return Container.this.getAccessibleChild(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AccessibleAWTContainer() {
/* 3845 */       this.propertyListenersCount = 0;
/*      */       
/* 3847 */       this.accessibleContainerHandler = null;
/*      */     }
/*      */     
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/*      */       return Container.this.getAccessibleAt(param1Point);
/*      */     }
/*      */     
/*      */     protected class AccessibleContainerHandler
/*      */       implements ContainerListener {
/*      */       public void componentAdded(ContainerEvent param2ContainerEvent) {
/* 3857 */         Component component = param2ContainerEvent.getChild();
/* 3858 */         if (component != null && component instanceof Accessible)
/* 3859 */           Container.AccessibleAWTContainer.this.firePropertyChange("AccessibleChild", null, ((Accessible)component)
/*      */               
/* 3861 */               .getAccessibleContext()); 
/*      */       }
/*      */       
/*      */       public void componentRemoved(ContainerEvent param2ContainerEvent) {
/* 3865 */         Component component = param2ContainerEvent.getChild();
/* 3866 */         if (component != null && component instanceof Accessible) {
/* 3867 */           Container.AccessibleAWTContainer.this.firePropertyChange("AccessibleChild", ((Accessible)component)
/*      */               
/* 3869 */               .getAccessibleContext(), null);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addPropertyChangeListener(PropertyChangeListener param1PropertyChangeListener) {
/* 3880 */       if (this.accessibleContainerHandler == null) {
/* 3881 */         this.accessibleContainerHandler = new AccessibleContainerHandler();
/*      */       }
/* 3883 */       if (this.propertyListenersCount++ == 0) {
/* 3884 */         Container.this.addContainerListener(this.accessibleContainerHandler);
/*      */       }
/* 3886 */       super.addPropertyChangeListener(param1PropertyChangeListener);
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
/* 3897 */       if (--this.propertyListenersCount == 0) {
/* 3898 */         Container.this.removeContainerListener(this.accessibleContainerHandler);
/*      */       }
/* 3900 */       super.removePropertyChangeListener(param1PropertyChangeListener);
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
/*      */   Accessible getAccessibleAt(Point paramPoint) {
/* 3917 */     synchronized (getTreeLock()) {
/* 3918 */       if (this instanceof Accessible) {
/* 3919 */         Accessible accessible = (Accessible)this;
/* 3920 */         AccessibleContext accessibleContext = accessible.getAccessibleContext();
/* 3921 */         if (accessibleContext != null) {
/*      */ 
/*      */           
/* 3924 */           int i = accessibleContext.getAccessibleChildrenCount();
/* 3925 */           for (byte b = 0; b < i; b++) {
/* 3926 */             accessible = accessibleContext.getAccessibleChild(b);
/* 3927 */             if (accessible != null) {
/* 3928 */               accessibleContext = accessible.getAccessibleContext();
/* 3929 */               if (accessibleContext != null) {
/* 3930 */                 AccessibleComponent accessibleComponent = accessibleContext.getAccessibleComponent();
/* 3931 */                 if (accessibleComponent != null && accessibleComponent.isShowing()) {
/* 3932 */                   Point point1 = accessibleComponent.getLocation();
/* 3933 */                   Point point2 = new Point(paramPoint.x - point1.x, paramPoint.y - point1.y);
/*      */                   
/* 3935 */                   if (accessibleComponent.contains(point2)) {
/* 3936 */                     return accessible;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 3943 */         return (Accessible)this;
/*      */       } 
/* 3945 */       Component component = this;
/* 3946 */       if (!contains(paramPoint.x, paramPoint.y)) {
/* 3947 */         component = null;
/*      */       } else {
/* 3949 */         int i = getComponentCount();
/* 3950 */         for (byte b = 0; b < i; b++) {
/* 3951 */           Component component1 = getComponent(b);
/* 3952 */           if (component1 != null && component1.isShowing()) {
/* 3953 */             Point point = component1.getLocation();
/* 3954 */             if (component1.contains(paramPoint.x - point.x, paramPoint.y - point.y)) {
/* 3955 */               component = component1;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 3960 */       if (component instanceof Accessible) {
/* 3961 */         return (Accessible)component;
/*      */       }
/*      */       
/* 3964 */       return null;
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
/*      */   int getAccessibleChildrenCount() {
/* 3976 */     synchronized (getTreeLock()) {
/* 3977 */       byte b1 = 0;
/* 3978 */       Component[] arrayOfComponent = getComponents();
/* 3979 */       for (byte b2 = 0; b2 < arrayOfComponent.length; b2++) {
/* 3980 */         if (arrayOfComponent[b2] instanceof Accessible) {
/* 3981 */           b1++;
/*      */         }
/*      */       } 
/* 3984 */       return b1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Accessible getAccessibleChild(int paramInt) {
/* 3995 */     synchronized (getTreeLock()) {
/* 3996 */       Component[] arrayOfComponent = getComponents();
/* 3997 */       int i = 0;
/* 3998 */       for (byte b = 0; b < arrayOfComponent.length; b++) {
/* 3999 */         if (arrayOfComponent[b] instanceof Accessible) {
/* 4000 */           if (i == paramInt) {
/* 4001 */             return (Accessible)arrayOfComponent[b];
/*      */           }
/* 4003 */           i++;
/*      */         } 
/*      */       } 
/*      */       
/* 4007 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void increaseComponentCount(Component paramComponent) {
/* 4014 */     synchronized (getTreeLock()) {
/* 4015 */       if (!paramComponent.isDisplayable()) {
/* 4016 */         throw new IllegalStateException("Peer does not exist while invoking the increaseComponentCount() method");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 4021 */       int i = 0;
/* 4022 */       int j = 0;
/*      */       
/* 4024 */       if (paramComponent instanceof Container) {
/* 4025 */         j = ((Container)paramComponent).numOfLWComponents;
/* 4026 */         i = ((Container)paramComponent).numOfHWComponents;
/*      */       } 
/* 4028 */       if (paramComponent.isLightweight()) {
/* 4029 */         j++;
/*      */       } else {
/* 4031 */         i++;
/*      */       } 
/*      */       
/* 4034 */       for (Container container = this; container != null; container = container.getContainer()) {
/* 4035 */         container.numOfLWComponents += j;
/* 4036 */         container.numOfHWComponents += i;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   final void decreaseComponentCount(Component paramComponent) {
/* 4042 */     synchronized (getTreeLock()) {
/* 4043 */       if (!paramComponent.isDisplayable()) {
/* 4044 */         throw new IllegalStateException("Peer does not exist while invoking the decreaseComponentCount() method");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 4049 */       int i = 0;
/* 4050 */       int j = 0;
/*      */       
/* 4052 */       if (paramComponent instanceof Container) {
/* 4053 */         j = ((Container)paramComponent).numOfLWComponents;
/* 4054 */         i = ((Container)paramComponent).numOfHWComponents;
/*      */       } 
/* 4056 */       if (paramComponent.isLightweight()) {
/* 4057 */         j++;
/*      */       } else {
/* 4059 */         i++;
/*      */       } 
/*      */       
/* 4062 */       for (Container container = this; container != null; container = container.getContainer()) {
/* 4063 */         container.numOfLWComponents -= j;
/* 4064 */         container.numOfHWComponents -= i;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private int getTopmostComponentIndex() {
/* 4070 */     checkTreeLock();
/* 4071 */     if (getComponentCount() > 0) {
/* 4072 */       return 0;
/*      */     }
/* 4074 */     return -1;
/*      */   }
/*      */   
/*      */   private int getBottommostComponentIndex() {
/* 4078 */     checkTreeLock();
/* 4079 */     if (getComponentCount() > 0) {
/* 4080 */       return getComponentCount() - 1;
/*      */     }
/* 4082 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final Region getOpaqueShape() {
/* 4091 */     checkTreeLock();
/* 4092 */     if (isLightweight() && isNonOpaqueForMixing() && 
/* 4093 */       hasLightweightDescendants()) {
/*      */       
/* 4095 */       Region region = Region.EMPTY_REGION;
/* 4096 */       for (byte b = 0; b < getComponentCount(); b++) {
/* 4097 */         Component component = getComponent(b);
/* 4098 */         if (component.isLightweight() && component.isShowing()) {
/* 4099 */           region = region.getUnion(component.getOpaqueShape());
/*      */         }
/*      */       } 
/* 4102 */       return region.getIntersection(getNormalShape());
/*      */     } 
/* 4104 */     return super.getOpaqueShape();
/*      */   }
/*      */   
/*      */   final void recursiveSubtractAndApplyShape(Region paramRegion) {
/* 4108 */     recursiveSubtractAndApplyShape(paramRegion, getTopmostComponentIndex(), getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveSubtractAndApplyShape(Region paramRegion, int paramInt) {
/* 4112 */     recursiveSubtractAndApplyShape(paramRegion, paramInt, getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveSubtractAndApplyShape(Region paramRegion, int paramInt1, int paramInt2) {
/* 4116 */     checkTreeLock();
/* 4117 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4118 */       mixingLog.fine("this = " + this + "; shape=" + paramRegion + "; fromZ=" + paramInt1 + "; toZ=" + paramInt2);
/*      */     }
/*      */     
/* 4121 */     if (paramInt1 == -1) {
/*      */       return;
/*      */     }
/* 4124 */     if (paramRegion.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4130 */     if (getLayout() != null && !isValid()) {
/*      */       return;
/*      */     }
/* 4133 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 4134 */       Component component = getComponent(i);
/* 4135 */       if (!component.isLightweight()) {
/* 4136 */         component.subtractAndApplyShape(paramRegion);
/* 4137 */       } else if (component instanceof Container && ((Container)component)
/* 4138 */         .hasHeavyweightDescendants() && component.isShowing()) {
/* 4139 */         ((Container)component).recursiveSubtractAndApplyShape(paramRegion);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   final void recursiveApplyCurrentShape() {
/* 4145 */     recursiveApplyCurrentShape(getTopmostComponentIndex(), getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveApplyCurrentShape(int paramInt) {
/* 4149 */     recursiveApplyCurrentShape(paramInt, getBottommostComponentIndex());
/*      */   }
/*      */   
/*      */   final void recursiveApplyCurrentShape(int paramInt1, int paramInt2) {
/* 4153 */     checkTreeLock();
/* 4154 */     if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4155 */       mixingLog.fine("this = " + this + "; fromZ=" + paramInt1 + "; toZ=" + paramInt2);
/*      */     }
/*      */     
/* 4158 */     if (paramInt1 == -1) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4164 */     if (getLayout() != null && !isValid()) {
/*      */       return;
/*      */     }
/* 4167 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 4168 */       Component component = getComponent(i);
/* 4169 */       if (!component.isLightweight()) {
/* 4170 */         component.applyCurrentShape();
/*      */       }
/* 4172 */       if (component instanceof Container && ((Container)component)
/* 4173 */         .hasHeavyweightDescendants()) {
/* 4174 */         ((Container)component).recursiveApplyCurrentShape();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void recursiveShowHeavyweightChildren() {
/* 4180 */     if (!hasHeavyweightDescendants() || !isVisible()) {
/*      */       return;
/*      */     }
/* 4183 */     for (byte b = 0; b < getComponentCount(); b++) {
/* 4184 */       Component component = getComponent(b);
/* 4185 */       if (component.isLightweight()) {
/* 4186 */         if (component instanceof Container) {
/* 4187 */           ((Container)component).recursiveShowHeavyweightChildren();
/*      */         }
/*      */       }
/* 4190 */       else if (component.isVisible()) {
/* 4191 */         ComponentPeer componentPeer = component.getPeer();
/* 4192 */         if (componentPeer != null) {
/* 4193 */           componentPeer.setVisible(true);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void recursiveHideHeavyweightChildren() {
/* 4201 */     if (!hasHeavyweightDescendants()) {
/*      */       return;
/*      */     }
/* 4204 */     for (byte b = 0; b < getComponentCount(); b++) {
/* 4205 */       Component component = getComponent(b);
/* 4206 */       if (component.isLightweight()) {
/* 4207 */         if (component instanceof Container) {
/* 4208 */           ((Container)component).recursiveHideHeavyweightChildren();
/*      */         }
/*      */       }
/* 4211 */       else if (component.isVisible()) {
/* 4212 */         ComponentPeer componentPeer = component.getPeer();
/* 4213 */         if (componentPeer != null) {
/* 4214 */           componentPeer.setVisible(false);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void recursiveRelocateHeavyweightChildren(Point paramPoint) {
/* 4222 */     for (byte b = 0; b < getComponentCount(); b++) {
/* 4223 */       Component component = getComponent(b);
/* 4224 */       if (component.isLightweight()) {
/* 4225 */         if (component instanceof Container && ((Container)component)
/* 4226 */           .hasHeavyweightDescendants()) {
/*      */           
/* 4228 */           Point point = new Point(paramPoint);
/* 4229 */           point.translate(component.getX(), component.getY());
/* 4230 */           ((Container)component).recursiveRelocateHeavyweightChildren(point);
/*      */         } 
/*      */       } else {
/* 4233 */         ComponentPeer componentPeer = component.getPeer();
/* 4234 */         if (componentPeer != null) {
/* 4235 */           componentPeer.setBounds(paramPoint.x + component.getX(), paramPoint.y + component.getY(), component
/* 4236 */               .getWidth(), component.getHeight(), 1);
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
/*      */   final boolean isRecursivelyVisibleUpToHeavyweightContainer() {
/* 4253 */     if (!isLightweight()) {
/* 4254 */       return true;
/*      */     }
/*      */     
/* 4257 */     Container container = this;
/* 4258 */     for (; container != null && container.isLightweight(); 
/* 4259 */       container = container.getContainer()) {
/*      */       
/* 4261 */       if (!container.isVisible()) {
/* 4262 */         return false;
/*      */       }
/*      */     } 
/* 4265 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   void mixOnShowing() {
/* 4270 */     synchronized (getTreeLock()) {
/* 4271 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4272 */         mixingLog.fine("this = " + this);
/*      */       }
/*      */       
/* 4275 */       boolean bool = isLightweight();
/*      */       
/* 4277 */       if (bool && isRecursivelyVisibleUpToHeavyweightContainer()) {
/* 4278 */         recursiveShowHeavyweightChildren();
/*      */       }
/*      */       
/* 4281 */       if (!isMixingNeeded()) {
/*      */         return;
/*      */       }
/*      */       
/* 4285 */       if (!bool || (bool && hasHeavyweightDescendants())) {
/* 4286 */         recursiveApplyCurrentShape();
/*      */       }
/*      */       
/* 4289 */       super.mixOnShowing();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void mixOnHiding(boolean paramBoolean) {
/* 4295 */     synchronized (getTreeLock()) {
/* 4296 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4297 */         mixingLog.fine("this = " + this + "; isLightweight=" + paramBoolean);
/*      */       }
/*      */       
/* 4300 */       if (paramBoolean) {
/* 4301 */         recursiveHideHeavyweightChildren();
/*      */       }
/* 4303 */       super.mixOnHiding(paramBoolean);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void mixOnReshaping() {
/* 4309 */     synchronized (getTreeLock()) {
/* 4310 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4311 */         mixingLog.fine("this = " + this);
/*      */       }
/*      */       
/* 4314 */       boolean bool = isMixingNeeded();
/*      */       
/* 4316 */       if (isLightweight() && hasHeavyweightDescendants()) {
/* 4317 */         Point point = new Point(getX(), getY());
/* 4318 */         Container container = getContainer();
/* 4319 */         for (; container != null && container.isLightweight(); 
/* 4320 */           container = container.getContainer())
/*      */         {
/* 4322 */           point.translate(container.getX(), container.getY());
/*      */         }
/*      */         
/* 4325 */         recursiveRelocateHeavyweightChildren(point);
/*      */         
/* 4327 */         if (!bool) {
/*      */           return;
/*      */         }
/*      */         
/* 4331 */         recursiveApplyCurrentShape();
/*      */       } 
/*      */       
/* 4334 */       if (!bool) {
/*      */         return;
/*      */       }
/*      */       
/* 4338 */       super.mixOnReshaping();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void mixOnZOrderChanging(int paramInt1, int paramInt2) {
/* 4344 */     synchronized (getTreeLock()) {
/* 4345 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4346 */         mixingLog.fine("this = " + this + "; oldZ=" + paramInt1 + "; newZ=" + paramInt2);
/*      */       }
/*      */ 
/*      */       
/* 4350 */       if (!isMixingNeeded()) {
/*      */         return;
/*      */       }
/*      */       
/* 4354 */       boolean bool = (paramInt2 < paramInt1) ? true : false;
/*      */       
/* 4356 */       if (bool && isLightweight() && hasHeavyweightDescendants()) {
/* 4357 */         recursiveApplyCurrentShape();
/*      */       }
/* 4359 */       super.mixOnZOrderChanging(paramInt1, paramInt2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   void mixOnValidating() {
/* 4365 */     synchronized (getTreeLock()) {
/* 4366 */       if (mixingLog.isLoggable(PlatformLogger.Level.FINE)) {
/* 4367 */         mixingLog.fine("this = " + this);
/*      */       }
/*      */       
/* 4370 */       if (!isMixingNeeded()) {
/*      */         return;
/*      */       }
/*      */       
/* 4374 */       if (hasHeavyweightDescendants()) {
/* 4375 */         recursiveApplyCurrentShape();
/*      */       }
/*      */       
/* 4378 */       if (isLightweight() && isNonOpaqueForMixing()) {
/* 4379 */         subtractAndApplyShapeBelowMe();
/*      */       }
/*      */       
/* 4382 */       super.mixOnValidating();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   static interface EventTargetFilter {
/*      */     boolean accept(Component param1Component);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */