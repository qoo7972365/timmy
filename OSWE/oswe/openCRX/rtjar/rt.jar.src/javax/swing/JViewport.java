/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Component;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.ComponentListener;
/*      */ import java.awt.peer.ComponentPeer;
/*      */ import java.beans.Transient;
/*      */ import java.io.Serializable;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.plaf.ViewportUI;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JViewport
/*      */   extends JComponent
/*      */   implements Accessible
/*      */ {
/*      */   private static final String uiClassID = "ViewportUI";
/*  111 */   static final Object EnableWindowBlit = "EnableWindowBlit";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isViewSizeSet = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   protected Point lastPaintPosition = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected boolean backingStore = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   protected transient Image backingStoreImage = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean scrollUnderway = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  161 */   private ComponentListener viewListener = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  168 */   private transient ChangeEvent changeEvent = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int BLIT_SCROLL_MODE = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int BACKINGSTORE_SCROLL_MODE = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int SIMPLE_SCROLL_MODE = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  206 */   private int scrollMode = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean repaintAll;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean waitingForRepaint;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient Timer repaintTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private transient boolean inBlitPaint;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasHadValidView;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean viewChanged;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JViewport() {
/*  275 */     setLayout(createLayoutManager());
/*  276 */     setOpaque(true);
/*  277 */     updateUI();
/*  278 */     setInheritsPopupMenu(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ViewportUI getUI() {
/*  290 */     return (ViewportUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUI(ViewportUI paramViewportUI) {
/*  307 */     setUI(paramViewportUI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  317 */     setUI((ViewportUI)UIManager.getUI(this));
/*      */   }
/*      */ 
/*      */ 
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
/*  331 */     return "ViewportUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  348 */     setView(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove(Component paramComponent) {
/*  358 */     paramComponent.removeComponentListener(this.viewListener);
/*  359 */     super.remove(paramComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  384 */     Component component = getView();
/*      */     
/*  386 */     if (component == null) {
/*      */       return;
/*      */     }
/*  389 */     if (!component.isValid())
/*      */     {
/*      */ 
/*      */       
/*  393 */       validateView();
/*      */     }
/*      */ 
/*      */     
/*  397 */     int i = positionAdjustment(getWidth(), paramRectangle.width, paramRectangle.x);
/*  398 */     int j = positionAdjustment(getHeight(), paramRectangle.height, paramRectangle.y);
/*      */     
/*  400 */     if (i != 0 || j != 0) {
/*  401 */       Point point = getViewPosition();
/*  402 */       Dimension dimension1 = component.getSize();
/*  403 */       int k = point.x;
/*  404 */       int m = point.y;
/*  405 */       Dimension dimension2 = getExtentSize();
/*      */       
/*  407 */       point.x -= i;
/*  408 */       point.y -= j;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  413 */       if (component.isValid()) {
/*  414 */         if (getParent().getComponentOrientation().isLeftToRight()) {
/*  415 */           if (point.x + dimension2.width > dimension1.width) {
/*  416 */             point.x = Math.max(0, dimension1.width - dimension2.width);
/*  417 */           } else if (point.x < 0) {
/*  418 */             point.x = 0;
/*      */           }
/*      */         
/*  421 */         } else if (dimension2.width > dimension1.width) {
/*  422 */           point.x = dimension1.width - dimension2.width;
/*      */         } else {
/*  424 */           point.x = Math.max(0, Math.min(dimension1.width - dimension2.width, point.x));
/*      */         } 
/*      */         
/*  427 */         if (point.y + dimension2.height > dimension1.height) {
/*  428 */           point.y = Math.max(0, dimension1.height - dimension2.height);
/*      */         
/*      */         }
/*  431 */         else if (point.y < 0) {
/*  432 */           point.y = 0;
/*      */         } 
/*      */       } 
/*  435 */       if (point.x != k || point.y != m) {
/*  436 */         setViewPosition(point);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  458 */         this.scrollUnderway = false;
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
/*      */   private void validateView() {
/*  475 */     Container container = SwingUtilities.getValidateRoot(this, false);
/*      */     
/*  477 */     if (container == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  482 */     container.validate();
/*      */ 
/*      */ 
/*      */     
/*  486 */     RepaintManager repaintManager = RepaintManager.currentManager(this);
/*      */     
/*  488 */     if (repaintManager != null) {
/*  489 */       repaintManager.removeInvalidComponent((JComponent)container);
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
/*      */   private int positionAdjustment(int paramInt1, int paramInt2, int paramInt3) {
/*  503 */     if (paramInt3 >= 0 && paramInt2 + paramInt3 <= paramInt1) {
/*  504 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  510 */     if (paramInt3 <= 0 && paramInt2 + paramInt3 >= paramInt1) {
/*  511 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  517 */     if (paramInt3 > 0 && paramInt2 <= paramInt1) {
/*  518 */       return -paramInt3 + paramInt1 - paramInt2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  524 */     if (paramInt3 >= 0 && paramInt2 >= paramInt1) {
/*  525 */       return -paramInt3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  531 */     if (paramInt3 <= 0 && paramInt2 <= paramInt1) {
/*  532 */       return -paramInt3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  538 */     if (paramInt3 < 0 && paramInt2 >= paramInt1) {
/*  539 */       return -paramInt3 + paramInt1 - paramInt2;
/*      */     }
/*      */     
/*  542 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setBorder(Border paramBorder) {
/*  562 */     if (paramBorder != null) {
/*  563 */       throw new IllegalArgumentException("JViewport.setBorder() not supported");
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
/*      */   public final Insets getInsets() {
/*  576 */     return new Insets(0, 0, 0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Insets getInsets(Insets paramInsets) {
/*  592 */     paramInsets.left = paramInsets.top = paramInsets.right = paramInsets.bottom = 0;
/*  593 */     return paramInsets;
/*      */   }
/*      */ 
/*      */   
/*      */   private Graphics getBackingStoreGraphics(Graphics paramGraphics) {
/*  598 */     Graphics graphics = this.backingStoreImage.getGraphics();
/*  599 */     graphics.setColor(paramGraphics.getColor());
/*  600 */     graphics.setFont(paramGraphics.getFont());
/*  601 */     graphics.setClip(paramGraphics.getClipBounds());
/*  602 */     return graphics;
/*      */   }
/*      */ 
/*      */   
/*      */   private void paintViaBackingStore(Graphics paramGraphics) {
/*  607 */     Graphics graphics = getBackingStoreGraphics(paramGraphics);
/*      */     try {
/*  609 */       super.paint(graphics);
/*  610 */       paramGraphics.drawImage(this.backingStoreImage, 0, 0, this);
/*      */     } finally {
/*  612 */       graphics.dispose();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void paintViaBackingStore(Graphics paramGraphics, Rectangle paramRectangle) {
/*  617 */     Graphics graphics = getBackingStoreGraphics(paramGraphics);
/*      */     try {
/*  619 */       super.paint(graphics);
/*  620 */       paramGraphics.setClip(paramRectangle);
/*  621 */       paramGraphics.drawImage(this.backingStoreImage, 0, 0, this);
/*      */     } finally {
/*  623 */       graphics.dispose();
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
/*      */   public boolean isOptimizedDrawingEnabled() {
/*  639 */     return false;
/*      */   }
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
/*  651 */     return (this.scrollMode == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Point getViewLocation() {
/*  659 */     Component component = getView();
/*  660 */     if (component != null) {
/*  661 */       return component.getLocation();
/*      */     }
/*      */     
/*  664 */     return new Point(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  683 */     int i = getWidth();
/*  684 */     int j = getHeight();
/*      */     
/*  686 */     if (i <= 0 || j <= 0) {
/*      */       return;
/*      */     }
/*      */     
/*  690 */     if (this.inBlitPaint) {
/*      */       
/*  692 */       super.paint(paramGraphics);
/*      */       
/*      */       return;
/*      */     } 
/*  696 */     if (this.repaintAll) {
/*  697 */       this.repaintAll = false;
/*  698 */       Rectangle rectangle1 = paramGraphics.getClipBounds();
/*  699 */       if (rectangle1.width < getWidth() || rectangle1.height < 
/*  700 */         getHeight()) {
/*  701 */         this.waitingForRepaint = true;
/*  702 */         if (this.repaintTimer == null) {
/*  703 */           this.repaintTimer = createRepaintTimer();
/*      */         }
/*  705 */         this.repaintTimer.stop();
/*  706 */         this.repaintTimer.start();
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  711 */         if (this.repaintTimer != null) {
/*  712 */           this.repaintTimer.stop();
/*      */         }
/*  714 */         this.waitingForRepaint = false;
/*      */       }
/*      */     
/*  717 */     } else if (this.waitingForRepaint) {
/*      */       
/*  719 */       Rectangle rectangle1 = paramGraphics.getClipBounds();
/*  720 */       if (rectangle1.width >= getWidth() && rectangle1.height >= 
/*  721 */         getHeight()) {
/*  722 */         this.waitingForRepaint = false;
/*  723 */         this.repaintTimer.stop();
/*      */       } 
/*      */     } 
/*      */     
/*  727 */     if (!this.backingStore || isBlitting() || getView() == null) {
/*  728 */       super.paint(paramGraphics);
/*  729 */       this.lastPaintPosition = getViewLocation();
/*      */ 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/*  737 */     Rectangle rectangle = getView().getBounds();
/*  738 */     if (!isOpaque()) {
/*  739 */       paramGraphics.clipRect(0, 0, rectangle.width, rectangle.height);
/*      */     }
/*      */     
/*  742 */     if (this.backingStoreImage == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  750 */       this.backingStoreImage = createImage(i, j);
/*  751 */       Rectangle rectangle1 = paramGraphics.getClipBounds();
/*  752 */       if (rectangle1.width != i || rectangle1.height != j) {
/*  753 */         if (!isOpaque()) {
/*  754 */           paramGraphics.setClip(0, 0, Math.min(rectangle.width, i), 
/*  755 */               Math.min(rectangle.height, j));
/*      */         } else {
/*      */           
/*  758 */           paramGraphics.setClip(0, 0, i, j);
/*      */         } 
/*  760 */         paintViaBackingStore(paramGraphics, rectangle1);
/*      */       } else {
/*      */         
/*  763 */         paintViaBackingStore(paramGraphics);
/*      */       }
/*      */     
/*      */     }
/*  767 */     else if (!this.scrollUnderway || this.lastPaintPosition.equals(getViewLocation())) {
/*      */       
/*  769 */       paintViaBackingStore(paramGraphics);
/*      */     } else {
/*      */       
/*  772 */       Point point1 = new Point();
/*  773 */       Point point2 = new Point();
/*  774 */       Dimension dimension = new Dimension();
/*  775 */       Rectangle rectangle1 = new Rectangle();
/*      */       
/*  777 */       Point point3 = getViewLocation();
/*  778 */       int k = point3.x - this.lastPaintPosition.x;
/*  779 */       int m = point3.y - this.lastPaintPosition.y;
/*  780 */       boolean bool = computeBlit(k, m, point1, point2, dimension, rectangle1);
/*  781 */       if (!bool) {
/*      */ 
/*      */         
/*  784 */         paintViaBackingStore(paramGraphics);
/*      */       } else {
/*  786 */         int n = point2.x - point1.x;
/*  787 */         int i1 = point2.y - point1.y;
/*      */ 
/*      */         
/*  790 */         Rectangle rectangle2 = paramGraphics.getClipBounds();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  795 */         paramGraphics.setClip(0, 0, i, j);
/*  796 */         Graphics graphics = getBackingStoreGraphics(paramGraphics);
/*      */         try {
/*  798 */           graphics.copyArea(point1.x, point1.y, dimension.width, dimension.height, n, i1);
/*      */           
/*  800 */           paramGraphics.setClip(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);
/*      */           
/*  802 */           Rectangle rectangle3 = rectangle.intersection(rectangle1);
/*  803 */           graphics.setClip(rectangle3);
/*  804 */           super.paint(graphics);
/*      */ 
/*      */           
/*  807 */           paramGraphics.drawImage(this.backingStoreImage, 0, 0, this);
/*      */         } finally {
/*  809 */           graphics.dispose();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  814 */     this.lastPaintPosition = getViewLocation();
/*  815 */     this.scrollUnderway = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reshape(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  831 */     boolean bool = (getWidth() != paramInt3 || getHeight() != paramInt4) ? true : false;
/*  832 */     if (bool) {
/*  833 */       this.backingStoreImage = null;
/*      */     }
/*  835 */     super.reshape(paramInt1, paramInt2, paramInt3, paramInt4);
/*  836 */     if (bool || this.viewChanged) {
/*  837 */       this.viewChanged = false;
/*      */       
/*  839 */       fireStateChanged();
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
/*      */   public void setScrollMode(int paramInt) {
/*  870 */     this.scrollMode = paramInt;
/*  871 */     this.backingStore = (paramInt == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getScrollMode() {
/*  882 */     return this.scrollMode;
/*      */   }
/*      */ 
/*      */ 
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
/*      */   public boolean isBackingStoreEnabled() {
/*  897 */     return (this.scrollMode == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public void setBackingStoreEnabled(boolean paramBoolean) {
/*  915 */     if (paramBoolean) {
/*  916 */       setScrollMode(2);
/*      */     } else {
/*  918 */       setScrollMode(1);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isBlitting() {
/*  923 */     Component component = getView();
/*  924 */     return (this.scrollMode == 1 && component instanceof JComponent && component
/*  925 */       .isOpaque());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getView() {
/*  937 */     return (getComponentCount() > 0) ? getComponent(0) : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setView(Component paramComponent) {
/*  954 */     int i = getComponentCount();
/*  955 */     for (int j = i - 1; j >= 0; j--) {
/*  956 */       remove(getComponent(j));
/*      */     }
/*      */     
/*  959 */     this.isViewSizeSet = false;
/*      */     
/*  961 */     if (paramComponent != null) {
/*  962 */       super.addImpl(paramComponent, (Object)null, -1);
/*  963 */       this.viewListener = createViewListener();
/*  964 */       paramComponent.addComponentListener(this.viewListener);
/*      */     } 
/*      */     
/*  967 */     if (this.hasHadValidView) {
/*      */       
/*  969 */       fireStateChanged();
/*      */     }
/*  971 */     else if (paramComponent != null) {
/*  972 */       this.hasHadValidView = true;
/*      */     } 
/*      */     
/*  975 */     this.viewChanged = true;
/*      */     
/*  977 */     revalidate();
/*  978 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension getViewSize() {
/*  990 */     Component component = getView();
/*      */     
/*  992 */     if (component == null) {
/*  993 */       return new Dimension(0, 0);
/*      */     }
/*  995 */     if (this.isViewSizeSet) {
/*  996 */       return component.getSize();
/*      */     }
/*      */     
/*  999 */     return component.getPreferredSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setViewSize(Dimension paramDimension) {
/* 1011 */     Component component = getView();
/* 1012 */     if (component != null) {
/* 1013 */       Dimension dimension = component.getSize();
/* 1014 */       if (!paramDimension.equals(dimension)) {
/*      */ 
/*      */ 
/*      */         
/* 1018 */         this.scrollUnderway = false;
/* 1019 */         component.setSize(paramDimension);
/* 1020 */         this.isViewSizeSet = true;
/* 1021 */         fireStateChanged();
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
/*      */   public Point getViewPosition() {
/* 1033 */     Component component = getView();
/* 1034 */     if (component != null) {
/* 1035 */       Point point = component.getLocation();
/* 1036 */       point.x = -point.x;
/* 1037 */       point.y = -point.y;
/* 1038 */       return point;
/*      */     } 
/*      */     
/* 1041 */     return new Point(0, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setViewPosition(Point paramPoint) {
/*      */     int i, j;
/* 1054 */     Component component = getView();
/* 1055 */     if (component == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1059 */     int k = paramPoint.x, m = paramPoint.y;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1065 */     if (component instanceof JComponent) {
/* 1066 */       JComponent jComponent = (JComponent)component;
/* 1067 */       i = jComponent.getX();
/* 1068 */       j = jComponent.getY();
/*      */     } else {
/*      */       
/* 1071 */       Rectangle rectangle = component.getBounds();
/* 1072 */       i = rectangle.x;
/* 1073 */       j = rectangle.y;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     int n = -k;
/* 1080 */     int i1 = -m;
/*      */     
/* 1082 */     if (i != n || j != i1) {
/* 1083 */       if (!this.waitingForRepaint && isBlitting() && canUseWindowBlitter()) {
/* 1084 */         RepaintManager repaintManager = RepaintManager.currentManager(this);
/*      */ 
/*      */         
/* 1087 */         JComponent jComponent = (JComponent)component;
/* 1088 */         Rectangle rectangle = repaintManager.getDirtyRegion(jComponent);
/* 1089 */         if (rectangle == null || !rectangle.contains(jComponent.getVisibleRect())) {
/* 1090 */           repaintManager.beginPaint();
/*      */           try {
/* 1092 */             Graphics graphics = JComponent.safelyGetGraphics(this);
/* 1093 */             flushViewDirtyRegion(graphics, rectangle);
/* 1094 */             component.setLocation(n, i1);
/*      */             
/* 1096 */             Rectangle rectangle1 = new Rectangle(0, 0, getWidth(), Math.min(getHeight(), jComponent.getHeight()));
/* 1097 */             graphics.setClip(rectangle1);
/*      */ 
/*      */             
/* 1100 */             this
/* 1101 */               .repaintAll = (windowBlitPaint(graphics) && needsRepaintAfterBlit());
/* 1102 */             graphics.dispose();
/* 1103 */             repaintManager.notifyRepaintPerformed(this, rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
/* 1104 */             repaintManager.markCompletelyClean((JComponent)getParent());
/* 1105 */             repaintManager.markCompletelyClean(this);
/* 1106 */             repaintManager.markCompletelyClean(jComponent);
/*      */           } finally {
/* 1108 */             repaintManager.endPaint();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1113 */           component.setLocation(n, i1);
/* 1114 */           this.repaintAll = false;
/*      */         } 
/*      */       } else {
/*      */         
/* 1118 */         this.scrollUnderway = true;
/*      */         
/* 1120 */         component.setLocation(n, i1);
/* 1121 */         this.repaintAll = false;
/*      */       } 
/*      */       
/* 1124 */       revalidate();
/* 1125 */       fireStateChanged();
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
/*      */   public Rectangle getViewRect() {
/* 1139 */     return new Rectangle(getViewPosition(), getExtentSize());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean computeBlit(int paramInt1, int paramInt2, Point paramPoint1, Point paramPoint2, Dimension paramDimension, Rectangle paramRectangle) {
/* 1167 */     int i = Math.abs(paramInt1);
/* 1168 */     int j = Math.abs(paramInt2);
/* 1169 */     Dimension dimension = getExtentSize();
/*      */     
/* 1171 */     if (paramInt1 == 0 && paramInt2 != 0 && j < dimension.height) {
/* 1172 */       if (paramInt2 < 0) {
/* 1173 */         paramPoint1.y = -paramInt2;
/* 1174 */         paramPoint2.y = 0;
/* 1175 */         paramRectangle.y = dimension.height + paramInt2;
/*      */       } else {
/*      */         
/* 1178 */         paramPoint1.y = 0;
/* 1179 */         paramPoint2.y = paramInt2;
/* 1180 */         paramRectangle.y = 0;
/*      */       } 
/*      */       
/* 1183 */       paramRectangle.x = paramPoint2.x = 0;
/*      */       
/* 1185 */       paramDimension.width = dimension.width;
/* 1186 */       dimension.height -= j;
/*      */       
/* 1188 */       paramRectangle.width = dimension.width;
/* 1189 */       paramRectangle.height = j;
/*      */       
/* 1191 */       return true;
/*      */     } 
/*      */     
/* 1194 */     if (paramInt2 == 0 && paramInt1 != 0 && i < dimension.width) {
/* 1195 */       if (paramInt1 < 0) {
/* 1196 */         paramPoint1.x = -paramInt1;
/* 1197 */         paramPoint2.x = 0;
/* 1198 */         paramRectangle.x = dimension.width + paramInt1;
/*      */       } else {
/*      */         
/* 1201 */         paramPoint1.x = 0;
/* 1202 */         paramPoint2.x = paramInt1;
/* 1203 */         paramRectangle.x = 0;
/*      */       } 
/*      */       
/* 1206 */       paramRectangle.y = paramPoint2.y = 0;
/*      */       
/* 1208 */       dimension.width -= i;
/* 1209 */       paramDimension.height = dimension.height;
/*      */       
/* 1211 */       paramRectangle.width = i;
/* 1212 */       paramRectangle.height = dimension.height;
/*      */       
/* 1214 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1218 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Transient
/*      */   public Dimension getExtentSize() {
/* 1230 */     return getSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension toViewCoordinates(Dimension paramDimension) {
/* 1243 */     return new Dimension(paramDimension);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Point toViewCoordinates(Point paramPoint) {
/* 1255 */     return new Point(paramPoint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExtentSize(Dimension paramDimension) {
/* 1266 */     Dimension dimension = getExtentSize();
/* 1267 */     if (!paramDimension.equals(dimension)) {
/* 1268 */       setSize(paramDimension);
/* 1269 */       fireStateChanged();
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
/*      */   protected class ViewListener
/*      */     extends ComponentAdapter
/*      */     implements Serializable
/*      */   {
/*      */     public void componentResized(ComponentEvent param1ComponentEvent) {
/* 1288 */       JViewport.this.fireStateChanged();
/* 1289 */       JViewport.this.revalidate();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ViewListener createViewListener() {
/* 1298 */     return new ViewListener();
/*      */   }
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
/* 1310 */     return ViewportLayout.SHARED_INSTANCE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChangeListener(ChangeListener paramChangeListener) {
/* 1326 */     this.listenerList.add(ChangeListener.class, paramChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeChangeListener(ChangeListener paramChangeListener) {
/* 1338 */     this.listenerList.remove(ChangeListener.class, paramChangeListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChangeListener[] getChangeListeners() {
/* 1350 */     return this.listenerList.<ChangeListener>getListeners(ChangeListener.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fireStateChanged() {
/* 1363 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 1364 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 1365 */       if (arrayOfObject[i] == ChangeListener.class) {
/* 1366 */         if (this.changeEvent == null) {
/* 1367 */           this.changeEvent = new ChangeEvent(this);
/*      */         }
/* 1369 */         ((ChangeListener)arrayOfObject[i + 1]).stateChanged(this.changeEvent);
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
/*      */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1386 */     Container container = getParent();
/* 1387 */     if (container != null) {
/* 1388 */       container.repaint(paramLong, paramInt1 + getX(), paramInt2 + getY(), paramInt3, paramInt4);
/*      */     } else {
/* 1390 */       super.repaint(paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   protected String paramString() {
/* 1405 */     String str1 = this.isViewSizeSet ? "true" : "false";
/*      */ 
/*      */     
/* 1408 */     String str2 = (this.lastPaintPosition != null) ? this.lastPaintPosition.toString() : "";
/* 1409 */     String str3 = this.scrollUnderway ? "true" : "false";
/*      */ 
/*      */     
/* 1412 */     return super.paramString() + ",isViewSizeSet=" + str1 + ",lastPaintPosition=" + str2 + ",scrollUnderway=" + str3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 1433 */     super.firePropertyChange(paramString, paramObject1, paramObject2);
/* 1434 */     if (paramString.equals(EnableWindowBlit)) {
/* 1435 */       if (paramObject2 != null) {
/* 1436 */         setScrollMode(1);
/*      */       } else {
/* 1438 */         setScrollMode(0);
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
/*      */   private boolean needsRepaintAfterBlit() {
/* 1450 */     Container container = getParent();
/*      */     
/* 1452 */     while (container != null && container.isLightweight()) {
/* 1453 */       container = container.getParent();
/*      */     }
/*      */     
/* 1456 */     if (container != null) {
/* 1457 */       ComponentPeer componentPeer = container.getPeer();
/*      */       
/* 1459 */       if (componentPeer != null && componentPeer.canDetermineObscurity() && 
/* 1460 */         !componentPeer.isObscured())
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1467 */         return false;
/*      */       }
/*      */     } 
/* 1470 */     return true;
/*      */   }
/*      */   
/*      */   private Timer createRepaintTimer() {
/* 1474 */     Timer timer = new Timer(300, new ActionListener()
/*      */         {
/*      */           
/*      */           public void actionPerformed(ActionEvent param1ActionEvent)
/*      */           {
/* 1479 */             if (JViewport.this.waitingForRepaint) {
/* 1480 */               JViewport.this.repaint();
/*      */             }
/*      */           }
/*      */         });
/* 1484 */     timer.setRepeats(false);
/* 1485 */     return timer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void flushViewDirtyRegion(Graphics paramGraphics, Rectangle paramRectangle) {
/* 1495 */     JComponent jComponent = (JComponent)getView();
/* 1496 */     if (paramRectangle != null && paramRectangle.width > 0 && paramRectangle.height > 0) {
/* 1497 */       paramRectangle.x += jComponent.getX();
/* 1498 */       paramRectangle.y += jComponent.getY();
/* 1499 */       Rectangle rectangle = paramGraphics.getClipBounds();
/* 1500 */       if (rectangle == null)
/*      */       {
/* 1502 */         paramGraphics.setClip(0, 0, getWidth(), getHeight());
/*      */       }
/* 1504 */       paramGraphics.clipRect(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/* 1505 */       rectangle = paramGraphics.getClipBounds();
/*      */       
/* 1507 */       if (rectangle.width > 0 && rectangle.height > 0) {
/* 1508 */         paintView(paramGraphics);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean windowBlitPaint(Graphics paramGraphics) {
/*      */     boolean bool;
/* 1520 */     int i = getWidth();
/* 1521 */     int j = getHeight();
/*      */     
/* 1523 */     if (i == 0 || j == 0) {
/* 1524 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1528 */     RepaintManager repaintManager = RepaintManager.currentManager(this);
/* 1529 */     JComponent jComponent = (JComponent)getView();
/*      */     
/* 1531 */     if (this.lastPaintPosition == null || this.lastPaintPosition
/* 1532 */       .equals(getViewLocation())) {
/* 1533 */       paintView(paramGraphics);
/* 1534 */       bool = false;
/*      */     }
/*      */     else {
/*      */       
/* 1538 */       Point point1 = new Point();
/* 1539 */       Point point2 = new Point();
/* 1540 */       Dimension dimension = new Dimension();
/* 1541 */       Rectangle rectangle = new Rectangle();
/*      */       
/* 1543 */       Point point3 = getViewLocation();
/* 1544 */       int k = point3.x - this.lastPaintPosition.x;
/* 1545 */       int m = point3.y - this.lastPaintPosition.y;
/* 1546 */       boolean bool1 = computeBlit(k, m, point1, point2, dimension, rectangle);
/*      */       
/* 1548 */       if (!bool1) {
/* 1549 */         paintView(paramGraphics);
/* 1550 */         bool = false;
/*      */       }
/*      */       else {
/*      */         
/* 1554 */         Rectangle rectangle1 = jComponent.getBounds().intersection(rectangle);
/* 1555 */         rectangle1.x -= jComponent.getX();
/* 1556 */         rectangle1.y -= jComponent.getY();
/*      */         
/* 1558 */         blitDoubleBuffered(jComponent, paramGraphics, rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height, point1.x, point1.y, point2.x, point2.y, dimension.width, dimension.height);
/*      */ 
/*      */         
/* 1561 */         bool = true;
/*      */       } 
/*      */     } 
/* 1564 */     this.lastPaintPosition = getViewLocation();
/* 1565 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void blitDoubleBuffered(JComponent paramJComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
/* 1584 */     RepaintManager repaintManager = RepaintManager.currentManager(this);
/* 1585 */     int i = paramInt7 - paramInt5;
/* 1586 */     int j = paramInt8 - paramInt6;
/*      */     
/* 1588 */     Composite composite = null;
/*      */     
/* 1590 */     if (paramGraphics instanceof Graphics2D) {
/* 1591 */       Graphics2D graphics2D = (Graphics2D)paramGraphics;
/* 1592 */       composite = graphics2D.getComposite();
/* 1593 */       graphics2D.setComposite(AlphaComposite.Src);
/*      */     } 
/* 1595 */     repaintManager.copyArea(this, paramGraphics, paramInt5, paramInt6, paramInt9, paramInt10, i, j, false);
/*      */     
/* 1597 */     if (composite != null) {
/* 1598 */       ((Graphics2D)paramGraphics).setComposite(composite);
/*      */     }
/*      */     
/* 1601 */     int k = paramJComponent.getX();
/* 1602 */     int m = paramJComponent.getY();
/* 1603 */     paramGraphics.translate(k, m);
/* 1604 */     paramGraphics.setClip(paramInt1, paramInt2, paramInt3, paramInt4);
/* 1605 */     paramJComponent.paintForceDoubleBuffered(paramGraphics);
/* 1606 */     paramGraphics.translate(-k, -m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paintView(Graphics paramGraphics) {
/* 1616 */     Rectangle rectangle = paramGraphics.getClipBounds();
/* 1617 */     JComponent jComponent = (JComponent)getView();
/*      */     
/* 1619 */     if (jComponent.getWidth() >= getWidth()) {
/*      */ 
/*      */       
/* 1622 */       int i = jComponent.getX();
/* 1623 */       int j = jComponent.getY();
/* 1624 */       paramGraphics.translate(i, j);
/* 1625 */       paramGraphics.setClip(rectangle.x - i, rectangle.y - j, rectangle.width, rectangle.height);
/* 1626 */       jComponent.paintForceDoubleBuffered(paramGraphics);
/* 1627 */       paramGraphics.translate(-i, -j);
/* 1628 */       paramGraphics.setClip(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 1634 */         this.inBlitPaint = true;
/* 1635 */         paintForceDoubleBuffered(paramGraphics);
/*      */       } finally {
/* 1637 */         this.inBlitPaint = false;
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
/*      */   private boolean canUseWindowBlitter() {
/* 1650 */     if (!isShowing() || (!(getParent() instanceof JComponent) && 
/* 1651 */       !(getView() instanceof JComponent))) {
/* 1652 */       return false;
/*      */     }
/* 1654 */     if (isPainting())
/*      */     {
/*      */ 
/*      */       
/* 1658 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1662 */     Rectangle rectangle1 = RepaintManager.currentManager(this).getDirtyRegion((JComponent)getParent());
/*      */     
/* 1664 */     if (rectangle1 != null && rectangle1.width > 0 && rectangle1.height > 0)
/*      */     {
/*      */       
/* 1667 */       return false;
/*      */     }
/*      */     
/* 1670 */     Rectangle rectangle2 = new Rectangle(0, 0, getWidth(), getHeight());
/* 1671 */     Rectangle rectangle3 = new Rectangle();
/* 1672 */     Rectangle rectangle4 = null;
/*      */     
/* 1674 */     Component component = null;
/*      */     
/*      */     Container container;
/* 1677 */     for (container = this; container != null && isLightweightComponent(container); container = container.getParent()) {
/* 1678 */       int i = container.getX();
/* 1679 */       int j = container.getY();
/* 1680 */       int k = container.getWidth();
/* 1681 */       int m = container.getHeight();
/*      */       
/* 1683 */       rectangle3.setBounds(rectangle2);
/* 1684 */       SwingUtilities.computeIntersection(0, 0, k, m, rectangle2);
/* 1685 */       if (!rectangle2.equals(rectangle3)) {
/* 1686 */         return false;
/*      */       }
/* 1688 */       if (component != null && container instanceof JComponent && 
/* 1689 */         !container.isOptimizedDrawingEnabled()) {
/* 1690 */         Component[] arrayOfComponent = container.getComponents();
/* 1691 */         int n = 0;
/*      */         
/* 1693 */         for (int i1 = arrayOfComponent.length - 1; i1 >= 0; i1--) {
/* 1694 */           if (arrayOfComponent[i1] == component) {
/* 1695 */             n = i1 - 1;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1700 */         while (n >= 0) {
/* 1701 */           rectangle4 = arrayOfComponent[n].getBounds(rectangle4);
/*      */           
/* 1703 */           if (rectangle4.intersects(rectangle2))
/* 1704 */             return false; 
/* 1705 */           n--;
/*      */         } 
/*      */       } 
/* 1708 */       rectangle2.x += i;
/* 1709 */       rectangle2.y += j;
/* 1710 */       component = container;
/*      */     } 
/* 1712 */     if (container == null)
/*      */     {
/* 1714 */       return false;
/*      */     }
/* 1716 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/* 1734 */     if (this.accessibleContext == null) {
/* 1735 */       this.accessibleContext = new AccessibleJViewport();
/*      */     }
/* 1737 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJViewport
/*      */     extends JComponent.AccessibleJComponent
/*      */   {
/*      */     public AccessibleRole getAccessibleRole() {
/* 1762 */       return AccessibleRole.VIEWPORT;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JViewport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */