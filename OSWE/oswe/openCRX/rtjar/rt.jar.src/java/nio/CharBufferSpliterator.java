/*    */ package java.nio;
/*    */ 
/*    */ import java.util.Spliterator;
/*    */ import java.util.function.IntConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CharBufferSpliterator
/*    */   implements Spliterator.OfInt
/*    */ {
/*    */   private final CharBuffer buffer;
/*    */   private int index;
/*    */   private final int limit;
/*    */   
/*    */   CharBufferSpliterator(CharBuffer paramCharBuffer) {
/* 45 */     this(paramCharBuffer, paramCharBuffer.position(), paramCharBuffer.limit());
/*    */   }
/*    */   
/*    */   CharBufferSpliterator(CharBuffer paramCharBuffer, int paramInt1, int paramInt2) {
/* 49 */     assert paramInt1 <= paramInt2;
/* 50 */     this.buffer = paramCharBuffer;
/* 51 */     this.index = (paramInt1 <= paramInt2) ? paramInt1 : paramInt2;
/* 52 */     this.limit = paramInt2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Spliterator.OfInt trySplit() {
/* 57 */     int i = this.index, j = i + this.limit >>> 1;
/* 58 */     return (i >= j) ? null : new CharBufferSpliterator(this.buffer, i, this.index = j);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void forEachRemaining(IntConsumer paramIntConsumer) {
/* 65 */     if (paramIntConsumer == null)
/* 66 */       throw new NullPointerException(); 
/* 67 */     CharBuffer charBuffer = this.buffer;
/* 68 */     int i = this.index;
/* 69 */     int j = this.limit;
/* 70 */     this.index = j;
/* 71 */     while (i < j) {
/* 72 */       paramIntConsumer.accept(charBuffer.getUnchecked(i++));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean tryAdvance(IntConsumer paramIntConsumer) {
/* 78 */     if (paramIntConsumer == null)
/* 79 */       throw new NullPointerException(); 
/* 80 */     if (this.index >= 0 && this.index < this.limit) {
/* 81 */       paramIntConsumer.accept(this.buffer.getUnchecked(this.index++));
/* 82 */       return true;
/*    */     } 
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long estimateSize() {
/* 89 */     return (this.limit - this.index);
/*    */   }
/*    */ 
/*    */   
/*    */   public int characteristics() {
/* 94 */     return 16464;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/CharBufferSpliterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */