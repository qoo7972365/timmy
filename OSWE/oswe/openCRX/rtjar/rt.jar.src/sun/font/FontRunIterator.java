/*     */ package sun.font;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FontRunIterator
/*     */ {
/*     */   CompositeFont font;
/*     */   char[] text;
/*     */   int start;
/*     */   int limit;
/*     */   CompositeGlyphMapper mapper;
/*  44 */   int slot = -1; int pos; static final int SURROGATE_START = 65536; static final int LEAD_START = 55296;
/*     */   static final int LEAD_LIMIT = 56320;
/*     */   
/*     */   public void init(CompositeFont paramCompositeFont, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  48 */     if (paramCompositeFont == null || paramArrayOfchar == null || paramInt1 < 0 || paramInt2 < paramInt1 || paramInt2 > paramArrayOfchar.length) {
/*  49 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  52 */     this.font = paramCompositeFont;
/*  53 */     this.text = paramArrayOfchar;
/*  54 */     this.start = paramInt1;
/*  55 */     this.limit = paramInt2;
/*     */     
/*  57 */     this.mapper = (CompositeGlyphMapper)paramCompositeFont.getMapper();
/*  58 */     this.slot = -1;
/*  59 */     this.pos = paramInt1;
/*     */   }
/*     */   static final int TAIL_START = 56320; static final int TAIL_LIMIT = 57344; static final int LEAD_SURROGATE_SHIFT = 10; static final int SURROGATE_OFFSET = -56613888; static final int DONE = -1;
/*     */   public PhysicalFont getFont() {
/*  63 */     return (this.slot == -1) ? null : this.font.getSlotFont(this.slot);
/*     */   }
/*     */   
/*     */   public int getGlyphMask() {
/*  67 */     return this.slot << 24;
/*     */   }
/*     */   
/*     */   public int getPos() {
/*  71 */     return this.pos;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean next(int paramInt1, int paramInt2) {
/* 116 */     if (this.pos == paramInt2) {
/* 117 */       return false;
/*     */     }
/*     */     
/* 120 */     int i = nextCodePoint(paramInt2);
/* 121 */     int j = this.mapper.charToGlyph(i) & 0xFF000000;
/* 122 */     this.slot = j >>> 24;
/* 123 */     while ((i = nextCodePoint(paramInt2)) != -1 && (this.mapper.charToGlyph(i) & 0xFF000000) == j);
/* 124 */     pushback(i);
/*     */     
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   public boolean next() {
/* 130 */     return next(0, this.limit);
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
/*     */   final int nextCodePoint() {
/* 144 */     return nextCodePoint(this.limit);
/*     */   }
/*     */   
/*     */   final int nextCodePoint(int paramInt) {
/* 148 */     if (this.pos >= paramInt) {
/* 149 */       return -1;
/*     */     }
/* 151 */     int i = this.text[this.pos++];
/* 152 */     if (i >= 55296 && i < 56320 && this.pos < paramInt) {
/* 153 */       char c = this.text[this.pos];
/* 154 */       if (c >= '?' && c < '') {
/* 155 */         this.pos++;
/* 156 */         i = (i << 10) + c + -56613888;
/*     */       } 
/*     */     } 
/* 159 */     return i;
/*     */   }
/*     */   
/*     */   final void pushback(int paramInt) {
/* 163 */     if (paramInt >= 0)
/* 164 */       if (paramInt >= 65536) {
/* 165 */         this.pos -= 2;
/*     */       } else {
/* 167 */         this.pos--;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/FontRunIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */