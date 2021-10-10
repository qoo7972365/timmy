/*     */ package javax.swing.border;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
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
/*     */ 
/*     */ public class EmptyBorder
/*     */   extends AbstractBorder
/*     */   implements Serializable
/*     */ {
/*     */   protected int left;
/*     */   protected int right;
/*     */   protected int top;
/*     */   protected int bottom;
/*     */   
/*     */   public EmptyBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  62 */     this.top = paramInt1;
/*  63 */     this.right = paramInt4;
/*  64 */     this.bottom = paramInt3;
/*  65 */     this.left = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"borderInsets"})
/*     */   public EmptyBorder(Insets paramInsets) {
/*  74 */     this.top = paramInsets.top;
/*  75 */     this.right = paramInsets.right;
/*  76 */     this.bottom = paramInsets.bottom;
/*  77 */     this.left = paramInsets.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/*  92 */     paramInsets.left = this.left;
/*  93 */     paramInsets.top = this.top;
/*  94 */     paramInsets.right = this.right;
/*  95 */     paramInsets.bottom = this.bottom;
/*  96 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets() {
/* 104 */     return new Insets(this.top, this.left, this.bottom, this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/border/EmptyBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */