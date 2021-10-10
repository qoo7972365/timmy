/*     */ package sun.text.normalizer;
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
/*     */ public class CharacterIteratorWrapper
/*     */   extends UCharacterIterator
/*     */ {
/*     */   private CharacterIterator iterator;
/*     */   
/*     */   public CharacterIteratorWrapper(CharacterIterator paramCharacterIterator) {
/*  53 */     if (paramCharacterIterator == null) {
/*  54 */       throw new IllegalArgumentException();
/*     */     }
/*  56 */     this.iterator = paramCharacterIterator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int current() {
/*  63 */     char c = this.iterator.current();
/*  64 */     if (c == Character.MAX_VALUE) {
/*  65 */       return -1;
/*     */     }
/*  67 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/*  74 */     return this.iterator.getEndIndex() - this.iterator.getBeginIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  81 */     return this.iterator.getIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int next() {
/*  88 */     char c = this.iterator.current();
/*  89 */     this.iterator.next();
/*  90 */     if (c == Character.MAX_VALUE) {
/*  91 */       return -1;
/*     */     }
/*  93 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int previous() {
/* 100 */     char c = this.iterator.previous();
/* 101 */     if (c == Character.MAX_VALUE) {
/* 102 */       return -1;
/*     */     }
/* 104 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndex(int paramInt) {
/* 111 */     this.iterator.setIndex(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getText(char[] paramArrayOfchar, int paramInt) {
/* 119 */     int i = this.iterator.getEndIndex() - this.iterator.getBeginIndex();
/* 120 */     int j = this.iterator.getIndex();
/* 121 */     if (paramInt < 0 || paramInt + i > paramArrayOfchar.length) {
/* 122 */       throw new IndexOutOfBoundsException(Integer.toString(i));
/*     */     }
/*     */     char c;
/* 125 */     for (c = this.iterator.first(); c != Character.MAX_VALUE; c = this.iterator.next()) {
/* 126 */       paramArrayOfchar[paramInt++] = c;
/*     */     }
/* 128 */     this.iterator.setIndex(j);
/*     */     
/* 130 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 139 */       CharacterIteratorWrapper characterIteratorWrapper = (CharacterIteratorWrapper)super.clone();
/* 140 */       characterIteratorWrapper.iterator = (CharacterIterator)this.iterator.clone();
/* 141 */       return characterIteratorWrapper;
/* 142 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 143 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/CharacterIteratorWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */