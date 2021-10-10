/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.im.InputMethodHighlight;
/*     */ import java.text.Annotation;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import sun.font.Decoration;
/*     */ import sun.font.FontResolver;
/*     */ import sun.text.CodePointIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class StyledParagraph
/*     */ {
/*     */   private int length;
/*     */   private Decoration decoration;
/*     */   private Object font;
/*     */   private Vector<Decoration> decorations;
/*     */   int[] decorationStarts;
/*     */   private Vector<Object> fonts;
/*     */   int[] fontStarts;
/*  87 */   private static int INITIAL_SIZE = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StyledParagraph(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfchar) {
/*  97 */     int i = paramAttributedCharacterIterator.getBeginIndex();
/*  98 */     int j = paramAttributedCharacterIterator.getEndIndex();
/*  99 */     this.length = j - i;
/*     */     
/* 101 */     int k = i;
/* 102 */     paramAttributedCharacterIterator.first();
/*     */     
/*     */     do {
/* 105 */       int m = paramAttributedCharacterIterator.getRunLimit();
/* 106 */       int n = k - i;
/*     */       
/* 108 */       Map<AttributedCharacterIterator.Attribute, Object> map = paramAttributedCharacterIterator.getAttributes();
/* 109 */       map = (Map)addInputMethodAttrs(map);
/* 110 */       Decoration decoration = Decoration.getDecoration(map);
/* 111 */       addDecoration(decoration, n);
/*     */       
/* 113 */       Object object = getGraphicOrFont(map);
/* 114 */       if (object == null) {
/* 115 */         addFonts(paramArrayOfchar, map, n, m - i);
/*     */       } else {
/*     */         
/* 118 */         addFont(object, n);
/*     */       } 
/*     */       
/* 121 */       paramAttributedCharacterIterator.setIndex(m);
/* 122 */       k = m;
/*     */     }
/* 124 */     while (k < j);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (this.decorations != null) {
/* 130 */       this.decorationStarts = addToVector(this, this.length, this.decorations, this.decorationStarts);
/*     */     }
/* 132 */     if (this.fonts != null) {
/* 133 */       this.fontStarts = addToVector(this, this.length, this.fonts, this.fontStarts);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertInto(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 143 */     while (paramArrayOfint[--paramInt2] > paramInt1) {
/* 144 */       paramArrayOfint[paramInt2] = paramArrayOfint[paramInt2] + 1;
/*     */     }
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
/*     */   public static StyledParagraph insertChar(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfchar, int paramInt, StyledParagraph paramStyledParagraph) {
/* 169 */     char c = paramAttributedCharacterIterator.setIndex(paramInt);
/* 170 */     int i = Math.max(paramInt - paramAttributedCharacterIterator.getBeginIndex() - 1, 0);
/*     */ 
/*     */     
/* 173 */     Map<? extends AttributedCharacterIterator.Attribute, ?> map = addInputMethodAttrs(paramAttributedCharacterIterator.getAttributes());
/* 174 */     Decoration decoration = Decoration.getDecoration(map);
/* 175 */     if (!paramStyledParagraph.getDecorationAt(i).equals(decoration)) {
/* 176 */       return new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfchar);
/*     */     }
/* 178 */     Object object = getGraphicOrFont(map);
/* 179 */     if (object == null) {
/* 180 */       FontResolver fontResolver = FontResolver.getInstance();
/* 181 */       int j = fontResolver.getFontIndex(c);
/* 182 */       object = fontResolver.getFont(j, map);
/*     */     } 
/* 184 */     if (!paramStyledParagraph.getFontOrGraphicAt(i).equals(object)) {
/* 185 */       return new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfchar);
/*     */     }
/*     */ 
/*     */     
/* 189 */     paramStyledParagraph.length++;
/* 190 */     if (paramStyledParagraph.decorations != null) {
/* 191 */       insertInto(i, paramStyledParagraph.decorationStarts, paramStyledParagraph.decorations
/*     */           
/* 193 */           .size());
/*     */     }
/* 195 */     if (paramStyledParagraph.fonts != null) {
/* 196 */       insertInto(i, paramStyledParagraph.fontStarts, paramStyledParagraph.fonts
/*     */           
/* 198 */           .size());
/*     */     }
/* 200 */     return paramStyledParagraph;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void deleteFrom(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/* 211 */     while (paramArrayOfint[--paramInt2] > paramInt1) {
/* 212 */       paramArrayOfint[paramInt2] = paramArrayOfint[paramInt2] - 1;
/*     */     }
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
/*     */   public static StyledParagraph deleteChar(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfchar, int paramInt, StyledParagraph paramStyledParagraph) {
/* 236 */     paramInt -= paramAttributedCharacterIterator.getBeginIndex();
/*     */     
/* 238 */     if (paramStyledParagraph.decorations == null && paramStyledParagraph.fonts == null) {
/* 239 */       paramStyledParagraph.length--;
/* 240 */       return paramStyledParagraph;
/*     */     } 
/*     */     
/* 243 */     if (paramStyledParagraph.getRunLimit(paramInt) == paramInt + 1 && (
/* 244 */       paramInt == 0 || paramStyledParagraph.getRunLimit(paramInt - 1) == paramInt)) {
/* 245 */       return new StyledParagraph(paramAttributedCharacterIterator, paramArrayOfchar);
/*     */     }
/*     */ 
/*     */     
/* 249 */     paramStyledParagraph.length--;
/* 250 */     if (paramStyledParagraph.decorations != null) {
/* 251 */       deleteFrom(paramInt, paramStyledParagraph.decorationStarts, paramStyledParagraph.decorations
/*     */           
/* 253 */           .size());
/*     */     }
/* 255 */     if (paramStyledParagraph.fonts != null) {
/* 256 */       deleteFrom(paramInt, paramStyledParagraph.fontStarts, paramStyledParagraph.fonts
/*     */           
/* 258 */           .size());
/*     */     }
/* 260 */     return paramStyledParagraph;
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
/*     */   public int getRunLimit(int paramInt) {
/* 272 */     if (paramInt < 0 || paramInt >= this.length) {
/* 273 */       throw new IllegalArgumentException("index out of range");
/*     */     }
/* 275 */     int i = this.length;
/* 276 */     if (this.decorations != null) {
/* 277 */       int k = findRunContaining(paramInt, this.decorationStarts);
/* 278 */       i = this.decorationStarts[k + 1];
/*     */     } 
/* 280 */     int j = this.length;
/* 281 */     if (this.fonts != null) {
/* 282 */       int k = findRunContaining(paramInt, this.fontStarts);
/* 283 */       j = this.fontStarts[k + 1];
/*     */     } 
/* 285 */     return Math.min(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decoration getDecorationAt(int paramInt) {
/* 295 */     if (paramInt < 0 || paramInt >= this.length) {
/* 296 */       throw new IllegalArgumentException("index out of range");
/*     */     }
/* 298 */     if (this.decorations == null) {
/* 299 */       return this.decoration;
/*     */     }
/* 301 */     int i = findRunContaining(paramInt, this.decorationStarts);
/* 302 */     return this.decorations.elementAt(i);
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
/*     */   public Object getFontOrGraphicAt(int paramInt) {
/* 314 */     if (paramInt < 0 || paramInt >= this.length) {
/* 315 */       throw new IllegalArgumentException("index out of range");
/*     */     }
/* 317 */     if (this.fonts == null) {
/* 318 */       return this.font;
/*     */     }
/* 320 */     int i = findRunContaining(paramInt, this.fontStarts);
/* 321 */     return this.fonts.elementAt(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findRunContaining(int paramInt, int[] paramArrayOfint) {
/* 331 */     for (byte b = 1;; b++) {
/* 332 */       if (paramArrayOfint[b] > paramInt) {
/* 333 */         return b - 1;
/*     */       }
/*     */     } 
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
/*     */   private static int[] addToVector(Object paramObject, int paramInt, Vector<E> paramVector, int[] paramArrayOfint) {
/* 350 */     if (!paramVector.lastElement().equals(paramObject)) {
/* 351 */       paramVector.addElement((E)paramObject);
/* 352 */       int i = paramVector.size();
/* 353 */       if (paramArrayOfint.length == i) {
/* 354 */         int[] arrayOfInt = new int[paramArrayOfint.length * 2];
/* 355 */         System.arraycopy(paramArrayOfint, 0, arrayOfInt, 0, paramArrayOfint.length);
/* 356 */         paramArrayOfint = arrayOfInt;
/*     */       } 
/* 358 */       paramArrayOfint[i - 1] = paramInt;
/*     */     } 
/* 360 */     return paramArrayOfint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDecoration(Decoration paramDecoration, int paramInt) {
/* 369 */     if (this.decorations != null) {
/* 370 */       this.decorationStarts = addToVector(paramDecoration, paramInt, this.decorations, this.decorationStarts);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 375 */     else if (this.decoration == null) {
/* 376 */       this.decoration = paramDecoration;
/*     */     
/*     */     }
/* 379 */     else if (!this.decoration.equals(paramDecoration)) {
/* 380 */       this.decorations = new Vector<>(INITIAL_SIZE);
/* 381 */       this.decorations.addElement(this.decoration);
/* 382 */       this.decorations.addElement(paramDecoration);
/* 383 */       this.decorationStarts = new int[INITIAL_SIZE];
/* 384 */       this.decorationStarts[0] = 0;
/* 385 */       this.decorationStarts[1] = paramInt;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFont(Object paramObject, int paramInt) {
/* 396 */     if (this.fonts != null) {
/* 397 */       this.fontStarts = addToVector(paramObject, paramInt, this.fonts, this.fontStarts);
/*     */     }
/* 399 */     else if (this.font == null) {
/* 400 */       this.font = paramObject;
/*     */     
/*     */     }
/* 403 */     else if (!this.font.equals(paramObject)) {
/* 404 */       this.fonts = new Vector(INITIAL_SIZE);
/* 405 */       this.fonts.addElement(this.font);
/* 406 */       this.fonts.addElement(paramObject);
/* 407 */       this.fontStarts = new int[INITIAL_SIZE];
/* 408 */       this.fontStarts[0] = 0;
/* 409 */       this.fontStarts[1] = paramInt;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFonts(char[] paramArrayOfchar, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, int paramInt1, int paramInt2) {
/* 421 */     FontResolver fontResolver = FontResolver.getInstance();
/* 422 */     CodePointIterator codePointIterator = CodePointIterator.create(paramArrayOfchar, paramInt1, paramInt2); int i;
/* 423 */     for (i = codePointIterator.charIndex(); i < paramInt2; i = codePointIterator.charIndex()) {
/* 424 */       int j = fontResolver.nextFontRunIndex(codePointIterator);
/* 425 */       addFont(fontResolver.getFont(j, paramMap), i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Map<? extends AttributedCharacterIterator.Attribute, ?> addInputMethodAttrs(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 436 */     Object object = paramMap.get(TextAttribute.INPUT_METHOD_HIGHLIGHT);
/*     */     
/*     */     try {
/* 439 */       if (object != null) {
/* 440 */         if (object instanceof Annotation) {
/* 441 */           object = ((Annotation)object).getValue();
/*     */         }
/*     */ 
/*     */         
/* 445 */         InputMethodHighlight inputMethodHighlight = (InputMethodHighlight)object;
/*     */         
/* 447 */         Map<TextAttribute, ?> map = null;
/*     */         try {
/* 449 */           map = inputMethodHighlight.getStyle();
/* 450 */         } catch (NoSuchMethodError noSuchMethodError) {}
/*     */ 
/*     */         
/* 453 */         if (map == null) {
/* 454 */           Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 455 */           map = toolkit.mapInputMethodHighlight(inputMethodHighlight);
/*     */         } 
/*     */         
/* 458 */         if (map != null)
/*     */         {
/* 460 */           HashMap<Object, Object> hashMap = new HashMap<>(5, 0.9F);
/* 461 */           hashMap.putAll(paramMap);
/*     */           
/* 463 */           hashMap.putAll(map);
/*     */           
/* 465 */           return (Map)hashMap;
/*     */         }
/*     */       
/*     */       } 
/* 469 */     } catch (ClassCastException classCastException) {}
/*     */ 
/*     */     
/* 472 */     return paramMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getGraphicOrFont(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/* 483 */     Object object = paramMap.get(TextAttribute.CHAR_REPLACEMENT);
/* 484 */     if (object != null) {
/* 485 */       return object;
/*     */     }
/* 487 */     object = paramMap.get(TextAttribute.FONT);
/* 488 */     if (object != null) {
/* 489 */       return object;
/*     */     }
/*     */     
/* 492 */     if (paramMap.get(TextAttribute.FAMILY) != null) {
/* 493 */       return Font.getFont(paramMap);
/*     */     }
/*     */     
/* 496 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/StyledParagraph.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */