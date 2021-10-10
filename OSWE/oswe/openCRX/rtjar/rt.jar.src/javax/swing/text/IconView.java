/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
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
/*     */ public class IconView
/*     */   extends View
/*     */ {
/*     */   private Icon c;
/*     */   
/*     */   public IconView(Element paramElement) {
/*  50 */     super(paramElement);
/*  51 */     AttributeSet attributeSet = paramElement.getAttributes();
/*  52 */     this.c = StyleConstants.getIcon(attributeSet);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*  71 */     Rectangle rectangle = paramShape.getBounds();
/*  72 */     this.c.paintIcon(getContainer(), paramGraphics, rectangle.x, rectangle.y);
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
/*     */   public float getPreferredSpan(int paramInt) {
/*  87 */     switch (paramInt) {
/*     */       case 0:
/*  89 */         return this.c.getIconWidth();
/*     */       case 1:
/*  91 */         return this.c.getIconHeight();
/*     */     } 
/*  93 */     throw new IllegalArgumentException("Invalid axis: " + paramInt);
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
/*     */   public float getAlignment(int paramInt) {
/* 111 */     switch (paramInt) {
/*     */       case 1:
/* 113 */         return 1.0F;
/*     */     } 
/* 115 */     return super.getAlignment(paramInt);
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
/*     */   public Shape modelToView(int paramInt, Shape paramShape, Position.Bias paramBias) throws BadLocationException {
/* 131 */     int i = getStartOffset();
/* 132 */     int j = getEndOffset();
/* 133 */     if (paramInt >= i && paramInt <= j) {
/* 134 */       Rectangle rectangle = paramShape.getBounds();
/* 135 */       if (paramInt == j) {
/* 136 */         rectangle.x += rectangle.width;
/*     */       }
/* 138 */       rectangle.width = 0;
/* 139 */       return rectangle;
/*     */     } 
/* 141 */     throw new BadLocationException(paramInt + " not in range " + i + "," + j, paramInt);
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
/*     */   public int viewToModel(float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 156 */     Rectangle rectangle = (Rectangle)paramShape;
/* 157 */     if (paramFloat1 < (rectangle.x + rectangle.width / 2)) {
/* 158 */       paramArrayOfBias[0] = Position.Bias.Forward;
/* 159 */       return getStartOffset();
/*     */     } 
/* 161 */     paramArrayOfBias[0] = Position.Bias.Backward;
/* 162 */     return getEndOffset();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/IconView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */