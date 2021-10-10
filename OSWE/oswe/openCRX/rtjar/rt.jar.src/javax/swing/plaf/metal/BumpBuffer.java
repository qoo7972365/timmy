/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.IndexColorModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BumpBuffer
/*     */ {
/*     */   static final int IMAGE_SIZE = 64;
/*     */   transient Image image;
/*     */   Color topColor;
/*     */   Color shadowColor;
/*     */   Color backColor;
/*     */   private GraphicsConfiguration gc;
/*     */   
/*     */   public BumpBuffer(GraphicsConfiguration paramGraphicsConfiguration, Color paramColor1, Color paramColor2, Color paramColor3) {
/* 156 */     this.gc = paramGraphicsConfiguration;
/* 157 */     this.topColor = paramColor1;
/* 158 */     this.shadowColor = paramColor2;
/* 159 */     this.backColor = paramColor3;
/* 160 */     createImage();
/* 161 */     fillBumpBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSameConfiguration(GraphicsConfiguration paramGraphicsConfiguration, Color paramColor1, Color paramColor2, Color paramColor3) {
/* 167 */     if (this.gc != null) {
/* 168 */       if (!this.gc.equals(paramGraphicsConfiguration)) {
/* 169 */         return false;
/*     */       }
/*     */     }
/* 172 */     else if (paramGraphicsConfiguration != null) {
/* 173 */       return false;
/*     */     } 
/* 175 */     return (this.topColor.equals(paramColor1) && this.shadowColor
/* 176 */       .equals(paramColor2) && this.backColor
/* 177 */       .equals(paramColor3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image getImage() {
/* 185 */     return this.image;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillBumpBuffer() {
/* 192 */     Graphics graphics = this.image.getGraphics();
/*     */     
/* 194 */     graphics.setColor(this.backColor);
/* 195 */     graphics.fillRect(0, 0, 64, 64);
/*     */     
/* 197 */     graphics.setColor(this.topColor); byte b;
/* 198 */     for (b = 0; b < 64; b += 4) {
/* 199 */       for (byte b1 = 0; b1 < 64; b1 += 4) {
/* 200 */         graphics.drawLine(b, b1, b, b1);
/* 201 */         graphics.drawLine(b + 2, b1 + 2, b + 2, b1 + 2);
/*     */       } 
/*     */     } 
/*     */     
/* 205 */     graphics.setColor(this.shadowColor);
/* 206 */     for (b = 0; b < 64; b += 4) {
/* 207 */       for (byte b1 = 0; b1 < 64; b1 += 4) {
/* 208 */         graphics.drawLine(b + 1, b1 + 1, b + 1, b1 + 1);
/* 209 */         graphics.drawLine(b + 3, b1 + 3, b + 3, b1 + 3);
/*     */       } 
/*     */     } 
/* 212 */     graphics.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createImage() {
/* 220 */     if (this.gc != null) {
/* 221 */       this.image = this.gc.createCompatibleImage(64, 64, (this.backColor != MetalBumps.ALPHA) ? 1 : 2);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 227 */       int[] arrayOfInt = { this.backColor.getRGB(), this.topColor.getRGB(), this.shadowColor.getRGB() };
/* 228 */       IndexColorModel indexColorModel = new IndexColorModel(8, 3, arrayOfInt, 0, false, (this.backColor == MetalBumps.ALPHA) ? 0 : -1, 0);
/*     */ 
/*     */       
/* 231 */       this.image = new BufferedImage(64, 64, 13, indexColorModel);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/metal/BumpBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */