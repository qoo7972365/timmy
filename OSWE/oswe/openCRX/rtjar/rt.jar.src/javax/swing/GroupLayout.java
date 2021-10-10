/*      */ package javax.swing;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.Container;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Insets;
/*      */ import java.awt.LayoutManager2;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GroupLayout
/*      */   implements LayoutManager2
/*      */ {
/*      */   private static final int MIN_SIZE = 0;
/*      */   private static final int PREF_SIZE = 1;
/*      */   private static final int MAX_SIZE = 2;
/*      */   private static final int SPECIFIC_SIZE = 3;
/*      */   private static final int UNSET = -2147483648;
/*      */   public static final int DEFAULT_SIZE = -1;
/*      */   public static final int PREFERRED_SIZE = -2;
/*      */   private boolean autocreatePadding;
/*      */   private boolean autocreateContainerPadding;
/*      */   private Group horizontalGroup;
/*      */   private Group verticalGroup;
/*      */   private Map<Component, ComponentInfo> componentInfos;
/*      */   private Container host;
/*      */   private Set<Spring> tmpParallelSet;
/*      */   private boolean springsChanged;
/*      */   private boolean isValid;
/*      */   private boolean hasPreferredPaddingSprings;
/*      */   private LayoutStyle layoutStyle;
/*      */   private boolean honorsVisibility;
/*      */   
/*      */   public enum Alignment
/*      */   {
/*  306 */     LEADING,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  316 */     TRAILING,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  324 */     CENTER,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  333 */     BASELINE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkSize(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/*  339 */     checkResizeType(paramInt1, paramBoolean);
/*  340 */     if (!paramBoolean && paramInt2 < 0)
/*  341 */       throw new IllegalArgumentException("Pref must be >= 0"); 
/*  342 */     if (paramBoolean) {
/*  343 */       checkResizeType(paramInt2, true);
/*      */     }
/*  345 */     checkResizeType(paramInt3, paramBoolean);
/*  346 */     checkLessThan(paramInt1, paramInt2);
/*  347 */     checkLessThan(paramInt2, paramInt3);
/*      */   }
/*      */   
/*      */   private static void checkResizeType(int paramInt, boolean paramBoolean) {
/*  351 */     if (paramInt < 0 && ((paramBoolean && paramInt != -1 && paramInt != -2) || (!paramBoolean && paramInt != -2)))
/*      */     {
/*      */       
/*  354 */       throw new IllegalArgumentException("Invalid size");
/*      */     }
/*      */   }
/*      */   
/*      */   private static void checkLessThan(int paramInt1, int paramInt2) {
/*  359 */     if (paramInt1 >= 0 && paramInt2 >= 0 && paramInt1 > paramInt2) {
/*  360 */       throw new IllegalArgumentException("Following is not met: min<=pref<=max");
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
/*      */   public GroupLayout(Container paramContainer) {
/*  373 */     if (paramContainer == null) {
/*  374 */       throw new IllegalArgumentException("Container must be non-null");
/*      */     }
/*  376 */     this.honorsVisibility = true;
/*  377 */     this.host = paramContainer;
/*  378 */     setHorizontalGroup(createParallelGroup(Alignment.LEADING, true));
/*  379 */     setVerticalGroup(createParallelGroup(Alignment.LEADING, true));
/*  380 */     this.componentInfos = new HashMap<>();
/*  381 */     this.tmpParallelSet = new HashSet<>();
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
/*      */   public void setHonorsVisibility(boolean paramBoolean) {
/*  405 */     if (this.honorsVisibility != paramBoolean) {
/*  406 */       this.honorsVisibility = paramBoolean;
/*  407 */       this.springsChanged = true;
/*  408 */       this.isValid = false;
/*  409 */       invalidateHost();
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
/*      */   public boolean getHonorsVisibility() {
/*  421 */     return this.honorsVisibility;
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
/*      */   public void setHonorsVisibility(Component paramComponent, Boolean paramBoolean) {
/*  446 */     if (paramComponent == null) {
/*  447 */       throw new IllegalArgumentException("Component must be non-null");
/*      */     }
/*  449 */     getComponentInfo(paramComponent).setHonorsVisibility(paramBoolean);
/*  450 */     this.springsChanged = true;
/*  451 */     this.isValid = false;
/*  452 */     invalidateHost();
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
/*      */   public void setAutoCreateGaps(boolean paramBoolean) {
/*  466 */     if (this.autocreatePadding != paramBoolean) {
/*  467 */       this.autocreatePadding = paramBoolean;
/*  468 */       invalidateHost();
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
/*      */   public boolean getAutoCreateGaps() {
/*  480 */     return this.autocreatePadding;
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
/*      */   public void setAutoCreateContainerGaps(boolean paramBoolean) {
/*  493 */     if (this.autocreateContainerPadding != paramBoolean) {
/*  494 */       this.autocreateContainerPadding = paramBoolean;
/*  495 */       this.horizontalGroup = createTopLevelGroup(getHorizontalGroup());
/*  496 */       this.verticalGroup = createTopLevelGroup(getVerticalGroup());
/*  497 */       invalidateHost();
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
/*      */   public boolean getAutoCreateContainerGaps() {
/*  509 */     return this.autocreateContainerPadding;
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
/*      */   public void setHorizontalGroup(Group paramGroup) {
/*  521 */     if (paramGroup == null) {
/*  522 */       throw new IllegalArgumentException("Group must be non-null");
/*      */     }
/*  524 */     this.horizontalGroup = createTopLevelGroup(paramGroup);
/*  525 */     invalidateHost();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Group getHorizontalGroup() {
/*  536 */     boolean bool = false;
/*  537 */     if (this.horizontalGroup.springs.size() > 1) {
/*  538 */       bool = true;
/*      */     }
/*  540 */     return (Group)this.horizontalGroup.springs.get(bool);
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
/*      */   public void setVerticalGroup(Group paramGroup) {
/*  552 */     if (paramGroup == null) {
/*  553 */       throw new IllegalArgumentException("Group must be non-null");
/*      */     }
/*  555 */     this.verticalGroup = createTopLevelGroup(paramGroup);
/*  556 */     invalidateHost();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Group getVerticalGroup() {
/*  567 */     boolean bool = false;
/*  568 */     if (this.verticalGroup.springs.size() > 1) {
/*  569 */       bool = true;
/*      */     }
/*  571 */     return (Group)this.verticalGroup.springs.get(bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Group createTopLevelGroup(Group paramGroup) {
/*  580 */     SequentialGroup sequentialGroup = createSequentialGroup();
/*  581 */     if (getAutoCreateContainerGaps()) {
/*  582 */       sequentialGroup.addSpring(new ContainerAutoPreferredGapSpring());
/*  583 */       sequentialGroup.addGroup(paramGroup);
/*  584 */       sequentialGroup.addSpring(new ContainerAutoPreferredGapSpring());
/*      */     } else {
/*  586 */       sequentialGroup.addGroup(paramGroup);
/*      */     } 
/*  588 */     return sequentialGroup;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SequentialGroup createSequentialGroup() {
/*  597 */     return new SequentialGroup();
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
/*      */   public ParallelGroup createParallelGroup() {
/*  609 */     return createParallelGroup(Alignment.LEADING);
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
/*      */   public ParallelGroup createParallelGroup(Alignment paramAlignment) {
/*  625 */     return createParallelGroup(paramAlignment, true);
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
/*      */   public ParallelGroup createParallelGroup(Alignment paramAlignment, boolean paramBoolean) {
/*  656 */     if (paramAlignment == null) {
/*  657 */       throw new IllegalArgumentException("alignment must be non null");
/*      */     }
/*      */     
/*  660 */     if (paramAlignment == Alignment.BASELINE) {
/*  661 */       return new BaselineGroup(paramBoolean);
/*      */     }
/*  663 */     return new ParallelGroup(paramAlignment, paramBoolean);
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
/*      */   public ParallelGroup createBaselineGroup(boolean paramBoolean1, boolean paramBoolean2) {
/*  678 */     return new BaselineGroup(paramBoolean1, paramBoolean2);
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
/*      */   public void linkSize(Component... paramVarArgs) {
/*  699 */     linkSize(0, paramVarArgs);
/*  700 */     linkSize(1, paramVarArgs);
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
/*      */   public void linkSize(int paramInt, Component... paramVarArgs) {
/*  727 */     if (paramVarArgs == null)
/*  728 */       throw new IllegalArgumentException("Components must be non-null"); 
/*      */     int i;
/*  730 */     for (i = paramVarArgs.length - 1; i >= 0; i--) {
/*  731 */       Component component = paramVarArgs[i];
/*  732 */       if (paramVarArgs[i] == null) {
/*  733 */         throw new IllegalArgumentException("Components must be non-null");
/*      */       }
/*      */ 
/*      */       
/*  737 */       getComponentInfo(component);
/*      */     } 
/*      */     
/*  740 */     if (paramInt == 0) {
/*  741 */       i = 0;
/*  742 */     } else if (paramInt == 1) {
/*  743 */       i = 1;
/*      */     } else {
/*  745 */       throw new IllegalArgumentException("Axis must be one of SwingConstants.HORIZONTAL or SwingConstants.VERTICAL");
/*      */     } 
/*      */ 
/*      */     
/*  749 */     LinkInfo linkInfo = getComponentInfo(paramVarArgs[paramVarArgs.length - 1]).getLinkInfo(i);
/*  750 */     for (int j = paramVarArgs.length - 2; j >= 0; j--) {
/*  751 */       linkInfo.add(getComponentInfo(paramVarArgs[j]));
/*      */     }
/*  753 */     invalidateHost();
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
/*      */   public void replace(Component paramComponent1, Component paramComponent2) {
/*  768 */     if (paramComponent1 == null || paramComponent2 == null) {
/*  769 */       throw new IllegalArgumentException("Components must be non-null");
/*      */     }
/*      */ 
/*      */     
/*  773 */     if (this.springsChanged) {
/*  774 */       registerComponents(this.horizontalGroup, 0);
/*  775 */       registerComponents(this.verticalGroup, 1);
/*      */     } 
/*  777 */     ComponentInfo componentInfo = this.componentInfos.remove(paramComponent1);
/*  778 */     if (componentInfo == null) {
/*  779 */       throw new IllegalArgumentException("Component must already exist");
/*      */     }
/*  781 */     this.host.remove(paramComponent1);
/*  782 */     if (paramComponent2.getParent() != this.host) {
/*  783 */       this.host.add(paramComponent2);
/*      */     }
/*  785 */     componentInfo.setComponent(paramComponent2);
/*  786 */     this.componentInfos.put(paramComponent2, componentInfo);
/*  787 */     invalidateHost();
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
/*      */   public void setLayoutStyle(LayoutStyle paramLayoutStyle) {
/*  799 */     this.layoutStyle = paramLayoutStyle;
/*  800 */     invalidateHost();
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
/*      */   public LayoutStyle getLayoutStyle() {
/*  812 */     return this.layoutStyle;
/*      */   }
/*      */   
/*      */   private LayoutStyle getLayoutStyle0() {
/*  816 */     LayoutStyle layoutStyle = getLayoutStyle();
/*  817 */     if (layoutStyle == null) {
/*  818 */       layoutStyle = LayoutStyle.getInstance();
/*      */     }
/*  820 */     return layoutStyle;
/*      */   }
/*      */   
/*      */   private void invalidateHost() {
/*  824 */     if (this.host instanceof JComponent) {
/*  825 */       ((JComponent)this.host).revalidate();
/*      */     } else {
/*  827 */       this.host.invalidate();
/*      */     } 
/*  829 */     this.host.repaint();
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
/*      */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeLayoutComponent(Component paramComponent) {
/*  857 */     ComponentInfo componentInfo = this.componentInfos.remove(paramComponent);
/*  858 */     if (componentInfo != null) {
/*  859 */       componentInfo.dispose();
/*  860 */       this.springsChanged = true;
/*  861 */       this.isValid = false;
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
/*      */   public Dimension preferredLayoutSize(Container paramContainer) {
/*  877 */     checkParent(paramContainer);
/*  878 */     prepare(1);
/*  879 */     return adjustSize(this.horizontalGroup.getPreferredSize(0), this.verticalGroup
/*  880 */         .getPreferredSize(1));
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
/*      */   public Dimension minimumLayoutSize(Container paramContainer) {
/*  895 */     checkParent(paramContainer);
/*  896 */     prepare(0);
/*  897 */     return adjustSize(this.horizontalGroup.getMinimumSize(0), this.verticalGroup
/*  898 */         .getMinimumSize(1));
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
/*      */   public void layoutContainer(Container paramContainer) {
/*  910 */     prepare(3);
/*  911 */     Insets insets = paramContainer.getInsets();
/*  912 */     int i = paramContainer.getWidth() - insets.left - insets.right;
/*  913 */     int j = paramContainer.getHeight() - insets.top - insets.bottom;
/*  914 */     boolean bool = isLeftToRight();
/*  915 */     if (getAutoCreateGaps() || getAutoCreateContainerGaps() || this.hasPreferredPaddingSprings) {
/*      */ 
/*      */       
/*  918 */       calculateAutopadding(this.horizontalGroup, 0, 3, 0, i);
/*      */       
/*  920 */       calculateAutopadding(this.verticalGroup, 1, 3, 0, j);
/*      */     } 
/*      */ 
/*      */     
/*  924 */     this.horizontalGroup.setSize(0, 0, i);
/*  925 */     this.verticalGroup.setSize(1, 0, j);
/*      */     
/*  927 */     for (ComponentInfo componentInfo : this.componentInfos.values()) {
/*  928 */       componentInfo.setBounds(insets, i, bool);
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
/*      */   public void addLayoutComponent(Component paramComponent, Object paramObject) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Dimension maximumLayoutSize(Container paramContainer) {
/*  959 */     checkParent(paramContainer);
/*  960 */     prepare(2);
/*  961 */     return adjustSize(this.horizontalGroup.getMaximumSize(0), this.verticalGroup
/*  962 */         .getMaximumSize(1));
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
/*      */   public float getLayoutAlignmentX(Container paramContainer) {
/*  978 */     checkParent(paramContainer);
/*  979 */     return 0.5F;
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
/*      */   public float getLayoutAlignmentY(Container paramContainer) {
/*  995 */     checkParent(paramContainer);
/*  996 */     return 0.5F;
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
/*      */   public void invalidateLayout(Container paramContainer) {
/* 1008 */     checkParent(paramContainer);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1013 */     synchronized (paramContainer.getTreeLock()) {
/* 1014 */       this.isValid = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void prepare(int paramInt) {
/* 1019 */     boolean bool = false;
/*      */     
/* 1021 */     if (!this.isValid) {
/* 1022 */       this.isValid = true;
/* 1023 */       this.horizontalGroup.setSize(0, -2147483648, -2147483648);
/* 1024 */       this.verticalGroup.setSize(1, -2147483648, -2147483648);
/* 1025 */       for (ComponentInfo componentInfo : this.componentInfos.values()) {
/* 1026 */         if (componentInfo.updateVisibility()) {
/* 1027 */           bool = true;
/*      */         }
/* 1029 */         componentInfo.clearCachedSize();
/*      */       } 
/*      */     } 
/*      */     
/* 1033 */     if (this.springsChanged) {
/* 1034 */       registerComponents(this.horizontalGroup, 0);
/* 1035 */       registerComponents(this.verticalGroup, 1);
/*      */     } 
/*      */ 
/*      */     
/* 1039 */     if (this.springsChanged || bool) {
/* 1040 */       checkComponents();
/* 1041 */       this.horizontalGroup.removeAutopadding();
/* 1042 */       this.verticalGroup.removeAutopadding();
/* 1043 */       if (getAutoCreateGaps()) {
/* 1044 */         insertAutopadding(true);
/* 1045 */       } else if (this.hasPreferredPaddingSprings || 
/* 1046 */         getAutoCreateContainerGaps()) {
/* 1047 */         insertAutopadding(false);
/*      */       } 
/* 1049 */       this.springsChanged = false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1056 */     if (paramInt != 3 && (getAutoCreateGaps() || 
/* 1057 */       getAutoCreateContainerGaps() || this.hasPreferredPaddingSprings)) {
/* 1058 */       calculateAutopadding(this.horizontalGroup, 0, paramInt, 0, 0);
/* 1059 */       calculateAutopadding(this.verticalGroup, 1, paramInt, 0, 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void calculateAutopadding(Group paramGroup, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1065 */     paramGroup.unsetAutopadding();
/* 1066 */     switch (paramInt2) {
/*      */       case 0:
/* 1068 */         paramInt4 = paramGroup.getMinimumSize(paramInt1);
/*      */         break;
/*      */       case 1:
/* 1071 */         paramInt4 = paramGroup.getPreferredSize(paramInt1);
/*      */         break;
/*      */       case 2:
/* 1074 */         paramInt4 = paramGroup.getMaximumSize(paramInt1);
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1079 */     paramGroup.setSize(paramInt1, paramInt3, paramInt4);
/* 1080 */     paramGroup.calculateAutopadding(paramInt1);
/*      */   }
/*      */   
/*      */   private void checkComponents() {
/* 1084 */     for (ComponentInfo componentInfo : this.componentInfos.values()) {
/* 1085 */       if (componentInfo.horizontalSpring == null) {
/* 1086 */         throw new IllegalStateException(componentInfo.component + " is not attached to a horizontal group");
/*      */       }
/*      */       
/* 1089 */       if (componentInfo.verticalSpring == null) {
/* 1090 */         throw new IllegalStateException(componentInfo.component + " is not attached to a vertical group");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerComponents(Group paramGroup, int paramInt) {
/* 1097 */     List<Spring> list = paramGroup.springs;
/* 1098 */     for (int i = list.size() - 1; i >= 0; i--) {
/* 1099 */       Spring spring = list.get(i);
/* 1100 */       if (spring instanceof ComponentSpring) {
/* 1101 */         ((ComponentSpring)spring).installIfNecessary(paramInt);
/* 1102 */       } else if (spring instanceof Group) {
/* 1103 */         registerComponents((Group)spring, paramInt);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private Dimension adjustSize(int paramInt1, int paramInt2) {
/* 1109 */     Insets insets = this.host.getInsets();
/* 1110 */     return new Dimension(paramInt1 + insets.left + insets.right, paramInt2 + insets.top + insets.bottom);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkParent(Container paramContainer) {
/* 1115 */     if (paramContainer != this.host) {
/* 1116 */       throw new IllegalArgumentException("GroupLayout can only be used with one Container at a time");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ComponentInfo getComponentInfo(Component paramComponent) {
/* 1126 */     ComponentInfo componentInfo = this.componentInfos.get(paramComponent);
/* 1127 */     if (componentInfo == null) {
/* 1128 */       componentInfo = new ComponentInfo(paramComponent);
/* 1129 */       this.componentInfos.put(paramComponent, componentInfo);
/* 1130 */       if (paramComponent.getParent() != this.host) {
/* 1131 */         this.host.add(paramComponent);
/*      */       }
/*      */     } 
/* 1134 */     return componentInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void insertAutopadding(boolean paramBoolean) {
/* 1144 */     this.horizontalGroup.insertAutopadding(0, new ArrayList<>(1), new ArrayList<>(1), new ArrayList<>(1), new ArrayList<>(1), paramBoolean);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     this.verticalGroup.insertAutopadding(1, new ArrayList<>(1), new ArrayList<>(1), new ArrayList<>(1), new ArrayList<>(1), paramBoolean);
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
/*      */   private boolean areParallelSiblings(Component paramComponent1, Component paramComponent2, int paramInt) {
/*      */     ComponentSpring componentSpring1, componentSpring2;
/* 1162 */     ComponentInfo componentInfo1 = getComponentInfo(paramComponent1);
/* 1163 */     ComponentInfo componentInfo2 = getComponentInfo(paramComponent2);
/*      */ 
/*      */     
/* 1166 */     if (paramInt == 0) {
/* 1167 */       componentSpring1 = componentInfo1.horizontalSpring;
/* 1168 */       componentSpring2 = componentInfo2.horizontalSpring;
/*      */     } else {
/* 1170 */       componentSpring1 = componentInfo1.verticalSpring;
/* 1171 */       componentSpring2 = componentInfo2.verticalSpring;
/*      */     } 
/* 1173 */     Set<Spring> set = this.tmpParallelSet;
/* 1174 */     set.clear();
/* 1175 */     Spring spring = componentSpring1.getParent();
/* 1176 */     while (spring != null) {
/* 1177 */       set.add(spring);
/* 1178 */       spring = spring.getParent();
/*      */     } 
/* 1180 */     spring = componentSpring2.getParent();
/* 1181 */     while (spring != null) {
/* 1182 */       if (set.contains(spring)) {
/* 1183 */         set.clear();
/* 1184 */         while (spring != null) {
/* 1185 */           if (spring instanceof ParallelGroup) {
/* 1186 */             return true;
/*      */           }
/* 1188 */           spring = spring.getParent();
/*      */         } 
/* 1190 */         return false;
/*      */       } 
/* 1192 */       spring = spring.getParent();
/*      */     } 
/* 1194 */     set.clear();
/* 1195 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isLeftToRight() {
/* 1199 */     return this.host.getComponentOrientation().isLeftToRight();
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
/*      */   public String toString() {
/* 1211 */     if (this.springsChanged) {
/* 1212 */       registerComponents(this.horizontalGroup, 0);
/* 1213 */       registerComponents(this.verticalGroup, 1);
/*      */     } 
/* 1215 */     StringBuffer stringBuffer = new StringBuffer();
/* 1216 */     stringBuffer.append("HORIZONTAL\n");
/* 1217 */     createSpringDescription(stringBuffer, this.horizontalGroup, "  ", 0);
/* 1218 */     stringBuffer.append("\nVERTICAL\n");
/* 1219 */     createSpringDescription(stringBuffer, this.verticalGroup, "  ", 1);
/* 1220 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private void createSpringDescription(StringBuffer paramStringBuffer, Spring paramSpring, String paramString, int paramInt) {
/* 1225 */     String str1 = "";
/* 1226 */     String str2 = "";
/* 1227 */     if (paramSpring instanceof ComponentSpring) {
/* 1228 */       ComponentSpring componentSpring = (ComponentSpring)paramSpring;
/* 1229 */       str1 = Integer.toString(componentSpring.getOrigin()) + " ";
/* 1230 */       String str = componentSpring.getComponent().getName();
/* 1231 */       if (str != null) {
/* 1232 */         str1 = "name=" + str + ", ";
/*      */       }
/*      */     } 
/* 1235 */     if (paramSpring instanceof AutoPreferredGapSpring) {
/* 1236 */       AutoPreferredGapSpring autoPreferredGapSpring = (AutoPreferredGapSpring)paramSpring;
/*      */ 
/*      */       
/* 1239 */       str2 = ", userCreated=" + autoPreferredGapSpring.getUserCreated() + ", matches=" + autoPreferredGapSpring.getMatchDescription();
/*      */     } 
/* 1241 */     paramStringBuffer.append(paramString + paramSpring.getClass().getName() + " " + 
/* 1242 */         Integer.toHexString(paramSpring.hashCode()) + " " + str1 + ", size=" + paramSpring
/*      */         
/* 1244 */         .getSize() + ", alignment=" + paramSpring
/* 1245 */         .getAlignment() + " prefs=[" + paramSpring
/* 1246 */         .getMinimumSize(paramInt) + " " + paramSpring
/* 1247 */         .getPreferredSize(paramInt) + " " + paramSpring
/* 1248 */         .getMaximumSize(paramInt) + str2 + "]\n");
/*      */     
/* 1250 */     if (paramSpring instanceof Group) {
/* 1251 */       List<Spring> list = ((Group)paramSpring).springs;
/* 1252 */       paramString = paramString + "  ";
/* 1253 */       for (byte b = 0; b < list.size(); b++) {
/* 1254 */         createSpringDescription(paramStringBuffer, list.get(b), paramString, paramInt);
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
/*      */   private abstract class Spring
/*      */   {
/*      */     private int size;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1277 */     private int min = this.pref = this.max = Integer.MIN_VALUE;
/*      */ 
/*      */ 
/*      */     
/*      */     private int max;
/*      */ 
/*      */ 
/*      */     
/*      */     private int pref;
/*      */ 
/*      */     
/*      */     private Spring parent;
/*      */ 
/*      */     
/*      */     private GroupLayout.Alignment alignment;
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int calculateMinimumSize(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int calculatePreferredSize(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     abstract int calculateMaximumSize(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     void setParent(Spring param1Spring) {
/* 1308 */       this.parent = param1Spring;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Spring getParent() {
/* 1315 */       return this.parent;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void setAlignment(GroupLayout.Alignment param1Alignment) {
/* 1321 */       this.alignment = param1Alignment;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     GroupLayout.Alignment getAlignment() {
/* 1328 */       return this.alignment;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int getMinimumSize(int param1Int) {
/* 1335 */       if (this.min == Integer.MIN_VALUE) {
/* 1336 */         this.min = constrain(calculateMinimumSize(param1Int));
/*      */       }
/* 1338 */       return this.min;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int getPreferredSize(int param1Int) {
/* 1345 */       if (this.pref == Integer.MIN_VALUE) {
/* 1346 */         this.pref = constrain(calculatePreferredSize(param1Int));
/*      */       }
/* 1348 */       return this.pref;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int getMaximumSize(int param1Int) {
/* 1355 */       if (this.max == Integer.MIN_VALUE) {
/* 1356 */         this.max = constrain(calculateMaximumSize(param1Int));
/*      */       }
/* 1358 */       return this.max;
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
/*      */     void setSize(int param1Int1, int param1Int2, int param1Int3) {
/* 1371 */       this.size = param1Int3;
/* 1372 */       if (param1Int3 == Integer.MIN_VALUE) {
/* 1373 */         unset();
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void unset() {
/* 1381 */       this.size = this.min = this.pref = this.max = Integer.MIN_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getSize() {
/* 1388 */       return this.size;
/*      */     }
/*      */     
/*      */     int constrain(int param1Int) {
/* 1392 */       return Math.min(param1Int, 32767);
/*      */     }
/*      */     
/*      */     int getBaseline() {
/* 1396 */       return -1;
/*      */     }
/*      */     
/*      */     Component.BaselineResizeBehavior getBaselineResizeBehavior() {
/* 1400 */       return Component.BaselineResizeBehavior.OTHER;
/*      */     }
/*      */     
/*      */     final boolean isResizable(int param1Int) {
/* 1404 */       int i = getMinimumSize(param1Int);
/* 1405 */       int j = getPreferredSize(param1Int);
/* 1406 */       return (i != j || j != getMaximumSize(param1Int));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract boolean willHaveZeroSize(boolean param1Boolean);
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
/*      */   public abstract class Group
/*      */     extends Spring
/*      */   {
/*      */     List<GroupLayout.Spring> springs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Group() {
/* 1485 */       this.springs = new ArrayList<>();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Group addGroup(Group param1Group) {
/* 1495 */       return addSpring(param1Group);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Group addComponent(Component param1Component) {
/* 1505 */       return addComponent(param1Component, -1, -1, -1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Group addComponent(Component param1Component, int param1Int1, int param1Int2, int param1Int3) {
/* 1524 */       return addSpring(new GroupLayout.ComponentSpring(param1Component, param1Int1, param1Int2, param1Int3));
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
/*      */     public Group addGap(int param1Int) {
/* 1536 */       return addGap(param1Int, param1Int, param1Int);
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
/*      */     public Group addGap(int param1Int1, int param1Int2, int param1Int3) {
/* 1550 */       return addSpring(new GroupLayout.GapSpring(param1Int1, param1Int2, param1Int3));
/*      */     }
/*      */     
/*      */     GroupLayout.Spring getSpring(int param1Int) {
/* 1554 */       return this.springs.get(param1Int);
/*      */     }
/*      */     
/*      */     int indexOf(GroupLayout.Spring param1Spring) {
/* 1558 */       return this.springs.indexOf(param1Spring);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Group addSpring(GroupLayout.Spring param1Spring) {
/* 1566 */       this.springs.add(param1Spring);
/* 1567 */       param1Spring.setParent(this);
/* 1568 */       if (!(param1Spring instanceof GroupLayout.AutoPreferredGapSpring) || 
/* 1569 */         !((GroupLayout.AutoPreferredGapSpring)param1Spring).getUserCreated()) {
/* 1570 */         GroupLayout.this.springsChanged = true;
/*      */       }
/* 1572 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setSize(int param1Int1, int param1Int2, int param1Int3) {
/* 1580 */       super.setSize(param1Int1, param1Int2, param1Int3);
/* 1581 */       if (param1Int3 == Integer.MIN_VALUE) {
/* 1582 */         for (int i = this.springs.size() - 1; i >= 0; 
/* 1583 */           i--) {
/* 1584 */           getSpring(i).setSize(param1Int1, param1Int2, param1Int3);
/*      */         }
/*      */       } else {
/* 1587 */         setValidSize(param1Int1, param1Int2, param1Int3);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void setValidSize(int param1Int1, int param1Int2, int param1Int3);
/*      */ 
/*      */ 
/*      */     
/*      */     int calculateMinimumSize(int param1Int) {
/* 1598 */       return calculateSize(param1Int, 0);
/*      */     }
/*      */     
/*      */     int calculatePreferredSize(int param1Int) {
/* 1602 */       return calculateSize(param1Int, 1);
/*      */     }
/*      */     
/*      */     int calculateMaximumSize(int param1Int) {
/* 1606 */       return calculateSize(param1Int, 2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int calculateSize(int param1Int1, int param1Int2) {
/* 1617 */       int i = this.springs.size();
/* 1618 */       if (i == 0) {
/* 1619 */         return 0;
/*      */       }
/* 1621 */       if (i == 1) {
/* 1622 */         return getSpringSize(getSpring(0), param1Int1, param1Int2);
/*      */       }
/* 1624 */       int j = constrain(operator(getSpringSize(getSpring(0), param1Int1, param1Int2), 
/* 1625 */             getSpringSize(getSpring(1), param1Int1, param1Int2)));
/* 1626 */       for (byte b = 2; b < i; b++) {
/* 1627 */         j = constrain(operator(j, getSpringSize(
/* 1628 */                 getSpring(b), param1Int1, param1Int2)));
/*      */       }
/* 1630 */       return j;
/*      */     }
/*      */     
/*      */     int getSpringSize(GroupLayout.Spring param1Spring, int param1Int1, int param1Int2) {
/* 1634 */       switch (param1Int2) {
/*      */         case 0:
/* 1636 */           return param1Spring.getMinimumSize(param1Int1);
/*      */         case 1:
/* 1638 */           return param1Spring.getPreferredSize(param1Int1);
/*      */         case 2:
/* 1640 */           return param1Spring.getMaximumSize(param1Int1);
/*      */       } 
/*      */       assert false;
/* 1643 */       return 0;
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
/*      */     
/*      */     abstract int operator(int param1Int1, int param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void insertAutopadding(int param1Int, List<GroupLayout.AutoPreferredGapSpring> param1List1, List<GroupLayout.AutoPreferredGapSpring> param1List2, List<GroupLayout.ComponentSpring> param1List3, List<GroupLayout.ComponentSpring> param1List4, boolean param1Boolean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void removeAutopadding() {
/* 1684 */       unset();
/* 1685 */       for (int i = this.springs.size() - 1; i >= 0; i--) {
/* 1686 */         GroupLayout.Spring spring = this.springs.get(i);
/* 1687 */         if (spring instanceof GroupLayout.AutoPreferredGapSpring) {
/* 1688 */           if (((GroupLayout.AutoPreferredGapSpring)spring).getUserCreated()) {
/* 1689 */             ((GroupLayout.AutoPreferredGapSpring)spring).reset();
/*      */           } else {
/* 1691 */             this.springs.remove(i);
/*      */           } 
/* 1693 */         } else if (spring instanceof Group) {
/* 1694 */           ((Group)spring).removeAutopadding();
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     void unsetAutopadding() {
/* 1701 */       unset();
/* 1702 */       for (int i = this.springs.size() - 1; i >= 0; i--) {
/* 1703 */         GroupLayout.Spring spring = this.springs.get(i);
/* 1704 */         if (spring instanceof GroupLayout.AutoPreferredGapSpring) {
/* 1705 */           spring.unset();
/* 1706 */         } else if (spring instanceof Group) {
/* 1707 */           ((Group)spring).unsetAutopadding();
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     void calculateAutopadding(int param1Int) {
/* 1713 */       for (int i = this.springs.size() - 1; i >= 0; i--) {
/* 1714 */         GroupLayout.Spring spring = this.springs.get(i);
/* 1715 */         if (spring instanceof GroupLayout.AutoPreferredGapSpring) {
/*      */           
/* 1717 */           spring.unset();
/* 1718 */           ((GroupLayout.AutoPreferredGapSpring)spring).calculatePadding(param1Int);
/* 1719 */         } else if (spring instanceof Group) {
/* 1720 */           ((Group)spring).calculateAutopadding(param1Int);
/*      */         } 
/*      */       } 
/*      */       
/* 1724 */       unset();
/*      */     }
/*      */ 
/*      */     
/*      */     boolean willHaveZeroSize(boolean param1Boolean) {
/* 1729 */       for (int i = this.springs.size() - 1; i >= 0; i--) {
/* 1730 */         GroupLayout.Spring spring = this.springs.get(i);
/* 1731 */         if (!spring.willHaveZeroSize(param1Boolean)) {
/* 1732 */           return false;
/*      */         }
/*      */       } 
/* 1735 */       return true;
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
/*      */   public class SequentialGroup
/*      */     extends Group
/*      */   {
/*      */     private GroupLayout.Spring baselineSpring;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addGroup(GroupLayout.Group param1Group) {
/* 1768 */       return (SequentialGroup)super.addGroup(param1Group);
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
/*      */     public SequentialGroup addGroup(boolean param1Boolean, GroupLayout.Group param1Group) {
/* 1780 */       super.addGroup(param1Group);
/* 1781 */       if (param1Boolean) {
/* 1782 */         this.baselineSpring = param1Group;
/*      */       }
/* 1784 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addComponent(Component param1Component) {
/* 1791 */       return (SequentialGroup)super.addComponent(param1Component);
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
/*      */     public SequentialGroup addComponent(boolean param1Boolean, Component param1Component) {
/* 1804 */       super.addComponent(param1Component);
/* 1805 */       if (param1Boolean) {
/* 1806 */         this.baselineSpring = this.springs.get(this.springs.size() - 1);
/*      */       }
/* 1808 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addComponent(Component param1Component, int param1Int1, int param1Int2, int param1Int3) {
/* 1816 */       return (SequentialGroup)super.addComponent(param1Component, param1Int1, param1Int2, param1Int3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addComponent(boolean param1Boolean, Component param1Component, int param1Int1, int param1Int2, int param1Int3) {
/* 1837 */       super.addComponent(param1Component, param1Int1, param1Int2, param1Int3);
/* 1838 */       if (param1Boolean) {
/* 1839 */         this.baselineSpring = this.springs.get(this.springs.size() - 1);
/*      */       }
/* 1841 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addGap(int param1Int) {
/* 1848 */       return (SequentialGroup)super.addGap(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addGap(int param1Int1, int param1Int2, int param1Int3) {
/* 1855 */       return (SequentialGroup)super.addGap(param1Int1, param1Int2, param1Int3);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addPreferredGap(JComponent param1JComponent1, JComponent param1JComponent2, LayoutStyle.ComponentPlacement param1ComponentPlacement) {
/* 1874 */       return addPreferredGap(param1JComponent1, param1JComponent2, param1ComponentPlacement, -1, -2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addPreferredGap(JComponent param1JComponent1, JComponent param1JComponent2, LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int1, int param1Int2) {
/* 1898 */       if (param1ComponentPlacement == null) {
/* 1899 */         throw new IllegalArgumentException("Type must be non-null");
/*      */       }
/* 1901 */       if (param1JComponent1 == null || param1JComponent2 == null) {
/* 1902 */         throw new IllegalArgumentException("Components must be non-null");
/*      */       }
/*      */       
/* 1905 */       checkPreferredGapValues(param1Int1, param1Int2);
/* 1906 */       return (SequentialGroup)addSpring(new GroupLayout.PreferredGapSpring(param1JComponent1, param1JComponent2, param1ComponentPlacement, param1Int1, param1Int2));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addPreferredGap(LayoutStyle.ComponentPlacement param1ComponentPlacement) {
/* 1930 */       return addPreferredGap(param1ComponentPlacement, -1, -1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addPreferredGap(LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int1, int param1Int2) {
/* 1957 */       if (param1ComponentPlacement != LayoutStyle.ComponentPlacement.RELATED && param1ComponentPlacement != LayoutStyle.ComponentPlacement.UNRELATED)
/*      */       {
/* 1959 */         throw new IllegalArgumentException("Type must be one of LayoutStyle.ComponentPlacement.RELATED or LayoutStyle.ComponentPlacement.UNRELATED");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1964 */       checkPreferredGapValues(param1Int1, param1Int2);
/* 1965 */       GroupLayout.this.hasPreferredPaddingSprings = true;
/* 1966 */       return (SequentialGroup)addSpring(new GroupLayout.AutoPreferredGapSpring(param1ComponentPlacement, param1Int1, param1Int2));
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
/*      */ 
/*      */     
/*      */     public SequentialGroup addContainerGap() {
/* 1982 */       return addContainerGap(-1, -1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SequentialGroup addContainerGap(int param1Int1, int param1Int2) {
/* 2000 */       if ((param1Int1 < 0 && param1Int1 != -1) || (param1Int2 < 0 && param1Int2 != -1 && param1Int2 != -2) || (param1Int1 >= 0 && param1Int2 >= 0 && param1Int1 > param1Int2))
/*      */       {
/*      */         
/* 2003 */         throw new IllegalArgumentException("Pref and max must be either DEFAULT_VALUE or >= 0 and pref <= max");
/*      */       }
/*      */ 
/*      */       
/* 2007 */       GroupLayout.this.hasPreferredPaddingSprings = true;
/* 2008 */       return (SequentialGroup)addSpring(new GroupLayout.ContainerAutoPreferredGapSpring(param1Int1, param1Int2));
/*      */     }
/*      */ 
/*      */     
/*      */     int operator(int param1Int1, int param1Int2) {
/* 2013 */       return constrain(param1Int1) + constrain(param1Int2);
/*      */     }
/*      */     
/*      */     void setValidSize(int param1Int1, int param1Int2, int param1Int3) {
/* 2017 */       int i = getPreferredSize(param1Int1);
/* 2018 */       if (param1Int3 == i) {
/*      */         
/* 2020 */         for (GroupLayout.Spring spring : this.springs) {
/* 2021 */           int j = spring.getPreferredSize(param1Int1);
/* 2022 */           spring.setSize(param1Int1, param1Int2, j);
/* 2023 */           param1Int2 += j;
/*      */         } 
/* 2025 */       } else if (this.springs.size() == 1) {
/* 2026 */         GroupLayout.Spring spring = getSpring(0);
/* 2027 */         spring.setSize(param1Int1, param1Int2, Math.min(
/* 2028 */               Math.max(param1Int3, spring.getMinimumSize(param1Int1)), spring
/* 2029 */               .getMaximumSize(param1Int1)));
/* 2030 */       } else if (this.springs.size() > 1) {
/*      */         
/* 2032 */         setValidSizeNotPreferred(param1Int1, param1Int2, param1Int3);
/*      */       } 
/*      */     }
/*      */     
/*      */     private void setValidSizeNotPreferred(int param1Int1, int param1Int2, int param1Int3) {
/* 2037 */       int i = param1Int3 - getPreferredSize(param1Int1);
/* 2038 */       assert i != 0;
/* 2039 */       boolean bool = (i < 0) ? true : false;
/* 2040 */       int j = this.springs.size();
/* 2041 */       if (bool) {
/* 2042 */         i *= -1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2057 */       List<GroupLayout.SpringDelta> list = buildResizableList(param1Int1, bool);
/* 2058 */       int k = list.size();
/*      */       
/* 2060 */       if (k > 0) {
/*      */         
/* 2062 */         int m = i / k;
/*      */         
/* 2064 */         int n = i - m * k;
/* 2065 */         int[] arrayOfInt = new int[j];
/* 2066 */         byte b1 = bool ? -1 : 1;
/*      */         
/*      */         byte b2;
/* 2069 */         for (b2 = 0; b2 < k; b2++) {
/* 2070 */           GroupLayout.SpringDelta springDelta = list.get(b2);
/* 2071 */           if (b2 + 1 == k) {
/* 2072 */             m += n;
/*      */           }
/* 2074 */           springDelta.delta = Math.min(m, springDelta.delta);
/* 2075 */           i -= springDelta.delta;
/* 2076 */           if (springDelta.delta != m && b2 + 1 < k) {
/*      */ 
/*      */ 
/*      */             
/* 2080 */             m = i / (k - b2 - 1);
/* 2081 */             n = i - m * (k - b2 - 1);
/*      */           } 
/* 2083 */           arrayOfInt[springDelta.index] = b1 * springDelta.delta;
/*      */         } 
/*      */ 
/*      */         
/* 2087 */         for (b2 = 0; b2 < j; b2++) {
/* 2088 */           GroupLayout.Spring spring = getSpring(b2);
/* 2089 */           int i1 = spring.getPreferredSize(param1Int1) + arrayOfInt[b2];
/* 2090 */           spring.setSize(param1Int1, param1Int2, i1);
/* 2091 */           param1Int2 += i1;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2096 */         for (byte b = 0; b < j; b++) {
/* 2097 */           int m; GroupLayout.Spring spring = getSpring(b);
/*      */           
/* 2099 */           if (bool) {
/* 2100 */             m = spring.getMinimumSize(param1Int1);
/*      */           } else {
/* 2102 */             m = spring.getMaximumSize(param1Int1);
/*      */           } 
/* 2104 */           spring.setSize(param1Int1, param1Int2, m);
/* 2105 */           param1Int2 += m;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private List<GroupLayout.SpringDelta> buildResizableList(int param1Int, boolean param1Boolean) {
/* 2118 */       int i = this.springs.size();
/* 2119 */       ArrayList<GroupLayout.SpringDelta> arrayList = new ArrayList(i);
/* 2120 */       for (byte b = 0; b < i; b++) {
/* 2121 */         int j; GroupLayout.Spring spring = getSpring(b);
/*      */         
/* 2123 */         if (param1Boolean) {
/*      */           
/* 2125 */           j = spring.getPreferredSize(param1Int) - spring.getMinimumSize(param1Int);
/*      */         } else {
/*      */           
/* 2128 */           j = spring.getMaximumSize(param1Int) - spring.getPreferredSize(param1Int);
/*      */         } 
/* 2130 */         if (j > 0) {
/* 2131 */           arrayList.add(new GroupLayout.SpringDelta(b, j));
/*      */         }
/*      */       } 
/* 2134 */       Collections.sort(arrayList);
/* 2135 */       return arrayList;
/*      */     }
/*      */ 
/*      */     
/*      */     private int indexOfNextNonZeroSpring(int param1Int, boolean param1Boolean) {
/* 2140 */       while (param1Int < this.springs.size()) {
/* 2141 */         GroupLayout.Spring spring = this.springs.get(param1Int);
/* 2142 */         if (!spring.willHaveZeroSize(param1Boolean)) {
/* 2143 */           return param1Int;
/*      */         }
/* 2145 */         param1Int++;
/*      */       } 
/* 2147 */       return param1Int;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void insertAutopadding(int param1Int, List<GroupLayout.AutoPreferredGapSpring> param1List1, List<GroupLayout.AutoPreferredGapSpring> param1List2, List<GroupLayout.ComponentSpring> param1List3, List<GroupLayout.ComponentSpring> param1List4, boolean param1Boolean) {
/* 2156 */       ArrayList<GroupLayout.AutoPreferredGapSpring> arrayList1 = new ArrayList<>(param1List1);
/*      */       
/* 2158 */       ArrayList<GroupLayout.AutoPreferredGapSpring> arrayList2 = new ArrayList(1);
/*      */       
/* 2160 */       ArrayList<GroupLayout.ComponentSpring> arrayList3 = new ArrayList<>(param1List3);
/*      */       
/* 2162 */       ArrayList<GroupLayout.ComponentSpring> arrayList4 = null;
/* 2163 */       int i = 0;
/*      */ 
/*      */       
/* 2166 */       while (i < this.springs.size()) {
/* 2167 */         GroupLayout.Spring spring = getSpring(i);
/* 2168 */         if (spring instanceof GroupLayout.AutoPreferredGapSpring) {
/* 2169 */           if (arrayList1.size() == 0) {
/*      */ 
/*      */             
/* 2172 */             GroupLayout.AutoPreferredGapSpring autoPreferredGapSpring = (GroupLayout.AutoPreferredGapSpring)spring;
/*      */             
/* 2174 */             autoPreferredGapSpring.setSources(arrayList3);
/* 2175 */             arrayList3.clear();
/* 2176 */             i = indexOfNextNonZeroSpring(i + 1, true);
/* 2177 */             if (i == this.springs.size()) {
/*      */ 
/*      */               
/* 2180 */               if (!(autoPreferredGapSpring instanceof GroupLayout.ContainerAutoPreferredGapSpring))
/*      */               {
/* 2182 */                 param1List2.add(autoPreferredGapSpring); } 
/*      */               continue;
/*      */             } 
/* 2185 */             arrayList1.clear();
/* 2186 */             arrayList1.add(autoPreferredGapSpring);
/*      */             continue;
/*      */           } 
/* 2189 */           i = indexOfNextNonZeroSpring(i + 1, true);
/*      */           
/*      */           continue;
/*      */         } 
/* 2193 */         if (arrayList3.size() > 0 && param1Boolean) {
/*      */ 
/*      */           
/* 2196 */           GroupLayout.AutoPreferredGapSpring autoPreferredGapSpring = new GroupLayout.AutoPreferredGapSpring();
/*      */ 
/*      */ 
/*      */           
/* 2200 */           this.springs.add(i, autoPreferredGapSpring);
/*      */           continue;
/*      */         } 
/* 2203 */         if (spring instanceof GroupLayout.ComponentSpring) {
/*      */ 
/*      */           
/* 2206 */           GroupLayout.ComponentSpring componentSpring = (GroupLayout.ComponentSpring)spring;
/* 2207 */           if (!componentSpring.isVisible()) {
/* 2208 */             i++;
/*      */             continue;
/*      */           } 
/* 2211 */           for (GroupLayout.AutoPreferredGapSpring autoPreferredGapSpring : arrayList1) {
/* 2212 */             autoPreferredGapSpring.addTarget(componentSpring, param1Int);
/*      */           }
/* 2214 */           arrayList3.clear();
/* 2215 */           arrayList1.clear();
/* 2216 */           i = indexOfNextNonZeroSpring(i + 1, false);
/* 2217 */           if (i == this.springs.size()) {
/*      */             
/* 2219 */             param1List4.add(componentSpring);
/*      */             continue;
/*      */           } 
/* 2222 */           arrayList3.add(componentSpring); continue;
/*      */         } 
/* 2224 */         if (spring instanceof GroupLayout.Group) {
/*      */           
/* 2226 */           if (arrayList4 == null) {
/* 2227 */             arrayList4 = new ArrayList(1);
/*      */           } else {
/* 2229 */             arrayList4.clear();
/*      */           } 
/* 2231 */           arrayList2.clear();
/* 2232 */           ((GroupLayout.Group)spring).insertAutopadding(param1Int, arrayList1, arrayList2, arrayList3, arrayList4, param1Boolean);
/*      */ 
/*      */           
/* 2235 */           arrayList3.clear();
/* 2236 */           arrayList1.clear();
/* 2237 */           i = indexOfNextNonZeroSpring(i + 1, 
/* 2238 */               (arrayList4.size() == 0));
/* 2239 */           if (i == this.springs.size()) {
/* 2240 */             param1List4.addAll(arrayList4);
/* 2241 */             param1List2.addAll(arrayList2); continue;
/*      */           } 
/* 2243 */           arrayList3.addAll(arrayList4);
/* 2244 */           arrayList1.addAll(arrayList2);
/*      */           
/*      */           continue;
/*      */         } 
/* 2248 */         arrayList1.clear();
/* 2249 */         arrayList3.clear();
/* 2250 */         i++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int getBaseline() {
/* 2257 */       if (this.baselineSpring != null) {
/* 2258 */         int i = this.baselineSpring.getBaseline();
/* 2259 */         if (i >= 0) {
/* 2260 */           int j = 0;
/* 2261 */           for (GroupLayout.Spring spring : this.springs) {
/* 2262 */             if (spring == this.baselineSpring) {
/* 2263 */               return j + i;
/*      */             }
/* 2265 */             j += spring.getPreferredSize(1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2270 */       return -1;
/*      */     }
/*      */     
/*      */     Component.BaselineResizeBehavior getBaselineResizeBehavior() {
/* 2274 */       if (isResizable(1)) {
/* 2275 */         if (!this.baselineSpring.isResizable(1)) {
/*      */ 
/*      */ 
/*      */           
/* 2279 */           boolean bool1 = false;
/* 2280 */           for (GroupLayout.Spring spring : this.springs) {
/* 2281 */             if (spring == this.baselineSpring)
/*      */               break; 
/* 2283 */             if (spring.isResizable(1)) {
/* 2284 */               bool1 = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 2288 */           boolean bool2 = false;
/* 2289 */           for (int i = this.springs.size() - 1; i >= 0; i--) {
/* 2290 */             GroupLayout.Spring spring = this.springs.get(i);
/* 2291 */             if (spring == this.baselineSpring) {
/*      */               break;
/*      */             }
/* 2294 */             if (spring.isResizable(1)) {
/* 2295 */               bool2 = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 2299 */           if (bool1 && !bool2)
/* 2300 */             return Component.BaselineResizeBehavior.CONSTANT_DESCENT; 
/* 2301 */           if (!bool1 && bool2) {
/* 2302 */             return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 2307 */           Component.BaselineResizeBehavior baselineResizeBehavior = this.baselineSpring.getBaselineResizeBehavior();
/* 2308 */           if (baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_ASCENT) {
/* 2309 */             for (GroupLayout.Spring spring : this.springs) {
/* 2310 */               if (spring == this.baselineSpring) {
/* 2311 */                 return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */               }
/* 2313 */               if (spring.isResizable(1)) {
/* 2314 */                 return Component.BaselineResizeBehavior.OTHER;
/*      */               }
/*      */             } 
/* 2317 */           } else if (baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
/* 2318 */             for (int i = this.springs.size() - 1; i >= 0; i--) {
/* 2319 */               GroupLayout.Spring spring = this.springs.get(i);
/* 2320 */               if (spring == this.baselineSpring) {
/* 2321 */                 return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
/*      */               }
/* 2323 */               if (spring.isResizable(1)) {
/* 2324 */                 return Component.BaselineResizeBehavior.OTHER;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 2329 */         return Component.BaselineResizeBehavior.OTHER;
/*      */       } 
/*      */       
/* 2332 */       return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */     }
/*      */     
/*      */     private void checkPreferredGapValues(int param1Int1, int param1Int2) {
/* 2336 */       if ((param1Int1 < 0 && param1Int1 != -1 && param1Int1 != -2) || (param1Int2 < 0 && param1Int2 != -1 && param1Int2 != -2) || (param1Int1 >= 0 && param1Int2 >= 0 && param1Int1 > param1Int2))
/*      */       {
/*      */         
/* 2339 */         throw new IllegalArgumentException("Pref and max must be either DEFAULT_SIZE, PREFERRED_SIZE, or >= 0 and pref <= max");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class SpringDelta
/*      */     implements Comparable<SpringDelta>
/*      */   {
/*      */     public final int index;
/*      */ 
/*      */     
/*      */     public int delta;
/*      */ 
/*      */ 
/*      */     
/*      */     public SpringDelta(int param1Int1, int param1Int2) {
/* 2357 */       this.index = param1Int1;
/* 2358 */       this.delta = param1Int2;
/*      */     }
/*      */     
/*      */     public int compareTo(SpringDelta param1SpringDelta) {
/* 2362 */       return this.delta - param1SpringDelta.delta;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2366 */       return super.toString() + "[index=" + this.index + ", delta=" + this.delta + "]";
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
/*      */   public class ParallelGroup
/*      */     extends Group
/*      */   {
/*      */     private final GroupLayout.Alignment childAlignment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final boolean resizable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ParallelGroup(GroupLayout.Alignment param1Alignment, boolean param1Boolean) {
/* 2461 */       this.childAlignment = param1Alignment;
/* 2462 */       this.resizable = param1Boolean;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParallelGroup addGroup(GroupLayout.Group param1Group) {
/* 2469 */       return (ParallelGroup)super.addGroup(param1Group);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParallelGroup addComponent(Component param1Component) {
/* 2476 */       return (ParallelGroup)super.addComponent(param1Component);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParallelGroup addComponent(Component param1Component, int param1Int1, int param1Int2, int param1Int3) {
/* 2484 */       return (ParallelGroup)super.addComponent(param1Component, param1Int1, param1Int2, param1Int3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParallelGroup addGap(int param1Int) {
/* 2491 */       return (ParallelGroup)super.addGap(param1Int);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParallelGroup addGap(int param1Int1, int param1Int2, int param1Int3) {
/* 2498 */       return (ParallelGroup)super.addGap(param1Int1, param1Int2, param1Int3);
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
/*      */ 
/*      */     
/*      */     public ParallelGroup addGroup(GroupLayout.Alignment param1Alignment, GroupLayout.Group param1Group) {
/* 2514 */       checkChildAlignment(param1Alignment);
/* 2515 */       param1Group.setAlignment(param1Alignment);
/* 2516 */       return (ParallelGroup)addSpring(param1Group);
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
/*      */     
/*      */     public ParallelGroup addComponent(Component param1Component, GroupLayout.Alignment param1Alignment) {
/* 2531 */       return addComponent(param1Component, param1Alignment, -1, -1, -1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ParallelGroup addComponent(Component param1Component, GroupLayout.Alignment param1Alignment, int param1Int1, int param1Int2, int param1Int3) {
/* 2550 */       checkChildAlignment(param1Alignment);
/* 2551 */       GroupLayout.ComponentSpring componentSpring = new GroupLayout.ComponentSpring(param1Component, param1Int1, param1Int2, param1Int3);
/*      */       
/* 2553 */       componentSpring.setAlignment(param1Alignment);
/* 2554 */       return (ParallelGroup)addSpring(componentSpring);
/*      */     }
/*      */     
/*      */     boolean isResizable() {
/* 2558 */       return this.resizable;
/*      */     }
/*      */     
/*      */     int operator(int param1Int1, int param1Int2) {
/* 2562 */       return Math.max(param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     int calculateMinimumSize(int param1Int) {
/* 2566 */       if (!isResizable()) {
/* 2567 */         return getPreferredSize(param1Int);
/*      */       }
/* 2569 */       return super.calculateMinimumSize(param1Int);
/*      */     }
/*      */     
/*      */     int calculateMaximumSize(int param1Int) {
/* 2573 */       if (!isResizable()) {
/* 2574 */         return getPreferredSize(param1Int);
/*      */       }
/* 2576 */       return super.calculateMaximumSize(param1Int);
/*      */     }
/*      */     
/*      */     void setValidSize(int param1Int1, int param1Int2, int param1Int3) {
/* 2580 */       for (GroupLayout.Spring spring : this.springs) {
/* 2581 */         setChildSize(spring, param1Int1, param1Int2, param1Int3);
/*      */       }
/*      */     }
/*      */     
/*      */     void setChildSize(GroupLayout.Spring param1Spring, int param1Int1, int param1Int2, int param1Int3) {
/* 2586 */       GroupLayout.Alignment alignment = param1Spring.getAlignment();
/* 2587 */       int i = Math.min(
/* 2588 */           Math.max(param1Spring.getMinimumSize(param1Int1), param1Int3), param1Spring
/* 2589 */           .getMaximumSize(param1Int1));
/* 2590 */       if (alignment == null) {
/* 2591 */         alignment = this.childAlignment;
/*      */       }
/* 2593 */       switch (alignment) {
/*      */         case CONSTANT_ASCENT:
/* 2595 */           param1Spring.setSize(param1Int1, param1Int2 + param1Int3 - i, i);
/*      */           return;
/*      */         
/*      */         case CONSTANT_DESCENT:
/* 2599 */           param1Spring.setSize(param1Int1, param1Int2 + (param1Int3 - i) / 2, i);
/*      */           return;
/*      */       } 
/*      */       
/* 2603 */       param1Spring.setSize(param1Int1, param1Int2, i);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void insertAutopadding(int param1Int, List<GroupLayout.AutoPreferredGapSpring> param1List1, List<GroupLayout.AutoPreferredGapSpring> param1List2, List<GroupLayout.ComponentSpring> param1List3, List<GroupLayout.ComponentSpring> param1List4, boolean param1Boolean) {
/* 2614 */       for (GroupLayout.Spring spring : this.springs) {
/* 2615 */         if (spring instanceof GroupLayout.ComponentSpring) {
/* 2616 */           if (((GroupLayout.ComponentSpring)spring).isVisible()) {
/*      */             
/* 2618 */             for (GroupLayout.AutoPreferredGapSpring autoPreferredGapSpring : param1List1) {
/* 2619 */               autoPreferredGapSpring.addTarget((GroupLayout.ComponentSpring)spring, param1Int);
/*      */             }
/* 2621 */             param1List4.add((GroupLayout.ComponentSpring)spring);
/*      */           }  continue;
/* 2623 */         }  if (spring instanceof GroupLayout.Group) {
/* 2624 */           ((GroupLayout.Group)spring).insertAutopadding(param1Int, param1List1, param1List2, param1List3, param1List4, param1Boolean); continue;
/*      */         } 
/* 2626 */         if (spring instanceof GroupLayout.AutoPreferredGapSpring) {
/* 2627 */           ((GroupLayout.AutoPreferredGapSpring)spring).setSources(param1List3);
/* 2628 */           param1List2.add((GroupLayout.AutoPreferredGapSpring)spring);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void checkChildAlignment(GroupLayout.Alignment param1Alignment) {
/* 2634 */       checkChildAlignment(param1Alignment, this instanceof GroupLayout.BaselineGroup);
/*      */     }
/*      */ 
/*      */     
/*      */     private void checkChildAlignment(GroupLayout.Alignment param1Alignment, boolean param1Boolean) {
/* 2639 */       if (param1Alignment == null) {
/* 2640 */         throw new IllegalArgumentException("Alignment must be non-null");
/*      */       }
/* 2642 */       if (!param1Boolean && param1Alignment == GroupLayout.Alignment.BASELINE) {
/* 2643 */         throw new IllegalArgumentException("Alignment must be one of:LEADING, TRAILING or CENTER");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class BaselineGroup
/*      */     extends ParallelGroup
/*      */   {
/*      */     private boolean allSpringsHaveBaseline;
/*      */ 
/*      */ 
/*      */     
/*      */     private int prefAscent;
/*      */ 
/*      */ 
/*      */     
/*      */     private int prefDescent;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean baselineAnchorSet;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean baselineAnchoredToTop;
/*      */ 
/*      */     
/*      */     private boolean calcedBaseline;
/*      */ 
/*      */ 
/*      */     
/*      */     BaselineGroup(boolean param1Boolean) {
/* 2678 */       super(GroupLayout.Alignment.LEADING, param1Boolean);
/* 2679 */       this.prefAscent = this.prefDescent = -1;
/* 2680 */       this.calcedBaseline = false;
/*      */     }
/*      */     
/*      */     BaselineGroup(boolean param1Boolean1, boolean param1Boolean2) {
/* 2684 */       this(param1Boolean1);
/* 2685 */       this.baselineAnchoredToTop = param1Boolean2;
/* 2686 */       this.baselineAnchorSet = true;
/*      */     }
/*      */     
/*      */     void unset() {
/* 2690 */       super.unset();
/* 2691 */       this.prefAscent = this.prefDescent = -1;
/* 2692 */       this.calcedBaseline = false;
/*      */     }
/*      */     
/*      */     void setValidSize(int param1Int1, int param1Int2, int param1Int3) {
/* 2696 */       checkAxis(param1Int1);
/* 2697 */       if (this.prefAscent == -1) {
/* 2698 */         super.setValidSize(param1Int1, param1Int2, param1Int3);
/*      */       } else {
/*      */         
/* 2701 */         baselineLayout(param1Int2, param1Int3);
/*      */       } 
/*      */     }
/*      */     
/*      */     int calculateSize(int param1Int1, int param1Int2) {
/* 2706 */       checkAxis(param1Int1);
/* 2707 */       if (!this.calcedBaseline) {
/* 2708 */         calculateBaselineAndResizeBehavior();
/*      */       }
/* 2710 */       if (param1Int2 == 0) {
/* 2711 */         return calculateMinSize();
/*      */       }
/* 2713 */       if (param1Int2 == 2) {
/* 2714 */         return calculateMaxSize();
/*      */       }
/* 2716 */       if (this.allSpringsHaveBaseline) {
/* 2717 */         return this.prefAscent + this.prefDescent;
/*      */       }
/* 2719 */       return Math.max(this.prefAscent + this.prefDescent, super
/* 2720 */           .calculateSize(param1Int1, param1Int2));
/*      */     }
/*      */ 
/*      */     
/*      */     private void calculateBaselineAndResizeBehavior() {
/* 2725 */       this.prefAscent = 0;
/* 2726 */       this.prefDescent = 0;
/* 2727 */       byte b = 0;
/* 2728 */       Component.BaselineResizeBehavior baselineResizeBehavior = null;
/* 2729 */       for (GroupLayout.Spring spring : this.springs) {
/* 2730 */         if (spring.getAlignment() == null || spring
/* 2731 */           .getAlignment() == GroupLayout.Alignment.BASELINE) {
/* 2732 */           int i = spring.getBaseline();
/* 2733 */           if (i >= 0) {
/* 2734 */             if (spring.isResizable(1)) {
/*      */               
/* 2736 */               Component.BaselineResizeBehavior baselineResizeBehavior1 = spring.getBaselineResizeBehavior();
/* 2737 */               if (baselineResizeBehavior == null) {
/* 2738 */                 baselineResizeBehavior = baselineResizeBehavior1;
/* 2739 */               } else if (baselineResizeBehavior1 != baselineResizeBehavior) {
/* 2740 */                 baselineResizeBehavior = Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */               } 
/*      */             } 
/*      */             
/* 2744 */             this.prefAscent = Math.max(this.prefAscent, i);
/* 2745 */             this.prefDescent = Math.max(this.prefDescent, spring
/* 2746 */                 .getPreferredSize(1) - i);
/* 2747 */             b++;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2751 */       if (!this.baselineAnchorSet) {
/* 2752 */         if (baselineResizeBehavior == Component.BaselineResizeBehavior.CONSTANT_DESCENT) {
/* 2753 */           this.baselineAnchoredToTop = false;
/*      */         } else {
/* 2755 */           this.baselineAnchoredToTop = true;
/*      */         } 
/*      */       }
/* 2758 */       this.allSpringsHaveBaseline = (b == this.springs.size());
/* 2759 */       this.calcedBaseline = true;
/*      */     }
/*      */     
/*      */     private int calculateMaxSize() {
/* 2763 */       int i = this.prefAscent;
/* 2764 */       int j = this.prefDescent;
/* 2765 */       int k = 0;
/* 2766 */       for (GroupLayout.Spring spring : this.springs) {
/*      */         
/* 2768 */         int n = spring.getMaximumSize(1); int m;
/* 2769 */         if ((spring.getAlignment() == null || spring
/* 2770 */           .getAlignment() == GroupLayout.Alignment.BASELINE) && (
/* 2771 */           m = spring.getBaseline()) >= 0) {
/* 2772 */           int i1 = spring.getPreferredSize(1);
/* 2773 */           if (i1 != n) {
/* 2774 */             switch (spring.getBaselineResizeBehavior()) {
/*      */               case CONSTANT_ASCENT:
/* 2776 */                 if (this.baselineAnchoredToTop) {
/* 2777 */                   j = Math.max(j, n - m);
/*      */                 }
/*      */                 continue;
/*      */               
/*      */               case CONSTANT_DESCENT:
/* 2782 */                 if (!this.baselineAnchoredToTop) {
/* 2783 */                   i = Math.max(i, n - i1 + m);
/*      */                 }
/*      */                 continue;
/*      */             } 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           continue;
/*      */         } 
/* 2793 */         k = Math.max(k, n);
/*      */       } 
/*      */       
/* 2796 */       return Math.max(k, i + j);
/*      */     }
/*      */     
/*      */     private int calculateMinSize() {
/* 2800 */       int i = 0;
/* 2801 */       int j = 0;
/* 2802 */       int k = 0;
/* 2803 */       if (this.baselineAnchoredToTop) {
/* 2804 */         i = this.prefAscent;
/*      */       } else {
/* 2806 */         j = this.prefDescent;
/*      */       } 
/* 2808 */       for (GroupLayout.Spring spring : this.springs) {
/* 2809 */         int m = spring.getMinimumSize(1);
/*      */         int n;
/* 2811 */         if ((spring.getAlignment() == null || spring
/* 2812 */           .getAlignment() == GroupLayout.Alignment.BASELINE) && (
/* 2813 */           n = spring.getBaseline()) >= 0) {
/* 2814 */           int i1 = spring.getPreferredSize(1);
/*      */           
/* 2816 */           Component.BaselineResizeBehavior baselineResizeBehavior = spring.getBaselineResizeBehavior();
/* 2817 */           switch (baselineResizeBehavior) {
/*      */             case CONSTANT_ASCENT:
/* 2819 */               if (this.baselineAnchoredToTop) {
/* 2820 */                 j = Math.max(m - n, j);
/*      */                 continue;
/*      */               } 
/* 2823 */               i = Math.max(n, i);
/*      */               continue;
/*      */             
/*      */             case CONSTANT_DESCENT:
/* 2827 */               if (!this.baselineAnchoredToTop) {
/* 2828 */                 i = Math.max(n - i1 - m, i);
/*      */                 
/*      */                 continue;
/*      */               } 
/* 2832 */               j = Math.max(i1 - n, j);
/*      */               continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2839 */           i = Math.max(n, i);
/* 2840 */           j = Math.max(i1 - n, j);
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 2846 */         k = Math.max(k, m);
/*      */       } 
/*      */       
/* 2849 */       return Math.max(k, i + j);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void baselineLayout(int param1Int1, int param1Int2) {
/*      */       int i;
/*      */       int j;
/* 2859 */       if (this.baselineAnchoredToTop) {
/* 2860 */         i = this.prefAscent;
/* 2861 */         j = param1Int2 - i;
/*      */       } else {
/* 2863 */         i = param1Int2 - this.prefDescent;
/* 2864 */         j = this.prefDescent;
/*      */       } 
/* 2866 */       for (GroupLayout.Spring spring : this.springs) {
/* 2867 */         GroupLayout.Alignment alignment = spring.getAlignment();
/* 2868 */         if (alignment == null || alignment == GroupLayout.Alignment.BASELINE) {
/* 2869 */           int k = spring.getBaseline();
/* 2870 */           if (k >= 0) {
/* 2871 */             int i2, m = spring.getMaximumSize(1);
/* 2872 */             int n = spring.getPreferredSize(1);
/* 2873 */             int i1 = n;
/*      */             
/* 2875 */             switch (spring.getBaselineResizeBehavior()) {
/*      */               case CONSTANT_ASCENT:
/* 2877 */                 i2 = param1Int1 + i - k;
/* 2878 */                 i1 = Math.min(j, m - k) + k;
/*      */                 break;
/*      */               
/*      */               case CONSTANT_DESCENT:
/* 2882 */                 i1 = Math.min(i, m - n + k) + n - k;
/*      */ 
/*      */                 
/* 2885 */                 i2 = param1Int1 + i + n - k - i1;
/*      */                 break;
/*      */               
/*      */               default:
/* 2889 */                 i2 = param1Int1 + i - k;
/*      */                 break;
/*      */             } 
/* 2892 */             spring.setSize(1, i2, i1); continue;
/*      */           } 
/* 2894 */           setChildSize(spring, 1, param1Int1, param1Int2);
/*      */           continue;
/*      */         } 
/* 2897 */         setChildSize(spring, 1, param1Int1, param1Int2);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     int getBaseline() {
/* 2903 */       if (this.springs.size() > 1) {
/*      */         
/* 2905 */         getPreferredSize(1);
/* 2906 */         return this.prefAscent;
/* 2907 */       }  if (this.springs.size() == 1) {
/* 2908 */         return ((GroupLayout.Spring)this.springs.get(0)).getBaseline();
/*      */       }
/* 2910 */       return -1;
/*      */     }
/*      */     
/*      */     Component.BaselineResizeBehavior getBaselineResizeBehavior() {
/* 2914 */       if (this.springs.size() == 1) {
/* 2915 */         return ((GroupLayout.Spring)this.springs.get(0)).getBaselineResizeBehavior();
/*      */       }
/* 2917 */       if (this.baselineAnchoredToTop) {
/* 2918 */         return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
/*      */       }
/* 2920 */       return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
/*      */     }
/*      */ 
/*      */     
/*      */     private void checkAxis(int param1Int) {
/* 2925 */       if (param1Int == 0) {
/* 2926 */         throw new IllegalStateException("Baseline must be used along vertical axis");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final class ComponentSpring
/*      */     extends Spring
/*      */   {
/*      */     private Component component;
/*      */     
/*      */     private int origin;
/*      */     
/*      */     private final int min;
/*      */     
/*      */     private final int pref;
/*      */     
/*      */     private final int max;
/* 2944 */     private int baseline = -1;
/*      */ 
/*      */     
/*      */     private boolean installed;
/*      */ 
/*      */     
/*      */     private ComponentSpring(Component param1Component, int param1Int1, int param1Int2, int param1Int3) {
/* 2951 */       this.component = param1Component;
/* 2952 */       if (param1Component == null) {
/* 2953 */         throw new IllegalArgumentException("Component must be non-null");
/*      */       }
/*      */ 
/*      */       
/* 2957 */       GroupLayout.checkSize(param1Int1, param1Int2, param1Int3, true);
/*      */       
/* 2959 */       this.min = param1Int1;
/* 2960 */       this.max = param1Int3;
/* 2961 */       this.pref = param1Int2;
/*      */ 
/*      */ 
/*      */       
/* 2965 */       GroupLayout.this.getComponentInfo(param1Component);
/*      */     }
/*      */     
/*      */     int calculateMinimumSize(int param1Int) {
/* 2969 */       if (isLinked(param1Int)) {
/* 2970 */         return getLinkSize(param1Int, 0);
/*      */       }
/* 2972 */       return calculateNonlinkedMinimumSize(param1Int);
/*      */     }
/*      */     
/*      */     int calculatePreferredSize(int param1Int) {
/* 2976 */       if (isLinked(param1Int)) {
/* 2977 */         return getLinkSize(param1Int, 1);
/*      */       }
/* 2979 */       int i = getMinimumSize(param1Int);
/* 2980 */       int j = calculateNonlinkedPreferredSize(param1Int);
/* 2981 */       int k = getMaximumSize(param1Int);
/* 2982 */       return Math.min(k, Math.max(i, j));
/*      */     }
/*      */     
/*      */     int calculateMaximumSize(int param1Int) {
/* 2986 */       if (isLinked(param1Int)) {
/* 2987 */         return getLinkSize(param1Int, 2);
/*      */       }
/* 2989 */       return Math.max(getMinimumSize(param1Int), 
/* 2990 */           calculateNonlinkedMaximumSize(param1Int));
/*      */     }
/*      */     
/*      */     boolean isVisible() {
/* 2994 */       return GroupLayout.this.getComponentInfo(getComponent()).isVisible();
/*      */     }
/*      */     
/*      */     int calculateNonlinkedMinimumSize(int param1Int) {
/* 2998 */       if (!isVisible()) {
/* 2999 */         return 0;
/*      */       }
/* 3001 */       if (this.min >= 0) {
/* 3002 */         return this.min;
/*      */       }
/* 3004 */       if (this.min == -2) {
/* 3005 */         return calculateNonlinkedPreferredSize(param1Int);
/*      */       }
/* 3007 */       assert this.min == -1;
/* 3008 */       return getSizeAlongAxis(param1Int, this.component.getMinimumSize());
/*      */     }
/*      */     
/*      */     int calculateNonlinkedPreferredSize(int param1Int) {
/* 3012 */       if (!isVisible()) {
/* 3013 */         return 0;
/*      */       }
/* 3015 */       if (this.pref >= 0) {
/* 3016 */         return this.pref;
/*      */       }
/* 3018 */       assert this.pref == -1 || this.pref == -2;
/* 3019 */       return getSizeAlongAxis(param1Int, this.component.getPreferredSize());
/*      */     }
/*      */     
/*      */     int calculateNonlinkedMaximumSize(int param1Int) {
/* 3023 */       if (!isVisible()) {
/* 3024 */         return 0;
/*      */       }
/* 3026 */       if (this.max >= 0) {
/* 3027 */         return this.max;
/*      */       }
/* 3029 */       if (this.max == -2) {
/* 3030 */         return calculateNonlinkedPreferredSize(param1Int);
/*      */       }
/* 3032 */       assert this.max == -1;
/* 3033 */       return getSizeAlongAxis(param1Int, this.component.getMaximumSize());
/*      */     }
/*      */     
/*      */     private int getSizeAlongAxis(int param1Int, Dimension param1Dimension) {
/* 3037 */       return (param1Int == 0) ? param1Dimension.width : param1Dimension.height;
/*      */     }
/*      */     
/*      */     private int getLinkSize(int param1Int1, int param1Int2) {
/* 3041 */       if (!isVisible()) {
/* 3042 */         return 0;
/*      */       }
/* 3044 */       GroupLayout.ComponentInfo componentInfo = GroupLayout.this.getComponentInfo(this.component);
/* 3045 */       return componentInfo.getLinkSize(param1Int1, param1Int2);
/*      */     }
/*      */     
/*      */     void setSize(int param1Int1, int param1Int2, int param1Int3) {
/* 3049 */       super.setSize(param1Int1, param1Int2, param1Int3);
/* 3050 */       this.origin = param1Int2;
/* 3051 */       if (param1Int3 == Integer.MIN_VALUE) {
/* 3052 */         this.baseline = -1;
/*      */       }
/*      */     }
/*      */     
/*      */     int getOrigin() {
/* 3057 */       return this.origin;
/*      */     }
/*      */     
/*      */     void setComponent(Component param1Component) {
/* 3061 */       this.component = param1Component;
/*      */     }
/*      */     
/*      */     Component getComponent() {
/* 3065 */       return this.component;
/*      */     }
/*      */     
/*      */     int getBaseline() {
/* 3069 */       if (this.baseline == -1) {
/* 3070 */         ComponentSpring componentSpring = (GroupLayout.this.getComponentInfo(this.component)).horizontalSpring;
/*      */         
/* 3072 */         int i = componentSpring.getPreferredSize(0);
/* 3073 */         int j = getPreferredSize(1);
/* 3074 */         if (i > 0 && j > 0) {
/* 3075 */           this.baseline = this.component.getBaseline(i, j);
/*      */         }
/*      */       } 
/* 3078 */       return this.baseline;
/*      */     }
/*      */     
/*      */     Component.BaselineResizeBehavior getBaselineResizeBehavior() {
/* 3082 */       return getComponent().getBaselineResizeBehavior();
/*      */     }
/*      */     
/*      */     private boolean isLinked(int param1Int) {
/* 3086 */       return GroupLayout.this.getComponentInfo(this.component).isLinked(param1Int);
/*      */     }
/*      */     
/*      */     void installIfNecessary(int param1Int) {
/* 3090 */       if (!this.installed) {
/* 3091 */         this.installed = true;
/* 3092 */         if (param1Int == 0) {
/* 3093 */           (GroupLayout.this.getComponentInfo(this.component)).horizontalSpring = this;
/*      */         } else {
/* 3095 */           (GroupLayout.this.getComponentInfo(this.component)).verticalSpring = this;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     boolean willHaveZeroSize(boolean param1Boolean) {
/* 3102 */       return !isVisible();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class PreferredGapSpring
/*      */     extends Spring
/*      */   {
/*      */     private final JComponent source;
/*      */     
/*      */     private final JComponent target;
/*      */     
/*      */     private final LayoutStyle.ComponentPlacement type;
/*      */     private final int pref;
/*      */     private final int max;
/*      */     
/*      */     PreferredGapSpring(JComponent param1JComponent1, JComponent param1JComponent2, LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int1, int param1Int2) {
/* 3119 */       this.source = param1JComponent1;
/* 3120 */       this.target = param1JComponent2;
/* 3121 */       this.type = param1ComponentPlacement;
/* 3122 */       this.pref = param1Int1;
/* 3123 */       this.max = param1Int2;
/*      */     }
/*      */     
/*      */     int calculateMinimumSize(int param1Int) {
/* 3127 */       return getPadding(param1Int);
/*      */     }
/*      */     
/*      */     int calculatePreferredSize(int param1Int) {
/* 3131 */       if (this.pref == -1 || this.pref == -2) {
/* 3132 */         return getMinimumSize(param1Int);
/*      */       }
/* 3134 */       int i = getMinimumSize(param1Int);
/* 3135 */       int j = getMaximumSize(param1Int);
/* 3136 */       return Math.min(j, Math.max(i, this.pref));
/*      */     }
/*      */     
/*      */     int calculateMaximumSize(int param1Int) {
/* 3140 */       if (this.max == -2 || this.max == -1) {
/* 3141 */         return getPadding(param1Int);
/*      */       }
/* 3143 */       return Math.max(getMinimumSize(param1Int), this.max);
/*      */     }
/*      */     
/*      */     private int getPadding(int param1Int) {
/*      */       byte b;
/* 3148 */       if (param1Int == 0) {
/* 3149 */         b = 3;
/*      */       } else {
/* 3151 */         b = 5;
/*      */       } 
/* 3153 */       return GroupLayout.this.getLayoutStyle0().getPreferredGap(this.source, this.target, this.type, b, GroupLayout.this
/* 3154 */           .host);
/*      */     }
/*      */ 
/*      */     
/*      */     boolean willHaveZeroSize(boolean param1Boolean) {
/* 3159 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class GapSpring
/*      */     extends Spring
/*      */   {
/*      */     private final int min;
/*      */     
/*      */     private final int pref;
/*      */     private final int max;
/*      */     
/*      */     GapSpring(int param1Int1, int param1Int2, int param1Int3) {
/* 3173 */       GroupLayout.checkSize(param1Int1, param1Int2, param1Int3, false);
/* 3174 */       this.min = param1Int1;
/* 3175 */       this.pref = param1Int2;
/* 3176 */       this.max = param1Int3;
/*      */     }
/*      */     
/*      */     int calculateMinimumSize(int param1Int) {
/* 3180 */       if (this.min == -2) {
/* 3181 */         return getPreferredSize(param1Int);
/*      */       }
/* 3183 */       return this.min;
/*      */     }
/*      */     
/*      */     int calculatePreferredSize(int param1Int) {
/* 3187 */       return this.pref;
/*      */     }
/*      */     
/*      */     int calculateMaximumSize(int param1Int) {
/* 3191 */       if (this.max == -2) {
/* 3192 */         return getPreferredSize(param1Int);
/*      */       }
/* 3194 */       return this.max;
/*      */     }
/*      */ 
/*      */     
/*      */     boolean willHaveZeroSize(boolean param1Boolean) {
/* 3199 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class AutoPreferredGapSpring
/*      */     extends Spring
/*      */   {
/*      */     List<GroupLayout.ComponentSpring> sources;
/*      */     
/*      */     GroupLayout.ComponentSpring source;
/*      */     
/*      */     private List<GroupLayout.AutoPreferredGapMatch> matches;
/*      */     
/*      */     int size;
/*      */     
/*      */     int lastSize;
/*      */     
/*      */     private final int pref;
/*      */     private final int max;
/*      */     private LayoutStyle.ComponentPlacement type;
/*      */     private boolean userCreated;
/*      */     
/*      */     private AutoPreferredGapSpring() {
/* 3223 */       this.pref = -2;
/* 3224 */       this.max = -2;
/* 3225 */       this.type = LayoutStyle.ComponentPlacement.RELATED;
/*      */     }
/*      */     
/*      */     AutoPreferredGapSpring(int param1Int1, int param1Int2) {
/* 3229 */       this.pref = param1Int1;
/* 3230 */       this.max = param1Int2;
/*      */     }
/*      */     
/*      */     AutoPreferredGapSpring(LayoutStyle.ComponentPlacement param1ComponentPlacement, int param1Int1, int param1Int2) {
/* 3234 */       this.type = param1ComponentPlacement;
/* 3235 */       this.pref = param1Int1;
/* 3236 */       this.max = param1Int2;
/* 3237 */       this.userCreated = true;
/*      */     }
/*      */     
/*      */     public void setSource(GroupLayout.ComponentSpring param1ComponentSpring) {
/* 3241 */       this.source = param1ComponentSpring;
/*      */     }
/*      */     
/*      */     public void setSources(List<GroupLayout.ComponentSpring> param1List) {
/* 3245 */       this.sources = new ArrayList<>(param1List);
/*      */     }
/*      */     
/*      */     public void setUserCreated(boolean param1Boolean) {
/* 3249 */       this.userCreated = param1Boolean;
/*      */     }
/*      */     
/*      */     public boolean getUserCreated() {
/* 3253 */       return this.userCreated;
/*      */     }
/*      */     
/*      */     void unset() {
/* 3257 */       this.lastSize = getSize();
/* 3258 */       super.unset();
/* 3259 */       this.size = 0;
/*      */     }
/*      */     
/*      */     public void reset() {
/* 3263 */       this.size = 0;
/* 3264 */       this.sources = null;
/* 3265 */       this.source = null;
/* 3266 */       this.matches = null;
/*      */     }
/*      */     
/*      */     public void calculatePadding(int param1Int) {
/* 3270 */       this.size = Integer.MIN_VALUE;
/* 3271 */       int i = Integer.MIN_VALUE;
/* 3272 */       if (this.matches != null) {
/* 3273 */         byte b; LayoutStyle layoutStyle = GroupLayout.this.getLayoutStyle0();
/*      */         
/* 3275 */         if (param1Int == 0) {
/* 3276 */           if (GroupLayout.this.isLeftToRight()) {
/* 3277 */             b = 3;
/*      */           } else {
/* 3279 */             b = 7;
/*      */           } 
/*      */         } else {
/* 3282 */           b = 5;
/*      */         } 
/* 3284 */         for (int j = this.matches.size() - 1; j >= 0; j--) {
/* 3285 */           GroupLayout.AutoPreferredGapMatch autoPreferredGapMatch = this.matches.get(j);
/* 3286 */           i = Math.max(i, 
/* 3287 */               calculatePadding(layoutStyle, b, autoPreferredGapMatch.source, autoPreferredGapMatch.target));
/*      */         } 
/*      */       } 
/*      */       
/* 3291 */       if (this.size == Integer.MIN_VALUE) {
/* 3292 */         this.size = 0;
/*      */       }
/* 3294 */       if (i == Integer.MIN_VALUE) {
/* 3295 */         i = 0;
/*      */       }
/* 3297 */       if (this.lastSize != Integer.MIN_VALUE) {
/* 3298 */         this.size += Math.min(i, this.lastSize);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int calculatePadding(LayoutStyle param1LayoutStyle, int param1Int, GroupLayout.ComponentSpring param1ComponentSpring1, GroupLayout.ComponentSpring param1ComponentSpring2) {
/* 3306 */       int i = param1ComponentSpring2.getOrigin() - param1ComponentSpring1.getOrigin() + param1ComponentSpring1.getSize();
/* 3307 */       if (i >= 0) {
/*      */         byte b;
/* 3309 */         if (param1ComponentSpring1.getComponent() instanceof JComponent && param1ComponentSpring2
/* 3310 */           .getComponent() instanceof JComponent) {
/* 3311 */           b = param1LayoutStyle.getPreferredGap((JComponent)param1ComponentSpring1
/* 3312 */               .getComponent(), (JComponent)param1ComponentSpring2
/* 3313 */               .getComponent(), this.type, param1Int, GroupLayout.this
/* 3314 */               .host);
/*      */         } else {
/* 3316 */           b = 10;
/*      */         } 
/* 3318 */         if (b > i) {
/* 3319 */           this.size = Math.max(this.size, b - i);
/*      */         }
/* 3321 */         return b;
/*      */       } 
/* 3323 */       return 0;
/*      */     }
/*      */     
/*      */     public void addTarget(GroupLayout.ComponentSpring param1ComponentSpring, int param1Int) {
/* 3327 */       boolean bool = (param1Int == 0) ? true : false;
/* 3328 */       if (this.source != null) {
/* 3329 */         if (GroupLayout.this.areParallelSiblings(this.source.getComponent(), param1ComponentSpring
/* 3330 */             .getComponent(), bool)) {
/* 3331 */           addValidTarget(this.source, param1ComponentSpring);
/*      */         }
/*      */       } else {
/* 3334 */         Component component = param1ComponentSpring.getComponent();
/* 3335 */         for (int i = this.sources.size() - 1; i >= 0; 
/* 3336 */           i--) {
/* 3337 */           GroupLayout.ComponentSpring componentSpring = this.sources.get(i);
/* 3338 */           if (GroupLayout.this.areParallelSiblings(componentSpring.getComponent(), component, bool))
/*      */           {
/* 3340 */             addValidTarget(componentSpring, param1ComponentSpring);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private void addValidTarget(GroupLayout.ComponentSpring param1ComponentSpring1, GroupLayout.ComponentSpring param1ComponentSpring2) {
/* 3348 */       if (this.matches == null) {
/* 3349 */         this.matches = new ArrayList<>(1);
/*      */       }
/* 3351 */       this.matches.add(new GroupLayout.AutoPreferredGapMatch(param1ComponentSpring1, param1ComponentSpring2));
/*      */     }
/*      */     
/*      */     int calculateMinimumSize(int param1Int) {
/* 3355 */       return this.size;
/*      */     }
/*      */     
/*      */     int calculatePreferredSize(int param1Int) {
/* 3359 */       if (this.pref == -2 || this.pref == -1) {
/* 3360 */         return this.size;
/*      */       }
/* 3362 */       return Math.max(this.size, this.pref);
/*      */     }
/*      */     
/*      */     int calculateMaximumSize(int param1Int) {
/* 3366 */       if (this.max >= 0) {
/* 3367 */         return Math.max(getPreferredSize(param1Int), this.max);
/*      */       }
/* 3369 */       return this.size;
/*      */     }
/*      */     
/*      */     String getMatchDescription() {
/* 3373 */       return (this.matches == null) ? "" : this.matches.toString();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 3377 */       return super.toString() + getMatchDescription();
/*      */     }
/*      */ 
/*      */     
/*      */     boolean willHaveZeroSize(boolean param1Boolean) {
/* 3382 */       return param1Boolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class AutoPreferredGapMatch
/*      */   {
/*      */     public final GroupLayout.ComponentSpring source;
/*      */     
/*      */     public final GroupLayout.ComponentSpring target;
/*      */ 
/*      */     
/*      */     AutoPreferredGapMatch(GroupLayout.ComponentSpring param1ComponentSpring1, GroupLayout.ComponentSpring param1ComponentSpring2) {
/* 3396 */       this.source = param1ComponentSpring1;
/* 3397 */       this.target = param1ComponentSpring2;
/*      */     }
/*      */     
/*      */     private String toString(GroupLayout.ComponentSpring param1ComponentSpring) {
/* 3401 */       return param1ComponentSpring.getComponent().getName();
/*      */     }
/*      */     
/*      */     public String toString() {
/* 3405 */       return "[" + toString(this.source) + "-" + toString(this.target) + "]";
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ContainerAutoPreferredGapSpring
/*      */     extends AutoPreferredGapSpring
/*      */   {
/*      */     private List<GroupLayout.ComponentSpring> targets;
/*      */ 
/*      */ 
/*      */     
/*      */     ContainerAutoPreferredGapSpring() {
/* 3419 */       setUserCreated(true);
/*      */     }
/*      */     
/*      */     ContainerAutoPreferredGapSpring(int param1Int1, int param1Int2) {
/* 3423 */       super(param1Int1, param1Int2);
/* 3424 */       setUserCreated(true);
/*      */     }
/*      */     
/*      */     public void addTarget(GroupLayout.ComponentSpring param1ComponentSpring, int param1Int) {
/* 3428 */       if (this.targets == null) {
/* 3429 */         this.targets = new ArrayList<>(1);
/*      */       }
/* 3431 */       this.targets.add(param1ComponentSpring);
/*      */     }
/*      */     
/*      */     public void calculatePadding(int param1Int) {
/* 3435 */       LayoutStyle layoutStyle = GroupLayout.this.getLayoutStyle0();
/* 3436 */       int i = 0;
/*      */       
/* 3438 */       this.size = 0;
/* 3439 */       if (this.targets != null) {
/*      */         byte b;
/* 3441 */         if (param1Int == 0) {
/* 3442 */           if (GroupLayout.this.isLeftToRight()) {
/* 3443 */             b = 7;
/*      */           } else {
/* 3445 */             b = 3;
/*      */           } 
/*      */         } else {
/* 3448 */           b = 5;
/*      */         } 
/* 3450 */         for (int j = this.targets.size() - 1; j >= 0; j--) {
/* 3451 */           GroupLayout.ComponentSpring componentSpring = this.targets.get(j);
/* 3452 */           int k = 10;
/* 3453 */           if (componentSpring.getComponent() instanceof JComponent) {
/* 3454 */             k = layoutStyle.getContainerGap((JComponent)componentSpring
/* 3455 */                 .getComponent(), b, GroupLayout.this
/* 3456 */                 .host);
/* 3457 */             i = Math.max(k, i);
/* 3458 */             k -= componentSpring.getOrigin();
/*      */           } else {
/* 3460 */             i = Math.max(k, i);
/*      */           } 
/* 3462 */           this.size = Math.max(this.size, k);
/*      */         } 
/*      */       } else {
/*      */         byte b;
/* 3466 */         if (param1Int == 0) {
/* 3467 */           if (GroupLayout.this.isLeftToRight()) {
/* 3468 */             b = 3;
/*      */           } else {
/* 3470 */             b = 7;
/*      */           } 
/*      */         } else {
/* 3473 */           b = 5;
/*      */         } 
/* 3475 */         if (this.sources != null) {
/* 3476 */           for (int j = this.sources.size() - 1; j >= 0; j--) {
/* 3477 */             GroupLayout.ComponentSpring componentSpring = this.sources.get(j);
/* 3478 */             i = Math.max(i, 
/* 3479 */                 updateSize(layoutStyle, componentSpring, b));
/*      */           } 
/* 3481 */         } else if (this.source != null) {
/* 3482 */           i = updateSize(layoutStyle, this.source, b);
/*      */         } 
/*      */       } 
/* 3485 */       if (this.lastSize != Integer.MIN_VALUE) {
/* 3486 */         this.size += Math.min(i, this.lastSize);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private int updateSize(LayoutStyle param1LayoutStyle, GroupLayout.ComponentSpring param1ComponentSpring, int param1Int) {
/* 3492 */       int i = 10;
/* 3493 */       if (param1ComponentSpring.getComponent() instanceof JComponent) {
/* 3494 */         i = param1LayoutStyle.getContainerGap((JComponent)param1ComponentSpring
/* 3495 */             .getComponent(), param1Int, GroupLayout.this
/* 3496 */             .host);
/*      */       }
/* 3498 */       int j = Math.max(0, getParent().getSize() - param1ComponentSpring
/* 3499 */           .getSize() - param1ComponentSpring.getOrigin());
/* 3500 */       this.size = Math.max(this.size, i - j);
/* 3501 */       return i;
/*      */     }
/*      */     
/*      */     String getMatchDescription() {
/* 3505 */       if (this.targets != null) {
/* 3506 */         return "leading: " + this.targets.toString();
/*      */       }
/* 3508 */       if (this.sources != null) {
/* 3509 */         return "trailing: " + this.sources.toString();
/*      */       }
/* 3511 */       return "--";
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class LinkInfo
/*      */   {
/*      */     private final int axis;
/*      */     
/*      */     private final List<GroupLayout.ComponentInfo> linked;
/*      */     private int size;
/*      */     
/*      */     LinkInfo(int param1Int) {
/* 3524 */       this.linked = new ArrayList<>();
/* 3525 */       this.size = Integer.MIN_VALUE;
/* 3526 */       this.axis = param1Int;
/*      */     }
/*      */     
/*      */     public void add(GroupLayout.ComponentInfo param1ComponentInfo) {
/* 3530 */       LinkInfo linkInfo = param1ComponentInfo.getLinkInfo(this.axis, false);
/* 3531 */       if (linkInfo == null) {
/* 3532 */         this.linked.add(param1ComponentInfo);
/* 3533 */         param1ComponentInfo.setLinkInfo(this.axis, this);
/* 3534 */       } else if (linkInfo != this) {
/* 3535 */         this.linked.addAll(linkInfo.linked);
/* 3536 */         for (GroupLayout.ComponentInfo componentInfo : linkInfo.linked) {
/* 3537 */           componentInfo.setLinkInfo(this.axis, this);
/*      */         }
/*      */       } 
/* 3540 */       clearCachedSize();
/*      */     }
/*      */     
/*      */     public void remove(GroupLayout.ComponentInfo param1ComponentInfo) {
/* 3544 */       this.linked.remove(param1ComponentInfo);
/* 3545 */       param1ComponentInfo.setLinkInfo(this.axis, null);
/* 3546 */       if (this.linked.size() == 1) {
/* 3547 */         ((GroupLayout.ComponentInfo)this.linked.get(0)).setLinkInfo(this.axis, null);
/*      */       }
/* 3549 */       clearCachedSize();
/*      */     }
/*      */     
/*      */     public void clearCachedSize() {
/* 3553 */       this.size = Integer.MIN_VALUE;
/*      */     }
/*      */     
/*      */     public int getSize(int param1Int) {
/* 3557 */       if (this.size == Integer.MIN_VALUE) {
/* 3558 */         this.size = calculateLinkedSize(param1Int);
/*      */       }
/* 3560 */       return this.size;
/*      */     }
/*      */     
/*      */     private int calculateLinkedSize(int param1Int) {
/* 3564 */       int i = 0;
/* 3565 */       for (GroupLayout.ComponentInfo componentInfo : this.linked) {
/*      */         GroupLayout.ComponentSpring componentSpring;
/* 3567 */         if (param1Int == 0) {
/* 3568 */           componentSpring = componentInfo.horizontalSpring;
/*      */         } else {
/* 3570 */           assert param1Int == 1;
/* 3571 */           componentSpring = componentInfo.verticalSpring;
/*      */         } 
/* 3573 */         i = Math.max(i, componentSpring
/* 3574 */             .calculateNonlinkedPreferredSize(param1Int));
/*      */       } 
/* 3576 */       return i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class ComponentInfo
/*      */   {
/*      */     private Component component;
/*      */ 
/*      */     
/*      */     GroupLayout.ComponentSpring horizontalSpring;
/*      */ 
/*      */     
/*      */     GroupLayout.ComponentSpring verticalSpring;
/*      */     
/*      */     private GroupLayout.LinkInfo horizontalMaster;
/*      */     
/*      */     private GroupLayout.LinkInfo verticalMaster;
/*      */     
/*      */     private boolean visible;
/*      */     
/*      */     private Boolean honorsVisibility;
/*      */ 
/*      */     
/*      */     ComponentInfo(Component param1Component) {
/* 3602 */       this.component = param1Component;
/* 3603 */       updateVisibility();
/*      */     }
/*      */ 
/*      */     
/*      */     public void dispose() {
/* 3608 */       removeSpring(this.horizontalSpring);
/* 3609 */       this.horizontalSpring = null;
/* 3610 */       removeSpring(this.verticalSpring);
/* 3611 */       this.verticalSpring = null;
/*      */       
/* 3613 */       if (this.horizontalMaster != null) {
/* 3614 */         this.horizontalMaster.remove(this);
/*      */       }
/* 3616 */       if (this.verticalMaster != null) {
/* 3617 */         this.verticalMaster.remove(this);
/*      */       }
/*      */     }
/*      */     
/*      */     void setHonorsVisibility(Boolean param1Boolean) {
/* 3622 */       this.honorsVisibility = param1Boolean;
/*      */     }
/*      */     
/*      */     private void removeSpring(GroupLayout.Spring param1Spring) {
/* 3626 */       if (param1Spring != null) {
/* 3627 */         ((GroupLayout.Group)param1Spring.getParent()).springs.remove(param1Spring);
/*      */       }
/*      */     }
/*      */     
/*      */     public boolean isVisible() {
/* 3632 */       return this.visible;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean updateVisibility() {
/*      */       boolean bool1;
/* 3642 */       if (this.honorsVisibility == null) {
/* 3643 */         bool1 = GroupLayout.this.getHonorsVisibility();
/*      */       } else {
/* 3645 */         bool1 = this.honorsVisibility.booleanValue();
/*      */       } 
/*      */       
/* 3648 */       boolean bool2 = bool1 ? this.component.isVisible() : true;
/* 3649 */       if (this.visible != bool2) {
/* 3650 */         this.visible = bool2;
/* 3651 */         return true;
/*      */       } 
/* 3653 */       return false;
/*      */     }
/*      */     
/*      */     public void setBounds(Insets param1Insets, int param1Int, boolean param1Boolean) {
/* 3657 */       int i = this.horizontalSpring.getOrigin();
/* 3658 */       int j = this.horizontalSpring.getSize();
/* 3659 */       int k = this.verticalSpring.getOrigin();
/* 3660 */       int m = this.verticalSpring.getSize();
/*      */       
/* 3662 */       if (!param1Boolean) {
/* 3663 */         i = param1Int - i - j;
/*      */       }
/* 3665 */       this.component.setBounds(i + param1Insets.left, k + param1Insets.top, j, m);
/*      */     }
/*      */     
/*      */     public void setComponent(Component param1Component) {
/* 3669 */       this.component = param1Component;
/* 3670 */       if (this.horizontalSpring != null) {
/* 3671 */         this.horizontalSpring.setComponent(param1Component);
/*      */       }
/* 3673 */       if (this.verticalSpring != null) {
/* 3674 */         this.verticalSpring.setComponent(param1Component);
/*      */       }
/*      */     }
/*      */     
/*      */     public Component getComponent() {
/* 3679 */       return this.component;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLinked(int param1Int) {
/* 3687 */       if (param1Int == 0) {
/* 3688 */         return (this.horizontalMaster != null);
/*      */       }
/* 3690 */       assert param1Int == 1;
/* 3691 */       return (this.verticalMaster != null);
/*      */     }
/*      */     
/*      */     private void setLinkInfo(int param1Int, GroupLayout.LinkInfo param1LinkInfo) {
/* 3695 */       if (param1Int == 0) {
/* 3696 */         this.horizontalMaster = param1LinkInfo;
/*      */       } else {
/* 3698 */         assert param1Int == 1;
/* 3699 */         this.verticalMaster = param1LinkInfo;
/*      */       } 
/*      */     }
/*      */     
/*      */     public GroupLayout.LinkInfo getLinkInfo(int param1Int) {
/* 3704 */       return getLinkInfo(param1Int, true);
/*      */     }
/*      */     
/*      */     private GroupLayout.LinkInfo getLinkInfo(int param1Int, boolean param1Boolean) {
/* 3708 */       if (param1Int == 0) {
/* 3709 */         if (this.horizontalMaster == null && param1Boolean)
/*      */         {
/*      */           
/* 3712 */           (new GroupLayout.LinkInfo(0)).add(this);
/*      */         }
/* 3714 */         return this.horizontalMaster;
/*      */       } 
/* 3716 */       assert param1Int == 1;
/* 3717 */       if (this.verticalMaster == null && param1Boolean)
/*      */       {
/*      */         
/* 3720 */         (new GroupLayout.LinkInfo(1)).add(this);
/*      */       }
/* 3722 */       return this.verticalMaster;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clearCachedSize() {
/* 3727 */       if (this.horizontalMaster != null) {
/* 3728 */         this.horizontalMaster.clearCachedSize();
/*      */       }
/* 3730 */       if (this.verticalMaster != null) {
/* 3731 */         this.verticalMaster.clearCachedSize();
/*      */       }
/*      */     }
/*      */     
/*      */     int getLinkSize(int param1Int1, int param1Int2) {
/* 3736 */       if (param1Int1 == 0) {
/* 3737 */         return this.horizontalMaster.getSize(param1Int1);
/*      */       }
/* 3739 */       assert param1Int1 == 1;
/* 3740 */       return this.verticalMaster.getSize(param1Int1);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/GroupLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */