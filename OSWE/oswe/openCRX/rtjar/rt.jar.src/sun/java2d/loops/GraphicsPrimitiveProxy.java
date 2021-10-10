/*     */ package sun.java2d.loops;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GraphicsPrimitiveProxy
/*     */   extends GraphicsPrimitive
/*     */ {
/*     */   private Class owner;
/*     */   private String relativeClassName;
/*     */   
/*     */   public GraphicsPrimitiveProxy(Class paramClass, String paramString1, String paramString2, int paramInt, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  63 */     super(paramString2, paramInt, paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  64 */     this.owner = paramClass;
/*  65 */     this.relativeClassName = paramString1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GraphicsPrimitive makePrimitive(SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  72 */     throw new InternalError("makePrimitive called on a Proxy!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GraphicsPrimitive instantiate() {
/*  80 */     String str = getPackageName(this.owner.getName()) + "." + this.relativeClassName;
/*     */     
/*     */     try {
/*  83 */       Class<?> clazz = Class.forName(str);
/*  84 */       GraphicsPrimitive graphicsPrimitive = (GraphicsPrimitive)clazz.newInstance();
/*  85 */       if (!satisfiesSameAs(graphicsPrimitive)) {
/*  86 */         throw new RuntimeException("Primitive " + graphicsPrimitive + " incompatible with proxy for " + str);
/*     */       }
/*     */ 
/*     */       
/*  90 */       return graphicsPrimitive;
/*  91 */     } catch (ClassNotFoundException classNotFoundException) {
/*  92 */       throw new RuntimeException(classNotFoundException.toString());
/*  93 */     } catch (InstantiationException instantiationException) {
/*  94 */       throw new RuntimeException(instantiationException.toString());
/*  95 */     } catch (IllegalAccessException illegalAccessException) {
/*  96 */       throw new RuntimeException(illegalAccessException.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getPackageName(String paramString) {
/* 104 */     int i = paramString.lastIndexOf('.');
/* 105 */     if (i < 0) {
/* 106 */       return paramString;
/*     */     }
/* 108 */     return paramString.substring(0, i);
/*     */   }
/*     */   
/*     */   public GraphicsPrimitive traceWrap() {
/* 112 */     return instantiate().traceWrap();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/GraphicsPrimitiveProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */