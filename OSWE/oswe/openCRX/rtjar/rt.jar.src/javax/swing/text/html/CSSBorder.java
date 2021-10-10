/*     */ package javax.swing.text.html;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.border.AbstractBorder;
/*     */ import javax.swing.text.AttributeSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CSSBorder
/*     */   extends AbstractBorder
/*     */ {
/*     */   static final int COLOR = 0;
/*     */   static final int STYLE = 1;
/*     */   static final int WIDTH = 2;
/*     */   static final int TOP = 0;
/*     */   static final int RIGHT = 1;
/*     */   static final int BOTTOM = 2;
/*     */   static final int LEFT = 3;
/*  62 */   static final CSS.Attribute[][] ATTRIBUTES = new CSS.Attribute[][] { { CSS.Attribute.BORDER_TOP_COLOR, CSS.Attribute.BORDER_RIGHT_COLOR, CSS.Attribute.BORDER_BOTTOM_COLOR, CSS.Attribute.BORDER_LEFT_COLOR }, { CSS.Attribute.BORDER_TOP_STYLE, CSS.Attribute.BORDER_RIGHT_STYLE, CSS.Attribute.BORDER_BOTTOM_STYLE, CSS.Attribute.BORDER_LEFT_STYLE }, { CSS.Attribute.BORDER_TOP_WIDTH, CSS.Attribute.BORDER_RIGHT_WIDTH, CSS.Attribute.BORDER_BOTTOM_WIDTH, CSS.Attribute.BORDER_LEFT_WIDTH } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   static final CSS.CssValue[] PARSERS = new CSS.CssValue[] { new CSS.ColorValue(), new CSS.BorderStyle(), new CSS.BorderWidthValue(null, 0) };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   static final Object[] DEFAULTS = new Object[] { CSS.Attribute.BORDER_COLOR, PARSERS[1]
/*     */       
/*  79 */       .parseCssValue(CSS.Attribute.BORDER_STYLE.getDefaultValue()), PARSERS[2]
/*  80 */       .parseCssValue(CSS.Attribute.BORDER_WIDTH.getDefaultValue()) };
/*     */ 
/*     */ 
/*     */   
/*     */   final AttributeSet attrs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CSSBorder(AttributeSet paramAttributeSet) {
/*  90 */     this.attrs = paramAttributeSet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Color getBorderColor(int paramInt) {
/*     */     CSS.ColorValue colorValue;
/*  97 */     Object object = this.attrs.getAttribute(ATTRIBUTES[0][paramInt]);
/*     */     
/*  99 */     if (object instanceof CSS.ColorValue) {
/* 100 */       colorValue = (CSS.ColorValue)object;
/*     */     }
/*     */     else {
/*     */       
/* 104 */       colorValue = (CSS.ColorValue)this.attrs.getAttribute(CSS.Attribute.COLOR);
/* 105 */       if (colorValue == null) {
/* 106 */         colorValue = (CSS.ColorValue)PARSERS[0].parseCssValue(CSS.Attribute.COLOR
/* 107 */             .getDefaultValue());
/*     */       }
/*     */     } 
/* 110 */     return colorValue.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getBorderWidth(int paramInt) {
/* 117 */     int i = 0;
/* 118 */     CSS.BorderStyle borderStyle = (CSS.BorderStyle)this.attrs.getAttribute(ATTRIBUTES[1][paramInt]);
/*     */     
/* 120 */     if (borderStyle != null && borderStyle.getValue() != CSS.Value.NONE) {
/*     */ 
/*     */       
/* 123 */       CSS.LengthValue lengthValue = (CSS.LengthValue)this.attrs.getAttribute(ATTRIBUTES[2][paramInt]);
/*     */       
/* 125 */       if (lengthValue == null) {
/* 126 */         lengthValue = (CSS.LengthValue)DEFAULTS[2];
/*     */       }
/* 128 */       i = (int)lengthValue.getValue(true);
/*     */     } 
/* 130 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] getWidths() {
/* 137 */     int[] arrayOfInt = new int[4];
/* 138 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 139 */       arrayOfInt[b] = getBorderWidth(b);
/*     */     }
/* 141 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CSS.Value getBorderStyle(int paramInt) {
/* 149 */     CSS.BorderStyle borderStyle = (CSS.BorderStyle)this.attrs.getAttribute(ATTRIBUTES[1][paramInt]);
/* 150 */     if (borderStyle == null) {
/* 151 */       borderStyle = (CSS.BorderStyle)DEFAULTS[1];
/*     */     }
/* 153 */     return borderStyle.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Polygon getBorderShape(int paramInt) {
/* 161 */     Polygon polygon = null;
/* 162 */     int[] arrayOfInt = getWidths();
/* 163 */     if (arrayOfInt[paramInt] != 0) {
/* 164 */       polygon = new Polygon(new int[4], new int[4], 0);
/* 165 */       polygon.addPoint(0, 0);
/* 166 */       polygon.addPoint(-arrayOfInt[(paramInt + 3) % 4], -arrayOfInt[paramInt]);
/* 167 */       polygon.addPoint(arrayOfInt[(paramInt + 1) % 4], -arrayOfInt[paramInt]);
/* 168 */       polygon.addPoint(0, 0);
/*     */     } 
/* 170 */     return polygon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BorderPainter getBorderPainter(int paramInt) {
/* 177 */     CSS.Value value = getBorderStyle(paramInt);
/* 178 */     return borderPainters.get(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Color getAdjustedColor(Color paramColor, double paramDouble) {
/* 188 */     double d1 = 1.0D - Math.min(Math.abs(paramDouble), 1.0D);
/* 189 */     double d2 = (paramDouble > 0.0D) ? (255.0D * (1.0D - d1)) : 0.0D;
/* 190 */     return new Color((int)(paramColor.getRed() * d1 + d2), 
/* 191 */         (int)(paramColor.getGreen() * d1 + d2), 
/* 192 */         (int)(paramColor.getBlue() * d1 + d2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/* 199 */     int[] arrayOfInt = getWidths();
/* 200 */     paramInsets.set(arrayOfInt[0], arrayOfInt[3], arrayOfInt[2], arrayOfInt[1]);
/* 201 */     return paramInsets;
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 206 */     if (!(paramGraphics instanceof Graphics2D)) {
/*     */       return;
/*     */     }
/*     */     
/* 210 */     Graphics2D graphics2D = (Graphics2D)paramGraphics.create();
/*     */     
/* 212 */     int[] arrayOfInt = getWidths();
/*     */ 
/*     */     
/* 215 */     int i = paramInt1 + arrayOfInt[3];
/* 216 */     int j = paramInt2 + arrayOfInt[0];
/* 217 */     int k = paramInt3 - arrayOfInt[1] + arrayOfInt[3];
/* 218 */     int m = paramInt4 - arrayOfInt[0] + arrayOfInt[2];
/*     */ 
/*     */     
/* 221 */     int[][] arrayOfInt1 = { { i, j }, { i + k, j }, { i + k, j + m }, { i, j + m } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     for (byte b = 0; b < 4; b++) {
/* 230 */       CSS.Value value = getBorderStyle(b);
/* 231 */       Polygon polygon = getBorderShape(b);
/* 232 */       if (value != CSS.Value.NONE && polygon != null) {
/* 233 */         int n = (b % 2 == 0) ? k : m;
/*     */ 
/*     */         
/* 236 */         polygon.xpoints[2] = polygon.xpoints[2] + n;
/* 237 */         polygon.xpoints[3] = polygon.xpoints[3] + n;
/* 238 */         Color color = getBorderColor(b);
/* 239 */         BorderPainter borderPainter = getBorderPainter(b);
/*     */         
/* 241 */         double d = b * Math.PI / 2.0D;
/* 242 */         graphics2D.setClip(paramGraphics.getClip());
/* 243 */         graphics2D.translate(arrayOfInt1[b][0], arrayOfInt1[b][1]);
/* 244 */         graphics2D.rotate(d);
/* 245 */         graphics2D.clip(polygon);
/* 246 */         borderPainter.paint(polygon, graphics2D, color, b);
/* 247 */         graphics2D.rotate(-d);
/* 248 */         graphics2D.translate(-arrayOfInt1[b][0], -arrayOfInt1[b][1]);
/*     */       } 
/*     */     } 
/* 251 */     graphics2D.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static interface BorderPainter
/*     */   {
/*     */     void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class NullPainter
/*     */     implements BorderPainter
/*     */   {
/*     */     public void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SolidPainter
/*     */     implements BorderPainter
/*     */   {
/*     */     public void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int) {
/* 283 */       param1Graphics.setColor(param1Color);
/* 284 */       param1Graphics.fillPolygon(param1Polygon);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class StrokePainter
/*     */     implements BorderPainter
/*     */   {
/*     */     void paintStrokes(Rectangle param1Rectangle, Graphics param1Graphics, int param1Int, int[] param1ArrayOfint, Color[] param1ArrayOfColor) {
/* 298 */       boolean bool = (param1Int == 0) ? true : false;
/* 299 */       int i = 0;
/* 300 */       int j = bool ? param1Rectangle.width : param1Rectangle.height;
/* 301 */       while (i < j) {
/* 302 */         for (byte b = 0; b < param1ArrayOfint.length && 
/* 303 */           i < j; b++) {
/*     */ 
/*     */           
/* 306 */           int k = param1ArrayOfint[b];
/* 307 */           Color color = param1ArrayOfColor[b];
/* 308 */           if (color != null) {
/* 309 */             int m = param1Rectangle.x + (bool ? i : 0);
/* 310 */             int n = param1Rectangle.y + (bool ? 0 : i);
/* 311 */             int i1 = bool ? k : param1Rectangle.width;
/* 312 */             int i2 = bool ? param1Rectangle.height : k;
/* 313 */             param1Graphics.setColor(color);
/* 314 */             param1Graphics.fillRect(m, n, i1, i2);
/*     */           } 
/* 316 */           i += k;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class DoublePainter
/*     */     extends StrokePainter
/*     */   {
/*     */     public void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int) {
/* 327 */       Rectangle rectangle = param1Polygon.getBounds();
/* 328 */       int i = Math.max(rectangle.height / 3, 1);
/* 329 */       int[] arrayOfInt = { i, i };
/* 330 */       Color[] arrayOfColor = { param1Color, null };
/* 331 */       paintStrokes(rectangle, param1Graphics, 1, arrayOfInt, arrayOfColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class DottedDashedPainter
/*     */     extends StrokePainter
/*     */   {
/*     */     final int factor;
/*     */     
/*     */     DottedDashedPainter(int param1Int) {
/* 342 */       this.factor = param1Int;
/*     */     }
/*     */     
/*     */     public void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int) {
/* 346 */       Rectangle rectangle = param1Polygon.getBounds();
/* 347 */       int i = rectangle.height * this.factor;
/* 348 */       int[] arrayOfInt = { i, i };
/* 349 */       Color[] arrayOfColor = { param1Color, null };
/* 350 */       paintStrokes(rectangle, param1Graphics, 0, arrayOfInt, arrayOfColor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class ShadowLightPainter
/*     */     extends StrokePainter
/*     */   {
/*     */     static Color getShadowColor(Color param1Color) {
/* 362 */       return CSSBorder.getAdjustedColor(param1Color, -0.3D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Color getLightColor(Color param1Color) {
/* 369 */       return CSSBorder.getAdjustedColor(param1Color, 0.7D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class GrooveRidgePainter
/*     */     extends ShadowLightPainter
/*     */   {
/*     */     final CSS.Value type;
/*     */     
/*     */     GrooveRidgePainter(CSS.Value param1Value) {
/* 380 */       this.type = param1Value;
/*     */     }
/*     */     
/*     */     public void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int) {
/* 384 */       Rectangle rectangle = param1Polygon.getBounds();
/* 385 */       int i = Math.max(rectangle.height / 2, 1);
/* 386 */       int[] arrayOfInt = { i, i };
/* 387 */       (new Color[2])[0] = 
/*     */         
/* 389 */         getShadowColor(param1Color); (new Color[2])[1] = getLightColor(param1Color); (new Color[2])[0] = 
/* 390 */         getLightColor(param1Color); (new Color[2])[1] = getShadowColor(param1Color); Color[] arrayOfColor = ((((param1Int + 1) % 4 < 2) ? true : false) == ((this.type == CSS.Value.GROOVE) ? true : false)) ? new Color[2] : new Color[2];
/* 391 */       paintStrokes(rectangle, param1Graphics, 1, arrayOfInt, arrayOfColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class InsetOutsetPainter
/*     */     extends ShadowLightPainter
/*     */   {
/*     */     CSS.Value type;
/*     */     
/*     */     InsetOutsetPainter(CSS.Value param1Value) {
/* 402 */       this.type = param1Value;
/*     */     }
/*     */     
/*     */     public void paint(Polygon param1Polygon, Graphics param1Graphics, Color param1Color, int param1Int) {
/* 406 */       param1Graphics.setColor(((((param1Int + 1) % 4 < 2) ? true : false) == ((this.type == CSS.Value.INSET) ? true : false)) ? 
/* 407 */           getShadowColor(param1Color) : getLightColor(param1Color));
/* 408 */       param1Graphics.fillPolygon(param1Polygon);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void registerBorderPainter(CSS.Value paramValue, BorderPainter paramBorderPainter) {
/* 416 */     borderPainters.put(paramValue, paramBorderPainter);
/*     */   }
/*     */ 
/*     */   
/* 420 */   static Map<CSS.Value, BorderPainter> borderPainters = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 425 */     registerBorderPainter(CSS.Value.NONE, new NullPainter());
/* 426 */     registerBorderPainter(CSS.Value.HIDDEN, new NullPainter());
/* 427 */     registerBorderPainter(CSS.Value.SOLID, new SolidPainter());
/* 428 */     registerBorderPainter(CSS.Value.DOUBLE, new DoublePainter());
/* 429 */     registerBorderPainter(CSS.Value.DOTTED, new DottedDashedPainter(1));
/* 430 */     registerBorderPainter(CSS.Value.DASHED, new DottedDashedPainter(3));
/* 431 */     registerBorderPainter(CSS.Value.GROOVE, new GrooveRidgePainter(CSS.Value.GROOVE));
/* 432 */     registerBorderPainter(CSS.Value.RIDGE, new GrooveRidgePainter(CSS.Value.RIDGE));
/* 433 */     registerBorderPainter(CSS.Value.INSET, new InsetOutsetPainter(CSS.Value.INSET));
/* 434 */     registerBorderPainter(CSS.Value.OUTSET, new InsetOutsetPainter(CSS.Value.OUTSET));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/html/CSSBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */