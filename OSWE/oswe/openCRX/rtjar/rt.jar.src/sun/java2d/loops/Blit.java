/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.awt.CompositeContext;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.lang.ref.WeakReference;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.SpanIterator;
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
/*     */ public class Blit
/*     */   extends GraphicsPrimitive
/*     */ {
/*  53 */   public static final String methodSignature = "Blit(...)".toString();
/*     */   
/*  55 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  57 */   private static RenderCache blitcache = new RenderCache(20);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Blit locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  63 */     return 
/*  64 */       (Blit)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Blit getFromCache(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  72 */     Object object = blitcache.get(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  73 */     if (object != null) {
/*  74 */       return (Blit)object;
/*     */     }
/*     */     
/*  77 */     Blit blit = locate(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  78 */     if (blit == null) {
/*  79 */       System.out.println("blit loop not found for:");
/*  80 */       System.out.println("src:  " + paramSurfaceType1);
/*  81 */       System.out.println("comp: " + paramCompositeType);
/*  82 */       System.out.println("dst:  " + paramSurfaceType2);
/*     */     } else {
/*  84 */       blitcache.put(paramSurfaceType1, paramCompositeType, paramSurfaceType2, blit);
/*     */     } 
/*  86 */     return blit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Blit(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  93 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Blit(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 101 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/*     */   static {
/* 114 */     GraphicsPrimitiveMgr.registerGeneral(new Blit(null, null, null));
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
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 128 */     if (paramCompositeType.isDerivedFrom(CompositeType.Xor)) {
/* 129 */       GeneralXorBlit generalXorBlit = new GeneralXorBlit(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */ 
/*     */       
/* 132 */       setupGeneralBinaryOp(generalXorBlit);
/* 133 */       return generalXorBlit;
/* 134 */     }  if (paramCompositeType.isDerivedFrom(CompositeType.AnyAlpha)) {
/* 135 */       return new GeneralMaskBlit(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */     }
/* 137 */     return AnyBlit.instance;
/*     */   }
/*     */   
/*     */   private static class AnyBlit
/*     */     extends Blit {
/* 142 */     public static AnyBlit instance = new AnyBlit();
/*     */     
/*     */     public AnyBlit() {
/* 145 */       super(SurfaceType.Any, CompositeType.Any, SurfaceType.Any);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Blit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 156 */       ColorModel colorModel1 = param1SurfaceData1.getColorModel();
/* 157 */       ColorModel colorModel2 = param1SurfaceData2.getColorModel();
/*     */       
/* 159 */       CompositeContext compositeContext = param1Composite.createContext(colorModel1, colorModel2, new RenderingHints(null));
/*     */       
/* 161 */       Raster raster = param1SurfaceData1.getRaster(param1Int1, param1Int2, param1Int5, param1Int6);
/*     */       
/* 163 */       WritableRaster writableRaster = (WritableRaster)param1SurfaceData2.getRaster(param1Int3, param1Int4, param1Int5, param1Int6);
/*     */       
/* 165 */       if (param1Region == null) {
/* 166 */         param1Region = Region.getInstanceXYWH(param1Int3, param1Int4, param1Int5, param1Int6);
/*     */       }
/* 168 */       int[] arrayOfInt = { param1Int3, param1Int4, param1Int3 + param1Int5, param1Int4 + param1Int6 };
/* 169 */       SpanIterator spanIterator = param1Region.getSpanIterator(arrayOfInt);
/* 170 */       param1Int1 -= param1Int3;
/* 171 */       param1Int2 -= param1Int4;
/* 172 */       while (spanIterator.nextSpan(arrayOfInt)) {
/* 173 */         int i = arrayOfInt[2] - arrayOfInt[0];
/* 174 */         int j = arrayOfInt[3] - arrayOfInt[1];
/* 175 */         Raster raster1 = raster.createChild(param1Int1 + arrayOfInt[0], param1Int2 + arrayOfInt[1], i, j, 0, 0, null);
/*     */         
/* 177 */         WritableRaster writableRaster1 = writableRaster.createWritableChild(arrayOfInt[0], arrayOfInt[1], i, j, 0, 0, null);
/*     */         
/* 179 */         compositeContext.compose(raster1, writableRaster1, writableRaster1);
/*     */       } 
/* 181 */       compositeContext.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class GeneralMaskBlit
/*     */     extends Blit
/*     */   {
/*     */     MaskBlit performop;
/*     */     
/*     */     public GeneralMaskBlit(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/* 192 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/* 193 */       this.performop = MaskBlit.locate(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Blit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 204 */       this.performop.MaskBlit(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, null, 0, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class GeneralXorBlit
/*     */     extends Blit
/*     */     implements GraphicsPrimitive.GeneralBinaryOp
/*     */   {
/*     */     Blit convertsrc;
/*     */     
/*     */     Blit convertdst;
/*     */     
/*     */     Blit performop;
/*     */     
/*     */     Blit convertresult;
/*     */     
/*     */     WeakReference srcTmp;
/*     */     
/*     */     WeakReference dstTmp;
/*     */ 
/*     */     
/*     */     public GeneralXorBlit(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/* 227 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPrimitives(Blit param1Blit1, Blit param1Blit2, GraphicsPrimitive param1GraphicsPrimitive, Blit param1Blit3) {
/* 235 */       this.convertsrc = param1Blit1;
/* 236 */       this.convertdst = param1Blit2;
/* 237 */       this.performop = (Blit)param1GraphicsPrimitive;
/* 238 */       this.convertresult = param1Blit3;
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
/*     */     public synchronized void Blit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/*     */       SurfaceData surfaceData1, surfaceData2;
/*     */       Region region;
/*     */       boolean bool1, bool2, bool3, bool4;
/* 253 */       if (this.convertsrc == null) {
/* 254 */         surfaceData1 = param1SurfaceData1;
/* 255 */         bool1 = param1Int1;
/* 256 */         bool2 = param1Int2;
/*     */       } else {
/* 258 */         SurfaceData surfaceData = null;
/* 259 */         if (this.srcTmp != null) {
/* 260 */           surfaceData = this.srcTmp.get();
/*     */         }
/* 262 */         surfaceData1 = convertFrom(this.convertsrc, param1SurfaceData1, param1Int1, param1Int2, param1Int5, param1Int6, surfaceData);
/*     */         
/* 264 */         bool1 = false;
/* 265 */         bool2 = false;
/* 266 */         if (surfaceData1 != surfaceData) {
/* 267 */           this.srcTmp = new WeakReference<>(surfaceData1);
/*     */         }
/*     */       } 
/*     */       
/* 271 */       if (this.convertdst == null) {
/* 272 */         surfaceData2 = param1SurfaceData2;
/* 273 */         bool3 = param1Int3;
/* 274 */         bool4 = param1Int4;
/* 275 */         region = param1Region;
/*     */       } else {
/*     */         
/* 278 */         SurfaceData surfaceData = null;
/* 279 */         if (this.dstTmp != null) {
/* 280 */           surfaceData = this.dstTmp.get();
/*     */         }
/* 282 */         surfaceData2 = convertFrom(this.convertdst, param1SurfaceData2, param1Int3, param1Int4, param1Int5, param1Int6, surfaceData);
/*     */         
/* 284 */         bool3 = false;
/* 285 */         bool4 = false;
/* 286 */         region = null;
/* 287 */         if (surfaceData2 != surfaceData) {
/* 288 */           this.dstTmp = new WeakReference<>(surfaceData2);
/*     */         }
/*     */       } 
/*     */       
/* 292 */       this.performop.Blit(surfaceData1, surfaceData2, param1Composite, region, bool1, bool2, bool3, bool4, param1Int5, param1Int6);
/*     */ 
/*     */ 
/*     */       
/* 296 */       if (this.convertresult != null)
/*     */       {
/* 298 */         convertTo(this.convertresult, surfaceData2, param1SurfaceData2, param1Region, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 305 */     return new TraceBlit(this);
/*     */   }
/*     */   
/*     */   public native void Blit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */   
/*     */   private static class TraceBlit extends Blit {
/*     */     public TraceBlit(Blit param1Blit) {
/* 312 */       super(param1Blit.getSourceType(), param1Blit
/* 313 */           .getCompositeType(), param1Blit
/* 314 */           .getDestType());
/* 315 */       this.target = param1Blit;
/*     */     }
/*     */     Blit target;
/*     */     public GraphicsPrimitive traceWrap() {
/* 319 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void Blit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 327 */       tracePrimitive(this.target);
/* 328 */       this.target.Blit(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/Blit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */