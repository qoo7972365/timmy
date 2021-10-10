/*     */ package java.lang;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.StreamSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface CharSequence
/*     */ {
/*     */   default IntStream chars() {
/*     */     class CharIterator
/*     */       implements PrimitiveIterator.OfInt
/*     */     {
/* 132 */       int cur = 0;
/*     */       
/*     */       public boolean hasNext() {
/* 135 */         return (this.cur < CharSequence.this.length());
/*     */       }
/*     */       
/*     */       public int nextInt() {
/* 139 */         if (hasNext()) {
/* 140 */           return CharSequence.this.charAt(this.cur++);
/*     */         }
/* 142 */         throw new NoSuchElementException();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 148 */         for (; this.cur < CharSequence.this.length(); this.cur++) {
/* 149 */           param1IntConsumer.accept(CharSequence.this.charAt(this.cur));
/*     */         }
/*     */       }
/*     */     };
/*     */     
/* 154 */     return StreamSupport.intStream(() -> Spliterators.spliterator(new CharIterator(), length(), 16), 16464, false);
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
/*     */   default IntStream codePoints() {
/*     */     class CodePointIterator
/*     */       implements PrimitiveIterator.OfInt
/*     */     {
/* 179 */       int cur = 0;
/*     */ 
/*     */       
/*     */       public void forEachRemaining(IntConsumer param1IntConsumer) {
/* 183 */         int i = CharSequence.this.length();
/* 184 */         int j = this.cur;
/*     */         try {
/* 186 */           while (j < i) {
/* 187 */             char c1 = CharSequence.this.charAt(j++);
/* 188 */             if (!Character.isHighSurrogate(c1) || j >= i) {
/* 189 */               param1IntConsumer.accept(c1); continue;
/*     */             } 
/* 191 */             char c2 = CharSequence.this.charAt(j);
/* 192 */             if (Character.isLowSurrogate(c2)) {
/* 193 */               j++;
/* 194 */               param1IntConsumer.accept(Character.toCodePoint(c1, c2)); continue;
/*     */             } 
/* 196 */             param1IntConsumer.accept(c1);
/*     */           }
/*     */         
/*     */         } finally {
/*     */           
/* 201 */           this.cur = j;
/*     */         } 
/*     */       }
/*     */       
/*     */       public boolean hasNext() {
/* 206 */         return (this.cur < CharSequence.this.length());
/*     */       }
/*     */       
/*     */       public int nextInt() {
/* 210 */         int i = CharSequence.this.length();
/*     */         
/* 212 */         if (this.cur >= i) {
/* 213 */           throw new NoSuchElementException();
/*     */         }
/* 215 */         char c = CharSequence.this.charAt(this.cur++);
/* 216 */         if (Character.isHighSurrogate(c) && this.cur < i) {
/* 217 */           char c1 = CharSequence.this.charAt(this.cur);
/* 218 */           if (Character.isLowSurrogate(c1)) {
/* 219 */             this.cur++;
/* 220 */             return Character.toCodePoint(c, c1);
/*     */           } 
/*     */         } 
/* 223 */         return c;
/*     */       }
/*     */     };
/*     */     
/* 227 */     return StreamSupport.intStream(() -> Spliterators.spliteratorUnknownSize(new CodePointIterator(), 16), 16, false);
/*     */   }
/*     */   
/*     */   int length();
/*     */   
/*     */   char charAt(int paramInt);
/*     */   
/*     */   CharSequence subSequence(int paramInt1, int paramInt2);
/*     */   
/*     */   String toString();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/CharSequence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */