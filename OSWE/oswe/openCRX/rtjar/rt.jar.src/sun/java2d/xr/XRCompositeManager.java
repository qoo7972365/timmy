/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import sun.font.XRTextRenderer;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.jules.TrapezoidList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRCompositeManager
/*     */ {
/*     */   private static boolean enableGradCache = true;
/*     */   private static XRCompositeManager instance;
/*     */   private static final int SOLID = 0;
/*     */   private static final int TEXTURE = 1;
/*     */   private static final int GRADIENT = 2;
/*     */   int srcType;
/*     */   XRSolidSrcPict solidSrc32;
/*     */   XRSurfaceData texture;
/*     */   XRSurfaceData gradient;
/*  59 */   int alphaMask = 0;
/*     */   
/*  61 */   XRColor solidColor = new XRColor();
/*  62 */   float extraAlpha = 1.0F;
/*  63 */   byte compRule = 3;
/*  64 */   XRColor alphaColor = new XRColor();
/*     */   
/*     */   XRSurfaceData solidSrcPict;
/*     */   
/*     */   int alphaMaskPict;
/*     */   int gradCachePixmap;
/*     */   int gradCachePicture;
/*     */   boolean xorEnabled = false;
/*  72 */   int validatedPixel = 0;
/*     */   Composite validatedComp;
/*     */   Paint validatedPaint;
/*  75 */   float validatedExtraAlpha = 1.0F;
/*     */   
/*     */   XRBackend con;
/*     */   
/*     */   MaskTileManager maskBuffer;
/*     */   XRTextRenderer textRenderer;
/*     */   XRMaskImage maskImage;
/*     */   
/*     */   public static synchronized XRCompositeManager getInstance(XRSurfaceData paramXRSurfaceData) {
/*  84 */     if (instance == null) {
/*  85 */       instance = new XRCompositeManager(paramXRSurfaceData);
/*     */     }
/*  87 */     return instance;
/*     */   }
/*     */   
/*     */   private XRCompositeManager(XRSurfaceData paramXRSurfaceData) {
/*  91 */     this.con = new XRBackendNative();
/*     */ 
/*     */     
/*  94 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public String run() {
/*  96 */             return System.getProperty("sun.java2d.xrgradcache");
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 102 */     enableGradCache = (str == null || (!str.equalsIgnoreCase("false") && !str.equalsIgnoreCase("f")));
/*     */     
/* 104 */     XRPaints.register(this);
/*     */     
/* 106 */     initResources(paramXRSurfaceData);
/*     */     
/* 108 */     this.maskBuffer = new MaskTileManager(this, paramXRSurfaceData.getXid());
/* 109 */     this.textRenderer = new XRTextRenderer(this);
/* 110 */     this.maskImage = new XRMaskImage(this, paramXRSurfaceData.getXid());
/*     */   }
/*     */   
/*     */   public void initResources(XRSurfaceData paramXRSurfaceData) {
/* 114 */     int i = paramXRSurfaceData.getXid();
/*     */     
/* 116 */     this.solidSrc32 = new XRSolidSrcPict(this.con, i);
/* 117 */     setForeground(0);
/*     */     
/* 119 */     int j = this.con.createPixmap(i, 8, 1, 1);
/* 120 */     this.alphaMaskPict = this.con.createPicture(j, 2);
/*     */     
/* 122 */     this.con.setPictureRepeat(this.alphaMaskPict, 1);
/* 123 */     this.con.renderRectangle(this.alphaMaskPict, (byte)0, XRColor.NO_ALPHA, 0, 0, 1, 1);
/*     */ 
/*     */     
/* 126 */     if (enableGradCache) {
/* 127 */       this.gradCachePixmap = this.con.createPixmap(i, 32, 256, 256);
/*     */       
/* 129 */       this.gradCachePicture = this.con.createPicture(this.gradCachePixmap, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setForeground(int paramInt) {
/* 135 */     this.solidColor.setColorValues(paramInt, true);
/*     */   }
/*     */   
/*     */   public void setGradientPaint(XRSurfaceData paramXRSurfaceData) {
/* 139 */     if (this.gradient != null) {
/* 140 */       this.con.freePicture(this.gradient.picture);
/*     */     }
/* 142 */     this.gradient = paramXRSurfaceData;
/* 143 */     this.srcType = 2;
/*     */   }
/*     */   
/*     */   public void setTexturePaint(XRSurfaceData paramXRSurfaceData) {
/* 147 */     this.texture = paramXRSurfaceData;
/* 148 */     this.srcType = 1;
/*     */   }
/*     */   
/*     */   public void XRResetPaint() {
/* 152 */     this.srcType = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void validateCompositeState(Composite paramComposite, AffineTransform paramAffineTransform, Paint paramPaint, SunGraphics2D paramSunGraphics2D) {
/* 157 */     boolean bool = (paramPaint != this.validatedPaint || paramPaint == null) ? true : false;
/*     */ 
/*     */     
/* 160 */     if (paramComposite != this.validatedComp) {
/* 161 */       if (paramComposite != null) {
/* 162 */         setComposite(paramComposite);
/*     */       } else {
/* 164 */         paramComposite = AlphaComposite.getInstance(3);
/* 165 */         setComposite(paramComposite);
/*     */       } 
/*     */ 
/*     */       
/* 169 */       bool = true;
/* 170 */       this.validatedComp = paramComposite;
/*     */     } 
/*     */     
/* 173 */     if (paramSunGraphics2D != null && (this.validatedPixel != paramSunGraphics2D.pixel || bool)) {
/* 174 */       this.validatedPixel = paramSunGraphics2D.pixel;
/* 175 */       setForeground(this.validatedPixel);
/*     */     } 
/*     */ 
/*     */     
/* 179 */     if (bool) {
/* 180 */       if (paramPaint != null && paramSunGraphics2D != null && paramSunGraphics2D.paintState >= 2) {
/*     */         
/* 182 */         XRPaints.setPaint(paramSunGraphics2D, paramPaint);
/*     */       } else {
/* 184 */         XRResetPaint();
/*     */       } 
/* 186 */       this.validatedPaint = paramPaint;
/*     */     } 
/*     */     
/* 189 */     if (this.srcType != 0) {
/* 190 */       AffineTransform affineTransform = (AffineTransform)paramAffineTransform.clone();
/*     */       try {
/* 192 */         affineTransform.invert();
/* 193 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/* 194 */         affineTransform.setToIdentity();
/*     */       } 
/* 196 */       getCurrentSource().validateAsSource(affineTransform, -1, XRUtils.ATransOpToXRQuality(paramSunGraphics2D.interpolationType));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setComposite(Composite paramComposite) {
/* 201 */     if (paramComposite instanceof AlphaComposite) {
/* 202 */       AlphaComposite alphaComposite = (AlphaComposite)paramComposite;
/* 203 */       this.validatedExtraAlpha = alphaComposite.getAlpha();
/*     */       
/* 205 */       this.compRule = XRUtils.j2dAlphaCompToXR(alphaComposite.getRule());
/* 206 */       this.extraAlpha = this.validatedExtraAlpha;
/*     */       
/* 208 */       if (this.extraAlpha == 1.0F) {
/* 209 */         this.alphaMask = 0;
/* 210 */         this.alphaColor.alpha = XRColor.FULL_ALPHA.alpha;
/*     */       } else {
/* 212 */         this.alphaColor
/* 213 */           .alpha = XRColor.byteToXRColorValue((int)(this.extraAlpha * 255.0F));
/* 214 */         this.alphaMask = this.alphaMaskPict;
/* 215 */         this.con.renderRectangle(this.alphaMaskPict, (byte)1, this.alphaColor, 0, 0, 1, 1);
/*     */       } 
/*     */ 
/*     */       
/* 219 */       this.xorEnabled = false;
/* 220 */     } else if (paramComposite instanceof sun.java2d.loops.XORComposite) {
/*     */       
/* 222 */       this.xorEnabled = true;
/*     */     } else {
/* 224 */       throw new InternalError("Composite accaleration not implemented for: " + paramComposite
/*     */           
/* 226 */           .getClass().getName());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean maskRequired() {
/* 231 */     return (!this.xorEnabled && (this.srcType != 0 || (this.srcType == 0 && this.solidColor.alpha != 65535) || this.extraAlpha != 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void XRComposite(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11) {
/* 238 */     int i = (paramInt1 == 0) ? (getCurrentSource()).picture : paramInt1;
/* 239 */     int j = paramInt4;
/* 240 */     int k = paramInt5;
/*     */     
/* 242 */     if (enableGradCache && this.gradient != null && i == this.gradient.picture) {
/*     */       
/* 244 */       this.con.renderComposite((byte)1, this.gradient.picture, 0, this.gradCachePicture, paramInt4, paramInt5, 0, 0, 0, 0, paramInt10, paramInt11);
/*     */ 
/*     */       
/* 247 */       j = 0;
/* 248 */       k = 0;
/* 249 */       i = this.gradCachePicture;
/*     */     } 
/*     */     
/* 252 */     this.con.renderComposite(this.compRule, i, paramInt2, paramInt3, j, k, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void XRCompositeTraps(int paramInt1, int paramInt2, int paramInt3, TrapezoidList paramTrapezoidList) {
/* 258 */     int i = 0;
/* 259 */     int j = 0;
/*     */     
/* 261 */     if (paramTrapezoidList.getP1YLeft(0) < paramTrapezoidList.getP2YLeft(0)) {
/* 262 */       i = paramTrapezoidList.getP1XLeft(0);
/* 263 */       j = paramTrapezoidList.getP1YLeft(0);
/*     */     } else {
/* 265 */       i = paramTrapezoidList.getP2XLeft(0);
/* 266 */       j = paramTrapezoidList.getP2YLeft(0);
/*     */     } 
/*     */     
/* 269 */     i = (int)Math.floor(
/* 270 */         XRUtils.XFixedToDouble(i));
/* 271 */     j = (int)Math.floor(
/* 272 */         XRUtils.XFixedToDouble(j));
/*     */     
/* 274 */     this.con.renderCompositeTrapezoids(this.compRule, (getCurrentSource()).picture, 2, paramInt1, i, j, paramTrapezoidList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void XRRenderRectangles(XRSurfaceData paramXRSurfaceData, GrowableRectArray paramGrowableRectArray) {
/* 280 */     if (this.xorEnabled) {
/* 281 */       this.con.GCRectangles(paramXRSurfaceData.getXid(), paramXRSurfaceData.getGC(), paramGrowableRectArray);
/*     */     }
/* 283 */     else if (paramGrowableRectArray.getSize() == 1) {
/* 284 */       this.con.renderRectangle(paramXRSurfaceData.getPicture(), this.compRule, this.solidColor, paramGrowableRectArray
/* 285 */           .getX(0), paramGrowableRectArray.getY(0), paramGrowableRectArray.getWidth(0), paramGrowableRectArray.getHeight(0));
/*     */     } else {
/* 287 */       this.con.renderRectangles(paramXRSurfaceData.getPicture(), this.compRule, this.solidColor, paramGrowableRectArray);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void XRCompositeRectangles(XRSurfaceData paramXRSurfaceData, GrowableRectArray paramGrowableRectArray) {
/* 293 */     int i = (getCurrentSource()).picture;
/*     */     
/* 295 */     for (byte b = 0; b < paramGrowableRectArray.getSize(); b++) {
/* 296 */       int j = paramGrowableRectArray.getX(b);
/* 297 */       int k = paramGrowableRectArray.getY(b);
/* 298 */       int m = paramGrowableRectArray.getWidth(b);
/* 299 */       int n = paramGrowableRectArray.getHeight(b);
/*     */       
/* 301 */       this.con.renderComposite(this.compRule, i, 0, paramXRSurfaceData.picture, j, k, 0, 0, j, k, m, n);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected XRSurfaceData getCurrentSource() {
/* 306 */     switch (this.srcType) {
/*     */       case 0:
/* 308 */         return this.solidSrc32.prepareSrcPict(this.validatedPixel);
/*     */       case 1:
/* 310 */         return this.texture;
/*     */       case 2:
/* 312 */         return this.gradient;
/*     */     } 
/*     */     
/* 315 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void compositeBlit(XRSurfaceData paramXRSurfaceData1, XRSurfaceData paramXRSurfaceData2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 320 */     this.con.renderComposite(this.compRule, paramXRSurfaceData1.picture, this.alphaMask, paramXRSurfaceData2.picture, paramInt1, paramInt2, 0, 0, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compositeText(XRSurfaceData paramXRSurfaceData, int paramInt1, int paramInt2, int paramInt3, int paramInt4, GrowableEltArray paramGrowableEltArray) {
/* 330 */     boolean bool = (this.compRule != 1) ? this.compRule : true;
/* 331 */     this.con.XRenderCompositeText(bool, (getCurrentSource()).picture, paramXRSurfaceData.picture, paramInt4, paramInt1, paramInt2, 0, 0, paramInt3, paramGrowableEltArray);
/*     */   }
/*     */ 
/*     */   
/*     */   public XRColor getMaskColor() {
/* 336 */     return !isTexturePaintActive() ? XRColor.FULL_ALPHA : getAlphaColor();
/*     */   }
/*     */   
/*     */   public int getExtraAlphaMask() {
/* 340 */     return this.alphaMask;
/*     */   }
/*     */   
/*     */   public boolean isTexturePaintActive() {
/* 344 */     return (this.srcType == 1);
/*     */   }
/*     */   
/*     */   public boolean isSolidPaintActive() {
/* 348 */     return (this.srcType == 0);
/*     */   }
/*     */   
/*     */   public XRColor getAlphaColor() {
/* 352 */     return this.alphaColor;
/*     */   }
/*     */   
/*     */   public XRBackend getBackend() {
/* 356 */     return this.con;
/*     */   }
/*     */   
/*     */   public float getExtraAlpha() {
/* 360 */     return this.validatedExtraAlpha;
/*     */   }
/*     */   
/*     */   public byte getCompRule() {
/* 364 */     return this.compRule;
/*     */   }
/*     */   
/*     */   public XRTextRenderer getTextRenderer() {
/* 368 */     return this.textRenderer;
/*     */   }
/*     */   
/*     */   public MaskTileManager getMaskBuffer() {
/* 372 */     return this.maskBuffer;
/*     */   }
/*     */   
/*     */   public XRMaskImage getMaskImage() {
/* 376 */     return this.maskImage;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRCompositeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */