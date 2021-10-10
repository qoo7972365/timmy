/*     */ package javax.swing.border;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.beans.ConstructorProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompoundBorder
/*     */   extends AbstractBorder
/*     */ {
/*     */   protected Border outsideBorder;
/*     */   protected Border insideBorder;
/*     */   
/*     */   public CompoundBorder() {
/*  66 */     this.outsideBorder = null;
/*  67 */     this.insideBorder = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"outsideBorder", "insideBorder"})
/*     */   public CompoundBorder(Border paramBorder1, Border paramBorder2) {
/*  78 */     this.outsideBorder = paramBorder1;
/*  79 */     this.insideBorder = paramBorder2;
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
/*     */   public boolean isBorderOpaque() {
/*  91 */     return ((this.outsideBorder == null || this.outsideBorder.isBorderOpaque()) && (this.insideBorder == null || this.insideBorder
/*  92 */       .isBorderOpaque()));
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
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 111 */     int i = paramInt1;
/* 112 */     int j = paramInt2;
/* 113 */     int k = paramInt3;
/* 114 */     int m = paramInt4;
/*     */     
/* 116 */     if (this.outsideBorder != null) {
/* 117 */       this.outsideBorder.paintBorder(paramComponent, paramGraphics, i, j, k, m);
/*     */       
/* 119 */       Insets insets = this.outsideBorder.getBorderInsets(paramComponent);
/* 120 */       i += insets.left;
/* 121 */       j += insets.top;
/* 122 */       k = k - insets.right - insets.left;
/* 123 */       m = m - insets.bottom - insets.top;
/*     */     } 
/* 125 */     if (this.insideBorder != null) {
/* 126 */       this.insideBorder.paintBorder(paramComponent, paramGraphics, i, j, k, m);
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
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/* 138 */     paramInsets.top = paramInsets.left = paramInsets.right = paramInsets.bottom = 0;
/* 139 */     if (this.outsideBorder != null) {
/* 140 */       Insets insets = this.outsideBorder.getBorderInsets(paramComponent);
/* 141 */       paramInsets.top += insets.top;
/* 142 */       paramInsets.left += insets.left;
/* 143 */       paramInsets.right += insets.right;
/* 144 */       paramInsets.bottom += insets.bottom;
/*     */     } 
/* 146 */     if (this.insideBorder != null) {
/* 147 */       Insets insets = this.insideBorder.getBorderInsets(paramComponent);
/* 148 */       paramInsets.top += insets.top;
/* 149 */       paramInsets.left += insets.left;
/* 150 */       paramInsets.right += insets.right;
/* 151 */       paramInsets.bottom += insets.bottom;
/*     */     } 
/* 153 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getOutsideBorder() {
/* 160 */     return this.outsideBorder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Border getInsideBorder() {
/* 167 */     return this.insideBorder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/border/CompoundBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */