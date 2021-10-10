/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontRenderContext
/*     */ {
/*     */   private transient AffineTransform tx;
/*     */   private transient Object aaHintValue;
/*     */   private transient Object fmHintValue;
/*     */   private transient boolean defaulting;
/*     */   
/*     */   protected FontRenderContext() {
/*  78 */     this.aaHintValue = RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT;
/*  79 */     this.fmHintValue = RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT;
/*  80 */     this.defaulting = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderContext(AffineTransform paramAffineTransform, boolean paramBoolean1, boolean paramBoolean2) {
/* 106 */     if (paramAffineTransform != null && !paramAffineTransform.isIdentity()) {
/* 107 */       this.tx = new AffineTransform(paramAffineTransform);
/*     */     }
/* 109 */     if (paramBoolean1) {
/* 110 */       this.aaHintValue = RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
/*     */     } else {
/* 112 */       this.aaHintValue = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
/*     */     } 
/* 114 */     if (paramBoolean2) {
/* 115 */       this.fmHintValue = RenderingHints.VALUE_FRACTIONALMETRICS_ON;
/*     */     } else {
/* 117 */       this.fmHintValue = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderContext(AffineTransform paramAffineTransform, Object paramObject1, Object paramObject2) {
/* 146 */     if (paramAffineTransform != null && !paramAffineTransform.isIdentity()) {
/* 147 */       this.tx = new AffineTransform(paramAffineTransform);
/*     */     }
/*     */     try {
/* 150 */       if (RenderingHints.KEY_TEXT_ANTIALIASING.isCompatibleValue(paramObject1)) {
/* 151 */         this.aaHintValue = paramObject1;
/*     */       } else {
/* 153 */         throw new IllegalArgumentException("AA hint:" + paramObject1);
/*     */       } 
/* 155 */     } catch (Exception exception) {
/* 156 */       throw new IllegalArgumentException("AA hint:" + paramObject1);
/*     */     } 
/*     */     try {
/* 159 */       if (RenderingHints.KEY_FRACTIONALMETRICS.isCompatibleValue(paramObject2)) {
/* 160 */         this.fmHintValue = paramObject2;
/*     */       } else {
/* 162 */         throw new IllegalArgumentException("FM hint:" + paramObject2);
/*     */       } 
/* 164 */     } catch (Exception exception) {
/* 165 */       throw new IllegalArgumentException("FM hint:" + paramObject2);
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
/*     */   public boolean isTransformed() {
/* 179 */     if (!this.defaulting) {
/* 180 */       return (this.tx != null);
/*     */     }
/* 182 */     return !getTransform().isIdentity();
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
/*     */   public int getTransformType() {
/* 195 */     if (!this.defaulting) {
/* 196 */       if (this.tx == null) {
/* 197 */         return 0;
/*     */       }
/* 199 */       return this.tx.getType();
/*     */     } 
/*     */     
/* 202 */     return getTransform().getType();
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
/*     */   public AffineTransform getTransform() {
/* 214 */     return (this.tx == null) ? new AffineTransform() : new AffineTransform(this.tx);
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
/*     */   public boolean isAntiAliased() {
/* 229 */     return (this.aaHintValue != RenderingHints.VALUE_TEXT_ANTIALIAS_OFF && this.aaHintValue != RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
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
/*     */   
/*     */   public boolean usesFractionalMetrics() {
/* 246 */     return (this.fmHintValue != RenderingHints.VALUE_FRACTIONALMETRICS_OFF && this.fmHintValue != RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
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
/*     */   public Object getAntiAliasingHint() {
/* 260 */     if (this.defaulting) {
/* 261 */       if (isAntiAliased()) {
/* 262 */         return RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
/*     */       }
/* 264 */       return RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
/*     */     } 
/*     */     
/* 267 */     return this.aaHintValue;
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
/*     */   public Object getFractionalMetricsHint() {
/* 280 */     if (this.defaulting) {
/* 281 */       if (usesFractionalMetrics()) {
/* 282 */         return RenderingHints.VALUE_FRACTIONALMETRICS_ON;
/*     */       }
/* 284 */       return RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
/*     */     } 
/*     */     
/* 287 */     return this.fmHintValue;
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
/*     */   public boolean equals(Object paramObject) {
/*     */     try {
/* 300 */       return equals((FontRenderContext)paramObject);
/*     */     }
/* 302 */     catch (ClassCastException classCastException) {
/* 303 */       return false;
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
/*     */   public boolean equals(FontRenderContext paramFontRenderContext) {
/* 317 */     if (this == paramFontRenderContext) {
/* 318 */       return true;
/*     */     }
/* 320 */     if (paramFontRenderContext == null) {
/* 321 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 325 */     if (!paramFontRenderContext.defaulting && !this.defaulting) {
/* 326 */       if (paramFontRenderContext.aaHintValue == this.aaHintValue && paramFontRenderContext.fmHintValue == this.fmHintValue)
/*     */       {
/*     */         
/* 329 */         return (this.tx == null) ? ((paramFontRenderContext.tx == null)) : this.tx.equals(paramFontRenderContext.tx);
/*     */       }
/* 331 */       return false;
/*     */     } 
/* 333 */     return (paramFontRenderContext
/* 334 */       .getAntiAliasingHint() == getAntiAliasingHint() && paramFontRenderContext
/* 335 */       .getFractionalMetricsHint() == getFractionalMetricsHint() && paramFontRenderContext
/* 336 */       .getTransform().equals(getTransform()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 344 */     int i = (this.tx == null) ? 0 : this.tx.hashCode();
/*     */ 
/*     */ 
/*     */     
/* 348 */     if (this.defaulting) {
/* 349 */       i += getAntiAliasingHint().hashCode();
/* 350 */       i += getFractionalMetricsHint().hashCode();
/*     */     } else {
/* 352 */       i += this.aaHintValue.hashCode();
/* 353 */       i += this.fmHintValue.hashCode();
/*     */     } 
/* 355 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/FontRenderContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */