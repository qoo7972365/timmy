/*     */ package javax.imageio;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageReadParam
/*     */   extends IIOParam
/*     */ {
/*     */   protected boolean canSetSourceRenderSize = false;
/* 152 */   protected Dimension sourceRenderSize = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   protected BufferedImage destination = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   protected int[] destinationBands = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   protected int minProgressivePass = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   protected int numProgressivePasses = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDestinationType(ImageTypeSpecifier paramImageTypeSpecifier) {
/* 200 */     super.setDestinationType(paramImageTypeSpecifier);
/* 201 */     setDestination(null);
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
/*     */   public void setDestination(BufferedImage paramBufferedImage) {
/* 232 */     this.destination = paramBufferedImage;
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
/*     */   public BufferedImage getDestination() {
/* 245 */     return this.destination;
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
/*     */   
/*     */   public void setDestinationBands(int[] paramArrayOfint) {
/* 282 */     if (paramArrayOfint == null) {
/* 283 */       this.destinationBands = null;
/*     */     } else {
/* 285 */       int i = paramArrayOfint.length;
/* 286 */       for (byte b = 0; b < i; b++) {
/* 287 */         int j = paramArrayOfint[b];
/* 288 */         if (j < 0) {
/* 289 */           throw new IllegalArgumentException("Band value < 0!");
/*     */         }
/* 291 */         for (int k = b + 1; k < i; k++) {
/* 292 */           if (j == paramArrayOfint[k]) {
/* 293 */             throw new IllegalArgumentException("Duplicate band value!");
/*     */           }
/*     */         } 
/*     */       } 
/* 297 */       this.destinationBands = (int[])paramArrayOfint.clone();
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
/*     */   public int[] getDestinationBands() {
/* 312 */     if (this.destinationBands == null) {
/* 313 */       return null;
/*     */     }
/* 315 */     return (int[])this.destinationBands.clone();
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
/*     */   public boolean canSetSourceRenderSize() {
/* 334 */     return this.canSetSourceRenderSize;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceRenderSize(Dimension paramDimension) throws UnsupportedOperationException {
/* 374 */     if (!canSetSourceRenderSize()) {
/* 375 */       throw new UnsupportedOperationException("Can't set source render size!");
/*     */     }
/*     */ 
/*     */     
/* 379 */     if (paramDimension == null) {
/* 380 */       this.sourceRenderSize = null;
/*     */     } else {
/* 382 */       if (paramDimension.width <= 0 || paramDimension.height <= 0) {
/* 383 */         throw new IllegalArgumentException("width or height <= 0!");
/*     */       }
/* 385 */       this.sourceRenderSize = (Dimension)paramDimension.clone();
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
/*     */   public Dimension getSourceRenderSize() {
/* 401 */     return (this.sourceRenderSize == null) ? null : (Dimension)this.sourceRenderSize
/* 402 */       .clone();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceProgressivePasses(int paramInt1, int paramInt2) {
/* 446 */     if (paramInt1 < 0) {
/* 447 */       throw new IllegalArgumentException("minPass < 0!");
/*     */     }
/* 449 */     if (paramInt2 <= 0) {
/* 450 */       throw new IllegalArgumentException("numPasses <= 0!");
/*     */     }
/* 452 */     if (paramInt2 != Integer.MAX_VALUE && (paramInt1 + paramInt2 - 1 & Integer.MIN_VALUE) != 0)
/*     */     {
/* 454 */       throw new IllegalArgumentException("minPass + numPasses - 1 > INTEGER.MAX_VALUE!");
/*     */     }
/*     */ 
/*     */     
/* 458 */     this.minProgressivePass = paramInt1;
/* 459 */     this.numProgressivePasses = paramInt2;
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
/*     */   public int getSourceMinProgressivePass() {
/* 473 */     return this.minProgressivePass;
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
/*     */   public int getSourceMaxProgressivePass() {
/* 487 */     if (this.numProgressivePasses == Integer.MAX_VALUE) {
/* 488 */       return Integer.MAX_VALUE;
/*     */     }
/* 490 */     return this.minProgressivePass + this.numProgressivePasses - 1;
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
/*     */   public int getSourceNumProgressivePasses() {
/* 506 */     return this.numProgressivePasses;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/ImageReadParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */