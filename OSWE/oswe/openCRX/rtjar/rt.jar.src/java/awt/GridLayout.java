/*     */ package java.awt;
/*     */ 
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
/*     */ public class GridLayout
/*     */   implements LayoutManager, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7411804673224730901L;
/*     */   int hgap;
/*     */   int vgap;
/*     */   int rows;
/*     */   int cols;
/*     */   
/*     */   public GridLayout() {
/* 150 */     this(1, 0, 0, 0);
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
/*     */   public GridLayout(int paramInt1, int paramInt2) {
/* 166 */     this(paramInt1, paramInt2, 0, 0);
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
/*     */   public GridLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 194 */     if (paramInt1 == 0 && paramInt2 == 0) {
/* 195 */       throw new IllegalArgumentException("rows and cols cannot both be zero");
/*     */     }
/* 197 */     this.rows = paramInt1;
/* 198 */     this.cols = paramInt2;
/* 199 */     this.hgap = paramInt3;
/* 200 */     this.vgap = paramInt4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRows() {
/* 209 */     return this.rows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRows(int paramInt) {
/* 220 */     if (paramInt == 0 && this.cols == 0) {
/* 221 */       throw new IllegalArgumentException("rows and cols cannot both be zero");
/*     */     }
/* 223 */     this.rows = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumns() {
/* 232 */     return this.cols;
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
/*     */   public void setColumns(int paramInt) {
/* 248 */     if (paramInt == 0 && this.rows == 0) {
/* 249 */       throw new IllegalArgumentException("rows and cols cannot both be zero");
/*     */     }
/* 251 */     this.cols = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHgap() {
/* 260 */     return this.hgap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHgap(int paramInt) {
/* 269 */     this.hgap = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVgap() {
/* 278 */     return this.vgap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVgap(int paramInt) {
/* 287 */     this.vgap = paramInt;
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
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayoutComponent(Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 326 */     synchronized (paramContainer.getTreeLock()) {
/* 327 */       Insets insets = paramContainer.getInsets();
/* 328 */       int i = paramContainer.getComponentCount();
/* 329 */       int j = this.rows;
/* 330 */       int k = this.cols;
/*     */       
/* 332 */       if (j > 0) {
/* 333 */         k = (i + j - 1) / j;
/*     */       } else {
/* 335 */         j = (i + k - 1) / k;
/*     */       } 
/* 337 */       int m = 0;
/* 338 */       int n = 0;
/* 339 */       for (byte b = 0; b < i; b++) {
/* 340 */         Component component = paramContainer.getComponent(b);
/* 341 */         Dimension dimension = component.getPreferredSize();
/* 342 */         if (m < dimension.width) {
/* 343 */           m = dimension.width;
/*     */         }
/* 345 */         if (n < dimension.height) {
/* 346 */           n = dimension.height;
/*     */         }
/*     */       } 
/* 349 */       return new Dimension(insets.left + insets.right + k * m + (k - 1) * this.hgap, insets.top + insets.bottom + j * n + (j - 1) * this.vgap);
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
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 375 */     synchronized (paramContainer.getTreeLock()) {
/* 376 */       Insets insets = paramContainer.getInsets();
/* 377 */       int i = paramContainer.getComponentCount();
/* 378 */       int j = this.rows;
/* 379 */       int k = this.cols;
/*     */       
/* 381 */       if (j > 0) {
/* 382 */         k = (i + j - 1) / j;
/*     */       } else {
/* 384 */         j = (i + k - 1) / k;
/*     */       } 
/* 386 */       int m = 0;
/* 387 */       int n = 0;
/* 388 */       for (byte b = 0; b < i; b++) {
/* 389 */         Component component = paramContainer.getComponent(b);
/* 390 */         Dimension dimension = component.getMinimumSize();
/* 391 */         if (m < dimension.width) {
/* 392 */           m = dimension.width;
/*     */         }
/* 394 */         if (n < dimension.height) {
/* 395 */           n = dimension.height;
/*     */         }
/*     */       } 
/* 398 */       return new Dimension(insets.left + insets.right + k * m + (k - 1) * this.hgap, insets.top + insets.bottom + j * n + (j - 1) * this.vgap);
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
/*     */   public void layoutContainer(Container paramContainer) {
/* 422 */     synchronized (paramContainer.getTreeLock()) {
/* 423 */       Insets insets = paramContainer.getInsets();
/* 424 */       int i = paramContainer.getComponentCount();
/* 425 */       int j = this.rows;
/* 426 */       int k = this.cols;
/* 427 */       boolean bool = paramContainer.getComponentOrientation().isLeftToRight();
/*     */       
/* 429 */       if (i == 0) {
/*     */         return;
/*     */       }
/* 432 */       if (j > 0) {
/* 433 */         k = (i + j - 1) / j;
/*     */       } else {
/* 435 */         j = (i + k - 1) / k;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 442 */       int m = (k - 1) * this.hgap;
/* 443 */       int n = paramContainer.width - insets.left + insets.right;
/* 444 */       int i1 = (n - m) / k;
/* 445 */       int i2 = (n - i1 * k + m) / 2;
/*     */       
/* 447 */       int i3 = (j - 1) * this.vgap;
/* 448 */       int i4 = paramContainer.height - insets.top + insets.bottom;
/* 449 */       int i5 = (i4 - i3) / j;
/* 450 */       int i6 = (i4 - i5 * j + i3) / 2;
/* 451 */       if (bool) {
/* 452 */         int i7; for (byte b = 0; b < k; b++, i7 += i1 + this.hgap) {
/* 453 */           int i8; for (byte b1 = 0; b1 < j; b1++, i8 += i5 + this.vgap) {
/* 454 */             int i9 = b1 * k + b;
/* 455 */             if (i9 < i)
/* 456 */               paramContainer.getComponent(i9).setBounds(i7, i8, i1, i5); 
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         int i7;
/* 461 */         for (byte b = 0; b < k; b++, i7 -= i1 + this.hgap) {
/* 462 */           int i8; for (byte b1 = 0; b1 < j; b1++, i8 += i5 + this.vgap) {
/* 463 */             int i9 = b1 * k + b;
/* 464 */             if (i9 < i) {
/* 465 */               paramContainer.getComponent(i9).setBounds(i7, i8, i1, i5);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 478 */     return getClass().getName() + "[hgap=" + this.hgap + ",vgap=" + this.vgap + ",rows=" + this.rows + ",cols=" + this.cols + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/GridLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */