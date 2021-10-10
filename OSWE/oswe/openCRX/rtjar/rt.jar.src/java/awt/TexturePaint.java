/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TexturePaint
/*     */   implements Paint
/*     */ {
/*     */   BufferedImage bufImg;
/*     */   double tx;
/*     */   double ty;
/*     */   double sx;
/*     */   double sy;
/*     */   
/*     */   public TexturePaint(BufferedImage paramBufferedImage, Rectangle2D paramRectangle2D) {
/*  68 */     this.bufImg = paramBufferedImage;
/*  69 */     this.tx = paramRectangle2D.getX();
/*  70 */     this.ty = paramRectangle2D.getY();
/*  71 */     this.sx = paramRectangle2D.getWidth() / this.bufImg.getWidth();
/*  72 */     this.sy = paramRectangle2D.getHeight() / this.bufImg.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BufferedImage getImage() {
/*  81 */     return this.bufImg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getAnchorRect() {
/*  91 */     return new Rectangle2D.Double(this.tx, this.ty, this.sx * this.bufImg
/*  92 */         .getWidth(), this.sy * this.bufImg
/*  93 */         .getHeight());
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
/*     */   public PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints) {
/* 129 */     if (paramAffineTransform == null) {
/* 130 */       paramAffineTransform = new AffineTransform();
/*     */     } else {
/* 132 */       paramAffineTransform = (AffineTransform)paramAffineTransform.clone();
/*     */     } 
/* 134 */     paramAffineTransform.translate(this.tx, this.ty);
/* 135 */     paramAffineTransform.scale(this.sx, this.sy);
/*     */     
/* 137 */     return TexturePaintContext.getContext(this.bufImg, paramAffineTransform, paramRenderingHints, paramRectangle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransparency() {
/* 148 */     return this.bufImg.getColorModel().getTransparency();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/TexturePaint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */