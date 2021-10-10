/*     */ package sun.java2d.x11;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Transparency;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import sun.awt.X11GraphicsConfig;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.SurfaceDataProxy;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class X11SurfaceDataProxy
/*     */   extends SurfaceDataProxy
/*     */   implements Transparency
/*     */ {
/*     */   X11GraphicsConfig x11gc;
/*     */   
/*     */   public static SurfaceDataProxy createProxy(SurfaceData paramSurfaceData, X11GraphicsConfig paramX11GraphicsConfig) {
/*  54 */     if (paramSurfaceData instanceof X11SurfaceData)
/*     */     {
/*     */       
/*  57 */       return UNCACHED;
/*     */     }
/*     */     
/*  60 */     ColorModel colorModel = paramSurfaceData.getColorModel();
/*  61 */     int i = colorModel.getTransparency();
/*     */     
/*  63 */     if (i == 1)
/*  64 */       return new Opaque(paramX11GraphicsConfig); 
/*  65 */     if (i == 2) {
/*     */       
/*  67 */       if (colorModel instanceof java.awt.image.IndexColorModel && colorModel.getPixelSize() == 8) {
/*  68 */         return new Bitmask(paramX11GraphicsConfig);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  73 */       if (colorModel instanceof DirectColorModel) {
/*  74 */         DirectColorModel directColorModel = (DirectColorModel)colorModel;
/*     */ 
/*     */         
/*  77 */         int j = directColorModel.getRedMask() | directColorModel.getGreenMask() | directColorModel.getBlueMask();
/*  78 */         int k = directColorModel.getAlphaMask();
/*     */         
/*  80 */         if ((j & 0xFF000000) == 0 && (k & 0xFF000000) != 0)
/*     */         {
/*     */           
/*  83 */           return new Bitmask(paramX11GraphicsConfig);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  90 */     return UNCACHED;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public X11SurfaceDataProxy(X11GraphicsConfig paramX11GraphicsConfig) {
/*  96 */     this.x11gc = paramX11GraphicsConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SurfaceData validateSurfaceData(SurfaceData paramSurfaceData1, SurfaceData paramSurfaceData2, int paramInt1, int paramInt2) {
/* 104 */     if (paramSurfaceData2 == null) {
/*     */       
/*     */       try {
/* 107 */         paramSurfaceData2 = X11SurfaceData.createData(this.x11gc, paramInt1, paramInt2, this.x11gc
/* 108 */             .getColorModel(), null, 0L, 
/* 109 */             getTransparency());
/* 110 */       } catch (OutOfMemoryError outOfMemoryError) {}
/*     */     }
/*     */     
/* 113 */     return paramSurfaceData2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Opaque
/*     */     extends X11SurfaceDataProxy
/*     */   {
/*     */     public Opaque(X11GraphicsConfig param1X11GraphicsConfig) {
/* 122 */       super(param1X11GraphicsConfig);
/*     */     }
/*     */     
/*     */     public int getTransparency() {
/* 126 */       return 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSupportedOperation(SurfaceData param1SurfaceData, int param1Int, CompositeType param1CompositeType, Color param1Color) {
/* 135 */       return (param1Int < 3 && (CompositeType.SrcOverNoEa
/* 136 */         .equals(param1CompositeType) || CompositeType.SrcNoEa
/* 137 */         .equals(param1CompositeType)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Bitmask
/*     */     extends X11SurfaceDataProxy
/*     */   {
/*     */     public Bitmask(X11GraphicsConfig param1X11GraphicsConfig) {
/* 148 */       super(param1X11GraphicsConfig);
/*     */     }
/*     */     
/*     */     public int getTransparency() {
/* 152 */       return 2;
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
/*     */     
/*     */     public boolean isSupportedOperation(SurfaceData param1SurfaceData, int param1Int, CompositeType param1CompositeType, Color param1Color) {
/* 166 */       if (param1Int >= 3) {
/* 167 */         return false;
/*     */       }
/*     */       
/* 170 */       if (param1Color != null && param1Color
/* 171 */         .getTransparency() != 1)
/*     */       {
/* 173 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (CompositeType.SrcOverNoEa.equals(param1CompositeType) || (CompositeType.SrcNoEa
/* 181 */         .equals(param1CompositeType) && param1Color != null))
/*     */       {
/*     */         
/* 184 */         return true;
/*     */       }
/*     */       
/* 187 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/X11SurfaceDataProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */