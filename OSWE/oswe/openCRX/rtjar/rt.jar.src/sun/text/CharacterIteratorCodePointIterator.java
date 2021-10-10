/*     */ package sun.text;
/*     */ 
/*     */ import java.text.CharacterIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class CharacterIteratorCodePointIterator
/*     */   extends CodePointIterator
/*     */ {
/*     */   private CharacterIterator iter;
/*     */   
/*     */   public CharacterIteratorCodePointIterator(CharacterIterator paramCharacterIterator) {
/* 188 */     this.iter = paramCharacterIterator;
/*     */   }
/*     */   
/*     */   public void setToStart() {
/* 192 */     this.iter.setIndex(this.iter.getBeginIndex());
/*     */   }
/*     */   
/*     */   public void setToLimit() {
/* 196 */     this.iter.setIndex(this.iter.getEndIndex());
/*     */   }
/*     */   
/*     */   public int next() {
/* 200 */     char c = this.iter.current();
/* 201 */     if (c != Character.MAX_VALUE) {
/* 202 */       char c1 = this.iter.next();
/* 203 */       if (Character.isHighSurrogate(c) && c1 != Character.MAX_VALUE && 
/* 204 */         Character.isLowSurrogate(c1)) {
/* 205 */         this.iter.next();
/* 206 */         return Character.toCodePoint(c, c1);
/*     */       } 
/*     */       
/* 209 */       return c;
/*     */     } 
/* 211 */     return -1;
/*     */   }
/*     */   
/*     */   public int prev() {
/* 215 */     char c = this.iter.previous();
/* 216 */     if (c != Character.MAX_VALUE) {
/* 217 */       if (Character.isLowSurrogate(c)) {
/* 218 */         char c1 = this.iter.previous();
/* 219 */         if (Character.isHighSurrogate(c1)) {
/* 220 */           return Character.toCodePoint(c1, c);
/*     */         }
/* 222 */         this.iter.next();
/*     */       } 
/* 224 */       return c;
/*     */     } 
/* 226 */     return -1;
/*     */   }
/*     */   
/*     */   public int charIndex() {
/* 230 */     return this.iter.getIndex();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/CharacterIteratorCodePointIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */