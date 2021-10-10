/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.LineMetrics;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontLineMetrics
/*     */   extends LineMetrics
/*     */   implements Cloneable
/*     */ {
/*     */   public int numchars;
/*     */   public final CoreMetrics cm;
/*     */   public final FontRenderContext frc;
/*     */   
/*     */   public FontLineMetrics(int paramInt, CoreMetrics paramCoreMetrics, FontRenderContext paramFontRenderContext) {
/*  48 */     this.numchars = paramInt;
/*  49 */     this.cm = paramCoreMetrics;
/*  50 */     this.frc = paramFontRenderContext;
/*     */   }
/*     */   
/*     */   public final int getNumChars() {
/*  54 */     return this.numchars;
/*     */   }
/*     */   
/*     */   public final float getAscent() {
/*  58 */     return this.cm.ascent;
/*     */   }
/*     */   
/*     */   public final float getDescent() {
/*  62 */     return this.cm.descent;
/*     */   }
/*     */   
/*     */   public final float getLeading() {
/*  66 */     return this.cm.leading;
/*     */   }
/*     */   
/*     */   public final float getHeight() {
/*  70 */     return this.cm.height;
/*     */   }
/*     */   
/*     */   public final int getBaselineIndex() {
/*  74 */     return this.cm.baselineIndex;
/*     */   }
/*     */   
/*     */   public final float[] getBaselineOffsets() {
/*  78 */     return (float[])this.cm.baselineOffsets.clone();
/*     */   }
/*     */   
/*     */   public final float getStrikethroughOffset() {
/*  82 */     return this.cm.strikethroughOffset;
/*     */   }
/*     */   
/*     */   public final float getStrikethroughThickness() {
/*  86 */     return this.cm.strikethroughThickness;
/*     */   }
/*     */   
/*     */   public final float getUnderlineOffset() {
/*  90 */     return this.cm.underlineOffset;
/*     */   }
/*     */   
/*     */   public final float getUnderlineThickness() {
/*  94 */     return this.cm.underlineThickness;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*  98 */     return this.cm.hashCode();
/*     */   }
/*     */   
/*     */   public final boolean equals(Object paramObject) {
/*     */     try {
/* 103 */       return this.cm.equals(((FontLineMetrics)paramObject).cm);
/*     */     }
/* 105 */     catch (ClassCastException classCastException) {
/* 106 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final Object clone() {
/*     */     try {
/* 113 */       return super.clone();
/*     */     }
/* 115 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 116 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontLineMetrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */