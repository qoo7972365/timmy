/*     */ package javax.swing.border;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.beans.ConstructorProperties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BevelBorder
/*     */   extends AbstractBorder
/*     */ {
/*     */   public static final int RAISED = 0;
/*     */   public static final int LOWERED = 1;
/*     */   protected int bevelType;
/*     */   protected Color highlightOuter;
/*     */   protected Color highlightInner;
/*     */   protected Color shadowInner;
/*     */   protected Color shadowOuter;
/*     */   
/*     */   public BevelBorder(int paramInt) {
/*  67 */     this.bevelType = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BevelBorder(int paramInt, Color paramColor1, Color paramColor2) {
/*  78 */     this(paramInt, paramColor1.brighter(), paramColor1, paramColor2, paramColor2.brighter());
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
/*     */ 
/*     */   
/*     */   @ConstructorProperties({"bevelType", "highlightOuterColor", "highlightInnerColor", "shadowOuterColor", "shadowInnerColor"})
/*     */   public BevelBorder(int paramInt, Color paramColor1, Color paramColor2, Color paramColor3, Color paramColor4) {
/*  95 */     this(paramInt);
/*  96 */     this.highlightOuter = paramColor1;
/*  97 */     this.highlightInner = paramColor2;
/*  98 */     this.shadowOuter = paramColor3;
/*  99 */     this.shadowInner = paramColor4;
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
/*     */   public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 113 */     if (this.bevelType == 0) {
/* 114 */       paintRaisedBevel(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/* 116 */     else if (this.bevelType == 1) {
/* 117 */       paintLoweredBevel(paramComponent, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getBorderInsets(Component paramComponent, Insets paramInsets) {
/* 127 */     paramInsets.set(2, 2, 2, 2);
/* 128 */     return paramInsets;
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
/*     */   public Color getHighlightOuterColor(Component paramComponent) {
/* 140 */     Color color = getHighlightOuterColor();
/* 141 */     return (color != null) ? color : paramComponent
/* 142 */       .getBackground().brighter().brighter();
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
/*     */   public Color getHighlightInnerColor(Component paramComponent) {
/* 154 */     Color color = getHighlightInnerColor();
/* 155 */     return (color != null) ? color : paramComponent
/* 156 */       .getBackground().brighter();
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
/*     */   public Color getShadowInnerColor(Component paramComponent) {
/* 168 */     Color color = getShadowInnerColor();
/* 169 */     return (color != null) ? color : paramComponent
/* 170 */       .getBackground().darker();
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
/*     */   public Color getShadowOuterColor(Component paramComponent) {
/* 182 */     Color color = getShadowOuterColor();
/* 183 */     return (color != null) ? color : paramComponent
/* 184 */       .getBackground().darker().darker();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getHighlightOuterColor() {
/* 194 */     return this.highlightOuter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getHighlightInnerColor() {
/* 204 */     return this.highlightInner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getShadowInnerColor() {
/* 214 */     return this.shadowInner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getShadowOuterColor() {
/* 224 */     return this.shadowOuter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBevelType() {
/* 231 */     return this.bevelType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBorderOpaque() {
/* 237 */     return true;
/*     */   }
/*     */   
/*     */   protected void paintRaisedBevel(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 241 */     Color color = paramGraphics.getColor();
/* 242 */     int i = paramInt4;
/* 243 */     int j = paramInt3;
/*     */     
/* 245 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */     
/* 247 */     paramGraphics.setColor(getHighlightOuterColor(paramComponent));
/* 248 */     paramGraphics.drawLine(0, 0, 0, i - 2);
/* 249 */     paramGraphics.drawLine(1, 0, j - 2, 0);
/*     */     
/* 251 */     paramGraphics.setColor(getHighlightInnerColor(paramComponent));
/* 252 */     paramGraphics.drawLine(1, 1, 1, i - 3);
/* 253 */     paramGraphics.drawLine(2, 1, j - 3, 1);
/*     */     
/* 255 */     paramGraphics.setColor(getShadowOuterColor(paramComponent));
/* 256 */     paramGraphics.drawLine(0, i - 1, j - 1, i - 1);
/* 257 */     paramGraphics.drawLine(j - 1, 0, j - 1, i - 2);
/*     */     
/* 259 */     paramGraphics.setColor(getShadowInnerColor(paramComponent));
/* 260 */     paramGraphics.drawLine(1, i - 2, j - 2, i - 2);
/* 261 */     paramGraphics.drawLine(j - 2, 1, j - 2, i - 3);
/*     */     
/* 263 */     paramGraphics.translate(-paramInt1, -paramInt2);
/* 264 */     paramGraphics.setColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paintLoweredBevel(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 270 */     Color color = paramGraphics.getColor();
/* 271 */     int i = paramInt4;
/* 272 */     int j = paramInt3;
/*     */     
/* 274 */     paramGraphics.translate(paramInt1, paramInt2);
/*     */     
/* 276 */     paramGraphics.setColor(getShadowInnerColor(paramComponent));
/* 277 */     paramGraphics.drawLine(0, 0, 0, i - 1);
/* 278 */     paramGraphics.drawLine(1, 0, j - 1, 0);
/*     */     
/* 280 */     paramGraphics.setColor(getShadowOuterColor(paramComponent));
/* 281 */     paramGraphics.drawLine(1, 1, 1, i - 2);
/* 282 */     paramGraphics.drawLine(2, 1, j - 2, 1);
/*     */     
/* 284 */     paramGraphics.setColor(getHighlightOuterColor(paramComponent));
/* 285 */     paramGraphics.drawLine(1, i - 1, j - 1, i - 1);
/* 286 */     paramGraphics.drawLine(j - 1, 1, j - 1, i - 2);
/*     */     
/* 288 */     paramGraphics.setColor(getHighlightInnerColor(paramComponent));
/* 289 */     paramGraphics.drawLine(2, i - 2, j - 2, i - 2);
/* 290 */     paramGraphics.drawLine(j - 2, 2, j - 2, i - 3);
/*     */     
/* 292 */     paramGraphics.translate(-paramInt1, -paramInt2);
/* 293 */     paramGraphics.setColor(color);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/border/BevelBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */