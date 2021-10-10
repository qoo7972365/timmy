/*      */ package sun.java2d.pipe;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.RectangularShape;
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
/*      */ public class Region
/*      */ {
/*      */   static final int INIT_SIZE = 50;
/*      */   static final int GROW_SIZE = 50;
/*      */   
/*      */   private static final class ImmutableRegion
/*      */     extends Region
/*      */   {
/*      */     public void appendSpans(SpanIterator param1SpanIterator) {}
/*      */     
/*      */     public void setOutputArea(Rectangle param1Rectangle) {}
/*      */     
/*      */     public void setOutputAreaXYWH(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
/*      */     
/*      */     protected ImmutableRegion(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*   74 */       super(param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setOutputArea(int[] param1ArrayOfint) {}
/*      */ 
/*      */     
/*      */     public void setOutputAreaXYXY(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
/*      */   }
/*      */ 
/*      */   
/*   85 */   public static final Region EMPTY_REGION = new ImmutableRegion(0, 0, 0, 0);
/*   86 */   public static final Region WHOLE_REGION = new ImmutableRegion(-2147483648, -2147483648, 2147483647, 2147483647);
/*      */   
/*      */   int lox;
/*      */   
/*      */   int loy;
/*      */   
/*      */   int hix;
/*      */   
/*      */   int hiy;
/*      */   
/*      */   int endIndex;
/*      */   int[] bands;
/*      */   static final int INCLUDE_A = 1;
/*      */   static final int INCLUDE_B = 2;
/*      */   static final int INCLUDE_COMMON = 4;
/*      */   
/*      */   static {
/*  103 */     initIDs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int dimAdd(int paramInt1, int paramInt2) {
/*  114 */     if (paramInt2 <= 0) return paramInt1; 
/*  115 */     if ((paramInt2 += paramInt1) < paramInt1) return Integer.MAX_VALUE; 
/*  116 */     return paramInt2;
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
/*      */   public static int clipAdd(int paramInt1, int paramInt2) {
/*  129 */     int i = paramInt1 + paramInt2;
/*  130 */     if (((i > paramInt1) ? true : false) != ((paramInt2 > 0) ? true : false)) {
/*  131 */       i = (paramInt2 < 0) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
/*      */     }
/*  133 */     return i;
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
/*      */   public static int clipScale(int paramInt, double paramDouble) {
/*  145 */     if (paramDouble == 1.0D) {
/*  146 */       return paramInt;
/*      */     }
/*  148 */     double d = paramInt * paramDouble;
/*  149 */     if (d < -2.147483648E9D) {
/*  150 */       return Integer.MIN_VALUE;
/*      */     }
/*  152 */     if (d > 2.147483647E9D) {
/*  153 */       return Integer.MAX_VALUE;
/*      */     }
/*  155 */     return (int)Math.round(d);
/*      */   }
/*      */   
/*      */   protected Region(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  159 */     this.lox = paramInt1;
/*  160 */     this.loy = paramInt2;
/*  161 */     this.hix = paramInt3;
/*  162 */     this.hiy = paramInt4;
/*      */   }
/*      */   
/*      */   private Region(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5) {
/*  166 */     this.lox = paramInt1;
/*  167 */     this.loy = paramInt2;
/*  168 */     this.hix = paramInt3;
/*  169 */     this.hiy = paramInt4;
/*  170 */     this.bands = paramArrayOfint;
/*  171 */     this.endIndex = paramInt5;
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
/*      */   public static Region getInstance(Shape paramShape, AffineTransform paramAffineTransform) {
/*  187 */     return getInstance(WHOLE_REGION, false, paramShape, paramAffineTransform);
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
/*      */   public static Region getInstance(Region paramRegion, Shape paramShape, AffineTransform paramAffineTransform) {
/*  215 */     return getInstance(paramRegion, false, paramShape, paramAffineTransform);
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
/*      */   public static Region getInstance(Region paramRegion, boolean paramBoolean, Shape paramShape, AffineTransform paramAffineTransform) {
/*  249 */     if (paramShape instanceof RectangularShape && ((RectangularShape)paramShape)
/*  250 */       .isEmpty())
/*      */     {
/*  252 */       return EMPTY_REGION;
/*      */     }
/*      */     
/*  255 */     int[] arrayOfInt = new int[4];
/*  256 */     ShapeSpanIterator shapeSpanIterator = new ShapeSpanIterator(paramBoolean);
/*      */     try {
/*  258 */       shapeSpanIterator.setOutputArea(paramRegion);
/*  259 */       shapeSpanIterator.appendPath(paramShape.getPathIterator(paramAffineTransform));
/*  260 */       shapeSpanIterator.getPathBox(arrayOfInt);
/*  261 */       Region region = getInstance(arrayOfInt);
/*  262 */       region.appendSpans(shapeSpanIterator);
/*  263 */       return region;
/*      */     } finally {
/*  265 */       shapeSpanIterator.dispose();
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
/*      */   static Region getInstance(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
/*  279 */     int i = paramArrayOfint[0];
/*  280 */     int j = paramArrayOfint[1];
/*  281 */     if (paramInt4 <= paramInt2 || paramInt3 <= paramInt1 || j <= i) {
/*  282 */       return EMPTY_REGION;
/*      */     }
/*      */     
/*  285 */     int[] arrayOfInt = new int[(j - i) * 5];
/*  286 */     byte b1 = 0;
/*  287 */     byte b2 = 2;
/*  288 */     for (int k = i; k < j; k++) {
/*  289 */       int m = Math.max(clipAdd(paramInt1, paramArrayOfint[b2++]), paramInt1);
/*  290 */       int n = Math.min(clipAdd(paramInt1, paramArrayOfint[b2++]), paramInt3);
/*  291 */       if (m < n) {
/*  292 */         int i1 = Math.max(clipAdd(paramInt2, k), paramInt2);
/*  293 */         int i2 = Math.min(clipAdd(i1, 1), paramInt4);
/*  294 */         if (i1 < i2) {
/*  295 */           arrayOfInt[b1++] = i1;
/*  296 */           arrayOfInt[b1++] = i2;
/*  297 */           arrayOfInt[b1++] = 1;
/*  298 */           arrayOfInt[b1++] = m;
/*  299 */           arrayOfInt[b1++] = n;
/*      */         } 
/*      */       } 
/*      */     } 
/*  303 */     return (b1 != 0) ? new Region(paramInt1, paramInt2, paramInt3, paramInt4, arrayOfInt, b1) : EMPTY_REGION;
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
/*      */   public static Region getInstance(Rectangle paramRectangle) {
/*  315 */     return getInstanceXYWH(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Region getInstanceXYWH(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  326 */     return getInstanceXYXY(paramInt1, paramInt2, dimAdd(paramInt1, paramInt3), dimAdd(paramInt2, paramInt4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Region getInstance(int[] paramArrayOfint) {
/*  337 */     return new Region(paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[2], paramArrayOfint[3]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Region getInstanceXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  348 */     return new Region(paramInt1, paramInt2, paramInt3, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputArea(Rectangle paramRectangle) {
/*  359 */     setOutputAreaXYWH(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
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
/*      */   public void setOutputAreaXYWH(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  372 */     setOutputAreaXYXY(paramInt1, paramInt2, dimAdd(paramInt1, paramInt3), dimAdd(paramInt2, paramInt4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputArea(int[] paramArrayOfint) {
/*  383 */     this.lox = paramArrayOfint[0];
/*  384 */     this.loy = paramArrayOfint[1];
/*  385 */     this.hix = paramArrayOfint[2];
/*  386 */     this.hiy = paramArrayOfint[3];
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
/*      */   public void setOutputAreaXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  398 */     this.lox = paramInt1;
/*  399 */     this.loy = paramInt2;
/*  400 */     this.hix = paramInt3;
/*  401 */     this.hiy = paramInt4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendSpans(SpanIterator paramSpanIterator) {
/*  412 */     int[] arrayOfInt = new int[6];
/*      */     
/*  414 */     while (paramSpanIterator.nextSpan(arrayOfInt)) {
/*  415 */       appendSpan(arrayOfInt);
/*      */     }
/*      */     
/*  418 */     endRow(arrayOfInt);
/*  419 */     calcBBox();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Region getScaledRegion(double paramDouble1, double paramDouble2) {
/*  427 */     if (paramDouble1 == 0.0D || paramDouble2 == 0.0D || this == EMPTY_REGION) {
/*  428 */       return EMPTY_REGION;
/*      */     }
/*  430 */     if ((paramDouble1 == 1.0D && paramDouble2 == 1.0D) || this == WHOLE_REGION) {
/*  431 */       return this;
/*      */     }
/*      */     
/*  434 */     int i = clipScale(this.lox, paramDouble1);
/*  435 */     int j = clipScale(this.loy, paramDouble2);
/*  436 */     int k = clipScale(this.hix, paramDouble1);
/*  437 */     int m = clipScale(this.hiy, paramDouble2);
/*  438 */     Region region = new Region(i, j, k, m);
/*  439 */     int[] arrayOfInt = this.bands;
/*  440 */     if (arrayOfInt != null) {
/*  441 */       int n = this.endIndex;
/*  442 */       int[] arrayOfInt1 = new int[n];
/*  443 */       int i1 = 0;
/*  444 */       int i2 = 0;
/*      */       
/*  446 */       while (i1 < n) {
/*      */         
/*  448 */         int i4 = clipScale(arrayOfInt[i1++], paramDouble2);
/*  449 */         int i5 = clipScale(arrayOfInt[i1++], paramDouble2);
/*  450 */         int i3 = arrayOfInt[i1++];
/*  451 */         int i6 = i2;
/*  452 */         if (i4 < i5) {
/*  453 */           while (--i3 >= 0) {
/*  454 */             int i7 = clipScale(arrayOfInt[i1++], paramDouble1);
/*  455 */             int i8 = clipScale(arrayOfInt[i1++], paramDouble1);
/*  456 */             if (i7 < i8) {
/*  457 */               arrayOfInt1[i2++] = i7;
/*  458 */               arrayOfInt1[i2++] = i8;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  462 */           i1 += i3 * 2;
/*      */         } 
/*      */         
/*  465 */         if (i2 > i6) {
/*  466 */           arrayOfInt1[i6 - 1] = (i2 - i6) / 2; continue;
/*      */         } 
/*  468 */         i2 = i6 - 3;
/*      */       } 
/*      */       
/*  471 */       if (i2 <= 5) {
/*  472 */         if (i2 < 5) {
/*      */           
/*  474 */           region.lox = region.loy = region.hix = region.hiy = 0;
/*      */         } else {
/*      */           
/*  477 */           region.loy = arrayOfInt1[0];
/*  478 */           region.hiy = arrayOfInt1[1];
/*  479 */           region.lox = arrayOfInt1[3];
/*  480 */           region.hix = arrayOfInt1[4];
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  487 */         region.endIndex = i2;
/*  488 */         region.bands = arrayOfInt1;
/*      */       } 
/*      */     } 
/*  491 */     return region;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Region getTranslatedRegion(int paramInt1, int paramInt2) {
/*  501 */     if ((paramInt1 | paramInt2) == 0) {
/*  502 */       return this;
/*      */     }
/*  504 */     int i = this.lox + paramInt1;
/*  505 */     int j = this.loy + paramInt2;
/*  506 */     int k = this.hix + paramInt1;
/*  507 */     int m = this.hiy + paramInt2;
/*  508 */     if (((i > this.lox) ? true : false) == ((paramInt1 > 0) ? true : false)) if (((j > this.loy) ? true : false) == ((paramInt2 > 0) ? true : false)) if (((k > this.hix) ? true : false) == ((paramInt1 > 0) ? true : false)) if (((m > this.hiy) ? true : false) == ((paramInt2 > 0) ? true : false)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  515 */             Region region = new Region(i, j, k, m);
/*  516 */             int[] arrayOfInt = this.bands;
/*  517 */             if (arrayOfInt != null) {
/*  518 */               int n = this.endIndex;
/*  519 */               region.endIndex = n;
/*  520 */               int[] arrayOfInt1 = new int[n];
/*  521 */               region.bands = arrayOfInt1;
/*  522 */               byte b = 0;
/*      */               
/*  524 */               while (b < n) {
/*  525 */                 arrayOfInt1[b] = arrayOfInt[b] + paramInt2; b++;
/*  526 */                 arrayOfInt1[b] = arrayOfInt[b] + paramInt2; b++;
/*  527 */                 int i1 = arrayOfInt[b]; b++;
/*  528 */                 while (--i1 >= 0) {
/*  529 */                   arrayOfInt1[b] = arrayOfInt[b] + paramInt1; b++;
/*  530 */                   arrayOfInt1[b] = arrayOfInt[b] + paramInt1; b++;
/*      */                 } 
/*      */               } 
/*      */             } 
/*  534 */             return region;
/*      */           }    
/*      */     return getSafeTranslatedRegion(paramInt1, paramInt2);
/*      */   } private Region getSafeTranslatedRegion(int paramInt1, int paramInt2) {
/*  538 */     int i = clipAdd(this.lox, paramInt1);
/*  539 */     int j = clipAdd(this.loy, paramInt2);
/*  540 */     int k = clipAdd(this.hix, paramInt1);
/*  541 */     int m = clipAdd(this.hiy, paramInt2);
/*  542 */     Region region = new Region(i, j, k, m);
/*  543 */     int[] arrayOfInt = this.bands;
/*  544 */     if (arrayOfInt != null) {
/*  545 */       int n = this.endIndex;
/*  546 */       int[] arrayOfInt1 = new int[n];
/*  547 */       int i1 = 0;
/*  548 */       int i2 = 0;
/*      */       
/*  550 */       while (i1 < n) {
/*      */         
/*  552 */         int i4 = clipAdd(arrayOfInt[i1++], paramInt2);
/*  553 */         int i5 = clipAdd(arrayOfInt[i1++], paramInt2);
/*  554 */         int i3 = arrayOfInt[i1++];
/*  555 */         int i6 = i2;
/*  556 */         if (i4 < i5) {
/*  557 */           while (--i3 >= 0) {
/*  558 */             int i7 = clipAdd(arrayOfInt[i1++], paramInt1);
/*  559 */             int i8 = clipAdd(arrayOfInt[i1++], paramInt1);
/*  560 */             if (i7 < i8) {
/*  561 */               arrayOfInt1[i2++] = i7;
/*  562 */               arrayOfInt1[i2++] = i8;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  566 */           i1 += i3 * 2;
/*      */         } 
/*      */         
/*  569 */         if (i2 > i6) {
/*  570 */           arrayOfInt1[i6 - 1] = (i2 - i6) / 2; continue;
/*      */         } 
/*  572 */         i2 = i6 - 3;
/*      */       } 
/*      */       
/*  575 */       if (i2 <= 5) {
/*  576 */         if (i2 < 5) {
/*      */           
/*  578 */           region.lox = region.loy = region.hix = region.hiy = 0;
/*      */         } else {
/*      */           
/*  581 */           region.loy = arrayOfInt1[0];
/*  582 */           region.hiy = arrayOfInt1[1];
/*  583 */           region.lox = arrayOfInt1[3];
/*  584 */           region.hix = arrayOfInt1[4];
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  591 */         region.endIndex = i2;
/*  592 */         region.bands = arrayOfInt1;
/*      */       } 
/*      */     } 
/*  595 */     return region;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Region getIntersection(Rectangle paramRectangle) {
/*  604 */     return getIntersectionXYWH(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Region getIntersectionXYWH(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  613 */     return getIntersectionXYXY(paramInt1, paramInt2, dimAdd(paramInt1, paramInt3), dimAdd(paramInt2, paramInt4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Region getIntersectionXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  622 */     if (isInsideXYXY(paramInt1, paramInt2, paramInt3, paramInt4)) {
/*  623 */       return this;
/*      */     }
/*  625 */     Region region = new Region((paramInt1 < this.lox) ? this.lox : paramInt1, (paramInt2 < this.loy) ? this.loy : paramInt2, (paramInt3 > this.hix) ? this.hix : paramInt3, (paramInt4 > this.hiy) ? this.hiy : paramInt4);
/*      */ 
/*      */ 
/*      */     
/*  629 */     if (this.bands != null) {
/*  630 */       region.appendSpans(getSpanIterator());
/*      */     }
/*  632 */     return region;
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
/*      */   public Region getIntersection(Region paramRegion) {
/*  648 */     if (isInsideQuickCheck(paramRegion)) {
/*  649 */       return this;
/*      */     }
/*  651 */     if (paramRegion.isInsideQuickCheck(this)) {
/*  652 */       return paramRegion;
/*      */     }
/*  654 */     Region region = new Region((paramRegion.lox < this.lox) ? this.lox : paramRegion.lox, (paramRegion.loy < this.loy) ? this.loy : paramRegion.loy, (paramRegion.hix > this.hix) ? this.hix : paramRegion.hix, (paramRegion.hiy > this.hiy) ? this.hiy : paramRegion.hiy);
/*      */ 
/*      */ 
/*      */     
/*  658 */     if (!region.isEmpty()) {
/*  659 */       region.filterSpans(this, paramRegion, 4);
/*      */     }
/*  661 */     return region;
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
/*      */   public Region getUnion(Region paramRegion) {
/*  677 */     if (paramRegion.isEmpty() || paramRegion.isInsideQuickCheck(this)) {
/*  678 */       return this;
/*      */     }
/*  680 */     if (isEmpty() || isInsideQuickCheck(paramRegion)) {
/*  681 */       return paramRegion;
/*      */     }
/*  683 */     Region region = new Region((paramRegion.lox > this.lox) ? this.lox : paramRegion.lox, (paramRegion.loy > this.loy) ? this.loy : paramRegion.loy, (paramRegion.hix < this.hix) ? this.hix : paramRegion.hix, (paramRegion.hiy < this.hiy) ? this.hiy : paramRegion.hiy);
/*      */ 
/*      */ 
/*      */     
/*  687 */     region.filterSpans(this, paramRegion, 7);
/*  688 */     return region;
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
/*      */   public Region getDifference(Region paramRegion) {
/*  704 */     if (!paramRegion.intersectsQuickCheck(this)) {
/*  705 */       return this;
/*      */     }
/*  707 */     if (isInsideQuickCheck(paramRegion)) {
/*  708 */       return EMPTY_REGION;
/*      */     }
/*  710 */     Region region = new Region(this.lox, this.loy, this.hix, this.hiy);
/*  711 */     region.filterSpans(this, paramRegion, 1);
/*  712 */     return region;
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
/*      */   public Region getExclusiveOr(Region paramRegion) {
/*  728 */     if (paramRegion.isEmpty()) {
/*  729 */       return this;
/*      */     }
/*  731 */     if (isEmpty()) {
/*  732 */       return paramRegion;
/*      */     }
/*  734 */     Region region = new Region((paramRegion.lox > this.lox) ? this.lox : paramRegion.lox, (paramRegion.loy > this.loy) ? this.loy : paramRegion.loy, (paramRegion.hix < this.hix) ? this.hix : paramRegion.hix, (paramRegion.hiy < this.hiy) ? this.hiy : paramRegion.hiy);
/*      */ 
/*      */ 
/*      */     
/*  738 */     region.filterSpans(this, paramRegion, 3);
/*  739 */     return region;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void filterSpans(Region paramRegion1, Region paramRegion2, int paramInt) {
/*  747 */     int[] arrayOfInt1 = paramRegion1.bands;
/*  748 */     int[] arrayOfInt2 = paramRegion2.bands;
/*  749 */     if (arrayOfInt1 == null) {
/*  750 */       arrayOfInt1 = new int[] { paramRegion1.loy, paramRegion1.hiy, 1, paramRegion1.lox, paramRegion1.hix };
/*      */     }
/*  752 */     if (arrayOfInt2 == null) {
/*  753 */       arrayOfInt2 = new int[] { paramRegion2.loy, paramRegion2.hiy, 1, paramRegion2.lox, paramRegion2.hix };
/*      */     }
/*  755 */     int[] arrayOfInt3 = new int[6];
/*  756 */     int i = 0;
/*  757 */     int j = arrayOfInt1[i++];
/*  758 */     int k = arrayOfInt1[i++];
/*  759 */     int m = arrayOfInt1[i++];
/*  760 */     m = i + 2 * m;
/*  761 */     int n = 0;
/*  762 */     int i1 = arrayOfInt2[n++];
/*  763 */     int i2 = arrayOfInt2[n++];
/*  764 */     int i3 = arrayOfInt2[n++];
/*  765 */     i3 = n + 2 * i3;
/*  766 */     int i4 = this.loy;
/*  767 */     while (i4 < this.hiy) {
/*  768 */       int i5; if (i4 >= k) {
/*  769 */         if (m < paramRegion1.endIndex) {
/*  770 */           i = m;
/*  771 */           j = arrayOfInt1[i++];
/*  772 */           k = arrayOfInt1[i++];
/*  773 */           m = arrayOfInt1[i++];
/*  774 */           m = i + 2 * m; continue;
/*      */         } 
/*  776 */         if ((paramInt & 0x2) == 0)
/*  777 */           break;  j = k = this.hiy;
/*      */         
/*      */         continue;
/*      */       } 
/*  781 */       if (i4 >= i2) {
/*  782 */         if (i3 < paramRegion2.endIndex) {
/*  783 */           n = i3;
/*  784 */           i1 = arrayOfInt2[n++];
/*  785 */           i2 = arrayOfInt2[n++];
/*  786 */           i3 = arrayOfInt2[n++];
/*  787 */           i3 = n + 2 * i3; continue;
/*      */         } 
/*  789 */         if ((paramInt & 0x1) == 0)
/*  790 */           break;  i1 = i2 = this.hiy;
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/*  795 */       if (i4 < i1) {
/*  796 */         if (i4 < j) {
/*  797 */           i4 = Math.min(j, i1);
/*      */           
/*      */           continue;
/*      */         } 
/*  801 */         i5 = Math.min(k, i1);
/*  802 */         if ((paramInt & 0x1) != 0) {
/*  803 */           arrayOfInt3[1] = i4;
/*  804 */           arrayOfInt3[3] = i5;
/*  805 */           int i6 = i;
/*  806 */           while (i6 < m) {
/*  807 */             arrayOfInt3[0] = arrayOfInt1[i6++];
/*  808 */             arrayOfInt3[2] = arrayOfInt1[i6++];
/*  809 */             appendSpan(arrayOfInt3);
/*      */           } 
/*      */         } 
/*  812 */       } else if (i4 < j) {
/*      */         
/*  814 */         i5 = Math.min(i2, j);
/*  815 */         if ((paramInt & 0x2) != 0) {
/*  816 */           arrayOfInt3[1] = i4;
/*  817 */           arrayOfInt3[3] = i5;
/*  818 */           int i6 = n;
/*  819 */           while (i6 < i3) {
/*  820 */             arrayOfInt3[0] = arrayOfInt2[i6++];
/*  821 */             arrayOfInt3[2] = arrayOfInt2[i6++];
/*  822 */             appendSpan(arrayOfInt3);
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  827 */         i5 = Math.min(k, i2);
/*  828 */         arrayOfInt3[1] = i4;
/*  829 */         arrayOfInt3[3] = i5;
/*  830 */         int i6 = i;
/*  831 */         int i7 = n;
/*  832 */         int i8 = arrayOfInt1[i6++];
/*  833 */         int i9 = arrayOfInt1[i6++];
/*  834 */         int i10 = arrayOfInt2[i7++];
/*  835 */         int i11 = arrayOfInt2[i7++];
/*  836 */         int i12 = Math.min(i8, i10);
/*  837 */         if (i12 < this.lox) i12 = this.lox; 
/*  838 */         while (i12 < this.hix) {
/*  839 */           int i13; boolean bool; if (i12 >= i9) {
/*  840 */             if (i6 < m) {
/*  841 */               i8 = arrayOfInt1[i6++];
/*  842 */               i9 = arrayOfInt1[i6++]; continue;
/*      */             } 
/*  844 */             if ((paramInt & 0x2) == 0)
/*  845 */               break;  i8 = i9 = this.hix;
/*      */             
/*      */             continue;
/*      */           } 
/*  849 */           if (i12 >= i11) {
/*  850 */             if (i7 < i3) {
/*  851 */               i10 = arrayOfInt2[i7++];
/*  852 */               i11 = arrayOfInt2[i7++]; continue;
/*      */             } 
/*  854 */             if ((paramInt & 0x1) == 0)
/*  855 */               break;  i10 = i11 = this.hix;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  861 */           if (i12 < i10) {
/*  862 */             if (i12 < i8) {
/*  863 */               i13 = Math.min(i8, i10);
/*  864 */               bool = false;
/*      */             } else {
/*  866 */               i13 = Math.min(i9, i10);
/*  867 */               bool = ((paramInt & 0x1) != 0) ? true : false;
/*      */             } 
/*  869 */           } else if (i12 < i8) {
/*  870 */             i13 = Math.min(i8, i11);
/*  871 */             bool = ((paramInt & 0x2) != 0) ? true : false;
/*      */           } else {
/*  873 */             i13 = Math.min(i9, i11);
/*  874 */             bool = ((paramInt & 0x4) != 0) ? true : false;
/*      */           } 
/*  876 */           if (bool) {
/*  877 */             arrayOfInt3[0] = i12;
/*  878 */             arrayOfInt3[2] = i13;
/*  879 */             appendSpan(arrayOfInt3);
/*      */           } 
/*  881 */           i12 = i13;
/*      */         } 
/*      */       } 
/*  884 */       i4 = i5;
/*      */     } 
/*  886 */     endRow(arrayOfInt3);
/*  887 */     calcBBox();
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
/*      */   public Region getBoundsIntersection(Rectangle paramRectangle) {
/*  899 */     return getBoundsIntersectionXYWH(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
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
/*      */   public Region getBoundsIntersectionXYWH(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  911 */     return getBoundsIntersectionXYXY(paramInt1, paramInt2, dimAdd(paramInt1, paramInt3), dimAdd(paramInt2, paramInt4));
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
/*      */   public Region getBoundsIntersectionXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  925 */     if (this.bands == null && this.lox >= paramInt1 && this.loy >= paramInt2 && this.hix <= paramInt3 && this.hiy <= paramInt4)
/*      */     {
/*      */ 
/*      */       
/*  929 */       return this;
/*      */     }
/*  931 */     return new Region((paramInt1 < this.lox) ? this.lox : paramInt1, (paramInt2 < this.loy) ? this.loy : paramInt2, (paramInt3 > this.hix) ? this.hix : paramInt3, (paramInt4 > this.hiy) ? this.hiy : paramInt4);
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
/*      */   public Region getBoundsIntersection(Region paramRegion) {
/*  946 */     if (encompasses(paramRegion)) {
/*  947 */       return paramRegion;
/*      */     }
/*  949 */     if (paramRegion.encompasses(this)) {
/*  950 */       return this;
/*      */     }
/*  952 */     return new Region((paramRegion.lox < this.lox) ? this.lox : paramRegion.lox, (paramRegion.loy < this.loy) ? this.loy : paramRegion.loy, (paramRegion.hix > this.hix) ? this.hix : paramRegion.hix, (paramRegion.hiy > this.hiy) ? this.hiy : paramRegion.hiy);
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
/*      */   private void appendSpan(int[] paramArrayOfint) {
/*      */     int i;
/*  968 */     if ((i = paramArrayOfint[0]) < this.lox) i = this.lox;  int j;
/*  969 */     if ((j = paramArrayOfint[1]) < this.loy) j = this.loy;  int k;
/*  970 */     if ((k = paramArrayOfint[2]) > this.hix) k = this.hix;  int m;
/*  971 */     if ((m = paramArrayOfint[3]) > this.hiy) m = this.hiy; 
/*  972 */     if (k <= i || m <= j) {
/*      */       return;
/*      */     }
/*      */     
/*  976 */     int n = paramArrayOfint[4];
/*  977 */     if (this.endIndex == 0 || j >= this.bands[n + 1]) {
/*  978 */       if (this.bands == null) {
/*  979 */         this.bands = new int[50];
/*      */       } else {
/*  981 */         needSpace(5);
/*  982 */         endRow(paramArrayOfint);
/*  983 */         n = paramArrayOfint[4];
/*      */       } 
/*  985 */       this.bands[this.endIndex++] = j;
/*  986 */       this.bands[this.endIndex++] = m;
/*  987 */       this.bands[this.endIndex++] = 0;
/*  988 */     } else if (j == this.bands[n] && m == this.bands[n + 1] && i >= this.bands[this.endIndex - 1]) {
/*      */ 
/*      */       
/*  991 */       if (i == this.bands[this.endIndex - 1]) {
/*  992 */         this.bands[this.endIndex - 1] = k;
/*      */         return;
/*      */       } 
/*  995 */       needSpace(2);
/*      */     } else {
/*  997 */       throw new InternalError("bad span");
/*      */     } 
/*  999 */     this.bands[this.endIndex++] = i;
/* 1000 */     this.bands[this.endIndex++] = k;
/* 1001 */     this.bands[n + 2] = this.bands[n + 2] + 1;
/*      */   }
/*      */   
/*      */   private void needSpace(int paramInt) {
/* 1005 */     if (this.endIndex + paramInt >= this.bands.length) {
/* 1006 */       int[] arrayOfInt = new int[this.bands.length + 50];
/* 1007 */       System.arraycopy(this.bands, 0, arrayOfInt, 0, this.endIndex);
/* 1008 */       this.bands = arrayOfInt;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void endRow(int[] paramArrayOfint) {
/* 1013 */     int i = paramArrayOfint[4];
/* 1014 */     int j = paramArrayOfint[5];
/* 1015 */     if (i > j) {
/* 1016 */       int[] arrayOfInt = this.bands;
/* 1017 */       if (arrayOfInt[j + 1] == arrayOfInt[i] && arrayOfInt[j + 2] == arrayOfInt[i + 2]) {
/*      */ 
/*      */         
/* 1020 */         int k = arrayOfInt[i + 2] * 2;
/* 1021 */         i += 3;
/* 1022 */         j += 3;
/* 1023 */         while (k > 0 && 
/* 1024 */           arrayOfInt[i++] == arrayOfInt[j++])
/*      */         {
/*      */           
/* 1027 */           k--;
/*      */         }
/* 1029 */         if (k == 0) {
/*      */           
/* 1031 */           arrayOfInt[paramArrayOfint[5] + 1] = arrayOfInt[j + 1];
/* 1032 */           this.endIndex = j;
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1037 */     paramArrayOfint[5] = paramArrayOfint[4];
/* 1038 */     paramArrayOfint[4] = this.endIndex;
/*      */   }
/*      */   
/*      */   private void calcBBox() {
/* 1042 */     int[] arrayOfInt = this.bands;
/* 1043 */     if (this.endIndex <= 5) {
/* 1044 */       if (this.endIndex == 0) {
/* 1045 */         this.lox = this.loy = this.hix = this.hiy = 0;
/*      */       } else {
/* 1047 */         this.loy = arrayOfInt[0];
/* 1048 */         this.hiy = arrayOfInt[1];
/* 1049 */         this.lox = arrayOfInt[3];
/* 1050 */         this.hix = arrayOfInt[4];
/* 1051 */         this.endIndex = 0;
/*      */       } 
/* 1053 */       this.bands = null;
/*      */       return;
/*      */     } 
/* 1056 */     int i = this.hix;
/* 1057 */     int j = this.lox;
/* 1058 */     int k = 0;
/*      */     
/* 1060 */     int m = 0;
/* 1061 */     while (m < this.endIndex) {
/* 1062 */       k = m;
/* 1063 */       int n = arrayOfInt[m + 2];
/* 1064 */       m += 3;
/* 1065 */       if (i > arrayOfInt[m]) {
/* 1066 */         i = arrayOfInt[m];
/*      */       }
/* 1068 */       m += n * 2;
/* 1069 */       if (j < arrayOfInt[m - 1]) {
/* 1070 */         j = arrayOfInt[m - 1];
/*      */       }
/*      */     } 
/*      */     
/* 1074 */     this.lox = i;
/* 1075 */     this.loy = arrayOfInt[0];
/* 1076 */     this.hix = j;
/* 1077 */     this.hiy = arrayOfInt[k + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getLoX() {
/* 1084 */     return this.lox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getLoY() {
/* 1091 */     return this.loy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getHiX() {
/* 1098 */     return this.hix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getHiY() {
/* 1105 */     return this.hiy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getWidth() {
/* 1112 */     if (this.hix < this.lox) return 0; 
/*      */     int i;
/* 1114 */     if ((i = this.hix - this.lox) < 0) {
/* 1115 */       i = Integer.MAX_VALUE;
/*      */     }
/* 1117 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getHeight() {
/* 1124 */     if (this.hiy < this.loy) return 0; 
/*      */     int i;
/* 1126 */     if ((i = this.hiy - this.loy) < 0) {
/* 1127 */       i = Integer.MAX_VALUE;
/*      */     }
/* 1129 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1136 */     return (this.hix <= this.lox || this.hiy <= this.loy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRectangular() {
/* 1144 */     return (this.bands == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(int paramInt1, int paramInt2) {
/* 1151 */     if (paramInt1 < this.lox || paramInt1 >= this.hix || paramInt2 < this.loy || paramInt2 >= this.hiy) return false; 
/* 1152 */     if (this.bands == null) return true; 
/* 1153 */     int i = 0;
/* 1154 */     while (i < this.endIndex) {
/* 1155 */       if (paramInt2 < this.bands[i++]) {
/* 1156 */         return false;
/*      */       }
/* 1158 */       if (paramInt2 >= this.bands[i++]) {
/* 1159 */         int k = this.bands[i++];
/* 1160 */         i += k * 2; continue;
/*      */       } 
/* 1162 */       int j = this.bands[i++];
/* 1163 */       j = i + j * 2;
/* 1164 */       while (i < j) {
/* 1165 */         if (paramInt1 < this.bands[i++]) return false; 
/* 1166 */         if (paramInt1 < this.bands[i++]) return true; 
/*      */       } 
/* 1168 */       return false;
/*      */     } 
/*      */     
/* 1171 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideXYWH(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1180 */     return isInsideXYXY(paramInt1, paramInt2, dimAdd(paramInt1, paramInt3), dimAdd(paramInt2, paramInt4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1188 */     return (this.lox >= paramInt1 && this.loy >= paramInt2 && this.hix <= paramInt3 && this.hiy <= paramInt4);
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
/*      */   public boolean isInsideQuickCheck(Region paramRegion) {
/* 1201 */     return (paramRegion.bands == null && paramRegion.lox <= this.lox && paramRegion.loy <= this.loy && paramRegion.hix >= this.hix && paramRegion.hiy >= this.hiy);
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
/*      */   public boolean intersectsQuickCheckXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1217 */     return (paramInt3 > this.lox && paramInt1 < this.hix && paramInt4 > this.loy && paramInt2 < this.hiy);
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
/*      */   public boolean intersectsQuickCheck(Region paramRegion) {
/* 1230 */     return (paramRegion.hix > this.lox && paramRegion.lox < this.hix && paramRegion.hiy > this.loy && paramRegion.loy < this.hiy);
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
/*      */   public boolean encompasses(Region paramRegion) {
/* 1242 */     return (this.bands == null && this.lox <= paramRegion.lox && this.loy <= paramRegion.loy && this.hix >= paramRegion.hix && this.hiy >= paramRegion.hiy);
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
/*      */   public boolean encompassesXYWH(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1255 */     return encompassesXYXY(paramInt1, paramInt2, dimAdd(paramInt1, paramInt3), dimAdd(paramInt2, paramInt4));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean encompassesXYXY(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 1266 */     return (this.bands == null && this.lox <= paramInt1 && this.loy <= paramInt2 && this.hix >= paramInt3 && this.hiy >= paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getBounds(int[] paramArrayOfint) {
/* 1275 */     paramArrayOfint[0] = this.lox;
/* 1276 */     paramArrayOfint[1] = this.loy;
/* 1277 */     paramArrayOfint[2] = this.hix;
/* 1278 */     paramArrayOfint[3] = this.hiy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clipBoxToBounds(int[] paramArrayOfint) {
/* 1285 */     if (paramArrayOfint[0] < this.lox) paramArrayOfint[0] = this.lox; 
/* 1286 */     if (paramArrayOfint[1] < this.loy) paramArrayOfint[1] = this.loy; 
/* 1287 */     if (paramArrayOfint[2] > this.hix) paramArrayOfint[2] = this.hix; 
/* 1288 */     if (paramArrayOfint[3] > this.hiy) paramArrayOfint[3] = this.hiy;
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public RegionIterator getIterator() {
/* 1295 */     return new RegionIterator(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpanIterator getSpanIterator() {
/* 1302 */     return new RegionSpanIterator(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpanIterator getSpanIterator(int[] paramArrayOfint) {
/* 1310 */     SpanIterator spanIterator = getSpanIterator();
/* 1311 */     spanIterator.intersectClipBox(paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[2], paramArrayOfint[3]);
/* 1312 */     return spanIterator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SpanIterator filter(SpanIterator paramSpanIterator) {
/* 1320 */     if (this.bands == null) {
/* 1321 */       paramSpanIterator.intersectClipBox(this.lox, this.loy, this.hix, this.hiy);
/*      */     } else {
/* 1323 */       paramSpanIterator = new RegionClipSpanIterator(this, paramSpanIterator);
/*      */     } 
/* 1325 */     return paramSpanIterator;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1329 */     StringBuffer stringBuffer = new StringBuffer();
/* 1330 */     stringBuffer.append("Region[[");
/* 1331 */     stringBuffer.append(this.lox);
/* 1332 */     stringBuffer.append(", ");
/* 1333 */     stringBuffer.append(this.loy);
/* 1334 */     stringBuffer.append(" => ");
/* 1335 */     stringBuffer.append(this.hix);
/* 1336 */     stringBuffer.append(", ");
/* 1337 */     stringBuffer.append(this.hiy);
/* 1338 */     stringBuffer.append("]");
/* 1339 */     if (this.bands != null) {
/* 1340 */       byte b = 0;
/* 1341 */       while (b < this.endIndex) {
/* 1342 */         stringBuffer.append("y{");
/* 1343 */         stringBuffer.append(this.bands[b++]);
/* 1344 */         stringBuffer.append(",");
/* 1345 */         stringBuffer.append(this.bands[b++]);
/* 1346 */         stringBuffer.append("}[");
/* 1347 */         int i = this.bands[b++];
/* 1348 */         i = b + i * 2;
/* 1349 */         while (b < i) {
/* 1350 */           stringBuffer.append("x(");
/* 1351 */           stringBuffer.append(this.bands[b++]);
/* 1352 */           stringBuffer.append(", ");
/* 1353 */           stringBuffer.append(this.bands[b++]);
/* 1354 */           stringBuffer.append(")");
/*      */         } 
/* 1356 */         stringBuffer.append("]");
/*      */       } 
/*      */     } 
/* 1359 */     stringBuffer.append("]");
/* 1360 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   public int hashCode() {
/* 1364 */     return isEmpty() ? 0 : (this.lox * 3 + this.loy * 5 + this.hix * 7 + this.hiy * 9);
/*      */   }
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 1368 */     if (!(paramObject instanceof Region)) {
/* 1369 */       return false;
/*      */     }
/* 1371 */     Region region = (Region)paramObject;
/* 1372 */     if (isEmpty())
/* 1373 */       return region.isEmpty(); 
/* 1374 */     if (region.isEmpty()) {
/* 1375 */       return false;
/*      */     }
/* 1377 */     if (region.lox != this.lox || region.loy != this.loy || region.hix != this.hix || region.hiy != this.hiy)
/*      */     {
/*      */       
/* 1380 */       return false;
/*      */     }
/* 1382 */     if (this.bands == null)
/* 1383 */       return (region.bands == null); 
/* 1384 */     if (region.bands == null) {
/* 1385 */       return false;
/*      */     }
/* 1387 */     if (this.endIndex != region.endIndex) {
/* 1388 */       return false;
/*      */     }
/* 1390 */     int[] arrayOfInt1 = this.bands;
/* 1391 */     int[] arrayOfInt2 = region.bands;
/* 1392 */     for (byte b = 0; b < this.endIndex; b++) {
/* 1393 */       if (arrayOfInt1[b] != arrayOfInt2[b]) {
/* 1394 */         return false;
/*      */       }
/*      */     } 
/* 1397 */     return true;
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/Region.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */