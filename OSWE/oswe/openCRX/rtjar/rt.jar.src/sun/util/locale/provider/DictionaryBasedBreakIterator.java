/*     */ package sun.util.locale.provider;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.CharacterIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DictionaryBasedBreakIterator
/*     */   extends RuleBasedBreakIterator
/*     */ {
/*     */   private BreakDictionary dictionary;
/*     */   private boolean[] categoryFlags;
/*     */   private int dictionaryCharCount;
/*     */   private int[] cachedBreakPositions;
/*     */   private int positionInCache;
/*     */   
/*     */   DictionaryBasedBreakIterator(String paramString1, String paramString2) throws IOException {
/* 118 */     super(paramString1);
/* 119 */     byte[] arrayOfByte = getAdditionalData();
/* 120 */     if (arrayOfByte != null) {
/* 121 */       prepareCategoryFlags(arrayOfByte);
/* 122 */       setAdditionalData(null);
/*     */     } 
/* 124 */     this.dictionary = new BreakDictionary(paramString2);
/*     */   }
/*     */   
/*     */   private void prepareCategoryFlags(byte[] paramArrayOfbyte) {
/* 128 */     this.categoryFlags = new boolean[paramArrayOfbyte.length];
/* 129 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 130 */       this.categoryFlags[b] = (paramArrayOfbyte[b] == 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(CharacterIterator paramCharacterIterator) {
/* 136 */     super.setText(paramCharacterIterator);
/* 137 */     this.cachedBreakPositions = null;
/* 138 */     this.dictionaryCharCount = 0;
/* 139 */     this.positionInCache = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int first() {
/* 149 */     this.cachedBreakPositions = null;
/* 150 */     this.dictionaryCharCount = 0;
/* 151 */     this.positionInCache = 0;
/* 152 */     return super.first();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int last() {
/* 162 */     this.cachedBreakPositions = null;
/* 163 */     this.dictionaryCharCount = 0;
/* 164 */     this.positionInCache = 0;
/* 165 */     return super.last();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int previous() {
/* 175 */     CharacterIterator characterIterator = getText();
/*     */ 
/*     */ 
/*     */     
/* 179 */     if (this.cachedBreakPositions != null && this.positionInCache > 0) {
/* 180 */       this.positionInCache--;
/* 181 */       characterIterator.setIndex(this.cachedBreakPositions[this.positionInCache]);
/* 182 */       return this.cachedBreakPositions[this.positionInCache];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     this.cachedBreakPositions = null;
/* 190 */     int i = super.previous();
/* 191 */     if (this.cachedBreakPositions != null) {
/* 192 */       this.positionInCache = this.cachedBreakPositions.length - 2;
/*     */     }
/* 194 */     return i;
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
/*     */   public int preceding(int paramInt) {
/* 206 */     CharacterIterator characterIterator = getText();
/* 207 */     checkOffset(paramInt, characterIterator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     if (this.cachedBreakPositions == null || paramInt <= this.cachedBreakPositions[0] || paramInt > this.cachedBreakPositions[this.cachedBreakPositions.length - 1]) {
/*     */       
/* 215 */       this.cachedBreakPositions = null;
/* 216 */       return super.preceding(paramInt);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     this.positionInCache = 0;
/* 224 */     while (this.positionInCache < this.cachedBreakPositions.length && paramInt > this.cachedBreakPositions[this.positionInCache])
/*     */     {
/* 226 */       this.positionInCache++;
/*     */     }
/* 228 */     this.positionInCache--;
/* 229 */     characterIterator.setIndex(this.cachedBreakPositions[this.positionInCache]);
/* 230 */     return characterIterator.getIndex();
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
/*     */   public int following(int paramInt) {
/* 242 */     CharacterIterator characterIterator = getText();
/* 243 */     checkOffset(paramInt, characterIterator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     if (this.cachedBreakPositions == null || paramInt < this.cachedBreakPositions[0] || paramInt >= this.cachedBreakPositions[this.cachedBreakPositions.length - 1]) {
/*     */       
/* 251 */       this.cachedBreakPositions = null;
/* 252 */       return super.following(paramInt);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     this.positionInCache = 0;
/* 260 */     while (this.positionInCache < this.cachedBreakPositions.length && paramInt >= this.cachedBreakPositions[this.positionInCache])
/*     */     {
/* 262 */       this.positionInCache++;
/*     */     }
/* 264 */     characterIterator.setIndex(this.cachedBreakPositions[this.positionInCache]);
/* 265 */     return characterIterator.getIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int handleNext() {
/* 274 */     CharacterIterator characterIterator = getText();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     if (this.cachedBreakPositions == null || this.positionInCache == this.cachedBreakPositions.length - 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       int i = characterIterator.getIndex();
/* 286 */       this.dictionaryCharCount = 0;
/* 287 */       int j = super.handleNext();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       if (this.dictionaryCharCount > 1 && j - i > 1) {
/* 293 */         divideUpDictionaryRange(i, j);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 299 */         this.cachedBreakPositions = null;
/* 300 */         return j;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     if (this.cachedBreakPositions != null) {
/* 308 */       this.positionInCache++;
/* 309 */       characterIterator.setIndex(this.cachedBreakPositions[this.positionInCache]);
/* 310 */       return this.cachedBreakPositions[this.positionInCache];
/*     */     } 
/* 312 */     return -9999;
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
/*     */   protected int lookupCategory(int paramInt) {
/* 325 */     int i = super.lookupCategory(paramInt);
/* 326 */     if (i != -1 && this.categoryFlags[i]) {
/* 327 */       this.dictionaryCharCount++;
/*     */     }
/* 329 */     return i;
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
/*     */   private void divideUpDictionaryRange(int paramInt1, int paramInt2) {
/* 342 */     CharacterIterator characterIterator = getText();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     characterIterator.setIndex(paramInt1);
/* 349 */     int i = getCurrent();
/* 350 */     int j = lookupCategory(i);
/* 351 */     while (j == -1 || !this.categoryFlags[j]) {
/* 352 */       i = getNext();
/* 353 */       j = lookupCategory(i);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 367 */     Stack<Integer> stack1 = new Stack();
/* 368 */     Stack<Integer> stack2 = new Stack();
/* 369 */     ArrayList<Integer> arrayList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     short s = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 384 */     int k = characterIterator.getIndex();
/* 385 */     Stack<Integer> stack3 = null;
/*     */ 
/*     */     
/* 388 */     i = getCurrent();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 394 */       if (this.dictionary.getNextState(s, 0) == -1) {
/* 395 */         stack2.push(Integer.valueOf(characterIterator.getIndex()));
/*     */       }
/*     */ 
/*     */       
/* 399 */       s = this.dictionary.getNextStateFromCharacter(s, i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 405 */       if (s == -1) {
/* 406 */         stack1.push(Integer.valueOf(characterIterator.getIndex()));
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 414 */       if (s == 0 || characterIterator.getIndex() >= paramInt2) {
/*     */ 
/*     */ 
/*     */         
/* 418 */         if (characterIterator.getIndex() > k) {
/* 419 */           k = characterIterator.getIndex();
/*     */ 
/*     */           
/* 422 */           Stack<Integer> stack = (Stack)stack1.clone();
/*     */           
/* 424 */           stack3 = stack;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 440 */         while (!stack2.isEmpty() && arrayList
/* 441 */           .contains(stack2.peek())) {
/* 442 */           stack2.pop();
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 450 */         if (stack2.isEmpty()) {
/* 451 */           if (stack3 != null) {
/* 452 */             stack1 = stack3;
/* 453 */             if (k < paramInt2) {
/* 454 */               characterIterator.setIndex(k + 1);
/*     */             } else {
/*     */               
/*     */               break;
/*     */             } 
/*     */           } else {
/*     */             
/* 461 */             if ((stack1.size() == 0 || ((Integer)stack1
/* 462 */               .peek()).intValue() != characterIterator.getIndex()) && characterIterator
/* 463 */               .getIndex() != paramInt1) {
/* 464 */               stack1.push(new Integer(characterIterator.getIndex()));
/*     */             }
/* 466 */             getNext();
/* 467 */             stack1.push(new Integer(characterIterator.getIndex()));
/*     */ 
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 477 */           Integer integer1 = stack2.pop();
/* 478 */           Integer integer2 = null;
/* 479 */           while (!stack1.isEmpty() && integer1.intValue() < ((Integer)stack1
/* 480 */             .peek()).intValue()) {
/* 481 */             integer2 = stack1.pop();
/* 482 */             arrayList.add(integer2);
/*     */           } 
/* 484 */           stack1.push(integer1);
/* 485 */           characterIterator.setIndex(((Integer)stack1.peek()).intValue());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 490 */         i = getCurrent();
/* 491 */         if (characterIterator.getIndex() >= paramInt2) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 499 */       i = getNext();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     if (!stack1.isEmpty()) {
/* 508 */       stack1.pop();
/*     */     }
/* 510 */     stack1.push(Integer.valueOf(paramInt2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 517 */     this.cachedBreakPositions = new int[stack1.size() + 1];
/* 518 */     this.cachedBreakPositions[0] = paramInt1;
/*     */     
/* 520 */     for (byte b = 0; b < stack1.size(); b++) {
/* 521 */       this.cachedBreakPositions[b + 1] = ((Integer)stack1.elementAt(b)).intValue();
/*     */     }
/* 523 */     this.positionInCache = 0;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/provider/DictionaryBasedBreakIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */