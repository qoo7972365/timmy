/*      */ package java.awt.geom;
/*      */ 
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
/*      */ import sun.awt.geom.Curve;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Path2D
/*      */   implements Shape, Cloneable
/*      */ {
/*      */   public static final int WIND_EVEN_ODD = 0;
/*      */   public static final int WIND_NON_ZERO = 1;
/*      */   private static final byte SEG_MOVETO = 0;
/*      */   private static final byte SEG_LINETO = 1;
/*      */   private static final byte SEG_QUADTO = 2;
/*      */   private static final byte SEG_CUBICTO = 3;
/*      */   private static final byte SEG_CLOSE = 4;
/*      */   transient byte[] pointTypes;
/*      */   transient int numTypes;
/*      */   transient int numCoords;
/*      */   transient int windingRule;
/*      */   static final int INIT_SIZE = 20;
/*      */   static final int EXPAND_MAX = 500;
/*      */   static final int EXPAND_MAX_COORDS = 1000;
/*      */   static final int EXPAND_MIN = 10;
/*      */   private static final byte SERIAL_STORAGE_FLT_ARRAY = 48;
/*      */   private static final byte SERIAL_STORAGE_DBL_ARRAY = 49;
/*      */   private static final byte SERIAL_SEG_FLT_MOVETO = 64;
/*      */   private static final byte SERIAL_SEG_FLT_LINETO = 65;
/*      */   private static final byte SERIAL_SEG_FLT_QUADTO = 66;
/*      */   private static final byte SERIAL_SEG_FLT_CUBICTO = 67;
/*      */   private static final byte SERIAL_SEG_DBL_MOVETO = 80;
/*      */   private static final byte SERIAL_SEG_DBL_LINETO = 81;
/*      */   private static final byte SERIAL_SEG_DBL_QUADTO = 82;
/*      */   private static final byte SERIAL_SEG_DBL_CUBICTO = 83;
/*      */   private static final byte SERIAL_SEG_CLOSE = 96;
/*      */   private static final byte SERIAL_PATH_END = 97;
/*      */   
/*      */   Path2D() {}
/*      */   
/*      */   Path2D(int paramInt1, int paramInt2) {
/*  133 */     setWindingRule(paramInt1);
/*  134 */     this.pointTypes = new byte[paramInt2];
/*      */   }
/*      */ 
/*      */   
/*      */   abstract float[] cloneCoordsFloat(AffineTransform paramAffineTransform);
/*      */ 
/*      */   
/*      */   abstract double[] cloneCoordsDouble(AffineTransform paramAffineTransform);
/*      */   
/*      */   abstract void append(float paramFloat1, float paramFloat2);
/*      */   
/*      */   abstract void append(double paramDouble1, double paramDouble2);
/*      */   
/*      */   static byte[] expandPointTypes(byte[] paramArrayOfbyte, int paramInt) {
/*  148 */     int i = paramArrayOfbyte.length;
/*  149 */     int j = i + paramInt;
/*  150 */     if (j < i)
/*      */     {
/*      */       
/*  153 */       throw new ArrayIndexOutOfBoundsException("pointTypes exceeds maximum capacity !");
/*      */     }
/*      */ 
/*      */     
/*  157 */     int k = i;
/*  158 */     if (k > 500) {
/*  159 */       k = Math.max(500, i >> 3);
/*  160 */     } else if (k < 10) {
/*  161 */       k = 10;
/*      */     } 
/*  163 */     assert k > 0;
/*      */     
/*  165 */     int m = i + k;
/*  166 */     if (m < j)
/*      */     {
/*  168 */       m = Integer.MAX_VALUE;
/*      */     }
/*      */     
/*      */     while (true) {
/*      */       try {
/*  173 */         return Arrays.copyOf(paramArrayOfbyte, m);
/*  174 */       } catch (OutOfMemoryError outOfMemoryError) {
/*  175 */         if (m == j) {
/*  176 */           throw outOfMemoryError;
/*      */         }
/*      */         
/*  179 */         m = j + (m - j) / 2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   abstract Point2D getPoint(int paramInt);
/*      */   abstract void needRoom(boolean paramBoolean, int paramInt);
/*      */   abstract int pointCrossings(double paramDouble1, double paramDouble2);
/*      */   abstract int rectCrossings(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   public abstract void moveTo(double paramDouble1, double paramDouble2);
/*      */   
/*      */   public abstract void lineTo(double paramDouble1, double paramDouble2);
/*      */   
/*      */   public abstract void quadTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   public abstract void curveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*      */   
/*      */   public static class Float extends Path2D implements Serializable { transient float[] floatCoords;
/*      */     private static final long serialVersionUID = 6990832515060788886L;
/*      */     
/*      */     public Float() {
/*  199 */       this(1, 20);
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
/*      */     public Float(int param1Int) {
/*  213 */       this(param1Int, 20);
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
/*      */     public Float(int param1Int1, int param1Int2) {
/*  232 */       super(param1Int1, param1Int2);
/*  233 */       this.floatCoords = new float[param1Int2 * 2];
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
/*      */     public Float(Shape param1Shape) {
/*  246 */       this(param1Shape, (AffineTransform)null);
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
/*      */     public Float(Shape param1Shape, AffineTransform param1AffineTransform) {
/*  262 */       if (param1Shape instanceof Path2D) {
/*  263 */         Path2D path2D = (Path2D)param1Shape;
/*  264 */         setWindingRule(path2D.windingRule);
/*  265 */         this.numTypes = path2D.numTypes;
/*      */         
/*  267 */         this.pointTypes = Arrays.copyOf(path2D.pointTypes, path2D.numTypes);
/*  268 */         this.numCoords = path2D.numCoords;
/*  269 */         this.floatCoords = path2D.cloneCoordsFloat(param1AffineTransform);
/*      */       } else {
/*  271 */         PathIterator pathIterator = param1Shape.getPathIterator(param1AffineTransform);
/*  272 */         setWindingRule(pathIterator.getWindingRule());
/*  273 */         this.pointTypes = new byte[20];
/*  274 */         this.floatCoords = new float[40];
/*  275 */         append(pathIterator, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     float[] cloneCoordsFloat(AffineTransform param1AffineTransform) {
/*      */       float[] arrayOfFloat;
/*  283 */       if (param1AffineTransform == null) {
/*  284 */         arrayOfFloat = Arrays.copyOf(this.floatCoords, this.numCoords);
/*      */       } else {
/*  286 */         arrayOfFloat = new float[this.numCoords];
/*  287 */         param1AffineTransform.transform(this.floatCoords, 0, arrayOfFloat, 0, this.numCoords / 2);
/*      */       } 
/*  289 */       return arrayOfFloat;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     double[] cloneCoordsDouble(AffineTransform param1AffineTransform) {
/*  295 */       double[] arrayOfDouble = new double[this.numCoords];
/*  296 */       if (param1AffineTransform == null) {
/*  297 */         for (byte b = 0; b < this.numCoords; b++) {
/*  298 */           arrayOfDouble[b] = this.floatCoords[b];
/*      */         }
/*      */       } else {
/*  301 */         param1AffineTransform.transform(this.floatCoords, 0, arrayOfDouble, 0, this.numCoords / 2);
/*      */       } 
/*  303 */       return arrayOfDouble;
/*      */     }
/*      */     
/*      */     void append(float param1Float1, float param1Float2) {
/*  307 */       this.floatCoords[this.numCoords++] = param1Float1;
/*  308 */       this.floatCoords[this.numCoords++] = param1Float2;
/*      */     }
/*      */     
/*      */     void append(double param1Double1, double param1Double2) {
/*  312 */       this.floatCoords[this.numCoords++] = (float)param1Double1;
/*  313 */       this.floatCoords[this.numCoords++] = (float)param1Double2;
/*      */     }
/*      */     
/*      */     Point2D getPoint(int param1Int) {
/*  317 */       return new Point2D.Float(this.floatCoords[param1Int], this.floatCoords[param1Int + 1]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void needRoom(boolean param1Boolean, int param1Int) {
/*  323 */       if (this.numTypes == 0 && param1Boolean) {
/*  324 */         throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */       }
/*      */       
/*  327 */       if (this.numTypes >= this.pointTypes.length) {
/*  328 */         this.pointTypes = expandPointTypes(this.pointTypes, 1);
/*      */       }
/*  330 */       if (this.numCoords > this.floatCoords.length - param1Int) {
/*  331 */         this.floatCoords = expandCoords(this.floatCoords, param1Int);
/*      */       }
/*      */     }
/*      */     
/*      */     static float[] expandCoords(float[] param1ArrayOffloat, int param1Int) {
/*  336 */       int i = param1ArrayOffloat.length;
/*  337 */       int j = i + param1Int;
/*  338 */       if (j < i)
/*      */       {
/*      */         
/*  341 */         throw new ArrayIndexOutOfBoundsException("coords exceeds maximum capacity !");
/*      */       }
/*      */ 
/*      */       
/*  345 */       int k = i;
/*  346 */       if (k > 1000) {
/*  347 */         k = Math.max(1000, i >> 3);
/*  348 */       } else if (k < 10) {
/*  349 */         k = 10;
/*      */       } 
/*  351 */       assert k > param1Int;
/*      */       
/*  353 */       int m = i + k;
/*  354 */       if (m < j)
/*      */       {
/*  356 */         m = Integer.MAX_VALUE;
/*      */       }
/*      */       
/*      */       while (true) {
/*      */         try {
/*  361 */           return Arrays.copyOf(param1ArrayOffloat, m);
/*  362 */         } catch (OutOfMemoryError outOfMemoryError) {
/*  363 */           if (m == j) {
/*  364 */             throw outOfMemoryError;
/*      */           }
/*      */           
/*  367 */           m = j + (m - j) / 2;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void moveTo(double param1Double1, double param1Double2) {
/*  376 */       if (this.numTypes > 0 && this.pointTypes[this.numTypes - 1] == 0) {
/*  377 */         this.floatCoords[this.numCoords - 2] = (float)param1Double1;
/*  378 */         this.floatCoords[this.numCoords - 1] = (float)param1Double2;
/*      */       } else {
/*  380 */         needRoom(false, 2);
/*  381 */         this.pointTypes[this.numTypes++] = 0;
/*  382 */         this.floatCoords[this.numCoords++] = (float)param1Double1;
/*  383 */         this.floatCoords[this.numCoords++] = (float)param1Double2;
/*      */       } 
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
/*      */     public final synchronized void moveTo(float param1Float1, float param1Float2) {
/*  401 */       if (this.numTypes > 0 && this.pointTypes[this.numTypes - 1] == 0) {
/*  402 */         this.floatCoords[this.numCoords - 2] = param1Float1;
/*  403 */         this.floatCoords[this.numCoords - 1] = param1Float2;
/*      */       } else {
/*  405 */         needRoom(false, 2);
/*  406 */         this.pointTypes[this.numTypes++] = 0;
/*  407 */         this.floatCoords[this.numCoords++] = param1Float1;
/*  408 */         this.floatCoords[this.numCoords++] = param1Float2;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void lineTo(double param1Double1, double param1Double2) {
/*  417 */       needRoom(true, 2);
/*  418 */       this.pointTypes[this.numTypes++] = 1;
/*  419 */       this.floatCoords[this.numCoords++] = (float)param1Double1;
/*  420 */       this.floatCoords[this.numCoords++] = (float)param1Double2;
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
/*      */     public final synchronized void lineTo(float param1Float1, float param1Float2) {
/*  438 */       needRoom(true, 2);
/*  439 */       this.pointTypes[this.numTypes++] = 1;
/*  440 */       this.floatCoords[this.numCoords++] = param1Float1;
/*  441 */       this.floatCoords[this.numCoords++] = param1Float2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void quadTo(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  451 */       needRoom(true, 4);
/*  452 */       this.pointTypes[this.numTypes++] = 2;
/*  453 */       this.floatCoords[this.numCoords++] = (float)param1Double1;
/*  454 */       this.floatCoords[this.numCoords++] = (float)param1Double2;
/*  455 */       this.floatCoords[this.numCoords++] = (float)param1Double3;
/*  456 */       this.floatCoords[this.numCoords++] = (float)param1Double4;
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
/*      */     public final synchronized void quadTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/*  481 */       needRoom(true, 4);
/*  482 */       this.pointTypes[this.numTypes++] = 2;
/*  483 */       this.floatCoords[this.numCoords++] = param1Float1;
/*  484 */       this.floatCoords[this.numCoords++] = param1Float2;
/*  485 */       this.floatCoords[this.numCoords++] = param1Float3;
/*  486 */       this.floatCoords[this.numCoords++] = param1Float4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void curveTo(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/*  497 */       needRoom(true, 6);
/*  498 */       this.pointTypes[this.numTypes++] = 3;
/*  499 */       this.floatCoords[this.numCoords++] = (float)param1Double1;
/*  500 */       this.floatCoords[this.numCoords++] = (float)param1Double2;
/*  501 */       this.floatCoords[this.numCoords++] = (float)param1Double3;
/*  502 */       this.floatCoords[this.numCoords++] = (float)param1Double4;
/*  503 */       this.floatCoords[this.numCoords++] = (float)param1Double5;
/*  504 */       this.floatCoords[this.numCoords++] = (float)param1Double6;
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
/*      */     public final synchronized void curveTo(float param1Float1, float param1Float2, float param1Float3, float param1Float4, float param1Float5, float param1Float6) {
/*  532 */       needRoom(true, 6);
/*  533 */       this.pointTypes[this.numTypes++] = 3;
/*  534 */       this.floatCoords[this.numCoords++] = param1Float1;
/*  535 */       this.floatCoords[this.numCoords++] = param1Float2;
/*  536 */       this.floatCoords[this.numCoords++] = param1Float3;
/*  537 */       this.floatCoords[this.numCoords++] = param1Float4;
/*  538 */       this.floatCoords[this.numCoords++] = param1Float5;
/*  539 */       this.floatCoords[this.numCoords++] = param1Float6;
/*      */     }
/*      */     
/*      */     int pointCrossings(double param1Double1, double param1Double2) {
/*  543 */       if (this.numTypes == 0) {
/*  544 */         return 0;
/*      */       }
/*      */       
/*  547 */       float[] arrayOfFloat = this.floatCoords;
/*  548 */       double d1 = arrayOfFloat[0], d3 = d1;
/*  549 */       double d2 = arrayOfFloat[1], d4 = d2;
/*  550 */       int i = 0;
/*  551 */       byte b1 = 2;
/*  552 */       for (byte b2 = 1; b2 < this.numTypes; b2++) {
/*  553 */         double d5; double d6; switch (this.pointTypes[b2]) {
/*      */           case 0:
/*  555 */             if (d4 != d2) {
/*  556 */               i += 
/*  557 */                 Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d1, d2);
/*      */             }
/*      */ 
/*      */             
/*  561 */             d1 = d3 = arrayOfFloat[b1++];
/*  562 */             d2 = d4 = arrayOfFloat[b1++];
/*      */           
/*      */           case 1:
/*  565 */             i += 
/*  566 */               Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d5 = arrayOfFloat[b1++], d6 = arrayOfFloat[b1++]);
/*      */ 
/*      */ 
/*      */             
/*  570 */             d3 = d5;
/*  571 */             d4 = d6;
/*      */           
/*      */           case 2:
/*  574 */             i += 
/*  575 */               Curve.pointCrossingsForQuad(param1Double1, param1Double2, d3, d4, arrayOfFloat[b1++], arrayOfFloat[b1++], d5 = arrayOfFloat[b1++], d6 = arrayOfFloat[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  582 */             d3 = d5;
/*  583 */             d4 = d6;
/*      */           
/*      */           case 3:
/*  586 */             i += 
/*  587 */               Curve.pointCrossingsForCubic(param1Double1, param1Double2, d3, d4, arrayOfFloat[b1++], arrayOfFloat[b1++], arrayOfFloat[b1++], arrayOfFloat[b1++], d5 = arrayOfFloat[b1++], d6 = arrayOfFloat[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  596 */             d3 = d5;
/*  597 */             d4 = d6;
/*      */           
/*      */           case 4:
/*  600 */             if (d4 != d2) {
/*  601 */               i += 
/*  602 */                 Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d1, d2);
/*      */             }
/*      */ 
/*      */             
/*  606 */             d3 = d1;
/*  607 */             d4 = d2;
/*      */             break;
/*      */         } 
/*      */       } 
/*  611 */       if (d4 != d2) {
/*  612 */         i += 
/*  613 */           Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d1, d2);
/*      */       }
/*      */ 
/*      */       
/*  617 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int rectCrossings(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/*  623 */       if (this.numTypes == 0) {
/*  624 */         return 0;
/*      */       }
/*  626 */       float[] arrayOfFloat = this.floatCoords;
/*      */       
/*  628 */       double d3 = arrayOfFloat[0], d1 = d3;
/*  629 */       double d4 = arrayOfFloat[1], d2 = d4;
/*  630 */       int i = 0;
/*  631 */       byte b1 = 2;
/*  632 */       byte b2 = 1;
/*  633 */       for (; i != Integer.MIN_VALUE && b2 < this.numTypes; 
/*  634 */         b2++) {
/*      */         double d5; double d6;
/*  636 */         switch (this.pointTypes[b2]) {
/*      */           case 0:
/*  638 */             if (d1 != d3 || d2 != d4)
/*      */             {
/*  640 */               i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d3, d4);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  648 */             d3 = d1 = arrayOfFloat[b1++];
/*  649 */             d4 = d2 = arrayOfFloat[b1++];
/*      */ 
/*      */           
/*      */           case 1:
/*  653 */             i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d5 = arrayOfFloat[b1++], d6 = arrayOfFloat[b1++]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  659 */             d1 = d5;
/*  660 */             d2 = d6;
/*      */ 
/*      */           
/*      */           case 2:
/*  664 */             i = Curve.rectCrossingsForQuad(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, arrayOfFloat[b1++], arrayOfFloat[b1++], d5 = arrayOfFloat[b1++], d6 = arrayOfFloat[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  673 */             d1 = d5;
/*  674 */             d2 = d6;
/*      */ 
/*      */           
/*      */           case 3:
/*  678 */             i = Curve.rectCrossingsForCubic(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, arrayOfFloat[b1++], arrayOfFloat[b1++], arrayOfFloat[b1++], arrayOfFloat[b1++], d5 = arrayOfFloat[b1++], d6 = arrayOfFloat[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  689 */             d1 = d5;
/*  690 */             d2 = d6;
/*      */           
/*      */           case 4:
/*  693 */             if (d1 != d3 || d2 != d4)
/*      */             {
/*  695 */               i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d3, d4);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  701 */             d1 = d3;
/*  702 */             d2 = d4;
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*  708 */       if (i != Integer.MIN_VALUE && (d1 != d3 || d2 != d4))
/*      */       {
/*      */ 
/*      */         
/*  712 */         i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d3, d4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  720 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void append(PathIterator param1PathIterator, boolean param1Boolean) {
/*  728 */       float[] arrayOfFloat = new float[6];
/*  729 */       while (!param1PathIterator.isDone()) {
/*  730 */         switch (param1PathIterator.currentSegment(arrayOfFloat)) {
/*      */           case 0:
/*  732 */             if (!param1Boolean || this.numTypes < 1 || this.numCoords < 1) {
/*  733 */               moveTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */               break;
/*      */             } 
/*  736 */             if (this.pointTypes[this.numTypes - 1] != 4 && this.floatCoords[this.numCoords - 2] == arrayOfFloat[0] && this.floatCoords[this.numCoords - 1] == arrayOfFloat[1]) {
/*      */               break;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  743 */             lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */             break;
/*      */           case 1:
/*  746 */             lineTo(arrayOfFloat[0], arrayOfFloat[1]);
/*      */             break;
/*      */           case 2:
/*  749 */             quadTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*      */             break;
/*      */           
/*      */           case 3:
/*  753 */             curveTo(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 4:
/*  758 */             closePath();
/*      */             break;
/*      */         } 
/*  761 */         param1PathIterator.next();
/*  762 */         param1Boolean = false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void transform(AffineTransform param1AffineTransform) {
/*  771 */       param1AffineTransform.transform(this.floatCoords, 0, this.floatCoords, 0, this.numCoords / 2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized Rectangle2D getBounds2D() {
/*      */       float f1, f2, f3, f4;
/*  780 */       int i = this.numCoords;
/*  781 */       if (i > 0) {
/*  782 */         f2 = f4 = this.floatCoords[--i];
/*  783 */         f1 = f3 = this.floatCoords[--i];
/*  784 */         while (i > 0) {
/*  785 */           float f5 = this.floatCoords[--i];
/*  786 */           float f6 = this.floatCoords[--i];
/*  787 */           if (f6 < f1) f1 = f6; 
/*  788 */           if (f5 < f2) f2 = f5; 
/*  789 */           if (f6 > f3) f3 = f6; 
/*  790 */           if (f5 > f4) f4 = f5; 
/*      */         } 
/*      */       } else {
/*  793 */         f1 = f2 = f3 = f4 = 0.0F;
/*      */       } 
/*  795 */       return new Rectangle2D.Float(f1, f2, f3 - f1, f4 - f2);
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
/*      */     public final PathIterator getPathIterator(AffineTransform param1AffineTransform) {
/*  810 */       if (param1AffineTransform == null) {
/*  811 */         return new CopyIterator(this);
/*      */       }
/*  813 */       return new TxIterator(this, param1AffineTransform);
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
/*      */     public final Object clone() {
/*  831 */       if (this instanceof GeneralPath) {
/*  832 */         return new GeneralPath(this);
/*      */       }
/*  834 */       return new Float(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/*  967 */       writeObject(param1ObjectOutputStream, false);
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
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/*  986 */       readObject(param1ObjectInputStream, false);
/*      */     }
/*      */     
/*      */     static class CopyIterator extends Path2D.Iterator {
/*      */       float[] floatCoords;
/*      */       
/*      */       CopyIterator(Path2D.Float param2Float) {
/*  993 */         super(param2Float);
/*  994 */         this.floatCoords = param2Float.floatCoords;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] param2ArrayOffloat) {
/*  998 */         byte b = this.path.pointTypes[this.typeIdx];
/*  999 */         int i = curvecoords[b];
/* 1000 */         if (i > 0) {
/* 1001 */           System.arraycopy(this.floatCoords, this.pointIdx, param2ArrayOffloat, 0, i);
/*      */         }
/*      */         
/* 1004 */         return b;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] param2ArrayOfdouble) {
/* 1008 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1009 */         int i = curvecoords[b];
/* 1010 */         if (i > 0) {
/* 1011 */           for (byte b1 = 0; b1 < i; b1++) {
/* 1012 */             param2ArrayOfdouble[b1] = this.floatCoords[this.pointIdx + b1];
/*      */           }
/*      */         }
/* 1015 */         return b;
/*      */       }
/*      */     }
/*      */     
/*      */     static class TxIterator extends Path2D.Iterator {
/*      */       float[] floatCoords;
/*      */       AffineTransform affine;
/*      */       
/*      */       TxIterator(Path2D.Float param2Float, AffineTransform param2AffineTransform) {
/* 1024 */         super(param2Float);
/* 1025 */         this.floatCoords = param2Float.floatCoords;
/* 1026 */         this.affine = param2AffineTransform;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] param2ArrayOffloat) {
/* 1030 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1031 */         int i = curvecoords[b];
/* 1032 */         if (i > 0) {
/* 1033 */           this.affine.transform(this.floatCoords, this.pointIdx, param2ArrayOffloat, 0, i / 2);
/*      */         }
/*      */         
/* 1036 */         return b;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] param2ArrayOfdouble) {
/* 1040 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1041 */         int i = curvecoords[b];
/* 1042 */         if (i > 0) {
/* 1043 */           this.affine.transform(this.floatCoords, this.pointIdx, param2ArrayOfdouble, 0, i / 2);
/*      */         }
/*      */         
/* 1046 */         return b;
/*      */       }
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Double
/*      */     extends Path2D
/*      */     implements Serializable
/*      */   {
/*      */     transient double[] doubleCoords;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final long serialVersionUID = 1826762518450014216L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Double() {
/* 1068 */       this(1, 20);
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
/*      */     public Double(int param1Int) {
/* 1082 */       this(param1Int, 20);
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
/*      */     public Double(int param1Int1, int param1Int2) {
/* 1101 */       super(param1Int1, param1Int2);
/* 1102 */       this.doubleCoords = new double[param1Int2 * 2];
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
/*      */     public Double(Shape param1Shape) {
/* 1115 */       this(param1Shape, (AffineTransform)null);
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
/*      */     public Double(Shape param1Shape, AffineTransform param1AffineTransform) {
/* 1131 */       if (param1Shape instanceof Path2D) {
/* 1132 */         Path2D path2D = (Path2D)param1Shape;
/* 1133 */         setWindingRule(path2D.windingRule);
/* 1134 */         this.numTypes = path2D.numTypes;
/*      */         
/* 1136 */         this.pointTypes = Arrays.copyOf(path2D.pointTypes, path2D.numTypes);
/* 1137 */         this.numCoords = path2D.numCoords;
/* 1138 */         this.doubleCoords = path2D.cloneCoordsDouble(param1AffineTransform);
/*      */       } else {
/* 1140 */         PathIterator pathIterator = param1Shape.getPathIterator(param1AffineTransform);
/* 1141 */         setWindingRule(pathIterator.getWindingRule());
/* 1142 */         this.pointTypes = new byte[20];
/* 1143 */         this.doubleCoords = new double[40];
/* 1144 */         append(pathIterator, false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     float[] cloneCoordsFloat(AffineTransform param1AffineTransform) {
/* 1151 */       float[] arrayOfFloat = new float[this.numCoords];
/* 1152 */       if (param1AffineTransform == null) {
/* 1153 */         for (byte b = 0; b < this.numCoords; b++) {
/* 1154 */           arrayOfFloat[b] = (float)this.doubleCoords[b];
/*      */         }
/*      */       } else {
/* 1157 */         param1AffineTransform.transform(this.doubleCoords, 0, arrayOfFloat, 0, this.numCoords / 2);
/*      */       } 
/* 1159 */       return arrayOfFloat;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     double[] cloneCoordsDouble(AffineTransform param1AffineTransform) {
/*      */       double[] arrayOfDouble;
/* 1166 */       if (param1AffineTransform == null) {
/* 1167 */         arrayOfDouble = Arrays.copyOf(this.doubleCoords, this.numCoords);
/*      */       } else {
/* 1169 */         arrayOfDouble = new double[this.numCoords];
/* 1170 */         param1AffineTransform.transform(this.doubleCoords, 0, arrayOfDouble, 0, this.numCoords / 2);
/*      */       } 
/* 1172 */       return arrayOfDouble;
/*      */     }
/*      */     
/*      */     void append(float param1Float1, float param1Float2) {
/* 1176 */       this.doubleCoords[this.numCoords++] = param1Float1;
/* 1177 */       this.doubleCoords[this.numCoords++] = param1Float2;
/*      */     }
/*      */     
/*      */     void append(double param1Double1, double param1Double2) {
/* 1181 */       this.doubleCoords[this.numCoords++] = param1Double1;
/* 1182 */       this.doubleCoords[this.numCoords++] = param1Double2;
/*      */     }
/*      */     
/*      */     Point2D getPoint(int param1Int) {
/* 1186 */       return new Point2D.Double(this.doubleCoords[param1Int], this.doubleCoords[param1Int + 1]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void needRoom(boolean param1Boolean, int param1Int) {
/* 1192 */       if (this.numTypes == 0 && param1Boolean) {
/* 1193 */         throw new IllegalPathStateException("missing initial moveto in path definition");
/*      */       }
/*      */       
/* 1196 */       if (this.numTypes >= this.pointTypes.length) {
/* 1197 */         this.pointTypes = expandPointTypes(this.pointTypes, 1);
/*      */       }
/* 1199 */       if (this.numCoords > this.doubleCoords.length - param1Int) {
/* 1200 */         this.doubleCoords = expandCoords(this.doubleCoords, param1Int);
/*      */       }
/*      */     }
/*      */     
/*      */     static double[] expandCoords(double[] param1ArrayOfdouble, int param1Int) {
/* 1205 */       int i = param1ArrayOfdouble.length;
/* 1206 */       int j = i + param1Int;
/* 1207 */       if (j < i)
/*      */       {
/*      */         
/* 1210 */         throw new ArrayIndexOutOfBoundsException("coords exceeds maximum capacity !");
/*      */       }
/*      */ 
/*      */       
/* 1214 */       int k = i;
/* 1215 */       if (k > 1000) {
/* 1216 */         k = Math.max(1000, i >> 3);
/* 1217 */       } else if (k < 10) {
/* 1218 */         k = 10;
/*      */       } 
/* 1220 */       assert k > param1Int;
/*      */       
/* 1222 */       int m = i + k;
/* 1223 */       if (m < j)
/*      */       {
/* 1225 */         m = Integer.MAX_VALUE;
/*      */       }
/*      */       
/*      */       while (true) {
/*      */         try {
/* 1230 */           return Arrays.copyOf(param1ArrayOfdouble, m);
/* 1231 */         } catch (OutOfMemoryError outOfMemoryError) {
/* 1232 */           if (m == j) {
/* 1233 */             throw outOfMemoryError;
/*      */           }
/*      */           
/* 1236 */           m = j + (m - j) / 2;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void moveTo(double param1Double1, double param1Double2) {
/* 1245 */       if (this.numTypes > 0 && this.pointTypes[this.numTypes - 1] == 0) {
/* 1246 */         this.doubleCoords[this.numCoords - 2] = param1Double1;
/* 1247 */         this.doubleCoords[this.numCoords - 1] = param1Double2;
/*      */       } else {
/* 1249 */         needRoom(false, 2);
/* 1250 */         this.pointTypes[this.numTypes++] = 0;
/* 1251 */         this.doubleCoords[this.numCoords++] = param1Double1;
/* 1252 */         this.doubleCoords[this.numCoords++] = param1Double2;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void lineTo(double param1Double1, double param1Double2) {
/* 1261 */       needRoom(true, 2);
/* 1262 */       this.pointTypes[this.numTypes++] = 1;
/* 1263 */       this.doubleCoords[this.numCoords++] = param1Double1;
/* 1264 */       this.doubleCoords[this.numCoords++] = param1Double2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void quadTo(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 1274 */       needRoom(true, 4);
/* 1275 */       this.pointTypes[this.numTypes++] = 2;
/* 1276 */       this.doubleCoords[this.numCoords++] = param1Double1;
/* 1277 */       this.doubleCoords[this.numCoords++] = param1Double2;
/* 1278 */       this.doubleCoords[this.numCoords++] = param1Double3;
/* 1279 */       this.doubleCoords[this.numCoords++] = param1Double4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized void curveTo(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/* 1290 */       needRoom(true, 6);
/* 1291 */       this.pointTypes[this.numTypes++] = 3;
/* 1292 */       this.doubleCoords[this.numCoords++] = param1Double1;
/* 1293 */       this.doubleCoords[this.numCoords++] = param1Double2;
/* 1294 */       this.doubleCoords[this.numCoords++] = param1Double3;
/* 1295 */       this.doubleCoords[this.numCoords++] = param1Double4;
/* 1296 */       this.doubleCoords[this.numCoords++] = param1Double5;
/* 1297 */       this.doubleCoords[this.numCoords++] = param1Double6;
/*      */     }
/*      */     
/*      */     int pointCrossings(double param1Double1, double param1Double2) {
/* 1301 */       if (this.numTypes == 0) {
/* 1302 */         return 0;
/*      */       }
/*      */       
/* 1305 */       double[] arrayOfDouble = this.doubleCoords;
/* 1306 */       double d1 = arrayOfDouble[0], d3 = d1;
/* 1307 */       double d2 = arrayOfDouble[1], d4 = d2;
/* 1308 */       int i = 0;
/* 1309 */       byte b1 = 2;
/* 1310 */       for (byte b2 = 1; b2 < this.numTypes; b2++) {
/* 1311 */         double d5; double d6; switch (this.pointTypes[b2]) {
/*      */           case 0:
/* 1313 */             if (d4 != d2) {
/* 1314 */               i += 
/* 1315 */                 Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d1, d2);
/*      */             }
/*      */ 
/*      */             
/* 1319 */             d1 = d3 = arrayOfDouble[b1++];
/* 1320 */             d2 = d4 = arrayOfDouble[b1++];
/*      */           
/*      */           case 1:
/* 1323 */             i += 
/* 1324 */               Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d5 = arrayOfDouble[b1++], d6 = arrayOfDouble[b1++]);
/*      */ 
/*      */ 
/*      */             
/* 1328 */             d3 = d5;
/* 1329 */             d4 = d6;
/*      */           
/*      */           case 2:
/* 1332 */             i += 
/* 1333 */               Curve.pointCrossingsForQuad(param1Double1, param1Double2, d3, d4, arrayOfDouble[b1++], arrayOfDouble[b1++], d5 = arrayOfDouble[b1++], d6 = arrayOfDouble[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1340 */             d3 = d5;
/* 1341 */             d4 = d6;
/*      */           
/*      */           case 3:
/* 1344 */             i += 
/* 1345 */               Curve.pointCrossingsForCubic(param1Double1, param1Double2, d3, d4, arrayOfDouble[b1++], arrayOfDouble[b1++], arrayOfDouble[b1++], arrayOfDouble[b1++], d5 = arrayOfDouble[b1++], d6 = arrayOfDouble[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1354 */             d3 = d5;
/* 1355 */             d4 = d6;
/*      */           
/*      */           case 4:
/* 1358 */             if (d4 != d2) {
/* 1359 */               i += 
/* 1360 */                 Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d1, d2);
/*      */             }
/*      */ 
/*      */             
/* 1364 */             d3 = d1;
/* 1365 */             d4 = d2;
/*      */             break;
/*      */         } 
/*      */       } 
/* 1369 */       if (d4 != d2) {
/* 1370 */         i += 
/* 1371 */           Curve.pointCrossingsForLine(param1Double1, param1Double2, d3, d4, d1, d2);
/*      */       }
/*      */ 
/*      */       
/* 1375 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int rectCrossings(double param1Double1, double param1Double2, double param1Double3, double param1Double4) {
/* 1381 */       if (this.numTypes == 0) {
/* 1382 */         return 0;
/*      */       }
/* 1384 */       double[] arrayOfDouble = this.doubleCoords;
/*      */       
/* 1386 */       double d3 = arrayOfDouble[0], d1 = d3;
/* 1387 */       double d4 = arrayOfDouble[1], d2 = d4;
/* 1388 */       int i = 0;
/* 1389 */       byte b1 = 2;
/* 1390 */       byte b2 = 1;
/* 1391 */       for (; i != Integer.MIN_VALUE && b2 < this.numTypes; 
/* 1392 */         b2++) {
/*      */         double d5; double d6;
/* 1394 */         switch (this.pointTypes[b2]) {
/*      */           case 0:
/* 1396 */             if (d1 != d3 || d2 != d4)
/*      */             {
/* 1398 */               i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d3, d4);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1406 */             d3 = d1 = arrayOfDouble[b1++];
/* 1407 */             d4 = d2 = arrayOfDouble[b1++];
/*      */           
/*      */           case 1:
/* 1410 */             d5 = arrayOfDouble[b1++];
/* 1411 */             d6 = arrayOfDouble[b1++];
/*      */             
/* 1413 */             i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d5, d6);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1418 */             d1 = d5;
/* 1419 */             d2 = d6;
/*      */             break;
/*      */           
/*      */           case 2:
/* 1423 */             i = Curve.rectCrossingsForQuad(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, arrayOfDouble[b1++], arrayOfDouble[b1++], d5 = arrayOfDouble[b1++], d6 = arrayOfDouble[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1432 */             d1 = d5;
/* 1433 */             d2 = d6;
/*      */ 
/*      */           
/*      */           case 3:
/* 1437 */             i = Curve.rectCrossingsForCubic(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, arrayOfDouble[b1++], arrayOfDouble[b1++], arrayOfDouble[b1++], arrayOfDouble[b1++], d5 = arrayOfDouble[b1++], d6 = arrayOfDouble[b1++], 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1448 */             d1 = d5;
/* 1449 */             d2 = d6;
/*      */           
/*      */           case 4:
/* 1452 */             if (d1 != d3 || d2 != d4)
/*      */             {
/* 1454 */               i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d3, d4);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1460 */             d1 = d3;
/* 1461 */             d2 = d4;
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/* 1467 */       if (i != Integer.MIN_VALUE && (d1 != d3 || d2 != d4))
/*      */       {
/*      */ 
/*      */         
/* 1471 */         i = Curve.rectCrossingsForLine(i, param1Double1, param1Double2, param1Double3, param1Double4, d1, d2, d3, d4);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1479 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void append(PathIterator param1PathIterator, boolean param1Boolean) {
/* 1487 */       double[] arrayOfDouble = new double[6];
/* 1488 */       while (!param1PathIterator.isDone()) {
/* 1489 */         switch (param1PathIterator.currentSegment(arrayOfDouble)) {
/*      */           case 0:
/* 1491 */             if (!param1Boolean || this.numTypes < 1 || this.numCoords < 1) {
/* 1492 */               moveTo(arrayOfDouble[0], arrayOfDouble[1]);
/*      */               break;
/*      */             } 
/* 1495 */             if (this.pointTypes[this.numTypes - 1] != 4 && this.doubleCoords[this.numCoords - 2] == arrayOfDouble[0] && this.doubleCoords[this.numCoords - 1] == arrayOfDouble[1]) {
/*      */               break;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1502 */             lineTo(arrayOfDouble[0], arrayOfDouble[1]);
/*      */             break;
/*      */           case 1:
/* 1505 */             lineTo(arrayOfDouble[0], arrayOfDouble[1]);
/*      */             break;
/*      */           case 2:
/* 1508 */             quadTo(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3]);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1512 */             curveTo(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3], arrayOfDouble[4], arrayOfDouble[5]);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 4:
/* 1517 */             closePath();
/*      */             break;
/*      */         } 
/* 1520 */         param1PathIterator.next();
/* 1521 */         param1Boolean = false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void transform(AffineTransform param1AffineTransform) {
/* 1530 */       param1AffineTransform.transform(this.doubleCoords, 0, this.doubleCoords, 0, this.numCoords / 2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final synchronized Rectangle2D getBounds2D() {
/*      */       double d1, d2, d3, d4;
/* 1539 */       int i = this.numCoords;
/* 1540 */       if (i > 0) {
/* 1541 */         d2 = d4 = this.doubleCoords[--i];
/* 1542 */         d1 = d3 = this.doubleCoords[--i];
/* 1543 */         while (i > 0) {
/* 1544 */           double d5 = this.doubleCoords[--i];
/* 1545 */           double d6 = this.doubleCoords[--i];
/* 1546 */           if (d6 < d1) d1 = d6; 
/* 1547 */           if (d5 < d2) d2 = d5; 
/* 1548 */           if (d6 > d3) d3 = d6; 
/* 1549 */           if (d5 > d4) d4 = d5; 
/*      */         } 
/*      */       } else {
/* 1552 */         d1 = d2 = d3 = d4 = 0.0D;
/*      */       } 
/* 1554 */       return new Rectangle2D.Double(d1, d2, d3 - d1, d4 - d2);
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
/*      */     public final PathIterator getPathIterator(AffineTransform param1AffineTransform) {
/* 1573 */       if (param1AffineTransform == null) {
/* 1574 */         return new CopyIterator(this);
/*      */       }
/* 1576 */       return new TxIterator(this, param1AffineTransform);
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
/*      */     public final Object clone() {
/* 1594 */       return new Double(this);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 1726 */       writeObject(param1ObjectOutputStream, true);
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
/*      */     private void readObject(ObjectInputStream param1ObjectInputStream) throws ClassNotFoundException, IOException {
/* 1745 */       readObject(param1ObjectInputStream, true);
/*      */     }
/*      */     
/*      */     static class CopyIterator extends Path2D.Iterator {
/*      */       double[] doubleCoords;
/*      */       
/*      */       CopyIterator(Path2D.Double param2Double) {
/* 1752 */         super(param2Double);
/* 1753 */         this.doubleCoords = param2Double.doubleCoords;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] param2ArrayOffloat) {
/* 1757 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1758 */         int i = curvecoords[b];
/* 1759 */         if (i > 0) {
/* 1760 */           for (byte b1 = 0; b1 < i; b1++) {
/* 1761 */             param2ArrayOffloat[b1] = (float)this.doubleCoords[this.pointIdx + b1];
/*      */           }
/*      */         }
/* 1764 */         return b;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] param2ArrayOfdouble) {
/* 1768 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1769 */         int i = curvecoords[b];
/* 1770 */         if (i > 0) {
/* 1771 */           System.arraycopy(this.doubleCoords, this.pointIdx, param2ArrayOfdouble, 0, i);
/*      */         }
/*      */         
/* 1774 */         return b;
/*      */       }
/*      */     }
/*      */     
/*      */     static class TxIterator extends Path2D.Iterator {
/*      */       double[] doubleCoords;
/*      */       AffineTransform affine;
/*      */       
/*      */       TxIterator(Path2D.Double param2Double, AffineTransform param2AffineTransform) {
/* 1783 */         super(param2Double);
/* 1784 */         this.doubleCoords = param2Double.doubleCoords;
/* 1785 */         this.affine = param2AffineTransform;
/*      */       }
/*      */       
/*      */       public int currentSegment(float[] param2ArrayOffloat) {
/* 1789 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1790 */         int i = curvecoords[b];
/* 1791 */         if (i > 0) {
/* 1792 */           this.affine.transform(this.doubleCoords, this.pointIdx, param2ArrayOffloat, 0, i / 2);
/*      */         }
/*      */         
/* 1795 */         return b;
/*      */       }
/*      */       
/*      */       public int currentSegment(double[] param2ArrayOfdouble) {
/* 1799 */         byte b = this.path.pointTypes[this.typeIdx];
/* 1800 */         int i = curvecoords[b];
/* 1801 */         if (i > 0) {
/* 1802 */           this.affine.transform(this.doubleCoords, this.pointIdx, param2ArrayOfdouble, 0, i / 2);
/*      */         }
/*      */         
/* 1805 */         return b;
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void closePath() {
/* 1876 */     if (this.numTypes == 0 || this.pointTypes[this.numTypes - 1] != 4) {
/* 1877 */       needRoom(true, 0);
/* 1878 */       this.pointTypes[this.numTypes++] = 4;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void append(Shape paramShape, boolean paramBoolean) {
/* 1905 */     append(paramShape.getPathIterator(null), paramBoolean);
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
/*      */   public abstract void append(PathIterator paramPathIterator, boolean paramBoolean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized int getWindingRule() {
/* 1943 */     return this.windingRule;
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
/*      */   public final void setWindingRule(int paramInt) {
/* 1959 */     if (paramInt != 0 && paramInt != 1) {
/* 1960 */       throw new IllegalArgumentException("winding rule must be WIND_EVEN_ODD or WIND_NON_ZERO");
/*      */     }
/*      */ 
/*      */     
/* 1964 */     this.windingRule = paramInt;
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
/*      */   public final synchronized Point2D getCurrentPoint() {
/* 1976 */     int i = this.numCoords;
/* 1977 */     if (this.numTypes < 1 || i < 1) {
/* 1978 */       return null;
/*      */     }
/* 1980 */     if (this.pointTypes[this.numTypes - 1] == 4)
/*      */     {
/* 1982 */       for (int j = this.numTypes - 2; j > 0; j--) {
/* 1983 */         switch (this.pointTypes[j]) {
/*      */           case 0:
/*      */             break;
/*      */           case 1:
/* 1987 */             i -= 2;
/*      */             break;
/*      */           case 2:
/* 1990 */             i -= 4;
/*      */             break;
/*      */           case 3:
/* 1993 */             i -= 6;
/*      */             break;
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*      */     }
/* 2000 */     return getPoint(i - 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized void reset() {
/* 2011 */     this.numTypes = this.numCoords = 0;
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
/*      */   public abstract void transform(AffineTransform paramAffineTransform);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final synchronized Shape createTransformedShape(AffineTransform paramAffineTransform) {
/* 2047 */     Path2D path2D = (Path2D)clone();
/* 2048 */     if (paramAffineTransform != null) {
/* 2049 */       path2D.transform(paramAffineTransform);
/*      */     }
/* 2051 */     return path2D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Rectangle getBounds() {
/* 2059 */     return getBounds2D().getBounds();
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
/*      */   public static boolean contains(PathIterator paramPathIterator, double paramDouble1, double paramDouble2) {
/* 2078 */     if (paramDouble1 * 0.0D + paramDouble2 * 0.0D == 0.0D) {
/*      */ 
/*      */ 
/*      */       
/* 2082 */       byte b = (paramPathIterator.getWindingRule() == 1) ? -1 : 1;
/* 2083 */       int i = Curve.pointCrossingsForPath(paramPathIterator, paramDouble1, paramDouble2);
/* 2084 */       return ((i & b) != 0);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2091 */     return false;
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
/*      */   public static boolean contains(PathIterator paramPathIterator, Point2D paramPoint2D) {
/* 2110 */     return contains(paramPathIterator, paramPoint2D.getX(), paramPoint2D.getY());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean contains(double paramDouble1, double paramDouble2) {
/* 2118 */     if (paramDouble1 * 0.0D + paramDouble2 * 0.0D == 0.0D) {
/*      */ 
/*      */ 
/*      */       
/* 2122 */       if (this.numTypes < 2) {
/* 2123 */         return false;
/*      */       }
/* 2125 */       boolean bool = (this.windingRule == 1) ? true : true;
/* 2126 */       return ((pointCrossings(paramDouble1, paramDouble2) & bool) != 0);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2133 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean contains(Point2D paramPoint2D) {
/* 2142 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
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
/*      */   public static boolean contains(PathIterator paramPathIterator, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 2179 */     if (Double.isNaN(paramDouble1 + paramDouble3) || Double.isNaN(paramDouble2 + paramDouble4))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2188 */       return false;
/*      */     }
/* 2190 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 2191 */       return false;
/*      */     }
/* 2193 */     byte b = (paramPathIterator.getWindingRule() == 1) ? -1 : 2;
/* 2194 */     int i = Curve.rectCrossingsForPath(paramPathIterator, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2195 */     return (i != Integer.MIN_VALUE && (i & b) != 0);
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
/*      */   public static boolean contains(PathIterator paramPathIterator, Rectangle2D paramRectangle2D) {
/* 2228 */     return contains(paramPathIterator, paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public final boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 2251 */     if (Double.isNaN(paramDouble1 + paramDouble3) || Double.isNaN(paramDouble2 + paramDouble4))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2260 */       return false;
/*      */     }
/* 2262 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 2263 */       return false;
/*      */     }
/* 2265 */     byte b = (this.windingRule == 1) ? -1 : 2;
/* 2266 */     int i = rectCrossings(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2267 */     return (i != Integer.MIN_VALUE && (i & b) != 0);
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
/*      */   public final boolean contains(Rectangle2D paramRectangle2D) {
/* 2291 */     return contains(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public static boolean intersects(PathIterator paramPathIterator, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 2329 */     if (Double.isNaN(paramDouble1 + paramDouble3) || Double.isNaN(paramDouble2 + paramDouble4))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2338 */       return false;
/*      */     }
/* 2340 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 2341 */       return false;
/*      */     }
/* 2343 */     byte b = (paramPathIterator.getWindingRule() == 1) ? -1 : 2;
/* 2344 */     int i = Curve.rectCrossingsForPath(paramPathIterator, paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2345 */     return (i == Integer.MIN_VALUE || (i & b) != 0);
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
/*      */   public static boolean intersects(PathIterator paramPathIterator, Rectangle2D paramRectangle2D) {
/* 2378 */     return intersects(paramPathIterator, paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public final boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 2400 */     if (Double.isNaN(paramDouble1 + paramDouble3) || Double.isNaN(paramDouble2 + paramDouble4))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2409 */       return false;
/*      */     }
/* 2411 */     if (paramDouble3 <= 0.0D || paramDouble4 <= 0.0D) {
/* 2412 */       return false;
/*      */     }
/* 2414 */     byte b = (this.windingRule == 1) ? -1 : 2;
/* 2415 */     int i = rectCrossings(paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4);
/* 2416 */     return (i == Integer.MIN_VALUE || (i & b) != 0);
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
/*      */   public final boolean intersects(Rectangle2D paramRectangle2D) {
/* 2439 */     return intersects(paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
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
/*      */   public final PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/* 2456 */     return new FlatteningPathIterator(getPathIterator(paramAffineTransform), paramDouble);
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
/*      */   public abstract Object clone();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void writeObject(ObjectOutputStream paramObjectOutputStream, boolean paramBoolean) throws IOException {
/*      */     float[] arrayOfFloat;
/*      */     Object object;
/* 2496 */     paramObjectOutputStream.defaultWriteObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2501 */     if (paramBoolean) {
/* 2502 */       object = ((Double)this).doubleCoords;
/* 2503 */       arrayOfFloat = null;
/*      */     } else {
/* 2505 */       arrayOfFloat = ((Float)this).floatCoords;
/* 2506 */       object = null;
/*      */     } 
/*      */     
/* 2509 */     int i = this.numTypes;
/*      */     
/* 2511 */     paramObjectOutputStream.writeByte(paramBoolean ? 49 : 48);
/*      */ 
/*      */     
/* 2514 */     paramObjectOutputStream.writeInt(i);
/* 2515 */     paramObjectOutputStream.writeInt(this.numCoords);
/* 2516 */     paramObjectOutputStream.writeByte((byte)this.windingRule);
/*      */     
/* 2518 */     byte b1 = 0;
/* 2519 */     for (byte b2 = 0; b2 < i; b2++) {
/*      */       byte b3, b4;
/*      */       
/* 2522 */       switch (this.pointTypes[b2]) {
/*      */         case 0:
/* 2524 */           b3 = 1;
/* 2525 */           b4 = paramBoolean ? 80 : 64;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/* 2530 */           b3 = 1;
/* 2531 */           b4 = paramBoolean ? 81 : 65;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 2536 */           b3 = 2;
/* 2537 */           b4 = paramBoolean ? 82 : 66;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/* 2542 */           b3 = 3;
/* 2543 */           b4 = paramBoolean ? 83 : 67;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 4:
/* 2548 */           b3 = 0;
/* 2549 */           b4 = 96;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 2554 */           throw new InternalError("unrecognized path type");
/*      */       } 
/* 2556 */       paramObjectOutputStream.writeByte(b4);
/* 2557 */       while (--b3 >= 0) {
/* 2558 */         if (paramBoolean) {
/* 2559 */           paramObjectOutputStream.writeDouble(object[b1++]);
/* 2560 */           paramObjectOutputStream.writeDouble(object[b1++]); continue;
/*      */         } 
/* 2562 */         paramObjectOutputStream.writeFloat(arrayOfFloat[b1++]);
/* 2563 */         paramObjectOutputStream.writeFloat(arrayOfFloat[b1++]);
/*      */       } 
/*      */     } 
/*      */     
/* 2567 */     paramObjectOutputStream.writeByte(97);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void readObject(ObjectInputStream paramObjectInputStream, boolean paramBoolean) throws ClassNotFoundException, IOException {
/* 2573 */     paramObjectInputStream.defaultReadObject();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2578 */     paramObjectInputStream.readByte();
/* 2579 */     int i = paramObjectInputStream.readInt();
/* 2580 */     int j = paramObjectInputStream.readInt();
/*      */     try {
/* 2582 */       setWindingRule(paramObjectInputStream.readByte());
/* 2583 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 2584 */       throw new InvalidObjectException(illegalArgumentException.getMessage());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2589 */     this.pointTypes = new byte[(i < 0 || i > 20) ? 20 : i];
/*      */     
/* 2591 */     if (j < 0 || j > 40) {
/* 2592 */       j = 40;
/*      */     }
/* 2594 */     if (paramBoolean) {
/* 2595 */       ((Double)this).doubleCoords = new double[j];
/*      */     } else {
/* 2597 */       ((Float)this).floatCoords = new float[j];
/*      */     } 
/*      */ 
/*      */     
/* 2601 */     for (byte b = 0; i < 0 || b < i; b++) {
/*      */       boolean bool;
/*      */       
/*      */       byte b1, b2;
/*      */       
/* 2606 */       byte b3 = paramObjectInputStream.readByte();
/* 2607 */       switch (b3) {
/*      */         case 64:
/* 2609 */           bool = false;
/* 2610 */           b1 = 1;
/* 2611 */           b2 = 0;
/*      */           break;
/*      */         case 65:
/* 2614 */           bool = false;
/* 2615 */           b1 = 1;
/* 2616 */           b2 = 1;
/*      */           break;
/*      */         case 66:
/* 2619 */           bool = false;
/* 2620 */           b1 = 2;
/* 2621 */           b2 = 2;
/*      */           break;
/*      */         case 67:
/* 2624 */           bool = false;
/* 2625 */           b1 = 3;
/* 2626 */           b2 = 3;
/*      */           break;
/*      */         
/*      */         case 80:
/* 2630 */           bool = true;
/* 2631 */           b1 = 1;
/* 2632 */           b2 = 0;
/*      */           break;
/*      */         case 81:
/* 2635 */           bool = true;
/* 2636 */           b1 = 1;
/* 2637 */           b2 = 1;
/*      */           break;
/*      */         case 82:
/* 2640 */           bool = true;
/* 2641 */           b1 = 2;
/* 2642 */           b2 = 2;
/*      */           break;
/*      */         case 83:
/* 2645 */           bool = true;
/* 2646 */           b1 = 3;
/* 2647 */           b2 = 3;
/*      */           break;
/*      */         
/*      */         case 96:
/* 2651 */           bool = false;
/* 2652 */           b1 = 0;
/* 2653 */           b2 = 4;
/*      */           break;
/*      */         
/*      */         case 97:
/* 2657 */           if (i < 0) {
/*      */             break;
/*      */           }
/* 2660 */           throw new StreamCorruptedException("unexpected PATH_END");
/*      */         
/*      */         default:
/* 2663 */           throw new StreamCorruptedException("unrecognized path type");
/*      */       } 
/* 2665 */       needRoom((b2 != 0), b1 * 2);
/* 2666 */       if (bool) {
/* 2667 */         while (--b1 >= 0) {
/* 2668 */           append(paramObjectInputStream.readDouble(), paramObjectInputStream.readDouble());
/*      */         }
/*      */       } else {
/* 2671 */         while (--b1 >= 0) {
/* 2672 */           append(paramObjectInputStream.readFloat(), paramObjectInputStream.readFloat());
/*      */         }
/*      */       } 
/* 2675 */       this.pointTypes[this.numTypes++] = b2;
/*      */     } 
/* 2677 */     if (i >= 0 && paramObjectInputStream.readByte() != 97)
/* 2678 */       throw new StreamCorruptedException("missing PATH_END"); 
/*      */   }
/*      */   
/*      */   static abstract class Iterator
/*      */     implements PathIterator
/*      */   {
/*      */     int typeIdx;
/*      */     int pointIdx;
/*      */     Path2D path;
/* 2687 */     static final int[] curvecoords = new int[] { 2, 2, 4, 6, 0 };
/*      */     
/*      */     Iterator(Path2D param1Path2D) {
/* 2690 */       this.path = param1Path2D;
/*      */     }
/*      */     
/*      */     public int getWindingRule() {
/* 2694 */       return this.path.getWindingRule();
/*      */     }
/*      */     
/*      */     public boolean isDone() {
/* 2698 */       return (this.typeIdx >= this.path.numTypes);
/*      */     }
/*      */     
/*      */     public void next() {
/* 2702 */       byte b = this.path.pointTypes[this.typeIdx++];
/* 2703 */       this.pointIdx += curvecoords[b];
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/Path2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */