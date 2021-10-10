/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.FlavorMap;
/*     */ import java.awt.datatransfer.SystemFlavorMap;
/*     */ import java.awt.dnd.peer.DropTargetPeer;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.peer.ComponentPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.TooManyListenersException;
/*     */ import javax.swing.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DropTarget
/*     */   implements DropTargetListener, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6283860791671019047L;
/*     */   
/*     */   public DropTarget(Component paramComponent, int paramInt, DropTargetListener paramDropTargetListener, boolean paramBoolean, FlavorMap paramFlavorMap) throws HeadlessException {
/*  94 */     if (GraphicsEnvironment.isHeadless()) {
/*  95 */       throw new HeadlessException();
/*     */     }
/*     */     
/*  98 */     this.component = paramComponent;
/*     */     
/* 100 */     setDefaultActions(paramInt);
/*     */     
/* 102 */     if (paramDropTargetListener != null) {
/* 103 */       try { addDropTargetListener(paramDropTargetListener); }
/* 104 */       catch (TooManyListenersException tooManyListenersException) {}
/*     */     }
/*     */ 
/*     */     
/* 108 */     if (paramComponent != null) {
/* 109 */       paramComponent.setDropTarget(this);
/* 110 */       setActive(paramBoolean);
/*     */     } 
/*     */     
/* 113 */     if (paramFlavorMap != null) {
/* 114 */       this.flavorMap = paramFlavorMap;
/*     */     } else {
/* 116 */       this.flavorMap = SystemFlavorMap.getDefaultFlavorMap();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTarget(Component paramComponent, int paramInt, DropTargetListener paramDropTargetListener, boolean paramBoolean) throws HeadlessException {
/* 141 */     this(paramComponent, paramInt, paramDropTargetListener, paramBoolean, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTarget() throws HeadlessException {
/* 151 */     this(null, 3, null, true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTarget(Component paramComponent, DropTargetListener paramDropTargetListener) throws HeadlessException {
/* 169 */     this(paramComponent, 3, paramDropTargetListener, true, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTarget(Component paramComponent, int paramInt, DropTargetListener paramDropTargetListener) throws HeadlessException {
/* 189 */     this(paramComponent, paramInt, paramDropTargetListener, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setComponent(Component paramComponent) {
/* 204 */     if (this.component == paramComponent || (this.component != null && this.component.equals(paramComponent))) {
/*     */       return;
/*     */     }
/*     */     
/* 208 */     ComponentPeer componentPeer = null;
/*     */     Component component;
/* 210 */     if ((component = this.component) != null) {
/* 211 */       clearAutoscroll();
/*     */       
/* 213 */       this.component = null;
/*     */       
/* 215 */       if (this.componentPeer != null) {
/* 216 */         componentPeer = this.componentPeer;
/* 217 */         removeNotify(this.componentPeer);
/*     */       } 
/*     */       
/* 220 */       component.setDropTarget((DropTarget)null);
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if ((this.component = paramComponent) != null) {
/* 225 */       try { paramComponent.setDropTarget(this); }
/* 226 */       catch (Exception exception)
/* 227 */       { if (component != null) {
/* 228 */           component.setDropTarget(this);
/* 229 */           addNotify(componentPeer);
/*     */         }  }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Component getComponent() {
/* 242 */     return this.component;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDefaultActions(int paramInt) {
/* 253 */     getDropTargetContext().setTargetActions(paramInt & 0x40000003);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doSetDefaultActions(int paramInt) {
/* 261 */     this.actions = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultActions() {
/* 272 */     return this.actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setActive(boolean paramBoolean) {
/* 283 */     if (paramBoolean != this.active) {
/* 284 */       this.active = paramBoolean;
/*     */     }
/*     */     
/* 287 */     if (!this.active) clearAutoscroll();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 299 */     return this.active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addDropTargetListener(DropTargetListener paramDropTargetListener) throws TooManyListenersException {
/* 313 */     if (paramDropTargetListener == null)
/*     */       return; 
/* 315 */     if (equals(paramDropTargetListener)) throw new IllegalArgumentException("DropTarget may not be its own Listener");
/*     */     
/* 317 */     if (this.dtListener == null) {
/* 318 */       this.dtListener = paramDropTargetListener;
/*     */     } else {
/* 320 */       throw new TooManyListenersException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeDropTargetListener(DropTargetListener paramDropTargetListener) {
/* 330 */     if (paramDropTargetListener != null && this.dtListener != null) {
/* 331 */       if (this.dtListener.equals(paramDropTargetListener)) {
/* 332 */         this.dtListener = null;
/*     */       } else {
/* 334 */         throw new IllegalArgumentException("listener mismatch");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void dragEnter(DropTargetDragEvent paramDropTargetDragEvent) {
/* 353 */     this.isDraggingInside = true;
/*     */     
/* 355 */     if (!this.active)
/*     */       return; 
/* 357 */     if (this.dtListener != null) {
/* 358 */       this.dtListener.dragEnter(paramDropTargetDragEvent);
/*     */     } else {
/* 360 */       paramDropTargetDragEvent.getDropTargetContext().setTargetActions(0);
/*     */     } 
/* 362 */     initializeAutoscrolling(paramDropTargetDragEvent.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void dragOver(DropTargetDragEvent paramDropTargetDragEvent) {
/* 380 */     if (!this.active)
/*     */       return; 
/* 382 */     if (this.dtListener != null && this.active) this.dtListener.dragOver(paramDropTargetDragEvent);
/*     */     
/* 384 */     updateAutoscroll(paramDropTargetDragEvent.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void dropActionChanged(DropTargetDragEvent paramDropTargetDragEvent) {
/* 402 */     if (!this.active)
/*     */       return; 
/* 404 */     if (this.dtListener != null) this.dtListener.dropActionChanged(paramDropTargetDragEvent);
/*     */     
/* 406 */     updateAutoscroll(paramDropTargetDragEvent.getLocation());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void dragExit(DropTargetEvent paramDropTargetEvent) {
/* 425 */     this.isDraggingInside = false;
/*     */     
/* 427 */     if (!this.active)
/*     */       return; 
/* 429 */     if (this.dtListener != null && this.active) this.dtListener.dragExit(paramDropTargetEvent);
/*     */     
/* 431 */     clearAutoscroll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void drop(DropTargetDropEvent paramDropTargetDropEvent) {
/* 450 */     this.isDraggingInside = false;
/*     */     
/* 452 */     clearAutoscroll();
/*     */     
/* 454 */     if (this.dtListener != null && this.active) {
/* 455 */       this.dtListener.drop(paramDropTargetDropEvent);
/*     */     } else {
/* 457 */       paramDropTargetDropEvent.rejectDrop();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FlavorMap getFlavorMap() {
/* 471 */     return this.flavorMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlavorMap(FlavorMap paramFlavorMap) {
/* 482 */     this.flavorMap = (paramFlavorMap == null) ? SystemFlavorMap.getDefaultFlavorMap() : paramFlavorMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify(ComponentPeer paramComponentPeer) {
/* 503 */     if (paramComponentPeer == this.componentPeer)
/*     */       return; 
/* 505 */     this.componentPeer = paramComponentPeer;
/*     */     
/* 507 */     Component component = this.component;
/* 508 */     for (; component != null && paramComponentPeer instanceof java.awt.peer.LightweightPeer; component = component.getParent()) {
/* 509 */       paramComponentPeer = component.getPeer();
/*     */     }
/*     */     
/* 512 */     if (paramComponentPeer instanceof DropTargetPeer) {
/* 513 */       this.nativePeer = paramComponentPeer;
/* 514 */       ((DropTargetPeer)paramComponentPeer).addDropTarget(this);
/*     */     } else {
/* 516 */       this.nativePeer = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify(ComponentPeer paramComponentPeer) {
/* 537 */     if (this.nativePeer != null) {
/* 538 */       ((DropTargetPeer)this.nativePeer).removeDropTarget(this);
/*     */     }
/* 540 */     this.componentPeer = this.nativePeer = null;
/*     */     
/* 542 */     synchronized (this) {
/* 543 */       if (this.isDraggingInside) {
/* 544 */         dragExit(new DropTargetEvent(getDropTargetContext()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTargetContext getDropTargetContext() {
/* 557 */     return this.dropTargetContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DropTargetContext createDropTargetContext() {
/* 572 */     return new DropTargetContext(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 587 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 589 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.dtListener) ? this.dtListener : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException {
/* 608 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */     
/*     */     try {
/* 611 */       this
/* 612 */         .dropTargetContext = (DropTargetContext)getField.get("dropTargetContext", (Object)null);
/* 613 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*     */ 
/*     */     
/* 616 */     if (this.dropTargetContext == null) {
/* 617 */       this.dropTargetContext = createDropTargetContext();
/*     */     }
/*     */     
/* 620 */     this.component = (Component)getField.get("component", (Object)null);
/* 621 */     this.actions = getField.get("actions", 3);
/* 622 */     this.active = getField.get("active", true);
/*     */ 
/*     */     
/*     */     try {
/* 626 */       this.dtListener = (DropTargetListener)getField.get("dtListener", (Object)null);
/* 627 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */       
/* 629 */       this.dtListener = (DropTargetListener)paramObjectInputStream.readObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class DropTargetAutoScroller
/*     */     implements ActionListener
/*     */   {
/*     */     private Component component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Autoscroll autoScroll;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Timer timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Point locn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Point prev;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Rectangle outer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Rectangle inner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int hysteresis;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updateRegion() {
/*     */       Insets insets = this.autoScroll.getAutoscrollInsets();
/*     */       Dimension dimension = this.component.getSize();
/*     */       if (dimension.width != this.outer.width || dimension.height != this.outer.height) {
/*     */         this.outer.reshape(0, 0, dimension.width, dimension.height);
/*     */       }
/*     */       if (this.inner.x != insets.left || this.inner.y != insets.top) {
/*     */         this.inner.setLocation(insets.left, insets.top);
/*     */       }
/*     */       int i = dimension.width - insets.left + insets.right;
/*     */       int j = dimension.height - insets.top + insets.bottom;
/*     */       if (i != this.inner.width || j != this.inner.height) {
/*     */         this.inner.setSize(i, j);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected synchronized void updateLocation(Point param1Point) {
/*     */       this.prev = this.locn;
/*     */       this.locn = param1Point;
/*     */       if (Math.abs(this.locn.x - this.prev.x) > this.hysteresis || Math.abs(this.locn.y - this.prev.y) > this.hysteresis) {
/*     */         if (this.timer.isRunning()) {
/*     */           this.timer.stop();
/*     */         }
/*     */       } else if (!this.timer.isRunning()) {
/*     */         this.timer.start();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void stop() {
/*     */       this.timer.stop();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void actionPerformed(ActionEvent param1ActionEvent) {
/*     */       updateRegion();
/*     */       if (this.outer.contains(this.locn) && !this.inner.contains(this.locn)) {
/*     */         this.autoScroll.autoscroll(this.locn);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected DropTargetAutoScroller(Component param1Component, Point param1Point) {
/* 759 */       this.outer = new Rectangle();
/* 760 */       this.inner = new Rectangle();
/*     */       
/* 762 */       this.hysteresis = 10; this.component = param1Component; this.autoScroll = (Autoscroll)this.component; Toolkit toolkit = Toolkit.getDefaultToolkit(); Integer integer1 = Integer.valueOf(100); Integer integer2 = Integer.valueOf(100); try {
/*     */         integer1 = (Integer)toolkit.getDesktopProperty("DnD.Autoscroll.initialDelay");
/*     */       } catch (Exception exception) {} try {
/*     */         integer2 = (Integer)toolkit.getDesktopProperty("DnD.Autoscroll.interval");
/*     */       } catch (Exception exception) {}
/*     */       this.timer = new Timer(integer2.intValue(), this);
/*     */       this.timer.setCoalesce(true);
/*     */       this.timer.setInitialDelay(integer1.intValue());
/*     */       this.locn = param1Point;
/*     */       this.prev = param1Point;
/*     */       try {
/*     */         this.hysteresis = ((Integer)toolkit.getDesktopProperty("DnD.Autoscroll.cursorHysteresis")).intValue();
/*     */       } catch (Exception exception) {}
/* 775 */       this.timer.start(); } } protected DropTargetAutoScroller createDropTargetAutoScroller(Component paramComponent, Point paramPoint) { return new DropTargetAutoScroller(paramComponent, paramPoint); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initializeAutoscrolling(Point paramPoint) {
/* 785 */     if (this.component == null || !(this.component instanceof Autoscroll))
/*     */       return; 
/* 787 */     this.autoScroller = createDropTargetAutoScroller(this.component, paramPoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateAutoscroll(Point paramPoint) {
/* 797 */     if (this.autoScroller != null) this.autoScroller.updateLocation(paramPoint);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearAutoscroll() {
/* 805 */     if (this.autoScroller != null) {
/* 806 */       this.autoScroller.stop();
/* 807 */       this.autoScroller = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 816 */   private DropTargetContext dropTargetContext = createDropTargetContext();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Component component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient ComponentPeer componentPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient ComponentPeer nativePeer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 843 */   int actions = 3;
/*     */   boolean active = true;
/*     */   private transient DropTargetAutoScroller autoScroller;
/*     */   private transient DropTargetListener dtListener;
/*     */   private transient FlavorMap flavorMap;
/*     */   private transient boolean isDraggingInside;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DropTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */