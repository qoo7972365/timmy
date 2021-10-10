/*     */ package sun.java2d.loops;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Color;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import sun.awt.SunHints;
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
/*     */ 
/*     */ 
/*     */ public final class GraphicsPrimitiveMgr
/*     */ {
/*     */   private static final boolean debugTrace = false;
/*     */   private static GraphicsPrimitive[] primitives;
/*     */   private static GraphicsPrimitive[] generalPrimitives;
/*     */   private static boolean needssort = true;
/*     */   
/*     */   static {
/*  56 */     initIDs(GraphicsPrimitive.class, SurfaceType.class, CompositeType.class, SunGraphics2D.class, Color.class, AffineTransform.class, XORComposite.class, AlphaComposite.class, Path2D.class, Path2D.Float.class, SunHints.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     CustomComponent.register();
/*  68 */     GeneralRenderer.register();
/*  69 */     registerNativeLoops();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static Comparator primSorter = new Comparator() {
/*     */       public int compare(Object param1Object1, Object param1Object2) {
/*  78 */         int i = ((GraphicsPrimitive)param1Object1).getUniqueID();
/*  79 */         int j = ((GraphicsPrimitive)param1Object2).getUniqueID();
/*     */         
/*  81 */         return (i == j) ? 0 : ((i < j) ? -1 : 1);
/*     */       }
/*     */     }; private static class PrimitiveSpec {
/*     */     public int uniqueID; private PrimitiveSpec() {} }
/*  85 */   private static Comparator primFinder = new Comparator() {
/*     */       public int compare(Object param1Object1, Object param1Object2) {
/*  87 */         int i = ((GraphicsPrimitive)param1Object1).getUniqueID();
/*  88 */         int j = ((GraphicsPrimitiveMgr.PrimitiveSpec)param1Object2).uniqueID;
/*     */         
/*  90 */         return (i == j) ? 0 : ((i < j) ? -1 : 1);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void register(GraphicsPrimitive[] paramArrayOfGraphicsPrimitive) {
/* 102 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive1 = primitives;
/* 103 */     int i = 0;
/* 104 */     int j = paramArrayOfGraphicsPrimitive.length;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (arrayOfGraphicsPrimitive1 != null) {
/* 112 */       i = arrayOfGraphicsPrimitive1.length;
/*     */     }
/* 114 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive2 = new GraphicsPrimitive[i + j];
/* 115 */     if (arrayOfGraphicsPrimitive1 != null) {
/* 116 */       System.arraycopy(arrayOfGraphicsPrimitive1, 0, arrayOfGraphicsPrimitive2, 0, i);
/*     */     }
/* 118 */     System.arraycopy(paramArrayOfGraphicsPrimitive, 0, arrayOfGraphicsPrimitive2, i, j);
/* 119 */     needssort = true;
/* 120 */     primitives = arrayOfGraphicsPrimitive2;
/*     */   }
/*     */   
/*     */   public static synchronized void registerGeneral(GraphicsPrimitive paramGraphicsPrimitive) {
/* 124 */     if (generalPrimitives == null) {
/* 125 */       generalPrimitives = new GraphicsPrimitive[] { paramGraphicsPrimitive };
/*     */       return;
/*     */     } 
/* 128 */     int i = generalPrimitives.length;
/* 129 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = new GraphicsPrimitive[i + 1];
/* 130 */     System.arraycopy(generalPrimitives, 0, arrayOfGraphicsPrimitive, 0, i);
/* 131 */     arrayOfGraphicsPrimitive[i] = paramGraphicsPrimitive;
/* 132 */     generalPrimitives = arrayOfGraphicsPrimitive;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized GraphicsPrimitive locate(int paramInt, SurfaceType paramSurfaceType) {
/* 138 */     return locate(paramInt, SurfaceType.OpaqueColor, CompositeType.Src, paramSurfaceType);
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
/*     */   public static synchronized GraphicsPrimitive locate(int paramInt, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 156 */     GraphicsPrimitive graphicsPrimitive = locatePrim(paramInt, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*     */ 
/*     */     
/* 159 */     if (graphicsPrimitive == null) {
/*     */       
/* 161 */       graphicsPrimitive = locateGeneral(paramInt);
/* 162 */       if (graphicsPrimitive != null) {
/* 163 */         graphicsPrimitive = graphicsPrimitive.makePrimitive(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/* 164 */         if (graphicsPrimitive != null && GraphicsPrimitive.traceflags != 0) {
/* 165 */           graphicsPrimitive = graphicsPrimitive.traceWrap();
/*     */         }
/*     */       } 
/*     */     } 
/* 169 */     return graphicsPrimitive;
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
/*     */   public static synchronized GraphicsPrimitive locatePrim(int paramInt, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/* 188 */     PrimitiveSpec primitiveSpec = new PrimitiveSpec();
/*     */     
/* 190 */     for (SurfaceType surfaceType = paramSurfaceType2; surfaceType != null; surfaceType = surfaceType.getSuperType()) {
/* 191 */       for (SurfaceType surfaceType1 = paramSurfaceType1; surfaceType1 != null; surfaceType1 = surfaceType1.getSuperType()) {
/* 192 */         for (CompositeType compositeType = paramCompositeType; compositeType != null; compositeType = compositeType.getSuperType()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 201 */           primitiveSpec
/* 202 */             .uniqueID = GraphicsPrimitive.makeUniqueID(paramInt, surfaceType1, compositeType, surfaceType);
/* 203 */           GraphicsPrimitive graphicsPrimitive = locate(primitiveSpec);
/* 204 */           if (graphicsPrimitive != null)
/*     */           {
/* 206 */             return graphicsPrimitive;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 211 */     return null;
/*     */   }
/*     */   
/*     */   private static GraphicsPrimitive locateGeneral(int paramInt) {
/* 215 */     if (generalPrimitives == null) {
/* 216 */       return null;
/*     */     }
/* 218 */     for (byte b = 0; b < generalPrimitives.length; b++) {
/* 219 */       GraphicsPrimitive graphicsPrimitive = generalPrimitives[b];
/* 220 */       if (graphicsPrimitive.getPrimTypeID() == paramInt) {
/* 221 */         return graphicsPrimitive;
/*     */       }
/*     */     } 
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static GraphicsPrimitive locate(PrimitiveSpec paramPrimitiveSpec) {
/* 229 */     if (needssort) {
/* 230 */       if (GraphicsPrimitive.traceflags != 0) {
/* 231 */         for (byte b = 0; b < primitives.length; b++) {
/* 232 */           primitives[b] = primitives[b].traceWrap();
/*     */         }
/*     */       }
/* 235 */       Arrays.sort(primitives, primSorter);
/* 236 */       needssort = false;
/*     */     } 
/* 238 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = primitives;
/* 239 */     if (arrayOfGraphicsPrimitive == null) {
/* 240 */       return null;
/*     */     }
/* 242 */     int i = Arrays.binarySearch((Object[])arrayOfGraphicsPrimitive, paramPrimitiveSpec, primFinder);
/* 243 */     if (i >= 0) {
/* 244 */       GraphicsPrimitive graphicsPrimitive = arrayOfGraphicsPrimitive[i];
/* 245 */       if (graphicsPrimitive instanceof GraphicsPrimitiveProxy) {
/* 246 */         graphicsPrimitive = ((GraphicsPrimitiveProxy)graphicsPrimitive).instantiate();
/* 247 */         arrayOfGraphicsPrimitive[i] = graphicsPrimitive;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 255 */       return graphicsPrimitive;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void writeLog(String paramString) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void testPrimitiveInstantiation() {
/* 280 */     testPrimitiveInstantiation(false);
/*     */   }
/*     */   
/*     */   public static void testPrimitiveInstantiation(boolean paramBoolean) {
/* 284 */     byte b1 = 0;
/* 285 */     byte b2 = 0;
/* 286 */     GraphicsPrimitive[] arrayOfGraphicsPrimitive = primitives;
/* 287 */     for (byte b3 = 0; b3 < arrayOfGraphicsPrimitive.length; b3++) {
/* 288 */       GraphicsPrimitive graphicsPrimitive = arrayOfGraphicsPrimitive[b3];
/* 289 */       if (graphicsPrimitive instanceof GraphicsPrimitiveProxy) {
/* 290 */         GraphicsPrimitive graphicsPrimitive1 = ((GraphicsPrimitiveProxy)graphicsPrimitive).instantiate();
/* 291 */         if (!graphicsPrimitive1.getSignature().equals(graphicsPrimitive.getSignature()) || graphicsPrimitive1
/* 292 */           .getUniqueID() != graphicsPrimitive.getUniqueID()) {
/* 293 */           System.out.println("r.getSignature == " + graphicsPrimitive1.getSignature());
/* 294 */           System.out.println("r.getUniqueID == " + graphicsPrimitive1.getUniqueID());
/* 295 */           System.out.println("p.getSignature == " + graphicsPrimitive.getSignature());
/* 296 */           System.out.println("p.getUniqueID == " + graphicsPrimitive.getUniqueID());
/* 297 */           throw new RuntimeException("Primitive " + graphicsPrimitive + " returns wrong signature for " + graphicsPrimitive1
/*     */               
/* 299 */               .getClass());
/*     */         } 
/*     */         
/* 302 */         b2++;
/* 303 */         graphicsPrimitive = graphicsPrimitive1;
/* 304 */         if (paramBoolean) {
/* 305 */           System.out.println(graphicsPrimitive);
/*     */         }
/*     */       } else {
/* 308 */         if (paramBoolean) {
/* 309 */           System.out.println(graphicsPrimitive + " (not proxied).");
/*     */         }
/* 311 */         b1++;
/*     */       } 
/*     */     } 
/* 314 */     System.out.println(b1 + " graphics primitives were not proxied.");
/*     */     
/* 316 */     System.out.println(b2 + " proxied graphics primitives resolved correctly.");
/*     */     
/* 318 */     System.out.println((b1 + b2) + " total graphics primitives");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 324 */     if (needssort) {
/* 325 */       Arrays.sort(primitives, primSorter);
/* 326 */       needssort = false;
/*     */     } 
/* 328 */     testPrimitiveInstantiation((paramArrayOfString.length > 0));
/*     */   }
/*     */   
/*     */   private static native void initIDs(Class paramClass1, Class paramClass2, Class paramClass3, Class paramClass4, Class paramClass5, Class paramClass6, Class paramClass7, Class paramClass8, Class paramClass9, Class paramClass10, Class paramClass11);
/*     */   
/*     */   private static native void registerNativeLoops();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/GraphicsPrimitiveMgr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */