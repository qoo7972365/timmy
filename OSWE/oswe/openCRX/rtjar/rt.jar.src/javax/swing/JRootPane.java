/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.LayoutManager2;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.swing.plaf.RootPaneUI;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JRootPane
/*      */   extends JComponent
/*      */   implements Accessible
/*      */ {
/*      */   boolean useTrueDoubleBuffering = true;
/*  353 */   private static final boolean LOG_DISABLE_TRUE_DOUBLE_BUFFERING = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("swing.logDoubleBufferingDisable"))).booleanValue();
/*      */ 
/*      */   
/*  356 */   private static final boolean IGNORE_DISABLE_TRUE_DOUBLE_BUFFERING = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("swing.ignoreDoubleBufferingDisable"))).booleanValue(); private static final String uiClassID = "RootPaneUI"; public static final int NONE = 0;
/*      */   public static final int FRAME = 1;
/*      */   public static final int PLAIN_DIALOG = 2;
/*      */   public static final int INFORMATION_DIALOG = 3;
/*      */   public static final int ERROR_DIALOG = 4;
/*      */   public static final int COLOR_CHOOSER_DIALOG = 5;
/*      */   public static final int FILE_CHOOSER_DIALOG = 6;
/*      */   public static final int QUESTION_DIALOG = 7;
/*      */   
/*      */   public JRootPane() {
/*  366 */     setGlassPane(createGlassPane());
/*  367 */     setLayeredPane(createLayeredPane());
/*  368 */     setContentPane(createContentPane());
/*  369 */     setLayout(createRootLayout());
/*  370 */     setDoubleBuffered(true);
/*  371 */     updateUI();
/*      */   }
/*      */   public static final int WARNING_DIALOG = 8;
/*      */   private int windowDecorationStyle;
/*      */   protected JMenuBar menuBar;
/*      */   protected Container contentPane;
/*      */   
/*      */   public void setDoubleBuffered(boolean paramBoolean) {
/*  379 */     if (isDoubleBuffered() != paramBoolean) {
/*  380 */       super.setDoubleBuffered(paramBoolean);
/*  381 */       RepaintManager.currentManager(this).doubleBufferingChanged(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected JLayeredPane layeredPane;
/*      */   
/*      */   protected Component glassPane;
/*      */   
/*      */   protected JButton defaultButton;
/*      */   
/*      */   @Deprecated
/*      */   protected DefaultAction defaultPressAction;
/*      */   @Deprecated
/*      */   protected DefaultAction defaultReleaseAction;
/*      */   
/*      */   public int getWindowDecorationStyle() {
/*  398 */     return this.windowDecorationStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWindowDecorationStyle(int paramInt) {
/*  439 */     if (paramInt < 0 || paramInt > 8)
/*      */     {
/*  441 */       throw new IllegalArgumentException("Invalid decoration style");
/*      */     }
/*  443 */     int i = getWindowDecorationStyle();
/*  444 */     this.windowDecorationStyle = paramInt;
/*  445 */     firePropertyChange("windowDecorationStyle", i, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RootPaneUI getUI() {
/*  457 */     return (RootPaneUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUI(RootPaneUI paramRootPaneUI) {
/*  474 */     setUI(paramRootPaneUI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  484 */     setUI((RootPaneUI)UIManager.getUI(this));
/*      */   }
/*      */ 
/*      */ 
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
/*  498 */     return "RootPaneUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected JLayeredPane createLayeredPane() {
/*  508 */     JLayeredPane jLayeredPane = new JLayeredPane();
/*  509 */     jLayeredPane.setName(getName() + ".layeredPane");
/*  510 */     return jLayeredPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Container createContentPane() {
/*  521 */     JPanel jPanel = new JPanel();
/*  522 */     jPanel.setName(getName() + ".contentPane");
/*  523 */     jPanel.setLayout(new BorderLayout()
/*      */         {
/*      */ 
/*      */           
/*      */           public void addLayoutComponent(Component param1Component, Object param1Object)
/*      */           {
/*  529 */             if (param1Object == null) {
/*  530 */               param1Object = "Center";
/*      */             }
/*  532 */             super.addLayoutComponent(param1Component, param1Object);
/*      */           }
/*      */         });
/*  535 */     return jPanel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Component createGlassPane() {
/*  546 */     JPanel jPanel = new JPanel();
/*  547 */     jPanel.setName(getName() + ".glassPane");
/*  548 */     jPanel.setVisible(false);
/*  549 */     jPanel.setOpaque(false);
/*  550 */     return jPanel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LayoutManager createRootLayout() {
/*  559 */     return new RootLayout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJMenuBar(JMenuBar paramJMenuBar) {
/*  567 */     if (this.menuBar != null && this.menuBar.getParent() == this.layeredPane)
/*  568 */       this.layeredPane.remove(this.menuBar); 
/*  569 */     this.menuBar = paramJMenuBar;
/*      */     
/*  571 */     if (this.menuBar != null) {
/*  572 */       this.layeredPane.add(this.menuBar, JLayeredPane.FRAME_CONTENT_LAYER);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void setMenuBar(JMenuBar paramJMenuBar) {
/*  583 */     if (this.menuBar != null && this.menuBar.getParent() == this.layeredPane)
/*  584 */       this.layeredPane.remove(this.menuBar); 
/*  585 */     this.menuBar = paramJMenuBar;
/*      */     
/*  587 */     if (this.menuBar != null) {
/*  588 */       this.layeredPane.add(this.menuBar, JLayeredPane.FRAME_CONTENT_LAYER);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public JMenuBar getJMenuBar() {
/*  595 */     return this.menuBar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public JMenuBar getMenuBar() {
/*  604 */     return this.menuBar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContentPane(Container paramContainer) {
/*  620 */     if (paramContainer == null)
/*  621 */       throw new IllegalComponentStateException("contentPane cannot be set to null."); 
/*  622 */     if (this.contentPane != null && this.contentPane.getParent() == this.layeredPane)
/*  623 */       this.layeredPane.remove(this.contentPane); 
/*  624 */     this.contentPane = paramContainer;
/*      */     
/*  626 */     this.layeredPane.add(this.contentPane, JLayeredPane.FRAME_CONTENT_LAYER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Container getContentPane() {
/*  635 */     return this.contentPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLayeredPane(JLayeredPane paramJLayeredPane) {
/*  647 */     if (paramJLayeredPane == null)
/*  648 */       throw new IllegalComponentStateException("layeredPane cannot be set to null."); 
/*  649 */     if (this.layeredPane != null && this.layeredPane.getParent() == this)
/*  650 */       remove(this.layeredPane); 
/*  651 */     this.layeredPane = paramJLayeredPane;
/*      */     
/*  653 */     add(this.layeredPane, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JLayeredPane getLayeredPane() {
/*  661 */     return this.layeredPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGlassPane(Component paramComponent) {
/*  689 */     if (paramComponent == null) {
/*  690 */       throw new NullPointerException("glassPane cannot be set to null.");
/*      */     }
/*      */     
/*  693 */     AWTAccessor.getComponentAccessor().setMixingCutoutShape(paramComponent, new Rectangle());
/*      */ 
/*      */     
/*  696 */     boolean bool = false;
/*  697 */     if (this.glassPane != null && this.glassPane.getParent() == this) {
/*  698 */       remove(this.glassPane);
/*  699 */       bool = this.glassPane.isVisible();
/*      */     } 
/*      */     
/*  702 */     paramComponent.setVisible(bool);
/*  703 */     this.glassPane = paramComponent;
/*  704 */     add(this.glassPane, 0);
/*  705 */     if (bool) {
/*  706 */       repaint();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Component getGlassPane() {
/*  716 */     return this.glassPane;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  734 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  750 */     return !this.glassPane.isVisible();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addNotify() {
/*  757 */     super.addNotify();
/*  758 */     enableEvents(8L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotify() {
/*  765 */     super.removeNotify();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultButton(JButton paramJButton) {
/*  790 */     JButton jButton = this.defaultButton;
/*      */     
/*  792 */     if (jButton != paramJButton) {
/*  793 */       this.defaultButton = paramJButton;
/*      */       
/*  795 */       if (jButton != null) {
/*  796 */         jButton.repaint();
/*      */       }
/*  798 */       if (paramJButton != null) {
/*  799 */         paramJButton.repaint();
/*      */       }
/*      */     } 
/*      */     
/*  803 */     firePropertyChange("defaultButton", jButton, paramJButton);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JButton getDefaultButton() {
/*  812 */     return this.defaultButton;
/*      */   }
/*      */   
/*      */   final void setUseTrueDoubleBuffering(boolean paramBoolean) {
/*  816 */     this.useTrueDoubleBuffering = paramBoolean;
/*      */   }
/*      */   
/*      */   final boolean getUseTrueDoubleBuffering() {
/*  820 */     return this.useTrueDoubleBuffering;
/*      */   }
/*      */   
/*      */   final void disableTrueDoubleBuffering() {
/*  824 */     if (this.useTrueDoubleBuffering && 
/*  825 */       !IGNORE_DISABLE_TRUE_DOUBLE_BUFFERING) {
/*  826 */       if (LOG_DISABLE_TRUE_DOUBLE_BUFFERING) {
/*  827 */         System.out.println("Disabling true double buffering for " + this);
/*      */         
/*  829 */         Thread.dumpStack();
/*      */       } 
/*  831 */       this.useTrueDoubleBuffering = false;
/*  832 */       RepaintManager.currentManager(this)
/*  833 */         .doubleBufferingChanged(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   static class DefaultAction
/*      */     extends AbstractAction {
/*      */     JButton owner;
/*      */     JRootPane root;
/*      */     boolean press;
/*      */     
/*      */     DefaultAction(JRootPane param1JRootPane, boolean param1Boolean) {
/*  844 */       this.root = param1JRootPane;
/*  845 */       this.press = param1Boolean;
/*      */     }
/*      */     public void setOwner(JButton param1JButton) {
/*  848 */       this.owner = param1JButton;
/*      */     }
/*      */     public void actionPerformed(ActionEvent param1ActionEvent) {
/*  851 */       if (this.owner != null && SwingUtilities.getRootPane(this.owner) == this.root) {
/*  852 */         ButtonModel buttonModel = this.owner.getModel();
/*  853 */         if (this.press) {
/*  854 */           buttonModel.setArmed(true);
/*  855 */           buttonModel.setPressed(true);
/*      */         } else {
/*  857 */           buttonModel.setPressed(false);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public boolean isEnabled() {
/*  862 */       return this.owner.getModel().isEnabled();
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
/*      */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/*  876 */     super.addImpl(paramComponent, paramObject, paramInt);
/*      */ 
/*      */     
/*  879 */     if (this.glassPane != null && this.glassPane
/*  880 */       .getParent() == this && 
/*  881 */       getComponent(0) != this.glassPane) {
/*  882 */       add(this.glassPane, 0);
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
/*      */   protected class RootLayout
/*      */     implements LayoutManager2, Serializable
/*      */   {
/*      */     public Dimension preferredLayoutSize(Container param1Container) {
/*      */       Dimension dimension1, dimension2;
/*  917 */       Insets insets = JRootPane.this.getInsets();
/*      */       
/*  919 */       if (JRootPane.this.contentPane != null) {
/*  920 */         dimension1 = JRootPane.this.contentPane.getPreferredSize();
/*      */       } else {
/*  922 */         dimension1 = param1Container.getSize();
/*      */       } 
/*  924 */       if (JRootPane.this.menuBar != null && JRootPane.this.menuBar.isVisible()) {
/*  925 */         dimension2 = JRootPane.this.menuBar.getPreferredSize();
/*      */       } else {
/*  927 */         dimension2 = new Dimension(0, 0);
/*      */       } 
/*  929 */       return new Dimension(Math.max(dimension1.width, dimension2.width) + insets.left + insets.right, dimension1.height + dimension2.height + insets.top + insets.bottom);
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
/*      */     public Dimension minimumLayoutSize(Container param1Container) {
/*      */       Dimension dimension1, dimension2;
/*  942 */       Insets insets = JRootPane.this.getInsets();
/*  943 */       if (JRootPane.this.contentPane != null) {
/*  944 */         dimension1 = JRootPane.this.contentPane.getMinimumSize();
/*      */       } else {
/*  946 */         dimension1 = param1Container.getSize();
/*      */       } 
/*  948 */       if (JRootPane.this.menuBar != null && JRootPane.this.menuBar.isVisible()) {
/*  949 */         dimension2 = JRootPane.this.menuBar.getMinimumSize();
/*      */       } else {
/*  951 */         dimension2 = new Dimension(0, 0);
/*      */       } 
/*  953 */       return new Dimension(Math.max(dimension1.width, dimension2.width) + insets.left + insets.right, dimension1.height + dimension2.height + insets.top + insets.bottom);
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
/*      */     public Dimension maximumLayoutSize(Container param1Container) {
/*      */       Dimension dimension1, dimension2;
/*  966 */       Insets insets = JRootPane.this.getInsets();
/*  967 */       if (JRootPane.this.menuBar != null && JRootPane.this.menuBar.isVisible()) {
/*  968 */         dimension2 = JRootPane.this.menuBar.getMaximumSize();
/*      */       } else {
/*  970 */         dimension2 = new Dimension(0, 0);
/*      */       } 
/*  972 */       if (JRootPane.this.contentPane != null) {
/*  973 */         dimension1 = JRootPane.this.contentPane.getMaximumSize();
/*      */       } else {
/*      */         
/*  976 */         dimension1 = new Dimension(2147483647, Integer.MAX_VALUE - insets.top - insets.bottom - dimension2.height - 1);
/*      */       } 
/*      */       
/*  979 */       return new Dimension(Math.min(dimension1.width, dimension2.width) + insets.left + insets.right, dimension1.height + dimension2.height + insets.top + insets.bottom);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void addLayoutComponent(String param1String, Component param1Component) {}
/*      */ 
/*      */     
/*      */     public void removeLayoutComponent(Component param1Component) {}
/*      */ 
/*      */     
/*      */     public void layoutContainer(Container param1Container) {
/*  991 */       Rectangle rectangle = param1Container.getBounds();
/*  992 */       Insets insets = JRootPane.this.getInsets();
/*  993 */       int i = 0;
/*  994 */       int j = rectangle.width - insets.right - insets.left;
/*  995 */       int k = rectangle.height - insets.top - insets.bottom;
/*      */       
/*  997 */       if (JRootPane.this.layeredPane != null) {
/*  998 */         JRootPane.this.layeredPane.setBounds(insets.left, insets.top, j, k);
/*      */       }
/* 1000 */       if (JRootPane.this.glassPane != null) {
/* 1001 */         JRootPane.this.glassPane.setBounds(insets.left, insets.top, j, k);
/*      */       }
/*      */ 
/*      */       
/* 1005 */       if (JRootPane.this.menuBar != null && JRootPane.this.menuBar.isVisible()) {
/* 1006 */         Dimension dimension = JRootPane.this.menuBar.getPreferredSize();
/* 1007 */         JRootPane.this.menuBar.setBounds(0, 0, j, dimension.height);
/* 1008 */         i += dimension.height;
/*      */       } 
/* 1010 */       if (JRootPane.this.contentPane != null) {
/* 1011 */         JRootPane.this.contentPane.setBounds(0, i, j, k - i);
/*      */       }
/*      */     }
/*      */     
/*      */     public void addLayoutComponent(Component param1Component, Object param1Object) {}
/*      */     
/*      */     public float getLayoutAlignmentX(Container param1Container) {
/* 1018 */       return 0.0F; } public float getLayoutAlignmentY(Container param1Container) {
/* 1019 */       return 0.0F;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void invalidateLayout(Container param1Container) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String paramString() {
/* 1033 */     return super.paramString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1051 */     if (this.accessibleContext == null) {
/* 1052 */       this.accessibleContext = new AccessibleJRootPane();
/*      */     }
/* 1054 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJRootPane
/*      */     extends JComponent.AccessibleJComponent
/*      */   {
/*      */     public AccessibleRole getAccessibleRole() {
/* 1080 */       return AccessibleRole.ROOT_PANE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/* 1089 */       return super.getAccessibleChildrenCount();
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
/*      */     public Accessible getAccessibleChild(int param1Int) {
/* 1103 */       return super.getAccessibleChild(param1Int);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JRootPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */