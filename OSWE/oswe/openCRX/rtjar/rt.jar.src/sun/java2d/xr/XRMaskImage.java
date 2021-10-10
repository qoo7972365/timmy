/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XRMaskImage
/*     */ {
/*     */   private static final int MASK_SCALE_FACTOR = 8;
/*     */   private static final int BLIT_MASK_SIZE = 8;
/*  43 */   Dimension blitMaskDimensions = new Dimension(8, 8);
/*     */   int blitMaskPixmap;
/*     */   int blitMaskPicture;
/*  46 */   int lastMaskWidth = 0;
/*  47 */   int lastMaskHeight = 0;
/*  48 */   int lastEA = -1;
/*     */   
/*     */   AffineTransform lastMaskTransform;
/*     */   XRCompositeManager xrMgr;
/*     */   XRBackend con;
/*     */   
/*     */   public XRMaskImage(XRCompositeManager paramXRCompositeManager, int paramInt) {
/*  55 */     this.xrMgr = paramXRCompositeManager;
/*  56 */     this.con = paramXRCompositeManager.getBackend();
/*     */     
/*  58 */     initBlitMask(paramInt, 8, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int prepareBlitMask(XRSurfaceData paramXRSurfaceData, AffineTransform paramAffineTransform, int paramInt1, int paramInt2) {
/*  69 */     int i = Math.max(paramInt1 / 8, 1);
/*  70 */     int j = Math.max(paramInt2 / 8, 1);
/*  71 */     paramAffineTransform.scale(paramInt1 / i, paramInt2 / j);
/*     */     
/*     */     try {
/*  74 */       paramAffineTransform.invert();
/*  75 */     } catch (NoninvertibleTransformException noninvertibleTransformException) {
/*  76 */       paramAffineTransform.setToIdentity();
/*     */     } 
/*     */     
/*  79 */     ensureBlitMaskSize(i, j);
/*     */     
/*  81 */     if (this.lastMaskTransform == null || !this.lastMaskTransform.equals(paramAffineTransform)) {
/*  82 */       this.con.setPictureTransform(this.blitMaskPicture, paramAffineTransform);
/*  83 */       this.lastMaskTransform = paramAffineTransform;
/*     */     } 
/*     */     
/*  86 */     int k = this.xrMgr.getAlphaColor().getAlpha();
/*  87 */     if (this.lastMaskWidth != i || this.lastMaskHeight != j || this.lastEA != k) {
/*     */       
/*  89 */       if (this.lastMaskWidth > i || this.lastMaskHeight > j) {
/*  90 */         this.con.renderRectangle(this.blitMaskPicture, (byte)0, XRColor.NO_ALPHA, 0, 0, this.lastMaskWidth, this.lastMaskHeight);
/*     */       }
/*     */       
/*  93 */       this.con.renderRectangle(this.blitMaskPicture, (byte)1, this.xrMgr.getAlphaColor(), 0, 0, i, j);
/*  94 */       this.lastEA = k;
/*     */     } 
/*     */     
/*  97 */     this.lastMaskWidth = i;
/*  98 */     this.lastMaskHeight = j;
/*     */     
/* 100 */     return this.blitMaskPicture;
/*     */   }
/*     */   
/*     */   private void initBlitMask(int paramInt1, int paramInt2, int paramInt3) {
/* 104 */     int i = this.con.createPixmap(paramInt1, 8, paramInt2, paramInt3);
/* 105 */     int j = this.con.createPicture(i, 2);
/*     */ 
/*     */     
/* 108 */     if (this.blitMaskPixmap != 0) {
/* 109 */       this.con.freePixmap(this.blitMaskPixmap);
/* 110 */       this.con.freePicture(this.blitMaskPicture);
/*     */     } 
/*     */     
/* 113 */     this.blitMaskPixmap = i;
/* 114 */     this.blitMaskPicture = j;
/*     */     
/* 116 */     this.con.renderRectangle(this.blitMaskPicture, (byte)0, XRColor.NO_ALPHA, 0, 0, paramInt2, paramInt3);
/*     */     
/* 118 */     this.blitMaskDimensions.width = paramInt2;
/* 119 */     this.blitMaskDimensions.height = paramInt3;
/* 120 */     this.lastMaskWidth = 0;
/* 121 */     this.lastMaskHeight = 0;
/* 122 */     this.lastMaskTransform = null;
/*     */   }
/*     */   
/*     */   private void ensureBlitMaskSize(int paramInt1, int paramInt2) {
/* 126 */     if (paramInt1 > this.blitMaskDimensions.width || paramInt2 > this.blitMaskDimensions.height) {
/* 127 */       int i = Math.max(paramInt1, this.blitMaskDimensions.width);
/* 128 */       int j = Math.max(paramInt2, this.blitMaskDimensions.height);
/* 129 */       initBlitMask(this.blitMaskPixmap, i, j);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRMaskImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */