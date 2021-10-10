/*     */ package sun.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CharArrayCodePointIterator
/*     */   extends CodePointIterator
/*     */ {
/*     */   private char[] text;
/*     */   private int start;
/*     */   private int limit;
/*     */   private int index;
/*     */   
/*     */   public CharArrayCodePointIterator(char[] paramArrayOfchar) {
/*  75 */     this.text = paramArrayOfchar;
/*  76 */     this.limit = paramArrayOfchar.length;
/*     */   }
/*     */   
/*     */   public CharArrayCodePointIterator(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  80 */     if (paramInt1 < 0 || paramInt2 < paramInt1 || paramInt2 > paramArrayOfchar.length) {
/*  81 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  84 */     this.text = paramArrayOfchar;
/*  85 */     this.start = this.index = paramInt1;
/*  86 */     this.limit = paramInt2;
/*     */   }
/*     */   
/*     */   public void setToStart() {
/*  90 */     this.index = this.start;
/*     */   }
/*     */   
/*     */   public void setToLimit() {
/*  94 */     this.index = this.limit;
/*     */   }
/*     */   
/*     */   public int next() {
/*  98 */     if (this.index < this.limit) {
/*  99 */       char c = this.text[this.index++];
/* 100 */       if (Character.isHighSurrogate(c) && this.index < this.limit) {
/* 101 */         char c1 = this.text[this.index];
/* 102 */         if (Character.isLowSurrogate(c1)) {
/* 103 */           this.index++;
/* 104 */           return Character.toCodePoint(c, c1);
/*     */         } 
/*     */       } 
/* 107 */       return c;
/*     */     } 
/* 109 */     return -1;
/*     */   }
/*     */   
/*     */   public int prev() {
/* 113 */     if (this.index > this.start) {
/* 114 */       char c = this.text[--this.index];
/* 115 */       if (Character.isLowSurrogate(c) && this.index > this.start) {
/* 116 */         char c1 = this.text[this.index - 1];
/* 117 */         if (Character.isHighSurrogate(c1)) {
/* 118 */           this.index--;
/* 119 */           return Character.toCodePoint(c1, c);
/*     */         } 
/*     */       } 
/* 122 */       return c;
/*     */     } 
/* 124 */     return -1;
/*     */   }
/*     */   
/*     */   public int charIndex() {
/* 128 */     return this.index;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/CharArrayCodePointIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */