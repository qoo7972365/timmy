/*      */ package sun.font;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphJustificationInfo;
/*      */ import java.awt.font.GlyphMetrics;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.NoninvertibleTransformException;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.text.CharacterIterator;
/*      */ import sun.java2d.loops.FontInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StandardGlyphVector
/*      */   extends GlyphVector
/*      */ {
/*      */   private Font font;
/*      */   private FontRenderContext frc;
/*      */   private int[] glyphs;
/*      */   private int[] userGlyphs;
/*      */   private float[] positions;
/*      */   private int[] charIndices;
/*      */   private int flags;
/*      */   private static final int UNINITIALIZED_FLAGS = -1;
/*      */   private GlyphTransformInfo gti;
/*      */   private AffineTransform ftx;
/*      */   private AffineTransform dtx;
/*      */   private AffineTransform invdtx;
/*      */   private AffineTransform frctx;
/*      */   private Font2D font2D;
/*      */   private SoftReference fsref;
/*      */   private SoftReference lbcacheRef;
/*      */   private SoftReference vbcacheRef;
/*      */   public static final int FLAG_USES_VERTICAL_BASELINE = 128;
/*      */   public static final int FLAG_USES_VERTICAL_METRICS = 256;
/*      */   public static final int FLAG_USES_ALTERNATE_ORIENTATION = 512;
/*      */   
/*      */   public StandardGlyphVector(Font paramFont, String paramString, FontRenderContext paramFontRenderContext) {
/*  163 */     init(paramFont, paramString.toCharArray(), 0, paramString.length(), paramFontRenderContext, -1);
/*      */   }
/*      */   
/*      */   public StandardGlyphVector(Font paramFont, char[] paramArrayOfchar, FontRenderContext paramFontRenderContext) {
/*  167 */     init(paramFont, paramArrayOfchar, 0, paramArrayOfchar.length, paramFontRenderContext, -1);
/*      */   }
/*      */ 
/*      */   
/*      */   public StandardGlyphVector(Font paramFont, char[] paramArrayOfchar, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext) {
/*  172 */     init(paramFont, paramArrayOfchar, paramInt1, paramInt2, paramFontRenderContext, -1);
/*      */   }
/*      */   
/*      */   private float getTracking(Font paramFont) {
/*  176 */     if (paramFont.hasLayoutAttributes()) {
/*  177 */       AttributeValues attributeValues = ((AttributeMap)paramFont.getAttributes()).getValues();
/*  178 */       return attributeValues.getTracking();
/*      */     } 
/*  180 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public StandardGlyphVector(Font paramFont, FontRenderContext paramFontRenderContext, int[] paramArrayOfint1, float[] paramArrayOffloat, int[] paramArrayOfint2, int paramInt) {
/*  186 */     initGlyphVector(paramFont, paramFontRenderContext, paramArrayOfint1, paramArrayOffloat, paramArrayOfint2, paramInt);
/*      */ 
/*      */     
/*  189 */     float f = getTracking(paramFont);
/*  190 */     if (f != 0.0F) {
/*  191 */       f *= paramFont.getSize2D();
/*  192 */       Point2D.Float float_ = new Point2D.Float(f, 0.0F);
/*  193 */       if (paramFont.isTransformed()) {
/*  194 */         AffineTransform affineTransform = paramFont.getTransform();
/*  195 */         affineTransform.deltaTransform(float_, float_);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  200 */       Font2D font2D = FontUtilities.getFont2D(paramFont);
/*  201 */       FontStrike fontStrike = font2D.getStrike(paramFont, paramFontRenderContext);
/*      */       
/*  203 */       float[] arrayOfFloat = { float_.x, float_.y };
/*  204 */       for (byte b = 0; b < arrayOfFloat.length; b++) {
/*  205 */         float f1 = arrayOfFloat[b];
/*  206 */         if (f1 != 0.0F) {
/*  207 */           float f2 = 0.0F;
/*  208 */           for (byte b1 = b, b2 = 0; b2 < paramArrayOfint1.length; b1 += 2) {
/*  209 */             if (fontStrike.getGlyphAdvance(paramArrayOfint1[b2++]) != 0.0F) {
/*  210 */               paramArrayOffloat[b1] = paramArrayOffloat[b1] + f2;
/*  211 */               f2 += f1;
/*      */             } 
/*      */           } 
/*  214 */           paramArrayOffloat[paramArrayOffloat.length - 2 + b] = paramArrayOffloat[paramArrayOffloat.length - 2 + b] + f2;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void initGlyphVector(Font paramFont, FontRenderContext paramFontRenderContext, int[] paramArrayOfint1, float[] paramArrayOffloat, int[] paramArrayOfint2, int paramInt) {
/*  222 */     this.font = paramFont;
/*  223 */     this.frc = paramFontRenderContext;
/*  224 */     this.glyphs = paramArrayOfint1;
/*  225 */     this.userGlyphs = paramArrayOfint1;
/*  226 */     this.positions = paramArrayOffloat;
/*  227 */     this.charIndices = paramArrayOfint2;
/*  228 */     this.flags = paramInt;
/*      */     
/*  230 */     initFontData();
/*      */   }
/*      */   
/*      */   public StandardGlyphVector(Font paramFont, CharacterIterator paramCharacterIterator, FontRenderContext paramFontRenderContext) {
/*  234 */     int i = paramCharacterIterator.getBeginIndex();
/*  235 */     char[] arrayOfChar = new char[paramCharacterIterator.getEndIndex() - i];
/*  236 */     char c = paramCharacterIterator.first();
/*  237 */     for (; c != Character.MAX_VALUE; 
/*  238 */       c = paramCharacterIterator.next()) {
/*  239 */       arrayOfChar[paramCharacterIterator.getIndex() - i] = c;
/*      */     }
/*  241 */     init(paramFont, arrayOfChar, 0, arrayOfChar.length, paramFontRenderContext, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public StandardGlyphVector(Font paramFont, int[] paramArrayOfint, FontRenderContext paramFontRenderContext) {
/*  247 */     this.font = paramFont;
/*  248 */     this.frc = paramFontRenderContext;
/*  249 */     this.flags = -1;
/*      */     
/*  251 */     initFontData();
/*  252 */     this.userGlyphs = paramArrayOfint;
/*  253 */     this.glyphs = getValidatedGlyphs(this.userGlyphs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static StandardGlyphVector getStandardGV(GlyphVector paramGlyphVector, FontInfo paramFontInfo) {
/*  268 */     if (paramFontInfo.aaHint == 2) {
/*  269 */       Object object = paramGlyphVector.getFontRenderContext().getAntiAliasingHint();
/*  270 */       if (object != RenderingHints.VALUE_TEXT_ANTIALIAS_ON && object != RenderingHints.VALUE_TEXT_ANTIALIAS_GASP) {
/*      */ 
/*      */         
/*  273 */         FontRenderContext fontRenderContext = paramGlyphVector.getFontRenderContext();
/*      */ 
/*      */         
/*  276 */         fontRenderContext = new FontRenderContext(fontRenderContext.getTransform(), RenderingHints.VALUE_TEXT_ANTIALIAS_ON, fontRenderContext.getFractionalMetricsHint());
/*  277 */         return new StandardGlyphVector(paramGlyphVector, fontRenderContext);
/*      */       } 
/*      */     } 
/*  280 */     if (paramGlyphVector instanceof StandardGlyphVector) {
/*  281 */       return (StandardGlyphVector)paramGlyphVector;
/*      */     }
/*  283 */     return new StandardGlyphVector(paramGlyphVector, paramGlyphVector.getFontRenderContext());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() {
/*  291 */     return this.font;
/*      */   }
/*      */   
/*      */   public FontRenderContext getFontRenderContext() {
/*  295 */     return this.frc;
/*      */   }
/*      */   
/*      */   public void performDefaultLayout() {
/*  299 */     this.positions = null;
/*  300 */     if (getTracking(this.font) == 0.0F) {
/*  301 */       clearFlags(2);
/*      */     }
/*      */   }
/*      */   
/*      */   public int getNumGlyphs() {
/*  306 */     return this.glyphs.length;
/*      */   }
/*      */   
/*      */   public int getGlyphCode(int paramInt) {
/*  310 */     return this.userGlyphs[paramInt];
/*      */   }
/*      */   
/*      */   public int[] getGlyphCodes(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/*  314 */     if (paramInt2 < 0) {
/*  315 */       throw new IllegalArgumentException("count = " + paramInt2);
/*      */     }
/*  317 */     if (paramInt1 < 0) {
/*  318 */       throw new IndexOutOfBoundsException("start = " + paramInt1);
/*      */     }
/*  320 */     if (paramInt1 > this.glyphs.length - paramInt2) {
/*  321 */       throw new IndexOutOfBoundsException("start + count = " + (paramInt1 + paramInt2));
/*      */     }
/*      */     
/*  324 */     if (paramArrayOfint == null) {
/*  325 */       paramArrayOfint = new int[paramInt2];
/*      */     }
/*      */ 
/*      */     
/*  329 */     for (byte b = 0; b < paramInt2; b++) {
/*  330 */       paramArrayOfint[b] = this.userGlyphs[b + paramInt1];
/*      */     }
/*      */     
/*  333 */     return paramArrayOfint;
/*      */   }
/*      */   
/*      */   public int getGlyphCharIndex(int paramInt) {
/*  337 */     if (paramInt < 0 && paramInt >= this.glyphs.length) {
/*  338 */       throw new IndexOutOfBoundsException("" + paramInt);
/*      */     }
/*  340 */     if (this.charIndices == null) {
/*  341 */       if ((getLayoutFlags() & 0x4) != 0) {
/*  342 */         return this.glyphs.length - 1 - paramInt;
/*      */       }
/*  344 */       return paramInt;
/*      */     } 
/*  346 */     return this.charIndices[paramInt];
/*      */   }
/*      */   
/*      */   public int[] getGlyphCharIndices(int paramInt1, int paramInt2, int[] paramArrayOfint) {
/*  350 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > this.glyphs.length - paramInt1) {
/*  351 */       throw new IndexOutOfBoundsException("" + paramInt1 + ", " + paramInt2);
/*      */     }
/*  353 */     if (paramArrayOfint == null) {
/*  354 */       paramArrayOfint = new int[paramInt2];
/*      */     }
/*  356 */     if (this.charIndices == null) {
/*  357 */       if ((getLayoutFlags() & 0x4) != 0) {
/*  358 */         byte b = 0; int i = this.glyphs.length - 1 - paramInt1;
/*  359 */         for (; b < paramInt2; b++, i--)
/*  360 */           paramArrayOfint[b] = i; 
/*      */       } else {
/*      */         byte b; int i;
/*  363 */         for (b = 0, i = paramInt1; b < paramInt2; b++, i++) {
/*  364 */           paramArrayOfint[b] = i;
/*      */         }
/*      */       } 
/*      */     } else {
/*  368 */       for (byte b = 0; b < paramInt2; b++) {
/*  369 */         paramArrayOfint[b] = this.charIndices[b + paramInt1];
/*      */       }
/*      */     } 
/*  372 */     return paramArrayOfint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getLogicalBounds() {
/*  379 */     setFRCTX();
/*  380 */     initPositions();
/*      */     
/*  382 */     LineMetrics lineMetrics = this.font.getLineMetrics("", this.frc);
/*      */ 
/*      */ 
/*      */     
/*  386 */     float f1 = 0.0F;
/*  387 */     float f2 = -lineMetrics.getAscent();
/*  388 */     float f3 = 0.0F;
/*  389 */     float f4 = lineMetrics.getDescent() + lineMetrics.getLeading();
/*  390 */     if (this.glyphs.length > 0) {
/*  391 */       f3 = this.positions[this.positions.length - 2];
/*      */     }
/*      */     
/*  394 */     return new Rectangle2D.Float(f1, f2, f3 - f1, f4 - f2);
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle2D getVisualBounds() {
/*  399 */     Rectangle2D rectangle2D = null;
/*  400 */     for (byte b = 0; b < this.glyphs.length; b++) {
/*  401 */       Rectangle2D rectangle2D1 = getGlyphVisualBounds(b).getBounds2D();
/*  402 */       if (!rectangle2D1.isEmpty()) {
/*  403 */         if (rectangle2D == null) {
/*  404 */           rectangle2D = rectangle2D1;
/*      */         } else {
/*  406 */           Rectangle2D.union(rectangle2D, rectangle2D1, rectangle2D);
/*      */         } 
/*      */       }
/*      */     } 
/*  410 */     if (rectangle2D == null) {
/*  411 */       rectangle2D = new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
/*      */     }
/*  413 */     return rectangle2D;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getPixelBounds(FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2) {
/*  419 */     return getGlyphsPixelBounds(paramFontRenderContext, paramFloat1, paramFloat2, 0, this.glyphs.length);
/*      */   }
/*      */   
/*      */   public Shape getOutline() {
/*  423 */     return getGlyphsOutline(0, this.glyphs.length, 0.0F, 0.0F);
/*      */   }
/*      */   
/*      */   public Shape getOutline(float paramFloat1, float paramFloat2) {
/*  427 */     return getGlyphsOutline(0, this.glyphs.length, paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   
/*      */   public Shape getGlyphOutline(int paramInt) {
/*  432 */     return getGlyphsOutline(paramInt, 1, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public Shape getGlyphOutline(int paramInt, float paramFloat1, float paramFloat2) {
/*  437 */     return getGlyphsOutline(paramInt, 1, paramFloat1, paramFloat2);
/*      */   }
/*      */   
/*      */   public Point2D getGlyphPosition(int paramInt) {
/*  441 */     initPositions();
/*      */     
/*  443 */     paramInt *= 2;
/*  444 */     return new Point2D.Float(this.positions[paramInt], this.positions[paramInt + 1]);
/*      */   }
/*      */   
/*      */   public void setGlyphPosition(int paramInt, Point2D paramPoint2D) {
/*  448 */     initPositions();
/*      */     
/*  450 */     int i = paramInt << 1;
/*  451 */     this.positions[i] = (float)paramPoint2D.getX();
/*  452 */     this.positions[i + 1] = (float)paramPoint2D.getY();
/*      */     
/*  454 */     clearCaches(paramInt);
/*  455 */     addFlags(2);
/*      */   }
/*      */   
/*      */   public AffineTransform getGlyphTransform(int paramInt) {
/*  459 */     if (paramInt < 0 || paramInt >= this.glyphs.length) {
/*  460 */       throw new IndexOutOfBoundsException("ix = " + paramInt);
/*      */     }
/*  462 */     if (this.gti != null) {
/*  463 */       return this.gti.getGlyphTransform(paramInt);
/*      */     }
/*  465 */     return null;
/*      */   }
/*      */   
/*      */   public void setGlyphTransform(int paramInt, AffineTransform paramAffineTransform) {
/*  469 */     if (paramInt < 0 || paramInt >= this.glyphs.length) {
/*  470 */       throw new IndexOutOfBoundsException("ix = " + paramInt);
/*      */     }
/*      */     
/*  473 */     if (this.gti == null) {
/*  474 */       if (paramAffineTransform == null || paramAffineTransform.isIdentity()) {
/*      */         return;
/*      */       }
/*  477 */       this.gti = new GlyphTransformInfo(this);
/*      */     } 
/*  479 */     this.gti.setGlyphTransform(paramInt, paramAffineTransform);
/*  480 */     if (this.gti.transformCount() == 0) {
/*  481 */       this.gti = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public int getLayoutFlags() {
/*  486 */     if (this.flags == -1) {
/*  487 */       this.flags = 0;
/*      */       
/*  489 */       if (this.charIndices != null && this.glyphs.length > 1) {
/*  490 */         boolean bool1 = true;
/*  491 */         boolean bool2 = true;
/*      */         
/*  493 */         int i = this.charIndices.length;
/*  494 */         for (byte b = 0; b < this.charIndices.length && (bool1 || bool2); b++) {
/*  495 */           int j = this.charIndices[b];
/*      */           
/*  497 */           bool1 = (bool1 && j == b) ? true : false;
/*  498 */           bool2 = (bool2 && j == --i) ? true : false;
/*      */         } 
/*      */         
/*  501 */         if (bool2) this.flags |= 0x4; 
/*  502 */         if (!bool2 && !bool1) this.flags |= 0x8;
/*      */       
/*      */       } 
/*      */     } 
/*  506 */     return this.flags;
/*      */   }
/*      */   
/*      */   public float[] getGlyphPositions(int paramInt1, int paramInt2, float[] paramArrayOffloat) {
/*  510 */     if (paramInt2 < 0) {
/*  511 */       throw new IllegalArgumentException("count = " + paramInt2);
/*      */     }
/*  513 */     if (paramInt1 < 0) {
/*  514 */       throw new IndexOutOfBoundsException("start = " + paramInt1);
/*      */     }
/*  516 */     if (paramInt1 > this.glyphs.length + 1 - paramInt2) {
/*  517 */       throw new IndexOutOfBoundsException("start + count = " + (paramInt1 + paramInt2));
/*      */     }
/*      */     
/*  520 */     return internalGetGlyphPositions(paramInt1, paramInt2, 0, paramArrayOffloat);
/*      */   }
/*      */   
/*      */   public Shape getGlyphLogicalBounds(int paramInt) {
/*  524 */     if (paramInt < 0 || paramInt >= this.glyphs.length) {
/*  525 */       throw new IndexOutOfBoundsException("ix = " + paramInt);
/*      */     }
/*      */     
/*      */     Shape[] arrayOfShape;
/*  529 */     if (this.lbcacheRef == null || (arrayOfShape = this.lbcacheRef.get()) == null) {
/*  530 */       arrayOfShape = new Shape[this.glyphs.length];
/*  531 */       this.lbcacheRef = new SoftReference<>(arrayOfShape);
/*      */     } 
/*      */     
/*  534 */     Shape shape = arrayOfShape[paramInt];
/*  535 */     if (shape == null) {
/*  536 */       setFRCTX();
/*  537 */       initPositions();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  545 */       ADL aDL = new ADL();
/*  546 */       GlyphStrike glyphStrike = getGlyphStrike(paramInt);
/*  547 */       glyphStrike.getADL(aDL);
/*      */       
/*  549 */       Point2D.Float float_ = glyphStrike.strike.getGlyphMetrics(this.glyphs[paramInt]);
/*      */       
/*  551 */       float f1 = float_.x;
/*  552 */       float f2 = float_.y;
/*  553 */       float f3 = aDL.descentX + aDL.leadingX + aDL.ascentX;
/*  554 */       float f4 = aDL.descentY + aDL.leadingY + aDL.ascentY;
/*  555 */       float f5 = this.positions[paramInt * 2] + glyphStrike.dx - aDL.ascentX;
/*  556 */       float f6 = this.positions[paramInt * 2 + 1] + glyphStrike.dy - aDL.ascentY;
/*      */       
/*  558 */       GeneralPath generalPath = new GeneralPath();
/*  559 */       generalPath.moveTo(f5, f6);
/*  560 */       generalPath.lineTo(f5 + f1, f6 + f2);
/*  561 */       generalPath.lineTo(f5 + f1 + f3, f6 + f2 + f4);
/*  562 */       generalPath.lineTo(f5 + f3, f6 + f4);
/*  563 */       generalPath.closePath();
/*      */       
/*  565 */       shape = new DelegatingShape(generalPath);
/*  566 */       arrayOfShape[paramInt] = shape;
/*      */     } 
/*      */     
/*  569 */     return shape;
/*      */   }
/*      */ 
/*      */   
/*      */   public Shape getGlyphVisualBounds(int paramInt) {
/*  574 */     if (paramInt < 0 || paramInt >= this.glyphs.length) {
/*  575 */       throw new IndexOutOfBoundsException("ix = " + paramInt);
/*      */     }
/*      */     
/*      */     Shape[] arrayOfShape;
/*  579 */     if (this.vbcacheRef == null || (arrayOfShape = this.vbcacheRef.get()) == null) {
/*  580 */       arrayOfShape = new Shape[this.glyphs.length];
/*  581 */       this.vbcacheRef = new SoftReference<>(arrayOfShape);
/*      */     } 
/*      */     
/*  584 */     Shape shape = arrayOfShape[paramInt];
/*  585 */     if (shape == null) {
/*  586 */       shape = new DelegatingShape(getGlyphOutlineBounds(paramInt));
/*  587 */       arrayOfShape[paramInt] = shape;
/*      */     } 
/*      */     
/*  590 */     return shape;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle getGlyphPixelBounds(int paramInt, FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2) {
/*  595 */     return getGlyphsPixelBounds(paramFontRenderContext, paramFloat1, paramFloat2, paramInt, 1);
/*      */   }
/*      */   
/*      */   public GlyphMetrics getGlyphMetrics(int paramInt) {
/*  599 */     if (paramInt < 0 || paramInt >= this.glyphs.length) {
/*  600 */       throw new IndexOutOfBoundsException("ix = " + paramInt);
/*      */     }
/*      */     
/*  603 */     Rectangle2D rectangle2D = getGlyphVisualBounds(paramInt).getBounds2D();
/*  604 */     Point2D point2D = getGlyphPosition(paramInt);
/*  605 */     rectangle2D.setRect(rectangle2D.getMinX() - point2D.getX(), rectangle2D
/*  606 */         .getMinY() - point2D.getY(), rectangle2D
/*  607 */         .getWidth(), rectangle2D
/*  608 */         .getHeight());
/*      */     
/*  610 */     Point2D.Float float_ = (getGlyphStrike(paramInt)).strike.getGlyphMetrics(this.glyphs[paramInt]);
/*  611 */     return new GlyphMetrics(true, float_.x, float_.y, rectangle2D, (byte)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GlyphJustificationInfo getGlyphJustificationInfo(int paramInt) {
/*  618 */     if (paramInt < 0 || paramInt >= this.glyphs.length) {
/*  619 */       throw new IndexOutOfBoundsException("ix = " + paramInt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  627 */     return null;
/*      */   }
/*      */   
/*      */   public boolean equals(GlyphVector paramGlyphVector) {
/*  631 */     if (this == paramGlyphVector) {
/*  632 */       return true;
/*      */     }
/*  634 */     if (paramGlyphVector == null) {
/*  635 */       return false;
/*      */     }
/*      */     
/*      */     try {
/*  639 */       StandardGlyphVector standardGlyphVector = (StandardGlyphVector)paramGlyphVector;
/*      */       
/*  641 */       if (this.glyphs.length != standardGlyphVector.glyphs.length) {
/*  642 */         return false;
/*      */       }
/*      */       byte b;
/*  645 */       for (b = 0; b < this.glyphs.length; b++) {
/*  646 */         if (this.glyphs[b] != standardGlyphVector.glyphs[b]) {
/*  647 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  651 */       if (!this.font.equals(standardGlyphVector.font)) {
/*  652 */         return false;
/*      */       }
/*      */       
/*  655 */       if (!this.frc.equals(standardGlyphVector.frc)) {
/*  656 */         return false;
/*      */       }
/*      */       
/*  659 */       if (((standardGlyphVector.positions == null) ? true : false) != ((this.positions == null) ? true : false)) {
/*  660 */         if (this.positions == null) {
/*  661 */           initPositions();
/*      */         } else {
/*  663 */           standardGlyphVector.initPositions();
/*      */         } 
/*      */       }
/*      */       
/*  667 */       if (this.positions != null) {
/*  668 */         for (b = 0; b < this.positions.length; b++) {
/*  669 */           if (this.positions[b] != standardGlyphVector.positions[b]) {
/*  670 */             return false;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  675 */       if (this.gti == null) {
/*  676 */         return (standardGlyphVector.gti == null);
/*      */       }
/*  678 */       return this.gti.equals(standardGlyphVector.gti);
/*      */     
/*      */     }
/*  681 */     catch (ClassCastException classCastException) {
/*      */ 
/*      */       
/*  684 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  693 */     return this.font.hashCode() ^ this.glyphs.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*      */     try {
/*  703 */       return equals((GlyphVector)paramObject);
/*      */     }
/*  705 */     catch (ClassCastException classCastException) {
/*  706 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StandardGlyphVector copy() {
/*  714 */     return (StandardGlyphVector)clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/*  725 */       StandardGlyphVector standardGlyphVector = (StandardGlyphVector)super.clone();
/*      */       
/*  727 */       standardGlyphVector.clearCaches();
/*      */       
/*  729 */       if (this.positions != null) {
/*  730 */         standardGlyphVector.positions = (float[])this.positions.clone();
/*      */       }
/*      */       
/*  733 */       if (this.gti != null) {
/*  734 */         standardGlyphVector.gti = new GlyphTransformInfo(standardGlyphVector, this.gti);
/*      */       }
/*      */       
/*  737 */       return standardGlyphVector;
/*      */     }
/*  739 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */ 
/*      */       
/*  742 */       return this;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGlyphPositions(float[] paramArrayOffloat, int paramInt1, int paramInt2, int paramInt3) {
/*  755 */     if (paramInt3 < 0) {
/*  756 */       throw new IllegalArgumentException("count = " + paramInt3);
/*      */     }
/*      */     
/*  759 */     initPositions();
/*  760 */     for (int i = paramInt2 * 2, j = i + paramInt3 * 2, k = paramInt1; i < j; i++, k++) {
/*  761 */       this.positions[i] = paramArrayOffloat[k];
/*      */     }
/*      */     
/*  764 */     clearCaches();
/*  765 */     addFlags(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGlyphPositions(float[] paramArrayOffloat) {
/*  773 */     int i = this.glyphs.length * 2 + 2;
/*  774 */     if (paramArrayOffloat.length != i) {
/*  775 */       throw new IllegalArgumentException("srcPositions.length != " + i);
/*      */     }
/*      */     
/*  778 */     this.positions = (float[])paramArrayOffloat.clone();
/*      */     
/*  780 */     clearCaches();
/*  781 */     addFlags(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getGlyphPositions(float[] paramArrayOffloat) {
/*  790 */     return internalGetGlyphPositions(0, this.glyphs.length + 1, 0, paramArrayOffloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform[] getGlyphTransforms(int paramInt1, int paramInt2, AffineTransform[] paramArrayOfAffineTransform) {
/*  801 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > this.glyphs.length) {
/*  802 */       throw new IllegalArgumentException("start: " + paramInt1 + " count: " + paramInt2);
/*      */     }
/*      */     
/*  805 */     if (this.gti == null) {
/*  806 */       return null;
/*      */     }
/*      */     
/*  809 */     if (paramArrayOfAffineTransform == null) {
/*  810 */       paramArrayOfAffineTransform = new AffineTransform[paramInt2];
/*      */     }
/*      */     
/*  813 */     for (byte b = 0; b < paramInt2; b++, paramInt1++) {
/*  814 */       paramArrayOfAffineTransform[b] = this.gti.getGlyphTransform(paramInt1);
/*      */     }
/*      */     
/*  817 */     return paramArrayOfAffineTransform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform[] getGlyphTransforms() {
/*  824 */     return getGlyphTransforms(0, this.glyphs.length, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGlyphTransforms(AffineTransform[] paramArrayOfAffineTransform, int paramInt1, int paramInt2, int paramInt3) {
/*  833 */     for (int i = paramInt2, j = paramInt2 + paramInt3; i < j; i++) {
/*  834 */       setGlyphTransform(i, paramArrayOfAffineTransform[paramInt1 + i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGlyphTransforms(AffineTransform[] paramArrayOfAffineTransform) {
/*  842 */     setGlyphTransforms(paramArrayOfAffineTransform, 0, 0, this.glyphs.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getGlyphInfo() {
/*  849 */     setFRCTX();
/*  850 */     initPositions();
/*  851 */     float[] arrayOfFloat = new float[this.glyphs.length * 8];
/*  852 */     for (byte b1 = 0, b2 = 0; b1 < this.glyphs.length; b1++, b2 += 8) {
/*  853 */       float f1 = this.positions[b1 * 2];
/*  854 */       float f2 = this.positions[b1 * 2 + 1];
/*  855 */       arrayOfFloat[b2] = f1;
/*  856 */       arrayOfFloat[b2 + 1] = f2;
/*      */       
/*  858 */       int i = this.glyphs[b1];
/*  859 */       GlyphStrike glyphStrike = getGlyphStrike(b1);
/*  860 */       Point2D.Float float_ = glyphStrike.strike.getGlyphMetrics(i);
/*  861 */       arrayOfFloat[b2 + 2] = float_.x;
/*  862 */       arrayOfFloat[b2 + 3] = float_.y;
/*      */       
/*  864 */       Rectangle2D rectangle2D = getGlyphVisualBounds(b1).getBounds2D();
/*  865 */       arrayOfFloat[b2 + 4] = (float)rectangle2D.getMinX();
/*  866 */       arrayOfFloat[b2 + 5] = (float)rectangle2D.getMinY();
/*  867 */       arrayOfFloat[b2 + 6] = (float)rectangle2D.getWidth();
/*  868 */       arrayOfFloat[b2 + 7] = (float)rectangle2D.getHeight();
/*      */     } 
/*  870 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pixellate(FontRenderContext paramFontRenderContext, Point2D paramPoint2D, Point paramPoint) {
/*  877 */     if (paramFontRenderContext == null) {
/*  878 */       paramFontRenderContext = this.frc;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  883 */     AffineTransform affineTransform = paramFontRenderContext.getTransform();
/*  884 */     affineTransform.transform(paramPoint2D, paramPoint2D);
/*  885 */     paramPoint.x = (int)paramPoint2D.getX();
/*  886 */     paramPoint.y = (int)paramPoint2D.getY();
/*  887 */     paramPoint2D.setLocation(paramPoint.x, paramPoint.y);
/*      */     try {
/*  889 */       affineTransform.inverseTransform(paramPoint2D, paramPoint2D);
/*      */     }
/*  891 */     catch (NoninvertibleTransformException noninvertibleTransformException) {
/*  892 */       throw new IllegalArgumentException("must be able to invert frc transform");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean needsPositions(double[] paramArrayOfdouble) {
/*  905 */     return (this.gti != null || (
/*  906 */       getLayoutFlags() & 0x2) != 0 || 
/*  907 */       !matchTX(paramArrayOfdouble, this.frctx));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Object setupGlyphImages(long[] paramArrayOflong, float[] paramArrayOffloat, double[] paramArrayOfdouble) {
/*  930 */     initPositions();
/*  931 */     setRenderTransform(paramArrayOfdouble);
/*      */     
/*  933 */     if (this.gti != null) {
/*  934 */       return this.gti.setupGlyphImages(paramArrayOflong, paramArrayOffloat, this.dtx);
/*      */     }
/*      */     
/*  937 */     GlyphStrike glyphStrike = getDefaultStrike();
/*  938 */     glyphStrike.strike.getGlyphImagePtrs(this.glyphs, paramArrayOflong, this.glyphs.length);
/*      */     
/*  940 */     if (paramArrayOffloat != null) {
/*  941 */       if (this.dtx.isIdentity()) {
/*  942 */         System.arraycopy(this.positions, 0, paramArrayOffloat, 0, this.glyphs.length * 2);
/*      */       } else {
/*  944 */         this.dtx.transform(this.positions, 0, paramArrayOffloat, 0, this.glyphs.length);
/*      */       } 
/*      */     }
/*      */     
/*  948 */     return glyphStrike;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean matchTX(double[] paramArrayOfdouble, AffineTransform paramAffineTransform) {
/*  962 */     return (paramArrayOfdouble[0] == paramAffineTransform
/*  963 */       .getScaleX() && paramArrayOfdouble[1] == paramAffineTransform
/*  964 */       .getShearY() && paramArrayOfdouble[2] == paramAffineTransform
/*  965 */       .getShearX() && paramArrayOfdouble[3] == paramAffineTransform
/*  966 */       .getScaleY());
/*      */   }
/*      */ 
/*      */   
/*      */   private static AffineTransform getNonTranslateTX(AffineTransform paramAffineTransform) {
/*  971 */     if (paramAffineTransform.getTranslateX() != 0.0D || paramAffineTransform.getTranslateY() != 0.0D)
/*      */     {
/*  973 */       paramAffineTransform = new AffineTransform(paramAffineTransform.getScaleX(), paramAffineTransform.getShearY(), paramAffineTransform.getShearX(), paramAffineTransform.getScaleY(), 0.0D, 0.0D);
/*      */     }
/*      */     
/*  976 */     return paramAffineTransform;
/*      */   }
/*      */   
/*      */   private static boolean equalNonTranslateTX(AffineTransform paramAffineTransform1, AffineTransform paramAffineTransform2) {
/*  980 */     return (paramAffineTransform1.getScaleX() == paramAffineTransform2.getScaleX() && paramAffineTransform1
/*  981 */       .getShearY() == paramAffineTransform2.getShearY() && paramAffineTransform1
/*  982 */       .getShearX() == paramAffineTransform2.getShearX() && paramAffineTransform1
/*  983 */       .getScaleY() == paramAffineTransform2.getScaleY());
/*      */   }
/*      */ 
/*      */   
/*      */   private void setRenderTransform(double[] paramArrayOfdouble) {
/*  988 */     assert paramArrayOfdouble.length == 4;
/*  989 */     if (!matchTX(paramArrayOfdouble, this.dtx)) {
/*  990 */       resetDTX(new AffineTransform(paramArrayOfdouble));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void setDTX(AffineTransform paramAffineTransform) {
/*  996 */     if (!equalNonTranslateTX(this.dtx, paramAffineTransform)) {
/*  997 */       resetDTX(getNonTranslateTX(paramAffineTransform));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private final void setFRCTX() {
/* 1003 */     if (!equalNonTranslateTX(this.frctx, this.dtx)) {
/* 1004 */       resetDTX(getNonTranslateTX(this.frctx));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void resetDTX(AffineTransform paramAffineTransform) {
/* 1014 */     this.fsref = null;
/* 1015 */     this.dtx = paramAffineTransform;
/* 1016 */     this.invdtx = null;
/* 1017 */     if (!this.dtx.isIdentity()) {
/*      */       try {
/* 1019 */         this.invdtx = this.dtx.createInverse();
/*      */       }
/* 1021 */       catch (NoninvertibleTransformException noninvertibleTransformException) {}
/*      */     }
/*      */ 
/*      */     
/* 1025 */     if (this.gti != null) {
/* 1026 */       this.gti.strikesRef = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StandardGlyphVector(GlyphVector paramGlyphVector, FontRenderContext paramFontRenderContext) {
/* 1037 */     this.font = paramGlyphVector.getFont();
/* 1038 */     this.frc = paramFontRenderContext;
/* 1039 */     initFontData();
/*      */     
/* 1041 */     int i = paramGlyphVector.getNumGlyphs();
/* 1042 */     this.userGlyphs = paramGlyphVector.getGlyphCodes(0, i, null);
/* 1043 */     if (paramGlyphVector instanceof StandardGlyphVector) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1050 */       this.glyphs = this.userGlyphs;
/*      */     } else {
/* 1052 */       this.glyphs = getValidatedGlyphs(this.userGlyphs);
/*      */     } 
/* 1054 */     this.flags = paramGlyphVector.getLayoutFlags() & 0xF;
/*      */     
/* 1056 */     if ((this.flags & 0x2) != 0) {
/* 1057 */       this.positions = paramGlyphVector.getGlyphPositions(0, i + 1, null);
/*      */     }
/*      */     
/* 1060 */     if ((this.flags & 0x8) != 0) {
/* 1061 */       this.charIndices = paramGlyphVector.getGlyphCharIndices(0, i, null);
/*      */     }
/*      */     
/* 1064 */     if ((this.flags & 0x1) != 0) {
/* 1065 */       AffineTransform[] arrayOfAffineTransform = new AffineTransform[i];
/* 1066 */       for (byte b = 0; b < i; b++) {
/* 1067 */         arrayOfAffineTransform[b] = paramGlyphVector.getGlyphTransform(b);
/*      */       }
/*      */       
/* 1070 */       setGlyphTransforms(arrayOfAffineTransform);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int[] getValidatedGlyphs(int[] paramArrayOfint) {
/* 1080 */     int i = paramArrayOfint.length;
/* 1081 */     int[] arrayOfInt = new int[i];
/* 1082 */     for (byte b = 0; b < i; b++) {
/* 1083 */       if (paramArrayOfint[b] == 65534 || paramArrayOfint[b] == 65535) {
/* 1084 */         arrayOfInt[b] = paramArrayOfint[b];
/*      */       } else {
/* 1086 */         arrayOfInt[b] = this.font2D.getValidatedGlyphCode(paramArrayOfint[b]);
/*      */       } 
/*      */     } 
/* 1089 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init(Font paramFont, char[] paramArrayOfchar, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext, int paramInt3) {
/* 1096 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 + paramInt2 > paramArrayOfchar.length) {
/* 1097 */       throw new ArrayIndexOutOfBoundsException("start or count out of bounds");
/*      */     }
/*      */     
/* 1100 */     this.font = paramFont;
/* 1101 */     this.frc = paramFontRenderContext;
/* 1102 */     this.flags = paramInt3;
/*      */     
/* 1104 */     if (getTracking(paramFont) != 0.0F) {
/* 1105 */       addFlags(2);
/*      */     }
/*      */ 
/*      */     
/* 1109 */     if (paramInt1 != 0) {
/* 1110 */       char[] arrayOfChar = new char[paramInt2];
/* 1111 */       System.arraycopy(paramArrayOfchar, paramInt1, arrayOfChar, 0, paramInt2);
/* 1112 */       paramArrayOfchar = arrayOfChar;
/*      */     } 
/*      */     
/* 1115 */     initFontData();
/*      */ 
/*      */ 
/*      */     
/* 1119 */     this.glyphs = new int[paramInt2];
/*      */     
/* 1121 */     this.userGlyphs = this.glyphs;
/* 1122 */     this.font2D.getMapper().charsToGlyphs(paramInt2, paramArrayOfchar, this.glyphs);
/*      */   }
/*      */   
/*      */   private void initFontData() {
/* 1126 */     this.font2D = FontUtilities.getFont2D(this.font);
/* 1127 */     if (this.font2D instanceof FontSubstitution) {
/* 1128 */       this.font2D = ((FontSubstitution)this.font2D).getCompositeFont2D();
/*      */     }
/* 1130 */     float f = this.font.getSize2D();
/* 1131 */     if (this.font.isTransformed()) {
/* 1132 */       this.ftx = this.font.getTransform();
/* 1133 */       if (this.ftx.getTranslateX() != 0.0D || this.ftx.getTranslateY() != 0.0D) {
/* 1134 */         addFlags(2);
/*      */       }
/* 1136 */       this.ftx.setTransform(this.ftx.getScaleX(), this.ftx.getShearY(), this.ftx.getShearX(), this.ftx.getScaleY(), 0.0D, 0.0D);
/* 1137 */       this.ftx.scale(f, f);
/*      */     } else {
/* 1139 */       this.ftx = AffineTransform.getScaleInstance(f, f);
/*      */     } 
/*      */     
/* 1142 */     this.frctx = this.frc.getTransform();
/* 1143 */     resetDTX(getNonTranslateTX(this.frctx));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] internalGetGlyphPositions(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat) {
/* 1160 */     if (paramArrayOffloat == null) {
/* 1161 */       paramArrayOffloat = new float[paramInt3 + paramInt2 * 2];
/*      */     }
/*      */     
/* 1164 */     initPositions();
/*      */ 
/*      */     
/* 1167 */     for (int i = paramInt3, j = paramInt3 + paramInt2 * 2, k = paramInt1 * 2; i < j; i++, k++) {
/* 1168 */       paramArrayOffloat[i] = this.positions[k];
/*      */     }
/*      */     
/* 1171 */     return paramArrayOffloat;
/*      */   }
/*      */   
/*      */   private Rectangle2D getGlyphOutlineBounds(int paramInt) {
/* 1175 */     setFRCTX();
/* 1176 */     initPositions();
/* 1177 */     return getGlyphStrike(paramInt).getGlyphOutlineBounds(this.glyphs[paramInt], this.positions[paramInt * 2], this.positions[paramInt * 2 + 1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Shape getGlyphsOutline(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
/* 1184 */     setFRCTX();
/* 1185 */     initPositions();
/*      */     
/* 1187 */     GeneralPath generalPath = new GeneralPath(1);
/* 1188 */     for (int i = paramInt1, j = paramInt1 + paramInt2, k = paramInt1 * 2; i < j; i++, k += 2) {
/* 1189 */       float f1 = paramFloat1 + this.positions[k];
/* 1190 */       float f2 = paramFloat2 + this.positions[k + 1];
/*      */       
/* 1192 */       getGlyphStrike(i).appendGlyphOutline(this.glyphs[i], generalPath, f1, f2);
/*      */     } 
/*      */     
/* 1195 */     return generalPath;
/*      */   }
/*      */   
/*      */   private Rectangle getGlyphsPixelBounds(FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
/* 1199 */     initPositions();
/*      */     
/* 1201 */     AffineTransform affineTransform = null;
/* 1202 */     if (paramFontRenderContext == null || paramFontRenderContext.equals(this.frc)) {
/* 1203 */       affineTransform = this.frctx;
/*      */     } else {
/* 1205 */       affineTransform = paramFontRenderContext.getTransform();
/*      */     } 
/* 1207 */     setDTX(affineTransform);
/*      */     
/* 1209 */     if (this.gti != null) {
/* 1210 */       return this.gti.getGlyphsPixelBounds(affineTransform, paramFloat1, paramFloat2, paramInt1, paramInt2);
/*      */     }
/*      */     
/* 1213 */     FontStrike fontStrike = (getDefaultStrike()).strike;
/* 1214 */     Rectangle rectangle1 = null;
/* 1215 */     Rectangle rectangle2 = new Rectangle();
/* 1216 */     Point2D.Float float_ = new Point2D.Float();
/* 1217 */     int i = paramInt1 * 2;
/* 1218 */     while (--paramInt2 >= 0) {
/* 1219 */       float_.x = paramFloat1 + this.positions[i++];
/* 1220 */       float_.y = paramFloat2 + this.positions[i++];
/* 1221 */       affineTransform.transform(float_, float_);
/* 1222 */       fontStrike.getGlyphImageBounds(this.glyphs[paramInt1++], float_, rectangle2);
/* 1223 */       if (!rectangle2.isEmpty()) {
/* 1224 */         if (rectangle1 == null) {
/* 1225 */           rectangle1 = new Rectangle(rectangle2); continue;
/*      */         } 
/* 1227 */         rectangle1.add(rectangle2);
/*      */       } 
/*      */     } 
/*      */     
/* 1231 */     return (rectangle1 != null) ? rectangle1 : rectangle2;
/*      */   }
/*      */   
/*      */   private void clearCaches(int paramInt) {
/* 1235 */     if (this.lbcacheRef != null) {
/* 1236 */       Shape[] arrayOfShape = this.lbcacheRef.get();
/* 1237 */       if (arrayOfShape != null) {
/* 1238 */         arrayOfShape[paramInt] = null;
/*      */       }
/*      */     } 
/*      */     
/* 1242 */     if (this.vbcacheRef != null) {
/* 1243 */       Shape[] arrayOfShape = this.vbcacheRef.get();
/* 1244 */       if (arrayOfShape != null) {
/* 1245 */         arrayOfShape[paramInt] = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearCaches() {
/* 1251 */     this.lbcacheRef = null;
/* 1252 */     this.vbcacheRef = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initPositions() {
/* 1299 */     if (this.positions == null) {
/* 1300 */       setFRCTX();
/*      */       
/* 1302 */       this.positions = new float[this.glyphs.length * 2 + 2];
/*      */       
/* 1304 */       Point2D.Float float_1 = null;
/* 1305 */       float f = getTracking(this.font);
/* 1306 */       if (f != 0.0F) {
/* 1307 */         f *= this.font.getSize2D();
/* 1308 */         float_1 = new Point2D.Float(f, 0.0F);
/*      */       } 
/*      */       
/* 1311 */       Point2D.Float float_2 = new Point2D.Float(0.0F, 0.0F);
/* 1312 */       if (this.font.isTransformed()) {
/* 1313 */         AffineTransform affineTransform = this.font.getTransform();
/* 1314 */         affineTransform.transform(float_2, float_2);
/* 1315 */         this.positions[0] = float_2.x;
/* 1316 */         this.positions[1] = float_2.y;
/*      */         
/* 1318 */         if (float_1 != null) {
/* 1319 */           affineTransform.deltaTransform(float_1, float_1);
/*      */         }
/*      */       } 
/* 1322 */       for (byte b1 = 0, b2 = 2; b1 < this.glyphs.length; b1++, b2 += 2) {
/* 1323 */         getGlyphStrike(b1).addDefaultGlyphAdvance(this.glyphs[b1], float_2);
/* 1324 */         if (float_1 != null) {
/* 1325 */           float_2.x += float_1.x;
/* 1326 */           float_2.y += float_1.y;
/*      */         } 
/* 1328 */         this.positions[b2] = float_2.x;
/* 1329 */         this.positions[b2 + 1] = float_2.y;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addFlags(int paramInt) {
/* 1338 */     this.flags = getLayoutFlags() | paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void clearFlags(int paramInt) {
/* 1345 */     this.flags = getLayoutFlags() & (paramInt ^ 0xFFFFFFFF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GlyphStrike getGlyphStrike(int paramInt) {
/* 1352 */     if (this.gti == null) {
/* 1353 */       return getDefaultStrike();
/*      */     }
/* 1355 */     return this.gti.getStrike(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private GlyphStrike getDefaultStrike() {
/* 1361 */     GlyphStrike glyphStrike = null;
/* 1362 */     if (this.fsref != null) {
/* 1363 */       glyphStrike = this.fsref.get();
/*      */     }
/* 1365 */     if (glyphStrike == null) {
/* 1366 */       glyphStrike = GlyphStrike.create(this, this.dtx, null);
/* 1367 */       this.fsref = new SoftReference<>(glyphStrike);
/*      */     } 
/* 1369 */     return glyphStrike;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static final class GlyphTransformInfo
/*      */   {
/*      */     StandardGlyphVector sgv;
/*      */ 
/*      */     
/*      */     int[] indices;
/*      */ 
/*      */     
/*      */     double[] transforms;
/*      */     
/*      */     SoftReference strikesRef;
/*      */     
/*      */     boolean haveAllStrikes;
/*      */ 
/*      */     
/*      */     GlyphTransformInfo(StandardGlyphVector param1StandardGlyphVector) {
/* 1390 */       this.sgv = param1StandardGlyphVector;
/*      */     }
/*      */ 
/*      */     
/*      */     GlyphTransformInfo(StandardGlyphVector param1StandardGlyphVector, GlyphTransformInfo param1GlyphTransformInfo) {
/* 1395 */       this.sgv = param1StandardGlyphVector;
/*      */       
/* 1397 */       this.indices = (param1GlyphTransformInfo.indices == null) ? null : (int[])param1GlyphTransformInfo.indices.clone();
/* 1398 */       this.transforms = (param1GlyphTransformInfo.transforms == null) ? null : (double[])param1GlyphTransformInfo.transforms.clone();
/* 1399 */       this.strikesRef = null;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(GlyphTransformInfo param1GlyphTransformInfo) {
/* 1404 */       if (param1GlyphTransformInfo == null) {
/* 1405 */         return false;
/*      */       }
/* 1407 */       if (param1GlyphTransformInfo == this) {
/* 1408 */         return true;
/*      */       }
/* 1410 */       if (this.indices.length != param1GlyphTransformInfo.indices.length) {
/* 1411 */         return false;
/*      */       }
/* 1413 */       if (this.transforms.length != param1GlyphTransformInfo.transforms.length) {
/* 1414 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1421 */       for (byte b = 0; b < this.indices.length; b++) {
/* 1422 */         int i = this.indices[b];
/* 1423 */         int j = param1GlyphTransformInfo.indices[b];
/* 1424 */         if (((i == 0) ? true : false) != ((j == 0) ? true : false)) {
/* 1425 */           return false;
/*      */         }
/* 1427 */         if (i != 0) {
/* 1428 */           i *= 6;
/* 1429 */           j *= 6;
/* 1430 */           for (byte b1 = 6; b1 > 0; b1--) {
/* 1431 */             if (this.indices[--i] != param1GlyphTransformInfo.indices[--j]) {
/* 1432 */               return false;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/* 1437 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setGlyphTransform(int param1Int, AffineTransform param1AffineTransform) {
/* 1453 */       double[] arrayOfDouble = new double[6];
/* 1454 */       boolean bool = true;
/*      */ 
/*      */       
/* 1457 */       arrayOfDouble[3] = 1.0D; arrayOfDouble[0] = 1.0D;
/*      */ 
/*      */       
/* 1460 */       bool = false;
/* 1461 */       param1AffineTransform.getMatrix(arrayOfDouble);
/*      */ 
/*      */       
/* 1464 */       if (this.indices == null) {
/* 1465 */         if (bool) {
/*      */           return;
/*      */         }
/*      */         
/* 1469 */         this.indices = new int[this.sgv.glyphs.length];
/* 1470 */         this.indices[param1Int] = 1;
/* 1471 */         this.transforms = arrayOfDouble;
/*      */       } else {
/* 1473 */         boolean bool1 = false;
/* 1474 */         int i = -1;
/* 1475 */         if (bool) {
/* 1476 */           i = 0;
/*      */         } else {
/* 1478 */           bool1 = true;
/*      */           
/*      */           byte b;
/* 1481 */           for (b = 0; b < this.transforms.length; ) {
/* 1482 */             for (byte b1 = 0; b1 < 6; b1++) {
/* 1483 */               if (this.transforms[b + b1] != arrayOfDouble[b1]) {
/*      */                 b += 6; continue;
/*      */               } 
/*      */             } 
/* 1487 */             bool1 = false;
/*      */           } 
/*      */           
/* 1490 */           i = b / 6 + 1;
/*      */         } 
/*      */ 
/*      */         
/* 1494 */         int j = this.indices[param1Int];
/* 1495 */         if (i != j) {
/*      */           
/* 1497 */           boolean bool2 = false;
/* 1498 */           if (j != 0) {
/* 1499 */             bool2 = true;
/* 1500 */             for (byte b = 0; b < this.indices.length; b++) {
/* 1501 */               if (this.indices[b] == j && b != param1Int) {
/* 1502 */                 bool2 = false;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1508 */           if (bool2 && bool1) {
/* 1509 */             i = j;
/* 1510 */             System.arraycopy(arrayOfDouble, 0, this.transforms, (i - 1) * 6, 6);
/* 1511 */           } else if (bool2) {
/* 1512 */             if (this.transforms.length == 6) {
/* 1513 */               this.indices = null;
/* 1514 */               this.transforms = null;
/*      */               
/* 1516 */               this.sgv.clearCaches(param1Int);
/* 1517 */               this.sgv.clearFlags(1);
/* 1518 */               this.strikesRef = null;
/*      */               
/*      */               return;
/*      */             } 
/*      */             
/* 1523 */             double[] arrayOfDouble1 = new double[this.transforms.length - 6];
/* 1524 */             System.arraycopy(this.transforms, 0, arrayOfDouble1, 0, (j - 1) * 6);
/* 1525 */             System.arraycopy(this.transforms, j * 6, arrayOfDouble1, (j - 1) * 6, this.transforms.length - j * 6);
/*      */             
/* 1527 */             this.transforms = arrayOfDouble1;
/*      */ 
/*      */             
/* 1530 */             for (byte b = 0; b < this.indices.length; b++) {
/* 1531 */               if (this.indices[b] > j) {
/* 1532 */                 this.indices[b] = this.indices[b] - 1;
/*      */               }
/*      */             } 
/* 1535 */             if (i > j) {
/* 1536 */               i--;
/*      */             }
/* 1538 */           } else if (bool1) {
/* 1539 */             double[] arrayOfDouble1 = new double[this.transforms.length + 6];
/* 1540 */             System.arraycopy(this.transforms, 0, arrayOfDouble1, 0, this.transforms.length);
/* 1541 */             System.arraycopy(arrayOfDouble, 0, arrayOfDouble1, this.transforms.length, 6);
/* 1542 */             this.transforms = arrayOfDouble1;
/*      */           } 
/*      */           
/* 1545 */           this.indices[param1Int] = i;
/*      */         } 
/*      */       } 
/*      */       
/* 1549 */       this.sgv.clearCaches(param1Int);
/* 1550 */       this.sgv.addFlags(1);
/* 1551 */       this.strikesRef = null;
/*      */     }
/*      */ 
/*      */     
/*      */     AffineTransform getGlyphTransform(int param1Int) {
/* 1556 */       int i = this.indices[param1Int];
/* 1557 */       if (i == 0) {
/* 1558 */         return null;
/*      */       }
/*      */       
/* 1561 */       int j = (i - 1) * 6;
/* 1562 */       return new AffineTransform(this.transforms[j + 0], this.transforms[j + 1], this.transforms[j + 2], this.transforms[j + 3], this.transforms[j + 4], this.transforms[j + 5]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int transformCount() {
/* 1571 */       if (this.transforms == null) {
/* 1572 */         return 0;
/*      */       }
/* 1574 */       return this.transforms.length / 6;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object setupGlyphImages(long[] param1ArrayOflong, float[] param1ArrayOffloat, AffineTransform param1AffineTransform) {
/* 1595 */       int i = this.sgv.glyphs.length;
/*      */       
/* 1597 */       StandardGlyphVector.GlyphStrike[] arrayOfGlyphStrike = getAllStrikes();
/* 1598 */       for (byte b = 0; b < i; b++) {
/* 1599 */         StandardGlyphVector.GlyphStrike glyphStrike = arrayOfGlyphStrike[this.indices[b]];
/* 1600 */         int j = this.sgv.glyphs[b];
/* 1601 */         param1ArrayOflong[b] = glyphStrike.strike.getGlyphImagePtr(j);
/*      */         
/* 1603 */         glyphStrike.getGlyphPosition(j, b * 2, this.sgv.positions, param1ArrayOffloat);
/*      */       } 
/* 1605 */       param1AffineTransform.transform(param1ArrayOffloat, 0, param1ArrayOffloat, 0, i);
/*      */       
/* 1607 */       return arrayOfGlyphStrike;
/*      */     }
/*      */     
/*      */     Rectangle getGlyphsPixelBounds(AffineTransform param1AffineTransform, float param1Float1, float param1Float2, int param1Int1, int param1Int2) {
/* 1611 */       Rectangle rectangle1 = null;
/* 1612 */       Rectangle rectangle2 = new Rectangle();
/* 1613 */       Point2D.Float float_ = new Point2D.Float();
/* 1614 */       int i = param1Int1 * 2;
/* 1615 */       while (--param1Int2 >= 0) {
/* 1616 */         StandardGlyphVector.GlyphStrike glyphStrike = getStrike(param1Int1);
/* 1617 */         float_.x = param1Float1 + this.sgv.positions[i++] + glyphStrike.dx;
/* 1618 */         float_.y = param1Float2 + this.sgv.positions[i++] + glyphStrike.dy;
/* 1619 */         param1AffineTransform.transform(float_, float_);
/* 1620 */         glyphStrike.strike.getGlyphImageBounds(this.sgv.glyphs[param1Int1++], float_, rectangle2);
/* 1621 */         if (!rectangle2.isEmpty()) {
/* 1622 */           if (rectangle1 == null) {
/* 1623 */             rectangle1 = new Rectangle(rectangle2); continue;
/*      */           } 
/* 1625 */           rectangle1.add(rectangle2);
/*      */         } 
/*      */       } 
/*      */       
/* 1629 */       return (rectangle1 != null) ? rectangle1 : rectangle2;
/*      */     }
/*      */     
/*      */     StandardGlyphVector.GlyphStrike getStrike(int param1Int) {
/* 1633 */       if (this.indices != null) {
/* 1634 */         StandardGlyphVector.GlyphStrike[] arrayOfGlyphStrike = getStrikeArray();
/* 1635 */         return getStrikeAtIndex(arrayOfGlyphStrike, this.indices[param1Int]);
/*      */       } 
/* 1637 */       return this.sgv.getDefaultStrike();
/*      */     }
/*      */     
/*      */     private StandardGlyphVector.GlyphStrike[] getAllStrikes() {
/* 1641 */       if (this.indices == null) {
/* 1642 */         return null;
/*      */       }
/*      */       
/* 1645 */       StandardGlyphVector.GlyphStrike[] arrayOfGlyphStrike = getStrikeArray();
/* 1646 */       if (!this.haveAllStrikes) {
/* 1647 */         for (byte b = 0; b < arrayOfGlyphStrike.length; b++) {
/* 1648 */           getStrikeAtIndex(arrayOfGlyphStrike, b);
/*      */         }
/* 1650 */         this.haveAllStrikes = true;
/*      */       } 
/*      */       
/* 1653 */       return arrayOfGlyphStrike;
/*      */     }
/*      */     
/*      */     private StandardGlyphVector.GlyphStrike[] getStrikeArray() {
/* 1657 */       StandardGlyphVector.GlyphStrike[] arrayOfGlyphStrike = null;
/* 1658 */       if (this.strikesRef != null) {
/* 1659 */         arrayOfGlyphStrike = this.strikesRef.get();
/*      */       }
/* 1661 */       if (arrayOfGlyphStrike == null) {
/* 1662 */         this.haveAllStrikes = false;
/* 1663 */         arrayOfGlyphStrike = new StandardGlyphVector.GlyphStrike[transformCount() + 1];
/* 1664 */         this.strikesRef = new SoftReference<>(arrayOfGlyphStrike);
/*      */       } 
/*      */       
/* 1667 */       return arrayOfGlyphStrike;
/*      */     }
/*      */     
/*      */     private StandardGlyphVector.GlyphStrike getStrikeAtIndex(StandardGlyphVector.GlyphStrike[] param1ArrayOfGlyphStrike, int param1Int) {
/* 1671 */       StandardGlyphVector.GlyphStrike glyphStrike = param1ArrayOfGlyphStrike[param1Int];
/* 1672 */       if (glyphStrike == null) {
/* 1673 */         if (param1Int == 0) {
/* 1674 */           glyphStrike = this.sgv.getDefaultStrike();
/*      */         } else {
/* 1676 */           int i = (param1Int - 1) * 6;
/* 1677 */           AffineTransform affineTransform = new AffineTransform(this.transforms[i], this.transforms[i + 1], this.transforms[i + 2], this.transforms[i + 3], this.transforms[i + 4], this.transforms[i + 5]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1684 */           glyphStrike = StandardGlyphVector.GlyphStrike.create(this.sgv, this.sgv.dtx, affineTransform);
/*      */         } 
/* 1686 */         param1ArrayOfGlyphStrike[param1Int] = glyphStrike;
/*      */       } 
/* 1688 */       return glyphStrike;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static final class GlyphStrike
/*      */   {
/*      */     StandardGlyphVector sgv;
/*      */     
/*      */     FontStrike strike;
/*      */     
/*      */     float dx;
/*      */     
/*      */     float dy;
/*      */     
/*      */     static GlyphStrike create(StandardGlyphVector param1StandardGlyphVector, AffineTransform param1AffineTransform1, AffineTransform param1AffineTransform2) {
/* 1704 */       float f1 = 0.0F;
/* 1705 */       float f2 = 0.0F;
/*      */       
/* 1707 */       AffineTransform affineTransform = param1StandardGlyphVector.ftx;
/* 1708 */       if (!param1AffineTransform1.isIdentity() || param1AffineTransform2 != null) {
/* 1709 */         affineTransform = new AffineTransform(param1StandardGlyphVector.ftx);
/* 1710 */         if (param1AffineTransform2 != null) {
/* 1711 */           affineTransform.preConcatenate(param1AffineTransform2);
/* 1712 */           f1 = (float)affineTransform.getTranslateX();
/* 1713 */           f2 = (float)affineTransform.getTranslateY();
/*      */         } 
/* 1715 */         if (!param1AffineTransform1.isIdentity()) {
/* 1716 */           affineTransform.preConcatenate(param1AffineTransform1);
/*      */         }
/*      */       } 
/*      */       
/* 1720 */       int i = 1;
/* 1721 */       Object object = param1StandardGlyphVector.frc.getAntiAliasingHint();
/* 1722 */       if (object == RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1727 */         if (!affineTransform.isIdentity() && (affineTransform
/* 1728 */           .getType() & 0xFFFFFFFE) != 0) {
/* 1729 */           double d = affineTransform.getShearX();
/* 1730 */           if (d != 0.0D) {
/* 1731 */             double d1 = affineTransform.getScaleY();
/*      */             
/* 1733 */             i = (int)Math.sqrt(d * d + d1 * d1);
/*      */           } else {
/* 1735 */             i = (int)Math.abs(affineTransform.getScaleY());
/*      */           } 
/*      */         } 
/*      */       }
/* 1739 */       int j = FontStrikeDesc.getAAHintIntVal(object, param1StandardGlyphVector.font2D, i);
/*      */       
/* 1741 */       int k = FontStrikeDesc.getFMHintIntVal(param1StandardGlyphVector.frc.getFractionalMetricsHint());
/*      */ 
/*      */       
/* 1744 */       FontStrikeDesc fontStrikeDesc = new FontStrikeDesc(param1AffineTransform1, affineTransform, param1StandardGlyphVector.font.getStyle(), j, k);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1749 */       Font2D font2D = param1StandardGlyphVector.font2D;
/* 1750 */       if (font2D instanceof FontSubstitution) {
/* 1751 */         font2D = ((FontSubstitution)font2D).getCompositeFont2D();
/*      */       }
/* 1753 */       FontStrike fontStrike = font2D.handle.font2D.getStrike(fontStrikeDesc);
/*      */       
/* 1755 */       return new GlyphStrike(param1StandardGlyphVector, fontStrike, f1, f2);
/*      */     }
/*      */     
/*      */     private GlyphStrike(StandardGlyphVector param1StandardGlyphVector, FontStrike param1FontStrike, float param1Float1, float param1Float2) {
/* 1759 */       this.sgv = param1StandardGlyphVector;
/* 1760 */       this.strike = param1FontStrike;
/* 1761 */       this.dx = param1Float1;
/* 1762 */       this.dy = param1Float2;
/*      */     }
/*      */     
/*      */     void getADL(StandardGlyphVector.ADL param1ADL) {
/* 1766 */       StrikeMetrics strikeMetrics = this.strike.getFontMetrics();
/* 1767 */       Point2D.Float float_ = null;
/* 1768 */       if (this.sgv.font.isTransformed()) {
/* 1769 */         float_ = new Point2D.Float();
/* 1770 */         float_.x = (float)this.sgv.font.getTransform().getTranslateX();
/* 1771 */         float_.y = (float)this.sgv.font.getTransform().getTranslateY();
/*      */       } 
/*      */       
/* 1774 */       param1ADL.ascentX = -strikeMetrics.ascentX;
/* 1775 */       param1ADL.ascentY = -strikeMetrics.ascentY;
/* 1776 */       param1ADL.descentX = strikeMetrics.descentX;
/* 1777 */       param1ADL.descentY = strikeMetrics.descentY;
/* 1778 */       param1ADL.leadingX = strikeMetrics.leadingX;
/* 1779 */       param1ADL.leadingY = strikeMetrics.leadingY;
/*      */     }
/*      */     
/*      */     void getGlyphPosition(int param1Int1, int param1Int2, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2) {
/* 1783 */       param1ArrayOffloat2[param1Int2] = param1ArrayOffloat1[param1Int2] + this.dx;
/* 1784 */       param1Int2++;
/* 1785 */       param1ArrayOffloat2[param1Int2] = param1ArrayOffloat1[param1Int2] + this.dy;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void addDefaultGlyphAdvance(int param1Int, Point2D.Float param1Float) {
/* 1791 */       Point2D.Float float_ = this.strike.getGlyphMetrics(param1Int);
/* 1792 */       param1Float.x += float_.x + this.dx;
/* 1793 */       param1Float.y += float_.y + this.dy;
/*      */     }
/*      */     
/*      */     Rectangle2D getGlyphOutlineBounds(int param1Int, float param1Float1, float param1Float2) {
/* 1797 */       Rectangle2D rectangle2D = null;
/* 1798 */       if (this.sgv.invdtx == null) {
/* 1799 */         rectangle2D = new Rectangle2D.Float();
/* 1800 */         rectangle2D.setRect(this.strike.getGlyphOutlineBounds(param1Int));
/*      */       } else {
/* 1802 */         GeneralPath generalPath = this.strike.getGlyphOutline(param1Int, 0.0F, 0.0F);
/* 1803 */         generalPath.transform(this.sgv.invdtx);
/* 1804 */         rectangle2D = generalPath.getBounds2D();
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1814 */       if (!rectangle2D.isEmpty()) {
/* 1815 */         rectangle2D.setRect(rectangle2D.getMinX() + param1Float1 + this.dx, rectangle2D
/* 1816 */             .getMinY() + param1Float2 + this.dy, rectangle2D
/* 1817 */             .getWidth(), rectangle2D.getHeight());
/*      */       }
/* 1819 */       return rectangle2D;
/*      */     }
/*      */ 
/*      */     
/*      */     void appendGlyphOutline(int param1Int, GeneralPath param1GeneralPath, float param1Float1, float param1Float2) {
/* 1824 */       GeneralPath generalPath = null;
/* 1825 */       if (this.sgv.invdtx == null) {
/* 1826 */         generalPath = this.strike.getGlyphOutline(param1Int, param1Float1 + this.dx, param1Float2 + this.dy);
/*      */       } else {
/* 1828 */         generalPath = this.strike.getGlyphOutline(param1Int, 0.0F, 0.0F);
/* 1829 */         generalPath.transform(this.sgv.invdtx);
/* 1830 */         generalPath.transform(AffineTransform.getTranslateInstance((param1Float1 + this.dx), (param1Float2 + this.dy)));
/*      */       } 
/* 1832 */       PathIterator pathIterator = generalPath.getPathIterator((AffineTransform)null);
/* 1833 */       param1GeneralPath.append(pathIterator, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1838 */     return appendString(null).toString();
/*      */   }
/*      */   
/*      */   StringBuffer appendString(StringBuffer paramStringBuffer) {
/* 1842 */     if (paramStringBuffer == null) {
/* 1843 */       paramStringBuffer = new StringBuffer();
/*      */     }
/*      */     try {
/* 1846 */       paramStringBuffer.append("SGV{font: ");
/* 1847 */       paramStringBuffer.append(this.font.toString());
/* 1848 */       paramStringBuffer.append(", frc: ");
/* 1849 */       paramStringBuffer.append(this.frc.toString());
/* 1850 */       paramStringBuffer.append(", glyphs: (");
/* 1851 */       paramStringBuffer.append(this.glyphs.length);
/* 1852 */       paramStringBuffer.append(")["); byte b;
/* 1853 */       for (b = 0; b < this.glyphs.length; b++) {
/* 1854 */         if (b > 0) {
/* 1855 */           paramStringBuffer.append(", ");
/*      */         }
/* 1857 */         paramStringBuffer.append(Integer.toHexString(this.glyphs[b]));
/*      */       } 
/* 1859 */       paramStringBuffer.append("]");
/* 1860 */       if (this.positions != null) {
/* 1861 */         paramStringBuffer.append(", positions: (");
/* 1862 */         paramStringBuffer.append(this.positions.length);
/* 1863 */         paramStringBuffer.append(")[");
/* 1864 */         for (b = 0; b < this.positions.length; b += 2) {
/* 1865 */           if (b > 0) {
/* 1866 */             paramStringBuffer.append(", ");
/*      */           }
/* 1868 */           paramStringBuffer.append(this.positions[b]);
/* 1869 */           paramStringBuffer.append("@");
/* 1870 */           paramStringBuffer.append(this.positions[b + 1]);
/*      */         } 
/* 1872 */         paramStringBuffer.append("]");
/*      */       } 
/* 1874 */       if (this.charIndices != null) {
/* 1875 */         paramStringBuffer.append(", indices: (");
/* 1876 */         paramStringBuffer.append(this.charIndices.length);
/* 1877 */         paramStringBuffer.append(")[");
/* 1878 */         for (b = 0; b < this.charIndices.length; b++) {
/* 1879 */           if (b > 0) {
/* 1880 */             paramStringBuffer.append(", ");
/*      */           }
/* 1882 */           paramStringBuffer.append(this.charIndices[b]);
/*      */         } 
/* 1884 */         paramStringBuffer.append("]");
/*      */       } 
/* 1886 */       paramStringBuffer.append(", flags:");
/* 1887 */       if (getLayoutFlags() == 0) {
/* 1888 */         paramStringBuffer.append(" default");
/*      */       } else {
/* 1890 */         if ((this.flags & 0x1) != 0) {
/* 1891 */           paramStringBuffer.append(" tx");
/*      */         }
/* 1893 */         if ((this.flags & 0x2) != 0) {
/* 1894 */           paramStringBuffer.append(" pos");
/*      */         }
/* 1896 */         if ((this.flags & 0x4) != 0) {
/* 1897 */           paramStringBuffer.append(" rtl");
/*      */         }
/* 1899 */         if ((this.flags & 0x8) != 0) {
/* 1900 */           paramStringBuffer.append(" complex");
/*      */         }
/*      */       }
/*      */     
/* 1904 */     } catch (Exception exception) {
/* 1905 */       paramStringBuffer.append(" " + exception.getMessage());
/*      */     } 
/* 1907 */     paramStringBuffer.append("}");
/*      */     
/* 1909 */     return paramStringBuffer;
/*      */   }
/*      */   
/*      */   static class ADL {
/*      */     public float ascentX;
/*      */     public float ascentY;
/*      */     public float descentX;
/*      */     public float descentY;
/*      */     public float leadingX;
/*      */     public float leadingY;
/*      */     
/*      */     public String toString() {
/* 1921 */       return toStringBuffer(null).toString();
/*      */     }
/*      */     
/*      */     protected StringBuffer toStringBuffer(StringBuffer param1StringBuffer) {
/* 1925 */       if (param1StringBuffer == null) {
/* 1926 */         param1StringBuffer = new StringBuffer();
/*      */       }
/* 1928 */       param1StringBuffer.append("ax: ");
/* 1929 */       param1StringBuffer.append(this.ascentX);
/* 1930 */       param1StringBuffer.append(" ay: ");
/* 1931 */       param1StringBuffer.append(this.ascentY);
/* 1932 */       param1StringBuffer.append(" dx: ");
/* 1933 */       param1StringBuffer.append(this.descentX);
/* 1934 */       param1StringBuffer.append(" dy: ");
/* 1935 */       param1StringBuffer.append(this.descentY);
/* 1936 */       param1StringBuffer.append(" lx: ");
/* 1937 */       param1StringBuffer.append(this.leadingX);
/* 1938 */       param1StringBuffer.append(" ly: ");
/* 1939 */       param1StringBuffer.append(this.leadingY);
/*      */       
/* 1941 */       return param1StringBuffer;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/StandardGlyphVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */