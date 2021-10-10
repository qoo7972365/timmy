/*     */ package java.awt.font;
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
/*     */ class CharArrayIterator
/*     */   implements CharacterIterator
/*     */ {
/*     */   private char[] chars;
/*     */   private int pos;
/*     */   private int begin;
/*     */   
/*     */   CharArrayIterator(char[] paramArrayOfchar) {
/*  38 */     reset(paramArrayOfchar, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   CharArrayIterator(char[] paramArrayOfchar, int paramInt) {
/*  43 */     reset(paramArrayOfchar, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char first() {
/*  54 */     this.pos = 0;
/*  55 */     return current();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char last() {
/*  66 */     if (this.chars.length > 0) {
/*  67 */       this.pos = this.chars.length - 1;
/*     */     } else {
/*     */       
/*  70 */       this.pos = 0;
/*     */     } 
/*  72 */     return current();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char current() {
/*  83 */     if (this.pos >= 0 && this.pos < this.chars.length) {
/*  84 */       return this.chars[this.pos];
/*     */     }
/*     */     
/*  87 */     return Character.MAX_VALUE;
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
/*     */   public char next() {
/* 101 */     if (this.pos < this.chars.length - 1) {
/* 102 */       this.pos++;
/* 103 */       return this.chars[this.pos];
/*     */     } 
/*     */     
/* 106 */     this.pos = this.chars.length;
/* 107 */     return Character.MAX_VALUE;
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
/*     */   public char previous() {
/* 120 */     if (this.pos > 0) {
/* 121 */       this.pos--;
/* 122 */       return this.chars[this.pos];
/*     */     } 
/*     */     
/* 125 */     this.pos = 0;
/* 126 */     return Character.MAX_VALUE;
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
/*     */   public char setIndex(int paramInt) {
/* 140 */     paramInt -= this.begin;
/* 141 */     if (paramInt < 0 || paramInt > this.chars.length) {
/* 142 */       throw new IllegalArgumentException("Invalid index");
/*     */     }
/* 144 */     this.pos = paramInt;
/* 145 */     return current();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBeginIndex() {
/* 153 */     return this.begin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndIndex() {
/* 162 */     return this.begin + this.chars.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 170 */     return this.begin + this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 178 */     CharArrayIterator charArrayIterator = new CharArrayIterator(this.chars, this.begin);
/* 179 */     charArrayIterator.pos = this.pos;
/* 180 */     return charArrayIterator;
/*     */   }
/*     */   
/*     */   void reset(char[] paramArrayOfchar) {
/* 184 */     reset(paramArrayOfchar, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   void reset(char[] paramArrayOfchar, int paramInt) {
/* 189 */     this.chars = paramArrayOfchar;
/* 190 */     this.begin = paramInt;
/* 191 */     this.pos = 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/CharArrayIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */