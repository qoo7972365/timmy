/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
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
/*     */ public class AAShapePipe
/*     */   implements ShapeDrawPipe, ParallelogramPipe
/*     */ {
/*  46 */   static RenderingEngine renderengine = RenderingEngine.getInstance();
/*     */   CompositePipe outpipe;
/*     */   private static byte[] theTile;
/*     */   
/*     */   public AAShapePipe(CompositePipe paramCompositePipe) {
/*  51 */     this.outpipe = paramCompositePipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*     */     BasicStroke basicStroke;
/*  57 */     if (paramSunGraphics2D.stroke instanceof BasicStroke) {
/*  58 */       basicStroke = (BasicStroke)paramSunGraphics2D.stroke;
/*     */     } else {
/*  60 */       paramShape = paramSunGraphics2D.stroke.createStrokedShape(paramShape);
/*  61 */       basicStroke = null;
/*     */     } 
/*     */     
/*  64 */     renderPath(paramSunGraphics2D, paramShape, basicStroke);
/*     */   }
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*  68 */     renderPath(paramSunGraphics2D, paramShape, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Rectangle2D computeBBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  74 */     if ((paramDouble3 -= paramDouble1) < 0.0D) {
/*  75 */       paramDouble1 += paramDouble3;
/*  76 */       paramDouble3 = -paramDouble3;
/*     */     } 
/*  78 */     if ((paramDouble4 -= paramDouble2) < 0.0D) {
/*  79 */       paramDouble2 += paramDouble4;
/*  80 */       paramDouble4 = -paramDouble4;
/*     */     } 
/*  82 */     return new Rectangle2D.Double(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10) {
/*  92 */     Region region = paramSunGraphics2D.getCompClip();
/*  93 */     int[] arrayOfInt = new int[4];
/*     */     
/*  95 */     AATileGenerator aATileGenerator = renderengine.getAATileGenerator(paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, 0.0D, 0.0D, region, arrayOfInt);
/*     */     
/*  97 */     if (aATileGenerator == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 102 */     renderTiles(paramSunGraphics2D, computeBBox(paramDouble1, paramDouble2, paramDouble3, paramDouble4), aATileGenerator, arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12) {
/* 113 */     Region region = paramSunGraphics2D.getCompClip();
/* 114 */     int[] arrayOfInt = new int[4];
/*     */     
/* 116 */     AATileGenerator aATileGenerator = renderengine.getAATileGenerator(paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12, region, arrayOfInt);
/*     */     
/* 118 */     if (aATileGenerator == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     renderTiles(paramSunGraphics2D, computeBBox(paramDouble1, paramDouble2, paramDouble3, paramDouble4), aATileGenerator, arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized byte[] getAlphaTile(int paramInt) {
/* 131 */     byte[] arrayOfByte = theTile;
/* 132 */     if (arrayOfByte == null || arrayOfByte.length < paramInt) {
/* 133 */       arrayOfByte = new byte[paramInt];
/*     */     } else {
/* 135 */       theTile = null;
/*     */     } 
/* 137 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   private static synchronized void dropAlphaTile(byte[] paramArrayOfbyte) {
/* 141 */     theTile = paramArrayOfbyte;
/*     */   }
/*     */   
/*     */   public void renderPath(SunGraphics2D paramSunGraphics2D, Shape paramShape, BasicStroke paramBasicStroke) {
/* 145 */     boolean bool1 = (paramBasicStroke != null && paramSunGraphics2D.strokeHint != 2) ? true : false;
/*     */     
/* 147 */     boolean bool2 = (paramSunGraphics2D.strokeState <= 1) ? true : false;
/*     */     
/* 149 */     Region region = paramSunGraphics2D.getCompClip();
/* 150 */     int[] arrayOfInt = new int[4];
/*     */     
/* 152 */     AATileGenerator aATileGenerator = renderengine.getAATileGenerator(paramShape, paramSunGraphics2D.transform, region, paramBasicStroke, bool2, bool1, arrayOfInt);
/*     */     
/* 154 */     if (aATileGenerator == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 159 */     renderTiles(paramSunGraphics2D, paramShape, aATileGenerator, arrayOfInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTiles(SunGraphics2D paramSunGraphics2D, Shape paramShape, AATileGenerator paramAATileGenerator, int[] paramArrayOfint) {
/* 165 */     Object object = null;
/* 166 */     byte[] arrayOfByte = null;
/*     */     try {
/* 168 */       object = this.outpipe.startSequence(paramSunGraphics2D, paramShape, new Rectangle(paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[2] - paramArrayOfint[0], paramArrayOfint[3] - paramArrayOfint[1]), paramArrayOfint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       int i = paramAATileGenerator.getTileWidth();
/* 175 */       int j = paramAATileGenerator.getTileHeight();
/* 176 */       arrayOfByte = getAlphaTile(i * j);
/*     */       
/*     */       int k;
/*     */       
/* 180 */       for (k = paramArrayOfint[1]; k < paramArrayOfint[3]; k += j) {
/* 181 */         int m; for (m = paramArrayOfint[0]; m < paramArrayOfint[2]; m += i) {
/* 182 */           int n = Math.min(i, paramArrayOfint[2] - m);
/* 183 */           int i1 = Math.min(j, paramArrayOfint[3] - k);
/*     */           
/* 185 */           int i2 = paramAATileGenerator.getTypicalAlpha();
/* 186 */           if (i2 == 0 || 
/* 187 */             !this.outpipe.needTile(object, m, k, n, i1)) {
/*     */             
/* 189 */             paramAATileGenerator.nextTile();
/* 190 */             this.outpipe.skipTile(object, m, k);
/*     */           } else {
/*     */             byte[] arrayOfByte1;
/* 193 */             if (i2 == 255) {
/* 194 */               arrayOfByte1 = null;
/* 195 */               paramAATileGenerator.nextTile();
/*     */             } else {
/* 197 */               arrayOfByte1 = arrayOfByte;
/* 198 */               paramAATileGenerator.getAlpha(arrayOfByte, 0, i);
/*     */             } 
/*     */             
/* 201 */             this.outpipe.renderPathTile(object, arrayOfByte1, 0, i, m, k, n, i1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 206 */       paramAATileGenerator.dispose();
/* 207 */       if (object != null) {
/* 208 */         this.outpipe.endSequence(object);
/*     */       }
/* 210 */       if (arrayOfByte != null)
/* 211 */         dropAlphaTile(arrayOfByte); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/AAShapePipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */