/*     */ package javax.swing.colorchooser;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager;
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
/*     */ class SmartGridLayout
/*     */   implements LayoutManager, Serializable
/*     */ {
/*  43 */   int rows = 2;
/*  44 */   int columns = 2;
/*  45 */   int xGap = 2;
/*  46 */   int yGap = 2;
/*  47 */   int componentCount = 0;
/*     */   
/*     */   Component[][] layoutGrid;
/*     */   
/*     */   public SmartGridLayout(int paramInt1, int paramInt2) {
/*  52 */     this.rows = paramInt2;
/*  53 */     this.columns = paramInt1;
/*  54 */     this.layoutGrid = new Component[paramInt1][paramInt2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void layoutContainer(Container paramContainer) {
/*  61 */     buildLayoutGrid(paramContainer);
/*     */     
/*  63 */     int[] arrayOfInt1 = new int[this.rows];
/*  64 */     int[] arrayOfInt2 = new int[this.columns];
/*     */     byte b;
/*  66 */     for (b = 0; b < this.rows; b++) {
/*  67 */       arrayOfInt1[b] = computeRowHeight(b);
/*     */     }
/*     */     
/*  70 */     for (b = 0; b < this.columns; b++) {
/*  71 */       arrayOfInt2[b] = computeColumnWidth(b);
/*     */     }
/*     */ 
/*     */     
/*  75 */     Insets insets = paramContainer.getInsets();
/*     */     
/*  77 */     if (paramContainer.getComponentOrientation().isLeftToRight()) {
/*  78 */       int i = insets.left;
/*  79 */       for (byte b1 = 0; b1 < this.columns; b1++) {
/*  80 */         int j = insets.top;
/*     */         
/*  82 */         for (byte b2 = 0; b2 < this.rows; b2++) {
/*  83 */           Component component = this.layoutGrid[b1][b2];
/*     */           
/*  85 */           component.setBounds(i, j, arrayOfInt2[b1], arrayOfInt1[b2]);
/*     */           
/*  87 */           j += arrayOfInt1[b2] + this.yGap;
/*     */         } 
/*  89 */         i += arrayOfInt2[b1] + this.xGap;
/*     */       } 
/*     */     } else {
/*  92 */       int i = paramContainer.getWidth() - insets.right;
/*  93 */       for (byte b1 = 0; b1 < this.columns; b1++) {
/*  94 */         int j = insets.top;
/*  95 */         i -= arrayOfInt2[b1];
/*     */         
/*  97 */         for (byte b2 = 0; b2 < this.rows; b2++) {
/*  98 */           Component component = this.layoutGrid[b1][b2];
/*     */           
/* 100 */           component.setBounds(i, j, arrayOfInt2[b1], arrayOfInt1[b2]);
/*     */           
/* 102 */           j += arrayOfInt1[b2] + this.yGap;
/*     */         } 
/* 104 */         i -= this.xGap;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 114 */     buildLayoutGrid(paramContainer);
/* 115 */     Insets insets = paramContainer.getInsets();
/*     */ 
/*     */ 
/*     */     
/* 119 */     int i = 0;
/* 120 */     int j = 0;
/*     */     byte b;
/* 122 */     for (b = 0; b < this.rows; b++) {
/* 123 */       i += computeRowHeight(b);
/*     */     }
/*     */     
/* 126 */     for (b = 0; b < this.columns; b++) {
/* 127 */       j += computeColumnWidth(b);
/*     */     }
/*     */     
/* 130 */     i += this.yGap * (this.rows - 1) + insets.top + insets.bottom;
/* 131 */     j += this.xGap * (this.columns - 1) + insets.right + insets.left;
/*     */     
/* 133 */     return new Dimension(j, i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 139 */     return minimumLayoutSize(paramContainer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*     */ 
/*     */   
/*     */   public void removeLayoutComponent(Component paramComponent) {}
/*     */ 
/*     */   
/*     */   private void buildLayoutGrid(Container paramContainer) {
/* 150 */     Component[] arrayOfComponent = paramContainer.getComponents();
/*     */     
/* 152 */     for (byte b = 0; b < arrayOfComponent.length; b++) {
/*     */       
/* 154 */       int i = 0;
/* 155 */       int j = 0;
/*     */       
/* 157 */       if (b != 0) {
/* 158 */         j = b % this.columns;
/* 159 */         i = (b - j) / this.columns;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 164 */       this.layoutGrid[j][i] = arrayOfComponent[b];
/*     */     } 
/*     */   }
/*     */   
/*     */   private int computeColumnWidth(int paramInt) {
/* 169 */     int i = 1;
/* 170 */     for (byte b = 0; b < this.rows; b++) {
/* 171 */       int j = (this.layoutGrid[paramInt][b].getPreferredSize()).width;
/* 172 */       if (j > i) {
/* 173 */         i = j;
/*     */       }
/*     */     } 
/* 176 */     return i;
/*     */   }
/*     */   
/*     */   private int computeRowHeight(int paramInt) {
/* 180 */     int i = 1;
/* 181 */     for (byte b = 0; b < this.columns; b++) {
/* 182 */       int j = (this.layoutGrid[b][paramInt].getPreferredSize()).height;
/* 183 */       if (j > i) {
/* 184 */         i = j;
/*     */       }
/*     */     } 
/* 187 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/colorchooser/SmartGridLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */