/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.geom.GeneralPath;
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
/*     */ public final class CompositeStrike
/*     */   extends FontStrike
/*     */ {
/*     */   static final int SLOTMASK = 16777215;
/*     */   private CompositeFont compFont;
/*     */   private PhysicalStrike[] strikes;
/*  48 */   int numGlyphs = 0;
/*     */   
/*     */   CompositeStrike(CompositeFont paramCompositeFont, FontStrikeDesc paramFontStrikeDesc) {
/*  51 */     this.compFont = paramCompositeFont;
/*  52 */     this.desc = paramFontStrikeDesc;
/*  53 */     this.disposer = new FontStrikeDisposer(this.compFont, paramFontStrikeDesc);
/*  54 */     if (paramFontStrikeDesc.style != this.compFont.style) {
/*  55 */       this.algoStyle = true;
/*  56 */       if ((paramFontStrikeDesc.style & 0x1) == 1 && (this.compFont.style & 0x1) == 0)
/*     */       {
/*  58 */         this.boldness = 1.33F;
/*     */       }
/*  60 */       if ((paramFontStrikeDesc.style & 0x2) == 2 && (this.compFont.style & 0x2) == 0)
/*     */       {
/*  62 */         this.italic = 0.7F;
/*     */       }
/*     */     } 
/*  65 */     this.strikes = new PhysicalStrike[this.compFont.numSlots];
/*     */   }
/*     */ 
/*     */   
/*     */   PhysicalStrike getStrikeForGlyph(int paramInt) {
/*  70 */     return getStrikeForSlot(paramInt >>> 24);
/*     */   }
/*     */ 
/*     */   
/*     */   PhysicalStrike getStrikeForSlot(int paramInt) {
/*  75 */     if (paramInt >= this.strikes.length) {
/*  76 */       paramInt = 0;
/*     */     }
/*     */     
/*  79 */     PhysicalStrike physicalStrike = this.strikes[paramInt];
/*  80 */     if (physicalStrike == null) {
/*     */       
/*  82 */       physicalStrike = (PhysicalStrike)this.compFont.getSlotFont(paramInt).getStrike(this.desc);
/*     */       
/*  84 */       this.strikes[paramInt] = physicalStrike;
/*     */     } 
/*  86 */     return physicalStrike;
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/*  90 */     return this.compFont.getNumGlyphs();
/*     */   }
/*     */   
/*     */   StrikeMetrics getFontMetrics() {
/*  94 */     if (this.strikeMetrics == null) {
/*  95 */       StrikeMetrics strikeMetrics = new StrikeMetrics();
/*  96 */       for (byte b = 0; b < this.compFont.numMetricsSlots; b++) {
/*  97 */         strikeMetrics.merge(getStrikeForSlot(b).getFontMetrics());
/*     */       }
/*  99 */       this.strikeMetrics = strikeMetrics;
/*     */     } 
/* 101 */     return this.strikeMetrics;
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
/*     */   void getGlyphImagePtrs(int[] paramArrayOfint, long[] paramArrayOflong, int paramInt) {
/* 118 */     PhysicalStrike physicalStrike = getStrikeForSlot(0);
/* 119 */     int i = physicalStrike.getSlot0GlyphImagePtrs(paramArrayOfint, paramArrayOflong, paramInt);
/* 120 */     if (i == paramInt) {
/*     */       return;
/*     */     }
/* 123 */     for (int j = i; j < paramInt; j++) {
/* 124 */       physicalStrike = getStrikeForGlyph(paramArrayOfint[j]);
/* 125 */       paramArrayOflong[j] = physicalStrike.getGlyphImagePtr(paramArrayOfint[j] & 0xFFFFFF);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   long getGlyphImagePtr(int paramInt) {
/* 131 */     PhysicalStrike physicalStrike = getStrikeForGlyph(paramInt);
/* 132 */     return physicalStrike.getGlyphImagePtr(paramInt & 0xFFFFFF);
/*     */   }
/*     */   
/*     */   void getGlyphImageBounds(int paramInt, Point2D.Float paramFloat, Rectangle paramRectangle) {
/* 136 */     PhysicalStrike physicalStrike = getStrikeForGlyph(paramInt);
/* 137 */     physicalStrike.getGlyphImageBounds(paramInt & 0xFFFFFF, paramFloat, paramRectangle);
/*     */   }
/*     */   
/*     */   Point2D.Float getGlyphMetrics(int paramInt) {
/* 141 */     PhysicalStrike physicalStrike = getStrikeForGlyph(paramInt);
/* 142 */     return physicalStrike.getGlyphMetrics(paramInt & 0xFFFFFF);
/*     */   }
/*     */   
/*     */   Point2D.Float getCharMetrics(char paramChar) {
/* 146 */     return getGlyphMetrics(this.compFont.getMapper().charToGlyph(paramChar));
/*     */   }
/*     */   
/*     */   float getGlyphAdvance(int paramInt) {
/* 150 */     PhysicalStrike physicalStrike = getStrikeForGlyph(paramInt);
/* 151 */     return physicalStrike.getGlyphAdvance(paramInt & 0xFFFFFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float getCodePointAdvance(int paramInt) {
/* 162 */     return getGlyphAdvance(this.compFont.getMapper().charToGlyph(paramInt));
/*     */   }
/*     */   
/*     */   Rectangle2D.Float getGlyphOutlineBounds(int paramInt) {
/* 166 */     PhysicalStrike physicalStrike = getStrikeForGlyph(paramInt);
/* 167 */     return physicalStrike.getGlyphOutlineBounds(paramInt & 0xFFFFFF);
/*     */   }
/*     */ 
/*     */   
/*     */   GeneralPath getGlyphOutline(int paramInt, float paramFloat1, float paramFloat2) {
/* 172 */     PhysicalStrike physicalStrike = getStrikeForGlyph(paramInt);
/* 173 */     GeneralPath generalPath = physicalStrike.getGlyphOutline(paramInt & 0xFFFFFF, paramFloat1, paramFloat2);
/* 174 */     if (generalPath == null) {
/* 175 */       return new GeneralPath();
/*     */     }
/* 177 */     return generalPath;
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
/*     */   GeneralPath getGlyphVectorOutline(int[] paramArrayOfint, float paramFloat1, float paramFloat2) {
/* 189 */     GeneralPath generalPath = null;
/*     */     
/* 191 */     byte b = 0;
/*     */ 
/*     */     
/* 194 */     while (b < paramArrayOfint.length) {
/* 195 */       byte b1 = b;
/* 196 */       int i = paramArrayOfint[b] >>> 24;
/* 197 */       while (b < paramArrayOfint.length && paramArrayOfint[b + 1] >>> 24 == i)
/*     */       {
/* 199 */         b++;
/*     */       }
/* 201 */       int j = b - b1 + 1;
/* 202 */       int[] arrayOfInt = new int[j];
/* 203 */       for (byte b2 = 0; b2 < j; b2++) {
/* 204 */         arrayOfInt[b2] = paramArrayOfint[b2] & 0xFFFFFF;
/*     */       }
/* 206 */       GeneralPath generalPath1 = getStrikeForSlot(i).getGlyphVectorOutline(arrayOfInt, paramFloat1, paramFloat2);
/* 207 */       if (generalPath == null) {
/* 208 */         generalPath = generalPath1; continue;
/* 209 */       }  if (generalPath1 != null) {
/* 210 */         generalPath.append(generalPath1, false);
/*     */       }
/*     */     } 
/* 213 */     if (generalPath == null) {
/* 214 */       return new GeneralPath();
/*     */     }
/* 216 */     return generalPath;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/CompositeStrike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */