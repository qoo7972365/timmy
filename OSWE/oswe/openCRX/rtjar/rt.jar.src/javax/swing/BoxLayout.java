/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.Component;
/*     */ import java.awt.ComponentOrientation;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.io.PrintStream;
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
/*     */ public class BoxLayout
/*     */   implements LayoutManager2, Serializable
/*     */ {
/*     */   public static final int X_AXIS = 0;
/*     */   public static final int Y_AXIS = 1;
/*     */   public static final int LINE_AXIS = 2;
/*     */   public static final int PAGE_AXIS = 3;
/*     */   private int axis;
/*     */   private Container target;
/*     */   private transient SizeRequirements[] xChildren;
/*     */   private transient SizeRequirements[] yChildren;
/*     */   private transient SizeRequirements xTotal;
/*     */   private transient SizeRequirements yTotal;
/*     */   private transient PrintStream dbg;
/*     */   
/*     */   @ConstructorProperties({"target", "axis"})
/*     */   public BoxLayout(Container paramContainer, int paramInt) {
/* 180 */     if (paramInt != 0 && paramInt != 1 && paramInt != 2 && paramInt != 3)
/*     */     {
/* 182 */       throw new AWTError("Invalid axis");
/*     */     }
/* 184 */     this.axis = paramInt;
/* 185 */     this.target = paramContainer;
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
/*     */   BoxLayout(Container paramContainer, int paramInt, PrintStream paramPrintStream) {
/* 203 */     this(paramContainer, paramInt);
/* 204 */     this.dbg = paramPrintStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Container getTarget() {
/* 215 */     return this.target;
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
/*     */   public final int getAxis() {
/* 231 */     return this.axis;
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
/*     */   public synchronized void invalidateLayout(Container paramContainer) {
/* 249 */     checkContainer(paramContainer);
/* 250 */     this.xChildren = null;
/* 251 */     this.yChildren = null;
/* 252 */     this.xTotal = null;
/* 253 */     this.yTotal = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {
/* 263 */     invalidateLayout(paramComponent.getParent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayoutComponent(Component paramComponent) {
/* 272 */     invalidateLayout(paramComponent.getParent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayoutComponent(Component paramComponent, Object paramObject) {
/* 282 */     invalidateLayout(paramComponent.getParent());
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
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/*     */     Dimension dimension;
/* 299 */     synchronized (this) {
/* 300 */       checkContainer(paramContainer);
/* 301 */       checkRequests();
/* 302 */       dimension = new Dimension(this.xTotal.preferred, this.yTotal.preferred);
/*     */     } 
/*     */     
/* 305 */     Insets insets = paramContainer.getInsets();
/* 306 */     dimension.width = (int)Math.min(dimension.width + insets.left + insets.right, 2147483647L);
/* 307 */     dimension.height = (int)Math.min(dimension.height + insets.top + insets.bottom, 2147483647L);
/* 308 */     return dimension;
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
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/*     */     Dimension dimension;
/* 324 */     synchronized (this) {
/* 325 */       checkContainer(paramContainer);
/* 326 */       checkRequests();
/* 327 */       dimension = new Dimension(this.xTotal.minimum, this.yTotal.minimum);
/*     */     } 
/*     */     
/* 330 */     Insets insets = paramContainer.getInsets();
/* 331 */     dimension.width = (int)Math.min(dimension.width + insets.left + insets.right, 2147483647L);
/* 332 */     dimension.height = (int)Math.min(dimension.height + insets.top + insets.bottom, 2147483647L);
/* 333 */     return dimension;
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
/*     */   public Dimension maximumLayoutSize(Container paramContainer) {
/*     */     Dimension dimension;
/* 349 */     synchronized (this) {
/* 350 */       checkContainer(paramContainer);
/* 351 */       checkRequests();
/* 352 */       dimension = new Dimension(this.xTotal.maximum, this.yTotal.maximum);
/*     */     } 
/*     */     
/* 355 */     Insets insets = paramContainer.getInsets();
/* 356 */     dimension.width = (int)Math.min(dimension.width + insets.left + insets.right, 2147483647L);
/* 357 */     dimension.height = (int)Math.min(dimension.height + insets.top + insets.bottom, 2147483647L);
/* 358 */     return dimension;
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
/*     */   public synchronized float getLayoutAlignmentX(Container paramContainer) {
/* 373 */     checkContainer(paramContainer);
/* 374 */     checkRequests();
/* 375 */     return this.xTotal.alignment;
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
/*     */   public synchronized float getLayoutAlignmentY(Container paramContainer) {
/* 390 */     checkContainer(paramContainer);
/* 391 */     checkRequests();
/* 392 */     return this.yTotal.alignment;
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
/*     */   public void layoutContainer(Container paramContainer) {
/* 405 */     checkContainer(paramContainer);
/* 406 */     int i = paramContainer.getComponentCount();
/* 407 */     int[] arrayOfInt1 = new int[i];
/* 408 */     int[] arrayOfInt2 = new int[i];
/* 409 */     int[] arrayOfInt3 = new int[i];
/* 410 */     int[] arrayOfInt4 = new int[i];
/*     */     
/* 412 */     Dimension dimension = paramContainer.getSize();
/* 413 */     Insets insets = paramContainer.getInsets();
/* 414 */     dimension.width -= insets.left + insets.right;
/* 415 */     dimension.height -= insets.top + insets.bottom;
/*     */ 
/*     */     
/* 418 */     ComponentOrientation componentOrientation = paramContainer.getComponentOrientation();
/* 419 */     int j = resolveAxis(this.axis, componentOrientation);
/* 420 */     boolean bool = (j != this.axis) ? componentOrientation.isLeftToRight() : true;
/*     */ 
/*     */ 
/*     */     
/* 424 */     synchronized (this) {
/* 425 */       checkRequests();
/*     */       
/* 427 */       if (j == 0) {
/* 428 */         SizeRequirements.calculateTiledPositions(dimension.width, this.xTotal, this.xChildren, arrayOfInt1, arrayOfInt2, bool);
/*     */ 
/*     */         
/* 431 */         SizeRequirements.calculateAlignedPositions(dimension.height, this.yTotal, this.yChildren, arrayOfInt3, arrayOfInt4);
/*     */       }
/*     */       else {
/*     */         
/* 435 */         SizeRequirements.calculateAlignedPositions(dimension.width, this.xTotal, this.xChildren, arrayOfInt1, arrayOfInt2, bool);
/*     */ 
/*     */         
/* 438 */         SizeRequirements.calculateTiledPositions(dimension.height, this.yTotal, this.yChildren, arrayOfInt3, arrayOfInt4);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     byte b;
/*     */     
/* 445 */     for (b = 0; b < i; b++) {
/* 446 */       Component component = paramContainer.getComponent(b);
/* 447 */       component.setBounds((int)Math.min(insets.left + arrayOfInt1[b], 2147483647L), 
/* 448 */           (int)Math.min(insets.top + arrayOfInt3[b], 2147483647L), arrayOfInt2[b], arrayOfInt4[b]);
/*     */     } 
/*     */ 
/*     */     
/* 452 */     if (this.dbg != null) {
/* 453 */       for (b = 0; b < i; b++) {
/* 454 */         Component component = paramContainer.getComponent(b);
/* 455 */         this.dbg.println(component.toString());
/* 456 */         this.dbg.println("X: " + this.xChildren[b]);
/* 457 */         this.dbg.println("Y: " + this.yChildren[b]);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void checkContainer(Container paramContainer) {
/* 464 */     if (this.target != paramContainer) {
/* 465 */       throw new AWTError("BoxLayout can't be shared");
/*     */     }
/*     */   }
/*     */   
/*     */   void checkRequests() {
/* 470 */     if (this.xChildren == null || this.yChildren == null) {
/*     */ 
/*     */       
/* 473 */       int i = this.target.getComponentCount();
/* 474 */       this.xChildren = new SizeRequirements[i];
/* 475 */       this.yChildren = new SizeRequirements[i]; int j;
/* 476 */       for (j = 0; j < i; j++) {
/* 477 */         Component component = this.target.getComponent(j);
/* 478 */         if (!component.isVisible()) {
/* 479 */           this.xChildren[j] = new SizeRequirements(0, 0, 0, component.getAlignmentX());
/* 480 */           this.yChildren[j] = new SizeRequirements(0, 0, 0, component.getAlignmentY());
/*     */         } else {
/*     */           
/* 483 */           Dimension dimension1 = component.getMinimumSize();
/* 484 */           Dimension dimension2 = component.getPreferredSize();
/* 485 */           Dimension dimension3 = component.getMaximumSize();
/* 486 */           this.xChildren[j] = new SizeRequirements(dimension1.width, dimension2.width, dimension3.width, component
/*     */               
/* 488 */               .getAlignmentX());
/* 489 */           this.yChildren[j] = new SizeRequirements(dimension1.height, dimension2.height, dimension3.height, component
/*     */               
/* 491 */               .getAlignmentY());
/*     */         } 
/*     */       } 
/*     */       
/* 495 */       j = resolveAxis(this.axis, this.target.getComponentOrientation());
/*     */       
/* 497 */       if (j == 0) {
/* 498 */         this.xTotal = SizeRequirements.getTiledSizeRequirements(this.xChildren);
/* 499 */         this.yTotal = SizeRequirements.getAlignedSizeRequirements(this.yChildren);
/*     */       } else {
/* 501 */         this.xTotal = SizeRequirements.getAlignedSizeRequirements(this.xChildren);
/* 502 */         this.yTotal = SizeRequirements.getTiledSizeRequirements(this.yChildren);
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
/*     */   private int resolveAxis(int paramInt, ComponentOrientation paramComponentOrientation) {
/*     */     int i;
/* 519 */     if (paramInt == 2) {
/* 520 */       i = paramComponentOrientation.isHorizontal() ? 0 : 1;
/* 521 */     } else if (paramInt == 3) {
/* 522 */       i = paramComponentOrientation.isHorizontal() ? 1 : 0;
/*     */     } else {
/* 524 */       i = paramInt;
/*     */     } 
/* 526 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/BoxLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */