/*     */ package javax.swing;
/*     */ 
/*     */ import java.awt.AWTError;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
/*     */ import java.beans.ConstructorProperties;
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
/*     */ public class OverlayLayout
/*     */   implements LayoutManager2, Serializable
/*     */ {
/*     */   private Container target;
/*     */   private SizeRequirements[] xChildren;
/*     */   private SizeRequirements[] yChildren;
/*     */   private SizeRequirements xTotal;
/*     */   private SizeRequirements yTotal;
/*     */   
/*     */   @ConstructorProperties({"target"})
/*     */   public OverlayLayout(Container paramContainer) {
/*  65 */     this.target = paramContainer;
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
/*  76 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateLayout(Container paramContainer) {
/*  86 */     checkContainer(paramContainer);
/*  87 */     this.xChildren = null;
/*  88 */     this.yChildren = null;
/*  89 */     this.xTotal = null;
/*  90 */     this.yTotal = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLayoutComponent(String paramString, Component paramComponent) {
/* 101 */     invalidateLayout(paramComponent.getParent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeLayoutComponent(Component paramComponent) {
/* 111 */     invalidateLayout(paramComponent.getParent());
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
/*     */   public void addLayoutComponent(Component paramComponent, Object paramObject) {
/* 123 */     invalidateLayout(paramComponent.getParent());
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
/*     */   public Dimension preferredLayoutSize(Container paramContainer) {
/* 137 */     checkContainer(paramContainer);
/* 138 */     checkRequests();
/*     */     
/* 140 */     Dimension dimension = new Dimension(this.xTotal.preferred, this.yTotal.preferred);
/* 141 */     Insets insets = paramContainer.getInsets();
/* 142 */     dimension.width += insets.left + insets.right;
/* 143 */     dimension.height += insets.top + insets.bottom;
/* 144 */     return dimension;
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
/*     */   public Dimension minimumLayoutSize(Container paramContainer) {
/* 157 */     checkContainer(paramContainer);
/* 158 */     checkRequests();
/*     */     
/* 160 */     Dimension dimension = new Dimension(this.xTotal.minimum, this.yTotal.minimum);
/* 161 */     Insets insets = paramContainer.getInsets();
/* 162 */     dimension.width += insets.left + insets.right;
/* 163 */     dimension.height += insets.top + insets.bottom;
/* 164 */     return dimension;
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
/* 179 */     checkContainer(paramContainer);
/* 180 */     checkRequests();
/*     */     
/* 182 */     Dimension dimension = new Dimension(this.xTotal.maximum, this.yTotal.maximum);
/* 183 */     Insets insets = paramContainer.getInsets();
/* 184 */     dimension.width += insets.left + insets.right;
/* 185 */     dimension.height += insets.top + insets.bottom;
/* 186 */     return dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLayoutAlignmentX(Container paramContainer) {
/* 196 */     checkContainer(paramContainer);
/* 197 */     checkRequests();
/* 198 */     return this.xTotal.alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getLayoutAlignmentY(Container paramContainer) {
/* 208 */     checkContainer(paramContainer);
/* 209 */     checkRequests();
/* 210 */     return this.yTotal.alignment;
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
/*     */   public void layoutContainer(Container paramContainer) {
/* 222 */     checkContainer(paramContainer);
/* 223 */     checkRequests();
/*     */     
/* 225 */     int i = paramContainer.getComponentCount();
/* 226 */     int[] arrayOfInt1 = new int[i];
/* 227 */     int[] arrayOfInt2 = new int[i];
/* 228 */     int[] arrayOfInt3 = new int[i];
/* 229 */     int[] arrayOfInt4 = new int[i];
/*     */ 
/*     */     
/* 232 */     Dimension dimension = paramContainer.getSize();
/* 233 */     Insets insets = paramContainer.getInsets();
/* 234 */     dimension.width -= insets.left + insets.right;
/* 235 */     dimension.height -= insets.top + insets.bottom;
/* 236 */     SizeRequirements.calculateAlignedPositions(dimension.width, this.xTotal, this.xChildren, arrayOfInt1, arrayOfInt2);
/*     */ 
/*     */     
/* 239 */     SizeRequirements.calculateAlignedPositions(dimension.height, this.yTotal, this.yChildren, arrayOfInt3, arrayOfInt4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     for (byte b = 0; b < i; b++) {
/* 245 */       Component component = paramContainer.getComponent(b);
/* 246 */       component.setBounds(insets.left + arrayOfInt1[b], insets.top + arrayOfInt3[b], arrayOfInt2[b], arrayOfInt4[b]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void checkContainer(Container paramContainer) {
/* 252 */     if (this.target != paramContainer) {
/* 253 */       throw new AWTError("OverlayLayout can't be shared");
/*     */     }
/*     */   }
/*     */   
/*     */   void checkRequests() {
/* 258 */     if (this.xChildren == null || this.yChildren == null) {
/*     */ 
/*     */       
/* 261 */       int i = this.target.getComponentCount();
/* 262 */       this.xChildren = new SizeRequirements[i];
/* 263 */       this.yChildren = new SizeRequirements[i];
/* 264 */       for (byte b = 0; b < i; b++) {
/* 265 */         Component component = this.target.getComponent(b);
/* 266 */         Dimension dimension1 = component.getMinimumSize();
/* 267 */         Dimension dimension2 = component.getPreferredSize();
/* 268 */         Dimension dimension3 = component.getMaximumSize();
/* 269 */         this.xChildren[b] = new SizeRequirements(dimension1.width, dimension2.width, dimension3.width, component
/*     */             
/* 271 */             .getAlignmentX());
/* 272 */         this.yChildren[b] = new SizeRequirements(dimension1.height, dimension2.height, dimension3.height, component
/*     */             
/* 274 */             .getAlignmentY());
/*     */       } 
/*     */       
/* 277 */       this.xTotal = SizeRequirements.getAlignedSizeRequirements(this.xChildren);
/* 278 */       this.yTotal = SizeRequirements.getAlignedSizeRequirements(this.yChildren);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/OverlayLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */