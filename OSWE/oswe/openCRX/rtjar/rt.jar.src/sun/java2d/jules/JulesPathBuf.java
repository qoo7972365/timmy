/*     */ package sun.java2d.jules;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.awt.X11GraphicsEnvironment;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.xr.GrowableByteArray;
/*     */ import sun.java2d.xr.GrowablePointArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JulesPathBuf
/*     */ {
/*  35 */   static final double[] emptyDash = new double[0];
/*     */   
/*     */   private static final byte CAIRO_PATH_OP_MOVE_TO = 0;
/*     */   
/*     */   private static final byte CAIRO_PATH_OP_LINE_TO = 1;
/*     */   
/*     */   private static final byte CAIRO_PATH_OP_CURVE_TO = 2;
/*     */   private static final byte CAIRO_PATH_OP_CLOSE_PATH = 3;
/*     */   private static final int CAIRO_FILL_RULE_WINDING = 0;
/*     */   private static final int CAIRO_FILL_RULE_EVEN_ODD = 1;
/*  45 */   GrowablePointArray points = new GrowablePointArray(128);
/*  46 */   GrowableByteArray ops = new GrowableByteArray(1, 128);
/*  47 */   int[] xTrapArray = new int[512];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final boolean isCairoAvailable = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run() {
/*  56 */           boolean bool = false;
/*  57 */           if (X11GraphicsEnvironment.isXRenderAvailable()) {
/*     */             try {
/*  59 */               System.loadLibrary("jules");
/*  60 */               bool = true;
/*  61 */               if (X11GraphicsEnvironment.isXRenderVerbose()) {
/*  62 */                 System.out.println("Xrender: INFO: Jules library loaded");
/*     */               }
/*     */             }
/*  65 */             catch (UnsatisfiedLinkError unsatisfiedLinkError) {
/*  66 */               bool = false;
/*  67 */               if (X11GraphicsEnvironment.isXRenderVerbose()) {
/*  68 */                 System.out.println("Xrender: INFO: Jules library not installed.");
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/*  73 */           return Boolean.valueOf(bool);
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*     */   
/*     */   public static boolean isCairoAvailable() {
/*  79 */     return isCairoAvailable;
/*     */   }
/*     */   
/*     */   public TrapezoidList tesselateFill(Shape paramShape, AffineTransform paramAffineTransform, Region paramRegion) {
/*  83 */     int i = convertPathData(paramShape, paramAffineTransform);
/*  84 */     this.xTrapArray[0] = 0;
/*     */     
/*  86 */     this.xTrapArray = tesselateFillNative(this.points.getArray(), this.ops.getArray(), this.points
/*  87 */         .getSize(), this.ops.getSize(), this.xTrapArray, this.xTrapArray.length, 
/*     */         
/*  89 */         getCairoWindingRule(i), paramRegion
/*  90 */         .getLoX(), paramRegion.getLoY(), paramRegion
/*  91 */         .getHiX(), paramRegion.getHiY());
/*     */     
/*  93 */     return new TrapezoidList(this.xTrapArray);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrapezoidList tesselateStroke(Shape paramShape, BasicStroke paramBasicStroke, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, AffineTransform paramAffineTransform, Region paramRegion) {
/*     */     float f;
/* 101 */     if (paramBoolean1) {
/* 102 */       if (paramBoolean3) {
/* 103 */         f = 0.5F;
/*     */       } else {
/* 105 */         f = 1.0F;
/*     */       } 
/*     */     } else {
/* 108 */       f = paramBasicStroke.getLineWidth();
/*     */     } 
/*     */     
/* 111 */     convertPathData(paramShape, paramAffineTransform);
/*     */     
/* 113 */     double[] arrayOfDouble = floatToDoubleArray(paramBasicStroke.getDashArray());
/* 114 */     this.xTrapArray[0] = 0;
/*     */     
/* 116 */     this
/* 117 */       .xTrapArray = tesselateStrokeNative(this.points.getArray(), this.ops.getArray(), this.points
/* 118 */         .getSize(), this.ops.getSize(), this.xTrapArray, this.xTrapArray.length, f, paramBasicStroke
/*     */         
/* 120 */         .getEndCap(), paramBasicStroke.getLineJoin(), paramBasicStroke
/* 121 */         .getMiterLimit(), arrayOfDouble, arrayOfDouble.length, paramBasicStroke
/* 122 */         .getDashPhase(), 1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, paramRegion
/*     */         
/* 124 */         .getLoX(), paramRegion.getLoY(), paramRegion
/* 125 */         .getHiX(), paramRegion.getHiY());
/*     */     
/* 127 */     return new TrapezoidList(this.xTrapArray);
/*     */   }
/*     */   
/*     */   protected double[] floatToDoubleArray(float[] paramArrayOffloat) {
/* 131 */     double[] arrayOfDouble = emptyDash;
/* 132 */     if (paramArrayOffloat != null) {
/* 133 */       arrayOfDouble = new double[paramArrayOffloat.length];
/*     */       
/* 135 */       for (byte b = 0; b < paramArrayOffloat.length; b++) {
/* 136 */         arrayOfDouble[b] = paramArrayOffloat[b];
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return arrayOfDouble;
/*     */   }
/*     */   
/*     */   protected int convertPathData(Shape paramShape, AffineTransform paramAffineTransform) {
/* 144 */     PathIterator pathIterator = paramShape.getPathIterator(paramAffineTransform);
/*     */     
/* 146 */     double[] arrayOfDouble = new double[6];
/* 147 */     double d1 = 0.0D;
/* 148 */     double d2 = 0.0D;
/*     */     
/* 150 */     while (!pathIterator.isDone()) {
/* 151 */       int j; double d3, d4, d5, d6, d7, d8; int i = pathIterator.currentSegment(arrayOfDouble);
/*     */ 
/*     */       
/* 154 */       switch (i) {
/*     */         
/*     */         case 0:
/* 157 */           this.ops.addByte((byte)0);
/* 158 */           j = this.points.getNextIndex();
/* 159 */           this.points.setX(j, DoubleToCairoFixed(arrayOfDouble[0]));
/* 160 */           this.points.setY(j, DoubleToCairoFixed(arrayOfDouble[1]));
/* 161 */           d1 = arrayOfDouble[0];
/* 162 */           d2 = arrayOfDouble[1];
/*     */           break;
/*     */         
/*     */         case 1:
/* 166 */           this.ops.addByte((byte)1);
/* 167 */           j = this.points.getNextIndex();
/* 168 */           this.points.setX(j, DoubleToCairoFixed(arrayOfDouble[0]));
/* 169 */           this.points.setY(j, DoubleToCairoFixed(arrayOfDouble[1]));
/* 170 */           d1 = arrayOfDouble[0];
/* 171 */           d2 = arrayOfDouble[1];
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 181 */           d3 = arrayOfDouble[0];
/* 182 */           d4 = arrayOfDouble[1];
/*     */           
/* 184 */           d7 = arrayOfDouble[2];
/* 185 */           d8 = arrayOfDouble[3];
/*     */           
/* 187 */           d5 = d3 + (d7 - d3) / 3.0D;
/* 188 */           d6 = d4 + (d8 - d4) / 3.0D;
/* 189 */           d3 = d1 + 2.0D * (d3 - d1) / 3.0D;
/* 190 */           d4 = d2 + 2.0D * (d4 - d2) / 3.0D;
/*     */           
/* 192 */           this.ops.addByte((byte)2);
/* 193 */           j = this.points.getNextIndex();
/* 194 */           this.points.setX(j, DoubleToCairoFixed(d3));
/* 195 */           this.points.setY(j, DoubleToCairoFixed(d4));
/* 196 */           j = this.points.getNextIndex();
/* 197 */           this.points.setX(j, DoubleToCairoFixed(d5));
/* 198 */           this.points.setY(j, DoubleToCairoFixed(d6));
/* 199 */           j = this.points.getNextIndex();
/* 200 */           this.points.setX(j, DoubleToCairoFixed(d7));
/* 201 */           this.points.setY(j, DoubleToCairoFixed(d8));
/* 202 */           d1 = d7;
/* 203 */           d2 = d8;
/*     */           break;
/*     */         
/*     */         case 3:
/* 207 */           this.ops.addByte((byte)2);
/* 208 */           j = this.points.getNextIndex();
/* 209 */           this.points.setX(j, DoubleToCairoFixed(arrayOfDouble[0]));
/* 210 */           this.points.setY(j, DoubleToCairoFixed(arrayOfDouble[1]));
/* 211 */           j = this.points.getNextIndex();
/* 212 */           this.points.setX(j, DoubleToCairoFixed(arrayOfDouble[2]));
/* 213 */           this.points.setY(j, DoubleToCairoFixed(arrayOfDouble[3]));
/* 214 */           j = this.points.getNextIndex();
/* 215 */           this.points.setX(j, DoubleToCairoFixed(arrayOfDouble[4]));
/* 216 */           this.points.setY(j, DoubleToCairoFixed(arrayOfDouble[5]));
/* 217 */           d1 = arrayOfDouble[4];
/* 218 */           d2 = arrayOfDouble[5];
/*     */           break;
/*     */         
/*     */         case 4:
/* 222 */           this.ops.addByte((byte)3);
/*     */           break;
/*     */       } 
/*     */       
/* 226 */       pathIterator.next();
/*     */     } 
/*     */     
/* 229 */     return pathIterator.getWindingRule();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int[] tesselateStrokeNative(int[] paramArrayOfint1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, double paramDouble1, int paramInt4, int paramInt5, double paramDouble2, double[] paramArrayOfdouble, int paramInt6, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, int paramInt7, int paramInt8, int paramInt9, int paramInt10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static native int[] tesselateFillNative(int[] paramArrayOfint1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int[] paramArrayOfint2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 250 */     this.points.clear();
/* 251 */     this.ops.clear();
/* 252 */     this.xTrapArray[0] = 0;
/*     */   }
/*     */   
/*     */   private static int DoubleToCairoFixed(double paramDouble) {
/* 256 */     return (int)(paramDouble * 256.0D);
/*     */   }
/*     */   
/*     */   private static int getCairoWindingRule(int paramInt) {
/* 260 */     switch (paramInt) {
/*     */       case 0:
/* 262 */         return 1;
/*     */       
/*     */       case 1:
/* 265 */         return 0;
/*     */     } 
/*     */     
/* 268 */     throw new IllegalArgumentException("Illegal Java2D winding rule specified");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/jules/JulesPathBuf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */