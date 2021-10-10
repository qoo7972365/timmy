/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
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
/*     */ public class SpanClipRenderer
/*     */   implements CompositePipe
/*     */ {
/*     */   CompositePipe outpipe;
/*  42 */   static Class RegionClass = Region.class;
/*  43 */   static Class RegionIteratorClass = RegionIterator.class;
/*     */   
/*     */   static {
/*  46 */     initIDs(RegionClass, RegionIteratorClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SpanClipRenderer(CompositePipe paramCompositePipe) {
/*  52 */     this.outpipe = paramCompositePipe;
/*     */   }
/*     */   
/*     */   class SCRcontext {
/*     */     RegionIterator iterator;
/*     */     Object outcontext;
/*     */     int[] band;
/*     */     byte[] tile;
/*     */     
/*     */     public SCRcontext(RegionIterator param1RegionIterator, Object param1Object) {
/*  62 */       this.iterator = param1RegionIterator;
/*  63 */       this.outcontext = param1Object;
/*  64 */       this.band = new int[4];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object startSequence(SunGraphics2D paramSunGraphics2D, Shape paramShape, Rectangle paramRectangle, int[] paramArrayOfint) {
/*  70 */     RegionIterator regionIterator = paramSunGraphics2D.clipRegion.getIterator();
/*     */     
/*  72 */     return new SCRcontext(regionIterator, this.outpipe.startSequence(paramSunGraphics2D, paramShape, paramRectangle, paramArrayOfint));
/*     */   }
/*     */   
/*     */   public boolean needTile(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  76 */     SCRcontext sCRcontext = (SCRcontext)paramObject;
/*  77 */     return this.outpipe.needTile(sCRcontext.outcontext, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPathTile(Object paramObject, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, ShapeSpanIterator paramShapeSpanIterator) {
/*  84 */     renderPathTile(paramObject, paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderPathTile(Object paramObject, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  90 */     SCRcontext sCRcontext = (SCRcontext)paramObject;
/*  91 */     RegionIterator regionIterator = sCRcontext.iterator.createCopy();
/*  92 */     int[] arrayOfInt = sCRcontext.band;
/*  93 */     arrayOfInt[0] = paramInt3;
/*  94 */     arrayOfInt[1] = paramInt4;
/*  95 */     arrayOfInt[2] = paramInt3 + paramInt5;
/*  96 */     arrayOfInt[3] = paramInt4 + paramInt6;
/*  97 */     if (paramArrayOfbyte == null) {
/*  98 */       int i = paramInt5 * paramInt6;
/*  99 */       paramArrayOfbyte = sCRcontext.tile;
/* 100 */       if (paramArrayOfbyte != null && paramArrayOfbyte.length < i) {
/* 101 */         paramArrayOfbyte = null;
/*     */       }
/* 103 */       if (paramArrayOfbyte == null) {
/* 104 */         paramArrayOfbyte = new byte[i];
/* 105 */         sCRcontext.tile = paramArrayOfbyte;
/*     */       } 
/* 107 */       paramInt1 = 0;
/* 108 */       paramInt2 = paramInt5;
/* 109 */       fillTile(regionIterator, paramArrayOfbyte, paramInt1, paramInt2, arrayOfInt);
/*     */     } else {
/* 111 */       eraseTile(regionIterator, paramArrayOfbyte, paramInt1, paramInt2, arrayOfInt);
/*     */     } 
/*     */     
/* 114 */     if (arrayOfInt[2] > arrayOfInt[0] && arrayOfInt[3] > arrayOfInt[1]) {
/* 115 */       paramInt1 += (arrayOfInt[1] - paramInt4) * paramInt2 + arrayOfInt[0] - paramInt3;
/* 116 */       this.outpipe.renderPathTile(sCRcontext.outcontext, paramArrayOfbyte, paramInt1, paramInt2, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */     } 
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
/*     */   public void skipTile(Object paramObject, int paramInt1, int paramInt2) {
/* 133 */     SCRcontext sCRcontext = (SCRcontext)paramObject;
/* 134 */     this.outpipe.skipTile(sCRcontext.outcontext, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void endSequence(Object paramObject) {
/* 138 */     SCRcontext sCRcontext = (SCRcontext)paramObject;
/* 139 */     this.outpipe.endSequence(sCRcontext.outcontext);
/*     */   }
/*     */   
/*     */   static native void initIDs(Class paramClass1, Class paramClass2);
/*     */   
/*     */   public native void fillTile(RegionIterator paramRegionIterator, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int[] paramArrayOfint);
/*     */   
/*     */   public native void eraseTile(RegionIterator paramRegionIterator, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int[] paramArrayOfint);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/SpanClipRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */