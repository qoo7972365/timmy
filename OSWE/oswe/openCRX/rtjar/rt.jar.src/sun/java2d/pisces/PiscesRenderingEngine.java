/*     */ package sun.java2d.pisces;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.util.Arrays;
/*     */ import sun.awt.geom.PathConsumer2D;
/*     */ import sun.java2d.pipe.AATileGenerator;
/*     */ import sun.java2d.pipe.Region;
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
/*     */ public class PiscesRenderingEngine
/*     */   extends RenderingEngine
/*     */ {
/*     */   private enum NormMode
/*     */   {
/*  40 */     OFF, ON_NO_AA, ON_WITH_AA;
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
/*     */   public Shape createStrokedShape(Shape paramShape, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float[] paramArrayOffloat, float paramFloat3) {
/*  67 */     final Path2D.Float p2d = new Path2D.Float();
/*     */     
/*  69 */     strokeTo(paramShape, null, paramFloat1, NormMode.OFF, paramInt1, paramInt2, paramFloat2, paramArrayOffloat, paramFloat3, new PathConsumer2D()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void moveTo(float param1Float1, float param1Float2)
/*     */           {
/*  80 */             p2d.moveTo(param1Float1, param1Float2);
/*     */           }
/*     */           public void lineTo(float param1Float1, float param1Float2) {
/*  83 */             p2d.lineTo(param1Float1, param1Float2);
/*     */           }
/*     */           public void closePath() {
/*  86 */             p2d.closePath();
/*     */           }
/*     */           
/*     */           public void pathDone() {}
/*     */           
/*     */           public void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/*  92 */             p2d.curveTo(param1Float1, param1Float2, param1Float3, param1Float4, param1Float5, param1Float6);
/*     */           }
/*     */           public void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/*  95 */             p2d.quadTo(param1Float1, param1Float2, param1Float3, param1Float4);
/*     */           }
/*     */           public long getNativeConsumer() {
/*  98 */             throw new InternalError("Not using a native peer");
/*     */           }
/*     */         });
/* 101 */     return float_;
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
/*     */   public void strokeTo(Shape paramShape, AffineTransform paramAffineTransform, BasicStroke paramBasicStroke, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, PathConsumer2D paramPathConsumer2D) {
/* 139 */     NormMode normMode = paramBoolean2 ? (paramBoolean3 ? NormMode.ON_WITH_AA : NormMode.ON_NO_AA) : NormMode.OFF;
/*     */ 
/*     */     
/* 142 */     strokeTo(paramShape, paramAffineTransform, paramBasicStroke, paramBoolean1, normMode, paramBoolean3, paramPathConsumer2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void strokeTo(Shape paramShape, AffineTransform paramAffineTransform, BasicStroke paramBasicStroke, boolean paramBoolean1, NormMode paramNormMode, boolean paramBoolean2, PathConsumer2D paramPathConsumer2D) {
/*     */     float f;
/* 154 */     if (paramBoolean1) {
/* 155 */       if (paramBoolean2) {
/* 156 */         f = userSpaceLineWidth(paramAffineTransform, 0.5F);
/*     */       } else {
/* 158 */         f = userSpaceLineWidth(paramAffineTransform, 1.0F);
/*     */       } 
/*     */     } else {
/* 161 */       f = paramBasicStroke.getLineWidth();
/*     */     } 
/* 163 */     strokeTo(paramShape, paramAffineTransform, f, paramNormMode, paramBasicStroke
/*     */ 
/*     */ 
/*     */         
/* 167 */         .getEndCap(), paramBasicStroke
/* 168 */         .getLineJoin(), paramBasicStroke
/* 169 */         .getMiterLimit(), paramBasicStroke
/* 170 */         .getDashArray(), paramBasicStroke
/* 171 */         .getDashPhase(), paramPathConsumer2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float userSpaceLineWidth(AffineTransform paramAffineTransform, float paramFloat) {
/*     */     double d;
/* 179 */     if ((paramAffineTransform.getType() & 0x24) != 0) {
/*     */       
/* 181 */       d = Math.sqrt(paramAffineTransform.getDeterminant());
/*     */     } else {
/*     */       
/* 184 */       double d1 = paramAffineTransform.getScaleX();
/* 185 */       double d2 = paramAffineTransform.getShearX();
/* 186 */       double d3 = paramAffineTransform.getShearY();
/* 187 */       double d4 = paramAffineTransform.getScaleY();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 204 */       double d5 = d1 * d1 + d3 * d3;
/* 205 */       double d6 = 2.0D * (d1 * d2 + d3 * d4);
/* 206 */       double d7 = d2 * d2 + d4 * d4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 231 */       double d8 = Math.sqrt(d6 * d6 + (d5 - d7) * (d5 - d7));
/*     */       
/* 233 */       double d9 = (d5 + d7 + d8) / 2.0D;
/*     */       
/* 235 */       d = Math.sqrt(d9);
/*     */     } 
/*     */     
/* 238 */     return (float)(paramFloat / d);
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
/*     */   void strokeTo(Shape paramShape, AffineTransform paramAffineTransform, float paramFloat1, NormMode paramNormMode, int paramInt1, int paramInt2, float paramFloat2, float[] paramArrayOffloat, float paramFloat3, PathConsumer2D paramPathConsumer2D) {
/* 267 */     AffineTransform affineTransform1 = null;
/* 268 */     AffineTransform affineTransform2 = null;
/*     */     
/* 270 */     PathIterator pathIterator = null;
/*     */     
/* 272 */     if (paramAffineTransform != null && !paramAffineTransform.isIdentity()) {
/* 273 */       double d1 = paramAffineTransform.getScaleX();
/* 274 */       double d2 = paramAffineTransform.getShearX();
/* 275 */       double d3 = paramAffineTransform.getShearY();
/* 276 */       double d4 = paramAffineTransform.getScaleY();
/* 277 */       double d5 = d1 * d4 - d3 * d2;
/* 278 */       if (Math.abs(d5) <= 2.802596928649634E-45D) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 290 */         paramPathConsumer2D.moveTo(0.0F, 0.0F);
/* 291 */         paramPathConsumer2D.pathDone();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 301 */       if (nearZero(d1 * d2 + d3 * d4, 2) && nearZero(d1 * d1 + d3 * d3 - d2 * d2 + d4 * d4, 2)) {
/* 302 */         double d = Math.sqrt(d1 * d1 + d3 * d3);
/* 303 */         if (paramArrayOffloat != null) {
/* 304 */           paramArrayOffloat = Arrays.copyOf(paramArrayOffloat, paramArrayOffloat.length);
/* 305 */           for (byte b = 0; b < paramArrayOffloat.length; b++) {
/* 306 */             paramArrayOffloat[b] = (float)(d * paramArrayOffloat[b]);
/*     */           }
/* 308 */           paramFloat3 = (float)(d * paramFloat3);
/*     */         } 
/* 310 */         paramFloat1 = (float)(d * paramFloat1);
/* 311 */         pathIterator = paramShape.getPathIterator(paramAffineTransform);
/* 312 */         if (paramNormMode != NormMode.OFF) {
/* 313 */           pathIterator = new NormalizingPathIterator(pathIterator, paramNormMode);
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 319 */       else if (paramNormMode != NormMode.OFF) {
/* 320 */         affineTransform1 = paramAffineTransform;
/* 321 */         pathIterator = paramShape.getPathIterator(paramAffineTransform);
/* 322 */         pathIterator = new NormalizingPathIterator(pathIterator, paramNormMode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 336 */         affineTransform2 = paramAffineTransform;
/* 337 */         pathIterator = paramShape.getPathIterator(null);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 346 */       pathIterator = paramShape.getPathIterator(null);
/* 347 */       if (paramNormMode != NormMode.OFF) {
/* 348 */         pathIterator = new NormalizingPathIterator(pathIterator, paramNormMode);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 356 */     paramPathConsumer2D = TransformingPathConsumer2D.transformConsumer(paramPathConsumer2D, affineTransform2);
/* 357 */     paramPathConsumer2D = TransformingPathConsumer2D.deltaTransformConsumer(paramPathConsumer2D, affineTransform1);
/* 358 */     paramPathConsumer2D = new Stroker(paramPathConsumer2D, paramFloat1, paramInt1, paramInt2, paramFloat2);
/* 359 */     if (paramArrayOffloat != null) {
/* 360 */       paramPathConsumer2D = new Dasher(paramPathConsumer2D, paramArrayOffloat, paramFloat3);
/*     */     }
/* 362 */     paramPathConsumer2D = TransformingPathConsumer2D.inverseDeltaTransformConsumer(paramPathConsumer2D, affineTransform1);
/* 363 */     pathTo(pathIterator, paramPathConsumer2D);
/*     */   }
/*     */   
/*     */   private static boolean nearZero(double paramDouble, int paramInt) {
/* 367 */     return (Math.abs(paramDouble) < paramInt * Math.ulp(paramDouble));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NormalizingPathIterator
/*     */     implements PathIterator
/*     */   {
/*     */     private final PathIterator src;
/*     */     private float curx_adjust;
/*     */     private float cury_adjust;
/*     */     private float movx_adjust;
/*     */     private float movy_adjust;
/*     */     private final float lval;
/*     */     private final float rval;
/*     */     
/*     */     NormalizingPathIterator(PathIterator param1PathIterator, PiscesRenderingEngine.NormMode param1NormMode) {
/* 383 */       this.src = param1PathIterator;
/* 384 */       switch (param1NormMode) {
/*     */         
/*     */         case ON_NO_AA:
/* 387 */           this.lval = this.rval = 0.25F;
/*     */           return;
/*     */         
/*     */         case ON_WITH_AA:
/* 391 */           this.lval = 0.0F;
/* 392 */           this.rval = 0.5F;
/*     */           return;
/*     */         case OFF:
/* 395 */           throw new InternalError("A NormalizingPathIterator should not be created if no normalization is being done");
/*     */       } 
/*     */       
/* 398 */       throw new InternalError("Unrecognized normalization mode");
/*     */     }
/*     */     
/*     */     public int currentSegment(float[] param1ArrayOffloat) {
/*     */       byte b;
/* 403 */       int i = this.src.currentSegment(param1ArrayOffloat);
/*     */ 
/*     */       
/* 406 */       switch (i) {
/*     */         case 3:
/* 408 */           b = 4;
/*     */           break;
/*     */         case 2:
/* 411 */           b = 2;
/*     */           break;
/*     */         case 0:
/*     */         case 1:
/* 415 */           b = 0;
/*     */           break;
/*     */         
/*     */         case 4:
/* 419 */           this.curx_adjust = this.movx_adjust;
/* 420 */           this.cury_adjust = this.movy_adjust;
/* 421 */           return i;
/*     */         default:
/* 423 */           throw new InternalError("Unrecognized curve type");
/*     */       } 
/*     */ 
/*     */       
/* 427 */       float f1 = (float)Math.floor((param1ArrayOffloat[b] + this.lval)) + this.rval - param1ArrayOffloat[b];
/*     */       
/* 429 */       float f2 = (float)Math.floor((param1ArrayOffloat[b + 1] + this.lval)) + this.rval - param1ArrayOffloat[b + 1];
/*     */ 
/*     */       
/* 432 */       param1ArrayOffloat[b] = param1ArrayOffloat[b] + f1;
/* 433 */       param1ArrayOffloat[b + 1] = param1ArrayOffloat[b + 1] + f2;
/*     */ 
/*     */       
/* 436 */       switch (i) {
/*     */         case 3:
/* 438 */           param1ArrayOffloat[0] = param1ArrayOffloat[0] + this.curx_adjust;
/* 439 */           param1ArrayOffloat[1] = param1ArrayOffloat[1] + this.cury_adjust;
/* 440 */           param1ArrayOffloat[2] = param1ArrayOffloat[2] + f1;
/* 441 */           param1ArrayOffloat[3] = param1ArrayOffloat[3] + f2;
/*     */           break;
/*     */         case 2:
/* 444 */           param1ArrayOffloat[0] = param1ArrayOffloat[0] + (this.curx_adjust + f1) / 2.0F;
/* 445 */           param1ArrayOffloat[1] = param1ArrayOffloat[1] + (this.cury_adjust + f2) / 2.0F;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 0:
/* 450 */           this.movx_adjust = f1;
/* 451 */           this.movy_adjust = f2;
/*     */           break;
/*     */         case 4:
/* 454 */           throw new InternalError("This should be handled earlier.");
/*     */       } 
/* 456 */       this.curx_adjust = f1;
/* 457 */       this.cury_adjust = f2;
/* 458 */       return i;
/*     */     }
/*     */     
/*     */     public int currentSegment(double[] param1ArrayOfdouble) {
/* 462 */       float[] arrayOfFloat = new float[6];
/* 463 */       int i = currentSegment(arrayOfFloat);
/* 464 */       for (byte b = 0; b < 6; b++) {
/* 465 */         param1ArrayOfdouble[b] = arrayOfFloat[b];
/*     */       }
/* 467 */       return i;
/*     */     }
/*     */     
/*     */     public int getWindingRule() {
/* 471 */       return this.src.getWindingRule();
/*     */     }
/*     */     
/*     */     public boolean isDone() {
/* 475 */       return this.src.isDone();
/*     */     }
/*     */     
/*     */     public void next() {
/* 479 */       this.src.next();
/*     */     }
/*     */   }
/*     */   
/*     */   static void pathTo(PathIterator paramPathIterator, PathConsumer2D paramPathConsumer2D) {
/* 484 */     RenderingEngine.feedConsumer(paramPathIterator, paramPathConsumer2D);
/* 485 */     paramPathConsumer2D.pathDone();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AATileGenerator getAATileGenerator(Shape paramShape, AffineTransform paramAffineTransform, Region paramRegion, BasicStroke paramBasicStroke, boolean paramBoolean1, boolean paramBoolean2, int[] paramArrayOfint) {
/*     */     Renderer renderer;
/* 544 */     NormMode normMode = paramBoolean2 ? NormMode.ON_WITH_AA : NormMode.OFF;
/* 545 */     if (paramBasicStroke == null) {
/*     */       PathIterator pathIterator;
/* 547 */       if (paramBoolean2) {
/* 548 */         pathIterator = new NormalizingPathIterator(paramShape.getPathIterator(paramAffineTransform), normMode);
/*     */       } else {
/* 550 */         pathIterator = paramShape.getPathIterator(paramAffineTransform);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 555 */       renderer = new Renderer(3, 3, paramRegion.getLoX(), paramRegion.getLoY(), paramRegion.getWidth(), paramRegion.getHeight(), pathIterator.getWindingRule());
/* 556 */       pathTo(pathIterator, renderer);
/*     */     }
/*     */     else {
/*     */       
/* 560 */       renderer = new Renderer(3, 3, paramRegion.getLoX(), paramRegion.getLoY(), paramRegion.getWidth(), paramRegion.getHeight(), 1);
/*     */       
/* 562 */       strokeTo(paramShape, paramAffineTransform, paramBasicStroke, paramBoolean1, normMode, true, renderer);
/*     */     } 
/* 564 */     renderer.endRendering();
/* 565 */     PiscesTileGenerator piscesTileGenerator = new PiscesTileGenerator(renderer, renderer.MAX_AA_ALPHA);
/* 566 */     piscesTileGenerator.getBbox(paramArrayOfint);
/* 567 */     return piscesTileGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AATileGenerator getAATileGenerator(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, Region paramRegion, int[] paramArrayOfint) {
/*     */     double d1, d2, d3, d4;
/* 579 */     boolean bool = (paramDouble7 > 0.0D && paramDouble8 > 0.0D) ? true : false;
/*     */     
/* 581 */     if (bool) {
/* 582 */       d1 = paramDouble3 * paramDouble7;
/* 583 */       d2 = paramDouble4 * paramDouble7;
/* 584 */       d3 = paramDouble5 * paramDouble8;
/* 585 */       d4 = paramDouble6 * paramDouble8;
/* 586 */       paramDouble1 -= (d1 + d3) / 2.0D;
/* 587 */       paramDouble2 -= (d2 + d4) / 2.0D;
/* 588 */       paramDouble3 += d1;
/* 589 */       paramDouble4 += d2;
/* 590 */       paramDouble5 += d3;
/* 591 */       paramDouble6 += d4;
/* 592 */       if (paramDouble7 > 1.0D && paramDouble8 > 1.0D)
/*     */       {
/* 594 */         bool = false;
/*     */       }
/*     */     } else {
/* 597 */       d1 = d2 = d3 = d4 = 0.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 602 */     Renderer renderer = new Renderer(3, 3, paramRegion.getLoX(), paramRegion.getLoY(), paramRegion.getWidth(), paramRegion.getHeight(), 0);
/*     */ 
/*     */     
/* 605 */     renderer.moveTo((float)paramDouble1, (float)paramDouble2);
/* 606 */     renderer.lineTo((float)(paramDouble1 + paramDouble3), (float)(paramDouble2 + paramDouble4));
/* 607 */     renderer.lineTo((float)(paramDouble1 + paramDouble3 + paramDouble5), (float)(paramDouble2 + paramDouble4 + paramDouble6));
/* 608 */     renderer.lineTo((float)(paramDouble1 + paramDouble5), (float)(paramDouble2 + paramDouble6));
/* 609 */     renderer.closePath();
/*     */     
/* 611 */     if (bool) {
/* 612 */       paramDouble1 += d1 + d3;
/* 613 */       paramDouble2 += d2 + d4;
/* 614 */       paramDouble3 -= 2.0D * d1;
/* 615 */       paramDouble4 -= 2.0D * d2;
/* 616 */       paramDouble5 -= 2.0D * d3;
/* 617 */       paramDouble6 -= 2.0D * d4;
/* 618 */       renderer.moveTo((float)paramDouble1, (float)paramDouble2);
/* 619 */       renderer.lineTo((float)(paramDouble1 + paramDouble3), (float)(paramDouble2 + paramDouble4));
/* 620 */       renderer.lineTo((float)(paramDouble1 + paramDouble3 + paramDouble5), (float)(paramDouble2 + paramDouble4 + paramDouble6));
/* 621 */       renderer.lineTo((float)(paramDouble1 + paramDouble5), (float)(paramDouble2 + paramDouble6));
/* 622 */       renderer.closePath();
/*     */     } 
/*     */     
/* 625 */     renderer.pathDone();
/*     */     
/* 627 */     renderer.endRendering();
/* 628 */     PiscesTileGenerator piscesTileGenerator = new PiscesTileGenerator(renderer, renderer.MAX_AA_ALPHA);
/* 629 */     piscesTileGenerator.getBbox(paramArrayOfint);
/* 630 */     return piscesTileGenerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinimumAAPenSize() {
/* 639 */     return 0.5F;
/*     */   }
/*     */   
/*     */   static {
/*     */   
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/PiscesRenderingEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */