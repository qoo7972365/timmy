/*     */ package sun.java2d.pisces;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import sun.awt.geom.PathConsumer2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TransformingPathConsumer2D
/*     */ {
/*     */   public static PathConsumer2D transformConsumer(PathConsumer2D paramPathConsumer2D, AffineTransform paramAffineTransform) {
/*  36 */     if (paramAffineTransform == null) {
/*  37 */       return paramPathConsumer2D;
/*     */     }
/*  39 */     float f1 = (float)paramAffineTransform.getScaleX();
/*  40 */     float f2 = (float)paramAffineTransform.getShearX();
/*  41 */     float f3 = (float)paramAffineTransform.getTranslateX();
/*  42 */     float f4 = (float)paramAffineTransform.getShearY();
/*  43 */     float f5 = (float)paramAffineTransform.getScaleY();
/*  44 */     float f6 = (float)paramAffineTransform.getTranslateY();
/*  45 */     if (f2 == 0.0F && f4 == 0.0F) {
/*  46 */       if (f1 == 1.0F && f5 == 1.0F) {
/*  47 */         if (f3 == 0.0F && f6 == 0.0F) {
/*  48 */           return paramPathConsumer2D;
/*     */         }
/*  50 */         return new TranslateFilter(paramPathConsumer2D, f3, f6);
/*     */       } 
/*     */       
/*  53 */       if (f3 == 0.0F && f6 == 0.0F) {
/*  54 */         return new DeltaScaleFilter(paramPathConsumer2D, f1, f5);
/*     */       }
/*  56 */       return new ScaleFilter(paramPathConsumer2D, f1, f5, f3, f6);
/*     */     } 
/*     */     
/*  59 */     if (f3 == 0.0F && f6 == 0.0F) {
/*  60 */       return new DeltaTransformFilter(paramPathConsumer2D, f1, f2, f4, f5);
/*     */     }
/*  62 */     return new TransformFilter(paramPathConsumer2D, f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PathConsumer2D deltaTransformConsumer(PathConsumer2D paramPathConsumer2D, AffineTransform paramAffineTransform) {
/*  70 */     if (paramAffineTransform == null) {
/*  71 */       return paramPathConsumer2D;
/*     */     }
/*  73 */     float f1 = (float)paramAffineTransform.getScaleX();
/*  74 */     float f2 = (float)paramAffineTransform.getShearX();
/*  75 */     float f3 = (float)paramAffineTransform.getShearY();
/*  76 */     float f4 = (float)paramAffineTransform.getScaleY();
/*  77 */     if (f2 == 0.0F && f3 == 0.0F) {
/*  78 */       if (f1 == 1.0F && f4 == 1.0F) {
/*  79 */         return paramPathConsumer2D;
/*     */       }
/*  81 */       return new DeltaScaleFilter(paramPathConsumer2D, f1, f4);
/*     */     } 
/*     */     
/*  84 */     return new DeltaTransformFilter(paramPathConsumer2D, f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PathConsumer2D inverseDeltaTransformConsumer(PathConsumer2D paramPathConsumer2D, AffineTransform paramAffineTransform) {
/*  92 */     if (paramAffineTransform == null) {
/*  93 */       return paramPathConsumer2D;
/*     */     }
/*  95 */     float f1 = (float)paramAffineTransform.getScaleX();
/*  96 */     float f2 = (float)paramAffineTransform.getShearX();
/*  97 */     float f3 = (float)paramAffineTransform.getShearY();
/*  98 */     float f4 = (float)paramAffineTransform.getScaleY();
/*  99 */     if (f2 == 0.0F && f3 == 0.0F) {
/* 100 */       if (f1 == 1.0F && f4 == 1.0F) {
/* 101 */         return paramPathConsumer2D;
/*     */       }
/* 103 */       return new DeltaScaleFilter(paramPathConsumer2D, 1.0F / f1, 1.0F / f4);
/*     */     } 
/*     */     
/* 106 */     float f5 = f1 * f4 - f2 * f3;
/* 107 */     return new DeltaTransformFilter(paramPathConsumer2D, f4 / f5, -f2 / f5, -f3 / f5, f1 / f5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class TranslateFilter
/*     */     implements PathConsumer2D
/*     */   {
/*     */     private final PathConsumer2D out;
/*     */     
/*     */     private final float tx;
/*     */     
/*     */     private final float ty;
/*     */ 
/*     */     
/*     */     TranslateFilter(PathConsumer2D param1PathConsumer2D, float param1Float1, float param1Float2) {
/* 123 */       this.out = param1PathConsumer2D;
/* 124 */       this.tx = param1Float1;
/* 125 */       this.ty = param1Float2;
/*     */     }
/*     */     
/*     */     public void moveTo(float param1Float1, float param1Float2) {
/* 129 */       this.out.moveTo(param1Float1 + this.tx, param1Float2 + this.ty);
/*     */     }
/*     */     
/*     */     public void lineTo(float param1Float1, float param1Float2) {
/* 133 */       this.out.lineTo(param1Float1 + this.tx, param1Float2 + this.ty);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 139 */       this.out.quadTo(param1Float1 + this.tx, param1Float2 + this.ty, param1Float3 + this.tx, param1Float4 + this.ty);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 147 */       this.out.curveTo(param1Float1 + this.tx, param1Float2 + this.ty, param1Float3 + this.tx, param1Float4 + this.ty, param1Float5 + this.tx, param1Float6 + this.ty);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void closePath() {
/* 153 */       this.out.closePath();
/*     */     }
/*     */     
/*     */     public void pathDone() {
/* 157 */       this.out.pathDone();
/*     */     }
/*     */     
/*     */     public long getNativeConsumer() {
/* 161 */       return 0L;
/*     */     }
/*     */   }
/*     */   
/*     */   static final class ScaleFilter
/*     */     implements PathConsumer2D
/*     */   {
/*     */     private final PathConsumer2D out;
/*     */     private final float sx;
/*     */     private final float sy;
/*     */     private final float tx;
/*     */     private final float ty;
/*     */     
/*     */     ScaleFilter(PathConsumer2D param1PathConsumer2D, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 175 */       this.out = param1PathConsumer2D;
/* 176 */       this.sx = param1Float1;
/* 177 */       this.sy = param1Float2;
/* 178 */       this.tx = param1Float3;
/* 179 */       this.ty = param1Float4;
/*     */     }
/*     */     
/*     */     public void moveTo(float param1Float1, float param1Float2) {
/* 183 */       this.out.moveTo(param1Float1 * this.sx + this.tx, param1Float2 * this.sy + this.ty);
/*     */     }
/*     */     
/*     */     public void lineTo(float param1Float1, float param1Float2) {
/* 187 */       this.out.lineTo(param1Float1 * this.sx + this.tx, param1Float2 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 193 */       this.out.quadTo(param1Float1 * this.sx + this.tx, param1Float2 * this.sy + this.ty, param1Float3 * this.sx + this.tx, param1Float4 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 201 */       this.out.curveTo(param1Float1 * this.sx + this.tx, param1Float2 * this.sy + this.ty, param1Float3 * this.sx + this.tx, param1Float4 * this.sy + this.ty, param1Float5 * this.sx + this.tx, param1Float6 * this.sy + this.ty);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void closePath() {
/* 207 */       this.out.closePath();
/*     */     }
/*     */     
/*     */     public void pathDone() {
/* 211 */       this.out.pathDone();
/*     */     }
/*     */     
/*     */     public long getNativeConsumer() {
/* 215 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class TransformFilter
/*     */     implements PathConsumer2D
/*     */   {
/*     */     private final PathConsumer2D out;
/*     */     private final float Mxx;
/*     */     private final float Mxy;
/*     */     private final float Mxt;
/*     */     private final float Myx;
/*     */     private final float Myy;
/*     */     private final float Myt;
/*     */     
/*     */     TransformFilter(PathConsumer2D param1PathConsumer2D, float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 232 */       this.out = param1PathConsumer2D;
/* 233 */       this.Mxx = param1Float1;
/* 234 */       this.Mxy = param1Float2;
/* 235 */       this.Mxt = param1Float3;
/* 236 */       this.Myx = param1Float4;
/* 237 */       this.Myy = param1Float5;
/* 238 */       this.Myt = param1Float6;
/*     */     }
/*     */     
/*     */     public void moveTo(float param1Float1, float param1Float2) {
/* 242 */       this.out.moveTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy + this.Mxt, param1Float1 * this.Myx + param1Float2 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */     
/*     */     public void lineTo(float param1Float1, float param1Float2) {
/* 247 */       this.out.lineTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy + this.Mxt, param1Float1 * this.Myx + param1Float2 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 254 */       this.out.quadTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy + this.Mxt, param1Float1 * this.Myx + param1Float2 * this.Myy + this.Myt, param1Float3 * this.Mxx + param1Float4 * this.Mxy + this.Mxt, param1Float3 * this.Myx + param1Float4 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 264 */       this.out.curveTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy + this.Mxt, param1Float1 * this.Myx + param1Float2 * this.Myy + this.Myt, param1Float3 * this.Mxx + param1Float4 * this.Mxy + this.Mxt, param1Float3 * this.Myx + param1Float4 * this.Myy + this.Myt, param1Float5 * this.Mxx + param1Float6 * this.Mxy + this.Mxt, param1Float5 * this.Myx + param1Float6 * this.Myy + this.Myt);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void closePath() {
/* 273 */       this.out.closePath();
/*     */     }
/*     */     
/*     */     public void pathDone() {
/* 277 */       this.out.pathDone();
/*     */     }
/*     */     
/*     */     public long getNativeConsumer() {
/* 281 */       return 0L;
/*     */     } }
/*     */   
/*     */   static final class DeltaScaleFilter implements PathConsumer2D {
/*     */     private final float sx;
/*     */     private final float sy;
/*     */     private final PathConsumer2D out;
/*     */     
/*     */     public DeltaScaleFilter(PathConsumer2D param1PathConsumer2D, float param1Float1, float param1Float2) {
/* 290 */       this.sx = param1Float1;
/* 291 */       this.sy = param1Float2;
/* 292 */       this.out = param1PathConsumer2D;
/*     */     }
/*     */     
/*     */     public void moveTo(float param1Float1, float param1Float2) {
/* 296 */       this.out.moveTo(param1Float1 * this.sx, param1Float2 * this.sy);
/*     */     }
/*     */     
/*     */     public void lineTo(float param1Float1, float param1Float2) {
/* 300 */       this.out.lineTo(param1Float1 * this.sx, param1Float2 * this.sy);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 306 */       this.out.quadTo(param1Float1 * this.sx, param1Float2 * this.sy, param1Float3 * this.sx, param1Float4 * this.sy);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 314 */       this.out.curveTo(param1Float1 * this.sx, param1Float2 * this.sy, param1Float3 * this.sx, param1Float4 * this.sy, param1Float5 * this.sx, param1Float6 * this.sy);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void closePath() {
/* 320 */       this.out.closePath();
/*     */     }
/*     */     
/*     */     public void pathDone() {
/* 324 */       this.out.pathDone();
/*     */     }
/*     */     
/*     */     public long getNativeConsumer() {
/* 328 */       return 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class DeltaTransformFilter
/*     */     implements PathConsumer2D
/*     */   {
/*     */     private PathConsumer2D out;
/*     */     private final float Mxx;
/*     */     private final float Mxy;
/*     */     private final float Myx;
/*     */     private final float Myy;
/*     */     
/*     */     DeltaTransformFilter(PathConsumer2D param1PathConsumer2D, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 343 */       this.out = param1PathConsumer2D;
/* 344 */       this.Mxx = param1Float1;
/* 345 */       this.Mxy = param1Float2;
/* 346 */       this.Myx = param1Float3;
/* 347 */       this.Myy = param1Float4;
/*     */     }
/*     */     
/*     */     public void moveTo(float param1Float1, float param1Float2) {
/* 351 */       this.out.moveTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy, param1Float1 * this.Myx + param1Float2 * this.Myy);
/*     */     }
/*     */ 
/*     */     
/*     */     public void lineTo(float param1Float1, float param1Float2) {
/* 356 */       this.out.lineTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy, param1Float1 * this.Myx + param1Float2 * this.Myy);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 363 */       this.out.quadTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy, param1Float1 * this.Myx + param1Float2 * this.Myy, param1Float3 * this.Mxx + param1Float4 * this.Mxy, param1Float3 * this.Myx + param1Float4 * this.Myy);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 373 */       this.out.curveTo(param1Float1 * this.Mxx + param1Float2 * this.Mxy, param1Float1 * this.Myx + param1Float2 * this.Myy, param1Float3 * this.Mxx + param1Float4 * this.Mxy, param1Float3 * this.Myx + param1Float4 * this.Myy, param1Float5 * this.Mxx + param1Float6 * this.Mxy, param1Float5 * this.Myx + param1Float6 * this.Myy);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void closePath() {
/* 382 */       this.out.closePath();
/*     */     }
/*     */     
/*     */     public void pathDone() {
/* 386 */       this.out.pathDone();
/*     */     }
/*     */     
/*     */     public long getNativeConsumer() {
/* 390 */       return 0L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/TransformingPathConsumer2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */