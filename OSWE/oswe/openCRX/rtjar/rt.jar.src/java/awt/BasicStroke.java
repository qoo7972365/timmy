/*     */ package java.awt;
/*     */ 
/*     */ import java.beans.ConstructorProperties;
/*     */ import java.util.Arrays;
/*     */ import sun.java2d.pipe.RenderingEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicStroke
/*     */   implements Stroke
/*     */ {
/*     */   public static final int JOIN_MITER = 0;
/*     */   public static final int JOIN_ROUND = 1;
/*     */   public static final int JOIN_BEVEL = 2;
/*     */   public static final int CAP_BUTT = 0;
/*     */   public static final int CAP_ROUND = 1;
/*     */   public static final int CAP_SQUARE = 2;
/*     */   float width;
/*     */   int join;
/*     */   int cap;
/*     */   float miterlimit;
/*     */   float[] dash;
/*     */   float dash_phase;
/*     */   
/*     */   @ConstructorProperties({"lineWidth", "endCap", "lineJoin", "miterLimit", "dashArray", "dashPhase"})
/*     */   public BasicStroke(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float[] paramArrayOffloat, float paramFloat3) {
/* 193 */     if (paramFloat1 < 0.0F) {
/* 194 */       throw new IllegalArgumentException("negative width");
/*     */     }
/* 196 */     if (paramInt1 != 0 && paramInt1 != 1 && paramInt1 != 2) {
/* 197 */       throw new IllegalArgumentException("illegal end cap value");
/*     */     }
/* 199 */     if (paramInt2 == 0) {
/* 200 */       if (paramFloat2 < 1.0F) {
/* 201 */         throw new IllegalArgumentException("miter limit < 1");
/*     */       }
/* 203 */     } else if (paramInt2 != 1 && paramInt2 != 2) {
/* 204 */       throw new IllegalArgumentException("illegal line join value");
/*     */     } 
/* 206 */     if (paramArrayOffloat != null) {
/* 207 */       if (paramFloat3 < 0.0F) {
/* 208 */         throw new IllegalArgumentException("negative dash phase");
/*     */       }
/* 210 */       boolean bool = true;
/* 211 */       for (byte b = 0; b < paramArrayOffloat.length; b++) {
/* 212 */         float f = paramArrayOffloat[b];
/* 213 */         if (f > 0.0D) {
/* 214 */           bool = false;
/* 215 */         } else if (f < 0.0D) {
/* 216 */           throw new IllegalArgumentException("negative dash length");
/*     */         } 
/*     */       } 
/* 219 */       if (bool) {
/* 220 */         throw new IllegalArgumentException("dash lengths all zero");
/*     */       }
/*     */     } 
/* 223 */     this.width = paramFloat1;
/* 224 */     this.cap = paramInt1;
/* 225 */     this.join = paramInt2;
/* 226 */     this.miterlimit = paramFloat2;
/* 227 */     if (paramArrayOffloat != null) {
/* 228 */       this.dash = (float[])paramArrayOffloat.clone();
/*     */     }
/* 230 */     this.dash_phase = paramFloat3;
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
/*     */   public BasicStroke(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2) {
/* 249 */     this(paramFloat1, paramInt1, paramInt2, paramFloat2, null, 0.0F);
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
/*     */   public BasicStroke(float paramFloat, int paramInt1, int paramInt2) {
/* 267 */     this(paramFloat, paramInt1, paramInt2, 10.0F, null, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicStroke(float paramFloat) {
/* 278 */     this(paramFloat, 2, 0, 10.0F, null, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BasicStroke() {
/* 288 */     this(1.0F, 2, 0, 10.0F, null, 0.0F);
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
/*     */   public Shape createStrokedShape(Shape paramShape) {
/* 300 */     RenderingEngine renderingEngine = RenderingEngine.getInstance();
/* 301 */     return renderingEngine.createStrokedShape(paramShape, this.width, this.cap, this.join, this.miterlimit, this.dash, this.dash_phase);
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
/*     */   public float getLineWidth() {
/* 314 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndCap() {
/* 324 */     return this.cap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineJoin() {
/* 334 */     return this.join;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMiterLimit() {
/* 342 */     return this.miterlimit;
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
/*     */   public float[] getDashArray() {
/* 358 */     if (this.dash == null) {
/* 359 */       return null;
/*     */     }
/*     */     
/* 362 */     return (float[])this.dash.clone();
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
/*     */   public float getDashPhase() {
/* 374 */     return this.dash_phase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 382 */     int i = Float.floatToIntBits(this.width);
/* 383 */     i = i * 31 + this.join;
/* 384 */     i = i * 31 + this.cap;
/* 385 */     i = i * 31 + Float.floatToIntBits(this.miterlimit);
/* 386 */     if (this.dash != null) {
/* 387 */       i = i * 31 + Float.floatToIntBits(this.dash_phase);
/* 388 */       for (byte b = 0; b < this.dash.length; b++) {
/* 389 */         i = i * 31 + Float.floatToIntBits(this.dash[b]);
/*     */       }
/*     */     } 
/* 392 */     return i;
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
/*     */   public boolean equals(Object paramObject) {
/* 411 */     if (!(paramObject instanceof BasicStroke)) {
/* 412 */       return false;
/*     */     }
/*     */     
/* 415 */     BasicStroke basicStroke = (BasicStroke)paramObject;
/* 416 */     if (this.width != basicStroke.width) {
/* 417 */       return false;
/*     */     }
/*     */     
/* 420 */     if (this.join != basicStroke.join) {
/* 421 */       return false;
/*     */     }
/*     */     
/* 424 */     if (this.cap != basicStroke.cap) {
/* 425 */       return false;
/*     */     }
/*     */     
/* 428 */     if (this.miterlimit != basicStroke.miterlimit) {
/* 429 */       return false;
/*     */     }
/*     */     
/* 432 */     if (this.dash != null) {
/* 433 */       if (this.dash_phase != basicStroke.dash_phase) {
/* 434 */         return false;
/*     */       }
/*     */       
/* 437 */       if (!Arrays.equals(this.dash, basicStroke.dash)) {
/* 438 */         return false;
/*     */       }
/*     */     }
/* 441 */     else if (basicStroke.dash != null) {
/* 442 */       return false;
/*     */     } 
/*     */     
/* 445 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/BasicStroke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */