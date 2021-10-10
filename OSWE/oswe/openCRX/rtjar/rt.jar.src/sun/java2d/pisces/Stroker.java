/*      */ package sun.java2d.pisces;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import sun.awt.geom.PathConsumer2D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class Stroker
/*      */   implements PathConsumer2D
/*      */ {
/*      */   private static final int MOVE_TO = 0;
/*      */   private static final int DRAWING_OP_TO = 1;
/*      */   private static final int CLOSE = 2;
/*      */   public static final int JOIN_MITER = 0;
/*      */   public static final int JOIN_ROUND = 1;
/*      */   public static final int JOIN_BEVEL = 2;
/*      */   public static final int CAP_BUTT = 0;
/*      */   public static final int CAP_ROUND = 1;
/*      */   public static final int CAP_SQUARE = 2;
/*      */   private final PathConsumer2D out;
/*      */   private final int capStyle;
/*      */   private final int joinStyle;
/*      */   private final float lineWidth2;
/*   81 */   private final float[][] offset = new float[3][2];
/*   82 */   private final float[] miter = new float[2];
/*      */   
/*      */   private final float miterLimitSq;
/*      */   
/*      */   private int prev;
/*      */   private float sx0;
/*      */   private float sy0;
/*      */   private float sdx;
/*      */   private float sdy;
/*      */   private float cx0;
/*      */   private float cy0;
/*      */   private float cdx;
/*      */   private float cdy;
/*      */   private float smx;
/*      */   private float smy;
/*      */   private float cmx;
/*      */   private float cmy;
/*   99 */   private final PolyStack reverse = new PolyStack();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float ROUND_JOIN_THRESHOLD = 0.015258789F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] middle;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] lp;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] rp;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MAX_N_CURVES = 11;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] subdivTs;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void computeOffset(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOffloat) {
/*  135 */     float f = (float)Math.sqrt((paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2));
/*  136 */     if (f == 0.0F) {
/*  137 */       paramArrayOffloat[1] = 0.0F; paramArrayOffloat[0] = 0.0F;
/*      */     } else {
/*  139 */       paramArrayOffloat[0] = paramFloat2 * paramFloat3 / f;
/*  140 */       paramArrayOffloat[1] = -(paramFloat1 * paramFloat3) / f;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isCW(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  155 */     return (paramFloat1 * paramFloat4 <= paramFloat2 * paramFloat3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawRoundJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean, float paramFloat7) {
/*  168 */     if ((paramFloat3 == 0.0F && paramFloat4 == 0.0F) || (paramFloat5 == 0.0F && paramFloat6 == 0.0F)) {
/*      */       return;
/*      */     }
/*      */     
/*  172 */     float f1 = paramFloat3 - paramFloat5;
/*  173 */     float f2 = paramFloat4 - paramFloat6;
/*  174 */     float f3 = f1 * f1 + f2 * f2;
/*  175 */     if (f3 < paramFloat7) {
/*      */       return;
/*      */     }
/*      */     
/*  179 */     if (paramBoolean) {
/*  180 */       paramFloat3 = -paramFloat3;
/*  181 */       paramFloat4 = -paramFloat4;
/*  182 */       paramFloat5 = -paramFloat5;
/*  183 */       paramFloat6 = -paramFloat6;
/*      */     } 
/*  185 */     drawRoundJoin(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawRoundJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean) {
/*      */     float f1, f2, f3, f4, f5, f6;
/*  196 */     double d = (paramFloat3 * paramFloat5 + paramFloat4 * paramFloat6);
/*      */ 
/*      */ 
/*      */     
/*  200 */     boolean bool = (d >= 0.0D) ? true : true;
/*      */     
/*  202 */     switch (bool) {
/*      */       case true:
/*  204 */         drawBezApproxForArc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramBoolean);
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case true:
/*  221 */         f1 = paramFloat6 - paramFloat4; f2 = paramFloat3 - paramFloat5;
/*  222 */         f3 = (float)Math.sqrt((f1 * f1 + f2 * f2));
/*  223 */         f4 = this.lineWidth2 / f3;
/*  224 */         f5 = f1 * f4; f6 = f2 * f4;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  229 */         if (paramBoolean) {
/*  230 */           f5 = -f5;
/*  231 */           f6 = -f6;
/*      */         } 
/*  233 */         drawBezApproxForArc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f5, f6, paramBoolean);
/*  234 */         drawBezApproxForArc(paramFloat1, paramFloat2, f5, f6, paramFloat5, paramFloat6, paramBoolean);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawBezApproxForArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean) {
/*  245 */     float f1 = (paramFloat3 * paramFloat5 + paramFloat4 * paramFloat6) / 2.0F * this.lineWidth2 * this.lineWidth2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  252 */     float f2 = (float)(1.3333333333333333D * Math.sqrt(0.5D - f1) / (1.0D + Math.sqrt(f1 + 0.5D)));
/*      */     
/*  254 */     if (paramBoolean) {
/*  255 */       f2 = -f2;
/*      */     }
/*  257 */     float f3 = paramFloat1 + paramFloat3;
/*  258 */     float f4 = paramFloat2 + paramFloat4;
/*  259 */     float f5 = f3 - f2 * paramFloat4;
/*  260 */     float f6 = f4 + f2 * paramFloat3;
/*      */     
/*  262 */     float f7 = paramFloat1 + paramFloat5;
/*  263 */     float f8 = paramFloat2 + paramFloat6;
/*  264 */     float f9 = f7 + f2 * paramFloat6;
/*  265 */     float f10 = f8 - f2 * paramFloat5;
/*      */     
/*  267 */     emitCurveTo(f3, f4, f5, f6, f9, f10, f7, f8, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawRoundCap(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  277 */     emitCurveTo(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, paramFloat1 + paramFloat3 - 0.5522848F * paramFloat4, paramFloat2 + paramFloat4 + 0.5522848F * paramFloat3, paramFloat1 - paramFloat4 + 0.5522848F * paramFloat3, paramFloat2 + paramFloat3 + 0.5522848F * paramFloat4, paramFloat1 - paramFloat4, paramFloat2 + paramFloat3, false);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  282 */     emitCurveTo(paramFloat1 - paramFloat4, paramFloat2 + paramFloat3, paramFloat1 - paramFloat4 - 0.5522848F * paramFloat3, paramFloat2 + paramFloat3 - 0.5522848F * paramFloat4, paramFloat1 - paramFloat3 - 0.5522848F * paramFloat4, paramFloat2 - paramFloat4 + 0.5522848F * paramFloat3, paramFloat1 - paramFloat3, paramFloat2 - paramFloat4, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void computeIntersection(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float[] paramArrayOffloat, int paramInt) {
/*  298 */     float f1 = paramFloat3 - paramFloat1;
/*  299 */     float f2 = paramFloat4 - paramFloat2;
/*  300 */     float f3 = paramFloat7 - paramFloat5;
/*  301 */     float f4 = paramFloat8 - paramFloat6;
/*      */     
/*  303 */     float f5 = f1 * f4 - f3 * f2;
/*  304 */     float f6 = f3 * (paramFloat2 - paramFloat6) - f4 * (paramFloat1 - paramFloat5);
/*  305 */     f6 /= f5;
/*  306 */     paramArrayOffloat[paramInt++] = paramFloat1 + f6 * f1;
/*  307 */     paramArrayOffloat[paramInt] = paramFloat2 + f6 * f2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawMiter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, boolean paramBoolean) {
/*  316 */     if ((paramFloat9 == paramFloat7 && paramFloat10 == paramFloat8) || (paramFloat1 == 0.0F && paramFloat2 == 0.0F) || (paramFloat5 == 0.0F && paramFloat6 == 0.0F)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  323 */     if (paramBoolean) {
/*  324 */       paramFloat7 = -paramFloat7;
/*  325 */       paramFloat8 = -paramFloat8;
/*  326 */       paramFloat9 = -paramFloat9;
/*  327 */       paramFloat10 = -paramFloat10;
/*      */     } 
/*      */     
/*  330 */     computeIntersection(paramFloat3 - paramFloat1 + paramFloat7, paramFloat4 - paramFloat2 + paramFloat8, paramFloat3 + paramFloat7, paramFloat4 + paramFloat8, paramFloat5 + paramFloat3 + paramFloat9, paramFloat6 + paramFloat4 + paramFloat10, paramFloat3 + paramFloat9, paramFloat4 + paramFloat10, this.miter, 0);
/*      */ 
/*      */ 
/*      */     
/*  334 */     float f = (this.miter[0] - paramFloat3) * (this.miter[0] - paramFloat3) + (this.miter[1] - paramFloat4) * (this.miter[1] - paramFloat4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  341 */     if (f < this.miterLimitSq) {
/*  342 */       emitLineTo(this.miter[0], this.miter[1], paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */   public void moveTo(float paramFloat1, float paramFloat2) {
/*  347 */     if (this.prev == 1) {
/*  348 */       finish();
/*      */     }
/*  350 */     this.sx0 = this.cx0 = paramFloat1;
/*  351 */     this.sy0 = this.cy0 = paramFloat2;
/*  352 */     this.cdx = this.sdx = 1.0F;
/*  353 */     this.cdy = this.sdy = 0.0F;
/*  354 */     this.prev = 0;
/*      */   }
/*      */   
/*      */   public void lineTo(float paramFloat1, float paramFloat2) {
/*  358 */     float f1 = paramFloat1 - this.cx0;
/*  359 */     float f2 = paramFloat2 - this.cy0;
/*  360 */     if (f1 == 0.0F && f2 == 0.0F) {
/*  361 */       f1 = 1.0F;
/*      */     }
/*  363 */     computeOffset(f1, f2, this.lineWidth2, this.offset[0]);
/*  364 */     float f3 = this.offset[0][0];
/*  365 */     float f4 = this.offset[0][1];
/*      */     
/*  367 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, f1, f2, this.cmx, this.cmy, f3, f4);
/*      */     
/*  369 */     emitLineTo(this.cx0 + f3, this.cy0 + f4);
/*  370 */     emitLineTo(paramFloat1 + f3, paramFloat2 + f4);
/*      */     
/*  372 */     emitLineTo(this.cx0 - f3, this.cy0 - f4, true);
/*  373 */     emitLineTo(paramFloat1 - f3, paramFloat2 - f4, true);
/*      */     
/*  375 */     this.cmx = f3;
/*  376 */     this.cmy = f4;
/*  377 */     this.cdx = f1;
/*  378 */     this.cdy = f2;
/*  379 */     this.cx0 = paramFloat1;
/*  380 */     this.cy0 = paramFloat2;
/*  381 */     this.prev = 1;
/*      */   }
/*      */   
/*      */   public void closePath() {
/*  385 */     if (this.prev != 1) {
/*  386 */       if (this.prev == 2) {
/*      */         return;
/*      */       }
/*  389 */       emitMoveTo(this.cx0, this.cy0 - this.lineWidth2);
/*  390 */       this.cmx = this.smx = 0.0F;
/*  391 */       this.cmy = this.smy = -this.lineWidth2;
/*  392 */       this.cdx = this.sdx = 1.0F;
/*  393 */       this.cdy = this.sdy = 0.0F;
/*  394 */       finish();
/*      */       
/*      */       return;
/*      */     } 
/*  398 */     if (this.cx0 != this.sx0 || this.cy0 != this.sy0) {
/*  399 */       lineTo(this.sx0, this.sy0);
/*      */     }
/*      */     
/*  402 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, this.sdx, this.sdy, this.cmx, this.cmy, this.smx, this.smy);
/*      */     
/*  404 */     emitLineTo(this.sx0 + this.smx, this.sy0 + this.smy);
/*      */     
/*  406 */     emitMoveTo(this.sx0 - this.smx, this.sy0 - this.smy);
/*  407 */     emitReverse();
/*      */     
/*  409 */     this.prev = 2;
/*  410 */     emitClose();
/*      */   }
/*      */   
/*      */   private void emitReverse() {
/*  414 */     while (!this.reverse.isEmpty()) {
/*  415 */       this.reverse.pop(this.out);
/*      */     }
/*      */   }
/*      */   
/*      */   public void pathDone() {
/*  420 */     if (this.prev == 1) {
/*  421 */       finish();
/*      */     }
/*      */     
/*  424 */     this.out.pathDone();
/*      */ 
/*      */     
/*  427 */     this.prev = 2;
/*      */   }
/*      */   
/*      */   private void finish() {
/*  431 */     if (this.capStyle == 1) {
/*  432 */       drawRoundCap(this.cx0, this.cy0, this.cmx, this.cmy);
/*  433 */     } else if (this.capStyle == 2) {
/*  434 */       emitLineTo(this.cx0 - this.cmy + this.cmx, this.cy0 + this.cmx + this.cmy);
/*  435 */       emitLineTo(this.cx0 - this.cmy - this.cmx, this.cy0 + this.cmx - this.cmy);
/*      */     } 
/*      */     
/*  438 */     emitReverse();
/*      */     
/*  440 */     if (this.capStyle == 1) {
/*  441 */       drawRoundCap(this.sx0, this.sy0, -this.smx, -this.smy);
/*  442 */     } else if (this.capStyle == 2) {
/*  443 */       emitLineTo(this.sx0 + this.smy - this.smx, this.sy0 - this.smx - this.smy);
/*  444 */       emitLineTo(this.sx0 + this.smy + this.smx, this.sy0 - this.smx + this.smy);
/*      */     } 
/*      */     
/*  447 */     emitClose();
/*      */   }
/*      */   
/*      */   private void emitMoveTo(float paramFloat1, float paramFloat2) {
/*  451 */     this.out.moveTo(paramFloat1, paramFloat2);
/*      */   }
/*      */   
/*      */   private void emitLineTo(float paramFloat1, float paramFloat2) {
/*  455 */     this.out.lineTo(paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitLineTo(float paramFloat1, float paramFloat2, boolean paramBoolean) {
/*  461 */     if (paramBoolean) {
/*  462 */       this.reverse.pushLine(paramFloat1, paramFloat2);
/*      */     } else {
/*  464 */       emitLineTo(paramFloat1, paramFloat2);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitQuadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean) {
/*  472 */     if (paramBoolean) {
/*  473 */       this.reverse.pushQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */     } else {
/*  475 */       this.out.quadTo(paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void emitCurveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, boolean paramBoolean) {
/*  484 */     if (paramBoolean) {
/*  485 */       this.reverse.pushCubic(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*      */     } else {
/*  487 */       this.out.curveTo(paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void emitClose() {
/*  492 */     this.out.closePath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10) {
/*  501 */     if (this.prev != 1) {
/*  502 */       emitMoveTo(paramFloat3 + paramFloat9, paramFloat4 + paramFloat10);
/*  503 */       this.sdx = paramFloat5;
/*  504 */       this.sdy = paramFloat6;
/*  505 */       this.smx = paramFloat9;
/*  506 */       this.smy = paramFloat10;
/*      */     } else {
/*  508 */       boolean bool = isCW(paramFloat1, paramFloat2, paramFloat5, paramFloat6);
/*  509 */       if (this.joinStyle == 0) {
/*  510 */         drawMiter(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, bool);
/*  511 */       } else if (this.joinStyle == 1) {
/*  512 */         drawRoundJoin(paramFloat3, paramFloat4, paramFloat7, paramFloat8, paramFloat9, paramFloat10, bool, 0.015258789F);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  517 */       emitLineTo(paramFloat3, paramFloat4, !bool);
/*      */     } 
/*  519 */     this.prev = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean within(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
/*  526 */     assert paramFloat5 > 0.0F : "";
/*      */ 
/*      */     
/*  529 */     return (Helpers.within(paramFloat1, paramFloat3, paramFloat5) && 
/*  530 */       Helpers.within(paramFloat2, paramFloat4, paramFloat5));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void getLineOffsets(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
/*  536 */     computeOffset(paramFloat3 - paramFloat1, paramFloat4 - paramFloat2, this.lineWidth2, this.offset[0]);
/*  537 */     paramArrayOffloat1[0] = paramFloat1 + this.offset[0][0];
/*  538 */     paramArrayOffloat1[1] = paramFloat2 + this.offset[0][1];
/*  539 */     paramArrayOffloat1[2] = paramFloat3 + this.offset[0][0];
/*  540 */     paramArrayOffloat1[3] = paramFloat4 + this.offset[0][1];
/*  541 */     paramArrayOffloat2[0] = paramFloat1 - this.offset[0][0];
/*  542 */     paramArrayOffloat2[1] = paramFloat2 - this.offset[0][1];
/*  543 */     paramArrayOffloat2[2] = paramFloat3 - this.offset[0][0];
/*  544 */     paramArrayOffloat2[3] = paramFloat4 - this.offset[0][1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int computeOffsetCubic(float[] paramArrayOffloat1, int paramInt, float[] paramArrayOffloat2, float[] paramArrayOffloat3) {
/*  557 */     float f1 = paramArrayOffloat1[paramInt + 0], f2 = paramArrayOffloat1[paramInt + 1];
/*  558 */     float f3 = paramArrayOffloat1[paramInt + 2], f4 = paramArrayOffloat1[paramInt + 3];
/*  559 */     float f5 = paramArrayOffloat1[paramInt + 4], f6 = paramArrayOffloat1[paramInt + 5];
/*  560 */     float f7 = paramArrayOffloat1[paramInt + 6], f8 = paramArrayOffloat1[paramInt + 7];
/*      */     
/*  562 */     float f9 = f7 - f5;
/*  563 */     float f10 = f8 - f6;
/*  564 */     float f11 = f3 - f1;
/*  565 */     float f12 = f4 - f2;
/*      */ 
/*      */ 
/*      */     
/*  569 */     boolean bool1 = within(f1, f2, f3, f4, 6.0F * Math.ulp(f4));
/*  570 */     boolean bool2 = within(f5, f6, f7, f8, 6.0F * Math.ulp(f8));
/*  571 */     if (bool1 && bool2) {
/*  572 */       getLineOffsets(f1, f2, f7, f8, paramArrayOffloat2, paramArrayOffloat3);
/*  573 */       return 4;
/*  574 */     }  if (bool1) {
/*  575 */       f11 = f5 - f1;
/*  576 */       f12 = f6 - f2;
/*  577 */     } else if (bool2) {
/*  578 */       f9 = f7 - f3;
/*  579 */       f10 = f8 - f4;
/*      */     } 
/*      */ 
/*      */     
/*  583 */     float f13 = f11 * f9 + f12 * f10;
/*  584 */     f13 *= f13;
/*  585 */     float f14 = f11 * f11 + f12 * f12, f15 = f9 * f9 + f10 * f10;
/*  586 */     if (Helpers.within(f13, f14 * f15, 4.0F * Math.ulp(f13))) {
/*  587 */       getLineOffsets(f1, f2, f7, f8, paramArrayOffloat2, paramArrayOffloat3);
/*  588 */       return 4;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  638 */     float f16 = 0.125F * (f1 + 3.0F * (f3 + f5) + f7);
/*  639 */     float f17 = 0.125F * (f2 + 3.0F * (f4 + f6) + f8);
/*      */ 
/*      */     
/*  642 */     float f18 = f5 + f7 - f1 - f3, f19 = f6 + f8 - f2 - f4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  647 */     computeOffset(f11, f12, this.lineWidth2, this.offset[0]);
/*  648 */     computeOffset(f18, f19, this.lineWidth2, this.offset[1]);
/*  649 */     computeOffset(f9, f10, this.lineWidth2, this.offset[2]);
/*  650 */     float f20 = f1 + this.offset[0][0];
/*  651 */     float f21 = f2 + this.offset[0][1];
/*  652 */     float f22 = f16 + this.offset[1][0];
/*  653 */     float f23 = f17 + this.offset[1][1];
/*  654 */     float f24 = f7 + this.offset[2][0];
/*  655 */     float f25 = f8 + this.offset[2][1];
/*      */     
/*  657 */     float f26 = 4.0F / 3.0F * (f11 * f10 - f12 * f9);
/*      */     
/*  659 */     float f27 = 2.0F * f22 - f20 - f24;
/*  660 */     float f28 = 2.0F * f23 - f21 - f25;
/*  661 */     float f29 = f26 * (f10 * f27 - f9 * f28);
/*  662 */     float f30 = f26 * (f11 * f28 - f12 * f27);
/*      */ 
/*      */     
/*  665 */     float f31 = f20 + f29 * f11;
/*  666 */     float f32 = f21 + f29 * f12;
/*  667 */     float f33 = f24 + f30 * f9;
/*  668 */     float f34 = f25 + f30 * f10;
/*      */     
/*  670 */     paramArrayOffloat2[0] = f20; paramArrayOffloat2[1] = f21;
/*  671 */     paramArrayOffloat2[2] = f31; paramArrayOffloat2[3] = f32;
/*  672 */     paramArrayOffloat2[4] = f33; paramArrayOffloat2[5] = f34;
/*  673 */     paramArrayOffloat2[6] = f24; paramArrayOffloat2[7] = f25;
/*      */     
/*  675 */     f20 = f1 - this.offset[0][0]; f21 = f2 - this.offset[0][1];
/*  676 */     f22 -= 2.0F * this.offset[1][0]; f23 -= 2.0F * this.offset[1][1];
/*  677 */     f24 = f7 - this.offset[2][0]; f25 = f8 - this.offset[2][1];
/*      */     
/*  679 */     f27 = 2.0F * f22 - f20 - f24;
/*  680 */     f28 = 2.0F * f23 - f21 - f25;
/*  681 */     f29 = f26 * (f10 * f27 - f9 * f28);
/*  682 */     f30 = f26 * (f11 * f28 - f12 * f27);
/*      */     
/*  684 */     f31 = f20 + f29 * f11;
/*  685 */     f32 = f21 + f29 * f12;
/*  686 */     f33 = f24 + f30 * f9;
/*  687 */     f34 = f25 + f30 * f10;
/*      */     
/*  689 */     paramArrayOffloat3[0] = f20; paramArrayOffloat3[1] = f21;
/*  690 */     paramArrayOffloat3[2] = f31; paramArrayOffloat3[3] = f32;
/*  691 */     paramArrayOffloat3[4] = f33; paramArrayOffloat3[5] = f34;
/*  692 */     paramArrayOffloat3[6] = f24; paramArrayOffloat3[7] = f25;
/*  693 */     return 8;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int computeOffsetQuad(float[] paramArrayOffloat1, int paramInt, float[] paramArrayOffloat2, float[] paramArrayOffloat3) {
/*  700 */     float f1 = paramArrayOffloat1[paramInt + 0], f2 = paramArrayOffloat1[paramInt + 1];
/*  701 */     float f3 = paramArrayOffloat1[paramInt + 2], f4 = paramArrayOffloat1[paramInt + 3];
/*  702 */     float f5 = paramArrayOffloat1[paramInt + 4], f6 = paramArrayOffloat1[paramInt + 5];
/*      */     
/*  704 */     float f7 = f5 - f3;
/*  705 */     float f8 = f6 - f4;
/*  706 */     float f9 = f3 - f1;
/*  707 */     float f10 = f4 - f2;
/*      */ 
/*      */     
/*  710 */     computeOffset(f9, f10, this.lineWidth2, this.offset[0]);
/*  711 */     computeOffset(f7, f8, this.lineWidth2, this.offset[1]);
/*      */     
/*  713 */     paramArrayOffloat2[0] = f1 + this.offset[0][0]; paramArrayOffloat2[1] = f2 + this.offset[0][1];
/*  714 */     paramArrayOffloat2[4] = f5 + this.offset[1][0]; paramArrayOffloat2[5] = f6 + this.offset[1][1];
/*  715 */     paramArrayOffloat3[0] = f1 - this.offset[0][0]; paramArrayOffloat3[1] = f2 - this.offset[0][1];
/*  716 */     paramArrayOffloat3[4] = f5 - this.offset[1][0]; paramArrayOffloat3[5] = f6 - this.offset[1][1];
/*      */     
/*  718 */     float f11 = paramArrayOffloat2[0];
/*  719 */     float f12 = paramArrayOffloat2[1];
/*  720 */     float f13 = paramArrayOffloat2[4];
/*  721 */     float f14 = paramArrayOffloat2[5];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     computeIntersection(f11, f12, f11 + f9, f12 + f10, f13, f14, f13 - f7, f14 - f8, paramArrayOffloat2, 2);
/*  733 */     float f15 = paramArrayOffloat2[2];
/*  734 */     float f16 = paramArrayOffloat2[3];
/*      */     
/*  736 */     if (!isFinite(f15) || !isFinite(f16)) {
/*      */       
/*  738 */       f11 = paramArrayOffloat3[0];
/*  739 */       f12 = paramArrayOffloat3[1];
/*  740 */       f13 = paramArrayOffloat3[4];
/*  741 */       f14 = paramArrayOffloat3[5];
/*  742 */       computeIntersection(f11, f12, f11 + f9, f12 + f10, f13, f14, f13 - f7, f14 - f8, paramArrayOffloat3, 2);
/*  743 */       f15 = paramArrayOffloat3[2];
/*  744 */       f16 = paramArrayOffloat3[3];
/*  745 */       if (!isFinite(f15) || !isFinite(f16)) {
/*      */         
/*  747 */         getLineOffsets(f1, f2, f5, f6, paramArrayOffloat2, paramArrayOffloat3);
/*  748 */         return 4;
/*      */       } 
/*      */       
/*  751 */       paramArrayOffloat2[2] = 2.0F * f3 - f15;
/*  752 */       paramArrayOffloat2[3] = 2.0F * f4 - f16;
/*  753 */       return 6;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  758 */     paramArrayOffloat3[2] = 2.0F * f3 - f15;
/*  759 */     paramArrayOffloat3[3] = 2.0F * f4 - f16;
/*  760 */     return 6;
/*      */   }
/*      */   
/*      */   private static boolean isFinite(float paramFloat) {
/*  764 */     return (Float.NEGATIVE_INFINITY < paramFloat && paramFloat < Float.POSITIVE_INFINITY);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroker(PathConsumer2D paramPathConsumer2D, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2) {
/*  770 */     this.middle = new float[16];
/*  771 */     this.lp = new float[8];
/*  772 */     this.rp = new float[8];
/*      */     
/*  774 */     this.subdivTs = new float[10];
/*      */     this.out = paramPathConsumer2D;
/*      */     this.lineWidth2 = paramFloat1 / 2.0F;
/*      */     this.capStyle = paramInt1;
/*      */     this.joinStyle = paramInt2;
/*      */     float f = paramFloat2 * this.lineWidth2;
/*      */     this.miterLimitSq = f * f;
/*      */     this.prev = 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  885 */   private static Curve c = new Curve();
/*      */   
/*      */   private static int findSubdivPoints(float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt, float paramFloat) {
/*  888 */     float f1 = paramArrayOffloat1[2] - paramArrayOffloat1[0];
/*  889 */     float f2 = paramArrayOffloat1[3] - paramArrayOffloat1[1];
/*      */ 
/*      */     
/*  892 */     if (f2 != 0.0F && f1 != 0.0F) {
/*      */ 
/*      */ 
/*      */       
/*  896 */       float f12, f13, f3 = (float)Math.sqrt((f1 * f1 + f2 * f2));
/*  897 */       float f4 = f1 / f3;
/*  898 */       float f5 = f2 / f3;
/*  899 */       float f6 = f4 * paramArrayOffloat1[0] + f5 * paramArrayOffloat1[1];
/*  900 */       float f7 = f4 * paramArrayOffloat1[1] - f5 * paramArrayOffloat1[0];
/*  901 */       float f8 = f4 * paramArrayOffloat1[2] + f5 * paramArrayOffloat1[3];
/*  902 */       float f9 = f4 * paramArrayOffloat1[3] - f5 * paramArrayOffloat1[2];
/*  903 */       float f10 = f4 * paramArrayOffloat1[4] + f5 * paramArrayOffloat1[5];
/*  904 */       float f11 = f4 * paramArrayOffloat1[5] - f5 * paramArrayOffloat1[4];
/*  905 */       switch (paramInt) {
/*      */         case 8:
/*  907 */           f12 = f4 * paramArrayOffloat1[6] + f5 * paramArrayOffloat1[7];
/*  908 */           f13 = f4 * paramArrayOffloat1[7] - f5 * paramArrayOffloat1[6];
/*  909 */           c.set(f6, f7, f8, f9, f10, f11, f12, f13);
/*      */           break;
/*      */         case 6:
/*  912 */           c.set(f6, f7, f8, f9, f10, f11);
/*      */           break;
/*      */       } 
/*      */     } else {
/*  916 */       c.set(paramArrayOffloat1, paramInt);
/*      */     } 
/*      */     
/*  919 */     int i = 0;
/*      */ 
/*      */     
/*  922 */     i += c.dxRoots(paramArrayOffloat2, i);
/*  923 */     i += c.dyRoots(paramArrayOffloat2, i);
/*      */     
/*  925 */     if (paramInt == 8)
/*      */     {
/*  927 */       i += c.infPoints(paramArrayOffloat2, i);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  932 */     i += c.rootsOfROCMinusW(paramArrayOffloat2, i, paramFloat, 1.0E-4F);
/*      */     
/*  934 */     i = Helpers.filterOutNotInAB(paramArrayOffloat2, 0, i, 1.0E-4F, 0.9999F);
/*  935 */     Helpers.isort(paramArrayOffloat2, 0, i);
/*  936 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
/*  943 */     this.middle[0] = this.cx0; this.middle[1] = this.cy0;
/*  944 */     this.middle[2] = paramFloat1; this.middle[3] = paramFloat2;
/*  945 */     this.middle[4] = paramFloat3; this.middle[5] = paramFloat4;
/*  946 */     this.middle[6] = paramFloat5; this.middle[7] = paramFloat6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     float f1 = this.middle[6], f2 = this.middle[7];
/*  953 */     float f3 = this.middle[2] - this.middle[0];
/*  954 */     float f4 = this.middle[3] - this.middle[1];
/*  955 */     float f5 = this.middle[6] - this.middle[4];
/*  956 */     float f6 = this.middle[7] - this.middle[5];
/*      */     
/*  958 */     boolean bool1 = (f3 == 0.0F && f4 == 0.0F) ? true : false;
/*  959 */     boolean bool2 = (f5 == 0.0F && f6 == 0.0F) ? true : false;
/*  960 */     if (bool1) {
/*  961 */       f3 = this.middle[4] - this.middle[0];
/*  962 */       f4 = this.middle[5] - this.middle[1];
/*  963 */       if (f3 == 0.0F && f4 == 0.0F) {
/*  964 */         f3 = this.middle[6] - this.middle[0];
/*  965 */         f4 = this.middle[7] - this.middle[1];
/*      */       } 
/*      */     } 
/*  968 */     if (bool2) {
/*  969 */       f5 = this.middle[6] - this.middle[2];
/*  970 */       f6 = this.middle[7] - this.middle[3];
/*  971 */       if (f5 == 0.0F && f6 == 0.0F) {
/*  972 */         f5 = this.middle[6] - this.middle[0];
/*  973 */         f6 = this.middle[7] - this.middle[1];
/*      */       } 
/*      */     } 
/*  976 */     if (f3 == 0.0F && f4 == 0.0F) {
/*      */       
/*  978 */       lineTo(this.middle[0], this.middle[1]);
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  984 */     if (Math.abs(f3) < 0.1F && Math.abs(f4) < 0.1F) {
/*  985 */       float f = (float)Math.sqrt((f3 * f3 + f4 * f4));
/*  986 */       f3 /= f;
/*  987 */       f4 /= f;
/*      */     } 
/*  989 */     if (Math.abs(f5) < 0.1F && Math.abs(f6) < 0.1F) {
/*  990 */       float f = (float)Math.sqrt((f5 * f5 + f6 * f6));
/*  991 */       f5 /= f;
/*  992 */       f6 /= f;
/*      */     } 
/*      */     
/*  995 */     computeOffset(f3, f4, this.lineWidth2, this.offset[0]);
/*  996 */     float f7 = this.offset[0][0];
/*  997 */     float f8 = this.offset[0][1];
/*  998 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, f3, f4, this.cmx, this.cmy, f7, f8);
/*      */     
/* 1000 */     int i = findSubdivPoints(this.middle, this.subdivTs, 8, this.lineWidth2);
/*      */     
/* 1002 */     int j = 0;
/* 1003 */     Iterator<Integer> iterator = Curve.breakPtsAtTs(this.middle, 8, this.subdivTs, i);
/* 1004 */     while (iterator.hasNext()) {
/* 1005 */       int k = ((Integer)iterator.next()).intValue();
/*      */       
/* 1007 */       j = computeOffsetCubic(this.middle, k, this.lp, this.rp);
/* 1008 */       emitLineTo(this.lp[0], this.lp[1]);
/* 1009 */       switch (j) {
/*      */         case 8:
/* 1011 */           emitCurveTo(this.lp[0], this.lp[1], this.lp[2], this.lp[3], this.lp[4], this.lp[5], this.lp[6], this.lp[7], false);
/* 1012 */           emitCurveTo(this.rp[0], this.rp[1], this.rp[2], this.rp[3], this.rp[4], this.rp[5], this.rp[6], this.rp[7], true);
/*      */           break;
/*      */         case 4:
/* 1015 */           emitLineTo(this.lp[2], this.lp[3]);
/* 1016 */           emitLineTo(this.rp[0], this.rp[1], true);
/*      */           break;
/*      */       } 
/* 1019 */       emitLineTo(this.rp[j - 2], this.rp[j - 1], true);
/*      */     } 
/*      */     
/* 1022 */     this.cmx = (this.lp[j - 2] - this.rp[j - 2]) / 2.0F;
/* 1023 */     this.cmy = (this.lp[j - 1] - this.rp[j - 1]) / 2.0F;
/* 1024 */     this.cdx = f5;
/* 1025 */     this.cdy = f6;
/* 1026 */     this.cx0 = f1;
/* 1027 */     this.cy0 = f2;
/* 1028 */     this.prev = 1;
/*      */   }
/*      */   
/*      */   public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 1032 */     this.middle[0] = this.cx0; this.middle[1] = this.cy0;
/* 1033 */     this.middle[2] = paramFloat1; this.middle[3] = paramFloat2;
/* 1034 */     this.middle[4] = paramFloat3; this.middle[5] = paramFloat4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1040 */     float f1 = this.middle[4], f2 = this.middle[5];
/* 1041 */     float f3 = this.middle[2] - this.middle[0];
/* 1042 */     float f4 = this.middle[3] - this.middle[1];
/* 1043 */     float f5 = this.middle[4] - this.middle[2];
/* 1044 */     float f6 = this.middle[5] - this.middle[3];
/* 1045 */     if ((f3 == 0.0F && f4 == 0.0F) || (f5 == 0.0F && f6 == 0.0F)) {
/* 1046 */       f3 = f5 = this.middle[4] - this.middle[0];
/* 1047 */       f4 = f6 = this.middle[5] - this.middle[1];
/*      */     } 
/* 1049 */     if (f3 == 0.0F && f4 == 0.0F) {
/*      */       
/* 1051 */       lineTo(this.middle[0], this.middle[1]);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1056 */     if (Math.abs(f3) < 0.1F && Math.abs(f4) < 0.1F) {
/* 1057 */       float f = (float)Math.sqrt((f3 * f3 + f4 * f4));
/* 1058 */       f3 /= f;
/* 1059 */       f4 /= f;
/*      */     } 
/* 1061 */     if (Math.abs(f5) < 0.1F && Math.abs(f6) < 0.1F) {
/* 1062 */       float f = (float)Math.sqrt((f5 * f5 + f6 * f6));
/* 1063 */       f5 /= f;
/* 1064 */       f6 /= f;
/*      */     } 
/*      */     
/* 1067 */     computeOffset(f3, f4, this.lineWidth2, this.offset[0]);
/* 1068 */     float f7 = this.offset[0][0];
/* 1069 */     float f8 = this.offset[0][1];
/* 1070 */     drawJoin(this.cdx, this.cdy, this.cx0, this.cy0, f3, f4, this.cmx, this.cmy, f7, f8);
/*      */     
/* 1072 */     int i = findSubdivPoints(this.middle, this.subdivTs, 6, this.lineWidth2);
/*      */     
/* 1074 */     int j = 0;
/* 1075 */     Iterator<Integer> iterator = Curve.breakPtsAtTs(this.middle, 6, this.subdivTs, i);
/* 1076 */     while (iterator.hasNext()) {
/* 1077 */       int k = ((Integer)iterator.next()).intValue();
/*      */       
/* 1079 */       j = computeOffsetQuad(this.middle, k, this.lp, this.rp);
/* 1080 */       emitLineTo(this.lp[0], this.lp[1]);
/* 1081 */       switch (j) {
/*      */         case 6:
/* 1083 */           emitQuadTo(this.lp[0], this.lp[1], this.lp[2], this.lp[3], this.lp[4], this.lp[5], false);
/* 1084 */           emitQuadTo(this.rp[0], this.rp[1], this.rp[2], this.rp[3], this.rp[4], this.rp[5], true);
/*      */           break;
/*      */         case 4:
/* 1087 */           emitLineTo(this.lp[2], this.lp[3]);
/* 1088 */           emitLineTo(this.rp[0], this.rp[1], true);
/*      */           break;
/*      */       } 
/* 1091 */       emitLineTo(this.rp[j - 2], this.rp[j - 1], true);
/*      */     } 
/*      */     
/* 1094 */     this.cmx = (this.lp[j - 2] - this.rp[j - 2]) / 2.0F;
/* 1095 */     this.cmy = (this.lp[j - 1] - this.rp[j - 1]) / 2.0F;
/* 1096 */     this.cdx = f5;
/* 1097 */     this.cdy = f6;
/* 1098 */     this.cx0 = f1;
/* 1099 */     this.cy0 = f2;
/* 1100 */     this.prev = 1;
/*      */   }
/*      */   
/*      */   public long getNativeConsumer() {
/* 1104 */     throw new InternalError("Stroker doesn't use a native consumer");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class PolyStack
/*      */   {
/* 1118 */     float[] curves = new float[400];
/* 1119 */     int[] curveTypes = new int[50];
/* 1120 */     int end = 0;
/* 1121 */     int numCurves = 0;
/*      */     private static final int INIT_SIZE = 50;
/*      */     
/*      */     public boolean isEmpty() {
/* 1125 */       return (this.numCurves == 0);
/*      */     }
/*      */     
/*      */     private void ensureSpace(int param1Int) {
/* 1129 */       if (this.end + param1Int >= this.curves.length) {
/* 1130 */         int i = (this.end + param1Int) * 2;
/* 1131 */         this.curves = Arrays.copyOf(this.curves, i);
/*      */       } 
/* 1133 */       if (this.numCurves >= this.curveTypes.length) {
/* 1134 */         int i = this.numCurves * 2;
/* 1135 */         this.curveTypes = Arrays.copyOf(this.curveTypes, i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void pushCubic(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/* 1143 */       ensureSpace(6);
/* 1144 */       this.curveTypes[this.numCurves++] = 8;
/*      */ 
/*      */ 
/*      */       
/* 1148 */       this.curves[this.end++] = param1Float5; this.curves[this.end++] = param1Float6;
/* 1149 */       this.curves[this.end++] = param1Float3; this.curves[this.end++] = param1Float4;
/* 1150 */       this.curves[this.end++] = param1Float1; this.curves[this.end++] = param1Float2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void pushQuad(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 1156 */       ensureSpace(4);
/* 1157 */       this.curveTypes[this.numCurves++] = 6;
/*      */       
/* 1159 */       this.curves[this.end++] = param1Float3; this.curves[this.end++] = param1Float4;
/* 1160 */       this.curves[this.end++] = param1Float1; this.curves[this.end++] = param1Float2;
/*      */     }
/*      */     
/*      */     public void pushLine(float param1Float1, float param1Float2) {
/* 1164 */       ensureSpace(2);
/* 1165 */       this.curveTypes[this.numCurves++] = 4;
/*      */       
/* 1167 */       this.curves[this.end++] = param1Float1; this.curves[this.end++] = param1Float2;
/*      */     }
/*      */ 
/*      */     
/*      */     public int pop(float[] param1ArrayOffloat) {
/* 1172 */       int i = this.curveTypes[this.numCurves - 1];
/* 1173 */       this.numCurves--;
/* 1174 */       this.end -= i - 2;
/* 1175 */       System.arraycopy(this.curves, this.end, param1ArrayOffloat, 0, i - 2);
/* 1176 */       return i;
/*      */     }
/*      */     
/*      */     public void pop(PathConsumer2D param1PathConsumer2D) {
/* 1180 */       this.numCurves--;
/* 1181 */       int i = this.curveTypes[this.numCurves];
/* 1182 */       this.end -= i - 2;
/* 1183 */       switch (i) {
/*      */         case 8:
/* 1185 */           param1PathConsumer2D.curveTo(this.curves[this.end + 0], this.curves[this.end + 1], this.curves[this.end + 2], this.curves[this.end + 3], this.curves[this.end + 4], this.curves[this.end + 5]);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 6:
/* 1190 */           param1PathConsumer2D.quadTo(this.curves[this.end + 0], this.curves[this.end + 1], this.curves[this.end + 2], this.curves[this.end + 3]);
/*      */           break;
/*      */         
/*      */         case 4:
/* 1194 */           param1PathConsumer2D.lineTo(this.curves[this.end], this.curves[this.end + 1]);
/*      */           break;
/*      */       } 
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1200 */       String str = "";
/* 1201 */       int i = this.numCurves;
/* 1202 */       int j = this.end;
/* 1203 */       while (i > 0) {
/* 1204 */         i--;
/* 1205 */         int k = this.curveTypes[this.numCurves];
/* 1206 */         j -= k - 2;
/* 1207 */         switch (k) {
/*      */           case 8:
/* 1209 */             str = str + "cubic: ";
/*      */             break;
/*      */           case 6:
/* 1212 */             str = str + "quad: ";
/*      */             break;
/*      */           case 4:
/* 1215 */             str = str + "line: ";
/*      */             break;
/*      */         } 
/* 1218 */         str = str + Arrays.toString(Arrays.copyOfRange(this.curves, j, j + k - 2)) + "\n";
/*      */       } 
/* 1220 */       return str;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pisces/Stroker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */