/*     */ package java.awt;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlowLayout
/*     */   implements LayoutManager, Serializable
/*     */ {
/*     */   public static final int LEFT = 0;
/*     */   public static final int CENTER = 1;
/*     */   public static final int RIGHT = 2;
/*     */   public static final int LEADING = 3;
/*     */   public static final int TRAILING = 4;
/*     */   int align;
/*     */   int newAlign;
/*     */   int hgap;
/*     */   int vgap;
/*     */   private boolean alignOnBaseline;
/*     */   private static final long serialVersionUID = -7262534875583282631L;
/*     */   private static final int currentSerialVersion = 1;
/*     */   private int serialVersionOnStream;
/*     */   
/*     */   public FlowLayout() {
/* 203 */     this(1, 5, 5);
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
/*     */   public FlowLayout(int paramInt) {
/* 216 */     this(paramInt, 5, 5);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAlignment() {
/*     */     return this.newAlign;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlignment(int paramInt) {
/*     */     this.newAlign = paramInt;
/*     */     switch (paramInt) {
/*     */       case 3:
/*     */         this.align = 0;
/*     */         return;
/*     */       case 4:
/*     */         this.align = 2;
/*     */         return;
/*     */     } 
/*     */     this.align = paramInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHgap() {
/*     */     return this.hgap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHgap(int paramInt) {
/*     */     this.hgap = paramInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVgap() {
/*     */     return this.vgap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVgap(int paramInt) {
/*     */     this.vgap = paramInt;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FlowLayout(int paramInt1, int paramInt2, int paramInt3) {
/* 659 */     this.serialVersionOnStream = 1;
/*     */     this.hgap = paramInt2;
/*     */     this.vgap = paramInt3;
/*     */     setAlignment(paramInt1);
/*     */   }
/*     */   public void setAlignOnBaseline(boolean paramBoolean) {
/*     */     this.alignOnBaseline = paramBoolean;
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 669 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 671 */     if (this.serialVersionOnStream < 1)
/*     */     {
/* 673 */       setAlignment(this.align);
/*     */     }
/* 675 */     this.serialVersionOnStream = 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAlignOnBaseline() {
/*     */     return this.alignOnBaseline;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 684 */     String str = "";
/* 685 */     switch (this.align) { case 0:
/* 686 */         str = ",align=left"; break;
/* 687 */       case 1: str = ",align=center"; break;
/* 688 */       case 2: str = ",align=right"; break;
/* 689 */       case 3: str = ",align=leading"; break;
/* 690 */       case 4: str = ",align=trailing"; break; }
/*     */     
/* 692 */     return getClass().getName() + "[hgap=" + this.hgap + ",vgap=" + this.vgap + str + "]";
/*     */   }
/*     */   
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*     */   
/*     */   public void removeLayoutComponent(Component paramComponent) {}
/*     */   
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/*     */     synchronized (paramContainer.getTreeLock()) {
/*     */       Dimension dimension = new Dimension(0, 0);
/*     */       int i = paramContainer.getComponentCount();
/*     */       boolean bool = true;
/*     */       boolean bool1 = getAlignOnBaseline();
/*     */       int j = 0;
/*     */       int k = 0;
/*     */       for (byte b = 0; b < i; b++) {
/*     */         Component component = paramContainer.getComponent(b);
/*     */         if (component.isVisible()) {
/*     */           Dimension dimension1 = component.getPreferredSize();
/*     */           dimension.height = Math.max(dimension.height, dimension1.height);
/*     */           if (bool) {
/*     */             bool = false;
/*     */           } else {
/*     */             dimension.width += this.hgap;
/*     */           } 
/*     */           dimension.width += dimension1.width;
/*     */           if (bool1) {
/*     */             int m = component.getBaseline(dimension1.width, dimension1.height);
/*     */             if (m >= 0) {
/*     */               j = Math.max(j, m);
/*     */               k = Math.max(k, dimension1.height - m);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       if (bool1)
/*     */         dimension.height = Math.max(j + k, dimension.height); 
/*     */       Insets insets = paramContainer.getInsets();
/*     */       dimension.width += insets.left + insets.right + this.hgap * 2;
/*     */       dimension.height += insets.top + insets.bottom + this.vgap * 2;
/*     */       return dimension;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/*     */     synchronized (paramContainer.getTreeLock()) {
/*     */       boolean bool = getAlignOnBaseline();
/*     */       Dimension dimension = new Dimension(0, 0);
/*     */       int i = paramContainer.getComponentCount();
/*     */       int j = 0;
/*     */       int k = 0;
/*     */       boolean bool1 = true;
/*     */       for (byte b = 0; b < i; b++) {
/*     */         Component component = paramContainer.getComponent(b);
/*     */         if (component.visible) {
/*     */           Dimension dimension1 = component.getMinimumSize();
/*     */           dimension.height = Math.max(dimension.height, dimension1.height);
/*     */           if (bool1) {
/*     */             bool1 = false;
/*     */           } else {
/*     */             dimension.width += this.hgap;
/*     */           } 
/*     */           dimension.width += dimension1.width;
/*     */           if (bool) {
/*     */             int m = component.getBaseline(dimension1.width, dimension1.height);
/*     */             if (m >= 0) {
/*     */               j = Math.max(j, m);
/*     */               k = Math.max(k, dimension.height - m);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       if (bool)
/*     */         dimension.height = Math.max(j + k, dimension.height); 
/*     */       Insets insets = paramContainer.getInsets();
/*     */       dimension.width += insets.left + insets.right + this.hgap * 2;
/*     */       dimension.height += insets.top + insets.bottom + this.vgap * 2;
/*     */       return dimension;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int moveComponents(Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
/*     */     switch (this.newAlign) {
/*     */       case 0:
/*     */         paramInt1 += paramBoolean1 ? 0 : paramInt3;
/*     */         break;
/*     */       case 1:
/*     */         paramInt1 += paramInt3 / 2;
/*     */         break;
/*     */       case 2:
/*     */         paramInt1 += paramBoolean1 ? paramInt3 : 0;
/*     */         break;
/*     */       case 4:
/*     */         paramInt1 += paramInt3;
/*     */         break;
/*     */     } 
/*     */     int i = 0;
/*     */     int j = 0;
/*     */     int k = 0;
/*     */     if (paramBoolean2) {
/*     */       int n = 0;
/*     */       for (int i1 = paramInt5; i1 < paramInt6; i1++) {
/*     */         Component component = paramContainer.getComponent(i1);
/*     */         if (component.visible)
/*     */           if (paramArrayOfint1[i1] >= 0) {
/*     */             i = Math.max(i, paramArrayOfint1[i1]);
/*     */             n = Math.max(n, paramArrayOfint2[i1]);
/*     */           } else {
/*     */             j = Math.max(component.getHeight(), j);
/*     */           }  
/*     */       } 
/*     */       paramInt4 = Math.max(i + n, j);
/*     */       k = (paramInt4 - i - n) / 2;
/*     */     } 
/*     */     for (int m = paramInt5; m < paramInt6; m++) {
/*     */       Component component = paramContainer.getComponent(m);
/*     */       if (component.isVisible()) {
/*     */         int n;
/*     */         if (paramBoolean2 && paramArrayOfint1[m] >= 0) {
/*     */           n = paramInt2 + k + i - paramArrayOfint1[m];
/*     */         } else {
/*     */           n = paramInt2 + (paramInt4 - component.height) / 2;
/*     */         } 
/*     */         if (paramBoolean1) {
/*     */           component.setLocation(paramInt1, n);
/*     */         } else {
/*     */           component.setLocation(paramContainer.width - paramInt1 - component.width, n);
/*     */         } 
/*     */         paramInt1 += component.width + this.hgap;
/*     */       } 
/*     */     } 
/*     */     return paramInt4;
/*     */   }
/*     */   
/*     */   public void layoutContainer(Container paramContainer) {
/*     */     synchronized (paramContainer.getTreeLock()) {
/*     */       Insets insets = paramContainer.getInsets();
/*     */       int i = paramContainer.width - insets.left + insets.right + this.hgap * 2;
/*     */       int j = paramContainer.getComponentCount();
/*     */       int k = 0, m = insets.top + this.vgap;
/*     */       int n = 0;
/*     */       byte b1 = 0;
/*     */       boolean bool1 = paramContainer.getComponentOrientation().isLeftToRight();
/*     */       boolean bool2 = getAlignOnBaseline();
/*     */       int[] arrayOfInt1 = null;
/*     */       int[] arrayOfInt2 = null;
/*     */       if (bool2) {
/*     */         arrayOfInt1 = new int[j];
/*     */         arrayOfInt2 = new int[j];
/*     */       } 
/*     */       for (byte b2 = 0; b2 < j; b2++) {
/*     */         Component component = paramContainer.getComponent(b2);
/*     */         if (component.isVisible()) {
/*     */           Dimension dimension = component.getPreferredSize();
/*     */           component.setSize(dimension.width, dimension.height);
/*     */           if (bool2) {
/*     */             int i1 = component.getBaseline(dimension.width, dimension.height);
/*     */             if (i1 >= 0) {
/*     */               arrayOfInt1[b2] = i1;
/*     */               arrayOfInt2[b2] = dimension.height - i1;
/*     */             } else {
/*     */               arrayOfInt1[b2] = -1;
/*     */             } 
/*     */           } 
/*     */           if (!k || k + dimension.width <= i) {
/*     */             if (k > 0)
/*     */               k += this.hgap; 
/*     */             k += dimension.width;
/*     */             n = Math.max(n, dimension.height);
/*     */           } else {
/*     */             n = moveComponents(paramContainer, insets.left + this.hgap, m, i - k, n, b1, b2, bool1, bool2, arrayOfInt1, arrayOfInt2);
/*     */             k = dimension.width;
/*     */             m += this.vgap + n;
/*     */             n = dimension.height;
/*     */             b1 = b2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       moveComponents(paramContainer, insets.left + this.hgap, m, i - k, n, b1, j, bool1, bool2, arrayOfInt1, arrayOfInt2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/FlowLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */