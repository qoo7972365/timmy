/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringCharacterIterator
/*     */   implements CharacterIterator
/*     */ {
/*     */   private String text;
/*     */   private int begin;
/*     */   private int end;
/*     */   private int pos;
/*     */   
/*     */   public StringCharacterIterator(String paramString) {
/*  67 */     this(paramString, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringCharacterIterator(String paramString, int paramInt) {
/*  78 */     this(paramString, 0, paramString.length(), paramInt);
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
/*     */   public StringCharacterIterator(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/*  91 */     if (paramString == null)
/*  92 */       throw new NullPointerException(); 
/*  93 */     this.text = paramString;
/*     */     
/*  95 */     if (paramInt1 < 0 || paramInt1 > paramInt2 || paramInt2 > paramString.length()) {
/*  96 */       throw new IllegalArgumentException("Invalid substring range");
/*     */     }
/*  98 */     if (paramInt3 < paramInt1 || paramInt3 > paramInt2) {
/*  99 */       throw new IllegalArgumentException("Invalid position");
/*     */     }
/* 101 */     this.begin = paramInt1;
/* 102 */     this.end = paramInt2;
/* 103 */     this.pos = paramInt3;
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
/*     */   public void setText(String paramString) {
/* 116 */     if (paramString == null)
/* 117 */       throw new NullPointerException(); 
/* 118 */     this.text = paramString;
/* 119 */     this.begin = 0;
/* 120 */     this.end = paramString.length();
/* 121 */     this.pos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char first() {
/* 130 */     this.pos = this.begin;
/* 131 */     return current();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char last() {
/* 140 */     if (this.end != this.begin) {
/* 141 */       this.pos = this.end - 1;
/*     */     } else {
/* 143 */       this.pos = this.end;
/*     */     } 
/* 145 */     return current();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char setIndex(int paramInt) {
/* 154 */     if (paramInt < this.begin || paramInt > this.end)
/* 155 */       throw new IllegalArgumentException("Invalid index"); 
/* 156 */     this.pos = paramInt;
/* 157 */     return current();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char current() {
/* 166 */     if (this.pos >= this.begin && this.pos < this.end) {
/* 167 */       return this.text.charAt(this.pos);
/*     */     }
/*     */     
/* 170 */     return Character.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char next() {
/* 180 */     if (this.pos < this.end - 1) {
/* 181 */       this.pos++;
/* 182 */       return this.text.charAt(this.pos);
/*     */     } 
/*     */     
/* 185 */     this.pos = this.end;
/* 186 */     return Character.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char previous() {
/* 196 */     if (this.pos > this.begin) {
/* 197 */       this.pos--;
/* 198 */       return this.text.charAt(this.pos);
/*     */     } 
/*     */     
/* 201 */     return Character.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBeginIndex() {
/* 211 */     return this.begin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndIndex() {
/* 220 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 229 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 240 */     if (this == paramObject)
/* 241 */       return true; 
/* 242 */     if (!(paramObject instanceof StringCharacterIterator)) {
/* 243 */       return false;
/*     */     }
/* 245 */     StringCharacterIterator stringCharacterIterator = (StringCharacterIterator)paramObject;
/*     */     
/* 247 */     if (hashCode() != stringCharacterIterator.hashCode())
/* 248 */       return false; 
/* 249 */     if (!this.text.equals(stringCharacterIterator.text))
/* 250 */       return false; 
/* 251 */     if (this.pos != stringCharacterIterator.pos || this.begin != stringCharacterIterator.begin || this.end != stringCharacterIterator.end)
/* 252 */       return false; 
/* 253 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 262 */     return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 272 */       return super
/* 273 */         .clone();
/*     */     
/*     */     }
/* 276 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 277 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/StringCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */