/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import javax.swing.text.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListView
/*     */   extends BlockView
/*     */ {
/*     */   private StyleSheet.ListPainter listPainter;
/*     */   
/*     */   public ListView(Element paramElement) {
/*  44 */     super(paramElement, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAlignment(int paramInt) {
/*  54 */     switch (paramInt) {
/*     */       case 0:
/*  56 */         return 0.5F;
/*     */       case 1:
/*  58 */         return 0.5F;
/*     */     } 
/*  60 */     throw new IllegalArgumentException("Invalid axis: " + paramInt);
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
/*     */   public void paint(Graphics paramGraphics, Shape paramShape) {
/*  73 */     super.paint(paramGraphics, paramShape);
/*  74 */     Rectangle rectangle1 = paramShape.getBounds();
/*  75 */     Rectangle rectangle2 = paramGraphics.getClipBounds();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (rectangle2.x + rectangle2.width < rectangle1.x + getLeftInset()) {
/*  81 */       Rectangle rectangle = rectangle1;
/*  82 */       rectangle1 = getInsideAllocation(paramShape);
/*  83 */       int i = getViewCount();
/*  84 */       int j = rectangle2.y + rectangle2.height;
/*  85 */       for (byte b = 0; b < i; ) {
/*  86 */         rectangle.setBounds(rectangle1);
/*  87 */         childAllocation(b, rectangle);
/*  88 */         if (rectangle.y < j) {
/*  89 */           if (rectangle.y + rectangle.height >= rectangle2.y) {
/*  90 */             this.listPainter.paint(paramGraphics, rectangle.x, rectangle.y, rectangle.width, rectangle.height, this, b);
/*     */           }
/*     */           b++;
/*     */         } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintChild(Graphics paramGraphics, Rectangle paramRectangle, int paramInt) {
/* 112 */     this.listPainter.paint(paramGraphics, paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height, this, paramInt);
/* 113 */     super.paintChild(paramGraphics, paramRectangle, paramInt);
/*     */   }
/*     */   
/*     */   protected void setPropertiesFromAttributes() {
/* 117 */     super.setPropertiesFromAttributes();
/* 118 */     this.listPainter = getStyleSheet().getListPainter(getAttributes());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/ListView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */