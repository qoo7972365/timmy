/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PixelToParallelogramConverter
/*     */   extends PixelToShapeConverter
/*     */   implements ShapeDrawPipe
/*     */ {
/*     */   ParallelogramPipe outrenderer;
/*     */   double minPenSize;
/*     */   double normPosition;
/*     */   double normRoundingBias;
/*     */   boolean adjustfill;
/*     */   
/*     */   public PixelToParallelogramConverter(ShapeDrawPipe paramShapeDrawPipe, ParallelogramPipe paramParallelogramPipe, double paramDouble1, double paramDouble2, boolean paramBoolean) {
/*  69 */     super(paramShapeDrawPipe);
/*  70 */     this.outrenderer = paramParallelogramPipe;
/*  71 */     this.minPenSize = paramDouble1;
/*  72 */     this.normPosition = paramDouble2;
/*  73 */     this.normRoundingBias = 0.5D - paramDouble2;
/*  74 */     this.adjustfill = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  80 */     if (!drawGeneralLine(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4)) {
/*  81 */       super.drawLine(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  88 */     if (paramInt3 >= 0 && paramInt4 >= 0) {
/*  89 */       if (paramSunGraphics2D.strokeState < 3) {
/*  90 */         BasicStroke basicStroke = (BasicStroke)paramSunGraphics2D.stroke;
/*  91 */         if (paramInt3 > 0 && paramInt4 > 0) {
/*  92 */           if (basicStroke.getLineJoin() == 0 && basicStroke
/*  93 */             .getDashArray() == null) {
/*     */             
/*  95 */             double d = basicStroke.getLineWidth();
/*  96 */             drawRectangle(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4, d);
/*     */ 
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } else {
/* 103 */           drawLine(paramSunGraphics2D, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
/*     */           return;
/*     */         } 
/*     */       } 
/* 107 */       super.drawRect(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 114 */     if (paramInt3 > 0 && paramInt4 > 0) {
/* 115 */       fillRectangle(paramSunGraphics2D, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 120 */     if (paramSunGraphics2D.strokeState < 3) {
/* 121 */       BasicStroke basicStroke = (BasicStroke)paramSunGraphics2D.stroke;
/* 122 */       if (paramShape instanceof Rectangle2D) {
/* 123 */         if (basicStroke.getLineJoin() == 0 && basicStroke
/* 124 */           .getDashArray() == null) {
/*     */           
/* 126 */           Rectangle2D rectangle2D = (Rectangle2D)paramShape;
/* 127 */           double d1 = rectangle2D.getWidth();
/* 128 */           double d2 = rectangle2D.getHeight();
/* 129 */           double d3 = rectangle2D.getX();
/* 130 */           double d4 = rectangle2D.getY();
/* 131 */           if (d1 >= 0.0D && d2 >= 0.0D) {
/* 132 */             double d = basicStroke.getLineWidth();
/* 133 */             drawRectangle(paramSunGraphics2D, d3, d4, d1, d2, d);
/*     */           } 
/*     */           return;
/*     */         } 
/* 137 */       } else if (paramShape instanceof Line2D) {
/* 138 */         Line2D line2D = (Line2D)paramShape;
/* 139 */         if (drawGeneralLine(paramSunGraphics2D, line2D
/* 140 */             .getX1(), line2D.getY1(), line2D
/* 141 */             .getX2(), line2D.getY2())) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 148 */     this.outpipe.draw(paramSunGraphics2D, paramShape);
/*     */   }
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 152 */     if (paramShape instanceof Rectangle2D) {
/* 153 */       Rectangle2D rectangle2D = (Rectangle2D)paramShape;
/* 154 */       double d1 = rectangle2D.getWidth();
/* 155 */       double d2 = rectangle2D.getHeight();
/* 156 */       if (d1 > 0.0D && d2 > 0.0D) {
/* 157 */         double d3 = rectangle2D.getX();
/* 158 */         double d4 = rectangle2D.getY();
/* 159 */         fillRectangle(paramSunGraphics2D, d3, d4, d1, d2);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 164 */     this.outpipe.fill(paramSunGraphics2D, paramShape);
/*     */   }
/*     */   
/*     */   static double len(double paramDouble1, double paramDouble2) {
/* 168 */     return (paramDouble1 == 0.0D) ? Math.abs(paramDouble2) : ((paramDouble2 == 0.0D) ? 
/* 169 */       Math.abs(paramDouble1) : 
/* 170 */       Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2));
/*     */   }
/*     */   
/*     */   double normalize(double paramDouble) {
/* 174 */     return Math.floor(paramDouble + this.normRoundingBias) + this.normPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawGeneralLine(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*     */     double d4, d5, d6, d7, arrayOfDouble[], d9, d10;
/* 181 */     if (paramSunGraphics2D.strokeState == 3 || paramSunGraphics2D.strokeState == 1)
/*     */     {
/*     */       
/* 184 */       return false;
/*     */     }
/* 186 */     BasicStroke basicStroke = (BasicStroke)paramSunGraphics2D.stroke;
/* 187 */     int i = basicStroke.getEndCap();
/* 188 */     if (i == 1 || basicStroke.getDashArray() != null)
/*     */     {
/*     */ 
/*     */       
/* 192 */       return false;
/*     */     }
/* 194 */     double d1 = basicStroke.getLineWidth();
/*     */ 
/*     */     
/* 197 */     double d2 = paramDouble3 - paramDouble1;
/* 198 */     double d3 = paramDouble4 - paramDouble2;
/*     */     
/* 200 */     switch (paramSunGraphics2D.transformState) {
/*     */       
/*     */       case 3:
/*     */       case 4:
/* 204 */         arrayOfDouble = new double[] { paramDouble1, paramDouble2, paramDouble3, paramDouble4 };
/* 205 */         paramSunGraphics2D.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/* 206 */         d4 = arrayOfDouble[0];
/* 207 */         d5 = arrayOfDouble[1];
/* 208 */         d6 = arrayOfDouble[2];
/* 209 */         d7 = arrayOfDouble[3];
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/*     */       case 2:
/* 215 */         d8 = paramSunGraphics2D.transform.getTranslateX();
/* 216 */         d9 = paramSunGraphics2D.transform.getTranslateY();
/* 217 */         d4 = paramDouble1 + d8;
/* 218 */         d5 = paramDouble2 + d9;
/* 219 */         d6 = paramDouble3 + d8;
/* 220 */         d7 = paramDouble4 + d9;
/*     */         break;
/*     */       
/*     */       case 0:
/* 224 */         d4 = paramDouble1;
/* 225 */         d5 = paramDouble2;
/* 226 */         d6 = paramDouble3;
/* 227 */         d7 = paramDouble4;
/*     */         break;
/*     */       default:
/* 230 */         throw new InternalError("unknown TRANSFORM state...");
/*     */     } 
/* 232 */     if (paramSunGraphics2D.strokeHint != 2) {
/* 233 */       if (paramSunGraphics2D.strokeState == 0 && this.outrenderer instanceof PixelDrawPipe) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 238 */         int j = (int)Math.floor(d4 - paramSunGraphics2D.transX);
/* 239 */         int k = (int)Math.floor(d5 - paramSunGraphics2D.transY);
/* 240 */         int m = (int)Math.floor(d6 - paramSunGraphics2D.transX);
/* 241 */         int n = (int)Math.floor(d7 - paramSunGraphics2D.transY);
/* 242 */         ((PixelDrawPipe)this.outrenderer).drawLine(paramSunGraphics2D, j, k, m, n);
/* 243 */         return true;
/*     */       } 
/* 245 */       d4 = normalize(d4);
/* 246 */       d5 = normalize(d5);
/* 247 */       d6 = normalize(d6);
/* 248 */       d7 = normalize(d7);
/*     */     } 
/* 250 */     if (paramSunGraphics2D.transformState >= 3) {
/*     */ 
/*     */ 
/*     */       
/* 254 */       d8 = len(d2, d3);
/* 255 */       if (d8 == 0.0D) {
/* 256 */         d2 = d8 = 1.0D;
/*     */       }
/*     */ 
/*     */       
/* 260 */       double[] arrayOfDouble1 = { d3 / d8, -d2 / d8 };
/* 261 */       paramSunGraphics2D.transform.deltaTransform(arrayOfDouble1, 0, arrayOfDouble1, 0, 1);
/* 262 */       d1 *= len(arrayOfDouble1[0], arrayOfDouble1[1]);
/*     */     } 
/* 264 */     d1 = Math.max(d1, this.minPenSize);
/* 265 */     d2 = d6 - d4;
/* 266 */     d3 = d7 - d5;
/* 267 */     double d8 = len(d2, d3);
/*     */     
/* 269 */     if (d8 == 0.0D) {
/* 270 */       if (i == 0) {
/* 271 */         return true;
/*     */       }
/* 273 */       d9 = d1;
/* 274 */       d10 = 0.0D;
/*     */     } else {
/* 276 */       d9 = d1 * d2 / d8;
/* 277 */       d10 = d1 * d3 / d8;
/*     */     } 
/* 279 */     double d11 = d4 + d10 / 2.0D;
/* 280 */     double d12 = d5 - d9 / 2.0D;
/* 281 */     if (i == 2) {
/* 282 */       d11 -= d9 / 2.0D;
/* 283 */       d12 -= d10 / 2.0D;
/* 284 */       d2 += d9;
/* 285 */       d3 += d10;
/*     */     } 
/* 287 */     this.outrenderer.fillParallelogram(paramSunGraphics2D, paramDouble1, paramDouble2, paramDouble3, paramDouble4, d11, d12, -d10, d9, d2, d3);
/*     */     
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRectangle(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 298 */     AffineTransform affineTransform = paramSunGraphics2D.transform;
/* 299 */     double d3 = affineTransform.getScaleX();
/* 300 */     double d4 = affineTransform.getShearY();
/* 301 */     double d5 = affineTransform.getShearX();
/* 302 */     double d6 = affineTransform.getScaleY();
/* 303 */     double d1 = paramDouble1 * d3 + paramDouble2 * d5 + affineTransform.getTranslateX();
/* 304 */     double d2 = paramDouble1 * d4 + paramDouble2 * d6 + affineTransform.getTranslateY();
/* 305 */     d3 *= paramDouble3;
/* 306 */     d4 *= paramDouble3;
/* 307 */     d5 *= paramDouble4;
/* 308 */     d6 *= paramDouble4;
/* 309 */     if (this.adjustfill && paramSunGraphics2D.strokeState < 3 && paramSunGraphics2D.strokeHint != 2) {
/*     */ 
/*     */ 
/*     */       
/* 313 */       double d7 = normalize(d1);
/* 314 */       double d8 = normalize(d2);
/* 315 */       d3 = normalize(d1 + d3) - d7;
/* 316 */       d4 = normalize(d2 + d4) - d8;
/* 317 */       d5 = normalize(d1 + d5) - d7;
/* 318 */       d6 = normalize(d2 + d6) - d8;
/* 319 */       d1 = d7;
/* 320 */       d2 = d8;
/*     */     } 
/* 322 */     this.outrenderer.fillParallelogram(paramSunGraphics2D, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, d1, d2, d3, d4, d5, d6);
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
/*     */   public void drawRectangle(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
/* 334 */     AffineTransform affineTransform = paramSunGraphics2D.transform;
/* 335 */     double d3 = affineTransform.getScaleX();
/* 336 */     double d4 = affineTransform.getShearY();
/* 337 */     double d5 = affineTransform.getShearX();
/* 338 */     double d6 = affineTransform.getScaleY();
/* 339 */     double d1 = paramDouble1 * d3 + paramDouble2 * d5 + affineTransform.getTranslateX();
/* 340 */     double d2 = paramDouble1 * d4 + paramDouble2 * d6 + affineTransform.getTranslateY();
/*     */ 
/*     */     
/* 343 */     double d7 = len(d3, d4) * paramDouble5;
/* 344 */     double d8 = len(d5, d6) * paramDouble5;
/* 345 */     d3 *= paramDouble3;
/* 346 */     d4 *= paramDouble3;
/* 347 */     d5 *= paramDouble4;
/* 348 */     d6 *= paramDouble4;
/* 349 */     if (paramSunGraphics2D.strokeState < 3 && paramSunGraphics2D.strokeHint != 2) {
/*     */ 
/*     */       
/* 352 */       double d11 = normalize(d1);
/* 353 */       double d12 = normalize(d2);
/* 354 */       d3 = normalize(d1 + d3) - d11;
/* 355 */       d4 = normalize(d2 + d4) - d12;
/* 356 */       d5 = normalize(d1 + d5) - d11;
/* 357 */       d6 = normalize(d2 + d6) - d12;
/* 358 */       d1 = d11;
/* 359 */       d2 = d12;
/*     */     } 
/* 361 */     d7 = Math.max(d7, this.minPenSize);
/* 362 */     d8 = Math.max(d8, this.minPenSize);
/* 363 */     double d9 = len(d3, d4);
/* 364 */     double d10 = len(d5, d6);
/* 365 */     if (d7 >= d9 || d8 >= d10) {
/*     */ 
/*     */ 
/*     */       
/* 369 */       fillOuterParallelogram(paramSunGraphics2D, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, d1, d2, d3, d4, d5, d6, d9, d10, d7, d8);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 374 */       this.outrenderer.drawParallelogram(paramSunGraphics2D, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, d1, d2, d3, d4, d5, d6, d7 / d9, d8 / d10);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillOuterParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13, double paramDouble14) {
/* 397 */     double d1 = paramDouble7 / paramDouble11;
/* 398 */     double d2 = paramDouble8 / paramDouble11;
/* 399 */     double d3 = paramDouble9 / paramDouble12;
/* 400 */     double d4 = paramDouble10 / paramDouble12;
/* 401 */     if (paramDouble11 == 0.0D) {
/*     */       
/* 403 */       if (paramDouble12 == 0.0D) {
/*     */         
/* 405 */         d3 = 0.0D;
/* 406 */         d4 = 1.0D;
/*     */       } 
/* 408 */       d1 = d4;
/* 409 */       d2 = -d3;
/* 410 */     } else if (paramDouble12 == 0.0D) {
/*     */       
/* 412 */       d3 = d2;
/* 413 */       d4 = -d1;
/*     */     } 
/* 415 */     d1 *= paramDouble13;
/* 416 */     d2 *= paramDouble13;
/* 417 */     d3 *= paramDouble14;
/* 418 */     d4 *= paramDouble14;
/* 419 */     paramDouble5 -= (d1 + d3) / 2.0D;
/* 420 */     paramDouble6 -= (d2 + d4) / 2.0D;
/* 421 */     paramDouble7 += d1;
/* 422 */     paramDouble8 += d2;
/* 423 */     paramDouble9 += d3;
/* 424 */     paramDouble10 += d4;
/*     */     
/* 426 */     this.outrenderer.fillParallelogram(paramSunGraphics2D, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/PixelToParallelogramConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */