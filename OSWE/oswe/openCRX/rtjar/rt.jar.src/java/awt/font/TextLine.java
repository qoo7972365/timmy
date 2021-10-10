/*      */ package java.awt.font;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.Bidi;
/*      */ import java.util.Map;
/*      */ import sun.font.AttributeValues;
/*      */ import sun.font.BidiUtils;
/*      */ import sun.font.CoreMetrics;
/*      */ import sun.font.Decoration;
/*      */ import sun.font.ExtendedTextLabel;
/*      */ import sun.font.FontResolver;
/*      */ import sun.font.GraphicComponent;
/*      */ import sun.font.LayoutPathImpl;
/*      */ import sun.font.TextLabelFactory;
/*      */ import sun.font.TextLineComponent;
/*      */ import sun.text.CodePointIterator;
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
/*      */ final class TextLine
/*      */ {
/*      */   private TextLineComponent[] fComponents;
/*      */   private float[] fBaselineOffsets;
/*      */   private int[] fComponentVisualOrder;
/*      */   private float[] locs;
/*      */   private char[] fChars;
/*      */   private int fCharsStart;
/*      */   private int fCharsLimit;
/*      */   private int[] fCharVisualOrder;
/*      */   private int[] fCharLogicalOrder;
/*      */   private byte[] fCharLevels;
/*      */   private boolean fIsDirectionLTR;
/*      */   private LayoutPathImpl lp;
/*      */   private boolean isSimple;
/*      */   private Rectangle pixelBounds;
/*      */   private FontRenderContext frc;
/*      */   
/*      */   static final class TextLineMetrics
/*      */   {
/*      */     public final float ascent;
/*      */     public final float descent;
/*      */     public final float leading;
/*      */     public final float advance;
/*      */     
/*      */     public TextLineMetrics(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/*   79 */       this.ascent = param1Float1;
/*   80 */       this.descent = param1Float2;
/*   81 */       this.leading = param1Float3;
/*   82 */       this.advance = param1Float4;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   private TextLineMetrics fMetrics = null;
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
/*      */   public TextLine(FontRenderContext paramFontRenderContext, TextLineComponent[] paramArrayOfTextLineComponent, float[] paramArrayOffloat, char[] paramArrayOfchar, int paramInt1, int paramInt2, int[] paramArrayOfint, byte[] paramArrayOfbyte, boolean paramBoolean) {
/*  114 */     int[] arrayOfInt = computeComponentOrder(paramArrayOfTextLineComponent, paramArrayOfint);
/*      */ 
/*      */     
/*  117 */     this.frc = paramFontRenderContext;
/*  118 */     this.fComponents = paramArrayOfTextLineComponent;
/*  119 */     this.fBaselineOffsets = paramArrayOffloat;
/*  120 */     this.fComponentVisualOrder = arrayOfInt;
/*  121 */     this.fChars = paramArrayOfchar;
/*  122 */     this.fCharsStart = paramInt1;
/*  123 */     this.fCharsLimit = paramInt2;
/*  124 */     this.fCharLogicalOrder = paramArrayOfint;
/*  125 */     this.fCharLevels = paramArrayOfbyte;
/*  126 */     this.fIsDirectionLTR = paramBoolean;
/*  127 */     checkCtorArgs();
/*      */     
/*  129 */     init();
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkCtorArgs() {
/*  134 */     int i = 0;
/*  135 */     for (byte b = 0; b < this.fComponents.length; b++) {
/*  136 */       i += this.fComponents[b].getNumCharacters();
/*      */     }
/*      */     
/*  139 */     if (i != characterCount()) {
/*  140 */       throw new IllegalArgumentException("Invalid TextLine!  char count is different from sum of char counts of components.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/*  151 */     float f1 = 0.0F;
/*  152 */     float f2 = 0.0F;
/*  153 */     float f3 = 0.0F;
/*  154 */     float f4 = 0.0F;
/*      */ 
/*      */     
/*  157 */     float f5 = 0.0F;
/*  158 */     float f6 = 0.0F;
/*      */ 
/*      */ 
/*      */     
/*  162 */     boolean bool = false;
/*      */     
/*  164 */     this.isSimple = true;
/*      */     
/*  166 */     for (byte b1 = 0; b1 < this.fComponents.length; b1++) {
/*  167 */       TextLineComponent textLineComponent = this.fComponents[b1];
/*      */       
/*  169 */       this.isSimple &= textLineComponent.isSimple();
/*      */       
/*  171 */       CoreMetrics coreMetrics1 = textLineComponent.getCoreMetrics();
/*      */       
/*  173 */       byte b = (byte)coreMetrics1.baselineIndex;
/*      */       
/*  175 */       if (b >= 0) {
/*  176 */         float f9 = this.fBaselineOffsets[b];
/*      */         
/*  178 */         f1 = Math.max(f1, -f9 + coreMetrics1.ascent);
/*      */         
/*  180 */         float f10 = f9 + coreMetrics1.descent;
/*  181 */         f2 = Math.max(f2, f10);
/*      */         
/*  183 */         f3 = Math.max(f3, f10 + coreMetrics1.leading);
/*      */       } else {
/*      */         
/*  186 */         bool = true;
/*  187 */         float f9 = coreMetrics1.ascent + coreMetrics1.descent;
/*  188 */         float f10 = f9 + coreMetrics1.leading;
/*  189 */         f5 = Math.max(f5, f9);
/*  190 */         f6 = Math.max(f6, f10);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  195 */     if (bool) {
/*  196 */       if (f5 > f1 + f2) {
/*  197 */         f2 = f5 - f1;
/*      */       }
/*  199 */       if (f6 > f1 + f3) {
/*  200 */         f3 = f6 - f1;
/*      */       }
/*      */     } 
/*      */     
/*  204 */     f3 -= f2;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  209 */     if (bool)
/*      */     {
/*      */       
/*  212 */       this.fBaselineOffsets = new float[] { this.fBaselineOffsets[0], this.fBaselineOffsets[1], this.fBaselineOffsets[2], f2, -f1 };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  221 */     float f7 = 0.0F;
/*  222 */     float f8 = 0.0F;
/*  223 */     CoreMetrics coreMetrics = null;
/*      */     
/*  225 */     int i = 0;
/*  226 */     this.locs = new float[this.fComponents.length * 2 + 2];
/*      */     
/*  228 */     for (byte b2 = 0, b3 = 0; b2 < this.fComponents.length; b2++, b3 += 2) {
/*  229 */       TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b2)];
/*  230 */       CoreMetrics coreMetrics1 = textLineComponent.getCoreMetrics();
/*      */       
/*  232 */       if (coreMetrics != null && (coreMetrics.italicAngle != 0.0F || coreMetrics1.italicAngle != 0.0F) && (coreMetrics.italicAngle != coreMetrics1.italicAngle || coreMetrics.baselineIndex != coreMetrics1.baselineIndex || coreMetrics.ssOffset != coreMetrics1.ssOffset)) {
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
/*  246 */         float f9 = coreMetrics.effectiveBaselineOffset(this.fBaselineOffsets);
/*  247 */         float f10 = f9 - coreMetrics.ascent;
/*  248 */         float f11 = f9 + coreMetrics.descent;
/*      */ 
/*      */         
/*  251 */         float f12 = coreMetrics1.effectiveBaselineOffset(this.fBaselineOffsets);
/*  252 */         float f13 = f12 - coreMetrics1.ascent;
/*  253 */         float f14 = f12 + coreMetrics1.descent;
/*      */ 
/*      */         
/*  256 */         float f15 = Math.max(f10, f13);
/*  257 */         float f16 = Math.min(f11, f14);
/*      */ 
/*      */         
/*  260 */         float f17 = coreMetrics.italicAngle * (f9 - f15);
/*  261 */         float f18 = coreMetrics.italicAngle * (f9 - f16);
/*      */         
/*  263 */         float f19 = coreMetrics1.italicAngle * (f12 - f15);
/*  264 */         float f20 = coreMetrics1.italicAngle * (f12 - f16);
/*      */ 
/*      */         
/*  267 */         float f21 = f17 - f19;
/*  268 */         float f22 = f18 - f20;
/*  269 */         float f23 = Math.max(f21, f22);
/*      */         
/*  271 */         f7 += f23;
/*  272 */         f8 = f12;
/*      */       } else {
/*      */         
/*  275 */         f8 = coreMetrics1.effectiveBaselineOffset(this.fBaselineOffsets);
/*      */       } 
/*      */       
/*  278 */       this.locs[b3] = f7;
/*  279 */       this.locs[b3 + 1] = f8;
/*      */       
/*  281 */       f7 += textLineComponent.getAdvance();
/*  282 */       coreMetrics = coreMetrics1;
/*      */       
/*  284 */       i |= (textLineComponent.getBaselineTransform() != null) ? 1 : 0;
/*      */     } 
/*      */ 
/*      */     
/*  288 */     if (coreMetrics.italicAngle != 0.0F) {
/*  289 */       float f12, f9 = coreMetrics.effectiveBaselineOffset(this.fBaselineOffsets);
/*  290 */       float f10 = f9 - coreMetrics.ascent;
/*  291 */       float f11 = f9 + coreMetrics.descent;
/*  292 */       f9 += coreMetrics.ssOffset;
/*      */ 
/*      */       
/*  295 */       if (coreMetrics.italicAngle > 0.0F) {
/*  296 */         f12 = f9 + coreMetrics.ascent;
/*      */       } else {
/*  298 */         f12 = f9 - coreMetrics.descent;
/*      */       } 
/*  300 */       f12 *= coreMetrics.italicAngle;
/*      */       
/*  302 */       f7 += f12;
/*      */     } 
/*  304 */     this.locs[this.locs.length - 2] = f7;
/*      */ 
/*      */ 
/*      */     
/*  308 */     f4 = f7;
/*  309 */     this.fMetrics = new TextLineMetrics(f1, f2, f3, f4);
/*      */ 
/*      */     
/*  312 */     if (i != 0) {
/*  313 */       this.isSimple = false;
/*      */       
/*  315 */       Point2D.Double double_ = new Point2D.Double();
/*  316 */       double d1 = 0.0D, d2 = 0.0D;
/*  317 */       LayoutPathImpl.SegmentPathBuilder segmentPathBuilder = new LayoutPathImpl.SegmentPathBuilder();
/*  318 */       segmentPathBuilder.moveTo(this.locs[0], 0.0D);
/*  319 */       for (byte b4 = 0, b5 = 0; b4 < this.fComponents.length; b4++, b5 += 2) {
/*  320 */         TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b4)];
/*  321 */         AffineTransform affineTransform = textLineComponent.getBaselineTransform();
/*  322 */         if (affineTransform != null && (affineTransform
/*  323 */           .getType() & 0x1) != 0) {
/*  324 */           double d3 = affineTransform.getTranslateX();
/*  325 */           double d4 = affineTransform.getTranslateY();
/*  326 */           segmentPathBuilder.moveTo(d1 += d3, d2 += d4);
/*      */         } 
/*  328 */         double_.x = (this.locs[b5 + 2] - this.locs[b5]);
/*  329 */         double_.y = 0.0D;
/*  330 */         if (affineTransform != null) {
/*  331 */           affineTransform.deltaTransform(double_, double_);
/*      */         }
/*  333 */         segmentPathBuilder.lineTo(d1 += double_.x, d2 += double_.y);
/*      */       } 
/*  335 */       this.lp = segmentPathBuilder.complete();
/*      */       
/*  337 */       if (this.lp == null) {
/*  338 */         TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(0)];
/*  339 */         AffineTransform affineTransform = textLineComponent.getBaselineTransform();
/*  340 */         if (affineTransform != null) {
/*  341 */           this.lp = new LayoutPathImpl.EmptyPath(affineTransform);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Rectangle getPixelBounds(FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2) {
/*  348 */     Rectangle rectangle = null;
/*      */ 
/*      */ 
/*      */     
/*  352 */     if (paramFontRenderContext != null && paramFontRenderContext.equals(this.frc)) {
/*  353 */       paramFontRenderContext = null;
/*      */     }
/*      */ 
/*      */     
/*  357 */     int i = (int)Math.floor(paramFloat1);
/*  358 */     int j = (int)Math.floor(paramFloat2);
/*  359 */     float f1 = paramFloat1 - i;
/*  360 */     float f2 = paramFloat2 - j;
/*  361 */     boolean bool = (paramFontRenderContext == null && f1 == 0.0F && f2 == 0.0F) ? true : false;
/*      */     
/*  363 */     if (bool && this.pixelBounds != null) {
/*  364 */       rectangle = new Rectangle(this.pixelBounds);
/*  365 */       rectangle.x += i;
/*  366 */       rectangle.y += j;
/*  367 */       return rectangle;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  372 */     if (this.isSimple) {
/*  373 */       for (byte b1 = 0, b2 = 0; b1 < this.fComponents.length; b1++, b2 += 2) {
/*  374 */         TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b1)];
/*  375 */         Rectangle rectangle1 = textLineComponent.getPixelBounds(paramFontRenderContext, this.locs[b2] + f1, this.locs[b2 + 1] + f2);
/*  376 */         if (!rectangle1.isEmpty()) {
/*  377 */           if (rectangle == null) {
/*  378 */             rectangle = rectangle1;
/*      */           } else {
/*  380 */             rectangle.add(rectangle1);
/*      */           } 
/*      */         }
/*      */       } 
/*  384 */       if (rectangle == null) {
/*  385 */         rectangle = new Rectangle(0, 0, 0, 0);
/*      */       }
/*      */     } else {
/*      */       
/*  389 */       Rectangle2D rectangle2D = getVisualBounds();
/*  390 */       if (this.lp != null) {
/*  391 */         rectangle2D = this.lp.mapShape(rectangle2D).getBounds();
/*      */       }
/*  393 */       Rectangle rectangle1 = rectangle2D.getBounds();
/*  394 */       BufferedImage bufferedImage = new BufferedImage(rectangle1.width + 6, rectangle1.height + 6, 2);
/*      */ 
/*      */ 
/*      */       
/*  398 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/*  399 */       graphics2D.setColor(Color.WHITE);
/*  400 */       graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
/*      */       
/*  402 */       graphics2D.setColor(Color.BLACK);
/*  403 */       draw(graphics2D, f1 + 3.0F - rectangle1.x, f2 + 3.0F - rectangle1.y);
/*      */       
/*  405 */       rectangle = computePixelBounds(bufferedImage);
/*  406 */       rectangle.x -= 3 - rectangle1.x;
/*  407 */       rectangle.y -= 3 - rectangle1.y;
/*      */     } 
/*      */     
/*  410 */     if (bool) {
/*  411 */       this.pixelBounds = new Rectangle(rectangle);
/*      */     }
/*      */     
/*  414 */     rectangle.x += i;
/*  415 */     rectangle.y += j;
/*  416 */     return rectangle;
/*      */   }
/*      */   
/*      */   static Rectangle computePixelBounds(BufferedImage paramBufferedImage) {
/*  420 */     int i = paramBufferedImage.getWidth();
/*  421 */     int j = paramBufferedImage.getHeight();
/*      */     
/*  423 */     byte b1 = -1, b2 = -1; int k = i, m = j;
/*      */ 
/*      */ 
/*      */     
/*  427 */     int[] arrayOfInt = new int[i];
/*  428 */     label47: while (++b2 < j) {
/*  429 */       paramBufferedImage.getRGB(0, b2, arrayOfInt.length, 1, arrayOfInt, 0, i);
/*  430 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/*  431 */         if (arrayOfInt[b] != -1) {
/*      */           break label47;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  440 */     arrayOfInt = new int[i];
/*  441 */     label48: while (--m > b2) {
/*  442 */       paramBufferedImage.getRGB(0, m, arrayOfInt.length, 1, arrayOfInt, 0, i);
/*  443 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/*  444 */         if (arrayOfInt[b] != -1) {
/*      */           break label48;
/*      */         }
/*      */       } 
/*      */     } 
/*  449 */     m++;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  454 */     label49: while (++b1 < k) {
/*  455 */       for (byte b = b2; b < m; b++) {
/*  456 */         int n = paramBufferedImage.getRGB(b1, b);
/*  457 */         if (n != -1) {
/*      */           break label49;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  466 */     label50: while (--k > b1) {
/*  467 */       for (byte b = b2; b < m; b++) {
/*  468 */         int n = paramBufferedImage.getRGB(k, b);
/*  469 */         if (n != -1) {
/*      */           break label50;
/*      */         }
/*      */       } 
/*      */     } 
/*  474 */     k++;
/*      */ 
/*      */     
/*  477 */     return new Rectangle(b1, b2, k - b1, m - b2);
/*      */   }
/*      */   
/*      */   private static abstract class Function
/*      */   {
/*      */     private Function() {}
/*      */     
/*      */     abstract float computeFunction(TextLine param1TextLine, int param1Int1, int param1Int2);
/*      */   }
/*      */   
/*  487 */   private static Function fgPosAdvF = new Function()
/*      */     {
/*      */       
/*      */       float computeFunction(TextLine param1TextLine, int param1Int1, int param1Int2)
/*      */       {
/*  492 */         TextLineComponent textLineComponent = param1TextLine.fComponents[param1Int1];
/*  493 */         int i = param1TextLine.getComponentVisualIndex(param1Int1);
/*  494 */         return param1TextLine.locs[i * 2] + textLineComponent.getCharX(param1Int2) + textLineComponent.getCharAdvance(param1Int2);
/*      */       }
/*      */     };
/*      */   
/*  498 */   private static Function fgAdvanceF = new Function()
/*      */     {
/*      */ 
/*      */       
/*      */       float computeFunction(TextLine param1TextLine, int param1Int1, int param1Int2)
/*      */       {
/*  504 */         TextLineComponent textLineComponent = param1TextLine.fComponents[param1Int1];
/*  505 */         return textLineComponent.getCharAdvance(param1Int2);
/*      */       }
/*      */     };
/*      */   
/*  509 */   private static Function fgXPositionF = new Function()
/*      */     {
/*      */ 
/*      */       
/*      */       float computeFunction(TextLine param1TextLine, int param1Int1, int param1Int2)
/*      */       {
/*  515 */         int i = param1TextLine.getComponentVisualIndex(param1Int1);
/*  516 */         TextLineComponent textLineComponent = param1TextLine.fComponents[param1Int1];
/*  517 */         return param1TextLine.locs[i * 2] + textLineComponent.getCharX(param1Int2);
/*      */       }
/*      */     };
/*      */   
/*  521 */   private static Function fgYPositionF = new Function()
/*      */     {
/*      */ 
/*      */       
/*      */       float computeFunction(TextLine param1TextLine, int param1Int1, int param1Int2)
/*      */       {
/*  527 */         TextLineComponent textLineComponent = param1TextLine.fComponents[param1Int1];
/*  528 */         float f = textLineComponent.getCharY(param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  533 */         return f + param1TextLine.getComponentShift(param1Int1);
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   public int characterCount() {
/*  539 */     return this.fCharsLimit - this.fCharsStart;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirectionLTR() {
/*  544 */     return this.fIsDirectionLTR;
/*      */   }
/*      */   
/*      */   public TextLineMetrics getMetrics() {
/*  548 */     return this.fMetrics;
/*      */   }
/*      */ 
/*      */   
/*      */   public int visualToLogical(int paramInt) {
/*  553 */     if (this.fCharLogicalOrder == null) {
/*  554 */       return paramInt;
/*      */     }
/*      */     
/*  557 */     if (this.fCharVisualOrder == null) {
/*  558 */       this.fCharVisualOrder = BidiUtils.createInverseMap(this.fCharLogicalOrder);
/*      */     }
/*      */     
/*  561 */     return this.fCharVisualOrder[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   public int logicalToVisual(int paramInt) {
/*  566 */     return (this.fCharLogicalOrder == null) ? paramInt : this.fCharLogicalOrder[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getCharLevel(int paramInt) {
/*  572 */     return (this.fCharLevels == null) ? 0 : this.fCharLevels[paramInt];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCharLTR(int paramInt) {
/*  577 */     return ((getCharLevel(paramInt) & 0x1) == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCharType(int paramInt) {
/*  582 */     return Character.getType(this.fChars[paramInt + this.fCharsStart]);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCharSpace(int paramInt) {
/*  587 */     return Character.isSpaceChar(this.fChars[paramInt + this.fCharsStart]);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCharWhitespace(int paramInt) {
/*  592 */     return Character.isWhitespace(this.fChars[paramInt + this.fCharsStart]);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAngle(int paramInt) {
/*  597 */     return (getCoreMetricsAt(paramInt)).italicAngle;
/*      */   }
/*      */ 
/*      */   
/*      */   public CoreMetrics getCoreMetricsAt(int paramInt) {
/*  602 */     if (paramInt < 0) {
/*  603 */       throw new IllegalArgumentException("Negative logicalIndex.");
/*      */     }
/*      */     
/*  606 */     if (paramInt > this.fCharsLimit - this.fCharsStart) {
/*  607 */       throw new IllegalArgumentException("logicalIndex too large.");
/*      */     }
/*      */     
/*  610 */     byte b = 0;
/*  611 */     int i = 0;
/*  612 */     int j = 0;
/*      */     
/*      */     do {
/*  615 */       j += this.fComponents[b].getNumCharacters();
/*  616 */       if (j > paramInt) {
/*      */         break;
/*      */       }
/*  619 */       b++;
/*  620 */       i = j;
/*  621 */     } while (b < this.fComponents.length);
/*      */     
/*  623 */     return this.fComponents[b].getCoreMetrics();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAscent(int paramInt) {
/*  628 */     return (getCoreMetricsAt(paramInt)).ascent;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharDescent(int paramInt) {
/*  633 */     return (getCoreMetricsAt(paramInt)).descent;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharShift(int paramInt) {
/*  638 */     return (getCoreMetricsAt(paramInt)).ssOffset;
/*      */   }
/*      */ 
/*      */   
/*      */   private float applyFunctionAtIndex(int paramInt, Function paramFunction) {
/*  643 */     if (paramInt < 0) {
/*  644 */       throw new IllegalArgumentException("Negative logicalIndex.");
/*      */     }
/*      */     
/*  647 */     int i = 0;
/*      */     
/*  649 */     for (byte b = 0; b < this.fComponents.length; b++) {
/*      */       
/*  651 */       int j = i + this.fComponents[b].getNumCharacters();
/*  652 */       if (j > paramInt) {
/*  653 */         return paramFunction.computeFunction(this, b, paramInt - i);
/*      */       }
/*      */       
/*  656 */       i = j;
/*      */     } 
/*      */ 
/*      */     
/*  660 */     throw new IllegalArgumentException("logicalIndex too large.");
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAdvance(int paramInt) {
/*  665 */     return applyFunctionAtIndex(paramInt, fgAdvanceF);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharXPosition(int paramInt) {
/*  670 */     return applyFunctionAtIndex(paramInt, fgXPositionF);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharYPosition(int paramInt) {
/*  675 */     return applyFunctionAtIndex(paramInt, fgYPositionF);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharLinePosition(int paramInt) {
/*  680 */     return getCharXPosition(paramInt);
/*      */   }
/*      */   
/*      */   public float getCharLinePosition(int paramInt, boolean paramBoolean) {
/*  684 */     Function function = (isCharLTR(paramInt) == paramBoolean) ? fgXPositionF : fgPosAdvF;
/*  685 */     return applyFunctionAtIndex(paramInt, function);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean caretAtOffsetIsValid(int paramInt) {
/*  690 */     if (paramInt < 0) {
/*  691 */       throw new IllegalArgumentException("Negative offset.");
/*      */     }
/*      */     
/*  694 */     int i = 0;
/*      */     
/*  696 */     for (byte b = 0; b < this.fComponents.length; b++) {
/*      */       
/*  698 */       int j = i + this.fComponents[b].getNumCharacters();
/*  699 */       if (j > paramInt) {
/*  700 */         return this.fComponents[b].caretAtOffsetIsValid(paramInt - i);
/*      */       }
/*      */       
/*  703 */       i = j;
/*      */     } 
/*      */ 
/*      */     
/*  707 */     throw new IllegalArgumentException("logicalIndex too large.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getComponentLogicalIndex(int paramInt) {
/*  714 */     if (this.fComponentVisualOrder == null) {
/*  715 */       return paramInt;
/*      */     }
/*  717 */     return this.fComponentVisualOrder[paramInt];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getComponentVisualIndex(int paramInt) {
/*  724 */     if (this.fComponentVisualOrder == null) {
/*  725 */       return paramInt;
/*      */     }
/*  727 */     for (byte b = 0; b < this.fComponentVisualOrder.length; b++) {
/*  728 */       if (this.fComponentVisualOrder[b] == paramInt) {
/*  729 */         return b;
/*      */       }
/*      */     } 
/*  732 */     throw new IndexOutOfBoundsException("bad component index: " + paramInt);
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle2D getCharBounds(int paramInt) {
/*  737 */     if (paramInt < 0) {
/*  738 */       throw new IllegalArgumentException("Negative logicalIndex.");
/*      */     }
/*      */     
/*  741 */     int i = 0;
/*      */     
/*  743 */     for (byte b = 0; b < this.fComponents.length; b++) {
/*      */       
/*  745 */       int j = i + this.fComponents[b].getNumCharacters();
/*  746 */       if (j > paramInt) {
/*      */         
/*  748 */         TextLineComponent textLineComponent = this.fComponents[b];
/*  749 */         int k = paramInt - i;
/*  750 */         Rectangle2D rectangle2D = textLineComponent.getCharVisualBounds(k);
/*      */         
/*  752 */         int m = getComponentVisualIndex(b);
/*  753 */         rectangle2D.setRect(rectangle2D.getX() + this.locs[m * 2], rectangle2D
/*  754 */             .getY() + this.locs[m * 2 + 1], rectangle2D
/*  755 */             .getWidth(), rectangle2D
/*  756 */             .getHeight());
/*  757 */         return rectangle2D;
/*      */       } 
/*      */       
/*  760 */       i = j;
/*      */     } 
/*      */ 
/*      */     
/*  764 */     throw new IllegalArgumentException("logicalIndex too large.");
/*      */   }
/*      */   
/*      */   private float getComponentShift(int paramInt) {
/*  768 */     CoreMetrics coreMetrics = this.fComponents[paramInt].getCoreMetrics();
/*  769 */     return coreMetrics.effectiveBaselineOffset(this.fBaselineOffsets);
/*      */   }
/*      */   
/*      */   public void draw(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2) {
/*  773 */     if (this.lp == null) {
/*  774 */       for (byte b1 = 0, b2 = 0; b1 < this.fComponents.length; b1++, b2 += 2) {
/*  775 */         TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b1)];
/*  776 */         textLineComponent.draw(paramGraphics2D, this.locs[b2] + paramFloat1, this.locs[b2 + 1] + paramFloat2);
/*      */       } 
/*      */     } else {
/*  779 */       AffineTransform affineTransform = paramGraphics2D.getTransform();
/*  780 */       Point2D.Float float_ = new Point2D.Float();
/*  781 */       for (byte b1 = 0, b2 = 0; b1 < this.fComponents.length; b1++, b2 += 2) {
/*  782 */         TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b1)];
/*  783 */         this.lp.pathToPoint(this.locs[b2], this.locs[b2 + 1], false, float_);
/*  784 */         float_.x += paramFloat1;
/*  785 */         float_.y += paramFloat2;
/*  786 */         AffineTransform affineTransform1 = textLineComponent.getBaselineTransform();
/*      */         
/*  788 */         if (affineTransform1 != null) {
/*  789 */           paramGraphics2D.translate(float_.x - affineTransform1.getTranslateX(), float_.y - affineTransform1.getTranslateY());
/*  790 */           paramGraphics2D.transform(affineTransform1);
/*  791 */           textLineComponent.draw(paramGraphics2D, 0.0F, 0.0F);
/*  792 */           paramGraphics2D.setTransform(affineTransform);
/*      */         } else {
/*  794 */           textLineComponent.draw(paramGraphics2D, float_.x, float_.y);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getVisualBounds() {
/*  806 */     Rectangle2D rectangle2D = null;
/*      */     
/*  808 */     for (byte b1 = 0, b2 = 0; b1 < this.fComponents.length; b1++, b2 += 2) {
/*  809 */       TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b1)];
/*  810 */       Rectangle2D rectangle2D1 = textLineComponent.getVisualBounds();
/*      */       
/*  812 */       Point2D.Float float_ = new Point2D.Float(this.locs[b2], this.locs[b2 + 1]);
/*  813 */       if (this.lp == null) {
/*  814 */         rectangle2D1.setRect(rectangle2D1.getMinX() + float_.x, rectangle2D1.getMinY() + float_.y, rectangle2D1
/*  815 */             .getWidth(), rectangle2D1.getHeight());
/*      */       } else {
/*  817 */         this.lp.pathToPoint(float_, false, float_);
/*      */         
/*  819 */         AffineTransform affineTransform = textLineComponent.getBaselineTransform();
/*  820 */         if (affineTransform != null) {
/*      */           
/*  822 */           AffineTransform affineTransform1 = AffineTransform.getTranslateInstance(float_.x - affineTransform.getTranslateX(), float_.y - affineTransform.getTranslateY());
/*  823 */           affineTransform1.concatenate(affineTransform);
/*  824 */           rectangle2D1 = affineTransform1.createTransformedShape(rectangle2D1).getBounds2D();
/*      */         } else {
/*  826 */           rectangle2D1.setRect(rectangle2D1.getMinX() + float_.x, rectangle2D1.getMinY() + float_.y, rectangle2D1
/*  827 */               .getWidth(), rectangle2D1.getHeight());
/*      */         } 
/*      */       } 
/*      */       
/*  831 */       if (rectangle2D == null) {
/*  832 */         rectangle2D = rectangle2D1;
/*      */       } else {
/*  834 */         rectangle2D.add(rectangle2D1);
/*      */       } 
/*      */     } 
/*      */     
/*  838 */     if (rectangle2D == null) {
/*  839 */       rectangle2D = new Rectangle2D.Float(Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
/*      */     }
/*      */     
/*  842 */     return rectangle2D;
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle2D getItalicBounds() {
/*  847 */     float f1 = Float.MAX_VALUE, f2 = -3.4028235E38F;
/*  848 */     float f3 = Float.MAX_VALUE, f4 = -3.4028235E38F;
/*      */     
/*  850 */     for (byte b1 = 0, b2 = 0; b1 < this.fComponents.length; b1++, b2 += 2) {
/*  851 */       TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b1)];
/*      */       
/*  853 */       Rectangle2D rectangle2D = textLineComponent.getItalicBounds();
/*  854 */       float f5 = this.locs[b2];
/*  855 */       float f6 = this.locs[b2 + 1];
/*      */       
/*  857 */       f1 = Math.min(f1, f5 + (float)rectangle2D.getX());
/*  858 */       f2 = Math.max(f2, f5 + (float)rectangle2D.getMaxX());
/*      */       
/*  860 */       f3 = Math.min(f3, f6 + (float)rectangle2D.getY());
/*  861 */       f4 = Math.max(f4, f6 + (float)rectangle2D.getMaxY());
/*      */     } 
/*      */     
/*  864 */     return new Rectangle2D.Float(f1, f3, f2 - f1, f4 - f3);
/*      */   }
/*      */ 
/*      */   
/*      */   public Shape getOutline(AffineTransform paramAffineTransform) {
/*  869 */     GeneralPath generalPath = new GeneralPath(1);
/*      */     
/*  871 */     for (byte b1 = 0, b2 = 0; b1 < this.fComponents.length; b1++, b2 += 2) {
/*  872 */       TextLineComponent textLineComponent = this.fComponents[getComponentLogicalIndex(b1)];
/*      */       
/*  874 */       generalPath.append(textLineComponent.getOutline(this.locs[b2], this.locs[b2 + 1]), false);
/*      */     } 
/*      */     
/*  877 */     if (paramAffineTransform != null) {
/*  878 */       generalPath.transform(paramAffineTransform);
/*      */     }
/*  880 */     return generalPath;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  884 */     return this.fComponents.length << 16 ^ this.fComponents[0]
/*  885 */       .hashCode() << 3 ^ this.fCharsLimit - this.fCharsStart;
/*      */   }
/*      */   
/*      */   public String toString() {
/*  889 */     StringBuilder stringBuilder = new StringBuilder();
/*      */     
/*  891 */     for (byte b = 0; b < this.fComponents.length; b++) {
/*  892 */       stringBuilder.append(this.fComponents[b]);
/*      */     }
/*      */     
/*  895 */     return stringBuilder.toString();
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
/*      */   public static TextLine fastCreateTextLine(FontRenderContext paramFontRenderContext, char[] paramArrayOfchar, Font paramFont, CoreMetrics paramCoreMetrics, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/*  910 */     boolean bool1 = true;
/*  911 */     byte[] arrayOfByte1 = null;
/*  912 */     int[] arrayOfInt = null;
/*  913 */     Bidi bidi = null;
/*  914 */     int i = paramArrayOfchar.length;
/*      */     
/*  916 */     boolean bool2 = false;
/*  917 */     byte[] arrayOfByte2 = null;
/*      */     
/*  919 */     AttributeValues attributeValues = null;
/*  920 */     if (paramMap != null) {
/*  921 */       attributeValues = AttributeValues.fromMap(paramMap);
/*  922 */       if (attributeValues.getRunDirection() >= 0) {
/*  923 */         bool1 = (attributeValues.getRunDirection() == 0);
/*  924 */         bool2 = !bool1;
/*      */       } 
/*  926 */       if (attributeValues.getBidiEmbedding() != 0) {
/*  927 */         bool2 = true;
/*  928 */         byte b = (byte)attributeValues.getBidiEmbedding();
/*  929 */         arrayOfByte2 = new byte[i];
/*  930 */         for (byte b1 = 0; b1 < arrayOfByte2.length; b1++) {
/*  931 */           arrayOfByte2[b1] = b;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  938 */     if (!bool2) {
/*  939 */       bool2 = Bidi.requiresBidi(paramArrayOfchar, 0, paramArrayOfchar.length);
/*      */     }
/*      */     
/*  942 */     if (bool2) {
/*      */ 
/*      */       
/*  945 */       boolean bool3 = (attributeValues == null) ? true : attributeValues.getRunDirection();
/*      */       
/*  947 */       bidi = new Bidi(paramArrayOfchar, 0, arrayOfByte2, 0, paramArrayOfchar.length, bool3);
/*  948 */       if (!bidi.isLeftToRight()) {
/*  949 */         arrayOfByte1 = BidiUtils.getLevels(bidi);
/*  950 */         int[] arrayOfInt1 = BidiUtils.createVisualToLogicalMap(arrayOfByte1);
/*  951 */         arrayOfInt = BidiUtils.createInverseMap(arrayOfInt1);
/*  952 */         bool1 = bidi.baseIsLeftToRight();
/*      */       } 
/*      */     } 
/*      */     
/*  956 */     Decoration decoration = Decoration.getDecoration(attributeValues);
/*      */     
/*  958 */     boolean bool = false;
/*  959 */     TextLabelFactory textLabelFactory = new TextLabelFactory(paramFontRenderContext, paramArrayOfchar, bidi, bool);
/*      */     
/*  961 */     TextLineComponent[] arrayOfTextLineComponent = new TextLineComponent[1];
/*      */     
/*  963 */     arrayOfTextLineComponent = createComponentsOnRun(0, paramArrayOfchar.length, paramArrayOfchar, arrayOfInt, arrayOfByte1, textLabelFactory, paramFont, paramCoreMetrics, paramFontRenderContext, decoration, arrayOfTextLineComponent, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  972 */     int j = arrayOfTextLineComponent.length;
/*  973 */     while (arrayOfTextLineComponent[j - 1] == null) {
/*  974 */       j--;
/*      */     }
/*      */     
/*  977 */     if (j != arrayOfTextLineComponent.length) {
/*  978 */       TextLineComponent[] arrayOfTextLineComponent1 = new TextLineComponent[j];
/*  979 */       System.arraycopy(arrayOfTextLineComponent, 0, arrayOfTextLineComponent1, 0, j);
/*  980 */       arrayOfTextLineComponent = arrayOfTextLineComponent1;
/*      */     } 
/*      */     
/*  983 */     return new TextLine(paramFontRenderContext, arrayOfTextLineComponent, paramCoreMetrics.baselineOffsets, paramArrayOfchar, 0, paramArrayOfchar.length, arrayOfInt, arrayOfByte1, bool1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextLineComponent[] expandArray(TextLineComponent[] paramArrayOfTextLineComponent) {
/*  989 */     TextLineComponent[] arrayOfTextLineComponent = new TextLineComponent[paramArrayOfTextLineComponent.length + 8];
/*  990 */     System.arraycopy(paramArrayOfTextLineComponent, 0, arrayOfTextLineComponent, 0, paramArrayOfTextLineComponent.length);
/*      */     
/*  992 */     return arrayOfTextLineComponent;
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
/*      */   public static TextLineComponent[] createComponentsOnRun(int paramInt1, int paramInt2, char[] paramArrayOfchar, int[] paramArrayOfint, byte[] paramArrayOfbyte, TextLabelFactory paramTextLabelFactory, Font paramFont, CoreMetrics paramCoreMetrics, FontRenderContext paramFontRenderContext, Decoration paramDecoration, TextLineComponent[] paramArrayOfTextLineComponent, int paramInt3) {
/* 1012 */     int i = paramInt1;
/*      */     do {
/* 1014 */       int j = firstVisualChunk(paramArrayOfint, paramArrayOfbyte, i, paramInt2);
/*      */       
/*      */       do {
/* 1017 */         int m, k = i;
/*      */ 
/*      */         
/* 1020 */         if (paramCoreMetrics == null) {
/* 1021 */           LineMetrics lineMetrics = paramFont.getLineMetrics(paramArrayOfchar, k, j, paramFontRenderContext);
/* 1022 */           paramCoreMetrics = CoreMetrics.get(lineMetrics);
/* 1023 */           m = lineMetrics.getNumChars();
/*      */         } else {
/*      */           
/* 1026 */           m = j - k;
/*      */         } 
/*      */ 
/*      */         
/* 1030 */         ExtendedTextLabel extendedTextLabel = paramTextLabelFactory.createExtended(paramFont, paramCoreMetrics, paramDecoration, k, k + m);
/*      */         
/* 1032 */         paramInt3++;
/* 1033 */         if (paramInt3 >= paramArrayOfTextLineComponent.length) {
/* 1034 */           paramArrayOfTextLineComponent = expandArray(paramArrayOfTextLineComponent);
/*      */         }
/*      */         
/* 1037 */         paramArrayOfTextLineComponent[paramInt3 - 1] = extendedTextLabel;
/*      */         
/* 1039 */         i += m;
/* 1040 */       } while (i < j);
/*      */     }
/* 1042 */     while (i < paramInt2);
/*      */     
/* 1044 */     return paramArrayOfTextLineComponent;
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
/*      */   public static TextLineComponent[] getComponents(StyledParagraph paramStyledParagraph, char[] paramArrayOfchar, int paramInt1, int paramInt2, int[] paramArrayOfint, byte[] paramArrayOfbyte, TextLabelFactory paramTextLabelFactory) {
/*      */     TextLineComponent[] arrayOfTextLineComponent2;
/* 1059 */     FontRenderContext fontRenderContext = paramTextLabelFactory.getFontRenderContext();
/*      */     
/* 1061 */     int i = 0;
/* 1062 */     TextLineComponent[] arrayOfTextLineComponent1 = new TextLineComponent[1];
/*      */     
/* 1064 */     int j = paramInt1;
/*      */     do {
/* 1066 */       int k = Math.min(paramStyledParagraph.getRunLimit(j), paramInt2);
/*      */       
/* 1068 */       Decoration decoration = paramStyledParagraph.getDecorationAt(j);
/*      */       
/* 1070 */       Object object = paramStyledParagraph.getFontOrGraphicAt(j);
/*      */       
/* 1072 */       if (object instanceof GraphicAttribute) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1077 */         AffineTransform affineTransform = null;
/* 1078 */         GraphicAttribute graphicAttribute = (GraphicAttribute)object;
/*      */         do {
/* 1080 */           int m = firstVisualChunk(paramArrayOfint, paramArrayOfbyte, j, k);
/*      */ 
/*      */           
/* 1083 */           GraphicComponent graphicComponent = new GraphicComponent(graphicAttribute, decoration, paramArrayOfint, paramArrayOfbyte, j, m, affineTransform);
/*      */           
/* 1085 */           j = m;
/*      */           
/* 1087 */           i++;
/* 1088 */           if (i >= arrayOfTextLineComponent1.length) {
/* 1089 */             arrayOfTextLineComponent1 = expandArray(arrayOfTextLineComponent1);
/*      */           }
/*      */           
/* 1092 */           arrayOfTextLineComponent1[i - 1] = graphicComponent;
/*      */         }
/* 1094 */         while (j < k);
/*      */       } else {
/*      */         
/* 1097 */         Font font = (Font)object;
/*      */         
/* 1099 */         arrayOfTextLineComponent1 = createComponentsOnRun(j, k, paramArrayOfchar, paramArrayOfint, paramArrayOfbyte, paramTextLabelFactory, font, null, fontRenderContext, decoration, arrayOfTextLineComponent1, i);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1107 */         j = k;
/* 1108 */         i = arrayOfTextLineComponent1.length;
/* 1109 */         while (arrayOfTextLineComponent1[i - 1] == null) {
/* 1110 */           i--;
/*      */         }
/*      */       }
/*      */     
/* 1114 */     } while (j < paramInt2);
/*      */ 
/*      */     
/* 1117 */     if (arrayOfTextLineComponent1.length == i) {
/* 1118 */       arrayOfTextLineComponent2 = arrayOfTextLineComponent1;
/*      */     } else {
/*      */       
/* 1121 */       arrayOfTextLineComponent2 = new TextLineComponent[i];
/* 1122 */       System.arraycopy(arrayOfTextLineComponent1, 0, arrayOfTextLineComponent2, 0, i);
/*      */     } 
/*      */     
/* 1125 */     return arrayOfTextLineComponent2;
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
/*      */   public static TextLine createLineFromText(char[] paramArrayOfchar, StyledParagraph paramStyledParagraph, TextLabelFactory paramTextLabelFactory, boolean paramBoolean, float[] paramArrayOffloat) {
/* 1139 */     paramTextLabelFactory.setLineContext(0, paramArrayOfchar.length);
/*      */     
/* 1141 */     Bidi bidi = paramTextLabelFactory.getLineBidi();
/* 1142 */     int[] arrayOfInt = null;
/* 1143 */     byte[] arrayOfByte = null;
/*      */     
/* 1145 */     if (bidi != null) {
/* 1146 */       arrayOfByte = BidiUtils.getLevels(bidi);
/* 1147 */       int[] arrayOfInt1 = BidiUtils.createVisualToLogicalMap(arrayOfByte);
/* 1148 */       arrayOfInt = BidiUtils.createInverseMap(arrayOfInt1);
/*      */     } 
/*      */ 
/*      */     
/* 1152 */     TextLineComponent[] arrayOfTextLineComponent = getComponents(paramStyledParagraph, paramArrayOfchar, 0, paramArrayOfchar.length, arrayOfInt, arrayOfByte, paramTextLabelFactory);
/*      */     
/* 1154 */     return new TextLine(paramTextLabelFactory.getFontRenderContext(), arrayOfTextLineComponent, paramArrayOffloat, paramArrayOfchar, 0, paramArrayOfchar.length, arrayOfInt, arrayOfByte, paramBoolean);
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
/*      */   private static int[] computeComponentOrder(TextLineComponent[] paramArrayOfTextLineComponent, int[] paramArrayOfint) {
/* 1174 */     int[] arrayOfInt = null;
/* 1175 */     if (paramArrayOfint != null && paramArrayOfTextLineComponent.length > 1) {
/* 1176 */       arrayOfInt = new int[paramArrayOfTextLineComponent.length];
/* 1177 */       int i = 0;
/* 1178 */       for (byte b = 0; b < paramArrayOfTextLineComponent.length; b++) {
/* 1179 */         arrayOfInt[b] = paramArrayOfint[i];
/* 1180 */         i += paramArrayOfTextLineComponent[b].getNumCharacters();
/*      */       } 
/*      */       
/* 1183 */       arrayOfInt = BidiUtils.createContiguousOrder(arrayOfInt);
/* 1184 */       arrayOfInt = BidiUtils.createInverseMap(arrayOfInt);
/*      */     } 
/* 1186 */     return arrayOfInt;
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
/*      */   public static TextLine standardCreateTextLine(FontRenderContext paramFontRenderContext, AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfchar, float[] paramArrayOffloat) {
/* 1198 */     StyledParagraph styledParagraph = new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfchar);
/* 1199 */     Bidi bidi = new Bidi(paramAttributedCharacterIterator);
/* 1200 */     if (bidi.isLeftToRight()) {
/* 1201 */       bidi = null;
/*      */     }
/* 1203 */     boolean bool = false;
/* 1204 */     TextLabelFactory textLabelFactory = new TextLabelFactory(paramFontRenderContext, paramArrayOfchar, bidi, bool);
/*      */     
/* 1206 */     boolean bool1 = true;
/* 1207 */     if (bidi != null) {
/* 1208 */       bool1 = bidi.baseIsLeftToRight();
/*      */     }
/* 1210 */     return createLineFromText(paramArrayOfchar, styledParagraph, textLabelFactory, bool1, paramArrayOffloat);
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
/*      */   static boolean advanceToFirstFont(AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 1255 */     char c = paramAttributedCharacterIterator.first();
/* 1256 */     for (; c != Character.MAX_VALUE; 
/* 1257 */       c = paramAttributedCharacterIterator.setIndex(paramAttributedCharacterIterator.getRunLimit())) {
/*      */ 
/*      */       
/* 1260 */       if (paramAttributedCharacterIterator.getAttribute(TextAttribute.CHAR_REPLACEMENT) == null) {
/* 1261 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1265 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static float[] getNormalizedOffsets(float[] paramArrayOffloat, byte paramByte) {
/* 1270 */     if (paramArrayOffloat[paramByte] != 0.0F) {
/* 1271 */       float f = paramArrayOffloat[paramByte];
/* 1272 */       float[] arrayOfFloat = new float[paramArrayOffloat.length];
/* 1273 */       for (byte b = 0; b < arrayOfFloat.length; b++)
/* 1274 */         arrayOfFloat[b] = paramArrayOffloat[b] - f; 
/* 1275 */       paramArrayOffloat = arrayOfFloat;
/*      */     } 
/* 1277 */     return paramArrayOffloat;
/*      */   }
/*      */ 
/*      */   
/*      */   static Font getFontAtCurrentPos(AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 1282 */     Object object = paramAttributedCharacterIterator.getAttribute(TextAttribute.FONT);
/* 1283 */     if (object != null) {
/* 1284 */       return (Font)object;
/*      */     }
/* 1286 */     if (paramAttributedCharacterIterator.getAttribute(TextAttribute.FAMILY) != null) {
/* 1287 */       return Font.getFont(paramAttributedCharacterIterator.getAttributes());
/*      */     }
/*      */     
/* 1290 */     int i = CodePointIterator.create(paramAttributedCharacterIterator).next();
/* 1291 */     if (i != -1) {
/* 1292 */       FontResolver fontResolver = FontResolver.getInstance();
/* 1293 */       return fontResolver.getFont(fontResolver.getFontIndex(i), paramAttributedCharacterIterator.getAttributes());
/*      */     } 
/* 1295 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int firstVisualChunk(int[] paramArrayOfint, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 1304 */     if (paramArrayOfint != null && paramArrayOfbyte != null) {
/* 1305 */       byte b = paramArrayOfbyte[paramInt1];
/* 1306 */       while (++paramInt1 < paramInt2 && paramArrayOfbyte[paramInt1] == b);
/* 1307 */       return paramInt1;
/*      */     } 
/* 1309 */     return paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextLine getJustifiedLine(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
/* 1318 */     TextLineComponent[] arrayOfTextLineComponent = new TextLineComponent[this.fComponents.length];
/* 1319 */     System.arraycopy(this.fComponents, 0, arrayOfTextLineComponent, 0, this.fComponents.length);
/*      */     
/* 1321 */     float f1 = 0.0F;
/* 1322 */     float f2 = 0.0F;
/* 1323 */     float f3 = 0.0F;
/* 1324 */     boolean bool = false;
/*      */     do {
/* 1326 */       f2 = getAdvanceBetween(arrayOfTextLineComponent, 0, characterCount());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1331 */       float f = getAdvanceBetween(arrayOfTextLineComponent, paramInt1, paramInt2);
/*      */ 
/*      */       
/* 1334 */       f3 = (paramFloat1 - f) * paramFloat2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       int[] arrayOfInt = new int[arrayOfTextLineComponent.length];
/* 1341 */       int i = 0;
/* 1342 */       for (byte b1 = 0; b1 < arrayOfTextLineComponent.length; b1++) {
/* 1343 */         int k = getComponentLogicalIndex(b1);
/* 1344 */         arrayOfInt[k] = i;
/* 1345 */         i += arrayOfTextLineComponent[k].getNumJustificationInfos();
/*      */       } 
/* 1347 */       GlyphJustificationInfo[] arrayOfGlyphJustificationInfo = new GlyphJustificationInfo[i];
/*      */ 
/*      */       
/* 1350 */       byte b2 = 0; byte b3;
/* 1351 */       for (b3 = 0; b3 < arrayOfTextLineComponent.length; b3++) {
/* 1352 */         TextLineComponent textLineComponent = arrayOfTextLineComponent[b3];
/* 1353 */         int k = textLineComponent.getNumCharacters();
/* 1354 */         int m = b2 + k;
/* 1355 */         if (m > paramInt1) {
/* 1356 */           int n = Math.max(0, paramInt1 - b2);
/* 1357 */           int i1 = Math.min(k, paramInt2 - b2);
/* 1358 */           textLineComponent.getJustificationInfos(arrayOfGlyphJustificationInfo, arrayOfInt[b3], n, i1);
/*      */           
/* 1360 */           if (m >= paramInt2) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1368 */       b3 = 0;
/* 1369 */       int j = i;
/* 1370 */       while (b3 < j && arrayOfGlyphJustificationInfo[b3] == null) {
/* 1371 */         b3++;
/*      */       }
/*      */       
/* 1374 */       while (j > b3 && arrayOfGlyphJustificationInfo[j - 1] == null) {
/* 1375 */         j--;
/*      */       }
/*      */ 
/*      */       
/* 1379 */       TextJustifier textJustifier = new TextJustifier(arrayOfGlyphJustificationInfo, b3, j);
/*      */       
/* 1381 */       float[] arrayOfFloat = textJustifier.justify(f3);
/*      */       
/* 1383 */       boolean bool1 = !bool ? true : false;
/* 1384 */       boolean bool2 = false;
/* 1385 */       boolean[] arrayOfBoolean = new boolean[1];
/*      */ 
/*      */       
/* 1388 */       b2 = 0;
/* 1389 */       for (byte b4 = 0; b4 < arrayOfTextLineComponent.length; b4++) {
/* 1390 */         TextLineComponent textLineComponent = arrayOfTextLineComponent[b4];
/* 1391 */         int k = textLineComponent.getNumCharacters();
/* 1392 */         int m = b2 + k;
/* 1393 */         if (m > paramInt1) {
/* 1394 */           int n = Math.max(0, paramInt1 - b2);
/* 1395 */           int i1 = Math.min(k, paramInt2 - b2);
/* 1396 */           arrayOfTextLineComponent[b4] = textLineComponent.applyJustificationDeltas(arrayOfFloat, arrayOfInt[b4] * 2, arrayOfBoolean);
/*      */           
/* 1398 */           bool2 |= arrayOfBoolean[0];
/*      */           
/* 1400 */           if (m >= paramInt2) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1406 */       bool = (bool2 && !bool) ? true : false;
/* 1407 */     } while (bool);
/*      */     
/* 1409 */     return new TextLine(this.frc, arrayOfTextLineComponent, this.fBaselineOffsets, this.fChars, this.fCharsStart, this.fCharsLimit, this.fCharLogicalOrder, this.fCharLevels, this.fIsDirectionLTR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getAdvanceBetween(TextLineComponent[] paramArrayOfTextLineComponent, int paramInt1, int paramInt2) {
/* 1416 */     float f = 0.0F;
/*      */     
/* 1418 */     int i = 0;
/* 1419 */     for (byte b = 0; b < paramArrayOfTextLineComponent.length; b++) {
/* 1420 */       TextLineComponent textLineComponent = paramArrayOfTextLineComponent[b];
/*      */       
/* 1422 */       int j = textLineComponent.getNumCharacters();
/* 1423 */       int k = i + j;
/* 1424 */       if (k > paramInt1) {
/* 1425 */         int m = Math.max(0, paramInt1 - i);
/* 1426 */         int n = Math.min(j, paramInt2 - i);
/* 1427 */         f += textLineComponent.getAdvanceBetween(m, n);
/* 1428 */         if (k >= paramInt2) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/* 1433 */       i = k;
/*      */     } 
/*      */     
/* 1436 */     return f;
/*      */   }
/*      */   
/*      */   LayoutPathImpl getLayoutPath() {
/* 1440 */     return this.lp;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/TextLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */