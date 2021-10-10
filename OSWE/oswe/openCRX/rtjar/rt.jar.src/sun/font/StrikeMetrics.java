/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StrikeMetrics
/*     */ {
/*     */   public float ascentX;
/*     */   public float ascentY;
/*     */   public float descentX;
/*     */   public float descentY;
/*     */   public float baselineX;
/*     */   public float baselineY;
/*     */   public float leadingX;
/*     */   public float leadingY;
/*     */   public float maxAdvanceX;
/*     */   public float maxAdvanceY;
/*     */   
/*     */   StrikeMetrics() {
/*  89 */     this.ascentX = this.ascentY = 2.14748365E9F;
/*  90 */     this.descentX = this.descentY = this.leadingX = this.leadingY = -2.14748365E9F;
/*  91 */     this.baselineX = this.maxAdvanceX = this.maxAdvanceY = -2.14748365E9F;
/*     */   }
/*     */ 
/*     */   
/*     */   StrikeMetrics(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10) {
/*  96 */     this.ascentX = paramFloat1;
/*  97 */     this.ascentY = paramFloat2;
/*  98 */     this.descentX = paramFloat3;
/*  99 */     this.descentY = paramFloat4;
/* 100 */     this.baselineX = paramFloat5;
/* 101 */     this.baselineY = paramFloat6;
/* 102 */     this.leadingX = paramFloat7;
/* 103 */     this.leadingY = paramFloat8;
/* 104 */     this.maxAdvanceX = paramFloat9;
/* 105 */     this.maxAdvanceY = paramFloat10;
/*     */   }
/*     */   
/*     */   public float getAscent() {
/* 109 */     return -this.ascentY;
/*     */   }
/*     */   
/*     */   public float getDescent() {
/* 113 */     return this.descentY;
/*     */   }
/*     */   
/*     */   public float getLeading() {
/* 117 */     return this.leadingY;
/*     */   }
/*     */   
/*     */   public float getMaxAdvance() {
/* 121 */     return this.maxAdvanceX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void merge(StrikeMetrics paramStrikeMetrics) {
/* 129 */     if (paramStrikeMetrics == null) {
/*     */       return;
/*     */     }
/* 132 */     if (paramStrikeMetrics.ascentX < this.ascentX) {
/* 133 */       this.ascentX = paramStrikeMetrics.ascentX;
/*     */     }
/* 135 */     if (paramStrikeMetrics.ascentY < this.ascentY) {
/* 136 */       this.ascentY = paramStrikeMetrics.ascentY;
/*     */     }
/* 138 */     if (paramStrikeMetrics.descentX > this.descentX) {
/* 139 */       this.descentX = paramStrikeMetrics.descentX;
/*     */     }
/* 141 */     if (paramStrikeMetrics.descentY > this.descentY) {
/* 142 */       this.descentY = paramStrikeMetrics.descentY;
/*     */     }
/* 144 */     if (paramStrikeMetrics.baselineX > this.baselineX) {
/* 145 */       this.baselineX = paramStrikeMetrics.baselineX;
/*     */     }
/* 147 */     if (paramStrikeMetrics.baselineY > this.baselineY) {
/* 148 */       this.baselineY = paramStrikeMetrics.baselineY;
/*     */     }
/* 150 */     if (paramStrikeMetrics.leadingX > this.leadingX) {
/* 151 */       this.leadingX = paramStrikeMetrics.leadingX;
/*     */     }
/* 153 */     if (paramStrikeMetrics.leadingY > this.leadingY) {
/* 154 */       this.leadingY = paramStrikeMetrics.leadingY;
/*     */     }
/* 156 */     if (paramStrikeMetrics.maxAdvanceX > this.maxAdvanceX) {
/* 157 */       this.maxAdvanceX = paramStrikeMetrics.maxAdvanceX;
/*     */     }
/* 159 */     if (paramStrikeMetrics.maxAdvanceY > this.maxAdvanceY) {
/* 160 */       this.maxAdvanceY = paramStrikeMetrics.maxAdvanceY;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void convertToUserSpace(AffineTransform paramAffineTransform) {
/* 169 */     Point2D.Float float_ = new Point2D.Float();
/*     */     
/* 171 */     float_.x = this.ascentX; float_.y = this.ascentY;
/* 172 */     paramAffineTransform.deltaTransform(float_, float_);
/* 173 */     this.ascentX = float_.x; this.ascentY = float_.y;
/*     */     
/* 175 */     float_.x = this.descentX; float_.y = this.descentY;
/* 176 */     paramAffineTransform.deltaTransform(float_, float_);
/* 177 */     this.descentX = float_.x; this.descentY = float_.y;
/*     */     
/* 179 */     float_.x = this.baselineX; float_.y = this.baselineY;
/* 180 */     paramAffineTransform.deltaTransform(float_, float_);
/* 181 */     this.baselineX = float_.x; this.baselineY = float_.y;
/*     */     
/* 183 */     float_.x = this.leadingX; float_.y = this.leadingY;
/* 184 */     paramAffineTransform.deltaTransform(float_, float_);
/* 185 */     this.leadingX = float_.x; this.leadingY = float_.y;
/*     */     
/* 187 */     float_.x = this.maxAdvanceX; float_.y = this.maxAdvanceY;
/* 188 */     paramAffineTransform.deltaTransform(float_, float_);
/* 189 */     this.maxAdvanceX = float_.x; this.maxAdvanceY = float_.y;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 193 */     return "ascent:x=" + this.ascentX + " y=" + this.ascentY + " descent:x=" + this.descentX + " y=" + this.descentY + " baseline:x=" + this.baselineX + " y=" + this.baselineY + " leading:x=" + this.leadingX + " y=" + this.leadingY + " maxAdvance:x=" + this.maxAdvanceX + " y=" + this.maxAdvanceY;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/StrikeMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */