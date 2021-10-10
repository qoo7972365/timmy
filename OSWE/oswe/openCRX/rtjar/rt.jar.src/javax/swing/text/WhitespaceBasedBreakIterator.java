/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.text.BreakIterator;
/*     */ import java.text.CharacterIterator;
/*     */ import java.text.StringCharacterIterator;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WhitespaceBasedBreakIterator
/*     */   extends BreakIterator
/*     */ {
/*  39 */   private char[] text = new char[0];
/*  40 */   private int[] breaks = new int[] { 0 };
/*  41 */   private int pos = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(CharacterIterator paramCharacterIterator) {
/*  47 */     int i = paramCharacterIterator.getBeginIndex();
/*  48 */     this.text = new char[paramCharacterIterator.getEndIndex() - i];
/*  49 */     int[] arrayOfInt = new int[this.text.length + 1];
/*  50 */     byte b1 = 0;
/*  51 */     arrayOfInt[b1++] = i;
/*     */     
/*  53 */     byte b2 = 0;
/*  54 */     boolean bool = false; char c;
/*  55 */     for (c = paramCharacterIterator.first(); c != Character.MAX_VALUE; c = paramCharacterIterator.next()) {
/*  56 */       this.text[b2] = c;
/*  57 */       boolean bool1 = Character.isWhitespace(c);
/*  58 */       if (bool && !bool1) {
/*  59 */         arrayOfInt[b1++] = b2 + i;
/*     */       }
/*  61 */       bool = bool1;
/*  62 */       b2++;
/*     */     } 
/*  64 */     if (this.text.length > 0) {
/*  65 */       arrayOfInt[b1++] = this.text.length + i;
/*     */     }
/*  67 */     System.arraycopy(arrayOfInt, 0, this.breaks = new int[b1], 0, b1);
/*     */   }
/*     */   
/*     */   public CharacterIterator getText() {
/*  71 */     return new StringCharacterIterator(new String(this.text));
/*     */   }
/*     */   
/*     */   public int first() {
/*  75 */     return this.breaks[this.pos = 0];
/*     */   }
/*     */   
/*     */   public int last() {
/*  79 */     return this.breaks[this.pos = this.breaks.length - 1];
/*     */   }
/*     */   
/*     */   public int current() {
/*  83 */     return this.breaks[this.pos];
/*     */   }
/*     */   
/*     */   public int next() {
/*  87 */     return (this.pos == this.breaks.length - 1) ? -1 : this.breaks[++this.pos];
/*     */   }
/*     */   
/*     */   public int previous() {
/*  91 */     return (this.pos == 0) ? -1 : this.breaks[--this.pos];
/*     */   }
/*     */   
/*     */   public int next(int paramInt) {
/*  95 */     return checkhit(this.pos + paramInt);
/*     */   }
/*     */   
/*     */   public int following(int paramInt) {
/*  99 */     return adjacent(paramInt, 1);
/*     */   }
/*     */   
/*     */   public int preceding(int paramInt) {
/* 103 */     return adjacent(paramInt, -1);
/*     */   }
/*     */   
/*     */   private int checkhit(int paramInt) {
/* 107 */     if (paramInt < 0 || paramInt >= this.breaks.length) {
/* 108 */       return -1;
/*     */     }
/* 110 */     return this.breaks[this.pos = paramInt];
/*     */   }
/*     */ 
/*     */   
/*     */   private int adjacent(int paramInt1, int paramInt2) {
/* 115 */     int i = Arrays.binarySearch(this.breaks, paramInt1);
/* 116 */     byte b = (i < 0) ? ((paramInt2 < 0) ? -1 : -2) : 0;
/* 117 */     return checkhit(Math.abs(i) + paramInt2 + b);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/WhitespaceBasedBreakIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */