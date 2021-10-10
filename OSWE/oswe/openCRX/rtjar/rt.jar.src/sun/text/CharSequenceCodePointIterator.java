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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CharSequenceCodePointIterator
/*     */   extends CodePointIterator
/*     */ {
/*     */   private CharSequence text;
/*     */   private int index;
/*     */   
/*     */   public CharSequenceCodePointIterator(CharSequence paramCharSequence) {
/* 137 */     this.text = paramCharSequence;
/*     */   }
/*     */   
/*     */   public void setToStart() {
/* 141 */     this.index = 0;
/*     */   }
/*     */   
/*     */   public void setToLimit() {
/* 145 */     this.index = this.text.length();
/*     */   }
/*     */   
/*     */   public int next() {
/* 149 */     if (this.index < this.text.length()) {
/* 150 */       char c = this.text.charAt(this.index++);
/* 151 */       if (Character.isHighSurrogate(c) && this.index < this.text.length()) {
/* 152 */         char c1 = this.text.charAt(this.index + 1);
/* 153 */         if (Character.isLowSurrogate(c1)) {
/* 154 */           this.index++;
/* 155 */           return Character.toCodePoint(c, c1);
/*     */         } 
/*     */       } 
/* 158 */       return c;
/*     */     } 
/* 160 */     return -1;
/*     */   }
/*     */   
/*     */   public int prev() {
/* 164 */     if (this.index > 0) {
/* 165 */       char c = this.text.charAt(--this.index);
/* 166 */       if (Character.isLowSurrogate(c) && this.index > 0) {
/* 167 */         char c1 = this.text.charAt(this.index - 1);
/* 168 */         if (Character.isHighSurrogate(c1)) {
/* 169 */           this.index--;
/* 170 */           return Character.toCodePoint(c1, c);
/*     */         } 
/*     */       } 
/* 173 */       return c;
/*     */     } 
/* 175 */     return -1;
/*     */   }
/*     */   
/*     */   public int charIndex() {
/* 179 */     return this.index;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/CharSequenceCodePointIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */