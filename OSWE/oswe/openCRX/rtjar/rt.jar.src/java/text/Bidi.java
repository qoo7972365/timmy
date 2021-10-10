/*     */ package java.text;
/*     */ 
/*     */ import sun.text.bidi.BidiBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Bidi
/*     */ {
/*     */   public static final int DIRECTION_LEFT_TO_RIGHT = 0;
/*     */   public static final int DIRECTION_RIGHT_TO_LEFT = 1;
/*     */   public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = -2;
/*     */   public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = -1;
/*     */   private BidiBase bidiBase;
/*     */   
/*     */   public Bidi(String paramString, int paramInt) {
/*  96 */     if (paramString == null) {
/*  97 */       throw new IllegalArgumentException("paragraph is null");
/*     */     }
/*     */     
/* 100 */     this.bidiBase = new BidiBase(paramString.toCharArray(), 0, null, 0, paramString.length(), paramInt);
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
/*     */   public Bidi(AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 129 */     if (paramAttributedCharacterIterator == null) {
/* 130 */       throw new IllegalArgumentException("paragraph is null");
/*     */     }
/*     */     
/* 133 */     this.bidiBase = new BidiBase(0, 0);
/* 134 */     this.bidiBase.setPara(paramAttributedCharacterIterator);
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
/*     */   public Bidi(char[] paramArrayOfchar, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4) {
/* 155 */     if (paramArrayOfchar == null) {
/* 156 */       throw new IllegalArgumentException("text is null");
/*     */     }
/* 158 */     if (paramInt3 < 0) {
/* 159 */       throw new IllegalArgumentException("bad length: " + paramInt3);
/*     */     }
/* 161 */     if (paramInt1 < 0 || paramInt3 > paramArrayOfchar.length - paramInt1) {
/* 162 */       throw new IllegalArgumentException("bad range: " + paramInt1 + " length: " + paramInt3 + " for text of length: " + paramArrayOfchar.length);
/*     */     }
/*     */ 
/*     */     
/* 166 */     if (paramArrayOfbyte != null && (paramInt2 < 0 || paramInt3 > paramArrayOfbyte.length - paramInt2)) {
/* 167 */       throw new IllegalArgumentException("bad range: " + paramInt2 + " length: " + paramInt3 + " for embeddings of length: " + paramArrayOfchar.length);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 172 */     this.bidiBase = new BidiBase(paramArrayOfchar, paramInt1, paramArrayOfbyte, paramInt2, paramInt3, paramInt4);
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
/*     */   public Bidi createLineBidi(int paramInt1, int paramInt2) {
/* 185 */     AttributedString attributedString = new AttributedString("");
/* 186 */     Bidi bidi = new Bidi(attributedString.getIterator());
/*     */     
/* 188 */     return this.bidiBase.setLine(this, this.bidiBase, bidi, bidi.bidiBase, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMixed() {
/* 198 */     return this.bidiBase.isMixed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeftToRight() {
/* 207 */     return this.bidiBase.isLeftToRight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRightToLeft() {
/* 215 */     return this.bidiBase.isRightToLeft();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 223 */     return this.bidiBase.getLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean baseIsLeftToRight() {
/* 231 */     return this.bidiBase.baseIsLeftToRight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBaseLevel() {
/* 239 */     return this.bidiBase.getParaLevel();
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
/*     */   public int getLevelAt(int paramInt) {
/* 251 */     return this.bidiBase.getLevelAt(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRunCount() {
/* 259 */     return this.bidiBase.countRuns();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRunLevel(int paramInt) {
/* 268 */     return this.bidiBase.getRunLevel(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRunStart(int paramInt) {
/* 278 */     return this.bidiBase.getRunStart(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRunLimit(int paramInt) {
/* 289 */     return this.bidiBase.getRunLimit(paramInt);
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
/*     */   public static boolean requiresBidi(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 304 */     return BidiBase.requiresBidi(paramArrayOfchar, paramInt1, paramInt2);
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
/*     */   public static void reorderVisually(byte[] paramArrayOfbyte, int paramInt1, Object[] paramArrayOfObject, int paramInt2, int paramInt3) {
/* 324 */     BidiBase.reorderVisually(paramArrayOfbyte, paramInt1, paramArrayOfObject, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 331 */     return this.bidiBase.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/Bidi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */