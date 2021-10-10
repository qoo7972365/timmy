/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Area;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Decoration
/*     */ {
/*     */   private Decoration() {}
/*     */   
/*     */   public static Decoration getPlainDecoration() {
/*  83 */     return PLAIN;
/*     */   }
/*     */ 
/*     */   
/*  87 */   private static final int VALUES_MASK = AttributeValues.getMask(new EAttribute[] { EAttribute.EFOREGROUND, EAttribute.EBACKGROUND, EAttribute.ESWAP_COLORS, EAttribute.ESTRIKETHROUGH, EAttribute.EUNDERLINE, EAttribute.EINPUT_METHOD_HIGHLIGHT, EAttribute.EINPUT_METHOD_UNDERLINE });
/*     */ 
/*     */ 
/*     */   
/*     */   public static Decoration getDecoration(AttributeValues paramAttributeValues) {
/*  92 */     if (paramAttributeValues == null || !paramAttributeValues.anyDefined(VALUES_MASK)) {
/*  93 */       return PLAIN;
/*     */     }
/*     */     
/*  96 */     paramAttributeValues = paramAttributeValues.applyIMHighlight();
/*     */     
/*  98 */     return new DecorationImpl(paramAttributeValues.getForeground(), paramAttributeValues
/*  99 */         .getBackground(), paramAttributeValues
/* 100 */         .getSwapColors(), paramAttributeValues
/* 101 */         .getStrikethrough(), 
/* 102 */         Underline.getUnderline(paramAttributeValues.getUnderline()), 
/* 103 */         Underline.getUnderline(paramAttributeValues.getInputMethodUnderline()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Decoration getDecoration(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 111 */     if (paramMap == null) {
/* 112 */       return PLAIN;
/*     */     }
/* 114 */     return getDecoration(AttributeValues.fromMap(paramMap));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextAndDecorations(Label paramLabel, Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2) {
/* 122 */     paramLabel.handleDraw(paramGraphics2D, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle2D getVisualBounds(Label paramLabel) {
/* 127 */     return paramLabel.handleGetVisualBounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle2D getCharVisualBounds(Label paramLabel, int paramInt) {
/* 132 */     return paramLabel.handleGetCharVisualBounds(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Shape getOutline(Label paramLabel, float paramFloat1, float paramFloat2) {
/* 139 */     return paramLabel.handleGetOutline(paramFloat1, paramFloat2);
/*     */   }
/*     */   
/* 142 */   private static final Decoration PLAIN = new Decoration();
/*     */   
/*     */   private static final class DecorationImpl
/*     */     extends Decoration {
/* 146 */     private Paint fgPaint = null;
/* 147 */     private Paint bgPaint = null;
/*     */     private boolean swapColors = false;
/*     */     private boolean strikethrough = false;
/* 150 */     private Underline stdUnderline = null;
/* 151 */     private Underline imUnderline = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DecorationImpl(Paint param1Paint1, Paint param1Paint2, boolean param1Boolean1, boolean param1Boolean2, Underline param1Underline1, Underline param1Underline2) {
/* 160 */       this.fgPaint = param1Paint1;
/* 161 */       this.bgPaint = param1Paint2;
/*     */       
/* 163 */       this.swapColors = param1Boolean1;
/* 164 */       this.strikethrough = param1Boolean2;
/*     */       
/* 166 */       this.stdUnderline = param1Underline1;
/* 167 */       this.imUnderline = param1Underline2;
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean areEqual(Object param1Object1, Object param1Object2) {
/* 172 */       if (param1Object1 == null) {
/* 173 */         return (param1Object2 == null);
/*     */       }
/*     */       
/* 176 */       return param1Object1.equals(param1Object2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 182 */       if (param1Object == this) {
/* 183 */         return true;
/*     */       }
/* 185 */       if (param1Object == null) {
/* 186 */         return false;
/*     */       }
/*     */       
/* 189 */       DecorationImpl decorationImpl = null;
/*     */       try {
/* 191 */         decorationImpl = (DecorationImpl)param1Object;
/*     */       }
/* 193 */       catch (ClassCastException classCastException) {
/* 194 */         return false;
/*     */       } 
/*     */       
/* 197 */       if (this.swapColors != decorationImpl.swapColors || this.strikethrough != decorationImpl.strikethrough)
/*     */       {
/* 199 */         return false;
/*     */       }
/*     */       
/* 202 */       if (!areEqual(this.stdUnderline, decorationImpl.stdUnderline)) {
/* 203 */         return false;
/*     */       }
/* 205 */       if (!areEqual(this.fgPaint, decorationImpl.fgPaint)) {
/* 206 */         return false;
/*     */       }
/* 208 */       if (!areEqual(this.bgPaint, decorationImpl.bgPaint)) {
/* 209 */         return false;
/*     */       }
/* 211 */       return areEqual(this.imUnderline, decorationImpl.imUnderline);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 216 */       int i = 1;
/* 217 */       if (this.strikethrough) {
/* 218 */         i |= 0x2;
/*     */       }
/* 220 */       if (this.swapColors) {
/* 221 */         i |= 0x4;
/*     */       }
/* 223 */       if (this.stdUnderline != null) {
/* 224 */         i += this.stdUnderline.hashCode();
/*     */       }
/* 226 */       return i;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float getUnderlineMaxY(CoreMetrics param1CoreMetrics) {
/* 235 */       float f = 0.0F;
/* 236 */       if (this.stdUnderline != null) {
/*     */         
/* 238 */         float f1 = param1CoreMetrics.underlineOffset;
/* 239 */         f1 += this.stdUnderline.getLowerDrawLimit(param1CoreMetrics.underlineThickness);
/* 240 */         f = Math.max(f, f1);
/*     */       } 
/*     */       
/* 243 */       if (this.imUnderline != null) {
/*     */         
/* 245 */         float f1 = param1CoreMetrics.underlineOffset;
/* 246 */         f1 += this.imUnderline.getLowerDrawLimit(param1CoreMetrics.underlineThickness);
/* 247 */         f = Math.max(f, f1);
/*     */       } 
/*     */       
/* 250 */       return f;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void drawTextAndEmbellishments(Decoration.Label param1Label, Graphics2D param1Graphics2D, float param1Float1, float param1Float2) {
/* 258 */       param1Label.handleDraw(param1Graphics2D, param1Float1, param1Float2);
/*     */       
/* 260 */       if (!this.strikethrough && this.stdUnderline == null && this.imUnderline == null) {
/*     */         return;
/*     */       }
/*     */       
/* 264 */       float f1 = param1Float1;
/* 265 */       float f2 = f1 + (float)param1Label.getLogicalBounds().getWidth();
/*     */       
/* 267 */       CoreMetrics coreMetrics = param1Label.getCoreMetrics();
/* 268 */       if (this.strikethrough) {
/* 269 */         Stroke stroke = param1Graphics2D.getStroke();
/* 270 */         param1Graphics2D.setStroke(new BasicStroke(coreMetrics.strikethroughThickness, 0, 0));
/*     */ 
/*     */         
/* 273 */         float f = param1Float2 + coreMetrics.strikethroughOffset;
/* 274 */         param1Graphics2D.draw(new Line2D.Float(f1, f, f2, f));
/* 275 */         param1Graphics2D.setStroke(stroke);
/*     */       } 
/*     */       
/* 278 */       float f3 = coreMetrics.underlineOffset;
/* 279 */       float f4 = coreMetrics.underlineThickness;
/*     */       
/* 281 */       if (this.stdUnderline != null) {
/* 282 */         this.stdUnderline.drawUnderline(param1Graphics2D, f4, f1, f2, param1Float2 + f3);
/*     */       }
/*     */       
/* 285 */       if (this.imUnderline != null) {
/* 286 */         this.imUnderline.drawUnderline(param1Graphics2D, f4, f1, f2, param1Float2 + f3);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawTextAndDecorations(Decoration.Label param1Label, Graphics2D param1Graphics2D, float param1Float1, float param1Float2) {
/* 295 */       if (this.fgPaint == null && this.bgPaint == null && !this.swapColors) {
/* 296 */         drawTextAndEmbellishments(param1Label, param1Graphics2D, param1Float1, param1Float2);
/*     */       } else {
/*     */         
/* 299 */         Paint paint2, paint3, paint1 = param1Graphics2D.getPaint();
/*     */ 
/*     */         
/* 302 */         if (this.swapColors) {
/* 303 */           paint3 = (this.fgPaint == null) ? paint1 : this.fgPaint;
/* 304 */           if (this.bgPaint == null) {
/* 305 */             if (paint3 instanceof Color) {
/* 306 */               Color color = (Color)paint3;
/*     */ 
/*     */ 
/*     */               
/* 310 */               int i = 33 * color.getRed() + 53 * color.getGreen() + 14 * color.getBlue();
/* 311 */               paint2 = (i > 18500) ? Color.BLACK : Color.WHITE;
/*     */             } else {
/* 313 */               paint2 = Color.WHITE;
/*     */             } 
/*     */           } else {
/* 316 */             paint2 = this.bgPaint;
/*     */           } 
/*     */         } else {
/*     */           
/* 320 */           paint2 = (this.fgPaint == null) ? paint1 : this.fgPaint;
/* 321 */           paint3 = this.bgPaint;
/*     */         } 
/*     */         
/* 324 */         if (paint3 != null) {
/*     */           
/* 326 */           Rectangle2D rectangle2D = param1Label.getLogicalBounds();
/*     */ 
/*     */ 
/*     */           
/* 330 */           rectangle2D = new Rectangle2D.Float(param1Float1 + (float)rectangle2D.getX(), param1Float2 + (float)rectangle2D.getY(), (float)rectangle2D.getWidth(), (float)rectangle2D.getHeight());
/*     */           
/* 332 */           param1Graphics2D.setPaint(paint3);
/* 333 */           param1Graphics2D.fill(rectangle2D);
/*     */         } 
/*     */         
/* 336 */         param1Graphics2D.setPaint(paint2);
/* 337 */         drawTextAndEmbellishments(param1Label, param1Graphics2D, param1Float1, param1Float2);
/* 338 */         param1Graphics2D.setPaint(paint1);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Rectangle2D getVisualBounds(Decoration.Label param1Label) {
/* 344 */       Rectangle2D rectangle2D = param1Label.handleGetVisualBounds();
/*     */       
/* 346 */       if (this.swapColors || this.bgPaint != null || this.strikethrough || this.stdUnderline != null || this.imUnderline != null) {
/*     */ 
/*     */         
/* 349 */         float f1 = 0.0F;
/* 350 */         Rectangle2D rectangle2D1 = param1Label.getLogicalBounds();
/*     */         
/* 352 */         float f2 = 0.0F, f3 = 0.0F;
/*     */         
/* 354 */         if (this.swapColors || this.bgPaint != null) {
/*     */           
/* 356 */           f2 = (float)rectangle2D1.getY();
/* 357 */           f3 = f2 + (float)rectangle2D1.getHeight();
/*     */         } 
/*     */         
/* 360 */         f3 = Math.max(f3, getUnderlineMaxY(param1Label.getCoreMetrics()));
/*     */         
/* 362 */         Rectangle2D.Float float_ = new Rectangle2D.Float(f1, f2, (float)rectangle2D1.getWidth(), f3 - f2);
/* 363 */         rectangle2D.add(float_);
/*     */       } 
/*     */       
/* 366 */       return rectangle2D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Shape getOutline(Decoration.Label param1Label, float param1Float1, float param1Float2) {
/* 373 */       if (!this.strikethrough && this.stdUnderline == null && this.imUnderline == null) {
/* 374 */         return param1Label.handleGetOutline(param1Float1, param1Float2);
/*     */       }
/*     */       
/* 377 */       CoreMetrics coreMetrics = param1Label.getCoreMetrics();
/*     */ 
/*     */ 
/*     */       
/* 381 */       float f1 = coreMetrics.underlineThickness;
/* 382 */       float f2 = coreMetrics.underlineOffset;
/*     */       
/* 384 */       Rectangle2D rectangle2D = param1Label.getLogicalBounds();
/* 385 */       float f3 = param1Float1;
/* 386 */       float f4 = f3 + (float)rectangle2D.getWidth();
/*     */       
/* 388 */       Area area = null;
/*     */       
/* 390 */       if (this.stdUnderline != null) {
/* 391 */         Shape shape = this.stdUnderline.getUnderlineShape(f1, f3, f4, param1Float2 + f2);
/*     */         
/* 393 */         area = new Area(shape);
/*     */       } 
/*     */       
/* 396 */       if (this.strikethrough) {
/* 397 */         BasicStroke basicStroke = new BasicStroke(coreMetrics.strikethroughThickness, 0, 0);
/*     */ 
/*     */         
/* 400 */         float f = param1Float2 + coreMetrics.strikethroughOffset;
/* 401 */         Line2D.Float float_ = new Line2D.Float(f3, f, f4, f);
/* 402 */         Area area1 = new Area(basicStroke.createStrokedShape(float_));
/* 403 */         if (area == null) {
/* 404 */           area = area1;
/*     */         } else {
/* 406 */           area.add(area1);
/*     */         } 
/*     */       } 
/*     */       
/* 410 */       if (this.imUnderline != null) {
/* 411 */         Shape shape = this.imUnderline.getUnderlineShape(f1, f3, f4, param1Float2 + f2);
/*     */         
/* 413 */         Area area1 = new Area(shape);
/* 414 */         if (area == null) {
/* 415 */           area = area1;
/*     */         } else {
/*     */           
/* 418 */           area.add(area1);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 423 */       area.add(new Area(param1Label.handleGetOutline(param1Float1, param1Float2)));
/*     */       
/* 425 */       return new GeneralPath(area);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 430 */       StringBuffer stringBuffer = new StringBuffer();
/* 431 */       stringBuffer.append(super.toString());
/* 432 */       stringBuffer.append("[");
/* 433 */       if (this.fgPaint != null) stringBuffer.append("fgPaint: " + this.fgPaint); 
/* 434 */       if (this.bgPaint != null) stringBuffer.append(" bgPaint: " + this.bgPaint); 
/* 435 */       if (this.swapColors) stringBuffer.append(" swapColors: true"); 
/* 436 */       if (this.strikethrough) stringBuffer.append(" strikethrough: true"); 
/* 437 */       if (this.stdUnderline != null) stringBuffer.append(" stdUnderline: " + this.stdUnderline); 
/* 438 */       if (this.imUnderline != null) stringBuffer.append(" imUnderline: " + this.imUnderline); 
/* 439 */       stringBuffer.append("]");
/* 440 */       return stringBuffer.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Label {
/*     */     CoreMetrics getCoreMetrics();
/*     */     
/*     */     Rectangle2D getLogicalBounds();
/*     */     
/*     */     void handleDraw(Graphics2D param1Graphics2D, float param1Float1, float param1Float2);
/*     */     
/*     */     Rectangle2D handleGetCharVisualBounds(int param1Int);
/*     */     
/*     */     Rectangle2D handleGetVisualBounds();
/*     */     
/*     */     Shape handleGetOutline(float param1Float1, float param1Float2);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/Decoration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */