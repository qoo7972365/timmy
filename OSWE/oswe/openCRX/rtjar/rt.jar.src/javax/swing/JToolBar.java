/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import javax.swing.plaf.ToolBarUI;
/*     */ import javax.swing.plaf.UIResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JToolBar
/*     */   extends JComponent
/*     */   implements SwingConstants, Accessible
/*     */ {
/*     */   private static final String uiClassID = "ToolBarUI";
/*     */   private boolean paintBorder = true;
/*  98 */   private Insets margin = null;
/*     */   private boolean floatable = true;
/* 100 */   private int orientation = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JToolBar() {
/* 107 */     this(0);
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
/*     */   public JToolBar(int paramInt) {
/* 119 */     this((String)null, paramInt);
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
/*     */   public JToolBar(String paramString) {
/* 131 */     this(paramString, 0);
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
/*     */   public JToolBar(String paramString, int paramInt) {
/* 149 */     setName(paramString);
/* 150 */     checkOrientation(paramInt);
/*     */     
/* 152 */     this.orientation = paramInt;
/* 153 */     DefaultToolBarLayout defaultToolBarLayout = new DefaultToolBarLayout(paramInt);
/* 154 */     setLayout(defaultToolBarLayout);
/*     */     
/* 156 */     addPropertyChangeListener(defaultToolBarLayout);
/*     */     
/* 158 */     updateUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolBarUI getUI() {
/* 166 */     return (ToolBarUI)this.ui;
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
/*     */   public void setUI(ToolBarUI paramToolBarUI) {
/* 181 */     setUI(paramToolBarUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 192 */     setUI((ToolBarUI)UIManager.getUI(this));
/*     */ 
/*     */ 
/*     */     
/* 196 */     if (getLayout() == null) {
/* 197 */       setLayout(new DefaultToolBarLayout(getOrientation()));
/*     */     }
/* 199 */     invalidate();
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
/*     */   public String getUIClassID() {
/* 212 */     return "ToolBarUI";
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
/*     */   public int getComponentIndex(Component paramComponent) {
/* 225 */     int i = getComponentCount();
/* 226 */     Component[] arrayOfComponent = getComponents();
/* 227 */     for (byte b = 0; b < i; b++) {
/* 228 */       Component component = arrayOfComponent[b];
/* 229 */       if (component == paramComponent)
/* 230 */         return b; 
/*     */     } 
/* 232 */     return -1;
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
/*     */   public Component getComponentAtIndex(int paramInt) {
/* 244 */     int i = getComponentCount();
/* 245 */     if (paramInt >= 0 && paramInt < i) {
/* 246 */       Component[] arrayOfComponent = getComponents();
/* 247 */       return arrayOfComponent[paramInt];
/*     */     } 
/* 249 */     return null;
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
/*     */   public void setMargin(Insets paramInsets) {
/* 272 */     Insets insets = this.margin;
/* 273 */     this.margin = paramInsets;
/* 274 */     firePropertyChange("margin", insets, paramInsets);
/* 275 */     revalidate();
/* 276 */     repaint();
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
/*     */   public Insets getMargin() {
/* 288 */     if (this.margin == null) {
/* 289 */       return new Insets(0, 0, 0, 0);
/*     */     }
/* 291 */     return this.margin;
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
/*     */   public boolean isBorderPainted() {
/* 303 */     return this.paintBorder;
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
/*     */   public void setBorderPainted(boolean paramBoolean) {
/* 323 */     if (this.paintBorder != paramBoolean) {
/*     */       
/* 325 */       boolean bool = this.paintBorder;
/* 326 */       this.paintBorder = paramBoolean;
/* 327 */       firePropertyChange("borderPainted", bool, paramBoolean);
/* 328 */       revalidate();
/* 329 */       repaint();
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
/*     */   protected void paintBorder(Graphics paramGraphics) {
/* 344 */     if (isBorderPainted())
/*     */     {
/* 346 */       super.paintBorder(paramGraphics);
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
/*     */   public boolean isFloatable() {
/* 359 */     return this.floatable;
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
/*     */   public void setFloatable(boolean paramBoolean) {
/* 382 */     if (this.floatable != paramBoolean) {
/*     */       
/* 384 */       boolean bool = this.floatable;
/* 385 */       this.floatable = paramBoolean;
/*     */       
/* 387 */       firePropertyChange("floatable", bool, paramBoolean);
/* 388 */       revalidate();
/* 389 */       repaint();
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
/*     */   public int getOrientation() {
/* 403 */     return this.orientation;
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
/*     */   public void setOrientation(int paramInt) {
/* 426 */     checkOrientation(paramInt);
/*     */     
/* 428 */     if (this.orientation != paramInt) {
/*     */       
/* 430 */       int i = this.orientation;
/* 431 */       this.orientation = paramInt;
/*     */       
/* 433 */       firePropertyChange("orientation", i, paramInt);
/* 434 */       revalidate();
/* 435 */       repaint();
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
/*     */   public void setRollover(boolean paramBoolean) {
/* 457 */     putClientProperty("JToolBar.isRollover", paramBoolean ? Boolean.TRUE : Boolean.FALSE);
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
/*     */   public boolean isRollover() {
/* 469 */     Boolean bool = (Boolean)getClientProperty("JToolBar.isRollover");
/* 470 */     if (bool != null) {
/* 471 */       return bool.booleanValue();
/*     */     }
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkOrientation(int paramInt) {
/* 478 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */         return;
/*     */     } 
/*     */     
/* 484 */     throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSeparator() {
/* 494 */     addSeparator((Dimension)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSeparator(Dimension paramDimension) {
/* 505 */     Separator separator = new Separator(paramDimension);
/* 506 */     add(separator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton add(Action paramAction) {
/* 516 */     JButton jButton = createActionComponent(paramAction);
/* 517 */     jButton.setAction(paramAction);
/* 518 */     add(jButton);
/* 519 */     return jButton;
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
/*     */   protected JButton createActionComponent(Action paramAction) {
/* 533 */     JButton jButton = new JButton() {
/*     */         protected PropertyChangeListener createActionPropertyChangeListener(Action param1Action) {
/* 535 */           PropertyChangeListener propertyChangeListener = JToolBar.this.createActionChangeListener(this);
/* 536 */           if (propertyChangeListener == null) {
/* 537 */             propertyChangeListener = super.createActionPropertyChangeListener(param1Action);
/*     */           }
/* 539 */           return propertyChangeListener;
/*     */         }
/*     */       };
/* 542 */     if (paramAction != null && (paramAction.getValue("SmallIcon") != null || paramAction
/* 543 */       .getValue("SwingLargeIconKey") != null)) {
/* 544 */       jButton.setHideActionText(true);
/*     */     }
/* 546 */     jButton.setHorizontalTextPosition(0);
/* 547 */     jButton.setVerticalTextPosition(3);
/* 548 */     return jButton;
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
/*     */   protected PropertyChangeListener createActionChangeListener(JButton paramJButton) {
/* 560 */     return null;
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
/*     */   protected void addImpl(Component paramComponent, Object paramObject, int paramInt) {
/* 573 */     if (paramComponent instanceof Separator) {
/* 574 */       if (getOrientation() == 1) {
/* 575 */         ((Separator)paramComponent).setOrientation(0);
/*     */       } else {
/* 577 */         ((Separator)paramComponent).setOrientation(1);
/*     */       } 
/*     */     }
/* 580 */     super.addImpl(paramComponent, paramObject, paramInt);
/* 581 */     if (paramComponent instanceof JButton) {
/* 582 */       ((JButton)paramComponent).setDefaultCapable(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Separator
/*     */     extends JSeparator
/*     */   {
/*     */     private Dimension separatorSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Separator() {
/* 601 */       this((Dimension)null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Separator(Dimension param1Dimension) {
/* 611 */       super(0);
/* 612 */       setSeparatorSize(param1Dimension);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getUIClassID() {
/* 624 */       return "ToolBarSeparatorUI";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSeparatorSize(Dimension param1Dimension) {
/* 634 */       if (param1Dimension != null) {
/* 635 */         this.separatorSize = param1Dimension;
/*     */       } else {
/* 637 */         updateUI();
/*     */       } 
/* 639 */       invalidate();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getSeparatorSize() {
/* 650 */       return this.separatorSize;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getMinimumSize() {
/* 661 */       if (this.separatorSize != null) {
/* 662 */         return this.separatorSize.getSize();
/*     */       }
/* 664 */       return super.getMinimumSize();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getMaximumSize() {
/* 676 */       if (this.separatorSize != null) {
/* 677 */         return this.separatorSize.getSize();
/*     */       }
/* 679 */       return super.getMaximumSize();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Dimension getPreferredSize() {
/* 691 */       if (this.separatorSize != null) {
/* 692 */         return this.separatorSize.getSize();
/*     */       }
/* 694 */       return super.getPreferredSize();
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 706 */     paramObjectOutputStream.defaultWriteObject();
/* 707 */     if (getUIClassID().equals("ToolBarUI")) {
/* 708 */       byte b = JComponent.getWriteObjCounter(this);
/* 709 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 710 */       if (b == 0 && this.ui != null) {
/* 711 */         this.ui.installUI(this);
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
/*     */   protected String paramString() {
/* 728 */     String str1 = this.paintBorder ? "true" : "false";
/*     */ 
/*     */     
/* 731 */     String str2 = (this.margin != null) ? this.margin.toString() : "";
/* 732 */     String str3 = this.floatable ? "true" : "false";
/*     */     
/* 734 */     String str4 = (this.orientation == 0) ? "HORIZONTAL" : "VERTICAL";
/*     */ 
/*     */     
/* 737 */     return super.paramString() + ",floatable=" + str3 + ",margin=" + str2 + ",orientation=" + str4 + ",paintBorder=" + str1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class DefaultToolBarLayout
/*     */     implements LayoutManager2, Serializable, PropertyChangeListener, UIResource
/*     */   {
/*     */     BoxLayout lm;
/*     */ 
/*     */ 
/*     */     
/*     */     DefaultToolBarLayout(int param1Int) {
/* 751 */       if (param1Int == 1) {
/* 752 */         this.lm = new BoxLayout(JToolBar.this, 3);
/*     */       } else {
/* 754 */         this.lm = new BoxLayout(JToolBar.this, 2);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void addLayoutComponent(String param1String, Component param1Component) {
/* 759 */       this.lm.addLayoutComponent(param1String, param1Component);
/*     */     }
/*     */     
/*     */     public void addLayoutComponent(Component param1Component, Object param1Object) {
/* 763 */       this.lm.addLayoutComponent(param1Component, param1Object);
/*     */     }
/*     */     
/*     */     public void removeLayoutComponent(Component param1Component) {
/* 767 */       this.lm.removeLayoutComponent(param1Component);
/*     */     }
/*     */     
/*     */     public Dimension preferredLayoutSize(Container param1Container) {
/* 771 */       return this.lm.preferredLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     public Dimension minimumLayoutSize(Container param1Container) {
/* 775 */       return this.lm.minimumLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     public Dimension maximumLayoutSize(Container param1Container) {
/* 779 */       return this.lm.maximumLayoutSize(param1Container);
/*     */     }
/*     */     
/*     */     public void layoutContainer(Container param1Container) {
/* 783 */       this.lm.layoutContainer(param1Container);
/*     */     }
/*     */     
/*     */     public float getLayoutAlignmentX(Container param1Container) {
/* 787 */       return this.lm.getLayoutAlignmentX(param1Container);
/*     */     }
/*     */     
/*     */     public float getLayoutAlignmentY(Container param1Container) {
/* 791 */       return this.lm.getLayoutAlignmentY(param1Container);
/*     */     }
/*     */     
/*     */     public void invalidateLayout(Container param1Container) {
/* 795 */       this.lm.invalidateLayout(param1Container);
/*     */     }
/*     */     
/*     */     public void propertyChange(PropertyChangeEvent param1PropertyChangeEvent) {
/* 799 */       String str = param1PropertyChangeEvent.getPropertyName();
/* 800 */       if (str.equals("orientation")) {
/* 801 */         int i = ((Integer)param1PropertyChangeEvent.getNewValue()).intValue();
/*     */         
/* 803 */         if (i == 1) {
/* 804 */           this.lm = new BoxLayout(JToolBar.this, 3);
/*     */         } else {
/* 806 */           this.lm = new BoxLayout(JToolBar.this, 2);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLayout(LayoutManager paramLayoutManager) {
/* 814 */     LayoutManager layoutManager = getLayout();
/* 815 */     if (layoutManager instanceof PropertyChangeListener) {
/* 816 */       removePropertyChangeListener((PropertyChangeListener)layoutManager);
/*     */     }
/* 818 */     super.setLayout(paramLayoutManager);
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 835 */     if (this.accessibleContext == null) {
/* 836 */       this.accessibleContext = new AccessibleJToolBar();
/*     */     }
/* 838 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJToolBar
/*     */     extends JComponent.AccessibleJComponent
/*     */   {
/*     */     public AccessibleStateSet getAccessibleStateSet() {
/* 856 */       return super.getAccessibleStateSet();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 868 */       return AccessibleRole.TOOL_BAR;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JToolBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */