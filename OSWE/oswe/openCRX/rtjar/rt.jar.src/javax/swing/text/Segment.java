/*     */ package javax.swing.text;
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
/*     */ public class Segment
/*     */   implements Cloneable, CharacterIterator, CharSequence
/*     */ {
/*     */   public char[] array;
/*     */   public int offset;
/*     */   public int count;
/*     */   private boolean partialReturn;
/*     */   private int pos;
/*     */   
/*     */   public Segment() {
/*  70 */     this(null, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Segment(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/*  81 */     this.array = paramArrayOfchar;
/*  82 */     this.offset = paramInt1;
/*  83 */     this.count = paramInt2;
/*  84 */     this.partialReturn = false;
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
/*     */   public void setPartialReturn(boolean paramBoolean) {
/*  99 */     this.partialReturn = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPartialReturn() {
/* 109 */     return this.partialReturn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     if (this.array != null) {
/* 119 */       return new String(this.array, this.offset, this.count);
/*     */     }
/* 121 */     return "";
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
/*     */   public char first() {
/* 134 */     this.pos = this.offset;
/* 135 */     if (this.count != 0) {
/* 136 */       return this.array[this.pos];
/*     */     }
/* 138 */     return Character.MAX_VALUE;
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
/* 149 */     this.pos = this.offset + this.count;
/* 150 */     if (this.count != 0) {
/* 151 */       this.pos--;
/* 152 */       return this.array[this.pos];
/*     */     } 
/* 154 */     return Character.MAX_VALUE;
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
/* 165 */     if (this.count != 0 && this.pos < this.offset + this.count) {
/* 166 */       return this.array[this.pos];
/*     */     }
/* 168 */     return Character.MAX_VALUE;
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
/*     */   public char next() {
/* 181 */     this.pos++;
/* 182 */     int i = this.offset + this.count;
/* 183 */     if (this.pos >= i) {
/* 184 */       this.pos = i;
/* 185 */       return Character.MAX_VALUE;
/*     */     } 
/* 187 */     return current();
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
/*     */   public char previous() {
/* 199 */     if (this.pos == this.offset) {
/* 200 */       return Character.MAX_VALUE;
/*     */     }
/* 202 */     this.pos--;
/* 203 */     return current();
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
/*     */   public char setIndex(int paramInt) {
/* 216 */     int i = this.offset + this.count;
/* 217 */     if (paramInt < this.offset || paramInt > i) {
/* 218 */       throw new IllegalArgumentException("bad position: " + paramInt);
/*     */     }
/* 220 */     this.pos = paramInt;
/* 221 */     if (this.pos != i && this.count != 0) {
/* 222 */       return this.array[this.pos];
/*     */     }
/* 224 */     return Character.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBeginIndex() {
/* 233 */     return this.offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndIndex() {
/* 243 */     return this.offset + this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 252 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char charAt(int paramInt) {
/* 262 */     if (paramInt < 0 || paramInt >= this.count)
/*     */     {
/* 264 */       throw new StringIndexOutOfBoundsException(paramInt);
/*     */     }
/* 266 */     return this.array[this.offset + paramInt];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/* 274 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSequence subSequence(int paramInt1, int paramInt2) {
/* 282 */     if (paramInt1 < 0) {
/* 283 */       throw new StringIndexOutOfBoundsException(paramInt1);
/*     */     }
/* 285 */     if (paramInt2 > this.count) {
/* 286 */       throw new StringIndexOutOfBoundsException(paramInt2);
/*     */     }
/* 288 */     if (paramInt1 > paramInt2) {
/* 289 */       throw new StringIndexOutOfBoundsException(paramInt2 - paramInt1);
/*     */     }
/* 291 */     Segment segment = new Segment();
/* 292 */     segment.array = this.array;
/* 293 */     this.offset += paramInt1;
/* 294 */     segment.count = paramInt2 - paramInt1;
/* 295 */     return segment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     Object object;
/*     */     try {
/* 306 */       object = super.clone();
/* 307 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 308 */       object = null;
/*     */     } 
/* 310 */     return object;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/Segment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */