/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Font;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ public class BlitBg
/*     */   extends GraphicsPrimitive
/*     */ {
/*  57 */   public static final String methodSignature = "BlitBg(...)".toString();
/*     */   
/*  59 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  61 */   private static RenderCache blitcache = new RenderCache(20);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlitBg locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  67 */     return 
/*  68 */       (BlitBg)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlitBg getFromCache(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  76 */     Object object = blitcache.get(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  77 */     if (object != null) {
/*  78 */       return (BlitBg)object;
/*     */     }
/*  80 */     BlitBg blitBg = locate(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  81 */     if (blitBg == null) {
/*  82 */       System.out.println("blitbg loop not found for:");
/*  83 */       System.out.println("src:  " + paramSurfaceType1);
/*  84 */       System.out.println("comp: " + paramCompositeType);
/*  85 */       System.out.println("dst:  " + paramSurfaceType2);
/*     */     } else {
/*  87 */       blitcache.put(paramSurfaceType1, paramCompositeType, paramSurfaceType2, blitBg);
/*     */     } 
/*  89 */     return blitBg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlitBg(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  96 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlitBg(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 104 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */   static {
/* 118 */     GraphicsPrimitiveMgr.registerGeneral(new BlitBg(null, null, null));
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
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 131 */     return new General(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class General
/*     */     extends BlitBg
/*     */   {
/*     */     CompositeType compositeType;
/*     */     
/*     */     public General(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/* 141 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/* 142 */       this.compositeType = param1CompositeType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void BlitBg(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 155 */       ColorModel colorModel = param1SurfaceData2.getColorModel();
/* 156 */       boolean bool = (param1Int1 >>> 24 != 255) ? true : false;
/* 157 */       if (!colorModel.hasAlpha() && bool) {
/* 158 */         colorModel = ColorModel.getRGBdefault();
/*     */       }
/*     */       
/* 161 */       WritableRaster writableRaster = colorModel.createCompatibleWritableRaster(param1Int6, param1Int7);
/* 162 */       boolean bool1 = colorModel.isAlphaPremultiplied();
/* 163 */       BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, bool1, null);
/*     */       
/* 165 */       SurfaceData surfaceData = BufImgSurfaceData.createData(bufferedImage);
/* 166 */       Color color = new Color(param1Int1, bool);
/* 167 */       SunGraphics2D sunGraphics2D = new SunGraphics2D(surfaceData, color, color, defaultFont);
/*     */       
/* 169 */       FillRect fillRect = FillRect.locate(SurfaceType.AnyColor, CompositeType.SrcNoEa, surfaceData
/*     */           
/* 171 */           .getSurfaceType());
/* 172 */       Blit blit1 = Blit.getFromCache(param1SurfaceData1.getSurfaceType(), CompositeType.SrcOverNoEa, surfaceData
/*     */           
/* 174 */           .getSurfaceType());
/* 175 */       Blit blit2 = Blit.getFromCache(surfaceData.getSurfaceType(), this.compositeType, param1SurfaceData2
/* 176 */           .getSurfaceType());
/* 177 */       fillRect.FillRect(sunGraphics2D, surfaceData, 0, 0, param1Int6, param1Int7);
/* 178 */       blit1.Blit(param1SurfaceData1, surfaceData, AlphaComposite.SrcOver, null, param1Int2, param1Int3, 0, 0, param1Int6, param1Int7);
/*     */       
/* 180 */       blit2.Blit(surfaceData, param1SurfaceData2, param1Composite, param1Region, 0, 0, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */ 
/*     */     
/* 184 */     private static Font defaultFont = new Font("Dialog", 0, 12);
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 188 */     return new TraceBlitBg(this);
/*     */   }
/*     */   
/*     */   public native void BlitBg(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*     */   
/*     */   private static class TraceBlitBg extends BlitBg {
/*     */     public TraceBlitBg(BlitBg param1BlitBg) {
/* 195 */       super(param1BlitBg.getSourceType(), param1BlitBg
/* 196 */           .getCompositeType(), param1BlitBg
/* 197 */           .getDestType());
/* 198 */       this.target = param1BlitBg;
/*     */     }
/*     */     BlitBg target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 202 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void BlitBg(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7) {
/* 212 */       tracePrimitive(this.target);
/* 213 */       this.target.BlitBg(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1Int7);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/BlitBg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */