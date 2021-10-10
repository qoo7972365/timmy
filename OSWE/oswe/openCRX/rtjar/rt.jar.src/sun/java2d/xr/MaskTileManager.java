/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MaskTileManager
/*     */ {
/*     */   public static final int MASK_SIZE = 256;
/*  45 */   MaskTile mainTile = new MaskTile();
/*     */   
/*     */   ArrayList<MaskTile> tileList;
/*  48 */   int allocatedTiles = 0;
/*     */   
/*     */   int xTiles;
/*     */   int yTiles;
/*     */   XRCompositeManager xrMgr;
/*     */   XRBackend con;
/*     */   int maskPixmap;
/*     */   int maskPicture;
/*     */   long maskGC;
/*     */   
/*     */   public MaskTileManager(XRCompositeManager paramXRCompositeManager, int paramInt) {
/*  59 */     this.tileList = new ArrayList<>();
/*  60 */     this.xrMgr = paramXRCompositeManager;
/*  61 */     this.con = paramXRCompositeManager.getBackend();
/*     */     
/*  63 */     this.maskPixmap = this.con.createPixmap(paramInt, 8, 256, 256);
/*  64 */     this.maskPicture = this.con.createPicture(this.maskPixmap, 2);
/*  65 */     this.con.renderRectangle(this.maskPicture, (byte)0, new XRColor(Color.black), 0, 0, 256, 256);
/*     */ 
/*     */     
/*  68 */     this.maskGC = this.con.createGC(this.maskPixmap);
/*  69 */     this.con.setGCExposures(this.maskGC, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillMask(XRSurfaceData paramXRSurfaceData) {
/*  78 */     boolean bool1 = this.xrMgr.maskRequired();
/*  79 */     boolean bool2 = XRUtils.isMaskEvaluated(this.xrMgr.compRule);
/*     */     
/*  81 */     if (bool1 && bool2) {
/*  82 */       this.mainTile.calculateDirtyAreas();
/*  83 */       DirtyRegion dirtyRegion = this.mainTile.getDirtyArea().cloneRegion();
/*  84 */       this.mainTile.translate(-dirtyRegion.x, -dirtyRegion.y);
/*     */       
/*  86 */       XRColor xRColor = this.xrMgr.getMaskColor();
/*     */ 
/*     */       
/*  89 */       if (dirtyRegion.getWidth() <= 256 && dirtyRegion
/*  90 */         .getHeight() <= 256) {
/*     */         
/*  92 */         compositeSingleTile(paramXRSurfaceData, this.mainTile, dirtyRegion, bool1, 0, 0, xRColor);
/*     */       } else {
/*     */         
/*  95 */         allocTiles(dirtyRegion);
/*  96 */         tileRects();
/*     */         
/*  98 */         for (byte b = 0; b < this.yTiles; b++) {
/*  99 */           for (byte b1 = 0; b1 < this.xTiles; b1++) {
/* 100 */             MaskTile maskTile = this.tileList.get(b * this.xTiles + b1);
/*     */             
/* 102 */             int i = b1 * 256;
/* 103 */             int j = b * 256;
/* 104 */             compositeSingleTile(paramXRSurfaceData, maskTile, dirtyRegion, bool1, i, j, xRColor);
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 114 */     else if (this.xrMgr.isSolidPaintActive()) {
/* 115 */       this.xrMgr.XRRenderRectangles(paramXRSurfaceData, this.mainTile.getRects());
/*     */     } else {
/* 117 */       this.xrMgr.XRCompositeRectangles(paramXRSurfaceData, this.mainTile.getRects());
/*     */     } 
/*     */ 
/*     */     
/* 121 */     this.mainTile.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int uploadMask(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte) {
/* 128 */     int i = 0;
/*     */     
/* 130 */     if (paramArrayOfbyte != null) {
/*     */       
/* 132 */       float f = this.xrMgr.isTexturePaintActive() ? this.xrMgr.getExtraAlpha() : 1.0F;
/* 133 */       this.con.putMaskImage(this.maskPixmap, this.maskGC, paramArrayOfbyte, 0, 0, 0, 0, paramInt1, paramInt2, paramInt4, paramInt3, f);
/*     */       
/* 135 */       i = this.maskPicture;
/* 136 */     } else if (this.xrMgr.isTexturePaintActive()) {
/* 137 */       i = this.xrMgr.getExtraAlphaMask();
/*     */     } 
/*     */     
/* 140 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearUploadMask(int paramInt1, int paramInt2, int paramInt3) {
/* 147 */     if (paramInt1 == this.maskPicture) {
/* 148 */       this.con.renderRectangle(this.maskPicture, (byte)0, XRColor.NO_ALPHA, 0, 0, paramInt2, paramInt3);
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
/*     */   protected void compositeSingleTile(XRSurfaceData paramXRSurfaceData, MaskTile paramMaskTile, DirtyRegion paramDirtyRegion, boolean paramBoolean, int paramInt1, int paramInt2, XRColor paramXRColor) {
/* 163 */     if (paramMaskTile.rects.getSize() > 0) {
/* 164 */       DirtyRegion dirtyRegion = paramMaskTile.getDirtyArea();
/*     */       
/* 166 */       int i = dirtyRegion.x + paramInt1 + paramDirtyRegion.x;
/* 167 */       int j = dirtyRegion.y + paramInt2 + paramDirtyRegion.y;
/* 168 */       int k = dirtyRegion.x2 - dirtyRegion.x;
/* 169 */       int m = dirtyRegion.y2 - dirtyRegion.y;
/* 170 */       k = Math.min(k, 256);
/* 171 */       m = Math.min(m, 256);
/*     */       
/* 173 */       int n = paramMaskTile.rects.getSize();
/*     */       
/* 175 */       if (paramBoolean) {
/* 176 */         int i1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 182 */         if (n > 1) {
/* 183 */           this.con.renderRectangles(this.maskPicture, (byte)1, paramXRColor, paramMaskTile.rects);
/*     */           
/* 185 */           i1 = this.maskPicture;
/*     */         }
/* 187 */         else if (this.xrMgr.isTexturePaintActive()) {
/* 188 */           i1 = this.xrMgr.getExtraAlphaMask();
/*     */         } 
/*     */ 
/*     */         
/* 192 */         this.xrMgr.XRComposite(0, i1, paramXRSurfaceData.getPicture(), i, j, dirtyRegion.x, dirtyRegion.y, i, j, k, m);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 197 */         if (n > 1) {
/* 198 */           this.con.renderRectangle(this.maskPicture, (byte)0, XRColor.NO_ALPHA, dirtyRegion.x, dirtyRegion.y, k, m);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 204 */         paramMaskTile.reset();
/* 205 */       } else if (n > 0) {
/* 206 */         paramMaskTile.rects.translateRects(paramInt1 + paramDirtyRegion.x, paramInt2 + paramDirtyRegion.y);
/*     */         
/* 208 */         this.xrMgr.XRRenderRectangles(paramXRSurfaceData, paramMaskTile.rects);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void allocTiles(DirtyRegion paramDirtyRegion) {
/* 219 */     this.xTiles = paramDirtyRegion.getWidth() / 256 + 1;
/* 220 */     this.yTiles = paramDirtyRegion.getHeight() / 256 + 1;
/* 221 */     int i = this.xTiles * this.yTiles;
/*     */     
/* 223 */     if (i > this.allocatedTiles) {
/* 224 */       for (byte b = 0; b < i; b++) {
/* 225 */         if (b < this.allocatedTiles) {
/* 226 */           ((MaskTile)this.tileList.get(b)).reset();
/*     */         } else {
/* 228 */           this.tileList.add(new MaskTile());
/*     */         } 
/*     */       } 
/*     */       
/* 232 */       this.allocatedTiles = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tileRects() {
/* 240 */     GrowableRectArray growableRectArray = this.mainTile.rects;
/*     */     
/* 242 */     for (byte b = 0; b < growableRectArray.getSize(); b++) {
/* 243 */       int i = growableRectArray.getX(b) / 256;
/* 244 */       int j = growableRectArray.getY(b) / 256;
/*     */       
/* 246 */       int k = (growableRectArray.getX(b) + growableRectArray.getWidth(b)) / 256 + 1 - i;
/*     */ 
/*     */       
/* 249 */       int m = (growableRectArray.getY(b) + growableRectArray.getHeight(b)) / 256 + 1 - j;
/*     */ 
/*     */       
/* 252 */       for (byte b1 = 0; b1 < m; b1++) {
/* 253 */         for (byte b2 = 0; b2 < k; b2++) {
/*     */           
/* 255 */           int n = this.xTiles * (j + b1) + i + b2;
/*     */           
/* 257 */           MaskTile maskTile = this.tileList.get(n);
/*     */           
/* 259 */           GrowableRectArray growableRectArray1 = maskTile.getRects();
/* 260 */           int i1 = growableRectArray1.getNextIndex();
/*     */           
/* 262 */           int i2 = (i + b2) * 256;
/* 263 */           int i3 = (j + b1) * 256;
/*     */           
/* 265 */           growableRectArray1.setX(i1, growableRectArray.getX(b) - i2);
/* 266 */           growableRectArray1.setY(i1, growableRectArray.getY(b) - i3);
/* 267 */           growableRectArray1.setWidth(i1, growableRectArray.getWidth(b));
/* 268 */           growableRectArray1.setHeight(i1, growableRectArray.getHeight(b));
/*     */           
/* 270 */           limitRectCoords(growableRectArray1, i1);
/*     */           
/* 272 */           maskTile.getDirtyArea()
/* 273 */             .growDirtyRegion(growableRectArray1.getX(i1), growableRectArray1
/* 274 */               .getY(i1), growableRectArray1
/* 275 */               .getWidth(i1) + growableRectArray1
/* 276 */               .getX(i1), growableRectArray1
/* 277 */               .getHeight(i1) + growableRectArray1
/* 278 */               .getY(i1));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void limitRectCoords(GrowableRectArray paramGrowableRectArray, int paramInt) {
/* 289 */     if (paramGrowableRectArray.getX(paramInt) + paramGrowableRectArray.getWidth(paramInt) > 256) {
/* 290 */       paramGrowableRectArray.setWidth(paramInt, 256 - paramGrowableRectArray.getX(paramInt));
/*     */     }
/* 292 */     if (paramGrowableRectArray.getY(paramInt) + paramGrowableRectArray.getHeight(paramInt) > 256) {
/* 293 */       paramGrowableRectArray.setHeight(paramInt, 256 - paramGrowableRectArray.getY(paramInt));
/*     */     }
/* 295 */     if (paramGrowableRectArray.getX(paramInt) < 0) {
/* 296 */       paramGrowableRectArray.setWidth(paramInt, paramGrowableRectArray.getWidth(paramInt) + paramGrowableRectArray.getX(paramInt));
/* 297 */       paramGrowableRectArray.setX(paramInt, 0);
/*     */     } 
/* 299 */     if (paramGrowableRectArray.getY(paramInt) < 0) {
/* 300 */       paramGrowableRectArray.setHeight(paramInt, paramGrowableRectArray.getHeight(paramInt) + paramGrowableRectArray.getY(paramInt));
/* 301 */       paramGrowableRectArray.setY(paramInt, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaskTile getMainTile() {
/* 309 */     return this.mainTile;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/MaskTileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */