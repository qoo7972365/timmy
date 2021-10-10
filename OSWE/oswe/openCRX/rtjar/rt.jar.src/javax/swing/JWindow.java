/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.WindowListener;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JWindow
/*     */   extends Window
/*     */   implements Accessible, RootPaneContainer, TransferHandler.HasGetTransferHandler
/*     */ {
/*     */   protected JRootPane rootPane;
/*     */   protected boolean rootPaneCheckingEnabled = false;
/*     */   private TransferHandler transferHandler;
/*     */   protected AccessibleContext accessibleContext;
/*     */   
/*     */   public JWindow() {
/* 139 */     this((Frame)null);
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
/*     */   public JWindow(GraphicsConfiguration paramGraphicsConfiguration) {
/* 165 */     this((Window)null, paramGraphicsConfiguration);
/* 166 */     setFocusableWindowState(false);
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
/*     */   public JWindow(Frame paramFrame)
/*     */   {
/* 187 */     super((paramFrame == null) ? SwingUtilities.getSharedOwnerFrame() : paramFrame);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 643 */     this.accessibleContext = null; if (paramFrame == null) { WindowListener windowListener = SwingUtilities.getSharedOwnerFrameShutdownListener(); addWindowListener(windowListener); }  windowInit(); } public JWindow(Window paramWindow) { super((paramWindow == null) ? SwingUtilities.getSharedOwnerFrame() : paramWindow); this.accessibleContext = null; if (paramWindow == null) { WindowListener windowListener = SwingUtilities.getSharedOwnerFrameShutdownListener(); addWindowListener(windowListener); }  windowInit(); } public JWindow(Window paramWindow, GraphicsConfiguration paramGraphicsConfiguration) { super((paramWindow == null) ? SwingUtilities.getSharedOwnerFrame() : paramWindow, paramGraphicsConfiguration); this.accessibleContext = null;
/*     */     if (paramWindow == null) {
/*     */       WindowListener windowListener = SwingUtilities.getSharedOwnerFrameShutdownListener();
/*     */       addWindowListener(windowListener);
/*     */     } 
/*     */     windowInit(); }
/*     */   protected void windowInit() { setLocale(JComponent.getDefaultLocale());
/*     */     setRootPane(createRootPane());
/*     */     setRootPaneCheckingEnabled(true);
/*     */     SunToolkit.checkAndSetPolicy(this); }
/*     */   protected JRootPane createRootPane() { JRootPane jRootPane = new JRootPane();
/*     */     jRootPane.setOpaque(true);
/* 655 */     return jRootPane; } public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 656 */       this.accessibleContext = new AccessibleJWindow();
/*     */     }
/* 658 */     return this.accessibleContext; }
/*     */ 
/*     */   
/*     */   protected boolean isRootPaneCheckingEnabled() {
/*     */     return this.rootPaneCheckingEnabled;
/*     */   }
/*     */   
/*     */   public void setTransferHandler(TransferHandler paramTransferHandler) {
/*     */     TransferHandler transferHandler = this.transferHandler;
/*     */     this.transferHandler = paramTransferHandler;
/*     */     SwingUtilities.installSwingDropTargetAsNecessary(this, this.transferHandler);
/*     */     firePropertyChange("transferHandler", transferHandler, paramTransferHandler);
/*     */   }
/*     */   
/*     */   public TransferHandler getTransferHandler() {
/*     */     return this.transferHandler;
/*     */   }
/*     */   
/*     */   public void update(Graphics paramGraphics) {
/*     */     paint(paramGraphics);
/*     */   }
/*     */   
/*     */   protected void setRootPaneCheckingEnabled(boolean paramBoolean) {
/*     */     this.rootPaneCheckingEnabled = paramBoolean;
/*     */   }
/*     */   
/*     */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/*     */     if (isRootPaneCheckingEnabled()) {
/*     */       getContentPane().add(paramComponent, paramObject, paramInt);
/*     */     } else {
/*     */       super.addImpl(paramComponent, paramObject, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(Component paramComponent) {
/*     */     if (paramComponent == this.rootPane) {
/*     */       super.remove(paramComponent);
/*     */     } else {
/*     */       getContentPane().remove(paramComponent);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLayout(LayoutManager paramLayoutManager) {
/*     */     if (isRootPaneCheckingEnabled()) {
/*     */       getContentPane().setLayout(paramLayoutManager);
/*     */     } else {
/*     */       super.setLayout(paramLayoutManager);
/*     */     } 
/*     */   }
/*     */   
/*     */   public JRootPane getRootPane() {
/*     */     return this.rootPane;
/*     */   }
/*     */   
/*     */   protected void setRootPane(JRootPane paramJRootPane) {
/*     */     if (this.rootPane != null)
/*     */       remove(this.rootPane); 
/*     */     this.rootPane = paramJRootPane;
/*     */     if (this.rootPane != null) {
/*     */       boolean bool = isRootPaneCheckingEnabled();
/*     */       try {
/*     */         setRootPaneCheckingEnabled(false);
/*     */         add(this.rootPane, "Center");
/*     */       } finally {
/*     */         setRootPaneCheckingEnabled(bool);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Container getContentPane() {
/*     */     return getRootPane().getContentPane();
/*     */   }
/*     */   
/*     */   public void setContentPane(Container paramContainer) {
/*     */     getRootPane().setContentPane(paramContainer);
/*     */   }
/*     */   
/*     */   public JLayeredPane getLayeredPane() {
/*     */     return getRootPane().getLayeredPane();
/*     */   }
/*     */   
/*     */   public void setLayeredPane(JLayeredPane paramJLayeredPane) {
/*     */     getRootPane().setLayeredPane(paramJLayeredPane);
/*     */   }
/*     */   
/*     */   public Component getGlassPane() {
/*     */     return getRootPane().getGlassPane();
/*     */   }
/*     */   
/*     */   public void setGlassPane(Component paramComponent) {
/*     */     getRootPane().setGlassPane(paramComponent);
/*     */   }
/*     */   
/*     */   public Graphics getGraphics() {
/*     */     JComponent.getGraphicsInvoked(this);
/*     */     return super.getGraphics();
/*     */   }
/*     */   
/*     */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
/*     */       RepaintManager.currentManager(this).addDirtyRegion(this, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } else {
/*     */       super.repaint(paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String paramString() {
/*     */     String str = this.rootPaneCheckingEnabled ? "true" : "false";
/*     */     return super.paramString() + ",rootPaneCheckingEnabled=" + str;
/*     */   }
/*     */   
/*     */   protected class AccessibleJWindow extends Window.AccessibleAWTWindow {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */