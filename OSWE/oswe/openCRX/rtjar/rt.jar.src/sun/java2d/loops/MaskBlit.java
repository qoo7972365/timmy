/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.Composite;
/*     */ import java.lang.ref.WeakReference;
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
/*     */ public class MaskBlit
/*     */   extends GraphicsPrimitive
/*     */ {
/*  51 */   public static final String methodSignature = "MaskBlit(...)".toString();
/*     */   
/*  53 */   public static final int primTypeID = makePrimTypeID();
/*     */   
/*  55 */   private static RenderCache blitcache = new RenderCache(20);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaskBlit locate(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  61 */     return 
/*  62 */       (MaskBlit)GraphicsPrimitiveMgr.locate(primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MaskBlit getFromCache(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  70 */     Object object = blitcache.get(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  71 */     if (object != null) {
/*  72 */       return (MaskBlit)object;
/*     */     }
/*  74 */     MaskBlit maskBlit = locate(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  75 */     if (maskBlit == null) {
/*  76 */       System.out.println("mask blit loop not found for:");
/*  77 */       System.out.println("src:  " + paramSurfaceType1);
/*  78 */       System.out.println("comp: " + paramCompositeType);
/*  79 */       System.out.println("dst:  " + paramSurfaceType2);
/*     */     } else {
/*  81 */       blitcache.put(paramSurfaceType1, paramCompositeType, paramSurfaceType2, maskBlit);
/*     */     } 
/*  83 */     return maskBlit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MaskBlit(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  90 */     super(methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaskBlit(long paramLong, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  98 */     super(paramLong, methodSignature, primTypeID, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
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
/* 112 */     GraphicsPrimitiveMgr.registerGeneral(new MaskBlit(null, null, null));
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
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 127 */     if (CompositeType.Xor.equals(paramCompositeType)) {
/* 128 */       throw new InternalError("Cannot construct MaskBlit for XOR mode");
/*     */     }
/*     */ 
/*     */     
/* 132 */     General general = new General(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/* 133 */     setupGeneralBinaryOp(general);
/* 134 */     return general;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class General
/*     */     extends MaskBlit
/*     */     implements GraphicsPrimitive.GeneralBinaryOp
/*     */   {
/*     */     Blit convertsrc;
/*     */     
/*     */     Blit convertdst;
/*     */     
/*     */     MaskBlit performop;
/*     */     
/*     */     Blit convertresult;
/*     */     WeakReference srcTmp;
/*     */     WeakReference dstTmp;
/*     */     
/*     */     public General(SurfaceType param1SurfaceType1, CompositeType param1CompositeType, SurfaceType param1SurfaceType2) {
/* 153 */       super(param1SurfaceType1, param1CompositeType, param1SurfaceType2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPrimitives(Blit param1Blit1, Blit param1Blit2, GraphicsPrimitive param1GraphicsPrimitive, Blit param1Blit3) {
/* 161 */       this.convertsrc = param1Blit1;
/* 162 */       this.convertdst = param1Blit2;
/* 163 */       this.performop = (MaskBlit)param1GraphicsPrimitive;
/* 164 */       this.convertresult = param1Blit3;
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
/*     */     public synchronized void MaskBlit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, byte[] param1ArrayOfbyte, int param1Int7, int param1Int8) {
/*     */       SurfaceData surfaceData1, surfaceData2;
/*     */       Region region;
/*     */       boolean bool1, bool2, bool3, bool4;
/* 180 */       if (this.convertsrc == null) {
/* 181 */         surfaceData1 = param1SurfaceData1;
/* 182 */         bool1 = param1Int1;
/* 183 */         bool2 = param1Int2;
/*     */       } else {
/* 185 */         SurfaceData surfaceData = null;
/* 186 */         if (this.srcTmp != null) {
/* 187 */           surfaceData = this.srcTmp.get();
/*     */         }
/* 189 */         surfaceData1 = convertFrom(this.convertsrc, param1SurfaceData1, param1Int1, param1Int2, param1Int5, param1Int6, surfaceData);
/*     */         
/* 191 */         bool1 = false;
/* 192 */         bool2 = false;
/* 193 */         if (surfaceData1 != surfaceData) {
/* 194 */           this.srcTmp = new WeakReference<>(surfaceData1);
/*     */         }
/*     */       } 
/*     */       
/* 198 */       if (this.convertdst == null) {
/* 199 */         surfaceData2 = param1SurfaceData2;
/* 200 */         bool3 = param1Int3;
/* 201 */         bool4 = param1Int4;
/* 202 */         region = param1Region;
/*     */       } else {
/*     */         
/* 205 */         SurfaceData surfaceData = null;
/* 206 */         if (this.dstTmp != null) {
/* 207 */           surfaceData = this.dstTmp.get();
/*     */         }
/* 209 */         surfaceData2 = convertFrom(this.convertdst, param1SurfaceData2, param1Int3, param1Int4, param1Int5, param1Int6, surfaceData);
/*     */         
/* 211 */         bool3 = false;
/* 212 */         bool4 = false;
/* 213 */         region = null;
/* 214 */         if (surfaceData2 != surfaceData) {
/* 215 */           this.dstTmp = new WeakReference<>(surfaceData2);
/*     */         }
/*     */       } 
/*     */       
/* 219 */       this.performop.MaskBlit(surfaceData1, surfaceData2, param1Composite, region, bool1, bool2, bool3, bool4, param1Int5, param1Int6, param1ArrayOfbyte, param1Int7, param1Int8);
/*     */ 
/*     */ 
/*     */       
/* 223 */       if (this.convertresult != null)
/*     */       {
/* 225 */         convertTo(this.convertresult, surfaceData2, param1SurfaceData2, param1Region, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 232 */     return new TraceMaskBlit(this);
/*     */   }
/*     */   
/*     */   public native void MaskBlit(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, Composite paramComposite, Region paramRegion, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfbyte, int paramInt7, int paramInt8);
/*     */   
/*     */   private static class TraceMaskBlit extends MaskBlit {
/*     */     MaskBlit target;
/*     */     
/*     */     public TraceMaskBlit(MaskBlit param1MaskBlit) {
/* 241 */       super(param1MaskBlit.getNativePrim(), param1MaskBlit
/* 242 */           .getSourceType(), param1MaskBlit
/* 243 */           .getCompositeType(), param1MaskBlit
/* 244 */           .getDestType());
/* 245 */       this.target = param1MaskBlit;
/*     */     }
/*     */     
/*     */     public GraphicsPrimitive traceWrap() {
/* 249 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void MaskBlit(SurfaceData param1SurfaceData1, SurfaceData param1SurfaceData2, Composite param1Composite, Region param1Region, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, byte[] param1ArrayOfbyte, int param1Int7, int param1Int8) {
/* 258 */       tracePrimitive(this.target);
/* 259 */       this.target.MaskBlit(param1SurfaceData1, param1SurfaceData2, param1Composite, param1Region, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6, param1ArrayOfbyte, param1Int7, param1Int8);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/MaskBlit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */