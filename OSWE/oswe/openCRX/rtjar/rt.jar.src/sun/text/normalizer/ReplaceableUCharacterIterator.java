/*     */ package sun.text.normalizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReplaceableUCharacterIterator
/*     */   extends UCharacterIterator
/*     */ {
/*     */   private Replaceable replaceable;
/*     */   private int currentIndex;
/*     */   
/*     */   public ReplaceableUCharacterIterator(String paramString) {
/*  59 */     if (paramString == null) {
/*  60 */       throw new IllegalArgumentException();
/*     */     }
/*  62 */     this.replaceable = new ReplaceableString(paramString);
/*  63 */     this.currentIndex = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReplaceableUCharacterIterator(StringBuffer paramStringBuffer) {
/*  72 */     if (paramStringBuffer == null) {
/*  73 */       throw new IllegalArgumentException();
/*     */     }
/*  75 */     this.replaceable = new ReplaceableString(paramStringBuffer);
/*  76 */     this.currentIndex = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/*  88 */       return super.clone();
/*  89 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int current() {
/*  99 */     if (this.currentIndex < this.replaceable.length()) {
/* 100 */       return this.replaceable.charAt(this.currentIndex);
/*     */     }
/* 102 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 110 */     return this.replaceable.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 118 */     return this.currentIndex;
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
/*     */   public int next() {
/* 130 */     if (this.currentIndex < this.replaceable.length()) {
/* 131 */       return this.replaceable.charAt(this.currentIndex++);
/*     */     }
/* 133 */     return -1;
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
/*     */   public int previous() {
/* 146 */     if (this.currentIndex > 0) {
/* 147 */       return this.replaceable.charAt(--this.currentIndex);
/*     */     }
/* 149 */     return -1;
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
/*     */   public void setIndex(int paramInt) {
/* 163 */     if (paramInt < 0 || paramInt > this.replaceable.length()) {
/* 164 */       throw new IllegalArgumentException();
/*     */     }
/* 166 */     this.currentIndex = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getText(char[] paramArrayOfchar, int paramInt) {
/* 171 */     int i = this.replaceable.length();
/* 172 */     if (paramInt < 0 || paramInt + i > paramArrayOfchar.length) {
/* 173 */       throw new IndexOutOfBoundsException(Integer.toString(i));
/*     */     }
/* 175 */     this.replaceable.getChars(0, i, paramArrayOfchar, paramInt);
/* 176 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/normalizer/ReplaceableUCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */