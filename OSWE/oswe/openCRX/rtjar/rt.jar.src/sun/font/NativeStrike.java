/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NativeStrike
/*     */   extends PhysicalStrike
/*     */ {
/*     */   NativeFont nativeFont;
/*     */   int numGlyphs;
/*     */   AffineTransform invertDevTx;
/*     */   AffineTransform fontTx;
/*     */   
/*     */   private int getNativePointSize() {
/*  58 */     double[] arrayOfDouble = new double[4];
/*  59 */     this.desc.glyphTx.getMatrix(arrayOfDouble);
/*  60 */     this.fontTx = new AffineTransform(arrayOfDouble);
/*     */ 
/*     */     
/*  63 */     if (!this.desc.devTx.isIdentity() && this.desc.devTx
/*  64 */       .getType() != 1) {
/*     */       try {
/*  66 */         this.invertDevTx = this.desc.devTx.createInverse();
/*  67 */         this.fontTx.concatenate(this.invertDevTx);
/*  68 */       } catch (NoninvertibleTransformException noninvertibleTransformException) {
/*  69 */         noninvertibleTransformException.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     Point2D.Float float_ = new Point2D.Float(1.0F, 1.0F);
/*  77 */     this.fontTx.deltaTransform(float_, float_);
/*  78 */     double d = Math.abs(float_.y);
/*  79 */     int i = this.fontTx.getType();
/*  80 */     if ((i & 0xFFFFFFFD) != 0 || this.fontTx
/*  81 */       .getScaleY() <= 0.0D) {
/*     */ 
/*     */ 
/*     */       
/*  85 */       this.fontTx.scale(1.0D / d, 1.0D / d);
/*     */     } else {
/*  87 */       this.fontTx = null;
/*     */     } 
/*  89 */     return (int)d;
/*     */   }
/*     */   
/*     */   NativeStrike(NativeFont paramNativeFont, FontStrikeDesc paramFontStrikeDesc) {
/*  93 */     super(paramNativeFont, paramFontStrikeDesc);
/*  94 */     this.nativeFont = paramNativeFont;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (paramNativeFont.isBitmapDelegate) {
/* 102 */       int j = paramFontStrikeDesc.glyphTx.getType();
/* 103 */       if ((j & 0xFFFFFFFD) != 0 || paramFontStrikeDesc.glyphTx
/* 104 */         .getScaleX() <= 0.0D) {
/* 105 */         this.numGlyphs = 0;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 110 */     int i = getNativePointSize();
/* 111 */     byte[] arrayOfByte = paramNativeFont.getPlatformNameBytes(i);
/* 112 */     double d = Math.abs(paramFontStrikeDesc.devTx.getScaleX());
/* 113 */     this.pScalerContext = createScalerContext(arrayOfByte, i, d);
/* 114 */     if (this.pScalerContext == 0L) {
/* 115 */       SunFontManager.getInstance().deRegisterBadFont(paramNativeFont);
/* 116 */       this.pScalerContext = createNullScalerContext();
/* 117 */       this.numGlyphs = 0;
/* 118 */       if (FontUtilities.isLogging()) {
/* 119 */         FontUtilities.getLogger()
/* 120 */           .severe("Could not create native strike " + new String(arrayOfByte));
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 125 */     this.numGlyphs = paramNativeFont.getMapper().getNumGlyphs();
/* 126 */     this.disposer = new NativeStrikeDisposer(paramNativeFont, paramFontStrikeDesc, this.pScalerContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean usingIntGlyphImages() {
/* 136 */     if (this.intGlyphImages != null)
/* 137 */       return true; 
/* 138 */     if (longAddresses) {
/* 139 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 144 */     int i = getMaxGlyph(this.pScalerContext);
/*     */ 
/*     */     
/* 147 */     if (i < this.numGlyphs) {
/* 148 */       i = this.numGlyphs;
/*     */     }
/* 150 */     this.intGlyphImages = new int[i];
/* 151 */     this.disposer.intGlyphImages = this.intGlyphImages;
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private long[] getLongGlyphImages() {
/* 157 */     if (this.longGlyphImages == null && longAddresses) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       int i = getMaxGlyph(this.pScalerContext);
/*     */ 
/*     */       
/* 165 */       if (i < this.numGlyphs) {
/* 166 */         i = this.numGlyphs;
/*     */       }
/* 168 */       this.longGlyphImages = new long[i];
/* 169 */       this.disposer.longGlyphImages = this.longGlyphImages;
/*     */     } 
/* 171 */     return this.longGlyphImages;
/*     */   }
/*     */ 
/*     */   
/*     */   NativeStrike(NativeFont paramNativeFont, FontStrikeDesc paramFontStrikeDesc, boolean paramBoolean) {
/* 176 */     super(paramNativeFont, paramFontStrikeDesc);
/* 177 */     this.nativeFont = paramNativeFont;
/*     */     
/* 179 */     int i = (int)paramFontStrikeDesc.glyphTx.getScaleY();
/* 180 */     double d = paramFontStrikeDesc.devTx.getScaleX();
/* 181 */     byte[] arrayOfByte = paramNativeFont.getPlatformNameBytes(i);
/* 182 */     this.pScalerContext = createScalerContext(arrayOfByte, i, d);
/*     */     
/* 184 */     int j = paramNativeFont.getMapper().getNumGlyphs();
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
/*     */   StrikeMetrics getFontMetrics() {
/* 196 */     if (this.strikeMetrics == null) {
/* 197 */       if (this.pScalerContext != 0L) {
/* 198 */         this.strikeMetrics = this.nativeFont.getFontMetrics(this.pScalerContext);
/*     */       }
/* 200 */       if (this.strikeMetrics != null && this.fontTx != null) {
/* 201 */         this.strikeMetrics.convertToUserSpace(this.fontTx);
/*     */       }
/*     */     } 
/* 204 */     return this.strikeMetrics;
/*     */   }
/*     */ 
/*     */   
/*     */   private native long createScalerContext(byte[] paramArrayOfbyte, int paramInt, double paramDouble);
/*     */   
/*     */   private native int getMaxGlyph(long paramLong);
/*     */   
/*     */   private native long createNullScalerContext();
/*     */   
/*     */   void getGlyphImagePtrs(int[] paramArrayOfint, long[] paramArrayOflong, int paramInt) {
/* 215 */     for (byte b = 0; b < paramInt; b++) {
/* 216 */       paramArrayOflong[b] = getGlyphImagePtr(paramArrayOfint[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getGlyphImagePtr(int paramInt) {
/* 223 */     if (usingIntGlyphImages()) {
/* 224 */       long l1; if ((l1 = this.intGlyphImages[paramInt] & 0xFFFFFFFFL) != 0L) {
/* 225 */         return l1;
/*     */       }
/* 227 */       l1 = this.nativeFont.getGlyphImage(this.pScalerContext, paramInt);
/*     */ 
/*     */ 
/*     */       
/* 231 */       synchronized (this) {
/* 232 */         if (this.intGlyphImages[paramInt] == 0) {
/* 233 */           this.intGlyphImages[paramInt] = (int)l1;
/* 234 */           return l1;
/*     */         } 
/* 236 */         StrikeCache.freeIntPointer((int)l1);
/* 237 */         return this.intGlyphImages[paramInt] & 0xFFFFFFFFL;
/*     */       } 
/*     */     } 
/*     */     
/*     */     long l;
/*     */     
/* 243 */     if ((l = getLongGlyphImages()[paramInt]) != 0L) {
/* 244 */       return l;
/*     */     }
/* 246 */     l = this.nativeFont.getGlyphImage(this.pScalerContext, paramInt);
/*     */     
/* 248 */     synchronized (this) {
/* 249 */       if (this.longGlyphImages[paramInt] == 0L) {
/* 250 */         this.longGlyphImages[paramInt] = l;
/* 251 */         return l;
/*     */       } 
/* 253 */       StrikeCache.freeLongPointer(l);
/* 254 */       return this.longGlyphImages[paramInt];
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
/*     */   long getGlyphImagePtrNoCache(int paramInt) {
/* 268 */     return this.nativeFont.getGlyphImageNoDefault(this.pScalerContext, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   void getGlyphImageBounds(int paramInt, Point2D.Float paramFloat, Rectangle paramRectangle) {}
/*     */ 
/*     */   
/*     */   Point2D.Float getGlyphMetrics(int paramInt) {
/* 276 */     return new Point2D.Float(getGlyphAdvance(paramInt), 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   float getGlyphAdvance(int paramInt) {
/* 281 */     return this.nativeFont.getGlyphAdvance(this.pScalerContext, paramInt);
/*     */   }
/*     */   
/*     */   Rectangle2D.Float getGlyphOutlineBounds(int paramInt) {
/* 285 */     return this.nativeFont.getGlyphOutlineBounds(this.pScalerContext, paramInt);
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphOutline(int paramInt, float paramFloat1, float paramFloat2) {
/* 289 */     return new GeneralPath();
/*     */   }
/*     */   
/*     */   GeneralPath getGlyphVectorOutline(int[] paramArrayOfint, float paramFloat1, float paramFloat2) {
/* 293 */     return new GeneralPath();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/NativeStrike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */