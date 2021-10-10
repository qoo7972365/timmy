/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class Underline
/*     */ {
/*     */   private static final float DEFAULT_THICKNESS = 1.0F;
/*     */   private static final boolean USE_THICKNESS = true;
/*     */   private static final boolean IGNORE_THICKNESS = false;
/*     */   
/*     */   private static final class StandardUnderline
/*     */     extends Underline
/*     */   {
/*     */     private float shift;
/*     */     private float thicknessMultiplier;
/*     */     private float[] dashPattern;
/*     */     private boolean useThickness;
/*     */     private BasicStroke cachedStroke;
/*     */     
/*     */     StandardUnderline(float param1Float1, float param1Float2, float[] param1ArrayOffloat, boolean param1Boolean) {
/* 119 */       this.shift = param1Float1;
/* 120 */       this.thicknessMultiplier = param1Float2;
/* 121 */       this.dashPattern = param1ArrayOffloat;
/* 122 */       this.useThickness = param1Boolean;
/* 123 */       this.cachedStroke = null;
/*     */     }
/*     */ 
/*     */     
/*     */     private BasicStroke createStroke(float param1Float) {
/* 128 */       if (this.dashPattern == null) {
/* 129 */         return new BasicStroke(param1Float, 0, 0);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 134 */       return new BasicStroke(param1Float, 0, 0, 10.0F, this.dashPattern, 0.0F);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private float getLineThickness(float param1Float) {
/* 145 */       if (this.useThickness) {
/* 146 */         return param1Float * this.thicknessMultiplier;
/*     */       }
/*     */       
/* 149 */       return 1.0F * this.thicknessMultiplier;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private Stroke getStroke(float param1Float) {
/* 155 */       float f = getLineThickness(param1Float);
/* 156 */       BasicStroke basicStroke = this.cachedStroke;
/* 157 */       if (basicStroke == null || basicStroke
/* 158 */         .getLineWidth() != f) {
/*     */         
/* 160 */         basicStroke = createStroke(f);
/* 161 */         this.cachedStroke = basicStroke;
/*     */       } 
/*     */       
/* 164 */       return basicStroke;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void drawUnderline(Graphics2D param1Graphics2D, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 174 */       Stroke stroke = param1Graphics2D.getStroke();
/* 175 */       param1Graphics2D.setStroke(getStroke(param1Float1));
/* 176 */       param1Graphics2D.draw(new Line2D.Float(param1Float2, param1Float4 + this.shift, param1Float3, param1Float4 + this.shift));
/* 177 */       param1Graphics2D.setStroke(stroke);
/*     */     }
/*     */ 
/*     */     
/*     */     float getLowerDrawLimit(float param1Float) {
/* 182 */       return this.shift + getLineThickness(param1Float);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Shape getUnderlineShape(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 190 */       Stroke stroke = getStroke(param1Float1);
/* 191 */       Line2D.Float float_ = new Line2D.Float(param1Float2, param1Float4 + this.shift, param1Float3, param1Float4 + this.shift);
/* 192 */       return stroke.createStrokedShape(float_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IMGrayUnderline
/*     */     extends Underline
/*     */   {
/* 202 */     private BasicStroke stroke = new BasicStroke(1.0F, 0, 0, 10.0F, new float[] { 1.0F, 1.0F }, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void drawUnderline(Graphics2D param1Graphics2D, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 216 */       Stroke stroke = param1Graphics2D.getStroke();
/* 217 */       param1Graphics2D.setStroke(this.stroke);
/*     */       
/* 219 */       Line2D.Float float_ = new Line2D.Float(param1Float2, param1Float4, param1Float3, param1Float4);
/* 220 */       param1Graphics2D.draw(float_);
/*     */       
/* 222 */       float_.y1++;
/* 223 */       float_.y2++;
/* 224 */       float_.x1++;
/*     */       
/* 226 */       param1Graphics2D.draw(float_);
/*     */       
/* 228 */       param1Graphics2D.setStroke(stroke);
/*     */     }
/*     */ 
/*     */     
/*     */     float getLowerDrawLimit(float param1Float) {
/* 233 */       return 2.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Shape getUnderlineShape(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
/* 241 */       GeneralPath generalPath = new GeneralPath();
/*     */       
/* 243 */       Line2D.Float float_ = new Line2D.Float(param1Float2, param1Float4, param1Float3, param1Float4);
/* 244 */       generalPath.append(this.stroke.createStrokedShape(float_), false);
/*     */       
/* 246 */       float_.y1++;
/* 247 */       float_.y2++;
/* 248 */       float_.x1++;
/*     */       
/* 250 */       generalPath.append(this.stroke.createStrokedShape(float_), false);
/*     */       
/* 252 */       return generalPath;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   private static final ConcurrentHashMap<Object, Underline> UNDERLINES = new ConcurrentHashMap<>(6);
/*     */   private static final Underline[] UNDERLINE_LIST;
/*     */   
/*     */   static {
/* 266 */     Underline[] arrayOfUnderline = new Underline[6];
/*     */     
/* 268 */     arrayOfUnderline[0] = new StandardUnderline(0.0F, 1.0F, null, true);
/* 269 */     UNDERLINES.put(TextAttribute.UNDERLINE_ON, arrayOfUnderline[0]);
/*     */     
/* 271 */     arrayOfUnderline[1] = new StandardUnderline(1.0F, 1.0F, null, false);
/* 272 */     UNDERLINES.put(TextAttribute.UNDERLINE_LOW_ONE_PIXEL, arrayOfUnderline[1]);
/*     */     
/* 274 */     arrayOfUnderline[2] = new StandardUnderline(1.0F, 2.0F, null, false);
/* 275 */     UNDERLINES.put(TextAttribute.UNDERLINE_LOW_TWO_PIXEL, arrayOfUnderline[2]);
/*     */     
/* 277 */     arrayOfUnderline[3] = new StandardUnderline(1.0F, 1.0F, new float[] { 1.0F, 1.0F }, false);
/* 278 */     UNDERLINES.put(TextAttribute.UNDERLINE_LOW_DOTTED, arrayOfUnderline[3]);
/*     */     
/* 280 */     arrayOfUnderline[4] = new IMGrayUnderline();
/* 281 */     UNDERLINES.put(TextAttribute.UNDERLINE_LOW_GRAY, arrayOfUnderline[4]);
/*     */     
/* 283 */     arrayOfUnderline[5] = new StandardUnderline(1.0F, 1.0F, new float[] { 4.0F, 4.0F }, false);
/* 284 */     UNDERLINES.put(TextAttribute.UNDERLINE_LOW_DASHED, arrayOfUnderline[5]);
/*     */     
/* 286 */     UNDERLINE_LIST = arrayOfUnderline;
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
/*     */   static Underline getUnderline(Object paramObject) {
/* 298 */     if (paramObject == null) {
/* 299 */       return null;
/*     */     }
/*     */     
/* 302 */     return UNDERLINES.get(paramObject);
/*     */   }
/*     */   
/*     */   static Underline getUnderline(int paramInt) {
/* 306 */     return (paramInt < 0) ? null : UNDERLINE_LIST[paramInt];
/*     */   }
/*     */   
/*     */   abstract void drawUnderline(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */   
/*     */   abstract float getLowerDrawLimit(float paramFloat);
/*     */   
/*     */   abstract Shape getUnderlineShape(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/Underline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */