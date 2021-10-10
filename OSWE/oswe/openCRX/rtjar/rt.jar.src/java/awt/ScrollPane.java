/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.peer.ScrollPanePeer;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.beans.Transient;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import sun.awt.ScrollPaneWheelScroller;
/*     */ import sun.awt.SunToolkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScrollPane
/*     */   extends Container
/*     */   implements Accessible
/*     */ {
/*     */   public static final int SCROLLBARS_AS_NEEDED = 0;
/*     */   public static final int SCROLLBARS_ALWAYS = 1;
/*     */   public static final int SCROLLBARS_NEVER = 2;
/*     */   private int scrollbarDisplayPolicy;
/*     */   private ScrollPaneAdjustable vAdjustable;
/*     */   private ScrollPaneAdjustable hAdjustable;
/*     */   private static final String base = "scrollpane";
/*     */   
/*     */   static {
/* 102 */     Toolkit.loadLibraries();
/* 103 */     if (!GraphicsEnvironment.isHeadless()) {
/* 104 */       initIDs();
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
/* 162 */   private static int nameCounter = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean defaultWheelScroll = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wheelScrollingEnabled = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7956609840827222915L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollPane() throws HeadlessException {
/* 188 */     this(0);
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
/*     */   @ConstructorProperties({"scrollbarDisplayPolicy"})
/*     */   public ScrollPane(int paramInt) throws HeadlessException {
/* 202 */     GraphicsEnvironment.checkHeadless();
/* 203 */     this.layoutMgr = null;
/* 204 */     this.width = 100;
/* 205 */     this.height = 100;
/* 206 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/* 210 */         this.scrollbarDisplayPolicy = paramInt;
/*     */         break;
/*     */       default:
/* 213 */         throw new IllegalArgumentException("illegal scrollbar display policy");
/*     */     } 
/*     */     
/* 216 */     this.vAdjustable = new ScrollPaneAdjustable(this, new PeerFixer(this), 1);
/*     */     
/* 218 */     this.hAdjustable = new ScrollPaneAdjustable(this, new PeerFixer(this), 0);
/*     */     
/* 220 */     setWheelScrollingEnabled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String constructComponentName() {
/* 228 */     synchronized (ScrollPane.class) {
/* 229 */       return "scrollpane" + nameCounter++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToPanel(Component paramComponent, Object paramObject, int paramInt) {
/* 237 */     Panel panel = new Panel();
/* 238 */     panel.setLayout(new BorderLayout());
/* 239 */     panel.add(paramComponent);
/* 240 */     super.addImpl(panel, paramObject, paramInt);
/* 241 */     validate();
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
/*     */   protected final void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 253 */     synchronized (getTreeLock()) {
/* 254 */       if (getComponentCount() > 0) {
/* 255 */         remove(0);
/*     */       }
/* 257 */       if (paramInt > 0) {
/* 258 */         throw new IllegalArgumentException("position greater than 0");
/*     */       }
/*     */       
/* 261 */       if (!SunToolkit.isLightweightOrUnknown(paramComponent)) {
/* 262 */         super.addImpl(paramComponent, paramObject, paramInt);
/*     */       } else {
/* 264 */         addToPanel(paramComponent, paramObject, paramInt);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScrollbarDisplayPolicy() {
/* 274 */     return this.scrollbarDisplayPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getViewportSize() {
/* 282 */     Insets insets = getInsets();
/* 283 */     return new Dimension(this.width - insets.right - insets.left, this.height - insets.top - insets.bottom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHScrollbarHeight() {
/* 294 */     int i = 0;
/* 295 */     if (this.scrollbarDisplayPolicy != 2) {
/* 296 */       ScrollPanePeer scrollPanePeer = (ScrollPanePeer)this.peer;
/* 297 */       if (scrollPanePeer != null) {
/* 298 */         i = scrollPanePeer.getHScrollbarHeight();
/*     */       }
/*     */     } 
/* 301 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVScrollbarWidth() {
/* 311 */     int i = 0;
/* 312 */     if (this.scrollbarDisplayPolicy != 2) {
/* 313 */       ScrollPanePeer scrollPanePeer = (ScrollPanePeer)this.peer;
/* 314 */       if (scrollPanePeer != null) {
/* 315 */         i = scrollPanePeer.getVScrollbarWidth();
/*     */       }
/*     */     } 
/* 318 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Adjustable getVAdjustable() {
/* 329 */     return this.vAdjustable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Adjustable getHAdjustable() {
/* 340 */     return this.hAdjustable;
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
/*     */   public void setScrollPosition(int paramInt1, int paramInt2) {
/* 359 */     synchronized (getTreeLock()) {
/* 360 */       if (getComponentCount() == 0) {
/* 361 */         throw new NullPointerException("child is null");
/*     */       }
/* 363 */       this.hAdjustable.setValue(paramInt1);
/* 364 */       this.vAdjustable.setValue(paramInt2);
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
/*     */   public void setScrollPosition(Point paramPoint) {
/* 383 */     setScrollPosition(paramPoint.x, paramPoint.y);
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
/*     */   @Transient
/*     */   public Point getScrollPosition() {
/* 397 */     synchronized (getTreeLock()) {
/* 398 */       if (getComponentCount() == 0) {
/* 399 */         throw new NullPointerException("child is null");
/*     */       }
/* 401 */       return new Point(this.hAdjustable.getValue(), this.vAdjustable.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setLayout(LayoutManager paramLayoutManager) {
/* 411 */     throw new AWTError("ScrollPane controls layout");
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
/*     */   public void doLayout() {
/* 423 */     layout();
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
/*     */   Dimension calculateChildSize() {
/*     */     boolean bool1, bool2;
/* 439 */     Dimension dimension1 = getSize();
/* 440 */     Insets insets = getInsets();
/* 441 */     int i = dimension1.width - insets.left * 2;
/* 442 */     int j = dimension1.height - insets.top * 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 449 */     Component component = getComponent(0);
/* 450 */     Dimension dimension2 = new Dimension(component.getPreferredSize());
/*     */     
/* 452 */     if (this.scrollbarDisplayPolicy == 0) {
/* 453 */       bool1 = (dimension2.height > j) ? true : false;
/* 454 */       bool2 = (dimension2.width > i) ? true : false;
/* 455 */     } else if (this.scrollbarDisplayPolicy == 1) {
/* 456 */       bool1 = bool2 = true;
/*     */     } else {
/* 458 */       bool1 = bool2 = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 464 */     int k = getVScrollbarWidth();
/* 465 */     int m = getHScrollbarHeight();
/* 466 */     if (bool1) {
/* 467 */       i -= k;
/*     */     }
/* 469 */     if (bool2) {
/* 470 */       j -= m;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     if (dimension2.width < i) {
/* 477 */       dimension2.width = i;
/*     */     }
/* 479 */     if (dimension2.height < j) {
/* 480 */       dimension2.height = j;
/*     */     }
/*     */     
/* 483 */     return dimension2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void layout() {
/* 492 */     if (getComponentCount() == 0) {
/*     */       return;
/*     */     }
/* 495 */     Component component = getComponent(0);
/* 496 */     Point point = getScrollPosition();
/* 497 */     Dimension dimension1 = calculateChildSize();
/* 498 */     Dimension dimension2 = getViewportSize();
/*     */     
/* 500 */     component.reshape(-point.x, -point.y, dimension1.width, dimension1.height);
/* 501 */     ScrollPanePeer scrollPanePeer = (ScrollPanePeer)this.peer;
/* 502 */     if (scrollPanePeer != null) {
/* 503 */       scrollPanePeer.childResized(dimension1.width, dimension1.height);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 509 */     dimension2 = getViewportSize();
/* 510 */     this.hAdjustable.setSpan(0, dimension1.width, dimension2.width);
/* 511 */     this.vAdjustable.setSpan(0, dimension1.height, dimension2.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void printComponents(Graphics paramGraphics) {
/* 521 */     if (getComponentCount() == 0) {
/*     */       return;
/*     */     }
/* 524 */     Component component = getComponent(0);
/* 525 */     Point point = component.getLocation();
/* 526 */     Dimension dimension = getViewportSize();
/* 527 */     Insets insets = getInsets();
/*     */     
/* 529 */     Graphics graphics = paramGraphics.create();
/*     */     try {
/* 531 */       graphics.clipRect(insets.left, insets.top, dimension.width, dimension.height);
/* 532 */       graphics.translate(point.x, point.y);
/* 533 */       component.printAll(graphics);
/*     */     } finally {
/* 535 */       graphics.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotify() {
/* 543 */     synchronized (getTreeLock()) {
/*     */       
/* 545 */       int i = 0;
/* 546 */       int j = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 552 */       if (getComponentCount() > 0) {
/* 553 */         i = this.vAdjustable.getValue();
/* 554 */         j = this.hAdjustable.getValue();
/* 555 */         this.vAdjustable.setValue(0);
/* 556 */         this.hAdjustable.setValue(0);
/*     */       } 
/*     */       
/* 559 */       if (this.peer == null)
/* 560 */         this.peer = getToolkit().createScrollPane(this); 
/* 561 */       super.addNotify();
/*     */ 
/*     */       
/* 564 */       if (getComponentCount() > 0) {
/* 565 */         this.vAdjustable.setValue(i);
/* 566 */         this.hAdjustable.setValue(j);
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
/*     */   public String paramString() {
/*     */     String str;
/* 583 */     switch (this.scrollbarDisplayPolicy) {
/*     */       case 0:
/* 585 */         str = "as-needed";
/*     */         break;
/*     */       case 1:
/* 588 */         str = "always";
/*     */         break;
/*     */       case 2:
/* 591 */         str = "never";
/*     */         break;
/*     */       default:
/* 594 */         str = "invalid display policy"; break;
/*     */     } 
/* 596 */     Point point = (getComponentCount() > 0) ? getScrollPosition() : new Point(0, 0);
/* 597 */     Insets insets = getInsets();
/* 598 */     return super.paramString() + ",ScrollPosition=(" + point.x + "," + point.y + "),Insets=(" + insets.top + "," + insets.left + "," + insets.bottom + "," + insets.right + "),ScrollbarDisplayPolicy=" + str + ",wheelScrollingEnabled=" + 
/*     */ 
/*     */       
/* 601 */       isWheelScrollingEnabled();
/*     */   }
/*     */   
/*     */   void autoProcessMouseWheel(MouseWheelEvent paramMouseWheelEvent) {
/* 605 */     processMouseWheelEvent(paramMouseWheelEvent);
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
/*     */   protected void processMouseWheelEvent(MouseWheelEvent paramMouseWheelEvent) {
/* 619 */     if (isWheelScrollingEnabled()) {
/* 620 */       ScrollPaneWheelScroller.handleWheelScrolling(this, paramMouseWheelEvent);
/* 621 */       paramMouseWheelEvent.consume();
/*     */     } 
/* 623 */     super.processMouseWheelEvent(paramMouseWheelEvent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean eventTypeEnabled(int paramInt) {
/* 631 */     if (paramInt == 507 && isWheelScrollingEnabled()) {
/* 632 */       return true;
/*     */     }
/*     */     
/* 635 */     return super.eventTypeEnabled(paramInt);
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
/*     */   public void setWheelScrollingEnabled(boolean paramBoolean) {
/* 652 */     this.wheelScrollingEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWheelScrollingEnabled() {
/* 663 */     return this.wheelScrollingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 674 */     paramObjectOutputStream.defaultWriteObject();
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws ClassNotFoundException, IOException, HeadlessException {
/* 687 */     GraphicsEnvironment.checkHeadless();
/*     */ 
/*     */     
/* 690 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */     
/* 693 */     this.scrollbarDisplayPolicy = getField.get("scrollbarDisplayPolicy", 0);
/*     */     
/* 695 */     this.hAdjustable = (ScrollPaneAdjustable)getField.get("hAdjustable", (Object)null);
/* 696 */     this.vAdjustable = (ScrollPaneAdjustable)getField.get("vAdjustable", (Object)null);
/*     */ 
/*     */     
/* 699 */     this.wheelScrollingEnabled = getField.get("wheelScrollingEnabled", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class PeerFixer
/*     */     implements AdjustmentListener, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1043664721353696630L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ScrollPane scroller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PeerFixer(ScrollPane param1ScrollPane1) {
/* 721 */       this.scroller = param1ScrollPane1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void adjustmentValueChanged(AdjustmentEvent param1AdjustmentEvent) {
/* 728 */       Adjustable adjustable = param1AdjustmentEvent.getAdjustable();
/* 729 */       int i = param1AdjustmentEvent.getValue();
/* 730 */       ScrollPanePeer scrollPanePeer = (ScrollPanePeer)this.scroller.peer;
/* 731 */       if (scrollPanePeer != null) {
/* 732 */         scrollPanePeer.setValue(adjustable, i);
/*     */       }
/*     */       
/* 735 */       Component component = this.scroller.getComponent(0);
/* 736 */       switch (adjustable.getOrientation()) {
/*     */         case 1:
/* 738 */           component.move((component.getLocation()).x, -i);
/*     */           return;
/*     */         case 0:
/* 741 */           component.move(-i, (component.getLocation()).y);
/*     */           return;
/*     */       } 
/* 744 */       throw new IllegalArgumentException("Illegal adjustable orientation");
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 767 */     if (this.accessibleContext == null) {
/* 768 */       this.accessibleContext = new AccessibleAWTScrollPane();
/*     */     }
/* 770 */     return this.accessibleContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native void initIDs();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class AccessibleAWTScrollPane
/*     */     extends Container.AccessibleAWTContainer
/*     */   {
/*     */     private static final long serialVersionUID = 6100703663886637L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 795 */       return AccessibleRole.SCROLL_PANE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ScrollPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */