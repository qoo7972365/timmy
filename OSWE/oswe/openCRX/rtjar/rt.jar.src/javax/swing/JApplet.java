/*     */ package javax.swing;
/*     */ 
/*     */ import java.applet.Applet;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.LayoutManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JApplet
/*     */   extends Applet
/*     */   implements Accessible, RootPaneContainer, TransferHandler.HasGetTransferHandler
/*     */ {
/*     */   protected JRootPane rootPane;
/*     */   protected boolean rootPaneCheckingEnabled = false;
/*     */   private TransferHandler transferHandler;
/*     */   protected AccessibleContext accessibleContext;
/*     */   
/*     */   protected JRootPane createRootPane() {
/* 161 */     JRootPane jRootPane = new JRootPane();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     jRootPane.setOpaque(true);
/* 167 */     return jRootPane;
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
/*     */   public void setTransferHandler(TransferHandler paramTransferHandler) {
/* 204 */     TransferHandler transferHandler = this.transferHandler;
/* 205 */     this.transferHandler = paramTransferHandler;
/* 206 */     SwingUtilities.installSwingDropTargetAsNecessary(this, this.transferHandler);
/* 207 */     firePropertyChange("transferHandler", transferHandler, paramTransferHandler);
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
/*     */   public TransferHandler getTransferHandler() {
/* 220 */     return this.transferHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(Graphics paramGraphics) {
/* 228 */     paint(paramGraphics);
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
/*     */   public void setJMenuBar(JMenuBar paramJMenuBar) {
/* 242 */     getRootPane().setMenuBar(paramJMenuBar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JMenuBar getJMenuBar() {
/* 251 */     return getRootPane().getMenuBar();
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
/*     */   protected boolean isRootPaneCheckingEnabled() {
/* 268 */     return this.rootPaneCheckingEnabled;
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
/*     */   protected void setRootPaneCheckingEnabled(boolean paramBoolean) {
/* 289 */     this.rootPaneCheckingEnabled = paramBoolean;
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
/*     */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 314 */     if (isRootPaneCheckingEnabled()) {
/* 315 */       getContentPane().add(paramComponent, paramObject, paramInt);
/*     */     } else {
/*     */       
/* 318 */       super.addImpl(paramComponent, paramObject, paramInt);
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
/*     */   public void remove(Component paramComponent) {
/* 335 */     if (paramComponent == this.rootPane) {
/* 336 */       super.remove(paramComponent);
/*     */     } else {
/* 338 */       getContentPane().remove(paramComponent);
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
/*     */   public void setLayout(LayoutManager paramLayoutManager) {
/* 355 */     if (isRootPaneCheckingEnabled()) {
/* 356 */       getContentPane().setLayout(paramLayoutManager);
/*     */     } else {
/*     */       
/* 359 */       super.setLayout(paramLayoutManager);
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
/*     */   public JRootPane getRootPane() {
/* 371 */     return this.rootPane;
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
/*     */   protected void setRootPane(JRootPane paramJRootPane) {
/* 386 */     if (this.rootPane != null) {
/* 387 */       remove(this.rootPane);
/*     */     }
/* 389 */     this.rootPane = paramJRootPane;
/* 390 */     if (this.rootPane != null) {
/* 391 */       boolean bool = isRootPaneCheckingEnabled();
/*     */       try {
/* 393 */         setRootPaneCheckingEnabled(false);
/* 394 */         add(this.rootPane, "Center");
/*     */       } finally {
/*     */         
/* 397 */         setRootPaneCheckingEnabled(bool);
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
/*     */   public Container getContentPane() {
/* 410 */     return getRootPane().getContentPane();
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
/*     */   public void setContentPane(Container paramContainer) {
/* 428 */     getRootPane().setContentPane(paramContainer);
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
/*     */   public JLayeredPane getLayeredPane() {
/* 440 */     return getRootPane().getLayeredPane();
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
/*     */   public void setLayeredPane(JLayeredPane paramJLayeredPane) {
/* 455 */     getRootPane().setLayeredPane(paramJLayeredPane);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getGlassPane() {
/* 465 */     return getRootPane().getGlassPane();
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
/*     */   public void setGlassPane(Component paramComponent) {
/* 481 */     getRootPane().setGlassPane(paramComponent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Graphics getGraphics() {
/* 490 */     JComponent.getGraphicsInvoked(this);
/* 491 */     return super.getGraphics();
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
/*     */   public void repaint(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 508 */     if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
/* 509 */       RepaintManager.currentManager(this).addDirtyRegion(this, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */     else {
/*     */       
/* 513 */       super.repaint(paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
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
/*     */   protected String paramString() {
/* 528 */     String str1 = (this.rootPane != null) ? this.rootPane.toString() : "";
/* 529 */     String str2 = this.rootPaneCheckingEnabled ? "true" : "false";
/*     */ 
/*     */     
/* 532 */     return super.paramString() + ",rootPane=" + str1 + ",rootPaneCheckingEnabled=" + str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JApplet() throws HeadlessException {
/* 543 */     this.accessibleContext = null;
/*     */     TimerQueue timerQueue = TimerQueue.sharedInstance();
/*     */     if (timerQueue != null)
/*     */       timerQueue.startIfNeeded(); 
/*     */     setForeground(Color.black);
/*     */     setBackground(Color.white);
/*     */     setLocale(JComponent.getDefaultLocale());
/*     */     setLayout(new BorderLayout());
/*     */     setRootPane(createRootPane());
/*     */     setRootPaneCheckingEnabled(true);
/*     */     setFocusTraversalPolicyProvider(true);
/*     */     SunToolkit.checkAndSetPolicy(this);
/* 555 */     enableEvents(8L); } public AccessibleContext getAccessibleContext() { if (this.accessibleContext == null) {
/* 556 */       this.accessibleContext = new AccessibleJApplet();
/*     */     }
/* 558 */     return this.accessibleContext; }
/*     */ 
/*     */   
/*     */   protected class AccessibleJApplet extends Applet.AccessibleApplet {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JApplet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */