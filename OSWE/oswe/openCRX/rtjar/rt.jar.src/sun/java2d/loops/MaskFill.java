/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.image.BufferedImage;
/*     */ import sun.awt.image.BufImgSurfaceData;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaskFill
/*     */   extends GraphicsPrimitive
/*     */ {
/*  53 */   public static final String methodSignature = "MaskFill(...)".toString();
/*  54 */   public static final String fillPgramSignature = "FillAAPgram(...)"
/*  55 */     .toString();
/*  56 */   public static final String drawPgramSignature = "DrawAAPgram(...)"
/*  57 */     .toString();
/*     */   
/*  59 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  61 */   private static RenderCache fillcache = new RenderCache(10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaskFill locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  67 */     return 
/*  68 */       (MaskFill)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaskFill locatePrim(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  76 */     return 
/*  77 */       (MaskFill)GraphicsPrimitiveMgr.locatePrim(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */   public static MaskFill getFromCache(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  89 */     Object object = fillcache.get(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  90 */     if (object != null) {
/*  91 */       return (MaskFill)object;
/*     */     }
/*  93 */     MaskFill maskFill = locatePrim(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  94 */     if (maskFill != null) {
/*  95 */       fillcache.put(paramSurfaceType1, paramCompositeType, paramSurfaceType2, maskFill);
/*     */     }
/*  97 */     return maskFill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MaskFill(String paramString, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 105 */     super(paramString, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MaskFill(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 112 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaskFill(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 120 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */   public boolean canDoParallelograms() {
/* 145 */     return (getNativePrim() != 0L);
/*     */   }
/*     */   
/*     */   static {
/* 149 */     GraphicsPrimitiveMgr.registerGeneral(new MaskFill(null, null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 156 */     if (SurfaceType.OpaqueColor.equals(paramSurfaceType1) || SurfaceType.AnyColor
/* 157 */       .equals(paramSurfaceType1)) {
/*     */       
/* 159 */       if (CompositeType.Xor.equals(paramCompositeType)) {
/* 160 */         throw new InternalError("Cannot construct MaskFill for XOR mode");
/*     */       }
/*     */       
/* 163 */       return new General(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */     } 
/*     */     
/* 166 */     throw new InternalError("MaskFill can only fill with colors");
/*     */   }
/*     */ 
/*     */   
/*     */   private static class General
/*     */     extends MaskFill
/*     */   {
/*     */     FillRect fillop;
/*     */     
/*     */     MaskBlit maskop;
/*     */     
/*     */     public General(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/* 178 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/* 179 */       this.fillop = FillRect.locate(param1SurfaceType1, CompositeType.SrcNoEa, SurfaceType.IntArgb);
/*     */ 
/*     */       
/* 182 */       this.maskop = MaskBlit.locate(SurfaceType.IntArgb, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void MaskFill(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, byte[] param1ArrayOfbyte, int param1Int5, int param1Int6) {
/* 191 */       BufferedImage bufferedImage = new BufferedImage(param1Int3, param1Int4, 2);
/*     */       
/* 193 */       SurfaceData surfaceData = BufImgSurfaceData.createData(bufferedImage);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       Region region = param1SunGraphics2D.clipRegion;
/* 199 */       param1SunGraphics2D.clipRegion = null;
/* 200 */       int i = param1SunGraphics2D.pixel;
/* 201 */       param1SunGraphics2D.pixel = surfaceData.pixelFor(param1SunGraphics2D.getColor());
/* 202 */       this.fillop.FillRect(param1SunGraphics2D, surfaceData, 0, 0, param1Int3, param1Int4);
/* 203 */       param1SunGraphics2D.pixel = i;
/* 204 */       param1SunGraphics2D.clipRegion = region;
/*     */       
/* 206 */       this.maskop.MaskBlit(surfaceData, param1SurfaceData, param1Composite, null, 0, 0, param1Int1, param1Int2, param1Int3, param1Int4, param1ArrayOfbyte, param1Int5, param1Int6);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 213 */     return new TraceMaskFill(this);
/*     */   }
/*     */   public native void MaskFill(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, Composite paramComposite, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, int paramInt5, int paramInt6);
/*     */   public native void FillAAPgram(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, Composite paramComposite, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   public native void DrawAAPgram(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, Composite paramComposite, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8);
/*     */   
/*     */   private static class TraceMaskFill extends MaskFill { MaskFill target;
/*     */     
/*     */     public TraceMaskFill(MaskFill param1MaskFill) {
/* 222 */       super(param1MaskFill.getSourceType(), param1MaskFill
/* 223 */           .getCompositeType(), param1MaskFill
/* 224 */           .getDestType());
/* 225 */       this.target = param1MaskFill;
/* 226 */       this
/*     */ 
/*     */         
/* 229 */         .fillPgramTarget = new MaskFill(fillPgramSignature, param1MaskFill.getSourceType(), param1MaskFill.getCompositeType(), param1MaskFill.getDestType());
/* 230 */       this
/*     */ 
/*     */         
/* 233 */         .drawPgramTarget = new MaskFill(drawPgramSignature, param1MaskFill.getSourceType(), param1MaskFill.getCompositeType(), param1MaskFill.getDestType());
/*     */     }
/*     */     MaskFill fillPgramTarget; MaskFill drawPgramTarget;
/*     */     public GraphicsPrimitive traceWrap() {
/* 237 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void MaskFill(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, Composite param1Composite, int param1Int1, int param1Int2, int param1Int3, int param1Int4, byte[] param1ArrayOfbyte, int param1Int5, int param1Int6) {
/* 245 */       tracePrimitive(this.target);
/* 246 */       this.target.MaskFill(param1SunGraphics2D, param1SurfaceData, param1Composite, param1Int1, param1Int2, param1Int3, param1Int4, param1ArrayOfbyte, param1Int5, param1Int6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void FillAAPgram(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, Composite param1Composite, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6) {
/* 256 */       tracePrimitive(this.fillPgramTarget);
/* 257 */       this.target.FillAAPgram(param1SunGraphics2D, param1SurfaceData, param1Composite, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void DrawAAPgram(SunGraphics2D param1SunGraphics2D, SurfaceData param1SurfaceData, Composite param1Composite, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8) {
/* 268 */       tracePrimitive(this.drawPgramTarget);
/* 269 */       this.target.DrawAAPgram(param1SunGraphics2D, param1SurfaceData, param1Composite, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canDoParallelograms() {
/* 274 */       return this.target.canDoParallelograms();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/MaskFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */