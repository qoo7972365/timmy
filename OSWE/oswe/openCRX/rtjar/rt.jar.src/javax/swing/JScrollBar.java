/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.Adjustable;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.accessibility.Accessible;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import javax.accessibility.AccessibleValue;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.plaf.ScrollBarUI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JScrollBar
/*     */   extends JComponent
/*     */   implements Adjustable, Accessible
/*     */ {
/*     */   private static final String uiClassID = "ScrollBarUI";
/*  93 */   private ChangeListener fwdAdjustmentEvents = new ModelListener();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BoundedRangeModel model;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int orientation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int unitIncrement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int blockIncrement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkOrientation(int paramInt) {
/* 123 */     switch (paramInt) {
/*     */       case 0:
/*     */       case 1:
/*     */         return;
/*     */     } 
/* 128 */     throw new IllegalArgumentException("orientation must be one of: VERTICAL, HORIZONTAL");
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
/*     */   public JScrollBar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 154 */     checkOrientation(paramInt1);
/* 155 */     this.unitIncrement = 1;
/* 156 */     this.blockIncrement = (paramInt3 == 0) ? 1 : paramInt3;
/* 157 */     this.orientation = paramInt1;
/* 158 */     this.model = new DefaultBoundedRangeModel(paramInt2, paramInt3, paramInt4, paramInt5);
/* 159 */     this.model.addChangeListener(this.fwdAdjustmentEvents);
/* 160 */     setRequestFocusEnabled(false);
/* 161 */     updateUI();
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
/*     */   public JScrollBar(int paramInt) {
/* 176 */     this(paramInt, 0, 10, 0, 100);
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
/*     */   public JScrollBar() {
/* 190 */     this(1);
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
/*     */   public void setUI(ScrollBarUI paramScrollBarUI) {
/* 207 */     setUI(paramScrollBarUI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScrollBarUI getUI() {
/* 218 */     return (ScrollBarUI)this.ui;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateUI() {
/* 227 */     setUI((ScrollBarUI)UIManager.getUI(this));
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
/*     */   public String getUIClassID() {
/* 239 */     return "ScrollBarUI";
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
/* 252 */     return this.orientation;
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
/*     */   public void setOrientation(int paramInt) {
/* 272 */     checkOrientation(paramInt);
/* 273 */     int i = this.orientation;
/* 274 */     this.orientation = paramInt;
/* 275 */     firePropertyChange("orientation", i, paramInt);
/*     */     
/* 277 */     if (i != paramInt && this.accessibleContext != null) {
/* 278 */       this.accessibleContext.firePropertyChange("AccessibleState", (i == 1) ? AccessibleState.VERTICAL : AccessibleState.HORIZONTAL, (paramInt == 1) ? AccessibleState.VERTICAL : AccessibleState.HORIZONTAL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     if (paramInt != i) {
/* 286 */       revalidate();
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
/*     */   public BoundedRangeModel getModel() {
/* 298 */     return this.model;
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
/*     */   public void setModel(BoundedRangeModel paramBoundedRangeModel) {
/* 313 */     Integer integer = null;
/* 314 */     BoundedRangeModel boundedRangeModel = this.model;
/* 315 */     if (this.model != null) {
/* 316 */       this.model.removeChangeListener(this.fwdAdjustmentEvents);
/* 317 */       integer = Integer.valueOf(this.model.getValue());
/*     */     } 
/* 319 */     this.model = paramBoundedRangeModel;
/* 320 */     if (this.model != null) {
/* 321 */       this.model.addChangeListener(this.fwdAdjustmentEvents);
/*     */     }
/*     */     
/* 324 */     firePropertyChange("model", boundedRangeModel, this.model);
/*     */     
/* 326 */     if (this.accessibleContext != null) {
/* 327 */       this.accessibleContext.firePropertyChange("AccessibleValue", integer, new Integer(this.model
/*     */             
/* 329 */             .getValue()));
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
/*     */   public int getUnitIncrement(int paramInt) {
/* 358 */     return this.unitIncrement;
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
/*     */   public void setUnitIncrement(int paramInt) {
/* 378 */     int i = this.unitIncrement;
/* 379 */     this.unitIncrement = paramInt;
/* 380 */     firePropertyChange("unitIncrement", i, paramInt);
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
/*     */   public int getBlockIncrement(int paramInt) {
/* 408 */     return this.blockIncrement;
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
/*     */   public void setBlockIncrement(int paramInt) {
/* 428 */     int i = this.blockIncrement;
/* 429 */     this.blockIncrement = paramInt;
/* 430 */     firePropertyChange("blockIncrement", i, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUnitIncrement() {
/* 440 */     return this.unitIncrement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlockIncrement() {
/* 450 */     return this.blockIncrement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 460 */     return getModel().getValue();
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
/*     */   public void setValue(int paramInt) {
/* 475 */     BoundedRangeModel boundedRangeModel = getModel();
/* 476 */     int i = boundedRangeModel.getValue();
/* 477 */     boundedRangeModel.setValue(paramInt);
/*     */     
/* 479 */     if (this.accessibleContext != null) {
/* 480 */       this.accessibleContext.firePropertyChange("AccessibleValue", 
/*     */           
/* 482 */           Integer.valueOf(i), 
/* 483 */           Integer.valueOf(boundedRangeModel.getValue()));
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
/*     */   public int getVisibleAmount() {
/* 497 */     return getModel().getExtent();
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
/*     */   public void setVisibleAmount(int paramInt) {
/* 511 */     getModel().setExtent(paramInt);
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
/*     */   public int getMinimum() {
/* 523 */     return getModel().getMinimum();
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
/*     */   public void setMinimum(int paramInt) {
/* 537 */     getModel().setMinimum(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximum() {
/* 548 */     return getModel().getMaximum();
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
/*     */   public void setMaximum(int paramInt) {
/* 563 */     getModel().setMaximum(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getValueIsAdjusting() {
/* 574 */     return getModel().getValueIsAdjusting();
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
/*     */   public void setValueIsAdjusting(boolean paramBoolean) {
/* 592 */     BoundedRangeModel boundedRangeModel = getModel();
/* 593 */     boolean bool = boundedRangeModel.getValueIsAdjusting();
/* 594 */     boundedRangeModel.setValueIsAdjusting(paramBoolean);
/*     */     
/* 596 */     if (bool != paramBoolean && this.accessibleContext != null) {
/* 597 */       this.accessibleContext.firePropertyChange("AccessibleState", bool ? AccessibleState.BUSY : null, paramBoolean ? AccessibleState.BUSY : null);
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
/*     */   public void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 621 */     BoundedRangeModel boundedRangeModel = getModel();
/* 622 */     int i = boundedRangeModel.getValue();
/* 623 */     boundedRangeModel.setRangeProperties(paramInt1, paramInt2, paramInt3, paramInt4, boundedRangeModel.getValueIsAdjusting());
/*     */     
/* 625 */     if (this.accessibleContext != null) {
/* 626 */       this.accessibleContext.firePropertyChange("AccessibleValue", 
/*     */           
/* 628 */           Integer.valueOf(i), 
/* 629 */           Integer.valueOf(boundedRangeModel.getValue()));
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
/*     */   public void addAdjustmentListener(AdjustmentListener paramAdjustmentListener) {
/* 652 */     this.listenerList.add(AdjustmentListener.class, paramAdjustmentListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAdjustmentListener(AdjustmentListener paramAdjustmentListener) {
/* 663 */     this.listenerList.remove(AdjustmentListener.class, paramAdjustmentListener);
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
/*     */   public AdjustmentListener[] getAdjustmentListeners() {
/* 676 */     return this.listenerList.<AdjustmentListener>getListeners(AdjustmentListener.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireAdjustmentValueChanged(int paramInt1, int paramInt2, int paramInt3) {
/* 687 */     fireAdjustmentValueChanged(paramInt1, paramInt2, paramInt3, getValueIsAdjusting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireAdjustmentValueChanged(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 698 */     Object[] arrayOfObject = this.listenerList.getListenerList();
/* 699 */     AdjustmentEvent adjustmentEvent = null;
/* 700 */     for (int i = arrayOfObject.length - 2; i >= 0; i -= 2) {
/* 701 */       if (arrayOfObject[i] == AdjustmentListener.class) {
/* 702 */         if (adjustmentEvent == null) {
/* 703 */           adjustmentEvent = new AdjustmentEvent(this, paramInt1, paramInt2, paramInt3, paramBoolean);
/*     */         }
/* 705 */         ((AdjustmentListener)arrayOfObject[i + 1]).adjustmentValueChanged(adjustmentEvent);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ModelListener
/*     */     implements ChangeListener, Serializable
/*     */   {
/*     */     private ModelListener() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void stateChanged(ChangeEvent param1ChangeEvent) {
/* 720 */       Object object = param1ChangeEvent.getSource();
/* 721 */       if (object instanceof BoundedRangeModel) {
/* 722 */         char c = 'ə';
/* 723 */         byte b = 5;
/* 724 */         BoundedRangeModel boundedRangeModel = (BoundedRangeModel)object;
/* 725 */         int i = boundedRangeModel.getValue();
/* 726 */         boolean bool = boundedRangeModel.getValueIsAdjusting();
/* 727 */         JScrollBar.this.fireAdjustmentValueChanged(c, b, i, bool);
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
/*     */   public Dimension getMinimumSize() {
/* 739 */     Dimension dimension = getPreferredSize();
/* 740 */     if (this.orientation == 1) {
/* 741 */       return new Dimension(dimension.width, 5);
/*     */     }
/* 743 */     return new Dimension(5, dimension.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMaximumSize() {
/* 752 */     Dimension dimension = getPreferredSize();
/* 753 */     if (getOrientation() == 1) {
/* 754 */       return new Dimension(dimension.width, 32767);
/*     */     }
/* 756 */     return new Dimension(32767, dimension.height);
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
/*     */   public void setEnabled(boolean paramBoolean) {
/* 768 */     super.setEnabled(paramBoolean);
/* 769 */     Component[] arrayOfComponent = getComponents();
/* 770 */     for (Component component : arrayOfComponent) {
/* 771 */       component.setEnabled(paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 780 */     paramObjectOutputStream.defaultWriteObject();
/* 781 */     if (getUIClassID().equals("ScrollBarUI")) {
/* 782 */       byte b = JComponent.getWriteObjCounter(this);
/* 783 */       b = (byte)(b - 1); JComponent.setWriteObjCounter(this, b);
/* 784 */       if (b == 0 && this.ui != null) {
/* 785 */         this.ui.installUI(this);
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
/*     */   protected String paramString() {
/* 801 */     String str = (this.orientation == 0) ? "HORIZONTAL" : "VERTICAL";
/*     */ 
/*     */     
/* 804 */     return super.paramString() + ",blockIncrement=" + this.blockIncrement + ",orientation=" + str + ",unitIncrement=" + this.unitIncrement;
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
/*     */   public AccessibleContext getAccessibleContext() {
/* 824 */     if (this.accessibleContext == null) {
/* 825 */       this.accessibleContext = new AccessibleJScrollBar();
/*     */     }
/* 827 */     return this.accessibleContext;
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
/*     */   protected class AccessibleJScrollBar
/*     */     extends JComponent.AccessibleJComponent
/*     */     implements AccessibleValue
/*     */   {
/*     */     public AccessibleStateSet getAccessibleStateSet() {
/* 856 */       AccessibleStateSet accessibleStateSet = super.getAccessibleStateSet();
/* 857 */       if (JScrollBar.this.getValueIsAdjusting()) {
/* 858 */         accessibleStateSet.add(AccessibleState.BUSY);
/*     */       }
/* 860 */       if (JScrollBar.this.getOrientation() == 1) {
/* 861 */         accessibleStateSet.add(AccessibleState.VERTICAL);
/*     */       } else {
/* 863 */         accessibleStateSet.add(AccessibleState.HORIZONTAL);
/*     */       } 
/* 865 */       return accessibleStateSet;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AccessibleRole getAccessibleRole() {
/* 875 */       return AccessibleRole.SCROLL_BAR;
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
/*     */     public AccessibleValue getAccessibleValue() {
/* 887 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getCurrentAccessibleValue() {
/* 896 */       return Integer.valueOf(JScrollBar.this.getValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean setCurrentAccessibleValue(Number param1Number) {
/* 906 */       if (param1Number == null) {
/* 907 */         return false;
/*     */       }
/* 909 */       JScrollBar.this.setValue(param1Number.intValue());
/* 910 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMinimumAccessibleValue() {
/* 919 */       return Integer.valueOf(JScrollBar.this.getMinimum());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Number getMaximumAccessibleValue() {
/* 929 */       return new Integer(JScrollBar.this.model.getMaximum() - JScrollBar.this.model.getExtent());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/JScrollBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */