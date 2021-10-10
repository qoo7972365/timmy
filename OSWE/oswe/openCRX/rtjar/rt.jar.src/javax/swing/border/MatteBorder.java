/*     */ package javax.swing.border;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.Icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatteBorder
/*     */   extends EmptyBorder
/*     */ {
/*     */   protected Color color;
/*     */   protected Icon tileIcon;
/*     */   
/*     */   public MatteBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor) {
/*  64 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  65 */     this.color = paramColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatteBorder(Insets paramInsets, Color paramColor) {
/*  75 */     super(paramInsets);
/*  76 */     this.color = paramColor;
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
/*     */   public MatteBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Icon paramIcon) {
/*  88 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*  89 */     this.tileIcon = paramIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatteBorder(Insets paramInsets, Icon paramIcon) {
/*  99 */     super(paramInsets);
/* 100 */     this.tileIcon = paramIcon;
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
/*     */   public MatteBorder(Icon paramIcon) {
/* 112 */     this(-1, -1, -1, -1, paramIcon);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 119 */     Insets insets = getBorderInsets(paramComponent);
/* 120 */     Color color = paramGraphics.getColor();
/* 121 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */ 
/*     */     
/* 124 */     if (this.tileIcon != null) {
/* 125 */       this.color = (this.tileIcon.getIconWidth() == -1) ? Color.gray : null;
/*     */     }
/*     */     
/* 128 */     if (this.color != null) {
/* 129 */       paramGraphics.setColor(this.color);
/* 130 */       paramGraphics.fillRect(0, 0, paramInt3 - insets.right, insets.top);
/* 131 */       paramGraphics.fillRect(0, insets.top, insets.left, paramInt4 - insets.top);
/* 132 */       paramGraphics.fillRect(insets.left, paramInt4 - insets.bottom, paramInt3 - insets.left, insets.bottom);
/* 133 */       paramGraphics.fillRect(paramInt3 - insets.right, 0, insets.right, paramInt4 - insets.bottom);
/*     */     }
/* 135 */     else if (this.tileIcon != null) {
/* 136 */       int i = this.tileIcon.getIconWidth();
/* 137 */       int j = this.tileIcon.getIconHeight();
/* 138 */       paintEdge(paramComponent, paramGraphics, 0, 0, paramInt3 - insets.right, insets.top, i, j);
/* 139 */       paintEdge(paramComponent, paramGraphics, 0, insets.top, insets.left, paramInt4 - insets.top, i, j);
/* 140 */       paintEdge(paramComponent, paramGraphics, insets.left, paramInt4 - insets.bottom, paramInt3 - insets.left, insets.bottom, i, j);
/* 141 */       paintEdge(paramComponent, paramGraphics, paramInt3 - insets.right, 0, insets.right, paramInt4 - insets.bottom, i, j);
/*     */     } 
/* 143 */     paramGraphics.translate(-paramInt1, -paramInt2);
/* 144 */     paramGraphics.setColor(color);
/*     */   }
/*     */ 
/*     */   
/*     */   private void paintEdge(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 149 */     paramGraphics = paramGraphics.create(paramInt1, paramInt2, paramInt3, paramInt4);
/* 150 */     int i = -(paramInt2 % paramInt6);
/* 151 */     for (paramInt1 = -(paramInt1 % paramInt5); paramInt1 < paramInt3; paramInt1 += paramInt5) {
/* 152 */       for (paramInt2 = i; paramInt2 < paramInt4; paramInt2 += paramInt6) {
/* 153 */         this.tileIcon.paintIcon(paramComponent, paramGraphics, paramInt1, paramInt2);
/*     */       }
/*     */     } 
/* 156 */     paramGraphics.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/* 166 */     return computeInsets(paramInsets);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets() {
/* 174 */     return computeInsets(new Insets(0, 0, 0, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   private Insets computeInsets(Insets paramInsets) {
/* 179 */     if (this.tileIcon != null && this.top == -1 && this.bottom == -1 && this.left == -1 && this.right == -1) {
/*     */       
/* 181 */       int i = this.tileIcon.getIconWidth();
/* 182 */       int j = this.tileIcon.getIconHeight();
/* 183 */       paramInsets.top = j;
/* 184 */       paramInsets.right = i;
/* 185 */       paramInsets.bottom = j;
/* 186 */       paramInsets.left = i;
/*     */     } else {
/* 188 */       paramInsets.left = this.left;
/* 189 */       paramInsets.top = this.top;
/* 190 */       paramInsets.right = this.right;
/* 191 */       paramInsets.bottom = this.bottom;
/*     */     } 
/* 193 */     return paramInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getMatteColor() {
/* 202 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getTileIcon() {
/* 211 */     return this.tileIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 219 */     return (this.color != null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/border/MatteBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */