/*      */ package sun.java2d.loops;
/*      */ 
/*      */ import java.awt.geom.Path2D;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.QuadCurve2D;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ProcessPath
/*      */ {
/*      */   public static final int PH_MODE_DRAW_CLIP = 0;
/*      */   public static final int PH_MODE_FILL_CLIP = 1;
/*      */   
/*      */   public static abstract class DrawHandler
/*      */   {
/*      */     public int xMin;
/*      */     public int yMin;
/*      */     public int xMax;
/*      */     public int yMax;
/*      */     public float xMinf;
/*      */     public float yMinf;
/*      */     public float xMaxf;
/*      */     public float yMaxf;
/*      */     public int strokeControl;
/*      */     
/*      */     public DrawHandler(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*   63 */       setBounds(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*   68 */       this.xMin = param1Int1;
/*   69 */       this.yMin = param1Int2;
/*   70 */       this.xMax = param1Int3;
/*   71 */       this.yMax = param1Int4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*   84 */       this.xMinf = param1Int1 - 0.5F;
/*   85 */       this.yMinf = param1Int2 - 0.5F;
/*   86 */       this.xMaxf = param1Int3 - 0.5F - 9.765625E-4F;
/*   87 */       this.yMaxf = param1Int4 - 0.5F - 9.765625E-4F;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/*   93 */       this.strokeControl = param1Int5;
/*   94 */       setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */ 
/*      */     
/*      */     public void adjustBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*   99 */       if (this.xMin > param1Int1) param1Int1 = this.xMin; 
/*  100 */       if (this.xMax < param1Int3) param1Int3 = this.xMax; 
/*  101 */       if (this.yMin > param1Int2) param1Int2 = this.yMin; 
/*  102 */       if (this.yMax < param1Int4) param1Int4 = this.yMax; 
/*  103 */       setBounds(param1Int1, param1Int2, param1Int3, param1Int4);
/*      */     }
/*      */     
/*      */     public DrawHandler(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  107 */       this(param1Int1, param1Int2, param1Int3, param1Int4, 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public abstract void drawLine(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
/*      */ 
/*      */     
/*      */     public abstract void drawPixel(int param1Int1, int param1Int2);
/*      */ 
/*      */     
/*      */     public abstract void drawScanline(int param1Int1, int param1Int2, int param1Int3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static abstract class ProcessHandler
/*      */     implements EndSubPathHandler
/*      */   {
/*      */     ProcessPath.DrawHandler dhnd;
/*      */     
/*      */     int clipMode;
/*      */ 
/*      */     
/*      */     public ProcessHandler(ProcessPath.DrawHandler param1DrawHandler, int param1Int) {
/*  130 */       this.dhnd = param1DrawHandler;
/*  131 */       this.clipMode = param1Int;
/*      */     }
/*      */     public abstract void processFixedLine(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int[] param1ArrayOfint, boolean param1Boolean1, boolean param1Boolean2); } private static final float UPPER_BND = 8.5070587E37F; private static final float LOWER_BND = -8.5070587E37F; private static final int FWD_PREC = 7; private static final int MDP_PREC = 10; private static final int MDP_MULT = 1024;
/*      */   private static final int MDP_HALF_MULT = 512;
/*      */   private static final int UPPER_OUT_BND = 1048576;
/*      */   private static final int LOWER_OUT_BND = -1048576;
/*      */   private static final float CALC_UBND = 1048576.0F;
/*      */   private static final float CALC_LBND = -1048576.0F;
/*      */   
/*  140 */   public static EndSubPathHandler noopEndSubPathHandler = new EndSubPathHandler() { public void processEndSubPath() {} }
/*      */   ;
/*      */   public static final int EPSFX = 1; public static final float EPSF = 9.765625E-4F;
/*      */   private static final int MDP_W_MASK = -1024;
/*      */   private static final int MDP_F_MASK = 1023;
/*      */   private static final int MAX_CUB_SIZE = 256;
/*      */   
/*      */   public static boolean fillPath(DrawHandler paramDrawHandler, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/*  148 */     FillProcessHandler fillProcessHandler = new FillProcessHandler(paramDrawHandler);
/*  149 */     if (!doProcessPath(fillProcessHandler, paramFloat, paramInt1, paramInt2)) {
/*  150 */       return false;
/*      */     }
/*  152 */     FillPolygon(fillProcessHandler, paramFloat.getWindingRule());
/*  153 */     return true;
/*      */   }
/*      */   private static final int MAX_QUAD_SIZE = 1024; private static final int DF_CUB_STEPS = 3; private static final int DF_QUAD_STEPS = 2; private static final int DF_CUB_SHIFT = 6; private static final int DF_QUAD_SHIFT = 1; private static final int DF_CUB_COUNT = 8; private static final int DF_QUAD_COUNT = 4; private static final int DF_CUB_DEC_BND = 262144; private static final int DF_CUB_INC_BND = 32768; private static final int DF_QUAD_DEC_BND = 8192; private static final int DF_QUAD_INC_BND = 1024; private static final int CUB_A_SHIFT = 7;
/*      */   private static final int CUB_B_SHIFT = 11;
/*      */   private static final int CUB_C_SHIFT = 13;
/*      */   private static final int CUB_A_MDP_MULT = 128;
/*      */   
/*      */   public static boolean drawPath(DrawHandler paramDrawHandler, EndSubPathHandler paramEndSubPathHandler, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/*  161 */     return doProcessPath(new DrawProcessHandler(paramDrawHandler, paramEndSubPathHandler), paramFloat, paramInt1, paramInt2);
/*      */   }
/*      */   private static final int CUB_B_MDP_MULT = 2048; private static final int CUB_C_MDP_MULT = 8192; private static final int QUAD_A_SHIFT = 7; private static final int QUAD_B_SHIFT = 9; private static final int QUAD_A_MDP_MULT = 128; private static final int QUAD_B_MDP_MULT = 512; private static final int CRES_MIN_CLIPPED = 0; private static final int CRES_MAX_CLIPPED = 1;
/*      */   private static final int CRES_NOT_CLIPPED = 3;
/*      */   private static final int CRES_INVISIBLE = 4;
/*      */   private static final int DF_MAX_POINT = 256;
/*      */   
/*      */   public static boolean drawPath(DrawHandler paramDrawHandler, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/*  169 */     return doProcessPath(new DrawProcessHandler(paramDrawHandler, noopEndSubPathHandler), paramFloat, paramInt1, paramInt2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static float CLIP(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, double paramDouble) {
/*  283 */     return (float)(paramFloat2 + (paramDouble - paramFloat1) * (paramFloat4 - paramFloat2) / (paramFloat3 - paramFloat1));
/*      */   }
/*      */   
/*      */   private static int CLIP(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble) {
/*  287 */     return (int)(paramInt2 + (paramDouble - paramInt1) * (paramInt4 - paramInt2) / (paramInt3 - paramInt1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean IS_CLIPPED(int paramInt) {
/*  297 */     return (paramInt == 0 || paramInt == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int TESTANDCLIP(float paramFloat1, float paramFloat2, float[] paramArrayOffloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  307 */     byte b = 3;
/*  308 */     if (paramArrayOffloat[paramInt1] < paramFloat1 || paramArrayOffloat[paramInt1] > paramFloat2) {
/*  309 */       double d; if (paramArrayOffloat[paramInt1] < paramFloat1) {
/*  310 */         if (paramArrayOffloat[paramInt3] < paramFloat1) {
/*  311 */           return 4;
/*      */         }
/*  313 */         b = 0;
/*  314 */         d = paramFloat1;
/*      */       } else {
/*  316 */         if (paramArrayOffloat[paramInt3] > paramFloat2) {
/*  317 */           return 4;
/*      */         }
/*  319 */         b = 1;
/*  320 */         d = paramFloat2;
/*      */       } 
/*  322 */       paramArrayOffloat[paramInt2] = CLIP(paramArrayOffloat[paramInt1], paramArrayOffloat[paramInt2], paramArrayOffloat[paramInt3], paramArrayOffloat[paramInt4], d);
/*  323 */       paramArrayOffloat[paramInt1] = (float)d;
/*      */     } 
/*  325 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int TESTANDCLIP(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  332 */     byte b = 3;
/*  333 */     if (paramArrayOfint[paramInt3] < paramInt1 || paramArrayOfint[paramInt3] > paramInt2) {
/*  334 */       double d; if (paramArrayOfint[paramInt3] < paramInt1) {
/*  335 */         if (paramArrayOfint[paramInt5] < paramInt1) {
/*  336 */           return 4;
/*      */         }
/*  338 */         b = 0;
/*  339 */         d = paramInt1;
/*      */       } else {
/*  341 */         if (paramArrayOfint[paramInt5] > paramInt2) {
/*  342 */           return 4;
/*      */         }
/*  344 */         b = 1;
/*  345 */         d = paramInt2;
/*      */       } 
/*  347 */       paramArrayOfint[paramInt4] = CLIP(paramArrayOfint[paramInt3], paramArrayOfint[paramInt4], paramArrayOfint[paramInt5], paramArrayOfint[paramInt6], d);
/*  348 */       paramArrayOfint[paramInt3] = (int)d;
/*      */     } 
/*  350 */     return b;
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
/*      */   private static int CLIPCLAMP(float paramFloat1, float paramFloat2, float[] paramArrayOffloat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  383 */     paramArrayOffloat[paramInt5] = paramArrayOffloat[paramInt1];
/*  384 */     paramArrayOffloat[paramInt6] = paramArrayOffloat[paramInt2];
/*  385 */     int i = TESTANDCLIP(paramFloat1, paramFloat2, paramArrayOffloat, paramInt1, paramInt2, paramInt3, paramInt4);
/*  386 */     if (i == 0) {
/*  387 */       paramArrayOffloat[paramInt5] = paramArrayOffloat[paramInt1];
/*  388 */     } else if (i == 1) {
/*  389 */       paramArrayOffloat[paramInt5] = paramArrayOffloat[paramInt1];
/*  390 */       i = 1;
/*  391 */     } else if (i == 4) {
/*  392 */       if (paramArrayOffloat[paramInt1] > paramFloat2) {
/*  393 */         i = 4;
/*      */       } else {
/*  395 */         paramArrayOffloat[paramInt1] = paramFloat1;
/*  396 */         paramArrayOffloat[paramInt3] = paramFloat1;
/*  397 */         i = 3;
/*      */       } 
/*      */     } 
/*  400 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int CLIPCLAMP(int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
/*  407 */     paramArrayOfint[paramInt7] = paramArrayOfint[paramInt3];
/*  408 */     paramArrayOfint[paramInt8] = paramArrayOfint[paramInt4];
/*  409 */     int i = TESTANDCLIP(paramInt1, paramInt2, paramArrayOfint, paramInt3, paramInt4, paramInt5, paramInt6);
/*  410 */     if (i == 0) {
/*  411 */       paramArrayOfint[paramInt7] = paramArrayOfint[paramInt3];
/*  412 */     } else if (i == 1) {
/*  413 */       paramArrayOfint[paramInt7] = paramArrayOfint[paramInt3];
/*  414 */       i = 1;
/*  415 */     } else if (i == 4) {
/*  416 */       if (paramArrayOfint[paramInt3] > paramInt2) {
/*  417 */         i = 4;
/*      */       } else {
/*  419 */         paramArrayOfint[paramInt3] = paramInt1;
/*  420 */         paramArrayOfint[paramInt5] = paramInt1;
/*  421 */         i = 3;
/*      */       } 
/*      */     } 
/*  424 */     return i;
/*      */   }
/*      */   
/*      */   private static class DrawProcessHandler
/*      */     extends ProcessHandler
/*      */   {
/*      */     ProcessPath.EndSubPathHandler processESP;
/*      */     
/*      */     public DrawProcessHandler(ProcessPath.DrawHandler param1DrawHandler, ProcessPath.EndSubPathHandler param1EndSubPathHandler) {
/*  433 */       super(param1DrawHandler, 0);
/*  434 */       this.dhnd = param1DrawHandler;
/*  435 */       this.processESP = param1EndSubPathHandler;
/*      */     }
/*      */     
/*      */     public void processEndSubPath() {
/*  439 */       this.processESP.processEndSubPath();
/*      */     }
/*      */ 
/*      */     
/*      */     void PROCESS_LINE(int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean, int[] param1ArrayOfint) {
/*  444 */       int i = param1Int1 >> 10;
/*  445 */       int j = param1Int2 >> 10;
/*  446 */       int k = param1Int3 >> 10;
/*  447 */       int m = param1Int4 >> 10;
/*      */ 
/*      */       
/*  450 */       if ((i ^ k | j ^ m) == 0) {
/*  451 */         if (param1Boolean && (this.dhnd.yMin > j || this.dhnd.yMax <= j || this.dhnd.xMin > i || this.dhnd.xMax <= i)) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  457 */         if (param1ArrayOfint[0] == 0) {
/*  458 */           param1ArrayOfint[0] = 1;
/*  459 */           param1ArrayOfint[1] = i;
/*  460 */           param1ArrayOfint[2] = j;
/*  461 */           param1ArrayOfint[3] = i;
/*  462 */           param1ArrayOfint[4] = j;
/*  463 */           this.dhnd.drawPixel(i, j);
/*  464 */         } else if ((i != param1ArrayOfint[3] || j != param1ArrayOfint[4]) && (i != param1ArrayOfint[1] || j != param1ArrayOfint[2])) {
/*      */           
/*  466 */           this.dhnd.drawPixel(i, j);
/*  467 */           param1ArrayOfint[3] = i;
/*  468 */           param1ArrayOfint[4] = j;
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*  473 */       if (!param1Boolean || (this.dhnd.yMin <= j && this.dhnd.yMax > j && this.dhnd.xMin <= i && this.dhnd.xMax > i))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  480 */         if (param1ArrayOfint[0] == 1 && ((param1ArrayOfint[1] == i && param1ArrayOfint[2] == j) || (param1ArrayOfint[3] == i && param1ArrayOfint[4] == j)))
/*      */         {
/*      */ 
/*      */           
/*  484 */           this.dhnd.drawPixel(i, j);
/*      */         }
/*      */       }
/*      */       
/*  488 */       this.dhnd.drawLine(i, j, k, m);
/*      */       
/*  490 */       if (param1ArrayOfint[0] == 0) {
/*  491 */         param1ArrayOfint[0] = 1;
/*  492 */         param1ArrayOfint[1] = i;
/*  493 */         param1ArrayOfint[2] = j;
/*  494 */         param1ArrayOfint[3] = i;
/*  495 */         param1ArrayOfint[4] = j;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  501 */       if ((param1ArrayOfint[1] == k && param1ArrayOfint[2] == m) || (param1ArrayOfint[3] == k && param1ArrayOfint[4] == m)) {
/*      */ 
/*      */         
/*  504 */         if (param1Boolean && (this.dhnd.yMin > m || this.dhnd.yMax <= m || this.dhnd.xMin > k || this.dhnd.xMax <= k)) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  512 */         this.dhnd.drawPixel(k, m);
/*      */       } 
/*  514 */       param1ArrayOfint[3] = k;
/*  515 */       param1ArrayOfint[4] = m;
/*      */     }
/*      */ 
/*      */     
/*      */     void PROCESS_POINT(int param1Int1, int param1Int2, boolean param1Boolean, int[] param1ArrayOfint) {
/*  520 */       int i = param1Int1 >> 10;
/*  521 */       int j = param1Int2 >> 10;
/*  522 */       if (param1Boolean && (this.dhnd.yMin > j || this.dhnd.yMax <= j || this.dhnd.xMin > i || this.dhnd.xMax <= i)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  536 */       if (param1ArrayOfint[0] == 0) {
/*  537 */         param1ArrayOfint[0] = 1;
/*  538 */         param1ArrayOfint[1] = i;
/*  539 */         param1ArrayOfint[2] = j;
/*  540 */         param1ArrayOfint[3] = i;
/*  541 */         param1ArrayOfint[4] = j;
/*  542 */         this.dhnd.drawPixel(i, j);
/*  543 */       } else if ((i != param1ArrayOfint[3] || j != param1ArrayOfint[4]) && (i != param1ArrayOfint[1] || j != param1ArrayOfint[2])) {
/*      */         
/*  545 */         this.dhnd.drawPixel(i, j);
/*  546 */         param1ArrayOfint[3] = i;
/*  547 */         param1ArrayOfint[4] = j;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void processFixedLine(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int[] param1ArrayOfint, boolean param1Boolean1, boolean param1Boolean2) {
/*  580 */       int j, k, m, n, i = param1Int1 ^ param1Int3 | param1Int2 ^ param1Int4;
/*      */       
/*  582 */       if ((i & 0xFFFFFC00) == 0) {
/*      */ 
/*      */ 
/*      */         
/*  586 */         if (i == 0) {
/*  587 */           PROCESS_POINT(param1Int1 + 512, param1Int2 + 512, param1Boolean1, param1ArrayOfint);
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  593 */       if (param1Int1 == param1Int3 || param1Int2 == param1Int4) {
/*  594 */         j = param1Int1 + 512;
/*  595 */         m = param1Int3 + 512;
/*  596 */         k = param1Int2 + 512;
/*  597 */         n = param1Int4 + 512;
/*      */       } else {
/*      */         
/*  600 */         int i1 = param1Int3 - param1Int1;
/*  601 */         int i2 = param1Int4 - param1Int2;
/*      */ 
/*      */         
/*  604 */         int i3 = param1Int1 & 0xFFFFFC00;
/*  605 */         int i4 = param1Int2 & 0xFFFFFC00;
/*  606 */         int i5 = param1Int3 & 0xFFFFFC00;
/*  607 */         int i6 = param1Int4 & 0xFFFFFC00;
/*      */ 
/*      */         
/*  610 */         if (i3 == param1Int1 || i4 == param1Int2) {
/*      */ 
/*      */ 
/*      */           
/*  614 */           j = param1Int1 + 512;
/*  615 */           k = param1Int2 + 512;
/*      */         } else {
/*      */           
/*  618 */           int i7 = (param1Int1 < param1Int3) ? (i3 + 1024) : i3;
/*  619 */           int i8 = (param1Int2 < param1Int4) ? (i4 + 1024) : i4;
/*      */ 
/*      */           
/*  622 */           int i9 = param1Int2 + (i7 - param1Int1) * i2 / i1;
/*  623 */           if (i9 >= i4 && i9 <= i4 + 1024) {
/*  624 */             j = i7;
/*  625 */             k = i9 + 512;
/*      */           } else {
/*      */             
/*  628 */             i9 = param1Int1 + (i8 - param1Int2) * i1 / i2;
/*  629 */             j = i9 + 512;
/*  630 */             k = i8;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  635 */         if (i5 == param1Int3 || i6 == param1Int4) {
/*      */ 
/*      */ 
/*      */           
/*  639 */           m = param1Int3 + 512;
/*  640 */           n = param1Int4 + 512;
/*      */         } else {
/*      */           
/*  643 */           int i7 = (param1Int1 > param1Int3) ? (i5 + 1024) : i5;
/*  644 */           int i8 = (param1Int2 > param1Int4) ? (i6 + 1024) : i6;
/*      */ 
/*      */           
/*  647 */           int i9 = param1Int4 + (i7 - param1Int3) * i2 / i1;
/*  648 */           if (i9 >= i6 && i9 <= i6 + 1024) {
/*  649 */             m = i7;
/*  650 */             n = i9 + 512;
/*      */           } else {
/*      */             
/*  653 */             i9 = param1Int3 + (i8 - param1Int4) * i1 / i2;
/*  654 */             m = i9 + 512;
/*  655 */             n = i8;
/*      */           } 
/*      */         } 
/*      */       } 
/*  659 */       PROCESS_LINE(j, k, m, n, param1Boolean1, param1ArrayOfint);
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
/*      */   private static void DrawMonotonicQuad(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, boolean paramBoolean, int[] paramArrayOfint) {
/*  673 */     int i = (int)(paramArrayOffloat[0] * 1024.0F);
/*  674 */     int j = (int)(paramArrayOffloat[1] * 1024.0F);
/*      */     
/*  676 */     int k = (int)(paramArrayOffloat[4] * 1024.0F);
/*  677 */     int m = (int)(paramArrayOffloat[5] * 1024.0F);
/*      */ 
/*      */     
/*  680 */     int n = (i & 0x3FF) << 1;
/*  681 */     int i1 = (j & 0x3FF) << 1;
/*      */ 
/*      */     
/*  684 */     int i2 = 4;
/*      */ 
/*      */     
/*  687 */     boolean bool = true;
/*      */     
/*  689 */     int i3 = (int)((paramArrayOffloat[0] - 2.0F * paramArrayOffloat[2] + paramArrayOffloat[4]) * 128.0F);
/*      */     
/*  691 */     int i4 = (int)((paramArrayOffloat[1] - 2.0F * paramArrayOffloat[3] + paramArrayOffloat[5]) * 128.0F);
/*      */ 
/*      */     
/*  694 */     int i5 = (int)((-2.0F * paramArrayOffloat[0] + 2.0F * paramArrayOffloat[2]) * 512.0F);
/*  695 */     int i6 = (int)((-2.0F * paramArrayOffloat[1] + 2.0F * paramArrayOffloat[3]) * 512.0F);
/*      */     
/*  697 */     int i7 = 2 * i3;
/*  698 */     int i8 = 2 * i4;
/*      */     
/*  700 */     int i9 = i3 + i5;
/*  701 */     int i10 = i4 + i6;
/*      */ 
/*      */ 
/*      */     
/*  705 */     int i11 = i;
/*  706 */     int i12 = j;
/*      */     
/*  708 */     int i13 = Math.max(Math.abs(i7), Math.abs(i8));
/*      */     
/*  710 */     int i14 = k - i;
/*  711 */     int i15 = m - j;
/*      */     
/*  713 */     int i16 = i & 0xFFFFFC00;
/*  714 */     int i17 = j & 0xFFFFFC00;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  722 */     while (i13 > 8192) {
/*  723 */       i9 = (i9 << 1) - i3;
/*  724 */       i10 = (i10 << 1) - i4;
/*  725 */       i2 <<= 1;
/*  726 */       i13 >>= 2;
/*  727 */       n <<= 2;
/*  728 */       i1 <<= 2;
/*  729 */       bool += true;
/*      */     } 
/*      */     
/*  732 */     while (i2-- > 1) {
/*  733 */       n += i9;
/*  734 */       i1 += i10;
/*      */       
/*  736 */       i9 += i7;
/*  737 */       i10 += i8;
/*      */       
/*  739 */       int i18 = i11;
/*  740 */       int i19 = i12;
/*      */       
/*  742 */       i11 = i16 + (n >> bool);
/*  743 */       i12 = i17 + (i1 >> bool);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  752 */       if ((k - i11 ^ i14) < 0) {
/*  753 */         i11 = k;
/*      */       }
/*      */ 
/*      */       
/*  757 */       if ((m - i12 ^ i15) < 0) {
/*  758 */         i12 = m;
/*      */       }
/*      */       
/*  761 */       paramProcessHandler.processFixedLine(i18, i19, i11, i12, paramArrayOfint, paramBoolean, false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  770 */     paramProcessHandler.processFixedLine(i11, i12, k, m, paramArrayOfint, paramBoolean, false);
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
/*      */   private static void ProcessMonotonicQuad(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, int[] paramArrayOfint) {
/*  782 */     float[] arrayOfFloat = new float[6];
/*      */ 
/*      */ 
/*      */     
/*  786 */     float f3 = paramArrayOffloat[0], f1 = f3;
/*  787 */     float f4 = paramArrayOffloat[1], f2 = f4;
/*  788 */     for (byte b = 2; b < 6; b += 2) {
/*  789 */       f1 = (f1 > paramArrayOffloat[b]) ? paramArrayOffloat[b] : f1;
/*  790 */       f3 = (f3 < paramArrayOffloat[b]) ? paramArrayOffloat[b] : f3;
/*  791 */       f2 = (f2 > paramArrayOffloat[b + 1]) ? paramArrayOffloat[b + 1] : f2;
/*  792 */       f4 = (f4 < paramArrayOffloat[b + 1]) ? paramArrayOffloat[b + 1] : f4;
/*      */     } 
/*      */     
/*  795 */     if (paramProcessHandler.clipMode == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  800 */       if (paramProcessHandler.dhnd.xMaxf < f1 || paramProcessHandler.dhnd.xMinf > f3 || paramProcessHandler.dhnd.yMaxf < f2 || paramProcessHandler.dhnd.yMinf > f4)
/*      */       {
/*      */         return;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  810 */       if (paramProcessHandler.dhnd.yMaxf < f2 || paramProcessHandler.dhnd.yMinf > f4 || paramProcessHandler.dhnd.xMaxf < f1) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  820 */       if (paramProcessHandler.dhnd.xMinf > f3) {
/*  821 */         paramArrayOffloat[4] = paramProcessHandler.dhnd.xMinf; paramArrayOffloat[2] = paramProcessHandler.dhnd.xMinf; paramArrayOffloat[0] = paramProcessHandler.dhnd.xMinf;
/*      */       } 
/*      */     } 
/*      */     
/*  825 */     if (f3 - f1 > 1024.0F || f4 - f2 > 1024.0F) {
/*  826 */       arrayOfFloat[4] = paramArrayOffloat[4];
/*  827 */       arrayOfFloat[5] = paramArrayOffloat[5];
/*  828 */       arrayOfFloat[2] = (paramArrayOffloat[2] + paramArrayOffloat[4]) / 2.0F;
/*  829 */       arrayOfFloat[3] = (paramArrayOffloat[3] + paramArrayOffloat[5]) / 2.0F;
/*  830 */       paramArrayOffloat[2] = (paramArrayOffloat[0] + paramArrayOffloat[2]) / 2.0F;
/*  831 */       paramArrayOffloat[3] = (paramArrayOffloat[1] + paramArrayOffloat[3]) / 2.0F;
/*  832 */       arrayOfFloat[0] = (paramArrayOffloat[2] + arrayOfFloat[2]) / 2.0F; paramArrayOffloat[4] = (paramArrayOffloat[2] + arrayOfFloat[2]) / 2.0F;
/*  833 */       arrayOfFloat[1] = (paramArrayOffloat[3] + arrayOfFloat[3]) / 2.0F; paramArrayOffloat[5] = (paramArrayOffloat[3] + arrayOfFloat[3]) / 2.0F;
/*      */       
/*  835 */       ProcessMonotonicQuad(paramProcessHandler, paramArrayOffloat, paramArrayOfint);
/*      */       
/*  837 */       ProcessMonotonicQuad(paramProcessHandler, arrayOfFloat, paramArrayOfint);
/*      */     } else {
/*  839 */       DrawMonotonicQuad(paramProcessHandler, paramArrayOffloat, (paramProcessHandler.dhnd.xMinf >= f1 || paramProcessHandler.dhnd.xMaxf <= f3 || paramProcessHandler.dhnd.yMinf >= f2 || paramProcessHandler.dhnd.yMaxf <= f4), paramArrayOfint);
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
/*      */   private static void ProcessQuad(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, int[] paramArrayOfint) {
/*  863 */     double d, arrayOfDouble[] = new double[2];
/*  864 */     byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  872 */     if ((paramArrayOffloat[0] > paramArrayOffloat[2] || paramArrayOffloat[2] > paramArrayOffloat[4]) && (paramArrayOffloat[0] < paramArrayOffloat[2] || paramArrayOffloat[2] < paramArrayOffloat[4])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  880 */       double d1 = (paramArrayOffloat[0] - 2.0F * paramArrayOffloat[2] + paramArrayOffloat[4]);
/*  881 */       if (d1 != 0.0D) {
/*      */ 
/*      */ 
/*      */         
/*  885 */         double d3 = (paramArrayOffloat[0] - paramArrayOffloat[2]);
/*      */         
/*  887 */         double d2 = d3 / d1;
/*  888 */         if (d2 < 1.0D && d2 > 0.0D) {
/*  889 */           arrayOfDouble[b++] = d2;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     if ((paramArrayOffloat[1] > paramArrayOffloat[3] || paramArrayOffloat[3] > paramArrayOffloat[5]) && (paramArrayOffloat[1] < paramArrayOffloat[3] || paramArrayOffloat[3] < paramArrayOffloat[5])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  907 */       double d1 = (paramArrayOffloat[1] - 2.0F * paramArrayOffloat[3] + paramArrayOffloat[5]);
/*      */       
/*  909 */       if (d1 != 0.0D) {
/*      */ 
/*      */ 
/*      */         
/*  913 */         double d3 = (paramArrayOffloat[1] - paramArrayOffloat[3]);
/*      */         
/*  915 */         double d2 = d3 / d1;
/*  916 */         if (d2 < 1.0D && d2 > 0.0D) {
/*  917 */           if (b > 0) {
/*      */ 
/*      */ 
/*      */             
/*  921 */             if (arrayOfDouble[0] > d2) {
/*  922 */               arrayOfDouble[b++] = arrayOfDouble[0];
/*  923 */               arrayOfDouble[0] = d2;
/*  924 */             } else if (arrayOfDouble[0] < d2) {
/*  925 */               arrayOfDouble[b++] = d2;
/*      */             } 
/*      */           } else {
/*  928 */             arrayOfDouble[b++] = d2;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  935 */     switch (b) {
/*      */ 
/*      */       
/*      */       case 1:
/*  939 */         ProcessFirstMonotonicPartOfQuad(paramProcessHandler, paramArrayOffloat, paramArrayOfint, (float)arrayOfDouble[0]);
/*      */         break;
/*      */       
/*      */       case 2:
/*  943 */         ProcessFirstMonotonicPartOfQuad(paramProcessHandler, paramArrayOffloat, paramArrayOfint, (float)arrayOfDouble[0]);
/*      */         
/*  945 */         d = arrayOfDouble[1] - arrayOfDouble[0];
/*  946 */         if (d > 0.0D) {
/*  947 */           ProcessFirstMonotonicPartOfQuad(paramProcessHandler, paramArrayOffloat, paramArrayOfint, (float)(d / (1.0D - arrayOfDouble[0])));
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  956 */     ProcessMonotonicQuad(paramProcessHandler, paramArrayOffloat, paramArrayOfint);
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
/*      */   private static void ProcessFirstMonotonicPartOfQuad(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, int[] paramArrayOfint, float paramFloat) {
/*  969 */     float[] arrayOfFloat = new float[6];
/*      */     
/*  971 */     arrayOfFloat[0] = paramArrayOffloat[0];
/*  972 */     arrayOfFloat[1] = paramArrayOffloat[1];
/*  973 */     arrayOfFloat[2] = paramArrayOffloat[0] + paramFloat * (paramArrayOffloat[2] - paramArrayOffloat[0]);
/*  974 */     arrayOfFloat[3] = paramArrayOffloat[1] + paramFloat * (paramArrayOffloat[3] - paramArrayOffloat[1]);
/*  975 */     paramArrayOffloat[2] = paramArrayOffloat[2] + paramFloat * (paramArrayOffloat[4] - paramArrayOffloat[2]);
/*  976 */     paramArrayOffloat[3] = paramArrayOffloat[3] + paramFloat * (paramArrayOffloat[5] - paramArrayOffloat[3]);
/*  977 */     arrayOfFloat[4] = arrayOfFloat[2] + paramFloat * (paramArrayOffloat[2] - arrayOfFloat[2]); paramArrayOffloat[0] = arrayOfFloat[2] + paramFloat * (paramArrayOffloat[2] - arrayOfFloat[2]);
/*  978 */     arrayOfFloat[5] = arrayOfFloat[3] + paramFloat * (paramArrayOffloat[3] - arrayOfFloat[3]); paramArrayOffloat[1] = arrayOfFloat[3] + paramFloat * (paramArrayOffloat[3] - arrayOfFloat[3]);
/*      */     
/*  980 */     ProcessMonotonicQuad(paramProcessHandler, arrayOfFloat, paramArrayOfint);
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
/*      */   private static void DrawMonotonicCubic(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, boolean paramBoolean, int[] paramArrayOfint) {
/*  992 */     int i = (int)(paramArrayOffloat[0] * 1024.0F);
/*  993 */     int j = (int)(paramArrayOffloat[1] * 1024.0F);
/*      */     
/*  995 */     int k = (int)(paramArrayOffloat[6] * 1024.0F);
/*  996 */     int m = (int)(paramArrayOffloat[7] * 1024.0F);
/*      */ 
/*      */     
/*  999 */     int n = (i & 0x3FF) << 6;
/* 1000 */     int i1 = (j & 0x3FF) << 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1006 */     int i2 = 32768;
/* 1007 */     int i3 = 262144;
/*      */ 
/*      */     
/* 1010 */     int i4 = 8;
/*      */ 
/*      */     
/* 1013 */     byte b = 6;
/*      */     
/* 1015 */     int i5 = (int)((-paramArrayOffloat[0] + 3.0F * paramArrayOffloat[2] - 3.0F * paramArrayOffloat[4] + paramArrayOffloat[6]) * 128.0F);
/*      */     
/* 1017 */     int i6 = (int)((-paramArrayOffloat[1] + 3.0F * paramArrayOffloat[3] - 3.0F * paramArrayOffloat[5] + paramArrayOffloat[7]) * 128.0F);
/*      */ 
/*      */     
/* 1020 */     int i7 = (int)((3.0F * paramArrayOffloat[0] - 6.0F * paramArrayOffloat[2] + 3.0F * paramArrayOffloat[4]) * 2048.0F);
/*      */     
/* 1022 */     int i8 = (int)((3.0F * paramArrayOffloat[1] - 6.0F * paramArrayOffloat[3] + 3.0F * paramArrayOffloat[5]) * 2048.0F);
/*      */ 
/*      */     
/* 1025 */     int i9 = (int)((-3.0F * paramArrayOffloat[0] + 3.0F * paramArrayOffloat[2]) * 8192.0F);
/* 1026 */     int i10 = (int)((-3.0F * paramArrayOffloat[1] + 3.0F * paramArrayOffloat[3]) * 8192.0F);
/*      */     
/* 1028 */     int i11 = 6 * i5;
/* 1029 */     int i12 = 6 * i6;
/*      */     
/* 1031 */     int i13 = i11 + i7;
/* 1032 */     int i14 = i12 + i8;
/*      */     
/* 1034 */     int i15 = i5 + (i7 >> 1) + i9;
/* 1035 */     int i16 = i6 + (i8 >> 1) + i10;
/*      */ 
/*      */ 
/*      */     
/* 1039 */     int i17 = i;
/* 1040 */     int i18 = j;
/*      */ 
/*      */     
/* 1043 */     int i19 = i & 0xFFFFFC00;
/* 1044 */     int i20 = j & 0xFFFFFC00;
/*      */     
/* 1046 */     int i21 = k - i;
/* 1047 */     int i22 = m - j;
/*      */     
/* 1049 */     while (i4 > 0) {
/*      */       
/* 1051 */       while (Math.abs(i13) > i3 || 
/* 1052 */         Math.abs(i14) > i3) {
/* 1053 */         i13 = (i13 << 1) - i11;
/* 1054 */         i14 = (i14 << 1) - i12;
/* 1055 */         i15 = (i15 << 2) - (i13 >> 1);
/* 1056 */         i16 = (i16 << 2) - (i14 >> 1);
/* 1057 */         i4 <<= 1;
/* 1058 */         i3 <<= 3;
/* 1059 */         i2 <<= 3;
/* 1060 */         n <<= 3;
/* 1061 */         i1 <<= 3;
/* 1062 */         b += 3;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1069 */       while ((i4 & 0x1) == 0 && b > 6 && 
/* 1070 */         Math.abs(i15) <= i2 && 
/* 1071 */         Math.abs(i16) <= i2) {
/* 1072 */         i15 = (i15 >> 2) + (i13 >> 3);
/* 1073 */         i16 = (i16 >> 2) + (i14 >> 3);
/* 1074 */         i13 = i13 + i11 >> 1;
/* 1075 */         i14 = i14 + i12 >> 1;
/* 1076 */         i4 >>= 1;
/* 1077 */         i3 >>= 3;
/* 1078 */         i2 >>= 3;
/* 1079 */         n >>= 3;
/* 1080 */         i1 >>= 3;
/* 1081 */         b -= 3;
/*      */       } 
/*      */       
/* 1084 */       i4--;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1091 */       if (i4 > 0) {
/* 1092 */         n += i15;
/* 1093 */         i1 += i16;
/*      */         
/* 1095 */         i15 += i13;
/* 1096 */         i16 += i14;
/* 1097 */         i13 += i11;
/* 1098 */         i14 += i12;
/*      */         
/* 1100 */         int i23 = i17;
/* 1101 */         int i24 = i18;
/*      */         
/* 1103 */         i17 = i19 + (n >> b);
/* 1104 */         i18 = i20 + (i1 >> b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1113 */         if ((k - i17 ^ i21) < 0) {
/* 1114 */           i17 = k;
/*      */         }
/*      */ 
/*      */         
/* 1118 */         if ((m - i18 ^ i22) < 0) {
/* 1119 */           i18 = m;
/*      */         }
/*      */         
/* 1122 */         paramProcessHandler.processFixedLine(i23, i24, i17, i18, paramArrayOfint, paramBoolean, false);
/*      */         continue;
/*      */       } 
/* 1125 */       paramProcessHandler.processFixedLine(i17, i18, k, m, paramArrayOfint, paramBoolean, false);
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
/*      */   private static void ProcessMonotonicCubic(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, int[] paramArrayOfint) {
/* 1140 */     float[] arrayOfFloat = new float[8];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     float f2 = paramArrayOffloat[0], f1 = f2;
/* 1146 */     float f4 = paramArrayOffloat[1], f3 = f4;
/*      */     
/* 1148 */     for (byte b = 2; b < 8; b += 2) {
/* 1149 */       f1 = (f1 > paramArrayOffloat[b]) ? paramArrayOffloat[b] : f1;
/* 1150 */       f2 = (f2 < paramArrayOffloat[b]) ? paramArrayOffloat[b] : f2;
/* 1151 */       f3 = (f3 > paramArrayOffloat[b + 1]) ? paramArrayOffloat[b + 1] : f3;
/* 1152 */       f4 = (f4 < paramArrayOffloat[b + 1]) ? paramArrayOffloat[b + 1] : f4;
/*      */     } 
/*      */     
/* 1155 */     if (paramProcessHandler.clipMode == 0) {
/*      */ 
/*      */ 
/*      */       
/* 1159 */       if (paramProcessHandler.dhnd.xMaxf < f1 || paramProcessHandler.dhnd.xMinf > f2 || paramProcessHandler.dhnd.yMaxf < f3 || paramProcessHandler.dhnd.yMinf > f4)
/*      */       {
/*      */         return;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1169 */       if (paramProcessHandler.dhnd.yMaxf < f3 || paramProcessHandler.dhnd.yMinf > f4 || paramProcessHandler.dhnd.xMaxf < f1) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1179 */       if (paramProcessHandler.dhnd.xMinf > f2) {
/* 1180 */         paramArrayOffloat[6] = paramProcessHandler.dhnd.xMinf; paramArrayOffloat[4] = paramProcessHandler.dhnd.xMinf; paramArrayOffloat[2] = paramProcessHandler.dhnd.xMinf; paramArrayOffloat[0] = paramProcessHandler.dhnd.xMinf;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1185 */     if (f2 - f1 > 256.0F || f4 - f3 > 256.0F) {
/* 1186 */       arrayOfFloat[6] = paramArrayOffloat[6];
/* 1187 */       arrayOfFloat[7] = paramArrayOffloat[7];
/* 1188 */       arrayOfFloat[4] = (paramArrayOffloat[4] + paramArrayOffloat[6]) / 2.0F;
/* 1189 */       arrayOfFloat[5] = (paramArrayOffloat[5] + paramArrayOffloat[7]) / 2.0F;
/* 1190 */       float f5 = (paramArrayOffloat[2] + paramArrayOffloat[4]) / 2.0F;
/* 1191 */       float f6 = (paramArrayOffloat[3] + paramArrayOffloat[5]) / 2.0F;
/* 1192 */       arrayOfFloat[2] = (f5 + arrayOfFloat[4]) / 2.0F;
/* 1193 */       arrayOfFloat[3] = (f6 + arrayOfFloat[5]) / 2.0F;
/* 1194 */       paramArrayOffloat[2] = (paramArrayOffloat[0] + paramArrayOffloat[2]) / 2.0F;
/* 1195 */       paramArrayOffloat[3] = (paramArrayOffloat[1] + paramArrayOffloat[3]) / 2.0F;
/* 1196 */       paramArrayOffloat[4] = (paramArrayOffloat[2] + f5) / 2.0F;
/* 1197 */       paramArrayOffloat[5] = (paramArrayOffloat[3] + f6) / 2.0F;
/* 1198 */       arrayOfFloat[0] = (paramArrayOffloat[4] + arrayOfFloat[2]) / 2.0F; paramArrayOffloat[6] = (paramArrayOffloat[4] + arrayOfFloat[2]) / 2.0F;
/* 1199 */       arrayOfFloat[1] = (paramArrayOffloat[5] + arrayOfFloat[3]) / 2.0F; paramArrayOffloat[7] = (paramArrayOffloat[5] + arrayOfFloat[3]) / 2.0F;
/*      */       
/* 1201 */       ProcessMonotonicCubic(paramProcessHandler, paramArrayOffloat, paramArrayOfint);
/*      */       
/* 1203 */       ProcessMonotonicCubic(paramProcessHandler, arrayOfFloat, paramArrayOfint);
/*      */     } else {
/* 1205 */       DrawMonotonicCubic(paramProcessHandler, paramArrayOffloat, (paramProcessHandler.dhnd.xMinf > f1 || paramProcessHandler.dhnd.xMaxf < f2 || paramProcessHandler.dhnd.yMinf > f3 || paramProcessHandler.dhnd.yMaxf < f4), paramArrayOfint);
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
/*      */   private static void ProcessCubic(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, int[] paramArrayOfint) {
/* 1231 */     double[] arrayOfDouble1 = new double[4];
/* 1232 */     double[] arrayOfDouble2 = new double[3];
/* 1233 */     double[] arrayOfDouble3 = new double[2];
/* 1234 */     byte b = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1241 */     if ((paramArrayOffloat[0] > paramArrayOffloat[2] || paramArrayOffloat[2] > paramArrayOffloat[4] || paramArrayOffloat[4] > paramArrayOffloat[6]) && (paramArrayOffloat[0] < paramArrayOffloat[2] || paramArrayOffloat[2] < paramArrayOffloat[4] || paramArrayOffloat[4] < paramArrayOffloat[6])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1251 */       arrayOfDouble2[2] = (-paramArrayOffloat[0] + 3.0F * paramArrayOffloat[2] - 3.0F * paramArrayOffloat[4] + paramArrayOffloat[6]);
/* 1252 */       arrayOfDouble2[1] = (2.0F * (paramArrayOffloat[0] - 2.0F * paramArrayOffloat[2] + paramArrayOffloat[4]));
/* 1253 */       arrayOfDouble2[0] = (-paramArrayOffloat[0] + paramArrayOffloat[2]);
/*      */       
/* 1255 */       int i = QuadCurve2D.solveQuadratic(arrayOfDouble2, arrayOfDouble3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1261 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1262 */         if (arrayOfDouble3[b1] > 0.0D && arrayOfDouble3[b1] < 1.0D) {
/* 1263 */           arrayOfDouble1[b++] = arrayOfDouble3[b1];
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1273 */     if ((paramArrayOffloat[1] > paramArrayOffloat[3] || paramArrayOffloat[3] > paramArrayOffloat[5] || paramArrayOffloat[5] > paramArrayOffloat[7]) && (paramArrayOffloat[1] < paramArrayOffloat[3] || paramArrayOffloat[3] < paramArrayOffloat[5] || paramArrayOffloat[5] < paramArrayOffloat[7])) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1283 */       arrayOfDouble2[2] = (-paramArrayOffloat[1] + 3.0F * paramArrayOffloat[3] - 3.0F * paramArrayOffloat[5] + paramArrayOffloat[7]);
/* 1284 */       arrayOfDouble2[1] = (2.0F * (paramArrayOffloat[1] - 2.0F * paramArrayOffloat[3] + paramArrayOffloat[5]));
/* 1285 */       arrayOfDouble2[0] = (-paramArrayOffloat[1] + paramArrayOffloat[3]);
/*      */       
/* 1287 */       int i = QuadCurve2D.solveQuadratic(arrayOfDouble2, arrayOfDouble3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1293 */       for (byte b1 = 0; b1 < i; b1++) {
/* 1294 */         if (arrayOfDouble3[b1] > 0.0D && arrayOfDouble3[b1] < 1.0D) {
/* 1295 */           arrayOfDouble1[b++] = arrayOfDouble3[b1];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1300 */     if (b > 0) {
/*      */ 
/*      */ 
/*      */       
/* 1304 */       Arrays.sort(arrayOfDouble1, 0, b);
/*      */ 
/*      */       
/* 1307 */       ProcessFirstMonotonicPartOfCubic(paramProcessHandler, paramArrayOffloat, paramArrayOfint, (float)arrayOfDouble1[0]);
/*      */       
/* 1309 */       for (byte b1 = 1; b1 < b; b1++) {
/* 1310 */         double d = arrayOfDouble1[b1] - arrayOfDouble1[b1 - 1];
/* 1311 */         if (d > 0.0D) {
/* 1312 */           ProcessFirstMonotonicPartOfCubic(paramProcessHandler, paramArrayOffloat, paramArrayOfint, (float)(d / (1.0D - arrayOfDouble1[b1 - 1])));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1319 */     ProcessMonotonicCubic(paramProcessHandler, paramArrayOffloat, paramArrayOfint);
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
/*      */   private static void ProcessFirstMonotonicPartOfCubic(ProcessHandler paramProcessHandler, float[] paramArrayOffloat, int[] paramArrayOfint, float paramFloat) {
/* 1333 */     float[] arrayOfFloat = new float[8];
/*      */ 
/*      */     
/* 1336 */     arrayOfFloat[0] = paramArrayOffloat[0];
/* 1337 */     arrayOfFloat[1] = paramArrayOffloat[1];
/* 1338 */     float f1 = paramArrayOffloat[2] + paramFloat * (paramArrayOffloat[4] - paramArrayOffloat[2]);
/* 1339 */     float f2 = paramArrayOffloat[3] + paramFloat * (paramArrayOffloat[5] - paramArrayOffloat[3]);
/* 1340 */     arrayOfFloat[2] = paramArrayOffloat[0] + paramFloat * (paramArrayOffloat[2] - paramArrayOffloat[0]);
/* 1341 */     arrayOfFloat[3] = paramArrayOffloat[1] + paramFloat * (paramArrayOffloat[3] - paramArrayOffloat[1]);
/* 1342 */     arrayOfFloat[4] = arrayOfFloat[2] + paramFloat * (f1 - arrayOfFloat[2]);
/* 1343 */     arrayOfFloat[5] = arrayOfFloat[3] + paramFloat * (f2 - arrayOfFloat[3]);
/* 1344 */     paramArrayOffloat[4] = paramArrayOffloat[4] + paramFloat * (paramArrayOffloat[6] - paramArrayOffloat[4]);
/* 1345 */     paramArrayOffloat[5] = paramArrayOffloat[5] + paramFloat * (paramArrayOffloat[7] - paramArrayOffloat[5]);
/* 1346 */     paramArrayOffloat[2] = f1 + paramFloat * (paramArrayOffloat[4] - f1);
/* 1347 */     paramArrayOffloat[3] = f2 + paramFloat * (paramArrayOffloat[5] - f2);
/* 1348 */     arrayOfFloat[6] = arrayOfFloat[4] + paramFloat * (paramArrayOffloat[2] - arrayOfFloat[4]); paramArrayOffloat[0] = arrayOfFloat[4] + paramFloat * (paramArrayOffloat[2] - arrayOfFloat[4]);
/* 1349 */     arrayOfFloat[7] = arrayOfFloat[5] + paramFloat * (paramArrayOffloat[3] - arrayOfFloat[5]); paramArrayOffloat[1] = arrayOfFloat[5] + paramFloat * (paramArrayOffloat[3] - arrayOfFloat[5]);
/*      */     
/* 1351 */     ProcessMonotonicCubic(paramProcessHandler, arrayOfFloat, paramArrayOfint);
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
/*      */   private static void ProcessLine(ProcessHandler paramProcessHandler, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int[] paramArrayOfint) {
/* 1363 */     boolean bool1 = false;
/*      */     
/* 1365 */     float[] arrayOfFloat = { paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F };
/*      */ 
/*      */ 
/*      */     
/* 1369 */     float f1 = paramProcessHandler.dhnd.xMinf;
/* 1370 */     float f2 = paramProcessHandler.dhnd.yMinf;
/* 1371 */     float f3 = paramProcessHandler.dhnd.xMaxf;
/* 1372 */     float f4 = paramProcessHandler.dhnd.yMaxf;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1377 */     int i = TESTANDCLIP(f2, f4, arrayOfFloat, 1, 0, 3, 2);
/* 1378 */     if (i == 4)
/* 1379 */       return;  bool1 = IS_CLIPPED(i);
/*      */ 
/*      */ 
/*      */     
/* 1383 */     i = TESTANDCLIP(f2, f4, arrayOfFloat, 3, 2, 1, 0);
/* 1384 */     if (i == 4)
/* 1385 */       return;  boolean bool2 = IS_CLIPPED(i);
/* 1386 */     bool1 = (bool1 || bool2);
/*      */     
/* 1388 */     if (paramProcessHandler.clipMode == 0) {
/*      */ 
/*      */ 
/*      */       
/* 1392 */       i = TESTANDCLIP(f1, f3, arrayOfFloat, 0, 1, 2, 3);
/* 1393 */       if (i == 4)
/* 1394 */         return;  bool1 = (bool1 || IS_CLIPPED(i));
/*      */ 
/*      */ 
/*      */       
/* 1398 */       i = TESTANDCLIP(f1, f3, arrayOfFloat, 2, 3, 0, 1);
/* 1399 */       if (i == 4)
/* 1400 */         return;  bool2 = (bool2 || IS_CLIPPED(i));
/* 1401 */       bool1 = (bool1 || bool2);
/* 1402 */       int j = (int)(arrayOfFloat[0] * 1024.0F);
/* 1403 */       int k = (int)(arrayOfFloat[1] * 1024.0F);
/* 1404 */       int m = (int)(arrayOfFloat[2] * 1024.0F);
/* 1405 */       int n = (int)(arrayOfFloat[3] * 1024.0F);
/*      */       
/* 1407 */       paramProcessHandler.processFixedLine(j, k, m, n, paramArrayOfint, bool1, bool2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1426 */       i = CLIPCLAMP(f1, f3, arrayOfFloat, 0, 1, 2, 3, 4, 5);
/* 1427 */       int j = (int)(arrayOfFloat[0] * 1024.0F);
/* 1428 */       int k = (int)(arrayOfFloat[1] * 1024.0F);
/*      */ 
/*      */       
/* 1431 */       if (i == 0) {
/* 1432 */         int i1 = (int)(arrayOfFloat[4] * 1024.0F);
/* 1433 */         int i2 = (int)(arrayOfFloat[5] * 1024.0F);
/* 1434 */         paramProcessHandler.processFixedLine(i1, i2, j, k, paramArrayOfint, false, bool2);
/*      */       
/*      */       }
/* 1437 */       else if (i == 4) {
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1446 */       i = CLIPCLAMP(f1, f3, arrayOfFloat, 2, 3, 0, 1, 4, 5);
/*      */ 
/*      */       
/* 1449 */       bool2 = (bool2 || i == 1);
/*      */       
/* 1451 */       int m = (int)(arrayOfFloat[2] * 1024.0F);
/* 1452 */       int n = (int)(arrayOfFloat[3] * 1024.0F);
/* 1453 */       paramProcessHandler.processFixedLine(j, k, m, n, paramArrayOfint, false, bool2);
/*      */ 
/*      */ 
/*      */       
/* 1457 */       if (i == 0) {
/* 1458 */         int i1 = (int)(arrayOfFloat[4] * 1024.0F);
/* 1459 */         int i2 = (int)(arrayOfFloat[5] * 1024.0F);
/* 1460 */         paramProcessHandler.processFixedLine(m, n, i1, i2, paramArrayOfint, false, bool2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean doProcessPath(ProcessHandler paramProcessHandler, Path2D.Float paramFloat, float paramFloat1, float paramFloat2) {
/* 1469 */     float[] arrayOfFloat1 = new float[8];
/* 1470 */     float[] arrayOfFloat2 = new float[8];
/* 1471 */     float[] arrayOfFloat3 = { 0.0F, 0.0F };
/* 1472 */     float[] arrayOfFloat4 = new float[2];
/* 1473 */     int[] arrayOfInt = new int[5];
/* 1474 */     boolean bool1 = false;
/* 1475 */     boolean bool2 = false;
/*      */     
/* 1477 */     arrayOfInt[0] = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1482 */     paramProcessHandler.dhnd.adjustBounds(-1048576, -1048576, 1048576, 1048576);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1495 */     if (paramProcessHandler.dhnd.strokeControl == 2) {
/* 1496 */       arrayOfFloat3[0] = -0.5F;
/* 1497 */       arrayOfFloat3[1] = -0.5F;
/* 1498 */       paramFloat1 = (float)(paramFloat1 - 0.5D);
/* 1499 */       paramFloat2 = (float)(paramFloat2 - 0.5D);
/*      */     } 
/*      */     
/* 1502 */     PathIterator pathIterator = paramFloat.getPathIterator(null);
/*      */     
/* 1504 */     while (!pathIterator.isDone()) {
/* 1505 */       float f1, f2; switch (pathIterator.currentSegment(arrayOfFloat1)) {
/*      */         
/*      */         case 0:
/* 1508 */           if (bool1 && !bool2) {
/* 1509 */             if (paramProcessHandler.clipMode == 1 && (
/* 1510 */               arrayOfFloat2[0] != arrayOfFloat3[0] || arrayOfFloat2[1] != arrayOfFloat3[1]))
/*      */             {
/*      */               
/* 1513 */               ProcessLine(paramProcessHandler, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat3[0], arrayOfFloat3[1], arrayOfInt);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1518 */             paramProcessHandler.processEndSubPath();
/*      */           } 
/*      */           
/* 1521 */           arrayOfFloat2[0] = arrayOfFloat1[0] + paramFloat1;
/* 1522 */           arrayOfFloat2[1] = arrayOfFloat1[1] + paramFloat2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1530 */           if (arrayOfFloat2[0] < 8.5070587E37F && arrayOfFloat2[0] > -8.5070587E37F && arrayOfFloat2[1] < 8.5070587E37F && arrayOfFloat2[1] > -8.5070587E37F) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1535 */             bool1 = true;
/* 1536 */             bool2 = false;
/* 1537 */             arrayOfFloat3[0] = arrayOfFloat2[0];
/* 1538 */             arrayOfFloat3[1] = arrayOfFloat2[1];
/*      */           } else {
/* 1540 */             bool2 = true;
/*      */           } 
/* 1542 */           arrayOfInt[0] = 0;
/*      */           break;
/*      */         case 1:
/* 1545 */           f1 = arrayOfFloat2[2] = arrayOfFloat1[0] + paramFloat1;
/* 1546 */           f2 = arrayOfFloat2[3] = arrayOfFloat1[1] + paramFloat2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1555 */           if (f1 < 8.5070587E37F && f1 > -8.5070587E37F && f2 < 8.5070587E37F && f2 > -8.5070587E37F) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1560 */             if (bool2) {
/* 1561 */               arrayOfFloat3[0] = f1; arrayOfFloat2[0] = f1;
/* 1562 */               arrayOfFloat3[1] = f2; arrayOfFloat2[1] = f2;
/* 1563 */               bool1 = true;
/* 1564 */               bool2 = false; break;
/*      */             } 
/* 1566 */             ProcessLine(paramProcessHandler, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2], arrayOfFloat2[3], arrayOfInt);
/*      */             
/* 1568 */             arrayOfFloat2[0] = f1;
/* 1569 */             arrayOfFloat2[1] = f2;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 2:
/* 1574 */           arrayOfFloat2[2] = arrayOfFloat1[0] + paramFloat1;
/* 1575 */           arrayOfFloat2[3] = arrayOfFloat1[1] + paramFloat2;
/* 1576 */           f1 = arrayOfFloat2[4] = arrayOfFloat1[2] + paramFloat1;
/* 1577 */           f2 = arrayOfFloat2[5] = arrayOfFloat1[3] + paramFloat2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1587 */           if (f1 < 8.5070587E37F && f1 > -8.5070587E37F && f2 < 8.5070587E37F && f2 > -8.5070587E37F) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1592 */             if (bool2) {
/* 1593 */               arrayOfFloat3[0] = f1; arrayOfFloat2[0] = f1;
/* 1594 */               arrayOfFloat3[1] = f2; arrayOfFloat2[1] = f2;
/* 1595 */               bool1 = true;
/* 1596 */               bool2 = false; break;
/*      */             } 
/* 1598 */             if (arrayOfFloat2[2] < 8.5070587E37F && arrayOfFloat2[2] > -8.5070587E37F && arrayOfFloat2[3] < 8.5070587E37F && arrayOfFloat2[3] > -8.5070587E37F) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1603 */               ProcessQuad(paramProcessHandler, arrayOfFloat2, arrayOfInt);
/*      */             } else {
/* 1605 */               ProcessLine(paramProcessHandler, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[4], arrayOfFloat2[5], arrayOfInt);
/*      */             } 
/*      */ 
/*      */             
/* 1609 */             arrayOfFloat2[0] = f1;
/* 1610 */             arrayOfFloat2[1] = f2;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 3:
/* 1615 */           arrayOfFloat2[2] = arrayOfFloat1[0] + paramFloat1;
/* 1616 */           arrayOfFloat2[3] = arrayOfFloat1[1] + paramFloat2;
/* 1617 */           arrayOfFloat2[4] = arrayOfFloat1[2] + paramFloat1;
/* 1618 */           arrayOfFloat2[5] = arrayOfFloat1[3] + paramFloat2;
/* 1619 */           f1 = arrayOfFloat2[6] = arrayOfFloat1[4] + paramFloat1;
/* 1620 */           f2 = arrayOfFloat2[7] = arrayOfFloat1[5] + paramFloat2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1630 */           if (f1 < 8.5070587E37F && f1 > -8.5070587E37F && f2 < 8.5070587E37F && f2 > -8.5070587E37F) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1635 */             if (bool2) {
/* 1636 */               arrayOfFloat3[0] = arrayOfFloat2[6]; arrayOfFloat2[0] = arrayOfFloat2[6];
/* 1637 */               arrayOfFloat3[1] = arrayOfFloat2[7]; arrayOfFloat2[1] = arrayOfFloat2[7];
/* 1638 */               bool1 = true;
/* 1639 */               bool2 = false; break;
/*      */             } 
/* 1641 */             if (arrayOfFloat2[2] < 8.5070587E37F && arrayOfFloat2[2] > -8.5070587E37F && arrayOfFloat2[3] < 8.5070587E37F && arrayOfFloat2[3] > -8.5070587E37F && arrayOfFloat2[4] < 8.5070587E37F && arrayOfFloat2[4] > -8.5070587E37F && arrayOfFloat2[5] < 8.5070587E37F && arrayOfFloat2[5] > -8.5070587E37F) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1650 */               ProcessCubic(paramProcessHandler, arrayOfFloat2, arrayOfInt);
/*      */             } else {
/* 1652 */               ProcessLine(paramProcessHandler, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[6], arrayOfFloat2[7], arrayOfInt);
/*      */             } 
/*      */ 
/*      */             
/* 1656 */             arrayOfFloat2[0] = f1;
/* 1657 */             arrayOfFloat2[1] = f2;
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 4:
/* 1662 */           if (bool1 && !bool2) {
/* 1663 */             bool2 = false;
/* 1664 */             if (arrayOfFloat2[0] != arrayOfFloat3[0] || arrayOfFloat2[1] != arrayOfFloat3[1]) {
/*      */ 
/*      */               
/* 1667 */               ProcessLine(paramProcessHandler, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat3[0], arrayOfFloat3[1], arrayOfInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1674 */               arrayOfFloat2[0] = arrayOfFloat3[0];
/* 1675 */               arrayOfFloat2[1] = arrayOfFloat3[1];
/*      */             } 
/* 1677 */             paramProcessHandler.processEndSubPath();
/*      */           } 
/*      */           break;
/*      */       } 
/* 1681 */       pathIterator.next();
/*      */     } 
/*      */ 
/*      */     
/* 1685 */     if ((bool1 & (!bool2 ? 1 : 0)) != 0) {
/* 1686 */       if (paramProcessHandler.clipMode == 1 && (
/* 1687 */         arrayOfFloat2[0] != arrayOfFloat3[0] || arrayOfFloat2[1] != arrayOfFloat3[1]))
/*      */       {
/*      */         
/* 1690 */         ProcessLine(paramProcessHandler, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat3[0], arrayOfFloat3[1], arrayOfInt);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1695 */       paramProcessHandler.processEndSubPath();
/*      */     } 
/* 1697 */     return true;
/*      */   }
/*      */   
/*      */   private static class Point { public int x;
/*      */     public int y;
/*      */     public boolean lastPoint;
/*      */     public Point prev;
/*      */     public Point next;
/*      */     public Point nextByY;
/*      */     public ProcessPath.Edge edge;
/*      */     
/*      */     public Point(int param1Int1, int param1Int2, boolean param1Boolean) {
/* 1709 */       this.x = param1Int1;
/* 1710 */       this.y = param1Int2;
/* 1711 */       this.lastPoint = param1Boolean;
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class Edge {
/*      */     int x;
/*      */     int dx;
/*      */     ProcessPath.Point p;
/*      */     int dir;
/*      */     Edge prev;
/*      */     Edge next;
/*      */     
/*      */     public Edge(ProcessPath.Point param1Point, int param1Int1, int param1Int2, int param1Int3) {
/* 1724 */       this.p = param1Point;
/* 1725 */       this.x = param1Int1;
/* 1726 */       this.dx = param1Int2;
/* 1727 */       this.dir = param1Int3;
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
/*      */   private static class FillData
/*      */   {
/* 1747 */     List<ProcessPath.Point> plgPnts = new Vector<>(256); public int plgYMin;
/*      */     public int plgYMax;
/*      */     
/*      */     public void addPoint(int param1Int1, int param1Int2, boolean param1Boolean) {
/* 1751 */       if (this.plgPnts.size() == 0) {
/* 1752 */         this.plgYMin = this.plgYMax = param1Int2;
/*      */       } else {
/* 1754 */         this.plgYMin = (this.plgYMin > param1Int2) ? param1Int2 : this.plgYMin;
/* 1755 */         this.plgYMax = (this.plgYMax < param1Int2) ? param1Int2 : this.plgYMax;
/*      */       } 
/*      */       
/* 1758 */       this.plgPnts.add(new ProcessPath.Point(param1Int1, param1Int2, param1Boolean));
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/* 1762 */       return (this.plgPnts.size() == 0);
/*      */     }
/*      */     
/*      */     public boolean isEnded() {
/* 1766 */       return ((ProcessPath.Point)this.plgPnts.get(this.plgPnts.size() - 1)).lastPoint;
/*      */     }
/*      */     
/*      */     public boolean setEnded() {
/* 1770 */       return ((ProcessPath.Point)this.plgPnts.get(this.plgPnts.size() - 1)).lastPoint = true;
/*      */     } }
/*      */   
/*      */   private static class ActiveEdgeList { ProcessPath.Edge head;
/*      */     
/*      */     private ActiveEdgeList() {}
/*      */     
/*      */     public boolean isEmpty() {
/* 1778 */       return (this.head == null);
/*      */     } public void insert(ProcessPath.Point param1Point, int param1Int) {
/*      */       int i2, i3, i4;
/*      */       boolean bool;
/* 1782 */       ProcessPath.Point point = param1Point.next;
/* 1783 */       int i = param1Point.x, j = param1Point.y;
/* 1784 */       int k = point.x, m = point.y;
/*      */       
/* 1786 */       if (j == m) {
/*      */         return;
/*      */       }
/*      */       
/* 1790 */       int n = k - i;
/* 1791 */       int i1 = m - j;
/*      */ 
/*      */       
/* 1794 */       if (j < m) {
/* 1795 */         i3 = i;
/* 1796 */         i4 = param1Int - j;
/* 1797 */         bool = true;
/*      */       } else {
/* 1799 */         i3 = k;
/* 1800 */         i4 = param1Int - m;
/* 1801 */         bool = true;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1809 */       if (n > 1048576.0F || n < -1048576.0F) {
/* 1810 */         i2 = (int)(n * 1024.0D / i1);
/* 1811 */         i3 += (int)(n * i4 / i1);
/*      */       } else {
/* 1813 */         i2 = (n << 10) / i1;
/* 1814 */         i3 += n * i4 / i1;
/*      */       } 
/*      */       
/* 1817 */       ProcessPath.Edge edge = new ProcessPath.Edge(param1Point, i3, i2, bool);
/*      */ 
/*      */       
/* 1820 */       edge.next = this.head;
/* 1821 */       edge.prev = null;
/* 1822 */       if (this.head != null) {
/* 1823 */         this.head.prev = edge;
/*      */       }
/* 1825 */       this.head = param1Point.edge = edge;
/*      */     }
/*      */     
/*      */     public void delete(ProcessPath.Edge param1Edge) {
/* 1829 */       ProcessPath.Edge edge1 = param1Edge.prev;
/* 1830 */       ProcessPath.Edge edge2 = param1Edge.next;
/* 1831 */       if (edge1 != null) {
/* 1832 */         edge1.next = edge2;
/*      */       } else {
/* 1834 */         this.head = edge2;
/*      */       } 
/* 1836 */       if (edge2 != null) {
/* 1837 */         edge2.prev = edge1;
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
/*      */     public void sort() {
/* 1852 */       ProcessPath.Edge edge3 = null;
/* 1853 */       boolean bool = true;
/*      */ 
/*      */ 
/*      */       
/* 1857 */       while (edge3 != this.head.next && bool) {
/* 1858 */         ProcessPath.Edge edge4 = this.head, edge6 = edge4;
/* 1859 */         ProcessPath.Edge edge5 = edge4.next;
/* 1860 */         bool = false;
/* 1861 */         while (edge4 != edge3) {
/* 1862 */           if (edge4.x >= edge5.x) {
/* 1863 */             bool = true;
/* 1864 */             if (edge4 == this.head) {
/* 1865 */               ProcessPath.Edge edge = edge5.next;
/* 1866 */               edge5.next = edge4;
/* 1867 */               edge4.next = edge;
/* 1868 */               this.head = edge5;
/* 1869 */               edge6 = edge5;
/*      */             } else {
/* 1871 */               ProcessPath.Edge edge = edge5.next;
/* 1872 */               edge5.next = edge4;
/* 1873 */               edge4.next = edge;
/* 1874 */               edge6.next = edge5;
/* 1875 */               edge6 = edge5;
/*      */             } 
/*      */           } else {
/* 1878 */             edge6 = edge4;
/* 1879 */             edge4 = edge4.next;
/*      */           } 
/* 1881 */           edge5 = edge4.next;
/* 1882 */           if (edge5 == edge3) edge3 = edge4;
/*      */         
/*      */         } 
/*      */       } 
/*      */       
/* 1887 */       ProcessPath.Edge edge1 = this.head;
/* 1888 */       ProcessPath.Edge edge2 = null;
/* 1889 */       while (edge1 != null) {
/* 1890 */         edge1.prev = edge2;
/* 1891 */         edge2 = edge1;
/* 1892 */         edge1 = edge1.next;
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void FillPolygon(FillProcessHandler paramFillProcessHandler, int paramInt) {
/* 1902 */     int k = paramFillProcessHandler.dhnd.xMax - 1;
/* 1903 */     FillData fillData = paramFillProcessHandler.fd;
/* 1904 */     int m = fillData.plgYMin;
/* 1905 */     int n = fillData.plgYMax;
/* 1906 */     int i1 = (n - m >> 10) + 4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1911 */     int i2 = m - 1 & 0xFFFFFC00;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1917 */     byte b2 = (paramInt == 1) ? -1 : 1;
/*      */ 
/*      */ 
/*      */     
/* 1921 */     List<Point> list = fillData.plgPnts;
/* 1922 */     int j = list.size();
/*      */     
/* 1924 */     if (j <= 1)
/*      */       return; 
/* 1926 */     Point[] arrayOfPoint = new Point[i1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1933 */     Point point1 = list.get(0);
/* 1934 */     point1.prev = null;
/* 1935 */     for (byte b3 = 0; b3 < j - 1; b3++) {
/* 1936 */       point1 = list.get(b3);
/* 1937 */       Point point = list.get(b3 + 1);
/* 1938 */       int i4 = point1.y - i2 - 1 >> 10;
/* 1939 */       point1.nextByY = arrayOfPoint[i4];
/* 1940 */       arrayOfPoint[i4] = point1;
/* 1941 */       point1.next = point;
/* 1942 */       point.prev = point1;
/*      */     } 
/*      */     
/* 1945 */     Point point2 = list.get(j - 1);
/* 1946 */     int i3 = point2.y - i2 - 1 >> 10;
/* 1947 */     point2.nextByY = arrayOfPoint[i3];
/* 1948 */     arrayOfPoint[i3] = point2;
/*      */     
/* 1950 */     ActiveEdgeList activeEdgeList = new ActiveEdgeList();
/*      */     
/* 1952 */     int i = i2 + 1024; byte b1 = 0;
/* 1953 */     for (; i <= n && b1 < i1; i += 1024, b1++) {
/*      */       
/* 1955 */       for (Point point = arrayOfPoint[b1]; point != null; point = point.nextByY) {
/*      */ 
/*      */ 
/*      */         
/* 1959 */         if (point.prev != null && !point.prev.lastPoint) {
/* 1960 */           if (point.prev.edge != null && point.prev.y <= i) {
/* 1961 */             activeEdgeList.delete(point.prev.edge);
/* 1962 */             point.prev.edge = null;
/* 1963 */           } else if (point.prev.y > i) {
/* 1964 */             activeEdgeList.insert(point.prev, i);
/*      */           } 
/*      */         }
/*      */         
/* 1968 */         if (!point.lastPoint && point.next != null) {
/* 1969 */           if (point.edge != null && point.next.y <= i) {
/* 1970 */             activeEdgeList.delete(point.edge);
/* 1971 */             point.edge = null;
/* 1972 */           } else if (point.next.y > i) {
/* 1973 */             activeEdgeList.insert(point, i);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1978 */       if (!activeEdgeList.isEmpty()) {
/*      */         
/* 1980 */         activeEdgeList.sort();
/*      */         
/* 1982 */         int i4 = 0;
/* 1983 */         boolean bool = false;
/*      */         
/* 1985 */         int i6 = paramFillProcessHandler.dhnd.xMin, i5 = i6;
/* 1986 */         Edge edge = activeEdgeList.head;
/* 1987 */         while (edge != null) {
/* 1988 */           i4 += edge.dir;
/* 1989 */           if ((i4 & b2) != 0 && !bool) {
/* 1990 */             i5 = edge.x + 1024 - 1 >> 10;
/* 1991 */             bool = true;
/*      */           } 
/*      */           
/* 1994 */           if ((i4 & b2) == 0 && bool) {
/* 1995 */             i6 = edge.x - 1 >> 10;
/* 1996 */             if (i5 <= i6) {
/* 1997 */               paramFillProcessHandler.dhnd.drawScanline(i5, i6, i >> 10);
/*      */             }
/* 1999 */             bool = false;
/*      */           } 
/*      */           
/* 2002 */           edge.x += edge.dx;
/* 2003 */           edge = edge.next;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2009 */         if (bool && i5 <= k)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 2014 */           paramFillProcessHandler.dhnd.drawScanline(i5, k, i >> 10);
/*      */         }
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
/*      */   private static class FillProcessHandler
/*      */     extends ProcessHandler
/*      */   {
/*      */     ProcessPath.FillData fd;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void processFixedLine(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int[] param1ArrayOfint, boolean param1Boolean1, boolean param1Boolean2) {
/* 2040 */       if (param1Boolean1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2046 */         int[] arrayOfInt = { param1Int1, param1Int2, param1Int3, param1Int4, 0, 0 };
/* 2047 */         int i = (int)(this.dhnd.xMinf * 1024.0F);
/* 2048 */         int j = (int)(this.dhnd.xMaxf * 1024.0F);
/* 2049 */         int k = (int)(this.dhnd.yMinf * 1024.0F);
/* 2050 */         int m = (int)(this.dhnd.yMaxf * 1024.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2055 */         int n = ProcessPath.TESTANDCLIP(k, m, arrayOfInt, 1, 0, 3, 2);
/* 2056 */         if (n == 4) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2061 */         n = ProcessPath.TESTANDCLIP(k, m, arrayOfInt, 3, 2, 1, 0);
/* 2062 */         if (n == 4)
/* 2063 */           return;  boolean bool = ProcessPath.IS_CLIPPED(n);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2070 */         n = ProcessPath.CLIPCLAMP(i, j, arrayOfInt, 0, 1, 2, 3, 4, 5);
/*      */ 
/*      */         
/* 2073 */         if (n == 0) {
/* 2074 */           processFixedLine(arrayOfInt[4], arrayOfInt[5], arrayOfInt[0], arrayOfInt[1], param1ArrayOfint, false, bool);
/*      */         
/*      */         }
/* 2077 */         else if (n == 4) {
/*      */           return;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2086 */         n = ProcessPath.CLIPCLAMP(i, j, arrayOfInt, 2, 3, 0, 1, 4, 5);
/*      */ 
/*      */         
/* 2089 */         bool = (bool || n == 1);
/*      */         
/* 2091 */         processFixedLine(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3], param1ArrayOfint, false, bool);
/*      */ 
/*      */ 
/*      */         
/* 2095 */         if (n == 0) {
/* 2096 */           processFixedLine(arrayOfInt[2], arrayOfInt[3], arrayOfInt[4], arrayOfInt[5], param1ArrayOfint, false, bool);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2106 */       if (this.fd.isEmpty() || this.fd.isEnded()) {
/* 2107 */         this.fd.addPoint(param1Int1, param1Int2, false);
/*      */       }
/*      */       
/* 2110 */       this.fd.addPoint(param1Int3, param1Int4, false);
/*      */       
/* 2112 */       if (param1Boolean2) {
/* 2113 */         this.fd.setEnded();
/*      */       }
/*      */     }
/*      */     
/*      */     FillProcessHandler(ProcessPath.DrawHandler param1DrawHandler) {
/* 2118 */       super(param1DrawHandler, 1);
/* 2119 */       this.fd = new ProcessPath.FillData();
/*      */     }
/*      */     
/*      */     public void processEndSubPath() {
/* 2123 */       if (!this.fd.isEmpty())
/* 2124 */         this.fd.setEnded(); 
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface EndSubPathHandler {
/*      */     void processEndSubPath();
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/ProcessPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */