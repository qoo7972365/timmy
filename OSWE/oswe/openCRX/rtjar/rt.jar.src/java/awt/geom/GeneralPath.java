/*     */ package java.awt.geom;
/*     */ 
/*     */ import java.awt.Shape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GeneralPath
/*     */   extends Path2D.Float
/*     */ {
/*     */   private static final long serialVersionUID = -8327096662768731142L;
/*     */   
/*     */   public GeneralPath() {
/*  59 */     super(1, 20);
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
/*     */   public GeneralPath(int paramInt) {
/*  73 */     super(paramInt, 20);
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
/*     */   public GeneralPath(int paramInt1, int paramInt2) {
/*  92 */     super(paramInt1, paramInt2);
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
/*     */   public GeneralPath(Shape paramShape) {
/* 105 */     super(paramShape, (AffineTransform)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GeneralPath(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, float[] paramArrayOffloat, int paramInt3) {
/* 116 */     this.windingRule = paramInt1;
/* 117 */     this.pointTypes = paramArrayOfbyte;
/* 118 */     this.numTypes = paramInt2;
/* 119 */     this.floatCoords = paramArrayOffloat;
/* 120 */     this.numCoords = paramInt3;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/geom/GeneralPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */